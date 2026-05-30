package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class SimpleEmotesSetupAnimTail implements Verificaciones {

	// Indica si el log contiene indicios globales del error
	private boolean posibleErrorSimpleEmotes = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde aparece la cadena
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global ligera para evitar trabajo innecesario por línea
		if (consola.contenido_verificar.contains("$simpleemotes$setupAnimTAIL")) {
			posibleErrorSimpleEmotes = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales o si ya fue activado
		if (!posibleErrorSimpleEmotes || activado) {
			return;
		}

		// Confirmación precisa por línea
		if (linea.contains("$simpleemotes$setupAnimTAIL")) {
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
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "simple_emotes_setup_anim_tail";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}