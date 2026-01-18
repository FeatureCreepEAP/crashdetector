package com.asbestosstar.crashdetector.mapas;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Estructura de datos que mapea un valor a una combinación de dos claves.
 * Similar a un mapa multiclave donde se requiere el conjunto completo de dos
 * claves para acceder al valor.
 *
 * @param <K0> Primer tipo de clave.
 * @param <K1> Segundo tipo de clave.
 * @param <V>  Tipo del valor almacenado.
 */
public class BiMap<K0, K1, V> {

	// Clave compuesta que combina dos claves
	public static class DoubleKey<K0, K1> {
		public final K0 key0;
		public final K1 key1;

		public DoubleKey(K0 key0, K1 key1) {
			this.key0 = key0;
			this.key1 = key1;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (!(o instanceof DoubleKey))
				return false;

			DoubleKey<?, ?> other = (DoubleKey<?, ?>) o;

			return Objects.equals(key0, other.key0) && Objects.equals(key1, other.key1);
		}

		@Override
		public int hashCode() {
			return Objects.hash(key0, key1);
		}

		@Override
		public String toString() {
			return "DoubleKey{" + "k0=" + key0 + ", k1=" + key1 + '}';
		}
	}

	// Mapa principal que usa la clave compuesta DoubleKey
	private final Map<DoubleKey<K0, K1>, V> map = new HashMap<>();

	/**
	 * Almacena un valor asociado a dos claves.
	 *
	 * @param key1  Primera clave.
	 * @param key2  Segunda clave.
	 * @param value Valor a almacenar.
	 */
	public void put(K0 key0, K1 key1, V value) {
		DoubleKey<K0, K1> doubleKey = new DoubleKey<>(key0, key1);
		map.put(doubleKey, value);
	}

	/**
	 * Obtiene el valor asociado a una combinación específica de dos claves.
	 *
	 * @param key1 Primera clave.
	 * @param key2 Segunda clave.
	 * @return El valor si existe, null en caso contrario.
	 */
	public V get(K0 key0, K1 key1) {
		DoubleKey<K0, K1> doubleKey = new DoubleKey<>(key0, key1);
		return map.get(doubleKey);
	}

	/**
	 * Elimina un valor asociado a una combinación específica de dos claves.
	 *
	 * @param key1 Primera clave.
	 * @param key2 Segunda clave.
	 * @return true si se eliminó un valor, false en caso contrario.
	 */
	public boolean remove(K0 key0, K1 key1) {
		DoubleKey<K0, K1> doubleKey = new DoubleKey<>(key0, key1);
		return map.remove(doubleKey) != null;
	}

	/**
	 * Devuelve el número de entradas en el Bimap.
	 */
	public int size() {
		return map.size();
	}

	/**
	 * Devuelve true si el Bimap está vacío.
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * Limpia todas las entradas del Bimap.
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * Devuelve un conjunto con todas las claves compuestas (DoubleKey).
	 */
	public Set<DoubleKey<K0, K1>> keySet() {
		return map.keySet();
	}

	/**
	 * Devuelve una colección con todos los valores del Bimap.
	 */
	public Collection<V> values() {
		return map.values();
	}

	/**
	 * Devuelve una vista de los pares clave-valor del Bimap.
	 */
	public Set<Map.Entry<DoubleKey<K0, K1>, V>> entrySet() {
		return map.entrySet();
	}

	/**
	 * Devuelve true si existe alguna entrada con la primera clave dada, sin tener
	 * en cuenta la segunda clave.
	 *
	 * @param key0 Primera clave a comprobar.
	 * @return true si al menos una entrada contiene esa key0.
	 */
	public boolean containsKey0(K0 key0) {
		for (DoubleKey<K0, K1> dk : map.keySet()) {
			if (Objects.equals(dk.key0, key0)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Devuelve una representación en cadena del Bimap.
	 */
	@Override
	public String toString() {
		return "Bimap{" + "entries=" + map + '}';
	}
}