package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el error de YesSteveModel en Linux donde solo se soporta el servidor
 * YSM, lo cual ha sido corregido en versiones más recientes desde el 23 de
 * noviembre.
 */
public class ErrorYesSteveModelLinux implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String YSM_LINUX_COMPLETO = "java.lang.RuntimeException: Only YSM server is supported on linux.";
	private static final String YSM_LINUX = "Only YSM server is supported on linux";

	@Override
	public String[] patronesRapidos() {
		return new String[] { YSM_LINUX };
	}

	/**
	 * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
	 */
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
	 * Se busca el patrón característico del error donde YesSteveModel indica que
	 * solo se soporta el servidor YSM en Linux.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscamos la línea que contiene el error de YesSteveModel en Linux
		if (lineaContieneErrorYesSteveModel(linea)) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de YesSteveModel
			mensaje = MonitorDePID.idioma.errorYesSteveModelLinux() + Verificaciones.nl_html;
			activado = true;
		}
	}

	private boolean lineaContieneErrorYesSteveModel(String linea) {
		return linea.contains(YSM_LINUX_COMPLETO) || linea.contains(YSM_LINUX);
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorYesSteveModelLinux();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Prioridad media-alta: rompe la funcionalidad en Linux
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorYesSteveModelLinux();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorYesSteveModelLinux())
				.construir();
	}

	@Override
	public String id() {
		return "error_yessetevemodel_linux";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad de YesSteveModel en Linux.
	 * </p>
	 */
	@Override
	public String[] ocupaTrazo() {
		return new String[] { YSM_LINUX };
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