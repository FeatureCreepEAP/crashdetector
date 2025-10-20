package com.asbestosstar.crashdetector.cargador;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig.Entry;
import com.electronwill.nightconfig.toml.TomlParser;

/**
 * Usa Electronwill NightConfig para leer mods.toml Devuelve modId principal y
 * modIds en secciones de dependencias Comentarios en español simples
 */
public class AnalizadorModsTomlForgeElectronwill {

	public static List<String> extraerModIds(String tomlTexto) {
		List<String> salida = new ArrayList<>();

		TomlParser parser = new TomlParser();
		UnmodifiableConfig root = parser.parse(new StringReader(tomlTexto));

		// obtener lista [[mods]] y tomar modId del primero
		Object modsVal = root.get("mods");
		if (modsVal instanceof List<?>) {
			List<?> mods = (List<?>) modsVal;
			if (!mods.isEmpty() && mods.get(0) instanceof UnmodifiableConfig) {
				Object modId = ((UnmodifiableConfig) mods.get(0)).get("modId");
				if (modId instanceof String && !((String) modId).isEmpty())
					agregarUnico(salida, (String) modId);
			}
		}

		// recorrer claves de nivel raiz que empiezan por dependencies punto
		for (Entry e : root.entrySet()) {
			String k = e.getKey();
			if (k != null && k.startsWith("dependencies.")) {
				String modid = k.substring("dependencies.".length()).trim();
				if (!modid.isEmpty())
					agregarUnico(salida, modid);
			}
		}
		return salida;
	}

	private static void agregarUnico(List<String> lista, String v) {
		if (!lista.contains(v))
			lista.add(v);
	}
}
