package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
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
public class ProblemaBlockStarlightEngine implements VerificacionRapida {

	private boolean activado = false;
	private String enlace = "";
	public boolean analizarLineas = false;

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

		if (evento.linea.contains(STARLIGHT) || evento.linea.contains(INIT_NIBBLE)) {
			analizarLineas = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {

		if (consola == null || consola.contenido_verificar == null)
			return;

		String log = consola.contenido_verificar;

		// Pre-check global rápido para evitar análisis innecesario
		if (!log.contains(STARLIGHT))
			return;

		// Si el método problemático aparece en el log, activamos el detector
		if (log.contains(INIT_NIBBLE)) {
			analizarLineas = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return analizarLineas && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || activado || linea == null)
			return;

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