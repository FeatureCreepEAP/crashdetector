package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.SSLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro;
import com.asbestosstar.crashdetector.api_sito_registro.DemasiadoGrande;
import com.asbestosstar.crashdetector.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sito_registro.LimteDeTasa;
import com.asbestosstar.crashdetector.api_sito_registro.NoAPIdeRegistro;
import com.asbestosstar.crashdetector.config.ConfigString;

public class GeneradorDeInformacion {

	public static File generarLocal(List<Consola> consolas, Instant instant) {
		try {
			StringBuilder cons = new StringBuilder();
			cons.append("<center>") // Center-align all content
					.append(MonitorDePID.idioma.ubicacionesDeLogs()).append("<br>");
			for (Consola co : consolas) {
				cons.append("<a href='file://").append(co.archivo.toAbsolutePath().toUri().toString())
						.append("'><font color='").append(Config.obtenerInstancia().obtenerColorEnlace()).append("'>") // Link
						.append(co.archivo.toString().trim()).append("</font></a><br>");
			}

			cons.append(generarTextoArcoiris("Feliz mes del orgullo"));

			cons.append("</center>");

			String pantilla = MonitorDePID.leer_archivo(Statics.carpeta.resolve("pantilla.htm"));
			String carp = Statics.carpeta.resolve(".informes").toString() + "/";
			File carp_file = new File(carp);
			carp_file.mkdirs();
			File ret = new File(carp + instant.toString().replace(":", "") + ".htm");
			FileWriter escribidor = new FileWriter(ret);
			escribidor.write(pantilla
					.replace("{constructor}",
							cons.toString() + "<br>" + MonitorDePID.idioma.infoDeVerificaciones() + "<br>"
									+ MonitorDePID.contenidoInforme.toString() + imagenesLocales())
					.replace("{mensaje_ayudar}", MonitorDePID.idioma.mensajeAyudar()));
			escribidor.close();
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String imagenesLocales() {
		StringBuilder cons = new StringBuilder();
		File imagenDir = Statics.carpeta.resolve("imagenes").toFile();
		String guraUrl = new File(imagenDir, "gura.png").toURI().toString();
		String mumeiUrl = new File(imagenDir, "nanashi_mumei.png").toURI().toString();
		String shionUrl = new File(imagenDir, "shion.png").toURI().toString();

		cons.append("<center>").append("<img src='").append(guraUrl).append("' width='200' height='112'>")
				.append("&nbsp;&nbsp;&nbsp;").append("<img src='").append(mumeiUrl)
				.append("' width='200' height='112'>").append("&nbsp;&nbsp;&nbsp;").append("<img src='")
				.append(shionUrl).append("' width='200' height='112'>").append("</center>");
		return cons.toString();
	}

	// contenedor para resultados concurrentes (índice estable)
	private static final class ResultadoEnlaces {
		final int index;
		final String nombreArchivo;
		final List<String> enlaces;

		ResultadoEnlaces(int index, String nombreArchivo, List<String> enlaces) {
			this.index = index;
			this.nombreArchivo = nombreArchivo;
			this.enlaces = enlaces;
		}
	}

	public static String compartir(List<Consola> consolas, Instant instant)
			throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro, LimteDeTasa {
		try {
			final int MAX_HILOS = 6;
			ExecutorService pool = Executors.newFixedThreadPool(Math.min(MAX_HILOS, Math.max(1, consolas.size())));
			List<Future<ResultadoEnlaces>> tareas = new ArrayList<>(consolas.size());

			for (int i = 0; i < consolas.size(); i++) {
				final int idx = i;
				final Consola co = consolas.get(i);
				tareas.add(pool.submit(() -> {
					// Asegura un groupId estable por archivo + instante
					com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro api = com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro
							.obtenerAPIdeConfig();
					String nombre = (co.archivo != null) ? co.archivo.toString().trim() : "log.txt";
					String gid = nombre + ":" + instant.toString();
					api.registrarGrupoActual(gid);

					List<String> enlaces = co.obtainerEnlaces();
					return new ResultadoEnlaces(idx, nombre, enlaces);
				}));
			}

			pool.shutdown();

			ResultadoEnlaces[] resultados = new ResultadoEnlaces[consolas.size()];
			for (Future<ResultadoEnlaces> f : tareas) {
				try {
					ResultadoEnlaces r = f.get();
					resultados[r.index] = r;
				} catch (ExecutionException ex) {
					Throwable causa = ex.getCause();
					if (causa instanceof DemasiadoGrande)
						throw (DemasiadoGrande) causa;
					if (causa instanceof ErrorConPublicar)
						throw (ErrorConPublicar) causa;
					if (causa instanceof NoAPIdeRegistro)
						throw (NoAPIdeRegistro) causa;
					if (causa instanceof LimteDeTasa)
						throw (LimteDeTasa) causa;
					CrashDetectorLogger.logException(causa);
					throw new IOException(causa.getMessage(), causa);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					throw new IOException("Operación interrumpida", ie);
				}
			}

			StringBuilder cons = new StringBuilder();
			cons.append("<center>").append(MonitorDePID.idioma.ubicacionesDeLogs()).append("<br>");

			for (ResultadoEnlaces r : resultados) {
				if (r == null)
					continue;

				List<String> enlaces = r.enlaces;
				String nombre = r.nombreArchivo;

				if (enlaces != null && enlaces.size() == 1) {
					cons.append("<a href='").append(enlaces.get(0)).append("'><font color='")
							.append(Config.obtenerInstancia().obtenerColorEnlace()).append("'>").append(nombre)
							.append("</font></a><br>");
				} else if (enlaces != null && enlaces.size() > 1) {
					cons.append("<span><font color='").append(Config.obtenerInstancia().obtenerColorEnlace())
							.append("'>").append(nombre).append("</font>: ");
					for (int i = 0; i < enlaces.size(); i++) {
						String url = enlaces.get(i);
						cons.append("<a href='").append(url).append("'>").append("(p ").append(i + 1).append(")")
								.append("</a>");
						if (i < enlaces.size() - 1)
							cons.append(" ");
					}
					cons.append("</span><br>");
				} else {
					cons.append("<span><font color='").append(Config.obtenerInstancia().obtenerColorEnlace())
							.append("'>").append(nombre).append("</font></span><br>");
				}
			}

			cons.append(generarTextoArcoiris("Feliz mes del orgullo"));
			cons.append("</center>");

			String plantilla = MonitorDePID.leer_archivo(Statics.carpeta.resolve("pantilla.htm"));

			// ¡No aplanamos “Ver en consola” aquí!
			String constructorHtml = cons.toString() + "<br>" + MonitorDePID.idioma.infoDeVerificaciones() + "<br>"
					+ MonitorDePID.contenidoInforme.toString() + imagenesParaCompartir();

			// Convertir lectador://… a enlaces reales (o eliminarlos si no se soportan)
			constructorHtml = postProcesarLinks(constructorHtml, instant);

			String ret = enviarInforme(
					plantilla.replace("{constructor}", constructorHtml).replace("{mensaje_ayudar}", ""));
			CrashDetectorLogger.log(ret);
			return ret;

		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
			return null;
		}
	}

	private static String postProcesarLinks(String html, Instant instant) {
		try {
			// API seleccionada y soporte de enlaces por línea
			APIdeSitioDeRegistro api = APIdeSitioDeRegistro.obtenerAPIdeConfig();

			// 1) Reemplazar <a href="lectador://ruta:linea">...</a>
			java.util.regex.Pattern pLectador = java.util.regex.Pattern
					.compile("(?i)<a\\b[^>]*href=[\"']lectador://(.*?):(\\d+)[\"'][^>]*>(.*?)</a>");
			java.util.regex.Matcher m = pLectador.matcher(html);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String ruta = m.group(1);
				int linea = Integer.parseInt(m.group(2));
				String inner = m.group(3);

				String gid = ruta.trim() + ":" + instant.toString();
				api.registrarGrupoActual(gid);

				String replacement;
				if (api.soporteEnlacesALinea()) {
					String urlLinea = api.obtenerEnlaceDeLinea(linea);
					if (urlLinea != null && !urlLinea.isEmpty()) {
						replacement = "<a href='" + urlLinea + "'>" + inner + "</a>";
					} else {
						// sin URL concreta — eliminar
						replacement = "";
					}
				} else {
					// no soporta saltos de línea — eliminar
					replacement = "";
				}
				m.appendReplacement(sb, java.util.regex.Matcher.quoteReplacement(replacement));
			}
			m.appendTail(sb);
			html = sb.toString();

			// 2) Si quitamos el enlace, pueden quedar separadores como " - " antes.
			// Elimina " - " o " — " sueltos justo antes de <br>, </span>, </a>, fin de
			// línea, etc.
			html = html.replaceAll("\\s*[-–—]\\s*(?=(</a>|</span>|</div>|<br\\s*/?>|$))", "");

			// 3) Si por alguna razón quedó el texto “Ver en consola” o “View in Log” sin
			// enlace,
			// y NO hay soporte, eliminarlo.
			if (!api.soporteEnlacesALinea()) {
				String etiqueta = MonitorDePID.idioma.verEnConsola();
				if (etiqueta != null && !etiqueta.isEmpty()) {
					String q = java.util.regex.Pattern.quote(etiqueta);
					// borrar con separadores opcionales previos
					html = html.replaceAll("\\s*[-–—]?\\s*" + q, "");
				}
			}

			return html;
		} catch (Throwable t) {
			// En caso de cualquier problema, devolvemos el HTML original
			return html;
		}
	}

	public static String imagenesParaCompartir() {
		StringBuilder cons = new StringBuilder();
		String enlace_a_imagen_gura = ConfigString
				.de("enlace_imagen_gura", "http://asbestosstar.egoism.jp/crash_detector/gura.png").obtener();
		String enlace_a_imagen_mumei = ConfigString
				.de("enlace_imagen_mumei", "http://asbestosstar.egoism.jp/crash_detector/nanashi_mumei.png").obtener();
		String enlace_a_imagen_shion = ConfigString
				.de("enlace_imagen_shion", "http://asbestosstar.egoism.jp/crash_detector/shion.png").obtener();

		cons.append("<center>").append("<img src='").append(enlace_a_imagen_gura).append("' width='200' height='112'>")
				.append("&nbsp;&nbsp;&nbsp;").append("<img src='").append(enlace_a_imagen_mumei)
				.append("' width='200' height='112'>").append("&nbsp;&nbsp;&nbsp;").append("<img src='")
				.append(enlace_a_imagen_shion).append("' width='200' height='112'>").append("</center>");
		return cons.toString();
	}

	public static String enviarInforme(String html) throws IOException {
		try {
			String servidor = Config.obtenerInstancia().obtenerSitoDeInformes();
			String parametros = "html_content=" + java.net.URLEncoder.encode(html, "UTF-8");

			HttpURLConnection conexion = (HttpURLConnection) new URL(servidor).openConnection();
			conexion.setRequestMethod("POST");
			conexion.setDoOutput(true);
			conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			try (OutputStream os = conexion.getOutputStream()) {
				os.write(parametros.getBytes(StandardCharsets.UTF_8));
			}

			int codigoRespuesta = conexion.getResponseCode();
			if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
				try (Scanner scanner = new Scanner(conexion.getInputStream(), StandardCharsets.UTF_8.name())) {
					return scanner.useDelimiter("\\A").next().trim();
				}
			} else {
				String mensajeError = "Error HTTP " + codigoRespuesta + ": ";
				try (Scanner scanner = new Scanner(conexion.getErrorStream())) {
					if (scanner.hasNext()) {
						mensajeError += scanner.useDelimiter("\\A").next();
					}
				}
				throw new IOException(mensajeError);
			}
		} catch (SSLException e) {
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(null, MonitorDePID.idioma.errorSSL());
			});
			CrashDetectorLogger.logException(e);
			throw new IOException("Error SSL: " + e.getMessage(), e);
		}
	}

	private static String generarTextoArcoiris(String texto) {
		String[] coloresArcoiris = { "#FF0000", "#FFA500", "#FFFF00", "#00FF00", "#0000FF", "#4B0082", "#EE82EE" };

		StringBuilder html = new StringBuilder();
		int indiceColor = 0;

		for (char c : texto.toCharArray()) {
			if (c == ' ') {
				html.append(' ');
			} else {
				html.append("<span style='color:").append(coloresArcoiris[indiceColor % coloresArcoiris.length])
						.append("'>").append(c).append("</span>");
				indiceColor++;
			}
		}

		return html.toString();
	}
}
