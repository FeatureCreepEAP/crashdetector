package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorSKLauncher implements DetectorLanzer {

	public static final String ID = "sklauncher";

	@Override
	public String id() {
		return ID;
	}

	@Override
	public boolean animado() {
		return false;
	}

	@Override
	public boolean desanimado() {
		return true;
	}// La agente tiene una posibilidad de casando una problema

	@Override
	public boolean detectar(App app, String cmd) {
		if (!app.equals(App.MINECRAFT))
			return false;
		return cmd.contains("-Dsklauncher.minecraft=") || cmd.contains("-Dsklauncher.dns=true");
	}
}
