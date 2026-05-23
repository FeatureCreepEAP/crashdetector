package com.asbestosstar.crashdetector.dto.modpack.tlmods;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.VersionJuego;

public class VersionJuegoTlmods extends VersionJuego {

	private final long identificador;
	private final String nombre;

	public VersionJuegoTlmods(Json.Nodo nodo) {
		this.identificador = nodo.obtener("id").comoLargo();
		this.nombre = nodo.obtener("name").comoCadena();
	}

	@Override
	public long obtenerIdentificador() {
		return identificador;
	}

	@Override
	public String obtenerNombre() {
		return nombre;
	}
}