package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class BloqueTeselado implements Verificaciones {

    public boolean activado = false;

    @Override
    public void verificar(String contenido_de_consola, CDStringBuilder constructor) {
        // Expresión regular para detectar el error específico
        if (contenido_de_consola.matches("(?sm).*Tesselating block in world$.*")) {
            constructor.append(MonitorDePID.idioma.errorDeBloqueTeselado())
                      .append(nl_html);
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new BloqueTeselado();
    }

    @Override
    public boolean activado() {
        return activado;
    }
}