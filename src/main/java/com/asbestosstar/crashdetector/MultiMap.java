package com.asbestosstar.crashdetector;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * MultiMap concurrente que permite asociar múltiples valores a una misma clave.
 * <p>
 * - Seguro para acceso concurrente (multihilo / multicore). - Permite eliminar
 * claves completas o valores individuales.
 *
 * @param <K> Tipo de la clave
 * @param <V> Tipo de los valores
 */
public class MultiMap<K, V> {

	private final Map<K, List<V>> mapa = new ConcurrentHashMap<>();

	/**
	 * Añade un valor a la lista asociada a la clave.
	 *
	 * @param clave Clave (id)
	 * @param valor Valor que se añadirá a la lista
	 */
	public void put(K clave, V valor) {
		mapa.computeIfAbsent(clave, k -> new CopyOnWriteArrayList<>()).add(valor);
	}

	/**
	 * Devuelve la lista de valores asociados a la clave. Si la clave no existe,
	 * devuelve una lista vacía.
	 *
	 * @param clave Clave buscada
	 * @return Lista de valores (nunca null)
	 */
	public List<V> get(K clave) {
		List<V> lista = mapa.get(clave);
		return (lista == null) ? new CopyOnWriteArrayList<>() : new CopyOnWriteArrayList<>(lista);
	}

	/**
	 * Elimina un valor específico de la lista asociada a la clave. Si la lista
	 * queda vacía, elimina la clave.
	 *
	 * @param clave Clave donde se encuentra el valor
	 * @param valor Valor a eliminar
	 * @return true si el valor existía y fue eliminado; false en caso contrario
	 */
	public boolean removeValue(K clave, V valor) {
		List<V> lista = mapa.get(clave);
		if (lista == null) {
			return false;
		}
		boolean eliminado = lista.remove(valor);
		if (lista.isEmpty()) {
			mapa.remove(clave);
		}
		return eliminado;
	}

	/**
	 * Elimina todos los valores asociados a una clave.
	 *
	 * @param clave Clave a eliminar
	 */
	public void remove(K clave) {
		mapa.remove(clave);
	}

	/**
	 * Limpia todo el contenido del multimap.
	 */
	public void clear() {
		mapa.clear();
	}

	/**
	 * Comprueba si existe una clave.
	 */
	public boolean containsKey(K clave) {
		return mapa.containsKey(clave);
	}

	/**
	 * Devuelve el número total de claves almacenadas.
	 */
	public int size() {
		return mapa.size();
	}
}
