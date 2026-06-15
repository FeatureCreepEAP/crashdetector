package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

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
public class ErrorRutaModLauncher implements VerificacionRapida {

	private static final String BAD_ESCAPE = "java.lang.IllegalArgumentException: Bad escape";
	private static final String UNIX_URI_UTILS = "sun.nio.fs.UnixUriUtils.fromUri";

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

	private boolean vioBadEscape = false;
	private boolean vioUnixUriUtils = false;

	@Override
	public String[] patronesRapidos() {
		return new String[] { BAD_ESCAPE, UNIX_URI_UTILS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		if (linea.contains(BAD_ESCAPE)) {
			vioBadEscape = true;
		}

		if (linea.contains(UNIX_URI_UTILS)) {
			vioUnixUriUtils = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificarPorLinea(Consola, String, int)}, llamada por el analizador
	 * línea a línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(BAD_ESCAPE)) {
			vioBadEscape = true;
		}

		if (contenido.contains(UNIX_URI_UTILS)) {
			vioUnixUriUtils = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return vioBadEscape && vioUnixUriUtils && !activado;
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
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!vioBadEscape || !vioUnixUriUtils || linea == null) {
			return;
		}

		// Analizar solo líneas que contengan el mensaje concreto
		if (!linea.contains(BAD_ESCAPE)) {
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
		return t.contains(BAD_ESCAPE) && t.contains(UNIX_URI_UTILS);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}