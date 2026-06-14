package com.asbestosstar.crashdetector.cargador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

/**
 * Cargador para mods de Meddle.
 *
 * Meddle usa principalmente: - MANIFEST.MF con TweakClass o TransformerClass -
 * Anotacion @MeddleMod en la clase principal del tweak/transformer
 */
public class CargadorMeddle implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {// TODO annotacion

		boolean tieneManifest = false;
		boolean tieneAnotacion = false;

		for (String archivo : mod.archivos()) {
			String norm = archivo.replace('\\', '/');
			String lower = norm.toLowerCase(Locale.ROOT);

			if (lower.equals("meta-inf/manifest.mf")) {
				tieneManifest = true;
				return true;
			}

			// Si existe la propia anotacion de Meddle en el jar, es una pista fuerte
			if (lower.equals("net/fybertech/meddle/meddlemod.class")) {
				tieneAnotacion = true;
			}

			// Por si se empaquetan clases conocidas del loader
			if (lower.startsWith("net/fybertech/meddle/")) {
				tieneAnotacion = true;
			}
		}

		return tieneManifest || tieneAnotacion;
	}

	@Override
	public boolean cargadorEsActivado() {
		return Cargador.claseExiste("net.fybertech.meddle.Meddle")
				|| Cargador.claseExiste("net.fybertech.meddle.MeddleMod");
	}

	@Override
	public String id() {
		return "meddle";
	}

	/**
	 * Devuelve true si el manifest parece pertenecer a un mod Meddle.
	 */
	public static boolean manifestEsDeMeddle(Manifest man) {
		if (man == null) {
			return false;
		}

		Attributes attr = man.getMainAttributes();

		if (attr == null) {
			return false;
		}

		String tweak = attr.getValue("TweakClass");
		String transformer = attr.getValue("TransformerClass");

		return tieneTexto(tweak) || tieneTexto(transformer);
	}

	/**
	 * Extrae nombres utiles desde un MANIFEST.MF de Meddle.
	 *
	 * Como Meddle no guarda el id real del mod en el manifest, aqui devolvemos las
	 * clases principales declaradas: - TweakClass - TransformerClass
	 */
	public static List<String> parsearNombresManifestMeddle(Manifest man) {
		List<String> salida = new ArrayList<>();

		if (man == null) {
			return salida;
		}

		Attributes attr = man.getMainAttributes();

		if (attr == null) {
			return salida;
		}

		String tweak = attr.getValue("TweakClass");
		String transformer = attr.getValue("TransformerClass");

		if (tieneTexto(tweak)) {
			agregarSinDuplicado(salida, tweak.trim());
		}

		if (tieneTexto(transformer)) {
			agregarSinDuplicado(salida, transformer.trim());
		}

		return salida;
	}

	/**
	 * El manifest de Meddle no contiene el id real del mod. Ese id vive en la
	 * anotacion @MeddleMod.
	 */
	public static List<String> parsearIdManifestMeddle(Manifest man) {
		return new ArrayList<>();
	}

	/**
	 * El manifest de Meddle tampoco contiene la version real del mod.
	 */
	public static String parsearVersionManifestMeddle(Manifest man) {
		return "";
	}

	/**
	 * Extrae el TweakClass desde un MANIFEST.MF de Meddle.
	 */
	public static String parsearTweakClassManifestMeddle(Manifest man) {
		if (man == null) {
			return "";
		}

		Attributes attr = man.getMainAttributes();

		if (attr == null) {
			return "";
		}

		String tweak = attr.getValue("TweakClass");

		if (tweak == null) {
			return "";
		}

		tweak = tweak.trim();
		return tweak.isEmpty() ? "" : tweak;
	}

	/**
	 * Extrae el TransformerClass desde un MANIFEST.MF de Meddle.
	 */
	public static String parsearTransformerClassManifestMeddle(Manifest man) {
		if (man == null) {
			return "";
		}

		Attributes attr = man.getMainAttributes();

		if (attr == null) {
			return "";
		}

		String transformer = attr.getValue("TransformerClass");

		if (transformer == null) {
			return "";
		}

		transformer = transformer.trim();
		return transformer.isEmpty() ? "" : transformer;
	}

	/**
	 * Compatibilidad: parseo desde bytes.
	 */
	public static String parsearTweakClassManifestMeddle(byte[] contenido) throws IOException {
		return parsearTweakClassManifestMeddle(leerManifest(contenido));
	}

	/**
	 * Compatibilidad: parseo desde bytes.
	 */
	public static String parsearTransformerClassManifestMeddle(byte[] contenido) throws IOException {
		return parsearTransformerClassManifestMeddle(leerManifest(contenido));
	}

	/**
	 * Compatibilidad: parseo desde texto.
	 */
	public static String parsearTweakClassManifestMeddle(String texto) throws IOException {
		return parsearTweakClassManifestMeddle(leerManifest(texto));
	}

	/**
	 * Compatibilidad: parseo desde texto.
	 */
	public static String parsearTransformerClassManifestMeddle(String texto) throws IOException {
		return parsearTransformerClassManifestMeddle(leerManifest(texto));
	}

	/**
	 * Extrae ids desde una anotacion @MeddleMod escrita como texto fuente o texto
	 * decompilado.
	 */
	public static List<String> parsearIdModMeddle(String texto) throws IOException {
		List<String> salida = new ArrayList<>();

		if (texto == null || texto.isEmpty()) {
			return salida;
		}

		Matcher m = Pattern.compile("\\bid\\s*=\\s*\"([^\"]+)\"").matcher(texto);

		while (m.find()) {
			String id = m.group(1).trim();
			if (!id.isEmpty() && !salida.contains(id)) {
				salida.add(id);
			}
		}

		return salida;
	}

	/**
	 * Extrae el nombre desde una anotacion @MeddleMod escrita como texto.
	 */
	public static String parsearNombreModMeddle(String texto) {
		return extraerCampoAnotacion(texto, "name");
	}

	/**
	 * Extrae la version desde una anotacion @MeddleMod escrita como texto.
	 */
	public static String parsearVersionModMeddle(String texto) {
		return extraerCampoAnotacion(texto, "version");
	}

	/**
	 * Extrae el autor desde una anotacion @MeddleMod escrita como texto.
	 */
	public static String parsearAutorModMeddle(String texto) {
		return extraerCampoAnotacion(texto, "author");
	}

	/**
	 * Extrae dependencias desde una anotacion @MeddleMod escrita como texto.
	 */
	public static List<String> parsearDependenciasModMeddle(String texto) {
		List<String> salida = new ArrayList<>();

		if (texto == null || texto.isEmpty()) {
			return salida;
		}

		Matcher bloque = Pattern.compile("\\bdepends\\s*=\\s*\\{(.*?)\\}", Pattern.DOTALL).matcher(texto);

		if (bloque.find()) {
			String dentro = bloque.group(1);

			Matcher item = Pattern.compile("\"([^\"]+)\"").matcher(dentro);
			while (item.find()) {
				String dep = item.group(1).trim();
				if (!dep.isEmpty() && !salida.contains(dep)) {
					salida.add(dep);
				}
			}
		}

		return salida;
	}

	private static Manifest leerManifest(byte[] contenido) throws IOException {
		if (contenido == null || contenido.length == 0) {
			return null;
		}

		try (InputStream in = new ByteArrayInputStream(contenido)) {
			return new Manifest(in);
		}
	}

	private static Manifest leerManifest(String texto) throws IOException {
		if (texto == null || texto.isEmpty()) {
			return null;
		}

		try (InputStream in = new ByteArrayInputStream(texto.getBytes(StandardCharsets.UTF_8))) {
			return new Manifest(in);
		}
	}

	private static String extraerCampoAnotacion(String texto, String campo) {
		if (texto == null || texto.isEmpty()) {
			return "";
		}

		Matcher m = Pattern.compile("\\b" + Pattern.quote(campo) + "\\s*=\\s*\"([^\"]*)\"").matcher(texto);

		if (m.find()) {
			String valor = m.group(1).trim();
			return valor.isEmpty() ? "" : valor;
		}

		return "";
	}

	private static boolean tieneTexto(String s) {
		return s != null && !s.trim().isEmpty();
	}

	private static void agregarSinDuplicado(List<String> salida, String valor) {
		if (valor == null) {
			return;
		}

		String limpio = valor.trim();

		if (!limpio.isEmpty() && !salida.contains(limpio)) {
			salida.add(limpio);
		}
	}

	/**
	 * Utilidad opcional para convertir bytes a texto UTF-8. Solo sirve si el
	 * contenido original ya era texto fuente o texto decompilado.
	 */
	public static String bytesATexto(byte[] contenido) {
		if (contenido == null) {
			return "";
		}
		return new String(contenido, StandardCharsets.UTF_8);
	}
}