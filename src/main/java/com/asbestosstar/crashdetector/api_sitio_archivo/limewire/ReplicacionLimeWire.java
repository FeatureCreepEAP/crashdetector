package com.asbestosstar.crashdetector.api_sitio_archivo.limewire;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.CifradorLimeWire.MaterialArchivo;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.ClienteHttpLimeWire.Respuesta;

/** Construye y envía los documentos de replicación observados en LimeWire. */
public final class ReplicacionLimeWire {

	private final ClienteHttpLimeWire http;
	private final CredencialesLimeWire credenciales;

	public ReplicacionLimeWire(ClienteHttpLimeWire http, CredencialesLimeWire credenciales) {
		this.http = http;
		this.credenciales = credenciales;
	}

	public ClaveUsuario obtenerOCrearClaveUsuario() throws IOException {
		Respuesta respuesta = http.postApiVacio("/sharing/user-encryption-key");
		respuesta.exigirExito("Obtener la clave de cifrado del usuario");
		String id = extraerCadena(respuesta.cuerpo(), "id");
		String publica = extraerCadena(respuesta.cuerpo(), "publicKey");
		if (id == null || publica == null) {
			throw new IOException("La respuesta user-encryption-key no incluyó id y publicKey");
		}
		return new ClaveUsuario(id, publica);
	}

	public EstadoComparticion crearEstadoInicial(String nombreArchivo, long tamano, MaterialArchivo material)
			throws IOException {
		EstadoComparticion estado = new EstadoComparticion();
		estado.idBucket = UUID.randomUUID().toString();
		estado.idContenido = UUID.randomUUID().toString();
		estado.idClaveArchivo = material.idClaveArchivo();
		estado.nombreArchivo = nombreArchivo;
		estado.tamano = tamano;
		estado.creada = Instant.now();
		estado.expira = estado.creada.plus(7, ChronoUnit.DAYS);

		push("SHARING_BUCKET", jsonPush(jsonBucket(estado, false)));
		push("CONTENT_ITEM", jsonPush(jsonContenido(estado, material, "PENDING")));
		return estado;
	}

	public void marcarSubidaCompleta(EstadoComparticion estado, MaterialArchivo material) throws IOException {
		String s3Status = propiedad("crashdetector.limewire.s3StatusFinal", "AVAILABLE");
		push("CONTENT_ITEM", jsonPush(jsonContenido(estado, material, s3Status)));
		push("SHARING_BUCKET", jsonPush(jsonBucket(estado, true)));
	}

	private void push(String tipo, String cuerpo) throws IOException {
		String ruta = "/sharing/replication/" + tipo + "/push?clientDeviceId="
				+ codificar(credenciales.idDispositivoCliente());
		Respuesta respuesta = http.postApiJson(ruta, cuerpo);
		respuesta.exigirExito("Replicar " + tipo);
		if (respuesta.cuerpo().contains("\"conflicts\"") && !respuesta.cuerpo().contains("\"conflicts\":[]")) {
			throw new IOException("LimeWire informó un conflicto al replicar " + tipo + ": " + respuesta.cuerpo());
		}
	}

	public String buscarEnlaceCompartible(EstadoComparticion estado) {
		String plantilla = primeroNoVacio(System.getProperty("crashdetector.limewire.shareUrlTemplate"),
				System.getenv("CRASHDETECTOR_LIMEWIRE_SHARE_URL_TEMPLATE"));
		if (plantilla != null) {
			return plantilla.replace("{bucketId}", estado.idBucket).replace("{contentItemId}", estado.idContenido);
		}

		try {
			String ruta = "/sharing/replication/SHARING_BUCKET/pull?timestampSeconds=0&timestampIncrement=0&limit=1000&clientDeviceId="
					+ codificar(credenciales.idDispositivoCliente());
			Respuesta respuesta = http.getApi(ruta);
			if (respuesta.exito() && respuesta.cuerpo().contains(estado.idBucket)) {
				for (String campo : new String[] { "shareUrl", "sharingUrl", "publicUrl", "downloadUrl", "url" }) {
					String valor = extraerCadenaCercana(respuesta.cuerpo(), estado.idBucket, campo);
					if (valor != null && valor.startsWith("http")) {
						return valor;
					}
				}
				String slug = extraerCadenaCercana(respuesta.cuerpo(), estado.idBucket, "slug");
				if (slug != null && !slug.trim().isEmpty()) {
					return "https://limewire.com/d/" + slug;
				}
			}
		} catch (Throwable ignored) {
			// El identificador del bucket sigue disponible como código de recuperación.
		}
		return null;
	}

	private String jsonBucket(EstadoComparticion e, boolean finalizado) {
		String estado = finalizado ? propiedad("crashdetector.limewire.sharingStatusFinal", "SHARED") : "NOT_SHARED";
		String ids = finalizado ? "[\"" + escapar(e.idContenido) + "\"]" : "[]";
		long total = finalizado ? e.tamano : 0L;
		return "{" + "\"id\":\"" + escapar(e.idBucket) + "\"," + "\"sharingStatus\":\""
				+ escapar(estado) + "\"," + "\"sharingPermission\":\"VIEWER\"," + "\"primaryEncryptionKeyId\":\""
				+ escapar(e.idClaveArchivo) + "\"," + "\"contentItemIds\":" + ids + "," + "\"totalFileSize\":"
				+ total + "," + "\"name\":\"" + escapar(e.nombreArchivo) + "\"," + "\"createdDate\":\""
				+ e.creada.toString() + "\"," + "\"deleted\":false," + "\"expiresAt\":\"" + e.expira.toString()
				+ "\"," + "\"pinned\":false" + "}";
	}

	private String jsonContenido(EstadoComparticion e, MaterialArchivo material, String s3Status) {
		return "{" + "\"id\":\"" + escapar(e.idContenido) + "\"," + "\"originalSharingBucketId\":\""
				+ escapar(e.idBucket) + "\"," + "\"s3Status\":\"" + escapar(s3Status) + "\"," + "\"itemType\":\"OTHER\"," + "\"mediaType\":\"application/zip\"," + "\"size\":" + e.tamano + ","
				+ "\"baseFileEncryptionKeyId\":\"" + escapar(e.idClaveArchivo) + "\"," + "\"ephemeralPublicKey\":\""
				+ escapar(material.clavePublicaEfimera()) + "\"," + "\"previews\":[]," + "\"createdDate\":\""
				+ e.creada.toString() + "\"," + "\"deleted\":false" + "}";
	}

	private static String jsonPush(String documento) {
		return "{\"changedDocuments\":[{\"newDocumentState\":" + documento + "}]}";
	}

	private static String propiedad(String nombre, String defecto) {
		String valor = System.getProperty(nombre);
		return valor == null || valor.trim().isEmpty() ? defecto : valor.trim();
	}

	public static String extraerCadena(String json, String campo) {
		if (json == null || campo == null) {
			return null;
		}
		Pattern p = Pattern.compile("\\\"" + Pattern.quote(campo)
				+ "\\\"\\s*:\\s*\\\"((?:\\\\.|[^\\\"])*)\\\"");
		Matcher m = p.matcher(json);
		return m.find() ? desescapar(m.group(1)) : null;
	}

	private static String extraerCadenaCercana(String json, String id, String campo) {
		int pos = json.indexOf(id);
		if (pos < 0) {
			return null;
		}
		int desde = Math.max(0, pos - 1000);
		int hasta = Math.min(json.length(), pos + 6000);
		return extraerCadena(json.substring(desde, hasta), campo);
	}

	public static String escapar(String s) {
		if (s == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\\':
				sb.append("\\\\");
				break;
			case '"':
				sb.append("\\\"");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				if (c < 0x20) {
					sb.append(String.format("\\u%04x", Integer.valueOf(c)));
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	private static String desescapar(String s) {
		return s.replace("\\\"", "\"").replace("\\/", "/").replace("\\\\", "\\").replace("\\n", "\n")
				.replace("\\r", "\r").replace("\\t", "\t");
	}

	private static String codificar(String valor) throws IOException {
		return URLEncoder.encode(valor, StandardCharsets.UTF_8.name());
	}

	private static String primeroNoVacio(String a, String b) {
		if (a != null && !a.trim().isEmpty()) {
			return a.trim();
		}
		if (b != null && !b.trim().isEmpty()) {
			return b.trim();
		}
		return null;
	}

	public static final class ClaveUsuario {
		public final String id;
		public final String clavePublica;

		ClaveUsuario(String id, String clavePublica) {
			this.id = id;
			this.clavePublica = clavePublica;
		}
	}

	public static final class EstadoComparticion {
		public String idBucket;
		public String idContenido;
		public String idClaveArchivo;
		public String nombreArchivo;
		public long tamano;
		public Instant creada;
		public Instant expira;
	}
}
