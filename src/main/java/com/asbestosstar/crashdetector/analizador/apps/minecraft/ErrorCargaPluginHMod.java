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
 * Detecta cuando HMod falla al cargar un plugin.
 *
 * Los errores tÃ­picos son:
 * "SEVERE: Exception while loading plugin" con ClassNotFoundException/NoClassDefFoundError
 * "SEVERE: Failed to find plugin file: plugins/&lt;name&gt;.jar"
 *
 * HMod carga plugins dinÃ¡micamente desde el directorio plugins/. Si un plugin
 * estÃ¡ corrupto, es incompatible, o falta su JAR, el cargador falla. Aunque
 * el servidor puede continuar sin ese plugin, indica un problema de configuraciÃ³n.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaHMod"
 * }, nota = "Cubre deteccion de error de carga de plugin en HMod y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaHMod" },
		nota = "Cubre deteccion de error de carga de plugin en HMod y falsos positivos")
public class ErrorCargaPluginHMod implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneHMod = new IdentityHashMap<Consola, Boolean>();

	private static final String EXCEPTION_LOADING_PLUGIN = "Exception while loading plugin";

	private static final String FAILED_PLUGIN_FILE = "Failed to find plugin file";

	private static final String HMOD_CONTEXT = "hmod";

	private static final String PLUGINLOADER = "pluginloader";

	@Override
	public String[] patronesRapidos() {
		return new String[] { EXCEPTION_LOADING_PLUGIN, FAILED_PLUGIN_FILE, HMOD_CONTEXT, PLUGINLOADER };
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

		if (lineaContieneErrorPlugin(linea) && tieneContextoHMod(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorPlugin(String linea) {
		return linea.contains(EXCEPTION_LOADING_PLUGIN) || linea.contains(FAILED_PLUGIN_FILE);
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
		return new ErrorCargaPluginHMod();
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
		return MonitorDePID.idioma.mensajeErrorCargaPluginHMod() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorCargaPluginHMod();
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
		return "error_carga_plugin_hmod";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
