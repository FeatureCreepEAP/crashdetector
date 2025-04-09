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
}
