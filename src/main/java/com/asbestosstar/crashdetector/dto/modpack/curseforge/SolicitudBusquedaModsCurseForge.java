package com.asbestosstar.crashdetector.dto.modpack.curseforge;

import java.io.IOException;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;

public class SolicitudBusquedaModsCurseForge {

	private static final String URL_BASE = ProveedorModsCurseForge.ENDPOINT + "v1/mods/search";
	private static final int ID_JUEGO_MINECRAFT = 432;

	public static PaginaMods buscarMods(String claveApi, String idioma, int pagina, String termino) throws IOException {
		StringBuilder urlStr = new StringBuilder(URL_BASE);
		urlStr.append("?gameId=").append(ID_JUEGO_MINECRAFT).append("&index=").append(pagina * 50)
				.append("&pageSize=50");

		if (termino != null && !termino.trim().isEmpty()) {
			String codificado = java.net.URLEncoder.encode(termino.trim());
			urlStr.append("&searchFilter=").append(codificado);
		}

		java.util.Map<String, String> headers = new java.util.HashMap<String, String>();
		headers.put("x-api-key", claveApi);
		Json.Nodo raiz = com.asbestosstar.crashdetector.dto.modpack.ClienteHTTP.getJsonSinCache(urlStr.toString(), headers);
		return new PaginaModsCurseForge(raiz);
	}
}