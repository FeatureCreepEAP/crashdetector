package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta plugins con dependencias faltantes en PocketMine-MP.Gracias a Aternos por que esta es una implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaDependenciaPluginPocketMine implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private final List<String> nombresPlugins = new ArrayList<>();
    private final List<String> dependencias = new ArrayList<>();

    /**
     * Verifica si el log contiene plugins con dependencias faltantes.
     */
    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contenido_verificar;

        // Patrón para detectar plugins con dependencias faltantes
        Pattern patron = Pattern.compile("Could not load plugin '(.*?)': Unknown dependency: (.*?)\\.");
        Matcher coincidencia = patron.matcher(contenido);

        while (coincidencia.find()) {
            String nombrePlugin = coincidencia.group(1).trim();
            String dependencia = coincidencia.group(2).trim();
            if (!nombrePlugin.isEmpty() && !dependencia.isEmpty()) {
                nombresPlugins.add(nombrePlugin);
                dependencias.add(dependencia);
            }
        }

        if (!nombresPlugins.isEmpty()) {
            if (nombresPlugins.size() > 1) {
                this.mensaje = MonitorDePID.idioma.mensajeDependenciaPluginFaltantePlural(nombresPlugins, dependencias);
            } else {
                this.mensaje = MonitorDePID.idioma.mensajeDependenciaPluginFaltanteSingular(nombresPlugins.get(0), dependencias.get(0));
            }
            activado = true;
        }
    }

    /**
     * Crea una nueva instancia del verificador.
     */
    @Override
    public Verificaciones nueva() {
        return new ProblemaDependenciaPluginPocketMine();
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
        return 900.0f;
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
        return MonitorDePID.idioma.nombreProblemaDependenciaPluginFaltante();
    }

    /**
     * Devuelve las soluciones posibles para este problema.
     */
    @Override
    public QuickFix solucion() {
        Builder builder = new Builder(nombre());

        for (int i = 0; i < nombresPlugins.size(); i++) {
            String nombre = nombresPlugins.get(i);
            String dependencia = dependencias.get(i);
            builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarPlugin(dependencia));
            builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(nombre));
        }

        return builder.construir();
    }
}