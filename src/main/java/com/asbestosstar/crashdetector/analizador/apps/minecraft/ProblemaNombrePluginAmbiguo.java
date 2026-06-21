package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta plugins con nombres ambiguos en la carpeta
 * 'plugins'.Gracias a Aternos por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaNombrePluginAmbiguo implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;

	private String mensaje = "";

	private List<String> nombresPlugins = new ArrayList<String>();
	private List<String> primerosArchivos = new ArrayList<String>();
	private List<String> segundosArchivos = new ArrayList<String>();

	private final List<String> enlaces = new ArrayList<String>();

	private static final String TEXTO_BASE = "Ambiguous plugin name ";
	private static final String TEXTO_FILES = "files ";
	private static final String TEXTO_AND = " and ";
	private static final String TEXTO_PLUGINS = "plugins/";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_BASE, TEXTO_FILES, TEXTO_PLUGINS };
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

		ResultadoAmbiguo resultado = extraerDatos(linea);

		if (resultado != null) {

			nombresPlugins.add(resultado.nombrePlugin);

			primerosArchivos.add(extraerNombrePlugin(resultado.primerArchivo));

			segundosArchivos.add(extraerNombrePlugin(resultado.segundoArchivo));

			String enlace = consola.agregarErrorALectador(numero_de_linea, this);

			enlaces.add(enlace);

			reconstruirMensaje();

			activado = true;
		}
	}

	private boolean lineaContieneIndicioPluginAmbiguo(String linea) {
		return linea.contains(TEXTO_BASE) || linea.contains(TEXTO_FILES) || linea.contains(TEXTO_PLUGINS);
	}

	/**
	 * Extrae:
	 * 
	 * - nombre plugin - primer archivo - segundo archivo
	 * 
	 * sin usar regex.
	 */
	private ResultadoAmbiguo extraerDatos(String linea) {

		if (linea == null || linea.isEmpty()) {
			return null;
		}

		int posBase = linea.indexOf(TEXTO_BASE);

		if (posBase < 0) {
			return null;
		}

		// Buscar nombre del plugin entre ` ` o ' '
		ParComillas plugin = extraerTextoEntreComillas(linea, posBase + TEXTO_BASE.length());

		if (plugin == null || plugin.texto.isEmpty()) {
			return null;
		}

		int posFiles = linea.indexOf(TEXTO_FILES, plugin.fin);

		if (posFiles < 0) {
			return null;
		}

		ParComillas archivo1 = extraerTextoEntreComillas(linea, posFiles + TEXTO_FILES.length());

		if (archivo1 == null || archivo1.texto.isEmpty()) {
			return null;
		}

		int posAnd = linea.indexOf(TEXTO_AND, archivo1.fin);

		if (posAnd < 0) {
			return null;
		}

		int posPlugins = linea.indexOf(TEXTO_PLUGINS, posAnd);

		if (posPlugins < 0) {
			return null;
		}

		ParComillas archivo2 = extraerTextoEntreComillas(linea, posPlugins);

		if (archivo2 == null || archivo2.texto.isEmpty()) {
			return null;
		}

		return new ResultadoAmbiguo(plugin.texto, archivo1.texto, archivo2.texto);
	}

	/**
	 * Extrae texto entre:
	 * 
	 * `texto` o 'texto'
	 */
	private ParComillas extraerTextoEntreComillas(String texto, int desde) {

		for (int i = desde; i < texto.length(); i++) {

			char inicio = texto.charAt(i);

			if (inicio == '\'' || inicio == '`') {

				for (int j = i + 1; j < texto.length(); j++) {

					if (texto.charAt(j) == inicio) {

						String contenido = texto.substring(i + 1, j);

						return new ParComillas(contenido, j + 1);
					}
				}

				return null;
			}
		}

		return null;
	}

	private static class ParComillas {

		final String texto;
		final int fin;

		ParComillas(String texto, int fin) {
			this.texto = texto;
			this.fin = fin;
		}
	}

	private static class ResultadoAmbiguo {

		final String nombrePlugin;
		final String primerArchivo;
		final String segundoArchivo;

		ResultadoAmbiguo(String nombrePlugin, String primerArchivo, String segundoArchivo) {

			this.nombrePlugin = nombrePlugin;
			this.primerArchivo = primerArchivo;
			this.segundoArchivo = segundoArchivo;
		}
	}

	/**
	 * Reconstruye el mensaje HTML a partir de las listas internas. Se llama cada
	 * vez que se encuentra un nuevo caso de nombre ambiguo.
	 */
	private void reconstruirMensaje() {

		if (nombresPlugins.isEmpty()) {
			mensaje = "";
			return;
		}

		StringBuilder mensajeBuilder = new StringBuilder();

		for (int i = 0; i < nombresPlugins.size(); i++) {

			String enlace = i < enlaces.size() ? enlaces.get(i) : "";

			mensajeBuilder
					.append(MonitorDePID.idioma.mensajeNombrePluginAmbiguo(nombresPlugins.get(i),
							primerosArchivos.get(i), segundosArchivos.get(i)))
					.append(" ").append(enlace).append("<br>");
		}

		this.mensaje = mensajeBuilder.toString();
	}

	/**
	 * Extrae solo el nombre del archivo del path completo.
	 */
	private String extraerNombrePlugin(String path) {

		int indiceUltimaBarra = path.lastIndexOf("/");

		return (indiceUltimaBarra != -1) ? path.substring(indiceUltimaBarra + 1) : path;
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaNombrePluginAmbiguo();
	}

	/**
	 * Indica si el problema fue detectado.
	 */
	@Override
	public boolean activado() {
		return activado;
	}

	/**
	 * Prioridad del problema (alta).
	 */
	@Override
	public float prioridad() {
		return 700.0f;
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
		return MonitorDePID.idioma.nombreProblemaNombrePluginAmbiguo();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {

		Builder builder = new Builder(nombre());

		for (int i = 0; i < primerosArchivos.size(); i++) {

			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(primerosArchivos.get(i)));

			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(segundosArchivos.get(i)));
		}

		return builder.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "nombre_plugin_ambiguo";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_BASE, TEXTO_FILES };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}