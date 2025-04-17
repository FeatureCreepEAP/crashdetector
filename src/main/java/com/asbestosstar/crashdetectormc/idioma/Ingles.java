package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Config;
import com.asbestosstar.crashdetectormc.Idioma;

public class Ingles implements Idioma {
    private final Config config = Config.obtenerInstancia(); // Assuming this is how you get the Config instance, like in Espanol

    @Override
    public String carpeta_de_mods_no_valido() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Not a valid mods folder</span>";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "<span style='color:#" + config.obtenerColorError() + "'>I don't know where the CrashDetector JAR file is</span>";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Looking for PID: " + String.valueOf(pid) + "</span>";
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid) + ") is dead!</span>";
    }

    @Override
    public String no_tenemos_jvm() {
        return "<span style='color:#" + config.obtenerColorError() + "'>We don't have JVM</span>";
    }

    @Override
    public String probelma_con_graficas_ati() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Updating your drivers might help. Please bear in mind that checking for updates the usual ways won't find any updates when drivers are in a broken state, so it's important you follow the linked guide. Important: If you have Nvidia graphics, also make sure you've set anything Minecraft-related (such as Java and launchers) to prioritise high performance graphics in both the Windows settings and Nvidia control panel. Read this guide to fix them: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Guide to update drivers</a></span>";
    }

    @Override
    public String probelma_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>Some older versions sometimes have a few issues with some Nouveau Graphics on early loading screen</span>";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>You have an issue with your Graphics Drivers. If you have an AMD/ATI GPU or APU update your AMD graphics drivers. If you have an NVIDIA graphics card make sure to mark the game and all instances of javaw.exe to use the dedicated graphics card. Read this guide: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Guide to update drivers</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Your FML Early Window is failing. "
                + "To Change this go to (.minecraft/config/fml.toml) "
                + "Edit earlyWindowProvider to be earlyWindowProvider=\"none\" "
                + "If you are on an M1 Mac you should also make sure you are using an ARM version of Java not an Intel x64 one. "
                + "This is also a common issue if you have outdated Drivers. Please check this guide if on windows and turning of this does not work. <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Guide to update drivers</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "<span style='color:#" + config.obtenerColorError() + "'>You do not have all the dependencies you need:</span>";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>" + linea + "</span>"; // Assuming 'linea' is already in English and just needs color
    }

    @Override
    public String local_headless(String archivo) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Your CrashDetector report is here: <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>View Report</a></span>";
    }

    @Override
    public String texto_de_gui() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>This is the CrashDetector GUI. If the game closes without issues, ignore it.</span>";
    }

    @Override
    public String texto_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorBoton() + "'>View Report</span>";
    }

    @Override
    public String texto_debajo_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>View a local report in your browser.</span>";
    }

    @Override
    public String texto_de_buton_compartir_enlance() {
        return "Share Report"; // Button text, no color span needed as per Spanish version (or you can add if you want)
    }

    @Override
    public String texto_debajo_de_buton_compartir_enlance() {
        return "Share report, logs will be uploaded to securelogger.net and stored on another site";
    }

    @Override
    public String problematico_jar() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Found potentially problematic JAR files (Prioritise FATAL then Higher lvl then lower ln):</b>";
    }

    @Override
    public String nivel() {
        return "<b style='color:#" + config.obtenerColorError() + "'>lvl: </b>";
    }

    @Override
    public String possibladad_fatal() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Possibly Fatal:</b> ";
    }

    @Override
    public String modids_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Found potentially problematic modids (Prioritise FATAL then Lower lvl then lower ln):</b>";
    }

    @Override
    public String packages_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Found potentially problematic packages (Prioritise FATAL then Lower lvl then lower ln):</b>";
    }

    @Override
    public String faltar_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Found potentially fatal missing classes:</b>";
    }

    @Override
    public String corchetes_ondulados() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Found contents in {} (Top is most important, only top 20 shown):</b>";
    }

    @Override
    public String config_spongemixin_problematico(String archivo_json) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Potentially Problematic SpongeMixin Config:</b> " + archivo_json;
    }

    @Override
    public String module_resolution_exception(String modules, String paquete) {
        return "<span style='color:#" + config.obtenerColorError() + "'>You have Mods with duplicated Packages: " + modules + " duplicated package " + paquete.replace(".", "/") + ". You can fix this by removing the package (folder) from the jar file. You can open the jar file using archive software like file-roller, WinRAR or 7-Zip, or change the file extension from .jar to .zip, delete the folder, and then change it back to a .jar file.</span>";
    }

    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>You have duplicate Mods</b> " + linea.replace("from mod files", "from mod files");
    }

    @Override
    public String mcforge_mod_suspechoso() {
        return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge suspicious: This mod has a problem:</b> ";
    }

    @Override
    public String lithostichctov() {
        return "<b style='color:#" + config.obtenerColorError() + "'>CTOV requires lithostitched, you can install it from here <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#" + config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
    }

    @Override
    public String necesitasSodiumParaIris() {
        return "<b style='color:#" + config.obtenerColorError() + "'>To use Iris Shaders or Oculus, you need Sodium or a copy for another loader (Rubidium, Embedium, Bedium)</b>";
    }

    @Override
    public String kubeJSResourcePack(String mod_nombre) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Problem with KubeJS extension </b>" + mod_nombre;
    }
}
