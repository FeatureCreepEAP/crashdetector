package com.asbestosstar.crashdetector.analizador.general;


import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detecta específicamente errores ZipException que incluyen el nombre del archivo JAR corrupto.
 * Extrae y muestra el nombre del archivo afectado para facilitar la identificación.
 */
public class ErrorJarCorruptoConNombre implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private static final Pattern PATRON_JAR_EN_ERROR = Pattern.compile(
        "Error analyzing \\[([^\\]]+\\.jar)\\]: java\\.util\\.zip\\.ZipException: zip END header not found"
    );

    @Override
    public void verificar(Consola consola) {
        // No se usa; el análisis es por línea
    }

    @Override
    public void verificar(Consola consola, String linea, int numero_de_linea) {
        if (this.activado) {
            return;
        }

        Matcher m = PATRON_JAR_EN_ERROR.matcher(linea);
        if (m.find()) {
            String rutaCompleta = m.group(1);
            // Extraer solo el nombre del archivo (último segmento después de '/')
            String nombreJar = rutaCompleta.substring(rutaCompleta.lastIndexOf('/') + 1);
            nombreJar = nombreJar.substring(nombreJar.lastIndexOf('\\') + 1); // Soporte para Windows

            String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
            this.mensaje = MonitorDePID.idioma.errorJarCorruptoConNombre(nombreJar) + enlaceHtml;
            this.activado = true;
        }
    }

    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        return PATRON_JAR_EN_ERROR.matcher(trazo.trace).find();
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorJarCorruptoConNombre();
    }

    @Override
    public boolean activado() {
        return this.activado;
    }

    @Override
    public String mensaje() {
        return this.mensaje;
    }

    @Override
    public float prioridad() {
        return 865.0f; // Ligeramente más alta que la genérica, para priorizar esta
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.solucionJarCorrupto())
            .construir();
    }

    @Override
    public String id() {
        return "jar_corrupto_con_nombre";
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreErrorJarCorruptoConNombre();
    }
}