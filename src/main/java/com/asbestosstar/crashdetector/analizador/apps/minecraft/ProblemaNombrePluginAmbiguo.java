package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que detecta plugins con nombres ambiguos en la carpeta 'plugins'. Gracias a Aternos por que esta es una implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaNombrePluginAmbiguo implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String nombrePlugin = "";
    private String primerPath = "";
    private String segundoPath = "";

    /**
     * Verifica si el log contiene el error de nombre ambiguo de plugin.
     */
    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contento_verificar;

        // Patrón de error: "Ambiguous plugin name 'Nombre' for files 'plugins/...' and 'plugins/...'"
        Pattern patron = Pattern.compile("Ambiguous plugin name '([^']+)'.*?files '(plugins/[^']+)'.*?'(plugins/[^']+)'.*?plugins");
        Matcher coincidencia = patron.matcher(contenido);

        if (coincidencia.find()) {
            this.nombrePlugin = coincidencia.group(1);
            this.primerPath = extraerNombrePlugin(coincidencia.group(2));
            this.segundoPath = extraerNombrePlugin(coincidencia.group(3));
            
            this.mensaje = MonitorDePID.idioma.mensajeNombrePluginAmbiguo(nombrePlugin, primerPath, segundoPath) + Verificaciones.nl_html;
            activado = true;
        }
    }

    /**
     * Extrae solo el nombre del archivo del path completo.
     */
    private String extraerNombrePlugin(String path) {
        int indiceUltimaBarra = path.lastIndexOf("/");
        return (indiceUltimaBarra != -1) ? path.substring(indiceUltimaBarra + 1) : path;
    }

    /**
     * Crea una nueva instancia del verificador.
     */
    @Override
    public Verificaciones nueva() {
        return new ProblemaNombrePluginAmbiguo();
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
        return 700.0f;
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
        return MonitorDePID.idioma.nombreProblemaNombrePluginAmbiguo();
    }

    /**
     * Devuelve las soluciones posibles para este problema.
     */
    @Override
    public QuickFix solucion() {
        return new Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(primerPath))
            .agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(segundoPath))
            .construir();
    }
}