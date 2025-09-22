package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class ConflictoMultiworldRendimiento implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String enlaceHtml = "";

    @Override
    public void verificar(Consola consola) {
        String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);

        for (int i = 0; i < lineas.length; i++) {
            String l = lineas[i];
            // Patrón mínimo del fallo observado
            if (l.contains("IncompatibleClassChangeError")
                    && l.contains("net.fabricmc.loader.api.FabricLoader.getInstance()")
                    && l.contains("must be Methodref constant")) {

                // Enlazar a la línea del error en el lector
                enlaceHtml = consola.agregarErrorALectador(i, this);

                // Mensaje corto y en color de error, mencionando Multiworld y Sodium/Embeddium/Rubidium
                mensaje = MonitorDePID.idioma.errorConflictoMultiworldRendimiento() + Verificaciones.nl_html;
                activado = true;
                break;
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ConflictoMultiworldRendimiento();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 950.0f; // alta: rompe inicialización del cliente
    }

    @Override
    public String mensaje() {
        return activado ? (mensaje + enlaceHtml) : "";
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreDeConflictoMultiworldRendimiento();
    }

    @Override
    public QuickFix solucion() {
        // Un solo paso
        return new QuickFix.Builder(nombre())
                .agregarEtiqueta(MonitorDePID.idioma.pasoConflictoMultiworldRendimiento())
                .construir();
    }
}
