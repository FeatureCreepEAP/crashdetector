package com.asbestosstar.crashdetector.api_sito_registro;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.BiMap;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro.ParteInfo;

public class MCLogsAPI implements APIdeSitioDeRegistro {

	private static final int ESPACIO_MAXIMO_BYTES = 10 * 1024 * 1024; // 10MB
	private static final int LINEAS_MAXIMAS = 25000;

	@Override
	public String nombre() {
		return "mclogs";
	}

	@Override
	public String publicarRegistro(Consola registro) throws DemasiadoGrande, ErrorConPublicar {
		String contenido = registro.obtainerContenidoParaPublicar().trim();

		// Verificar contenido vacío
		if (contenido.isEmpty()) {
			throw new ErrorConPublicar("El contenido del registro está vacío");
		}

		// Verificar límite de tamaño
		byte[] bytesContenido = contenido.getBytes(StandardCharsets.UTF_8);
		if (bytesContenido.length > ESPACIO_MAXIMO_BYTES) {
			throw new DemasiadoGrande();
		}

		// Verificar límite de líneas
		int cantidadLineas = contenido.split("\n").length;
		if (cantidadLineas > LINEAS_MAXIMAS) {
			throw new DemasiadoGrande();
		}

		// Preparar datos de solicitud
		String datosPost;
		try {
			datosPost = "content=" + URLEncoder.encode(contenido, StandardCharsets.UTF_8.toString());
		} catch (Exception e) {
			throw new ErrorConPublicar("Error al codificar los datos: " + e.getMessage());
		}

		// Enviar solicitud HTTP
		try {
			URL url = new URL(APIdeSitioDeRegistro.sitioDeConfig());
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setRequestMethod("POST");
			conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conexion.setRequestProperty("Content-Length", String.valueOf(datosPost.length()));
			conexion.setDoOutput(true);

			try (DataOutputStream salida = new DataOutputStream(conexion.getOutputStream())) {
				salida.writeBytes(datosPost);
				salida.flush();
			}

			int codigoRespuesta = conexion.getResponseCode();
			if (codigoRespuesta != 200) {
				throw new ErrorConPublicar("Código de error HTTP: " + codigoRespuesta);
			}

			// Procesar respuesta
			StringBuilder respuesta = new StringBuilder();
			try (BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					respuesta.append(linea);
				}
			}

			// Analizar respuesta JSON
			// Analizar respuesta manualmente
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

	private String extraerUrlDeRespuesta(String respuestaJson) {
		int inicio = respuestaJson.indexOf("\"url\":\"") + 7;
		int fin = respuestaJson.indexOf("\"", inicio);
		String url = respuestaJson.substring(inicio, fin);
		return url.replace("\\", ""); // Eliminar todas las barras invertidas
	}

	private String extraerErrorDeRespuesta(String respuestaJson) {
		int inicio = respuestaJson.indexOf("\"error\":\"") + 9;
		int fin = respuestaJson.indexOf("\"", inicio);
		return respuestaJson.substring(inicio, fin).replace("\\\"", "\""); // Corregir comillas escapadas
	}

	// MCLogsAPI.java
	@Override
	public String publicarTexto(String nombreSugerido, String contenido) throws DemasiadoGrande, ErrorConPublicar {
		// Validaciones propias de MCLogs (esta API SÍ tiene límites por archivo)
		if (contenido == null)
			contenido = "";
		String texto = contenido.trim();
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

		// Preparar y enviar POST (misma ruta que usarías en publicarRegistro)
		try {
			String datosPost = "content=" + URLEncoder.encode(texto, StandardCharsets.UTF_8.toString());

			URL url = new URL(APIdeSitioDeRegistro.sitioDeConfig());
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setRequestMethod("POST");
			conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conexion.setRequestProperty("Content-Length", String.valueOf(datosPost.length()));
			conexion.setDoOutput(true);

			try (DataOutputStream salida = new DataOutputStream(conexion.getOutputStream())) {
				salida.writeBytes(datosPost);
				salida.flush();
			}

			int codigoRespuesta = conexion.getResponseCode();
			if (codigoRespuesta != 200) {
				throw new ErrorConPublicar("Código de error HTTP: " + codigoRespuesta);
			}

			StringBuilder respuesta = new StringBuilder();
			try (BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					respuesta.append(linea);
				}
			}

			String respuestaStr = respuesta.toString();
			if (respuestaStr.contains("\"success\":true")) {
				return extraerUrlDeRespuesta(respuestaStr).replace("\\", "");
			} else {
				String error = extraerErrorDeRespuesta(respuestaStr);
				throw new ErrorConPublicar("Error del servidor: " + error);
			}
		} catch (IOException e) {
			throw new ErrorConPublicar("Error de red: " + e.getMessage());
		}
	}

	@Override
	public List<String> publicarRegistroEnPartes(Consola registro) throws ErrorConPublicar, DemasiadoGrande {

		String contenido = registro.obtainerContenidoParaPublicar();
		if (contenido == null)
			contenido = "";

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
		}

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
				partes.add(bloque.toString());
				bloque.setLength(0);
				lineasBloque = 0;
				bytesBloque = 0;
			}
			bloque.append(conSalto);
			lineasBloque++;
			bytesBloque += bytesLinea;
		}
		if (bloque.length() > 0)
			partes.add(bloque.toString());

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
		// TODO Auto-generated method stub
		return true;
	}

	private final BiMap<String, Integer, ParteInfo> indicePartes = new BiMap<>();

	private final ThreadLocal<String> grupoActual = new ThreadLocal<>();

	@Override
	public ThreadLocal<String> grupoActual() {
		// TODO Auto-generated method stub
		return grupoActual;
	}

	@Override
	public BiMap<String, Integer, ParteInfo> indicePartes() {
		// TODO Auto-generated method stub
		return indicePartes;
	}

}