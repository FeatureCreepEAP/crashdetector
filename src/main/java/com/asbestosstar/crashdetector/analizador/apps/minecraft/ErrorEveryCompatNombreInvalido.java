package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

/**
 * Detecta errores de Every Compat (Etc) donde hay bloques de madera con nombres
 * inválidos que provocan un UnsupportedOperationException durante la carga.
 */
public class ErrorEveryCompatNombreInvalido implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String enlaceHtml = "";

    /**
     * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
     */
    @Override
    public void verificar(Consola consola) {
        // Se usa el método verificar(Consola, String, int) en lugar de este.
    }

    /**
     * Análisis por línea del registro.
     * <p>
     * Se busca el patrón característico del error donde Every Compat encuentra
     * un nombre de bloque de madera inválido que provoca un UnsupportedOperationException.
     * </p>
     */
    @Override
    public void verificar(Consola consola, String linea, int numero_de_linea) {
        if (activado) {
            // Si ya se activó, no seguimos verificando más líneas.
            return;
        }

        // Buscamos la línea que contiene el error de nombre inválido de Every Compat
        if (linea.contains("java.lang.UnsupportedOperationException")
                && linea.contains("has an invalid item name")
                && (linea.contains("every_compat") || linea.contains("Every Compat") || linea.contains("SimpleEntrySet"))) {
            
            // Enlazar a la línea del error en el lector
            enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

            // Mensaje de error en HTML con referencia al problema de Every Compat
            mensaje = MonitorDePID.idioma.errorEveryCompatNombreInvalido() + Verificaciones.nl_html;
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorEveryCompatNombreInvalido();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 850.0f; // Prioridad media-alta: rompe la carga de bloques de madera
    }

    @Override
    public String mensaje() {
        return activado ? (mensaje + enlaceHtml) : "";
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreDeErrorEveryCompatNombreInvalido();
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
                .agregarEtiqueta(MonitorDePID.idioma.pasoErrorEveryCompatNombreInvalido())
                .construir();
    }

    @Override
    public String id() {
        return "error_every_compat_nombre_invalido";
    }

    /**
     * Asocia esta verificación con un trazo específico del stack.
     * <p>
     * Devuelve true si el trazo contiene las cadenas clave del error de
     * compatibilidad de nombres inválidos de Every Compat.
     * </p>
     */
    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        if (!activado || trazo == null || trazo.trace == null) {
            return false;
        }

        String t = trazo.trace;

        return t.contains("java.lang.UnsupportedOperationException")
                && t.contains("has an invalid item name")
                && (t.contains("every_compat") || t.contains("Every Compat") || t.contains("SimpleEntrySet"));
    }
}