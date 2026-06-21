package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class LetsDoCompatInterceptApply implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String RECIPE_MANAGER = "RecipeManager";
	private static final String INTERCEPT_APPLY = "interceptApply";
	private static final String LETS_DO_COMPAT = "$letsdocompat$";

	@Override
	public String[] patronesRapidos() {
		return new String[] { RECIPE_MANAGER, INTERCEPT_APPLY, LETS_DO_COMPAT };
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

		// Verificación precisa en la línea específica
		if (lineaContieneLetsDoCompat(linea)) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneLetsDoCompat(String linea) {
		return linea.contains(RECIPE_MANAGER) && linea.contains(INTERCEPT_APPLY) && linea.contains(LETS_DO_COMPAT);
	}

	@Override
	public Verificaciones nueva() {
		return new LetsDoCompatInterceptApply();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1450;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeLetsDoCompatInterceptApply() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreLetsDoCompatInterceptApply();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "lets_do_compat_intercept_apply";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}