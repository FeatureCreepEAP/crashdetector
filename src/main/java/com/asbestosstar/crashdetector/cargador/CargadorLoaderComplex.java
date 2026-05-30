package com.asbestosstar.crashdetector.cargador;

import java.util.Locale;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

/**
 * Cargador para LoaderComplex.
 */
public class CargadorLoaderComplex implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {

		String ubicacion = mod.ubicacion();

		if (ubicacion == null || ubicacion.isEmpty()) {
			return false;
		}

		String norm = ubicacion.replace('\\', '/');
		String lower = norm.toLowerCase(Locale.ROOT);

		// LoaderComplex carga desde:
		// - mods/*.lc.jar
		// - addons/*.jar
		if (lower.endsWith(".lc.jar")) {
			return true;
		}

		return estaEnCarpetaAddons(lower) && lower.endsWith(".jar");
	}

	@Override
	public boolean cargadorEsActivado() {
		return Cargador.claseExiste("com.enderzombi102.loadercomplex.api.Loader")
				|| Cargador.claseExiste("com.enderzombi102.loadercomplex.api.Addon")
				|| Cargador.claseExiste("com.enderzombi102.loadercomplex.modloader.AddonLoader")
				|| Cargador.claseExiste("com.enderzombi102.loadercomplex.modloader.AddonContainer");
	}

	@Override
	public String id() {
		return "loadercomplex";
	}

	private static boolean estaEnCarpetaAddons(String ruta) {
		return ruta.contains("/addons/");
	}
}