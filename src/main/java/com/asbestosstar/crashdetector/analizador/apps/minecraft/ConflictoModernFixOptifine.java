package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta incompatibilidad entre ModernFix y OptiFine. ModernFix emite un
 * mensaje explícito cuando detecta OptiFine.
 */
public class ConflictoModernFixOptifine implements VerificacionRapida {

	private boolean activado = false;
	private String enlaceHtml = "";
	public boolean analizarLineas = false;

	private static final String MENSAJE_MODERNFIX_OPTIFINE = "OptiFine detected. Use of ModernFix with OptiFine is not supported";

	private static final Set<String> REPORTADOS = Collections.synchronizedSet(new HashSet<String>());

	public static void reiniciarGlobal() {
		REPORTADOS.clear();
	}

	@Override
	public String[] patronesRapidos() {
		return new String[] { MENSAJE_MODERNFIX_OPTIFINE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		analizarLineas = true;
		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		// Modo streaming puro: puede no existir contenido_verificar
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String log = consola.contenido_verificar;

		if (log.contains(MENSAJE_MODERNFIX_OPTIFINE)) {
			analizarLineas = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return analizarLineas && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null) {
			return;
		}

		// Buscar la línea explícita emitida por ModernFix
		if (linea.contains(MENSAJE_MODERNFIX_OPTIFINE)) {
			activar(consola, numero_de_linea);
		}
	}

	private void activar(Consola consola, int numero_de_linea) {
		if (!REPORTADOS.add(id())) {
			return;
		}

		this.activado = true;
		this.enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
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