package com.asbestosstar.crashdetector.dto.modpack.minecraftstorage;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.json.Json;

public class RespuestaPaginadaModsMinecraftStorage extends PaginaMods {

	private final List<ModMinecraftStorage> listaMods;
	private final String idioma;

	public RespuestaPaginadaModsMinecraftStorage(Json.Nodo nodo, String idioma) {
		this.listaMods = new ArrayList<>();
		this.idioma = idioma;

		if (nodo != null && nodo.esArreglo()) {
			for (int i = 0; i < nodo.tamano(); i++) {
				listaMods.add(new ModMinecraftStorage(nodo.en(i), idioma));
			}
		}
	}

	@Override
	public int obtenerPaginaActual() {
		return 0;
	}

	@Override
	public int obtenerTotalPaginas() {
		return 1;
	}

	@Override
	public boolean haySiguiente() {
		return false;
	}

	@Override
	public List<? extends InternetMod> obtenerListaMods() {
		return listaMods;
	}

	@Override
	public long obtenerTotalElementos() {
		return listaMods.size();
	}

	@Override
	public boolean esVacia() {
		return listaMods.isEmpty();
	}

	public String obtenerIdioma() {
		return idioma;
	}
}