package com.asbestosstar.crashdetector;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Lector centralizado de URLs de logs/pastes.
 *
 * Importante: los enlaces SecureLogger con sufijo ".tar.gz" NO contienen un
 * TAR. Son simplemente texto plano comprimido con GZIP. Esta clase los
 * descomprime directamente con GZIPInputStream.
 */
public class DescargarRegistors {

	private static final int TIMEOUT_CONEXION_MS = 10000;
	private static final int TIMEOUT_LECTURA_MS = 30000;
	private static final int LIMITE_DESCARGA_BYTES = 128 * 1024 * 1024;

	private static final Pattern PATRON_MCLOGS = Pattern
			.compile("^(?:https?://)?(?:api\\.)?mclo\\.gs/(?:1/raw/)?([a-zA-Z0-9]+)(?:[?#].*)?$");

	private static final Pattern PATRON_GNOMEBOT = Pattern
			.compile("^(?:https?://)?gnomebot\\.dev/paste/mclogs/([a-zA-Z0-9]+)(?:[?#].*)?$");

	private static final Pattern PATRON_KOSTROMDAN = Pattern.compile(
			"^(?:https?://)?(?:kostromdan\\.dev/paste/mclogs|paste\\.kostromdan\\.dev/(?:paste/)?mclogs)/([a-zA-Z0-9]+)(?:[?#].*)?$");

	private static final Pattern PATRON_KDAN = Pattern
			.compile("^(?:https?://)?p\\.kdan\\.dev/([a-zA-Z0-9]+)(?:[?#].*)?$");

	private static final Pattern PATRON_PASTES_DEV = Pattern
			.compile("^(?:https?://)?pastes\\.dev/([a-zA-Z0-9]+)(?:[?#].*)?$");

	private static final Pattern PATRON_BYTEBIN = Pattern
			.compile("^(?:https?://)?bytebin\\.lucko\\.me/([a-zA-Z0-9]+)(?:[?#].*)?$");

	private static final Pattern PATRON_CRASHDETECTOR = Pattern
			.compile("^(?:https?://)?asbestosstar\\.egoism\\.jp/crash_detector/paste/endpoint\\.php"
					+ "(?:(?:\\?id=)|/logs/)([a-zA-Z0-9_-]+)(?:\\.gz)?(?:[&#?].*)?$");

	private static final Pattern PATRON_MMD = Pattern
			.compile("^(?:https?://)?paste\\.(mikumikudance\\.jp|centos\\.org)/"
					+ "(?:en/)?(?:view/)?(?:raw/)?([a-zA-Z0-9]+)(?:[?#].*)?$");

	private static final Pattern PATRON_SECURELOGGER = Pattern
			.compile("^(?:https?://)?((?:[a-zA-Z0-9-]+\\.)*securelogger\\.(?:net|top))/files/"
					+ "([a-zA-Z0-9-]+)(?:\\.tar\\.gz)?(?:[?#].*)?$");

	private static final Pattern PATRON_KOSTROMDAN_ARRAY = Pattern
			.compile("^(?:https?://)?paste\\.kostromdan\\.dev/mclogsarray/" + "([a-zA-Z0-9]+)(?:/.*)?(?:[?#].*)?$");

	private static final Pattern PATRON_STIKKED = Pattern
			.compile("^(https?://[^/]+)/(view|raw)/([a-fA-F0-9]+)(?:[?#].*)?$");

	private static final Pattern PATRON_GIST = Pattern.compile("^(?:https?://)?gist\\.githubusercontent\\.com/.*$");

	private static final class ResolucionURL {
		final String original;
		final String directa;
		final String tipo;
		final String id;
		final boolean gzip;

		ResolucionURL(String original, String directa, String tipo, String id, boolean gzip) {
			this.original = original;
			this.directa = directa;
			this.tipo = tipo;
			this.id = id;
			this.gzip = gzip;
		}
	}

	private DescargarRegistors() {
	}

	/**
	 * Descarga y devuelve el contenido de múltiples registros, guardándolos también
	 * en la carpeta indicada.
	 */
	public static Map<String, String> descargarRegistros(List<String> urls, File carpetaDestino) throws IOException {
		Map<String, String> resultados = new HashMap<String, String>();

		if (carpetaDestino == null) {
			throw new IOException("Carpeta de destino nula");
		}

		if (!carpetaDestino.exists() && !carpetaDestino.mkdirs() && !carpetaDestino.isDirectory()) {
			throw new IOException("No se pudo crear la carpeta: " + carpetaDestino);
		}

		if (urls == null) {
			return resultados;
		}

		for (String url : urls) {
			try {
				byte[] datos = descargarBytesLog(url);
				String contenido = new String(datos, StandardCharsets.UTF_8);
				String nombre = obtenerNombreDeURL(url);
				File destino = archivoUnico(carpetaDestino, nombre);
				escribirBytes(destino, datos);
				resultados.put(destino.getName(), contenido);
			} catch (IOException e) {
				System.err.println("Error descargando " + url + ": " + e.getMessage());
			}
		}

		return resultados;
	}

	/**
	 * Convierte un URL conocido a su endpoint raw/directo.
	 */
	private static String procesarURL(String url) {
		ResolucionURL r = resolver(url);
		return r == null ? normalizarHttp(url) : r.directa;
	}

	/**
	 * Lee un log/paste reconocido y devuelve su texto UTF-8.
	 */
	public static String leerRegistro(String url) throws IOException {
		return new String(descargarBytesLog(url), StandardCharsets.UTF_8);
	}

	/**
	 * Alias equivalente al PasteReader de referencia.
	 */
	public static String read(String url) throws IOException {
		if (!esURLSoportada(url)) {
			return null;
		}
		return leerRegistro(url);
	}

	private static byte[] descargarBytesLog(String url) throws IOException {
		ResolucionURL r = resolver(url);

		String directa;
		boolean gzip;

		if (r != null) {
			directa = r.directa;
			gzip = r.gzip;
		} else {
			directa = normalizarHttp(url);
			gzip = false;
		}

		if (directa == null || directa.isEmpty()) {
			throw new IOException("URL inválida: " + url);
		}

		byte[] datos = descargarBytesHTTP(directa);

		/*
		 * CD Paste y SecureLogger entregan un archivo GZIP cuyo contenido descomprimido
		 * es el propio texto del log. No hay TAR interno.
		 */
		if (gzip) {
			datos = descomprimirGzipSiNecesario(datos);
		}

		return datos;
	}

	private static byte[] descargarBytesHTTP(String urlString) throws IOException {
		HttpURLConnection conn = null;

		try {
			URL url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(TIMEOUT_CONEXION_MS);
			conn.setReadTimeout(TIMEOUT_LECTURA_MS);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("User-Agent", "CrashDetector-PasteReader/1.0");
			conn.setRequestProperty("Accept", "text/plain,text/*,application/octet-stream,*/*;q=0.2");

			int code = conn.getResponseCode();
			if (code < 200 || code >= 300) {
				throw new IOException("HTTP " + code);
			}

			try (InputStream in = conn.getInputStream()) {
				return leerBytesLimitado(in, LIMITE_DESCARGA_BYTES);
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	private static byte[] leerBytesLimitado(InputStream in, int limite) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int total = 0;
		int n;

		while ((n = in.read(buffer)) != -1) {
			if (n <= 0) {
				continue;
			}

			total += n;
			if (total > limite) {
				throw new IOException("El registro supera el límite de descarga");
			}

			out.write(buffer, 0, n);
		}

		return out.toByteArray();
	}

	private static byte[] descomprimirGzipSiNecesario(byte[] datos) throws IOException {
		if (datos == null || datos.length < 2) {
			return datos == null ? new byte[0] : datos;
		}

		boolean tieneCabeceraGzip = (datos[0] & 0xff) == 0x1f && (datos[1] & 0xff) == 0x8b;
		if (!tieneCabeceraGzip) {
			/*
			 * Algunos proxies pueden entregar el cuerpo ya descomprimido.
			 */
			return datos;
		}

		try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(datos))) {
			return leerBytesLimitado(gzip, LIMITE_DESCARGA_BYTES);
		}
	}

	private static ResolucionURL resolver(String url) {
		String u = limpiarURL(url);
		if (u.isEmpty() || esURLInformeColeccion(u)) {
			return null;
		}

		Matcher m;

		m = PATRON_MCLOGS.matcher(u);
		if (m.matches()) {
			return new ResolucionURL(u, "https://api.mclo.gs/1/raw/" + m.group(1), "mclo.gs", m.group(1), false);
		}

		m = PATRON_GNOMEBOT.matcher(u);
		if (m.matches()) {
			return new ResolucionURL(u, "https://api.mclo.gs/1/raw/" + m.group(1), "mclo.gs", m.group(1), false);
		}

		m = PATRON_KOSTROMDAN.matcher(u);
		if (m.matches()) {
			return new ResolucionURL(u, "https://api.mclo.gs/1/raw/" + m.group(1), "mclo.gs", m.group(1), false);
		}

		m = PATRON_KDAN.matcher(u);
		if (m.matches()) {
			return new ResolucionURL(u, "https://api.mclo.gs/1/raw/" + m.group(1), "mclo.gs", m.group(1), false);
		}

		m = PATRON_PASTES_DEV.matcher(u);
		if (m.matches()) {
			return new ResolucionURL(u, "https://api.pastes.dev/" + m.group(1), "pastes.dev", m.group(1), false);
		}

		m = PATRON_BYTEBIN.matcher(u);
		if (m.matches()) {
			return new ResolucionURL(u, "https://bytebin.lucko.me/" + m.group(1), "bytebin", m.group(1), false);
		}

		m = PATRON_CRASHDETECTOR.matcher(u);
		if (m.matches()) {
			String id = m.group(1);
			return new ResolucionURL(u,
					"https://asbestosstar.egoism.jp/crash_detector/paste/endpoint.php/logs/" + id + ".gz",
					"crashdetector", id, true);
		}

		m = PATRON_MMD.matcher(u);
		if (m.matches()) {
			String host = m.group(1);
			String id = m.group(2);
			String directa = "centos.org".equalsIgnoreCase(host) ? "https://paste.centos.org/view/raw/" + id
					: "https://paste.mikumikudance.jp/view/raw/" + id;
			return new ResolucionURL(u, directa, "mmd", id, false);
		}

		m = PATRON_SECURELOGGER.matcher(u);
		if (m.matches()) {
			String host = m.group(1);
			String id = m.group(2);
			return new ResolucionURL(u, "https://" + host + "/files/" + id + ".tar.gz", "securelogger", id, true);
		}

		m = PATRON_STIKKED.matcher(u);
		if (m.matches()) {
			return new ResolucionURL(u, m.group(1) + "/raw/" + m.group(3), "stikked", m.group(3), false);
		}

		m = PATRON_GIST.matcher(u);
		if (m.matches()) {
			String id = ultimoSegmento(u);
			return new ResolucionURL(u, asegurarEsquema(u), "gist.github.com", id, false);
		}

		return null;
	}

	private static String limpiarURL(String url) {
		if (url == null) {
			return "";
		}

		String u = url.trim();

		while (u.startsWith("<") && u.endsWith(">") && u.length() > 2) {
			u = u.substring(1, u.length() - 1).trim();
		}

		while (!u.isEmpty()) {
			char c = u.charAt(u.length() - 1);
			if (c == '.' || c == ',' || c == ';' || c == ')' || c == ']' || c == '}' || c == '"' || c == '\'') {
				u = u.substring(0, u.length() - 1);
			} else {
				break;
			}
		}

		return u;
	}

	private static String asegurarEsquema(String url) {
		if (url == null || url.isEmpty()) {
			return "";
		}
		return url.startsWith("http://") || url.startsWith("https://") ? url : "https://" + url;
	}

	private static String normalizarHttp(String url) {
		String u = limpiarURL(url);
		if (u.isEmpty()) {
			return "";
		}

		u = asegurarEsquema(u);

		try {
			URL parsed = new URL(u);
			String protocolo = parsed.getProtocol().toLowerCase(Locale.ROOT);
			if (!"http".equals(protocolo) && !"https".equals(protocolo)) {
				return "";
			}
			return parsed.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public static boolean esURLSoportada(String url) {
		return resolver(url) != null;
	}

	/**
	 * mclogsarray es una colección/informe de Crash Assistant, no un log.
	 */
	public static boolean esURLInformeColeccion(String url) {
		String u = limpiarURL(url);
		return !u.isEmpty() && PATRON_KOSTROMDAN_ARRAY.matcher(u).matches();
	}

	/**
	 * Conservado para compatibilidad con EnlanceMD de los parches anteriores.
	 */
	public static boolean esURLSecureLoggerArchivo(String url) {
		String u = limpiarURL(url);
		return !u.isEmpty() && PATRON_SECURELOGGER.matcher(u).matches();
	}

	public static boolean pareceRegistro(String url) {
		if (url == null) {
			return false;
		}

		String u = limpiarURL(url).toLowerCase(Locale.ROOT);

		if (esURLInformeColeccion(u)) {
			return false;
		}

		if (esURLSoportada(u)) {
			return true;
		}

		return u.endsWith(".log") || u.endsWith(".txt") || u.contains("launcher_log") || u.contains("stderr_stream")
				|| u.contains("crash-");
	}

	public static String extraerIDRegistro(String url) {
		ResolucionURL r = resolver(url);
		if (r != null && r.id != null && !r.id.isEmpty()) {
			return r.id;
		}
		return null;
	}

	public static boolean isMcLogsInstance(String url) {
		String u = limpiarURL(url);
		return PATRON_MCLOGS.matcher(u).matches() || PATRON_GNOMEBOT.matcher(u).matches()
				|| PATRON_KOSTROMDAN.matcher(u).matches() || PATRON_KDAN.matcher(u).matches();
	}

	public static String getMcLogsId(String url) {
		String u = limpiarURL(url);
		Matcher m;

		m = PATRON_MCLOGS.matcher(u);
		if (m.matches())
			return m.group(1);

		m = PATRON_GNOMEBOT.matcher(u);
		if (m.matches())
			return m.group(1);

		m = PATRON_KOSTROMDAN.matcher(u);
		if (m.matches())
			return m.group(1);

		m = PATRON_KDAN.matcher(u);
		if (m.matches())
			return m.group(1);

		return null;
	}

	public static String obtenerTipoRegistro(String url) {
		if (esURLInformeColeccion(url)) {
			return "informe";
		}

		ResolucionURL r = resolver(url);
		return r == null ? "desconocido" : r.tipo;
	}

	public static String convertirARaw(String url) {
		ResolucionURL r = resolver(url);
		return r == null ? url : r.directa;
	}

	public static boolean esRegistroMinecraft(String contenido) {
		if (contenido == null) {
			return false;
		}

		return contenido.contains("Minecraft") || contenido.contains("ModLauncher") || contenido.contains("Forge")
				|| contenido.contains("Fabric") || contenido.contains("java.lang.Throwable");
	}

	public static List<File> descargarYGuardarRegistros(List<String> urls, File carpetaDestino) throws IOException {
		List<File> archivos = new ArrayList<File>();

		if (carpetaDestino == null) {
			throw new IOException("Carpeta de destino nula");
		}

		if (!carpetaDestino.exists() && !carpetaDestino.mkdirs() && !carpetaDestino.isDirectory()) {
			throw new IOException("No se pudo crear la carpeta: " + carpetaDestino);
		}

		if (urls == null) {
			return archivos;
		}

		for (String url : urls) {
			try {
				File destino = archivoUnico(carpetaDestino, obtenerNombreDeURL(url));
				descargarArchivo(url, destino);
				archivos.add(destino);
			} catch (IOException e) {
				System.err.println("Error descargando " + url + ": " + e.getMessage());
			}
		}

		return archivos;
	}

	public static Map<String, String> descargarRegistrosPorTipo(List<String> urls, File carpetaDestino,
			String tipoFiltro) throws IOException {
		Map<String, String> resultado = new HashMap<String, String>();

		if (urls == null) {
			return resultado;
		}

		for (String url : urls) {
			String tipo = obtenerTipoRegistro(url);
			if (!"desconocido".equals(tipoFiltro) && !tipoFiltro.equals(tipo)) {
				continue;
			}

			byte[] datos = descargarBytesLog(url);
			String nombre = obtenerNombreDeURL(url);
			File destino = archivoUnico(carpetaDestino, nombre);
			escribirBytes(destino, datos);
			resultado.put(destino.getName(), new String(datos, StandardCharsets.UTF_8));
		}

		return resultado;
	}

	public static List<Consola> crearConsolasDesdeRegistros(List<String> urls, File carpetaDestino) throws IOException {
		List<Consola> consolas = new ArrayList<Consola>();

		for (File archivo : descargarYGuardarRegistros(urls, carpetaDestino)) {
			try {
				String contenido = MonitorDePID.leer_archivo(archivo.toPath());
				Consola consola = new Consola(archivo.toPath());
				consola.finalizarContenidoInyectado(contenido);
				consolas.add(consola);
			} catch (IOException e) {
				System.err.println("Error procesando " + archivo.getName() + ": " + e.getMessage());
			}
		}

		return consolas;
	}

	/**
	 * Descarga un único log. Si el servicio usa un endpoint raw especial, se
	 * normaliza. Si el contenido es GZIP de CD/SecureLogger, el archivo destino
	 * recibe el texto YA DESCOMPRIMIDO.
	 */
	public static void descargarArchivo(String url, File destino) throws IOException {
		if (destino == null) {
			throw new IOException("Archivo de destino nulo");
		}

		File padre = destino.getParentFile();
		if (padre != null && !padre.exists()) {
			padre.mkdirs();
		}

		byte[] datos = descargarBytesLog(url);
		escribirBytes(destino, datos);
	}

	private static void escribirBytes(File archivo, byte[] datos) throws IOException {
		try (FileOutputStream out = new FileOutputStream(archivo)) {
			out.write(datos);
		}
	}

	private static String obtenerNombreDeURL(String url) {
		ResolucionURL r = resolver(url);

		if (r != null) {
			String id = (r.id == null || r.id.isEmpty()) ? "log" : r.id;
			return sanitizarNombre("registro_" + r.tipo.replace('.', '_') + "_" + id + ".log");
		}

		String u = normalizarHttp(url);
		String segmento = ultimoSegmento(u);
		if (segmento != null && segmento.matches("(?i).+\\.(log|txt)$")) {
			return sanitizarNombre(segmento);
		}

		return "registro.log";
	}

	private static String ultimoSegmento(String url) {
		if (url == null || url.isEmpty()) {
			return "";
		}

		int q = url.indexOf('?');
		String limpio = q >= 0 ? url.substring(0, q) : url;

		int hash = limpio.indexOf('#');
		if (hash >= 0) {
			limpio = limpio.substring(0, hash);
		}

		int slash = limpio.lastIndexOf('/');
		return slash >= 0 && slash + 1 < limpio.length() ? limpio.substring(slash + 1) : limpio;
	}

	private static String sanitizarNombre(String nombre) {
		if (nombre == null || nombre.isEmpty()) {
			return "registro.log";
		}
		return nombre.replaceAll("[^a-zA-Z0-9._-]", "_");
	}

	private static File archivoUnico(File carpeta, String nombre) {
		File f = new File(carpeta, nombre);
		if (!f.exists()) {
			return f;
		}

		String base = nombre;
		String ext = "";

		int punto = nombre.lastIndexOf('.');
		if (punto > 0) {
			base = nombre.substring(0, punto);
			ext = nombre.substring(punto);
		}

		int i = 2;
		while (f.exists()) {
			f = new File(carpeta, base + "_" + i + ext);
			i++;
		}

		return f;
	}
}
