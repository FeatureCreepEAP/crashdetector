package com.asbestosstar.crashdetector.stream.intstream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

/**
 * Motor Oracle DAX Streams por reflection.
 *
 * No importa com.oracle.* directamente porque el JAR normalmente solo existe en
 * SPARC Solaris con /usr/lib/sparcv9/comOracleStream.jar.
 */
public final class CDOracleIntStream implements CDIntStream {

	private static final String CLASE_ARRAYS_ORACLE = "com.oracle.Arrays";
	private static final String CLASE_INT_STREAM_ORACLE = "com.oracle.stream.IntStream";

	private final Object stream;

	private CDOracleIntStream(Object stream) {
		this.stream = stream;
	}

	public static boolean oracleDisponible() {
		try {
			Class.forName(CLASE_ARRAYS_ORACLE);
			Class.forName(CLASE_INT_STREAM_ORACLE);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public static CDIntStream intentarCrear(int[] datos) {
		try {
			Class<?> arraysOracle = Class.forName(CLASE_ARRAYS_ORACLE);
			Method streamMethod = arraysOracle.getMethod("stream", int[].class);
			Object streamOracle = streamMethod.invoke(null, datos);

			if (streamOracle == null) {
				return null;
			}

			return new CDOracleIntStream(streamOracle);
		} catch (Throwable t) {
			return null;
		}
	}

	public static CDOracleIntStream envolver(Object streamOracle) {
		if (streamOracle == null) {
			throw new IllegalArgumentException("streamOracle no puede ser null");
		}

		return new CDOracleIntStream(streamOracle);
	}

	@Override
	public CDIntStream paralelo() {
		return invocarStream("parallel");
	}

	@Override
	public CDIntStream filtro(IntPredicate predicado) {
		return invocarStream("filter", IntPredicate.class, predicado);
	}

	@Override
	public CDIntStream mapa(IntUnaryOperator operador) {
		return invocarStream("map", IntUnaryOperator.class, operador);
	}

	@Override
	public long contar() {
		Object r = invocarTerminal("count");

		if (r instanceof Number) {
			return ((Number) r).longValue();
		}

		throw new IllegalStateException("Oracle IntStream.count() no devolvio numero: " + r);
	}

	@Override
	public int[] aArray() {
		Object r = invocarTerminal("toArray");

		if (r instanceof int[]) {
			return (int[]) r;
		}

		throw new IllegalStateException("Oracle IntStream.toArray() no devolvio int[]: " + r);
	}

	@Override
	public boolean todosCoinciden(IntPredicate predicado) {
		return invocarBoolean("allMatch", IntPredicate.class, predicado);
	}

	@Override
	public boolean algunoCoincide(IntPredicate predicado) {
		return invocarBoolean("anyMatch", IntPredicate.class, predicado);
	}

	@Override
	public boolean ningunoCoincide(IntPredicate predicado) {
		return invocarBoolean("noneMatch", IntPredicate.class, predicado);
	}

	private CDIntStream invocarStream(String metodo) {
		Object r = invocarTerminal(metodo);

		if (r == null) {
			throw new IllegalStateException("Oracle IntStream." + metodo + "() devolvio null");
		}

		return new CDOracleIntStream(r);
	}

	private CDIntStream invocarStream(String metodo, Class<?> tipoArg, Object arg) {
		Object r = invocarTerminal(metodo, tipoArg, arg);

		if (r == null) {
			throw new IllegalStateException("Oracle IntStream." + metodo + "(...) devolvio null");
		}

		return new CDOracleIntStream(r);
	}

	private boolean invocarBoolean(String metodo, Class<?> tipoArg, Object arg) {
		Object r = invocarTerminal(metodo, tipoArg, arg);

		if (r instanceof Boolean) {
			return ((Boolean) r).booleanValue();
		}

		throw new IllegalStateException("Oracle IntStream." + metodo + "(...) no devolvio boolean: " + r);
	}

	private Object invocarTerminal(String metodo) {
		try {
			Method m = stream.getClass().getMethod(metodo);
			return m.invoke(stream);
		} catch (InvocationTargetException e) {
			throw envolver(e.getCause());
		} catch (Throwable t) {
			throw envolver(t);
		}
	}

	private Object invocarTerminal(String metodo, Class<?> tipoArg, Object arg) {
		try {
			Method m = stream.getClass().getMethod(metodo, tipoArg);
			return m.invoke(stream, arg);
		} catch (InvocationTargetException e) {
			throw envolver(e.getCause());
		} catch (Throwable t) {
			throw envolver(t);
		}
	}

	private RuntimeException envolver(Throwable t) {
		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		}

		return new RuntimeException("Error invocando Oracle DAX IntStream por reflection", t);
	}
}