package com.asbestosstar.crashdetector.dto.modpack.packwiz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.dto.modpack.CopiaDeSeguridadDeArchivos;
import com.asbestosstar.crashdetector.dto.modpack.tlmods.ActualizadorTLauncherAdditional;
import com.asbestosstar.crashdetector.json.Json;

public class ExportadorModpackPackwiz {

	private static final String NOMBRE_PACK = "pack.toml";
	private static final String NOMBRE_INDEX = "index.toml";
	private static final String NOMBRE_TLAUNCHER_ADDITIONAL = "TLauncherAdditional.json";

	public static void exportar(Path ubicacionArchivoModpack, List<Path> rutasEntrada) throws IOException {
		if (ubicacionArchivoModpack == null) {
			throw new IOException("La ubicación del archivo de salida es nula.");
		}

		Path carpetaInstancia = Paths.get(".").toAbsolutePath().normalize();

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

		List<ArchivoMetaPackwiz> metas = crearMetafilesDesdeTLauncherAdditional(tla);
		Set<String> rutasDescargables = new HashSet<String>();

		for (ArchivoMetaPackwiz meta : metas) {
			rutasDescargables.add(meta.rutaDestino);
		}

		List<ArchivoParaZip> overrides = recolectarOverrides(carpetaInstancia, rutasEntrada, rutasDescargables);

		String indexSinHashPack = crearIndexToml(metas, overrides, archivoTLA, null);
		String hashIndex = sha256(indexSinHashPack.getBytes(StandardCharsets.UTF_8));

		String packToml = crearPackToml(tla, carpetaInstancia, hashIndex);
		String indexToml = crearIndexToml(metas, overrides, archivoTLA, "sha256");

		Path salida = ubicacionArchivoModpack.toAbsolutePath().normalize();

		if (salida.getParent() != null) {
			Files.createDirectories(salida.getParent());
		}

		try (ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(salida))) {
			escribirTexto(zip, NOMBRE_PACK, packToml);
			escribirTexto(zip, NOMBRE_INDEX, indexToml);
			escribirArchivo(zip, archivoTLA, NOMBRE_TLAUNCHER_ADDITIONAL);

			for (ArchivoMetaPackwiz meta : metas) {
				escribirTexto(zip, meta.rutaMetafile, meta.contenidoToml);
			}

			for (ArchivoParaZip archivo : overrides) {
				escribirArchivo(zip, archivo.archivo, archivo.rutaRelativa);
			}
		}
	}

	private static String crearPackToml(Json.Nodo tla, Path carpetaInstancia, String hashIndex) {
		Json.Nodo version = tla.obtener("modpack").obtener("version");

		String nombre = obtenerCadenaSeguro(tla.obtener("modpack").obtener("name"),
				CopiaDeSeguridadDeArchivos.obtenerNombreInstancia(carpetaInstancia));

		String minecraft = obtenerCadenaPrimera(version.obtener("minecraftVersion"), version.obtener("gameVersion"),
				"");
		String loader = obtenerCadenaSeguro(version.obtener("loader"), "");
		String loaderVersion = obtenerCadenaSeguro(version.obtener("loaderVersion"), "");

		StringBuilder sb = new StringBuilder();

		sb.append("name = \"").append(toml(nombre)).append("\"\n");
		sb.append("author = \"\"\n");
		sb.append("version = \"1.0.0\"\n");
		sb.append("pack-format = \"packwiz:1.1.0\"\n\n");

		sb.append("[index]\n");
		sb.append("file = \"index.toml\"\n");
		sb.append("hash-format = \"sha256\"\n");
		sb.append("hash = \"").append(hashIndex).append("\"\n\n");

		sb.append("[versions]\n");
		sb.append("minecraft = \"").append(toml(minecraft)).append("\"\n");

		if (!loader.trim().isEmpty() && !loaderVersion.trim().isEmpty()) {
			sb.append(normalizarLoaderPackwiz(loader)).append(" = \"").append(toml(loaderVersion)).append("\"\n");
		}

		return sb.toString();
	}

	private static String crearIndexToml(List<ArchivoMetaPackwiz> metas, List<ArchivoParaZip> overrides,
			Path archivoTLA, String hashFormat) throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append("hash-format = \"sha256\"\n\n");

		for (ArchivoMetaPackwiz meta : metas) {
			sb.append("[[files]]\n");
			sb.append("file = \"").append(toml(meta.rutaMetafile)).append("\"\n");
			sb.append("hash = \"").append(sha256(meta.contenidoToml.getBytes(StandardCharsets.UTF_8))).append("\"\n");
			sb.append("metafile = true\n\n");
		}

		for (ArchivoParaZip archivo : overrides) {
			sb.append("[[files]]\n");
			sb.append("file = \"").append(toml(archivo.rutaRelativa)).append("\"\n");
			sb.append("hash = \"").append(sha256(archivo.archivo)).append("\"\n\n");
		}

		sb.append("[[files]]\n");
		sb.append("file = \"").append(NOMBRE_TLAUNCHER_ADDITIONAL).append("\"\n");
		sb.append("hash = \"").append(sha256(archivoTLA)).append("\"\n\n");

		return sb.toString();
	}

	private static List<ArchivoMetaPackwiz> crearMetafilesDesdeTLauncherAdditional(Json.Nodo tla) throws IOException {
		List<ArchivoMetaPackwiz> ret = new ArrayList<ArchivoMetaPackwiz>();

		Json.Nodo version = tla.obtener("modpack").obtener("version");

		agregarMetas(ret, version.obtener("mods"), "mods");
		agregarMetas(ret, version.obtener("resourcePacks"), "resourcepacks");
		agregarMetas(ret, version.obtener("shaderPacks"), "shaderpacks");
		agregarMetas(ret, version.obtener("dataPacks"), "datapacks");

		return ret;
	}

	private static void agregarMetas(List<ArchivoMetaPackwiz> ret, Json.Nodo arreglo, String carpetaMetas)
			throws IOException {

		if (arreglo == null || !arreglo.esArreglo()) {
			return;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			Json.Nodo entrada = arreglo.en(i);
			Json.Nodo version = entrada.obtener("version");
			Json.Nodo metadata = version.obtener("metadata");

			String nombre = obtenerCadenaSeguro(entrada.obtener("name"), "Archivo");
			String path = obtenerCadenaSeguro(metadata.obtener("path"), "");
			String sha1 = obtenerCadenaSeguro(metadata.obtener("sha1"), "");
			String url = obtenerCadenaSeguro(metadata.obtener("url"), "");

			if (path.trim().isEmpty() || sha1.trim().isEmpty() || url.trim().isEmpty()) {
				continue;
			}

			String nombreArchivo = nombreArchivo(path);
			String slug = slug(nombreArchivo);
			String rutaMetafile = carpetaMetas + "/" + slug + ".pw.toml";

			String modrinthProjectId = obtenerCadenaSeguro(entrada.obtener("modrinthProjectId"), "");
			String modrinthVersionId = obtenerCadenaSeguro(version.obtener("modrinthVersionId"), "");
			String curseProjectId = String.valueOf(obtenerLargoSeguro(entrada.obtener("id"), 0L));
			String curseFileId = String.valueOf(obtenerLargoSeguro(version.obtener("id"), 0L));

			StringBuilder sb = new StringBuilder();

			sb.append("name = \"").append(toml(nombre)).append("\"\n");
			sb.append("filename = \"").append(toml(nombreArchivo)).append("\"\n");
			sb.append("side = \"both\"\n\n");

			sb.append("[download]\n");
			sb.append("url = \"").append(toml(url)).append("\"\n");
			sb.append("hash-format = \"sha1\"\n");
			sb.append("hash = \"").append(toml(sha1)).append("\"\n\n");

			if (!modrinthProjectId.trim().isEmpty() && !modrinthVersionId.trim().isEmpty()) {
				sb.append("[update.modrinth]\n");
				sb.append("mod-id = \"").append(toml(modrinthProjectId)).append("\"\n");
				sb.append("version = \"").append(toml(modrinthVersionId)).append("\"\n\n");
			}

			long cp = obtenerLargoSeguro(entrada.obtener("id"), 0L);
			long cf = obtenerLargoSeguro(version.obtener("id"), 0L);

			if (cp > 0L && cf > 0L) {
				sb.append("[update.curseforge]\n");
				sb.append("project-id = ").append(curseProjectId).append("\n");
				sb.append("file-id = ").append(curseFileId).append("\n\n");
			}

			ArchivoMetaPackwiz meta = new ArchivoMetaPackwiz();
			meta.rutaMetafile = rutaMetafile;
			meta.rutaDestino = path.replace('\\', '/');
			meta.contenidoToml = sb.toString();

			ret.add(meta);
		}
	}

	private static List<ArchivoParaZip> recolectarOverrides(Path carpetaInstancia, List<Path> rutasEntrada,
			Set<String> rutasDescargables) throws IOException {

		List<ArchivoParaZip> ret = new ArrayList<ArchivoParaZip>();
		Set<String> rutasYaAgregadas = new HashSet<String>();

		if (rutasEntrada == null || rutasEntrada.isEmpty()) {
			return ret;
		}

		final Path base = carpetaInstancia.toAbsolutePath().normalize();

		for (Path rutaEntrada : rutasEntrada) {
			if (rutaEntrada == null) {
				continue;
			}

			Path ruta = rutaEntrada.toAbsolutePath().normalize();

			if (!Files.exists(ruta) || !ruta.startsWith(base)) {
				continue;
			}

			if (Files.isRegularFile(ruta)) {
				agregarOverride(ret, rutasYaAgregadas, base, ruta, rutasDescargables);
			} else if (Files.isDirectory(ruta)) {
				Files.walkFileTree(ruta, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path archivo, BasicFileAttributes attrs) throws IOException {
						agregarOverride(ret, rutasYaAgregadas, base, archivo.toAbsolutePath().normalize(),
								rutasDescargables);
						return FileVisitResult.CONTINUE;
					}
				});
			}
		}

		return ret;
	}

	private static void agregarOverride(List<ArchivoParaZip> ret, Set<String> rutasYaAgregadas, Path base, Path archivo,
			Set<String> rutasDescargables) {

		String rutaRelativa = base.relativize(archivo).toString().replace('\\', '/');

		if (NOMBRE_PACK.equalsIgnoreCase(rutaRelativa) || NOMBRE_INDEX.equalsIgnoreCase(rutaRelativa)
				|| NOMBRE_TLAUNCHER_ADDITIONAL.equalsIgnoreCase(rutaRelativa)) {
			return;
		}

		if (rutasDescargables.contains(rutaRelativa)) {
			return;
		}

		if (!rutasYaAgregadas.add(rutaRelativa)) {
			return;
		}

		ret.add(new ArchivoParaZip(archivo, rutaRelativa));
	}

	private static void escribirTexto(ZipOutputStream zip, String nombre, String texto) throws IOException {
		ZipEntry e = new ZipEntry(nombre.replace('\\', '/'));
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

	private static String sha256(Path archivo) throws IOException {
		try (InputStream is = Files.newInputStream(archivo)) {
			return sha256(leerBytes(is));
		}
	}

	private static String sha256(byte[] bytes) throws IOException {
		return hash(bytes, "SHA-256");
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

	private static byte[] leerBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int leidos;

		while ((leidos = is.read(buffer)) != -1) {
			baos.write(buffer, 0, leidos);
		}

		return baos.toByteArray();
	}

	private static String normalizarLoaderPackwiz(String loader) {
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

	private static String nombreArchivo(String path) {
		if (path == null) {
			return "";
		}

		String p = path.replace('\\', '/');
		int slash = p.lastIndexOf('/');
		return slash >= 0 ? p.substring(slash + 1) : p;
	}

	private static String slug(String texto) {
		if (texto == null) {
			return "archivo";
		}

		String s = texto.toLowerCase().replace(".jar", "").replace(".zip", "").replaceAll("[^a-z0-9]+", "-")
				.replaceAll("^-+", "").replaceAll("-+$", "");

		return s.trim().isEmpty() ? "archivo" : s;
	}

	private static String toml(String s) {
		if (s == null) {
			return "";
		}

		return s.replace("\\", "\\\\").replace("\"", "\\\"");
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

	private static class ArchivoMetaPackwiz {
		String rutaMetafile;
		String rutaDestino;
		String contenidoToml;
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