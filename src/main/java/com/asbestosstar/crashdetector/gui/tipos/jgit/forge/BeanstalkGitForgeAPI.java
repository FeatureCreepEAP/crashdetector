package com.asbestosstar.crashdetector.gui.tipos.jgit.forge;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Implementacion de la API de forge para Beanstalk.
 *
 * Beanstalk usa cuentas por subdominio: https://cuenta.beanstalkapp.com
 *
 * Usa: POST /api/repositories.json
 *
 * Autenticacion: Basic usuario:access_token
 *
 * En esta clase: - config.forgeUrl debe ser la URL de la cuenta, por ejemplo:
 * https://micuenta.beanstalkapp.com - config.token debe venir como:
 * usuario:access_token
 *
 * Nota: Beanstalk es normalmente privado por cuenta. El campo config.privado no
 * se manda porque la API de repositorios no expone un campo simple equivalente.
 */
public class BeanstalkGitForgeAPI implements GitForgeAPI {

	public static final String ID = "beanstalk";
	public static final String FORGE_PREDETERMINADO = "https://cuenta.beanstalkapp.com";

	@Override
	public String id() {
		return ID;
	}

	@Override
	public String nombreParaMostrar() {
		return "Beanstalk";
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

		String titulo = descripcion;
		String rama = limpiarOpcional(config.defaultBranch);

		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"repository\":{");
		agregarCampoJson(json, "type_id", "git");
		agregarCampoJson(json, "name", nombreRepo);
		agregarCampoJson(json, "title", titulo);
		agregarCampoJson(json, "description", descripcion);
		agregarCampoJson(json, "color_label", "label-blue");

		if (rama != null && !rama.isEmpty()) {
			agregarCampoJson(json, "default_branch", rama);
		}

		json.append("}");
		json.append("}");

		RespuestaHttp respuesta = postJson(forge + "/api/repositories.json", config.token, json.toString());

		if (!respuesta.exitoHttp()) {
			return new ResultadoCrearRepositorio(false, null,
					"Beanstalk HTTP " + respuesta.codigo + ": " + respuesta.cuerpo);
		}

		String remote = extraerCampoJsonSimple(respuesta.cuerpo, "repository_url");

		return new ResultadoCrearRepositorio(true, remote,
				"Repositorio Beanstalk creado. Respuesta: " + respuesta.cuerpo);
	}

	private static void validarConfig(ConfigCrearRepositorio config) {
		if (config == null) {
			throw new IllegalArgumentException("ConfigCrearRepositorio es null.");
		}

		if (config.forgeUrl == null || config.forgeUrl.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"Falta URL de cuenta Beanstalk. Ejemplo: https://cuenta.beanstalkapp.com");
		}

		if (config.nombreRepositorio == null || config.nombreRepositorio.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta nombre del repositorio.");
		}

		if (config.token == null || config.token.trim().isEmpty()) {
			throw new IllegalArgumentException("Falta autenticacion Beanstalk. Use usuario:access_token.");
		}

		if (config.token.indexOf(':') <= 0) {
			throw new IllegalArgumentException("Beanstalk requiere token en formato usuario:access_token.");
		}
	}

	private static String normalizarForge(String forgeUrl) {
		String f = forgeUrl == null ? "" : forgeUrl.trim();

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
		if (json.length() > 1 && json.charAt(json.length() - 1) != '{') {
			json.append(",");
		}

		json.append("\"").append(escaparJson(clave)).append("\":");
		json.append("\"").append(escaparJson(valor == null ? "" : valor)).append("\"");
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
		con.setRequestProperty("User-Agent", "CrashDetector-Beanstalk-API");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Authorization",
				"Basic " + Base64.getEncoder().encodeToString(token.trim().getBytes(StandardCharsets.UTF_8)));

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