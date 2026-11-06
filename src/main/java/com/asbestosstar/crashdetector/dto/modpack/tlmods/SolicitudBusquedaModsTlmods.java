package com.asbestosstar.crashdetector.dto.modpack.tlmods;

import java.io.IOException;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;

public class SolicitudBusquedaModsTlmods {

	private static final String URL_BASE = ProveedorModsTlmods.ENDPOINT.obtener() + "api/client/gameentities";
	private static final String AGENTE_USUARIO = "TLauncher/2.9365";

	public static PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException {
		StringBuilder constructorUrl = new StringBuilder(URL_BASE);
		constructorUrl.append("?type=MOD").append("&lang=").append(idioma).append("&page=").append(pagina)
				.append("&sort=POPULAR").append("&lv=2.9365");

		if (termino != null && !termino.trim().isEmpty()) {
			String codificado = java.net.URLEncoder.encode(termino.trim(), "UTF-8");
			constructorUrl.append("&search=").append(codificado);
		}

		String urlCompleta = constructorUrl.toString();
		java.util.Map<String, String> headers = new java.util.HashMap<String, String>();
		headers.put("User-Agent", AGENTE_USUARIO);
		Json.Nodo raiz = com.asbestosstar.crashdetector.dto.modpack.ClienteHTTP.getJsonSinCache(urlCompleta, headers);
		return new RespuestaPaginadaModsTlmods(raiz);
	}
}