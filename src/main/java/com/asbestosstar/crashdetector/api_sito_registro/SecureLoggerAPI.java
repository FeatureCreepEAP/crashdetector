package com.asbestosstar.crashdetector.api_sito_registro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.mapas.BiMap;

public class SecureLoggerAPI implements APIdeSitioDeRegistro {

	private static final int TIMEOUT_MS = 30000;

	private static final String CHARSET_BODY = "windows-1251";

	private static final Charset CHARSET = Charset.forName(CHARSET_BODY);

	private static final int LIMITE_GZIP = 10 * 1024 * 1024;

	private static final int PRESUPUESTO_RAW = 11 * 1024 * 1024;


	@Override
	public String nombre() {
		return "SecureLogger";
	}


	@Override
	public List<String> sitiosPorDefecto() {

		List<String> sitios = new ArrayList<>();

		sitios.add("https://securelogger.net/save/log?");
		sitios.add("https://securelogger.top/save/log?");
		sitios.add("https://ruzone.securelogger.net/save/log?");

		return sitios;
	}


	@Override
	public String publicarRegistro(Consola registro) throws ErrorConPublicar {

		try {

			String req = logRequest(
				"USER_CODE",
				registro.obtainerContenidoParaPublicar()
			);

			String link = extraerEnlace(req);

			if (link == null || link.isEmpty())
				throw new ErrorConPublicar("Respuesta sin enlace");

			return link;

		}
		catch (LimteDeTasa lt) {

			throw new ErrorConPublicar(
				MonitorDePID.idioma.limite_de_solicitudes()
			);

		}
		catch (Exception e) {

			throw new ErrorConPublicar(e.getMessage());

		}
	}


	public static String extraerEnlace(String jsonString) {

		if (jsonString == null)
			return null;

		String key = "\"link\"";

		int keyIndex = jsonString.indexOf(key);

		if (keyIndex < 0)
			return null;

		int valueStart = jsonString.indexOf(':', keyIndex);

		if (valueStart < 0)
			return null;

		valueStart++;

		while (valueStart < jsonString.length()
			&& Character.isWhitespace(jsonString.charAt(valueStart)))
			valueStart++;

		if (valueStart >= jsonString.length()
			|| jsonString.charAt(valueStart) != '"')
			return null;

		int valueEnd = jsonString.indexOf('"', valueStart + 1);

		if (valueEnd < 0)
			return null;

		return jsonString.substring(valueStart + 1, valueEnd);
	}


	public String logRequest(
		String tipoCliente,
		String contenidoLog
	)
		throws LimteDeTasa {

		try {

			String parametros =
				"version="
				+ URLEncoder.encode("2.923", "UTF-8")
				+ "&clientType="
				+ URLEncoder.encode(tipoCliente, "UTF-8");

			String urlCompleta =
				APIdeSitioDeRegistro.sitioDeConfig()
				+ parametros;

			byte[] cuerpo =
				(contenidoLog == null ? "" : contenidoLog)
					.getBytes(CHARSET);

			return enviarPost(
				new URL(urlCompleta),
				cuerpo
			);

		}
		catch (LimteDeTasa e) {

			throw e;

		}
		catch (Exception e) {

			CrashDetectorLogger.logException(e);

			return "Error: " + e.getMessage();

		}
	}


	/**
	 * Envía POST usando compresión real SOLO cuando es necesario.
	 */
	private String enviarPost(
		URL url,
		byte[] cuerpo
	)
		throws IOException, LimteDeTasa {

		HttpURLConnection conexion =
			(HttpURLConnection) url.openConnection(
				Proxy.NO_PROXY
			);

		conexion.setRequestMethod("POST");

		conexion.setConnectTimeout(TIMEOUT_MS);

		conexion.setReadTimeout(TIMEOUT_MS);

		conexion.setRequestProperty(
			"Content-Type",
			"text/plain; charset=" + CHARSET_BODY
		);

		conexion.setRequestProperty(
			"Content-Encoding",
			"gzip"
		);

		conexion.setDoOutput(true);


		byte[] gz =
			EstimadorGZIP.comprimir(cuerpo);


		conexion.setFixedLengthStreamingMode(
			gz.length
		);


		try (OutputStream os =
			conexion.getOutputStream()) {

			os.write(gz);

		}


		int code = conexion.getResponseCode();


		if (code == 429) {

			throw new LimteDeTasa(
				MonitorDePID.idioma
					.limite_de_solicitudes()
			);

		}


		if (code < 200 || code >= 300)
			throw new IOException(
				"HTTP " + code
			);


		try (
			InputStream is =
				conexion.getInputStream();

			BufferedReader reader =
				new BufferedReader(
					new InputStreamReader(is)
				)
		) {

			return reader.lines()
				.collect(Collectors.joining("\n"));

		}
	}


	@Override
	public String publicarTexto(
		String nombreSugerido,
		String contenido
	)
		throws ErrorConPublicar, LimteDeTasa {

		try {

			String req =
				logRequest(
					"USER_CODE",
					contenido
				);

			String link =
				extraerEnlace(req);

			if (link == null)
				throw new ErrorConPublicar(
					"Respuesta inválida"
				);

			return link;

		}
		catch (LimteDeTasa rl) {

			throw rl;

		}
		catch (Exception e) {

			throw new ErrorConPublicar(
				e.getMessage()
			);

		}
	}


	private final BiMap<String, Integer, ParteInfo>
		indicePartes = new BiMap<>();

	private final ThreadLocal<String>
		grupoActual = new ThreadLocal<>();


	@Override
	public ThreadLocal<String>
		grupoActual() {

		return grupoActual;

	}


	@Override
	public BiMap<String, Integer, ParteInfo>
		indicePartes() {

		return indicePartes;

	}


	/**
	 * Publicación en partes usando estimación rápida GZIP.
	 */
	@Override
	public List<String>
		publicarRegistroEnPartes(
			Consola registro
		)
		throws ErrorConPublicar, LimteDeTasa {

		String contenido =
			registro.obtainerContenidoParaPublicar();

		byte[] raw =
			contenido.getBytes(CHARSET);


		/*
		 * vía rápida usando estimador
		 */
		if (EstimadorGZIP.cabeDentroLimite(
			raw,
			LIMITE_GZIP
		)) {

			List<String> unica =
				new ArrayList<>();

			unica.add(
				publicarTexto(
					"log.txt",
					contenido
				)
			);

			return unica;

		}


		List<String> urls =
			new ArrayList<>();


		StringBuilder parte =
			new StringBuilder();


		int bytesParte = 0;


		String[] lineas =
			contenido.split("\n", -1);


		for (String linea : lineas) {

			byte[] bytesLinea =
				(linea + "\n")
					.getBytes(CHARSET);


			if (bytesParte + bytesLinea.length
				> PRESUPUESTO_RAW) {

				byte[] rawParte =
					parte.toString()
						.getBytes(CHARSET);


				if (!EstimadorGZIP
					.cabeDentroLimite(
						rawParte,
						LIMITE_GZIP
					))
					throw new ErrorConPublicar(
						"Parte excede límite"
					);


				urls.add(
					publicarTexto(
						"log.txt",
						parte.toString()
					)
				);


				parte.setLength(0);

				bytesParte = 0;

			}


			parte.append(linea)
				.append('\n');

			bytesParte +=
				bytesLinea.length;

		}


		if (parte.length() > 0)
			urls.add(
				publicarTexto(
					"log.txt",
					parte.toString()
				)
			);


		return urls;

	}


	private String publicarParte(
		String base,
		int indiceParte,
		String texto
	)
		throws ErrorConPublicar, LimteDeTasa {

		return publicarTexto(
			base + " (parte " + indiceParte + ")",
			texto
		);

	}


	@Override
	public boolean soporteEnlacesALinea() {
		// TODO Auto-generated method stub
		return false;
	}

}
