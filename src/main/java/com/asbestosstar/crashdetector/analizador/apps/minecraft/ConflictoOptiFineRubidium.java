package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre OptiFine y Rubidium que provocan un error de
 * inyección crítica relacionado con WorldRenderer durante la inicialización del
 * juego.
 */
public class ConflictoOptiFineRubidium implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoOptiFine = false;
	public boolean analizarLineas = false;

	private static final String OPTIFINE_MINUSCULA = "optifine";
	private static final String OPTIFINE_MIXTA = "Optifine";
	private static final String OPTIFINE_CORRECTA = "OptiFine";

	private static final String CRITICAL_INJECTION = "Critical injection failure";
	private static final String REDIRECT_FANCY_WEATHER = "redirectGetFancyWeather";
	private static final String RUBIDIUM_MIXINS = "rubidium.mixins.json";
	private static final String WORLD_RENDERER_MIXIN = "WorldRendererMixin";

	@Override
	public String[] patronesRapidos() {
		return new String[] { OPTIFINE_MINUSCULA, OPTIFINE_MIXTA, OPTIFINE_CORRECTA, CRITICAL_INJECTION,
				REDIRECT_FANCY_WEATHER, RUBIDIUM_MIXINS, WORLD_RENDERER_MIXIN };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		if (contieneOptiFine(linea)) {
			encontradoOptiFine = true;
		}

		if (lineaContieneErrorRubidium(linea)) {
			analizarLineas = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	/**
	 * Método de compatibilidad — no hace nada en modo rápido/streaming.
	 */
	@Override
	public void verificar(Consola consola) {
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return analizarLineas && !activado;
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error de inyección crítica de Rubidium
	 * con WorldRenderer, que ocurre cuando OptiFine y Rubidium no son compatibles.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		if (!analizarLineas || !encontradoOptiFine) {
			return;
		}

		// Verificamos si la línea contiene el error de inyección crítica de Rubidium
		// con WorldRenderer
		if (lineaContieneErrorRubidium(linea)) {
			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al conflicto entre OptiFine y
			// Rubidium
			mensaje = MonitorDePID.idioma.errorConflictoOptiFineRubidium() + Verificaciones.nl_html;
			activado = true;
		}
	}

	private boolean contieneOptiFine(String texto) {
		return texto.contains(OPTIFINE_MINUSCULA) || texto.contains(OPTIFINE_MIXTA)
				|| texto.contains(OPTIFINE_CORRECTA);
	}

	private boolean lineaContieneErrorRubidium(String linea) {
		return linea.contains(CRITICAL_INJECTION) && linea.contains(REDIRECT_FANCY_WEATHER)
				&& linea.contains(RUBIDIUM_MIXINS) && linea.contains(WORLD_RENDERER_MIXIN);
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoOptiFineRubidium();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1200.0f; // Alta prioridad: rompe la inicialización del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeConflictoOptiFineRubidium();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoConflictoOptiFineRubidium())
				.construir();
	}

	@Override
	public String id() {
		return "conflicto_optifine_rubidium";
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

		return lineaContieneErrorRubidium(trazo.trace) && encontradoOptiFine;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}