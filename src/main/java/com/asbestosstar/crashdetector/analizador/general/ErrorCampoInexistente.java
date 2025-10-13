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
 * a un campo que ya no existe en la versión actual del juego u otro mod.
 * Soporta formatos:
 *  - "java.lang.NoSuchFieldError: DEAD_PLANKS"
 *  - "java.lang.NoSuchFieldError: Class X does not have member field 'paquete.Clase$Tipo nombreCampo'"
 */
public class ErrorCampoInexistente implements Verificaciones {

    // Patrón simple tradicional: toma el token inmediatamente después de "NoSuchFieldError:"
    private static final Pattern PATRON_ERROR_SIMPLE =
            Pattern.compile("java\\.lang\\.NoSuchFieldError:\\s+(\\w+)");

    // Patrón extendido (Forge/Fabric más verboso): extrae el nombre de campo dentro de comillas simples.
    // Ejemplo:
    //   java.lang.NoSuchFieldError: Class traben.entity_model_features.config.EMFConfig
    //   does not have member field 'traben.entity_model_features.config.EMFConfig$PhysicsModCompatChoice attemptPhysicsModPatch_2'
    private static final Pattern PATRON_ERROR_MIEMBRO =
            Pattern.compile("java\\.lang\\.NoSuchFieldError:\\s*Class\\s+[^\\s]+\\s+does\\s+not\\s+have\\s+member\\s+field\\s+'([^']+)'");

    private boolean activado = false;
    private String mensaje = "";
    private String enlaceHtml = "";

    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contenido_verificar;
        String[] lineas = contenido.split(Verificaciones.nl);

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i];

            if (!linea.contains("java.lang.NoSuchFieldError:")) continue;

            // Intentar patrón extendido primero
            String nombreCampo = null;
            Matcher mExt = PATRON_ERROR_MIEMBRO.matcher(linea);
            if (mExt.find()) {
                // Dentro de la comilla simple puede venir "paquete.Clase$Tipo nombreCampo"
                // Nos quedamos con el último token después del espacio, si existe.
                String crudo = mExt.group(1).trim();
                int idxEspacio = crudo.lastIndexOf(' ');
                nombreCampo = (idxEspacio >= 0 && idxEspacio < crudo.length() - 1)
                        ? crudo.substring(idxEspacio + 1)
                        : crudo;
            } else {
                // Fallback: patrón simple clásico
                Matcher mSimple = PATRON_ERROR_SIMPLE.matcher(linea);
                if (mSimple.find()) {
                    nombreCampo = mSimple.group(1);
                }
            }

            if (nombreCampo != null && !nombreCampo.isEmpty()) {
                String lineaError = linea.trim();

                // Buscar la primera línea siguiente que empiece por "at "
                String lineaSiguiente = "";
                if (i + 1 < lineas.length && lineas[i + 1].trim().startsWith("at ")) {
                    lineaSiguiente = lineas[i + 1].trim();
                }

                this.enlaceHtml = consola.agregarErrorALectador(i, this);
                this.activado = true;

                // Construir mensaje: primero el mensaje localizado, luego un bloque monoespaciado con contexto y enlace
                StringBuilder sb = new StringBuilder();
                sb.append(MonitorDePID.idioma.errorCampoInexistente(nombreCampo, lineaError));
                sb.append(Verificaciones.nl_html);
                sb.append("<span style='color:#888888; font-family:monospace;'>");
                // Mostrar la línea "at ..." si existe
                if (!lineaSiguiente.isEmpty()) {
                    sb.append(escapeHtml(lineaSiguiente));
                }
                sb.append("</span>");
                sb.append(Verificaciones.nl_html);
                sb.append(enlaceHtml);

                this.mensaje = sb.toString();
                break; // con el primer match es suficiente
            }
        }
    }

    // Utilidad para escapar HTML básico
    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
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
        return trazo != null && trazo.trace != null && trazo.trace.contains("NoSuchFieldError");
    }
}
