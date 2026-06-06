package com.asbestosstar.crashdetector.analizador;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.StackTracesDenegadosDeMinecraftPorDefecto;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.mapas.BiMap;
import com.asbestosstar.crashdetector.mapas.TriMap;

public class VerificacionDeStackTrace {

	/**
	 * Stacktraces para ignorar
	 */
	public static List<ListaDenegadosTrace> denegados = new ArrayList<ListaDenegadosTrace>();

	// Reglas rápidas: solo "contains" simple.
	public static List<String> denegadosContiene = new ArrayList<String>();

	Consola consola;

	private static final java.util.Set<String> TOKENS_FALSOS_SM = new java.util.HashSet<>(Arrays.asList("app", "APP",
			"a", "A", "b", "B", "mixin", "pl", "re", "accesstransformer", "runtimedistcleaner", "classloading"));

	// Ahora es un BiMap que asocia (nombre JSON, línea_consola) con si es fatal
	public BiMap<String, Integer, Boolean> sm_config = new BiMap<>(); // (nombre JSON, línea_consola, es fatal)

	public TriMap<String, Integer, Integer, String> clases_fatales_no_existentes = new TriMap<>();// (clase,
																									// nivel_prioridad,

	// línea_consola,
	// sospechoso)
	public Map<Integer, TraceInfo> nivel_trazo = new HashMap<>();

	// Estos solo contienen el contenido pero no el nivel
	public List<String> jar_malo = new ArrayList<String>();
	public List<String> modid_malo = new ArrayList<String>();
	public List<String> package_malo = new ArrayList<String>();
	public List<String> brace_malo = new ArrayList<String>();

	public static String[] package_no_permite = { "java.", "net.minecraft", "net.minecraftforge", "org.spongepowered",
			"it.unimi", "com.mojang.", "cpw.", "featurecreep.", "jdk.", "sun.", "com.sun.", "org.lwjgl.", "org.apache.",
			"io.netty", "org.prismlauncher", "io.github.zekerzhayard", "org.multimc", "org.polymc", "org.tlauncher",
			"net.fabricmc", "org.objectweb.asm", "datafixerupper", "org.slf4j", "com.asbestosstar", "srg",
			"asbestosstar.", "org.openjdk", "com.google", "cpw.mods.modlauncher.", "com.modrinth.theseus.",
			"net.neoforged.", "fmlloader", "fmlearlydisplay", "org.lwjgl"

	};

	/**
	 * Lista canónica de todos los TraceInfo completos del último reiniciar().
	 * Ordenados por prioridad (nivel ascendente). ESTA es la fuente que debe usar
	 * ContenidoDeTrazos.
	 */
	public List<TraceInfo> trazos_completos = new ArrayList<>();

	static {
		StackTracesDenegadosDeMinecraftPorDefecto.init();
	}

	public VerificacionDeStackTrace(Consola cons) {
		this.consola = cons;
	}

	public void reiniciar() {

		ConfigBoolean suprimir = ConfigBoolean.de("suprimir_verificacion_de_stacktrazos", false);
		if (suprimir.obtener()) {
			return;
		}

		// === Reset estado ===
		sm_config.clear();
		clases_fatales_no_existentes.clear();

		jar_malo.clear();
		modid_malo.clear();
		package_malo.clear();
		brace_malo.clear();

		nivel_trazo.clear();
		trazos_completos.clear();

		String[] lineas = consola.lineas_verificar;
		if (lineas == null || lineas.length == 0) {
			return;
		}

		List<TraceInfo> tracesFatal = new ArrayList<>();
		List<TraceInfo> tracesNormales = new ArrayList<>();

		// === Una sola pasada para encontrar trazos fatales y normales ===
		int i = 0;
		while (i < lineas.length) {

			String header = lineas[i];
			if (header == null || header.isEmpty()) {
				i++;
				continue;
			}

			boolean esFatal = header.contains("/FATAL]");
			boolean esNormal = false;

			if (!esFatal && i + 1 < lineas.length && !Character.isWhitespace(header.charAt(0))) {
				String next = normalizarLineaStack(lineas[i + 1]);
				esNormal = next != null && next.trim().startsWith("at ");
			}

			if (!esFatal && !esNormal) {
				i++;
				continue;
			}

			int inicio = i;
			int j = i + 1;

			while (j < lineas.length && esParteDeStack(lineas[j])) {
				j++;
			}

			int fin = j - 1;

			if (tracePermitePorRango(lineas, inicio, fin)) {
				TraceInfo base = new TraceInfo(null, inicio, -1, esFatal);
				base.consolaLineaTerminar = fin;

				if (esFatal) {
					tracesFatal.add(base);
				} else {
					tracesNormales.add(base);
				}
			}

			i = Math.max(j, i + 1);
		}

		int nivel_prioridad = 0;

		// === FATALES ===
		Collections.reverse(tracesFatal);

		for (TraceInfo base : tracesFatal) {
			nivel_prioridad++;

			TraceInfo info = construirYProcesarTraceInfo(lineas, base.consolaLineaComenzar,
					base.consolaLineaTerminar - 1, nivel_prioridad, true);

			trazos_completos.add(info);
			nivel_trazo.put(nivel_prioridad, info);
		}

		// === NORMALES ===
		Collections.reverse(tracesNormales);

		for (TraceInfo base : tracesNormales) {
			nivel_prioridad++;

			TraceInfo info = construirYProcesarTraceInfo(lineas, base.consolaLineaComenzar, base.consolaLineaTerminar,
					nivel_prioridad, false);

			trazos_completos.add(info);
			nivel_trazo.put(nivel_prioridad, info);
		}
	}

	private static boolean tracePermitePorRango(String[] lineas, int inicio, int fin) {

		if ((denegadosContiene == null || denegadosContiene.isEmpty()) && (denegados == null || denegados.isEmpty())) {
			return true;
		}

		for (int i = inicio; i <= fin && i < lineas.length; i++) {

			String linea = lineas[i];
			if (linea == null || linea.isEmpty()) {
				continue;
			}

			// === Reglas rápidas: contains simple ===
			for (String texto : denegadosContiene) {
				if (linea.contains(texto)) {
					return false;
				}
			}

			// === Reglas avanzadas: lambdas/predicados ===
			for (ListaDenegadosTrace pred : denegados) {
				if (pred.predicado(linea)) {
					return false;
				}
			}
		}

		return true;
	}

	public static void agregarDenegadoContiene(String texto) {
		if (texto == null || texto.isEmpty()) {
			return;
		}
		denegadosContiene.add(texto);
	}

	public static void agregarDenegadoPredicado(ListaDenegadosTrace predicado) {
		if (predicado == null) {
			return;
		}
		denegados.add(predicado);
	}

	private TraceInfo construirYProcesarTraceInfo(String[] lineas, int inicio, int fin, int nivel, boolean fatal) {

		TraceInfo info = new TraceInfo(null, inicio, nivel, fatal);
		info.consolaLineaTerminar = fin;

		String claseFaltantePendiente = null;
		int lineaClaseFaltantePendiente = -1;

		for (int i = inicio; i <= fin && i < lineas.length; i++) {

			String lineaOriginal = lineas[i];
			String normalizada = normalizarLineaStack(lineaOriginal);
			if (normalizada == null) {
				continue;
			}

			String t = normalizada.trim();
			if (t.isEmpty()) {
				continue;
			}

			// === SpongeMixin JSON, sin split extra ===
			if (t.contains("org.spongepowered.asm.mixin")) {
				procesarJsonMixinEnLinea(t, inicio, fatal);
			}

			// === ClassNotFound / NoClassDef, sin volver a escanear todo el trace ===
			if ((t.contains("ClassNotFoundException") || t.contains("NoClassDefFoundError"))
					&& !esLineaDeAdvertenciaEstandar(t)) {

				claseFaltantePendiente = extraerClaseFaltanteDeLinea(t);
				lineaClaseFaltantePendiente = i;
			}

			if (t.contains("org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException:")) {
				String clase = extraerClaseDeMetadataNoEncontrada(t);
				if (clase != null && !clase.isEmpty()) {
					clases_fatales_no_existentes.put(clase, nivel, i, "");
				}
			}

			// Solo las líneas "at ..." producen LineaTrazo
			if (!t.startsWith("at ")) {
				continue;
			}

			LineaTrazo lt = construirLineaTrazoDesdeLinea(t, nivel, i, fatal);
			if (lt == null) {
				continue;
			}

			info.lineas.add(lt);

			// Si antes vimos una clase faltante, usamos el primer origen válido posterior
			// como sospechoso. Esto evita re-escanear el trace entero.
			if (claseFaltantePendiente != null) {
				clases_fatales_no_existentes.put(claseFaltantePendiente, nivel, lineaClaseFaltantePendiente, lt.origen);

				claseFaltantePendiente = null;
				lineaClaseFaltantePendiente = -1;
			}
		}

		return info;
	}

	private static String extraerClaseFaltanteDeLinea(String linea) {

		if (linea == null) {
			return null;
		}

		String claseFaltante;

		if (linea.contains("ClassNotFoundException")) {
			int startIdx = linea.indexOf("ClassNotFoundException:") + "ClassNotFoundException:".length();
			claseFaltante = linea.substring(startIdx).trim();
		} else if (linea.contains("NoClassDefFoundError")) {
			int startIdx = linea.indexOf("NoClassDefFoundError:") + "NoClassDefFoundError:".length();
			claseFaltante = linea.substring(startIdx).trim();
		} else {
			return null;
		}

		if (claseFaltante.startsWith("Could not initialize class ")) {
			claseFaltante = claseFaltante.substring("Could not initialize class ".length()).trim();
		}

		int espacio = claseFaltante.indexOf(' ');
		if (espacio >= 0) {
			claseFaltante = claseFaltante.substring(0, espacio).trim();
		}

		int paren = claseFaltante.indexOf('(');
		if (paren >= 0) {
			claseFaltante = claseFaltante.substring(0, paren).trim();
		}

		if (claseFaltante.isEmpty()) {
			return null;
		}

		return claseFaltante.replace('.', '/');
	}

	private void procesarJsonMixinEnLinea(String linea, int consolaLineaInicio, boolean fatal) {

		if (linea == null || linea.indexOf(".json") < 0) {
			return;
		}

		for (String nombreJson : escanearJsonsMixin(linea.trim())) {

			if (nombreJson == null) {
				continue;
			}

			nombreJson = nombreJson.trim();

			if (nombreJson.startsWith("[")) {
				nombreJson = nombreJson.substring(1);
			}

			if (!sm_config.containsKey0(nombreJson)) {
				sm_config.put(nombreJson, consolaLineaInicio, fatal);
				CrashDetectorLogger.log("Agregando JSON " + nombreJson);
			}
		}
	}

	private LineaTrazo construirLineaTrazoDesdeLinea(String normalizada, int nivel, int lineaConsola, boolean fatal) {

		if (normalizada == null) {
			return null;
		}

		String t = normalizada.trim();
		if (!t.startsWith("at ")) {
			return null;
		}

		// 1. JAR primero porque tiene prioridad y evita extraer modid/paquete si ya hay
		// origen bueno.
		String jar = extraerPrimerJarDeLinea(t);
		if (jar != null) {
			if (isJarNoPermite(jar)) {
				return null;
			}

			String clase = extraerClaseDeLinea(t);
			if (clase == null || clase.isEmpty()) {
				return null;
			}

			LineaTrazo lt = new LineaTrazo();
			lt.origen = jar;
			lt.clase = clase;
			lt.nivel = nivel;
			lt.lineaConsola = lineaConsola;
			lt.fatal = fatal;
			lt.llaves = t.indexOf('{') >= 0 ? extraerLlavesDeLinea(t) : Collections.<String>emptyList();

			return lt;
		}

		String clase = extraerClaseDeLinea(t);
		if (clase == null || clase.isEmpty()) {
			return null;
		}

		String origen = null;

		// 2. ModID
		String modid = extraerModidDeLinea(t);
		if (modid != null) {
			if (esModNoPermite(modid)) {
				return null;
			}
			origen = modid;
		}

		// 3. Paquete
		if (origen == null) {
			String paquete = extraerPaqueteDeLinea(t);
			if (paquete != null) {
				String dec = nivel + "," + lineaConsola;

				if (packNoEsPermite(paquete, dec, fatal)) {
					return null;
				}

				origen = paquete;
			}
		}

		// 4. Clase fallback
		if (origen == null) {
			if (claseNoEsPermitida(clase)) {
				return null;
			}

			origen = clase;
		}

		LineaTrazo lt = new LineaTrazo();
		lt.origen = origen;
		lt.clase = clase;
		lt.nivel = nivel;
		lt.lineaConsola = lineaConsola;
		lt.fatal = fatal;
		lt.llaves = t.indexOf('{') >= 0 ? extraerLlavesDeLinea(t) : Collections.<String>emptyList();

		return lt;
	}

	private static String extraerPrimerJarDeLinea(String linea) {

		if (linea == null) {
			return null;
		}

		int inicio = 0;

		while (true) {
			int abrir = linea.indexOf('[', inicio);
			if (abrir < 0) {
				return null;
			}

			int cerrar = linea.indexOf(']', abrir + 1);
			if (cerrar < 0) {
				return null;
			}

			int jar = linea.indexOf(".jar", abrir + 1);
			if (jar >= 0 && jar < cerrar) {
				return linea.substring(abrir + 1, jar + 4);
			}

			inicio = cerrar + 1;
		}
	}

	private static boolean claseNoEsPermitida(String clase) {

		if (clase == null || clase.isEmpty()) {
			return true;
		}

		for (String pref : package_no_permite) {
			if (pref == null || pref.isEmpty()) {
				continue;
			}

			String prefSlash = pref.replace('.', '/');

			if (clase.startsWith(prefSlash)) {
				return true;
			}
		}

		return false;
	}

	private TraceInfo construirTraceInfo(String trace, int consolaLineaInicio, int nivel, boolean fatal) {

		TraceInfo info = new TraceInfo(trace, consolaLineaInicio, nivel, fatal);

		String[] lineas = dividirPorCadena(trace, Verificaciones.nl);

		for (int i = 0; i < lineas.length; i++) {

			String normalizada = normalizarLineaStack(lineas[i]);
			if (normalizada == null) {
				continue;
			}

			normalizada = normalizada.trim();
			if (!normalizada.startsWith("at ")) {
				continue;
			}

			int lineaConsola = consolaLineaInicio + i;

			// === EXTRAER CLASE REAL ===
			String clase = extraerClaseDeLinea(normalizada);
			if (clase == null || clase.isEmpty()) {
				continue;
			}

			String origen = null;

			// ---- 1. VERIFICAR MODID (Para denegar o usar) ----
			String modid = extraerModidDeLinea(normalizada);
			if (modid != null) {
				// Si el ModID está prohibido, saltamos la línea inmediatamente
				if (esModNoPermite(modid)) {
					continue;
				}
				// Lo guardamos como candidato temporal
				origen = modid;
			}

			// ---- 2. VERIFICAR PAQUETE (Para denegar o usar) ----
			String paquete = extraerPaqueteDeLinea(normalizada);
			if (paquete != null) {
				String dec = nivel + "," + lineaConsola;
				// Si el paquete está prohibido (ej. net.minecraft), saltamos la línea
				if (packNoEsPermite(paquete, dec, fatal)) {
					continue;
				}
				// Si no teníamos ModID, usamos el paquete como candidato
				if (origen == null) {
					origen = paquete;
				}
			}

			// ---- 3. VERIFICAR JAR (PRIORIDAD MÁXIMA SI EXISTE) ----
			List<String> jars = extraerJarsDeLinea(normalizada);
			if (!jars.isEmpty()) {
				String jar = jars.get(0);
				// Si el JAR está prohibido, saltamos la línea
				if (isJarNoPermite(jar)) {
					continue;
				}
				// Si el JAR es válido, SOBRESCRIBIMOS el origen anterior.
				// El JAR tiene prioridad sobre el ModID y el Paquete.
				origen = jar;
			}

			// ---- 4. VERIFICAR CLASE (FALLBACK FINAL) ----
			// Si después de todo no hay origen, verificamos la clase
			if (origen == null) {
				boolean claseDenegada = false;
				for (String pref : package_no_permite) {
					if (pref == null || pref.isEmpty())
						continue;
					String prefSlash = pref.replace('.', '/');
					if (clase.startsWith(prefSlash)) {
						claseDenegada = true;
						break;
					}
				}
				if (claseDenegada) {
					continue;
				}
				// Si pasó los filtros, usamos la clase como origen
				origen = clase;
			}

			LineaTrazo lt = new LineaTrazo();
			lt.origen = origen;
			lt.clase = clase;
			lt.nivel = nivel;
			lt.lineaConsola = lineaConsola;
			lt.fatal = fatal;

			// Llaves tipo {re:classloading}
			lt.llaves = extraerLlavesDeLinea(normalizada);

			info.lineas.add(lt);
		}

		return info;
	}

	/**
	 * Extrae todas las líneas de stacktrace de forma unificada. No separa jars /
	 * modids / paquetes. Cada línea representa una causa potencial real.
	 */
	public List<LineaTrazo> extraerLineasDeTrazoUnificadas() {

		List<LineaTrazo> resultado = new ArrayList<>();

		if (nivel_trazo == null || nivel_trazo.isEmpty()) {
			return resultado;
		}

		for (Map.Entry<Integer, TraceInfo> entrada : nivel_trazo.entrySet()) {

			int nivel = entrada.getKey();
			TraceInfo info = entrada.getValue();

			boolean fatal = info.trace.contains("/FATAL]");
			String[] lineas = dividirPorCadena(info.trace, Verificaciones.nl);

			for (int i = 0; i < lineas.length; i++) {

				String normalizada = normalizarLineaStack(lineas[i]);
				if (normalizada == null)
					continue;

				normalizada = normalizada.trim();
				if (!normalizada.startsWith("at "))
					continue;

				int lineaConsola = info.consolaLineaComenzar + i;

				String clase = extraerClaseDeLinea(normalizada);
				if (clase == null || clase.isEmpty())
					continue;

				// === Resolver origen ===
				String origen = null;

				// JAR
				List<String> jars = extraerJarsDeLinea(normalizada);
				if (!jars.isEmpty()) {
					String jar = jars.get(0);
					if (!isJarNoPermite(jar)) {
						origen = jar;
					}
				}

				// MODID
				if (origen == null) {
					String modid = extraerModidDeLinea(normalizada);
					if (modid != null && !esModNoPermite(modid)) {
						origen = modid;
					}
				}

				// PAQUETE
				if (origen == null) {
					String pack = extraerPaqueteDeLinea(normalizada);
					if (pack != null && !packNoEsPermite(pack, nivel + "," + lineaConsola, fatal)) {
						origen = pack;
					}
				}

				// FALLBACK A CLASE
				if (origen == null) {
					origen = clase;
				}

				LineaTrazo lt = new LineaTrazo();
				lt.origen = origen;
				lt.clase = clase;
				lt.nivel = nivel;
				lt.lineaConsola = lineaConsola;
				lt.fatal = fatal;

				resultado.add(lt);
			}
		}

		return resultado;
	}

	/**
	 * Información sobre un stack trace incluyendo su contenido y la línea inicial
	 * en la consola
	 */
	public static class TraceInfo {

		public int consolaLineaTerminar = -1;
		public final String trace;
		public final int consolaLineaComenzar;

		public final int nivel;
		public final boolean fatal;

		// Todas las líneas "at ..." normalizadas y procesadas
		public final List<LineaTrazo> lineas = new ArrayList<>();

		TraceInfo(String trace, int consolaLineaComenzar, int nivel, boolean fatal) {
			this.trace = trace;
			this.consolaLineaComenzar = consolaLineaComenzar;
			this.nivel = nivel;
			this.fatal = fatal;
		}
	}

	public static String extraerClaseDeLinea(String linea) {
		if (linea == null) {
			return "";
		}

		String texto = linea.trim();

		// Quitar comentarios tipo "// at ..."
		if (texto.startsWith("//")) {
			texto = texto.substring(2).trim();
		}

		// Quitar prefijo "at "
		if (texto.startsWith("at ")) {
			texto = texto.substring(3).trim();
		}

		// Quitar prefijos de cargador (pueden venir anidados: knot//MC//...)
		texto = limpiarPrefijosYCargadores(texto);

		// Cortar "(Clase.java:123)"
		int idxPar = texto.indexOf('(');
		if (idxPar >= 0) {
			texto = texto.substring(0, idxPar).trim();
		}

		// Eliminar sufijos sintéticos ($$Lambda, $$Enhancer, etc.)
		int idxDoble = texto.indexOf("$$");
		if (idxDoble >= 0) {
			texto = texto.substring(0, idxDoble).trim();
		}

		// Quitar el nombre del método (si existe)
		// Ej: net.minecraft.client.MinecraftClient.render ->
		// net.minecraft.client.MinecraftClient
		// Ej: net.minecraft.class_156.method_654 -> net.minecraft.class_156
		int ultimoPunto = texto.lastIndexOf('.');
		if (ultimoPunto > 0) {
			texto = texto.substring(0, ultimoPunto).trim();
		}

		// Normalizar a formato interno con '/'
		texto = texto.replace('.', '/').trim();

		// Limpieza defensiva de barras iniciales
		while (texto.startsWith("/")) {
			texto = texto.substring(1);
		}

		return texto;
	}

	/**
	 * Elimina prefijos de cargador y metadata sin destruir la ruta real del
	 * paquete. Soporta prefijos anidados como: knot//MC//net.minecraft...
	 *
	 * Prefijos soportados: - knot//, knott//, app//, MC// (pueden repetirse) - jdk/
	 * (puede repetirse) - TRANSFORMER/, MC-BOOTSTRAP/, LAYER PLUGIN/ (con metadata)
	 */
	private static String limpiarPrefijosYCargadores(String texto) {
		if (texto == null || texto.isEmpty()) {
			return "";
		}

		String t = texto.trim();

		// Quitar prefijos simples anidados (con //)
		boolean cambio;
		do {
			cambio = false;

			if (t.startsWith("knot//")) {
				t = t.substring("knot//".length()).trim();
				cambio = true;
			}
			if (t.startsWith("knott//")) {
				t = t.substring("knott//".length()).trim();
				cambio = true;
			}
			if (t.startsWith("app//")) {
				t = t.substring("app//".length()).trim();
				cambio = true;
			}
			if (t.startsWith("MC//")) {
				t = t.substring("MC//".length()).trim();
				cambio = true;
			}

			// Quitar prefijos simples con /
			if (t.startsWith("jdk/")) {
				t = t.substring("jdk/".length()).trim();
				cambio = true;
			}

			// Agregar este bloque junto con los otros prefijos con metadata
			if (t.startsWith("SECURE-BOOTSTRAP/")) {
				t = t.substring("SECURE-BOOTSTRAP/".length()).trim();
				// Saltar el primer segmento (modulo/modid + version) hasta la siguiente '/'
				int idx = t.indexOf('/');
				if (idx >= 0 && idx + 1 < t.length()) {
					t = t.substring(idx + 1).trim();
				}
			}

			// Agregar este bloque junto con los otros prefijos con metadata
			if (t.startsWith("MC-BOOTSTRAP/")) {
				t = t.substring("MC-BOOTSTRAP/".length()).trim();
				// Saltar el primer segmento (modulo/modid + version) hasta la siguiente '/'
				int idx = t.indexOf('/');
				if (idx >= 0 && idx + 1 < t.length()) {
					t = t.substring(idx + 1).trim();
				}
			}

			// Agregar este bloque junto con los otros prefijos con metadata
			if (t.startsWith("MC/")) {
				t = t.substring("MC/".length()).trim();
				// Saltar el primer segmento (modulo/modid + version) hasta la siguiente '/'
				int idx = t.indexOf('/');
				if (idx >= 0 && idx + 1 < t.length()) {
					t = t.substring(idx + 1).trim();
				}
			}

		} while (cambio);

		// Quitar prefijos con metadata del estilo:
		// TRANSFORMER/<modid>@<ver>/ruta.clase.metodo
		// MC-BOOTSTRAP/<modulo>@<ver>/ruta.clase.metodo
		// LAYER PLUGIN/<plugin>@<ver>/ruta.clase.metodo
		String[] prefijosMetadata = { "TRANSFORMER/", "MC-BOOTSTRAP/", "LAYER PLUGIN/", "MC/" };

		for (String pref : prefijosMetadata) {
			if (t.startsWith(pref)) {
				t = t.substring(pref.length()).trim();

				// Saltar el primer segmento (modulo/modid + version) hasta la siguiente '/'
				int idx = t.indexOf('/');
				if (idx >= 0 && idx + 1 < t.length()) {
					t = t.substring(idx + 1).trim();
				}
				break;
			}
		}

		// Por si tras quitar metadata quedaron prefijos simples otra vez
		do {
			cambio = false;

			if (t.startsWith("knot//MC//")) {
				t = t.substring("knot//MC//".length()).trim();
				cambio = true;
			}
			if (t.startsWith("knott//")) {
				t = t.substring("knott//".length()).trim();
				cambio = true;
			}
			if (t.startsWith("app//")) {
				t = t.substring("app//".length()).trim();
				cambio = true;
			}
			if (t.startsWith("MC//")) {
				t = t.substring("MC//".length()).trim();
				cambio = true;
			}
			if (t.startsWith("jdk/")) {
				t = t.substring("jdk/".length()).trim();
				cambio = true;
			}

		} while (cambio);

		return t;
	}

	/**
	 * Obtiene stack traces fatales junto con su línea inicial en la consola
	 */
	public static List<TraceInfo> obtenerTracesFatalConLinea(Consola log) {

		List<TraceInfo> resultado = new ArrayList<>();
		String[] lineas = log.lineas_verificar;

		for (int i = 0; i < lineas.length; i++) {

			String header = lineas[i];
			if (header == null || !header.contains("/FATAL]")) {
				continue;
			}

			StringBuilder traza = new StringBuilder(header);
			int j = i + 1;

			while (j < lineas.length && esParteDeStack(lineas[j])) {
				traza.append(Verificaciones.nl).append(lineas[j]);
				j++;
			}

			String traceStr = traza.toString();
			if (!tracePermite(traceStr)) {
				continue;
			}

			// nivel y fatal se asignan después
			resultado.add(new TraceInfo(traceStr, i, -1, true));

			i = j - 1;
		}

		return resultado;
	}

	/**
	 * Obtiene stack traces normales junto con su línea inicial en la consola
	 */
	public static List<TraceInfo> obtenerTracesConLinea(Consola log) {

		List<TraceInfo> resultado = new ArrayList<>();
		String[] lineas = log.lineas_verificar;

		int i = 0;
		while (i < lineas.length - 1) {

			String header = lineas[i];
			if (header == null || header.isEmpty()) {
				i++;
				continue;
			}

			if (Character.isWhitespace(header.charAt(0))) {
				i++;
				continue;
			}

			String next = normalizarLineaStack(lineas[i + 1]);
			if (next == null || !next.trim().startsWith("at ")) {
				i++;
				continue;
			}

			StringBuilder traza = new StringBuilder(header);
			int j = i + 1;

			while (j < lineas.length && esParteDeStack(lineas[j])) {
				traza.append(Verificaciones.nl).append(lineas[j]);
				j++;
			}

			String traceStr = traza.toString();
			if (tracePermite(traceStr)) {
				resultado.add(new TraceInfo(traceStr, i, -1, false));
			}

			i = j;
		}

		return resultado;
	}

	public void procesarTrace(String trace, boolean fatal, int nivel, int consolaLineaInicio) {

		// === SpongeMixin JSON ===
		List<String> jsons = obtenerArchivosJsonEnMixinExceptions(trace);
		for (String json : jsons) {
			if (!sm_config.containsKey0(json)) {
				sm_config.put(json, consolaLineaInicio, fatal);
				CrashDetectorLogger.log("Agregando JSON " + json);
			}
		}

		// === ClassNotFound / NoClassDef ===
		String[] arr = dividirPorCadena(trace, Verificaciones.nl);

		for (int i = 0; i < arr.length; i++) {

			String linea = normalizarLineaStack(arr[i]);
			if (linea == null) {
				continue;
			}

			String t = linea.trim();
			if (t.isEmpty()) {
				continue;
			}

			int consolaLinea = consolaLineaInicio + i;

			if ((t.contains("ClassNotFoundException") || t.contains("NoClassDefFoundError"))
					&& !esLineaDeAdvertenciaEstandar(t)) {

				Map.Entry<String, String> res = procesarErrorClaseNoEncontrada(t, arr, consolaLinea, nivel);

				if (res != null) {
					clases_fatales_no_existentes.put(res.getKey(), nivel, consolaLinea, res.getValue());
				}
			}

			if (t.contains("org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException:")) {

				String clase = extraerClaseDeMetadataNoEncontrada(t);
				if (clase != null && !clase.isEmpty()) {
					clases_fatales_no_existentes.put(clase, nivel, consolaLinea, "");
				}
			}
		}
	}

	/**
	 * 
	 * Otra forma de "clase faltante" desde SpongeMixin:
	 * org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException:
	 * <clase> Ver referencia en mcforge discord:
	 * https://discord.com/channels/1129059589325852724/1129069799545241703/1427526571622928384
	 * 
	 * Extrae la clase desde:
	 * "org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException:
	 * com.ejemplo.Algo" Devuelve en formato con barras: com/ejemplo/Algo
	 */
	private static String extraerClaseDeMetadataNoEncontrada(String linea) {
		if (linea == null)
			return null;
		final String clave = "ClassMetadataNotFoundException:";
		int p = linea.indexOf(clave);
		if (p < 0)
			return null;
		String clase = linea.substring(p + clave.length()).trim();
		// Normalizamos a forma con '/' para ser consistente con otras rutas de clase
		return clase.replace('.', '/');
	}

	public static boolean esLineaDeAdvertenciaEstandar(String l) {
		if (l == null)
			return false;
		String t = l.trim();

		// Casos típicos con corchetes de log, por ejemplo:
		// [08:53:21] [mixin/WARN]: ..., [main] [Render thread/WARN]: ...
		if (t.contains("/WARN]"))
			return true;
		if (t.contains("] [WARN"))
			return true;

		// Forma genérica: dos bloques entre [ ] y el segundo contiene WARN antes de ':'
		// y luego cualquier texto.
		if (coincideWarnDobleBloque(t))
			return true;

		// También cubrir formatos tipo "[mixin/WARN]:" directamente
		if (coincideWarnBloqueSimple(t))
			return true;

		return false;
	}

	/**
	 * Extrae nombres de jars de una línea de stack trace. Ejemplo:
	 * ~[Ponder-Forge-1.20.1-1.0.80.jar%23795!/:1.0.80] ->
	 * "Ponder-Forge-1.20.1-1.0.80.jar"
	 */
	public static List<String> extraerJarsDeLinea(String linea) {
		List<String> jars = new ArrayList<>();
		int inicio = 0;

		while (true) {
			int abrir = linea.indexOf('[', inicio);
			if (abrir == -1)
				break;
			int cerrar = linea.indexOf(']', abrir);
			if (cerrar == -1)
				break;

			String contenido = linea.substring(abrir + 1, cerrar);

			// Buscar ".jar" y cortar ahí
			int indiceJar = contenido.indexOf(".jar");
			if (indiceJar != -1) {
				String nombreJar = contenido.substring(0, indiceJar + 4);
				jars.add(nombreJar);
			}

			inicio = cerrar + 1;
		}

		return jars;
	}

	/**
	 * Extrae un modid de una línea de stack trace de forma estricta. Prioriza el
	 * formato TRANSFORMER/<modid>@<version>. En caso de no coincidir, opcionalmente
	 * intenta con un patrón simple "at <modid>@<ver>/...". Devuelve null si no se
	 * detecta un modid confiable.
	 */
	public static String extraerModidDeLinea(String linea) {
		if (linea == null)
			return null;
		String t = normalizarLineaStack(linea);
		if (t == null)
			return null;
		t = t.trim();

		String transformer = modidDesdeTransformer(t);
		if (transformer != null) {
			return transformer;
		}

		String simple = modidDesdeSimple(t);
		if (simple != null) {
			return esModNoPermite(simple) ? null : simple;
		}

		// Detección adicional: handler$...$<modid>$...
		// Ej.: "...BufferBuilder.handler$cdk000$iris$beforeNext..."
		int h = t.indexOf("handler$");
		if (h >= 0) {
			String tail = t.substring(h + "handler$".length());
			String[] segs = dividirPorChar(tail, '$');
			for (String s : segs) {
				String k = sane(s);
				if (k.isEmpty())
					continue;
				if (TOKENS_FALSOS_SM.contains(k))
					continue;
				if (pareceMetodoOClase(k))
					continue;
				if (esModIdPlausible(k) && !esModNoPermite(k)) {
					return k; // p.ej. "iris"
				}
			}
		}

		return null;
	}

	/**
	 * Extrae el nombre de paquete de una línea de stack trace. Maneja: - Módulos de
	 * Java (ej: java.base@21.0.7/Clase.java:línea) - Clases lambda sintéticas
	 * ($$Lambda/0x...) - Casos normales de paquetes y clases
	 *
	 * @param linea Línea completa del stack trace
	 * @return El paquete extraído o null si no se puede determinar
	 */
	public static String extraerPaqueteDeLinea(String linea) {
		if (linea == null)
			return null;
		String texto = normalizarLineaStack(linea);
		if (texto == null)
			return null;
		texto = texto.trim();
		if (!texto.startsWith("at "))
			return null;

		// quitar "at "
		texto = texto.substring(3).trim();

		// si aún quedara un prefijo tipo "knot//"
		for (String p : PREFIJOS_CARGADOR) {
			if (texto.startsWith(p)) {
				texto = texto.substring(p.length());
				break;
			}
		}

		// cortar "(...)" si existe
		int idxPar = texto.indexOf('(');
		if (idxPar != -1)
			texto = texto.substring(0, idxPar);

		// manejar lambdas sintéticas
		int idxLambda = texto.indexOf("$$Lambda");
		if (idxLambda != -1)
			texto = texto.substring(0, idxLambda);

		// purgar direcciones /0x... (equivale a replaceAll("/0x[0-9a-fA-F]+.*", ""))
		int idx0x = texto.indexOf("/0x");
		while (idx0x >= 0) {
			int hexPos = idx0x + 3;
			if (hexPos < texto.length() && esHex(texto.charAt(hexPos))) {
				texto = texto.substring(0, idx0x);
				break;
			}
			idx0x = texto.indexOf("/0x", idx0x + 1);
		}

		// formato módulos java: paquete@ver/Clase -> quedarnos con paquete
		if (texto.contains("@") && texto.contains("/")) {
			int barra = texto.indexOf('/');
			texto = texto.substring(0, barra);
		}

		int ultimoPunto = texto.lastIndexOf('.');
		if (ultimoPunto > 0) {
			return texto.substring(0, ultimoPunto);
		}
		return null;
	}

	/**
	 * Extrae contenido dentro de llaves {} Ejemplo: {re:classloading}
	 * 
	 * @param linea Línea de stack trace a procesar
	 * @return Lista de contenidos encontrados dentro de llaves
	 */
	public static List<String> extraerLlavesDeLinea(String linea) {
		List<String> llaves = new ArrayList<>();
		if (linea == null) {
			return llaves;
		}
		// Escaneo manual equivalente a "\\{([^}]+)\\}": pares { ... } sin '}' interno.
		int i = 0;
		int n = linea.length();
		while (i < n) {
			int abrir = linea.indexOf('{', i);
			if (abrir < 0) {
				break;
			}
			int cerrar = linea.indexOf('}', abrir + 1);
			if (cerrar < 0) {
				break;
			}
			if (cerrar > abrir + 1) { // [^}]+ requiere al menos un carácter
				String contenido = linea.substring(abrir + 1, cerrar).trim();
				if (esLlaveDeSistema(contenido)) {
					llaves.add(contenido);
				}
			}
			i = cerrar + 1;
		}
		return llaves;
	}

	// Heurística general para decidir si el contenido entre llaves representa
	// pares "clave:valor" de sistema/cargador y no un toString() de objetos.
	// Criterios:
	// - No debe contener '=' (propio de toString tipo name=value)
	// - Debe tener al menos un ':'
	// - Debe consistir en segmentos separados por comas
	// - Cada segmento debe tener forma clave:valor con clave alfanumérica sencilla
	// - Evitar valores con espacios “largos” (frases) u otros '='
	private static boolean esLlaveDeSistema(String c) {
		if (c == null)
			return false;
		String s = c.trim();

		// Rechazar formatos típicos de toString()
		if (s.indexOf('=') >= 0)
			return false;

		// Debe existir al menos un ':'
		if (s.indexOf(':') < 0)
			return false;

		// Equivale a s.split("\\s*,\\s*") sobre 's' (ya recortado): segmentos entre
		// comas con el whitespace adyacente eliminado.
		List<String> segs = new ArrayList<>();
		boolean hayComa = s.indexOf(',') >= 0;
		int ini = 0;
		int len = s.length();
		for (int i = 0; i <= len; i++) {
			if (i == len || s.charAt(i) == ',') {
				segs.add(s.substring(ini, i).trim());
				ini = i + 1;
			}
		}
		if (hayComa) {
			// split con límite 0: quitar segmentos vacíos finales
			while (!segs.isEmpty() && segs.get(segs.size() - 1).isEmpty()) {
				segs.remove(segs.size() - 1);
			}
		}
		if (segs.isEmpty())
			return false;

		int paresValidos = 0;

		for (String seg : segs) {
			int p = seg.indexOf(':');
			if (p <= 0 || p == seg.length() - 1)
				return false; // sin clave o sin valor

			String clave = seg.substring(0, p).trim();
			String valor = seg.substring(p + 1).trim();

			// clave alfanumérica simple para evitar frases
			if (!esClaveLlaveValida(clave))
				return false;

			// evitar valores con '=' (otra señal de toString) o espacios excesivos tipo
			// frases
			if (valor.indexOf('=') >= 0)
				return false;
			if (tieneEspaciosDoblesRegex(valor))
				return false;

			paresValidos++;
		}

		// Requiere al menos 2 pares para ser confiable
		return paresValidos >= 2;
	}

	/**
	 * Procesa líneas que contienen ClassNotFoundException o NoClassDefFoundError
	 * Busca la clase faltante y trata de identificar el mod/jar sospechoso
	 * 
	 * @param linea             Línea que contiene el error de clase
	 * @param arr               Array completo de líneas del stack trace
	 * @param consoleLineNumber Número de línea actual EN LA CONSOLA
	 * @param nivel_prioridad   Nivel de prioridad (no es el número de línea)
	 * @return Un entry con la clase faltante y el sospechoso, o null si no se
	 *         encontró
	 */
	public Map.Entry<String, String> procesarErrorClaseNoEncontrada(String linea, String[] arr, int consoleLineNumber,
			int nivel_prioridad) {
		String claseFaltante = null;
		CrashDetectorLogger.log("procesarErrorClaseNoEncontrada " + linea);

		if (linea.contains("ClassNotFoundException")) {
			int startIdx = linea.indexOf("ClassNotFoundException:") + "ClassNotFoundException:".length();
			claseFaltante = linea.substring(startIdx).trim();
		} else {
			int startIdx = linea.indexOf("NoClassDefFoundError:") + "NoClassDefFoundError:".length();
			claseFaltante = linea.substring(startIdx).trim();
		}
		CrashDetectorLogger.log(claseFaltante);

		// Ahora extraemos solo el nombre de la clase (eliminamos mensajes adicionales)
		if (claseFaltante.contains(" ")) {
			CrashDetectorLogger.log("espacio");
			int spaceIdx = claseFaltante.indexOf(' ');
			CrashDetectorLogger.log("spaceidx");
			if (spaceIdx != -1) {
				CrashDetectorLogger.log("-1");
				if (claseFaltante.startsWith("Could not initialize class ")) {
					claseFaltante = claseFaltante.replace("Could not initialize class ", "");
					spaceIdx = claseFaltante.indexOf(' ');
					CrashDetectorLogger.log(claseFaltante);
				}
			}
		}

		if (claseFaltante.contains("(")) {
			CrashDetectorLogger.log("(");
			int parenIdx = claseFaltante.indexOf('(');
			if (parenIdx != -1) {
				claseFaltante = claseFaltante.substring(0, parenIdx);
			}
		}
		claseFaltante = claseFaltante.replace(".", "/");

		// Variables para almacenar el mejor origen encontrado
		String mejorOrigen = null;
		boolean esOrigenDirecto = false;
		boolean esTransformer = false;

		// Primero buscamos desde el principio del stack trace (cerca del error) hacia
		// abajo
		for (int i = 0; i < arr.length; i++) {
			String notrim = arr[i];
			String t = notrim.trim();

			// Ignorar líneas irrelevantes
			if (t.isEmpty() || t.contains("... more")) {
				continue;
			}

			// 1. Verificar si es una línea TRANSFORMER (prioridad máxima)
			boolean esLineaTransformer = t.startsWith("at TRANSFORMER/");

			// 2. Verificar si es una línea directamente relacionada con el error
			boolean esLineaDirecta = t.contains(claseFaltante.replace("/", "."));

			// 3. Extraer JARs
			List<String> jarsEncontrados = extraerJarsDeLinea(t);
			for (String jar : jarsEncontrados) {
				if (jar.contains(".jar") && !isJarNoPermite(jar)) {
					// Si encontramos un JAR válido y es una línea directa o TRANSFORMER, es
					// prioritario
					if (esLineaDirecta || esLineaTransformer) {
						mejorOrigen = jar;
						esOrigenDirecto = esLineaDirecta;
						esTransformer = esLineaTransformer;
						// Continuamos buscando pero priorizamos este origen
					}
					// Si no hemos encontrado un origen mejor, guardamos este
					else if (mejorOrigen == null) {
						mejorOrigen = jar;
					}
				}
			}

			// 4. Extraer modid
			String modid = extraerModidDeLinea(t);
			if (modid != null && !esModNoPermite(modid)) {
				// Si encontramos un modid válido y es una línea directa o TRANSFORMER, es
				// prioritario
				if (esLineaDirecta || esLineaTransformer) {
					mejorOrigen = modid;
					esOrigenDirecto = esLineaDirecta;
					esTransformer = esLineaTransformer;
					// Continuamos buscando pero priorizamos este origen
				}
				// Si no hemos encontrado un origen mejor, guardamos este
				else if (mejorOrigen == null) {
					mejorOrigen = modid;
				}
			}

			// 5. Extraer paquetes
			String pack = extraerPaqueteDeLinea(t);
			if (pack != null) {
				String representacion = Integer.toString(nivel_prioridad) + "," + Integer.toString(consoleLineNumber);
				if (!packNoEsPermite(pack, representacion, false)) {
					// Si encontramos un paquete válido y es una línea directa o TRANSFORMER, es
					// prioritario
					if (esLineaDirecta || esLineaTransformer) {
						mejorOrigen = pack;
						esOrigenDirecto = esLineaDirecta;
						esTransformer = esLineaTransformer;
						// Continuamos buscando pero priorizamos este origen
					}
					// Si no hemos encontrado un origen mejor, guardamos este
					else if (mejorOrigen == null) {
						mejorOrigen = pack;
					}
				}
			}

			// Si ya encontramos un origen TRANSFORMER, es muy probable que sea el culpable
			if (esTransformer) {
				break;
			}
		}

		// Devolver el mejor origen encontrado
		if (mejorOrigen != null) {
			CrashDetectorLogger.log("Origen identificado: " + mejorOrigen
					+ (esTransformer ? " (TRANSFORMER)" : esOrigenDirecto ? " (directo)" : ""));
			return new AbstractMap.SimpleEntry<>(claseFaltante, mejorOrigen);
		}

		return null;
	}

	// Heurística: ¿parece un modid válido?
	private static boolean esModIdPlausible(String s) {
		// minúsculas, dígitos, guion y guion_bajo, opcionalmente con puntos:
		// equivale a "^[a-z0-9_\\-.]{2,64}$"
		if (s == null) {
			return false;
		}
		int n = s.length();
		if (n < 2 || n > 64) {
			return false;
		}
		for (int i = 0; i < n; i++) {
			if (!esCharModidPunto(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	// Heurística: ¿parece nombre de método o clase?
	private static boolean pareceMetodoOClase(String s) {
		if (s == null)
			return false;
		if (s.indexOf('(') >= 0)
			return true; // foo(bar)
		if (s.indexOf('.') >= 0)
			return true; // paquete.clase
		if (!s.equals(s.toLowerCase()))
			return true; // camelCase o Mayúsculas
		return false;
	}

	/**
	 * Procesa los handlers de SpongeMixin para extraer el modid
	 * 
	 * @param pack  Cadena que contiene el handler de SpongeMixin
	 * @param dec   Cadena que contiene el nivel de prioridad y número de línea de
	 *              la consola (ej: "1,23")
	 * @param fatal Indica si es un error fatal
	 */
	public void processarSMHandler(String pack, String dec, boolean fatal) {
		try {
			int idx = pack.indexOf("handler$");
			if (idx < 0)
				return;

			String tail = pack.substring(idx + "handler$".length());
			String[] segs = dividirPorChar(tail, '$');
			if (segs.length == 0)
				return;

			String candidato = null;

			// Preferir primer segmento si parece modid real
			String s0 = sane(segs[0]);
			if (!TOKENS_FALSOS_SM.contains(s0) && esModIdPlausible(s0) && !esModNoPermite(s0)) {
				if (segs.length >= 2) {
					String s1 = sane(segs[1]);
					if (pareceMetodoOClase(s1) || !esModIdPlausible(s1)) {
						candidato = s0;
					}
				} else {
					candidato = s0;
				}
			}

			// Fallback: seg[1]
			if (candidato == null && segs.length >= 2) {
				String s1 = sane(segs[1]);
				if (!TOKENS_FALSOS_SM.contains(s1) && esModIdPlausible(s1) && !esModNoPermite(s1)
						&& !pareceMetodoOClase(s1)) {
					candidato = s1;
				}
			}

			// Escaneo extendido
			if (candidato == null && segs.length >= 2) {
				for (int i = 2; i < segs.length; i++) {
					String si = sane(segs[i]);
					if (TOKENS_FALSOS_SM.contains(si))
						continue;
					if (esModIdPlausible(si) && !esModNoPermite(si) && !pareceMetodoOClase(si)) {
						candidato = si;
						break;
					}
				}
			}

			if (candidato == null)
				return;

			if (!modid_malo.contains(candidato)) {
				modid_malo.add(candidato);
				String[] lvlLinea = dividirPorChar(dec, ',');
				int nivel_prioridad = Integer.parseInt(lvlLinea[0]);
				int consoleLineNumber = Integer.parseInt(lvlLinea[1]);
				// modids.put(candidato, nivel_prioridad,
				// consoleLineNumber,extraerClaseDeLinea(dec) ,fatal);
				CrashDetectorLogger.log("Mod ID por handler detectado: " + candidato);
			}
		} catch (Exception ex) {
			CrashDetectorLogger.log("processarSMHandler ignorado: " + ex.getMessage());
		}
	}

	// Limpieza básica de un segmento: corta "(", y si hay puntos, deja el primer
	// token
	private static String sane(String s) {
		if (s == null)
			return "";
		int p = s.indexOf('(');
		if (p >= 0)
			s = s.substring(0, p);
		// En handler suelen venir cosas como "paquete.Clase.metodo"
		// nos quedamos con el primer token para comparar contra modid
		int dot = s.indexOf('.');
		if (dot >= 0)
			s = s.substring(0, dot);
		return s;
	}

	public static class LineaTrazo {
		public String origen; // jar | modid | paquete | clase
		public String clase;
		public int nivel;
		public int lineaConsola;
		public boolean fatal;

		// NUEVO
		public List<String> llaves = Collections.emptyList();
	}

	/**
	 * Verifica si un modid está en la lista de elementos no permitidos
	 * 
	 * @param modid ID del mod a verificar (sin versión)
	 * @return true si el modid no está permitido, false en caso contrario
	 */
	public static boolean esModNoPermite(String modid) {
		if (modid == null || modid.replace(" ", "").equals("")) {
			return true;
		}

		String[] ids = { "java", "minecraft", "minecraftforge", "net.minecraftforge", "eventbus", "cpw", "coremods",
				"featurecreep", "mixin", "accesstransformer", "forge", "neoforge", "authlib", "sun", "jdk", "java",
				"fmlloader", "fmlcore", "org.spongepowered.mixin", "fmlearlydisplay", "com.sun.jna", "text2speech",
				"xf:crashdetector:default", "crashdetector", "srg", "org.objectweb.asm", "it.unimi", "datafixerupper",
				"com.google.gson", "org.openjdk", "launchwrapper" };

		for (String id : ids) {
			if (modid.startsWith(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica si un paquete está en la lista de elementos no permitidos También
	 * procesa handlers de SpongeMixin si están presentes
	 * 
	 * @param pack  Paquete a verificar
	 * @param dec   Cadena que contiene el nivel de prioridad y número de línea de
	 *              la consola (ej: "1,23")
	 * @param fatal Indica si es un error fatal
	 * @return true si el paquete no está permitido, false en caso contrario
	 */
	public boolean packNoEsPermite(String pack, String dec, boolean fatal) {
		// Si el paquete contiene un handler de SpongeMixin, procesarlo
		if (pack.contains("handler$")) {
			processarSMHandler(pack, dec, fatal);
		}

		// Verificar contra la lista de prefijos no permitidos
		String packSlash = pack.replace('.', '/');

		for (String prefix : package_no_permite) {

			String prefSlash = prefix.replace('.', '/');

			if (packSlash.startsWith(prefSlash)) {
				return true;
			}
		}

		return false;
	}

	public static String[] eliminarDuplicados(String[] inputArray) {
		// Usar LinkedHashSet para mantener el orden de inserción mientras se eliminan
		// duplicados
		Set<String> set = new HashSet<>(Arrays.asList(inputArray));

		// Convertir el set sin duplicados de vuelta a array
		String[] ret = set.toArray(new String[0]);

		return ret;
	}

	public static boolean tracePermite(String str) {
		for (ListaDenegadosTrace pred : denegados) {
			if (pred.predicado(str)) {
				return false;
			}
		}

		// Incluir otras líneas que no coincidan con los criterios de exclusión
		return true;
	}

	/**
	 * Verifica si una línea es parte de un stack trace
	 */
	private static boolean esParteDeStack(String l) {
		String t = normalizarLineaStack(l);
		if (t == null)
			return false;
		t = t.trim();

		if (t.endsWith("more"))
			return false;

		return t.startsWith("at ") || t.startsWith("Caused by:") || t.startsWith("Suppressed:") || t.startsWith("...")
				|| t.startsWith("SECURE-BOOTSTRAP") || pareceEncabezadoExcepcion(t);
	}

	public List<String> obtenerArchivosJsonEnMixinExceptions(String contenido_de_logs) {
		List<String> archivos_json = new ArrayList<>();

		// TODO [20:12:32] [main/ERROR]: fpsreducer.mixins.json:VideoSettingScreenMixin:
		// Super class 'net.minecraft.client.gui.screens.options.OptionsSubScreen' of
		// VideoSettingScreenMixin was not found in the hierarchy of target class
		// 'net/minecraft/client/gui/screens/options/VideoSettingsScreen'
		// org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException:
		// Super class 'net.minecraft.client.gui.screens.options.OptionsSubScreen' of
		// VideoSettingScreenMixin was not found in the hierarchy of target class
		// 'net/minecraft/client/gui/screens/options/VideoSettingsScreen'

		String[] lineas = dividirEnLineas(contenido_de_logs);
		for (String linea : lineas) {
			if (linea.contains("org.spongepowered.asm.mixin")) {
				CrashDetectorLogger.log(linea + "tiene error SM");
				for (String nombreJson : escanearJsonsMixin(linea.trim())) {
					if (nombreJson != null) {
						nombreJson = nombreJson.trim();
						CrashDetectorLogger.log(nombreJson + " json tiene error SM");

						// a veces tiene [
						if (nombreJson.startsWith("[")) {
							nombreJson = nombreJson.substring(1);
						}

						archivos_json.add(nombreJson);
					} else {
						CrashDetectorLogger.log(linea + "nil 1");
					}
				}
			}
		}

		return archivos_json;
	}

	/**
	 * Verifica si un nombre de jar está en la lista de elementos no permitidos
	 * 
	 * @param jarName Nombre del jar a verificar
	 * @return true si el jar no está permitido, false en caso contrario
	 */
	public static boolean isJarNoPermite(String jarName) {
		if (jarName.startsWith("fml")) {
			return true;
		}
		if (jarName.startsWith("fabric-loader")) {
			return true;
		}
		if (jarName.startsWith("crashdetectormc")) {
			return true;
		}
		if (jarName.startsWith("sponge-mixin")) {
			return true;
		}
		if (jarName.startsWith("forge-")) {
			return true;
		}

		if (jarName.startsWith("ForgeWrapper")) {
			return true;
		}

		if (jarName.startsWith("NewLaunch")) {
			return true;
		}

		if (jarName.contains("fmlcore")) {
			return true;
		}
		if (jarName.startsWith("mixin")) {
			return true;
		}

		if (jarName.startsWith("gson-")) {
			return true;
		}
		if (jarName.startsWith("eventbus")) {
			return true;
		}
		if (jarName.startsWith("featurecreep-")) {
			return true;
		}
		if (jarName.startsWith("server-")) {
			return true;
		}
		if (jarName.startsWith("modlauncher")) {
			return true;
		}
		if (jarName.startsWith("launchwrapper")) {
			return true;
		}
		if (jarName.startsWith("com.google")) {
			return true;
		}
		if (jarName.startsWith("toml-")) {
			return true;
		}
		if (jarName.startsWith("javafmllanguage")) {
			return true;
		}
		if (jarName.startsWith("client-")) {
			return true;
		}
		if (jarName.startsWith("lwjgl-")) {
			return true;
		}
		if (jarName.startsWith("netty-")) {
			return true;
		}
		if (jarName.startsWith("bootstraplauncher")) {
			return true;
		}
		if (jarName.startsWith("neoforge-")) {
			return true;
		}

		if (jarName.startsWith("bootstrap-")) {
			return true;
		}
		if (jarName.startsWith("bus-")) {
			return true;
		}
		if (jarName.startsWith("securejarhandler")) {
			return true;
		}
		if (jarName.startsWith("securemodules-")) {
			return true;
		}
		if (jarName.startsWith("core-")) {
			return true;
		}
		if (jarName.startsWith("asm-")) {
			return true;
		}
		if (jarName.startsWith("loader-")) {
			return true;
		}
		if (jarName.startsWith("authlib-")) {
			return true;
		}
		if (jarName.startsWith("jna-")) {
			return true;
		}
		if (jarName.startsWith("text2speech-")) {
			return true;
		}
		if (jarName.startsWith("Fabric%")) {
			return true;
		}
		if (jarName.startsWith("Forge%")) {
			return true;
		}
		if (jarName.startsWith("language-")) {
			return true;
		}
		if (jarName.startsWith("language-")) {
			return true;
		}
		if (jarName.startsWith("minecraft-") && jarName.contains("server")) {
			return true;
		}
		if (jarName.startsWith("minecraft-") && jarName.contains("client")) {
			return true;
		}
		if (jarName.startsWith("coremods-")) {
			return true;
		}
		if (jarName.startsWith("nashorn-core-")) {
			return true;
		}
		if (jarName.startsWith("guava-")) {
			return true;
		}

		if (jarName.startsWith("sun")) {
			return true;
		}

		if (jarName.startsWith("com.sun")) {
			return true;
		}
		if (jarName.startsWith("datafixerupper")) {
			return true;
		}

		if (jarName.startsWith("theseus")) {
			return true;
		}

		return false;
	}

	// Normalizador de líneas de stack: quita // y prefijos de cargador conocidos
	private static final String[] PREFIJOS_CARGADOR = { "knot//MC//", "knot//", "knott//", "app//", "SECURE-BOOTSTRAP/",
			"MC-BOOTSTRAP/", "MC/" };

	public static String normalizarLineaStack(String l) {
		if (l == null)
			return null;
		String t = l.trim();

		// Si la línea viene comentada tipo "// at ..."
		if (t.startsWith("//")) {
			t = t.substring(2).trim();
		}
		// Quitar prefijos de cargador antes de "at " o justo después de "at "
		// Casos: "at knot//com.paquete.Clase.metodo(...)" ó "knot//at com..."
		if (t.startsWith("at ")) {
			// remover prefijos justo después de "at "
			for (String p : PREFIJOS_CARGADOR) {
				String marca = "at " + p;
				if (t.startsWith(marca)) {
					t = "at " + t.substring(marca.length());
					break;
				}
			}
		} else {
			// si viene como "knot//at ..."
			for (String p : PREFIJOS_CARGADOR) {
				String marca = p + "at ";
				if (t.startsWith(marca)) {
					t = "at " + t.substring(marca.length());
					break;
				}
			}
		}

		// También limpiar cualquier prefijo repetido "knot//" al inicio de la parte de
		// clase/paquete
		for (String p : PREFIJOS_CARGADOR) {
			if (t.startsWith("at " + p)) {
				t = "at " + t.substring(("at " + p).length());
			}
		}

		return t;
	}

	// =====================================================================
	// Analizadores manuales de alto rendimiento (reemplazan expresiones
	// regulares). Cada método reproduce exactamente el comportamiento del
	// patrón original pero con escaneo de caracteres, evitando el coste de
	// compilar y ejecutar java.util.regex en cada línea de log.
	// =====================================================================

	private static boolean esEspacioRegex(char c) {
		// Equivale a \s de Java: [ \t\n\x0B\f\r]
		return c == ' ' || c == '\t' || c == '\n' || c == '\u000B' || c == '\f' || c == '\r';
	}

	private static boolean esAlnum(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
	}

	private static boolean esWordChar(char c) {
		// Equivale a \w (sin Unicode): [a-zA-Z0-9_]
		return esAlnum(c) || c == '_';
	}

	private static boolean esHex(char c) {
		return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
	}

	private static boolean esCharModidPunto(char c) {
		// [a-z0-9_\-.]
		return (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '_' || c == '-' || c == '.';
	}

	private static boolean esCharModidSinPunto(char c) {
		// [a-z0-9_\-]
		return (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '_' || c == '-';
	}

	/**
	 * Divide una cadena por un separador literal reproduciendo la semántica de
	 * String.split(regex) con límite 0 para separadores sin metacaracteres: si no
	 * hay coincidencias devuelve [s]; en caso contrario elimina las cadenas vacías
	 * finales.
	 */
	private static String[] dividirPorCadena(String s, String sep) {
		if (s == null) {
			return new String[0];
		}
		if (sep == null || sep.isEmpty()) {
			return new String[] { s };
		}
		int idx = s.indexOf(sep);
		if (idx < 0) {
			return new String[] { s };
		}
		List<String> partes = new ArrayList<>();
		int ini = 0;
		int paso = sep.length();
		while (idx >= 0) {
			partes.add(s.substring(ini, idx));
			ini = idx + paso;
			idx = s.indexOf(sep, ini);
		}
		partes.add(s.substring(ini));
		int fin = partes.size();
		while (fin > 0 && partes.get(fin - 1).isEmpty()) {
			fin--;
		}
		return partes.subList(0, fin).toArray(new String[0]);
	}

	/**
	 * Divide por un carácter literal con la misma semántica que
	 * String.split(regexDeUnCaracter) y límite 0.
	 */
	private static String[] dividirPorChar(String s, char sep) {
		if (s == null) {
			return new String[0];
		}
		if (s.indexOf(sep) < 0) {
			return new String[] { s };
		}
		List<String> partes = new ArrayList<>();
		int ini = 0;
		int n = s.length();
		for (int i = 0; i < n; i++) {
			if (s.charAt(i) == sep) {
				partes.add(s.substring(ini, i));
				ini = i + 1;
			}
		}
		partes.add(s.substring(ini));
		int fin = partes.size();
		while (fin > 0 && partes.get(fin - 1).isEmpty()) {
			fin--;
		}
		return partes.subList(0, fin).toArray(new String[0]);
	}

	/**
	 * Equivale a contenido.split("\\r?\\n") con límite 0: separa por '\n'
	 * consumiendo un '\r' previo opcional como parte del delimitador.
	 */
	private static String[] dividirEnLineas(String s) {
		if (s == null) {
			return new String[0];
		}
		int n = s.length();
		boolean hubo = false;
		List<String> partes = new ArrayList<>();
		int ini = 0;
		for (int i = 0; i < n; i++) {
			if (s.charAt(i) == '\n') {
				hubo = true;
				int corte = i;
				if (corte > ini && s.charAt(corte - 1) == '\r') {
					corte--; // \r? antes de \n
				}
				partes.add(s.substring(ini, corte));
				ini = i + 1;
			}
		}
		if (!hubo) {
			return new String[] { s };
		}
		// Último segmento: un '\r' final no va seguido de '\n', por lo que NO se
		// recorta
		partes.add(s.substring(ini));
		int fin = partes.size();
		while (fin > 0 && partes.get(fin - 1).isEmpty()) {
			fin--;
		}
		return partes.subList(0, fin).toArray(new String[0]);
	}

	/**
	 * Escáner manual equivalente al patrón "(\\S+\\.json)(?=[: ])": localiza las
	 * secuencias de caracteres no-espacio que terminan en ".json" seguidas de ':' o
	 * ' '. Reproduce la voracidad de \S+ (toma el ".json" más a la derecha dentro
	 * de cada bloque sin espacios y exige al menos un carácter antes del punto).
	 */
	private static List<String> escanearJsonsMixin(String linea) {
		List<String> res = new ArrayList<>();
		if (linea == null) {
			return res;
		}
		int n = linea.length();
		int i = 0;
		while (i < n) {
			if (esEspacioRegex(linea.charAt(i))) {
				i++;
				continue;
			}
			int runIni = i;
			int j = i;
			while (j < n && !esEspacioRegex(linea.charAt(j))) {
				j++;
			}
			// Buscar el último ".json" del bloque [runIni, j) seguido de ':' o ' '
			int ultimo = -1;
			int k = runIni;
			while (true) {
				int idx = linea.indexOf(".json", k);
				if (idx < 0 || idx + 5 > j) {
					break;
				}
				int sig = idx + 5;
				char c = (sig < n) ? linea.charAt(sig) : '\0';
				if ((c == ':' || c == ' ') && idx > runIni) {
					ultimo = idx;
				}
				k = idx + 5;
			}
			if (ultimo >= 0) {
				res.add(linea.substring(runIni, ultimo + 5));
			}
			i = j;
		}
		return res;
	}

	/**
	 * Equivale a "^\\s*at\\s+TRANSFORMER/([a-z0-9_\\-.]+)(?:@|/).*$" sobre una
	 * línea ya recortada. Devuelve el modid o null.
	 */
	private static String modidDesdeTransformer(String t) {
		int n = t.length();
		int i = 0;
		while (i < n && esEspacioRegex(t.charAt(i))) {
			i++;
		}
		if (!t.startsWith("at", i)) {
			return null;
		}
		i += 2;
		int ws = i;
		while (i < n && esEspacioRegex(t.charAt(i))) {
			i++;
		}
		if (i == ws) {
			return null; // \s+ requiere al menos un espacio
		}
		final String marca = "TRANSFORMER/";
		if (!t.startsWith(marca, i)) {
			return null;
		}
		i += marca.length();
		int ini = i;
		while (i < n && esCharModidPunto(t.charAt(i))) {
			i++;
		}
		if (i == ini || i >= n) {
			return null; // grupo vacío o falta (?:@|/)
		}
		char c = t.charAt(i);
		if (c != '@' && c != '/') {
			return null;
		}
		return t.substring(ini, i);
	}

	private static final String[] MODID_SIMPLE_BLOQUEADOS = { "java", "jdk", "sun", "org", "com", "net" };

	/**
	 * Equivale a
	 * "^\\s*at\\s+(?!java\\b|jdk\\b|sun\\b|org\\b|com\\b|net\\b)([a-z0-9_\\-]+)@[^/]+/.*$".
	 * Devuelve el modid candidato o null (sin aplicar esModNoPermite, igual que el
	 * grupo capturado original).
	 */
	private static String modidDesdeSimple(String t) {
		int n = t.length();
		int i = 0;
		while (i < n && esEspacioRegex(t.charAt(i))) {
			i++;
		}
		if (!t.startsWith("at", i)) {
			return null;
		}
		i += 2;
		int ws = i;
		while (i < n && esEspacioRegex(t.charAt(i))) {
			i++;
		}
		if (i == ws) {
			return null;
		}
		// Lookahead negativo (?!java\b|jdk\b|sun\b|org\b|com\b|net\b)
		for (String pre : MODID_SIMPLE_BLOQUEADOS) {
			if (t.startsWith(pre, i)) {
				int after = i + pre.length();
				boolean limite = (after >= n) || !esWordChar(t.charAt(after));
				if (limite) {
					return null; // \b se cumple => el lookahead negativo falla
				}
			}
		}
		int ini = i;
		while (i < n && esCharModidSinPunto(t.charAt(i))) {
			i++;
		}
		if (i == ini) {
			return null; // grupo vacío
		}
		int finGrupo = i;
		if (i >= n || t.charAt(i) != '@') {
			return null;
		}
		i++; // '@'
		int verIni = i;
		while (i < n && t.charAt(i) != '/') {
			i++;
		}
		if (i == verIni || i >= n) {
			return null; // [^/]+ seguido de '/'
		}
		return t.substring(ini, finGrupo);
	}

	/**
	 * Equivale a "[a-z][a-z0-9_\\-]*" (coincidencia completa).
	 */
	private static boolean esClaveLlaveValida(String clave) {
		int n = clave.length();
		if (n == 0) {
			return false;
		}
		char c0 = clave.charAt(0);
		if (c0 < 'a' || c0 > 'z') {
			return false;
		}
		for (int i = 1; i < n; i++) {
			char c = clave.charAt(i);
			if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '_' || c == '-') {
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * Equivale a ".*\\s{2,}.*": ¿hay dos caracteres de espacio (\s) consecutivos?
	 */
	private static boolean tieneEspaciosDoblesRegex(String v) {
		int n = v.length();
		for (int i = 1; i < n; i++) {
			if (esEspacioRegex(v.charAt(i)) && esEspacioRegex(v.charAt(i - 1))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Equivale a "^[a-zA-Z0-9_.]+\\.[A-Z][a-zA-Z0-9]+Exception.*" (coincidencia
	 * completa) sobre una línea de una sola fila.
	 */
	private static boolean pareceEncabezadoExcepcion(String t) {
		int desde = 0;
		while (true) {
			int e = t.indexOf("Exception", desde);
			if (e < 0) {
				return false;
			}
			// Retroceder sobre el bloque alfanumérico (M) inmediatamente anterior
			int k = e - 1;
			while (k >= 0 && esAlnum(t.charAt(k))) {
				k--;
			}
			int alnumIni = k + 1;
			int lenAlnum = e - alnumIni;
			// [A-Z][a-zA-Z0-9]+ => primer carácter mayúscula y longitud >= 2,
			// precedido de '.', y antes de ese '.' al menos un carácter de P.
			if (lenAlnum >= 2 && t.charAt(alnumIni) >= 'A' && t.charAt(alnumIni) <= 'Z' && alnumIni - 1 >= 1
					&& t.charAt(alnumIni - 1) == '.') {
				int pFin = alnumIni - 1; // exclusivo: posición del '.'
				boolean pValido = true;
				for (int x = 0; x < pFin; x++) {
					char c = t.charAt(x);
					if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_'
							|| c == '.')) {
						pValido = false;
						break;
					}
				}
				if (pValido) {
					return true;
				}
			}
			desde = e + 1;
		}
	}

	/**
	 * ¿El subrango [ini, fin) de t contiene "WARN" como palabra completa
	 * (\\bWARN\\b)? Los límites de palabra usan los caracteres adyacentes reales de
	 * t (por ejemplo los corchetes que rodean el bloque de log).
	 */
	private static boolean contieneWarnPalabra(String t, int ini, int fin) {
		int idx = ini;
		while (true) {
			int p = t.indexOf("WARN", idx);
			if (p < 0 || p + 4 > fin) {
				return false;
			}
			boolean limIzq = (p == 0) || !esWordChar(t.charAt(p - 1));
			boolean limDer = (p + 4 >= t.length()) || !esWordChar(t.charAt(p + 4));
			if (limIzq && limDer) {
				return true;
			}
			idx = p + 1;
		}
	}

	/**
	 * Equivale a ".*\\[[^\\]]*\\bWARN\\b[^\\]]*\\]:.*": un bloque [..WARN..]
	 * seguido inmediatamente de ':'.
	 */
	private static boolean coincideWarnBloqueSimple(String t) {
		int n = t.length();
		int i = 0;
		while (i < n) {
			int a = t.indexOf('[', i);
			if (a < 0) {
				return false;
			}
			int c = t.indexOf(']', a + 1);
			if (c < 0) {
				return false;
			}
			if (c + 1 < n && t.charAt(c + 1) == ':' && contieneWarnPalabra(t, a + 1, c)) {
				return true;
			}
			i = a + 1;
		}
		return false;
	}

	/**
	 * Equivale a ".*\\[[^\\]]*\\]\\s*\\[[^\\]]*\\bWARN\\b[^\\]]*\\]\\s*:.*": dos
	 * bloques [..] [..WARN..] separados por \s* y seguidos de \s*:.
	 */
	private static boolean coincideWarnDobleBloque(String t) {
		int n = t.length();
		int i = 0;
		while (i < n) {
			int a1 = t.indexOf('[', i);
			if (a1 < 0) {
				return false;
			}
			int c1 = t.indexOf(']', a1 + 1);
			if (c1 < 0) {
				return false;
			}
			int j = c1 + 1;
			while (j < n && esEspacioRegex(t.charAt(j))) {
				j++;
			}
			if (j < n && t.charAt(j) == '[') {
				int c2 = t.indexOf(']', j + 1);
				if (c2 >= 0 && contieneWarnPalabra(t, j + 1, c2)) {
					int k = c2 + 1;
					while (k < n && esEspacioRegex(t.charAt(k))) {
						k++;
					}
					if (k < n && t.charAt(k) == ':') {
						return true;
					}
				}
			}
			i = a1 + 1;
		}
		return false;
	}

}