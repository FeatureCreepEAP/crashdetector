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

	/**
	 * Devuelve la version del mod principal.
	 */
	public static String extraerVersionPrincipal(String toml) {
		if (electronwillDisponible()) {
			try {
				String v = AnalizadorModsTomlForgeElectronwill.extraerVersionPrincipal(toml);
				if (v != null && !v.isEmpty())
					return v;
			} catch (Throwable ignored) {
			}
		}
		return extraerVersionPrincipalPorRegex(toml);
	}

	// Respaldo por regex simple para versión principal
	private static String extraerVersionPrincipalPorRegex(String toml) {

		String version = null;

		// Captura el bloque completo del primer [[mods]]
		java.util.regex.Pattern pBloque = java.util.regex.Pattern.compile("\\[\\[mods\\]\\](.*?)(?=\\[\\[|\\Z)",
				java.util.regex.Pattern.DOTALL);

		java.util.regex.Matcher mBloque = pBloque.matcher(toml);

		if (mBloque.find()) {

			String bloque = mBloque.group(1);

			// Extraer version dentro del bloque
			java.util.regex.Pattern pVersion = java.util.regex.Pattern.compile("version\\s*=\\s*\"([^\"]+)\"");

			java.util.regex.Matcher mVersion = pVersion.matcher(bloque);

			if (mVersion.find()) {
				version = mVersion.group(1).trim();
			}
		}

		if (version == null || version.isEmpty()) {
			return "";
		}

		return version;
	}

}
