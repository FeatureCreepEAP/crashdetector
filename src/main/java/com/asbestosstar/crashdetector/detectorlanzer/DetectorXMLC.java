package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorXMLC implements DetectorLanzer {
	@Override
	public String id() {
		return "xmlc";
	}

	@Override
	public boolean animado() {
		return true;
	}// Mystic Pasta dice esta bien. Tiene mas poder de otras lanzeres electron, pero
		// similar a otras lanzeres electron es lento

	@Override
	public boolean desanimado() {
		return false;
	}

	@Override
	public boolean detectar(App app, String cmd) {
		return app.equals(App.MINECRAFT) && cmd.contains("-Dminecraft.launcher.brand=xmcl");
	}
}
