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
 * Detecta cuando FeatureCreep falla al inicializar el runtime de Mixin.
 *
 * Los errores típicos son:
 * "Standalone Mixin bootstrap failed"
 * "Mixin transform failed for"
 * "Vanilla Mixin configs require startup Instrumentation"
 * "Standalone Mixin runtime is not installed"
 *
 * Cuando Mixin no puede inicializarse, los mods que dependen de inyección
 * de Mixin no pueden cargar, lo que causa fallos graves en el inicio.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaFeatureCreep"
 * }, nota = "Cubre deteccion de fallo de bootstrap de Mixin de FeatureCreep")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaFeatureCreep" },
		nota = "Cubre deteccion de fallo de bootstrap de Mixin de FeatureCreep")
public class ErrorMixinBootstrapFeatureCreep implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneFeatureCreep = new IdentityHashMap<Consola, Boolean>();

	private static final String MIXIN_BOOTSTRAP_FAILED = "Standalone Mixin bootstrap failed";

	private static final String MIXIN_TRANSFORM_FAILED = "Mixin transform failed for";

	private static final String MIXIN_REQUIRE_INSTRUMENTATION = "Vanilla Mixin configs require startup Instrumentation";

	private static final String MIXIN_RUNTIME_NOT_INSTALLED = "Standalone Mixin runtime is not installed";

	private static final String FEATURECREEP_LOADER = "featurecreep";

	@Override
	public String[] patronesRapidos() {
		return new String[] { MIXIN_BOOTSTRAP_FAILED, MIXIN_TRANSFORM_FAILED, MIXIN_REQUIRE_INSTRUMENTATION,
				MIXIN_RUNTIME_NOT_INSTALLED, FEATURECREEP_LOADER };
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

		if (lineaContieneErrorMixin(linea) && tieneContextoFeatureCreep(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorMixin(String linea) {
		return linea.contains(MIXIN_BOOTSTRAP_FAILED) || linea.contains(MIXIN_TRANSFORM_FAILED)
				|| linea.contains(MIXIN_REQUIRE_INSTRUMENTATION) || linea.contains(MIXIN_RUNTIME_NOT_INSTALLED);
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
		return new ErrorMixinBootstrapFeatureCreep();
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
		return MonitorDePID.idioma.mensajeErrorMixinBootstrapFeatureCreep() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorMixinBootstrapFeatureCreep();
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
		return "error_mixin_bootstrap_featurecreep";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
