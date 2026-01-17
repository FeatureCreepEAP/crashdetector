package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorCurseMojang implements DetectorLanzer {
	@Override
	public String id() {
		return "cursed_y_mojang";
	}

	@Override
	public boolean animado() {
		return false;
	}

	@Override
	public boolean desanimado() {
		return false;
	}// Tiene problemas con instalaciones corruptos y esta lento pero es menos mala
		// en comparación con otra lanzeres

	@Override
	public boolean detectar(App app, String cmd) {
		if (!app.equals(App.MINECRAFT))
			return false;
		String n = cmd.toLowerCase().replace('\\', '/');
		return n.contains("/curseforge/minecraft/install/libraries") || n.contains("curseforge");
	}
}
