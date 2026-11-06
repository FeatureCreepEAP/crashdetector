package com.asbestosstar.crashdetector.analizador.general;

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
 * Detecta cuando FeatureCreep falla al adjuntar el agente HotSpot directamente.
 *
 * El error típico es:
 * "Direct HotSpot attach is unsupported on &lt;os.name&gt;"
 * "[BootstrapCommon] FeatureCreep direct attach failed: &lt;exception&gt;"
 *
 * Esto ocurre cuando el mecanismo de adjunción nativa de FeatureCreep no puede
 * conectarse a la JVM actual. En la mayoría de los casos, el juego puede
 * continuar sin Instrumentation; solo algunos mods la requieren. Por eso
 * esta verificación es una advertencia de baja prioridad, no un error.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaFeatureCreep"
 * }, nota = "Cubre deteccion de fallo de adjuncion HotSpot de FeatureCreep y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaFeatureCreep" },
		nota = "Cubre deteccion de fallo de adjuncion HotSpot de FeatureCreep y falsos positivos")
public class AdvertenciaHotSpotAttachFeatureCreep implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	/*
	 * La misma instancia de Verificaciones puede recibir líneas procedentes de más
	 * de una Consola. El estado se conserva por identidad de Consola para evitar
	 * combinar pruebas procedentes de archivos diferentes.
	 */
	private final Map<Consola, Boolean> tieneFeatureCreep = new IdentityHashMap<Consola, Boolean>();

	private static final String HOTSPOT_ATTACH_UNSUPPORTED = "Direct HotSpot attach is unsupported on";

	private static final String FEATURECREEP_ATTACH_FAILED = "FeatureCreep direct attach failed";

	private static final String FEATURECREEP_LOADER = "featurecreep";

	@Override
	public String[] patronesRapidos() {
		return new String[] { HOTSPOT_ATTACH_UNSUPPORTED, FEATURECREEP_ATTACH_FAILED, FEATURECREEP_LOADER };
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

		/*
		 * Registra si esta consola menciona FeatureCreep.
		 */
		if (lineaContieneFeatureCreep(linea)) {
			tieneFeatureCreep.put(consola, true);
		}

		/*
		 * El error principal es el fallo de adjuncion HotSpot. Se activa solo si el
		 * registro menciona FeatureCreep.
		 */
		if (lineaContieneErrorAttach(linea) && tieneContextoFeatureCreep(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	/**
	 * Comprueba si una línea contiene un error de adjuncion HotSpot.
	 */
	private boolean lineaContieneErrorAttach(String linea) {
		return linea.contains(HOTSPOT_ATTACH_UNSUPPORTED) || linea.contains(FEATURECREEP_ATTACH_FAILED);
	}

	/**
	 * Verifica si una línea menciona FeatureCreep.
	 */
	private boolean lineaContieneFeatureCreep(String linea) {
		return linea.toLowerCase().contains(FEATURECREEP_LOADER);
	}

	/**
	 * Verifica si la consola ya registró menciones de FeatureCreep.
	 */
	private boolean tieneContextoFeatureCreep(Consola consola) {
		Boolean encontrado = tieneFeatureCreep.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new AdvertenciaHotSpotAttachFeatureCreep();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 500f; // Prioridad baja: es una advertencia, no un error critico
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeAdvertenciaHotSpotAttachFeatureCreep() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAdvertenciaHotSpotAttachFeatureCreep();
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
		return "advertencia_hotspot_attach_featurecreep";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
