package com.asbestosstar.crashdetector.buscar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Clase que procesa carpetas para buscar mods y clases dentro de ellas.
 * Soporta tanto mods de HOI4 como módulos de JBoss en estructuras de carpetas.
 * Maneja archivos JAR/ZIP anidados dentro de estructuras de carpetas (especialmente para JBoss).
 * 
 * NOTA: Las subcarpetas normalmente NO son mods independientes, sino parte del mismo mod.
 */
public class ModCarpeta implements ArchivoDeMod {

    public ArchivoDeMod desde;
    public String ubicacion;
    public List<ArchivoDeMod> mods_en_mod = new ArrayList<>();
    public List<String> nombres = new ArrayList<>();
    public List<String> clases = new ArrayList<>();
    public List<String> archivos = new ArrayList<>();
    private final Map<String, byte[]> mapaBytesClase = new HashMap<>();
    private final Path rutaRaiz;

    /**
     * Constructor principal que procesa una carpeta de mods.
     * Detecta automáticamente si es un mod de HOI4 o un módulo de JBoss.
     */
    public ModCarpeta(String ubicacion, ArchivoDeMod desde, Path rutaRaiz) {
        this.ubicacion = ubicacion;
        this.desde = desde;
        this.rutaRaiz = rutaRaiz;

        try {
            procesarCarpeta(rutaRaiz, true);
        } catch (Exception e) {
            CrashDetectorLogger.logException(e);
        }
    }

    /**
     * Procesa recursivamente una carpeta para identificar mods y clases.
     * @param ruta Ruta a procesar
     * @param esRaiz Indica si es la carpeta raíz del mod actual
     */
    private boolean procesarCarpeta(Path ruta, boolean esRaiz) {
        boolean contieneDefinicionMod = false;
        
        try (DirectoryStream<Path> flujoDirectorio = Files.newDirectoryStream(ruta)) {
            for (Path entrada : flujoDirectorio) {
                String nombre = entrada.getFileName().toString();
                archivos.add(nombre);

                if (Files.isDirectory(entrada)) {
                    // Procesar subcarpeta
                    boolean subContieneDefinicionMod = procesarCarpeta(entrada, false);
                    
                    // Si la subcarpeta contiene un mod definido y estamos en un nivel raíz,
                    // entonces es un mod independiente
                    if (subContieneDefinicionMod && esRaiz) {
                        String nuevaUbicacion = ubicacion + "/" + nombre;
                        mods_en_mod.add(new ModCarpeta(nuevaUbicacion, this, entrada));
                    }
                } else {
                    // Procesar archivo
                    if (nombre.endsWith("modules.xml") || nombre.endsWith(".mod")) {
                        contieneDefinicionMod = true;
                        
                        if (nombre.endsWith("modules.xml")) {
                            nombres.add(parsearNombreModuloJBoss(Files.readAllBytes(entrada)));
                        } else if (nombre.endsWith(".mod")) {
                            nombres.add(parsearNombreModHOI4(Files.readAllBytes(entrada)));
                        }
                    } else if (nombre.endsWith(".class")) {
                        procesarClase(entrada);
                    } else if (esArchivoAnidado(nombre)) {
                        procesarArchivoAnidado(entrada, nombre);
                    }
                }
            }
        } catch (IOException e) {
            CrashDetectorLogger.logException(e);
        }
        
        return contieneDefinicionMod;
    }

    /**
     * Procesa un archivo de clase y lo añade al mapa de bytes de clase.
     */
    private void procesarClase(Path entrada) {
        String nombreClase = entrada.toString()
                .replace(rutaRaiz.toString(), "")
                .replace(File.separator, ".")
                .replace(".class", "");
        if (nombreClase.startsWith(".")) {
            nombreClase = nombreClase.substring(1);
        }
        clases.add(nombreClase);
        
        // Guardar bytes de clase para análisis posterior con ASM
        String nombreInterno = entrada.toString()
                .replace(rutaRaiz.toString(), "")
                .substring(1)
                .replace(File.separator, "/")
                .replace(".class", "");
        try {
            mapaBytesClase.put(nombreInterno, Files.readAllBytes(entrada));
        } catch (IOException e) {
            CrashDetectorLogger.logException(e);
        }
    }

    /**
     * Procesa archivos anidados (JAR, ZIP, etc.) creando instancias de ModPKZip.
     */
    private void procesarArchivoAnidado(Path entrada, String nombre) {
        try (InputStream inputStream = new FileInputStream(entrada.toFile())) {
            String nuevaUbicacion = ubicacion + "/" + nombre;
            mods_en_mod.add(new ModPKZip(nuevaUbicacion, this, inputStream));
        } catch (IOException e) {
            CrashDetectorLogger.logException(e);
        }
    }

    /**
     * Devuelve true si el archivo es un contenedor anidado (como .jar, .zip, etc.)
     */
    private boolean esArchivoAnidado(String nombreArchivo) {
        return nombreArchivo.endsWith(".jar") ||
                nombreArchivo.endsWith(".zip") ||
                nombreArchivo.endsWith(".fpm") ||
                nombreArchivo.endsWith(".litemod") ||
                nombreArchivo.endsWith(".war") ||
                nombreArchivo.endsWith(".ear") ||
                nombreArchivo.endsWith(".rar");
    }

    /**
     * Extrae el nombre del módulo de un archivo modules.xml (JBoss).
     */
    private String parsearNombreModuloJBoss(byte[] contenido) throws IOException {
        String xml = new String(contenido);
        int inicio = xml.indexOf("name=\"") + "name=\"".length();
        int fin = xml.indexOf("\"", inicio);
        return (inicio != -1 && fin != -1) ? xml.substring(inicio, fin) : "Módulo JBoss desconocido";
    }

    /**
     * Extrae el nombre del mod de un archivo HOI4 (.mod).
     */
    private String parsearNombreModHOI4(byte[] contenido) throws IOException {
        String texto = new String(contenido);
        int inicio = texto.indexOf("name=\"") + "name=\"".length();
        int fin = texto.indexOf("\"", inicio);
        return (inicio != -1 && fin != -1) ? texto.substring(inicio, fin) : "Mod HOI4 desconocido";
    }

    // Métodos de búsqueda recursiva

    @Override
    public ArchivoDeMod obtenerDesde() {
        return desde;
    }

    @Override
    public List<ArchivoDeMod> mods_en_mods() {
        return mods_en_mod;
    }

    @Override
    public List<String> nombre() {
        return nombres;
    }

    @Override
    public String ubicacion() {
        return ubicacion;
    }

    @Override
    public List<String> clases() {
        return clases;
    }

    @Override
    public boolean tieneNombreRecursivo(String nombre) {
        if (this.nombres.contains(nombre)) return true;
        for (ArchivoDeMod mod : mods_en_mods()) {
            if (mod.tieneNombreRecursivo(nombre)) return true;
        }
        return false;
    }

    @Override
    public String obtenerNombreRecursivo(String nombre) {
        if (tieneNombreRecursivo(nombre)) {
            if (this.nombres.contains(nombre)) return this.ubicacion();
            for (ArchivoDeMod mod : mods_en_mods()) {
                String resultado = mod.obtenerNombreRecursivo(nombre);
                if (resultado != null) return resultado;
            }
        }
        return null;
    }

    @Override
    public boolean tieneArchivoRecursivo(String archivo) {
        if (this.archivos().contains(archivo)) return true;
        for (ArchivoDeMod mod : mods_en_mods()) {
            if (mod.tieneArchivoRecursivo(archivo)) return true;
        }
        return false;
    }

    @Override
    public String obtenerArchivoRecursivo(String archivo) {
        if (tieneArchivoRecursivo(archivo)) {
            if (this.archivos().contains(archivo)) return this.ubicacion() + "/" + archivo;
            for (ArchivoDeMod mod : mods_en_mods()) {
                String resultado = mod.obtenerArchivoRecursivo(archivo);
                if (resultado != null) return resultado;
            }
        }
        return null;
    }

    @Override
    public List<String> archivos() {
        return archivos;
    }

    @Override
    public List<ArchivoDeMod> buscarModsCon(String termino) {
        List<ArchivoDeMod> resultados = new ArrayList<>();

        boolean tieneArchivo = archivos.contains(termino);
        boolean tieneClase = clases.contains(termino);
        String rutaPaquete = termino.replace('.', '/');
        boolean tienePaquete = clases.stream().anyMatch(clase -> clase.startsWith(rutaPaquete));

        if (tieneArchivo || tieneClase || tienePaquete) {
            resultados.add(this);
        }

        for (ArchivoDeMod mod : mods_en_mod) {
            resultados.addAll(mod.buscarModsCon(termino));
        }

        return resultados;
    }
    
    @Override
    public boolean existeClase(String nombreClase) {
        // Convertir nombre de clase de formato interno (ej: "java/lang/Object") a formato Java (ej: "java.lang.Object")
        String formatoJava = nombreClase.replace('/', '.');
        return clases.contains(formatoJava);
    }
    
    @Override
    public byte[] obtenerBytesClase(String nombreClase) {
        return mapaBytesClase.get(nombreClase);
    }
    
    @Override
    public List<String> obtenerTodosLosNombresDeClases() {
        return new ArrayList<>(mapaBytesClase.keySet());
    }
}