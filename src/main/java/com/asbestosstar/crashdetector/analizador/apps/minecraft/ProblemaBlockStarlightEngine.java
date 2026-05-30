package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
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

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global rápido para evitar análisis innecesario
		if (!log.contains("ca.spottedleaf.starlight"))
			return;

		// Si el método problemático aparece en el log, activamos el detector
		if (log.contains("BlockStarLightEngine.initNibble")) {
			activado = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (activado || linea == null)
			return;

		if (linea.contains("BlockStarLightEngine.initNibble")) {

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

		return trazo.trace.contains("ca.spottedleaf.starlight")
				&& trazo.trace.contains("BlockStarLightEngine.initNibble");
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}