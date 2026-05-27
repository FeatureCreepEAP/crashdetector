package com.asbestosstar.crashdetector.gui.tipos.jgit;

import java.io.File;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Puente seguro hacia JGit. No importa clases de JGit directamente.
 *
 * Llama por reflexión a OperacionesJGitDirectas solamente si JGit existe.
 */
public class JGitReflexivo {

	public static final String CLASE_DIRECTA = "com.asbestosstar.crashdetector.gui.tipos.jgit.OperacionesJGitDirectas";

	public static boolean disponible() {
		return BuscarParaJGit.estaJGitBasicoEnClasspath();
	}

	public static boolean repositorioExiste(File carpeta) {
		Object r = llamar("repositorioExiste", new Class[] { File.class }, new Object[] { carpeta });
		return r instanceof Boolean ? ((Boolean) r).booleanValue() : false;
	}

	public static boolean inicializarRepositorio(File carpeta) {
		Object r = llamar("inicializarRepositorio", new Class[] { File.class }, new Object[] { carpeta });
		return r instanceof Boolean ? ((Boolean) r).booleanValue() : false;
	}

	public static boolean commitAutomatico(File carpeta, String mensaje) {
		Object r = llamar("commitAutomatico", new Class[] { File.class, String.class },
				new Object[] { carpeta, mensaje });
		return r instanceof Boolean ? ((Boolean) r).booleanValue() : false;
	}

	public static boolean tieneRemote(File carpeta) {
		Object r = llamar("tieneRemote", new Class[] { File.class }, new Object[] { carpeta });
		return r instanceof Boolean ? ((Boolean) r).booleanValue() : false;
	}

	public static String obtenerRemote(File carpeta) {
		Object r = llamar("obtenerRemote", new Class[] { File.class }, new Object[] { carpeta });
		return r == null ? "" : r.toString();
	}

	public static boolean establecerRemote(File carpeta, String url) {
		Object r = llamar("establecerRemote", new Class[] { File.class, String.class }, new Object[] { carpeta, url });
		return r instanceof Boolean ? ((Boolean) r).booleanValue() : false;
	}

	public static boolean push(File carpeta) {
		Object r = llamar("push", new Class[] { File.class }, new Object[] { carpeta });
		return r instanceof Boolean ? ((Boolean) r).booleanValue() : false;
	}

	public static void abrirGuiSwing(File carpeta) {
		llamar("abrirGuiSwing", new Class[] { File.class }, new Object[] { carpeta });
	}

	public static Object llamar(String metodo, Class[] tipos, Object[] args) {
		if (!disponible()) {
			return null;
		}

		try {
			Class<?> c = Class.forName(CLASE_DIRECTA);
			return c.getMethod(metodo, tipos).invoke(null, args);
		} catch (Throwable t) {
			CrashDetectorLogger.log("Error llamando JGit por reflexión: " + metodo + " - " + t.getMessage());
			CrashDetectorLogger.logException(t);
			return null;
		}
	}
}