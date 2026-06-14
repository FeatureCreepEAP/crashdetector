package com.asbestosstar.crashdetector.cargador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

/**
 * Cargador para mods de Risugami's ModLoader.
 */
public class CargadorRisugami implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {

		// Risugami ModLoader carga clases que empiezan con mod_
		// y que extienden BaseMod. Como no leemos bytecode aqui,
		// usamos la regla fuerte del nombre de clase.
		for (String archivo : mod.archivos()) {

			if (archivo.endsWith(".class")) {
				String nombreClase = archivo;

				int slash = nombreClase.lastIndexOf('/');
				if (slash >= 0) {
					nombreClase = nombreClase.substring(slash + 1);
				}

				if (nombreClase.startsWith("mod_") && nombreClase.endsWith(".class")) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean cargadorEsActivado() {

		return Cargador.claseExiste("ModLoader") || Cargador.claseExiste("BaseMod") || Cargador.claseExiste("MLProp");
	}

	@Override
	public String id() {
		return "risugami";
	}

	/**
	 * Extrae IDs/nombres de mod desde clases mod_*.class.
	 */
	public static List<String> parsearIdModRisugami(ArchivoDeMod mod) throws IOException {
		List<String> salida = new ArrayList<>();

		for (String archivo : mod.archivos()) {
			String norm = archivo.replace('\\', '/');

			if (!norm.toLowerCase(Locale.ROOT).endsWith(".class")) {
				continue;
			}

			String nombreClase = norm;

			int slash = nombreClase.lastIndexOf('/');
			if (slash >= 0) {
				nombreClase = nombreClase.substring(slash + 1);
			}

			if (!nombreClase.startsWith("mod_")) {
				continue;
			}

			nombreClase = nombreClase.substring(0, nombreClase.length() - ".class".length());

			if (!nombreClase.isEmpty() && !salida.contains(nombreClase)) {
				salida.add(nombreClase);
			}
		}

		return salida;
	}

	/**
	 * Risugami ModLoader obtiene la version llamando getVersion() en el mod. Sin
	 * ejecutar la clase, no hay una forma segura de extraerla aqui.
	 */
	public static String parsearVersionModRisugami(String texto) {
		return "";
	}

	/**
	 * Para Risugami ModLoader, la clase principal normalmente es la clase mod_*.
	 */
	public static String parsearMainModRisugami(ArchivoDeMod mod) {
		for (String archivo : mod.archivos()) {
			String norm = archivo.replace('\\', '/');

			if (!norm.toLowerCase(Locale.ROOT).endsWith(".class")) {
				continue;
			}

			String nombreClase = norm.substring(0, norm.length() - ".class".length()).replace('/', '.');

			int ultimoPunto = nombreClase.lastIndexOf('.');
			String simple = ultimoPunto >= 0 ? nombreClase.substring(ultimoPunto + 1) : nombreClase;

			if (simple.startsWith("mod_")) {
				return nombreClase;
			}
		}

		return "";
	}
}