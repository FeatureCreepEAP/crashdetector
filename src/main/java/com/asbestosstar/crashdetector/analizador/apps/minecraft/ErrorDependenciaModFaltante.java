package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Analiza errores cuando un mod tiene dependencias con campos obligatorios
 * faltantes. Detecta específicamente el error "Missing required field mandatory
 * in dependency" y muestra el nombre del JAR problemático.
 */
public class ErrorDependenciaModFaltante implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreJar = "";
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
	 * Busca el patrón: "Missing required field mandatory in dependency (<jar>)" en
	 * la línea actual, extrae el nombre del JAR y registra el enlace.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguimos procesando más líneas.
		if (activado) {
			return;
		}

		// Detecta el error específico de campo obligatorio faltante en dependencias
		if (linea.contains("Missing required field mandatory in dependency")) {
			// Extrae el nombre del JAR problemático usando expresión regular
			Pattern pattern = Pattern.compile("Missing required field mandatory in dependency \\(([^)]+)\\)");
			Matcher matcher = pattern.matcher(linea);
			if (matcher.find()) {
				nombreJar = matcher.group(1);
				mensaje = MonitorDePID.idioma.errorDependenciaModFaltante(nombreJar) + Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				activado = true;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorDependenciaModFaltante();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 920.0f; // Prioridad muy alta - error crítico que impide cargar mods
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_dependencia_mod_faltante();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_dependencia_mod_faltante(nombreJar))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_dependencia_mod_faltante(nombreJar))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_dependencia_mod_faltante()).construir();
	}

	@Override
	public String id() {
		return "dependencia_mod_faltante";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene la cadena base del error y, si se conoce, el nombre del
	 * JAR problemático.</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo a marcar
	 * trazos que no correspondan a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (!nombreJar.isEmpty()) {
			String esperado = "Missing required field mandatory in dependency (" + nombreJar + ")";
			return t.contains(esperado);
		}

		// Fallback muy estricto si por alguna razón no se capturó el nombre del JAR.
		return t.contains("Missing required field mandatory in dependency");
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
