package com.asbestosstar.crashdetector.idioma;

import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;

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
public String problema_con_graficas_ati() {
    return "<span style='color:#" + config.obtenerColorError() + "'>Ĝisdatigi viajn kontrolilojn ATI/AMD eble helpas vin. Legu ĉi tiun gvidilon por ripari ĝin: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Gvidilo pri ĝisdatigo de kontroliloj</a> https://www.amd.com/es/support/download/drivers.html Elŝuti </span>";
}

    @Override
    public String problema_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>Kelkaj malnovaj versioj foje havas problemojn kun fruaj ŝarĝoj de Nouveau-kartoj.</span>";
    }

    @Override
    public String problema_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Estas problemo kun via grafik-ŝoforprogramo. Se vi uzas AMD/ATI GPU aŭ APU, ĝisdatigu viajn AMD-ŝoforprogramojn. Se vi uzas NVIDIA-karton, certigu ke la ludo kaj ĉiuj javaw.exe-ekzempleroj uzas apartan grafikkarton. Legu ĉi tiun gvidilon: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Gvido por ĝisdatigi ŝoforprogramojn</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Via FML-frua fenestro malsukcesis ŝargi. Por ripari, iru al (.minecraft/config/fml.toml) kaj agordu 'earlyWindowProvider' al \"none\". Se vi uzas Mac M1, certigu ke vi uzas ARM-version de Java, ne Intel x64. Ĉi tio ankaŭ ofte okazas pro malnovaj ŝoforprogramoj. Se vi uzas Windows kaj malŝalti ĉi tiun agordon ne helpas, konsultu ĉi tiun gvidilon: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Gvido por ĝisdatigi ŝoforprogramojn</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesarias() {
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
    public String texto_de_boton_local_enlace() {
        return "<span style='color:#" + config.obtenerColorBoton() + "'>Vidi raporton</span>";
    }

    @Override
    public String texto_debajo_de_buton_local_enlace() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Vidi lokan raporton en retumilo.</span>";
    }

    @Override
    public String texto_de_buton_compartir_enlace() {
        return "Kunhavigi raporton";
    }

    @Override
    public String texto_debajo_de_buton_compartir_enlace() {
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
    public String posibilidad_fatal() {
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
    public String falta_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Vi havas fatalajn klasojn (FATAL), tio estas tre gravega. Oftaj kaŭzoj estas malbonaj CoreMods aŭ fatalaj dependecoj. Vi povas uzi QuickFix por serĉi modojn kun fatalaj klasoj. Mankantaj fatalaj klasoj trovitaj:</b>";
    }

    @Override
    public String corchetes_ondulados() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Enhavo de {} (plej grava supre, nur unua 20 montritaj):</b>";
    }

    @Override
    public String config_spongemixin_problematico() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Detektita problema SpongeMixin-agordo: </b>";
    }

    @Override
    public String module_resolution_exception() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Vi havas modojn kun duplikitaj pakaĵoj. Vi povas solvi tion forigante la duplikitan pakaĵon (dosierujon) el la JAR-dosiero. Vi povas malfermi la JAR-on en arkivilo kiel WinRAR aŭ 7z, aŭ vi povas ŝanĝi la dosiernomon de .jar al zip kaj poste forigi la dosierujon kaj tiam reŝanĝi ĝin al .jar dosiero.</span>";
    }

    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Detektitaj duoblaj Modoj</b> " + linea.replace("from mod files", "el mod-dosieroj");
    }

    @Override
    public String mcforge_mod_sospechoso() {
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
    
    
    
    
    @Override
public String problema_con_graficas_nvidia_windows_viejo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "Problemoj detektitaj kun NVIDIA-drajviloj en versioj antaŭ Windows 11."
            + "</span><br/><br/>"
            + "Por certigi, ke Minecraft (kaj la nuna JVM) uzas la dediĉitan grafikan karton NVIDIA, sekvi tiujn paŝojn:<br/><br/>"
            + "1. <strong>Identigu la Java-ekzekuteblon:</strong><br/>"
            + "   - Ĉi tiu programo uzas la jenan Java-ekzekuteblon: "
            + obtenerRutaJava() + "<br/>"
            + "   - Se vi ne vidas specifan vojon, vi povas trovi la Java-ekzekuteblon per serĉado de 'java.exe' en via sistemo.<br/><br/>"
            + "2. <strong>Malfermu la NVIDIA-Kontrolpanelon:</strong><br/>"
            + "   - Alklaku dekstre sur la labortablo kaj elektu 'NVIDIA-Kontrolpanelo'.<br/><br/>"
            + "3. <strong>Agordu la preferatan GPU-on:</strong><br/>"
            + "   - En la NVIDIA-Kontrolpanelo, iru al 'Administri 3D-agordojn'.<br/>"
            + "   - Elektu la opcion 'Programa agordo'.<br/>"
            + "   - Alklaku 'Aldoni' kaj serĉu la jam identigitan Java-ekzekuteblon (ekz.: 'java.exe').<br/>"
            + "   - Certigu, ke ĝi estas agordita por uzi la 'Alta-rendimentan NVIDIA-procesoron'.<br/><br/>"
            + "4. <strong>Konservu la ŝanĝojn:</strong><br/>"
            + "   - Apliku la ŝanĝojn kaj fermu la NVIDIA-Kontrolpanelon.<br/><br/>"
            + "5. <strong>Restartu Minecraft:</strong><br/>"
            + "   - Restartu Minecraft por ke la ŝanĝoj efektiviĝu.<br/><br/>"
            + "Se vi uzas Windows Server 2022 aŭ Windows 10, tiuj paŝoj validas se vi havas la plej novajn NVIDIA-drajvilojn instalitajn.<br/><br/>"
            + "Notu: Se vi ne povas trovi la NVIDIA-Kontrolpanelon, certigu, ke la NVIDIA-drajviloj estas ĝuste instalitaj.";
}




@Override
public String problema_con_graficas_nvidia_windows_nuevo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "Problemoj detektitaj kun NVIDIA-drajviloj en Windows 11/Server 2025 aŭ poste."
            + "</span><br/><br/>"
            + "Por certigi, ke Minecraft (kaj la nuna JVM) uzas la dediĉitan grafikan karton NVIDIA, sekvi tiujn paŝojn:<br/><br/>"
            + "1. <strong>Identigu la Java-ekzekuteblon:</strong><br/>"
            + "   - Ĉi tiu programo uzas la jenan Java-ekzekuteblon: "
            + obtenerRutaJava() + "<br/>"
            + "   - Se vi ne vidas specifan vojon, vi povas trovi la Java-ekzekuteblon per serĉado de 'java.exe' en via sistemo.<br/><br/>"
            + "2. <strong>Malfermu la Agordan Aplikaĵon:</strong><br/>"
            + "   - Premu la klavojn <code>Win + I</code> por malfermi la Agordan Aplikaĵon.<br/>"
            + "   - Navigu al <strong>Sistemo > Ekrano > Grafiko</strong>.<br/><br/>"
            + "3. <strong>Agordu la preferatan GPU-on:</strong><br/>"
            + "   - En la sekcio 'Grafiko', alklaku 'Defaŭltaj grafikaj agordoj'.<br/>"
            + "   - Elektu 'Labortablaj aplikaĵoj' kaj poste alklaku 'Foliumi'.<br/>"
            + "   - Serĉu kaj elektu la jam identigitan Java-ekzekuteblon (ekz.: 'java.exe').<br/>"
            + "   - Post aldono, elektu la Java-aplikaĵon en la listo kaj agordu ĝin por uzi 'Alta rendimento (NVIDIA)'.<br/><br/>"
            + "4. <strong>Konservu la ŝanĝojn:</strong><br/>"
            + "   - Apliku la ŝanĝojn kaj fermu la Agordan Aplikaĵon.<br/><br/>"
            + "5. <strong>Restartu Minecraft:</strong><br/>"
            + "   - Restartu Minecraft por ke la ŝanĝoj efektiviĝu.<br/><br/>"
            + "Se vi uzas Windows 11 aŭ Windows Server 2025+, tiuj paŝoj validas se vi havas la plej novajn NVIDIA-drajvilojn instalitajn.<br/><br/>"
            + "Notu: Se vi ne povas trovi la grafikagordan opcion, certigu, ke la NVIDIA-drajviloj estas ĝuste instalitaj.";
}




@Override
public String segundo60Tick() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Via Servilo aŭ Mondo havas tiko pli longaj ol 60 sekundoj. Tio povas esti pro tio, ke aldonaĵoj malrapidigas la servilon aŭ la aparataro estas tro malforta.</b>";
}



@Override
public String noTieneMemoria() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Vi ne havas sufiĉe da RAM/Memoro. Vi devas atribui pli.</b>";
}


@Override
public String theseus() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Se vi uzas Theseus/ModrinthApp, ni ne povas helpi multe ĉar Theseus ne havas lanĉilan konzolon. Theseus ankaŭ havas pliajn problemojn, inkluzive de malnovaj versioj de Mod-ŝarĝiloj, spionprogramaro, malbonaj registroj, kaj pli. La kompanio Modrinth ankaŭ ne estas honesta. Ili faras malverajn akuzojn, ke la programistoj de modoj uzas robotprogramojn por pligrandigi siajn elŝutojn kaj multfoje ŝanĝis siajn asertojn pri monumado.</b>";
}


@Override
public String noTieneConsolaDeLauncherCursedForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Vi ne havas la dosieron launcher_log.txt. Ni bezonas ĉi tiun dosieron por serĉi erarojn. Tio okazas pro la opcio \"Preterpasi lanĉilan startigon\". Malŝaltu ĝin.</b>";
}


@Override
public String faltar_de_clases_advertencia() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Averto: Mankas klasoj (averta nivelo). Kutime ne grave, sed povas okazi problemoj — malsamas de fatalaj klasaj eraroj. Oftaj kaŭzoj estas malbonaj CoreMods aŭ mankantaj dependecoj. Vi povas uzi QuickFix por serĉi modojn kun mankantaj klasoj. Mankantaj klasoj trovitaj:</b>";
}


@Override
public String noResultados() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Neniu rezulto</b>";
}

@Override
public String ubicacionesDeLogs() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>Lokoj de protokoloj:</b>";
}

@Override
public String infoDeVerificaciones() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>Jen la rezultoj de viaj kontroladoj. Ripari la suprajn partojn de la protokoloj estas la unua prioritato. Faru ĝin malrapide.</b>";
}

@Override
public String versionDeJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Bonvolu uzi Java 17 por versioj 1.17-1.20.4 kaj Java 21 por iu ajn pli nova versio. Uzu Java 8 por pli malnovaj versioj. [Gvidilo](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Se vi ankoraŭ havas problemojn, eble pro tio ke iu mod havas tro novajn aŭ malnovajn dosierojn.</b>";
}

@Override
public String java22() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java 22 kaj super ne funkcias en Minecraft-versioj sub 1.20.5 por plej multaj modŝarĝiloj pro malaktuala ASM.</b>" + versionDeJava();
}

@Override
public String javaObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java estas malmoderna </b>" + versionDeJava();
}

@Override
public String jpms_modules_faltas(String mod_necesitas, String submod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Vi bezonas la JPMS-modulon " + mod_necesitas + " el " + submod + "</b>";
}

@Override
public String null_pointer_error(String metodo, String objeto) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Ne eblas voki " + metodo + " ĉar " + objeto + " estas nula</b>";
}

@Override
public String analisisAvanzado() {
    return "Progresinta Analizo";
}


@Override
public String seleccionarCarpeta() {
    return "Elekti Dosierujon";
}

@Override
public String cadenaBusqueda() {
    return "Serĉa Ĉeno";
}

@Override
public String usarRegex() {
    return "Uzi Regulajn Esprimojn";
}

@Override
public String ignorarMayusculas() {
    return "Ignori Majusklojn";
}

@Override
public String buscar() {
    return "Serĉi";
}

@Override
public String busquedaEnProgreso() {
    return "Serĉo en Progreso";
}

@Override
public String noSeEncontraronResultados() {
    return "Neniuj Rezultoj Trovitaj";
}

@Override
public String errorBusqueda() {
    return "Eraro en Serĉo";
}

@Override
public String omitirYCerrar() {
    return "Preterpasi kaj Feri";
}

@Override
public String guardarYCerrar() {
    return "Konservi kaj Feri";
}

@Override
public String pegaLosRegistrosAqui() {
    return "Algluu la protokolojn ĉi tie";
}

@Override
public String archivo() {
    return "Dosiero";
}

@Override
public String incluir() {
    return "Inkluzivi";
}

@Override
public String abrir() {
    return "Malfermi";
}

@Override
public String endpointDeInforme() {
    return "Fina Punkto de Raporto";
}

@Override
public String sitoDeLogging() {
    return "Protokola Retpaĝaro:";
}

@Override
public String apiDeLogging() {
    return "Protokola API:";
}

@Override
public String anonimizarRegistros() {
    return "Anonimigi protokolojn (Beta)";
}

@Override
public String botonDeCompartirInforme() {
    return "Kunhavigi Raporton kaj ĉiujn elektitajn protokolojn";
}

@Override
public String arco() {
    return "Ĉi tiu dialogo ebligas vin kunhavigi protokolojn uzante la API-on de SecureLogger "
            + "ĉe securelogger.net. Kiam vi premas la butonon por kunhavigi raporton, via raporto estas sendata al "
            + "la elektita raporto-punkto (defaŭlte asbestosstar.egoism.jp) (Povas esti ŝanĝita malsupre). Vi povas kunhavigi ĉiujn elektitajn protokolojn "
            + "kune kun la raporto. Se vi ne volas alŝuti, ne uzu ĉi tiun dialogon! Ni ne prilaboras vian raporton ĉe la oficiala finpunkto (https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb); ni nur forigas malpermesitajn ligilojn. La kodo troviĝas ĉi tie: https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb. Ĉi tio estas uzata nur por montri informojn pri via kraŝo kaj la ligilon al la protokoloj. Tamen, eblas uzi propran finpunkton, kiu eble ne havas la samajn metodojn. Vi uzas la raportan retejon " 
            + Config.obtenerInstancia().obtenerSitoDeInformes() + " kaj la protokolan retejon " 
            + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". Vi ankaŭ povas kunhavigi individuajn protokolojn sen raporto per premo de la kunhavigaj butonoj apud la nomoj de la protokoloj; la protokoloj iras al la elektita protokola retejo. CrashDetector havas defaŭltan protokolan anonimigon, kiu provas forigi uzantnomojn, UUIDjn, alirajn ĵetonojn, sesiajn identigilojn, IP-adresojn kaj aliajn datumojn. Tamen, ĝi ne estas perfekta. Tamen, la aŭtoro de la modpakaĵo povas malaktivigi ĝin. Ĝi povas esti aktivigita aŭ malaktivigita per la markobutono ĉe la malsupra parto de ĉi tiu ekrano. Vi estas la posedanto de viaj datumoj; vi decidas kie alŝuti viajn datumojn. Protokolaj retejoj estas posedataj de triaj partioj, kies posedeco ofte estas kaŝita pro privateco. Vi plene respondas pri la mastrumado de viaj datumoj kaj la riskoj ligitaj al ili. La Kunhaviga Dialogo de CrashDetector estas nur interfaco, kiu ebligas vin administri tion. Gravas, ke vi estu konscia pri GDPR kaj ARCO.";
}

@Override
public String enlaceDelReporte() {
    return "Ligilo de Raporto:";
}

@Override
public String guardarConfigDeCompartir() {
    return "Konservi Kunhavigan Agordon";
}


@Override
public String registroDemasiadoGrande() {
    return "La protokolo estas tro granda por ĉi tiu protokola retejo. Bonvolu elekti alian kaj provu denove.";
}

@Override
public String errorConPublicarRegistro(String error) {
    return "Eraro dum publikigo de protokolo " + error;
}

@Override
public String apiDeRegistroNoExiste() {
    return "Protokola API ne ekzistas. Bonvolu ŝanĝi la protokolan API-on en la agordoj.";
}

@Override
public String errorSSL() {
    return "Vi havas eraron de SSL. Tio estas ofta kun malnovaj versioj de Java, "
            + "inkluzive de Java 8 versioj en la defaŭlta Minecraft-Lanĉilo kaj versioj ĉe sun.com kaj java.com. "
            + "Tio influas multajn aspektojn, kiel ekzemple la JAR-dosieroj de la instalilo de MinecraftForge, "
            + "la funkcio por kunhavigi raportojn de CrashDetector dum vi uzas la defaŭltan finpunkton, "
            + "iu modifoj kiuj bezonas interretan konekton kaj iujn protokolajn retejojn. "
            + "Se tio okazas al vi dum provo kunhavigi raporton, simple aldonu ekranbildon "
            + "kaj elektu protokolan retejon kiu kongruas kun malnovaj versioj de Java 8.";
}

@Override
public String errorJavaFMLVersion(String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Ne kongrua versio de JavaFML: necesas " + requerido 
         + ", troviĝis " + encontrado + "</b>";
}

@Override
public String errorJavaFML_MCForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "Atentu! JavaFML bezonas specifan version de Minecraft Forge</b>";
}

@Override
public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "La JAR-dosiero '" + archivoJar + "' bezonas la lingvo-provizanton '" + proveedor + "' version '"
         + requerido + "' aŭ pli novan, sed nur la versio '" + encontrado + "' estis trovita.</b>";
}

//@Override
//public String advertenciaMalwareFalso() {
//    return "<b style='color:#" + config.obtenerColorError() + "'>"
//         + "ATENTU! Crash Assistant estas falsa malprogram-detektilo. Ĝi intence blokas la lanĉon de la ludo, ignorante vian liberecon daŭrigi ludi kun la celitaj kromprogramoj. "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Vidi kodon de MalwareMod.java</a>   "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Vidi kodon de JarInJarHelper.java</a>. "
//         + "Nur ĉi tiu kromprogramo estas nuntempe sur ilia listo, kaj ili fakte nur celas la defaŭltan protokolregistran retejon, kiun la uzanto povas ŝanĝi, kaj tio nur okazas se vi elekte uzas la enigitan protokolan kunhavigan funkcion. CrashAssistant NE faras iujn ajn kontroladojn por eĉ determini kiu protokolregistra retejo estas uzata kaj ne klarigas kiel ŝanĝi ĝin (estas falmenuo ĉe la malsupra parto de la kunhaviga dialogo), kaj sendepende de la agordita retejo, CrashAssistant blokos la lanĉon de la ludo. En sia mesaĝo, ili diras fari vian propran esploradon, FARU ĜIN, rigardu la kodon de CrashDetector kaj Crash Assistant kaj komprenu kion ili faras, NE fidas al aŭtoritato.</b>";
//}

@Override
public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "La modulo '" + idMod + "' malsukcesis ĉar la bezonata klaso ne estis trovita: '"
         + claseNoEncontrada + "'. Certigu, ke ĉiuj dependecoj estas instalitaj ĝuste.</b>";
}


@Override
public String waterMediaTL() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Watermedia blokis ludi per TLauncher.</b>";
}


@Override
public String optifineObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Vi uzas version de Optifine por malaktuala versio de Minecraft. Vi devas uzi la version de Optifine por la versio de Minecraft kiun vi uzas.</b>";
}

@Override
public String servicioMLNoPudoCargar(String servicio) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Ne eblis ŝargi la servon de ModLauncher: </b>" + servicio + ".";
}

@Override
public String errorConJSONDeRegistro(String archivoJar, String recurso) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Eraro dum analizado de la JSON-dosiero '" + recurso + "' el la JAR-dosiero '" + archivoJar
         + "'. Estas problemoj kun la registrado.</b>";
}

@Override
public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
    return "<span style='color:#" + config.obtenerColorError() + "'>" 
        + "Eraro: La modulo '" + modId + "' bezonas la version '" + requerido 
        + "' aŭ pli novan de '" + dependencia + "', sed troviĝis '" + actual + "'."
        + "</span>";
}


@Override
public String gpu_no_compatible() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Via grafika procezilo (GPU) ne subtenas la bezonatan version de OpenGL por ĉi tiu ludo-versio. Ĝisdatigu viajn pelilojn aŭ ŝanĝu vian grafikan karton.</b>";
}


@Override
public String recomendacionMemoria() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "Pligrandigu la memoron asignitan al la ludo aŭ malpliigu la uzadon de kromprogramoj/kromaĵoj</b>";
}

@Override
public String error32BitMemoria() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "32-bita JVM detektita: Ne povas uzi pli ol 4GB da RAM. "
         + "Instalu 64-bitan JVM por profiti ĉiun vian disponeblan memoron</b>";
}

@Override
public String permGenError() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Kritika eraro de PermGen-memoro. Pligrandigu la konstantan memorospacon aŭ malpliigu la ŝarĝon de klasoj</b>";
}



public String errorCompatibilidadJava8() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Kongrua eraro inter Java 8 kaj modernaj versioj</b>";
}

public String errorJava9NoSoportado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 9+ ne subtenata - Uzu Java 8 por malnovaj versioj de Forge</b>";
}

public String errorJava8Requerido() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 8 bezonata (versio 52.0). Ĝisdatigu aŭ agordu ĝuste</b>";
}


@Override
public String errorDeBloqueTeselado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Kritika eraro de kongrueco: Blokoj ne subtenataj en ĉi tiu versio. "
         + "Kontrolu, ke la versioj de modoj kaj servilo estas kongruaj</b>";
}

@Override
public String errorMonitorLWJGL() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Eraro de agordado de monitoroj: "
         + "Ne eblis difini ekranan modon. "
         + "Kontrolu:</b>"
         + "<br>- Agordo de multaj monitoroj"
         + "<br>- Ĝisdatigitaj grafikaj peliloj"
         + "<br>- Sistem-subebla rezolucio";
}

@Override
public String errorOpcionesGCJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Eraro en opcioj de Java: "
         + "Konfliktantaj rubkolektaj opcioj. "
         + "Kontrolu, ke vi ne kombinas plurajn GC-algoritmojn en JVM-parametroj</b>";
}

@Override
public String errorConfigMCForge() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Kritika eraro de agordo de Forge: "
         + "Difektita aŭ nekompleta agorda dosiero. "
         + "Forigu la dosierujon 'config' kaj restartigu la ludon</b>";
}

@Override
public String problema_con_graficas_intel() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Intel HD Graphics-pelila eraro detektita. Solvoj:</b>"
         + "<br>1. Ĝisdatigu Intel-pelilojn el <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a> (minimuma versio 15.xx.xx.xx)"
         + "<br>2. En Minecraft: Opcioj → Video → Aktivigu 'Enable VBOs' kaj 'VSync'"
         + "<br>3. Atribuu 1GB-2GB da RAM al la ludo en la lanĉilo"
         + "<br>4. Ĉarmpere malaktivigu antivirusan programon/tufekranon dum ĝisdatigo";
}


public String nombre_de_faltar_de_clases_advertencia() {
    return "Averto: Mankantaj klasoj trovitaj";
}

public String nombre_de_bloque_teselado() {
    return "Eraro de blok-rendado";
}

public String nombre_de_contenido_de_stacktrace() {
    return "Analizo de stapleraroj (stack trace)";
}

public String nombre_de_cursed_consola() {
    return "Nekompleta CurseForge-konzolo";
}

public String nombre_de_early_window() {
    return "Eraro de frua fenestro (FMLEarlyWindow)";
}

public String nombre_de_drivers() {
    return "Problemoj kun grafikaj peliloj";
}

public String nombre_de_error_de_config_mcforge() {
    return "Difektita agordo de MCForge";
}

public String nombre_de_error_de_monitor_lwjgl() {
    return "Malsukceso de vidiga reĝimo (LWJGL)";
}

public String nombre_de_fabricmc_runtime_error_provided_by() {
    return "Eraro de komencigo de FabricMC";
}

public String nombre_de_falta_module_jmps() {
    return "Mankantaj JPMS-moduloj";
}

public String nombre_de_faltar_de_clases() {
    return "Grave mankantaj klasoj";
}

public String nombre_de_faltas_dependencias_de_modlauncher() {
    return "Mankantaj dependecoj de ModLauncher";
}

public String nombre_de_java_versiones() {
    return "Nekongruaj versioj de Java";
}

public String nombre_de_faltar_de_kubejs_resourcepack() {
    return "Eraro de KubeJS-rimedo";
}

public String nombre_de_lenguaje_proveedor_check() {
    return "Nekongrua lingvo-provizanto";
}

public String nombre_de_faltar_de_liyhostictchctov() {
    return "Eraro de Litchhost";
}

public String nombre_de_malware_falso_crash_assistant() {
    return "Malvera detekto de malprogramoj";
}

public String nombre_de_mcforge_mod_sespechoso() {
    return "Trovo de suspekta modifo";
}

public String nombre_de_mods_duplicados_modlauncher() {
    return "Duoblaj modifoj en ModLauncher";
}

public String nombre_de_modules_duplicados_jmps() {
    return "Konfliktoj de JPMS-moduloj";
}

public String nombre_de_necesitas_sodium() {
    return "Iris bezonas Sodium";
}

public String nombre_de_no_puede_analizar_json_de_registro() {
    return "Ne eblas analizi la JSON-registron";
}

public String nombre_de_no_tiene_memoria() {
    return "Memoro ne sufiĉas";
}

public String nombre_de_null_pointer() {
    return "Eraro de nuligita indiko (NullPointerException)";
}

public String nombre_de_opciones_java_gc_invalidas() {
    return "Nevalidaj opcioj de Java GC";
}

public String nombre_de_optifine_obsoleta() {
    return "Malaktuala/nekonforma OptiFine";
}

public String nombre_de_60_segundo_trick() {
    return "Kritika servila tiko (60 sekundoj)";
}

public String nombre_de_servicio_de_modlauncher_no_funciona() {
    return "Malsukceso de ModLauncher-servoj";
}

public String nombre_de_spongemixin_configs_problematicos() {
    return "Problemaj SpongeMixing-agordoj";
}

public String nombre_de_theseus() {
    return "Theseus ne konformas";
}

public String nombre_de_watermedia_tl() {
    return "TLauncher ne estas subtenata de WATERMeDIA";
}


@Override
public String auditorias_transformer() {
    return "Transformaj Kontroloj";
}

@Override
public String auditorias_transformer_detectadas() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Jen estas la rezultoj de la enhavo de la Transformaj Kontroloj en la Vanilla-Lanĉilo. Ĝenerale, ĝi estas malpli preciza ol la StackTrace-analizilo, sed la Vanilla-Lanĉilo ne ĉiam havas enhavon por {}</b>";
}

@Override
public String descripcionEscanerMCreator() {
    return "Ĉi tio kontrolas viajn modifojn por trovi tiujn kreitajn per MCreator. Kvankam la plejparto de la modifoj faritaj per MCreator estas bonaj kaj ekzistas multaj grandaj modifoj kreitaj per MCreator, foje ili havas problemojn kaj malbonan reputacion. Ĉi tio helpas ilin identigi. Rimarku, ke eĉ la tre alte valorigitaj povas fakte ne esti de MCreator; ekzemple, ili povas havi integriĝon kun MCreator.";
}

@Override
public String escanear() {
    return "Skani";
}

@Override
public String cargando() {
    return "Ŝarĝante";
}

@Override
public String codigo() {
	// TODO Auto-generated method stub
	return "eo";
}

@Override
public String inicioApp() {
    return "Komenco de Ludo/Aplikaĵo";
}

@Override
public String ajustesCrashDetector() {
    return "Agordoj de CrashDetector";
}

@Override
public String confidencialidad() {
    return "Privateco";
}

@Override
public String tooltip() {
    return "Ilustro";
}

@Override
public String config() {
    return "Agordo";
}

@Override
public String abrirCarpeta() {
    return "Malfermi Dosierujon";
}

@Override
public String actualizar() {
    return "Ĝisdatigi";
}

@Override
public String anadirRegistro() {
    return "Aldoni Registradon";
}

@Override
public String usarIdiomaDelSistema() {
    return "Uzi sistem-lingvon";
}

@Override
public String volver() {
    return "Reiri";
}

@Override
public String colorFondo() {
    return "Fona koloro (#RRGGBB):";
}

@Override
public String colorTexto() {
    return "Teksta koloro (#RRGGBB):";
}

@Override
public String colorBoton() {
    return "Butona koloro (#RRGGBB):";
}

@Override
public String colorCajaTexto() {
    return "Tekstokesta koloro (#RRGGBB):";
}

@Override
public String colorEnlace() {
    return "Ligila koloro (#RRGGBB):";
}

@Override
public String colorTitulosConsolas() {
    return "Konsoltitola koloro (#RRGGBB):";
}

@Override
public String colorError() {
    return "Erara koloro (#RRGGBB):";
}

@Override
public String colorAdvertencia() {
    return "Averto-koloro (#RRGGBB):";
}

@Override
public String colorInfo() {
    return "Informa koloro (#RRGGBB):";
}

@Override
public String colorTitulo() {
    return "Titola koloro (#RRGGBB):";
}

@Override
public String colorEnlaceTexto() {
    return "Ligila teksta koloro (#RRGGBB):";
}

@Override
public String transformacionDeMinecraftCodigo0() {
    return "Malfermu nur CrashDetector dum malsukceso";
}

@Override
public String activar_parche() {
    return "Aktivigi flikon";
}

@Override
public String noHaySolucionDisponible() {
    return "Neniu solvo disponeblas";
}

@Override
public String error() {
    return "eraro";
}

@Override
public String error_al_eliminar_jar() {
    return "Eraro dum forigo de Jar";
}

@Override
public String jar_eliminado_exitosamente() {
    return "Jar sukcese forigita";
}

@Override
public String exito() {
    return "sukceso";
}

@Override
public String eliminar() {
    return "forigi";
}

@Override
public String error_al_eliminar_paquete() {
    return "Eraro dum forigo de pakaĵo";
}

@Override
public String paquete_eliminado_exitosamente() {
    return "Pakaĵo sukcese forigita";
}

@Override
public String eliminar_paquete() {
    return "Forigi pakaĵon";
}

@Override
public String no_se_encontraron_mods_con_paquete() {
    return "Neniu mod kun pakaĵo trovita";
}

@Override
public String no_se_puede_eliminar_paquete() {
    return "Ne eblas forigi la pakaĵon";
}

@Override
public String eliminar_jar() {
    return "Forigi Jar";
}

@Override
public String no_se_encontraron_mods_duplicados() {
    return "Neniu duobligita mod trovita";
}

@Override
public String archivo_no_encontrado() {
    return "Dosiero ne trovita";
}

@Override
public String directorio_eliminado() {
    return "Dosierujo forigita";
}

@Override
public String error_al_eliminar_jar_anidado() {
    return "Eraro dum forigo de enigita Jar";
}

@Override
public String archivo_interno_no_encontrado() {
    return "Interna dosiero ne trovita";
}

@Override
public String archivo_eliminado() {
    return "dosiero forigita";
}

@Override
public String error_al_eliminar_archivo() {
    return "eraro dum forigo de dosiero";
}

@Override
public String archivo_externo_no_valido() {
    return "nevalida ekstera dosiero";
}

@Override
public String elemento_mod_eliminado() {
    return "Mod-elemento forigita";
}

@Override
public String error_al_reemplazar_jar_externo() {
    return "Eraro dum anstataŭigo de ekstera Jar";
}

@Override
public String error_al_eliminar_elemento_mod() {
    return "eraro dum forigo de mod-elemento";
}

@Override
public String error_al_eliminar_directorio() {
    return "eraro dum forigo de dosierujo";
}

@Override
public String formato_invalido_para_jar_anidado() {
    return "nevalida formato por enigita Jar";
}

@Override
public String jar_anidado_eliminado() {
    return "enigita Jar forigita";
}

@Override
public String error_al_limpiar_temporales() {
    return "eraro dum purigo de provizoraj dosieroj";
}


@Override
public String mensaje_de_trace_fatal_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Fina fatale spurmesaĝo (Ne tradukita):</b> ";
}

@Override
public String mensaje_de_trace_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Fina spurmesaĝo (Ne tradukita):</b> ";
}

@Override
public String solucionParaAdvertenciaFaltasClases() {
    return "Vi povas serĉi en la datumbazo WaifuNeoForge por trovi kromprogramojn. Elektu la ludversion, modŝarĝilon kaj klason. Uzu la plej similan kombinaĵon. Vi povas serĉi nur unufoje en unu minuto.";
}

@Override
public String solucionFaltasClases() {
    return "Vi povas serĉi en la datumbazo WaifuNeoForge por trovi kromprogramojn. Elektu la ludversion, modŝarĝilon kaj klason. Uzu la plej similan kombinaĵon. Vi povas serĉi nur unufoje en unu minuto.";
}

@Override
public String solucionParaJavaInstallar() {
    return "Ambaŭ lanĉiloj havas la ĝustajn versiojn de Java, sed ne ĉiujn; vi povas instali la ĝustan version de Java per la pakaĵadministrilo en via sistemo aŭ per la butonoj.";
}

@Override
public String error_animacion_no_encontrada() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Modo kun Mankanta Animo: " + "</b>";
}

@Override
public String nombre_de_error_animacion_minecraft() {
    return "NoSuchElementException (Eraro pri Mankanta Elemento) Mankas Animo";
}

@Override
public String no_se_encontraron_mods_para_eliminar() {
    return "Ne trovis modojn por forigi";
}

@Override
public String opcionesGCInvalidas() {
    return "Anstataŭi konfliktantajn GC-opciojn per -XX:+UseG1GC";
}

@Override
public String aumentarMemoriaHeap() {
    return "Pligrandigu la grandon de la heap-memoro uzante la opcio -Xmx.";
}

@Override
public String aumentarMemoriaPermgen() {
    return "Pligrandigu la grandon de la permgen-memoro uzante la opcio -XX:MaxPermSize.";
}

@Override
public String utilizarJVM64Bits() {
    return "Uzu 64-bitan JVM por pliigi la haveblan memoron.";
}

@Override
public String optimizarCodigo() {
    return "Optimizu la kodon por malpliigi la memoruzadon kaj plibonigi la efikecon.";
}

@Override
public String utilizarRecolectorBasuraEficiente() {
    return "Uzu efikan rubobruligon por malpliigi la paŭzojn en la aplikaĵo.";
}

@Override
public String modulos() {
    return "Moduloj";
}

@Override
public String paquete() {
    return "Pakaĵo";
}

@Override
public String solucionRegistrosMalMapeados() {
    return "Manqas kelkaj ID-oj. Komunaj kialoj estas mankantaj modoj aŭ datumoj pri objektoj. Komunaj datumbibliotekoj estas datafiedcontents/ kaj kubejs/ aŭ aliaj mod-bibliotekoj.";
}

@Override
public String nombre_de_registros_mal_mapeados() {
    return "malbone mapitaj registriĝoj";
}


public String mensajeCierreAuthMe() {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo 'AuthMe' ne sukcesis ŝargi kaj haltigis la servilon.</b> ";
}

public String nombreProblemaCierreAuthMe() {
    return "Problemo pri haltigo pro AuthMe";
}

public String solucionCierreAuthMe() {
    return "La regulo 'stopServer' ŝanĝis al 'true'.";
}

public String solucionConfigurarPluginAuthMe() {
    return "Agordu la kromprogramon AuthMe (plugins/AuthMe/config.yml)";
}

public String solucionInstalarVersionDiferenteAuthMe() {
    return "Instalu alian version de la kromprogramo 'AuthMe'";
}

public String solucionEliminarPluginAuthMe() {
    return "Forigu la kromprogramon 'AuthMe'";
}

@Override
public String mensajeProblemaCargaMultiverso(String nombreMundo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La mondo '" + nombreMundo + "' ne povis ŝargi ĉar ĝi enhavas erarojn kaj verŝajne estas difektita.</b> ";
}

@Override
public String nombreProblemaCargaMultiverso() {
    return "Problemeco pri ŝarĝo de Multiverse-mondo";
}

@Override
public String solucionRepararMundo(String nombreMundo) {
    return "Riparu la mondo '" + nombreMundo + "', ekzemple per Minecraft Region Fixer aŭ MCEdit.";
}

@Override
public String solucionEliminarCarpetaMundo(String nombreMundo) {
    return "Forigu la dosierujon de la mondo '" + nombreMundo + "'.";
}

@Override
public String mensajeConfiguracionPermissionsEx() {
    return "<b style='color:#" + config.obtenerColorError() + "'>La agordo de la kromprogramo 'PermissionsEx' estas nesprava.</b> ";
}

@Override
public String nombreProblemaConfiguracionPermissionsEx() {
    return "Agordoproblemo de PermissionsEx";
}

@Override
public String solucionConfigurarPermissionsEx() {
    return "Agordu la kromprogramon PermissionsEx (plugins/PermissionsEx/permissions.yml)";
}

@Override
public String solucionEliminarPluginPermissionsEx() {
    return "Forigu la kromprogramon 'PermissionsEx'";
}

@Override
public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Estas pluraj kromprogramdosieroj kun la nomo '" + nombrePlugin + "': '" + primerPath + "' kaj '" + segundoPath + "'.</b> ";
}

@Override
public String nombreProblemaNombrePluginAmbiguo() {
    return "Nepreciza kromprogramnomo";
}

@Override
public String solucionEliminarPlugin(String nombrePlugin) {
    return "Forigu la kromprogramon '" + nombrePlugin + "'";
}

@Override
public String mensajeCargaChunk() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Okazis eraro dum la mondo ŝargis la chunk-ojn.</b> ";
}

@Override
public String nombreProblemaCargaChunk() {
    return "Eraro dum ŝarĝado de chunk-oj";
}

@Override
public String solucionEliminarChunk() {
    return "Forigu la probleman chunk-on de la mondo, ekzemple per MCEdit aŭ forigante la regionan dosieron.";
}


@Override
public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin + "' ne povas plenumi la komandon '/" + comando + "'.</b> ";
}

@Override
public String nombreProblemaExcepcionComandoPlugin() {
    return "Eraro dum plenumo de kromprogramkomando";
}

@Override
public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
    return "Instalu diferenan version de la kromprogramo '" + nombrePlugin + "'";
}

@Override
public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin + 
           "' bezonas la dependecon '" + dependencia + "'.</b> ";
}

@Override
public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
    StringBuilder deps = new StringBuilder();
    for (int i = 0; i < dependencias.size(); i++) {
        if (i > 0) deps.append(", ");
        deps.append("'").append(dependencias.get(i)).append("'");
    }
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin + 
           "' mankas jenajn dependecojn: " + deps.toString() + ".</b> ";
}

@Override
public String nombreProblemaDependenciaPlugin() {
    return "Mankanta kromprogramdependeco";
}

@Override
public String solucionInstalarPlugin(String nombrePlugin) {
    return "Instalu la kromprogramon '" + nombrePlugin + "'";
}

@Override
public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin + 
           "' postulas API-version '" + versionAPI + "' kiu ne estas kongrua kun la nuna servilo.</b> ";
}

@Override
public String nombreProblemaVersionAPIIncompatible() {
    return "Nekongrua API-versio";
}

@Override
public String solucionInstalarVersionServidor(String version) {
    return "Instalu version '" + version + "' de via servila programaro.";
}

@Override
public String mensajeMundoDuplicado(String nombreMundo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La mondo '" + nombreMundo + 
           "' estas duplikato de alia mondo kaj ne povas esti ŝargita.</b> ";
}

@Override
public String nombreProblemaMundoDuplicado() {
    return "Duplikata mondo";
}

@Override
public String solucionEliminarUID(String nombreMundo) {
    return "Forigu la dosieron 'uid.dat' en la mondo '" + nombreMundo + "'";
}

@Override
public String solucionEliminarMundo(String nombreMundo) {
    return "Forigu la mondodossieron '" + nombreMundo + "'";
}

@Override
public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
    String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
    return "<b style='color:#" + config.obtenerColorError() + "'>La bloka ento '" + nombre + 
           "' de tipo '" + tipo + "' je la koordinatoj " + coords + " kaŭzas erarojn dum takto.</b> ";
}

@Override
public String nombreProblemaTickingEntidadBloque() {
    return "Erara bloka ento";
}

@Override
public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
    String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
    return "Forigu la enton '" + nombre + "' je la koordinatoj " + coords + " per MCEdit aŭ rekte en la mondo.";
}

@Override
public String mensajeModDuplicadoFabric(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod + "' havas plurajn versiojn instalitajn.</b> ";
}

@Override
public String nombreProblemaModDuplicadoFabric() {
    return "Duplikita kromprogramo en Fabric";
}

@Override
public String solucionEliminarModDuplicado(String rutaMod) {
    return "Forigu la duplikitan kromprogramdosieron: " + rutaMod;
}

@Override
public String mensajeModIncompatible(String primerMod, String segundoMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramoj '" + primerMod + 
           "' kaj '" + segundoMod + "' ne estas reciproke kongruaj.</b> ";
}

@Override
public String nombreProblemaModIncompatibleFabric() {
    return "Nekongrua kromprogramo en Fabric";
}

@Override
public String solucionEliminarMod(String nombreMod) {
    return "Forigu la kromprogramon '" + nombreMod + "'";
}

@Override
public String mensajeModFatal(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod + "' havas gravan eraron kaj ne povas ruli.</b> ";
}

@Override
public String nombreProblemaModFatal() {
    return "Kromprogramo kun grava eraro";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Jenaj kromprogramoj estas bezonataj sed mankas: " + deps.toString() + ".</b>";
}

@Override
public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
    if (version == null || version.isEmpty()) {
        return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod + 
               "' postulas la kromprogramon '" + dependencia + "' kiel dependecon.</b>";
    } else {
        return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod + 
               "' postulas la kromprogramon '" + dependencia + "' en versio " + version + ".</b>";
    }
}

@Override
public String nombreProblemaDependenciaMod() {
    return "Mankanta kromprogramdependeco";
}

@Override
public String solucionInstalarMod(String nombreMod) {
    return "Instalu la kromprogramon '" + nombreMod + "'";
}

@Override
public String solucionInstalarModConVersion(String nombreMod, String version) {
    return "Instalu la kromprogramon '" + nombreMod + "' kun versio " + version;
}


@Override
public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin + 
           "' ne kongruas kun la regiona takto de Folia.</b> ";
}

@Override
public String mensajePluginTickingRegionalPlural(List<String> plugins) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Jenaj kromprogramoj ne kongruas kun la regiona takto de Folia: ");
    
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
    return "Kromprogramo nekongrua kun regiona takto";
}

@Override
public String solucionInstalarSoftwareSinTickingRegional(String software) {
    return "Instalu version sen regiona takto, ekzemple " + software;
}

@Override
public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Manko de mod '" + nombreMod + 
           "' en la datumasko.</b>";
}

@Override
public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Jenaj modoj mankas en la datumasko: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" kaj ");
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
    return "Modo mankanta en datumasko";
}

@Override
public String mensajeModExcepcionSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod + "' kaŭzis eraron.</b>";
}

@Override
public String mensajeModExcepcionPlural(List<String> mods) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Jenaj kromprogramoj kaŭzis erarojn: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" kaj ");
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
    return "Forge-kromprogramo kun Eraro";
}

@Override
public String solucionInstalarVersionDiferenteMod(String nombreMod) {
    return "Instalu alian version de la kromprogramo '" + nombreMod + "'";
}

@Override
public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod + 
           "' ne estas kongrua kun Minecraft versio " + versionMC + ".</b>";
}

@Override
public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Jenaj kromprogramoj ne estas kongruaj kun iliaj Minecraft-versioj: ");
    
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
    return "Ne kongrua kromprogramo kun Minecraft";
}

@Override
public String solucionInstalarVersionForge(String versionMC) {
    return "Instalu Forge-version kongruan kun Minecraft " + versionMC;
}

@Override
public String mensajeDependenciaModFaltante(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod + "' mankas kaj necesas por ŝargi la mondon aŭ kromprogramon.</b>";
}

@Override
public String nombreProblemaDependenciaModFaltante() {
    return "Mankanta kromprogramo";
}

@Override
public String mensajeWorldModFaltanteSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La mondo estis konservita kun kromprogramo '" + nombreMod + 
           "', kiu nun mankas.</b>";
}

@Override
public String mensajeWorldModFaltantePlural(List<String> mods) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("La mondo estis konservita kun jenaj kromprogramoj, kiuj nun mankas: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" kaj ");
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
    return "Mankanta kromprogramo en la mondo";
}

@Override
public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La mondo estis konservita kun kromprogramo '" + nombreMod + 
           "' versio " + versionEsperada + ", sed nun estas uzata versio " + versionActual + ".</b>";
}

@Override
public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas, List<String> versionesActuales) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Jenaj kromprogramoj havas versiodiferencojn en la konservita mondo: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" kaj ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
        sb.append(" (Konservita: ").append(versionesEsperadas.get(i)).append(", Nuntempa: ").append(versionesActuales.get(i)).append(")");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

@Override
public String nombreProblemaVersionModMundo() {
    return "Versia nekongruo de kromprogramo en konservita mondo";
}

@Override
public String mensajeVersionDowngrade() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Vi provis ŝargi mondon kreitan per pli nova versio de Minecraft.</b>";
}

@Override
public String nombreProblemaVersionDowngrade() {
    return "Klopodo ŝargi mondon el pli nova versio";
}

@Override
public String solucionVersionDowngrade() {
    return "Instalu pli novan version de Minecraft.";
}

@Override
public String solucionGenerarNuevoMundo() {
    return "Kreu novan mondon.";
}

@Override
public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin + 
           "' postulas jenan dependecon: '" + dependencia + "'.</b>";
}

@Override
public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Jenaj kromprogramoj postulas neinstalitajn dependecojn: ");
    
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
    return "Kromprogramo kun mankanta dependeco";
}

@Override
public String mensajePluginIncompatibleSingular(String nombrePlugin) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin + "' ne estas kongrua kun la nuna servila versio.</b>";
}

@Override
public String mensajePluginIncompatiblePlural(List<String> plugins) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Jenaj kromprogramoj ne estas kongruaj kun la nuna servila versio: ");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            if (i == plugins.size() - 1) {
                sb.append(" kaj ");
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
    return "Nekongrua kromprogramo kun PocketMine-MP";
}

@Override
public String mensajePluginEjecucionSingular(String nombrePlugin) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin + "' kaŭzis eraron dum plenumo.</b>";
}

@Override
public String mensajePluginEjecucionPlural(List<String> plugins) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Jenaj kromprogramoj kaŭzis erarojn dum plenumo: ");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            if (i == plugins.size() - 1) {
                sb.append(" kaj ");
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
    return "Kromprogramo kun rultempa eraro";
}

@Override
public String nombreLegacyRandomSourceMultiHilos() {
    return "Multobla hilo en LegacyRandomSource";
}

@Override
public String mensajeLegacyRandomSourceMultiHilos() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Kelkaj hiloj aliras la klason LegacyRandomSource samtempe. Por pluaj informoj, uzu la modon 'Unsafe World Random Access Detector' aŭ 'C2ME'.</b>";
}

@Override
public String desdeUltimoExito() {
    return "Ekde lasta sukceso";
}

@Override
public String noHayCambios() {
    return "Neniu ŝanĝo";
}

@Override
public String desdeUltimoIntento() {
    return "Ekde lasta provo";
}

@Override
public String fallo() {
    return "Malsukcesis";
}

@Override
public String diferentesDeLasMods() {
    return "Malsama de la kromprogramoj";
}

@Override
public String historialDeMods() {
    return "Mod-historio";
}

@Override
public String archivo0() {
    return "Dosiero0";
}

@Override
public String archivo1() {
    return "Dosiero1";
}

@Override
public String comparar() {
    return "Kompari";
}

@Override
public String seleccionarDosArchivos() {
    return "Elektu du dosierojn";
}

@Override
public String archivoExito() {
    return "Sukcesa dosiero";
}

@Override
public String archivoFalla() {
    return "Fiaska dosiero";
}

@Override
public String errorComparandoArchivos() {
    return "Eraro dum komparo de dosieroj";
}

@Override
public String comparando() {
    return "Komparante";
}

@Override
public String con() {
    return "kun";
}

@Override
public String descripcionPanelHistoriaMods() {
    return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
        + "<p><b>Panelo por MOD-historia komparo</b></p>"
        + "<p>Tiu ĉi panelo ebligas al vi kompari du listojn de MOD-oj el diversaj sesioj. Elektu unu dosieron el la maldekstra kolumno kaj alian el la dekstra por vidi ŝanĝojn inter ambaŭ versioj.</p>"
        
        + "<h3>Kiel uzi:</h3>"
        + "<ol>"
        + "<li><b>Dosierselektado:</b> Klaku la radiobutonojn apud la dosiernomoj. Dosieroj finitaj per <span style='color: #4CAF50; font-weight: bold;'>.exito</span> indikas sukcesajn sesiojn, dum tiuj kun <span style='color: #F44336; font-weight: bold;'>.falla</span> montras fiaskojn.</li>"
        
        + "<li><b>Aŭtomata komparo:</b> Premante butonon 'Kompari', la sistemo analizos la du listojn kaj montramos kiujn MOD-ojn oni aldonis (+) aŭ forigis (-).</li>"
        
        + "<li><b>Rezultmontrado:</b> La rezultoj estas prezentitaj en HTML-kolora formato: "
        + "<ul>"
        + "<li><span style='color: green;'>+ Aldonita MOD</span></li>"
        + "<li><span style='color: red;'>- Forigita MOD</span></li>"
        + "</ul></li>"
        + "</ol>"
        
        + "<h3>Ĉefaj funkcioj:</h3>"
        + "<ul>"
        + "<li>Subtenas ajnan kombinaĵon de sukcesaj/malsukcesaj dosieroj.</li>"
        + "<li>Bidirektaj diferencoj por preciza sekvo de ŝanĝoj.</li>"
        + "<li>Rulumilo inkluzive por longaj MOD-listoj.</li>"
        + "<li>Integriĝas kun klarigaj bildoj por plibonigi vidan komprenon.</li>"
        + "</ul>"
        
        + "<p>Evoluinta kun <3️ por helpi vin sekvi ŝanĝojn en via agordo.</p>"
        + "</body></html>";
}

@Override
public String tieneErrorIPV6() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
           + "Eblas, ke vi havas problemon rilatantan al IPv6. "
           + "Estas du solvoj: "
           + "1) Aldonu la JVM-argumenton <code>-Djava.net.preferIPv4Stack=true</code> al via lanĉilo, aŭ "
           + "2) Uzu la butonon 'QuickFix' en CrashDetector por apliki korekton, kiu aŭtomate ŝaltas tiun agordon."
           + "</b>";
}

@Override
public String parcheIPv4() {
    return "IPv4/6 Korekto";
}

@Override
public String carpetaHMCL() {
    return "HMCL Dosierujo (Nur por HelloMineCraftLauncher)";
}

@Override
public String descripcionCurseforge() {
    return "Noto: Ne estas kreita loko se aktivigas \"Preterlasi Lanĉilon\" en Agordoj > Minecraft";
}

@Override
public String descripcionPrism() {
    return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/Derivaĵoj: Alklaku per dekstra butono sur la instanco kaj elektu \"Redakti Instancon\". En la malfermita fenestro serĉu sekcion \"Minecraft-a loko\" aŭ similan.<br>" +
           "Ĉi tiuj lokoj enhavas norman eliron (STDOUT), kiu gravas por diagnozi erarojn.";
}

@Override
public String descripcionHMCL() {
    return "HMCL (HelloMinecraftLauncher): Devus elekti la dosierujon kie HMCL estas instalita kaj trovi la dosierujon \".hmcl\". La HMCL-lokoj estas konservata tie kaj enhavas gravajn informojn pri eraroj.<br>";
}

@Override
public String descripcionFenix() {
    return "LauncherFenix: La lanĉilo havas fakon por evoluo montranta detalan lokon. Ĝin trovu en la agorda menuo de la lanĉilo.";
}

@Override
public String descripcionATLauncher() {
    return "ATLauncher: Ekzistas elireta fenestro kun lokoj. Ĝi havas butonojn por kopii kaj alŝuti ilin. Lokoj estas generitaj aŭtomate dum ludo kaj enhavas gravajn diagnozajn informojn.";
}

@Override
public String descripcionGDLauncher() {
    return "GDLauncher: Dekstre klaku sur la instanco kaj elektu \"Agordoj\". Poste iru al la loko-sekcio por vidi gravan informon el la norma eliro (STDOUT).";
}

@Override
public String descripcionLinksMarkdown() {
    return "Markdown-ligiloj: Algluu ajnan Markdown-formatan ligilon al loko-tiedoj. La sistemo provos aŭtomate ekstrakti lokajn ligilojn (latest.log, launcher_log.txt, debug.log ktp.) kaj analizi ilin.";
}

@Override
public String noRegistroLauncherTitulo() {
    return "Loko de Lanĉilo ne trovita";
}

@Override
public String imagenNoEncontrada() {
    return "Bildo ne trovita";
}

@Override
public String noRegistroDeLauncher() {
    return "ĜENERALA: Elektu la tipon de lanĉilo kiun vi uzas. La loko-dosieroj de lanĉilo (launcher_log.txt, stdout, ktp.) enhavas gravajn erarinformojn, kiujn latest.log ne montras. CrashDetector ne povas legi viajn loko-dosierojn — eble mankas loko-dosiero kaj vi devas alglui la lokon.<br>" +
           "Por pli da informo, vidu <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">tiun problemon</a>. Ĉi tiuj lokoj enhavas la norman eliron (STDOUT) necesa por diagnozi multajn erartipojn.";
}

@Override
public String faltar_de_clases_create() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Mankas klaso(j) el Create. Create multe ŝanĝis — multaj klasoj estas forigitaj. Ĉefe ekde Create 6 (februaro 2025), aldonaĵoj por pli fruaj versioj ne plu funkcias. QuickFix ne havas solvon por ĉi tiu problemo. Vi devas ĝisdatigi viajn Create-aldonaĵojn, forigi malnovajn aŭ uzi la ĝustan Create-version por viaj dezirataj aldonaĵoj.</b>";
}

@Override
public String faltar_de_clases_epicfight() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Mankas klaso(j) el EpicFight. EpicFight multe ŝanĝis — multaj klasoj estas forigitaj. QuickFix ne havas solvon por ĉi tiu problemo. Vi devas ĝisdatigi viajn EpicFight-aldonaĵojn, forigi malnovajn aŭ uzi la ĝustan EpicFight-version por viaj dezirataj aldonaĵoj.</b>";
}

@Override
public String openJ9NoSoportado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>OpenJ9 を使用していますが、このアプリケーションは OpenJ9 をサポートしていません。多くのアプリ（このアプリも含む）は JVM 実装の違いにより OpenJ9 に対応していません。QuickFix ではこの問題を自動的に解決できません。Oracle JDK、OpenJDK Hotspot、またはその他の推奨代替 JDK をインストールする必要があります。</b>";
}

@Override
public String necesitasJDK11() {
    return "<b style='color:#" + config.obtenerColorError() + "'>このアプリのバージョンは正常に動作するために Java 11 (JDK 11) が必要です。現在、互換性のない古いバージョンの Java を使用しています。QuickFix では Java のアップグレードは自動的に行えません。解決策に記載されたリンクから JDK 11 またはそれ以上の互換性のあるバージョンをダウンロード・インストールする必要があります。</b>";
}

@Override
public String memoriaExcesiva() {
    return "<b style='color:#" + config.obtenerColorError() + "'>メモリを過剰に割り当てており、システムリソースが不足しています。これは、システムの実装容量を超える RAM を指定した場合や、大容量メモリを扱えない 32 ビット JVM を使用している場合に発生します。</b>";
}

@Override
public String recomendacionMemoriaExcesiva() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>この問題を解決するには、アプリに割り当てるメモリ量を減らしてください。推奨される最大値はシステムによりますが、一般的に全 RAM の 70～80％を超えないようにしてください。32 ビット JVM を使用している場合、物理 RAM の量に関係なく、上限は約 2～3 GB です。</b>";
}

@Override
public String disminuirMemoriaHeap() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>アプリに割り当てられたヒープメモリを減らすには、ランチャーの設定を開き、RAM オプションを探します。最大値 (Xmx) を適切な量に下げてください。例：8GB の RAM がある場合、3～4GB を割り当てます。16GB の場合、6～8GB が適切です。OS や他のプログラムのために十分なメモリを残すことを忘れないでください。</b>";
}


@Override
public String forgeArchivosFaltantes(String archivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Famas gravaj dosieroj de Forge. La dosiero '" + archivo + "' ne troviĝas en via instalo. Tio okazas kutime se la instalo de Forge ĉesis aŭ gravaj dosieroj forviŝiĝis. QuickFix ne povas denove trovi tiujn dosierojn aŭtomate. Vi devas reinstali Forge per la oficiala instalejo.</b>";
}

@Override
public String forgeVersionNoEncontrada(String version, String archivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Forge ne povas trovi la bezonatan version de Minecraft. Versio " + version + " estas bezonata sed ne troviĝas en dosiero '" + archivo + "'. Tio okazas se ekzistas nekongruo inter via Minecraft-versio kaj via Forge-versio. Certiĝu, ke vi elŝutis la ĝustan Forge-version por via Minecraft-versio.</b>";
}

@Override
public String forgeTargetFmlclientNoEncontrado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>La celo 'fmlclient' bezonata por lanĉi Forge ne troviĝas. Tio indikas nekompletan aŭ difektitan Forge-instalon. Eble gravaj dosieroj ne ĝuste instaliĝis. Vi devas reinstali Forge per la oficiala instalejo.</b>";
}

@Override
public String forgeClaseMinecraftFaltante() {
    return "<b style='color:#" + config.obtenerColorError() + "'>La ĉefa Minecraft-klaso ne troviĝas en la klasŝarĝilo. Tio kutime indikas nekompletan Forge-instalon aŭ konflikton kun aliaj kromprogramoj. Eble dosieroj de Minecraft difektiĝis dum la instalo. Vi devas reinstali Forge ĝuste.</b>";
}

@Override
public String forgeInstallacionNoCompleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>La Forge-instalo ne estas plena. Tio povas okazi pro interrompita instalo, forviŝitaj dosieroj aŭ nekongruo kun via Minecraft-versio. Forge bezonas specifajn dosierojn por funkcii, sed iuj el ili mankas nun.</b>";
}

@Override
public String nombre_de_forge_instalacion_no_completa() {
    return "Nekompleta Forge-instalo";
}

@Override
public String solucion_para_forge_instalacion_no_completa() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Por solvi tion, reinstalu Forge ĝuste. Certiĝu pri la ĝusta versio por via Minecraft-versio kaj plenumu la instalprocezon sen interrompo.</b>";
}

@Override
public String descargar_forge_oficial() {
    return "Elŝuti Forge Oficiale";
}

@Override
public String reinstalar_forge_correctamente() {
    return "Kiel Reininstali Forge Ĝuste";
}

@Override
public String instrucciones_reinstalar_forge() {
    return "<html><body style='width: 500px;'>" +
           "<h3 style='color:#" + config.obtenerColorTitulo() + "'>Instrukcioj por Reininstali Forge:</h3>" +
           "<ol>" +
           "<li>Elŝutu la ĝustan Forge-instalejon el la oficiala retejo (rekomendita versio por via Minecraft)</li>" +
           "<li>Fermu vian Minecraft-lanĉilon tute</li>" +
           "<li>Ĉerpu la Forge-instalejon kiel administranto</li>" +
           "<li>Elektu 'Installer' (ne 'Installer (run client)')</li>" +
           "<li>Elektu vian Minecraft-profilan dosierujon en la lanĉilo</li>" +
           "<li>Premu 'OK' kaj atendu plenan instalon</li>" +
           "<li>Restartigu vian lanĉilon kaj kontrolu ke Forge aperas en la profila listo</li>" +
           "</ol>" +
           "<p><b>Grava:</b> Se vi uzas ĉefan lanĉilon, certiĝu ke vi elektis la ĝustan profilan dosierujon.</p>" +
           "</body></html>";
}

@Override
public String titulo_instrucciones_reinstaler_mcforge() {
    return "Instrukcioj por Reininstali Forge";
}


@Override
public String error_enlace_insatisfecho(String nombreBiblioteca) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Eraro pri nesolvita ligilo: Malsukcesis ŝargi bibliotekon " + nombreBiblioteca + ". Eblaj solvoj:<br/><br/>" +
           "a) Aldonu la dosierujon kun la komuna biblioteko al -Djava.library.path aŭ -Dorg.lwjgl.librarypath.<br/>" +
           "b) Aldonu la JAR-dosieron kun la komuna biblioteko al la classpath.<br/><br/>" +
           "Tiu eraro okazas kiam Minecraft ne povas trovi gravajn dosierojn por sia funkciado. " +
           "Ĝi kutime okazas pro neprofunda instalo aŭ permesaj problemoj de la sistemo.</b>";
}

@Override
public String nombre_de_error_enlace_insatisfecho() {
    return "Eraro pri nesolvita ligilo";
}

@Override
public String solucion_para_error_enlace_insatisfecho() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Malsukcesis ŝargi bibliotekon. Eblaj solvoj:<br/><br/>" +
           "a) Aldonu la dosierujon kun la komuna biblioteko al -Djava.library.path aŭ -Dorg.lwjgl.librarypath.<br/>" +
           "b) Aldonu la JAR-dosieron kun la komuna biblioteko al la classpath.<br/><br/>" +
           "Tiuj teknikaj solvoj estas por spertaj uzantoj. Plej multaj uzantoj devus provi " +
           "reinstali Minecraft antaŭ ol ŝanĝi tiujn parametrojn.</b>";
}

@Override
public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
    return "<b style='color:#" + config.obtenerColorError() + "'>ID-Konflikto: La ID <strong>" + id + "</strong> jam okupita de <strong>" + modOrigen + "</strong> dum provo aldoni <strong>" + modDestino + "</strong>. Tio okazas kiam du kromprogramoj provas uzi la saman ID por malsamaj elementoj.</b>";
}

@Override
public String conflicto_id_maximo() {
    return "<b style='color:#" + config.obtenerColorError() + "'>La maksimuma permesita ID-ĝamo estas transpasita. Tio okazas kiam kromprogramoj provas registri blokojn aŭ erojn kun ID-oj ekster la permesita ĉirkaŭĵeto por via Minecraft-versio.</b>";
}

@Override
public String nombre_de_conflicto_ids() {
    return "ID-Konflikto";
}

@Override
public String solucion_maximo_rango() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Por solvi tion en Minecraft 1.12.2, instalu <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. Por 1.7.10, uzu <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
}

@Override
public String solucion_colision_id() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Uzu ilojn kiel <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> aŭ <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> por solvi ID-konfliktojn.</b>";
}

@Override
public String instalar_justenoughids() {
    return "Instali JustEnoughIDs";
}

@Override
public String instalar_endlessids() {
    return "Instali EndlessIDs";
}

@Override
public String usar_idfix_minus() {
    return "Uzi IdFix Minus aŭ IdFix";
}

@Override
public String usar_minecraft_id_resolver() {
    return "Uzi Minecraft-ID-Resolver";
}

@Override
public String ver_documentacion_jp() {
    return "Vidi la japanan dokumenton";
}

@Override
public String escanearDeMCreator() {
    return "Skani MCreator";
}

@Override
public String tituloArbolDeMods() {
    return "Modula kaj Klasa Arbo";
}

@Override
public String tipoBusqueda() {
    return "Tipo";
}

@Override
public String filtroTodos() {
    return "Ĉiuj";
}

@Override
public String filtroPaquetes() {
    return "Pakaĵoj";
}

@Override
public String filtroClases() {
    return "Klasoj";
}

@Override
public String filtroMetodos() {
    return "Metodoj";
}

@Override
public String filtroCampos() {
    return "Kampoj";
}

@Override
public String filtroReferenciasCampo() {
    return "Referencoj de kampo";
}

@Override
public String filtroReferenciasMetodo() {
    return "Referencoj de metodo";
}

@Override
public String filtroReferenciasClase() {
    return "Referencoj de klaso";
}

@Override
public String tipBuscar() {
    return "Tajpu ĉi tie por serĉi en la modarbo";
}

@Override
public String botonBuscar() {
    return "Serĉi";
}

@Override
public String botonResetearArbol() {
    return "Reŝargi arbon";
}

@Override
public String modsCargados() {
    return "Ŝarĝitaj modoj";
}

@Override
public String clases() {
    return "Klasoj";
}

@Override
public String metodos() {
    return "Metodoj";
}

@Override
public String campos() {
    return "Kampoj";
}

@Override
public String referencias() {
    return "Referencoj";
}

@Override
public String resultadosBusqueda() {
    return "Serĉrezultoj";
}

@Override
public String buscarReferencias() {
    return "Serĉi referencojn";
}

@Override
public String referenciasMod() {
    return "Referencoj de mod";
}

@Override
public String referenciasClase() {
    return "Referencoj de klaso";
}

@Override
public String referenciasMetodo() {
    return "Referencoj de metodo";
}

@Override
public String referenciasCampo() {
    return "Referencoj de kampo";
}

@Override
public String noSeEncontraronReferencias() {
    return "Neniu referenco trovita";
}

@Override
public String detalleMod() {
    return "Detaloj de mod:";
}

@Override
public String ubicacion() {
    return "Loko";
}

@Override
public String nombres() {
    return "Nomoj";
}

@Override
public String modulo() {
    return "Modulo";
}

@Override
public String detalleClase() {
    return "Detaloj de klaso:";
}

@Override
public String detalleMetodo() {
    return "Detaloj de metodo:";
}

@Override
public String detalleCampo() {
    return "Detaloj de kampo:";
}

@Override
public String clase() {
    return "Klaso";
}

@Override
public String tipo() {
    return "Tipo";
}

@Override
public String referenciasAMetodos() {
    return "Referencoj al metodoj:";
}

@Override
public String referenciasACampos() {
    return "Referencoj al kampoj:";
}

@Override
public String arbolDeMods() {
    return "Modarbo";
}

@Override
public String metodo() {
    return "Metodo";
}

@Override
public String campo() {
    return "Kampo";
}

@Override
public String descompilar() {
    return "Malkomposti";
}

@Override
public String exportar() {
    return "Eksporti";
}

@Override
public String importar() {
    return "Importi";
}

@Override
public String errorImportar() {
    return "Importa Eraro";
}

@Override
public String estructuraImportada() {
    return "Strukturo Importita";
}

@Override
public String estructuraExportada() {
    return "Strukturo Eksporita";
}

@Override
public String errorExportar() {
    return "Eksporta Eraro";
}

@Override
public String exportando() {
    return "Eksporatas";
}

@Override
public String exportacionTardara() {
    return "Eksporo povas daŭri iom da tempo";
}

@Override
public String porFavorEspere() {
    return "Bonvolu atendi";
}

@Override
public String noTienesVLCBin() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Vi ne havas la VLC-binarojn. WaterMedia bezonas la VLC-binarojn. Vi devas instali ilin mane el https://www.videolan.org/vlc/.  </b>";
}

@Override
public String descargar_vlc() {
    return "Elŝuti VLC";
}

@Override
public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Grava eraro: La modula nomo '" + nombreModulo + 
           "' enhavas nevalidajn signojn. La parto '" + parteInvalida + 
           "' ne estas valida Java-identigilo. Tio okazas kiam mod uzas rezervitajn vortojn de Java (kiel 'true', 'class') aŭ nepermesitajn signojn en sia nomo.</b>";
}

@Override
public String nombre_de_error_caracteres_invalidos() {
    return "Nevalidaj Signoj en Modnomo";
}

@Override
public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
    return "La modnomo '" + nombreModulo + "' estas nevalida ĉar ĝi enhavas '" + parteInvalida + 
           "', kiu estas rezervita vorto de Java aŭ nepermesita signo. " +
           "Serĉu en la protokoloj kiun modon ĉi tiu nomo reprezentas (plejofte la nomo de la JAR-dosiero)";
}

@Override
public String paso2_caracteres_invalidos(String nombreModulo) {
    return "Iru al la dosierujo de la mod kaj redaktu la dosieron <b>mods.toml</b> ene de la dosierujo <b>/META-INF/</b>. " +
           "Ŝanĝu la valoron de <b>modId</b> por uzi nur literojn, ciferojn kaj substrekojn, sen Java-rezervitaj vortoj";
}

@Override
public String paso3_caracteres_invalidos() {
    return "Ekzemplo de valida nomo: 'truemod_shot_enchantment' anstataŭ 'true.shot.enchantment'. " +
           "Memoru ke modnomoj ne povas enhavi punktojn, strekojn aŭ Java-rezervitajn vortojn kiel 'true', 'false' aŭ 'class'";
}

@Override
public String errorDependenciaModFaltante(String nombreJar) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Grava eraro kun la mod: '" + nombreJar + "'. Mankas la deviga kampo 'mandatory' en ĝiaj dependoj. Tio okazas kiam la dosiero mods.toml ne specifas ĉu dependo estas deviga.</b>";
}

@Override
public String nombre_de_error_dependencia_mod_faltante() {
    return "Moddependeco sen Mankanta Deviga Kampo";
}

@Override
public String paso1_dependencia_mod_faltante(String nombreJar) {
    return "La problemeca mod estas: <b>" + nombreJar + "</b>. Ĉi tiu dosiero havas eraron en sia dependeca agordo";
}

@Override
public String paso2_dependencia_mod_faltante(String nombreJar) {
    return "Malfermu la dosieron <b>mods.toml</b> ene de la dosierujo <b>/META-INF/</b> de la mod <b>" + nombreJar + "</b>";
}

@Override
public String paso3_dependencia_mod_faltante() {
    return "En la dependecoj sekcio, certiĝu ke ĉiu enigo inkludu <b>mandatory=true</b> aŭ <b>mandatory=false</b> (ekz: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
}


@Override
public String errorAccessTransformerInvalido(String nombreJar) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Grava eraro ĉe la mod: '" + nombreJar + "'. Nevalida konfiguro de access transformer. Tio okazas kiam la konfigura dosiero havas malĝustan sintakson aŭ referencojn al neekzistantaj klasoj/metodoj.</b>";
}

@Override
public String nombre_de_error_access_transformer_invalido() {
    return "Nevalida Access Transformer";
}

@Override
public String paso1_access_transformer_invalido(String nombreJar) {
    return "La problemeca mod estas: <b>" + nombreJar + "</b>. Ĉi tiu mod enhavas nevalidan konfiguron de access transformer";
}

@Override
public String paso2_access_transformer_invalido(String nombreJar) {
    return "Malfermu la dosieron <b>accessTransformer.cfg</b> ene de la mod <b>" + nombreJar + "</b> (plejofte en la ĉefa dosierujo de la JAR-dosiero)";
}

@Override
public String paso3_access_transformer_invalido() {
    return "Ĝustigu la sintakson de la access transformer. Linioj devas sekvi la formaton: <b>access class.method</b> (ekz: public net.minecraft.world.entity.Entity.func_200560_a). Forigu liniojn kiuj referencas klason aŭ metodon kiuj ne ekzistas en via Minecraft-versio";
}

@Override
public String errorDiscrepanciaModID(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Grava eraro: Malsameco inter la mod-ID en la @Mod notacio kaj la dosiero mods.toml. La mod '" + nombreMod + "' ne povas ŝargiĝi ĉar la ID-oj ne samas.</b>";
}

@Override
public String nombre_de_error_discrepancia_mod_id() {
    return "Malsameco inter @Mod kaj mods.toml";
}

@Override
public String paso1_discrepancia_mod_id(String nombreMod) {
    return "La en-disvolviĝanta mod '" + nombreMod + "' havas malsamecon inter la ID en la <b>@Mod</b> notacio kaj la valoro en <b>mods.toml</b>";
}

@Override
public String paso2_discrepancia_mod_id() {
    return "Certigu ke via ĉefa klaso havas saman ID-on kiel la <b>modId</b> valoro en la dosiero <b>/META-INF/mods.toml</b>. Ekzemple: <b>@Mod(\"mymod\")</b> devas sami kun <b>modId=\"mymod\"</b>";
}

@Override
public String paso3_discrepancia_mod_id() {
    return "Se vi uzas Gradle, rulu <b>clean</b> post ŝanĝoj por certigi ke la rimedoj aktualasĝu ĝuste. Foje malnovaj dosieroj restas en la build dosierujo";
}


@Override
public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
    String plataforma = entornoInvalido.equals("CLIENT") ? "kliento" : "servilo";
    String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "servilo" : "kliento";
    
    return "<b style='color:#" + config.obtenerColorError() + "'>Grava eraro: Prenas ŝargi klason '" + nombreClase + 
           "' en la " + plataforma + " medion, sed ĝi estas dizajnita por " + plataformaOpuesta + 
           ". <b>Uzu la funkcion 'Modarbo' en la flankoŝranko por trovi kiun modon provas ŝargi tiun klason</b>. " +
           "Modoj estas konstruitaj specife por unu platformo kaj ne funkcias en la alia.</b>";
}

@Override
public String nombre_de_error_mod_plataforma_incorrecta() {
    return "Modo en Malĝusta Platformo";
}

@Override
public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
    return "En la langeto <b>Modarbo</b> (dekstre), serĉu referencojn al la klaso <b>" + nombreClase + 
           "</b> por identigi kiun modon kaŭzas la problemon";
}

@Override
public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
    String plataforma = entornoInvalido.equals("CLIENT") ? "kliento" : "servilo";
    String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "servilo" : "kliento";
    
    return "La identigita mod estas <b>" + plataformaOpuesta + "</b> mod kiu ne devas uziĝi en via " + plataforma + " medio.";
}

@Override
public String paso3_mod_plataforma_incorrecta() {
    return "Forigu la probleman modon el via dosierujo <b>modoj</b>. Se vi bezonas similan funkciecon por tiu platformo, " +
           "serĉu alternativan modon specife dizajnitan por <b>kliento</b> aŭ <b>servilo</b> laŭbezone";
}

@Override
public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
    StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
    mensaje.append("Grava eraro: Mankas metadatenoj por modid '").append(modIdFaltante).append("'. ");
    
    if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
        mensaje.append("Jenaj modoj povas kaŭzi la problemon: <b>");
        for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
            mensaje.append(modsPotenciales.get(i));
            if (i < modsPotenciales.size() - 1 && i < 2) mensaje.append(", ");
        }
        if (modsPotenciales.size() > 3) mensaje.append(", kaj aliaj...");
        mensaje.append("</b>. ");
    }
    
    mensaje.append("Tio okazas kiam mod dependas de alia mod neinstalita aŭ kun malĝusta dosiero mods.toml.");
    mensaje.append("</b>");
    
    return mensaje.toString();
}

@Override
public String nombre_de_error_metadata_mods_toml_faltante() {
    return "Mankas Metadatenoj de mods.toml";
}

@Override
public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
    if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
        StringBuilder paso = new StringBuilder("Jenaj modoj dependas de '").append(modIdFaltante).append("': <b>");
        for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
            paso.append(modsPotenciales.get(i));
            if (i < modsPotenciales.size() - 1 && i < 2) paso.append(", ");
        }
        if (modsPotenciales.size() > 3) paso.append(", kaj aliaj...");
        paso.append("</b>. Uzu la funkcion <b>Modarbo</b> por konfirmi kiun modon kaŭzas la problemon");
        return paso.toString();
    }
    return "Mod provas dependi de '".concat(modIdFaltante).concat("', sed tiu mod ne estas instalita. Uzu la funkcion <b>Modarbo</b> por identigi kiun modon kaŭzas la problemon");
}

@Override
public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
    return "Vi havas du elektojn:<br/>" +
           "1. <b>Instalu la mankantan modon</b>: Serĉu kai instalu la modon kun ID '".concat(modIdFaltante).concat("'<br/>") +
           "2. <b>Forigu la dependan modon</b>: Se vi ne bezonas la funkcion, forigu la modon kiun dependas de '".concat(modIdFaltante).concat("'");
}

@Override
public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
    return "Se '".concat(modIdFaltante).concat("' estas biblioteko (kiel 'forge', 'minecraft', 'curios'), ") +
           "certigu ke vi havas la ĝustajn versiojn de Minecraft kaj Forge instalitajn. " +
           "Se estas regula mod, kontrolu ĉe sia elŝuta paĝo la antaŭajn postulojn.";
}

@Override
public String errorSistemaSonido() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Averto: Malsukcesis lanĉi sonan sistemon. Sonoj kaj muziko estas malebligitaj. Ĉi tiu eraro kutime rilatas al la aldonaĵo SoundPhysicsMod kaj povas okazi pro konfliktoj kun aliaj sonaj bibliotekoj.</b>";
}

@Override
public String nombre_de_error_sistema_sonido() {
    return "Eraro en Sona Sistemo";
}

@Override
public String paso1_sistema_sonido() {
    return "La eraro kutime rilatas al <b>SoundPhysicsMod</b>. Kontrolu ĉu vi havas la plej novan version kompatan kun via Minecraft versio";
}

@Override
public String paso2_sistema_sonido() {
    return "Se vi uzas aliajn sonajn aldonaĵojn (kiel Sound Filters, Dynamic Surroundings, ktp.), provu dumtempe forigi SoundPhysicsMod por vidi ĉu tio solvas la konflikton";
}

@Override
public String paso3_sistema_sonido() {
    return "Ekzameni la dosierujon <b>logs</b> por trovi pliajn mesaĝojn rilatajn al LWJGL aŭ OpenAL, kiuj povas indiki problemojn kun subaj sonaj bibliotekoj";
}

@Override
public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
    StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
    mensaje.append("Grava eraro: La klaso '").append(nombreClase).append("' estas registrita kiel eventa aŭskultanto sed ne enhavas validajn metodojn. ");
    
    if (!modsUbicacion.isEmpty()) {
        mensaje.append("Ĉi tiu klaso troviĝas en jenaj aldonaĵoj: <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            mensaje.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) mensaje.append(", ");
        }
        if (modsUbicacion.size() > 3) mensaje.append(", kaj aliaj...");
        mensaje.append("</b>. ");
    }
    
    mensaje.append("Tio okazas kiam klason oni registras por aŭskulti eventojn sed ĝi ne havas metodojn kun @SubscribeEvent marko.");
    mensaje.append("</b>");
    
    return mensaje.toString();
}

@Override
public String nombre_de_error_sin_listeners_en_clase() {
    return "Klaso Registrita Sen Eventaj Aŭskultantoj";
}

@Override
public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
    if (!modsUbicacion.isEmpty()) {
        StringBuilder paso = new StringBuilder("La problemeca klaso troviĝas en ĉi tiuj aldonaĵoj: <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            paso.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) paso.append(", ");
        }
        if (modsUbicacion.size() > 3) paso.append(", kaj aliaj...");
        paso.append("</b>. Ĉi tiuj aldonaĵoj provas registri eventojn sen validaj metodoj");
        return paso.toString();
    }
    return "La klaso <b>" + nombreClase + "</b> estis registrita por aŭskulti eventojn sed ne enhavas metodojn kun marko <b>@SubscribeEvent</b>. Uzu la funkcion <b>Modarbo</b> por identigi kiun aldonaĵon enhavas ĉi tiun klason";
}

@Override
public String paso2_sin_listeners_en_clase(String nombreClase) {
    return "En la fontkodo, certigu ke la klaso <b>" + nombreClase + "</b> enhavu almenaŭ unu metodon kun: " +
           "<b>@SubscribeEvent public void nomoDeMetodo(SpecifaEvento evento) { ... }</b>. " +
           "Se estas ena klaso, certigu ke ĝi ne estas markita kiel static";
}

@Override
public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
    StringBuilder paso = new StringBuilder();
    
    if (!modsUbicacion.isEmpty()) {
        paso.append("Por la identigitaj aldonaĵoj (<b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
            paso.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 1) paso.append(", ");
        }
        if (modsUbicacion.size() > 2) paso.append(", ktp.");
        paso.append("</b>): ");
        
        if (modsUbicacion.size() == 1) {
            paso.append("kontaktu la programiston de ĉi tiu aldonaĵo por ripari la problemon. ");
        } else {
            paso.append("kontaktu la programistojn de ĉi tiuj aldonaĵoj por ripari la problemon. ");
        }
    }
    
    paso.append("Se vi estas programisto, forigu la registron de ĉi tiu klaso en la EventBus aŭ aldonu validajn @SubscribeEvent metodojn");
    
    return paso.toString();
}

@Override
public String errorUnionFileSystemCorrupto(String nombreArchivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Grava eraro: Okazis escepto 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' dum prilaborado de la dosiero '" + 
           nombreArchivo + "'. Ĉi tiu specifa eraro indikas ke la lanĉilo malsukcesis ĝuste elŝuti aŭ elpaketi la dosierojn de la modopako. " +
           "La mesaĝo 'zip END header not found' montras ke la JAR-dosiero estas neprofunda aŭ difektita, kio estas tre ofta ĉe lanĉiloj kiuj malbone prilaboras grandajn dosierojn. " +
           "Ĉi tiu problemo ĉefe tuŝas uzantojn de Twitch/CurseForge, Technic Launcher, kaj precipe Luna Pixel, ĉar tiuj lanĉiloj ofte malsukcesas kontroli la plenan integrecon de elŝutitaj dosieroj. " +
           "Uzantoj de Luna Pixel devus konsideri ATLauncher kiel pli stabilan alternativon, kiu pli bone prilaboras dosierajn integrecojn kaj evitas ĉi tiun eraron. " +
           "La sistemo ne povas ŝargi la modojn ĉar la ZIP-formo estas difektita, malebligante al Forge legi la necesajn rimedojn por komenci la ludon.</b>";
}

@Override
public String nombre_de_error_union_filesystem_corrupto() {
    return "UnionFileSystem Eraro - Difektita Dosiero";
}

@Override
public String paso1_union_filesystem_corrupto(String nombreArchivo) {
    return "Komplete reinstalu la modopakon el komenco";
}

@Override
public String paso2_union_filesystem_corrupto() {
    return "Se vi uzas Luna Pixel, ŝanĝu al ATLauncher";
}

@Override
public String paso3_union_filesystem_corrupto() {
    return "Kontrolu vian interretan konekton kaj diskospacon antaŭ ol reinstali";
}





@Override
public String habilitarProxySysOutSysErrMensaje() {
    return "Ĉu ebligi ProxySysOutSysErr?\n\n" +
           "Ĉi tiu opcio donas al CrashDetector aliron al System.out kaj System.err kiam la lanĉilo ne provizas logojn.\n\n" +
           "Ĝi nur devus esti ebligita se vi ne povas alglui registron mane.\n\n" +
           "Averto: Ĉi tio povas interferi kun iuj aldonaĵoj aŭ lanĉiloj.\n\n" +
           "Necesas restarti la ludon/aplikon por ke la ŝanĝoj efektiviĝu.";
}

@Override
public String confirmacionTitulo() {
    return "Konfirmo";
}

@Override
public String proxyHabilitadoMensaje() {
    return "ProxySysOutSysErr sukcese ebligita.\n\n" +
           "Necesas restarti CrashDetector por ke la ŝanĝoj efektiviĝu.";
}

@Override
public String informacionTitulo() {
    return "Informo";
}











}
