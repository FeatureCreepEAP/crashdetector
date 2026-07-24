package com.asbestosstar.crashdetector.api_sitio_archivo.limewire;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.api_sitio_archivo.SitioDeArchivoAPI.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sitio_archivo.SitioDeArchivoAPI.ObservadorDeTransferencia;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.CifradorLimeWire.MaterialArchivo;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.ClienteHttpLimeWire.Respuesta;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.ReplicacionLimeWire.EstadoComparticion;

/** Gestiona el inicio, las partes prefirmadas y el cierre multipart. */
public final class MultipartLimeWire {

	private static final int MIB = 1024 * 1024;
	private static final int PARTE_PREDETERMINADA = 16 * MIB;
	private static final int MAXIMO_PARTES = 10000;

	private final ClienteHttpLimeWire http;

	public MultipartLimeWire(ClienteHttpLimeWire http) {
		this.http = http;
	}

	public Resultado subir(Path archivo, EstadoComparticion estado, MaterialArchivo material,
			ObservadorDeTransferencia observador, ControlCancelacion cancelacion)
			throws IOException, GeneralSecurityException, ErrorConPublicar {

		Inicio inicio = iniciar(estado, material);
		int tamanoParte = calcularTamanoParte(Files.size(archivo));
		List<Parte> partes = new ArrayList<Parte>();
		long enviadosPlanos = 0L;
		int numeroParte = 1;

		try (InputStream in = Files.newInputStream(archivo)) {
			while (true) {
				if (cancelacion != null && cancelacion.cancelada()) {
					abortarSilencioso(inicio);
					throw new ErrorConPublicar("La subida a LimeWire fue cancelada");
				}

				byte[] plano = leerHasta(in, tamanoParte);
				if (plano.length == 0) {
					break;
				}

				byte[] cifrado = material.cifrarParte(plano, numeroParte);
				String url = obtenerUrlParte(inicio, numeroParte);
				Respuesta respuestaPut = http.putPrefirmado(url, cifrado);
				respuestaPut.exigirExito("Subir parte " + numeroParte + " a LimeWire");
				String etag = respuestaPut.cabecera("ETag");
				if (etag == null || etag.trim().isEmpty()) {
					etag = CifradorLimeWire.sha256Base64(cifrado);
				}
				partes.add(new Parte(numeroParte, etag));
				enviadosPlanos += plano.length;
				if (observador != null) {
					observador.alActualizarProgreso(enviadosPlanos, estado.tamano);
				}
				numeroParte++;
			}
		}

		if (partes.isEmpty()) {
			throw new ErrorConPublicar("No se generó ninguna parte para LimeWire");
		}
		completar(inicio, partes);
		return new Resultado(inicio.key, inicio.uploadId, partes);
	}

	private Inicio iniciar(EstadoComparticion e, MaterialArchivo material) throws IOException {
		String bucket = "{" + "\"id\":\"" + ReplicacionLimeWire.escapar(e.idBucket) + "\"," + "\"sharingStatus\":\"NOT_SHARED\"," + "\"sharingPermission\":\"VIEWER\"," + "\"primaryEncryptionKeyId\":\""
				+ ReplicacionLimeWire.escapar(e.idClaveArchivo) + "\"," + "\"contentItemIds\":[],"
				+ "\"totalFileSize\":0," + "\"name\":\"" + ReplicacionLimeWire.escapar(e.nombreArchivo)
				+ "\"," + "\"createdDate\":\"" + e.creada.toString() + "\"," + "\"deleted\":false,"
				+ "\"expiresAt\":\"" + e.expira.toString() + "\"," + "\"pinned\":false" + "}";

		String contenido = "{" + "\"id\":\"" + ReplicacionLimeWire.escapar(e.idContenido) + "\"," + "\"originalSharingBucketId\":\""
				+ ReplicacionLimeWire.escapar(e.idBucket) + "\"," + "\"s3Status\":\"PENDING\"," + "\"itemType\":\"OTHER\"," + "\"mediaType\":\"application/zip\"," + "\"size\":" + e.tamano + ","
				+ "\"baseFileEncryptionKeyId\":\"" + ReplicacionLimeWire.escapar(e.idClaveArchivo) + "\"," + "\"ephemeralPublicKey\":\""
				+ ReplicacionLimeWire.escapar(material.clavePublicaEfimera()) + "\"," + "\"previews\":[],"
				+ "\"createdDate\":\"" + e.creada.toString() + "\"," + "\"deleted\":false" + "}";

		String clave = "{" + "\"id\":\"" + ReplicacionLimeWire.escapar(material.idClaveArchivo()) + "\"," + "\"publicKey\":\""
				+ ReplicacionLimeWire.escapar(material.clavePublicaArchivo()) + "\"," + "\"privateKeys\":[{"
				+ "\"encryptedPrivateKey\":\"" + ReplicacionLimeWire.escapar(material.clavePrivadaArchivoCifrada())
				+ "\"," + "\"encryptedByKeyType\":\"USER_ENCRYPTION_KEY\"," + "\"encryptedByKeyId\":\""
				+ ReplicacionLimeWire.escapar(material.idClaveUsuario()) + "\"}]," + "\"deleted\":false,"
				+ "\"createdDate\":\"" + e.creada.toString() + "\"" + "}";

		String cuerpo = "{\"sharingBucket\":" + bucket + ",\"contentItem\":" + contenido
				+ ",\"fileEncryptionKey\":" + clave + "}";

		Respuesta respuesta = http.postApiJson("/sharing/upload/s3/multipart", cuerpo);
		respuesta.exigirExito("Iniciar multipart de LimeWire");
		String key = ReplicacionLimeWire.extraerCadena(respuesta.cuerpo(), "key");
		String uploadId = ReplicacionLimeWire.extraerCadena(respuesta.cuerpo(), "uploadId");
		if (key == null || uploadId == null) {
			throw new IOException("LimeWire no devolvió key y uploadId para el multipart");
		}
		return new Inicio(key, uploadId);
	}

	private String obtenerUrlParte(Inicio inicio, int numeroParte) throws IOException {
		String ruta = "/sharing/upload/s3/multipart/" + codificarSegmento(inicio.uploadId) + "/" + numeroParte
				+ "?key=" + codificarQuery(inicio.key);
		Respuesta respuesta = http.getApi(ruta);
		respuesta.exigirExito("Solicitar URL prefirmado de la parte " + numeroParte);
		String metodo = ReplicacionLimeWire.extraerCadena(respuesta.cuerpo(), "method");
		String url = ReplicacionLimeWire.extraerCadena(respuesta.cuerpo(), "url");
		if (url == null || (metodo != null && !"PUT".equalsIgnoreCase(metodo))) {
			throw new IOException("Respuesta prefirmada inválida para la parte " + numeroParte);
		}
		return url;
	}

	private void completar(Inicio inicio, List<Parte> partes) throws IOException {
		String plantilla = propiedad("crashdetector.limewire.completePathTemplate",
				"/sharing/upload/s3/multipart/{uploadId}/complete?key={key}");
		String ruta = plantilla.replace("{uploadId}", codificarSegmento(inicio.uploadId)).replace("{key}",
				codificarQuery(inicio.key));
		StringBuilder json = new StringBuilder("{\"parts\":[");
		for (int i = 0; i < partes.size(); i++) {
			if (i > 0) {
				json.append(',');
			}
			Parte p = partes.get(i);
			json.append("{\"partNumber\":").append(p.numero).append(",\"etag\":\"")
					.append(ReplicacionLimeWire.escapar(p.etag)).append("\"}");
		}
		json.append("]}");
		Respuesta respuesta = http.postApiJson(ruta, json.toString());
		respuesta.exigirExito("Completar multipart de LimeWire");
	}

	private void abortarSilencioso(Inicio inicio) {
		try {
			String plantilla = propiedad("crashdetector.limewire.abortPathTemplate",
					"/sharing/upload/s3/multipart/{uploadId}?key={key}");
			String ruta = plantilla.replace("{uploadId}", codificarSegmento(inicio.uploadId)).replace("{key}",
					codificarQuery(inicio.key));
			http.deleteApi(ruta);
		} catch (Throwable ignored) {
		}
	}

	private static int calcularTamanoParte(long tamano) {
		long minimo = (tamano + MAXIMO_PARTES - 1L) / MAXIMO_PARTES;
		long redondeado = ((minimo + MIB - 1L) / MIB) * MIB;
		long elegido = Math.max(PARTE_PREDETERMINADA, redondeado);
		if (elegido > Integer.MAX_VALUE - 32L) {
			return Integer.MAX_VALUE - 32;
		}
		return (int) elegido;
	}

	private static byte[] leerHasta(InputStream in, int maximo) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(Math.min(maximo, 4 * MIB));
		byte[] buffer = new byte[Math.min(256 * 1024, maximo)];
		int restantes = maximo;
		while (restantes > 0) {
			int leidos = in.read(buffer, 0, Math.min(buffer.length, restantes));
			if (leidos < 0) {
				break;
			}
			if (leidos > 0) {
				out.write(buffer, 0, leidos);
				restantes -= leidos;
			}
		}
		return out.toByteArray();
	}

	private static String propiedad(String nombre, String defecto) {
		String valor = System.getProperty(nombre);
		return valor == null || valor.trim().isEmpty() ? defecto : valor.trim();
	}

	private static String codificarQuery(String valor) throws IOException {
		return URLEncoder.encode(valor, StandardCharsets.UTF_8.name());
	}

	private static String codificarSegmento(String valor) throws IOException {
		return codificarQuery(valor).replace("+", "%20").replace("%2F", "/");
	}

	private static final class Inicio {
		final String key;
		final String uploadId;

		Inicio(String key, String uploadId) {
			this.key = key;
			this.uploadId = uploadId;
		}
	}

	private static final class Parte {
		final int numero;
		final String etag;

		Parte(int numero, String etag) {
			this.numero = numero;
			this.etag = etag;
		}
	}

	public interface ControlCancelacion {
		boolean cancelada();
	}

	public static final class Resultado {
		public final String key;
		public final String uploadId;
		public final int cantidadPartes;

		Resultado(String key, String uploadId, List<Parte> partes) {
			this.key = key;
			this.uploadId = uploadId;
			this.cantidadPartes = partes.size();
		}
	}
}
