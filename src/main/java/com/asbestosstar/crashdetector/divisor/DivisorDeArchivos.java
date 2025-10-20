package com.asbestosstar.crashdetector.divisor;

import java.nio.file.Path;

public interface DivisorDeArchivos {

	public int obtenerLineaOriginal(String contentido_existente);

	public boolean predicado(Path archivo);

}
