package com.asbestosstar.crashdetector.dto.modpack.curseforge;

import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;

import java.util.ArrayList;
import java.util.List;

public class PaginaModsCurseForge extends PaginaMods {

	private final int paginaActual;
	private final int totalPaginas;
	private final boolean haySiguiente;
	private final List<ModCurseForge> listaMods;
	private final long totalElementos;
	private final boolean vacia;

	public PaginaModsCurseForge(Json.Nodo nodo) {
		Json.Nodo paginacion = nodo.obtener("pagination");
		int indice = paginacion.obtener("index").comoEntero();
		int tamanoPagina = paginacion.obtener("pageSize").comoEntero();
		int total = paginacion.obtener("totalCount").comoEntero();

		this.paginaActual = indice / Math.max(1, tamanoPagina);
		this.totalPaginas = (int) Math.ceil((double) total / tamanoPagina);
		this.haySiguiente = (indice + tamanoPagina) < total;
		this.totalElementos = total;
		this.vacia = total == 0;

		Json.Nodo contenido = nodo.obtener("data");
		if (contenido.esArreglo()) {
			this.listaMods = new ArrayList<>();
			for (int i = 0; i < contenido.tamano(); i++) {
				this.listaMods.add(new ModCurseForge(contenido.en(i)));
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