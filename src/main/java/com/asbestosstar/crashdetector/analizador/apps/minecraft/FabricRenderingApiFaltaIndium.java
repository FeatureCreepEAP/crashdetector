package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class FabricRenderingApiFaltaIndium implements Verificaciones {

	// Indica si el log contiene indicios globales del error
	private boolean posibleFaltaRenderingApi = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global ligera: este error aparece cuando RendererAccess no puede
		// obtener un renderer válido. Suele estar relacionado con Fabric Rendering API,
		// Sodium e Indium.
		if (consola.contenido_verificar.contains("net.fabricmc.fabric.api.renderer.v1.Renderer.materialFinder()")
				&& consola.contenido_verificar.contains("RendererAccess.getRenderer()")
				&& consola.contenido_verificar.contains("is null")) {
			posibleFaltaRenderingApi = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales
		if (!posibleFaltaRenderingApi) {
			return;
		}

		// Verificación precisa en la línea principal del error
		if (linea.contains("java.lang.NullPointerException:") && linea.contains("Renderer.materialFinder()")
				&& linea.contains("RendererAccess.getRenderer()") && linea.contains("is null")) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new FabricRenderingApiFaltaIndium();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeFabricRenderingApiFaltaIndium() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreFabricRenderingApiFaltaIndium();
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
		return "fabric_rendering_api_falta_indium";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}