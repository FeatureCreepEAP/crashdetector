package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorMultiMC implements DetectorLanzer {
	@Override
	public String id() {
		return "multimc";
	}

	@Override
	public boolean animado() {
		return false;
	}

	@Override
	public boolean desanimado() {
		return true;
	}// Obsoleta,Prism es mas nueva, MultiMC tiene problemas con Args con
		// CrashDetector. CDLauncher y verificaciones de args no funcionan

	@Override
	public boolean detectar(App app, String cmd) {
		if (!app.equals(App.MINECRAFT))
			return false;
		try {
			Class.forName("org.multimc.onesix.OneSixLauncher", false, this.getClass().getClassLoader());
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}