package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class KubeJSResourcePack implements Verificaciones {

	public boolean activado=false;

	
    @Override
    public void verificar(String contenido_de_consola, CDStringBuilder constructor) {
        // Procesar cada línea del contenido de la consola
        for (String linea : contenido_de_consola.split("\n")) {
            // Verificar si la línea contiene el patrón esperado
            if (linea.contains("from pack KubeJS Resource Pack [data]") && linea.contains("Failed to parse ")) {
                try {
                    // Extraer el nombre del mod
                    String[] partes = linea.split("Failed to parse ");
                    if (partes.length > 1) { // Asegurarse de que hay algo después de "Failed to parse"
                        String mod_nombre = partes[1].split(":")[0]; // Extraer el nombre del mod antes de los dos puntos
                        constructor.append(MonitorDePID.idioma.kubeJSResourcePack(mod_nombre)).append(nl_html);
                        activado=true;
                    }
                } catch (Exception e) {
                    // Registrar errores en caso de problemas al procesar la línea
                    System.err.println("Error al procesar la línea: " + linea);
                    e.printStackTrace();
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
		// TODO Auto-generated method stub
		return activado;
	}
    
}