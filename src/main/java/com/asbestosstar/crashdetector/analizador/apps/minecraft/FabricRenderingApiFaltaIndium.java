package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class FabricRenderingApiFaltaIndium implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String MATERIAL_FINDER_COMPLETO = "net.fabricmc.fabric.api.renderer.v1.Renderer.materialFinder()";
	private static final String MATERIAL_FINDER = "Renderer.materialFinder()";
	private static final String RENDERER_ACCESS = "RendererAccess.getRenderer()";
	private static final String IS_NULL = "is null";
	private static final String NULL_POINTER_EXCEPTION = "java.lang.NullPointerException:";

	@Override
	public String[] patronesRapidos() {
		return new String[] { MATERIAL_FINDER_COMPLETO, RENDERER_ACCESS, IS_NULL };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		// Verificación precisa en la línea principal del error
		if (lineaContieneErrorRenderingApi(linea)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneErrorRenderingApi(String linea) {
		return linea.contains(NULL_POINTER_EXCEPTION) && linea.contains(MATERIAL_FINDER)
				&& linea.contains(RENDERER_ACCESS) && linea.contains(IS_NULL);
	}

	@Override
	public VerificacionesLegacy nueva() {
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