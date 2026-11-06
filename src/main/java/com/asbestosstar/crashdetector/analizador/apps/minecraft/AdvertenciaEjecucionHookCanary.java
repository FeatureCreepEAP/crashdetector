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
 * Detecta cuando un hook de Canary/Neptune lanza una excepcion durante la ejecucion.
 *
 * El error típico es:
 * "Exception while executing Hook: X in PluginListener: Y (Plugin: Z)"
 *
 * Canary captura las excepciones de los hooks y las registra como errores,
 * pero permite que el servidor continúe funcionando. Es una advertencia porque
 * el servidor no se detiene, pero la funcionalidad del plugin afectado está rota.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaCanary"
 * }, nota = "Cubre deteccion de advertencia de ejecucion de hook en Canary/Neptune")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaCanary" },
		nota = "Cubre deteccion de advertencia de ejecucion de hook en Canary/Neptune")
public class AdvertenciaEjecucionHookCanary implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneCanary = new IdentityHashMap<Consola, Boolean>();

	private static final String HOOK_EXECUTION_ERROR = "Exception while executing Hook";

	private static final String CANARY_CONTEXT = "canary";

	private static final String NEPTUNE_CONTEXT = "neptune";

	@Override
	public String[] patronesRapidos() {
		return new String[] { HOOK_EXECUTION_ERROR, CANARY_CONTEXT, NEPTUNE_CONTEXT };
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
		return linea.contains(HOOK_EXECUTION_ERROR);
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
		return new AdvertenciaEjecucionHookCanary();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 500f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeAdvertenciaEjecucionHookCanary() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAdvertenciaEjecucionHookCanary();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ADVERTENCIA;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "advertencia_ejecucion_hook_canary";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
