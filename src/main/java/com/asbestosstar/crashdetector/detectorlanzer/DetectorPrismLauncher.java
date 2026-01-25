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
		// Verifica si la aplicación es Minecraft
		if (!app.equals(App.MINECRAFT)) {
			return false;
		}

		// Llama al nuevo método para buscar la clase en múltiples class loaders
		return buscarClase("org.prismlauncher.EntryPoint");
	}

}