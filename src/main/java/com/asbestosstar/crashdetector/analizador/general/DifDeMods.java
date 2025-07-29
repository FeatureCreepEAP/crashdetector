package com.asbestosstar.crashdetector.analizador.general;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class DifDeMods implements Verificaciones {
    private boolean activado = false;
    private String mensajeHTML = "";
    
    @Override
    public void verificar(Consola consola) {
        try {
            // Obtener lista actual de mods
            Set<String> modsActual = obtenerMods(MonitorDePID.ultimo_mods);
            
            // Buscar últimos archivos históricos
            Path exitoFile = obtenerUltimoArchivo("exito");
            Path fallaFile = obtenerUltimoArchivo("falla");
            
            // Determinar archivo más reciente globalmente
            Path archivoUltimo = null;
            if (exitoFile != null && fallaFile != null) {
                int numExito = obtenerNumeroArchivo(exitoFile);
                int numFalla = obtenerNumeroArchivo(fallaFile);
                
                if (numExito > numFalla) {
                    archivoUltimo = exitoFile;
                } else {
                    archivoUltimo = fallaFile;
                }
            } else {
                if (exitoFile != null) {
                    archivoUltimo = exitoFile;
                } else {
                    archivoUltimo = fallaFile;
                }
            }

            
            // Ajustar archivos a comparar según el último estado
            if (archivoUltimo != null && archivoUltimo.toString().endsWith(".exito")) {
                fallaFile = null; // Solo comparar con éxito si es el último
            }
            
            // Comparar con último éxito
            List<String> diffExito = new ArrayList<>();
            if (exitoFile != null) {
                Set<String> modsExito = obtenerMods(exitoFile);
                diffExito = compararMods(modsExito, modsActual);
            }
            
            // Comparar con última falla (si no es el último estado)
            List<String> diffFalla = new ArrayList<>();
            if (fallaFile != null) {
                Set<String> modsFalla = obtenerMods(fallaFile);
                diffFalla = compararMods(modsFalla, modsActual);
            }
            
            // Generar HTML si hay diferencias
            if (!diffExito.isEmpty() || !diffFalla.isEmpty()) {
                activado = true;
                mensajeHTML = generarHTML(diffExito, diffFalla, exitoFile != null, fallaFile != null);
            }
            
        } catch (IOException e) {
            CrashDetectorLogger.log("Error comparando mods: " + e.getMessage());
        }
    }

    private Set<String> obtenerMods(Path archivo) throws IOException {
        return Files.readAllLines(archivo).stream()
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.toSet());
    }

    private List<String> compararMods(Set<String> viejos, Set<String> nuevos) {
        List<String> diff = new ArrayList<>();
        nuevos.stream()
              .filter(mod -> !viejos.contains(mod))
              .forEach(mod -> diff.add("+ " + mod));
        viejos.stream()
              .filter(mod -> !nuevos.contains(mod))
              .forEach(mod -> diff.add("- " + mod));
        return diff;
    }

    private Path obtenerUltimoArchivo(String extension) {
        Path dir = MonitorDePID.carpeta.resolve("historia_mods");
        if (!Files.exists(dir)) return null;

        File[] files = dir.toFile().listFiles((d, name) -> 
            name.matches("\\d{6}\\.\\Q" + extension + "\\E"));

        if (files == null || files.length == 0) return null;

        List<File> sortedFiles = Arrays.stream(files)
            .sorted((f1, f2) -> {
                int num1 = obtenerNumeroArchivo(f1.toPath());
                int num2 = obtenerNumeroArchivo(f2.toPath());
                return Integer.compare(num2, num1);
            })
            .collect(Collectors.toList());

        if (sortedFiles.size() > 1) {
            return sortedFiles.get(1).toPath();
        } else if (sortedFiles.size() == 1) {
            return sortedFiles.get(0).toPath();
        }

        return null;
    }

    private int obtenerNumeroArchivo(Path archivo) {
        String nombre = archivo.getFileName().toString();
        return Integer.parseInt(nombre.substring(0, 6));
    }

    private String generarHTML(List<String> diffExito, List<String> diffFalla, 
                              boolean tieneExito, boolean tieneFalla) {
        StringBuilder html = new StringBuilder();
        
        if (tieneExito) {
            html.append("<div style='margin:10px 0;padding:10px;border:1px solid #ccc'>")
                .append("<h3>")
                .append(MonitorDePID.idioma.desdeUltimoExito())
                .append(" (")
                .append(extensionToNombre("exito"))
                .append("):</h3>");
                
            if (diffExito.isEmpty()) {
                html.append("<p style='color:green'>")
                    .append(MonitorDePID.idioma.noHayCambios())
                    .append("</p>");
            } else {
                html.append("<ul>");
                for (String linea : diffExito) {
                    String color = linea.startsWith("+") ? "green" : "red";
                    html.append("<li style='color:" + color + "'>").append(linea).append("</li>");
                }
                html.append("</ul>");
            }
            html.append("</div>");
        }

        if (tieneFalla) {
            html.append("<div style='margin:10px 0;padding:10px;border:1px solid #ccc'>")
                .append("<h3>")
                .append(MonitorDePID.idioma.desdeUltimoIntento())
                .append(" (")
                .append(extensionToNombre("falla"))
                .append("):</h3>");
                
            if (diffFalla.isEmpty()) {
                html.append("<p style='color:green'>")
                    .append(MonitorDePID.idioma.noHayCambios())
                    .append("</p>");
            } else {
                html.append("<ul>");
                for (String linea : diffFalla) {
                    String color = linea.startsWith("+") ? "green" : "red";
                    html.append("<li style='color:" + color + "'>").append(linea).append("</li>");
                }
                html.append("</ul>");
            }
            html.append("</div>");
        }

        return html.toString();
    }

    private String extensionToNombre(String extension) {
        return extension.equals("exito") ? 
            MonitorDePID.idioma.exito() : 
            MonitorDePID.idioma.fallo();
    }

    @Override
    public String mensaje() {
        return mensajeHTML;
    }

    @Override
    public Verificaciones nueva() {
        return new DifDeMods();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return -1000;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.diferentesDeLasMods();
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
            .construir();
    }
}