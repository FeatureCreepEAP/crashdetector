package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta problemas con el mod Supplementaries que impiden la carga del
 * servidor debido a errores en el registro de comportamientos de fuego,
 * causando un NullPointerException durante la carga de datapacks.
 */
public class ErrorSupplementariesCargaServidor implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String SUPPLEMENTARIES_FIRE_BEHAVIOR = "net.mehvahdjukaar.supplementaries.common.block.fire_behaviors.FireBehaviorsManager.registerBehaviors";
	private static final String FAILED_TO_LOAD_DATAPACKS = "Failed to load datapacks";
	private static final String FAILED_TO_LOAD_DATAPACKS_COMPLETO = "Failed to load datapacks, can't proceed with server load";
	private static final String EXECUTION_EXCEPTION = "java.util.concurrent.ExecutionException";
	private static final String NULL_POINTER_EXCEPTION = "java.lang.NullPointerException";
	private static final String CANNOT_INVOKE = "Cannot invoke";

	/**
	 * Método de compatibilidad — busca si Supplementaries está presente en el
	 * contenido completo del registro.
	 */
	@Override
	public String[] patronesRapidos() {
		return new String[] { SUPPLEMENTARIES_FIRE_BEHAVIOR, FAILED_TO_LOAD_DATAPACKS };
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
	 * Se busca el patrón característico del error donde Supplementaries falla
	 * durante la carga de datapacks, específicamente con FireBehaviorsManager.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscamos la combinación de mensajes que indican el problema de
		// Supplementaries
		if (linea.contains(FAILED_TO_LOAD_DATAPACKS)) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de Supplementaries
			mensaje = MonitorDePID.idioma.errorSupplementariesCargaServidor() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorSupplementariesCargaServidor();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1100.0f; // Alta prioridad: impide la carga del servidor
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorSupplementariesCargaServidor();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.pasoErrorSupplementariesCargaServidor()).construir();
	}

	@Override
	public String id() {
		return "error_supplementaries_carga_servidor";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad de Supplementaries con la carga del servidor.
	 * </p>
	 */
	@Override
	public String[] ocupaTrazo() {
		return new String[] { FAILED_TO_LOAD_DATAPACKS_COMPLETO, EXECUTION_EXCEPTION, NULL_POINTER_EXCEPTION,
				CANNOT_INVOKE, SUPPLEMENTARIES_FIRE_BEHAVIOR };
	}

	@Override
	public boolean anularNormal() {
		return true;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}