package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.CDStringBuilder;

public interface Verificaciones {

	public static String nl = System.lineSeparator();
	public static String nl_html = "<br>";
	
	
	public void verificar(String contento_de_consola,CDStringBuilder constructor);
	
	/**
	 * Una nueva instancia
	 * @return
	 */
	public Verificaciones nueva();
	
	

	
	
}
