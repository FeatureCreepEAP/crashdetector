package com.asbestosstar.crashdetector.analizador.rapido;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Interfaz para verificaciones que aprovechan el escaneo rápido del log.
 */
public interface VerificacionRapida extends Verificaciones {

	/**
	 * Devuelve patrones literales que sirven como disparadores rápidos.
	 *
	 * El motor usa estos textos en un escáner rápido. Cuando uno aparece, la
	 * verificación recibe un EventoDeCoincidencia con archivo, línea y contexto.
	 */
	default String[] patronesRapidos() {
		return new String[0];
	}

	/**
	 * Indica si esta verificación necesita ver todas las líneas aunque no haya
	 * coincidencia rápida.
	 */
	@Override
	default boolean necesitaTodasLasLineas() {
		return false;
	}

	/**
	 * Indica si la verificación quiere activarse para el escaneo línea a línea para
	 * una consola específica.
	 */
	@Override
	default boolean activarEscaneoPorLinea(Consola consola) {
		return false;
	}

	/**
	 * Se ejecuta cuando el motor rápido encuentra uno de los patrones declarados.
	 */
	@Override
	default void verificarCoincidencia(EventoDeCoincidencia evento) {
	}

	/**
	 * Se ejecuta al terminar un archivo. Sirve para verificaciones que dependen del
	 * estado acumulado del archivo.
	 */
	public default void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
	}
}
