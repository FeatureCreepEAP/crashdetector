package com.asbestosstar.crashdetector.gui.tipos.jgit.avanzado;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.gui.tipos.jgit.BuscarParaJGit;

/**
 * Puente que mantiene las clases JGit fuera del classloader cuando las
 * dependencias todavía no están disponibles.
 */
public final class JGitAvanzadoReflexivo {

	public static final String CLASE_DIRECTA = "com.asbestosstar.crashdetector.gui.tipos.jgit.avanzado.OperacionesJGitAvanzadasDirectas";

	private JGitAvanzadoReflexivo() {
	}

	public static boolean disponible() {
		return BuscarParaJGit.estaJGitBasicoEnClasspath();
	}

	public static EstadoGitAvanzado obtenerEstado(File repo, int maximoHistorial) {
		Object valor = llamar("obtenerEstado", new Class[] { File.class, int.class },
				new Object[] { repo, Integer.valueOf(maximoHistorial) });
		return valor instanceof EstadoGitAvanzado ? (EstadoGitAvanzado) valor : null;
	}

	public static boolean preparar(File repo, List<String> rutas) {
		return booleano(llamar("preparar", new Class[] { File.class, List.class }, new Object[] { repo, rutas }));
	}

	public static boolean prepararTodo(File repo) {
		return booleano(llamar("prepararTodo", new Class[] { File.class }, new Object[] { repo }));
	}

	public static boolean quitarPreparacion(File repo, List<String> rutas) {
		return booleano(
				llamar("quitarPreparacion", new Class[] { File.class, List.class }, new Object[] { repo, rutas }));
	}

	public static boolean quitarTodaPreparacion(File repo) {
		return booleano(llamar("quitarTodaPreparacion", new Class[] { File.class }, new Object[] { repo }));
	}

	public static boolean commit(File repo, String mensaje) {
		return booleano(llamar("commit", new Class[] { File.class, String.class }, new Object[] { repo, mensaje }));
	}

	public static String diffArchivo(File repo, String ruta, boolean preparado) {
		Object valor = llamar("diffArchivo", new Class[] { File.class, String.class, boolean.class },
				new Object[] { repo, ruta, Boolean.valueOf(preparado) });
		return valor == null ? "" : valor.toString();
	}

	public static String diffCommit(File repo, String hash) {
		Object valor = llamar("diffCommit", new Class[] { File.class, String.class }, new Object[] { repo, hash });
		return valor == null ? "" : valor.toString();
	}

	public static String blame(File repo, String ruta) {
		Object valor = llamar("blame", new Class[] { File.class, String.class }, new Object[] { repo, ruta });
		return valor == null ? "" : valor.toString();
	}

	public static boolean crearRama(File repo, String nombre) {
		return booleano(llamar("crearRama", new Class[] { File.class, String.class }, new Object[] { repo, nombre }));
	}

	public static boolean cambiarRama(File repo, String nombre) {
		return booleano(llamar("cambiarRama", new Class[] { File.class, String.class }, new Object[] { repo, nombre }));
	}

	public static boolean fetch(File repo) {
		return booleano(llamar("fetch", new Class[] { File.class }, new Object[] { repo }));
	}

	public static boolean pull(File repo) {
		return booleano(llamar("pull", new Class[] { File.class }, new Object[] { repo }));
	}

	public static boolean push(File repo) {
		return booleano(llamar("push", new Class[] { File.class }, new Object[] { repo }));
	}

	private static boolean booleano(Object valor) {
		return valor instanceof Boolean && ((Boolean) valor).booleanValue();
	}

	private static Object llamar(String metodo, Class<?>[] tipos, Object[] argumentos) {
		if (!disponible()) {
			return null;
		}
		try {
			ClassLoader contexto = Thread.currentThread().getContextClassLoader();
			Class<?> clase;
			try {
				clase = Class.forName(CLASE_DIRECTA, true, contexto);
			} catch (Throwable ignorado) {
				clase = Class.forName(CLASE_DIRECTA);
			}
			return clase.getMethod(metodo, tipos).invoke(null, argumentos);
		} catch (Throwable t) {
			CrashDetectorLogger.log("Error en JGit avanzado: " + metodo + " - " + t.getMessage());
			CrashDetectorLogger.logException(t);
			return null;
		}
	}
}
