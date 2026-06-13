package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class TickLargoServidorMinecraft implements Verificaciones {

	private boolean posibleTickLargo = false;
	private boolean activado = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global: watchdog de ModernFix
		if (consola.contenido_verificar.contains("ModernFix integrated server watchdog")) {
			posibleTickLargo = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posibleTickLargo)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleTickLargo)
			return;

		if (linea.contains("A single server tick has taken")) {
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
