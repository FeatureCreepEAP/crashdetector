package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class EntradaDuplicadaIdModerno implements Verificaciones {

	// Indica si el log contiene indicios globales del error
	private boolean posibleEntradaDuplicada = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global ligera: en versiones modernas de Minecraft este error suele
		// indicar que dos mods registraron entradas distintas usando el mismo ID.
		if (consola.contenido_verificar.contains("java.lang.IllegalArgumentException: Duplicate entry on id")
				&& consola.contenido_verificar.contains("current=")
				&& consola.contenido_verificar.contains("previous=")) {
			posibleEntradaDuplicada = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales
		if (!posibleEntradaDuplicada) {
			return;
		}

		// Verificación precisa en la línea principal del error
		if (linea.contains("java.lang.IllegalArgumentException: Duplicate entry on id") && linea.contains("current=")
				&& linea.contains("previous=")) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
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

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}