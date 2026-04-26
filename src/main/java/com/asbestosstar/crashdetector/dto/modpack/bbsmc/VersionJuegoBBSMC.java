package com.asbestosstar.crashdetector.dto.modpack.bbsmc;

import com.asbestosstar.crashdetector.dto.modpack.VersionJuego;

public class VersionJuegoBBSMC extends VersionJuego {

	private final long identificador;
	private final String nombre;

	public VersionJuegoBBSMC(String nombre) {
		this.identificador = 0L;
		this.nombre = nombre;
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