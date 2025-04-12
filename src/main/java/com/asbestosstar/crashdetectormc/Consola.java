package com.asbestosstar.crashdetectormc;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

import com.asbestosstar.crashdetectormc.analyzador.Analyzador;
import com.asbestosstar.crashdetectormc.analyzador.Verificaciones;

public class Consola {

	public String contento;
	public Path archivo;
	public String enlance;

	public Consola(Path archivo) throws IOException {
		super();
		this.archivo = archivo;
		this.contento = MonitorDePID.leer_archivo(archivo);
	}

	public static List<Consola> obtainerConsolas(Instant tiempo) {
		List<Consola> resulto = new ArrayList<Consola>();

		for (File archivo : obtainerArchivosDeConsolas()) {
			if (archivo.exists()) {

				Instant epoc = Instant.ofEpochMilli(archivo.lastModified());

				if (epoc.isAfter(tiempo)) {
					try {
						resulto.add(new Consola(archivo.toPath()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		return resulto;

	}

	public static List<File> obtainerArchivosDeConsolas() {
		List<File> resulto = new ArrayList<File>();

		File carpetaLogs = new File("logs/");
		File carpetaCrashReports = new File("crash-reports/");

		if (carpetaLogs.exists()) {
			for (File archivo : carpetaLogs.listFiles()) {
				if (!archivo.getAbsolutePath().endsWith(".gz")) {
					resulto.add(archivo);
				}
			}
		}

		if (carpetaCrashReports.exists()) {
			for (File archivo : carpetaCrashReports.listFiles()) {
				if (!archivo.getAbsolutePath().endsWith(".gz")) {
					resulto.add(archivo);
				}
			}
		}
		String home = System.getProperty("user.home");
		String applicationSupport = home + "/Library/Application Support/";
		String appdata = System.getenv("APPDATA");

		File carpetaTLauncherStarter;
		File carpetaTLauncher;
		if (appdata != null) {// Windows
			carpetaTLauncherStarter = new File(appdata + "/.tlauncher/logs/starter/");
			carpetaTLauncher = new File(appdata + "/.tlauncher/logs/tlauncher/");
		} else if (new File(applicationSupport + "/tlauncher/").exists()) {
			carpetaTLauncherStarter = new File(applicationSupport + "/tlauncher/logs/starter/");
			carpetaTLauncher = new File(applicationSupport + "/tlauncher/logs/tlauncher/");
		} else {
			carpetaTLauncherStarter = new File(home + "/.tlauncher/logs/starter/");
			carpetaTLauncher = new File(home + "/.tlauncher/logs/tlauncher/");
		}

		if (carpetaTLauncher.exists()) {
			for (File archivo : carpetaTLauncher.listFiles()) {
				if (!archivo.getAbsolutePath().endsWith(".gz")) {
					resulto.add(archivo);
				}
			}
		}
		if (carpetaTLauncherStarter.exists()) {
			for (File archivo : carpetaTLauncherStarter.listFiles()) {
				if (!archivo.getAbsolutePath().endsWith(".gz")) {
					resulto.add(archivo);
				}
			}
		}

		if (appdata != null) {
			resulto.add(new File(appdata + "/AtLauncher/logs/atlauncher.log"));// ATLauncher DOS
			resulto.add(new File(appdata + "/.minecraft/launcher_log.txt"));// CurseForgeApp
		}

		resulto.add(new File("launcher_log.txt"));
		resulto.add(new File("../../Install/launcher_log.txt"));// CurseForgeApp
		resulto.add(new File(applicationSupport + "minecraft/launcher_log.txt"));// CurseForgeApp
		resulto.add(new File(home + ".minecraft/launcher_log.txt"));// CurseForgeApp

		resulto.add(new File("../../logs/ftb-app-electron.log"));// FTB
		resulto.add(new File("../../logs/atlauncher.log"));// ATLauncher UNIX

		resulto.add(new File("../../../logs/PrismLauncher-0.log"));
		resulto.add(new File("../../../logs/PollyMC-0.log"));
		resulto.add(new File("../../../PolyMC-0.log"));
		resulto.add(new File("../../../UltimMC-0.log"));
		resulto.add(new File("../../../MultiMC-0.log"));
//TODO LauncherFenix

		resulto.add(new File("sklauncher/sklauncher_logs.txt"));

		resulto.add(new File("../../../../main.log"));// GDLauncher

		return resulto;

	}

	public void analyzar(StringBuilder constructor) {
		// StringBuilder temporal para almacenar los resultados de las verificaciones
		StringBuilder contenidoVerificaciones = new StringBuilder();

		// Iterar a través de todas las verificaciones y recopilar su salida
		for (Verificaciones verificacion : Analyzador.verificaciones) {
			verificacion.nueva().verificar(contento, contenidoVerificaciones);
		}

		// Verificar si hay algún contenido generado por las verificaciones
		if (contenidoVerificaciones.length() > 0) {
			// Crear un desplegable con el nombre del archivo como etiqueta
			constructor.append("<details><summary>").append("<br>").append("<b>").append(archivo.getFileName()) // Nombre
																												// del
																												// archivo
					.append("</b></summary>").append(contenidoVerificaciones.toString()) // Contenido de las
																							// verificaciones
					.append("</details><br>");
		}
	}

	// Para todos el code aqui,escribir otra vez estar mas simplicado pero hacer la
	// funcion misma
	public String obtainerEnlance() {
		if (enlance != null) {
			CrashDetectorLogger.log("elance no es null");

			return enlance;
		} else {
			CrashDetectorLogger.log("enlance null");
			String req = logRequest("USER_CODE");
			this.enlance = extractLink(req);
			return enlance;
		}
	}

	/**
	 * Extracts the "link" value from a JSON string using plain Java.
	 *
	 * @param jsonString The JSON string containing the "link" field.
	 * @return The value of the "link" field, or null if not found.
	 */
	public static String extractLink(String jsonString) {
		// Define the key to search for
		String key = "\"link\"";

		// Find the index of the key in the JSON string
		int keyIndex = jsonString.indexOf(key);
		if (keyIndex == -1) {
			// Key not found
			return null;
		}

		// Find the start of the value (after the colon and optional whitespace)
		int valueStart = jsonString.indexOf(':', keyIndex) + 1;
		if (valueStart == 0) {
			// Invalid JSON format
			return null;
		}

		// Trim leading whitespace
		while (valueStart < jsonString.length() && Character.isWhitespace(jsonString.charAt(valueStart))) {
			valueStart++;
		}

		// Check if the value is enclosed in quotes
		if (jsonString.charAt(valueStart) != '"') {
			// Value is not a string (unsupported format)
			return null;
		}

		// Find the end of the value (closing quote)
		int valueEnd = jsonString.indexOf('"', valueStart + 1);
		if (valueEnd == -1) {
			// Missing closing quote
			return null;
		}

		// Extract and return the link
		return jsonString.substring(valueStart + 1, valueEnd);
	}

	// Simplificación de métodos relacionados con la solicitud HTTP
	public String logRequest(String tipoCliente) {
		try {
			// Construir parámetros de la URL
			String parametros = "version=2.923&clientType=" + URLEncoder.encode(tipoCliente, "UTF-8");
			String urlCompleta = "https://securelogger.net/save/log?" + parametros;

			// Leer contenido del archivo de log
			String contenidoLog = contento;

			// Realizar solicitud POST comprimida
			return enviarPost(new URL(urlCompleta), contenidoLog.getBytes("cp1251"));
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
			return "Error: " + e.getMessage();
		}
	}

	private String enviarPost(URL url, byte[] cuerpo) throws IOException {
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
		conexion.setRequestMethod("POST");
		conexion.setConnectTimeout(30000);
		conexion.setReadTimeout(30000);
		conexion.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
		conexion.setRequestProperty("Content-Encoding", "gzip");
		conexion.setDoOutput(true);

		try (OutputStream os = conexion.getOutputStream()) {
			os.write(comprimirGZIP(cuerpo));
		}

		try (InputStream is = conexion.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			return reader.lines().collect(Collectors.joining("\n"));
		}
	}

	private byte[] comprimirGZIP(byte[] datos) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try (GZIPOutputStream gzip = new GZIPOutputStream(buffer)) {
			gzip.write(datos);
		}
		return buffer.toByteArray();
	}

}
