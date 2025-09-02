package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Analiza errores críticos relacionados con plugins de JEI (Just Enough Items) que causan crashes.
 * Detecta específicamente el error "Caught an error from mod plugin: class [clase] [modid]:[plugin_id]".
 * Este error puede causar crashes durante la inicialización del juego.
 */
public class ErrorJEIPluginFallido implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String nombreClase = "";
    private String modId = "";
    private String pluginId = "";

    @Override
    public void verificar(Consola consola) {
        String contenidoConsola = consola.contenido_verificar;

        // Analiza cada línea del registro buscando el patrón específico de error de plugin JEI
        for (String linea : contenidoConsola.split(Verificaciones.nl)) {
            // Detecta el error específico de plugin JEI fallido
            if (linea.contains("Caught an error from mod plugin: class")) {
                
                // Extrae la información usando expresión regular
                Pattern pattern = Pattern.compile("Caught an error from mod plugin: class ([^ ]+) ([^:]+):([^ ]+)");
                Matcher matcher = pattern.matcher(linea);
                if (matcher.find()) {
                    nombreClase = matcher.group(1);
                    modId = matcher.group(2);
                    pluginId = matcher.group(3);
                    
                    mensaje = MonitorDePID.idioma.errorJEIPluginFallido(nombreClase, modId, pluginId) + Verificaciones.nl_html;
                    activado = true;
                    break; // Detiene al encontrar el primer error
                }
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorJEIPluginFallido();
    }

    @Override
    public boolean activado() {
        return activado; 
    }

    @Override
    public float prioridad() {
        return 950.0f; // Máxima prioridad - error crítico que causa crashes
    }

    @Override
    public String mensaje() {
        return mensaje; 
    }
    
    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_de_error_jei_plugin_fallido();
    }
    
    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.paso1_jei_plugin_fallido(modId))
            .agregarEtiqueta(MonitorDePID.idioma.paso2_jei_plugin_fallido(modId))
            .agregarEtiqueta(MonitorDePID.idioma.paso3_jei_plugin_fallido(modId))
            .construir();
    }
}