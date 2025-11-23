package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscardor;

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

	// Patrón para saltar líneas de nivel DEBUG/TRACE
	private final Pattern patronNivelLogParaSaltar = Pattern.compile("^\\[[^\\]]*/(DEBUG|TRACE)\\].*");

	// Patrones para extraer mod IDs de las líneas de error
	private final Pattern pModidEnLinea = Pattern.compile("(?i)for\\s+modid\\s+([a-z0-9_\\-.]+)");
	private final Pattern pModIdDosPuntos = Pattern.compile("(?i)\\bMod\\s*ID\\s*:\\s*([a-z0-9_\\-.]+)");
	private final Pattern pModidIgual = Pattern.compile("(?i)\\bmodid=([a-z0-9_\\-.]+)");
	private final Pattern patronDespachoModid = Pattern.compile(
			"(?i)(?:encountered\\s+an\\s+(?:error|exception)|caught\\s+exception)\\s+during\\s+.*?dispatch\\s+for\\s+modid\\s+([a-z0-9_\\-.]+)");

	// Nuevo patrón para detectar la sección de mods específicos en el crash report
	private final Pattern patronModSection = Pattern.compile("(?i)--\\s*MOD\\s+([a-z0-9_\\-.]+)\\s*--");

	// Patrón mejorado para detectar mod IDs en la sección "Suspected Mod"
	private final Pattern patronSuspectedMod = Pattern.compile("(?i)\\b([a-z0-9_\\-.]+)\\s*\\(([a-z0-9_\\-.]+)\\)");

	// Patrón para detectar líneas con indicadores de error
	private final Pattern patronIndicadorError = Pattern.compile(
			"(?i)(error|exception|fail|crash|problem|unable|cannot|didn't|couldn't|missing|corrupt|invalid|unsupported)");

	// Función para extraer el mod ID de cada línea usando expresiones regulares
	private String extraerModidDeLinea(String linea, boolean esSeccionSuspectedMod) {
		if (linea == null || linea.isEmpty())
			return null;

		// Nueva lógica para la sección "Suspected Mod"
		if (esSeccionSuspectedMod && linea.contains("(") && linea.contains(")")) {
			Matcher mSuspected = patronSuspectedMod.matcher(linea);
			if (mSuspected.find()) {
				// Priorizar el ID entre paréntesis si está presente
				return mSuspected.group(2).trim();
			}
		}

		Matcher m;

		// Buscar mod IDs en el formato -- MOD modid --
		m = patronModSection.matcher(linea);
		if (m.find()) {
			return m.group(1).trim();
		}

		// Buscar otros patrones para mod IDs
		m = pModidEnLinea.matcher(linea);
		if (m.find())
			return m.group(1).trim();

		m = pModIdDosPuntos.matcher(linea);
		if (m.find())
			return m.group(1).trim();

		m = pModidIgual.matcher(linea);
		if (m.find())
			return m.group(1).trim();

		return null;
	}

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		boolean encontradoModSospechoso = false; // Flag para detectar el inicio de "Suspected Mods"
		boolean encontradoStacktrace = false; // Flag para detectar el fin de la sección "Stacktrace"
		boolean encontradoModSection = false; // Flag para detectar secciones de mods específicos

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i].trim();

			// Saltar líneas de nivel DEBUG/TRACE para evitar falsos positivos
			if (patronNivelLogParaSaltar.matcher(linea).matches()) {
				continue;
			}

			// 1) Detectar secciones de mods específicos (-- MOD modid --)
			Matcher mModSection = patronModSection.matcher(linea);
			if (mModSection.find()) {
				encontradoModSection = true;
				String modID = mModSection.group(1).trim();
				if (!modID.isEmpty()) {
					String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(i, this);
						enlacesPorError.put(mensaje, enlace);
						modidPorError.put(mensaje, modID);
					}
					activado = true;
				}
				// Continuar procesando la línea para otros posibles errores
			}

			// 2) Si estamos en una sección de mod específico, buscar detalles adicionales
			if (encontradoModSection && linea.contains("Failure message:") && i + 1 < lineas.length) {
				String siguienteLinea = lineas[i + 1].trim();
				if (siguienteLinea.contains("has failed to load correctly")) {
					// Ya hemos procesado el mod ID en el patrón anterior
				}
			}

			// 3) Detectando "Suspected Mod:" - Solo procesamos las líneas entre "Suspected
			// Mod:" y "Stacktrace:"
			if (linea.contains("Suspected Mod:")) {
				encontradoModSospechoso = true;
				encontradoStacktrace = false; // Reiniciar para nueva sección
				continue; // Saltamos la línea que contiene "Suspected Mod:"
			}

			// 4) Termina la sección "Suspected Mods:" cuando encontramos "Stacktrace:"
			if (linea.contains("Stacktrace:")) {
				encontradoStacktrace = true;
				encontradoModSospechoso = false; // Terminar esta sección
				continue;
			}

			// 5) Si encontramos una línea vacía después de "Stacktrace:", terminamos la
			// sección actual
			if (encontradoStacktrace && linea.isEmpty()) {
				encontradoStacktrace = false;
				continue;
			}

			// 6) Procesar mod IDs entre "Suspected Mods:" y "Stacktrace:"
			if (encontradoModSospechoso && !encontradoStacktrace) {
				// Saltar líneas que contienen información de stack trace o son demasiado largas
				if (linea.contains(".java:") || linea.startsWith("at ") || linea.startsWith("java.lang.")) {
					continue;
				}

				// Ignorar líneas que son solo información adicional sin mod IDs
				if (linea.startsWith("Mixin class:") || linea.startsWith("Target:")
						|| linea.startsWith("at TRANSFORMER/") || linea.startsWith("at MC-BOOTSTRAP/")) {
					continue;
				}

				// Extraer mod ID usando la lógica mejorada para esta sección
				String modId = extraerModidDeLinea(linea, true);
				if (modId != null && !modId.isEmpty() && !modId.contains(".java")) {
					String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modId;
					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(i, this);
						enlacesPorError.put(mensaje, enlace);
						modidPorError.put(mensaje, modId);
					}
					activado = true;
				}
				continue;
			}

			// 7) Detectar "Fallo al crear la instancia del mod"
			if (linea.contains("Failed to create mod instance. ModID:")) {
				String prefijo = "Failed to create mod instance. ModID: ";
				int indiceInicio = linea.indexOf(prefijo);
				if (indiceInicio != -1) {
					indiceInicio += prefijo.length();
					String resto = linea.substring(indiceInicio).trim();
					StringBuilder sb = new StringBuilder();
					int j = 0;
					while (j < resto.length()) {
						char c = resto.charAt(j);
						if (Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == '+') {
							sb.append(c);
							j++;
						} else {
							break;
						}
					}
					String modID = sb.toString().trim();
					if (!modID.isEmpty()) {
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

			// 8) Detectar modid en el patrón "dispatch for modid"
			Matcher matcherDespacho = patronDespachoModid.matcher(linea);
			while (matcherDespacho.find()) {
				String modID = matcherDespacho.group(1).trim();
				String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
				if (errores.add(mensaje)) {
					String enlace = consola.agregarErrorALectador(i, this);
					enlacesPorError.put(mensaje, enlace);
					modidPorError.put(mensaje, modID);
				}
				activado = true;
			}

			// 9) Detectar mod ID en otras excepciones - SOLO si hay indicadores de error
			if (!encontradoModSection) {
				// Verificar si la línea contiene indicadores de error antes de procesar
				if (patronIndicadorError.matcher(linea).find()) {
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

			// Si hay modid asociado al error, buscar las ubicaciones por nombre
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
}