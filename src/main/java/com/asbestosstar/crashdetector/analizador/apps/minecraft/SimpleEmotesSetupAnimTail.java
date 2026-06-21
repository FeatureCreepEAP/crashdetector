package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class SimpleEmotesSetupAnimTail implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde aparece la cadena
	private String enlace = "";

	private static final String TEXTO_ERROR = "$simpleemotes$setupAnimTAIL";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		// Confirmación precisa por línea
		if (linea.contains(TEXTO_ERROR)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new SimpleEmotesSetupAnimTail();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeSimpleEmotesSetupAnimTail() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreSimpleEmotesSetupAnimTail();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "simple_emotes_setup_anim_tail";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}