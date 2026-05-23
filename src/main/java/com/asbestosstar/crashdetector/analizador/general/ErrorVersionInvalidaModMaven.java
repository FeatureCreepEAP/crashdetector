package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de especificación de versión inválida en mods donde la
 * versión no está correctamente rodeada por corchetes, causando un
 * InvalidVersionSpecificationException.
 */
public class ErrorVersionInvalidaModMaven implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private String versionDetectada = "";

	/**
	 * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
	 */
	@Override
	public void verificar(Consola consola) {
		// Se usa el método verificar(Consola, String, int) en lugar de este.
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde un mod tiene una
	 * especificación de versión inválida.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el error de especificación de versión inválida
		if (linea.contains("org.apache.maven.artifact.versioning.InvalidVersionSpecificationException")
				&& linea.contains("Single version must be surrounded by []:")) {

			// Extraemos la versión detectada del error, que está después de "must be
			// surrounded by []:"
			int inicio = linea.indexOf("must be surrounded by []:") + "must be surrounded by []:".length();
			if (inicio != -1 && inicio < linea.length()) {
				versionDetectada = linea.substring(inicio).trim();
			}

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de versión inválida
			mensaje = MonitorDePID.idioma.errorVersionInvalidaMod(versionDetectada) + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorVersionInvalidaModMaven();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f; // Alta prioridad: rompe la carga del mod
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorVersionInvalidaMod();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorVersionInvalidaMod())
				.construir();
	}

	@Override
	public String id() {
		return "error_version_invalida_mod";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * especificación de versión inválida.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("org.apache.maven.artifact.versioning.InvalidVersionSpecificationException")
				&& t.contains("Single version must be surrounded by []:");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}

}