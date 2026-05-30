package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre OptiFine y Entity Model Features (EMF) que provocan
 * un error de inyección crítica durante la inicialización del juego.
 */
public class ConflictoOptiFineEMF implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoOptiFine = false;

	/**
	 * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
	 */
	@Override
	public void verificar(Consola consola) {
		// Verificamos si OptiFine está presente en el contenido del registro
		if (consola.contenido_verificar != null) {
			encontradoOptiFine = consola.contenido_verificar.toLowerCase().contains("optifine");
		}
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
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Verificamos si la línea contiene el error de inyección crítica de EMF
		if (linea.contains("org.spongepowered.asm.mixin.injection.throwables.InjectionError")
				&& linea.contains("Critical injection failure") && linea.contains("entity_model_features.mixins.json")
				&& linea.contains("MixinBlockEntityWithoutLevelRenderer") && linea.contains("emf$setRenderFactory")) {

			// Verificamos también que OptiFine esté presente en el registro
			if (encontradoOptiFine) {
				// Enlazar a la línea del error en el lector
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

				// Mensaje de error en HTML con referencia al conflicto entre OptiFine y EMF
				mensaje = MonitorDePID.idioma.errorConflictoOptiFineEMF() + Verificaciones.nl_html;
				activado = true;
			}
		}
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

		String t = trazo.trace;

		return t.contains("org.spongepowered.asm.mixin.injection.throwables.InjectionError")
				&& t.contains("Critical injection failure") && t.contains("entity_model_features.mixins.json")
				&& t.contains("MixinBlockEntityWithoutLevelRenderer") && t.contains("emf$setRenderFactory")
				&& encontradoOptiFine;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}