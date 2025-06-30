package com.asbestosstar.crashdetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DescargarRegistors {
    // Patrones para detectar servicios
    private static final Pattern PATRON_STIKKED = Pattern.compile("^(https?://[^/]+)/(view|raw)/([a-f0-9]+)$");
    private static final Pattern PATRON_MCLO_GS = Pattern.compile("^(?:https?://)?(?:mclo\\.gs|gnomebot\\.dev/paste/mclogs)/([a-zA-Z0-9]+)$");
    private static final Pattern PATRON_GIST = Pattern.compile("^(?:https?://)?gist\\.githubusercontent.com/.*$");
    private static final Pattern PATRON_MCLO_GS_API = Pattern.compile("^(?:https?://)?api\\.mclo\\.gs/1/raw/([a-zA-Z0-9]+)$");

    /**
     * Descarga y devuelve el contenido de múltiples registros
     * @param urls Lista de URLs a descargar
     * @param carpetaDestino Carpeta donde se guardarán los registros descargados
     * @return Mapa con los nombres de archivo y su contenido
     * @throws IOException Si hay un error en la descarga
     */
    public static Map<String, String> descargarRegistros(List<String> urls, File carpetaDestino) throws IOException {
        Map<String, String> resultados = new HashMap<>();
        int contadorGenerico = 1;
        
        // Crear carpeta de destino si no existe
        if (!carpetaDestino.exists()) {
            carpetaDestino.mkdirs();
        }

        for (String url : urls) {
            try {
                String urlProcesada = procesarURL(url);
                CrashDetectorLogger.log(urlProcesada);
                String contenido = descargarContenido(urlProcesada);
                
                // Determinar nombre de archivo
                String nombreArchivo = obtenerNombreDeURL(urlProcesada);
                if (nombreArchivo == null || nombreArchivo.isEmpty()) {
                    nombreArchivo = "registro_" + (contadorGenerico++) + ".log";
                }
                
                // Guardar en carpeta temporal
                File archivoDestino = new File(carpetaDestino, nombreArchivo);
                guardarContenido(contenido, archivoDestino);
                
                resultados.put(nombreArchivo, contenido);
                
            } catch (IOException e) {
                System.err.println("Error descargando " + url + ": " + e.getMessage());
            }
        }
        
        return resultados;
    }

    /**
     * Procesa la URL para convertirla en una URL de descarga directa
     * @param url URL original
     * @return URL procesada para descarga directa
     */
    private static String procesarURL(String url) {
        // Verificar y convertir URL de mclo.gs
        Matcher matcherMcloGs = PATRON_MCLO_GS.matcher(url);
        if (matcherMcloGs.matches()) {
            return "https://api.mclo.gs/1/raw/" + matcherMcloGs.group(1);
        }
        
        // Verificar URL de Stikked
        Matcher matcherStikked = PATRON_STIKKED.matcher(url);
        if (matcherStikked.matches()) {
            return matcherStikked.group(1) + "/raw/" + matcherStikked.group(3);
        }
        
        // Gist ya tiene el formato correcto
        Matcher matcherGist = PATRON_GIST.matcher(url);
        if (matcherGist.matches()) {
            return url;
        }
        
        // Verificar si es un URL de mclo.gs con API
        Matcher matcherMcloGsAPI = PATRON_MCLO_GS_API.matcher(url);
        if (matcherMcloGsAPI.matches()) {
            return url; // Ya es una URL válida para mclo.gs
        }
        
        // Para otros casos, asumimos que es un URL raw válido
        return url;
    }

    /**
     * Descarga el contenido desde una URL
     * @param url URL a descargar
     * @return Contenido descargado
     * @throws IOException Si hay un error en la descarga
     */
    private static String descargarContenido(String url) throws IOException {
        HttpURLConnection conexion = null;
        StringBuilder contenido = new StringBuilder();
        
        try {
            conexion = (HttpURLConnection) new URL(url).openConnection();
            conexion.setRequestMethod("GET");
            conexion.setConnectTimeout(5000);
            conexion.setReadTimeout(5000);
            
            if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException("Error HTTP: " + conexion.getResponseCode());
            }
            
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conexion.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
            }
        } finally {
            if (conexion != null) {
                conexion.disconnect();
            }
        }
        
        return contenido.toString();
    }

    /**
     * Extrae el nombre del archivo desde la URL
     * @param url URL de origen
     * @return Nombre de archivo sugerido o null si no se puede extraer
     */
    private static String obtenerNombreDeURL(String url) {
        // Para URLs de Stikked
        Matcher matcherStikked = Pattern.compile("/([a-f0-9]{12})$").matcher(url);
        if (matcherStikked.find()) {
            return "registro_stikked_" + matcherStikked.group(1) + ".log";
        }
        
        // Para URLs de mclo.gs
        Matcher matcherMcloGs = Pattern.compile("([a-zA-Z0-9]{6,})$").matcher(url);
        if (matcherMcloGs.find()) {
            return "registro_mclo_gs_" + matcherMcloGs.group(1) + ".log";
        }
        
        // Para URLs de Gist
        Matcher matcherGist = Pattern.compile("/([^/]+)$").matcher(url);
        if (matcherGist.find()) {
            return "registro_gist_" + matcherGist.group(1) + ".log";
        }
        
        // Para URLs genéricas
        return null;
    }

    /**
     * Guarda el contenido en un archivo
     * @param contenido Contenido a guardar
     * @param archivo Archivo de destino
     * @throws IOException Si hay un error al guardar
     */
    private static void guardarContenido(String contenido, File archivo) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(contenido.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * Verifica si la URL pertenece a un servicio conocido
     * @param url URL a verificar
     * @return true si el URL pertenece a un servicio conocido
     */
    public static boolean esURLSoportada(String url) {
        return PATRON_STIKKED.matcher(url).matches() || 
               PATRON_MCLO_GS.matcher(url).matches() ||
               PATRON_GIST.matcher(url).matches() ||
               PATRON_MCLO_GS_API.matcher(url).matches();
    }

    /**
     * Descarga y guarda múltiples registros
     * @param urls Lista de URLs a descargar
     * @param carpetaDestino Carpeta donde se guardarán los archivos
     * @return Lista de archivos descargados
     * @throws IOException Si hay un error en alguna descarga
     */
    public static List<File> descargarYGuardarRegistros(List<String> urls, File carpetaDestino) throws IOException {
        List<File> archivosDescargados = new ArrayList<>();
        Map<String, String> resultados = descargarRegistros(urls, carpetaDestino);
        
        for (Map.Entry<String, String> entrada : resultados.entrySet()) {
            String nombreArchivo = entrada.getKey();
            String contenido = entrada.getValue();
            
            File archivo = new File(carpetaDestino, nombreArchivo);
            guardarContenido(contenido, archivo);
            archivosDescargados.add(archivo);
        }
        
        return archivosDescargados;
    }

    /**
     * Convierte una URL a su versión raw si es posible
     * @param url URL a convertir
     * @return URL convertida a raw o la original si no necesita conversión
     */
    public static String convertirARaw(String url) {
        // Si ya es una URL de raw de Stikked o mclo.gs, devolver como está
        if (url.contains("/raw/") || url.contains("api.mclo.gs")) {
            return url;
        }
        
        // Convertir URLs normales a raw si es posible
        if (esURLSoportada(url)) {
            return procesarURL(url);
        }
        
        return url; // Devolver URL original si no se puede convertir
    }

    /**
     * Verifica si una URL parece contener un registro
     * @param url URL a verificar
     * @return true si parece ser un registro
     */
    public static boolean pareceRegistro(String url) {
        // Verificar extensión común de registro
        if (url.endsWith(".log") || url.endsWith(".txt") || url.contains("launcher_log")) {
            return true;
        }
        
        // Verificar si es un servicio de registro conocido
        if (esURLSoportada(url)) {
            return true;
        }
        
        return false;
    }

    /**
     * Extrae el ID del registro desde la URL
     * @param url URL a analizar
     * @return ID del registro o null si no se puede extraer
     */
    public static String extraerIDRegistro(String url) {
        // Extraer ID de Stikked
        Matcher matcherStikked = PATRON_STIKKED.matcher(url);
        if (matcherStikked.matches()) {
            return matcherStikked.group(3);
        }
        
        // Extraer ID de mclo.gs
        Matcher matcherMcloGs = PATRON_MCLO_GS.matcher(url);
        if (matcherMcloGs.matches()) {
            return matcherMcloGs.group(1);
        }
        
        // Extraer ID de Gist
        Matcher matcherGist = Pattern.compile("/([^/]+)$").matcher(url);
        if (matcherGist.find()) {
            return "gist_" + matcherGist.group(1);
        }
        
        return null;
    }

    /**
     * Comprueba si el contenido parece ser un registro de Minecraft
     * @param contenido Contenido a verificar
     * @return true si parece ser un registro de Minecraft
     */
    public static boolean esRegistroMinecraft(String contenido) {
        // Verificar patrones comunes en registros de Minecraft
        return contenido.contains("Minecraft") || 
               contenido.contains("ModLauncher") ||
               contenido.contains("Forge") ||
               contenido.contains("Fabric") ||
               contenido.contains("java.lang.Throwable");
    }

    /**
     * Obtiene el tipo de registro según su URL
     * @param url URL del registro
     * @return Tipo de registro o "desconocido" si no se puede determinar
     */
    public static String obtenerTipoRegistro(String url) {
        if (PATRON_STIKKED.matcher(url).matches()) {
            return "stikked";
        }
        
        if (PATRON_MCLO_GS.matcher(url).matches() || PATRON_MCLO_GS_API.matcher(url).matches()) {
            return "mclo.gs";
        }
        
        if (PATRON_GIST.matcher(url).matches()) {
            return "gist.github.com";
        }
        
        return "desconocido";
    }

    /**
     * Descarga y filtra registros por tipo de registro
     * @param urls Lista de URLs a descargar
     * @param carpetaDestino Carpeta donde se guardarán los archivos
     * @param tipoFiltro Filtro para el tipo de registro (stikked, mclo.gs, gist.github.com, desconocido)
     * @return Mapa con los registros filtrados
     * @throws IOException Si hay un error en la descarga
     */
    public static Map<String, String> descargarRegistrosPorTipo(List<String> urls, File carpetaDestino, String tipoFiltro) throws IOException {
        Map<String, String> todosLosRegistros = descargarRegistros(urls, carpetaDestino);
        Map<String, String> registrosFiltrados = new HashMap<>();
        
        for (Map.Entry<String, String> entrada : todosLosRegistros.entrySet()) {
            String url = entrada.getKey(); // En esta implementación, la key es el nombre del archivo
            String contenido = entrada.getValue();
            
            if (tipoFiltro.equals("desconocido") || tipoFiltro.equals(obtenerTipoRegistro(url))) {
                registrosFiltrados.put(url, contenido);
            }
        }
        
        return registrosFiltrados;
    }

    /**
     * Descarga registros y crea objetos Consola para cada uno
     * @param urls Lista de URLs a descargar
     * @param carpetaDestino Carpeta donde se guardarán los archivos
     * @return Lista de objetos Consola creados
     * @throws IOException Si hay un error en la descarga
     */
    public static List<Consola> crearConsolasDesdeRegistros(List<String> urls, File carpetaDestino) throws IOException {
        List<Consola> consolas = new ArrayList<>();
        
        for (File archivo : descargarYGuardarRegistros(urls, carpetaDestino)) {
            try {
                String contenido = MonitorDePID.leer_archivo(archivo.toPath());
                Consola consola = new Consola(archivo.toPath());
                consola.finalizarContentoInyectado(contenido);
                consolas.add(consola);
            } catch (IOException e) {
                System.err.println("Error procesando " + archivo.getName() + ": " + e.getMessage());
            }
        }
        
        return consolas;
    }
    
    /**
     * Descarga y guarda un único archivo
     * @param url - URL del archivo
     * @param destino - Archivo de destino
     * @throws IOException si hay un error
     */
    public static void descargarArchivo(String url, File destino) throws IOException {
        // Usar la implementación original de descarga
        try (InputStream input = new URL(url).openStream()) {
            Files.copy(input, destino.toPath());
        } catch (IOException e) {
            throw new IOException("Error al descargar archivo desde " + url, e);
        }
    }
    
    
}