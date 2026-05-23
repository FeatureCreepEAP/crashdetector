package com.asbestosstar.crashdetector.config.ini;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.ini4j.Ini;

/**
 * Motor INI basado en ini4j.
 */
public class IniIni4j implements com.asbestosstar.crashdetector.config.ini.Ini.Motor {

	@Override
	public String nombre() {
		return "ini4j";
	}

	@Override
	public com.asbestosstar.crashdetector.config.ini.Ini.Nodo leer(String texto) {
		try {
			org.ini4j.Ini ini = new org.ini4j.Ini();
			ini.load(new StringReader(texto));
			return new com.asbestosstar.crashdetector.config.ini.Ini.Nodo(ini, this);
		} catch (Exception e) {
			throw new RuntimeException("No se pudo leer INI con ini4j", e);
		}
	}

	@Override
	public com.asbestosstar.crashdetector.config.ini.Ini.Nodo crear() {
		return new com.asbestosstar.crashdetector.config.ini.Ini.Nodo(new org.ini4j.Ini(), this);
	}

	@Override
	public String escribir(com.asbestosstar.crashdetector.config.ini.Ini.Nodo nodo) {
		try {
			org.ini4j.Ini ini = raiz(nodo);
			StringWriter sw = new StringWriter();
			ini.store(sw);
			return sw.toString();
		} catch (Exception e) {
			throw new RuntimeException("No se pudo escribir INI con ini4j", e);
		}
	}

	@Override
	public List<String> secciones(com.asbestosstar.crashdetector.config.ini.Ini.Nodo raiz) {
		return new ArrayList<>(raiz(raiz).keySet());
	}

	@Override
	public com.asbestosstar.crashdetector.config.ini.Ini.Nodo obtenerSeccion(
			com.asbestosstar.crashdetector.config.ini.Ini.Nodo raiz, String nombre) {

		org.ini4j.Ini ini = raiz(raiz);
		Ini.Section sec = ini.get(nombre);
		if (sec == null)
			sec = ini.add(nombre);

		return new com.asbestosstar.crashdetector.config.ini.Ini.Nodo(sec, this, ini, nombre, null);
	}

	@Override
	public List<String> claves(com.asbestosstar.crashdetector.config.ini.Ini.Nodo seccion) {
		if (!(seccion.interno instanceof Ini.Section))
			return new ArrayList<>();
		return new ArrayList<>(((Ini.Section) seccion.interno).keySet());
	}

	@Override
	public com.asbestosstar.crashdetector.config.ini.Ini.Nodo obtener(
			com.asbestosstar.crashdetector.config.ini.Ini.Nodo seccion, String clave) {

		Ini.Section sec = (Ini.Section) seccion.interno;
		Object valor = sec.get(clave);
		return new com.asbestosstar.crashdetector.config.ini.Ini.Nodo(valor, this, sec, seccion.seccion, clave);
	}

	@Override
	public com.asbestosstar.crashdetector.config.ini.Ini.Nodo poner(
			com.asbestosstar.crashdetector.config.ini.Ini.Nodo actual, String valor) {

		if (actual.padre instanceof Ini.Section) {
			((Ini.Section) actual.padre).put(actual.clave, valor);
			actual.interno = valor;
			return actual;
		}

		throw new IllegalStateException("Solo se puede poner valor sobre una clave INI");
	}

	@Override
	public boolean eliminar(com.asbestosstar.crashdetector.config.ini.Ini.Nodo actual, String clave) {

		if (!(actual.interno instanceof Ini.Section))
			throw new IllegalStateException("No es seccion INI");

		Ini.Section sec = (Ini.Section) actual.interno;
		return sec.remove(clave) != null;
	}

	@Override
	public String comoCadena(com.asbestosstar.crashdetector.config.ini.Ini.Nodo nodo) {
		return nodo.interno == null ? null : String.valueOf(nodo.interno);
	}

	private org.ini4j.Ini raiz(com.asbestosstar.crashdetector.config.ini.Ini.Nodo nodo) {
		if (nodo.interno instanceof org.ini4j.Ini)
			return (org.ini4j.Ini) nodo.interno;
		if (nodo.padre instanceof org.ini4j.Ini)
			return (org.ini4j.Ini) nodo.padre;

		throw new IllegalStateException("Nodo INI sin raiz");
	}
}