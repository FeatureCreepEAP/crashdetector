package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.cargador.Cargador;
import com.asbestosstar.crashdetector.detectorlanzer.DetectorLanzer;

/**
 * Entregar
 *
 * RESPONSABILIDAD REAL: - JVM_ARGS: SOLO desde RuntimeMXBean (classpath,
 * module-path, flags) - APP_ARGS: SOLO desde sun.java.command (main class +
 * game args)
 *
 * Nunca reconstruye JVM args desde strings. Nunca vuelve a parsear JVM flags.
 */
public class Entregar {

	public static File archivo = Statics.carpeta.resolve("entregar").toFile();

	private static final String MASK = "*********************************";
	public static App app_detecta;

	/* ========================================================= */
	/* ======================= ESCRITOR ======================== */
	/* ========================================================= */

	public static void comenzarEntregar() {

		app_detecta = App.obtenerApp();
		String idApp = app_detecta != null ? app_detecta.id() : "";

		Buscardor.cargadoresPredetermindado();

		List<Cargador> activos = new ArrayList<Cargador>();
		for (Cargador c : Cargador.cargadores) {
			try {
				if (c.cargadorEsActivado())
					activos.add(c);
			} catch (Throwable ignored) {
			}
		}

		/* ===================================================== */
		/* 1) JVM ARGS (FUENTE CORRECTA) */
		/* ===================================================== */

		List<String> jvmArgs = new ArrayList<String>();
		try {
			jvmArgs.addAll(ManagementFactory.getRuntimeMXBean().getInputArguments());
		} catch (Throwable ignored) {
		}

		Statics.JVM_ARGS = jvmArgs;

		/* ===================================================== */
		/* 2) APP ARGS (MAIN CLASS + GAME ARGS) */
		/* ===================================================== */

		String rawCmd = System.getProperty("sun.java.command", "");
		List<String> appArgs = parsearArgsInicial(rawCmd);

		if (!ConfigMundial.obtenerInstancia().obtenerHabilitarTokenDeAccesoEnLaEntregaDelMonitorDePID()) {
			appArgs = ocultarAccessToken(appArgs);
		}

		Statics.APP_ARGS = appArgs;

		/* ===================================================== */
		/* 3) DETECCIÓN DE LAUNCHER (STRING SOLO PARA DETECTAR) */
		/* ===================================================== */

		String appCmdDetectar = construirStringDetectar(appArgs);

		String lanzer = DetectorLanzer.detectarLanzer(app_detecta, appCmdDetectar);

		Statics.lanzer_del_app = lanzer;

		long inicioApp = obtenerInicioDeLaApp();
		Statics.INICIO_DE_LA_APP = inicioApp;

		String contenido = construirContenidoArchivo(idApp, jvmArgs, appArgs, activos, lanzer, inicioApp);

		escribirArchivo(contenido);
	}

	/* ========================================================= */
	/* ======================== LECTOR ========================= */
	/* ========================================================= */

	public static void recibir() {

		if (!archivo.exists())
			return;

		List<String> jvmArgs = new ArrayList<String>();
		List<String> appArgs = new ArrayList<String>();
		String idApp = "";
		String lanzer = "";
		long inicioApp = 0L;
		String idsCargadores = "";

		try {
			List<String> lineas = java.nio.file.Files.readAllLines(archivo.toPath(), StandardCharsets.UTF_8);

			for (String l : lineas) {
				int p = l.indexOf(':');
				if (p <= 0)
					continue;

				String k = l.substring(0, p).trim();
				String v = l.substring(p + 1).trim();

				if ("app".equals(k))
					idApp = v;
				else if ("lanzer".equals(k))
					lanzer = v;
				else if ("inicio".equals(k)) {
					try {
						inicioApp = Long.parseLong(v);
					} catch (Throwable ignored) {
					}
				} else if ("jvm-arg".equals(k))
					jvmArgs.add(v);
				else if ("app-arg".equals(k))
					appArgs.add(v);
				else if ("cargadores".equals(k))
					idsCargadores = v;
			}

		} catch (IOException ignored) {
		}

		Statics.APP = buscarAppPorId(idApp);
		Statics.lanzer_del_app = lanzer != null ? lanzer : "";
		Statics.JVM_ARGS = jvmArgs;
		Statics.APP_ARGS = appArgs;
		Statics.INICIO_DE_LA_APP = inicioApp;

		Buscardor.cargadoresPredetermindado();

		if (!idsCargadores.isEmpty()) {
			for (String id : idsCargadores.split(",")) {
				Cargador c = buscarCargadorPorId(id.trim());
				if (c != null && !Cargador.cargadores_activados.contains(c)) {
					Cargador.cargadores_activados.add(c);
				}
			}
		}

		archivo.delete();
	}

	/* ========================================================= */
	/* ========================= UTIL ========================== */
	/* ========================================================= */

	/**
	 * Parseo SIMPLE para sun.java.command. SOLO se usa aquí.
	 */
	private static List<String> parsearArgsInicial(String raw) {

		List<String> out = new ArrayList<String>();
		StringBuilder cur = new StringBuilder();
		boolean d = false, s = false;

		for (int i = 0; i < raw.length(); i++) {
			char c = raw.charAt(i);

			if (c == '"' && !s) {
				d = !d;
				continue;
			}
			if (c == '\'' && !d) {
				s = !s;
				continue;
			}

			if (Character.isWhitespace(c) && !d && !s) {
				if (cur.length() > 0) {
					out.add(cur.toString());
					cur.setLength(0);
				}
				continue;
			}
			cur.append(c);
		}

		if (cur.length() > 0)
			out.add(cur.toString());

		return out;
	}

	private static long obtenerInicioDeLaApp() {
		try {
			return ManagementFactory.getRuntimeMXBean().getStartTime();
		} catch (Throwable t) {
			return 0L;
		}
	}

	private static App buscarAppPorId(String id) {
		for (Object o : App.APPS) {
			if (o instanceof App && id.equals(((App) o).id()))
				return (App) o;
		}
		return null;
	}

	private static Cargador buscarCargadorPorId(String id) {
		for (Cargador c : Cargador.cargadores) {
			try {
				if (id.equals(c.id()))
					return c;
			} catch (Throwable ignored) {
			}
		}
		return null;
	}

	private static String construirStringDetectar(List<String> appArgs) {
		StringBuilder sb = new StringBuilder();
		for (String a : appArgs) {
			if (sb.length() > 0)
				sb.append(' ');
			sb.append(a);
		}
		return sb.toString();
	}

	private static List<String> ocultarAccessToken(List<String> args) {
		List<String> out = new ArrayList<String>();
		for (int i = 0; i < args.size(); i++) {
			String a = args.get(i);
			if ("--accessToken".equalsIgnoreCase(a) && i + 1 < args.size()) {
				out.add(a);
				out.add(MASK);
				i++;
			} else {
				out.add(a);
			}
		}
		return out;
	}

	private static String construirContenidoArchivo(String idApp, List<String> jvmArgs, List<String> appArgs,
			List<Cargador> activos, String lanzer, long inicioApp) {

		StringBuilder sb = new StringBuilder();

		sb.append("app: ").append(idApp).append('\n');
		sb.append("lanzer: ").append(lanzer).append('\n');
		sb.append("inicio: ").append(inicioApp).append('\n');

		for (String a : jvmArgs)
			sb.append("jvm-arg: ").append(a).append('\n');

		for (String a : appArgs)
			sb.append("app-arg: ").append(a).append('\n');

		StringBuilder ids = new StringBuilder();
		for (Cargador c : activos) {
			try {
				if (ids.length() > 0)
					ids.append(',');
				ids.append(c.id());
			} catch (Throwable ignored) {
			}
		}

		sb.append("cargadores: ").append(ids).append('\n');
		return sb.toString();
	}

	private static void escribirArchivo(String contenido) {
		try {
			File dir = archivo.getParentFile();
			if (dir != null && !dir.exists())
				dir.mkdirs();
			java.nio.file.Files.write(archivo.toPath(), contenido.getBytes(StandardCharsets.UTF_8));
		} catch (IOException ignored) {
		}
	}
}
