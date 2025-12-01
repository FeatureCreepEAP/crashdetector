package com.asbestosstar.crashdetector.dto.modpack.curseforge;

import com.asbestosstar.crashdetector.dto.modpack.VersionJuego;

public class VersionJuegoCurseForge extends VersionJuego {

	private final long identificador; // falso, no existe en CF
	private final String nombre;

	public VersionJuegoCurseForge(String nombre) {
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