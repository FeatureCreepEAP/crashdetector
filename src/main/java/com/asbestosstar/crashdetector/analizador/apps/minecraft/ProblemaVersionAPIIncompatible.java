package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta plugins con versiones de API no compatibles.Gracias a
 * Aternos por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaVersionAPIIncompatible implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private final List<String> nombresPlugins = new ArrayList<>();
	private final List<String> versionesAPI = new ArrayList<>();

	// Patrones para versiones antiguas
	private static final Pattern PATRON_VIEJO = Pattern.compile(
			"Could not load 'plugins[/\\\\]([^']+)' in folder '?([^']*)'?\\n"
					+ "org\\.bukkit\\.plugin\\.InvalidPluginException: Unsupported API version ([\\d\\.]+)",
			Pattern.DOTALL);

	private static final Pattern PATRON_NUEVO = Pattern.compile(
			"Could not load plugin '((?!\\.jar).*\\.jar)' in folder '?([^']*)'?\\n"
					+ "org\\.bukkit\\.plugin\\.InvalidPluginException: Unsupported API version ([\\d\\.]+)",
			Pattern.DOTALL);

	private static final Pattern PATRON_PAPER_NUEVO = Pattern
			.compile(
					"Could not load plugin '([^']+)' in folder.*?\\n.*?\\n.*?\\n"
							+ "Plugin API version ([\\d\\.]+) is lower than the minimum allowed version",
					Pattern.DOTALL);

	private static final Pattern PATRON_PAPER_MENSAJE = Pattern
			.compile("Could not load plugin '([^']+)' in folder.*?\\n.*?\\n.*?\\n"
					+ "Plugin API version ([\\d\\.]+) is not supported by this server", Pattern.DOTALL);

	// Estado para el procesamiento línea a línea del nuevo formato de Paper
	// (1.20.6+)
	private String pluginPendiente = null;
	private boolean esperandoVersionAPI = false;

	/**
	 * Verifica si el log contiene errores de versión de API incompatible.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// 1. Usar regex para formatos antiguos
		procesarCoincidenciasRegex(PATRON_VIEJO.matcher(contenido));
		procesarCoincidenciasRegex(PATRON_NUEVO.matcher(contenido));
		procesarCoincidenciasRegex(PATRON_PAPER_NUEVO.matcher(contenido));
		procesarCoincidenciasRegex(PATRON_PAPER_MENSAJE.matcher(contenido));

		// 2. El nuevo formato de Paper 1.20.6+ se procesa ahora línea a línea
		// en verificar(Consola, String, int). Este método ya no recorre las líneas
		// para ese caso por motivos de rendimiento.

		// 3. Generar mensaje si se encontraron plugins problemáticos (por regex)
		reconstruirMensaje();
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (linea == null) {
			return;
		}

		String l = linea.trim();

		// 1) Detectar la línea principal del nuevo formato Paper 1.20.6+
		// "Could not load plugin 'X.jar' in folder ..."
		if (l.contains("Could not load plugin") && l.contains(".jar")) {
			int inicioNombre = l.indexOf('\'') + 1;
			int finNombre = l.indexOf('\'', inicioNombre);
			if (inicioNombre > 0 && finNombre > inicioNombre) {
				String nombrePlugin = extraerNombrePlugin(l.substring(inicioNombre, finNombre));
				if (!nombrePlugin.isEmpty()) {
					pluginPendiente = nombrePlugin;
					esperandoVersionAPI = true;
				}
			}
			return;
		}

		// 2) Si estamos esperando la línea con "Plugin API version ..."
		if (esperandoVersionAPI && l.contains("Plugin API version")) {
			int inicioVersion = l.indexOf("Plugin API version ") + "Plugin API version ".length();
			if (inicioVersion <= 0 || inicioVersion >= l.length()) {
				return;
			}
			int finVersion = l.indexOf(" ", inicioVersion);
			if (finVersion == -1) {
				finVersion = l.length();
			}

			String version = l.substring(inicioVersion, finVersion).trim();

			if (pluginPendiente != null && !pluginPendiente.isEmpty() && !version.isEmpty()) {
				nombresPlugins.add(pluginPendiente);
				versionesAPI.add(version);
				pluginPendiente = null;
				esperandoVersionAPI = false;
				reconstruirMensaje();
			}
		}
	}

	/**
	 * Procesa coincidencias usando regex.
	 */
	private void procesarCoincidenciasRegex(Matcher coincidencia) {
		while (coincidencia.find()) {
			if (coincidencia.groupCount() >= 2) {
				nombresPlugins.add(extraerNombrePlugin(coincidencia.group(1)));
				versionesAPI.add(coincidencia.group(2));
			}
		}
	}

	/**
	 * Procesa el nuevo formato de Paper 1.20.6+ sin usar regex específicos.
	 *
	 * En la versión actual, el procesamiento de este formato se realiza línea a
	 * línea en {@link #verificar(Consola, String, int)}, por lo que este método se
	 * mantiene únicamente por compatibilidad y ya no realiza trabajo adicional.
	 */
	@SuppressWarnings("unused")
	private void procesarFormatoPaperNuevo(Consola consola) {
		// Lógica migrada a verificar(Consola, String, int).
	}

	/**
	 * Procesa errores específicos del nuevo formato de Paper 1.20.6+
	 *
	 * En la versión actual se utiliza un pequeño estado (pluginPendiente /
	 * esperandoVersionAPI) en el análisis por línea, por lo que este método ya no
	 * es necesario y se mantiene solo por compatibilidad histórica.
	 */
	@SuppressWarnings("unused")
	private void procesarErrorPaper1206(String[] lineas, int indiceLinea) {
		// Lógica migrada a verificar(Consola, String, int).
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
					MonitorDePID.idioma.mensajeVersionAPIIncompatible(nombresPlugins.get(i), versionesAPI.get(i)))
					.append("<br><br>");
		}

		this.mensaje = mensajeBuilder.toString();
		activado = true;
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
	 * Prioridad del problema (alta).
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
		// TODO Auto-generated method stub
		return "problema_version_api_incompatible";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
