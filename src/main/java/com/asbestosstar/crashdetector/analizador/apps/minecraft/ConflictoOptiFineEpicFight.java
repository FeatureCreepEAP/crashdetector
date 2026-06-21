package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
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

	private static final String OPTIFINE_MINUSCULA = "optifine";
	private static final String OPTIFINE_MIXTA = "Optifine";
	private static final String OPTIFINE_CORRECTA = "OptiFine";

	private static final String CRITICAL_INJECTION = "Critical injection failure";
	private static final String LEVEL_RENDERER = "net/minecraft/client/renderer/LevelRenderer";
	private static final String EPICFIGHT_MIXINS = "mixins.epicfight.json";
	private static final String MIXIN_LEVEL_RENDERER = "client.MixinLevelRenderer";
	private static final String EPICFIGHT_RENDER_LEVEL = "epicfight$renderLevel";

	@Override
	public String[] patronesRapidos() {
		return new String[] { OPTIFINE_MINUSCULA, OPTIFINE_MIXTA, OPTIFINE_CORRECTA, CRITICAL_INJECTION, LEVEL_RENDERER,
				EPICFIGHT_MIXINS, MIXIN_LEVEL_RENDERER, EPICFIGHT_RENDER_LEVEL };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
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
		if (activado || linea == null) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Verificamos si la línea contiene el error de inyección crítica de Epic Fight
		// con LevelRenderer
		if (lineaContieneErrorEpicFight(linea)) {
			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al conflicto entre OptiFine y Epic
			// Fight
			mensaje = MonitorDePID.idioma.errorConflictoOptiFineEpicFight() + VerificacionesLegacy.nl_html;
			activado = true;
		}
	}

	private boolean contieneOptiFine(String texto) {
		return texto.contains(OPTIFINE_MINUSCULA) || texto.contains(OPTIFINE_MIXTA)
				|| texto.contains(OPTIFINE_CORRECTA);
	}

	private boolean lineaContieneErrorEpicFight(String linea) {
		return linea.contains(CRITICAL_INJECTION) && linea.contains(LEVEL_RENDERER) && linea.contains(EPICFIGHT_MIXINS)
				&& linea.contains(MIXIN_LEVEL_RENDERER) && linea.contains(EPICFIGHT_RENDER_LEVEL);
	}

	@Override
	public VerificacionesLegacy nueva() {
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

		return lineaContieneErrorEpicFight(trazo.trace);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}