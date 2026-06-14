package com.asbestosstar.crashdetector.analizador.rapido.motor;

/**
 * Implementación escalar básica de búsqueda de bytes.
 */
public final class MotorBusquedaEscalar implements MotorBusquedaBytes {

	@Override
	public int buscar(byte[] datos, int inicio, int fin, byte valor, int[] posiciones, int maxPosiciones) {
		int total = 0;

		for (int i = inicio; i < fin && total < maxPosiciones; i++) {
			if (datos[i] == valor) {
				posiciones[total++] = i;
			}
		}

		return total;
	}

	@Override
	public String nombre() {
		return "escalar";
	}
}
