package com.asbestosstar.crashdetector.bittorrent;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;

/** Descarga el diccionario info de un magnet mediante BEP 10 y BEP 9. */
final class DescargadorMetadatosMagnet {

	private static final int PIEZA_METADATA = 16 * 1024;
	private static final int METADATA_MAXIMA = 16 * 1024 * 1024;
	private static final int ID_LOCAL_UT_METADATA = 1;

	private DescargadorMetadatosMagnet() {
	}

	static Resultado obtener(EnlaceMagnet magnet, byte[] peerId, int puerto) throws Exception {
		Set<DireccionPeer> pares = new LinkedHashSet<DireccionPeer>(magnet.paresExplicitos());
		List<String> trackers = new ArrayList<String>(magnet.trackers());
		for (String tracker : ConfiguracionBitTorrent.obtenerTrackers()) {
			if (!trackers.contains(tracker)) {
				trackers.add(tracker);
			}
		}

		SolicitudTracker solicitud = new SolicitudTracker(magnet.infoHash(), peerId, puerto, 0L, 1L, 0L,
				EventoTracker.INICIADO, ConfiguracionBitTorrent.obtenerMaximoPares());
		for (String tracker : trackers) {
			try {
				pares.addAll(FabricaTrackers.crear(tracker).anunciar(solicitud).pares());
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}

		if (pares.isEmpty()) {
			throw new IOException(MonitorDePID.idioma.bittorrentSinTrackersNiDht());
		}

		List<DireccionPeer> lista = new ArrayList<DireccionPeer>(pares);
		Collections.shuffle(lista);
		Throwable ultimo = null;
		int limite = Math.min(lista.size(), ConfiguracionBitTorrent.obtenerMaximoPares());
		for (int i = 0; i < limite; i++) {
			try {
				byte[] info = obtenerDePeer(lista.get(i), magnet.infoHash(), peerId, puerto);
				MetainfoTorrent metainfo = MetainfoTorrent.desdeInfoBytes(info, trackers);
				if (!UtilBitTorrent.igualesConstante(metainfo.infoHash(), magnet.infoHash())) {
					throw new IOException();
				}
				return new Resultado(metainfo, pares);
			} catch (Throwable t) {
				ultimo = t;
			}
		}
		throw new IOException(MonitorDePID.idioma.bittorrentMagnetNoValido(), ultimo);
	}

	@SuppressWarnings("unchecked")
	private static byte[] obtenerDePeer(DireccionPeer peer, byte[] infoHash, byte[] peerId, int puerto)
			throws Exception {
		Socket socket = new Socket();
		socket.connect(peer.socketAddress(), ConfiguracionBitTorrent.obtenerTimeoutMilisegundos());
		socket.setSoTimeout(ConfiguracionBitTorrent.obtenerTimeoutMilisegundos());
		try (Socket cierre = socket;
				DataInputStream entrada = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				DataOutputStream salida = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {

			HandshakeBitTorrent.escribir(salida, infoHash, peerId, true);
			HandshakeBitTorrent remoto = HandshakeBitTorrent.leer(entrada);
			if (!UtilBitTorrent.igualesConstante(infoHash, remoto.infoHash()) || !remoto.soportaExtensiones()) {
				throw new IOException();
			}

			enviarHandshakeExtensiones(salida, puerto);
			int idRemoto = -1;
			int tamanoMetadata = -1;
			long limiteHandshake = System.currentTimeMillis() + ConfiguracionBitTorrent.obtenerTimeoutMilisegundos();

			while (System.currentTimeMillis() < limiteHandshake && idRemoto <= 0) {
				MensajePeer mensaje = MensajePeer.leer(entrada);
				if (mensaje.id != MensajePeer.EXTENDED || mensaje.carga.length < 2 || (mensaje.carga[0] & 0xff) != 0) {
					continue;
				}
				byte[] cuerpo = new byte[mensaje.carga.length - 1];
				System.arraycopy(mensaje.carga, 1, cuerpo, 0, cuerpo.length);
				Object objeto = Bencode.leer(cuerpo);
				if (!(objeto instanceof Map<?, ?>)) {
					continue;
				}
				Map<String, Object> mapa = (Map<String, Object>) objeto;
				Object m = mapa.get("m");
				if (m instanceof Map<?, ?>) {
					Object id = ((Map<String, Object>) m).get("ut_metadata");
					if (id instanceof Number) {
						idRemoto = ((Number) id).intValue();
					}
				}
				Object tamano = mapa.get("metadata_size");
				if (tamano instanceof Number) {
					tamanoMetadata = ((Number) tamano).intValue();
				}
			}

			if (idRemoto <= 0 || tamanoMetadata <= 0 || tamanoMetadata > METADATA_MAXIMA) {
				throw new IOException();
			}

			int piezas = (tamanoMetadata + PIEZA_METADATA - 1) / PIEZA_METADATA;
			byte[][] recibidas = new byte[piezas][];
			for (int i = 0; i < piezas; i++) {
				enviarSolicitudMetadata(salida, idRemoto, i);
			}

			int pendientes = piezas;
			while (pendientes > 0) {
				MensajePeer mensaje = MensajePeer.leer(entrada);
				if (mensaje.id != MensajePeer.EXTENDED || mensaje.carga.length < 2
						|| (mensaje.carga[0] & 0xff) != ID_LOCAL_UT_METADATA) {
					continue;
				}
				Bencode.Resultado cabecera = Bencode.leerUno(mensaje.carga, 1);
				if (!(cabecera.valor() instanceof Map<?, ?>)) {
					continue;
				}
				Map<String, Object> mapa = (Map<String, Object>) cabecera.valor();
				int tipo = entero(mapa.get("msg_type"), -1);
				int pieza = entero(mapa.get("piece"), -1);
				if (tipo == 2) {
					throw new IOException();
				}
				if (tipo != 1 || pieza < 0 || pieza >= piezas || recibidas[pieza] != null) {
					continue;
				}
				int inicioDatos = cabecera.siguiente();
				int esperado = Math.min(PIEZA_METADATA, tamanoMetadata - pieza * PIEZA_METADATA);
				int disponible = mensaje.carga.length - inicioDatos;
				if (disponible != esperado) {
					throw new IOException();
				}
				byte[] datos = new byte[esperado];
				System.arraycopy(mensaje.carga, inicioDatos, datos, 0, esperado);
				recibidas[pieza] = datos;
				pendientes--;
			}

			ByteArrayOutputStream info = new ByteArrayOutputStream(tamanoMetadata);
			for (byte[] pieza : recibidas) {
				info.write(pieza);
			}
			byte[] resultado = info.toByteArray();
			byte[] hash = MessageDigest.getInstance("SHA-1").digest(resultado);
			if (!UtilBitTorrent.igualesConstante(hash, infoHash)) {
				throw new IOException();
			}
			return resultado;
		}
	}

	private static void enviarHandshakeExtensiones(DataOutputStream salida, int puerto) throws IOException {
		Map<String, Object> extensiones = new LinkedHashMap<String, Object>();
		extensiones.put("ut_metadata", Long.valueOf(ID_LOCAL_UT_METADATA));
		Map<String, Object> mensaje = new LinkedHashMap<String, Object>();
		mensaje.put("m", extensiones);
		mensaje.put("p", Long.valueOf(puerto));
		mensaje.put("v", "CrashDetector BitTorrent 1.0");
		byte[] bencode = Bencode.codificar(mensaje);
		byte[] carga = new byte[bencode.length + 1];
		System.arraycopy(bencode, 0, carga, 1, bencode.length);
		MensajePeer.escribir(salida, MensajePeer.EXTENDED, carga);
	}

	private static void enviarSolicitudMetadata(DataOutputStream salida, int idRemoto, int pieza) throws IOException {
		Map<String, Object> solicitud = new LinkedHashMap<String, Object>();
		solicitud.put("msg_type", Long.valueOf(0));
		solicitud.put("piece", Long.valueOf(pieza));
		byte[] bencode = Bencode.codificar(solicitud);
		byte[] carga = new byte[bencode.length + 1];
		carga[0] = (byte) idRemoto;
		System.arraycopy(bencode, 0, carga, 1, bencode.length);
		MensajePeer.escribir(salida, MensajePeer.EXTENDED, carga);
	}

	private static int entero(Object valor, int porDefecto) {
		return valor instanceof Number ? ((Number) valor).intValue() : porDefecto;
	}

	static final class Resultado {
		final MetainfoTorrent metainfo;
		final Set<DireccionPeer> pares;

		Resultado(MetainfoTorrent metainfo, Set<DireccionPeer> pares) {
			this.metainfo = metainfo;
			this.pares = pares;
		}
	}
}
