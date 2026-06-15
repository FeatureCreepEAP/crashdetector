package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de animaciones faltantes en Cobblemon.
 *
 * Ejemplo: Animation idle not found in animation group battle
 *
 * Este problema es específico de Cobblemon y addons relacionados.
 */
public class ProblemaAnimacionCobblemon implements VerificacionRapida {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";
	private String animacion = "";
	private String grupo = "";

	private static final String ANIMATION = "Animation ";
	private static final String NOT_FOUND_IN_ANIMATION_GROUP = " not found in animation group ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ANIMATION, NOT_FOUND_IN_ANIMATION_GROUP };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneProblemaAnimacion(evento.linea)) {
			analizarLineas = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String log = consola.contenido_verificar;

		// Pre-check global: patrón específico
		if (log.contains(ANIMATION) && log.contains(NOT_FOUND_IN_ANIMATION_GROUP)) {
			analizarLineas = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return analizarLineas && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (lineaContieneProblemaAnimacion(linea)) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			int inicioAnim = linea.indexOf(ANIMATION) + ANIMATION.length();
			int finAnim = linea.indexOf(NOT_FOUND_IN_ANIMATION_GROUP);

			if (inicioAnim > -1 && finAnim > inicioAnim) {
				animacion = linea.substring(inicioAnim, finAnim).trim();
			}

			int inicioGrupo = finAnim + NOT_FOUND_IN_ANIMATION_GROUP.length();
			if (inicioGrupo > -1 && inicioGrupo < linea.length()) {
				grupo = linea.substring(inicioGrupo).trim();
			}

			activado = true;
		}
	}

	private boolean lineaContieneProblemaAnimacion(String linea) {
		return linea.contains(ANIMATION) && linea.contains(NOT_FOUND_IN_ANIMATION_GROUP);
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaAnimacionCobblemon();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeAnimacionCobblemon(animacion, grupo) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaAnimacionCobblemon();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "problema_animacion_cobblemon";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}