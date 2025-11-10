package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Analiza errores críticos relacionados con plugins de JEI (Just Enough Items)
 * que causan crashes. Detecta específicamente el error "Caught an error from
 * mod plugin: class [clase] [modid]:[plugin_id]". Este error puede causar
 * crashes durante la inicialización del juego.
 */
public class ErrorJEIPluginFallido implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreClase = "";
	private String modId = "";
	private String pluginId = "";
	private String enlaceHtml = "";

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificar(Consola, String, int)}, llamada por el analizador línea a
	 * línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca el patrón: "Caught an error from mod plugin: class [CLASE]
	 * [MODID]:[PLUGIN_ID]" en la línea actual, extrae los campos y registra el
	 * enlace.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguimos procesando más líneas.
		if (activado) {
			return;
		}

		// Detecta el error específico de plugin JEI fallido
		if (linea.contains("Caught an error from mod plugin: class")) {

			// Extrae la información usando expresión regular
			Pattern pattern = Pattern.compile("Caught an error from mod plugin: class ([^ ]+) ([^:]+):([^ ]+)");
			Matcher matcher = pattern.matcher(linea);
			if (matcher.find()) {
				nombreClase = matcher.group(1);
				modId = matcher.group(2);
				pluginId = matcher.group(3);

				mensaje = MonitorDePID.idioma.errorJEIPluginFallido(nombreClase, modId, pluginId)
						+ Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				activado = true;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorJEIPluginFallido();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f; // Máxima prioridad - error crítico que causa crashes
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_jei_plugin_fallido();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1_jei_plugin_fallido(modId))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_jei_plugin_fallido(modId))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_jei_plugin_fallido(modId)).construir();
	}

	@Override
	public String id() {
		return "jei_plugin_fallido";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene la línea completa característica del error (incluyendo
	 * clase, modId y pluginId) o, como fallback muy estricto, el patrón base.</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo antes que
	 * marcar un trazo que no corresponda a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (!nombreClase.isEmpty() && !modId.isEmpty() && !pluginId.isEmpty()) {
			String esperado = "Caught an error from mod plugin: class " + nombreClase + " " + modId + ":" + pluginId;
			return t.contains(esperado);
		}

		// Fallback muy estricto si por alguna razón no se capturaron todos los datos.
		return t.contains("Caught an error from mod plugin: class") && t.contains("JEI"); // opcional si el trazo suele
																							// mencionar JEI
	}

}
