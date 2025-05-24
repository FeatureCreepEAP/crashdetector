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

	public static File generarLocal(List<Consola> consolas, StringBuilder constructor, Instant instant) {
		try {
            StringBuilder cons = new StringBuilder();
            cons.append("<center>") // Center-align all content
                .append(MonitorDePID.idioma.ubicacionesDeLogs()).append("<br>");
            for (Consola co : consolas) {
                cons.append("<a href='file://")
                    .append(co.archivo.toAbsolutePath().toUri().toString())
                    .append("'><font color='").append(Config.obtenerInstancia().obtenerColorEnlace()).append("'>") // Link color
                    .append(co.archivo.toString().trim())
                    .append("</font></a><br>");
            }
            cons.append("</center>");

            

            
            
			String pantilla = MonitorDePID.leer_archivo(new File("crash_detector/pantilla.htm").toPath());
			File ret = new File("crash_detector/" + instant.toString().replace(":", "") + ".htm");
			FileWriter escribidor = new FileWriter(ret);
			escribidor.write(pantilla.replace("{constructor}", cons.toString() + "<br>" +MonitorDePID.idioma.infoDeVerificaciones() + "<br>" + constructor.toString()+imagenesLocales()));
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
            String imagePathPrefix = new File("crash_detector/").getAbsolutePath().replace("\\", "/") + "/";
	        cons.append("<center>")
	            .append("<img src='file://").append(imagePathPrefix).append("gura.png' width='200' height='112'>") // Local Gura image
	            .append("&nbsp;&nbsp;&nbsp;") // Spacing between images
	            .append("<img src='file://").append(imagePathPrefix).append("nanashi_mumei.png' width='200' height='112'>") // Local Nanashi Mumei image
	            .append("&nbsp;&nbsp;&nbsp;")
	            .append("<img src='file://").append(imagePathPrefix).append("shion.png' width='200' height='112'>") // Local Shion image
	            .append("</center>");
	        return cons.toString();
	    }

	public static String compartir(List<Consola> consolas, StringBuilder constructor, Instant instant) throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro {
		try {
		       StringBuilder cons = new StringBuilder();
	            cons.append("<center>")
	                .append(MonitorDePID.idioma.ubicacionesDeLogs()).append("<br>");

	            for (Consola co : consolas) {
	                cons.append("<a href='").append(co.obtainerEnlance())
	                    .append("'><font color='").append(Config.obtenerInstancia().obtenerColorEnlace()).append("'>") // Link color
	                    .append(co.obtainerRutaParaPublicar().trim())
	                    .append("</font></a><br>");
	            }
	            cons.append("</center>");

			String pantilla = MonitorDePID.leer_archivo(new File("crash_detector/pantilla.htm").toPath());
			String ret = enviarInforme(
					pantilla.replace("{constructor}", cons.toString() + "<br>" + MonitorDePID.idioma.infoDeVerificaciones() + "<br>" + constructor.toString()+imagenesParaCompartir()));
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
	            JOptionPane.showMessageDialog(null, 
	                MonitorDePID.idioma.errorSSL());
	        });
	        // Registrar el error completo
	        CrashDetectorLogger.logException(e);
	        throw new IOException("Error SSL: " + e.getMessage(), e);
	    }
	}

}
