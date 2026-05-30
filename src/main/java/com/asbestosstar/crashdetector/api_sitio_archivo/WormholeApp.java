package com.asbestosstar.crashdetector.api_sitio_archivo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.api_sitio_archivo.wormholeapp.CifradorDeFlujo;
import com.asbestosstar.crashdetector.api_sitio_archivo.wormholeapp.CriptoWormhole;
import com.asbestosstar.crashdetector.api_sitio_archivo.wormholeapp.HasheadorDePiezas;
import com.asbestosstar.crashdetector.api_sitio_archivo.wormholeapp.TorrentWormhole;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;

/**
 * Implementación real de wormhole.app usando el flujo web del CLI en Rust.
 *
 * Versión compatible con Java 8 y sin dependencias externas.
 *
 * No usa java.net.http.*. En su lugar usa un cliente HTTPS mínimo hecho sobre
 * SSLSocket para poder enviar métodos HTTP reales como PATCH.
 */
public class WormholeApp implements SitioDeArchivoAPI {

	private static final long GIB = 1024L * 1024L * 1024L;
	private static final long LIMITE_SUBIDA_NORMAL = 5L * GIB;
	private static final long LIMITE_MAXIMO = 10L * GIB;

	private static final String URL_WORMHOLE = "https://wormhole.app";
	private static final String API_BASE = "https://wormhole.app/api";
	private static final String USER_AGENT = Statics.nombre_cd.obtener() + "-Wormhole/1.0";

	/** Longitud de claves y sales en bytes. */
	private static final int LONGITUD_CLAVE = 16;

	/** Tamaño de registro para el cifrado RFC 8188. */
	private static final int TAMANO_REGISTRO = 64 * 1024;

	/** Cantidad máxima de autorizaciones de subida solicitadas. */
	private static final int SUBIDAS_PARALELAS = 10;

	private final SecureRandom aleatorio = new SecureRandom();
	private final ClienteHttpSimple clienteHttp = new ClienteHttpSimple();

	@Override
	public String nombre() {
		return "wormhole.app";
	}

	@Override
	public List<String> sitiosPorDefecto() {
		return Collections.singletonList(URL_WORMHOLE);
	}

	@Override
	public PoliticaDeRetencion politicaGeneral() {
		return PoliticaDeRetencion.VARIABLE;
	}

	@Override
	public void validarArchivoZip(Path archivoZip) throws ErrorDeArchivo, ArchivoDemasiadoGrande, ServicioNoSoportado {
		Objects.requireNonNull(archivoZip, "archivoZip no puede ser null");

		if (!Files.exists(archivoZip)) {
			throw new ErrorDeArchivo("El archivo ZIP no existe: " + archivoZip);
		}

		if (!Files.isRegularFile(archivoZip)) {
			throw new ErrorDeArchivo("La ruta no corresponde a un archivo normal: " + archivoZip);
		}

		String nombre = archivoZip.getFileName() != null ? archivoZip.getFileName().toString() : "";
		if (!nombre.toLowerCase().endsWith(".zip")) {
			throw new ErrorDeArchivo("Solo se admite un único archivo .zip");
		}

		final long tamano;
		try {
			tamano = Files.size(archivoZip);
		} catch (IOException e) {
			throw new ErrorDeArchivo("No se pudo leer el tamaño del archivo: " + archivoZip, e);
		}

		if (tamano <= 0) {
			throw new ErrorDeArchivo("El archivo ZIP está vacío");
		}

		if (tamano > LIMITE_MAXIMO) {
			throw new ArchivoDemasiadoGrande(tamano, LIMITE_MAXIMO);
		}
	}

	@Override
	public SesionDeTransferencia publicarArchivoZip(Path archivoZip, ObservadorDeTransferencia observador)
			throws ErrorDeArchivo, ArchivoDemasiadoGrande, ErrorConPublicar, ServicioNoSoportado {

		validarArchivoZip(archivoZip);

		final long tamano;
		try {
			tamano = Files.size(archivoZip);
		} catch (IOException e) {
			throw new ErrorDeArchivo("No se pudo leer el tamaño del archivo: " + archivoZip, e);
		}

		final boolean requiereSesionActiva = tamano > LIMITE_SUBIDA_NORMAL;
		final ModoDeTransferencia modo = requiereSesionActiva ? ModoDeTransferencia.TRANSFERENCIA_CON_EMISOR_ACTIVO
				: ModoDeTransferencia.SUBIDA_REMOTA;

		final SesionWormhole sesion = new SesionWormhole(archivoZip, tamano, modo, requiereSesionActiva);

		ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
			Thread t = new Thread(r, "wormhole-subida-" + sesion.id());
			t.setDaemon(true);
			return t;
		});

		executor.submit(() -> {
			try {
				subirArchivoZipReal(archivoZip, sesion, observador);
			} catch (Throwable t) {
				sesion.marcarComoError("Error al publicar en wormhole.app", t);

				if (observador != null) {
					observador.alCambiarEstado(EstadoDeTransferencia.ERROR);
					observador.alError(t.getMessage(), t);
				}
			} finally {
				executor.shutdown();
			}
		});

		return sesion;
	}

	/**
	 * Flujo real de subida:
	 *
	 * 1. Crear claves y tokens 2. Crear room 3. Marcar uploader como online 4.
	 * Primera pasada: cifrar + calcular hashes de piezas 5. Generar y cifrar
	 * torrent 6. Actualizar metadata de la room 7. Obtener tokens de subida B2 8.
	 * Segunda pasada: volver a cifrar con la misma sal y subir por chunks 9.
	 * Finalizar subida
	 */
	private void subirArchivoZipReal(Path archivoZip, SesionWormhole sesion, ObservadorDeTransferencia observador)
			throws Exception {

		sesion.setEstado(EstadoDeTransferencia.CONECTANDO);
		if (observador != null) {
			observador.alCambiarEstado(EstadoDeTransferencia.CONECTANDO);
			observador.alConocerTamanoTotal(sesion.tamanoBytes());
		}

		String nombreArchivo = archivoZip.getFileName().toString();

		byte[] claveMaestra = bytesAleatorios(LONGITUD_CLAVE);
		byte[] salSala = bytesAleatorios(LONGITUD_CLAVE);

		byte[] tokenLector = CriptoWormhole.derivarTokenAutenticacion(claveMaestra, salSala);
		String tokenLectorBase64 = Base64.getEncoder().encodeToString(tokenLector);
		String salSalaBase64 = Base64.getEncoder().encodeToString(salSala);

		RespuestaCrearSala sala = crearSala(tokenLectorBase64, salSalaBase64);
		byte[] tokenEscritor = Base64.getDecoder().decode(sala.writerToken);

		marcarUploaderEnLinea(sala.id, tokenEscritor);

		sesion.setEstado(EstadoDeTransferencia.SUBIENDO);
		if (observador != null) {
			observador.alCambiarEstado(EstadoDeTransferencia.SUBIENDO);
		}

		int tamanoCifradoEstimado = estimarTamanoCifrado(Files.size(archivoZip));
		int tamanoPieza = TorrentWormhole.calcularTamanoDePieza(tamanoCifradoEstimado);

		CifradorDeFlujo cifradorPrimeraPasada = CifradorDeFlujo.nuevoAleatorio(claveMaestra, TAMANO_REGISTRO);

		byte[] salArchivo = cifradorPrimeraPasada.sal();

		HasheadorDePiezas hasheador = new HasheadorDePiezas(tamanoPieza);
		long tamanoCifradoReal = 0;

		try (InputStream in = new BufferedInputStream(Files.newInputStream(archivoZip))) {
			byte[] buffer = new byte[256 * 1024];
			int leidos;

			while ((leidos = in.read(buffer)) >= 0) {
				if (leidos == 0) {
					continue;
				}

				byte[] chunkCifrado = cifradorPrimeraPasada.actualizar(buffer, 0, leidos);
				if (chunkCifrado.length > 0) {
					hasheador.actualizar(chunkCifrado);
					tamanoCifradoReal += chunkCifrado.length;
				}
			}
		}

		byte[] finalCifrado = cifradorPrimeraPasada.finalizar();
		if (finalCifrado.length > 0) {
			hasheador.actualizar(finalCifrado);
			tamanoCifradoReal += finalCifrado.length;
		}

		final long tamanoCifradoFinal = tamanoCifradoReal;

		List<byte[]> hashesDePiezas = hasheador.finalizar();

		TorrentWormhole.ResultadoConstruccionTorrent torrent = TorrentWormhole.crearTorrentArchivoUnicoDesdeHashes(
				nombreArchivo, Math.toIntExact(tamanoCifradoFinal), tamanoPieza, hashesDePiezas);

		byte[] claveMeta = CriptoWormhole.derivarClaveMeta(claveMaestra, salSala);
		byte[] torrentCifrado = CriptoWormhole.cifrarMetadata(torrent.bytesTorrent(), claveMeta);
		String torrentCifradoBase64 = Base64.getEncoder().encodeToString(torrentCifrado);

		RespuestaSala salaActualizada = actualizarMetadataSala(sala.id, tokenEscritor, torrent.infoHash(),
				torrentCifradoBase64, false, TorrentWormhole.redondearTamanoEnMb(Math.toIntExact(tamanoCifradoFinal)));

		int cantidadChunks = divisionHaciaArriba(Math.toIntExact(tamanoCifradoFinal), tamanoPieza);

		List<TokenSubidaB2> tokensSubida = obtenerAutorizacionSubidaB2(sala.id, tokenEscritor,
				Math.min(SUBIDAS_PARALELAS, cantidadChunks));

		String urlCompartible = URL_WORMHOLE + "/" + sala.id + "#"
				+ Base64.getUrlEncoder().withoutPadding().encodeToString(claveMaestra);

		sesion.setCodigo(sala.id);
		sesion.setEnlace(urlCompartible);

		if (observador != null) {
			observador.alRecibirCodigo(sala.id);
			observador.alRecibirEnlace(urlCompartible);
		}

		AtomicLong bytesEnviados = new AtomicLong(0);

		try (InputStream in = new BufferedInputStream(Files.newInputStream(archivoZip))) {
			CifradorDeFlujo cifradorSegundaPasada = CifradorDeFlujo.conSal(claveMaestra, TAMANO_REGISTRO, salArchivo);

			AcumuladorDeChunks acumulador = new AcumuladorDeChunks(tamanoPieza);
			int indiceToken = 0;

			byte[] buffer = new byte[256 * 1024];
			int leidos;

			while ((leidos = in.read(buffer)) >= 0) {
				if (leidos == 0) {
					continue;
				}

				byte[] chunkCifrado = cifradorSegundaPasada.actualizar(buffer, 0, leidos);
				for (byte[] pieza : acumulador.agregar(chunkCifrado)) {
					int indiceChunk = acumulador.siguienteIndiceChunk();
					TokenSubidaB2 token = tokensSubida.get(indiceToken % tokensSubida.size());
					indiceToken++;

					subirAB2(token, sala.id, indiceChunk, pieza, bytes -> {
						long enviados = bytesEnviados.addAndGet(bytes);
						if (observador != null) {
							observador.alActualizarProgreso(enviados, tamanoCifradoFinal);
						}
					});
				}
			}

			byte[] cola = cifradorSegundaPasada.finalizar();

			for (byte[] pieza : acumulador.agregar(cola)) {
				int indiceChunk = acumulador.siguienteIndiceChunk();
				TokenSubidaB2 token = tokensSubida.get(indiceToken % tokensSubida.size());
				indiceToken++;

				subirAB2(token, sala.id, indiceChunk, pieza, bytes -> {
					long enviados = bytesEnviados.addAndGet(bytes);
					if (observador != null) {
						observador.alActualizarProgreso(enviados, tamanoCifradoFinal);
					}
				});
			}

			for (byte[] pieza : acumulador.finalizar()) {
				int indiceChunk = acumulador.siguienteIndiceChunk();
				TokenSubidaB2 token = tokensSubida.get(indiceToken % tokensSubida.size());
				indiceToken++;

				subirAB2(token, sala.id, indiceChunk, pieza, bytes -> {
					long enviados = bytesEnviados.addAndGet(bytes);
					if (observador != null) {
						observador.alActualizarProgreso(enviados, tamanoCifradoFinal);
					}
				});
			}
		}

		finalizarSubida(sala.id, tokenEscritor);

		if (salaActualizada != null && "complete".equalsIgnoreCase(salaActualizada.cloudState)) {
			sesion.setEstado(EstadoDeTransferencia.FINALIZADA);
		} else if (sesion.requiereMantenerSesionAbierta()) {
			sesion.setEstado(EstadoDeTransferencia.ESPERANDO_DESCARGA);
		} else {
			sesion.setEstado(EstadoDeTransferencia.FINALIZADA);
		}

		if (sesion.requiereMantenerSesionAbierta()) {
			if (observador != null) {
				observador.alCambiarEstado(EstadoDeTransferencia.ESPERANDO_DESCARGA);
			}
		} else {
			sesion.marcarComoFinalizada();
			if (observador != null) {
				observador.alCambiarEstado(EstadoDeTransferencia.FINALIZADA);
				observador.alFinalizar(sesion);
			}
		}
	}

	private byte[] bytesAleatorios(int n) {
		byte[] v = new byte[n];
		aleatorio.nextBytes(v);
		return v;
	}

	private int estimarTamanoCifrado(long tamanoPlano) {
		long estimado = tamanoPlano + (tamanoPlano / 10L);
		if (estimado > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Archivo demasiado grande para representarlo en int");
		}
		return (int) estimado;
	}

	private int divisionHaciaArriba(int a, int b) {
		return (a + b - 1) / b;
	}

	private String authBearer(byte[] token) {
		return "Bearer sync-v1 " + Base64.getEncoder().encodeToString(token);
	}

	private void verificar2xx(RespuestaHttp res, String mensaje) throws ErrorConPublicar {
		if (res.codigoEstado < 200 || res.codigoEstado >= 300) {
			throw new ErrorConPublicar(mensaje + ": HTTP " + res.codigoEstado + " -> " + res.cuerpo);
		}
	}

	private RespuestaCrearSala crearSala(String tokenLectorBase64, String salBase64) throws Exception {
		Nodo req = Json.crearObjeto();
		req.obtener("readerToken").poner(tokenLectorBase64);
		req.obtener("salt").poner(salBase64);

		RespuestaHttp res = clienteHttp.enviarJson("POST", API_BASE + "/room",
				cabeceras("Content-Type", "application/json", "User-Agent", USER_AGENT), req.escribir());

		verificar2xx(res, "Falló la creación de la sala");

		Nodo jsonRes = Json.leer(res.cuerpo);

		RespuestaCrearSala r = new RespuestaCrearSala();
		r.id = leerCadena(jsonRes, "id");
		r.writerToken = leerCadena(jsonRes, "writerToken");
		r.expiresAtTimestampMs = leerLargoNullable(jsonRes, "expiresAtTimestampMs");
		r.maxDownloads = leerEnteroNullable(jsonRes, "maxDownloads");
		r.lifetime = leerLargoNullable(jsonRes, "lifetime");
		return r;
	}

	private void marcarUploaderEnLinea(String idSala, byte[] tokenEscritor) throws Exception {
		RespuestaHttp res = clienteHttp.enviar("PATCH", API_BASE + "/room/" + idSala + "/uploader-online",
				cabeceras("Authorization", authBearer(tokenEscritor), "User-Agent", USER_AGENT), new byte[0]);

		verificar2xx(res, "Falló marcar uploader-online");
	}

	private RespuestaSala actualizarMetadataSala(String idSala, byte[] tokenEscritor, String infoHash,
			String torrentCifradoBase64, boolean multiFile, int sizeMb) throws Exception {

		Nodo body = Json.crearObjeto();
		body.obtener("infoHash").poner(infoHash);
		body.obtener("encryptedTorrentFile").poner(torrentCifradoBase64);
		body.obtener("multiFile").poner(multiFile);
		body.obtener("sizeMb").poner(sizeMb);

		RespuestaHttp res = clienteHttp.enviarJson("PATCH", API_BASE + "/room/" + idSala,
				cabeceras("Authorization", authBearer(tokenEscritor), "Content-Type", "application/json", "Accept",
						"application/json", "Origin", "https://wormhole.app", "Referer", "https://wormhole.app/",
						"User-Agent", USER_AGENT),
				body.escribir());

		verificar2xx(res, "Falló la actualización de metadata de la sala");

		Nodo jsonRes = Json.leer(res.cuerpo);

		RespuestaSala r = new RespuestaSala();
		r.cloudState = leerCadenaNullable(jsonRes, "cloudState");
		r.multiFile = leerBooleanoNullable(jsonRes, "multiFile");
		r.remainingDownloads = leerEnteroNullable(jsonRes, "remainingDownloads");
		r.encryptedTorrentFile = leerCadenaNullable(jsonRes, "encryptedTorrentFile");
		return r;
	}

	private List<TokenSubidaB2> obtenerAutorizacionSubidaB2(String idSala, byte[] tokenEscritor, int cantidadTokens)
			throws Exception {

		Nodo body = Json.crearObjeto();
		body.obtener("numTokens").poner(cantidadTokens);

		RespuestaHttp res = clienteHttp.enviarJson(
				"POST", API_BASE + "/room/" + idSala + "/b2/auth-upload", cabeceras("Authorization",
						authBearer(tokenEscritor), "Content-Type", "application/json", "User-Agent", USER_AGENT),
				body.escribir());

		verificar2xx(res, "Falló obtener autorización de subida B2");

		Nodo jsonRes = Json.leer(res.cuerpo);
		List<TokenSubidaB2> ret = new ArrayList<TokenSubidaB2>();

		if (!jsonRes.esArreglo()) {
			throw new ErrorConPublicar("La respuesta de auth-upload no fue un arreglo JSON");
		}

		for (int i = 0; i < jsonRes.tamano(); i++) {
			Nodo item = jsonRes.en(i);

			TokenSubidaB2 token = new TokenSubidaB2();
			token.uploadUrl = leerCadena(item, "uploadUrl");
			token.authorizationToken = leerCadena(item, "authorizationToken");
			ret.add(token);
		}

		if (ret.isEmpty()) {
			throw new ErrorConPublicar("No se recibieron tokens de subida B2");
		}

		return ret;
	}

	private void subirAB2(TokenSubidaB2 token, String idSala, int indiceChunk, byte[] data, CallbackProgreso callback)
			throws Exception {

		String sha1Hex = CriptoWormhole.sha1Hex(data);

		RespuestaHttp res = clienteHttp.enviar("POST", token.uploadUrl,
				cabeceras("Authorization", token.authorizationToken, "X-Bz-File-Name", idSala + "/" + indiceChunk,
						"X-Bz-Content-Sha1", sha1Hex, "Content-Type", "application/octet-stream", "User-Agent",
						USER_AGENT),
				data);

		verificar2xx(res, "Falló la subida del chunk a B2");

		if (callback != null) {
			callback.alEnviar(data.length);
		}
	}

	private void finalizarSubida(String idSala, byte[] tokenEscritor) throws Exception {
		Nodo body = Json.crearObjeto();
		body.obtener("success").poner(true);

		RespuestaHttp res = clienteHttp.enviarJson(
				"POST", API_BASE + "/room/" + idSala + "/b2/finish-upload", cabeceras("Authorization",
						authBearer(tokenEscritor), "Content-Type", "application/json", "User-Agent", USER_AGENT),
				body.escribir());

		verificar2xx(res, "Falló finalizar la subida");
	}

	private String leerCadena(Nodo objeto, String campo) throws ErrorConPublicar {
		try {
			String v = objeto.obtener(campo).comoCadena();
			if (v == null) {
				throw new ErrorConPublicar("Campo JSON requerido ausente o null: " + campo);
			}
			return v;
		} catch (Throwable t) {
			if (t instanceof ErrorConPublicar) {
				throw (ErrorConPublicar) t;
			}
			throw new ErrorConPublicar("No se pudo leer el campo JSON requerido: " + campo, t);
		}
	}

	private String leerCadenaNullable(Nodo objeto, String campo) {
		try {
			return objeto.obtener(campo).comoCadena();
		} catch (Throwable t) {
			return null;
		}
	}

	private Integer leerEnteroNullable(Nodo objeto, String campo) {
		try {
			return Integer.valueOf(objeto.obtener(campo).comoEntero());
		} catch (Throwable t) {
			return null;
		}
	}

	private Long leerLargoNullable(Nodo objeto, String campo) {
		try {
			return Long.valueOf(objeto.obtener(campo).comoLargo());
		} catch (Throwable t) {
			return null;
		}
	}

	private Boolean leerBooleanoNullable(Nodo objeto, String campo) {
		try {
			return Boolean.valueOf(objeto.obtener(campo).comoBooleano());
		} catch (Throwable t) {
			return null;
		}
	}

	private List<String[]> cabeceras(String... pares) {
		List<String[]> lista = new ArrayList<String[]>();

		if (pares == null) {
			return lista;
		}

		for (int i = 0; i + 1 < pares.length; i += 2) {
			lista.add(new String[] { pares[i], pares[i + 1] });
		}

		return lista;
	}

	@FunctionalInterface
	private interface CallbackProgreso {
		void alEnviar(int bytes);
	}

	/**
	 * Acumula bytes cifrados y los parte en chunks del tamaño de pieza requerido.
	 */
	private static final class AcumuladorDeChunks {
		private final int tamanoChunk;
		private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		private int siguienteIndiceChunk = 0;

		private AcumuladorDeChunks(int tamanoChunk) {
			this.tamanoChunk = tamanoChunk;
		}

		List<byte[]> agregar(byte[] data) {
			if (data == null || data.length == 0) {
				return Collections.emptyList();
			}

			try {
				buffer.write(data);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}

			List<byte[]> salida = new ArrayList<byte[]>();

			while (buffer.size() >= tamanoChunk) {
				byte[] todo = buffer.toByteArray();
				byte[] chunk = java.util.Arrays.copyOfRange(todo, 0, tamanoChunk);
				salida.add(chunk);

				buffer.reset();
				buffer.write(todo, tamanoChunk, todo.length - tamanoChunk);
			}

			return salida;
		}

		List<byte[]> finalizar() {
			if (buffer.size() == 0) {
				return Collections.emptyList();
			}

			byte[] resto = buffer.toByteArray();
			buffer.reset();

			return Collections.singletonList(resto);
		}

		int siguienteIndiceChunk() {
			return siguienteIndiceChunk++;
		}
	}

	private static final class RespuestaHttp {
		public final int codigoEstado;
		public final String cuerpo;

		private RespuestaHttp(int codigoEstado, String cuerpo) {
			this.codigoEstado = codigoEstado;
			this.cuerpo = cuerpo;
		}
	}

	private static final class CabeceraHttp {
		public final String nombre;
		public final String valor;

		private CabeceraHttp(String nombre, String valor) {
			this.nombre = nombre;
			this.valor = valor;
		}
	}

	/**
	 * Cliente HTTPS mínimo, suficiente para este flujo.
	 *
	 * Soporta: - POST - PATCH real - Content-Length - chunked transfer encoding -
	 * cierre por EOF
	 *
	 * Solo maneja HTTPS.
	 */
	private static final class ClienteHttpSimple {

		private static final int TIMEOUT_MS = 30000;
		private static final String CRLF = "\r\n";

		RespuestaHttp enviarJson(String metodo, String url, List<String[]> cabeceras, String cuerpoJson)
				throws IOException {

			byte[] cuerpo = cuerpoJson != null ? cuerpoJson.getBytes(StandardCharsets.UTF_8) : null;
			return enviar(metodo, url, cabeceras, cuerpo);
		}

		RespuestaHttp enviar(String metodo, String url, List<String[]> cabeceras, byte[] cuerpo) throws IOException {

			URI uri = URI.create(url);

			if (!"https".equalsIgnoreCase(uri.getScheme())) {
				throw new IOException("Solo se soporta HTTPS: " + url);
			}

			String host = uri.getHost();
			int puerto = uri.getPort() > 0 ? uri.getPort() : 443;
			String ruta = construirRuta(uri);

			SSLSocketFactory fabrica = (SSLSocketFactory) SSLSocketFactory.getDefault();

			try (SSLSocket socket = (SSLSocket) fabrica.createSocket(host, puerto)) {
				socket.setSoTimeout(TIMEOUT_MS);
				socket.startHandshake();

				OutputStream out = socket.getOutputStream();
				InputStream in = socket.getInputStream();

				escribirSolicitud(out, metodo, host, ruta, cabeceras, cuerpo);
				out.flush();

				return leerRespuesta(in);
			}
		}

		private static String construirRuta(URI uri) {
			String path = uri.getRawPath();
			if (path == null || path.isEmpty()) {
				path = "/";
			}

			String query = uri.getRawQuery();
			if (query != null && !query.isEmpty()) {
				path += "?" + query;
			}

			return path;
		}

		private void escribirSolicitud(OutputStream out, String metodo, String host, String ruta,
				List<String[]> cabeceras, byte[] cuerpo) throws IOException {

			StringBuilder sb = new StringBuilder();
			sb.append(metodo).append(" ").append(ruta).append(" HTTP/1.1").append(CRLF);
			sb.append("Host: ").append(host).append(CRLF);
			sb.append("User-Agent: ").append(USER_AGENT).append(CRLF);
			sb.append("Connection: close").append(CRLF);

			boolean tieneContentLength = false;
			boolean tieneHost = false;
			boolean tieneConnection = false;
			boolean tieneUserAgent = false;

			if (cabeceras != null) {
				for (String[] cabecera : cabeceras) {
					if (cabecera == null || cabecera.length < 2) {
						continue;
					}

					String nombre = cabecera[0];
					String valor = cabecera[1];

					if (nombre == null || valor == null) {
						continue;
					}

					String nombreNormalizado = nombre.toLowerCase(Locale.ROOT);
					if ("content-length".equals(nombreNormalizado)) {
						tieneContentLength = true;
					} else if ("host".equals(nombreNormalizado)) {
						tieneHost = true;
					} else if ("connection".equals(nombreNormalizado)) {
						tieneConnection = true;
					} else if ("user-agent".equals(nombreNormalizado)) {
						tieneUserAgent = true;
					}
				}
			}

			StringBuilder finales = new StringBuilder();

			if (cabeceras != null) {
				for (String[] cabecera : cabeceras) {
					if (cabecera == null || cabecera.length < 2) {
						continue;
					}

					String nombre = cabecera[0];
					String valor = cabecera[1];

					if (nombre == null || valor == null) {
						continue;
					}

					String nombreNormalizado = nombre.toLowerCase(Locale.ROOT);

					if ("host".equals(nombreNormalizado) || "connection".equals(nombreNormalizado)
							|| "user-agent".equals(nombreNormalizado)) {
						continue;
					}

					finales.append(nombre).append(": ").append(valor).append(CRLF);
				}
			}

			StringBuilder solicitud = new StringBuilder();
			solicitud.append(metodo).append(" ").append(ruta).append(" HTTP/1.1").append(CRLF);

			if (!tieneHost) {
				solicitud.append("Host: ").append(host).append(CRLF);
			}
			if (!tieneUserAgent) {
				solicitud.append("User-Agent: ").append(USER_AGENT).append(CRLF);
			}
			if (!tieneConnection) {
				solicitud.append("Connection: close").append(CRLF);
			}

			solicitud.append(finales);

			int longitud = cuerpo != null ? cuerpo.length : 0;
			if (!tieneContentLength) {
				solicitud.append("Content-Length: ").append(longitud).append(CRLF);
			}

			solicitud.append(CRLF);

			out.write(solicitud.toString().getBytes(StandardCharsets.UTF_8));

			if (cuerpo != null && cuerpo.length > 0) {
				out.write(cuerpo);
			}
		}

		private RespuestaHttp leerRespuesta(InputStream in) throws IOException {
			String lineaEstado = leerLineaAscii(in);
			if (lineaEstado == null || lineaEstado.isEmpty()) {
				throw new EOFException("No se recibió línea de estado HTTP");
			}

			String[] partes = lineaEstado.split(" ", 3);
			if (partes.length < 2) {
				throw new IOException("Línea de estado HTTP inválida: " + lineaEstado);
			}

			int codigoEstado;
			try {
				codigoEstado = Integer.parseInt(partes[1]);
			} catch (NumberFormatException e) {
				throw new IOException("Código HTTP inválido: " + lineaEstado, e);
			}

			List<CabeceraHttp> cabeceras = new ArrayList<CabeceraHttp>();

			while (true) {
				String linea = leerLineaAscii(in);
				if (linea == null) {
					throw new EOFException("Fin inesperado al leer cabeceras HTTP");
				}
				if (linea.isEmpty()) {
					break;
				}

				int idx = linea.indexOf(':');
				if (idx <= 0) {
					continue;
				}

				String nombre = linea.substring(0, idx).trim();
				String valor = linea.substring(idx + 1).trim();
				cabeceras.add(new CabeceraHttp(nombre, valor));
			}

			byte[] cuerpo = leerCuerpoSegunCabeceras(in, cabeceras);
			String cuerpoTexto = new String(cuerpo, StandardCharsets.UTF_8);

			return new RespuestaHttp(codigoEstado, cuerpoTexto);
		}

		private byte[] leerCuerpoSegunCabeceras(InputStream in, List<CabeceraHttp> cabeceras) throws IOException {
			String transferEncoding = obtenerCabecera(cabeceras, "Transfer-Encoding");
			if (transferEncoding != null && transferEncoding.toLowerCase(Locale.ROOT).contains("chunked")) {
				return leerChunked(in);
			}

			String contentLength = obtenerCabecera(cabeceras, "Content-Length");
			if (contentLength != null) {
				int longitud;
				try {
					longitud = Integer.parseInt(contentLength.trim());
				} catch (NumberFormatException e) {
					throw new IOException("Content-Length inválido: " + contentLength, e);
				}

				return leerBytesExactos(in, longitud);
			}

			return leerHastaEof(in);
		}

		private static String obtenerCabecera(List<CabeceraHttp> cabeceras, String nombreBuscado) {
			for (CabeceraHttp h : cabeceras) {
				if (h.nombre.equalsIgnoreCase(nombreBuscado)) {
					return h.valor;
				}
			}
			return null;
		}

		private byte[] leerChunked(InputStream in) throws IOException {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			while (true) {
				String lineaTamano = leerLineaAscii(in);
				if (lineaTamano == null) {
					throw new EOFException("Fin inesperado leyendo chunk size");
				}

				lineaTamano = lineaTamano.trim();
				int puntoYComa = lineaTamano.indexOf(';');
				if (puntoYComa >= 0) {
					lineaTamano = lineaTamano.substring(0, puntoYComa).trim();
				}

				int tamanoChunk;
				try {
					tamanoChunk = Integer.parseInt(lineaTamano, 16);
				} catch (NumberFormatException e) {
					throw new IOException("Chunk size inválido: " + lineaTamano, e);
				}

				if (tamanoChunk == 0) {
					/*
					 * Después del chunk 0 vienen posibles trailing headers y una línea vacía final.
					 */
					while (true) {
						String trailing = leerLineaAscii(in);
						if (trailing == null || trailing.isEmpty()) {
							break;
						}
					}
					break;
				}

				byte[] chunk = leerBytesExactos(in, tamanoChunk);
				out.write(chunk);

				/*
				 * Después de cada chunk debe venir CRLF.
				 */
				String finChunk = leerLineaAscii(in);
				if (finChunk == null) {
					throw new EOFException("Fin inesperado después de chunk");
				}
			}

			return out.toByteArray();
		}

		private byte[] leerHastaEof(InputStream in) throws IOException {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int leidos;

			while ((leidos = in.read(buffer)) != -1) {
				out.write(buffer, 0, leidos);
			}

			return out.toByteArray();
		}

		private byte[] leerBytesExactos(InputStream in, int cantidad) throws IOException {
			byte[] datos = new byte[cantidad];
			int offset = 0;

			while (offset < cantidad) {
				int leidos = in.read(datos, offset, cantidad - offset);
				if (leidos < 0) {
					throw new EOFException("Fin inesperado al leer " + cantidad + " bytes");
				}
				offset += leidos;
			}

			return datos;
		}

		private String leerLineaAscii(InputStream in) throws IOException {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			while (true) {
				int b = in.read();
				if (b < 0) {
					if (out.size() == 0) {
						return null;
					}
					break;
				}

				if (b == '\n') {
					break;
				}

				if (b != '\r') {
					out.write(b);
				}
			}

			return new String(out.toByteArray(), StandardCharsets.ISO_8859_1);
		}
	}

	private static class RespuestaCrearSala {
		public String id;
		public String writerToken;
		public Long expiresAtTimestampMs;
		public Integer maxDownloads;
		public Long lifetime;
	}

	private static class RespuestaSala {
		public String cloudState;
		public Boolean multiFile;
		public Integer remainingDownloads;
		public String encryptedTorrentFile;
	}

	private static class TokenSubidaB2 {
		public String uploadUrl;
		public String authorizationToken;
	}

	/**
	 * Representa una sesión real de publicación en Wormhole.
	 */
	private static final class SesionWormhole implements SesionDeTransferencia {
		private final String id;
		private final Path archivoZip;
		private final long tamanoBytes;
		private final ModoDeTransferencia modo;
		private final boolean requiereMantenerSesionAbierta;
		private final Instant creadaEn;

		private final CountDownLatch latchFinalizacion;
		private final AtomicBoolean cancelada;
		private final AtomicReference<EstadoDeTransferencia> estado;
		private final AtomicReference<String> enlace;
		private final AtomicReference<String> codigo;
		private final AtomicReference<ErrorConPublicar> errorFinal;

		private SesionWormhole(Path archivoZip, long tamanoBytes, ModoDeTransferencia modo,
				boolean requiereMantenerSesionAbierta) {

			this.id = UUID.randomUUID().toString();
			this.archivoZip = archivoZip;
			this.tamanoBytes = tamanoBytes;
			this.modo = modo;
			this.requiereMantenerSesionAbierta = requiereMantenerSesionAbierta;
			this.creadaEn = Instant.now();

			this.latchFinalizacion = new CountDownLatch(1);
			this.cancelada = new AtomicBoolean(false);
			this.estado = new AtomicReference<EstadoDeTransferencia>(EstadoDeTransferencia.PENDIENTE);
			this.enlace = new AtomicReference<String>(null);
			this.codigo = new AtomicReference<String>(null);
			this.errorFinal = new AtomicReference<ErrorConPublicar>(null);
		}

		@Override
		public String id() {
			return id;
		}

		@Override
		public String nombreServicio() {
			return "wormhole.app";
		}

		@Override
		public Path archivoZip() {
			return archivoZip;
		}

		@Override
		public long tamanoBytes() {
			return tamanoBytes;
		}

		@Override
		public ModoDeTransferencia modo() {
			return modo;
		}

		@Override
		public EstadoDeTransferencia estado() {
			return estado.get();
		}

		@Override
		public String enlace() {
			return enlace.get();
		}

		@Override
		public String codigo() {
			return codigo.get();
		}

		@Override
		public Instant creadaEn() {
			return creadaEn;
		}

		@Override
		public boolean requiereMantenerSesionAbierta() {
			return requiereMantenerSesionAbierta;
		}

		@Override
		public void esperarFinalizacion() throws InterruptedException, ErrorConPublicar {
			latchFinalizacion.await();

			ErrorConPublicar error = errorFinal.get();
			if (error != null) {
				throw error;
			}
		}

		@Override
		public void cancelar() {
			if (cancelada.compareAndSet(false, true)) {
				estado.set(EstadoDeTransferencia.CANCELADA);
				latchFinalizacion.countDown();
			}
		}

		@Override
		public void close() {
			cancelar();
		}

		private void setEstado(EstadoDeTransferencia nuevoEstado) {
			estado.set(nuevoEstado);
		}

		private void setEnlace(String nuevoEnlace) {
			enlace.set(nuevoEnlace);
		}

		private void setCodigo(String nuevoCodigo) {
			codigo.set(nuevoCodigo);
		}

		private void marcarComoFinalizada() {
			estado.set(EstadoDeTransferencia.FINALIZADA);
			latchFinalizacion.countDown();
		}

		private void marcarComoError(String mensaje, Throwable causa) {
			estado.set(EstadoDeTransferencia.ERROR);
			errorFinal.set(new ErrorConPublicar(mensaje, causa));
			latchFinalizacion.countDown();
		}
	}
}