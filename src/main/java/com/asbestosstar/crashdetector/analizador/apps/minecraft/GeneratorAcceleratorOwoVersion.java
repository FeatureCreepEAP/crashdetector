package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class GeneratorAcceleratorOwoVersion implements VerificacionRapida {

	// Indica si el log contiene indicios globales del error
	private boolean posibleProblemaGeneratorAccelerator = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String FOUND = "Found:";
	private static final String AVAILABLE = "Available:";
	private static final String FAST_TARGET = "dev/sixik/generator_accelerator/common/features/FastTarget";
	private static final String GENERATOR_ACCELERATOR = "dev/sixik/generator_accelerator";
	private static final String BIT_SET = "Ljava/util/BitSet;";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FAST_TARGET, FOUND, AVAILABLE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneProblemaGeneratorAccelerator(evento.linea)) {
			posibleProblemaGeneratorAccelerator = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		// Detección global ligera: este error suele aparecer como una diferencia entre
		// firmas "Found" y "Available", junto con clases de Generator Accelerator.
		if (consola.contenido_verificar.contains(FOUND) && consola.contenido_verificar.contains(AVAILABLE)
				&& consola.contenido_verificar.contains(FAST_TARGET)) {
			posibleProblemaGeneratorAccelerator = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales
		if (!posibleProblemaGeneratorAccelerator || activado || linea == null) {
			return;
		}

		// Verificación precisa en una de las líneas más representativas del error.
		if (linea.contains(FAST_TARGET)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Alternativa: si se encuentra primero la línea "Found", también puede servir
		// como punto de referencia útil.
		if (linea.contains(FOUND) && linea.contains(BIT_SET)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneProblemaGeneratorAccelerator(String linea) {
		return linea.contains(FAST_TARGET) || linea.contains(GENERATOR_ACCELERATOR) || linea.contains(FOUND)
				|| linea.contains(AVAILABLE);
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleProblemaGeneratorAccelerator && !activado;
	}

	@Override
	public Verificaciones nueva() {
		return new GeneratorAcceleratorOwoVersion();
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
		return MonitorDePID.idioma.mensajeGeneratorAcceleratorOwoVersion() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreGeneratorAcceleratorOwoVersion();
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
		return "generator_accelerator_owo_version";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}