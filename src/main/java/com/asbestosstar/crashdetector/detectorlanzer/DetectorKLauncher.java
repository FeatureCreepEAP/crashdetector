package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorKLauncher implements DetectorLanzer {
	@Override
	public String id() {
		return "klauncher";
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
		if (!app.equals(App.MINECRAFT))
			return false;
		String n = cmd.replace('\\', '/');
		return n.contains("/libraries/com/mojang/authlib/kl/");
	}
}