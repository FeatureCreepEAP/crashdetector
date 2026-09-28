package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ControlifyRemoveReloadingScreen implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	/*
	 * El conflicto real necesita DOS señales a la vez:
	 *
	 * 1) La línea propia de Controlify (error de inicialización de su config).
	 * 2) La línea del mixin de Remove Reloading Screen ($rrls$init).
	 *
	 * OJO: la línea "handler$...$rrls$init" la genera el mixin de Remove
	 * Reloading Screen y aparece en la traza aunque Controlify NO esté
	 * instalado. Por eso no basta con ver $rrls$init para culpar a un conflicto:
	 * exigimos que ambas señales estén presentes.
	 */
	private static final String CONTROLIFY_CONFIG_READY = "Attempted to fetch default config before DefaultConfigManager was ready!";
	private static final String RRLS_INIT = "$rrls$init";

	// Marca si ya vimos la señal de Controlify y la de Remove Reloading Screen
	private boolean vioControlify = false;
	private boolean vioRrls = false;

	@Override
	public String[] patronesRapidos() {
		return new String[] { CONTROLIFY_CONFIG_READY, RRLS_INIT };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (activado || linea == null) {
			return;
		}

		// Señal propia de Controlify: su config no estaba lista.
		if (linea.contains(CONTROLIFY_CONFIG_READY)) {
			vioControlify = true;
		}

		// Señal de Remove Reloading Screen: línea transformada por su mixin.
		if (linea.contains(RRLS_INIT)) {
			vioRrls = true;
		}

		// Solo es un conflicto real si aparecen AMBAS señales. Si solo vemos
		// $rrls$init (o solo la de Controlify) no activamos nada, evitando el
		// falso positivo cuando Controlify no existe.
		if (vioControlify && vioRrls) {
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
	public String[] ocupaTrazo() {
		return new String[0];
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
