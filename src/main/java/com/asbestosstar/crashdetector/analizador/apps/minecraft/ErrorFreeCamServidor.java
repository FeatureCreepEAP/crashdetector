package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el uso de FreeCam en un servidor dedicado, lo cual provoca un error
 * porque FreeCam intenta cargar clases del cliente en un entorno de servidor.
 */
public class ErrorFreeCamServidor implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String LOCAL_PLAYER_SERVER_ERROR = "Attempted to load class net/minecraft/client/player/LocalPlayer for invalid dist DEDICATED_SERVER";
	private static final String FAILED_TO_CREATE_MOD_INSTANCE = "Failed to create mod instance";
	private static final String FREECAM_MOD_ID = "ModID: freecam";
	private static final String FREECAM_CLASS = "net.xolt.freecam.Freecam";

	@Override
	public String[] patronesRapidos() {
		return new String[] { LOCAL_PLAYER_SERVER_ERROR, FAILED_TO_CREATE_MOD_INSTANCE, FREECAM_MOD_ID, FREECAM_CLASS };
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
	 * Se busca el patrón característico del error donde FreeCam falla al crear su
	 * instancia en un servidor dedicado porque intenta cargar clases del cliente.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscamos la línea que contiene ambos elementos clave en una sola línea
		if (linea.contains(FAILED_TO_CREATE_MOD_INSTANCE) && linea.contains(FREECAM_MOD_ID)
				&& linea.contains(FREECAM_CLASS)) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al uso incorrecto de FreeCam
			mensaje = MonitorDePID.idioma.errorFreeCamServidor() + VerificacionesLegacy.nl_html;
			activado = true;
		}
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new ErrorFreeCamServidor();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f; // Alta prioridad: rompe la carga del mod
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorFreeCamServidor();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorFreeCamServidor())
				.construir();
	}

	@Override
	public String id() {
		return "error_freecam_servidor";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad entre FreeCam y servidor dedicado.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains(FAILED_TO_CREATE_MOD_INSTANCE) && t.contains(FREECAM_MOD_ID) && t.contains(FREECAM_CLASS)
				&& t.contains(LOCAL_PLAYER_SERVER_ERROR);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}