package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de especificación de versión inválida en mods donde la
 * versión no está correctamente rodeada por corchetes, causando un
 * InvalidVersionSpecificationException.
 */
public class ErrorVersionInvalidaModMaven implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private String versionDetectada = "";
	public boolean posibleError = false;

	private static final String INVALID_VERSION_SPECIFICATION = "org.apache.maven.artifact.versioning.InvalidVersionSpecificationException";
	private static final String SINGLE_VERSION_SURROUNDED = "Single version must be surrounded by []:";
	private static final String MUST_BE_SURROUNDED = "must be surrounded by []:";

	@Override
	public String[] patronesRapidos() {
		return new String[] { INVALID_VERSION_SPECIFICATION, SINGLE_VERSION_SURROUNDED };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneVersionInvalida(evento.linea)) {
			posibleError = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Método de compatibilidad — mantiene el análisis legacy por contenido
	 * completo.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null)
			return;

		String log = consola.contenido_verificar;

		if (log.contains(INVALID_VERSION_SPECIFICATION) && log.contains(SINGLE_VERSION_SURROUNDED)) {
			posibleError = true;
		}

	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleError && !activado;
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

		if (!posibleError || linea == null) {
			return;
		}

		// Buscamos la línea que contiene el error de especificación de versión inválida
		if (lineaContieneVersionInvalida(linea)) {

			// Extraemos la versión detectada del error, que está después de "must be
			// surrounded by []:"
			int idx = linea.indexOf(MUST_BE_SURROUNDED);
			int inicio = idx >= 0 ? idx + MUST_BE_SURROUNDED.length() : -1;

			if (inicio >= 0 && inicio < linea.length()) {
				versionDetectada = linea.substring(inicio).trim();
			}

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de versión inválida
			mensaje = MonitorDePID.idioma.errorVersionInvalidaMod(versionDetectada) + Verificaciones.nl_html;
			activado = true;
		}
	}

	private boolean lineaContieneVersionInvalida(String linea) {
		return linea.contains(INVALID_VERSION_SPECIFICATION) && linea.contains(SINGLE_VERSION_SURROUNDED);
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

		return t.contains(INVALID_VERSION_SPECIFICATION) && t.contains(SINGLE_VERSION_SURROUNDED);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}