package com.asbestosstar.crashdetector.dto.modpack.modrinth;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.bbsmc.ModBBSMC;
import com.asbestosstar.crashdetector.dto.modpack.bbsmc.PaginaModsBBSMC;

public class PaginaModsModrinth extends PaginaModsBBSMC {

	public PaginaModsModrinth(Json.Nodo nodo) {
		super(nodo);
	}

	@Override
	protected ModBBSMC crearMod(Json.Nodo nodo) {
		return new ModModrinth(nodo);
	}
}