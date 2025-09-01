package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.BiMap.DoubleKey;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Verificación especializada para detectar y resumir todas las
 * {@code NullPointerException} (NPE) que aparecen en la consola.
 * <p>
 * - Analiza tanto el texto crudo de la consola como los trazos ya extraídos por
 *   {@link VerificacionDeStackTrace} para asegurar que no se escape ningún caso.
 * - Para cada NPE, identifica (si es posible) el método y el objeto nulo implicado.
 * - Añade un posible origen (JAR, modid o clase) usando solo información del trazo actual,
 *   evitando falsos positivos al no usar datos globales de otros errores.
 * - La salida es un bloque HTML con lista de errores, pensado para mostrarse en la UI.
 */
public class NullPointer implements Verificaciones {


    /**
     * Detecta el encabezado de una NPE:
     * java.lang.NullPointerException: <mensaje opcional>
     */
    private static final Pattern CABECERA_NPE = Pattern.compile("java\\.lang\\.NullPointerException(?::\\s*)?(.*)",
            Pattern.CASE_INSENSITIVE);

    /**
     * Detecta mensajes modernos de Java como:
     * "Cannot invoke 'X' because the return value of 'Y' is null"
     */
    private static final Pattern FORMATO_CANNOT = Pattern
            .compile("Cannot\\s+(invoke|read|assign)[^\"]*\"([^\"]+)\"[^\"]*\"([^\"]+)\"");

    /**
     * Patrón común en errores de Gson: intentar usar un JsonObject que es null
     * Ej: "Cannot invoke \"JsonObject.entrySet()\" because \"jsonobject\" is null"
     */
    private static final Pattern ERROR_JSON = Pattern.compile("JsonObject\\.[a-zA-Z]+\\(\\).*\"([^\"]+)\" is null");

    /**
     * Separador de líneas, definido en la interfaz base
     */
    private static final String NL = Verificaciones.nl;


    /**
     * Almacena los mensajes de error únicos detectados
     */
    private final Set<String> errores = new HashSet<>();

    /**
     * Indica si se encontró al menos una NPE
     */
    private boolean activado = false;


    @Override
    public void verificar(Consola consola) {
        // Limpiar resultados anteriores
        errores.clear();
        activado = false;

        VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;

        // Colección de trazos (fatales y no fatales)
        List<String> trazos = new ArrayList<>();
        trazos.addAll(VerificacionDeStackTrace.obtenerTraces(consola.contenido_verificar));
        trazos.addAll(VerificacionDeStackTrace.obtenerTracesFatal(consola.contenido_verificar));

        // Analizar cada trazo
        for (String trazo : trazos) {
            if (!trazo.contains("NullPointerException")) {
                continue;
            }

            String metodo = "método desconocido";
            String objeto = "objeto";
            String origen = "";

            Matcher mCannot = FORMATO_CANNOT.matcher(trazo);
            Matcher mCabecera = CABECERA_NPE.matcher(trazo);
            Matcher mJson = ERROR_JSON.matcher(trazo);

            // Caso 1: Mensaje moderno "Cannot invoke X because Y is null"
            if (mCannot.find()) {
                metodo = mCannot.group(2);
                objeto = mCannot.group(3);
            }
            // Caso 2: Gson específico: "JsonObject.entrySet() because 'jsonobject' is null"
            else if (mJson.find()) {
                metodo = "JsonObject.*()";
                objeto = mJson.group(1);
            }
            // Caso 3: Encabezado con descripción adicional
            else if (mCabecera.find()) {
                String detalle = mCabecera.group(1).trim();
                if (!detalle.isEmpty()) {
                    metodo = detalle;
                }
            } else {
                // No se pudo extraer información útil
                continue;
            }

            // Buscar origen SOLO en este trazo (evita falsos positivos)
            origen = detectarOrigenEnTraza(trazo, vdst);

            // Construir mensaje
            String mensaje = MonitorDePID.idioma.null_pointer_error(metodo, objeto);
            if (!origen.isEmpty()) {
                mensaje += " (" + origen + ")";
            }

            errores.add(mensaje);
            activado = true;
        }

        // Analizar líneas sueltas sin trazo completo (ej: errores sin "at ...")
        for (String linea : consola.contenido_verificar.split(NL)) {
            if (linea.contains("NullPointerException") && !linea.contains("at ") && VerificacionDeStackTrace.tracePermite(linea)) {
                procesarLineaSinTraza(linea, vdst);
            }
        }
    }

    /**
     * Procesa una línea con NPE que no tiene stack trace completo
     */
    private void procesarLineaSinTraza(String linea, VerificacionDeStackTrace vdst) {
        String metodo = "desconocido";
        String objeto = "desconocido";

        Matcher mCannot = FORMATO_CANNOT.matcher(linea);
        Matcher mJson = ERROR_JSON.matcher(linea);

        if (mCannot.find()) {
            metodo = mCannot.group(2);
            objeto = mCannot.group(3);
        } else if (mJson.find()) {
            metodo = "JsonObject.*()";
            objeto = mJson.group(1);
        } else {
            return; // No es relevante
        }

        String origen = detectarOrigen(vdst); // Usa origen global como último recurso
        String mensaje = MonitorDePID.idioma.null_pointer_error(metodo, objeto);
        if (!origen.isEmpty()) mensaje += " (" + origen + ")";

        errores.add(mensaje);
        activado = true;
    }


    /**
     * Busca un origen (mod, JAR o clase) DIRECTAMENTE en el trazo del error.
     * Esto evita asociar errores con mods que solo aparecen en otros trazos.
     */
    private String detectarOrigenEnTraza(String trazo, VerificacionDeStackTrace vdst) {
        // 1. Buscar JAR en las líneas del trazo
        for (String linea : trazo.split(NL)) {
            for (String jar : vdst.jars.keySet()) {
                if (linea.contains(jar)) {
                    int idx = jar.indexOf(MonitorDePID.idioma.nivel());
                    return idx == -1 ? jar : jar.substring(0, idx);
                }
            }
            // 2. Buscar modid
            for (DoubleKey<String, String> entry : vdst.modids.keySet()) {
                if (linea.contains(entry.key0)) {
                    return entry.key0;
                }
            }
            // 3. Buscar paquete/clase
            for (DoubleKey<String, String> entry : vdst.packs.keySet()) {
                if (linea.contains(entry.key0)) {
                    return entry.key0;
                }
            }
        }
        return ""; // No se encontró origen en este trazo
    }

    /**
     * Determina un posible origen usando datos globales (último recurso).
     * Solo usado si el error no tiene trazo.
     */
    private static String detectarOrigen(VerificacionDeStackTrace vdst) {
        if (!vdst.jars.isEmpty()) {
            String clave = vdst.jars.keySet().iterator().next();
            int idx = clave.indexOf(MonitorDePID.idioma.nivel());
            return idx == -1 ? clave : clave.substring(0, idx);
        }
        if (!vdst.modids.isEmpty()) {
            return vdst.modids.entrySet().iterator().next().getKey().key0;
        }
        if (!vdst.packs.isEmpty()) {
            return vdst.packs.entrySet().iterator().next().getKey().key0;
        }
        return "";
    }


    @Override
    public Verificaciones nueva() {
        return new NullPointer();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 50f; // Alta prioridad: NPE suele ser crítico
    }

    @Override
    public String mensaje() {
        if (errores.isEmpty()) return "";
        StringBuilder sb = new StringBuilder("<ul>");
        errores.forEach(e -> sb.append("<li>").append(e).append("</li>"));
        sb.append("</ul>");
        return sb.toString();
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_de_null_pointer();
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
                .agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
                .construir();
    }
}