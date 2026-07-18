package com.asbestosstar.crashdetector.bajo.vectorapi;

import java.util.Arrays;
import java.util.function.IntPredicate;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorSpecies;

/**
 * Implementacion directa de igualdad sobre int[] mediante la Vector API.
 *
 * Los IntPredicate arbitrarios permanecen escalares porque no se pueden
 * convertir de forma general en operadores SIMD.
 */
public final class CDIntVectorVectorApi implements CDIntVector {

	private static final VectorSpecies<Integer> ESPECIE = IntVector.SPECIES_PREFERRED;
	private static final int LARGO = ESPECIE.length();

	@Override
	public int indexOf(int[] datos, int valor) {
		if (datos == null) {
			return -1;
		}

		int i = 0;
		int limite = ESPECIE.loopBound(datos.length);

		for (; i < limite; i += LARGO) {
			VectorMask<Integer> mascara = IntVector.fromArray(ESPECIE, datos, i).eq(valor);
			long bits = mascara.toLong();

			if (bits != 0) {
				return i + Long.numberOfTrailingZeros(bits);
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
	public int contarIgual(int[] datos, int valor) {
		if (datos == null) {
			return 0;
		}

		int total = 0;
		int i = 0;
		int limite = ESPECIE.loopBound(datos.length);

		for (; i < limite; i += LARGO) {
			VectorMask<Integer> mascara = IntVector.fromArray(ESPECIE, datos, i).eq(valor);
			total += Long.bitCount(mascara.toLong());
		}

		for (; i < datos.length; i++) {
			if (datos[i] == valor) {
				total++;
			}
		}

		return total;
	}

	@Override
	public int contar(int[] datos, IntPredicate predicado) {
		if (datos == null || predicado == null) {
			return 0;
		}

		int total = 0;

		for (int i = 0; i < datos.length; i++) {
			if (predicado.test(datos[i])) {
				total++;
			}
		}

		return total;
	}

	@Override
	public boolean algunoCoincide(int[] datos, IntPredicate predicado) {
		if (datos == null || predicado == null) {
			return false;
		}

		for (int i = 0; i < datos.length; i++) {
			if (predicado.test(datos[i])) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean todosCoinciden(int[] datos, IntPredicate predicado) {
		if (datos == null || predicado == null) {
			return false;
		}

		for (int i = 0; i < datos.length; i++) {
			if (!predicado.test(datos[i])) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int[] filtrar(int[] datos, IntPredicate predicado) {
		if (datos == null || predicado == null) {
			return new int[0];
		}

		int[] temporal = new int[datos.length];
		int total = 0;

		for (int i = 0; i < datos.length; i++) {
			if (predicado.test(datos[i])) {
				temporal[total++] = datos[i];
			}
		}

		return Arrays.copyOf(temporal, total);
	}
}
