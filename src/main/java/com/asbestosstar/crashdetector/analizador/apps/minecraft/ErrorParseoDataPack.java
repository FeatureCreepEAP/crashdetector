package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores donde un datapack o resourcepack falla al parsearse y provoca
 * errores de carga del registry.
 *
 * Requiere que existan: - "Failed to parse" - "Registry loading errors:"
 */
public class ErrorParseoDataPack implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private boolean vioParse = false;
	private boolean vioRegistry = false;

	private String enlace = "";
	private String archivo = "";
	private String pack = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global: deben existir ambas cadenas
		if (log.contains("Failed to parse") && log.contains("Registry loading errors:")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (linea.contains("Failed to parse")) {

			vioParse = true;
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			// Extraer archivo y pack si es posible
			int inicioArchivo = linea.indexOf("Failed to parse") + "Failed to parse".length();
			int desdePack = linea.indexOf(" from pack ");

			if (desdePack > -1) {
				archivo = linea.substring(inicioArchivo, desdePack).trim();
				pack = linea.substring(desdePack + " from pack ".length()).trim();
			}
		}

		if (linea.contains("Registry loading errors:")) {
			vioRegistry = true;
		}

		if (vioParse && vioRegistry) {
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorParseoDataPack();
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
		return MonitorDePID.idioma.mensajeErrorParseoDataPack(archivo, pack) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorParseoDataPack();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "error_parseo_datapack";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
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