package com.asbestosstar.crashdetector;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.appender.OutputStreamAppender;

import com.asbestosstar.crashdetector.cargador.Cargador;

/**
 * Integra Log4j2 con el mismo archivo usado por ProxySysOutSysErr SIN TOCAR
 * System.out/System.err directamente.
 * 
 * IMPORTANTE: Llamar a ProxyLog4j2.init() SIEMPRE DESPUÉS de
 * ProxySysOutSysErr.init()
 */
public class ProxyLog4j2 {

	public static void init() {
		if (!log4j2existe()) {
			System.err.println("[ProxyLog4j2] Saltando - clases de Log4j2 no encontradas");
			return;
		}

		try {
			// CRÍTICO: Esperar a que el proxy esté inicializado
			while (ProxySysOutSysErr.flujoSincronizadoSeguro == null) {
				Thread.sleep(10); // Esperar inicialización del proxy
			}

			// Obtener contexto y configuración actual de Log4j2
			LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
			Configuration config = ctx.getConfiguration();

			// PATRÓN AL ESTILO MINECRAFT
			PatternLayout layout = PatternLayout.newBuilder().withConfiguration(config)
					.withPattern("[%d{HH:mm:ss}] [%t/%level]: %msg%n") // Formato consistente sin espacios extra
					.build();

			// USAR EL FLUJO SINCRONIZADO DEL PROXY (clave para evitar duplicados)
			OutputStreamAppender appender = OutputStreamAppender.newBuilder().setName("CrashDetectorFileAppender")
					.setTarget(ProxySysOutSysErr.flujoSincronizadoSeguro) // <-- CAMBIO CLAVE
					.setLayout(layout).setIgnoreExceptions(false) // Manejar errores correctamente
					.build();

			appender.start();
			config.addAppender(appender);

			// Eliminar appenders de consola EXISTENTES para evitar duplicados
			LoggerConfig loggerRaiz = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
//			loggerRaiz.getAppenders().values().stream()
//					.filter(a -> a instanceof org.apache.logging.log4j.core.appender.ConsoleAppender)
//					.forEach(a -> loggerRaiz.removeAppender(a.getName()));

			// Añadir nuestro appender de archivo
			loggerRaiz.addAppender(appender, Level.ALL, null);
			ctx.updateLoggers();

			System.err.println(
					"[ProxyLog4j2] Integrado con archivo de log: " + ProxySysOutSysErr.archivoLog.getAbsolutePath());

		} catch (Throwable t) {
			System.err.println("[ProxyLog4j2] Falló inicialización: " + t.getMessage());
			t.printStackTrace();
		}
	}

	public static boolean log4j2existe() {
		return Cargador.claseExiste("org.apache.logging.log4j.core.LoggerContext");
	}
}