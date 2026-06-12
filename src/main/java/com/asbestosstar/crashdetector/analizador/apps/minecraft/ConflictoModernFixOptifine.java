package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta incompatibilidad entre ModernFix y OptiFine. ModernFix emite un
 * mensaje explícito cuando detecta OptiFine.
 */
public class ConflictoModernFixOptifine implements Verificaciones {

	private boolean activado = false;
	private String enlaceHtml = "";
	public boolean analizarLineas = false;

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		if (log.contains("OptiFine detected. Use of ModernFix with OptiFine is not supported")) {
			analizarLineas = true;

		}

	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!analizarLineas)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null) {
			return;
		}

		// Buscar la línea explícita emitida por ModernFix
		if (linea.contains("OptiFine detected. Use of ModernFix with OptiFine is not supported")) {
			this.activado = true;
			this.enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoModernFixOptifine();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 940.0f;
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return MonitorDePID.idioma.conflicto_modernfix_optifine_html() + (enlaceHtml.isEmpty() ? "" : " " + enlaceHtml);
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_conflicto_modernfix_optifine();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_eliminar_optifine_o_modernfix());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_usar_alternativa_modernfix());
		return builder.construir();
	}

	@Override
	public String id() {
		return "conflicto_modernfix_optifine";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}