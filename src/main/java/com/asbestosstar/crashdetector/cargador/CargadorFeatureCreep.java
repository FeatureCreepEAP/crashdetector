package com.asbestosstar.crashdetector.cargador;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

/**
 * Cargador de FeatureCreep.
 *
 * Soporta: - Loader clasico de FeatureCreep - FlatLoader de FeatureCreep -
 * Metadata de JBoss modules.xml - Metadata de HOI4 .mod - Metadata de FlatMods
 * en fcflat.properties
 */
public class CargadorFeatureCreep implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {

		// FeatureCreep historicamente puede consumir muchos formatos,
		// pero ahora tambien detectamos de forma explicita FlatMods.
//		for (String archivo : mod.archivos()) {
//
//
//			// FlatMods de FeatureCreep
//			if (archivo.equals("fcflat.properties")) {
//				return true;
//			}
//
//			// JBoss modules
//			if (archivo.endsWith("modules.xml")) {
//				return true;
//			}
//
//			// Mods tipo HOI4
//			if (archivo.endsWith(".mod")) {
//				return true;
//			}
//		}

		// Mantener compatibilidad anterior:
		// FeatureCreep puede leer casi cualquier mod/archivo por ahora.
		return true;
	}

	@Override
	public boolean cargadorEsActivado() {

		// Loader clasico
		boolean loaderClasico = Cargador.claseExiste("featurecreep.loader.FCLoaderBasic");

		// FlatLoader
		boolean flatLoader = Cargador.claseExiste("featurecreep.loader.flat.FCLoaderFlat");

		return loaderClasico || flatLoader;
	}

	@Override
	public String id() {
		return "featurecreep";
	}

	/**
	 * Extrae el nombre del modulo de un archivo modules.xml de JBoss.
	 */
	public static List<String> parsearNombreModuloJBoss(byte[] contenido) throws IOException {
		String xml = new String(contenido, StandardCharsets.UTF_8);
		String clave = "name=\"";
		int pos = xml.indexOf(clave);
		if (pos < 0)
			return java.util.Collections.emptyList();
		int inicio = pos + clave.length();
		int fin = xml.indexOf("\"", inicio);
		if (fin < 0)
			return java.util.Collections.emptyList();
		String nombre = xml.substring(inicio, fin).trim();
		if (nombre.isEmpty())
			return java.util.Collections.emptyList();
		return java.util.Collections.singletonList(nombre);
	}

	/**
	 * Extrae el nombre del mod de un archivo HOI4 con extension .mod.
	 * https://hoi4.paradoxwikis.com/Mod_structure
	 */
	public static List<String> parsearNombreModHOI4(byte[] contenido) throws IOException {
		String texto = new String(contenido, StandardCharsets.UTF_8);
		String clave = "name=\"";
		int pos = texto.indexOf(clave);
		if (pos < 0)
			return java.util.Collections.emptyList();
		int inicio = pos + clave.length();
		int fin = texto.indexOf("\"", inicio);
		if (fin < 0)
			return java.util.Collections.emptyList();
		String nombre = texto.substring(inicio, fin).trim();
		if (nombre.isEmpty())
			return java.util.Collections.emptyList();
		return java.util.Collections.singletonList(nombre);
	}

	/**
	 * Extrae la version (slot) de un archivo modules.xml de JBoss. Devuelve cadena
	 * vacia si no existe.
	 */
	public static String parsearVersionModuloJBoss(byte[] contenido) throws IOException {

		String xml = new String(contenido, StandardCharsets.UTF_8);

		String clave = "slot=\"";
		int pos = xml.indexOf(clave);

		if (pos < 0)
			return "";

		int inicio = pos + clave.length();
		int fin = xml.indexOf("\"", inicio);

		if (fin < 0)
			return "";

		String version = xml.substring(inicio, fin).trim();

		return version.isEmpty() ? "" : version;
	}

	/**
	 * Extrae la version de un archivo HOI4 (.mod). Devuelve cadena vacia si no
	 * existe.
	 */
	public static String parsearVersionModHOI4(byte[] contenido) throws IOException {

		String texto = new String(contenido, StandardCharsets.UTF_8);

		String clave = "version=\"";
		int pos = texto.indexOf(clave);

		if (pos < 0)
			return "";

		int inicio = pos + clave.length();
		int fin = texto.indexOf("\"", inicio);

		if (fin < 0)
			return "";

		String version = texto.substring(inicio, fin).trim();

		return version.isEmpty() ? "" : version;
	}

	/**
	 * Extrae el modid de un archivo fcflat.properties.
	 *
	 * Segun FlatModMetadata, la clave usada es "modid".
	 */
	public static List<String> parsearIdModFlat(byte[] contenido) throws IOException {

		Properties props = new Properties();

		try (java.io.ByteArrayInputStream in = new java.io.ByteArrayInputStream(contenido)) {
			props.load(in);
		}

		String modid = props.getProperty("modid", "").trim();

		if (modid.isEmpty()) {
			return java.util.Collections.emptyList();
		}

		List<String> salida = new ArrayList<>();
		salida.add(modid);
		return salida;
	}

	/**
	 * Extrae la version de un archivo fcflat.properties.
	 *
	 * FlatModMetadata no expone campo de version, asi que por compatibilidad se
	 * devuelve vacio cuando no exista una clave util.
	 */
	public static String parsearVersionModFlat(byte[] contenido) throws IOException {

		Properties props = new Properties();

		try (java.io.ByteArrayInputStream in = new java.io.ByteArrayInputStream(contenido)) {
			props.load(in);
		}

		// Por si en el futuro algun flat mod la incluye
		String version = props.getProperty("version", "").trim();

		return version;
	}

	/**
	 * Alias mas descriptivo por si quieres mantener separado el nombre "FlatMod".
	 */
	public static List<String> parsearIdFlatMod(byte[] contenido) throws IOException {
		return parsearIdModFlat(contenido);
	}

	/**
	 * Alias mas descriptivo por si quieres mantener separado el nombre "FlatMod".
	 */
	public static String parsearVersionFlatMod(byte[] contenido) throws IOException {
		return parsearVersionModFlat(contenido);
	}

	@Override
	public boolean suporteModsDeCarpetas() {
		return true;
	}

}