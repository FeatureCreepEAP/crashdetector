package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el uso de Controllable en un servidor dedicado, lo cual provoca un
 * error porque Controllable intenta cargar clases del cliente en un entorno de
 * servidor.
 */
public class ErrorControllableServidor implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoControllable = false;

	private static final String CONTROLLABLE = "com.mrcrayfish.controllable";
	private static final String BOOTSTRAP_METHOD_ERROR = "java.lang.BootstrapMethodError";
	private static final String ATTEMPTED_TO_LOAD_CLASS = "Attempted to load class";
	private static final String SCREEN_CLIENTE = "net/minecraft/client/gui/screens/Screen";
	private static final String INVALID_DIST_SERVER = "for invalid dist DEDICATED_SERVER";

	@Override
	public String[] patronesRapidos() {
		return new String[] { CONTROLLABLE, BOOTSTRAP_METHOD_ERROR, ATTEMPTED_TO_LOAD_CLASS, SCREEN_CLIENTE,
				INVALID_DIST_SERVER };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(CONTROLLABLE)) {
			encontradoControllable = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde Controllable falla al crear
	 * su instancia en un servidor dedicado porque intenta cargar clases del
	 * cliente.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea.contains(CONTROLLABLE)) {
			encontradoControllable = true;
		}

		// Buscamos la línea que contiene el error de carga de clase para servidor
		// dedicado de Controllable
		if (encontradoControllable && linea.contains(BOOTSTRAP_METHOD_ERROR) && linea.contains(ATTEMPTED_TO_LOAD_CLASS)
				&& linea.contains(SCREEN_CLIENTE) && linea.contains(INVALID_DIST_SERVER)) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al uso incorrecto de Controllable
			mensaje = MonitorDePID.idioma.errorControllableServidor() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorControllableServidor();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Alta prioridad: rompe la carga del mod
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorControllableServidor();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorControllableServidor())
				.construir();
	}

	@Override
	public String id() {
		return "error_controllable_servidor";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad entre Controllable y servidor dedicado.
	 * </p>
	 */
	@Override
	public String[] ocupaTrazo() {
		return new String[] { CONTROLLABLE, SCREEN_CLIENTE, INVALID_DIST_SERVER };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}