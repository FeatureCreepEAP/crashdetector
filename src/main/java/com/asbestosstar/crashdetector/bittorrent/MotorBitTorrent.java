package com.asbestosstar.crashdetector.bittorrent;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Motor BitTorrent v1 puro Java 8.
 *
 * Comparte un único puerto entrante entre todas las sesiones y despacha cada
 * handshake por info-hash. No requiere bibliotecas Maven ni comandos del
 * sistema.
 */
public final class MotorBitTorrent {

	private static final MotorBitTorrent INSTANCIA = new MotorBitTorrent();

	private final Map<String, SesionTorrent> sesiones = Collections
			.synchronizedMap(new LinkedHashMap<String, SesionTorrent>());
	private final Map<String, SesionTorrent> sesionesPorHash = new ConcurrentHashMap<String, SesionTorrent>();
	private final byte[] peerId = UtilBitTorrent.crearPeerId();
	private final ExecutorService ejecutorEntrantes = Executors.newCachedThreadPool();
	private final AtomicBoolean cierreRegistrado = new AtomicBoolean(false);
	private volatile ServerSocket servidor;
	private volatile Thread hiloAceptador;

	private MotorBitTorrent() {
	}

	public static MotorBitTorrent obtenerInstancia() {
		return INSTANCIA;
	}

	public SesionTorrent descargarMagnet(String magnet, Path destino, boolean seguirCompartiendo) throws Exception {
		validarDestino(destino);
		asegurarServidor();
		EnlaceMagnet enlace = EnlaceMagnet.analizar(magnet)
				.conTrackersAdicionales(ConfiguracionBitTorrent.obtenerTrackers());
		DescargadorMetadatosMagnet.Resultado metadata = DescargadorMetadatosMagnet.obtener(enlace, peerId, puerto());
		String nombre = metadata.metainfo.nombre();
		return iniciar(nombre, MonitorDePID.idioma.bittorrentTipoDescarga(), metadata.metainfo, destino,
				seguirCompartiendo, metadata.pares);
	}

	public SesionTorrent abrirTorrent(Path torrent, Path destino, boolean seguirCompartiendo) throws Exception {
		validarDestino(destino);
		asegurarServidor();
		MetainfoTorrent metainfo = MetainfoTorrent.leer(torrent);
		return iniciar(metainfo.nombre(), MonitorDePID.idioma.bittorrentTipoDescarga(), metainfo, destino,
				seguirCompartiendo, Collections.<DireccionPeer>emptySet());
	}

	/**
	 * Inicia la siembra usando la carpeta que contiene el nombre raíz del torrent.
	 */
	public SesionTorrent sembrarTorrent(Path torrent, Path raizAlmacenamiento, String nombre) throws Exception {
		validarDestino(raizAlmacenamiento);
		asegurarServidor();
		MetainfoTorrent metainfo = MetainfoTorrent.leer(torrent);
		return iniciar(nombre == null || nombre.trim().isEmpty() ? metainfo.nombre() : nombre,
				MonitorDePID.idioma.bittorrentTipoSemilla(), metainfo, raizAlmacenamiento, true,
				Collections.<DireccionPeer>emptySet());
	}

	private SesionTorrent iniciar(String nombre, String tipo, MetainfoTorrent metainfo, Path raiz,
			boolean seguirCompartiendo, Set<DireccionPeer> paresIniciales) throws Exception {
		String hash = metainfo.infoHashHex();
		SesionTorrent existente = sesionesPorHash.get(hash);
		if (existente != null && !MonitorDePID.idioma.bittorrentEstadoDetenido().equals(existente.estado().estado())) {
			return existente;
		}
		SesionTorrent sesion = new SesionTorrent(nombre, tipo, metainfo, raiz, this, peerId.clone(), seguirCompartiendo,
				paresIniciales);
		sesiones.put(sesion.id(), sesion);
		sesion.iniciar();
		registrarCierre();
		return sesion;
	}

	public List<EstadoTorrent> estados() {
		List<EstadoTorrent> resultado = new ArrayList<EstadoTorrent>();
		synchronized (sesiones) {
			for (SesionTorrent sesion : sesiones.values()) {
				resultado.add(sesion.estado());
			}
		}
		return resultado;
	}

	public SesionTorrent obtenerSesion(String id) {
		return sesiones.get(id);
	}

	public void detener(String id) {
		SesionTorrent sesion = sesiones.get(id);
		if (sesion != null) {
			sesion.detener();
		}
	}

	public void detenerTodo() {
		synchronized (sesiones) {
			for (SesionTorrent sesion : sesiones.values()) {
				try {
					sesion.detener();
				} catch (Throwable ignorado) {
				}
			}
		}
		ServerSocket actual = servidor;
		servidor = null;
		if (actual != null) {
			try {
				actual.close();
			} catch (IOException ignorado) {
			}
		}
		ejecutorEntrantes.shutdownNow();
	}

	void registrarSesion(SesionTorrent sesion) {
		sesionesPorHash.put(sesion.metainfo().infoHashHex(), sesion);
	}

	void quitarSesion(SesionTorrent sesion) {
		sesionesPorHash.remove(sesion.metainfo().infoHashHex(), sesion);
	}

	int puerto() {
		ServerSocket actual = servidor;
		return actual == null ? ConfiguracionBitTorrent.obtenerPuerto() : actual.getLocalPort();
	}

	boolean esDireccionPropia(DireccionPeer peer) {
		if (peer == null || peer.puerto() != puerto()) {
			return false;
		}
		try {
			InetAddress direccion = InetAddress.getByName(peer.host());
			if (direccion.isAnyLocalAddress() || direccion.isLoopbackAddress()) {
				return true;
			}
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces != null && interfaces.hasMoreElements()) {
				Enumeration<InetAddress> direcciones = interfaces.nextElement().getInetAddresses();
				while (direcciones.hasMoreElements()) {
					if (direccion.equals(direcciones.nextElement())) {
						return true;
					}
				}
			}
		} catch (Throwable ignorado) {
		}
		return false;
	}

	private synchronized void asegurarServidor() throws IOException {
		if (servidor != null && !servidor.isClosed()) {
			return;
		}
		try {
			servidor = new ServerSocket(ConfiguracionBitTorrent.obtenerPuerto());
			servidor.setReuseAddress(false);
		} catch (IOException e) {
			throw new IOException(MonitorDePID.idioma.bittorrentErrorCrearComparticion(), e);
		}
		hiloAceptador = new Thread(new Runnable() {
			@Override
			public void run() {
				aceptarConexiones();
			}
		}, "BitTorrent-Aceptador");
		hiloAceptador.setDaemon(true);
		hiloAceptador.start();
	}

	private void aceptarConexiones() {
		while (servidor != null && !servidor.isClosed()) {
			try {
				final Socket socket = servidor.accept();
				ejecutorEntrantes.execute(new Runnable() {
					@Override
					public void run() {
						despachar(socket);
					}
				});
			} catch (IOException e) {
				if (servidor != null && !servidor.isClosed()) {
					CrashDetectorLogger.logException(e);
				}
			}
		}
	}

	private void despachar(Socket socket) {
		try {
			socket.setSoTimeout(ConfiguracionBitTorrent.obtenerTimeoutMilisegundos());
			DataInputStream entrada = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			DataOutputStream salida = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			HandshakeBitTorrent handshake = HandshakeBitTorrent.leer(entrada);
			SesionTorrent sesion = sesionesPorHash.get(UtilBitTorrent.hex(handshake.infoHash()));
			if (sesion == null) {
				socket.close();
				return;
			}
			sesion.aceptarEntrante(socket, entrada, salida, handshake);
		} catch (Throwable t) {
			try {
				socket.close();
			} catch (IOException ignorado) {
			}
		}
	}

	private void validarDestino(Path destino) throws Exception {
		if (destino == null) {
			throw new IllegalArgumentException(MonitorDePID.idioma.bittorrentDestinoNoValido());
		}
		Files.createDirectories(destino);
		if (!Files.isDirectory(destino) || !Files.isWritable(destino)) {
			throw new IllegalArgumentException(MonitorDePID.idioma.bittorrentDestinoNoValido());
		}
	}

	private void registrarCierre() {
		if (cierreRegistrado.compareAndSet(false, true)) {
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					detenerTodo();
				}
			}, "BitTorrent-Cierre"));
		}
	}
}
