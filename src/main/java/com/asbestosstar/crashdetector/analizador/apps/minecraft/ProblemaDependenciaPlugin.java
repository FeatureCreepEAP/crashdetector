package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta errores de dependencias faltantes en plugins Bukkit.Gracias a Aternos por que esta es una implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaDependenciaPlugin implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String nombrePlugin = "";
    private final List<String> dependencias = new ArrayList<>();
    private String rutaPlugin = "";

    /**
     * Verifica si el log contiene errores de dependencias faltantes.
     */
    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contento_verificar;
        String[] lineas = contenido.split("\n"); // Divide por líneas

        for (String linea : lineas) {
            // Busca la línea que contiene el mensaje de dependencia faltante
            if (linea.contains("Unknown/missing dependency plugins:")) {
                // Extrae la parte que contiene las dependencias
                int inicio = linea.indexOf('[') + 1;
                int fin = linea.indexOf(']');
                
                if (inicio > 0 && fin > inicio) {
                    String depsTexto = linea.substring(inicio, fin);
                    String[] deps = depsTexto.split(", ");

                    for (String dep : deps) {
                        dependencias.add(dep.trim());
                    }

                    // Extrae el nombre del plugin desde la ruta
                    int inicioRuta = linea.indexOf("plugins") + "plugins".length();
                    int finRuta = linea.indexOf(".jar") + ".jar".length();
                    
                    if (inicioRuta > 0 && finRuta > inicioRuta) {
                        String rutaRelativa = linea.substring(inicioRuta, finRuta);
                        this.nombrePlugin = extraerNombrePlugin(rutaRelativa);
                    }

                    this.mensaje = MonitorDePID.idioma.mensajeDependenciaPluginMultiples(nombrePlugin, dependencias);
                    activado = true;
                }
            } else if (linea.contains("Unknown dependency")) {
                // Caso de dependencia única
                if (linea.contains("plugins")) {
                    int inicioRuta = linea.indexOf("plugins") + "plugins".length();
                    int finRuta = linea.indexOf(".jar") + ".jar".length();

                    if (inicioRuta > 0 && finRuta > inicioRuta) {
                        String rutaRelativa = linea.substring(inicioRuta, finRuta);
                        this.nombrePlugin = extraerNombrePlugin(rutaRelativa);
                    }

                    // Extrae el nombre de la dependencia faltante
                    int inicioDep = linea.indexOf("Unknown dependency ") + "Unknown dependency ".length();
                    int finDep = linea.indexOf(".", inicioDep);

                    if (inicioDep > 0 && finDep > inicioDep) {
                        String dependencia = linea.substring(inicioDep, finDep).trim();
                        dependencias.add(dependencia);
                    }

                    this.mensaje = MonitorDePID.idioma.mensajeDependenciaPluginUnica(nombrePlugin, dependencias.get(0));
                    activado = true;
                }
            }
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
        return new ProblemaDependenciaPlugin();
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
        return MonitorDePID.idioma.nombreProblemaDependenciaPlugin();
    }

    /**
     * Devuelve las soluciones posibles para este problema.
     */
    @Override
    public QuickFix solucion() {
        Builder builder = new Builder(nombre());

        // Solución para eliminar el plugin si tiene dependencias faltantes
        if (!rutaPlugin.isEmpty()) {
            builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(nombrePlugin));
        }

        // Soluciones para instalar cada dependencia faltante
        for (String dependencia : dependencias) {
            builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarPlugin(dependencia));
        }

        return builder.construir();
    }
}