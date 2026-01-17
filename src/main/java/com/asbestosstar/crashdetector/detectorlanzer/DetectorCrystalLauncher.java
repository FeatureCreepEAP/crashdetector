package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorCrystalLauncher implements DetectorLanzer {
	@Override
	public String id() {
		return "crystal_launcher";
	}

	@Override
	public boolean animado() {
		return false;
	}

	@Override
	public boolean desanimado() {
		return true;
	}// Tiene muchas problemas

	@Override
	public boolean detectar(App app, String cmd) {
		if (!app.equals(App.MINECRAFT))
			return false;
		String userDir = System.getProperty("user.dir", "");
		return userDir.contains("Crystal-Launcher");
	}
}