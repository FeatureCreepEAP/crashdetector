package com.asbestosstar.crashdetector.cargador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.config.json.Json;

/**
 * Cargador para Flint Loader.
 */
public class CargadorFlint implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {

		for (String archivo : mod.archivos()) {

			if (archivo.equals("flintmodule.json")) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean cargadorEsActivado() {
		return Cargador.claseExiste("net.flintloader.loader.FlintLoader")
				|| Cargador.claseExiste("net.flintloader.loader.api.FlintModule");
	}

	@Override
	public String id() {
		return "flint";
	}

	/**
	 * Extrae el id del mod desde flintmodule.json.
	 */
	public static List<String> parsearIdModFlint(String jsonTexto) throws IOException {
		List<String> salida = new ArrayList<>();

		try {
			Json.Nodo raiz = Json.leer(jsonTexto);
			Json.Nodo idNodo = raiz.obtener("id");

			if (idNodo != null) {
				String id = idNodo.comoCadena();

				if (id != null) {
					id = id.trim();

					if (!id.isEmpty() && !salida.contains(id)) {
						salida.add(id);
					}
				}
			}
		} catch (Throwable ignorado) {
		}

		return salida;
	}

	/**
	 * Extrae la version desde flintmodule.json.
	 */
	public static String parsearVersionModFlint(String jsonTexto) {
		try {
			Json.Nodo raiz = Json.leer(jsonTexto);
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
		} catch (Throwable ignorado) {
		}

		return "";
	}

}