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
 * Clase que detecta plugins que no son compatibles con ticking regional en
 * Folia. Gracias a Aternos porque esta es una implementacion de su codex:
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaTickingRegionalPlugin implements Verificaciones {

	private boolean posibleTickingRegionalPlugin = false;
	private boolean activado = false;

	private String mensaje = "";

	private final List<String> nombresPlugins = new ArrayList<>();
	private final List<String> rutasPlugins = new ArrayList<>();
	private final List<String> enlaces = new ArrayList<>();

	private String pluginPendiente = "";
	private String carpetaPendiente = "";
	private int lineaPluginPendiente = -1;
	private boolean esperandoMensajeTicking = false;
	private int lineasEsperando = 0;

	private static final String TEXTO_COULD_NOT_LOAD_PLUGIN = "Could not load plugin '";
	private static final String TEXTO_IN_FOLDER = "in folder '";
	private static final String TEXTO_TICKING_REGIONAL = "not marked as supporting regionised multithreading";

	/**
	 * Verificacion global ligera.
	 *
	 * Se ejecuta primero. No se limpian listas aquí porque esta verificación puede
	 * ejecutarse sobre varios archivos de log con la misma instancia.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(TEXTO_COULD_NOT_LOAD_PLUGIN) && contenido.contains(TEXTO_TICKING_REGIONAL)) {
			posibleTickingRegionalPlugin = true;
		}
	}

	/**
	 * Verificacion por linea.
	 *
	 * Detecta el plugin exacto y agrega enlace a la linea del error.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleTickingRegionalPlugin || linea == null || linea.isEmpty()) {
			return;
		}

		String l = linea.trim();

		// 1) Detectar la linea principal:
		// Could not load plugin 'plugins/worldedit.jar' in folder 'plugins'
		if (l.contains(TEXTO_COULD_NOT_LOAD_PLUGIN)) {
			DatosPlugin datos = extraerDatosPlugin(l);

			if (datos != null && !datos.rutaCompleta.isEmpty()) {
				pluginPendiente = datos.rutaCompleta;
				carpetaPendiente = datos.carpeta;
				lineaPluginPendiente = numero_de_linea;
				esperandoMensajeTicking = true;
				lineasEsperando = 0;

				// Si el mensaje completo está en la misma línea, registrar ahora.
				if (l.contains(TEXTO_TICKING_REGIONAL)) {
					registrarPlugin(consola, pluginPendiente, lineaPluginPendiente);
					limpiarPendiente();
				}
			}

			return;
		}

		// 2) Si el mensaje de Folia aparece unas lineas después.
		if (esperandoMensajeTicking) {
			lineasEsperando++;

			if (l.contains(TEXTO_TICKING_REGIONAL)) {
				registrarPlugin(consola, pluginPendiente,
						lineaPluginPendiente >= 0 ? lineaPluginPendiente : numero_de_linea);
				limpiarPendiente();
				return;
			}

			// Evitar que un plugin pendiente sobreviva demasiado tiempo.
			if (lineasEsperando > 12) {
				limpiarPendiente();
			}
		}
	}

	/**
	 * Extrae datos de:
	 *
	 * Could not load plugin 'plugins/plugin.jar' in folder 'plugins'
	 *
	 * Sin Pattern/Matcher.
	 */
	private DatosPlugin extraerDatosPlugin(String linea) {
		int inicio = linea.indexOf(TEXTO_COULD_NOT_LOAD_PLUGIN);

		if (inicio < 0) {
			return null;
		}

		int inicioRuta = inicio + TEXTO_COULD_NOT_LOAD_PLUGIN.length();
		int finRuta = linea.indexOf('\'', inicioRuta);

		if (finRuta <= inicioRuta) {
			return null;
		}

		String rutaCompleta = linea.substring(inicioRuta, finRuta).trim();

		String carpeta = "";
		int inicioFolder = linea.indexOf(TEXTO_IN_FOLDER, finRuta);

		if (inicioFolder >= 0) {
			int inicioCarpeta = inicioFolder + TEXTO_IN_FOLDER.length();
			int finCarpeta = linea.indexOf('\'', inicioCarpeta);

			if (finCarpeta > inicioCarpeta) {
				carpeta = linea.substring(inicioCarpeta, finCarpeta).trim();
			}
		}

		return new DatosPlugin(rutaCompleta, carpeta);
	}

	/**
	 * Registra el plugin afectado evitando duplicados.
	 */
	private void registrarPlugin(Consola consola, String rutaCompleta, int numero_de_linea) {
		if (rutaCompleta == null || rutaCompleta.isEmpty()) {
			return;
		}

		String nombrePlugin = extraerNombrePlugin(rutaCompleta);

		if (nombrePlugin.isEmpty()) {
			return;
		}

		if (contienePlugin(nombrePlugin, rutaCompleta)) {
			return;
		}

		nombresPlugins.add(nombrePlugin);
		rutasPlugins.add(rutaCompleta);
		enlaces.add(consola.agregarErrorALectador(numero_de_linea, this));

		activado = true;
		reconstruirMensaje();
	}

	private boolean contienePlugin(String nombrePlugin, String rutaCompleta) {
		for (int i = 0; i < nombresPlugins.size(); i++) {
			if (nombresPlugins.get(i).equalsIgnoreCase(nombrePlugin)
					&& rutasPlugins.get(i).equalsIgnoreCase(rutaCompleta)) {
				return true;
			}
		}

		return false;
	}

	private void limpiarPendiente() {
		pluginPendiente = "";
		carpetaPendiente = "";
		lineaPluginPendiente = -1;
		esperandoMensajeTicking = false;
		lineasEsperando = 0;
	}

	/**
	 * Reconstruye el mensaje HTML basado en las listas de plugins.
	 */
	private void reconstruirMensaje() {
		if (nombresPlugins.isEmpty()) {
			mensaje = "";
			activado = false;
			return;
		}

		if (nombresPlugins.size() > 1) {
			mensaje = MonitorDePID.idioma.mensajePluginTickingRegionalPlural(nombresPlugins);
		} else {
			mensaje = MonitorDePID.idioma.mensajePluginTickingRegionalSingular(nombresPlugins.get(0));
		}

		StringBuilder sb = new StringBuilder(mensaje);

		for (String enlace : enlaces) {
			if (enlace != null && !enlace.isEmpty()) {
				sb.append(" ").append(enlace);
			}
		}

		mensaje = sb.toString();
	}

	/**
	 * Extrae el nombre del plugin desde la ruta completa.
	 */
	private String extraerNombrePlugin(String ruta) {
		if (ruta == null || ruta.isEmpty()) {
			return "";
		}

		int indiceUltimaBarraNormal = ruta.lastIndexOf("/");
		int indiceUltimaBarraWindows = ruta.lastIndexOf("\\");
		int indiceUltimaBarra = Math.max(indiceUltimaBarraNormal, indiceUltimaBarraWindows);

		return (indiceUltimaBarra != -1) ? ruta.substring(indiceUltimaBarra + 1) : ruta;
	}

	private static class DatosPlugin {
		private final String rutaCompleta;
		private final String carpeta;

		private DatosPlugin(String rutaCompleta, String carpeta) {
			this.rutaCompleta = rutaCompleta;
			this.carpeta = carpeta;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaTickingRegionalPlugin();
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
		return MonitorDePID.idioma.nombreProblemaPluginTickingRegional();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		// Soluciones para cada plugin afectado
		for (int i = 0; i < nombresPlugins.size(); i++) {
			String nombre = nombresPlugins.get(i);
			String ruta = rutasPlugins.get(i);

			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionDiferentePlugin(nombre));
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(ruta));
		}

		// Solución general para cambiar de software
		builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarSoftwareSinTickingRegional("Paper"));

		return builder.construir();
	}

	@Override
	public String id() {
		return "ticking_regional_plugin";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return trazo.trace.contains(TEXTO_COULD_NOT_LOAD_PLUGIN) && trazo.trace.contains(TEXTO_TICKING_REGIONAL);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}