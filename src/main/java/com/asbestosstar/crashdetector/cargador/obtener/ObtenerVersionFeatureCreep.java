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

public class ObtenerVersionFeatureCreep {

	/*
	 * Loader id: featurecreep
	 */
	public static final String ID_CARGADOR = "featurecreep";

	/*
	 * Ejemplos soportados:
	 *
	 * featurecreepmc-26.1-6.jar -> 6 featurecreepdz-2.8-6.jar -> 6
	 * featurecreeptc-2412-6.jar -> 6 featurecreepnxopen-2412-6.jar -> 6
	 * featurecreepl2dcubism-3-6.jar -> 6 featurecreepmc-1.21.10-5am0.jar -> 5am0
	 * featurecreep-4.0AM18-1.20.5.el9fc4.noarch.fpm.jar -> 4.0AM18
	 * featurecreep-4.0AM18-1.20.5FabricMC.el9fc4.noarch.fpm.jar -> 4.0AM18
	 * featurecreep-loader-5.0.jar -> 5.0
	 */
	private static final Pattern PATRON_FEATURECREEP_LOADER = Pattern
			.compile("(?:^|[/\\\\])featurecreep-loader-([a-zA-Z0-9_.+-]+)\\.jar(?:$|[;:])?", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_FEATURECREEP_FPM = Pattern.compile(
			"(?:^|[/\\\\])featurecreep-([a-zA-Z0-9_.+-]+)-[^/\\\\]*\\.fpm\\.jar(?:$|[;:])?", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_FEATURECREEP_MODULO = Pattern.compile(
			"(?:^|[/\\\\])featurecreep(?:mc|dz|tc|nxopen|l2dcubism)-[^/\\\\]+-([a-zA-Z0-9_.+-]+)\\.jar(?:$|[;:])?",
			Pattern.CASE_INSENSITIVE);

	static {
		for (Cargador carg : Cargador.cargadores_activados) {
			if (carg.id().equals(ID_CARGADOR)) {
				// Tiene FeatureCreep.
			}
		}
	}

	public static class ResultadoFeatureCreep {
		public String versionFeatureCreep;
		public String archivo;
		public String fuente;

		public boolean encontrado() {
			return versionFeatureCreep != null && !versionFeatureCreep.trim().isEmpty();
		}

		@Override
		public String toString() {
			return "ResultadoFeatureCreep{" + "versionFeatureCreep='" + versionFeatureCreep + '\'' + ", archivo='"
					+ archivo + '\'' + ", fuente='" + fuente + '\'' + '}';
		}
	}

	public static ResultadoFeatureCreep detectarDesdeDirectorioActual() throws IOException {
		return detectarDesdeInstancia(Statics.CARPETA_DE_APP.toPath());
	}

	public static ResultadoFeatureCreep detectarDesdeSistema() throws IOException {
		ResultadoFeatureCreep r = detectarDesdeClasspath();
		if (r.encontrado()) {
			return r;
		}

		return detectarDesdeDirectorioActual();
	}

	public static ResultadoFeatureCreep detectarDesdeInstancia(Path carpetaInstancia) throws IOException {
		ResultadoFeatureCreep r;

		r = detectarDesdeClasspath();
		if (r.encontrado()) {
			return r;
		}

		r = detectarDesdeMods(carpetaInstancia);
		if (r.encontrado()) {
			return r;
		}

		r = detectarDesdeLibraries(carpetaInstancia);
		if (r.encontrado()) {
			return r;
		}

		return nuevoVacio("No encontrado");
	}

	public static ResultadoFeatureCreep detectarDesdeClasspath() {
		String cp = System.getProperty("java.class.path", "");
		ResultadoFeatureCreep r = detectarDesdeTexto(cp, "classpath");
		if (r.encontrado()) {
			return r;
		}

		String comando = System.getProperty("sun.java.command", "");
		return detectarDesdeTexto(comando, "sun.java.command");
	}

	public static ResultadoFeatureCreep detectarDesdeMods(Path carpetaInstancia) throws IOException {
		Path mods = carpetaInstancia.resolve("mods");
		return detectarDesdeCarpeta(mods, "mods");
	}

	public static ResultadoFeatureCreep detectarDesdeLibraries(Path carpetaInstancia) throws IOException {
		Path libraries = carpetaInstancia.resolve("libraries");
		return detectarDesdeCarpeta(libraries, "libraries");
	}

	public static ResultadoFeatureCreep detectarDesdeCarpeta(Path carpeta, final String fuente) throws IOException {
		final ResultadoFeatureCreep[] encontrado = new ResultadoFeatureCreep[] { nuevoVacio(fuente) };

		if (!Files.isDirectory(carpeta)) {
			return encontrado[0];
		}

		Files.walkFileTree(carpeta, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path archivo, BasicFileAttributes attrs) {
				String texto = archivo.toString().replace('\\', '/');

				ResultadoFeatureCreep r = detectarDesdeTexto(texto, fuente);
				if (r.encontrado()) {
					encontrado[0] = r;
					return FileVisitResult.TERMINATE;
				}

				return FileVisitResult.CONTINUE;
			}
		});

		return encontrado[0];
	}

	public static ResultadoFeatureCreep detectarDesdeArchivoSiExiste(Path archivo, String fuente) throws IOException {
		if (!Files.isRegularFile(archivo)) {
			return nuevoVacio(fuente);
		}

		return detectarDesdeTexto(leerArchivo(archivo), fuente);
	}

	public static ResultadoFeatureCreep detectarDesdeTexto(String texto, String fuente) {
		if (texto == null || texto.trim().isEmpty()) {
			return nuevoVacio(fuente);
		}

		ResultadoFeatureCreep r;

		/*
		 * Primero loader explícito: featurecreep-loader-5.0.jar -> 5.0
		 */
		r = detectarConPatron(texto, PATRON_FEATURECREEP_LOADER, fuente);
		if (r.encontrado()) {
			return r;
		}

		/*
		 * Luego formato FPM: featurecreep-4.0AM18-1.20.5.el9fc4.noarch.fpm.jar ->
		 * 4.0AM18
		 */
		r = detectarConPatron(texto, PATRON_FEATURECREEP_FPM, fuente);
		if (r.encontrado()) {
			return r;
		}

		/*
		 * Luego módulos: featurecreepmc-26.1-6.jar -> 6 featurecreepmc-1.21.10-5am0.jar
		 * -> 5am0
		 */
		r = detectarConPatron(texto, PATRON_FEATURECREEP_MODULO, fuente);
		if (r.encontrado()) {
			return r;
		}

		return nuevoVacio(fuente);
	}

	private static ResultadoFeatureCreep detectarConPatron(String texto, Pattern patron, String fuente) {
		Matcher m = patron.matcher(texto);

		if (!m.find()) {
			return nuevoVacio(fuente);
		}

		ResultadoFeatureCreep r = new ResultadoFeatureCreep();
		r.versionFeatureCreep = m.group(1);
		r.archivo = extraerArchivo(m.group(0));
		r.fuente = fuente;
		return r;
	}

	private static String extraerArchivo(String texto) {
		if (texto == null) {
			return "";
		}

		String limpio = texto.replace('\\', '/');

		int separadorClasspath = Math.max(limpio.lastIndexOf(';'), limpio.lastIndexOf(':'));
		if (separadorClasspath >= 0 && separadorClasspath + 1 < limpio.length()) {
			limpio = limpio.substring(separadorClasspath + 1);
		}

		int slash = limpio.lastIndexOf('/');
		if (slash >= 0 && slash + 1 < limpio.length()) {
			return limpio.substring(slash + 1);
		}

		return limpio;
	}

	private static ResultadoFeatureCreep nuevoVacio(String fuente) {
		ResultadoFeatureCreep r = new ResultadoFeatureCreep();
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
		ResultadoFeatureCreep r = detectarDesdeInstancia(carpeta);

		System.out.println("FeatureCreep: " + r.versionFeatureCreep);
		System.out.println("Archivo: " + r.archivo);
		System.out.println("Fuente: " + r.fuente);
	}
}