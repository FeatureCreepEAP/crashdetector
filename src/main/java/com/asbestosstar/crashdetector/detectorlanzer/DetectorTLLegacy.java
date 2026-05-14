package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorTLLegacy implements DetectorLanzer {
	@Override
	public String id() {
		return "tl_legacy";
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
		String n = cmd.replace('\\', '/');
		return n.contains("tlauncher/legacy/Minecraft/game/") || n.contains("ch.tlaun.TL");

	}
}