package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscardor;

/**
 * Analiza errores cuando falta metadata en mods.toml para un mod específico.
 * Detecta específicamente el error "mods.toml missing metadata for modid
 * [modid]". Utiliza Buscardor para identificar mods que podrían estar causando
 * el problema.
 */
public class ErrorMetadataModsTomlFaltante implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String modIdFaltante = "";
	private List<String> modsPotenciales = null;

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Analiza cada línea del registro buscando el patrón específico de error
		for (String linea : contenidoConsola.split(Verificaciones.nl)) {
			// Detecta el error específico de metadata faltante
			if (linea.contains("mods.toml missing metadata for modid")) {

				// Extrae el modid faltante usando expresión regular
				Pattern pattern = Pattern.compile("mods\\.toml missing metadata for modid (\\w+)");
				Matcher matcher = pattern.matcher(linea);
				if (matcher.find()) {
					modIdFaltante = matcher.group(1);

					// Busca mods que podrían estar causando el problema
					modsPotenciales = Buscardor.obtenerModsConNombre(modIdFaltante);

					mensaje = MonitorDePID.idioma.errorMetadataModsTomlFaltante(modIdFaltante, modsPotenciales)
							+ Verificaciones.nl_html;
					activado = true;
					break;
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorMetadataModsTomlFaltante();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 925.0f; // Prioridad muy alta - error crítico que impide cargar mods
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_metadata_mods_toml_faltante();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_metadata_mods_toml_faltante(modIdFaltante, modsPotenciales))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_metadata_mods_toml_faltante(modIdFaltante))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_metadata_mods_toml_faltante(modIdFaltante)).construir();
	}

}