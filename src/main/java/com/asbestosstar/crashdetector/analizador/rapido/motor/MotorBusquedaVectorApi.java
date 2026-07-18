package com.asbestosstar.crashdetector.analizador.rapido.motor;

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorSpecies;

/**
 * Busqueda directa de bytes mediante la Vector API, sin reflexion dentro del
 * bucle caliente.
 */
public final class MotorBusquedaVectorApi implements MotorBusquedaBytes {

	private static final VectorSpecies<Byte> ESPECIE = ByteVector.SPECIES_PREFERRED;
	private static final int LARGO = ESPECIE.length();

	@Override
	public int buscar(byte[] datos, int inicio, int fin, byte valor, int[] posiciones, int maxPosiciones) {

		if (datos == null) {
			throw new NullPointerException("datos");
		}

		if (posiciones == null) {
			throw new NullPointerException("posiciones");
		}

		if (inicio < 0 || fin < inicio || fin > datos.length) {
			throw new IndexOutOfBoundsException(
					"Ventana invalida: inicio=" + inicio + ", fin=" + fin + ", longitud=" + datos.length);
		}

		int capacidad = Math.min(maxPosiciones, posiciones.length);

		if (capacidad <= 0 || inicio == fin) {
			return 0;
		}

		int total = 0;
		int i = inicio;
		int limiteVectorial = inicio + ESPECIE.loopBound(fin - inicio);

		for (; i < limiteVectorial && total < capacidad; i += LARGO) {
			VectorMask<Byte> mascara = ByteVector.fromArray(ESPECIE, datos, i).eq(valor);

			if (LARGO <= Long.SIZE) {
				long bits = mascara.toLong();

				while (bits != 0 && total < capacidad) {
					int carril = Long.numberOfTrailingZeros(bits);
					posiciones[total++] = i + carril;
					bits &= bits - 1;
				}
			} else if (mascara.anyTrue()) {
				/* Reserva para una especie futura con mas de 64 carriles byte. */
				int finBloque = i + LARGO;

				for (int j = i; j < finBloque && total < capacidad; j++) {
					if (datos[j] == valor) {
						posiciones[total++] = j;
					}
				}
			}
		}

		for (; i < fin && total < capacidad; i++) {
			if (datos[i] == valor) {
				posiciones[total++] = i;
			}
		}

		return total;
	}

	@Override
	public String nombre() {
		return "vector-api-byte-" + (LARGO * Byte.SIZE);
	}
}
