package com.asbestosstar.crashdetector.buscar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Clase que procesa archivos ZIP/JAR para buscar mods y clases dentro de ellos.
 * Evita errores de cierre de flujo leyendo todas las entradas al inicio.
 */
public class ModPKZip implements ArchivoDeMod {

    public ArchivoDeMod desde;
    public String ubicacion;
    public List<ArchivoDeMod> mods_en_mod = new ArrayList<>();
    public List<String> nombres = new ArrayList<>();
    public List<String> clases = new ArrayList<>();
    public List<String> archivos = new ArrayList<>();
    private final Map<String, byte[]> bytesClaseMap = new HashMap<>();

    /**
     * Constructor principal que procesa un archivo ZIP/JAR.
     * Lee todas las entradas del flujo de entrada al inicio para evitar cierre prematuro.
     */
    public ModPKZip(String ubicacion, ArchivoDeMod desde, InputStream inputStream) {
        CrashDetectorLogger.log("en mod pkzip " + ubicacion);
    	this.ubicacion = ubicacion;
        this.desde = desde;
        CrashDetectorLogger.log("Buscando en ubicacion");
        try {
            // Leer todas las entradas al inicio
            Map<String, byte[]> todasLasEntradas = leerTodasLasEntradas(inputStream);

            // Procesar cada entrada de forma independiente
            for (Map.Entry<String, byte[]> entrada : todasLasEntradas.entrySet()) {
                String nombreArchivo = entrada.getKey();
                byte[] contenido = entrada.getValue();

                // Añadir nombre del archivo a lista interna
                this.archivos.add(nombreArchivo);

                // Manejar archivos anidados
                if (esArchivoAnidado(nombreArchivo)) {
                    String nuevaUbicacion = ubicacion + "!/" + nombreArchivo;
                    InputStream entryStream = new ByteArrayInputStream(contenido);
                    mods_en_mod.add(new ModPKZip(nuevaUbicacion, this, entryStream));
                } else {
                    // Procesar metadatos
                    if (nombreArchivo.endsWith("modules.xml")) {
                        nombres.add(parsearNombreModuloJBoss(contenido));
                    } else if (nombreArchivo.endsWith(".mod")) {
                        nombres.add(parsearNombreModHOI4(contenido));
                    } else if (nombreArchivo.equals("fabric.mod.json")) {
                        nombres.add(parsearIdModFabric(contenido));
                    } else if (nombreArchivo.endsWith("mods.toml")) {
                        nombres.add(parsearIdModMCForge(contenido));
                    } else if (nombreArchivo.endsWith(".class")) {
                        String nombreClase = nombreArchivo.replace("/", ".").replace(".class", "");
                        clases.add(nombreClase);
                        // Guardar bytes de clase para análisis posterior con ASM
                        String nombreInterno = nombreArchivo.substring(0, nombreArchivo.length() - 6);
                        bytesClaseMap.put(nombreInterno, contenido);
                    }
                }
            }

        } catch (IOException e) {
            CrashDetectorLogger.logException(e);
        }
    }

    /**
     * Lee todas las entradas del ZIP en memoria como pares (nombre, contenido).
     */
    private Map<String, byte[]> leerTodasLasEntradas(InputStream inputStream) throws IOException {
        Map<String, byte[]> entradas = new HashMap<>();

        try (ZipInputStream zip = new ZipInputStream(inputStream)) {
            ZipEntry entrada;
            while ((entrada = zip.getNextEntry()) != null) {
                String nombre = entrada.getName();
                entradas.put(nombre, leer(zip));
                zip.closeEntry();
            }
        }

        return entradas;
    }

    /**
     * Lee el contenido de un flujo de entrada en un arreglo de bytes.
     */
    private byte[] leer(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int bytesRead;

        while ((bytesRead = input.read(data)) != -1) {
            buffer.write(data, 0, bytesRead);
        }

        return buffer.toByteArray();
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
        String txt = new String(contenido);
        int inicio = txt.indexOf("name=\"") + "name=\"".length();
        int fin = txt.indexOf("\"", inicio);
        return (inicio != -1 && fin != -1) ? txt.substring(inicio, fin) : "Mod HOI4 desconocido";
    }

    /**
     * Extrae el id del mod de un archivo fabric.mod.json.
     */
    private String parsearIdModFabric(byte[] contenido) throws IOException {
        String json = new String(contenido);
        int inicio = json.indexOf("\"id\": \"") + "\"id\": \"".length();
        int fin = json.indexOf("\"", inicio);
        return (inicio != -1 && fin != -1) ? json.substring(inicio, fin) : "Mod Fabric desconocido";
    }

    /**
     * Extrae el modId de un archivo mods.toml (Forge).
     */
    private String parsearIdModMCForge(byte[] contenido) throws IOException {
        String toml = new String(contenido, StandardCharsets.UTF_8);
        
        // Limpiar la lista de nombres previos
        this.nombres.clear();

        // Patrón para encontrar el modId principal en el bloque [[mods]]
        Pattern modPattern = Pattern.compile("\\[\\[mods\\]\\][^\\[]*modId\\s*=\\s*\"([^\"]+)\"", Pattern.DOTALL);
        Matcher modMatcher = modPattern.matcher(toml);

        // Valor por defecto si no se encuentra el modId
        String modIdPrincipal = "Mod Forge desconocido";

        if (modMatcher.find()) {
            modIdPrincipal = modMatcher.group(1);
        }

        // Añadir el modId principal a la lista de nombres
        this.nombres.add(modIdPrincipal);

        // Patrón para encontrar nombres en bloques de dependencias: [[dependencies.nombre]] por que es la causa de una problema grande
        Pattern depPattern = Pattern.compile("\\[\\[dependencies\\.([a-zA-Z0-9_]+)\\]\\]");
        Matcher depMatcher = depPattern.matcher(toml);

        // Añadir cada nombre encontrado en dependencies a la lista de nombres
        while (depMatcher.find()) {
            String nombreDependencia = depMatcher.group(1);
            // Evitar duplicados
            if (!this.nombres.contains(nombreDependencia)) {
                this.nombres.add(nombreDependencia);
            }
        }

        return modIdPrincipal;
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
            if (this.archivos().contains(archivo)) return this.ubicacion() + "!/" + archivo;
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
        return bytesClaseMap.get(nombreClase);
    }
    
    @Override
    public List<String> obtenerTodosLosNombresDeClases() {
        return new ArrayList<>(bytesClaseMap.keySet());
    }
}