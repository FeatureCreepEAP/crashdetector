package com.asbestosstar.crashdetector.bajo.hw.gpu;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Utilidades pequeñas de reflexión usadas por los tres backends gráficos.
 *
 * La reflexión es intencional: permite que el mismo código fuente se compile
 * sin enlazarse directamente contra LWJGL 2, GLFW o SDL3. Solamente se carga el
 * backend que realmente existe en el classpath del juego.
 */
final class ReflexionGPU {

	private ReflexionGPU() {
		// Clase de utilidades; no se crean instancias.
	}

	static boolean existeClase(String nombreClase) {
		try {
			Class.forName(nombreClase, false, cargador());
			return true;
		} catch (Throwable ignorado) {
			return false;
		}
	}

	static Class<?> cargarClase(String nombreClase) throws ClassNotFoundException {
		return Class.forName(nombreClase, true, cargador());
	}

	static Object invocarEstatico(String nombreClase, String nombreMetodo, Object... argumentos) throws Throwable {
		Class<?> clase = cargarClase(nombreClase);
		Method metodo = buscarMetodo(clase, nombreMetodo, true, argumentos);
		return invocarMetodo(metodo, null, argumentos);
	}

	static Object invocarInstancia(Object instancia, String nombreMetodo, Object... argumentos) throws Throwable {
		if (instancia == null) {
			throw new IllegalArgumentException("La instancia no puede ser null.");
		}

		Method metodo = buscarMetodo(instancia.getClass(), nombreMetodo, false, argumentos);
		return invocarMetodo(metodo, instancia, argumentos);
	}

	static Object construir(String nombreClase, Object... argumentos) throws Throwable {
		Class<?> clase = cargarClase(nombreClase);

		for (Constructor<?> constructor : clase.getConstructors()) {
			Class<?>[] tipos = constructor.getParameterTypes();
			if (sonCompatibles(tipos, argumentos)) {
				try {
					return constructor.newInstance(argumentos);
				} catch (InvocationTargetException e) {
					throw causaReal(e);
				}
			}
		}

		throw new NoSuchMethodException("No se encontró un constructor compatible en " + nombreClase + " con "
				+ argumentos.length + " argumentos.");
	}

	static int campoInt(String nombreClase, String nombreCampo) throws Throwable {
		Object valor = campoEstatico(nombreClase, nombreCampo);
		if (!(valor instanceof Number)) {
			throw new IllegalStateException(nombreClase + "." + nombreCampo + " no es numérico.");
		}
		return ((Number) valor).intValue();
	}

	static long campoLong(String nombreClase, String nombreCampo) throws Throwable {
		Object valor = campoEstatico(nombreClase, nombreCampo);
		if (!(valor instanceof Number)) {
			throw new IllegalStateException(nombreClase + "." + nombreCampo + " no es numérico.");
		}
		return ((Number) valor).longValue();
	}

	static Object campoEstatico(String nombreClase, String nombreCampo) throws Throwable {
		Class<?> clase = cargarClase(nombreClase);
		Field campo = clase.getField(nombreCampo);
		return campo.get(null);
	}

	static boolean resultadoExitoso(Object resultado) {
		if (resultado == null) {
			// Los métodos void se consideran exitosos si no lanzaron excepción.
			return true;
		}
		if (resultado instanceof Boolean) {
			return ((Boolean) resultado).booleanValue();
		}
		if (resultado instanceof Number) {
			return ((Number) resultado).longValue() != 0L;
		}
		return true;
	}

	static String mensajeSeguro(Throwable error) {
		if (error == null) {
			return "error desconocido";
		}

		Throwable causa = causaReal(error);
		String mensaje = causa.getMessage();
		return mensaje != null && !mensaje.trim().isEmpty() ? mensaje : causa.getClass().getName();
	}

	static Throwable causaReal(Throwable error) {
		Throwable actual = error;
		while ((actual instanceof InvocationTargetException || actual instanceof ExceptionInInitializerError)
				&& actual.getCause() != null) {
			actual = actual.getCause();
		}
		return actual;
	}

	private static Method buscarMetodo(Class<?> clase, String nombreMetodo, boolean debeSerEstatico,
			Object[] argumentos) throws NoSuchMethodException {

		for (Method metodo : clase.getMethods()) {
			if (!metodo.getName().equals(nombreMetodo)) {
				continue;
			}

			boolean esEstatico = Modifier.isStatic(metodo.getModifiers());
			if (esEstatico != debeSerEstatico) {
				continue;
			}

			if (sonCompatibles(metodo.getParameterTypes(), argumentos)) {
				return metodo;
			}
		}

		throw new NoSuchMethodException("No se encontró " + clase.getName() + "." + nombreMetodo + " con "
				+ argumentos.length + " argumentos compatibles.");
	}

	private static Object invocarMetodo(Method metodo, Object instancia, Object[] argumentos) throws Throwable {
		try {
			return metodo.invoke(instancia, argumentos);
		} catch (InvocationTargetException e) {
			throw causaReal(e);
		}
	}

	private static boolean sonCompatibles(Class<?>[] tipos, Object[] argumentos) {
		if (tipos.length != argumentos.length) {
			return false;
		}

		for (int i = 0; i < tipos.length; i++) {
			if (!esCompatible(tipos[i], argumentos[i])) {
				return false;
			}
		}
		return true;
	}

	private static boolean esCompatible(Class<?> tipo, Object argumento) {
		if (argumento == null) {
			return !tipo.isPrimitive();
		}

		Class<?> claseArgumento = argumento.getClass();
		if (tipo.isAssignableFrom(claseArgumento)) {
			return true;
		}

		if (!tipo.isPrimitive()) {
			return false;
		}

		if (tipo == boolean.class) {
			return claseArgumento == Boolean.class;
		}
		if (tipo == char.class) {
			return claseArgumento == Character.class;
		}
		if (tipo == byte.class) {
			return claseArgumento == Byte.class;
		}
		if (tipo == short.class) {
			return claseArgumento == Short.class || claseArgumento == Byte.class;
		}
		if (tipo == int.class) {
			return claseArgumento == Integer.class || claseArgumento == Short.class || claseArgumento == Byte.class;
		}
		if (tipo == long.class) {
			return claseArgumento == Long.class || claseArgumento == Integer.class || claseArgumento == Short.class
					|| claseArgumento == Byte.class;
		}
		if (tipo == float.class) {
			return claseArgumento == Float.class || claseArgumento == Integer.class || claseArgumento == Short.class
					|| claseArgumento == Byte.class;
		}
		if (tipo == double.class) {
			return Number.class.isAssignableFrom(claseArgumento);
		}

		return false;
	}

	private static ClassLoader cargador() {
		ClassLoader contexto = Thread.currentThread().getContextClassLoader();
		return contexto != null ? contexto : ReflexionGPU.class.getClassLoader();
	}
}
