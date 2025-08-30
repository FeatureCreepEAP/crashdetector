package com.asbestosstar.crashdetector.analizador;

@FunctionalInterface
public interface ListaDenegadosTrace {

	public boolean predicado(String contentido);
	
}
