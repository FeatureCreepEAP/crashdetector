package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores OpenGL por falta de memoria de video.
 *
 * Ejemplo: Error has been generated. GL error GL_OUT_OF_MEMORY in (null): (ID:
 * 173538523) Generic error
 */
public class ErrorOpenGLMemoriaInsuficiente implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Detección global ligera
		if (log.contains("GL_OUT_OF_MEMORY")
				&& (log.contains("GL error") || log.contains("OpenGL") || log.contains("Error has been generated"))) {

			activado = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int num) {

		// Evitar trabajo innecesario
		if (!activado)
			return;

		// Solo registrar el primer error encontrado
		if (enlace.length() == 0 && linea.contains("GL_OUT_OF_MEMORY")) {

			enlace = consola.agregarErrorALectador(num, this);
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
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}