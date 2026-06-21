package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de ClassCastException causados por Preloading Tricks.
 *
 * Ejemplo: Caused by: java.lang.ClassCastException: class java.lang.String
 * cannot be cast to class java.lang.module.ModuleDescriptor
 *
 * Se detecta por la presencia de "Preloading Tricks Installed" y el error de
 * casting en el log.
 */
public class ErrorPreloadingTricks implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private static final String PRELOADING_TRICKS_INSTALLED = "Preloading Tricks Installed";
	private static final String CLASS_CAST_EXCEPTION = "ClassCastException";
	private static final String MODULE_DESCRIPTOR_CAST = "cannot be cast to class java.lang.module.ModuleDescriptor";

	@Override
	public String[] patronesRapidos() {
		return new String[] { PRELOADING_TRICKS_INSTALLED, MODULE_DESCRIPTOR_CAST };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Marcamos el error en la línea donde aparece el ClassCastException
		if (linea.contains(CLASS_CAST_EXCEPTION) && linea.contains(MODULE_DESCRIPTOR_CAST)) {
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorPreloadingTricks();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorPreloadingTricks() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorPreloadingTricks();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "error_preloading_tricks";
	}

	@Override
	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}