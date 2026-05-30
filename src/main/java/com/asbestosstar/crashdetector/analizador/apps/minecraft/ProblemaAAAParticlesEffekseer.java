package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta crashes nativos asociados con Effekseer usado por AAAParticles.
 *
 * Ejemplo de log: Problematic frame: C [libEffekseerNativeForJava.so+...]
 *
 * o
 *
 * Problematic frame: C [EffekseerNativeForJava.dll+...]
 */
public class ProblemaAAAParticlesEffekseer implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global rápido
		if (!log.contains("Problematic frame:"))
			return;

		if (log.contains("[libEffekseerNativeForJava.so") || log.contains("[EffekseerNativeForJava.dll")) {

			activado = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (activado || linea == null)
			return;

		if (linea.contains("[libEffekseerNativeForJava.so") || linea.contains("[EffekseerNativeForJava.dll")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaAAAParticlesEffekseer();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900f;
	}

	@Override
	public String mensaje() {

		if (!activado)
			return "";

		return MonitorDePID.idioma.problemaAAAParticlesEffekseer() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaAAAParticlesEffekseer();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "problema_aaaparticles_effekseer";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {

		if (trazo == null || trazo.trace == null)
			return false;

		return trazo.trace.contains("EffekseerNativeForJava") && trazo.trace.contains("Problematic frame");
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}