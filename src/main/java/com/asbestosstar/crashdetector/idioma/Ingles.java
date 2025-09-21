package com.asbestosstar.crashdetector.idioma;

import java.util.List;

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
    public String problema_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>Some older versions sometimes have a few issues with some Nouveau Graphics on early loading screen</span>";
    }

    @Override
    public String problema_con_graficas_general() {
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
    public String no_tienes_las_dependencias_necesarias() {
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
    public String texto_de_boton_local_enlace() {
        return "<span style='color:#" + config.obtenerColorBoton() + "'>View Report</span>";
    }

    @Override
    public String texto_debajo_de_buton_local_enlace() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>View a local report in your browser.</span>";
    }

    @Override
    public String texto_de_buton_compartir_enlace() {
        return "Share Report"; // Button text, no color span needed as per Spanish version (or you can add if you want)
    }

    @Override
    public String texto_debajo_de_buton_compartir_enlace() {
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
    public String posibilidad_fatal() {
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
    public String falta_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>You have fatal classes (FATAL) — this is very serious. Common causes include bad CoreMods or fatal dependency errors. You can use QuickFix to scan for mods with fatal classes. Missing fatal classes detected:</b>";
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
    public String module_resolution_exception() {
        return "<span style='color:#" + config.obtenerColorError() + "'>You have mods with duplicated packages. You can fix this by removing the duplicate package (folder) from the JAR file. You can open the JAR using an archive program like WinRAR or 7-Zip, or you can change the file extension from .jar to .zip, delete the folder, and then rename it back to a .jar file.</span>";
    }

    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>You have duplicate Mods</b> " + linea.replace("from mod files", "from mod files");
    }

    @Override
    public String mcforge_mod_sospechoso() {
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
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Warning: Missing classes detected (Warning level). Usually not critical, but can still cause issues — different from fatal class errors. Common causes include bad CoreMods or missing dependencies. You can use QuickFix to scan for mods with missing classes. Missing classes detected:</b>";
}

@Override
public String noResultados() {
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

public String nombre_de_contenido_de_stacktrace() {
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

@Override
public String codigo() {
	// TODO Auto-generated method stub
	return "en";
}

@Override
public String inicioApp() {
    return "Game/App Start";
}

@Override
public String ajustesCrashDetector() {
    return "CrashDetector Settings";
}

@Override
public String confidencialidad() {
    return "Privacy";
}

@Override
public String tooltip() {
    return "Tooltip";
}

@Override
public String config() {
    return "Configuration";
}

@Override
public String abrirCarpeta() {
    return "Open Folder";
}

@Override
public String actualizar() {
    return "Update";
}

@Override
public String anadirRegistro() {
    return "Add Log";
}

@Override
public String usarIdiomaDelSistema() {
    return "Use system language";
}

@Override
public String volver() {
    return "Back";
}

@Override
public String colorFondo() {
    return "Background colour (#RRGGBB):";
}

@Override
public String colorTexto() {
    return "Text colour (#RRGGBB):";
}

@Override
public String colorBoton() {
    return "Button colour (#RRGGBB):";
}

@Override
public String colorCajaTexto() {
    return "Text box colour (#RRGGBB):";
}

@Override
public String colorEnlace() {
    return "Link colour (#RRGGBB):";
}

@Override
public String colorTitulosConsolas() {
    return "Console title colour (#RRGGBB):";
}

@Override
public String colorError() {
    return "Error colour (#RRGGBB):";
}

@Override
public String colorAdvertencia() {
    return "Warning colour (#RRGGBB):";
}

@Override
public String colorInfo() {
    return "Info colour (#RRGGBB):";
}

@Override
public String colorTitulo() {
    return "Title colour (#RRGGBB):";
}

@Override
public String colorEnlaceTexto() {
    return "Link text colour (#RRGGBB):";
}

@Override
public String transformacionDeMinecraftCodigo0() {
    return "Only open CrashDetector on failure";
}



@Override
public String activar_parche() {
    return "Activate Patch";
}

@Override
public String noHaySolucionDisponible() {
    return "No Solution Available";
}

@Override
public String error() {
    return "error";
}

@Override
public String error_al_eliminar_jar() {
    return "Error deleting Jar";
}

@Override
public String jar_eliminado_exitosamente() {
    return "Jar deleted successfully";
}

@Override
public String exito() {
    return "success";
}

@Override
public String eliminar() {
    return "delete";
}

@Override
public String error_al_eliminar_paquete() {
    return "Error deleting package";
}

@Override
public String paquete_eliminado_exitosamente() {
    return "Package deleted successfully";
}

@Override
public String eliminar_paquete() {
    return "Delete package";
}

@Override
public String no_se_encontraron_mods_con_paquete() {
    return "No mods with package found";
}

@Override
public String no_se_puede_eliminar_paquete() {
    return "Cannot delete package";
}

@Override
public String eliminar_jar() {
    return "Delete jar";
}

@Override
public String no_se_encontraron_mods_duplicados() {
    return "No duplicate mods found";
}

@Override
public String archivo_no_encontrado() {
    return "File Not Found";
}

@Override
public String directorio_eliminado() {
    return "Directory Deleted";
}

@Override
public String error_al_eliminar_jar_anidado() {
    return "Error deleting nested Jar";
}

@Override
public String archivo_interno_no_encontrado() {
    return "Internal file not found";
}

@Override
public String archivo_eliminado() {
    return "file deleted";
}

@Override
public String error_al_eliminar_archivo() {
    return "error deleting file";
}

@Override
public String archivo_externo_no_valido() {
    return "invalid external file";
}

@Override
public String elemento_mod_eliminado() {
    return "Mod element deleted";
}

@Override
public String error_al_reemplazar_jar_externo() {
    return "Error replacing external Jar";
}

@Override
public String error_al_eliminar_elemento_mod() {
    return "error deleting mod element";
}

@Override
public String error_al_eliminar_directorio() {
    return "error deleting directory";
}

@Override
public String formato_invalido_para_jar_anidado() {
    return "invalid format for nested Jar";
}

@Override
public String jar_anidado_eliminado() {
    return "nested Jar deleted";
}

@Override
public String error_al_limpiar_temporales() {
    return "error clearing temporary files";
}

@Override
public String mensaje_de_trace_fatal_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Fatal Trace Message Last (Not translated):</b> ";
}

@Override
public String mensaje_de_trace_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Trace Message Last (Not translated):</b> ";
}

@Override
public String solucionParaAdvertenciaFaltasClases() {
    return "You can search the WaifuNeoForge database to find mods. Select the game version, mod loader, and class. Use the closest matching combination. You can search once per minute.";
}

@Override
public String solucionFaltasClases() {
    return "You can search the WaifuNeoForge database to find mods. Select the game version, mod loader, and class. Use the closest matching combination. You can search once per minute.";
}

@Override
public String solucionParaJavaInstallar() {
    return "Both launchers have the correct Java versions but not all of them; you can install the correct Java version from the package manager on your system or using the buttons.";
}

@Override
public String error_animacion_no_encontrada() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Mod with Missing Animation: " + "</b>";
}

@Override
public String nombre_de_error_animacion_minecraft() {
    return "NoSuchElementException (No Element Exception) Missing Animation";
}

@Override
public String no_se_encontraron_mods_para_eliminar() {
    return "No mods were found to delete";
}

@Override
public String opcionesGCInvalidas() {
    return "Replace conflicting GC options with -XX:+UseG1GC";
}

@Override
public String aumentarMemoriaHeap() {
    return "Increase heap memory size using the -Xmx option.";
}

@Override
public String aumentarMemoriaPermgen() {
    return "Increase permgen memory size using the -XX:MaxPermSize option.";
}

@Override
public String utilizarJVM64Bits() {
    return "Use a 64-bit JVM to increase available memory.";
}

@Override
public String optimizarCodigo() {
    return "Optimize code to reduce memory usage and improve performance.";
}

@Override
public String utilizarRecolectorBasuraEficiente() {
    return "Use an efficient garbage collector to reduce application pauses.";
}

@Override
public String modulos() {
    return "Modules";
}

@Override
public String paquete() {
    return "Package";
}

@Override
public String solucionRegistrosMalMapeados() {
    return "There are missing IDs. Common causes are missing mods or missing item data. Common data folders are datafiedcontents/ and kubejs/ or other mod folders.";
}

@Override
public String nombre_de_registros_mal_mapeados() {
    return "mismatched records";
}

/**
 * Returns the error message for shutdown caused by AuthMe.
 */
public String mensajeCierreAuthMe() {
    return "<b style='color:#" + config.obtenerColorError() + "'>The 'AuthMe' plugin failed to load and has shut down the server.</b> ";
}

/**
 * Returns the problem name to display in the interface.
 */
public String nombreProblemaCierreAuthMe() {
    return "Shutdown issue caused by AuthMe";
}

/**
 * Returns the solution for server shutdown due to AuthMe.
 */
public String solucionCierreAuthMe() {
    return "The 'stopServer' rule changed to 'true'.";
}

/**
 * Returns the solution to configure the AuthMe plugin.
 */
public String solucionConfigurarPluginAuthMe() {
    return "Configure the AuthMe plugin (plugins/AuthMe/config.yml)";
}

/**
 * Returns the solution to install a different version of the AuthMe plugin.
 */
public String solucionInstalarVersionDiferenteAuthMe() {
    return "Install a different version of the 'AuthMe' plugin";
}

/**
 * Returns the solution to remove the AuthMe plugin.
 */
public String solucionEliminarPluginAuthMe() {
    return "Remove the 'AuthMe' plugin";
}

/**
 * Returns the error message for corrupted worlds due to Multiverse.
 */
@Override
public String mensajeProblemaCargaMultiverso(String nombreMundo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The world '" + nombreMundo + "' could not be loaded because it contains errors and is likely corrupted.</b>";
}

/**
 * Returns the problem name to display in the interface.
 */
@Override
public String nombreProblemaCargaMultiverso() {
    return "Multiverse world loading issue";
}

/**
 * Returns the solution to repair the world.
 */
@Override
public String solucionRepararMundo(String nombreMundo) {
    return "Repair the world '" + nombreMundo + "', for example using Minecraft Region Fixer or MCEdit.";
}

/**
 * Returns the solution to delete the world folder.
 */
@Override
public String solucionEliminarCarpetaMundo(String nombreMundo) {
    return "Delete the world folder '" + nombreMundo + "'.";
}


@Override
public String mensajeConfiguracionPermissionsEx() {
    return "<b style='color:#" + config.obtenerColorError() + "'>The configuration for the 'PermissionsEx' extension is invalid.</b> ";
}

@Override
public String nombreProblemaConfiguracionPermissionsEx() {
    return "PermissionsEx configuration issue";
}

@Override
public String solucionConfigurarPermissionsEx() {
    return "Configure the PermissionsEx plugin (plugins/PermissionsEx/permissions.yml)";
}

@Override
public String solucionEliminarPluginPermissionsEx() {
    return "Remove the 'PermissionsEx' plugin";
}

@Override
public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
    return "<b style='color:#" + config.obtenerColorError() + "'>There are multiple plugin files for the plugin named '" + nombrePlugin + "': '" + primerPath + "' and '" + segundoPath + "'.</b> ";
}

@Override
public String nombreProblemaNombrePluginAmbiguo() {
    return "Ambiguous plugin name issue";
}

@Override
public String solucionEliminarPlugin(String nombrePlugin) {
    return "Remove the plugin '" + nombrePlugin + "'";
}

@Override
public String mensajeCargaChunk() {
    return "<b style='color:#" + config.obtenerColorError() + "'>An exception occurred while the world was loading chunks.</b> ";
}

@Override
public String nombreProblemaCargaChunk() {
    return "Chunk loading exception";
}

@Override
public String solucionEliminarChunk() {
    return "Remove the problematic chunk from the world, for example using MCEdit or by deleting the region file.";
}


@Override
public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin + "' cannot execute the command '/" + comando + "'.</b> ";
}

@Override
public String nombreProblemaExcepcionComandoPlugin() {
    return "Exception while executing plugin command";
}

@Override
public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
    return "Install a different version of the plugin '" + nombrePlugin + "'";
}

@Override
public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin + 
           "' requires the following dependency: '" + dependencia + "'.</b> ";
}

@Override
public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
    StringBuilder deps = new StringBuilder();
    for (int i = 0; i < dependencias.size(); i++) {
        if (i > 0) deps.append(", ");
        deps.append("'").append(dependencias.get(i)).append("'");
    }
    return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin + 
           "' is missing the following required dependencies: " + deps.toString() + ".</b> ";
}

@Override
public String nombreProblemaDependenciaPlugin() {
    return "Missing plugin dependency";
}

@Override
public String solucionInstalarPlugin(String nombrePlugin) {
    return "Install the plugin '" + nombrePlugin + "'";
}

@Override
public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin + 
           "' requires API version '" + versionAPI + "' which is not compatible with the current server.</b> ";
}

@Override
public String nombreProblemaVersionAPIIncompatible() {
    return "Incompatible API Version";
}

@Override
public String solucionInstalarVersionServidor(String version) {
    return "Install version '" + version + "' of your server software.";
}

@Override
public String mensajeMundoDuplicado(String nombreMundo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The world '" + nombreMundo + 
           "' is a duplicate of another world and cannot be loaded.</b> ";
}

@Override
public String nombreProblemaMundoDuplicado() {
    return "Duplicate World";
}

@Override
public String solucionEliminarUID(String nombreMundo) {
    return "Delete the 'uid.dat' file in the world folder '" + nombreMundo + "'";
}

@Override
public String solucionEliminarMundo(String nombreMundo) {
    return "Delete the world folder '" + nombreMundo + "'";
}

@Override
public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
    String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
    return "<b style='color:#" + config.obtenerColorError() + "'>The block entity '" + nombre + 
           "' of type '" + tipo + "' at location " + coords + " is causing ticking errors.</b> ";
}

@Override
public String nombreProblemaTickingEntidadBloque() {
    return "Problematic Block Entity";
}

@Override
public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
    String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
    return "Remove the '" + nombre + "' entity at location " + coords + " using MCEdit or by editing the world directly.";
}

@Override
public String mensajeModDuplicadoFabric(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod + "' has multiple versions installed.</b> ";
}

@Override
public String nombreProblemaModDuplicadoFabric() {
    return "Duplicate Mod in Fabric";
}

@Override
public String solucionEliminarModDuplicado(String rutaMod) {
    return "Delete the duplicate mod file: " + rutaMod;
}

@Override
public String mensajeModIncompatible(String primerMod, String segundoMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The mods '" + primerMod + 
           "' and '" + segundoMod + "' are incompatible with each other.</b> ";
}

@Override
public String nombreProblemaModIncompatibleFabric() {
    return "Incompatible Mod in Fabric";
}

@Override
public String solucionEliminarMod(String nombreMod) {
    return "Remove the mod '" + nombreMod + "'";
}

@Override
public String mensajeModFatal(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod + "' has a critical error and cannot run.</b> ";
}

@Override
public String nombreProblemaModFatal() {
    return "Mod with Critical Error";
}

@Override
public String mensajeModDependenciaPlural(List<String> dependencias) {
    StringBuilder deps = new StringBuilder();
    for (int i = 0; i < dependencias.size(); i++) {
        if (i > 0) {
            deps.append(", ");
        }
        deps.append("'").append(dependencias.get(i)).append("'");
    }
    return "<b style='color:#" + config.obtenerColorError() + "'>The following mods are required but not installed: " + deps.toString() + ".</b>";
}

@Override
public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
    if (version == null || version.isEmpty()) {
        return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod + 
               "' requires the mod '" + dependencia + "' as a dependency.</b>";
    } else {
        return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod + 
               "' requires the mod '" + dependencia + "' version " + version + ".</b>";
    }
}

@Override
public String nombreProblemaDependenciaMod() {
    return "Missing mod dependency";
}

@Override
public String solucionInstalarMod(String nombreMod) {
    return "Install the mod '" + nombreMod + "'";
}

@Override
public String solucionInstalarModConVersion(String nombreMod, String version) {
    return "Install the mod '" + nombreMod + "' with version " + version;
}

@Override
public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin + 
           "' is not compatible with Folia's regional ticking.</b> ";
}

@Override
public String mensajePluginTickingRegionalPlural(List<String> plugins) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("The following plugins are not compatible with Folia regional ticking: ");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            sb.append(", ");
        }
        sb.append("'").append(plugins.get(i)).append("'");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

@Override
public String nombreProblemaPluginTickingRegional() {
    return "Plugin incompatible with Regional Ticking";
}

@Override
public String solucionInstalarSoftwareSinTickingRegional(String software) {
    return "Install a version without regional ticking, such as " + software;
}

@Override
public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod + 
           "' is missing from the datapack.</b>";
}

@Override
public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("The following mods are missing from the datapack: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" and ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

@Override
public String nombreProblemaModFaltanteEnDatapack() {
    return "Missing mod in datapack";
}

@Override
public String mensajeModExcepcionSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod + "' has caused an error.</b>";
}

@Override
public String mensajeModExcepcionPlural(List<String> mods) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("The following mods have caused errors: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" and ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

@Override
public String nombreProblemaModExcepcion() {
    return "Forge Mod with Exception";
}

@Override
public String solucionInstalarVersionDiferenteMod(String nombreMod) {
    return "Install a different version of the mod '" + nombreMod + "'";
}

@Override
public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod + 
           "' is not compatible with Minecraft version " + versionMC + ".</b>";
}

@Override
public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("The following mods are not compatible with their respective Minecraft versions: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            sb.append(", ");
        }
        sb.append("'").append(mods.get(i)).append("'");
        sb.append(" (Minecraft ").append(versionesMC.get(i)).append(")");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

@Override
public String nombreProblemaModIncompatibleConMinecraft() {
    return "Mod incompatible with Minecraft";
}

@Override
public String solucionInstalarVersionForge(String versionMC) {
    return "Install a Forge version compatible with Minecraft " + versionMC;
}

@Override
public String mensajeDependenciaModFaltante(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod + "' is missing and required to load the world or plugin.</b>";
}

@Override
public String nombreProblemaDependenciaModFaltante() {
    return "Missing mod";
}

@Override
public String mensajeWorldModFaltanteSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The world was saved with the mod '" + nombreMod + 
           "' which seems to be missing.</b>";
}

@Override
public String mensajeWorldModFaltantePlural(List<String> mods) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("The world was saved with the following mods which seem to be missing: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" and ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

@Override
public String nombreProblemaWorldModFaltante() {
    return "Missing mod in world";
}

@Override
public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The world was saved with mod '" + nombreMod + 
           "' version " + versionEsperada + ", but it is now running version " + versionActual + ".</b>";
}

@Override
public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas, List<String> versionesActuales) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("The following mods have version mismatches in the saved world: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" and ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
        sb.append(" (Saved: ").append(versionesEsperadas.get(i)).append(", Current: ").append(versionesActuales.get(i)).append(")");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

@Override
public String nombreProblemaVersionModMundo() {
    return "Mod version mismatch in saved world";
}

@Override
public String mensajeVersionDowngrade() {
    return "<b style='color:#" + config.obtenerColorError() + "'>You tried to load a world created with a newer version of Minecraft.</b>";
}

@Override
public String nombreProblemaVersionDowngrade() {
    return "Attempted to load world from newer version";
}

@Override
public String solucionVersionDowngrade() {
    return "Install a newer version of Minecraft.";
}

@Override
public String solucionGenerarNuevoMundo() {
    return "Generate a new world.";
}

@Override
public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin + 
           "' requires the following dependency: '" + dependencia + "'.</b>";
}

@Override
public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("The following plugins require dependencies that are not installed: ");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            sb.append(", ");
        }
        sb.append("'").append(plugins.get(i)).append("' (").append(dependencias.get(i)).append(")");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

@Override
public String nombreProblemaDependenciaPluginFaltante() {
    return "Missing plugin dependency";
}

@Override
public String mensajePluginIncompatibleSingular(String nombrePlugin) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin + "' is incompatible with the current server version.</b>";
}

@Override
public String mensajePluginIncompatiblePlural(List<String> plugins) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("The following plugins are incompatible with the current server version: ");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            if (i == plugins.size() - 1) {
                sb.append(" and ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(plugins.get(i)).append("'");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

@Override
public String nombreProblemaPluginIncompatible() {
    return "Plugin incompatible with PocketMine-MP";
}

@Override
public String mensajePluginEjecucionSingular(String nombrePlugin) {
    return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin + "' has caused an error during execution.</b>";
}

@Override
public String mensajePluginEjecucionPlural(List<String> plugins) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("The following plugins have caused errors during execution: ");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            if (i == plugins.size() - 1) {
                sb.append(" and ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(plugins.get(i)).append("'");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

@Override
public String nombreProblemaPluginEjecucion() {
    return "Plugin with runtime error";
}

@Override
public String nombreLegacyRandomSourceMultiHilos() {
    return "LegacyRandomSource Multi Threads";
}

@Override
public String mensajeLegacyRandomSourceMultiHilos() {
    return "<b style='color:#" + config.obtenerColorError() + "'>You have a problem with multiple threads accessing the LegacyRandomSource class. You can get more information using the 'Unsafe World Random Access Detector' or 'C2ME' mods.</b>";
}


@Override
public String desdeUltimoExito() {
    return "Since Last Success";
}

@Override
public String noHayCambios() {
    return "No Changes";
}

@Override
public String desdeUltimoIntento() {
    return "Since Last Attempt";
}

@Override
public String fallo() {
    return "Failed";
}

@Override
public String diferentesDeLasMods() {
    return "Different From Mods";
}

@Override
public String historialDeMods() {
    return "Mod History";
}

@Override
public String archivo0() {
    return "File0";
}

@Override
public String archivo1() {
    return "File1";
}

@Override
public String comparar() {
    return "Compare";
}

@Override
public String seleccionarDosArchivos() {
    return "Select Two Files";
}

@Override
public String archivoExito() {
    return "Success File";
}

@Override
public String archivoFalla() {
    return "Failure File";
}

@Override
public String errorComparandoArchivos() {
    return "Error Comparing Files";
}

@Override
public String comparando() {
    return "Comparing";
}

@Override
public String con() {
    return "With";
}


@Override
public String descripcionPanelHistoriaMods() {
    return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
        + "<p><b>Mod History Comparison Panel</b></p>"
        + "<p>This panel lets you compare two mod lists from different sessions. "
        + "Select one file from the left column and another from the right to see what changed between them.</p>"
        
        + "<h3>How to use it:</h3>"
        + "<ol>"
        + "<li><b>Select files:</b> Click the radio buttons next to the file names. "
        + "Files ending in <span style='color: #4CAF50; font-weight: bold;'>.success</span> mean successful sessions, "
        + "while those ending in <span style='color: #F44336; font-weight: bold;'>.failure</span> indicate crashes or errors.</li>"
        
        + "<li><b>Automatic comparison:</b> Press the 'Compare' button to analyse both lists and show added (+) or removed (-) mods.</li>"
        
        + "<li><b>View results:</b> The results are shown in colour-coded HTML format: "
        + "<ul>"
        + "<li><span style='color: green;'>+ Added mod</span></li>"
        + "<li><span style='color: red;'>- Removed mod</span></li>"
        + "</ul></li>"
        + "</ol>"
        
        + "<h3>Key Features:</h3>"
        + "<ul>"
        + "<li>Supports any combination of success/failure files.</li>"
        + "<li>Shows two-way differences for precise change tracking.</li>"
        + "<li>Includes scrolling for long mod lists.</li>"
        + "<li>Integrated explanatory images for better visual understanding.</li>"
        + "</ul>"
        
        + "<p>Made with <3️ to help track changes in your configurations.</p>"
        + "</body></html>";
}

@Override
public String tieneErrorIPV6() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
           + "You may be experiencing an issue related to IPv6. "
           + "There are two solutions: "
           + "1) Add the JVM argument <code>-Djava.net.preferIPv4Stack=true</code> to your launcher, or "
           + "2) Use the 'QuickFix' button in CrashDetector to apply a patch that enables this setting automatically."
           + "</b>";
}

@Override
public String parcheIPv4() {
    return "IPv4/6 Patch";
}

@Override
public String carpetaHMCL() {
    return "HMCL Folder (For HelloMineCraftLauncher only)";
}

@Override
public String descripcionCurseforge() {
    return "Note: No log is generated if \"Skip Launcher\" is enabled under Settings > Minecraft";
}

@Override
public String descripcionPrism() {
    return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/Derived: Right-click on the instance and select \"Edit Instance\". In the window that opens, look for the \"Minecraft Logs\" section or similar.<br>" +
           "These logs contain standard output (STDOUT), which is essential for diagnosing errors.";
}

@Override
public String descripcionHMCL() {
    return "HMCL (HelloMinecraftLauncher): You must select the folder where HMCL is installed and choose the \".hmcl\" folder. HMCL logs are saved here and contain important error information.<br>";
}

@Override
public String descripcionFenix() {
    return "LauncherFenix: The launcher has a development tab showing detailed logs. You can find this tab in the launcher options menu.";
}

@Override
public String descripcionATLauncher() {
    return "ATLauncher: There is a pop-up window displaying logs. It has buttons to copy and upload them. Logs are automatically generated when launching the game and contain critical diagnostic information.";
}

@Override
public String descripcionGDLauncher() {
    return "GDLauncher: Right-click the instance and select \"Settings\". Then go to the Logs section to view important data from standard output (STDOUT).";
}

@Override
public String descripcionLinksMarkdown() {
    return "Markdown Links: Paste any Markdown-formatted log links here. The system will attempt to extract log links (latest.log, launcher_log.txt, debug.log, etc.) and analyse them.";
}

@Override
public String noRegistroLauncherTitulo() {
    return "Launcher Log Not Found";
}

@Override
public String imagenNoEncontrada() {
    return "Image not found";
}

@Override
public String noRegistroDeLauncher() {
    return "GENERIC: Select the type of launcher you're using. Launcher logs (launcher_log.txt, stdout, etc.) contain vital error details not present in latest.log. CrashDetector cannot read your launcher's logs — it might not generate one, so you'll need to paste the logs manually.<br>" +
           "For more info, see <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">this issue</a>. These logs include standard output (STDOUT), which is essential for diagnosing many types of errors.";
}

@Override
public String faltar_de_clases_create() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Missing classes from Create detected. Create has changed significantly — many classes have been removed. Especially since Create 6 (February 2025), addons for older Create versions no longer work. QuickFix cannot fix this. You must update your Create addons, remove outdated ones, or use the correct Create version for the addons you want.</b>";
}

@Override
public String faltar_de_clases_epicfight() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Missing classes from EpicFight detected. EpicFight has undergone major changes — many classes have been removed. QuickFix cannot fix this. You must update your EpicFight addons, remove outdated ones, or use the correct EpicFight version for the addons you want.</b>";
}


@Override
public String openJ9NoSoportado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>You are using OpenJ9, which is not supported by this application. Many apps, including this one, do not support OpenJ9 due to differences in JVM implementation. QuickFix cannot resolve this issue automatically. You need to install a compatible JDK such as Oracle JDK, OpenJDK Hotspot, or other recommended alternatives.</b>";
}

@Override
public String necesitasJDK11() {
    return "<b style='color:#" + config.obtenerColorError() + "'>This version of the application requires Java 11 (JDK 11) to run properly. You are using an older, incompatible version of Java. QuickFix cannot upgrade your Java automatically. You must download and install JDK 11 or a newer compatible version from the links provided in the solution.</b>";
}

@Override
public String memoriaExcesiva() {
    return "<b style='color:#" + config.obtenerColorError() + "'>You have allocated too much memory, causing the system to lack sufficient resources. This usually happens when you set RAM higher than what your system has available, or when using a 32-bit JVM that cannot handle large memory allocations.</b>";
}

@Override
public String recomendacionMemoriaExcesiva() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>To fix this, reduce the amount of memory allocated to the app. The recommended maximum depends on your system, but should generally not exceed 70–80% of your total RAM. If using a 32-bit JVM, the limit is around 2–3 GB regardless of your physical RAM.</b>";
}

@Override
public String disminuirMemoriaHeap() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>To reduce the heap memory allocated to the app, open your launcher settings and find the RAM option. Lower the maximum value (Xmx) to a more suitable amount. For example, if you have 8 GB of RAM, assign 3–4 GB to the app. With 16 GB, you can assign 6–8 GB. Remember to leave enough memory for your OS and other programs.</b>";
}

@Override
public String forgeArchivosFaltantes(String archivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Faltan archivos esenciales de Forge. El archivo '" + archivo + "' no se encuentra en tu instalación. Esto suele ocurrir cuando la instalación de Forge se interrumpió o se eliminaron archivos importantes. QuickFix no puede recuperar estos archivos automáticamente. Necesitas reinstalar Forge correctamente desde el instalador oficial.</b>";
}

@Override
public String forgeVersionNoEncontrada(String version, String archivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Forge no puede encontrar la versión de Minecraft requerida. Se necesita la versión " + version + " pero no se encuentra en el archivo '" + archivo + "'. Esto ocurre cuando hay una incompatibilidad entre la versión de Minecraft y la versión de Forge que estás utilizando. Asegúrate de descargar la versión correcta de Forge que coincida con tu versión de Minecraft.</b>";
}

@Override
public String forgeTargetFmlclientNoEncontrado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>No se puede encontrar el target 'fmlclient' necesario para iniciar Forge. Esto indica que la instalación de Forge está incompleta o dañada. Es probable que los archivos esenciales de Forge no se hayan instalado correctamente. Necesitas reinstalar Forge usando el instalador oficial.</b>";
}

@Override
public String forgeClaseMinecraftFaltante() {
    return "<b style='color:#" + config.obtenerColorError() + "'>No se puede encontrar la clase principal de Minecraft en el cargador de clases. Esto suele indicar que la instalación de Forge está incompleta o que hay un conflicto con otros mods. Es posible que los archivos de Minecraft se hayan dañado durante la instalación de Forge. Necesitas reinstalar Forge correctamente.</b>";
}

@Override
public String forgeInstallacionNoCompleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>La instalación de Forge no está completa. Esto puede deberse a una instalación interrumpida, archivos eliminados o una incompatibilidad con tu versión de Minecraft. Forge necesita archivos específicos para funcionar correctamente, y algunos de ellos están faltando en tu instalación actual.</b>";
}

@Override
public String nombre_de_forge_instalacion_no_completa() {
    return "Instalación incompleta de Forge";
}

@Override
public String solucion_para_forge_instalacion_no_completa() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Para resolver este problema, necesitas reinstalar Forge correctamente. Asegúrate de descargar la versión adecuada para tu versión de Minecraft y seguir el proceso de instalación completo sin interrumpirlo.</b>";
}

@Override
public String descargar_forge_oficial() {
    return "Descargar Forge oficialmente";
}

@Override
public String reinstalar_forge_correctamente() {
    return "Cómo reinstalar Forge correctamente";
}

@Override
public String instrucciones_reinstalar_forge() {
    return "<html><body style='width: 500px;'>" +
           "<h3 style='color:#" + config.obtenerColorTitulo() + "'>Instrucciones para reinstalar Forge:</h3>" +
           "<ol>" +
           "<li>Descarga el instalador correcto de Forge desde el sitio oficial (versión recomendada para tu versión de Minecraft)</li>" +
           "<li>Cierra completamente tu launcher de Minecraft</li>" +
           "<li>Ejecuta el instalador de Forge como administrador</li>" +
           "<li>Selecciona la opción 'Installer' (no 'Installer (run client)')</li>" +
           "<li>Elige la carpeta de tu perfil de Minecraft en el launcher</li>" +
           "<li>Presiona 'OK' y espera a que termine la instalación</li>" +
           "<li>Reinicia tu launcher y verifica que Forge aparezca en la lista de perfiles</li>" +
           "</ol>" +
           "<p><b>Nota importante:</b> Si usas un launcher personalizado, asegúrate de seleccionar la carpeta correcta del perfil.</p>" +
           "</body></html>";
}

@Override
public String titulo_instrucciones_reinstaler_mcforge() {
    return "Instrucciones para reinstalar Forge";
}

@Override
public String error_enlace_insatisfecho(String nombreBiblioteca) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Unsatisfied Link Error: Failed to load library " + nombreBiblioteca + ". Possible solutions:<br/><br/>" +
           "a) Add the directory containing the shared library to -Djava.library.path or -Dorg.lwjgl.librarypath.<br/>" +
           "b) Add the JAR file containing the shared library to the classpath.<br/><br/>" +
           "This error occurs when Minecraft cannot locate essential files for execution. " +
           "It is usually caused by an incomplete Minecraft installation or system permission issues.</b>";
}

@Override
public String nombre_de_error_enlace_insatisfecho() {
    return "Unsatisfied Link Error";
}

@Override
public String solucion_para_error_enlace_insatisfecho() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Failed to load a library. Possible solutions:<br/><br/>" +
           "a) Add the directory containing the shared library to -Djava.library.path or -Dorg.lwjgl.librarypath.<br/>" +
           "b) Add the JAR file containing the shared library to the classpath.<br/><br/>" +
           "These technical solutions are intended for advanced users. Most users should try " +
           "reinstalling Minecraft before modifying these parameters.</b>";
}

@Override
public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
    return "<b style='color:#" + config.obtenerColorError() + "'>ID Conflict: The ID <strong>" + id + "</strong> is already in use by <strong>" + modOrigen + "</strong> when trying to add <strong>" + modDestino + "</strong>. This happens when two mods attempt to use the same ID for different elements.</b>";
}

@Override
public String conflicto_id_maximo() {
    return "<b style='color:#" + config.obtenerColorError() + "'>The maximum allowed ID range has been exceeded. This occurs when mods try to register blocks or items with IDs beyond the range supported by your Minecraft version.</b>";
}

@Override
public String nombre_de_conflicto_ids() {
    return "ID Conflict";
}

@Override
public String solucion_maximo_rango() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>To fix this on Minecraft 1.12.2, install <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. For 1.7.10, use <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
}

@Override
public String solucion_colision_id() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Use tools like <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> or <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> to resolve ID conflicts.</b>";
}

@Override
public String instalar_justenoughids() {
    return "Install JustEnoughIDs";
}

@Override
public String instalar_endlessids() {
    return "Install EndlessIDs";
}

@Override
public String usar_idfix_minus() {
    return "Use IdFix Minus or IdFix";
}

@Override
public String usar_minecraft_id_resolver() {
    return "Use Minecraft-ID-Resolver";
}

@Override
public String ver_documentacion_jp() {
    return "View Japanese documentation";
}

@Override
public String escanearDeMCreator() {
    return "Scan MCreator";
}

@Override
public String tituloArbolDeMods() {
    return "Module and Class Tree";
}

@Override
public String tipoBusqueda() {
    return "Type";
}

@Override
public String filtroTodos() {
    return "All";
}

@Override
public String filtroPaquetes() {
    return "Packages";
}

@Override
public String filtroClases() {
    return "Classes";
}

@Override
public String filtroMetodos() {
    return "Methods";
}

@Override
public String filtroCampos() {
    return "Fields";
}

@Override
public String filtroReferenciasCampo() {
    return "Field References";
}

@Override
public String filtroReferenciasMetodo() {
    return "Method References";
}

@Override
public String filtroReferenciasClase() {
    return "Class References";
}

@Override
public String tipBuscar() {
    return "Type here to search in the mod tree";
}

@Override
public String botonBuscar() {
    return "Search";
}

@Override
public String botonResetearArbol() {
    return "Reset Tree";
}

@Override
public String modsCargados() {
    return "Loaded Mods";
}

@Override
public String clases() {
    return "Classes";
}

@Override
public String metodos() {
    return "Methods";
}

@Override
public String campos() {
    return "Fields";
}

@Override
public String referencias() {
    return "References";
}

@Override
public String resultadosBusqueda() {
    return "Search Results";
}

@Override
public String buscarReferencias() {
    return "Find References";
}

@Override
public String referenciasMod() {
    return "Mod References";
}

@Override
public String referenciasClase() {
    return "Class References";
}

@Override
public String referenciasMetodo() {
    return "Method References";
}

@Override
public String referenciasCampo() {
    return "Field References";
}

@Override
public String noSeEncontraronReferencias() {
    return "No references found";
}

@Override
public String detalleMod() {
    return "Mod Details:";
}

@Override
public String ubicacion() {
    return "Location";
}

@Override
public String nombres() {
    return "Names";
}

@Override
public String modulo() {
    return "Module";
}

@Override
public String detalleClase() {
    return "Class Details:";
}

@Override
public String detalleMetodo() {
    return "Method Details:";
}

@Override
public String detalleCampo() {
    return "Field Details:";
}

@Override
public String clase() {
    return "Class";
}

@Override
public String tipo() {
    return "Type";
}

@Override
public String referenciasAMetodos() {
    return "References to Methods:";
}

@Override
public String referenciasACampos() {
    return "References to Fields:";
}

@Override
public String arbolDeMods() {
    return "Mod Tree";
}

@Override
public String metodo() {
    return "Method";
}

@Override
public String campo() {
    return "Field";
}


@Override
public String descompilar() {
    return "Decompile";
}

@Override
public String exportar() {
    return "Export";
}

@Override
public String importar() {
    return "Import";
}

@Override
public String errorImportar() {
	// TODO Auto-generated method stub
	return "Error Importar";
}

@Override
public String estructuraImportada() {
	// TODO Auto-generated method stub
	return "Estructura Importada";
}

@Override
public String estructuraExportada() {
	// TODO Auto-generated method stub
	return "Estructura Exportada";
}

@Override
public String errorExportar() {
	// TODO Auto-generated method stub
	return "Error Exportar";
}

@Override
public String exportando() {
    return "Exporting";
}

@Override
public String exportacionTardara() {
    return "Export may take time";
}

@Override
public String porFavorEspere() {
    return "Please wait";
}

@Override
public String noTienesVLCBin() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>You don't have VLC binaries. WaterMedia requires VLC binaries. You need to install them manually from https://www.videolan.org/vlc/.  </b>";
}

@Override
public String descargar_vlc() {
    return "Download VLC";
}

@Override
public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Critical error: The module name '" + nombreModulo + 
           "' contains invalid characters. The part '" + parteInvalida + 
           "' is not a valid Java identifier. This occurs when a mod uses Java reserved keywords (like 'true', 'class') or disallowed characters in its name.</b>";
}

@Override
public String nombre_de_error_caracteres_invalidos() {
    return "Invalid Characters in Mod Name";
}

@Override
public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
    return "The mod name '" + nombreModulo + "' is invalid because it contains '" + parteInvalida + 
           "', which is a Java reserved word or an invalid character. " +
           "Check the logs to find which mod corresponds to this name (usually the JAR file name)";
}

@Override
public String paso2_caracteres_invalidos(String nombreModulo) {
    return "Go to the mod's folder and edit the <b>mods.toml</b> file inside the <b>/META-INF/</b> folder. " +
           "Change the <b>modId</b> value to use only letters, numbers, and underscores, avoiding Java reserved keywords";
}

@Override
public String paso3_caracteres_invalidos() {
    return "Example of a valid name: 'truemod_shot_enchantment' instead of 'true.shot.enchantment'. " +
           "Remember that mod names cannot contain dots, hyphens, or Java reserved words like 'true', 'false', or 'class'";
}

@Override
public String errorDependenciaModFaltante(String nombreJar) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Critical error with mod: '" + nombreJar + "'. The required 'mandatory' field is missing from its dependencies. This occurs when the mods.toml file does not specify whether a dependency is required.</b>";
}

@Override
public String nombre_de_error_dependencia_mod_faltante() {
    return "Mod Dependency Missing Mandatory Field";
}

@Override
public String paso1_dependencia_mod_faltante(String nombreJar) {
    return "The problematic mod is: <b>" + nombreJar + "</b>. This file has an error in its dependency configuration";
}

@Override
public String paso2_dependencia_mod_faltante(String nombreJar) {
    return "Open the <b>mods.toml</b> file inside the <b>/META-INF/</b> folder of the mod <b>" + nombreJar + "</b>";
}

@Override
public String paso3_dependencia_mod_faltante() {
    return "In the dependencies section, ensure each entry includes <b>mandatory=true</b> or <b>mandatory=false</b> (e.g., modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
}

@Override
public String errorAccessTransformerInvalido(String nombreJar) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Critical error with mod: '" + nombreJar + "'. Invalid access transformer configuration. This occurs when the config file has incorrect syntax or references to non-existent classes/methods.</b>";
}

@Override
public String nombre_de_error_access_transformer_invalido() {
    return "Invalid Access Transformer";
}

@Override
public String paso1_access_transformer_invalido(String nombreJar) {
    return "The problematic mod is: <b>" + nombreJar + "</b>. This mod contains an invalid access transformer configuration";
}

@Override
public String paso2_access_transformer_invalido(String nombreJar) {
    return "Open the <b>accessTransformer.cfg</b> file inside the mod <b>" + nombreJar + "</b> (usually in the root folder of the JAR file)";
}

@Override
public String paso3_access_transformer_invalido() {
    return "Fix the access transformer syntax. Lines must follow the format: <b>access class.method</b> (e.g., public net.minecraft.world.entity.Entity.func_200560_a). Remove lines referencing classes or methods that don't exist in your Minecraft version";
}

@Override
public String errorDiscrepanciaModID(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Critical error: Mismatch between the mod ID in the @Mod annotation and the mods.toml file. The mod '" + nombreMod + "' cannot load because the IDs do not match.</b>";
}

@Override
public String nombre_de_error_discrepancia_mod_id() {
    return "Mismatch between @Mod and mods.toml";
}

@Override
public String paso1_discrepancia_mod_id(String nombreMod) {
    return "The mod '" + nombreMod + "' has a mismatch between the ID in the <b>@Mod</b> annotation and the value in <b>mods.toml</b>";
}

@Override
public String paso2_discrepancia_mod_id() {
    return "Ensure the ID in your main class matches the <b>modId</b> value in <b>/META-INF/mods.toml</b>. Example: <b>@Mod(\"mymod\")</b> must match <b>modId=\"mymod\"</b>";
}

@Override
public String paso3_discrepancia_mod_id() {
    return "If using Gradle, run <b>clean</b> after changes to ensure resources are properly updated. Sometimes old files remain in the build folder";
}

@Override
public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
    String plataforma = entornoInvalido.equals("CLIENT") ? "client" : "server";
    String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "server" : "client";
    
    return "<b style='color:#" + config.obtenerColorError() + "'>Critical error: Attempting to load class '" + nombreClase + 
           "' in the " + plataforma + " environment, but it is designed for " + plataformaOpuesta + 
           ". <b>Use the 'Mod Tree' feature in the sidebar to find which mod is trying to load this class</b>. " +
           "Mods are built specifically for one platform and do not work on the other.</b>";
}

@Override
public String nombre_de_error_mod_plataforma_incorrecta() {
    return "Mod on Wrong Platform";
}

@Override
public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
    return "In the <b>Mod Tree</b> tab (on the right), search for references to the class <b>" + nombreClase + 
           "</b> to identify which mod is causing the issue";
}

@Override
public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
    String plataforma = entornoInvalido.equals("CLIENT") ? "client" : "server";
    String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "server" : "client";
    
    return "The identified mod is a <b>" + plataformaOpuesta + "</b> mod and should not be used in your " + plataforma + " environment.";
}

@Override
public String paso3_mod_plataforma_incorrecta() {
    return "Remove the problematic mod from your <b>mods</b> folder. If you need similar functionality for this platform, " +
           "look for an alternative mod specifically designed for <b>client</b> or <b>server</b> as appropriate";
}

@Override
public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
    StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
    mensaje.append("Critical error: Missing metadata for modid '").append(modIdFaltante).append("'. ");
    
    if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
        mensaje.append("The following mods might be causing the issue: <b>");
        for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
            mensaje.append(modsPotenciales.get(i));
            if (i < modsPotenciales.size() - 1 && i < 2) mensaje.append(", ");
        }
        if (modsPotenciales.size() > 3) mensaje.append(", and others...");
        mensaje.append("</b>. ");
    }
    
    mensaje.append("This happens when a mod depends on another mod that is not installed or has an incorrect mods.toml file.");
    mensaje.append("</b>");
    
    return mensaje.toString();
}

@Override
public String nombre_de_error_metadata_mods_toml_faltante() {
    return "Missing mods.toml Metadata";
}

@Override
public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
    if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
        StringBuilder paso = new StringBuilder("The following mods depend on '").append(modIdFaltante).append("': <b>");
        for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
            paso.append(modsPotenciales.get(i));
            if (i < modsPotenciales.size() - 1 && i < 2) paso.append(", ");
        }
        if (modsPotenciales.size() > 3) paso.append(", and others...");
        paso.append("</b>. Use the <b>Mod Tree</b> feature to confirm which mod is causing the issue");
        return paso.toString();
    }
    return "A mod is trying to depend on '" + modIdFaltante + "', but this mod is not installed. Use the <b>Mod Tree</b> feature to identify the problematic mod";
}

@Override
public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
    return "You have two options:<br/>" +
           "1. <b>Install the missing mod</b>: Find and install the mod with ID '" + modIdFaltante + "'<br/>" +
           "2. <b>Remove the dependent mod</b>: If you don't need the functionality, remove the mod that depends on '" + modIdFaltante + "'";
}

@Override
public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
    return "If '" + modIdFaltante + "' is a library (like 'forge', 'minecraft', 'curios'), " +
           "ensure you have the correct versions of Minecraft and Forge installed. " +
           "If it's a regular mod, check its download page for required dependencies";
}

@Override
public String errorSistemaSonido() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Warning: Failed to initialize sound system. Sounds and music have been disabled. This error is commonly associated with SoundPhysicsMod and may be caused by conflicts with other sound libraries.</b>";
}

@Override
public String nombre_de_error_sistema_sonido() {
    return "Sound System Initialization Error";
}

@Override
public String paso1_sistema_sonido() {
    return "The error is commonly related to <b>SoundPhysicsMod</b>. Check if you have the latest version compatible with your Minecraft version";
}

@Override
public String paso2_sistema_sonido() {
    return "If you use other sound mods (like Sound Filters, Dynamic Surroundings, etc.), try temporarily removing SoundPhysicsMod to see if it resolves the conflict";
}

@Override
public String paso3_sistema_sonido() {
    return "Check the <b>logs</b> folder for additional messages related to LWJGL or OpenAL, which may indicate issues with underlying sound libraries";
}

@Override
public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
    StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
    mensaje.append("Critical error: Class '").append(nombreClase).append("' is registered as an event listener but contains no valid methods. ");
    
    if (!modsUbicacion.isEmpty()) {
        mensaje.append("This class is located in the following mods: <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            mensaje.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) mensaje.append(", ");
        }
        if (modsUbicacion.size() > 3) mensaje.append(", and others...");
        mensaje.append("</b>. ");
    }
    
    mensaje.append("This happens when a class is registered to listen to events but has no methods annotated with @SubscribeEvent.");
    mensaje.append("</b>");
    
    return mensaje.toString();
}

@Override
public String nombre_de_error_sin_listeners_en_clase() {
    return "Class Registered Without Event Listeners";
}

@Override
public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
    if (!modsUbicacion.isEmpty()) {
        StringBuilder paso = new StringBuilder("The problematic class is in these mods: <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            paso.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) paso.append(", ");
        }
        if (modsUbicacion.size() > 3) paso.append(", and others...");
        paso.append("</b>. These mods are attempting to register events without valid methods");
        return paso.toString();
    }
    return "The class <b>" + nombreClase + "</b> was registered to listen to events but has no methods with <b>@SubscribeEvent</b> annotation. Use the <b>Mod Tree</b> feature to identify which mod contains this class";
}

@Override
public String paso2_sin_listeners_en_clase(String nombreClase) {
    return "In the source code, ensure that class <b>" + nombreClase + "</b> contains at least one method with: " +
           "<b>@SubscribeEvent public void methodName(SpecificEvent event) { ... }</b>. " +
           "If it's an inner class, make sure it is not marked as static";
}

@Override
public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
    StringBuilder paso = new StringBuilder();
    
    if (!modsUbicacion.isEmpty()) {
        paso.append("For the identified mods (<b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
            paso.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 1) paso.append(", ");
        }
        if (modsUbicacion.size() > 2) paso.append(", etc.");
        paso.append("</b>): ");
        
        if (modsUbicacion.size() == 1) {
            paso.append("contact the mod developer to fix the issue. ");
        } else {
            paso.append("contact the developers of these mods to fix the issue. ");
        }
    }
    
    paso.append("If you are the developer, remove this class from the EventBus or add valid @SubscribeEvent methods");
    
    return paso.toString();
}

@Override
public String errorUnionFileSystemCorrupto(String nombreArchivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Critical error: An exception 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' occurred while processing the file '" + 
           nombreArchivo + "'. This specific error indicates the launcher failed to properly download or extract the modpack files. " +
           "The message 'zip END header not found' reveals the JAR file is incomplete or corrupted, which is extremely common in launchers that poorly handle large file downloads. " +
           "This issue primarily affects users of Twitch/CurseForge, Technic Launcher, and especially Luna Pixel users, as these launchers often fail to verify the full integrity of downloaded files. " +
           "Luna Pixel users should consider switching to ATLauncher as a more stable alternative, which better handles file integrity and avoids this specific error. " +
           "The system cannot load mods because the ZIP format is damaged, preventing Forge from reading the necessary resources to start the game.</b>";
}

@Override
public String nombre_de_error_union_filesystem_corrupto() {
    return "UnionFileSystem Error - Corrupted File";
}

@Override
public String paso1_union_filesystem_corrupto(String nombreArchivo) {
    return "Completely reinstall the modpack from scratch";
}

@Override
public String paso2_union_filesystem_corrupto() {
    return "If you use Luna Pixel, switch to ATLauncher";
}

@Override
public String paso3_union_filesystem_corrupto() {
    return "Check your internet connection and available disk space before reinstalling";
}




@Override
public String habilitarProxySysOutSysErrMensaje() {
    return "Enable ProxySysOutSysErr?\n\n" +
           "This option allows CrashDetector to access System.out and System.err when the launcher does not provide logs.\n\n" +
           "Should only be enabled if you cannot manually paste a log.\n\n" +
           "Warning: This may interfere with some mods or launchers.\n\n" +
           "Restarting the game/app is required for changes to take effect.";
}

@Override
public String confirmacionTitulo() {
    return "Confirmation";
}

@Override
public String proxyHabilitadoMensaje() {
    return "ProxySysOutSysErr enabled successfully.\n\n" +
           "CrashDetector must be restarted for changes to take effect.";
}

@Override
public String informacionTitulo() {
    return "Information";
}


@Override
public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError, boolean connectorPresente) {
    StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
    
    if (azureLibError && geckoLibError) {
        mensaje.append("Critical error: AzureLib and GeckoLib initialized too early! ");
    } else if (azureLibError) {
        mensaje.append("Critical error: AzureLib initialized too early! ");
    } else if (geckoLibError) {
        mensaje.append("Critical error: GeckoLib initialized too early! ");
    }
    
    mensaje.append("This error occurs when trying to use Fabric mods with non-Fabric versions of these libraries. ");
    
    if (connectorPresente) {
        mensaje.append("A compatibility mod (Sinytra Connector or specialcompatibilityoperation) was detected, indicating you're trying to run Fabric mods in a Forge or FeatureCreep environment. ");
        mensaje.append("Check the 'FabricMC initialization error' in the logs to identify the specific mod causing the issue. ");
    }
    
    mensaje.append("AzureLib and GeckoLib are essential for animation mods, but must match the correct platform (Fabric or Forge). ");
    mensaje.append("The game cannot properly load animation mods due to this initialization conflict.");
    
    mensaje.append("</b>");
    return mensaje.toString();
}

@Override
public String nombre_de_error_azure_geckolib_inicializo_pronto() {
    return "Library Initialized Too Early";
}

@Override
public String paso1_azure_geckolib_inicializo_pronto() {
    return "Check the 'FabricMC initialization error' in the logs to identify the problematic mod";
}

@Override
public String paso2_azure_geckolib_inicializo_pronto() {
    return "Ensure you're using the correct version of AzureLib/GeckoLib for your platform (Forge or Fabric)";
}

@Override
public String errorCompatibilidadC2ME() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Critical error: Incompatibility between C2ME and compatibility mods. " +
           "This error occurs because C2ME attempts to access internal Java components restricted in environments with " +
           "Sinytra Connector or specialcompatibilityoperation, or other Fabric/Forge compatibility mods. " +
           "<b>C2ME is not compatible with these setups, but <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> is the recommended alternative</b> that works correctly " +
           "with connection mods. The game cannot start due to Java security permission conflicts.</b>";
}

@Override
public String nombre_de_error_compatibilidad_c2me() {
    return "C2ME Incompatibility with Compatibility Mods";
}

@Override
public String paso1_compatibilidad_c2me() {
    return "Remove C2ME from your mods folder";
}

@Override
public String paso2_compatibilidad_c2me() {
    return "Download and install <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> instead (compatible with Sinytra Connector)";
}

@Override
public String paso3_compatibilidad_c2me() {
    return "Ensure all compatibility mods (like Sinytra Connector) are updated to their latest versions";
}


@Override
public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Critical Error: Failed to load JEI plugin for mod '" + modId + 
           "'. The class '" + nombreClase + "' (plugin ID: '" + pluginId + 
           "') threw an error causing the game to crash during startup. " +
           "This issue occurs when a mod has an incompatible or broken JEI integration that interrupts game initialization.</b>";
}

@Override
public String nombre_de_error_jei_plugin_fallido() {
    return "JEI Plugin Failed - Causes Crash";
}

@Override
public String paso1_jei_plugin_fallido(String modId) {
    return "The mod <b>" + modId + "</b> contains a broken JEI plugin causing the crash. Use the <b>Mod Tree</b> feature to confirm which mod is causing the issue";
}

@Override
public String paso2_jei_plugin_fallido(String modId) {
    return "Temporarily remove the mod <b>" + modId + "</b> from your mods folder to check if it resolves the crash";
}

@Override
public String paso3_jei_plugin_fallido(String modId) {
    return "Look for updates for the mod <b>" + modId + "</b> or contact its developer reporting the JEI plugin issue. " +
           "In the meantime, the mod must be removed to be able to start the game";
}


@Override
public String tituloLectador() {
    return "Log Reader - Crash Detector";
}

@Override
public String obtenerTituloLeyenda() {
    return "COLOUR LEGEND";
}

@Override
public String obtenerErrorEnLeyenda() {
    return "Critical errors";
}

@Override
public String obtenerStacktraceEnLeyenda() {
    return "Stack traces";
}

@Override
public String obtenerTituloError() {
    return "Error";
}

@Override
public String obtenerErrorAlProcesarLinea() {
    return "An error occurred while processing the selected line";
}

@Override
public String obtenerNombreError() {
    return "ERROR NAME";
}

@Override
public String obtenerDescripcionError() {
    return "DETAILED DESCRIPTION";
}

@Override
public String obtenerSeleccionarConsola() {
    return "SELECT LOG";
}

@Override
public String obtenerNombreErrorPorDefecto() {
    return "Unidentified error";
}

@Override
public String obtenerDescripcionErrorPorDefecto() {
    return "A critical error was detected in the logs. " +
           "Check the stack trace to identify the root cause.";
}

@Override
public String obtenerErrorLecturaArchivo() {
    return "Could not read the log file. " +
           "Please verify the file exists and has read permissions.";
}

@Override
public String obtenerEtiquetaBotonLectador() {
    return "Log Reader";
}

@Override
public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
    StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
    mensaje.append("Critical error: Failed to register automatic event subscribers for mod '").append(modId).append("'. ");
    
    mensaje.append("Problematic class: <b>").append(nombreClase).append("</b>. ");
    
    if (!modsUbicacion.isEmpty()) {
        mensaje.append("This class is located in: <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            mensaje.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) mensaje.append(", ");
        }
        if (modsUbicacion.size() > 3) mensaje.append(", and others...");
        mensaje.append("</b>. ");
    }
    
    mensaje.append("This error occurs when a mod tries to automatically register a class as an event subscriber, but the class cannot be loaded. ");
    mensaje.append("<b>Check other errors in the log, as the root cause may be a previous failed load</b>.");
    mensaje.append("</b>");
    
    return mensaje.toString();
}

@Override
public String nombre_de_error_registro_suscriptores_automaticos() {
    return "Failure in Automatic Subscriber Registration";
}

@Override
public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
    return "The mod <b>" + modId + "</b> is trying to register the class <b>" + nombreClase + 
           "</b> as an automatic subscriber, but failed. Verify this class exists and is accessible";
}

@Override
public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase, List<String> modsUbicacion) {
    if (!modsUbicacion.isEmpty()) {
        StringBuilder paso = new StringBuilder("The problematic class <b>" + nombreClase + "</b> is in these files: <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            paso.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) paso.append(", ");
        }
        if (modsUbicacion.size() > 3) paso.append(", and others...");
        paso.append("</b>. ");
        paso.append("Use the <b>Mod Tree</b> feature to confirm which specific file contains the problematic class");
        return paso.toString();
    }
    return "The class <b>" + nombreClase + "</b> is not found in any mod file. Verify that mod <b>" + modId + 
           "</b> is installed correctly. Use the <b>Mod Tree</b> feature to help identify the issue";
}

@Override
public String paso3_registro_suscriptores_automaticos(String modId) {
    return "Update mod <b>" + modId + "</b> to the latest version compatible with your Minecraft and Forge versions. " +
           "If the issue persists, contact the mod developer and report the error with the problematic class";
}

@Override
public String paso4_registro_suscriptores_automaticos() {
    return "Review <b>other errors in the log</b> before this message, as the real issue may be an earlier load failure. " +
           "Sometimes a prior error prevents necessary classes from loading for event registration";
}

@Override
public String limpiado() {
	// TODO Auto-generated method stub
	return "Cleaned";
}

@Override
public String original() {
	// TODO Auto-generated method stub
	return "Original";
}

@Override
public String verEnConsola() {
	// TODO Auto-generated method stub
	return "View in Log";
}

@Override
public String advertencia() {
	// TODO Auto-generated method stub
	return "Advertencia";
}

@Override
public String fatal() {
	// TODO Auto-generated method stub
	return "Fatal";
}
@Override
public String noRegistroDeBattly() {
	// TODO Auto-generated method stub
	return "BattlyLauncher does not have a log or console available for copying. You can use ProxySysOutSysErr to intercept STDOUT and STDERR by restarting the game, but ProxySysOutSysErr may conflict with mods that modify STDOUT or STDERR";
}

@Override
public String noRegistroDeNightWorld() {
	// TODO Auto-generated method stub
	return "You need to enable debug mode in NightWorld's settings to obtain a launcher log. This is very important, especially because it includes STDOUT and STDERR";
}

@Override
public String noRegistroDeMCServidor() {
	// TODO Auto-generated method stub
	return "You need to save or paste the content from your server's terminal, as it contains information not found in other logs, including STDOUT, STDERR, and other errors. Please paste the content from your most recent session. For the future, you can save terminal output to a file called cd_launcherlog. To avoid having to paste it, add >> cd_launcherlog after your startup command, as shown in the image. Note this will prevent display in the terminal; output will only appear in that file afterward.";
}




























}
