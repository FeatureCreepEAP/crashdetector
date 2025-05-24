package com.asbestosstar.crashdetector.analyzador;

import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class ServicioDeModLauncherNoFunciona implements Verificaciones {

    private boolean activado = false;
    private final Set<String> serviciosFallidos = new HashSet<>();

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;
        String[] lineas = contenidoConsola.split(Verificaciones.nl);
        String carga = "Service failed to load";
        
        for (String linea : lineas) {
            if (linea.contains(carga)) {
                
                String servicio = linea.split(carga)[1].trim();
                String mensaje = MonitorDePID.idioma.servicioMLNoPudoCargar(servicio);
                serviciosFallidos.add(mensaje);
                activado = true;
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ServicioDeModLauncherNoFunciona();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1000.0f; // Prioridad media
    }

    @Override
    public String mensaje() {
        if (serviciosFallidos.isEmpty()) return "";
        
        CDStringBuilder html = new CDStringBuilder();
        html.append("<ul>");
        
        for (String servicio : serviciosFallidos) {
            html.append("<li>"+servicio+"</li>");
        }
        
        html.append("</ul>");
        return html.toString();
    }
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_servicio_de_modlauncher_no_funciona();
	}
    
    
}