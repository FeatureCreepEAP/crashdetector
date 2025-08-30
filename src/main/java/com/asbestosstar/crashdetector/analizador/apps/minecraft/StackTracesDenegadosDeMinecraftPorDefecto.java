package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.analizador.ListaDenegadosTrace;
import java.util.ArrayList;
import java.util.List;

public class StackTracesDenegadosDeMinecraftPorDefecto {

    public static List<ListaDenegadosTrace> denegados = new ArrayList<>();

    public static void init() {
        denegados.add(contentido -> contentido.contains("Preparing crash report with UUID"));		// Excluir líneas que contienen "Preparing crash report with UUID"
        denegados.add(contentido -> contentido.contains("Failed to complete lifecycle event"));
        denegados.add(contentido -> contentido.contains("Crash report saved to"));
        denegados.add(contentido -> contentido.contains("Mod Loading has failed"));
        denegados.add(contentido -> contentido.contains("Could not determine mod trust worthiness, Assuming Jar was downloaded from trusted source!"));// FUCK STOPMODREPOSTS
        denegados.add(contentido -> contentido.contains("org.watermedia.videolan4j.discovery.providers"));
        denegados.add(contentido -> contentido.contains("libflite.so"));//TTS no fatal y es comun en TL linux y mac. TODO agregar una verificacion para este
        denegados.add(contentido -> contentido.trim().equals("Stacktrace:"));// Incluir líneas que sean exactamente "Stacktrace:" o contengan "Stacktrace:"
		// como inicio
        denegados.add(contentido -> contentido.trim().startsWith("Stacktrace:"));
    }
}
