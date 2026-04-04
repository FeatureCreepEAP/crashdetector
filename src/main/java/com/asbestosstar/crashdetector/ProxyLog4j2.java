package com.asbestosstar.crashdetector;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import com.asbestosstar.crashdetector.cargador.Cargador;

/**
 * Integra Log4j2 con el mismo archivo usado por ProxySysOutSysErr sin tocar
 * System.out/System.err directamente.
 *
 * IMPORTANTE: llamar a ProxyLog4j2.init() siempre después de
 * ProxySysOutSysErr.init().
 *
 * Esta implementación evita depender de clases y firmas inestables entre
 * versiones viejas de Log4j2.
 */
public final class ProxyLog4j2 {

	private static final String NOMBRE_APPENDER = "CrashDetectorFileAppender";
	private static final String PATRON_LOG = "[%d{HH:mm:ss}] [%t/%level]: %msg%n";

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

			Map<String, Appender> appenders = configuracion.getAppenders();
			if (appenders != null && appenders.containsKey(NOMBRE_APPENDER)) {
				System.err.println("[ProxyLog4j2] Ya estaba inicializado: " + NOMBRE_APPENDER);
				return;
			}

			Layout<?> layout = crearPatternLayoutCompatible(configuracion, PATRON_LOG);
			if (layout == null) {
				System.err.println("[ProxyLog4j2] No se pudo crear un PatternLayout compatible");
				return;
			}

			Appender appender = new AppenderFlujoCompatible(NOMBRE_APPENDER, layout, null, false,
					ProxySysOutSysErr.flujoSincronizadoSeguro, true);

			appender.start();

			LoggerConfig loggerRaiz = configuracion.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
			if (loggerRaiz == null) {
				System.err.println("[ProxyLog4j2] No se encontró el logger raíz");
				return;
			}

			loggerRaiz.addAppender(appender, Level.ALL, null);
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

	/**
	 * Crea un PatternLayout compatible probando varias APIs conocidas.
	 */
	@SuppressWarnings("unchecked")
	private static Layout<?> crearPatternLayoutCompatible(Configuration configuracion, String patron) {
		try {
			Class<?> clasePatternLayout = Class.forName("org.apache.logging.log4j.core.layout.PatternLayout");

			// Variante moderna con builder
			try {
				Method newBuilder = clasePatternLayout.getMethod("newBuilder");
				Object builder = newBuilder.invoke(null);

				Method withConfiguration = builder.getClass().getMethod("withConfiguration", Configuration.class);
				Method withPattern = builder.getClass().getMethod("withPattern", String.class);
				Method build = builder.getClass().getMethod("build");

				withConfiguration.invoke(builder, configuracion);
				withPattern.invoke(builder, patron);

				Object layout = build.invoke(builder);
				if (layout instanceof Layout) {
					return (Layout<?>) layout;
				}
			} catch (Throwable ignored) {
				// Probar otra variante
			}

			// Variante vieja: createLayout(String, Configuration, RegexReplacement, String,
			// String)
			try {
				Class<?> regexReplacementClass = Class
						.forName("org.apache.logging.log4j.core.pattern.RegexReplacement");

				Method createLayout = clasePatternLayout.getMethod("createLayout", String.class, Configuration.class,
						regexReplacementClass, String.class, String.class);

				Object layout = createLayout.invoke(null, patron, configuracion, null, null, null);
				if (layout instanceof Layout) {
					return (Layout<?>) layout;
				}
			} catch (Throwable ignored) {
				// Probar otra variante
			}

			// Variante larga: createLayout(... 9 parámetros ...)
			try {
				Class<?> patternSelectorClass = Class.forName("org.apache.logging.log4j.core.layout.PatternSelector");
				Class<?> regexReplacementClass = Class
						.forName("org.apache.logging.log4j.core.pattern.RegexReplacement");
				Class<?> charsetClass = Class.forName("java.nio.charset.Charset");

				Method createLayout = clasePatternLayout.getMethod("createLayout", String.class, patternSelectorClass,
						Configuration.class, regexReplacementClass, charsetClass, boolean.class, boolean.class,
						String.class, String.class);

				Object layout = createLayout.invoke(null, patron, null, configuracion, null, null, Boolean.TRUE,
						Boolean.FALSE, null, null);

				if (layout instanceof Layout) {
					return (Layout<?>) layout;
				}
			} catch (Throwable ignored) {
				// Probar otra variante
			}

			// Último recurso
			try {
				Method createDefaultLayout = clasePatternLayout.getMethod("createDefaultLayout");
				Object layout = createDefaultLayout.invoke(null);
				if (layout instanceof Layout) {
					return (Layout<?>) layout;
				}
			} catch (Throwable ignored) {
				// Sin más alternativas
			}

		} catch (Throwable t) {
			System.err.println("[ProxyLog4j2] Error creando PatternLayout compatible: " + t);
		}

		return null;
	}
}