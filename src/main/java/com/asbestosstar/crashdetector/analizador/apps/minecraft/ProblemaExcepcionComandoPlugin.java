package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que detecta excepciones al ejecutar comandos de plugins. Gracias a Aternos por que esta es una implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaExcepcionComandoPlugin implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String nombrePlugin = "";
    private String comando = "";

    /**
     * Verifica si el log contiene una excepción al ejecutar un comando de plugin.
     */
    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contento_verificar;

        // Patrón de error: "org.bukkit.command.CommandException: Cannot execute command 'nombre' in plugin Plugin"
        Pattern patron = Pattern.compile("org\\.bukkit\\.command\\.CommandException: Cannot execute command '([^']+)' in plugin (\\w+)");
        Matcher coincidencia = patron.matcher(contenido);

        if (coincidencia.find()) {
            this.nombrePlugin = coincidencia.group(2);
            this.comando = coincidencia.group(1);
            
            this.mensaje = MonitorDePID.idioma.mensajeExcepcionComandoPlugin(nombrePlugin, comando) + Verificaciones.nl_html;
            activado = true;
        }
    }

    /**
     * Crea una nueva instancia del verificador.
     */
    @Override
    public Verificaciones nueva() {
        return new ProblemaExcepcionComandoPlugin();
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
        return 500.0f;
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
        return MonitorDePID.idioma.nombreProblemaExcepcionComandoPlugin();
    }

    /**
     * Devuelve las soluciones posibles para este problema.
     */
    @Override
    public QuickFix solucion() {
        return new Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionDiferentePlugin(nombrePlugin))
            .agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(nombrePlugin))
            .construir();
    }
}