package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta que Immersive Tooltips necesita Immersive Messages como dependencia
 * pero esta no está instalada, causando un NoClassDefFoundError.
 */
public class ErrorImmersiveTooltipsSinDependencia implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoImmersiveTooltips = false;

	/**
	 * Método de compatibilidad — busca si Immersive Tooltips está presente en el
	 * contenido completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Verificamos si Immersive Tooltips está presente en el contenido del registro
		if (consola.contenido_verificar != null) {
			encontradoImmersiveTooltips = consola.contenido_verificar.toLowerCase().contains("immersivetips")
					|| consola.contenido_verificar.toLowerCase().contains("immersive tooltips");
		}
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde Immersive Tooltips falla
	 * porque necesita Immersive Messages como dependencia pero no está instalada.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el error de clase no encontrada de Immersive
		// Messages
		if (linea.contains("java.lang.NoClassDefFoundError")
				&& linea.contains("toni/immersivemessages/renderers/ITooltipRenderer") && encontradoImmersiveTooltips) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de dependencia
			mensaje = MonitorDePID.idioma.errorImmersiveTooltipsSinDependencia() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorImmersiveTooltipsSinDependencia();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400.0f; // Alta prioridad: rompe la carga del mod
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorImmersiveTooltipsSinDependencia();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.pasoErrorImmersiveTooltipsSinDependencia()).construir();
	}

	@Override
	public String id() {
		return "error_immersive_tooltips_sin_dependencia";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de dependencia
	 * faltante de Immersive Tooltips.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("java.lang.NoClassDefFoundError")
				&& t.contains("toni/immersivemessages/renderers/ITooltipRenderer")
				&& (t.toLowerCase().contains("immersivetips") || t.toLowerCase().contains("immersive tooltips"));
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}