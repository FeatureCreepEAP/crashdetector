package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.SSLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.api_sito_registro.DemasiadoGrande;
import com.asbestosstar.crashdetector.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sito_registro.NoAPIdeRegistro;

public class GeneradorDeInformacion {

	public static File generarLocal(List<Consola> consolas, Instant instant) {
		try {
			StringBuilder cons = new StringBuilder();
			cons.append("<center>") // Center-align all content
					.append(MonitorDePID.idioma.ubicacionesDeLogs()).append("<br>");
			for (Consola co : consolas) {
				cons.append("<a href='file://").append(co.archivo.toAbsolutePath().toUri().toString())
						.append("'><font color='").append(Config.obtenerInstancia().obtenerColorEnlace()).append("'>") // Link
																														// color
						.append(co.archivo.toString().trim()).append("</font></a><br>");
			}

			cons.append(generarTextoArcoiris("Feliz mes del orgullo"));

			cons.append("</center>");

			String pantilla = MonitorDePID.leer_archivo(MonitorDePID.carpeta.resolve("pantilla.htm"));
			String carp = MonitorDePID.carpeta.resolve(".informes").toString() + "/";
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String imagenesLocales() {
		StringBuilder cons = new StringBuilder();
		File imagenDir = MonitorDePID.carpeta.resolve("imagenes").toFile();
		String guraUrl = new File(imagenDir, "gura.png").toURI().toString();
		String mumeiUrl = new File(imagenDir, "nanashi_mumei.png").toURI().toString();
		String shionUrl = new File(imagenDir, "shion.png").toURI().toString();

		cons.append("<center>").append("<img src='").append(guraUrl).append("' width='200' height='112'>")
				.append("&nbsp;&nbsp;&nbsp;").append("<img src='").append(mumeiUrl)
				.append("' width='200' height='112'>").append("&nbsp;&nbsp;&nbsp;").append("<img src='")
				.append(shionUrl).append("' width='200' height='112'>").append("</center>");
		return cons.toString();
	}

	public static String compartir(List<Consola> consolas, Instant instant)
			throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro {
		try {
			StringBuilder cons = new StringBuilder();
			cons.append("<center>").append(MonitorDePID.idioma.ubicacionesDeLogs()).append("<br>");

			for (Consola co : consolas) {
				cons.append("<a href='").append(co.obtainerEnlance()).append("'><font color='")
						.append(Config.obtenerInstancia().obtenerColorEnlace()).append("'>") // Link color
						.append(co.obtenerRutaParaPublicar().trim()).append("</font></a><br>");
			}
			cons.append(generarTextoArcoiris("Feliz mes del orgullo"));
			cons.append("</center>");

			String pantilla = MonitorDePID.leer_archivo(MonitorDePID.carpeta.resolve("pantilla.htm"));
			String ret = enviarInforme(pantilla.replace("{constructor}",
					cons.toString() + "<br>" + MonitorDePID.idioma.infoDeVerificaciones() + "<br>"
							+ MonitorDePID.contenidoInforme.toString() + imagenesParaCompartir()).replace("{mensaje_ayudar}", MonitorDePID.idioma.mensajeAyudar()));
			CrashDetectorLogger.log(ret);
			return ret;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			CrashDetectorLogger.logException(e);
			return null;
		}
	}

	public static String imagenesParaCompartir() {
		StringBuilder cons = new StringBuilder();
//		cons.append("<center>")
//        .append("<img src='/../gura.png' width='200' height='112'>") // Imagen Gura
//        .append("&nbsp;&nbsp;&nbsp;") // Espaciado entre imágenes
//        .append("<img src='/../nanashi_mumei.png' width='200' height='112'>") // Imagen Nanashi Mumei
//        .append("&nbsp;&nbsp;&nbsp;")
//        .append("<img src='/../shion.png' width='200' height='112'>") // Imagen Shion
//        .append("</center>");
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
			// Mostrar diálogo de error SSL en el EDT
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(null, MonitorDePID.idioma.errorSSL());
			});
			// Registrar el error completo
			CrashDetectorLogger.logException(e);
			throw new IOException("Error SSL: " + e.getMessage(), e);
		}
	}

	/**
	 * Genera una cadena HTML con cada letra coloreada en arcoíris.
	 *
	 * @param texto El texto de entrada a colorear
	 * @return Cadena HTML con letras de colores
	 */
	private static String generarTextoArcoiris(String texto) {
		// Colores del arcoíris (formato hexadecimal)
		String[] coloresArcoiris = { "#FF0000", // Rojo
				"#FFA500", // Naranja
				"#FFFF00", // Amarillo
				"#00FF00", // Verde
				"#0000FF", // Azul
				"#4B0082", // Índigo
				"#EE82EE" // Violeta
		};

		StringBuilder html = new StringBuilder();
		int indiceColor = 0;

		// Iterar por cada carácter del mensaje
		for (char c : texto.toCharArray()) {
			if (c == ' ') {
				html.append(' '); // Mantener espacios sin color
			} else {
				// Añadir span con color correspondiente
				html.append("<span style='color:").append(coloresArcoiris[indiceColor % coloresArcoiris.length])
						.append("'>").append(c).append("</span>");
				indiceColor++; // Siguiente color
			}
		}

		return html.toString();
	}

}
