package com.asbestosstar.crashdetectormc.analyzador;

import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class FaltaModuleJPMS implements Verificaciones {

    private boolean activado = false;
    private final Set<String> errores = new HashSet<>();

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;

        
        for (String linea : contenidoConsola.split(Verificaciones.nl)) {
            if (linea.contains("java.lang.module.FindException: Module ") 
                && linea.contains(" not found, required by ")) {
                
                try {
                    String modNecesitado = linea.split("Module ")[1].split(" not found")[0].trim();
                    String modRequeridor = linea.split("required by ")[1].trim();
                    
                    String mensaje = MonitorDePID.idioma.jpms_modules_faltas(modNecesitado, modRequeridor);
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
        return new FaltaModuleJPMS();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1000f; // Prioridad alta para errores de módulos JPMS [[7]]
    }

    @Override
    public String mensaje() {
        if (errores.isEmpty()) return "";
        
        StringBuilder html = new StringBuilder("<ul>").append(Verificaciones.nl_html);
        for (String error : errores) {
            html.append("<li>").append(error).append("</li>").append(Verificaciones.nl_html);
        }
        html.append("</ul>");
        return html.toString();
    }
    
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_falta_module_jmps();
	}
    
    
}