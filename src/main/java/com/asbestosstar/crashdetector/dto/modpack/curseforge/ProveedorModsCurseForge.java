package com.asbestosstar.crashdetector.dto.modpack.curseforge;

import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import java.io.IOException;

public class ProveedorModsCurseForge implements ProveedorMods {

	private final String claveApi;

	public ProveedorModsCurseForge(String claveApi) {
		this.claveApi = claveApi;
	}

	@Override
	public PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException {
		return SolicitudBusquedaModsCurseForge.buscarMods(claveApi, idioma, pagina, termino);
	}

	@Override
	public InternetMod obtenerModPorId(long identificador, String idioma) throws IOException {
		throw new UnsupportedOperationException("No implementado aún");
	}

	@Override
	public String obtenerNombreProveedor() {
		return "CurseForge";
	}
}