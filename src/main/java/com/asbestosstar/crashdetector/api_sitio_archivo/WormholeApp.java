package com.asbestosstar.crashdetector.api_sitio_archivo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.asbestosstar.crashdetector.api_sitio_archivo.wormholeapp.CifradorDeFlujo;
import com.asbestosstar.crashdetector.api_sitio_archivo.wormholeapp.CriptoWormhole;
import com.asbestosstar.crashdetector.api_sitio_archivo.wormholeapp.HasheadorDePiezas;
import com.asbestosstar.crashdetector.api_sitio_archivo.wormholeapp.TorrentWormhole;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

/**
 * Implementación real de wormhole.app usando el flujo web del CLI en Rust.
 *
 * Esta versión no usa Jackson. Toda la serialización y lectura JSON se hace
 * mediante la clase Json del proyecto.
 */
public class WormholeApp implements SitioDeArchivoAPI {

	private static final long GIB = 1024L * 1024L * 1024L;
	private static final long LIMITE_SUBIDA_NORMAL = 5L * GIB;
	private static final long LIMITE_MAXIMO = 10L * GIB;

	private static final String URL_WORMHOLE = "https://wormhole.app";
	private static final String API_BASE = "https://wormhole.app/api";
	private static final String USER_AGENT = "CrashDetector-Wormhole/1.0";

	/** Longitud de claves y sales en bytes. */
	private static final int LONGITUD_CLAVE = 16;

	/** Tamaño de registro para el cifrado RFC 8188. */
	private static final int TAMANO_REGISTRO = 64 * 1024;

	/** Cantidad máxima de autorizaciones de subida solicitadas. */
	private static final int SUBIDAS_PARALELAS = 10;

	private final HttpClient clienteHttp = HttpClient.newBuilder().build();
	private final SecureRandom aleatorio = new SecureRandom();

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

	private void verificar2xx(HttpResponse<String> res, String mensaje) throws ErrorConPublicar {
		if (res.statusCode() < 200 || res.statusCode() >= 300) {
			throw new ErrorConPublicar(mensaje + ": HTTP " + res.statusCode() + " -> " + res.body());
		}
	}

	private RespuestaCrearSala crearSala(String tokenLectorBase64, String salBase64) throws Exception {
		Nodo req = Json.crearObjeto();
		req.obtener("readerToken").poner(tokenLectorBase64);
		req.obtener("salt").poner(salBase64);

		HttpRequest httpReq = HttpRequest.newBuilder().uri(URI.create(API_BASE + "/room"))
				.header("Content-Type", "application/json").header("User-Agent", USER_AGENT)
				.POST(HttpRequest.BodyPublishers.ofString(req.escribir())).build();

		HttpResponse<String> res = clienteHttp.send(httpReq, HttpResponse.BodyHandlers.ofString());
		verificar2xx(res, "Falló la creación de la sala");

		Nodo jsonRes = Json.leer(res.body());

		RespuestaCrearSala r = new RespuestaCrearSala();
		r.id = leerCadena(jsonRes, "id");
		r.writerToken = leerCadena(jsonRes, "writerToken");
		r.expiresAtTimestampMs = leerLargoNullable(jsonRes, "expiresAtTimestampMs");
		r.maxDownloads = leerEnteroNullable(jsonRes, "maxDownloads");
		r.lifetime = leerLargoNullable(jsonRes, "lifetime");
		return r;
	}

	private void marcarUploaderEnLinea(String idSala, byte[] tokenEscritor) throws Exception {
		HttpRequest req = HttpRequest.newBuilder().uri(URI.create(API_BASE + "/room/" + idSala + "/uploader-online"))
				.header("Authorization", authBearer(tokenEscritor)).header("User-Agent", USER_AGENT)
				.method("PATCH", HttpRequest.BodyPublishers.noBody()).build();

		HttpResponse<String> res = clienteHttp.send(req, HttpResponse.BodyHandlers.ofString());
		verificar2xx(res, "Falló marcar uploader-online");
	}

	private RespuestaSala actualizarMetadataSala(String idSala, byte[] tokenEscritor, String infoHash,
			String torrentCifradoBase64, boolean multiFile, int sizeMb) throws Exception {

		Nodo body = Json.crearObjeto();
		body.obtener("infoHash").poner(infoHash);
		body.obtener("encryptedTorrentFile").poner(torrentCifradoBase64);
		body.obtener("multiFile").poner(multiFile);
		body.obtener("sizeMb").poner(sizeMb);

		HttpRequest req = HttpRequest.newBuilder().uri(URI.create(API_BASE + "/room/" + idSala))
				.header("Authorization", authBearer(tokenEscritor)).header("Content-Type", "application/json")
				.header("Accept", "application/json").header("Origin", "https://wormhole.app")
				.header("Referer", "https://wormhole.app/").header("User-Agent", USER_AGENT)
				.method("PATCH", HttpRequest.BodyPublishers.ofString(body.escribir())).build();

		HttpResponse<String> res = clienteHttp.send(req, HttpResponse.BodyHandlers.ofString());
		verificar2xx(res, "Falló la actualización de metadata de la sala");

		Nodo jsonRes = Json.leer(res.body());

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

		HttpRequest req = HttpRequest.newBuilder().uri(URI.create(API_BASE + "/room/" + idSala + "/b2/auth-upload"))
				.header("Authorization", authBearer(tokenEscritor)).header("Content-Type", "application/json")
				.header("User-Agent", USER_AGENT).POST(HttpRequest.BodyPublishers.ofString(body.escribir())).build();

		HttpResponse<String> res = clienteHttp.send(req, HttpResponse.BodyHandlers.ofString());
		verificar2xx(res, "Falló obtener autorización de subida B2");

		Nodo jsonRes = Json.leer(res.body());
		List<TokenSubidaB2> ret = new ArrayList<>();

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

		HttpRequest req = HttpRequest.newBuilder().uri(URI.create(token.uploadUrl))
				.header("Authorization", token.authorizationToken).header("X-Bz-File-Name", idSala + "/" + indiceChunk)
				.header("X-Bz-Content-Sha1", sha1Hex).header("Content-Type", "application/octet-stream")
				.header("User-Agent", USER_AGENT).POST(HttpRequest.BodyPublishers.ofByteArray(data)).build();

		HttpResponse<String> res = clienteHttp.send(req, HttpResponse.BodyHandlers.ofString());
		verificar2xx(res, "Falló la subida del chunk a B2");

		if (callback != null) {
			callback.alEnviar(data.length);
		}
	}

	private void finalizarSubida(String idSala, byte[] tokenEscritor) throws Exception {
		Nodo body = Json.crearObjeto();
		body.obtener("success").poner(true);

		HttpRequest req = HttpRequest.newBuilder().uri(URI.create(API_BASE + "/room/" + idSala + "/b2/finish-upload"))
				.header("Authorization", authBearer(tokenEscritor)).header("Content-Type", "application/json")
				.header("User-Agent", USER_AGENT).POST(HttpRequest.BodyPublishers.ofString(body.escribir())).build();

		HttpResponse<String> res = clienteHttp.send(req, HttpResponse.BodyHandlers.ofString());
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

			List<byte[]> salida = new ArrayList<>();

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
			this.estado = new AtomicReference<>(EstadoDeTransferencia.PENDIENTE);
			this.enlace = new AtomicReference<>(null);
			this.codigo = new AtomicReference<>(null);
			this.errorFinal = new AtomicReference<>(null);
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