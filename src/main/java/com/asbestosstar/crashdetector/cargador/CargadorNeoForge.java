package com.asbestosstar.crashdetector.cargador;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

public class CargadorNeoForge implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {
		// Se recorre la lista de rutas internas del mod.
		for (String archivo : mod.archivos()) {
			if (archivo == null || archivo.isEmpty()) {
				continue;
			}

			String ruta = archivo.indexOf('\\') >= 0 ? archivo.replace('\\', '/') : archivo;

			// Detección de neoforge.mods.toml en META-INF.
			if (ruta.equals("META-INF/neoforge.mods.toml")) {
				return true;
			}

			// Detección de servicios bajo META-INF.
			if (ruta.startsWith("META-INF/services/")) {
				String servicio = ruta.substring("META-INF/services/".length());

				if (servicio.startsWith("cpw") || servicio.startsWith("net.neoforged")) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean cargadorEsActivado() {
		// es comun en versiones actuales y 1.13
		return Cargador.claseExiste("cpw.mods.modlauncher.api.TargetType")
				&& Cargador.claseExiste("cpw.mods.modlauncher.api.ITransformationService")
				|| Cargador.claseExiste("net.neoforged.neoforgespi.transformation.ClassProcessor");// TargetType solo
																									// existe en ML de
																									// CPW en NeoForge y
																									// Pillow. No creo
																									// otra cargadores
																									// usa esta version
																									// de ML y ML
																									// independente es
																									// decrecado
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "neoforge";
	}

}
