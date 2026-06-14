package com.asbestosstar.crashdetector.bajo.vectorapi;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class CDByteVectorJdkMH implements CDByteVector {

	private static final Motor MOTOR = Motor.crear();

	@Override
	public int indexOf(byte[] datos, byte valor) {
		if (datos == null) {
			return -1;
		}

		if (MOTOR.disponible) {
			return MOTOR.indexOf(datos, valor);
		}

		return indexOfEscalar(datos, valor);
	}

	@Override
	public int indexOf(byte[] datos, byte[] patron) {
		// Posible mejora futura:
		// crear CDByteVectorJava como motor escalar separado.
		// crear CDByteVectorVectorAPI como motor real con import directo
		// de jdk.incubator.vector cuando exista una clase Java separada.
		return indexOfEscalar(datos, patron);
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

		if (MOTOR.disponible) {
			return MOTOR.contar(datos, valor);
		}

		return contarEscalar(datos, valor);
	}

	private static int indexOfEscalar(byte[] datos, byte valor) {
		for (int i = 0; i < datos.length; i++) {
			if (datos[i] == valor) {
				return i;
			}
		}

		return -1;
	}

	private static int indexOfEscalar(byte[] datos, byte[] patron) {
		if (datos == null || patron == null || patron.length == 0 || patron.length > datos.length) {
			return -1;
		}

		int max = datos.length - patron.length;

		for (int i = 0; i <= max; i++) {
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

	private static int contarEscalar(byte[] datos, byte valor) {
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
		MethodHandle compareByte;
		MethodHandle anyTrue;

		static Motor crear() {
			Motor m = new Motor();

			try {
				ClassLoader cl = ClassLoader.getSystemClassLoader();
				Class<?> byteVector = Class.forName("jdk.incubator.vector.ByteVector", false, cl);
				Class<?> vectorMask = Class.forName("jdk.incubator.vector.VectorMask", false, cl);
				Class<?> vectorOperators = Class.forName("jdk.incubator.vector.VectorOperators", false, cl);

				Field speciesField = byteVector.getField("SPECIES_PREFERRED");
				m.species = speciesField.get(null);

				Method length = m.species.getClass().getMethod("length");
				m.largo = ((Number) length.invoke(m.species)).intValue();

				Field eqField = vectorOperators.getField("EQ");
				m.eq = eqField.get(null);

				MethodHandles.Lookup lookup = MethodHandles.lookup();

				m.fromArray = lookup.unreflect(buscarMetodo(byteVector, "fromArray", 3));
				m.compareByte = lookup.unreflect(buscarMetodo(byteVector, "compare", 2));
				m.anyTrue = lookup.unreflect(vectorMask.getMethod("anyTrue"));

				m.disponible = true;
			} catch (Throwable t) {
				m.disponible = false;
			}

			return m;
		}

		int indexOf(byte[] datos, byte valor) {
			int i = 0;
			int limite = datos.length - largo;

			try {
				for (; i <= limite; i += largo) {
					Object vector = fromArray.invokeWithArguments(species, datos, i);
					Object mask = compareByte.invokeWithArguments(vector, eq, valor);

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

		int contar(byte[] datos, byte valor) {
			int total = 0;
			int i = 0;
			int limite = datos.length - largo;

			try {
				for (; i <= limite; i += largo) {
					Object vector = fromArray.invokeWithArguments(species, datos, i);
					Object mask = compareByte.invokeWithArguments(vector, eq, valor);

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