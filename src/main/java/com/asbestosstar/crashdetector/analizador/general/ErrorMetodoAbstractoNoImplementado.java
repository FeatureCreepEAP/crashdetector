package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores AbstractMethodError específicos donde una clase no implementa
 * un método de una interfaz o clase abstracta.
 *
 * La detección se hace por línea. La resolución del origen se hace tarde, en
 * mensaje(), para no depender de VDST antes de finalizarArchivo().
 */
public class ErrorMetodoAbstractoNoImplementado implements Verificaciones {

	private boolean activado = false;

	private Consola consolaDetectada = null;
	private int lineaDetectada = -1;

	private String claseConcreta = "";
	private String firmaMetodo = "";
	private String interfaz = "";
	private String enlaceHtml = "";

	private String origen = "";
	private boolean origenResuelto = false;

	private static final String TEXTO_ABSTRACT = "java.lang.AbstractMethodError";
	private static final String TEXTO_NO_IMPLEMENTA = "does not define or inherit an implementation";
	private static final String TEXTO_INTERFACE = "of interface";
	private static final String TEXTO_CLASE_ABSTRACTA = "of abstract class";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ABSTRACT, TEXTO_NO_IMPLEMENTA, TEXTO_INTERFACE, TEXTO_CLASE_ABSTRACTA };
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
		if (linea == null || linea.isEmpty()) {
			return;
		}

		if (!lineaContieneMetodoAbstracto(linea)) {
			return;
		}

		String recorte = linea.trim();

		String textoTipoObjetivo = obtenerTextoTipoObjetivo(recorte);
		if (textoTipoObjetivo == null) {
			return;
		}

		int idxClaseStart = recorte.indexOf(':') + 1;
		int idxMetodoStart = recorte.indexOf('\'', idxClaseStart);
		int idxMetodoEnd = recorte.indexOf('\'', idxMetodoStart + 1);
		int idxInterfaz = recorte.lastIndexOf(textoTipoObjetivo);

		if (idxClaseStart <= 0 || idxMetodoStart < 0 || idxMetodoEnd < 0 || idxInterfaz < 0) {
			return;
		}

		this.claseConcreta = recorte.substring(idxClaseStart, idxMetodoStart).trim();
		this.firmaMetodo = recorte.substring(idxMetodoStart + 1, idxMetodoEnd).trim();
		this.interfaz = recorte.substring(idxInterfaz + textoTipoObjetivo.length()).trim();

		this.consolaDetectada = consola;
		this.lineaDetectada = numero_de_linea;
		this.enlaceHtml = consola != null ? consola.agregarErrorALectador(numero_de_linea, this) : "";

		this.origen = "";
		this.origenResuelto = false;
		this.activado = true;
	}

	private boolean lineaContieneMetodoAbstracto(String linea) {
		return linea != null && linea.contains(TEXTO_ABSTRACT) && linea.contains(TEXTO_NO_IMPLEMENTA)
				&& contieneTipoObjetivo(linea);
	}

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

		int ventanaSuperior = lineaDetectada + 30;

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

	private boolean contieneTipoObjetivo(String texto) {
		return texto != null && (texto.contains(TEXTO_INTERFACE) || texto.contains(TEXTO_CLASE_ABSTRACTA));
	}

	private String obtenerTextoTipoObjetivo(String texto) {
		if (texto == null) {
			return null;
		}
		if (texto.contains(TEXTO_INTERFACE)) {
			return TEXTO_INTERFACE;
		}
		if (texto.contains(TEXTO_CLASE_ABSTRACTA)) {
			return TEXTO_CLASE_ABSTRACTA;
		}
		return null;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_ABSTRACT, TEXTO_NO_IMPLEMENTA };
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorMetodoAbstractoNoImplementado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public String mensaje() {
		if (!activado) {
			return "";
		}

		resolverOrigenSiNecesario();

		return MonitorDePID.idioma.errorMetodoAbstractoNoImplementadoDetallado(claseConcreta, firmaMetodo, interfaz,
				origen) + Verificaciones.nl_html + enlaceHtml;
	}

	@Override
	public float prioridad() {
		return 1200.0f;
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucionMetodoAbstractoNoImplementado()).construir();
	}

	@Override
	public String id() {
		return "metodo_abstracto_no_implementado";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorMetodoAbstractoNoImplementado();
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}