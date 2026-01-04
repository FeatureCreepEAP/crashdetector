package com.asbestosstar.crashdetector.analizador.general;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

/**
 * Verificador anti-manipulación: compara hashes de archivos y carpetas contra un archivo de control.
 * Soporta archivos individuales y carpetas recursivas.
 * Usa xxHash32 para hashing rápido y ForkJoinPool para paralelismo.
 */
public class AntiManipulacion implements Verificaciones {

    public static final Path ARCHIVO_ANTIMANIPULACION = Statics.carpeta.resolve("antimanipulacion.json");
    private boolean activado = false;
    private String mensaje = "";

    @Override
    public void verificar(Consola consola) {
        this.activado = false;
        this.mensaje = "";

        if (!ARCHIVO_ANTIMANIPULACION.toFile().exists()) {
            return;
        }

        String contenido;
        try {
            contenido = new String(java.nio.file.Files.readAllBytes(ARCHIVO_ANTIMANIPULACION), java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            return;
        }
        if (contenido == null || contenido.trim().isEmpty()) {
            return;
        }

        Nodo raiz;
        try {
            raiz = Json.leer(contenido);
        } catch (Exception e) {
            return;
        }
        if (!raiz.esArreglo()) {
            return;
        }

        List<String> errores = new ArrayList<>();
        int tam = raiz.tamano();

        // Determinar número de hilos: 2x núcleos
        //int numHilos = Runtime.getRuntime().availableProcessors() * 2;
        int numHilos = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(numHilos);

        try {
            for (int i = 0; i < tam; i++) {
                Nodo item = raiz.en(i);
                if (item == null || !item.esObjeto()) continue;

                Nodo nodoRuta = item.obtener("ruta");
                if (nodoRuta == null || nodoRuta.esObjeto() || nodoRuta.esArreglo()) continue;

                String rutaRelativa = nodoRuta.comoCadena();
                if (rutaRelativa == null || rutaRelativa.trim().isEmpty()) continue;

                // Convertir a ruta absoluta
                Path rutaAbsoluta = Paths.get(rutaRelativa);
                
                if (!rutaAbsoluta.isAbsolute()) {
                    Path userDir = Paths.get(System.getProperty("user.dir"));
                    rutaAbsoluta = userDir.resolve(rutaRelativa);
                }

                Nodo nodoCarpetas = item.obtener("es_carpeta");
                boolean esCarpeta = false;
                if (nodoCarpetas != null) {
                    try {
                        esCarpeta = nodoCarpetas.comoBooleano();
                    } catch (Exception ignored) {
                        esCarpeta = false;
                    }
                }

                if (esCarpeta) {
                    // Verificar carpeta recursivamente
                    Nodo nodoHashes = item.obtener("hashes");
                    if (nodoHashes == null || !nodoHashes.esObjeto()) {
                        errores.add("Entrada de carpeta inválida: " + rutaRelativa);
                        continue;
                    }

                    Map<String, String> hashesEsperados = new HashMap<>();
                    int numHashes = 0;
                    while (true) {
                        Nodo clave = nodoHashes.obtener(String.valueOf(numHashes));
                        if (clave == null) break;
                        if (clave.esArreglo() && clave.tamano() == 2) {
                            Nodo subRuta = clave.en(0);
                            Nodo hash = clave.en(1);
                            if (subRuta != null && hash != null) {
                                String r = subRuta.comoCadena();
                                String h = hash.comoCadena();
                                if (r != null && h != null) {
                                    hashesEsperados.put(r, h);
                                }
                            }
                        }
                        numHashes++;
                    }

                    if (hashesEsperados.isEmpty()) {
                        errores.add("Carpeta sin hashes: " + rutaRelativa);
                        continue;
                    }

                    // Escanear archivos reales
                    Map<String, String> hashesReales = new ConcurrentHashMap<>();
                    List<Path> archivosParaHash = new ArrayList<>();

                    try {
                        Files.walk(rutaAbsoluta)
                            .filter(Files::isRegularFile)
                            .forEach(p -> archivosParaHash.add(p));
                    } catch (IOException e) {
                        errores.add("No se pudo acceder a la carpeta: " + rutaRelativa);
                        continue;
                    }

                    // Calcular hashes en paralelo
                    pool.submit(new HashTask(archivosParaHash, rutaAbsoluta, hashesReales)).join();

                    // Verificar
                    // 1. Archivos faltantes o con hash incorrecto
                    for (Map.Entry<String, String> entry : hashesEsperados.entrySet()) {
                        String subRuta = entry.getKey();
                        String hashEsperado = entry.getValue();
                        String hashReal = hashesReales.get(subRuta);
                        if (hashReal == null) {
                            errores.add("Archivo faltante en carpeta: " + rutaRelativa + "/" + subRuta);
                        } else if (!hashReal.equals(hashEsperado)) {
                            errores.add("Hash incorrecto en: " + rutaRelativa + "/" + subRuta);
                        }
                    }

                    // 2. Archivos nuevos no esperados
                    for (String subRuta : hashesReales.keySet()) {
                        if (!hashesEsperados.containsKey(subRuta)) {
                            errores.add("Archivo no autorizado en carpeta: " + rutaRelativa + "/" + subRuta);
                        }
                    }

                } else {
                    // Verificar archivo individual
                    Nodo nodoHash = item.obtener("hash");
                    if (nodoHash == null || nodoHash.esObjeto() || nodoHash.esArreglo()) {
                        errores.add("Entrada de archivo inválida: " + rutaRelativa);
                        continue;
                    }

                    String hashEsperado = nodoHash.comoCadena();
                    if (hashEsperado == null || hashEsperado.trim().isEmpty()) {
                        errores.add("Hash faltante para archivo: " + rutaRelativa);
                        continue;
                    }

                    if (!Files.exists(rutaAbsoluta)) {
                        errores.add("Archivo faltante: " + rutaRelativa);
                        continue;
                    }

                    String hashReal;
                    try {
                        hashReal = calcularHash(rutaAbsoluta);
                    } catch (IOException e) {
                        errores.add("Error al leer archivo: " + rutaRelativa);
                        continue;
                    }

                    if (!hashReal.equals(hashEsperado)) {
                        errores.add("Hash incorrecto para archivo: " + rutaRelativa);
                    }
                }
            }
        } finally {
            pool.shutdown();
        }

        if (!errores.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(MonitorDePID.idioma.antimanipulacion_titulo());
            sb.append("<ul>");
            for (String error : errores) {
                // Convertir rutas absolutas a relativas en el mensaje
            	String errorRel = error.replace(Paths.get(System.getProperty("user.dir")).toString(), ".");
                sb.append("<li>").append(errorRel).append("</li>");
            }
            sb.append("</ul>");
            this.mensaje = sb.toString();
            this.activado = true;
        }
    }

    // === Tarea recursiva para hashing paralelo ===
    private static class HashTask extends RecursiveAction {
        private final List<Path> archivos;
        private final Path raiz;
        private final Map<String, String> resultado;

        HashTask(List<Path> archivos, Path raiz, Map<String, String> resultado) {
            this.archivos = archivos;
            this.raiz = raiz;
            this.resultado = resultado;
        }

        @Override
        protected void compute() {
            if (archivos.size() <= 100) {
                // Procesar directamente
                for (Path archivo : archivos) {
                    try {
                        String rel = raiz.relativize(archivo).toString();
                        String hash = calcularHash(archivo);
                        resultado.put(rel, hash);
                    } catch (IOException ignored) {
                        // Ignorar archivos no legibles
                    }
                }
            } else {
                // Dividir
                int mitad = archivos.size() / 2;
                List<Path> izq = archivos.subList(0, mitad);
                List<Path> der = archivos.subList(mitad, archivos.size());
                invokeAll(new HashTask(izq, raiz, resultado), new HashTask(der, raiz, resultado));
            }
        }
    }

    // === xxHash32 simple y rápido ===
    private static String calcularHash(Path archivo) throws IOException {
        try (InputStream in = Files.newInputStream(archivo)) {
            long hash = 0x9E3779B1L;
            byte[] buffer = new byte[8192];
            int n;
            while ((n = in.read(buffer)) != -1) {
                for (int i = 0; i < n; i++) {
                    hash ^= (hash << 5) + (hash >>> 2) + (buffer[i] & 0xFF);
                }
            }
            return String.format("%08x", hash & 0xFFFFFFFFL);
        }
    }

    @Override
    public Verificaciones nueva() {
        return new AntiManipulacion();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1500.0f;
    }

    @Override
    public String mensaje() {
        return mensaje;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_antimanipulacion();
    }

    @Override
    public QuickFix solucion() {
        Builder builder = new Builder(nombre());
        builder.agregarEtiqueta(MonitorDePID.idioma.antimanipulacion_reinstalar());
        return builder.construir();
    }

    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        return false;
    }

    @Override
    public String id() {
        return "antimanipulacion";
    }

    @Override
    public boolean anularNormal() {
        return activado; // Si hay manipulación, forzar apertura de CrashDetector
    }
}