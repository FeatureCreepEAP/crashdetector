package com.asbestosstar.crashdetector.cargador;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Analiza mods.toml de Forge Si Electronwill esta disponible usa
 * AnalizadorModsTomlForgeElectronwill Si no esta disponible usa respaldo por
 * regex
 */
public class AnalizadorModsTomlForge {

	public static boolean electronwillDisponible() {
		try {
			Class.forName("com.electronwill.nightconfig.toml.TomlParser", false,
					AnalizadorModsTomlForge.class.getClassLoader());
			Class.forName("com.electronwill.nightconfig.core.UnmodifiableConfig", false,
					AnalizadorModsTomlForge.class.getClassLoader());
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	/**
	 * Devuelve modId principal y modIds en secciones de dependencias Usa
	 * Electronwill si esta presente Usa regex si no lo esta
	 */
	public static List<String> extraerModIds(String toml) {
		if (electronwillDisponible()) {
			return AnalizadorModsTomlForgeElectronwill.extraerModIds(toml);
		} else {
			return extraerModIdsPorRegex(toml);
		}
	}

	// respaldo por regex simple
	private static List<String> extraerModIdsPorRegex(String toml) {
		List<String> nombres = new ArrayList<>();

		java.util.regex.Pattern pMods = java.util.regex.Pattern
				.compile("\\[\\[mods\\]\\][^\\[]*?modId\\s*\\=\\s*\"([^\"]+)\"", java.util.regex.Pattern.DOTALL);
		java.util.regex.Matcher mMods = pMods.matcher(toml);
		if (mMods.find()) {
			String modIdPrincipal = mMods.group(1).trim();
			if (!modIdPrincipal.isEmpty() && !nombres.contains(modIdPrincipal)) {
				nombres.add(modIdPrincipal);
			}
		} else {
			nombres.add("Mod Forge desconocido");
		}

		java.util.regex.Pattern pDep = java.util.regex.Pattern.compile("\\[\\[dependencies\\.([a-zA-Z0-9_\\-]+)\\]\\]");
		java.util.regex.Matcher mDep = pDep.matcher(toml);
		while (mDep.find()) {
			String modid = mDep.group(1).trim();
			if (!modid.isEmpty() && !nombres.contains(modid)) {
				nombres.add(modid);
			}
		}
		return nombres;
	}
}
