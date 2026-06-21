package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Analiza errores de compatibilidad entre C2ME y mods de conexión. Detecta
 * específicamente el error de IllegalAccessException relacionado con el sistema
 * de módulos de Java. Recomienda usar C3ME en lugar de C2ME cuando se utilizan
 * mods de conexión.
 */
public class SCOErrorCompatibilidadC2ME implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private boolean connectorPresente = false;
	private String enlaceHtml = "";

	private static final String C2ME = "com.ishland.c2me";
	private static final String SINYTRA_CONNECTOR = "SINYTRA CONNECTOR IS PRESENT!";
	private static final String SPECIAL_COMPATIBILITY_OPERATION = "specialcompatibilityoperation";
	private static final String ILLEGAL_ACCESS_EXCEPTION = "java.lang.IllegalAccessException";
	private static final String UNSAFE_ACCESS = "cannot access class jdk.internal.misc.Unsafe";
	private static final String JAVA_BASE_EXPORT = "because module java.base does not export";

	@Override
	public String[] patronesRapidos() {
		return new String[] { C2ME, SINYTRA_CONNECTOR, SPECIAL_COMPATIBILITY_OPERATION, ILLEGAL_ACCESS_EXCEPTION,
				UNSAFE_ACCESS, JAVA_BASE_EXPORT };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		if (linea.contains(SINYTRA_CONNECTOR) || linea.contains(SPECIAL_COMPATIBILITY_OPERATION)) {
			connectorPresente = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	/**
	 * Analiza cada línea del registro para detectar los errores específicos.
	 * <p>
	 * Solo se procesa la línea si previamente se ha detectado que C2ME y un mod de
	 * conexión están presentes en el log (para reducir trabajo y falsos positivos).
	 * Cuando se encuentra el patrón de IllegalAccessException contra
	 * {@code jdk.internal.misc.Unsafe}, se construye el mensaje y se registra el
	 * enlace.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Detecta el error específico de acceso ilegal entre módulos de Java
		if (linea.contains(ILLEGAL_ACCESS_EXCEPTION) && linea.contains(UNSAFE_ACCESS)
				&& linea.contains(JAVA_BASE_EXPORT)) {
			mensaje = MonitorDePID.idioma.errorCompatibilidadC2ME() + Verificaciones.nl_html;
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new SCOErrorCompatibilidadC2ME();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 945.0f; // Alta prioridad - error que impide la carga correcta del juego
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_compatibilidad_c2me();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1_compatibilidad_c2me())
				.agregarEtiqueta(MonitorDePID.idioma.paso2_compatibilidad_c2me())
				.agregarEtiqueta(MonitorDePID.idioma.paso3_compatibilidad_c2me()).construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "sco_error_compatibilidad_c2me";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { ILLEGAL_ACCESS_EXCEPTION, UNSAFE_ACCESS, JAVA_BASE_EXPORT };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}