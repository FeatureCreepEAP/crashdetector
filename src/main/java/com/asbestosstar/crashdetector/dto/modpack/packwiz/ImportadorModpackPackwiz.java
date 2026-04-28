package com.asbestosstar.crashdetector.dto.modpack.packwiz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.dto.modpack.importar.ImportadorModpack;
import com.asbestosstar.crashdetector.dto.modpack.importar.InfoEntradaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.PoliticaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResolutorConflictosImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;
import com.asbestosstar.crashdetector.dto.modpack.tlmods.ActualizadorTLauncherAdditional;

public class ImportadorModpackPackwiz implements ImportadorModpack {

	private static final String NOMBRE_PACK = "pack.toml";
	private static final String NOMBRE_INDEX = "index.toml";
	private static final String NOMBRE_TLAUNCHER_ADDITIONAL = "TLauncherAdditional.json";

	@Override
	public String obtenerNombreImportador() {
		return "Packwiz";
	}

	@Override
	public String obtenerExtensionModpack() {
		return "zip";
	}

	@Override
	public boolean puedeImportar(Path archivoModpack) {
		return archivoModpack != null && archivoModpack.getFileName() != null
				&& archivoModpack.getFileName().toString().toLowerCase().endsWith(".zip");
	}

	@Override
	public ResultadoImportacion importarModpack(Path archivoModpack, Path carpetaDestino, PoliticaImportacion politica)
			throws IOException {

		return importar(archivoModpack, carpetaDestino, politica, null);
	}

	public ResultadoImportacion importar(Path archivoModpack, Path carpetaDestino, PoliticaImportacion politica,
			ResolutorConflictosImportacion resolutor) throws IOException {

		if (archivoModpack == null || !Files.isRegularFile(archivoModpack)) {
			throw new IOException("El modpack Packwiz no existe: " + archivoModpack);
		}

		if (carpetaDestino == null) {
			carpetaDestino = java.nio.file.Paths.get(".").toAbsolutePath().normalize();
		}

		if (politica == null) {
			politica = new PoliticaImportacion();
		}

		carpetaDestino = carpetaDestino.toAbsolutePath().normalize();

		ResultadoImportacion total = new ResultadoImportacion();

		Map<String, EntradaZipLeida> entradas = leerEntradasZip(archivoModpack);

		EntradaZipLeida packEntrada = buscarEntrada(entradas, NOMBRE_PACK);
		if (packEntrada == null) {
			throw new IOException("El ZIP no contiene " + NOMBRE_PACK + ".");
		}

		Map<String, String> pack = parsearTomlPlano(new String(packEntrada.bytes, StandardCharsets.UTF_8));
		String indexPath = valor(pack, "index.file", NOMBRE_INDEX);

		EntradaZipLeida indexEntrada = buscarEntrada(entradas, indexPath);
		if (indexEntrada == null) {
			throw new IOException("El ZIP no contiene el index de Packwiz: " + indexPath);
		}

		mezclar(total, instalarDesdeIndex(entradas, indexEntrada, carpetaDestino, politica));

		EntradaZipLeida tlaEntrada = buscarEntrada(entradas, NOMBRE_TLAUNCHER_ADDITIONAL);
		if (tlaEntrada != null) {
			Path archivoTLA = carpetaDestino.resolve(NOMBRE_TLAUNCHER_ADDITIONAL);
			InfoEntradaImportacion info = crearInfoSimple(NOMBRE_TLAUNCHER_ADDITIONAL);

			mezclar(total, instalarEntradaConConflictos(tlaEntrada.bytes, info, archivoTLA, politica));
		}

		actualizarTLauncherAdditionalDespuesDeImportar(carpetaDestino);

		return total;
	}

	private ResultadoImportacion instalarDesdeIndex(Map<String, EntradaZipLeida> entradas, EntradaZipLeida indexEntrada,
			Path carpetaDestino, PoliticaImportacion politica) throws IOException {

		ResultadoImportacion total = new ResultadoImportacion();

		String texto = new String(indexEntrada.bytes, StandardCharsets.UTF_8);
		String hashFormatIndex = "sha256";

		EntradaIndexPackwiz actual = null;

		for (String lineaOriginal : texto.split("\\r?\\n")) {
			String linea = limpiarComentario(lineaOriginal).trim();

			if (linea.isEmpty()) {
				continue;
			}

			if ("[[files]]".equals(linea)) {
				if (actual != null) {
					mezclar(total, instalarEntradaIndex(actual, entradas, carpetaDestino, politica));
				}

				actual = new EntradaIndexPackwiz();
				actual.hashFormat = hashFormatIndex;
				continue;
			}

			if (linea.startsWith("hash-format") && actual == null) {
				hashFormatIndex = leerValorToml(linea);
				continue;
			}

			if (actual == null) {
				continue;
			}

			if (linea.startsWith("file")) {
				actual.file = leerValorToml(linea);
			} else if (linea.startsWith("hash-format")) {
				actual.hashFormat = leerValorToml(linea);
			} else if (linea.startsWith("hash")) {
				actual.hash = leerValorToml(linea);
			} else if (linea.startsWith("metafile")) {
				actual.metafile = "true".equalsIgnoreCase(leerValorToml(linea));
			} else if (linea.startsWith("preserve")) {
				actual.preserve = "true".equalsIgnoreCase(leerValorToml(linea));
			}
		}

		if (actual != null) {
			mezclar(total, instalarEntradaIndex(actual, entradas, carpetaDestino, politica));
		}

		return total;
	}

	private ResultadoImportacion instalarEntradaIndex(EntradaIndexPackwiz entrada,
			Map<String, EntradaZipLeida> entradas, Path carpetaDestino, PoliticaImportacion politica)
			throws IOException {

		ResultadoImportacion total = new ResultadoImportacion();

		if (entrada.file == null || entrada.file.trim().isEmpty()) {
			return total;
		}

		if (!rutaSegura(entrada.file)) {
			total.saltados++;
			total.mensajes.add("Ruta insegura omitida desde index.toml: " + entrada.file);
			return total;
		}

		if (entrada.metafile) {
			EntradaZipLeida metaZip = buscarEntrada(entradas, entrada.file);

			if (metaZip == null) {
				total.saltados++;
				total.mensajes.add("Metafile Packwiz no encontrado en ZIP: " + entrada.file);
				return total;
			}

			mezclar(total, instalarMetafile(metaZip, entrada.file, carpetaDestino, politica));
			return total;
		}

		EntradaZipLeida archivoZip = buscarEntrada(entradas, entrada.file);

		if (archivoZip == null) {
			total.saltados++;
			total.mensajes.add("Archivo local de Packwiz no encontrado en ZIP: " + entrada.file);
			return total;
		}

		Path destino = carpetaDestino.resolve(entrada.file).normalize();

		if (!destino.toAbsolutePath().normalize().startsWith(carpetaDestino)) {
			total.saltados++;
			total.mensajes.add("Ruta intenta salir de carpeta destino: " + entrada.file);
			return total;
		}

		if (entrada.hash != null && !entrada.hash.trim().isEmpty()) {
			verificarHashSiExiste(archivoZip.bytes, entrada.hashFormat, entrada.hash, entrada.file);
		}

		InfoEntradaImportacion info = crearInfoSimple(entrada.file);
		info.fechaModificacion = archivoZip.fechaModificacion;
		info.tamanoDeclarado = archivoZip.bytes.length;
		info.sha1 = "sha1".equalsIgnoreCase(entrada.hashFormat) ? entrada.hash : "";

		mezclar(total, instalarEntradaConConflictos(archivoZip.bytes, info, destino, politica));

		return total;
	}

	private ResultadoImportacion instalarMetafile(EntradaZipLeida metaZip, String rutaMetafile, Path carpetaDestino,
			PoliticaImportacion politica) throws IOException {

		ResultadoImportacion total = new ResultadoImportacion();

		String texto = new String(metaZip.bytes, StandardCharsets.UTF_8);
		Map<String, String> toml = parsearTomlPlano(texto);

		String filename = valor(toml, "filename", "");
		String url = valor(toml, "download.url", "");
		String hashFormat = valor(toml, "download.hash-format", "sha1");
		String hash = valor(toml, "download.hash", "");

		if (filename.trim().isEmpty() || url.trim().isEmpty()) {
			total.saltados++;
			total.mensajes.add("Metafile Packwiz incompleto: " + rutaMetafile);
			return total;
		}

		String carpeta = carpetaDeMetafile(rutaMetafile);
		String rutaDestino = carpeta.trim().isEmpty() ? filename : carpeta + "/" + filename;

		if (!rutaSegura(rutaDestino)) {
			total.saltados++;
			total.mensajes.add("Ruta insegura desde metafile Packwiz: " + rutaDestino);
			return total;
		}

		Path destino = carpetaDestino.resolve(rutaDestino).normalize();

		if (!destino.toAbsolutePath().normalize().startsWith(carpetaDestino)) {
			total.saltados++;
			total.mensajes.add("Ruta intenta salir de carpeta destino: " + rutaDestino);
			return total;
		}

		byte[] bytes = descargarBytes(url);

		if (!hash.trim().isEmpty()) {
			verificarHashSiExiste(bytes, hashFormat, hash, rutaDestino);
		}

		InfoEntradaImportacion info = crearInfoSimple(rutaDestino);
		info.tamanoDeclarado = bytes.length;
		info.sha1 = "sha1".equalsIgnoreCase(hashFormat) ? hash : "";
		info.modrinthProjectId = valor(toml, "update.modrinth.mod-id", "");
		info.modrinthVersionId = valor(toml, "update.modrinth.version", "");
		info.curseForgeProjectId = valor(toml, "update.curseforge.project-id", "");
		info.curseForgeFileId = valor(toml, "update.curseforge.file-id", "");

		mezclar(total, instalarEntradaConConflictos(bytes, info, destino, politica));

		return total;
	}

	private static Map<String, String> parsearTomlPlano(String texto) {
		Map<String, String> ret = new LinkedHashMap<String, String>();
		String seccion = "";

		for (String lineaOriginal : texto.split("\\r?\\n")) {
			String linea = limpiarComentario(lineaOriginal).trim();

			if (linea.isEmpty()) {
				continue;
			}

			if (linea.startsWith("[[") && linea.endsWith("]]")) {
				seccion = linea.substring(2, linea.length() - 2).trim();
				continue;
			}

			if (linea.startsWith("[") && linea.endsWith("]")) {
				seccion = linea.substring(1, linea.length() - 1).trim();
				continue;
			}

			int eq = linea.indexOf('=');
			if (eq < 0) {
				continue;
			}

			String clave = linea.substring(0, eq).trim();
			String valor = linea.substring(eq + 1).trim();

			if (!seccion.trim().isEmpty()) {
				clave = seccion + "." + clave;
			}

			ret.put(clave, quitarComillas(valor));
		}

		return ret;
	}

	private static String limpiarComentario(String linea) {
		boolean comillas = false;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < linea.length(); i++) {
			char c = linea.charAt(i);

			if (c == '"' && (i == 0 || linea.charAt(i - 1) != '\\')) {
				comillas = !comillas;
			}

			if (c == '#' && !comillas) {
				break;
			}

			sb.append(c);
		}

		return sb.toString();
	}

	private static String leerValorToml(String linea) {
		int eq = linea.indexOf('=');
		if (eq < 0) {
			return "";
		}

		return quitarComillas(linea.substring(eq + 1).trim());
	}

	private static String quitarComillas(String valor) {
		if (valor == null) {
			return "";
		}

		String v = valor.trim();

		if (v.startsWith("\"") && v.endsWith("\"") && v.length() >= 2) {
			v = v.substring(1, v.length() - 1);
		}

		return v.replace("\\\"", "\"").replace("\\\\", "\\");
	}

	private static String valor(Map<String, String> mapa, String clave, String def) {
		String v = mapa.get(clave);
		return v == null ? def : v;
	}

	private static String carpetaDeMetafile(String rutaMetafile) {
		if (rutaMetafile == null) {
			return "";
		}

		String r = rutaMetafile.replace('\\', '/');
		int slash = r.lastIndexOf('/');

		if (slash < 0) {
			return "";
		}

		return r.substring(0, slash);
	}

	private static byte[] descargarBytes(String url) throws IOException {
		HttpURLConnection con = null;

		try {
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(20000);
			con.setReadTimeout(60000);
			con.setRequestProperty("User-Agent", "CrashDetectorMC/1.0");

			int codigo = con.getResponseCode();

			if (codigo < 200 || codigo >= 300) {
				throw new IOException("No se pudo descargar " + url + " HTTP " + codigo);
			}

			return leerBytes(con.getInputStream());
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}

	private static Map<String, EntradaZipLeida> leerEntradasZip(Path zip) throws IOException {
		Map<String, EntradaZipLeida> ret = new LinkedHashMap<String, EntradaZipLeida>();

		try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zip))) {
			ZipEntry e;

			while ((e = zis.getNextEntry()) != null) {
				if (e.isDirectory()) {
					continue;
				}

				EntradaZipLeida entrada = new EntradaZipLeida();
				entrada.nombre = e.getName().replace('\\', '/');
				entrada.bytes = leerBytes(zis);
				entrada.fechaModificacion = e.getTime();

				ret.put(entrada.nombre, entrada);
			}
		}

		return ret;
	}

	private static EntradaZipLeida buscarEntrada(Map<String, EntradaZipLeida> entradas, String nombre) {
		if (nombre == null) {
			return null;
		}

		String buscado = nombre.replace('\\', '/');

		for (EntradaZipLeida e : entradas.values()) {
			if (buscado.equalsIgnoreCase(e.nombre)) {
				return e;
			}
		}

		return null;
	}

	private static byte[] leerBytes(InputStream is) throws IOException {
		if (is == null) {
			return new byte[0];
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int leidos;

		while ((leidos = is.read(buffer)) != -1) {
			baos.write(buffer, 0, leidos);
		}

		return baos.toByteArray();
	}

	private static void verificarHashSiExiste(byte[] bytes, String hashFormat, String esperado, String ruta)
			throws IOException {

		if (esperado == null || esperado.trim().isEmpty()) {
			return;
		}

		String algoritmo = algoritmoJava(hashFormat);

		if (algoritmo == null) {
			return;
		}

		String real = hash(bytes, algoritmo);

		if (!real.equalsIgnoreCase(esperado.trim())) {
			throw new IOException(
					hashFormat + " no coincide para " + ruta + ". Esperado: " + esperado + ", real: " + real);
		}
	}

	private static String algoritmoJava(String hashFormat) {
		if (hashFormat == null) {
			return null;
		}

		String h = hashFormat.trim().toLowerCase();

		if ("sha1".equals(h)) {
			return "SHA-1";
		}

		if ("sha256".equals(h)) {
			return "SHA-256";
		}

		if ("sha512".equals(h)) {
			return "SHA-512";
		}

		if ("md5".equals(h)) {
			return "MD5";
		}

		return null;
	}

	private static String hash(byte[] bytes, String algoritmo) throws IOException {
		try {
			MessageDigest md = MessageDigest.getInstance(algoritmo);
			byte[] digest = md.digest(bytes);

			StringBuilder sb = new StringBuilder();

			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}

			return sb.toString();
		} catch (Throwable t) {
			throw new IOException("No se pudo calcular hash " + algoritmo, t);
		}
	}

	private static InfoEntradaImportacion crearInfoSimple(String rutaRelativa) {
		InfoEntradaImportacion info = new InfoEntradaImportacion();
		info.rutaRelativa = rutaRelativa.replace('\\', '/');
		info.nombreEntrada = rutaRelativa;
		info.esMod = info.rutaRelativa.startsWith("mods/");
		return info;
	}

	private static void actualizarTLauncherAdditionalDespuesDeImportar(Path carpetaDestino) {
		try {
			ActualizadorTLauncherAdditional.actualizar(carpetaDestino);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private static void mezclar(ResultadoImportacion total, ResultadoImportacion r) {
		if (r == null) {
			return;
		}

		total.copiados += r.copiados;
		total.reemplazados += r.reemplazados;
		total.saltados += r.saltados;
		total.renombrados += r.renombrados;
		total.errores += r.errores;
		total.mensajes.addAll(r.mensajes);
	}

	private static boolean rutaSegura(String ruta) {
		if (ruta == null) {
			return false;
		}

		String r = ruta.replace('\\', '/');

		return !r.startsWith("/") && !r.contains("../") && !r.equals("..") && !r.contains(":/");
	}

	private static class EntradaIndexPackwiz {
		String file;
		String hash;
		String hashFormat;
		boolean metafile;
		boolean preserve;
	}

	private static class EntradaZipLeida {
		String nombre;
		byte[] bytes;
		long fechaModificacion;
	}
}