package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class GeneratorAcceleratorOwoVersion implements Verificaciones {

	// Indica si el log contiene indicios globales del error
	private boolean posibleProblemaGeneratorAccelerator = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global ligera: este error suele aparecer como una diferencia entre
		// firmas "Found" y "Available", junto con clases de Generator Accelerator.
		if (consola.contenido_verificar.contains("Found:") && consola.contenido_verificar.contains("Available:")
				&& consola.contenido_verificar.contains("dev/sixik/generator_accelerator/common/features/FastTarget")) {
			posibleProblemaGeneratorAccelerator = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales
		if (!posibleProblemaGeneratorAccelerator) {
			return;
		}

		// Verificación precisa en una de las líneas más representativas del error.
		if (linea.contains("dev/sixik/generator_accelerator/common/features/FastTarget")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Alternativa: si se encuentra primero la línea "Found", también puede servir
		// como punto de referencia útil.
		if (linea.contains("Found:") && linea.contains("Ljava/util/BitSet;")
				&& consola.contenido_verificar.contains("dev/sixik/generator_accelerator")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
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

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}