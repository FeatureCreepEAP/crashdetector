package com.asbestosstar.crashdetector.bittorrent;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Announce HTTP/HTTPS compatible con respuestas compactas IPv4 e IPv6. */
final class ClienteTrackerHttp implements ClienteTracker {

	private static final int RESPUESTA_MAXIMA = 8 * 1024 * 1024;
	private final URI uri;

	ClienteTrackerHttp(URI uri) {
		this.uri = uri;
	}

	@Override
	public RespuestaTracker anunciar(SolicitudTracker solicitud) throws Exception {
		StringBuilder consulta = new StringBuilder();
		agregar(consulta, "info_hash", UtilBitTorrent.codificarParametroBinario(solicitud.infoHash), false);
		agregar(consulta, "peer_id", UtilBitTorrent.codificarParametroBinario(solicitud.peerId), false);
		agregar(consulta, "port", String.valueOf(solicitud.puerto), false);
		agregar(consulta, "uploaded", String.valueOf(solicitud.subido), false);
		agregar(consulta, "downloaded", String.valueOf(solicitud.descargado), false);
		agregar(consulta, "left", String.valueOf(solicitud.restante), false);
		agregar(consulta, "compact", "1", false);
		agregar(consulta, "no_peer_id", "1", false);
		agregar(consulta, "numwant", String.valueOf(solicitud.cantidadDeseada), false);
		if (solicitud.evento != EventoTracker.NINGUNO) {
			agregar(consulta, "event", solicitud.evento.textoHttp(), false);
		}

		String base = uri.toASCIIString();
		String separador = base.indexOf('?') >= 0 ? "&" : "?";
		URL url = new URL(base + separador + consulta.toString());
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		conexion.setConnectTimeout(ConfiguracionBitTorrent.obtenerTimeoutMilisegundos());
		conexion.setReadTimeout(ConfiguracionBitTorrent.obtenerTimeoutMilisegundos());
		conexion.setRequestMethod("GET");
		conexion.setRequestProperty("User-Agent", "CrashDetector-BitTorrent/1.0");
		conexion.setRequestProperty("Accept", "application/x-bittorrent, */*");
		conexion.setInstanceFollowRedirects(true);

		int codigo = conexion.getResponseCode();
		InputStream flujo = codigo >= 200 && codigo < 300 ? conexion.getInputStream() : conexion.getErrorStream();
		byte[] cuerpo = leerLimitado(flujo);
		conexion.disconnect();
		if (codigo < 200 || codigo >= 300) {
			throw new IOException("HTTP " + codigo);
		}
		return parsear(cuerpo);
	}

	@SuppressWarnings("unchecked")
	private RespuestaTracker parsear(byte[] cuerpo) throws Exception {
		Object objeto = Bencode.leer(cuerpo);
		if (!(objeto instanceof Map<?, ?>)) {
			throw new IOException();
		}
		Map<String, Object> mapa = (Map<String, Object>) objeto;
		String error = texto(mapa.get("failure reason"));
		if (error != null && !error.isEmpty()) {
			throw new IOException(error);
		}
		int intervalo = (int) numero(mapa.get("interval"), 1800L);
		int completos = (int) numero(mapa.get("complete"), -1L);
		int incompletos = (int) numero(mapa.get("incomplete"), -1L);
		List<DireccionPeer> pares = new ArrayList<DireccionPeer>();
		agregarPares(pares, mapa.get("peers"), false);
		agregarPares(pares, mapa.get("peers6"), true);
		return new RespuestaTracker(intervalo, completos, incompletos, pares);
	}

	@SuppressWarnings("unchecked")
	private void agregarPares(List<DireccionPeer> destino, Object valor, boolean ipv6) throws Exception {
		if (valor instanceof byte[]) {
			byte[] compactos = (byte[]) valor;
			int bloque = ipv6 ? 18 : 6;
			for (int i = 0; i + bloque <= compactos.length; i += bloque) {
				byte[] direccion = new byte[ipv6 ? 16 : 4];
				System.arraycopy(compactos, i, direccion, 0, direccion.length);
				int puerto = ((compactos[i + direccion.length] & 0xff) << 8)
						| (compactos[i + direccion.length + 1] & 0xff);
				if (puerto > 0) {
					destino.add(new DireccionPeer(InetAddress.getByAddress(direccion).getHostAddress(), puerto));
				}
			}
			return;
		}
		if (valor instanceof List<?>) {
			for (Object item : (List<?>) valor) {
				if (!(item instanceof Map<?, ?>)) {
					continue;
				}
				Map<String, Object> peer = (Map<String, Object>) item;
				String host = texto(peer.get("ip"));
				int puerto = (int) numero(peer.get("port"), 0L);
				if (host != null && puerto > 0 && puerto <= 65535) {
					destino.add(new DireccionPeer(host, puerto));
				}
			}
		}
	}

	private static void agregar(StringBuilder consulta, String clave, String valor, boolean codificarValor) {
		if (consulta.length() > 0) {
			consulta.append('&');
		}
		consulta.append(clave).append('=');
		consulta.append(codificarValor ? UtilBitTorrent.codificarParametroTexto(valor) : valor);
	}

	private static byte[] leerLimitado(InputStream entrada) throws IOException {
		if (entrada == null) {
			return new byte[0];
		}
		try (InputStream in = new BufferedInputStream(entrada);
				ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[8192];
			int leidos;
			int total = 0;
			while ((leidos = in.read(buffer)) >= 0) {
				total += leidos;
				if (total > RESPUESTA_MAXIMA) {
					throw new IOException();
				}
				salida.write(buffer, 0, leidos);
			}
			return salida.toByteArray();
		}
	}

	private static long numero(Object valor, long porDefecto) {
		return valor instanceof Number ? ((Number) valor).longValue() : porDefecto;
	}

	private static String texto(Object valor) {
		return valor instanceof byte[] ? new String((byte[]) valor, StandardCharsets.UTF_8) : null;
	}
}
