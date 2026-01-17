package com.asbestosstar.crashdetector.detectorlanzer;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import com.asbestosstar.crashdetector.App;

public class DetectorMinecraftLauncher implements DetectorLanzer {

	// Hora de inicio real de la máquina virtual (en milisegundos desde la época)
	private static final long horaInicioJvm = obtenerHoraInicioJvm();

	/**
	 * Obtiene la marca de tiempo de inicio de la JVM usando RuntimeMXBean.
	 * 
	 * @return tiempo en milisegundos desde epoch cuando la JVM inició
	 */
	private static long obtenerHoraInicioJvm() {
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		return bean.getStartTime();
	}

	@Override
	public String id() {
		return "minecraft_launcher"; // ID técnico → snake_case, sin traducir (como identificador interno)
	}

	@Override
	public boolean animado() {
		return true; // launcher oficial → recomendado
	}

	@Override
	public boolean desanimado() {
		return false;
	}

	@Override
	public boolean detectar(App app, String cmd) {// Esta solucion no esta perfecta
		if (!app.equals(App.MINECRAFT)) {
			return false;
		}

		// Verificar que la marca coincida con el launcher oficial
		if (!cmd.contains("-Dminecraft.launcher.brand=minecraft-launcher")) {
			return false;
		}

		// Buscar el archivo distintivo en el directorio actual
		String directorioUsuario = System.getProperty("user.dir", "");
		File archivoLog = new File(directorioUsuario, "launcher_log.txt");

		if (!archivoLog.exists() || !archivoLog.isFile()) {
			return false;
		}

		// Asegurar que el archivo fue modificado DESPUÉS del inicio de la JVM
		long ultimaModificacion = archivoLog.lastModified();
		return ultimaModificacion > horaInicioJvm;
	}
}