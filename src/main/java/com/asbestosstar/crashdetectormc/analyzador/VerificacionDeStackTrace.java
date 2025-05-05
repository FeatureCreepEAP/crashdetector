package com.asbestosstar.crashdetectormc.analyzador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class VerificacionDeStackTrace implements Verificaciones {

	public boolean activado=false;
public CDStringBuilder sb;
	
	// 正则表达式用于匹配Java异常堆栈跟踪
	private static final Pattern STACK_TRACE_PATTERN = Pattern.compile("(?m)(^\\S.*(?:\\r?\\n[ \\t]+at\\s+.*)+)");
	// 正则表达式用于匹配包含org.spongepowered.asm.mixin的异常并提取JSON文件名（不包括refmap）
	private static final Pattern JSON_PATTERN = Pattern.compile("(\\S+\\.json)(?=[: ])");
	// 正则表达式用于匹配{}内的内容
	private static final Pattern BRACE_PATTERN = Pattern.compile("\\{([^}]+)\\}");
	
	List<String> sm_config = new ArrayList<>();
	Map<String, Boolean> jars = new LinkedHashMap<>();// FATAL
	Map<String, Boolean> modids = new LinkedHashMap<>();// FATAL
	Map<String, Boolean> packs = new LinkedHashMap<>();// FATAL

	List<String> braceContentos = new LinkedList<>();

	List<String> fatal_clases_no_existe = new ArrayList<String>();

	// These only contain the content but not lvl
	List<String> jar_malo = new ArrayList<String>();
	List<String> modid_malo = new ArrayList<String>();
	List<String> package_malo = new ArrayList<String>();

	public String[] package_no_permite = { "java.", "net.minecraft", "net.minecraftforge", "org.spongepowered",
			"it.unimi", "com.mojang.", "cpw.", "featurecreep.", "jdk.", "sun.", "com.sun.", "org.lwjgl.", "org.apache.",
			"io.netty", "org.prismlauncher", "io.github.zekerzhayard", "org.multimc", "org.polymc", "org.tlauncher", "net.fabricmc","com.asbestosstar","asbestosstar."

	};

	@Override
	public void verificar(String log, CDStringBuilder build) {
		this.sb=build;
		int lvl = 0;
		for (String trace : inverso(obtenerTracesFatal(log))) {// Las ultimas son las más importante
			lvl++;
			this.procesarTrace(build, trace, true, lvl);
		}

		for (String trace : inverso(obtenerTraces(log))) {// Las ultimas son las más importante
			lvl++;
			this.procesarTrace(build, trace, false, lvl);
		}

	}

	public void procesarTrace(CDStringBuilder build, String trace, boolean fatal, int lvl) {

		List<String> archivos_json = obtenerArchivosJsonEnMixinExceptions(trace);

		if (!archivos_json.isEmpty()) {
			for (String jsonFile : archivos_json) {
				if (!sm_config.contains(jsonFile) && !jsonFile.endsWith(".refmap.json")) {
					sm_config.add(jsonFile);
					activado=true;
					build.append(MonitorDePID.idioma.config_spongemixin_problematico(jsonFile)).append(nl_html);
				}
			}
		} else {

			String[] arr = trace.split(nl);
			int linea_num = 0;

			for (String untrimmed : arr) {
				linea_num++;
				String line = untrimmed.trim();
				if (line.contains("[")) {// There is not always a Jar
					extractarJarNombresEnBrackets(line, fatal, lvl, linea_num);
				} else if (line.contains("/")) {// Some dev enviornments like ForgeGradle or dev orientated launchers
												// like TLauncher display the modID and layer, this is helpful
												// especially when the Jar cannot be found
					String[] arr_modid = line.split("/");
					if (arr_modid.length > 1) {
						String modid = arr_modid[1].split("@")[0];
						if (!modid_malo.contains(modid) && !line.split("/")[0].startsWith("java.")
								&& !esModNoPermite(modid) && line.startsWith("at")) {
							modid_malo.add(modid);
							modids.put(
									modid + MonitorDePID.idioma.nivel() + String.valueOf(lvl) + "** ln** " + String.valueOf(linea_num),
									fatal);

						}
					}

				} 
				else if (line.startsWith("at")) { // a veces necesitemos usar packages
				    String pack = line.substring(3);  if (!package_malo.contains(pack) && !packNoEsPermite(pack)) {
				        packs.put(
				            pack + MonitorDePID.idioma.nivel() + Integer.toString(lvl) + "** ln** " + Integer.toString(linea_num),
				            fatal
				        );
				        package_malo.add(pack); 
				    }
				}
				
				//else if (line.contains("ClassNotFoundException") && fatal) {
				else if (line.contains("ClassNotFoundException")) {//No Necesitemos fatal en FabricMC o FeatureCreep
					fatal_clases_no_existe.add(MonitorDePID.idioma.faltar_de_clases_fatales() + line);
				}

				Matcher braceMatcher = BRACE_PATTERN.matcher(line);
				while (braceMatcher.find()) {
					String content = braceMatcher.group(1).trim();
					if (!braceContentos.contains(content)) {// TODO, readd levels
						braceContentos.add(content);
					}
				}

			}

//			if (trace_contain(arr, "[")) {
//				for (String untrimmed : arr) {
//					String line = untrimmed.trim();
//					extractJarNamesSquareBracket(line, jars, false);
//				}
//			}

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

	private boolean esModNoPermite(String modid) {
		// TODO Auto-generated method stub
		if (modid.replace(" ", "").equals("")) {
			return true;
		}

		String[] ids = { "java", "minecraft", "minecraftforge","net.minecraftforge", "eventbus", "cpw.", "coremods", "featurecreep", "mixin",
				"accesstransformer","forge", "authlib","sun.", "jdk.", "java.", "fmlloader", "fmlcore", "org.spongepowered.mixin",
				"fmlearlydisplay","com.sun.jna", "text2speech","xf:crashdetector:default","crashdetector" };

		for (String id : ids) {
			if (modid.startsWith(id)) {
				return true;
			}
		}

		return false;
	}

	private boolean packNoEsPermite(String pack) {
		// TODO Auto-generated method stub

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

	private List<String> obtenerTracesFatal(String log) {
		// TODO Auto-generated method stub
		List<String> ret = new ArrayList<String>();
		String[] lineas = log.split(nl);
		int len = lineas.length;
		for (int i = 0; i < len; i++) {
			String line = lineas[i];
			if (line.contains("/FATAL]")) {
				if (i + 2 > len) {
				} else {
					StringBuilder trace = new StringBuilder();
					trace.append(lineas[i + 1]);
					anadirTracesFata(trace, lineas, i + 2);
					ret.add(trace.toString());
				}
			}
		}
		return ret;
	}

	private void anadirTracesFata(StringBuilder trace, String[] lineas, int index) {
		// TODO Auto-generated method stub
		int len = lineas.length;
		for (int i = index; i < len; i++) {
			String linea = lineas[i];
			if (linea.trim().startsWith("at ")) {
				trace.append(linea);
			} else {
				return;
			}

		}

	}

	private void extractarJarNombresEnBrackets(String linea, boolean fatal, int lvl, int linea_num) {
		int startIdx = linea.indexOf('[');
		int endIdx = linea.indexOf(']');

		while (startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
			String candidito = linea.substring(startIdx + 1, endIdx);
			// Check if the candidate string ends with ".jar" or contains ".jar%23"
			if (candidito.contains(".jar") && !isJarNoPermite(candidito)) {
				if (!jar_malo.contains(candidito)) {
					jar_malo.add(candidito);
					jars.put(
							candidito + MonitorDePID.idioma.nivel() + Integer.toString(lvl) + "** ln** " + Integer.toString(linea_num),
							fatal);
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
			stackTraces.add(matcher.group());
		}
		return stackTraces;
	}

	public List<String> obtenerArchivosJsonEnMixinExceptions(String contento_de_logs) {
		List<String> archivos_json = new ArrayList<>();

		String[] lineas = contento_de_logs.split("\r?\n");
		for (String linea : lineas) {
			if (linea.contains("org.spongepowered.asm.mixin")) {
				Matcher matcher = JSON_PATTERN.matcher(linea.trim());
				while (matcher.find()) {
					// Group 1 captures the mixin JSON file name
					if (matcher.group(1) != null) {
						archivos_json.add(matcher.group(1));
					}
				}
			}
		}

		return archivos_json;
	}

	private boolean isJarNoPermite(String jarName) {
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
		if (jarName.startsWith("securejarhandler")) {
			return true;
		}
		if (jarName.startsWith("core-")) {
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
		return false;
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new VerificacionDeStackTrace();
	}

	public static List<String> inverso(List<String> original) {
		List<String> reversed = new ArrayList<>(original);
		Collections.reverse(reversed);
		return reversed;
	}
	
	
	
	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}
}