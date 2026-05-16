package com.asbestosstar.crashdetector.cargador;

import java.util.Locale;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

/**
 * Cargador para LitLaunch.
 *
 * Deteccion simple: - El cargador esta activo si existe LitLaunch. - Un mod se
 * considera de LitLaunch si alguna clase contiene "tps" en el nombre.
 */
public class CargadorLitLaunch implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {

		for (String archivo : mod.archivos()) {
			String norm = archivo.replace('\\', '/');
			String lower = norm.toLowerCase(Locale.ROOT);

			if (!lower.endsWith(".class")) {
				continue;
			}

			String nombreClase = norm;

			int slash = nombreClase.lastIndexOf('/');
			if (slash >= 0) {
				nombreClase = nombreClase.substring(slash + 1);
			}

			if (nombreClase.toLowerCase(Locale.ROOT).contains("tpsmod")) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean cargadorEsActivado() {
		return Cargador.claseExiste("io.github.codetoil.litlaunch.api.LitLaunch")
				|| Cargador.claseExiste("io.github.codetoil.litlaunch.api.IMod")
				|| Cargador.claseExiste("io.github.codetoil.litlaunch.modloader.ModFinder");
	}

	@Override
	public String id() {
		return "litlaunch";
	}
}