package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de compatibilidad entre Simple Radio y Lexiconfig.
 *
 * Ejemplo de StackTrace: at
 * com.codinglitch.simpleradio.SimpleRadioLibrary.shelveLexicons at
 * com.codinglitch.lexiconfig.LexiconfigApi.shelveLexicon
 *
 * Causado generalmente por tener una versión de Simple Radio incompatible con
 * la versión instalada de Lexiconfig.
 */
public class ErrorSimpleRadioLexiconfig implements Verificaciones {

	private boolean activado = false;
	private boolean vioSimpleRadio = false;
	private boolean vioLexiconfig = false;
	private String enlace = "";

	private static final String SIMPLE_RADIO_LIBRARY = "com.codinglitch.simpleradio.SimpleRadioLibrary";
	private static final String SIMPLE_RADIO_SHELVE_LEXICONS = "com.codinglitch.simpleradio.SimpleRadioLibrary.shelveLexicons";
	private static final String LEXICONFIG_API = "com.codinglitch.lexiconfig.LexiconfigApi";

	@Override
	public String[] patronesRapidos() {
		return new String[] { SIMPLE_RADIO_LIBRARY, SIMPLE_RADIO_SHELVE_LEXICONS, LEXICONFIG_API };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		if (linea.contains(SIMPLE_RADIO_LIBRARY)) {
			vioSimpleRadio = true;
		}

		if (linea.contains(LEXICONFIG_API)) {
			vioLexiconfig = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscamos la línea específica del crash en SimpleRadioLibrary
		if (linea.contains(SIMPLE_RADIO_SHELVE_LEXICONS)) {
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorSimpleRadioLexiconfig();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorSimpleRadioLexiconfig() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorSimpleRadioLexiconfig();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "error_simple_radio_lexiconfig";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}