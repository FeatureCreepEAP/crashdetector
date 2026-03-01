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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.StackTracesDenegadosDeMinecraftPorDefecto;
import com.asbestosstar.crashdetector.mapas.BiMap;
import com.asbestosstar.crashdetector.mapas.TriMap;

public class VerificacionDeStackTrace {

	/**
	 * Stacktraces para ignorar
	 */
	public static List<ListaDenegadosTrace> denegados = new ArrayList<ListaDenegadosTrace>();

	Consola consola;
	public static String nl = System.lineSeparator();

	// Patrón para coincidir con rastros de pila de excepciones de Java
	private static final Pattern STACK_TRACE_PATTERN = Pattern
			.compile("(?m)(^\\S.*(?:\\r?\\n[ \\t]*(?://\\s*)?at\\s+.*)+)");
	// Patrón para coincidir con excepciones que contienen
	// org.spongepowered.asm.mixin y extraer nombres de JSON (sin incluir refmap)
	private static final Pattern JSON_PATTERN = Pattern.compile("(\\S+\\.json)(?=[: ])");
	// Patrón para coincidir con contenido dentro de llaves {}
	private static final Pattern BRACE_PATTERN = Pattern.compile("\\{([^}]+)\\}");

	// === Patrones para detectar modid de forma estricta ===
	// Solo acepta frames TRANSFORMER/<modid>@<version>/...
	private static final Pattern PATRON_MODID_TRANSFORMER = Pattern
			.compile("^\\s*at\\s+TRANSFORMER/([a-z0-9_\\-.]+)(?:@|/).*$");

	// Opcional y conservador: admite "at <modid>@<ver>/..."
	// Evita confundir paquetes comunes (java, jdk, sun, org, com, net) y modids con
	// puntos.
	private static final Pattern PATRON_MODID_SIMPLE = Pattern
			.compile("^\\s*at\\s+(?!java\\b|jdk\\b|sun\\b|org\\b|com\\b|net\\b)([a-z0-9_\\-]+)@[^/]+/.*$");

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
			"net.neoforged."

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

		// === Reset estado ===
		sm_config.clear();
		clases_fatales_no_existentes.clear();

		jar_malo.clear();
		modid_malo.clear();
		package_malo.clear();
		brace_malo.clear();

		nivel_trazo.clear();
		trazos_completos.clear();

		int nivel_prioridad = 0;
		String contenido = consola.contenido_verificar;

		// === FATALES ===
		List<TraceInfo> tracesFatal = obtenerTracesFatalConLinea(contenido);
		Collections.reverse(tracesFatal); // últimos = mayor prioridad

		for (TraceInfo base : tracesFatal) {
			nivel_prioridad++;

			TraceInfo info = construirTraceInfo(base.trace, base.consolaLineaComenzar, nivel_prioridad, true);

			// Registro canónico
			trazos_completos.add(info);

			// Registro legacy / lookup
			nivel_trazo.put(nivel_prioridad, info);

			// Compatibilidad antigua (JSON / clases faltantes)
			procesarTrace(base.trace, true, nivel_prioridad, base.consolaLineaComenzar);
		}

		// === NORMALES ===
		List<TraceInfo> tracesNormales = obtenerTracesConLinea(contenido);
		Collections.reverse(tracesNormales); // últimos = mayor prioridad

		for (TraceInfo base : tracesNormales) {
			nivel_prioridad++;

			TraceInfo info = construirTraceInfo(base.trace, base.consolaLineaComenzar, nivel_prioridad, false);

			// Registro canónico
			trazos_completos.add(info);

			// Registro legacy / lookup
			nivel_trazo.put(nivel_prioridad, info);

			// Compatibilidad antigua
			procesarTrace(base.trace, false, nivel_prioridad, base.consolaLineaComenzar);
		}
	}

	private TraceInfo construirTraceInfo(String trace, int consolaLineaInicio, int nivel, boolean fatal) {

		TraceInfo info = new TraceInfo(trace, consolaLineaInicio, nivel, fatal);

		String[] lineas = trace.split(nl);

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

			// =========================================================
			// 1️⃣ RESOLVER ORIGEN (ANTES DE CUALQUIER DENY)
			// =========================================================
			String origen = null;

			// ---- JAR ----
			List<String> jars = extraerJarsDeLinea(normalizada);
			if (!jars.isEmpty()) {
				String jar = jars.get(0);
				if (!isJarNoPermite(jar)) {
					origen = jar;
				}
			}

			// ---- MODID ----
			if (origen == null) {
				String modid = extraerModidDeLinea(normalizada);
				if (modid != null && !esModNoPermite(modid)) {
					origen = modid;
				}
			}

			// ---- PAQUETE ----
			if (origen == null) {
				String paquete = extraerPaqueteDeLinea(normalizada);
				if (paquete != null) {
					String dec = nivel + "," + lineaConsola;
					if (!packNoEsPermite(paquete, dec, fatal)) {
						origen = paquete;
					}
				}
			}

			// =========================================================
			// 2️⃣ DENYLIST DE PAQUETES SOLO SI NO HAY ORIGEN
			// =========================================================
			if (origen == null) {
				boolean denegado = false;
				for (String pref : package_no_permite) {
					if (pref == null || pref.isEmpty()) {
						continue;
					}
					String prefSlash = pref.replace('.', '/');
					if (clase.startsWith(prefSlash)) {
						denegado = true;
						break;
					}
				}
				if (denegado) {
					continue;
				}
			}

			// =========================================================
			// 3️⃣ FALLBACK FINAL
			// =========================================================
			if (origen == null) {
				origen = clase;
			}

			// =========================================================
			// 4️⃣ REGISTRAR LÍNEA DE TRAZO
			// =========================================================
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
			String[] lineas = info.trace.split(nl);

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

		} while (cambio);

		// Quitar prefijos con metadata del estilo:
		// TRANSFORMER/<modid>@<ver>/ruta.clase.metodo
		// MC-BOOTSTRAP/<modulo>@<ver>/ruta.clase.metodo
		// LAYER PLUGIN/<plugin>@<ver>/ruta.clase.metodo
		String[] prefijosMetadata = { "TRANSFORMER/", "MC-BOOTSTRAP/", "LAYER PLUGIN/" };

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
	public static List<TraceInfo> obtenerTracesFatalConLinea(String log) {

		List<TraceInfo> resultado = new ArrayList<>();
		String[] lineas = log.split(nl);

		for (int i = 0; i < lineas.length; i++) {

			String header = lineas[i];
			if (header == null || !header.contains("/FATAL]")) {
				continue;
			}

			StringBuilder traza = new StringBuilder(header);
			int j = i + 1;

			while (j < lineas.length && esParteDeStack(lineas[j])) {
				traza.append(nl).append(lineas[j]);
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
	public static List<TraceInfo> obtenerTracesConLinea(String log) {

		List<TraceInfo> resultado = new ArrayList<>();
		String[] lineas = log.split(nl);

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
				traza.append(nl).append(lineas[j]);
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
			}
		}

		// === ClassNotFound / NoClassDef ===
		String[] arr = trace.split(nl);

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
		if (t.matches(".*\\[[^\\]]*\\]\\s*\\[[^\\]]*\\bWARN\\b[^\\]]*\\]\\s*:.*"))
			return true;

		// También cubrir formatos tipo "[mixin/WARN]:" directamente
		if (t.matches(".*\\[[^\\]]*\\bWARN\\b[^\\]]*\\]:.*"))
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

		Matcher mTrans = PATRON_MODID_TRANSFORMER.matcher(t);
		if (mTrans.matches()) {
			return mTrans.group(1);
		}

		Matcher mSimple = PATRON_MODID_SIMPLE.matcher(t);
		if (mSimple.matches()) {
			String cand = mSimple.group(1);
			return esModNoPermite(cand) ? null : cand;
		}

		// Detección adicional: handler$...$<modid>$...
		// Ej.: "...BufferBuilder.handler$cdk000$iris$beforeNext..."
		int h = t.indexOf("handler$");
		if (h >= 0) {
			String tail = t.substring(h + "handler$".length());
			String[] segs = tail.split("\\$");
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

		// purgar direcciones /0x...
		texto = texto.replaceAll("/0x[0-9a-fA-F]+.*", "");

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
		Matcher m = BRACE_PATTERN.matcher(linea);
		while (m.find()) {
			String contenido = m.group(1).trim();
			if (esLlaveDeSistema(contenido)) {
				llaves.add(contenido);
			}
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

		String[] segs = s.split("\\s*,\\s*");
		if (segs.length == 0)
			return false;

		int paresValidos = 0;

		for (String seg : segs) {
			int p = seg.indexOf(':');
			if (p <= 0 || p == seg.length() - 1)
				return false; // sin clave o sin valor

			String clave = seg.substring(0, p).trim();
			String valor = seg.substring(p + 1).trim();

			// clave alfanumérica simple para evitar frases
			if (!clave.matches("[a-z][a-z0-9_\\-]*"))
				return false;

			// evitar valores con '=' (otra señal de toString) o espacios excesivos tipo
			// frases
			if (valor.indexOf('=') >= 0)
				return false;
			if (valor.matches(".*\\s{2,}.*"))
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
		// minúsculas, dígitos, guion y guion_bajo, opcionalmente con puntos
		return s != null && s.matches("^[a-z0-9_\\-.]{2,64}$");
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
			String[] segs = tail.split("\\$");
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
				String[] lvlLinea = dec.split(",");
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
				|| t.startsWith("SECURE-BOOTSTRAP") || t.matches("^[a-zA-Z0-9_.]+\\.[A-Z][a-zA-Z0-9]+Exception.*");
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

		String[] lineas = contenido_de_logs.split("\r?\n");
		for (String linea : lineas) {
			if (linea.contains("org.spongepowered.asm.mixin")) {
				Matcher matcher = JSON_PATTERN.matcher(linea.trim());
				while (matcher.find()) {
					if (matcher.group(1) != null) {
						String nombreJson = matcher.group(1).trim();

						// a veces tiene [
						if (nombreJson.startsWith("[")) {
							nombreJson = nombreJson.substring(1);
						}

						archivos_json.add(nombreJson);
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
	private static final String[] PREFIJOS_CARGADOR = { "knot//MC//", "knot//", "knott//", "app//",
			"SECURE-BOOTSTRAP/" };

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

}