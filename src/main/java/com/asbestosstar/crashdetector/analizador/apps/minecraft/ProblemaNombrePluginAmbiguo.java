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
 * Clase que detecta plugins con nombres ambiguos en la carpeta
 * 'plugins'.Gracias a Aternos por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaNombrePluginAmbiguo implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private List<String> nombresPlugins = new ArrayList<>();
	private List<String> primerosArchivos = new ArrayList<>();
	private List<String> segundosArchivos = new ArrayList<>();
	private final List<String> enlaces = new ArrayList<>();

	// Patrón mejorado para manejar cualquier combinación de comillas (cacheado)
	private static final Pattern PATRON = Pattern.compile(
			"Ambiguous plugin name [`']([^`']*)[`'].*?files [`']plugins/([^`']*)[`'].*?and [`']plugins/([^`']*)[`']");

	/**
	 * Verifica si el log contiene errores de nombre ambiguo de plugins.
	 * 
	 * En esta versión, el análisis real se hace línea a línea en
	 * {@link #verificar(Consola, String, int)}, aprovechando que el motor de
	 * análisis llama a ese método para cada línea de la consola.
	 */
	@Override
	public void verificar(Consola consola) {
		// No se recorre el contenido completo aquí; cada línea se procesa en
		// verificar(Consola, String, int).
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (linea == null) {
			return;
		}

		Matcher coincidencia = PATRON.matcher(linea.trim());

		if (coincidencia.find()) {
			nombresPlugins.add(coincidencia.group(1));
			primerosArchivos.add(extraerNombrePlugin(coincidencia.group(2)));
			segundosArchivos.add(extraerNombrePlugin(coincidencia.group(3)));
			String enlace = consola.agregarErrorALectador(numero_de_linea, this);
			enlaces.add(enlace);

			reconstruirMensaje();
			activado = true;
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
