package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta cuando el mod Simple Voice Chat no puede enlazarse al puerto UDP
 * porque ya está en uso o la dirección IP especificada no es válida.
 */
public class ErrorVoiceChatPuertoOcupado implements Verificaciones {

	private boolean activado = false;

	private String mensaje = "";

	private static final String VOICECHAT_FAILED = "[voicechat] Failed to run voice chat at UDP port";
	private static final String ADDRESS_ALREADY_IN_USE = "java.net.BindException: Address already in use";

	@Override
	public String[] patronesRapidos() {
		return new String[] { VOICECHAT_FAILED, ADDRESS_ALREADY_IN_USE };
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

		if (linea.contains(VOICECHAT_FAILED)) {
			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorVoiceChatPuertoOcupado() + enlaceHtml;
			this.activado = true;
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return trazo != null && trazo.trace != null && trazo.trace.contains(VOICECHAT_FAILED)
				&& trazo.trace.contains(ADDRESS_ALREADY_IN_USE);
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new ErrorVoiceChatPuertoOcupado();
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
		return 1200.0f; // Media-alta: afecta funcionalidad, no impide iniciar
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionErrorVoiceChatPuertoOcupado())
				.construir();
	}

	@Override
	public String id() {
		return "voicechat_puerto_ocupado";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorVoiceChatPuertoOcupado();
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}