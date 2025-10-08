package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta plugins de PocketMine-MP que no son compatibles con la
 * versión actual del servidor.Gracias a Aternos por que esta es una
 * implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaPluginIncompatible implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private final List<String> nombresPlugins = new ArrayList<>();

	/**
	 * Verifica si el log contiene plugins incompatibles con PocketMine-MP.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Patrones para detectar plugins con API o protocolo incompatible
		Pattern[] patrones = { Pattern.compile("Could not load plugin '([^']+)'\\: Incompatible API version"),
				Pattern.compile("Could not load plugin '([^']+)'\\: Incompatible network protocol version") };

		for (Pattern patron : patrones) {
			Matcher coincidencia = patron.matcher(contenido);
			while (coincidencia.find()) {
				String nombrePlugin = coincidencia.group(1).trim();
				if (!nombrePlugin.isEmpty() && !nombresPlugins.contains(nombrePlugin)) {
					nombresPlugins.add(nombrePlugin);
				}
			}
		}

		if (!nombresPlugins.isEmpty()) {
			if (nombresPlugins.size() > 1) {
				this.mensaje = MonitorDePID.idioma.mensajePluginIncompatiblePlural(nombresPlugins);
			} else {
				this.mensaje = MonitorDePID.idioma.mensajePluginIncompatibleSingular(nombresPlugins.get(0));
			}
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaPluginIncompatible();
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
		return 900.0f;
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
		return MonitorDePID.idioma.nombreProblemaPluginIncompatible();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		for (String plugin : nombresPlugins) {
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionDiferentePlugin(plugin));
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(plugin));
		}

		return builder.construir();
	}
	
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "plugin_incompatible";
	}
}