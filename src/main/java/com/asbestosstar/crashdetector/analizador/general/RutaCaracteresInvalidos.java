package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class RutaCaracteresInvalidos implements Verificaciones {

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

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

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
	public String[] ocupaTrazo() {
		return new String[0];
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