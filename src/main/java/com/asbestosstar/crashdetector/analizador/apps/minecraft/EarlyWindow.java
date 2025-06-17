package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

public class EarlyWindow implements Verificaciones {

    private boolean activado = false;
    private String mensaje = ""; 

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;

        
        String[] lineas = contenidoConsola.split(Verificaciones.nl);
        if (lineas.length == 0) return;

        String ultimaLinea = lineas[lineas.length - 1].trim();
        if (ultimaLinea.contains("Loading ImmediateWindowProvider fmlearlywindow")) {
            mensaje = MonitorDePID.idioma.fmlEarlyWindow() + Verificaciones.nl_html;
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new EarlyWindow();
    }

    @Override
    public boolean activado() {
        return activado; 
    }

    @Override
    public float prioridad() {
        return 800.0f; // Prioridad media para advertencias de inicialización
    }

    @Override
    public String mensaje() {
        return mensaje;
    }
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_early_window();
	}
	
	
    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
            .construir();
    }
	
}