package com.asbestosstar.crashdetector.divisor;

import java.nio.file.Path;

public class TLauncherConsolaDivisor implements DivisorDeArchivos {

	@Override
	public int obtenerLineaOriginal(String[] lineas) {
		// TODO Auto-generated method stub

		int ultima = 0;
		for (int i = 0; i < lineas.length - 1; i++) {
			String lin = lineas[i];
			if (lin.contains("[Launcher] Launching Minecraft...") || lin.contains("[MinecraftLauncher] Starting")) {
				ultima = i;
			}

		}

		return ultima;
	}

	@Override
	public boolean predicado(Path archivo, String nada) {
		// TODO Auto-generated method stub
		return archivo.toString().contains("tlauncher") && !archivo.toString().contains("starter");
	}

}
