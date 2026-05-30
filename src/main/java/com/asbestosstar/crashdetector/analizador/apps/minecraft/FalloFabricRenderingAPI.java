package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores causados por la falta de la Fabric Rendering API.
 *
 * Ejemplo: Caused by: java.lang.IllegalStateException: The Fabric Rendering API
 * is not available. If you have Sodium, install Indium!
 *
 * Causado generalmente por: - Porting Lib u otros mods que requieren la API de
 * renderizado de Fabric. - Tener Sodium instalado sin el soporte necesario
 * (Indium o versión moderna de Sodium).
 *
 * Solución moderna: - En versiones nuevas del juego, Indium está obsoleto. Se
 * debe actualizar Sodium a 0.6.0+ (que incluye el soporte) y tener Fabric API.
 */
public class FalloFabricRenderingAPI implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global para activar el análisis línea por línea
		if (log.contains("IllegalStateException") && log.contains("The Fabric Rendering API is not available")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (linea.contains("IllegalStateException") && linea.contains("The Fabric Rendering API is not available")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new FalloFabricRenderingAPI();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1250.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeFalloFabricRenderingAPI() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreFalloFabricRenderingAPI();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "fallo_fabric_rendering_api";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}