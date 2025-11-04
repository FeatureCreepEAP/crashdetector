package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Analiza errores cuando un mod se está utilizando en una plataforma incorrecta
 * (cliente/servidor). Detecta específicamente el error "Attempted to load class
 * [...] for invalid dist [CLIENT|SERVER]". Incluye sugerencias para usar
 * ModsTree (Arbol de Mods) para identificar el mod problemático.
 */
public class ErrorCargaClaseEntornoInvalido implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreClase = "";
	private String entornoInvalido = "";
	private String enlaceHtml = "";

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección se hace por línea en {@link #verificar(Consola, String, int)},
	 * llamado por el sistema de análisis línea a línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca el patrón: "Attempted to load class [CLASE] for invalid dist
	 * [CLIENT|SERVER]" en la línea actual, extrae la clase y el entorno y registra
	 * el enlace.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguimos procesando más líneas.
		if (activado) {
			return;
		}

		// Detecta el error específico de carga en entorno incorrecto
		if (linea.contains("Attempted to load class") && linea.contains("for invalid dist")) {

			// Extrae el nombre de la clase y el entorno usando expresión regular
			Pattern pattern = Pattern.compile("Attempted to load class ([^ ]+) for invalid dist (CLIENT|SERVER)");
			Matcher matcher = pattern.matcher(linea);
			if (matcher.find()) {
				nombreClase = matcher.group(1);
				entornoInvalido = matcher.group(2);

				mensaje = MonitorDePID.idioma.errorModEnPlataformaIncorrecta(nombreClase, entornoInvalido)
						+ Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				activado = true;
			}
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
		return 850.0f; // Prioridad alta - error que impide iniciar el juego
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
		return t.contains("Attempted to load class") && t.contains("for invalid dist");
	}
}
