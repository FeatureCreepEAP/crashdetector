package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class NoTieneMemoria implements Verificaciones {

    private boolean activado = false;
    private final CDStringBuilder mensajes = new CDStringBuilder();

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;

        
        // Verificación principal de memoria
        if (contenidoConsola.contains("java.lang.OutOfMemoryError") ||
            contenidoConsola.contains("Could not reserve enough space for") ||
            contenidoConsola.contains("Native memory allocation failed") ||
            contenidoConsola.contains("swap file increase") ||
            contenidoConsola.contains("Problem with RAM")) {
            
            mensajes.append(MonitorDePID.idioma.noTieneMemoria())
                    .append(Verificaciones.nl_html)
                    .append(MonitorDePID.idioma.recomendacionMemoria());
            
            verificarJVMMemoria();
            activado = true;
        }

        // Verificación específica de PermGen
        if (contenidoConsola.contains("exit code: -805306369") || 
            contenidoConsola.contains("PermGen error")) {
            
            mensajes.append(MonitorDePID.idioma.permGenError())
                    .append(Verificaciones.nl_html);
            
            verificarJVMMemoria();
            activado = true;
        }
    }

    private void verificarJVMMemoria() {
        String modeloJVM = System.getProperty("sun.arch.data.model");
        String arquitecturaOS = System.getProperty("os.arch");
        boolean esJVM32Bits = false;

        if (modeloJVM != null && modeloJVM.equals("32")) {
            esJVM32Bits = true;
        } else if (arquitecturaOS != null) {
            esJVM32Bits = arquitecturaOS.matches("(i?[3-6]86|x86|ppc32|ppc|powerpc|armv\\d+)")
                        || (arquitecturaOS.endsWith("86") && 
                            System.getProperty("os.name").toLowerCase().contains("windows"));
        }

        if (esJVM32Bits) {
            mensajes.append(Verificaciones.nl_html)
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

    @Override
    public float prioridad() {
        return 400.0f; // Prioridad crítica para errores de memoria [[6]]
    }

    @Override
    public String mensaje() {
        return mensajes.toString();
    }
    
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_no_tiene_memoria();
	}
    
}