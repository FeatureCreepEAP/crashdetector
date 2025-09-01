package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class FabricMCRuntimeErrorProvidedBy implements Verificaciones {

    private boolean activado = false;
    private final Set<String> modIdsProblematicos = new HashSet<>();

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contenido_verificar;

        
        for (String linea : contenidoConsola.split(Verificaciones.nl)) {
            if (linea.contains("Could not execute entrypoint stage") && linea.contains("provided by")) {
                try {
                    String modId = linea.split("provided by")[1].trim();
                    modIdsProblematicos.add(modId);
                    activado = true;
                } catch (Exception e) {
                    // Ignora líneas mal formateadas
                }
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new FabricMCRuntimeErrorProvidedBy();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 900.0f; // Prioridad media-alta para errores de inicialización
    }

    @Override
    public String mensaje() {
        if (modIdsProblematicos.isEmpty()) return "";
        
        StringBuilder html = new StringBuilder("<ul>");
        for (String modId : modIdsProblematicos) {
            html.append("<li>")
                .append(MonitorDePID.idioma.modids_problematicos())
                .append(" <b>")
                .append(modId)
                .append("</b></li>")
                .append(Verificaciones.nl_html);
        }
        html.append("</ul>");
        return html.toString();
    }
    
    
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_fabricmc_runtime_error_provided_by();
	}
	
    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
            .construir();
    }
    
    
}