package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de versión de clase incompatible causados por
 * GregTechEasyCore donde AbstractFurnaceBlockEntity ha sido compilado con una
 * versión más reciente de Java que la que está usando el servidor.
 */
public class ErrorVersionClaseGregTechEasyCore implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String GTECORE = "gtecore";
	private static final String UNSUPPORTED_CLASS_VERSION_ERROR = "java.lang.UnsupportedClassVersionError";
	private static final String ABSTRACT_FURNACE_BLOCK_ENTITY = "net/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity";
	private static final String JAVA_RUNTIME_RECIENTE = "has been compiled by a more recent version of the Java Runtime";

	@Override
	public String[] patronesRapidos() {
		return new String[] { GTECORE, UNSUPPORTED_CLASS_VERSION_ERROR, ABSTRACT_FURNACE_BLOCK_ENTITY,
				JAVA_RUNTIME_RECIENTE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde AbstractFurnaceBlockEntity
	 * ha sido compilado con una versión más reciente de Java.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscamos la línea que contiene el error de versión de clase incompatible de
		// GregTechEasyCore
		if (linea.contains(UNSUPPORTED_CLASS_VERSION_ERROR) && linea.contains(ABSTRACT_FURNACE_BLOCK_ENTITY)
				&& linea.contains(JAVA_RUNTIME_RECIENTE)) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de GregTechEasyCore
			mensaje = MonitorDePID.idioma.errorVersionClaseGregTechEasyCore() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorVersionClaseGregTechEasyCore();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000.0f; // Prioridad máxima: rompe la carga del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorVersionClaseGregTechEasyCore();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.pasoErrorVersionClaseGregTechEasyCore()).construir();
	}

	@Override
	public String id() {
		return "error_version_clase_gtecore";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de versión de
	 * clase incompatible de GregTechEasyCore.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains(UNSUPPORTED_CLASS_VERSION_ERROR) && t.contains(ABSTRACT_FURNACE_BLOCK_ENTITY)
				&& t.contains(JAVA_RUNTIME_RECIENTE) && t.contains(GTECORE);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}