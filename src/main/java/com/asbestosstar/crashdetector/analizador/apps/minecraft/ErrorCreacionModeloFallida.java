package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de modelos faltantes: "No model for layer
 * <modid>:<nombre_modelo>#<capa>".
 *
 * Este error suele indicar que un mod, resource pack o datapack tiene recursos
 * corruptos, faltantes, o incompatibles con la versión actual de Minecraft.
 */
public class ErrorCreacionModeloFallida implements VerificacionRapida {

	private boolean posibleErrorCreacionModelo = false;

	private final List<String> mensajes = new ArrayList<>();
	private final Set<String> modelosReportados = new HashSet<>();

	private static final String NO_MODEL_FOR_LAYER = "No model for layer";

	@Override
	public String[] patronesRapidos() {
		return new String[] { NO_MODEL_FOR_LAYER };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		this.posibleErrorCreacionModelo = true;
		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		// Solo activar el análisis por línea si algún log contiene el texto base.
		// Importante: la misma instancia puede analizar varios logs, por eso nunca
		// volvemos esto a false aquí.
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		if (consola.contenido_verificar.contains(NO_MODEL_FOR_LAYER)) {
			this.posibleErrorCreacionModelo = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleErrorCreacionModelo;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!this.posibleErrorCreacionModelo || linea == null) {
			return;
		}

		if (linea.contains("WARN") || linea.contains("Warn")) {
			return;
		}

		if (!linea.contains(NO_MODEL_FOR_LAYER)) {
			return;
		}

		String modeloCompleto = extraerModelo(linea);
		if (modeloCompleto == null || modeloCompleto.isEmpty()) {
			return;
		}

		int dosPuntos = modeloCompleto.indexOf(':');
		if (dosPuntos <= 0 || dosPuntos >= modeloCompleto.length() - 1) {
			return;
		}

		String modid = modeloCompleto.substring(0, dosPuntos).trim();
		String nombreModelo = modeloCompleto.substring(dosPuntos + 1).trim();

		if (modid.isEmpty() || nombreModelo.isEmpty()) {
			return;
		}

		String claveModelo = modid + ":" + nombreModelo;

		if (this.modelosReportados.contains(claveModelo)) {
			return;
		}

		this.modelosReportados.add(claveModelo);

		String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		this.mensajes.add(MonitorDePID.idioma.errorCreacionModeloFallida(modid, nombreModelo) + enlaceHtml);
	}

	private String extraerModelo(String linea) {
		int inicio = linea.indexOf(NO_MODEL_FOR_LAYER);

		if (inicio < 0) {
			return null;
		}

		inicio += NO_MODEL_FOR_LAYER.length();

		while (inicio < linea.length() && Character.isWhitespace(linea.charAt(inicio))) {
			inicio++;
		}

		int fin = inicio;

		while (fin < linea.length()) {
			char c = linea.charAt(fin);

			if (Character.isWhitespace(c)) {
				break;
			}

			if (c == '#' || c == '"' || c == '\'' || c == ',' || c == ';' || c == ')' || c == ']') {
				break;
			}

			fin++;
		}

		if (fin <= inicio) {
			return null;
		}

		return linea.substring(inicio, fin).trim();
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return trazo != null && trazo.trace != null && trazo.trace.contains(NO_MODEL_FOR_LAYER);
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCreacionModeloFallida();
	}

	@Override
	public boolean activado() {
		return !this.mensajes.isEmpty();
	}

	@Override
	public String mensaje() {
		return String.join("<br>", this.mensajes);
	}

	@Override
	public float prioridad() {
		return 1505.0f;
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionErrorCreacionModeloFallida())
				.construir();
	}

	@Override
	public String id() {
		return "creacion_modelo_fallida";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorCreacionModeloFallida();
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}