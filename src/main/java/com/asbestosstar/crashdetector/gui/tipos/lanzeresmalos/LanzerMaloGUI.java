package com.asbestosstar.crashdetector.gui.tipos.lanzeresmalos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.general.LanzerDesAnimado;
import com.asbestosstar.crashdetector.detectorlanzer.DetectorLanzer;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

public abstract class LanzerMaloGUI extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {
    public static Map<String, Supplier<LanzerMaloGUI>> GUIS = new HashMap<String, Supplier<LanzerMaloGUI>>();
    
    protected List<DetectorLanzer> lanzadores;
    protected List<String> recomendados;
    protected Map<String, Map<String, String>> noRecomendados;
    
    protected Path archivoRecomendados = LanzerDesAnimado.ARCHIVO_ANIMADOS;
    protected Path archivoNoRecomendados = LanzerDesAnimado.ARCHIVO_DESANIMADOS;
    
    /**
     * NOSOTROS dice es malo, pero la corporacion es diferente
     * @return true si CrashDetector desaconseja este lanzador
     */
    public static boolean nosotrosDiceEsMalo(DetectorLanzer lanzer) {
        return lanzer.desanimado();
    }
    
    @Override
    public TipoGUI tipo() {
        return TipoGUI.LANZER_MALO;
    }
    
    /**
     * Carga los datos iniciales desde los archivos JSON
     */
    protected void cargarDatos() {
        this.lanzadores = DetectorLanzer.DETECTORES_DE_LANZERES;
        this.recomendados = new ArrayList<>();
        this.noRecomendados = new HashMap<>();
        
        cargarRecomendados();
        cargarNoRecomendados();
    }
    
    /**
     * Carga los lanzadores recomendados desde el archivo JSON
     */
    private void cargarRecomendados() {
        if (archivoRecomendados.toFile().exists() && archivoRecomendados.toFile().length() > 0) {
            try {
                String contenido = new String(Files.readAllBytes(archivoRecomendados), StandardCharsets.UTF_8);
                if (contenido != null && !contenido.trim().isEmpty()) {
                    Nodo raiz = Json.leer(contenido);
                    if (raiz.esArreglo()) {
                        for (int i = 0; i < raiz.tamano(); i++) {
                            Nodo item = raiz.en(i);
                            if (item != null && !item.esObjeto() && !item.esArreglo()) {
                                String id = item.comoCadena();
                                if (id != null && !id.trim().isEmpty() && !recomendados.contains(id)) {
                                    recomendados.add(id);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // Ignorar errores
            }
        }
    }
    
    /**
     * Carga los lanzadores no recomendados desde el archivo JSON
     */
    private void cargarNoRecomendados() {
        if (archivoNoRecomendados.toFile().exists() && archivoNoRecomendados.toFile().length() > 0) {
            try {
                String contenido = new String(Files.readAllBytes(archivoNoRecomendados), StandardCharsets.UTF_8);
                if (contenido != null && !contenido.trim().isEmpty()) {
                    Nodo raiz = Json.leer(contenido);
                    if (raiz.esObjeto()) {
                        for (String key : obtenerClaves(raiz)) {
                            Nodo valor = raiz.obtener(key);
                            if (valor.esObjeto()) {
                                Map<String, String> motivos = new HashMap<>();
                                for (String lang : obtenerClaves(valor)) {
                                    Nodo motivoNode = valor.obtener(lang);
                                    if (motivoNode != null && !motivoNode.esObjeto() && !motivoNode.esArreglo()) {
                                        String motivo = motivoNode.comoCadena();
                                        if (motivo != null && !motivo.trim().isEmpty()) {
                                            motivos.put(lang, motivo);
                                        }
                                    }
                                }
                                if (!noRecomendados.containsKey(key)) {
                                    noRecomendados.put(key, motivos);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // Ignorar errores
            }
        }
    }
    
    /**
     * Obtiene las claves de un objeto JSON
     */
    private List<String> obtenerClaves(Nodo nodo) {
        // Este método es necesario porque el API de Json no expone directamente las claves
        List<String> claves = new ArrayList<>();
        try {
            // Intentar usar reflexión para obtener las claves si el método no está disponible
            java.lang.reflect.Method method = nodo.getClass().getMethod("obtenerClaves");
            Object result = method.invoke(nodo);
            if (result instanceof List) {
                @SuppressWarnings("unchecked")
                List<String> lista = (List<String>) result;
                claves.addAll(lista);
            }
        } catch (Exception e) {
            // Si falla, intentar con métodos alternativos dependiendo del motor JSON
            try {
                if (nodo.interno instanceof com.google.gson.JsonObject) {
                    com.google.gson.JsonObject obj = (com.google.gson.JsonObject) nodo.interno;
                    for (String key : obj.keySet()) {
                        claves.add(key.toString());
                    }
                } else if (nodo.interno instanceof org.jboss.dmr.ModelNode) {
                    org.jboss.dmr.ModelNode node = (org.jboss.dmr.ModelNode) nodo.interno;
                    for (String key : node.keys()) {
                        claves.add(key);
                    }
                }
            } catch (Exception ignored) {
                // No hacer nada, devolver lista vacía
            }
        }
        return claves;
    }
    
    /**
     * Guarda los datos en los archivos JSON
     */
    protected void guardarDatos() {
        guardarRecomendados();
        guardarNoRecomendados();
    }
    
    /**
     * Guarda los lanzadores recomendados en el archivo JSON
     */
    private void guardarRecomendados() {
        try {
            // Crear un array vacío
            Nodo raiz = Json.leer("[]");
            for (String id : recomendados) {
                raiz.agregar(id);
            }
            
            Files.write(archivoRecomendados, raiz.escribir().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // Manejar error
        }
    }
    
    /**
     * Guarda los lanzadores no recomendados en el archivo JSON
     */
    private void guardarNoRecomendados() {
        try {
            // Crear un objeto vacío
            Nodo raiz = Json.crearObjeto();
            for (Map.Entry<String, Map<String, String>> entry : noRecomendados.entrySet()) {
                String id = entry.getKey();
                Map<String, String> motivos = entry.getValue();
                
                Nodo objetoLanzador = raiz.obtener(id).poner(Json.crearObjeto());
                for (Map.Entry<String, String> motivoEntry : motivos.entrySet()) {
                    String lang = motivoEntry.getKey();
                    String motivo = motivoEntry.getValue();
                    objetoLanzador.obtener(lang).poner(motivo);
                }
            }
            
            Files.write(archivoNoRecomendados, raiz.escribir().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // Manejar error
        }
    }
    
    /**
     * Obtiene todos los lanzadores disponibles
     * @return Lista de nombres de lanzadores
     */
    public List<String> obtenerNombresLanzadores() {
        List<String> nombres = new ArrayList<>();
        for (DetectorLanzer lanzador : lanzadores) {
            nombres.add(lanzador.id());
        }
        return nombres;
    }
    
    /**
     * Obtiene los lanzadores recomendados actualmente
     * @return Lista de nombres de lanzadores recomendados
     */
    public List<String> obtenerRecomendados() {
        return new ArrayList<>(recomendados);
    }
    
    /**
     * Obtiene los lanzadores no recomendados actualmente
     * @return Mapa con nombres de lanzadores y sus motivos por idioma
     */
    public Map<String, Map<String, String>> obtenerNoRecomendados() {
        return new HashMap<>(noRecomendados);
    }
    
    /**
     * Mueve un lanzador de recomendados a no recomendados
     * @param id ID del lanzador
     * @param motivos Mapa de motivos por idioma
     */
    public void moverANoRecomendados(String id, Map<String, String> motivos) {
        recomendados.remove(id);
        noRecomendados.put(id, motivos);
    }
    
    /**
     * Mueve un lanzador de no recomendados a recomendados
     * @param id ID del lanzador
     */
    public void moverARecomendados(String id) {
        if (noRecomendados.containsKey(id)) {
            recomendados.add(id);
            noRecomendados.remove(id);
        } else if (!recomendados.contains(id)) {
            recomendados.add(id);
        }
    }
    
    /**
     * Obtiene si un lanzador está marcado como desaconsejado por CrashDetector
     * @param id ID del lanzador
     * @return true si CrashDetector lo desaconseja
     */
    public boolean esDesaconsejadoPorCrashDetector(String id) {
        for (DetectorLanzer lanzador : lanzadores) {
            if (lanzador.id().equals(id)) {
                return nosotrosDiceEsMalo(lanzador);
            }
        }
        return false;
    }
}