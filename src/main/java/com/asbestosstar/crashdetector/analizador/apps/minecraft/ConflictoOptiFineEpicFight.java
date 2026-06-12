package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre OptiFine y Epic Fight que provocan un error de
 * inyección crítica relacionado con LevelRenderer durante la inicialización del
 * juego.
 */
public class ConflictoOptiFineEpicFight implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoOptiFine = false;
	public boolean analizarLineas = false;

	/**
	 * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
	 */
	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		if (log.contains("optifine") || log.contains("Optifine")) {
			encontradoOptiFine = true;
		}

		if (log.contains("Critical injection failure") && log.contains("net/minecraft/client/renderer/LevelRenderer")
				&& log.contains("mixins.epicfight.json") && log.contains("client.MixinLevelRenderer")
				&& log.contains("epicfight$renderLevel")) {
			analizarLineas = true;
		}

	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!analizarLineas)
			return false;

		return true;
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error de inyección crítica de Epic
	 * Fight con LevelRenderer, que ocurre cuando OptiFine y Epic Fight no son
	 * compatibles.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Verificamos si la línea contiene el error de inyección crítica de Epic Fight
		// con LevelRenderer
		if (linea.contains("Critical injection failure")
				&& linea.contains("net/minecraft/client/renderer/LevelRenderer")
				&& linea.contains("mixins.epicfight.json") && linea.contains("client.MixinLevelRenderer")
				&& linea.contains("epicfight$renderLevel")) {

			// Verificamos también que OptiFine esté presente en el registro
			if (encontradoOptiFine) {
				// Enlazar a la línea del error en el lector
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

				// Mensaje de error en HTML con referencia al conflicto entre OptiFine y Epic
				// Fight
				mensaje = MonitorDePID.idioma.errorConflictoOptiFineEpicFight() + Verificaciones.nl_html;
				activado = true;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoOptiFineEpicFight();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f; // Alta prioridad: rompe la inicialización del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeConflictoOptiFineEpicFight();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoConflictoOptiFineEpicFight())
				.construir();
	}

	@Override
	public String id() {
		return "conflicto_optifine_epicfight";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Conservador: solo devuelve true si el trazo contiene las cadenas clave
	 * exactas del error y se ha detectado OptiFine.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("Critical injection failure") && t.contains("net/minecraft/client/renderer/LevelRenderer")
				&& t.contains("mixins.epicfight.json") && t.contains("client.MixinLevelRenderer")
				&& t.contains("epicfight$renderLevel") && encontradoOptiFine;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}