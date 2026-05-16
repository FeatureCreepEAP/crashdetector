package com.asbestosstar.crashdetector.cargador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

/**
 * Cargador para plugins de hMod.
 */
public class CargadorHMod implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {

		boolean tieneClaseEnPaqueteDefault = false;

		for (String archivo : mod.archivos()) {
			String norm = archivo.replace('\\', '/');

			if (norm.endsWith(".class") && !norm.contains("/")) {
				tieneClaseEnPaqueteDefault = true;
				break;
			}
		}

		boolean estaEnServerProperties = jarEstaEnServerProperties(mod);

		return estaEnServerProperties && tieneClaseEnPaqueteDefault;
	}

	@Override
	public boolean cargadorEsActivado() {

		// hMod tiene clases propias como PluginLoader, Plugin y Server.
		// Estas suelen estar en el paquete default.
		return Cargador.claseExiste("PluginLoader") || Cargador.claseExiste("Plugin") || Cargador.claseExiste("Server");
	}

	@Override
	public String id() {
		return "hmod";
	}

	/**
	 * Extrae los nombres de plugins desde server.properties.
	 *
	 * Ejemplo:
	 *
	 * plugins=PluginUno,PluginDos
	 */
	public static List<String> parsearPluginsHMod(String texto) throws IOException {
		List<String> salida = new ArrayList<>();

		String plugins = extraerPropiedadSimple(texto, "plugins");

		if (plugins == null) {
			return salida;
		}

		for (String parte : plugins.split(",")) {
			String nombre = limpiarValorProperties(parte);

			if (!nombre.isEmpty() && !salida.contains(nombre)) {
				salida.add(nombre);
			}
		}

		return salida;
	}

	/**
	 * Devuelve true si server.properties menciona este archivo jar como plugin de
	 * hMod.
	 *
	 * Si el jar se llama CrashDetectorHMod.jar, server.properties debe tener:
	 *
	 * plugins=CrashDetectorHMod
	 */
	public static boolean jarEstaEnServerProperties(String textoServerProperties, String nombreArchivoJar)
			throws IOException {

		if (nombreArchivoJar == null || nombreArchivoJar.isEmpty()) {
			return false;
		}

		String nombreBase = nombreArchivoJar;

		if (nombreBase.toLowerCase(Locale.ROOT).endsWith(".jar")) {
			nombreBase = nombreBase.substring(0, nombreBase.length() - 4);
		}

		for (String plugin : parsearPluginsHMod(textoServerProperties)) {
			if (plugin.equalsIgnoreCase(nombreBase)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Verifica si el archivo parece ser un plugin de hMod usando server.properties.
	 *
	 * Requisitos:
	 *
	 * 1. El jar debe estar listado en server.properties. 2. El jar debe contener al
	 * menos una clase en el paquete default.
	 */
	public static boolean esPluginHModSegunServerProperties(ArchivoDeMod mod, String textoServerProperties,
			String nombreArchivoJar) throws IOException {

		if (!jarEstaEnServerProperties(textoServerProperties, nombreArchivoJar)) {
			return false;
		}

		for (String archivo : mod.archivos()) {
			String norm = archivo.replace('\\', '/');

			if (norm.endsWith(".class") && !norm.contains("/")) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Extrae una propiedad sencilla del tipo:
	 *
	 * clave=valor
	 *
	 * Tambien acepta:
	 *
	 * clave: valor
	 */
	private static String extraerPropiedadSimple(String texto, String campo) {

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
	 * Limpia un valor sencillo de properties:
	 *
	 * - quita espacios - elimina comentarios finales - quita comillas si envuelven
	 * todo el valor
	 */
	private static String limpiarValorProperties(String valor) {

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

	private static boolean jarEstaEnServerProperties(ArchivoDeMod mod) {

		try {
			java.io.File serverProperties = new java.io.File("server.properties");

			if (!serverProperties.isFile()) {
				return false;
			}

			String texto = new String(java.nio.file.Files.readAllBytes(serverProperties.toPath()),
					java.nio.charset.StandardCharsets.UTF_8);

			List<String> plugins = parsearPluginsHMod(texto);

			for (String nombreJar : mod.nombre()) {
				if (nombreJar == null || nombreJar.isEmpty()) {
					continue;
				}

				String nombreBase = nombreJar.trim();

				if (nombreBase.toLowerCase(Locale.ROOT).endsWith(".jar")) {
					nombreBase = nombreBase.substring(0, nombreBase.length() - 4);
				}

				for (String plugin : plugins) {
					if (plugin.equalsIgnoreCase(nombreBase)) {
						return true;
					}
				}
			}

			return false;

		} catch (Throwable t) {
			return false;
		}
	}

}