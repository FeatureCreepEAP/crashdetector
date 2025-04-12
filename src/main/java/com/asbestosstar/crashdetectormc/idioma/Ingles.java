package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Idioma;

public class Ingles implements Idioma {

    @Override
    public String carpeta_de_mods_no_valido() {
        return "Not a valid mods folder";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "I don't know where the CrashDetector JAR file is";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "Looking for PID: " + String.valueOf(pid);
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "(PID: " + String.valueOf(pid) + ") is dead!";
    }

    @Override
    public String no_tenemos_jvm() {
        return "We don't have JVM";
    }
    
    @Override
    public String probelma_con_graficas_ati() {
		return "Updating your drivers might help. Please bear in mind that checking for updates the usual ways won't find any updates when drivers are in a broken state, so it's important you follow the linked guide. Important: If you have Nvidia graphics, also make sure you've set anything Minecraft-related (such as Java and launchers) to prioritise high performance graphics in both the Windows settings and Nvidia control panel. Read this guide to fix them: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }
    
    @Override
    public String probelma_con_graficas_nouveau() {
		return 	"Some older versions sometimes have a few issues with some Nouveau Graphics on early loading screen";
    }
    
    @Override
    public String probelma_con_graficas_general() {
		return "You have an issue with your Graphics Drivers. If you have an AMD/ATI GPU or APU update your AMD graphics drivers. If you have an NVIDIA graphics card make sure to mark the game and all instances of javaw.exe to use the dedicated graphics card. Read this guide: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }
    
    @Override
    public String fmlEarlyWindow() {
    	return "Your FML Early Window is failing. "
				+ "To Change this go to (.)minecraft/config/fml.toml"
				+ "Edit earlyWindowProvider to be earlyWindowProvider=\"none\""
				+ "If you are on an M1 Mac you should also make sure you are using an ARM version of Java not an Intel x64 one."
				+ "This is also a common issue if you have outdated Drivers. Please check this guide if on windows and turning of this does not work. https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }
    
    @Override
    public String no_tienes_las_dependencias_necesitas() {
    	return "You do not have all the dependencies you need:";
    }
    
    @Override
    public String linea_de_dependencia(String linea) {
    	return linea;//La linea es en ingles
    }
    
@Override
    public String local_headless(String archivo) {
        return "Your CrashDetector report is here: " + archivo;
    }
    
     @Override
    // English GUI text
    public String texto_de_gui() {
        return "This is the CrashDetector GUI. If the game closes without issues, ignore it.";
    }
    
        @Override
    // English version
    public String texto_de_buton_local_enlance() {
        return "View Report";
    }

    @Override
    // Local button description
    public String texto_debajo_de_buton_local_enlance() {
        return "View a local report in your browser";
    }

    @Override
    // Share button text
    public String texto_de_buton_compartir_enlance() {
        return "Share Report";
    }

    @Override
    // Share button description
    public String texto_debajo_de_buton_compartir_enlance() {
        return "Share report, logs will be uploaded to securelogger.net and stored on another site for 3 days";
    }
    
    
    @Override
public String problematico_jar() {
    return "<b>Found potentially problematic JAR files (Prioritise FATAL then Higher lvl then lower ln):</b>";
}

@Override
public String nivel() {
    return "<b>lvl: </b>";
}

@Override
public String possibladad_fatal() {
    return "<b>Possibly Fatal:</b> ";
}

@Override
public String modids_problematicos() {
    return "<b>Found potentially problematic modids (Prioritise FATAL then Lower lvl then lower ln):</b>";
}

@Override
public String packages_problematicos() {
    return "<b>Found potentially problematic packages (Prioritise FATAL then Lower lvl then lower ln):</b>";
}

@Override
public String faltar_de_clases_fatales() {
    return "<b>Found potentially fatal missing classes:</b>";
}

@Override
public String corchetes_ondulados() {
    return "<b>Found contents in {} (Top is most important, only top 20 shown):</b>";
}

@Override
public String config_spongemixin_problematico(String archivo_json) {
    return "<b>Potentially Problematic SpongeMixin Config:</b> " + archivo_json;
}


@Override
public String module_resolution_exception(String modules, String paquete) {
    return "You have Mods with duplicated Packages: " + modules + " duplicated package " + paquete.replace(".", "/") + ". You can fix this by removing the package (folder) from the jar file. You can open the jar file using archive software like file-roller, WinRAR or 7-Zip, or change the file extension from .jar to .zip, delete the folder, and then change it back to a .jar file.";
}

@Override
public String modlauncher_mods_duplicado(String linea) {
    return "<b>You have duplicate Mods</b> " + linea.replace("from mod files", "from mod files");
}

@Override
public String mcforge_mod_suspechoso() {
    return "<b>MinecraftForge suspicious: This mod has a problem:</b> ";
}

@Override
public String lithostichctov() {
    return "<b>CTOV requires lithostitched, you can install it from here https://www.curseforge.com/minecraft/mc-mods/lithostitched</b>";
}




}
