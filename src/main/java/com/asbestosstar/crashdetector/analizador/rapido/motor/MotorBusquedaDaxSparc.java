package com.asbestosstar.crashdetector.analizador.rapido.motor;

import java.util.function.IntUnaryOperator;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.stream.intstream.CDOracleIntStream;

/**
 * Motor de busqueda de bytes mediante Oracle DAX Stream Offload.
 *
 * Esta clase solamente debe cargarse sobre Solaris SPARC V9 cuando
 * com.oracle.stream.DaxIntStream y libstreamoffload estan disponibles.
 */
public final class MotorBusquedaDaxSparc implements MotorBusquedaBytes {

	private final MotorBusquedaBytes respaldo = new MotorBusquedaEscalar();

	private volatile boolean daxDesactivado;

	@Override
	public int buscar(byte[] datos, int inicio, int fin, byte valor, int[] posiciones, int maxPosiciones) {

		validarArgumentos(datos, inicio, fin, posiciones, maxPosiciones);

		int capacidad = Math.min(maxPosiciones, posiciones.length);

		if (capacidad <= 0 || inicio == fin) {
			return 0;
		}

		if (daxDesactivado) {
			return respaldo.buscar(datos, inicio, fin, valor, posiciones, capacidad);
		}

		try {
			return buscarConDax(datos, inicio, fin, valor, posiciones, capacidad);

		} catch (LinkageError e) {
			desactivarDax(e);

		} catch (RuntimeException e) {
			desactivarDax(e);
		}

		return respaldo.buscar(datos, inicio, fin, valor, posiciones, capacidad);
	}

	private int buscarConDax(byte[] datos, int inicio, int fin, byte valor, int[] posiciones, int capacidad) {

		int longitud = fin - inicio;
		int[] entrada = new int[longitud];

		for (int i = 0; i < longitud; i++) {
			entrada[i] = datos[inicio + i];
		}

		final int valorBuscado = valor;

		IntUnaryOperator marcador = elemento -> elemento == valorBuscado ? 1 : 0;

		int[] coincidencias = CDOracleIntStream.deParalelo(entrada).map(marcador).toArray();

		int total = 0;

		for (int i = 0; i < coincidencias.length && total < capacidad; i++) {

			if (coincidencias[i] != 0) {
				posiciones[total++] = inicio + i;
			}
		}

		return total;
	}

	private void desactivarDax(Throwable causa) {
		if (daxDesactivado) {
			return;
		}

		daxDesactivado = true;

		CrashDetectorLogger.log("Oracle DAX ha sido desactivado despues de un error: " + causa.getClass().getName()
				+ ": " + String.valueOf(causa.getMessage()) + ". Se usara el motor escalar.");
	}

	private static void validarArgumentos(byte[] datos, int inicio, int fin, int[] posiciones, int maxPosiciones) {

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

		if (maxPosiciones < 0) {
			throw new IllegalArgumentException("maxPosiciones no puede ser negativo");
		}
	}

	@Override
	public String nombre() {
		return daxDesactivado ? "dax-sparc-desactivado-escalar" : "dax-sparc";
	}
}