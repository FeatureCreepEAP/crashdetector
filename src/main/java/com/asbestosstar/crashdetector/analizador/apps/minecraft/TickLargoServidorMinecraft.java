package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class TickLargoServidorMinecraft implements VerificacionRapida {

	private boolean posibleTickLargo = false;
	private boolean activado = false;
	private String enlace = "";

	private static final String TEXTO_WATCHDOG = "ModernFix integrated server watchdog";
	private static final String TEXTO_TICK_LARGO = "A single server tick has taken";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_WATCHDOG, TEXTO_TICK_LARGO };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(TEXTO_WATCHDOG)) {
			posibleTickLargo = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		// Detección global: watchdog de ModernFix
		if (consola.contenido_verificar.contains(TEXTO_WATCHDOG)) {
			posibleTickLargo = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posibleTickLargo)
			return false;

		return !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleTickLargo || activado || linea == null) {
			return;
		}

		if (linea.contains(TEXTO_TICK_LARGO)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new TickLargoServidorMinecraft();
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
		return MonitorDePID.idioma.mensajeTickLargoServidor() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreTickLargoServidor();
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
		return "tick_largo_servidor";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}