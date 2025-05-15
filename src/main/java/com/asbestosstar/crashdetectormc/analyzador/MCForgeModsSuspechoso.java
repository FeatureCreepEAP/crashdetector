package com.asbestosstar.crashdetectormc.analyzador;

import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class MCForgeModsSuspechoso implements Verificaciones {

    private boolean activado = false;
    private final Set<String> errores = new HashSet<>(); 

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;
        String[] lineas = contenidoConsola.split(Verificaciones.nl);
        
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i];
            

            if (linea.contains("Suspected Mod:")) {
                if (i+1 < lineas.length) {
                    String mod = lineas[i+1].trim();
                    errores.add(MonitorDePID.idioma.mcforge_mod_suspechoso() + ": " + mod);
                    activado = true;
                }
            } 

            else if (linea.contains("Failed to create mod instance. ModID:")) {
                try {
                    String modID = linea.split("ModID: ")[1].split(",")[0].trim();
                    String detalles = linea.split(", ")[1].trim();
                    errores.add(
                        String.format("%s: %s - %s", 
                            MonitorDePID.idioma.mcforge_mod_suspechoso(),
                            modID, 
                            detalles
                        )
                    );
                    activado = true;
                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new MCForgeModsSuspechoso();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 900.0f; // Prioridad alta para mods sospechosos [[4]]
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
		return MonitorDePID.idioma.nombre_de_mcforge_mod_sespechoso();
	}
    
    
}