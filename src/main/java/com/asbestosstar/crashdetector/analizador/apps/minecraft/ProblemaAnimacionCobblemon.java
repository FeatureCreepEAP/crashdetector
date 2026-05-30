package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de animaciones faltantes en Cobblemon.
 *
 * Ejemplo: Animation idle not found in animation group battle
 *
 * Este problema es específico de Cobblemon y addons relacionados.
 */
public class ProblemaAnimacionCobblemon implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";
	private String animacion = "";
	private String grupo = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global: patrón específico
		if (log.contains("Animation ") && log.contains(" not found in animation group ")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (linea.contains("Animation ") && linea.contains(" not found in animation group ")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			int inicioAnim = linea.indexOf("Animation ") + "Animation ".length();
			int finAnim = linea.indexOf(" not found in animation group ");

			if (inicioAnim > -1 && finAnim > inicioAnim) {
				animacion = linea.substring(inicioAnim, finAnim).trim();
			}

			int inicioGrupo = finAnim + " not found in animation group ".length();
			if (inicioGrupo > -1 && inicioGrupo < linea.length()) {
				grupo = linea.substring(inicioGrupo).trim();
			}

			activado = true;
		}
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

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}