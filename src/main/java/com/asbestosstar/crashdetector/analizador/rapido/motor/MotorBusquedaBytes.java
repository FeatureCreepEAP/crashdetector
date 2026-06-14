package com.asbestosstar.crashdetector.analizador.rapido.motor;

/**
 * Interfaz para la búsqueda acelerada de bytes en un buffer.
 */
public interface MotorBusquedaBytes {

	/**
	 * Busca todas las posiciones de un byte dentro de una ventana de datos.
	 *
	 * Se usa para encontrar saltos de línea y delimitadores frecuentes.
	 */
	int buscar(byte[] datos, int inicio, int fin, byte valor, int[] posiciones, int maxPosiciones);

	/**
	 * Indica el tipo de aceleración usada para diagnóstico.
	 */
	String nombre();
}
