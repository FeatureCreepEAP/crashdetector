package com.asbestosstar.crashdetector.idioma;

import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Esperanto implements Idioma {
	private final Config config = Config.obtenerInstancia();

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Malvalida mod-dosierujo</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Ne troveblas JAR-dosiero de CrashDetector</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Serĉante PID: " + String.valueOf(pid)
				+ "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") ĉesis funkcii!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Mankas JVM</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Ĝisdatigi viajn kontrolilojn ATI/AMD eble helpas vin. Legu ĉi tiun gvidilon por ripari ĝin: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>Gvidilo pri ĝisdatigo de kontroliloj</a> https://www.amd.com/es/support/download/drivers.html Elŝuti </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Kelkaj malnovaj versioj foje havas problemojn kun fruaj ŝarĝoj de Nouveau-kartoj.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Estas problemo kun via grafik-ŝoforprogramo. Se vi uzas AMD/ATI GPU aŭ APU, ĝisdatigu viajn AMD-ŝoforprogramojn. Se vi uzas NVIDIA-karton, certigu ke la ludo kaj ĉiuj javaw.exe-ekzempleroj uzas apartan grafikkarton. Legu ĉi tiun gvidilon: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Gvido por ĝisdatigi ŝoforprogramojn</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Via FML-frua fenestro malsukcesis ŝargi. Por ripari, iru al (.minecraft/config/fml.toml) kaj agordu 'earlyWindowProvider' al \"none\". Se vi uzas Mac M1, certigu ke vi uzas ARM-version de Java, ne Intel x64. Ĉi tio ankaŭ ofte okazas pro malnovaj ŝoforprogramoj. Se vi uzas Windows kaj malŝalti ĉi tiun agordon ne helpas, konsultu ĉi tiun gvidilon: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Gvido por ĝisdatigi ŝoforprogramojn</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Mankas necesaj dependaĵoj:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "Petita de").replace("Expected range", "Atendata amplekso") + "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>Via CrashDetector-raporto troviĝas ĉi tie <a href='" + archivo + "' style='color:#"
				+ config.obtenerColorEnlace() + "'>Vidi raporton</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>Ĉi tiu estas la GUI de CrashDetector. Se la ludo normale fermiĝis, ignoru ĉi tiun ekranon.</span>";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Detektita problema JAR-dosiero (prioritatu FATAL, poste alta kaj malalta prioritato):</b>";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Detektitaj problemaj ModID (prioritatu FATAL, poste alta kaj malalta prioritato):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Detektitaj problemaj pakoj (prioritatu FATAL, poste alta kaj malalta prioritato):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vi havas fatalajn klasojn (FATAL), tio estas tre gravega. Oftaj kaŭzoj estas malbonaj CoreMods aŭ fatalaj dependecoj. Vi povas uzi QuickFix por serĉi modojn kun fatalaj klasoj. Mankantaj fatalaj klasoj trovitaj:</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Enhavo de {} (plej grava supre, nur unua 20 montritaj):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Detektita problema SpongeMixin-agordo: </b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Vi havas modojn kun duplikitaj pakaĵoj. Vi povas solvi tion forigante la duplikitan pakaĵon (dosierujon) el la JAR-dosiero. Vi povas malfermi la JAR-on en arkivilo kiel WinRAR aŭ 7z, aŭ vi povas ŝanĝi la dosiernomon de .jar al zip kaj poste forigi la dosierujon kaj tiam reŝanĝi ĝin al .jar dosiero.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Detektitaj duoblaj Modoj</b> "
				+ linea.replace("from mod files", "el mod-dosieroj");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>MinecraftForge detektis problemojn kun suspektinda mod:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV bezonas lithostitched, instalebla ĉi tie: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Por uzi Iris-ŝejnilojn aŭ Oculus, vi bezonas kongruan version de SODIUM aŭ alia ŝarĝilo (Rubidium, Embedium, Bedium)</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Problemo kun KubeJS-etendaĵo </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Problemoj detektitaj kun NVIDIA-drajviloj en versioj antaŭ Windows 11." + "</span><br/><br/>"
				+ "Por certigi, ke Minecraft (kaj la nuna JVM) uzas la dediĉitan grafikan karton NVIDIA, sekvi tiujn paŝojn:<br/><br/>"
				+ "1. <strong>Identigu la Java-ekzekuteblon:</strong><br/>"
				+ "   - Ĉi tiu programo uzas la jenan Java-ekzekuteblon: " + obtenerRutaJava() + "<br/>"
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
				+ "Problemoj detektitaj kun NVIDIA-drajviloj en Windows 11/Server 2025 aŭ poste." + "</span><br/><br/>"
				+ "Por certigi, ke Minecraft (kaj la nuna JVM) uzas la dediĉitan grafikan karton NVIDIA, sekvi tiujn paŝojn:<br/><br/>"
				+ "1. <strong>Identigu la Java-ekzekuteblon:</strong><br/>"
				+ "   - Ĉi tiu programo uzas la jenan Java-ekzekuteblon: " + obtenerRutaJava() + "<br/>"
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Via Servilo aŭ Mondo havas tiko pli longaj ol 60 sekundoj. Tio povas esti pro tio, ke aldonaĵoj malrapidigas la servilon aŭ la aparataro estas tro malforta.</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vi ne havas sufiĉe da RAM/Memoro. Vi devas atribui pli.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Theseus havas pliajn problemojn, inkluzive de malsukceso dum forigo de aldonaĵoj kiam vi provas. Se vi bezonas ludi kun mrpack-dosieroj, vi povas uzi aliajn lanĉilojn kiel Prism Launcher (nur por modrinth.com), ATLauncher (nur por modrinth.com), aŭ Hello Minecraft Launcher (por modrinth.com kai bbsmc.net).</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Vi uzas \"Preterlasi lanĉilon\" (CurseForge App). Foje tio kaŭzas malfacile detekteblajn problemojn. Tio devenas de la opcio \"Preterlasi lanĉilon\" en malnovaj versioj de la CurseForge App aŭ en la nova versio. Malŝaltu ĝin kaj uzu la opcion \"Mojang Launcher\" en la agordoj de CurseForge. Vi povas rigardi tiun anglalingvan videon de Claws of Berk (iru al 1:11) "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>ĉi tie</a>.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Averto: Mankas klasoj (averta nivelo). Kutime ne grave, sed povas okazi problemoj — malsamas de fatalaj klasaj eraroj. Oftaj kaŭzoj estas malbonaj CoreMods aŭ mankantaj dependecoj. Vi povas uzi QuickFix por serĉi modojn kun mankantaj klasoj. Mankantaj klasoj trovitaj:</b>";
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
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Jen viaj rezultoj de kontroloj. Faru ĉion paĉe; kutime la ĉefa kaŭzo troviĝas en la unua aŭ dua kontrolo. La ceteraj (eraroj 3 kai pli supre) povas esti uzitaj por konfirmo, sed ili estas plejofte kaskadaj eraroj kiujn vi povas ĉifone ignori. Malsukcesoj okazas en tavoloj, do solvante la ĉefan problemon vi solvos tiun apartan eraron. Tamen, eble morgaŭ aperos nova eraro ne rilatanta al nuna, ĉar ofte unu eraro malebligas alian aperi en la konzolo.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bonvolu uzi Java 17 por versioj 1.17-1.20.4 kaj Java 21 por iu ajn pli nova versio. Uzu Java 8 por pli malnovaj versioj. [Gvidilo](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Se vi ankoraŭ havas problemojn, eble pro tio ke iu mod havas tro novajn aŭ malnovajn dosierojn.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 kaj super ne funkcias en Minecraft-versioj sub 1.20.5 por plej multaj modŝarĝiloj pro malaktuala ASM.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java estas malmoderna </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Vi bezonas la JPMS-modulon " + mod_necesitas
				+ " el " + submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Ne eblas voki " + metodo + " ĉar " + objeto
				+ " estas nula</b>";
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
		return "Ĉi tiu dialogo permesas al vi kunhavigi protokolojn uzante la API de SecureLogger "
				+ "ĉe <a href=\"https://securelogger.net\">securelogger.net</a>. Premante la butonon por kunhavigi la raporton, "
				+ "via raporto sendiĝas al la elektita finpunkto (defaŭlte asbestosstar.egoism.jp) (ŝanĝebla malsupre). "
				+ "Vi povas kunhavigi ĉiujn elektitajn protokolojn kune kun la raporto. Se vi ne volas alŝuti, ne uzu ĉi tiun dialogon! "
				+ "Ni ne prilaboras vian raporton ĉe la oficala finpunkto (<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\">https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb</a>); "
				+ "ni nur forigas malpermesitajn ligilojn. La kodo estas ĉi tie: <a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">fontkodo</a>. "
				+ "Ĉi tio estas uzata sole por montri informojn pri via kraŝo kaj ligilon al la protokoloj. Tamen, eblas uzi propran finpunkton, kiu eble ne havas la samajn metodojn. "
				+ "Vi uzas la raport-siton " + Config.obtenerInstancia().obtenerSitoDeInformes()
				+ " kaj la protokol-siton " + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". "
				+ "Vi ankaŭ povas kunhavigi unuopajn protokolojn sen raporto per premado de la kunhavig-butonoj apud la nomoj de la unuopaj protokoloj; "
				+ "la protokoloj iros al la elektita protokol-sito. CrashDetector havas defaŭltan anonimigon de protokoloj, kiu provas forigi uzantnomojn, UUID-ojn, "
				+ "alirkodojn, seanco-ID-ojn, IP-adresojn kaj aliajn datumojn. Tamen, ĝi ne estas perfekta. Tamen, la kreinto de la modpakaĵo povas malŝalti ĝin. "
				+ "Ĝi povas esti ŝaltita aŭ malŝaltita per la markobutono malsupre en ĉi tiu ekrano. Vi estas la reganto de viaj propraj datumoj; vi decidu kien vi alŝutas viajn datumojn. "
				+ "La protokol-sitoj estas posedataj de triaj partioj, kies posedeco ofte kaŝiĝas pro privateco. Vi prenas tutan respondecon pri administrado de viaj datumoj kaj la implikitaj riskoj. "
				+ "La Kunhavig-Dialogo de CrashDetector estas simple interfaco, kiu permesas al vi administri tion. "
				+ "Estas grava, ke vi sciu pri GDPR kaj ARCO. "
				+ "Se vi estas en Eŭropo, vi povas uzi <a href=\"https://securelogger.top\">securelogger.top</a> gastigatan en Germanio de Hetzner. "
				+ "Por pli da jura informo, vidu la jenajn ligilojn: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>, "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">GDPR</a>, "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">Baza Politiko pri Datumprotekto en Japanio</a>.";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Ne kongrua versio de JavaFML: necesas "
				+ requerido + ", troviĝis " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Atentu! JavaFML bezonas specifan version de Minecraft Forge</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "La JAR-dosiero '" + archivoJar
				+ "' bezonas la lingvo-provizanton '" + proveedor + "' version '" + requerido
				+ "' aŭ pli novan, sed nur la versio '" + encontrado + "' estis trovita.</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ATENTU! Crash Assistant estas falsa malprogram-detektilo. Ĝi intence blokas la lanĉon de la ludo, ignorante vian liberecon daŭrigi ludi kun la celitaj kromprogramoj. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Vidi kodon de MalwareMod.java</a>   "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Vidi kodon de JarInJarHelper.java</a>. "
				+ "Nur ĉi tiu kromprogramo estas nuntempe sur ilia listo, kaj ili fakte nur celas la defaŭltan protokolregistran retejon, kiun la uzanto povas ŝanĝi, kaj tio nur okazas se vi elekte uzas la enigitan protokolan kunhavigan funkcion. CrashAssistant NE faras iujn ajn kontroladojn por eĉ determini kiu protokolregistra retejo estas uzata kaj ne klarigas kiel ŝanĝi ĝin (estas falmenuo ĉe la malsupra parto de la kunhaviga dialogo), kaj sendepende de la agordita retejo, CrashAssistant blokos la lanĉon de la ludo. En sia mesaĝo, ili diras fari vian propran esploradon, FARU ĜIN, rigardu la kodon de CrashDetector kaj Crash Assistant kaj komprenu kion ili faras, NE fidas al aŭtoritato.</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "La modulo '" + idMod
				+ "' malsukcesis ĉar la bezonata klaso ne estis trovita: '" + claseNoEncontrada
				+ "'. Certigu, ke ĉiuj dependecoj estas instalitaj ĝuste.</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Watermedia blokis ludi per TLauncher.</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Vi uzas version de Optifine por malaktuala versio de Minecraft. Vi devas uzi la version de Optifine por la versio de Minecraft kiun vi uzas.</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Ne eblis ŝargi la servon de ModLauncher: </b>"
				+ servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Eraro dum analizado de la JSON-dosiero '"
				+ recurso + "' el la JAR-dosiero '" + archivoJar + "'. Estas problemoj kun la registrado.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Eraro: La modulo '" + modId
				+ "' bezonas la version '" + requerido + "' aŭ pli novan de '" + dependencia + "', sed troviĝis '"
				+ actual + "'." + "</span>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Eraro de agordado de monitoroj: "
				+ "Ne eblis difini ekranan modon. " + "Kontrolu:</b>" + "<br>- Agordo de multaj monitoroj"
				+ "<br>- Ĝisdatigitaj grafikaj peliloj" + "<br>- Sistem-subebla rezolucio";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Eraro en opcioj de Java: "
				+ "Konfliktantaj rubkolektaj opcioj. "
				+ "Kontrolu, ke vi ne kombinas plurajn GC-algoritmojn en JVM-parametroj</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Grava eraro de agordo de NightConfig/Forge: "
				+ "Agorda dosiero difektita aŭ nekompleta. "
				+ "Tio povas esti kaŭzita de malplenaj agorddosieroj (ofte 0 bajtoj) en la dosierujo 'config' en pli malnovaj aŭ modifitaj versioj de NightConfig. "
				+ "Por la plej multaj versioj, Night Config Fixes solvos la problemon, sed se vi uzas nekongruan aŭ propran versioon de NightConfig, vi devos forigi la agorddosierojn mane. "
				+ "Ĉi tiu problemo estas pli komuna en malnovaj versioj de MC Forge (kiuj inkluzivas NightConfig) kaj en malnovaj modoj de FabricMC kiuj pakas NightConfig, sed ĝi ankaŭ povas ekzisti en iuj propraj versioj de NightConfig. "
				+ "Pli da informo pri solvoj haveblas ĉe <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a>.</b>";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>La kromprogramo 'AuthMe' ne sukcesis ŝargi kaj haltigis la servilon.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La mondo '" + nombreMundo
				+ "' ne povis ŝargi ĉar ĝi enhavas erarojn kaj verŝajne estas difektita.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>La agordo de la kromprogramo 'PermissionsEx' estas nesprava.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>Estas pluraj kromprogramdosieroj kun la nomo '"
				+ nombrePlugin + "': '" + primerPath + "' kaj '" + segundoPath + "'.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Okazis escepto dum la mondo ŝargis chunk-ojn. Se ĝi ekzistas por via platformo, eble FeatureRecycler povas solvi la problemon. https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin
				+ "' ne povas plenumi la komandon '/" + comando + "'.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin
				+ "' bezonas la dependecon '" + dependencia + "'.</b> ";
	}

	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin
				+ "' mankas jenajn dependecojn: " + deps.toString() + ".</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin
				+ "' postulas API-version '" + versionAPI + "' kiu ne estas kongrua kun la nuna servilo.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La mondo '" + nombreMundo
				+ "' estas duplikato de alia mondo kaj ne povas esti ŝargita.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La bloka ento '" + nombre + "' de tipo '" + tipo
				+ "' je la koordinatoj " + coords + " kaŭzas erarojn dum takto.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod
				+ "' havas plurajn versiojn instalitajn.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramoj '" + primerMod + "' kaj '"
				+ segundoMod + "' ne estas reciproke kongruaj.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod
				+ "' havas gravan eraron kaj ne povas ruli.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>Jenaj kromprogramoj estas bezonataj sed mankas: "
				+ deps.toString() + ".</b>";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod
					+ "' postulas la kromprogramon '" + dependencia + "' kiel dependecon.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod
					+ "' postulas la kromprogramon '" + dependencia + "' en versio " + version + ".</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin
				+ "' ne kongruas kun la regiona takto de Folia.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>Manko de mod '" + nombreMod
				+ "' en la datumasko.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod
				+ "' kaŭzis eraron.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod
				+ "' ne estas kongrua kun Minecraft versio " + versionMC + ".</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombreMod
				+ "' mankas kaj necesas por ŝargi la mondon aŭ kromprogramon.</b>";
	}

	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "Mankanta kromprogramo";
	}

	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>La mondo estis konservita kun kromprogramo '"
				+ nombreMod + "', kiu nun mankas.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La mondo estis konservita kun kromprogramo '"
				+ nombreMod + "' versio " + versionEsperada + ", sed nun estas uzata versio " + versionActual + ".</b>";
	}

	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
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
			sb.append(" (Konservita: ").append(versionesEsperadas.get(i)).append(", Nuntempa: ")
					.append(versionesActuales.get(i)).append(")");
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vi provis ŝargi mondon kreitan per pli nova versio de Minecraft.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin
				+ "' postulas jenan dependecon: '" + dependencia + "'.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin
				+ "' ne estas kongrua kun la nuna servila versio.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La kromprogramo '" + nombrePlugin
				+ "' kaŭzis eraron dum plenumo.</b>";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Kelkaj hiloj aliras la klason LegacyRandomSource samtempe. Por pluaj informoj, uzu la modon 'Unsafe World Random Access Detector' aŭ 'C2ME'.</b>";
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

				+ "<h3>Kiel uzi:</h3>" + "<ol>"
				+ "<li><b>Dosierselektado:</b> Klaku la radiobutonojn apud la dosiernomoj. Dosieroj finitaj per <span style='color: #4CAF50; font-weight: bold;'>.exito</span> indikas sukcesajn sesiojn, dum tiuj kun <span style='color: #F44336; font-weight: bold;'>.falla</span> montras fiaskojn.</li>"

				+ "<li><b>Aŭtomata komparo:</b> Premante butonon 'Kompari', la sistemo analizos la du listojn kaj montramos kiujn MOD-ojn oni aldonis (+) aŭ forigis (-).</li>"

				+ "<li><b>Rezultmontrado:</b> La rezultoj estas prezentitaj en HTML-kolora formato: " + "<ul>"
				+ "<li><span style='color: green;'>+ Aldonita MOD</span></li>"
				+ "<li><span style='color: red;'>- Forigita MOD</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>Ĉefaj funkcioj:</h3>" + "<ul>"
				+ "<li>Subtenas ajnan kombinaĵon de sukcesaj/malsukcesaj dosieroj.</li>"
				+ "<li>Bidirektaj diferencoj por preciza sekvo de ŝanĝoj.</li>"
				+ "<li>Rulumilo inkluzive por longaj MOD-listoj.</li>"
				+ "<li>Integriĝas kun klarigaj bildoj por plibonigi vidan komprenon.</li>" + "</ul>"

				+ "<p>Evoluinta kun <3️ por helpi vin sekvi ŝanĝojn en via agordo.</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Eblas, ke vi havas problemon rilatantan al IPv6. " + "Estas du solvoj: "
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
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/Derivaĵoj: Alklaku per dekstra butono sur la instanco kaj elektu \"Redakti Instancon\". En la malfermita fenestro serĉu sekcion \"Minecraft-a loko\" aŭ similan.<br>"
				+ "Ĉi tiuj lokoj enhavas norman eliron (STDOUT), kiu gravas por diagnozi erarojn.";
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
		return "ĜENERALA: Elektu la tipon de lanĉilo kiun vi uzas. La loko-dosieroj de lanĉilo (launcher_log.txt, stdout, ktp.) enhavas gravajn erarinformojn, kiujn latest.log ne montras. CrashDetector ne povas legi viajn loko-dosierojn — eble mankas loko-dosiero kaj vi devas alglui la lokon.<br>"
				+ "Por pli da informo, vidu <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">tiun problemon</a>. Ĉi tiuj lokoj enhavas la norman eliron (STDOUT) necesa por diagnozi multajn erartipojn.";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Mankas klaso(j) el Create. Create multe ŝanĝis — multaj klasoj estas forigitaj. Ĉefe ekde Create 6 (februaro 2025), aldonaĵoj por pli fruaj versioj ne plu funkcias. QuickFix ne havas solvon por ĉi tiu problemo. Vi devas ĝisdatigi viajn Create-aldonaĵojn, forigi malnovajn aŭ uzi la ĝustan Create-version por viaj dezirataj aldonaĵoj.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Mankas klaso(j) el EpicFight. EpicFight multe ŝanĝis — multaj klasoj estas forigitaj. QuickFix ne havas solvon por ĉi tiu problemo. Vi devas ĝisdatigi viajn EpicFight-aldonaĵojn, forigi malnovajn aŭ uzi la ĝustan EpicFight-version por viaj dezirataj aldonaĵoj.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>OpenJ9 を使用していますが、このアプリケーションは OpenJ9 をサポートしていません。多くのアプリ（このアプリも含む）は JVM 実装の違いにより OpenJ9 に対応していません。QuickFix ではこの問題を自動的に解決できません。Oracle JDK、OpenJDK Hotspot、またはその他の推奨代替 JDK をインストールする必要があります。</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>このアプリのバージョンは正常に動作するために Java 11 (JDK 11) が必要です。現在、互換性のない古いバージョンの Java を使用しています。QuickFix では Java のアップグレードは自動的に行えません。解決策に記載されたリンクから JDK 11 またはそれ以上の互換性のあるバージョンをダウンロード・インストールする必要があります。</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>メモリを過剰に割り当てており、システムリソースが不足しています。これは、システムの実装容量を超える RAM を指定した場合や、大容量メモリを扱えない 32 ビット JVM を使用している場合に発生します。</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>この問題を解決するには、アプリに割り当てるメモリ量を減らしてください。推奨される最大値はシステムによりますが、一般的に全 RAM の 70～80％を超えないようにしてください。32 ビット JVM を使用している場合、物理 RAM の量に関係なく、上限は約 2～3 GB です。</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>アプリに割り当てられたヒープメモリを減らすには、ランチャーの設定を開き、RAM オプションを探します。最大値 (Xmx) を適切な量に下げてください。例：8GB の RAM がある場合、3～4GB を割り当てます。16GB の場合、6～8GB が適切です。OS や他のプログラムのために十分なメモリを残すことを忘れないでください。</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Famas gravaj dosieroj de Forge. La dosiero '"
				+ archivo
				+ "' ne troviĝas en via instalo. Tio okazas kutime se la instalo de Forge ĉesis aŭ gravaj dosieroj forviŝiĝis. QuickFix ne povas denove trovi tiujn dosierojn aŭtomate. Vi devas reinstali Forge per la oficiala instalejo.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge ne povas trovi la bezonatan version de Minecraft. Versio " + version
				+ " estas bezonata sed ne troviĝas en dosiero '" + archivo
				+ "'. Tio okazas se ekzistas nekongruo inter via Minecraft-versio kaj via Forge-versio. Certiĝu, ke vi elŝutis la ĝustan Forge-version por via Minecraft-versio.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>La celo 'fmlclient' bezonata por lanĉi Forge ne troviĝas. Tio indikas nekompletan aŭ difektitan Forge-instalon. Eble gravaj dosieroj ne ĝuste instaliĝis. Vi devas reinstali Forge per la oficiala instalejo.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>La ĉefa Minecraft-klaso ne troviĝas en la klasŝarĝilo. Tio kutime indikas nekompletan Forge-instalon aŭ konflikton kun aliaj kromprogramoj. Eble dosieroj de Minecraft difektiĝis dum la instalo. Vi devas reinstali Forge ĝuste.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>La Forge-instalo ne estas plena. Tio povas okazi pro interrompita instalo, forviŝitaj dosieroj aŭ nekongruo kun via Minecraft-versio. Forge bezonas specifajn dosierojn por funkcii, sed iuj el ili mankas nun.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "Nekompleta Forge-instalo";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Por solvi tion, reinstalu Forge ĝuste. Certiĝu pri la ĝusta versio por via Minecraft-versio kaj plenumu la instalprocezon sen interrompo.</b>";
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
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>Instrukcioj por Reininstali Forge:</h3>" + "<ol>"
				+ "<li>Elŝutu la ĝustan Forge-instalejon el la oficiala retejo (rekomendita versio por via Minecraft)</li>"
				+ "<li>Fermu vian Minecraft-lanĉilon tute</li>"
				+ "<li>Ĉerpu la Forge-instalejon kiel administranto</li>"
				+ "<li>Elektu 'Installer' (ne 'Installer (run client)')</li>"
				+ "<li>Elektu vian Minecraft-profilan dosierujon en la lanĉilo</li>"
				+ "<li>Premu 'OK' kaj atendu plenan instalon</li>"
				+ "<li>Restartigu vian lanĉilon kaj kontrolu ke Forge aperas en la profila listo</li>" + "</ol>"
				+ "<p><b>Grava:</b> Se vi uzas ĉefan lanĉilon, certiĝu ke vi elektis la ĝustan profilan dosierujon.</p>"
				+ "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "Instrukcioj por Reininstali Forge";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Eraro pri nesolvita ligilo: Malsukcesis ŝargi bibliotekon " + nombreBiblioteca
				+ ". Eblaj solvoj:<br/><br/>"
				+ "a) Aldonu la dosierujon kun la komuna biblioteko al -Djava.library.path aŭ -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Aldonu la JAR-dosieron kun la komuna biblioteko al la classpath.<br/><br/>"
				+ "Tiu eraro okazas kiam Minecraft ne povas trovi gravajn dosierojn por sia funkciado. "
				+ "Ĝi kutime okazas pro neprofunda instalo aŭ permesaj problemoj de la sistemo.</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "Eraro pri nesolvita ligilo";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Malsukcesis ŝargi bibliotekon. Eblaj solvoj:<br/><br/>"
				+ "a) Aldonu la dosierujon kun la komuna biblioteko al -Djava.library.path aŭ -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Aldonu la JAR-dosieron kun la komuna biblioteko al la classpath.<br/><br/>"
				+ "Tiuj teknikaj solvoj estas por spertaj uzantoj. Plej multaj uzantoj devus provi "
				+ "reinstali Minecraft antaŭ ol ŝanĝi tiujn parametrojn.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ID-Konflikto: La ID <strong>" + id
				+ "</strong> jam okupita de <strong>" + modOrigen + "</strong> dum provo aldoni <strong>" + modDestino
				+ "</strong>. Tio okazas kiam du kromprogramoj provas uzi la saman ID por malsamaj elementoj.</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>La maksimuma permesita ID-ĝamo estas transpasita. Tio okazas kiam kromprogramoj provas registri blokojn aŭ erojn kun ID-oj ekster la permesita ĉirkaŭĵeto por via Minecraft-versio.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "ID-Konflikto";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Por solvi tion en Minecraft 1.12.2, instalu <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. Por 1.7.10, uzu <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Uzu ilojn kiel <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> aŭ <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> por solvi ID-konfliktojn.</b>";
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
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Vi ne havas la VLC-binarojn. WaterMedia bezonas la VLC-binarojn. Vi devas instali ilin mane el https://www.videolan.org/vlc/.  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "Elŝuti VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Grava eraro: La modula nomo '" + nombreModulo
				+ "' enhavas nevalidajn signojn. La parto '" + parteInvalida
				+ "' ne estas valida Java-identigilo. Tio okazas kiam mod uzas rezervitajn vortojn de Java (kiel 'true', 'class') aŭ nepermesitajn signojn en sia nomo.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "Nevalidaj Signoj en Modnomo";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "La modnomo '" + nombreModulo + "' estas nevalida ĉar ĝi enhavas '" + parteInvalida
				+ "', kiu estas rezervita vorto de Java aŭ nepermesita signo. "
				+ "Serĉu en la protokoloj kiun modon ĉi tiu nomo reprezentas (plejofte la nomo de la JAR-dosiero)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "Iru al la dosierujo de la mod kaj redaktu la dosieron <b>mods.toml</b> ene de la dosierujo <b>/META-INF/</b>. "
				+ "Ŝanĝu la valoron de <b>modId</b> por uzi nur literojn, ciferojn kaj substrekojn, sen Java-rezervitaj vortoj";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "Ekzemplo de valida nomo: 'truemod_shot_enchantment' anstataŭ 'true.shot.enchantment'. "
				+ "Memoru ke modnomoj ne povas enhavi punktojn, strekojn aŭ Java-rezervitajn vortojn kiel 'true', 'false' aŭ 'class'";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Grava eraro kun la mod: '" + nombreJar
				+ "'. Mankas la deviga kampo 'mandatory' en ĝiaj dependoj. Tio okazas kiam la dosiero mods.toml ne specifas ĉu dependo estas deviga.</b>";
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
		return "Malfermu la dosieron <b>mods.toml</b> ene de la dosierujo <b>/META-INF/</b> de la mod <b>" + nombreJar
				+ "</b>";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "En la dependecoj sekcio, certiĝu ke ĉiu enigo inkludu <b>mandatory=true</b> aŭ <b>mandatory=false</b> (ekz: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Grava eraro ĉe la mod: '" + nombreJar
				+ "'. Nevalida konfiguro de access transformer. Tio okazas kiam la konfigura dosiero havas malĝustan sintakson aŭ referencojn al neekzistantaj klasoj/metodoj.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Nevalida Access Transformer";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "La problemeca mod estas: <b>" + nombreJar
				+ "</b>. Ĉi tiu mod enhavas nevalidan konfiguron de access transformer";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "Malfermu la dosieron <b>accessTransformer.cfg</b> ene de la mod <b>" + nombreJar
				+ "</b> (plejofte en la ĉefa dosierujo de la JAR-dosiero)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "Ĝustigu la sintakson de la access transformer. Linioj devas sekvi la formaton: <b>access class.method</b> (ekz: public net.minecraft.world.entity.Entity.func_200560_a). Forigu liniojn kiuj referencas klason aŭ metodon kiuj ne ekzistas en via Minecraft-versio";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Grava eraro: Malsameco inter la mod-ID en la @Mod notacio kaj la dosiero mods.toml. La mod '"
				+ nombreMod + "' ne povas ŝargiĝi ĉar la ID-oj ne samas.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "Malsameco inter @Mod kaj mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "La en-disvolviĝanta mod '" + nombreMod
				+ "' havas malsamecon inter la ID en la <b>@Mod</b> notacio kaj la valoro en <b>mods.toml</b>";
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

		return "<b style='color:#" + config.obtenerColorError() + "'>Grava eraro: Prenas ŝargi klason '" + nombreClase
				+ "' en la " + plataforma + " medion, sed ĝi estas dizajnita por " + plataformaOpuesta
				+ ". <b>Uzu la funkcion 'Modarbo' en la flankoŝranko por trovi kiun modon provas ŝargi tiun klason</b>. "
				+ "Modoj estas konstruitaj specife por unu platformo kaj ne funkcias en la alia.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "Modo en Malĝusta Platformo";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "En la langeto <b>Modarbo</b> (dekstre), serĉu referencojn al la klaso <b>" + nombreClase
				+ "</b> por identigi kiun modon kaŭzas la problemon";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "kliento" : "servilo";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "servilo" : "kliento";

		return "La identigita mod estas <b>" + plataformaOpuesta + "</b> mod kiu ne devas uziĝi en via " + plataforma
				+ " medio.";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "Forigu la probleman modon el via dosierujo <b>modoj</b>. Se vi bezonas similan funkciecon por tiu platformo, "
				+ "serĉu alternativan modon specife dizajnitan por <b>kliento</b> aŭ <b>servilo</b> laŭbezone";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Grava eraro: Mankas metadatenoj por modid '").append(modIdFaltante).append("'. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("Jenaj modoj povas kaŭzi la problemon: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", kaj aliaj...");
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
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", kaj aliaj...");
			paso.append("</b>. Uzu la funkcion <b>Modarbo</b> por konfirmi kiun modon kaŭzas la problemon");
			return paso.toString();
		}
		return "Mod provas dependi de '".concat(modIdFaltante).concat(
				"', sed tiu mod ne estas instalita. Uzu la funkcion <b>Modarbo</b> por identigi kiun modon kaŭzas la problemon");
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Vi havas du elektojn:<br/>"
				+ "1. <b>Instalu la mankantan modon</b>: Serĉu kai instalu la modon kun ID '".concat(modIdFaltante)
						.concat("'<br/>")
				+ "2. <b>Forigu la dependan modon</b>: Se vi ne bezonas la funkcion, forigu la modon kiun dependas de '"
						.concat(modIdFaltante).concat("'");
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Se '".concat(modIdFaltante).concat("' estas biblioteko (kiel 'forge', 'minecraft', 'curios'), ")
				+ "certigu ke vi havas la ĝustajn versiojn de Minecraft kaj Forge instalitajn. "
				+ "Se estas regula mod, kontrolu ĉe sia elŝuta paĝo la antaŭajn postulojn.";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Averto: Malsukcesis lanĉi sonan sistemon. Sonoj kaj muziko estas malebligitaj. Ĉi tiu eraro kutime rilatas al la aldonaĵo SoundPhysicsMod kaj povas okazi pro konfliktoj kun aliaj sonaj bibliotekoj.</b>";
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
		mensaje.append("Grava eraro: La klaso '").append(nombreClase)
				.append("' estas registrita kiel eventa aŭskultanto sed ne enhavas validajn metodojn. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Ĉi tiu klaso troviĝas en jenaj aldonaĵoj: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", kaj aliaj...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Tio okazas kiam klason oni registras por aŭskulti eventojn sed ĝi ne havas metodojn kun @SubscribeEvent marko.");
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
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", kaj aliaj...");
			paso.append("</b>. Ĉi tiuj aldonaĵoj provas registri eventojn sen validaj metodoj");
			return paso.toString();
		}
		return "La klaso <b>" + nombreClase
				+ "</b> estis registrita por aŭskulti eventojn sed ne enhavas metodojn kun marko <b>@SubscribeEvent</b>. Uzu la funkcion <b>Modarbo</b> por identigi kiun aldonaĵon enhavas ĉi tiun klason";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "En la fontkodo, certigu ke la klaso <b>" + nombreClase + "</b> enhavu almenaŭ unu metodon kun: "
				+ "<b>@SubscribeEvent public void nomoDeMetodo(SpecifaEvento evento) { ... }</b>. "
				+ "Se estas ena klaso, certigu ke ĝi ne estas markita kiel static";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("Por la identigitaj aldonaĵoj (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", ktp.");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("kontaktu la programiston de ĉi tiu aldonaĵo por ripari la problemon. ");
			} else {
				paso.append("kontaktu la programistojn de ĉi tiuj aldonaĵoj por ripari la problemon. ");
			}
		}

		paso.append(
				"Se vi estas programisto, forigu la registron de ĉi tiu klaso en la EventBus aŭ aldonu validajn @SubscribeEvent metodojn");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Grava eraro: Okazis escepto 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' dum prilaborado de la dosiero '"
				+ nombreArchivo
				+ "'. Ĉi tiu specifa eraro indikas ke la lanĉilo malsukcesis ĝuste elŝuti aŭ elpaketi la dosierojn de la modopako. "
				+ "La mesaĝo 'zip END header not found' montras ke la JAR-dosiero estas neprofunda aŭ difektita, kio estas tre ofta ĉe lanĉiloj kiuj malbone prilaboras grandajn dosierojn. "
				+ "Ĉi tiu problemo ĉefe tuŝas uzantojn de Twitch/CurseForge, Technic Launcher, kaj precipe Luna Pixel, ĉar tiuj lanĉiloj ofte malsukcesas kontroli la plenan integrecon de elŝutitaj dosieroj. "
				+ "Uzantoj de Luna Pixel devus konsideri ATLauncher kiel pli stabilan alternativon, kiu pli bone prilaboras dosierajn integrecojn kaj evitas ĉi tiun eraron. "
				+ "La sistemo ne povas ŝargi la modojn ĉar la ZIP-formo estas difektita, malebligante al Forge legi la necesajn rimedojn por komenci la ludon.</b>";
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
		return "Ĉu ebligi ProxySysOutSysErr?\n\n"
				+ "Ĉi tiu opcio donas al CrashDetector aliron al System.out kaj System.err kiam la lanĉilo ne provizas logojn.\n\n"
				+ "Ĝi nur devus esti ebligita se vi ne povas alglui registron mane.\n\n"
				+ "Averto: Ĉi tio povas interferi kun iuj aldonaĵoj aŭ lanĉiloj.\n\n"
				+ "Necesas restarti la ludon/aplikon por ke la ŝanĝoj efektiviĝu.";
	}

	@Override
	public String confirmacionTitulo() {
		return "Konfirmo";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr sukcese ebligita.\n\n"
				+ "Necesas restarti CrashDetector por ke la ŝanĝoj efektiviĝu.";
	}

	@Override
	public String informacionTitulo() {
		return "Informo";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("Grava eraro: AzureLib kaj GeckoLib frue ĉifis! ");
		} else if (azureLibError) {
			mensaje.append("Grava eraro: AzureLib frue ĉifis! ");
		} else if (geckoLibError) {
			mensaje.append("Grava eraro: GeckoLib frue ĉifis! ");
		}

		mensaje.append(
				"Tiu eraro okazas kiam oni provas uzi Fabric aldonaĵojn per ne-Fabric versioj de tiuj bibliotekoj. ");

		if (connectorPresente) {
			mensaje.append(
					"Kompateca aldonaĵo (Sinytra Connector aŭ specialcompatibilityoperation) estis detektita, kio indikas ke vi provas ruli Fabric aldonaĵojn en Forge aŭ FeatureCreep medion. ");
			mensaje.append(
					"Rigardu la 'FabricMC komenca eraro' en la protokoloj por trovi kiun precizan aldonaĵon kaŭzas la problemon. ");
		}

		mensaje.append(
				"AzureLib kaj GeckoLib estas esencaj por animaciaj aldonaĵoj, sed ili devas kongrui kun la ĝusta platformo (Fabric aŭ Forge). ");
		mensaje.append("La ludo ne povas bone ŝargi animaciajn aldonaĵojn pro tiu komenca konflikto.");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "Biblioteko Komencis Tro Frue";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "Rigardu la eraron 'FabricMC komenca eraro' en la protokoloj por trovi la probleman aldonaĵon";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "Certigu ke vi uzas la ĝustan version de AzureLib/GeckoLib por via platformo (Forge aŭ Fabric)";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Grava eraro: Malkongrueco inter C2ME kaj konektecaj aldonaĵoj. "
				+ "Tiu eraro okazas ĉar C2ME provas aliri internajn Java komponantojn kiuj estas limigitaj en medioj kun "
				+ "Sinytra Connector aŭ specialcompatibilityoperation aŭ aliaj Fabric/Forge kompatiblaj aldonaĵoj. "
				+ "<b>C2ME ne estas kompatibla kun tiuj medioj, sed <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> estas la rekomendita anstataŭaĵo</b> kiu bone funkcias "
				+ "kun konektecaj aldonaĵoj. La ludo ne povas lanĉiĝi pro Java sekurecaj permesaj konfliktoj.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "C2ME Malkongrueco kun Konektecaj Aldonaĵoj";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "Forigu C2ME el via dosierujo de aldonaĵoj";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "Elŝutu kai instalu <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> anstataŭe (kompatibla kun Sinytra Connector)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "Certigu ke ĉiuj konektecaj aldonaĵoj (kiel Sinytra Connector) estas ĝisdatigitaj al sia plej nova versio";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Grava eraro: Malsukcesis ŝargi JEI-pluginon por la aldonaĵo '" + modId + "'. La klaso '"
				+ nombreClase + "' (plugin-ID: '" + pluginId + "') ĉifaris eraron kaŭzan de ĵetasĝo dum lanĉo. "
				+ "Tiu problemo okazas kiam aldonaĵo havas nekompatiblan aŭ difektitan JEI-integron kiun ĉesigas la komencan inicialigon.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "Malsukcesa JEI-plugin - Kaŭzas ĵetasĝon";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "La aldonaĵo <b>" + modId
				+ "</b> enhavas difektitan JEI-pluginon kaŭzan de la ĵetasĝo. Uzu la funkcion <b>Mod-Arbo</b> por konfirmi kiun aldonaĵon kaŭzas la problemon";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "Tempe forigu la aldonaĵon <b>" + modId
				+ "</b> el via aldonaĵdosierujo por kontroli ĉu tio solvas la ĵetasĝon";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "Serĉu ĝisdatigojn por la aldonaĵo <b>" + modId
				+ "</b> aŭ kontaktu ĝian programiston pri la JEI-plugin problemo. "
				+ "Dume, la aldonaĵo devas esti forigita por povi lanĉi la ludon";
	}

	@Override
	public String tituloLectador() {
		return "Legilo de Ĵurnaloj - Detektilo de Ĵetasĝoj";
	}

	@Override
	public String obtenerTituloLeyenda() {
		return "KOLORKLAVO";
	}

	@Override
	public String obtenerErrorEnLeyenda() {
		return "Gravaj eraroj";
	}

	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "Stakspuroj";
	}

	@Override
	public String obtenerTituloError() {
		return "Eraro";
	}

	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "Okazis eraro dum prilaborado de la elektita linio";
	}

	@Override
	public String obtenerNombreError() {
		return "NOMO DE ERARO";
	}

	@Override
	public String obtenerDescripcionError() {
		return "DETALA PRISKRIBO";
	}

	@Override
	public String obtenerSeleccionarConsola() {
		return "ELEKTU ĴURNAOLON";
	}

	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "Nerilata eraro";
	}

	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "Grava eraro detektita en la ĵurnaloj. " + "Bonvolu ekzameni la stakspuron por trovi la ĉefan kaŭzon.";
	}

	@Override
	public String obtenerErrorLecturaArchivo() {
		return "Ne povis legi la ĵurnalan dosieron. "
				+ "Bonvolu kontroli, ĉu la dosiero ekzistas kaj havas legrajtojn.";
	}

	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "Ĵurnala Analizilo";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Grava eraro: Malsukcesis registri aŭtomatajn eventajn abonantojn por la aldonaĵo '")
				.append(modId).append("'. ");

		mensaje.append("Problemeca klaso: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Ĉi tiu klaso troviĝas en: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", kaj aliaj...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Tiu eraro okazas kiam aldonaĵo provas aŭtomate registri klason kiel eventan abonanton, sed la klaso ne povas esti ŝargita. ");
		mensaje.append(
				"<b>Ekzamenu aliajn erarojn en la protokolo, ĉar la vera kaŭzo povas esti antaŭa malsukcesa ŝargo</b>.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "Malsukceso ĉe Registro de Aŭtomataj Abonantoj";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "La aldonaĵo <b>" + modId + "</b> provas registri la klason <b>" + nombreClase
				+ "</b> kiel aŭtomatan abonanton, sed malsukcesis. Kontrolu ĉu ĉi tiu klaso ekzistas kaj alireblas";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder(
					"La problemeca klaso <b>" + nombreClase + "</b> troviĝas en ĉi tiuj dosieroj: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", kaj aliaj...");
			paso.append("</b>. ");
			paso.append("Uzu la funkcion <b>Modarbo</b> por konfirmi en kiun dosieron estas la problemeca klaso");
			return paso.toString();
		}
		return "La klaso <b>" + nombreClase + "</b> ne troviĝas en iu ajn aldonaĵa dosiero. Kontrolu ĉu la aldonaĵo <b>"
				+ modId + "</b> estas ĉeeste instalita. Uzu la funkcion <b>Modarbo</b> por helpi trovi la problemon";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "Ĝisdatigu la aldonaĵon <b>" + modId
				+ "</b> al la plej nova versio kompatibla kun viaj versioj de Minecraft kaj Forge. "
				+ "Se la problemo daŭras, kontaktu la ellaboranton de la aldonaĵo kiu raportas la eraron per la problemeca klaso";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "Ekzamenu <b>aliajn erarojn en la protokolo</b> antaŭ ol ĉi tiu mesaĝo, ĉar la vera problemo povas esti antaŭa malsukcesa ŝargo. "
				+ "Iomatempe antaŭa eraro malebligas ŝargi necesajn klasojn por eventa registro";
	}

	@Override
	public String limpiado() {
		// TODO Auto-generated method stub
		return "Purigite";
	}

	@Override
	public String original() {
		// TODO Auto-generated method stub
		return "Originala";
	}

	@Override
	public String verEnConsola() {
		// TODO Auto-generated method stub
		return "Vidi en ĵurnalo";
	}

	@Override
	public String advertencia() {
		// TODO Auto-generated method stub
		return "Averto";
	}

	@Override
	public String fatal() {
		// TODO Auto-generated method stub
		return "Fatale";
	}

	@Override
	public String noRegistroDeBattly() {
		// TODO Auto-generated method stub
		return "BattlyLauncher ne havas registron aŭ konzolon por kopii. Vi povas uzi ProxySysOutSysErr por interkapti STDOUT kaj STDERR kai restarti la ludon, sed ProxySysOutSysErr povas malboni kun aldonaĵoj kiuj modifas STDOUT aŭ STDERR";
	}

	@Override
	public String noRegistroDeNightWorld() {
		// TODO Auto-generated method stub
		return "Vi devas ebligi sencivan reĝimon en la agordo de NightWorld por ricevi lanĉan registron. Tio estas tre grave, precipe ĉar inkluzivas STDOUT kaj STDERR";
	}

	@Override
	public String noRegistroDeMCServidor() {
		// TODO Auto-generated method stub
		return "Vi devas konservi aŭ alglui la enhavon de via servila terminalo, ĉar ĝi enhavas informon ne trovitan en aliaj logoj, inkluzive de STDOUT, STDERR kai aliajn erarojn. Bonvolu alglui la enhavon de via lasta sesio. Por estonteco, vi povas savu la terminalan eligon al dosiero cd_launcherlog. Por eviti algluon, aldonu >> cd_launcherlog post via komenco-komando, kiel montrite sur la bildo. Rimarku ke tio malebligos videblon en la terminalo; nur videblas en tiu dosiero post tio.";
	}

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("Grava eraro: LexForge-transformilo detektita en NeoForge-medio. ");
		sb.append("</b>");

		sb.append("Rilata klaso: <b>").append(claseReceptora).append("</b>. ");
		sb.append("La influita interfaceto estas <b>").append(interfazObjetivo).append("</b>, ");
		sb.append("kaj mankas la metodo <b>").append(firmaMetodoFaltante).append("</b>. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("La klaso troviĝis en: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", ktp...");
			sb.append("</b>. ");
		} else {
			sb.append("Neniu JAR-dosiero enhavas tiun klason; ĝi povas esti ŝancelita aŭ inkludita kiel jar-en-jar. ");
		}

		sb.append(
				"Tiu malsukceso okazas kiam transformilo/afero de ModLauncher kompilita por MinecraftForge/LexForge ");
		sb.append("oniŝas sub NeoForge per nekompatibla versio de la ModLauncher-API. ");
		sb.append("Ĝisdatigu aŭ anstataŭigu la komponenton por NeoForge.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "LexForge Transformilo uzita en NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "Identigu la nekompatan transformilon: <b>" + claseReceptora + "</b>. " + "La atendita API estas <b>"
				+ interfazObjetivo + "</b> kiu bezonas <b>" + firmaMetodoFaltante + "</b>. "
				+ "Kontrolu ĉu la aldonaĵo registras tiun klason en <b>META-INF/services</b> kaj forigu ĝin aŭ malaktivigu en NeoForge.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Loko de la rilataj aldonaĵoj: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", ktp...");
			sb.append("</b>. ");
		} else {
			sb.append("Neniu JAR trovita kun tiu klaso. Kontrolu jar-en-jar kaj ŝancelitajn dependecojn. ");
		}
		sb.append("Tempe forigu tiujn JAR-ojn aŭ uzu versiojn kompatiblajn kun NeoForge por konfirmi la originon.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "Anstataŭigu la komponenton per specifa versio por NeoForge aŭ remalfraku ĝin kontraŭ la "
				+ "ModLauncher-versio uzitan de NeoForge. Evitu malnovajn binarojn de LexForge/MinecraftForge.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "Purigu vian mods-dosierujon kaj forigu duoblajn jar-en-jar-erojn. Viŝu lanĉilan kaŝmemoron se necese "
				+ "kai restartu por certiĝi ke neniu LexForge-transformilo plu estos ŝargita.";
	}

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia ne povas lanĉiĝi: Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("ne estas kompatibla.</b> ");
		sb.append("Forigu Xenon kai uzu anstataŭe Embeddium aŭ Sodium. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Malkovrite en: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", ktp...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia nekompatibla kun Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "Malkovriĝis " + label + " nekompatiblan kun WaterMedia. Forigu ĝin el via profilo.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("Lokoj: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", ktp...");
			sb.append("</b>. Forigu tiun JAR-on.");
			return sb.toString();
		}
		return "Neniu JAR trovita. Kontrolu vian mods dosierujon kai forigu Xenon.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "Instalu Embeddium aŭ Sodium kiel anstataŭon kai restartu la ludon.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "Eraro dum kunpremado (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>Deflater fermiĝis dum kopio de rimedoj de TACZ.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Rilata al: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", ktp");
			sb.append("</b>. ");
		}
		sb.append("<br/><b>Solvo:</b> en <code>tacz/tacz-pre.toml</code> agordu <code>DefaultPackDebug=true</code>. ")
				.append("Se necese, unue generu mapon, poste aktivigu ĝin.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "En tacz/tacz-pre.toml agordu DefaultPackDebug=true. Se necese, unue generu mapon, poste aktivigu ĝin.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "Nerilataj densofunkcioj";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>Mankas densecaj funkcioj en la registro.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("Mankantaj: ");
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
				"<br/><b>Solvo:</b> instalu aŭ aktivigu la mod/datapack, kiu difinas tiujn funkciojn, kaj restartigu. Alia komuna kialo por tiu ĉi problemo estas, kiam vi havas la bezonatan modon, sed ĝi havas problemon aŭ konflikton kun alia mod; ekzemple, Terralith havas multajn problemojn kaj povas kaŭzi tiun ĉi eraron kaj aliajn, inkluzive de eraroj kun JSON.");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "Instalu aŭ aktivigu la modon/datopakon kiun provizas tiujn funkciojn kai restartu la ludon.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// Mallonga mesaĝo per erara koloro, eksplicite menciante la aldonaĵon
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Registroenskribo ne ĉeesta: ").append(claveFaltante).append(". ");
		sb.append("Ofte okazas kun la alfaversio de Steam & Railways por Create 6.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (alfa)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "Forigu aŭ anstataŭigu la alfaversion de Steam & Railways por Create 6 per kompatibla versio.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// Mallonga, per erara koloro kaj rekta rekomendo
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Ŝarĝa konflikto: Multiworld kun Sodium/Embeddium/Rubidium kaŭzas ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance). ")
				.append("Propono: ĉesu uzi Multiworld aŭ la rapidigan aldonaĵon, aŭ uzas kompatiblajn versiojn.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "Konflikto: Multiworld kun rapidigaj aldonaĵoj";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Malinstalu Multiworld aŭ Sodium/Embeddium/Rubidium, aŭ ĝisdatigu al interkompatiblaj versioj.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Sodium detektis nekompatan grafikan ŝoforon. "
				+ "Ĝisdatigu vian GPU-ŝoforon al la minimume postulita aŭ sekvu la gvidilon de Sodium." + "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "Eraro de OpenGL-kunteksto";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OpenGL malsukcesis: neniu nuna kunteksto aŭ funkcio ne disponeblas en tiu kunteksto. "
				+ "Eble estas problemo kun videaj ŝoforoj." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "Ĝisdatigu/reinstalu viajn GPU-ŝoforojn kai restartu; ĉesigu suprenmetojn kai provu sen rapidigaj aldonaĵoj.";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "Ligilo kopita en la tondejon.";
	}

//Esperanto
	@Override
	public String buscarDentroDeComprimidos() {
		return "Serĉi ene de arkivoj (.zip/.jar/.war/.ear/.fpm/.rar por Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Eraro pri tekstura risolucio: ne povas adapti " + recurso + " - grandeco: " + tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "Eraro pri Tekstura Risolucio";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "Tiu eraro okazas kiam teksturoj estas tro grandaj aŭ ekzistas tro da rimedopakoj. "
				+ "Provu uzi rimedopakojn kun pli malalta risolucio aŭ forigu kelkajn. "
				+ "Certigu, ke vi ne aldonis kutimajn teksturojn per pli alta ol permesita risolucio.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Eraro de ModLauncher-servoj: vojo kun nevalidaj signoj. "
				+ "ModLauncher-servoj ne povas prilabori vojojn kiuj enhavas ne-ASCII aŭ specialajn signojn. "
				+ "Problemaj signoj inkludas: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요, kiel ankaŭ precipe la signo '\"' se ĝi troviĝas je fino de nomo. "
				+ "Komunaj servoj en ModLauncher inkludas CrashDetector, " + Config.obtenerInstancia().obtenerNombreCD()
				+ ", FeatureCreep, Vivicraft, Optifine, Sodium, clonos, Iris Shaders/Oculus, MixerLogger, CrashAssistant kaj Sintrya Connector. "
				+ "Vi povas forigi ĉiujn servojn, sed eble estos aliaj problemoj pro via voja nomo. "
				+ "Solvado: Ŝanĝu la nomon de via instanco por uzi nur ASCII-signojn (a-z, A-Z, 0-9), sen spacetoj aŭ specialaj signoj.</b>";
	}

	@Override
	public String nombre_error_modlauncher_path() {
		return "Eraro pri Vojo en ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "Tiu eraro okazas kiam la instanca vojo enhavas ne-ASCII aŭ specialajn signojn. "
				+ "ModLauncher-servoj ne povas pritrakti tiujn vojojn. "
				+ "Solvado: ŝanĝu la instancan nomon por uzi nur ASCII-signojn (a-z, A-Z, 0-9), evitu spacetojn kaj specialajn signojn. "
				+ "Prestu apartan atenton al la signo '\"', kiu tre problemas, precipe se ĝi estas je fino de nomo.";
	}

	@Override
	public String tituloEditorCodice() {
		return "Redaktilo de Codice";
	}

	@Override
	public String nuevo() {
		return "Nova";
	}

	@Override
	public String actualizarSeleccionado() {
		return "Ĝisdatigi elektitan";
	}

	@Override
	public String eliminarSeleccionado() {
		return "Forigi elektitan";
	}

	@Override
	public String exportarJSON() {
		return "Eksporti JSON...";
	}

	@Override
	public String guardarTodo() {
		return "Konservi ĉion";
	}

	@Override
	public String general() {
		return "Ĝenerala";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "Teksto por serĉi";
	}

	@Override
	public String filtro() {
		return "Filtraĵo (id)";
	}

	@Override
	public String criticalidad() {
		return "Graveco (AVERTO/ERARO/FATALE)";
	}

	@Override
	public String prioridad() {
		return "Prioritato";
	}

	@Override
	public String lista() {
		return "Kontroloj";
	}

	@Override
	public String colIdioma() {
		return "Lingvo";
	}

	@Override
	public String colNombre() {
		return "Nomo";
	}

	@Override
	public String colResultado() {
		return "Rezulto";
	}

	@Override
	public String vistaJson() {
		return "Antaŭvido de JSON";
	}

	@Override
	public String idiomas() {
		return "Lingvoj (ĉiuj devigaj)";
	}

	@Override
	public String elegirFiltro() {
		return "Elekti...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "Bonvolu elekti filtrilon";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "Disponeblaj filtriloj";
	}

	@Override
	public String faltanCampos() {
		return "Plenu ĉiujn devigajn ĝeneralajn kampojn.";
	}

	@Override
	public String critInvalida() {
		return "Nevalida graveco. Uzu AVERTO, ERARO aŭ FATALE.";
	}

	@Override
	public String filtroNoExiste() {
		return "La indikita filtrilo ne ekzistas.";
	}

	@Override
	public String faltanIdiomas() {
		return "Bonvolu plenumi nomon kaj rezulton por ĉiuj lingvoj:";
	}

	@Override
	public String verificacionInvalida() {
		return "Unu kontrolo estas nevalida. Bonvolu revizii la kampojn.";
	}

	@Override
	public String guardadoOk() {
		return "Konservite sukcese.";
	}

	@Override
	public String editorCodiceBoton() {
		return "Aldoni kaŭzojn";
	}

	@Override
	public String descripcionEditorCodice() {
		return "Vi povas registri kaŭzojn ĉi tie. Vi bezonas ID-on, tekston sen specialaj signoj, akcentoj aŭ spacetoj. Por filtriloj vi povas uzi \"linio enhavas\" por serĉi tekston en linio, \"ĉio enhavas\" se la protokolo havas tekston, \"regex linio\" se linio konformas regulan esprimon, kai \"regex ĉio\" (rekomencas uzi liniajn versiojn). Vi devas agordi gravecon: FATAL, ERARO aŭ AVERTO por la koloroj. Por ĉiuj lingvoj vi devas tajpi nomon kai respondon por la ekrano. Vi povas aldoni pliajn kontrolojn aŭ forigi aliajn. Vi konservas post kompletigo.";
	}

	@Override
	public String descartarCambios() {
		return "Ĉu ĉesi senkonservitajn ŝanĝojn en la nuna kontrolo?";
	}

	@Override
	public String confirmacion() {
		return "Konfirmo";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "Ĉu vi volas konservi viajn ŝanĝojn antaŭ ol eliri?";
	}

	@Override
	public String salirSinGuardar() {
		return "Eliri sen konservi";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Grava eraro: Malsukcesis ŝargi servon de modlauncher (IDependencyLocator).<br>");
		sb.append("🔹 <b>Problema klaso:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>Afektita aldonaĵo:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append(
					"🔸 <b>Aldonaĵo neidentigita.</b> Bonvolu kontroli lastatempe instalitajn, evoluajn aŭ malbone pakitajn aldonaĵojn.<br>");
		}

		sb.append("🔸 <b>Kaŭzo:</b> La dosiero <code>META-INF/services/...</code> de la aldonaĵo estas difektita, ");
		sb.append("nekompatibla kun via versio de Forge/NeoForge, aŭ la aldonaĵo estas por malĝusta versio.<br>");
		sb.append("🔸 <b>Konsekvenco:</b> Forge/NeoForge ne povas registri dependecojn de la aldonaĵo, ");
		sb.append("tio malebligas lanĉon de la ludo.<br>");
		sb.append("🔸 <b>Solvo:</b> Ĝisdatigu, reinstalu aŭ forigu la probleman aldonaĵon. ");
		sb.append(
				"Se vi uzas evoluajn aldonaĵojn, certigu ke ili estas kompilitaj por via ekzakta Forge/NeoForge-versio.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "Eraro pri Servokonfiguracio (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. Identigu la kaŭzan aldonaĵon: kontrolu antaŭnelonge instalitajn aŭ evoluajn aldonaĵojn.";
		}
		return "1. La problemeca aldonaĵo estas: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. Ĝisdatigu, reinstalu aŭ forigu la aldonaĵon. Certigu uzi version kompatan kun via Forge/NeoForge.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>Grava eraro: Neksista metodo.</b><br>"
				+ "La aldonaĵo provis voki la metodon <b style='color:#" + colorCodigo + "'>" + metodo + "</b>, "
				+ "kiu ne ekzistas en tiu ĉi versio de la ludo aŭ alia aldonaĵo.<br>" + "<span style='color:#"
				+ colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "Neksista Metodo (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. Tiu eraro okazas kiam aldonaĵo ne estas kompatibla kun la nuna versio de la ludo aŭ alia aldonaĵo.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. Ĝisdatigu ĉiujn rilatajn aldonaĵojn. Se daŭras, raportu la eraron al la verkinto de la tuŝita aldonaĵo.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>Grava eraro: Neksista kampo.</b><br>"
				+ "La aldonaĵo provis aliri kampon <b style='color:#" + colorCodigo + "'>" + campo + "</b>, "
				+ "kiu ne ekzistas en tiu ĉi versio de la ludo aŭ alia aldonaĵo.<br>" + "<span style='color:#"
				+ colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "Neksista Kampo (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. Tiu eraro kutime okazas kiam aldonaĵo ne estas kompatibla kun la nuna versio de la ludo aŭ alia aldonaĵo.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. Ĝisdatigu ĉiujn tuŝitajn aldonaĵojn. Se daŭras, kontaktu la verkinton de la aldonaĵo kaŭzanta la eraron.";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();
		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>Ĉu vi bezonas helpon?</strong><br>"
				+ "  Se vi ne scias kiel ripari ĉi tion aŭ se la kaŭzo ne estas tie ĉi, vi povas ricevi helpon per niaj sociaj retoj. "
				+ "  Uzu la butonon <img src='" + iconoCompartir
				+ "' alt='Komunigi' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>Komunigi</strong> por akiri ligilojn al viaj protokoloj kai rezultoj por nia teamo. "
				+ "  Se vi estas kreinto de modpack aŭ korporacio, redaktu <code>crash_detector/plantilla.htm</code> "
				+ "  por alĝustigi viajn teamajn ligilojn." + "</div>";
	}

	@Override
	public String restablecerPlantilla() {
		return "Restarigi ŝablonon";
	}

	@Override
	public String restablecer() {
		return "Restarigi";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		return "Ĉu restarigi " + nombreImagen + " al defaŭltaj valoroj?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		return "Ĉu restarigi la ŝablonon al defaŭltaj valoroj?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Mankas klasoj de AzureLib. Se vi jam havas AzureLib, bonvolu instali version antaŭ la 8-a de oktobro 2025. Tio estis komune. Se vi ne havas AzureLib, instalu la aktualan version.</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "La aldonaĵo <code>healight</code> kaŭzas gravan eraron: <code>java.lang.NoSuchFieldError: INT</code>. "
				+ "Tiu eraro okazas ĉar la aldonaĵo provas aliri kampon kiu jam ne ekzistas en MCForge 47.10 por Minecraft 1.20+. "
				+ "La ludo ne povas lanĉiĝi pro tiu problemo.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• Forigu aŭ ĝisdatigu la aldonaĵon <code>healight</code>. "
				+ "La nuna versio ne estas kompatibla kun MinecraftForge 47.10 por 1.20.1. "
				+ "Serĉu pli novan version de la aldonaĵo aŭ pripensu uzon de alternativo.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "Grava eraro: healight - Kampo 'INT' ne trovita";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("La klaso <code>").append(clase)
				.append("</code> ne realigas la postulitan metodon:<br>").append("<code>").append(metodo)
				.append("</code><br>").append("el la interfaceto <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br><br>Ĉefa afero aŭ dosiero: <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• Tiu eraro okazas kiam aldonaĵo realigas interfaceton sed preterprenas devigan metodon. "
				+ "Ĝisdatigu <b>ambaŭn aldonaĵojn</b> implikitajn (tiun kiun difinas la interfaceton kai tiun kiun ĝin realigas). "
				+ "Se vi ne scias kiuj estas, serĉu la nomojn videblajn en la erarmesaĝo.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "Nerealigita Interfaceta Metodo (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Aldonaĵo provas ŝargi klason de la <b>klienta flanko</b> "
				+ "(<code>AnimationMetadataSection</code>) sur <b>diferencitan servilon</b>, kio estas neebla. "
				+ "Tiu eraro kutime aperas kiam aldonaĵo ne bone apartigas siajn kodon por kliento kaj servilo. "
				+ "La ĉeesto de <code>ModernFix</code> povas malkovri tiun problemon, kvankam ĝi ne estas rektan kaŭzon.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>Rapida opcio:</b> Forigu tempore <code>ModernFix</code> por konfirmi ĉu la servilo lanĉiĝas. "
				+ "Se jes, do la problemo estas en alia aldonaĵo kiun ŝargas klientajn klasojn sur servilon.<br>"
				+ "• <b>Daŭra solvo:</b> Identigu la kulpan aldonaĵon (serĉu aldonaĵojn kun animaciitaj rimedoj, ĉefaj teksturoj aŭ grafikaj bibliotekoj) kai ĝisdatigu aŭ forigu ĝin.<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "Client-side class ŝargita sur servilo (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "La agorda dosiero de <code>Sinytra Connector</code>-aldonaĵo estas difektita. "
				+ "Tio ofte okazas kiam la dosiero plenas per nulaj signoj (<code>\\u0000</code>) "
				+ "pro neatendita ĉesigo de la ludo, skribaj malsukcesoj aŭ aldonaĵaj konfliktoj.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• Iru al la dosierujon <code>config/</code> de via Minecraft-instanco.<br>"
				+ "• Serĉu kai forigu la agordojn de connector-aldonaĵoj.<br>"
				+ "• Restartu la ludon: Sinytra Connector estos kreinta novan, puran agordan dosieron.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "Difektita Agordo de Sinytra Connector";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "La dosiero <code>" + nombreJar
				+ "</code> estas difektita aŭ nekompleta.<br>"
				+ "La sistemo ne povas legi ĝian enhavon ĉar mankas la fina kapo de la ZIP-dosiero.<br>"
				+ "Tiu eraro kutime okazas post nepropra elŝuto aŭ malsukceso de lanĉilo.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "Difektita JAR-dosiero (kun specifa nomo)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>Forigu la difektitan dosieron</b> kai elŝutu ĝin denove el oficiala fonto (CurseForge, MinecraftStorage, ktp.).<br>"
				+ "• Se vi uzas lanĉilon kiel CurseForge, Technic aŭ Luna Pixel, pripensu ŝanĝi al <b>ATLauncher</b> aŭ <b>Prism Launcher</b>, "
				+ "kiuj pli bone kontrolas dosieran integrecon.<br>"
				+ "• Certigu ke via interreta konekto estu stabila dum la elŝuto.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Ne povas ŝargi mondon ĉar unu el liaj NBT-dosieroj estas difektita "
				+ "(ekzemple: <code>level.dat</code>, <code>playerdata/*.dat</code>, aŭ chunkoj).<br>"
				+ "La specifa eraro estas: <code>UTFDataFormatException: malĝusta enigo apud bajto " + byteCorrupto
				+ "</code>.<br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ Antaŭ ol provi iun riparon, faru plenan kopion de la dosierujo de via mondo.</b><br><br>"
				+ "Vi povas provi ripari la difektitan dosieron uzante <b>redaktilon por NBT</b> kiel <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>.<br>"
				+ "Se la damaĝo estas grava, uzu <b>heksan redaktilon</b> (kiel HxD) por ekzameni kai ğustigi nevalidajn bajtojn "
				+ "(nur se vi havas sperton pri la NBT-formato).<br>"
				+ "Fine, vi povas restaŭri el savkopio aŭ uzi la funkcion <i>repair world</i> de aldonaĵoj kiel <code>FTB Backup</code>.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>Faru plenan sekuran kopion de la mondo-dosierujo</b> antaŭ ol provi iun ajn riparon.<br>"
				+ "• Uzu NBT-redaktilon (kiel NBT Studio) por malfermi kai ripari la difektitan dosieron.<br>"
				+ "• Se malsukcesas, ekzamenu la dosieron per heksa redaktilo je la pozicio de la difektita bajto.<br>"
				+ "• Se vi ne havas sperton, restaŭru el lastatempa sekura kopio.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "Difektita mondo: eraro dum ŝarĝado de NBT datumoj";
	}

	@Override
	public String problema_con_openAL() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Vi havas problemon kun OpenAL. Foje peliloj Nouveau povas kaŭzi tion, sed foje la versio de OpenAL en via aplikaĵo ne estas kompatibla kun tiu en via distribuaĵo, do vi devas uzi la version el via distribuaĵo. Tio estas aparte ofta ĉe Red Hat-similaj distribuaĵoj kai kun sono-modifoj kiel Sound Physics Remastered. Vidu ĉi tiun gvidilon por plia helpo: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>Kiel solvi sonojn en Minecraft sur Linux</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "La servilo ne povas lanĉiĝi ĉar dosiero de la mondo estas blokita de alia procezo.<br>"
				+ "Tio kutime okazas se:<br>" + "• Jam ekzistas funkcianta servila instanco.<br>"
				+ "• Antiviruso aŭ dosierumilo havas malfermitan la dosierujon de via mondo.<br>"
				+ "• La antaŭa procezo ne ĝuste ĉesis kai lasis dosierojn blokitajn.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>Fermu ĉiujn servilajn instancojn</b> (inkluzive de fono-procezoj kiel javaw.exe).<br>"
				+ "• Se vi uzas gastigan panelon (kiel Multicraft), tute restartigu la servilon per la panelo.<br>"
				+ "• <b>Tempe malaktivigu vian antiviruson</b> se vi suspektas ke ĝi blokas dosierojn.<br>"
				+ "• Je lokaj sistemoj, fermu ajnan Fenestron-Eksplorilan fenestron kiun montras vian mondan dosierujon.<br>"
				+ "• Se daŭras la problemo, mane forigu la dosieron <code>session.lock</code> ene de via monda dosierujo (nur se vi certas ke neniu alia servilo funkcias).";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "Monda dosiero blokita de alia procezo";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "La aldonaĵo provis plilongigi la klason <code>" + clasePadreFinal + "</code>, "
				+ "sed tiu ĉi klaso nun estas <b>fina</b> kai ne povas esti heredita.<br>"
				+ "La problemeca klaso estas: <code>" + claseHija + "</code>.<br><br>"
				+ "Tio kutime okazas kiam aldonaĵo estas kompilita por antaŭa versio de Minecraft aŭ alia baza aldonaĵo, "
				+ "kiu markis kelkajn klason kiel <code>final</code> en lastatempaj versioj.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>Ĝisdatigu ĉiujn implikitajn aldonaĵojn</b>, precipe tiujn kiuj eble rilatas al la menciita baza aldonaĵo.<br>"
				+ "• Se daŭras la problemo, serĉu version de la aldonaĵo kompatan kun via nuna versio de Minecraft kai ties dependecoj.<br>"
				+ "• En iuj kazoj, forigo de la aldonaĵo enhavanta la filan klason povas helpi konfirmi la kaŭzon.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "Demando pri heredo de fina klaso";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Vi uzas <b>Rubidium</b> (malnovan furkilon de Sodium por Forge) kun <b>Iris aŭ Oculus</b>.<br>"
				+ "En lastatempaj versioj de Minecraft (1.19.2+), "
				+ "Rubidium ne sekvis la pacon de Sodium kai havas problemojn pri dependecoj.<br><br>"
				+ "Ĉi tiu eraro ankaŭ povas okazi pro konflikto inter rendimentaj aldonaĵoj (Sodium, Rubidium, Embeddium, Bedium, Xeonium, ktp.) aŭ Iris Shaders kun alia aldonaĵo.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>Forigu Rubidium</b> el via dosierujo <code>mods</code>.<br>"
				+ "• <b>Instalu <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a></b>, "
				+ "la aktivan kai kompatan furkilon de Sodium por Forge kiun subtenas Iris/Oculus en 1.20+.<br>"
				+ "• Certigu ke vi ne havas pli ol unu furkilon de Sodium instalitan samtempe (ekzemple: Rubidium + Embeddium).<br>"
				+ "• Se vi uzas Oculus anstataŭe de Iris, kontrolu ke ĝi ankaŭ estas kompata kun via versio de Forge kai Embeddium.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Malnova Rubidium kun Iris/Oculus (OptionInstance estas final)";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "La aldonaĵo <code>Simple Voice Chat</code> ne povas lanĉi siajn voĉan servilon ĉar "
				+ "la UDP-pordo jam estas uzata aŭ la agordita IP-adreso ne validas.<br>"
				+ "Tio ne malebligas lanĉon de la ludo, sed malŝaltas la voĉan babilejan funkcion.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• <b>Fermu ajnan alian instancon de Minecraft</b> aŭ aplikaĵon kiun uzas la UDP-pordon 24454.<br>"
				+ "• Se vi estas sur servilo, certigu ke <b>neniu alia servo</b> uzas tiun pordon.<br>"
				+ "• En la agordoj de la aldonaĵo (<code>config/voicechat/</code>), ŝanĝu la UDP-pordon al libera (ekzemple, 24455).<br>"
				+ "• Se vi uzas propran IP-adreson, kontrolu ke ĝi estas ĝusta aŭ lasu ĝin malplena por uzi la defaŭltan.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Voĉa Babilejo: UDP-pordo okupita aŭ nevalida IP";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "La BlockItem <code>" + nombreBlockItem
				+ "</code> havas nulan blokon.<br>"
				+ "Tiu eraro kutime okazas en <b>aldonaĵoj por Create</b> (kiel <code>dndecor</code>, <code>createdeco</code>) "
				+ "kiam ekzistas konfliktoj kun <code>Amendments</code>, <code>Moonshine</code>, aŭ malĝusta komencaĵo de blokoj.<br>"
				+ "<b>Noto:</b> Tio ne estas rekte eraro de Amendments, sed simptomo de pli profunda problemo pri registra ŝarĝado.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• <b>Ĝisdatigu ĉiujn rilatajn aldonaĵojn:</b> Create, Amendments, Moonshine, kai ajnan aldonaĵon (precipe <code>dndecor</code> kai <code>createdeco</code>).<br>"
				+ "• Se daŭras la problemo, <b>forigu tempore la Create-aldonaĵojn</b> unu post alia por trovi la kulpan.<br>"
				+ "• Certigu ke <b>Amendments kai Moonshine estas kompatoblaj</b> kun via versio de Create kai Forge.<br>"
				+ "• Kontrolu ĉu ekzistas beta-versioj aŭ aktualigitaj fork'oj de la problemaj aldonaĵoj.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "Nula BlockItem en Create-aldonaĵo";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>").append(
				"Malsukcesis trovi aldonaĵojn kiuj ne apartenas al iu aktiva platformo (Forge, Fabric, ktp.):<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>Tio kutime okazas kiam:<br>")
				.append("• Oni miksis aldonaĵojn de <b>Fabric kai Forge</b> en la sama dosierujo.<br>")
				.append("• Oni instalas aldonaĵon por nekompatibla versio de Minecraft.<br>")
				.append("• La aldonaĵo estas difektita aŭ ne validas kiel dosiero JAR.</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>Kontrolu ke ĉiuj aldonaĵoj estas por la sama platformo</b> (Forge <b>aŭ</b> Fabric, ne ambaŭ).<br>"
				+ "• Uzu la <b>arbo de aldonaĵoj</b> por identigi kiun platformon detektas ĉiu dosiero.<br>"
				+ "• Forigu ajnan aldonaĵon kiun vi ne rekonoas aŭ kiu estas por malsama platformo.<br>"
				+ "• Se vi uzas lanĉilon kiel CurseForge aŭ Prism, certigu ke via profilo estas ĉefe agordita.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "Aldonaĵo nekompatibla kun aktiva ŝarĝilo";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Malsukcesis krei modelon <code>" + modid
				+ ":" + nombreModelo + "</code>.<br>" + "Tio indikas ke la aldonaĵo <code>" + modid
				+ "</code> havas difektitajn, mankantajn "
				+ "aŭ nekompatiblajn rimedojn por via versio de Minecraft.</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>Ĝisdatigu la aldonaĵon</b> al la plej nova versio kompata kun via instanco.<br>"
				+ "• Se vi uzas evoluantan aŭ ĉefan version, revenu al la oficiala versio.<br>"
				+ "• Kontrolu ke la dosiero JAR ne estas difektita (reinstalu ĝin).<br>"
				+ "• Se daŭras la problemo, raportu la eraron al la verkinto de la aldonaĵo inkluzive ĉi tiun protokolon.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "Malsukcesis krei rimedan modelon";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Detektis gravan konflikton inter la aldonaĵoj <code>Moonlight</code> kai <code>Iceberg</code>.<br>"
				+ "Ambaŭ provas registri rimedajn reŝarĝajn sistemojn nekompatibile, "
				+ "kiu kaŭzas malsukceson de OpenGL pro foresto de valida grafika konteksto.<br>"
				+ "Tiu problemo estas ofta dum uzado de versioj de Forge kiuj inkludas adaptilojn por Fabric-aldonaĵoj.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>Ĝisdatigu ambaŭn aldonaĵojn</b> al iliaj lastaj versioj kompatoblaj kun via versio de Forge.<br>"
				+ "• Se daŭras la problemo, <b>forigu tempore Iceberg</b>, ĉar Moonlight kutime estas pli grava dependeco por aliaj aldonaĵoj.<br>"
				+ "• Certigu ke vi ne havas duoblan aŭ miksitajn versiojn de Forge/Fabric por tiuj ĉi aldonaĵoj.<br>"
				+ "• Kontrolu ĉu iu alia aldonaĵo (kiel Supplementaries, Citadel, ktp.) jam enhavas funkciecon de Iceberg ene.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "Grava konflikto: Moonlight kontraŭ Iceberg (OpenGL sen konteksto)";
	}

	@Override
	public String instantanea() {
		return "Bildaĵo";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "Ekde lasta bildaĵo";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "Elekti dosieron";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "Bildaĵo sukcese kreita";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "Eraro dum kreo de bildaĵo";
	}

	@Override
	public String consejo() {
		return "Konsilo";
	}

	@Override
	public String resultadoMuestra() {
		return "Montri rezulton";
	}

	@Override
	public String historaDeModsDesc() {
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>Konsilo:</b> Elektu du protokolajn dosierojn por kompari liston de aldonaĵoj. "
				+ "  La rezulto montras <span style='color:%s;'>aldonitajn (+)</span> kai "
				+ "  <span style='color:%s;'>forigitajn (&#8722;)</span> bazite sur normigitaj nomoj. "
				+ "  Uzu la butonon 'Bildaĵo' por krei kopion de ekzistanta dosiero per etendita nomo .instantanea."
				+ "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		return "Akiri retejojn de protokoloj kiel Markdown sen raporto";
	}

	@Override
	public String titulo_configuracion() {
		return "Agordo";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "Necerta eraro dum kunhavigo.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "Necerta eraro dum genero de ligiloj.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "Necerta eraro dum prilaboro de butono.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "Neniu dosiero estas ligita por malfermi.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "Dosiero ne ekzistas:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "Ne povis malfermi en redaktilo.\nLa vojo estos kopita al tondejo.";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "Ne povis malfermi dosieron; la vojo estis kopita al tondejo.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "Labortablo ne subtenata; la vojo estis kopita al tondejo.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "Vi spertas limon de petoj. Provuzu alian registran retejon aŭ alian registran API-n.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		return "Komuniki ligilon";
	}

	@Override
	public String infoDeTrazos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Rezolvi suprajn partojn de trunkoj estas unua prioritato. " + "La formato estas Nivelo, Linio. "
				+ "Ĉiuj protokoloj havas nombran sistemon. " + Verificaciones.nl_html
				+ "Ĝenerale vi bezonas serĉi plej malsuprajn nivelojn en ĉiuj protokoloj; spuradoj kun altpoziciaj niveloj kutime estas malveraj pozitivoj. "
				+ "Gravegas uzi vian kapablon rigardi en la konzolon, ĉar analizo de spuradoj ne estas perfekta kiam ekzistas multaj spuradoj."
				+ "</b>";
	}

	// --- Serĉilo pri Ordo-Kanario (Warrant Canary) ---
	/**
	 * Teksto sur la butono por la serĉilo pri ordo-kanario. Ekzemple: "Serĉilo pri
	 * ordo-kanario"
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "Serĉilo pri ordo-kanario";
	}

	/**
	 * Mesaĝo montrita en dialogo informante ke la funkcio estos disponebla baldaŭ.
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "Ĉi tiu funkcio estos disponebla baldaŭ.";
	}

	/**
	 * Titolo de la dialogo, kiu informas pri la venonta disponebleco de la serĉilo
	 * pri ordo-kanario.
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "Baldaŭ";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		return "Nekongruaj aldonaĵoj kun Crash Assistant (Malveraĵo)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		return "Nekongrua Modpack kun CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant havas liston de aldonaĵoj kiujn ĝi diras esti nekongruaj, sed ni ne havas pruvon ke ili estas nekongruaj kai la eraro estas nur angle. Se vi volas ludi per tiuj aldonaĵoj, vi povas redakti la dosieron <code>config/crash_assistant/config.toml</code> kai ŝanĝi <code>enabled = true</code> en [kompatibileco] al <code>enabled = false</code>.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant povas diri ke iuj aldonaĵoj estas nekongruaj, sed foje ili estas kompatataj kai la eraro estas nur en unu lingvo. Se vi volas uzi tiujn aldonaĵojn, vi povas redakti la dosieron <code>config/crash_assistant/problematic_mods_config.json</code> kai ŝanĝi <code>should_crash_on_startup</code> de <code>true</code> al <code>false</code>.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Eraro: La aldonaĵo '" + modId
				+ "' bezonas la aldonaĵon '" + dependencia + "'. Nuntempe, " + actual + "." + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Eraro: La aldonaĵo '" + modId
				+ "' postulas version '" + requerido + "' aŭ pli novan de '" + dependencia
				+ "', sed la aldonaĵo ne estas instalita." + "</span>";
	}

	// En la klaso MonitorDePID.idioma (aldonu tiun metodon)
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Eraro: La aldonaĵo '" + modId
				+ "' ne estas kompata kun la nuna versio de '" + dependencia + "'. "
				+ "Vi devas forigi la aldonaĵon 'Iris/Oculus & GeckoLib Compat' ĉar ĝi ne estas kompata kun Superb Warfare kai ne funkcias per la plej nova versio de GeckoLib. "
				+ "Nuna versio: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "오류: '" + clase + "' 클래스의 작업을 실행할 수 없습니다. " + "이 오류는 서로 호환되지 않거나 설치된 다른 모드와 충돌하는 모드에서 흔히 발생합니다.";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "작업 실행 실패";
	}

	public String recomendacion_fallos_ejecucion() {
		return "이러한 종류의 오류는 일반적으로 모드 간의 호환성 문제로 인해 발생합니다. " + "특히 ConnectorMod과 함께 제대로 작동하지 않는 모드에서 흔합니다.";
	}

	public String info_clase_problematica() {
		return "문제 있는 클래스:";
	}

	public String ver_en_log() {
		return "로그에서 보기";
	}

	public String no_se_encontraron_clases_problema() {
		return "실행 문제가 있는 특정 클래스를 찾을 수 없습니다.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Detektis gravan konflikton inter OptiFine kai Entity Model Features (EMF). "
				+ "Ĉi tiuj aldonaĵoj ne estas kompatataj kai kaŭzas fiaskon de enĵeto kiun malhelpas lanĉon de la ludo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "Konflikto inter OptiFine kai Entity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "Malinstalu aŭ OptiFine aŭ Entity Model Features, ĉar ili ne estas kompatataj inter si.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Detektis gravan konflikton inter OptiFine kai Fusion. "
				+ "Ĉi tiuj aldonaĵoj ne estas kompatataj kai kaŭzas fiaskon de enĵeto kiun malhelpas lanĉon de la ludo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "Konflikto inter OptiFine kai Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "Malinstalu aŭ OptiFine aŭ Fusion, ĉar ili ne estas kompatataj inter si.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel (postulita de Create) bezonas Sodium 0.6.0-beta.2 aŭ pli novan. Rubidium estas 0.5.3. "
				+ "Konsideru uzi <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> kiel alternativon."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "Konflikto inter Flywheel kai versio de Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "Ĝisdatigu Sodium al 0.6.0-beta.2 aŭ pli supre, aŭ instalu <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> kiel kompatan alternativon.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Detektis gravan konflikton inter OptiFine kai Epic Fight. "
				+ "Ĉi tiuj aldonaĵoj ne estas kompatataj kai kaŭzas fiaskon de enĵeto kiun malhelpas lanĉon de la ludo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "Konflikto inter OptiFine kai Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "Malinstalu aŭ OptiFine aŭ Epic Fight, ĉar ili ne estas kompatataj inter si.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Detektita gravaj konflikto inter OptiFine kaj Rubidium. "
				+ "Tiuj modoj ne estas kongruaj kaj kaŭzas enjekcian fiaskon, kiu malpermesas lanĉon de la ludo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "Konflikto inter OptiFine kaj Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "Forigu OptiFine aŭ Rubidium, ĉar ili ne estas kongruaj unu kun la alia.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam provas ŝargiĝi sur dediĉita servilo, sed ĝi estas kompatibla nur kun la kliento. "
				+ "Forigu FreeCam de la servilo aŭ certigu, ke ĝi estas instalita nur sur la kliento." + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam sur dediĉita servilo";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "Forigu FreeCam de la dediĉita servilo, ĉar ĝi devas esti instalita nur sur la kliento.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) provas ŝargiĝi sur dediĉita servilo, sed ĝi estas kompatibla nur kun la kliento. "
				+ "Forigu ETF de la servilo aŭ certigu, ke ĝi estas instalita nur sur la kliento." + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features sur dediĉita servilo";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "Forigu Entity Texture Features de la dediĉita servilo, ĉar ĝi devas esti instalita nur sur la kliento.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Vi devas akcepti la EULOn de Minecraft por ruli la servilon. "
				+ "Redaktu la dosieron eula.txt kaj ŝanĝu 'eula=false' al 'eula=true'." + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "EULA de Minecraft ne akceptita";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "Redaktu la dosieron eula.txt en la servila dosierujo kaj ŝanĝu 'eula=false' al 'eula=true'.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine provas ŝargiĝi sur dediĉita servilo, sed ĝi estas kompatibla nur kun la kliento. "
				+ "Forigu OptiFine de la servilo aŭ certigu, ke ĝi estas instalita nur sur la kliento." + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine sur dediĉita servilo";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "Forigu OptiFine de la dediĉita servilo, ĉar ĝi devas esti instalita nur sur la kliento.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks estas malĝuste markita por 1.20.1 sed uzas metodojn de 1.21.1. "
				+ "La mod provas uzi ResourceLocation.fromNamespaceAndPath, kiu ne ekzistas en 1.20.1." + "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "Versio-eraro en Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "Certigu, ke vi uzas la ĝustan version de Iron's Spellbooks kongruan kun via Minecraft-versio.";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Detektita gravaj konflikto inter OptiFine kaj Embeddium. "
				+ "Tiuj modoj ne estas kongruaj kaj kaŭzas enjekcian fiaskon, kiu malpermesas lanĉon de la ludo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "Konflikto inter OptiFine kaj Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "Forigu OptiFine aŭ Embeddium, ĉar ili ne estas kongruaj unu kun la alia.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Tio estas ofta ĉe konfliktaj mondo-generantaj modoj, precipe Terralinth, AmplifiedNether, Nullscape kaj Incendium, kaj aliaj mondo-generantaj modoj. Eble vi devas instali mankantan modon.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable provas ŝargiĝi sur dediĉita servilo, sed ĝi estas kompatibla nur kun la kliento. "
				+ "Forigu Controllable de la servilo aŭ certigu, ke ĝi estas instalita nur sur la kliento." + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable sur dediĉita servilo";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "Forigu Controllable de la dediĉita servilo, ĉar ĝi devas esti instalita nur sur la kliento.";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries kaŭzas eraron, kiu malhelpas la ŝargadon de la servilo. "
				+ "La mod havas problemojn kun la registo de fajrokondutoj, kiuj kaŭzas fiaskon dum la ŝargado de datapack-oj."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries malhelpas la ŝargadon de la servilo";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "Provu ĝisdatigi Supplementaries al la plej nova versio aŭ malaktivigu ĝin provizore por permesi la ŝargadon de la servilo.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) trovis problemon pro mankantaj Jackson-moduloj. "
				+ "Iuj modoj kiel Valkyrien Skies povas kaŭzi tiun eraron pro ne inkluzivi ĉiujn necesajn dependecojn."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "Mankanta Jackson-modulo en Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "Forigu Groovy Modloader kaj rilatajn modojn kiel Valkyrien Skies, kiuj povas kaŭzi konfliktojn de dependecoj.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Every Compat trovis nevalidan nomon de lignon blokon. "
				+ "Every Compat ĝenerale havas multajn problemojn. Ne uzu ĝin!" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "Nevalida nomo en Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "Kontrolu la risurcpakojn aŭ modojn, kiuj uzas Every Compat, ĉar ili povas enhavi nevalidajn bloknomojn.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erara kodo (-1073741819) estis trovita, kiu povas esti kaŭzita de supermetoj (overlays) kiel GameCaster de Razer, Discord, OBS Studio aŭ problemoj kun NVIDIA-aj peliloj."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "Erara kodo -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "Provu malŝalti supermetojn kiel GameCaster, Discord aŭ OBS Studio, kaj kontrolu ke viaj NVIDIA-aj peliloj estu ĝisdataj.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips는 dependencia인 Immersive Messages가 필요하지만 설치되어 있지 않습니다. "
				+ "Immersive Tooltips가 제대로 작동하려면 Immersive Messages를 설치하세요." + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "dependencia 없는 Immersive Tooltips";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips에 필요한 dependencia인 Immersive Messages를 설치하세요.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins havas kongruecproblemon kun Apoli Mod, kie ItemStack ne povas esti 'cast'-ita al EntityLinkedItemStack. "
				+ "Tio estas komuna en versioj pli novaj ol 6.6.0. Konsideru uzi pli malnovan version aŭ ŝanĝi inter versioj de Fabric kaj Forge."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "Eraro de 'cast' en Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "Uzu version de Medieval Origins 6.6.0 aŭ pli malnovan, aŭ provu ŝanĝi inter versioj de Fabric kaj Forge de la mod.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether kaŭzas eraron kun Registry Object ne ĉeestanta en MusicManager. "
				+ "Ĉi tiu problemo rilatas al la mixin de MusicManager de Reign of Nether." + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "Eraro de MusicManager en Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "Provu ĝisdatigi Reign of Nether aŭ provizore forigi ĝin por solvi la eraron.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel subtenas la servilon YSM nur sur Linux aŭ Android. "
				+ "Ĉi tiu problemo estis riparita en pli novaj versioj ekde la 23-a de novembro 2025 sur Modrinth."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel ne kongruas kun Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "Ĝisdatigu YesSteveModel al pli nova versio el Modrinth, ĉar la problemo estis riparita post la 23-a de novembro.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Detektita grava konflikto inter Moving Elevators kaj OptiFine. "
				+ "Tiuj modoj ne estas kongruaj kaj kaŭzas enjekcian fiaskon, kiu malpermesas lanĉon de la ludo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "Konflikto inter Moving Elevators kaj OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "Forigu OptiFine aŭ Moving Elevators, ĉar ili ne estas kongruaj unu kun la alia.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Detektita grava konflikto inter Fabric API (fabric-resource-loader-v0) kaj OptiFine. "
				+ "Tiuj modoj ne estas kongruaj kaj kaŭzas enjekcian fiaskon, kiu malpermesas lanĉon de la ludo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "Konflikto inter Fabric API kaj OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "Forigu OptiFine aŭ ĝisdatigu Fabric API al kongrua versio.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Modo havas difektitan ITransformationService, kiu ne povas esti instancigita: " + claseProveedor
				+ ". " + "Ĉi tiu modo devas esti forigita por permesi lanĉon de la ludo." + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "Difektita ITransformationService";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "Forigu la modon, kiu enhavas la klason " + claseProveedor
				+ ", ĉar ĝi havas difektitan ITransformationService.";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError() + "'>Modo havas nevalidan versiospecifon. "
				+ "La versio devas esti ĉirkaŭita de kvadrataj krampoj. "
				+ "Vi povas uzi la ilon grep/greprf en la flanka panelo serĉante la version </span>" + version
				+ "<span style='color:#" + config.obtenerColorError()
				+ "'> por identigi, kiu modo havas la problemon.</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "Nevalida versio en modo";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "Uzu la ilon grep/greprf en la flanka panelo por serĉi la probleman version kaj trovi la modon, kiu ĝin enhavas.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Eraro de stack smashing estis trovita, kiu finis la procezon. "
				+ "Tio povas esti kaŭzita de problemoj kun Early Window en Forge/NeoForge/PillowMC aŭ kun LWJGL 3.2.2 kaj pli novaj."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "Stack Smashing Detektita";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "Kontrolu la agordojn de Early Window kaj konsideru uzi alian version de LWJGL aŭ reviziu modojn rilatajn al frua fenestro.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore estas nur por specifa modpack kaj ne uzebla en ĝeneralaj instalaĵoj, ĉar ĝi kaŭzas problemon."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore kun nekongrua versio de Java";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "Forigu GregTechEasyCore, ĉar ĝi estas nur por specifa modpack kaj ne kongruas kun via ĝenerala instalaĵo.";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Detektita konflikto inter MoniLabs kaj Connector Extras rilate al modifoj de KubeJS. "
				+ "Tiuj modoj ne estas kongruaj en siaj KubeJS-modifoj." + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "Konflikto inter MoniLabs kaj Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "Provu malinstali unu el la modoj (MoniLabs aŭ Connector Extras), ĉar ili konfliktas en siaj KubeJS-modifoj.";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris postulas Distant Horizons [2.0.4] aŭ DH API versio [1.1.0] aŭ pli nova. "
				+ "Vidu la kongruec-gvidilon ĉe https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e por solvi la problemon."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Kongrueco de Iris kaj Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "Vidu la kongruec-gvidilon ĉe https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e kaj ĝisdatigu Iris kaj Distant Horizons al kongruaj versioj.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Vi mankas klasojn de Minecraft. Eblaj kialoj:</b>"
				+ "<ul>"
				+ "<li>Vi havas modojn por aliaj versioj de la ludo. Vi povas uzi <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> por kontroli ĉu la klaso ekzistas en via versio.</li>"
				+ "<li>Vi havas difektitan instalaĵon de Minecraft (komune kun CurseForge App, ModrinthApp/Theseus/Astralrinth kaj aliaj modpack-lanĉiloj). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Vidu la lernilon</a> por solvi problemojn kun CurseForge.</li>"
				+ "<li>Vi havas difektitan coremod-on (se coremod malsukcesas, ĝi povas bloki la ŝarĝon de la klaso).</li>"
				+ "</ul>"
				+ "<p>Noto: Vi povas uzi la ilon <b>grepr/fgrepr</b> en la flankpanelo por trovi la modojn, kiuj referencas la mankantajn klasojn, ĉiam kiam vi uzas '/' en la nomoj.</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Vi mankas klasojn de DangerZone. Eblaj kialoj:</b>"
				+ "<ul>" + "<li>Vi havas modojn por aliaj versioj de la ludo.</li>"
				+ "<li>Vi havas difektitajn coremod-ojn.</li>" + "<li>Vi havas difektitan lanĉilon aŭ instalaĵon.</li>"
				+ "</ul>"
				+ "<p>Noto: Vi povas uzi la ilon <b>grepr/fgrepr</b> en la flankpanelo por trovi la modojn, kiuj referencas la mankantajn klasojn, ĉiam kiam vi uzas '/' en la nomoj.</p>";
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
				+ "'>Vi mankas klasojn de Minecraft Forge. Eblaj kialoj:</b>" + "<ul>"
				+ "<li>Viaj modoj estas por alia build de MinecraftForge.</li>"
				+ "<li>Ekzistas multaj ĝisdatigoj de modloaders por unu versio de Minecraft.</li>"
				+ "<li>Vi havas difektitan instalaĵon (komune kun CurseForge App, ModrinthApp/Theseus/Astralrinth kaj aliaj modpack-lanĉiloj). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Vidu la lernilon</a> por solvi problemojn kun CurseForge.</li>"
				+ "</ul>"
				+ "<p>Noto: Vi povas uzi la ilon <b>grepr/fgrepr</b> en la flankpanelo por trovi la modojn, kiuj referencas la mankantajn klasojn, ĉiam kiam vi uzas '/' en la nomoj.</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Vi mankas klasojn de NeoForge. Eblaj kialoj:</b>"
				+ "<ul>" + "<li>Viaj modoj estas por alia build de NeoForge.</li>"
				+ "<li>Ekzistas multaj ĝisdatigoj de modloaders por unu versio de Minecraft.</li>"
				+ "<li>Vi havas difektitan instalaĵon (komune kun CurseForge App, ModrinthApp/Theseus/Astralrinth kaj aliaj modpack-lanĉiloj). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Vidu la lernilon</a> por solvi problemojn kun CurseForge.</li>"
				+ "</ul>"
				+ "<p>Noto: Vi povas uzi la ilon <b>grepr/fgrepr</b> en la flankpanelo por trovi la modojn, kiuj referencas la mankantajn klasojn, ĉiam kiam vi uzas '/' en la nomoj.</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vi mankas klasojn de Fabric Loader. Eblaj kialoj:</b>" + "<ul>"
				+ "<li>Viaj modoj estas por alia build de Fabric Loader.</li>"
				+ "<li>Ekzistas multaj ĝisdatigoj de modloaders por unu versio de Minecraft.</li>"
				+ "<li>Vi havas difektitan instalaĵon (komune kun CurseForge App, ModrinthApp/Theseus/Astralrinth kaj aliaj modpack-lanĉiloj). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Vidu la lernilon</a> por solvi problemojn kun CurseForge.</li>"
				+ "<li>Multaj modoj postulas Fabric API. Bonvolu instali Fabric API se necesas.</li>" + "</ul>"
				+ "<p>Noto: Vi povas uzi la ilon <b>grepr/fgrepr</b> en la flankpanelo por trovi la modojn, kiuj referencas la mankantajn klasojn, ĉiam kiam vi uzas '/' en la nomoj.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Vi mankas klasojn de PillowMC. Eblaj kialoj:</b>"
				+ "<ul>" + "<li>Viaj modoj estas por alia build de PillowMC.</li>"
				+ "<li>Ekzistas multaj ĝisdatigoj de modloaders por unu versio de Minecraft.</li>"
				+ "<li>Vi havas difektitan instalaĵon (komune kun CurseForge App, ModrinthApp/Theseus/Astralrinth kaj aliaj modpack-lanĉiloj). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Vidu la lernilon</a> por solvi problemojn kun CurseForge.</li>"
				+ "</ul>"
				+ "<p>Noto: Vi povas uzi la ilon <b>grepr/fgrepr</b> en la flankpanelo por trovi la modojn, kiuj referencas la mankantajn klasojn, ĉiam kiam vi uzas '/' en la nomoj.</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Vi havas modon, kiu intence kaŭzas malrapidon (lag). Uranium estas mod de malrapidigo. Ĝi ne ĉiam kaŭzas kraŝojn, sed iam povas fari tion."
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack estas markita kiel kongrua kun 1.19.*, sed estas por 1.20.*, kio kaŭzas eraron \"klaso ne trovita\". "
				+ "La mod provas uzi DamageSources, kiuj ne ekzistas en la nuna versio de Minecraft." + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Versieraro en Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "Certigu, ke vi uzas la ĝustan version de Falling Attack, kongruan kun via versio de Minecraft.";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>").append("Vi devas instali CFR (Class File Reader) por uzi tiun ĉi funkcion.<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append("En sistemoj Linux, NetBSD aŭ FreeBSD, vi povas instali CFR per via pakaĵadministrilo.<br>")
					.append("Serĉu la pakaĵon ĉe: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append("Alternative, vi povas elŝuti la modifitan version uzatan de FabricMC de:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("Konservu ĝin en la sekva dosierujo:<br>").append("<b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>Grava:</b> post instali CFR, vi devas restartigi la modon por ke ĝi ĝuste rekonu ĝin.")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "Neniu portreto disponebla";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "Ne eblis trovi la klason: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "Malfasilaĵilo CFR – Sakura Riddle (Neoficiala)";
	}

	@Override
	public String cfrClaseActual() {
		return "Nuna klaso";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "Portreto de Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "Eraro dum ŝargado de la portreto";
	}

	public String noticiaLegalCFR() {
		return "Ĉi tiu grafika uzantinterfaco (GUI) por malfasili modojn estas dizajnita por helpi uzantojn identigi la kialojn de programeraroj. "
				+ "Tamen, malfasilo de modoj povas esti necesa, kaj uzantoj devas zorgi ne uzi la generitan kodon por transpasi la Federan Leĝon pri Aŭtorrajtoj. "
				+ "Oni rekomendas kontroli la permesilon de la rilata mod antaŭ ol uzi ajnan akiritan kodon. Plie, ofte modoj ofertas sian fontkodon oficiale, "
				+ "kiu kutime estas pli klara kaj pli facile komprenebla ol malfasilita kodo. Memorindas ke respekto al intelekta propraĵo kaj uzpermesiloj estas fundamenta por "
				+ "la mod-elsvolva komunumo. Vi povas konsulti la Federan Leĝon pri Aŭtorrajtoj ĉe: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Ley Federal de Derechos de Autor (Hispane)</a> "
				+ "kaj la anglan version ĉi tie: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (Angle)</a>. "
				+ "Ĉar ni estas en CurseForge, ni ankaŭ donas ligilon al la Usona Leĝo pri Aŭtorrajto: "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>. "
				+ "Krome, rekomendiĝas ke uzantoj esploru la leĝojn aplikeblajn en sia propra loko. "
				+ "Nia GUI estas nur por simplaj kontroloj; por pli progresinta analizo, oni devus uzi la Forkon Enigma de FabricMC ĉe "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>. Se oni deziras redakti JAR-dosierojn por flikado sen disponebla fontkodo, oni povas uzi Recaf ĉe "
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">ĝia retejo</a>.";
	}

	@Override
	public String botonDescargarCfr() {
		return "Elŝuti CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "Malfermi instalan dosierujon";
	}

	@Override
	public String colorFondoPrincipal() {
		return "Ĉefa fona koloro";
	}

	@Override
	public String colorTextoBotonReset() {
		return "Koloro de la teksto de la restar-butono";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "Koloro de la serĉkampa teksto";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "Koloro de la teksto de la falmenuo de filtrado";
	}

	@Override
	public String colorTextoRenderer() {
		return "Koloro de la teksto de la bildigilo (renderilo)";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "Koloro de la teksto de la ŝarĝa supermeto (overlay)";
	}

	@Override
	public String colorBorde() {
		return "Koloro de la bordero";
	}

	@Override
	public String colorFondoRetrato() {
		return "Fona koloro en portreta reĝimo";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "Koloro de la ligilo por kunhavigi";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "Fona koloro de la kunhaviga kampo";
	}

	@Override
	public String rosaFondo() {
		return "Rozkolora fono";
	}

	@Override
	public String rosaSuave() {
		return "Milda rozo";
	}

	@Override
	public String moradoAcento() {
		return "Akkentpurpuro";
	}

	@Override
	public String textoOscuro() {
		return "Malhela teksto";
	}

	@Override
	public String bordeSuave() {
		return "Milda bordero";
	}

	@Override
	public String fondoCampo() {
		return "Kampa fono";
	}

	@Override
	public String fondoVistaPrevia() {
		return "Antaŭrigarda fono";
	}

	@Override
	public String sintaxisConstructor() {
		return "Sintaksa koloro: konstruilo";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "Sintaksa koloro: helpmesaĝo";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "Sintaksa koloro: HTML-etikedoj";
	}

	@Override
	public String colorFondoVentana() {
		return "Fena fona koloro";
	}

	@Override
	public String colorPanel() {
		return "Panela koloro";
	}

	@Override
	public String colorBotonTexto() {
		return "Butona teksta koloro";
	}

	@Override
	public String colorCampo() {
		return "Kampa koloro";
	}

	@Override
	public String colorBordeDestacado() {
		return "Elstara bordera koloro";
	}

	@Override
	public String colorSeleccionTexto() {
		return "Teksta elekta fona koloro";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "Koloro de la elektita teksto";
	}

	@Override
	public String colorEstadoExito() {
		return "Koloro de stato: sukceso";
	}

	@Override
	public String colorEstadoFallo() {
		return "Koloro de stato: malsukceso";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "Koloro de stato: tujtempa";
	}

	@Override
	public String colorResultadoAnadido() {
		return "Koloro de aldonita rezulto";
	}

	@Override
	public String colorResultadoEliminado() {
		return "Koloro de forigita rezulto";
	}

	@Override
	public String colorBordeScroll() {
		return "Koloro de la rul-streka bordero";
	}

	@Override
	public String colorFondoPanel() {
		return "Fona koloro de la panelo";
	}

	@Override
	public String colorBeigeListas() {
		return "Beĝa koloro de listoj";
	}

	@Override
	public String colorTextoListas() {
		return "Teksta koloro en listoj";
	}

	@Override
	public String colorBordeListas() {
		return "Bordera koloro de listoj";
	}

	@Override
	public String colorBotonFondo() {
		return "Fona koloro de la butono";
	}

	@Override
	public String colorBordeBoton() {
		return "Bordera koloro de la butono";
	}

	@Override
	public String colorDoradoTexto() {
		return "Orkolora teksto";
	}

	@Override
	public String colorPila() {
		return "Koloro de la stakspuro (stack trace)";
	}

	@Override
	public String colorTextoPanel() {
		return "Panea teksta koloro";
	}

	@Override
	public String colorTextoNegro() {
		return "Nigra teksta koloro";
	}

	@Override
	public String colorTextoPrincipal() {
		return "Ĉefa teksta koloro";
	}

	@Override
	public String colorFondoResultados() {
		return "Fona koloro de la rezultoj";
	}

	@Override
	public String colorEstado() {
		return "Koloro de stato";
	}

	@Override
	public String colorTextoDescripcion() {
		return "Priskriba teksta koloro";
	}

	@Override
	public String colorTextoEstado() {
		return "Stata teksta koloro";
	}

	@Override
	public String colorTextoExtra() {
		return "Kroma teksta koloro";
	}

	@Override
	public String colorSeparador() {
		return "Koloro de la dividilo";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "Detektita indiĝena eraro <code>StubRoutines::SafeFetch32</code>. "
				+ "Ĉi tiu problemo okazas en macOS kun JDK 17.0.9 kaj estas riparita en JDK 17.0.10 aŭ pli nova. https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "Indiĝena eraro SafeFetch32 en JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "Ĝisdatigu vian JDK al versio 17.0.10 aŭ pli nova (ekz. 17.0.15).";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "Se vi uzas lanĉilon kiel MultiMC, Prism Launcher aŭ TLauncher, agordu ĝin por uzi pli novan JDK. "
				+ "Iuj jam inkluzivas JDK 17.0.15 integrite.";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "La mod Spark ankaŭ povas kontribui al ĉi tiu eraro. Konsideru ĝin provizore malŝalti. https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "La mod MCEF (Chromium Embedded Framework) kaŭzas silentan ĉeson.</b>" + "<ul>"
				+ "<li>MCEF estas iniciatigata ĉe la fino de la protokolo, kio normale signifas ke la ludo haltis dum ŝargado.</li>"
				+ "<li>Ĉi tiu mod estas konata pro kaŭzi kraŝojn en Linux, macOS aŭ kun certaj versioj de Java.</li>"
				+ "<li>Ne ĉiam aperas klara eraro, sed la ludo neniam atingas la ĉefmenuon.</li>" + "</ul>"
				+ "<p>Se vi ne bezonas retumilan funkcion en la ludo (kiel retaj mapoj aŭ enigitaj paĝoj), forigu la modon.</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "Problemo de iniciatigo de MCEF (mod de enigita retumilo)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "Forigu la dosieron de la mod MCEF (serĉu 'mcef' en la dosiernomo) el la dosierujo 'mods'.";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "Se vi vere bezonas ĝin, certigu, ke vi uzas version kongruan kun via operaciumo kaj Minecraft-versio.";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Konflikto estis trovita inter <b>OptiFine</b> kaj <b>Iris/Oculus</b>.</b>" + "<ul>"
				+ "<li>OptiFine modifas la bildigon de Minecraft nekongrue kun Iris aŭ Oculus.</li>"
				+ "<li>La eraro <code>MixinLevelRenderer failed injection check</code> devenas el <code>mixins.iris.json</code> aŭ <code>mixins.oculus.json</code>.</li>"
				+ "</ul>" + "<p>Ĉi tiuj modoj ne uzeblas kune. Forigu OptiFine por uzi shaders kun Iris aŭ Oculus.</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "Konflikto inter OptiFine kaj Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "Forigu la dosieron de OptiFine el la dosierujo 'mods'.";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "Uzu Iris aŭ Oculus sen OptiFine por modernaj shaders.";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Konflikto estis trovita inter <b>ModernFix</b> kaj <b>OptiFine</b>.</b>" + "<ul>"
				+ "<li>ModernFix ne estas kongrua kun OptiFine ĉar ĝi rompas funkciojn de Forge kaj malrapidigas lanĉon.</li>"
				+ "<li>ModernFix mem avertas: <i>\"Use of ModernFix with OptiFine is not supported\"</i>.</li>"
				+ "</ul>" + "<p>Vi devas forigi unu el la du modoj por ke la ludo funkciu ĝuste.</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "Konflikto inter ModernFix kaj OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "Forigu OptiFine aŭ ModernFix el la dosierujo 'mods'. Ili ne uzeblas kune.";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "Se vi bezonas optimumigojn, konsideru uzi nur OptiFine, aŭ anstataŭigi ModernFix per pli malpezaĵaj modoj kiel FerriteCore aŭ EntityCulling.";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Eraro: nevalida registra ŝlosilo kun nepermesitaj signoj.</b>" + "<ul>"
				+ "<li><b>Detektita ŝlosilo:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>En Minecraft, ĉiuj registraj ŝlosiloj (etikedoj, receptoj, atingoj, ktp.) devas esti <b>minusklaj</b> kaj enhavi nur literojn, ciferojn, substrekojn, strekojn kaj oblikvajn strekojn.</li>"
				+ "<li>Ĉi tiu eraro kutime estas kaŭzita de malbone programita mod aŭ difektita datapack.</li>"
				+ "</ul>"
				+ "<p><b>Grava konsilo:</b> Uzu la ilon <b>grepr</b> aŭ <b>fgrepr</b> en la flankpanelo kaj aktivigu la opcion <b>\"Serĉi en JAR-dosieroj\"</b> por trovi, kiu mod enhavas ĉi tiun neĝustan ŝlosilon.</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "Registra ŝlosilo kun majuskloj aŭ nevalidaj signoj";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "Uzu 'grepr' aŭ 'fgrepr' kun \"Serĉi en JAR-dosieroj\" por lokalizi la kulpan modon.";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "Se vi ne povas identigi la modon, forigu ĵusajn modojn, precipe tiujn, kiuj aldonas blokojn, objektojn aŭ ilojn.";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Eraro dum ŝargado de la mod <b>"
				+ escapeHtml(modNombre) + "</b>.</b>" + "<ul>"
				+ "<li>La mod malsukcesis je la iniciatigo de unu el ĝiaj komponantoj (ekz. agordmenuo).</li>"
				+ "<li>Tio kutime okazas pro nekongrueco kun la versio de Minecraft, Fabric aŭ aliaj modoj.</li>"
				+ "</ul>" + "<p>Se la eraro persistas, forigu aŭ ĝisdatigu la mod <b>" + escapeHtml(modNombre)
				+ "</b>.</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "Eraro de iniciatigo de mod (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "Forigu la modon '" + modNombre + "' el la dosierujo 'mods'.";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "Ĝisdatigu la modon '" + modNombre + "' al versio kongrua kun via instalaĵo.";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Eraro rilata al la mod <b>En Garde!</b> estis trovita.</b>" + "<ul>"
				+ "<li>Ĉi tiu mod aldonas proksimumkomatajn mekanikojn (parado, blokado, ktp.).</li>"
				+ "<li>La eraro kutime okazas pro nekongrueco kun aliaj kombataj modoj (kiel Epic Fight, DualRiders, ktp.) aŭ pro uzo de malĝusta versio por via Minecraft.</li>"
				+ "</ul>"
				+ "<p>Se vi ne uzas altnivelan kombaton, konsideru forigi En Garde! por eviti konfliktojn.</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "Eraro en la mod En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "Certigu, ke vi uzas la version de En Garde! kongruan kun via Minecraft-versio kaj ŝargilo (Fabric/Forge).";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "Se vi uzas aliajn kombatajn modojn (Epic Fight, Caelus, ktp.), malaktivigu ilin aŭ forigu En Garde! por eviti konfliktojn.";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Eraro kaŭzita de la mod <b>IdleTweaks</b> estis trovita.</b>" + "<ul>"
				+ "<li>IdleTweaks provis liberigi neekzistantan retkanalon (<code>Tried to release unknown channel</code>).</li>"
				+ "<li>Ĉi tiu eraro kutime okazas en malnovaj versioj de la mod aŭ kiam ĝi estas uzata sur misagorditaj serviloj.</li>"
				+ "</ul>"
				+ "<p>IdleTweaks estas mod por plibonigi vivkvaliton, sed ĝi povas kaŭzi malstabilecon. Konsideru ĝisdatigi aŭ forigi ĝin.</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "Eraro en IdleTweaks (nekonata retkanalo)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "Ĝisdatigu IdleTweaks al la plej nova versio kongrua kun via Minecraft.";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "Forigu IdleTweaks el la dosierujo 'mods' se vi ne bezonas ĝin.";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Aŭtentiga eraro (HTTP 401) estis trovita dum provo ensaluti en Minecraft.</b>"
				+ "<p>Ĉi tiu eraro <b>malofte estas la rekta kialo de kraŝo</b>, sed ĝi indikas, ke vi uzas nevalidigitan konton (pirata kopio).</p>"
				+ "<p>Oficialaj subtenkanaloj (korporaciaj projektoj, VTubers, kreadintoj de modpakaĵoj, ktp.) <b>ne povas helpi vin</b> se vi uzas piratan kopion, "
				+ "pro limigoj en iliaj babilejaj reguloj, kontraktoj, interkonsentoj kun Mojang/Microsoft, aŭ reputaciaj politikoj.</p>"
				+ "<p>Ĉi tiu kontrolo povas esti <b>malŝaltita en la korporaciaj agordoj</b> de la detektilo. "
				+ "Averto: la kontraŭpirata detekto <b>ne estas perfekta</b> kaj povas aktiviĝi en programaj medioj, kun malstabila interreto, aŭ kun modifitaj lanĉiloj.</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>Miranda-j rajtoj se vi tamen provas aliĝi al la subteno:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Pirata Minecraft";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "Malŝalti kontraŭpiratan kontrolon";
	}

	@Override
	public String comprarMC() {
		return "Aĉeti Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Vi uzas la lanĉilon <code>" + id
				+ "</code>, kiu <b>ne estas en la listo de rekomenditaj lanĉiloj</b>.</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>Ĉiuj povas funkcii, sed nerekomenditaj lanĉiloj kutime kaŭzas:</p>" + "<ul>"
				+ "<li>Difektitan instalaĵon de modoj aŭ la Aplikaĵo.</li>"
				+ "<li>Ludo ne lanĉiĝas aŭ haltas sen klara eraro.</li>"
				+ "<li>Nenormalan dosierujan strukturon (malfacilas diagnozon).</li>"
				+ "<li>Neprevideblan konduton kun Java, memoro aŭ modoj.</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "Por pli bona sperto, uzu unu el la sekvaj rekomenditaj lanĉiloj:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Nerekomendita lanĉilo";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "Ŝanĝu al lanĉilo el la rekomendita listo.";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Vi uzas <b>malrekomenditan lanĉilon</b>: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>Malrekomenditaj lanĉiloj povas kaŭzi:</p>" + "<ul>"
				+ "<li>Difektitan instalaĵon de la Aplikaĵo aŭ modoj.</li>"
				+ "<li>Ludo ne lanĉiĝas aŭ fiaskas sen bruo.</li>"
				+ "<li>Nenormalan aranĝon de dosieroj (malfacilas depuradon).</li>"
				+ "<li>Necerton pri kiel ĝi administras modojn, Java-n aŭ memoron.</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "Tre rekomendiĝas uzi unu el la jenaj lanĉiloj:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Malrekomendita lanĉilo";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "Ŝanĝu al rekomendita lanĉilo por ricevi subtenon.";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mankas rekomenditaj modoj por ĉi tiu medio.</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "Mankantaj rekomenditaj modoj";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "Instalu la rekomenditajn modojn por optimuma sperto.";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Detektita nerekomenditaj modoj en via instalaĵo.</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "Detektitaj nerekomenditaj modoj";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "Forigu la nerekomenditajn modojn por eviti problemojn.";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Maldolĉa manipulado estis trovita en kritikaj dosieroj. Vi aŭ modifis la dosierojn aŭ uzas malfidindan lanĉilon.</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "Manipulado detektita";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "Reinstalu la originalajn dosierojn por restaŭri la integrecon.";
	}

	@Override
	public String configuracionCorporativa() {
		return "Korporaciaj Agordoj";
	}

	@Override
	public String idiomaRespaldo() {
		return "Rezerva Lingvo";
	}

	@Override
	public String buscardorHabilitado() {
		return "Ŝalti Serĉilon";
	}

	@Override
	public String nombreHerramienta() {
		return "Nomo de la Ilo";
	}

	@Override
	public String condenarPirateria() {
		return "Kondamni Piratadon";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "Rekomenditaj Lanĉiloj";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "Nerekomenditaj Lanĉiloj";
	}

	@Override
	public String modsRecomendados() {
		return "Rekomenditaj Modoj";
	}

	@Override
	public String modsDesaconsejados() {
		return "Nerekomenditaj Modoj";
	}

	@Override
	public String antiTamper() {
		return "KontraŭManiplado";
	}

	@Override
	public String proximamente() {
		return "Baldaŭ";
	}

	@Override
	public String informacion() {
		return "Informo";
	}

	@Override
	public String errorCargandoImagen() {
		return "Eraro dum ŝargado de bildo";
	}

	@Override
	public String configuracionBasica() {
		return "Bazaj Agordoj";
	}

	@Override
	public String funcionalidades() {
		return "Funkcioj";
	}

	@Override
	public String derechosMiranda() {
		return "Miranda-j Rajtoj (TRE rekomenditaj)";
	}

	@Override
	public String gestionVerificaciones() {
		return "Administri Kontrolojn";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "Nomo";
	}

	@Override
	public String codigoVerificacion() {
		return "Kodo";
	}

	@Override
	public String documentacionVerificacion() {
		return "Dokumentaro";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "Ebligitaj Kontroloj:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "Malebligitaj Kontroloj:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "Malebligi ĉiujn ne-korporaciajn";
	}

	@Override
	public String verCodigo() {
		return "Vidi Kodon";
	}

	@Override
	public String verDocumentacion() {
		return "Vidi Dokumentaron";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "Elektu kontrolo por malebligi.";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "Elektu kontrolo por ebligi.";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "Malebligitaj %d kontroloj ne rekomenditaj por korporacia uzo.";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "Neniu ne-korporacia kontrolo por malebligi.";
	}

	@Override
	public String operacionCompletada() {
		return "Operacio finita";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "Ni sopiras vin, Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "Koloro de Korporacia Kontrolo";
	}

	@Override
	public String nombreLanzador() {
		return "Nomo de la Lanĉilo";
	}

	@Override
	public String motivo() {
		return "Kialo";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Nerekomenditaj Lanĉiloj";
	}

	@Override
	public String moverADesaconsejados() {
		return "Malebligi Rekomendon";
	}

	@Override
	public String moverARecomendados() {
		return "Ebligi Rekomendon";
	}

	@Override
	public String guardarCambios() {
		return "Konservi Ŝanĝojn";
	}

	@Override
	public String cancelar() {
		return "Nuligi";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "Bonvolu elekti lanĉilon por movi.";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "Ŝanĝoj estis sukcese konservitaj!";
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
		return "Kialoj";
	}

	@Override
	public String agregarLanzador() {
		return "Aldoni lanĉilon";
	}

	@Override
	public String quitarLanzador() {
		return "Forigi lanĉilon";
	}

	@Override
	public String editarRazones() {
		return "Redakti kialojn";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "Elektu lanĉilon por forigi.";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "Elektu lanĉilon por redakti.";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "Redakti kialojn por " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "Aldoni novan lingvon";
	}

	@Override
	public String aceptar() {
		return "Akcepti";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "Elektu la lingvon";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "Ĉi tiuj lanĉiloj estas tiuj, kiujn CrashDetector sugestas kiel bonaj.";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "Ĝusta rezulto";
	}

	public String modsNoRecomendados() {
		return "Nerekomenditaj modoj";
	}

	public String agregarMod() {
		return "Aldoni modon";
	}

	public String quitarMod() {
		return "Forigi modon";
	}

	public String modId() {
		return "Mod-ID / Nomo de JBoss Modules";
	}

	public String rutaMod() {
		return "Vojo / dosiero de la modo";
	}

	public String errorDebeIndicarMod() {
		return "Vi devas indiki almenaŭ la modid aŭ la vojon de la modo.";
	}

	public String modsNoRecomendadosAviso() {
		return "Ĉi tie vi povas registri nerekomenditajn modojn por ke CrashDetector detektu ilin, se ili estas instalitaj.";
	}

	@Override
	public String anularNormal() {
		return "Malebligi Normalan";
	}

	@Override
	public String anularNormalDescripcion() {
		return "CrashDetector devus averti eĉ se ne okazas kraŝo.";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "Registru la modojn, kiujn CrashDetector rekomendas. Se ili mankas, CrashDetector povas averti.";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "Se vi decidis ebligi la kontraŭpirata averto, rekomendiĝas difini ĉi tie "
				+ "la rajtojn de la persono petanta subtenon, kiel preventa mezuro.\n\n"

				+ "Kontraŭe al komuna kredo, multaj popularaj komunumoj kaj subtenkanaloj "
				+ "NE postulas ebligi kontraŭpiratajn avertojn por doni helpon. Tamen, "
				+ "dokumenti tiujn rajtojn povas esti utila se iu tamen aliĝas al la subtenkanalo.\n\n"

				+ "Vi povas bazi vin sur oficialaj dokumentoj kiel la Folieto de Bazaj Rajtoj de Arestito "
				+ "en Meksiko:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "Same kiel sur kompareblaj juraj principoj uzataj en aliaj landoj, inkluzive "
				+ "Usono, Rusia Federacio, Popola Respubliko Ĉinio, Islama Respubliko "
				+ "Iran kaj Demokratia Popola Respubliko Koreio.\n\n"

				+ "Kelkaj ekzemploj de rajtoj kiujn oni povas inkluzivi estas:\n"
				+ "• La rajto ne liveri nenecesajn informojn por subteno, kiel la uzata lanĉilo, "
				+ "uzantnomo aŭ UUID.\n" + "• La rajto ne memakuzi sin.\n"
				+ "• La rajto rifuzi respondi demandojn ne necesajn por solvi la problemon.\n"
				+ "• La rajto ricevi gvidadon en la babilejo.\n"
				+ "• La rajto uzi la integritan anonimigilon de protokoloj (logs) en CrashDetector.\n\n"

				+ "Ĉi tiu teksto akceptas HTML-enhavon.";
	}

	@Override
	public String editar() {
		return "Redakti";
	}

	@Override
	public String advertenciaHashLento() {
		return "Averto: aldoni multajn grandajn dosierojn povas igi la kontrolon daŭri plurajn minutojn. CrashDetector devos kalkuli la haketon de ĉiu dosiero antaŭ ol daŭrigi. Rekomendiĝas protekti nur strikte necesajn dosierojn.";
	}

	@Override
	public String agregarArchivo() {
		return "Aldoni dosieron";
	}

	@Override
	public String agregarCarpeta() {
		return "Aldoni dosierujon";
	}

	@Override
	public String quitar() {
		return "Forigi";
	}

	@Override
	public String rutaArchivo() {
		return "Vojo de la dosiero";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "La elektita vojo estas ekster la nuna lud-dosierujo. Nur dosieroj kaj dosierujoj ene de la nuna dosierujo aŭ ĝiaj subdosierujoj estas permesitaj.";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>"
				+ "La opinioj kaj komentoj de Sylent Bell ne nepre kongruas kun niaj; "
				+ "ni simple pensis, ke estus amuze meti ŝin ĉi tie. CrashDetector estas laika." + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "La mod GML (Groovy ModLoader) postulas tiujn ŝanĝojn kaj estas la plej komuna kaŭzo de ĉi tiu problemo.</b>";
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
				+ "Oni detektis la uzon de <i>Independent Flywheel</i>.</b>"
				+ "<p><b>Independent Flywheel estas eksmoda (malrekomendata)</b> kaj ne uzebla en modernaj versioj.</p>"
				+ "<p>Aktualaj versioj de <b>Create</b> <b>jam inkluzivas Flywheel</b>, do aparta instalo "
				+ "kaŭzas nekongruecojn kaj ŝargajn erarojn.</p>"
				+ "<p>Iuj modoj kiuj eksplicite dependas de Independent Flywheel povas "
				+ "<b>ne funkcii</b> aŭ <b>funkcii malstabile</b>. "
				+ "En iuj progresintaj kazoj, ili eble funkcios se oni "
				+ "<b>mane redaktas la dosieron <code>mods.toml</code></b> por ĝustigi versiajn intervalojn, "
				+ "kvankam tio <b>ne rekomendiĝas</b>.</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>Detektitaj modoj referencantaj Flywheel:</b></p>" + "<ul>" + listaMods.toString()
								+ "</ul>")
				+ "<p>La rekomendita solvo estas <b>forigi Independent Flywheel</b> kaj uzi nur "
				+ "la version inkluzivitan en Create.</p>";
		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Sendependa Flywheel";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Eraro rilata al la mod <i>Floral Enchantments</i> estis trovita.</b>"
				+ "<p>La kraŝo estas kaŭzita de interna eraro de la mod dum traktado de ludadaj datumoj, "
				+ "kio kaŭzas <b>NullPointerException</b> dum ekzekuto.</p>"
				+ "<p>Ĉi tiu problemo kutime solviĝas per ĝisdatigo aŭ forigo de la mod.</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "Eraro de Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Vi havas kaj la NeoForge-version kaj la norman version de MixinExtras. Se vi uzas MinecraftForge, vi povas instali <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>ĉi tiun ligilon</a> por solvi la problemon.</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Eraro estis trovita en terensombroj kun shaders (Iris).</b>"
				+ "<p>La problemo okazas dum bildigo de la tereno.</p>"
				+ "<p>Oni rekomendas <b>provu ludi sen shaders</b> aŭ malaltigi grafikan kvaliton, "
				+ "precipe ĉe agordoj <b>Ultra</b>.</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "Terensombroj (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Tro longa servila tiko estis detektita.</b>"
				+ "<p>Tio indikas ke la ludo haltis tro longe dum unu tiko.</p>"
				+ "<p>Oni rekomendas <b>kontroli la thread dump</b> generitan en la protokolo por identigi la kialon.</p>"
				+ "<p>La <b>Analizo de Stack Trace</b> povas helpi vin trovi la fonton de la blokado.</p>"
				+ "<p>Krome tio, la butono <b>Vidi en protokolo</b> markos per ruĝa koloro eblajn kulpajn modojn, "
				+ "same kiel enirojn ĉirkaŭitajn de <code>$modid$</code>, kiuj kutime indikas la probleman fonton. Por realtempa skanado, ni rekomendas uzi la CPU-provanton en VisualVM. Certigu, ke via servilo aŭ komputilo estas sufiĉe pova por pritrakti viajn modojn — eĉ se ĉiuj funkcias ĝuste, tro multaj povas kaŭzi tiun problemon.</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Longa servila tiko";
	}

	@Override
	public String tituloLFPDPPP() {
		return "FEDERA LEĜO PRI PROTEKTO DE PERSONAJ DATUMOJ EN POSIDO DE PRIVATULOJ";
	}

	@Override
	public String aceptarPermanentemente() {
		return "Akcepti permanente";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "La korea traduko enhavas sudajn ĝargonvortojn, kiujn oni devas eviti por obei la leĝon. "
				+ "La uzo de fremdlingvaĵoj, precipe tiuj devenantaj el la Sudo, estas strikte malpermesita "
				+ "laŭ la Leĝo pri Protekto de la Kultura Lingvo de Pjongjango.";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "Por pli da informo, vidu la oficialan dokumenton de la leĝo: "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>Leĝo pri Protekto de la Kultura Lingvo de Pjongjango</a>";
	}

	public String leerLeyCompleta() {
		return "Legi la tutan leĝon";
	}

	public String errorAbriendoEnlace() {
		return "Eraro dum malfermo de la ligilo";
	}

	public String actaProteccionIdiomaCultural() {
		return "Leĝo pri Protekto de la Kultura Lingvo de Pjongjango";
	}

	@Override
	public String canarioTitulo() {
		return "Kanario de Juĝa Ordo";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — Videbla Monitoro";
	}

	@Override
	public String revisar() {
		return "Revizii";
	}

	@Override
	public String cerrar() {
		return "Fermi";
	}

	@Override
	public String canarioTodoSeguro() {
		return "Ĉiuj servoj raportas sekuran staton.";
	}

	@Override
	public String canarioComprometido(int c) {
		return "AVERTO: " + c + " servo(j) raportas nesekuran staton.";
	}

	@Override
	public String colorAlerta() {
		return "Averto-koloro";
	}

	public String opcionesMunidiales() {
		return "Munidialaj Opcioj";
	}

	public String consentimientoLFPDPPP() {
		return "Konsento LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "Ebligi transdonon de alirkodo en Handoff por ReLauncher (malebligita).";
	}

	public String consolaDesarrollo() {
		return "Programista Konzolo";
	}

	public String mundial() {
		return "Tutmonda";
	}

	public String ningun() {
		return "Neniu";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "Programista Konzolo";
	}

	public String bajar() {
		return "Elŝuti";
	}

	public String logsSoporte() {
		return "Protokoloj por subteno";
	}

	public String detenerProceso() {
		return "Haltigi procezon";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "Kopii elektaĵon";
	}

	public String seleccionarTodo() {
		return "Elekti ĉion";
	}

	public String copiarTodo() {
		return "Kopii ĉion";
	}

	public String guardarTodoComoArchivo() {
		return "Konservi ĉion kiel dosieron";
	}

	public String obtenerEnlaceSoporte() {
		return "Akiri ligilon por subteno";
	}

	public String borrarTodo() {
		return "Forigi ĉion";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "Fona koloro de la konzolo";
	}

	public String colorTextoConsola() {
		return "Teksta koloro de la konzolo";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "Konsento konfirmiĝis.\nLa integrigo por kunhavigi protokolojn estos efektivigita ĉi tie.";
	}

	@Override
	public String usarSakuraOriginal() {
		return "Uzi la originan bildon de Sakura Riddle";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "\"Warrant canary\" estas travidebleca mekanismo.\n\n"
				+ "Dum ĉi tiu mesaĝo ekzistas kaj la servoj aperas kiel sekuraj, "
				+ "tio signifas ke la projekto NE ricevis sekretajn juĝajn ordonojn, "
				+ "cenzurpostulojn, nek leĝajn petojn pri surveilado.\n\n"
				+ "Se iu kanario malaperas aŭ markiĝas kiel nesekura, " + "tio indikas ke io ŝanĝiĝis laŭleĝe.\n\n"
				+ "Ĉi tiu panelo kontrolas ĉiujn registritajn kanariojn en la sistemo kaj montras "
				+ "iliajn aktualajn statojn.\n\n" + "Premu \"Revizii\" por ĝisdatigi la statojn.";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "Ĉu restarigi ĉiujn opciojn al iliaj defaŭltaj valoroj?";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "Sen opcioj";
	}

	@Override
	public String seleccionaColor() {
		return "Elekti koloron";
	}

	@Override
	public String botonMostrarGUI() {
		return "Montri GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "Konservi ĉion";
	}

	@Override
	public String botonRestablecerTodo() {
		return "Restarigi ĉion";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms ne ŝargita";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Eraro estis detektita dum aliro al la API de LuckPerms.</b>"
				+ "<p>La mesaĝo indikas ke <b>LuckPerms ne estis ŝargita</b> kiam alia kromaĵo provis uzi ĝin.</p>"
				+ "<p><b>Eblaj kialoj:</b></p>" + "<ul>"
				+ "<li>La kromaĵo <b>LuckPerms ne estas instalita</b> aŭ <b>malsukcesis lanĉiĝi</b>.</li>"
				+ "<li>Alia kromaĵo provas aliri LuckPerms <b>tropre mature</b> aŭ <b>malĝuste</b>.</li>" + "</ul>"
				+ "<p>Rekomendiĝas <b>kontroli la konzolon</b> per la ligilo por identigi "
				+ "la kromaĵon, kiu alvokas LuckPerms, kaj kontroli ĝian kongruecon.</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Shaderpack de Iris ne ŝargita";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "nekonata" : shaderpack;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Eraro estis detektita dum ŝargado de shaderpack per Iris/Oculus.</b>"
				+ "<p><b>Afektita shaderpack:</b> " + nombre + "</p>"
				+ "<p>Minecraft ne povis malfermi la dosieron de la shaderpack (FileSystemNotFoundException).</p>"
				+ "<p><b>Eblaj solvoj:</b></p>" + "<ul>"
				+ "<li>Kontrolu ke la shaderpack estas ĝuste instalita en la dosierujo <b>shaderpacks</b>.</li>"
				+ "<li>Elŝutu la shaderpack denove, ĉar la dosiero eble estas difektita.</li>"
				+ "<li>Se la problemo daŭras, forigu la dosieron <b>config/iris.properties</b> por rekomenci la agordojn de Iris.</li>"
				+ "</ul>" + "<p>Post apliki la ŝanĝojn, restartigu la ludon.</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "Ne eblis skribi la agorddosieron";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "nekonata" : ruta;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Eraro okazis dum konservado de agorddosiero.</b>" + "<p><b>Afektita dosiero:</b> " + archivo + "</p>"
				+ "<p>Minecraft ne povis skribi la dosieron per atoma skribmaniero (REPLACE_ATOMIC).</p>"
				+ "<p><b>Tio kutime okazas pro:</b></p>" + "<ul>"
				+ "<li>Malĝustaj permesoj sur la dosierujo aŭ dosiero.</li>"
				+ "<li>La dosiero estas markita kiel nurlegebla.</li>"
				+ "<li>Alia programo (antiviruso, savkopio, redaktilo) blokas la dosieron.</li>" + "</ul>"
				+ "<p><b>Rekomendoj:</b></p>" + "<ul>" + "<li>Kontrolu ke vi havas skribpermeson en la dosierujo.</li>"
				+ "<li>Forigu la nurlegeblan atributon de la dosiero.</li>"
				+ "<li>Fermu programojn kiuj eble uzas tiun dosieron.</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "백업 생성 시 접근 거부됨";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "알 수 없음" : origen;
		String dst = backup == null || backup.isEmpty() ? "알 수 없음" : backup;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "설정 파일의 백업을 만드는 도중 권한 오류가 발생하였습니다.</b>" + "<p><b>원본 파일:</b> " + src + "</p>" + "<p><b>백업 파일:</b> "
				+ dst + "</p>" + "<p>운영체계가 파일 저장 과정에서 접근을 차단하였습니다.</p>" + "<p><b>이는 보통 다음 원인으로 일어납니다:</b></p>" + "<ul>"
				+ "<li>폴더에 대한 권한이 부족함.</li>" + "<li>파일이 읽기 전용으로 표시되여 있음.</li>"
				+ "<li>다른 프로그람(바이러스 백신, 동기화 소프트웨어, 편집기 등)이 파일을 사용하고 있음.</li>" + "</ul>" + "<p><b>권장 조치:</b></p>"
				+ "<ul>" + "<li><b>config</b> 폴더의 권한을 확인하십시오.</li>" + "<li>해당 파일에 접근하고 있을 수 있는 프로그람들을 종료하십시오.</li>"
				+ "<li>런처 또는 마인크래프트를 관리자 권한으로 실행해 보십시오.</li>" + "</ul>";
	}

}
