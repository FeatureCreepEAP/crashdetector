package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;

/**
 * Analiza errores cuando una clase registrada como listener de eventos no contiene métodos válidos.
 * Detecta específicamente el error "No listeners found in class [nombre de clase]".
 * Utiliza Buscardor para identificar mods que contienen la clase problemática.
 */
public class ErrorSinListenersEnClase implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String nombreClase = "";
    private List<String> modsUbicacion = new ArrayList<>();

    @Override
    public void verificar(Consola consola) {
        String contenidoConsola = consola.contenido_verificar;

        // Analiza cada línea del registro buscando el patrón específico de error
        for (String linea : contenidoConsola.split(Verificaciones.nl)) {
            // Detecta el error específico de clase sin listeners
            if (linea.contains("No listeners found in class")) {
                
                // Extrae el nombre de la clase usando expresión regular
                Pattern pattern = Pattern.compile("No listeners found in class ([^\\s]+)");
                Matcher matcher = pattern.matcher(linea);
                if (matcher.find()) {
                    nombreClase = matcher.group(1);
                    
                    // Convierte el nombre de clase de notación con puntos a notación con barras
                    String classPath = nombreClase.replace('.', '/');
                    
                    // Busca mods que contienen esta clase
                    List<ArchivoDeMod> modsPotenciales = Buscardor.buscarModsConTermino(classPath);
                    
                    // Extrae las ubicaciones para publicar de cada mod encontrado
                    for (ArchivoDeMod mod : modsPotenciales) {
                        modsUbicacion.add(mod.ubicacion_para_publicar());
                    }
                    
                    mensaje = MonitorDePID.idioma.errorSinListenersEnClase(nombreClase, modsUbicacion) + Verificaciones.nl_html;
                    activado = true;
                    break; // Detiene al encontrar el primer error
                }
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorSinListenersEnClase();
    }

    @Override
    public boolean activado() {
        return activado; 
    }

    @Override
    public float prioridad() {
        return 880.0f; // Prioridad alta - error que impide registrar eventos del mod
    }

    @Override
    public String mensaje() {
        return mensaje; 
    }
    
    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_de_error_sin_listeners_en_clase();
    }
    
    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.paso1_sin_listeners_en_clase(nombreClase, modsUbicacion))
            .agregarEtiqueta(MonitorDePID.idioma.paso2_sin_listeners_en_clase(nombreClase))
            .agregarEtiqueta(MonitorDePID.idioma.paso3_sin_listeners_en_clase(nombreClase, modsUbicacion))
            .construir();
    }
}