package com.asbestosstar.crashdetector.buscarparajava;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.asbestosstar.crashdetector.CSVParser;
import com.asbestosstar.crashdetector.ConfigMundial;

/**
 * Busca un ejecutable java.exe de Java 8 en Windows.
 *
 * Orden: 1. Revisar ruta guardada en ConfigMundial. 2. Buscar Java 8 de 64
 * bits. 3. Buscar Java 8 de 32 bits. 4. Buscar runtimes Java 8 de launchers de
 * Minecraft. 5. Si nada sirve, caer en la lógica vieja de Windows.
 *
 * Reglas: - Solo acepta java.exe. - No acepta javaw.exe. - Verifica que
 * realmente sea Java 8 ejecutando: java.exe -version. - Si encuentra una ruta
 * válida, la guarda en ConfigMundial. - Si no encuentra nada, deja la config
 * mundial en blanco.
 */
public class BuscarParaJava8Windows {

	private static final String JAVA_EXE = "java.exe";

	private BuscarParaJava8Windows() {
		// Clase utilitaria.
	}

	public static String encontrarJava8WindowsConFallback(long pid) {
		String encontrado = encontrarJava8Windows();

		if (encontrado != null && !encontrado.trim().isEmpty()) {
			return encontrado;
		}

		return obtenerRutaEjecutableActualWindows(pid);
	}

	public static String encontrarJava8Windows() {
		ConfigMundial config = ConfigMundial.obtenerInstancia();

		// 1. Revisar primero la config mundial.
		String rutaGuardada = config.obtenerRutaEjecutableJava8();

		if (esJava8Valido(rutaGuardada)) {
			return new File(rutaGuardada).getAbsolutePath();
		}

		// Si estaba vacía o inválida, buscar de nuevo.
		// Si no encontramos nada, al final se vuelve a guardar blanco.
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
		// Prioridad 64 bits.
		for (String ruta : obtenerCandidatos64Bits()) {
			if (esJava8Valido(ruta)) {
				return ruta;
			}
		}

		// Luego 32 bits.
		for (String ruta : obtenerCandidatos32Bits()) {
			if (esJava8Valido(ruta)) {
				return ruta;
			}
		}

		// Luego launchers.
		for (String ruta : obtenerCandidatosLaunchersMinecraft()) {
			if (esJava8Valido(ruta)) {
				return ruta;
			}
		}

		return null;
	}

	private static List<String> obtenerCandidatos64Bits() {
		List<String> rutas = new ArrayList<String>();

		String programFiles = env("ProgramFiles");
		if (programFiles == null || programFiles.trim().isEmpty()) {
			programFiles = "C:\\Program Files";
		}

		agregarCandidatosDeRaiz(rutas, new File(programFiles, "Java"));
		agregarCandidatosDeRaiz(rutas, new File(programFiles, "OpenJDK"));
		agregarCandidatosDeRaiz(rutas, new File(programFiles, "RedHat"));
		agregarCandidatosDeRaiz(rutas, new File(programFiles, "Eclipse Foundation"));
		agregarCandidatosDeRaiz(rutas, new File(programFiles, "Amazon Corretto"));
		agregarCandidatosDeRaiz(rutas, new File(programFiles, "Zulu"));
		agregarCandidatosDeRaiz(rutas, new File(programFiles, "Microsoft"));
		agregarCandidatosDeRaiz(rutas, new File(programFiles, "BellSoft"));

		agregarDirecto(rutas, programFiles + "\\Zulu\\zulu-8\\bin\\java.exe");
		agregarDirecto(rutas, programFiles + "\\BellSoft\\LibericaJDK-8-Full\\bin\\java.exe");

		return sinDuplicados(rutas);
	}

	private static List<String> obtenerCandidatos32Bits() {
		List<String> rutas = new ArrayList<String>();

		String programFilesX86 = env("ProgramFiles(x86)");
		if (programFilesX86 == null || programFilesX86.trim().isEmpty()) {
			programFilesX86 = "C:\\Program Files (x86)";
		}

		agregarCandidatosDeRaiz(rutas, new File(programFilesX86, "Java"));
		agregarCandidatosDeRaiz(rutas, new File(programFilesX86, "OpenJDK"));
		agregarCandidatosDeRaiz(rutas, new File(programFilesX86, "RedHat"));
		agregarCandidatosDeRaiz(rutas, new File(programFilesX86, "Eclipse Foundation"));
		agregarCandidatosDeRaiz(rutas, new File(programFilesX86, "Amazon Corretto"));
		agregarCandidatosDeRaiz(rutas, new File(programFilesX86, "Zulu"));
		agregarCandidatosDeRaiz(rutas, new File(programFilesX86, "Microsoft"));
		agregarCandidatosDeRaiz(rutas, new File(programFilesX86, "BellSoft"));

		return sinDuplicados(rutas);
	}

	private static List<String> obtenerCandidatosLaunchersMinecraft() {
		List<String> rutas = new ArrayList<String>();

		String localAppData = env("LOCALAPPDATA");
		String appData = env("APPDATA");
		String userHome = System.getProperty("user.home");

		if (localAppData != null) {
			// Minecraft Launcher Microsoft Store.
			agregarDirecto(rutas, localAppData
					+ "\\Packages\\Microsoft.4297127D64EC6_8wekyb3d8bbwe\\LocalCache\\Local\\runtime\\jre-legacy\\windows-x64\\jre-legacy\\bin\\java.exe");
		}

		// Minecraft Launcher legacy.
		agregarDirecto(rutas,
				"C:\\Program Files (x86)\\Minecraft Launcher\\runtime\\jre-legacy\\windows-x64\\jre-legacy\\bin\\java.exe");

		if (userHome != null) {
			// CurseForge moderno.
			agregarDirecto(rutas, userHome
					+ "\\curseforge\\minecraft\\Install\\runtime\\jre-legacy\\windows-x64\\jre-legacy\\bin\\java.exe");

			// CurseForge viejo.
			agregarDirecto(rutas, userHome
					+ "\\Documents\\Curse\\Minecraft\\Install\\runtime\\jre-legacy\\windows-x64\\jre-legacy\\bin\\java.exe");
		}

		if (appData != null) {
			// TLauncher / .minecraft común.
			agregarDirecto(rutas,
					appData + "\\.minecraft\\runtime\\jre-legacy\\windows-x64\\jre-legacy\\bin\\java.exe");

			// Algunos TLauncher guardan JVMs aquí.
			agregarCandidatosDeRaiz(rutas, new File(appData, ".tlauncher\\jvms"));
		}

		return sinDuplicados(rutas);
	}

	private static void agregarCandidatosDeRaiz(List<String> rutas, File raiz) {
		agregarCandidatosDeRaiz(rutas, raiz, 0, 6);
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
			agregarDirecto(rutas, new File(bin, JAVA_EXE).getAbsolutePath());
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

			if (pareceCarpetaJava8(nombre) || profundidad < 2) {
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
				|| n.contains("temurin-8") || n.contains("corretto-8") || n.contains("liberica") || n.contains("8u")
				|| n.contains("jdk1.8") || n.contains("jre1.8") || n.contains("jre-legacy");
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
	 * Verifica de forma estricta: - Existe. - Es archivo. - Se llama exactamente
	 * java.exe. - Al ejecutar -version, reporta Java 8.
	 */
	private static boolean esJava8Valido(String ruta) {
		if (ruta == null || ruta.trim().isEmpty()) {
			return false;
		}

		File archivo = new File(ruta.trim());

		if (!archivo.exists() || !archivo.isFile()) {
			return false;
		}

		if (!JAVA_EXE.equalsIgnoreCase(archivo.getName())) {
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

	private static String env(String clave) {
		try {
			return System.getenv(clave);
		} catch (Throwable t) {
			return null;
		}
	}

	/**
	 * Lógica vieja de Windows desde obtenerRutaEjecutable8().
	 *
	 * Solo se usa si no se encuentra java.exe de Java 8.
	 */
	private static String obtenerRutaEjecutableActualWindows(long pid) {
		try {
			ProcessBuilder pb = new ProcessBuilder("powershell",
					"Get-WmiObject -Class Win32_Process -Filter \"ProcessId = " + pid
							+ "\" | Select-Object -ExpandProperty ExecutablePath");

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

			pb = new ProcessBuilder("tasklist", "/FI", "PID eq " + pid, "/FO", "CSV", "/NH");
			proceso = pb.start();

			try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
				String linea;

				while ((linea = lector.readLine()) != null) {
					if (!linea.trim().isEmpty()) {
						String[] valores = new CSVParser().parsear(linea);

						if (valores.length > 0 && !valores[0].trim().isEmpty()) {
							return valores[0].trim();
						}
					}
				}
			}

			return null;
		} catch (Exception e) {
			System.err.println("Error al obtener ruta ejecutable Windows: " + e.getMessage());
			return null;
		}
	}
}