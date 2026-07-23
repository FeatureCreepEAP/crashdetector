package com.asbestosstar.crashdetector.controljvm;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Conserva la Instrumentation de la JVM observada sin introducir dependencias
 * hacia cargadores concretos.
 */
public final class InstrumentacionCrashDetector {

	private static volatile Instrumentation instrumentacion;

	private InstrumentacionCrashDetector() {
	}

	public static void establecer(Instrumentation valor) {
		if (valor != null) {
			instrumentacion = valor;
		}
	}

	public static Instrumentation obtener() {
		Instrumentation actual = instrumentacion;
		if (actual != null) {
			return actual;
		}

		actual = buscarEnClasesConocidas();
		if (actual != null) {
			instrumentacion = actual;
		}
		return actual;
	}

	private static Instrumentation buscarEnClasesConocidas() {
		Instrumentation resultado = llamarGetter("asbestosstar.bootstrap.FeatureCreepAgent", "getInstrumentation");
		if (resultado != null) {
			return resultado;
		}

		resultado = leerCampo("asbestosstar.bootstrap.BootstrapCommon", "instrument");
		if (resultado != null) {
			return resultado;
		}

		String[] campos = { "instrumentation", "instrumentacion", "instrument", "inst" };
		for (String campo : campos) {
			resultado = leerCampo("com.asbestosstar.crashdetector.CrashDetectorFCMC", campo);
			if (resultado != null) {
				return resultado;
			}
		}

		return null;
	}

	private static Instrumentation llamarGetter(String nombreClase, String nombreMetodo) {
		for (ClassLoader cargador : cargadoresCandidatos()) {
			try {
				Class<?> clase = Class.forName(nombreClase, false, cargador);
				Method metodo = clase.getMethod(nombreMetodo);
				Object valor = metodo.invoke(null);
				if (valor instanceof Instrumentation) {
					return (Instrumentation) valor;
				}
			} catch (Throwable ignorado) {
			}
		}
		return null;
	}

	private static Instrumentation leerCampo(String nombreClase, String nombreCampo) {
		for (ClassLoader cargador : cargadoresCandidatos()) {
			try {
				Class<?> clase = Class.forName(nombreClase, false, cargador);
				Field campo = clase.getDeclaredField(nombreCampo);
				campo.setAccessible(true);
				Object valor = campo.get(null);
				if (valor instanceof Instrumentation) {
					return (Instrumentation) valor;
				}
			} catch (Throwable ignorado) {
			}
		}
		return null;
	}

	private static ClassLoader[] cargadoresCandidatos() {
		return new ClassLoader[] { Thread.currentThread().getContextClassLoader(),
				InstrumentacionCrashDetector.class.getClassLoader(), ClassLoader.getSystemClassLoader() };
	}
}
