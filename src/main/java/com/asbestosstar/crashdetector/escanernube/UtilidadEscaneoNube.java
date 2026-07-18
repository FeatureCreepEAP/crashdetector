package com.asbestosstar.crashdetector.escanernube;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.json.Json;

/**
 * Funciones compartidas por los clientes de VirusTotal y MetaDefender.
 */
public final class UtilidadEscaneoNube {

	private static final int TAMANO_BUFFER = 64 * 1024;
	private static final int LIMITE_TEXTO_ERROR = 1200;

	private UtilidadEscaneoNube() {
	}

	public static String calcularSha256(File archivo, AtomicBoolean cancelado) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] buffer = new byte[TAMANO_BUFFER];

		try (InputStream entrada = new BufferedInputStream(new FileInputStream(archivo))) {
			int leidos;
			while ((leidos = entrada.read(buffer)) != -1) {
				comprobarCancelacion(cancelado);
				digest.update(buffer, 0, leidos);
			}
		}

		StringBuilder resultado = new StringBuilder(64);
		for (byte valor : digest.digest()) {
			resultado.append(String.format(Locale.ROOT, "%02x", valor & 0xff));
		}
		return resultado.toString();
	}

	public static void copiarArchivoAlFlujo(File archivo, java.io.OutputStream salida, AtomicBoolean cancelado)
			throws IOException {
		byte[] buffer = new byte[TAMANO_BUFFER];

		try (InputStream entrada = new BufferedInputStream(new FileInputStream(archivo))) {
			int leidos;
			while ((leidos = entrada.read(buffer)) != -1) {
				comprobarCancelacion(cancelado);
				salida.write(buffer, 0, leidos);
			}
		}
	}

	public static RespuestaHttp leerRespuesta(HttpURLConnection conexion) throws IOException {
		int codigo = conexion.getResponseCode();
		InputStream flujo = codigo >= 200 && codigo < 400 ? conexion.getInputStream() : conexion.getErrorStream();
		String cuerpo = leerTexto(flujo);
		return new RespuestaHttp(codigo, cuerpo);
	}

	public static String leerTexto(InputStream flujo) throws IOException {
		if (flujo == null) {
			return "";
		}

		StringBuilder texto = new StringBuilder();
		try (BufferedReader lector = new BufferedReader(new InputStreamReader(flujo, StandardCharsets.UTF_8))) {
			char[] buffer = new char[8192];
			int leidos;
			while ((leidos = lector.read(buffer)) != -1) {
				texto.append(buffer, 0, leidos);
			}
		}
		return texto.toString();
	}

	public static Json.Nodo analizarObjetoJson(String cuerpo, ProveedorEscaneoNube proveedor) throws IOException {
		try {
			String texto = cuerpo == null || cuerpo.trim().isEmpty() ? "{}" : cuerpo;
			Json.Nodo nodo = Json.leer(texto);

			if (!esObjetoReal(nodo)) {
				throw new IOException(MonitorDePID.idioma.escanerNubeErrorRespuestaJson(proveedor.nombreVisible()));
			}

			return nodo;
		} catch (RuntimeException e) {
			throw new IOException(MonitorDePID.idioma.escanerNubeErrorRespuestaJson(proveedor.nombreVisible()), e);
		}
	}

	/**
	 * Recorre una ruta de objetos sin crear claves inexistentes.
	 *
	 * Json.Nodo.obtener(...) crea una clave vacia cuando no existe para mantener
	 * compatibilidad con el estilo DMR. Por eso primero se comprueba claves().
	 */
	public static Json.Nodo objeto(Json.Nodo raiz, String... ruta) {
		Json.Nodo actual = raiz;

		if (!esObjetoReal(actual)) {
			return null;
		}

		for (String parte : ruta) {
			if (parte == null || !contieneClave(actual, parte)) {
				return null;
			}

			actual = actual.obtener(parte);
			if (!esObjetoReal(actual)) {
				return null;
			}
		}

		return actual;
	}

	public static String cadena(Json.Nodo objeto, String clave) {
		if (!contieneClave(objeto, clave)) {
			return "";
		}

		try {
			String valor = objeto.obtener(clave).comoCadena();
			return valor == null ? "" : valor;
		} catch (RuntimeException e) {
			return "";
		}
	}

	public static int entero(Json.Nodo objeto, String clave) {
		if (!contieneClave(objeto, clave)) {
			return 0;
		}

		try {
			return objeto.obtener(clave).comoEntero();
		} catch (RuntimeException e) {
			return 0;
		}
	}

	public static int sumarEnteros(Json.Nodo objeto) {
		if (!esObjetoReal(objeto)) {
			return 0;
		}

		int total = 0;
		for (String clave : objeto.claves()) {
			try {
				int valor = objeto.obtener(clave).comoEntero();
				total += Math.max(0, valor);
			} catch (RuntimeException ignorado) {
				// La propiedad no es un entero; no participa en las estadisticas.
			}
		}

		return total;
	}

	private static boolean contieneClave(Json.Nodo objeto, String clave) {
		if (!esObjetoReal(objeto) || clave == null) {
			return false;
		}

		try {
			return objeto.claves().contains(clave);
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * JsonGson considera JsonNull compatible con esObjeto() para poder convertir un
	 * nodo obtenido en objeto al escribir sobre el. Aqui necesitamos distinguir un
	 * objeto JSON real de un valor nulo recibido desde una API.
	 */
	private static boolean esObjetoReal(Json.Nodo nodo) {
		if (nodo == null) {
			return false;
		}

		try {
			if (!nodo.esObjeto()) {
				return false;
			}

			return nodo.comoCadena() != null;
		} catch (RuntimeException e) {
			return false;
		}
	}

	public static IOException errorHttp(ProveedorEscaneoNube proveedor, int codigo, String cuerpo) {
		if (codigo == HttpURLConnection.HTTP_UNAUTHORIZED || codigo == HttpURLConnection.HTTP_FORBIDDEN) {
			return new IOException(MonitorDePID.idioma.escanerNubeErrorClaveRechazada(proveedor.nombreVisible()));
		}
		if (codigo == 429) {
			return new IOException(MonitorDePID.idioma.escanerNubeErrorLimite(proveedor.nombreVisible()));
		}

		String detalle = limpiarDetalleServidor(cuerpo);
		return new IOException(MonitorDePID.idioma.escanerNubeErrorHttp(proveedor.nombreVisible(), codigo, detalle));
	}

	public static String limpiarDetalleServidor(String cuerpo) {
		if (cuerpo == null) {
			return "";
		}
		String limpio = cuerpo.replace('\r', ' ').replace('\n', ' ').trim();
		if (limpio.length() > LIMITE_TEXTO_ERROR) {
			return limpio.substring(0, LIMITE_TEXTO_ERROR);
		}
		return limpio;
	}

	public static String codificarSegmentoRuta(String valor) throws IOException {
		return URLEncoder.encode(valor, StandardCharsets.UTF_8.name()).replace("+", "%20");
	}

	public static String nombreSeguroParaCabecera(File archivo) {
		String nombre = archivo == null ? "" : archivo.getName();
		StringBuilder seguro = new StringBuilder(nombre.length());

		for (int i = 0; i < nombre.length(); i++) {
			char caracter = nombre.charAt(i);
			if (caracter >= 32 && caracter <= 126 && caracter != '"' && caracter != '\\') {
				seguro.append(caracter);
			} else {
				seguro.append('_');
			}
		}

		return seguro.toString();
	}

	public static void dormir(long milisegundos, AtomicBoolean cancelado) throws InterruptedException {
		long restante = milisegundos;
		while (restante > 0L) {
			comprobarCancelacion(cancelado);
			long tramo = Math.min(restante, 500L);
			Thread.sleep(tramo);
			restante -= tramo;
		}
	}

	public static void comprobarCancelacion(AtomicBoolean cancelado) {
		if (Thread.currentThread().isInterrupted() || (cancelado != null && cancelado.get())) {
			throw new CancellationException(MonitorDePID.idioma.escanerNubeEstadoCancelado());
		}
	}

	public static byte[] bytesUtf8(String texto) {
		return texto.getBytes(StandardCharsets.UTF_8);
	}

	public static final class RespuestaHttp {
		public final int codigo;
		public final String cuerpo;

		public RespuestaHttp(int codigo, String cuerpo) {
			this.codigo = codigo;
			this.cuerpo = cuerpo == null ? "" : cuerpo;
		}

		public boolean esCorrecta() {
			return codigo >= 200 && codigo < 300;
		}
	}
}
