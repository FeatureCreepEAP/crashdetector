package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre OptiFine y Embeddium que provocan un error de
 * inyección crítica relacionado con InGameHud durante la inicialización del
 * juego.
 */
public class ConflictoOptiFineEmbeddium implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoOptiFine = false;

	public boolean analizarLineas = false;

	private static final String OPTIFINE_MINUSCULA = "optifine";
	private static final String OPTIFINE_MIXTA = "Optifine";
	private static final String OPTIFINE_CORRECTA = "OptiFine";

	private static final String CRITICAL_INJECTION = "Critical injection failure";
	private static final String REDIRECT_FANCY_GRAPHICS = "redirectFancyGraphicsVignette";
	private static final String EMBEDDIUM_MIXINS = "embeddium.mixins.json";
	private static final String IN_GAME_HUD_MIXIN = "InGameHudMixin";

	private static final Set<String> REPORTADOS = Collections.synchronizedSet(new HashSet<String>());

	public static void reiniciarGlobal() {
		REPORTADOS.clear();
	}

	@Override
	public String[] patronesRapidos() {
		return new String[] { OPTIFINE_MINUSCULA, OPTIFINE_MIXTA, OPTIFINE_CORRECTA, CRITICAL_INJECTION,
				REDIRECT_FANCY_GRAPHICS, EMBEDDIUM_MIXINS, IN_GAME_HUD_MIXIN };
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

		if (lineaContieneErrorEmbeddium(linea)) {
			analizarLineas = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	/**
	 * Método de compatibilidad — busca si OptiFine está presente en el contenido
	 * completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Modo streaming puro: puede no existir contenido_verificar
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String log = consola.contenido_verificar;

		if (contieneOptiFine(log)) {
			encontradoOptiFine = true;
		}

		if (encontradoOptiFine && lineaContieneErrorEmbeddium(log)) {
			analizarLineas = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return analizarLineas && !activado;
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
		if (activado || linea == null) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		if (!analizarLineas || !encontradoOptiFine) {
			return;
		}

		// Verificamos si la línea contiene el error de inyección crítica de Embeddium
		// con InGameHud
		if (lineaContieneErrorEmbeddium(linea)) {
			activar(consola, numero_de_linea);
		}
	}

	private boolean contieneOptiFine(String texto) {
		return texto.contains(OPTIFINE_MINUSCULA) || texto.contains(OPTIFINE_MIXTA)
				|| texto.contains(OPTIFINE_CORRECTA);
	}

	private boolean lineaContieneErrorEmbeddium(String linea) {
		return linea.contains(CRITICAL_INJECTION) && linea.contains(REDIRECT_FANCY_GRAPHICS)
				&& linea.contains(EMBEDDIUM_MIXINS) && linea.contains(IN_GAME_HUD_MIXIN);
	}

	private void activar(Consola consola, int numero_de_linea) {
		if (!REPORTADOS.add(id())) {
			return;
		}

		// Enlazar a la línea del error en el lector
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		// Mensaje de error en HTML con referencia al conflicto entre OptiFine y
		// Embeddium
		mensaje = MonitorDePID.idioma.errorConflictoOptiFineEmbeddium() + Verificaciones.nl_html;
		activado = true;
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

		return lineaContieneErrorEmbeddium(trazo.trace) && encontradoOptiFine;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}