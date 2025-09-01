package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta intentos de cargar un mundo creado en una versión más reciente de Minecraft.Gracias a Aternos por que esta es una implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaVersionDowngrade implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";

    /**
     * Verifica si el log contiene errores de versión de Minecraft (intentar usar un mundo de una versión más reciente).
     */
    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contenido_verificar;

        // Patrón para detectar intentos de cargar un mundo de una versión más nueva
        Pattern patron = Pattern.compile(
            "java\\.lang\\.RuntimeException: Server attempted to load chunk saved with newer version of minecraft! (\\d+) > (\\d+)",
            Pattern.DOTALL
        );
        Matcher coincidencia = patron.matcher(contenido);

        if (coincidencia.find()) {
            this.mensaje = MonitorDePID.idioma.mensajeVersionDowngrade();
            activado = true;
        }
    }

    /**
     * Crea una nueva instancia del verificador.
     */
    @Override
    public Verificaciones nueva() {
        return new ProblemaVersionDowngrade();
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
        return 850.0f;
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
        return MonitorDePID.idioma.nombreProblemaVersionDowngrade();
    }

    /**
     * Devuelve las soluciones posibles para este problema.
     */
    @Override
    public QuickFix solucion() {
        return new Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.solucionVersionDowngrade())
            .agregarEtiqueta(MonitorDePID.idioma.solucionGenerarNuevoMundo())
            .construir();
    }
    
    @Override
    public boolean anularNormal() {
    	return true;
    }
    
    
    
    
}