package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta crashes provocados manualmente usando comandos como: /crash_assistant
 * crash /crash_assistant *
 */
public class CrashProvocadoPorComando implements Verificaciones {

	private boolean activado = false;
	private boolean posibleCrashPorComando = false;
	private String mensaje = "";

	private static final String TEXTO_REPORTED_EXCEPTION = "net.minecraft.ReportedException";
	private static final String TEXTO_CRASH_ASSISTANT = "/crash_assistant";

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		posibleCrashPorComando = consola.contenido_verificar.contains(TEXTO_REPORTED_EXCEPTION)
				&& consola.contenido_verificar.contains(TEXTO_CRASH_ASSISTANT);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleCrashPorComando || activado || linea == null) {
			return;
		}

		String recorte = linea.trim();

		if (!recorte.contains(TEXTO_CRASH_ASSISTANT)) {
			return;
		}

		String comandoDetectado = extraerComando(recorte);

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

		String comando = linea.substring(inicio).trim();

		int comillaFinal = comando.indexOf("'", 1);
		if (comillaFinal > 0) {
			comando = comando.substring(0, comillaFinal).trim();
		}

		int comillaDobleFinal = comando.indexOf("\"", 1);
		if (comillaDobleFinal > 0) {
			comando = comando.substring(0, comillaDobleFinal).trim();
		}

		return comando;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return trazo.trace.contains(TEXTO_CRASH_ASSISTANT);
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