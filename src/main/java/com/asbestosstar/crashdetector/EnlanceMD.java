package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

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

	private static final Pattern PATRON_URL_DESNUDA = Pattern.compile("https?://[^\\s<>\"']+");
	private static final Pattern PATRON_ANCLA_HTML = Pattern
			.compile("(?is)<a\\b([^>]*)href\\s*=\\s*(['\"])(.*?)\\2([^>]*)>(.*?)</a>");
	private static final int LIMITE_HTML_INFORME = 8 * 1024 * 1024;

	// INFORME_UN_SOLO_NIVEL_V3
	private static final int REDIRECCIONES_MAX_INFORME = 8;

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
		String texto = textoDeMensaje == null ? "" : textoDeMensaje;
		List<Enlace> enlaces = new ArrayList<>();
		Set<String> urlsVistas = new LinkedHashSet<>();

		Matcher m = PATRON_ENLACE_MD.matcher(texto);
		while (m.find()) {
			String url = limpiarURLExtraida(m.group(2));
			if (!url.isEmpty() && urlsVistas.add(url)) {
				enlaces.add(new Enlace(m.group(1), url));
			}
		}

		Matcher desnuda = PATRON_URL_DESNUDA.matcher(texto);
		while (desnuda.find()) {
			String url = limpiarURLExtraida(desnuda.group());
			if (!url.isEmpty() && urlsVistas.add(url)) {
				enlaces.add(new Enlace(url, url));
			}
		}

		return enlaces;
	}

	/**
	 * Importa un informe de Crash Assistant o CrashDetector. Puede recibir una URL,
	 * varias URLs o HTML ya pegado. El informe no se analiza como log: sólo se
	 * extraen enlaces que representen registros compatibles.
	 */
	public static void guardarInforme(String textoOUrl) {
		String entrada = textoOUrl == null ? "" : textoOUrl.trim();
		if (entrada.isEmpty()) {
			return;
		}

		StringBuilder markdown = new StringBuilder();
		Set<String> logsEncontrados = new LinkedHashSet<>();

		/*
		 * HTML pegado directamente: analizar únicamente ese documento. No volver a
		 * tratar todas las URLs encontradas dentro como entradas iniciales.
		 */
		if (pareceHtmlInforme(entrada)) {
			procesarDocumentoInformeUnNivel(entrada, detectarBaseParaHtmlPegado(entrada), markdown, logsEncontrados);
		} else {
			/*
			 * Cada URL escrita por el usuario se inspecciona una sola vez.
			 */
			for (String urlInicial : extraerUrlsTexto(entrada)) {
				procesarUrlInicialInforme(urlInicial, markdown, logsEncontrados);
			}
		}

		if (markdown.length() > 0) {
			guardar(markdown.toString());
		} else {
			CrashDetectorLogger.log("Informe: no se encontraron enlaces directos de log compatibles.");
		}
	}

	private static final class PaginaInformeDescargada {
		final String urlFinal;
		final String contenido;
		final String contentType;

		PaginaInformeDescargada(String urlFinal, String contenido, String contentType) {
			this.urlFinal = urlFinal == null ? "" : urlFinal;
			this.contenido = contenido == null ? "" : contenido;
			this.contentType = contentType == null ? "" : contentType;
		}
	}

	private static void procesarUrlInicialInforme(String urlInicial, StringBuilder markdown,
			Set<String> logsEncontrados) {

		String url = normalizarURLHttp(urlInicial);
		if (url.isEmpty()) {
			return;
		}

		if (!DescargarRegistors.esURLInformeColeccion(url) && esLogDirectoInforme(url, "")) {
			registrarLogInforme(markdown, logsEncontrados, nombreSugeridoParaUrl(url), url);
			return;
		}

		try {
			PaginaInformeDescargada pagina = descargarPaginaInformeUnaVez(url);
			if (pagina == null) {
				return;
			}

			if (!DescargarRegistors.esURLInformeColeccion(pagina.urlFinal)
					&& esLogDirectoInforme(pagina.urlFinal, "")) {
				registrarLogInforme(markdown, logsEncontrados, nombreSugeridoParaUrl(pagina.urlFinal), pagina.urlFinal);
				return;
			}

			if (!esContenidoInspeccionableInforme(pagina.contentType, pagina.contenido)) {
				return;
			}

			procesarDocumentoInformeUnNivel(pagina.contenido, pagina.urlFinal, markdown, logsEncontrados);
		} catch (Throwable e) {
			CrashDetectorLogger.log("Informe: no se pudo inspeccionar " + url + " - " + e.getMessage());
		}
	}

	/**
	 * Examina únicamente los enlaces directos de este documento. Nunca descarga los
	 * enlaces encontrados como páginas adicionales.
	 */
	private static void procesarDocumentoInformeUnNivel(String contenido, String baseUrl, StringBuilder markdown,
			Set<String> logsEncontrados) {

		if (contenido == null || contenido.isEmpty()) {
			return;
		}

		Set<String> urlsVistas = new LinkedHashSet<>();

		Matcher ancla = PATRON_ANCLA_HTML.matcher(contenido);
		while (ancla.find()) {
			String atributos = (ancla.group(1) == null ? "" : ancla.group(1)) + " "
					+ (ancla.group(4) == null ? "" : ancla.group(4));

			String href = extraerHrefHttpInforme(ancla.group(3));
			if (href.isEmpty()) {
				continue;
			}

			String url = resolverUrl(baseUrl, href);
			if (url.isEmpty() || !urlsVistas.add(url)) {
				continue;
			}

			if (DescargarRegistors.esURLInformeColeccion(url)) {
				continue;
			}

			String nombre = extraerAtributoHtml(atributos, "data-file-name");
			if (nombre.isEmpty()) {
				nombre = limpiarTextoHtml(ancla.group(5));
			}

			if (!esLogDirectoInforme(url, nombre)) {
				continue;
			}

			if (nombre.isEmpty() || !contieneNombreLog(nombre)) {
				nombre = nombreSugeridoParaUrl(url);
			}

			registrarLogInforme(markdown, logsEncontrados, nombre, url);
		}

		/*
		 * URLs desnudas sí se reconocen, pero sólo si ellas mismas ya son logs. No se
		 * descargan ni se siguen.
		 */
		Matcher desnuda = PATRON_URL_DESNUDA.matcher(contenido);
		while (desnuda.find()) {
			String url = limpiarURLExtraida(desnuda.group());

			if (url.isEmpty() || !urlsVistas.add(url) || DescargarRegistors.esURLInformeColeccion(url)
					|| !esLogDirectoInforme(url, "")) {
				continue;
			}

			registrarLogInforme(markdown, logsEncontrados, nombreSugeridoParaUrl(url), url);
		}
	}

	private static boolean esLogDirectoInforme(String url, String nombreVisible) {
		if (url == null || url.isEmpty() || DescargarRegistors.esURLInformeColeccion(url)) {
			return false;
		}

		if (DescargarRegistors.esURLSoportada(url) || DescargarRegistors.esURLSecureLoggerArchivo(url)
				|| DescargarRegistors.pareceRegistro(url)) {
			return true;
		}

		return nombreVisible != null && contieneNombreLog(nombreVisible);
	}

	/**
	 * Normaliza href normales y copias como: [https://host/log](https://host/log)
	 */
	private static String extraerHrefHttpInforme(String href) {
		if (href == null) {
			return "";
		}

		String h = decodificarHtmlBasico(href.trim());

		if (h.isEmpty() || h.startsWith("#") || h.startsWith("javascript:") || h.startsWith("mailto:")
				|| h.contains("view-source:")) {
			return "";
		}

		Matcher markdown = Pattern.compile("\\]\\((https?://[^)\\s]+)\\)").matcher(h);
		if (markdown.find()) {
			return limpiarURLExtraida(markdown.group(1));
		}

		if (h.startsWith("http://") || h.startsWith("https://") || h.startsWith("/") || h.startsWith("./")
				|| h.startsWith("../")) {
			return h;
		}

		Matcher url = PATRON_URL_DESNUDA.matcher(h);
		if (url.find()) {
			return limpiarURLExtraida(url.group());
		}

		return "";
	}

	private static boolean pareceHtmlInforme(String texto) {
		if (texto == null) {
			return false;
		}
		String t = texto.toLowerCase(java.util.Locale.ROOT);
		return t.contains("<html") || t.contains("<!doctype html") || t.contains("<a ") || t.contains("href=");
	}

	private static List<String> extraerUrlsTexto(String texto) {
		List<String> urls = new ArrayList<>();
		Set<String> vistas = new LinkedHashSet<>();
		Matcher m = PATRON_URL_DESNUDA.matcher(texto == null ? "" : texto);

		while (m.find()) {
			String url = limpiarURLExtraida(m.group());
			if (!url.isEmpty() && vistas.add(url)) {
				urls.add(url);
			}
		}

		return urls;
	}

	private static String limpiarURLExtraida(String url) {
		if (url == null) {
			return "";
		}

		String u = decodificarHtmlBasico(url.trim());

		while (!u.isEmpty()) {
			char ultimo = u.charAt(u.length() - 1);
			if (ultimo == '.' || ultimo == ',' || ultimo == ';' || ultimo == ')' || ultimo == ']' || ultimo == '}'
					|| ultimo == '"' || ultimo == '\'') {
				u = u.substring(0, u.length() - 1);
			} else {
				break;
			}
		}

		return normalizarURLHttp(u);
	}

	private static String normalizarURLHttp(String url) {
		if (url == null) {
			return "";
		}

		String u = url.trim();
		if (!(u.startsWith("http://") || u.startsWith("https://"))) {
			return "";
		}

		try {
			URL parsed = new URL(u);
			String protocolo = parsed.getProtocol();
			if (!"http".equalsIgnoreCase(protocolo) && !"https".equalsIgnoreCase(protocolo)) {
				return "";
			}
			return parsed.toString();
		} catch (Exception e) {
			return "";
		}
	}

	private static boolean esContenidoInspeccionableInforme(String contentType, String contenido) {

		String tipo = contentType == null ? "" : contentType.toLowerCase(java.util.Locale.ROOT);

		if (tipo.contains("text/") || tipo.contains("html") || tipo.contains("json") || tipo.contains("xml")
				|| tipo.isEmpty()) {
			return true;
		}

		return pareceHtmlInforme(contenido);
	}

	private static void registrarLogInforme(StringBuilder markdown, Set<String> logsEncontrados, String nombre,
			String url) {

		String normalizada = normalizarURLHttp(url);
		if (normalizada.isEmpty() || !logsEncontrados.add(normalizada)) {
			return;
		}

		agregarEnlaceInformeMarkdown(markdown, nombre, normalizada);
	}

	private static String detectarBaseParaHtmlPegado(String html) {
		if (html == null) {
			return "";
		}

		String minus = html.toLowerCase(java.util.Locale.ROOT);

		if (minus.contains("crash assistant paste") || minus.contains("id=\"paste-array\"")
				|| minus.contains("class=\"content paste-array\"")) {
			return "https://paste.kostromdan.dev/";
		}

		Matcher base = Pattern.compile("(?is)<base\\b[^>]*href\\s*=\\s*(['\"])(https?://.*?)\\1").matcher(html);

		if (base.find()) {
			return base.group(2);
		}

		return "";
	}

	private static PaginaInformeDescargada descargarPaginaInformeUnaVez(String url) throws IOException {

		String actual = normalizarURLHttp(url);
		if (actual.isEmpty()) {
			throw new IOException("URL HTTP inválida");
		}

		for (int redireccion = 0; redireccion <= REDIRECCIONES_MAX_INFORME; redireccion++) {

			HttpURLConnection conexion = null;

			try {
				conexion = (HttpURLConnection) new URL(actual).openConnection();
				conexion.setInstanceFollowRedirects(false);
				conexion.setRequestMethod("GET");
				conexion.setConnectTimeout(10000);
				conexion.setReadTimeout(20000);
				conexion.setRequestProperty("User-Agent", "CrashDetector-Informe/3.0");
				conexion.setRequestProperty("Accept",
						"text/html,text/plain,application/xhtml+xml," + "application/json,application/xml,*/*;q=0.1");

				int codigo = conexion.getResponseCode();

				if (codigo == 301 || codigo == 302 || codigo == 303 || codigo == 307 || codigo == 308) {

					String location = conexion.getHeaderField("Location");
					String siguiente = resolverUrl(actual, location);

					if (siguiente.isEmpty()) {
						throw new IOException("Redirección HTTP sin Location válido");
					}

					actual = siguiente;
					continue;
				}

				if (codigo < 200 || codigo >= 300) {
					throw new IOException("HTTP " + codigo);
				}

				String contentType = conexion.getContentType();
				String encoding = conexion.getContentEncoding();
				InputStream raw = conexion.getInputStream();
				InputStream entrada = raw;

				if (encoding != null && encoding.toLowerCase(java.util.Locale.ROOT).contains("gzip")) {
					entrada = new GZIPInputStream(raw);
				}

				StringBuilder contenido = new StringBuilder();

				try (java.io.BufferedReader reader = new java.io.BufferedReader(
						new java.io.InputStreamReader(entrada, StandardCharsets.UTF_8))) {

					char[] buffer = new char[8192];
					int leidos;

					while ((leidos = reader.read(buffer)) != -1) {
						int restantes = LIMITE_HTML_INFORME - contenido.length();

						if (restantes <= 0) {
							break;
						}

						int copiar = Math.min(restantes, leidos);
						contenido.append(buffer, 0, copiar);

						if (copiar < leidos) {
							break;
						}
					}
				}

				return new PaginaInformeDescargada(actual, contenido.toString(), contentType);
			} finally {
				if (conexion != null) {
					conexion.disconnect();
				}
			}
		}

		throw new IOException("Demasiadas redirecciones HTTP");
	}

	private static void agregarEnlaceInformeMarkdown(StringBuilder markdown, String nombre, String url) {
		if (nombre == null || nombre.trim().isEmpty()) {
			nombre = nombreSugeridoParaUrl(url);
		}
		markdown.append('[').append(nombre.replace("[", "").replace("]", "")).append("](").append(url).append(')')
				.append('\n');
	}

	private static String nombreSugeridoParaUrl(String url) {
		String id = DescargarRegistors.extraerIDRegistro(url);
		if (id != null && !id.trim().isEmpty()) {
			return "registro_" + id.trim() + ".log";
		}
		return "registro_generico_" + contadorGenerico.getAndIncrement() + ".log";
	}

	private static String resolverUrl(String baseUrl, String href) {
		if (href == null) {
			return "";
		}
		String h = href.trim();
		if (h.isEmpty() || h.startsWith("#") || h.startsWith("javascript:") || h.startsWith("mailto:")) {
			return "";
		}
		try {
			if (baseUrl != null && !baseUrl.trim().isEmpty()) {
				return new URL(new URL(baseUrl), h).toString();
			}
			return new URL(h).toString();
		} catch (Exception e) {
			return "";
		}
	}

	private static String extraerAtributoHtml(String atributos, String nombre) {
		if (atributos == null || atributos.isEmpty()) {
			return "";
		}
		Pattern p = Pattern.compile("(?is)" + Pattern.quote(nombre) + "\\s*=\\s*(['\"])(.*?)\\1");
		Matcher m = p.matcher(atributos);
		return m.find() ? decodificarHtmlBasico(m.group(2)).trim() : "";
	}

	private static String limpiarTextoHtml(String html) {
		if (html == null) {
			return "";
		}
		String sinTags = html.replaceAll("(?is)<[^>]+>", " ");
		return normalizarEspacios(decodificarHtmlBasico(sinTags));
	}

	private static String decodificarHtmlBasico(String s) {
		if (s == null) {
			return "";
		}
		return s.replace("&amp;", "&").replace("&quot;", "\"").replace("&#39;", "'").replace("&lt;", "<")
				.replace("&gt;", ">");
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
		if (enlace == null || enlace.url == null || DescargarRegistors.esURLInformeColeccion(enlace.url)) {
			return null;
		}

		String fuenteDeNombre = contieneNombreLog(enlace.texto) ? enlace.texto : enlace.url;
		String nombreBase;

		if (contieneNombreLog(fuenteDeNombre)) {
			nombreBase = extraerNombreLog(fuenteDeNombre);
		} else if (DescargarRegistors.esURLSoportada(enlace.url)
				|| DescargarRegistors.esURLSecureLoggerArchivo(enlace.url)) {
			nombreBase = nombreSugeridoParaUrl(enlace.url);
		} else {
			return null;
		}

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
		/*
		 * Toda la normalización (mclo.gs, GnomeBot, Kostromdan, PastesDev, Bytebin, CD
		 * Paste, MMD, KDAN y SecureLogger) vive en un solo sitio.
		 */
		DescargarRegistors.descargarArchivo(url, destino);
	}

	/** Extrae el primer archivo regular de un .tar.gz de SecureLogger. */
	private static void descargarSecureLoggerTarGz(String url, File destino) throws IOException {
		HttpURLConnection conexion = null;
		try {
			conexion = (HttpURLConnection) new URL(url).openConnection();
			conexion.setRequestMethod("GET");
			conexion.setConnectTimeout(10000);
			conexion.setReadTimeout(30000);
			conexion.setRequestProperty("User-Agent", "CrashDetector-EnlanceMD/1.0");
			int codigo = conexion.getResponseCode();
			if (codigo < 200 || codigo >= 300) {
				throw new IOException("HTTP " + codigo);
			}
			try (InputStream raw = conexion.getInputStream(); GZIPInputStream gzip = new GZIPInputStream(raw)) {
				extraerPrimerArchivoTar(gzip, destino);
			}
		} finally {
			if (conexion != null) {
				conexion.disconnect();
			}
		}
	}

	private static void extraerPrimerArchivoTar(InputStream tar, File destino) throws IOException {
		byte[] cabecera = new byte[512];
		while (true) {
			int leidos = leerExactamenteHastaEOF(tar, cabecera, 0, cabecera.length);
			if (leidos == 0) {
				throw new IOException("TAR vacío");
			}
			if (leidos != cabecera.length) {
				throw new IOException("Cabecera TAR incompleta");
			}
			if (bloqueTarCero(cabecera)) {
				throw new IOException("TAR sin archivo regular");
			}

			long tamano = leerOctalTar(cabecera, 124, 12);
			int tipo = cabecera[156] & 0xff;
			boolean regular = tipo == 0 || tipo == '0';

			if (regular) {
				try (OutputStream out = Files.newOutputStream(destino.toPath())) {
					copiarExactamente(tar, out, tamano);
				}
				saltarExactamente(tar, paddingTar(tamano));
				return;
			}

			saltarExactamente(tar, tamano);
			saltarExactamente(tar, paddingTar(tamano));
		}
	}

	private static int leerExactamenteHastaEOF(InputStream in, byte[] buffer, int off, int len) throws IOException {
		int total = 0;
		while (total < len) {
			int n = in.read(buffer, off + total, len - total);
			if (n < 0) {
				break;
			}
			if (n > 0) {
				total += n;
			}
		}
		return total;
	}

	private static void copiarExactamente(InputStream in, OutputStream out, long cantidad) throws IOException {
		byte[] buffer = new byte[8192];
		long restante = cantidad;
		while (restante > 0) {
			int pedir = (int) Math.min(buffer.length, restante);
			int n = in.read(buffer, 0, pedir);
			if (n < 0) {
				throw new IOException("TAR truncado");
			}
			if (n > 0) {
				out.write(buffer, 0, n);
				restante -= n;
			}
		}
	}

	private static void saltarExactamente(InputStream in, long cantidad) throws IOException {
		long restante = cantidad;
		while (restante > 0) {
			long n = in.skip(restante);
			if (n > 0) {
				restante -= n;
				continue;
			}
			if (in.read() < 0) {
				throw new IOException("TAR truncado");
			}
			restante--;
		}
	}

	private static long paddingTar(long tamano) {
		long resto = tamano % 512L;
		return resto == 0L ? 0L : 512L - resto;
	}

	private static boolean bloqueTarCero(byte[] bloque) {
		for (byte b : bloque) {
			if (b != 0) {
				return false;
			}
		}
		return true;
	}

	private static long leerOctalTar(byte[] bloque, int inicio, int largo) throws IOException {
		long valor = 0L;
		boolean vioDigito = false;
		for (int i = inicio; i < inicio + largo && i < bloque.length; i++) {
			int c = bloque[i] & 0xff;
			if (c == 0 || c == ' ' || c == '\t') {
				if (vioDigito) {
					break;
				}
				continue;
			}
			if (c < '0' || c > '7') {
				throw new IOException("Tamaño TAR inválido");
			}
			vioDigito = true;
			valor = (valor << 3) + (c - '0');
		}
		return valor;
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
