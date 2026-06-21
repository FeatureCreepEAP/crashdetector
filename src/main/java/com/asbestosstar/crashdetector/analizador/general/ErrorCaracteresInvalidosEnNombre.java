package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Analiza errores cuando los mods contienen caracteres inválidos en sus nombres
 * de módulo. Detecta específicamente el error "Invalid module name: 'X' is not
 * a Java identifier". Incluye el nombre del módulo y la parte inválida en los
 * mensajes.
 */
public class ErrorCaracteresInvalidosEnNombre implements Verificaciones {

	private static final String PREFIJO_EXCEPCION = "IllegalArgumentException: ";
	private static final String TEXTO_ERROR = ": Invalid module name: '";
	private static final String TEXTO_ERROR_SIMPLE = "Invalid module name: '";
	private static final String TEXTO_FINAL = "' is not a Java identifier";

	private boolean activado = false;
	private String mensaje = "";
	private String nombreModulo = "";
	private String parteInvalida = "";
	private String enlaceHtml = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR_SIMPLE, TEXTO_FINAL };
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

		if (!lineaContieneNombreInvalido(linea)) {
			return;
		}

		String[] datos = extraerDatos(linea);
		if (datos == null) {
			return;
		}

		nombreModulo = datos[0];
		parteInvalida = datos[1];

		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		mensaje = MonitorDePID.idioma.errorCaracteresInvalidosEnNombre(nombreModulo, parteInvalida)
				+ Verificaciones.nl_html;

		activado = true;
	}

	private boolean lineaContieneNombreInvalido(String linea) {
		return linea.contains(TEXTO_ERROR_SIMPLE) && linea.contains(TEXTO_FINAL);
	}

	/**
	 * Extrae: IllegalArgumentException: <modulo>: Invalid module name: '<parte>' is
	 * not a Java identifier
	 *
	 * Sin regex.
	 */
	private static String[] extraerDatos(String linea) {
		int inicioExcepcion = linea.indexOf(PREFIJO_EXCEPCION);
		if (inicioExcepcion < 0) {
			return null;
		}

		int inicioModulo = inicioExcepcion + PREFIJO_EXCEPCION.length();

		int inicioTextoError = linea.indexOf(TEXTO_ERROR, inicioModulo);
		if (inicioTextoError < 0 || inicioTextoError <= inicioModulo) {
			return null;
		}

		String modulo = linea.substring(inicioModulo, inicioTextoError).trim();
		if (modulo.length() == 0) {
			return null;
		}

		int inicioParteInvalida = inicioTextoError + TEXTO_ERROR.length();

		int finParteInvalida = linea.indexOf(TEXTO_FINAL, inicioParteInvalida);
		if (finParteInvalida < 0 || finParteInvalida <= inicioParteInvalida) {
			return null;
		}

		String parte = linea.substring(inicioParteInvalida, finParteInvalida).trim();
		if (parte.length() == 0) {
			return null;
		}

		return new String[] { modulo, parte };
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCaracteresInvalidosEnNombre();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f;
	}

	@Override
	public String mensaje() {
		if (!activado) {
			return "";
		}
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_caracteres_invalidos();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_caracteres_invalidos(nombreModulo, parteInvalida))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_caracteres_invalidos(nombreModulo))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_caracteres_invalidos()).construir();
	}

	@Override
	public String id() {
		return "caracters_invalidos_en_nombre";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { "Invalid module name:", "is not a Java identifier" };
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}