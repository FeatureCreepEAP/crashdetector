package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnlanceMD {
    public static File carpeta = new File("crash_detector/.tmp/tmp_registros/");
    
    // Contador estático para registros genéricos
    private static int contadorGenerico = 1;

    /**
     * Elimina el contenido de la carpeta y procesa el texto Markdown 
     * para extraer y guardar registros desde enlaces de log
     * @param texto_de_mensaje - Texto en formato Markdown con enlaces a registros
     */
    public static void guardar(String texto_de_mensaje) {
    	CrashDetectorLogger.log("limpiar");
        limpiarCarpetaTemporal();
        contadorGenerico = 1; // Reiniciar contador
    	CrashDetectorLogger.log("compile regex");

  

    	Pattern pattern = Pattern.compile(
    		    Pattern.quote("[") 
    		    + "([^\\]]+)"              // Grupo 1: texto visible del enlace
    		    + Pattern.quote("](") 
    		    + "<?([^)>]+)>?"           // Grupo 2: URL (sin '>' o ')')
    		    + Pattern.quote(")")
    		);

    	
    	
        Matcher matcher = pattern.matcher(texto_de_mensaje);
        
        List<Consola> nuevosRegistros = new ArrayList<>();
    	CrashDetectorLogger.log("matcher buscar");

        while (matcher.find()) {
        	
            String textoLink = matcher.group(1);  // Texto visible del enlace
            String url = matcher.group(2);        // URL real
        	CrashDetectorLogger.log("url " + url);

            // Usar la URL si contiene el nombre del archivo, sino usar el texto del enlace
            String nombreArchivo = contieneNombreLog(url) ? url : textoLink;
            
            if (contieneNombreLog(nombreArchivo)) {
                try {
                    String nombreFinal = extraerNombreLog(nombreArchivo);
                    File archivoDestino = new File(carpeta, obtenerNombreUnico(nombreFinal));
                    
                    // Descargar y guardar el archivo
                    descargarArchivo(url, archivoDestino);
                    
                    // Crear objeto Consola y añadir a la lista
                    Consola consola = new Consola(archivoDestino.toPath());
                    consola.finalizarContentoInyectado(MonitorDePID.leer_archivo(archivoDestino.toPath()));
                    nuevosRegistros.add(consola);
                    
                } catch (Exception e) {
                    CrashDetectorLogger.log("Error procesando enlace de log: " + url + " - " + e.getMessage());
                }
            }
        }
        
        // Actualizar las consolas en MonitorDePID
        if (!nuevosRegistros.isEmpty()) {
            MonitorDePID.consolas.addAll(nuevosRegistros);
            MonitorDePID.consola_de_launcher_inyectado = true;
        }
    }

    /**
     * Verifica si el texto contiene un nombre de archivo de registro conocido
     * @param texto - Texto a verificar
     * @return true si contiene un nombre de log reconocido
     */
    private static boolean contieneNombreLog(String texto) {
        // Patrones reconocidos incluyendo informes de crash
        String[] patrones = {
            "latest\\.log",
            "launcher_log\\.txt",
            "debug\\.log",
            "tlauncher.*\\.log",
            "sklauncher_logs\\.txt",
            "hs_err_pid.*\\.log",
            "crash-.*\\.(?:log|txt)"
        };
        
        // Verificar si el texto contiene alguno de los patrones
        for (String patron : patrones) {
            if (texto.matches(".*" + patron + ".*")) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Extrae el nombre del archivo de registro del texto
     * @param texto - Texto que contiene el nombre del archivo
     * @return nombre del archivo de registro limpio
     */
    private static String extraerNombreLog(String texto) {
        String[] patrones = {
            "(latest\\.log)",
            "(launcher_log\\.txt)",
            "(debug\\.log)",
            "(tlauncher.*\\.log)",
            "(sklauncher_logs\\.txt)",
            "(hs_err_pid.*\\.log)",
            "(crash-.*\\.(?:log|txt))"
        };
        
        for (String patron : patrones) {
            Pattern pattern = Pattern.compile(patron);
            Matcher matcher = pattern.matcher(texto);
            if (matcher.find()) {
                String nombre = matcher.group(1);
                
                // No eliminar sufijos numéricos de ciertos tipos de logs
                if (nombre.startsWith("crash-")) {
                    return nombre;
                }
                
                // Eliminar sufijos numéricos de otros registros
                if (nombre.matches(".*\\.log(?:_\\d+)?")) {
                    nombre = nombre.replaceAll("(_\\d+)?\\.log$", ".log");
                } else if (nombre.matches(".*\\.txt(?:_\\d+)?")) {
                    nombre = nombre.replaceAll("(_\\d+)?\\.txt$", ".txt");
                }
                
                return nombre;
            }
        }
        
        // Si no se encontró un patrón exacto, extraer el último segmento de la URL
        int slashIndex = texto.lastIndexOf('/');
        if (slashIndex != -1) {
            String nombre = texto.substring(slashIndex + 1);
            
            // Verificar si el nombre contiene un patrón conocido
            if (nombre.matches("(latest|launcher_log|debug|tlauncher.*|sklauncher_logs|hs_err_pid).*\\.(?:log|txt)(_\\d+)?")) {
                nombre = nombre.replaceAll("(_\\d+)?\\.(?:log|txt)$", ".$1");
                return nombre;
            }
            
            return "registro_adicional_" + (contadorGenerico++) + ".log"; // Nombre genérico único
        }
        
        return "registro_generico_" + (contadorGenerico++) + ".log"; // Nombre genérico único por defecto
    }

    /**
     * Genera un nombre de archivo único sin modificar el nombre base
     * @param nombreOriginal - Nombre base del archivo
     * @return nombre de archivo único
     */
    private static String obtenerNombreUnico(String nombreOriginal) {
        // Mantener el nombre original sin sufijo numérico para ciertos tipos de logs
        if (nombreOriginal.startsWith("crash-")) {
            // Para informes de crash, mantener el nombre original
            return nombreOriginal;
        }
        
        // Para otros registros, aplicar lógica normal
        String nombreBase = nombreOriginal;
        if (!nombreOriginal.matches("crash-.*")) {
            // Eliminar sufijo numérico para registros que no son crash reports
            nombreBase = nombreOriginal.replaceFirst("(_\\d+)?\\.log$", ".log")
                                      .replaceFirst("(_\\d+)?\\.txt$", ".txt");
        }
        
        // Verificar si ya existe un archivo con este nombre
        File archivo = new File(carpeta, nombreBase);
        int contador = 1;
        
        while (archivo.exists()) {
            archivo = new File(carpeta, nombreBase.replaceFirst("\\.(?:log|txt)$", "_" + (++contador) + ".$1"));
        }
        
        return archivo.getName();
    }

    /**
     * Limpia la carpeta temporal eliminando todos los archivos existentes
     */
    private static void limpiarCarpetaTemporal() {
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        } else {
            File[] archivos = carpeta.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isFile()) {
                        archivo.delete();
                    }
                }
            }
        }
    }

    /**
     * Descarga un archivo desde una URL y lo guarda localmente
     * @param url - URL del archivo a descargar
     * @param destino - Archivo local donde se guardará el contenido
     * @throws IOException si hay un error en la descarga
     */
    private static void descargarArchivo(String url, File destino) throws IOException {
        // Verificar si el URL es soportado por DescargarRegistors
        if (DescargarRegistors.esURLSoportada(url)) {
            // Usar DescargarRegistors para procesar el URL
            List<String> urls = new ArrayList<>();
            urls.add(url);
            
            // Descargar y guardar registros usando DescargarRegistors
            List<File> archivosDescargados = DescargarRegistors.descargarYGuardarRegistros(urls, destino.getParentFile());
            
            // Mover el archivo al destino específico si es necesario
            if (!archivosDescargados.isEmpty()) {
                File archivoDescargado = archivosDescargados.get(0);
                if (!archivoDescargado.renameTo(destino)) {
                    // Si no se puede renombrar, copiar y eliminar el original
                    try {
                        Files.copy(archivoDescargado.toPath(), destino.toPath());
                        archivoDescargado.delete();
                    } catch (IOException e) {
                        throw new IOException("No se pudo mover el archivo descargado a la ubicación especificada", e);
                    }
                }
            } else {
                throw new IOException("No se pudo descargar el archivo desde: " + url);
            }
        } else {
            // Para URLs no soportados, usar descarga directa
            DescargarRegistors.descargarArchivo(url, destino);
        }
    }
}