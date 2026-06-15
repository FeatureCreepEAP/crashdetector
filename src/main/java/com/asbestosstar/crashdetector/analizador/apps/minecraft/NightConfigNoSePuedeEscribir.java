package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class NightConfigNoSePuedeEscribir implements VerificacionRapida {

	// Indica si el log contiene indicios globales del error
	private boolean posibleErrorEscritura = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	// Ruta del archivo de configuración afectado
	private String rutaConfig = "";

	private static final String WRITING_EXCEPTION_COMPLETO = "com.electronwill.nightconfig.core.io.WritingException";
	private static final String WRITING_EXCEPTION = "WritingException";
	private static final String FAILED_TO_WRITE = "Failed to write";
	private static final String TO = "to:";

	@Override
	public String[] patronesRapidos() {
		return new String[] { WRITING_EXCEPTION_COMPLETO, FAILED_TO_WRITE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneErrorEscritura(evento.linea)) {
			posibleErrorEscritura = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		// Detección global por rendimiento
		if (consola.contenido_verificar.contains(WRITING_EXCEPTION_COMPLETO)
				&& consola.contenido_verificar.contains(FAILED_TO_WRITE)) {
			posibleErrorEscritura = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleErrorEscritura && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleErrorEscritura || activado || linea == null)
			return;

		if (lineaContieneErrorEscritura(linea)) {

			// Extraer la ruta del archivo desde la línea
			this.rutaConfig = extraerRuta(linea);

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneErrorEscritura(String linea) {
		return linea.contains(WRITING_EXCEPTION) && linea.contains(FAILED_TO_WRITE);
	}

	// Intenta extraer la ruta del archivo después de "to:"
	private String extraerRuta(String linea) {
		int idx = linea.lastIndexOf(TO);
		if (idx == -1)
			return "";

		return linea.substring(idx + TO.length()).trim();
	}

	@Override
	public Verificaciones nueva() {
		return new NightConfigNoSePuedeEscribir();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1100;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeNightConfigNoSePuedeEscribir(rutaConfig) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreNightConfigNoSePuedeEscribir();
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
		return "nightconfig_no_se_puede_escribir";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}