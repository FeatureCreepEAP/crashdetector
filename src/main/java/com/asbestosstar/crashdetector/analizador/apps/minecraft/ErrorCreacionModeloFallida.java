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
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de modelos faltantes: "No model for layer
 * <modid>:<nombre_modelo>#<capa>".
 *
 * Este error suele indicar que un mod, resource pack o datapack tiene recursos
 * corruptos, faltantes, o incompatibles con la versión actual de Minecraft.
 */
public class ErrorCreacionModeloFallida implements Verificaciones {

	private boolean posibleErrorCreacionModelo = false;

	private final List<String> mensajes = new ArrayList<>();
	private final Set<String> modelosReportados = new HashSet<>();

	@Override
	public void verificar(Consola consola) {
		// Solo activar el análisis por línea si algún log contiene el texto base.
		// Importante: la misma instancia puede analizar varios logs, por eso nunca
		// volvemos esto a false aquí.
		if (consola.contenido_verificar.contains("No model for layer")) {
			this.posibleErrorCreacionModelo = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (!this.posibleErrorCreacionModelo) {
			return;
		}

		if (linea.contains("WARN") || linea.contains("Warn")) {
			return;
		}

		if (!linea.contains("No model for layer")) {
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
		String marcador = "No model for layer";
		int inicio = linea.indexOf(marcador);

		if (inicio < 0) {
			return null;
		}

		inicio += marcador.length();

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
		return trazo.trace.contains("No model for layer");
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

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}