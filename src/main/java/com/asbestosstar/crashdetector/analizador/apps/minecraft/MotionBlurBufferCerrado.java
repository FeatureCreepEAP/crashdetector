package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class MotionBlurBufferCerrado implements Verificaciones {

	// Indica si el log contiene indicios globales del error.
	// Se usan dos líneas separadas para reducir falsos positivos.
	private boolean posibleProblemaMotionBlur = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global ligera: la clase de Motion Blur junto con el mensaje
		// "Buffer already closed" suele indicar un problema del mod Motion Blur.
		if (consola.contenido_verificar
				.contains("net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO")
				&& consola.contenido_verificar.contains("java.lang.IllegalStateException: Buffer already closed")) {
			posibleProblemaMotionBlur = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales
		if (!posibleProblemaMotionBlur) {
			return;
		}

		// Verificación precisa en la línea del error principal.
		if (linea.contains("java.lang.IllegalStateException: Buffer already closed")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Alternativa: si se encuentra primero la línea del stack trace de Motion Blur,
		// también puede servir como enlace útil.
		if (linea.contains("net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new MotionBlurBufferCerrado();
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
		return MonitorDePID.idioma.mensajeMotionBlurBufferCerrado() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreMotionBlurBufferCerrado();
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
		return "motion_blur_buffer_cerrado";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}