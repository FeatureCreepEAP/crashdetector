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
 * Detecta cuando Rift no puede leer el archivo riftmod.json de un mod.
 *
 * El error típico es:
 * "Could not read riftmod.json in &lt;file&gt;"
 *
 * Cuando el archivo de manifiesto del mod está dañado o malformado, Rift
 * no puede cargar el mod y el juego falla al iniciar.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaRift"
 * }, nota = "Cubre deteccion de riftmod.json invalido en Rift y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaRift" },
		nota = "Cubre deteccion de riftmod.json invalido en Rift y falsos positivos")
public class ErrorRiftModJSONRift implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneRift = new IdentityHashMap<Consola, Boolean>();

	private static final String COULD_NOT_READ_RIFTMOD = "Could not read riftmod.json";

	private static final String RIFT_LOADER = "rift";

	@Override
	public String[] patronesRapidos() {
		return new String[] { COULD_NOT_READ_RIFTMOD, RIFT_LOADER };
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

		if (lineaContieneErrorRiftModJSON(linea) && tieneContextoRift(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorRiftModJSON(String linea) {
		return linea.contains(COULD_NOT_READ_RIFTMOD);
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
		return new ErrorRiftModJSONRift();
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
		return MonitorDePID.idioma.mensajeErrorRiftModJSONRift() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorRiftModJSONRift();
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
		return "error_riftmod_json_rift";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
