package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de Every Compat (Etc) donde hay bloques de madera con nombres
 * inválidos que provocan un UnsupportedOperationException durante la carga.
 */
public class ErrorEveryCompatNombreInvalido implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String UNSUPPORTED_OPERATION_EXCEPTION = "java.lang.UnsupportedOperationException";
	private static final String INVALID_ITEM_NAME = "has an invalid item name";
	private static final String EVERY_COMPAT_ID = "every_compat";
	private static final String EVERY_COMPAT_NAME = "Every Compat";
	private static final String SIMPLE_ENTRY_SET = "SimpleEntrySet";

	@Override
	public String[] patronesRapidos() {
		return new String[] { UNSUPPORTED_OPERATION_EXCEPTION, INVALID_ITEM_NAME, EVERY_COMPAT_ID, EVERY_COMPAT_NAME,
				SIMPLE_ENTRY_SET };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde Every Compat encuentra un
	 * nombre de bloque de madera inválido que provoca un
	 * UnsupportedOperationException.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscamos la línea que contiene el error de nombre inválido de Every Compat
		if (lineaContieneEveryCompatNombreInvalido(linea)) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de Every Compat
			mensaje = MonitorDePID.idioma.errorEveryCompatNombreInvalido() + Verificaciones.nl_html;
			activado = true;
		}
	}

	private boolean lineaContieneEveryCompatNombreInvalido(String linea) {
		return linea.contains(UNSUPPORTED_OPERATION_EXCEPTION) && linea.contains(INVALID_ITEM_NAME)
				&& (linea.contains(EVERY_COMPAT_ID) || linea.contains(EVERY_COMPAT_NAME)
						|| linea.contains(SIMPLE_ENTRY_SET));
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorEveryCompatNombreInvalido();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 850.0f; // Prioridad media-alta: rompe la carga de bloques de madera
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorEveryCompatNombreInvalido();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorEveryCompatNombreInvalido())
				.construir();
	}

	@Override
	public String id() {
		return "error_every_compat_nombre_invalido";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad de nombres inválidos de Every Compat.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains(UNSUPPORTED_OPERATION_EXCEPTION) && t.contains(INVALID_ITEM_NAME)
				&& (t.contains(EVERY_COMPAT_ID) || t.contains(EVERY_COMPAT_NAME) || t.contains(SIMPLE_ENTRY_SET));
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}