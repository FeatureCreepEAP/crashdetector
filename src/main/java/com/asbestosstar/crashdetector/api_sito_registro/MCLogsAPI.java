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
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.mapas.BiMap;

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

				HistoriaMCLogs.EntradaMCLogs entrada = extraerEntradaDeRespuesta(respuestaStr,
						APIdeSitioDeRegistro.sitioDeConfig());
				HistoriaMCLogs.agregar(entrada);
				return entrada.url;

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

				HistoriaMCLogs.EntradaMCLogs entrada = extraerEntradaDeRespuesta(respuestaStr,
						APIdeSitioDeRegistro.sitioDeConfig());
				HistoriaMCLogs.agregar(entrada);
				return entrada.url;

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

	private HistoriaMCLogs.EntradaMCLogs extraerEntradaDeRespuesta(String respuestaJson, String endpoint) {
		HistoriaMCLogs.EntradaMCLogs e = new HistoriaMCLogs.EntradaMCLogs();

		e.endpoint = endpoint;
		e.id = extraerString(respuestaJson, "id");
		e.url = extraerString(respuestaJson, "url");
		e.raw = extraerString(respuestaJson, "raw");
		e.token = extraerString(respuestaJson, "token");
		e.source = extraerString(respuestaJson, "source");

		e.created = extraerLong(respuestaJson, "created");
		e.expires = extraerLong(respuestaJson, "expires");
		e.size = extraerLong(respuestaJson, "size");
		e.lines = extraerLong(respuestaJson, "lines");
		e.errors = extraerLong(respuestaJson, "errors");

		return e;
	}

	/**
	 * Extrae un string simple de una respuesta JSON plana.
	 */
	private String extraerString(String json, String key) {
		if (json == null || key == null)
			return "";

		String patron = "\"" + key + "\":";
		int p = json.indexOf(patron);
		if (p < 0)
			return "";

		p += patron.length();

		while (p < json.length() && Character.isWhitespace(json.charAt(p)))
			p++;

		if (p >= json.length())
			return "";

		if (json.startsWith("null", p))
			return "";

		if (json.charAt(p) != '"')
			return "";

		p++;
		StringBuilder sb = new StringBuilder();
		boolean escape = false;

		for (; p < json.length(); p++) {
			char c = json.charAt(p);

			if (escape) {
				sb.append(c);
				escape = false;
				continue;
			}

			if (c == '\\') {
				escape = true;
				continue;
			}

			if (c == '"')
				break;

			sb.append(c);
		}

		return sb.toString().replace("\\/", "/");
	}

	/**
	 * Extrae un número largo de una respuesta JSON plana.
	 */
	private long extraerLong(String json, String key) {
		if (json == null || key == null)
			return 0L;

		String patron = "\"" + key + "\":";
		int p = json.indexOf(patron);
		if (p < 0)
			return 0L;

		p += patron.length();

		while (p < json.length() && Character.isWhitespace(json.charAt(p)))
			p++;

		int inicio = p;
		while (p < json.length()) {
			char c = json.charAt(p);
			if ((c >= '0' && c <= '9') || c == '-') {
				p++;
			} else {
				break;
			}
		}

		try {
			return Long.parseLong(json.substring(inicio, p));
		} catch (Exception e) {
			return 0L;
		}
	}

	/**
	 * Elimina un registro MCLogs usando el token recibido al publicarlo.
	 */
	public boolean eliminarRegistro(String endpointBase, String id, String token) throws ErrorConPublicar {
		if (endpointBase == null || endpointBase.trim().isEmpty())
			endpointBase = "https://api.mclo.gs/1/log";

		if (id == null || id.trim().isEmpty())
			throw new ErrorConPublicar("Falta el ID del registro");

		if (token == null || token.trim().isEmpty())
			throw new ErrorConPublicar("Falta el token de eliminación");

		try {
			String endpoint = endpointBase.trim();
			if (!endpoint.endsWith("/")) {
				endpoint += "/";
			}
			endpoint += id.trim();

			URL url = new URL(endpoint);
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);

			conexion.setRequestMethod("DELETE");
			conexion.setConnectTimeout(TIMEOUT_MS);
			conexion.setReadTimeout(TIMEOUT_MS);
			conexion.setRequestProperty("User-Agent", UA);
			conexion.setRequestProperty("Authorization", "Bearer " + token.trim());

			int codigo = conexion.getResponseCode();

			StringBuilder respuesta = new StringBuilder();
			try (BufferedReader lector = new BufferedReader(new InputStreamReader(
					(codigo >= 200 && codigo < 300) ? conexion.getInputStream() : conexion.getErrorStream(),
					StandardCharsets.UTF_8))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					respuesta.append(linea);
				}
			}

			String r = respuesta.toString();
			if (codigo == HttpURLConnection.HTTP_OK && r.contains("\"success\":true")) {
				return true;
			}

			throw new ErrorConPublicar(extraerErrorDeRespuesta(r));
		} catch (IOException e) {
			throw new ErrorConPublicar("Error de red: " + e.getMessage());
		}
	}

	/**
	 * Devuelve la GUI opcional para ver historial y eliminar registros MCLogs.
	 */
	@Override
	public Supplier<TipoGUI> eliminador() {
		return () -> TipoGUI.MCLOGS_HISTORIAL;
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
