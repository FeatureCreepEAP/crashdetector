package com.asbestosstar.crashdetector;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.OutputStreamAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import com.asbestosstar.crashdetector.cargador.Cargador;

/**
 * Integra Log4j2 con el mismo archivo usado por ProxySysOutSysErr sin tocar
 * System.out/System.err directamente.
 *
 * IMPORTANTE: llamar a ProxyLog4j2.init() después de ProxySysOutSysErr.init()
 *
 * Esta implementación evita el uso del Builder de OutputStreamAppender porque
 * algunas versiones viejas de Log4j2 no son compatibles binariamente con builds
 * compiladas contra versiones más nuevas.
 */
public final class ProxyLog4j2 {

	private static final String NOMBRE_APPENDER = "CrashDetectorFileAppender";

	private ProxyLog4j2() {
		// Clase utilitaria
	}

	public static void init() {
		if (!log4j2existe()) {
			System.err.println("[ProxyLog4j2] Saltando - clases de Log4j2 no encontradas");
			return;
		}

		try {
			esperarProxyListo();

			LoggerContext contexto = (LoggerContext) LogManager.getContext(false);
			Configuration configuracion = contexto.getConfiguration();

			// Evitar registrar dos veces el mismo appender si init() se llama más de una
			// vez.
			Appender existente = configuracion.getAppender(NOMBRE_APPENDER);
			if (existente != null) {
				System.err.println("[ProxyLog4j2] Ya estaba inicializado: " + NOMBRE_APPENDER);
				return;
			}

			PatternLayout layout = PatternLayout.newBuilder().withConfiguration(configuracion)
					.withPattern("[%d{HH:mm:ss}] [%t/%level]: %msg%n").build();

			/*
			 * Se usa la factoría estática en lugar del Builder para mejorar la
			 * compatibilidad con versiones viejas de Log4j2.
			 *
			 * Parámetros: - layout: formato de salida - filter: null - target: flujo del
			 * proxy - name: nombre del appender - follow: false - ignore: false para no
			 * tragar errores silenciosamente
			 */
			OutputStreamAppender appender = OutputStreamAppender.createAppender(layout, null,
					ProxySysOutSysErr.flujoSincronizadoSeguro, NOMBRE_APPENDER, false, false);

			if (appender == null) {
				System.err.println("[ProxyLog4j2] No se pudo crear el appender de Log4j2");
				return;
			}

			appender.start();
			configuracion.addAppender(appender);

			LoggerConfig loggerRaiz = configuracion.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

			// Añadir nuestro appender al logger raíz.
			loggerRaiz.addAppender(appender, Level.ALL, null);

			/*
			 * Si quieres evitar duplicados de consola, puedes descomentar este bloque para
			 * eliminar appenders de consola ya existentes.
			 */
			/*
			 * for (Appender a : loggerRaiz.getAppenders().values()) { if (a instanceof
			 * org.apache.logging.log4j.core.appender.ConsoleAppender) {
			 * loggerRaiz.removeAppender(a.getName()); } }
			 */

			contexto.updateLoggers();

			System.err.println(
					"[ProxyLog4j2] Integrado con archivo de log: " + ProxySysOutSysErr.archivoLog.getAbsolutePath());

		} catch (Throwable t) {
			System.err.println("[ProxyLog4j2] Falló inicialización: " + t);
			t.printStackTrace();
		}
	}

	private static void esperarProxyListo() throws InterruptedException {
		while (ProxySysOutSysErr.flujoSincronizadoSeguro == null) {
			Thread.sleep(10);
		}
	}

	public static boolean log4j2existe() {
		return Cargador.claseExiste("org.apache.logging.log4j.core.LoggerContext");
	}
}