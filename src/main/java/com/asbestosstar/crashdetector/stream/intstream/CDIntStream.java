package com.asbestosstar.crashdetector.stream.intstream;

import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

/**
 * Interfaz base para streams de int.
 *
 * Puede usar Oracle DAX Streams si el JAR existe, o Java IntStream normal si
 * Oracle no esta disponible.
 */
public interface CDIntStream {

	CDIntStream paralelo();

	CDIntStream filtro(IntPredicate predicado);

	CDIntStream mapa(IntUnaryOperator operador);

	long contar();

	int[] aArray();

	boolean todosCoinciden(IntPredicate predicado);

	boolean algunoCoincide(IntPredicate predicado);

	boolean ningunoCoincide(IntPredicate predicado);

	public static CDIntStream de(int[] datos) {
		CDIntStream oracle = CDOracleIntStream.intentarCrear(datos);

		if (oracle != null) {
			return oracle;
		}

		return CDJavaIntStream.de(datos);
	}

	public static CDIntStream deJava(int[] datos) {
		return CDJavaIntStream.de(datos);
	}

	public static boolean oracleDisponible() {
		return CDOracleIntStream.oracleDisponible();
	}
}