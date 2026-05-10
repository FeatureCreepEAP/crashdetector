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
 * Detecta plugins con dependencias faltantes en PocketMine-MP sin usar regex.
 */
public class ProblemaDependenciaPluginPocketMine implements Verificaciones {

	private boolean posibleDependenciaFaltante = false;
	private boolean activado = false;

	private String mensaje = "";
	private final List<String> nombresPlugins = new ArrayList<>();
	private final List<String> dependencias = new ArrayList<>();
	private final List<String> enlaces = new ArrayList<>();

	private static final String TEXTO_PLUGIN = "Could not load plugin '";
	private static final String TEXTO_DEPENDENCIA = "': Unknown dependency: ";
	private static final String TEXTO_FINAL = ".";

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(TEXTO_PLUGIN) && contenido.contains(TEXTO_DEPENDENCIA)) {
			posibleDependenciaFaltante = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (!posibleDependenciaFaltante || linea == null || linea.isEmpty()) {
			return;
		}

		int idxPlugin = linea.indexOf(TEXTO_PLUGIN);
		int idxDependencia = linea.indexOf(TEXTO_DEPENDENCIA, idxPlugin);
		int idxFinal = linea.indexOf(TEXTO_FINAL, idxDependencia + TEXTO_DEPENDENCIA.length());

		if (idxPlugin < 0 || idxDependencia <= idxPlugin || idxFinal <= idxDependencia) {
			return;
		}

		String nombrePlugin = linea.substring(idxPlugin + TEXTO_PLUGIN.length(), idxDependencia).trim();
		String dependencia = linea.substring(idxDependencia + TEXTO_DEPENDENCIA.length(), idxFinal).trim();

		if (nombrePlugin.isEmpty() || dependencia.isEmpty() || contieneEntrada(nombrePlugin, dependencia)) {
			return;
		}

		nombresPlugins.add(nombrePlugin);
		dependencias.add(dependencia);
		enlaces.add(consola.agregarErrorALectador(numero_de_linea, this));

		activado = true;
		reconstruirMensaje();
	}

	private boolean contieneEntrada(String nombrePlugin, String dependencia) {
		for (int i = 0; i < nombresPlugins.size(); i++) {
			if (nombresPlugins.get(i).equalsIgnoreCase(nombrePlugin)
					&& dependencias.get(i).equalsIgnoreCase(dependencia)) {
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
			mensaje = MonitorDePID.idioma.mensajeDependenciaPluginFaltantePlural(nombresPlugins, dependencias);
		} else {
			mensaje = MonitorDePID.idioma.mensajeDependenciaPluginFaltanteSingular(nombresPlugins.get(0),
					dependencias.get(0));
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
		return new ProblemaDependenciaPluginPocketMine();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaDependenciaPluginFaltante();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		for (int i = 0; i < nombresPlugins.size(); i++) {
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarPlugin(dependencias.get(i)));
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(nombresPlugins.get(i)));
		}
		return builder.construir();
	}

	@Override
	public String id() {
		return "problema_dependencia_plugin_pocketmine";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}
		return trazo.trace.contains(TEXTO_PLUGIN) && trazo.trace.contains(TEXTO_DEPENDENCIA);
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