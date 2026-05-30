package com.asbestosstar.crashdetector.dto.modpack.tlmods;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.CopiaDeSeguridadDeArchivos;

public class ExportadorModpackTlmods {

	private static final String NOMBRE_TLAUNCHER_ADDITIONAL = "TLauncherAdditional.json";

	public static void exportar(Path ubicacionArchivoModpack, List<Path> rutasEntrada) throws IOException {
		if (ubicacionArchivoModpack == null) {
			throw new IOException("La ubicación del archivo de salida es nula.");
		}

		if (rutasEntrada == null || rutasEntrada.isEmpty()) {
			throw new IOException("No hay rutas seleccionadas para exportar.");
		}

		Path carpetaInstancia = Paths.get(".").toAbsolutePath().normalize();
		Path archivoTLauncherAdditional = carpetaInstancia.resolve(NOMBRE_TLAUNCHER_ADDITIONAL);

		// Actualiza TLauncherAdditional.json si corresponde.
		// Si estamos dentro de .minecraft/versions/<instancia>/, el actualizador no lo
		// toca.
		try {
			ActualizadorTLauncherAdditional.actualizar(carpetaInstancia);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		Set<String> archivosNoNecesarios = obtenerArchivosDescargablesDesdeTLauncherAdditional(
				archivoTLauncherAdditional);

		List<ArchivoParaZip> archivos = recolectarArchivos(carpetaInstancia, rutasEntrada, archivosNoNecesarios);

		// El formato TLMods debe llevar TLauncherAdditional.json dentro de la carpeta
		// raíz.
		if (Files.isRegularFile(archivoTLauncherAdditional)) {
			agregarArchivoUnico(archivos, archivoTLauncherAdditional, NOMBRE_TLAUNCHER_ADDITIONAL);
		}

		String nombreInstancia = CopiaDeSeguridadDeArchivos.obtenerNombreInstancia(carpetaInstancia);
		Path salida = ubicacionArchivoModpack.toAbsolutePath().normalize();

		if (salida.getParent() != null) {
			Files.createDirectories(salida.getParent());
		}

		escribirZipMultihilo(salida, nombreInstancia, archivos);
	}

	private static List<ArchivoParaZip> recolectarArchivos(Path carpetaInstancia, List<Path> rutasEntrada,
			Set<String> archivosNoNecesarios) throws IOException {

		final List<ArchivoParaZip> ret = new ArrayList<ArchivoParaZip>();
		final Set<String> rutasYaAgregadas = new HashSet<String>();
		final Path carpetaNormal = carpetaInstancia.toAbsolutePath().normalize();

		for (Path rutaEntrada : rutasEntrada) {
			if (rutaEntrada == null) {
				continue;
			}

			final Path ruta = rutaEntrada.toAbsolutePath().normalize();

			if (!Files.exists(ruta)) {
				continue;
			}

			if (!ruta.startsWith(carpetaNormal)) {
				continue;
			}

			if (Files.isRegularFile(ruta)) {
				agregarArchivoSiCorresponde(ret, rutasYaAgregadas, carpetaNormal, ruta, archivosNoNecesarios);
			} else if (Files.isDirectory(ruta)) {
				Files.walkFileTree(ruta, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path archivo, BasicFileAttributes attrs) throws IOException {
						if (Files.isRegularFile(archivo)) {
							agregarArchivoSiCorresponde(ret, rutasYaAgregadas, carpetaNormal,
									archivo.toAbsolutePath().normalize(), archivosNoNecesarios);
						}

						return FileVisitResult.CONTINUE;
					}
				});
			}
		}

		return ret;
	}

	private static void agregarArchivoSiCorresponde(List<ArchivoParaZip> ret, Set<String> rutasYaAgregadas,
			Path carpetaInstancia, Path archivo, Set<String> archivosNoNecesarios) {

		try {
			String nombre = archivo.getFileName() != null ? archivo.getFileName().toString() : "";

			// Se agrega manualmente al final para que siempre quede en la raíz del modpack.
			if (NOMBRE_TLAUNCHER_ADDITIONAL.equalsIgnoreCase(nombre)) {
				return;
			}

			// Si el jar/zip ya tiene información descargable en TLauncherAdditional.json,
			// no se incluye físicamente en el modpack.
			if (esJarOZip(nombre) && archivosNoNecesarios.contains(nombre)) {
				return;
			}

			if (!archivo.startsWith(carpetaInstancia)) {
				return;
			}

			String rutaRelativa = carpetaInstancia.relativize(archivo).toString().replace('\\', '/');

			if (!rutasYaAgregadas.add(rutaRelativa)) {
				return;
			}

			ret.add(new ArchivoParaZip(archivo, rutaRelativa));
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private static void agregarArchivoUnico(List<ArchivoParaZip> archivos, Path archivo, String rutaRelativaForzada) {
		for (ArchivoParaZip existente : archivos) {
			if (existente.rutaRelativa.equals(rutaRelativaForzada)) {
				return;
			}
		}

		archivos.add(new ArchivoParaZip(archivo.toAbsolutePath().normalize(), rutaRelativaForzada));
	}

	private static Set<String> obtenerArchivosDescargablesDesdeTLauncherAdditional(Path archivoJson) {
		Set<String> ret = new LinkedHashSet<String>();

		try {
			if (!Files.isRegularFile(archivoJson)) {
				return ret;
			}

			String texto = new String(Files.readAllBytes(archivoJson), StandardCharsets.UTF_8);

			if (texto.trim().isEmpty()) {
				return ret;
			}

			Json.Nodo raiz = Json.leer(texto);
			Json.Nodo version = raiz.obtener("modpack").obtener("version");

			leerArregloDescargable(version.obtener("mods"), ret);
			leerArregloDescargable(version.obtener("resourcePacks"), ret);
			leerArregloDescargable(version.obtener("shaderPacks"), ret);
			leerArregloDescargable(version.obtener("dataPacks"), ret);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		return ret;
	}

	private static void leerArregloDescargable(Json.Nodo arreglo, Set<String> ret) {
		if (arreglo == null || !arreglo.esArreglo()) {
			return;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			Json.Nodo entrada = arreglo.en(i);

			String path = obtenerCadenaSeguro(entrada.obtener("version").obtener("metadata").obtener("path"), null);

			if (path == null || path.trim().isEmpty()) {
				continue;
			}

			String nombreArchivo = nombreArchivo(path);

			if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
				continue;
			}

			if (!esJarOZip(nombreArchivo)) {
				continue;
			}

			if (tieneUrlODatosDeVersion(entrada)) {
				ret.add(nombreArchivo);
			}
		}
	}

	private static boolean tieneUrlODatosDeVersion(Json.Nodo entrada) {
		String url = obtenerCadenaSeguro(entrada.obtener("version").obtener("metadata").obtener("url"), null);

		if (url != null && !url.trim().isEmpty()) {
			return true;
		}

		Json.Nodo gameVersionsDTO = entrada.obtener("version").obtener("gameVersionsDTO");
		if (gameVersionsDTO != null && gameVersionsDTO.esArreglo() && gameVersionsDTO.tamano() > 0) {
			return true;
		}

		Json.Nodo minecraftVersionTypes = entrada.obtener("version").obtener("minecraftVersionTypes");
		if (minecraftVersionTypes != null && minecraftVersionTypes.esArreglo() && minecraftVersionTypes.tamano() > 0) {
			return true;
		}

		return false;
	}

	private static void escribirZipMultihilo(Path salida, String nombreInstancia, List<ArchivoParaZip> archivos)
			throws IOException {

		int hilos = Math.max(1, Runtime.getRuntime().availableProcessors());
		ExecutorService executor = Executors.newFixedThreadPool(hilos);
		List<Future<ArchivoLeido>> futuros = new ArrayList<Future<ArchivoLeido>>();

		for (final ArchivoParaZip archivo : archivos) {
			futuros.add(executor.submit(new java.util.concurrent.Callable<ArchivoLeido>() {
				@Override
				public ArchivoLeido call() throws Exception {
					return new ArchivoLeido(archivo.rutaRelativa, leerBytes(archivo.archivo));
				}
			}));
		}

		executor.shutdown();

		try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(salida))) {
			Set<String> entradas = new HashSet<String>();

			for (Future<ArchivoLeido> futuro : futuros) {
				try {
					ArchivoLeido leido = futuro.get();
					String nombreEntrada = nombreInstancia + "/" + leido.rutaRelativa.replace('\\', '/');

					if (!entradas.add(nombreEntrada)) {
						continue;
					}

					ZipEntry entry = new ZipEntry(nombreEntrada);
					zos.putNextEntry(entry);
					zos.write(leido.bytes);
					zos.closeEntry();
				} catch (Exception e) {
					throw new IOException("Error escribiendo archivo en modpack TLMods.", e);
				}
			}
		}
	}

	private static byte[] leerBytes(Path archivo) throws IOException {
		try (InputStream is = Files.newInputStream(archivo); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

			byte[] buffer = new byte[8192];
			int leidos;

			while ((leidos = is.read(buffer)) != -1) {
				baos.write(buffer, 0, leidos);
			}

			return baos.toByteArray();
		}
	}

	private static boolean esJarOZip(String nombre) {
		if (nombre == null) {
			return false;
		}

		String n = nombre.toLowerCase();
		return n.endsWith(".jar") || n.endsWith(".zip");
	}

	private static String nombreArchivo(String path) {
		if (path == null) {
			return "";
		}

		String limpio = path.replace('\\', '/');
		int idx = limpio.lastIndexOf('/');

		if (idx >= 0 && idx + 1 < limpio.length()) {
			return limpio.substring(idx + 1);
		}

		return limpio;
	}

	private static String obtenerCadenaSeguro(Json.Nodo nodo, String def) {
		try {
			String v = nodo.comoCadena();
			return v != null ? v : def;
		} catch (Throwable t) {
			return def;
		}
	}

	private static class ArchivoParaZip {
		public final Path archivo;
		public final String rutaRelativa;

		public ArchivoParaZip(Path archivo, String rutaRelativa) {
			this.archivo = archivo;
			this.rutaRelativa = rutaRelativa;
		}
	}

	private static class ArchivoLeido {
		public final String rutaRelativa;
		public final byte[] bytes;

		public ArchivoLeido(String rutaRelativa, byte[] bytes) {
			this.rutaRelativa = rutaRelativa;
			this.bytes = bytes;
		}
	}
}