package com.asbestosstar.crashdetector;

import java.util.*;

/**
 * Una estructura de datos que mapea un valor a una combinación de tres claves.
 * Similar a un mapa multiclave donde se requiere el conjunto completo de tres claves para acceder al valor.
 *
 * @param <K1> Primer tipo de clave.
 * @param <K2> Segundo tipo de clave.
 * @param <K3> Tercer tipo de clave.
 * @param <V>  Tipo del valor almacenado.
 */
public class TriMap<K1, K2, K3, V> {

    // Clave compuesta que combina tres claves
    private static class TripleKey<K1, K2, K3> {
        private final K1 key1;
        private final K2 key2;
        private final K3 key3;

        public TripleKey(K1 key1, K2 key2, K3 key3) {
            this.key1 = key1;
            this.key2 = key2;
            this.key3 = key3;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TripleKey)) return false;

            TripleKey<?, ?, ?> other = (TripleKey<?, ?, ?>) o;

            return Objects.equals(key1, other.key1) &&
                   Objects.equals(key2, other.key2) &&
                   Objects.equals(key3, other.key3);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key1, key2, key3);
        }

        @Override
        public String toString() {
            return "TripleKey{" +
                    "k1=" + key1 +
                    ", k2=" + key2 +
                    ", k3=" + key3 +
                    '}';
        }
    }

    // Mapa principal que usa la clave compuesta TripleKey
    private final Map<TripleKey<K1, K2, K3>, V> map = new HashMap<>();

    /**
     * Almacena un valor asociado a tres claves.
     *
     * @param key1 Primera clave.
     * @param key2 Segunda clave.
     * @param key3 Tercera clave.
     * @param value Valor a almacenar.
     */
    public void put(K1 key1, K2 key2, K3 key3, V value) {
        TripleKey<K1, K2, K3> tripleKey = new TripleKey<>(key1, key2, key3);
        map.put(tripleKey, value);
    }

    /**
     * Obtiene el valor asociado a una combinación específica de tres claves.
     *
     * @param key1 Primera clave.
     * @param key2 Segunda clave.
     * @param key3 Tercera clave.
     * @return El valor si existe, null en caso contrario.
     */
    public V get(K1 key1, K2 key2, K3 key3) {
        TripleKey<K1, K2, K3> tripleKey = new TripleKey<>(key1, key2, key3);
        return map.get(tripleKey);
    }

    /**
     * Elimina un valor asociado a una combinación específica de tres claves.
     *
     * @param key1 Primera clave.
     * @param key2 Segunda clave.
     * @param key3 Tercera clave.
     * @return true si se eliminó un valor, false en caso contrario.
     */
    public boolean remove(K1 key1, K2 key2, K3 key3) {
        TripleKey<K1, K2, K3> tripleKey = new TripleKey<>(key1, key2, key3);
        return map.remove(tripleKey) != null;
    }

    /**
     * Devuelve el número de entradas en el Trimap.
     */
    public int size() {
        return map.size();
    }

    /**
     * Devuelve true si el Trimap está vacío.
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Limpia todas las entradas del Trimap.
     */
    public void clear() {
        map.clear();
    }

    /**
     * Devuelve un conjunto con todas las claves compuestas (TripleKey).
     */
    public Set<TripleKey<K1, K2, K3>> keySet() {
        return map.keySet();
    }

    /**
     * Devuelve una colección con todos los valores del Trimap.
     */
    public Collection<V> values() {
        return map.values();
    }

    /**
     * Devuelve una vista de los pares clave-valor del Trimap.
     */
    public Set<Map.Entry<TripleKey<K1, K2, K3>, V>> entrySet() {
        return map.entrySet();
    }

    /**
     * Devuelve una representación en cadena del Trimap.
     */
    @Override
    public String toString() {
        return "Trimap{" +
                "entries=" + map +
                '}';
    }
}
