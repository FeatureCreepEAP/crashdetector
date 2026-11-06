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
 * Detecta cuando ModLoader falla al inicializar.
 *
 * El error típico es:
 * "ModLoader has failed to initialize."
 *
 * Cuando ModLoader no puede inicializar, el juego no puede cargar mods
 * y falla al iniciar. Esto puede ser causado por errores de reflexión,
 * incompatibilidad de versión, o mods corruptos.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaModLoader"
 * }, nota = "Cubre deteccion de error de inicializacion de ModLoader y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaModLoader" },
		nota = "Cubre deteccion de error de inicializacion de ModLoader y falsos positivos")
public class ErrorInicializacionModLoader implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneModLoader = new IdentityHashMap<Consola, Boolean>();

	private static final String FAILED_TO_INITIALIZE = "ModLoader has failed to initialize";

	private static final String MODLOADER_CONTEXT = "modloader";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FAILED_TO_INITIALIZE, MODLOADER_CONTEXT };
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

		if (lineaContieneModLoader(linea)) {
			tieneModLoader.put(consola, true);
		}

		if (lineaContieneErrorInicializacion(linea) && tieneContextoModLoader(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorInicializacion(String linea) {
		return linea.contains(FAILED_TO_INITIALIZE);
	}

	private boolean lineaContieneModLoader(String linea) {
		String lower = linea.toLowerCase();
		return lower.contains(MODLOADER_CONTEXT);
	}

	private boolean tieneContextoModLoader(Consola consola) {
		Boolean encontrado = tieneModLoader.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorInicializacionModLoader();
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
		return MonitorDePID.idioma.mensajeErrorInicializacionModLoader() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorInicializacionModLoader();
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
		return "error_inicializacion_modloader";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
