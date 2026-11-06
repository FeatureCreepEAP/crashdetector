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
 * Detecta cuando ModLoaderMP recibe un mod nulo al enviar paquetes o teclas.
 *
 * El error típico es:
 * "baseModMp cannot be null."
 *
 * ModLoaderMP requiere una referencia válida a BaseModMp para enviar paquetes
 * o teclas. Si un mod llama a sendPacket o sendKey con null, se lanza
 * IllegalArgumentException y el juego falla.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaModLoaderMP"
 * }, nota = "Cubre deteccion de error de mod nulo en ModLoaderMP")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaModLoaderMP" },
		nota = "Cubre deteccion de error de mod nulo en ModLoaderMP")
public class ErrorModNuloModLoaderMP implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneModLoaderMP = new IdentityHashMap<Consola, Boolean>();

	private static final String BASEMODMP_NULL = "baseModMp cannot be null";

	private static final String MODLOADERMP_CONTEXT = "modloadermp";

	@Override
	public String[] patronesRapidos() {
		return new String[] { BASEMODMP_NULL, MODLOADERMP_CONTEXT };
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
		return linea.contains(BASEMODMP_NULL);
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
		return new ErrorModNuloModLoaderMP();
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
		return MonitorDePID.idioma.mensajeErrorModNuloModLoaderMP() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorModNuloModLoaderMP();
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
		return "error_mod_nulo_modloadermp";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
