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
 * Detecta cuando Canary/Neptune no puede cargar un plugin porque es inválido.
 *
 * El error típico es:
 * "InvalidPluginException" o "Could not find databases mainclass"
 *
 * Canary usa un sistema de plugins similar a Bukkit. Si un plugin está
 * corrupto, tiene un Canary.inf mal formado, o la clase principal no existe,
 * se lanza InvalidPluginException y el plugin no se carga.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaCanary"
 * }, nota = "Cubre deteccion de error de plugin invalido en Canary/Neptune")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaCanary" },
		nota = "Cubre deteccion de error de plugin invalido en Canary/Neptune")
public class ErrorPluginInvalidoCanary implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneCanary = new IdentityHashMap<Consola, Boolean>();

	private static final String INVALID_PLUGIN = "InvalidPluginException";

	private static final String COULD_NOT_FIND_MAINCLASS = "Could not find databases mainclass";

	private static final String CANARY_CONTEXT = "canary";

	private static final String NEPTUNE_CONTEXT = "neptune";

	@Override
	public String[] patronesRapidos() {
		return new String[] { INVALID_PLUGIN, COULD_NOT_FIND_MAINCLASS, CANARY_CONTEXT, NEPTUNE_CONTEXT };
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

		if (lineaContieneCanary(linea)) {
			tieneCanary.put(consola, true);
		}

		if (lineaContieneError(linea) && tieneContextoCanary(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneError(String linea) {
		return linea.contains(INVALID_PLUGIN) || linea.contains(COULD_NOT_FIND_MAINCLASS);
	}

	private boolean lineaContieneCanary(String linea) {
		String lower = linea.toLowerCase();
		return lower.contains(CANARY_CONTEXT) || lower.contains(NEPTUNE_CONTEXT);
	}

	private boolean tieneContextoCanary(Consola consola) {
		Boolean encontrado = tieneCanary.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorPluginInvalidoCanary();
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
		return MonitorDePID.idioma.mensajeErrorPluginInvalidoCanary() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorPluginInvalidoCanary();
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
		return "error_plugin_invalido_canary";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
