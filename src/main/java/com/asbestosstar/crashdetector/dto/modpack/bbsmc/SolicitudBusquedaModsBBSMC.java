package com.asbestosstar.crashdetector.dto.modpack.bbsmc;

import java.io.IOException;
import java.net.URLEncoder;

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

		Json.Nodo raiz = com.asbestosstar.crashdetector.dto.modpack.ClienteHTTP.getJson(urlStr.toString());
		return crearPagina(raiz);
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