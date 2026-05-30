package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores de IdleTweaks: "Tried to release unknown channel" +
 * stacktrace con idletweaks.
 */
public class ErrorIdleTweaks implements Verificaciones {

	private boolean activado = false;
	private String enlaceHtml = "";
	private boolean logContieneErrorDeCanal = false;

	@Override
	public void verificar(Consola consola) {
		// Solo verificamos si el mensaje de error está en el log
		boolean tiene = consola.contenido_verificar.contains("Tried to release unknown channel");
		if (tiene) {
			this.logContieneErrorDeCanal = true;
		}

	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado)
			return;
		if (linea == null)
			return;

		// Si el log contiene el error de canal Y esta línea contiene el paquete de
		// idletweaks → activar
		if (logContieneErrorDeCanal && linea.contains("io.armandukx.idletweaks.utils.GameSettingsModifier")) {
			this.activado = true;
			this.enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorIdleTweaks();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400.0f;
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return MonitorDePID.idioma.error_idletweaks_html() + (enlaceHtml.isEmpty() ? "" : " " + enlaceHtml);
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_idletweaks();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_actualizar_idletweaks());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_eliminar_idletweaks());
		return builder.construir();
	}

	@Override
	public String id() {
		return "error_idletweaks";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}