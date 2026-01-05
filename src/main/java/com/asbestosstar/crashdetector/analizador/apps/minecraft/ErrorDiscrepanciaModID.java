package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

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

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificar(Consola, String, int)}, llamada por el analizador línea a
	 * línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca el patrón: "The Mod File (ruta) has mods that were not found" en la
	 * línea actual, extrae la ruta y el nombre del archivo/directorio, y registra
	 * el enlace correspondiente.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguimos procesando más líneas.
		if (activado) {
			return;
		}

		// Detecta el error específico de mods no encontrados
		if (linea.contains("has mods that were not found")) {
			// Extrae la ruta del mod problemático usando expresión regular
			Pattern pattern = Pattern.compile("The Mod File (.+) has mods that were not found");
			Matcher matcher = pattern.matcher(linea);
			if (matcher.find()) {
				rutaMod = matcher.group(1);

				// Extrae solo el nombre del archivo/directorio de la ruta completa
				if (rutaMod.contains("\\")) {
					nombreMod = rutaMod.substring(rutaMod.lastIndexOf("\\") + 1);
				} else if (rutaMod.contains("/")) {
					nombreMod = rutaMod.substring(rutaMod.lastIndexOf("/") + 1);
				} else {
					nombreMod = rutaMod;
				}

				mensaje = MonitorDePID.idioma.errorDiscrepanciaModID(nombreMod) + Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				activado = true;
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
		return "discrepancia_modid";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene la frase completa con la ruta del mod, o, en su
	 * defecto, el patrón base del error.</li>
	 * </ul>
	 * Es deliberadamente conservador: se prefiere un falso negativo antes que
	 * marcar un trazo ajeno a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (rutaMod != null && !rutaMod.isEmpty()) {
			String esperado = "The Mod File " + rutaMod + " has mods that were not found";
			return t.contains(esperado);
		}

		// Fallback muy estricto si por alguna razón no se guardó la ruta.
		return t.contains("The Mod File ") && t.contains(" has mods that were not found");
	}
	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
	
	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"+this.getClass().getSimpleName()+".java";
	}
	
	
	
	
}
