package com.asbestosstar.crashdetector.bittorrent;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.BitSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/** Una conexión peer-wire que descarga piezas y sirve las ya verificadas. */
final class ConexionPeer implements Runnable {

	private static final int BLOQUE = 16 * 1024;
	private static final int ID_LOCAL_UT_METADATA = 1;

	private final SesionTorrent sesion;
	private final DireccionPeer direccion;
	private final boolean entrante;
	private Socket socket;
	private DataInputStream entrada;
	private DataOutputStream salida;
	private HandshakeBitTorrent handshakeInicial;
	private final AtomicBoolean cerrada = new AtomicBoolean(false);
	private final Object bloqueoSalida = new Object();
	private final BitSet piezasRemotas = new BitSet();
	private volatile boolean remotoChoking = true;
	private volatile boolean interesadoLocal;
	private volatile int idRemotoUtMetadata = -1;
	private PiezaEnDescarga piezaActual;

	private ConexionPeer(SesionTorrent sesion, DireccionPeer direccion, boolean entrante) {
		this.sesion = sesion;
		this.direccion = direccion;
		this.entrante = entrante;
	}

	static ConexionPeer saliente(SesionTorrent sesion, DireccionPeer direccion) {
		return new ConexionPeer(sesion, direccion, false);
	}

	static ConexionPeer entrante(SesionTorrent sesion, Socket socket, DataInputStream entrada, DataOutputStream salida,
			HandshakeBitTorrent handshake) {
		DireccionPeer direccion = new DireccionPeer(socket.getInetAddress().getHostAddress(), socket.getPort());
		ConexionPeer conexion = new ConexionPeer(sesion, direccion, true);
		conexion.socket = socket;
		conexion.entrada = entrada;
		conexion.salida = salida;
		conexion.handshakeInicial = handshake;
		return conexion;
	}

	@Override
	public void run() {
		try {
			prepararConexion();
			sesion.registrarConexion(this);
			enviarBitfield();
			if (handshakeInicial.soportaExtensiones()) {
				enviarHandshakeExtensiones();
			}
			if (!sesion.estaCompleto()) {
				enviarSimple(MensajePeer.INTERESTED);
				interesadoLocal = true;
			}

			while (!cerrada.get() && !sesion.detenida()) {
				try {
					MensajePeer mensaje = MensajePeer.leer(entrada);
					procesar(mensaje);
				} catch (SocketTimeoutException e) {
					enviarKeepAlive();
				}
			}
		} catch (Throwable t) {
			if (!cerrada.get() && !sesion.detenida()) {
				CrashDetectorLogger.logException(t);
			}
		} finally {
			cerrar();
		}
	}

	private void prepararConexion() throws Exception {
		if (!entrante) {
			socket = new Socket();
			socket.connect(direccion.socketAddress(), ConfiguracionBitTorrent.obtenerTimeoutMilisegundos());
			socket.setSoTimeout(ConfiguracionBitTorrent.obtenerTimeoutMilisegundos() * 3);
			entrada = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			salida = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			HandshakeBitTorrent.escribir(salida, sesion.metainfo().infoHash(), sesion.peerId(), true);
			handshakeInicial = HandshakeBitTorrent.leer(entrada);
		} else {
			socket.setSoTimeout(ConfiguracionBitTorrent.obtenerTimeoutMilisegundos() * 3);
			HandshakeBitTorrent.escribir(salida, sesion.metainfo().infoHash(), sesion.peerId(), true);
		}
		if (!UtilBitTorrent.igualesConstante(handshakeInicial.infoHash(), sesion.metainfo().infoHash())
				|| UtilBitTorrent.igualesConstante(handshakeInicial.peerId(), sesion.peerId())) {
			throw new IOException();
		}
	}

	private void procesar(MensajePeer mensaje) throws Exception {
		switch (mensaje.id) {
		case MensajePeer.KEEP_ALIVE:
			return;
		case MensajePeer.CHOKE:
			remotoChoking = true;
			abandonarPieza();
			return;
		case MensajePeer.UNCHOKE:
			remotoChoking = false;
			solicitarMas();
			return;
		case MensajePeer.INTERESTED:
			enviarSimple(MensajePeer.UNCHOKE);
			return;
		case MensajePeer.NOT_INTERESTED:
			return;
		case MensajePeer.HAVE:
			if (mensaje.carga.length == 4) {
				int indice = ByteBuffer.wrap(mensaje.carga).order(ByteOrder.BIG_ENDIAN).getInt();
				if (indice >= 0 && indice < sesion.metainfo().cantidadPiezas()) {
					piezasRemotas.set(indice);
					solicitarMas();
				}
			}
			return;
		case MensajePeer.BITFIELD:
			piezasRemotas.clear();
			piezasRemotas.or(BitfieldTorrent.decodificar(mensaje.carga, sesion.metainfo().cantidadPiezas()));
			solicitarMas();
			return;
		case MensajePeer.REQUEST:
			servirSolicitud(mensaje.carga);
			return;
		case MensajePeer.PIECE:
			recibirBloque(mensaje.carga);
			return;
		case MensajePeer.CANCEL:
			return;
		case MensajePeer.EXTENDED:
			procesarExtension(mensaje.carga);
			return;
		default:
			return;
		}
	}

	private void solicitarMas() throws IOException {
		if (remotoChoking || sesion.estaCompleto() || cerrada.get()) {
			return;
		}
		if (piezaActual == null) {
			int indice = sesion.reservarPieza(piezasRemotas);
			if (indice < 0) {
				if (interesadoLocal) {
					enviarSimple(MensajePeer.NOT_INTERESTED);
					interesadoLocal = false;
				}
				return;
			}
			piezaActual = new PiezaEnDescarga(indice, sesion.metainfo().longitudPieza(indice));
			if (!interesadoLocal) {
				enviarSimple(MensajePeer.INTERESTED);
				interesadoLocal = true;
			}
		}

		int limite = ConfiguracionBitTorrent.obtenerSolicitudesPorPar();
		while (piezaActual != null && piezaActual.pendientes < limite
				&& piezaActual.siguienteBloque < piezaActual.cantidadBloques()) {
			int bloque = piezaActual.siguienteBloque++;
			int inicio = bloque * BLOQUE;
			int longitud = Math.min(BLOQUE, piezaActual.datos.length - inicio);
			ByteBuffer carga = ByteBuffer.allocate(12).order(ByteOrder.BIG_ENDIAN);
			carga.putInt(piezaActual.indice).putInt(inicio).putInt(longitud);
			enviar(MensajePeer.REQUEST, carga.array());
			piezaActual.solicitados.set(bloque);
			piezaActual.pendientes++;
		}
	}

	private void recibirBloque(byte[] carga) throws IOException {
		if (carga.length < 8 || piezaActual == null) {
			return;
		}
		ByteBuffer entrada = ByteBuffer.wrap(carga).order(ByteOrder.BIG_ENDIAN);
		int indice = entrada.getInt();
		int inicio = entrada.getInt();
		int longitud = carga.length - 8;
		if (indice != piezaActual.indice || inicio < 0 || longitud <= 0 || inicio + longitud < inicio
				|| inicio + longitud > piezaActual.datos.length || inicio % BLOQUE != 0) {
			throw new IOException();
		}
		int bloque = inicio / BLOQUE;
		if (bloque >= piezaActual.cantidadBloques() || piezaActual.recibidos.get(bloque)) {
			return;
		}
		System.arraycopy(carga, 8, piezaActual.datos, inicio, longitud);
		piezaActual.recibidos.set(bloque);
		piezaActual.pendientes = Math.max(0, piezaActual.pendientes - 1);
		sesion.sumarDescargado(longitud);

		if (piezaActual.recibidos.cardinality() == piezaActual.cantidadBloques()) {
			PiezaEnDescarga terminada = piezaActual;
			piezaActual = null;
			if (!sesion.guardarPieza(terminada.indice, terminada.datos)) {
				sesion.liberarPieza(terminada.indice);
			}
		}
		solicitarMas();
	}

	private void servirSolicitud(byte[] carga) throws IOException {
		if (carga.length != 12) {
			return;
		}
		ByteBuffer solicitud = ByteBuffer.wrap(carga).order(ByteOrder.BIG_ENDIAN);
		int pieza = solicitud.getInt();
		int inicio = solicitud.getInt();
		int longitud = solicitud.getInt();
		if (pieza < 0 || pieza >= sesion.metainfo().cantidadPiezas() || inicio < 0 || longitud <= 0 || longitud > BLOQUE
				|| inicio + longitud > sesion.metainfo().longitudPieza(pieza) || !sesion.tienePieza(pieza)) {
			return;
		}
		byte[] bloque = sesion.leerBloque(pieza, inicio, longitud);
		ByteBuffer respuesta = ByteBuffer.allocate(8 + bloque.length).order(ByteOrder.BIG_ENDIAN);
		respuesta.putInt(pieza).putInt(inicio).put(bloque);
		enviar(MensajePeer.PIECE, respuesta.array());
		sesion.sumarSubido(bloque.length);
	}

	@SuppressWarnings("unchecked")
	private void procesarExtension(byte[] carga) throws Exception {
		if (carga.length < 2) {
			return;
		}
		int id = carga[0] & 0xff;
		if (id == 0) {
			byte[] cuerpo = new byte[carga.length - 1];
			System.arraycopy(carga, 1, cuerpo, 0, cuerpo.length);
			Object objeto = Bencode.leer(cuerpo);
			if (objeto instanceof Map<?, ?>) {
				Object m = ((Map<String, Object>) objeto).get("m");
				if (m instanceof Map<?, ?>) {
					Object valor = ((Map<String, Object>) m).get("ut_metadata");
					if (valor instanceof Number) {
						idRemotoUtMetadata = ((Number) valor).intValue();
					}
				}
			}
			return;
		}
		if (id != ID_LOCAL_UT_METADATA) {
			return;
		}
		Bencode.Resultado cabecera = Bencode.leerUno(carga, 1);
		if (!(cabecera.valor() instanceof Map<?, ?>)) {
			return;
		}
		Map<String, Object> mapa = (Map<String, Object>) cabecera.valor();
		int tipo = mapa.get("msg_type") instanceof Number ? ((Number) mapa.get("msg_type")).intValue() : -1;
		int pieza = mapa.get("piece") instanceof Number ? ((Number) mapa.get("piece")).intValue() : -1;
		if (tipo == 0) {
			servirMetadata(pieza);
		}
	}

	private void enviarHandshakeExtensiones() throws IOException {
		Map<String, Object> extensiones = new LinkedHashMap<String, Object>();
		extensiones.put("ut_metadata", Long.valueOf(ID_LOCAL_UT_METADATA));
		Map<String, Object> mapa = new LinkedHashMap<String, Object>();
		mapa.put("m", extensiones);
		mapa.put("metadata_size", Long.valueOf(sesion.metainfo().infoBytes().length));
		mapa.put("p", Long.valueOf(ConfiguracionBitTorrent.obtenerPuerto()));
		mapa.put("v", "CrashDetector BitTorrent 1.0");
		byte[] bencode = Bencode.codificar(mapa);
		byte[] carga = new byte[bencode.length + 1];
		System.arraycopy(bencode, 0, carga, 1, bencode.length);
		enviar(MensajePeer.EXTENDED, carga);
	}

	private void servirMetadata(int pieza) throws IOException {
		if (idRemotoUtMetadata <= 0 || pieza < 0) {
			return;
		}
		byte[] info = sesion.metainfo().infoBytes();
		int inicio = pieza * BLOQUE;
		if (inicio >= info.length) {
			enviarRespuestaMetadata(2, pieza, null, info.length);
			return;
		}
		int largo = Math.min(BLOQUE, info.length - inicio);
		byte[] datos = new byte[largo];
		System.arraycopy(info, inicio, datos, 0, largo);
		enviarRespuestaMetadata(1, pieza, datos, info.length);
	}

	private void enviarRespuestaMetadata(int tipo, int pieza, byte[] datos, int total) throws IOException {
		Map<String, Object> cabecera = new LinkedHashMap<String, Object>();
		cabecera.put("msg_type", Long.valueOf(tipo));
		cabecera.put("piece", Long.valueOf(pieza));
		if (tipo == 1) {
			cabecera.put("total_size", Long.valueOf(total));
		}
		byte[] bencode = Bencode.codificar(cabecera);
		ByteArrayOutputStream carga = new ByteArrayOutputStream();
		carga.write((byte) idRemotoUtMetadata);
		carga.write(bencode);
		if (datos != null) {
			carga.write(datos);
		}
		enviar(MensajePeer.EXTENDED, carga.toByteArray());
	}

	private void enviarBitfield() throws IOException {
		enviar(MensajePeer.BITFIELD,
				BitfieldTorrent.codificar(sesion.copiarPiezasCompletas(), sesion.metainfo().cantidadPiezas()));
	}

	void enviarHave(int indice) {
		if (cerrada.get()) {
			return;
		}
		try {
			ByteBuffer carga = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(indice);
			enviar(MensajePeer.HAVE, carga.array());
		} catch (Throwable t) {
			cerrar();
		}
	}

	private void enviarSimple(int id) throws IOException {
		enviar(id, new byte[0]);
	}

	private void enviar(int id, byte[] carga) throws IOException {
		synchronized (bloqueoSalida) {
			MensajePeer.escribir(salida, id, carga);
		}
	}

	private void enviarKeepAlive() throws IOException {
		synchronized (bloqueoSalida) {
			MensajePeer.keepAlive(salida);
		}
	}

	private void abandonarPieza() {
		PiezaEnDescarga actual = piezaActual;
		piezaActual = null;
		if (actual != null) {
			sesion.liberarPieza(actual.indice);
		}
	}

	void cerrar() {
		if (cerrada.compareAndSet(false, true)) {
			abandonarPieza();
			sesion.quitarConexion(this);
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException ignorado) {
			}
		}
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	@Override
	public boolean equals(Object objeto) {
		return this == objeto;
	}

	private static final class PiezaEnDescarga {
		final int indice;
		final byte[] datos;
		final BitSet solicitados;
		final BitSet recibidos;
		int siguienteBloque;
		int pendientes;

		PiezaEnDescarga(int indice, int longitud) {
			this.indice = indice;
			this.datos = new byte[longitud];
			this.solicitados = new BitSet(cantidadBloques());
			this.recibidos = new BitSet(cantidadBloques());
		}

		int cantidadBloques() {
			return (datos.length + BLOQUE - 1) / BLOQUE;
		}
	}
}
