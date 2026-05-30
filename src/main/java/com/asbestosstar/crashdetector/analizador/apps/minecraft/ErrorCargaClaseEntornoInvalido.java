package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Analiza errores cuando un mod se está utilizando en una plataforma incorrecta
 * (cliente/servidor). Detecta específicamente el error "Attempted to load class
 * [...] for invalid dist [CLIENT|SERVER]". Incluye sugerencias para usar
 * ModsTree (Arbol de Mods) para identificar el mod problemático.
 */
public class ErrorCargaClaseEntornoInvalido implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;

	private String mensaje = "";
	private String nombreClase = "";
	private String entornoInvalido = "";
	private String enlaceHtml = "";

	private static final String TEXTO_CLASE = "Attempted to load class";
	private static final String TEXTO_DIST = "for invalid dist";

	/**
	 * Verificación global barata.
	 * <p>
	 * Si el log completo no contiene las dos señales principales, se desactiva el
	 * análisis por línea para evitar trabajo innecesario.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {

		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(TEXTO_CLASE) && contenido.contains(TEXTO_DIST)) {
			this.analizarLineas = true;
		}
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca el patrón textual: "Attempted to load class [CLASE] for invalid dist
	 * [CLIENT|SERVER]" en la línea actual, extrae la clase y el entorno y registra
	 * el enlace.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si el chequeo global no encontró las señales principales, no analizamos
		// línea por línea.
		if (!analizarLineas) {
			return;
		}

		// Si ya se activó, no seguimos procesando más líneas.
		if (activado) {
			return;
		}

		if (linea == null) {
			return;
		}

		// Detecta el error específico de carga en entorno incorrecto.
		if (linea.contains(TEXTO_CLASE) && linea.contains(TEXTO_DIST)) {

			// Extrae el nombre de la clase y el entorno sin regex.
			// Acepta CLIENT, SERVER, DEDICATED_SERVER, etc. (cualquier token MAYÚSCULAS+_).
			ResultadoExtraccion resultado = extraerClaseYEntorno(linea);

			if (resultado != null) {
				nombreClase = resultado.nombreClase;
				entornoInvalido = resultado.entornoInvalido;

				mensaje = MonitorDePID.idioma.errorModEnPlataformaIncorrecta(nombreClase, entornoInvalido)
						+ Verificaciones.nl_html;

				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				activado = true;
			}
		}
	}

	private ResultadoExtraccion extraerClaseYEntorno(String linea) {
		int posClase = linea.indexOf(TEXTO_CLASE);
		if (posClase < 0) {
			return null;
		}

		int inicioClase = posClase + TEXTO_CLASE.length();
		inicioClase = saltarEspacios(linea, inicioClase);

		int posDist = linea.indexOf(TEXTO_DIST, inicioClase);
		if (posDist < 0 || posDist <= inicioClase) {
			return null;
		}

		String clase = linea.substring(inicioClase, posDist).trim();
		if (clase.isEmpty() || contieneEspacio(clase)) {
			return null;
		}

		int inicioEntorno = posDist + TEXTO_DIST.length();
		inicioEntorno = saltarEspacios(linea, inicioEntorno);

		int finEntorno = inicioEntorno;
		while (finEntorno < linea.length() && esCaracterEntorno(linea.charAt(finEntorno))) {
			finEntorno++;
		}

		if (finEntorno <= inicioEntorno) {
			return null;
		}

		String entorno = linea.substring(inicioEntorno, finEntorno);
		return new ResultadoExtraccion(clase, entorno);
	}

	private int saltarEspacios(String texto, int inicio) {
		int i = inicio;
		while (i < texto.length() && Character.isWhitespace(texto.charAt(i))) {
			i++;
		}
		return i;
	}

	private boolean contieneEspacio(String texto) {
		for (int i = 0; i < texto.length(); i++) {
			if (Character.isWhitespace(texto.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	private boolean esCaracterEntorno(char c) {
		return (c >= 'A' && c <= 'Z') || c == '_';
	}

	private static class ResultadoExtraccion {
		final String nombreClase;
		final String entornoInvalido;

		ResultadoExtraccion(String nombreClase, String entornoInvalido) {
			this.nombreClase = nombreClase;
			this.entornoInvalido = entornoInvalido;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCargaClaseEntornoInvalido();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400.0f; // Prioridad alta - error que impide iniciar el juego
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_mod_plataforma_incorrecta();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_mod_plataforma_incorrecta(nombreClase, entornoInvalido))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_mod_plataforma_incorrecta(entornoInvalido))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_mod_plataforma_incorrecta()).construir();
	}

	@Override
	public String id() {
		return "error_carga_clase_entorno_invalido";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene la cadena base del error y, si se conoce, la clase y el
	 * entorno inválido concretos.</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo a marcar
	 * trazos que no correspondan a este error.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (!nombreClase.isEmpty() && !entornoInvalido.isEmpty()) {
			// Patrón completo exacto, máximo nivel de precisión.
			String esperado = "Attempted to load class " + nombreClase + " for invalid dist " + entornoInvalido;
			return t.contains(esperado);
		}

		// Fallback muy conservador si por alguna razón no se capturó la clase o el
		// entorno.
		return t.contains(TEXTO_CLASE) && t.contains(TEXTO_DIST);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}