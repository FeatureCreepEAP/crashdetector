package com.asbestosstar.crashdetector.cargador.obtener;

import com.asbestosstar.crashdetector.Statics;

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

public class ObtenerVersionNeoForge {

	/*
	 * Acepta versiones de Minecraft normales y raras: 1.20.1, 1.0, 26.1, 1.7.3b,
	 * 26.1-pre-3
	 */
	private static final String REGEX_VERSION_MC = "[0-9]+\\.[0-9]+(?:\\.[0-9]+)?[a-zA-Z]?(?:-[a-zA-Z0-9_.+]+)?";

	/*
	 * Acepta versiones de NeoForge como: 21.11.38-beta 26.1.0.0-alpha.15+pre-3
	 */
	private static final String REGEX_VERSION_NEOFORGE = "[0-9]+(?:\\.[0-9]+){1,5}(?:[-+][a-zA-Z0-9_.+-]+)?";

	private static final Pattern PATRON_ID_VERSION_JSON = Pattern
			.compile("\"id\"\\s*:\\s*\"neoforge-(" + REGEX_VERSION_NEOFORGE + ")\"", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_ARGUMENTO_NEOFORGE = Pattern.compile(
			"\"--fml\\.neoForgeVersion\"[\\s\\S]*?\"(" + REGEX_VERSION_NEOFORGE + ")\"", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_ARGUMENTO_MC = Pattern
			.compile("\"--fml\\.mcVersion\"[\\s\\S]*?\"(" + REGEX_VERSION_MC + ")\"", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_MAIN_CLASS_NEOFORGE = Pattern
			.compile("net\\.neoforged\\.fml\\.startup\\.(?:Client|Server)", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_RUTA_NEOFORGE = Pattern.compile(
			"(?:^|[/\\\\])net[/\\\\]neoforged[/\\\\]neoforge[/\\\\](" + REGEX_VERSION_NEOFORGE + ")[/\\\\]",
			Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_NOMBRE_NEOFORGE = Pattern
			.compile("net\\.neoforged:neoforge:(" + REGEX_VERSION_NEOFORGE + ")", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_JAR_NEOFORGE = Pattern
			.compile("neoforge-(" + REGEX_VERSION_NEOFORGE + ")\\.jar", Pattern.CASE_INSENSITIVE);

	static {
		for (Cargador carg : Cargador.cargadores_activados) {
			if (carg.id().equals("neoforge")) {
				// Tiene NeoForge.
			}
		}
	}

	public static class ResultadoNeoForge {
		public String versionMinecraft;
		public String versionNeoForge;
		public String fuente;

		public boolean encontrado() {
			return versionNeoForge != null && !versionNeoForge.trim().isEmpty();
		}

		@Override
		public String toString() {
			return "ResultadoNeoForge{" + "versionMinecraft='" + versionMinecraft + '\'' + ", versionNeoForge='"
					+ versionNeoForge + '\'' + ", fuente='" + fuente + '\'' + '}';
		}
	}

	public static ResultadoNeoForge detectarDesdeDirectorioActual() throws IOException {
		return detectarDesdeInstancia(Statics.CARPETA_DE_APP.toPath());
	}

	public static ResultadoNeoForge detectarDesdeSistema() throws IOException {
		ResultadoNeoForge r = detectarDesdeClasspath();
		if (r.encontrado()) {
			return r;
		}

		return detectarDesdeDirectorioActual();
	}

	public static ResultadoNeoForge detectarDesdeInstancia(Path carpetaInstancia) throws IOException {
		ResultadoNeoForge r;

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

		r = detectarDesdeTLauncherAdditional(carpetaInstancia);
		if (r.encontrado()) {
			return r;
		}

		return nuevoVacio("No encontrado");
	}

	public static ResultadoNeoForge detectarDesdeClasspath() {
		String cp = System.getProperty("java.class.path", "");
		ResultadoNeoForge r = detectarDesdeTexto(cp, "classpath");
		if (r.encontrado()) {
			return r;
		}

		String comando = System.getProperty("sun.java.command", "");
		return detectarDesdeTexto(comando, "sun.java.command");
	}

	public static ResultadoNeoForge detectarDesdeLibraries(Path carpetaInstancia) throws IOException {
		final ResultadoNeoForge[] encontrado = new ResultadoNeoForge[] { nuevoVacio("libraries") };

		Path libraries = carpetaInstancia.resolve("libraries");
		if (!Files.isDirectory(libraries)) {
			return encontrado[0];
		}

		Files.walkFileTree(libraries, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path archivo, BasicFileAttributes attrs) {
				String texto = archivo.toString().replace('\\', '/');

				ResultadoNeoForge r = detectarDesdeTexto(texto, "libraries");
				if (r.encontrado()) {
					encontrado[0] = r;
					return FileVisitResult.TERMINATE;
				}

				return FileVisitResult.CONTINUE;
			}
		});

		return encontrado[0];
	}

	public static ResultadoNeoForge detectarDesdeRunScripts(Path carpetaInstancia) throws IOException {
		ResultadoNeoForge r;

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

	public static ResultadoNeoForge detectarDesdeJsonesVersion(Path carpetaInstancia) throws IOException {
		final ResultadoNeoForge[] encontrado = new ResultadoNeoForge[] { nuevoVacio("json versiones") };

		Files.walkFileTree(carpetaInstancia, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path archivo, BasicFileAttributes attrs) throws IOException {
				String nombre = archivo.getFileName().toString().toLowerCase();

				if (!nombre.endsWith(".json")) {
					return FileVisitResult.CONTINUE;
				}

				if (!nombre.contains("neoforge")) {
					return FileVisitResult.CONTINUE;
				}

				ResultadoNeoForge r = detectarDesdeArchivoSiExiste(archivo, "json version NeoForge");
				if (r.encontrado()) {
					encontrado[0] = r;
					return FileVisitResult.TERMINATE;
				}

				return FileVisitResult.CONTINUE;
			}
		});

		return encontrado[0];
	}

	public static ResultadoNeoForge detectarDesdeTLauncherAdditional(Path carpetaInstancia) throws IOException {
		String[] nombres = { "TLauncherAdditional.json", "TLauncherAdditional(2).json", "TLauncherAdditional(3).json",
				"tlauncheradditional.json", "additional.json" };

		for (String nombre : nombres) {
			Path p = carpetaInstancia.resolve(nombre);
			ResultadoNeoForge r = detectarDesdeArchivoSiExiste(p, nombre);
			if (r.encontrado()) {
				return r;
			}
		}

		return nuevoVacio("TLauncherAdditional");
	}

	public static ResultadoNeoForge detectarDesdeArchivoSiExiste(Path archivo, String fuente) throws IOException {
		if (!Files.isRegularFile(archivo)) {
			return nuevoVacio(fuente);
		}

		return detectarDesdeTexto(leerArchivo(archivo), fuente);
	}

	public static ResultadoNeoForge detectarDesdeTexto(String texto, String fuente) {
		if (texto == null || texto.trim().isEmpty()) {
			return nuevoVacio(fuente);
		}

		ResultadoNeoForge r = nuevoVacio(fuente);

		String versionNeoForge = buscarPrimero(texto, PATRON_ID_VERSION_JSON, PATRON_ARGUMENTO_NEOFORGE,
				PATRON_NOMBRE_NEOFORGE, PATRON_RUTA_NEOFORGE, PATRON_JAR_NEOFORGE);

		String versionMinecraft = buscarPrimero(texto, PATRON_ARGUMENTO_MC);

		if (versionNeoForge != null) {
			r.versionNeoForge = versionNeoForge;
			r.versionMinecraft = versionMinecraft;
			r.fuente = fuente;
			return r;
		}

		/*
		 * Algunos JSON de launcher solo tienen mainClass neoforged y mcVersion. En ese
		 * caso sabemos que es NeoForge, pero no sabemos el build exacto.
		 */
		if (PATRON_MAIN_CLASS_NEOFORGE.matcher(texto).find()) {
			r.versionNeoForge = "";
			r.versionMinecraft = versionMinecraft;
			r.fuente = fuente + " / mainClass NeoForge sin build";
			return r;
		}

		return r;
	}

	private static String buscarPrimero(String texto, Pattern... patrones) {
		for (Pattern p : patrones) {
			Matcher m = p.matcher(texto);
			if (m.find()) {
				return m.group(1);
			}
		}

		return null;
	}

	private static ResultadoNeoForge nuevoVacio(String fuente) {
		ResultadoNeoForge r = new ResultadoNeoForge();
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
		Path carpeta = args.length > 0 ? java.nio.file.Paths.get(args[0]) : Statics.CARPETA_DE_APP.toPath();
		ResultadoNeoForge r = detectarDesdeInstancia(carpeta);

		System.out.println("Minecraft: " + r.versionMinecraft);
		System.out.println("NeoForge: " + r.versionNeoForge);
		System.out.println("Fuente: " + r.fuente);
	}
}