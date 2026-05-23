package com.asbestosstar.crashdetector.divisor;

import java.nio.file.Path;

public class VainillaConsolaDivisor implements DivisorDeArchivos {

	@Override
	public int obtenerLineaOriginal(String[] lineas) {
		// TODO Auto-generated method stub

		System.out.println("DEBUG Linea de launcher_log es " + String.valueOf(lineas.length));
		return lineas.length;

	}

	@Override
	public boolean predicado(Path archivo, String nada) {
		// TODO Auto-generated method stub
		return archivo.toString().contains("launcher_log") || archivo.toString().contains(".hmcl");
	}

}
