package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detecta errores IncompatibleClassChangeError causados por intentar heredar de
 * una clase marcada como 'final' en versiones recientes de Minecraft o mods.
 * 
 * Ejemplo: java.lang.IncompatibleClassChangeError: class A cannot inherit from
 * final class B
 */
public class ErrorClaseFinalExtendida implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private static final Pattern PATRON_CLASE_FINAL = Pattern.compile(
			"java\\.lang\\.IncompatibleClassChangeError: class ([^ ]+) cannot inherit from final class ([^\\s]+)");

	@Override
	public void verificar(Consola consola) {
		// Este método no se usa; el análisis se hace por línea
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (this.activado) {
			return;
		}

		Matcher m = PATRON_CLASE_FINAL.matcher(linea);
		if (m.find()) {
			String claseHija = m.group(1);
			String clasePadreFinal = m.group(2);

			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorClaseFinalExtendida(claseHija, clasePadreFinal) + enlaceHtml;
			this.activado = true;
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return PATRON_CLASE_FINAL.matcher(trazo.trace).find();
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorClaseFinalExtendida();
	}

	@Override
	public boolean activado() {
		return this.activado;
	}

	@Override
	public String mensaje() {
		return this.mensaje;
	}

	@Override
	public float prioridad() {
		return 820.0f; // Alta: impide la carga del mod
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionErrorClaseFinalExtendida())
				.construir();
	}

	@Override
	public String id() {
		return "clase_final_extendida";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorClaseFinalExtendida();
	}
}