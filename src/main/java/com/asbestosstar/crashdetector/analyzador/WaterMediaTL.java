package com.asbestosstar.crashdetector.analyzador;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class WaterMediaTL implements Verificaciones {

    private boolean activado = false;
    private String mensaje = ""; 

    @Override
    public void verificar(Consola consola) {
    	String contento_de_consola=consola.contento_verificar;
        if (contento_de_consola.contains("TLauncher is NOT supported by WATERMeDIA, please stop using it")) {
            this.mensaje = MonitorDePID.idioma.waterMediaTL() + Verificaciones.nl_html;
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new WaterMediaTL();
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
        return mensaje;
    }
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_watermedia_tl();
	}
    
    
}