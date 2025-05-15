package com.asbestosstar.crashdetectormc.analyzador;

import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class JavaVersiones implements Verificaciones {

    private boolean activado = false;
    private final Set<String> mensajes = new HashSet<>();

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;

        // Verificación de versión Java 22 no soportada
        if (contenidoConsola.contains("java.lang.IllegalArgumentException: Unsupported class file major version")) {
            if (contenidoConsola.contains("--fml.forgeVersion, 4") || contenidoConsola.contains("--fml.forgeVersion, 3")) {
                if (contenidoConsola.contains("java version 2") && !contenidoConsola.contains("java version 20") && !contenidoConsola.contains("java version 21")) {
                    mensajes.add(MonitorDePID.idioma.java22());
                    activado = true;
                }
            }
        }

        // Verificación de versión Java obsoleta
        if (contenidoConsola.contains("has been compiled by a more recent version of the Java Runtime")) {
            mensajes.add(MonitorDePID.idioma.javaObsoleta());
            activado = true;
        }

        // Verificación de incompatibilidad con Java 8
        if (contenidoConsola.contains("class jdk.internal.loader.ClassLoaders$AppClassLoader cannot be cast to class java.net.URLClassLoader")) {
            mensajes.add(MonitorDePID.idioma.errorCompatibilidadJava8());
            activado = true;
        }

        // Verificación de Java 9+ no soportado
        if (contenidoConsola.contains("java.base/jdk.internal.loader.ClassLoaders$AppClassLoader cannot be cast to java.base/java.net.URLClassLoader")) {
            mensajes.add(MonitorDePID.idioma.errorJava9NoSoportado());
            activado = true;
        }

        // Verificación de requerimiento Java 8
        if (contenidoConsola.contains("net/minecraft/client/main/Main : Unsupported major.minor version 52.0")) {
            mensajes.add(MonitorDePID.idioma.errorJava8Requerido());
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

    @Override
    public float prioridad() {
        return 800.0f;
}

    @Override
    public String mensaje() {
        if (mensajes.isEmpty()) return "";
        
        StringBuilder html = new StringBuilder("<ul>").append(Verificaciones.nl_html);
        for (String msg : mensajes) {
            html.append("<li>").append(msg).append("</li>").append(Verificaciones.nl_html);
        }
        html.append("</ul>");
        return html.toString();
    }
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_java_versiones();
	}
    
    
    
}