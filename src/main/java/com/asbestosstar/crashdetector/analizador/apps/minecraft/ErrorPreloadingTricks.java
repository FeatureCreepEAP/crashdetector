package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
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
	private boolean analizarLineas = false;
	private String enlace = "";
	private boolean tienePreLoading = false;

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global: Debemos encontrar tanto el error de casting como la mención
		// del mod
		if (log.contains("Preloading Tricks Installed")) {

			tienePreLoading = true;
		}
		if (log.contains("cannot be cast to class java.lang.module.ModuleDescriptor")) {
			analizarLineas = true;
		}

	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {

		if (!tienePreLoading || !analizarLineas || linea == null || activado)
			return;

		// Marcamos el error en la línea donde aparece el ClassCastException
		if (linea.contains("ClassCastException")
				&& linea.contains("cannot be cast to class java.lang.module.ModuleDescriptor")) {

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
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
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