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
 * Detecta crashes nativos asociados con Effekseer usado por AAAParticles.
 *
 * Ejemplo de log: Problematic frame: C [libEffekseerNativeForJava.so+...]
 *
 * o
 *
 * Problematic frame: C [EffekseerNativeForJava.dll+...]
 */
public class ProblemaAAAParticlesEffekseer implements VerificacionRapida {

	private boolean activado = false;
	private String enlace = "";
	public boolean posible = false;

	private static final String PROBLEMATIC_FRAME = "Problematic frame:";
	private static final String EFFEKSEER_SO = "[libEffekseerNativeForJava.so";
	private static final String EFFEKSEER_DLL = "[EffekseerNativeForJava.dll";
	private static final String EFFEKSEER_NATIVE = "EffekseerNativeForJava";

	@Override
	public String[] patronesRapidos() {
		return new String[] { PROBLEMATIC_FRAME, EFFEKSEER_SO, EFFEKSEER_DLL, EFFEKSEER_NATIVE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneEffekseer(evento.linea)) {
			posible = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String log = consola.contenido_verificar;

		// Pre-check global rápido
		if (!log.contains(PROBLEMATIC_FRAME))
			return;

		if (log.contains(EFFEKSEER_SO) || log.contains(EFFEKSEER_DLL)) {
			posible = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posible && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (activado || linea == null || !posible)
			return;

		if (linea.contains(EFFEKSEER_SO) || linea.contains(EFFEKSEER_DLL)) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneEffekseer(String linea) {
		return linea.contains(PROBLEMATIC_FRAME) && (linea.contains(EFFEKSEER_SO) || linea.contains(EFFEKSEER_DLL));
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

		return trazo.trace.contains(EFFEKSEER_NATIVE) && trazo.trace.contains(PROBLEMATIC_FRAME);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}