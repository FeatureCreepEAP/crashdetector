package com.asbestosstar.crashdetector.dto.modpack.bbsmc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;

public class SolicitudBusquedaModsBBSMC {

	private static final int LIMITE = 20;

	public static PaginaMods buscarMods(String endpoint, String idioma, int pagina, String termino) throws IOException {
		StringBuilder urlStr = new StringBuilder(endpoint);

		if (!endpoint.contains("?")) {
			urlStr.append("?");
		} else if (!endpoint.endsWith("&") && !endpoint.endsWith("?")) {
			urlStr.append("&");
		}

		urlStr.append("limit=").append(LIMITE);
		urlStr.append("&offset=").append(Math.max(0, pagina) * LIMITE);
		urlStr.append("&index=relevance");

		if (termino != null && !termino.trim().isEmpty()) {
			urlStr.append("&query=").append(URLEncoder.encode(termino.trim(), "UTF-8"));
		}

		urlStr.append("&facets=").append(URLEncoder.encode(obtenerFacetsMods(), "UTF-8"));

		URL url = new URL(urlStr.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setConnectTimeout(20000);
		conn.setReadTimeout(30000);
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("User-Agent", "CrashDetectorMC/1.0");

		int status = conn.getResponseCode();
		if (status != 200) {
			throw new IOException("HTTP " + status + " desde " + urlStr);
		}

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {

			StringBuilder body = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				body.append(line);
			}

			Json.Nodo raiz = Json.leer(body.toString());
			return crearPagina(raiz);
		} finally {
			conn.disconnect();
		}
	}

	protected static PaginaMods crearPagina(Json.Nodo raiz) {
		return new PaginaModsBBSMC(raiz);
	}

	protected static String obtenerFacetsMods() {
		return "[[\"categories:'forge'\",\"categories:'fabric'\",\"categories:'quilt'\","
				+ "\"categories:'liteloader'\",\"categories:'modloader'\",\"categories:'rift'\","
				+ "\"categories:'neoforge'\"],[\"project_type:mod\"]]";
	}
}