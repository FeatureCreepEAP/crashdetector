package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ControlifyRemoveReloadingScreen implements Verificaciones {

	// Indica si el log contiene indicios globales del error.
	// Esto evita hacer verificaciones más específicas en cada línea si el log no
	// contiene las cadenas importantes.
	private boolean posibleIncompatibilidad = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global ligera: ambas cadenas suelen aparecer cuando existe el
		// conflicto entre Controlify y Remove Reloading Screen.
		if (consola.contenido_verificar
				.contains("Attempted to fetch default config before DefaultConfigManager was ready!")
				&& consola.contenido_verificar.contains("$rrls$init")) {
			posibleIncompatibilidad = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales
		if (!posibleIncompatibilidad) {
			return;
		}

		// Verificación precisa: normalmente esta línea es el síntoma más claro del
		// problema de inicialización causado por el conflicto.
		if (linea.contains("Attempted to fetch default config before DefaultConfigManager was ready!")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Alternativa: si el lector encuentra primero la línea transformada por
		// Remove Reloading Screen, también podemos enlazar ahí.
		if (linea.contains("$rrls$init")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ControlifyRemoveReloadingScreen();
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
		return MonitorDePID.idioma.mensajeControlifyRemoveReloadingScreen() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreControlifyRemoveReloadingScreen();
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
		return "controlify_remove_reloading_screen";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}