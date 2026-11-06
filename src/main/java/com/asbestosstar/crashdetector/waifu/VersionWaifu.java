package com.asbestosstar.crashdetector.waifu;

import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaWaifuAPI" },
		nota = "Cubre constructor de VersionWaifu con diferentes cargadores")
public class VersionWaifu {

	public String cargador;
	public String version_del_juego;

	/**
	 * https://waifu.neoforged.net/a/neoforged-waifuvis-app/mods?var-version=1.21.1-fabric&var-filters=
	 * Necesita estar aqui
	 * 
	 * @param cargador
	 * @param version_del_juego
	 */
	public VersionWaifu(String cargador, String version_del_juego) {
		// TODO Auto-generated constructor stub
		this.cargador = cargador;
		this.version_del_juego = version_del_juego;
	}

}
