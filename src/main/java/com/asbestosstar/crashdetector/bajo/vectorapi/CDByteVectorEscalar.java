package com.asbestosstar.crashdetector.bajo.vectorapi;

/**
 * Implementacion escalar que no depende de la Vector API.
 */
public final class CDByteVectorEscalar implements CDByteVector {

	@Override
	public int indexOf(byte[] datos, byte valor) {
		if (datos == null) {
			return -1;
		}

		for (int i = 0; i < datos.length; i++) {
			if (datos[i] == valor) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public int indexOf(byte[] datos, byte[] patron) {
		if (datos == null || patron == null || patron.length == 0 || patron.length > datos.length) {
			return -1;
		}

		int limite = datos.length - patron.length;

		for (int i = 0; i <= limite; i++) {
			int j = 0;

			while (j < patron.length && datos[i + j] == patron[j]) {
				j++;
			}

			if (j == patron.length) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public boolean contiene(byte[] datos, byte valor) {
		return indexOf(datos, valor) >= 0;
	}

	@Override
	public boolean contiene(byte[] datos, byte[] patron) {
		return indexOf(datos, patron) >= 0;
	}

	@Override
	public int contar(byte[] datos, byte valor) {
		if (datos == null) {
			return 0;
		}

		int total = 0;

		for (int i = 0; i < datos.length; i++) {
			if (datos[i] == valor) {
				total++;
			}
		}

		return total;
	}
}
