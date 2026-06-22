package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta "FATAL ERROR in native method … No context is current or a function
 * that is not available…".
 *
 * La resolución del origen se hace tarde, en mensaje(), para no depender de
 * VDST antes de finalizarArchivo().
 */
public class ErrorContextoOpenGL implements Verificaciones {

	private boolean activado = false;

	private Consola consolaDetectada = null;
	private int lineaDetectada = -1;

	private String enlaceHtml = "";
	private String origen = "";
	private boolean origenResuelto = false;

	// Estado para detectar el caso donde la cabecera está en una línea y el detalle
	// en la siguiente.
	private boolean cabeceraPendiente = false;
	private int lineaCabeceraPendiente = -1;

	private static final String TEXTO_CABECERA = "FATAL ERROR in native method";
	private static final String TEXTO_DETALLE = "No context is current or a function that is not available in the current context was called";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_CABECERA, TEXTO_DETALLE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación por línea.
	 *
	 * Solo detecta el error y guarda la línea. No consulta VDST aquí porque VDST
	 * puede estar trabajando en modo streaming y todavía no ha finalizado.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (linea == null || linea.isEmpty()) {
			return;
		}

		boolean cabeceraAqui = linea.contains(TEXTO_CABECERA);
		boolean detalleAqui = linea.contains(TEXTO_DETALLE);

		// Caso 1: cabecera y detalle en la misma línea.
		if (cabeceraAqui && detalleAqui) {
			activar(consola, numero_de_linea);
			return;
		}

		// Caso 2: cabecera en la línea anterior y detalle en esta línea.
		if (cabeceraPendiente && detalleAqui) {
			activar(consola, lineaCabeceraPendiente >= 0 ? lineaCabeceraPendiente : numero_de_linea);
			cabeceraPendiente = false;
			lineaCabeceraPendiente = -1;
			return;
		}

		// Guardar cabecera por si el detalle está en la siguiente línea.
		if (cabeceraAqui) {
			cabeceraPendiente = true;
			lineaCabeceraPendiente = numero_de_linea;
			return;
		}

		// La cabecera solo debe vivir una línea.
		if (cabeceraPendiente) {
			cabeceraPendiente = false;
			lineaCabeceraPendiente = -1;
		}
	}

	private void activar(Consola consola, int numero_de_linea) {
		if (activado) {
			return;
		}

		consolaDetectada = consola;
		lineaDetectada = numero_de_linea;
		enlaceHtml = consola != null ? consola.agregarErrorALectador(numero_de_linea, this) : "";
		activado = true;
	}

	/**
	 * Resuelve el origen usando los resultados ya terminados de VDST.
	 *
	 * En el nuevo sistema LineaTrazo.origen puede ser simplemente la clase. Eso es
	 * suficiente aquí: este verificador solo necesita dar una pista de dónde salió
	 * el trace, no resolver jar/modid durante el paso caliente.
	 */
	private void resolverOrigenSiNecesario() {
		if (origenResuelto) {
			return;
		}

		origenResuelto = true;
		origen = "";

		if (consolaDetectada == null || consolaDetectada.verificacion_de_stacktrace == null || lineaDetectada < 0) {
			return;
		}

		VerificacionDeStackTrace vdst = consolaDetectada.verificacion_de_stacktrace;

		if (vdst.trazos_completos == null || vdst.trazos_completos.isEmpty()) {
			return;
		}

		int ventanaSuperior = lineaDetectada + 60;

		VerificacionDeStackTrace.TraceInfo mejor = null;

		for (VerificacionDeStackTrace.TraceInfo ti : vdst.trazos_completos) {
			if (ti == null) {
				continue;
			}

			if (ti.consolaLineaComenzar >= lineaDetectada && ti.consolaLineaComenzar <= ventanaSuperior) {
				mejor = ti;
				break;
			}
		}

		if (mejor == null || mejor.lineas == null || mejor.lineas.isEmpty()) {
			return;
		}

		for (VerificacionDeStackTrace.LineaTrazo lt : mejor.lineas) {
			if (lt == null) {
				continue;
			}

			if (lt.clase != null && !lt.clase.isEmpty()) {
				origen = lt.clase;
				return;
			}

			if (lt.origen != null && !lt.origen.isEmpty()) {
				origen = lt.origen;
				return;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorContextoOpenGL();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1050.0f;
	}

	@Override
	public String mensaje() {
		if (!activado) {
			return "";
		}

		resolverOrigenSiNecesario();

		String mensaje = MonitorDePID.idioma.errorContextoOpenGL();

		if (!origen.isEmpty()) {
			mensaje = mensaje + " <b>(" + origen + ")</b>";
		}

		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorContextoOpenGL();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1ErrorContextoOpenGL())
				.construir();
	}

	@Override
	public String id() {
		return "contexto_opengl";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_CABECERA, TEXTO_DETALLE };
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}