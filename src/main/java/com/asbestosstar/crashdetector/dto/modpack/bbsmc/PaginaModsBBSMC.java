package com.asbestosstar.crashdetector.dto.modpack.bbsmc;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.json.Json;

public class PaginaModsBBSMC extends PaginaMods {

	private final int paginaActual;
	private final int totalPaginas;
	private final boolean haySiguiente;
	private final List<ModBBSMC> listaMods;
	private final long totalElementos;
	private final boolean vacia;

	public PaginaModsBBSMC(Json.Nodo nodo) {
		int offset = obtenerEnteroSeguro(nodo.obtener("offset"), 0);
		int limit = obtenerEnteroSeguro(nodo.obtener("limit"), 20);
		int total = obtenerEnteroSeguro(nodo.obtener("total_hits"), 0);

		this.paginaActual = limit <= 0 ? 0 : offset / limit;
		this.totalPaginas = limit <= 0 ? 0 : (int) Math.ceil((double) total / (double) limit);
		this.haySiguiente = (offset + limit) < total;
		this.totalElementos = total;
		this.vacia = total == 0;

		this.listaMods = new ArrayList<ModBBSMC>();

		Json.Nodo hits = nodo.obtener("hits");
		if (hits != null && hits.esArreglo()) {
			for (int i = 0; i < hits.tamano(); i++) {
				listaMods.add(crearMod(hits.en(i)));
			}
		}
	}

	protected ModBBSMC crearMod(Json.Nodo nodo) {
		return new ModBBSMC(nodo);
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

	private static int obtenerEnteroSeguro(Json.Nodo nodo, int def) {
		try {
			return nodo.comoEntero();
		} catch (Throwable t) {
			return def;
		}
	}
}