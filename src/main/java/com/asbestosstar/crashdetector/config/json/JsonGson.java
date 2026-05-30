package com.asbestosstar.crashdetector.config.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

/**
 * Motor basado en Gson. Nodos con contexto para operar como DMR.
 */
public class JsonGson implements Json.Motor {

	@Override
	public String nombre() {
		return "gson";
	}

	@Override
	public Json.Nodo leer(String json) {
		JsonElement elem = JsonParser.parseString(json);
		return new Json.Nodo(elem, this);
	}

	@Override
	public Json.Nodo crearObjeto() {
		return new Json.Nodo(new JsonObject(), this);
	}

	@Override
	public String escribir(Json.Nodo nodo) {
		return new GsonBuilder().disableHtmlEscaping().create().toJson((JsonElement) nodo.interno);
	}

	@Override
	public boolean esObjeto(Json.Nodo nodo) {
		JsonElement e = (JsonElement) nodo.interno;
		return e.isJsonObject() || e.isJsonNull();
	}

	@Override
	public boolean esArreglo(Json.Nodo nodo) {
		return ((JsonElement) nodo.interno).isJsonArray();
	}

	@Override
	public List<String> claves(Json.Nodo objeto) {
		JsonElement base = (JsonElement) objeto.interno;
		if (base.isJsonNull()) {
			return new ArrayList<>();
		}
		if (!base.isJsonObject()) {
			return new ArrayList<>();
		}
		JsonObject obj = base.getAsJsonObject();
		return new ArrayList<>(obj.keySet());
	}

	@Override
	public Json.Nodo obtener(Json.Nodo actual, String nombre) {
		JsonElement base = (JsonElement) actual.interno;

		if (base.isJsonNull()) {
			JsonObject nuevo = new JsonObject();
			if (actual.padre instanceof JsonObject && actual.claveEnPadre != null) {
				((JsonObject) actual.padre).add(actual.claveEnPadre, nuevo);
			}
			actual.interno = nuevo;
			base = nuevo;
		}

		if (!base.isJsonObject()) {
			throw new IllegalStateException("No es objeto");
		}

		JsonObject obj = base.getAsJsonObject();
		JsonElement hijo = obj.get(nombre);
		if (hijo == null) {
			hijo = JsonNull.INSTANCE;
			obj.add(nombre, hijo);
		}
		return new Json.Nodo(hijo, this, obj, nombre, null);
	}

	@Override
	public boolean eliminar(Json.Nodo actual, String nombre) {
		JsonElement base = (JsonElement) actual.interno;

		if (base.isJsonNull()) {
			// Un objeto nulo no tiene propiedades para eliminar
			return false;
		}
		if (!base.isJsonObject()) {
			throw new IllegalStateException("No es objeto");
		}
		JsonObject obj = base.getAsJsonObject();
		boolean existia = obj.has(nombre);
		obj.remove(nombre);
		return existia;
	}

	@Override
	public Json.Nodo poner(Json.Nodo actual, Object valor) {
		if (actual.padre instanceof JsonObject && actual.claveEnPadre != null) {
			JsonElement e = aElemento(valor);
			((JsonObject) actual.padre).add(actual.claveEnPadre, e);
			actual.interno = e;
			return actual;
		}

		if (actual.padre instanceof JsonArray && actual.indiceEnPadre != null) {
			JsonArray pa = (JsonArray) actual.padre;
			JsonElement e = aElemento(valor);
			int idx = actual.indiceEnPadre.intValue();
			while (pa.size() <= idx)
				pa.add(JsonNull.INSTANCE);
			pa.set(idx, e);
			actual.interno = e;
			return actual;
		}

		if (actual.interno instanceof JsonObject || actual.interno instanceof JsonArray
				|| actual.interno instanceof JsonNull) {
			JsonElement e = aElemento(valor);
			actual.interno = e;
			return actual;
		}

		JsonElement e = aElemento(valor);
		actual.interno = e;
		return actual;
	}

	@Override
	public Json.Nodo agregar(Json.Nodo actual, Object valor) {
		JsonElement base = (JsonElement) actual.interno;

		if (base.isJsonNull() && actual.padre instanceof JsonObject && actual.claveEnPadre != null) {
			JsonArray nuevo = new JsonArray();
			((JsonObject) actual.padre).add(actual.claveEnPadre, nuevo);
			actual.interno = nuevo;
			base = nuevo;
		}

		if (!base.isJsonArray()) {
			if (base.isJsonNull()) {
				JsonArray arr = new JsonArray();
				actual.interno = arr;
				base = arr;
			} else {
				throw new IllegalStateException("No es arreglo");
			}
		}

		JsonArray arr = base.getAsJsonArray();
		JsonElement e = aElemento(valor);
		arr.add(e);
		return new Json.Nodo(e, this, arr, null, arr.size() - 1);
	}

	@Override
	public Json.Nodo agregarNodo(Json.Nodo actual, Json.Nodo valorNodo) {
		return agregar(actual, valorNodo);
	}

	@Override
	public int tamano(Json.Nodo arreglo) {
		JsonElement base = (JsonElement) arreglo.interno;
		if (!base.isJsonArray())
			return 0;
		return base.getAsJsonArray().size();
	}

	@Override
	public Json.Nodo en(Json.Nodo arreglo, int indice) {
		JsonElement base = (JsonElement) arreglo.interno;
		if (!base.isJsonArray())
			throw new IllegalStateException("No es arreglo");
		JsonArray arr = base.getAsJsonArray();
		JsonElement e = arr.get(indice);
		return new Json.Nodo(e, this, arr, null, indice);
	}

	@Override
	public String comoCadena(Json.Nodo nodo) {
		JsonElement e = (JsonElement) nodo.interno;
		if (e.isJsonNull())
			return null;
		if (e.isJsonPrimitive())
			return e.getAsString();
		return e.toString();
	}

	@Override
	public int comoEntero(Json.Nodo nodo) {
		JsonElement e = (JsonElement) nodo.interno;
		if (e.isJsonPrimitive())
			return e.getAsInt();
		throw new IllegalStateException("No es numero entero");
	}

	@Override
	public long comoLargo(Json.Nodo nodo) {
		JsonElement e = (JsonElement) nodo.interno;
		if (e.isJsonPrimitive())
			return e.getAsLong();
		throw new IllegalStateException("No es numero");
	}

	@Override
	public boolean comoBooleano(Json.Nodo nodo) {
		JsonElement e = (JsonElement) nodo.interno;
		if (e.isJsonPrimitive())
			return e.getAsBoolean();
		throw new IllegalStateException("No es booleano");
	}

	@Override
	public double comoDouble(Json.Nodo nodo) {
		JsonElement e = (JsonElement) nodo.interno;
		if (e.isJsonPrimitive())
			return e.getAsDouble();
		throw new IllegalStateException("No es numero");
	}

	private JsonElement aElemento(Object v) {
		if (v == null)
			return JsonNull.INSTANCE;
		if (v instanceof Json.Nodo)
			return (JsonElement) ((Json.Nodo) v).interno;
		if (v instanceof String)
			return new JsonPrimitive((String) v);
		if (v instanceof Integer)
			return new JsonPrimitive(((Integer) v).intValue());
		if (v instanceof Long)
			return new JsonPrimitive(((Long) v).longValue());
		if (v instanceof Boolean)
			return new JsonPrimitive(((Boolean) v).booleanValue());
		if (v instanceof Double)
			return new JsonPrimitive(((Double) v).doubleValue());
		throw new IllegalArgumentException("Tipo no soportado por Gson");
	}
}
