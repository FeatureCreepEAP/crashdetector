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

import com.asbestosstar.crashdetector.BiMap;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.StackTracesDenegadosDeMinecraftPorDefecto;

public class VerificacionDeStackTrace {

	/**
	 * Stacktraces para ignorar
	 */
	public static List<ListaDenegadosTrace> denegados = new ArrayList<ListaDenegadosTrace>();

	Consola consola;
	public static String nl = System.lineSeparator();

	// 正则表达式用于匹配Java异常堆栈跟踪
	private static final Pattern STACK_TRACE_PATTERN = Pattern.compile("(?m)(^\\S.*(?:\\r?\\n[ \\t]+at\\s+.*)+)");
	// 正则表达式用于匹配包含org.spongepowered.asm.mixin的异常并提取JSON文件名（不包括refmap）
	private static final Pattern JSON_PATTERN = Pattern.compile("(\\S+\\.json)(?=[: ])");
	// 正则表达式用于匹配{}内的内容
	private static final Pattern BRACE_PATTERN = Pattern.compile("\\{([^}]+)\\}");

	public List<String> sm_config = new ArrayList<>();
	public Map<String, Boolean> jars = new LinkedHashMap<>();// FATAL
	public BiMap<String, String, Boolean> modids = new BiMap<>();// FATAL
	public BiMap<String, String, Boolean> packs = new BiMap<>();// FATAL
	public BiMap<String, String, Boolean> braces = new BiMap<>();// FATAL

	public Map<String, String> fatal_clases_no_existe = new HashMap<String, String>();

	// These only contain the content but not lvl
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

	public void reincinar() {

		sm_config.clear();
		jars.clear();
		modids.clear();
		packs.clear();

		fatal_clases_no_existe.clear();

		jar_malo.clear();
		modid_malo.clear();
		package_malo.clear();

		int lvl = 0;
		String contento = consola.contenido_verificar;
		for (String trace : inverso(obtenerTracesFatal(contento))) {// Las ultimas son las más importante
			lvl++;
			this.procesarTrace(trace, true, lvl);
		}

		for (String trace : inverso(obtenerTraces(contento))) {// Las ultimas son las más importante
			lvl++;
			this.procesarTrace(trace, false, lvl);
		}
	}

	public void procesarTrace(String trace, boolean fatal, int lvl) {
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
			int linea_num = 0;
			for (String untrimmed : arr) {
				linea_num++;
				String linea = untrimmed.trim();
				String dec = Integer.toString(lvl) + "," + Integer.toString(linea_num);
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
							modids.put(modid, idiomaNivel + dec, fatal);
						}
					}
				}
				// A veces necesitamos usar paquetes
				else if (linea.startsWith("at")) {
					String pack = extraerPaqueteDeLinea(linea);
					if (pack != null) {
						if (!package_malo.contains(pack) && !packNoEsPermite(pack, dec, fatal)) {
							packs.put(pack, idiomaNivel + dec, fatal);
							package_malo.add(pack);
						}
					}
				}

				// Procesar línea que contiene ClassNotFoundException
				// No necesitamos fatal para FabricMC o FeatureCreep y en casos en ModLauncher
				// en launcher_log
				else if ((linea.contains("ClassNotFoundException") || linea.contains("NoClassDefFoundError"))
						&& !linea.contains("The specified mixin") && !linea.contains("WARN/]")) {

					Map.Entry<String, String> resultado = procesarErrorClaseNoEncontrada(linea, arr, linea_num, lvl);
					if (resultado != null) {
						String claseFaltante = resultado.getKey();
						String sospechoso = resultado.getValue();

						if (!fatal_clases_no_existe.containsKey(claseFaltante)
								|| fatal_clases_no_existe.get(claseFaltante).isEmpty()) {
							fatal_clases_no_existe.put(claseFaltante, sospechoso);
						}
					}
				}

				// Extraer contenido entre llaves
				List<String> llavesEncontradas = extraerLlavesDeLinea(linea);
				for (String content : llavesEncontradas) {
					if (!brace_malo.contains(content)) {
						braces.put(content, idiomaNivel + dec, fatal);
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
	 * @param linea     Línea que contiene el error de clase
	 * @param arr       Array completo de líneas del stack trace
	 * @param linea_num Número de línea actual
	 * @param lvl       Nivel de prioridad
	 * @return Un entry con la clase faltante y el sospechoso, o null si no se
	 *         encontró
	 */
	public Map.Entry<String, String> procesarErrorClaseNoEncontrada(String linea, String[] arr, int linea_num,
			int lvl) {
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
				if (!packNoEsPermite(pack, Integer.toString(lvl) + "," + Integer.toString(linea_num), false)) {
					sospechoso = pack;
					return new AbstractMap.SimpleEntry<>(claseFaltante, sospechoso);
				}
			}
		}

		return null;
	}

	public void processarSMHandler(String pack, String dec, boolean fatal) {
		// Split the input string by '$' to identify potential mod IDs
		String[] parts = pack.split("\\$");

		// Check if the string has at least 4 parts (indicating the presence of a mod
		// ID)
		if (parts.length >= 4) {
			// Extract the mod ID (located after the third '$')
			String modid = parts[3];

			// Log the extracted mod ID for debugging purposes
			CrashDetectorLogger.log("Mod ID encontrado: " + modid);
			if (!modid_malo.contains(modid)) {
				modid_malo.add(modid);

				// Add the mod ID to the modids map with the appropriate key and value
				modids.put(modid, MonitorDePID.idioma.nivel() + dec, fatal);
			}

		} else {
			// Log that the line does not contain a valid mod ID and will be ignored
			CrashDetectorLogger.log("Línea ignorada: No contiene un mod ID válido.");
		}
	}

//	public boolean trace_contain(String[] arr, String cont) {
//		// TODO Auto-generated method stub
//		if (arr.length == 1) {
//			return arr[0].contains(cont);
//		}
//
//		for (int i = 1; i < arr.length; i++) {// start at 1 to only get trace
//			if (arr[i].contains(cont)) {
//				return true;
//			}
//		}
//
//		return false;
//	}

	public static boolean esModNoPermite(String modid) {
		// TODO Auto-generated method stub
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

	public boolean packNoEsPermite(String pack, String dec, boolean fatal) {
		// TODO Auto-generated method stub

		if (pack.contains("handler$")) {
			processarSMHandler(pack, dec, fatal);
		}

		for (String prefix : package_no_permite) {
			if (pack.startsWith(prefix)) {
				return true;
			}
		}

		return false;
	}

	public static String[] eliminarDuplicados(String[] inputArray) {
		// 使用LinkedHashSet来保持插入顺序的同时去除重复项
		Set<String> set = new HashSet<>(Arrays.asList(inputArray));

		// 将去重后的set转回数组
		String[] ret = set.toArray(new String[0]);

		return ret;
	}

	public static List<String> obtenerTracesFatal(String log) {
		List<String> ret = new ArrayList<>();
		String[] lineas = log.split(nl);
		int len = lineas.length;

		for (int i = 0; i < len; i++) {
			String linea = lineas[i];
			if (linea.contains("/FATAL]")) {

				StringBuilder trace = new StringBuilder();
				trace.append(linea);
				int j = i + 1;

				while (j < len && esParteDeStack(lineas[j])) {
					trace.append(nl).append(lineas[j]);
					j++;
				}
				String str = trace.toString();
				if (tracePermite(str)) {
					ret.add(str);
				}
			}
		}
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
				// excepcion mensajes (“org.spongepowered…InvalidMixinException …”)
				|| t.matches("^[a-zA-Z0-9_.]+\\.[A-Z][a-zA-Z0-9]+Exception.*");
	}

//	private static void anadirTracesFata(StringBuilder trace, String[] lineas, int index) {
//		// TODO Auto-generated method stub
//		int len = lineas.length;
//		for (int i = index; i < len; i++) {
//			String linea = lineas[i];
//			if (linea.trim().startsWith("at ")) {
//				trace.append(linea);
//			} else {
//				return;
//			}
//
//		}
//
//	}

	private void extractarJarNombresEnBrackets(String linea, boolean fatal, int lvl, int linea_num) {
		int startIdx = linea.indexOf('[');
		int endIdx = linea.indexOf(']');

		while (startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
			String candidito = linea.substring(startIdx + 1, endIdx);
			// Check if the candidate string ends with ".jar" or contains ".jar%23"
			if (candidito.contains(".jar") && !isJarNoPermite(candidito)) {
				if (!jar_malo.contains(candidito)) {
					jar_malo.add(candidito);
					jars.put(candidito + MonitorDePID.idioma.nivel() + Integer.toString(lvl) + ","
							+ Integer.toString(linea_num), fatal);
				}
			}
			// Look for the next '[' and ']'
			startIdx = linea.indexOf('[', endIdx);
			endIdx = linea.indexOf(']', endIdx + 1);
		}
	}

	public static List<String> obtenerTraces(String log) {
		List<String> stackTraces = new ArrayList<>();
		Matcher matcher = STACK_TRACE_PATTERN.matcher(log);
		while (matcher.find()) {

			String str = matcher.group();
			if (tracePermite(str)) {
				stackTraces.add(str);
			}
		}
		return stackTraces;
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

	public static List<String> inverso(List<String> original) {
		List<String> reversed = new ArrayList<>(original);
		Collections.reverse(reversed);
		return reversed;
	}

}