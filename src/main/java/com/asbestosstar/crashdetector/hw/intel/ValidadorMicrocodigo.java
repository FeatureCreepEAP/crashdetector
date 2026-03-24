package com.asbestosstar.crashdetector.hw.intel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

public class ValidadorMicrocodigo {

    public static void main() {
        System.out.println("\n--- Validador de Microcódigo Intel ---");
        
        // 1. Verificar Arquitectura
        String arq = System.getProperty("os.arch").toLowerCase(Locale.ENGLISH);
        boolean esX64 = arq.contains("amd64") || arq.contains("x86_64");
        boolean esItanium = arq.contains("ia64");

        if (!esX64 && !esItanium) {
            System.out.println("❌ Arquitectura no compatible: " + arq + ". Se requiere Intel x64 o Itanium.");
            return;
        }

        // 2. Verificar Fabricante
        String fabricante = obtenerFabricanteCPU();
        System.out.println("Arquitectura: " + (esItanium ? "Itanium (IA-64)" : "x64 (Intel/AMD)"));
        System.out.println("Fabricante detectado: " + fabricante);

        if (!fabricante.toLowerCase(Locale.ENGLISH).contains("intel")) {
            System.out.println("❌ Microcódigo no aplicable: El procesador no es Intel.");
            return;
        }

        // 3. Obtener Microcódigo
        String microcodigo = obtenerRevisionMicrocodigo();
        
        if (microcodigo.isEmpty() || microcodigo.equals("Desconocido")) {
            System.out.println("⚠️ No se pudo determinar la revisión del microcódigo.");
        } else {
            System.out.println("✅ Revisión de Microcódigo Intel: " + microcodigo);
        }
    }

    // ==========================================================
    // OBTENER FABRICANTE (CPUID)
    // ==========================================================
    private static String obtenerFabricanteCPU() {
        String so = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

        try {
            if (so.contains("win")) {
                return ejecutarComando("wmic", "cpu", "get", "Manufacturer");
            } else if (so.contains("linux") || so.contains("nix") || so.contains("nux") || so.contains("bsd")) {
                // Linux/Unix: grep "vendor_id" /proc/cpuinfo
                return parsearProcCpuinfo("vendor_id");
            } else if (so.contains("mac")) {
                // macOS: sysctl -n machdep.cpu.vendor
                return ejecutarComando("sysctl", "-n", "machdep.cpu.vendor");
            }
        } catch (Exception e) {
            System.err.println("Error detectando fabricante: " + e.getMessage());
        }
        return "Desconocido";
    }

    // ==========================================================
    // OBTENER REVISIÓN DE MICROCÓDIGO
    // ==========================================================
    private static String obtenerRevisionMicrocodigo() {
        String so = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

        try {
            if (so.contains("win")) {
                // Windows: wmic path win32_processor get Revision
                // Nota: Devuelve el valor en decimal, suele ser stepping + revision
                return ejecutarComando("wmic", "path", "win32_processor", "get", "Revision");
            } else if (so.contains("linux") || so.contains("nix") || so.contains("nux")) {
                // Linux: microcode en /proc/cpuinfo
                String mc = parsearProcCpuinfo("microcode");
                if (!mc.isEmpty()) return mc;

                // Alternativa: dmesg (buscar microcode revised)
                // Comando: dmesg | grep "microcode" | grep "revision"
                // Esto es complejo de parsear de forma limpia sin permisos a veces, 
                // pero /proc/cpuinfo es estándar.
            } else if (so.contains("bsd")) {
                // FreeBSD: sysctl dev.cpu.0.microcode_version
                // Iteramos dev.cpu.X.microcode_version
                String mc = ejecutarComando("sysctl", "-n", "dev.cpu.0.microcode_version");
                if (!mc.isEmpty() && !mc.contains("unknown")) return mc;
                
                // Alternativa para otros BSDs: pciconf o dmidecode (requiere root)
                // Usamos cpuinfo si está disponible en compatibilidad Linux
                return parsearProcCpuinfo("microcode");
            } else if (so.contains("mac")) {
                // macOS: sysctl -n machdep.cpu.microcode_version
                // Nota: macOS no expone fácilmente la versión de microcódigo en todas las versiones.
                String mc = ejecutarComando("sysctl", "-n", "machdep.cpu.microcode_version");
                if (!mc.isEmpty() && !mc.contains("unknown")) return mc;
                
                // Intel Macs a veces lo exponen en registry, pero es poco accesible desde Java.
                return "No expuesto por el SO";
            }
        } catch (Exception e) {
            System.err.println("Error leyendo microcódigo: " + e.getMessage());
        }
        return "Desconocido";
    }

    // ==========================================================
    // HELPERS DE EJECUCIÓN
    // ==========================================================
    
    private static String ejecutarComando(String... comando) {
        try {
            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String linea;
                StringBuilder resultado = new StringBuilder();
                while ((linea = reader.readLine()) != null) {
                    linea = linea.trim();
                    if (linea.isEmpty() || linea.toLowerCase().contains("revision") || linea.toLowerCase().contains("manufacturer")) {
                        continue; // Saltar cabeceras
                    }
                    resultado.append(linea);
                }
                return resultado.toString().trim();
            }
        } catch (Exception e) {
            return "";
        }
    }

    private static String parsearProcCpuinfo(String claveBuscada) {
        try {
            Process p = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    if (linea.toLowerCase().contains(claveBuscada.toLowerCase())) {
                        // Formato: "clave : valor"
                        String[] partes = linea.split(":");
                        if (partes.length > 1) {
                            return partes[1].trim();
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Ignorar
        }
        return "";
    }
}