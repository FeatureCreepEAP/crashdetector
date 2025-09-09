package com.asbestosstar.crashdetector.analizador;

import java.awt.Color;

import com.asbestosstar.crashdetector.Config;
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
	
	
	
    
    public static void abrirEnNavegador(String url) {
        try {
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
            }
        } catch (Exception e) {
            // Manejo de excepción silencioso
        }
    }
	
    
    public default Criticalidad nivel_de_criticalidad() {
    	return Criticalidad.ERROR;
    }

    
    
    public static class Criticalidad{
    	
    	public static Criticalidad FATAL = new Criticalidad(Color.RED);
    	public static Criticalidad ERROR = new Criticalidad(Config.convertirAColor(Config.obtenerInstancia().obtenerColorError()));
    	public static Criticalidad ADVERTENCIA = new Criticalidad(Config.convertirAColor(Config.obtenerInstancia().obtenerColorAdvertencia()));
    	
    	/**
    	 * Color en lectador
    	 */
    	public Color color;
    	
    	/**
    	 * Criticalidad en Lectador
    	 * @param color
    	 */
    	public Criticalidad(Color color) {
    		this.color=color;
    	}
    	
    }
    
    
    
	
}


