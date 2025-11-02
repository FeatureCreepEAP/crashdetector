package com.asbestosstar.crashdetector.api_sito_registro;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.asbestosstar.crashdetector.BiMap;
import com.asbestosstar.crashdetector.BiMap.DoubleKey;
import com.asbestosstar.crashdetector.Consola;

public class StikkedAPI implements APIdeSitioDeRegistro {

	private static final int MAX_SIZE_BYTES = 16 * 1024 * 1024; // 16MB

	@Override
	public String nombre() {
		return "stikked";
	}

	@Override
	public String publicarRegistro(Consola registro) throws DemasiadoGrande, ErrorConPublicar {
		String texto = registro.obtainerContenidoParaPublicar().trim();

		if (texto.equals("")) {
			throw new ErrorConPublicar("Registro no tiene texto " + registro.archivo.toString());
		}

		byte[] textobytes = texto.getBytes(StandardCharsets.UTF_8);
		if (textobytes.length > MAX_SIZE_BYTES) {
			throw new DemasiadoGrande();
		}

		// Prepare parameters
		String params = "";
		try {
			params = String.format("text=%s&title=CrashLog&name=CrashDetector&private=1&lang=java",
					URLEncoder.encode(texto, StandardCharsets.UTF_8.toString()));
		} catch (Exception e) {
			throw new ErrorConPublicar("Encoding error: " + e.getMessage());
		}

		try {
			URL url = new URL(APIdeSitioDeRegistro.sitioDeConfig());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(params.length()));
			conn.setDoOutput(true);

			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.writeBytes(params);
				wr.flush();
			}

			int responseCode = conn.getResponseCode();
			if (responseCode != 200) {
				throw new ErrorConPublicar("HTTP error: " + responseCode);
			}

			StringBuilder response = new StringBuilder();
			try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {

				String line;
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
			}

			if (response.toString().startsWith("Error:")) {
				throw new ErrorConPublicar(response.toString());
			} else {
				return response.toString();
			}
		} catch (IOException e) {
			throw new ErrorConPublicar("error de red: " + e.getMessage());
		}
	}

	@Override
	public List<String> sitiosPorDefecto() {
		List<String> sitios = new ArrayList<>();
		sitios.add("https://paste.mikumikudance.jp/api/create");
		sitios.add("https://paste.mikumikudance.jp/en/api/create");
		// sitios.add("https://paste.atlauncher.com/api/create");
		return sitios;
	}

	// StikkedAPI.java
	@Override
	public String publicarTexto(String nombreSugerido, String contenido) throws DemasiadoGrande, ErrorConPublicar {
		if (contenido == null)
			contenido = "";
		String texto = contenido.trim();
		if (texto.isEmpty()) {
			throw new ErrorConPublicar("Registro no tiene texto");
		}

		byte[] textobytes = texto.getBytes(StandardCharsets.UTF_8);
		if (textobytes.length > MAX_SIZE_BYTES) {
			throw new DemasiadoGrande();
		}

		try {
			String params = String.format("text=%s&title=%s&name=CrashDetector&private=1&lang=java",
					URLEncoder.encode(texto, StandardCharsets.UTF_8.toString()), URLEncoder.encode(
							nombreSugerido == null ? "CrashLog" : nombreSugerido, StandardCharsets.UTF_8.toString()));

			URL url = new URL(APIdeSitioDeRegistro.sitioDeConfig());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(params.length()));
			conn.setDoOutput(true);

			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.writeBytes(params);
				wr.flush();
			}

			int responseCode = conn.getResponseCode();
			if (responseCode != 200) {
				throw new ErrorConPublicar("HTTP error: " + responseCode);
			}

			StringBuilder response = new StringBuilder();
			try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
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

	@Override
	public List<String> publicarRegistroEnPartes(Consola registro) throws ErrorConPublicar {
		// Intentar directo
		String contenido = registro.obtainerContenidoParaPublicar();
		if (contenido == null)
			contenido = "";
		try {
			String enlace = publicarTexto(
					registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt", contenido);
			ArrayList<String> uno = new ArrayList<>();
			uno.add(enlace);
			return uno;
		} catch (DemasiadoGrande e) {
			// dividir por tamaño (Stikked usa solo límite de bytes)
		}

		// Dividir por bytes (máx 16 MB por parte)
		final int MAX_BYTES = MAX_SIZE_BYTES;
		List<String> enlaces = new ArrayList<>();
		byte[] src = contenido.getBytes(StandardCharsets.UTF_8);

		int ini = 0;
		int parte = 1;
		String nombre = (registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt");

		while (ini < src.length) {
			int fin = Math.min(src.length, ini + MAX_BYTES);
			// Intentar no cortar la última línea si es posible (retroceder hasta '\n')
			if (fin < src.length) {
				int back = fin;
				while (back > ini && src[back - 1] != (byte) '\n')
					back--;
				if (back > ini + 1024) { // no retroceder demasiado; umbral heurístico
					fin = back;
				}
			}
			String bloque = new String(src, ini, fin - ini, StandardCharsets.UTF_8);
			String etiqueta = nombre + " (parte " + (parte++) + ")";
			try {
				enlaces.add(publicarTexto(etiqueta, bloque));
			} catch (DemasiadoGrande e) {
				// Si aún excede, algo raro pasa (caracteres multibyte). Cortar más pequeño:
				int recorte = Math.min(fin - ini, MAX_BYTES / 2);
				if (recorte <= 0)
					throw new ErrorConPublicar("No se pudo dividir para Stikked.");
				fin = ini + recorte;
				continue; // reintenta con corte menor
			}
			ini = fin;
		}
		return enlaces;
	}

	








	@Override
	public boolean soporteEnlacesALinea() {
	    return true;
	}

	
	private final BiMap<String, Integer, ParteInfo> indicePartes =
	        new BiMap<>();

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