package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class LuckPermsNoCargado implements Verificaciones {

	// Indica si el log contiene indicios globales del error
	private boolean posibleErrorLuckPerms = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Verificación global por rendimiento: buscar el error base
		if (consola.contenido_verificar.contains("net.luckperms.api.LuckPermsProvider$NotLoadedException")) {
			posibleErrorLuckPerms = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posibleErrorLuckPerms)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Si no se detectó el posible error global, no continuar
		if (!posibleErrorLuckPerms)
			return;

		// Detección exacta de la línea del error
		if (linea.contains("LuckPermsProvider$NotLoadedException")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new LuckPermsNoCargado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		// Prioridad alta, pero menor que errores de renderizado críticos
		return 1200;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeLuckPermsNoCargado() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreLuckPermsNoCargado();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// El análisis se basa en la línea directa, no en el stacktrace completo
		return false;
	}

	@Override
	public String id() {
		return "luckperms_no_cargado";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}
