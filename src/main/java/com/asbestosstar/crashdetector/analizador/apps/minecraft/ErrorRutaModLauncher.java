package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

/**
 * Verificación especializada para detectar errores de ModLauncher causados por
 * rutas que contienen caracteres no ASCII o caracteres especiales.
 * <p>
 * - Analiza la consola en busca del error específico "Bad escape" de
 * ModLauncher - Detecta cuando las rutas contienen caracteres no ASCII que
 * rompen el sistema de servicios - Incluye la solución directamente en el
 * mensaje principal - La salida es un bloque HTML con información detallada
 * para la UI
 */
public class ErrorRutaModLauncher implements Verificaciones {

	/**
	 * Separador de líneas, definido en la interfaz base
	 */
	private static final String NL = Verificaciones.nl;

	/**
	 * Almacena los mensajes de error únicos detectados
	 */
	private final Map<String, String> errores = new HashMap<>();

	/**
	 * Indica si se encontró al menos un error de ruta en ModLauncher
	 */
	private boolean activado = false;

	/**
	 * Almacena el enlace HTML por mensaje base
	 */
	private final Map<String, String> enlacesPorLinea = new HashMap<>();

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
	 * Busca el patrón: "java.lang.IllegalArgumentException: Bad escape" siempre y
	 * cuando en el contenido completo del log también aparezca
	 * "sun.nio.fs.UnixUriUtils.fromUri" (misma condición que en la versión original
	 * para reducir falsos positivos).
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Comprobación global del tipo de error (igual que en la versión original).
		String contenido = consola.contenido_verificar;
		if (!contenido.contains("java.lang.IllegalArgumentException: Bad escape")
				|| !contenido.contains("sun.nio.fs.UnixUriUtils.fromUri")) {
			return;
		}

		// Analizar solo líneas que contengan el mensaje concreto
		if (!linea.contains("java.lang.IllegalArgumentException: Bad escape")) {
			return;
		}

		String mensajeBase = MonitorDePID.idioma.error_modlauncher_path();

		// Registrar el enlace a esta línea solo la primera vez
		if (!enlacesPorLinea.containsKey(mensajeBase)) {
			String enlace = consola.agregarErrorALectador(numero_de_linea, this);
			enlacesPorLinea.put(mensajeBase, enlace);
		}

		// Agregar el error al mapa (clave única por mensaje base)
		errores.put(mensajeBase, "");
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorRutaModLauncher();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000f; // Prioridad máxima: error crítico que impide el arranque
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
				sb.append("<li>").append(mensajeBase).append(" ").append(enlace).append("</li>");
			} else {
				sb.append("<li>").append(mensajeBase).append("</li>");
			}
		}

		sb.append("</ul>");
		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_modlauncher_path();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucion_modlauncher_path())
				.construir();
	}

	@Override
	public String id() {
		return "ruta_modlauncher";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene tanto "java.lang.IllegalArgumentException: Bad escape"
	 * como "sun.nio.fs.UnixUriUtils.fromUri".</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefieren falsos negativos a marcar
	 * trazos que no correspondan a este problema concreto.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;
		return t.contains("java.lang.IllegalArgumentException: Bad escape")
				&& t.contains("sun.nio.fs.UnixUriUtils.fromUri");
	}

}
