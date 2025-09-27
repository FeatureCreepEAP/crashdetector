package com.asbestosstar.crashdetector.json;

import com.google.gson.*;

/**
 * Motor basado en Gson
 * Implementa nodos con contexto para operar como DMR
 */
public class JsonGson implements Json.Motor {

    @Override
    public String nombre() { return "gson"; }

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
    public Json.Nodo obtener(Json.Nodo actual, String nombre) {
        // asegura que el actual sea objeto o null representando objeto
        JsonElement base = (JsonElement) actual.interno;

        if (base.isJsonNull()) {
            JsonObject nuevo = new JsonObject();
            // si hay padre y clave se sustituye en el padre
            if (actual.padre instanceof JsonObject po && actual.claveEnPadre != null) {
                po.add(actual.claveEnPadre, nuevo);
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
            // insertar placeholder para poder usar poner mas tarde
            hijo = JsonNull.INSTANCE;
            obj.add(nombre, hijo);
        }
        // devuelve nodo hijo con contexto al padre y la clave
        return new Json.Nodo(hijo, this, obj, nombre, null);
    }

    @Override
    public Json.Nodo poner(Json.Nodo actual, Object valor) {
        // si el nodo representa una propiedad de un objeto
        if (actual.padre instanceof JsonObject po && actual.claveEnPadre != null) {
            JsonElement e = aElemento(valor);
            po.add(actual.claveEnPadre, e);
            actual.interno = e;
            return actual;
        }

        // si el nodo representa un elemento de arreglo
        if (actual.padre instanceof JsonArray pa && actual.indiceEnPadre != null) {
            JsonElement e = aElemento(valor);
            int idx = actual.indiceEnPadre;
            // reemplazo seguro
            while (pa.size() <= idx) pa.add(JsonNull.INSTANCE);
            pa.set(idx, e);
            actual.interno = e;
            return actual;
        }

        // si es nodo raiz
        if (actual.interno instanceof JsonObject || actual.interno instanceof JsonArray || actual.interno instanceof JsonNull) {
            JsonElement e = aElemento(valor);
            actual.interno = e;
            return actual;
        }

        // si es primitivo sin padre no se puede cambiar de tipo con seguridad
        JsonElement e = aElemento(valor);
        actual.interno = e;
        return actual;
    }

    @Override
    public Json.Nodo agregar(Json.Nodo actual, Object valor) {
        JsonElement base = (JsonElement) actual.interno;

        // si actual es una propiedad nula dentro de un objeto convertir a arreglo
        if (base.isJsonNull() && actual.padre instanceof JsonObject po && actual.claveEnPadre != null) {
            JsonArray nuevo = new JsonArray();
            po.add(actual.claveEnPadre, nuevo);
            actual.interno = nuevo;
            base = nuevo;
        }

        if (!base.isJsonArray()) {
            // si no es arreglo y es raiz nula convertir a arreglo
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
        if (!base.isJsonArray()) return 0;
        return base.getAsJsonArray().size();
    }

    @Override
    public Json.Nodo en(Json.Nodo arreglo, int indice) {
        JsonElement base = (JsonElement) arreglo.interno;
        if (!base.isJsonArray()) throw new IllegalStateException("No es arreglo");
        JsonArray arr = base.getAsJsonArray();
        JsonElement e = arr.get(indice);
        return new Json.Nodo(e, this, arr, null, indice);
    }

    @Override
    public String comoCadena(Json.Nodo nodo) {
        JsonElement e = (JsonElement) nodo.interno;
        if (e.isJsonNull()) return null;
        if (e.isJsonPrimitive()) return e.getAsString();
        return e.toString();
    }

    @Override
    public int comoEntero(Json.Nodo nodo) {
        JsonElement e = (JsonElement) nodo.interno;
        if (e.isJsonPrimitive()) return e.getAsInt();
        throw new IllegalStateException("No es numero entero");
    }

    @Override
    public long comoLargo(Json.Nodo nodo) {
        JsonElement e = (JsonElement) nodo.interno;
        if (e.isJsonPrimitive()) return e.getAsLong();
        throw new IllegalStateException("No es numero");
    }

    @Override
    public boolean comoBooleano(Json.Nodo nodo) {
        JsonElement e = (JsonElement) nodo.interno;
        if (e.isJsonPrimitive()) return e.getAsBoolean();
        throw new IllegalStateException("No es booleano");
    }

    @Override
    public double comoDouble(Json.Nodo nodo) {
        JsonElement e = (JsonElement) nodo.interno;
        if (e.isJsonPrimitive()) return e.getAsDouble();
        throw new IllegalStateException("No es numero");
    }

    private JsonElement aElemento(Object v) {
        if (v == null) return JsonNull.INSTANCE;
        if (v instanceof Json.Nodo n) return (JsonElement) n.interno;
        if (v instanceof String s) return new JsonPrimitive(s);
        if (v instanceof Integer i) return new JsonPrimitive(i);
        if (v instanceof Long l) return new JsonPrimitive(l);
        if (v instanceof Boolean b) return new JsonPrimitive(b);
        if (v instanceof Double d) return new JsonPrimitive(d);
        throw new IllegalArgumentException("Tipo no soportado por Gson");
    }
}
