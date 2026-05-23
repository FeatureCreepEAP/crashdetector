package com.asbestosstar.crashdetector.config.ini;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;

/**
 * Motor INI basado en Apache Commons Configuration 2.
 */
public class IniApacheCommonsConfig implements Ini.Motor {

	@Override
	public String nombre() {
		return "apache-commons-configuration";
	}

	@Override
	public Ini.Nodo leer(String texto) {
		try {
			INIConfiguration cfg = new INIConfiguration();
			cfg.read(new StringReader(texto));
			return new Ini.Nodo(cfg, this);
		} catch (Exception e) {
			throw new RuntimeException("No se pudo leer INI con Apache Commons Configuration", e);
		}
	}

	@Override
	public Ini.Nodo crear() {
		return new Ini.Nodo(new INIConfiguration(), this);
	}

	@Override
	public String escribir(Ini.Nodo nodo) {
		try {
			INIConfiguration cfg = raiz(nodo);
			StringWriter sw = new StringWriter();
			cfg.write(sw);
			return sw.toString();
		} catch (Exception e) {
			throw new RuntimeException("No se pudo escribir INI con Apache Commons Configuration", e);
		}
	}

	@Override
	public List<String> secciones(Ini.Nodo raiz) {
		return new ArrayList<String>(raiz(raiz).getSections());
	}

	@Override
	public Ini.Nodo obtenerSeccion(Ini.Nodo raiz, String nombre) {
		INIConfiguration cfg = raiz(raiz);
		SubnodeConfiguration sec = cfg.getSection(nombre);
		return new Ini.Nodo(sec, this, cfg, nombre, null);
	}

	@Override
	public List<String> claves(Ini.Nodo seccion) {
		List<String> lista = new ArrayList<String>();

		if (!(seccion.interno instanceof SubnodeConfiguration))
			return lista;

		SubnodeConfiguration sec = (SubnodeConfiguration) seccion.interno;
		Iterator<String> it = sec.getKeys();

		while (it.hasNext())
			lista.add(it.next());

		return lista;
	}

	@Override
	public Ini.Nodo obtener(Ini.Nodo seccion, String clave) {
		SubnodeConfiguration sec = (SubnodeConfiguration) seccion.interno;
		Object valor = sec.getProperty(clave);
		return new Ini.Nodo(valor, this, seccion.interno, seccion.seccion, clave);
	}

	@Override
	public Ini.Nodo poner(Ini.Nodo actual, String valor) {
		if (actual.padre instanceof SubnodeConfiguration) {
			((SubnodeConfiguration) actual.padre).setProperty(actual.clave, valor);
			actual.interno = valor;
			return actual;
		}

		throw new IllegalStateException("Solo se puede poner valor sobre una clave INI");
	}

	@Override
	public boolean eliminar(Ini.Nodo actual, String clave) {
		if (!(actual.interno instanceof SubnodeConfiguration))
			throw new IllegalStateException("No es seccion INI");

		SubnodeConfiguration sec = (SubnodeConfiguration) actual.interno;
		boolean existia = sec.containsKey(clave);
		sec.clearProperty(clave);
		return existia;
	}

	@Override
	public String comoCadena(Ini.Nodo nodo) {
		return nodo.interno == null ? null : String.valueOf(nodo.interno);
	}

	private INIConfiguration raiz(Ini.Nodo nodo) {
		if (nodo.interno instanceof INIConfiguration)
			return (INIConfiguration) nodo.interno;

		if (nodo.padre instanceof INIConfiguration)
			return (INIConfiguration) nodo.padre;

		throw new IllegalStateException("Nodo INI sin raiz");
	}
}