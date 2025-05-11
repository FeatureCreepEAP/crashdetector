package com.asbestosstar.crashdetectormc.analyzador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Analizador dedicado a detectar problemas relacionados con controladores (drivers)
 * de vídeo/OpenGL.  La clase reconoce firmas de «Bad video drivers» y, ahora,
 * mantiene un conjunto separado de patrones que indican explícitamente una GPU
 * no compatible (unsupported/old videocard).
 */
public class Drivers implements Verificaciones {

    /** Indica si este verificador identificó un problema. */
    private boolean activado = false;

    /** Patrones genéricos de fallos de driver/ OpenGL. Desde signatures.json de TLauncher*/
    private static final String[] DRIVER_PATTERNS = {
        // Mensajes LWJGL / GLFW
        "Pixel format not accelerated",
        "The driver does not appear to support OpenGL",
        "GLFW error 65542",   // WGL: driver sin soporte
        "GLFW error 65543",   // "OpenGL profile requested but …"
        "No context is current or a function that is not available in the current context",
        "The driver does not appear to support framebuffer objects",
        // Excepciones típicas
        "org.lwjgl.LWJGLException",
        // DLLs Intel / AMD / Mesa que suelen fallar
        "atio6axx.dll", "atioglxx.dll"
        // Otros
    };

    /**
     * Patrones que indican explícitamente que la GPU es demasiado antigua o no
     * soporta las características requeridas.  Se maneja por separado para
     * ofrecer un mensaje más claro al usuario.
     */
    private static final String[] UNSUPPORTED_GPU_PATTERNS = {
        "old-videocard",
        "unsupported by videocard",
        "Your video card does not meet the requirements",
        "need to purchase a newer video card",
        "videocard is too old",
        "does not support OpenGL 3",
        "OpenGL unsupported by videocard"
    };

    @Override
    public void verificar(String log, CDStringBuilder mensaje) {
        if (log.contains("EXCEPTION_ACCESS_VIOLATION") && log.contains("atio6axx.dll")) {
            procesarProblemaAMD(mensaje);
            return;
        }
        if (log.contains("EXCEPTION_ACCESS_VIOLATION") && log.contains("nouveau")) {
            mensaje.append(nl_html)
                   .append(MonitorDePID.idioma.probelma_con_graficas_nouveau());
            activado = true;
            return;
        }
        if (log.contains("EXCEPTION_ACCESS_VIOLATION") && log.contains("PhysX_64.dll")) {
        	procesarProblemaGraficos(mensaje);
            return;
        }
        
        
        if (log.contains("EXCEPTION_ACCESS_VIOLATION") && log.contains("glfw.dll")) {
            procesarProblemaGraficos(mensaje);
            return;
        }
        
        
        verificarProblemasIntel(log, mensaje);
        if (activado) return;


        // Nueva detección específica de GPU no compatible --------
        if (contienePatron(log, UNSUPPORTED_GPU_PATTERNS)) {
            procesarGpuNoCompatible(mensaje);
            return;
        }

        // Detección general de driver ----------------------------
        if (contienePatron(log, DRIVER_PATTERNS)) {
            procesarProblemaGraficos(mensaje);
            return;
        }

        // Heurística de la última línea (respaldo) ---------------
        String ultimaLinea = obtenerUltimaLinea(log);
        if (ultimaLinea != null && contienePatron(ultimaLinea, DRIVER_PATTERNS)) {
            procesarProblemaGraficos(mensaje);
        } else if (ultimaLinea != null && contienePatron(ultimaLinea, UNSUPPORTED_GPU_PATTERNS)) {
            procesarGpuNoCompatible(mensaje);
        }
    }

    private void procesarProblemaGraficos(CDStringBuilder mensaje) {
        boolean esWindows = esWindows();
        boolean tieneNvidia = esWindows && tieneNvidiaGPU();
        boolean esWindowsNuevo = esWindows && esWindows11OServer2025();

        if (tieneNvidia) {
            if (esWindowsNuevo) {
                mensaje.append(nl_html)
                       .append(MonitorDePID.idioma.problema_con_graficas_nvidia_windows_nuevo());
            } else {
                mensaje.append(nl_html)
                       .append(MonitorDePID.idioma.problema_con_graficas_nvidia_windows_viejo());
            }
        } else {
            mensaje.append(nl_html)
                   .append(MonitorDePID.idioma.probelma_con_graficas_general());
        }
        activado = true;
    }

    private void procesarGpuNoCompatible(CDStringBuilder mensaje) {
        mensaje.append(nl_html)
               .append(MonitorDePID.idioma.gpu_no_compatible()); // Debe existir en el bundle de idioma
        activado = true;
    }

    private void procesarProblemaAMD(CDStringBuilder mensaje) {
        mensaje.append(nl_html)
               .append(MonitorDePID.idioma.problema_con_graficas_ati());
        activado = true;
    }

    private boolean contienePatron(String texto, String[] patrones) {
        for (String p : patrones) {
            if (texto.contains(p)) return true;
        }
        return false;
    }

    private String obtenerUltimaLinea(String log) {
        String[] lineas = log.split(nl);
        return lineas.length > 0 ? lineas[lineas.length - 1] : null;
    }

    private boolean esWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    private boolean tieneNvidiaGPU() {
        if (!esWindows()) return false;
        try {
            Process p = Runtime.getRuntime().exec("wmic path win32_VideoController get name");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String l;
                while ((l = br.readLine()) != null) {
                    if (l.toLowerCase().contains("nvidia")) return true;
                }
            }
        } catch (IOException e) {
            // Silenciar: simplemente devolvemos false.
        }
        return false;
    }

    private boolean esWindows11OServer2025() {
        if (!esWindows()) return false;
        String[] v = System.getProperty("os.version").split("\\.");
        try {
            int build = v.length > 2 ? Integer.parseInt(v[2]) : 0;
            return "10".equals(v[0]) && "0".equals(v[1]) && build >= 22000;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    
    
    
 // En la clase Drivers (analizador)
    private void verificarProblemasIntel(String log, CDStringBuilder mensaje) {
        String[] dllsIntel = {
            "ig7icd32.dll", "ig7icd64.dll",
            "ig75icd32.dll", "ig75icd64.dll",
            "ig8icd64.dll",
            "ig9icd32.dll", "ig9icd64.dll"
        };//https://tlauncher.org/en/ig9icd32-dll-error.html TODO para intel


        for (String dll : dllsIntel) {
            if (log.contains(dll)&&log.contains("EXCEPTION_ACCESS_VIOLATION")) {
                procesarProblemaIntel(mensaje);
                return;
            }
        }
    }

    private void procesarProblemaIntel(CDStringBuilder mensaje) {
        mensaje.append(nl_html)
               .append(MonitorDePID.idioma.problema_con_graficas_intel());
        activado = true;
    }
    
    
    
    
    
    
    
//    private String obtenerVersionControladorIntel() {
//        if (!esWindows()) return null;
//        
//        try {
//            Process p = Runtime.getRuntime().exec(
//                "wmic path win32_VideoController where \"Name like '%Intel%'\" get DriverVersion /value"
//            );
//            
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
//                String linea;
//                String ultimaVersion = null;
//                while ((linea = br.readLine()) != null) {
//                    if (linea.startsWith("DriverVersion=")) {
//                        String version = linea.split("=")[1].trim();
//                        if (version.compareTo(ultimaVersion) > 0) {
//                            ultimaVersion = version;
//                        }
//                    }
//                }
//                return ultimaVersion;
//            }
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    private boolean esVersionIntelObsoleta(String version) {
//        String[] partes = version.split("\\.");
//        if (partes.length < 1) return true;
//        
//        try {
//            int major = Integer.parseInt(partes[0]);
//            return major < 15;
//        } catch (NumberFormatException e) {
//            return true;
//        }
//    }
    
    
    

    @Override
    public Verificaciones nueva() {
        return new Drivers();
    }

    @Override
    public boolean activado() {
        return activado;
    }
}
