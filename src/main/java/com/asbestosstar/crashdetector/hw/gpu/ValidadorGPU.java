package com.asbestosstar.crashdetector.hw.gpu;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.lwjgl.glfw.GLFW.*;


//Gracias a TLauncher por parte de la información sobre cómo obtener los datos de la GPU de la jar descompilado. También necesitamos examinar el instalador .exe para obtener información sobre cómo configurar la GPU en Windows, una vez que lleguemos al punto de añadir soluciones rápidas para la configuración de las GPUs.
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

        try {
            ejecutar();
            System.out.println(HacerVerificacionGPU.LOG_FIN);

        } catch (Throwable t) {
            System.out.println(HacerVerificacionGPU.LOG_ERROR);
            System.err.println("ERROR FATAL: " + t.getMessage());
            t.printStackTrace();
            System.out.println(HacerVerificacionGPU.NO_FATAL);
        }
    }

    private static void ejecutar() {
        System.out.println(MSG_SO + System.getProperty("os.name"));

        String gpuActiva = obtenerGPUActivaOpenGL();
        System.out.println(MSG_GPU_ACTIVA + gpuActiva);

        List<InfoGPU> todas = obtenerGPUsDelSistema();

        if (todas.isEmpty()) {
            System.out.println(MSG_NO_GPUS);
        } else {
            todas.forEach(System.out::println);
        }

        if (gpuActiva == null || gpuActiva.isEmpty() || todas.isEmpty()) {
            System.out.println(MSG_ANALISIS_CANCELADO);
            return;
        }

        if (estaUsandoMejorGPU(gpuActiva, todas)) {
            System.out.println(MSG_EXITO);
        } else {
            System.out.println(HacerVerificacionGPU.MSG_ADVERTENCIA);
        }
    }

    // ==========================================================
    // DETECCIÓN OPENGL (ZONA DE RIESGO CONTROLADA)
    // ==========================================================
    private static String obtenerGPUActivaOpenGL() {
        long ventana = 0;

        System.out.println(HacerVerificacionGPU.LOG_OPENGL_INICIO);

        try {
            if (!GLFW.glfwInit()) {
                return "Error GLFW";
            }

            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

            ventana = glfwCreateWindow(100, 100, "Detector", 0, 0);
            if (ventana == 0) {
                return "Error ventana";
            }

            glfwMakeContextCurrent(ventana);

            GL.createCapabilities();

            String renderer = GL11.glGetString(GL11.GL_RENDERER);

            System.out.println(HacerVerificacionGPU.LOG_OPENGL_FIN);

            return renderer != null ? renderer : "Desconocido";

        } catch (Throwable t) {
            System.out.println(HacerVerificacionGPU.LOG_OPENGL_ERROR);
            return "Error OpenGL";

        } finally {
            try {
                if (ventana != 0) glfwDestroyWindow(ventana);
            } catch (Exception ignored) {}

            try {
                glfwTerminate();
            } catch (Exception ignored) {}
        }
    }

    // ==========================================================
    // MODELO GPU
    // ==========================================================
    private static class InfoGPU {
        String nombre;
        String tipo = "Desconocido";

        public String toString() {
            return "GPU detectada: " + nombre + " | Tipo: " + tipo;
        }
    }

    // ==========================================================
    // DETECCIÓN POR SISTEMA
    // ==========================================================
    private static List<InfoGPU> obtenerGPUsDelSistema() {
        List<InfoGPU> lista = new ArrayList<>();

        try {
            String so = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

            if (so.contains("win")) return obtenerWindows();
            if (so.contains("linux")) return obtenerLinux();
            if (so.contains("mac")) return obtenerMac();

        } catch (Exception e) {
            System.err.println("Error detectando SO");
        }

        return lista;
    }

    private static List<InfoGPU> obtenerWindows() {
        List<InfoGPU> lista = new ArrayList<>();

        try {
            Process p = new ProcessBuilder("wmic", "path", "win32_VideoController", "get", "name")
                    .start();

            p.waitFor(5, TimeUnit.SECONDS);

            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;

            while ((linea = r.readLine()) != null) {
                try {
                    if (linea.trim().isEmpty() || linea.contains("Name")) continue;

                    InfoGPU g = new InfoGPU();
                    g.nombre = linea.trim();
                    clasificarGPU(g);

                    lista.add(g);

                } catch (Exception ignored) {}
            }

        } catch (Exception e) {
            System.err.println("Error WMIC");
        }

        return lista;
    }

    private static List<InfoGPU> obtenerLinux() {
        List<InfoGPU> lista = new ArrayList<>();

        try {
            Process p = Runtime.getRuntime().exec("lspci");
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String linea;
            while ((linea = r.readLine()) != null) {
                try {
                    if (linea.toLowerCase().contains("vga")) {
                        InfoGPU g = new InfoGPU();
                        g.nombre = linea;
                        clasificarGPU(g);
                        lista.add(g);
                    }
                } catch (Exception ignored) {}
            }

        } catch (Exception e) {
            System.err.println("Error Linux");
        }

        return lista;
    }

    private static List<InfoGPU> obtenerMac() {
        return new ArrayList<>(); // Simplificado
    }

    // ==========================================================
    // CLASIFICACIÓN
    // ==========================================================
    private static void clasificarGPU(InfoGPU g) {
        try {
            String n = g.nombre.toLowerCase();

            if (n.contains("nvidia") || n.contains("rtx") || n.contains("gtx")) {
                g.tipo = "Discreta";
            } else if (n.contains("radeon") || n.contains("amd")) {
                g.tipo = "Discreta";
            } else if (n.contains("intel")) {
                g.tipo = "Integrada";
            }

        } catch (Exception e) {
            g.tipo = "Desconocido";
        }
    }

    // ==========================================================
    // ANÁLISIS FINAL
    // ==========================================================
    private static boolean estaUsandoMejorGPU(String activa, List<InfoGPU> todas) {
        try {
            String lower = activa.toLowerCase();

            if (lower.contains("nvidia") || lower.contains("radeon") || lower.contains("rtx")) {
                return true;
            }

            for (InfoGPU g : todas) {
                if (g.tipo.equals("Discreta")) {
                    return false;
                }
            }

        } catch (Exception e) {
            System.err.println("Error análisis");
        }

        return true;
    }
}