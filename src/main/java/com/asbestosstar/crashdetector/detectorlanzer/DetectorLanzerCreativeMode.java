package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorLanzerCreativeMode implements DetectorLanzer {
	@Override
	public String id() {
		return "creativemode";
	}

	@Override
	public boolean animado() {
		return false;
	}

	@Override
	public boolean desanimado() {
		return false;
	}

	@Override
	public boolean detectar(App app, String cmd) {
		return app.equals(App.MINECRAFT) && cmd.contains("-Dminecraft.launcher.brand=creative-mode-launcher");
	}
}