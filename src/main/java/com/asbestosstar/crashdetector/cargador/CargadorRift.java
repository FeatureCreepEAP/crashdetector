package com.asbestosstar.crashdetector.cargador;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.json.Json;

/**
 * Cargador para mods de Rift.
 */
public class CargadorRift implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {

		// Se recorre la lista de rutas internas del mod
		for (String archivo : mod.archivos()) {
			String norm = archivo.replace('\\', '/');
			String lower = norm.toLowerCase(Locale.ROOT);

			// Deteccion de riftmod.json en la raiz del mod
			if (lower.equals("riftmod.json")) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean cargadorEsActivado() {

		// RiftLoader
		return Cargador.claseExiste("org.dimdev.riftloader.RiftLoader");
	}

	@Override
	public String id() {
		return "rift";
	}

	/**
	 * Extrae todos los ids desde un archivo riftmod.json.
	 *
	 * Rift normalmente solo usa "id", pero se devuelve una lista para mantener el
	 * mismo patron que otros cargadores.
	 *
	 * Usa la clase Json y si falla usa una ruta de respaldo con regex.
	 */
	public static List<String> parsearIdModRift(String texto) throws java.io.IOException {
		List<String> salida = new ArrayList<>();

		try {
			// Intento con la capa Json
			Json.Nodo raiz = Json.leer(texto);

			String id = raiz.obtener("id").comoCadena();

			if (id != null) {
				id = id.trim();
				if (!id.isEmpty() && !salida.contains(id)) {
					salida.add(id);
				}
			}

			return salida;
		} catch (Throwable t) {
			// Respaldo por regex
			Pattern pid = Pattern.compile("\"id\"\\s*:\\s*\"([^\"]+)\"");
			Matcher mid = pid.matcher(texto);

			if (mid.find()) {
				String id = mid.group(1).trim();
				if (!id.isEmpty() && !salida.contains(id)) {
					salida.add(id);
				}
			}

			return salida;
		}
	}

	/**
	 * Extrae la version desde riftmod.json.
	 *
	 * Rift no parece tener un campo de version en este archivo, asi que se devuelve
	 * cadena vacia.
	 */
	public static String parsearVersionModRift(String texto) {
		return "";
	}
}