package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class NoPuedeAnalizarJSONDeRegistro implements Verificaciones {

	boolean activado = false;
	private final List<String> erroresJSON = new ArrayList<>(); // Almacena múltiples errores
	private final Map<String, String> enlacesPorError = new HashMap<>();

	private static final String FAILED_TO_PARSE = "Failed to parse";
	private static final String JSON_FROM_PACK = ".json from pack";
	private static final String FROM_PACK = "from pack ";
	private static final String FROM_PACK_SEPARATOR = " from pack";
	private static final String JAR = ".jar";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FAILED_TO_PARSE, JSON_FROM_PACK, FROM_PACK };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verifica una línea de la consola para detectar errores de análisis en
	 * archivos JSON de mods.
	 * <p>
	 * Formato esperado:
	 * 
	 * <pre>
	 * java.lang.IllegalStateException: Failed to parse [recurso] from pack [archivo.jar]
	 * </pre>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!lineaContieneErrorJSON(linea)) {
			return;
		}

		CrashDetectorLogger.log("Se detectó error de análisis JSON en registro");

		try {
			String archivoJar = extraerArchivoJar(linea);
			String recurso = extraerRecurso(linea);

			if (archivoJar.isEmpty() || recurso.isEmpty()) {
				throw new IllegalArgumentException("No se pudo extraer archivo jar o recurso JSON");
			}

			String mensaje = MonitorDePID.idioma.errorConJSONDeRegistro(archivoJar, recurso);

			// Solo registrar si es un error nuevo
			if (!erroresJSON.contains(mensaje)) {
				erroresJSON.add(mensaje);
				String enlace = consola.agregarErrorALectador(numero_de_linea, this);
				enlacesPorError.put(mensaje, enlace);
			}
			activado = true;

		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
			// Aún así registrar la línea como problema
			consola.agregarErrorALectador(numero_de_linea, this);
		}
	}

	private boolean lineaContieneErrorJSON(String linea) {
		return linea.contains(FAILED_TO_PARSE) && linea.contains(JSON_FROM_PACK);
	}

	private String extraerArchivoJar(String linea) {
		int inicio = linea.indexOf(FROM_PACK);
		if (inicio < 0) {
			return "";
		}

		inicio += FROM_PACK.length();

		int fin = linea.indexOf(JAR, inicio);
		if (fin < inicio) {
			return "";
		}

		return linea.substring(inicio, fin).trim() + JAR;
	}

	private String extraerRecurso(String linea) {
		int inicio = linea.indexOf(FAILED_TO_PARSE);
		if (inicio < 0) {
			return "";
		}

		inicio += FAILED_TO_PARSE.length();

		int fin = linea.indexOf(FROM_PACK_SEPARATOR, inicio);
		if (fin < inicio) {
			return "";
		}

		return linea.substring(inicio, fin).trim();
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new NoPuedeAnalizarJSONDeRegistro();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 500.0f; // Prioridad alta para errores de configuración crítica [[4]]
	}

	@Override
	public String mensaje() {
		if (erroresJSON.isEmpty())
			return "";

		StringBuilder html = new StringBuilder(MonitorDePID.idioma.noPuedeAnalizarJSON() + "<ul>");
		for (String error : erroresJSON) {
			String enlace = enlacesPorError.getOrDefault(error, "");
			html.append("<li>").append(error).append(" ").append(enlace).append("</li>");
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_no_puede_analizar_json_de_registro();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		return "no_puede_analizar_json_de_registro";
	}

	/**
	 * Marca un trazo como ocupado si contiene claramente el mismo patrón que usamos
	 * para detectar el error en el log:
	 * <ul>
	 * <li>"Failed to parse"</li>
	 * <li>".json from pack"</li>
	 * </ul>
	 * Se exige además que el verificador ya esté activado para reducir falsos
	 * positivos. Es deliberadamente conservador: mejor falso negativo que atribuir
	 * un trazo incorrecto.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (!lineaContieneErrorJSON(t)) {
			return false;
		}

		// Opcionalmente podríamos exigir también " from pack " para ser aún más
		// estrictos,
		// pero con ".json from pack" ya es bastante específico.
		return true;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}