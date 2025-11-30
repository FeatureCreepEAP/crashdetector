package com.asbestosstar.crashdetector.divisor;

import java.io.File;
import java.nio.file.Path;

import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class VainillaConsolaDivisor implements DivisorDeArchivos {

	@Override
	public int obtenerLineaOriginal(String contentido_existente) {
		// TODO Auto-generated method stub

		String[] lineas = contentido_existente.split(Verificaciones.nl);
		System.out.println("DEBUG Linea de launcher_log es " + String.valueOf(lineas.length));
		return lineas.length;

	}

	@Override
	public boolean predicado(Path archivo, String nada) {
		// TODO Auto-generated method stub
		return archivo.toString().contains("launcher_log") || archivo.toString().contains(".hmcl");
	}

}
