package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class TwilightForestIntelShaders implements Verificaciones {

	// Indica si el log contiene indicios globales del error (optimización de
	// rendimiento)
	private boolean posibleFalloIntelShaders = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		// Detección global ligera:
		// Se comprueba que existan las tres pistas clave del error
		if (consola.contenido_verificar.contains("Problematic frame:")
				&& consola.contenido_verificar.contains("igxelpicd64")
				&& consola.contenido_verificar.contains("twilightforest.client.TFShaders$BindableShaderInstance")) {

			posibleFalloIntelShaders = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		// Salir temprano si no hay indicios globales
		if (!posibleFalloIntelShaders) {
			return;
		}

		// Verificación precisa en línea
		if (linea.contains("TFShaders$BindableShaderInstance")) {

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

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}