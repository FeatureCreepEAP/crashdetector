package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de compatibilidad entre Simple Radio y Lexiconfig.
 *
 * Ejemplo de StackTrace: at
 * com.codinglitch.simpleradio.SimpleRadioLibrary.shelveLexicons at
 * com.codinglitch.lexiconfig.LexiconfigApi.shelveLexicon
 *
 * Causado generalmente por tener una versión de Simple Radio incompatible con
 * la versión instalada de Lexiconfig.
 */
public class ErrorSimpleRadioLexiconfig implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global: Verificamos que ambas clases aparezcan en el log
		if (log.contains("com.codinglitch.simpleradio.SimpleRadioLibrary")
				&& log.contains("com.codinglitch.lexiconfig.LexiconfigApi")) {
			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		// Buscamos la línea específica del crash en SimpleRadioLibrary
		if (linea.contains("com.codinglitch.simpleradio.SimpleRadioLibrary.shelveLexicons")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorSimpleRadioLexiconfig();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorSimpleRadioLexiconfig() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorSimpleRadioLexiconfig();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "error_simple_radio_lexiconfig";
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
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}