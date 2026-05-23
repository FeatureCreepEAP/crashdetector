package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta mods sospechosos y excepciones relacionadas con Forge.
 */
public class MCForgeModsSuspechoso implements Verificaciones {

	private boolean activado = false;

	// Conjunto para almacenar los mensajes de error únicos
	private final Set<String> errores = new HashSet<>();

	// Mapas para almacenar enlaces y mod IDs asociados con cada error
	private final Map<String, String> enlacesPorError = new HashMap<>();
	private final Map<String, String> modidPorError = new HashMap<>();

	// -------------------------------------------------------------------------
	// Helpers sin regex — equivalentes directos a los patrones del original
	// -------------------------------------------------------------------------

	private static boolean esLineaDebugOTrace(String linea) {
		if (!linea.startsWith("["))
			return false;
		int corchete = linea.indexOf(']');
		if (corchete == -1)
			return false;
		String cabecera = linea.substring(0, corchete);
		return cabecera.contains("/DEBUG") || cabecera.contains("/TRACE");
	}

	/**
	 * Reemplaza: pModidEnLinea = "(?i)for\s+modid\s+([a-z0-9_\-.]+)" Extrae el mod
	 * ID tras "for modid ".
	 */
	private static String extraerForModid(String linea) {
		String lower = linea.toLowerCase();
		int idx = lower.indexOf("for modid ");
		if (idx == -1)
			return null;
		return extraerToken(linea.substring(idx + "for modid ".length()).trim());
	}

	/**
	 * Reemplaza: pModIdDosPuntos = "(?i)\bMod\s*ID\s*:\s*([a-z0-9_\-.]+)" Extrae el
	 * mod ID tras "Mod ID:" o "ModID:".
	 */
	private static String extraerModIdDosPuntos(String linea) {
		String lower = linea.toLowerCase();
		// Buscar "mod id:" o "modid:" (con o sin espacio)
		int idx = lower.indexOf("mod id:");
		if (idx == -1)
			idx = lower.indexOf("modid:");
		if (idx == -1)
			return null;
		int colon = linea.indexOf(':', idx);
		if (colon == -1)
			return null;
		return extraerToken(linea.substring(colon + 1).trim());
	}

	/**
	 * Reemplaza: pModidIgual = "(?i)\bmodid=([a-z0-9_\-.]+)" Extrae el mod ID tras
	 * "modid=".
	 */
	private static String extraerModidIgual(String linea) {
		String lower = linea.toLowerCase();
		int idx = lower.indexOf("modid=");
		if (idx == -1)
			return null;
		return extraerToken(linea.substring(idx + "modid=".length()));
	}

	/**
	 * Reemplaza: patronModSection = "(?i)--\s*MOD\s+([a-z0-9_\-.]+)\s*--" Extrae el
	 * mod ID de una línea "-- MOD modid --".
	 */
	private static String extraerSeccionMod(String linea) {
		String limpia = linea.trim();
		if (!limpia.startsWith("--") || !limpia.endsWith("--"))
			return null;
		String interior = limpia.substring(2, limpia.length() - 2).trim().toLowerCase();
		if (!interior.startsWith("mod "))
			return null;
		return extraerToken(limpia.substring(limpia.toLowerCase().indexOf("mod ") + 4).trim());
	}

	/**
	 * Reemplaza: patronSuspectedMod =
	 * "^(.*?)\s*\(([^()]+)\)(?:\s*\(([^()]+)\))?.*$" Extrae el mod ID de una línea
	 * de "Suspected Mods" con paréntesis. Si hay dos grupos entre paréntesis
	 * devuelve el último (el real); si hay uno, ese. Devuelve null si no hay
	 * paréntesis o si el candidato contiene ".java".
	 */
	private static String extraerSuspectedMod(String linea) {
		int primerAbre = linea.indexOf('(');
		if (primerAbre == -1)
			return null;
		int primerCierra = linea.indexOf(')', primerAbre);
		if (primerCierra == -1)
			return null;
		String candidato1 = linea.substring(primerAbre + 1, primerCierra).trim();

		// Buscar un segundo grupo entre paréntesis
		String candidato2 = "";
		int segundoAbre = linea.indexOf('(', primerCierra + 1);
		if (segundoAbre != -1) {
			int segundoCierra = linea.indexOf(')', segundoAbre);
			if (segundoCierra != -1) {
				candidato2 = linea.substring(segundoAbre + 1, segundoCierra).trim();
			}
		}

		String modID = !candidato2.isEmpty() ? candidato2 : candidato1;
		if (modID.contains(".java"))
			return null;
		return modID.isEmpty() ? null : modID;
	}

	/**
	 * Reemplaza: patronDespachoModid =
	 * "(?i)(?:encountered\s+an\s+(?:error|exception)|caught\s+exception)\s+during\s+.*?dispatch\s+for\s+modid\s+([a-z0-9_\-.]+)"
	 * Detecta el patrón de despacho y extrae el mod ID.
	 */
	private static String extraerDespachoModid(String linea) {
		String lower = linea.toLowerCase();
		boolean tieneDespacho = lower.contains("encountered an error") || lower.contains("encountered an exception")
				|| lower.contains("caught exception");
		if (!tieneDespacho)
			return null;
		if (!lower.contains("dispatch"))
			return null;
		return extraerForModid(linea);
	}

	/**
	 * Reemplaza: patronIndicadorError =
	 * "(?i)(error|exception|fail|crash|problem|unable|cannot|didn't|couldn't|missing|corrupt|invalid|unsupported)"
	 */
	private static boolean tieneIndicadorError(String linea) {
		String lower = linea.toLowerCase();
		return lower.contains("error") || lower.contains("exception") || lower.contains("fail")
				|| lower.contains("crash") || lower.contains("problem") || lower.contains("unable")
				|| lower.contains("cannot") || lower.contains("didn't") || lower.contains("couldn't")
				|| lower.contains("missing") || lower.contains("corrupt") || lower.contains("invalid")
				|| lower.contains("unsupported");
	}

	/**
	 * Equivalente a extraerModidDeLinea(linea, esSeccionSuspectedMod). Prueba cada
	 * sub-patrón en el mismo orden que el original.
	 */
	private static String extraerModidDeLinea(String linea, boolean esSeccionSuspectedMod) {
		if (linea == null || linea.contains("Object with ID ") || linea.isEmpty())
			return null;

		if (esSeccionSuspectedMod && linea.contains("(") && linea.contains(")")) {
			String m = extraerSuspectedMod(linea);
			if (m != null)
				return m;
		}

		String m;

		m = extraerSeccionMod(linea);
		if (m != null)
			return m;

		m = extraerForModid(linea);
		if (m != null)
			return m;

		m = extraerModIdDosPuntos(linea);
		if (m != null)
			return m;

		m = extraerModidIgual(linea);
		if (m != null)
			return m;

		return null;
	}

	/**
	 * Extrae un token de mod ID válido desde el inicio del texto. Caracteres
	 * aceptados: letras, dígitos, '_', '-', '.', '+'.
	 */
	private static String extraerToken(String texto) {
		if (texto == null || texto.isEmpty())
			return null;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);
			if (Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == '+') {
				sb.append(c);
			} else {
				break;
			}
		}
		String resultado = sb.toString();
		return resultado.isEmpty() ? null : resultado;
	}

	// -------------------------------------------------------------------------
	// Lógica principal — idéntica al original
	// -------------------------------------------------------------------------

	@Override
	public void verificar(Consola consola) {
		String[] lineas = consola.lineas_verificar;

		boolean encontradoModSospechoso = false;
		boolean encontradoStacktrace = false;
		boolean encontradoModSection = false;

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i].trim();

			// Saltar líneas de nivel DEBUG/TRACE
			if (esLineaDebugOTrace(linea))
				continue;

			// 1) Detectar secciones -- MOD modid --
			String modDeSeccion = extraerSeccionMod(linea);
			if (modDeSeccion != null) {
				encontradoModSection = true;
				if (!modDeSeccion.isEmpty()) {
					String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modDeSeccion;
					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(i, this);
						enlacesPorError.put(mensaje, enlace);
						modidPorError.put(mensaje, modDeSeccion);
					}
					activado = true;
				}
				continue;
			}

			// 2) Detectar sección "Suspected Mods:" (soporta singular y plural)
			if (linea.equalsIgnoreCase("Suspected Mod:") || linea.equalsIgnoreCase("Suspected Mods:")) {
				encontradoModSospechoso = true;
				encontradoStacktrace = false;
				continue;
			}

			// 3) Terminar la sección al encontrar "Stacktrace:"
			if (linea.contains("Stacktrace:")) {
				encontradoModSospechoso = false;
				encontradoStacktrace = true;
				continue;
			}

			// 4) Procesar los mods dentro de "Suspected Mods:"
			if (encontradoModSospechoso) {
				if (linea.isEmpty() || linea.startsWith("--") || linea.contains("Details:"))
					continue;

				String modID = extraerSuspectedMod(linea);
				if (modID != null) {
					if (!modID.isEmpty() && !modID.contains(".java")) {
						String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
						if (errores.add(mensaje)) {
							String enlace = consola.agregarErrorALectador(i, this);
							enlacesPorError.put(mensaje, enlace);
							modidPorError.put(mensaje, modID);
						}
						activado = true;
					}
					// Saltar las siguientes líneas del mismo mod (Mixin class, Target, etc.)
					while (i + 1 < lineas.length) {
						String next = lineas[i + 1].trim();
						if (next.startsWith("Mixin class:") || next.startsWith("Target:")
								|| next.startsWith("at TRANSFORMER/") || next.startsWith("Version:")
								|| next.startsWith("Issue tracker")) {
							i++;
						} else {
							break;
						}
					}
					continue;
				}

				// Fallback: intentar extraer mod ID con los otros sub-patrones
				modID = extraerModidDeLinea(linea, true);
				if (modID != null && !modID.isEmpty() && !modID.contains(".java")) {
					String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(i, this);
						enlacesPorError.put(mensaje, enlace);
						modidPorError.put(mensaje, modID);
					}
					activado = true;
				}
				continue;
			}

			// 5) Detectar "Failed to create mod instance"
			if (linea.contains("Failed to create mod instance. ModID:")) {
				String prefijo = "Failed to create mod instance. ModID: ";
				int indiceInicio = linea.indexOf(prefijo);
				if (indiceInicio != -1) {
					String modID = extraerToken(linea.substring(indiceInicio + prefijo.length()).trim());
					if (modID != null && !modID.isEmpty()) {
						String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
						if (errores.add(mensaje)) {
							String enlace = consola.agregarErrorALectador(i, this);
							enlacesPorError.put(mensaje, enlace);
							modidPorError.put(mensaje, modID);
						}
						activado = true;
					}
				}
				continue;
			}

			// 6) Patrón de despacho de modid
			String modDespacho = extraerDespachoModid(linea);
			if (modDespacho != null) {
				String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modDespacho;
				if (errores.add(mensaje)) {
					String enlace = consola.agregarErrorALectador(i, this);
					enlacesPorError.put(mensaje, enlace);
					modidPorError.put(mensaje, modDespacho);
				}
				activado = true;
			}

			// 7) Otros errores con indicadores, fuera de secciones específicas
			if (!encontradoModSection) {
				if (tieneIndicadorError(linea)) {
					String modID = extraerModidDeLinea(linea, false);
					if (modID != null && !modID.isEmpty()) {
						String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
						if (errores.add(mensaje)) {
							String enlace = consola.agregarErrorALectador(i, this);
							enlacesPorError.put(mensaje, enlace);
							modidPorError.put(mensaje, modID);
						}
						activado = true;
					}
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new MCForgeModsSuspechoso();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f;
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		for (String error : errores) {
			String enlace = enlacesPorError.getOrDefault(error, "");
			String extraUbicaciones = "";

			String modid = modidPorError.get(error);
			if (modid != null && !modid.isEmpty()) {
				List<String> ubicaciones = Buscardor.obtenerModsConNombre(modid);
				if (!ubicaciones.isEmpty()) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < ubicaciones.size(); i++) {
						if (i > 0)
							sb.append(", ");
						sb.append(ubicaciones.get(i));
					}
					extraUbicaciones = " (" + sb + ")";
				}
			}

			html.append("<li>").append(error).append(extraUbicaciones);
			if (!enlace.isEmpty()) {
				html.append(" ").append(enlace);
			}
			html.append("</li>");
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_mcforge_mod_sespechoso();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta("No hay solución disponible").construir();
	}

	@Override
	public String id() {
		return "mcforge_mods_sospechoso";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}