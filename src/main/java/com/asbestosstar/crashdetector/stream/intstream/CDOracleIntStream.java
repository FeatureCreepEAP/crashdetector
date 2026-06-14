package com.asbestosstar.crashdetector.stream.intstream;

import java.util.stream.IntStream;

import com.oracle.stream.DaxIntStream;

/**
 * Fabrica separada para Oracle DAX IntStream.
 *
 * Esta clase solo debe cargarse cuando comOracleStream.jar o el stub compatible
 * ya esta en el classpath.
 */
public final class CDOracleIntStream {

	private CDOracleIntStream() {
	}

	public static IntStream de(int[] datos) {
		return DaxIntStream.of(datos);
	}
}