package com.asbestosstar.crashdetector.cargador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

/**
 * Cargador para plugins de CanaryMod.
 */
public class CargadorCanary implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {

		boolean tieneCanaryInf = false;
		boolean tieneClasePlugin = false;

		for (String archivo : mod.archivos()) {

			if (archivo.equals("Canary.inf")) {
				tieneCanaryInf = true;
			}

			if (archivo.endsWith(".class")) {
				tieneClasePlugin = true;
			}
		}

		return tieneCanaryInf && tieneClasePlugin;
	}

	@Override
	public boolean cargadorEsActivado() {

		return Cargador.claseExiste("net.canarymod.Canary") || Cargador.claseExiste("net.canarymod.plugin.Plugin")
				|| Cargador.claseExiste("net.canarymod.plugin.PluginManager")
				|| Cargador.claseExiste("net.canarymod.plugin.lifecycle.JavaPluginLifecycle")
				|| Cargador.claseExiste("net.canarymod.hook.HookExecutor");
	}

	@Override
	public String id() {
		return "canary";
	}

	/**
	 * Extrae el identificador principal del plugin desde canary.inf.
	 *
	 * En CanaryMod normalmente el campo principal es name.
	 */
	public static List<String> parsearIdModCanary(String texto) throws IOException {
		List<String> salida = new ArrayList<>();

		String name = extraerCampoSimple(texto, "name");

		if (name != null) {
			name = limpiarValor(name);

			if (!name.isEmpty() && !salida.contains(name)) {
				salida.add(name);
			}
		}

		return salida;
	}

	/**
	 * Extrae la version desde canary.inf.
	 */
	public static String parsearVersionModCanary(String texto) {
		String version = extraerCampoSimple(texto, "version");

		if (version == null) {
			return "";
		}

		version = limpiarValor(version);

		return version.isEmpty() ? "" : version;
	}

	/**
	 * Extrae la clase principal desde canary.inf.
	 */
	public static String parsearMainModCanary(String texto) {
		String main = extraerCampoSimple(texto, "main-class");

		if (main == null) {
			main = extraerCampoSimple(texto, "main");
		}

		if (main == null) {
			return "";
		}

		main = limpiarValor(main);

		return main.isEmpty() ? "" : main;
	}

	/**
	 * Extrae el tipo de ciclo de vida desde canary.inf.
	 *
	 * Normalmente sera java.
	 */
	public static String parsearLifecycleCanary(String texto) {
		String lifecycle = extraerCampoSimple(texto, "language");

		if (lifecycle == null) {
			lifecycle = extraerCampoSimple(texto, "lifecycle");
		}

		if (lifecycle == null) {
			return "";
		}

		lifecycle = limpiarValor(lifecycle);

		return lifecycle.isEmpty() ? "" : lifecycle;
	}

	/**
	 * Extrae dependencias sencillas desde canary.inf.
	 *
	 * Soporta valores separados por coma.
	 */
	public static List<String> parsearDependenciasCanary(String texto) {
		List<String> salida = new ArrayList<>();

		String deps = extraerCampoSimple(texto, "dependencies");

		if (deps == null) {
			deps = extraerCampoSimple(texto, "depends");
		}

		if (deps == null) {
			return salida;
		}

		for (String parte : deps.split(",")) {
			String dep = limpiarValor(parte);

			if (!dep.isEmpty() && !salida.contains(dep)) {
				salida.add(dep);
			}
		}

		return salida;
	}

	/**
	 * Extrae un campo sencillo tipo:
	 *
	 * clave=valor
	 *
	 * Tambien acepta:
	 *
	 * clave: valor
	 */
	private static String extraerCampoSimple(String texto, String campo) {

		if (texto == null || texto.isEmpty() || campo == null || campo.isEmpty()) {
			return null;
		}

		Pattern p = Pattern.compile("(?im)^\\s*" + Pattern.quote(campo) + "\\s*[:=]\\s*(.+?)\\s*$");
		Matcher m = p.matcher(texto);

		if (m.find()) {
			return m.group(1);
		}

		return null;
	}

	/**
	 * Limpia un valor sencillo:
	 *
	 * - quita espacios - elimina comentarios finales - quita comillas si envuelven
	 * todo el valor
	 */
	private static String limpiarValor(String valor) {

		if (valor == null) {
			return "";
		}

		String limpio = valor.trim();

		int comentarioHash = limpio.indexOf('#');
		int comentarioExclamacion = limpio.indexOf('!');

		int comentario = -1;

		if (comentarioHash >= 0 && comentarioExclamacion >= 0) {
			comentario = Math.min(comentarioHash, comentarioExclamacion);
		} else if (comentarioHash >= 0) {
			comentario = comentarioHash;
		} else if (comentarioExclamacion >= 0) {
			comentario = comentarioExclamacion;
		}

		if (comentario >= 0) {
			limpio = limpio.substring(0, comentario).trim();
		}

		if (limpio.length() >= 2) {
			if ((limpio.startsWith("\"") && limpio.endsWith("\""))
					|| (limpio.startsWith("'") && limpio.endsWith("'"))) {
				limpio = limpio.substring(1, limpio.length() - 1).trim();
			}
		}

		return limpio;
	}
}