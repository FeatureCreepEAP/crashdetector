package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta que Immersive Tooltips necesita Immersive Messages como dependencia
 * pero esta no está instalada, causando un NoClassDefFoundError. Patrón
 * moderno: global barato + per-línea.
 */
public class ErrorImmersiveTooltipsSinDependencia implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean posibleError = false;

	private static final String TEXTO_IMMERSIVE_TOOLTIPS_1 = "immersivetips";
	private static final String TEXTO_IMMERSIVE_TOOLTIPS_2 = "immersive tooltips";
	private static final String TEXTO_ERROR_CLASE = "java.lang.NoClassDefFoundError";
	private static final String TEXTO_CLASE_FALTANTE = "toni/immersivemessages/renderers/ITooltipRenderer";

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty())
			return;

		String contenido = consola.contenido_verificar;

		// Activar flag solo si:
		// 1) Aparece el error de clase o la clase faltante
		// 2) Y además aparece Immersive Tooltips
		boolean contieneClase = contenido.contains(TEXTO_ERROR_CLASE) || contenido.contains(TEXTO_CLASE_FALTANTE);
		boolean contieneMod = contenido.contains(TEXTO_IMMERSIVE_TOOLTIPS_1)
				|| contenido.contains(TEXTO_IMMERSIVE_TOOLTIPS_2);

		posibleError = contieneClase && contieneMod;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleError || linea == null || linea.isEmpty() || activado)
			return;

		if ((linea.contains(TEXTO_ERROR_CLASE) && linea.contains(TEXTO_CLASE_FALTANTE))) {

			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
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
		return 1400.0f;
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

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null)
			return false;

		String t = trazo.trace;
		return (t.contains(TEXTO_ERROR_CLASE) || t.contains(TEXTO_CLASE_FALTANTE))
				&& (t.contains(TEXTO_IMMERSIVE_TOOLTIPS_1) || t.contains(TEXTO_IMMERSIVE_TOOLTIPS_2));
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}