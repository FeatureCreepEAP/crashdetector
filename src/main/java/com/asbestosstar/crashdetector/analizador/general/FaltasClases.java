package com.asbestosstar.crashdetector.analizador.general;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

public class FaltasClases implements Verificaciones {

    private boolean activado = false;
    private final Set<String> clases = new HashSet<>();
    public final Set<String> todos = new LinkedHashSet<>();

    @Override
    public void verificar(Consola consola) {
        String contenidoConsola = consola.contento_verificar;
        VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;

        // Agregar clases faltantes desde stacktraces fatales
        for (String clase : vdst.fatal_clases_no_existe) {
            if (todos.add(clase)) {
                clases.add(clase.replace(".", "/"));
            }
        }

        // Procesar línea por línea la consola
        for (String linea : contenidoConsola.split(Verificaciones.nl)) {
            // Eliminar prefijo de log
            int indiceDosPuntos = linea.indexOf(':');
            if (indiceDosPuntos != -1 && linea.charAt(0) == '[') {
                linea = linea.substring(indiceDosPuntos + 1).trim();
            }

            // Saltar líneas con WARN (sin excepciones)
            if (linea.contains("/WARN]")||linea.contains("Warn")) {
                continue;
            }

            String clase = null;

            // Procesar errores de clase faltante
            if (linea.contains("java.lang.ClassNotFoundException:") || 
                linea.contains("java.lang.NoClassDefFoundError:")) {

                String[] llevas = {
                    "java.lang.ClassNotFoundException:",
                    "java.lang.NoClassDefFoundError:"
                };

                for (String lleva : llevas) {
                    int index = linea.indexOf(lleva);
                    if (index != -1) {
                        String candidate = linea.substring(index + lleva.length()).trim();
                        if (!candidate.isEmpty()) {
                            clase = candidate.split("[\\s\\)]")[0].trim();
                            break;
                        }
                    }
                }

            } else if (linea.contains("Error loading class:")) {
                // Este caso ya no debería ocurrir por el filtro anterior
                int index = linea.indexOf("Error loading class:");
                if (index != -1) {
                    String candidate = linea.substring(index + "Error loading class:".length()).trim();
                    if (!candidate.isEmpty()) {
                        clase = candidate.split("[\\s\\)]")[0].trim();
                    }
                }
            }

            // Validar formato de clase antes de agregarla
            if (clase != null && esNombreClaseValido(clase) && todos.add(clase)) {
                clases.add(clase);
            }
        }
        
        
        //TODO mejor
        for(String clase:clases) {
        	if(clase.startsWith("gg/essential/")||clase.startsWith("kotlin/")||clase.startsWith("kotlinx/")) {
        		clases.remove(clase);
        	}
        	
        	
        }
        
        

        activado = !clases.isEmpty();
    }

    // Validar que el nombre siga el patrón de una clase Java
    private boolean esNombreClaseValido(String clase) {
        return clase.matches("[a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)+");
    }

    @Override
    public Verificaciones nueva() {
        return new FaltasClases();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 925.0f; // Máxima prioridad para errores de clases faltantes
    }

    @Override
    public String mensaje() {
        if (clases.isEmpty()) return "";

        StringBuilder html = new StringBuilder("<ul>");
        for (String clase : clases) {
            html.append("<li>").append(clase).append("</li>");
        }
        html.append("</ul>");
        return MonitorDePID.idioma.faltar_de_clases_fatales() + html;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_de_faltar_de_clases();
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.solucionFaltasClases())
            .construir();
    }
}