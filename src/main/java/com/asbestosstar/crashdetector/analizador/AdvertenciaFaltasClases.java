package com.asbestosstar.crashdetector.analizador;

import java.util.LinkedHashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class AdvertenciaFaltasClases implements Verificaciones {

    private boolean activado = false;
    private final Set<String> clases = new LinkedHashSet<>(); 

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;
        
        for (String linea : contenidoConsola.split(Verificaciones.nl)) {
            if (linea.contains("Error loading class:") && linea.contains("WARN")) {
                try {
                    String clase = linea.split("Error loading class: ")[1].split(" ")[0].trim();
                   // if (!FaltasClases.todos.contains(clase)) { // Verifica globalmente
                        clases.add(clase);
                  //      FaltasClases.todos.add(clase); // Actualiza registro global
                   // }
                } catch (Exception ignored) {
                    // Ignora líneas mal formateadas
                }
            }
        }
        
        activado = !clases.isEmpty();
    }

    @Override
    public Verificaciones nueva() {
        return new AdvertenciaFaltasClases(); 
    }

    @Override
    public boolean activado() {
        return activado; 
    }

    @Override
    public float prioridad() {
        return 2.0f; 
    }

    @Override
    public String mensaje() {
        if (clases.isEmpty()) return "";
        
        StringBuilder html = new StringBuilder("<ul>");
        for (String clase : clases) {
            html.append("<li>").append(clase).append("</li>");
        }
        html.append("</ul>");
        return MonitorDePID.idioma.faltar_de_clases_advertencia() + html.toString();
    }

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_faltar_de_clases_advertencia();
	}
	
	
    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
            .construir();
    }
	
	
	
}