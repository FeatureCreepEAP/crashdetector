package com.asbestosstar.crashdetectormc.analyzador;

import java.io.File;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class CursedConsola implements Verificaciones {

    private boolean activado = false;
    private String mensaje = ""; 

    @Override
    public void verificar(Consola consolaog) {
        String ubicacion = new File(".").getAbsolutePath().toLowerCase();
        if (ubicacion.contains("curseforge") && ubicacion.contains("instances")) {
            
            boolean tieneConsolaLauncher = false;
            for (Consola consola : MonitorDePID.consolas) {
                if (consola.archivo.toString().contains("launcher_log")) {
                    tieneConsolaLauncher = true;
                    break;
                }
            }
            
            if (!tieneConsolaLauncher) {
                mensaje = MonitorDePID.idioma.noTieneConsolaDeLauncherCursedForge();
                activado = true;
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new CursedConsola(); 
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1000f; // Prioridad baja para advertencias de logs faltantes
    }

    @Override
    public String mensaje() {
        return mensaje;
    }
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_cursed_consola();
	}
}