package com.asbestosstar.crashdetector.analyzador;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class ErrorDeMonitorLWJGL implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;

        // Verifica cada línea buscando el error específico
        for (String linea : contenidoConsola.split(Verificaciones.nl)) {
            if (linea.contains("org.lwjgl.LWJGLException: Failed to set display mode")) {
                mensaje = MonitorDePID.idioma.errorMonitorLWJGL() + Verificaciones.nl_html;
                activado = true;
                break; 
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorDeMonitorLWJGL();
    }

    @Override
    public boolean activado() {
        return activado; 
    }

    @Override
    public float prioridad() {
        return 800.0f; // Prioridad crítica para errores de renderizado
    }

    @Override
    public String mensaje() {
        return mensaje; 
    }
    
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_error_de_monitor_lwjgl();
	}
    
}