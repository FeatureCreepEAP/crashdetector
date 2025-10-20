package com.asbestosstar.crashdetector.api_sito_registro;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * API para publicar registros en el servicio PHP de CrashDetector.
 *
 * Fuente del endpoint:
 * https://pagure.io/CrashDetectorMC/blob/main/f/paste/endpoint.php
 *
 * - Envía el log comprimido en GZIP por POST a:
 * https://asbestosstar.egoism.jp/crash_detector/paste/endpoint.php?action=save_log
 * - Respuesta JSON con {"link":"https://..."} que se devuelve como enlace
 * público.
 */
public class CrashDetectorPasteAPI implements APIdeSitioDeRegistro {

	private static final String UA = "CrashDetector/1.x (+crashdetector-php)";
	private static final int TIMEOUT = 30000;

	@Override
	public String nombre() {
		return "CrashDetectorPaste";
	}

	@Override
	public List<String> sitiosPorDefecto() {
		// Endpoint recomendado con la acción de guardado
		List<String> sitios = new ArrayList<>();
		sitios.add("https://asbestosstar.egoism.jp/crash_detector/paste/endpoint.php?action=save_log");
		return sitios;
	}

	@Override
	public String publicarRegistro(Consola registro) {
		try {
			// Contenido del log (UTF-8)
			String contenido = registro.obtainerContenidoParaPublicar();
			byte[] datos = contenido.getBytes(StandardCharsets.UTF_8);

			// Normalizar endpoint según Config
			String endpoint = normalizarEndpoint(APIdeSitioDeRegistro.sitioDeConfig());
			URL url = new URL(endpoint);

			HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
			con.setRequestMethod("POST");
			con.setConnectTimeout(TIMEOUT);
			con.setReadTimeout(TIMEOUT);
			con.setRequestProperty("User-Agent", UA);
			con.setRequestProperty("Content-Type", "application/octet-stream");
			con.setRequestProperty("Content-Encoding", "gzip");
			con.setDoOutput(true);

			// Enviar cuerpo comprimido en GZIP
			try (OutputStream os = con.getOutputStream()) {
				os.write(comprimirGZIP(datos));
			}

			int code = con.getResponseCode();
			if (code != HttpURLConnection.HTTP_OK) {
				String err = leer(con.getErrorStream());
				CrashDetectorLogger.log("CrashDetectorPaste HTTP " + code + (err == null ? "" : (": " + err)));
				return null;
			}

			// Leer JSON y extraer "link"
			String body = leer(con.getInputStream());
			String link = extraerLinkDeJson(body);
			if (link == null || link.isEmpty()) {
				CrashDetectorLogger.log("CrashDetectorPaste: no se pudo extraer el enlace.");
				return null;
			}

			// Algunos servidores devuelven barras escapadas
			return link.replace("\\/", "/");
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
			return null;
		}
	}

	/*
	 * ========================= Utilidades privadas =========================
	 */

	/** Garantiza que el endpoint incluya la acción de guardado. */
	private String normalizarEndpoint(String s) {
		if (s == null || s.isEmpty()) {
			return "https://asbestosstar.egoism.jp/crash_detector/paste/endpoint.php?action=save_log";
		}
		// Si el usuario pegó la ruta sin acción, añadirla.
		String basePhp = "endpoint.php";
		if (s.endsWith("?"))
			s = s.substring(0, s.length() - 1);
		if (s.contains(basePhp) && !s.contains("action=save_log")) {
			if (s.contains("?")) {
				s = s + "&action=save_log";
			} else {
				s = s + "?action=save_log";
			}
		}
		return s;
	}

	/** Comprime un arreglo de bytes en GZIP. */
	private static byte[] comprimirGZIP(byte[] datos) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (GZIPOutputStream gz = new GZIPOutputStream(baos)) {
			gz.write(datos);
		}
		return baos.toByteArray();
	}

	/** Lee un InputStream completo a String (UTF-8). */
	private static String leer(InputStream is) {
		if (is == null)
			return null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			StringBuilder sb = new StringBuilder();
			String l;
			while ((l = br.readLine()) != null) {
				sb.append(l);
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/** Extrae el valor de "link" de un JSON simple: {"link":"..."} */
	private static String extraerLinkDeJson(String json) {
		if (json == null)
			return null;
		String key = "\"link\"";
		int i = json.indexOf(key);
		if (i < 0)
			return null;
		int colon = json.indexOf(':', i);
		if (colon < 0)
			return null;

		int pos = colon + 1;
		// Saltar espacios
		while (pos < json.length() && Character.isWhitespace(json.charAt(pos)))
			pos++;

		if (pos >= json.length() || json.charAt(pos) != '"')
			return null;
		int fin = json.indexOf('"', pos + 1);
		if (fin < 0)
			return null;

		return json.substring(pos + 1, fin);
	}
}
