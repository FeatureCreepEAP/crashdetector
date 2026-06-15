package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta mundos duplicados que no pueden cargarse en Minecraft. Moderniza la
 * detección sin usar Pattern/Matcher.
 */
public class ProblemaMundoDuplicado implements VerificacionRapida {

	private boolean posibleMundoDuplicado = false;
	private boolean activado = false;

	private String nombreMundo = "";
	private String enlace = "";

	private static final String TEXTO_INICIO = "World ";
	private static final String TEXTO_MEDIO = " is a duplicate of another world and has been prevented from loading";
	private static final String TEXTO_FINAL = "uid.dat";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_MEDIO, TEXTO_FINAL };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneIndicioMundoDuplicado(evento.linea)) {
			posibleMundoDuplicado = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(TEXTO_INICIO) && contenido.contains(TEXTO_MEDIO) && contenido.contains(TEXTO_FINAL)) {
			posibleMundoDuplicado = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleMundoDuplicado && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleMundoDuplicado || activado || linea == null || linea.isEmpty()) {
			return;
		}

		int inicio = linea.indexOf(TEXTO_INICIO);
		if (inicio < 0)
			return;

		int inicioMundo = inicio + TEXTO_INICIO.length();
		int finMundo = linea.indexOf(TEXTO_MEDIO, inicioMundo);
		if (finMundo <= inicioMundo)
			return;

		if (!linea.contains(TEXTO_FINAL)) {
			return;
		}

		String mundo = linea.substring(inicioMundo, finMundo).trim();
		if (mundo.isEmpty())
			return;

		nombreMundo = mundo;
		enlace = consola.agregarErrorALectador(numero_de_linea, this);

		activado = true;
	}

	private boolean lineaContieneIndicioMundoDuplicado(String linea) {
		return linea.contains(TEXTO_MEDIO) || linea.contains(TEXTO_FINAL);
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaMundoDuplicado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 700.0f;
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return MonitorDePID.idioma.mensajeMundoDuplicado(nombreMundo) + " " + enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaMundoDuplicado();
	}

	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionEliminarUID(nombreMundo))
				.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMundo(nombreMundo)).construir();
	}

	@Override
	public String id() {
		return "mundo_duplicado";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null)
			return false;
		return trazo.trace.contains(TEXTO_MEDIO) && trazo.trace.contains(TEXTO_FINAL);
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