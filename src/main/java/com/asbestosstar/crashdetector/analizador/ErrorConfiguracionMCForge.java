package com.asbestosstar.crashdetector.analizador;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class ErrorConfiguracionMCForge implements Verificaciones {

    private boolean activado = false;
    private String mensaje = ""; 

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;

        for (String linea : contenidoConsola.split(Verificaciones.nl)) {
            if (linea.contains("ParsingException: Not enough data available")) {
                mensaje = MonitorDePID.idioma.errorConfigMCForge() + Verificaciones.nl_html;
                activado = true;
                break;
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorConfiguracionMCForge(); 
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 500.0f; // Prioridad alta para errores de configuración críticos
    }

    @Override
    public String mensaje() {
        return mensaje;
    }
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_error_de_config_mcforge();
	}
}