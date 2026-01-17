package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorTheseus implements DetectorLanzer {
	@Override
	public String id() {
		return "theseus";
	}

	@Override
	public boolean animado() {
		return false;
	}

	@Override
	public boolean desanimado() {
		return true;
	}// Spyware y generalmente tiene muchas problemas

	@Override
	public boolean detectar(App app, String cmd) {
		return app.equals(App.MINECRAFT) && cmd.contains("-Dminecraft.launcher.brand=theseus");
	}
}