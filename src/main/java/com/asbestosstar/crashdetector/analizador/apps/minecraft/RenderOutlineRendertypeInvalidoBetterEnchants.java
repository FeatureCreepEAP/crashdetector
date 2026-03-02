package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class RenderOutlineRendertypeInvalidoBetterEnchants implements Verificaciones {

	private boolean posibleError = false;
	private boolean modEnchantOutlineDetectado = false;
	private boolean activado = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		// Error base
		if (consola.contenido_verificar.contains("Can't render an outline for this rendertype")) {
			posibleError = true;
		}

		// Buscar mod sospechoso
		if (consola.contenido_verificar.contains("enchant-outline")
				|| consola.contenido_verificar.contains("better-enchants")
				|| consola.contenido_verificar.contains("enchant_outline")) {

			modEnchantOutlineDetectado = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int num) {

		if (!posibleError) {
			return;
		}

		if (linea.contains("Can't render an outline for this rendertype")) {

			if (!activado) {
				this.enlace = consola.agregarErrorALectador(num, this);
				this.activado = true;
			}
		}
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
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "render_outline_rendertype_invalido";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}