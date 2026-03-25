package com.asbestosstar.crashdetector.hw.gpu;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.lwjgl.glfw.GLFW.*;

//Gracias a TLauncher por parte de la información sobre cómo obtener los datos de la GPU.
//Clase unificada: Detección Segura + Análisis Detallado.
public class ValidadorGPU {

    // Mensajes generales
    public static final String MSG_SO = "Sistema Operativo detectado: ";
    public static final String MSG_GPU_ACTIVA = "GPU en uso: ";
    public static final String MSG_NO_GPUS = "No se detectaron GPUs o hubo un error.";
    public static final String MSG_ANALISIS_CANCELADO = "Análisis cancelado.";
    public static final String MSG_EXITO = "ÉXITO: Se usa la mejor GPU.";

    // ==========================================================
    // MAIN SEGURO CON MARCADORES
    // ==========================================================
    public static void main() {
        System.out.println(HacerVerificacionGPU.LOG_INICIO);
        CrashDetectorLogger.log(HacerVerificacionGPU.LOG_INICIO);
        
        try {
            ejecutar();
            System.out.println(HacerVerificacionGPU.LOG_FIN);

        } catch (Throwable t) {
            System.out.println(HacerVerificacionGPU.LOG_ERROR);
            System.err.println("ERROR FATAL: " + t.getMessage());
            t.printStackTrace();
            CrashDetectorLogger.log(HacerVerificacionGPU.LOG_ERROR + ": " + t.getMessage());
            System.out.println(HacerVerificacionGPU.NO_FATAL);
        }
    }

    private static void ejecutar() {
        String soName = System.getProperty("os.name");
        System.out.println(MSG_SO + soName);
        CrashDetectorLogger.log(MSG_SO + soName);

        // ---------------------------------------------------------
        // VALIDACIÓN DE GPU
        // ---------------------------------------------------------
        String gpuActiva = obtenerGPUActivaOpenGL();
        System.out.println(MSG_GPU_ACTIVA + gpuActiva);

        if (gpuActiva != null && !gpuActiva.startsWith("Error") && !gpuActiva.equals("Desconocido")) {
            CrashDetectorLogger.log("GPU ACTIVA DETECTADA: " + gpuActiva);
        } else {
            CrashDetectorLogger.log("ADVERTENCIA: No se pudo determinar la GPU activa vía OpenGL (" + gpuActiva + ")");
        }

        List<InfoGPU> todas = obtenerGPUsDelSistema();

        if (todas.isEmpty()) {
            System.out.println(MSG_NO_GPUS);
            CrashDetectorLogger.log(MSG_NO_GPUS);
        } else {
            CrashDetectorLogger.log("--- LISTA DE GPUs EN EL SISTEMA ---");
            for (InfoGPU g : todas) {
                if (g.nombre != null && !g.nombre.isEmpty()) {
                    CrashDetectorLogger.log(g.toString());
                }
            }
            CrashDetectorLogger.log("-----------------------------------");
        }

        if (gpuActiva == null || gpuActiva.isEmpty() || todas.isEmpty()) {
            System.out.println(MSG_ANALISIS_CANCELADO);
            CrashDetectorLogger.log(MSG_ANALISIS_CANCELADO);
            return;
        }

        InfoGPU mejorGPU = encontrarMejorGPU(todas);
        if (mejorGPU != null) {
            CrashDetectorLogger.log("MEJOR GPU DISPONIBLE (Teórica): " + mejorGPU.nombre + " (" + mejorGPU.tipo + ")");
        }

        if (estaUsandoMejorGPU(gpuActiva, todas)) {
            System.out.println(MSG_EXITO);
            CrashDetectorLogger.log(MSG_EXITO);
        } else {
            System.out.println(HacerVerificacionGPU.MSG_ADVERTENCIA);
            CrashDetectorLogger.log(HacerVerificacionGPU.MSG_ADVERTENCIA);
            if (mejorGPU != null) {
                CrashDetectorLogger.log("ACCIÓN SUGERIDA: El sistema debería estar usando " + mejorGPU.nombre + " pero está usando " + gpuActiva);
            }
        }
    }

    // ==========================================================
    // DETECCIÓN OPENGL SEGURA (NO ROMPE MINECRAFT)
    // ==========================================================
    private static String obtenerGPUActivaOpenGL() {
        System.out.println(HacerVerificacionGPU.LOG_OPENGL_INICIO);

        boolean nosotrosIniciamos = false;
        long ventanaCreada = 0;

        try {
            // 1. Verificar si ya hay un contexto actual (Minecraft ya lo inicializó)
            long currentContext = glfwGetCurrentContext();
            
            if (currentContext != 0) {
                // Si hay contexto, lo reutilizamos.
                System.out.println("[GPU Check] Usando contexto OpenGL existente de Minecraft.");
                
                try {
                    GL.getCapabilities(); 
                } catch (Exception e) {
                    GL.createCapabilities(); 
                }

                String renderer = GL11.glGetString(GL11.GL_RENDERER);
                System.out.println(HacerVerificacionGPU.LOG_OPENGL_FIN);
                return renderer != null ? renderer : "Desconocido";
            }

            // 2. Si no hay contexto, creamos uno temporal
            System.out.println("[GPU Check] Creando contexto OpenGL temporal.");
            if (!GLFW.glfwInit()) {
                return "Error GLFW";
            }
            nosotrosIniciamos = true;

            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

            ventanaCreada = glfwCreateWindow(100, 100, "Detector", 0, 0);
            if (ventanaCreada == 0) {
                return "Error ventana";
            }

            glfwMakeContextCurrent(ventanaCreada);
            GL.createCapabilities();

            String renderer = GL11.glGetString(GL11.GL_RENDERER);
            System.out.println(HacerVerificacionGPU.LOG_OPENGL_FIN);
            return renderer != null ? renderer : "Desconocido";

        } catch (Throwable t) {
            System.out.println(HacerVerificacionGPU.LOG_OPENGL_ERROR);
            CrashDetectorLogger.log("Error en detección OpenGL: " + t.getMessage());
            return "Error OpenGL";
        } finally {
            // 3. LIMPIEZA SEGURA
            if (nosotrosIniciamos) {
                try { if (ventanaCreada != 0) glfwDestroyWindow(ventanaCreada); } catch (Exception ignored) {}
                try { glfwTerminate(); } catch (Exception ignored) {}
            }
        }
    }

    // ==========================================================
    // MODELO DE DATOS
    // ==========================================================
    private static class InfoGPU {
        String nombre;
        long memoriaMB = 0;
        String relojNucleo = "N/A";
        String relojMemoria = "N/A";
        String tipo = "Desconocido"; // Integrada, Discreta, Acelerador, Matrox_Embebida, Matrox_Dedicada

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("GPU: ").append(nombre);
            if (memoriaMB > 0) sb.append(" | VRAM: ").append(memoriaMB).append(" MB");
            if (!"N/A".equals(relojNucleo)) sb.append(" | Núcleo: ").append(relojNucleo);
            if (!"N/A".equals(relojMemoria)) sb.append(" | Mem: ").append(relojMemoria);
            sb.append(" | Tipo: ").append(tipo);
            return sb.toString();
        }
    }

    // ==========================================================
    // ENUMERACIÓN DE HARDWARE POR SO
    // ==========================================================
    private static List<InfoGPU> obtenerGPUsDelSistema() {
        String so = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

        if (so.contains("win")) return obtenerGPUsWindows();
        if (so.contains("mac")) return obtenerGPUsMacOS();
        if (so.contains("nux")) return obtenerGPUsLinux();

        // Sistemas Unix-like
        if (so.contains("nix") || so.contains("nbsd") || so.contains("bsd") || so.contains("sunos") || so.contains("unix")) {
            List<InfoGPU> gpus = obtenerGPUsUnix();
            if (!gpus.isEmpty()) return gpus;
        }

        return new ArrayList<>();
    }

    // WINDOWS: Usa wmic
private static List<InfoGPU> obtenerGPUsWindows() {
    List<InfoGPU> gpus = new ArrayList<>();
    try {
        ProcessBuilder pb = new ProcessBuilder("wmic", "path", "win32_VideoController", "get", "name");
        pb.redirectErrorStream(true);
        Process p = pb.start();
        p.waitFor(5, TimeUnit.SECONDS);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String linea;
            reader.readLine();  // Skip header line

            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                // Only split the name, no need to handle RAM
                String[] partes = linea.trim().split("\\s+");
                if (partes.length > 0) {
                    InfoGPU info = new InfoGPU();
                    info.nombre = partes[0].trim();  // GPU Name

                    // Classify the GPU based on its name
                    clasificarGPU(info);  
                    gpus.add(info);
                }
            }
        }
    } catch (Exception e) {
        System.err.println("Error ejecutando wmic en Windows: " + e.getMessage());
    }
    return gpus;
}

    // MACOS: Usa system_profiler
    private static List<InfoGPU> obtenerGPUsMacOS() {
        List<InfoGPU> gpus = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec("system_profiler SPDisplaysDataType");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String linea;
                InfoGPU actual = null;
                while ((linea = reader.readLine()) != null) {
                    linea = linea.trim();
                    if (linea.startsWith("Chipset Model:")) {
                        if (actual != null) gpus.add(actual);
                        actual = new InfoGPU();
                        actual.nombre = linea.substring(linea.indexOf(":") + 1).trim();
                        clasificarGPU(actual);
                    } else if (actual != null) {
                        if (linea.startsWith("VRAM (Total):")) {
                            String memStr = linea.substring(linea.indexOf(":") + 1).trim();
                            try {
                                String[] partes = memStr.split(" ");
                                long val = Long.parseLong(partes[0]);
                                if (memStr.contains("GB")) val *= 1024;
                                actual.memoriaMB = val;
                            } catch (Exception e) { /* Ignorar */ }
                        }
                    }
                }
                if (actual != null) gpus.add(actual);
            }
        } catch (Exception e) {
            System.err.println("Error en system_profiler (macOS): " + e.getMessage());
        }
        return gpus;
    }

    // LINUX: Usa lspci
    private static List<InfoGPU> obtenerGPUsLinux() {
        return parsearLspci();
    }

    // UNIX (BSD, Solaris, etc.)
    private static List<InfoGPU> obtenerGPUsUnix() {
        List<InfoGPU> gpus = parsearLspci();
        if (!gpus.isEmpty()) return gpus;

        gpus = parsearPciConf();
        if (!gpus.isEmpty()) return gpus;

        gpus = parsearPrtConf();
        if (!gpus.isEmpty()) return gpus;

        gpus = parsearHwConfig();
        return gpus;
    }

    // --- Métodos Auxiliares de Parsing ---

    private static List<InfoGPU> parsearLspci() {
        List<InfoGPU> gpus = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec("lspci -v");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String linea;
                InfoGPU actual = null;
                while ((linea = reader.readLine()) != null) {
                    String lineaLower = linea.toLowerCase();
                    if (lineaLower.contains("vga") || lineaLower.contains("3d") || lineaLower.contains("display")) {
                        if (actual != null) gpus.add(actual);
                        actual = new InfoGPU();
                        actual.nombre = linea.substring(linea.indexOf(":") + 1).trim();
                        clasificarGPU(actual);
                    }
                }
                if (actual != null) gpus.add(actual);
            }
        } catch (Exception e) {
            try {
                Process p = Runtime.getRuntime().exec("lspci");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        String lineaLower = linea.toLowerCase();
                        if (lineaLower.contains("vga") || lineaLower.contains("3d") || lineaLower.contains("display")) {
                            InfoGPU info = new InfoGPU();
                            info.nombre = linea.substring(linea.indexOf(":") + 1).trim();
                            clasificarGPU(info);
                            gpus.add(info);
                        }
                    }
                }
            } catch (Exception ex) { /* Ignorar */ }
        }
        return gpus;
    }

    private static List<InfoGPU> parsearPciConf() {
        List<InfoGPU> gpus = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec("pciconf -lv");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String lower = linea.toLowerCase();
                    if (lower.contains("vendor") || lower.contains("device")) {
                        if (lower.contains("nvidia") || lower.contains("amd") || lower.contains("ati") || 
                            lower.contains("intel") || lower.contains("matrox") || lower.contains("moore threads") ||
                            lower.contains("jingjia") || lower.contains("zhaoxin")) {
                            InfoGPU info = new InfoGPU();
                            info.nombre = linea.trim();
                            clasificarGPU(info);
                            gpus.add(info);
                        }
                    }
                }
            }
        } catch (Exception e) { /* Ignorar */ }
        return gpus;
    }

    private static List<InfoGPU> parsearPrtConf() {
        List<InfoGPU> gpus = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec("prtconf -v");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String lower = linea.toLowerCase();
                    if (lower.contains("display") || lower.contains("model")) {
                        if (lower.contains("nvidia") || lower.contains("amd") || lower.contains("ati") || 
                            lower.contains("intel") || lower.contains("matrox")) {
                            InfoGPU info = new InfoGPU();
                            info.nombre = linea.trim();
                            clasificarGPU(info);
                            gpus.add(info);
                        }
                    }
                }
            }
        } catch (Exception e) { /* Ignorar */ }
        return gpus;
    }

    private static List<InfoGPU> parsearHwConfig() {
        List<InfoGPU> gpus = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec("hwconfig");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String lower = linea.toLowerCase();
                    if (lower.contains("video") || lower.contains("vga") || lower.contains("display")) {
                        InfoGPU info = new InfoGPU();
                        info.nombre = linea.trim();
                        clasificarGPU(info);
                        gpus.add(info);
                    }
                }
            }
        } catch (Exception e) { /* Ignorar */ }
        return gpus;
    }

// ==========================================================
// CLASIFICACIÓN AVANZADA
// ==========================================================
private static void clasificarGPU(InfoGPU info) {
    if (info == null || info.nombre == null) return;
    String lower = info.nombre.toLowerCase(Locale.ENGLISH);

    // 1. Aceleradores Gráficos (Tesla, Quadro, etc.)
    if (lower.contains("tesla") || lower.contains("quadro") || lower.contains("rtx a")) {
        info.tipo = "Acelerador Gráfico";
        info.nombre = info.nombre.replace("NVIDIA", "Nvidia");
        return;
    }

    // 2. NVIDIA (Discretas de Alto Rendimiento)
    if (lower.contains("nvidia") || lower.contains("geforce") || lower.contains("rtx") || lower.contains("gtx")) {
        info.tipo = "Discreta (Alto Rendimiento)";
        info.nombre = info.nombre.replace("NVIDIA", "Nvidia");
        return;
    }

    // 3. AMD (Clasificado un poco más bajo que NVIDIA)
    if (lower.contains("radeon") || lower.contains("amd") || lower.contains("firepro")) {
        // Si la GPU de AMD es una iGPU (integrada), darle menor prioridad
        if (lower.contains("apu") || lower.contains("integrada")) {
            info.tipo = "Integrada (AMD)";
        } else {
            info.tipo = "Discreta (Alto Rendimiento AMD)";
        }
        return;
    }

    // 4. Intel (posibles iGPUs o discretas)
    if (lower.contains("intel")) {
        info.tipo = lower.contains("arc") ? "Discreta (Alto Rendimiento)" : "Integrada";
        return;
    }

    // 5. Apple Silicon
    if (lower.contains("apple m")) {
        info.tipo = "Integrada (Alto Rendimiento)";
        return;
    }

    // 6. Caso Especial Matrox
    if (lower.contains("matrox")) {
        boolean esDedicada = lower.contains("parhelia") || lower.contains("millennium") || 
                             lower.contains("m912") || lower.contains("orion") || 
                             lower.contains("apvi") || lower.contains("m-series");

        if (esDedicada) {
            info.tipo = "Matrox_Dedicada";
        } else {
            info.tipo = "Matrox_Embebida";
        }
        return;
    }

    // 7. Marcas Chinas y Nuevas Entradas
    if (lower.contains("mthreads") || lower.contains("moore threads")) {
        info.tipo = "Discreta (Nueva Generación)";
        return;
    }
    if (lower.contains("jingjia") || lower.contains("jm5")) {
        info.tipo = "Integrada";
        return;
    }
    if (lower.contains("zhaoxin") || lower.contains("glenfly")) {
        info.tipo = "Discreta (Nueva Generación)";
        return;
    }
}

    // ==========================================================
    // ANÁLISIS FINAL
    // ==========================================================
    
    private static InfoGPU encontrarMejorGPU(List<InfoGPU> todas) {
        for (InfoGPU g : todas) {
            if ("Acelerador Gráfico".equals(g.tipo) || "Discreta (Alto Rendimiento)".equals(g.tipo)) {
                return g;
            }
        }
        // Si no hay discreta, devolver la primera disponible
        if (!todas.isEmpty()) {
            return todas.get(0);
        }
        return null;
    }

    private static boolean estaUsandoMejorGPU(String gpuActiva, List<InfoGPU> todasLasGPUs) {
        String activaLower = gpuActiva.toLowerCase(Locale.ENGLISH);

        InfoGPU infoActiva = new InfoGPU();
        infoActiva.nombre = gpuActiva;
        clasificarGPU(infoActiva);

        // 1. Si la activa es potente, es la mejor.
        if (infoActiva.tipo.equals("Acelerador Gráfico") || 
            infoActiva.tipo.equals("Discreta (Alto Rendimiento)") ||
            infoActiva.tipo.equals("Integrada (Alto Rendimiento)") ||
            infoActiva.tipo.equals("Discreta (Nueva Generación)")) {
            return true;
        }
        
        // Si es Matrox Dedicada, se considera "Buena", pero verificamos si hay algo MEJOR
        if (infoActiva.tipo.equals("Matrox_Dedicada")) {
            for (InfoGPU hw : todasLasGPUs) {
                if (hw.tipo.equals("Acelerador Gráfico") || hw.tipo.equals("Discreta (Alto Rendimiento)")) {
                    CrashDetectorLogger.log("Detectada Matrox Dedicada activa, pero existe hardware superior: " + hw.nombre);
                    return false;
                }
            }
            return true;
        }

        // 2. Si la activa es Matrox Embebida o Integrada estándar
        if (infoActiva.tipo.equals("Matrox_Embebida") || infoActiva.tipo.equals("Integrada") || activaLower.contains("llvmpipe") || activaLower.contains("software")) {
            for (InfoGPU hw : todasLasGPUs) {
                if (hw.tipo.equals("Acelerador Gráfico") || 
                    hw.tipo.equals("Discreta (Alto Rendimiento)") ||
                    hw.tipo.equals("Discreta (Nueva Generación)") ||
                    hw.tipo.equals("Matrox_Dedicada")) {
                    CrashDetectorLogger.log("Detectada GPU de bajo rendimiento activa, pero se encontró hardware superior: " + hw.nombre);
                    return false;
                }
            }
        }

        return true;
    }
}