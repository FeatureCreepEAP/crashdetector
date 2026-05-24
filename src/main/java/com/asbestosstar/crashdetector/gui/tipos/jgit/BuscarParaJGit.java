package com.asbestosstar.crashdetector.gui.tipos.jgit;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Statics;

/**
 * Localizador de JGit. Busca los JARs de JGit en el classpath actual y en
 * ~/crash_detector/jgit/.
 *
 * Esta clase NO importa JGit. Debe ser segura aunque JGit no exista.
 */
public class BuscarParaJGit {

	public static final File CARPETA_JGIT = new File(System.getProperty("user.home"), "crash_detector/deps");

	public static final File CARPETA_JGIT_INSTANCIA = Statics.carpeta.resolve("deps").toFile();

	// Compatibilidad legacy: ubicación vieja.
	public static final File CARPETA_JGIT_LEGACY = new File(System.getProperty("user.home"), "crash_detector/jgit");

	private static final String VERSION_JGIT = "5.13.3.202401111512-r";

	private static final List<DependenciaJGit> DEPENDENCIAS_REQUERIDAS = Arrays.asList(
			// JGit principales
			new DependenciaJGit("org.eclipse.jgit", "org.eclipse.jgit", VERSION_JGIT),
			new DependenciaJGit("org.eclipse.jgit", "org.eclipse.jgit.ssh.apache", VERSION_JGIT),
			new DependenciaJGit("org.eclipse.jgit", "org.eclipse.jgit.http.apache", VERSION_JGIT),
			new DependenciaJGit("org.eclipse.jgit", "org.eclipse.jgit.ui", VERSION_JGIT),
			new DependenciaJGit("org.eclipse.jgit", "org.eclipse.jgit.archive", VERSION_JGIT),
			new DependenciaJGit("org.eclipse.jgit", "org.eclipse.jgit.lfs", VERSION_JGIT),
			new DependenciaJGit("org.eclipse.jgit", "org.eclipse.jgit.gpg.bc", VERSION_JGIT),

			// Dependencias transitivas vistas en tu classpath
			new DependenciaJGit("com.googlecode.javaewah", "JavaEWAH", "1.1.13"),
			new DependenciaJGit("org.slf4j", "slf4j-api", "1.7.30"),
			new DependenciaJGit("org.slf4j", "jcl-over-slf4j", "1.7.30"),

			new DependenciaJGit("org.apache.sshd", "sshd-osgi", "2.7.0"),
			new DependenciaJGit("org.apache.sshd", "sshd-core", "2.7.0"),
			new DependenciaJGit("org.apache.sshd", "sshd-common", "2.7.0"),
			new DependenciaJGit("net.i2p.crypto", "eddsa", "0.3.0"),

			new DependenciaJGit("org.apache.httpcomponents", "httpclient", "4.5.13"),
			new DependenciaJGit("org.apache.httpcomponents", "httpcore", "4.4.14"),
			new DependenciaJGit("commons-logging", "commons-logging", "1.2"),
			new DependenciaJGit("commons-codec", "commons-codec", "1.11"),

			new DependenciaJGit("org.apache.commons", "commons-compress", "1.21"),
			new DependenciaJGit("org.osgi", "org.osgi.core", "4.3.1"),

			new DependenciaJGit("org.bouncycastle", "bcprov-jdk15on", "1.69"),
			new DependenciaJGit("org.bouncycastle", "bcutil-jdk15on", "1.69"),
			new DependenciaJGit("org.bouncycastle", "bcpkix-jdk15on", "1.69"));

	private static final List<String> CLASES_REQUERIDAS_MINIMAS = Arrays.asList("org.eclipse.jgit.api.Git",
			"org.eclipse.jgit.lib.Repository", "org.eclipse.jgit.api.PushCommand");

	public static boolean estaJGitBasicoEnClasspath() {
		for (String clase : CLASES_REQUERIDAS_MINIMAS) {
			if (!existeClase(clase)) {
				CrashDetectorLogger.log("JGit no disponible. Falta clase: " + clase);
				return false;
			}
		}

		return true;
	}

	public static List<DependenciaJGit> dependenciasRequeridas() {
		return DEPENDENCIAS_REQUERIDAS;
	}

	public static List<DependenciaJGit> dependenciasFaltantesEnClasspath() {
		List<DependenciaJGit> faltantes = new ArrayList<DependenciaJGit>();
		String cp = System.getProperty("java.class.path", "");

		for (DependenciaJGit dep : DEPENDENCIAS_REQUERIDAS) {
			if (!classpathContieneDependencia(cp, dep)) {
				faltantes.add(dep);
			}
		}

		return faltantes;
	}

	public static boolean estanTodosLosArtefactosEnClasspath() {
		/*
		 * Primero usar prueba real de clases. Esto es más confiable que leer
		 * java.class.path, especialmente en Eclipse, Maven, ModLauncher o classloaders
		 * personalizados.
		 */
		if (!estaJGitBasicoEnClasspath()) {
			return false;
		}

		/*
		 * Si JGit básico carga, consideramos que está disponible para usar. La lista
		 * completa de JARs todavía se usa para instalación en ~/crash_detector/jgit/,
		 * pero no debe bloquear la GUI en Eclipse.
		 */
		return true;
	}

	private static boolean classpathContieneDependencia(String cp, DependenciaJGit dependencia) {
		String[] partes = cp.split(java.io.File.pathSeparator);

		for (String parte : partes) {
			String nombre = new File(parte).getName();

			if (dependencia.coincideConNombreJar(nombre)) {
				return true;
			}
		}

		return false;
	}

	private static boolean classpathContieneArtefacto(String cp, String artefacto) {
		String[] partes = cp.split(java.io.File.pathSeparator);

		String buscado = normalizarNombreJar(artefacto);

		for (String parte : partes) {
			String nombre = new File(parte).getName();

			if (!nombre.toLowerCase().endsWith(".jar")) {
				continue;
			}

			String normalizado = normalizarNombreJar(nombre);

			if (normalizado.startsWith(buscado + "-") || normalizado.equals(buscado)) {
				return true;
			}
		}

		return false;
	}

	private static String normalizarNombreJar(String nombre) {
		if (nombre == null) {
			return "";
		}

		String n = nombre.trim().toLowerCase();

		if (n.endsWith(".jar")) {
			n = n.substring(0, n.length() - 4);
		}

		n = n.replace('.', '-');

		return n;
	}

	public static boolean existeClase(String nombreClase) {
		if (nombreClase == null || nombreClase.trim().isEmpty()) {
			return false;
		}

		// 1. ClassLoader de esta clase
		try {
			Class.forName(nombreClase, false, BuscarParaJGit.class.getClassLoader());
			return true;
		} catch (Throwable t) {
			// seguir intentando
		}

		// 2. Context ClassLoader del hilo actual
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();

			if (cl != null) {
				Class.forName(nombreClase, false, cl);
				return true;
			}
		} catch (Throwable t) {
			// seguir intentando
		}

		// 3. System ClassLoader
		try {
			Class.forName(nombreClase, false, ClassLoader.getSystemClassLoader());
			return true;
		} catch (Throwable t) {
			// seguir intentando
		}

		// 4. Fallback simple
		try {
			Class.forName(nombreClase);
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.log(
					"No se pudo cargar clase: " + nombreClase + " / " + t.getClass().getName() + ": " + t.getMessage());
			return false;
		}
	}

	/**
	 * Localizador de JGit. Busca los JARs de JGit en el classpath actual, en
	 * ~/crash_detector/deps/ y, por compatibilidad legacy, en
	 * ~/crash_detector/jgit/.
	 *
	 * Esta clase NO importa JGit. Debe ser segura aunque JGit no exista.
	 */
	public static List<File> encontrarJarsInstalados() {
		List<File> ret = new ArrayList<File>();
		List<String> nombresYaAgregados = new ArrayList<String>();

		CARPETA_JGIT.mkdirs();

		// Primero la ubicación nueva/oficial.
		agregarJarsDeCarpetaSinDuplicarPorNombre(ret, nombresYaAgregados, CARPETA_JGIT);

		// Después la carpeta deps de la instancia.
		agregarJarsDeCarpetaSinDuplicarPorNombre(ret, nombresYaAgregados, CARPETA_JGIT_INSTANCIA);

		// Legacy: intentar mover a la ubicación universal; si falla, usar como
		// fallback.
		moverLegacyAUniversal();

		// Legacy solo si el mismo nombre de JAR no existe en deps o instancia.
		agregarJarsDeCarpetaSinDuplicarPorNombre(ret, nombresYaAgregados, CARPETA_JGIT_LEGACY);

		return ret;
	}

	private static void moverLegacyAUniversal() {
		if (CARPETA_JGIT_LEGACY == null || !CARPETA_JGIT_LEGACY.exists() || !CARPETA_JGIT_LEGACY.isDirectory()) {
			return;
		}

		CARPETA_JGIT.mkdirs();

		File[] archivos = CARPETA_JGIT_LEGACY.listFiles();

		if (archivos == null) {
			return;
		}

		for (File archivo : archivos) {
			if (archivo == null || !archivo.isFile()) {
				continue;
			}

			String nombre = archivo.getName();

			if (!nombre.toLowerCase().endsWith(".jar")) {
				continue;
			}

			File destino = new File(CARPETA_JGIT, nombre);

			if (!destino.exists()) {
				moverArchivo(archivo, destino);
			}
		}
	}

	private static boolean moverArchivo(File origen, File destino) {
		if (origen == null || destino == null || !origen.exists() || !origen.isFile()) {
			return false;
		}

		try {
			File carpetaDestino = destino.getParentFile();

			if (carpetaDestino != null) {
				carpetaDestino.mkdirs();
			}

			if (destino.exists()) {
				return true;
			}

			return origen.renameTo(destino);
		} catch (Throwable t) {
			return false;
		}
	}

	private static void agregarJarsDeCarpetaSinDuplicarPorNombre(List<File> ret, List<String> nombresYaAgregados,
			File carpeta) {
		if (carpeta == null || !carpeta.exists() || !carpeta.isDirectory()) {
			return;
		}

		File[] archivos = carpeta.listFiles();

		if (archivos == null) {
			return;
		}

		for (File archivo : archivos) {
			if (archivo == null || !archivo.isFile()) {
				continue;
			}

			String nombre = archivo.getName();

			if (!nombre.toLowerCase().endsWith(".jar")) {
				continue;
			}

			String nombreNormalizado = nombre.toLowerCase();

			if (nombresYaAgregados.contains(nombreNormalizado)) {
				continue;
			}

			nombresYaAgregados.add(nombreNormalizado);
			ret.add(archivo);
		}
	}

	private static void agregarJarsDeCarpeta(List<File> ret, File carpeta) {
		if (carpeta == null || !carpeta.exists() || !carpeta.isDirectory()) {
			return;
		}

		File[] archivos = carpeta.listFiles();

		if (archivos == null) {
			return;
		}

		for (File archivo : archivos) {
			if (archivo != null && archivo.isFile() && archivo.getName().toLowerCase().endsWith(".jar")) {
				ret.add(archivo);
			}
		}
	}

	public static boolean carpetaTieneJarsJGit() {
		return !encontrarJarsInstalados().isEmpty();
	}

	public static List<DependenciaJGit> dependenciasFaltantesEnCarpetaInstalacion() {
		List<DependenciaJGit> faltantes = new ArrayList<DependenciaJGit>();

		/*
		 * Nueva lógica: - La carpeta oficial actual es ~/crash_detector/deps/. - La
		 * carpeta legacy ~/crash_detector/jgit/ solo cuenta como fallback. - Si el
		 * mismo JAR existe en deps, se ignora el legacy. - Para instalaciones futuras,
		 * los faltantes se deben descargar solo en deps.
		 */
		List<File> jarsInstalados = encontrarJarsInstalados();

		for (DependenciaJGit dep : dependenciasRequeridas()) {
			boolean encontrada = false;

			for (File archivo : jarsInstalados) {
				if (archivo == null || !archivo.isFile()) {
					continue;
				}

				if (dep.coincideConNombreJar(archivo.getName()) && archivo.length() > 0L) {
					encontrada = true;
					break;
				}
			}

			if (!encontrada) {
				faltantes.add(dep);
			}
		}

		return faltantes;
	}

}