package com.asbestosstar.crashdetector.dto.modpack.tlmods;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.cargador.obtener.ObtenerVersionFabric;
import com.asbestosstar.crashdetector.cargador.obtener.ObtenerVersionFeatureCreep;
import com.asbestosstar.crashdetector.cargador.obtener.ObtenerVersionMCForge;
import com.asbestosstar.crashdetector.cargador.obtener.ObtenerVersionNeoForge;
import com.asbestosstar.crashdetector.json.Json;

public class GeneradorManifiestoTLauncherAdditional {

	public static class LoaderPrincipal {
		public String id;
		public String version;
		public String versionMinecraft;
	}

	public static void completarManifiestoPrincipal(Json.Nodo raiz, Path carpetaInstancia) throws IOException {
		carpetaInstancia = carpetaInstancia.toAbsolutePath().normalize();

		// TLauncherAdditional debe funcionar como manifiesto principal, no solo como
		// archivo auxiliar del exportador TLMods.
		raiz.obtener("activateSkinCapeForUserVersion").poner(false);
		raiz.obtener("skinVersion").poner(false);
		raiz.obtener("skipHashsumValidation").poner(false);
		raiz.obtener("tlauncherVersion").poner(0);

		LoaderPrincipal loader = detectarLoaderPrincipal(carpetaInstancia);

		Json.Nodo modpack = raiz.obtener("modpack");
		Json.Nodo version = modpack.obtener("version");

		if (loader != null) {
			version.obtener("loader").poner(valorSeguro(loader.id));
			version.obtener("loaderId").poner(valorSeguro(loader.id));
			version.obtener("loaderVersion").poner(valorSeguro(loader.version));
			version.obtener("gameVersion").poner(valorSeguro(loader.versionMinecraft));
			version.obtener("minecraftVersion").poner(valorSeguro(loader.versionMinecraft));
		} else {
			version.obtener("loader").poner("");
			version.obtener("loaderId").poner("");
			version.obtener("loaderVersion").poner("");
			version.obtener("gameVersion").poner("");
			version.obtener("minecraftVersion").poner("");
		}

		version.obtener("libraries").poner(crearLibrariesLocales(carpetaInstancia));
		raiz.obtener("additionalFiles").poner(crearAdditionalFilesLibraries(carpetaInstancia));
	}

	public static LoaderPrincipal detectarLoaderPrincipal(Path carpetaInstancia) {
		LoaderPrincipal ret;

		// Orden principal: Forge, NeoForge, Fabric/Quilt, LiteLoader, FeatureCreep.
		ret = detectarMinecraftForge(carpetaInstancia);
		if (ret != null) {
			return ret;
		}

		ret = detectarNeoForge(carpetaInstancia);
		if (ret != null) {
			return ret;
		}

		ret = detectarFabricOQuilt(carpetaInstancia);
		if (ret != null) {
			return ret;
		}

		ret = detectarLiteLoader(carpetaInstancia);
		if (ret != null) {
			return ret;
		}

		ret = detectarFeatureCreep(carpetaInstancia);
		if (ret != null) {
			return ret;
		}

		return null;
	}

	private static LoaderPrincipal detectarMinecraftForge(Path carpetaInstancia) {
		try {
			ObtenerVersionMCForge.ResultadoMCForge r = ObtenerVersionMCForge.detectarDesdeInstancia(carpetaInstancia);

			if (r != null && r.encontrado()) {
				LoaderPrincipal l = new LoaderPrincipal();
				l.id = "forge"; // FIX
				l.version = r.buildForge;
				l.versionMinecraft = r.versionMinecraft;
				return l;
			}
		} catch (Throwable ignored) {
		}

		return null;
	}

	private static LoaderPrincipal detectarNeoForge(Path carpetaInstancia) {
		try {
			ObtenerVersionNeoForge.ResultadoNeoForge r = ObtenerVersionNeoForge
					.detectarDesdeInstancia(carpetaInstancia);

			if (r != null && r.encontrado()) {
				LoaderPrincipal l = new LoaderPrincipal();
				l.id = "neoforge";
				l.version = r.versionNeoForge;
				l.versionMinecraft = r.versionMinecraft;
				return l;
			}
		} catch (Throwable ignored) {
		}

		return null;
	}

	private static LoaderPrincipal detectarFabricOQuilt(Path carpetaInstancia) {
		try {
			ObtenerVersionFabric.ResultadoFabric r = ObtenerVersionFabric.detectarDesdeInstancia(carpetaInstancia);

			if (r != null && r.encontrado()) {
				LoaderPrincipal l = new LoaderPrincipal();

				// Si es QCC/quilt-loader, se escribe quilt directamente.
				// No se agrega una línea qcc al JSON.
				l.id = r.esQCC() ? "quilt" : "fabric";
				l.version = r.versionLoader;
				l.versionMinecraft = detectarVersionMinecraftPorArchivos(carpetaInstancia);
				return l;
			}
		} catch (Throwable ignored) {
		}

		return null;
	}

	private static LoaderPrincipal detectarLiteLoader(Path carpetaInstancia) {
		// Placeholder para cuando agregues ObtenerVersionLiteLoader.
		return null;
	}

	private static LoaderPrincipal detectarFeatureCreep(Path carpetaInstancia) {
		try {
			ObtenerVersionFeatureCreep.ResultadoFeatureCreep r = ObtenerVersionFeatureCreep
					.detectarDesdeInstancia(carpetaInstancia);

			if (r != null && r.encontrado()) {
				LoaderPrincipal l = new LoaderPrincipal();
				l.id = "featurecreep";
				l.version = r.versionFeatureCreep;
				l.versionMinecraft = detectarVersionMinecraftPorArchivos(carpetaInstancia);
				return l;
			}
		} catch (Throwable ignored) {
		}

		return null;
	}

	private static Json.Nodo crearLibrariesLocales(Path carpetaInstancia) throws IOException {
		Json.Nodo obj = Json.crearObjeto();
		Json.Nodo arr = obj.obtener("arr");

		Path libraries = carpetaInstancia.resolve("libraries");
		if (!Files.isDirectory(libraries)) {
			return arr;
		}

		List<Path> archivos = listarArchivos(libraries);
		for (Path archivo : archivos) {
			if (!Files.isRegularFile(archivo)) {
				continue;
			}

			Json.Nodo lib = Json.crearObjeto();
			String rel = carpetaInstancia.relativize(archivo).toString().replace('\\', '/');

			lib.obtener("path").poner(rel);
			lib.obtener("sha1").poner(sha1(archivo));
			lib.obtener("size").poner(Files.size(archivo));
			lib.obtener("url").poner("");

			arr.agregar(lib);
		}

		return arr;
	}

	private static Json.Nodo crearAdditionalFilesLibraries(Path carpetaInstancia) throws IOException {
		Json.Nodo obj = Json.crearObjeto();
		Json.Nodo arr = obj.obtener("arr");

		Path libraries = carpetaInstancia.resolve("libraries");
		if (!Files.isDirectory(libraries)) {
			return arr;
		}

		List<Path> archivos = listarArchivos(libraries);
		for (Path archivo : archivos) {
			if (!Files.isRegularFile(archivo)) {
				continue;
			}

			Json.Nodo f = Json.crearObjeto();
			String rel = carpetaInstancia.relativize(archivo).toString().replace('\\', '/');

			f.obtener("sha1").poner(sha1(archivo));
			f.obtener("size").poner(Files.size(archivo));
			f.obtener("path").poner(rel);
			f.obtener("url").poner("");

			arr.agregar(f);
		}

		return arr;
	}

	private static List<Path> listarArchivos(Path carpeta) throws IOException {
		final List<Path> ret = new ArrayList<Path>();

		Files.walkFileTree(carpeta, new java.nio.file.SimpleFileVisitor<Path>() {
			@Override
			public java.nio.file.FileVisitResult visitFile(Path file,
					java.nio.file.attribute.BasicFileAttributes attrs) {
				if (Files.isRegularFile(file)) {
					ret.add(file.toAbsolutePath().normalize());
				}

				return java.nio.file.FileVisitResult.CONTINUE;
			}
		});

		return ret;
	}

	private static String detectarVersionMinecraftPorArchivos(Path carpetaInstancia) {
		try {
			Path archivo = carpetaInstancia.resolve("TLauncherAdditional.json");
			if (Files.isRegularFile(archivo)) {
				String texto = new String(Files.readAllBytes(archivo), "UTF-8");

				java.util.regex.Matcher m = java.util.regex.Pattern
						.compile("\"gameVersion\"\\s*:\\s*\"([^\"]+)\"|\"minecraftVersion\"\\s*:\\s*\"([^\"]+)\"")
						.matcher(texto);

				if (m.find()) {
					return m.group(1) != null ? m.group(1) : m.group(2);
				}
			}
		} catch (Throwable ignored) {
		}

		return "";
	}

	private static String sha1(Path archivo) throws IOException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] datos = Files.readAllBytes(archivo);
			byte[] digest = md.digest(datos);

			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}

			return sb.toString();
		} catch (Exception e) {
			throw new IOException("No se pudo calcular SHA-1 de " + archivo, e);
		}
	}

	private static String valorSeguro(String valor) {
		return valor == null ? "" : valor;
	}
}