package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

import java.io.File;

import com.asbestosstar.crashdetector.Statics;

public class CwtoolsCarpetas {

	public static File carpetaCwtoolsLsp() {
		File carpeta = Statics.carpeta_mundial.resolve("cwtools_lsp").toFile();

		if (!carpeta.exists()) {
			carpeta.mkdirs();
		}

		return carpeta;
	}

	public static File carpetaConfigsCwtools() {
		File carpeta = new File(carpetaCwtoolsLsp(), "configs");

		if (!carpeta.exists()) {
			carpeta.mkdirs();
		}

		crearCarpetasConfigCwtools(carpeta);

		return carpeta;
	}

	public static void crearCarpetasConfigCwtools(File carpetaConfigs) {
		String[] configs = new String[] { "vic2-config", "vic3-config", "ck2-config", "eu4-config", "hoi4-config",
				"stellaris-config", "ir-config", "ck3-config" };

		for (String nombre : configs) {
			File carpeta = new File(carpetaConfigs, nombre);

			if (!carpeta.exists()) {
				carpeta.mkdirs();
			}
		}
	}
}