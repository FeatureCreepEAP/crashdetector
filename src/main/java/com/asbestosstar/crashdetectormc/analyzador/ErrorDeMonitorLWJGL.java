package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class ErrorDeMonitorLWJGL implements Verificaciones {


    public boolean activado = false;

    @Override
    public void verificar(String contenido_de_consola, CDStringBuilder constructor) {
        // Expresión regular para detectar el error de display mode
        if (contenido_de_consola.matches("(?sm).*org\\.lwjgl\\.LWJGLException:\\s*Failed to set display mode \\(.*")) {
            constructor.append(MonitorDePID.idioma.errorMonitorLWJGL())
                      .append(nl_html);
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorDeMonitorLWJGL();
    }

    @Override
    public boolean activado() {
        return activado;
    }

}
