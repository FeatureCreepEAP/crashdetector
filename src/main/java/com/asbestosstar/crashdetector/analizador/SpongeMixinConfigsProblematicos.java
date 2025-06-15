package com.asbestosstar.crashdetector.analizador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.buscar.Buscardor;

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
        Buscardor.cargar();

        StringBuilder html = new StringBuilder();
        html.append("<span style='color: #")
            .append(Config.obtenerInstancia().obtenerColorDeTitulosDeConsolas())
            .append("; font-weight: bold;'>")
            .append(MonitorDePID.idioma.config_spongemixin_problematico())
            .append("</span>")
            .append(Verificaciones.nl_html)
            .append("<ul>");

        List<String> listItems = new ArrayList<>();
        for (String sm : sm_config) {
            String jars_de_sm_string = "";
            List<String> jars_de_sm = Buscardor.obternerUbicaciones(Buscardor.buscarModsConTermino(sm));

            if (!jars_de_sm.isEmpty()) {
                List<String> boldJars = new ArrayList<>();
                for (String jar : jars_de_sm) {
                    boldJars.add("<strong>" + jar + "</strong>");
                }

                jars_de_sm_string = " (" + String.join(", ", boldJars) + ")";
            }

            listItems.add("<li>" + sm + jars_de_sm_string + "</li>");
        }

        html.append(String.join("", listItems))
            .append("</ul>");

        return html.toString();
    }
    
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_spongemixin_configs_problematicos();
	}
    
    
    
}