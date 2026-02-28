package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class RutaCaracteresInvalidos implements Verificaciones {

	// Indica si el log contiene indicios globales del error (optimización de rendimiento)
	private boolean posibleRutaInvalida = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		// Detección global ligera:
		// Solo buscamos la excepción principal sin usar regex ni operaciones costosas.
		if (consola.contenido_verificar.contains("java.nio.file.InvalidPathException")
				&& consola.contenido_verificar.contains("Illegal char <:>")) {

			posibleRutaInvalida = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int num) {

		// Salir temprano si no hay indicios globales
		if (!posibleRutaInvalida) {
			return;
		}

		// Verificación precisa en la línea específica
		if (linea.contains("java.nio.file.InvalidPathException")
				&& linea.contains("Illegal char <:>")) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new RutaCaracteresInvalidos();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1200;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeRutaCaracteresInvalidos() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreRutaCaracteresInvalidos();
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
		return "ruta_caracteres_invalidos";
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