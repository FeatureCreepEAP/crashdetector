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
 * Clase que detecta errores de ejecución en plugins de PocketMine-MP. Gracias a
 * Aternos por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaEjecucionPlugin implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private final List<String> nombresPlugins = new ArrayList<>();
	private final List<String> rutasPlugins = new ArrayList<>();

	/**
	 * Verifica si el log contiene errores de ejecución en plugins de PocketMine-MP.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Patrón para detectar errores de ejecución en plugins de PocketMine
		Pattern patron = Pattern.compile(
				"Error: \".*?\" \\$EXCEPTION\\$ in \"(plugins/[^\\.]+\\.phar)(?:/[^/\\\"]+)*\" at line \\d+",
				Pattern.DOTALL);

		Matcher coincidencia = patron.matcher(contenido);

		while (coincidencia.find()) {
			String rutaPlugin = coincidencia.group(1); // plugins/NombrePlugin.phar
			String nombrePlugin = coincidencia.group(2); // NombrePlugin

			if (!nombrePlugin.isEmpty() && !nombresPlugins.contains(nombrePlugin)) {
				nombresPlugins.add(nombrePlugin);
				rutasPlugins.add(rutaPlugin);
			}
		}

		if (!nombresPlugins.isEmpty()) {
			if (nombresPlugins.size() > 1) {
				this.mensaje = MonitorDePID.idioma.mensajePluginEjecucionPlural(nombresPlugins);
			} else {
				this.mensaje = MonitorDePID.idioma.mensajePluginEjecucionSingular(nombresPlugins.get(0));
			}
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaEjecucionPlugin();
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
		return MonitorDePID.idioma.nombreProblemaPluginEjecucion();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		for (int i = 0; i < nombresPlugins.size(); i++) {
			String nombre = nombresPlugins.get(i);
			String ruta = rutasPlugins.get(i);
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(ruta));
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionDiferentePlugin(nombre));
		}

		return builder.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "ejecucion_plugin";
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
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"+this.getClass().getSimpleName()+".java";
	}
	
	
	
	
	

}