package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores al cargar un mundo con Multiverse. Moderniza la detección sin
 * usar Pattern/Matcher.
 */
public class ProblemaCargaMultiverso implements Verificaciones {

	private boolean activado = false;

	private String nombreMundo = "";
	private String enlace = "";
	public String mensaje = "";

	private static final String TEXTO_ERROR = "The world '";
	private static final String TEXTO_FIN = "' could NOT be loaded because it contains errors and is probably corrupt!";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR, TEXTO_FIN };
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

		int inicio = linea.indexOf(TEXTO_ERROR);
		if (inicio < 0)
			return;

		int startMundo = inicio + TEXTO_ERROR.length();
		int finMundo = linea.indexOf(TEXTO_FIN, startMundo);

		if (finMundo <= startMundo)
			return;

		nombreMundo = linea.substring(startMundo, finMundo).trim();
		enlace = consola.agregarErrorALectador(numero_de_linea, this);
		mensaje = MonitorDePID.idioma.mensajeProblemaCargaMultiverso(nombreMundo) + " " + enlace;
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaCargaMultiverso();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaCargaMultiverso();
	}

	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionRepararMundo(nombreMundo))
				.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarCarpetaMundo(nombreMundo)).construir();
	}

	@Override
	public boolean anularNormal() {
		return true;
	}

	@Override
	public String id() {
		return "carga_multiverso";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_ERROR, TEXTO_FIN };
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}