package com.asbestosstar.crashdetectormc.idioma;

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
            + "                - MultiMC/PolyMC/PrismLauncher/PollyMC/UltimMC: NOTO: Aŭtomate detektitaj protokoloj NE estas ĝustaj.\n"
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
    return "Anonimigi protokolojn (Baldaŭ)";
}

@Override
public String botonDeCompartirInforme() {
    return "Kunhavigi Raporton kaj ĉiujn elektitajn protokolojn";
}

@Override
public String arco() {
    return "Ĉi tiu dialogo ebligas kunhavigi protokolojn uzante la API-on de SecureLogger "
            + "ĉe securelogger.net. Kiam vi premas la kunhavigajn butonojn, dosieroj "
            + "alŝutiĝas al la elektita retejo (defaŭlte asbestosstar.egoism.jp). Vi povas kunhavigi ĉiujn elektitajn protokolojn "
            + "kune kun la raporto. Se vi ne volas alŝuti, ne uzu ĉi tiun dialogon! Ni ne prilaboras vian raporton ĉe la oficiala finpunkto (https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb); ni nur forigas nepermesatajn ligilojn. La kodo estas ĉi tie: https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb. Tio estas uzata nur por montri informon pri via paneo kaj la ligilon al la protokoloj. Tamen, eblas uzi propran finpunkton, kiu eble ne havas la samajn metodojn. Vi uzas la raportan retejon " 
            + Config.obtenerInstancia().obtenerSitoDeInformes() + " kaj la protokolan retejon " 
            + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado();
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






}
