package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Analiza errores cuando hay discrepancia entre el ID del mod en la
 * anotación @Mod y el archivo mods.toml. Detecta específicamente el error "The
 * Mod File [...] has mods that were not found" que ocurre durante el
 * desarrollo.
 */
public class ErrorDiscrepanciaModID implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String rutaMod = "";
	private String nombreMod = "";
	private String enlaceHtml = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		// Analiza cada línea del registro buscando el patrón específico de error de
		// discrepancia de IDs
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			// Detecta el error específico de mods no encontrados
			if (linea.contains("has mods that were not found")) {
				// Extrae la ruta del mod problemático usando expresión regular
				Pattern pattern = Pattern.compile("The Mod File (.+) has mods that were not found");
				Matcher matcher = pattern.matcher(linea);
				if (matcher.find()) {
					rutaMod = matcher.group(1);

					// Extrae solo el nombre del archivo/directorio de la ruta completa
					nombreMod = rutaMod.contains("\\") ? rutaMod.substring(rutaMod.lastIndexOf("\\") + 1)
							: rutaMod.contains("/") ? rutaMod.substring(rutaMod.lastIndexOf("/") + 1) : rutaMod;

					mensaje = MonitorDePID.idioma.errorDiscrepanciaModID(nombreMod) + Verificaciones.nl_html;
					enlaceHtml = consola.agregarErrorALectador(i, this);
					activado = true;
					break; // Detiene al encontrar el primer error
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorDiscrepanciaModID();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 940.0f; // Prioridad máxima para errores de desarrollo de mods
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_discrepancia_mod_id();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1_discrepancia_mod_id(nombreMod))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_discrepancia_mod_id())
				.agregarEtiqueta(MonitorDePID.idioma.paso3_discrepancia_mod_id()).construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "discrepancia_modid";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

}