package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class SimpleCloudsIrisDH implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String TEXTO_RENDERER = "Simple Clouds renderer could not initialize";
	private static final String TEXTO_DH = "does not currently support shaders with Distant Horizons";
	private static final String TEXTO_RENDERER_COMPLETO = "Simple Clouds renderer could not initialize due to compat error(s):";
	private static final String TEXTO_DH_COMPLETO = "Simple Clouds does not currently support shaders with Distant Horizons";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_RENDERER, TEXTO_DH, TEXTO_RENDERER_COMPLETO, TEXTO_DH_COMPLETO };
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

		// Verificación precisa en la línea
		if (linea.contains(TEXTO_RENDERER_COMPLETO) && linea.contains(TEXTO_DH_COMPLETO)) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new SimpleCloudsIrisDH();
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
		return MonitorDePID.idioma.mensajeSimpleCloudsIncompatibilidadShaders() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreSimpleCloudsIncompatibilidadShaders();
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
		return "simple_clouds_incompatibilidad_shaders";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}