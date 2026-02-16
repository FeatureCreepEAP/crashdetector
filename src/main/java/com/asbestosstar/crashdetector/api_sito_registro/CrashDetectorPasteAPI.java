package com.asbestosstar.crashdetector.api_sito_registro;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro.ParteInfo;
import com.asbestosstar.crashdetector.mapas.BiMap;

/**
 * Implementación de APIdeSitioDeRegistro para publicar registros comprimidos en
 * el servicio PHP remoto (CrashDetector Paste API).
 *
 * - Envía el log comprimido con GZIP mediante POST. - Si el registro completo
 * pesa menos de 20 MB (sin comprimir), se envía en una sola parte (sin
 * dividir). - Si supera ese tamaño, se divide inteligentemente sin comprimir
 * cada línea de manera individual.
 */
public class CrashDetectorPasteAPI implements APIdeSitioDeRegistro {

	private static final String UA = "CrashDetector/1.x (+crashdetector-php)";
	private static final int TIMEOUT = 30000;

	@Override
	public String nombre() {
		return "CrashDetectorPaste";
	}

	@Override
	public List<String> sitiosPorDefecto() {
		List<String> sitios = new ArrayList<>();
		sitios.add("https://asbestosstar.egoism.jp/crash_detector/paste/endpoint.php?action=save_log");
		return sitios;
	}

	/** Asegura que el endpoint contenga la acción de guardado. */
	private String normalizarEndpoint(String s) {
		if (s == null || s.isEmpty()) {
			return "https://asbestosstar.egoism.jp/crash_detector/paste/endpoint.php?action=save_log";
		}
		String basePhp = "endpoint.php";
		if (s.endsWith("?"))
			s = s.substring(0, s.length() - 1);
		if (s.contains(basePhp) && !s.contains("action=save_log")) {
			if (s.contains("?")) {
				s = s + "&action=save_log";
			} else {
				s = s + "?action=save_log";
			}
		}
		return s;
	}

	/** Comprime un arreglo de bytes usando GZIP. */
	private static byte[] comprimirGZIP(byte[] datos) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (GZIPOutputStream gz = new GZIPOutputStream(baos)) {
			gz.write(datos);
		}
		return baos.toByteArray();
	}

	/** Lee un flujo de entrada completo y lo devuelve como texto UTF-8. */
	private static String leer(InputStream is) {
		if (is == null)
			return null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			StringBuilder sb = new StringBuilder();
			String l;
			while ((l = br.readLine()) != null) {
				sb.append(l);
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/** Extrae el valor del campo "link" de una respuesta JSON simple. */
	private static String extraerLinkDeJson(String json) {
		if (json == null)
			return null;
		String clave = "\"link\"";
		int i = json.indexOf(clave);
		if (i < 0)
			return null;
		int dosPuntos = json.indexOf(':', i);
		if (dosPuntos < 0)
			return null;

		int pos = dosPuntos + 1;
		while (pos < json.length() && Character.isWhitespace(json.charAt(pos)))
			pos++;

		if (pos >= json.length() || json.charAt(pos) != '"')
			return null;
		int fin = json.indexOf('"', pos + 1);
		if (fin < 0)
			return null;

		return json.substring(pos + 1, fin);
	}

	/** Publica texto arbitrario en el servidor remoto. */
	@Override
	public String publicarTexto(String nombreSugerido, String contenido) throws ErrorConPublicar {
		try {
			byte[] datos = (contenido == null ? "" : contenido).getBytes(StandardCharsets.UTF_8);
			String endpoint = normalizarEndpoint(APIdeSitioDeRegistro.sitioDeConfig());
			URL url = new URL(endpoint);

			// Comprimir y enviar con longitud fija para evitar transferencia chunked
			byte[] gzData = comprimirGZIP(datos);

			HttpURLConnection con = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
			con.setRequestMethod("POST");
			con.setConnectTimeout(TIMEOUT);
			con.setReadTimeout(TIMEOUT);
			con.setRequestProperty("User-Agent", UA);
			con.setRequestProperty("Content-Type", "application/octet-stream");
			con.setRequestProperty("Content-Encoding", "gzip");
			con.setDoOutput(true);
			con.setFixedLengthStreamingMode(gzData.length); // ← importante

			try (OutputStream os = con.getOutputStream()) {
				os.write(gzData);
			}

			int code = con.getResponseCode();
			if (code != HttpURLConnection.HTTP_OK) {
				String err = leer(con.getErrorStream());
				throw new ErrorConPublicar("CrashDetectorPaste HTTP " + code + (err == null ? "" : (": " + err)));
			}

			String body = leer(con.getInputStream());
			String link = extraerLinkDeJson(body);
			if (link == null || link.isEmpty())
				throw new ErrorConPublicar("Respuesta sin link");
			return link.replace("\\/", "/");
		} catch (Exception ex) {
			throw new ErrorConPublicar(ex.getMessage());
		}
	}

	@Override
	public String publicarRegistro(Consola registro) {
		try {
			return publicarTexto(registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt",
					registro.obtainerContenidoParaPublicar());
		} catch (ErrorConPublicar e) {
			CrashDetectorLogger.logException(e);
			return null;
		}
	}

	@Override
	public boolean soporteEnlacesALinea() {
		return true;
	}

	private final BiMap<String, Integer, ParteInfo> indicePartes = new BiMap<>();
	private final ThreadLocal<String> grupoActual = new ThreadLocal<>();

	@Override
	public ThreadLocal<String> grupoActual() {
		return grupoActual;
	}

	@Override
	public BiMap<String, Integer, ParteInfo> indicePartes() {
		return indicePartes;
	}

	/**
	 * Publica el registro en una o varias partes. Si el texto sin comprimir es ≤ 20
	 * MB, se publica directamente sin dividir.
	 */
	@Override
	public List<String> publicarRegistroEnPartes(Consola registro)
			throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro, LimteDeTasa {

		String contenido = registro.obtainerContenidoParaPublicar();

		if (contenido == null)
			contenido = "";

		final int LIMITE_GZIP = 20 * 1024 * 1024;

		final int PRESUPUESTO_RAW = 22 * 1024 * 1024;

		byte[] raw = contenido.getBytes(StandardCharsets.UTF_8);

		/*
		 * VIA RAPIDA usando estimador real
		 */
		if (EstimadorGZIP.cabeDentroLimite(raw, LIMITE_GZIP)) {

			String base = registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt";

			String url = publicarTexto(base, contenido);

			List<String> unica = new ArrayList<>();

			unica.add(url);

			String gid = grupoActual().get();

			if (gid != null) {

				int totalLineas = contenido.isEmpty() ? 0 : contenido.split("\n", -1).length;

				registrarParte(gid, 1, url, 1, Math.max(1, totalLineas));
			}

			return unica;
		}

		/*
		 * DIVISION usando estimador GZIP
		 */
		List<String> urls = new ArrayList<>();

		String[] lineas = contenido.split("\n", -1);

		StringBuilder parte = new StringBuilder();

		int bytesParte = 0;

		int lineaDesde = 1;

		int acumuladas = 0;

		int indiceParte = 1;

		String gid = grupoActual().get();

		for (int i = 0; i < lineas.length; i++) {

			String l = lineas[i];

			String candidata = parte.length() == 0 ? l : "\n" + l;

			byte[] candidataBytes = candidata.getBytes(StandardCharsets.UTF_8);

			if (bytesParte + candidataBytes.length <= PRESUPUESTO_RAW) {

				parte.append(candidata);

				bytesParte += candidataBytes.length;

				acumuladas++;

				continue;
			}

			byte[] rawParte = parte.toString().getBytes(StandardCharsets.UTF_8);

			if (!EstimadorGZIP.cabeDentroLimite(rawParte, LIMITE_GZIP)) {

				throw new ErrorConPublicar("Parte excede limite GZIP");
			}

			String url = publicarParte(registro, indiceParte, parte.toString());

			urls.add(url);

			if (gid != null) {

				registrarParte(gid, indiceParte++, url, lineaDesde, lineaDesde + acumuladas - 1);

				lineaDesde += acumuladas;
			}

			parte.setLength(0);

			bytesParte = 0;

			acumuladas = 0;

			i--;
		}

		if (parte.length() > 0) {

			String url = publicarParte(registro, indiceParte, parte.toString());

			urls.add(url);

			if (gid != null) {

				registrarParte(gid, indiceParte, url, lineaDesde, lineaDesde + acumuladas - 1);
			}
		}

		return urls;
	}

	/** Publica una parte con el nombre adecuado. */
	private String publicarParte(Consola registro, int indiceParte, String texto) throws ErrorConPublicar {
		String base = (registro.archivo != null) ? registro.archivo.getFileName().toString() : "log.txt";
		String nombre = (indiceParte > 1) ? base + " (parte " + indiceParte + ")" : base;
		return publicarTexto(nombre, texto);
	}
}
