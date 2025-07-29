package com.asbestosstar.crashdetector.api_sito_registro;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;

public class MCLogsAPI implements APIdeSitioDeRegistro {

    private static final int ESPACIO_MAXIMO_BYTES = 10 * 1024 * 1024; // 10MB
    private static final int LINEAS_MAXIMAS = 25000;

    @Override
    public String nombre() {
        return "mclogs";
    }

    @Override
    public String publicarRegistro(Consola registro) throws DemasiadoGrande, ErrorConPublicar {
        String contenido = registro.obtainerContenidoParaPublicar().trim();
        
        // Verificar contenido vacío
        if (contenido.isEmpty()) {
            throw new ErrorConPublicar("El contenido del registro está vacío");
        }
        
        // Verificar límite de tamaño
        byte[] bytesContenido = contenido.getBytes(StandardCharsets.UTF_8);
        if (bytesContenido.length > ESPACIO_MAXIMO_BYTES) {
            throw new DemasiadoGrande();
        }
        
        // Verificar límite de líneas
        int cantidadLineas = contenido.split("\n").length;
        if (cantidadLineas > LINEAS_MAXIMAS) {
            throw new DemasiadoGrande();
        }
        
        // Preparar datos de solicitud
        String datosPost;
        try {
            datosPost = "content=" + URLEncoder.encode(contenido, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            throw new ErrorConPublicar("Error al codificar los datos: " + e.getMessage());
        }
        
        // Enviar solicitud HTTP
        try {
            URL url = new URL(APIdeSitioDeRegistro.sitioDeConfig());
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conexion.setRequestProperty("Content-Length", String.valueOf(datosPost.length()));
            conexion.setDoOutput(true);
            
            try (DataOutputStream salida = new DataOutputStream(conexion.getOutputStream())) {
                salida.writeBytes(datosPost);
                salida.flush();
            }
            
            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta != 200) {
                throw new ErrorConPublicar("Código de error HTTP: " + codigoRespuesta);
            }
            
            // Procesar respuesta
            StringBuilder respuesta = new StringBuilder();
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    respuesta.append(linea);
                }
            }
            
            // Analizar respuesta JSON
            // Analizar respuesta manualmente
            String respuestaStr = respuesta.toString();
            if (respuestaStr.contains("\"success\":true")) {
                return extraerUrlDeRespuesta(respuestaStr);
            } else {
                String error = extraerErrorDeRespuesta(respuestaStr);
                throw new ErrorConPublicar("Error del servidor: " + error);
            }
        } catch (IOException e) {
            throw new ErrorConPublicar("Error de red: " + e.getMessage());
        }
    }

    @Override
    public List<String> sitiosPorDefecto() {
        List<String> sitios = new ArrayList<>();
        sitios.add("https://api.mclo.gs/1/log");
        return sitios;
    }


    private String extraerUrlDeRespuesta(String respuestaJson) {
        int inicio = respuestaJson.indexOf("\"url\":\"") + 7;
        int fin = respuestaJson.indexOf("\"", inicio);
        String url = respuestaJson.substring(inicio, fin);
        return url.replace("\\", ""); // Eliminar todas las barras invertidas
    }

    private String extraerErrorDeRespuesta(String respuestaJson) {
        int inicio = respuestaJson.indexOf("\"error\":\"") + 9;
        int fin = respuestaJson.indexOf("\"", inicio);
        return respuestaJson.substring(inicio, fin).replace("\\\"", "\""); // Corregir comillas escapadas
    }
}