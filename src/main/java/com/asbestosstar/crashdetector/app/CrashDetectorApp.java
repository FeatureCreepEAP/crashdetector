package com.asbestosstar.crashdetector.app;

import java.io.File;
import java.nio.file.Path;

import com.asbestosstar.crashdetector.CargadoresComun;
import com.asbestosstar.crashdetector.CargadoresComun.CDOrigin;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Transformaciones;

/**
 * Controlador principal de la aplicación.
 * 
 * Se comporta como un modloader normal (inicializando transformaciones y
 * cargadores) y asegura que, al momento de lanzar el proceso monitor hijo, se
 * pase implícitamente el argumento --idemode sin modificar la lógica de
 * construcción de ProcessBuilder en MonitorDePID.
 */
public class CrashDetectorApp {

	/**
	 * Origen de ejecución detectado. Por defecto asume que es una ejecución directa
	 * desde JAR o IDE (modo app independiente).
	 */
	private static CDOrigin origenActual = CDOrigin.STANDALONE;

	public static void main(String[] args) {
		// 1. Verificación de seguridad: Bloquear si estamos dentro de JBoss Modules
		if (esClassLoaderDeJBossModules()) {
			System.out.println("Se está ejecutando con un classloader de JBoss Modules, no se iniciará CrashDetector.");
			return;
		}

		// 2. Inicialización estilo Modloader (igual que hace CrashDetectorFlint)
		inicializarComoModloader();

		// 3. Forzamos el modo IDE para que el proceso hijo que se generará
		// más abajo no escriba en el disco duro.
		MonitorDePID.forzarIdeMode = true;

	}

	/**
	 * Inicializa el entorno de transformaciones y cargadores, simulando el
	 * comportamiento que tendría al ser cargado por FlintLoader, Forge, Fabric,
	 * etc.
	 */
	private static void inicializarComoModloader() {
		try {
			// Inicializamos transformaciones (necesario para el análisis de bytecode)
			Transformaciones.init();
		} catch (Exception e) {
			System.err.println("Error al inicializar transformaciones en modo App:");
			e.printStackTrace();
		}

		// Inicializamos el cargador común apuntando a la carpeta mods estándar.
		// Si el origen fuera otro, se podría cambiar, pero para un JAR standalone esto
		// es correcto.
		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, origenActual);
	}

	/**
	 * Comprueba si el classloader actual pertenece al ecosistema de JBoss Modules.
	 * 
	 * @return true si se detecta que el classloader actual es de JBoss Modules,
	 *         false si no.
	 */
	private static boolean esClassLoaderDeJBossModules() {
		ClassLoader clActual = CrashDetectorApp.class.getClassLoader();

		if (clActual == null) {
			return false;
		}

		try {
			// Intentamos cargar la clase base de JBoss Modules.
			Class<?> jbossModuleClassLoaderClass = Class.forName("org.jboss.modules.ModuleClassLoader", false,
					clActual);

			// Obtenemos la clase real de la instancia de nuestro classloader actual
			Class<?> claseRealDelClActual = clActual.getClass();

			// Verificamos si la clase de nuestro classloader ES o HEREDA de
			// ModuleClassLoader
			return jbossModuleClassLoaderClass.isAssignableFrom(claseRealDelClActual);

		} catch (ClassNotFoundException e) {
			// No estamos en JBoss
			return false;
		}
	}
}