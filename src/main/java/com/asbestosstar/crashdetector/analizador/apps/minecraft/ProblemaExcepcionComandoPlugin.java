package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta excepciones al ejecutar comandos de plugins. Gracias a
 * Aternos porque esta es una implementacion de su codex:
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaExcepcionComandoPlugin implements Verificaciones {

	private boolean posibleExcepcionComandoPlugin = false;
	private boolean activado = false;

	private String mensaje = "";
	private String nombrePlugin = "";
	private String comando = "";
	private String enlace = "";

	private static final String TEXTO_INICIO = "org.bukkit.command.CommandException: Cannot execute command '";

	private static final String TEXTO_ENTRE_COMANDO_Y_PLUGIN = "' in plugin ";

	/**
	 * Verificacion global ligera.
	 *
	 * Se ejecuta primero. No se limpian campos porque esta verificacion puede
	 * ejecutarse sobre varios archivos de log con la misma instancia.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(TEXTO_INICIO) && contenido.contains(TEXTO_ENTRE_COMANDO_Y_PLUGIN)) {
			posibleExcepcionComandoPlugin = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posibleExcepcionComandoPlugin)
			return false;

		return true;
	}

	/**
	 * Verificacion por linea.
	 *
	 * Aqui se detecta el comando exacto, el plugin exacto y se guarda el enlace
	 * hacia la linea del log.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleExcepcionComandoPlugin || activado || linea == null || linea.isEmpty()) {
			return;
		}

		DatosComandoPlugin datos = extraerDatosComandoPlugin(linea);

		if (datos == null) {
			return;
		}

		this.comando = datos.comando;
		this.nombrePlugin = datos.nombrePlugin;
		this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

		this.mensaje = MonitorDePID.idioma.mensajeExcepcionComandoPlugin(nombrePlugin, comando) + Verificaciones.nl_html
				+ enlace;

		activado = true;
	}

	/**
	 * Extrae el comando y el nombre del plugin sin usar Pattern/Matcher.
	 *
	 * Formato esperado: org.bukkit.command.CommandException: Cannot execute command
	 * 'comando' in plugin Plugin
	 */
	private DatosComandoPlugin extraerDatosComandoPlugin(String texto) {
		int inicio = texto.indexOf(TEXTO_INICIO);

		if (inicio < 0) {
			return null;
		}

		int inicioComando = inicio + TEXTO_INICIO.length();
		int finComando = texto.indexOf(TEXTO_ENTRE_COMANDO_Y_PLUGIN, inicioComando);

		if (finComando < 0 || finComando <= inicioComando) {
			return null;
		}

		String comandoDetectado = texto.substring(inicioComando, finComando).trim();

		int inicioPlugin = finComando + TEXTO_ENTRE_COMANDO_Y_PLUGIN.length();
		int finPlugin = buscarFinNombrePlugin(texto, inicioPlugin);

		if (finPlugin <= inicioPlugin) {
			return null;
		}

		String pluginDetectado = texto.substring(inicioPlugin, finPlugin).trim();

		if (comandoDetectado.isEmpty() || pluginDetectado.isEmpty()) {
			return null;
		}

		return new DatosComandoPlugin(comandoDetectado, pluginDetectado);
	}

	/**
	 * Busca el final del nombre del plugin.
	 *
	 * El patron original usaba \w+, entonces aqui se aceptan: letras, numeros y
	 * guion bajo.
	 */
	private int buscarFinNombrePlugin(String texto, int inicio) {
		int i = inicio;

		while (i < texto.length()) {
			char c = texto.charAt(i);

			if (Character.isLetterOrDigit(c) || c == '_') {
				i++;
			} else {
				break;
			}
		}

		return i;
	}

	private static class DatosComandoPlugin {
		private final String comando;
		private final String nombrePlugin;

		private DatosComandoPlugin(String comando, String nombrePlugin) {
			this.comando = comando;
			this.nombrePlugin = nombrePlugin;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaExcepcionComandoPlugin();
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
		return 500.0f;
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
		return MonitorDePID.idioma.nombreProblemaExcepcionComandoPlugin();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionDiferentePlugin(nombrePlugin))
				.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(nombrePlugin)).construir();
	}

	@Override
	public String id() {
		return "excepcion_comando_plugin";
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