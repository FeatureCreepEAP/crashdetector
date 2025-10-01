package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnlanceMD {

	/** Carpeta temporal donde se guardan los registros descargados. */
	public static File carpeta = MonitorDePID.carpeta.resolve(".tmp/tmp_registros").toFile();
	/**
	 * Contador para nombres genéricos cuando no se puede extraer un nombre claro.
	 */
	private static final AtomicInteger contadorGenerico = new AtomicInteger(1);

	/** Número máximo de hilos para descargas en paralelo. */
	private static final int HILOS_MAX = Math.max(2, Math.min(8, Runtime.getRuntime().availableProcessors()));

	/**
	 * Punto de entrada: limpia la carpeta temporal, extrae enlaces Markdown y
	 * descarga en paralelo los registros reconocidos. Cada archivo descargado se
	 * agrega como una consola a {@link MonitorDePID}.
	 *
	 * @param textoDeMensaje Texto Markdown con enlaces del tipo [texto](url)
	 */
	public static void guardar(String textoDeMensaje) {
		//CrashDetectorLogger.log("EnlaceMD: limpiando carpeta temporal");
		limpiarCarpetaTemporal();
		contadorGenerico.set(1);

		Pattern patron = Pattern.compile(Pattern.quote("[") + "([^\\]]+)" + // Grupo 1: texto visible
				Pattern.quote("](") + "<?([^)>]+)>?" + // Grupo 2: URL
				Pattern.quote(")"));

		Matcher m = patron.matcher(textoDeMensaje);
		List<Enlace> enlaces = new ArrayList<>();
		while (m.find()) {
			String texto = m.group(1);
			String url = m.group(2);
			//CrashDetectorLogger.log("EnlaceMD: enlace -> " + url);
			enlaces.add(new Enlace(texto, url));
		}

		if (enlaces.isEmpty()) {
			//CrashDetectorLogger.log("EnlaceMD: no se encontraron enlaces");
			return;
		}

		ExecutorService pool = Executors.newFixedThreadPool(HILOS_MAX);
		List<Future<Consola>> tareas = new ArrayList<>();

		for (Enlace e : enlaces) {
			tareas.add(pool.submit(new TareaDescarga(e)));
		}

		List<Consola> consolasNuevas = new ArrayList<>();
		for (Future<Consola> f : tareas) {
			try {
				Consola c = f.get();
				if (c != null)
					consolasNuevas.add(c);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			//	CrashDetectorLogger.log("EnlaceMD: descarga interrumpida");
			} catch (ExecutionException ex) {
			//	CrashDetectorLogger.log("EnlaceMD: error en tarea -> " + ex.getCause());
			}
		}

		pool.shutdown();

		if (!consolasNuevas.isEmpty()) {
			MonitorDePID.consolas.addAll(consolasNuevas);
			MonitorDePID.consola_de_launcher_inyectado = true;
		}

		//CrashDetectorLogger.log("EnlaceMD: finalizado, consolas nuevas = " + consolasNuevas.size());
	}

	/** Datos de un enlace Markdown. */
	private static class Enlace {
		final String texto;
		final String url;

		Enlace(String texto, String url) {
			this.texto = texto;
			this.url = url;
		}
	}

	/**
	 * Tarea que descarga un enlace si se reconoce como registro y construye la
	 * consola correspondiente.
	 */
	private static class TareaDescarga implements Callable<Consola> {
		private final Enlace enlace;

		TareaDescarga(Enlace enlace) {
			this.enlace = enlace;
		}

		@Override
		public Consola call() {
			String texto = enlace.texto;
			String url = enlace.url;

			String fuenteDeNombre = contieneNombreLog(url) ? url : texto;
			if (!contieneNombreLog(fuenteDeNombre)) {
				//CrashDetectorLogger.log("EnlaceMD: se omite (no parece log) -> " + url);
				return null;
			}

			try {
				String nombreFinal = extraerNombreLog(fuenteDeNombre);
				File destino = new File(carpeta, obtenerNombreUnico(nombreFinal));

				descargarArchivo(url, destino);

				Consola consola = new Consola(destino.toPath());
				consola.finalizarContenidoInyectado(MonitorDePID.leer_archivo(destino.toPath()));
				return consola;
			} catch (Exception e) {
			//	CrashDetectorLogger.log("EnlaceMD: error procesando " + url + " -> " + e.getMessage());
				return null;
			}
		}
	}

	/**
	 * Indica si el texto contiene un patrón de nombre de archivo de log conocido.
	 */
	private static boolean contieneNombreLog(String texto) {
		String[] patrones = { "latest\\.log", "launcher_log\\.txt", "debug\\.log", "tlauncher.*\\.log",
				"sklauncher_logs\\.txt", "hs_err_pid.*\\.log", "crash-.*\\.(?:log|txt)" };
		for (String p : patrones) {
			if (texto.matches(".*" + p + ".*"))
				return true;
		}
		return false;
	}

	/**
	 * Extrae un nombre de archivo de log adecuado a partir del texto dado. Si no se
	 * puede, genera un nombre genérico.
	 */
	private static String extraerNombreLog(String texto) {
		String[] patrones = { "(latest\\.log)", "(launcher_log\\.txt)", "(debug\\.log)", "(tlauncher.*\\.log)",
				"(sklauncher_logs\\.txt)", "(hs_err_pid.*\\.log)", "(crash-.*\\.(?:log|txt))" };
		for (String p : patrones) {
			Matcher m = Pattern.compile(p).matcher(texto);
			if (m.find()) {
				String nombre = m.group(1);
				if (nombre.startsWith("crash-"))
					return nombre;
				nombre = nombre.replaceFirst("(_\\d+)?\\.(log)$", ".log").replaceFirst("(_\\d+)?\\.(txt)$", ".txt");
				return nombre;
			}
		}

		int slash = texto.lastIndexOf('/');
		if (slash != -1 && slash + 1 < texto.length()) {
			String ultimo = texto.substring(slash + 1);
			if (ultimo.matches(".*\\.(log|txt)"))
				return ultimo;
		}

		return "registro_generico_" + contadorGenerico.getAndIncrement() + ".log";
	}

	/**
	 * Obtiene un nombre único dentro de la carpeta temporal conservando la
	 * extensión. Se sincroniza para evitar condiciones de carrera entre hilos.
	 */
	private static synchronized String obtenerNombreUnico(String nombreOriginal) {
		if (!carpeta.exists())
			carpeta.mkdirs();

		File f = new File(carpeta, nombreOriginal);
		if (!f.exists())
			return nombreOriginal;

		Matcher m = Pattern.compile("\\.(log|txt)$").matcher(nombreOriginal);
		String base;
		String ext;
		if (m.find()) {
			ext = m.group(1);
			base = nombreOriginal.substring(0, m.start());
		} else {
			ext = "";
			base = nombreOriginal;
		}

		int i = 2;
		do {
			String candidato = ext.isEmpty() ? base + "_" + i : base + "_" + i + "." + ext;
			f = new File(carpeta, candidato);
			i++;
		} while (f.exists());

		return f.getName();
	}

	/**
	 * Limpia la carpeta temporal eliminando archivos existentes.
	 */
	private static void limpiarCarpetaTemporal() {
		if (!carpeta.exists()) {
			carpeta.mkdirs();
			return;
		}
		File[] archivos = carpeta.listFiles();
		if (archivos == null)
			return;
		for (File a : archivos) {
			if (a.isFile()) {
				try {
					a.delete();
				} catch (Exception ignorado) {
				}
			}
		}
	}

	/**
	 * Descarga el archivo al destino. Usa DescargarRegistors si soporta la URL.
	 */
	private static void descargarArchivo(String url, File destino) throws IOException {
		if (DescargarRegistors.esURLSoportada(url)) {
			List<String> urls = Collections.singletonList(url);
			List<File> descargados = DescargarRegistors.descargarYGuardarRegistros(urls, destino.getParentFile());
			if (descargados.isEmpty())
				throw new IOException("No se pudo descargar: " + url);

			File origen = descargados.get(0);
			if (!origen.equals(destino)) {
				if (!origen.renameTo(destino)) {
					Files.copy(origen.toPath(), destino.toPath());
					try {
						origen.delete();
					} catch (Exception ignorado) {
					}
				}
			}
		} else {
			DescargarRegistors.descargarArchivo(url, destino);
		}
	}
}
