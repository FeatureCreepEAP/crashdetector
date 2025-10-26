package com.asbestosstar.crashdetector.analizador.general;


import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta cuando el servidor no puede iniciar porque un archivo del mundo está bloqueado
 * por otro proceso (por ejemplo: otra instancia del servidor, antivirus, explorador de archivos).
 */
public class ErrorArchivoBloqueadoPorOtroProceso implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";

    @Override
    public void verificar(Consola consola) {
        // Este método no se usa; el análisis se hace por línea
    }

    @Override
    public void verificar(Consola consola, String linea, int numero_de_linea) {
        if (this.activado) {
            return;
        }

        if (linea.contains("java.io.IOException: The process cannot access the file because another process has locked a portion of the file")) {
            String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
            this.mensaje = MonitorDePID.idioma.errorArchivoBloqueadoPorOtroProceso() + enlaceHtml;
            this.activado = true;
        }
    }

    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        return trazo.trace.contains("java.io.IOException: The process cannot access the file because another process has locked a portion of the file");
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorArchivoBloqueadoPorOtroProceso();
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
        return 840.0f; // Alta: impide iniciar el servidor
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.solucionErrorArchivoBloqueadoPorOtroProceso())
            .construir();
    }

    @Override
    public String id() {
        return "archivo_bloqueado_por_otro_proceso";
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreErrorArchivoBloqueadoPorOtroProceso();
    }
}