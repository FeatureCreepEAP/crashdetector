package com.asbestosstar.crashdetector.analizador.general;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

/**
 * Detecta mods que están en la lista de mods desaconsejados ("desanimados") y que sí están instalados.
 * Usa los 3 métodos disponibles para encontrar mods instalados y elimina duplicados.
 */
public class TienesModDesAnimado implements Verificaciones {

    public static final Path ARCHIVO_DESANIMADOS = Statics.carpeta.resolve("mods_desanimados.json");
    private boolean activado = false;
    private String mensaje = "";
    private boolean activarCD = false;

    @Override
    public void verificar(Consola consola) {

        if (!ARCHIVO_DESANIMADOS.toFile().exists()) {
            return;
        }

        String contenido;
        try {
            contenido = new String(Files.readAllBytes(ARCHIVO_DESANIMADOS), java.nio.charset.StandardCharsets.UTF_8);
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

        // === Recopilar ubicaciones y modids instalados ===
        Set<String> rutasInstaladas = new HashSet<>();
        Set<String> modidsInstalados = new HashSet<>();

        // 1. Desde archivo de mods
        try {
            if (MonitorDePID.ultimo_mods != null && Files.exists(MonitorDePID.ultimo_mods)) {
                Set<String> desdeArchivo = obtenerMods(MonitorDePID.ultimo_mods);
                rutasInstaladas.addAll(desdeArchivo);
            }
        } catch (Exception ignored) {}

        // 2. Desde escaneo recursivo
        try {
            List<ArchivoDeMod> desdeRecursivo = Buscardor.obtenerTodosLosModsYSubmodsRecursivos();
            if (desdeRecursivo != null) {
                for (ArchivoDeMod mod : desdeRecursivo) {
                    if (!mod.nombre().isEmpty()) {
                        modidsInstalados.addAll(mod.nombre());
                    }
                    String ubic = mod.ubicacion_para_publicar();
                    if (ubic != null && !ubic.isEmpty()) {
                        String rel = ubic.replace(MonitorDePID.ultimo_mods.toString(), ".");
                        rutasInstaladas.add(rel);
                    }
                }
            }
        } catch (Exception ignored) {}

        // === Buscar coincidencias ===
        Set<String> ubicacionesCoincidentes = new HashSet<>();
        int tam = raiz.tamano();
        for (int i = 0; i < tam; i++) {
            Nodo item = raiz.en(i);
            if (item == null || !item.esObjeto()) continue;

            String modid = null;
            String ruta = null;
            boolean abrirCD = false;

            Nodo nodoModid = item.obtener("modid");
            if (nodoModid != null && !nodoModid.esObjeto() && !nodoModid.esArreglo()) {
                modid = nodoModid.comoCadena();
            }

            Nodo nodoRuta = item.obtener("ruta");
            if (nodoRuta != null && !nodoRuta.esObjeto() && !nodoRuta.esArreglo()) {
                ruta = nodoRuta.comoCadena();
            }

            if (modid == null && ruta == null) {
                continue;
            }

            Nodo nodoAbrir = item.obtener("abrir_cd_si_coincide");
            if (nodoAbrir != null) {
                try {
                    abrirCD = nodoAbrir.comoBooleano();
                } catch (Exception ignored) {
                    abrirCD = false;
                }
            }

            boolean encontrado = false;

            // Coincidir por modid (si está presente)
            if (modid != null && !modid.trim().isEmpty()) {
                try {
                    List<String> rutasPorModid = Buscardor.obtenerModsConNombre(modid);
                    if (rutasPorModid != null) {
                        for (String rutaAbs : rutasPorModid) {
                            String rel = rutaAbs.replace(MonitorDePID.ultimo_mods.toString(), ".");
                            if (rutasInstaladas.contains(rel)) {
                                ubicacionesCoincidentes.add(rel);
                                encontrado = true;
                            }
                        }
                    }
                } catch (Exception ignored) {}
            }

            // Coincidir por ruta (si está presente)
            if (!encontrado && ruta != null && !ruta.trim().isEmpty()) {
                for (String ubic : rutasInstaladas) {
                    if (ubic.contains(ruta)) {
                        ubicacionesCoincidentes.add(ubic);
                        encontrado = true;
                        break;
                    }
                }
            }

            if (encontrado && abrirCD) {
                this.activarCD = true;
            }
        }

        if (!ubicacionesCoincidentes.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(MonitorDePID.idioma.tienes_mod_desanimado_titulo());
            sb.append("<ul>");
            for (String ubic : ubicacionesCoincidentes) {
                sb.append("<li><code>").append(ubic).append("</code></li>");
            }
            sb.append("</ul>");

            // Agregar razones si existen
            for (int i = 0; i < tam; i++) {
                Nodo item = raiz.en(i);
                if (item == null || !item.esObjeto()) continue;

                Nodo nodoRazon = item.obtener("razon");
                if (nodoRazon != null && nodoRazon.esObjeto()) {
                    String langActual = MonitorDePID.idioma.codigo();
                    String langRespaldo = Idioma.idioma_respaldo.obtener();
                    String[] orden = { langActual, langRespaldo, "es" };
                    for (String lang : orden) {
                        if (lang != null && !lang.isEmpty()) {
                            Nodo nodoTxt = nodoRazon.obtener(lang);
                            if (nodoTxt != null && !nodoTxt.esObjeto() && !nodoTxt.esArreglo()) {
                                String txt = nodoTxt.comoCadena();
                                if (txt != null && !txt.trim().isEmpty()) {
                                    sb.append("<p>").append(txt).append("</p>");
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            this.mensaje = sb.toString();
            this.activado = true;
        }
    }

    private static Set<String> obtenerMods(Path archivo) throws IOException {
        return Files.readAllLines(archivo).stream()
            .filter(line -> !line.trim().isEmpty())
            .map(line -> line.replace(MonitorDePID.ultimo_mods.toString(), "."))
            .collect(Collectors.toSet());
    }

    @Override
    public Verificaciones nueva() {
        return new TienesModDesAnimado();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1250.0f;
    }

    @Override
    public String mensaje() {
        return mensaje;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_tienes_mod_desanimado();
    }

    @Override
    public QuickFix solucion() {
        Builder builder = new Builder(nombre());
        builder.agregarEtiqueta(MonitorDePID.idioma.tienes_mod_desanimado_eliminar());
        return builder.construir();
    }

    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        return false;
    }

    @Override
    public String id() {
        return "tienes_mod_desanimado";
    }

    @Override
    public boolean anularNormal() {
        return activarCD;
    }
}