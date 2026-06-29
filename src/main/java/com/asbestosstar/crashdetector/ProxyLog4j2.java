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
 */
public final class ProxyLog4j2 {

	private static final String NOMBRE_APPENDER = "CrashDetectorFileAppender";
	private static final String PATRON_LOG = "[%d{HH:mm:ss}] [%t/%level]: %msg%n";

	// Variables para el vigilante
	private static volatile boolean vigilando = false;
	private static Thread hiloVigilante;

	private ProxyLog4j2() {
		// Clase utilitaria
	}

	public static void init() {

		try {
			esperarProxyListo();

			if (inyectarAppender(false)) {
				iniciarVigilante();
			}

		} catch (Throwable t) {
			System.err.println("[ProxyLog4j2] Falló inicialización: " + t);
			t.printStackTrace();
		}
	}

	/**
	 * Separamos la inyección en su propio método.
	 * 
	 * @param forzarLimpieza Si es true, elimina el appender antes de inyectar para
	 *                       evitar conflictos de estado.
	 */
	private static synchronized boolean inyectarAppender(boolean forzarLimpieza) {
		try {
			LoggerContext contexto = (LoggerContext) LogManager.getContext(false);
			Configuration configuracion = contexto.getConfiguration();

			LoggerConfig loggerRaiz = configuracion.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
			if (loggerRaiz == null) {
				return false;
			}

			// Limpieza preventiva si un mod lo corrompió o para evitar duplicados exactos
			if (forzarLimpieza) {
				try {
					Appender existente = loggerRaiz.getAppenders().get(NOMBRE_APPENDER);
					if (existente != null) {
						existente.stop();
						loggerRaiz.removeAppender(NOMBRE_APPENDER);
					}
				} catch (Throwable ignored) {
				}
			} else {
				// En el inicio normal, si ya existe, no hacemos nada
				if (loggerRaiz.getAppenders().containsKey(NOMBRE_APPENDER)) {
					return true;
				}
			}

			Layout<?> layout = crearPatternLayoutCompatible(configuracion, PATRON_LOG);
			if (layout == null) {
				return false;
			}

			Appender appender = new AppenderFlujoCompatible(NOMBRE_APPENDER, layout, null, false,
					ProxySysOutSysErr.flujoSincronizadoSeguro, true);

			appender.start();
			loggerRaiz.addAppender(appender, Level.ALL, null);
			contexto.updateLoggers();

			System.err.println("[ProxyLog4j2] Appender inyectado exitosamente.");
			return true;

		} catch (Throwable t) {
			System.err.println("[ProxyLog4j2] Error al inyectar Appender: " + t.getMessage());
			return false;
		}
	}

	/**
	 * Hilo de fondo que verifica cada 5 segundos si el appender sigue vivo.
	 */
	private static void iniciarVigilante() {
		if (vigilando) {
			return;
		}
		vigilando = true;

		hiloVigilante = new Thread(() -> {
			int fallosConsecutivos = 0;
			final int UMBRAL_FALLOS = 3; // Esperar a que falle 3 veces seguidas (15 segundos)

			while (vigilando) {
				try {
					// Esperar 5 segundos
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}

				try {
					if (ProxySysOutSysErr.flujoSincronizadoSeguro == null) {
						continue;
					}

					LoggerContext contexto = (LoggerContext) LogManager.getContext(false);
					if (contexto == null) {
						fallosConsecutivos++;
						continue;
					}

					Configuration configuracion = contexto.getConfiguration();
					if (configuracion == null) {
						fallosConsecutivos++;
						continue;
					}

					LoggerConfig loggerRaiz = configuracion.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
					if (loggerRaiz == null) {
						fallosConsecutivos++;
						continue;
					}

					Map<String, Appender> appenders = loggerRaiz.getAppenders();

					// CASO 1: El mapa de appenders es nulo (actualización temporal de Log4j2)
					if (appenders == null) {
						fallosConsecutivos++;
						continue;
					}

					// CASO 2: Realmente no está en la lista
					if (!appenders.containsKey(NOMBRE_APPENDER)) {
						fallosConsecutivos++;
					}
					// CASO 3: Sí está, pero está detenido (otro mod hizo stop() pero no
					// removeAppender())
					else if (!appenders.get(NOMBRE_APPENDER).isStarted()) {
						fallosConsecutivos++;
					}
					// CASO 4: Todo funciona bien
					else {
						fallosConsecutivos = 0; // Reiniciamos el contador
						continue;
					}

					// Si falla 3 veces seguidas (15 seg), asumimos que un mod lo borró de verdad
					if (fallosConsecutivos >= UMBRAL_FALLOS) {
						System.err.println(
								"[ProxyLog4j2] ¡ALERTA! El Appender fue eliminado o detenido por un mod. Restaurando...");
						inyectarAppender(true); // true = forzar limpieza antes de inyectar
						fallosConsecutivos = 0; // Reiniciamos el contador
					}

				} catch (Throwable t) {
					// Evitar que el vigilante muera por errores de concurrencia raros
					fallosConsecutivos++;
				}
			}
		}, "ProxyLog4j2-Vigilante");

		hiloVigilante.setDaemon(true);
		hiloVigilante.start();
	}

	private static void esperarProxyListo() throws InterruptedException {
		while (ProxySysOutSysErr.flujoSincronizadoSeguro == null) {
			Thread.sleep(10);
		}
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
			}

			// Último recurso
			try {
				Method createDefaultLayout = clasePatternLayout.getMethod("createDefaultLayout");
				Object layout = createDefaultLayout.invoke(null);
				if (layout instanceof Layout) {
					return (Layout<?>) layout;
				}
			} catch (Throwable ignored) {
			}

		} catch (Throwable t) {
			System.err.println("[ProxyLog4j2] Error creando PatternLayout compatible: " + t);
		}

		return null;
	}
}