package com.asbestosstar.crashdetector.cargador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

/**
 * Cargador para plugins/mods de Bukkit, Spigot y Paper.
 */
public class CargadorBukkit implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {

		// Se recorre la lista de rutas internas del archivo
		for (String archivo : mod.archivos()) {
			String norm = archivo.replace('\\', '/');
			String lower = norm.toLowerCase(Locale.ROOT);

			// Bukkit y derivados usan plugin.yml o paper-plugin.yml
			if (lower.equals("plugin.yml") || lower.equals("paper-plugin.yml")) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean cargadorEsActivado() {

		// Bukkit / Spigot / Paper
		return Cargador.claseExiste("org.bukkit.Bukkit") || Cargador.claseExiste("org.bukkit.plugin.java.JavaPlugin")
				|| Cargador.claseExiste("org.spigotmc.SpigotConfig")
				|| Cargador.claseExiste("com.destroystokyo.paper.PaperConfig")
				|| Cargador.claseExiste("io.papermc.paper.plugin.loader.PluginLoader");
	}

	@Override
	public String id() {
		return "bukkit";
	}

	/**
	 * Extrae el identificador principal del plugin desde plugin.yml o
	 * paper-plugin.yml.
	 *
	 * En Bukkit/Paper el campo mas parecido a modid es "name".
	 */
	public static List<String> parsearIdModBukkit(String texto) throws IOException {
		List<String> salida = new ArrayList<>();

		String name = extraerCampoSimpleYaml(texto, "name");

		if (name != null) {
			name = limpiarValorYaml(name);

			if (!name.isEmpty() && !salida.contains(name)) {
				salida.add(name);
			}
		}

		return salida;
	}

	/**
	 * Extrae la version desde plugin.yml o paper-plugin.yml.
	 */
	public static String parsearVersionModBukkit(String texto) {
		String version = extraerCampoSimpleYaml(texto, "version");

		if (version == null) {
			return "";
		}

		version = limpiarValorYaml(version);

		return version.isEmpty() ? "" : version;
	}

	/**
	 * Extrae la clase principal desde plugin.yml o paper-plugin.yml.
	 */
	public static String parsearMainModBukkit(String texto) {
		String main = extraerCampoSimpleYaml(texto, "main");

		if (main == null) {
			return "";
		}

		main = limpiarValorYaml(main);

		return main.isEmpty() ? "" : main;
	}

	/**
	 * Extrae un campo YAML sencillo del tipo:
	 *
	 * clave: valor
	 *
	 * Ignora mayusculas/minusculas en la clave y soporta espacios.
	 */
	private static String extraerCampoSimpleYaml(String texto, String campo) {

		if (texto == null || texto.isEmpty() || campo == null || campo.isEmpty()) {
			return null;
		}

		Pattern p = Pattern.compile("(?im)^\\s*" + Pattern.quote(campo) + "\\s*:\\s*(.+?)\\s*$");
		Matcher m = p.matcher(texto);

		if (m.find()) {
			return m.group(1);
		}

		return null;
	}

	/**
	 * Limpia un valor YAML sencillo: - quita espacios - quita comillas simples o
	 * dobles si envuelven todo el valor - elimina comentarios al final si existen
	 */
	private static String limpiarValorYaml(String valor) {

		if (valor == null) {
			return "";
		}

		String limpio = valor.trim();

		// Quitar comentario final si existe
		int comentario = limpio.indexOf('#');
		if (comentario >= 0) {
			limpio = limpio.substring(0, comentario).trim();
		}

		// Quitar comillas envolventes
		if (limpio.length() >= 2) {
			if ((limpio.startsWith("\"") && limpio.endsWith("\""))
					|| (limpio.startsWith("'") && limpio.endsWith("'"))) {
				limpio = limpio.substring(1, limpio.length() - 1).trim();
			}
		}

		return limpio;
	}
}