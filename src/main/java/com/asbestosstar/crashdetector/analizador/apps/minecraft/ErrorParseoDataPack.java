package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores donde un datapack o resourcepack falla al parsearse y provoca
 * errores de carga del registry.
 *
 * Requiere que existan: - "Failed to parse" - "Registry loading errors:"
 */
public class ErrorParseoDataPack implements Verificaciones {

	private boolean activado = false;
	private boolean vioParse = false;
	private boolean vioRegistry = false;

	private String enlace = "";
	private String archivo = "";
	private String pack = "";

	private static final String FAILED_TO_PARSE = "Failed to parse";
	private static final String REGISTRY_LOADING_ERRORS = "Registry loading errors:";
	private static final String FROM_PACK = " from pack ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FAILED_TO_PARSE, REGISTRY_LOADING_ERRORS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea.contains(FAILED_TO_PARSE)) {
			vioParse = true;
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			// Extraer archivo y pack si es posible
			int inicioArchivo = linea.indexOf(FAILED_TO_PARSE) + FAILED_TO_PARSE.length();
			int desdePack = linea.indexOf(FROM_PACK);

			if (desdePack > -1) {
				archivo = linea.substring(inicioArchivo, desdePack).trim();
				pack = linea.substring(desdePack + FROM_PACK.length()).trim();
			}
		}

		if (linea.contains(REGISTRY_LOADING_ERRORS)) {
			vioRegistry = true;
		}

		if (vioParse && vioRegistry) {
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorParseoDataPack();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorParseoDataPack(archivo, pack) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorParseoDataPack();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "error_parseo_datapack";
	}

	@Override
	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}