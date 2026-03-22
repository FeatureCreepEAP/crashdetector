package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Detecta errores de restricciones de dependencias (JarInJar) en Forge.
 * 
 * Agrupa los conflictos por Archivo JAR para mostrar qué mods solicitan qué
 * dependencias internas.
 *
 * Ejemplo: net.minecraftforge.fml.loading.EarlyLoadingException: 3 Dependency restrictions were not met.
 */
public class RestriccionesDependenciaNoCumplidas implements Verificaciones {

    private boolean activado = false;
    private String enlace = "";
    private String cantidad = "varias";
    // Mapa: Nombre del Archivo -> Lista de dependencias solicitadas
    private Map<String, List<String>> conflictosPorMod = new HashMap<>();

    @Override
    public void verificar(Consola consola) {
        String log = consola.contenido_verificar;
        if (log == null) return;

        // Paso 1: Buscar la línea de la excepción principal
        int indexException = log.indexOf("EarlyLoadingException");
        if (indexException == -1) return;

        // Extraer la cantidad de errores
        int lineEnd = log.indexOf("\n", indexException);
        String exceptionLine = lineEnd > indexException 
                ? log.substring(indexException, lineEnd) 
                : log.substring(indexException);
        
        if (exceptionLine.contains("Dependency restrictions were not met")) {
            int colonIndex = exceptionLine.indexOf(": ");
            if (colonIndex != -1) {
                String temp = exceptionLine.substring(colonIndex + 2);
                String[] partes = temp.split(" ");
                if (partes.length > 0) {
                    this.cantidad = partes[0];
                }
            }
        } else {
            return;
        }

        // Paso 2: Localizar el bloque de error detallado
        int selectJarsStart = log.lastIndexOf("[main/ERROR]: Failed to select jars", indexException);
        if (selectJarsStart == -1) {
            selectJarsStart = log.lastIndexOf("ResolutionFailureInformation", indexException);
            if (selectJarsStart != -1) {
                selectJarsStart = log.lastIndexOf("\n", selectJarsStart) + 1;
            }
        }

        if (selectJarsStart != -1 && selectJarsStart < indexException) {
            int selectJarsEnd = log.indexOf("\n", selectJarsStart);
            String errorContent = log.substring(selectJarsStart, selectJarsEnd != -1 ? selectJarsEnd : log.length());

            parsearConflictosAgrupados(errorContent);
            activado = true;
            this.enlace = consola.agregarErrorALectador(log.substring(0, indexException).split("\n").length, this);
        }
    }
    
    private void parsearConflictosAgrupados(String texto) {
        conflictosPorMod.clear();
        
        // Dividir por bloques de información de resolución
        String[] bloques = texto.split("ResolutionFailureInformation\\{");
        
        for (String bloque : bloques) {
            if (!bloque.contains("artifact=") || !bloque.contains("Mod File:")) continue;
            
            String dependencia = "Desconocida";
            
            // 1. Extraer Nombre de la Dependencia
            int idxArt = bloque.indexOf("artifact=");
            if (idxArt != -1) {
                String sub = bloque.substring(idxArt + 8);
                int fin = sub.indexOf("]");
                int coma = sub.indexOf(",");
                int minFin = Math.min(fin != -1 ? fin : 9999, coma != -1 ? coma : 9999);
                if (minFin != 9999) {
                    dependencia = sub.substring(0, minFin).trim();
                }
            }
            
            // 2. Buscar TODOS los archivos que referencian esta dependencia en este bloque
            int idxSource = 0;
            while ((idxSource = bloque.indexOf("Mod File:", idxSource)) != -1) {
                String sub = bloque.substring(idxSource + 9);
                int fin = sub.indexOf("]");
                int coma = sub.indexOf(",");
                int minFin = Math.min(fin != -1 ? fin : 9999, coma != -1 ? coma : 9999);
                
                String archivo = "Desconocido";
                if (minFin != 9999) {
                    archivo = sub.substring(0, minFin).trim();
                }

                // Limpiar ruta
                if (archivo.contains("\\")) archivo = archivo.substring(archivo.lastIndexOf("\\") + 1);
                else if (archivo.contains("/")) archivo = archivo.substring(archivo.lastIndexOf("/") + 1);

                // Agregar al Mapa
                if (!archivo.isEmpty()) {
                    conflictosPorMod.computeIfAbsent(archivo, k -> new ArrayList<>());
                    // Evitar duplicados si el log es redundante
                    List<String> deps = conflictosPorMod.get(archivo);
                    if (!deps.contains(dependencia)) {
                        deps.add(dependencia);
                    }
                }
                idxSource += 9;
            }
        }
    }

    @Override
    public void verificar(Consola consola, String linea, int numero_de_linea) {
        // No se usa en este enfoque
    }

    @Override
    public Verificaciones nueva() {
        return new RestriccionesDependenciaNoCumplidas();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1200.0f;
    }

    @Override
    public String mensaje() {
        return MonitorDePID.idioma.mensajeRestriccionesDependenciaNoCumplidas(cantidad, conflictosPorMod) + this.enlace;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreRestriccionesDependenciaNoCumplidas();
    }

    @Override
    public QuickFix solucion() {
        return QuickFix.NINGUN;
    }

    @Override
    public String id() {
        return "restricciones_dependencia_no_cumplidas";
    }

    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        return false;
    }

    @Override
    public Documento docs() {
        return Documento.NINGUN;
    }

    @Override
    public String enlaceACodigo() {
        return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
                + this.getClass().getSimpleName() + ".java";
    }
}