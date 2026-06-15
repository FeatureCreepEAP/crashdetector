package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ErrorDeMonitorLWJGL implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	/**
	 * Bandera ligera para saber si el log contiene el texto base del error. Sirve
	 * como filtro rápido antes del análisis por línea.
	 */
	private boolean posibleErrorMonitor = false;

	private static final String TEXTO_ERROR = "org.lwjgl.LWJGLException: Failed to set display mode";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(TEXTO_ERROR)) {
			posibleErrorMonitor = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			posibleErrorMonitor = false;
			return;
		}

		String contenidoConsola = consola.contenido_verificar;

		// Trabajo global mínimo: solo comprobamos si aparece el texto del error.
		// Si no está, la verificación por línea se saltará completamente.
		posibleErrorMonitor = contenidoConsola.contains(TEXTO_ERROR);
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleErrorMonitor && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó o sabemos que no hay rastro del error en el log, no hacemos
		// nada.
		if (activado || !posibleErrorMonitor || linea == null) {
			return;
		}

		// Verifica cada línea buscando el error específico
		if (linea.contains(TEXTO_ERROR)) {
			mensaje = MonitorDePID.idioma.errorMonitorLWJGL() + Verificaciones.nl_html;
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorDeMonitorLWJGL();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 800.0f; // Prioridad crítica para errores de renderizado
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_error_de_monitor_lwjgl();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "monitor_lwjgl";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// Solo marcamos trazos si ya se detectó el problema y el texto del trazo
		// contiene exactamente el error de LWJGL sobre el modo de pantalla.
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}
		return trazo.trace.contains(TEXTO_ERROR);
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