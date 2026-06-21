package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores internos del Lunar Client.
 *
 * Se activa si el log contiene: - "Caused by: com.moonsworth.lunar" - "An error
 * occurred while launching Lunar Client."
 *
 * Estos errores son fallos propios del cliente Lunar.
 */
public class ProblemaLunarClient implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private static final String LUNAR_PACKAGE = "com.moonsworth.lunar";
	private static final String LUNAR_LAUNCH_ERROR = "An error occurred while launching Lunar Client.";

	@Override
	public String[] patronesRapidos() {
		return new String[] { LUNAR_PACKAGE, LUNAR_LAUNCH_ERROR };
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

		if (lineaContieneProblemaLunar(linea)) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneProblemaLunar(String linea) {
		return linea.contains(LUNAR_PACKAGE) || linea.contains(LUNAR_LAUNCH_ERROR);
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new ProblemaLunarClient();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500.0f; // Cliente externo
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeProblemaLunarClient() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaLunarClient();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "problema_lunar_client";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.builder().doc("en", "minecraft/Launchers.md").build();
	}

}