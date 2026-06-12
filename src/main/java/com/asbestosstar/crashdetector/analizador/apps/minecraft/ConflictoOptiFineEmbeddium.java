package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre OptiFine y Embeddium que provocan un error de
 * inyección crítica relacionado con InGameHud durante la inicialización del
 * juego.
 */
public class ConflictoOptiFineEmbeddium implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoOptiFine = false;

	public boolean analizarLineas = false;

	/**
	 * Método de compatibilidad — busca si OptiFine está presente en el contenido
	 * completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		if (log.contains("optifine") || log.contains("Optifine")) {
			encontradoOptiFine = true;
		}

		if (log.contains("Critical injection failure") && log.contains("redirectFancyGraphicsVignette")
				&& log.contains("embeddium.mixins.json") && log.contains("InGameHudMixin") && encontradoOptiFine) {
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
	 * Se busca el patrón característico del error de inyección crítica de Embeddium
	 * con InGameHud, que ocurre cuando OptiFine y Embeddium no son compatibles.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Verificamos si la línea contiene el error de inyección crítica de Embeddium
		// con InGameHud
		if (linea.contains("Critical injection failure") && linea.contains("redirectFancyGraphicsVignette")
				&& linea.contains("embeddium.mixins.json") && linea.contains("InGameHudMixin") && encontradoOptiFine) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al conflicto entre OptiFine y
			// Embeddium
			mensaje = MonitorDePID.idioma.errorConflictoOptiFineEmbeddium() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoOptiFineEmbeddium();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1100.0f; // Alta prioridad: rompe la inicialización del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeConflictoOptiFineEmbeddium();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoConflictoOptiFineEmbeddium())
				.construir();
	}

	@Override
	public String id() {
		return "conflicto_optifine_embeddium";
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

		return t.contains("Critical injection failure") && t.contains("redirectFancyGraphicsVignette")
				&& t.contains("embeddium.mixins.json") && t.contains("InGameHudMixin") && encontradoOptiFine;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}