package com.asbestosstar.crashdetector.app;

import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Nuevo punto de entrada principal para el JAR.
 * 
 * - Si se ejecuta normalmente mediante "java -jar", engaña a MonitorDePID para
 * que abra directamente en modo IDE. Esto se logra pasando "--monitor", un PID
 * imposible (666666) y "--idemode". Al no existir dicho proceso en el sistema
 * operativo, el bucle de monitoreo termina al instante y lanza la GUI.
 * 
 * - Si el classloader actual es asignable a org.jboss.modules.ModuleClassLoader
 * (o cualquier subclase no-final), escribe un mensaje en la consola y no hace
 * nada más para evitar conflictos en entornos JBoss/WildFly.
 */
public class CrashDetectorApp {

	public static void main(String[] args) {
		if (esClassLoaderDeJBossModules()) {
			System.out.println("Se está ejecutando con un classloader de JBoss Modules, no se iniciará CrashDetector.");
			return;
		}

		// Simulamos el modo IDE original de tu configuración de ejecución:
		// Al pasarle un PID imposible (666666), la función nativa viva(pid)
		// retornará false instantáneamente, lo que fuerza a que la lógica de
		// monitor_proceso salte el bucle infinito y abra la GUI de inmediato.
		MonitorDePID.main(new String[] { "--monitor", "666666", "--idemode" });
	}

	/**
	 * Comprueba si el classloader actual pertenece al ecosistema de JBoss Modules.
	 * 
	 * En lugar de fiarnos de cadenas de texto, buscamos la clase base de JBoss en
	 * el classpath. Como ModuleClassLoader no es 'final', un classloader
	 * personalizado podría heredar de ella. Usamos isAssignableFrom sobre el
	 * .getClass() del classloader actual para cubrir la clase base y cualquier
	 * subclase.
	 * 
	 * @return true si se detecta que el classloader actual es de JBoss Modules,
	 *         false si no.
	 */
	private static boolean esClassLoaderDeJBossModules() {
		ClassLoader clActual = CrashDetectorApp.class.getClassLoader();

		// Si por alguna razón no hay classloader
		if (clActual == null) {
			return false;
		}

		try {
			// Intentamos cargar la clase base de JBoss Modules.
			// Si lanza ClassNotFoundException, significa que JBoss NO está en el classpath.
			Class<?> jbossModuleClassLoaderClass = Class.forName("org.jboss.modules.ModuleClassLoader", false,
					clActual);

			// Obtenemos la clase real de la instancia de nuestro classloader actual
			Class<?> claseRealDelClActual = clActual.getClass();

			// Verificamos si la clase de nuestro classloader ES un ModuleClassLoader
			// o si HEREDA de él (isAssignableFrom maneja la jerarquía correctamente).
			return jbossModuleClassLoaderClass.isAssignableFrom(claseRealDelClActual);

		} catch (ClassNotFoundException e) {
			// La clase de JBoss no existe en el classpath actual.
			// Estamos en un entorno normal (java -jar estándar, IDE, etc.).
			return false;
		}
	}
}