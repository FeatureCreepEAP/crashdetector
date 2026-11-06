package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.IdentityHashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

/**
 * Detecta cuando Rift no puede cargar una clase de listener de un mod.
 *
 * El error típico es:
 * "Failed to find listener class &lt;class_name&gt;"
 *
 * Cuando una clase de listener referenciada en el riftmod.json no existe,
 * Rift no puede inicializar el mod y el juego falla al iniciar.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaRift"
 * }, nota = "Cubre deteccion de fallo de listener en Rift y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaRift" },
		nota = "Cubre deteccion de fallo de listener en Rift y falsos positivos")
public class ErrorListenerRift implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneRift = new IdentityHashMap<Consola, Boolean>();

	private static final String FAILED_TO_FIND_LISTENER = "Failed to find listener class";

	private static final String RIFT_LOADER = "rift";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FAILED_TO_FIND_LISTENER, RIFT_LOADER };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.consola == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {
		if (activado || consola == null || linea == null) {
			return;
		}

		if (lineaContieneRift(linea)) {
			tieneRift.put(consola, true);
		}

		if (lineaContieneErrorListener(linea) && tieneContextoRift(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorListener(String linea) {
		return linea.contains(FAILED_TO_FIND_LISTENER);
	}

	private boolean lineaContieneRift(String linea) {
		return linea.toLowerCase().contains(RIFT_LOADER);
	}

	private boolean tieneContextoRift(Consola consola) {
		Boolean encontrado = tieneRift.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorListenerRift();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1800f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorListenerRift() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorListenerRift();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ERROR;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "error_listener_rift";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
