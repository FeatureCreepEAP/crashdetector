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
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class NoPuedeAnalizarJSONDeRegistro implements Verificaciones {

	boolean activado = false;
	private final List<String> erroresJSON = new ArrayList<>(); // Almacena múltiples errores
	private final Map<String, String> enlacesPorError = new HashMap<>();

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
		// No se usa: este verificador funciona en modo por línea.
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
		if (!linea.contains("Failed to parse") || !linea.contains(".json from pack")) {
			return;
		}

		CrashDetectorLogger.log("Se detectó error de análisis JSON en registro");

		try {
			String archivoJar = linea.split("from pack ")[1].split("\\.jar")[0].trim() + ".jar";
			String recurso = linea.split("Failed to parse ")[1].split(" from pack")[0].trim();

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

	@Override
	public Verificaciones nueva() {
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

		if (!t.contains("Failed to parse") || !t.contains(".json from pack")) {
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

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
