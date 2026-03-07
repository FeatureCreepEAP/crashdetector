package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores donde Minecraft no puede localizar el directorio de assets.
 *
 * Ejemplo: Exception in thread "main"
 * joptsimple.OptionArgumentConversionException: Cannot parse argument
 * '...assets' of option assetsDir
 *
 * Este problema suele ocurrir cuando el launcher no instala correctamente los
 * assets del juego.
 */
public class ProblemaAssetsDirFaltante implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global para rendimiento
		if (log.contains("OptionArgumentConversionException") && log.contains("assetsDir")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null)
			return;

		if (linea.contains("OptionArgumentConversionException") && linea.contains("assetsDir")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaAssetsDirFaltante();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400f;
	}

	@Override
	public String mensaje() {

		if (!activado)
			return "";

		return MonitorDePID.idioma.problemaAssetsDirFaltante() + enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaAssetsDirFaltante();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "problema_assets_dir_faltante";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {

		if (trazo == null || trazo.trace == null)
			return false;

		String t = trazo.trace;

		return t.contains("OptionArgumentConversionException") && t.contains("assetsDir");
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