package com.asbestosstar.crashdetector;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;

public class EscanerMCreator {

	public static String obtainerMCreatorMods() {
		StringBuilder resultado = new StringBuilder();
		Buscador.cargar(); // Carga todos los mods

		List<ArchivoDeMod> todosLosMods = new ArrayList<>();
		// Recopilar todos los mods incluyendo anidados
		for (ArchivoDeMod mod : Buscador.obtenerTodosLosModsYSubmodsRecursivos()) {
			coleccionarModsAnidados(mod, todosLosMods);
		}

		List<ArchivoDeMod> altaPrioridad = new ArrayList<>();
		List<ArchivoDeMod> menosAltaPrioridad = new ArrayList<>();
		List<ArchivoDeMod> mediaPrioridad = new ArrayList<>();

		for (ArchivoDeMod mod : todosLosMods) {
			boolean esAlta = false;
			boolean esMenosAlta = false;
			boolean esMedia = false;

			// Verificación de media prioridad (solo si no es alta)
			if (!esAlta && mod.MetaDataTieneReferenciaDeMCReator()) {
				esMenosAlta = true;
			}

			for (String clase : mod.clases()) {
				int ultimoPunto = clase.lastIndexOf('/');
				if (ultimoPunto != -1) {
					String paquete = clase.substring(0, ultimoPunto);
					// CrashDetectorLogger.log(paquete);

					// Verificación de alta prioridad
					if (paquete.startsWith("net/mcreator")) {
						esAlta = true;
						break; // Prioridad máxima, no seguimos revisando
					}

					// Verificación de media prioridad (solo si no es alta)
					if (!esAlta && !esMenosAlta && (paquete.endsWith("/procedures")
							|| paquete.endsWith("/elements") && !esAlta && !esMenosAlta)) {
						esMedia = true;
					}
				}
			}

			if (esAlta) {
				altaPrioridad.add(mod);
			} else if (esMenosAlta) {
				menosAltaPrioridad.add(mod);
			}

			else if (esMedia) {
				mediaPrioridad.add(mod);
			}
		}

		// Construir resultado
		resultado.append("Resultados del análisis MCreator:\n");

		if (!altaPrioridad.isEmpty()) {
			resultado.append("\n--- Alta Prioridad (net.mcreator) ---\n");
			for (ArchivoDeMod mod : altaPrioridad) {
				resultado.append(Buscador.rutaParaPublicar(mod.ubicacion())).append("\n");
			}
		}

		if (!menosAltaPrioridad.isEmpty()) {
			resultado.append(
					"\n--- Menos Alta Prioridad (Referencia a mcreator en fabric.mod.json/mods.toml/modules.xml/descriptor.mod/neoforge.mods.toml) ---\n");
			for (ArchivoDeMod mod : menosAltaPrioridad) {
				resultado.append(Buscador.rutaParaPublicar(mod.ubicacion())).append("\n");
			}
		}

		if (!mediaPrioridad.isEmpty()) {
			resultado.append("\n--- Media Prioridad (procedures/elements) ---\n");
			for (ArchivoDeMod mod : mediaPrioridad) {
				resultado.append(Buscador.rutaParaPublicar(mod.ubicacion())).append("\n");
			}
		}

		if (altaPrioridad.isEmpty() && menosAltaPrioridad.isEmpty() && mediaPrioridad.isEmpty()) {
			resultado.append("\nNo se encontraron mods de MCreator");
		}

		return resultado.toString();
	}

	/**
	 * Recursivamente colecciona todos los mods anidados
	 */
	private static void coleccionarModsAnidados(ArchivoDeMod mod, List<ArchivoDeMod> coleccion) {
		coleccion.add(mod);
		for (ArchivoDeMod submod : mod.mods_en_mods()) {
			coleccionarModsAnidados(submod, coleccion);
		}
	}
}