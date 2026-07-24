package com.asbestosstar.crashdetector.gui.tipos.correo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Puerta segura hacia la implementación Jakarta Mail.
 *
 * Esta clase no importa ni menciona tipos Jakarta Mail en firmas, campos o
 * bytecode enlazado. Por eso el resto de CrashDetector puede iniciar y abrir la
 * GUI aunque los JAR de correo no estén presentes en tiempo de ejecución.
 *
 * La clase {@link CorreoImapJakarta}, que sí contiene las llamadas directas a
 * Jakarta Mail, solo se carga por reflexión después de comprobar que tanto la
 * API como la implementación IMAPS están disponibles.
 */
public final class CorreoImapOpcional {

	private static final String CLASE_SESSION = "jakarta.mail.Session";
	private static final String CLASE_IMPLEMENTACION_IMAP = "org.eclipse.angus.mail.imap.IMAPStore";
	private static final String CLASE_CLIENTE = "com.asbestosstar.crashdetector.gui.tipos.correo.CorreoImapJakarta";

	private CorreoImapOpcional() {
	}

	public static boolean dependenciasDisponibles() {
		ClassLoader loader = obtenerClassLoader();
		return existeClase(CLASE_SESSION, loader) && existeClase(CLASE_IMPLEMENTACION_IMAP, loader);
	}

	public static String diagnosticoDependencias() {
		ClassLoader loader = obtenerClassLoader();
		return estadoClase(CLASE_SESSION, loader) + ", " + estadoClase(CLASE_IMPLEMENTACION_IMAP, loader);
	}

	public static List<MensajeCorreo> descargarMensajes(CuentaCorreo cuenta, int cantidadMaxima,
			int cuerpoMaximoCaracteres) throws Exception {
		ClassLoader loader = obtenerClassLoader();
		if (!existeClase(CLASE_SESSION, loader) || !existeClase(CLASE_IMPLEMENTACION_IMAP, loader)) {
			throw new ClassNotFoundException(diagnosticoDependencias());
		}

		try {
			Class<?> cliente = Class.forName(CLASE_CLIENTE, true, loader);
			Method metodo = cliente.getMethod("descargarMensajes", CuentaCorreo.class, int.class, int.class);
			Object resultado = metodo.invoke(null, cuenta, Integer.valueOf(cantidadMaxima),
					Integer.valueOf(cuerpoMaximoCaracteres));
			return convertirLista(resultado);
		} catch (InvocationTargetException e) {
			lanzarCausa(e.getCause());
			return java.util.Collections.emptyList();
		} catch (NoClassDefFoundError e) {
			throw new ClassNotFoundException(diagnosticoDependencias(), e);
		} catch (LinkageError e) {
			throw new ClassNotFoundException(diagnosticoDependencias(), e);
		}
	}

	@SuppressWarnings("unchecked")
	private static List<MensajeCorreo> convertirLista(Object resultado) {
		if (!(resultado instanceof List<?>)) {
			return java.util.Collections.emptyList();
		}
		return (List<MensajeCorreo>) resultado;
	}

	private static String estadoClase(String nombre, ClassLoader loader) {
		return nombre + "=" + (existeClase(nombre, loader) ? "OK" : "NO");
	}

	private static boolean existeClase(String nombre, ClassLoader loader) {
		try {
			Class.forName(nombre, false, loader);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	private static ClassLoader obtenerClassLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		return loader == null ? CorreoImapOpcional.class.getClassLoader() : loader;
	}

	private static void lanzarCausa(Throwable causa) throws Exception {
		if (causa instanceof Exception) {
			throw (Exception) causa;
		}
		if (causa instanceof Error) {
			throw (Error) causa;
		}
		throw new RuntimeException(causa);
	}
}
