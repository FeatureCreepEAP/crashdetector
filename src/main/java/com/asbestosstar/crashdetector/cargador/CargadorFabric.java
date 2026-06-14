package com.asbestosstar.crashdetector.cargador;

import java.util.Locale;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.config.json.Json;

public class CargadorFabric implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {

		boolean tieneFabric = false;
		boolean tieneQuilt = false;

		// Se recorre la lista de rutas internas del mod
		for (String archivo : mod.archivos()) {

			if (archivo.equals("fabric.mod.json")) {
				tieneFabric = true;
			}

			if (archivo.equals("quilt.mod.json")) {
				tieneQuilt = true;
			}
		}

		// Si tiene fabric.mod.json, se considera Fabric aunque tambien tenga Quilt.
		if (tieneFabric) {
			return true;
		}

		// Si solo tiene quilt.mod.json, se considera compatible con este cargador solo
		// cuando Quilt esta activo.
		return tieneQuilt && esQCC();
	}

	/**
	 * Devuelve true si Quilt Loader esta presente.
	 *
	 * QCC = Quilt Compatibility Check.o Quilt Community Collab
	 */
	public static boolean esQCC() {
		return Cargador.claseExiste("org.quiltmc.loader.api.QuiltLoader");
	}

	@Override
	public boolean cargadorEsActivado() {
		// TODO Auto-generated method stub
		return Cargador.claseExiste("net.fabricmc.loader.api.FabricLoader");
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "fabric";
	}

	/**
	 * Extrae todos los ids y provides desde un archivo fabric.mod.json Devuelve una
	 * lista con valores unicos Usa la clase Json y si falla usa una ruta de
	 * respaldo con regex
	 */
	public static java.util.List<String> parsearIdModFabric(String texto) throws java.io.IOException {
		java.util.List<String> salida = new java.util.ArrayList<>();

		try {
			// intento con la capa Json
			Json.Nodo raiz = Json.leer(texto);

			// id principal
			String id = raiz.obtener("id").comoCadena();
			if (id != null && !id.isEmpty() && !salida.contains(id)) {
				salida.add(id);
			}

			// arreglo provides si existe
			Json.Nodo provides = raiz.obtener("provides");
			if (provides != null && provides.esArreglo()) {
				for (int i = 0; i < provides.tamano(); i++) {
					String p = provides.en(i).comoCadena();
					if (p != null && !p.isEmpty() && !salida.contains(p)) {
						salida.add(p);
					}
				}
			}

			return salida;
		} catch (Throwable t) {
			// respaldo por si Json no esta disponible o hubo error de parseo
			// id
			java.util.regex.Pattern pid = java.util.regex.Pattern.compile("\"id\"\\s*:\\s*\"([^\"]+)\"");
			java.util.regex.Matcher mid = pid.matcher(texto);
			if (mid.find()) {
				String id = mid.group(1).trim();
				if (!id.isEmpty() && !salida.contains(id)) {
					salida.add(id);
				}
			}

			// provides como arreglo
			java.util.regex.Pattern pprov = java.util.regex.Pattern.compile("\"provides\"\\s*:\\s*\\[(.*?)\\]",
					java.util.regex.Pattern.DOTALL);
			java.util.regex.Matcher mprov = pprov.matcher(texto);
			if (mprov.find()) {
				String dentro = mprov.group(1);
				java.util.regex.Matcher mitem = java.util.regex.Pattern.compile("\"([^\"]+)\"").matcher(dentro);
				while (mitem.find()) {
					String p = mitem.group(1).trim();
					if (!p.isEmpty() && !salida.contains(p)) {
						salida.add(p);
					}
				}
			}

			return salida;
		}
	}

	/**
	 * Extrae la version desde fabric.mod.json. Devuelve cadena vacia si no existe.
	 * Usa la clase Json y si falla usa respaldo por regex.
	 */
	public static String parsearVersionModFabric(String texto) {

		try {
			// intento con la capa Json
			Json.Nodo raiz = Json.leer(texto);

			String version = raiz.obtener("version").comoCadena();

			if (version != null) {
				version = version.trim();
				if (!version.isEmpty()) {
					return version;
				}
			}

		} catch (Throwable t) {
			// si falla Json, continuar con regex
		}

		// ==========================
		// RESPALDO POR REGEX
		// ==========================

		java.util.regex.Pattern pver = java.util.regex.Pattern.compile("\"version\"\\s*:\\s*\"([^\"]+)\"");

		java.util.regex.Matcher mver = pver.matcher(texto);

		if (mver.find()) {
			String version = mver.group(1).trim();
			if (!version.isEmpty()) {
				return version;
			}
		}

		return "";
	}

	/**
	 * Extrae el id desde quilt.mod.json.
	 *
	 * El id esta en:
	 *
	 * quilt_loader.id
	 */
	public static java.util.List<String> parsearIdModQuilt(String texto) throws java.io.IOException {
		java.util.List<String> salida = new java.util.ArrayList<>();

		try {
			Json.Nodo raiz = Json.leer(texto);
			Json.Nodo quiltLoader = raiz.obtener("quilt_loader");

			if (quiltLoader != null) {
				Json.Nodo idNodo = quiltLoader.obtener("id");

				if (idNodo != null) {
					String id = idNodo.comoCadena();

					if (id != null) {
						id = id.trim();

						if (!id.isEmpty() && !salida.contains(id)) {
							salida.add(id);
						}
					}
				}
			}

			return salida;
		} catch (Throwable t) {
			java.util.regex.Pattern p = java.util.regex.Pattern.compile(
					"\"quilt_loader\"\\s*:\\s*\\{.*?\"id\"\\s*:\\s*\"([^\"]+)\"", java.util.regex.Pattern.DOTALL);

			java.util.regex.Matcher m = p.matcher(texto);

			if (m.find()) {
				String id = m.group(1).trim();

				if (!id.isEmpty() && !salida.contains(id)) {
					salida.add(id);
				}
			}

			return salida;
		}
	}

	/**
	 * Extrae la version desde quilt.mod.json.
	 *
	 * La version esta en:
	 *
	 * quilt_loader.version
	 */
	public static String parsearVersionModQuilt(String texto) {

		try {
			Json.Nodo raiz = Json.leer(texto);
			Json.Nodo quiltLoader = raiz.obtener("quilt_loader");

			if (quiltLoader != null) {
				Json.Nodo versionNodo = quiltLoader.obtener("version");

				if (versionNodo != null) {
					String version = versionNodo.comoCadena();

					if (version != null) {
						version = version.trim();

						if (!version.isEmpty()) {
							return version;
						}
					}
				}
			}
		} catch (Throwable t) {
			// Si falla Json, continuar con regex.
		}

		java.util.regex.Pattern p = java.util.regex.Pattern.compile(
				"\"quilt_loader\"\\s*:\\s*\\{.*?\"version\"\\s*:\\s*\"([^\"]+)\"", java.util.regex.Pattern.DOTALL);

		java.util.regex.Matcher m = p.matcher(texto);

		if (m.find()) {
			String version = m.group(1).trim();

			if (!version.isEmpty()) {
				return version;
			}
		}

		return "";
	}

}
