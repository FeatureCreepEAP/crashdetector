package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class OpcionesJavaGCInvalidas implements Verificaciones {


    public boolean activado = false;

    @Override
    public void verificar(String contenido_de_consola, CDStringBuilder constructor) {
        // Detectar conflictos de opciones de recolección de basura
        if (contenido_de_consola.matches("(?sm).*Conflicting collector combinations in option list.*")) {
            constructor.append(MonitorDePID.idioma.errorOpcionesGCJava())
                      .append(nl_html);
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new OpcionesJavaGCInvalidas();
    }

    @Override
    public boolean activado() {
        return activado;
    }

}
