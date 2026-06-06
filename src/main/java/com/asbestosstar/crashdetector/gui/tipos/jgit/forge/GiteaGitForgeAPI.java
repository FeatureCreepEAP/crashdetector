package com.asbestosstar.crashdetector.gui.tipos.jgit.forge;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Implementacion de la API de forge para Gitea y Forgejo.
 *
 * Forge predeterminado: https://gitea.com
 *
 * Tambien sirve para Forgejo, Codeberg y servidores propios compatibles.
 *
 * Repositorio personal: POST /api/v1/user/repos
 *
 * Repositorio de organizacion: POST /api/v1/orgs/{org}/repos
 *
 * Autenticacion: Authorization: token <token>
 */
public class GiteaGitForgeAPI implements GitForgeAPI {

	public static final String ID = "gitea";
	public static final String FORGE_PREDETERMINADO = "https://codeberg.org";

	@Override
	public String id() {
		return ID;
	}

	@Override
	public String nombreParaMostrar() {
		return "Gitea / Forgejo";
	}

	@Override
	public String urlPorDefecto() {
		return FORGE_PREDETERMINADO;
	}

	@Override
	public ResultadoCrearRepositorio crearRepositorio(ConfigCrearRepositorio config) throws Exception {
		validarConfig(config);

		String forge = normalizarForge(config.forgeUrl);
		String nombreRepo = limpiarNombreRepo(config.nombreRepositorio);
		String descripcion = limpiarOpcional(config.descripcion);

		if (descripcion == null || descripcion.isEmpty()) {
			descripcion = nombreRepo;
		}

		String namespace = limpiarOpcional(config.namespace);

		if (namespace == null || namespace.isEmpty()) {
			namespace = limpiarOpcional(config.propietario);
		}

		String endpoint;

		if (namespace == null || namespace.isEmpty()) {
			endpoint = forge + "/api/v1/user/repos";
		} else {
			endpoint = forge + "/api/v1/orgs/" + codificarRuta(namespace) + "/repos";
		}

		StringBuilder json = new StringBuilder();
		json.append("{");
		agregarCampoJson(json, "name", nombreRepo);
		agregarCampoJson(json, "description", descripcion);
		agregarCampoJson(json, "private", config.privado);
		agregarCampoJson(json, "auto_init", config.crearReadme);

		if (config.defaultBranch != null && !config.defaultBranch.trim().isEmpty()) {
			agregarCampoJson(json, "default_branch", config.defaultBranch.trim());
		}

		if (config.urlProyecto != null && !config.urlProyecto.trim().isEmpty()) {
			agregarCampoJson(json, "website", config.urlProyecto.trim());
		}

		json.append("}");

		RespuestaHttp respuesta = postJson(endpoint, config.token, json.toString());

		if (!respuesta.exitoHttp()) {
			return new ResultadoCrearRepositorio(false, null,
					"Gitea/Forgejo HTTP " + respuesta.codigo + ": " + respuesta.cuerpo);
		}

		String remote = extraerCampoJsonSimple(respuesta.cuerpo, "ssh_url");

		if (remote == null || remote.trim().isEmpty()) {
			remote = extraerCampoJsonSimple(respuesta.cuerpo, "clone_url");
		}

		return new ResultadoCrearRepositorio(true, remote,
				"Repositorio Gitea/Forgejo creado. Respuesta: " + respuesta.cuerpo);
	}

	private static void validarConfig(ConfigCrearRepositorio config) {
		if (config == null) {
			throw new IllegalArgumentException("ConfigCrearRepositorio es null.");
		}

		if (config.nombreRepositorio == null || config.nombreRepositorio.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta nombre del repositorio.");
		}

		if (config.token == null || config.token.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta token de API de Gitea/Forgejo.");
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
		con.setRequestProperty("User-Agent", "CrashDetector-Gitea-Forgejo-API");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("Accept", "application/json");

		if (token != null && !token.trim().isEmpty()) {
			con.setRequestProperty("Authorization", "token " + token.trim());
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