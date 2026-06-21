package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class RenderOutlineRendertypeInvalidoBetterEnchants implements Verificaciones {

	private boolean modEnchantOutlineDetectado = false;
	private boolean activado = false;
	private String enlace = "";

	private static final String TEXTO_ERROR = "Can't render an outline for this rendertype";
	private static final String MOD_ENCHANT_OUTLINE = "enchant-outline";
	private static final String MOD_BETTER_ENCHANTS = "better-enchants";
	private static final String MOD_ENCHANT_OUTLINE_ALT = "enchant_outline";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR, MOD_ENCHANT_OUTLINE, MOD_BETTER_ENCHANTS, MOD_ENCHANT_OUTLINE_ALT };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (contieneModSospechoso(evento.linea)) {
			modEnchantOutlineDetectado = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		if (linea.contains(TEXTO_ERROR)) {

			if (consola != null) {
				this.enlace = consola.agregarErrorALectador(num, this);
			}

			this.activado = true;
		}
	}

	private boolean contieneModSospechoso(String linea) {
		return linea.contains(MOD_ENCHANT_OUTLINE) || linea.contains(MOD_BETTER_ENCHANTS)
				|| linea.contains(MOD_ENCHANT_OUTLINE_ALT);
	}

	@Override
	public Verificaciones nueva() {
		return new RenderOutlineRendertypeInvalidoBetterEnchants();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return modEnchantOutlineDetectado ? 1700 : 1550;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeRenderOutlineRendertypeInvalido(modEnchantOutlineDetectado) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreRenderOutlineRendertypeInvalido();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "render_outline_rendertype_invalido";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}