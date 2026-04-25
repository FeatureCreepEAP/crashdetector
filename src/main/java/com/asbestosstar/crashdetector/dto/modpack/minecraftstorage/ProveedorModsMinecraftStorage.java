package com.asbestosstar.crashdetector.dto.modpack.minecraftstorage;

import java.io.IOException;

import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;

public class ProveedorModsMinecraftStorage implements ProveedorMods {

	public static ConfigString ENDPOINT = ConfigString.de("minecraftstorage.endpoint",
			"https://api.minecraftstorage.com/");

	@Override
	public boolean soportaBusqueda() {
		return true;
	}

	@Override
	public PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException {
		return SolicitudBusquedaModsMinecraftStorage.buscarMods(idioma, pagina, termino);
	}

	@Override
	public InternetMod obtenerModPorId(long identificador, String idioma) throws IOException {
		throw new UnsupportedOperationException("No implementado aún");
	}

	@Override
	public String obtenerNombreProveedor() {
		return "MinecraftStorage";
	}
}