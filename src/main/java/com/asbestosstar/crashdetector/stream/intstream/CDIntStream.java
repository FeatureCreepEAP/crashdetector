package com.asbestosstar.crashdetector.stream.intstream;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.IntStream;

/**
 * Fabrica para crear IntStream.
 *
 * Usa Oracle DAX solamente cuando se ejecuta sobre Solaris SPARC V9 y la
 * dependencia Oracle esta disponible.
 */
public final class CDIntStream {

	private static final String CLASE_DAX_INT_STREAM = "com.oracle.stream.DaxIntStream";

	private static final String BIBLIOTECA_DAX_NUEVA = "/usr/lib/streamoffload/libstreamoffload.so";

	private static final String BIBLIOTECA_DAX_ANTIGUA = "/usr/lib/sparcv9/libstreamoffload.so";

	private CDIntStream() {
	}

	public static IntStream de(int[] datos) {
		if (oracleDisponible()) {
			try {
				return CDOracleIntStream.de(datos);
			} catch (LinkageError e) {
				/*
				 * La clase existe, pero la biblioteca nativa o alguna dependencia no pudo
				 * enlazarse.
				 */
			} catch (RuntimeException e) {
				/* DAX no pudo inicializar el flujo. */
			}
		}

		return deJava(datos);
	}

	public static IntStream deJava(int[] datos) {
		return Arrays.stream(datos);
	}

	/**
	 * Indica si el sistema operativo y la arquitectura pueden usar la biblioteca
	 * Oracle DAX.
	 */
	public static boolean esSolarisSparcV9() {
		String arquitectura = System.getProperty("os.arch", "").toLowerCase(Locale.ROOT);

		String sistema = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);

		if (!sistema.contains("sunos") && !sistema.contains("solaris")) {
			return false;
		}

		return arquitectura.equals("sparcv9") || arquitectura.equals("sparc64") || arquitectura.equals("sparc")
				|| arquitectura.contains("sparcv9") || arquitectura.contains("sparc64");
	}

	/**
	 * Indica si Oracle DAX esta disponible para usarse.
	 *
	 * La comprobacion de arquitectura se realiza antes de intentar cargar cualquier
	 * clase Oracle.
	 */
	public static boolean oracleDisponible() {
		if (!esSolarisSparcV9()) {
			return false;
		}

		if (!bibliotecaNativaDisponible()) {
			return false;
		}

		try {
			Class.forName(CLASE_DAX_INT_STREAM, false, CDIntStream.class.getClassLoader());

			return true;
		} catch (ClassNotFoundException e) {
			return false;
		} catch (LinkageError e) {
			return false;
		} catch (SecurityException e) {
			return false;
		}
	}

	private static boolean bibliotecaNativaDisponible() {
		return esArchivo(BIBLIOTECA_DAX_NUEVA) || esArchivo(BIBLIOTECA_DAX_ANTIGUA);
	}

	private static boolean esArchivo(String ruta) {
		try {
			return new File(ruta).isFile();
		} catch (SecurityException e) {
			return false;
		}
	}
}