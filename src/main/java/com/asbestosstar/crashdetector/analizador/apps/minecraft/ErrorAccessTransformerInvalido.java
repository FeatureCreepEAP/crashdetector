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
 * Analiza errores cuando un mod tiene una configuración inválida de access
 * transformer. Detecta específicamente el error "Invalid access transformer
 * line in [nombre.jar]".
 */
public class ErrorAccessTransformerInvalido implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreJar = "";
	private String enlaceHtml = "";

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificar(Consola, String, int)}, que es llamado por el sistema de
	 * análisis línea a línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca el patrón "Invalid access transformer line in [nombre.jar]:" en la
	 * línea actual, extrae el nombre del JAR y registra el enlace al lector.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no hace falta seguir procesando más líneas.
		if (activado) {
			return;
		}

		// Detecta el error específico de access transformer inválido
		if (linea.contains("Invalid access transformer line in")) {
			// Extrae el nombre del JAR problemático usando expresión regular
			Pattern pattern = Pattern.compile("Invalid access transformer line in ([^:]+):");
			Matcher matcher = pattern.matcher(linea);
			if (matcher.find()) {
				nombreJar = matcher.group(1);
				mensaje = MonitorDePID.idioma.errorAccessTransformerInvalido(nombreJar) + Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				activado = true;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorAccessTransformerInvalido();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 910.0f; // Prioridad muy alta - error crítico que impide cargar mods
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_access_transformer_invalido();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_access_transformer_invalido(nombreJar))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_access_transformer_invalido(nombreJar))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_access_transformer_invalido()).construir();
	}

	@Override
	public String id() {
		return "access_transformer_invalido";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene la cadena base del error "Invalid access transformer
	 * line in" y, si se conoce, el nombre del JAR problemático.</li>
	 * </ul>
	 * Es intencionadamente conservador: mejor falsos negativos que falsos
	 * positivos.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (!nombreJar.isEmpty()) {
			return t.contains("Invalid access transformer line in") && t.contains(nombreJar);
		}

		// Caso de fallback si por alguna razón no se llegó a capturar el nombre del
		// JAR.
		return t.contains("Invalid access transformer line in");
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
