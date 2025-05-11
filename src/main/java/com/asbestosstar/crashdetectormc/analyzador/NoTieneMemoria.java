package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class NoTieneMemoria implements Verificaciones {

    public boolean activado = false;

    @Override
    public void verificar(String contenido_de_consola, CDStringBuilder constructor) {
        // Patrones principales detectados en las firmas
        boolean memoriaInsuficiente = contenido_de_consola.contains("java.lang.OutOfMemoryError") ||
                                     contenido_de_consola.contains("Could not reserve enough space for") ||
                                     contenido_de_consola.contains("Native memory allocation failed") ||
                                     contenido_de_consola.contains("swap file increase") ||
                                     contenido_de_consola.contains("Problem with RAM");

        // Caso principal de memoria insuficiente
        if (memoriaInsuficiente) {
            constructor.append(MonitorDePID.idioma.noTieneMemoria())
                      .append("<br>")
                      .append(MonitorDePID.idioma.recomendacionMemoria());
            
            verificarJVMMemoria(constructor);
            activado = true;
        }

        // Caso específico de PermGen (código de salida único)
        if (contenido_de_consola.contains("exit code: -805306369") || 
            contenido_de_consola.contains("PermGen error")) {
            
            constructor.append(MonitorDePID.idioma.permGenError())
                      .append("<br>")
                      .append(MonitorDePID.idioma.permGenError());
            
            verificarJVMMemoria(constructor);
            activado = true;
        }
    }
    
    private void verificarJVMMemoria(CDStringBuilder constructor) {
        String modeloJVM = System.getProperty("sun.arch.data.model");
        String arquitecturaOS = System.getProperty("os.arch");
        boolean esJVM32Bits = false;

        // Verificación principal usando propiedad JVM
        if (modeloJVM != null && modeloJVM.equals("32")) {
            esJVM32Bits = true;
        } else if (arquitecturaOS != null) {
            // Patrones de arquitecturas de 32 bits conocidas
            esJVM32Bits = arquitecturaOS.matches("(i?[3-6]86|x86|ppc32|ppc|powerpc|armv\\d+)")
                        || (arquitecturaOS.endsWith("86") && 
                            System.getProperty("os.name").toLowerCase().contains("windows"));
        }

        if (esJVM32Bits) {
            constructor.append("<br>")
                      .append(MonitorDePID.idioma.error32BitMemoria());
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
}