package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Idioma;

public class Esperanto implements Idioma {

    @Override
    public String carpeta_de_mods_no_valido() {
        return "Ne validas dosierujo por aldonaĵoj";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "Mi ne scias kie estas la JAR-dosiero de CrashDetector";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "Serĉante PID: " + String.valueOf(pid);
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "(PID: " + String.valueOf(pid) + ") estas morta!";
    }

    @Override
    public String no_tenemos_jvm() {
        return "Ni ne havas JVM";
    }
    
    @Override
    public String probelma_con_graficas_ati() {
        return "Ĝisdatigi viajn pelilojn eble helpos. Notu, ke serĉi ĝisdatigojn per la kutimaj manieroj ne trovos iujn ajn, kiam la peliloj estas difektitaj, do gravas sekvi la ligitan gvidilon. Grava: Se vi havas Nvidia-grafikojn, certigu, ke vi agordis ion ajn rilatan al Minecraft (kiel Java kaj lanĉiloj) por prioritati altan rendimenton en ambaŭ la Windows-agordoj kaj la Nvidia-kontrolpanelo. Legu ĉi tiun gvidilon por solvi tion: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }

    @Override
    public String probelma_con_graficas_nouveau() {
        return "Kelkaj pli malnovaj versioj foje havas kelkajn problemojn kun iuj Nouveau-grafikoj dum la frua ŝargada ekrano.";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "Vi havas problemon kun viaj grafikaj peliloj. Se vi havas AMD/ATI GPU aŭ APU, ĝisdatigu viajn AMD-grafikajn pelilojn. Se vi havas NVIDIA-grafikan karton, certigu, ke vi markis la ludon kaj ĉiujn kazojn de javaw.exe por uzi la dediĉitan grafikan karton. Legu ĉi tiun gvidilon: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }

    @Override
    public String fmlEarlyWindow() {
        return "Via FML Early fenestro malsukcesas. "
                + "Por ŝanĝi tion, iru al (.)minecraft/config/fml.toml "
                + "Redaktu earlyWindowProvider por ke ĝi estu earlyWindowProvider=\"none\" "
                + "Se vi uzas M1-Mak, certigu ankaŭ, ke vi uzas ARM-version de Java, ne Intel x64-version. "
                + "Tio ankaŭ estas komuna problemo se vi havas malĝisdatigitajn pelilojn. Kontrolu ĉi tiun gvidilon se vi estas en Windows kaj malaktivigado de tio ne funkcias: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "Vi ne havas ĉiujn bezonatajn dependaĵojn:";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return linea.replace("Requested by", "Petita de").replace("Expected range", "Atendata intervalo");
    }
    
        @Override
    public String local_headless(String archivo) {
        return "Via CrashDetector-raporto estas ĉi tie: " + archivo;
    }
    
        @Override
    // Esperanto-teksto por GUI
    public String texto_de_gui() {
        return "Jen la grafika interfaco de CrashDetector. Se la ludo fermiĝas sen problemoj, ignoru ĝin.";
    }
    
        @Override
    // Esperanto-versio
    public String texto_de_buton_local_enlance() {
        return "Vidi Raporton";
    }

    @Override
    // Subteksto por loka butono
    public String texto_debajo_de_buton_local_enlance() {
        return "Vidi lokan raporton en via retumilo";
    }

    @Override
    // Teksto por kunhava butono
    public String texto_de_buton_compartir_enlance() {
        return "Kunhavigi Raporton";
    }

    @Override
    // Subteksto por kunhava butono
    public String texto_debajo_de_buton_compartir_enlance() {
        return "Viaj registroj alŝutiĝos al securelogger.net kaj la raporto restos en alia retejo por 3 tagoj";
    }

    @Override
public String problematico_jar() {
    return "<b>Trovitaj potence problemaj JAR-dosieroj (Prioritato: FATAL > pli alta nivelo > pli malalta linio):</b>";
}

@Override
public String nivel() {
    return "<b>lvl: </b>";
}

@Override
public String possibladad_fatal() {
    return "<b>Eble fatale:</b> ";
}

@Override
public String modids_problematicos() {
    return "<b>Trovitaj problemaj modid (Prioritato: FATAL > pli malalta nivelo > pli malalta linio):</b>";
}

@Override
public String packages_problematicos() {
    return "<b>Trovitaj problemaj pakaĵoj (Prioritato: FATAL > pli malalta nivelo > pli malalta linio):</b>";
}

@Override
public String faltar_de_clases_fatales() {
    return "<b>Mankantaj fatalaj klasoj trovitaj:</b>";
}

@Override
public String corchetes_ondulados() {
    return "<b>Enhavo en {} (Plej grava supre, nur unuaj 20):</b>";
}

@Override
public String config_spongemixin_problematico(String archivo) {
    return "<b>Trovita problemata SpongeMixin agordo: " + archivo + "</b>";
}

@Override
public String module_resolution_exception(String modules, String paquete) {
    return "Vi havas Modojn kun duplikataj Pakoj: " + modules + " duplikata pako " + paquete.replace(".", "/") + ". Vi povas solvi tion forigante la pakon (dosierujon) el la jar-dosiero. Vi povas malfermi la jar-dosieron per arkiva programaro kiel File-Roller,WinRAR aŭ 7-Zip, aŭ ŝanĝi la dosiernomon de .jar al .zip, forigi la dosierujon, kaj poste reŝanĝi ĝin al .jar.";
}

@Override
public String modlauncher_mods_duplicado(String linea) {
    return "<b>Vi havas duplikatajn Modojn</b> " + linea.replace("from mod files", "el mod-dosieroj");
}

@Override
public String mcforge_mod_suspechoso() {
    return "<b>MinecraftForge suspektinda: Ĉi tiu mod enhavas problemon:</b> ";
}

@Override
public String lithostichctov() {
    return "<b>CTOV bezonas lithostitched, vi povas instali ĝin de tie ĉi https://www.curseforge.com/minecraft/mc-mods/lithostitched</b>";
}


}
