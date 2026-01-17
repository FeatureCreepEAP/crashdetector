package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Analiza errores cuando los mods contienen caracteres inválidos en sus nombres
 * de módulo. Detecta específicamente el error "Invalid module name: 'X' is not
 * a Java identifier". Incluye el nombre del módulo y la parte inválida en los
 * mensajes.
 */
public class ErrorCaracteresInvalidosEnNombre implements Verificaciones {

	// Patrón reutilizable para no recompilarlo por cada línea.
	private static final Pattern PATRON_ERROR = Pattern
			.compile("IllegalArgumentException: ([^:]+): Invalid module name: '([^']+)'");

	private boolean activado = false;
	private String mensaje = "";
	private String nombreModulo = "";
	private String parteInvalida = "";
	private String enlaceHtml = "";

	/**
	 * Bandera ligera para saber si el log contiene indicios del error. Se usa como
	 * filtro rápido antes del análisis línea a línea.
	 */
	private boolean posibleErrorNombreInvalido = false;

	@Override
	public void verificar(Consola consola) {
		// Trabajo global mínimo: solo comprobamos si el texto base del error aparece
		// en algún punto del log. Si no, la verificación por línea se saltará.
		String contenidoConsola = consola.contenido_verificar;
		if (contenidoConsola == null) {
			posibleErrorNombreInvalido = false;
			return;
		}

		posibleErrorNombreInvalido = contenidoConsola.contains("Invalid module name: '")
				&& contenidoConsola.contains("' is not a Java identifier");
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Busca el patrón:
	 * {@code IllegalArgumentException: <modulo>: Invalid module name: '<x>' is not a Java identifier}
	 * y extrae tanto el nombre del módulo como la parte inválida.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó o sabemos que el log no contiene este tipo de error,
		// no hacemos trabajo adicional.
		if (activado || !posibleErrorNombreInvalido || linea == null) {
			return;
		}

		// Detecta cualquier caso de "Invalid module name: 'X' is not a Java identifier"
		if (linea.contains("Invalid module name: '") && linea.contains("' is not a Java identifier")) {

			// Extrae el nombre completo del módulo y la parte inválida usando expresiones
			// regulares
			Matcher matcher = PATRON_ERROR.matcher(linea);

			if (matcher.find()) {
				nombreModulo = matcher.group(1);
				parteInvalida = matcher.group(2);

				// Registrar el error en el sistema de lectura con el número de línea
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

				mensaje = MonitorDePID.idioma.errorCaracteresInvalidosEnNombre(nombreModulo, parteInvalida)
						+ Verificaciones.nl_html;
				activado = true; // Es crítico y suele ocurrir una sola vez
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCaracteresInvalidosEnNombre();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f; // Máxima prioridad - error crítico que impide iniciar el juego
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		// Incluir solo el mensaje original y el enlace HTML (que ya tiene su formato)
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_caracteres_invalidos();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_caracteres_invalidos(nombreModulo, parteInvalida))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_caracteres_invalidos(nombreModulo))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_caracteres_invalidos()).construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "caracters_invalidos_en_nombre";
	}

	/**
	 * Marca trazos del stack trace que corresponden claramente a este problema.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se ha activado, y</li>
	 * <li>El trazo contiene tanto la frase base "Invalid module name:" como "is not
	 * a Java identifier".</li>
	 * </ul>
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}
		String t = trazo.trace;
		return t.contains("Invalid module name:") && t.contains("is not a Java identifier");
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
