package com.asbestosstar.crashdetector.dto.modpack.modrinth;

import com.asbestosstar.crashdetector.Statics;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.CopiaDeSeguridadDeArchivos;
import com.asbestosstar.crashdetector.dto.modpack.tlmods.ActualizadorTLauncherAdditional;

public class ExportadorModpackModrinth {

	private static final String NOMBRE_INDEX = "modrinth.index.json";
	private static final String NOMBRE_TLAUNCHER_ADDITIONAL = "TLauncherAdditional.json";

	public static void exportar(Path ubicacionArchivoModpack, List<Path> rutasEntrada) throws IOException {
		if (ubicacionArchivoModpack == null) {
			throw new IOException("La ubicación del archivo de salida es nula.");
		}

		Path carpetaInstancia = Statics.CARPETA_DE_APP.toPath().toAbsolutePath().normalize();

		try {
			ActualizadorTLauncherAdditional.actualizar(carpetaInstancia);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		Path archivoTLA = carpetaInstancia.resolve(NOMBRE_TLAUNCHER_ADDITIONAL);
		if (!Files.isRegularFile(archivoTLA)) {
			throw new IOException("No existe " + NOMBRE_TLAUNCHER_ADDITIONAL + " en " + carpetaInstancia);
		}

		Json.Nodo tla = Json.leer(new String(Files.readAllBytes(archivoTLA), StandardCharsets.UTF_8));
		Json.Nodo index = crearIndexModrinth(tla, carpetaInstancia);

		Set<String> rutasDescargables = obtenerRutasDescargables(index);
		List<ArchivoParaZip> overrides = recolectarOverrides(carpetaInstancia, rutasEntrada, rutasDescargables);

		Path salida = ubicacionArchivoModpack.toAbsolutePath().normalize();
		if (salida.getParent() != null) {
			Files.createDirectories(salida.getParent());
		}

		try (ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(salida))) {
			escribirTexto(zip, NOMBRE_INDEX, Json.escribir(index));
			escribirArchivo(zip, archivoTLA, NOMBRE_TLAUNCHER_ADDITIONAL);

			for (ArchivoParaZip archivo : overrides) {
				escribirArchivo(zip, archivo.archivo, "overrides/" + archivo.rutaRelativa);
			}
		}
	}

	private static Json.Nodo crearIndexModrinth(Json.Nodo tla, Path carpetaInstancia) throws IOException {
		Json.Nodo version = tla.obtener("modpack").obtener("version");

		Json.Nodo index = Json.crearObjeto();
		index.obtener("formatVersion").poner(1);
		index.obtener("game").poner("minecraft");

		index.obtener("versionId").poner("1.0.0");
		index.obtener("name").poner(obtenerCadenaSeguro(tla.obtener("modpack").obtener("name"),
				CopiaDeSeguridadDeArchivos.obtenerNombreInstancia(carpetaInstancia)));
		index.obtener("summary").poner("");

		Json.Nodo deps = index.obtener("dependencies");
		String mc = obtenerCadenaPrimera(version.obtener("minecraftVersion"), version.obtener("gameVersion"), "");
		String loader = obtenerCadenaSeguro(version.obtener("loader"), "");
		String loaderVersion = obtenerCadenaSeguro(version.obtener("loaderVersion"), "");

		if (!mc.trim().isEmpty()) {
			deps.obtener("minecraft").poner(mc);
		}

		if (!loader.trim().isEmpty() && !loaderVersion.trim().isEmpty()) {
			deps.obtener(normalizarLoaderModrinth(loader)).poner(loaderVersion);
		}

		Json.Nodo files = index.obtener("files");

		agregarArchivosIndex(files, version.obtener("mods"));
		agregarArchivosIndex(files, version.obtener("resourcePacks"));
		agregarArchivosIndex(files, version.obtener("shaderPacks"));
		agregarArchivosIndex(files, version.obtener("dataPacks"));

		return index;
	}

	private static void agregarArchivosIndex(Json.Nodo files, Json.Nodo arreglo) throws IOException {
		if (arreglo == null || !arreglo.esArreglo()) {
			return;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			Json.Nodo entrada = arreglo.en(i);
			Json.Nodo version = entrada.obtener("version");
			Json.Nodo metadata = version.obtener("metadata");

			String path = obtenerCadenaSeguro(metadata.obtener("path"), "");
			String sha1 = obtenerCadenaSeguro(metadata.obtener("sha1"), "");
			String versionId = obtenerCadenaSeguro(version.obtener("modrinthVersionId"), "");
			String entradaVersionId = obtenerCadenaSeguro(entrada.obtener("modrinthVersionId"), "");

			if (versionId.trim().isEmpty()) {
				versionId = entradaVersionId;
			}

			if (path.trim().isEmpty()) {
				continue;
			}

			Json.Nodo versionMR = null;

			try {
				if (!versionId.trim().isEmpty()) {
					versionMR = solicitarVersionModrinth(versionId);
				} else if (!sha1.trim().isEmpty()) {
					versionMR = MRModDesdeJar.solicitarVersionPorSha1ModrinthSeguro(sha1);
				}
			} catch (Throwable t) {
				// No se debe romper todo el exportador por un solo archivo.
				// Si no existe en Modrinth, quedara como override local.
				CrashDetectorLogger.logException(t);
				versionMR = null;
			}

			if (versionMR == null) {
				continue;
			}

			Json.Nodo archivoPrimario = obtenerArchivoPrimario(versionMR);
			String url = obtenerCadenaSeguro(archivoPrimario.obtener("url"), "");
			String sha1Api = obtenerCadenaSeguro(archivoPrimario.obtener("hashes").obtener("sha1"), sha1);
			String sha512Api = obtenerCadenaSeguro(archivoPrimario.obtener("hashes").obtener("sha512"), "");
			long size = obtenerLargoSeguro(archivoPrimario.obtener("size"),
					obtenerLargoSeguro(metadata.obtener("size"), 0L));

			if (url.trim().isEmpty() || sha1Api.trim().isEmpty()) {
				continue;
			}

			Json.Nodo f = Json.crearObjeto();
			f.obtener("path").poner(path.replace('\\', '/'));

			Json.Nodo hashes = f.obtener("hashes");
			hashes.obtener("sha1").poner(sha1Api);

			if (!sha512Api.trim().isEmpty()) {
				hashes.obtener("sha512").poner(sha512Api);
			}

			Json.Nodo env = f.obtener("env");
			env.obtener("client").poner("required");
			env.obtener("server").poner("required");

			f.obtener("downloads").agregar(url);
			f.obtener("fileSize").poner(size);

			files.agregar(f);
		}
	}

	private static Json.Nodo solicitarVersionModrinth(String versionId) throws IOException {
		String url = MRModDesdeJar.ENDPOINT_MODRINTH + "/version/" + java.net.URLEncoder.encode(versionId, "UTF-8");
		return getJson(url);
	}

	private static Json.Nodo getJson(String url) throws IOException {
		java.net.HttpURLConnection con = null;

		try {
			java.net.URL u = new java.net.URL(url);
			con = (java.net.HttpURLConnection) u.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(20000);
			con.setReadTimeout(30000);
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("User-Agent", "CrashDetectorMC/1.0");

			int codigo = con.getResponseCode();
			java.io.InputStream is = codigo >= 200 && codigo < 300 ? con.getInputStream() : con.getErrorStream();
			String respuesta = leerCompleto(is);

			if (codigo < 200 || codigo >= 300) {
				throw new IOException("Error Modrinth HTTP " + codigo + ": " + respuesta);
			}

			return Json.leer(respuesta);
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}

	private static String leerCompleto(java.io.InputStream is) throws IOException {
		if (is == null) {
			return "";
		}

		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int leidos;

		while ((leidos = is.read(buffer)) != -1) {
			baos.write(buffer, 0, leidos);
		}

		return new String(baos.toByteArray(), "UTF-8");
	}

	private static Json.Nodo obtenerArchivoPrimario(Json.Nodo version) {
		Json.Nodo archivos = version.obtener("files");

		if (archivos != null && archivos.esArreglo() && archivos.tamano() > 0) {
			for (int i = 0; i < archivos.tamano(); i++) {
				Json.Nodo f = archivos.en(i);
				if (obtenerBooleanoSeguro(f.obtener("primary"), false)) {
					return f;
				}
			}

			return archivos.en(0);
		}

		return Json.crearObjeto();
	}

	private static Set<String> obtenerRutasDescargables(Json.Nodo index) {
		Set<String> ret = new HashSet<String>();
		Json.Nodo files = index.obtener("files");

		if (files == null || !files.esArreglo()) {
			return ret;
		}

		for (int i = 0; i < files.tamano(); i++) {
			String path = obtenerCadenaSeguro(files.en(i).obtener("path"), "");
			if (!path.trim().isEmpty()) {
				ret.add(path.replace('\\', '/'));
			}
		}

		return ret;
	}

	private static List<ArchivoParaZip> recolectarOverrides(Path carpetaInstancia, List<Path> rutasEntrada,
			Set<String> rutasDescargables) throws IOException {

		List<ArchivoParaZip> ret = new ArrayList<ArchivoParaZip>();
		Set<String> rutasAgregadas = new HashSet<String>();

		if (rutasEntrada == null || rutasEntrada.isEmpty()) {
			return ret;
		}

		final Path base = carpetaInstancia.toAbsolutePath().normalize();

		for (Path entrada : rutasEntrada) {
			if (entrada == null) {
				continue;
			}

			Path ruta = entrada.toAbsolutePath().normalize();
			if (!Files.exists(ruta) || !ruta.startsWith(base)) {
				continue;
			}

			if (Files.isRegularFile(ruta)) {
				agregarOverride(ret, rutasAgregadas, base, ruta, rutasDescargables);
			} else if (Files.isDirectory(ruta)) {
				Files.walkFileTree(ruta, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path archivo, BasicFileAttributes attrs) throws IOException {
						agregarOverride(ret, rutasAgregadas, base, archivo.toAbsolutePath().normalize(),
								rutasDescargables);
						return FileVisitResult.CONTINUE;
					}
				});
			}
		}

		return ret;
	}

	private static void agregarOverride(List<ArchivoParaZip> ret, Set<String> rutasAgregadas, Path base, Path archivo,
			Set<String> rutasDescargables) {

		String rutaRelativa = base.relativize(archivo).toString().replace('\\', '/');

		if (NOMBRE_TLAUNCHER_ADDITIONAL.equalsIgnoreCase(rutaRelativa)) {
			return;
		}

		if (NOMBRE_INDEX.equalsIgnoreCase(rutaRelativa)) {
			return;
		}

		if (rutasDescargables.contains(rutaRelativa)) {
			return;
		}

		if (!rutasAgregadas.add(rutaRelativa)) {
			return;
		}

		ret.add(new ArchivoParaZip(archivo, rutaRelativa));
	}

	private static void escribirTexto(ZipOutputStream zip, String nombre, String texto) throws IOException {
		ZipEntry e = new ZipEntry(nombre);
		zip.putNextEntry(e);
		zip.write(texto.getBytes(StandardCharsets.UTF_8));
		zip.closeEntry();
	}

	private static void escribirArchivo(ZipOutputStream zip, Path archivo, String nombreZip) throws IOException {
		ZipEntry e = new ZipEntry(nombreZip.replace('\\', '/'));
		zip.putNextEntry(e);
		Files.copy(archivo, zip);
		zip.closeEntry();
	}

	private static String normalizarLoaderModrinth(String loader) {
		if (loader == null) {
			return "";
		}

		String l = loader.trim().toLowerCase();

		if ("mcforge".equals(l)) {
			return "forge";
		}

		if ("neoforge".equals(l)) {
			return "neoforge";
		}

		return l;
	}

	private static String obtenerCadenaPrimera(Json.Nodo a, Json.Nodo b, String def) {
		String av = obtenerCadenaSeguro(a, null);
		if (av != null && !av.isEmpty()) {
			return av;
		}

		String bv = obtenerCadenaSeguro(b, null);
		if (bv != null && !bv.isEmpty()) {
			return bv;
		}

		return def;
	}

	private static String obtenerCadenaSeguro(Json.Nodo nodo, String def) {
		try {
			String s = nodo.comoCadena();
			return s == null ? def : s;
		} catch (Throwable t) {
			return def;
		}
	}

	private static long obtenerLargoSeguro(Json.Nodo nodo, long def) {
		try {
			return nodo.comoLargo();
		} catch (Throwable t) {
			return def;
		}
	}

	private static boolean obtenerBooleanoSeguro(Json.Nodo nodo, boolean def) {
		try {
			return nodo.comoBooleano();
		} catch (Throwable t) {
			return def;
		}
	}

	private static class ArchivoParaZip {
		Path archivo;
		String rutaRelativa;

		ArchivoParaZip(Path archivo, String rutaRelativa) {
			this.archivo = archivo;
			this.rutaRelativa = rutaRelativa;
		}
	}
}