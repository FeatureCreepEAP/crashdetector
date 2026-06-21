package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
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

	private static final String TEXTO_IMMERSIVE_TOOLTIPS_1 = "immersivetips";
	private static final String TEXTO_IMMERSIVE_TOOLTIPS_2 = "immersive tooltips";
	private static final String TEXTO_ERROR_CLASE = "java.lang.NoClassDefFoundError";
	private static final String TEXTO_CLASE_FALTANTE = "toni/immersivemessages/renderers/ITooltipRenderer";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_IMMERSIVE_TOOLTIPS_1, TEXTO_IMMERSIVE_TOOLTIPS_2, TEXTO_ERROR_CLASE,
				TEXTO_CLASE_FALTANTE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea.contains(TEXTO_ERROR_CLASE) && linea.contains(TEXTO_CLASE_FALTANTE)) {

			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			mensaje = MonitorDePID.idioma.errorImmersiveTooltipsSinDependencia() + Verificaciones.nl_html;
			activado = true;
		}
	}

	private boolean lineaContieneClaseFaltante(String linea) {
		return linea.contains(TEXTO_ERROR_CLASE) || linea.contains(TEXTO_CLASE_FALTANTE);
	}

	private boolean lineaContieneMod(String linea) {
		return linea.contains(TEXTO_IMMERSIVE_TOOLTIPS_1) || linea.contains(TEXTO_IMMERSIVE_TOOLTIPS_2);
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
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_CLASE_FALTANTE };
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}