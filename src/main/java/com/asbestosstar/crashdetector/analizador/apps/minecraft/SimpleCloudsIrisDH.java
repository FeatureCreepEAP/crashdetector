package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class SimpleCloudsIrisDH implements Verificaciones {

	// Indica si el log contiene indicios globales del error (optimización de
	// rendimiento)
	private boolean posibleIncompatibilidad = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global ligera: solo buscar subcadenas clave sin regex ni
		// operaciones costosas
		if (consola.contenido_verificar.contains("Simple Clouds renderer could not initialize")
				&& consola.contenido_verificar.contains("does not currently support shaders with Distant Horizons")) {
			posibleIncompatibilidad = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales
		if (!posibleIncompatibilidad) {
			return;
		}

		// Verificación precisa en la línea
		if (linea.contains("Simple Clouds renderer could not initialize due to compat error(s):")
				&& linea.contains("Simple Clouds does not currently support shaders with Distant Horizons")) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new SimpleCloudsIrisDH();
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
		return MonitorDePID.idioma.mensajeSimpleCloudsIncompatibilidadShaders() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreSimpleCloudsIncompatibilidadShaders();
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
		return "simple_clouds_incompatibilidad_shaders";
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