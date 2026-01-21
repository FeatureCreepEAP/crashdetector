package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorFeather implements DetectorLanzer {
	@Override
	public String id() {
		return "feather";
	}

	@Override
	public boolean animado() {
		return false;
	}

	@Override
	public boolean desanimado() {
		return true;
	}// PVP clientes y Feather tienen muchos problemas para mods normales

	@Override
	public boolean detectar(App app, String cmd) {
		if (!app.equals(App.MINECRAFT))
			return false;
	
		return 	buscarClase("net.digitalingot.rustextension.ProxiedStart");

	}
}
