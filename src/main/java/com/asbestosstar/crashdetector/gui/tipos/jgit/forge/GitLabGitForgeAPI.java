package com.asbestosstar.crashdetector.gui.tipos.jgit.forge;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Implementacion de la API de forge para GitLab.
 *
 * Sirve para: - GitLab.com - GitLab Self-Managed - GitLab Dedicated
 *
 * GitLab usa la misma API REST en los tres casos: https://host/api/v4
 *
 * Usa: POST /projects
 *
 * Autenticacion: PRIVATE-TOKEN: <token>
 */
public class GitLabGitForgeAPI implements GitForgeAPI {

	public static final String ID = "gitlab";
	public static final String FORGE_PREDETERMINADO = "https://gitlab.com";

	@Override
	public String id() {
		return ID;
	}

	@Override
	public String nombreParaMostrar() {
		return "GitLab";
	}

	@Override
	public String urlPorDefecto() {
		return FORGE_PREDETERMINADO;
	}

	@Override
	public ResultadoCrearRepositorio crearRepositorio(ConfigCrearRepositorio config) throws Exception {
		validarConfig(config);

		String apiBase = resolverApiBase(config.forgeUrl);
		String nombreRepo = limpiarNombreRepo(config.nombreRepositorio);
		String descripcion = limpiarOpcional(config.descripcion);

		if (descripcion == null || descripcion.isEmpty()) {
			descripcion = nombreRepo;
		}

		String namespaceId = limpiarOpcional(config.namespace);

		StringBuilder json = new StringBuilder();
		json.append("{");
		agregarCampoJson(json, "name", nombreRepo);
		agregarCampoJson(json, "path", nombreRepo);
		agregarCampoJson(json, "description", descripcion);
		agregarCampoJson(json, "visibility", config.privado ? "private" : "public");
		agregarCampoJson(json, "initialize_with_readme", config.crearReadme);

		if (namespaceId != null && !namespaceId.isEmpty() && esNumero(namespaceId)) {
			agregarCampoJson(json, "namespace_id", namespaceId);
		}

		if (config.defaultBranch != null && !config.defaultBranch.trim().isEmpty()) {
			agregarCampoJson(json, "default_branch", config.defaultBranch.trim());
		}

		json.append("}");

		RespuestaHttp respuesta = postJson(apiBase + "/projects", config.token, json.toString());

		if (!respuesta.exitoHttp()) {
			return new ResultadoCrearRepositorio(false, null,
					"GitLab HTTP " + respuesta.codigo + ": " + respuesta.cuerpo);
		}

		String remote = extraerCampoJsonSimple(respuesta.cuerpo, "ssh_url_to_repo");

		if (remote == null || remote.trim().isEmpty()) {
			remote = extraerCampoJsonSimple(respuesta.cuerpo, "http_url_to_repo");
		}

		return new ResultadoCrearRepositorio(true, remote, "Repositorio GitLab creado. Respuesta: " + respuesta.cuerpo);
	}

	private static void validarConfig(ConfigCrearRepositorio config) {
		if (config == null) {
			throw new IllegalArgumentException("ConfigCrearRepositorio es null.");
		}

		if (config.nombreRepositorio == null || config.nombreRepositorio.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta nombre del repositorio.");
		}

		if (config.token == null || config.token.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta token de API de GitLab.");
		}
	}

	private static String resolverApiBase(String forgeUrl) {
		String f = normalizarForge(forgeUrl);

		if (f.endsWith("/api/v4")) {
			return f;
		}

		return f + "/api/v4";
	}

	private static String normalizarForge(String forgeUrl) {
		String f = forgeUrl == null || forgeUrl.trim().isEmpty() ? FORGE_PREDETERMINADO : forgeUrl.trim();

		while (f.endsWith("/")) {
			f = f.substring(0, f.length() - 1);
		}

		if (!f.startsWith("http://") && !f.startsWith("https://")) {
			f = "https://" + f;
		}

		return f;
	}

	private static String limpiarNombreRepo(String repo) {
		String r = repo == null ? "" : repo.trim();

		while (r.startsWith("/")) {
			r = r.substring(1);
		}

		while (r.endsWith(".git")) {
			r = r.substring(0, r.length() - 4);
		}

		if (r.contains("/")) {
			r = r.substring(r.lastIndexOf('/') + 1);
		}

		return r;
	}

	private static String limpiarOpcional(String s) {
		if (s == null) {
			return null;
		}

		String t = s.trim();
		return t.isEmpty() ? null : t;
	}

	private static boolean esNumero(String s) {
		if (s == null || s.isEmpty()) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	private static void agregarCampoJson(StringBuilder json, String clave, String valor) {
		if (json.length() > 1) {
			json.append(",");
		}

		json.append("\"").append(escaparJson(clave)).append("\":");
		json.append("\"").append(escaparJson(valor == null ? "" : valor)).append("\"");
	}

	private static void agregarCampoJson(StringBuilder json, String clave, boolean valor) {
		if (json.length() > 1) {
			json.append(",");
		}

		json.append("\"").append(escaparJson(clave)).append("\":");
		json.append(valor ? "true" : "false");
	}

	private static String escaparJson(String s) {
		if (s == null) {
			return "";
		}

		return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t",
				"\\t");
	}

	private static RespuestaHttp postJson(String endpoint, String token, String cuerpo) throws Exception {
		HttpURLConnection con = (HttpURLConnection) new URL(endpoint).openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setConnectTimeout(15000);
		con.setReadTimeout(60000);
		con.setRequestProperty("User-Agent", "CrashDetector-GitLab-API");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("PRIVATE-TOKEN", token.trim());

		byte[] bytes = cuerpo.getBytes(StandardCharsets.UTF_8);
		con.setRequestProperty("Content-Length", Integer.toString(bytes.length));

		OutputStream out = con.getOutputStream();

		try {
			out.write(bytes);
		} finally {
			out.close();
		}

		return leerRespuesta(con);
	}

	private static RespuestaHttp leerRespuesta(HttpURLConnection con) throws Exception {
		int codigo = con.getResponseCode();
		InputStream in = codigo >= 200 && codigo < 300 ? con.getInputStream() : con.getErrorStream();
		return new RespuestaHttp(codigo, leerTodo(in));
	}

	private static String leerTodo(InputStream in) throws Exception {
		if (in == null) {
			return "";
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();

		try {
			String linea;

			while ((linea = br.readLine()) != null) {
				sb.append(linea).append('\n');
			}
		} finally {
			br.close();
		}

		return sb.toString().trim();
	}

	private static String extraerCampoJsonSimple(String json, String campo) {
		if (json == null || campo == null) {
			return null;
		}

		String marcador = "\"" + campo + "\"";
		int i = json.indexOf(marcador);

		if (i < 0) {
			return null;
		}

		int dosPuntos = json.indexOf(':', i);
		int primeraComilla = json.indexOf('"', dosPuntos + 1);
		int segundaComilla = json.indexOf('"', primeraComilla + 1);

		if (dosPuntos < 0 || primeraComilla < 0 || segundaComilla < 0) {
			return null;
		}

		return json.substring(primeraComilla + 1, segundaComilla).replace("\\/", "/").replace("\\u0040", "@");
	}

	private static class RespuestaHttp {

		public final int codigo;
		public final String cuerpo;

		public RespuestaHttp(int codigo, String cuerpo) {
			this.codigo = codigo;
			this.cuerpo = cuerpo;
		}

		public boolean exitoHttp() {
			return codigo >= 200 && codigo < 300;
		}
	}
}