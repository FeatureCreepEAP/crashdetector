package com.asbestosstar.crashdetector.dto.modpack.modrinth;

import java.io.IOException;
import java.net.URLEncoder;

import com.asbestosstar.crashdetector.config.json.Json;

public class SolicitudHTTPModrinth {

	private static final int LIMITE = 20;

	public static Json.Nodo buscarJson(String endpoint, int pagina, String termino) throws IOException {
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

		java.util.Map<String, String> headers = new java.util.HashMap<String, String>();
		headers.put("User-Agent", "CrashDetectorMC/1.0 (asbestosstar)");
		return com.asbestosstar.crashdetector.dto.modpack.ClienteHTTP.getJsonSinCache(urlStr.toString(), headers);
	}

	private static String obtenerFacetsMods() {
		return "[[\"categories:'forge'\",\"categories:'fabric'\",\"categories:'quilt'\","
				+ "\"categories:'liteloader'\",\"categories:'modloader'\",\"categories:'rift'\","
				+ "\"categories:'neoforge'\"],[\"project_type:mod\"]]";
	}
}