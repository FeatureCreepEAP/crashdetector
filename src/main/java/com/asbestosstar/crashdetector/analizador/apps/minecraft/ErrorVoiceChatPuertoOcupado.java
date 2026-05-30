package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta cuando el mod Simple Voice Chat no puede enlazarse al puerto UDP
 * porque ya está en uso o la dirección IP especificada no es válida.
 */
public class ErrorVoiceChatPuertoOcupado implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		// Este método no se usa; el análisis se hace por línea
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (this.activado) {
			return;
		}

		if (linea.contains("[voicechat] Failed to run voice chat at UDP port")
				&& consola.contenido_verificar.contains("java.net.BindException: Address already in use")) {

			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorVoiceChatPuertoOcupado() + enlaceHtml;
			this.activado = true;
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return trazo.trace.contains("[voicechat] Failed to run voice chat at UDP port")
				&& trazo.trace.contains("java.net.BindException: Address already in use");
	}

	@Override
	public Verificaciones nueva() {
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
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}