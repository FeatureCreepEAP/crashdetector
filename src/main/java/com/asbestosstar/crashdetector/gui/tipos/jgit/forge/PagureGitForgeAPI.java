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
 * Implementación de la API de forge para Pagure.
 *
 * Forge predeterminado: https://pagure.io
 *
 * Pagure también existe en otras instalaciones, así que config.forgeUrl puede
 * apuntar a cualquier instancia compatible con Pagure.
 *
 * Usa: POST /api/0/new
 *
 * Autenticación: Authorization: token <token>
 *
 * Formato: application/x-www-form-urlencoded
 */
public class PagureGitForgeAPI implements GitForgeAPI {

	public static final String ID = "pagure";
	public static final String FORGE_PREDETERMINADO = "https://pagure.io";

	@Override
	public String id() {
		return ID;
	}

	@Override
	public String nombreParaMostrar() {
		return "Pagure";
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
		String descripcion = config.descripcion == null || config.descripcion.trim().isEmpty() ? nombreRepo
				: config.descripcion.trim();

		String namespace = limpiarOpcional(config.namespace);
		String rama = limpiarOpcional(config.defaultBranch);

		StringBuilder cuerpo = new StringBuilder();
		agregarCampo(cuerpo, "name", nombreRepo);
		agregarCampo(cuerpo, "description", descripcion);
		agregarCampo(cuerpo, "wait", "true");
		agregarCampo(cuerpo, "create_readme", Boolean.toString(config.crearReadme));
		agregarCampo(cuerpo, "private", Boolean.toString(config.privado));

		if (namespace != null && !namespace.isEmpty()) {
			agregarCampo(cuerpo, "namespace", namespace);
		}

		if (rama != null && !rama.isEmpty()) {
			agregarCampo(cuerpo, "default_branch", rama);
		}

		if (config.urlProyecto != null && !config.urlProyecto.trim().isEmpty()) {
			agregarCampo(cuerpo, "url", config.urlProyecto.trim());
		}

		String endpoint = forge + "/api/0/new";
		RespuestaHttp respuesta = postForm(endpoint, config.token, cuerpo.toString());

		if (!respuesta.exitoHttp()) {
			return new ResultadoCrearRepositorio(false, null,
					"Pagure HTTP " + respuesta.codigo + ": " + respuesta.cuerpo);
		}

		String remote = construirRemoteSsh(forge, namespace, nombreRepo);

		return new ResultadoCrearRepositorio(true, remote,
				"Repositorio Pagure creado o puesto en cola. Respuesta: " + respuesta.cuerpo);
	}

	public ResultadoCrearRepositorio probarProyecto(ConfigCrearRepositorio config) throws Exception {
		validarConfigMinimaProyecto(config);

		String forge = normalizarForge(config.forgeUrl);
		String namespace = limpiarOpcional(config.namespace);
		String nombreRepo = limpiarNombreRepo(config.nombreRepositorio);

		String endpoint = forge + "/api/0/" + rutaProyectoApi(namespace, nombreRepo);

		RespuestaHttp respuesta = get(endpoint, config.token);

		if (!respuesta.exitoHttp()) {
			return new ResultadoCrearRepositorio(false, null,
					"Pagure HTTP " + respuesta.codigo + ": " + respuesta.cuerpo);
		}

		String remote = construirRemoteSsh(forge, namespace, nombreRepo);

		return new ResultadoCrearRepositorio(true, remote, respuesta.cuerpo);
	}

	public ResultadoCrearRepositorio obtenerUrlsGit(ConfigCrearRepositorio config) throws Exception {
		validarConfigMinimaProyecto(config);

		String forge = normalizarForge(config.forgeUrl);
		String namespace = limpiarOpcional(config.namespace);
		String nombreRepo = limpiarNombreRepo(config.nombreRepositorio);

		String endpoint = forge + "/api/0/" + rutaProyectoApi(namespace, nombreRepo) + "/git/urls";

		RespuestaHttp respuesta = get(endpoint, config.token);

		if (!respuesta.exitoHttp()) {
			return new ResultadoCrearRepositorio(false, null,
					"Pagure HTTP " + respuesta.codigo + ": " + respuesta.cuerpo);
		}

		String remote = extraerUrlSshDeJsonSimple(respuesta.cuerpo);

		if (remote == null || remote.trim().isEmpty()) {
			remote = construirRemoteSsh(forge, namespace, nombreRepo);
		}

		return new ResultadoCrearRepositorio(true, remote, respuesta.cuerpo);
	}

	private static void validarConfig(ConfigCrearRepositorio config) {
		validarConfigMinimaProyecto(config);

		if (config.token == null || config.token.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta token de API de Pagure.");
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

	private static String rutaProyectoApi(String namespace, String repo) {
		if (namespace == null || namespace.trim().isEmpty()) {
			return codificarRuta(repo);
		}

		return codificarRuta(namespace) + "/" + codificarRuta(repo);
	}

	private static String construirRemoteSsh(String forgeUrl, String namespace, String repo) throws Exception {
		URI uri = new URI(forgeUrl);
		String host = uri.getHost();

		if (host == null || host.trim().isEmpty()) {
			host = forgeUrl.replace("https://", "").replace("http://", "");
			int slash = host.indexOf('/');
			if (slash >= 0) {
				host = host.substring(0, slash);
			}
		}

		StringBuilder ruta = new StringBuilder();

		if (namespace != null && !namespace.trim().isEmpty()) {
			ruta.append(namespace.trim()).append("/");
		}

		ruta.append(repo).append(".git");

		return "ssh://git@" + host + "/" + ruta.toString();
	}

	private static void agregarCampo(StringBuilder cuerpo, String clave, String valor) throws Exception {
		if (cuerpo.length() > 0) {
			cuerpo.append("&");
		}

		cuerpo.append(URLEncoder.encode(clave, "UTF-8"));
		cuerpo.append("=");
		cuerpo.append(URLEncoder.encode(valor == null ? "" : valor, "UTF-8"));
	}

	private static String codificarRuta(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8").replace("+", "%20");
		} catch (Exception e) {
			return s;
		}
	}

	private static RespuestaHttp postForm(String endpoint, String token, String cuerpo) throws Exception {
		HttpURLConnection con = (HttpURLConnection) new URL(endpoint).openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setConnectTimeout(15000);
		con.setReadTimeout(60000);
		con.setRequestProperty("User-Agent", "CrashDetector-Pagure-API");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
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

	private static RespuestaHttp get(String endpoint, String token) throws Exception {
		HttpURLConnection con = (HttpURLConnection) new URL(endpoint).openConnection();
		con.setRequestMethod("GET");
		con.setConnectTimeout(15000);
		con.setReadTimeout(60000);
		con.setRequestProperty("User-Agent", "CrashDetector-Pagure-API");
		con.setRequestProperty("Accept", "application/json");

		if (token != null && !token.trim().isEmpty()) {
			con.setRequestProperty("Authorization", "token " + token.trim());
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

	private static String extraerUrlSshDeJsonSimple(String json) {
		if (json == null) {
			return null;
		}

		String marcador = "\"ssh\"";
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

		if (segundaComilla < 0) {
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