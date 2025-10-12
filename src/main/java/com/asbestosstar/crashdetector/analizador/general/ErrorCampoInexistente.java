package com.asbestosstar.crashdetector.analizador.general;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores NoSuchFieldError que ocurren cuando un mod intenta acceder
 * a un campo que ya no existe en la version actual del juego o de otro mod.
 * Ejemplo comun: "DEAD_PLANKS" tras cambios en Biomes O' Plenty.
 */
public class ErrorCampoInexistente implements Verificaciones {

    private static final String PATRON_ERROR = "java\\.lang\\.NoSuchFieldError: (\\w+)";

    private boolean activado = false;
    private String mensaje = "";
    private String enlaceHtml = "";

    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contenido_verificar;
        String[] lineas = contenido.split(Verificaciones.nl);

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i];
            if (linea.contains("java.lang.NoSuchFieldError:")) {
                Pattern patron = Pattern.compile(PATRON_ERROR);
                Matcher matcher = patron.matcher(linea);
                if (matcher.find()) {
                    String nombreCampo = matcher.group(1);
                    String lineaError = linea.trim();
                    String lineaSiguiente = "";

                    // Verificar si hay una siguiente linea y empieza con "at"
                    if (i + 1 < lineas.length && lineas[i + 1].trim().startsWith("at ")) {
                        lineaSiguiente = lineas[i + 1].trim();
                    }

                    this.enlaceHtml = consola.agregarErrorALectador(i, this);
                    this.activado = true;

                    // Construir mensaje: primero el mensaje localizado, luego las lineas del log
                    StringBuilder sb = new StringBuilder();
                    sb.append(MonitorDePID.idioma.errorCampoInexistente(nombreCampo, lineaError));
                    sb.append(Verificaciones.nl_html);
                    sb.append("<span style='color:#888888; font-family:monospace;'>");
                    //sb.append(escapeHtml(lineaError));
                    if (!lineaSiguiente.isEmpty()) {
                       // sb.append(Verificaciones.nl_html);
                        sb.append(escapeHtml(lineaSiguiente));
                    }
                    sb.append("</span>");
                    sb.append(Verificaciones.nl_html);
                    sb.append(enlaceHtml);

                    this.mensaje = sb.toString();
                    break;
                }
            }
        }
    }

    // Helper para escapar caracteres HTML
    private String escapeHtml(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "<")
                .replace(">", ">")
                .replace("\"", "&quot;");
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorCampoInexistente();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 850.0f;
    }

    @Override
    public String mensaje() {
        return mensaje;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_error_campo_inexistente();
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
                .agregarEtiqueta(MonitorDePID.idioma.paso1_campo_inexistente())
                .agregarEtiqueta(MonitorDePID.idioma.paso2_campo_inexistente())
                .construir();
    }

    @Override
    public String id() {
        return "error_campo_inexistente";
    }

    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        return trazo.trace.contains("NoSuchFieldError");
    }
}