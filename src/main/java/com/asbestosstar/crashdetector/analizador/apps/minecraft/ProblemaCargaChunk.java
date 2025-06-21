package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta excepciones al cargar chunks en el mundo.Gracias a Aternos por que esta es una implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaCargaChunk implements Verificaciones {

    private boolean activado = false;
    private String mensaje = ""; 

    /**
     * Verifica si el log contiene excepciones al cargar chunks.
     */
    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contento_verificar;

        // Patrones de error comunes al cargar chunks
        Pattern patron1 = Pattern.compile("Encountered an unexpected exception.*?at.*ChunkRegionLoader\\.loadChunk", Pattern.DOTALL);
        Pattern patron2 = Pattern.compile("Encountered an unexpected exception.*?at.*ChunkRegionLoader\\.loadEntities", Pattern.DOTALL);
        Pattern patron3 = Pattern.compile("Exception generating new chunk", Pattern.DOTALL);
        Pattern patron4 = Pattern.compile("Couldn't load chunk", Pattern.DOTALL);

        if (patron1.matcher(contenido).find() || 
            patron2.matcher(contenido).find() || 
            patron3.matcher(contenido).find() || 
            patron4.matcher(contenido).find()) {
            
            this.mensaje = MonitorDePID.idioma.mensajeCargaChunk() + Verificaciones.nl_html;
            activado = true;
        }
    }

    /**
     * Crea una nueva instancia del verificador.
     */
    @Override
    public Verificaciones nueva() {
        return new ProblemaCargaChunk();
    }

    /**
     * Indica si el problema fue detectado.
     */
    @Override
    public boolean activado() {
        return activado;
    }

    /**
     * Prioridad del problema (alta).
     */
    @Override
    public float prioridad() {
        return 1000.0f;
    }

    /**
     * Devuelve el mensaje de error almacenado.
     */
    @Override
    public String mensaje() {
        return mensaje;
    }

    /**
     * Devuelve el nombre del problema para mostrar en la interfaz.
     */
    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreProblemaCargaChunk();
    }

    /**
     * Devuelve las soluciones posibles para este problema.
     */
    @Override
    public QuickFix solucion() {
        return new Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.solucionRepararMundo("world"))
            .agregarEtiqueta(MonitorDePID.idioma.solucionEliminarCarpetaMundo("world"))
            .agregarEtiqueta(MonitorDePID.idioma.solucionEliminarChunk())
            .construir();
    }
}