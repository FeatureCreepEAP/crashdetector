package com.asbestosstar.crashdetector.divisor;

import java.io.File;
import java.nio.file.Path;

public class TLauncherConsolaDivisor implements DivisorDeArchivos {

	@Override
	public int obtenerLineaOriginal(String contentido_existente) {
		// TODO Auto-generated method stub
		
		String[] lineas = contentido_existente.split(File.pathSeparator);
		for (int i = 0; i < lineas.length - 1; i++) {
			String lin = lineas[i];
			if (lin.contains("[Launcher] Launching Minecraft...")||lin.contains("[MinecraftLauncher] Starting")) {
				return i;
			}

		}
		
		return 0;
	}

	@Override
	public boolean predicado(Path archivo) {
		// TODO Auto-generated method stub
		return archivo.toString().contains("tlauncher") && !archivo.toString().contains("starter");
	}

}
