package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que detecta dependencias faltantes o versiones incorrectas en mods de Fabric.
 */
public class ProblemaDependenciaModFabric implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private final List<String> nombresMods = new ArrayList<>();
    private final List<String> dependencias = new ArrayList<>();
    private final List<String> versiones = new ArrayList<>();

    /**
     * Verifica si el log contiene dependencias faltantes o versiones incorrectas en mods de Fabric.
     */
    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contento_verificar;
        String[] lineas = contenido.split("\n");

        boolean enDetalles = false;

        for (String linea : lineas) {
            // Buscar el inicio de la seccion de detalles
            if (linea.trim().equals("More details:")) {
                enDetalles = true;
                continue;
            }

            if (!enDetalles) continue;
            
            // Salir del bucle si ya no estamos en la seccion de detalles
            if (linea.trim().startsWith("at ") || linea.trim().startsWith("Caused by:")) {
                break;
            }

            // Procesar solo lineas que contienen errores de dependencia
            if (linea.contains("- Mod ")) {
                procesarLineaError(linea.trim());
            }
        }

        // Generar mensaje si se encontraron problemas
        if (!nombresMods.isEmpty()) {
            StringBuilder mensajeBuilder = new StringBuilder();
            
            for (int i = 0; i < nombresMods.size(); i++) {
                mensajeBuilder.append(MonitorDePID.idioma.mensajeDependenciaModFaltante(
                    nombresMods.get(i), 
                    dependencias.get(i),
                    versiones.get(i)
                )).append("<br><br>");
            }

            this.mensaje = mensajeBuilder.toString().replaceAll("<br><br>$", "");
            activado = true;
        }
    }

    /**
     * Procesa una linea de error y extrae informacion relevante.
     */
    private void procesarLineaError(String linea) {
        // Ejemplo: - Mod 'Indium' (indium) 1.0.27+mc1.20.1 requires version 0.5.3 of mod 'Sodium' (sodium), but only the wrong version is present: 0.4.10+build.27!
        // Ejemplo: - Mod 'Indium' (indium) 1.0.27+mc1.20.1 requires version 3.2.0 or later of fabric-renderer-api-v1, which is missing!

        // Extraer nombre del mod (ID interno)
        int inicioModID = linea.indexOf("(");
        int finModID = linea.indexOf(")");
        String nombreMod = "";
        if (inicioModID > 0 && finModID > inicioModID) {
            nombreMod = linea.substring(inicioModID + 1, finModID);
        }

        // Procesar solo si hay un mod identificado
        if (!nombreMod.isEmpty()) {
            if (linea.contains("requires version")) {
                String dependencia = "";
                String version = "";
                
                // Caso: version especifica requerida
                if (linea.contains("mod '")) {
                    int inicioDep = linea.indexOf("mod '") + 5;
                    int finDep = linea.indexOf("'", inicioDep);
                    if (inicioDep > 0 && finDep > inicioDep) {
                        dependencia = linea.substring(inicioDep, finDep);
                    }
                    
                    int inicioVer = linea.indexOf("version ") + 8;
                    int finVer = linea.indexOf(" of", inicioVer);
                    if (inicioVer > 0 && finVer > inicioVer) {
                        version = linea.substring(inicioVer, finVer);
                    }
                    
                    // Detectar version actual si existe
                    if (!version.isEmpty() && linea.contains("present:")) {
                        int inicioActual = linea.indexOf("present:") + 9;
                        int finActual = linea.indexOf("!", inicioActual);
                        if (inicioActual > 0) {
                            String versionActual = linea.substring(inicioActual, 
                                (finActual > 0) ? finActual : linea.length()).trim();
                            versiones.add("requiere " + version + ", encontrada " + versionActual);
                        } else {
                            versiones.add("requiere version " + version);
                        }
                    } 
                    // Detectar version minima requerida
                    else if (!version.isEmpty() && linea.contains("or later")) {
                        versiones.add("version minima " + version + " requerida");
                    } 
                    // Solo dependencia sin version especifica
                    else if (!dependencia.isEmpty() && linea.contains("which is missing")) {
                        versiones.add("no encontrada");
                    }
                    
                    // Extraer nombre de la dependencia si se encuentra
                    if (!dependencia.isEmpty()) {
                        dependencias.add(dependencia);
                        nombresMods.add(nombreMod);
                    }
                }
            }
        }
    }

    /**
     * Crea una nueva instancia del verificador.
     */
    @Override
    public Verificaciones nueva() {
        return new ProblemaDependenciaModFabric();
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
        return MonitorDePID.idioma.nombreProblemaDependenciaMod();
    }

    /**
     * Devuelve las soluciones posibles para este problema.
     */
    @Override
    public QuickFix solucion() {
        Builder builder = new Builder(nombre());
        
        for (int i = 0; i < dependencias.size(); i++) {
            String dep = dependencias.get(i);
            String ver = versiones.get(i);
            
            if (ver.contains("requiere")) {
                // Caso: version incorrecta
                String versionRequerida = ver.split(",")[0].replace("requiere ", "").trim();
                builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarModConVersion(dep, versionRequerida));
            } else if (ver.contains("minima")) {
                // Caso: version minima requerida
                String versionMinima = ver.replace("version minima ", "").replace(" requerida", "").trim();
                builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarModConVersion(dep, versionMinima));
            } else if (ver.contains("no encontrada")) {
                // Caso: dependencia completamente faltante
                builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarMod(dep));
            } else {
                // Caso general
                builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarMod(dep));
            }
        }
        
        return builder.construir();
    }
}