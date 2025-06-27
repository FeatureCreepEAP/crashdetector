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
 * Clase que detecta plugins con versiones de API no compatibles.Gracias a Aternos por que esta es una implementacion de su codex https://github.com/aternosorg/codex-minecraft   
 */
public class ProblemaVersionAPIIncompatible implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private final List<String> nombresPlugins = new ArrayList<>();
    private final List<String> versionesAPI = new ArrayList<>();

    // Patrones para versiones antiguas
    private static final Pattern PATRON_VIEJO = Pattern.compile(
        "Could not load 'plugins[/\\\\]([^']+)' in folder '?([^']*)'?\\n" +
        "org\\.bukkit\\.plugin\\.InvalidPluginException: Unsupported API version ([\\d\\.]+)",
        Pattern.DOTALL
    );

    private static final Pattern PATRON_NUEVO = Pattern.compile(
        "Could not load plugin '((?!\\.jar).*\\.jar)' in folder '?([^']*)'?\\n" +
        "org\\.bukkit\\.plugin\\.InvalidPluginException: Unsupported API version ([\\d\\.]+)",
        Pattern.DOTALL
    );

    private static final Pattern PATRON_PAPER_NUEVO = Pattern.compile(
        "Could not load plugin '([^']+)' in folder.*?\\n.*?\\n.*?\\n" +
        "Plugin API version ([\\d\\.]+) is lower than the minimum allowed version",
        Pattern.DOTALL
    );

    private static final Pattern PATRON_PAPER_MENSAJE = Pattern.compile(
        "Could not load plugin '([^']+)' in folder.*?\\n.*?\\n.*?\\n" +
        "Plugin API version ([\\d\\.]+) is not supported by this server",
        Pattern.DOTALL
    );

    /**
     * Verifica si el log contiene errores de versión de API incompatible.
     */
    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contento_verificar;
        
        // 1. Usar regex para formatos antiguos
        procesarCoincidenciasRegex(PATRON_VIEJO.matcher(contenido));
        procesarCoincidenciasRegex(PATRON_NUEVO.matcher(contenido));
        procesarCoincidenciasRegex(PATRON_PAPER_NUEVO.matcher(contenido));
        procesarCoincidenciasRegex(PATRON_PAPER_MENSAJE.matcher(contenido));

        // 2. Procesar el nuevo formato de Paper 1.20.6+ sin regex
        procesarFormatoPaperNuevo(consola);

        // 3. Generar mensaje si se encontraron plugins problemáticos
        if (!nombresPlugins.isEmpty()) {
            StringBuilder mensajeBuilder = new StringBuilder();
            
            for (int i = 0; i < nombresPlugins.size(); i++) {
                mensajeBuilder.append(MonitorDePID.idioma.mensajeVersionAPIIncompatible(
                    nombresPlugins.get(i), 
                    versionesAPI.get(i)
                )).append("<br><br>");
            }

            this.mensaje = mensajeBuilder.toString();
            activado = true;
        }
    }

    /**
     * Procesa coincidencias usando regex.
     */
    private void procesarCoincidenciasRegex(Matcher coincidencia) {
        while (coincidencia.find()) {
            if (coincidencia.groupCount() >= 2) {
                nombresPlugins.add(extraerNombrePlugin(coincidencia.group(1)));
                versionesAPI.add(coincidencia.group(2));
            }
        }
    }

    /**
     * Procesa el nuevo formato de Paper 1.20.6+ sin usar regex específicos.
     */
    private void procesarFormatoPaperNuevo(Consola consola) {
        String[] lineas = consola.contento_verificar.split("\n");
        
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();

            // Busca líneas que contengan la carga fallida de un plugin
            if (linea.contains("Could not load plugin") && linea.contains(".jar")) {
                procesarErrorPaper1206(lineas, i);
            }
        }
    }

    /**
     * Procesa errores específicos del nuevo formato de Paper 1.20.6+
     */
    private void procesarErrorPaper1206(String[] lineas, int indiceLinea) {
        String lineaPrincipal = lineas[indiceLinea].trim();

        // Extrae el nombre del plugin entre comillas simples
        int inicioNombre = lineaPrincipal.indexOf("'") + 1;
        int finNombre = lineaPrincipal.indexOf("'", inicioNombre);
        if (inicioNombre <= 0 || finNombre <= inicioNombre) return;

        String nombrePlugin = extraerNombrePlugin(lineaPrincipal.substring(inicioNombre, finNombre));

        // Busca la línea con el mensaje de versión incompatible
        for (int j = indiceLinea + 1; j < Math.min(indiceLinea + 5, lineas.length); j++) {
            String lineaError = lineas[j].trim();

            if (lineaError.contains("Plugin API version")) {
                // Extrae la versión de API
                int inicioVersion = lineaError.indexOf("Plugin API version ") + 21;
                int finVersion = lineaError.indexOf(" ", inicioVersion);
                if (inicioVersion <= 0 || finVersion <= inicioVersion) continue;

                String version = lineaError.substring(inicioVersion, finVersion);

                if (!nombrePlugin.isEmpty() && !version.isEmpty()) {
                    nombresPlugins.add(nombrePlugin);
                    versionesAPI.add(version);
                }
                break;
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
        Builder builder = new Builder(nombre());
        for (int i = 0; i < nombresPlugins.size(); i++) {
            builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionServidor(versionesAPI.get(i)));
            builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPlugin(nombresPlugins.get(i)));
        }
        return builder.construir();
    }
}