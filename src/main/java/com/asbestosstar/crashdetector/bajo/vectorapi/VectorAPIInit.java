package com.asbestosstar.crashdetector.bajo.vectorapi;

/**
 * Detecta si la Vector API real está disponible.
 *
 * Si encuentra StubChecker, las clases de jdk.incubator.vector proceden del JAR
 * de stubs y no deben usarse en tiempo de ejecución.
 */
public final class VectorAPIInit {

	private static final String CLASE_VECTOR_API = "jdk.incubator.vector.ByteVector";

	private static final String CLASE_STUB = "jdk.incubator.vector.StubChecker";

	private static final String ARG_ADD_MODULE = "--add-modules=jdk.incubator.vector";

	private VectorAPIInit() {
	}

	/**
	 * Devuelve true solamente cuando está disponible la Vector API real.
	 *
	 * Devuelve false cuando: - está presente únicamente el JAR de stubs; - el
	 * módulo Vector API no fue añadido; - la versión de la API es incompatible; -
	 * ocurre un error de enlace.
	 */
	public static boolean vectorAPIDisponible() {
		ClassLoader cargador = VectorAPIInit.class.getClassLoader();

		/*
		 * Comprobar primero el marcador de los stubs.
		 *
		 * Aunque ByteVector también exista, la presencia de StubChecker significa que
		 * no debemos usar esas clases.
		 */
		if (claseDisponible(CLASE_STUB, cargador)) {
			return false;
		}

		return claseDisponible(CLASE_VECTOR_API, cargador);
	}

	/**
	 * Indica si el JAR de stubs está visible en tiempo de ejecución.
	 */
	public static boolean stubsPresentes() {
		return claseDisponible(CLASE_STUB, VectorAPIInit.class.getClassLoader());
	}

	/**
	 * Indica si probablemente hace falta añadir el módulo incubador.
	 */
	public static boolean necesitaArgEspecialVectorAPI() {
		return !stubsPresentes() && !vectorAPIDisponible() && javaMayorOIgual(16);
	}

	public static String obtenerArgEspecialVectorAPI() {
		return ARG_ADD_MODULE;
	}

	private static boolean claseDisponible(String nombre, ClassLoader cargador) {

		try {
			Class.forName(nombre, false, cargador);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		} catch (LinkageError e) {
			return false;
		} catch (SecurityException e) {
			return false;
		}
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
		} catch (RuntimeException e) {
			return false;
		}
	}
}