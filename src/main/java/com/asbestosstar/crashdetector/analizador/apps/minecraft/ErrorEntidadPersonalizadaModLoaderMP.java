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
 * Detecta cuando ModLoaderMP falla al inicializar una entidad personalizada.
 *
 * El error típico es:
 * "Error initializing entity of type %s."
 *
 * ModLoaderMP permite registrar entidades personalizadas para multijugador.
 * Si la entidad no se puede crear (constructor faltante, clase no encontrada,
 * o error de reflexión), el spawn falla y puede causar un crash.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaModLoaderMP"
 * }, nota = "Cubre deteccion de error de entidad personalizada en ModLoaderMP")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaModLoaderMP" },
		nota = "Cubre deteccion de error de entidad personalizada en ModLoaderMP")
public class ErrorEntidadPersonalizadaModLoaderMP implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneModLoaderMP = new IdentityHashMap<Consola, Boolean>();

	private static final String ERROR_INITIALIZING_ENTITY = "Error initializing entity of type";

	private static final String MODLOADERMP_CONTEXT = "modloadermp";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ERROR_INITIALIZING_ENTITY, MODLOADERMP_CONTEXT };
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

		if (lineaContieneModLoaderMP(linea)) {
			tieneModLoaderMP.put(consola, true);
		}

		if (lineaContieneError(linea) && tieneContextoModLoaderMP(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneError(String linea) {
		return linea.contains(ERROR_INITIALIZING_ENTITY);
	}

	private boolean lineaContieneModLoaderMP(String linea) {
		String lower = linea.toLowerCase();
		return lower.contains(MODLOADERMP_CONTEXT);
	}

	private boolean tieneContextoModLoaderMP(Consola consola) {
		Boolean encontrado = tieneModLoaderMP.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorEntidadPersonalizadaModLoaderMP();
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
		return MonitorDePID.idioma.mensajeErrorEntidadPersonalizadaModLoaderMP() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorEntidadPersonalizadaModLoaderMP();
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
		return "error_entidad_personalizada_modloadermp";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
