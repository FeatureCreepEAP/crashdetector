package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class EntradaDuplicadaIdModerno implements VerificacionRapida {

	// Indica si el log contiene indicios globales del error
	private boolean posibleEntradaDuplicada = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String DUPLICATE_ENTRY = "java.lang.IllegalArgumentException: Duplicate entry on id";
	private static final String CURRENT = "current=";
	private static final String PREVIOUS = "previous=";

	@Override
	public String[] patronesRapidos() {
		return new String[] { DUPLICATE_ENTRY, CURRENT, PREVIOUS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneEntradaDuplicada(evento.linea)) {
			posibleEntradaDuplicada = true;
		}

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
		return posibleEntradaDuplicada && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleEntradaDuplicada || activado || linea == null) {
			return;
		}

		// Verificación precisa en la línea principal del error
		if (lineaContieneEntradaDuplicada(linea)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneEntradaDuplicada(String linea) {
		return linea.contains(DUPLICATE_ENTRY) && linea.contains(CURRENT) && linea.contains(PREVIOUS);
	}

	@Override
	public Verificaciones nueva() {
		return new EntradaDuplicadaIdModerno();
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
		return MonitorDePID.idioma.mensajeEntradaDuplicadaIdModerno() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreEntradaDuplicadaIdModerno();
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
		return "entrada_duplicada_id_moderno";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}