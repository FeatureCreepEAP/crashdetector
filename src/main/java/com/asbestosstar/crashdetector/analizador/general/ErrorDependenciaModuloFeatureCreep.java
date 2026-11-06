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
 * Detecta cuando FeatureCreep falla al resolver el grafo de dependencias de
 * módulos.
 *
 * El error típico es:
 * "IncompatibleEventListenerMethodReference" o mensajes de dependencias
 * no satisfechas en el grafo de módulos de FeatureCreep.
 *
 * Cuando el grafo de dependencias no se puede resolver, los módulos
 * incompatibles o cíclicos impiden que el juego inicie correctamente.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaFeatureCreep"
 * }, nota = "Cubre deteccion de fallo de grafo de dependencias de FeatureCreep")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaFeatureCreep" },
		nota = "Cubre deteccion de fallo de grafo de dependencias de FeatureCreep")
public class ErrorDependenciaModuloFeatureCreep implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneFeatureCreep = new IdentityHashMap<Consola, Boolean>();

	private static final String INCOMPATIBLE_EVENT = "IncompatibleEventListenerMethodReference";

	private static final String UNMET_DEPENDENCY = "unmet";

	private static final String CYCLIC_DEPENDENCY = "cyclic";

	private static final String FEATURECREEP_LOADER = "featurecreep";

	@Override
	public String[] patronesRapidos() {
		return new String[] { INCOMPATIBLE_EVENT, UNMET_DEPENDENCY, CYCLIC_DEPENDENCY, FEATURECREEP_LOADER };
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

		if (lineaContieneErrorDependencia(linea) && tieneContextoFeatureCreep(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorDependencia(String linea) {
		return linea.contains(INCOMPATIBLE_EVENT) || linea.toLowerCase().contains(UNMET_DEPENDENCY)
				|| linea.toLowerCase().contains(CYCLIC_DEPENDENCY);
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
		return new ErrorDependenciaModuloFeatureCreep();
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
		return MonitorDePID.idioma.mensajeErrorDependenciaModuloFeatureCreep() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorDependenciaModuloFeatureCreep();
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
		return "error_dependencia_modulo_featurecreep";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
