package com.asbestosstar.crashdetector.dto.modpack.minecraftstorage;

import java.io.IOException;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;

public class SolicitudBusquedaModsMinecraftStorage {

	private static final String AGENTE_USUARIO = "Mozilla/5.0";

	public static PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException {

		String idiomaNormalizado = normalizarIdioma(idioma);

		StringBuilder constructorUrl = new StringBuilder();
		constructorUrl.append(ProveedorModsMinecraftStorage.ENDPOINT.obtener());
		constructorUrl.append("posts/suggest?keyword=");

		if (termino != null && !termino.trim().isEmpty()) {
			constructorUrl.append(java.net.URLEncoder.encode(termino.trim(), "UTF-8"));
		}

		String urlCompleta = constructorUrl.toString();

		java.util.Map<String, String> headers = new java.util.HashMap<String, String>();
		headers.put("User-Agent", AGENTE_USUARIO);
		headers.put("Origin", "https://minecraftstorage.com");
		headers.put("Referer", "https://minecraftstorage.com/");
		headers.put("Accept-Language", idiomaNormalizado);

		Json.Nodo raiz = com.asbestosstar.crashdetector.dto.modpack.ClienteHTTP.getJsonSinCache(urlCompleta, headers);
		return new RespuestaPaginadaModsMinecraftStorage(raiz, idiomaNormalizado);
	}

	private static String normalizarIdioma(String idioma) {

		if (idioma == null) {
			return "en";
		}

		String i = idioma.trim().toLowerCase();

		if (i.startsWith("es")) {
			return "es";
		}

		if (i.startsWith("ru")) {
			return "ru";
		}

		return "en";
	}
}