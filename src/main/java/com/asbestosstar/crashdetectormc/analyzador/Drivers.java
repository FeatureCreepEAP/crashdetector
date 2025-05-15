package com.asbestosstar.crashdetectormc.analyzador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
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
    private final CDStringBuilder mensajes = new CDStringBuilder();


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
    public void verificar(Consola consola) {
    	String log=consola.contento_verificar;


        if (log.contains("EXCEPTION_ACCESS_VIOLATION") && log.contains("atio6axx.dll")) {
            procesarProblemaAMD();
            return;
        }
        if (log.contains("EXCEPTION_ACCESS_VIOLATION") && log.contains("nouveau")) {
            mensajes.append(MonitorDePID.idioma.probelma_con_graficas_nouveau());
            activado = true;
            return;
        }
        if (contienePatron(log, new String[]{"PhysX_64.dll", "glfw.dll"})) {
            procesarProblemaGraficos();
            return;
        }

        verificarProblemasIntel(log);
        if (activado) return;

        if (contienePatron(log, UNSUPPORTED_GPU_PATTERNS)) {
            procesarGpuNoCompatible();
            return;
        }

        if (contienePatron(log, DRIVER_PATTERNS)) {
            procesarProblemaGraficos();
            return;
        }

        String ultimaLinea = obtenerUltimaLinea(log);
        if (ultimaLinea != null) {
            if (contienePatron(ultimaLinea, DRIVER_PATTERNS)) {
                procesarProblemaGraficos();
            } else if (contienePatron(ultimaLinea, UNSUPPORTED_GPU_PATTERNS)) {
                procesarGpuNoCompatible();
            }
        }
    }

    private void procesarProblemaGraficos() {
        boolean esWindows = esWindows();
        boolean tieneNvidia = esWindows && tieneNvidiaGPU();
        boolean esWindowsNuevo = esWindows && esWindows11OServer2025();

        if (tieneNvidia) {
            mensajes.append(esWindowsNuevo 
                ? MonitorDePID.idioma.problema_con_graficas_nvidia_windows_nuevo()
                : MonitorDePID.idioma.problema_con_graficas_nvidia_windows_viejo());
        } else {
            mensajes.append(MonitorDePID.idioma.probelma_con_graficas_general());
        }
        activado = true;
    }


    private void procesarGpuNoCompatible() {
        mensajes.append(MonitorDePID.idioma.gpu_no_compatible());
        activado = true;
    }

    private void procesarProblemaAMD() {
        mensajes.append(MonitorDePID.idioma.problema_con_graficas_ati());
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
    private void verificarProblemasIntel(String log) {
        String[] dllsIntel = {
            "ig7icd32.dll", "ig7icd64.dll",
            "ig75icd32.dll", "ig75icd64.dll",
            "ig8icd64.dll",
            "ig9icd32.dll", "ig9icd64.dll"
        };//https://tlauncher.org/en/ig9icd32-dll-error.html TODO para intel


        for (String dll : dllsIntel) {
            if (log.contains(dll) && log.contains("EXCEPTION_ACCESS_VIOLATION")) {
                procesarProblemaIntel();
            	return;
            }
        }
    }

    private void procesarProblemaIntel() {
        mensajes.append(MonitorDePID.idioma.problema_con_graficas_intel());
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
    
    @Override
    public float prioridad() {
        return 800.0f; // Prioridad crítica para errores de drivers [[8]]
    }

    @Override
    public String mensaje() {
        return mensajes.toString().replaceAll("\n", Verificaciones.nl_html);
    }

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_drivers();
	}
}
