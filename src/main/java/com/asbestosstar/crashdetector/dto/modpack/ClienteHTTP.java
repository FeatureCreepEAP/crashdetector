package com.asbestosstar.crashdetector.dto.modpack;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.config.json.Json;

/**
 * Cliente HTTP centralizado para el importador de modpacks.
 *
 * <p>Proporciona:</p>
 * <ul>
 *   <li>Reutilización de conexiones por host mediante keep-alive (sin llamar disconnect())</li>
 *   <li>Descargas con streaming directo a disco (sin buffer completo en memoria)</li>
 *   <li>Caché local de respuestas API con TTL configurable</li>
 * </ul>
 *
 * <p>Compatible con Java 8+.</p>
 */
public class ClienteHTTP {

	/*
	 * Tiempo de vida de caché local para respuestas API en milisegundos.
	 * 1 hora por defecto: los metadatos de mods no cambian frecuentemente.
	 */
	private static final long CACHE_TTL_MS = 3600_000L;

	/*
	 * Tamaño de buffer para streaming de descargas: 64KB.
	 */
	private static final int BUFFER_DESCARGA = 65536;

	/*
	 * Timeout de conexión en milisegundos.
	 */
	private static final int TIMEOUT_CONEXION = 20000;

	/*
	 * Timeout de lectura para JSON (API calls).
	 */
	private static final int TIMEOUT_LECTURA_JSON = 30000;

	/*
	 * Timeout de lectura para descargas de archivos.
	 */
	private static final int TIMEOUT_LECTURA_DESCARGA = 120000;

	/*
	 * Caché de respuestas API: clave = URL, valor = contenido JSON.
	 * Se limpia automáticamente cuando excede el TTL.
	 */
	private static final Map<String, CacheEntry> cacheRespuestas = new ConcurrentHashMap<String, CacheEntry>();

	/*
	 * Directorio de caché local. Se inicializa lazily.
	 */
	private static volatile Path directorioCache;

	/**
	 * Entrada de caché con timestamp de creación.
	 */
	private static class CacheEntry {
		final String contenido;
		final long timestamp;

		CacheEntry(String contenido) {
			this.contenido = contenido;
			this.timestamp = System.currentTimeMillis();
		}

		boolean esValido() {
			return (System.currentTimeMillis() - timestamp) < CACHE_TTL_MS;
		}
	}

	/**
	 * Obtiene el directorio de caché local, creándolo si es necesario.
	 */
	private static Path obtenerDirectorioCache() throws IOException {
		if (directorioCache == null) {
			synchronized (ClienteHTTP.class) {
				if (directorioCache == null) {
					String userHome = System.getProperty("user.home", ".");
					Path base = Paths.get(userHome, ".crashdetector", "modpack-cache");
					Files.createDirectories(base);
					directorioCache = base;
				}
			}
		}
		return directorioCache;
	}

	/**
	 * Obtiene el archivo de caché local para una URL dada.
	 */
	private static Path obtenerArchivoCache(String url) throws IOException {
		String nombre = hashUrl(url);
		return obtenerDirectorioCache().resolve(nombre);
	}

	/**
	 * Genera un nombre de archivo seguro a partir de una URL.
	 */
	private static String hashUrl(String url) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(url.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString() + ".json";
		} catch (Throwable t) {
			return System.nanoTime() + ".json";
		}
	}

	/**
	 * Realiza una solicitud GET y devuelve el resultado como Json.Nodo.
	 *
	 * <p>Utiliza caché en memoria y caché local para evitar llamadas HTTP repetidas.</p>
	 *
	 * @param url la URL a solicitar
	 * @param headers encabezados adicionales (puede ser null)
	 * @return el resultado parseado como JSON
	 * @throws IOException si la solicitud falla
	 */
	public static Json.Nodo getJson(String url, Map<String, String> headers) throws IOException {
		String contenido = getJsonRaw(url, headers);
		if (contenido == null || contenido.trim().isEmpty()) {
			throw new IOException("Respuesta vacia desde " + url);
		}
		return Json.leer(contenido);
	}

	/**
	 * Realiza una solicitud GET y devuelve el resultado como String sin parsear.
	 */
	public static String getJsonRaw(String url, Map<String, String> headers) throws IOException {

		// 1. Intentar caché en memoria
		CacheEntry cached = cacheRespuestas.get(url);
		if (cached != null && cached.esValido()) {
			return cached.contenido;
		} else if (cached != null) {
			// Entrada expirada, eliminar
			cacheRespuestas.remove(url, cached);
		}

		// 2. Intentar caché local
		try {
			Path archivoCache = obtenerArchivoCache(url);
			if (Files.exists(archivoCache)) {
				long modificado = Files.getLastModifiedTime(archivoCache).toMillis();
				if ((System.currentTimeMillis() - modificado) < CACHE_TTL_MS) {
					String contenido = new String(Files.readAllBytes(archivoCache), "UTF-8");
					// Actualizar caché en memoria
					cacheRespuestas.put(url, new CacheEntry(contenido));
					return contenido;
				}
			}
		} catch (Throwable t) {
			// Ignorar errores de caché local
		}

		// 3. Realizar la solicitud HTTP
		String contenido = realizarGet(url, headers, TIMEOUT_LECTURA_JSON);

		// 4. Almacenar en caché
		try {
			cacheRespuestas.put(url, new CacheEntry(contenido));
			Path archivoCache = obtenerArchivoCache(url);
			Files.write(archivoCache, contenido.getBytes("UTF-8"));
		} catch (Throwable t) {
			// Ignorar errores de caché
		}

		return contenido;
	}

	/**
	 * Realiza una solicitud GET simple con headers predeterminados.
	 */
	public static Json.Nodo getJson(String url) throws IOException {
		return getJson(url, null);
	}

	/**
	 * Realiza una solicitud POST con cuerpo JSON.
	 *
	 * @param url la URL a solicitar
	 * @param cuerpo el cuerpo JSON como String
	 * @param headers encabezados adicionales (puede ser null)
	 * @return el resultado parseado como JSON
	 * @throws IOException si la solicitud falla
	 */
	public static Json.Nodo postJson(String url, String cuerpo, Map<String, String> headers) throws IOException {
		HttpURLConnection con = crearConexion(url, "POST", headers);
		con.setDoOutput(true);
		con.setRequestProperty("Content-Type", "application/json");

		try {
			try (OutputStream os = con.getOutputStream()) {
				os.write(cuerpo.getBytes("UTF-8"));
			}

			int codigo = con.getResponseCode();
			InputStream is = codigo >= 200 && codigo < 300 ? con.getInputStream() : con.getErrorStream();
			String respuesta = leerString(is);

			if (codigo < 200 || codigo >= 300) {
				throw new IOException("HTTP " + codigo + ": " + respuesta);
			}

			return Json.leer(respuesta);
		} finally {
			// No llamar disconnect() para permitir reutilización de conexión
			// La conexión se cierra automáticamente por timeout de keep-alive
		}
	}

	/**
	 * Descarga un archivo directamente a disco con streaming (sin buffer completo en memoria).
	 *
	 * @param url la URL del archivo a descargar
	 * @param destino la ruta de destino en disco
	 * @throws IOException si la descarga falla
	 */
	public static void descargarArchivo(String url, Path destino) throws IOException {
		if (destino.getParent() != null) {
			Files.createDirectories(destino.getParent());
		}

		HttpURLConnection con = crearConexion(url, "GET", null);
		con.setReadTimeout(TIMEOUT_LECTURA_DESCARGA);

		try {
			int codigo = con.getResponseCode();
			if (codigo < 200 || codigo >= 300) {
				throw new IOException("No se pudo descargar " + url + " HTTP " + codigo);
			}

			// Streaming directo: InputStream -> FileOutputStream sin buffer intermedio en memoria
			try (InputStream is = new BufferedInputStream(con.getInputStream(), BUFFER_DESCARGA);
					OutputStream os = new FileOutputStream(destino.toFile())) {

				byte[] buffer = new byte[BUFFER_DESCARGA];
				int leidos;
				while ((leidos = is.read(buffer)) != -1) {
					os.write(buffer, 0, leidos);
				}
			}
		} finally {
			// No llamar disconnect() para permitir reutilización de conexión
		}
	}

	/**
	 * Descarga un archivo y devuelve los bytes (para archivos pequeños que ya se necesitan en memoria).
	 *
	 * @param url la URL del archivo a descargar
	 * @return los bytes del archivo
	 * @throws IOException si la descarga falla
	 */
	public static byte[] descargarBytes(String url) throws IOException {
		HttpURLConnection con = crearConexion(url, "GET", null);
		con.setReadTimeout(TIMEOUT_LECTURA_DESCARGA);

		try {
			int codigo = con.getResponseCode();
			if (codigo < 200 || codigo >= 300) {
				throw new IOException("No se pudo descargar " + url + " HTTP " + codigo);
			}

			return leerBytes(con.getInputStream());
		} finally {
			// No llamar disconnect() para permitir reutilización de conexión
		}
	}

	/**
	 * Crea y configura una HttpURLConnection con settings optimizados para reutilización.
	 */
	private static HttpURLConnection crearConexion(String url, String metodo, Map<String, String> headers)
			throws IOException {

		URL u = new URL(url);
		HttpURLConnection con = (HttpURLConnection) u.openConnection();
		con.setRequestMethod(metodo);
		con.setConnectTimeout(TIMEOUT_CONEXION);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("User-Agent", "CrashDetectorMC/1.0");

		// Habilitar keep-alive para reutilización de conexiones
		con.setRequestProperty("Connection", "keep-alive");
		con.setInstanceFollowRedirects(true);

		if (headers != null) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				con.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}

		return con;
	}

	/**
	 * Realiza una solicitud GET con headers personalizados.
	 */
	private static String realizarGet(String url, Map<String, String> headers, int readTimeout) throws IOException {
		HttpURLConnection con = crearConexion(url, "GET", headers);
		con.setReadTimeout(readTimeout);

		try {
			int codigo = con.getResponseCode();
			InputStream is = codigo >= 200 && codigo < 300 ? con.getInputStream() : con.getErrorStream();
			String respuesta = leerString(is);

			if (codigo < 200 || codigo >= 300) {
				throw new IOException("HTTP " + codigo + ": " + respuesta);
			}

			return respuesta;
		} finally {
			// No llamar disconnect() para permitir reutilización de conexión
		}
	}

	/**
	 * Lee un InputStream completo como String UTF-8.
	 */
	private static String leerString(InputStream is) throws IOException {
		if (is == null) {
			return "";
		}
		byte[] bytes = leerBytes(is);
		return new String(bytes, "UTF-8");
	}

	/**
	 * Lee un InputStream completo como byte[].
	 */
	private static byte[] leerBytes(InputStream is) throws IOException {
		if (is == null) {
			return new byte[0];
		}

		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		byte[] buffer = new byte[BUFFER_DESCARGA];
		int leidos;

		while ((leidos = is.read(buffer)) != -1) {
			baos.write(buffer, 0, leidos);
		}

		return baos.toByteArray();
	}

	/**
	 * Limpia la caché en memoria. Útil para forzar actualización de metadatos.
	 */
	public static void limpiarCache() {
		cacheRespuestas.clear();
	}

	/**
	 * Limpia la caché local en disco.
	 */
	public static void limpiarCacheLocal() {
		try {
			Path dir = obtenerDirectorioCache();
			if (Files.exists(dir)) {
				try (java.nio.file.DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.json")) {
					for (Path p : stream) {
						Files.deleteIfExists(p);
					}
				}
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	/**
	 * Limpia entradas de caché expiradas. Llamar periódicamente para liberar memoria.
	 */
	public static void limpiarCacheExpirado() {
		cacheRespuestas.entrySet().removeIf(entry -> !entry.getValue().esValido());
	}

	/**
	 * Fuerza una solicitud HTTP sin usar caché.
	 */
	public static Json.Nodo getJsonSinCache(String url, Map<String, String> headers) throws IOException {
		String contenido = realizarGet(url, headers, TIMEOUT_LECTURA_JSON);
		return Json.leer(contenido);
	}

	/**
	 * Fuerza una solicitud POST sin caché.
	 */
	public static Json.Nodo postJsonSinCache(String url, String cuerpo, Map<String, String> headers)
			throws IOException {
		return postJson(url, cuerpo, headers);
	}
}
