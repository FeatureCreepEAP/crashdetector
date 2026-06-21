package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de IdleTweaks: "Tried to release unknown channel" +
 * stacktrace con idletweaks.
 */
public class ErrorIdleTweaks implements Verificaciones {

	private boolean activado = false;
	private String enlaceHtml = "";

	private static final String UNKNOWN_CHANNEL = "Tried to release unknown channel";
	private static final String IDLETWEAKS_CLASS = "io.armandukx.idletweaks.utils.GameSettingsModifier";

	@Override
	public String[] patronesRapidos() {
		return new String[] { UNKNOWN_CHANNEL, IDLETWEAKS_CLASS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Si el log contiene el error de canal Y esta línea contiene el paquete de
		// idletweaks → activar
		if (linea.contains(IDLETWEAKS_CLASS)) {
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
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}