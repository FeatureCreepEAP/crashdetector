package com.asbestosstar.crashdetector.limpiador;

public class LimpiadorRegistroLatestLog {

    public static String limpiarConsola(String contenido) {
        if (contenido == null || contenido.isEmpty()) {
            return contenido;
        }

        String[] lineas = contenido.split("\n");
        StringBuilder resultado = new StringBuilder();

        String error1 = "[java.lang.ThreadGroup:uncaughtException:";
        String error2 = "[java.lang.Throwable:printStackTrace:";

        for (String linea : lineas) {
            String lineaOriginal = linea;

            // Buscar el final del prefijo (ej: [19:09:14] [main/INFO]: )
            int indicePrefijo = linea.indexOf("]: ");
            if (indicePrefijo != -1) {
                // Extraer contenido después del prefijo
                String contenidoDespuesPrefijo = linea.substring(indicePrefijo + 3);

                // Verificar si empieza con ThreadGroup o Throwable
                boolean esError = false;
                if (contenidoDespuesPrefijo.contains(error1)) {
                    esError = true;
                    int inicio = contenidoDespuesPrefijo.indexOf(error1);
                    contenidoDespuesPrefijo = contenidoDespuesPrefijo.substring(inicio + error1.length());
                    int fin = contenidoDespuesPrefijo.indexOf("]:");
                    if (fin != -1) {
                        contenidoDespuesPrefijo = contenidoDespuesPrefijo.substring(fin + 2).trim();
                    }
                } else if (contenidoDespuesPrefijo.contains(error2)) {
                    esError = true;
                    int inicio = contenidoDespuesPrefijo.indexOf(error2);
                    contenidoDespuesPrefijo = contenidoDespuesPrefijo.substring(inicio + error2.length());
                    int fin = contenidoDespuesPrefijo.indexOf("]:");
                    if (fin != -1) {
                        contenidoDespuesPrefijo = contenidoDespuesPrefijo.substring(fin + 2).trim();
                    }
                }

                if (esError) {
                    // Eliminar posibles `: ` iniciales en trazas de pila
                    if (contenidoDespuesPrefijo.startsWith(": ")) {
                        contenidoDespuesPrefijo = contenidoDespuesPrefijo.substring(2);
                    }
                    linea = contenidoDespuesPrefijo;
                }
            }

            if(linea.startsWith("at")) {
            	linea="  "+linea;
            }
            
            resultado.append(linea).append("\n");
        }

        return resultado.toString();
    }
}