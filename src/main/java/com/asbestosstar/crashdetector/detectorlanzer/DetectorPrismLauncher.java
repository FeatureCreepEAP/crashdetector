package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorPrismLauncher implements DetectorLanzer {
	@Override
	public String id() {
		return "prism_launcher";
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
		try {
			Class.forName("org.prismlauncher.launcher.Launcher", false, this.getClass().getClassLoader());
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}