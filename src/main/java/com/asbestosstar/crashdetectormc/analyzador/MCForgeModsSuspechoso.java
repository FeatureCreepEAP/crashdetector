package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class MCForgeModsSuspechoso implements Verificaciones {

	public boolean activado=false;

	
    @Override
    public void verificar(String contenido_de_consola, CDStringBuilder constructor) {
        String[] líneas = contenido_de_consola.split(nl);
        int longitud = líneas.length;
        
        for (int i = 0; i < longitud; i++) {
            String línea = líneas[i];
            
            // Verificación original de mods sospechosos
            if (línea.contains("Suspected Mod:")) {
                if (i + 1 < longitud) {
                    constructor.append(MonitorDePID.idioma.mcforge_mod_suspechoso())
                               .append(" ")
                               .append(líneas[i + 1].trim())
                               .append(nl_html);
                }
                activado=true;
            }
            // Nueva verificación de errores de creación de mods
            else if (línea.contains("Failed to create mod instance. ModID:")) {
                int inicioIDMod = línea.indexOf("ModID: ") + "ModID: ".length();
                int índiceComa = línea.indexOf(',', inicioIDMod);
                
                if (índiceComa > inicioIDMod) {
                    String idMod = línea.substring(inicioIDMod, índiceComa).trim();
                    String detallesError = línea.substring(índiceComa + 1).trim();
                    
                    constructor.append(MonitorDePID.idioma.mcforge_mod_suspechoso()+" "+idMod+": "+detallesError).append(nl_html);
                }
                activado=true;
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new MCForgeModsSuspechoso();
    }
    
	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}
    
    
}