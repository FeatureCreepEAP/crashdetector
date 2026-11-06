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
 * Detecta cuando un plugin de HMod lanza una excepciÃ³n durante la ejecuciÃ³n de un hook.
 *
 * El error tÃ­pico es:
 * "SEVERE: Exception while calling plugin function in '&lt;class&gt;' while calling hook: '&lt;hook&gt;'"
 *
 * A diferencia de los errores de carga, esto ocurre en tiempo de ejecuciÃ³n cuando
 * un plugin registrado falla al procesar un evento. HMod captura la excepciÃ³n
 * y continua, pero el plugin no funcionarÃ¡ correctamente. Es una advertencia
 * porque el servidor sigue funcionando, pero el plugin afectado estÃ¡ roto.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaHMod"
 * }, nota = "Cubre deteccion de excepciones de hooks de plugin en HMod y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaHMod" },
		nota = "Cubre deteccion de excepciones de hooks de plugin en HMod y falsos positivos")
public class AdvertenciaPluginHookHMod implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneHMod = new IdentityHashMap<Consola, Boolean>();

	private static final String EXCEPTION_CALLING_PLUGIN = "Exception while calling plugin function";

	private static final String HMOD_CONTEXT = "hmod";

	private static final String PLUGINLOADER = "pluginloader";

	@Override
	public String[] patronesRapidos() {
		return new String[] { EXCEPTION_CALLING_PLUGIN, HMOD_CONTEXT, PLUGINLOADER };
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

		if (lineaContieneHMod(linea)) {
			tieneHMod.put(consola, true);
		}

		if (lineaContieneErrorHook(linea) && tieneContextoHMod(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorHook(String linea) {
		return linea.contains(EXCEPTION_CALLING_PLUGIN);
	}

	private boolean lineaContieneHMod(String linea) {
		String lower = linea.toLowerCase();
		return lower.contains(HMOD_CONTEXT) || lower.contains(PLUGINLOADER);
	}

	private boolean tieneContextoHMod(Consola consola) {
		Boolean encontrado = tieneHMod.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new AdvertenciaPluginHookHMod();
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
		return MonitorDePID.idioma.mensajeAdvertenciaPluginHookHMod() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAdvertenciaPluginHookHMod();
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
		return "advertencia_plugin_hook_hmod";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
