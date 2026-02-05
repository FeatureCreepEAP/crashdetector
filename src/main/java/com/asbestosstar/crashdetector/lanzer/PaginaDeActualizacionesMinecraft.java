package com.asbestosstar.crashdetector.lanzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;

public class PaginaDeActualizacionesMinecraft {

    // Archivo de caché local donde se guarda la página ya procesada
    public static File archivo = Statics.carpeta.resolve("versiones_minecraft_cdlauncher.htm").toFile(); 

    /**
     * Obtiene el HTML final.
     * Si existe caché, se usa.
     * Si no, se descarga, se limpia y se guarda.
     */
    public static String obtenerHTML() {
        try {
            if (archivo.exists()) {
                return leerArchivo(archivo);
            }

            cache();
            return archivo.exists() ? leerArchivo(archivo) : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Descarga el contenido desde la URL, elimina bloques innecesarios
     * (publicidad, menús y enlaces) y guarda el resultado en caché.
     */
    public static void cache() {
        try {
            String html = descargar(obtenerURL());

            // Limpiar anuncios, menús y enlaces
            html = eliminarEnlacesYContenido(html);

            // Guardar en archivo local
            try (FileOutputStream fos = new FileOutputStream(archivo)) {
                fos.write(html.getBytes(StandardCharsets.UTF_8));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina:
     * - div class="row"
     * - div class="glavnoe_menu"
     * - todos los enlaces <a>
     */
    public static String eliminarEnlacesYContenido(String html) {

        // Eliminar div.row completos
        html = html.replaceAll("(?is)<div[^>]*class\\s*=\\s*\"row\"[^>]*>.*?</div>", "");

        // Eliminar div.glavnoe_menu completos
        html = html.replaceAll("(?is)<div[^>]*class\\s*=\\s*\"glavnoe_menu\"[^>]*>.*?</div>", "");

        // Eliminar todos los enlaces <a> pero conservar su contenido interno
        html = html.replaceAll("(?is)<a[^>]*>", "");
        html = html.replaceAll("(?is)</a>", "");

        return html;
    }

    /**
     * Devuelve la URL según el idioma actual del launcher.
     */
    public static String obtenerURL() {
        return "https://repo.tlauncher.org/update/downloads/configs_v1/pages/index_"
                + MonitorDePID.idioma.codigo() + ".html";
    }

    // ================= Utilidades internas =================

    /**
     * Descarga una URL y devuelve su contenido como String UTF-8.
     */
    private static String descargar(String urlStr) throws Exception {
        URL url = new URL(urlStr);

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Lee un archivo completo como UTF-8.
     */
    private static String leerArchivo(File f) throws Exception {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }
        }

        return sb.toString();
    }
}
