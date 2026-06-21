package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de módulos faltantes relacionados con Groovy Modloader (GML),
 * específicamente cuando Jackson core no se encuentra pero es requerido por
 * Jackson module paramnames, común en mods como Valkyrien Skies.
 */
public class ErrorGroovyModloaderModuloFaltante implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String FIND_EXCEPTION = "java.lang.module.FindException";
	private static final String JACKSON_CORE_NOT_FOUND = "Module com.fasterxml.jackson.core not found";
	private static final String REQUIRED_BY_JACKSON = "required by com.fasterxml.jackson";
	private static final String REQUIRED_BY_PARAMNAMES = "required by com.fasterxml.jackson.module.paramnames";
	private static final String GROOVY = "groovy";
	private static final String GML = "gml";
	private static final String VALKYRIEN = "valkyrien";
	private static final String VALKERIAN = "valkerian";
	private static final String GROOVY_MODLOADER = "groovy modloader";

	@Override
	public String[] patronesRapidos() {
		return new String[] { JACKSON_CORE_NOT_FOUND, REQUIRED_BY_JACKSON, FIND_EXCEPTION, GROOVY, GML, VALKYRIEN,
				VALKERIAN };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde un módulo Jackson no se
	 * encuentra pero es requerido por otro módulo Jackson, común en entornos con
	 * Groovy Modloader.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscamos la línea que contiene el error de módulo faltante de Jackson
		if (lineaContieneModuloJacksonFaltante(linea)) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de módulos de GML
			mensaje = MonitorDePID.idioma.errorGroovyModloaderModuloFaltante() + Verificaciones.nl_html;
			activado = true;
		}
	}

	private boolean lineaContieneModuloJacksonFaltante(String linea) {
		return linea.contains(FIND_EXCEPTION) && linea.contains(JACKSON_CORE_NOT_FOUND)
				&& linea.contains(REQUIRED_BY_JACKSON);
	}

	private boolean lineaContieneGML(String linea) {
		return linea.contains(GROOVY) || linea.contains(GML) || linea.contains(VALKYRIEN) || linea.contains(VALKERIAN);
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorGroovyModloaderModuloFaltante();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400.0f; // Alta prioridad: rompe la carga del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorGroovyModloaderModuloFaltante();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.pasoErrorGroovyModloaderModuloFaltante()).construir();
	}

	@Override
	public String id() {
		return "error_gml_modulo_faltante";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de módulos
	 * faltantes de Groovy Modloader.
	 * </p>
	 */
	@Override
	public String[] ocupaTrazo() {
		return new String[] { FIND_EXCEPTION, JACKSON_CORE_NOT_FOUND, REQUIRED_BY_PARAMNAMES };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}