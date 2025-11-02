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

import com.asbestosstar.crashdetector.BiMap;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro.ParteInfo;

public class MCLogsAPI implements APIdeSitioDeRegistro {

	// Límites de la API MCLogs
	private static final int ESPACIO_MAXIMO_BYTES = 10 * 1024 * 1024; // 10MB
	private static final int LINEAS_MAXIMAS = 25000;

	// Paràmetros de red
	private static final int TIMEOUT_MS = 30000;
	private static final String UA = "CrashDetector/1.x (+mclogs)";

	@Override
	public String nombre() {
		return "mclogs";
	}

	/*
	 * ============================ Publicación básica ============================
	 */

	@Override
	public String publicarRegistro(Consola registro) throws DemasiadoGrande, ErrorConPublicar {
		String contenido = (registro.obtainerContenidoParaPublicar() == null) ? ""
				: registro.obtainerContenidoParaPublicar().trim();

		// Validaciones mínimas de MCLogs
		if (contenido.isEmpty()) {
			throw new ErrorConPublicar("El contenido del registro está vacío");
		}
		byte[] bytesContenido = contenido.getBytes(StandardCharsets.UTF_8);
		if (bytesContenido.length > ESPACIO_MAXIMO_BYTES) {
			throw new DemasiadoGrande();
		}
		int cantidadLineas = contenido.split("\n", -1).length;
		if (cantidadLineas > LINEAS_MAXIMAS) {
			throw new DemasiadoGrande();
		}

		// Cuerpo x-www-form-urlencoded (UTF-8)
		try {
			String datosPost = "content=" + URLEncoder.encode(contenido, StandardCharsets.UTF_8.toString());
			byte[] body = datosPost.getBytes(StandardCharsets.UTF_8);

			URL url = new URL(APIdeSitioDeRegistro.sitioDeConfig());
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
			conexion.setRequestMethod("POST");
			conexion.setConnectTimeout(TIMEOUT_MS);
			conexion.setReadTimeout(TIMEOUT_MS);
			conexion.setRequestProperty("User-Agent", UA);
			conexion.setRequestProperty("Accept-Charset", "utf-8");
			conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

			// Longitud fija en BYTES (evita chunked)
			conexion.setDoOutput(true);
			conexion.setFixedLengthStreamingMode(body.length);
			try (OutputStream salida = conexion.getOutputStream()) {
				salida.write(body);
			}

			int codigoRespuesta = conexion.getResponseCode();
			if (codigoRespuesta != HttpURLConnection.HTTP_OK) {
				throw new ErrorConPublicar("Código de error HTTP: " + codigoRespuesta);
			}

			// Respuesta en UTF-8
			StringBuilder respuesta = new StringBuilder();
			try (BufferedReader lector = new BufferedReader(
					new InputStreamReader(conexion.getInputStream(), StandardCharsets.UTF_8))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					respuesta.append(linea);
				}
			}

			String respuestaStr = respuesta.toString();
			if (respuestaStr.contains("\"success\":true")) {
				return extraerUrlDeRespuesta(respuestaStr);
			} else {
				String error = extraerErrorDeRespuesta(respuestaStr);
				throw new ErrorConPublicar("Error del servidor: " + error);
			}
		} catch (IOException e) {
			throw new ErrorConPublicar("Error de red: " + e.getMessage());
		}
	}

	@Override
	public List<String> sitiosPorDefecto() {
		List<String> sitios = new ArrayList<>();
		sitios.add("https://api.mclo.gs/1/log");
		return sitios;
	}

	/** Extrae el campo "url" del JSON de éxito. */
	private String extraerUrlDeRespuesta(String respuestaJson) {
		int inicio = respuestaJson.indexOf("\"url\":\"");
		if (inicio < 0)
			return null;
		inicio += 7;
		int fin = respuestaJson.indexOf("\"", inicio);
		if (fin < 0)
			return null;
		String url = respuestaJson.substring(inicio, fin);
		// El JSON de MCLogs escapa con barras invertidas, las retiramos
		return url.replace("\\", "");
	}

	/** Extrae el campo "error" del JSON de error. */
	private String extraerErrorDeRespuesta(String respuestaJson) {
		int inicio = respuestaJson.indexOf("\"error\":\"");
		if (inicio < 0)
			return "desconocido";
		inicio += 9;
		int fin = respuestaJson.indexOf("\"", inicio);
		if (fin < 0)
			fin = respuestaJson.length();
		return respuestaJson.substring(inicio, fin).replace("\\\"", "\"");
	}

	// MCLogsAPI.java — versión con envío en bytes y longitud fija
	@Override
	public String publicarTexto(String nombreSugerido, String contenido) throws DemasiadoGrande, ErrorConPublicar {
		String texto = (contenido == null) ? "" : contenido.trim();
		if (texto.isEmpty()) {
			throw new ErrorConPublicar("El contenido del registro está vacío");
		}

		byte[] bytesContenido = texto.getBytes(StandardCharsets.UTF_8);
		if (bytesContenido.length > ESPACIO_MAXIMO_BYTES) {
			throw new DemasiadoGrande();
		}

		int cantidadLineas = texto.split("\n", -1).length;
		if (cantidadLineas > LINEAS_MAXIMAS) {
			throw new DemasiadoGrande();
		}

		try {
			String datosPost = "content=" + URLEncoder.encode(texto, StandardCharsets.UTF_8.toString());
			byte[] body = datosPost.getBytes(StandardCharsets.UTF_8);

			URL url = new URL(APIdeSitioDeRegistro.sitioDeConfig());
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
			conexion.setRequestMethod("POST");
			conexion.setConnectTimeout(TIMEOUT_MS);
			conexion.setReadTimeout(TIMEOUT_MS);
			conexion.setRequestProperty("User-Agent", UA);
			conexion.setRequestProperty("Accept-Charset", "utf-8");
			conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

			conexion.setDoOutput(true);
			conexion.setFixedLengthStreamingMode(body.length);
			try (OutputStream salida = conexion.getOutputStream()) {
				salida.write(body);
			}

			int codigoRespuesta = conexion.getResponseCode();
			if (codigoRespuesta != HttpURLConnection.HTTP_OK) {
				throw new ErrorConPublicar("Código de error HTTP: " + codigoRespuesta);
			}

			StringBuilder respuesta = new StringBuilder();
			try (BufferedReader lector = new BufferedReader(
					new InputStreamReader(conexion.getInputStream(), StandardCharsets.UTF_8))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					respuesta.append(linea);
				}
			}

			String respuestaStr = respuesta.toString();
			if (respuestaStr.contains("\"success\":true")) {
				return extraerUrlDeRespuesta(respuestaStr);
			} else {
				String error = extraerErrorDeRespuesta(respuestaStr);
				throw new ErrorConPublicar("Error del servidor: " + error);
			}
		} catch (IOException e) {
			throw new ErrorConPublicar("Error de red: " + e.getMessage());
		}
	}

	/*
	 * ============================ Publicación por partes
	 * ============================
	 */

	@Override
	public List<String> publicarRegistroEnPartes(Consola registro) throws ErrorConPublicar, DemasiadoGrande {
		String contenido = (registro.obtainerContenidoParaPublicar() == null) ? ""
				: registro.obtainerContenidoParaPublicar();

		// Intento directo (si cumple límites de MCLogs)
		try {
			String unico = publicarTexto(
					(registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt"), contenido);
			List<String> una = new ArrayList<>();
			una.add(unico);

			String gid = grupoActual().get();
			if (gid != null) {
				int totalLineas = contenido.split("\n", -1).length;
				registrarParte(gid, 1, unico, 1, totalLineas);
			}
			return una;
		} catch (DemasiadoGrande e) {
			// Continuar con división controlada
		}

		// Fallback: dividir por líneas y bytes respetando límites MCLogs
		final int MAX_BYTES = ESPACIO_MAXIMO_BYTES;
		final int MAX_LINEAS = LINEAS_MAXIMAS;

		String[] lineas = contenido.split("\n", -1);
		List<String> partes = new ArrayList<>();

		StringBuilder bloque = new StringBuilder();
		int lineasBloque = 0;
		int bytesBloque = 0;

		for (String linea : lineas) {
			String conSalto = linea + "\n";
			int bytesLinea = conSalto.getBytes(StandardCharsets.UTF_8).length;

			boolean cortaPorLineas = (lineasBloque + 1) > MAX_LINEAS;
			boolean cortaPorBytes = (bytesBloque + bytesLinea) > MAX_BYTES;

			if (cortaPorLineas || cortaPorBytes) {
				// descargar el bloque actual y reiniciar acumuladores
				partes.add(bloque.toString());
				bloque.setLength(0);
				lineasBloque = 0;
				bytesBloque = 0;
			}

			bloque.append(conSalto);
			lineasBloque++;
			bytesBloque += bytesLinea;
		}
		if (bloque.length() > 0) {
			partes.add(bloque.toString());
		}

		List<String> enlaces = new ArrayList<>();
		String nombreBase = (registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt");

		String gid = grupoActual().get();
		int lineaDesde = 1;

		for (int i = 0; i < partes.size(); i++) {
			String etiqueta = (partes.size() == 1) ? nombreBase : (nombreBase + " (parte " + (i + 1) + ")");
			String bloqueTxt = partes.get(i);

			String url = publicarTexto(etiqueta, bloqueTxt);
			enlaces.add(url);

			if (gid != null) {
				int lineasEnParte = bloqueTxt.split("\n", -1).length;
				int lineaHasta = lineaDesde + lineasEnParte - 1;
				registrarParte(gid, i + 1, url, lineaDesde, lineaHasta);
				lineaDesde = lineaHasta + 1;
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
