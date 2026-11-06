package com.asbestosstar.crashdetector.analizador.general;

import java.util.IdentityHashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

/**
 * Detecta cuando FeatureCreep encuentra una incompatibilidad de versión entre
 * módulos cargados.
 *
 * El error típico es:
 * "FCModuleCompatibility" con mensajes de versión no satisfecha o
 * "rangeMismatch" cuando la versión del juego no cumple los requisitos
 * del módulo.
 *
 * Cuando las versiones son incompatibles, los módulos pueden fallar con
 * ClassNotFoundException o NoSuchMethodError en tiempo de ejecución.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaFeatureCreep"
 * }, nota = "Cubre deteccion de incompatibilidad de version de FeatureCreep")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaFeatureCreep" },
		nota = "Cubre deteccion de incompatibilidad de version de FeatureCreep")
public class ErrorVersionModuloFeatureCreep implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneFeatureCreep = new IdentityHashMap<Consola, Boolean>();

	private static final String VERSION_MISMATCH = "FCModuleCompatibility";

	private static final String RANGE_MISMATCH = "rangeMismatch";

	private static final String VERSION_NOT_SATISFIED = "version.*not.*satisf";

	private static final String FEATURECREEP_LOADER = "featurecreep";

	@Override
	public String[] patronesRapidos() {
		return new String[] { VERSION_MISMATCH, RANGE_MISMATCH, FEATURECREEP_LOADER };
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

		if (lineaContieneFeatureCreep(linea)) {
			tieneFeatureCreep.put(consola, true);
		}

		if (lineaContieneErrorVersion(linea) && tieneContextoFeatureCreep(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorVersion(String linea) {
		return linea.contains(VERSION_MISMATCH) || linea.contains(RANGE_MISMATCH);
	}

	private boolean lineaContieneFeatureCreep(String linea) {
		return linea.toLowerCase().contains(FEATURECREEP_LOADER);
	}

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
		return new ErrorVersionModuloFeatureCreep();
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
		return MonitorDePID.idioma.mensajeErrorVersionModuloFeatureCreep() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorVersionModuloFeatureCreep();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "error_version_modulo_featurecreep";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
