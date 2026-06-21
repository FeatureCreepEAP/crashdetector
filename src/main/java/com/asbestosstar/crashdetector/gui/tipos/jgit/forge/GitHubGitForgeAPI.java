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
 * Implementacion de la API de forge para GitHub.
 *
 * Forge predeterminado: https://github.com
 *
 * Para repositorios personales usa: POST https://api.github.com/user/repos
 *
 * Para repositorios de organizacion usa: POST
 * https://api.github.com/orgs/{org}/repos
 *
 * En esta clase: - config.propietario indica la organizacion. - si propietario
 * esta vacio, se crea el repositorio en el usuario autenticado. -
 * config.namespace tambien se acepta como alias de propietario para encajar con
 * la GUI existente.
 *
 * Autenticacion: Authorization: Bearer <token>
 *
 * Formato: application/json
 */
public class GitHubGitForgeAPI implements GitForgeAPI {

	public static final String ID = "github";
	public static final String FORGE_PREDETERMINADO = "https://github.com";
	public static final String API_PREDETERMINADA = "https://api.github.com";
	public static final String VERSION_API_GITHUB = "2026-03-10";

	@Override
	public String id() {
		return ID;
	}

	@Override
	public String nombreParaMostrar() {
		return "GitHub";
	}

	@Override
	public String urlPorDefecto() {
		return FORGE_PREDETERMINADO;
	}

	@Override
	public ResultadoCrearRepositorio crearRepositorio(ConfigCrearRepositorio config) throws Exception {
		validarConfig(config);

		String nombreRepo = limpiarNombreRepo(config.nombreRepositorio);
		String descripcion = limpiarOpcional(config.descripcion);

		if (descripcion == null || descripcion.isEmpty()) {
			descripcion = nombreRepo;
		}

		String propietario = limpiarOpcional(config.propietario);

		if (propietario == null || propietario.isEmpty()) {
			propietario = limpiarOpcional(config.namespace);
		}

		String apiBase = resolverApiBase(config.forgeUrl);
		String endpoint;

		if (propietario == null || propietario.isEmpty()) {
			endpoint = apiBase + "/user/repos";
		} else {
			endpoint = apiBase + "/orgs/" + codificarRuta(propietario) + "/repos";
		}

		StringBuilder json = new StringBuilder();
		json.append("{");
		agregarCampoJson(json, "name", nombreRepo);
		agregarCampoJson(json, "description", descripcion);

		if (config.urlProyecto != null && !config.urlProyecto.trim().isEmpty()) {
			agregarCampoJson(json, "homepage", config.urlProyecto.trim());
		}

		agregarCampoJson(json, "private", config.privado);
		agregarCampoJson(json, "auto_init", config.crearReadme);
		agregarCampoJson(json, "has_issues", true);
		agregarCampoJson(json, "has_projects", true);
		agregarCampoJson(json, "has_wiki", true);

		json.append("}");

		RespuestaHttp respuesta = postJson(endpoint, config.token, json.toString());

		if (!respuesta.exitoHttp()) {
			return new ResultadoCrearRepositorio(false, null,
					"GitHub HTTP " + respuesta.codigo + ": " + respuesta.cuerpo);
		}

		String remote = extraerCampoJsonSimple(respuesta.cuerpo, "ssh_url");

		if (remote == null || remote.trim().isEmpty()) {
			remote = construirRemoteSsh(config.forgeUrl, propietario, nombreRepo);
		}

		return new ResultadoCrearRepositorio(true, remote, "Repositorio GitHub creado. Respuesta: " + respuesta.cuerpo);
	}

	public ResultadoCrearRepositorio probarProyecto(ConfigCrearRepositorio config) throws Exception {
		validarConfigMinimaProyecto(config);

		String propietario = limpiarOpcional(config.propietario);

		if (propietario == null || propietario.isEmpty()) {
			propietario = limpiarOpcional(config.namespace);
		}

		if (propietario == null || propietario.isEmpty()) {
			throw new IllegalArgumentException("Falta propietario para consultar el repositorio GitHub.");
		}

		String nombreRepo = limpiarNombreRepo(config.nombreRepositorio);
		String apiBase = resolverApiBase(config.forgeUrl);
		String endpoint = apiBase + "/repos/" + codificarRuta(propietario) + "/" + codificarRuta(nombreRepo);

		RespuestaHttp respuesta = get(endpoint, config.token);

		if (!respuesta.exitoHttp()) {
			return new ResultadoCrearRepositorio(false, null,
					"GitHub HTTP " + respuesta.codigo + ": " + respuesta.cuerpo);
		}

		String remote = extraerCampoJsonSimple(respuesta.cuerpo, "ssh_url");

		if (remote == null || remote.trim().isEmpty()) {
			remote = construirRemoteSsh(config.forgeUrl, propietario, nombreRepo);
		}

		return new ResultadoCrearRepositorio(true, remote, respuesta.cuerpo);
	}

	private static void validarConfig(ConfigCrearRepositorio config) {
		validarConfigMinimaProyecto(config);

		if (config.token == null || config.token.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta token de API de GitHub.");
		}
	}

	private static void validarConfigMinimaProyecto(ConfigCrearRepositorio config) {
		if (config == null) {
			throw new IllegalArgumentException("ConfigCrearRepositorio es null.");
		}

		if (config.nombreRepositorio == null || config.nombreRepositorio.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta nombre del repositorio.");
		}
	}

	private static String resolverApiBase(String forgeUrl) {
		String f = normalizarForge(forgeUrl);

		if (f.equals("https://github.com") || f.equals("http://github.com")) {
			return API_PREDETERMINADA;
		}

		if (f.equals("https://api.github.com") || f.equals("http://api.github.com")) {
			return f;
		}

		/*
		 * Para GitHub Enterprise normalmente la API queda en: https://host/api/v3
		 */
		if (f.endsWith("/api/v3")) {
			return f;
		}

		return f + "/api/v3";
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

	private static String construirRemoteSsh(String forgeUrl, String propietario, String repo) throws Exception {
		String forge = normalizarForge(forgeUrl);
		URI uri = new URI(forge);
		String host = uri.getHost();

		if (host == null || host.trim().isEmpty()) {
			host = "github.com";
		}

		if (propietario == null || propietario.trim().isEmpty()) {
			return null;
		}

		return "git@" + host + ":" + propietario.trim() + "/" + repo + ".git";
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

		StringBuilder ret = new StringBuilder();

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			switch (c) {
			case '\\':
				ret.append("\\\\");
				break;
			case '"':
				ret.append("\\\"");
				break;
			case '\n':
				ret.append("\\n");
				break;
			case '\r':
				ret.append("\\r");
				break;
			case '\t':
				ret.append("\\t");
				break;
			default:
				ret.append(c);
				break;
			}
		}

		return ret.toString();
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
		con.setRequestProperty("User-Agent", "CrashDetector-GitHub-API");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("Accept", "application/vnd.github+json");
		con.setRequestProperty("X-GitHub-Api-Version", VERSION_API_GITHUB);

		if (token != null && !token.trim().isEmpty()) {
			con.setRequestProperty("Authorization", "Bearer " + token.trim());
		}

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

	private static RespuestaHttp get(String endpoint, String token) throws Exception {
		HttpURLConnection con = (HttpURLConnection) new URL(endpoint).openConnection();
		con.setRequestMethod("GET");
		con.setConnectTimeout(15000);
		con.setReadTimeout(60000);
		con.setRequestProperty("User-Agent", "CrashDetector-GitHub-API");
		con.setRequestProperty("Accept", "application/vnd.github+json");
		con.setRequestProperty("X-GitHub-Api-Version", VERSION_API_GITHUB);

		if (token != null && !token.trim().isEmpty()) {
			con.setRequestProperty("Authorization", "Bearer " + token.trim());
		}

		return leerRespuesta(con);
	}

	private static RespuestaHttp leerRespuesta(HttpURLConnection con) throws Exception {
		int codigo = con.getResponseCode();

		InputStream in;

		if (codigo >= 200 && codigo < 300) {
			in = con.getInputStream();
		} else {
			in = con.getErrorStream();
		}

		String cuerpo = leerTodo(in);

		return new RespuestaHttp(codigo, cuerpo);
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

		if (dosPuntos < 0) {
			return null;
		}

		int primeraComilla = json.indexOf('"', dosPuntos + 1);

		if (primeraComilla < 0) {
			return null;
		}

		int segundaComilla = json.indexOf('"', primeraComilla + 1);

		while (segundaComilla > 0 && json.charAt(segundaComilla - 1) == '\\') {
			segundaComilla = json.indexOf('"', segundaComilla + 1);
		}

		if (segundaComilla < 0) {
			return null;
		}

		return json.substring(primeraComilla + 1, segundaComilla).replace("\\/", "/").replace("\\u0040", "@")
				.replace("\\\"", "\"").replace("\\\\", "\\");
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