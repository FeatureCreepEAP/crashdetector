package com.asbestosstar.crashdetector.escanernube;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.escanernube.UtilidadEscaneoNube.RespuestaHttp;
import com.asbestosstar.crashdetector.config.json.Json;

/**
 * Cliente de VirusTotal API v3.
 *
 * Primero consulta el SHA-256. Solo sube el archivo cuando todavía no existe un
 * informe para ese hash.
 */
public final class ClienteVirusTotal implements ClienteEscaneoNube {

	private static final String API_BASE = "https://www.virustotal.com/api/v3";
	private static final String RUTA_ARCHIVOS = API_BASE + "/files/";
	private static final String RUTA_SUBIDA = API_BASE + "/files";
	private static final String RUTA_URL_SUBIDA_GRANDE = API_BASE + "/files/upload_url";
	private static final String RUTA_ANALISIS = API_BASE + "/analyses/";

	private static final long LIMITE_SUBIDA_DIRECTA = 32L * 1024L * 1024L;
	private static final int TIEMPO_CONEXION_MS = 30_000;
	private static final int TIEMPO_LECTURA_MS = 120_000;
	private static final int MAXIMO_CONSULTAS_ANALISIS = 120;
	private static final long ESPERA_ENTRE_CONSULTAS_MS = 5_000L;

	private final String claveApi;

	public ClienteVirusTotal(String claveApi) {
		this.claveApi = claveApi == null ? "" : claveApi.trim();
	}

	@Override
	public ResultadoEscaneoNube escanear(File archivo, AtomicBoolean cancelado) throws Exception {
		validarClave();
		validarArchivo(archivo);
		UtilidadEscaneoNube.comprobarCancelacion(cancelado);

		String sha256 = UtilidadEscaneoNube.calcularSha256(archivo, cancelado);
		Json.Nodo informeExistente = obtenerInforme(sha256);

		if (informeExistente != null) {
			return convertirInforme(archivo, sha256, informeExistente,
					MonitorDePID.idioma.escanerNubeDetalleInformeExistente());
		}

		String idAnalisis = subirArchivo(archivo, cancelado);
		Json.Nodo analisis = esperarAnalisis(idAnalisis, cancelado);

		/*
		 * El objeto de análisis incluye estadísticas, pero el informe de archivo ofrece
		 * el formato más estable. Se intenta recuperar varias veces porque puede
		 * existir un pequeño retraso entre la finalización y la publicación del
		 * informe.
		 */
		for (int intento = 0; intento < 6; intento++) {
			UtilidadEscaneoNube.comprobarCancelacion(cancelado);
			Json.Nodo informe = obtenerInforme(sha256);
			if (informe != null) {
				return convertirInforme(archivo, sha256, informe,
						MonitorDePID.idioma.escanerNubeDetalleArchivoSubido());
			}
			UtilidadEscaneoNube.dormir(2_000L, cancelado);
		}

		Json.Nodo atributos = UtilidadEscaneoNube.objeto(analisis, "data", "attributes");
		Json.Nodo estadisticas = atributos == null ? null : UtilidadEscaneoNube.objeto(atributos, "stats");
		return convertirEstadisticas(archivo, sha256, estadisticas,
				MonitorDePID.idioma.escanerNubeDetalleArchivoSubido());
	}

	private void validarClave() throws IOException {
		if (claveApi.isEmpty()) {
			throw new IOException(MonitorDePID.idioma.escanerNubeFaltaClaveVirusTotal());
		}
	}

	private void validarArchivo(File archivo) throws IOException {
		if (archivo == null || !archivo.isFile() || !archivo.canRead()) {
			throw new IOException(MonitorDePID.idioma.escanerNubeArchivoNoLegible());
		}
	}

	private Json.Nodo obtenerInforme(String sha256) throws IOException {
		HttpURLConnection conexion = abrirConexion(RUTA_ARCHIVOS + UtilidadEscaneoNube.codificarSegmentoRuta(sha256),
				"GET");
		try {
			RespuestaHttp respuesta = UtilidadEscaneoNube.leerRespuesta(conexion);
			if (respuesta.codigo == HttpURLConnection.HTTP_NOT_FOUND) {
				return null;
			}
			if (!respuesta.esCorrecta()) {
				throw UtilidadEscaneoNube.errorHttp(ProveedorEscaneoNube.VIRUSTOTAL, respuesta.codigo,
						respuesta.cuerpo);
			}
			return UtilidadEscaneoNube.analizarObjetoJson(respuesta.cuerpo, ProveedorEscaneoNube.VIRUSTOTAL);
		} finally {
			conexion.disconnect();
		}
	}

	private String subirArchivo(File archivo, AtomicBoolean cancelado) throws Exception {
		String urlSubida = archivo.length() <= LIMITE_SUBIDA_DIRECTA ? RUTA_SUBIDA : obtenerUrlSubidaGrande();
		return subirMultipart(urlSubida, archivo, cancelado);
	}

	private String obtenerUrlSubidaGrande() throws IOException {
		HttpURLConnection conexion = abrirConexion(RUTA_URL_SUBIDA_GRANDE, "GET");
		try {
			RespuestaHttp respuesta = UtilidadEscaneoNube.leerRespuesta(conexion);
			if (!respuesta.esCorrecta()) {
				throw UtilidadEscaneoNube.errorHttp(ProveedorEscaneoNube.VIRUSTOTAL, respuesta.codigo,
						respuesta.cuerpo);
			}

			Json.Nodo raiz = UtilidadEscaneoNube.analizarObjetoJson(respuesta.cuerpo, ProveedorEscaneoNube.VIRUSTOTAL);
			String url = UtilidadEscaneoNube.cadena(raiz, "data");

			if (url.isEmpty()) {
				throw new IOException(MonitorDePID.idioma.escanerNubeErrorUrlSubidaVirusTotal());
			}

			URL urlAnalizada = new URL(url);
			String host = urlAnalizada.getHost();

			/*
			 * Algunas respuestas históricas mostraban el mismo host con HTTP. Solo se
			 * permite corregirlo automáticamente cuando el destino sigue perteneciendo a
			 * VirusTotal; nunca se envía la clave API a un host HTTP arbitrario.
			 */
			if ("http".equalsIgnoreCase(urlAnalizada.getProtocol()) && ("virustotal.com".equalsIgnoreCase(host)
					|| host.toLowerCase(java.util.Locale.ROOT).endsWith(".virustotal.com"))) {
				urlAnalizada = new URL("https", host, -1, urlAnalizada.getFile());
			}

			if (!"https".equalsIgnoreCase(urlAnalizada.getProtocol())) {
				throw new IOException(MonitorDePID.idioma.escanerNubeErrorUrlSubidaNoSegura());
			}
			return urlAnalizada.toExternalForm();
		} finally {
			conexion.disconnect();
		}
	}

	private String subirMultipart(String url, File archivo, AtomicBoolean cancelado) throws Exception {
		String frontera = "----CrashDetector" + UUID.randomUUID().toString().replace("-", "");
		String nombre = UtilidadEscaneoNube.nombreSeguroParaCabecera(archivo);

		byte[] inicio = ("--" + frontera + "\r\n" + "Content-Disposition: form-data; name=\"file\"; filename=\""
				+ nombre + "\"\r\n" + "Content-Type: application/java-archive\r\n\r\n")
				.getBytes(StandardCharsets.UTF_8);
		byte[] finalMultipart = ("\r\n--" + frontera + "--\r\n").getBytes(StandardCharsets.UTF_8);

		HttpURLConnection conexion = abrirConexion(url, "POST");
		conexion.setDoOutput(true);
		conexion.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + frontera);
		conexion.setFixedLengthStreamingMode((long) inicio.length + archivo.length() + finalMultipart.length);

		try {
			try (OutputStream salida = new BufferedOutputStream(conexion.getOutputStream())) {
				salida.write(inicio);
				UtilidadEscaneoNube.copiarArchivoAlFlujo(archivo, salida, cancelado);
				salida.write(finalMultipart);
			}

			RespuestaHttp respuesta = UtilidadEscaneoNube.leerRespuesta(conexion);
			if (!respuesta.esCorrecta()) {
				throw UtilidadEscaneoNube.errorHttp(ProveedorEscaneoNube.VIRUSTOTAL, respuesta.codigo,
						respuesta.cuerpo);
			}

			Json.Nodo raiz = UtilidadEscaneoNube.analizarObjetoJson(respuesta.cuerpo, ProveedorEscaneoNube.VIRUSTOTAL);
			Json.Nodo datos = UtilidadEscaneoNube.objeto(raiz, "data");
			String id = UtilidadEscaneoNube.cadena(datos, "id");

			if (id.isEmpty()) {
				throw new IOException(MonitorDePID.idioma.escanerNubeErrorIdAnalisis());
			}
			return id;
		} finally {
			conexion.disconnect();
		}
	}

	private Json.Nodo esperarAnalisis(String idAnalisis, AtomicBoolean cancelado) throws Exception {
		String ruta = RUTA_ANALISIS + UtilidadEscaneoNube.codificarSegmentoRuta(idAnalisis);

		for (int intento = 0; intento < MAXIMO_CONSULTAS_ANALISIS; intento++) {
			UtilidadEscaneoNube.comprobarCancelacion(cancelado);
			HttpURLConnection conexion = abrirConexion(ruta, "GET");

			try {
				RespuestaHttp respuesta = UtilidadEscaneoNube.leerRespuesta(conexion);
				if (!respuesta.esCorrecta()) {
					throw UtilidadEscaneoNube.errorHttp(ProveedorEscaneoNube.VIRUSTOTAL, respuesta.codigo,
							respuesta.cuerpo);
				}

				Json.Nodo raiz = UtilidadEscaneoNube.analizarObjetoJson(respuesta.cuerpo,
						ProveedorEscaneoNube.VIRUSTOTAL);
				Json.Nodo atributos = UtilidadEscaneoNube.objeto(raiz, "data", "attributes");
				String estado = UtilidadEscaneoNube.cadena(atributos, "status");

				if ("completed".equalsIgnoreCase(estado)) {
					return raiz;
				}
			} finally {
				conexion.disconnect();
			}

			UtilidadEscaneoNube.dormir(ESPERA_ENTRE_CONSULTAS_MS, cancelado);
		}

		throw new IOException(
				MonitorDePID.idioma.escanerNubeErrorTiempoEspera(ProveedorEscaneoNube.VIRUSTOTAL.nombreVisible()));
	}

	private ResultadoEscaneoNube convertirInforme(File archivo, String sha256, Json.Nodo raiz, String detalle) {
		Json.Nodo atributos = UtilidadEscaneoNube.objeto(raiz, "data", "attributes");
		Json.Nodo estadisticas = atributos == null ? null
				: UtilidadEscaneoNube.objeto(atributos, "last_analysis_stats");
		return convertirEstadisticas(archivo, sha256, estadisticas, detalle);
	}

	private ResultadoEscaneoNube convertirEstadisticas(File archivo, String sha256, Json.Nodo estadisticas,
			String detalle) {
		if (estadisticas == null) {
			return new ResultadoEscaneoNube(ProveedorEscaneoNube.VIRUSTOTAL, archivo,
					MonitorDePID.idioma.escanerNubeResultadoDesconocido(), 0, 0, sha256,
					MonitorDePID.idioma.escanerNubeDetalleSinEstadisticas());
		}

		int maliciosos = UtilidadEscaneoNube.entero(estadisticas, "malicious");
		int sospechosos = UtilidadEscaneoNube.entero(estadisticas, "suspicious");
		int detecciones = maliciosos + sospechosos;
		int motores = UtilidadEscaneoNube.sumarEnteros(estadisticas);

		String estado;
		if (maliciosos > 0) {
			estado = MonitorDePID.idioma.escanerNubeResultadoMalicioso();
		} else if (sospechosos > 0) {
			estado = MonitorDePID.idioma.escanerNubeResultadoSospechoso();
		} else {
			estado = MonitorDePID.idioma.escanerNubeResultadoSinDetecciones();
		}

		return new ResultadoEscaneoNube(ProveedorEscaneoNube.VIRUSTOTAL, archivo, estado, detecciones, motores, sha256,
				detalle);
	}

	private HttpURLConnection abrirConexion(String url, String metodo) throws IOException {
		HttpURLConnection conexion = (HttpURLConnection) new URL(url).openConnection();
		conexion.setRequestMethod(metodo);
		conexion.setConnectTimeout(TIEMPO_CONEXION_MS);
		conexion.setReadTimeout(TIEMPO_LECTURA_MS);
		conexion.setUseCaches(false);
		conexion.setRequestProperty("Accept", "application/json");
		conexion.setRequestProperty("x-apikey", claveApi);
		return conexion;
	}
}
