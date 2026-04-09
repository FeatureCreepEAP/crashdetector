package com.asbestosstar.crashdetector.guard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

/**
 * Busca servidores problemáticos dentro de ./servers.dat usando la lista de
 * TLauncher.
 *
 * Reglas: - Todo queda dentro de esta clase. - El JSON se guarda en
 * Statics.carpeta. - Se usa el archivo servers.dat del directorio actual. -
 * Solo se comprueba la raíz del servidor problemático dentro del contenido del
 * servers.dat.
 */
public class ServidoresProblematicos {

	/** URL remota del JSON de TLauncher. */
	private static final String URL_JSON = "https://repo.tlauncher.org/update/downloads/configs/inner_servers-1.2.json";

	/** Archivo local cacheado dentro de Statics.carpeta. */
	private static final File ARCHIVO_JSON_LOCAL = Statics.carpeta.resolve("inner_servers-1.2.json").toFile();

	/** Archivo servers.dat del directorio actual. */
	private static final File ARCHIVO_SERVERS_DAT = new File("servers.dat");

	/**
	 * Devuelve una lista con todas las raíces de servidores problemáticos
	 * encontradas dentro de ./servers.dat.
	 *
	 * @param actualizarDefiniciones si es true, fuerza la descarga del JSON remoto
	 *                               y reemplaza la copia local; si es false, usa la
	 *                               copia local existente o la descarga si no
	 *                               existe
	 * @return lista de raíces encontradas en servers.dat
	 */
	public static List<String> obtenerServidoresProblematicos(boolean actualizarDefiniciones) {
		List<String> encontrados = new ArrayList<>();

		try {
			if (!ARCHIVO_SERVERS_DAT.isFile()) {
				return encontrados;
			}

			File archivoJson = obtenerJsonLocal(actualizarDefiniciones);
			if (archivoJson == null || !archivoJson.isFile()) {
				return encontrados;
			}

			String textoJson = new String(leerBytes(archivoJson), StandardCharsets.UTF_8);
			Nodo raiz = Json.leer(textoJson);

			Set<String> raicesProblematicas = extraerRaicesProblematicas(raiz);
			if (raicesProblematicas.isEmpty()) {
				return encontrados;
			}

			String contenidoServersDat = new String(leerBytes(ARCHIVO_SERVERS_DAT), StandardCharsets.ISO_8859_1)
					.toLowerCase(Locale.ROOT);

			for (String raizProblematica : raicesProblematicas) {
				if (raizProblematica != null && !raizProblematica.isEmpty()
						&& contenidoServersDat.contains(raizProblematica)) {
					encontrados.add(raizProblematica);
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return encontrados;
	}

	/**
	 * Método de compatibilidad.
	 *
	 * Usa la copia local del JSON si existe. Si no existe, la descarga.
	 *
	 * @return lista de raíces encontradas en servers.dat
	 */
	public static List<String> obtenerServidoresProblematicos() {
		return obtenerServidoresProblematicos(false);
	}

	/**
	 * Indica si el archivo local de definiciones ya existe.
	 *
	 * @return true si el JSON local existe y es un archivo; false en caso contrario
	 */
	public static boolean existenDefiniciones() {
		return ARCHIVO_JSON_LOCAL.isFile();
	}

	/**
	 * Devuelve el JSON local.
	 *
	 * Si actualizarDefiniciones es true, fuerza una nueva descarga y sobrescribe el
	 * archivo local.
	 *
	 * Si actualizarDefiniciones es false y el archivo ya existe, se reutiliza.
	 *
	 * @param actualizarDefiniciones indica si debe forzarse la actualización
	 * @return archivo JSON local o null si no pudo obtenerse
	 */
	private static File obtenerJsonLocal(boolean actualizarDefiniciones) throws Exception {
		if (!actualizarDefiniciones && ARCHIVO_JSON_LOCAL.isFile()) {
			return ARCHIVO_JSON_LOCAL;
		}

		File carpeta = ARCHIVO_JSON_LOCAL.getParentFile();
		if (carpeta != null && !carpeta.exists()) {
			carpeta.mkdirs();
		}

		byte[] datos = descargarBytes(URL_JSON);
		if (datos == null || datos.length == 0) {
			return null;
		}

		try (FileOutputStream fos = new FileOutputStream(ARCHIVO_JSON_LOCAL)) {
			fos.write(datos);
		}

		return ARCHIVO_JSON_LOCAL;
	}

	/**
	 * Extrae las raíces problemáticas del JSON.
	 *
	 * Se toman: - removedServers[] - clientChangedAddress[].oldAddress
	 *
	 * Solo se guarda la raíz, por ejemplo: - "mc.ejemplo.com:25565" ->
	 * "mc.ejemplo.com" - "mc.ejemplo.com" -> "mc.ejemplo.com"
	 */
	private static Set<String> extraerRaicesProblematicas(Nodo raiz) {
		Set<String> resultado = new LinkedHashSet<>();

		if (raiz == null || !raiz.esObjeto()) {
			return resultado;
		}

		Nodo removedServers = raiz.obtener("removedServers");
		if (removedServers != null && removedServers.esArreglo()) {
			for (int i = 0; i < removedServers.tamano(); i++) {
				String direccion = removedServers.en(i).comoCadena();
				agregarRaiz(resultado, direccion);
			}
		}

		Nodo clientChangedAddress = raiz.obtener("clientChangedAddress");
		if (clientChangedAddress != null && clientChangedAddress.esArreglo()) {
			for (int i = 0; i < clientChangedAddress.tamano(); i++) {
				Nodo cambio = clientChangedAddress.en(i);
				if (cambio != null && cambio.esObjeto()) {
					Nodo nodoOldAddress = cambio.obtener("oldAddress");
					if (nodoOldAddress != null) {
						String oldAddress = nodoOldAddress.comoCadena();
						agregarRaiz(resultado, oldAddress);
					}
				}
			}
		}

		return resultado;
	}

	/**
	 * Agrega únicamente la raíz de una dirección.
	 */
	private static void agregarRaiz(Set<String> resultado, String direccion) {
		String raiz = extraerRaiz(direccion);
		if (!raiz.isEmpty()) {
			resultado.add(raiz);
		}
	}

	/**
	 * Extrae la raíz de una dirección de servidor.
	 *
	 * Ejemplos: - "mc.ejemplo.com:25565" -> "mc.ejemplo.com" - "mc.ejemplo.com" ->
	 * "mc.ejemplo.com" - "146.59.181.68:25584" -> "146.59.181.68"
	 *
	 * No cambia prefijos como mc., play., etc. Solo elimina el puerto si existe.
	 */
	private static String extraerRaiz(String direccion) {
		if (direccion == null) {
			return "";
		}

		String s = direccion.trim().toLowerCase(Locale.ROOT);
		if (s.isEmpty()) {
			return "";
		}

		int ultimoDosPuntos = s.lastIndexOf(':');
		if (ultimoDosPuntos > 0) {
			String posiblePuerto = s.substring(ultimoDosPuntos + 1);
			if (esNumero(posiblePuerto)) {
				s = s.substring(0, ultimoDosPuntos);
			}
		}

		return s;
	}

	/**
	 * Indica si una cadena contiene solo números.
	 */
	private static boolean esNumero(String texto) {
		if (texto == null || texto.isEmpty()) {
			return false;
		}

		for (int i = 0; i < texto.length(); i++) {
			if (!Character.isDigit(texto.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Descarga todos los bytes de una URL.
	 */
	private static byte[] descargarBytes(String url) throws Exception {
		try (InputStream in = new URL(url).openStream()) {
			return leerBytes(in);
		}
	}

	/**
	 * Lee todos los bytes de un archivo.
	 */
	private static byte[] leerBytes(File archivo) throws Exception {
		try (InputStream in = new FileInputStream(archivo)) {
			return leerBytes(in);
		}
	}

	/**
	 * Lee todos los bytes de un InputStream.
	 */
	private static byte[] leerBytes(InputStream in) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int leidos;

		while ((leidos = in.read(buffer)) != -1) {
			out.write(buffer, 0, leidos);
		}

		return out.toByteArray();
	}
}