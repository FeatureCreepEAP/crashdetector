package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta múltiples plugins con dependencias faltantes. Gracias a
 * Aternos por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaDependenciaPlugin implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private final Map<String, List<String>> plugins = new HashMap<>();
	private final Map<String, String> enlacesPorPlugin = new HashMap<>();
	private String nombrePluginActual = "";

	/**
	 * Verifica si el log contiene errores de dependencias faltantes.
	 * 
	 * En esta implementación, el análisis real se realiza línea a línea en
	 * {@link #verificar(Consola, String, int)}, aprovechando el estado interno para
	 * relacionar la línea de carga del plugin con las líneas de dependencias.
	 */
	@Override
	public void verificar(Consola consola) {
		// No se recorre el contenido completo aquí; el escaneo se hace en el método
		// verificar(Consola, String, int) que se llama por cada línea.
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// 1. Detecta carga fallida de plugin
		if (linea.contains("Could not load 'plugins/")) {
			extraerNombrePluginDesdeRuta(linea, numero_de_linea, consola);
		}
		// 2. Detecta dependencias múltiples
		else if (linea.contains("Unknown/missing dependency plugins:")) {
			procesarLineaDependenciaMultiple(linea);
			reconstruirMensaje();
		}
		// 3. Detecta dependencia única
		else if (linea.contains("Unknown dependency")) {
			procesarLineaDependenciaUnica(linea);
			reconstruirMensaje();
		}
	}

	/**
	 * Extrae el nombre del plugin desde la ruta en la línea de error.
	 */
	private void extraerNombrePluginDesdeRuta(String linea, int numeroLinea, Consola consola) {
		int primerApostrofe = linea.indexOf('\'');
		int segundoApostrofe = linea.indexOf('\'', primerApostrofe + 1);

		if (primerApostrofe >= 0 && segundoApostrofe > primerApostrofe) {
			String ruta = linea.substring(primerApostrofe + 1, segundoApostrofe);
			this.nombrePluginActual = extraerNombrePlugin(ruta);

			// Registrar el error con el enlace
			if (!nombrePluginActual.isEmpty()) {
				String enlace = consola.agregarErrorALectador(numeroLinea, this);
				enlacesPorPlugin.put(nombrePluginActual, enlace);
			}
		}
	}

	/**
	 * Procesa líneas con múltiples dependencias faltantes.
	 */
	private void procesarLineaDependenciaMultiple(String linea) {
		if (nombrePluginActual.isEmpty())
			return;

		int inicio = linea.indexOf('[') + 1;
		int fin = linea.indexOf(']');

		if (inicio > 0 && fin > inicio) {
			String depsTexto = linea.substring(inicio, fin);
			String[] deps = depsTexto.split(", ");

			plugins.computeIfAbsent(nombrePluginActual, k -> new ArrayList<>());

			for (String dep : deps) {
				plugins.get(nombrePluginActual).add(dep.trim());
			}

			nombrePluginActual = ""; // Reinicia para evitar duplicados
		}
	}

	/**
	 * Procesa líneas con una sola dependencia faltante.
	 */
	private void procesarLineaDependenciaUnica(String linea) {
		if (nombrePluginActual.isEmpty())
			return;

		int inicioDep = linea.indexOf("Unknown dependency ") + "Unknown dependency ".length();
		int finDep = linea.indexOf(".", inicioDep);

		if (inicioDep > 0 && finDep > inicioDep) {
			String dependencia = linea.substring(inicioDep, finDep).trim();

			plugins.computeIfAbsent(nombrePluginActual, k -> new ArrayList<>());
			plugins.get(nombrePluginActual).add(dependencia);

			nombrePluginActual = ""; // Reinicia para evitar duplicados
		}
	}

	/**
	 * Reconstruye el mensaje final a partir del mapa de plugins y dependencias. Se
	 * llama cada vez que se añaden dependencias para mantener el mensaje al día.
	 */
	private void reconstruirMensaje() {
		if (plugins.isEmpty()) {
			mensaje = "";
			activado = false;
			return;
		}

		StringBuilder mensajeBuilder = new StringBuilder();
		boolean primerPlugin = true;

		for (Map.Entry<String, List<String>> entry : plugins.entrySet()) {
			String nombrePlugin = entry.getKey();
			List<String> dependencias = entry.getValue();
			String enlace = enlacesPorPlugin.getOrDefault(nombrePlugin, "");

			if (!primerPlugin) {
				mensajeBuilder.append("<br>");
			}

			if (dependencias.size() == 1) {
				mensajeBuilder
						.append(MonitorDePID.idioma.mensajeDependenciaPluginUnica(nombrePlugin, dependencias.get(0)));
			} else {
				mensajeBuilder
						.append(MonitorDePID.idioma.mensajeDependenciaPluginMultiples(nombrePlugin, dependencias));
			}

			mensajeBuilder.append(" ").append(enlace);
			primerPlugin = false;
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
		return new ProblemaDependenciaPlugin();
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
		return MonitorDePID.idioma.nombreProblemaDependenciaPlugin();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		for (Map.Entry<String, List<String>> entry : plugins.entrySet()) {
			String nombrePlugin = entry.getKey();
			List<String> dependencias = entry.getValue();

			// Solución para eliminar el plugin
			if (!nombrePlugin.isEmpty()) {
				builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(nombrePlugin));
			}

			// Soluciones para instalar cada dependencia faltante
			for (String dependencia : dependencias) {
				builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarPlugin(dependencia));
			}
		}

		return builder.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "dependencia_plugin";
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
