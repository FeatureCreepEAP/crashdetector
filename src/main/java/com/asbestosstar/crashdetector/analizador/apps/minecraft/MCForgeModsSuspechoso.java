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

	// Patrones para extraer mod IDs de las líneas de error
	private final Pattern pModidEnLinea = Pattern.compile("(?i)for\\s+modid\\s+([a-z0-9_\\-.]+)");
	private final Pattern pModIdDosPuntos = Pattern.compile("(?i)\\bMod\\s*ID\\s*:\\s*([a-z0-9_\\-.]+)");
	private final Pattern pModidIgual = Pattern.compile("(?i)\\bmodid=([a-z0-9_\\-.]+)");
	private final Pattern patronDespachoModid = Pattern.compile(
			"(?i)(?:encountered\\s+an\\s+(?:error|exception)|caught\\s+exception)\\s+during\\s+.*?dispatch\\s+for\\s+modid\\s+([a-z0-9_\\-.]+)");

	// Nueva expresión regular para detectar mod IDs dentro de paréntesis (como
	// Create (create))
	private final Pattern pModidConParentesis = Pattern.compile("\\(([^)]+)\\)");

	// Función para extraer el mod ID de cada línea usando expresiones regulares
	private String extraerModidDeLinea(String linea) {
		if (linea == null || linea.isEmpty())
			return null;

		Matcher m;

		// Buscar mod IDs en el formato (modid)
		m = pModidConParentesis.matcher(linea);
		if (m.find())
			return m.group(1).trim();

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

	// Precálculo de mod IDs para cada línea para optimizar el bucle de análisis
	// principal
	private String[] precalcularModidsPorLinea(String[] lineas) {
		String[] modidsPorLinea = new String[lineas.length];
		for (int i = 0; i < lineas.length; i++) {
			String modid = extraerModidDeLinea(lineas[i]);
			if (modid != null && !modid.isEmpty()) {
				modidsPorLinea[i] = modid;
			}
		}
		return modidsPorLinea;
	}

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		// Precálculo O(N): cada línea se analiza una sola vez para sacar el modid.
		String[] modidsPorLinea = precalcularModidsPorLinea(lineas);

		boolean encontradoModSospechoso = false; // Flag para detectar el inicio de "Suspected Mods"
		boolean encontradoStacktrace = false; // Flag para detectar el fin de la sección "Stacktrace"

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];

			// 1) Detectando "Suspected Mod:" - Solo procesamos las líneas entre "Suspected
			// Mod:" y "Stacktrace:"
			if (linea.contains("Suspected Mod:")) {
				encontradoModSospechoso = true;
				continue; // Saltamos la línea que contiene "Suspected Mod:"
			}

			// 2) Termina la sección "Suspected Mods:" cuando encontramos "Stacktrace:"
			if (linea.contains("Stacktrace:")) {
				encontradoStacktrace = true;
				break; // Salir del bucle cuando llegamos al "Stacktrace"
			}

			// 3) Procesar mod IDs entre "Suspected Mods:" y "Stacktrace:" solo con el nuevo
			// formato
			if (encontradoModSospechoso && !encontradoStacktrace) {
				// Detectar mod ID en esta línea usando la nueva regex para paréntesis
				String modId = extraerModidDeLinea(linea);
				if (modId != null && !modId.isEmpty()) {
					// Procesar el mensaje del error con el modID
					String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modId;
					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(i, this);
						enlacesPorError.put(mensaje, enlace);
						modidPorError.put(mensaje, modId);
					}
					activado = true;
				}
			}

			// 4) Detectar "Fallo al crear la instancia del mod"
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

			// 5) Detectar modid en el patrón "dispatch for modid"
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

			// 6) Detectar mod ID en otras excepciones
			Matcher mEnc = pModidEnLinea.matcher(linea);
			if (mEnc.find()) {
				String modID = modidsPorLinea[i];
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
