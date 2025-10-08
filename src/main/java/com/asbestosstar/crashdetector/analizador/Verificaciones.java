package com.asbestosstar.crashdetector.analizador;

import java.awt.Color;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public interface Verificaciones {

	public static String nl = System.lineSeparator();
	public static String nl_html = "<br>";
	
	
	/**
	 * Aqui es donde verificar. Se ejecuta una vez por consola.
	 * @param consola
	 */
	public void verificar(Consola consola);
	
	/**
	 * Aqui es donde verificar contenido en una linea specific. Se ejecuta para todos lineas.
	 * @param consola
	 */
	public default void verificar(Consola consola, String linea,int numero_de_linea) {
		//Por defecto hace nada, para muchos puede usar verificar(Consola)
	}
	
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
    	
    	public static Criticalidad FATAL = new Criticalidad(Color.RED,MonitorDePID.idioma.fatal());
    	public static Criticalidad ERROR = new Criticalidad(Config.convertirAColor(Config.obtenerInstancia().obtenerColorError()),MonitorDePID.idioma.error());
    	public static Criticalidad ADVERTENCIA = new Criticalidad(Config.convertirAColor(Config.obtenerInstancia().obtenerColorAdvertencia()),MonitorDePID.idioma.advertencia());
    	
    	/**
    	 * Color en lectador
    	 */
    	public Color color;
    	public String nombre;
    	
    	
    	/**
    	 * Criticalidad en Lectador
    	 * @param color
    	 */
    	public Criticalidad(Color color,String nombre) {
    		this.color=color;
    		this.nombre=nombre;
    	}
    	
    }
    
    /**
     * Una id sin simbolas o accentos o espacios
     * @return
     */
    public String id();
    
    
    
    
	
}


