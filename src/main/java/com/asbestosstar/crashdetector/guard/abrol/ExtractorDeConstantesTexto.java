package com.asbestosstar.crashdetector.guard.abrol;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;

/**
 * Adaptador central para extracción de constantes de texto.
 *
 * Idea: - No reimplementar el análisis de bytecode aquí. - Reutilizar el
 * Buscardor para asegurar la carga y precarga de clases. - Reutilizar
 * ArchivoDeMod.buscarConstantesEnMetodo(...) para obtener constantes.
 *
 * Comportamiento: - Si se pasa un mod concreto, solo analiza ese mod. - Si se
 * pasa mod == null, intenta buscar la clase entre todos los mods cargados por
 * el Buscardor y agrega resultados de todos los que la contengan. - Solo
 * devuelve constantes cuyo valor sea String. - Elimina duplicados manteniendo
 * el orden de aparición.
 */
public class ExtractorDeConstantesTexto {

	/**
	 * Extrae todas las constantes de texto encontradas en un método concreto.
	 *
	 * @param mod          Mod a analizar. Puede ser null.
	 * @param claseInterna Nombre de clase en formato interno, por ejemplo:
	 *                     "com/ejemplo/MiClase"
	 * @param metodo       Nombre del método
	 * @param descriptor   Descriptor JVM del método
	 * @return Lista de cadenas encontradas en el método
	 */
	public static List<String> extraerConstantesTexto(ArchivoDeMod mod, String claseInterna, String metodo,
			String descriptor) {
		Set<String> resultado = new LinkedHashSet<String>();

		try {
			// Asegura que los mods estén cargados y que las clases estén precacheadas.
			Buscardor.cargarYPrecargarClasesEnCache();

			if (claseInterna == null || claseInterna.trim().isEmpty()) {
				return new ArrayList<String>();
			}

			if (metodo == null || metodo.trim().isEmpty()) {
				return new ArrayList<String>();
			}

			if (descriptor == null || descriptor.trim().isEmpty()) {
				return new ArrayList<String>();
			}

			// Caso rápido: ya nos dieron el mod exacto.
			if (mod != null) {
				extraerDeUnMod(mod, claseInterna, metodo, descriptor, resultado);
				return new ArrayList<String>(resultado);
			}

		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		return new ArrayList<String>(resultado);
	}

	/**
	 * Extrae constantes de texto desde un mod concreto.
	 */
	private static void extraerDeUnMod(ArchivoDeMod mod, String claseInterna, String metodo, String descriptor,
			Set<String> salida) {
		if (mod == null) {
			return;
		}

		try {
			List<ArchivoDeMod.Constante> constantes = mod.buscarConstantesEnMetodo(claseInterna, metodo, descriptor);
			if (constantes == null || constantes.isEmpty()) {
				return;
			}

			for (ArchivoDeMod.Constante constante : constantes) {
				if (constante == null) {
					continue;
				}

				Object valor = constante.obtenerValor();
				if (!(valor instanceof String)) {
					continue;
				}

				String texto = ((String) valor);
				if (texto == null) {
					continue;
				}

				texto = texto.trim();
				if (!texto.isEmpty()) {
					salida.add(texto);
				}
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}
}