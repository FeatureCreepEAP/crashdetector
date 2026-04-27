package com.asbestosstar.crashdetector.cargador.obtener;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.cargador.Cargador;

public class ObtenerVersionFabric {

	/*
	 * Fabric loader no depende directamente de la versión de Minecraft. Ejemplo:
	 * net.fabricmc:fabric-loader:0.18.4
	 *
	 * Quilt puede aparecer como: org.quiltmc:quilt-loader:... quilt-loader-...
	 */
	private static final String REGEX_VERSION_LOADER = "[0-9]+(?:\\.[0-9]+){1,5}(?:[-+][a-zA-Z0-9_.+-]+)?";

	private static final Pattern PATRON_FABRIC_LOADER_MAVEN = Pattern
			.compile("net\\.fabricmc:fabric-loader:(" + REGEX_VERSION_LOADER + ")", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_QUILT_LOADER_MAVEN = Pattern
			.compile("org\\.quiltmc:quilt-loader:(" + REGEX_VERSION_LOADER + ")", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_FABRIC_LOADER_RUTA = Pattern.compile(
			"(?:^|[/\\\\])net[/\\\\]fabricmc[/\\\\]fabric-loader[/\\\\](" + REGEX_VERSION_LOADER + ")[/\\\\]",
			Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_QUILT_LOADER_RUTA = Pattern.compile(
			"(?:^|[/\\\\])org[/\\\\]quiltmc[/\\\\]quilt-loader[/\\\\](" + REGEX_VERSION_LOADER + ")[/\\\\]",
			Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_FABRIC_LOADER_JAR = Pattern
			.compile("fabric-loader-(" + REGEX_VERSION_LOADER + ")\\.jar", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_QUILT_LOADER_JAR = Pattern
			.compile("quilt-loader-(" + REGEX_VERSION_LOADER + ")\\.jar", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_MAIN_CLASS_FABRIC = Pattern.compile(
			"net\\.fabricmc\\.loader\\.impl\\.launch\\.knot\\.Knot(?:Client|Server)", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_MAIN_CLASS_QUILT = Pattern
			.compile("org\\.quiltmc\\.loader\\.impl\\.launch\\.knot\\.Knot(?:Client|Server)", Pattern.CASE_INSENSITIVE);

	static {
		for (Cargador carg : Cargador.cargadores_activados) {
			if (carg.id().equals("fabric")) {
				// Tiene Fabric.
			}
		}
	}

	public static class ResultadoFabric {
		public String versionLoader;
		public boolean qcc;
		public String fuente;

		public boolean encontrado() {
			return versionLoader != null && !versionLoader.trim().isEmpty();
		}

		/*
		 * QCC = Quilt Compatible / Quilt loader. Devuelve true cuando realmente
		 * detectamos quilt-loader.
		 */
		public boolean esQCC() {
			return qcc;
		}

		@Override
		public String toString() {
			return "ResultadoFabric{" + "versionLoader='" + versionLoader + '\'' + ", qcc=" + qcc + ", fuente='"
					+ fuente + '\'' + '}';
		}
	}

	public static ResultadoFabric detectarDesdeDirectorioActual() throws IOException {
		return detectarDesdeInstancia(java.nio.file.Paths.get("."));
	}

	public static ResultadoFabric detectarDesdeSistema() throws IOException {
		ResultadoFabric r = detectarDesdeClasspath();
		if (r.encontrado()) {
			return r;
		}

		return detectarDesdeDirectorioActual();
	}

	public static boolean esQCC() throws IOException {
		return detectarDesdeSistema().esQCC();
	}

	public static boolean esQCC(Path carpetaInstancia) throws IOException {
		return detectarDesdeInstancia(carpetaInstancia).esQCC();
	}

	public static ResultadoFabric detectarDesdeInstancia(Path carpetaInstancia) throws IOException {
		ResultadoFabric r;

		r = detectarDesdeClasspath();
		if (r.encontrado()) {
			return r;
		}

		r = detectarDesdeLibraries(carpetaInstancia);
		if (r.encontrado()) {
			return r;
		}

		r = detectarDesdeRunScripts(carpetaInstancia);
		if (r.encontrado()) {
			return r;
		}

		r = detectarDesdeJsonesVersion(carpetaInstancia);
		if (r.encontrado()) {
			return r;
		}

		return nuevoVacio("No encontrado");
	}

	public static ResultadoFabric detectarDesdeClasspath() {
		String cp = System.getProperty("java.class.path", "");
		ResultadoFabric r = detectarDesdeTexto(cp, "classpath");
		if (r.encontrado()) {
			return r;
		}

		String comando = System.getProperty("sun.java.command", "");
		return detectarDesdeTexto(comando, "sun.java.command");
	}

	public static ResultadoFabric detectarDesdeLibraries(Path carpetaInstancia) throws IOException {
		final ResultadoFabric[] encontrado = new ResultadoFabric[] { nuevoVacio("libraries") };

		Path libraries = carpetaInstancia.resolve("libraries");
		if (!Files.isDirectory(libraries)) {
			return encontrado[0];
		}

		Files.walkFileTree(libraries, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path archivo, BasicFileAttributes attrs) {
				String texto = archivo.toString().replace('\\', '/');

				ResultadoFabric r = detectarDesdeTexto(texto, "libraries");
				if (r.encontrado()) {
					encontrado[0] = r;
					return FileVisitResult.TERMINATE;
				}

				return FileVisitResult.CONTINUE;
			}
		});

		return encontrado[0];
	}

	public static ResultadoFabric detectarDesdeRunScripts(Path carpetaInstancia) throws IOException {
		ResultadoFabric r;

		r = detectarDesdeArchivoSiExiste(carpetaInstancia.resolve("run.sh"), "run.sh");
		if (r.encontrado()) {
			return r;
		}

		r = detectarDesdeArchivoSiExiste(carpetaInstancia.resolve("run.bat"), "run.bat");
		if (r.encontrado()) {
			return r;
		}

		return nuevoVacio("run scripts");
	}

	public static ResultadoFabric detectarDesdeJsonesVersion(Path carpetaInstancia) throws IOException {
		final ResultadoFabric[] encontrado = new ResultadoFabric[] { nuevoVacio("json versiones") };

		Files.walkFileTree(carpetaInstancia, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path archivo, BasicFileAttributes attrs) throws IOException {
				String nombre = archivo.getFileName().toString().toLowerCase();

				if (!nombre.endsWith(".json")) {
					return FileVisitResult.CONTINUE;
				}

				ResultadoFabric r = detectarDesdeArchivoSiExiste(archivo, "json version Fabric/Quilt");
				if (r.encontrado()) {
					encontrado[0] = r;
					return FileVisitResult.TERMINATE;
				}

				return FileVisitResult.CONTINUE;
			}
		});

		return encontrado[0];
	}

	public static ResultadoFabric detectarDesdeArchivoSiExiste(Path archivo, String fuente) throws IOException {
		if (!Files.isRegularFile(archivo)) {
			return nuevoVacio(fuente);
		}

		return detectarDesdeTexto(leerArchivo(archivo), fuente);
	}

	public static ResultadoFabric detectarDesdeTexto(String texto, String fuente) {
		if (texto == null || texto.trim().isEmpty()) {
			return nuevoVacio(fuente);
		}

		ResultadoFabric r;

		r = detectarConPatrones(texto, fuente, true, PATRON_QUILT_LOADER_MAVEN, PATRON_QUILT_LOADER_RUTA,
				PATRON_QUILT_LOADER_JAR);

		if (r.encontrado()) {
			return r;
		}

		r = detectarConPatrones(texto, fuente, false, PATRON_FABRIC_LOADER_MAVEN, PATRON_FABRIC_LOADER_RUTA,
				PATRON_FABRIC_LOADER_JAR);

		if (r.encontrado()) {
			return r;
		}

		/*
		 * Si solo aparece el mainClass, sabemos el tipo de loader, pero no la versión
		 * exacta.
		 */
		if (PATRON_MAIN_CLASS_QUILT.matcher(texto).find()) {
			r = nuevoVacio(fuente + " / mainClass Quilt sin versión");
			r.qcc = true;
			return r;
		}

		if (PATRON_MAIN_CLASS_FABRIC.matcher(texto).find()) {
			r = nuevoVacio(fuente + " / mainClass Fabric sin versión");
			r.qcc = false;
			return r;
		}

		return nuevoVacio(fuente);
	}

	private static ResultadoFabric detectarConPatrones(String texto, String fuente, boolean qcc, Pattern... patrones) {
		for (Pattern p : patrones) {
			Matcher m = p.matcher(texto);
			if (m.find()) {
				ResultadoFabric r = new ResultadoFabric();
				r.versionLoader = m.group(1);
				r.qcc = qcc;
				r.fuente = fuente;
				return r;
			}
		}

		return nuevoVacio(fuente);
	}

	private static ResultadoFabric nuevoVacio(String fuente) {
		ResultadoFabric r = new ResultadoFabric();
		r.fuente = fuente;
		return r;
	}

	private static String leerArchivo(Path archivo) throws IOException {
		StringBuilder sb = new StringBuilder();

		try (BufferedReader br = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
			String linea;
			while ((linea = br.readLine()) != null) {
				sb.append(linea).append('\n');
			}
		}

		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		Path carpeta = args.length > 0 ? java.nio.file.Paths.get(args[0]) : java.nio.file.Paths.get(".");
		ResultadoFabric r = detectarDesdeInstancia(carpeta);

		System.out.println("Fabric/Quilt loader: " + r.versionLoader);
		System.out.println("Es QCC / Quilt: " + r.esQCC());
		System.out.println("Fuente: " + r.fuente);
	}
}