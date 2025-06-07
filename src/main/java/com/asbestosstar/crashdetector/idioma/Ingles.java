package com.asbestosstar.crashdetector.idioma;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;

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
public String problema_con_graficas_ati() {
    return "<span style='color:#" + config.obtenerColorError() + "'>Updating your ATI/AMD drivers might help. Read this guide to fix it: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Driver Update Guide</a> https://www.amd.com/en/support/download/drivers.html Download </span>";
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
    public String config_spongemixin_problematico() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Potentially Problematic SpongeMixin Config:</b> ";
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
    
    
    @Override
public String problema_con_graficas_nvidia_windows_viejo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "Issues detected with NVIDIA drivers on versions prior to Windows 11."
            + "</span><br/><br/>"
            + "To ensure that Minecraft (and the current JVM) uses the dedicated NVIDIA GPU, follow these steps:<br/><br/>"
            + "1. <strong>Identify the Java executable:</strong><br/>"
            + "   - This program is using the following Java executable: "
            + obtenerRutaJava() + "<br/>"
            + "   - If you don't see a specific path, you can find the Java executable by searching for 'java.exe' on your system.<br/><br/>"
            + "2. <strong>Open the NVIDIA Control Panel:</strong><br/>"
            + "   - Right-click on the desktop and select 'NVIDIA Control Panel'.<br/><br/>"
            + "3. <strong>Configure the preferred GPU:</strong><br/>"
            + "   - In the NVIDIA Control Panel, go to 'Manage 3D settings'.<br/>"
            + "   - Select the 'Program Settings' option.<br/>"
            + "   - Click 'Add' and locate the previously identified Java executable (e.g., 'java.exe').<br/>"
            + "   - Ensure it is set to use the 'High-performance NVIDIA processor'.<br/><br/>"
            + "4. <strong>Save the changes:</strong><br/>"
            + "   - Apply the changes and close the NVIDIA Control Panel.<br/><br/>"
            + "5. <strong>Restart Minecraft:</strong><br/>"
            + "   - Restart Minecraft for the changes to take effect.<br/><br/>"
            + "If you're using Windows Server 2022 or Windows 10, these steps are valid as long as you have the latest NVIDIA drivers installed.<br/><br/>"
            + "Note: If you can't find the NVIDIA Control Panel, make sure the NVIDIA drivers are properly installed.";
}

@Override
public String problema_con_graficas_nvidia_windows_nuevo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "Issues detected with NVIDIA drivers on Windows 11/Server 2025 or later."
            + "</span><br/><br/>"
            + "To ensure that Minecraft (and the current JVM) uses the dedicated NVIDIA GPU, follow these steps:<br/><br/>"
            + "1. <strong>Identify the Java executable:</strong><br/>"
            + "   - This program is using the following Java executable: "
            + obtenerRutaJava() + "<br/>"
            + "   - If you don't see a specific path, you can find the Java executable by searching for 'java.exe' on your system.<br/><br/>"
            + "2. <strong>Open the Settings app:</strong><br/>"
            + "   - Press <code>Win + I</code> to open the Settings app.<br/>"
            + "   - Navigate to <strong>System > Display > Graphics</strong>.<br/><br/>"
            + "3. <strong>Configure the preferred GPU:</strong><br/>"
            + "   - In the 'Graphics' section, click on 'Default graphics settings'.<br/>"
            + "   - Select 'Desktop apps' and then click 'Browse'.<br/>"
            + "   - Locate and select the previously identified Java executable (e.g., 'java.exe').<br/>"
            + "   - Once added, select the Java application in the list and configure it to use 'High performance (NVIDIA)'.<br/><br/>"
            + "4. <strong>Save the changes:</strong><br/>"
            + "   - Apply the changes and close the Settings app.<br/><br/>"
            + "5. <strong>Restart Minecraft:</strong><br/>"
            + "   - Restart Minecraft for the changes to take effect.<br/><br/>"
            + "If you're using Windows 11 or Windows Server 2025+, these steps are valid as long as you have the latest NVIDIA drivers installed.<br/><br/>"
            + "Note: If you can't find the graphics settings option, make sure the NVIDIA drivers are properly installed.";
}


@Override
public String segundo60Tick() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Your Server or World has ticks longer than 60 seconds. This may be due to mods slowing down the server or hardware being too weak.</b>";
}

@Override
public String noTieneMemoria() {
    return "<b style='color:#" + config.obtenerColorError() + "'>You don't have enough RAM/Memory. You need to allocate more.</b>";
}



@Override
public String theseus() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>If you're using Theseus/ModrinthApp, we can't help much because Theseus doesn't have a Launcher console. Theseus also has more issues, including outdated Mod Loader versions, spyware, bad logs, and more. The Modrinth company isn't honest either. They make false accusations that mod developers use bots to inflate their downloads and have changed their monetisation claims multiple times.</b>";
}

@Override
public String noTieneConsolaDeLauncherCursedForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>You don't have launcher_log.txt. We need this file to search for errors. This is due to the \"Skip Launcher Startup\" option. Disable it.</b>";
}


@Override
public String faltar_de_clases_advertencia() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Warning: Missing classes detected:</b>";
}

@Override
public String noResultos() {
    return "<b style='color:#" + config.obtenerColorError() + "'>No Results</b>";
}

@Override
public String ubicacionesDeLogs() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>Log Locations:</b>";
}

@Override
public String infoDeVerificaciones() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>Here are the results of your checks. Fixing the upper parts of the logs is the first priority. Do it slowly.</b>";
}


@Override
public String versionDeJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Please use Java 17 for 1.17-1.20.4 and Java 21 for Anything newer, Java 8 for anything older. [Guide](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). If you still have issues it could be because some mod has too new or old of files.</b>";
}


@Override
public String java22() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java 22 and above do not work on Minecraft Versions bellow 1.20.5 for most modloaders due to ASM being outdated.</b>"+versionDeJava();
}

@Override
public String javaObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java is Obsolete </b>"+versionDeJava();
}

@Override
public String jpms_modules_faltas(String mod_necesitas, String submod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>You need JPMS Module " + mod_necesitas + " from " + submod + "</b>";
}

@Override
public String null_pointer_error(String metodo, String objeto) {
	// TODO Auto-generated method stub
	    return "<b style='color:#" + config.obtenerColorError() + "'>" +"Cannot invoke " + metodo + " because " + objeto + " is null"+"</b>";
}

@Override
public String analisisAvanzado() {
    return "Advanced Analysis";
}


@Override
public String seleccionarCarpeta() {
    return "Select Folder";
}

@Override
public String cadenaBusqueda() {
    return "Search String";
}

@Override
public String usarRegex() {
    return "Use Regex";
}

@Override
public String ignorarMayusculas() {
    return "Ignore Case";
}

@Override
public String buscar() {
    return "Search";
}

@Override
public String busquedaEnProgreso() {
    return "Search in Progress";
}

@Override
public String noSeEncontraronResultados() {
    return "No Results Found";
}

@Override
public String errorBusqueda() {
    return "Search Error";
}

@Override
public String noRegistroDeLauncher() {
    return "No launcher logs found! This may complicate the investigation.\n"
            + "                \n"
            + "                To obtain the correct logs:\n"
            + "                - MultiMC/PolyMC/PrismLauncher/: NOTE: Automatically detected logs are NOT correct.\n"
            + "                  1. Open the instance interface\n"
            + "                  2. Go to the \"Minecraft Log\" section\n"
            + "                  3. Right-click and copy the content\n"
            + "                - CurseForgeApp:\n"
            + "                  1. Restart the game WITHOUT skipping the launcher\n"
            + "                  \n";
}

@Override
public String omitirYCerrar() {
    return "Skip and Close";
}

@Override
public String guardarYCerrar() {
    return "Save and Close";
}

@Override
public String pegaLosRegistrosAqui() {
    return "Paste the logs here";
}

@Override
public String archivo() {
    return "File";
}

@Override
public String incluir() {
    return "Include";
}

@Override
public String abrir() {
    return "Open";
}

@Override
public String endpointDeInforme() {
    return "Report Endpoint";
}

@Override
public String sitoDeLogging() {
    return "Logging Site:";
}

@Override
public String apiDeLogging() {
    return "Logging API:";
}

@Override
public String anonimizarRegistros() {
    return "Anonymise logs (Beta)";
}

@Override
public String botonDeCompartirInforme() {
    return "Share Report and all selected logs";
}

@Override
public String arco() {
    return "This dialogue allows you to share logs using the SecureLogger API "
            + "at securelogger.net. When pressing the share report button your report is sent to the "
            + "are uploaded to the selected report endpoint (default asbestosstar.egoism.jp) (Can be changed at the bottom). You can share all selected logs "
            + "along with the report. If you don't want to upload, don't use this dialogue! We do not process your report at the official endpoint (https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb); we only remove disallowed links. The code is here: https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb. This is solely used to display information about your crash and the link to the logs. However, it is possible to use a custom endpoint that might not have the same methods. You are using the report site " 
            + Config.obtenerInstancia().obtenerSitoDeInformes() + " and the log site " 
            + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". You can also share individual logs without a report by pressing the share buttons next to the individual log names, the logs will go to the selected log site. CrashDetector has default log anonymisation, which attempts to remove usernames, UUIDs, access tokens, session IDs, IP addresses, and other data. However, it is not perfect. Nevertheless, the modpack author can disable it. It can be enabled or disabled with the checkbox at the bottom of this screen. You are your own data controller, you make the decision of where you upload your data, logging sites are owned by third parties whos ownership is often hidden for privacy, you take full responsibility for managing your data and the risks involved with it, the CrashDetector Share Dialogue is merly an interface allowing you to manage that, it is important that you are aware of GDPR and ARCO.";
}

@Override
public String enlaceDelReporte() {
    return "Report Link:";
}


@Override
public String guardarConfigDeCompartir() {
    return "Save Sharing Config";
}


@Override
public String registroDemasiadoGrande() {
    return "The log is too large for this logging site. Please choose another one and try again.";
}

@Override
public String errorConPublicarRegistro(String error) {
    return "Error publishing log " + error;
}

@Override
public String apiDeRegistroNoExiste() {
    return "Logging API does not exist. Please change the logging API in the settings.";
}

@Override
public String errorSSL() {
    return "You have an SSL Error. This is common with older versions of Java, "
            + "including Java 8 versions in the default Minecraft Launcher "
            + "and versions from sun.com and java.com. This affects many aspects, "
            + "such as the MinecraftForge installer JAR files, the function to share "
            + "CrashDetector reports when using the default endpoint, some mods that require internet, "
            + "and some logging sites. If this happens to you while trying to share a report, "
            + "simply attach a screenshot and select a logging site compatible "
            + "with older Java 8 versions.";
}

@Override
public String errorJavaFMLVersion(String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Incompatible JavaFML: Requires version " + requerido 
         + ", detected " + encontrado + "</b>";
}

@Override
public String errorJavaFML_MCForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "Attention! JavaFML requires a specific version of Minecraft Forge</b>";
}

@Override
public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "The JAR file '" + archivoJar + "' requires the language provider '" + proveedor + "' version '"
         + requerido + "' or higher, but only version '" + encontrado + "' was found.</b>";
}

//@Override
//public String advertenciaMalwareFalso() {
//    return "<b style='color:#" + config.obtenerColorError() + "'>"
//         + "ALERT! Crash Assistant is a fake malware detector. It intentionally blocks the game from launching disregarding your freedom to keep playing with the mods it targets. "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>View MalwareMod.java code  </a>   "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>View JarInJarHelper.java code  </a>. Only this mod is on their list at this time and they are only really going after the default logging site which can be changed by the user and that only ever does anything if you explicitly choose to use the built in log sharing feature. CrashAssistant does NOT do any checks to even check which logging site is the one being set and do not explain how to change it (there is a dropdown on the bottom of the share dialogue) and no matter what site you have CrashAssistant will block the game from launching. In their message they say to do your own research, DO IT, look into the code of CrashDetector and Crash Assistant and understand what they do, do NOT rely on appeal to authority.</b>";
//}

@Override
public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "The mod '" + idMod + "' has failed because the required class was not found: '"
         + claseNoEncontrada + "'. Make sure all dependencies are installed correctly.</b>";
}

@Override
public String waterMediaTL() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Watermedia has blocked playing with TLauncher.</b>";
}


@Override
public String optifineObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "You are using a version of Optifine for an obsolete version of Minecraft. You need to use the version of Optifine for the version of Minecraft you are using.</b>";
}

@Override
public String servicioMLNoPudoCargar(String servicio) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Failed to load ModLauncher Service: </b>" + servicio + ".";
}

@Override
public String errorConJSONDeRegistro(String archivoJar, String recurso) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Error parsing the JSON file '" + recurso + "' from the JAR file '" + archivoJar
         + "'. There are issues with the registration.</b>";
}

@Override
public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
    return "<span style='color:#" + config.obtenerColorError() + "'>" 
        + "Error: The mod '" + modId + "' requires version '" + requerido 
        + "' or higher of '" + dependencia + "', but found '" + actual + "'."
        + "</span>";
}

@Override
public String gpu_no_compatible() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Your GPU does not support the required OpenGL version for this game version. Update your drivers or change your graphics card.</b>";
}

@Override
public String recomendacionMemoria() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "Increase the memory allocated to the game or reduce the usage of mods/plugins</b>";
}

@Override
public String error32BitMemoria() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "32-bit JVM detected: Cannot use more than 4GB of RAM. "
         + "Install a 64-bit JVM to utilize all your available memory</b>";
}

@Override
public String permGenError() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Critical PermGen memory error. Increase the permanent memory space or reduce the class load</b>";
}

public String errorCompatibilidadJava8() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Compatibility error between Java 8 and modern versions</b>";
}

public String errorJava9NoSoportado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 9+ not supported - Use Java 8 for older Forge versions</b>";
}

public String errorJava8Requerido() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 8 required (version 52.0). Update or configure correctly</b>";
}


@Override
public String errorDeBloqueTeselado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Critical compatibility error: Blocks not supported in this version. "
         + "Ensure that mod and server versions are compatible</b>";
}

@Override
public String errorMonitorLWJGL() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Monitor configuration error: "
         + "Failed to set screen mode. "
         + "Check:</b>"
         + "<br>- Multi-monitor configuration"
         + "<br>- Updated graphics card drivers"
         + "<br>- System-supported resolution";
}

@Override
public String errorOpcionesGCJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java options error: "
         + "Conflicting garbage collector options. "
         + "Ensure you don't combine multiple GC algorithms in JVM parameters</b>";
}

@Override
public String errorConfigMCForge() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Critical Forge configuration error: "
         + "Corrupted or incomplete configuration file. "
         + "Delete the 'config' folder and restart the game</b>";
}

@Override
public String problema_con_graficas_intel() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Intel HD Graphics driver error detected. Solutions:</b>"
         + "<br>1. Update Intel drivers from <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a> (minimum version 15.xx.xx.xx)"
         + "<br>2. In Minecraft: Options → Video → Enable 'Enable VBOs' and 'VSync'"
         + "<br>3. Allocate 1GB-2GB of RAM to the game in the launcher"
         + "<br>4. Temporarily disable antivirus/firewall during the update";
}

public String nombre_de_faltar_de_clases_advertencia() {
    return "Warning: Missing classes detected";
}

public String nombre_de_bloque_teselado() {
    return "Block rendering error";
}

public String nombre_de_contento_de_stacktrace() {
    return "Stack trace analysis";
}

public String nombre_de_cursed_consola() {
    return "Incomplete CurseForge console";
}

public String nombre_de_early_window() {
    return "Early window error (FMLEarlyWindow)";
}

public String nombre_de_drivers() {
    return "Video driver issues";
}

public String nombre_de_error_de_config_mcforge() {
    return "Corrupted MCForge configuration";
}

public String nombre_de_error_de_monitor_lwjgl() {
    return "Display mode failure (LWJGL)";
}

public String nombre_de_fabricmc_runtime_error_provided_by() {
    return "FabricMC initialisation error";
}

public String nombre_de_falta_module_jmps() {
    return "Missing JPMS modules";
}

public String nombre_de_faltar_de_clases() {
    return "Critical missing classes";
}

public String nombre_de_faltas_dependencias_de_modlauncher() {
    return "Missing ModLauncher dependencies";
}

public String nombre_de_java_versiones() {
    return "Incompatible Java versions";
}

public String nombre_de_faltar_de_kubejs_resourcepack() {
    return "KubeJS resource error";
}

public String nombre_de_lenguaje_proveedor_check() {
    return "Incompatible language provider";
}

public String nombre_de_faltar_de_liyhostictchctov() {
    return "Litchhost error";
}

public String nombre_de_malware_falso_crash_assistant() {
    return "False malware detection";
}

public String nombre_de_mcforge_mod_sespechoso() {
    return "Suspicious mod detected";
}

public String nombre_de_mods_duplicados_modlauncher() {
    return "Duplicated mods in ModLauncher";
}

public String nombre_de_modules_duplicados_jmps() {
    return "JPMS module conflicts";
}

public String nombre_de_necesitas_sodium() {
    return "Sodium required for Iris";
}

public String nombre_de_no_puede_analizar_json_de_registro() {
    return "Failed to parse JSON registry";
}

public String nombre_de_no_tiene_memoria() {
    return "Insufficient memory";
}

public String nombre_de_null_pointer() {
    return "Null pointer error (NullPointerException)";
}

public String nombre_de_opciones_java_gc_invalidas() {
    return "Invalid Java GC options";
}

public String nombre_de_optifine_obsoleta() {
    return "Outdated/Incompatible OptiFine";
}

public String nombre_de_60_segundo_trick() {
    return "Critical server tick (60s)";
}

public String nombre_de_servicio_de_modlauncher_no_funciona() {
    return "Failed ModLauncher services";
}

public String nombre_de_spongemixin_configs_problematicos() {
    return "Problematic SpongeMixing configurations";
}

public String nombre_de_theseus() {
    return "Theseus incompatible";
}

public String nombre_de_watermedia_tl() {
    return "TLauncher unsupported by WATERMeDIA";
}

@Override
public String auditorias_transformer() {
    return "Transformer Audits";
}

@Override
public String auditorias_transformer_detectadas() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "These are the results of the Transformer Audit contents in the Vanilla Launcher. It is generally less precise than the StackTrace analyser, but the Vanilla Launcher does not always have content for {}</b>";
}

@Override
public String descripcionEscanerMCreator() {
	// TODO Auto-generated method stub
	return "This looks through your mods for MCreator mods. While most MCreator mods are just fine and there are many great MCreator mods, sometimes they have issues and they have a bad reputation. This helps identify them. Note that even the very high rated ones may not actually be mcreator, for example they may have integration with MCreator.";
}

@Override
public String escanear() {
	// TODO Auto-generated method stub
	return "Scan";
}

@Override
public String cargando() {
	// TODO Auto-generated method stub
	return "Loading";
}


}
