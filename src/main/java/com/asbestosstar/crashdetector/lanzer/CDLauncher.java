package com.asbestosstar.crashdetector.lanzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador.NoRegistroDeLauncherVShojo;

/**
 * CDLauncher
 *
 * Relanza la aplicación objetivo y garantiza:
 *
 * - stdout y stderr siempre drenados (sin bloqueos) - stderr redirigido a
 * stdout (orden consistente) - Log4j2 forzado a PatternLayout (NO XML / NO
 * CDATA) - Sin inheritIO() - Compatible con Java 8 - CrashDetector solo como
 * javaagent (JPMS-safe) - Argumentos sensibles censurados - Spam de OptiFine
 * silenciado
 */
public class CDLauncher {

	/** Proceso relanzado */
	public static volatile Process proceso_cdlauncher;

	/**
	 * Punto de entrada del relanzador. Construye el comando, inyecta el javaagent y
	 * fuerza la configuración de Log4j2.
	 */
	public static void lanzer() {

		// Limpiar consolas previas (seguridad)
		MonitorDePID.consolas.clear();
		Consola.archivos_en_lista.clear();

		CrashDetectorLogger.enviarALaConsola("[CDLauncher] Iniciando relanzamiento");

		if (Statics.APP_ARGS == null || Statics.APP_ARGS.isEmpty()) {
			throw new IllegalStateException("APP_ARGS vacío");
		}

		// Marca temporal usada por el monitor
		Statics.INICIO_DE_LA_APP = System.currentTimeMillis();

		/* ===================================================== */
		/* 1) Construcción del comando */
		/* ===================================================== */

		List<String> comando = new ArrayList<String>();

		if (Statics.JVM_ARGS != null && !Statics.JVM_ARGS.isEmpty()) {
			comando.addAll(Statics.JVM_ARGS);
		}

		comando.addAll(Statics.APP_ARGS);

		asegurarJVM(comando);
		asegurarClassPath(comando);
		eliminarCrashDetectorDelClasspath(comando);
		inyectarCdLauncher(comando);
		inyectarOpcionesCDLauncher(comando);
		forzarLog4jPatternLayout(comando);
		inyectarProteccionesConsola(comando); // ← flags JVM correctos

		CrashDetectorLogger.enviarALaConsola("[CDLauncher] CMD:");
		CrashDetectorLogger.enviarALaConsola(construirStringLogCensurado(comando));

		/* ===================================================== */
		/* 2) Lanzamiento del proceso */
		/* ===================================================== */

		try {
			ProcessBuilder pb = new ProcessBuilder(comando);

			// stderr → stdout (orden consistente)
			pb.redirectErrorStream(true);

			// ==================================================
			// CLAVE ABSOLUTA:
			// stdin NO interactivo para evitar bloqueo nativo
			// en JLine / TerminalConsoleAppender
			// ==================================================
			pb.redirectInput(ProcessBuilder.Redirect.PIPE);

			Process proceso = pb.start();
			proceso_cdlauncher = proceso;

			// Cerrar stdin inmediatamente (EOF)
			try {
				proceso.getOutputStream().close();
			} catch (Throwable ignored) {
			}

			CrashDetectorLogger.enviarALaConsola("[CDLauncher] Proceso relanzado");

			/* ================================================= */
			/* 3) Bombeo del stream combinado */
			/* ================================================= */

			iniciarBombeoStream(proceso.getInputStream());

			/* ================================================= */
			/* 4) Monitoreo del proceso */
			/* ================================================= */

			Thread monitor = new Thread(() -> {
				try {
					MonitorDePID.monitor_cdlauncher(proceso);
				} catch (Throwable t) {
					try {
						CrashDetectorLogger.enviarALaConsola("[CDLauncher][ERROR] monitor_cdlauncher: "
								+ t.getClass().getName() + ": " + t.getMessage());
					} catch (Throwable ignorado) {
					}
				}
			}, "CDLauncher-MONITOR");

			monitor.setDaemon(true);
			monitor.start();

		} catch (Exception e) {
			CrashDetectorLogger
					.enviarALaConsola("[CDLauncher][ERROR] " + e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	

	/**
	 * Inyecta argumentos JVM para cada opción habilitada en ConfigCDLauncher.
	 *
	 * Formato: -Dcrashdetector.<opcion>=true
	 */
	private static void inyectarOpcionesCDLauncher(List<String> cmd) {

		for (Map.Entry<String, Boolean> e : ConfigCDLauncher.opciones.entrySet()) {

			String opcion = e.getKey();
			Boolean habilitada = e.getValue();

			if (habilitada == null || !habilitada) {
				continue;
			}

			String arg = "-Dcrashdetector." + opcion + "=true";

			// Evitar duplicados
			for (String s : cmd) {
				if (s.equals(arg)) {
					return;
				}
			}

			// INSERTAR COMO JVM ARG (antes de la clase principal)
			cmd.add(1, arg);
		}
	}


	/* ========================================================= */
	/* ================= BOMBA DE STREAM ====================== */
	/* ========================================================= */

	/**
	 * Lee continuamente stdout del proceso relanzado para evitar bloqueos por
	 * buffers llenos y escribe el contenido a un archivo de log de forma eficiente.
	 */
	private static void iniciarBombeoStream(final InputStream entrada) {

		Thread t = new Thread(() -> {

			File log = NoRegistroDeLauncherVShojo.cd_launcherlog;
log.delete();
//log.createNewFile();
			
			
			// Tamaño de buffer grande para minimizar I/O
			final int BUFFER_SIZE = 64 * 1024; // 64 KB

			try (BufferedReader br = new BufferedReader(new InputStreamReader(entrada, StandardCharsets.UTF_8),
					BUFFER_SIZE); BufferedWriter bw = new BufferedWriter(new FileWriter(log, true), BUFFER_SIZE)) {

				String linea;
				int lineasDesdeFlush = 0;

				while ((linea = br.readLine()) != null) {
					linea = censurarTokens(linea);
					if (linea.isEmpty()) {
						continue;
					}

					// Consola (comportamiento actual)
					CrashDetectorLogger.enviarALaConsola(linea);

					// Archivo (append eficiente)
					bw.write(linea);
					bw.newLine();

					// Flush ocasional (no por línea)
					if (++lineasDesdeFlush >= 200) {
						bw.flush();
						lineasDesdeFlush = 0;
					}
				}

				// Flush final al recibir EOF
				bw.flush();

			} catch (Throwable t1) {
				System.err.println("[CDLauncher][stream error] " + t1.getClass().getName() + ": " + t1.getMessage());
			}

		}, "CDLauncher-STREAM-PUMP");

		t.setDaemon(true);
		t.start();
	}

	/**
	 * Inyecta flags JVM para desactivar consola interactiva (JLine /
	 * TerminalConsoleAppender) y evitar bloqueos nativos al cerrar el juego.
	 */
	private static void inyectarProteccionesConsola(List<String> cmd) {

		boolean tieneJLine = false;
		boolean tieneTerminal = false;

		for (String s : cmd) {
			if (s.startsWith("-Dterminal.jline=")) {
				tieneJLine = true;
			}
			if (s.startsWith("-Djline.terminal=")) {
				tieneTerminal = true;
			}
		}

		// Desactivar JLine si no fue definido por el usuario
		if (!tieneJLine) {
			cmd.add(1, "-Dterminal.jline=false");
		}

		// Forzar terminal no interactiva
		if (!tieneTerminal) {
			cmd.add(1, "-Djline.terminal=jline.UnsupportedTerminal");
		}

		// TODO Opcional: silenciar consola Forge
		boolean tieneForgeConsole = false;
//		for (String s : cmd) {
//			if (s.startsWith("-Dforge.logging.console.level=")) {
//				tieneForgeConsole = true;
//				break;
//			}
//		}

		if (!tieneForgeConsole) {
			cmd.add(1, "-Dforge.logging.console.level=off");
		}
	}

	/* ========================================================= */
	/* ======= FORZAR LOG4J2 A PATTERNLAYOUT =================== */
	/* ========================================================= */

	/**
	 * Inyecta propiedades JVM para forzar Log4j2 a usar PatternLayout y evitar
	 * XmlLayout.
	 *
	 * No sobrescribe si el usuario ya definió log4j2.configurationFile.
	 */
	private static void forzarLog4jPatternLayout(List<String> cmd) {

		for (String s : cmd) {
			if (s.startsWith("-Dlog4j2.configurationFile=")) {
				return;
			}
		}

		cmd.add(1, "-Dlog4j2.formatMsgNoLookups=true");
		cmd.add(2, "-Dlog4j2.configurationFile=classpath:log4j2-cdlauncher.properties");
	}

	/* ========================================================= */
	/* ================= CENSURA DE TOKENS ==================== */
	/* ========================================================= */

	/**
	 * Elimina tokens sensibles de argumentos y salida.
	 */
	private static String censurarTokens(String linea) {
		if (linea == null)
			return null;

		linea = linea.replaceAll("(--accessToken\\s+)(\\S+)", "$1null");
		linea = linea.replaceAll("--accessToken=\\S+", "--accessToken=null");
		linea = linea.replaceAll("(--clientId\\s+)(\\S+)", "$1null");
		linea = linea.replaceAll("--clientId=\\S+", "--clientId=null");

		return linea;
	}

	/* ========================================================= */
	/* =================== UTILIDADES JVM ===================== */
	/* ========================================================= */

	/**
	 * Asegura que el comando comience con la JVM correcta.
	 */
	private static void asegurarJVM(List<String> cmd) {
		if (cmd.isEmpty())
			throw new IllegalStateException("Comando vacío");

		String primero = cmd.get(0);
		if (primero.contains("java"))
			return;

		String jvm = MonitorDePID.jvm();
		if (jvm == null || jvm.isEmpty())
			throw new IllegalStateException("No se pudo determinar la JVM");

		cmd.add(0, jvm);
	}

	/**
	 * Inserta el classpath actual si no fue definido.
	 */
	private static void asegurarClassPath(List<String> cmd) {
		for (String s : cmd) {
			if ("-cp".equals(s) || "--class-path".equals(s))
				return;
		}

		String cp = System.getProperty("java.class.path");
		if (cp == null || cp.isEmpty())
			return;

		int pos = 1;
		while (pos < cmd.size()) {
			String a = cmd.get(pos);
			if (a != null && a.startsWith("-javaagent:")) {
				pos++;
				continue;
			}
			break;
		}

		cmd.add(pos, "-cp");
		cmd.add(pos + 1, cp);
	}

	/**
	 * Elimina CrashDetector del classpath para evitar doble carga.
	 */
	private static void eliminarCrashDetectorDelClasspath(List<String> cmd) {

		String jar = MonitorDePID.obtenerRutaJarCrashDetector();
		String sep = System.getProperty("path.separator");
		String jarLower = jar != null ? jar.toLowerCase() : null;

		for (int i = 0; i < cmd.size(); i++) {
			if (!"-cp".equals(cmd.get(i)) && !"--class-path".equals(cmd.get(i)))
				continue;
			if (i + 1 >= cmd.size())
				return;

			String[] partes = cmd.get(i + 1).split(java.util.regex.Pattern.quote(sep));
			StringBuilder limpio = new StringBuilder();

			for (String p : partes) {
				if (p == null || p.isEmpty())
					continue;

				String pl = p.toLowerCase();
				if (jarLower != null && pl.equals(jarLower))
					continue;
				if (pl.contains("crashdetector") && pl.endsWith(".jar"))
					continue;

				if (limpio.length() > 0)
					limpio.append(sep);
				limpio.append(p);
			}

			cmd.set(i + 1, limpio.toString());
			return;
		}
	}

	/**
	 * Inyecta CrashDetector como javaagent si no está presente.
	 */
	private static void inyectarCdLauncher(List<String> cmd) {

		String jar = MonitorDePID.obtenerRutaJarCrashDetector();
		if (jar == null || jar.isEmpty())
			return;

		for (String s : cmd) {
			if (s.startsWith("-javaagent:") && s.contains(jar))
				return;
		}

		cmd.add(1, "-javaagent:" + jar + "=cdlauncher");
	}

	/* ========================================================= */
	/* ================= UTILIDADES DE LOG ==================== */
	/* ========================================================= */

	/**
	 * Construye una representación del comando ocultando tokens sensibles.
	 */
	private static String construirStringLogCensurado(List<String> cmd) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < cmd.size(); i++) {
			String s = cmd.get(i);
			if (sb.length() > 0)
				sb.append(' ');

			if ("--accessToken".equals(s) && i + 1 < cmd.size()) {
				sb.append("--accessToken null");
				i++;
				continue;
			}

			if ("--clientId".equals(s) && i + 1 < cmd.size()) {
				sb.append("--clientId null");
				i++;
				continue;
			}

			if (s.startsWith("--accessToken=")) {
				sb.append("--accessToken=null");
				continue;
			}

			if (s.startsWith("--clientId=")) {
				sb.append("--clientId=null");
				continue;
			}

			sb.append(s);
		}

		return sb.toString();
	}
}
