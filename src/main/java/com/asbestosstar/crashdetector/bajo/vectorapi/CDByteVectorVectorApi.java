package com.asbestosstar.crashdetector.bajo.vectorapi;

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorSpecies;

/**
 * Implementacion directa de operaciones sobre byte[] mediante la Vector API.
 */
public final class CDByteVectorVectorApi implements CDByteVector {

	private static final VectorSpecies<Byte> ESPECIE = ByteVector.SPECIES_PREFERRED;
	private static final int LARGO = ESPECIE.length();

	@Override
	public int indexOf(byte[] datos, byte valor) {
		if (datos == null) {
			return -1;
		}

		int i = 0;
		int limite = ESPECIE.loopBound(datos.length);

		for (; i < limite; i += LARGO) {
			VectorMask<Byte> mascara = ByteVector.fromArray(ESPECIE, datos, i).eq(valor);

			if (LARGO <= Long.SIZE) {
				long bits = mascara.toLong();

				if (bits != 0) {
					return i + Long.numberOfTrailingZeros(bits);
				}
			} else if (mascara.anyTrue()) {
				int finBloque = i + LARGO;

				for (int j = i; j < finBloque; j++) {
					if (datos[j] == valor) {
						return j;
					}
				}
			}
		}

		for (; i < datos.length; i++) {
			if (datos[i] == valor) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public int indexOf(byte[] datos, byte[] patron) {
		/* Un patron arbitrario no se vectoriza de forma segura con este contrato. */
		return new CDByteVectorEscalar().indexOf(datos, patron);
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
		int i = 0;
		int limite = ESPECIE.loopBound(datos.length);

		for (; i < limite; i += LARGO) {
			VectorMask<Byte> mascara = ByteVector.fromArray(ESPECIE, datos, i).eq(valor);

			if (LARGO <= Long.SIZE) {
				total += Long.bitCount(mascara.toLong());
			} else if (mascara.anyTrue()) {
				int finBloque = i + LARGO;

				for (int j = i; j < finBloque; j++) {
					if (datos[j] == valor) {
						total++;
					}
				}
			}
		}

		for (; i < datos.length; i++) {
			if (datos[i] == valor) {
				total++;
			}
		}

		return total;
	}
}
