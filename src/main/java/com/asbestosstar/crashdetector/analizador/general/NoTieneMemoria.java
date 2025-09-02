package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class NoTieneMemoria implements Verificaciones {

    private boolean activado = false;
    private final CDStringBuilder mensajes = new CDStringBuilder();
    private boolean esProblemaDeMemoriaInsuficiente = false;
    private boolean esProblemaDeMemoriaExcesiva = false;
    private boolean esProblemaPermGen = false;

    @Override
    public void verificar(Consola consola) {
        String contenidoConsola = consola.contenido_verificar;

        //Muchos de las ideas son de TLauncher signatures.json o HMCL CrashAnalyzer.java
        
        // Verificar si es un problema de memoria insuficiente (el juego necesita MÁS memoria)
        if (esProblemaMemoriaInsuficiente(contenidoConsola)) {
            esProblemaDeMemoriaInsuficiente = true;
            mensajes.append(MonitorDePID.idioma.noTieneMemoria()).append(Verificaciones.nl_html)
                    .append(MonitorDePID.idioma.recomendacionMemoria());
        }

        // Verificar si es un problema de memoria excesiva (se asignó DEMASIADA memoria)
        if (esProblemaMemoriaExcesiva(contenidoConsola)) {
            esProblemaDeMemoriaExcesiva = true;
            mensajes.append(MonitorDePID.idioma.memoriaExcesiva()).append(Verificaciones.nl_html)
                    .append(MonitorDePID.idioma.recomendacionMemoriaExcesiva());
        }

        // Verificación específica de PermGen (solo en Java 7 y anteriores)
        if (contenidoConsola.contains("exit code: -805306369") || 
            contenidoConsola.contains("PermGen error") || 
            contenidoConsola.contains("java.lang.OutOfMemoryError: PermGen space")) {
            
            esProblemaPermGen = true;
            mensajes.append(Verificaciones.nl_html)
                    .append(MonitorDePID.idioma.permGenError());
        }

        // Si se detectó algún problema de memoria, activamos el verificador
        if (esProblemaDeMemoriaInsuficiente || esProblemaDeMemoriaExcesiva || esProblemaPermGen) {
            verificarJVMMemoria();
            activado = true;
        }
    }

    /**
     * Determina si el problema es que el juego no tiene suficiente memoria asignada
     * (necesita más RAM para funcionar correctamente)
     */
    private boolean esProblemaMemoriaInsuficiente(String contenidoConsola) {
        return contenidoConsola.contains("java.lang.OutOfMemoryError") &&
               !contenidoConsola.contains("Could not reserve enough space for") &&
               !contenidoConsola.contains("The specified size exceeds the maximum representable size") &&
               !contenidoConsola.contains("Invalid maximum heap size") &&
               !contenidoConsola.contains("There is insufficient memory for the Java Runtime Environment to continue") &&
               (contenidoConsola.contains("The system is out of physical RAM or swap space") ||
                contenidoConsola.contains("Out of Memory Error") ||
                contenidoConsola.contains("Too small maximum heap") ||
                contenidoConsola.contains("Insufficient memory") ||//DH TODO escribies descripcion
                contenidoConsola.contains("Problem with RAM"));
    }

    /**
     * Determina si el problema es que se asignó demasiada memoria al juego
     * (dejando insuficiente para el sistema operativo u otros procesos)
     */
    private boolean esProblemaMemoriaExcesiva(String contenidoConsola) {
        return contenidoConsola.contains("Could not reserve enough space for") ||
               contenidoConsola.contains("The specified size exceeds the maximum representable size") ||
               contenidoConsola.contains("Invalid maximum heap size") ||
               contenidoConsola.contains("There is insufficient memory for the Java Runtime Environment to continue");
    }

    private void verificarJVMMemoria() {
        String modeloJVM = System.getProperty("sun.arch.data.model");
        String arquitecturaOS = System.getProperty("os.arch");
        boolean esJVM32Bits = false;

        if (modeloJVM != null && modeloJVM.equals("32")) {
            esJVM32Bits = true;
        } else if (arquitecturaOS != null) {
            esJVM32Bits = arquitecturaOS.matches("(i?[3-6]86|x86|ppc32|ppc|powerpc|armv\\d+)")
                    || (arquitecturaOS.endsWith("86")
                            && System.getProperty("os.name").toLowerCase().contains("windows"));
        }

        if (esJVM32Bits) {
            mensajes.append(Verificaciones.nl_html).append(MonitorDePID.idioma.error32BitMemoria());
        }
    }

    @Override
    public Verificaciones nueva() {
        return new NoTieneMemoria();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 400.0f; // Prioridad crítica para errores de memoria
    }

    @Override
    public String mensaje() {
        return mensajes.toString();
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_de_no_tiene_memoria();
    }

    @Override
    public QuickFix solucion() {
        CDStringBuilder solucion = new CDStringBuilder();
        
        // Soluciones específicas para memoria insuficiente
        if (esProblemaDeMemoriaInsuficiente) {
            solucion.append(MonitorDePID.idioma.aumentarMemoriaHeap()).append(Verificaciones.nl_html);
        }
        
        // Soluciones específicas para memoria excesiva
        if (esProblemaDeMemoriaExcesiva) {
            solucion.append(MonitorDePID.idioma.disminuirMemoriaHeap()).append(Verificaciones.nl_html);
        }
        
        // Soluciones para PermGen
        if (esProblemaPermGen) {
            solucion.append(MonitorDePID.idioma.aumentarMemoriaPermgen()).append(Verificaciones.nl_html);
        }
        
        // Soluciones comunes
        solucion.append(MonitorDePID.idioma.utilizarJVM64Bits()).append(Verificaciones.nl_html)
                .append(MonitorDePID.idioma.optimizarCodigo()).append(Verificaciones.nl_html)
                .append(MonitorDePID.idioma.utilizarRecolectorBasuraEficiente());

        return new QuickFix.Builder(nombre()).agregarEtiqueta(solucion.toString()).construir();
    }
}