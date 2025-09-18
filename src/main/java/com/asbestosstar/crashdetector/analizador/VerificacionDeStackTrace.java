package com.asbestosstar.crashdetector.analizador;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.TriMap;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.StackTracesDenegadosDeMinecraftPorDefecto;

public class VerificacionDeStackTrace {

	/**
	 * Stacktraces para ignorar
	 */
	public static List<ListaDenegadosTrace> denegados = new ArrayList<ListaDenegadosTrace>();

	Consola consola;
	public static String nl = System.lineSeparator();

	// Patrón para coincidir con rastros de pila de excepciones de Java
	private static final Pattern STACK_TRACE_PATTERN = Pattern.compile("(?m)(^\\S.*(?:\\r?\\n[ \\t]+at\\s+.*)+)");
	// Patrón para coincidir con excepciones que contienen
	// org.spongepowered.asm.mixin y extraer nombres de JSON (sin incluir refmap)
	private static final Pattern JSON_PATTERN = Pattern.compile("(\\S+\\.json)(?=[: ])");
	// Patrón para coincidir con contenido dentro de llaves {}
	private static final Pattern BRACE_PATTERN = Pattern.compile("\\{([^}]+)\\}");

	public List<String> sm_config = new ArrayList<>();
	public Map<String, Boolean> jars = new LinkedHashMap<>();// FATAL
	public TriMap<String, Integer, Integer, Boolean> modids = new TriMap<>();// FATAL (modid, nivel_prioridad,
																				// línea_consola, es fatal)
	public TriMap<String, Integer, Integer, Boolean> packs = new TriMap<>();// FATAL (paquete, nivel_prioridad,
																			// línea_consola, es fatal)
	public TriMap<String, Integer, Integer, Boolean> braces = new TriMap<>();// FATAL (contenido llaves,
																				// nivel_prioridad, línea_consola, es
																				// fatal)
	public TriMap<String, Integer, Integer, String> clases_fatales_no_existentes = new TriMap<>();// (clase,
																									// nivel_prioridad,
																									// línea_consola,
																									// sospechoso)

	// Estos solo contienen el contenido pero no el nivel
	public List<String> jar_malo = new ArrayList<String>();
	public List<String> modid_malo = new ArrayList<String>();
	public List<String> package_malo = new ArrayList<String>();
	public List<String> brace_malo = new ArrayList<String>();

	public static String[] package_no_permite = { "java.", "net.minecraft", "net.minecraftforge", "org.spongepowered",
			"it.unimi", "com.mojang.", "cpw.", "featurecreep.", "jdk.", "sun.", "com.sun.", "org.lwjgl.", "org.apache.",
			"io.netty", "org.prismlauncher", "io.github.zekerzhayard", "org.multimc", "org.polymc", "org.tlauncher",
			"net.fabricmc", "org.objectweb.asm", "datafixerupper", "org.slf4j", "com.asbestosstar", "srg",
			"asbestosstar.", "org.openjdk", "com.google"

	};

	static {
		StackTracesDenegadosDeMinecraftPorDefecto.init();
	}

	public VerificacionDeStackTrace(Consola cons) {
		this.consola = cons;
	}

	public void reiniciar() {

		sm_config.clear();
		jars.clear();
		modids.clear();
		packs.clear();
		clases_fatales_no_existentes.clear();

		jar_malo.clear();
		modid_malo.clear();
		package_malo.clear();

		int nivel_prioridad = 0;
		String contenido = consola.contenido_verificar;
		List<TraceInfo> tracesFatal = obtenerTracesFatalConLinea(contenido);
		Collections.reverse(tracesFatal); // Las últimas son las más importantes
		for (TraceInfo traceInfo : tracesFatal) {
			nivel_prioridad++;
			this.procesarTrace(traceInfo.trace, true, nivel_prioridad, traceInfo.consolaLineaComenzar);
		}

		List<TraceInfo> tracesNormales = obtenerTracesConLinea(contenido);
		Collections.reverse(tracesNormales); // Las últimas son las más importantes
		for (TraceInfo traceInfo : tracesNormales) {
			nivel_prioridad++;
			this.procesarTrace(traceInfo.trace, false, nivel_prioridad, traceInfo.consolaLineaComenzar);
		}
	}

	/**
	 * Información sobre un stack trace incluyendo su contenido y la línea inicial
	 * en la consola
	 */
	public static class TraceInfo {
		public String trace;
		public int consolaLineaComenzar;

		TraceInfo(String trace, int consolaLineaComenzar) {
			this.trace = trace;
			this.consolaLineaComenzar = consolaLineaComenzar;
		}
	}

	/**
	 * Obtiene stack traces fatales junto con su línea inicial en la consola
	 */
	public static List<TraceInfo> obtenerTracesFatalConLinea(String log) {
		List<TraceInfo> ret = new ArrayList<>();
		String[] lineas = log.split(nl);

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			if (linea.contains("/FATAL]")) {
				StringBuilder trace = new StringBuilder();
				trace.append(linea);
				int j = i + 1;

				while (j < lineas.length && esParteDeStack(lineas[j])) {
					trace.append(nl).append(lineas[j]);
					j++;
				}

				if (tracePermite(trace.toString())) {
					ret.add(new TraceInfo(trace.toString(), i));
				}
			}
		}
		return ret;
	}

	/**
	 * Obtiene stack traces normales junto con su línea inicial en la consola
	 */
	public static List<TraceInfo> obtenerTracesConLinea(String log) {
		List<TraceInfo> ret = new ArrayList<>();
		String[] lineas = log.split(nl);
		Matcher matcher = STACK_TRACE_PATTERN.matcher(log);

		int i = 0;
		while (i < lineas.length && matcher.find()) {
			// Encontrar la línea donde comienza el stack trace
			int lineaPrimera = -1;
			for (int j = i; j < lineas.length; j++) {
				if (matcher.reset(lineas[j]).find()) {
					lineaPrimera = j;
					break;
				}
			}

			if (lineaPrimera == -1)
				break;

			// Construir el stack trace completo
			StringBuilder trace = new StringBuilder(lineas[lineaPrimera]);
			int j = lineaPrimera + 1;

			while (j < lineas.length && esParteDeStack(lineas[j])) {
				trace.append(nl).append(lineas[j]);
				j++;
			}

			if (tracePermite(trace.toString())) {
				ret.add(new TraceInfo(trace.toString(), lineaPrimera));
			}

			i = j;
		}
		return ret;
	}

	public void procesarTrace(String trace, boolean fatal, int nivel_prioridad, int consolaLineaPrimera) {
		String idiomaNivel = MonitorDePID.idioma.nivel();
		List<String> archivos_json = obtenerArchivosJsonEnMixinExceptions(trace);

		if (!archivos_json.isEmpty()) {
			for (String jsonFile : archivos_json) {
				if (!sm_config.contains(jsonFile) && !jsonFile.endsWith(".refmap.json")) {
					sm_config.add(jsonFile);
					// build.append(MonitorDePID.idioma.config_spongemixin_problematico(jsonFile)).append(nl_html);
				}
			}
		} else {
			String[] arr = trace.split(nl);
			for (int i = 0; i < arr.length; i++) {
				String untrimmed = arr[i];
				String linea = untrimmed.trim();
				// Calcular la línea real en la consola
				int consolaNumLinea = consolaLineaPrimera + i;
				String dec = Integer.toString(nivel_prioridad) + "," + Integer.toString(consolaNumLinea);

				// No siempre hay un Jar
				if (linea.contains("[")) {
					List<String> jarsEncontrados = extraerJarsDeLinea(linea);
					for (String jar : jarsEncontrados) {
						if (jar.contains(".jar") && !isJarNoPermite(jar)) {
							if (!jar_malo.contains(jar)) {
								jar_malo.add(jar);
								jars.put(jar + idiomaNivel + dec, fatal);
							}
						}
					}
				}
				// Algunos entornos de desarrollo como ForgeGradle o lanzadores orientados a
				// desarrollo
				// como TLauncher muestran el modID y la capa, esto es útil
				// especialmente cuando no se puede encontrar el Jar
				else if (linea.contains("/") && !linea.contains("NoClassDefFoundError")) {
					String modid = extraerModidDeLinea(linea);
					if (modid != null) {
						if (!modid_malo.contains(modid) && !linea.split("/")[0].startsWith("java.")
								&& !esModNoPermite(modid) && linea.startsWith("at")) {
							modid_malo.add(modid);
							// Ahora usamos TriMap con nivel de prioridad y número de línea en la consola
							modids.put(modid, nivel_prioridad, consolaNumLinea, fatal);
						}
					}
				}
				// A veces necesitamos usar paquetes
				else if (linea.startsWith("at")) {
					String pack = extraerPaqueteDeLinea(linea);
					if (pack != null) {
						if (!package_malo.contains(pack) && !packNoEsPermite(pack, dec, fatal)) {
							// Ahora usamos TriMap con nivel de prioridad y número de línea en la consola
							packs.put(pack, nivel_prioridad, consolaNumLinea, fatal);
							package_malo.add(pack);
						}
					}
				}

				// Procesar línea que contiene ClassNotFoundException
				// No necesitamos fatal para FabricMC o FeatureCreep y en casos en ModLauncher
				// en launcher_log
				else if ((linea.contains("ClassNotFoundException") || linea.contains("NoClassDefFoundError"))
						&& !linea.contains("The specified mixin") && !linea.contains("WARN/]")) {

					Map.Entry<String, String> resultado = procesarErrorClaseNoEncontrada(linea, arr, consolaNumLinea,
							nivel_prioridad);
					if (resultado != null) {
						String claseFaltante = resultado.getKey();
						String sospechoso = resultado.getValue();

						// Almacenar con nivel de prioridad y número de línea en la consola
						clases_fatales_no_existentes.put(claseFaltante, nivel_prioridad, consolaNumLinea, sospechoso);
					}
				}

				// Extraer contenido entre llaves
				List<String> llavesEncontradas = extraerLlavesDeLinea(linea);
				for (String content : llavesEncontradas) {
					if (!brace_malo.contains(content)) {
						// Ahora usamos TriMap con nivel de prioridad y número de línea en la consola
						braces.put(content, nivel_prioridad, consolaNumLinea, fatal);
						brace_malo.add(content);
					}
				}
			}
		}
	}

	/**
	 * Extrae nombres de jars de una línea de stack trace que contiene corchetes []
	 * Ejemplo: at
	 * net.createmod.catnip.render.StitchedSprite.<init>(StitchedSprite.java:23)
	 * ~[Ponder-Forge-1.20.1-1.0.80.jar%23795!/:1.0.80]
	 * 
	 * @param linea Línea de stack trace a procesar
	 * @return Lista de nombres de jars encontrados en la línea
	 */
	public static List<String> extraerJarsDeLinea(String linea) {
		List<String> jars = new ArrayList<>();

		int startIdx = linea.indexOf('[');
		int endIdx = linea.indexOf(']');

		while (startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
			String candidito = linea.substring(startIdx + 1, endIdx);
			jars.add(candidito);

			// Buscar el próximo par de corchetes
			startIdx = linea.indexOf('[', endIdx);
			endIdx = linea.indexOf(']', endIdx + 1);
		}

		return jars;
	}

	/**
	 * Extrae identificadores de mods de una línea que contiene barras / Ejemplo: at
	 * TRANSFORMER/createappliedkinetics@1.3.2-1.20.1/com.forsteri.createappliedkinetics.entry.Registration.<clinit>(Registration.java:81)
	 * 
	 * @param linea Línea de stack trace a procesar
	 * @return ModID encontrado o null si no se encontró
	 */
	public static String extraerModidDeLinea(String linea) {
		String[] arr_modid = linea.split("/");
		if (arr_modid.length > 1) {
			return arr_modid[1].split("@")[0];
		}
		return null;
	}

	/**
	 * Extrae nombres de paquetes de una línea que comienza con "at" Ejemplo: at
	 * java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:77)
	 * 
	 * @param linea Línea de stack trace a procesar
	 * @return Nombre del paquete encontrado o null si no se encontró
	 */
	public static String extraerPaqueteDeLinea(String linea) {
		if (linea.startsWith("at")) {
			return linea.substring(3).trim();
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
		Matcher braceMatcher = BRACE_PATTERN.matcher(linea);
		while (braceMatcher.find()) {
			llaves.add(braceMatcher.group(1).trim());
		}
		return llaves;
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
		CrashDetectorLogger.log(linea);

		if (linea.contains("ClassNotFoundException")) {
			int startIdx = linea.indexOf("ClassNotFoundException:") + "ClassNotFoundException:".length();
			claseFaltante = linea.substring(startIdx).trim();
		} else {
			int startIdx = linea.indexOf("NoClassDefFoundError:") + "NoClassDefFoundError:".length();
			claseFaltante = linea.substring(startIdx).trim();
		}
		CrashDetectorLogger.log(claseFaltante);
		// Ahora extraemos solo el nombre de la clase (eliminamos mensajes adicionales
		// como "Could not initialize class")

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
				// claseFaltante = claseFaltante.substring(0, spaceIdx);
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

		String sospechoso = "";
		String[] arr_nuevo = Arrays.copyOfRange(arr, 1, arr.length);

		for (String notrim : arr_nuevo) {
			String t = notrim.trim();

			// Extracción de jar
			int a = t.indexOf('['), b = t.indexOf(']');
			if (a != -1 && b != -1 && a < b) {
				String jar = t.substring(a + 1, b);
				if (jar.endsWith(".jar") && !isJarNoPermite(jar)) {
					sospechoso = jar;
					return new AbstractMap.SimpleEntry<>(claseFaltante, sospechoso);
				}
			}

			// Extracción de modid
			int slash1 = t.indexOf('/');
			int slash2 = (slash1 != -1) ? t.indexOf('/', slash1 + 1) : -1;
			if (slash1 != -1 && slash2 != -1) {
				String cand = t.substring(slash1 + 1, slash2);
				if (!esModNoPermite(cand) && !cand.isEmpty()) {
					sospechoso = cand;
					return new AbstractMap.SimpleEntry<>(claseFaltante, sospechoso);
				}
			}

			// Extracción de paquetes
			if (t.startsWith("at ")) {
				String pack = t.substring(3);
				// Usamos consoleLineNumber en lugar de línea dentro del stack trace
				if (!packNoEsPermite(pack,
						Integer.toString(nivel_prioridad) + "," + Integer.toString(consoleLineNumber), false)) {
					sospechoso = pack;
					return new AbstractMap.SimpleEntry<>(claseFaltante, sospechoso);
				}
			}
		}

		return null;
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
		// Dividir la cadena por '$' para identificar posibles IDs de mod
		String[] parts = pack.split("\\$");

		// Verificar si la cadena tiene al menos 4 partes (indicando la presencia de un
		// mod ID)
		if (parts.length >= 4) {
			// Extraer el mod ID (ubicado después del tercer '$')
			String modid = parts[3];

			// Registrar el mod ID extraído para propósitos de depuración
			CrashDetectorLogger.log("Mod ID encontrado: " + modid);
			if (!modid_malo.contains(modid)) {
				modid_malo.add(modid);

				// Extraer nivel de prioridad y número de línea de la cadena dec
				String[] lvlLinea = dec.split(",");
				int nivel_prioridad = Integer.parseInt(lvlLinea[0]);
				int consoleLineNumber = Integer.parseInt(lvlLinea[1]);

				// Añadir el mod ID al mapa modids con la clave y valor apropiados
				modids.put(modid, nivel_prioridad, consoleLineNumber, fatal);
			}

		} else {
			// Registrar que la línea no contiene un mod ID válido y se ignorará
			CrashDetectorLogger.log("Línea ignorada: No contiene un mod ID válido.");
		}
	}

	/**
	 * Verifica si un modid está en la lista de elementos no permitidos
	 * 
	 * @param modid ID del mod a verificar
	 * @return true si el modid no está permitido, false en caso contrario
	 */
	public static boolean esModNoPermite(String modid) {
		if (modid.replace(" ", "").equals("")) {
			return true;
		}

		String[] ids = { "java", "minecraft", "minecraftforge", "net.minecraftforge", "eventbus", "cpw.", "coremods",
				"featurecreep", "mixin", "accesstransformer", "forge", "authlib", "sun.", "jdk.", "java.", "fmlloader",
				"fmlcore", "org.spongepowered.mixin", "fmlearlydisplay", "com.sun.jna", "text2speech",
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
		for (String prefix : package_no_permite) {
			if (pack.startsWith(prefix)) {
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

	private static boolean esParteDeStack(String l) {
		String t = l.trim();
		return t.startsWith("at ") || t.startsWith("Caused by:") || t.startsWith("Suppressed:") || t.startsWith("...")
		// secure-bootstrap class-loader etc.
				|| t.startsWith("SECURE-BOOTSTRAP")
				// mensajes de excepción ("org.spongepowered...InvalidMixinException ...")
				|| t.matches("^[a-zA-Z0-9_.]+\\.[A-Z][a-zA-Z0-9]+Exception.*");
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
		if (jarName.startsWith("bootstrap-")) {
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

		return false;
	}

}