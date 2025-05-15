package com.asbestosstar.crashdetectormc.analyzador;

import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class KubeJSResourcePack implements Verificaciones {

    private boolean activado = false;
    private final Set<String> errores = new HashSet<>();

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;
        String[] lineas = contenidoConsola.split(Verificaciones.nl);
        
        for (String linea : lineas) {
            if (linea.contains("from pack KubeJS Resource Pack [data]") && 
                linea.contains("Failed to parse ")) {
                
                try {
                    String modNombre = linea.split("Failed to parse ")[1].split(":")[0];
                    String mensaje = MonitorDePID.idioma.kubeJSResourcePack(modNombre);
                    errores.add(mensaje);
                    activado = true;
                } catch (Exception e) {
                    // Ignora errores de parseo para evitar fallos críticos
                }
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new KubeJSResourcePack();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 300.0f; // Prioridad media-alta para errores de recursos críticos [[3]]
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
		return MonitorDePID.idioma.nombre_de_faltar_de_kubejs_resourcepack();
	}
    
}