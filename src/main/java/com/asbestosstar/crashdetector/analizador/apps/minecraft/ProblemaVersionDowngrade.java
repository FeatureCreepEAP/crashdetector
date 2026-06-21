package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta intentos de cargar un mundo creado en una versión más
 * reciente de Minecraft. Gracias a Aternos porque esta es una implementacion de
 * su codex: https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaVersionDowngrade implements Verificaciones {

	private boolean activado = false;

	private String mensaje = "";
	private String enlace = "";

	private static final String TEXTO_ERROR = "java.lang.RuntimeException: Server attempted to load chunk saved with newer version of minecraft! ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificacion por linea.
	 *
	 * Detecta la linea exacta del error y agrega enlace al lector.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!esLineaVersionDowngrade(linea)) {
			return;
		}

		this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
		this.mensaje = MonitorDePID.idioma.mensajeVersionDowngrade() + " " + enlace;
		this.activado = true;
	}

	/**
	 * Detecta:
	 *
	 * java.lang.RuntimeException: Server attempted to load chunk saved with newer
	 * version of minecraft! 3955 > 3465
	 *
	 * Sin Pattern/Matcher.
	 */
	private boolean esLineaVersionDowngrade(String linea) {
		int inicio = linea.indexOf(TEXTO_ERROR);

		if (inicio < 0) {
			return false;
		}

		inicio += TEXTO_ERROR.length();

		int finNumeroNuevo = leerFinNumero(linea, inicio);

		if (finNumeroNuevo <= inicio) {
			return false;
		}

		int indice = finNumeroNuevo;

		while (indice < linea.length() && Character.isWhitespace(linea.charAt(indice))) {
			indice++;
		}

		if (indice >= linea.length() || linea.charAt(indice) != '>') {
			return false;
		}

		indice++;

		while (indice < linea.length() && Character.isWhitespace(linea.charAt(indice))) {
			indice++;
		}

		int finNumeroServidor = leerFinNumero(linea, indice);

		return finNumeroServidor > indice;
	}

	/**
	 * Lee una secuencia de digitos.
	 */
	private int leerFinNumero(String texto, int inicio) {
		int i = inicio;

		while (i < texto.length() && Character.isDigit(texto.charAt(i))) {
			i++;
		}

		return i;
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public VerificacionesLegacy nueva() {
		return new ProblemaVersionDowngrade();
	}

	/**
	 * Indica si el problema fue detectado.
	 */
	@Override
	public boolean activado() {
		return activado;
	}

	/**
	 * Prioridad del problema.
	 */
	@Override
	public float prioridad() {
		return 850.0f;
	}

	/**
	 * Devuelve el mensaje de error almacenado.
	 */
	@Override
	public String mensaje() {
		return mensaje;
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaVersionDowngrade();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionVersionDowngrade())
				.agregarEtiqueta(MonitorDePID.idioma.solucionGenerarNuevoMundo()).construir();
	}

	@Override
	public boolean anularNormal() {
		return true;
	}

	@Override
	public String id() {
		return "version_downgrade";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}