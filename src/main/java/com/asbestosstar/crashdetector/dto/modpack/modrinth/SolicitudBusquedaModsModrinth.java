package com.asbestosstar.crashdetector.dto.modpack.modrinth;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.bbsmc.SolicitudBusquedaModsBBSMC;

public class SolicitudBusquedaModsModrinth extends SolicitudBusquedaModsBBSMC {

	protected static PaginaMods crearPaginaModrinth(Json.Nodo raiz) {
		return new PaginaModsModrinth(raiz);
	}

	public static PaginaMods buscarMods(String endpoint, String idioma, int pagina, String termino)
			throws java.io.IOException {

		Json.Nodo raiz = SolicitudHTTPModrinth.buscarJson(endpoint, pagina, termino);
		return new PaginaModsModrinth(raiz);
	}
}