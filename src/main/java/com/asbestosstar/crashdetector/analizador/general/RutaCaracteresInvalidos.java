package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class RutaCaracteresInvalidos implements VerificacionRapida {

	// Indica si el log contiene indicios globales del error (optimización de
	// rendimiento)
	private boolean posibleRutaInvalida = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String TEXTO_INVALID_PATH = "java.nio.file.InvalidPathException";
	private static final String TEXTO_ILLEGAL_CHAR = "Illegal char <:>";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_INVALID_PATH, TEXTO_ILLEGAL_CHAR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		if (lineaContieneRutaInvalida(evento.linea)) {
			posibleRutaInvalida = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		// Detección global ligera:
		// Solo buscamos la excepción principal sin usar regex ni operaciones costosas.
		if (consola.contenido_verificar.contains(TEXTO_INVALID_PATH)
				&& consola.contenido_verificar.contains(TEXTO_ILLEGAL_CHAR)) {

			posibleRutaInvalida = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleRutaInvalida && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleRutaInvalida || activado || linea == null || linea.isEmpty()) {
			return;
		}

		// Verificación precisa en la línea específica
		if (lineaContieneRutaInvalida(linea)) {
			if (consola != null) {
				this.enlace = consola.agregarErrorALectador(num, this);
			}

			this.activado = true;
		}
	}

	private boolean lineaContieneRutaInvalida(String linea) {
		return linea.contains(TEXTO_INVALID_PATH) && linea.contains(TEXTO_ILLEGAL_CHAR);
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

}