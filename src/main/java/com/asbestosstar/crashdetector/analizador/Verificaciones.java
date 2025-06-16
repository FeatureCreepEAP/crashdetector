package com.asbestosstar.crashdetector.analizador;

import com.asbestosstar.crashdetector.Consola;

public interface Verificaciones {

	public static String nl = System.lineSeparator();
	public static String nl_html = "<br>";
	
	
	public void verificar(Consola consola);
	
	/**
	 * Una nueva instancia
	 * @return
	 */
	public Verificaciones nueva();
	
	
	public boolean activado();
	
	/**
	 * Si esta activado  abrimos crashdectector se cerrada normalmente
	 * @return
	 */
	public default boolean anularNormal() {
		return false;
	}
	
	public float prioridad();
	
	/**
	 * Solo activar cuando activado()
	 * @return
	 */
	public String mensaje();
	
	
	public String nombre();
	
	public QuickFix solucion();
	
}
