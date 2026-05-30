package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta plugins de PocketMine-MP que no son compatibles con la
 * versión actual del servidor.Gracias a Aternos por que esta es una
 * implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaPluginIncompatible implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private final List<String> nombresPlugins = new ArrayList<String>();

	private static final String PREFIJO = "Could not load plugin '";
	private static final String SUFIJO_API = "': Incompatible API version";
	private static final String SUFIJO_PROTOCOLO = "': Incompatible network protocol version";

	/**
	 * Verifica si el log contiene plugins incompatibles con PocketMine-MP.
	 */
	@Override
	public void verificar(Consola consola) {

		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String contenido = consola.contenido_verificar;

		// Chequeo global barato:
		// si el log no contiene la frase base ni ningún motivo incompatible,
		// no hace falta escanear buscando nombres de plugins.
		if (!contenido.contains(PREFIJO)
				|| (!contenido.contains(SUFIJO_API) && !contenido.contains(SUFIJO_PROTOCOLO))) {
			return;
		}

		extraerPluginsIncompatibles(contenido, SUFIJO_API);
		extraerPluginsIncompatibles(contenido, SUFIJO_PROTOCOLO);

		if (!nombresPlugins.isEmpty()) {
			if (nombresPlugins.size() > 1) {
				this.mensaje = MonitorDePID.idioma.mensajePluginIncompatiblePlural(nombresPlugins);
			} else {
				this.mensaje = MonitorDePID.idioma.mensajePluginIncompatibleSingular(nombresPlugins.get(0));
			}
			activado = true;
		}
	}

	private void extraerPluginsIncompatibles(String contenido, String sufijo) {
		int desde = 0;

		while (true) {
			int inicioPrefijo = contenido.indexOf(PREFIJO, desde);
			if (inicioPrefijo < 0) {
				return;
			}

			int inicioNombre = inicioPrefijo + PREFIJO.length();
			int finNombre = contenido.indexOf(sufijo, inicioNombre);

			if (finNombre > inicioNombre) {
				String nombrePlugin = contenido.substring(inicioNombre, finNombre).trim();

				if (!nombrePlugin.isEmpty() && !nombresPlugins.contains(nombrePlugin)) {
					nombresPlugins.add(nombrePlugin);
				}

				desde = finNombre + sufijo.length();
			} else {
				desde = inicioNombre;
			}
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

}