package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.CDStringBuilder;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class NullPointer implements Verificaciones {

    public boolean activado = false;

    @Override
    public void verificar(String contento_de_consola, CDStringBuilder constructor) {
        for (String linea : contento_de_consola.split(MonitorDePID.nl)) {
            if (linea.contains("java.lang.NullPointerException: Cannot invoke")) {
                activado = true;

                String metodo = "";
                String objeto = "";
                try {
                    String[] parts = linea.split("Cannot invoke \"");
                    if (parts.length > 1) {
                        metodo = parts[1].split("\"")[0]; 
                    }

                    parts = linea.split("because the return value of \"");
                    if (parts.length > 1) {
                        objeto = parts[1].split("\"")[0]; 
                    }
                } catch (Exception e) {
                    metodo = "method";
                    objeto = "object";
                }

                constructor.append(MonitorDePID.idioma.null_pointer_error(metodo, objeto))
                           .append(nl_html);
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new NullPointer();
    }

    @Override
    public boolean activado() {
        return activado;
    }
}