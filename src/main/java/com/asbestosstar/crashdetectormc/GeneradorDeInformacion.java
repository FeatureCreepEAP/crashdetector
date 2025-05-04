package com.asbestosstar.crashdetectormc;

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

import com.asbestosstar.crashdetectormc.api_sito_registro.DemasiadoGrande;
import com.asbestosstar.crashdetectormc.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetectormc.api_sito_registro.NoAPIdeRegistro;

public class GeneradorDeInformacion {

	public static File generarLocal(List<Consola> consolas, StringBuilder constructor, Instant instant) {
		try {
			StringBuilder cons = new StringBuilder();
			cons.append(MonitorDePID.idioma.ubicacionesDeLogs()+"<br>");
			for (Consola co : consolas) {
					cons.append("<a href='file://").append(co.archivo.toUri().toString()).append("'>")
							.append(co.archivo.toString().strip()).append("</a><br>");
			}

			String pantilla = MonitorDePID.leer_archivo(new File("crash_detector/pantilla.htm").toPath());
			File ret = new File("crash_detector/" + instant.toString().replace(":", "") + ".htm");
			FileWriter escribidor = new FileWriter(ret);
			escribidor.write(pantilla.replace("{constructor}", cons.toString() + "<br>" +MonitorDePID.idioma.infoDeVerificaciones() + "<br>" + constructor.toString()));
			escribidor.close();
			return ret;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String compartir(List<Consola> consolas, StringBuilder constructor, Instant instant) throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro {
		try {
			StringBuilder cons = new StringBuilder();
			cons.append(MonitorDePID.idioma.ubicacionesDeLogs()+"<br>");
			for (Consola co : consolas) {
					cons.append("<a href=" + co.obtainerEnlance() + ">" + co.archivo.toString().strip() + "</a>")
							.append("<br>");
			}

			String pantilla = MonitorDePID.leer_archivo(new File("crash_detector/pantilla.htm").toPath());
			String ret = enviarInforme(
					pantilla.replace("{constructor}", cons.toString() + "<br>" + MonitorDePID.idioma.infoDeVerificaciones() + "<br>" + constructor.toString()));
			CrashDetectorLogger.log(ret);
			return ret;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			CrashDetectorLogger.logException(e);
			return null;
		}
	}

	public static String enviarInforme(String html) throws IOException {
		String servidor = Config.obtenerInstancia().obtenerSitoDeInformes();
		String parametros = "html_content=" + java.net.URLEncoder.encode(html, StandardCharsets.UTF_8);

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
	}

}
