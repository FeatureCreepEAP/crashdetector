package com.asbestosstar.crashdetector.stream.intstream;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Fabrica para crear IntStream.
 *
 * Si com.oracle.stream.DaxIntStream existe en el classpath, usa el motor
 * Oracle. Si no existe, usa Java IntStream normal.
 */
public final class CDIntStream {

	private static final String CLASE_DAX_INT_STREAM = "com.oracle.stream.DaxIntStream";

	private CDIntStream() {
	}

	public static IntStream de(int[] datos) {
		if (oracleDisponible()) {
			return CDOracleIntStream.de(datos);
		}

		return deJava(datos);
	}

	public static IntStream deJava(int[] datos) {
		return Arrays.stream(datos);
	}

	public static boolean oracleDisponible() {
		if (!esSparcV9()) {
			return false;
		}

		try {
			Class.forName(CLASE_DAX_INT_STREAM, false, CDIntStream.class.getClassLoader());
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	private static boolean esSparcV9() {
		String arch = System.getProperty("os.arch", "").toLowerCase();
		String os = System.getProperty("os.name", "").toLowerCase();

		return os.contains("sunos") && (arch.contains("sparcv9") || arch.contains("sparc64") || arch.equals("sparc"));
	}

}