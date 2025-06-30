package com.asbestosstar.crashdetector.limpiador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LimpiadorRegistroDeLauncherVainilla {

    /**
     * Limpia el contenido completo de la consola.
     * 
     * @param contento_de_consola Contenido completo de la consola
     * @return Contenido procesado sin corchetes ni etiquetas innecesarias
     */
    public static String limpiarConsola(String contento_de_consola) {
        // Dividir el contenido en líneas usando el separador del sistema
        String[] lineas_viejas = contento_de_consola.split(System.lineSeparator());
        
        // Limpiar cada línea individualmente
        List<String> lineas_limpias = limpiarLineas(Arrays.asList(lineas_viejas));
        
        // Unir las líneas limpias con el separador del sistema
        return String.join(System.lineSeparator(), lineas_limpias);
    }

    /**
     * Limpia una sola línea de registro.
     * 
     * @param linea Línea original del registro
     * @return Línea procesada sin elementos innecesarios
     */
    public static String limpiarLinea(String linea) {
        // Eliminar corchetes iniciales y su contenido
        if (linea.startsWith("[")) {
            int finCorchete = linea.indexOf(']');
            if (finCorchete != -1) {
                linea = linea.substring(finCorchete + 1);
            }
        }

        // Eliminar "Game/game () Info" y espacios previos
        linea = linea.replaceFirst("^\\s*Game/game \\(\\) Info", "");

        // Limpiar espacios y ajustar formato de stacktrace
        linea = linea.trim();
        if (linea.startsWith("at ")) {  // Verificar si es línea de stacktrace
            linea = " " + linea;        // Agregar espacio solo para "at"
        }

        return linea;
    }

    /**
     * Procesa múltiples líneas de registro.
     * 
     * @param lineas Lista de líneas originales
     * @return Lista de líneas procesadas (sin líneas vacías)
     */
    public static List<String> limpiarLineas(Iterable<String> lineas) {
        List<String> resultado = new ArrayList<>();
        for (String linea : lineas) {
            String lineaProcesada = limpiarLinea(linea);
            String trim = lineaProcesada.trim();
            if (!trim.isEmpty()) {
                resultado.add(lineaProcesada);
            }
        }
        return resultado;
    }
}