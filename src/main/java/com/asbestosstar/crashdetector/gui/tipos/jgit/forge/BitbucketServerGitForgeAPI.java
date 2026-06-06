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
import java.util.Base64;

/**
 * Implementacion de la API de forge para Bitbucket Server / Data Center.
 *
 * No tiene un forge publico predeterminado universal.
 *
 * Usa: POST {forge}/rest/api/1.0/projects/{projectKey}/repos
 *
 * En esta clase: - config.namespace es el projectKey. - config.propietario
 * tambien se acepta como fallback del projectKey. - config.token puede ser: 1)
 * "usuario:password_o_token" para Basic auth 2) "bearer:TOKEN" para Bearer auth
 * 3) token normal, usado como Bearer por defecto
 */
public class BitbucketServerGitForgeAPI implements GitForgeAPI {

	public static final String ID = "bitbucket_server";
	public static final String FORGE_PREDETERMINADO = "https://bitbucket.example.com";

	@Override
	public String id() {
		return ID;
	}

	@Override
	public String nombreParaMostrar() {
		return "Bitbucket Server / Data Center";
	}

	@Override
	public String urlPorDefecto() {
		return FORGE_PREDETERMINADO;
	}

	@Override
	public ResultadoCrearRepositorio crearRepositorio(ConfigCrearRepositorio config) throws Exception {
		validarConfig(config);

		String forge = normalizarForge(config.forgeUrl);
		String projectKey = limpiarOpcional(config.namespace);

		if (projectKey == null || projectKey.isEmpty()) {
			projectKey = limpiarOpcional(config.propietario);
		}

		if (projectKey == null || projectKey.isEmpty()) {
			throw new IllegalArgumentException(
					"Falta projectKey de Bitbucket Server/Data Center en namespace o propietario.");
		}

		String nombreRepo = limpiarNombreRepo(config.nombreRepositorio);
		String descripcion = limpiarOpcional(config.descripcion);

		if (descripcion == null || descripcion.isEmpty()) {
			descripcion = nombreRepo;
		}

		String endpoint = forge + "/rest/api/1.0/projects/" + codificarRuta(projectKey) + "/repos";

		StringBuilder json = new StringBuilder();
		json.append("{");
		agregarCampoJson(json, "name", nombreRepo);
		agregarCampoJson(json, "scmId", "git");
		agregarCampoJson(json, "forkable", true);
		agregarCampoJson(json, "public", !config.privado);
		agregarCampoJson(json, "description", descripcion);
		json.append("}");

		RespuestaHttp respuesta = postJson(endpoint, config.token, json.toString());

		if (!respuesta.exitoHttp()) {
			return new ResultadoCrearRepositorio(false, null,
					"Bitbucket Server/Data Center HTTP " + respuesta.codigo + ": " + respuesta.cuerpo);
		}

		String remote = extraerHrefClone(respuesta.cuerpo, "ssh");

		if (remote == null || remote.trim().isEmpty()) {
			remote = extraerHrefClone(respuesta.cuerpo, "http");
		}

		if (remote == null || remote.trim().isEmpty()) {
			remote = construirRemoteSsh(forge, projectKey, nombreRepo);
		}

		return new ResultadoCrearRepositorio(true, remote,
				"Repositorio Bitbucket Server/Data Center creado. Respuesta: " + respuesta.cuerpo);
	}

	private static void validarConfig(ConfigCrearRepositorio config) {
		if (config == null) {
			throw new IllegalArgumentException("ConfigCrearRepositorio es null.");
		}

		if (config.nombreRepositorio == null || config.nombreRepositorio.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta nombre del repositorio.");
		}

		if (config.token == null || config.token.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta token de API de Bitbucket Server/Data Center.");
		}
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

	private static String construirRemoteSsh(String forgeUrl, String projectKey, String repo) throws Exception {
		URI uri = new URI(forgeUrl);
		String host = uri.getHost();

		if (host == null || host.trim().isEmpty()) {
			host = forgeUrl.replace("https://", "").replace("http://", "");
			int slash = host.indexOf('/');

			if (slash >= 0) {
				host = host.substring(0, slash);
			}
		}

		return "ssh://git@" + host + "/" + projectKey + "/" + repo + ".git";
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

	private static String codificarRuta(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8").replace("+", "%20");
		} catch (Exception e) {
			return s;
		}
	}

	private static RespuestaHttp postJson(String endpoint, String token, String cuerpo) throws Exception {
		HttpURLConnection con = (HttpURLConnection) new URL(endpoint).openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setConnectTimeout(15000);
		con.setReadTimeout(60000);
		con.setRequestProperty("User-Agent", "CrashDetector-Bitbucket-Server-API");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("Accept", "application/json");
		establecerAutenticacion(con, token);

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

	private static void establecerAutenticacion(HttpURLConnection con, String token) {
		String t = token == null ? "" : token.trim();

		if (t.startsWith("bearer:")) {
			con.setRequestProperty("Authorization", "Bearer " + t.substring("bearer:".length()).trim());
			return;
		}

		if (t.indexOf(':') > 0) {
			String basic = Base64.getEncoder().encodeToString(t.getBytes(StandardCharsets.UTF_8));
			con.setRequestProperty("Authorization", "Basic " + basic);
			return;
		}

		con.setRequestProperty("Authorization", "Bearer " + t);
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

	private static String extraerHrefClone(String json, String nombre) {
		if (json == null || nombre == null) {
			return null;
		}

		String marcador = "\"name\":\"" + nombre + "\"";
		int i = json.indexOf(marcador);

		if (i < 0) {
			marcador = "\"name\": \"" + nombre + "\"";
			i = json.indexOf(marcador);
		}

		if (i < 0) {
			return null;
		}

		int href = json.indexOf("\"href\"", i);

		if (href < 0) {
			return null;
		}

		int dosPuntos = json.indexOf(':', href);
		int primeraComilla = json.indexOf('"', dosPuntos + 1);
		int segundaComilla = json.indexOf('"', primeraComilla + 1);

		if (dosPuntos < 0 || primeraComilla < 0 || segundaComilla < 0) {
			return null;
		}

		return json.substring(primeraComilla + 1, segundaComilla).replace("\\/", "/");
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