package com.asbestosstar.crashdetector.gui.tipos.jgit.forge;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Implementacion de la API de forge para Bitbucket Cloud.
 *
 * Forge predeterminado: https://bitbucket.org
 *
 * Usa: POST https://api.bitbucket.org/2.0/repositories/{workspace}/{repo_slug}
 *
 * En esta clase: - config.namespace es el workspace. - config.propietario
 * tambien se acepta como fallback del workspace. - config.token puede ser: 1)
 * "usuario:app_password" para Basic auth 2) "bearer:TOKEN" para Bearer auth 3)
 * token normal, usado como Bearer por defecto
 */
public class BitbucketCloudGitForgeAPI implements GitForgeAPI {

	public static final String ID = "bitbucket_cloud";
	public static final String FORGE_PREDETERMINADO = "https://bitbucket.org";
	public static final String API_PREDETERMINADA = "https://api.bitbucket.org/2.0";

	@Override
	public String id() {
		return ID;
	}

	@Override
	public String nombreParaMostrar() {
		return "Bitbucket Cloud";
	}

	@Override
	public String urlPorDefecto() {
		return FORGE_PREDETERMINADO;
	}

	@Override
	public ResultadoCrearRepositorio crearRepositorio(ConfigCrearRepositorio config) throws Exception {
		validarConfig(config);

		String workspace = limpiarOpcional(config.namespace);

		if (workspace == null || workspace.isEmpty()) {
			workspace = limpiarOpcional(config.propietario);
		}

		if (workspace == null || workspace.isEmpty()) {
			throw new IllegalArgumentException("Falta workspace de Bitbucket Cloud en namespace o propietario.");
		}

		String nombreRepo = limpiarNombreRepo(config.nombreRepositorio);
		String slug = limpiarSlug(nombreRepo);
		String descripcion = limpiarOpcional(config.descripcion);

		if (descripcion == null || descripcion.isEmpty()) {
			descripcion = nombreRepo;
		}

		String endpoint = API_PREDETERMINADA + "/repositories/" + codificarRuta(workspace) + "/" + codificarRuta(slug);

		StringBuilder json = new StringBuilder();
		json.append("{");
		agregarCampoJson(json, "scm", "git");
		agregarCampoJson(json, "name", nombreRepo);
		agregarCampoJson(json, "description", descripcion);
		agregarCampoJson(json, "is_private", config.privado);

		if (config.urlProyecto != null && !config.urlProyecto.trim().isEmpty()) {
			agregarCampoJson(json, "website", config.urlProyecto.trim());
		}

		json.append("}");

		RespuestaHttp respuesta = postJson(endpoint, config.token, json.toString());

		if (!respuesta.exitoHttp()) {
			return new ResultadoCrearRepositorio(false, null,
					"Bitbucket Cloud HTTP " + respuesta.codigo + ": " + respuesta.cuerpo);
		}

		String remote = extraerHrefClone(respuesta.cuerpo, "ssh");

		if (remote == null || remote.trim().isEmpty()) {
			remote = extraerHrefClone(respuesta.cuerpo, "https");
		}

		if (remote == null || remote.trim().isEmpty()) {
			remote = "git@bitbucket.org:" + workspace + "/" + slug + ".git";
		}

		return new ResultadoCrearRepositorio(true, remote,
				"Repositorio Bitbucket Cloud creado. Respuesta: " + respuesta.cuerpo);
	}

	private static void validarConfig(ConfigCrearRepositorio config) {
		if (config == null) {
			throw new IllegalArgumentException("ConfigCrearRepositorio es null.");
		}

		if (config.nombreRepositorio == null || config.nombreRepositorio.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta nombre del repositorio.");
		}

		if (config.token == null || config.token.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta token de API de Bitbucket Cloud.");
		}
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

	private static String limpiarSlug(String repo) {
		String r = limpiarNombreRepo(repo).toLowerCase();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < r.length(); i++) {
			char c = r.charAt(i);

			if (Character.isLetterOrDigit(c) || c == '_' || c == '-') {
				sb.append(c);
			} else if (c == ' ' || c == '.') {
				sb.append('-');
			}
		}

		return sb.length() == 0 ? "repositorio" : sb.toString();
	}

	private static String limpiarOpcional(String s) {
		if (s == null) {
			return null;
		}

		String t = s.trim();

		return t.isEmpty() ? null : t;
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
		con.setRequestProperty("User-Agent", "CrashDetector-Bitbucket-Cloud-API");
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