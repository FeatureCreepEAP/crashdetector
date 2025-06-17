package com.asbestosstar.crashdetector.analizador.general;

import java.awt.Desktop;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

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
        
        StringBuilder html = new StringBuilder("<ul>");
        for (String msg : mensajes) {
            html.append("<li>").append(msg).append("</li>");
        }
        html.append("</ul>");
        return html.toString();
    }
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_java_versiones();
	}
	
    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.solucionParaJavaInstallar())
            
            // Botón para Red Hat OpenJDK (Windows/x86_x64 + Linux/x86_x64 + SRC)
            .agregarBoton("Java Windows-x86_x64+Linux-x86_x64+SRC", (bool) -> {
                abrirEnNavegador("https://developers.redhat.com/products/openjdk/download"); 
            })
            
            // Botón para OpenLogic (Mac Intel)
            .agregarBoton("Java Mac Intel", (bool) -> {
                abrirEnNavegador("https://www.openlogic.com/openjdk-downloads"); 
            })
            
            // Botón para JDK PPC Mac (GitHub)
            .agregarBoton("Java Mac PPC", (bool) -> {
                abrirEnNavegador("https://github.com/nilsvanvelzen/mac_ppc_openjdk8u60"); 
            })
            
            // Botón para Oracle Java Downloads
            .agregarBoton("Java Sun Microsystems", (bool) -> {
                abrirEnNavegador("https://www.oracle.com/java/technologies/downloads/"); 
            })
            
            // Botón para Tribblix JDK (Solaris/Illumos)
            .agregarBoton("Java 15+ Solaris/Iluminos", (bool) -> {
                abrirEnNavegador("https://pkgs.tribblix.org/openjdk/"); 
            })
            .construir();
    }
    
    /**
     * Abre una URL en el navegador predeterminado del sistema
     * @param url La dirección web a abrir
     */
    private void abrirEnNavegador(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI(url));
                } 
            }
        } catch (Exception e) {
    CrashDetectorLogger.logException(e);
        }
    }
    
    
    
}