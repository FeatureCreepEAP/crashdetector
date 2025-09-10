package com.asbestosstar.crashdetector.limpiador;

import java.nio.file.Path;

public interface LimpiadorDeRegistro {

	public String limpiarConsola(String contento_de_consola);
	
	public boolean predicado(Path archivo);
	
	/**
	 * @param linea_de_comenzar linea de Divisor para repetir registros
	 * @param linea_nueva linea de registro limpiado
	 * @return linea del registro original
	 */
	public default int obtenerLineaOriginalDesdeLineaLimpiada(int linea_de_comenzar, int linea_nueva) {
		// TODO Auto-generated method stub
		return linea_de_comenzar+linea_nueva;
	}	
}
