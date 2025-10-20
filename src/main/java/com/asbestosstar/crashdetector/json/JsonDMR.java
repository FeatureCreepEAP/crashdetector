package com.asbestosstar.crashdetector.json;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

/**
 * Motor basado en JBoss DMR Estilo de ModelNode con nombres en español
 */
public class JsonDMR implements Json.Motor {

	@Override
	public String nombre() {
		return "jboss-dmr";
	}

	@Override
	public Json.Nodo leer(String json) {
		ModelNode nodo = ModelNode.fromJSONString(json);
		return new Json.Nodo(nodo, this);
	}

	@Override
	public Json.Nodo crearObjeto() {
		ModelNode obj = new ModelNode();
		obj.setEmptyObject();
		return new Json.Nodo(obj, this);
	}

	@Override
	public String escribir(Json.Nodo nodo) {
		return ((ModelNode) nodo.interno).toJSONString(true);
	}

	@Override
	public boolean esObjeto(Json.Nodo nodo) {
		ModelNode n = (ModelNode) nodo.interno;
		return n.getType() == ModelType.OBJECT || n.getType() == ModelType.UNDEFINED;
	}

	@Override
	public boolean esArreglo(Json.Nodo nodo) {
		ModelNode n = (ModelNode) nodo.interno;
		return n.getType() == ModelType.LIST;
	}

	@Override
	public Json.Nodo obtener(Json.Nodo actual, String nombre) {
		ModelNode n = (ModelNode) actual.interno;
		if (n.getType() == ModelType.UNDEFINED)
			n.setEmptyObject();
		ModelNode hijo = n.get(nombre);
		return new Json.Nodo(hijo, this, n, nombre, null);
	}

	@Override
	public Json.Nodo poner(Json.Nodo actual, Object valor) {
		ModelNode n = (ModelNode) actual.interno;

		if (valor == null) {
			n.clear();
			return actual;
		}
		if (valor instanceof Json.Nodo) {
			n.set((ModelNode) ((Json.Nodo) valor).interno);
		} else if (valor instanceof String) {
			n.set((String) valor);
		} else if (valor instanceof Integer) {
			n.set(((Integer) valor).intValue());
		} else if (valor instanceof Long) {
			n.set(((Long) valor).longValue());
		} else if (valor instanceof Boolean) {
			n.set(((Boolean) valor).booleanValue());
		} else if (valor instanceof Double) {
			n.set(((Double) valor).doubleValue());
		} else if (valor instanceof Float) {
			n.set(((Float) valor).floatValue());
		} else if (valor instanceof Short) {
			n.set(((Short) valor).shortValue());
		} else if (valor instanceof Number) {
			// conversion generica para otros numeros
			n.set(((Number) valor).doubleValue());
		} else {
			throw new IllegalArgumentException("Tipo no soportado por DMR");
		}
		return actual;
	}

	@Override
	public Json.Nodo agregar(Json.Nodo actual, Object valor) {
		ModelNode n = (ModelNode) actual.interno;
		if (n.getType() == ModelType.UNDEFINED)
			n.setEmptyList();
		if (n.getType() != ModelType.LIST)
			throw new IllegalStateException("No es arreglo");

		int idx = n.asList().size();
		ModelNode nuevo = n.add();

		if (valor == null) {
			nuevo.clear();
		} else if (valor instanceof Json.Nodo) {
			nuevo.set((ModelNode) ((Json.Nodo) valor).interno);
		} else if (valor instanceof String) {
			nuevo.set((String) valor);
		} else if (valor instanceof Integer) {
			nuevo.set(((Integer) valor).intValue());
		} else if (valor instanceof Long) {
			nuevo.set(((Long) valor).longValue());
		} else if (valor instanceof Boolean) {
			nuevo.set(((Boolean) valor).booleanValue());
		} else if (valor instanceof Double) {
			nuevo.set(((Double) valor).doubleValue());
		} else if (valor instanceof Float) {
			nuevo.set(((Float) valor).floatValue());
		} else if (valor instanceof Short) {
			nuevo.set(((Short) valor).shortValue());
		} else if (valor instanceof Number) {
			nuevo.set(((Number) valor).doubleValue());
		} else {
			throw new IllegalArgumentException("Tipo no soportado por DMR");
		}
		return new Json.Nodo(nuevo, this, n, null, idx);
	}

	@Override
	public Json.Nodo agregarNodo(Json.Nodo actual, Json.Nodo valorNodo) {
		return agregar(actual, valorNodo);
	}

	@Override
	public int tamano(Json.Nodo arreglo) {
		ModelNode n = (ModelNode) arreglo.interno;
		if (n.getType() != ModelType.LIST)
			return 0;
		return n.asList().size();
	}

	@Override
	public Json.Nodo en(Json.Nodo arreglo, int indice) {
		ModelNode n = (ModelNode) arreglo.interno;
		if (n.asType() != ModelType.LIST)
			throw new IllegalStateException("No es arreglo");
		return new Json.Nodo(n.get(indice), this, n, null, indice);
	}

	@Override
	public String comoCadena(Json.Nodo nodo) {
		return ((ModelNode) nodo.interno).asString();
	}

	@Override
	public int comoEntero(Json.Nodo nodo) {
		return ((ModelNode) nodo.interno).asInt();
	}

	@Override
	public long comoLargo(Json.Nodo nodo) {
		return ((ModelNode) nodo.interno).asLong();
	}

	@Override
	public boolean comoBooleano(Json.Nodo nodo) {
		return ((ModelNode) nodo.interno).asBoolean();
	}

	@Override
	public double comoDouble(Json.Nodo nodo) {
		return ((ModelNode) nodo.interno).asDouble();
	}
}
