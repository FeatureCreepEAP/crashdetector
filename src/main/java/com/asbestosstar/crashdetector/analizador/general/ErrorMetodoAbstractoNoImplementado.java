package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores AbstractMethodError específicos donde una clase no implementa
 * un método de una interfaz. Extrae los nombres concretos y el origen desde el
 * trace, sin usar regex ni mantener todas las líneas en memoria.
 */
public class ErrorMetodoAbstractoNoImplementado implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

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

		if (!lineaContieneMetodoAbstracto(linea)) {
			return;
		}

		String recorte = linea.trim();

		String textoTipoObjetivo = obtenerTextoTipoObjetivo(recorte);
		if (textoTipoObjetivo == null) {
			return;
		}

		// Extraer clase, método e interfaz
		int idxClaseStart = recorte.indexOf(':') + 1;
		int idxMetodoStart = recorte.indexOf('\'', idxClaseStart);
		int idxMetodoEnd = recorte.indexOf('\'', idxMetodoStart + 1);
		int idxInterfaz = recorte.lastIndexOf(textoTipoObjetivo);

		if (idxClaseStart <= 0 || idxMetodoStart < 0 || idxMetodoEnd < 0 || idxInterfaz < 0)
			return;

		String claseConcreta = recorte.substring(idxClaseStart, idxMetodoStart).trim();
		String firmaMetodo = recorte.substring(idxMetodoStart + 1, idxMetodoEnd).trim();
		String interfaz = recorte.substring(idxInterfaz + textoTipoObjetivo.length()).trim();

		// Buscar origen dinámicamente en las siguientes 10 líneas
		String origen = buscarOrigenCercano(consola, numero_de_linea);

		// Registrar el enlace a la línea concreta
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		mensaje = MonitorDePID.idioma.errorMetodoAbstractoNoImplementadoDetallado(claseConcreta, firmaMetodo, interfaz,
				origen) + VerificacionesLegacy.nl_html + enlaceHtml;

		activado = true;
	}

	private boolean lineaContieneMetodoAbstracto(String linea) {
		return linea.contains(TEXTO_ABSTRACT) && linea.contains(TEXTO_NO_IMPLEMENTA) && contieneTipoObjetivo(linea);
	}

	private String buscarOrigenCercano(Consola consola, int numero_de_linea) {
		if (consola == null || consola.lineas_verificar == null) {
			return "";
		}

		String[] lineas = consola.lineas_verificar;
		int inicio = numero_de_linea + 1;
		int fin = Math.min(numero_de_linea + 11, lineas.length);

		for (int j = inicio; j < fin; j++) {
			String l = lineas[j];

			if (l == null) {
				continue;
			}

			l = l.trim();

			if (l.startsWith("at ")) {
				String posibleOrigen = VerificacionDeStackTrace.extraerModidDeLinea(l);

				if (posibleOrigen == null || VerificacionDeStackTrace.esModNoPermite(posibleOrigen)) {
					java.util.List<String> jars = VerificacionDeStackTrace.extraerJarsDeLinea(l);
					if (!jars.isEmpty())
						posibleOrigen = jars.get(0);
					else
						posibleOrigen = VerificacionDeStackTrace.extraerPaqueteDeLinea(l);
				}

				if (posibleOrigen != null && !posibleOrigen.isEmpty()
						&& !VerificacionDeStackTrace.esModNoPermite(posibleOrigen)) {
					return posibleOrigen;
				}
			} else if (!l.isEmpty() && !l.startsWith("Caused by") && !l.startsWith("...")) {
				break;
			}
		}

		return "";
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
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null)
			return false;
		return trazo.trace.contains(TEXTO_ABSTRACT) && trazo.trace.contains(TEXTO_NO_IMPLEMENTA)
				&& contieneTipoObjetivo(trazo.trace);
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new ErrorMetodoAbstractoNoImplementado();
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