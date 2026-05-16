package com.asbestosstar.crashdetector.cargador;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

/**
 * Cargador para M3L.
 *
 * Solo detecta si M3L esta presente en el entorno.
 */
public class CargadorM3L implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {
		return false;
	}

	@Override
	public boolean cargadorEsActivado() {
		return Cargador.claseExiste("cuchaz.m3l.M3L") || Cargador.claseExiste("cuchaz.m3l.MainLauncher")
				|| Cargador.claseExiste("cuchaz.m3l.tweaker.TweakerClient")
				|| Cargador.claseExiste("cuchaz.m3l.tweaker.TweakerServer");
	}

	@Override
	public String id() {
		return "m3l";
	}
}