package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta "FATAL ERROR in native method … No context is current or a function that is not available…".
 * Si existe un stacktrace cerca, intenta obtener el origen con vdst.
 */
public class ErrorContextoOpenGL implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String enlaceHtml = "";
    private String origen = "";

    @Override
    public void verificar(Consola consola) {
        String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);

        for (int i = 0; i < lineas.length; i++) {
            String l = lineas[i];

            boolean cabecera = l.contains("FATAL ERROR in native method");
            boolean detalleAqui = l.contains("No context is current or a function that is not available in the current context was called");
            boolean detalleSiguiente = !detalleAqui
                    && i + 1 < lineas.length
                    && lineas[i + 1].contains("No context is current or a function that is not available in the current context was called");

            if (cabecera && (detalleAqui || detalleSiguiente)) {
                // Mensaje base
                mensaje = MonitorDePID.idioma.errorContextoOpenGL();

                // Intentar detectar origen con el stacktrace a partir de esta línea
                origen = detectarOrigenConVDST(consola.verificacion_de_stacktrace, consola.contenido_verificar, i);

                if (!origen.isEmpty()) {
                    // Añadir origen aquí, no en idioma
                    mensaje = mensaje + " <b>(" + origen + ")</b>";
                }

                enlaceHtml = consola.agregarErrorALectador(i, this);
                activado = true;
                break;
            }
        }
    }

    /**
     * Busca un trace cercano usando vdst y extrae el primer origen plausible
     * (jar/modid/paquete) con los métodos utilitarios de VerificacionDeStackTrace.
     */
    private String detectarOrigenConVDST(VerificacionDeStackTrace vdst, String log, int lineaInicio) {
        // Preferir un trace que empiece en o después del error
        VerificacionDeStackTrace.TraceInfo candidato = null;
        int ventanaSuperior = lineaInicio + 60;

        for (VerificacionDeStackTrace.TraceInfo ti : VerificacionDeStackTrace.obtenerTracesFatalConLinea(log)) {
            if (ti.consolaLineaComenzar >= lineaInicio && ti.consolaLineaComenzar <= ventanaSuperior) {
                candidato = ti;
                break;
            }
        }
        if (candidato == null) {
            for (VerificacionDeStackTrace.TraceInfo ti : VerificacionDeStackTrace.obtenerTracesConLinea(log)) {
                if (ti.consolaLineaComenzar >= lineaInicio && ti.consolaLineaComenzar <= ventanaSuperior) {
                    candidato = ti;
                    break;
                }
            }
        }

        if (candidato == null) {
            // Fallback: mirar unas cuantas líneas siguientes por si hay pistas sueltas
            String[] arr = log.split(Verificaciones.nl);
            for (int ln = lineaInicio; ln < Math.min(arr.length, lineaInicio + 30); ln++) {
                String posible = origenEnLinea(arr[ln]);
                if (!posible.isEmpty()) return posible;
            }
            return "";
        }

        // Analizar el trace encontrado, línea por línea, para extraer origen
        String[] arr = candidato.trace.split(Verificaciones.nl);
        for (String linea : arr) {
            String posible = origenEnLinea(linea);
            if (!posible.isEmpty()) return posible;
        }
        return "";
    }

    /**
     * Extrae origen de una línea con los utilitarios vdst:
     * - jar primero, luego modid, luego paquete (evitando prefijos comunes).
     */
    private String origenEnLinea(String linea) {
        // JAR
        for (String jar : VerificacionDeStackTrace.extraerJarsDeLinea(linea)) {
            if (jar.contains(".jar") && !VerificacionDeStackTrace.isJarNoPermite(jar)) {
                return jar;
            }
        }
        // Mod ID
        String modid = VerificacionDeStackTrace.extraerModidDeLinea(linea);
        if (modid != null && !VerificacionDeStackTrace.esModNoPermite(modid)) {
            return modid;
        }
        // Paquete
        String pack = VerificacionDeStackTrace.extraerPaqueteDeLinea(linea);
        if (pack != null && !empiezaConPrefijoNoPermitido(pack)) {
            return pack;
        }
        return "";
    }

    private boolean empiezaConPrefijoNoPermitido(String pack) {
        for (String p : VerificacionDeStackTrace.package_no_permite) {
            if (pack.startsWith(p)) return true;
        }
        return false;
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorContextoOpenGL();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1050.0f;
    }

    @Override
    public String mensaje() {
        if (!activado) return "";
        return mensaje + enlaceHtml;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreErrorContextoOpenGL();
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.paso1ErrorContextoOpenGL())
            .construir();
    }
    
    
    @Override
	public String id() {
		// TODO Auto-generated method stub
		return "contexto_opengl";
	}
}
