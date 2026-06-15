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
 * Detecta conflictos entre OptiFine y Entity Model Features (EMF) que provocan
 * un error de inyección crítica durante la inicialización del juego.
 */
public class ConflictoOptiFineEMF implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoOptiFine = false;
	public boolean analizarLineas = false;

	private static final String OPTIFINE_MINUSCULA = "optifine";
	private static final String OPTIFINE_MIXTA = "Optifine";
	private static final String OPTIFINE_CORRECTA = "OptiFine";

	private static final String INJECTION_ERROR = "org.spongepowered.asm.mixin.injection.throwables.InjectionError";
	private static final String CRITICAL_INJECTION = "Critical injection failure";
	private static final String EMF_MIXINS = "entity_model_features.mixins.json";
	private static final String MIXIN_RENDERER = "MixinBlockEntityWithoutLevelRenderer";
	private static final String EMF_RENDER_FACTORY = "emf$setRenderFactory";

	private static final Set<String> REPORTADOS = Collections.synchronizedSet(new HashSet<String>());

	public static void reiniciarGlobal() {
		REPORTADOS.clear();
	}

	@Override
	public String[] patronesRapidos() {
		return new String[] { OPTIFINE_MINUSCULA, OPTIFINE_MIXTA, OPTIFINE_CORRECTA, INJECTION_ERROR,
				CRITICAL_INJECTION, EMF_MIXINS, MIXIN_RENDERER, EMF_RENDER_FACTORY };
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

		if (lineaContieneErrorEMF(linea)) {
			analizarLineas = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	/**
	 * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
	 */
	@Override
	public void verificar(Consola consola) {
		// Modo streaming puro: puede no existir contenido_verificar
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		// Verificamos si OptiFine está presente en el contenido del registro
		String log = consola.contenido_verificar;

		if (contieneOptiFine(log)) {
			encontradoOptiFine = true;
		}

		if (encontradoOptiFine && lineaContieneErrorEMF(log)) {
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
	 * Se busca el patrón característico del error de inyección crítica de EMF, que
	 * ocurre cuando OptiFine y EMF no son compatibles.
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

		// Verificamos si la línea contiene el error de inyección crítica de EMF
		if (lineaContieneErrorEMF(linea)) {
			activar(consola, numero_de_linea);
		}
	}

	private boolean contieneOptiFine(String texto) {
		return texto.contains(OPTIFINE_MINUSCULA) || texto.contains(OPTIFINE_MIXTA)
				|| texto.contains(OPTIFINE_CORRECTA);
	}

	private boolean lineaContieneErrorEMF(String linea) {
		return linea.contains(INJECTION_ERROR) && linea.contains(CRITICAL_INJECTION) && linea.contains(EMF_MIXINS)
				&& linea.contains(MIXIN_RENDERER) && linea.contains(EMF_RENDER_FACTORY);
	}

	private void activar(Consola consola, int numero_de_linea) {
		if (!REPORTADOS.add(id())) {
			return;
		}

		// Enlazar a la línea del error en el lector
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		// Mensaje de error en HTML con referencia al conflicto entre OptiFine y EMF
		mensaje = MonitorDePID.idioma.errorConflictoOptiFineEMF() + Verificaciones.nl_html;
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoOptiFineEMF();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Alta prioridad: rompe la inicialización del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeConflictoOptiFineEMF();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoConflictoOptiFineEMF())
				.construir();
	}

	@Override
	public String id() {
		return "conflicto_optifine_emf";
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

		return lineaContieneErrorEMF(trazo.trace) && encontradoOptiFine;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}