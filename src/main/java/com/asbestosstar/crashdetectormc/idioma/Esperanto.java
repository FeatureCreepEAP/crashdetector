package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Config;
import com.asbestosstar.crashdetectormc.Idioma;

public class Esperanto implements Idioma {
    private final Config config = Config.obtenerInstancia();

    @Override
    public String carpeta_de_mods_no_valido() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Malvalida mod-dosierujo</span>";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Ne troveblas JAR-dosiero de CrashDetector</span>";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Serĉante PID: " + String.valueOf(pid) + "</span>";
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid) + ") ĉesis funkcii!</span>";
    }

    @Override
    public String no_tenemos_jvm() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Mankas JVM</span>";
    }

    @Override
    public String probelma_con_graficas_ati() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Ĝisdatigo de ŝoforprogramoj eble helpos. Notu: kiam ŝoforprogramoj difektiĝas, regula ĝisdatigo eble ne trovos ĝin. Bonvolu sekvi la gvidilon ĉe la ligilo. Grava: Se vi uzas Nvidia-karton, certigu ke ĉiuj Minecraft-rilataj agordoj (kiel Java kaj lanĉilo) estas agorditaj al 'preferi altan rendimenton' en Windows-agordoj kaj Nvidia-kontrolpanelo. Legu ĉi tiun gvidilon: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Gvido por ĝisdatigi ŝoforprogramojn</a></span>";
    }

    @Override
    public String probelma_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>Kelkaj malnovaj versioj foje havas problemojn kun fruaj ŝarĝoj de Nouveau-kartoj.</span>";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Estas problemo kun via grafik-ŝoforprogramo. Se vi uzas AMD/ATI GPU aŭ APU, ĝisdatigu viajn AMD-ŝoforprogramojn. Se vi uzas NVIDIA-karton, certigu ke la ludo kaj ĉiuj javaw.exe-ekzempleroj uzas apartan grafikkarton. Legu ĉi tiun gvidilon: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Gvido por ĝisdatigi ŝoforprogramojn</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Via FML-frua fenestro malsukcesis ŝargi. Por ripari, iru al (.minecraft/config/fml.toml) kaj agordu 'earlyWindowProvider' al \"none\". Se vi uzas Mac M1, certigu ke vi uzas ARM-version de Java, ne Intel x64. Ĉi tio ankaŭ ofte okazas pro malnovaj ŝoforprogramoj. Se vi uzas Windows kaj malŝalti ĉi tiun agordon ne helpas, konsultu ĉi tiun gvidilon: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Gvido por ĝisdatigi ŝoforprogramojn</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Mankas necesaj dependaĵoj:</span>";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>" + linea.replace("Requested by", "Petita de").replace("Expected range", "Atendata amplekso") + "</span>";
    }

    @Override
    public String local_headless(String archivo) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Via CrashDetector-raporto troviĝas ĉi tie <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>Vidi raporton</a></span>";
    }

    @Override
    public String texto_de_gui() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Ĉi tiu estas la GUI de CrashDetector. Se la ludo normale fermiĝis, ignoru ĉi tiun ekranon.</span>";
    }

    @Override
    public String texto_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorBoton() + "'>Vidi raporton</span>";
    }

    @Override
    public String texto_debajo_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Vidi lokan raporton en retumilo.</span>";
    }

    @Override
    public String texto_de_buton_compartir_enlance() {
        return "Kunhavigi raporton";
    }

    @Override
    public String texto_debajo_de_buton_compartir_enlance() {
        return "Kunhavigi raporton. Viaj protokoloj estos alŝutitaj al securelogger.net, kaj la raporto al aliaj ejoj.";
    }

    @Override
    public String problematico_jar() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Detektita problema JAR-dosiero (prioritatu FATAL, poste alta kaj malalta prioritato):</b>";
    }

    @Override
    public String nivel() {
        return "<b style='color:#" + config.obtenerColorError() + "'> Nivelo:</b> ";
    }

    @Override
    public String possibladad_fatal() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Eble fatala:</b> ";
    }

    @Override
    public String modids_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Detektitaj problemaj ModID (prioritatu FATAL, poste alta kaj malalta prioritato):</b>";
    }

    @Override
    public String packages_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Detektitaj problemaj pakoj (prioritatu FATAL, poste alta kaj malalta prioritato):</b>";
    }

    @Override
    public String faltar_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Detektitaj mankantaj kritikaj klasoj:</b>";
    }

    @Override
    public String corchetes_ondulados() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Enhavo de {} (plej grava supre, nur unua 20 montritaj):</b>";
    }

    @Override
    public String config_spongemixin_problematico(String archivo) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Detektita problema SpongeMixin-agordo: </b>" + archivo;
    }

    @Override
    public String module_resolution_exception(String modules, String paquete) {
        return "<span style='color:#" + config.obtenerColorError() + "'>Viaj Modoj havas duoblajn pakojn: " + modules + " duobla pako " + paquete.replace(".", "/") + ". Vi povas ripari ĉi tion forigante la pakon (dosierujon) el la JAR-dosiero uzante arkivilon kiel WinRAR aŭ 7z, aŭ ŝanĝante la dosierfinaĵon de .jar al .zip, forigante la dosierujon, kaj reŝanĝante ĝin al .jar.</span>";
    }

    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Detektitaj duoblaj Modoj</b> " + linea.replace("from mod files", "el mod-dosieroj");
    }

    @Override
    public String mcforge_mod_suspechoso() {
        return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge detektis problemojn kun suspektinda mod:</b> ";
    }

    @Override
    public String lithostichctov() {
        return "<b style='color:#" + config.obtenerColorError() + "'>CTOV bezonas lithostitched, instalebla ĉi tie: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#" + config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
    }

    @Override
    public String necesitasSodiumParaIris() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Por uzi Iris-ŝejnilojn aŭ Oculus, vi bezonas kongruan version de SODIUM aŭ alia ŝarĝilo (Rubidium, Embedium, Bedium)</b>";
    }

    @Override
    public String kubeJSResourcePack(String mod_nombre) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Problemo kun KubeJS-etendaĵo </b>" + mod_nombre;
    }
}
