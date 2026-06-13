package com.asbestosstar.crashdetector.stream.intstream;

import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

/**
 * Motor normal basado en java.util.stream.IntStream.
 */
public final class CDJavaIntStream implements CDIntStream {

	private final java.util.stream.IntStream stream;

	private CDJavaIntStream(java.util.stream.IntStream stream) {
		this.stream = stream;
	}

	public static CDJavaIntStream de(int[] datos) {
		return new CDJavaIntStream(java.util.Arrays.stream(datos));
	}

	public static CDJavaIntStream envolver(java.util.stream.IntStream stream) {
		return new CDJavaIntStream(stream);
	}

	@Override
	public CDIntStream paralelo() {
		return new CDJavaIntStream(stream.parallel());
	}

	@Override
	public CDIntStream filtro(IntPredicate predicado) {
		return new CDJavaIntStream(stream.filter(predicado));
	}

	@Override
	public CDIntStream mapa(IntUnaryOperator operador) {
		return new CDJavaIntStream(stream.map(operador));
	}

	@Override
	public long contar() {
		return stream.count();
	}

	@Override
	public int[] aArray() {
		return stream.toArray();
	}

	@Override
	public boolean todosCoinciden(IntPredicate predicado) {
		return stream.allMatch(predicado);
	}

	@Override
	public boolean algunoCoincide(IntPredicate predicado) {
		return stream.anyMatch(predicado);
	}

	@Override
	public boolean ningunoCoincide(IntPredicate predicado) {
		return stream.noneMatch(predicado);
	}
}