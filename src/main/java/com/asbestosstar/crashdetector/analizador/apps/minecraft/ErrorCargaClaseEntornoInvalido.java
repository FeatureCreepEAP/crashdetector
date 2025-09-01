package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Analiza errores cuando un mod se está utilizando en una plataforma incorrecta (cliente/servidor).
 * Detecta específicamente el error "Attempted to load class [...] for invalid dist [CLIENT|SERVER]".
 * Incluye sugerencias para usar ModsTree (Arbol de Mods) para identificar el mod problemático.
 */
public class ErrorCargaClaseEntornoInvalido implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String nombreClase = "";
    private String entornoInvalido = "";

    @Override
    public void verificar(Consola consola) {
        String contenidoConsola = consola.contenido_verificar;

        // Analiza cada línea del registro buscando el patrón específico de error
        for (String linea : contenidoConsola.split(Verificaciones.nl)) {
            // Detecta el error específico de carga en entorno incorrecto
            if (linea.contains("Attempted to load class") && 
                linea.contains("for invalid dist")) {
                
                // Extrae el nombre de la clase y el entorno usando expresión regular
                Pattern pattern = Pattern.compile("Attempted to load class ([^ ]+) for invalid dist (CLIENT|SERVER)");
                Matcher matcher = pattern.matcher(linea);
                if (matcher.find()) {
                    nombreClase = matcher.group(1);
                    entornoInvalido = matcher.group(2);
                    
                    mensaje = MonitorDePID.idioma.errorModEnPlataformaIncorrecta(nombreClase, entornoInvalido) + Verificaciones.nl_html;
                    activado = true;
                    break;
                }
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorCargaClaseEntornoInvalido();
    }

    @Override
    public boolean activado() {
        return activado; 
    }

    @Override
    public float prioridad() {
        return 850.0f; // Prioridad alta - error que impide iniciar el juego
    }

    @Override
    public String mensaje() {
        return mensaje; 
    }
    
    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_de_error_mod_plataforma_incorrecta();
    }
    
    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.paso1_mod_plataforma_incorrecta(nombreClase, entornoInvalido))
            .agregarEtiqueta(MonitorDePID.idioma.paso2_mod_plataforma_incorrecta(entornoInvalido))
            .agregarEtiqueta(MonitorDePID.idioma.paso3_mod_plataforma_incorrecta())
            .construir();
    }
}