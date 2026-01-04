package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores causados por el mod En Garde! (mod ID: en_garde).
 */
public class ErrorEnGarde implements Verificaciones {

	private boolean activado = false;
	private String enlaceHtml = "";

	@Override
	public void verificar(Consola consola) {
		// No se usa
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null) {
			return;
		}

		// Buscar indicios claros de En Garde! en mixins
		if (linea.contains("handler$") && linea.contains("$en_garde$")) {
			this.activado = true;
			this.enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorEnGarde();
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
		if (!activado) return "";
		return MonitorDePID.idioma.error_en_garde_html() + (enlaceHtml.isEmpty() ? "" : " " + enlaceHtml);
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_en_garde();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_actualizar_en_garde());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_eliminar_conflicto_mod_combate());
		return builder.construir();
	}

	@Override
	public String id() {
		return "error_en_garde";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}
}