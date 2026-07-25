package com.asbestosstar.crashdetector.bajo.hw.cpu.ibmz;

import java.util.Locale;

/**
 * Configuración de IBM zIIP para IBM Semeru Runtime sobre z/OS.
 *
 *
 * - zIIP es administrado por z/OS y por la JVM de IBM. - El código Java
 * elegible puede ser enviado automáticamente a zIIP.
 */
public final class ZiipInit {

	private static final String ARGUMENTO_ZIIP = "-Xifa:on";

	private ZiipInit() {
	}

	/**
	 * Comprueba si la aplicación se está ejecutando sobre z/OS.
	 *
	 * zIIP es una característica de z/OS. No debe activarse solamente porque la
	 * arquitectura sea s390x, ya que Linux sobre IBM Z utiliza un modelo diferente
	 * de procesadores especializados.
	 */
	public static boolean esZos() {
		String sistema = System.getProperty("os.name", "");

		sistema = sistema.trim().toLowerCase(Locale.ROOT);

		return "z/os".equals(sistema) || sistema.contains("z/os");
	}

	/**
	 * Comprueba si la JVM parece ser IBM Semeru, IBM J9 u OpenJ9.
	 */
	public static boolean esJvmIBM() {
		String fabricante = System.getProperty("java.vendor", "");
		String nombreJvm = System.getProperty("java.vm.name", "");
		String nombreRuntime = System.getProperty("java.runtime.name", "");

		String descripcion = (fabricante + " " + nombreJvm + " " + nombreRuntime).toLowerCase(Locale.ROOT);

		return descripcion.contains("ibm") || descripcion.contains("semeru") || descripcion.contains("openj9")
				|| descripcion.contains("j9");
	}

	/**
	 * Indica si puede utilizarse la configuración zIIP de IBM.
	 */
	public static boolean disponible() {
		return esZos() && esJvmIBM();
	}

	/**
	 * Devuelve true cuando debe agregarse explícitamente -Xifa:on.
	 *
	 * IBM Semeru ya utiliza "on" de forma predeterminada, pero agregarlo
	 * explícitamente documenta que la aplicación desea permitir zIIP.
	 */
	public static boolean necesitaArgEspecialZiip() {
		return disponible();
	}

	/**
	 * Devuelve el argumento reconocido por IBM Semeru y IBM SDK sobre z/OS.
	 */
	public static String obtenerArgEspecialZiip() {
		return ARGUMENTO_ZIIP;
	}

	/**
	 * Descripción destinada al registro de diagnóstico.
	 */
	public static String descripcion() {
		if (!esZos()) {
			return "zIIP no disponible: el sistema operativo no es z/OS";
		}

		if (!esJvmIBM()) {
			return "zIIP no configurado: la JVM no parece IBM Semeru/OpenJ9";
		}

		return "IBM zIIP permitido mediante " + ARGUMENTO_ZIIP;
	}
}