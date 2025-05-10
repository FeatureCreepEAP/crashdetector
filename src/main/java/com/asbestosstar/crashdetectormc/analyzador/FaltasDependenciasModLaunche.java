package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class FaltasDependenciasModLaunche implements Verificaciones {

    public boolean activado = false;

    @Override
    public void verificar(String str, CDStringBuilder messanje) {
        String[] lineas = str.split("\r?\n");
        
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();
            
            // Check for new error format (Mod X only supports Y version Z or above)
            if (linea.contains("only supports") && linea.contains("or above")) {
                // Extract mod ID and dependency
                String modId = extraerModId(linea);
                String dependencia = extraerDependencia(linea);
                String versionRequerida = extraerVersionRequerida(linea);
                
                // Check next line for current version
                if (i+1 < lineas.length) {
                    String lineaSiguiente = lineas[i+1].trim();
                    if (lineaSiguiente.startsWith("Currently,") && lineaSiguiente.contains("is")) {
                        String versionActual = extraerVersionActual(lineaSiguiente, dependencia);
                        
                        // Add error message
                        messanje.append(
                            MonitorDePID.idioma.errorVersionDependencia(
                                modId, dependencia, versionRequerida, versionActual
                            )
                        ).append(nl_html);
                        
                        activado = true;
                    }
                }
            }
            
            else if (linea.contains("Missing or unsupported mandatory dependencies:")) {
                StringBuilder out = new StringBuilder(
                    MonitorDePID.idioma.no_tienes_las_dependencias_necesitas()
                ).append(nl_html);
                
                for (String line : lineas) {
                    if (line.contains("Mod ID") && line.contains("Requested by") && line.contains("Expected range")) {
                        out.append(MonitorDePID.idioma.linea_de_dependencia(line)).append(nl_html);
                    }
                }
                messanje.append(nl_html).append(out.toString());
                activado = true;
            }
        }
    }

    // Helper methods for string parsing
    private String extraerModId(String linea) {
        int inicio = linea.indexOf("Mod ") + 4; // "Mod ".length()
        int fin = linea.indexOf(" only supports");
        return limpiarFormato(linea.substring(inicio, fin));
    }

    private String extraerDependencia(String linea) {
        int inicio = linea.indexOf("supports ") + 9; // "supports ".length()
        int fin = linea.indexOf(" ", inicio);
        return limpiarFormato(linea.substring(inicio, fin));
    }

    private String extraerVersionRequerida(String linea) {
        int inicio = linea.indexOf("supports ") + 9;
        int fin = linea.indexOf(" or above");
        return limpiarFormato(linea.substring(inicio, fin));
    }

    private String extraerVersionActual(String linea, String dependencia) {
        int inicio = linea.indexOf("is ") + 3; // "is ".length()
        return limpiarFormato(linea.substring(inicio));
    }

    private String limpiarFormato(String texto) {
        return texto.replaceAll("§[a-zA-Z0-9]", "");
    }

    @Override
    public Verificaciones nueva() {
        return new FaltasDependenciasModLaunche();
    }

    @Override
    public boolean activado() {
        return activado;
    }
}