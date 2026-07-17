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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detecta la versión de Minecraft Forge / MCForge usada por una instancia.
 *
 * Orden recomendado: 1. Classpath actual. 2. Carpeta libraries. 3. run.sh /
 * run.bat de servidor. 4. JSON adicional de TLauncher o respuesta tipo
 * CurseForge. 5. Formato viejo Maven de Forge.
 */
public class ObtenerVersionMCForge {

	public static class ResultadoMCForge {
		public String versionMinecraft;
		public String buildForge;
		public String versionForgeCompleta;
		public String fuente;

		public boolean encontrado() {
			return versionForgeCompleta != null && !versionForgeCompleta.trim().isEmpty();
		}

		@Override
		public String toString() {
			return "ResultadoMCForge{" + "versionMinecraft='" + versionMinecraft + '\'' + ", buildForge='" + buildForge
					+ '\'' + ", versionForgeCompleta='" + versionForgeCompleta + '\'' + ", fuente='" + fuente + '\''
					+ '}';
		}
	}

	private static final String REGEX_VERSION_MC = "[0-9]+\\.[0-9]+(?:\\.[0-9]+)?[a-zA-Z]?";

	private static final Pattern PATRON_FORGE_MODERNO = Pattern
			.compile(
					"(?:fmlcore|fmlloader|forge|javafmllanguage|mclanguage|lowcodelanguage)-" + "(" + REGEX_VERSION_MC
							+ ")-" + "([0-9]+(?:\\.[0-9]+){1,3})" + "(?:-[a-zA-Z0-9_\\-.]+)?\\.jar",
					Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_RUTA_FORGE_MODERNO = Pattern.compile(
			"libraries[/\\\\]net[/\\\\]minecraftforge[/\\\\](?:fmlcore|fmlloader|forge|javafmllanguage|mclanguage|lowcodelanguage)[/\\\\]"
					+ "(" + REGEX_VERSION_MC + ")-" + "([0-9]+(?:\\.[0-9]+){1,3})[/\\\\]",
			Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_ARGS_SERVIDOR = Pattern
			.compile(
					"libraries[/\\\\]net[/\\\\]minecraftforge[/\\\\]forge[/\\\\]" + "(" + REGEX_VERSION_MC + ")-"
							+ "([0-9]+(?:\\.[0-9]+){1,3})[/\\\\](?:unix_args|win_args)\\.txt",
					Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_FORGE_VIEJO_MAVEN = Pattern.compile("net\\.minecraftforge:forge:" + "("
			+ REGEX_VERSION_MC + ")-" + "([0-9]+(?:\\.[0-9]+){2,4})" + "(?:-" + REGEX_VERSION_MC + ")?",
			Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_NOMBRE_VERSION_MC = Pattern
			.compile("\"name\"\\s*:\\s*\"(" + REGEX_VERSION_MC + ")\"");

	private static final Pattern PATRON_VERSION_MC_EN_CADENA = Pattern
			.compile("(?<![0-9A-Za-z])(" + REGEX_VERSION_MC + ")(?![0-9A-Za-z])");

	private static final Pattern PATRON_GAME_VERSION_DTO = Pattern.compile("\"gameVersionsDTO\"\\s*:\\s*\\[(.*?)\\]",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private static final Pattern PATRON_GAME_VERSIONS_CF = Pattern.compile("\"gameVersions\"\\s*:\\s*\\[(.*?)\\]",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static ResultadoMCForge detectarDesdeDirectorioActual() throws IOException {
		return detectarDesdeInstancia(Statics.CARPETA_DE_APP.toPath());
	}

	public static ResultadoMCForge detectarDesdeSistema() throws IOException {
		ResultadoMCForge r = detectarDesdeClasspath();
		if (r.encontrado()) {
			return r;
		}

		return detectarDesdeDirectorioActual();
	}

	public static ResultadoMCForge detectarDesdeInstancia(Path carpetaInstancia) throws IOException {
		ResultadoMCForge r;

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

		r = detectarDesdeJsonTLauncherSiExiste(carpetaInstancia);
		if (r.encontrado()) {
			return r;
		}

		return nuevoVacio("No encontrado");
	}

	public static ResultadoMCForge detectarDesdeClasspath() {
		String cp = System.getProperty("java.class.path", "");
		ResultadoMCForge r = detectarDesdeTexto(cp, "classpath");
		if (r.encontrado()) {
			return r;
		}

		String comando = System.getProperty("sun.java.command", "");
		return detectarDesdeTexto(comando, "sun.java.command");
	}

	public static ResultadoMCForge detectarDesdeLibraries(Path carpetaInstancia) throws IOException {
		final ResultadoMCForge[] encontrado = new ResultadoMCForge[] { nuevoVacio("libraries") };

		Path libraries = carpetaInstancia.resolve("libraries");
		if (!Files.isDirectory(libraries)) {
			return encontrado[0];
		}

		Files.walkFileTree(libraries, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path archivo, BasicFileAttributes attrs) {
				String texto = archivo.toString().replace('\\', '/');

				ResultadoMCForge r = detectarDesdeTexto(texto, "libraries");
				if (r.encontrado()) {
					encontrado[0] = r;
					return FileVisitResult.TERMINATE;
				}

				return FileVisitResult.CONTINUE;
			}
		});

		return encontrado[0];
	}

	public static ResultadoMCForge detectarDesdeRunScripts(Path carpetaInstancia) throws IOException {
		ResultadoMCForge r;

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

	public static ResultadoMCForge detectarDesdeJsonTLauncherSiExiste(Path carpetaInstancia) throws IOException {
		String[] nombres = { "TLauncherAdditional.json", "TLauncherAdditional(2).json", "tlauncheradditional.json",
				"additional.json" };

		for (String nombre : nombres) {
			Path p = carpetaInstancia.resolve(nombre);
			ResultadoMCForge r = detectarDesdeArchivoSiExiste(p, nombre);
			if (r.encontrado()) {
				String versionJuego = detectarVersionMinecraftDesdeJson(p);
				if (r.versionMinecraft == null || r.versionMinecraft.isEmpty()) {
					r.versionMinecraft = versionJuego;
				}
				return r;
			}
		}

		return nuevoVacio("json TLauncher");
	}

	public static ResultadoMCForge detectarDesdeArchivoSiExiste(Path archivo, String fuente) throws IOException {
		if (!Files.isRegularFile(archivo)) {
			return nuevoVacio(fuente);
		}

		String texto = leerArchivo(archivo);
		return detectarDesdeTexto(texto, fuente);
	}

	public static ResultadoMCForge detectarDesdeTexto(String texto, String fuente) {
		if (texto == null) {
			return nuevoVacio(fuente);
		}

		ResultadoMCForge r;

		r = detectarConPatron(texto, PATRON_FORGE_MODERNO, fuente + " / jar moderno");
		if (r.encontrado()) {
			return r;
		}

		r = detectarConPatron(texto, PATRON_RUTA_FORGE_MODERNO, fuente + " / ruta libraries");
		if (r.encontrado()) {
			return r;
		}

		r = detectarConPatron(texto, PATRON_ARGS_SERVIDOR, fuente + " / run script servidor");
		if (r.encontrado()) {
			return r;
		}

		r = detectarConPatron(texto, PATRON_FORGE_VIEJO_MAVEN, fuente + " / Maven Forge viejo");
		if (r.encontrado()) {
			return r;
		}

		return nuevoVacio(fuente);
	}

	private static ResultadoMCForge detectarConPatron(String texto, Pattern patron, String fuente) {
		Matcher m = patron.matcher(texto);
		if (!m.find()) {
			return nuevoVacio(fuente);
		}

		String mc = m.group(1);
		String build = m.group(2);

		ResultadoMCForge r = new ResultadoMCForge();
		r.versionMinecraft = mc;
		r.buildForge = build;
		r.versionForgeCompleta = mc + "-" + build;
		r.fuente = fuente;
		return r;
	}

	public static String detectarVersionMinecraftDesdeJson(Path archivoJson) throws IOException {
		if (!Files.isRegularFile(archivoJson)) {
			return null;
		}

		return detectarVersionMinecraftDesdeJsonTexto(leerArchivo(archivoJson));
	}

	public static String detectarVersionMinecraftDesdeJsonTexto(String json) {
		if (json == null || json.trim().isEmpty()) {
			return null;
		}

		Map<String, Integer> votos = new LinkedHashMap<String, Integer>();

		contarVersionesEnBloques(json, PATRON_GAME_VERSION_DTO, votos);
		contarVersionesEnBloques(json, PATRON_GAME_VERSIONS_CF, votos);

		if (votos.isEmpty()) {
			Matcher m = PATRON_VERSION_MC_EN_CADENA.matcher(json);
			while (m.find()) {
				String v = m.group(1);
				agregarVoto(votos, v);
			}
		}

		return obtenerVersionConMasVotos(votos);
	}

	private static void contarVersionesEnBloques(String json, Pattern patronBloque, Map<String, Integer> votos) {
		Matcher bloques = patronBloque.matcher(json);

		while (bloques.find()) {
			String bloque = bloques.group(1);
			Matcher nombres = PATRON_NOMBRE_VERSION_MC.matcher(bloque);

			while (nombres.find()) {
				agregarVoto(votos, nombres.group(1));
			}

			Matcher versionesSimples = PATRON_VERSION_MC_EN_CADENA.matcher(bloque);
			while (versionesSimples.find()) {
				agregarVoto(votos, versionesSimples.group(1));
			}
		}
	}

	private static void agregarVoto(Map<String, Integer> votos, String version) {
		if (version == null || version.trim().isEmpty()) {
			return;
		}

		Integer actual = votos.get(version);
		votos.put(version, actual == null ? 1 : actual + 1);
	}

	private static String obtenerVersionConMasVotos(Map<String, Integer> votos) {
		String mejor = null;
		int mayor = 0;

		for (Map.Entry<String, Integer> e : votos.entrySet()) {
			if (e.getValue() > mayor) {
				mayor = e.getValue();
				mejor = e.getKey();
			}
		}

		return mejor;
	}

	private static ResultadoMCForge nuevoVacio(String fuente) {
		ResultadoMCForge r = new ResultadoMCForge();
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
		ResultadoMCForge r = detectarDesdeInstancia(carpeta);

		System.out.println("Minecraft: " + r.versionMinecraft);
		System.out.println("Forge build: " + r.buildForge);
		System.out.println("Forge completo: " + r.versionForgeCompleta);
		System.out.println("Fuente: " + r.fuente);
	}
}