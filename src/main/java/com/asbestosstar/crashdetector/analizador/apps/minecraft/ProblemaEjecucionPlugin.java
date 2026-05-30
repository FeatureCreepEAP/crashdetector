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
 * Detecta errores de ejecución en plugins de PocketMine-MP sin usar regex.
 */
public class ProblemaEjecucionPlugin implements Verificaciones {

	private boolean posibleEjecucion = false;
	private boolean activado = false;

	private String mensaje = "";
	private final List<String> nombresPlugins = new ArrayList<>();
	private final List<String> rutasPlugins = new ArrayList<>();
	private final List<String> enlaces = new ArrayList<>();

	private static final String TEXTO_ERROR = "Error: ";
	private static final String TEXTO_PLUGINS = "plugins/";
	private static final String TEXTO_EXCEPTION = "$EXCEPTION$ in ";

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(TEXTO_ERROR) && contenido.contains(TEXTO_PLUGINS)
				&& contenido.contains(TEXTO_EXCEPTION)) {
			posibleEjecucion = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleEjecucion || linea == null || linea.isEmpty()) {
			return;
		}

		int idxPlugins = linea.indexOf(TEXTO_PLUGINS);
		int idxException = linea.indexOf(TEXTO_EXCEPTION, idxPlugins);

		if (idxPlugins < 0 || idxException <= idxPlugins) {
			return;
		}

		String rutaPlugin = extraerRutaPlugin(linea, idxPlugins, idxException);
		String nombrePlugin = extraerNombrePlugin(rutaPlugin);

		if (rutaPlugin.isEmpty() || nombrePlugin.isEmpty() || contienePlugin(nombrePlugin, rutaPlugin)) {
			return;
		}

		rutasPlugins.add(rutaPlugin);
		nombresPlugins.add(nombrePlugin);
		enlaces.add(consola.agregarErrorALectador(numero_de_linea, this));

		activado = true;
		reconstruirMensaje();
	}

	private String extraerRutaPlugin(String linea, int inicio, int fin) {
		String ruta = linea.substring(inicio, fin).trim();
		int finComillas = ruta.indexOf('"');
		if (finComillas > 0) {
			ruta = ruta.substring(0, finComillas);
		}
		return ruta;
	}

	private String extraerNombrePlugin(String rutaPlugin) {
		int idxSlash = rutaPlugin.lastIndexOf('/');
		return idxSlash >= 0 ? rutaPlugin.substring(idxSlash + 1) : rutaPlugin;
	}

	private boolean contienePlugin(String nombre, String ruta) {
		for (int i = 0; i < nombresPlugins.size(); i++) {
			if (nombresPlugins.get(i).equalsIgnoreCase(nombre) && rutasPlugins.get(i).equalsIgnoreCase(ruta)) {
				return true;
			}
		}
		return false;
	}

	private void reconstruirMensaje() {
		if (nombresPlugins.isEmpty()) {
			mensaje = "";
			activado = false;
			return;
		}

		if (nombresPlugins.size() > 1) {
			mensaje = MonitorDePID.idioma.mensajePluginEjecucionPlural(nombresPlugins);
		} else {
			mensaje = MonitorDePID.idioma.mensajePluginEjecucionSingular(nombresPlugins.get(0));
		}

		StringBuilder sb = new StringBuilder(mensaje);
		for (String enlace : enlaces) {
			if (enlace != null && !enlace.isEmpty()) {
				sb.append(" ").append(enlace);
			}
		}
		mensaje = sb.toString();
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaEjecucionPlugin();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 700.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaPluginEjecucion();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		for (int i = 0; i < nombresPlugins.size(); i++) {
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(rutasPlugins.get(i)));
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionDiferentePlugin(nombresPlugins.get(i)));
		}
		return builder.construir();
	}

	@Override
	public String id() {
		return "ejecucion_plugin";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null)
			return false;
		return trazo.trace.contains(TEXTO_PLUGINS) && trazo.trace.contains(TEXTO_EXCEPTION);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}