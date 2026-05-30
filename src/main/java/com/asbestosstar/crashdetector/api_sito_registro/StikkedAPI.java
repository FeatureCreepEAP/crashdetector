package com.asbestosstar.crashdetector.api_sito_registro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.mapas.BiMap;

public class StikkedAPI implements APIdeSitioDeRegistro {

	/** Límite duro del endpoint Stikked en bytes (cuerpo x-www-form-urlencoded). */
	private static final int MAX_SIZE_BYTES = 16 * 1024 * 1024; // 16MB
	private static final int TIMEOUT_MS = 30000;
	private static final String UA = Statics.nombre_cd.obtener() + "/1.x (+stikked)";

	@Override
	public String nombre() {
		return "stikked";
	}

	@Override
	public List<String> sitiosPorDefecto() {
		List<String> sitios = new ArrayList<>();
		sitios.add("https://paste.mikumikudance.jp/api/create");
		sitios.add("https://paste.mikumikudance.jp/en/api/create");
		// sitios.add("https://paste.atlauncher.com/api/create");
		return sitios;
	}

	/* =================== Publicación básica =================== */

	@Override
	public String publicarRegistro(Consola registro) throws DemasiadoGrande, ErrorConPublicar {
		String texto = (registro.obtainerContenidoParaPublicar() == null) ? ""
				: registro.obtainerContenidoParaPublicar().trim();

		if (texto.isEmpty()) {
			throw new ErrorConPublicar(
					"Registro no tiene texto " + (registro.archivo == null ? "" : registro.archivo.toString()));
		}

		byte[] cuerpoTexto = texto.getBytes(StandardCharsets.UTF_8);
		if (cuerpoTexto.length > MAX_SIZE_BYTES) {
			throw new DemasiadoGrande();
		}

		// Parámetros form (urlencoded). Nota: medimos y enviamos SIEMPRE en bytes
		// UTF-8.
		try {
			String titulo = (registro.archivo != null) ? registro.archivo.getFileName().toString() : "CrashLog";
			String params = construirParams(texto, titulo);

			URL url = new URL(APIdeSitioDeRegistro.sitioDeConfig());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(TIMEOUT_MS);
			conn.setReadTimeout(TIMEOUT_MS);
			conn.setRequestProperty("User-Agent", UA);
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

			// Enviar con longitud fija (en bytes) para evitar transferencias chunked
			byte[] body = params.getBytes(StandardCharsets.UTF_8);
			conn.setDoOutput(true);
			conn.setFixedLengthStreamingMode(body.length);
			try (OutputStream os = conn.getOutputStream()) {
				os.write(body);
			}

			int code = conn.getResponseCode();
			if (code != HttpURLConnection.HTTP_OK) {
				throw new ErrorConPublicar("HTTP error: " + code);
			}

			StringBuilder response = new StringBuilder();
			try (BufferedReader in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
				String line;
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
			}

			String out = response.toString();
			if (out.startsWith("Error:")) {
				throw new ErrorConPublicar(out);
			}
			return out;
		} catch (IOException e) {
			throw new ErrorConPublicar("error de red: " + e.getMessage());
		}
	}

	// StikkedAPI.java
	@Override
	public String publicarTexto(String nombreSugerido, String contenido) throws DemasiadoGrande, ErrorConPublicar {
		String texto = (contenido == null) ? "" : contenido.trim();
		if (texto.isEmpty()) {
			throw new ErrorConPublicar("Registro no tiene texto");
		}

		byte[] cuerpoTexto = texto.getBytes(StandardCharsets.UTF_8);
		if (cuerpoTexto.length > MAX_SIZE_BYTES) {
			throw new DemasiadoGrande();
		}

		try {
			String titulo = (nombreSugerido == null || nombreSugerido.trim().isEmpty()) ? "CrashLog"
					: nombreSugerido.trim();
			String params = construirParams(texto, titulo);

			URL url = new URL(APIdeSitioDeRegistro.sitioDeConfig());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(TIMEOUT_MS);
			conn.setReadTimeout(TIMEOUT_MS);
			conn.setRequestProperty("User-Agent", UA);
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

			byte[] body = params.getBytes(StandardCharsets.UTF_8);
			conn.setDoOutput(true);
			conn.setFixedLengthStreamingMode(body.length);
			try (OutputStream os = conn.getOutputStream()) {
				os.write(body);
			}

			int code = conn.getResponseCode();
			if (code != HttpURLConnection.HTTP_OK) {
				throw new ErrorConPublicar("HTTP error: " + code);
			}

			StringBuilder response = new StringBuilder();
			try (BufferedReader in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
				String line;
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
			}

			String out = response.toString();
			if (out.startsWith("Error:")) {
				throw new ErrorConPublicar(out);
			}
			return out;
		} catch (IOException e) {
			throw new ErrorConPublicar("error de red: " + e.getMessage());
		}
	}

	/**
	 * Construye el cuerpo application/x-www-form-urlencoded (UTF-8) de forma
	 * segura.
	 */
	private static String construirParams(String texto, String titulo) throws IOException {
		// Importante: URLEncoder sobre cada valor, no sobre el string completo.
		String textEnc = URLEncoder.encode(texto, StandardCharsets.UTF_8.toString());
		String titleEnc = URLEncoder.encode(titulo, StandardCharsets.UTF_8.toString());
		String nameEnc = URLEncoder.encode(Statics.nombre_cd.obtener(), StandardCharsets.UTF_8.toString());
		String privEnc = URLEncoder.encode("1", StandardCharsets.UTF_8.toString());
		String langEnc = URLEncoder.encode("java", StandardCharsets.UTF_8.toString());

		// Evitar String.format (más GC); construir manualmente.
		StringBuilder sb = new StringBuilder(textEnc.length() + titleEnc.length() + 64);
		sb.append("text=").append(textEnc).append("&title=").append(titleEnc).append("&name=").append(nameEnc)
				.append("&private=").append(privEnc).append("&lang=").append(langEnc);
		return sb.toString();
	}

	/* =================== Publicación por partes (fallback) =================== */

	@Override
	public List<String> publicarRegistroEnPartes(Consola registro) throws ErrorConPublicar {
		String contenido = registro.obtainerContenidoParaPublicar();
		if (contenido == null)
			contenido = "";

		try {
			// Vía rápida: si cabe en una sola petición, listo.
			String enlace = publicarTexto(
					(registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt"), contenido);
			ArrayList<String> uno = new ArrayList<>();
			uno.add(enlace);

			String gid = grupoActual().get();
			if (gid != null) {
				int totalLineas = contenido.split("\n", -1).length;
				registrarParte(gid, 1, enlace, 1, totalLineas);
			}
			return uno;
		} catch (DemasiadoGrande e) {
			// Continuamos con división por bytes
		}

		final int MAX_BYTES = MAX_SIZE_BYTES;
		List<String> enlaces = new ArrayList<>();
		byte[] src = contenido.getBytes(StandardCharsets.UTF_8);

		int ini = 0;
		int parte = 1;
		int lineaDesde = 1;
		String nombre = (registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt");
		String gid = grupoActual().get();

		while (ini < src.length) {
			int fin = Math.min(src.length, ini + MAX_BYTES);

			// Intentar cortar en salto de línea para no romper líneas
			if (fin < src.length) {
				int back = fin;
				while (back > ini && src[back - 1] != (byte) '\n')
					back--;
				if (back > ini + 1024) { // no partir si apenas habría avance
					fin = back;
				}
			}

			String bloque = new String(src, ini, fin - ini, StandardCharsets.UTF_8);
			String etiqueta = nombre + " (parte " + (parte) + ")";

			try {
				String url = publicarTexto(etiqueta, bloque);
				enlaces.add(url);
				if (gid != null) {
					int lineasEnParte = bloque.split("\n", -1).length;
					int lineaHasta = lineaDesde + lineasEnParte - 1;
					registrarParte(gid, parte, url, lineaDesde, lineaHasta);
					lineaDesde = lineaHasta + 1;
				}
				parte++;
				ini = fin;
			} catch (DemasiadoGrande ex) {
				// Si aun así supera el límite (muy raro), recortar a la mitad y reintentar.
				int recorte = Math.min(fin - ini, MAX_BYTES / 2);
				if (recorte <= 0)
					throw new ErrorConPublicar("No se pudo dividir para Stikked.");
				fin = ini + recorte;
				// no avanzamos ini; repetimos el ciclo con el nuevo fin
			}
		}
		return enlaces;
	}

	@Override
	public boolean soporteEnlacesALinea() {
		return true;
	}

	private final BiMap<String, Integer, ParteInfo> indicePartes = new BiMap<>();
	private final ThreadLocal<String> grupoActual = new ThreadLocal<>();

	@Override
	public ThreadLocal<String> grupoActual() {
		return grupoActual;
	}

	@Override
	public BiMap<String, Integer, ParteInfo> indicePartes() {
		return indicePartes;
	}
}
