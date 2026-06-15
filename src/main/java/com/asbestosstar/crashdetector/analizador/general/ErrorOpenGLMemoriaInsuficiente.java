package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores OpenGL por falta de memoria de video.
 *
 * Ejemplo: Error has been generated. GL error GL_OUT_OF_MEMORY in (null): (ID:
 * 173538523) Generic error
 */
public class ErrorOpenGLMemoriaInsuficiente implements VerificacionRapida {

	private boolean activado = false;
	private String enlace = "";

	private static final String GL_OUT_OF_MEMORY = "GL_OUT_OF_MEMORY";
	private static final String GL_ERROR = "GL error";
	private static final String OPENGL = "OpenGL";
	private static final String ERROR_GENERATED = "Error has been generated";

	@Override
	public String[] patronesRapidos() {
		return new String[] { GL_OUT_OF_MEMORY, GL_ERROR, OPENGL, ERROR_GENERATED };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {

		if (consola == null || consola.contenido_verificar == null)
			return;

		String log = consola.contenido_verificar;

		// Detección global ligera
		if (log.contains(GL_OUT_OF_MEMORY)
				&& (log.contains(GL_ERROR) || log.contains(OPENGL) || log.contains(ERROR_GENERATED))) {

			activado = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return !activado || enlace.length() == 0;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		// Evitar trabajo innecesario
		if (activado && enlace.length() != 0)
			return;

		if (linea == null)
			return;

		if (!linea.contains(GL_OUT_OF_MEMORY))
			return;

		if (linea.contains(GL_ERROR) || linea.contains(OPENGL) || linea.contains(ERROR_GENERATED)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorOpenGLMemoriaInsuficiente();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1200;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeOpenGLMemoriaInsuficiente() + enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreOpenGLMemoriaInsuficiente();
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
		return "opengl_memoria_insuficiente";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}