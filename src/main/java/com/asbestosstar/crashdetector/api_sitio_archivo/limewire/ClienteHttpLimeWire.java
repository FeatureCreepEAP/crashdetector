package com.asbestosstar.crashdetector.api_sitio_archivo.limewire;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import com.asbestosstar.crashdetector.Statics;

/** Cliente HTTPS mínimo para el flujo web de LimeWire. */
public final class ClienteHttpLimeWire {

	public static final String API_BASE = "https://api.limewire.com";
	private static final int TIMEOUT_CONEXION_MS = 30000;
	private static final int TIMEOUT_LECTURA_MS = 120000;
	private static final String USER_AGENT = Statics.nombre_cd.obtener() + "-LimeWire/1.0";

	private final CredencialesLimeWire credenciales;

	public ClienteHttpLimeWire(CredencialesLimeWire credenciales) {
		if (credenciales == null) {
			throw new IllegalArgumentException("credenciales no puede ser null");
		}
		this.credenciales = credenciales;
	}

	public Respuesta getApi(String ruta) throws IOException {
		return solicitarApi("GET", ruta, null, null);
	}

	public Respuesta postApiVacio(String ruta) throws IOException {
		return solicitarApi("POST", ruta, "application/json", new byte[0]);
	}

	public Respuesta postApiJson(String ruta, String json) throws IOException {
		byte[] cuerpo = json == null ? new byte[0] : json.getBytes(StandardCharsets.UTF_8);
		return solicitarApi("POST", ruta, "application/json", cuerpo);
	}

	public Respuesta deleteApi(String ruta) throws IOException {
		return solicitarApi("DELETE", ruta, null, null);
	}

	public Respuesta solicitarApi(String metodo, String ruta, String contentType, byte[] cuerpo) throws IOException {
		String url = ruta.startsWith("http://") || ruta.startsWith("https://") ? ruta : API_BASE + ruta;
		HttpsURLConnection conexion = abrir(url, metodo);
		conexion.setRequestProperty("Accept", "application/json, text/plain, */*");
		conexion.setRequestProperty("Accept-Encoding", "gzip");
		conexion.setRequestProperty("Origin", "https://limewire.com");
		conexion.setRequestProperty("Referer", "https://limewire.com/");
		conexion.setRequestProperty("x-csrf-token", credenciales.tokenCsrf());
		conexion.setRequestProperty("Cookie", credenciales.cookie());
		conexion.setRequestProperty("DNT", "1");
		conexion.setRequestProperty("Sec-GPC", "1");
		if (contentType != null) {
			conexion.setRequestProperty("Content-Type", contentType);
		}
		if (cuerpo != null) {
			conexion.setDoOutput(true);
			conexion.setFixedLengthStreamingMode(cuerpo.length);
			try (OutputStream out = conexion.getOutputStream()) {
				out.write(cuerpo);
			}
		}
		return leer(conexion);
	}

	/**
	 * Sube una parte directamente al URL prefirmado. No propaga cookies ni CSRF al
	 * proveedor de almacenamiento.
	 */
	public Respuesta putPrefirmado(String url, byte[] cuerpo) throws IOException {
		HttpsURLConnection conexion = abrir(url, "PUT");
		conexion.setRequestProperty("Content-Type", "application/octet-stream");
		conexion.setRequestProperty("Accept", "*/*");
		conexion.setDoOutput(true);
		conexion.setFixedLengthStreamingMode(cuerpo.length);
		try (OutputStream out = conexion.getOutputStream()) {
			out.write(cuerpo);
		}
		return leer(conexion);
	}

	private HttpsURLConnection abrir(String url, String metodo) throws IOException {
		HttpURLConnection base = (HttpURLConnection) new URL(url).openConnection();
		if (!(base instanceof HttpsURLConnection)) {
			throw new IOException("LimeWire solamente se admite mediante HTTPS");
		}
		HttpsURLConnection conexion = (HttpsURLConnection) base;
		conexion.setConnectTimeout(TIMEOUT_CONEXION_MS);
		conexion.setReadTimeout(TIMEOUT_LECTURA_MS);
		conexion.setUseCaches(false);
		conexion.setInstanceFollowRedirects(true);
		conexion.setRequestMethod(metodo);
		conexion.setRequestProperty("User-Agent", USER_AGENT);
		conexion.setRequestProperty("Connection", "close");
		return conexion;
	}

	private Respuesta leer(HttpURLConnection conexion) throws IOException {
		int codigo = conexion.getResponseCode();
		InputStream raw = codigo >= 400 ? conexion.getErrorStream() : conexion.getInputStream();
		byte[] cuerpo = new byte[0];
		if (raw != null) {
			String encoding = conexion.getHeaderField("Content-Encoding");
			InputStream in = raw;
			if (encoding != null && encoding.toLowerCase(java.util.Locale.ROOT).contains("gzip")) {
				in = new GZIPInputStream(raw);
			}
			try (InputStream cierre = in; ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				byte[] buffer = new byte[8192];
				int leidos;
				while ((leidos = cierre.read(buffer)) >= 0) {
					if (leidos > 0) {
						out.write(buffer, 0, leidos);
					}
				}
				cuerpo = out.toByteArray();
			}
		}
		return new Respuesta(codigo, new String(cuerpo, StandardCharsets.UTF_8), conexion.getHeaderFields());
	}

	public static final class Respuesta {
		private final int codigo;
		private final String cuerpo;
		private final Map<String, List<String>> cabeceras;

		Respuesta(int codigo, String cuerpo, Map<String, List<String>> cabeceras) {
			this.codigo = codigo;
			this.cuerpo = cuerpo == null ? "" : cuerpo;
			this.cabeceras = cabeceras;
		}

		public int codigo() {
			return codigo;
		}

		public String cuerpo() {
			return cuerpo;
		}

		public boolean exito() {
			return codigo >= 200 && codigo < 300;
		}

		public String cabecera(String nombre) {
			if (cabeceras == null || nombre == null) {
				return null;
			}
			for (Map.Entry<String, List<String>> e : cabeceras.entrySet()) {
				if (e.getKey() != null && e.getKey().equalsIgnoreCase(nombre) && e.getValue() != null
						&& !e.getValue().isEmpty()) {
					return e.getValue().get(0);
				}
			}
			return null;
		}

		public void exigirExito(String operacion) throws IOException {
			if (!exito()) {
				throw new IOException(operacion + " falló: HTTP " + codigo + " -> " + limitar(cuerpo, 1200));
			}
		}

		private static String limitar(String texto, int maximo) {
			if (texto == null || texto.length() <= maximo) {
				return texto;
			}
			return texto.substring(0, maximo) + "...";
		}
	}
}
