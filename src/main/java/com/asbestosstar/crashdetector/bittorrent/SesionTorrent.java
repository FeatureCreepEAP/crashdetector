package com.asbestosstar.crashdetector.bittorrent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;

/** Sesión de descarga o siembra administrada por el motor puro Java. */
public final class SesionTorrent {

	private final String id = UUID.randomUUID().toString();
	private final String nombre;
	private final String tipo;
	private final MetainfoTorrent metainfo;
	private final AlmacenTorrent almacenamiento;
	private final MotorBitTorrent motor;
	private final byte[] peerId;
	private final boolean seguirCompartiendo;
	private final AtomicReference<EstadoTorrent> estado;
	private final AtomicBoolean detenida = new AtomicBoolean(false);
	private final AtomicBoolean completadoAnunciado = new AtomicBoolean(false);
	private final AtomicLong descargadoRed = new AtomicLong();
	private final AtomicLong subidoRed = new AtomicLong();
	private final BitSet piezasCompletas;
	private final PlanificadorPiezas planificador;
	private final Set<ConexionPeer> conexiones = Collections
			.newSetFromMap(new ConcurrentHashMap<ConexionPeer, Boolean>());
	private final Set<DireccionPeer> paresConocidos = Collections
			.newSetFromMap(new ConcurrentHashMap<DireccionPeer, Boolean>());
	private final Set<DireccionPeer> paresConectando = Collections
			.newSetFromMap(new ConcurrentHashMap<DireccionPeer, Boolean>());
	private final ConcurrentLinkedQueue<DireccionPeer> colaPares = new ConcurrentLinkedQueue<DireccionPeer>();
	private final ExecutorService ejecutorPeers = Executors.newCachedThreadPool();
	private final ScheduledExecutorService coordinador = Executors.newSingleThreadScheduledExecutor();
	private volatile Future<?> futuroPrincipal;
	private volatile long siguienteAnnounce;
	private volatile int intervaloTracker = 1800;
	private volatile boolean verificacionTerminada;

	SesionTorrent(String nombre, String tipo, MetainfoTorrent metainfo, Path raiz, MotorBitTorrent motor, byte[] peerId,
			boolean seguirCompartiendo, Set<DireccionPeer> paresIniciales) throws IOException {
		this.nombre = nombre;
		this.tipo = tipo;
		this.metainfo = metainfo;
		this.almacenamiento = new AlmacenTorrent(metainfo, raiz);
		this.motor = motor;
		this.peerId = peerId;
		this.seguirCompartiendo = seguirCompartiendo;
		this.piezasCompletas = new BitSet(metainfo.cantidadPiezas());
		this.planificador = new PlanificadorPiezas(piezasCompletas, metainfo.cantidadPiezas());
		this.estado = new AtomicReference<EstadoTorrent>(
				new EstadoTorrent(id, nombre, tipo, MonitorDePID.idioma.bittorrentEstadoIniciando(), 0.0, 0, 0L, 0L));
		agregarPares(paresIniciales);
	}

	void iniciar() {
		futuroPrincipal = coordinador.submit(new Runnable() {
			@Override
			public void run() {
				ejecutarSesion();
			}
		});
	}

	private void ejecutarSesion() {
		try {
			BitSet existentes = almacenamiento.verificarExistentes(ConfiguracionBitTorrent.obtenerHilosHash(), null);
			synchronized (piezasCompletas) {
				piezasCompletas.or(existentes);
			}
			verificacionTerminada = true;
			motor.registrarSesion(this);
			anunciar(EventoTracker.INICIADO);
			actualizarEstado();

			while (!detenida.get()) {
				if (System.currentTimeMillis() >= siguienteAnnounce) {
					anunciar(EventoTracker.NINGUNO);
				}
				conectarParesPendientes();
				actualizarEstado();

				if (estaCompleto()) {
					if (completadoAnunciado.compareAndSet(false, true)) {
						anunciar(EventoTracker.COMPLETADO);
					}
					if (!seguirCompartiendo) {
						detener();
						break;
					}
				}
				Thread.sleep(1000L);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			estado.set(new EstadoTorrent(id, nombre, tipo, MonitorDePID.idioma.bittorrentEstadoError(), porcentaje(),
					conexiones.size(), descargadoRed.get(), subidoRed.get()));
		} finally {
			cerrarRecursos();
		}
	}

	private void anunciar(EventoTracker evento) {
		List<String> trackers = trackersCombinados();
		if (trackers.isEmpty()) {
			siguienteAnnounce = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);
			return;
		}

		int menorIntervalo = Integer.MAX_VALUE;
		SolicitudTracker solicitud = new SolicitudTracker(metainfo.infoHash(), peerId, motor.puerto(),
				descargadoRed.get(), bytesRestantes(), subidoRed.get(), evento,
				ConfiguracionBitTorrent.obtenerMaximoPares());
		for (String tracker : trackers) {
			try {
				RespuestaTracker respuesta = FabricaTrackers.crear(tracker).anunciar(solicitud);
				agregarPares(respuesta.pares());
				menorIntervalo = Math.min(menorIntervalo, respuesta.intervaloSegundos());
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}
		if (menorIntervalo == Integer.MAX_VALUE) {
			menorIntervalo = Math.min(300, intervaloTracker);
		}
		intervaloTracker = Math.max(60, menorIntervalo);
		siguienteAnnounce = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(intervaloTracker);
	}

	private List<String> trackersCombinados() {
		LinkedHashSet<String> resultado = new LinkedHashSet<String>(metainfo.trackers());
		resultado.addAll(ConfiguracionBitTorrent.obtenerTrackers());
		return new ArrayList<String>(resultado);
	}

	private void agregarPares(Iterable<DireccionPeer> pares) {
		if (pares == null) {
			return;
		}
		for (DireccionPeer peer : pares) {
			if (peer == null || paresConocidos.size() >= ConfiguracionBitTorrent.obtenerMaximoPares()) {
				break;
			}
			if (motor.esDireccionPropia(peer)) {
				continue;
			}
			if (paresConocidos.add(peer)) {
				colaPares.offer(peer);
			}
		}
	}

	private void conectarParesPendientes() {
		int limite = ConfiguracionBitTorrent.obtenerParesSimultaneos();
		while (!detenida.get() && conexiones.size() + paresConectando.size() < limite) {
			final DireccionPeer peer = colaPares.poll();
			if (peer == null) {
				return;
			}
			if (!paresConectando.add(peer)) {
				continue;
			}
			ejecutorPeers.execute(new Runnable() {
				@Override
				public void run() {
					try {
						ConexionPeer.saliente(SesionTorrent.this, peer).run();
					} finally {
						paresConectando.remove(peer);
					}
				}
			});
		}
	}

	void aceptarEntrante(Socket socket, DataInputStream entrada, DataOutputStream salida,
			HandshakeBitTorrent handshake) {
		if (detenida.get()) {
			try {
				socket.close();
			} catch (IOException ignorado) {
			}
			return;
		}
		ejecutorPeers.execute(ConexionPeer.entrante(this, socket, entrada, salida, handshake));
	}

	int reservarPieza(BitSet disponibles) {
		return planificador.reservar(disponibles);
	}

	void liberarPieza(int indice) {
		planificador.liberar(indice);
	}

	boolean guardarPieza(int indice, byte[] datos) {
		try {
			byte[] hash = java.security.MessageDigest.getInstance("SHA-1").digest(datos);
			if (!UtilBitTorrent.igualesConstante(hash, metainfo.hashPieza(indice))) {
				planificador.liberar(indice);
				return false;
			}
			almacenamiento.escribirPieza(indice, datos);
			synchronized (piezasCompletas) {
				piezasCompletas.set(indice);
			}
			planificador.completar(indice);
			for (ConexionPeer conexion : conexiones) {
				conexion.enviarHave(indice);
			}
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			planificador.liberar(indice);
			return false;
		}
	}

	byte[] leerBloque(int pieza, int inicio, int longitud) throws IOException {
		if (!tienePieza(pieza)) {
			throw new IOException();
		}
		return almacenamiento.leerBloque(pieza, inicio, longitud);
	}

	boolean tienePieza(int indice) {
		synchronized (piezasCompletas) {
			return piezasCompletas.get(indice);
		}
	}

	BitSet copiarPiezasCompletas() {
		synchronized (piezasCompletas) {
			return (BitSet) piezasCompletas.clone();
		}
	}

	void registrarConexion(ConexionPeer conexion) {
		conexiones.add(conexion);
	}

	void quitarConexion(ConexionPeer conexion) {
		conexiones.remove(conexion);
	}

	void sumarDescargado(long bytes) {
		if (bytes > 0) {
			descargadoRed.addAndGet(bytes);
		}
	}

	void sumarSubido(long bytes) {
		if (bytes > 0) {
			subidoRed.addAndGet(bytes);
		}
	}

	private void actualizarEstado() {
		String texto = estaCompleto() ? MonitorDePID.idioma.bittorrentEstadoCompartiendo()
				: MonitorDePID.idioma.bittorrentEstadoDescargando();
		if (!verificacionTerminada) {
			texto = MonitorDePID.idioma.bittorrentEstadoIniciando();
		}
		estado.set(new EstadoTorrent(id, nombre, tipo, texto, porcentaje(), conexiones.size(), descargadoRed.get(),
				subidoRed.get()));
	}

	private double porcentaje() {
		long completos = bytesCompletos();
		return metainfo.longitudTotal() <= 0L ? 0.0
				: Math.max(0.0, Math.min(100.0, completos * 100.0 / metainfo.longitudTotal()));
	}

	private long bytesCompletos() {
		return almacenamiento.bytesCompletos(copiarPiezasCompletas());
	}

	private long bytesRestantes() {
		return Math.max(0L, metainfo.longitudTotal() - bytesCompletos());
	}

	boolean estaCompleto() {
		synchronized (piezasCompletas) {
			return piezasCompletas.cardinality() == metainfo.cantidadPiezas();
		}
	}

	public String id() {
		return id;
	}

	public EstadoTorrent estado() {
		return estado.get();
	}

	public void detener() {
		if (detenida.compareAndSet(false, true)) {
			Future<?> futuro = futuroPrincipal;
			if (futuro != null) {
				futuro.cancel(true);
			}
		}
	}

	private void cerrarRecursos() {
		try {
			anunciar(EventoTracker.DETENIDO);
		} catch (Throwable ignorado) {
		}
		for (ConexionPeer conexion : conexiones) {
			conexion.cerrar();
		}
		conexiones.clear();
		ejecutorPeers.shutdownNow();
		coordinador.shutdownNow();
		motor.quitarSesion(this);
		if (!MonitorDePID.idioma.bittorrentEstadoError().equals(estado.get().estado())) {
			estado.set(new EstadoTorrent(id, nombre, tipo, MonitorDePID.idioma.bittorrentEstadoDetenido(), porcentaje(),
					0, descargadoRed.get(), subidoRed.get()));
		}
	}

	MetainfoTorrent metainfo() {
		return metainfo;
	}

	byte[] peerId() {
		return peerId;
	}

	String nombre() {
		return nombre;
	}

	String tipo() {
		return tipo;
	}

	boolean detenida() {
		return detenida.get();
	}
}
