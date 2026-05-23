package com.asbestosstar.crashdetector.config.json;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Motor basado en json-simple. Es un respaldo liviano cuando no existe
 * DMR, Gson ni Jackson.
 */
public class JsonSimple implements Json.Motor {

	@Override
	public String nombre() {
		return "json-simple";
	}

	@Override
	public Json.Nodo leer(String json) {
		try {
			Object obj = new JSONParser().parse(json);
			return new Json.Nodo(obj, this);
		} catch (Exception e) {
			throw new IllegalArgumentException("JSON invalido para json-simple", e);
		}
	}

	@Override
	public Json.Nodo crearObjeto() {
		return new Json.Nodo(new JSONObject(), this);
	}

	@Override
	public String escribir(Json.Nodo nodo) {
		Object obj = nodo.interno;

		if (obj == null)
			return "null";

		if (obj instanceof JSONObject)
			return ((JSONObject) obj).toJSONString();

		if (obj instanceof JSONArray)
			return ((JSONArray) obj).toJSONString();

		return obj.toString();
	}

	@Override
	public boolean esObjeto(Json.Nodo nodo) {
		return nodo.interno == null || nodo.interno instanceof JSONObject;
	}

	@Override
	public boolean esArreglo(Json.Nodo nodo) {
		return nodo.interno instanceof JSONArray;
	}

	@Override
	public List<String> claves(Json.Nodo objeto) {
		List<String> claves = new ArrayList<>();

		if (!(objeto.interno instanceof JSONObject))
			return claves;

		JSONObject obj = (JSONObject) objeto.interno;

		for (Object k : obj.keySet()) {
			if (k != null)
				claves.add(String.valueOf(k));
		}

		return claves;
	}

	@Override
	public Json.Nodo obtener(Json.Nodo actual, String nombre) {
		if (actual.interno == null) {
			JSONObject nuevo = new JSONObject();
			actual.interno = nuevo;
			reemplazarEnPadre(actual, nuevo);
		}

		if (!(actual.interno instanceof JSONObject))
			throw new IllegalStateException("No es objeto");

		JSONObject obj = (JSONObject) actual.interno;
		Object hijo = obj.get(nombre);

		if (!obj.containsKey(nombre)) {
			hijo = null;
			obj.put(nombre, hijo);
		}

		return new Json.Nodo(hijo, this, obj, nombre, null);
	}

	@Override
	public boolean eliminar(Json.Nodo actual, String nombre) {
		if (actual.interno == null)
			return false;

		if (!(actual.interno instanceof JSONObject))
			throw new IllegalStateException("No es objeto");

		JSONObject obj = (JSONObject) actual.interno;
		boolean existia = obj.containsKey(nombre);
		obj.remove(nombre);
		return existia;
	}

	@Override
	public Json.Nodo poner(Json.Nodo actual, Object valor) {
		Object v = normalizar(valor);
		actual.interno = v;
		reemplazarEnPadre(actual, v);
		return actual;
	}

	@Override
	public Json.Nodo agregar(Json.Nodo actual, Object valor) {
		if (actual.interno == null) {
			JSONArray arr = new JSONArray();
			actual.interno = arr;
			reemplazarEnPadre(actual, arr);
		}

		if (!(actual.interno instanceof JSONArray))
			throw new IllegalStateException("No es arreglo");

		JSONArray arr = (JSONArray) actual.interno;
		Object v = normalizar(valor);
		arr.add(v);

		return new Json.Nodo(v, this, arr, null, Integer.valueOf(arr.size() - 1));
	}

	@Override
	public Json.Nodo agregarNodo(Json.Nodo actual, Json.Nodo valorNodo) {
		return agregar(actual, valorNodo);
	}

	@Override
	public int tamano(Json.Nodo arreglo) {
		if (!(arreglo.interno instanceof JSONArray))
			return 0;

		return ((JSONArray) arreglo.interno).size();
	}

	@Override
	public Json.Nodo en(Json.Nodo arreglo, int indice) {
		if (!(arreglo.interno instanceof JSONArray))
			throw new IllegalStateException("No es arreglo");

		JSONArray arr = (JSONArray) arreglo.interno;
		return new Json.Nodo(arr.get(indice), this, arr, null, Integer.valueOf(indice));
	}

	@Override
	public String comoCadena(Json.Nodo nodo) {
		if (nodo.interno == null)
			return null;

		if (nodo.interno instanceof JSONObject || nodo.interno instanceof JSONArray)
			return escribir(nodo);

		return String.valueOf(nodo.interno);
	}

	@Override
	public int comoEntero(Json.Nodo nodo) {
		if (nodo.interno instanceof Number)
			return ((Number) nodo.interno).intValue();

		throw new IllegalStateException("No es numero entero");
	}

	@Override
	public long comoLargo(Json.Nodo nodo) {
		if (nodo.interno instanceof Number)
			return ((Number) nodo.interno).longValue();

		throw new IllegalStateException("No es numero");
	}

	@Override
	public boolean comoBooleano(Json.Nodo nodo) {
		if (nodo.interno instanceof Boolean)
			return ((Boolean) nodo.interno).booleanValue();

		throw new IllegalStateException("No es booleano");
	}

	@Override
	public double comoDouble(Json.Nodo nodo) {
		if (nodo.interno instanceof Number)
			return ((Number) nodo.interno).doubleValue();

		throw new IllegalStateException("No es numero");
	}

	private Object normalizar(Object valor) {
		if (valor == null)
			return null;

		if (valor instanceof Json.Nodo)
			return ((Json.Nodo) valor).interno;

		if (valor instanceof String)
			return valor;

		if (valor instanceof Number)
			return valor;

		if (valor instanceof Boolean)
			return valor;

		if (valor instanceof JSONObject)
			return valor;

		if (valor instanceof JSONArray)
			return valor;

		throw new IllegalArgumentException("Tipo no soportado por json-simple");
	}

	@SuppressWarnings("unchecked")
	private void reemplazarEnPadre(Json.Nodo actual, Object nuevo) {
		if (actual.padre instanceof JSONObject && actual.claveEnPadre != null) {
			((JSONObject) actual.padre).put(actual.claveEnPadre, nuevo);
			return;
		}

		if (actual.padre instanceof JSONArray && actual.indiceEnPadre != null) {
			JSONArray arr = (JSONArray) actual.padre;
			int idx = actual.indiceEnPadre.intValue();

			while (arr.size() <= idx)
				arr.add(null);

			arr.set(idx, nuevo);
		}
	}
}