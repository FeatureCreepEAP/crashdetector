package com.asbestosstar.crashdetector.idioma;

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
    public String config_spongemixin_problematico() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Detektita problema SpongeMixin-agordo: </b>";
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
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Averto: Mankantaj klasoj trovitaj:</b>";
}


@Override
public String noResultos() {
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
public String noRegistroDeLauncher() {
    return "Neniu protokolo de la lanĉilo troviĝis! Tio povas kompliki la esploradon.\n"
            + "                \n"
            + "                Por akiri la ĝustajn protokolojn:\n"
            + "                - MultiMC/PolyMC/PrismLauncher: NOTO: Aŭtomate detektitaj protokoloj NE estas ĝustaj.\n"
            + "                  1. Malfermu la instancan interfaco\n"
            + "                  2. Iru al la sekcio \"Minecraft Log\"\n"
            + "                  3. Alklaku dekstre kaj kopiu la enhavon\n"
            + "                - CurseForgeApp:\n"
            + "                  1. Restartu la ludon SEN salti la lanĉilon\n"
            + "                  \n";
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

public String nombre_de_contento_de_stacktrace() {
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



}
