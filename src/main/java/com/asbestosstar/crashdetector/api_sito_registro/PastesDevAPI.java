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
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro.ParteInfo;
import com.asbestosstar.crashdetector.mapas.BiMap;

/**
 * API para publicar registros en pastes.dev.
 *
 * - POST a https://api.pastes.dev/post - Cuerpo GZIP (text/plain;
 * charset=utf-8), longitud fija (no chunked) - Obtiene la clave desde Location
 * o JSON {"key":"..."} - Devuelve https://pastes.dev/{key}
 *
 * Optimizaciones: - Si el texto SIN comprimir <= 15 MB, publica en UNA sola
 * parte (sin dividir). - Si excede, divide sin recomprimir por cada línea: GZIP
 * una sola vez por parte.
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
		List<String> sitios = new ArrayList<>();
		sitios.add("https://api.pastes.dev/post");
		return sitios;
	}

	/* ========================= Utilidades ========================= */

	/** Normaliza el endpoint seleccionado por el usuario. */
	private String normalizarEndpoint(String s) {
		if (s == null || s.isEmpty()) {
			return "https://api.pastes.dev/post";
		}
		if (s.endsWith("?"))
			s = s.substring(0, s.length() - 1);
		if (s.equalsIgnoreCase("https://api.pastes.dev") || s.equalsIgnoreCase("https://api.pastes.dev/")) {
			s = "https://api.pastes.dev/post";
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
			while ((l = br.readLine()) != null)
				sb.append(l);
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/** Extrae la clave desde la cabecera Location. */
	private static String extraerKeyDeLocation(String location) {
		if (location == null || location.isEmpty())
			return null;
		String loc = location.trim();
		String apiBase = "https://api.pastes.dev/";
		if (loc.startsWith(apiBase)) {
			loc = loc.substring(apiBase.length());
		}
		if (loc.startsWith("/"))
			loc = loc.substring(1);
		int slash = loc.indexOf('/');
		return (slash >= 0) ? loc.substring(0, slash) : loc;
	}

	/** Extrae "key" de un JSON simple: {"key":"..."} */
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

	/* ========================= Publicación básica ========================= */

	@Override
	public String publicarTexto(String nombreSugerido, String contenido) throws ErrorConPublicar {
		try {
			byte[] datos = (contenido == null ? "" : contenido).getBytes(StandardCharsets.UTF_8);
			String endpoint = normalizarEndpoint(APIdeSitioDeRegistro.sitioDeConfig());
			URL url = new URL(endpoint);

			// Comprimir UNA vez y configurar longitud fija (evita chunked)
			byte[] gzData = comprimirGZIP(datos);

			HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
			con.setRequestMethod("POST");
			con.setConnectTimeout(TIMEOUT);
			con.setReadTimeout(TIMEOUT);
			con.setRequestProperty("User-Agent", UA);
			con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			con.setRequestProperty("Content-Encoding", "gzip");
			con.setDoOutput(true);
			con.setFixedLengthStreamingMode(gzData.length);

			try (OutputStream os = con.getOutputStream()) {
				os.write(gzData);
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

	/* ========================= Publicación por partes ========================= */

	@Override
	public List<String> publicarRegistroEnPartes(Consola registro)
			throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro, LimteDeTasa {

		String contenido = registro.obtainerContenidoParaPublicar();
		if (contenido == null)
			contenido = "";

		// Límite del SERVIDOR (pastes.dev) en bytes (GZIP) y presupuesto en crudo
		final int LIMITE_GZIP = 15 * 1024 * 1024; // 15 MB (servidor)
		final int PRESUPUESTO_RAW = 17 * 1024 * 1024; // margen crudo antes de comprimir

		byte[] sinComprimir = contenido.getBytes(StandardCharsets.UTF_8);

		// ---- Vía rápida: ≤ 15 MB sin comprimir → una sola publicación ----
		if (sinComprimir.length <= LIMITE_GZIP) {
			String base = (registro.archivo != null) ? registro.archivo.getFileName().toString() : "log.txt";
			String url = publicarTexto(base, contenido);
			List<String> unica = new ArrayList<>();
			unica.add(url);

			String gid = grupoActual().get();
			if (gid != null) {
				int totalLineas = contenido.isEmpty() ? 0 : contenido.split("\n", -1).length;
				registrarParte(gid, 1, url, 1, Math.max(1, totalLineas));
			}
			return unica;
		}

		// ---- Caso grande: dividir con una sola compresión por parte ----
		List<String> urls = new ArrayList<>();
		String[] lineas = contenido.split("\n", -1);

		StringBuilder parte = new StringBuilder();
		int bytesParte = 0;
		int lineaDesde = 1;
		int acumuladas = 0;
		int indiceParte = 1;
		String gid = grupoActual().get();

		for (int i = 0; i < lineas.length; i++) {
			String l = lineas[i];
			String candidata = (parte.length() == 0) ? l : ("\n" + l);
			int bytesCand = candidata.getBytes(StandardCharsets.UTF_8).length;

			// Acumular hasta el presupuesto en crudo
			if (bytesParte + bytesCand <= PRESUPUESTO_RAW) {
				parte.append(candidata);
				bytesParte += bytesCand;
				acumuladas++;
				continue;
			}

			// Presupuesto alcanzado → comprimir UNA vez y verificar
			try {
				byte[] gz = comprimirGZIP(parte.toString().getBytes(StandardCharsets.UTF_8));
				if (gz.length <= LIMITE_GZIP) {
					// Publicar la parte actual
					String u = publicarParte(registro, indiceParte, parte.toString());
					urls.add(u);
					if (gid != null) {
						registrarParte(gid, indiceParte++, u, lineaDesde, lineaDesde + acumuladas - 1);
						lineaDesde += acumuladas;
					}
					// Resetear y reintentar la misma línea en nueva parte
					parte.setLength(0);
					bytesParte = 0;
					acumuladas = 0;
					i--;
				} else {
					// Todavía excede comprimido: partir aproximadamente por la mitad (buscando \n)
					int mid = parte.length() / 2;
					int split = parte.lastIndexOf("\n", mid);
					if (split <= 0)
						split = mid;

					String izquierda = parte.substring(0, split);
					byte[] gzIzq = comprimirGZIP(izquierda.getBytes(StandardCharsets.UTF_8));
					if (gzIzq.length > LIMITE_GZIP) {
						// Partición más agresiva (aprox. 1/4) si aún se excede
						int q = parte.length() / 4;
						int s2 = parte.lastIndexOf("\n", q);
						if (s2 > 0)
							split = s2;
						izquierda = parte.substring(0, split);
						gzIzq = comprimirGZIP(izquierda.getBytes(StandardCharsets.UTF_8));
					}

					// Publicar izquierda
					String u = publicarParte(registro, indiceParte, izquierda);
					urls.add(u);
					if (gid != null) {
						int lineasIzq = izquierda.isEmpty() ? 0 : izquierda.split("\n", -1).length;
						registrarParte(gid, indiceParte++, u, lineaDesde, lineaDesde + lineasIzq - 1);
						lineaDesde += lineasIzq;
					}

					// Conservar derecha y reintentar línea actual
					String derecha = parte.substring(split);
					parte.setLength(0);
					parte.append(derecha);
					bytesParte = derecha.getBytes(StandardCharsets.UTF_8).length;
					acumuladas = derecha.isEmpty() ? 0 : derecha.split("\n", -1).length;
					i--;
				}
			} catch (Exception ex) {
				throw new ErrorConPublicar(ex.getMessage());
			}
		}

		// Última descarga pendiente
		if (parte.length() > 0) {
			String u = publicarParte(registro, indiceParte, parte.toString());
			urls.add(u);
			if (gid != null) {
				registrarParte(gid, indiceParte, u, lineaDesde, lineaDesde + acumuladas - 1);
			}
		}

		return urls;
	}

	/** Publica una parte con el nombre adecuado (mantiene sufijo " (parte n)"). */
	private String publicarParte(Consola registro, int indiceParte, String texto) throws ErrorConPublicar {
		String base = (registro.archivo != null) ? registro.archivo.getFileName().toString() : "log.txt";
		String nombre = (indiceParte > 1) ? base + " (parte " + indiceParte + ")" : base;
		return publicarTexto(nombre, texto);
	}
}
