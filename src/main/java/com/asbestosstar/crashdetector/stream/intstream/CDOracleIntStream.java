package com.asbestosstar.crashdetector.stream.intstream;

import java.util.stream.IntStream;

import com.oracle.stream.DaxIntStream;

/**
 * Puente separado hacia Oracle DAX Stream Offload.
 *
 * <p>
 * Esta clase solamente debe cargarse cuando el JAR y la biblioteca nativa de
 * Oracle estén disponibles.
 * </p>
 */
public final class CDOracleIntStream {

	private CDOracleIntStream() {
	}

	public static IntStream de(int[] datos) {
		return DaxIntStream.of(datos);
	}

	/**
	 * Oracle solamente considera para DAX los flujos marcados como paralelos.
	 */
	public static IntStream deParalelo(int[] datos) {
		return DaxIntStream.of(datos).parallel();
	}
}
