package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el uso de OptiFine en un servidor dedicado, lo cual provoca múltiples
 * errores porque OptiFine intenta cargar clases del cliente en un entorno de
 * servidor.
 */
public class ErrorOptiFineServidor implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	// Indica si el log completo tiene señales de OptiFine.
	private boolean posibleOptiFineServidor = false;

	private static final String TEXTO_OPTIFINE_PATCHER = "optifine.Patcher.applyPatch";
	private static final String TEXTO_BASE_RESOURCE_NOT_FOUND = "Base resource not found";
	private static final String TEXTO_BASE_RESOURCE_NOT_FOUND_COMPLETO = "java.io.IOException: Base resource not found:";
	private static final String TEXTO_LAYER_OPTIFINE = "LAYER SERVICE/optifine/optifine.Patcher.applyPatch";
	private static final String TEXTO_OPTIFINE = "optifine";

	/**
	 * Método global ligero.
	 *
	 * No usa regex, no usa toLowerCase() y no usa regionMatches().
	 *
	 * En los logs reales de Forge/OptiFine, el nombre del paquete y clase suele
	 * aparecer con esta forma exacta:
	 *
	 * optifine.Patcher.applyPatch
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(TEXTO_OPTIFINE_PATCHER)) {
			this.posibleOptiFineServidor = true;
		}
	}

	/**
	 * Análisis por línea del registro.
	 *
	 * Solo se ejecuta si el método global encontró OptiFine en el log.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || !posibleOptiFineServidor || linea == null || linea.isEmpty()) {
			return;
		}

		if (linea.contains(TEXTO_BASE_RESOURCE_NOT_FOUND)) {
			this.enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorOptiFineServidor() + Verificaciones.nl_html;
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorOptiFineServidor();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500.0f;
	}

	@Override
	public String mensaje() {
		return activado ? mensaje + enlaceHtml : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorOptiFineServidor();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorOptiFineServidor())
				.construir();
	}

	@Override
	public String id() {
		return "error_optifine_servidor";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 *
	 * También evita búsqueda case-insensitive. Para este error, las cadenas reales
	 * del stack trace son suficientemente estables.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains(TEXTO_BASE_RESOURCE_NOT_FOUND_COMPLETO) && t.contains(TEXTO_LAYER_OPTIFINE)
				&& t.contains(TEXTO_OPTIFINE);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}