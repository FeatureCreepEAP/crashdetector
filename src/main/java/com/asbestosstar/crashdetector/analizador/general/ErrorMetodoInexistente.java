package com.asbestosstar.crashdetector.analizador.general;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores NoSuchMethodError que ocurren cuando un mod intenta llamar
 * a un metodo que ya no existe en la version actual del juego o de otro mod.
 */
public class ErrorMetodoInexistente implements Verificaciones {

    private static final Pattern PATRON = Pattern.compile("java\\.lang\\.NoSuchMethodError:\\s*(.+)$");

    private boolean activado = false;
    private String mensaje = "";
    private String enlaceHtml = "";
    private String firmaMetodo = "";
    private String lineaSiguiente = "";

    @Override
    public void verificar(Consola consola, String linea, int numLinea) {
        if (activado) return;

        if (linea.contains("java.lang.NoSuchMethodError:")) {
            Matcher m = PATRON.matcher(linea);
            if (m.find()) {
                this.firmaMetodo = m.group(1).trim();

                // Obtener la siguiente linea si empieza con "at "
                String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);
                if (numLinea + 1 < lineas.length) {
                    String sig = lineas[numLinea + 1].trim();
                    if (sig.startsWith("at ")) {
                        this.lineaSiguiente = sig;
                    }
                }

                this.enlaceHtml = consola.agregarErrorALectador(numLinea, this);
                this.activado = true;

                // Solo el mensaje localizado + (opcional) la linea "at ..."
                StringBuilder sb = new StringBuilder();
                sb.append(MonitorDePID.idioma.errorMetodoInexistente(firmaMetodo, firmaMetodo));
                if (!lineaSiguiente.isEmpty()) {
                    sb.append(Verificaciones.nl_html);
                    sb.append("<span style='color:#888888; font-family:monospace;'>");
                    sb.append(escapeHtml(lineaSiguiente));
                    sb.append("</span>");
                }
                sb.append(Verificaciones.nl_html);
                sb.append(enlaceHtml);
                this.mensaje = sb.toString();
            }
        }
    }

    private String escapeHtml(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "<")
                .replace(">", ">")
                .replace("\"", "&quot;");
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorMetodoInexistente();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1100.0f;
    }

    @Override
    public String mensaje() {
        return mensaje;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_error_metodo_inexistente();
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
                .agregarEtiqueta(MonitorDePID.idioma.paso1_metodo_inexistente())
                .agregarEtiqueta(MonitorDePID.idioma.paso2_metodo_inexistente())
                .construir();
    }

    @Override
    public String id() {
        return "error_metodo_inexistente";
    }

    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        return trazo.trace.contains("NoSuchMethodError");
    }

    @Override
    public void verificar(Consola consola) {
        // No se usa; el sistema llama a verificar(Consola, String, int)
    }
}