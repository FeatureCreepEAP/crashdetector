package com.asbestosstar.crashdetector.bajo.vectorapi;

/**
 * Fabrica sin reflexion para los adaptadores de la Vector API.
 */
public final class CDVectores {

	private CDVectores() {
	}

	public static CDByteVector bytes() {
		if (VectorAPIInit.vectorAPIDisponible()) {
			try {
				return new CDByteVectorVectorApi();
			} catch (LinkageError e) {
				/* Continuar con el motor escalar. */
			}
		}

		return new CDByteVectorEscalar();
	}

	public static CDIntVector enteros() {
		if (VectorAPIInit.vectorAPIDisponible()) {
			try {
				return new CDIntVectorVectorApi();
			} catch (LinkageError e) {
				/* Continuar con el motor escalar. */
			}
		}

		return new CDIntVectorEscalar();
	}
}
