package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores causados por el mod En Garde! (mod ID: en_garde).
 */
public class ErrorEnGarde implements Verificaciones {

	private boolean activado = false;

	private String enlaceHtml = "";

	private static final String HANDLER = "handler$";
	private static final String EN_GARDE = "$en_garde$";

	@Override
	public String[] patronesRapidos() {
		return new String[] { HANDLER, EN_GARDE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscar indicios claros de En Garde! en mixins
		if (lineaContieneEnGarde(linea)) {
			this.activado = true;
			this.enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		}
	}

	private boolean lineaContieneEnGarde(String linea) {
		return linea.contains(HANDLER) && linea.contains(EN_GARDE);
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
		if (!activado)
			return "";
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
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}