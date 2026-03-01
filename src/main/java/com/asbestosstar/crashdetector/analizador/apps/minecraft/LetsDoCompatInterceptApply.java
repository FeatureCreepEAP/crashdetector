package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class LetsDoCompatInterceptApply implements Verificaciones {

	// Indica si el log contiene indicios globales del error (optimización de
	// rendimiento)
	private boolean posibleLetsDoCompat = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		// Detección global ligera:
		// Solo se buscan fragmentos estables del método transformado
		if (consola.contenido_verificar.contains("RecipeManager")
				&& consola.contenido_verificar.contains("interceptApply")
				&& consola.contenido_verificar.contains("$letsdocompat$")) {

			posibleLetsDoCompat = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int num) {

		// Salida temprana si no hay indicios globales
		if (!posibleLetsDoCompat) {
			return;
		}

		// Verificación precisa en la línea específica
		if (linea.contains("RecipeManager") && linea.contains("interceptApply") && linea.contains("$letsdocompat$")) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new LetsDoCompatInterceptApply();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1450;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeLetsDoCompatInterceptApply() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreLetsDoCompatInterceptApply();
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
		return "lets_do_compat_intercept_apply";
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