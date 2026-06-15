package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class TwilightForestIntelShaders implements VerificacionRapida {

	// Indica si el log contiene indicios globales del error (optimización de
	// rendimiento)
	private boolean posibleFalloIntelShaders = false;

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

		if (evento.linea.contains(TEXTO_PROBLEMATIC_FRAME) || evento.linea.contains(TEXTO_INTEL_DRIVER)
				|| evento.linea.contains(TEXTO_TF_SHADER_COMPLETO)) {
			posibleFalloIntelShaders = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		// Detección global ligera:
		// Se comprueba que existan las tres pistas clave del error
		if (consola.contenido_verificar.contains(TEXTO_PROBLEMATIC_FRAME)
				&& consola.contenido_verificar.contains(TEXTO_INTEL_DRIVER)
				&& consola.contenido_verificar.contains(TEXTO_TF_SHADER_COMPLETO)) {

			posibleFalloIntelShaders = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posibleFalloIntelShaders)
			return false;

		return !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		// Salir temprano si no hay indicios globales
		if (!posibleFalloIntelShaders || activado || linea == null) {
			return;
		}

		// Verificación precisa en línea
		if (linea.contains(TEXTO_TF_SHADER_CORTO)) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
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