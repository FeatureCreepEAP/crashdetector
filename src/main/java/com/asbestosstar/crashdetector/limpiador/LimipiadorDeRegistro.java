package com.asbestosstar.crashdetector.limpiador;

import java.nio.file.Path;

public interface LimipiadorDeRegistro {

	public String limpiarConsola(String contento_de_consola);
	
	public boolean predicado(Path archivo);
}
