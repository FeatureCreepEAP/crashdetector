package com.asbestosstar.crashdetector.limpiador;

import java.nio.file.Path;

public class LimpiadorNingun implements LimpiadorDeRegistro{

	@Override
	public String limpiarConsola(String contento_de_consola) {
		// TODO Auto-generated method stub
		return contento_de_consola;
	}

	@Override
	public boolean predicado(Path archivo) {
		// TODO Auto-generated method stub
		return true;
	}

}
