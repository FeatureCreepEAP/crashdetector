package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta plugins con versiones de API no compatibles. Gracias a
 * Aternos porque esta es una implementacion de su codex:
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaVersionAPIIncompatible implements Verificaciones {

	private boolean posibleVersionAPIIncompatible = false;
	private boolean activado = false;

	private String mensaje = "";

	private final List<String> nombresPlugins = new ArrayList<>();
	private final List<String> versionesAPI = new ArrayList<>();
	private final List<String> enlaces = new ArrayList<>();

	// Estado para formatos donde el plugin aparece en una linea y la version en
	// otra.
	private String pluginPendiente = null;
	private int lineaPluginPendiente = -1;
	private boolean esperandoVersionAPI = false;
	private int lineasEsperandoVersion = 0;

	private static final String TEXTO_COULD_NOT_LOAD_OLD = "Could not load 'plugins";
	private static final String TEXTO_COULD_NOT_LOAD_PLUGIN = "Could not load plugin '";
	private static final String TEXTO_INVALID_API = "org.bukkit.plugin.InvalidPluginException: Unsupported API version ";
	private static final String TEXTO_PLUGIN_API_VERSION = "Plugin API version ";
	private static final String TEXTO_LOWER_THAN_MINIMUM = " is lower than the minimum allowed version";
	private static final String TEXTO_NOT_SUPPORTED = " is not supported by this server";

	/**
	 * Verificacion global ligera.
	 *
	 * Se ejecuta primero. No se limpian listas aqui porque esta verificacion puede
	 * ejecutarse sobre varios archivos de log con la misma instancia.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if ((contenido.contains("Could not load") && contenido.contains("Unsupported API version"))
				|| (contenido.contains("Could not load plugin") && contenido.contains("Plugin API version"))) {
			posibleVersionAPIIncompatible = true;
		}
	}

	/**
	 * Verificacion por linea.
	 *
	 * Detecta el plugin exacto, la version API exacta y agrega enlace a la linea.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleVersionAPIIncompatible || linea == null || linea.isEmpty()) {
			return;
		}

		String l = linea.trim();

		// Evitar que un plugin pendiente se quede activo por demasiadas lineas.
		if (esperandoVersionAPI) {
			lineasEsperandoVersion++;
			if (lineasEsperandoVersion > 8) {
				limpiarPendiente();
			}
		}

		// Formato antiguo:
		// Could not load 'plugins/Plugin.jar' in folder 'plugins'
		if (l.contains("Could not load 'plugins")) {
			String plugin = extraerPluginFormatoViejo(l);

			if (!plugin.isEmpty()) {
				pluginPendiente = plugin;
				lineaPluginPendiente = numero_de_linea;
				esperandoVersionAPI = true;
				lineasEsperandoVersion = 0;
			}

			return;
		}

		// Formato nuevo:
		// Could not load plugin 'Plugin.jar' in folder ...
		if (l.contains(TEXTO_COULD_NOT_LOAD_PLUGIN)) {
			String plugin = extraerPluginEntreComillas(l, TEXTO_COULD_NOT_LOAD_PLUGIN);

			if (!plugin.isEmpty()) {
				pluginPendiente = extraerNombrePlugin(plugin);
				lineaPluginPendiente = numero_de_linea;
				esperandoVersionAPI = true;
				lineasEsperandoVersion = 0;
			}

			return;
		}

		// Version antigua Bukkit/Spigot:
		// org.bukkit.plugin.InvalidPluginException: Unsupported API version 1.20
		if (esperandoVersionAPI && l.contains(TEXTO_INVALID_API)) {
			String version = extraerVersionDespuesDe(l, TEXTO_INVALID_API);

			if (!version.isEmpty()) {
				registrarPlugin(consola, pluginPendiente, version, elegirLineaParaEnlace(numero_de_linea));
				limpiarPendiente();
			}

			return;
		}

		// Formato Paper:
		// Plugin API version 1.20 is lower than the minimum allowed version
		// Plugin API version 1.20 is not supported by this server
		if (esperandoVersionAPI && l.contains(TEXTO_PLUGIN_API_VERSION)) {
			String version = extraerVersionAPIPlugin(l);

			if (!version.isEmpty() && (l.contains(TEXTO_LOWER_THAN_MINIMUM) || l.contains(TEXTO_NOT_SUPPORTED))) {
				registrarPlugin(consola, pluginPendiente, version, elegirLineaParaEnlace(numero_de_linea));
				limpiarPendiente();
			}
		}
	}

	/**
	 * Registra un plugin problemático evitando duplicados simples.
	 */
	private void registrarPlugin(Consola consola, String plugin, String version, int numero_de_linea) {
		if (plugin == null || plugin.isEmpty() || version == null || version.isEmpty()) {
			return;
		}

		for (int i = 0; i < nombresPlugins.size(); i++) {
			if (nombresPlugins.get(i).equalsIgnoreCase(plugin) && versionesAPI.get(i).equalsIgnoreCase(version)) {
				return;
			}
		}

		nombresPlugins.add(plugin);
		versionesAPI.add(version);
		enlaces.add(consola.agregarErrorALectador(numero_de_linea, this));

		reconstruirMensaje();
	}

	/**
	 * Preferimos enlazar la linea del error de version, pero si existe una linea
	 * principal del plugin pendiente, puede usarse esa linea.
	 */
	private int elegirLineaParaEnlace(int lineaActual) {
		if (lineaPluginPendiente >= 0) {
			return lineaPluginPendiente;
		}

		return lineaActual;
	}

	private void limpiarPendiente() {
		pluginPendiente = null;
		lineaPluginPendiente = -1;
		esperandoVersionAPI = false;
		lineasEsperandoVersion = 0;
	}

	/**
	 * Extrae plugin de: Could not load 'plugins/Plugin.jar' in folder ...
	 *
	 * Tambien acepta plugins\Plugin.jar.
	 */
	private String extraerPluginFormatoViejo(String linea) {
		int inicio = linea.indexOf(TEXTO_COULD_NOT_LOAD_OLD);

		if (inicio < 0) {
			return "";
		}

		int primeraComilla = linea.indexOf('\'', inicio);
		if (primeraComilla < 0) {
			return "";
		}

		int segundaComilla = linea.indexOf('\'', primeraComilla + 1);
		if (segundaComilla <= primeraComilla + 1) {
			return "";
		}

		String ruta = linea.substring(primeraComilla + 1, segundaComilla).trim();
		return extraerNombrePlugin(ruta);
	}

	/**
	 * Extrae el texto entre comillas después de cierto prefijo.
	 */
	private String extraerPluginEntreComillas(String linea, String prefijo) {
		int inicio = linea.indexOf(prefijo);

		if (inicio < 0) {
			return "";
		}

		int inicioNombre = inicio + prefijo.length();
		int finNombre = linea.indexOf('\'', inicioNombre);

		if (finNombre <= inicioNombre) {
			return "";
		}

		return linea.substring(inicioNombre, finNombre).trim();
	}

	/**
	 * Extrae una version después de un texto fijo.
	 */
	private String extraerVersionDespuesDe(String linea, String prefijo) {
		int inicio = linea.indexOf(prefijo);

		if (inicio < 0) {
			return "";
		}

		inicio += prefijo.length();

		while (inicio < linea.length() && Character.isWhitespace(linea.charAt(inicio))) {
			inicio++;
		}

		int fin = leerFinVersion(linea, inicio);

		if (fin <= inicio) {
			return "";
		}

		return linea.substring(inicio, fin).trim();
	}

	/**
	 * Extrae version de: Plugin API version 1.20 is ...
	 */
	private String extraerVersionAPIPlugin(String linea) {
		return extraerVersionDespuesDe(linea, TEXTO_PLUGIN_API_VERSION);
	}

	/**
	 * Lee versiones tipo: 1 1.20 1.20.6
	 */
	private int leerFinVersion(String texto, int inicio) {
		int i = inicio;

		while (i < texto.length()) {
			char c = texto.charAt(i);

			if (Character.isDigit(c) || c == '.') {
				i++;
			} else {
				break;
			}
		}

		return i;
	}

	/**
	 * Reconstruye el mensaje HTML basado en las listas de plugins/versiones.
	 */
	private void reconstruirMensaje() {
		if (nombresPlugins.isEmpty()) {
			mensaje = "";
			activado = false;
			return;
		}

		StringBuilder mensajeBuilder = new StringBuilder();

		for (int i = 0; i < nombresPlugins.size(); i++) {
			mensajeBuilder.append(
					MonitorDePID.idioma.mensajeVersionAPIIncompatible(nombresPlugins.get(i), versionesAPI.get(i)));

			String enlace = enlaces.size() > i ? enlaces.get(i) : "";
			if (enlace != null && !enlace.isEmpty()) {
				mensajeBuilder.append(" ").append(enlace);
			}

			mensajeBuilder.append("<br><br>");
		}

		this.mensaje = mensajeBuilder.toString();
		activado = true;
	}

	/**
	 * Extrae solo el nombre del archivo del path completo.
	 */
	private String extraerNombrePlugin(String path) {
		if (path == null || path.isEmpty()) {
			return "";
		}

		int indiceUltimaBarraNormal = path.lastIndexOf("/");
		int indiceUltimaBarraWindows = path.lastIndexOf("\\");
		int indiceUltimaBarra = Math.max(indiceUltimaBarraNormal, indiceUltimaBarraWindows);

		return (indiceUltimaBarra != -1) ? path.substring(indiceUltimaBarra + 1) : path;
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaVersionAPIIncompatible();
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
		return 800.0f;
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
		return MonitorDePID.idioma.nombreProblemaVersionAPIIncompatible();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		for (int i = 0; i < nombresPlugins.size(); i++) {
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionServidor(versionesAPI.get(i)));
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(nombresPlugins.get(i)));
		}

		return builder.construir();
	}

	@Override
	public String id() {
		return "problema_version_api_incompatible";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}