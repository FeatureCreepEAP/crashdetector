package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

/**
 * Verificación especializada para detectar errores de resolución de texturas que
 * ocurren cuando las texturas no caben en la hoja de texturas.
 * <p>
 * - Analiza stack traces en busca de la frase clave "Unable to fit:" y "Maybe try a lower resolution resourcepack?"
 * - Extrae información sobre el recurso problemático y su tamaño
 * - No busca un origen específico (JAR/mod) ya que el problema es de resolución de texturas
 * - Proporciona soluciones prácticas específicas para este problema
 * - La salida es un bloque HTML con información detallada para la UI
 */
public class ErrorResolucionDeTextura implements Verificaciones {

	/**
	 * Patrón para detectar errores de resolución de texturas con las frases clave:
	 * "Unable to fit:" y "Maybe try a lower resolution resourcepack?"
	 */
	private static final Pattern ERRORES_RESOLUCION = Pattern.compile(
			"net\\.minecraft\\.client\\.renderer\\.texture\\.StitcherException:\\s*Unable to fit:\\s*([^\\s]+)\\s*-\\s*size:\\s*(\\d+x\\d+)(?:\\s*-\\s*Maybe try a lower resolution resourcepack\\?)?",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Separador de líneas, definido en la interfaz base
	 */
	private static final String NL = Verificaciones.nl;

	/**
	 * Almacena los mensajes de error únicos detectados
	 */
	private final Map<String, String> errores = new HashMap<>();

	/**
	 * Indica si se encontró al menos un error de resolución de texturas
	 */
	private boolean activado = false;

	/**
	 * Almacena el enlace HTML por mensaje base
	 */
	private final Map<String, String> enlacesPorLinea = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		// Limpiar resultados anteriores
		errores.clear();
		activado = false;
		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;

		// Colección de trazos (fatales y no fatales)
		List<VerificacionDeStackTrace.TraceInfo> trazosInfo = new ArrayList<>();
		trazosInfo.addAll(VerificacionDeStackTrace.obtenerTracesConLinea(consola.contenido_verificar));
		trazosInfo.addAll(VerificacionDeStackTrace.obtenerTracesFatalConLinea(consola.contenido_verificar));

		// Analizar cada trazo
		for (VerificacionDeStackTrace.TraceInfo traceInfo : trazosInfo) {
			String trazo = traceInfo.trace;
			
			// Verificar las frases clave específicas
			if (!trazo.contains("Unable to fit:") || 
				!trazo.contains("Maybe try a lower resolution resourcepack?")) {
				continue;
			}

			Matcher matcher = ERRORES_RESOLUCION.matcher(trazo);
			if (!matcher.find()) {
				continue;
			}

			String recurso = matcher.group(1);
			String tamaño = matcher.group(2);
			String mensajeBase = MonitorDePID.idioma.error_resolucion_textura(recurso, tamaño);

			// Registrar enlace para este error
			enlacesPorLinea.putIfAbsent(mensajeBase,
					consola.agregarErrorALectador(traceInfo.consolaLineaComenzar, this));

			// Agregar el error al mapa
			errores.put(mensajeBase, "");
			activado = true;
		}

		// Analizar líneas sueltas sin trazo completo
		String[] lineas = consola.contenido_verificar.split(NL);
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			if (linea.contains("Unable to fit:") && 
				linea.contains("Maybe try a lower resolution resourcepack?") && 
				!linea.contains("at ") && 
				VerificacionDeStackTrace.tracePermite(linea)) {
				procesarLineaSinTraza(linea, vdst, i, consola);
			}
		}
	}

	/**
	 * Procesa una línea con error de resolución de texturas que no tiene stack trace completo
	 */
	private void procesarLineaSinTraza(String linea, VerificacionDeStackTrace vdst, int numeroLinea, Consola consola) {
		Matcher matcher = ERRORES_RESOLUCION.matcher(linea);
		if (!matcher.find()) {
			return;
		}

		String recurso = matcher.group(1);
		String tamaño = matcher.group(2);
		String mensajeBase = MonitorDePID.idioma.error_resolucion_textura(recurso, tamaño);

		// Registrar el error en el sistema de lectura
		enlacesPorLinea.put(mensajeBase, consola.agregarErrorALectador(numeroLinea, this));

		// Agregar el error al mapa
		errores.put(mensajeBase, "");
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorResolucionDeTextura();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000f; // Prioridad muy alta: problema crítico que impide el arranque
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder sb = new StringBuilder("<ul>");

		// Para cada tipo de error
		for (String mensajeBase : errores.keySet()) {
			// Recuperar el enlace
			String enlace = enlacesPorLinea.getOrDefault(mensajeBase, "");
			if (!enlace.isEmpty()) {
				sb.append("<li>").append(mensajeBase).append(MonitorDePID.idioma.solucion_resolucion_textura()).append(" ").append(enlace).append("</li>");
			} else {
				sb.append("<li>").append(mensajeBase).append("</li>");
			}
		}

		sb.append("</ul>");
		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_resolucion_textura();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucion_resolucion_textura())
				.construir();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "error_resolucion_de_textura";
	}
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;//TODO
	}
	
	
}