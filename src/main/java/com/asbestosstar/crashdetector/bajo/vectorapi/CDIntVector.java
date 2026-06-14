package com.asbestosstar.crashdetector.bajo.vectorapi;

import java.util.function.IntPredicate;

public interface CDIntVector {

	int indexOf(int[] datos, int valor);

	int contarIgual(int[] datos, int valor);

	int contar(int[] datos, IntPredicate predicado);

	boolean algunoCoincide(int[] datos, IntPredicate predicado);

	boolean todosCoinciden(int[] datos, IntPredicate predicado);

	int[] filtrar(int[] datos, IntPredicate predicado);
}