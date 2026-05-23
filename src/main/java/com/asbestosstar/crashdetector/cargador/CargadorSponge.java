package com.asbestosstar.crashdetector.cargador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.config.json.Json;

/**
 * Detector de entorno y metadatos para plataformas SpongePowered.
 */
public class CargadorSponge implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {
		for (String archivo : mod.archivos()) {
			String norm = archivo.replace('\\', '/');
			String lower = norm.toLowerCase(Locale.ROOT);

			// Detección estándar de metadatos de Sponge en API 8+
			if (lower.equals("meta-inf/sponge_plugins.json")) {
				return true;
			}

			// Compatibilidad heredada (Legacy Sponge API 7 y anteriores usaban mcmod.info
			// modificado)
			if (lower.equals("mcmod.info")) {
				try {
					// Usamos obtenerArchivoRecursivo para extraer el contenido de texto de forma
					// segura
					String contenido = mod.obtenerArchivoRecursivo(archivo);
					if (contenido != null
							&& (contenido.contains("\"spongeapi\"") || contenido.contains("spongepowered"))) {
						return true;
					}
				} catch (Throwable ignorado) {
					// Prevenir caídas si el archivo está corrupto o ilegible
				}
			}
		}
		return false;
	}

	@Override
	public boolean cargadorEsActivado() {
		// Detecta el entorno de ejecución de Sponge (Vanilla o Forge runtime wrapper)
		return Cargador.claseExiste("org.spongepowered.api.Sponge")
				|| Cargador.claseExiste("org.spongepowered.common.SpongeLifecycle");
	}

	@Override
	public String id() {
		return "sponge";
	}

	/**
	 * Extrae los IDs de plugins desde el archivo de configuración nativo
	 * sponge_plugins.json
	 */
	public static List<String> parsearIdPluginSponge(String jsonTexto) throws IOException {
		List<String> salida = new ArrayList<>();
		try {
			Json.Nodo raiz = Json.leer(jsonTexto);
			Json.Nodo pluginsNodo = raiz.obtener("plugins");

			if (pluginsNodo != null && pluginsNodo.esArreglo()) {
				for (int i = 0; i < pluginsNodo.tamano(); i++) {
					Json.Nodo entrada = pluginsNodo.en(i);
					if (entrada == null)
						continue;

					Json.Nodo idNodo = entrada.obtener("id");
					if (idNodo != null) {
						String id = idNodo.comoCadena();
						if (id != null) {
							id = id.trim();
							if (!id.isEmpty() && !salida.contains(id)) {
								salida.add(id);
							}
						}
					}
				}
			}
		} catch (Throwable t) {
			// Si el parser JSON falla por corrupción parcial, se pueden implementar
			// expresiones regulares aquí
		}
		return salida;
	}

	/**
	 * Extrae la version principal desde sponge_plugins.json.
	 */
	public static String parsearVersionPluginSponge(String jsonTexto) {
		try {
			Json.Nodo raiz = Json.leer(jsonTexto);
			Json.Nodo pluginsNodo = raiz.obtener("plugins");

			if (pluginsNodo != null && pluginsNodo.esArreglo()) {
				for (int i = 0; i < pluginsNodo.tamano(); i++) {
					Json.Nodo entrada = pluginsNodo.en(i);

					if (entrada == null) {
						continue;
					}

					Json.Nodo versionNodo = entrada.obtener("version");

					if (versionNodo != null) {
						String version = versionNodo.comoCadena();

						if (version != null) {
							version = version.trim();

							if (!version.isEmpty()) {
								return version;
							}
						}
					}
				}
			}
		} catch (Throwable ignorado) {
		}

		return "";
	}

}