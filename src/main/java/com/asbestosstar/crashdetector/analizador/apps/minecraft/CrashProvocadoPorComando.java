package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta crashes provocados manualmente usando comandos como: /crash_assistant
 * crash /crash_assistant *
 */
public class CrashProvocadoPorComando implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	private static final String TEXTO_REPORTED_EXCEPTION = "net.minecraft.ReportedException";
	private static final String TEXTO_CRASH_ASSISTANT = "/crash_assistant";
	private static final String TEXTO_MODLIST = "Modlist";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_REPORTED_EXCEPTION, TEXTO_CRASH_ASSISTANT };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (linea.contains("|") || linea.contains(".jar")) {
			return;
		}

		if (!linea.contains(TEXTO_CRASH_ASSISTANT)) {
			return;
		}

		if (linea.contains(TEXTO_MODLIST)) {
			return;
		}

		String comandoDetectado = extraerComando(linea);

		String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		mensaje = MonitorDePID.idioma.errorCrashProvocadoPorComando(comandoDetectado) + Verificaciones.nl_html
				+ enlaceHtml;

		activado = true;
	}

	private String extraerComando(String linea) {
		int inicio = linea.indexOf(TEXTO_CRASH_ASSISTANT);
		if (inicio < 0) {
			return TEXTO_CRASH_ASSISTANT;
		}

		String comando = linea.substring(inicio);

		int comillaFinal = comando.indexOf("'", 1);
		if (comillaFinal > 0) {
			comando = comando.substring(0, comillaFinal);
		}

		int comillaDobleFinal = comando.indexOf("\"", 1);
		if (comillaDobleFinal > 0) {
			comando = comando.substring(0, comillaDobleFinal);
		}

		return comando;
	}

	@Override
	@Override
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_CRASH_ASSISTANT };
	}

	@Override
	public Verificaciones nueva() {
		return new CrashProvocadoPorComando();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public float prioridad() {
		return 1500.0f;
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "crash_provocado_por_comando";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreCrashProvocadoPorComando();
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}