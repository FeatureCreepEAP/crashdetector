package com.asbestosstar.crashdetector.dto.modpack.curseforge;

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

public class ExportadorModpackCurseForge {

	private static final String NOMBRE_MANIFEST = "manifest.json";
	private static final String NOMBRE_MODLIST = "modlist.html";
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
		Json.Nodo manifest = crearManifestCurseForge(tla, carpetaInstancia);
		String modlist = crearModlistHtml(tla);

		List<ArchivoParaZip> overrides = recolectarOverrides(carpetaInstancia, rutasEntrada, tla);

		Path salida = ubicacionArchivoModpack.toAbsolutePath().normalize();
		if (salida.getParent() != null) {
			Files.createDirectories(salida.getParent());
		}

		try (ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(salida))) {
			escribirTexto(zip, NOMBRE_MANIFEST, Json.escribir(manifest));
			escribirTexto(zip, NOMBRE_MODLIST, modlist);
			escribirArchivo(zip, archivoTLA, NOMBRE_TLAUNCHER_ADDITIONAL);

			for (ArchivoParaZip archivo : overrides) {
				escribirArchivo(zip, archivo.archivo, "overrides/" + archivo.rutaRelativa);
			}
		}
	}

	private static Json.Nodo crearManifestCurseForge(Json.Nodo tla, Path carpetaInstancia) throws IOException {
		Json.Nodo version = tla.obtener("modpack").obtener("version");

		Json.Nodo manifest = Json.crearObjeto();
		Json.Nodo minecraft = manifest.obtener("minecraft");

		String minecraftVersion = obtenerCadenaSeguro(version.obtener("minecraftVersion"),
				obtenerCadenaSeguro(version.obtener("gameVersion"), ""));

		minecraft.obtener("version").poner(minecraftVersion);

		Json.Nodo loaders = minecraft.obtener("modLoaders");
		Json.Nodo loader = Json.crearObjeto();

		String loaderId = normalizarLoaderCurseForge(obtenerCadenaSeguro(version.obtener("loader"), ""));
		String loaderVersion = obtenerCadenaSeguro(version.obtener("loaderVersion"), "");

		if (!loaderId.trim().isEmpty() && !loaderVersion.trim().isEmpty()) {
			loader.obtener("id").poner(loaderId + "-" + loaderVersion);
			loader.obtener("primary").poner(true);
			loaders.agregar(loader);
		}

		manifest.obtener("manifestType").poner("minecraftModpack");
		manifest.obtener("manifestVersion").poner(1);
		manifest.obtener("name").poner(obtenerCadenaSeguro(tla.obtener("modpack").obtener("name"),
				CopiaDeSeguridadDeArchivos.obtenerNombreInstancia(carpetaInstancia)));
		manifest.obtener("version").poner("");
		manifest.obtener("author").poner("");

		Json.Nodo files = manifest.obtener("files");
		agregarArchivosManifest(files, version.obtener("mods"));
		agregarArchivosManifest(files, version.obtener("resourcePacks"));
		agregarArchivosManifest(files, version.obtener("shaderPacks"));
		agregarArchivosManifest(files, version.obtener("dataPacks"));

		manifest.obtener("overrides").poner("overrides");

		return manifest;
	}

	private static void agregarArchivosManifest(Json.Nodo files, Json.Nodo arreglo) {
		if (arreglo == null || !arreglo.esArreglo()) {
			return;
		}

		Set<String> vistos = new HashSet<String>();

		for (int i = 0; i < arreglo.tamano(); i++) {
			Json.Nodo entrada = arreglo.en(i);

			long projectId = obtenerLargoSeguro(entrada.obtener("id"), 0L);
			long fileId = obtenerLargoSeguro(entrada.obtener("version").obtener("id"), 0L);

			if (projectId <= 0L || fileId <= 0L) {
				continue;
			}

			String clave = projectId + ":" + fileId;
			if (!vistos.add(clave)) {
				continue;
			}

			Json.Nodo file = Json.crearObjeto();
			file.obtener("projectID").poner(projectId);
			file.obtener("fileID").poner(fileId);
			file.obtener("required").poner(true);
			file.obtener("isLocked").poner(false);

			files.agregar(file);
		}
	}

	private static String crearModlistHtml(Json.Nodo tla) {
		StringBuilder sb = new StringBuilder();
		sb.append("<ul>\n");

		Json.Nodo version = tla.obtener("modpack").obtener("version");
		agregarModlist(sb, version.obtener("mods"));
		agregarModlist(sb, version.obtener("resourcePacks"));
		agregarModlist(sb, version.obtener("shaderPacks"));
		agregarModlist(sb, version.obtener("dataPacks"));

		sb.append("</ul>\n");
		return sb.toString();
	}

	private static void agregarModlist(StringBuilder sb, Json.Nodo arreglo) {
		if (arreglo == null || !arreglo.esArreglo()) {
			return;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			Json.Nodo entrada = arreglo.en(i);

			String nombre = escaparHtml(obtenerCadenaSeguro(entrada.obtener("name"), "Archivo"));
			String autor = escaparHtml(obtenerCadenaSeguro(entrada.obtener("author"), ""));
			String enlace = escaparHtml(obtenerCadenaSeguro(entrada.obtener("linkProject"), ""));

			sb.append("<li>");

			if (!enlace.trim().isEmpty()) {
				sb.append("<a href=\"").append(enlace).append("\">");
			}

			sb.append(nombre);

			if (!autor.trim().isEmpty()) {
				sb.append(" (by ").append(autor).append(")");
			}

			if (!enlace.trim().isEmpty()) {
				sb.append("</a>");
			}

			sb.append("</li>\n");
		}
	}

	private static List<ArchivoParaZip> recolectarOverrides(Path carpetaInstancia, List<Path> rutasEntrada,
			Json.Nodo tla) throws IOException {

		List<ArchivoParaZip> ret = new ArrayList<ArchivoParaZip>();
		Set<String> rutasDescargables = obtenerRutasDescargables(tla);
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

		if (NOMBRE_MANIFEST.equalsIgnoreCase(rutaRelativa) || NOMBRE_MODLIST.equalsIgnoreCase(rutaRelativa)) {
			return;
		}

		// CurseForge no debe meter en overrides los archivos que van en manifest.json.
		if (rutasDescargables.contains(rutaRelativa)) {
			return;
		}

		if (!rutasAgregadas.add(rutaRelativa)) {
			return;
		}

		ret.add(new ArchivoParaZip(archivo, rutaRelativa));
	}

	private static Set<String> obtenerRutasDescargables(Json.Nodo tla) {
		Set<String> ret = new HashSet<String>();
		Json.Nodo version = tla.obtener("modpack").obtener("version");

		agregarRutasDescargables(ret, version.obtener("mods"));
		agregarRutasDescargables(ret, version.obtener("resourcePacks"));
		agregarRutasDescargables(ret, version.obtener("shaderPacks"));
		agregarRutasDescargables(ret, version.obtener("dataPacks"));

		return ret;
	}

	private static void agregarRutasDescargables(Set<String> ret, Json.Nodo arreglo) {
		if (arreglo == null || !arreglo.esArreglo()) {
			return;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			String path = obtenerCadenaSeguro(arreglo.en(i).obtener("version").obtener("metadata").obtener("path"), "");

			if (!path.trim().isEmpty()) {
				ret.add(path.replace('\\', '/'));
			}
		}
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

	private static String normalizarLoaderCurseForge(String loader) {
		if (loader == null) {
			return "";
		}

		String l = loader.trim().toLowerCase();

		if ("neoforge".equals(l)) {
			return "neoforge";
		}

		if ("quilt".equals(l)) {
			return "quilt";
		}

		if ("fabric".equals(l)) {
			return "fabric";
		}

		if ("forge".equals(l) || "mcforge".equals(l)) {
			return "forge";
		}

		return l;
	}

	private static String escaparHtml(String s) {
		if (s == null) {
			return "";
		}

		return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
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

	private static class ArchivoParaZip {
		Path archivo;
		String rutaRelativa;

		ArchivoParaZip(Path archivo, String rutaRelativa) {
			this.archivo = archivo;
			this.rutaRelativa = rutaRelativa;
		}
	}
}