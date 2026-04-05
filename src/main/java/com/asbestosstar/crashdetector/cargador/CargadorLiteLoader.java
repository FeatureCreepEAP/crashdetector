package com.asbestosstar.crashdetector.cargador;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.json.Json;

/**
 * Cargador para mods de LiteLoader.
 */
public class CargadorLiteLoader implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {

		// Se recorre la lista de rutas internas del mod
		for (String archivo : mod.archivos()) {
			String norm = archivo.replace('\\', '/');
			String lower = norm.toLowerCase(Locale.ROOT);

			// Deteccion de litemod.json en la raiz del mod
			if (lower.equals("litemod.json")) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean cargadorEsActivado() {

		// Clase base de LiteLoader
		return Cargador.claseExiste("com.mumfrey.liteloader.core.LiteLoader");
	}

	@Override
	public String id() {
		return "liteloader";
	}

	/**
	 * Extrae ids de litemod.json.
	 *
	 * LiteLoader normalmente usa "name" como identificador visible del mod. Para
	 * mantener compatibilidad con el resto del sistema, se devuelve una lista.
	 */
	public static List<String> parsearIdModLiteLoader(String texto) throws java.io.IOException {
		List<String> salida = new ArrayList<>();

		try {
			// Intento con la capa Json
			Json.Nodo raiz = Json.leer(texto);

			String name = raiz.obtener("name").comoCadena();

			if (name != null) {
				name = name.trim();
				if (!name.isEmpty() && !salida.contains(name)) {
					salida.add(name);
				}
			}

			return salida;
		} catch (Throwable t) {
			// Respaldo por regex
			Pattern pname = Pattern.compile("\"name\"\\s*:\\s*\"([^\"]+)\"");
			Matcher mname = pname.matcher(texto);

			if (mname.find()) {
				String name = mname.group(1).trim();
				if (!name.isEmpty() && !salida.contains(name)) {
					salida.add(name);
				}
			}

			return salida;
		}
	}

	/**
	 * Extrae la version desde litemod.json.
	 *
	 * El campo version puede venir como numero o como cadena, asi que se soportan
	 * ambos casos.
	 */
	public static String parsearVersionModLiteLoader(String texto) {

		try {
			// Intento con la capa Json
			Json.Nodo raiz = Json.leer(texto);

			Json.Nodo versionNodo = raiz.obtener("version");

			if (versionNodo != null) {
				String version = versionNodo.comoCadena();

				if (version != null) {
					version = version.trim();
					if (!version.isEmpty()) {
						return version;
					}
				}
			}

		} catch (Throwable t) {
			// Si falla Json, continuar con regex
		}

		// ==========================
		// RESPALDO POR REGEX
		// ==========================

		// Caso cadena: "version": "4.0"
		Pattern pverCadena = Pattern.compile("\"version\"\\s*:\\s*\"([^\"]+)\"");
		Matcher mverCadena = pverCadena.matcher(texto);

		if (mverCadena.find()) {
			String version = mverCadena.group(1).trim();
			if (!version.isEmpty()) {
				return version;
			}
		}

		// Caso numerico: "version": 4.0
		Pattern pverNumero = Pattern.compile("\"version\"\\s*:\\s*([-+]?[0-9]+(?:\\.[0-9]+)?)");
		Matcher mverNumero = pverNumero.matcher(texto);

		if (mverNumero.find()) {
			String version = mverNumero.group(1).trim();
			if (!version.isEmpty()) {
				return version;
			}
		}

		return "";
	}
}