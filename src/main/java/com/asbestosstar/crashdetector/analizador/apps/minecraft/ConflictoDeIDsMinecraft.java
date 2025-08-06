package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * https://github.com/HMCL-dev/HMCL/blob/77dc5dbe06ef1ca1cc08cd6c47525999d92a992a/HMCLCore/src/main/java/org/jackhuang/hmcl/game/CrashReportAnalyzer.java#L109C55-L109C80
 * https://forum.feed-the-beast.com/threads/guide-resolving-block-conflict-ids.30682/
 */
public class ConflictoDeIDsMinecraft implements Verificaciones {

    private boolean activado = false;
    private String tipoConflicto = "";
    private String idConflictivo = "";
    private String modOrigen = "";
    private String modDestino = "";
    private final StringBuilder mensaje = new StringBuilder();

    @Override
    public void verificar(Consola consola) {
        String contenidoConsola = consola.contenido_verificar;
        
        // Detección de conflicto de rango máximo de IDs
        if (contenidoConsola.contains("maximum id range exceeded")) {
            tipoConflicto = "maximo_rango";
            activado = true;
        }
        
        // Detección de colisión de IDs específicos
        Pattern patternIDColision = Pattern.compile(
            "java\\.lang\\.IllegalArgumentException: Slot (\\d+) is already occupied by (.*?) when adding (.*?)"
        );
        Matcher matcherIDColision = patternIDColision.matcher(contenidoConsola);
        
        if (matcherIDColision.find()) {
            tipoConflicto = "colision_id";
            idConflictivo = matcherIDColision.group(1);
            modOrigen = matcherIDColision.group(2);
            modDestino = matcherIDColision.group(3);
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ConflictoDeIDsMinecraft();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 750.0f; // Prioridad alta pero menor que errores críticos de drivers
    }

    @Override
    public String mensaje() {
        if ("colision_id".equals(tipoConflicto)) {
            return MonitorDePID.idioma.conflicto_id_colision_especifico(idConflictivo, modOrigen, modDestino);
        } else if ("maximo_rango".equals(tipoConflicto)) {
            return MonitorDePID.idioma.conflicto_id_maximo();
        }
        return "";
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_de_conflicto_ids();
    }

    @Override
    public QuickFix solucion() {
        QuickFix.Builder builder = new QuickFix.Builder(nombre())
            .agregarEtiqueta(obtenerSolucionEspecifica());
        
        // Soluciones según el tipo de conflicto
        if ("maximo_rango".equals(tipoConflicto)) {
            builder.agregarBoton(MonitorDePID.idioma.instalar_justenoughids(), (bool) -> {
                Verificaciones.abrirEnNavegador("https://www.curseforge.com/minecraft/mc-mods/justenoughids");
            })
            .agregarBoton(MonitorDePID.idioma.instalar_endlessids(), (bool) -> {
            	Verificaciones.abrirEnNavegador("https://www.curseforge.com/minecraft/mc-mods/endless-ids");
            });
        } else if ("colision_id".equals(tipoConflicto)) {
            builder.agregarBoton(MonitorDePID.idioma.usar_idfix_minus(), (bool) -> {
            	Verificaciones.abrirEnNavegador("https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id");
            })
            .agregarBoton(MonitorDePID.idioma.usar_minecraft_id_resolver(), (bool) -> {
            	Verificaciones.abrirEnNavegador("https://github.com/SS111/Minecraft-ID-Resolver");
            })
            .agregarBoton(MonitorDePID.idioma.ver_documentacion_jp(), (bool) -> {
            	Verificaciones.abrirEnNavegador("https://minecraftjapan.miraheze.org/wiki/MOD%E8%A7%A3%E8%AA%AC/ID%E7%AB%B6%E5%90%88%E9%98%B2%E6%AD%A2%E6%94%AF%E6%8F%B4MOD");
            });
        }
        
        return builder.construir();
    }
    
    private String obtenerSolucionEspecifica() {
        if ("maximo_rango".equals(tipoConflicto)) {
            return MonitorDePID.idioma.solucion_maximo_rango();
        } else if ("colision_id".equals(tipoConflicto)) {
            return MonitorDePID.idioma.solucion_colision_id();
        }
        return "";
    }

}