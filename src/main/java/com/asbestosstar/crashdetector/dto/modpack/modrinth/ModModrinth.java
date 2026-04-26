package com.asbestosstar.crashdetector.dto.modpack.modrinth;

import com.asbestosstar.crashdetector.dto.modpack.bbsmc.ModBBSMC;
import com.asbestosstar.crashdetector.json.Json;

public class ModModrinth extends ModBBSMC {

	public ModModrinth(Json.Nodo nodo) {
		super(nodo);
	}

	@Override
	protected String crearEnlaceProyecto(Json.Nodo nodo) {
		String slug = obtenerCadenaSeguro(nodo.obtener("slug"), "");
		String id = obtenerCadenaPrimera(nodo.obtener("project_id"), nodo.obtener("id"), slug);

		if (slug != null && !slug.isEmpty()) {
			return "https://modrinth.com/mod/" + slug;
		}

		if (id != null && !id.isEmpty()) {
			return "https://modrinth.com/mod/" + id;
		}

		return "https://modrinth.com/mods";
	}
}