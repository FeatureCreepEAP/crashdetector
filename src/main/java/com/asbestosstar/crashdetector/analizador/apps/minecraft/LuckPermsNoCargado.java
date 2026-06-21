package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class LuckPermsNoCargado implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String LUCKPERMS_NOT_LOADED = "net.luckperms.api.LuckPermsProvider$NotLoadedException";
	private static final String LUCKPERMS_NOT_LOADED_SIMPLE = "LuckPermsProvider$NotLoadedException";

	@Override
	public String[] patronesRapidos() {
		return new String[] { LUCKPERMS_NOT_LOADED, LUCKPERMS_NOT_LOADED_SIMPLE };
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

		// Detección exacta de la línea del error
		if (lineaContieneLuckPermsNoCargado(linea)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneLuckPermsNoCargado(String linea) {
		return linea.contains(LUCKPERMS_NOT_LOADED_SIMPLE);
	}

	@Override
	public VerificacionesLegacy nueva() {
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