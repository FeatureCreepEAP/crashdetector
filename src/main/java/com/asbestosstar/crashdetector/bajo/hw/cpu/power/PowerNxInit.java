package com.asbestosstar.crashdetector.bajo.hw.cpu.power;

import java.io.File;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Inicialización del acelerador de compresión NX de IBM Power.
 *
 * Esta integración está destinada a:
 *
 * - AIX. - Arquitectura IBM Power. - Eclipse OpenJ9 / IBM Semeru. - zlibNX
 * instalado. - Una versión de OpenJ9 que reconozca -XX:+UseZlibNX.
 *
 * NX acelera compresión y descompresión compatibles con zlib. No acelera
 * búsquedas arbitrarias de bytes, por lo que no debe implementarse como un
 * MotorBusquedaBytes.
 */
public final class PowerNxInit {

	private static final String ARGUMENTO_ZLIB_NX = "-XX:+UseZlibNX";

	/*
	 * Rutas documentadas de zlibNX en AIX.
	 *
	 * La biblioteca dinámica normalmente se entrega como libz.a. También se
	 * comprueban nombres de objeto compartido para tolerar instalaciones diferentes
	 * o futuras.
	 */
	private static final String[] RUTAS_ZLIB_NX = { "/usr/opt/zlibNX/lib/libz.a", "/usr/opt/zlibNX/lib/libz.so",
			"/usr/opt/zlibNX/lib/libz.so.1", "/usr/opt/zlibNX/static/lib/libz.a" };

	/*
	 * -XX:+UseZlibNX apareció en Eclipse OpenJ9 0.41.0.
	 *
	 * En versiones anteriores, OpenJ9 podía utilizar zlibNX automáticamente, pero
	 * no necesariamente reconocía este argumento. Por seguridad, no se entrega el
	 * argumento a una versión anterior o desconocida.
	 */
	private static final int VERSION_OPENJ9_MINIMA_MAYOR = 0;
	private static final int VERSION_OPENJ9_MINIMA_MENOR = 41;

	private static final Pattern PATRON_VERSION_OPENJ9 = Pattern
			.compile("openj9[-_\\s]*([0-9]+)\\.([0-9]+)(?:\\.([0-9]+))?", Pattern.CASE_INSENSITIVE);

	private PowerNxInit() {
	}

	/**
	 * Comprueba si el sistema operativo es AIX.
	 */
	public static boolean esAix() {
		String sistema = System.getProperty("os.name", "").trim().toLowerCase(Locale.ROOT);

		return "aix".equals(sistema) || sistema.startsWith("aix ");
	}

	/**
	 * Comprueba si la JVM se está ejecutando sobre una arquitectura IBM Power.
	 *
	 * AIX suele informar ppc, ppc64 o powerpc.
	 */
	public static boolean esArquitecturaPower() {
		String arquitectura = System.getProperty("os.arch", "").trim().toLowerCase(Locale.ROOT);

		return arquitectura.equals("ppc") || arquitectura.equals("ppc64") || arquitectura.equals("ppc64le")
				|| arquitectura.equals("powerpc") || arquitectura.equals("powerpc64")
				|| arquitectura.equals("powerpc64le") || arquitectura.contains("power");
	}

	/**
	 * Comprueba si la JVM parece ser Eclipse OpenJ9, IBM Semeru o IBM J9.
	 */
	public static boolean esJvmOpenJ9() {
		String descripcion = (System.getProperty("java.vm.name", "") + " " + System.getProperty("java.vm.info", "")
				+ " " + System.getProperty("java.vendor", "") + " " + System.getProperty("java.runtime.name", ""))
				.toLowerCase(Locale.ROOT);

		return descripcion.contains("openj9") || descripcion.contains("eclipse openj9")
				|| descripcion.contains("semeru") || descripcion.contains("ibm j9");
	}

	/**
	 * Comprueba si existe una instalación conocida de zlibNX.
	 *
	 * La presencia del archivo no garantiza por sí sola que el acelerador esté
	 * habilitado por el firmware o por la configuración de la LPAR, pero evita
	 * añadir el argumento en sistemas donde la biblioteca ni siquiera existe.
	 */
	public static boolean tieneZlibNxInstalado() {
		for (String ruta : RUTAS_ZLIB_NX) {
			File archivo = new File(ruta);

			if (archivo.isFile()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Comprueba si la versión actual de OpenJ9 reconoce -XX:+UseZlibNX.
	 *
	 * La versión se extrae de propiedades como java.vm.version y java.vm.info,
	 * donde normalmente aparece un texto parecido a:
	 *
	 * openj9-0.48.0
	 */
	public static boolean soportaArgumentoUseZlibNx() {
		int[] version = obtenerVersionOpenJ9();

		if (version == null) {
			return false;
		}

		if (version[0] > VERSION_OPENJ9_MINIMA_MAYOR) {
			return true;
		}

		return version[0] == VERSION_OPENJ9_MINIMA_MAYOR && version[1] >= VERSION_OPENJ9_MINIMA_MENOR;
	}

	/**
	 * Indica si Power NX puede activarse de forma conservadora.
	 *
	 * No intenta detectar directamente POWER9, POWER10 o POWER11 porque no hay una
	 * propiedad Java estándar que exponga esa generación. La instalación de zlibNX
	 * se usa como indicio administrado por AIX.
	 */
	public static boolean disponible() {
		return esAix() && esArquitecturaPower() && esJvmOpenJ9() && tieneZlibNxInstalado()
				&& soportaArgumentoUseZlibNx();
	}

	/**
	 * Indica si debe agregarse el argumento especial al nuevo proceso Java.
	 */
	public static boolean necesitaArgEspecialPowerNx() {
		return disponible();
	}

	/**
	 * Devuelve el argumento que debe insertarse antes de -cp, -jar o la clase
	 * principal del nuevo proceso Java.
	 */
	public static String obtenerArgEspecialPowerNx() {
		return ARGUMENTO_ZLIB_NX;
	}

	/**
	 * Devuelve una descripción apropiada para el registro de diagnóstico.
	 */
	public static String descripcion() {
		if (!esAix()) {
			return "IBM Power NX no disponible: el sistema operativo no es AIX";
		}

		if (!esArquitecturaPower()) {
			return "IBM Power NX no disponible: la arquitectura no es IBM Power";
		}

		if (!esJvmOpenJ9()) {
			return "IBM Power NX no configurado: la JVM no parece OpenJ9/Semeru";
		}

		if (!tieneZlibNxInstalado()) {
			return "IBM Power NX no disponible: no se encontró zlibNX";
		}

		if (!soportaArgumentoUseZlibNx()) {
			return "IBM Power NX no configurado: la versión de OpenJ9 no reconoce " + ARGUMENTO_ZLIB_NX;
		}

		return "IBM Power NX habilitado mediante " + ARGUMENTO_ZLIB_NX;
	}

	/**
	 * Devuelve la versión OpenJ9 como {mayor, menor, parche}.
	 */
	private static int[] obtenerVersionOpenJ9() {
		String descripcion = System.getProperty("java.vm.version", "") + " " + System.getProperty("java.vm.info", "")
				+ " " + System.getProperty("java.runtime.version", "");

		Matcher coincidencia = PATRON_VERSION_OPENJ9.matcher(descripcion);

		if (!coincidencia.find()) {
			return null;
		}

		try {
			int mayor = Integer.parseInt(coincidencia.group(1));
			int menor = Integer.parseInt(coincidencia.group(2));
			int parche = coincidencia.group(3) == null ? 0 : Integer.parseInt(coincidencia.group(3));

			return new int[] { mayor, menor, parche };

		} catch (NumberFormatException excepcion) {
			return null;
		}
	}
}