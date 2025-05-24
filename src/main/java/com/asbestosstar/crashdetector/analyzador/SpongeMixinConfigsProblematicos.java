package com.asbestosstar.crashdetector.analyzador;

import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class SpongeMixinConfigsProblematicos implements Verificaciones {

    private boolean activado = false;
    private final Set<String> sm_config = new HashSet<>();

    @Override
    public void verificar(Consola consola) {
    	sm_config.addAll(consola.verificacion_de_stacktrace.sm_config);
    	if(!sm_config.isEmpty()) {
    	activado=true;
    	}
    }

    @Override
    public Verificaciones nueva() {
        return new SpongeMixinConfigsProblematicos();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1000.0f; // Prioridad media-alta para errores de inicialización
    }

    @Override
    public String mensaje() {
        if (sm_config.isEmpty()) return "";
        
        StringBuilder html = new StringBuilder("<ul>");
        for (String sm : sm_config) {
            html.append("<li>")
                .append(MonitorDePID.idioma.config_spongemixin_problematico(sm))
                .append("</li>")
                .append(Verificaciones.nl_html);
        }
        html.append("</ul>");
        return html.toString();
    }
    
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_spongemixin_configs_problematicos();
	}
    
    
    
}