package com.asbestosstar.crashdetector.dto.modpack.modrinth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.List;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.bbsmc.BBSModDesdeJar;

public class MRModDesdeJar extends BBSModDesdeJar {

	public static final String ENDPOINT_MODRINTH = "https://api.modrinth.com/v2";

	public static ResultadoBBS analizarInstancia(Path carpetaInstancia) throws IOException {
		return analizarInstanciaConEndpoint(carpetaInstancia, ENDPOINT_MODRINTH);
	}

	public static ResultadoBBS analizarArchivos(Path carpetaInstancia, List<ArchivoLocalBBS> archivos)
			throws IOException {
		return analizarArchivosConEndpoint(carpetaInstancia, archivos, ENDPOINT_MODRINTH);
	}

	public static Json.Nodo solicitarVersionPorSha1Modrinth(String sha1) throws IOException {
		if (sha1 == null || sha1.trim().isEmpty()) {
			throw new IOException("SHA-1 vacio para consulta Modrinth.");
		}

		String url = ENDPOINT_MODRINTH + "/version_file/" + URLEncoder.encode(sha1.trim(), "UTF-8")
				+ "?algorithm=sha1";

		return getJsonModrinth(url);
	}

	public static Json.Nodo solicitarVersionPorSha1ModrinthSeguro(String sha1) {
		try {
			return solicitarVersionPorSha1Modrinth(sha1);
		} catch (Throwable t) {
			return null;
		}
	}

	public static Json.Nodo solicitarProyectoModrinth(String projectId) throws IOException {
		if (projectId == null || projectId.trim().isEmpty()) {
			throw new IOException("Project ID vacio para consulta Modrinth.");
		}

		String url = ENDPOINT_MODRINTH + "/project/" + URLEncoder.encode(projectId.trim(), "UTF-8");
		return getJsonModrinth(url);
	}

	public static Json.Nodo solicitarProyectoModrinthSeguro(String projectId) {
		try {
			return solicitarProyectoModrinth(projectId);
		} catch (Throwable t) {
			return null;
		}
	}

	private static Json.Nodo getJsonModrinth(String url) throws IOException {
		HttpURLConnection con = null;

		try {
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();

			con.setRequestMethod("GET");
			con.setConnectTimeout(20000);
			con.setReadTimeout(30000);
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("User-Agent", "CrashDetectorMC/1.0 (asbestosstar)");

			int codigo = con.getResponseCode();
			InputStream is = codigo >= 200 && codigo < 300 ? con.getInputStream() : con.getErrorStream();
			String respuesta = leerCompleto(is);

			if (codigo < 200 || codigo >= 300) {
				throw new IOException("Error Modrinth HTTP " + codigo + ": " + respuesta);
			}

			return Json.leer(respuesta);
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}

	private static String leerCompleto(InputStream is) throws IOException {
		if (is == null) {
			return "";
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int leidos;

		while ((leidos = is.read(buffer)) != -1) {
			baos.write(buffer, 0, leidos);
		}

		return new String(baos.toByteArray(), "UTF-8");
	}
}