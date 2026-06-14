package com.asbestosstar.crashdetector.bajo.vectorapi;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.IntPredicate;

public final class CDIntVectorJdkMH implements CDIntVector {

	private static final Motor MOTOR = Motor.crear();

	@Override
	public int indexOf(int[] datos, int valor) {
		if (datos == null) {
			return -1;
		}

		if (MOTOR.disponible) {
			return MOTOR.indexOf(datos, valor);
		}

		return indexOfEscalar(datos, valor);
	}

	@Override
	public int contarIgual(int[] datos, int valor) {
		if (datos == null) {
			return 0;
		}

		if (MOTOR.disponible) {
			return MOTOR.contarIgual(datos, valor);
		}

		return contarIgualEscalar(datos, valor);
	}

	@Override
	public int contar(int[] datos, IntPredicate predicado) {
		// Posible mejora futura:
		// crear CDIntVectorJava como motor escalar separado.
		// crear CDIntVectorVectorAPI con import directo de jdk.incubator.vector
		// para predicados soportados simples.
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

		int[] tmp = new int[datos.length];
		int total = 0;

		for (int i = 0; i < datos.length; i++) {
			if (predicado.test(datos[i])) {
				tmp[total++] = datos[i];
			}
		}

		return Arrays.copyOf(tmp, total);
	}

	private static int indexOfEscalar(int[] datos, int valor) {
		for (int i = 0; i < datos.length; i++) {
			if (datos[i] == valor) {
				return i;
			}
		}

		return -1;
	}

	private static int contarIgualEscalar(int[] datos, int valor) {
		int total = 0;

		for (int i = 0; i < datos.length; i++) {
			if (datos[i] == valor) {
				total++;
			}
		}

		return total;
	}

	private static final class Motor {
		boolean disponible;
		Object species;
		Object eq;
		int largo;
		MethodHandle fromArray;
		MethodHandle compareInt;
		MethodHandle anyTrue;

		static Motor crear() {
			Motor m = new Motor();

			try {
				ClassLoader cl = ClassLoader.getSystemClassLoader();
				Class<?> intVector = Class.forName("jdk.incubator.vector.IntVector", false, cl);
				Class<?> vectorMask = Class.forName("jdk.incubator.vector.VectorMask", false, cl);
				Class<?> vectorOperators = Class.forName("jdk.incubator.vector.VectorOperators", false, cl);

				m.species = intVector.getField("SPECIES_PREFERRED").get(null);
				m.largo = ((Number) m.species.getClass().getMethod("length").invoke(m.species)).intValue();
				m.eq = vectorOperators.getField("EQ").get(null);

				MethodHandles.Lookup lookup = MethodHandles.lookup();

				m.fromArray = lookup.unreflect(buscarMetodo(intVector, "fromArray", 3));
				m.compareInt = lookup.unreflect(buscarMetodo(intVector, "compare", 2));
				m.anyTrue = lookup.unreflect(vectorMask.getMethod("anyTrue"));

				m.disponible = true;
			} catch (Throwable t) {
				m.disponible = false;
			}

			return m;
		}

		int indexOf(int[] datos, int valor) {
			int i = 0;
			int limite = datos.length - largo;

			try {
				for (; i <= limite; i += largo) {
					Object vector = fromArray.invokeWithArguments(species, datos, i);
					Object mask = compareInt.invokeWithArguments(vector, eq, valor);

					if (((Boolean) anyTrue.invokeWithArguments(mask)).booleanValue()) {
						for (int j = i; j < i + largo; j++) {
							if (datos[j] == valor) {
								return j;
							}
						}
					}
				}
			} catch (Throwable t) {
				// Si una version del Vector API no coincide, continuar escalar.
			}

			for (; i < datos.length; i++) {
				if (datos[i] == valor) {
					return i;
				}
			}

			return -1;
		}

		int contarIgual(int[] datos, int valor) {
			int total = 0;
			int i = 0;
			int limite = datos.length - largo;

			try {
				for (; i <= limite; i += largo) {
					Object vector = fromArray.invokeWithArguments(species, datos, i);
					Object mask = compareInt.invokeWithArguments(vector, eq, valor);

					if (((Boolean) anyTrue.invokeWithArguments(mask)).booleanValue()) {
						for (int j = i; j < i + largo; j++) {
							if (datos[j] == valor) {
								total++;
							}
						}
					}
				}
			} catch (Throwable t) {
				// Si una version del Vector API no coincide, continuar escalar.
			}

			for (; i < datos.length; i++) {
				if (datos[i] == valor) {
					total++;
				}
			}

			return total;
		}

		private static Method buscarMetodo(Class<?> clase, String nombre, int parametros) throws NoSuchMethodException {
			Method[] metodos = clase.getMethods();

			for (int i = 0; i < metodos.length; i++) {
				Method m = metodos[i];

				if (m.getName().equals(nombre) && m.getParameterTypes().length == parametros) {
					return m;
				}
			}

			throw new NoSuchMethodException(clase.getName() + "." + nombre);
		}
	}
}