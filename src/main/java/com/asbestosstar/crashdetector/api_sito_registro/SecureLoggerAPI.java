package com.asbestosstar.crashdetector.api_sito_registro;

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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

import com.asbestosstar.crashdetector.BiMap;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro.ParteInfo;

public class SecureLoggerAPI implements APIdeSitioDeRegistro {

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return "SecureLogger";
	}

	@Override
	public List<String> sitiosPorDefecto() {
		// TODO Auto-generated method stub
		List<String> sitios = new ArrayList<String>();
		sitios.add("https://securelogger.net/save/log?");
		sitios.add("https://securelogger.top/save/log?");
		return sitios;
	}

	@Override
	public String publicarRegistro(Consola registro) throws ErrorConPublicar {
		// TODO Auto-generated method stub
		CrashDetectorLogger.log("enlance null");
		try {
			String req = logRequest("USER_CODE", registro.obtainerContenidoParaPublicar());
			return extractLink(req);
		} catch (LimteDeTasa lt) {
			// Mantener el flujo existente: convertir a ErrorConPublicar con mensaje humano
			// para que la GUI muestre un popup ya con el texto correcto.
			throw new ErrorConPublicar(MonitorDePID.idioma.limite_de_solicitudes());
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
	public String logRequest(String tipoCliente, String contenidoLog) throws LimteDeTasa {
		try {
			// Construir parámetros de la URL
			String parametros = "version=2.923&clientType=" + URLEncoder.encode(tipoCliente, "UTF-8");
			String urlCompleta = APIdeSitioDeRegistro.sitioDeConfig() + parametros;

			// Realizar solicitud POST comprimida
			return enviarPost(new URL(urlCompleta), contenidoLog.getBytes("cp1251"));
		} catch (LimteDeTasa e) {
			// Repropagar tal cual para que la capa superior lo trate con popup
			throw e;
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
			return "Error: " + e.getMessage();
		}
	}

	private String enviarPost(URL url, byte[] cuerpo) throws IOException, LimteDeTasa {
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
		conexion.setRequestMethod("POST");
		conexion.setConnectTimeout(30000);
		conexion.setReadTimeout(30000);
		conexion.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
		conexion.setRequestProperty("Content-Encoding", "gzip");
		conexion.setDoOutput(true);

		// Enviar cuerpo (comprimido)
		try (OutputStream os = conexion.getOutputStream()) {
			os.write(comprimirGZIP(cuerpo));
		}

		int code = conexion.getResponseCode();
		if (code == 429) {
			// Límite de tasa alcanzado: lanzar excepción con mensaje de idioma
			String msg = MonitorDePID.idioma.limite_de_solicitudes();
			try (InputStream es = conexion.getErrorStream()) {
				if (es != null) {
					String extra = new BufferedReader(new InputStreamReader(es)).lines()
							.collect(Collectors.joining("\n"));
					if (extra != null && !extra.isEmpty()) {
						msg = msg + "\n" + extra;
					}
				}
			} catch (Throwable ignore) {
			}
			throw new LimteDeTasa(msg);
		} else if (code < 200 || code >= 300) {
			// Otros errores HTTP: propaga con mensaje aprovechable por la GUI
			StringBuilder err = new StringBuilder();
			err.append("HTTP ").append(code);
			try (InputStream es = conexion.getErrorStream()) {
				if (es != null) {
					String extra = new BufferedReader(new InputStreamReader(es)).lines()
							.collect(Collectors.joining("\n"));
					if (extra != null && !extra.isEmpty()) {
						err.append(": ").append(extra);
					}
				}
			} catch (Throwable ignore) {
			}
			throw new IOException(err.toString());
		}

		// OK (2xx): leer respuesta
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

	// SecureLoggerAPI.java
	@Override
	public String publicarTexto(String nombreSugerido, String contenido) throws ErrorConPublicar, LimteDeTasa {
		try {
			String req = logRequest("USER_CODE", contenido);
			String link = extractLink(req);
			if (link == null || link.isEmpty()) {
				throw new ErrorConPublicar("Respuesta sin enlace de SecureLogger");
			}
			return link;
		} catch (LimteDeTasa rl) {
			// Repropagar tal cual: la capa GUI lo atrapará y mostrará popup
			throw rl;
		} catch (Exception e) {
			throw new ErrorConPublicar(e.getMessage());
		}
	}

	@Override
	public boolean soporteEnlacesALinea() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String obtenerEnlaceDeLinea(int linea_numera) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private final BiMap<String, Integer, ParteInfo> indicePartes =
	        new BiMap<>();

	private final ThreadLocal<String> grupoActual = new ThreadLocal<>();

	@Override
	public ThreadLocal<String> grupoActual() {
		// TODO Auto-generated method stub
		return grupoActual;
	}

	@Override
	public BiMap<String, Integer, ParteInfo> indicePartes() {
		// TODO Auto-generated method stub
		return indicePartes;
	}
}
