package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

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

	private static final String TEXTO_INICIO = "The Mod File ";
	private static final String TEXTO_FIN = " has mods that were not found";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_INICIO, TEXTO_FIN };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
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
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		int inicio = linea.indexOf(TEXTO_INICIO);
		if (inicio < 0) {
			return;
		}

		inicio += TEXTO_INICIO.length();

		int fin = linea.indexOf(TEXTO_FIN, inicio);
		if (fin < 0 || fin <= inicio) {
			return;
		}

		// Extrae la ruta completa del mod problemático.
		rutaMod = linea.substring(inicio, fin).trim();

		if (rutaMod.isEmpty()) {
			return;
		}

		// Extrae solo el nombre del archivo/directorio de la ruta completa.
		nombreMod = extraerNombreArchivo(rutaMod);

		mensaje = MonitorDePID.idioma.errorDiscrepanciaModID(nombreMod) + Verificaciones.nl_html;
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		activado = true;
	}

	/**
	 * Extrae el nombre final de una ruta Windows, Linux o macOS.
	 */
	private String extraerNombreArchivo(String ruta) {
		if (ruta == null || ruta.isEmpty()) {
			return "";
		}

		int ultimoSlash = ruta.lastIndexOf('/');
		int ultimoBackslash = ruta.lastIndexOf('\\');
		int ultimoSeparador = Math.max(ultimoSlash, ultimoBackslash);

		if (ultimoSeparador >= 0 && ultimoSeparador + 1 < ruta.length()) {
			return ruta.substring(ultimoSeparador + 1);
		}

		return ruta;
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
		if (!activado) {
			return "";
		}

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
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_INICIO, TEXTO_FIN };
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}