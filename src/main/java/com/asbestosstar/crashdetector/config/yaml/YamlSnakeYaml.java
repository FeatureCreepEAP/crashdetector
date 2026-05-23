package com.asbestosstar.crashdetector.config.yaml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * Motor YAML basado en SnakeYAML.
 */
public class YamlSnakeYaml implements com.asbestosstar.crashdetector.config.yaml.Yaml.Motor {

	private final org.yaml.snakeyaml.Yaml yaml;

	public YamlSnakeYaml() {
		DumperOptions opciones = new DumperOptions();
		opciones.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		opciones.setPrettyFlow(true);
		this.yaml = new org.yaml.snakeyaml.Yaml(opciones);
	}

	@Override
	public String nombre() {
		return "snakeyaml";
	}

	@Override
	public com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo leer(String texto) {
		Object obj = yaml.load(texto);
		if (obj == null)
			obj = new LinkedHashMap<String, Object>();
		return new com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo(obj, this);
	}

	@Override
	public com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo crearObjeto() {
		return new com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo(new LinkedHashMap<String, Object>(), this);
	}

	@Override
	public com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo crearArreglo() {
		return new com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo(new ArrayList<Object>(), this);
	}

	@Override
	public String escribir(com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo nodo) {
		return yaml.dump(nodo.interno);
	}

	@Override
	public boolean esObjeto(com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo nodo) {
		return nodo.interno instanceof Map;
	}

	@Override
	public boolean esArreglo(com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo nodo) {
		return nodo.interno instanceof List;
	}

	@Override
	public List<String> claves(com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo objeto) {
		List<String> salida = new ArrayList<String>();
		if (!(objeto.interno instanceof Map))
			return salida;

		Map<?, ?> mapa = (Map<?, ?>) objeto.interno;
		for (Object clave : mapa.keySet())
			salida.add(String.valueOf(clave));

		return salida;
	}

	@Override
	public com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo obtener(
			com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo actual, String nombre) {
		if (actual.interno == null) {
			actual.interno = new LinkedHashMap<String, Object>();
			actualizarPadre(actual);
		}

		if (!(actual.interno instanceof Map))
			throw new IllegalStateException("No es objeto");

		Map<String, Object> mapa = (Map<String, Object>) actual.interno;
		Object hijo = mapa.get(nombre);
		return new com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo(hijo, this, mapa, nombre, null);
	}

	@Override
	public boolean eliminar(com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo actual, String nombre) {
		if (!(actual.interno instanceof Map))
			throw new IllegalStateException("No es objeto");

		Map<?, ?> mapa = (Map<?, ?>) actual.interno;
		boolean existia = mapa.containsKey(nombre);
		((Map<?, ?>) actual.interno).remove(nombre);
		return existia;
	}

	@Override
	public com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo poner(
			com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo actual, Object valor) {
		if (valor instanceof com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo)
			valor = ((com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo) valor).interno;

		actual.interno = valor;
		actualizarPadre(actual);
		return actual;
	}

	@Override
	public com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo agregar(
			com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo actual, Object valor) {
		if (valor instanceof com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo)
			valor = ((com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo) valor).interno;

		if (actual.interno == null) {
			actual.interno = new ArrayList<Object>();
			actualizarPadre(actual);
		}

		if (!(actual.interno instanceof List))
			throw new IllegalStateException("No es arreglo");

		List<Object> lista = (List<Object>) actual.interno;
		lista.add(valor);
		return new com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo(valor, this, lista, null,
				Integer.valueOf(lista.size() - 1));
	}

	@Override
	public com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo agregarNodo(
			com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo actual,
			com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo valorNodo) {
		return agregar(actual, valorNodo);
	}

	@Override
	public int tamano(com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo arreglo) {
		if (arreglo.interno instanceof List)
			return ((List<?>) arreglo.interno).size();
		if (arreglo.interno instanceof Map)
			return ((Map<?, ?>) arreglo.interno).size();
		return 0;
	}

	@Override
	public com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo en(
			com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo arreglo, int indice) {
		if (!(arreglo.interno instanceof List))
			throw new IllegalStateException("No es arreglo");

		List<?> lista = (List<?>) arreglo.interno;
		return new com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo(lista.get(indice), this, arreglo.interno, null,
				Integer.valueOf(indice));
	}

	@Override
	public String comoCadena(com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo nodo) {
		if (nodo.interno == null)
			return null;
		return String.valueOf(nodo.interno);
	}

	@Override
	public int comoEntero(com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo nodo) {
		if (nodo.interno instanceof Number)
			return ((Number) nodo.interno).intValue();
		return Integer.parseInt(comoCadena(nodo));
	}

	@Override
	public long comoLargo(com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo nodo) {
		if (nodo.interno instanceof Number)
			return ((Number) nodo.interno).longValue();
		return Long.parseLong(comoCadena(nodo));
	}

	@Override
	public boolean comoBooleano(com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo nodo) {
		if (nodo.interno instanceof Boolean)
			return ((Boolean) nodo.interno).booleanValue();
		return Boolean.parseBoolean(comoCadena(nodo));
	}

	@Override
	public double comoDouble(com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo nodo) {
		if (nodo.interno instanceof Number)
			return ((Number) nodo.interno).doubleValue();
		return Double.parseDouble(comoCadena(nodo));
	}

	private void actualizarPadre(com.asbestosstar.crashdetector.config.yaml.Yaml.Nodo nodo) {
		if (nodo.padre instanceof Map && nodo.claveEnPadre != null) {
			((Map<String, Object>) nodo.padre).put(nodo.claveEnPadre, nodo.interno);
		} else if (nodo.padre instanceof List && nodo.indiceEnPadre != null) {
			((List<Object>) nodo.padre).set(nodo.indiceEnPadre.intValue(), nodo.interno);
		}
	}
}