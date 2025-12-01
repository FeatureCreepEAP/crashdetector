package com.asbestosstar.crashdetector.dto.modpack.tlmods;

import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.json.Json;
import java.util.ArrayList;
import java.util.List;

public class RespuestaPaginadaModsTlmods extends PaginaMods {

	private final int paginaActual;
	private final int totalPaginas;
	private final boolean haySiguiente;
	private final List<ModTlmods> listaMods;
	private final long totalElementos;
	private final boolean vacia;

	public RespuestaPaginadaModsTlmods(Json.Nodo nodo) {
		this.paginaActual = nodo.obtener("current").comoEntero();
		this.totalPaginas = nodo.obtener("allPages").comoEntero();
		this.haySiguiente = nodo.obtener("next").comoBooleano();
		this.totalElementos = nodo.obtener("allElements").comoLargo();
		this.vacia = nodo.obtener("empty").comoBooleano();

		Json.Nodo contenido = nodo.obtener("content");
		if (contenido.esArreglo()) {
			this.listaMods = new ArrayList<>();
			for (int i = 0; i < contenido.tamano(); i++) {
				this.listaMods.add(new ModTlmods(contenido.en(i)));
			}
		} else {
			this.listaMods = new ArrayList<>();
		}
	}

	@Override
	public int obtenerPaginaActual() {
		return paginaActual;
	}

	@Override
	public int obtenerTotalPaginas() {
		return totalPaginas;
	}

	@Override
	public boolean haySiguiente() {
		return haySiguiente;
	}

	@Override
	public List<? extends InternetMod> obtenerListaMods() {
		return listaMods;
	}

	@Override
	public long obtenerTotalElementos() {
		return totalElementos;
	}

	@Override
	public boolean esVacia() {
		return vacia;
	}
}