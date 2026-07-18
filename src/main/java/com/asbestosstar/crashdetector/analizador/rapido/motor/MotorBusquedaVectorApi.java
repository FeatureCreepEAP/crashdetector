package com.asbestosstar.crashdetector.analizador.rapido.motor;

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorSpecies;

/**
 * Motor de busqueda de bytes acelerado mediante la Vector API.
 *
 * Busca todas las apariciones de un byte dentro del intervalo
 * {@code [inicio, fin)} y escribe sus posiciones absolutas en
 * {@code posiciones}.
 *
 * Esta clase debe cargarse solamente cuando la Vector API real este disponible.
 * El JAR de stubs debe usarse unicamente para compilacion.
 */
public final class MotorBusquedaVectorApi implements MotorBusquedaBytes {

	private static final VectorSpecies<Byte> ESPECIE = ByteVector.SPECIES_PREFERRED;

	@Override
	public int buscar(byte[] datos, int inicio, int fin, byte valor, int[] posiciones, int maxPosiciones) {

		validarArgumentos(datos, inicio, fin, posiciones, maxPosiciones);

		int capacidad = Math.min(maxPosiciones, posiciones.length);

		if (capacidad == 0 || inicio == fin) {
			return 0;
		}

		int total = 0;
		int longitud = fin - inicio;

		/*
		 * loopBound devuelve la cantidad de elementos que pueden procesarse usando
		 * vectores completos.
		 */
		int limiteVectorial = inicio + ESPECIE.loopBound(longitud);

		int i = inicio;

		for (; i < limiteVectorial && total < capacidad; i += ESPECIE.length()) {

			ByteVector vector = ByteVector.fromArray(ESPECIE, datos, i);

			VectorMask<Byte> mascara = vector.eq(valor);

			/*
			 * Cada bit representa un carril coincidente.
			 *
			 * Para ByteVector, las especies actualmente disponibles tienen como maximo 64
			 * carriles, por lo que las coincidencias caben en un long.
			 */
			long bits = mascara.toLong();

			/*
			 * Extraer las coincidencias desde el carril menor hasta el mayor para conservar
			 * el orden ascendente.
			 */
			while (bits != 0L && total < capacidad) {
				int carril = Long.numberOfTrailingZeros(bits);

				posiciones[total++] = i + carril;

				/*
				 * Eliminar el bit activo menos significativo.
				 */
				bits &= bits - 1L;
			}
		}

		/*
		 * Procesar escalarmente solamente la cola que no completa un vector entero.
		 */
		for (; i < fin && total < capacidad; i++) {

			if (datos[i] == valor) {
				posiciones[total++] = i;
			}
		}

		return total;
	}

	@Override
	public String nombre() {
		return "vector-api-byte-" + (ESPECIE.length() * Byte.SIZE);
	}

	private static void validarArgumentos(byte[] datos, int inicio, int fin, int[] posiciones, int maxPosiciones) {

		if (datos == null) {
			throw new NullPointerException("datos");
		}

		if (posiciones == null) {
			throw new NullPointerException("posiciones");
		}

		if (inicio < 0) {
			throw new IndexOutOfBoundsException("inicio no puede ser negativo: " + inicio);
		}

		if (fin < inicio) {
			throw new IndexOutOfBoundsException("fin no puede ser menor que inicio: inicio=" + inicio + ", fin=" + fin);
		}

		if (fin > datos.length) {
			throw new IndexOutOfBoundsException(
					"fin supera la longitud de datos: fin=" + fin + ", longitud=" + datos.length);
		}

		if (maxPosiciones < 0) {
			throw new IllegalArgumentException("maxPosiciones no puede ser negativo: " + maxPosiciones);
		}
	}
}