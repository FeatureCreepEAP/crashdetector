package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ControlifyRemoveReloadingScreen implements VerificacionRapida {

	// Indica si el log contiene indicios globales del error.
	// Esto evita hacer verificaciones más específicas en cada línea si el log no
	// contiene las cadenas importantes.
	private boolean posibleIncompatibilidad = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String CONTROLIFY_CONFIG_READY = "Attempted to fetch default config before DefaultConfigManager was ready!";
	private static final String RRLS_INIT = "$rrls$init";

	@Override
	public String[] patronesRapidos() {
		return new String[] { CONTROLIFY_CONFIG_READY, RRLS_INIT };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		posibleIncompatibilidad = true;
		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Método de compatibilidad — no hace nada en modo rápido/streaming.
	 */
	@Override
	public void verificar(Consola consola) {
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleIncompatibilidad && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (activado || linea == null) {
			return;
		}

		// Salir temprano si no hay indicios globales
		if (!posibleIncompatibilidad) {
			return;
		}

		// Verificación precisa: normalmente esta línea es el síntoma más claro del
		// problema de inicialización causado por el conflicto.
		if (linea.contains(CONTROLIFY_CONFIG_READY)) {
			activar(consola, num);
			return;
		}

		// Alternativa: si el lector encuentra primero la línea transformada por
		// Remove Reloading Screen, también podemos enlazar ahí.
		if (linea.contains(RRLS_INIT)) {
			activar(consola, num);
		}
	}

	private void activar(Consola consola, int num) {
		this.enlace = consola.agregarErrorALectador(num, this);
		this.activado = true;
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

}