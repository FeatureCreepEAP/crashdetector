package com.asbestosstar.crashdetector.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.CargadoresComun;
import com.asbestosstar.crashdetector.CargadoresComun.CDOrigin;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Transformaciones;

/**
 * Controlador principal de la aplicación.
 *
 * Se comporta como un modloader normal, pero también expone los comandos CLI de
 * CDGrepR y permite inyectar uno o varios archivos de log para que sean
 * analizados por el flujo normal de CrashDetector.
 */
public class CrashDetectorApp {

	/**
	 * Origen de ejecución detectado. Por defecto asume que es una ejecución directa
	 * desde JAR o IDE (modo app independiente).
	 */
	private static CDOrigin origenActual = CDOrigin.STANDALONE;

	public static void main(String[] args) {
		if (esClassLoaderDeJBossModules()) {
			System.out.println("Se está ejecutando con un classloader de JBoss Modules, no se iniciará CrashDetector.");
			return;
		}

		/*
		 * Procesa help, grepr, fgrepr, rutas de logs y texto literal de logs.
		 *
		 * Para grep y help devuelve true porque el comando ya terminó. Para los
		 * comandos de análisis guarda o materializa las entradas y devuelve false para
		 * permitir que el arranque normal cree el proceso hijo --monitor.
		 */
		if (MonitorDePID.procesarArgumentosCLIApp(args)) {
			return;
		}

		/*
		 * La versión App no debe crear un cd_launcherlog adicional. Las entradas CLI se
		 * enviarán al proceso monitor como --log o --log-temporal.
		 */
		MonitorDePID.forzarIdeMode = true;

		inicializarComoModloader();
	}

	private static boolean esComandoAnalizarLogs(String comando) {
		return "analizar".equals(comando) || "analyse".equals(comando) || "analyze".equals(comando)
				|| "logs".equals(comando) || "--analizar".equals(comando) || "--analyse".equals(comando)
				|| "--analyze".equals(comando) || "--logs".equals(comando);
	}

	/**
	 * Valida e inyecta los archivos de log recibidos después del subcomando.
	 *
	 * No llama finalizarContenido() ni finalizarContenidoInyectado(): esto conserva
	 * el mismo comportamiento que la inyección desde la GUI y permite que
	 * AnalizadorNuevo lea cada archivo mediante su ruta normal de streaming.
	 *
	 * @param args         argumentos completos del programa
	 * @param primerIndice primer índice que puede contener una ruta
	 * @return true si se inyectó al menos un archivo; false si hubo un error
	 */
	private static boolean inyectarArchivosLog(String[] args, int primerIndice) {
		if (primerIndice >= args.length) {
			System.err.println("Error: falta al menos un archivo de log para analizar.");
			mostrarAyudaCLI();
			return false;
		}

		List<File> archivosValidados = new ArrayList<File>();
		Set<String> rutasCanonicas = new LinkedHashSet<String>();

		for (int i = primerIndice; i < args.length; i++) {
			String argumento = args[i];

			// Permite: analizar -- archivo-con-nombre-que-empieza-con-guion.log
			if ("--".equals(argumento)) {
				continue;
			}

			if (argumento == null || argumento.trim().isEmpty()) {
				System.err.println("Error: se recibió una ruta de log vacía.");
				return false;
			}

			File archivo;
			try {
				archivo = new File(argumento).getCanonicalFile();
			} catch (IOException e) {
				System.err.println("Error al resolver la ruta del log: " + argumento);
				System.err.println(e.getMessage());
				return false;
			}

			if (!archivo.exists()) {
				System.err.println("Error: el archivo de log no existe: " + archivo.getAbsolutePath());
				return false;
			}

			if (!archivo.isFile()) {
				System.err.println("Error: la ruta no es un archivo: " + archivo.getAbsolutePath());
				return false;
			}

			if (!archivo.canRead()) {
				System.err.println("Error: no se puede leer el archivo de log: " + archivo.getAbsolutePath());
				return false;
			}

			String rutaCanonica = archivo.getAbsolutePath();
			if (rutasCanonicas.add(rutaCanonica)) {
				archivosValidados.add(archivo);
			}
		}

		if (archivosValidados.isEmpty()) {
			System.err.println("Error: no se recibió ningún archivo de log válido.");
			mostrarAyudaCLI();
			return false;
		}

		/*
		 * Solo modificamos el estado global después de validar todas las rutas. Así un
		 * argumento incorrecto no deja una inyección parcial.
		 */
		List<Consola> consolasNuevas = new ArrayList<Consola>();
		try {
			for (File archivo : archivosValidados) {
				consolasNuevas.add(new Consola(archivo.toPath()));
			}
		} catch (IOException e) {
			System.err.println("Error al preparar un archivo de log para el análisis:");
			System.err.println(e.getMessage());
			return false;
		}

		MonitorDePID.consolas.addAll(consolasNuevas);
		MonitorDePID.consola_de_launcher_inyectado = true;

		for (Consola consola : consolasNuevas) {
			System.out.println("Log inyectado para análisis: " + consola.archivo.toAbsolutePath());
		}

		return true;
	}

	private static void mostrarAyudaCLI() {
		System.out.println("Uso:");
		System.out.println("  java -jar CrashDetectorApp.jar");
		System.out.println("  java -jar CrashDetectorApp.jar grepr [-i] <regex> [directorio]");
		System.out.println("  java -jar CrashDetectorApp.jar fgrepr [-i] <texto> [directorio]");
		System.out.println("  java -jar CrashDetectorApp.jar analizar <log1> [log2 ...]");
		System.out.println();
		System.out.println("Comandos:");
		System.out.println("  grepr     Busca mediante una expresión regular.");
		System.out.println("  fgrepr    Busca texto literal, sin expresión regular.");
		System.out.println("  analizar  Inyecta uno o varios archivos de log y abre el análisis normal.");
		System.out.println();
		System.out.println("Opciones de grep:");
		System.out.println("  -i        Ignora mayúsculas y minúsculas.");
		System.out.println();
		System.out.println("Alias de analizar: analyse, analyze y logs.");
	}

	/**
	 * Inicializa el entorno de transformaciones y cargadores, simulando el
	 * comportamiento que tendría al ser cargado por FlintLoader, Forge, Fabric,
	 * etc.
	 */
	private static void inicializarComoModloader() {
		try {
			// Inicializamos transformaciones, necesarias para el análisis de bytecode.
			Transformaciones.init();
		} catch (Exception e) {
			System.err.println("Error al inicializar transformaciones en modo App:");
			e.printStackTrace();
		}

		// Para un JAR standalone, la carpeta estándar es mods/.
		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, origenActual);
	}

	/**
	 * Comprueba si el classloader actual pertenece al ecosistema de JBoss Modules.
	 *
	 * @return true si se detecta un ModuleClassLoader de JBoss; false en otro caso
	 */
	private static boolean esClassLoaderDeJBossModules() {
		ClassLoader clActual = CrashDetectorApp.class.getClassLoader();

		if (clActual == null) {
			return false;
		}

		try {
			Class<?> jbossModuleClassLoaderClass = Class.forName("org.jboss.modules.ModuleClassLoader", false,
					clActual);
			Class<?> claseRealDelClActual = clActual.getClass();
			return jbossModuleClassLoaderClass.isAssignableFrom(claseRealDelClActual);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}