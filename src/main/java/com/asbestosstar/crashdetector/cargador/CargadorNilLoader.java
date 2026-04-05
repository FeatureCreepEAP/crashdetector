package com.asbestosstar.crashdetector.cargador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

/**
 * Cargador para mods de NilLoader.
 *
 * En NilLoader: - el archivo de metadata suele terminar en ".nilmod.css" - no
 * existe campo "id" dentro del archivo - el id del mod lo define el nombre del
 * archivo
 *
 * Ejemplo: modid.nilmod.css -> id = "modid"
 */
public class CargadorNilLoader implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {

		for (String archivo : mod.archivos()) {
			String norm = archivo.replace('\\', '/');
			String lower = norm.toLowerCase(Locale.ROOT);

			if (lower.endsWith(".nilmod.css")) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean cargadorEsActivado() {
		return Cargador.claseExiste("nilloader.NilAgent") || Cargador.claseExiste("nilloader.NilLoader")
				|| Cargador.claseExiste("nilloader.api.NilLoader");
	}

	@Override
	public String id() {
		return "nilloader";
	}

	/**
	 * Extrae el id del mod desde el nombre del archivo.
	 *
	 * Ejemplo: modid.nilmod.css -> modid carpeta/modid.nilmod.css -> modid
	 */
	public static List<String> parsearIdModNilLoaderDesdeNombreArchivo(String nombreArchivo) throws IOException {
		List<String> salida = new ArrayList<>();

		if (nombreArchivo == null || nombreArchivo.isEmpty()) {
			return salida;
		}

		String normalizado = nombreArchivo.replace('\\', '/');
		int ultimaBarra = normalizado.lastIndexOf('/');

		String base = ultimaBarra >= 0 ? normalizado.substring(ultimaBarra + 1) : normalizado;

		String sufijo = ".nilmod.css";
		if (!base.toLowerCase(Locale.ROOT).endsWith(sufijo)) {
			return salida;
		}

		String id = base.substring(0, base.length() - sufijo.length()).trim();

		if (!id.isEmpty()) {
			salida.add(id);
		}

		return salida;
	}

	/**
	 * Extrae el nombre visible del mod desde el archivo .nilmod.css.
	 *
	 * Ejemplo: name: "NilExample";
	 */
	public static String parsearNombreModNilLoader(String texto) {
		return extraerCampoNilmod(texto, "name");
	}

	/**
	 * Extrae la version del mod desde el archivo .nilmod.css.
	 *
	 * Ejemplo: version: "1.0.0";
	 */
	public static String parsearVersionModNilLoader(String texto) {
		return extraerCampoNilmod(texto, "version");
	}

	/**
	 * Extrae la descripcion desde el archivo .nilmod.css.
	 */
	public static String parsearDescripcionModNilLoader(String texto) {
		return extraerCampoNilmod(texto, "description");
	}

	/**
	 * Extrae autores desde el archivo .nilmod.css.
	 *
	 * Soporta tanto: authors: "unascribed";
	 *
	 * como variantes simples separadas por coma: authors: "a, b, c";
	 */
	public static List<String> parsearAutoresModNilLoader(String texto) {
		List<String> salida = new ArrayList<>();

		String autores = extraerCampoNilmod(texto, "authors");

		if (autores == null || autores.isEmpty()) {
			return salida;
		}

		String[] partes = autores.split(",");

		for (String parte : partes) {
			String limpio = parte.trim();
			if (!limpio.isEmpty() && !salida.contains(limpio)) {
				salida.add(limpio);
			}
		}

		return salida;
	}

	/**
	 * Extrae la clase del entrypoint premain.
	 *
	 * Ejemplo: premain: "com.example.nilexample.NilExamplePremain";
	 */
	public static String parsearEntrypointPremainNilLoader(String texto) {

		if (texto == null || texto.isEmpty()) {
			return "";
		}

		Matcher m = Pattern.compile("(?s)entrypoints\\s*\\{.*?\\bpremain\\s*:\\s*\"([^\"]+)\"\\s*;.*?\\}")
				.matcher(texto);

		if (m.find()) {
			String valor = m.group(1).trim();
			return valor.isEmpty() ? "" : valor;
		}

		// Respaldo mas simple por si el bloque tiene formato raro
		Matcher simple = Pattern.compile("\\bpremain\\s*:\\s*\"([^\"]+)\"\\s*;").matcher(texto);

		if (simple.find()) {
			String valor = simple.group(1).trim();
			return valor.isEmpty() ? "" : valor;
		}

		return "";
	}

	/**
	 * Extrae un campo simple dentro del bloque @nilmod.
	 *
	 * Ejemplo:
	 * 
	 * @nilmod { name: "NilExample"; version: "1.0.0"; }
	 */
	private static String extraerCampoNilmod(String texto, String campo) {

		if (texto == null || texto.isEmpty() || campo == null || campo.isEmpty()) {
			return "";
		}

		Matcher bloque = Pattern.compile("(?s)@nilmod\\s*\\{(.*?)\\}").matcher(texto);

		if (bloque.find()) {
			String dentro = bloque.group(1);

			Matcher valor = Pattern.compile("\\b" + Pattern.quote(campo) + "\\s*:\\s*\"([^\"]*)\"\\s*;")
					.matcher(dentro);

			if (valor.find()) {
				String limpio = valor.group(1).trim();
				return limpio.isEmpty() ? "" : limpio;
			}
		}

		return "";
	}
}