package com.asbestosstar.crashdetector.app;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.security.CodeSource;
import java.util.Optional;

import com.asbestosstar.crashdetector.CargadoresComun;
import com.asbestosstar.crashdetector.CargadoresComun.CDOrigin;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Transformaciones;
import com.asbestosstar.crashdetector.cargador.LectorModuleXmlFC12;

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

		if (declaraOtraMainClassFC12()) {
			System.out.println("El module.xml de FeatureCreep 12 de este JAR declara otra main-class; "
					+ "CrashDetectorApp no se ejecutará como aplicación independiente.");
			return;
		}

		if (CrashDetectorCli.procesarArgumentos(args)) {
			int codigo = CrashDetectorCli.obtenerCodigoSalida();
			if (codigo != CrashDetectorCli.CODIGO_EXITO) {
				System.exit(codigo);
			}
			return;
		}

		/*
		 * La aplicación independiente no debe generar otro cd_launcherlog. Las entradas
		 * CLI ya se enviarán al monitor mediante --log o --log-temporal.
		 */
		MonitorDePID.forzarIdeMode = true;

		inicializarComoModloader();
		int codigo = CrashDetectorCli.obtenerCodigoSalida();
		if (codigo != CrashDetectorCli.CODIGO_EXITO) {
			System.exit(codigo);
		}
	}

	/**
	 * Un module.xml FC12 del origen tiene precedencia sobre el Main-Class del
	 * manifiesto: si declara otra clase principal, esa es la que debe ejecutarse.
	 */
	private static boolean declaraOtraMainClassFC12() {
		Optional<LectorModuleXmlFC12.Descriptor> descriptor = LectorModuleXmlFC12.buscarEnOrigen(origenDeCodigo());
		String mainClass = LectorModuleXmlFC12.resolverMainClass(descriptor, null);
		return !mainClass.isEmpty() && !mainClass.equals(CrashDetectorApp.class.getName());
	}

	private static File origenDeCodigo() {
		try {
			CodeSource codigo = CrashDetectorApp.class.getProtectionDomain().getCodeSource();
			if (codigo == null || codigo.getLocation() == null) {
				return null;
			}
			return new File(codigo.getLocation().toURI());
		} catch (URISyntaxException | SecurityException | IllegalArgumentException e) {
			return null;
		}
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