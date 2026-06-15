package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class MotionBlurBufferCerrado implements VerificacionRapida {

	// Indica si el log contiene indicios globales del error.
	// Se usan dos líneas separadas para reducir falsos positivos.
	private boolean posibleProblemaMotionBlur = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String MOTION_BLUR_STACK = "net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO";
	private static final String BUFFER_ALREADY_CLOSED = "java.lang.IllegalStateException: Buffer already closed";

	@Override
	public String[] patronesRapidos() {
		return new String[] { MOTION_BLUR_STACK, BUFFER_ALREADY_CLOSED };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneMotionBlur(evento.linea)) {
			posibleProblemaMotionBlur = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		// Detección global ligera: la clase de Motion Blur junto con el mensaje
		// "Buffer already closed" suele indicar un problema del mod Motion Blur.
		if (consola.contenido_verificar.contains(MOTION_BLUR_STACK)
				&& consola.contenido_verificar.contains(BUFFER_ALREADY_CLOSED)) {
			posibleProblemaMotionBlur = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleProblemaMotionBlur && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales
		if (!posibleProblemaMotionBlur || activado || linea == null) {
			return;
		}

		// Verificación precisa en la línea del error principal.
		if (linea.contains(BUFFER_ALREADY_CLOSED)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Alternativa: si se encuentra primero la línea del stack trace de Motion Blur,
		// también puede servir como enlace útil.
		if (linea.contains(MOTION_BLUR_STACK)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneMotionBlur(String linea) {
		return linea.contains(MOTION_BLUR_STACK) || linea.contains(BUFFER_ALREADY_CLOSED);
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

}