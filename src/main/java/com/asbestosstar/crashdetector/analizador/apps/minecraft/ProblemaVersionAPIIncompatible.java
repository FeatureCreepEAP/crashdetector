package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que detecta plugins con versiones de API no compatibles.Gracias a Aternos por que esta es una implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaVersionAPIIncompatible implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String nombrePlugin = "";
    private String versionAPI = "";

    /**
     * Verifica si el log contiene errores de versión de API incompatible.
     */
    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contento_verificar;

        // Patrón para versiones menores a 1.20
        Pattern patronViejo = Pattern.compile(
            "Could not load 'plugins[/\\\\]([^']+)' in folder '?([^']*)'?\\n" +
            "org\\.bukkit\\.plugin\\.InvalidPluginException: Unsupported API version ([\\d\\.]+)",
            Pattern.DOTALL
        );

        // Patrón para versiones 1.20 o superiores
        Pattern patronNuevo = Pattern.compile(
            "Could not load plugin '((?!\\.jar).*\\.jar)' in folder '?([^']*)'?\\n" +
            "org\\.bukkit\\.plugin\\.InvalidPluginException: Unsupported API version ([\\d\\.]+)",
            Pattern.DOTALL
        );

        Matcher coincidenciaViejo = patronViejo.matcher(contenido);
        Matcher coincidenciaNuevo = patronNuevo.matcher(contenido);

        if (coincidenciaViejo.find()) {
            this.nombrePlugin = extraerNombrePlugin(coincidenciaViejo.group(1));
            this.versionAPI = coincidenciaViejo.group(3);
            this.mensaje = MonitorDePID.idioma.mensajeVersionAPIIncompatible(nombrePlugin, versionAPI) + Verificaciones.nl_html;
            activado = true;

        } else if (coincidenciaNuevo.find()) {
            this.nombrePlugin = extraerNombrePlugin(coincidenciaNuevo.group(1));
            this.versionAPI = coincidenciaNuevo.group(3);
            this.mensaje = MonitorDePID.idioma.mensajeVersionAPIIncompatible(nombrePlugin, versionAPI) + Verificaciones.nl_html;
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
        return new ProblemaVersionAPIIncompatible();
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
        return 800.0f;
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
        return MonitorDePID.idioma.nombreProblemaVersionAPIIncompatible();
    }

    /**
     * Devuelve las soluciones posibles para este problema.
     */
    @Override
    public QuickFix solucion() {
        return new Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionServidor(versionAPI))
            .construir();
    }
}