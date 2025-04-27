package com.asbestosstar.crashdetectormc.analyzador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.asbestosstar.crashdetectormc.CDStringBuilder;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class Drivers implements Verificaciones {

	public boolean activado=false;

	
    @Override
    public void verificar(String str, CDStringBuilder messanje) {
        if (str.contains("EXCEPTION_ACCESS_VIOLATION") && str.contains("atio6axx.dll")) {
            procesarProblemaAMD(str, messanje);
        } else if (str.contains("EXCEPTION_ACCESS_VIOLATION") && str.contains("nouveau")) {
            messanje.append(nl_html).append(MonitorDePID.idioma.probelma_con_graficas_nouveau());
        } 
        else if (str.contains("glfw.dll")) {
        	procesarProblemaGraficos(messanje);
        }
        else {
            String ultimaLinea = obtenerUltimaLinea(str);
            
            if (ultimaLinea != null && contieneErrorGraficos(ultimaLinea)) {
                procesarProblemaGraficos(messanje);
            }
        }
    }
    
    
    
    /*
     * 
     * FATAL ERROR in native method: Thread[pool-2-thread-1,5,main]: No context is current or a function that is not available in the current context was called. The JVM will abort execution.
	at org.lwjgl.opengl.GL11C.nglGetString(org.lwjgl.opengl@3.3.1+7/Native Method)
	at org.lwjgl.opengl.GL11C.glGetString(org.lwjgl.opengl@3.3.1+7/GL11C.java:978)
	at net.minecraftforge.fml.earlydisplay.DisplayWindow.initRender(fmlearlydisplay@1.20.1-47.2.32/DisplayWindow.java:212)
	at net.minecraftforge.fml.earlydisplay.DisplayWindow.lambda$start$5(fmlearlydisplay@1.20.1-47.2.32/DisplayWindow.java:295)
	at net.minecraftforge.fml.earlydisplay.DisplayWindow$$Lambda$434/0x00000294dc20f6a8.run(fmlearlydisplay@1.20.1-47.2.32/Unknown Source)
     * 
     * 
     */

    private void procesarProblemaGraficos(CDStringBuilder mensaje) {
        boolean esWindows = esWindows();
        boolean tieneNvidia = esWindows ? tieneNvidiaGPU() : false;
        boolean esWindowsNuevo = esWindows && esWindows11OServer2025();

        if (tieneNvidia) {
            if (esWindowsNuevo) {
                mensaje.append(nl_html).append(MonitorDePID.idioma.problema_con_graficas_nvidia_windows_nuevo());
                activado=true;
            } else {
                mensaje.append(nl_html).append(MonitorDePID.idioma.problema_con_graficas_nvidia_windows_viejo());
                activado=true;
            }
        } else {
            mensaje.append(nl_html).append(MonitorDePID.idioma.probelma_con_graficas_general());
            activado=true;
        }
        
    }

    private boolean esWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    private boolean tieneNvidiaGPU() {
        if (!esWindows()) return false; // Doble verificación
        
        try {
            Process process = Runtime.getRuntime().exec("wmic path win32_VideoController get name");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.toLowerCase().contains("nvidia")) {
                    return true;
                }
            }
        } catch (IOException e) {

        }
        return false;
    }
    
    
    private boolean esWindows11OServer2025() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (!osName.contains("windows")) return false;

        String osVersion = System.getProperty("os.version");
        String[] partes = osVersion.split("\\.");
        
        try {
            int major = Integer.parseInt(partes[0]);
            int minor = Integer.parseInt(partes[1]);
            int build = partes.length > 2 ? Integer.parseInt(partes[2]) : 0;
            
            // Windows 11 (build 22000+), Server 2022 (20348+), Server 2025 (25xxx+)
            return (major == 10 && minor == 0 && build >= 22000);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void procesarProblemaAMD(String log, CDStringBuilder mensaje) {
        mensaje.append(nl).append(MonitorDePID.idioma.problema_con_graficas_ati());
    }
    
    private String obtenerUltimaLinea(String log) {
        String[] lineas = log.split(nl);
        return lineas.length > 0 ? lineas[lineas.length - 1] : null;
    }

    private boolean contieneErrorGraficos(String linea) {
        return linea.contains("Backend library: LWJGL") ||
               linea.contains("Trying GL version") ||
               linea.contains("you probably have a driver issue");
    }
    // ... (resto de métodos permanecen igual)

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new Drivers();
	}
	
	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}
}