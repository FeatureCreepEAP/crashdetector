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

import com.asbestosstar.crashdetector.BiMap;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro.ParteInfo;

import java.util.zip.GZIPOutputStream;

/**
 * API para publicar registros en pastes.dev.
 * 
 * - Envío por HTTP POST a https://api.pastes.dev/post - Cuerpo comprimido en
 * GZIP (text/plain; charset=utf-8) - Obtiene la clave desde la cabecera
 * Location o desde JSON {"key":"..."} - Devuelve el enlace público
 * https://pastes.dev/{key}
 */
public class PastesDevAPI implements APIdeSitioDeRegistro {

	private static final String UA = "CrashDetector/1.x (+pastes.dev)";
	private static final int TIMEOUT = 30000;

	@Override
	public String nombre() {
		return "PastesDev";
	}

	@Override
	public List<String> sitiosPorDefecto() {
		// Usamos el endpoint de la API (POST). El usuario puede cambiarlo en Config.
		List<String> sitios = new ArrayList<>();
		sitios.add("https://api.pastes.dev/post");
		return sitios;
	}

	/*
	 * ========================= Métodos auxiliares =========================
	 */

	/** Normaliza el endpoint seleccionado por el usuario. */
	private String normalizarEndpoint(String s) {
		if (s == null || s.isEmpty()) {
			// Fallback seguro
			return "https://api.pastes.dev/post";
		}
		// Quitar posibles '?' finales heredados de otros servicios
		if (s.endsWith("?"))
			s = s.substring(0, s.length() - 1);
		// Si el usuario puso la raíz, añadimos /post
		if (s.equalsIgnoreCase("https://api.pastes.dev") || s.equalsIgnoreCase("https://api.pastes.dev/")) {
			s = "https://api.pastes.dev/post";
		}
		return s;
	}

	/** Comprime en GZIP un arreglo de bytes. */
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

	/** Intenta extraer la clave desde la cabecera Location. */
	private static String extraerKeyDeLocation(String location) {
		if (location == null || location.isEmpty())
			return null;
		// Ejemplos:
		// Location: /abcd12
		// Location: https://api.pastes.dev/abcd12
		String loc = location.trim();
		// Quitar prefijo de la API si aparece completo
		String apiBase = "https://api.pastes.dev/";
		if (loc.startsWith(apiBase)) {
			loc = loc.substring(apiBase.length());
		}
		// Quitar barra inicial si existe
		if (loc.startsWith("/"))
			loc = loc.substring(1);

		// La "key" debería ser el primer segmento
		int slash = loc.indexOf('/');
		return (slash >= 0) ? loc.substring(0, slash) : loc;
	}

	/** Extrae "key" de un JSON muy simple: {"key":"..."} (sin usar librerías). */
	private static String extraerKeyDeJson(String json) {
		if (json == null)
			return null;
		String key = "\"key\"";
		int i = json.indexOf(key);
		if (i < 0)
			return null;
		int colon = json.indexOf(':', i);
		if (colon < 0)
			return null;

		// Saltar espacios
		int pos = colon + 1;
		while (pos < json.length() && Character.isWhitespace(json.charAt(pos)))
			pos++;

		if (pos >= json.length() || json.charAt(pos) != '\"')
			return null;
		int fin = json.indexOf('\"', pos + 1);
		if (fin < 0)
			return null;

		return json.substring(pos + 1, fin);
	}

	// PastesDevAPI.java
	@Override
	public String publicarTexto(String nombreSugerido, String contenido) throws ErrorConPublicar {
		try {
			byte[] datos = (contenido == null ? "" : contenido).getBytes(StandardCharsets.UTF_8);
			String endpoint = normalizarEndpoint(APIdeSitioDeRegistro.sitioDeConfig());
			URL url = new URL(endpoint);

			HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
			con.setRequestMethod("POST");
			con.setConnectTimeout(TIMEOUT);
			con.setReadTimeout(TIMEOUT);
			con.setRequestProperty("User-Agent", UA);
			con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			con.setRequestProperty("Content-Encoding", "gzip");
			con.setDoOutput(true);

			try (OutputStream os = con.getOutputStream()) {
				os.write(comprimirGZIP(datos));
			}

			int code = con.getResponseCode();
			if (code != HttpURLConnection.HTTP_CREATED && code != HttpURLConnection.HTTP_OK) {
				String err = leer(con.getErrorStream());
				throw new ErrorConPublicar("PastesDev HTTP " + code + (err == null ? "" : (": " + err)));
			}

			String location = con.getHeaderField("Location");
			String key = extraerKeyDeLocation(location);
			if (key == null || key.isEmpty()) {
				String body = leer(con.getInputStream());
				key = extraerKeyDeJson(body);
			}
			if (key == null || key.isEmpty()) {
				throw new ErrorConPublicar("PastesDev sin clave en la respuesta");
			}
			return "https://pastes.dev/" + key;
		} catch (Exception ex) {
			throw new ErrorConPublicar(ex.getMessage());
		}
	}

	@Override
	public String publicarRegistro(Consola registro) {
		try {
			return publicarTexto(registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt",
					registro.obtainerContenidoParaPublicar());
		} catch (ErrorConPublicar e) {
			CrashDetectorLogger.logException(e);
			return null;
		}
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

	@Override
	public List<String> publicarRegistroEnPartes(Consola registro)
			throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro, LimteDeTasa {
		String contenido = registro.obtainerContenidoParaPublicar();
		if (contenido == null)
			contenido = "";
		final int LIMITE = 15 * 1024 * 1024;

		List<String> urls = new ArrayList<>();
		String[] lineas = contenido.split("\n", -1);
		StringBuilder parte = new StringBuilder();
		int lineaDesde = 1;
		int acumuladas = 0;
		int indiceParte = 1;
		String gid = grupoActual().get();

		for (int i = 0; i < lineas.length; i++) {
			String l = lineas[i];
			String candidata = parte.length() == 0 ? l : ("\n" + l);
			try {
				byte[] gz = comprimirGZIP((parte.toString() + candidata).getBytes(StandardCharsets.UTF_8));
				if (gz.length <= LIMITE) {
					parte.append(candidata);
					acumuladas++;
				} else {
					if (parte.length() == 0) {
						String u = publicarTexto(
								registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt", l);
						urls.add(u);
						if (gid != null) {
							registrarParte(gid, indiceParte++, u, lineaDesde, lineaDesde);
							lineaDesde++;
						}
					} else {
						String base = registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt";
						String u = publicarTexto(base + " (parte " + indiceParte + ")", parte.toString());
						urls.add(u);
						if (gid != null) {
							registrarParte(gid, indiceParte++, u, lineaDesde, lineaDesde + acumuladas - 1);
							lineaDesde += acumuladas;
						}
						parte.setLength(0);
						acumuladas = 0;
						i--;
					}
				}
			} catch (Exception ex) {
				throw new ErrorConPublicar(ex.getMessage());
			}
		}
		if (parte.length() > 0) {
			String base = registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt";
			String suf = (indiceParte > 1) ? " (parte " + indiceParte + ")" : "";
			String u = publicarTexto(base + suf, parte.toString());
			urls.add(u);
			if (gid != null) {
				registrarParte(gid, indiceParte, u, lineaDesde, lineaDesde + acumuladas - 1);
			}
		}
		return urls;
	}

}
