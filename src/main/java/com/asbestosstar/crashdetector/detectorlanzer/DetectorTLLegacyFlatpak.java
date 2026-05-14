package com.asbestosstar.crashdetector.detectorlanzer;

import java.io.File;

import com.asbestosstar.crashdetector.App;
import com.asbestosstar.crashdetector.CrashDetectorLogger;

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
		if (!App.MINECRAFT.equals(app)) {
			return false;
		}

		String userDir = normalizar(System.getProperty("user.dir", ""));
		String cmdNorm = normalizar(cmd);
		String flatpakId = System.getenv("FLATPAK_ID");

		boolean enFlatpak =
				flatpakId != null
				|| new File("/.flatpak-info").exists()
				|| userDir.startsWith("/var/data/")
				|| userDir.contains(".var/app/");

		boolean esTLFlatpak =
				"ch.tlaun.TL".equals(flatpakId)
				|| cmdNorm.contains("ch.tlaun.TL")
				|| userDir.contains("ch.tlaun.TL");

		boolean esTLLegacy =
				cmdNorm.contains(".tlauncher/legacy/Minecraft/game/")
				|| userDir.contains(".tlauncher/legacy/Minecraft/game/");

		CrashDetectorLogger.log("[DetectorTLLegacyFlatpak] userDir=" + userDir);
		CrashDetectorLogger.log("[DetectorTLLegacyFlatpak] FLATPAK_ID=" + flatpakId);
		CrashDetectorLogger.log("[DetectorTLLegacyFlatpak] enFlatpak=" + enFlatpak);
		CrashDetectorLogger.log("[DetectorTLLegacyFlatpak] esTLFlatpak=" + esTLFlatpak);
		CrashDetectorLogger.log("[DetectorTLLegacyFlatpak] esTLLegacy=" + esTLLegacy);

		return enFlatpak && (esTLFlatpak || esTLLegacy);
	}

	private static String normalizar(String s) {
		return s == null ? "" : s.replace('\\', '/');
	}
	
	
}