package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores relacionados con el motor de iluminación del mod Starlight.
 *
 * Ejemplo de línea detectada: at
 * ca.spottedleaf.starlight.common.light.BlockStarLightEngine.initNibble
 *
 * Este tipo de error suele aparecer cuando Starlight falla durante la
 * inicialización del sistema de iluminación del mundo.
 */
public class ProblemaBlockStarlightEngine implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private static final String STARLIGHT = "ca.spottedleaf.starlight";
	private static final String INIT_NIBBLE = "BlockStarLightEngine.initNibble";

	@Override
	public String[] patronesRapidos() {
		return new String[] { STARLIGHT, INIT_NIBBLE };
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

		if (linea.contains(INIT_NIBBLE)) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaBlockStarlightEngine();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400.0f;
	}

	@Override
	public String mensaje() {

		if (!activado)
			return "";

		return MonitorDePID.idioma.problemaBlockStarlightEngineDetectado() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaStarlight();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "problema_starlight";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {

		if (trazo == null || trazo.trace == null)
			return false;

		return trazo.trace.contains(STARLIGHT) && trazo.trace.contains(INIT_NIBBLE);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}