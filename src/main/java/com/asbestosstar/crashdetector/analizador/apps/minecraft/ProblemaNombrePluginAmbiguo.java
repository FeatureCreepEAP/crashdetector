package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que detecta plugins con nombres ambiguos en la carpeta 'plugins'.Gracias a Aternos por que esta es una implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaNombrePluginAmbiguo implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private List<String> nombresPlugins = new ArrayList<>();
    private List<String> primerosArchivos = new ArrayList<>();
    private List<String> segundosArchivos = new ArrayList<>();

    /**
     * Verifica si el log contiene errores de nombre ambiguo de plugins.
     */
    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contenido_verificar;
        String[] lineas = contenido.split("\n");

        // Patrón mejorado para manejar cualquier combinación de comillas
        Pattern patron = Pattern.compile("Ambiguous plugin name [`']([^`']*)[`'].*?files [`']plugins/([^`']*)[`'].*?and [`']plugins/([^`']*)[`']");

        for (String linea : lineas) {
            Matcher coincidencia = patron.matcher(linea.trim());

            if (coincidencia.find()) {
                nombresPlugins.add(coincidencia.group(1));
                primerosArchivos.add(extraerNombrePlugin(coincidencia.group(2)));
                segundosArchivos.add(extraerNombrePlugin(coincidencia.group(3)));
            }
        }

        if (!nombresPlugins.isEmpty()) {
            StringBuilder mensajeBuilder = new StringBuilder();
            
            for (int i = 0; i < nombresPlugins.size(); i++) {
                mensajeBuilder.append(MonitorDePID.idioma.mensajeNombrePluginAmbiguo(
                    nombresPlugins.get(i),
                    primerosArchivos.get(i),
                    segundosArchivos.get(i)
                )).append("<br>");
            }

            this.mensaje = mensajeBuilder.toString();
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
        Builder builder = new Builder(nombre());
        
        for (int i = 0; i < primerosArchivos.size(); i++) {
            builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(primerosArchivos.get(i)));
            builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(segundosArchivos.get(i)));
        }
        
        return builder.construir();
    }
}