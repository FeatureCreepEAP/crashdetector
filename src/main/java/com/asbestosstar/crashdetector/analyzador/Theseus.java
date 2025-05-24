package com.asbestosstar.crashdetector.analyzador;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class Theseus implements Verificaciones {

    private boolean activado = false;
    private String mensaje = ""; 

    @Override
    public void verificar(Consola consola) {
    	String contenidoDeConsola=consola.contento_verificar;
        if (contenidoDeConsola.contains("com.modrinth.theseus") || contenidoDeConsola.contains("ModrinthApp")) {
            this.mensaje = MonitorDePID.idioma.theseus() + Verificaciones.nl_html;
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new Theseus(); 
    }

    @Override
    public boolean activado() {
        return activado; 
    }

    @Override
    public float prioridad() {
        return 1000.0f;
    }

    @Override
    public String mensaje() {
        return mensaje; // Devuelve el mensaje almacenado
    }
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_theseus();
	}
    
    
}