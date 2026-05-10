package com.asbestosstar.crashdetector.buscarparajava;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.asbestosstar.crashdetector.ConfigMundial;

/**
 * Busca un ejecutable "java" de Java 8 en sistemas Unix/macOS/Linux/BSD.
 *
 * Orden: 1. Revisar ruta guardada en ConfigMundial. 2. Buscar primero usando
 * java.home actual. 3. Buscar rutas comunes de macOS. 4. Buscar rutas comunes
 * de Linux. 5. Buscar rutas comunes de Solaris/Illumos/BSD. 6. Buscar runtimes
 * Java 8 de launchers de Minecraft. 7. Si nada sirve, caer en la lógica vieja
 * Unix de obtenerRutaEjecutable8().
 *
 * Reglas: - Solo acepta ejecutable llamado "java". - Verifica que realmente sea
 * Java 8 ejecutando: java -version. - Si encuentra una ruta válida, la guarda
 * en ConfigMundial. - Si no encuentra nada, deja la config mundial en blanco.
 */
public class BuscarParaJava8Unix {

	private static final String JAVA = "java";

	private BuscarParaJava8Unix() {
		// Clase utilitaria.
	}

	public static String encontrarJava8UnixConFallback(long pid) {
		String encontrado = encontrarJava8Unix();

		if (encontrado != null && !encontrado.trim().isEmpty()) {
			return encontrado;
		}

		return obtenerRutaEjecutableActualUnix(pid);
	}

	public static String encontrarJava8Unix() {
		ConfigMundial config = ConfigMundial.obtenerInstancia();

		// 1. Revisar primero la config mundial.
		String rutaGuardada = config.obtenerRutaEjecutableJava8();

		if (esJava8Valido(rutaGuardada)) {
			return new File(rutaGuardada).getAbsolutePath();
		}

		String encontrado = buscarEnCandidatos();

		if (encontrado != null && !encontrado.trim().isEmpty()) {
			String limpio = new File(encontrado).getAbsolutePath();
			config.guardarRutaEjecutableJava8(limpio);
			return limpio;
		}

		// Mantener la entrada mundial, pero en blanco.
		config.guardarRutaEjecutableJava8("");
		return null;
	}

	private static String buscarEnCandidatos() {
		for (String ruta : obtenerCandidatosJavaHomeActual()) {
			if (esJava8Valido(ruta)) {
				return ruta;
			}
		}

		for (String ruta : obtenerCandidatosMacOS()) {
			if (esJava8Valido(ruta)) {
				return ruta;
			}
		}

		for (String ruta : obtenerCandidatosLinux()) {
			if (esJava8Valido(ruta)) {
				return ruta;
			}
		}

		for (String ruta : obtenerCandidatosSolarisIllumosBSD()) {
			if (esJava8Valido(ruta)) {
				return ruta;
			}
		}

		for (String ruta : obtenerCandidatosLaunchersMinecraft()) {
			if (esJava8Valido(ruta)) {
				return ruta;
			}
		}

		return null;
	}

	private static List<String> obtenerCandidatosJavaHomeActual() {
		List<String> rutas = new ArrayList<String>();

		String javaHome = System.getProperty("java.home");

		if (javaHome != null && !javaHome.trim().isEmpty()) {
			agregarDirecto(rutas, javaHome + "/bin/java");

			// A veces java.home apunta a jre dentro del JDK.
			File home = new File(javaHome);
			File padre = home.getParentFile();
			if (padre != null) {
				agregarDirecto(rutas, padre.getAbsolutePath() + "/bin/java");
			}
		}

		return sinDuplicados(rutas);
	}

	private static List<String> obtenerCandidatosMacOS() {
		List<String> rutas = new ArrayList<String>();

		File raiz = new File("/Library/Java/JavaVirtualMachines");
		agregarCandidatosDeRaizMacOS(rutas, raiz);

		String home = System.getProperty("user.home");
		if (home != null) {
			agregarCandidatosDeRaizMacOS(rutas, new File(home + "/Library/Java/JavaVirtualMachines"));
		}

		// Rutas directas comunes.
		agregarDirecto(rutas, "/Library/Java/JavaVirtualMachines/jdk1.8.0.jdk/Contents/Home/bin/java");
		agregarDirecto(rutas, "/Library/Java/JavaVirtualMachines/temurin-8.jdk/Contents/Home/bin/java");
		agregarDirecto(rutas, "/Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/bin/java");
		agregarDirecto(rutas, "/Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/bin/java");
		agregarDirecto(rutas, "/Library/Java/JavaVirtualMachines/microsoft-8.jdk/Contents/Home/bin/java");

		return sinDuplicados(rutas);
	}

	private static List<String> obtenerCandidatosLinux() {
		List<String> rutas = new ArrayList<String>();

		// Debian/Ubuntu.
		agregarDirecto(rutas, "/usr/lib/jvm/java-8-openjdk-amd64/bin/java");
		agregarDirecto(rutas, "/usr/lib/jvm/java-8-openjdk-i386/bin/java");

		// RHEL/CentOS/Fedora.
		agregarDirecto(rutas, "/usr/lib/jvm/java-1.8.0-openjdk/bin/java");
		agregarDirecto(rutas, "/usr/lib/jvm/java-1.8.0-openjdk-amd64/bin/java");
		agregarDirecto(rutas, "/usr/lib/jvm/java-1.8.0-openjdk.x86_64/bin/java");

		// Oracle manual.
		agregarCandidatosDeRaiz(rutas, new File("/usr/java"));
		agregarCandidatosDeRaiz(rutas, new File("/opt"));

		// Vendors.
		agregarDirecto(rutas, "/usr/lib/jvm/java-1.8.0-amazon-corretto/bin/java");
		agregarDirecto(rutas, "/usr/lib/jvm/zulu-8-amd64/bin/java");

		// Buscar dentro de /usr/lib/jvm porque los nombres varían bastante.
		agregarCandidatosDeRaiz(rutas, new File("/usr/lib/jvm"));

		// SDKMAN.
		String home = System.getProperty("user.home");
		if (home != null) {
			agregarCandidatosDeRaiz(rutas, new File(home + "/.sdkman/candidates/java"));
		}

		return sinDuplicados(rutas);
	}

	private static List<String> obtenerCandidatosSolarisIllumosBSD() {
		List<String> rutas = new ArrayList<String>();

		// Solaris / Illumos.
		agregarDirecto(rutas, "/usr/jdk/instances/jdk1.8.0/bin/java");
		agregarDirecto(rutas, "/usr/jdk/openjdk1.8.0/bin/java");
		agregarDirecto(rutas, "/usr/java/bin/java");

		agregarCandidatosDeRaiz(rutas, new File("/usr/jdk"));
		agregarCandidatosDeRaiz(rutas, new File("/usr/java"));

		// FreeBSD / OpenBSD.
		agregarDirecto(rutas, "/usr/local/openjdk8/bin/java");
		agregarDirecto(rutas, "/usr/local/jdk1.8.0/bin/java");

		agregarCandidatosDeRaiz(rutas, new File("/usr/local"));

		// OpenLogic / Terenium y otros manuales.
		agregarDirecto(rutas, "/opt/openlogic/java-8/bin/java");
		agregarDirecto(rutas, "/opt/terenium/java-8/bin/java");

		return sinDuplicados(rutas);
	}

	private static List<String> obtenerCandidatosLaunchersMinecraft() {
		List<String> rutas = new ArrayList<String>();

		String home = System.getProperty("user.home");

		if (home == null || home.trim().isEmpty()) {
			return rutas;
		}

		// Vanilla Minecraft / TLauncher en macOS.
		agregarDirecto(rutas, home
				+ "/Library/Application Support/minecraft/runtime/jre-legacy/mac-os/jre-legacy/jre.bundle/Contents/Home/bin/java");

		// CurseForge macOS.
		agregarDirecto(rutas, home
				+ "/Documents/curseforge/minecraft/Install/runtime/jre-legacy/mac-os/jre-legacy/jre.bundle/Contents/Home/bin/java");

		// Vanilla Minecraft / TLauncher en Linux.
		agregarDirecto(rutas, home + "/.minecraft/runtime/jre-legacy/linux-x86/jre-legacy/bin/java");

		// CurseForge Linux.
		agregarDirecto(rutas,
				home + "/Documents/curseforge/minecraft/Install/runtime/jre-legacy/linux-x86/jre-legacy/bin/java");

		return sinDuplicados(rutas);
	}

	private static void agregarCandidatosDeRaizMacOS(List<String> rutas, File raiz) {
		if (raiz == null || !raiz.exists() || !raiz.isDirectory()) {
			return;
		}

		File[] hijos = raiz.listFiles();
		if (hijos == null) {
			return;
		}

		for (File hijo : hijos) {
			if (hijo == null || !hijo.isDirectory()) {
				continue;
			}

			String nombre = hijo.getName().toLowerCase();

			if (pareceCarpetaJava8(nombre)) {
				agregarDirecto(rutas, hijo.getAbsolutePath() + "/Contents/Home/bin/java");
			}
		}
	}

	private static void agregarCandidatosDeRaiz(List<String> rutas, File raiz) {
		agregarCandidatosDeRaiz(rutas, raiz, 0, 5);
	}

	private static void agregarCandidatosDeRaiz(List<String> rutas, File raiz, int profundidad, int maxProfundidad) {
		if (raiz == null || !raiz.exists() || profundidad > maxProfundidad) {
			return;
		}

		if (!raiz.isDirectory()) {
			return;
		}

		File bin = new File(raiz, "bin");
		if (bin.exists() && bin.isDirectory()) {
			agregarDirecto(rutas, new File(bin, JAVA).getAbsolutePath());
		}

		File[] hijos = raiz.listFiles();
		if (hijos == null) {
			return;
		}

		for (File hijo : hijos) {
			if (hijo == null || !hijo.isDirectory()) {
				continue;
			}

			String nombre = hijo.getName().toLowerCase();

			if (pareceCarpetaJava8(nombre) || profundidad < 1) {
				agregarCandidatosDeRaiz(rutas, hijo, profundidad + 1, maxProfundidad);
			}
		}
	}

	private static boolean pareceCarpetaJava8(String nombre) {
		if (nombre == null) {
			return false;
		}

		String n = nombre.toLowerCase();

		return n.contains("1.8") || n.contains("jdk8") || n.contains("jre8") || n.contains("jdk-8")
				|| n.contains("jre-8") || n.contains("java-8") || n.contains("java8") || n.contains("zulu-8")
				|| n.contains("temurin-8") || n.contains("corretto-8") || n.contains("amazon-corretto-8")
				|| n.contains("microsoft-8") || n.contains("liberica") || n.contains("graalvm-ce-java8")
				|| n.contains("8u") || n.contains("jdk1.8") || n.contains("jre1.8") || n.contains("openjdk8")
				|| n.contains("openjdk1.8") || n.contains("jre-legacy");
	}

	private static void agregarDirecto(List<String> rutas, String ruta) {
		if (ruta != null && !ruta.trim().isEmpty()) {
			rutas.add(ruta);
		}
	}

	private static List<String> sinDuplicados(List<String> rutas) {
		Set<String> set = new LinkedHashSet<String>();

		for (String ruta : rutas) {
			if (ruta != null && !ruta.trim().isEmpty()) {
				set.add(ruta);
			}
		}

		return new ArrayList<String>(set);
	}

	/**
	 * Verifica de forma estricta: - Existe. - Es archivo. - Se llama "java". - Al
	 * ejecutar -version, reporta Java 8.
	 */
	private static boolean esJava8Valido(String ruta) {
		if (ruta == null || ruta.trim().isEmpty()) {
			return false;
		}

		File archivo = new File(ruta.trim());

		if (!archivo.exists() || !archivo.isFile()) {
			return false;
		}

		if (!JAVA.equals(archivo.getName())) {
			return false;
		}

		String version = leerVersionJava(archivo);
		if (version == null || version.trim().isEmpty()) {
			return false;
		}

		String v = version.toLowerCase();

		// Oracle/OpenJDK Java 8 clásico:
		// java version "1.8.0_XXX"
		// openjdk version "1.8.0_XXX"
		if (v.contains("version \"1.8.")) {
			return true;
		}

		// Algunos builds modernos de Java 8 pueden imprimir:
		// openjdk version "8uXXX"
		// java version "8..."
		if (v.contains("version \"8u")) {
			return true;
		}

		if (v.contains("version \"8.")) {
			return true;
		}

		return false;
	}

	private static String leerVersionJava(File ejecutable) {
		Process proceso = null;

		try {
			ProcessBuilder pb = new ProcessBuilder(ejecutable.getAbsolutePath(), "-version");
			pb.redirectErrorStream(true);

			proceso = pb.start();

			boolean terminado = proceso.waitFor(4, TimeUnit.SECONDS);
			if (!terminado) {
				proceso.destroy();
				return null;
			}

			StringBuilder sb = new StringBuilder();

			try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
				String linea;

				while ((linea = lector.readLine()) != null) {
					sb.append(linea).append('\n');
				}
			}

			return sb.toString();
		} catch (Throwable t) {
			if (proceso != null) {
				try {
					proceso.destroy();
				} catch (Throwable ignorado) {
				}
			}

			return null;
		}
	}

	/**
	 * Lógica vieja de Unix/macOS desde obtenerRutaEjecutable8().
	 *
	 * Solo se usa si no se encuentra un binario Java 8 real.
	 */
	private static String obtenerRutaEjecutableActualUnix(long pid) {
		try {
			ProcessBuilder pb = new ProcessBuilder("ps", "-p", String.valueOf(pid), "-o", "comm=");
			Process proceso = pb.start();

			try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
				String linea;

				while ((linea = lector.readLine()) != null) {
					String limpiado = linea.trim();

					if (!limpiado.isEmpty()) {
						return limpiado;
					}
				}
			}

			return null;
		} catch (Exception e) {
			System.err.println("Error al obtener ruta ejecutable Unix: " + e.getMessage());
			return null;
		}
	}
}