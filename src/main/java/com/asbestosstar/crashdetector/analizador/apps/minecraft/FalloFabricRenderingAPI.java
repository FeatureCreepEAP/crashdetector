package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
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
public class FalloFabricRenderingAPI implements VerificacionRapida {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";

	private static final String ILLEGAL_STATE_EXCEPTION = "IllegalStateException";
	private static final String FABRIC_RENDERING_API_NO_DISPONIBLE = "The Fabric Rendering API is not available";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ILLEGAL_STATE_EXCEPTION, FABRIC_RENDERING_API_NO_DISPONIBLE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneFalloFabricRenderingAPI(evento.linea)) {
			analizarLineas = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String log = consola.contenido_verificar;

		// Pre-check global para activar el análisis línea por línea
		if (log.contains(ILLEGAL_STATE_EXCEPTION) && log.contains(FABRIC_RENDERING_API_NO_DISPONIBLE)) {
			analizarLineas = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return analizarLineas && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!analizarLineas || linea == null || activado) {
			return;
		}

		if (lineaContieneFalloFabricRenderingAPI(linea)) {
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	private boolean lineaContieneFalloFabricRenderingAPI(String linea) {
		return linea.contains(ILLEGAL_STATE_EXCEPTION) && linea.contains(FABRIC_RENDERING_API_NO_DISPONIBLE);
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