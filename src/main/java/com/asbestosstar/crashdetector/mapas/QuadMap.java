package com.asbestosstar.crashdetector.mapas;

import java.util.*;

/**
 * Una estructura de datos que mapea un valor a una combinación de cuatro
 * claves. Similar a un mapa multiclave donde se requiere el conjunto completo
 * de cuatro claves para acceder al valor.
 *
 * @param <K1> Primer tipo de clave.
 * @param <K2> Segundo tipo de clave.
 * @param <K3> Tercer tipo de clave.
 * @param <K4> Cuarto tipo de clave.
 * @param <V>  Tipo del valor almacenado.
 */
public class QuadMap<K1, K2, K3, K4, V> {

	// Clave compuesta que combina cuatro claves
	public static class QuadrupleKey<K1, K2, K3, K4> {
		public final K1 key1;
		public final K2 key2;
		public final K3 key3;
		public final K4 key4;

		public QuadrupleKey(K1 key1, K2 key2, K3 key3, K4 key4) {
			this.key1 = key1;
			this.key2 = key2;
			this.key3 = key3;
			this.key4 = key4;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (!(o instanceof QuadrupleKey))
				return false;

			QuadrupleKey<?, ?, ?, ?> other = (QuadrupleKey<?, ?, ?, ?>) o;

			return Objects.equals(key1, other.key1) && Objects.equals(key2, other.key2)
					&& Objects.equals(key3, other.key3) && Objects.equals(key4, other.key4);
		}

		@Override
		public int hashCode() {
			return Objects.hash(key1, key2, key3, key4);
		}

		@Override
		public String toString() {
			return "QuadrupleKey{" + "k1=" + key1 + ", k2=" + key2 + ", k3=" + key3 + ", k4=" + key4 + '}';
		}
	}

	// Mapa principal que usa la clave compuesta QuadrupleKey
	private final Map<QuadrupleKey<K1, K2, K3, K4>, V> map = new HashMap<>();

	/**
	 * Almacena un valor asociado a cuatro claves.
	 *
	 * @param key1  Primera clave.
	 * @param key2  Segunda clave.
	 * @param key3  Tercera clave.
	 * @param key4  Cuarta clave.
	 * @param value Valor a almacenar.
	 */
	public void put(K1 key1, K2 key2, K3 key3, K4 key4, V value) {
		QuadrupleKey<K1, K2, K3, K4> quadKey = new QuadrupleKey<>(key1, key2, key3, key4);
		map.put(quadKey, value);
	}

	/**
	 * Obtiene el valor asociado a una combinación específica de cuatro claves.
	 *
	 * @param key1 Primera clave.
	 * @param key2 Segunda clave.
	 * @param key3 Tercera clave.
	 * @param key4 Cuarta clave.
	 * @return El valor si existe, null en caso contrario.
	 */
	public V get(K1 key1, K2 key2, K3 key3, K4 key4) {
		QuadrupleKey<K1, K2, K3, K4> quadKey = new QuadrupleKey<>(key1, key2, key3, key4);
		return map.get(quadKey);
	}

	/**
	 * Elimina un valor asociado a una combinación específica de cuatro claves.
	 *
	 * @param key1 Primera clave.
	 * @param key2 Segunda clave.
	 * @param key3 Tercera clave.
	 * @param key4 Cuarta clave.
	 * @return true si se eliminó un valor, false en caso contrario.
	 */
	public boolean remove(K1 key1, K2 key2, K3 key3, K4 key4) {
		QuadrupleKey<K1, K2, K3, K4> quadKey = new QuadrupleKey<>(key1, key2, key3, key4);
		return map.remove(quadKey) != null;
	}

	/**
	 * Devuelve el número de entradas en el QuadMap.
	 */
	public int size() {
		return map.size();
	}

	/**
	 * Devuelve true si el QuadMap está vacío.
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * Limpia todas las entradas del QuadMap.
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * Devuelve un conjunto con todas las claves compuestas (QuadrupleKey).
	 */
	public Set<QuadrupleKey<K1, K2, K3, K4>> keySet() {
		return map.keySet();
	}

	/**
	 * Devuelve una colección con todos los valores del QuadMap.
	 */
	public Collection<V> values() {
		return map.values();
	}

	/**
	 * Devuelve una vista de los pares clave-valor del QuadMap.
	 */
	public Set<Map.Entry<QuadrupleKey<K1, K2, K3, K4>, V>> entrySet() {
		return map.entrySet();
	}

	/**
	 * Devuelve una representación en cadena del QuadMap.
	 */
	@Override
	public String toString() {
		return "QuadMap{" + "entries=" + map + '}';
	}
}
