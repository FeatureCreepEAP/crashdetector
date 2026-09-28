package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el error java.lang.IllegalArgumentException con el mensaje
 * Multiple overrides for option sodium, con Sources que lista dos o mas mods.
 *
 * Ocurre cuando dos o mas mods registran un valor para la MISMA opcion de
 * configuracion de Sodium (o de mods derivados como Embeddium, Rubidium o
 * Chloride). Sodium no puede decidir que valor usar, lanza la excepcion y el
 * juego se cierra.
 *
 * La verificacion no depende de los mods concretos que aparezcan en Sources:
 * cubre CUALQUIER mod que anule opciones de Sodium extrayendo dinamicamente el
 * nombre de la opcion y los mods en conflicto para mostrarlos en el mensaje.
 */
public class ConflictoOverridesOpcionesSodium implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";
	private String opcion = "";
	private String fuentes = "";

	private static final String OVERRIDE = "Multiple overrides for option";
	private static final String SODIUM = "sodium:";
	private static final String OPTION = "option '";
	private static final String SOURCES = "Sources:";
	private static final String EXCEPCION = "IllegalArgumentException";

	@Override
	public String[] patronesRapidos() {
		return new String[] { OVERRIDE, SODIUM, EXCEPCION, SOURCES };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {
		if (activado || linea == null) {
			return;
		}

		/*
		 * La linea real contiene tanto el texto Multiple overrides for option como la
		 * clave de la opcion, que siempre empieza por sodium:. Asi se evita activarse
		 * con mensajes que solo mencionen Sodium de pasada.
		 */
		if (!linea.contains(OVERRIDE) || !linea.contains(SODIUM)) {
			return;
		}

		this.opcion = extraerEntre(linea, OPTION, "'");
		this.fuentes = extraerFuentes(linea);

		if (consola != null) {
			this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		}

		this.activado = true;
	}

	/**
	 * Devuelve el texto que queda entre dos cadenas, sin incluirlas. Si el
	 * delimitador final no aparece, devuelve el resto de la linea.
	 */
	private String extraerEntre(String linea, String inicio, String fin) {
		int pos = linea.indexOf(inicio);

		if (pos < 0) {
			return "";
		}

		int desde = pos + inicio.length();
		int hasta = linea.indexOf(fin, desde);

		if (hasta < 0) {
			return linea.substring(desde).trim();
		}

		return linea.substring(desde, hasta).trim();
	}

	/**
	 * Extrae la lista de mods que aparece despues de Sources:, quitando la
	 * puntuacion final del mensaje.
	 */
	private String extraerFuentes(String linea) {
		int pos = linea.indexOf(SOURCES);

		if (pos < 0) {
			return "";
		}

		String resto = linea.substring(pos + SOURCES.length()).trim();

		while (resto.endsWith("!") || resto.endsWith(".")) {
			resto = resto.substring(0, resto.length() - 1).trim();
		}

		return resto;
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoOverridesOpcionesSodium();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeConflictoOverridesOpcionesSodium(opcion, fuentes) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreConflictoOverridesOpcionesSodium();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "conflicto_overrides_opciones_sodium";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
