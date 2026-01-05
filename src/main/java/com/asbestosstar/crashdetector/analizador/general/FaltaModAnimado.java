package com.asbestosstar.crashdetector.analizador.general;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

/**
 * Detecta mods que están en la lista de mods recomendados ("animados") pero que no están instalados.
 * Usa los 3 métodos disponibles para encontrar mods instalados y elimina duplicados.
 */
public class FaltaModAnimado implements Verificaciones {

    public static final Path ARCHIVO_ANIMADOS = Statics.carpeta.resolve("mods_animados.json");
    private boolean activado = false;
    private String mensaje = "";
    private boolean activarCD = false;
    boolean completa=true;

    @Override
    public void verificar(Consola consola) {
    	if(completa) {
    		return;
    	}
    	this.completa=true;

        if (!ARCHIVO_ANIMADOS.toFile().exists()) {
            return;
        }

        String contenido;
        try {
            contenido = new String(Files.readAllBytes(ARCHIVO_ANIMADOS), java.nio.charset.StandardCharsets.UTF_8);
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

        // === Recopilar todas las ubicaciones y modids instalados ===
        Set<String> rutasInstaladas = new HashSet<>();
        Set<String> modidsInstalados = new HashSet<>();

        // 1. Desde archivo de mods (mods.txt, etc.)
        try {
            if (MonitorDePID.ultimo_mods != null && Files.exists(MonitorDePID.ultimo_mods)) {
                Set<String> desdeArchivo = obtenerMods(MonitorDePID.ultimo_mods);
                rutasInstaladas.addAll(desdeArchivo);
            }
        } catch (Exception ignored) {}

        // 2. Desde escaneo recursivo de mods
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

        // === Buscar mods animados que falten ===
        List<String> modsFaltantesHTML = new ArrayList<>();
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

            // Al menos uno debe existir
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

            boolean presentePorModid = (modid != null) && modidsInstalados.contains(modid);
            boolean presentePorRuta = esPresentePorRuta(ruta,rutasInstaladas);
            
            
            if (!presentePorModid && !presentePorRuta) {
                if (abrirCD) {
                    this.activarCD = true;
                }

                StringBuilder modHTML = new StringBuilder("<li>");
                if (modid != null) {
                    modHTML.append("ModID: <code>").append(modid).append("</code>");
                    if (ruta != null) {
                        modHTML.append(", ");
                    }
                }
                if (ruta != null) {
                    modHTML.append("Archivo: <code>").append(ruta).append("</code>");
                }

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
                                    modHTML.append(" — ").append(txt);
                                    break;
                                }
                            }
                        }
                    }
                }

                modHTML.append("</li>");
                modsFaltantesHTML.add(modHTML.toString());
            }
        }

        if (!modsFaltantesHTML.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(MonitorDePID.idioma.falta_mod_animado_titulo());
            sb.append("<ul>");
            for (String mod : modsFaltantesHTML) {
                sb.append(mod);
            }
            sb.append("</ul>");
            this.mensaje = sb.toString();
            this.activado = true;
        }
    }
    

public boolean esPresentePorRuta(String ruta,Set<String> rutasInstaladas) {
    if (ruta == null) {
        return false;
    }

    for (String r : rutasInstaladas) {
        if (r.contains(ruta)) {
            return true;
        }
    }
    return false;
}
    
    

    private static Set<String> obtenerMods(Path archivo) throws IOException {
        return Files.readAllLines(archivo).stream()
            .filter(line -> !line.trim().isEmpty())
            .map(line -> line.replace(MonitorDePID.ultimo_mods.toString(), "."))
            .collect(Collectors.toSet());
    }

    @Override
    public Verificaciones nueva() {
        return new FaltaModAnimado();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1200.0f;
    }

    @Override
    public String mensaje() {
        return mensaje;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_falta_mod_animado();
    }

    @Override
    public QuickFix solucion() {
        Builder builder = new Builder(nombre());
        builder.agregarEtiqueta(MonitorDePID.idioma.falta_mod_animado_instalar());
        return builder.construir();
    }

    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        return false;
    }

    @Override
    public String id() {
        return "falta_mod_animado";
    }

    @Override
    public boolean anularNormal() {
        return activarCD;
    }
    
	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"+this.getClass().getSimpleName()+".java";
	}
	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
	
	
    
}