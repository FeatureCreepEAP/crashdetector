package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorTL implements DetectorLanzer {
	@Override
	public String id() {
		return "tlauncher";
	}

	@Override
	public boolean animado() {
		return true;
	}

	@Override
	public boolean desanimado() {
		return false;
	}

	@Override
	public boolean detectar(App app, String cmd) {
		if (!app.equals(App.MINECRAFT))
			return false;
		String n = cmd.toLowerCase().replace('\\', '/');
		return n.contains("/org/tlauncher/patchy/") || n.contains("/org/tlauncher/authlib/");// No es perfecta ,
																								// especialmente para
																								// versiones
																								// personalizado, pero
																								// esta bien para ahora
	}
}