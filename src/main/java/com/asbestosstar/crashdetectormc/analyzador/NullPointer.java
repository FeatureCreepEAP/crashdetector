package com.asbestosstar.crashdetectormc.analyzador;

import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class NullPointer implements Verificaciones {

    private boolean activado = false;
    private final Set<String> errores = new HashSet<>(); // Almacena múltiples errores [[5]]

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;

        String[] lineas = contenidoConsola.split(Verificaciones.nl);
        
        for (String linea : lineas) {
            if (linea.contains("java.lang.NullPointerException: Cannot invoke")) {
                String metodo = "método desconocido";
                String objeto = "objeto";
                
                // Extraer método usando split seguro [[5]]
                try {
                    String[] parts = linea.split("Cannot invoke \"");
                    if (parts.length > 1) {
                        metodo = parts[1].split("\"")[0];
                    }
                    
                    parts = linea.split("because the return value of \"");
                    if (parts.length > 1) {
                        objeto = parts[1].split("\"")[0];
                    }
                } catch (Exception ignored) { 
                    // Valores por defecto ya asignados
                }

                // Formato: "Método 'X' en objeto 'Y' es nulo"
                String mensaje = MonitorDePID.idioma.null_pointer_error(metodo, objeto);
                errores.add(mensaje);
                activado = true;
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new NullPointer();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 50.0f; // Prioridad crítica para NullPointer [[6]][[10]]
    }

    @Override
    public String mensaje() {
        if (errores.isEmpty()) return "";
        
        StringBuilder html = new StringBuilder("<ul>");
        for (String error : errores) {
            html.append("<li>").append(error).append("</li>");
        }
        html.append("</ul>");
        return html.toString();
    }
    
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_null_pointer();
	}
    
}