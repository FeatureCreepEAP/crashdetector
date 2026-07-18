package com.asbestosstar.crashdetector.bajo.vectorapi;

import java.util.Arrays;
import java.util.function.IntPredicate;

/**
 * Implementacion escalar que no depende de la Vector API.
 */
public final class CDIntVectorEscalar implements CDIntVector {

	@Override
	public int indexOf(int[] datos, int valor) {
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
	public int contarIgual(int[] datos, int valor) {
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
