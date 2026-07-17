package com.asbestosstar.crashdetector.app;

import java.io.File;
import java.nio.file.Path;

import com.asbestosstar.crashdetector.CargadoresComun;
import com.asbestosstar.crashdetector.CargadoresComun.CDOrigin;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Transformaciones;

/**
 * Controlador principal de la aplicación independiente.
 *
 * La interpretación de argumentos pertenece a {@link CrashDetectorCli}; esta
 * clase se limita a preparar el entorno de la aplicación.
 */
public class CrashDetectorApp {

	/**
	 * Origen de ejecución detectado. Por defecto asume una ejecución directa desde
	 * JAR o IDE.
	 */
	private static CDOrigin origenActual = CDOrigin.STANDALONE;

	public static void main(String[] args) {
		if (esClassLoaderDeJBossModules()) {
			System.out.println("Se está ejecutando con un classloader de JBoss Modules, no se iniciará CrashDetector.");// No
																														// para
																														// JBM
																														// o
																														// FeatureCreep
			return;
		}

		if (CrashDetectorCli.procesarArgumentos(args)) {
			return;
		}

		/*
		 * La aplicación independiente no debe generar otro cd_launcherlog. Las entradas
		 * CLI ya se enviarán al monitor mediante --log o --log-temporal.
		 */
		MonitorDePID.forzarIdeMode = true;

		inicializarComoModloader();
	}

	/**
	 * Inicializa transformaciones y cargadores como una aplicación independiente.
	 */
	private static void inicializarComoModloader() {
		try {
			Transformaciones.init();
		} catch (Exception e) {
			System.err.println("Error al inicializar transformaciones en modo App:");
			e.printStackTrace();
		}

		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, origenActual);
	}

	/**
	 * Comprueba si el classloader actual pertenece al ecosistema JBoss Modules.
	 */
	private static boolean esClassLoaderDeJBossModules() {
		ClassLoader clActual = CrashDetectorApp.class.getClassLoader();

		if (clActual == null) {
			return false;
		}

		try {
			Class<?> jbossModuleClassLoaderClass = Class.forName("org.jboss.modules.ModuleClassLoader", false,
					clActual);
			return jbossModuleClassLoaderClass.isAssignableFrom(clActual.getClass());
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}