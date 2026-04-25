package com.asbestosstar.crashdetector.dto.modpack.curseforge;

import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.json.Json;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SolicitudBusquedaModsCurseForge {

	private static final String URL_BASE = ProveedorModsCurseForge.ENDPOINT + "v1/mods/search";
	private static final int ID_JUEGO_MINECRAFT = 432;

	public static PaginaMods buscarMods(String claveApi, String idioma, int pagina, String termino) throws IOException {
		StringBuilder urlStr = new StringBuilder(URL_BASE);
		urlStr.append("?gameId=").append(ID_JUEGO_MINECRAFT).append("&index=").append(pagina * 50)
				.append("&pageSize=50");

		if (termino != null && !termino.trim().isEmpty()) {
//            String codificado = java.net.URLEncoder.encode(termino.trim(), StandardCharsets.UTF_8);
			String codificado = java.net.URLEncoder.encode(termino.trim());

			urlStr.append("&searchFilter=").append(codificado);
		}

		URL url = new URL(urlStr.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("x-api-key", claveApi);
		conn.setRequestProperty("Accept", "application/json");

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
			return new PaginaModsCurseForge(raiz);
		} finally {
			conn.disconnect();
		}
	}
}