package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.TriMap;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Verificación especializada para detectar y resumir todas las
 * {@code NullPointerException} (NPE) que aparecen en la consola.
 * <p>
 * - Analiza tanto el texto crudo de la consola como los trazos ya extraídos por
 * {@link VerificacionDeStackTrace} para asegurar que no se escape ningún caso.
 * - Para cada NPE, identifica (si es posible) el método y el objeto nulo
 * implicado. - Añade un posible origen (JAR, modid o clase) usando solo
 * información del trazo actual, evitando falsos positivos al no usar datos
 * globales de otros errores. - Agrupa errores idénticos mostrando todos los
 * JARs relacionados en un solo mensaje. - La salida es un bloque HTML con lista
 * de errores, pensado para mostrarse en la UI.
 */
public class NullPointer implements Verificaciones {

	/**
	 * Detecta el encabezado de una NPE: java.lang.NullPointerException: <mensaje
	 * opcional>
	 */
	private static final Pattern CABECERA_NPE = Pattern.compile("java\\.lang\\.NullPointerException(?::\\s*)?(.*)",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Detecta mensajes modernos de Java como: "Cannot invoke 'X' because the return
	 * value of 'Y' is null"
	 */
	private static final Pattern FORMATO_CANNOT = Pattern
			.compile("Cannot\\s+(invoke|read|assign)[^\"]*\"([^\"]+)\"[^\"]*\"([^\"]+)\"");

	/**
	 * Patrón común en errores de Gson: intentar usar un JsonObject que es null Ej:
	 * "Cannot invoke \"JsonObject.entrySet()\" because \"jsonobject\" is null"
	 */
	private static final Pattern ERROR_JSON = Pattern.compile("JsonObject\\.[a-zA-Z]+\\(\\).*\"([^\"]+)\" is null");

	/**
	 * Separador de líneas, definido en la interfaz base
	 */
	private static final String NL = Verificaciones.nl;

	/**
	 * Almacena los mensajes de error únicos detectados, agrupados por tipo de error
	 */
	private final Map<String, Set<String>> errores = new HashMap<>();

	/**
	 * Indica si se encontró al menos una NPE
	 */
	private boolean activado = false;

	/**
	 * Almacena el enlace HTML por mensaje base (solo para líneas sueltas)
	 */
	private final Map<String, String> enlacesPorLinea = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		// Limpiar resultados anteriores
		errores.clear();
		activado = false;

		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;

		// Colección de trazos (fatales y no fatales)
		List<VerificacionDeStackTrace.TraceInfo> trazosInfo = new ArrayList<>();
		trazosInfo.addAll(VerificacionDeStackTrace.obtenerTracesConLinea(consola.contenido_verificar));
		trazosInfo.addAll(VerificacionDeStackTrace.obtenerTracesFatalConLinea(consola.contenido_verificar));

		// Analizar cada trazo
		for (VerificacionDeStackTrace.TraceInfo traceInfo : trazosInfo) {
			String trazo = traceInfo.trace;
			if (!trazo.contains("NullPointerException")) {
				continue;
			}

			String metodo = "método desconocido";
			String objeto = "objeto";
			String origen = "";

			Matcher mCannot = FORMATO_CANNOT.matcher(trazo);
			Matcher mCabecera = CABECERA_NPE.matcher(trazo);
			Matcher mJson = ERROR_JSON.matcher(trazo);

			// Caso 1: Mensaje moderno "Cannot invoke X because Y is null"
			if (mCannot.find()) {
				metodo = mCannot.group(2);
				objeto = mCannot.group(3);
			}
			// Caso 2: Gson específico: "JsonObject.entrySet() because 'jsonobject' is null"
			else if (mJson.find()) {
				metodo = "JsonObject.*()";
				objeto = mJson.group(1);
			}
			// Caso 3: Encabezado con descripción adicional
			else if (mCabecera.find()) {
				String detalle = mCabecera.group(1).trim();
				if (!detalle.isEmpty()) {
					metodo = detalle;
				}
			} else {
				// No se pudo extraer información útil
				continue;
			}

			// Buscar origen SOLO en este trazo (evita falsos positivos)
			origen = detectarOrigenEnTraza(trazo, vdst, traceInfo.consolaLineaComenzar);

			// Construir mensaje base (sin el origen)
			String mensajeBase = MonitorDePID.idioma.null_pointer_error(metodo, objeto);

			// Agregar el error al mapa, agrupando por mensaje base
			errores.computeIfAbsent(mensajeBase, k -> new HashSet<>());
			if (!origen.isEmpty()) {
				errores.get(mensajeBase).add(origen);
			}

			activado = true;
		}

		// Analizar líneas sueltas sin trazo completo (ej: errores sin "at ...")
		String[] lineas = consola.contenido_verificar.split(NL);
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			if (linea.contains("NullPointerException") && !linea.contains("at ")
					&& VerificacionDeStackTrace.tracePermite(linea)) {
				procesarLineaSinTraza(linea, vdst, i, consola);
			}
		}
	}

	/**
	 * Procesa una línea con NPE que no tiene stack trace completo
	 */
	private void procesarLineaSinTraza(String linea, VerificacionDeStackTrace vdst, int numeroLinea, Consola consola) {
		String metodo = "desconocido";
		String objeto = "desconocido";

		Matcher mCannot = FORMATO_CANNOT.matcher(linea);
		Matcher mJson = ERROR_JSON.matcher(linea);

		if (mCannot.find()) {
			metodo = mCannot.group(2);
			objeto = mCannot.group(3);
		} else if (mJson.find()) {
			metodo = "JsonObject.*()";
			objeto = mJson.group(1);
		} else {
			return; // No es relevante
		}

		// Buscar origen SOLO en esta línea
		String origen = detectarOrigenEnLinea(linea, vdst, numeroLinea);
		String mensajeBase = MonitorDePID.idioma.null_pointer_error(metodo, objeto);

		// Registrar el error en el sistema de lectura
		String enlace = consola.agregarErrorALectador(numeroLinea, this);
		enlacesPorLinea.put(mensajeBase, enlace);

		// Agregar el error al mapa, agrupando por mensaje base
		errores.computeIfAbsent(mensajeBase, k -> new HashSet<>());
		if (!origen.isEmpty()) {
			errores.get(mensajeBase).add(origen);
		}

		activado = true;
	}

	/**
	 * Busca un origen (mod, JAR o clase) DIRECTAMENTE en el trazo del error. Esto
	 * evita asociar errores con mods que solo aparecen en otros trazos.
	 */
	private String detectarOrigenEnTraza(String trazo, VerificacionDeStackTrace vdst, int lineaConsola) {
		String[] lines = trazo.split(NL);

		// Primero, buscar en las primeras líneas del stack trace (donde ocurre el
		// error)
		for (int i = 0; i < Math.min(lines.length, 5); i++) {
			String linea = lines[i];
			String origen = detectarOrigenEnLinea(linea, vdst, lineaConsola + i);
			if (!origen.isEmpty()) {
				return origen;
			}
		}

		// Si no encontramos nada en las primeras líneas, buscar en el resto del trazo
		for (int i = 5; i < lines.length; i++) {
			String linea = lines[i];
			String origen = detectarOrigenEnLinea(linea, vdst, lineaConsola + i);
			if (!origen.isEmpty()) {
				return origen;
			}
		}

		return ""; // No se encontró origen en este trazo
	}

	/**
	 * Busca un origen (JAR, modid o clase) DIRECTAMENTE en una línea específica.
	 * Normaliza los nombres para evitar problemas con codificación y sufijos.
	 */
	private String detectarOrigenEnLinea(String linea, VerificacionDeStackTrace vdst, int numeroLineaConsola) {
		// 1. Buscar JAR en la línea
		List<String> jarsEncontrados = VerificacionDeStackTrace.extraerJarsDeLinea(linea);
		for (String jar : jarsEncontrados) {
			if (jar.contains(".jar") && !VerificacionDeStackTrace.isJarNoPermite(jar)) {
				return jar;
			}
		}

		// 2. Buscar modid
		String modid = VerificacionDeStackTrace.extraerModidDeLinea(linea);
		if (modid != null && !VerificacionDeStackTrace.esModNoPermite(modid)) {
			return modid;
		}

		// 3. Buscar paquete/clase
		String pack = VerificacionDeStackTrace.extraerPaqueteDeLinea(linea);
		if (pack != null) {
			// Crear una representación adecuada para el método packNoEsPermitido
			String representacion = "0," + numeroLineaConsola; // nivel 0, número de línea real
			if (!vdst.packNoEsPermite(pack, representacion, false)) {
				return pack;
			}
		}

		return ""; // No se encontró origen en esta línea
	}

	/**
	 * Determina un posible origen usando datos globales (último recurso). Solo
	 * usado si el error no tiene trazo.
	 */
	private static String detectarOrigen(VerificacionDeStackTrace vdst) {
		if (!vdst.jars.isEmpty()) {
			String clave = vdst.jars.keySet().iterator().next();
			int idx = clave.indexOf(MonitorDePID.idioma.nivel());
			return idx == -1 ? clave : clave.substring(0, idx);
		}
		if (!vdst.modids.isEmpty()) {
			return vdst.modids.keySet().iterator().next().key1; // key1 es el modid
		}
		if (!vdst.packs.isEmpty()) {
			return vdst.packs.keySet().iterator().next().key1; // key1 es el paquete
		}
		return "";
	}

	@Override
	public Verificaciones nueva() {
		return new NullPointer();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 50f; // Alta prioridad: NPE suele ser crítico
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder sb = new StringBuilder("<ul>");

		// Para cada tipo de error
		for (Map.Entry<String, Set<String>> entry : errores.entrySet()) {
			String mensajeBase = entry.getKey();
			Set<String> origenes = entry.getValue();

			// Si hay origenes, añadirlos al mensaje
			if (!origenes.isEmpty()) {
				StringBuilder origenesStr = new StringBuilder();
				for (String origen : origenes) {
					if (origenesStr.length() > 0) {
						origenesStr.append(", ");
					}
					origenesStr.append(origen);
				}
				mensajeBase += " (" + origenesStr.toString() + ")";
			}

			// Añadir enlace solo si es de línea suelta
			String enlace = enlacesPorLinea.getOrDefault(mensajeBase, "");
			if (!enlace.isEmpty()) {
				mensajeBase += " " + enlace;
			}

			sb.append("<li>").append(mensajeBase).append("</li>");
		}

		sb.append("</ul>");
		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_null_pointer();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}
}