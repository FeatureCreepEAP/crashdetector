package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
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
	private String enlaceHtml = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		// Analiza cada línea del registro buscando el patrón específico de error
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
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
					enlaceHtml = consola.agregarErrorALectador(i, this);
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
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
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
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "metadata_mods_toml_faltante";
	}
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;//TODO
	}
	
	
	
}