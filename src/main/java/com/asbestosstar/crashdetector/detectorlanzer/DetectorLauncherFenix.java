package com.asbestosstar.crashdetector.detectorlanzer;

import java.io.File;

import com.asbestosstar.crashdetector.App;

public class DetectorLauncherFenix implements DetectorLanzer {

	// 8 horas en milisegundos
	private static final long OCHO_HORAS_EN_MILISEGUNDOS = 8 * 60 * 60 * 1000L;

	@Override
	public String id() {
		return "launcherfenix"; // ID técnico en snake_case
	}

	@Override
	public boolean animado() {
		return false; // launcher de terceros → desaconsejado
	}

	@Override
	public boolean desanimado() {
		return true;// Obsoleta y tiene problemas
	}

	@Override
	public boolean detectar(App app, String cmd) {// Esta solucion no esta perfeca
		if (!app.equals(App.MINECRAFT)) {
			return false;
		}

		// Determinar la carpeta predeterminada de Minecraft según el sistema operativo
		String directorioMinecraft = obtenerDirectorioMinecraftPredeterminado();
		if (directorioMinecraft == null) {
			return false;
		}

		// Ruta completa al archivo launcherfenix.jar
		File archivoLauncherFenix = new File(directorioMinecraft, "launcherfenix.jar");

		if (!archivoLauncherFenix.exists() || !archivoLauncherFenix.isFile()) {
			return false;
		}

		// Verificar si fue modificado en las últimas 8 horas
		long ultimaModificacion = archivoLauncherFenix.lastModified();
		long ahora = System.currentTimeMillis();
		return (ahora - ultimaModificacion) <= OCHO_HORAS_EN_MILISEGUNDOS;
	}

	/**
	 * Obtiene la ruta a la carpeta predeterminada de Minecraft según el sistema
	 * operativo.
	 * 
	 * @return ruta absoluta a la carpeta .minecraft, o null si no se puede
	 *         determinar
	 */
	private static String obtenerDirectorioMinecraftPredeterminado() {
		String sistemaOperativo = System.getProperty("os.name", "").toLowerCase();

		if (sistemaOperativo.contains("win")) {
			// Windows: %APPDATA%\.minecraft
			String appData = System.getenv("APPDATA");
			if (appData != null) {
				return new File(appData, ".minecraft").getAbsolutePath();
			}
		} else if (sistemaOperativo.contains("mac")) {
			// macOS: ~/Library/Application Support/minecraft
			String userHome = System.getProperty("user.home");
			if (userHome != null) {
				return new File(userHome, "Library/Application Support/minecraft").getAbsolutePath();
			}
		} else {
			// Asumir Linux/Unix: ~/.minecraft
			String userHome = System.getProperty("user.home");
			if (userHome != null) {
				return new File(userHome, ".minecraft").getAbsolutePath();
			}
		}

		return null;
	}
}