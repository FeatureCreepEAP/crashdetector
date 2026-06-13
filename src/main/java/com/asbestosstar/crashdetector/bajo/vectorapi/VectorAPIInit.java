package com.asbestosstar.crashdetector.bajo.vectorapi;

public class VectorAPIInit {

	private static final String CLASE_VECTOR_API = "jdk.incubator.vector.IntVector";
	private static final String ARG_ADD_MODULE = "--add-modules=jdk.incubator.vector";

	/**
	 * Devuelve true si el JDK parece tener el modulo incubador Vector API.
	 *
	 * En Java 8 normalmente sera false.
	 */
	public static boolean vectorAPIDisponible() {
		try {
			Class.forName(CLASE_VECTOR_API, false, ClassLoader.getSystemClassLoader());
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	/**
	 * Si el codigo va a usar jdk.incubator.vector, normalmente necesita:
	 * --add-modules=jdk.incubator.vector
	 *
	 * No lo agregamos si la clase ya es visible.
	 */
	public static boolean necesitaArgEspecialVectorAPI() {
		return !vectorAPIDisponible() && javaMayorOIgual(16);
	}

	public static String obtenerArgEspecialVectorAPI() {
		return ARG_ADD_MODULE;
	}

	private static boolean javaMayorOIgual(int minimo) {
		String version = System.getProperty("java.specification.version", "");

		try {
			int mayor;

			if (version.startsWith("1.")) {
				mayor = Integer.parseInt(version.substring(2));
			} else {
				int punto = version.indexOf('.');
				mayor = Integer.parseInt(punto >= 0 ? version.substring(0, punto) : version);
			}

			return mayor >= minimo;
		} catch (Throwable t) {
			return false;
		}
	}
}