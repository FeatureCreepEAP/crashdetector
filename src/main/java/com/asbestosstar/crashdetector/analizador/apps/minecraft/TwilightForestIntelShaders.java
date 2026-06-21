package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class TwilightForestIntelShaders implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String TEXTO_PROBLEMATIC_FRAME = "Problematic frame:";
	private static final String TEXTO_INTEL_DRIVER = "igxelpicd64";
	private static final String TEXTO_TF_SHADER_COMPLETO = "twilightforest.client.TFShaders$BindableShaderInstance";
	private static final String TEXTO_TF_SHADER_CORTO = "TFShaders$BindableShaderInstance";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_PROBLEMATIC_FRAME, TEXTO_INTEL_DRIVER, TEXTO_TF_SHADER_COMPLETO,
				TEXTO_TF_SHADER_CORTO };
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

		// Verificación precisa en línea
		if (linea.contains(TEXTO_TF_SHADER_CORTO)) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new TwilightForestIntelShaders();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeTwilightForestIntelShaders() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreTwilightForestIntelShaders();
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
		return "twilight_forest_intel_shaders";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}