package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta conflicto entre OptiFine e Iris/Oculus basado en errores de inyección
 * de mixins. Requiere que aparezca "mixins.iris.json" o "mixins.oculus.json" y
 * "OptiFine" en el log.
 */
public class ConflictoIrisOptifine implements Verificaciones {

	private boolean activado = false;
	private String enlaceHtml = "";
	private boolean encontroOptifine = false;

	@Override
	public void verificar(Consola consola) {
		this.activado = false;
		this.enlaceHtml = "";
		this.encontroOptifine = false;

		// Buscar "optifine" en todo el log (case-insensitive)
		String contenido = consola.contenido_verificar.toLowerCase();
		if (contenido.contains("optifine")) {
			this.encontroOptifine = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || !encontroOptifine || linea == null) {
			return;
		}

		String l = linea.toLowerCase();

		// Verificar que la línea contenga:
		// - El archivo de mixin de Iris u Oculus
		// - El error de inyección
		// - La clase MixinLevelRenderer
		boolean tieneIrisOculusJson = l.contains("mixins.iris.json") || l.contains("mixins.iris.forge.json")
				|| l.contains("mixins.iris.fabric.json") || l.contains("mixins.oculus.json");
		boolean tieneInjectionError = l.contains("injectionerror");
		boolean tieneMixinLevelRenderer = l.contains("mixinlevelrenderer");
		boolean tieneFailedCheck = l.contains("failed injection check");

		if (tieneIrisOculusJson && tieneInjectionError && tieneMixinLevelRenderer && tieneFailedCheck) {
			this.activado = true;
			this.enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoIrisOptifine();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f;
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return MonitorDePID.idioma.conflicto_iris_optifine_html() + (enlaceHtml.isEmpty() ? "" : " " + enlaceHtml);
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_conflicto_iris_optifine();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_eliminar_optifine());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_usar_iris_sin_optifine());
		return builder.construir();
	}

	@Override
	public String id() {
		return "conflicto_iris_optifine";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}