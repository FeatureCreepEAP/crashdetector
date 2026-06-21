package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el problema de Decocraft Nature con el mixin config
 * com/razz/essentialpartnermod/mixins.json.
 */
public class ProblemaDecocraftNatureEssentialPartnerMod implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private static final String MIXIN_ERROR = "Error initialising mixin config com/razz/essentialpartnermod/mixins.json";

	@Override
	public String[] patronesRapidos() {
		return new String[] { MIXIN_ERROR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea.contains(MIXIN_ERROR)) {
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaDecocraftNatureEssentialPartnerMod();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1450.0f;
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";

		return MonitorDePID.idioma.mensajeProblemaDecocraftNatureEssentialPartnerMod() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaDecocraftNatureEssentialPartnerMod();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "problema_decocraft_nature_essential_partner_mod";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (trazo == null || trazo.trace == null)
			return false;

		return trazo.trace.contains(MIXIN_ERROR);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}