package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta un error específico de carga de modelo relacionado con Rhyhorn
 * de Cobblemon: Pinkan Islands.
 *
 * <p>Ejemplo típico:
 * <br>Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn
 *
 * <p>Aunque el mensaje menciona el namespace de Cobblemon, en este caso
 * concreto el origen más probable es el addon/mod <b>Cobblemon: Pinkan Islands</b>,
 * que es de donde proviene ese Rhyhorn.
 */
public class CobblemonPinkanIslandsRhyhornModelo implements Verificaciones {

	// Indica si el log contiene indicios globales del error
	private boolean posibleError = false;

	// Indica si esta verificación ya fue activada
	private boolean activado = false;

	// Enlace a la línea representativa del error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global ligera para evitar trabajo innecesario por línea
		if (consola.contenido_verificar.contains("Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn")) {
			posibleError = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales o si ya fue activado
		if (!posibleError || activado) {
			return;
		}

		if (linea.contains("Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new CobblemonPinkanIslandsRhyhornModelo();
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
		return MonitorDePID.idioma.mensajeCobblemonPinkanIslandsRhyhornModelo() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreCobblemonPinkanIslandsRhyhornModelo();
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
		return "cobblemon_pinkan_islands_rhyhorn_modelo";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}