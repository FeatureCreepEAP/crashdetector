package com.asbestosstar.crashdetector.api_sito_registro;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;

public class SecureLoggerAPI implements APIdeSitioDeRegistro {

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return "SecureLogger";
	}

	@Override
	public List<String> sitosPorDefecto() {
		// TODO Auto-generated method stub
		List<String> sitios = new ArrayList<String>();
		sitios.add("https://securelogger.net/save/log?");
		sitios.add("https://securelogger.top/save/log?");
		return sitios;
	}

	@Override
	public String publicarRegistro(Consola registro) {
		// TODO Auto-generated method stub
		CrashDetectorLogger.log("enlance null");
		String req = logRequest("USER_CODE", registro);

		return extractLink(req);
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
	public String logRequest(String tipoCliente, Consola consola) {
		try {
			// Construir parámetros de la URL
			String parametros = "version=2.923&clientType=" + URLEncoder.encode(tipoCliente, "UTF-8");
			String urlCompleta = APIdeSitioDeRegistro.sitioDeConfig() + parametros;

			// Leer contenido del archivo de log
			String contenidoLog = consola.obtainerContentoParaPublicar(); // para ahora solo los registros para verificar

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
