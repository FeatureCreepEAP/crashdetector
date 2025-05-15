package com.asbestosstar.crashdetectormc.analyzador;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class FaltasClases implements Verificaciones {

    private boolean activado = false;
    private final Set<String> clases = new HashSet<>();
    public final Set<String> todos = new LinkedHashSet<>(); 

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;
    	VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;

        // Check from VerificacionDeStackTrace
        for (String clase : vdst.fatal_clases_no_existe) {
            if (todos.add(clase)) { // Atomic check-and-add [[3]]
                clases.add(clase);
            }
        }

        // Check from console content
        for (String linea : contenidoConsola.split(Verificaciones.nl)) {
            String clase = null;
            
            if (linea.contains("Caused by: java.lang.ClassNotFoundException")) {
                clase = linea.split(":")[2].trim();
            } else if (linea.contains("Error loading class:") && !linea.contains("WARN")) {
                clase = linea.split("Error loading class: ")[1].split(" ")[0].trim();
            }

            if (clase != null && todos.add(clase)) {
                clases.add(clase);
            }
        }

        activado = !clases.isEmpty();
    }

    @Override
    public Verificaciones nueva() {
        return new FaltasClases();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 600.0f; // Highest priority for missing class errors [[10]]
    }

    @Override
    public String mensaje() {
        if (clases.isEmpty()) return "";

        StringBuilder html = new StringBuilder("<ul>").append(Verificaciones.nl_html);
        for (String clase : clases) {
            html.append("<li>").append(clase).append("</li>").append(Verificaciones.nl_html);
        }
        html.append("</ul>");
        return MonitorDePID.idioma.faltar_de_clases_fatales() + html;
    }
    
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_faltar_de_clases();
	}
}