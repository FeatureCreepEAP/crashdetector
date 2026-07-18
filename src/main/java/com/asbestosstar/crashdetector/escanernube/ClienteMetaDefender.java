package com.asbestosstar.crashdetector.escanernube;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.escanernube.UtilidadEscaneoNube.RespuestaHttp;
import com.asbestosstar.crashdetector.config.json.Json;

/**
 * Cliente de MetaDefender Cloud API v4.
 *
 * Primero consulta el SHA-256. Solo sube el archivo cuando el hash no existe.
 */
public final class ClienteMetaDefender implements ClienteEscaneoNube {

	private static final String API_BASE = "https://api.metadefender.com/v4";
	private static final String RUTA_HASH = API_BASE + "/hash/";
	private static final String RUTA_SUBIDA = API_BASE + "/file";
	private static final String RUTA_RESULTADO = API_BASE + "/file/";

	private static final int TIEMPO_CONEXION_MS = 30_000;
	private static final int TIEMPO_LECTURA_MS = 120_000;
	private static final int MAXIMO_CONSULTAS_ANALISIS = 120;
	private static final long ESPERA_ENTRE_CONSULTAS_MS = 5_000L;

	private final String claveApi;

	public ClienteMetaDefender(String claveApi) {
		this.claveApi = claveApi == null ? "" : claveApi.trim();
	}

	@Override
	public ResultadoEscaneoNube escanear(File archivo, AtomicBoolean cancelado) throws Exception {
		validarClave();
		validarArchivo(archivo);
		UtilidadEscaneoNube.comprobarCancelacion(cancelado);

		String sha256 = UtilidadEscaneoNube.calcularSha256(archivo, cancelado);
		Json.Nodo informeExistente = obtenerInformePorHash(sha256);

		if (informeExistente != null) {
			return convertirInforme(archivo, sha256, informeExistente,
					MonitorDePID.idioma.escanerNubeDetalleInformeExistente());
		}

		String dataId = subirArchivo(archivo, cancelado);
		Json.Nodo informe = esperarResultado(dataId, cancelado);
		return convertirInforme(archivo, sha256, informe, MonitorDePID.idioma.escanerNubeDetalleArchivoSubido());
	}

	private void validarClave() throws IOException {
		if (claveApi.isEmpty()) {
			throw new IOException(MonitorDePID.idioma.escanerNubeFaltaClaveMetaDefender());
		}
	}

	private void validarArchivo(File archivo) throws IOException {
		if (archivo == null || !archivo.isFile() || !archivo.canRead()) {
			throw new IOException(MonitorDePID.idioma.escanerNubeArchivoNoLegible());
		}
	}

	private Json.Nodo obtenerInformePorHash(String sha256) throws IOException {
		HttpURLConnection conexion = abrirConexion(RUTA_HASH + UtilidadEscaneoNube.codificarSegmentoRuta(sha256),
				"GET");
		try {
			RespuestaHttp respuesta = UtilidadEscaneoNube.leerRespuesta(conexion);
			if (respuesta.codigo == HttpURLConnection.HTTP_NOT_FOUND) {
				return null;
			}
			if (!respuesta.esCorrecta()) {
				throw UtilidadEscaneoNube.errorHttp(ProveedorEscaneoNube.METADEFENDER, respuesta.codigo,
						respuesta.cuerpo);
			}
			return UtilidadEscaneoNube.analizarObjetoJson(respuesta.cuerpo, ProveedorEscaneoNube.METADEFENDER);
		} finally {
			conexion.disconnect();
		}
	}

	private String subirArchivo(File archivo, AtomicBoolean cancelado) throws Exception {
		HttpURLConnection conexion = abrirConexion(RUTA_SUBIDA, "POST");
		conexion.setDoOutput(true);
		conexion.setRequestProperty("Content-Type", "application/octet-stream");
		conexion.setRequestProperty("filename", UtilidadEscaneoNube.nombreSeguroParaCabecera(archivo));
		conexion.setFixedLengthStreamingMode(archivo.length());

		try {
			try (OutputStream salida = new BufferedOutputStream(conexion.getOutputStream())) {
				UtilidadEscaneoNube.copiarArchivoAlFlujo(archivo, salida, cancelado);
			}

			RespuestaHttp respuesta = UtilidadEscaneoNube.leerRespuesta(conexion);
			if (!respuesta.esCorrecta()) {
				throw UtilidadEscaneoNube.errorHttp(ProveedorEscaneoNube.METADEFENDER, respuesta.codigo,
						respuesta.cuerpo);
			}

			Json.Nodo raiz = UtilidadEscaneoNube.analizarObjetoJson(respuesta.cuerpo,
					ProveedorEscaneoNube.METADEFENDER);
			String dataId = UtilidadEscaneoNube.cadena(raiz, "data_id");

			if (dataId.isEmpty()) {
				throw new IOException(MonitorDePID.idioma.escanerNubeErrorIdAnalisis());
			}
			return dataId;
		} finally {
			conexion.disconnect();
		}
	}

	private Json.Nodo esperarResultado(String dataId, AtomicBoolean cancelado) throws Exception {
		String ruta = RUTA_RESULTADO + UtilidadEscaneoNube.codificarSegmentoRuta(dataId);

		for (int intento = 0; intento < MAXIMO_CONSULTAS_ANALISIS; intento++) {
			UtilidadEscaneoNube.comprobarCancelacion(cancelado);
			HttpURLConnection conexion = abrirConexion(ruta, "GET");

			try {
				RespuestaHttp respuesta = UtilidadEscaneoNube.leerRespuesta(conexion);
				if (!respuesta.esCorrecta()) {
					throw UtilidadEscaneoNube.errorHttp(ProveedorEscaneoNube.METADEFENDER, respuesta.codigo,
							respuesta.cuerpo);
				}

				Json.Nodo raiz = UtilidadEscaneoNube.analizarObjetoJson(respuesta.cuerpo,
						ProveedorEscaneoNube.METADEFENDER);
				Json.Nodo proceso = UtilidadEscaneoNube.objeto(raiz, "process_info");
				int progreso = UtilidadEscaneoNube.entero(proceso, "progress_percentage");

				if (progreso >= 100) {
					return raiz;
				}
			} finally {
				conexion.disconnect();
			}

			UtilidadEscaneoNube.dormir(ESPERA_ENTRE_CONSULTAS_MS, cancelado);
		}

		throw new IOException(
				MonitorDePID.idioma.escanerNubeErrorTiempoEspera(ProveedorEscaneoNube.METADEFENDER.nombreVisible()));
	}

	private ResultadoEscaneoNube convertirInforme(File archivo, String sha256, Json.Nodo raiz, String detalle) {
		Json.Nodo resultados = UtilidadEscaneoNube.objeto(raiz, "scan_results");
		if (resultados == null) {
			return new ResultadoEscaneoNube(ProveedorEscaneoNube.METADEFENDER, archivo,
					MonitorDePID.idioma.escanerNubeResultadoDesconocido(), 0, 0, sha256,
					MonitorDePID.idioma.escanerNubeDetalleSinEstadisticas());
		}

		int detecciones = UtilidadEscaneoNube.entero(resultados, "total_detected_avs");
		int motores = UtilidadEscaneoNube.entero(resultados, "total_avs");
		int codigoResultado = UtilidadEscaneoNube.entero(resultados, "scan_all_result_i");

		String estado;
		switch (codigoResultado) {
		case 0:
			estado = detecciones > 0 ? MonitorDePID.idioma.escanerNubeResultadoMalicioso()
					: MonitorDePID.idioma.escanerNubeResultadoSinDetecciones();
			break;
		case 1:
			estado = MonitorDePID.idioma.escanerNubeResultadoMalicioso();
			break;
		case 2:
			estado = MonitorDePID.idioma.escanerNubeResultadoSospechoso();
			break;
		case 3:
			estado = MonitorDePID.idioma.escanerNubeResultadoFallido();
			break;
		case 5:
			estado = MonitorDePID.idioma.escanerNubeResultadoDesconocido();
			break;
		default:
			estado = detecciones > 0 ? MonitorDePID.idioma.escanerNubeResultadoMalicioso()
					: MonitorDePID.idioma.escanerNubeResultadoDesconocido();
			break;
		}

		return new ResultadoEscaneoNube(ProveedorEscaneoNube.METADEFENDER, archivo, estado, detecciones, motores,
				sha256, detalle);
	}

	private HttpURLConnection abrirConexion(String url, String metodo) throws IOException {
		HttpURLConnection conexion = (HttpURLConnection) new URL(url).openConnection();
		conexion.setRequestMethod(metodo);
		conexion.setConnectTimeout(TIEMPO_CONEXION_MS);
		conexion.setReadTimeout(TIEMPO_LECTURA_MS);
		conexion.setUseCaches(false);
		conexion.setRequestProperty("Accept", "application/json");
		conexion.setRequestProperty("apikey", claveApi);
		return conexion;
	}
}
