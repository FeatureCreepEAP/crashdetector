package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorTLLegacyFlatpak implements DetectorLanzer {

	@Override
	public String id() {
		return "tl_legacy_flatpak";
	}

	@Override
	public boolean animado() {
		return false; // desaconsejado
	}

	@Override
	public boolean desanimado() {
		return true;// La verison flatpak es diferente y tiene problemas, especialmente con la
					// consola. Generalmente versiones de apps de flatpak son inferior a las
					// versiones nativas
	}

	@Override
	public boolean detectar(App app, String cmd) {
		if (!app.equals(App.MINECRAFT)) {
			return false;
		}

		// Verificar que se ejecuta dentro de un entorno Flatpak
		String userDir = System.getProperty("user.dir", "");
		boolean enFlatpak = userDir.contains(".var/app/");

		if (!enFlatpak) {
			return false;
		}

		// Verificar que es TLauncher Legacy (por classpath en cmd)
		String normalizedCmd = cmd.replace('\\', '/');
		return normalizedCmd.contains(".tlauncher/legacy/Minecraft/game/");
	}
}