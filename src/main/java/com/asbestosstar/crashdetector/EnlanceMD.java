package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
	public static File carpeta = Statics.carpeta.resolve(".tmp/tmp_registros").toFile();

	/**
	 * Contador para nombres genéricos cuando no se puede extraer un nombre claro.
	 */
	private static final AtomicInteger contadorGenerico = new AtomicInteger(1);

	/** Número máximo de hilos para descargas en paralelo. */
	private static final int HILOS_MAX = Math.max(2, Math.min(8, Runtime.getRuntime().availableProcessors()));

	/**
	 * Expresión para extraer enlaces Markdown del tipo [texto](url) y también
	 * soportar URLs entre <...>.
	 */
	private static final Pattern PATRON_ENLACE_MD = Pattern
			.compile(Pattern.quote("[") + "([^\\]]+)" + Pattern.quote("](") + "<?([^)>]+)>?" + Pattern.quote(")"));

	/**
	 * Patrones de nombres de archivos de log/crash comúnmente compartidos.
	 */
	private static final String[] PATRONES_NOMBRE_LOG = { "latest\\.log", "launcher_log\\.txt", "debug\\.log",
			"tlauncher.*\\.log", "stderr_stream\\.log", "hs_err_pid.*\\.log", "crash-.*\\.(?:log|txt)",
			"modlist\\.txt" };

	/**
	 * Punto de entrada: limpia la carpeta temporal, extrae enlaces Markdown y
	 * descarga en paralelo los registros reconocidos. Si un mismo archivo viene
	 * dividido en varias partes (por ejemplo head/tail o partes numeradas), se
	 * combinan en un único archivo antes de crear la consola.
	 *
	 * @param textoDeMensaje Texto Markdown con enlaces del tipo [texto](url)
	 */
	public static void guardar(String textoDeMensaje) {
		limpiarCarpetaTemporal();
		contadorGenerico.set(1);

		List<Enlace> enlaces = extraerEnlacesMarkdown(textoDeMensaje);
		if (enlaces.isEmpty()) {
			return;
		}

		/*
		 * Primero se agrupan los enlaces que realmente representan el mismo archivo
		 * lógico. Después, cada grupo se procesa en paralelo para no perder el
		 * comportamiento multihilo que ya tenía la clase original.
		 */
		Map<String, GrupoDeArchivo> grupos = agruparEnlaces(enlaces);
		if (grupos.isEmpty()) {
			return;
		}

		ExecutorService pool = Executors.newFixedThreadPool(Math.min(HILOS_MAX, Math.max(1, grupos.size())));
		List<Future<Consola>> tareas = new ArrayList<>();

		for (GrupoDeArchivo grupo : grupos.values()) {
			tareas.add(pool.submit(new TareaDescargaGrupo(grupo)));
		}

		List<Consola> consolasNuevas = new ArrayList<>();
		for (Future<Consola> f : tareas) {
			try {
				Consola c = f.get();
				if (c != null) {
					consolasNuevas.add(c);
				}
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			} catch (ExecutionException ex) {
				// Se omite el grupo fallido para no bloquear el resto.
			}
		}

		pool.shutdown();

		if (!consolasNuevas.isEmpty()) {
			MonitorDePID.consolas.addAll(consolasNuevas);
			MonitorDePID.consola_de_launcher_inyectado = true;
		}
	}

	/** Datos originales de un enlace Markdown. */
	private static class Enlace {
		final String texto;
		final String url;

		Enlace(String texto, String url) {
			this.texto = texto == null ? "" : texto.trim();
			this.url = url == null ? "" : url.trim();
		}
	}

	/**
	 * Describe un fragmento descargable de un archivo lógico. Puede representar un
	 * archivo completo, un head, un tail o una parte numerada.
	 */
	private static class Fragmento {
		final Enlace enlace;
		final String nombreBase;
		final TipoFragmento tipo;
		final int indiceParte;

		Fragmento(Enlace enlace, String nombreBase, TipoFragmento tipo, int indiceParte) {
			this.enlace = enlace;
			this.nombreBase = nombreBase;
			this.tipo = tipo;
			this.indiceParte = indiceParte;
		}
	}

	/** Archivo lógico compuesto por uno o varios fragmentos. */
	private static class GrupoDeArchivo {
		final String nombreBase;
		final List<Fragmento> fragmentos = new ArrayList<>();

		GrupoDeArchivo(String nombreBase) {
			this.nombreBase = nombreBase;
		}
	}

	/**
	 * Tarea multihilo que descarga y reconstruye un archivo lógico completo.
	 */
	private static class TareaDescargaGrupo implements Callable<Consola> {
		private final GrupoDeArchivo grupo;

		TareaDescargaGrupo(GrupoDeArchivo grupo) {
			this.grupo = grupo;
		}

		@Override
		public Consola call() {
			try {
				return descargarYConstruirConsola(grupo);
			} catch (Exception e) {
				return null;
			}
		}
	}

	/** Tipo de fragmento detectado en el texto visible del enlace. */
	private enum TipoFragmento {
		ARCHIVO_COMPLETO, HEAD, PARTE_NUMERADA, TAIL, DESCONOCIDO
	}

	/**
	 * Extrae todos los enlaces Markdown presentes en el texto.
	 */
	private static List<Enlace> extraerEnlacesMarkdown(String textoDeMensaje) {
		Matcher m = PATRON_ENLACE_MD.matcher(textoDeMensaje == null ? "" : textoDeMensaje);
		List<Enlace> enlaces = new ArrayList<>();
		while (m.find()) {
			enlaces.add(new Enlace(m.group(1), m.group(2)));
		}
		return enlaces;
	}

	/**
	 * Agrupa todos los enlaces que pertenecen al mismo archivo lógico.
	 */
	private static Map<String, GrupoDeArchivo> agruparEnlaces(List<Enlace> enlaces) {
		Map<String, GrupoDeArchivo> grupos = new LinkedHashMap<>();

		for (Enlace enlace : enlaces) {
			Fragmento fragmento = construirFragmento(enlace);
			if (fragmento == null) {
				continue;
			}

			GrupoDeArchivo grupo = grupos.get(fragmento.nombreBase);
			if (grupo == null) {
				grupo = new GrupoDeArchivo(fragmento.nombreBase);
				grupos.put(fragmento.nombreBase, grupo);
			}
			grupo.fragmentos.add(fragmento);
		}

		return grupos;
	}

	/**
	 * Convierte un enlace en un fragmento reconocible. Si no se puede determinar un
	 * nombre lógico de log, el enlace se descarta.
	 */
	private static Fragmento construirFragmento(Enlace enlace) {
		String fuenteDeNombre = contieneNombreLog(enlace.texto) ? enlace.texto : enlace.url;
		if (!contieneNombreLog(fuenteDeNombre)) {
			return null;
		}

		String nombreBase = extraerNombreLog(fuenteDeNombre);
		TipoFragmento tipo = detectarTipoFragmento(enlace.texto);
		int indiceParte = extraerIndiceParte(enlace.texto);

		return new Fragmento(enlace, nombreBase, tipo, indiceParte);
	}

	/**
	 * Descarga uno o más fragmentos, los combina en orden correcto y crea una
	 * consola con el contenido final.
	 */
	private static Consola descargarYConstruirConsola(GrupoDeArchivo grupo) throws IOException {
		if (grupo == null || grupo.fragmentos.isEmpty()) {
			return null;
		}

		File destino = new File(carpeta, obtenerNombreUnico(grupo.nombreBase));

		/*
		 * Se ordenan los fragmentos para que la reconstrucción sea estable: archivo
		 * completo -> head -> partes numeradas -> tail -> desconocidos.
		 */
		grupo.fragmentos.sort(new Comparator<Fragmento>() {
			@Override
			public int compare(Fragmento a, Fragmento b) {
				int cmpTipo = Integer.compare(pesoTipo(a.tipo), pesoTipo(b.tipo));
				if (cmpTipo != 0) {
					return cmpTipo;
				}
				int cmpParte = Integer.compare(a.indiceParte, b.indiceParte);
				if (cmpParte != 0) {
					return cmpParte;
				}
				return a.enlace.texto.compareToIgnoreCase(b.enlace.texto);
			}
		});

		if (grupo.fragmentos.size() == 1 && grupo.fragmentos.get(0).tipo == TipoFragmento.ARCHIVO_COMPLETO) {
			descargarArchivo(grupo.fragmentos.get(0).enlace.url, destino);
		} else {
			StringBuilder combinado = new StringBuilder();

			for (int i = 0; i < grupo.fragmentos.size(); i++) {
				Fragmento fragmento = grupo.fragmentos.get(i);
				File temporal = new File(carpeta, "__parte_" + Thread.currentThread().getId() + "_" + i + "_"
						+ sanitizarNombreTemporal(grupo.nombreBase));
				descargarArchivo(fragmento.enlace.url, temporal);

				String contenido = MonitorDePID.leer_archivo(temporal.toPath());
				if (contenido != null && !contenido.isEmpty()) {
					if (combinado.length() > 0 && !terminaConSalto(combinado)) {
						combinado.append(System.lineSeparator());
					}
					combinado.append(contenido);
				}

				try {
					temporal.delete();
				} catch (Exception ignorado) {
				}
			}

			Files.write(destino.toPath(), combinado.toString().getBytes(StandardCharsets.UTF_8));
		}

		return new Consola(destino.toPath());
	}

	/**
	 * Asigna prioridad de ensamblado según el tipo de fragmento.
	 */
	private static int pesoTipo(TipoFragmento tipo) {
		switch (tipo) {
		case ARCHIVO_COMPLETO:
			return 0;
		case HEAD:
			return 1;
		case PARTE_NUMERADA:
			return 2;
		case TAIL:
			return 3;
		default:
			return 4;
		}
	}

	/**
	 * Detecta si el enlace representa head, tail o parte numerada. El análisis se
	 * hace sobre el texto visible del Markdown, que es donde normalmente vienen
	 * esas etiquetas en gnomebot/CrashAssistant.
	 */
	private static TipoFragmento detectarTipoFragmento(String texto) {
		String t = normalizarEspacios(texto).toLowerCase();

		if (t.matches(".*\\bhead\\b.*") || t.matches(".*\\bcabeza\\b.*") || t.matches(".*\\binicio\\b.*")) {
			return TipoFragmento.HEAD;
		}
		if (t.matches(".*\\btail\\b.*") || t.matches(".*\\bfinal\\b.*")) {
			return TipoFragmento.TAIL;
		}
		if (t.matches(".*\\bparte\\s*\\d+\\b.*") || t.matches(".*\\bpart\\s*\\d+\\b.*")
				|| t.matches(".*\\b\\d+\\s*/\\s*\\d+\\b.*") || t.matches(".*\\bsegment\\s*\\d+\\b.*")
				|| t.matches(".*\\bchunk\\s*\\d+\\b.*")) {
			return TipoFragmento.PARTE_NUMERADA;
		}
		return TipoFragmento.ARCHIVO_COMPLETO;
	}

	/**
	 * Intenta obtener el número de parte cuando el enlace está numerado.
	 */
	private static int extraerIndiceParte(String texto) {
		if (texto == null) {
			return Integer.MAX_VALUE;
		}

		String t = normalizarEspacios(texto).toLowerCase();

		Matcher mParte = Pattern.compile("\\b(?:parte|part|segment|chunk)\\s*(\\d+)\\b").matcher(t);
		if (mParte.find()) {
			return parseEnteroSeguro(mParte.group(1), Integer.MAX_VALUE);
		}

		Matcher mFraccion = Pattern.compile("\\b(\\d+)\\s*/\\s*(\\d+)\\b").matcher(t);
		if (mFraccion.find()) {
			return parseEnteroSeguro(mFraccion.group(1), Integer.MAX_VALUE);
		}

		return Integer.MAX_VALUE;
	}

	/**
	 * Indica si el texto contiene un patrón de nombre de archivo de log conocido.
	 */
	private static boolean contieneNombreLog(String texto) {
		if (texto == null || texto.isEmpty()) {
			return false;
		}
		for (String p : PATRONES_NOMBRE_LOG) {
			if (texto.matches(".*" + p + ".*")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Extrae un nombre de archivo de log adecuado a partir del texto dado. Si no se
	 * puede, genera un nombre genérico.
	 */
	private static String extraerNombreLog(String texto) {
		for (String p : PATRONES_NOMBRE_LOG) {
			Matcher m = Pattern.compile("(" + p + ")").matcher(texto == null ? "" : texto);
			if (m.find()) {
				String nombre = m.group(1);
				if (nombre.startsWith("crash-")) {
					return nombre;
				}
				nombre = nombre.replaceFirst("(_\\d+)?\\.(log)$", ".log").replaceFirst("(_\\d+)?\\.(txt)$", ".txt");
				return nombre;
			}
		}

		int slash = texto == null ? -1 : texto.lastIndexOf('/');
		if (slash != -1 && slash + 1 < texto.length()) {
			String ultimo = texto.substring(slash + 1);
			if (ultimo.matches(".*\\.(log|txt)")) {
				return ultimo;
			}
		}

		return "registro_generico_" + contadorGenerico.getAndIncrement() + ".log";
	}

	/**
	 * Obtiene un nombre único dentro de la carpeta temporal conservando la
	 * extensión. Se sincroniza para evitar condiciones de carrera.
	 */
	private static synchronized String obtenerNombreUnico(String nombreOriginal) {
		if (!carpeta.exists()) {
			carpeta.mkdirs();
		}

		File f = new File(carpeta, nombreOriginal);
		if (!f.exists()) {
			return nombreOriginal;
		}

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
		if (archivos == null) {
			return;
		}
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
			if (descargados.isEmpty()) {
				throw new IOException("No se pudo descargar: " + url);
			}

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

	/**
	 * Normaliza espacios repetidos para que el parser sea más robusto.
	 */
	private static String normalizarEspacios(String s) {
		return s == null ? "" : s.trim().replaceAll("\\s+", " ");
	}

	/**
	 * Convierte un texto a entero sin lanzar excepción.
	 */
	private static int parseEnteroSeguro(String valor, int valorPorDefecto) {
		try {
			return Integer.parseInt(valor);
		} catch (Exception e) {
			return valorPorDefecto;
		}
	}

	/**
	 * Indica si el StringBuilder ya termina con un salto de línea.
	 */
	private static boolean terminaConSalto(StringBuilder sb) {
		if (sb.length() == 0) {
			return true;
		}
		char ultimo = sb.charAt(sb.length() - 1);
		return ultimo == '\n' || ultimo == '\r';
	}

	/**
	 * Limpia caracteres problemáticos para nombres temporales internos.
	 */
	private static String sanitizarNombreTemporal(String nombre) {
		if (nombre == null || nombre.isEmpty()) {
			return "tmp.log";
		}
		return nombre.replaceAll("[^a-zA-Z0-9._-]", "_");
	}
}