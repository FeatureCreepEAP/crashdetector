package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que detecta plugins que no son compatibles con ticking regional en Folia. Gracias a Aternos por que esta es una implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaTickingRegionalPlugin implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private final List<String> nombresPlugins = new ArrayList<>();
    private final List<String> rutasPlugins = new ArrayList<>();

    /**
     * Verifica si el log contiene plugins no compatibles con ticking regional en Folia.
     */
    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contento_verificar;

        // Patrón para detectar plugins que no soportan ticking regional
        Pattern patron = Pattern.compile(
            "Could not load plugin '([^']+)'.*?in folder '([^']+)'.*?" +
            "not marked as supporting regionised multithreading",
            Pattern.DOTALL
        );
        Matcher coincidencia = patron.matcher(contenido);

        while (coincidencia.find()) {
            String rutaCompleta = coincidencia.group(1); // Ej: plugins/worldedit-bukkit-7.3.4-beta-01.jar
            String carpeta = coincidencia.group(2);     // Ej: plugins
            String nombrePlugin = extraerNombrePlugin(rutaCompleta);

            this.nombresPlugins.add(nombrePlugin);
            this.rutasPlugins.add(rutaCompleta);
        }

        if (!nombresPlugins.isEmpty()) {
            if (nombresPlugins.size() > 1) {
                this.mensaje = MonitorDePID.idioma.mensajePluginTickingRegionalPlural(nombresPlugins);
            } else {
                this.mensaje = MonitorDePID.idioma.mensajePluginTickingRegionalSingular(nombresPlugins.get(0));
            }
            activado = true;
        }
    }

    /**
     * Extrae el nombre del plugin desde la ruta completa.
     */
    private String extraerNombrePlugin(String ruta) {
        int indiceUltimaBarra = ruta.lastIndexOf("/");
        return (indiceUltimaBarra != -1) ? ruta.substring(indiceUltimaBarra + 1) : ruta;
    }

    /**
     * Crea una nueva instancia del verificador.
     */
    @Override
    public Verificaciones nueva() {
        return new ProblemaTickingRegionalPlugin();
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
        return MonitorDePID.idioma.nombreProblemaPluginTickingRegional();
    }

    /**
     * Devuelve las soluciones posibles para este problema.
     */
    @Override
    public QuickFix solucion() {
        Builder builder = new Builder(nombre());

        // Soluciones para cada plugin afectado
        for (int i = 0; i < nombresPlugins.size(); i++) {
            String nombre = nombresPlugins.get(i);
            String ruta = rutasPlugins.get(i);

            builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionDiferentePlugin(nombre));
            builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(ruta));
        }

        // Solución general para cambiar de software
        builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarSoftwareSinTickingRegional("Paper"));

        return builder.construir();
    }
}