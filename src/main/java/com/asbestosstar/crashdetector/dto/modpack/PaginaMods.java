package com.asbestosstar.crashdetector.dto.modpack;

import java.util.List;

public abstract class PaginaMods {

	public abstract int obtenerPaginaActual();

	public abstract int obtenerTotalPaginas();

	public abstract boolean haySiguiente();

	public abstract List<? extends InternetMod> obtenerListaMods();

	public abstract long obtenerTotalElementos();

	public abstract boolean esVacia();
}