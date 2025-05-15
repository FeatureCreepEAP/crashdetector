package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class Segundo60Tick implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";

    @Override
    public void verificar(Consola consola) {
    	String contento_de_consola=consola.contento_verificar;
        // Verifica la presencia del error de tick crítico
        String patron = "[Server Watchdog/FATAL] [net.minecraft.server.dedicated.ServerHangWatchdog]: " +
                       "A single server tick took 60.00 seconds (should be max 0.05)";
        
        if (contento_de_consola.contains(patron)) {
            this.mensaje = MonitorDePID.idioma.segundo60Tick() + Verificaciones.nl_html;
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new Segundo60Tick();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 100.0f;
    }

    @Override
    public String mensaje() {
        return mensaje;
    }
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_60_segundo_trick();
	}
    
    
}