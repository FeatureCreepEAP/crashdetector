package com.asbestosstar.crashdetector.idioma;

import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Ingles implements Idioma {
	private final Config config = Config.obtenerInstancia(); // Assuming this is how you get the Config instance, like
																// in Espanol

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Not a valid mods folder</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>I don't know where the CrashDetector JAR file is</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Looking for PID: " + String.valueOf(pid)
				+ "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") is dead!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>We don't have JVM</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Updating your ATI/AMD drivers might help. Read this guide to fix it: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>Driver Update Guide</a> https://www.amd.com/en/support/download/drivers.html Download </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Some older versions sometimes have a few issues with some Nouveau Graphics on early loading screen</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>You have an issue with your Graphics Drivers. If you have an AMD/ATI GPU or APU update your AMD graphics drivers. If you have an NVIDIA graphics card make sure to mark the game and all instances of javaw.exe to use the dedicated graphics card. Read this guide: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Guide to update drivers</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Your FML Early Window is failing. "
				+ "To Change this go to (.minecraft/config/fml.toml) "
				+ "Edit earlyWindowProvider to be earlyWindowProvider=\"none\" "
				+ "If you are on an M1 Mac you should also make sure you are using an ARM version of Java not an Intel x64 one. "
				+ "This is also a common issue if you have outdated Drivers. Please check this guide if on windows and turning of this does not work. <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Guide to update drivers</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>You do not have all the dependencies you need:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>" + linea + "</span>"; // Assuming 'linea' is
																								// already in English
																								// and just needs color
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Your CrashDetector report is here: <a href='"
				+ archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>View Report</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>This is the CrashDetector GUI. If the game closes without issues, ignore it.</span>";
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
		return "Share Report"; // Button text, no color span needed as per Spanish version (or you can add if
								// you want)
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "Share report, logs will be uploaded to securelogger.net and stored on another site";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Found potentially problematic JAR files (Prioritise FATAL then Higher lvl then lower ln):</b>";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Found potentially problematic modids (Prioritise FATAL then Lower lvl then lower ln):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Found potentially problematic packages (Prioritise FATAL then Lower lvl then lower ln):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You have fatal classes (FATAL) — this is very serious. Common causes include bad CoreMods or fatal dependency errors. You can use QuickFix to scan for mods with fatal classes. Missing fatal classes detected:</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Found contents in {} (Top is most important, only top 20 shown):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Potentially Problematic SpongeMixin Config:</b> ";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>You have mods with duplicated packages. You can fix this by removing the duplicate package (folder) from the JAR file. You can open the JAR using an archive program like WinRAR or 7-Zip, or you can change the file extension from .jar to .zip, delete the folder, and then rename it back to a .jar file.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>You have duplicate Mods</b> "
				+ linea.replace("from mod files", "from mod files");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>MinecraftForge suspicious: This mod has a problem:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV requires lithostitched, you can install it from here <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>To use Iris Shaders or Oculus, you need Sodium or a copy for another loader (Rubidium, Embedium, Bedium)</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Problem with KubeJS extension </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Issues detected with NVIDIA drivers on versions prior to Windows 11." + "</span><br/><br/>"
				+ "To ensure that Minecraft (and the current JVM) uses the dedicated NVIDIA GPU, follow these steps:<br/><br/>"
				+ "1. <strong>Identify the Java executable:</strong><br/>"
				+ "   - This program is using the following Java executable: " + obtenerRutaJava() + "<br/>"
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
				+ "Issues detected with NVIDIA drivers on Windows 11/Server 2025 or later." + "</span><br/><br/>"
				+ "To ensure that Minecraft (and the current JVM) uses the dedicated NVIDIA GPU, follow these steps:<br/><br/>"
				+ "1. <strong>Identify the Java executable:</strong><br/>"
				+ "   - This program is using the following Java executable: " + obtenerRutaJava() + "<br/>"
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Your Server or World has ticks longer than 60 seconds. This may be due to mods slowing down the server or hardware being too weak.</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You don't have enough RAM/Memory. You need to allocate more.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Theseus has additional issues, including failing to remove mods when you try. If you need to play with mrpack files, you can use alternative launchers such as Prism Launcher (for modrinth.com only), ATLauncher (for modrinth.com only), or Hello Minecraft Launcher (supports modrinth.com and bbsmc.net).</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>You are using \"Skip Launcher Start\" (CurseForge App). This can sometimes cause hard-to-detect issues. This is due to the \"Skip Launcher Start\" option in older versions of the CurseForge App or in the new version. Disable it and use the \"Mojang Launcher\" option in CurseForge settings. You can watch this English video by Claws of Berk (go to 1:11) "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>here</a>.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Warning: Missing classes detected (Warning level). Usually not critical, but can still cause issues — different from fatal class errors. Common causes include bad CoreMods or missing dependencies. You can use QuickFix to scan for mods with missing classes. Missing classes detected:</b>";
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
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Here are your verification results. Take it slowly; usually, the correct cause is in check 1 or 2. The rest (errors 3 and above) can be used for confirmation, but they're generally cascade errors you can ignore. Failures occur in layers, so fixing the root problem will resolve this particular error today but a new unrelated error may be back at it again tommarow, as one error often prevents another from showing up in the console.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Please use Java 17 for 1.17-1.20.4 and Java 21 for Anything newer, Java 8 for anything older. [Guide](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). If you still have issues it could be because some mod has too new or old of files.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 and above do not work on Minecraft Versions bellow 1.20.5 for most modloaders due to ASM being outdated.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java is Obsolete </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>You need JPMS Module " + mod_necesitas + " from "
				+ submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Cannot invoke " + metodo + " because "
				+ objeto + " is null" + "</b>";
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
				+ "at <a href=\"https://securelogger.net\">securelogger.net</a>. When you press the button to share the report, "
				+ "your report is sent to the selected endpoint (default: asbestosstar.egoism.jp) (changeable at the bottom). "
				+ "You can share all selected logs together with the report. If you do not wish to upload, do not use this dialogue! "
				+ "We do not process your report on the official endpoint (<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\">https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb</a>); "
				+ "we only remove disallowed links. The code is here: <a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">source code</a>. "
				+ "This is used solely to display information about your crash and the link to the logs. However, you may use a custom endpoint that might not use the same methods. "
				+ "You are using the report site " + Config.obtenerInstancia().obtenerSitoDeInformes()
				+ " and the log site " + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". "
				+ "You can also share individual logs without a report by pressing the share buttons next to individual log names; "
				+ "the logs will go to the selected log site. CrashDetector has default log anonymisation, which attempts to remove usernames, UUIDs, "
				+ "access tokens, session IDs, IP addresses, and other data. However, it is not perfect. Nevertheless, the modpack author can disable it. "
				+ "It can be enabled or disabled via the checkbox at the bottom of this screen. You are the controller of your own data; you decide where to upload your data. "
				+ "Log sites are owned by third parties whose ownership is often hidden for privacy reasons. You assume full responsibility for managing your data and the associated risks. "
				+ "CrashDetector’s Share Dialogue is merely an interface that allows you to manage this. "
				+ "It is important that you are aware of GDPR and ARCO. "
				+ "If you are in Europe, you can use <a href=\"https://securelogger.top\">securelogger.top</a> hosted in Germany by Hetzner. "
				+ "For more legal information, consult the following links: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>, "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">GDPR</a>, "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">Japan’s Basic Policy on Personal Information Protection</a>.";
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
				+ "simply attach a screenshot and select a logging site compatible " + "with older Java 8 versions.";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Incompatible JavaFML: Requires version "
				+ requerido + ", detected " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Attention! JavaFML requires a specific version of Minecraft Forge</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "The JAR file '" + archivoJar
				+ "' requires the language provider '" + proveedor + "' version '" + requerido
				+ "' or higher, but only version '" + encontrado + "' was found.</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ALERT! Crash Assistant is a fake malware detector. It intentionally blocks the game from launching disregarding your freedom to keep playing with the mods it targets. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>View MalwareMod.java code  </a>   "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>View JarInJarHelper.java code  </a>. Only this mod is on their list at this time and they are only really going after the default logging site which can be changed by the user and that only ever does anything if you explicitly choose to use the built in log sharing feature. CrashAssistant does NOT do any checks to even check which logging site is the one being set and do not explain how to change it (there is a dropdown on the bottom of the share dialogue) and no matter what site you have CrashAssistant will block the game from launching. In their message they say to do your own research, DO IT, look into the code of CrashDetector and Crash Assistant and understand what they do, do NOT rely on appeal to authority.</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "The mod '" + idMod
				+ "' has failed because the required class was not found: '" + claseNoEncontrada
				+ "'. Make sure all dependencies are installed correctly.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Failed to load ModLauncher Service: </b>"
				+ servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Error parsing the JSON file '" + recurso
				+ "' from the JAR file '" + archivoJar + "'. There are issues with the registration.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Error: The mod '" + modId
				+ "' requires version '" + requerido + "' or higher of '" + dependencia + "', but found '" + actual
				+ "'." + "</span>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Monitor configuration error: "
				+ "Failed to set screen mode. " + "Check:</b>" + "<br>- Multi-monitor configuration"
				+ "<br>- Updated graphics card drivers" + "<br>- System-supported resolution";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Java options error: "
				+ "Conflicting garbage collector options. "
				+ "Ensure you don't combine multiple GC algorithms in JVM parameters</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Critical NightConfig/Forge configuration error: " + "Configuration file is corrupted or incomplete. "
				+ "This can be caused by empty configuration files (often 0 bytes) in the 'config' folder in older or custom versions of NightConfig. "
				+ "For most versions, Night Config Fixes will resolve the issue, but if you are using an incompatible or custom version of NightConfig, you will need to delete the configuration files manually. "
				+ "This issue is more common in older MC Forge versions (which include NightConfig) and in older FabricMC mods that bundle NightConfig, but it can also occur in some custom NightConfig builds. "
				+ "More information about solutions is available at <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a>.</b>";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>The 'AuthMe' plugin failed to load and has shut down the server.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The world '" + nombreMundo
				+ "' could not be loaded because it contains errors and is likely corrupted.</b>";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>The configuration for the 'PermissionsEx' extension is invalid.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>There are multiple plugin files for the plugin named '" + nombrePlugin + "': '" + primerPath
				+ "' and '" + segundoPath + "'.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>An exception occurred while the world was loading chunks. If it exists for your platform, FeatureRecycler may be able to resolve the issue. https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin
				+ "' cannot execute the command '/" + comando + "'.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin
				+ "' requires the following dependency: '" + dependencia + "'.</b> ";
	}

	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin
				+ "' is missing the following required dependencies: " + deps.toString() + ".</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin
				+ "' requires API version '" + versionAPI + "' which is not compatible with the current server.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The world '" + nombreMundo
				+ "' is a duplicate of another world and cannot be loaded.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The block entity '" + nombre + "' of type '" + tipo
				+ "' at location " + coords + " is causing ticking errors.</b> ";
	}

	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "Problematic Block Entity";
	}

	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "Remove the '" + nombre + "' entity at location " + coords
				+ " using MCEdit or by editing the world directly.";
	}

	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod
				+ "' has multiple versions installed.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The mods '" + primerMod + "' and '" + segundoMod
				+ "' are incompatible with each other.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod
				+ "' has a critical error and cannot run.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>The following mods are required but not installed: " + deps.toString() + ".</b>";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod + "' requires the mod '"
					+ dependencia + "' as a dependency.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod + "' requires the mod '"
					+ dependencia + "' version " + version + ".</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin
				+ "' is not compatible with Folia's regional ticking.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod
				+ "' is missing from the datapack.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod
				+ "' has caused an error.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod
				+ "' is not compatible with Minecraft version " + versionMC + ".</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The mod '" + nombreMod
				+ "' is missing and required to load the world or plugin.</b>";
	}

	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "Missing mod";
	}

	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>The world was saved with the mod '" + nombreMod
				+ "' which seems to be missing.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The world was saved with mod '" + nombreMod
				+ "' version " + versionEsperada + ", but it is now running version " + versionActual + ".</b>";
	}

	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
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
			sb.append(" (Saved: ").append(versionesEsperadas.get(i)).append(", Current: ")
					.append(versionesActuales.get(i)).append(")");
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You tried to load a world created with a newer version of Minecraft.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin
				+ "' requires the following dependency: '" + dependencia + "'.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin
				+ "' is incompatible with the current server version.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>The plugin '" + nombrePlugin
				+ "' has caused an error during execution.</b>";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You have a problem with multiple threads accessing the LegacyRandomSource class. You can get more information using the 'Unsafe World Random Access Detector' or 'C2ME' mods.</b>";
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

				+ "<h3>How to use it:</h3>" + "<ol>"
				+ "<li><b>Select files:</b> Click the radio buttons next to the file names. "
				+ "Files ending in <span style='color: #4CAF50; font-weight: bold;'>.success</span> mean successful sessions, "
				+ "while those ending in <span style='color: #F44336; font-weight: bold;'>.failure</span> indicate crashes or errors.</li>"

				+ "<li><b>Automatic comparison:</b> Press the 'Compare' button to analyse both lists and show added (+) or removed (-) mods.</li>"

				+ "<li><b>View results:</b> The results are shown in colour-coded HTML format: " + "<ul>"
				+ "<li><span style='color: green;'>+ Added mod</span></li>"
				+ "<li><span style='color: red;'>- Removed mod</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>Key Features:</h3>" + "<ul>" + "<li>Supports any combination of success/failure files.</li>"
				+ "<li>Shows two-way differences for precise change tracking.</li>"
				+ "<li>Includes scrolling for long mod lists.</li>"
				+ "<li>Integrated explanatory images for better visual understanding.</li>" + "</ul>"

				+ "<p>Made with <3️ to help track changes in your configurations.</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "You may be experiencing an issue related to IPv6. " + "There are two solutions: "
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
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/Derived: Right-click on the instance and select \"Edit Instance\". In the window that opens, look for the \"Minecraft Logs\" section or similar.<br>"
				+ "These logs contain standard output (STDOUT), which is essential for diagnosing errors.";
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
		return "GENERIC: Select the type of launcher you're using. Launcher logs (launcher_log.txt, stdout, etc.) contain vital error details not present in latest.log. CrashDetector cannot read your launcher's logs — it might not generate one, so you'll need to paste the logs manually.<br>"
				+ "For more info, see <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">this issue</a>. These logs include standard output (STDOUT), which is essential for diagnosing many types of errors.";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Missing classes from Create detected. Create has changed significantly — many classes have been removed. Especially since Create 6 (February 2025), addons for older Create versions no longer work. QuickFix cannot fix this. You must update your Create addons, remove outdated ones, or use the correct Create version for the addons you want.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Missing classes from EpicFight detected. EpicFight has undergone major changes — many classes have been removed. QuickFix cannot fix this. You must update your EpicFight addons, remove outdated ones, or use the correct EpicFight version for the addons you want.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You are using OpenJ9, which is not supported by this application. Many apps, including this one, do not support OpenJ9 due to differences in JVM implementation. QuickFix cannot resolve this issue automatically. You need to install a compatible JDK such as Oracle JDK, OpenJDK Hotspot, or other recommended alternatives.</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>This version of the application requires Java 11 (JDK 11) to run properly. You are using an older, incompatible version of Java. QuickFix cannot upgrade your Java automatically. You must download and install JDK 11 or a newer compatible version from the links provided in the solution.</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You have allocated too much memory, causing the system to lack sufficient resources. This usually happens when you set RAM higher than what your system has available, or when using a 32-bit JVM that cannot handle large memory allocations.</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>To fix this, reduce the amount of memory allocated to the app. The recommended maximum depends on your system, but should generally not exceed 70–80% of your total RAM. If using a 32-bit JVM, the limit is around 2–3 GB regardless of your physical RAM.</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>To reduce the heap memory allocated to the app, open your launcher settings and find the RAM option. Lower the maximum value (Xmx) to a more suitable amount. For example, if you have 8 GB of RAM, assign 3–4 GB to the app. With 16 GB, you can assign 6–8 GB. Remember to leave enough memory for your OS and other programs.</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Faltan archivos esenciales de Forge. El archivo '"
				+ archivo
				+ "' no se encuentra en tu instalación. Esto suele ocurrir cuando la instalación de Forge se interrumpió o se eliminaron archivos importantes. QuickFix no puede recuperar estos archivos automáticamente. Necesitas reinstalar Forge correctamente desde el instalador oficial.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge no puede encontrar la versión de Minecraft requerida. Se necesita la versión " + version
				+ " pero no se encuentra en el archivo '" + archivo
				+ "'. Esto ocurre cuando hay una incompatibilidad entre la versión de Minecraft y la versión de Forge que estás utilizando. Asegúrate de descargar la versión correcta de Forge que coincida con tu versión de Minecraft.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>No se puede encontrar el target 'fmlclient' necesario para iniciar Forge. Esto indica que la instalación de Forge está incompleta o dañada. Es probable que los archivos esenciales de Forge no se hayan instalado correctamente. Necesitas reinstalar Forge usando el instalador oficial.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>No se puede encontrar la clase principal de Minecraft en el cargador de clases. Esto suele indicar que la instalación de Forge está incompleta o que hay un conflicto con otros mods. Es posible que los archivos de Minecraft se hayan dañado durante la instalación de Forge. Necesitas reinstalar Forge correctamente.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>La instalación de Forge no está completa. Esto puede deberse a una instalación interrumpida, archivos eliminados o una incompatibilidad con tu versión de Minecraft. Forge necesita archivos específicos para funcionar correctamente, y algunos de ellos están faltando en tu instalación actual.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "Instalación incompleta de Forge";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Para resolver este problema, necesitas reinstalar Forge correctamente. Asegúrate de descargar la versión adecuada para tu versión de Minecraft y seguir el proceso de instalación completo sin interrumpirlo.</b>";
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
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>Instrucciones para reinstalar Forge:</h3>" + "<ol>"
				+ "<li>Descarga el instalador correcto de Forge desde el sitio oficial (versión recomendada para tu versión de Minecraft)</li>"
				+ "<li>Cierra completamente tu launcher de Minecraft</li>"
				+ "<li>Ejecuta el instalador de Forge como administrador</li>"
				+ "<li>Selecciona la opción 'Installer' (no 'Installer (run client)')</li>"
				+ "<li>Elige la carpeta de tu perfil de Minecraft en el launcher</li>"
				+ "<li>Presiona 'OK' y espera a que termine la instalación</li>"
				+ "<li>Reinicia tu launcher y verifica que Forge aparezca en la lista de perfiles</li>" + "</ol>"
				+ "<p><b>Nota importante:</b> Si usas un launcher personalizado, asegúrate de seleccionar la carpeta correcta del perfil.</p>"
				+ "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "Instrucciones para reinstalar Forge";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Unsatisfied Link Error: Failed to load library "
				+ nombreBiblioteca + ". Possible solutions:<br/><br/>"
				+ "a) Add the directory containing the shared library to -Djava.library.path or -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Add the JAR file containing the shared library to the classpath.<br/><br/>"
				+ "This error occurs when Minecraft cannot locate essential files for execution. "
				+ "It is usually caused by an incomplete Minecraft installation or system permission issues.</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "Unsatisfied Link Error";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Failed to load a library. Possible solutions:<br/><br/>"
				+ "a) Add the directory containing the shared library to -Djava.library.path or -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Add the JAR file containing the shared library to the classpath.<br/><br/>"
				+ "These technical solutions are intended for advanced users. Most users should try "
				+ "reinstalling Minecraft before modifying these parameters.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ID Conflict: The ID <strong>" + id
				+ "</strong> is already in use by <strong>" + modOrigen + "</strong> when trying to add <strong>"
				+ modDestino
				+ "</strong>. This happens when two mods attempt to use the same ID for different elements.</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>The maximum allowed ID range has been exceeded. This occurs when mods try to register blocks or items with IDs beyond the range supported by your Minecraft version.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "ID Conflict";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>To fix this on Minecraft 1.12.2, install <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. For 1.7.10, use <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Use tools like <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> or <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> to resolve ID conflicts.</b>";
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
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>You don't have VLC binaries. WaterMedia requires VLC binaries. You need to install them manually from https://www.videolan.org/vlc/.  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "Download VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Critical error: The module name '" + nombreModulo
				+ "' contains invalid characters. The part '" + parteInvalida
				+ "' is not a valid Java identifier. This occurs when a mod uses Java reserved keywords (like 'true', 'class') or disallowed characters in its name.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "Invalid Characters in Mod Name";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "The mod name '" + nombreModulo + "' is invalid because it contains '" + parteInvalida
				+ "', which is a Java reserved word or an invalid character. "
				+ "Check the logs to find which mod corresponds to this name (usually the JAR file name)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "Go to the mod's folder and edit the <b>mods.toml</b> file inside the <b>/META-INF/</b> folder. "
				+ "Change the <b>modId</b> value to use only letters, numbers, and underscores, avoiding Java reserved keywords";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "Example of a valid name: 'truemod_shot_enchantment' instead of 'true.shot.enchantment'. "
				+ "Remember that mod names cannot contain dots, hyphens, or Java reserved words like 'true', 'false', or 'class'";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Critical error with mod: '" + nombreJar
				+ "'. The required 'mandatory' field is missing from its dependencies. This occurs when the mods.toml file does not specify whether a dependency is required.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "Mod Dependency Missing Mandatory Field";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "The problematic mod is: <b>" + nombreJar
				+ "</b>. This file has an error in its dependency configuration";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>Critical error with mod: '" + nombreJar
				+ "'. Invalid access transformer configuration. This occurs when the config file has incorrect syntax or references to non-existent classes/methods.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Invalid Access Transformer";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "The problematic mod is: <b>" + nombreJar
				+ "</b>. This mod contains an invalid access transformer configuration";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "Open the <b>accessTransformer.cfg</b> file inside the mod <b>" + nombreJar
				+ "</b> (usually in the root folder of the JAR file)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "Fix the access transformer syntax. Lines must follow the format: <b>access class.method</b> (e.g., public net.minecraft.world.entity.Entity.func_200560_a). Remove lines referencing classes or methods that don't exist in your Minecraft version";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Critical error: Mismatch between the mod ID in the @Mod annotation and the mods.toml file. The mod '"
				+ nombreMod + "' cannot load because the IDs do not match.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "Mismatch between @Mod and mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "The mod '" + nombreMod
				+ "' has a mismatch between the ID in the <b>@Mod</b> annotation and the value in <b>mods.toml</b>";
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

		return "<b style='color:#" + config.obtenerColorError() + "'>Critical error: Attempting to load class '"
				+ nombreClase + "' in the " + plataforma + " environment, but it is designed for " + plataformaOpuesta
				+ ". <b>Use the 'Mod Tree' feature in the sidebar to find which mod is trying to load this class</b>. "
				+ "Mods are built specifically for one platform and do not work on the other.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "Mod on Wrong Platform";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "In the <b>Mod Tree</b> tab (on the right), search for references to the class <b>" + nombreClase
				+ "</b> to identify which mod is causing the issue";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "client" : "server";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "server" : "client";

		return "The identified mod is a <b>" + plataformaOpuesta + "</b> mod and should not be used in your "
				+ plataforma + " environment.";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "Remove the problematic mod from your <b>mods</b> folder. If you need similar functionality for this platform, "
				+ "look for an alternative mod specifically designed for <b>client</b> or <b>server</b> as appropriate";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Critical error: Missing metadata for modid '").append(modIdFaltante).append("'. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("The following mods might be causing the issue: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", and others...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"This happens when a mod depends on another mod that is not installed or has an incorrect mods.toml file.");
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
			StringBuilder paso = new StringBuilder("The following mods depend on '").append(modIdFaltante)
					.append("': <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", and others...");
			paso.append("</b>. Use the <b>Mod Tree</b> feature to confirm which mod is causing the issue");
			return paso.toString();
		}
		return "A mod is trying to depend on '" + modIdFaltante
				+ "', but this mod is not installed. Use the <b>Mod Tree</b> feature to identify the problematic mod";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "You have two options:<br/>" + "1. <b>Install the missing mod</b>: Find and install the mod with ID '"
				+ modIdFaltante + "'<br/>"
				+ "2. <b>Remove the dependent mod</b>: If you don't need the functionality, remove the mod that depends on '"
				+ modIdFaltante + "'";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "If '" + modIdFaltante + "' is a library (like 'forge', 'minecraft', 'curios'), "
				+ "ensure you have the correct versions of Minecraft and Forge installed. "
				+ "If it's a regular mod, check its download page for required dependencies";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Warning: Failed to initialize sound system. Sounds and music have been disabled. This error is commonly associated with SoundPhysicsMod and may be caused by conflicts with other sound libraries.</b>";
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
		mensaje.append("Critical error: Class '").append(nombreClase)
				.append("' is registered as an event listener but contains no valid methods. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("This class is located in the following mods: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", and others...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"This happens when a class is registered to listen to events but has no methods annotated with @SubscribeEvent.");
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
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", and others...");
			paso.append("</b>. These mods are attempting to register events without valid methods");
			return paso.toString();
		}
		return "The class <b>" + nombreClase
				+ "</b> was registered to listen to events but has no methods with <b>@SubscribeEvent</b> annotation. Use the <b>Mod Tree</b> feature to identify which mod contains this class";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "In the source code, ensure that class <b>" + nombreClase + "</b> contains at least one method with: "
				+ "<b>@SubscribeEvent public void methodName(SpecificEvent event) { ... }</b>. "
				+ "If it's an inner class, make sure it is not marked as static";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("For the identified mods (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", etc.");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("contact the mod developer to fix the issue. ");
			} else {
				paso.append("contact the developers of these mods to fix the issue. ");
			}
		}

		paso.append(
				"If you are the developer, remove this class from the EventBus or add valid @SubscribeEvent methods");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Critical error: An exception 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' occurred while processing the file '"
				+ nombreArchivo
				+ "'. This specific error indicates the launcher failed to properly download or extract the modpack files. "
				+ "The message 'zip END header not found' reveals the JAR file is incomplete or corrupted, which is extremely common in launchers that poorly handle large file downloads. "
				+ "This issue primarily affects users of Twitch/CurseForge, Technic Launcher, and especially Luna Pixel users, as these launchers often fail to verify the full integrity of downloaded files. "
				+ "Luna Pixel users should consider switching to ATLauncher as a more stable alternative, which better handles file integrity and avoids this specific error. "
				+ "The system cannot load mods because the ZIP format is damaged, preventing Forge from reading the necessary resources to start the game.</b>";
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
		return "Enable ProxySysOutSysErr?\n\n"
				+ "This option allows CrashDetector to access System.out and System.err when the launcher does not provide logs.\n\n"
				+ "Should only be enabled if you cannot manually paste a log.\n\n"
				+ "Warning: This may interfere with some mods or launchers.\n\n"
				+ "Restarting the game/app is required for changes to take effect.";
	}

	@Override
	public String confirmacionTitulo() {
		return "Confirmation";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr enabled successfully.\n\n"
				+ "CrashDetector must be restarted for changes to take effect.";
	}

	@Override
	public String informacionTitulo() {
		return "Information";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("Critical error: AzureLib and GeckoLib initialized too early! ");
		} else if (azureLibError) {
			mensaje.append("Critical error: AzureLib initialized too early! ");
		} else if (geckoLibError) {
			mensaje.append("Critical error: GeckoLib initialized too early! ");
		}

		mensaje.append(
				"This error occurs when trying to use Fabric mods with non-Fabric versions of these libraries. ");

		if (connectorPresente) {
			mensaje.append(
					"A compatibility mod (Sinytra Connector or specialcompatibilityoperation) was detected, indicating you're trying to run Fabric mods in a Forge or FeatureCreep environment. ");
			mensaje.append(
					"Check the 'FabricMC initialization error' in the logs to identify the specific mod causing the issue. ");
		}

		mensaje.append(
				"AzureLib and GeckoLib are essential for animation mods, but must match the correct platform (Fabric or Forge). ");
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Critical error: Incompatibility between C2ME and compatibility mods. "
				+ "This error occurs because C2ME attempts to access internal Java components restricted in environments with "
				+ "Sinytra Connector or specialcompatibilityoperation, or other Fabric/Forge compatibility mods. "
				+ "<b>C2ME is not compatible with these setups, but <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> is the recommended alternative</b> that works correctly "
				+ "with connection mods. The game cannot start due to Java security permission conflicts.</b>";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Critical Error: Failed to load JEI plugin for mod '" + modId + "'. The class '" + nombreClase
				+ "' (plugin ID: '" + pluginId + "') threw an error causing the game to crash during startup. "
				+ "This issue occurs when a mod has an incompatible or broken JEI integration that interrupts game initialization.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "JEI Plugin Failed - Causes Crash";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "The mod <b>" + modId
				+ "</b> contains a broken JEI plugin causing the crash. Use the <b>Mod Tree</b> feature to confirm which mod is causing the issue";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "Temporarily remove the mod <b>" + modId
				+ "</b> from your mods folder to check if it resolves the crash";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "Look for updates for the mod <b>" + modId
				+ "</b> or contact its developer reporting the JEI plugin issue. "
				+ "In the meantime, the mod must be removed to be able to start the game";
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
		return "A critical error was detected in the logs. " + "Check the stack trace to identify the root cause.";
	}

	@Override
	public String obtenerErrorLecturaArchivo() {
		return "Could not read the log file. " + "Please verify the file exists and has read permissions.";
	}

	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "Log Reader";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Critical error: Failed to register automatic event subscribers for mod '").append(modId)
				.append("'. ");

		mensaje.append("Problematic class: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("This class is located in: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", and others...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"This error occurs when a mod tries to automatically register a class as an event subscriber, but the class cannot be loaded. ");
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
		return "The mod <b>" + modId + "</b> is trying to register the class <b>" + nombreClase
				+ "</b> as an automatic subscriber, but failed. Verify this class exists and is accessible";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder(
					"The problematic class <b>" + nombreClase + "</b> is in these files: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", and others...");
			paso.append("</b>. ");
			paso.append(
					"Use the <b>Mod Tree</b> feature to confirm which specific file contains the problematic class");
			return paso.toString();
		}
		return "The class <b>" + nombreClase + "</b> is not found in any mod file. Verify that mod <b>" + modId
				+ "</b> is installed correctly. Use the <b>Mod Tree</b> feature to help identify the issue";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "Update mod <b>" + modId
				+ "</b> to the latest version compatible with your Minecraft and Forge versions. "
				+ "If the issue persists, contact the mod developer and report the error with the problematic class";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "Review <b>other errors in the log</b> before this message, as the real issue may be an earlier load failure. "
				+ "Sometimes a prior error prevents necessary classes from loading for event registration";
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

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("Critical error: LexForge transformer detected in a NeoForge environment. ");
		sb.append("</b>");

		sb.append("Involved class: <b>").append(claseReceptora).append("</b>. ");
		sb.append("The affected interface is <b>").append(interfazObjetivo).append("</b> ");
		sb.append("and the missing method is <b>").append(firmaMetodoFaltante).append("</b>. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("The class was found in: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", and others...");
			sb.append("</b>. ");
		} else {
			sb.append("No JARs containing this class were located; it may be shaded or embedded as jar-in-jar. ");
		}

		sb.append("This failure occurs when a ModLauncher transformer/service compiled for MinecraftForge/LexForge ");
		sb.append("is loaded under NeoForge with an incompatible version of the ModLauncher API. ");
		sb.append("Update or replace the component for NeoForge.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "LexForge Transformer Used in NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "Identify the incompatible transformer: <b>" + claseReceptora + "</b>. " + "The expected API is <b>"
				+ interfazObjetivo + "</b> and the missing method is <b>" + firmaMetodoFaltante + "</b>. "
				+ "Check if the mod registers this class in <b>META-INF/services</b> and remove or disable it in NeoForge.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Location of involved mod(s): <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", and others...");
			sb.append("</b>. ");
		} else {
			sb.append("No JARs containing the class were found. Check jar-in-jar and shaded dependencies. ");
		}
		sb.append("Temporarily remove those JARs or use NeoForge-compatible versions to confirm the source.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "Replace the component with a NeoForge-specific version or recompile it against the "
				+ "ModLauncher version used by NeoForge. Avoid old binaries from LexForge/MinecraftForge.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "Clean your mods folder and remove duplicate jar-in-jar entries. Clear launcher cache if needed "
				+ "and restart to ensure no LexForge transformers are being loaded.";
	}

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia cannot start: Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("is incompatible.</b> ");
		sb.append("Remove Xenon and use Embeddium or Sodium instead. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Detected in: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", and others...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia incompatible with Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "Detected " + label + " incompatible with WaterMedia. Remove it from your profile.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("Locations: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", and others...");
			sb.append("</b>. Delete that JAR.");
			return sb.toString();
		}
		return "No JARs found. Check your mods folder and remove Xenon.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "Install Embeddium or Sodium as a replacement and restart the game.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "Compression error (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>Deflater closed during TACZ resource copy.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Related to: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", and others");
			sb.append("</b>. ");
		}
		sb.append("<br/><b>Solution:</b> in <code>tacz/tacz-pre.toml</code>, set <code>DefaultPackDebug=true</code>. ")
				.append("If needed, generate a map first and then activate it.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "In tacz/tacz-pre.toml, set DefaultPackDebug=true. If needed, generate a map first and then activate it.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "Unbound density functions";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>Density functions are missing from the registry.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("Missing: ");
			for (int i = 0; i < Math.min(4, claves.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append("<code>").append(claves.get(i)).append("</code>");
			}
			if (claves.size() > 4)
				sb.append(", …");
			sb.append(". ");
		}
		sb.append(
				"<br/><b>Solution:</b> install or enable the mod/datapack that defines those functions and restart. Another common cause of this issue is when you have the required mod, but it has a problem or conflict with another mod; for example, Terralith has many issues and can trigger this error and others, including JSON errors.");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "Install or enable the mod/datapack providing these functions and restart the game.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// Short message in error colour, explicitly mentioning the mod
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Registry entry not present: ").append(claveFaltante).append(". ");
		sb.append("Common with the alpha version of Steam & Railways for Create 6.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (alpha)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "Remove or replace the alpha version of Steam & Railways for Create 6 with a compatible version.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// Short, in error colour with direct recommendation
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Load conflict: Multiworld together with Sodium/Embeddium/Rubidium causes ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance). ")
				.append("Suggestion: remove Multiworld or the performance mod, or use compatible versions.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "Conflict: Multiworld with performance mods";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Uninstall Multiworld or Sodium/Embeddium/Rubidium, or update to mutually compatible versions.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Sodium detected an incompatible graphics driver. "
				+ "Update your GPU driver to the minimum required or follow the Sodium guide." + "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "OpenGL Context Error";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OpenGL failed: no current context or function not available in this context. "
				+ "It could also be a video driver issue." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "Update/reinstall your GPU drivers and restart; disable overlays and try without performance mods.";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "Link copied to clipboard.";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		return "Search inside archives (.zip/.jar/.war/.ear/.fpm/.rar for Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Texture resolution error: Cannot load "
				+ recurso + " - size: " + tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "Texture Resolution Error";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "This error occurs when textures are too large or there are too many resource packs. "
				+ "Try using lower-resolution resource packs or removing some resource packs. "
				+ "Check that you haven't added custom textures with resolution exceeding the allowed limit.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ModLauncher service error: Path contains invalid characters. "
				+ "ModLauncher services cannot process paths containing non-ASCII or special characters. "
				+ "Problematic characters include: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요, and especially the '\"' character when at the end of the name. "
				+ "Common ModLauncher service components include CrashDetector, "
				+ Config.obtenerInstancia().obtenerNombreCD()
				+ ", FeatureCreep, Vivicraft, Optifine, Sodium, clonos, Iris Shaders/Oculus, MixerLogger, CrashAssistant and Sintrya Connector. "
				+ "You can remove all services, but other issues may arise due to the path name. "
				+ "Solution: Rename the instance to use only ASCII characters (a-z, A-Z, 0-9), without spaces or special characters.</b>";
	}

	@Override
	public String nombre_error_modlauncher_path() {
		return "ModLauncher Path Error";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "This error occurs when the instance path contains non-ASCII or special characters. "
				+ "ModLauncher services cannot handle these paths. "
				+ "Solution: Rename the instance to use only ASCII characters (a-z, A-Z, 0-9) and avoid spaces and special characters. "
				+ "Pay special attention to the '\"' character, which is highly problematic, especially when at the end of the name.";
	}

	@Override
	public String tituloEditorCodice() {
		return "Codice Editor";
	}

	@Override
	public String nuevo() {
		return "New";
	}

	@Override
	public String actualizarSeleccionado() {
		return "Update selected";
	}

	@Override
	public String eliminarSeleccionado() {
		return "Delete selected";
	}

	@Override
	public String exportarJSON() {
		return "Export JSON...";
	}

	@Override
	public String guardarTodo() {
		return "Save all";
	}

	@Override
	public String general() {
		return "General";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "Text to search";
	}

	@Override
	public String filtro() {
		return "Filter (id)";
	}

	@Override
	public String criticalidad() {
		return "Criticality (WARNING/ERROR/FATAL)";
	}

	@Override
	public String prioridad() {
		return "Priority";
	}

	@Override
	public String lista() {
		return "Checks";
	}

	@Override
	public String colIdioma() {
		return "Language";
	}

	@Override
	public String colNombre() {
		return "Name";
	}

	@Override
	public String colResultado() {
		return "Result";
	}

	@Override
	public String vistaJson() {
		return "JSON Preview";
	}

	@Override
	public String idiomas() {
		return "Languages (all required)";
	}

	@Override
	public String elegirFiltro() {
		return "Choose...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "Select a filter";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "Available filters";
	}

	@Override
	public String faltanCampos() {
		return "Fill in all mandatory general fields.";
	}

	@Override
	public String critInvalida() {
		return "Invalid criticality. Use WARNING, ERROR or FATAL.";
	}

	@Override
	public String filtroNoExiste() {
		return "The specified filter does not exist.";
	}

	@Override
	public String faltanIdiomas() {
		return "Complete name and result for all languages:";
	}

	@Override
	public String verificacionInvalida() {
		return "A verification is invalid. Please check the fields.";
	}

	@Override
	public String guardadoOk() {
		return "Saved successfully.";
	}

	@Override
	public String editorCodiceBoton() {
		return "Add reasons";
	}

	@Override
	public String descripcionEditorCodice() {
		return "You can register reasons here. You need an ID — a string without special characters, accents or spaces. For filters, you can use \"line contains\" to search for a string in a line, \"all contains\" if the log contains a string, \"regex line\" if a line matches a regex, and \"regex all\" (we suggest using the line versions). You must set the criticality: FATAL, ERROR, or WARNING for colouring. For each language, you need to enter a name and result that will appear on screen. You can add more checks or remove others. Saved upon completion.";
	}

	@Override
	public String descartarCambios() {
		return "Discard unsaved changes in the current check?";
	}

	@Override
	public String confirmacion() {
		return "Confirmation";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "Do you want to save your changes before exiting?";
	}

	@Override
	public String salirSinGuardar() {
		return "Exit without saving";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Critical error: Failed to load a modlauncher service (IDependencyLocator).<br>");
		sb.append("🔹 <b>Problematic class:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>Affected mod:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append(
					"🔸 <b>Mod not identified.</b> Check recently installed, development, or poorly packaged mods.<br>");
		}

		sb.append("🔸 <b>Cause:</b> The mod's <code>META-INF/services/...</code> file is corrupted, ");
		sb.append("incompatible with this version of Forge/NeoForge, or the mod is for the wrong version.<br>");
		sb.append("🔸 <b>Consequence:</b> Forge/NeoForge cannot register the mod's dependencies, ");
		sb.append("preventing the game from starting.<br>");
		sb.append("🔸 <b>Solution:</b> Update, reinstall, or remove the problematic mod. ");
		sb.append("If using development mods, ensure they are compiled for your exact Forge/NeoForge version.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "Service Configuration Error (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. Identify the culprit mod: check recently installed or development mods.";
		}
		return "1. The problematic mod is: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. Update, reinstall, or remove the mod. Make sure to use a version compatible with your Forge/NeoForge.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>Critical error: Method not found.</b><br>"
				+ "The mod attempted to call the method <b style='color:#" + colorCodigo + "'>" + metodo + "</b>, "
				+ "which does not exist in this version of the game or another mod.<br>" + "<span style='color:#"
				+ colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "Method Not Found (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. This error occurs when a mod is incompatible with the current version of the game or another mod.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. Update all involved mods. If it persists, report the issue to the author of the affected mod.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>Critical error: Field not found.</b><br>"
				+ "The mod attempted to access the field <b style='color:#" + colorCodigo + "'>" + campo + "</b>, "
				+ "which does not exist in this version of the game or another mod.<br>" + "<span style='color:#"
				+ colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "Field Not Found (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. This error usually occurs when a mod is incompatible with the current version of the game or another mod.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. Update all affected mods. If the problem persists, contact the author of the mod that caused the error.";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();
		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>Need help?</strong><br>"
				+ "  If you don't know how to fix it or the reason isn't listed here, you can get help through our social networks. "
				+ "  Use the <img src='" + iconoCompartir
				+ "' alt='Share' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>Share</strong> button to generate links to your logs and results for our team. "
				+ "  If you're a modpack creator or organisation, edit <code>crash_detector/plantilla.htm</code> "
				+ "  to customise your team's links." + "</div>";
	}

	@Override
	public String restablecerPlantilla() {
		return "Reset Template";
	}

	@Override
	public String restablecer() {
		return "Reset";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		return "Reset " + nombreImagen + " to default values?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		return "Reset template to default values?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Missing AzureLib classes. If you already have AzureLib, please install a version from before 8 October 2025. This was common. If you don't have AzureLib, install the current version.</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "The mod <code>healight</code> is causing a critical error: <code>java.lang.NoSuchFieldError: INT</code>. "
				+ "This error occurs because the mod is trying to access a field that no longer exists in MCForge 47.10 for Minecraft 1.20+. "
				+ "The game cannot start due to this issue.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• Remove or update the <code>healight</code> mod. "
				+ "The current version is not compatible with MinecraftForge 47.10 for 1.20.1. "
				+ "Look for a newer version of the mod or consider using an alternative.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "Critical Error: healight - Field 'INT' not found";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("The class <code>").append(clase)
				.append("</code> does not implement the required method:<br>").append("<code>").append(metodo)
				.append("</code><br>").append("from interface <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br><br>Suspect mod or file: <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• This error occurs when a mod implements an interface but omits a required method. "
				+ "Update <b>both mods</b> involved (the one defining the interface and the one implementing it). "
				+ "If you don't know which ones, look for the names shown in the error message.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "Interface Method Not Implemented (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "A mod is attempting to load a <b>client-side</b> class "
				+ "(<code>AnimationMetadataSection</code>) on a <b>dedicated server</b>, which is impossible. "
				+ "This error usually occurs when a mod does not properly separate its client and server code. "
				+ "The presence of <code>ModernFix</code> may expose this issue, although it is not the direct cause.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>Quick fix:</b> Temporarily remove <code>ModernFix</code> to confirm if the server starts. "
				+ "If it does, the issue lies with another mod loading client classes on the server.<br>"
				+ "• <b>Permanent solution:</b> Identify the guilty mod (look for mods with animated resources, custom textures, or graphics libraries) and update or remove it.<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "Client-side class loaded on server (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "The configuration file of a <code>Sinytra Connector</code> mod is corrupted. "
				+ "This usually happens when the file becomes filled with null characters (<code>\\u0000</code>) "
				+ "due to an unexpected game shutdown, write failures, or mod conflicts.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• Navigate to the <code>config/</code> folder of your Minecraft instance.<br>"
				+ "• Locate and delete the configuration files for connector mods.<br>"
				+ "• Restart the game: Sinytra Connector will generate a fresh, clean configuration file.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "Sinytra Connector Configuration Corrupted";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "The file <code>" + nombreJar
				+ "</code> is corrupted or incomplete.<br>"
				+ "The system cannot read its contents because the final ZIP file header is missing.<br>"
				+ "This error usually occurs after an interrupted download or launcher failure.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "Corrupted JAR file (with specific name)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>Delete the corrupted file</b> and re-download it from the official source (CurseForge, MinecraftStorage, etc.).<br>"
				+ "• If you're using a launcher like CurseForge, Technic, or Luna Pixel, consider switching to <b>ATLauncher</b> or <b>Prism Launcher</b>, "
				+ "which better verify file integrity.<br>"
				+ "• Ensure your internet connection is stable during the download.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Cannot load the world because one of its NBT files is corrupted "
				+ "(e.g., <code>level.dat</code>, <code>playerdata/*.dat</code>, or chunks).<br>"
				+ "The specific error is: <code>UTFDataFormatException: malformed input around byte " + byteCorrupto
				+ "</code>.<br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ Before attempting any repair, make a full backup of the world folder.</b><br><br>"
				+ "You can try repairing the corrupted file using an <b>NBT editor</b> like <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>.<br>"
				+ "If the damage is severe, use a <b>hex editor</b> (like HxD) to inspect and correct invalid bytes "
				+ "(only if you have experience with the NBT format).<br>"
				+ "As a last resort, restore from a backup or use the <i>world repair</i> feature from mods like <code>FTB Backup</code>.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>Make a full backup of the world folder</b> before attempting any repair.<br>"
				+ "• Use an NBT editor (like NBT Studio) to open and fix the corrupted file.<br>"
				+ "• If that fails, inspect the file with a hex editor at the position of the corrupt byte.<br>"
				+ "• If inexperienced, restore from a recent backup.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "World Corrupted: Error loading NBT data";
	}

	@Override
	public String problema_con_openAL() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>You have an issue with OpenAL. Sometimes Nouveau drivers can cause this, but sometimes the application's bundled OpenAL version is incompatible with the one in your distribution and you need to use your distro's version. This is especially common with Red Hat-based distributions and sound mods like Sound Physics Remastered. See this guide for more help: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>How to fix Minecraft sound problems using Linux</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "The server cannot start because a world file is locked by another process.<br>"
				+ "This usually happens if:<br>" + "• There is already a server instance running.<br>"
				+ "• An antivirus or file explorer has the world folder open.<br>"
				+ "• The previous process did not close properly and left files locked.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>Close all server instances</b> (including background processes like javaw.exe).<br>"
				+ "• If you're using a hosting panel (like Multicraft), completely restart the server from the panel.<br>"
				+ "• <b>Temporarily disable your antivirus</b> if you suspect it's blocking the files.<br>"
				+ "• On local systems, close any Windows Explorer windows showing the world folder.<br>"
				+ "• If the problem persists, manually delete the <code>session.lock</code> file inside the world folder (only if you are sure no other server is active).";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "World file locked by another process";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "The mod attempted to extend the class <code>"
				+ clasePadreFinal + "</code>, " + "but this class is now <b>final</b> and cannot be inherited from.<br>"
				+ "The problematic class is: <code>" + claseHija + "</code>.<br><br>"
				+ "This usually occurs when a mod is compiled for an older version of Minecraft or another base mod, "
				+ "which has marked classes as <code>final</code> in recent versions.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>Update all involved mods</b>, especially those that might be related to the mentioned base mod.<br>"
				+ "• If the issue persists, look for a mod version compatible with your current Minecraft version and its dependencies.<br>"
				+ "• In some cases, temporarily removing the mod containing the child class can help confirm the cause.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "Attempt to inherit from a final class";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "You are using <b>Rubidium</b> (an obsolete fork of Sodium for Forge) alongside <b>Iris or Oculus</b>.<br>"
				+ "In recent versions of Minecraft (1.19.2+), "
				+ "Rubidium has not kept pace with Sodium and its dependencies have had issues.<br><br>"
				+ "This error can also occur if there is a conflict between performance mods (Sodium, Rubidium, Embeddium, Bedium, Xeonium, etc.) or Iris Shaders and another mod.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>Remove Rubidium</b> from your <code>mods</code> folder.<br>"
				+ "• <b>Install <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a></b>, "
				+ "the active and compatible fork of Sodium for Forge that does support Iris/Oculus on 1.20+.<br>"
				+ "• Make sure you don't have more than one Sodium fork installed at the same time (e.g., Rubidium + Embeddium).<br>"
				+ "• If you're using Oculus instead of Iris, verify it is also compatible with your Forge and Embeddium versions.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Obsolete Rubidium with Iris/Oculus (OptionInstance is final)";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "The <code>Simple Voice Chat</code> mod cannot start its voice server because "
				+ "the UDP port is already in use or the configured IP address is invalid.<br>"
				+ "This does not prevent the game from starting, but disables the voice chat functionality.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• <b>Close any other instance of Minecraft</b> or application using UDP port 24454.<br>"
				+ "• If you are on a server, ensure that <b>no other service</b> is using that port.<br>"
				+ "• In the mod's configuration (<code>config/voicechat/</code>), change the UDP port to a free one (e.g., 24455).<br>"
				+ "• If you're using a custom IP address, verify it is correct or leave it blank to use the default.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Voice Chat: UDP port occupied or invalid IP";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "The BlockItem <code>" + nombreBlockItem
				+ "</code> has a null block.<br>"
				+ "This error usually occurs in <b>Create addons</b> (such as <code>dndecor</code>, <code>createdeco</code>) "
				+ "when there are conflicts with <code>Amendments</code>, <code>Moonshine</code>, or incorrect block initialization.<br>"
				+ "<b>Note:</b> This is not a direct error from Amendments, but a symptom of a deeper issue in registry loading.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• <b>Update all related mods:</b> Create, Amendments, Moonshine, and any addon (especially <code>dndecor</code> and <code>createdeco</code>).<br>"
				+ "• If the problem persists, <b>temporarily remove Create addons</b> one by one to identify the culprit.<br>"
				+ "• Make sure <b>Amendments and Moonshine are compatible</b> with your version of Create and Forge.<br>"
				+ "• Check if there are beta versions or updated forks of the problematic addons.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "Null BlockItem in Create addon";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>")
				.append("Found mods that do not belong to any active platform (Forge, Fabric, etc.):<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>This usually occurs when:<br>")
				.append("• Mods from <b>Fabric and Forge</b> are mixed in the same folder.<br>")
				.append("• A mod is installed for an incompatible version of Minecraft.<br>")
				.append("• The mod is corrupted or not a valid JAR file.</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>Verify all mods are for the same platform</b> (Forge <b>or</b> Fabric, not both).<br>"
				+ "• Use the <b>mod tree</b> to identify which platform each file is detected as.<br>"
				+ "• Remove any mod you don't recognise or that is for a different platform.<br>"
				+ "• If you're using a launcher like CurseForge or Prism, ensure the profile is configured correctly.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "Mod incompatible with active loader";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Failed to create model <code>" + modid + ":"
				+ nombreModelo + "</code>.<br>" + "This indicates that the mod <code>" + modid
				+ "</code> has corrupted, missing, " + "or incompatible resources for your version of Minecraft.</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>Update the mod</b> to the latest version compatible with your instance.<br>"
				+ "• If you're using a development or custom build, revert to the official release.<br>"
				+ "• Verify the JAR file is not corrupted (reinstall it).<br>"
				+ "• If the issue persists, report the error to the mod author including this log.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "Failed to create resource model";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "A critical conflict between the mods <code>Moonlight</code> and <code>Iceberg</code> has been detected.<br>"
				+ "Both attempt to register resource reloading systems in an incompatible way, "
				+ "causing an OpenGL failure due to the absence of a valid graphics context.<br>"
				+ "This issue is common when using Forge versions that include Fabric mod adapters.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>Update both mods</b> to their latest versions compatible with your Forge version.<br>"
				+ "• If the problem persists, <b>temporarily remove Iceberg</b>, as Moonlight is often a more critical dependency for other mods.<br>"
				+ "• Ensure you don't have duplicate or mixed Forge/Fabric versions of these mods.<br>"
				+ "• Check if another mod (such as Supplementaries, Citadel, etc.) already includes Iceberg functionality internally.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "Critical Conflict: Moonlight vs Iceberg (OpenGL without context)";
	}

	@Override
	public String instantanea() {
		return "Snapshot";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "Since Last Snapshot";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "Select a file";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "Snapshot created successfully";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "Error creating snapshot";
	}

	@Override
	public String consejo() {
		return "Tip";
	}

	@Override
	public String resultadoMuestra() {
		return "Show Result";
	}

	@Override
	public String historaDeModsDesc() {
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>Tip:</b> Select two history files to compare the mod list. "
				+ "  The result shows <span style='color:%s;'>added (+)</span> and "
				+ "  <span style='color:%s;'>removed (&#8722;)</span> based on normalised names. "
				+ "  Use the 'Snapshot' button to create a copy of an existing file with the .instantanea extension."
				+ "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		return "Get Log Links as Markdown without Report";
	}

	@Override
	public String titulo_configuracion() {
		return "Configuration";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "Unexpected error while sharing.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "Unexpected error while generating links.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "Unexpected error while processing button.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "No associated file to open.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "File does not exist:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "Could not open in editor.\nPath will be copied to clipboard.";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "Could not open file; path was copied to clipboard.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "Desktop not supported; path was copied to clipboard.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "You are experiencing a rate limit. Try using another log site or another logging API.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		return "Share Link";
	}

	@Override
	public String infoDeTrazos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Fixing the tops of logs is the top priority. "
				+ "The format is Level, Line. " + "All logs have a numbering system. " + Verificaciones.nl_html
				+ "Generally, you need to look at the lowest levels across all logs; traces with high levels are usually false positives. "
				+ "It's important to use your ability to read the console, as stack trace analysis isn't perfect when there are many traces."
				+ "</b>";
	}

	// --- Warrant Canary Searcher ---
	/**
	 * Text on the button for the warrant canary searcher. Example: "Warrant Canary
	 * Searcher"
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "Warrant Canary Searcher";
	}

	/**
	 * Message shown in the dialog box informing that the feature will be available
	 * soon.
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "This feature will be available soon.";
	}

	/**
	 * Title of the dialog box informing about the future availability of the
	 * warrant canary searcher.
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "Coming Soon";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		return "Mods Incompatible with Crash Assistant (False)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		return "Mod Incompatible with Modpack using CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant has a list of mods it says are incompatible, but we have no evidence they are and the error is only in English. If you want to play with these mods, you can edit the file <code>config/crash_assistant/config.toml</code> and change <code>enabled = true</code> in the [compatibility] section to <code>enabled = false</code>.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant has the ability to mark mods as incompatible, but sometimes this is incorrect and the error message is only in one language. If you want to use these mods, you can edit the file <code>config/crash_assistant/problematic_mods_config.json</code> and change <code>should_crash_on_startup</code> from <code>true</code> to <code>false</code>.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Error: The mod '" + modId
				+ "' requires the mod '" + dependencia + "'. Currently, " + actual + "." + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Error: The mod '" + modId
				+ "' requires version '" + requerido + "' or higher of '" + dependencia
				+ "', but the mod is not installed." + "</span>";
	}

	// In the class MonitorDePID.idioma (add this method)
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Error: The mod '" + modId
				+ "' is incompatible with the current version of '" + dependencia + "'. "
				+ "You must remove the mod 'Iris/Oculus & GeckoLib Compat' as it is incompatible with Superb Warfare and does not work with the latest version of GeckoLib. "
				+ "Current version: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "Error: Failed to execute task for class '" + clase + "'. "
				+ "This error is common with mods that are not compatible with each other or have conflicts with other installed mods.";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "Task Execution Failures";
	}

	public String recomendacion_fallos_ejecucion() {
		return "This type of error usually occurs due to incompatibilities between mods. "
				+ "Especially common with mods that don't work correctly with ConnectorMod.";
	}

	public String info_clase_problematica() {
		return "Problematic Class:";
	}

	public String no_se_encontraron_clases_problema() {
		return "No specific classes with execution problems were found.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "A critical conflict between OptiFine and Entity Model Features (EMF) has been detected. "
				+ "These mods are incompatible and cause an injection failure that prevents the game from starting."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "Conflict between OptiFine and Entity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "Uninstall either OptiFine or Entity Model Features, as they are not compatible with each other.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "A critical conflict between OptiFine and Fusion has been detected. "
				+ "These mods are incompatible and cause an injection failure that prevents the game from starting."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "Conflict between OptiFine and Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "Uninstall either OptiFine or Fusion, as they are not compatible with each other.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel (required by Create) needs Sodium 0.6.0-beta.2 or higher. Rubidium is 0.5.3. "
				+ "Consider using <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> as an alternative."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "Flywheel and Sodium version conflict";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "Update Sodium to 0.6.0-beta.2 or higher, or install <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> as a compatible alternative.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "A critical conflict between OptiFine and Epic Fight has been detected. "
				+ "These mods are incompatible and cause an injection failure that prevents the game from starting."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "Conflict between OptiFine and Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "Uninstall either OptiFine or Epic Fight, as they are not compatible with each other.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "A critical conflict has been detected between OptiFine and Rubidium. "
				+ "These mods are incompatible and cause an injection failure that prevents the game from launching."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "OptiFine and Rubidium Conflict";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "Uninstall OptiFine or Rubidium, as they are incompatible with each other.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam is attempting to load on a dedicated server, but it is only compatible with the client. "
				+ "Remove FreeCam from the server or ensure it is installed only on the client." + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam on dedicated server";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "Remove FreeCam from the dedicated server, as it should only be installed on the client.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) is attempting to load on a dedicated server, but it is only compatible with the client. "
				+ "Remove ETF from the server or ensure it is installed only on the client." + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features on dedicated server";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "Remove Entity Texture Features from the dedicated server, as it should only be installed on the client.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "You must accept the Minecraft EULA to run the server. "
				+ "Edit the eula.txt file and change 'eula=false' to 'eula=true'." + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "Minecraft EULA not accepted";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "Edit the eula.txt file in the server folder and change 'eula=false' to 'eula=true'.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine is attempting to load on a dedicated server, but it is only compatible with the client. "
				+ "Remove OptiFine from the server or ensure it is installed only on the client." + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine on dedicated server";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "Remove OptiFine from the dedicated server, as it should only be installed on the client.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks is incorrectly marked for 1.20.1 but uses methods from 1.21.1. "
				+ "The mod is attempting to use ResourceLocation.fromNamespaceAndPath, which does not exist in 1.20.1."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "Iron's Spellbooks version error";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "Ensure you are using the correct version of Iron's Spellbooks compatible with your Minecraft version.";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "A critical conflict has been detected between OptiFine and Embeddium. "
				+ "These mods are incompatible and cause an injection failure that prevents the game from launching."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "OptiFine and Embeddium Conflict";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "Uninstall OptiFine or Embeddium, as they are incompatible with each other.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>This is common with conflicting world generation mods, especially Terralinth, AmplifiedNether, Nullscape, and Incendium, and other world generation mods. You may also need to install a missing mod.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable is attempting to load on a dedicated server, but it is only compatible with the client. "
				+ "Remove Controllable from the server or ensure it is installed only on the client." + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable on dedicated server";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "Remove Controllable from the dedicated server, as it should only be installed on the client.";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries is causing an error that prevents the server from loading. "
				+ "The mod has issues with the fire behaviour registry that cause a failure during datapack loading."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries prevents server loading";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "Try updating Supplementaries to the latest version or temporarily disable it to allow the server to load.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) encountered an issue with missing Jackson modules. "
				+ "Some mods, such as Valkyrien Skies, may cause this error by not including all required dependencies."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "Missing Jackson module in Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "Remove Groovy Modloader and related mods such as Valkyrien Skies that may cause dependency conflicts.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Every Compat found an invalid wood block name. "
				+ "Every Compat generally has many issues. Do not use it!" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "Invalid name in Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "Check the resource packs or mods that use Every Compat, as they may contain invalid block names.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "An error code (-1073741819) was detected, which may be caused by overlays such as Razer's GameCaster, Discord, OBS Studio, or NVIDIA driver issues."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "Error code -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "Try disabling overlays such as GameCaster, Discord or OBS Studio, and ensure your NVIDIA drivers are up to date.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips requires Immersive Messages as a dependency but it is not installed. "
				+ "Install Immersive Messages so that Immersive Tooltips works correctly." + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips missing dependency";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "Install Immersive Messages, as it is a required dependency for Immersive Tooltips.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins has a compatibility issue with Apoli Mod where ItemStack cannot be cast to EntityLinkedItemStack. "
				+ "This is common in versions later than 6.6.0. Consider using an earlier version or switching between Fabric and Forge versions."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "Casting error in Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "Use Medieval Origins version 6.6.0 or earlier, or try switching between the Fabric and Forge versions of the mod.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether is causing an error with a Registry Object not present in MusicManager. "
				+ "This issue is related to the MusicManager mixin from Reign of Nether." + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "MusicManager error in Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "Try updating Reign of Nether or temporarily removing it to resolve the error.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel only supports the YSM server on Linux or Android. "
				+ "This issue has been fixed in newer versions since 23 November 2025 on Modrinth." + "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel incompatible with Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "Update YesSteveModel to a newer version from Modrinth, as the issue has been fixed after 23 November.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "A critical conflict has been detected between Moving Elevators and OptiFine. "
				+ "These mods are incompatible and cause an injection failure that prevents the game from launching."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "Moving Elevators and OptiFine Conflict";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "Uninstall OptiFine or Moving Elevators, as they are incompatible with each other.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "A critical conflict has been detected between Fabric API (fabric-resource-loader-v0) and OptiFine. "
				+ "These mods are incompatible and cause an injection failure that prevents the game from launching."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "Fabric API and OptiFine Conflict";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "Uninstall OptiFine or update Fabric API to a compatible version.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "A mod has a faulty ITransformationService that cannot be instantiated: " + claseProveedor + ". "
				+ "This mod must be removed to allow the game to load." + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "Faulty ITransformationService";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "Remove the mod that contains the class " + claseProveedor
				+ ", as it has a faulty ITransformationService.";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError() + "'>A mod has an invalid version specification. "
				+ "The version must be enclosed in square brackets. "
				+ "You can use the grep/greprf utility from the side panel to search for the version </span>" + version
				+ "<span style='color:#" + config.obtenerColorError()
				+ "'> to identify which mod has the issue.</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "Invalid version in mod";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "Use the grep/greprf utility from the side panel to search for the problematic version and find the mod that contains it.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "A stack smashing error was detected, which terminated the process. "
				+ "This may be caused by issues with Early Window in Forge/NeoForge/PillowMC or with LWJGL 3.2.2 and newer."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "Stack Smashing Detected";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "Check your Early Window settings and consider using a different version of LWJGL or reviewing mods related to early windowing.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore is only for a specific modpack and should not be used in general installations, as it causes an issue."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore with incompatible Java version";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "Remove GregTechEasyCore, as it is only for a specific modpack and is not compatible with your general installation.";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "A conflict has been detected between MoniLabs and Connector Extras related to KubeJS modifications. "
				+ "These mods are incompatible in their KubeJS modifications." + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "MoniLabs and Connector Extras Conflict";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "Try uninstalling one of the mods (MoniLabs or Connector Extras) as they conflict in their KubeJS modifications.";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris requires Distant Horizons [2.0.4] or DH API version [1.1.0] or newer. "
				+ "See the compatibility guide at https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e to resolve the issue."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Iris and Distant Horizons Compatibility";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "Consult the compatibility guide at https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e and update Iris and Distant Horizons to compatible versions.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You are missing Minecraft classes. Possible reasons:</b>" + "<ul>"
				+ "<li>You have mods for other versions of the game. You can use <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> to check if the class exists in your version.</li>"
				+ "<li>You have a corrupted Minecraft installation (common with CurseForge App, ModrinthApp/Theseus/Astralrinth and other modpack launchers). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Watch tutorial</a> to troubleshoot CurseForge issues.</li>"
				+ "<li>You have a faulty coremod (if a coremod fails, it may block class loading).</li>" + "</ul>"
				+ "<p>Note: You can use the <b>grepr/fgrepr</b> tool in the sidebar to find mods that reference the missing classes, as long as you use '/' in the names.</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You are missing DangerZone classes. Possible reasons:</b>" + "<ul>"
				+ "<li>You have mods for other versions of the game.</li>" + "<li>You have faulty coremods.</li>"
				+ "<li>You have a corrupted launcher or installation.</li>" + "</ul>"
				+ "<p>Note: You can use the <b>grepr/fgrepr</b> tool in the sidebar to find mods that reference the missing classes, as long as you use '/' in the names.</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You are missing FeatureCreep classes. Possible reasons:</b>" + "<ul>"
				+ "<li>You have mods for other versions of FeatureCreep (e.g. ESR vs Nightly or v4 vs v12).</li>"
				+ "<li>You can install FeatureCreep from CurseForge or MinecraftStorage.</li>" + "</ul>"
				+ "<p>Note: You can use the <b>grepr/fgrepr</b> tool in the sidebar to find mods that reference the missing classes, as long as you use '/' in the names.</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You are missing ModLauncher classes. Possible reasons:</b>" + "<ul>"
				+ "<li>Your mods are for a different build of MinecraftForge, PillowMC or NeoForge (ModLauncher is used with these loaders).</li>"
				+ "<li>There are many modloader updates for a single Minecraft version.</li>"
				+ "<li>You have a corrupted launcher installation (common with CurseForge App, ModrinthApp/Theseus/Astralrinth and other modpack launchers). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Watch tutorial</a> to troubleshoot CurseForge issues.</li>"
				+ "</ul>"
				+ "<p>Note: You can use the <b>grepr/fgrepr</b> tool in the sidebar to find mods that reference the missing classes, as long as you use '/' in the names.</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You are missing Minecraft Forge classes. Possible reasons:</b>" + "<ul>"
				+ "<li>Your mods are for a different build of MinecraftForge.</li>"
				+ "<li>There are many modloader updates for a single Minecraft version.</li>"
				+ "<li>You have a corrupted installation (common with CurseForge App, ModrinthApp/Theseus/Astralrinth and other modpack launchers). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Watch tutorial</a> to troubleshoot CurseForge issues.</li>"
				+ "</ul>"
				+ "<p>Note: You can use the <b>grepr/fgrepr</b> tool in the sidebar to find mods that reference the missing classes, as long as you use '/' in the names.</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You are missing NeoForge classes. Possible reasons:</b>" + "<ul>"
				+ "<li>Your mods are for a different build of NeoForge.</li>"
				+ "<li>There are many modloader updates for a single Minecraft version.</li>"
				+ "<li>You have a corrupted installation (common with CurseForge App, ModrinthApp/Theseus/Astralrinth and other modpack launchers). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Watch tutorial</a> to troubleshoot CurseForge issues.</li>"
				+ "</ul>"
				+ "<p>Note: You can use the <b>grepr/fgrepr</b> tool in the sidebar to find mods that reference the missing classes, as long as you use '/' in the names.</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You are missing Fabric Loader classes. Possible reasons:</b>" + "<ul>"
				+ "<li>Your mods are for a different build of Fabric Loader.</li>"
				+ "<li>There are many modloader updates for a single Minecraft version.</li>"
				+ "<li>You have a corrupted installation (common with CurseForge App, ModrinthApp/Theseus/Astralrinth and other modpack launchers). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Watch tutorial</a> to troubleshoot CurseForge issues.</li>"
				+ "<li>Many mods require Fabric API. Please install Fabric API if needed.</li>" + "</ul>"
				+ "<p>Note: You can use the <b>grepr/fgrepr</b> tool in the sidebar to find mods that reference the missing classes, as long as you use '/' in the names.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>You are missing PillowMC classes. Possible reasons:</b>" + "<ul>"
				+ "<li>Your mods are for a different build of PillowMC.</li>"
				+ "<li>There are many modloader updates for a single Minecraft version.</li>"
				+ "<li>You have a corrupted installation (common with CurseForge App, ModrinthApp/Theseus/Astralrinth and other modpack launchers). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Watch tutorial</a> to troubleshoot CurseForge issues.</li>"
				+ "</ul>"
				+ "<p>Note: You can use the <b>grepr/fgrepr</b> tool in the sidebar to find mods that reference the missing classes, as long as you use '/' in the names.</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "You have a mod that is intentionally causing lag. Uranium is a lag mod. It does not always cause crashes, but it eventually may."
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack is marked as compatible with 1.19.* but is actually for 1.20.*, causing a 'class not found' error. "
				+ "The mod attempts to use DamageSources that do not exist in your current Minecraft version." + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Falling Attack version error";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "Ensure you are using the correct version of Falling Attack compatible with your Minecraft version.";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>").append("You need to install CFR (Class File Reader) to use this feature.<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append("On Linux, NetBSD or FreeBSD systems, you can install CFR from your package manager.<br>")
					.append("Search for the package at: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append("Alternatively, you can download the modified version used by FabricMC from:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("Save it in the following folder:<br>").append("<b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>Important:</b> after installing CFR, you must restart the mod for it to be recognised correctly.")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "No portrait available";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "Could not find class: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "CFR Decompiler – Sakura Riddle (Unofficial)";
	}

	@Override
	public String cfrClaseActual() {
		return "Current class";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "Portrait of Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "Error loading portrait";
	}

	public String noticiaLegalCFR() {
		return "This graphical user interface (GUI) for decompiling mods is designed to help users identify the causes of software failures. "
				+ "However, decompilation may sometimes be necessary, and users must take care not to use the generated code to infringe copyright law. "
				+ "It is recommended to review the license of the relevant mod before using any obtained code. Moreover, many mods officially provide their source code, "
				+ "which is generally cleaner and easier to understand than decompiled output. Remember that respect for intellectual property and usage licenses is fundamental to "
				+ "the mod development community. You may consult Mexico’s Federal Copyright Law here: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Ley Federal de Derechos de Autor (Spanish)</a> "
				+ "and the English version here: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (English)</a>. "
				+ "Given that you are on CurseForge, we also provide the U.S. Copyright Law in English: "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>. "
				+ "Furthermore, users are advised to research the laws applicable in their own jurisdiction. "
				+ "Our GUI is intended only for basic checks; for advanced analysis, please use FabricMC’s Enigma fork available on "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>. If you need to edit JAR files directly to create patches when source code is unavailable, consider using Recaf at "
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">its website</a>.";
	}

	@Override
	public String botonDescargarCfr() {
		return "Download CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "Open installation folder";
	}

	@Override
	public String colorFondoPrincipal() {
		return "Main background colour";
	}

	@Override
	public String colorTextoBotonReset() {
		return "Reset button text colour";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "Search field text colour";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "Filter dropdown menu text colour";
	}

	@Override
	public String colorTextoRenderer() {
		return "Renderer text colour";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "Loading overlay text colour";
	}

	@Override
	public String colorBorde() {
		return "Border colour";
	}

	@Override
	public String colorFondoRetrato() {
		return "Portrait mode background colour";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "Share link colour";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "Share field background colour";
	}

	@Override
	public String rosaFondo() {
		return "Background pink";
	}

	@Override
	public String rosaSuave() {
		return "Soft pink";
	}

	@Override
	public String moradoAcento() {
		return "Accent purple";
	}

	@Override
	public String textoOscuro() {
		return "Dark text";
	}

	@Override
	public String bordeSuave() {
		return "Soft border";
	}

	@Override
	public String fondoCampo() {
		return "Field background";
	}

	@Override
	public String fondoVistaPrevia() {
		return "Preview background";
	}

	@Override
	public String sintaxisConstructor() {
		return "Syntax colour: constructor";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "Syntax colour: help message";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "Syntax colour: HTML tags";
	}

	@Override
	public String colorFondoVentana() {
		return "Window background colour";
	}

	@Override
	public String colorPanel() {
		return "Panel colour";
	}

	@Override
	public String colorBotonTexto() {
		return "Button text colour";
	}

	@Override
	public String colorCampo() {
		return "Field colour";
	}

	@Override
	public String colorBordeDestacado() {
		return "Highlighted border colour";
	}

	@Override
	public String colorSeleccionTexto() {
		return "Text selection background colour";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "Selected text colour";
	}

	@Override
	public String colorEstadoExito() {
		return "Status colour: success";
	}

	@Override
	public String colorEstadoFallo() {
		return "Status colour: failure";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "Status colour: instant";
	}

	@Override
	public String colorResultadoAnadido() {
		return "Added result colour";
	}

	@Override
	public String colorResultadoEliminado() {
		return "Removed result colour";
	}

	@Override
	public String colorBordeScroll() {
		return "Scroll bar border colour";
	}

	@Override
	public String colorFondoPanel() {
		return "Panel background colour";
	}

	@Override
	public String colorBeigeListas() {
		return "List beige";
	}

	@Override
	public String colorTextoListas() {
		return "List text colour";
	}

	@Override
	public String colorBordeListas() {
		return "List border colour";
	}

	@Override
	public String colorBotonFondo() {
		return "Button background colour";
	}

	@Override
	public String colorBordeBoton() {
		return "Button border colour";
	}

	@Override
	public String colorDoradoTexto() {
		return "Golden text colour";
	}

	@Override
	public String colorPila() {
		return "Stack trace colour";
	}

	@Override
	public String colorTextoPanel() {
		return "Panel text colour";
	}

	@Override
	public String colorTextoNegro() {
		return "Black text colour";
	}

	@Override
	public String colorTextoPrincipal() {
		return "Main text colour";
	}

	@Override
	public String colorFondoResultados() {
		return "Results background colour";
	}

	@Override
	public String colorEstado() {
		return "Status colour";
	}

	@Override
	public String colorTextoDescripcion() {
		return "Description text colour";
	}

	@Override
	public String colorTextoEstado() {
		return "Status text colour";
	}

	@Override
	public String colorTextoExtra() {
		return "Extra text colour";
	}

	@Override
	public String colorSeparador() {
		return "Separator colour";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "A native error <code>StubRoutines::SafeFetch32</code> has been detected. "
				+ "This issue occurs on macOS with JDK 17.0.9 and is fixed in JDK 17.0.10 or later. https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "Native SafeFetch32 error in JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "Update your JDK to version 17.0.10 or later (e.g. 17.0.15).";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "If you use a launcher such as MultiMC, Prism Launcher or TLauncher, configure it to use a newer JDK. "
				+ "Some already include JDK 17.0.15 built-in.";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "The Spark mod may also contribute to this error. Consider disabling it temporarily. https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "The MCEF mod (Chromium Embedded Framework) is causing a silent hang.</b>" + "<ul>"
				+ "<li>MCEF is initialising at the end of the log, which usually means the game froze during loading.</li>"
				+ "<li>This mod is known to cause crashes on Linux, macOS, or with certain Java versions.</li>"
				+ "<li>An explicit error does not always appear, but the game never reaches the main menu.</li>"
				+ "</ul>"
				+ "<p>If you do not need in-game browser functionality (such as web maps or embedded pages), remove the mod.</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "MCEF initialisation issue (embedded browser mod)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "Remove the MCEF mod file (look for 'mcef' in the filename) from the 'mods' folder.";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "If you truly need it, ensure you are using a version compatible with your operating system and Minecraft version.";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "A conflict has been detected between <b>OptiFine</b> and <b>Iris/Oculus</b>.</b>" + "<ul>"
				+ "<li>OptiFine modifies Minecraft's rendering in a way incompatible with Iris or Oculus.</li>"
				+ "<li>The error <code>MixinLevelRenderer failed injection check</code> originates from <code>mixins.iris.json</code> or <code>mixins.oculus.json</code>.</li>"
				+ "</ul>"
				+ "<p>These mods cannot be used together. Remove OptiFine to use shaders with Iris or Oculus.</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "Conflict between OptiFine and Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "Remove the OptiFine file from the 'mods' folder.";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "Use Iris or Oculus without OptiFine for modern shaders.";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "A conflict has been detected between <b>ModernFix</b> and <b>OptiFine</b>.</b>" + "<ul>"
				+ "<li>ModernFix is incompatible with OptiFine because it breaks Forge functionality and slows down startup.</li>"
				+ "<li>ModernFix itself warns: <i>\"Use of ModernFix with OptiFine is not supported\"</i>.</li>"
				+ "</ul>" + "<p>You must remove one of the two mods for the game to work correctly.</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "Conflict between ModernFix and OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "Remove OptiFine or ModernFix from the 'mods' folder. They cannot be used together.";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "If you need optimisations, consider using only OptiFine, or replace ModernFix with lighter mods such as FerriteCore or EntityCulling.";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Error: invalid registry key with disallowed characters.</b>" + "<ul>"
				+ "<li><b>Detected key:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>In Minecraft, all registry keys (tags, recipes, advancements, etc.) must be <b>lowercase</b> and use only letters, numbers, underscores, hyphens, and slashes.</li>"
				+ "<li>This error is usually caused by a poorly coded mod or a faulty datapack.</li>" + "</ul>"
				+ "<p><b>Important tip:</b> Use the <b>grepr</b> or <b>fgrepr</b> tool in the sidebar and enable the <b>\"Search inside JAR files\"</b> option to find which mod contains this invalid key.</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "Registry key with uppercase or invalid characters";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "Use 'grepr' or 'fgrepr' with \"Search inside JAR files\" to locate the offending mod.";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "If you cannot identify the mod, remove recently added mods, especially those that add blocks, items, or tools.";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Error loading mod <b>" + escapeHtml(modNombre)
				+ "</b>.</b>" + "<ul>"
				+ "<li>The mod failed to initialise one of its components (e.g. the configuration menu).</li>"
				+ "<li>This usually occurs due to incompatibility with your Minecraft, Fabric, or other mods.</li>"
				+ "</ul>" + "<p>If the error persists, remove or update the mod <b>" + escapeHtml(modNombre)
				+ "</b>.</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "Mod initialisation error (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "Remove the mod '" + modNombre + "' from the 'mods' folder.";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "Update the mod '" + modNombre + "' to a version compatible with your installation.";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "An error related to the <b>En Garde!</b> mod has been detected.</b>" + "<ul>"
				+ "<li>This mod adds melee combat mechanics (parry, blocking, etc.).</li>"
				+ "<li>The error usually occurs due to incompatibility with other combat mods (such as Epic Fight, DualRiders, etc.) or using an incorrect version for your Minecraft.</li>"
				+ "</ul>" + "<p>If you don’t use advanced combat, consider removing En Garde! to avoid conflicts.</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "Error in En Garde! mod";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "Ensure you are using the version of En Garde! compatible with your Minecraft version and loader (Fabric/Forge).";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "If you use other combat mods (Epic Fight, Caelus, etc.), disable them or remove En Garde! to avoid conflicts.";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "An error caused by the <b>IdleTweaks</b> mod has been detected.</b>" + "<ul>"
				+ "<li>IdleTweaks tried to release a network channel that no longer exists (<code>Tried to release unknown channel</code>).</li>"
				+ "<li>This error usually occurs in older versions of the mod or when used on misconfigured servers.</li>"
				+ "</ul>"
				+ "<p>IdleTweaks is a quality-of-life mod, but it can cause instability. Consider updating or removing it.</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "IdleTweaks error (unknown network channel)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "Update IdleTweaks to the latest version compatible with your Minecraft.";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "Remove IdleTweaks from the 'mods' folder if you don’t need it.";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "An authentication error (HTTP 401) was detected while trying to log in to Minecraft.</b>"
				+ "<p>This error is <b>rarely the direct cause of a crash</b>, but it indicates you are using an unauthenticated (pirated) account.</p>"
				+ "<p>Official support channels (corporate projects, VTubers, modpack creators, etc.) <b>cannot assist you</b> if you are using a pirated copy, "
				+ "due to restrictions in their chat rules, contracts, agreements with Mojang/Microsoft, or reputation policies.</p>"
				+ "<p>This check can be <b>disabled in the corporate settings</b> of the detector. "
				+ "Warning: anti-piracy detection is <b>not perfect</b> and may trigger in development environments, with unstable internet, or when using modified launchers.</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>Miranda rights if you attempt to join support anyway:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Pirated Minecraft";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "Disable anti-piracy verification";
	}

	@Override
	public String comprarMC() {
		return "Buy Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "You are using the launcher <code>" + id
				+ "</code>, which is <b>not on the list of recommended launchers</b>.</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>Although it may work, non-recommended launchers often cause:</p>" + "<ul>"
				+ "<li>Corrupted installations of mods or the App.</li>"
				+ "<li>The game fails to start or hangs without a clear error.</li>"
				+ "<li>Unusual folder structure (makes diagnosis difficult).</li>"
				+ "<li>Unpredictable behaviour with Java, memory, or mods.</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "For a better experience, use one of the following recommended launchers:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Non-recommended launcher";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "Switch to a launcher from the recommended list.";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "You are using a <b>discouraged launcher</b>: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>Discouraged launchers may cause:</p>" + "<ul>"
				+ "<li>Corrupted installations of the App or mods.</li>"
				+ "<li>The game fails to start or crashes silently.</li>"
				+ "<li>Unusual file organisation (hard to debug).</li>"
				+ "<li>Uncertainty about how it manages mods, Java, or memory.</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "It is strongly recommended to use one of the following launchers:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Discouraged launcher";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "Switch to a recommended launcher to receive support.";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Recommended mods are missing for this environment.</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "Recommended mods missing";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "Install the recommended mods for an optimal experience.";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Discouraged mods were detected in your installation.</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "Discouraged mods detected";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "Remove the discouraged mods to avoid issues.";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Unauthorised modification detected in critical files. You have either edited files manually or are using an untrusted launcher.</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "Tampering detected";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "Reinstall the original files to restore integrity.";
	}

	@Override
	public String configuracionCorporativa() {
		return "Corporate Settings";
	}

	@Override
	public String idiomaRespaldo() {
		return "Fallback Language";
	}

	@Override
	public String buscardorHabilitado() {
		return "Enable Searcher";
	}

	@Override
	public String nombreHerramienta() {
		return "Tool Name";
	}

	@Override
	public String condenarPirateria() {
		return "Condemn Piracy";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "Recommended Launchers";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "Discouraged Launchers";
	}

	@Override
	public String modsRecomendados() {
		return "Recommended Mods";
	}

	@Override
	public String modsDesaconsejados() {
		return "Discouraged Mods";
	}

	@Override
	public String antiTamper() {
		return "AntiTamper";
	}

	@Override
	public String proximamente() {
		return "Coming Soon";
	}

	@Override
	public String informacion() {
		return "Information";
	}

	@Override
	public String errorCargandoImagen() {
		return "Error loading image";
	}

	@Override
	public String configuracionBasica() {
		return "Basic Settings";
	}

	@Override
	public String funcionalidades() {
		return "Features";
	}

	@Override
	public String derechosMiranda() {
		return "Miranda Rights (HIGHLY recommended)";
	}

	@Override
	public String gestionVerificaciones() {
		return "Verification Management";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "Name";
	}

	@Override
	public String codigoVerificacion() {
		return "Code";
	}

	@Override
	public String documentacionVerificacion() {
		return "Documentation";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "Enabled Verifications:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "Disabled Verifications:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "Disable all non-corporate";
	}

	@Override
	public String verCodigo() {
		return "View Code";
	}

	@Override
	public String verDocumentacion() {
		return "View Documentation";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "Select a verification to disable.";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "Select a verification to enable.";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "%d verifications not recommended for corporate use have been disabled.";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "No non-corporate verifications to disable.";
	}

	@Override
	public String operacionCompletada() {
		return "Operation completed";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "We miss you, Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "Corporate Verification Colour";
	}

	@Override
	public String nombreLanzador() {
		return "Launcher Name";
	}

	@Override
	public String motivo() {
		return "Reason";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Discouraged Launchers";
	}

	@Override
	public String moverADesaconsejados() {
		return "Discourage";
	}

	@Override
	public String moverARecomendados() {
		return "Recommend";
	}

	@Override
	public String guardarCambios() {
		return "Save Changes";
	}

	@Override
	public String cancelar() {
		return "Cancel";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "Please select a launcher to move.";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "Changes have been saved successfully!";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEs(String nombreLanzador) {
		return "Este lanzador no es recomendado debido a problemas de seguridad y estabilidad conocidos.";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEn(String nombreLanzador) {
		return "This launcher is not recommended due to known security and stability issues.";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoPt(String nombreLanzador) {
		return "Este lançador não é recomendado devido a problemas conhecidos de segurança e estabilidade.";
	}

	@Override
	public String razones() {
		return "Reasons";
	}

	@Override
	public String agregarLanzador() {
		return "Add launcher";
	}

	@Override
	public String quitarLanzador() {
		return "Remove launcher";
	}

	@Override
	public String editarRazones() {
		return "Edit reasons";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "Select a launcher to remove.";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "Select a launcher to edit.";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "Edit reasons for " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "Add new language";
	}

	@Override
	public String aceptar() {
		return "Accept";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "Select language";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "These are the launchers that CrashDetector recommends as good.";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "Correct result";
	}

	public String modsNoRecomendados() {
		return "Discouraged Mods";
	}

	public String agregarMod() {
		return "Add mod";
	}

	public String quitarMod() {
		return "Remove mod";
	}

	public String modId() {
		return "Mod ID / JBoss Modules Name";
	}

	public String rutaMod() {
		return "Mod path / file";
	}

	public String errorDebeIndicarMod() {
		return "You must specify at least the modid or the mod path.";
	}

	public String modsNoRecomendadosAviso() {
		return "Here you can register discouraged mods so that CrashDetector detects them if they are installed.";
	}

	@Override
	public String anularNormal() {
		return "Disable Normal";
	}

	@Override
	public String anularNormalDescripcion() {
		return "CrashDetector should warn even if there is no actual crash.";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "Register mods that CrashDetector recommends. If they are missing, CrashDetector may warn you.";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "If you choose to enable the anti-piracy warning, it is recommended to define here "
				+ "the rights of the person requesting support, as a preventive measure.\n\n"

				+ "Contrary to common belief, many popular communities and support channels "
				+ "do NOT require anti-piracy warnings to be enabled in order to provide help. However, "
				+ "documenting these rights may be useful if someone accesses the support channel anyway.\n\n"

				+ "You may refer to official documents such as Mexico’s Basic Rights Handbook for Detainees:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "As well as comparable legal principles used in other countries, including "
				+ "the United States, the Russian Federation, the People’s Republic of China, the Islamic Republic "
				+ "of Iran, and the Democratic People’s Republic of Korea.\n\n"

				+ "Examples of rights that may be included are:\n"
				+ "• The right not to provide unnecessary information for support, such as the launcher used, "
				+ "username, or UUID.\n" + "• The right against self-incrimination.\n"
				+ "• The right to refuse to answer questions not necessary for resolving the issue.\n"
				+ "• The right to receive guidance within the chat.\n"
				+ "• The right to use CrashDetector’s built-in log anonymisation feature.\n\n"

				+ "This text accepts HTML content.";
	}

	@Override
	public String editar() {
		return "Edit";
	}

	@Override
	public String advertenciaHashLento() {
		return "Warning: adding many large files may cause verification to take several minutes. CrashDetector will need to calculate the hash of each file before continuing. It is recommended to protect only strictly necessary files.";
	}

	@Override
	public String agregarArchivo() {
		return "Add file";
	}

	@Override
	public String agregarCarpeta() {
		return "Add folder";
	}

	@Override
	public String quitar() {
		return "Remove";
	}

	@Override
	public String rutaArchivo() {
		return "File path";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "The selected path is outside the current game directory. Only files and folders within the current directory or its subdirectories are allowed.";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>"
				+ "The opinions and comments of Sylent Bell do not necessarily reflect our own; "
				+ "we just thought it would be funny to put her here. CrashDetector is secular." + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "The GML (Groovy ModLoader) mod requires these changes and is the most common cause of this issue.</b>";
	}

	@Override
	public String mensajeIndependenteFlywheel(Set<String> mods) {
		StringBuilder listaMods = new StringBuilder();
		if (!mods.isEmpty()) {
			for (String mod : mods) {
				listaMods.append("<li>").append(mod).append("</li>");
			}
		}

		String mensaje = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "The use of <i>Independent Flywheel</i> has been detected.</b>"
				+ "<p><b>Independent Flywheel is obsolete (deprecated)</b> and should not be used in modern versions.</p>"
				+ "<p>Current versions of <b>Create</b> <b>already include Flywheel</b>, so installing it separately "
				+ "causes compatibility conflicts and loading errors.</p>"
				+ "<p>Some mods that explicitly depend on Independent Flywheel may "
				+ "<b>fail to work</b> or <b>behave unstably</b>. "
				+ "In certain advanced cases, these mods might work if you "
				+ "<b>manually edit the <code>mods.toml</code> file</b> to adjust version ranges, "
				+ "though this is <b>not recommended</b>.</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>Detected mods referencing Flywheel:</b></p>" + "<ul>" + listaMods.toString() + "</ul>")
				+ "<p>The recommended solution is to <b>remove Independent Flywheel</b> and use only "
				+ "the version included with Create.</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Independent Flywheel";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "An error related to the <i>Floral Enchantments</i> mod has been detected.</b>"
				+ "<p>The crash is caused by an internal failure in the mod while handling game data, "
				+ "which triggers a <b>NullPointerException</b> during execution.</p>"
				+ "<p>This issue is usually resolved by updating or removing the mod.</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "Floral Enchantments Error";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "You have both the NeoForge and standard versions of MixinExtras installed. If you are using MinecraftForge, you can install the fix from <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>this link</a>.</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "An error has been detected in terrain shadows with shaders (Iris).</b>"
				+ "<p>The issue occurs during terrain rendering.</p>"
				+ "<p>It is recommended to <b>try running the game without shaders</b> or lower graphical quality, "
				+ "especially on <b>Ultra</b> settings.</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "Terrain Shadows (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "An excessively long server tick has been detected.</b>"
				+ "<p>This indicates the game was stuck for too long in a single tick.</p>"
				+ "<p>It is recommended to <b>review the thread dump</b> generated in the log to identify the cause.</p>"
				+ "<p>The <b>Stack Trace Analysis</b> can help you locate the source of the freeze.</p>"
				+ "<p>Additionally, the <b>View in Log</b> button will highlight in red the possible responsible mods, "
				+ "as well as entries surrounded by <code>$modid$</code>, which often indicate the origin of the issue. For real-time scanning, we recommend using the CPU sampler in VisualVM. Ensure your server or computer is powerful enough to handle the mods you are using — it is possible that all your mods work correctly, but you simply have too many.</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Long Server Tick";
	}

	@Override
	public String tituloLFPDPPP() {
		return "Federal Law on Protection of Personal Data Held by Private Parties";
	}

	@Override
	public String aceptarPermanentemente() {
		return "Accept permanently";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "The Korean translation contains Southern slang terms that must be avoided to comply with the law. "
				+ "The use of foreign language, especially from the South, is strictly prohibited "
				+ "under the Pyongyang Cultural Language Protection Act.";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "For more information, consult the official document of the law: "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>Pyongyang Cultural Language Protection Act</a>";
	}

	public String leerLeyCompleta() {
		return "Read Full Law";
	}

	public String errorAbriendoEnlace() {
		return "Error opening link";
	}

	public String actaProteccionIdiomaCultural() {
		return "Pyongyang Cultural Language Protection Act";
	}

	@Override
	public String canarioTitulo() {
		return "Judicial Order Canary";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — Surveillance Monitor";
	}

	@Override
	public String revisar() {
		return "Review";
	}

	@Override
	public String cerrar() {
		return "Close";
	}

	@Override
	public String canarioTodoSeguro() {
		return "All services report a secure status.";
	}

	@Override
	public String canarioComprometido(int c) {
		return "ALERT: " + c + " service(s) report an insecure status.";
	}

	@Override
	public String colorAlerta() {
		return "Alert colour";
	}

	public String opcionesMunidiales() {
		return "Munidial Options";
	}

	public String consentimientoLFPDPPP() {
		return "LFPDPPP Consent";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "Enable access token handoff in Handoff for ReLauncher (discouraged).";
	}

	public String consolaDesarrollo() {
		return "Development Console";
	}

	public String mundial() {
		return "Global";
	}

	public String ningun() {
		return "None";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "Developer Console";
	}

	public String bajar() {
		return "Download";
	}

	public String logsSoporte() {
		return "Support logs";
	}

	public String detenerProceso() {
		return "Stop process";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "Copy selection";
	}

	public String seleccionarTodo() {
		return "Select all";
	}

	public String copiarTodo() {
		return "Copy all";
	}

	public String guardarTodoComoArchivo() {
		return "Save all as file";
	}

	public String obtenerEnlaceSoporte() {
		return "Get support link";
	}

	public String borrarTodo() {
		return "Clear all";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "Console background colour";
	}

	public String colorTextoConsola() {
		return "Console text colour";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "Consent confirmed.\nThe log sharing integration will be implemented here.";
	}

	@Override
	public String usarSakuraOriginal() {
		return "Use original Sakura Riddle image";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "A \"warrant canary\" is a transparency mechanism.\n\n"
				+ "As long as this message exists and services appear secure, "
				+ "it means the project has NOT received secret judicial orders, "
				+ "censorship demands, or lawful surveillance requests.\n\n"
				+ "If any canary disappears or is marked as insecure, "
				+ "that indicates a legal change has occurred.\n\n"
				+ "This panel checks all canaries registered in the system and displays " + "their current status.\n\n"
				+ "Press \"Review\" to update statuses.";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "Reset all options to their default values?";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "No options";
	}

	@Override
	public String seleccionaColor() {
		return "Select colour";
	}

	@Override
	public String botonMostrarGUI() {
		return "Show GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "Save all";
	}

	@Override
	public String botonRestablecerTodo() {
		return "Reset all";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms not loaded";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "An error was detected while accessing the LuckPerms API.</b>"
				+ "<p>The message indicates that <b>LuckPerms was not loaded</b> when another plugin attempted to use it.</p>"
				+ "<p><b>Possible causes:</b></p>" + "<ul>"
				+ "<li>The <b>LuckPerms plugin is not installed</b> or <b>failed to start up</b>.</li>"
				+ "<li>Another plugin is attempting to access LuckPerms <b>too early</b> or <b>incorrectly</b>.</li>"
				+ "</ul>" + "<p>It is recommended to <b>check the console</b> using the link to identify "
				+ "the plugin calling LuckPerms and verify its compatibility.</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Iris shaderpack not loaded";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "unknown" : shaderpack;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "An error was detected while loading a shaderpack with Iris/Oculus.</b>"
				+ "<p><b>Affected shaderpack:</b> " + nombre + "</p>"
				+ "<p>Minecraft could not open the shaderpack file (FileSystemNotFoundException).</p>"
				+ "<p><b>Possible solutions:</b></p>" + "<ul>"
				+ "<li>Verify that the shaderpack is correctly installed in the <b>shaderpacks</b> folder.</li>"
				+ "<li>Re-download the shaderpack, as the file may be corrupted.</li>"
				+ "<li>If the issue persists, delete the <b>config/iris.properties</b> file to reset Iris configuration.</li>"
				+ "</ul>" + "<p>After applying the changes, restart the game.</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "Failed to write configuration file";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "unknown" : ruta;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "An error occurred while saving a configuration file.</b>" + "<p><b>Affected file:</b> " + archivo
				+ "</p>" + "<p>Minecraft could not write the file using atomic writing (REPLACE_ATOMIC).</p>"
				+ "<p><b>This usually happens due to:</b></p>" + "<ul>"
				+ "<li>Incorrect permissions on the folder or file.</li>" + "<li>The file is marked as read-only.</li>"
				+ "<li>Another program (antivirus, backup, editor) is locking the file.</li>" + "</ul>"
				+ "<p><b>Recommendations:</b></p>" + "<ul>"
				+ "<li>Verify that you have write permissions in the folder.</li>"
				+ "<li>Remove the read-only attribute from the file.</li>"
				+ "<li>Close programs that might be using this file.</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "Access denied when creating backup";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "unknown" : origen;
		String dst = backup == null || backup.isEmpty() ? "unknown" : backup;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "A permissions error occurred while creating a backup of the configuration file.</b>"
				+ "<p><b>Original file:</b> " + src + "</p>" + "<p><b>Backup file:</b> " + dst + "</p>"
				+ "<p>The operating system blocked access during file saving.</p>"
				+ "<p><b>This usually happens due to:</b></p>" + "<ul>"
				+ "<li>Insufficient permissions on the folder.</li>" + "<li>The file is marked as read-only.</li>"
				+ "<li>Another program (antivirus, sync tool, editor) is using the file.</li>" + "</ul>"
				+ "<p><b>Recommendations:</b></p>" + "<ul>"
				+ "<li>Check the permissions of the <b>config</b> folder.</li>"
				+ "<li>Close programs that might be accessing the file.</li>"
				+ "<li>Try launching the launcher or Minecraft as administrator.</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		return "Enable Console";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>Debugging Tools</b><br><br>"
				+ "Here you can enable advanced features to debug CrashDetector and your games.<br><br>"
				+ "It is recommended to enable the development console to obtain detailed information, traces, and diagnostics during analysis.<br><br>"
				+ "If you need to test a multiplayer server in online mode, it may be necessary to allow the transfer of the access token to the CrashDetector process from privacy settings. "
				+ "This is generally <b>not recommended</b> in other cases.<br><br>"
				+ "Full instructions: <a href='https://example.com'>Link!</a>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "Incompatibility: Simple Clouds vs Shaders";
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Incompatibility detected between Simple Clouds and shaders.</b>"
				+ "<p>Simple Clouds is incompatible with shadow mods (Iris/Oculus) when Distant Horizons is installed.</p>"
				+ "<p><b>Recommended options:</b></p>" + "<ul>"
				+ "<li>Remove <b>Simple Clouds</b> if you wish to use shaders.</li>"
				+ "<li>Alternatively, uninstall <b>Iris or Oculus</b> if you prefer to keep Simple Clouds.</li>"
				+ "</ul>"
				+ "<p>This limitation originates from the Simple Clouds mod itself and cannot be resolved without modifying its code.</p>";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "Sidebar button colour";
	}

	
	@Override public String profilerTitulo() { return "Profiler"; }
	@Override public String profilerDescripcion() { return "Performance analysis tool based on instrumentation and sampling."; }
	@Override public String profilerIniciar() { return "Start"; }
	@Override public String profilerDetener() { return "Stop"; }
	@Override public String profilerLimpiar() { return "Clear"; }
	@Override public String profilerEstadoIniciado() { return "Profiler started."; }
	@Override public String profilerEstadoDetenido() { return "Profiler stopped."; }
	@Override public String profilerMuestraHilo(String nombreHilo) { return "Sample received from thread: " + nombreHilo; }
	
	@Override
	public String samplerDescripcion() {
	    return "Periodic sampling of stacks to detect bottlenecks and deadlocks.";
	}
	
	
	
	
	
	
	
}
