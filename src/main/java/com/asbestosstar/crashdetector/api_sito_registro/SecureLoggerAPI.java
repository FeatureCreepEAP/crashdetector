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
import java.nio.charset.Charset;
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

	// --- Configuración general ---
	private static final int TIMEOUT_MS = 30000;
	private static final String CHARSET_BODY = "windows-1251"; // CP1251 para compatibilidad
	private static final int LIMITE_GZIP = 10 * 1024 * 1024; // 10 MB (límite del servidor)
	private static final int PRESUPUESTO_RAW = 11 * 1024 * 1024; // margen crudo antes de comprimir

	@Override
	public String nombre() {
		return "SecureLogger";
	}

	@Override
	public List<String> sitiosPorDefecto() {
		List<String> sitios = new ArrayList<String>();
		// Nota: mantener '?' porque esta API trabaja con querystring + POST
		sitios.add("https://securelogger.net/save/log?");
		sitios.add("https://securelogger.top/save/log?");
		return sitios;
	}

	// --- Publicación de registro completo (si ≤ 10MB sin dividir) ---
	@Override
	public String publicarRegistro(Consola registro) throws ErrorConPublicar {
		try {
			String req = logRequest("USER_CODE", registro.obtainerContenidoParaPublicar());
			String link = extraerEnlace(req);
			if (link == null || link.isEmpty()) {
				throw new ErrorConPublicar("Respuesta sin enlace de SecureLogger");
			}
			return link;
		} catch (LimteDeTasa lt) {
			throw new ErrorConPublicar(MonitorDePID.idioma.limite_de_solicitudes());
		} catch (Exception e) {
			throw new ErrorConPublicar(e.getMessage());
		}
	}

	/**
	 * Extrae el valor de "link" de un JSON simple: {"link":"..."}. Implementación
	 * ligera sin librerías externas.
	 */
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

		while (valueStart < jsonString.length() && Character.isWhitespace(jsonString.charAt(valueStart))) {
			valueStart++;
		}
		if (valueStart >= jsonString.length() || jsonString.charAt(valueStart) != '\"')
			return null;

		int valueEnd = jsonString.indexOf('\"', valueStart + 1);
		if (valueEnd < 0)
			return null;

		return jsonString.substring(valueStart + 1, valueEnd);
	}

	/**
	 * Construye la solicitud y la envía (cuerpo en CP1251, GZIP, longitud fija).
	 */
	public String logRequest(String tipoCliente, String contenidoLog) throws LimteDeTasa {
		try {
			String parametros = "version=" + URLEncoder.encode("2.923", "UTF-8") + "&clientType="
					+ URLEncoder.encode(tipoCliente, "UTF-8");
			String urlCompleta = APIdeSitioDeRegistro.sitioDeConfig() + parametros;

			byte[] cuerpo = (contenidoLog == null ? "" : contenidoLog).getBytes(Charset.forName(CHARSET_BODY));
			return enviarPost(new URL(urlCompleta), cuerpo);
		} catch (LimteDeTasa e) {
			throw e;
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
			return "Error: " + e.getMessage();
		}
	}

	/**
	 * Envía POST con cuerpo GZIP y longitud fija (evita chunked).
	 */
	private String enviarPost(URL url, byte[] cuerpo) throws IOException, LimteDeTasa {
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
		conexion.setRequestMethod("POST");
		conexion.setConnectTimeout(TIMEOUT_MS);
		conexion.setReadTimeout(TIMEOUT_MS);
		conexion.setRequestProperty("Content-Type", "text/plain; charset=" + CHARSET_BODY);
		conexion.setRequestProperty("Content-Encoding", "gzip");
		conexion.setDoOutput(true);

		// Comprimir una vez y anunciar longitud fija
		byte[] gz = comprimirGZIP(cuerpo);
		conexion.setFixedLengthStreamingMode(gz.length);

		try (OutputStream os = conexion.getOutputStream()) {
			os.write(gz);
		}

		int code = conexion.getResponseCode();
		if (code == 429) {
			// Límite de tasa alcanzado → mensaje en idioma + detalle si existe
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
			StringBuilder err = new StringBuilder("HTTP ").append(code);
			try (InputStream es = conexion.getErrorStream()) {
				if (es != null) {
					String extra = new BufferedReader(new InputStreamReader(es)).lines()
							.collect(Collectors.joining("\n"));
					if (extra != null && !extra.isEmpty())
						err.append(": ").append(extra);
				}
			} catch (Throwable ignore) {
			}
			throw new IOException(err.toString());
		}

		try (InputStream is = conexion.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			return reader.lines().collect(Collectors.joining("\n"));
		}
	}

	/** GZIP simple. */
	private byte[] comprimirGZIP(byte[] datos) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try (GZIPOutputStream gzip = new GZIPOutputStream(buffer)) {
			gzip.write(datos);
		}
		return buffer.toByteArray();
	}

	// --- APIdeSitioDeRegistro: publicar texto directo usando SecureLogger ---
	@Override
	public String publicarTexto(String nombreSugerido, String contenido) throws ErrorConPublicar, LimteDeTasa {
		try {
			String req = logRequest("USER_CODE", contenido);
			String link = extraerEnlace(req);
			if (link == null || link.isEmpty()) {
				throw new ErrorConPublicar("Respuesta sin enlace de SecureLogger");
			}
			return link;
		} catch (LimteDeTasa rl) {
			throw rl;
		} catch (Exception e) {
			throw new ErrorConPublicar(e.getMessage());
		}
	}

	// --- Enlaces a línea: SecureLogger no los soporta ---
	@Override
	public boolean soporteEnlacesALinea() {
		return false;
	}

	@Override
	public String obtenerEnlaceDeLinea(int linea_numera) {
		return null;
	}

	// --- Índice de partes (para mapear rangos de líneas) ---
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

	// --- Publicación por partes con vía rápida ≤10MB sin comprimir ---
	@Override
	public List<String> publicarRegistroEnPartes(Consola registro) throws ErrorConPublicar, LimteDeTasa {
		String contenido = (registro.obtainerContenidoParaPublicar() == null) ? ""
				: registro.obtainerContenidoParaPublicar();

		// Vía rápida: si el texto sin comprimir ≤ 10 MB → una sola parte
		byte[] sinComprimir = contenido.getBytes(Charset.forName(CHARSET_BODY));
		if (sinComprimir.length <= LIMITE_GZIP) {
			String base = (registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt");
			String u = publicarTexto(base, contenido);
			List<String> unica = new ArrayList<>();
			unica.add(u);
			return unica;
		}

		// Caso grande: dividir sin GZIP por línea (GZIP una sola vez por parte)
		List<String> urls = new ArrayList<>();
		String[] lineas = contenido.split("\n", -1);

		StringBuilder parte = new StringBuilder();
		int bytesParte = 0;
		int lineaDesde = 1;
		int acumuladas = 0;
		int indiceParte = 1;

		String gid = grupoActual().get();
		String base = (registro.archivo != null ? registro.archivo.getFileName().toString() : "log.txt");

		for (int i = 0; i < lineas.length; i++) {
			String l = lineas[i];
			String candidata = (parte.length() == 0) ? l : ("\n" + l);
			int bytesCand = candidata.getBytes(Charset.forName(CHARSET_BODY)).length;

			// Acumular hasta presupuesto crudo (rápido)
			if (bytesParte + bytesCand <= PRESUPUESTO_RAW) {
				parte.append(candidata);
				bytesParte += bytesCand;
				acumuladas++;
				continue;
			}

			// Presupuesto alcanzado → comprimir UNA vez y verificar
			try {
				byte[] gz = comprimirGZIP(parte.toString().getBytes(Charset.forName(CHARSET_BODY)));
				if (gz.length <= LIMITE_GZIP) {
					String u = publicarParte(base, indiceParte, parte.toString());
					urls.add(u);
					if (gid != null) {
						registrarParte(gid, indiceParte++, u, lineaDesde, lineaDesde + acumuladas - 1);
						lineaDesde += acumuladas;
					}
					// Reset y reintentar misma línea
					parte.setLength(0);
					bytesParte = 0;
					acumuladas = 0;
					i--;
				} else {
					// Raro: incluso comprimido excede → partir aproximadamente a la mitad
					int mid = parte.length() / 2;
					int split = parte.lastIndexOf("\n", mid);
					if (split <= 0)
						split = mid;

					String izquierda = parte.substring(0, split);
					byte[] gzIzq = comprimirGZIP(izquierda.getBytes(Charset.forName(CHARSET_BODY)));
					if (gzIzq.length > LIMITE_GZIP) {
						int q = parte.length() / 4;
						int s2 = parte.lastIndexOf("\n", q);
						if (s2 > 0)
							split = s2;
						izquierda = parte.substring(0, split);
						gzIzq = comprimirGZIP(izquierda.getBytes(Charset.forName(CHARSET_BODY)));
					}

					String u = publicarParte(base, indiceParte, izquierda);
					urls.add(u);
					if (gid != null) {
						int lineasIzq = izquierda.isEmpty() ? 0 : izquierda.split("\n", -1).length;
						registrarParte(gid, indiceParte++, u, lineaDesde, lineaDesde + lineasIzq - 1);
						lineaDesde += lineasIzq;
					}

					String derecha = parte.substring(split);
					parte.setLength(0);
					parte.append(derecha);
					bytesParte = derecha.getBytes(Charset.forName(CHARSET_BODY)).length;
					acumuladas = derecha.isEmpty() ? 0 : derecha.split("\n", -1).length;
					i--;
				}
			} catch (LimteDeTasa e) {
				throw e;
			} catch (Exception ex) {
				throw new ErrorConPublicar(ex.getMessage());
			}
		}

		// Última parte pendiente
		if (parte.length() > 0) {
			String u = publicarParte(base, indiceParte, parte.toString());
			urls.add(u);
			if (gid != null) {
				registrarParte(gid, indiceParte, u, lineaDesde, lineaDesde + acumuladas - 1);
			}
		}
		return urls;
	}

	/** Publica una parte con el nombre adecuado y sufijo " (parte n)". */
	private String publicarParte(String base, int indiceParte, String texto) throws ErrorConPublicar, LimteDeTasa {
		String nombre = (indiceParte > 1) ? base + " (parte " + indiceParte + ")" : base;
		return publicarTexto(nombre, texto);
	}
}
