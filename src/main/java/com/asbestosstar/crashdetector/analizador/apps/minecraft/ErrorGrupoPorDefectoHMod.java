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
 * Detecta cuando HMod no encuentra un grupo por defecto en la base de datos.
 *
 * El error tÃ­pico es:
 * "SEVERE: No default group! Expect lots of errors!"
 *
 * HMod requiere un grupo marcado como DefaultGroup en la tabla de grupos.
 * Sin Ã©l, los nuevos jugadores no se asignan a ningÃºn grupo, lo que causa
 * NullPointerExceptions en operaciones de permisos y comandos.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaHMod"
 * }, nota = "Cubre deteccion de grupo por defecto faltante en HMod y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaHMod" },
		nota = "Cubre deteccion de grupo por defecto faltante en HMod y falsos positivos")
public class ErrorGrupoPorDefectoHMod implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneHMod = new IdentityHashMap<Consola, Boolean>();

	private static final String NO_DEFAULT_GROUP = "No default group";

	private static final String HMOD_CONTEXT = "hmod";

	private static final String DATASOURCE = "datasource";

	@Override
	public String[] patronesRapidos() {
		return new String[] { NO_DEFAULT_GROUP, HMOD_CONTEXT, DATASOURCE };
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

		if (lineaContieneErrorGrupo(linea) && tieneContextoHMod(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorGrupo(String linea) {
		return linea.contains(NO_DEFAULT_GROUP);
	}

	private boolean lineaContieneHMod(String linea) {
		String lower = linea.toLowerCase();
		return lower.contains(HMOD_CONTEXT) || lower.contains(DATASOURCE);
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
		return new ErrorGrupoPorDefectoHMod();
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
		return MonitorDePID.idioma.mensajeErrorGrupoPorDefectoHMod() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorGrupoPorDefectoHMod();
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
		return "error_grupo_por_defecto_hmod";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
