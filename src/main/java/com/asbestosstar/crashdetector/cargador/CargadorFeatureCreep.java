package com.asbestosstar.crashdetector.cargador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

public class CargadorFeatureCreep implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {
		// TODO Auto-generated method stub
		return true;// FeatureCreep puede leer todos para ahora
	}

	@Override
	public boolean cargadorEsActivado() {
		// TODO Auto-generated method stub
		return Cargador.claseExiste("featurecreep.loader.FCLoaderBasic");
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "featurecreep";
	}

	// TODO crerar una clase specificamente para JBoss Modules
	/**
	 * Extrae el nombre del modulo de un archivo modules.xml de JBoss
	 */
	public static List<String> parsearNombreModuloJBoss(byte[] contenido) throws IOException {
		String xml = new String(contenido, java.nio.charset.StandardCharsets.UTF_8);
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

	// TODO Suporte la parser en FeatureCreep HOI4
	/**
	 * Extrae el nombre del mod de un archivo HOI4 con extension .mod
	 * https://hoi4.paradoxwikis.com/Mod_structure
	 */
	public static List<String> parsearNombreModHOI4(byte[] contenido) throws IOException {
		String texto = new String(contenido, java.nio.charset.StandardCharsets.UTF_8);
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

}
