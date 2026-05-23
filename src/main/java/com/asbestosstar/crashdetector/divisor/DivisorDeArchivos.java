package com.asbestosstar.crashdetector.divisor;

import java.nio.file.Path;

public interface DivisorDeArchivos {

	public int obtenerLineaOriginal(String[] lineas);

	public boolean predicado(Path archivo, String contento_existe);

}
