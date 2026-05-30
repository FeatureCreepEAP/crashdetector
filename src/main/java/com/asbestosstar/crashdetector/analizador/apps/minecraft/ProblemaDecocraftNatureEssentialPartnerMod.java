package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el problema de Decocraft Nature con el mixin config
 * com/razz/essentialpartnermod/mixins.json.
 */
public class ProblemaDecocraftNatureEssentialPartnerMod implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global para rendimiento
		if (log.contains("Error initialising mixin config com/razz/essentialpartnermod/mixins.json")) {
			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (linea.contains("Error initialising mixin config com/razz/essentialpartnermod/mixins.json")) {
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

		return trazo.trace.contains("Error initialising mixin config com/razz/essentialpartnermod/mixins.json");
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}