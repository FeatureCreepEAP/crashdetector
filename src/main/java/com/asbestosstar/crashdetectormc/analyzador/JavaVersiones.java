package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class JavaVersiones implements Verificaciones {

    public boolean activado = false;
    private static final String nl_html = "<br>";

    @Override
    public void verificar(String contenido_de_consola, CDStringBuilder constructor) {
        // Lógica original preservada
        String out = MonitorDePID.idioma.versionDeJava();
        if (contenido_de_consola.contains("java.lang.IllegalArgumentException: Unsupported class file major version")) {
            if (contenido_de_consola.contains("--fml.forgeVersion, 4") || contenido_de_consola.contains("--fml.forgeVersion, 3")) {
                if (contenido_de_consola.contains("java version 2") && !contenido_de_consola.contains("java version 20") && !contenido_de_consola.contains("java version 21")) {
                    out = MonitorDePID.idioma.java22();
                    constructor.append(out).append(nl_html);
                    activado = true;
                }
            }
        } else if (contenido_de_consola.contains("has been compiled by a more recent version of the Java Runtime")) {
            constructor.append(MonitorDePID.idioma.javaObsoleta()).append(nl_html);
            activado = true;
        }

        // Nuevas validaciones añadidas
        if (contenido_de_consola.contains("class jdk.internal.loader.ClassLoaders$AppClassLoader cannot be cast to class java.net.URLClassLoader")) {
            constructor.append(MonitorDePID.idioma.errorCompatibilidadJava8())
                .append(nl_html);
            activado = true;
        }

        if (contenido_de_consola.contains("java.base/jdk.internal.loader.ClassLoaders$AppClassLoader cannot be cast to java.base/java.net.URLClassLoader")) {
            constructor.append(MonitorDePID.idioma.errorJava9NoSoportado())
                .append(nl_html);
            activado = true;
        }

        if (contenido_de_consola.contains("net/minecraft/client/main/Main : Unsupported major.minor version 52.0")) {
            constructor.append(MonitorDePID.idioma.errorJava8Requerido())
                .append(nl_html);
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new JavaVersiones();
    }

    @Override
    public boolean activado() {
        return activado;
    }
}