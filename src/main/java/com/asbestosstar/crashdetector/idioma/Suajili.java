package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Suajili implements Idioma {
	Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "sw";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "suajili";
	}

	@Override
	public String nombre_del_idioma() {
		return "Kiswahili";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_suajili.png");
	}

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Folda ya mods si halali</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Sijui faili ya JAR ya "
				+ Statics.nombre_cd.obtener() + " iko wapi</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Inatafuta PID: " + pid + "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + pid + ") imekufa!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Hakuna JVM</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Sasisha viendeshi vya ATI/AMD. Soma mwongozo huu: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>Mwongozo wa kusasisha viendeshi</a> https://www.amd.com/support/download/drivers.html</span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Baadhi ya matoleo ya zamani yanaweza kuwa na matatizo ya michoro ya Nouveau wakati wa kuanza.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Kuna tatizo na viendeshi vya picha. Sasisha viendeshi au hakikisha GPU sahihi inatumika. Soma mwongozo huu: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Mwongozo wa kusasisha viendeshi</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Dirisha la FML Early linashindwa. Nenda kwenye (.minecraft/config/fml.toml) na weka earlyWindowProvider=\"none\". Kwa Mac M1 tumia Java ya ARM.</span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Unakosa vitegemezi vinavyohitajika:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "Imeombwa na").replace("Expected range", "Kiwango kinachotarajiwa")
				+ "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Ripoti yako iko hapa <a href='" + archivo
				+ "' style='color:#" + config.obtenerColorEnlace() + "'>fungua ripoti</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Hii ni GUI ya " + Statics.nombre_cd.obtener()
				+ ". Ikiwa hakuna matatizo, unaweza kuipuuza.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>Fungua Ripoti</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Fungua ripoti kwenye kivinjari chako.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "Shiriki Ripoti";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "Shiriki ripoti. Kumbukumbu zitapakiwa kwenye securelogger.net.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Faili za JAR zenye matatizo zimegunduliwa:</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Kiwango:</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Inaweza kuwa mbaya:</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ModIDs zenye matatizo zimegunduliwa:</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Pakiti zenye matatizo zimegunduliwa:</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Madarasa muhimu hayapo (FATAL). Sababu za kawaida ni mods mbovu au utegemezi usiokamilika.</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Yaliyomo ndani ya {} (muhimu zaidi juu, hadi 20):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Mipangilio yenye tatizo ya SpongeMixin imegunduliwa:</b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Mods zina pakiti zilizojirudia. Ondoa pakiti hizo ndani ya faili ya JAR.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mods zimerudiwa</b> "
				+ linea.replace("from mod files", "kutoka faili za mod");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Mod ya MinecraftForge inaonekana kuwa na tatizo:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV inahitaji Lithostitched: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Ili kutumia Iris/Oculus unahitaji SODIUM au mbadala wake</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Tatizo la KubeJS resource pack: </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Matatizo yamegunduliwa kwenye viendeshi vya NVIDIA katika matoleo yaliyo kabla ya Windows 11."
				+ "</span><br/><br/>"
				+ "Ili kuhakikisha kwamba Minecraft (na JVM ya sasa) inatumia kadi ya picha ya NVIDIA iliyotengwa, fuata hatua hizi:<br/><br/>"
				+ "1. <strong>Tambua faili tekelezi ya Java:</strong><br/>"
				+ "   - Programu hii inatumia faili tekelezi hii ya Java: " + obtenerRutaJava() + "<br/>"
				+ "   - Ikiwa huoni njia maalum, unaweza kuitafuta faili ya Java kwa kutafuta 'java.exe' kwenye mfumo wako.<br/><br/>"
				+ "2. <strong>Fungua NVIDIA Control Panel:</strong><br/>"
				+ "   - Bofya kulia kwenye eneo-kazi na uchague 'NVIDIA Control Panel'.<br/><br/>"
				+ "3. <strong>Weka GPU unayopendelea:</strong><br/>"
				+ "   - Ndani ya NVIDIA Control Panel, nenda kwenye 'Manage 3D settings'.<br/>"
				+ "   - Chagua chaguo la 'Program Settings'.<br/>"
				+ "   - Bofya 'Add' kisha utafute faili ya Java iliyotambuliwa hapo juu (mfano: 'java.exe').<br/>"
				+ "   - Hakikisha imewekwa kutumia 'High-performance NVIDIA processor'.<br/><br/>"
				+ "4. <strong>Hifadhi mabadiliko:</strong><br/>"
				+ "   - Tumia mabadiliko hayo na ufunge NVIDIA Control Panel.<br/><br/>"
				+ "5. <strong>Anzisha upya Minecraft:</strong><br/>"
				+ "   - Anzisha upya Minecraft ili mabadiliko yaanze kufanya kazi.<br/><br/>"
				+ "Ikiwa unatumia Windows Server 2022 au Windows 10, hatua hizi zinafaa mradi tu viendeshi vya hivi karibuni vya NVIDIA vimewekwa.<br/><br/>"
				+ "Kumbuka: Ikiwa huwezi kupata NVIDIA Control Panel, hakikisha kuwa viendeshi vya NVIDIA vimewekwa vizuri.";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Matatizo yamegunduliwa kwenye viendeshi vya NVIDIA katika Windows 11/Server 2025 au toleo la baadaye."
				+ "</span><br/><br/>"
				+ "Ili kuhakikisha kwamba Minecraft (na JVM ya sasa) inatumia kadi ya picha ya NVIDIA iliyotengwa, fuata hatua hizi:<br/><br/>"
				+ "1. <strong>Tambua faili tekelezi ya Java:</strong><br/>"
				+ "   - Programu hii inatumia faili tekelezi hii ya Java: " + obtenerRutaJava() + "<br/>"
				+ "   - Ikiwa huoni njia maalum, unaweza kuitafuta faili ya Java kwa kutafuta 'java.exe' kwenye mfumo wako.<br/><br/>"
				+ "2. <strong>Fungua programu ya Mipangilio:</strong><br/>"
				+ "   - Bonyeza vitufe vya <code>Win + I</code> ili kufungua programu ya Mipangilio.<br/>"
				+ "   - Nenda kwenye <strong>Mfumo > Onyesho > Michoro</strong>.<br/><br/>"
				+ "3. <strong>Weka GPU unayopendelea:</strong><br/>"
				+ "   - Katika sehemu ya 'Michoro', bofya 'Mipangilio ya msingi ya michoro'.<br/>"
				+ "   - Chagua 'Programu za eneo-kazi' kisha bofya 'Vinjari'.<br/>"
				+ "   - Tafuta na uchague faili ya Java iliyotambuliwa hapo juu (mfano: 'java.exe').<br/>"
				+ "   - Baada ya kuiongeza, chagua programu ya Java kwenye orodha na uiweke kutumia 'Utendaji wa juu (NVIDIA)'.<br/><br/>"
				+ "4. <strong>Hifadhi mabadiliko:</strong><br/>"
				+ "   - Tumia mabadiliko hayo na ufunge programu ya Mipangilio.<br/><br/>"
				+ "5. <strong>Anzisha upya Minecraft:</strong><br/>"
				+ "   - Anzisha upya Minecraft ili mabadiliko yaanze kufanya kazi.<br/><br/>"
				+ "Ikiwa unatumia Windows 11 au Windows Server 2025+, hatua hizi zinafaa mradi tu viendeshi vya hivi karibuni vya NVIDIA vimewekwa.<br/><br/>"
				+ "Kumbuka: Ikiwa huoni chaguo la mipangilio ya michoro, hakikisha kuwa viendeshi vya NVIDIA vimewekwa vizuri.";
	}

	@Override
	public String segundo60Tick() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Seva au ulimwengu wako una ticks zinazozidi sekunde 60. Hii inaweza kusababishwa na mods zinazopunguza kasi ya seva au vifaa dhaifu sana.</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Huna RAM/kumbukumbu ya kutosha. Unahitaji kutenga zaidi.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Theseus pia ina matatizo mengine, ikiwa ni pamoja na kushindwa kufuta mods unapojaribu. Ikiwa unahitaji kucheza kwa kutumia faili za mrpack, unaweza kutumia vizindua vingine kama Prism Launcher (kwa modrinth.com tu), ATLauncher (kwa modrinth.com tu), au Hello Minecraft Launcher (inaendana na modrinth.com na bbsmc.net).</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Unatumia chaguo la \"Ruka kuanzisha Launcher\" (CurseForge App). Wakati mwingine hii husababisha matatizo magumu kugundua. "
				+ "Zima chaguo hili na tumia \"Mojang Launcher\" kwenye mipangilio ya CurseForge. "
				+ "Unaweza kutazama video hii (nenda dakika 1:11): "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>hapa</a>.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Onyo: Kuna madarasa yanayokosekana (si FATAL). Kwa kawaida si tatizo kubwa, lakini si kila wakati. "
				+ "Sababu za kawaida ni coremods mbovu au utegemezi unaokosekana. "
				+ "Unaweza kutumia QuickFix kutafuta mods zinazohusiana. Madarasa yanayokosekana:</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Hakuna matokeo</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Mahali pa kumbukumbu (logs):</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Hizi ni matokeo ya ukaguzi wako. Soma kwa makini. Kwa kawaida sababu sahihi iko kwenye ukaguzi wa 1 au 2. "
				+ "Ukaguzi wa baadaye unaweza kuwa matokeo ya mfululizo wa makosa (cascade) na unaweza kupuuzwa. "
				+ "Makosa hutokea kwa tabaka, hivyo kutatua tatizo kuu mara nyingi hutatua mengine pia.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tumia Java 17 kwa matoleo 1.17–1.20.4, Java 21 kwa matoleo mapya zaidi, na Java 8 kwa matoleo ya zamani. "
				+ "<a href='https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft'>Mwongozo</a>. "
				+ "Ikiwa bado kuna matatizo, inaweza kuwa mod haitangamani na toleo la Java.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 au zaidi haifanyi kazi kwa matoleo ya Minecraft chini ya 1.20.5 kwa sababu ASM imepitwa na wakati.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java yako imepitwa na wakati.</b> "
				+ versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Unahitaji JPMS module " + mod_necesitas
				+ " kutoka " + submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Haiwezi kuita " + metodo + " kwa sababu " + objeto
				+ " ni null</b>";
	}

	@Override
	public String analisisAvanzado() {
		return "Uchanganuzi wa Kina";
	}

	@Override
	public String seleccionarCarpeta() {
		return "Chagua folda";
	}

	@Override
	public String cadenaBusqueda() {
		return "Mfuatano wa utafutaji";
	}

	@Override
	public String usarRegex() {
		return "Tumia Regex";
	}

	@Override
	public String ignorarMayusculas() {
		return "Puuza herufi kubwa";
	}

	@Override
	public String buscar() {
		return "Tafuta";
	}

	@Override
	public String busquedaEnProgreso() {
		return "Utafutaji unaendelea";
	}

	@Override
	public String noSeEncontraronResultados() {
		return "Hakuna matokeo yaliyopatikana";
	}

	@Override
	public String errorBusqueda() {
		return "Hitilafu katika utafutaji";
	}

	@Override
	public String omitirYCerrar() {
		return "Ruka na funga";
	}

	@Override
	public String guardarYCerrar() {
		return "Hifadhi na funga";
	}

	@Override
	public String pegaLosRegistrosAqui() {
		return "Bandika kumbukumbu hapa";
	}

	@Override
	public String archivo() {
		return "Faili";
	}

	@Override
	public String incluir() {
		return "Jumuisha";
	}

	@Override
	public String abrir() {
		return "Fungua";
	}

	@Override
	public String endpointDeInforme() {
		return "Kituo cha ripoti (endpoint)";
	}

	@Override
	public String sitoDeLogging() {
		return "Tovuti ya kumbukumbu:";
	}

	@Override
	public String apiDeLogging() {
		return "API ya kumbukumbu:";
	}

	@Override
	public String anonimizarRegistros() {
		return "Fanya kumbukumbu zisitambulike (Beta)";
	}

	@Override
	public String botonDeCompartirInforme() {
		return "Shiriki ripoti na kumbukumbu zilizochaguliwa";
	}

	@Override
	public String arco() {
		return "Dirisha hili linakuwezesha kushiriki kumbukumbu kupitia API ya SecureLogger katika "
				+ "<a href=\"https://securelogger.net\">securelogger.net</a>. "
				+ "Unapobonyeza kitufe cha kushiriki ripoti, ripoti yako hutumwa kwenye kituo kilichochaguliwa "
				+ "(kwa chaguo-msingi asbestosstar.egoism.jp). "
				+ "Unaweza kushiriki kumbukumbu zote ulizochagua pamoja na ripoti. "
				+ "Ikiwa hutaki kupakia data, usitumie dirisha hili. "
				+ "Hatuchakata ripoti yako kwenye kituo rasmi; tunaondoa tu viungo visivyoruhusiwa. "
				+ "Msimbo uko hapa: <a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">msimbo chanzo</a>. "
				+ "Hii hutumika tu kuonyesha taarifa za hitilafu yako na viungo vya kumbukumbu. "
				+ "Unaweza pia kutumia kituo maalum ambacho kinaweza kuwa na tabia tofauti. "
				+ "Unatumia tovuti ya ripoti " + Config.obtenerInstancia().obtenerSitoDeInformes()
				+ " na tovuti ya kumbukumbu " + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". "
				+ "Unaweza pia kushiriki kumbukumbu binafsi bila ripoti kwa kutumia vitufe vya kushiriki. "
				+ Statics.nombre_cd.obtener()
				+ " ina mfumo wa kuondoa taarifa binafsi kama majina ya watumiaji, UUID, tokeni, IP, n.k., "
				+ "lakini si kamilifu. " + "Unaweza kuwasha au kuzima hili kupitia kisanduku cha chini. "
				+ "Wewe ndiye unadhibiti data zako na unaamua wapi zipakiwe. "
				+ "Tovuti za kumbukumbu ni za wahusika wengine na zinaweza kuwa na hatari. "
				+ "Ni muhimu kufahamu sheria kama RGPD na ARCO. "
				+ "Ikiwa uko Ulaya, unaweza kutumia <a href=\"https://securelogger.top\">securelogger.top</a>. "
				+ "Kwa taarifa zaidi za kisheria, angalia: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>, "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">RGPD</a>, "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">Sera ya Japani</a>.";
	}

	@Override
	public String enlaceDelReporte() {
		return "Kiungo cha ripoti:";
	}

	@Override
	public String guardarConfigDeCompartir() {
		return "Hifadhi mipangilio ya kushiriki";
	}

	@Override
	public String registroDemasiadoGrande() {
		return "Kumbukumbu hii ni kubwa sana kwa tovuti hii. Chagua nyingine na ujaribu tena.";
	}

	@Override
	public String errorConPublicarRegistro(String error) {
		return "Hitilafu wakati wa kuchapisha kumbukumbu: " + error;
	}

	@Override
	public String apiDeRegistroNoExiste() {
		return "API ya kumbukumbu haipo. Tafadhali badilisha mipangilio.";
	}

	@Override
	public String errorSSL() {
		return "Kuna hitilafu ya SSL. Hii hutokea mara nyingi kwa matoleo ya zamani ya Java, "
				+ "ikiwemo Java 8 kwenye Minecraft Launcher ya kawaida. "
				+ "Hii inaweza kuathiri vipengele kama usakinishaji wa Forge, kushiriki ripoti, "
				+ "mods zinazohitaji intaneti, na tovuti za kumbukumbu. "
				+ "Ikiwa unakutana na tatizo hili wakati wa kushiriki ripoti, "
				+ "jaribu kuchukua picha ya skrini na kutumia tovuti ya kumbukumbu inayooana na Java 8.";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JavaFML haioani: Inahitaji toleo " + requerido
				+ ", lakini imepatikana " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Tahadhari: JavaFML inahitaji toleo maalum la Minecraft Forge</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Faili ya JAR '" + archivoJar
				+ "' inahitaji mtoa huduma wa lugha '" + proveedor + "' toleo '" + requerido
				+ "' au zaidi, lakini imepatikana '" + encontrado + "'.</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ONYO: Crash Assistant si detector halisi ya malware. Inazuia uzinduzi wa mchezo kimakusudi. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Tazama msimbo</a>. "
				+ "Inashauriwa kuchunguza msimbo wa " + Statics.nombre_cd.obtener()
				+ " na Crash Assistant binafsi badala ya kutegemea madai.</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Mod '" + idMod
				+ "' imeshindwa kwa sababu darasa halikupatikana: '" + claseNoEncontrada
				+ "'. Hakikisha utegemezi wote umewekwa.</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "WaterMedia imezuia matumizi na TLauncher.</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Unatumia toleo la Optifine lisiloendana na toleo la Minecraft. Tumia toleo sahihi.</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Haikuweza kupakia huduma ya ModLauncher: </b>"
				+ servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Hitilafu ya kuchanganua JSON '" + recurso
				+ "' ndani ya JAR '" + archivoJar + "'.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Mod '" + modId + "' inahitaji '"
				+ dependencia + "' toleo '" + requerido + "' au zaidi, lakini imepatikana '" + actual + "'.</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GPU yako haiungi mkono toleo la OpenGL linalohitajika. Sasisha viendeshi au badilisha GPU.</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Ongeza kumbukumbu iliyotengwa au punguza matumizi ya mods/plugins.</b>";
	}

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "JVM ya 32-bit imegunduliwa. Haiwezi kutumia zaidi ya 4GB RAM. Tumia JVM ya 64-bit.</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Hitilafu ya PermGen. Ongeza kumbukumbu au punguza mzigo wa madarasa.</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Tatizo la uoanifu kati ya Java 8 na matoleo mapya.</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+ haiauniwi. Tumia Java 8 kwa Forge ya zamani.</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 8 inahitajika. Sasisha au rekebisha mipangilio.</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Hitilafu ya uoanifu: vizuizi havitumiki katika toleo hili. Angalia mods na seva.</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Hitilafu ya mpangilio wa skrini. Hakikisha mipangilio ya monitor na viendeshi ni sahihi.</b>";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Chaguo za GC za Java zina mgongano. Usichanganye algorithimu nyingi.</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Hitilafu ya usanidi wa Forge/NightConfig. Faili za config zinaweza kuwa mbovu. "
				+ "Unaweza kuzirekebisha au kuzifuta ili kuunda mpya.</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Hitilafu ya Intel HD Graphics. Sasisha viendeshi na hakikisha mipangilio sahihi.</b>";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "Onyo: Madarasa yanakosekana";
	}

	public String nombre_de_bloque_teselado() {
		return "Hitilafu ya uchoraji wa vizuizi";
	}

	public String nombre_de_contenido_de_stacktrace() {
		return "Uchambuzi wa stack trace";
	}

	public String nombre_de_cursed_consola() {
		return "Konsoli ya CurseForge haijakamilika";
	}

	public String nombre_de_early_window() {
		return "Hitilafu ya dirisha la awali";
	}

	public String nombre_de_drivers() {
		return "Tatizo la viendeshi vya picha";
	}

	public String nombre_de_error_de_config_mcforge() {
		return "Usanidi wa MCForge umeharibika";
	}

	public String nombre_de_error_de_monitor_lwjgl() {
		return "Hitilafu ya skrini (LWJGL)";
	}

	public String nombre_de_fabricmc_runtime_error_provided_by() {
		return "Hitilafu ya kuanzisha FabricMC";
	}

	public String nombre_de_falta_module_jmps() {
		return "Moduli za JPMS zinakosekana";
	}

	public String nombre_de_faltar_de_clases() {
		return "Madarasa muhimu hayapo";
	}

	public String nombre_de_faltas_dependencias_de_modlauncher() {
		return "Utegemezi wa ModLauncher unakosekana";
	}

	public String nombre_de_java_versiones() {
		return "Matoleo ya Java hayalingani";
	}

	public String nombre_de_faltar_de_kubejs_resourcepack() {
		return "Tatizo la rasilimali ya KubeJS";
	}

	public String nombre_de_lenguaje_proveedor_check() {
		return "Mtoa huduma wa lugha haioani";
	}

	public String nombre_de_faltar_de_liyhostictchctov() {
		return "Tatizo la Lithostitched";
	}

	public String nombre_de_malware_falso_crash_assistant() {
		return "Onyo: malware ya uongo imegunduliwa";
	}

	public String nombre_de_mcforge_mod_sespechoso() {
		return "Mod yenye shaka imegunduliwa";
	}

	public String nombre_de_mods_duplicados_modlauncher() {
		return "Mods zimerudiwa";
	}

	public String nombre_de_modules_duplicados_jmps() {
		return "Migongano ya moduli JPMS";
	}

	public String nombre_de_necesitas_sodium() {
		return "Sodium inahitajika";
	}

	public String nombre_de_no_puede_analizar_json_de_registro() {
		return "Hitilafu ya JSON ya kumbukumbu";
	}

	public String nombre_de_no_tiene_memoria() {
		return "Kumbukumbu haitoshi";
	}

	public String nombre_de_null_pointer() {
		return "Hitilafu ya NullPointer";
	}

	public String nombre_de_opciones_java_gc_invalidas() {
		return "Chaguo za GC si halali";
	}

	public String nombre_de_optifine_obsoleta() {
		return "OptiFine isiyolingana / ya zamani";
	}

	public String nombre_de_60_segundo_trick() {
		return "Tick muhimu ya seva (sekunde 60)";
	}

	public String nombre_de_servicio_de_modlauncher_no_funciona() {
		return "Huduma za ModLauncher zimeshindwa";
	}

	public String nombre_de_spongemixin_configs_problematicos() {
		return "Mipangilio ya SpongeMixin yenye matatizo";
	}

	public String nombre_de_theseus() {
		return "Theseus haiendani";
	}

	public String nombre_de_watermedia_tl() {
		return "Launcher ya TLauncher haiungwa mkono na WATERMeDIA";
	}

	@Override
	public String auditorias_transformer() {
		return "Ukaguzi wa Transformer";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Haya ni matokeo ya ukaguzi wa Transformer katika Launcher ya kawaida. "
				+ "Kwa kawaida si sahihi kama uchambuzi wa StackTrace, lakini Launcher ya kawaida "
				+ "si kila wakati ina taarifa kamili.</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "Hii hutafuta mods zilizoundwa kwa kutumia MCreator. Ingawa nyingi ni nzuri, "
				+ "baadhi zinaweza kuwa na matatizo au sifa mbaya. Hii husaidia kuzitambua. "
				+ "Tambua kuwa hata mods zilizo bora zinaweza kuonekana kama za MCreator "
				+ "kwa sababu ya ujumuishaji au matumizi ya zana zake.";
	}

	@Override
	public String escanear() {
		return "Changanua";
	}

	@Override
	public String cargando() {
		return "Inapakia";
	}

	@Override
	public String inicioApp() {
		return "Mwanzo wa programu/mchezo";
	}

	@Override
	public String ajustesCrashDetector() {
		return "Mipangilio ya " + Statics.nombre_cd.obtener();
	}

	@Override
	public String confidencialidad() {
		return "Faragha";
	}

	@Override
	public String tooltip() {
		return "Kidokezo";
	}

	@Override
	public String config() {
		return "Mipangilio";
	}

	@Override
	public String abrirCarpeta() {
		return "Fungua folda";
	}

	@Override
	public String actualizar() {
		return "Sasisha";
	}

	@Override
	public String anadirRegistro() {
		return "Ongeza kumbukumbu";
	}

	@Override
	public String usarIdiomaDelSistema() {
		return "Tumia lugha ya mfumo";
	}

	@Override
	public String volver() {
		return "Rudi";
	}

	@Override
	public String colorFondo() {
		return "Rangi ya mandharinyuma (#RRGGBB):";
	}

	@Override
	public String colorTexto() {
		return "Rangi ya maandishi (#RRGGBB):";
	}

	@Override
	public String colorBoton() {
		return "Rangi ya kitufe (#RRGGBB):";
	}

	@Override
	public String colorCajaTexto() {
		return "Rangi ya kisanduku cha maandishi (#RRGGBB):";
	}

	@Override
	public String colorEnlace() {
		return "Rangi ya kiungo (#RRGGBB):";
	}

	@Override
	public String colorTitulosConsolas() {
		return "Rangi ya vichwa vya konsoli (#RRGGBB):";
	}

	@Override
	public String colorError() {
		return "Rangi ya hitilafu (#RRGGBB):";
	}

	@Override
	public String colorAdvertencia() {
		return "Rangi ya onyo (#RRGGBB):";
	}

	@Override
	public String colorInfo() {
		return "Rangi ya taarifa (#RRGGBB):";
	}

	@Override
	public String colorTitulo() {
		return "Rangi ya kichwa (#RRGGBB):";
	}

	@Override
	public String colorEnlaceTexto() {
		return "Rangi ya maandishi ya kiungo (#RRGGBB):";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "Fungua " + Statics.nombre_cd.obtener() + " tu wakati kuna hitilafu";
	}

	@Override
	public String activar_parche() {
		return "Washa kiraka";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "Hakuna suluhisho linalopatikana";
	}

	@Override
	public String error() {
		return "Hitilafu";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "Hitilafu wakati wa kufuta JAR";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "JAR imefutwa kwa mafanikio";
	}

	@Override
	public String exito() {
		return "Mafanikio";
	}

	@Override
	public String eliminar() {
		return "Futa";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "Hitilafu wakati wa kufuta pakiti";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "Pakiti imefutwa kwa mafanikio";
	}

	@Override
	public String eliminar_paquete() {
		return "Futa pakiti";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "Hakuna mods zilizopatikana zilizo na pakiti hii";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "Haiwezekani kufuta pakiti";
	}

	@Override
	public String eliminar_jar() {
		return "Futa JAR";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "Hakuna mods rudufu zilizopatikana";
	}

	@Override
	public String archivo_no_encontrado() {
		return "Faili haikupatikana";
	}

	@Override
	public String directorio_eliminado() {
		return "Saraka imefutwa";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "Hitilafu wakati wa kufuta JAR iliyowekwa ndani";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "Faili ya ndani haikupatikana";
	}

	@Override
	public String archivo_eliminado() {
		return "Faili imefutwa";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "Hitilafu wakati wa kufuta faili";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "Faili ya nje si halali";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "Kipengee cha mod kimefutwa";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "Hitilafu wakati wa kubadilisha JAR ya nje";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "Hitilafu wakati wa kufuta kipengee cha mod";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "Hitilafu wakati wa kufuta saraka";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "Umbizo si halali kwa JAR iliyowekwa ndani";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "JAR iliyowekwa ndani imefutwa";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "Hitilafu wakati wa kusafisha faili za muda";
	}

	@Override
	public String mensaje_de_trace_fatal_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Ujumbe wa mwisho wa trace mbaya (haujatafsiriwa):</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Ujumbe wa mwisho wa trace (haujatafsiriwa):</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "Unaweza kutafuta katika hifadhidata ya WaifuNeoForge ili kupata mods. "
				+ "Chagua toleo la mchezo, loader ya mods, na darasa. "
				+ "Tumia mchanganyiko unaofanana zaidi. Unaweza kutafuta mara moja kwa dakika.";
	}

	@Override
	public String solucionFaltasClases() {
		return solucionParaAdvertenciaFaltasClases();
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "Unaweza kusakinisha toleo sahihi la Java kupitia meneja wa pakiti wa mfumo wako "
				+ "au kwa kutumia vitufe vilivyopo.";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod ina uhuishaji unaokosekana:</b> ";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException (kipengee hakipatikani) – uhuishaji unakosekana";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "Hakuna mods zilizopatikana za kufuta";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "Badilisha chaguo za GC zinazogongana kwa -XX:+UseG1GC";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "Ongeza ukubwa wa heap kwa kutumia -Xmx.";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "Ongeza ukubwa wa PermGen kwa kutumia -XX:MaxPermSize.";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "Tumia JVM ya 64-bit ili kuongeza kumbukumbu inayopatikana.";
	}

	@Override
	public String optimizarCodigo() {
		return "Boresha msimbo ili kupunguza matumizi ya kumbukumbu na kuboresha utendaji.";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "Tumia mkusanyaji taka (GC) wenye ufanisi ili kupunguza ucheleweshaji.";
	}

	@Override
	public String modulos() {
		return "Moduli";
	}

	@Override
	public String paquete() {
		return "Pakiti";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "Kuna vitambulisho (IDs) vinavyokosekana. Sababu za kawaida ni mods au data ya vitu kukosekana. "
				+ "Folda za kawaida ni datafiedcontents/ na kubejs/ au folda nyingine za mods.";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "Rekodi zimepangwa vibaya";
	}

	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Plugin 'AuthMe' imeshindwa kupakia na imefunga seva.</b> ";
	}

	public String nombreProblemaCierreAuthMe() {
		return "Tatizo la kufungwa kwa sababu ya AuthMe";
	}

	public String solucionCierreAuthMe() {
		return "Thamani ya 'stopServer' imewekwa kuwa 'true'.";
	}

	/**
	 * Suluhisho la kusanidi plagin ya AuthMe.
	 */
	public String solucionConfigurarPluginAuthMe() {
		return "Sanidi plagin ya AuthMe (plugins/AuthMe/config.yml)";
	}

	/**
	 * Suluhisho la kusakinisha toleo tofauti la AuthMe.
	 */
	public String solucionInstalarVersionDiferenteAuthMe() {
		return "Sakinisha toleo tofauti la plagin 'AuthMe'";
	}

	/**
	 * Suluhisho la kuondoa plagin ya AuthMe.
	 */
	public String solucionEliminarPluginAuthMe() {
		return "Futa plagin 'AuthMe'";
	}

	/**
	 * Ujumbe wa hitilafu kwa ulimwengu uliharibika (Multiverse).
	 */
	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Ulimwengu '" + nombreMundo
				+ "' haukuweza kupakiwa kwa sababu una hitilafu na huenda umeharibika.</b> ";
	}

	/**
	 * Jina la tatizo kwa kiolesura.
	 */
	@Override
	public String nombreProblemaCargaMultiverso() {
		return "Tatizo la kupakia Multiverse";
	}

	/**
	 * Suluhisho la kurekebisha ulimwengu.
	 */
	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "Rekebisha ulimwengu '" + nombreMundo + "', kwa mfano kwa kutumia Minecraft Region Fixer au MCEdit.";
	}

	/**
	 * Suluhisho la kufuta folda ya ulimwengu.
	 */
	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "Futa folda ya ulimwengu '" + nombreMundo + "'.";
	}

	/**
	 * Hitilafu ya usanidi wa PermissionsEx.
	 */
	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Usanidi wa 'PermissionsEx' si sahihi.</b> ";
	}

	/**
	 * Jina la tatizo.
	 */
	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "Tatizo la usanidi wa PermissionsEx";
	}

	/**
	 * Suluhisho la kusanidi PermissionsEx.
	 */
	@Override
	public String solucionConfigurarPermissionsEx() {
		return "Sanidi plagin ya PermissionsEx (plugins/PermissionsEx/permissions.yml)";
	}

	/**
	 * Suluhisho la kuondoa PermissionsEx.
	 */
	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "Futa plagin 'PermissionsEx'";
	}

	/**
	 * Hitilafu ya majina ya plagin yanayofanana.
	 */
	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Kuna faili nyingi za plagin kwa jina '"
				+ nombrePlugin + "': '" + primerPath + "' na '" + segundoPath + "'.</b> ";
	}

	/**
	 * Jina la tatizo.
	 */
	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "Tatizo la jina tata la plagin";
	}

	/**
	 * Suluhisho la kufuta plagin.
	 */
	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "Futa plagin '" + nombrePlugin + "'";
	}

	/**
	 * Hitilafu ya kupakia chunks.
	 */
	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Kulitokea hitilafu wakati ulimwengu unapakia chunks. "
				+ "Ikiwezekana, mod ya FeatureRecycler inaweza kusaidia: "
				+ "https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
	}

	/**
	 * Jina la tatizo.
	 */
	@Override
	public String nombreProblemaCargaChunk() {
		return "Hitilafu wakati wa kupakia chunks";
	}

	/**
	 * Suluhisho la kuondoa chunk.
	 */
	@Override
	public String solucionEliminarChunk() {
		return "Ondoa chunk yenye tatizo kutoka ulimwengu, kwa mfano kwa kutumia MCEdit "
				+ "au kwa kufuta faili ya region.";
	}

	/**
	 * Hitilafu ya amri ya plagin.
	 */
	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plagin '" + nombrePlugin
				+ "' haiwezi kutekeleza amri '/" + comando + "'.</b> ";
	}

	/**
	 * Jina la tatizo.
	 */
	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "Hitilafu wakati wa kutekeleza amri ya plagin";
	}

	/**
	 * Suluhisho la kusakinisha toleo tofauti la plagin.
	 */
	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "Sakinisha toleo tofauti la plagin '" + nombrePlugin + "'";
	}

	/**
	 * Hitilafu ya utegemezi mmoja.
	 */
	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plagin '" + nombrePlugin + "' inahitaji plagin '"
				+ dependencia + "'.</b> ";
	}

	/**
	 * Hitilafu ya utegemezi mwingi.
	 */
	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>Plagin '" + nombrePlugin
				+ "' inakosa vitegemezi vifuatavyo: " + deps.toString() + ".</b> ";
	}

	/**
	 * Jina la tatizo.
	 */
	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "Kitegemezi cha plagin kinakosekana";
	}

	/**
	 * Suluhisho la kusakinisha plagin.
	 */
	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "Sakinisha plagin '" + nombrePlugin + "'";
	}

	/**
	 * Hitilafu ya API isiyolingana.
	 */
	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plagin '" + nombrePlugin
				+ "' inahitaji API toleo '" + versionAPI + "' ambalo halilingani na seva ya sasa.</b> ";
	}

	/**
	 * Jina la tatizo.
	 */
	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "Toleo la API halilingani";
	}

	/**
	 * Suluhisho la kusakinisha toleo la seva.
	 */
	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "Sakinisha toleo '" + version + "' la seva yako.";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Ulimwengu '" + nombreMundo
				+ "' ni rudufu na hauwezi kupakiwa.</b> ";
	}

	/**
	 * Jina la tatizo.
	 */
	@Override
	public String nombreProblemaMundoDuplicado() {
		return "Ulimwengu rudufu";
	}

	/**
	 * Suluhisho la kufuta uid.dat.
	 */
	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "Futa faili 'uid.dat' kutoka ulimwengu '" + nombreMundo + "'";
	}

	/**
	 * Suluhisho la kufuta ulimwengu mzima.
	 */
	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "Futa folda ya ulimwengu '" + nombreMundo + "'";
	}

	/**
	 * Hitilafu ya entity/block entity inayosababisha ticking.
	 */
	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		String color = config.obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>Entity au Block Entity '</span>" + nombre
				+ "<span style='color:#" + color + "'>' ya aina '</span>" + tipo + "<span style='color:#" + color
				+ "'>' katika eneo </span>" + coords + "<span style='color:#" + color
				+ "'> inasababisha hitilafu za ticking.</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'>" + "Maelekezo ya kurekebisha:<br>"
				+ "1. **MCForge**: Nenda kwenye '[jina_la_ulimwengu]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: Nenda kwenye 'config/neoforge-server.toml'.<br>"
				+ "   *(Kwa singleplayer, ulimwengu uko kwenye folda ya 'saves')*.<br>"
				+ "3. Badilisha **removeErroringBlockEntities** na **removeErroringEntities** kuwa **true**.<br><br>"
				+ "Njia nyingine:<br>" + "- **MCEdit**: Futa entity kwenye kuratibu zilizoonyeshwa.<br>"
				+ "- **Neruina (Mod)**: Inaweza kuzuia crash lakini si ya kuaminika kila wakati." + "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "Entity yenye tatizo";
	}

	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "Futa entity '" + nombre + "' katika eneo " + coords + " kwa kutumia MCEdit au kuhariri ulimwengu.";
	}

	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' ina matoleo mengi yaliyosakinishwa.</b> ";
	}

	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "Mod rudufu kwenye Fabric";
	}

	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "Futa faili rudufu ya mod: " + rutaMod;
	}

	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mods '" + primerMod + "' na '" + segundoMod
				+ "' hazilingani.</b> ";
	}

	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "Mod isiyolingana kwenye Fabric";
	}

	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "Futa mod '" + nombreMod + "'";
	}

	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' ina hitilafu kubwa na haiwezi kuendeshwa.</b> ";
	}

	@Override
	public String nombreProblemaModFatal() {
		return "Mod yenye hitilafu kubwa";
	}

	@Override
	public String mensajeModDependenciaPlural(List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Mods zifuatazo zinahitajika lakini hazijasakinishwa: " + deps.toString() + ".</b>";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod + "' inahitaji mod '"
					+ dependencia + "'.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod + "' inahitaji mod '"
					+ dependencia + "' yenye toleo " + version + ".</b>";
		}
	}

	@Override
	public String nombreProblemaDependenciaMod() {
		return "Kitegemezi cha mod kinakosekana";
	}

	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "Sakinisha mod '" + nombreMod + "'";
	}

	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "Sakinisha mod '" + nombreMod + "' yenye toleo " + version;
	}

	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plagin '" + nombrePlugin
				+ "' haiendani na ticking ya kieneo ya Folia.</b> ";
	}

	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Plagin zifuatazo hazilingani na ticking ya kieneo ya Folia: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0)
				sb.append(", ");
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaPluginTickingRegional() {
		return "Plagin isiyolingana na ticking ya kieneo";
	}

	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "Sakinisha programu isiyotumia ticking ya kieneo, kama " + software;
	}

	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' haipo kwenye datapack.</b>";
	}

	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Mods zifuatazo hazipo kwenye datapack: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1)
					sb.append(" na ");
				else
					sb.append(", ");
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaModFaltanteEnDatapack() {
		return "Mod inakosekana kwenye datapack";
	}

	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' imesababisha hitilafu.</b>";
	}

	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Mods zifuatazo zimesababisha hitilafu: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" na ");
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
		return "Mod yenye hitilafu";
	}

	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "Sakinisha toleo tofauti la mod '" + nombreMod + "'";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' haiendani na toleo la Minecraft " + versionMC + ".</b>";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Mods zifuatazo hazilingani na matoleo ya Minecraft: ");

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
		return "Mod isiyolingana na Minecraft";
	}

	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "Sakinisha toleo la Forge linalolingana na Minecraft " + versionMC;
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' inakosekana na inahitajika ili kupakia ulimwengu au plugin.</b>";
	}

	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "Mod inakosekana";
	}

	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Ulimwengu ulihifadhiwa na mod '" + nombreMod
				+ "' ambayo sasa haipo.</b>";
	}

	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Ulimwengu ulihifadhiwa na mods zifuatazo ambazo sasa hazipo: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" na ");
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
		return "Mod inakosekana kwenye ulimwengu";
	}

	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Ulimwengu ulihifadhiwa na mod '" + nombreMod
				+ "' toleo " + versionEsperada + ", lakini sasa ipo kwenye toleo " + versionActual + ".</b>";
	}

	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Mods zifuatazo zina tofauti ya matoleo katika ulimwengu ulihifadhiwa: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" na ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (Ilihifadhiwa: ").append(versionesEsperadas.get(i)).append(", Sasa: ")
					.append(versionesActuales.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaVersionModMundo() {
		return "Tofauti ya toleo la mod kwenye ulimwengu";
	}

	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Umejaribu kufungua ulimwengu ulioundwa kwa toleo jipya zaidi la Minecraft.</b>";
	}

	@Override
	public String nombreProblemaVersionDowngrade() {
		return "Jaribio la kufungua ulimwengu kutoka toleo la zamani";
	}

	@Override
	public String solucionVersionDowngrade() {
		return "Sakinisha toleo jipya zaidi la Minecraft.";
	}

	@Override
	public String solucionGenerarNuevoMundo() {
		return "Unda ulimwengu mpya.";
	}

	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin + "' inahitaji plugin '"
				+ dependencia + "'.</b>";
	}

	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Plugins zifuatazo zinahitaji utegemezi ambao haujasakinishwa: ");

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
		return "Plugin yenye utegemezi unaokosekana";
	}

	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' haiendani na toleo la sasa la seva.</b>";
	}

	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Plugins zifuatazo hazilingani na toleo la sasa la seva: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" na ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Jina la tatizo kwa kuonyesha kwenye kiolesura.
	 */
	@Override
	public String nombreProblemaPluginIncompatible() {
		return "Plugin isiyolingana na PocketMine-MP";
	}

	/**
	 * Ujumbe wa hitilafu kwa plugin yenye hitilafu wakati wa utekelezaji (umoja).
	 */
	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' imesababisha hitilafu wakati wa utekelezaji.</b>";
	}

	/**
	 * Ujumbe wa hitilafu kwa plugins nyingi zenye hitilafu wakati wa utekelezaji
	 * (wingi).
	 */
	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Plugins zifuatazo zimesababisha hitilafu wakati wa utekelezaji: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" na ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Jina la tatizo kwa kuonyesha kwenye kiolesura.
	 */
	@Override
	public String nombreProblemaPluginEjecucion() {
		return "Plugin yenye hitilafu ya utekelezaji";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "LegacyRandomSource ya nyuzi nyingi";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Kuna tatizo la nyuzi nyingi kufikia darasa la LegacyRandomSource kwa wakati mmoja. "
				+ "Unaweza kupata taarifa zaidi kwa mod Unsafe World Random Access Detector au mod C2ME.</b> ";
	}

	@Override
	public String desdeUltimoExito() {
		return "Tangu mafanikio ya mwisho";
	}

	@Override
	public String noHayCambios() {
		return "Hakuna mabadiliko";
	}

	@Override
	public String desdeUltimoIntento() {
		return "Tangu jaribio la mwisho";
	}

	@Override
	public String fallo() {
		return "Imeshindwa";
	}

	@Override
	public String diferentesDeLasMods() {
		return "Tofauti na mods";
	}

	@Override
	public String historialDeMods() {
		return "Historia ya mods";
	}

	@Override
	public String archivo0() {
		return "Faili0";
	}

	@Override
	public String archivo1() {
		return "Faili1";
	}

	@Override
	public String comparar() {
		return "Linganisha";
	}

	@Override
	public String seleccionarDosArchivos() {
		return "Chagua faili mbili";
	}

	@Override
	public String archivoExito() {
		return "Faili ya mafanikio";
	}

	@Override
	public String archivoFalla() {
		return "Faili ya hitilafu";
	}

	@Override
	public String errorComparandoArchivos() {
		return "Hitilafu wakati wa kulinganisha faili";
	}

	@Override
	public String comparando() {
		return "Inalinganisha";
	}

	@Override
	public String con() {
		return "Na";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
				+ "<p><b>Paneli ya Ulinganisho wa Historia ya Mods</b></p>"
				+ "<p>Paneli hii hukuwezesha kulinganisha orodha mbili za mods kutoka vipindi tofauti vya uendeshaji. "
				+ "Chagua faili moja upande wa kushoto na nyingine upande wa kulia ili kuona mabadiliko kati ya matoleo hayo mawili.</p>"

				+ "<h3>Jinsi ya kutumia:</h3><ol>"
				+ "<li><b>Kuchagua faili:</b> Bofya vitufe vya redio karibu na majina ya faili. "
				+ "Faili zinazoishia na <span style='color: #4CAF50; font-weight: bold;'>.exito</span> zinaonyesha vipindi vilivyofanikiwa, "
				+ "wakati zile za <span style='color: #F44336; font-weight: bold;'>.falla</span> zinaonyesha makosa.</li>"

				+ "<li><b>Kulinganisha kiotomatiki:</b> Ukibonyeza kitufe cha 'Compare', mfumo utachambua orodha mbili zilizochaguliwa "
				+ "na kuonyesha mods zilizoongezwa (+) au zilizoondolewa (-).</li>"

				+ "<li><b>Kuonyesha matokeo:</b> Matokeo huwasilishwa kwa HTML yenye rangi tofauti: " + "<ul>"
				+ "<li><span style='color: green;'>+ Mod imeongezwa</span></li>"
				+ "<li><span style='color: red;'>- Mod imeondolewa</span></li>" + "</ul></li></ol>"

				+ "<h3>Sifa kuu:</h3><ul>"
				+ "<li>Inaunga mkono mchanganyiko wowote wa faili (mafanikio/kushindwa).</li>"
				+ "<li>Inaonyesha tofauti za pande mbili kwa utambuzi sahihi wa mabadiliko.</li>"
				+ "<li>Ina scroll kwa orodha ndefu za mods.</li>"
				+ "<li>Ina picha za maelezo kusaidia uelewa wa kuona.</li>" + "</ul>"

				+ "<p>Imetengenezwa kwa <3 kusaidia kufuatilia mabadiliko ya usanidi wako.</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Inawezekana una tatizo linalohusiana na IPv6. Kuna suluhisho mbili: "
				+ "1) Ongeza hoja ya JVM <code>-Djava.net.preferIPv4Stack=true</code> kwenye launcher yako, au "
				+ "2) Tumia kitufe cha 'QuickFix' kwenye " + Statics.nombre_cd.obtener()
				+ " kuwezesha mpangilio huu kiotomatiki." + "</b>";
	}

	@Override
	public String parcheIPv4() {
		return "Patch ya IPv4/IPv6";
	}

	@Override
	public String carpetaHMCL() {
		return "Folda ya HMCL (kwa HelloMinecraftLauncher tu)";
	}

	@Override
	public String descripcionCurseforge() {
		return "Kumbuka: Hakuna log itakayozalishwa ikiwa chaguo \"Ruka Launcher\" limewashwa katika Settings > Minecraft";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/UltimMC: Bofya kulia kwenye instance na uchague \"Hariri Instance\". "
				+ "Kisha tafuta sehemu ya \"Minecraft Logs\".<br>"
				+ "Logs hizi zina STDOUT muhimu kwa kutambua hitilafu.";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL: Chagua folda ya usakinishaji wa HMCL na uchague \".hmcl\". "
				+ "Logs huhifadhiwa hapo na zina taarifa muhimu kuhusu hitilafu.<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: Ina tab ya maendeleo yenye logs za kina ndani ya menyu ya options.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: Ina dirisha la popup lenye logs. Unaweza kunakili au kupakia logs. "
				+ "Logs hutengenezwa kiotomatiki wakati wa kuanza mchezo.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: Bofya kulia instance, chagua \"Mipangilio\", kisha nenda kwenye sehemu ya Logs kuona STDOUT.";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "Viungo vya Markdown: Bandika viungo vya logs hapa. Mfumo utachambua kiotomatiki (latest.log, debug.log, nk).";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "Log ya Launcher haijapatikana";
	}

	@Override
	public String imagenNoEncontrada() {
		return "Picha haijapatikana";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "Chagua aina ya launcher unayotumia. Logs za launcher zina taarifa muhimu ambazo hazipo kwenye latest.log.<br>"
				+ Statics.nombre_cd.obtener() + " haiwezi kusoma logs moja kwa moja, unaweza kuziingiza kwa mkono.<br>"
				+ "Angalia: https://github.com/HMCL-dev/HMCL/issues/2663";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Kuna classes za Create zinazokosekana. Sasisha addons, ondoa zilizopitwa na wakati, au tumia toleo sahihi.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Kuna classes za EpicFight zinazokosekana. Sasisha addons au tumia toleo sahihi.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>OpenJ9 haitumiki. Sakinisha OpenJDK Hotspot au Oracle JDK.</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Unahitaji Java 11 au zaidi. Sasisha JDK yako.</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Umetenga RAM nyingi sana. Punguza kiasi kilichotengwa.</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Pendekezo: tumia 70–80% ya RAM yako pekee.</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Punguza thamani ya Xmx kwenye mipangilio ya launcher.</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Faili ya Forge '" + archivo
				+ "' haipo. Sakinisha tena Forge.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Toleo la Minecraft " + version + " halipo kwenye '"
				+ archivo + "'.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Target 'fmlclient' haipatikani. Sakinisha tena Forge.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Darasa kuu la Minecraft halipatikani. Reinstall Forge.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Usakinishaji wa Forge haujakamilika.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "Usakinishaji wa Forge haujakamilika";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Ili kutatua tatizo hili, unahitaji kusakinisha tena Forge kwa usahihi. "
				+ "Hakikisha unapakua toleo linalolingana na toleo lako la Minecraft na kufuata hatua zote bila kukatiza.</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "Pakua Forge rasmi";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "Jinsi ya kusakinisha tena Forge kwa usahihi";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>Maelekezo ya kusakinisha tena Forge:</h3>" + "<ol>"
				+ "<li>Pakua kisakinishi sahihi cha Forge kutoka tovuti rasmi (toleo linalopendekezwa kwa Minecraft yako)</li>"
				+ "<li>Funga kabisa launcher ya Minecraft</li>"
				+ "<li>Endesha kisakinishi cha Forge kama msimamizi</li>" + "<li>Chagua chaguo la 'Installer'</li>"
				+ "<li>Chagua folda ya wasifu wa Minecraft</li>"
				+ "<li>Bonyeza 'OK' na subiri usakinishaji ukamilike</li>"
				+ "<li>Anzisha tena launcher na hakikisha Forge inaonekana kwenye profiles</li>" + "</ol>"
				+ "<p><b>Kumbuka:</b> Ikiwa unatumia launcher maalum, hakikisha umechagua folda sahihi.</p>"
				+ "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "Maelekezo ya kusakinisha tena Forge";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Hitilafu ya kuunganisha: Maktaba "
				+ nombreBiblioteca + " haikuweza kupakiwa.<br/><br/>"
				+ "a) Ongeza folda ya maktaba kwenye -Djava.library.path au -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Ongeza faili ya JAR kwenye classpath.<br/><br/>"
				+ "Hitilafu hii hutokea wakati Minecraft haiwezi kupata faili muhimu.</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "Hitilafu ya kuunganisha";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Maktaba haikupatikana. Suluhisho:<br/><br/>"
				+ "a) Ongeza njia ya maktaba kwenye java.library.path.<br/>"
				+ "b) Ongeza JAR kwenye classpath.<br/><br/>"
				+ "Kwa watumiaji wengi, ni rahisi kusakinisha tena Minecraft.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mgongano wa ID: " + id + " tayari inatumiwa na "
				+ modOrigen + " wakati wa kuongeza " + modDestino + ".</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Kiwango cha juu cha ID kimezidiwa.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "Mgongano wa IDs";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Tumia JustEnoughIDs au EndlessIDs kulingana na toleo la Minecraft.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Tumia IdFix Minus au Minecraft-ID-Resolver kurekebisha mgongano wa IDs.</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "Sakinisha JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "Sakinisha EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "Tumia IdFix Minus";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "Tumia Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "Angalia nyaraka za Kijapani";
	}

	@Override
	public String escanearDeMCreator() {
		return "Changanua MCreator";
	}

	@Override
	public String tituloArbolDeMods() {
		return "Mti wa Mods na Madarasa";
	}

	@Override
	public String tipoBusqueda() {
		return "Aina";
	}

	/**
	 * Hupata maandishi ya kichujio cha "Zote".
	 *
	 * @return Maandishi ya kichujio
	 */
	@Override
	public String filtroTodos() {
		return "Zote";
	}

	/**
	 * Hupata maandishi ya kichujio cha "Pakiti".
	 *
	 * @return Maandishi ya kichujio
	 */
	@Override
	public String filtroPaquetes() {
		return "Pakiti";
	}

	/**
	 * Hupata maandishi ya kichujio cha "Madarasa".
	 *
	 * @return Maandishi ya kichujio
	 */
	@Override
	public String filtroClases() {
		return "Madarasa";
	}

	/**
	 * Hupata maandishi ya kichujio cha "Mbinu".
	 *
	 * @return Maandishi ya kichujio
	 */
	@Override
	public String filtroMetodos() {
		return "Mbinu";
	}

	/**
	 * Hupata maandishi ya kichujio cha "Sehemu".
	 *
	 * @return Maandishi ya kichujio
	 */
	@Override
	public String filtroCampos() {
		return "Sehemu";
	}

	/**
	 * Hupata maandishi ya kichujio cha "Marejeleo ya Sehemu".
	 *
	 * @return Maandishi ya kichujio
	 */
	@Override
	public String filtroReferenciasCampo() {
		return "Marejeleo ya Sehemu";
	}

	/**
	 * Hupata maandishi ya kichujio cha "Marejeleo ya Mbinu".
	 *
	 * @return Maandishi ya kichujio
	 */
	@Override
	public String filtroReferenciasMetodo() {
		return "Marejeleo ya Mbinu";
	}

	/**
	 * Hupata maandishi ya kichujio cha "Marejeleo ya Darasa".
	 *
	 * @return Maandishi ya kichujio
	 */
	@Override
	public String filtroReferenciasClase() {
		return "Marejeleo ya Darasa";
	}

	/**
	 * Hupata maandishi ya kidokezo cha uga wa utafutaji.
	 *
	 * @return Maandishi ya kidokezo
	 */
	@Override
	public String tipBuscar() {
		return "Andika hapa kutafuta katika mti wa mods";
	}

	/**
	 * Hupata maandishi ya kitufe cha kutafuta.
	 *
	 * @return Maandishi ya kitufe
	 */
	@Override
	public String botonBuscar() {
		return "Tafuta";
	}

	/**
	 * Hupata maandishi ya kitufe cha kuweka upya mti.
	 *
	 * @return Maandishi ya kitufe
	 */
	@Override
	public String botonResetearArbol() {
		return "Weka upya mti";
	}

	/**
	 * Hupata maandishi ya kuonyesha mods zilizopakiwa.
	 *
	 * @return Maandishi ya maelezo
	 */
	@Override
	public String modsCargados() {
		return "Mods zilizopakiwa";
	}

	/**
	 * Hupata maandishi ya kundi la madarasa.
	 *
	 * @return Maandishi ya kundi
	 */
	@Override
	public String clases() {
		return "Madarasa";
	}

	/**
	 * Hupata maandishi ya kundi la mbinu.
	 *
	 * @return Maandishi ya kundi
	 */
	@Override
	public String metodos() {
		return "Mbinu";
	}

	/**
	 * Hupata maandishi ya kundi la sehemu.
	 *
	 * @return Maandishi ya kundi
	 */
	@Override
	public String campos() {
		return "Sehemu";
	}

	/**
	 * Hupata maandishi ya kundi la marejeleo.
	 *
	 * @return Maandishi ya kundi
	 */
	@Override
	public String referencias() {
		return "Marejeleo";
	}

	/**
	 * Hupata maandishi ya matokeo ya utafutaji.
	 *
	 * @return Maandishi ya matokeo
	 */
	@Override
	public String resultadosBusqueda() {
		return "Matokeo ya utafutaji";
	}

	/**
	 * Hupata maandishi ya chaguo la kutafuta marejeleo.
	 *
	 * @return Maandishi ya chaguo
	 */
	@Override
	public String buscarReferencias() {
		return "Tafuta marejeleo";
	}

	/**
	 * Hupata maandishi ya marejeleo ya mod.
	 *
	 * @return Maandishi ya maelezo
	 */
	@Override
	public String referenciasMod() {
		return "Marejeleo ya Mod";
	}

	/**
	 * Hupata maandishi ya marejeleo ya darasa.
	 *
	 * @return Maandishi ya maelezo
	 */
	@Override
	public String referenciasClase() {
		return "Marejeleo ya Darasa";
	}

	/**
	 * Hupata maandishi ya marejeleo ya mbinu.
	 *
	 * @return Maandishi ya maelezo
	 */
	@Override
	public String referenciasMetodo() {
		return "Marejeleo ya Mbinu";
	}

	/**
	 * Hupata maandishi ya marejeleo ya sehemu.
	 *
	 * @return Maandishi ya maelezo
	 */
	@Override
	public String referenciasCampo() {
		return "Marejeleo ya Sehemu";
	}

	/**
	 * Hupata maandishi wakati hakuna marejeleo yaliyopatikana.
	 *
	 * @return Ujumbe
	 */
	@Override
	public String noSeEncontraronReferencias() {
		return "Hakuna marejeleo yaliyopatikana";
	}

	/**
	 * Hupata maandishi ya maelezo ya mod.
	 *
	 * @return Kichwa cha maelezo
	 */
	@Override
	public String detalleMod() {
		return "Maelezo ya Mod:";
	}

	/**
	 * Hupata maandishi ya eneo.
	 *
	 * @return Lebo ya eneo
	 */
	@Override
	public String ubicacion() {
		return "Mahali";
	}

	/**
	 * Hupata maandishi ya majina.
	 *
	 * @return Lebo ya majina
	 */
	@Override
	public String nombres() {
		return "Majina";
	}

	/**
	 * Hupata maandishi ya moduli.
	 *
	 * @return Lebo ya moduli
	 */
	@Override
	public String modulo() {
		return "Moduli";
	}

	/**
	 * Hupata maandishi kwa ajili ya maelezo ya darasa.
	 * 
	 * @return Kichwa cha maelezo
	 */
	@Override
	public String detalleClase() {
		return "Maelezo ya Darasa:";
	}

	/**
	 * Hupata maandishi kwa ajili ya maelezo ya njia (method).
	 * 
	 * @return Kichwa cha maelezo
	 */
	@Override
	public String detalleMetodo() {
		return "Maelezo ya Njia:";
	}

	/**
	 * Hupata maandishi kwa ajili ya maelezo ya uga (field).
	 * 
	 * @return Kichwa cha maelezo
	 */
	@Override
	public String detalleCampo() {
		return "Maelezo ya Uga:";
	}

	/**
	 * Hupata maandishi kwa ajili ya darasa.
	 * 
	 * @return Lebo ya darasa
	 */
	@Override
	public String clase() {
		return "Darasa";
	}

	/**
	 * Hupata maandishi kwa ajili ya aina (type).
	 * 
	 * @return Lebo ya aina
	 */
	@Override
	public String tipo() {
		return "Aina";
	}

	/**
	 * Hupata maandishi kwa ajili ya marejeleo ya njia.
	 * 
	 * @return Lebo ya marejeleo
	 */
	@Override
	public String referenciasAMetodos() {
		return "Marejeleo ya Njia:";
	}

	/**
	 * Hupata maandishi kwa ajili ya marejeleo ya uga.
	 * 
	 * @return Lebo ya marejeleo
	 */
	@Override
	public String referenciasACampos() {
		return "Marejeleo ya Uga:";
	}

	/**
	 * Hupata maandishi kwa ajili ya kitufe cha mti wa mod.
	 * 
	 * @return Maandishi ya kitufe
	 */
	@Override
	public String arbolDeMods() {
		return "Mti wa Mod";
	}

	/**
	 * Hupata maandishi kwa ajili ya njia.
	 * 
	 * @return Neno "Njia"
	 */
	@Override
	public String metodo() {
		return "Njia";
	}

	/**
	 * Hupata maandishi kwa ajili ya uga.
	 * 
	 * @return Neno "Uga"
	 */
	@Override
	public String campo() {
		return "Uga";
	}

	@Override
	public String descompilar() {
		// TODO Auto-generated method stub
		return "Tengua Msimo (Decompile)";
	}

	@Override
	public String exportar() {
		// TODO Auto-generated method stub
		return "Safirisha Nje (Export)";
	}

	@Override
	public String importar() {
		// TODO Auto-generated method stub
		return "Ingiza (Import)";
	}

	@Override
	public String errorImportar() {
		// TODO Auto-generated method stub
		return "Hitilafu ya Kuingiza";
	}

	@Override
	public String estructuraImportada() {
		// TODO Auto-generated method stub
		return "Muundo Umeingizwa";
	}

	@Override
	public String estructuraExportada() {
		// TODO Auto-generated method stub
		return "Muundo Umesafirishwa";
	}

	@Override
	public String errorExportar() {
		// TODO Auto-generated method stub
		return "Hitilafu ya Kusafirisha";
	}

	@Override
	public String exportando() {
		// TODO Auto-generated method stub
		return "Inasafirisha...";
	}

	@Override
	public String exportacionTardara() {
		// TODO Auto-generated method stub
		return "Usafirishaji Utachukua Muda";
	}

	@Override
	public String porFavorEspere() {
		// TODO Auto-generated method stub
		return "Tafadhali Subiri";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Huna faili za binary za VLC. Unahitaji faili za binary za VLC kwa ajili ya WaterMedia. Unahitaji kusakinisha (install) manually kutoka https://www.videolan.org/vlc/.  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "Pakua VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Hitilafu mbaya: Jina la moduli '" + nombreModulo
				+ "' lina herufi zisizo halali. Sehemu '" + parteInvalida
				+ "' si kitambulisho halali cha Java. Hii hutokea wakati mod inapotumia maneno yaliyohifadhiwa ya Java (kama 'true', 'class') au herufi zisyoruhusiwa katika jina lake.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "Herufi Zisizo Halali katika Jina la Mod";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "Jina la mod '" + nombreModulo + "' si halali kwa sababu lina '" + parteInvalida
				+ "', ambayo ni neno lililohifadhiwa la Java au herufi isiyoruhusiwa. "
				+ "Tafuta katika logs kuona ni mod ipi inalingana na jina hili (kawaida ni jina la faili la JAR)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "Fika kwenye folda ya mod naihariri faili <b>mods.toml</b> iliyo ndani ya folda <b>/META-INF/</b>. "
				+ "Badilisha thamani ya <b>modId</b> ili itumie herufi, nambari na underscore tu, bila maneno yaliyohifadhiwa ya Java";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "Mfano wa jina halali: 'truemod_shot_enchantment' badala ya 'true.shot.enchantment'. "
				+ "Kumbuka kuwa majina ya mod hayawezi kuwa na alama za nukta, dashi, au maneno yaliyohifadhiwa ya Java kama 'true', 'false' au 'class'";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Hitilafu mbaya na mod: '" + nombreJar
				+ "'. Inakosa sehemu ya lazima 'mandatory' katika utegemezi wake. Hii hutokea wakati faili la mods.toml halibainishi ikiwa utegemezi ni wa lazima.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "Utegemezi wa Mod Unaokosa Sehemu ya Lazima";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "Mod yenye shida ni: <b>" + nombreJar
				+ "</b>. Faili hili lina hitilafu katika usanidi wake wa utegemezi";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "Fungua faili <b>mods.toml</b> iliyo ndani ya folda <b>/META-INF/</b> ya mod <b>" + nombreJar + "</b>";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "Katika sehemu ya utegemezi (dependencies), hakikisha kila ingizo linajumuisha <b>mandatory=true</b> au <b>mandatory=false</b> (mfano: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Hitilafu mbaya na mod: '" + nombreJar
				+ "'. Usanidi batili wa access transformer. Hii hutokea wakati faili la usanidi lina sintaksia isiyo sahihi au marejeleo ya madarasa/njia ambayo hayapo.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Access Transformer Batili";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "Mod yenye shida ni: <b>" + nombreJar + "</b>. Mod hii ina usanidi batili wa access transformer";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "Fungua faili <b>accessTransformer.cfg</b> ndani ya mod <b>" + nombreJar
				+ "</b> (kawaida iko kwenye folda kuu/root ya faili la JAR)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "Rekebisha sintaksia ya access transformer. Mistari inapaswa kufuata muundo: <b>access class.method</b> (mfano: public net.minecraft.world.entity.Entity.func_200560_a). Futa mistari yenye marejeleo ya madarasa au njia ambazo hazipo katika toleo lako la Minecraft";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Hitilafu mbaya: Kutokubaliana kati ya ID ya mod katika annotation @Mod na faili mods.toml. Mod '"
				+ nombreMod + "' haiwezi kupakiwa kwa sababu IDs hazilingani.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "Kutokubaliana kati ya @Mod na mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "Mod inayotengenezwa '" + nombreMod
				+ "' ina kutokubaliana kati ya ID katika annotation <b>@Mod</b> na thamani katika <b>mods.toml</b>";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "Hakikisha kuwa ID katika darasa lako kuu inalingana na thamani <b>modId</b> katika faili <b>/META-INF/mods.toml</b>. Mfano: <b>@Mod(\"mimod\")</b> inapaswa kulingana na <b>modId=\"mimod\"</b>";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "Ikiwa unatumia Gradle,endesha amri <b>clean</b> baada ya kufanya mabadiliko ili kuhakikisha rasilimali zinasasishwa vizuri. Wakati mwingine faili za zamani hubaki kwenye folda ya build";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "kteja" : "seva";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "seva" : "kteja";

		return "<b style='color:#" + config.obtenerColorError() + "'>Hitilafu mbaya: Inajaribu kupakia darasa '"
				+ nombreClase + "' katika mazingira ya " + plataforma + ", lakini limeundwa kwa ajili ya "
				+ plataformaOpuesta
				+ ". <b>Tumia kazi ya 'Mti wa Mods' kwenye upau wa pembeni kutafuta ni mod ipi inayojaribu kupakia darasa hili</b>. "
				+ "Mods zimejengwa mahususi kwa ajili ya jukwaa moja na hazifanyi kazi katika jukwaa lingine.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "Mod kwenye Jukwaa Lisilo Sahihi";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "Katika kichupo cha <b>Mti wa Mods</b> (upande wa kulia), tafuta marejeleo ya darasa <b>" + nombreClase
				+ "</b> kutambua ni mod ipi inayosababisha tatizo";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "kteja" : "seva";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "seva" : "kteja";

		return "Mod iliyotambuliwa ni mod ya <b>" + plataformaOpuesta
				+ "</b> na haipaswi kutumika katika mazingira yako ya " + plataforma + ".";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "Futa mod yenye shida kutoka kwenye folda yako ya <b>mods</b>. Ikiwa unahitaji utendakazi unaofanana kwa jukwaa hili, "
				+ "tafuta mod mbadala iliyoundwa mahususi kwa ajili ya <b>kteja</b> au <b>seva</b> kulingana na hitaji";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Hitilafu mbaya: Metadata haipo kwa ajili ya modid '").append(modIdFaltante).append("'. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("Mods zifuatazo zinaweza kusababisha tatizo: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", na nyingine...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Hii hutokea wakati mod inategemea mod nyingine ambayo haijasakinishwa au ina faili lisilo sahihi la mods.toml.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "Metadata ya mods.toml Haipo";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("Mods zifuatazo zinategemea '").append(modIdFaltante)
					.append("': <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", na nyingine...");
			paso.append("</b>. Tumia kazi ya <b>Mti wa Mods</b> kuthibitisha ni mod ipi inayosababisha tatizo");
			return paso.toString();
		}
		return "Mod inajaribu kutegemea '" + modIdFaltante
				+ "', lakini mod hii haijasakinishwa. Tumia kazi ya <b>Mti wa Mods</b> kutambua ni mod ipi inayosababisha tatizo";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Una chaguo mbili:<br/>" + "1. <b>Sakinisha mod inayokosekana</b>: Tafuta na usakinishe mod yenye ID '"
				+ modIdFaltante + "'<br/>"
				+ "2. <b>Futa mod inayotegemea</b>: Ikiwa huhitaji utendakazi huo, futa mod inayotegemea '"
				+ modIdFaltante + "'";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Ikiwa mod '" + modIdFaltante + "' ni maktaba (kama vile 'forge', 'minecraft', 'curios'), "
				+ "hakikisha umesakinisha toleo sahihi la Minecraft na Forge. "
				+ "Ikiwa ni mod ya kawaida, tafuta kwenye ukurasa wake wa upakuaji mahitaji ya awali yanayohitajika";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Onyo: Hitilafu katika kuanzisha mfumo wa sauti. Sauti na muziki zimezimwa. Hitilafu hii mara nyingi huhusishwa na mod ya SoundPhysicsMod na inaweza kusababishwa na migogoro na maktaba nyingine za sauti.</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "Hitilafu katika Mfumo wa Sauti";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "Hitilafu hii mara nyingi inahusiana na <b>SoundPhysicsMod</b>. Angalia ikiwa umesakinisha toleo la hivi karibuni linaloendana na toleo lako la Minecraft";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "Ikiwa unatumia mods nyingine za sauti (kama vile Sound Filters, Dynamic Surroundings, n.k.), jaribu kufuta SoundPhysicsMod kwa muda kuona kama itatatua mgogoro";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "Angalia folda ya <b>logs</b> kupata ujumbe wa ziada unaohusiana na LWJGL au OpenAL, ambao unaweza kuonyesha matatizo na maktaba za msingi za sauti";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Hitilafu mbaya: Darasa '").append(nombreClase)
				.append("' limesajiliwa kama msikilizaji (listener) wa matukio lakini halina njia (methods) halali. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Darasa hili linapatikana katika mods zifuatazo: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", na nyingine...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Hii hutokea wakati darasa linaposajiliwa kusikiliza matukio bila kuwa na njia zilizowekewa annotation @SubscribeEvent.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "Darasa Lililosajiliwa Bila Wasikilizaji wa Matukio";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("Darasa lenye shida linapatikana katika mods hizi: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", na nyingine...");
			paso.append("</b>. Mods hizi zinajaribu kusajili matukio bila njia halali");
			return paso.toString();
		}
		return "Darasa <b>" + nombreClase
				+ "</b> lilisajiliwa kusikiliza matukio lakini halina njia zenye annotation <b>@SubscribeEvent</b>. Tumia kazi ya <b>Mti wa Mods</b> kutambua ni mod ipi inayopakia darasa hili";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "Katika msimbo wa chanzo (source code), hakikisha kuwa darasa <b>" + nombreClase
				+ "</b> lina angalau njia moja yenye: "
				+ "<b>@SubscribeEvent public void jinaLaNjia(TukioMaalum tukio) { ... }</b>. "
				+ "Ikiwa ni darasa la ndani (inner class), hakikisha halijawekwa alama kama static";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("Kwa mods zilizotambuliwa (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", n.k.");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("wasiliana na mtengenezaji wa mod hii ili arekebishe tatizo. ");
			} else {
				paso.append("wasiliana na watengenezaji wa mods hizi ili warekebishe tatizo. ");
			}
		}

		paso.append(
				"Ikiwa wewe ndiye mtengenezaji, futa usajili wa darasa hili kwenye EventBus au ongeza njia halali za @SubscribeEvent");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		String mensaje = "<b style='color:#" + config.obtenerColorError()
				+ "'>Hitilafu mbaya: Hitilafu katika UnionFileSystem wakati wa kuchakata '" + nombreArchivo + "'. ";

		mensaje += "Hitilafu hii ni ya kawaida sana katika modpacks zilizosanidiwa mapema na inahusiana moja kwa moja na matatizo ya lanzador (launcher). ";

		mensaje += "Mfumo hauwezi kusoma faili za mod vizuri kwa sababu zimeharibika au hazikukamilika.</b>";

		return mensaje;
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "Hitilafu ya UnionFileSystem - Faili Limeharibika";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		String paso = "Hitilafu maalum <b>cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException</b> imegunduliwa na faili <b>"
				+ nombreArchivo + "</b>.";

		paso += " Hii ni hitilafu inayojulikana katika lanzadors za modpacks wakati faili hazipakuliwi kikamilifu.";

		return paso;
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "Sakinisha tena modpack kwa ukamilifu. Hitilafu hii hutokea hasa wakati lanzador haikamilishi upakuaji wa faili zote. "
				+ "Ikiwa unatumia <b>Luna Pixel</b>, tunapendekeza sana utumie <b>ATLauncher</b> badala yake, "
				+ "kwani lanzador hushughulikia vyema faili za mods na kuepuka hitilafu hii maalum.";

	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "Ikiwa tatizo litaendelea baada ya kusakinisha tena: <br/>"
				+ "1. <b>Badilisha kwenda kwa lanzador mwingine</b> <br/>"
				+ "2. Ikiwa unatumia <b>Luna Pixel</b>, <b>tumia ATLauncher</b> ambayo ni ya kuaminika zaidi kwa kuepuka hitilafu hii maalum<br/>"
				+ "3. Angalia muunganisho wako wa intaneti na nafasi ya diski kabla ya kusakinisha tena modpack";

	}

	/**
	 * Hupata ujumbe wa uthibitisho kwa ajili ya kuwasha proxy ya
	 * System.out/System.err
	 * 
	 * @return Ujumbe wa maelezo wenye onyo na mahitaji
	 */
	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "Je, unataka kuwasha ProxySysOutSysErr?\n\n" + "Chaguo hili linampa " + Statics.nombre_cd.obtener()
				+ " ufikiaji wa System.out na System.err wakati lanzador (launcher) haitoi rekodi (logs).\n\n"
				+ "Inapaswa kuwashwa tu wakati huwezi kubandika rekodi (log) manually.\n\n"
				+ "Onyo: Hii inaweza kuingilia kazi na baadhi ya mods au lanzadors.\n\n"
				+ "Inahitajika kuanzisha upya mchezo/app ili mabadiliko yatumike.";
	}

	/**
	 * Hupata kichwa kwa ajili ya majadiliano ya uthibitisho
	 * 
	 * @return Kichwa katika Kiswahili kwa madirisha ya uthibitisho
	 */
	@Override
	public String confirmacionTitulo() {
		return "Uthibitisho";
	}

	/**
	 * Hupata ujumbe wa mafanikio baada ya kuwasha proxy
	 * 
	 * @return Ujumbe wa habari kuhusu hali ya proxy
	 */
	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr imewashwa kikamilifu.\n\n" + "Inahitajika kuanzisha upya "
				+ Statics.nombre_cd.obtener() + " ili mabadiliko yatumike.";
	}

	/**
	 * Hupata kichwa kwa ajili ya majadiliano ya habari
	 * 
	 * @return Kichwa katika Kiswahili kwa madirisha ya habari
	 */
	@Override
	public String informacionTitulo() {
		return "Habari";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("Hitilafu mbaya: AzureLib na GeckoLib zilianzishwa mapema sana! ");
		} else if (azureLibError) {
			mensaje.append("Hitilafu mbaya: AzureLib ilianzishwa mapema sana! ");
		} else if (geckoLibError) {
			mensaje.append("Hitilafu mbaya: GeckoLib ilianzishwa mapema sana! ");
		}

		mensaje.append(
				"Hitilafu hii hutokea wakati unapojaribu kutumia mods za Fabric na matoleo yasiyo ya Fabric ya maktaba hizi. ");

		if (connectorPresente) {
			mensaje.append(
					"Mod ya utangamano (Sinytra Connector au specialcompatibilityoperation) imegunduliwa, jambo linaonyesha unajaribu kutumia mods za Fabric katika mazingira ya Forge au FeatureCreep. ");
			mensaje.append(
					"Angalia hitilafu 'Error de inicialización de FabricMC' katika logs kutambua ni mod ipi maalum inayosababisha tatizo. ");
		}

		mensaje.append(
				"AzureLib na GeckoLib ni maktaba muhimu kwa ajili ya mods za uhuishaji (animation), lakini zinapaswa kulingana na jukwaa sahihi (Fabric au Forge). ");
		mensaje.append("Mchezo hauwezi kupakia vizuri mods za uhuishaji kutokana na mgogoro huu wa uanzishaji.");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "Maktaba Iliyoanzishwa Mapema Sana";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "Angalia hitilafu 'Error de inicialización de FabricMC' kutambua mod yenye shida";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "Hakikisha unatumia toleo sahihi la AzureLib/GeckoLib kwa ajili ya jukwaa lako (Forge au Fabric)";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Hitilafu mbaya: Kutokuendana kati ya C2ME na mods za muunganisho. "
				+ "Hitilafu hii hutokea kwa sababu C2ME inajaribu kufikia vipengele vya ndani vya Java ambavyo vimezuiliwa katika mazingira yenye "
				+ "Sinytra Connector au specialcompatibilityoperation au mods nyingine za utangamano wa Fabric/Forge. "
				+ "<b>C2ME haiendani na mazingira haya, lakini <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> ni mbadala unaopendekezwa</a> ambao hufanya kazi vizuri "
				+ "na mods za muunganisho. Mchezo hauwezi kuanza kutokana na mgogoro huu wa ruhusa za usalama wa Java.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "Kutokuendana kwa C2ME na Mods za Muunganisho";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "Futa C2ME kutoka kwenye folda yako ya mods";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "Pakua na usakinishe <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> badala yake (inayoendana na Sinytra Connector)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "Hakikisha mods zote za muunganisho (kama vile Sinytra Connector) zimesasishwa hadi toleo la hivi karibuni";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Hitilafu mbaya: Kushindwa kupakia plugin ya JEI kwa mod '" + modId + "'. Darasa '" + nombreClase
				+ "' (plugin ID: '" + pluginId
				+ "') lilisababisha hitilafu ambayo inasababisha mchezo kuganda (crash) wakati wa kuanzishwa. "
				+ "Tatizo hili hutokea wakati mod ina muunganisho wa JEI usioendana au wenye kasoro unaoingilia uanzishwaji wa mchezo.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "Plugin ya JEI Imeshindwa - Inasababisha Crash";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "Mod <b>" + modId
				+ "</b> ina plugin ya JEI yenye kasoro inayosababisha crash. Tumia kazi ya <b>Mti wa Mods</b> kuthibitisha ni mod ipi inayosababisha tatizo";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "Futa kwa muda mod <b>" + modId + "</b> kutoka kwenye folda yako ya mods kuona kama itatatua crash";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "Tafuta visasisho vya mod <b>" + modId
				+ "</b> au wasiliana na mtengenezaji wake ukiripoti tatizo la plugin ya JEI. "
				+ "Wakati huo huo, mod inapaswa kufutwa ili uweze kuanzisha mchezo";
	}

	/**
	 * Hupata kichwa cha programu
	 * 
	 * @return Kichwa cha dirisha kuu
	 */
	@Override
	public String tituloLectador() {
		return "Kisomaji cha Konsoli - Crash Detector";
	}

	/**
	 * Hupata kichwa kwa ajili ya ufunguo wa rangi
	 * 
	 * @return Kichwa cha sehemu ya ufunguo
	 */
	@Override
	public String obtenerTituloLeyenda() {
		return "UFUNGUO WA RANGI";
	}

	/**
	 * Hupata maandishi ya kutambua makosa katika ufunguo
	 * 
	 * @return Maandishi ya maelezo kwa ajili ya makosa
	 */
	@Override
	public String obtenerErrorEnLeyenda() {
		return "Makosa makubwa";
	}

	/**
	 * Hupata maandishi ya kutambua traza za stack katika ufunguo
	 * 
	 * @return Maandishi ya maelezo kwa ajili ya traza za stack
	 */
	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "Traza za Stack";
	}

	/**
	 * Hupata kichwa kwa ajili ya madirisha ya makosa
	 * 
	 * @return Kichwa sanifu kwa ajili ya ujumbe wa makosa
	 */
	@Override
	public String obtenerTituloError() {
		return "Hitilafu";
	}

	/**
	 * Hupata ujumbe wa makosa wakati wa kuchakata mistari ya log
	 * 
	 * @return Ujumbe maalum wa hitilafu
	 */
	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "Hitilafu ilitokea wakati wa kuchakata mstari uliochaguliwa";
	}

	/**
	 * Hupata kichwa kwa ajili ya eneo la jina la hitilafu
	 * 
	 * @return Kichwa cha paneli ya jina la hitilafu
	 */
	@Override
	public String obtenerNombreError() {
		return "JINA LA HITILAFU";
	}

	/**
	 * Hupata kichwa kwa ajili ya eneo la maelezo ya hitilafu
	 * 
	 * @return Kichwa cha paneli ya maelezo ya hitilafu
	 */
	@Override
	public String obtenerDescripcionError() {
		return "MAELEZO YA KINA";
	}

	/**
	 * Hupata kichwa kwa ajili ya kiteuzi cha konsoli
	 * 
	 * @return Kichwa cha kisanduku cha mchanganyiko (combobox) cha uchaguzi
	 */
	@Override
	public String obtenerSeleccionarConsola() {
		return "CHAGUA KONSOLI";
	}

	/**
	 * Hupata jina la chaguomsingi kwa ajili ya makosa
	 * 
	 * @return Jina la jumla kwa ajili ya makosa
	 */
	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "Hitilafu haijatambuliwa";
	}

	/**
	 * Hupata maelezo ya chaguomsingi kwa ajili ya makosa
	 * 
	 * @return Maelezo ya jumla kwa ajili ya makosa
	 */
	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "Hitilafu kubwa imegunduliwa katika rekodi (logs). "
				+ "Angalia traza ya stack kutambua chanzo cha tatizo.";
	}

	/**
	 * Hupata ujumbe wa makosa ya kusoma faili
	 * 
	 * @return Ujumbe maalum kwa ajili ya kushindwa kusoma
	 */
	@Override
	public String obtenerErrorLecturaArchivo() {
		return "Faili la rekodi (log) halikuweza kusomwa. " + "Hakikisha faili lipo na lina ruhusa za kusoma.";
	}

	/**
	 * Hupata lebo kwa ajili ya kitufe kwenye upau wa pembeni
	 * 
	 * @return Maandishi yatayoonekana kwenye kitufe cha pembeni
	 */
	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "Kichambuzi cha Logs";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Hitilafu mbaya: Kushindwa kusajili wasubskriba (subscribers) wa kiotomatiki kwa mod '")
				.append(modId).append("'. ");

		mensaje.append("Darasa lenye shida: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Darasa hili linapatikana katika: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", na nyingine...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Hitilafu hii hutokea wakati mod inapojaribu kusajili darasa kama msubskriba wa kiotomatiki lakini darasa halipakiwi vizuri. ");
		mensaje.append(
				"<b>Angalia makosa mengine katika log, kwani tatizo halisi linaweza kuwa sehemu nyingine ya rekodi</b>.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "Kushindwa Kusajili Wasubskriba wa Kiotomatiki";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "Mod <b>" + modId + "</b> inajaribu kusajili darasa <b>" + nombreClase
				+ "</b> kama msubskriba wa kiotomatiki, lakini imeshindwa. Hakikisha darasa hili lipo na linaweza kufikiwa";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder(
					"Darasa lenye shida <b>" + nombreClase + "</b> linapatikana katika faili hizi: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", na nyingine...");
			paso.append("</b>. ");
			paso.append(
					"Tumia kazi ya <b>Mti wa Mods</b> kuthibitisha ni faili lipi maalum linalopakia darasa lenye shida");
			return paso.toString();
		}
		return "Darasa <b>" + nombreClase + "</b> halipatikani katika faili yoyote ya mod. Hakikisha mod <b>" + modId
				+ "</b> imesakinishwa vizuri. Tumia kazi ya <b>Mti wa Mods</b> kusaidia kutambua tatizo";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "Sasisha mod <b>" + modId
				+ "</b> hadi toleo la hivi karibuni linaloendana na toleo lako la Minecraft na Forge. "
				+ "Ikiwa tatizo litaendelea, wasiliana na mtengenezaji wa mod ukiripoti hitilafu hiyo pamoja na darasa lenye shida";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "Angalia <b>makosa mengine katika log</b> yaliyotokea kabla ya ujumbe huu, kwani tatizo halisi linaweza kuwa sehemu nyingine ya rekodi. "
				+ "Wakati mwingine hitilafu ya awali huzuia kupakiwa vizuri kwa madarasa yanayohitajika kwa ajili ya usajili wa wasubskriba";
	}

	@Override
	public String limpiado() {
		// TODO Auto-generated method stub
		return "Imesafishwa";
	}

	@Override
	public String original() {
		// TODO Auto-generated method stub
		return "Asili";
	}

	@Override
	public String verEnConsola() {
		// TODO Auto-generated method stub
		return "Tazama kwenye Konsoli";
	}

	@Override
	public String advertencia() {
		// TODO Auto-generated method stub
		return "Onyo";
	}

	@Override
	public String fatal() {
		// TODO Auto-generated method stub
		return "Hatari";
	}

	@Override
	public String noRegistroDeBattly() {
		// TODO Auto-generated method stub
		return "BattlyLauncher haina rekodi (log) wala konsoli ya kunakili. Unaweza kutumia ProxySysOutSysErr kukamata STDOUT na STDERR na kuanzisha upya mchezo, lakini ProxySysOutSysErr inaweza kugongana na mods zinazorekebisha STDOUT au STDERR";
	}

	@Override
	public String noRegistroDeNightWorld() {
		// TODO Auto-generated method stub
		return "Unahitaji kuwasha hali ya utatuzi (debug mode) katika usanidi wa NightWorld ili kupata rekodi ya lanzador. Ni muhimu sana hasa kwa sababu ina STDOUT na STDERR";
	}

	@Override
	public String noRegistroDeMCServidor() {
		// TODO Auto-generated method stub
		return "Unahitaji kuhifadhi au kubandika yaliyomo kwenye Terminal ya seva yako kwa sababu ina taarifa zisizopatikana katika rekodi nyingine, ikiwa ni pamoja na STDOUT, STDERR, na makosa mengine. Tafadhali bandika yaliyomo katika kipindi cha mwisho. Kwa siku zijazo, unaweza kuhifadhi yaliyomo kwenye terminal kwenda kwenye faili cd_launcherlog. Ili kuepuka haja ya kubandika, ongeza >> cd_launcherlog baada ya amri, kama inavyoonekana kwenye picha. Kumbuka kuwa hii itazuia taarifa kuonekana kwenye terminal; zitaonekana tu kwenye faili hilo baada ya kufanya hivyo.";
	}

//Métodos para Idioma relacionados con la verificación LexForgeMLTransformerEnNeoForge

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("Hitilafu mbaya: Transforma ya LexForge imegunduliwa katika mazingira ya NeoForge. ");
		sb.append("</b>");

		sb.append("Darasa husika: <b>").append(claseReceptora).append("</b>. ");
		sb.append("Interfeisi iliyoharibiwa ni <b>").append(interfazObjetivo).append("</b> ");
		sb.append("na njia <b>").append(firmaMetodoFaltante).append("</b> haipo. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Darasa limepatikana katika: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", na nyingine...");
			sb.append("</b>. ");
		} else {
			sb.append(
					"Hakuna JARs zilizopatikana zenye darasa hilo; linaweza kuwa limewekwa ndani (shaded) au likijumuishwa kama jar-in-jar. ");
		}

		sb.append(
				"Kashfa hii hutokea wakati transforma/huduma ya ModLauncher iliyokompiliwa kwa ajili ya MinecraftForge/LexForge ");
		sb.append("inapopakiwa chini ya NeoForge na toleo lisiloendana la API ya ModLauncher. ");
		sb.append("Sasisha au badilisha kipengele hicho kwa ajili ya NeoForge.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "Transforma ya LexForge Imetumika katika NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "Tambua transforma isiyoendana: <b>" + claseReceptora + "</b>. " + "API inayotarajiwa ni <b>"
				+ interfazObjetivo + "</b> na <b>" + firmaMetodoFaltante + "</b> haipo. "
				+ "Angalia kama mod inasajili darasa hili katika <b>META-INF/services</b> na ulifute au ulizime katika NeoForge.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Mahali pa mod(s) husika: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", na nyingine...");
			sb.append("</b>. ");
		} else {
			sb.append(
					"Hakuna JARs zilizopatikana zenye darasa hilo. Angalia jar-in-jar na utegemezi uliowekwa ndani (shaded dependencies). ");
		}
		sb.append("Ondoa JARs hizo kwa muda au tumia matoleo yanayoendana na NeoForge kuthibitisha chanzo.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "Badilisha kipengele hicho na toleo maalum la NeoForge au kikompilie tena dhidi ya "
				+ "toleo la ModLauncher linalotumiwa na NeoForge. Epuka binari za zamani za LexForge/MinecraftForge.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "Safisha folda ya mods na ondoa nakala rudufu za jar-in-jar. Futa kashe (caches) za lanzador ikiwa inahitajika "
				+ "na uanze upya ili kuhakikisha hakuna transforma za LexForge zinazopakiwa.";
	}
//En tu clase de idioma:

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia haiwezi kuanza: Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("haiendani.</b> ");
		sb.append("Ondoa Xenon na tumia Embeddium au Sodium. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Imegunduliwa katika: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", na nyingine...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia Haiendani na Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "Imegunduliwa " + label + " isioendana na WaterMedia. Ondoa kutoka kwenye profaili.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("Mahali: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", na nyingine...");
			sb.append("</b>. Futa JAR hilo.");
			return sb.toString();
		}
		return "Hakuna JARs zilizopatikana. Angalia folda ya mods na futa Xenon.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "Sakinisha Embeddium au Sodium kama mbadala na uanze upya mchezo.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "Hitilafu ya Kukandamiza (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>Deflater imefungwa wakati wa kunakili rasilimali za TACZ.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Inahusiana na: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", na nyingine");
			sb.append("</b>. ");
		}
		sb.append(
				"<br/><b>Suluhisho:</b> katika <code>tacz/tacz-pre.toml</code> weka <code>DefaultPackDebug=true</code>. ")
				.append("Ikiwa inahitajika, tengeneza ramani kwanza na kisha uiwashe.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "Katika tacz/tacz-pre.toml weka DefaultPackDebug=true. Ikiwa inahitajika, tengeneza ramani kwanza na kisha uiwashe.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "Funksheni za Msongamano Hazijaunganishwa";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>Funksheni za msongamano hazipo katika rejista.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("Hazipo: ");
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
				"<br/><b>Suluhisho:</b> sakinisha au washa mod/datapack inayofafanua funksheni hizo na uanze upya. Sababu nyingine ya kawaida ya tatizo hili ni wakati una mod inayohitajika, lakini ina shida au migogoro na mod nyingine; kwa mfano, Terralith ina matatizo mengi na inaweza kusababisha hitilafu hii na nyingine, ikiwa ni pamoja na makosa ya JSON.");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "Sakinisha au washa mod/datapack inayotoa funksheni hizo na uanze upya mchezo.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// Mensaje breve, en color de error, mencionando explícitamente el mod
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Ingizo la rejista halipo: ").append(claveFaltante).append(". ");
		sb.append("Hii ni ya kawaida na toleo la alpha la Steam & Railways kwa Create 6.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (alpha)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "Ondoa au badilisha toleo la alpha la Steam & Railways kwa Create 6 na toleo linaloendana.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// Corto, con color de error y recomendación directa
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Mgogoro wa upakiaji: Multiworld pamoja na Sodium/Embeddium/Rubidium husababisha ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance). ")
				.append("Shauri: ondoa Multiworld au mod ya utendaji (performance), au tumia matoleo yanayoendana.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "Mgogoro: Multiworld na Mods za Utendaji";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Ondoa Multiworld au Sodium/Embeddium/Rubidium, au sasisha hadi matoleo yanayoendana.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Sodium imegundua dereva (driver) wa kadi ya grafiki usioendana. "
				+ "Sasisha dereva wa GPU yako hadi kiwango cha chini kinachohitajika au fuata mwongozo wa Sodium."
				+ "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "Hitilafu ya Muktadha wa OpenGL";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OpenGL imeshindwa: hakuna muktadha (context) wa sasa au kazi haipatikani katika muktadha huu. "
				+ "Pia inaweza kuwa tatizo la madereva ya video." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "Sasisha/weka upya madereva ya GPU na uanze upya; zima vipengele vya juu (overlays) na jaribu bila mods za utendaji (performance).";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "Kiungo kimenakiliwa kwenye ubao wa kubandika (clipboard).";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		// TODO Auto-generated method stub
		return "Tafuta ndani ya faili zilizoshindiliwa (.zip/.jar/.war/.ear/.fpm/.rar za Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Hitilafu ya azimio (resolution) la tekstura: Haiwezi kurekebisha " + recurso + " - ukubwa: " + tamaño
				+ "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "Hitilafu ya Azimio la Tekstura";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "Hitilafu hii hutokea wakati tekstura ni kubwa sana au kuna pakiti nyingi sana za rasilimali. "
				+ "Jaribu kutumia pakiti za rasilimali zenye azimio la chini au ondoa baadhi ya pakiti za rasilimali. "
				+ "Hakikisha hujaongeza tekstura za kawaida zenye azimio kubwa kuliko lililoruhusiwa.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Hitilafu ya huduma za ModLauncher: Njina (path) lina herufi zisizo halali. "
				+ "Huduma za ModLauncher haziwezi kuchakata njina zenye herufi zisizo ASCII au herufi maalum. "
				+ "Herufi zenye shida ni pamoja na: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요, na hasa herufi '\"' ikiwa iko mwishoni mwa jina. "
				+ "Vipengele vya huduma za kawaida katika ModLauncher ni pamoja na " + Statics.nombre_cd.obtener()
				+ ", " + Config.obtenerInstancia().obtenerNombreCD()
				+ ", FeatureCreep, Vivicraft, Optifine, Sodium, nakala (clones), Iris Shaders/Oculus, MixerLogger, CrashAssistant na Sinytra Connector. "
				+ "Unaweza kufuta huduma zote, lakini matatizo mengine yanaweza kutokea kutokana na jina la njia. "
				+ "Suluhisho: Badilisha jina la instansi litumie herufi za ASCII pekee (a-z, A-Z, 0-9), bila nafasi wala herufi maalum.</b>";
	}// TODO incluye un Buscardor para mods con servicios

	@Override
	public String nombre_error_modlauncher_path() {
		return "Hitilafu ya Njia katika ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "Hitilafu hii hutokea wakati njia ya instansi ina herufi zisizo ASCII au herufi maalum. "
				+ "Huduma za ModLauncher haziwezi kushughulikia njina hizi. "
				+ "Suluhisho: Badilisha jina la instansi litumie herufi za ASCII pekee (a-z, A-Z, 0-9) na epuka nafasi na herufi maalum. "
				+ "Angalia hasa herufi '\"' ambayo inaleta shida nyingi, hasa ikiwa iko mwishoni mwa jina.";
	}

	@Override
	public String tituloEditorCodice() {
		return "Kihariri cha Msimbo";
	}

	@Override
	public String nuevo() {
		return "Mpya";
	}

	@Override
	public String actualizarSeleccionado() {
		return "Sasisha Kilichochaguliwa";
	}

	@Override
	public String eliminarSeleccionado() {
		return "Futa Kilichochaguliwa";
	}

	@Override
	public String exportarJSON() {
		return "Safirisha JSON...";
	}

	@Override
	public String guardarTodo() {
		return "Hifadhi Yote";
	}

	@Override
	public String general() {
		return "Jumla";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "Maandishi ya kutafuta";
	}

	@Override
	public String filtro() {
		return "Kichujio (id)";
	}

	@Override
	public String criticalidad() {
		return "Ukali (ONYO/HITILAFU/HATARI)";
	}

	@Override
	public String prioridad() {
		return "Utangulizi";
	}

	@Override
	public String lista() {
		return "Uhakiki";
	}

	@Override
	public String colIdioma() {
		return "Lugha";
	}

	@Override
	public String colNombre() {
		return "Jina";
	}

	@Override
	public String colResultado() {
		return "Matokeo";
	}

	@Override
	public String vistaJson() {
		return "Hakiki ya JSON";
	}

	@Override
	public String idiomas() {
		return "Lugha (zote zinahitajika)";
	}

	@Override
	public String elegirFiltro() {
		return "Chagua...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "Chagua kichujio";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "Vichujio vinavyopatikana";
	}

	@Override
	public String faltanCampos() {
		return "Jaza sehemu zote za jumla zinazohitajika.";
	}

	@Override
	public String critInvalida() {
		return "Ukali si sahihi. Tumia ONYO, HITILAFU au HATARI.";
	}

	@Override
	public String filtroNoExiste() {
		return "Kichujio kilichoonyeshwa hakipo.";
	}

	@Override
	public String faltanIdiomas() {
		return "Jaza jina na matokeo kwa lugha zote:";
	}

	@Override
	public String verificacionInvalida() {
		return "Uhakiki moja si sahihi. Angalia sehemu.";
	}

	@Override
	public String guardadoOk() {
		return "Imehifadhiwa vizuri.";
	}

	@Override
	public String editorCodiceBoton() {
		return "Ongeza sababu";
	}

	@Override
	public String descripcionEditorCodice() {
		// TODO Auto-generated method stub
		return "Unaweza kusajili sababu hapa. Unahitaji ID, maandishi yasiyo na herufi maalum, alama za accenti au nafasi. Kwa vichujio unaweza kutumia \"mstari una\" (line contains) kutafuta maandishi katika mstari, \"yote yana\" (all contain) ikiwa rekodi ina maandishi, \"regex mstari\" ikiwa mstari una regex, na \"regex yote\" inashauriwa kutumia toleo la mstari. Unahitaji kuweka ukali, HATARI, HITILAFU, au ONYO kwa ajili ya rangi. Kwa lugha zote unahitaji kuandika jina na jibu kwa ajili ya skrini. Unaweza kuongeza uhakiki zaidi au kufuta nyingine. Unahifadhi unapokamilisha.";
	}

	@Override
	public String descartarCambios() {
		return "Je, unataka kutupa mabadiliko yasiyohifadhiwa katika uhakiki wa sasa?";
	}

	@Override
	public String confirmacion() {
		return "Uthibitisho";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "Je, unataka kuhifadhi mabadiliko kabla ya kutoka?";
	}

	@Override
	public String salirSinGuardar() {
		return "Toka bila Kuhifadhi";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Hitilafu mbaya: Kushindwa kupakia huduma ya modlauncher (IDependencyLocator).<br>");
		sb.append("🔹 <b>Darasa lenye shida:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>Mod iliyoharibiwa:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append(
					"🔸 <b>Mod haijatambuliwa.</b> Angalia mods za hivi karibuni, za maendeleo au zisizofungashwa vizuri.<br>");
		}

		sb.append("🔸 <b>Sababu:</b> Faili <code>META-INF/services/...</code> la mod limeharibika, ");
		sb.append("halioendani na toleo hili la Forge/NeoForge, au mod ni kwa ajili ya toleo lisilo sahihi.<br>");
		sb.append("🔸 <b>Matokeo:</b> Forge/NeoForge haiwezi kusajili utegemezi wa mod, ");
		sb.append("jambo linalozuia kuanzishwa kwa mchezo.<br>");
		sb.append("🔸 <b>Suluhisho:</b> Sasisha, weka upya au ondoa mod yenye shida. ");
		sb.append(
				"Ikiwa unatumia mods za maendeleo, hakikisha zimekompiliwa kwa ajili ya toleo lako mahususi la Forge/NeoForge.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "Hitilafu ya Usanidi wa Huduma (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. Tambua mod inayosababisha: angalia mods zilizosakinishwa hivi karibuni au za maendeleo.";
		}
		return "1. Mod yenye shida ni: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. Sasisha, weka upya au ondoa mod. Hakikisha unatumia toleo linaloendana na Forge/NeoForge yako.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888"; // Gris neutro para nombres de clase/campo

		return "<b style='color:#" + colorError + "'>Hitilafu mbaya: Uga haupo.</b><br>"
				+ "Mod imejaribu kufikia uga <b style='color:#" + colorCodigo + "'>" + campo + "</b>, "
				+ "ambao haupo katika toleo hili la mchezo au la mod nyingine.<br>" + "<span style='color:#"
				+ colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "Uga Haupo (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. Hitilafu hii mara nyingi hutokea wakati mod haiendani na toleo la sasa la mchezo au la mod nyingine.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. Sasisha mods zote zilizoharibiwa. Ikiwa tatizo litaendelea, wasiliana na mtengenezaji wa mod uliosababisha hitilafu.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888"; // Gris para metodos/clases

		return "<b style='color:#" + colorError + "'>Hitilafu mbaya: Njia haipo.</b><br>"
				+ "Mod imejaribu kuita njia <b style='color:#" + colorCodigo + "'>" + metodo + "</b>, "
				+ "ambayo haipo katika toleo hili la mchezo au la mod nyingine.<br>" + "<span style='color:#"
				+ colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "Njia Haipo (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. Hitilafu hii hutokea wakati mod haiendani na toleo la sasa la mchezo au la mod nyingine.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. Sasisha mods zote zinazohusika. Ikiwa tatizo litaendelea, ripoti hitilafu kwa mtengenezaji wa mod iliyoharibiwa.";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();

		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>Unahitaji msaada?</strong><br>"
				+ "  Ikiwa hujui jinsi ya kurekebisha au sababu haipo hapa, unaweza kupokea msaada kwenye mitandao yetu ya kijamii. "
				+ "  Tumia kitufe <img src='" + iconoCompartir
				+ "' alt='Shiriki' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>Shiriki</strong> kupata viungo vya rekodi (logs) na matokeo kwa ajili ya timu yetu. "
				+ "  Ikiwa wewe ni mtengenezaji wa modpack au shirika, hariri <code>crash_detector/plantilla.htm</code> "
				+ "  ili ubinafsishe viungo vya timu yako." + "</div>";
	}

	@Override
	public String restablecerPlantilla() {
		// TODO Auto-generated method stub
		return "Rejesha Kiolezo";
	}

	@Override
	public String restablecer() {
		// TODO Auto-generated method stub
		return "Rejesha";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		// TODO Auto-generated method stub
		return "Je, unataka kurejesha " + nombreImagen + " hadi thamani za chaguomsingi?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		// TODO Auto-generated method stub
		return "Je, unataka kurejesha kiolezo hadi thamani za chaguomsingi?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Madarasa ya AzureLib hayapo. Ikiwa tayari una AzureLib, tafadhali sakinisha toleo la kabla ya Oktoba 8, 2025. Hii ilikuwa ya kawaida. Ikiwa huna AzureLib, sakinisha toleo la sasa.</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Mod <code>healight</code> inasababisha hitilafu kubwa: <code>java.lang.NoSuchFieldError: INT</code>. "
				+ "Hitilafu hii hutokea kwa sababu mod inajaribu kufikia uga ambao haupo tena katika toleo la MCForge 47.10 Minecraft 1.20+. "
				+ "Mchezo hauwezi kuanza kutokana na tatizo hili.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• Ondoa au sasisha mod <code>healight</code>. "
				+ "Toleo la sasa halioendani na MinecraftForge 47.10 kwa 1.20.1. "
				+ "Tafuta toleo jipya zaidi la mod au fikiria kutumia mbadala.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "Hitilafu kubwa: healight - Uga 'INT' haupatikani";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("Darasa <code>").append(clase)
				.append("</code> halitekelezi njia inayohitajika:<br>").append("<code>").append(metodo)
				.append("</code><br>").append("kutoka kwenye interfeisi <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br><br>Mod au faili linaloshukiwa: <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• Hitilafu hii hutokea wakati mod inatekeleza interfeisi lakini inakosa njia ya lazima. "
				+ "Sasisha <b>mods zote mbili</b> zinazohusika (ile inayofafanua interfeisi na ile inayeitekeleza). "
				+ "Ikiwa hujui ni zipi, tafuta majina yanayoonekana kwenye ujumbe wa hitilafu.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "Njia ya Interfeisi Haijatekelezwa (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Mod inajaribu kupakia darasa la <b>upande wa mteja</b> "
				+ "(<code>AnimationMetadataSection</code>) kwenye <b>seva maalum</b>, jambo ambalo haiwezekani. "
				+ "Hitilafu hii mara nyingi huonekana wakati mod haijatenganisha msimbo wake vizuri kati ya mteja na seva. "
				+ "Uwepo wa <code>ModernFix</code> unaweza kuonyesha tatizo hili, ingawa siyo chanzo cha moja kwa moja.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>Chaguo la haraka:</b> Ondoa kwa muda <code>ModernFix</code> kuthibitisha ikiwa seva itaanza. "
				+ "Ikiwa itafanya kazi, tatizo liko kwenye mod nyingine inayopakia madarasa ya mteja kwenye seva.<br>"
				+ "• <b>Suluhisho la kudumu:</b> Tambua mod yenye hatia (tafuta mods zenye rasilimali zilizohuishwa, tekstura za kawaida au maktaba za grafiki) na uiwashe au uiondoe.<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "Darasa la Mteja Limepakia kwenye Seva (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Faili la mipangilio la mod ya <code>Sinytra Connector</code> limeharibika. "
				+ "Hii mara nyingi hutokea wakati faili linajaa herufi tupu (<code>\\u0000</code>) "
				+ "kutokana na kufungwa ghafla kwa mchezo, hitilafu za uandishi au migogoro ya mods.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• Nenda kwenye folda <code>config/</code> ya instansi yako ya Minecraft.<br>"
				+ "• Tafuta na ufute mipangilio ya mods za connector.<br>"
				+ "• Anzisha upya mchezo: Sinytra Connector itatengeneza faili jipya la mipangilio safi.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "Mipangilio ya Sinytra Connector Imeharibika";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Faili <code>" + nombreJar
				+ "</code> limeharibika au halijakamilika.<br>"
				+ "Mfumo hauwezi kusoma yaliyomo ndani yake kwa sababu kichwa cha mwisho cha faili ZIP hakipo.<br>"
				+ "Hitilafu hii mara nyingi hutokea baada ya upakuaji kukatika au hitilafu ya lanzador.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "Faili JAR Limeharibika (lina jina mahususi)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>Futa faili lililoHaribika</b> na lipakue tena kutoka kwenye chanzo rasmi (CurseForge, MinecraftStorage, n.k.).<br>"
				+ "• Ikiwa unatumia lanzador kama CurseForge, Technic au Luna Pixel, fikiria kubadilisha kwenda <b>ATLauncher</b> au <b>Prism Launcher</b>, "
				+ "ambazo huhakikisha vyema uadilifu wa faili.<br>"
				+ "• Hakikisha muunganisho wako wa intaneti ni thabiti wakati wa upakuaji.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Haiwezekani kupakia dunia kwa sababu moja ya faili zake za NBT imeharibika "
				+ "(kwa mfano: <code>level.dat</code>, <code>playerdata/*.dat</code>, au vipande/chunks).<br>"
				+ "Hitilafu mahususi ni: <code>UTFDataFormatException: malformed input around byte " + byteCorrupto
				+ "</code>.<br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ Kabla ya kujaribu ukarabati wowote, futa nakala rudufu kamili ya folda ya dunia.</b><br><br>"
				+ "Unaweza kujaribu kurekebisha faili lililoHaribika kwa kutumia <b>kihariri cha NBT</b> kama <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>.<br>"
				+ "Ikiwa uharibifu ni mkubwa, tumia <b>kihariri cha heksadesimali</b> (kama HxD) kuchunguza na kurekebisha baiti zisizo halali "
				+ "(tu ikiwa una uzoefu na umbizo la NBT).<br>"
				+ "Kama njia ya mwisho, rejesha kutoka kwenye nakala rudufu au tumia <i>ukarabati wa dunia</i> wa mods kama <code>FTB Backup</code>.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>Futa nakala rudufu kamili ya folda ya dunia</b> kabla ya kujaribu ukarabati wowote.<br>"
				+ "• Tumia kihariri cha NBT (kama NBT Studio) kufungua na kurekebisha faili lililoHaribika.<br>"
				+ "• Ikiwa itashindwa, chunguza faili hilo na kihariri cha heksadesimali katika nafasi ya baiti iliyoharibika.<br>"
				+ "• Ikiwa huna uzoefu, rejesha kutoka kwenye nakala rudufu ya hivi karibuni.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "Dunia Imeharibika: hitilafu wakati wa kupakia data za NBT";
	}

	@Override
	public String problema_con_openAL() {
		// TODO Auto-generated method stub
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Una tatizo na OpenAL. Wakati mwingine madereva ya Nouveau yanaweza kusababisha hili, lakini wakati mwingine toleo la OpenAL lililojumuishwa katika programu halioendani na toleo lililo kwenye usambazaji wako (distro) na unahitaji kutumia toleo linalotolewa na distro yako, hii ni ya kawaida hasa na usambazaji wa Red Hat na na mods za sauti kama Sound Physics Remastered. Angalia mwongozo huu kwa msaada zaidi: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>Suluhisho la matatizo ya sauti ya Minecraft kwenye Linux</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Seva haiwezi kuanza kwa sababu faili la dunia limefungwa na mchakato mwingine.<br>"
				+ "Hii mara nyingi hutokea ikiwa:<br>" + "• Tayari kuna instansi ya seva inayoendesha.<br>"
				+ "• Antivirus au kivinjari cha faili kimefungua folda ya dunia.<br>"
				+ "• Mchakato uliopita haukufungwa vizuri na kuacha faili zimefungwa.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>Funga instansi zote za seva</b> (ikiwa ni pamoja na michakato ya nyuma kama javaw.exe).<br>"
				+ "• Ikiwa unatumia paneli ya hosting (kama Multicraft), anzisha upya seva kabisa kutoka kwenye paneli.<br>"
				+ "• <b>Zima antivirus kwa muda</b> ikiwa unashuku inazuia faili.<br>"
				+ "• Kwenye mifumo ya ndani, fungua dirisha lolote la Windows Explorer linaloonyesha folda ya dunia.<br>"
				+ "• Ikiwa tatizo litaendelea, futa kwa mikono faili <code>session.lock</code> ndani ya folda ya dunia (tu ikiwa una uhakika hakuna seva nyingine inayoendesha).";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "Faili la Dunia Limefungwa na Mchakato Mwingine";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Mod imejaribu kurithi darasa <code>"
				+ clasePadreFinal + "</code>, " + "lakini darasa hili sasa ni <b>final</b> na halirithiki.<br>"
				+ "Darasa lenye shida ni: <code>" + claseHija + "</code>.<br><br>"
				+ "Hii mara nyingi hutokea wakati mod imekompiliwa kwa ajili ya toleo la zamani la Minecraft au la mod nyingine msingi, "
				+ "ambayo imeweka alama ya madarasa kama <code>final</code> katika matoleo ya hivi karibuni.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>Sasisha mods zote zinazohusika</b>, hasa zile zinazoweza kuhusiana na mod msingi uliotajwa.<br>"
				+ "• Ikiwa tatizo litaendelea, tafuta toleo la mod linaloendana na toleo lako la sasa la Minecraft na utegemezi wake.<br>"
				+ "• Katika baadhi ya kesi, kufuta kwa muda mod iliyo na darasa la mtoto kunaweza kusaidia kuthibitisha chanzo.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "Jaribio la Kurithi Darasa la Final";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Unatumia <b>Rubidium</b> (fork ya zamani ya Sodium kwa Forge) pamoja na <b>Iris au Oculus</b>.<br>"
				+ "Katika matoleo ya hivi karibuni ya Minecraft (1.19.2+), "
				+ "Rubidium haikufuatilia Sodium na utegemezi wake umekuwa na matatizo.<br><br>"
				+ "Hitilafu hii pia inaweza kutokea ikiwa kuna mgogoro kati ya mods za utendaji (Sodium, Rubidium, Embeddium, Bedium, Xeonium, n.k.) au Iris Shaders na mod nyingine.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>Ondoa Rubidium</b> kutoka kwenye folda yako ya <code>mods</code>.<br>"
				+ "• <b>Sakinisha <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a></b>, "
				+ "fork hai na inayoendana ya Sodium kwa Forge ambayo inasupport Iris/Oculus katika 1.20+.<br>"
				+ "• Hakikisha huna zaidi ya fork moja ya Sodium iliyosakinishwa wakati mmoja (kwa mfano: Rubidium + Embeddium).<br>"
				+ "• Ikiwa unatumia Oculus badala ya Iris, hakikisha pia inaendana na toleo lako la Forge na Embeddium.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium ya Zamani na Iris/Oculus (OptionInstance ni final)";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Mod <code>Simple Voice Chat</code> haiwezi kuanzisha seva yake ya sauti kwa sababu "
				+ "bandari (port) ya UDP tayari inatumika au anwani ya IP iliyosanidiwa si sahihi.<br>"
				+ "Hii haizuii mchezo kuanza, lakini inazima utendakazi wa mazungumzo ya sauti.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• <b>Funga instansi nyingine zozote za Minecraft</b> au programu nyingine zinazotumia bandari ya UDP 24454.<br>"
				+ "• Ikiwa uko kwenye seva, hakikisha kuwa <b>hakuna huduma nyingine</b> inayotumia bandari hiyo.<br>"
				+ "• Katika mipangilio ya mod (<code>config/voicechat/</code>), badilisha bandari ya UDP kwenda nyingine iliyo wazi (kwa mfano, 24455).<br>"
				+ "• Ikiwa unatumia anwani ya IP maalum, hakikisha ni sahihi au uiache tupu ili kutumia chaguomsingi.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Mazungumzo ya Sauti: Bandari ya UDP Imeshikiliwa au IP Si Sahihi";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "BlockItem <code>" + nombreBlockItem
				+ "</code> ina block null (tupu).<br>"
				+ "Hitilafu hii mara nyingi hutokea katika <b>addons za Create</b> (kama vile <code>dndecor</code>, <code>createdeco</code>) "
				+ "wakati kuna migogoro na <code>Amendments</code>, <code>Moonshine</code>, au uanzishaji usio sahihi wa blocks.<br>"
				+ "<b>Angalia:</b> Hii si hitilafu ya Amendments moja kwa moja, bali ni dalili ya tatizo la kina zaidi katika upakiaji wa rejista.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• <b>Sasisha mods zote zinazohusiana:</b> Create, Amendments, Moonshine, na addons zozote (hasa <code>dndecor</code> na <code>createdeco</code>).<br>"
				+ "• Ikiwa tatizo litaendelea, <b>ondoa kwa muda addons za Create</b> moja baada ya nyingine kutambua yenye hatia.<br>"
				+ "• Hakikisha kuwa <b>Amendments na Moonshine zinaendana</b> na toleo lako la Create na Forge.<br>"
				+ "• Angalia ikiwa kuna matoleo ya beta au forks zilizosasishwa za addons zenye shida.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "BlockItem Null katika Addon ya Create";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>").append(
				"Mods zimepatikana ambazo hazihusiani na jukwaa lolote linalotumika (Forge, Fabric, n.k.):<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>Hii mara nyingi hutokea wakati:<br>")
				.append("• Mods za <b>Fabric na Forge</b> zimechanganywa katika folda moja.<br>")
				.append("• Mod imesakinishwa kwa ajili ya toleo lisiloendana la Minecraft.<br>")
				.append("• Mod imeharibika au si faili halali la JAR.</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>Hakikisha mods zote ni kwa ajili ya jukwaa moja</b> (Forge <b>au</b> Fabric, si vyote viwili).<br>"
				+ "• Tumia <b>mti wa mods</b> kutambua ni jukwaa gani linagundua kila faili.<br>"
				+ "• Futa mod yoyote usiyoitambua au ambayo ni ya jukwaa tofauti.<br>"
				+ "• Ikiwa unatumia lanzador kama CurseForge au Prism, hakikisha profaili imesanidiwa vizuri.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "Mod Haiendani na Kipakiaji Kinachotumika";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Haiwezekani kuunda modeli <code>" + modid
				+ ":" + nombreModelo + "</code>.<br>" + "Hii inaonyesha kuwa mod <code>" + modid
				+ "</code> ina rasilimali zilizoharibika, zisizopo "
				+ "au zisizoendana na toleo lako la Minecraft.</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>Sasisha mod</b> hadi toleo la hivi karibuni linaloendana na instansi yako.<br>"
				+ "• Ikiwa unatumia toleo la maendeleo au lililobinafsishwa, rudia kwenye toleo rasmi.<br>"
				+ "• Hakikisha faili la JAR halijaharibika (liweke upya).<br>"
				+ "• Ikiwa tatizo litaendelea, ripoti hitilafu kwa mtengenezaji wa mod ukijumuisha logi hii.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "Kushindwa Kuunda Modeli ya Rasilimali";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Mgogoro mkubwa umegunduliwa kati ya mods <code>Moonlight</code> na <code>Iceberg</code>.<br>"
				+ "Zote mbili zinajaribu kusajili mifumo ya kupakia upya rasilimali kwa njia isayoendana, "
				+ "jambo linalosababisha hitilafu ya OpenGL kutokana na kutokuwepo kwa muktadha sahihi wa grafiki.<br>"
				+ "Tatizo hili ni la kawaida wakati wa kutumia matoleo ya Forge yanayojumuisha viunganishi vya mods za Fabric.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>Sasisha mods zote mbili</b> hadi matoleo yao ya hivi karibuni yanayoendana na toleo lako la Forge.<br>"
				+ "• Ikiwa tatizo litaendelea, <b>ondoa kwa muda Iceberg</b>, kwani Moonlight mara nyingi ni utegemezi muhimu zaidi kwa mods nyingine.<br>"
				+ "• Hakikisha huna matoleo yaliyorudiwa au yaliyochanganywa ya Forge/Fabric ya mods hizi.<br>"
				+ "• Angalia ikiwa mod nyingine yoyote (kama Supplementaries, Citadel, n.k.) tayari inajumuisha utendakazi wa Iceberg ndani yake.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "Mgogoro Mkubwa: Moonlight dhidi ya Iceberg (OpenGL bila Muktadha)";
	}

	@Override
	public String instantanea() {
		// TODO Auto-generated method stub
		return "Bandiko (Snapshot)";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		// TODO Auto-generated method stub
		return "Tangu Bandiko la Mwisho";
	}

	@Override
	public String seleccionarUnArchivo() {
		// TODO Auto-generated method stub
		return "Chagua Faili";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		// TODO Auto-generated method stub
		return "Bandiko limeundwa vizuri";
	}

	@Override
	public String errorCreandoInstantanea() {
		// TODO Auto-generated method stub
		return "Hitilafu wakati wa kuunda bandiko";
	}

	@Override
	public String consejo() {
		// TODO Auto-generated method stub
		return "Shauri";
	}

	@Override
	public String resultadoMuestra() {
		// TODO Auto-generated method stub
		return "Matokeo ya Sampuli";
	}

	@Override
	public String historaDeModsDesc() {
		// TODO Auto-generated method stub
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>Shauri:</b> Chagua faili mbili za historia kulinganisha orodha ya mods. "
				+ "  Matokeo yanaonyesha <span style='color:%s;'>zilizoongezwa (+)</span> na "
				+ "  <span style='color:%s;'>zilizoondolewa (&#8722;)</span> kulingana na majina yaliyorekebishwa. "
				+ "  Tumia kitufe 'Bandiko' kuunda nakala ya faili iliyopo yenye kiendelezi .instantanea." + "</body>"
				+ "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		// TODO Auto-generated method stub
		return "Pata Viungo vya Logi kama Markdown bila Ripoti";
	}

	@Override
	public String titulo_configuracion() {
		return "Mipangilio";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "Hitilafu isiyotarajiwa wakati wa kushiriki.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "Hitilafu isiyotarajiwa wakati wa kutengeneza viungo.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "Hitilafu isiyotarajiwa wakati wa kuchakata kitufe.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "Hakuna faili lililohusishwa la kufungua.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "Faili halipo:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "Haikuweza kufunguliwa katika kihariri.\nNjina itanakiliwa kwenye ubao wa kubandika (clipboard).";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "Haikuweza kufungua faili; njina imenakiliwa kwenye ubao wa kubandika.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "Desktropi haiungiwi mkono; njina imenakiliwa kwenye ubao wa kubandika.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "Unakumbana na kikomo cha ombi. Jaribu kutumia tovuti nyingine ya logi au API nyingine ya logi.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		// TODO Auto-generated method stub
		return "Shiriki Kiungo";
	}

	@Override
	public String infoDeTrazos() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Kurekebisha sehemu za juu za traza (stack traces) ni kipaumbele cha kwanza. "
				+ "Muundo ni Ngazi, Mstari. " + "Logi zote zina mfumo wa nambari. " + Verificaciones.nl_html
				+ "Kwa kawaida unahitaji kutafuta katika ngazi za chini katika logi zote; traza zenye ngazi za juu mara nyingi ni chanya uwongo (false positives). "
				+ "Ni muhimu kutumia uwezo wako wa kuangalia konsoli, kwani uchambuzi wa traza haukamili wakati kuna traza nyingi."
				+ "</b>";
	}

	// --- Buscador de Canario de Orden (Warrant Canary) ---

	/**
	 * Texto del botón para el buscador de canario de orden. Ejemplo: "Buscador de
	 * canario de orden"
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "Kitafutaji cha Canary ya Agizo";
	}

	/**
	 * Mensaje mostrado en el cuadro de diálogo informando que la función estará
	 * disponible próximamente.
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "Kipengele hiki kitapatikana hivi karibuni.";
	}

	/**
	 * Título del cuadro de diálogo que informa sobre la disponibilidad futura del
	 * buscador de canario de orden.
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "Hivi Karibuni";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		// TODO Auto-generated method stub
		return "Mods Zisizoendana na Crash Assistant (Uongo)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		// TODO Auto-generated method stub
		return "Mod Isiyoendana na Pakiti ya Mods (Modpack) inayotumia CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant ina orodha ya mods ambazo inasema hazioendani, lakini hatuna ushahidi wa kuwa hivyo na hitilafu iko kwa Kiingereza pekee. Ikiwa unataka kucheza na mods hizi, unaweza kuhariri faili <code>config/crash_assistant/config.toml</code> na kubadilisha <code>enabled = true</code> katika sehemu [compatibility] kuwa <code>enabled = false</code>.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant ina uwezo wa kuweka alama kwenye mods kama zisizoendana, lakini wakati mwingine hii si sahihi na ujumbe wa hitilafu upatikana kwa Kiingereza pekee. Ikiwa unataka kucheza na mods hizi, unaweza kuhariri faili <code>config/crash_assistant/problematic_mods_config.json</code> na kubadilisha <code>should_crash_on_startup</code> kutoka <code>true</code> kwenda <code>false</code>.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Hitilafu: Mod '" + modId
				+ "' inahitaji mod '" + dependencia + "'. Kwa sasa, " + actual + "." + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Hitilafu: Mod '" + modId
				+ "' inahitaji toleo '" + requerido + "' au la juu zaidi la '" + dependencia
				+ "', lakini mod haijasakinishwa." + "</span>";
	}

	// En la clase MonitorDePID.idioma (añadir este método)
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Hitilafu: Mod '" + modId
				+ "' haiendani na toleo la sasa la '" + dependencia + "'. "
				+ "Unapaswa kufuta mod 'Iris/Oculus & GeckoLib Compat' kwani haiendani na Superb Warfare na haifanyi kazi na toleo la hivi karibuni la GeckoLib. "
				+ "Toleo la sasa: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "Hitilafu: Haikuweza kutekeleza kazi kwa ajili ya darasa '" + clase + "'. "
				+ "Hitilafu hii ni ya kawaida na mods ambazo hazioendani au zina migogoro na mods nyingine zilizosakinishwa.";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "Kushindwa Kutekeleza Kazi";
	}

	public String recomendacion_fallos_ejecucion() {
		return "Aina hii ya hitilafu mara nyingi hutokea wakati kuna kutokuendana kati ya mods. "
				+ "Ni ya kawaida hasa na mods ambazo hazifanyi kazi vizuri na ConnectorMod.";
	}

	public String info_clase_problematica() {
		return "Darasa lenye shida:";
	}

	public String no_se_encontraron_clases_problema() {
		return "Hakuna madarasa mahususi yaliyopatikana yenye matatizo ya utekelezaji.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mgogoro mkubwa umegunduliwa kati ya OptiFine na Entity Model Features (EMF). "
				+ "Mods hizi hazioendani na husababisha hitilafu ya injection inayozuia kuanzishwa kwa mchezo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "Mgogoro OptiFine na Entity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "Ondoa OptiFine au Entity Model Features, kwani hazioendani.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mgogoro mkubwa umegunduliwa kati ya OptiFine na Fusion. "
				+ "Mods hizi hazioendani na husababisha hitilafu ya injection inayozuia kuanzishwa kwa mchezo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "Mgogoro OptiFine na Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "Ondoa OptiFine au Fusion, kwani hazioendani.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel (inayohitajika na Create) inahitaji Sodium 0.6.0-beta.2 au ya juu zaidi. Rubidium ni 0.5.3. "
				+ "Fikiria kutumia <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> kama mbadala."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "Mgogoro Flywheel na Toleo la Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "Sasisha Sodium hadi 0.6.0-beta.2 au ya juu zaidi, au sakinisha <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> kama mbadala unaoendana.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mgogoro mkubwa umegunduliwa kati ya OptiFine na Epic Fight. "
				+ "Mods hizi hazioendani na husababisha hitilafu ya injection inayozuia kuanzishwa kwa mchezo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "Mgogoro OptiFine na Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "Ondoa OptiFine au Epic Fight, kwani hazioendani.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mgogoro mkubwa umegunduliwa kati ya OptiFine na Rubidium. "
				+ "Mods hizi hazioendani na husababisha hitilafu ya injection inayozuia kuanzishwa kwa mchezo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "Mgogoro OptiFine na Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "Ondoa OptiFine au Rubidium, kwani hazioendani.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam inajaribu kupakia kwenye seva maalum, lakini inaendana na mteja pekee. "
				+ "Ondoa FreeCam kutoka kwenye seva au hakikisha imesakinishwa kwenye mteja pekee." + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam kwenye Seva Maalum";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "Ondoa FreeCam kutoka kwenye seva maalum, kwani inapaswa kusakinishwa kwenye mteja pekee.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) inajaribu kupakia kwenye seva maalum, lakini inaendana na mteja pekee. "
				+ "Ondoa ETF kutoka kwenye seva au hakikisha imesakinishwa kwenye mteja pekee." + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features kwenye Seva Maalum";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "Ondoa Entity Texture Features kutoka kwenye seva maalum, kwani inapaswa kusakinishwa kwenye mteja pekee.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Unapaswa kukubali EULA ya Minecraft ili kuendesha seva. "
				+ "Hariri faili eula.txt na badilisha 'eula=false' kuwa 'eula=true'." + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "EULA ya Minecraft Haijakubaliwa";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "Hariri faili eula.txt katika folda ya seva na badilisha 'eula=false' kuwa 'eula=true'.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine inajaribu kupakia kwenye seva maalum, lakini inaendana na mteja pekee. "
				+ "Ondoa OptiFine kutoka kwenye seva au hakikisha imesakinishwa kwenye mteja pekee." + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine kwenye Seva Maalum";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "Ondoa OptiFine kutoka kwenye seva maalum, kwani inapaswa kusakinishwa kwenye mteja pekee. Tatizo hili mara nyingi husababishwa pia nausakinishaji wa toleo la Optifine lisiloendana na toleo la Minecraft au mgogoro na mod nyingine na Optifine.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks imeandikwa vibaya kwa 1.20.1 lakini inatumia njia za 1.21.1. "
				+ "Mod inajaribu kutumia ResourceLocation.fromNamespaceAndPath, ambayo haipo katika 1.20.1." + "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "Hitilafu ya Toleo katika Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "Hakikisha unatumia toleo sahihi la Iron's Spellbooks linaloendana na toleo lako la Minecraft.";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mgogoro mkubwa umegunduliwa kati ya OptiFine na Embeddium. "
				+ "Mods hizi hazioendani na husababisha hitilafu ya injection inayozuia kuanzishwa kwa mchezo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "Mgogoro OptiFine na Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "Ondoa OptiFine au Embeddium, kwani hazioendani.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Ni ya kawaida na mods zenye migogoro ya uzalishaji wa dunia, hasa Terralith, AmplifiedNether, Nullscape na Incendium, na mods nyingine za uzalishaji wa dunia. Pia inawezekana unahitaji kusakinisha mod inayokosekana.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable inajaribu kupakia kwenye seva maalum, lakini inaendana na mteja pekee. "
				+ "Ondoa Controllable kutoka kwenye seva au hakikisha imesakinishwa kwenye mteja pekee." + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable kwenye Seva Maalum";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "Ondoa Controllable kutoka kwenye seva maalum, kwani inapaswa kusakinishwa kwenye mteja pekee.";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries inasababisha hitilafu inayozuia upakiaji wa seva. "
				+ "Mod ina matatizo na usajili wa tabia za moto ambazo husababisha hitilafu wakati wa upakiaji wa datapacks."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries Inazuia Upakiaji wa Seva";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "Jaribu kusasisha Supplementaries hadi toleo la hivi karibuni au izime kwa muda kuruhusu upakiaji wa seva.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) imegundua tatizo na vipengele vya Jackson visivyopo. "
				+ "Baadhi ya mods kama Valkyrien Skies zinaweza kusababisha hitilafu hii kwa kutujumuisha utegemezi wote unaohitajika."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "Kipengele cha Jackson Hakipo katika Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "Ondoa Groovy Modloader na mods zinazohusiana kama Valkyrien Skies ambazo zinaweza kusababisha migogoro ya utegemezi.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Every Compat imegundua jina batili la block ya mbao. "
				+ "Every Compat kwa kawaida ina matatizo mengi. Usitumie!" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "Jina Batili katika Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "Angalia pakiti za rasilimali au mods zinazotumia Every Compat, kwani zinaweza kuwa na majina ya blocks yasiyo sahihi.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Msimbo wa hitilafu (-1073741819) umegunduliwa ambao unaweza kusababishwa na overlays kama GameCaster ya Razer, Discord, OBS Studio au matatizo na madereva ya NVIDIA."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "Msimbo wa Hitilafu -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "Jaribu kuzima overlays kama GameCaster, Discord au OBS Studio, na hakikisha madereva yako ya NVIDIA yamesasishwa.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Kidokezos inahitaji Immersive Messages kama utegemezi lakini haijasakinishwa. "
				+ "Sakinisha Immersive Messages ili Immersive Kidokezos ifanye kazi vizuri." + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Kidokezos Bila Utegemezi";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "Sakinisha Immersive Messages, kwani ni utegemezi unaohitajika kwa ajili ya Immersive Kidokezos.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins ina tatizo la utangamano na Apoli Mod ambapo ItemStack haiwezi kubadilishwa (cast) kuwa EntityLinkedItemStack. "
				+ "Hii ni ya kawaida katika matoleo ya juu kuliko 6.6.0. Fikiria kutumia toleo la zamani au kubadilisha kati ya matoleo ya Fabric na Forge."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "Hitilafu ya Kubadilisha Aina (Cast) katika Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "Tumia toleo la Medieval Origins 6.6.0 au la zamani, au jaribu kubadilisha kati ya matoleo ya Fabric na Forge ya mod.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether inasababisha hitilafu na Kitu cha Rejista (Registry Object) kisichopo katika MusicManager. "
				+ "Tatizo hili linahusiana na mixin ya MusicManager kutoka Reign of Nether." + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "Hitilafu ya MusicManager katika Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "Jaribu kusasisha Reign of Nether au uiondoe kwa muda kutatua hitilafu.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel inasupport seva ya YSM kwenye Linux au Android pekee. "
				+ "Tatizo hili limerekebishwa katika matoleo mapya zaidi tangu Novemba 23, 2025 kwenye Modrinth."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel Haiendani na Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "Sasisha YesSteveModel hadi toleo la hivi karibuni kutoka Modrinth, kwani tatizo limerekebishwa baada ya Novemba 23.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mgogoro mkubwa umegunduliwa kati ya Moving Elevators na OptiFine. "
				+ "Mods hizi hazioendani na husababisha hitilafu ya injection inayozuia kuanzishwa kwa mchezo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "Mgogoro Moving Elevators na OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "Ondoa OptiFine au Moving Elevators, kwani hazioendani.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mgogoro mkubwa umegunduliwa kati ya Fabric API (fabric-resource-loader-v0) na OptiFine. "
				+ "Mods hizi hazioendani na husababisha hitilafu ya injection inayozuia kuanzishwa kwa mchezo."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "Mgogoro Fabric API na OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "Ondoa OptiFine au sasisha Fabric API hadi toleo linaloendana.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mod ina ITransformationService yenye kasoro ambayo haiwezi kuundwa (instanciated): " + claseProveedor
				+ ". " + "Mod hii inapaswa kuondolewa ili kuruhusu upakiaji wa mchezo." + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "ITransformationService Yenye Kasoro";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "Ondoa mod iliyo na darasa " + claseProveedor + ", kwani ina ITransformationService yenye kasoro.";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError() + "'>Mod ina specifikesheni ya toleo isiyo sahihi. "
				+ "Toleo linapaswa kuwa ndani ya mabano ya mraba. "
				+ "Unaweza kutumia zana ya grep/greprf kwenye upau wa pembeni kutafuta toleo </span>" + version
				+ "<span style='color:#" + config.obtenerColorError() + "'> kutambua ni mod ipi ina tatizo.</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "Toleo Lisilo Sahihi katika Mod";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "Tumia zana ya grep/greprf kwenye upau wa pembeni kutafuta toleo lenye shida na utafute mod iliyo nalo.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Hitilafu ya stack smashing imegunduliwa ambayo imemaliza mchakato. "
				+ "Hii inaweza kusababishwa na matatizo na Early Window katika Forge/NeoForge/PillowMC au na LWJGL 3.2.2 na matoleo mapya zaidi."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "Stack Smashing Imegunduliwa";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "Angalia mipangilio ya Early Window na fikiria kutumia toleo tofauti la LWJGL au kuangalia mods zinazohusiana na dirisha la awali.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore ni kwa ajili ya modpack mahususi pekee na haipaswi kutumika katika usakinishaji wa jumla, kwani husababisha tatizo."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore na Toleo Lisiloendana la Java";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "Ondoa GregTechEasyCore, kwani ni kwa ajili ya modpack mahususi pekee na haiendani na usakinishaji wako wa jumla.";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mgogoro umegunduliwa kati ya MoniLabs na Connector Extras unaohusiana marekebisho ya KubeJS. "
				+ "Mods hizi hazioendani katika marekebisho yao ya KubeJS." + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "Mgogoro MoniLabs na Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "Jaribu kuondoa moja ya mods (MoniLabs au Connector Extras) kwani zina migogoro na marekebisho yao ya KubeJS.";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris inahitaji Distant Horizons [2.0.4] au DH API toleo [1.1.0] au la juu zaidi. "
				+ "Angalia mwongozo wa utangamano kwenye https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e kutatua tatizo."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Utangamano Iris na Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "Angalia mwongozo wa utangamano kwenye https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e na usasishe Iris na Distant Horizons hadi matoleo yanayoendana.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Una madarasa yasiyopo ya Minecraft. Sababu zinazowezekana:</b>" + "<ul>"
				+ "<li>Una mods za matoleo mengine ya mchezo. Unaweza kutumia <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> kuangalia ikiwa darasa lipo katika toleo lako.</li>"
				+ "<li>Una usakinishaji mbaya wa Minecraft (ya kawaida na CurseForge App, ModrinthApp/Theseus/Astralrinth na lanzadors nyingine za modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Angalia tutorial</a> kutatua matatizo na CurseForge.</li>"
				+ "<li>Una coremod yenye kasoro (ikiwa coremod inashindwa, inaweza kuzuia upakiaji wa darasa).</li>"
				+ "</ul>"
				+ "<p>Angalia: Unaweza kutumia zana <b>grepr/fgrepr</b> kwenye upau wa pembeni kupata mods zinazorejelea madarasa yasiyopo, mradi utumie '/' katika majina.</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Una madarasa yasiyopo ya DangerZone. Sababu zinazowezekana:</b>" + "<ul>"
				+ "<li>Una mods za matoleo mengine ya mchezo.</li>" + "<li>Una coremods zenye kasoro.</li>"
				+ "<li>Una lanzador au usakinishaji mbaya.</li>" + "</ul>"
				+ "<p>Angalia: Unaweza kutumia zana <b>grepr/fgrepr</b> kwenye upau wa pembeni kupata mods zinazorejelea madarasa yasiyopo, mradi utumie '/' katika majina.</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Una madarasa yasiyopo ya FeatureCreep. Sababu zinazowezekana:</b>" + "<ul>"
				+ "<li>Una mods za matoleo mengine ya FeatureCreep (mfano: ESR vs Nightly au v4 vs v12).</li>"
				+ "<li>Unaweza kusakinisha FeatureCreep kutoka CurseForge au MinecraftStorage.</li>" + "</ul>"
				+ "<p>Angalia: Unaweza kutumia zana <b>grepr/fgrepr</b> kwenye upau wa pembeni kupata mods zinazorejelea madarasa yasiyopo, mradi utumie '/' katika majina.</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Una madarasa yasiyopo ya ModLauncher. Sababu zinazowezekana:</b>" + "<ul>"
				+ "<li>Mods zako ni kwa ajili ya build tofauti ya MinecraftForge, PillowMC au NeoForge (ModLauncher hutumiwa na vipakiaji hivi).</li>"
				+ "<li>Kuna visasisho vingi vya modloaders kwa toleo moja la Minecraft.</li>"
				+ "<li>Una usakinishaji mbaya wa lanzador yako (ya kawaida na CurseForge App, ModrinthApp/Theseus/Astralrinth na lanzadors nyingine za modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Angalia tutorial</a> kutatua matatizo na CurseForge.</li>"
				+ "</ul>"
				+ "<p>Angalia: Unaweza kutumia zana <b>grepr/fgrepr</b> kwenye upau wa pembeni kupata mods zinazorejelea madarasa yasiyopo, mradi utumie '/' katika majina.</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Una madarasa yasiyopo ya Minecraft Forge. Sababu zinazowezekana:</b>" + "<ul>"
				+ "<li>Mods zako ni kwa ajili ya build tofauti ya MinecraftForge.</li>"
				+ "<li>Kuna visasisho vingi vya modloaders kwa toleo moja la Minecraft.</li>"
				+ "<li>Una usakinishaji mbaya (ya kawaida na CurseForge App, ModrinthApp/Theseus/Astralrinth na lanzadors nyingine za modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Angalia tutorial</a> kutatua matatizo na CurseForge.</li>"
				+ "</ul>"
				+ "<p>Angalia: Unaweza kutumia zana <b>grepr/fgrepr</b> kwenye upau wa pembeni kupata mods zinazorejelea madarasa yasiyopo, mradi utumie '/' katika majina.</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Una madarasa yasiyopo ya NeoForge. Sababu zinazowezekana:</b>" + "<ul>"
				+ "<li>Mods zako ni kwa ajili ya build tofauti ya NeoForge.</li>"
				+ "<li>Kuna visasisho vingi vya modloaders kwa toleo moja la Minecraft.</li>"
				+ "<li>Una usakinishaji mbaya (ya kawaida na CurseForge App, ModrinthApp/Theseus/Astralrinth na lanzadors nyingine za modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Angalia tutorial</a> kutatua matatizo na CurseForge.</li>"
				+ "</ul>"
				+ "<p>Angalia: Unaweza kutumia zana <b>grepr/fgrepr</b> kwenye upau wa pembeni kupata mods zinazorejelea madarasa yasiyopo, mradi utumie '/' katika majina.</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Una madarasa yasiyopo ya Fabric Loader. Sababu zinazowezekana:</b>" + "<ul>"
				+ "<li>Mods zako ni kwa ajili ya build tofauti ya Fabric Loader.</li>"
				+ "<li>Kuna visasisho vingi vya modloaders kwa toleo moja la Minecraft.</li>"
				+ "<li>Una usakinishaji mbaya (ya kawaida na CurseForge App, ModrinthApp/Theseus/Astralrinth na lanzadors nyingine za modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Angalia tutorial</a> kutatua matatizo na CurseForge.</li>"
				+ "<li>Mods nyingi zinahitaji Fabric API. Tafadhali sakinisha Fabric API ikiwa inahitajika.</li>"
				+ "</ul>"
				+ "<p>Angalia: Unaweza kutumia zana <b>grepr/fgrepr</b> kwenye upau wa pembeni kupata mods zinazorejelea madarasa yasiyopo, mradi utumie '/' katika majina.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Una madarasa yasiyopo ya PillowMC. Sababu zinazowezekana:</b>" + "<ul>"
				+ "<li>Mods zako ni kwa ajili ya build tofauti ya PillowMC.</li>"
				+ "<li>Kuna visasisho vingi vya modloaders kwa toleo moja la Minecraft.</li>"
				+ "<li>Una usakinishaji mbaya (ya kawaida na CurseForge App, ModrinthApp/Theseus/Astralrinth na lanzadors nyingine za modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Angalia tutorial</a> kutatua matatizo na CurseForge.</li>"
				+ "</ul>"
				+ "<p>Angalia: Unaweza kutumia zana <b>grepr/fgrepr</b> kwenye upau wa pembeni kupata mods zinazorejelea madarasa yasiyopo, mradi utumie '/' katika majina.</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Una mod ambayo kwa makusudi husababisha uchache (lag). Uranium ni mod ya uchache. Si mara zote husababisha kuganda, lakini hatimaye inaweza kufanya hivyo."
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack imeandikwa kuwa inaoendana na 1.19.* lakini ni kwa ajili ya 1.20.*, jambo linalosababisha hitilafu ya darasa lisilopatikana. "
				+ "Mod inajaribu kutumia DamageSources ambazo hazipo katika toleo la sasa la Minecraft." + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Hitilafu ya Toleo katika Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "Hakikisha unatumia toleo sahihi la Falling Attack linaloendana na toleo lako la Minecraft.";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>")
				.append("Unahitaji kusakinisha CFR (Class File Reader) ili kutumia kipengele hiki.<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append(
					"Kwenye mifumo ya Linux, NetBSD au FreeBSD, unaweza kusakinisha CFR kutoka kwenye meneja wa pakiti zako.<br>")
					.append("Tafuta pakiti hapa: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append("Badala yake, unaweza kupakua toleo lililorekebishwa linalotumiwa na FabricMC kutoka:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("Lihifadhi katika folda ifuatayo:<br>").append("<b>")
				.append(new java.io.File(com.asbestosstar.crashdetector.Statics.carpeta_mundial_como_archivo, "cfr/")
						.getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>Muhimu:</b> baada ya kusakinisha CFR, unapaswa kuanzisha upya mod ili itambue vizuri.")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "Hakuna picha inayopatikana";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "Haikuweza kupata darasa: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "Kitengeuzi CFR – Sakura Riddle (Si Rasmi)";
	}

	@Override
	public String cfrClaseActual() {
		return "Darasa la Sasa";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "Picha ya Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "Hitilafu wakati wa kupakia picha";
	}

	public String noticiaLegalCFR() {
		return "Programu hii ya kiolesura cha grafiki (GUI) ya kutengua msimo (decompile) wa mods imeundwa kusaidia watumiaji kutambua sababu za kuganda kwa programu. "
				+ "Hata hivyo, kutengua msimo wa mods kunaweza kuhitajika, na watumiaji wanapaswa kuwa makini wasitumie msimo uliotengenezwa kuvunja Sheria ya Shirikisho ya Haki za Wachapishaji. "
				+ "Inapendekezwa kukagua leseni ya mod husika kabla ya kutumia msimo wowote uliopatikana. Zaidi ya hayo, mara nyingi mods hutoa msimo wao wa chanzo rasmi, "
				+ "ambao kwa kawaida ni safi na rahisi kuelewa kuliko msimo uliotenguliwa. Kumbuka kuwa heshima kwa mali ya kiakili na leseni za matumizi ni msingi wa "
				+ "jamii ya watengenezaji wa mods. Unaweza kutazama Sheria ya Shirikisho ya Haki za Wachapishaji kwenye kiungo kifuatacho: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Sheria ya Shirikisho ya Haki za Wachapishaji (Kiswahili)</a> "
				+ "na toleo la Kiingereza hapa: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Sheria ya Haki za Wachapishaji (Kiingereza)</a>. "
				+ "Kwa kuwa tuko kwenye CurseForge, pia tunatoa kiungo cha Sheria ya Haki za Wachapishaji ya Marekani kwa Kiingereza: "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">Sheria ya Haki za Wachapishaji ya Marekani</a>. "
				+ "Zaidi ya hayo, inapendekezwa watumiaji washauriane na kujulisha kuhusu sheria zinazotumika katika eneo lao. "
				+ "GUI yetu ni kwa ajili ya ukaguzi rahisi tu; kwa uchambuzi wa hali ya juu zaidi, wanapaswa kutumia Fork Enigma ya FabricMC inayopatikana kwenye "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>. Ikiwa wanataka kuhariri faili za JAR kutengeneza patchi wakati hakuna msimo wa chanzo unapatikana, wanaweza kutumia Recaf kwenye "
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">tovuti yake</a>.";
	}

	@Override
	public String botonDescargarCfr() {
		return "Pakua CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "Fungua Folda ya Usakinishaji";
	}

	@Override
	public String colorFondoPrincipal() {
		return "Rangi ya Mandhari Makuu";
	}

	@Override
	public String colorTextoBotonReset() {
		return "Rangi ya maandishi ya kitufe cha kuweka upya";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "Rangi ya maandishi ya uga wa kutafuta";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "Rangi ya maandishi ya menyu kunjuzi ya kichujio";
	}

	@Override
	public String colorTextoRenderer() {
		return "Rangi ya maandishi ya kirenderi";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "Rangi ya maandishi ya overlay ya upakiaji";
	}

	@Override
	public String colorBorde() {
		return "Rangi ya mpaka";
	}

	@Override
	public String colorFondoRetrato() {
		return "Rangi ya mandhari katika hali ya picha";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "Rangi ya kiungo cha kushiriki";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "Rangi ya mandhari ya uga wa kushiriki";
	}

	@Override
	public String rosaFondo() {
		return "Waridi wa mandhari";
	}

	@Override
	public String rosaSuave() {
		return "Waridi laini";
	}

	@Override
	public String moradoAcento() {
		return "Zambarau ya alama";
	}

	@Override
	public String textoOscuro() {
		return "Maandishi meusi";
	}

	@Override
	public String bordeSuave() {
		return "Mpaka laini";
	}

	@Override
	public String fondoCampo() {
		return "Mandhari ya uga";
	}

	@Override
	public String fondoVistaPrevia() {
		return "Mandhari ya hakiki";
	}

	@Override
	public String sintaxisConstructor() {
		return "Rangi ya sintaksia: kitenzi cha kujenga (constructor)";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "Rangi ya sintaksia: ujumbe wa msaada";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "Rangi ya sintaksia: lebo za HTML";
	}

	@Override
	public String colorFondoVentana() {
		return "Rangi ya mandhari ya dirisha";
	}

	@Override
	public String colorPanel() {
		return "Rangi ya paneli";
	}

	@Override
	public String colorBotonTexto() {
		return "Rangi ya maandishi ya kitufe";
	}

	@Override
	public String colorCampo() {
		return "Rangi ya uga";
	}

	@Override
	public String colorBordeDestacado() {
		return "Rangi ya mpaka ulioangaziwa";
	}

	@Override
	public String colorSeleccionTexto() {
		return "Rangi ya mandhari ya uteuzi wa maandishi";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "Rangi ya maandishi yaliyochaguliwa";
	}

	@Override
	public String colorEstadoExito() {
		return "Rangi ya hali: mafanikio";
	}

	@Override
	public String colorEstadoFallo() {
		return "Rangi ya hali: kushindwa";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "Rangi ya hali: bandiko (snapshot)";
	}

	@Override
	public String colorResultadoAnadido() {
		return "Rangi ya matokeo yaliyoongezwa";
	}

	@Override
	public String colorResultadoEliminado() {
		return "Rangi ya matokeo yaliyoondolewa";
	}

	@Override
	public String colorBordeScroll() {
		return "Rangi ya mpaka wa kusogeza (scroll)";
	}

	@Override
	public String colorFondoPanel() {
		return "Rangi ya mandhari ya paneli";
	}

	@Override
	public String colorBeigeListas() {
		return "Beiji ya orodha";
	}

	@Override
	public String colorTextoListas() {
		return "Rangi ya maandishi katika orodha";
	}

	@Override
	public String colorBordeListas() {
		return "Rangi ya mpaka wa orodha";
	}

	@Override
	public String colorBotonFondo() {
		return "Rangi ya mandhari ya kitufe";
	}

	@Override
	public String colorBordeBoton() {
		return "Rangi ya mpaka wa kitufe";
	}

	@Override
	public String colorDoradoTexto() {
		return "Rangi ya dhahabu ya maandishi";
	}

	@Override
	public String colorPila() {
		return "Rangi ya traza ya mfumo (stack trace)";
	}

	@Override
	public String colorTextoPanel() {
		return "Rangi ya maandishi ya paneli";
	}

	@Override
	public String colorTextoNegro() {
		return "Rangi nyeusi ya maandishi";
	}

	@Override
	public String colorTextoPrincipal() {
		return "Rangi kuu ya maandishi";
	}

	@Override
	public String colorFondoResultados() {
		return "Rangi ya mandhari ya matokeo";
	}

	@Override
	public String colorEstado() {
		return "Rangi ya hali";
	}

	@Override
	public String colorTextoDescripcion() {
		return "Rangi ya maandishi ya maelezo";
	}

	@Override
	public String colorTextoEstado() {
		return "Rangi ya maandishi ya hali";
	}

	@Override
	public String colorTextoExtra() {
		return "Rangi ya maandishi ya ziada";
	}

	@Override
	public String colorSeparador() {
		return "Rangi ya kitenganishi";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "Hitilafu asilia <code>StubRoutines::SafeFetch32</code> imegunduliwa. "
				+ "Tatizo hili hutokea kwenye macOS na JDK 17.0.9 na linarekebishwa katika JDK 17.0.10 au matoleo ya baadaye. https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "Hitilafu Asilia SafeFetch32 katika JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "Sasisha JDK yako hadi toleo 17.0.10 au la juu zaidi (kwa mfano, 17.0.15).";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "Ikiwa unatumia lanzador kama MultiMC, Prism Launcher au TLauncher, isanidishe kutumia JDK mpya zaidi. "
				+ "Baadhi tayari zinajumuisha JDK 17.0.15.";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "Mod ya Spark pia inaweza kuchangia hitilafu hii. Fikiria kuizima kwa muda. https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mod ya MCEF (Chromium Embedded Framework) inasababisha kuganda kimya.</b>" + "<ul>"
				+ "<li>MCEF inaanza kufanya kazi mwishoni mwa logi, ambayo kwa kawaida inamaanisha mchezo umeganda wakati wa upakiaji.</li>"
				+ "<li>Mod hii inajulikana kusababisha kuganda kwenye mifumo ya Linux, macOS au na matoleo fulani ya Java.</li>"
				+ "<li>Sio mara zote hitilafu wazi huonekana, lakini mchezo hauwahi kufika kwenye menyu kuu.</li>"
				+ "</ul>"
				+ "<p>Ikiwa huhitaji utendakazi wa kivinjari ndani ya mchezo (kama ramani za wavuti au kurasa zilizojumuishwa), ondoa mod.</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "Tatizo la Uanzishaji wa MCEF (mod ya kivinjari kilichojumuishwa)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "Futa faili la mod ya MCEF (tafuta 'mcef' katika jina la faili) kutoka kwenye folda ya 'mods'.";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "Ikiwa kweli unaihitaji, hakikisha unatumia toleo linaloendana na mfumo wako wa uendeshaji na toleo la Minecraft.";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mgogoro umegunduliwa kati ya <b>OptiFine</b> na <b>Iris/Oculus</b>.</b>" + "<ul>"
				+ "<li>OptiFine hubadilisha urenderi wa Minecraft kwa njia isiyoendana na Iris au Oculus.</li>"
				+ "<li>Hitilafu <code>MixinLevelRenderer failed injection check</code> inatoka kwenye <code>mixins.iris.json</code> au <code>mixins.oculus.json</code>.</li>"
				+ "</ul>"
				+ "<p>Mods hizi haziwezi kutumika pamoja. Ondoa OptiFine ili kutumia shaders na Iris au Oculus.</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "Mgogoro kati ya OptiFine na Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "Futa faili la OptiFine kutoka kwenye folda ya 'mods'.";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "Tumia Iris au Oculus bila OptiFine kwa ajili ya shaders za kisasa.";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mgogoro umegunduliwa kati ya <b>ModernFix</b> na <b>OptiFine</b>.</b>" + "<ul>"
				+ "<li>ModernFix haiendani na OptiFine kwa sababu huvunja utendakazi wa Forge na kupunguza kasi ya kuanzishwa.</li>"
				+ "<li>ModernFix yenyewe inatoa onyo: <i>\"Use of ModernFix with OptiFine is not supported\"</i>.</li>"
				+ "</ul>" + "<p>Unapaswa kufuta moja ya mods hizi mbili ili mcheze ufanye kazi vizuri.</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "Mgogoro kati ya ModernFix na OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "Ondoa OptiFine au ModernFix kutoka kwenye folda ya 'mods'. Haziwezi kutumika pamoja.";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "Ikiwa unahitaji uboreshaji, fikiria kutumia OptiFine pekee, au badilisha ModernFix na mods nyepesi kama FerriteCore au EntityCulling.";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Hitilafu: ufunguo wa rejista (registry key) batili wenye herufi zisizoruhusiwa.</b>" + "<ul>"
				+ "<li><b>Ufunguo uliogunduliwa:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>Katika Minecraft, funguo zote za rejista (lebo, mapishi, mafanikio, n.k.) lazima ziwe katika <b>herufi ndogo</b> na zitumie herufi, nambari, alama za chini, dashi na mistari tu.</li>"
				+ "<li>Hitilafu hii mara nyingi husababishwa na mod iliyopangwa vibaya au datapack yenye kasoro.</li>"
				+ "</ul>"
				+ "<p><b>Shauri muhimu:</b> Tumia zana <b>grepr</b> au <b>fgrepr</b> kwenye upau wa pembeni na washa chaguo <b>\"Tafuta katika faili za JAR\"</b> kupata ni mod ipi inayo ufunguo huu usio sahihi.</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "Ufunguo wa Rejista Wenye Herufi Kubwa au Herufi Zisizo Sahihi";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "Tumia 'grepr' au 'fgrepr' na chaguo \"Tafuta katika faili za JAR\" kupata mod yenye hatia.";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "Ikiwa huwezi kutambua mod, ondoa mods za hivi karibuni, hasa zile zinazoongeza blocks, vitu au zana.";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Hitilafu wakati wa kupakia mod <b>"
				+ escapeHtml(modNombre) + "</b>.</b>" + "<ul>"
				+ "<li>Mod imeshindwa kuanzisha moja ya vipengele vyake (kwa mfano, menyu ya mipangilio).</li>"
				+ "<li>Hii mara nyingi hutokea kutokana na kutokuendana na toleo la Minecraft, Fabric au mods nyingine.</li>"
				+ "</ul>" + "<p>Ikiwa hitilafu itaendelea, ondoa au sasisha mod <b>" + escapeHtml(modNombre)
				+ "</b>.</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "Hitilafu ya Uanzishaji wa Mod (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "Ondoa mod '" + modNombre + "' kutoka kwenye folda ya 'mods'.";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "Sasisha mod '" + modNombre + "' hadi toleo linaloendana na usakinishaji wako.";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Hitilafu inayohusiana na mod <b>En Garde!</b> imegunduliwa.</b>" + "<ul>"
				+ "<li>Mod hii inaongeza mbinu za mapigano ya karibu (parry, kuzuia, n.k.).</li>"
				+ "<li>Hitilafu mara nyingi hutokea kutokana na kutokuendana na mods nyingine za mapigano (kama Epic Fight, DualRiders, n.k.) au kutumia toleo lisilo sahihi kwa Minecraft yako.</li>"
				+ "</ul>"
				+ "<p>Ikiwa hutumii mapigano ya hali ya juu, fikiria kufuta En Garde! ili kuepuka migogoro.</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "Hitilafu katika Mod En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "Hakikisha unatumia toleo la En Garde! linaloendana na toleo lako la Minecraft na kipakiaji chako (Fabric/Forge).";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "Ikiwa unatumia mods nyingine za mapigano (Epic Fight, Caelus, n.k.), zizime au uondoe En Garde! ili kuepuka migogoro.";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Hitilafu iliyosababishwa na mod <b>IdleTweaks</b> imegunduliwa.</b>" + "<ul>"
				+ "<li>IdleTweaks ilijaribu kuachilia kituo cha mtandao ambacho hakipo tena (<code>Tried to release unknown channel</code>).</li>"
				+ "<li>Hitilafu hii mara nyingi hutokea katika matoleo ya zamani ya mod au wakati wa kuitumia kwenye seva zilizosanidiwa vibaya.</li>"
				+ "</ul>"
				+ "<p>IdleTweaks ni mod ya ubora wa maisha, lakini inaweza kusababisha kutokuwa na utulivu. Fikiria kuisasisha au kuifuta.</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "Hitilafu katika IdleTweaks (kituo cha mtandao kisichojulikana)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "Sasisha IdleTweaks hadi toleo la hivi karibuni linaloendana na Minecraft yako.";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "Ondoa IdleTweaks kutoka kwenye folda ya 'mods' ikiwa huihitaji.";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Hitilafu ya uthibitishaji (HTTP 401) imegunduliwa wakati wa kujaribu kuingia katika Minecraft.</b>"
				+ "<p>Hitilafu hii <b>mara chache ndiyo sababu ya moja kwa moja ya kuganda</b>, lakini inaonyesha unatumia akaunti isiyothibitishwa (pirated).</p>"
				+ "<p>Mitaando rasmi ya usaidizi (miradi ya mashirika, VTubers, watengenezaji wa modpacks, n.k.) <b>hawawezi kukusaidia</b> ikiwa unatumia nakala pirated, "
				+ "kutokana na vikwazo vya sheria zao za mazungumzo, mikataba, makubaliano na Mojang/Microsoft, au sera za sifa.</p>"
				+ "<p>Uhakiki huu unaweza <b>kuzimwa katika mipangilio ya shirika</b> ya detector. "
				+ "Onyo: ugunduzi wa anti-piracy <b>sio kamili</b> na unaweza kuwashwa katika mazingira ya maendeleo, na intaneti isiyo thabiti, au wakati wa kutumia lanzadors zilizorekebishwa.</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>Haki za Miranda ikiwa utajaribu kujiunga na usaidizi bado:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Minecraft Pirated";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "Zima uhakiki wa anti-piracy";
	}

	@Override
	public String comprarMC() {
		return "Nunua Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Unatumia lanzador <code>" + id
				+ "</code>, ambayo <b>haiko kwenye orodha ya lanzadors zinazopendekezwa</b>.</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>Ingawa inaweza kufanya kazi, lanzadors zisizopendekezwa mara nyingi husababisha:</p>" + "<ul>"
				+ "<li>Usakinishaji ulioharibika wa mods au App.</li>"
				+ "<li>Mchezo hauanzi au huganda bila hitilafu wazi.</li>"
				+ "<li>Muundo wa folda usio wa kawaida (unafanya utambuzi kuwa mgumu).</li>"
				+ "<li>Tabia isiyotarajiwa na Java, kumbukumbu au mods.</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "Kwa uzoefu bora, tumia moja ya lanzadors hizi zinazopendekezwa:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Lanzador Haipendekezwi";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "Badilisha kwenda kwa lanzador iliyo kwenye orodha inayopendekezwa.";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Unatumia <b>lanzador isiyopendekezwa</b>: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>Lanzadors zisizopendekezwa zinaweza kusababisha:</p>" + "<ul>"
				+ "<li>Usakinishaji ulioharibika wa App au mods.</li>" + "<li>Mchezo hauanzi au unashindwa kimya.</li>"
				+ "<li>Mpangilio usio wa kawaida wa faili (mgumu kutatua).</li>"
				+ "<li>Kutokuwa na uhakika jinsi inavyoshughulikia mods, Java au kumbukumbu.</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "Inapendekezwa sana kutumia moja ya lanzadors zifuatazo:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Lanzador Isiyopendekezwa";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "Badilisha kwenda kwa lanzador inayopendekezwa ili kupokea usaidizi.";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mods zinazopendekezwa hazipo katika mazingira haya.</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "Mods Zinazopendekezwa Hazipo";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "Sakinisha mods zinazopendekezwa kwa ajili ya uzoefu bora.";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mods zisizopendekezwa zimegunduliwa katika usakinishaji wako.</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "Mods Zisizopendekezwa Zimegunduliwa";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "Ondoa mods zisizopendekezwa ili kuepuka matatizo.";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Udanganyifu usioruhusiwa umegunduliwa katika faili muhimu. Umerekebisha faili au unatumia lanzador isiyoaminika.</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "Udanganyifu Umegunduliwa";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "Weka upya faili asili ili kurejesha uadilifu.";
	}

	@Override
	public String configuracionCorporativa() {
		return "Mipangilio ya Shirika";
	}

	@Override
	public String idiomaRespaldo() {
		return "Lugha ya Akiba";
	}

	@Override
	public String buscardorHabilitado() {
		return "Washa Kitafutaji";
	}

	@Override
	public String nombreHerramienta() {
		return "Jina la Zana";
	}

	@Override
	public String condenarPirateria() {
		return "Laani Upirateshi";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "Lanzadors Zinazopendekezwa";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "Lanzadors Zisizopendekezwa";
	}

	@Override
	public String modsRecomendados() {
		return "Mods Zinazopendekezwa";
	}

	@Override
	public String modsDesaconsejados() {
		return "Mods Zisizopendekezwa";
	}

	@Override
	public String antiTamper() {
		return "AntiTamper";
	}

	@Override
	public String proximamente() {
		return "Hivi Karibuni";
	}

	@Override
	public String informacion() {
		return "Habari";
	}

	@Override
	public String errorCargandoImagen() {
		return "Hitilafu wakati wa kupakia picha";
	}

	@Override
	public String configuracionBasica() {
		return "Mipangilio Msingi";
	}

	@Override
	public String funcionalidades() {
		return "Utendakazi";
	}

	@Override
	public String derechosMiranda() {
		return "Haki za Miranda (INAPENDEKEZWA SANA)";
	}

	@Override
	public String gestionVerificaciones() {
		return "Usimamizi wa Uhakiki";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "Jina";
	}

	@Override
	public String codigoVerificacion() {
		return "Msimbo";
	}

	@Override
	public String documentacionVerificacion() {
		return "Nyaraka";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "Uhakiki Uliowashwa:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "Uhakiki Uliozimwa:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "Zima zote zisizo za shirika";
	}

	@Override
	public String verCodigo() {
		return "Tazama Msimbo";
	}

	@Override
	public String verDocumentacion() {
		return "Tazama Nyaraka";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "Chagua uhakiki wa kuzima.";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "Chagua uhakiki wa kuwasha.";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "Uhakiki %d usiopendekezwa kwa matumizi ya shirika umezimwa.";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "Hakuna uhakiki usio wa shirika wa kuzima.";
	}

	@Override
	public String operacionCompletada() {
		return "Shughuli imekamilika";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "Tunakukumbuka Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "Rangi ya Uhakiki wa Shirika";
	}

	// Métodos para la gestión de lanzadores
	@Override
	public String nombreLanzador() {
		return "Jina la Lanzador";
	}

	@Override
	public String motivo() {
		return "Sababu";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Lanzadors Zisizopendekezwa";
	}

	@Override
	public String moverADesaconsejados() {
		return "Usipendekeze";
	}

	@Override
	public String moverARecomendados() {
		return "Pendekeza";
	}

	@Override
	public String guardarCambios() {
		return "Hifadhi Mabadiliko";
	}

	@Override
	public String cancelar() {
		return "Ghairi";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "Tafadhali chagua lanzador ya kusogeza.";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "Mabadiliko yamehifadhiwa kikamilifu!";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEs(String nombreLanzador) {
		return "Lanzador hii haipendekezwi kutokana na matatizo ya usalama na utulivu yanayojulikana.";
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
		return "Sababu";
	}

	@Override
	public String agregarLanzador() {
		return "Ongeza Lanzador";
	}

	@Override
	public String quitarLanzador() {
		return "Ondoa Lanzador";
	}

	@Override
	public String editarRazones() {
		return "Hariri Sababu";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "Chagua lanzador ya kuondoa.";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "Chagua lanzador ya kuhariri.";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "Hariri sababu za " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "Ongeza Lugha Mpya";
	}

	@Override
	public String aceptar() {
		return "Kubali";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "Chagua Lugha";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "Hizi ni lanzadors ambazo " + Statics.nombre_cd.obtener() + " inapendekeza kama nzuri.";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "Matokeo Sahihi";
	}

	public String modsNoRecomendados() {
		return "Mods Zisizopendekezwa";
	}

	public String agregarMod() {
		return "Ongeza Mod";
	}

	public String quitarMod() {
		return "Ondoa Mod";
	}

	public String modId() {
		return "Mod ID/Jina la JBoss Modules";
	}

	public String rutaMod() {
		return "Njina / Faili la Mod";
	}

	public String errorDebeIndicarMod() {
		return "Unapaswa kuonyesha angalau modid au njina ya mod.";
	}

	public String modsNoRecomendadosAviso() {
		return "Hapa unaweza kusajili mods zisizopendekezwa ili " + Statics.nombre_cd.obtener()
				+ " izigundue ikiwa zimesakinishwa.";
	}

	@Override
	public String anularNormal() {
		// TODO Auto-generated method stub
		return "Batilisha Kawaida";
	}

	@Override
	public String anularNormalDescripcion() {
		// TODO Auto-generated method stub
		return Statics.nombre_cd.obtener() + " inapaswa kuonya hata ikiwa haigandi";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "Sajili mods ambazo " + Statics.nombre_cd.obtener() + " inapendekeza. Ikiwa hazipo, "
				+ Statics.nombre_cd.obtener() + " inaweza kuonya.";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "Ikiwa utaamua kuwasha onyo la anti-piracy, inapendekezwa kufafanua hapa "
				+ "haki za mtu anaomba usaidizi, kama hatua ya kinga.\n\n"

				+ "Kinyume na imani ya kawaida, jamii nyingi na mitandao maarufu ya usaidizi "
				+ "HAHITAJI kuwasha maonyo ya anti-piracy ili kutoa msaada. Hata hivyo, "
				+ "kunyaraka haki hizi kunaweza kuwa na faida ikiwa mtu ataingia kwenye kituo "
				+ "cha usaidizi anyway.\n\n"

				+ "Unaweza kuingiza hati rasmi kama vile Karteli ya Haki Msingi za Mtuhumiwa " + "nchini Mexico:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "Pamoja na kanuni za kisheria zinazolinganishwa zinazotumika katika nchi nyingine, ikiwa ni pamoja na "
				+ "Marekani, Shirikisho la Urusi, Jamhuri ya Watu wa China, Jamhuri ya Kiislamu "
				+ "ya Iran na Jamhuri ya Kidemokrasia ya Watu wa Korea.\n\n"

				+ "Baadhi ya mifano ya haki ambazo zinaweza kujumuishwa ni:\n"
				+ "• Haki ya kutoa taarifa zisizohitajika kwa ajili ya usaidizi, kama vile lanzador inayotumika, "
				+ "jina la mtumiaji au UUID.\n" + "• Haki ya kujitosheleza (kutokujilaumu).\n"
				+ "• Haki ya kukataa kujibu maswali ambayo siyo muhimu kutatua tatizo.\n"
				+ "• Haki ya kupokea maelekezo ndani ya mazungumzo.\n"
				+ "• Haki ya kutumia ufinyuaji wa rekodi (logs) uliojumuishwa katika " + Statics.nombre_cd.obtener()
				+ ".\n\n"

				+ "Maandishi haya yanakubali maudhui ya HTML.";
	}

	@Override
	public String editar() {
		// TODO Auto-generated method stub
		return "Hariri";
	}

	@Override
	public String advertenciaHashLento() {
		return "Onyo: kuongeza faili nyingi kubwa kunaweza kufanya uhakiki " + "uchukue dakika kadhaa. "
				+ Statics.nombre_cd.obtener() + " itahitaji kuhesabu hash ya kila faili "
				+ "kabla ya kuendelea. Inapendekezwa kulinda faili zinazohitajika sana pekee.";
	}

	@Override
	public String agregarArchivo() {
		return "Ongeza Faili";
	}

	@Override
	public String agregarCarpeta() {
		return "Ongeza Folda";
	}

	@Override
	public String quitar() {
		return "Ondoa";
	}

	@Override
	public String rutaArchivo() {
		return "Njina ya Faili";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "Njina iliyochaguliwa iko nje ya saraka ya sasa ya mchezo. "
				+ "Faili na folda zinazoruhusiwa ni zile zilizo ndani ya saraka ya sasa au saraka ndogo zake.";
	}

	@Override
	public String mensajeDeSylentBell() {
		// TODO Auto-generated method stub
		return "<html><div style='width:150px; text-align:center;'>"
				+ "Maoni na maoni ya Sylent Bell hayalingani necessarily na yetu; "
				+ "tunadhani tu ingekuwa vichekesho kumweka hapa. " + Statics.nombre_cd.obtener()
				+ " ni ya kidunia (secular)." + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mod ya GML (Groovy ModLoader) inahitaji mabadiliko haya na ndiyo chanzo kikuu cha tatizo hili.</b>";
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
				+ "Matumizi ya <i>Flywheel Huru</i> yamegunduliwa.</b>"
				+ "<p><b>Flywheel Huru imeachwa (deprecated)</b> na haipaswi kutumika katika matoleo ya kisasa.</p>"
				+ "<p>Matoleo ya sasa ya <b>Create</b> <b>tayari yanajumuisha Flywheel</b>, kwa hivyo kuisakinisha peke yake "
				+ "husababisha migogoro ya utangamano na hitilafu za upakiaji.</p>"
				+ "<p>Baadhi ya mods ambazo zinategemea wazi Flywheel Huru zinaweza "
				+ "<b>kutofanya kazi</b> au <b>kufanya kazi bila utulivu</b>. "
				+ "Katika baadhi ya kesi za hali ya juu, mods hizi zinaweza kufanya kazi ikiwa "
				+ "<b>faili la <code>mods.toml</code> litarekebishwa kwa mikono</b> ili kurekebisha mipango ya toleo, "
				+ "ingawa hii <b>haipendekezwi</b>.</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>Mods ziligunduliwa zinazorejelea Flywheel:</b></p>" + "<ul>" + listaMods.toString()
								+ "</ul>")
				+ "<p>Suluhisho linalopendekezwa ni <b>kufuta Flywheel Huru</b> na kutumia pekee "
				+ "toleo lililojumuishwa na Create.</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Flywheel Huru";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu inayohusiana na mod <i>Floral Enchantments</i> imegunduliwa.</b>"
				+ "<p>Kuganda kunasababishwa na hitilafu ya ndani ya mod wakati wa kushughulikia data ya mchezo, "
				+ "ambayo husababisha <b>NullPointerException</b> wakati wa utekelezaji.</p>"
				+ "<p>Tatizo hili mara nyingi hulifikia kwa kusasisha mod au kuiondoa.</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "Hitilafu ya Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Una toleo la NeoForge la MixinExtras na toleo la kawaida. Ikiwa uko kwenye MinecraftForge, unaweza kusakinisha <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>kiungo hiki</a> kwa suluhisho.</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu katika vivuli vya ardhi na shaders (Iris) imegunduliwa.</b>"
				+ "<p>Tatizo hutokea wakati wa urenderi wa ardhi.</p>"
				+ "<p>Inapendekezwa <b>kujaribu mchezo bila shaders</b> au kupunguza ubora wa grafiki, "
				+ "hasa katika mipangilio ya <b>Ultra</b>.</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "Vivuli vya Ardhi (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Tick ndefu ya seva imegunduliwa.</b>"
				+ "<p>Hii inaonyesha kuwa mchezo umebaki kuganda kwa muda mrefu sana katika tick moja.</p>"
				+ "<p>Inapendekezwa <b>kuangalia thread dump</b> iliyotengenezwa katika logi kutambua sababu.</p>"
				+ "<p><b>Uchambuzi wa Traza ya Stack</b> unaweza kukusaidia kupata chanzo cha kuganda.</p>"
				+ "<p>Zaidi ya hayo, kitufe <b>Tazama katika logi</b> kitaweka alama nyekundu mods zinazoweza kuhusika, "
				+ "pamoja na entries zilizozungukwa na <code>$modid$</code>, ambazo mara nyingi huonyesha chanzo cha tatizo. Kwa uchambuzi wa wakati halisi, tunapendekeza kutumia CPU sampler katika VisualVM. Hakikisha seva yako au kompyuta ina nguvu ya kutosha kushughulikia mods unazotumia, kwani inawezekana mods zako zote zinafanya kazi vizuri, lakini una nyingi sana.</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Tick Ndefu ya Seva";
	}

	@Override
	public String tituloLFPDPPP() {
		return "SHERIA YA SHIRIKISHO YA ULINZI WA DATA YA BINAFSI ILIYO MILIKINI NA WATU BINAFSI";
	}

	@Override
	public String aceptarPermanentemente() {
		return "Kubali Milele";
	}

	// Métodos para la Acta de Protección del Idioma Cultural de Pyongyang
	public String actaProteccionIdiomaCultural() {
		return "Sheria ya Ulinzi wa Lugha ya Utamaduni wa Pyongyang";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "Tafsiri ya Kikorea ina istilahi za slang kutoka Kusini ambazo zinapaswa kuepukwa ili kuzingatia sheria. "
				+ "Matumizi ya lugha ya kigeni, hasa inayotoka Kusini, yamepigwa marufuku kabisa "
				+ "kulingana na Sheria ya Ulinzi wa Lugha ya Utamaduni wa Pyongyang.";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "Kwa taarifa zaidi, angalia hati rasmi ya sheria: "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>Sheria ya Ulinzi wa Lugha ya Utamaduni wa Pyongyang</a>";
	}

	public String leerLeyCompleta() {
		return "Soma Sheria Kamili";
	}

	public String errorAbriendoEnlace() {
		return "Hitilafu wakati wa kufungua kiungo";
	}

	@Override
	public String canarioTitulo() {
		return "Canary ya Agizo la Mahakama";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — Mfuatiliaji wa Ulinzi";
	}

	@Override
	public String revisar() {
		return "Angalia";
	}

	@Override
	public String cerrar() {
		return "Funga";
	}

	@Override
	public String canarioTodoSeguro() {
		return "Huduma zote zinaripoti hali salama.";
	}

	@Override
	public String canarioComprometido(int c) {
		return "ONYO: Huduma " + c + " zinaripoti hali isiyo salama.";
	}

	@Override
	public String colorAlerta() {
		return "Rangi ya onyo";
	}

	public String opcionesMunidiales() {
		return "Chaguo za Kimataifa";
	}

	public String consentimientoLFPDPPP() {
		return "Idhini LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "Washa uhamishaji wa tokeni ya ufikiaji katika Handoff kwa ReLauncher (haipendekezwi).";
	}

	public String consolaDesarrollo() {
		return "Konsoli ya Maendeleo";
	}

	public String mundial() {
		return "Kimataifa";
	}

	public String ningun() {
		return "Hakuna";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "Konsoli ya Msanidi Programu";
	}

	public String bajar() {
		return "Shusha";
	}

	public String logsSoporte() {
		return "Logi za Usaidizi";
	}

	public String detenerProceso() {
		return "Simamisha Mchakato";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "Nakili Uteuzi";
	}

	public String seleccionarTodo() {
		return "Chagua Yote";
	}

	public String copiarTodo() {
		return "Nakili Yote";
	}

	public String guardarTodoComoArchivo() {
		return "Hifadhi Yote kama Faili";
	}

	public String obtenerEnlaceSoporte() {
		return "Pata Kiungo cha Usaidizi";
	}

	public String borrarTodo() {
		return "Futa Yote";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "Rangi ya mandhari ya konsoli";
	}

	public String colorTextoConsola() {
		return "Rangi ya maandishi ya konsoli";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "Idhini imethibitishwa.\nMuunganisho wa kushiriki logi utatekelezwa hapa.";
	}

	@Override
	public String usarSakuraOriginal() {
		// TODO Auto-generated method stub
		return "Tumia Picha Asili ya Sakura Riddle";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "\"Warrant canary\" ni utaratibu wa uwazi.\n\n"
				+ "Mradi huu haujapokea maagizo ya siri ya mahakama, "
				+ "mahitaji ya udhibiti, au ombi halali za ufuatiliaji.\n\n"
				+ "Ikiwa canary yoyote itaacha kuwepo au itaweka alama kama isiyo salama, "
				+ "hii inaonyesha kuwa kitu kimebadilika kisheria.\n\n"
				+ "Paneli hii huangalia canaries zote zilizosajiliwa katika mfumo na kuonyesha "
				+ "hali yao ya sasa.\n\n" + "Bonyeza \"Angalia\" kusasisha hali.";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		// TODO Auto-generated method stub
		return "Je, unataka kurejesha chaguo zote hadi thamani zao za chaguomsingi?";
	}

	@Override
	public String gui() {
		// TODO Auto-generated method stub
		return "Kiolesura (GUI)";
	}

	@Override
	public String sinOpciones() {
		// TODO Auto-generated method stub
		return "Hakuna Chaguo";
	}

	@Override
	public String seleccionaColor() {
		// TODO Auto-generated method stub
		return "Chagua Rangi";
	}

	@Override
	public String botonMostrarGUI() {
		return "Onyesha Kiolesura";
	}

	@Override
	public String botonGuardarTodo() {
		return "Hifadhi Yote";
	}

	@Override
	public String botonRestablecerTodo() {
		return "Rejesha Yote";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms Haijapakia";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu imegunduliwa wakati wa kufikia API ya LuckPerms.</b>"
				+ "<p>Ujumbe unaonyesha kuwa <b>LuckPerms haikupakiwa</b> wakati plugin nyingine ilipojaribu kuitumia.</p>"
				+ "<p><b>Sababu zinazowezekana:</b></p>" + "<ul>"
				+ "<li>Plugin ya <b>LuckPerms haijasakinishwa</b> au <b>imeshindwa kuanza</b>.</li>"
				+ "<li>Plugin nyingine inajaribu kufikia LuckPerms mapema sana au kwa njia isiyo sahihi.</li>" + "</ul>"
				+ "<p>Inapendekezwa <b>kuangalia konsoli</b> kwa kutumia kiungo kutambua "
				+ "plugin inayoita LuckPerms na kuhakikisha utangamano wake.</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Shaderpack ya Iris Haijapakia";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "haijulikani" : shaderpack;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu imegunduliwa wakati wa kupakia shaderpack na Iris/Oculus.</b>"
				+ "<p><b>Shaderpack iliyoharibiwa:</b> " + nombre + "</p>"
				+ "<p>Minecraft haikuweza kufungua faili la shaderpack (FileSystemNotFoundException).</p>"
				+ "<p><b>Suluhisho zinazowezekana:</b></p>" + "<ul>"
				+ "<li>Hakikisha shaderpack imesakinishwa vizuri katika folda ya <b>shaderpacks</b>.</li>"
				+ "<li>Pakua tena shaderpack, kwani faili linaweza kuwa limeharibika.</li>"
				+ "<li>Ikiwa tatizo litaendelea, futa faili <b>config/iris.properties</b> ili kuweka upya mipangilio ya Iris.</li>"
				+ "</ul>" + "<p>Baada ya kufanya mabadiliko, anza mchezo tena.</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "Haikuweza Kuandika Faili la Mipangilio";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "haijulikani" : ruta;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu imetokea wakati wa kuhifadhi faili la mipangilio.</b>" + "<p><b>Faili lililoathirika:</b> "
				+ archivo + "</p>"
				+ "<p>Minecraft haikuweza kuandika faili kwa kutumia uandishi wa atomiki (REPLACE_ATOMIC).</p>"
				+ "<p><b>Hii mara nyingi hutokea kwa sababu ya:</b></p>" + "<ul>"
				+ "<li>Ruhusa zisizo sahihi kwenye folda au faili.</li>"
				+ "<li>Faili limewekwa alama kama la kusoma pekee (read-only).</li>"
				+ "<li>Programu nyingine (antivirus, nakala rudufu, kihariri) imefunga faili.</li>" + "</ul>"
				+ "<p><b>Mapendekezo:</b></p>" + "<ul>" + "<li>Hakikisha una ruhusa za kuandika kwenye folda.</li>"
				+ "<li>Ondoa sifa ya kusoma pekee kutoka kwenye faili.</li>"
				+ "<li>Funga programu zinazoweza kutumia faili hilo.</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "Ufikiaji Umekataliwa Wakati wa Kutengeneza Nakala Rudufu";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "haijulikani" : origen;
		String dst = backup == null || backup.isEmpty() ? "haijulikani" : backup;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu ya ruhusa imetokea wakati wa kutengeneza nakala rudufu ya faili la mipangilio.</b>"
				+ "<p><b>Faili asili:</b> " + src + "</p>" + "<p><b>Faili la nakala rudufu:</b> " + dst + "</p>"
				+ "<p>Mfumo wa uendeshaji umekataa ufikiaji wakati wa kuhifadhi faili.</p>"
				+ "<p><b>Hii mara nyingi hutokea kwa sababu ya:</b></p>" + "<ul>"
				+ "<li>Ruhusa zisizo za kutosha kwenye folda.</li>"
				+ "<li>Faili limewekwa alama kama la kusoma pekee.</li>"
				+ "<li>Programu nyingine (antivirus, usawazishaji, kihariri) inatumia faili.</li>" + "</ul>"
				+ "<p><b>Mapendekezo:</b></p>" + "<ul>" + "<li>Angalia ruhusa za folda <b>config</b>.</li>"
				+ "<li>Funga programu zinazoweza kufikia faili.</li>"
				+ "<li>Jaribu kuanzisha lanzador au Minecraft kama msimamizi (administrator).</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		// TODO Auto-generated method stub
		return "Washa Konsoli";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>Zana za utatuzi (debugging)</b><br><br>"
				+ "Hapa unaweza kuwasha vipengele vya hali ya juu kutatua matatizo ya " + Statics.nombre_cd.obtener()
				+ " na michezo yako.<br><br>"
				+ "Inapendekezwa kuwasha konsoli ya maendeleo kupata taarifa za kina, traza na uchunguzi wakati wa uchambuzi.<br><br>"
				+ "Ikiwa unahitaji kujaribu seva ya wachezaji wengi katika hali ya mtandaoni (online), kunaweza kuhitajika kuruhusu uhamishaji wa tokeni ya ufikiaji kwenye mchakato wa "
				+ Statics.nombre_cd.obtener() + " kutoka kwenye mipangilio ya faragha. "
				+ "Hii kwa kawaida <b>haipendekezwi</b> katika kesi nyingine.<br><br>"
				+ "Maagizo kamili: <a href='https://example.com'>Kiungo!</a>";// TODO
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kutokuendana kumegunduliwa kati ya Simple Clouds na shaders.</b>"
				+ "<p>Simple Clouds haiendani na mods za vivuli (Iris/Oculus) wakati Distant Horizons imesakinishwa.</p>"
				+ "<p><b>Chaguo zinazopendekezwa:</b></p>" + "<ul>"
				+ "<li>Ondoa <b>Simple Clouds</b> ikiwa unataka kutumia shaders.</li>"
				+ "<li>Au, ondoa <b>Iris au Oculus</b> ikiwa unapendelea kudumisha Simple Clouds.</li>" + "</ul>"
				+ "<p>Kikomo hiki kinatoka kwenye mod yenyewe ya Simple Clouds na hakiwezi kutatuliwa bila kurekebisha msimbo wake.</p>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "Kutokuendana: Simple Clouds dhidi ya Shaders";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "Rangi ya kitufe cha upau wa pembeni";
	}

	@Override
	public String profilerTitulo() {
		return "Profiler";
	}

	@Override
	public String profilerDescripcion() {
		return "Zana ya uchambuzi wa utendaji inayotegemea instrumentation na sampling.";
	}

	@Override
	public String profilerIniciar() {
		return "Anza";
	}

	@Override
	public String profilerDetener() {
		return "Simamisha";
	}

	@Override
	public String profilerLimpiar() {
		return "Safisha";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "Profiler imeanzishwa.";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "Profiler imesimamishwa.";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "Sampuli iliyopokelewa kutoka kwenye msururu: " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "Uchukuzi wa sampuli wa mara kwa mara wa traza za stack ili kugundua vizuizi na kufungwa.";
	}

	@Override
	public String entrarAlJuego() {
		// TODO Auto-generated method stub
		return "Ingia kwenye Mchezo";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu katika njina ya mfumo imegunduliwa.</b>"
				+ "<p>Minecraft haikuweza kuanza kutokana na herufi zisizo halali katika jina la folda.</p>"
				+ "<p>Mfumo umegundua herufi batili katika njina (kwa mfano: ':' au alama nyingine maalum).</p>"
				+ "<p><b>Suluhisho linalopendekezwa:</b></p>" + "<ul>"
				+ "<li>Badilisha jina la folda ya instansi au profaili.</li>"
				+ "<li>Tumia herufi za ASCII msingi pekee (A-Z, a-z, 0-9).</li>"
				+ "<li>Usitumie alama za akcenti, alama maalum, nafasi zenye shida au emojis.</li>" + "</ul>"
				+ "<p>Mfano sahihi: <b>MiInstancia1</b></p>"
				+ "<p>Mfano usio sahihi: <b>Instancia🔥</b> au <b>Instancia:Mod</b></p>";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "Njina Batili: Herufi Zisizoruhusiwa";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kushindwa kumegunduliwa katika shaders za Twilight Forest na GPU ya Intel.</b>"
				+ "<p>Hitilafu hii inahusiana na madereva ya grafiki ya Intel wakati wa kupakia shaders za mod ya Twilight Forest.</p>"
				+ "<p>Kushindwa kunatokea ndani ya dereva (igxelpicd64) na siyo tatizo la moja kwa moja la mod au Minecraft.</p>"
				+ "<p><b>Suluhisho zinazopendekezwa:</b></p>" + "<ul>"
				+ "<li>Sasisha madereva ya Intel hadi toleo la hivi karibuni linalopatikana.</li>"
				+ "<li>Jaribu hasa matoleo 31.0.101.8331 au 31.0.101.8247 WHQL, ambayo kulingana na maoni hayana tatizo hili.</li>"
				+ "</ul>" + "<p>Ufuatiliaji rasmi wa tatizo:</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>Angalia:</b> Baadhi ya GPUs za zamani za Intel zinaweza kupokea visasisho vinavyorekebisha tatizo hili.</p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "Kuganda: Twilight Forest + Madereva ya Intel";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "Forge: Mtoaji wa Lugha Haikuweza Kupakia";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "Mtoaji haijulikani" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Forge haikuweza kupakia mtoaji wa lugha.</b>"
				+ "<p>Hitilafu imetokea wakati wa kuanzisha IModLanguageProvider.</p>"
				+ "<p><b>Mtoaji aliyeshindwa:</b> " + providerTexto + "</p>"
				+ "<p>Tatizo hili mara nyingi hutokea wakati:</p>" + "<ul>"
				+ "<li>Utegemezi unaohitajika unakosekana (kwa mfano, Kotlin for Forge).</li>"
				+ "<li>Toleo la mod halioendani na toleo lako la Forge.</li>" + "<li>Faili la mod limeharibika.</li>"
				+ "</ul>" + "<p><b>Suluhisho zinazopendekezwa:</b></p>" + "<ul>" + "<li>Weka upya mod husika.</li>"
				+ "<li>Hakikisha kuwa utegemezi wake wote umesakinishwa.</li>"
				+ "<li>Hakikisha unatumia matoleo yanayoendana na Forge yako ya sasa.</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "Kuganda: Lets Do Compat (RecipeManager intercept)";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kuganda kumegunduliwa katika Lets Do Compat (interceptApply).</b>"
				+ "<p>Hitilafu inatokea ndani ya ubadilishaji wa njia "
				+ "<b>RecipeManager.interceptApply</b> uliofanywa na Lets Do Compat.</p>"
				+ "<p>Hii mara nyingi huonyesha:</p>" + "<ul>"
				+ "<li>Kutokuendana kati ya Lets Do Compat na mod nyingine inayorekebisha mapishi.</li>"
				+ "<li>Toleo lisilo sahihi kwa ajili ya toleo lako la Minecraft.</li>"
				+ "<li>Mgogoro kati ya vabadilishaji (mixin/coremod).</li>" + "</ul>"
				+ "<p><b>Suluhisho zinazopendekezwa:</b></p>" + "<ul>"
				+ "<li>Jaribu kufuta kwa muda Lets Do Compat kuthibitisha mgogoro.</li>" + "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI: Kushindwa katika Kikundi cha Vitu (plugin isiyoendana)";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "Plugin haijulikani"
				: String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "JEI imegundua kushindwa wakati wa kujenga kikundi cha vitu.</b>"
				+ "<p>Plugin moja au zaidi zilisababisha hitilafu wakati JEI ilipokuwa ikitoa orodha ya viungo.</p>"
				+ "<p><b>Vikundi/Plugins vilivyoathirika:</b> " + listaPlugins + "</p>"
				+ "<p>Tatizo hili ni la kawaida wakati:</p>" + "<ul>"
				+ "<li>Plugin ya JEI imeandikwa vibaya au haijasasishwa.</li>"
				+ "<li>Kuna kutokuendana na toleo la sasa la JEI.</li>"
				+ "<li>Fabric API inatumika na mod fulani inasajili Kikundi chake cha Vitu vibaya.</li>" + "</ul>"
				+ "<p><b>Suluhisho zinazopendekezwa:</b></p>" + "<ul>"
				+ "<li>Sasisha JEI na mods zilizoorodheshwa.</li>"
				+ "<li>Ondoa kwa muda plugins zilizoathirika kuthibitisha mgogoro.</li>"
				+ "<li>Ripoti hitilafu kwa mtengenezaji wa mod husika.</li>" + "</ul>"
				+ "<p>Vitu vya vikundi hivi havitaonekana katika orodha ya viungo hadi tatizo litakaporekebishwa.</p>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "Toleo Batili la Mod (SemVer)";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "Haijulikani" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "Haikuweza kupata mod" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Toleo batili la mod limegunduliwa.</b>" + "<p>Toleo <b>" + v
				+ "</b> halikidhi muundo sahihi wa SemVer.</p>"
				+ "<p>Hitilafu inaonyesha pre-release tupu (inaishia na '+').</p>" + "<p><b>Mod inayehusika:</b><br>"
				+ u + "</p>" + "<p><b>Suluhisho linalopendekezwa:</b></p>" + "<ul>"
				+ "<li>Hariri faili la mod na urekebishe toleo.</li>"
				+ "<li>Ondoa '+' ya mwisho ikiwa hakuna metadata baada yake.</li>"
				+ "<li>Sasisha mod hadi toleo lililorekebishwa.</li>" + "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS: Ufikiaji Haramu Kati ya Moduli";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Ufikiaji haramu kati ya moduli (JPMS) umegunduliwa.</b>"
				+ "<p>Mfumo wa moduli wa Java (JPMS) ulizuia ufikiaji kati ya madarasa.</p>"
				+ "<p><b>Darasa linalojaribu kufikia:</b><br>" + claseOrigen + " (moduli: " + moduloOrigen + ")</p>"
				+ "<p><b>Darasa lililozuiwa:</b><br>" + claseDestino + " (moduli: " + moduloDestino + ")</p>"
				+ "<p>Aina hii ya hitilafu hutokea wakati mod haitangazi vizuri "
				+ "exports au opens katika module-info.java yake.</p>" + "<p><b>Sababu zinazowezekana:</b></p>" + "<ul>"
				+ "<li>Moduli haielekezi pakiti inayohitajika.</li>"
				+ "<li>Inakosa agizo la <b>opens</b> kwa ajili ya reflection.</li>"
				+ "<li>Mod haijasanidiwa vizuri kwa ajili ya JPMS.</li>" + "</ul>"
				+ "<p>Tatizo hili linapaswa kurekebishwa na mtengenezaji wa mod.</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin: Darasa Lililowekwa Vibaya katika Pakiti ya Mixin";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Darasa limewekwa vibaya katika pakiti ya Mixin.</b>"
				+ "<p>Darasa la kawaida limewekwa ndani ya pakiti iliyotangazwa kama mixin.</p>"
				+ "<p><b>Darasa lenye mgogoro:</b><br>" + clase + "</p>"
				+ "<p><b>Pakiti ya mixin iliyotangazwa:</b><br>" + paquete + "</p>"
				+ "<p><b>Faili la mixins linalohusika:</b><br>" + archivoMixin + "</p>"
				+ "<p>Madarasa ya kawaida hayapaswi kuwa ndani ya pakiti iliyofafanuliwa katika mixins.json.</p>"
				+ "<p>Madarasa yaliyo na annotation ya mixin pekee ndiyo yanapaswa kuwepo katika pakiti hiyo.</p>"
				+ "<p><b>Suluhisho kwa msanidi programu:</b> Hamisha madarasa ya kawaida nje ya pakiti ya mixin "
				+ "au urekebishe usanidi wa faili la mixins.json.</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Tatizo limegunduliwa na madereva ya GPU ya Matrox.</b>"
				+ "<p>Logi inaonyesha kuwa kushindwa kulitokea ndani ya maktaba ya dereva wa Matrox.</p>"
				+ "<p>GPUs za Matrox (hasa modeli za G200/G400 zinazotumika kwenye seva) "
				+ "hazijaundwa kwa ajili ya urenderi wa 3D wa kisasa na zinaweza kusupport "
				+ "matoleo ya OpenGL yanayohitajika na Minecraft.</p>" + "<p><b>Suluhisho zinazopendekezwa:</b></p>"
				+ "<ul>" + "<li>Sasisha dereva wa Matrox hadi toleo la hivi karibuni linalopatikana.</li>"
				+ "<li>Sakinisha madereva rasmi badala ya madereva ya jumla ya mfumo.</li>"
				+ "<li>Ikiwa vifaa ni vya zamani, tumia GPU inayosupport OpenGL 3.2 au ya juu zaidi.</li>" + "</ul>"
				+ "<p>Kwenye seva, GPUs hizi mara nyingi hutumika kwa ajili ya video msingi tu "
				+ "na siyo kwa ajili ya programu za 3D kama Minecraft.</p>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod: GPU Haiendi";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod haikuweza kugundua GPU inayoenda.</b>"
				+ "<p>Mod <b>VulkanMod</b> ilijaribu kuanza kwa kutumia Vulkan lakini haikupata GPU inayosupport Vulkan vizuri.</p>"
				+ "<p>Hii mara nyingi hutokea wakati:</p>" + "<ul>" + "<li>GPU hausupport Vulkan.</li>"
				+ "<li>Madereva ya GPU yamepitwa na wakati au hayapo.</li>"
				+ "<li>Adapter ya grafiki isiyo sahihi inatumika (kwa mfano, GPU iliyounganishwa badala ya ile maalum).</li>"
				+ "</ul>" + "<p><b>Suluhisho zinazopendekezwa:</b></p>" + "<ul>"
				+ "<li>Sasisha madereva ya GPU hadi toleo la hivi karibuni.</li>"
				+ "<li>Hakikisha GPU yako inasupport Vulkan.</li>"
				+ "<li>Ikiwa una GPUs mbili, lazimisha matumizi ya GPU maalum kwa ajili ya Minecraft.</li>"
				+ "<li>Ikiwa GPU yako hausupport Vulkan, ondoa VulkanMod.</li>" + "</ul>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "RenderType Batili kwa ajili ya Outline";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		String base = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod ilijaribu kutumia outline kwenye RenderType isiyolingana.</b>" + "<p>Hitilafu ilikuwa:</p>"
				+ "<code>Can't render an outline for this rendertype!</code>";

		if (enchantDetectado) {
			base += "<p><b>Mod ya enchant-outline / better-enchants imegunduliwa katika ripoti.</b></p>"
					+ "<p>Mod hii inajulikana kusababisha tatizo hili katika matoleo ya hivi karibuni ya Minecraft.</p>"
					+ "<p><b>Suluhisho linalopendekezwa:</b> futa au sasisha enchant-outline.</p>";
		} else {
			base += "<p>Tatizo hili mara nyingi linahusiana na mods zinazorekebisha urenderi "
					+ "(Entity Model Features, Entity Texture Features, Visuality au migogoro na Sodium).</p>"
					+ "<p><b>Suluhisho linalopendekezwa:</b> sasisha au zima mods za urenderi moja baada ya nyingine.</p>";
		}

		return base;
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – DimensionalInventory Null";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG ilijaribu kuhifadhi DimensionalInventory null.</b>" + "<p>Mchezo ulirusha:</p>"
				+ "<code>Cannot invoke DimensionalInventory.saveInventory(...) because \"d\" is null</code>"
				+ "<p>Hii ni bug inayojulikana ya DivineRPG inayohusiana na mfumo wa inventory wa Vethean.</p>"
				+ "<p><b>Suluhisho linalopendekezwa:</b></p>" + "<ul>"
				+ "<li>Nenda kwenye faili la mipangilio la DivineRPG.</li>"
				+ "<li>Weka <code>saferVetheanInventory=true</code></li>" + "<li>Hifadhi na uanze upya mchezo.</li>"
				+ "</ul>"
				+ "<p>Inapendekezwa pia kusasisha DivineRPG ikiwa kuna toleo la hivi karibuni linalopatikana.</p>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "Mgogoro katika Render Pass";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mgogoro umegunduliwa katika mfumo wa urenderi.</b>" + "<p>Mchezo ulirusha:</p>"
				+ "<code>Close the existing render pass before performing additional commands</code>"
				+ "<p>Hitilafu hii mara nyingi inahusiana na migogoro kati ya mods za urenderi "
				+ "kama Iris, OptiFine, VulkanMod au nyingine zinazorekebisha pipeline ya grafiki.</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "Kushindwa kwa Ndani kwa Feather Client";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kushindwa kwa ndani kwa Feather Client kumegunduliwa.</b>" + "<p>Mchezo ulirusha:</p>"
				+ "<code>NoClassDefFoundError: feather/lib/sentry/Sentry</code>"
				+ "<p>Hitilafu hii inasababishwa na Feather Client.</p>" + "<p>Feather haipendekezwi kwa sababu:</p>"
				+ "<ul>" + "<li>Inatumia matoleo ya kibinafsi na yaliyorekebishwa ya mods maarufu.</li>"
				+ "<li>Inavunja utangamano na Fabric ya kawaida.</li>"
				+ "<li>Inafanya kazi kama modpack iliyokusanywa tayari na marekebisho ya ndani.</li>"
				+ "<li>Mara nyingi husababisha migogoro na Sodium na mods nyingine za utendaji.</li>" + "</ul>"
				+ "<p>Inapendekezwa kutumia usakinishaji wa kawaida wa Fabric badala ya Feather.</p>";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "Mgogoro Iris + Flywheel (Create 6)";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mgogoro umegunduliwa kati ya Iris na Flywheel katika Create 6.</b>" + "<p>Mchezo ulirusha:</p>"
				+ "<code>NoSuchFieldError: TESSELATION_SHADERS</code>"
				+ "<p>Marejeleo ya ndani <code>$irisflw$</code> yamegunduliwa, "
				+ "ambayo yanaonyesha mgogoro kati ya Iris na Flywheel.</p>"
				+ "<p>Iris Flywheel 2.0 kwa ajili ya Create 6 inasupport rasmi NeoForge pekee.</p>"
				+ "<p>Ikiwa unatumia Forge au Fabric, mchanganyiko huu unaweza kusababisha hitilafu hii.</p>";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "Modeli ya GeckoLib Haipatikani";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod haikuweza kupata modeli ya GeckoLib.</b>" + "<p>Modeli iliyoathirika:</p>" + "<code>" + modelo
				+ "</code>" + "<p>Hitilafu hii hutokea wakati faili <code>.geo.json</code> halipo "
				+ "au limesanidiwa vibaya ndani ya mod.</p>" + "<p>Sababu zinazowezekana:</p>" + "<ul>"
				+ "<li>Modeli iliondolewa lakini bado inarejelewa.</li>" + "<li>Hitilafu katika njina ya faili.</li>"
				+ "<li>Faili halipo ndani ya JAR.</li>" + "<li>Toleo lisiloendana la mod.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – Animation Haipo";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon ilijaribu kucheza animation isiyopo.</b>" + "<p>Animation:</p>" + "<code>" + animacion
				+ "</code>" + "<p>Kikundi:</p>" + "<code>" + grupo + "</code>"
				+ "<p>Hitilafu hii mara nyingi hutokea wakati:</p>" + "<ul>"
				+ "<li>Matoleo yasiyoendana ya Cobblemon yamechanganywa.</li>"
				+ "<li>Addon hailingani na toleo lililosakinishwa.</li>"
				+ "<li>Rasilimali au animations za ndani hazipo.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "Kushindwa kwa Ndani kwa Lunar Client";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kushindwa kwa ndani kwa Lunar Client kumegunduliwa.</b>"
				+ "<p>Hitilafu inatoka kwenye client yenyewe ya Lunar na siyo kwenye mchezo msingi au mods.</p>"
				+ "<p>Lunar Client hutumia marekebisho ya ndani na yaliyobinafsishwa ambayo yanaweza "
				+ "kusababisha kutokuendana na mods au mipangilio mahususi.</p>"
				+ "<p>Inapendekezwa kujaribu na usakinishaji wa kawaida wa Minecraft "
				+ "ili kuondoa matatizo yanayotokana na client yenyewe.</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "Ufikiaji Haramu wa Njia au Uga";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod ilijaribu kufikia njia au uga uliolindwa/wa faragha.</b>" + "<p>Darasa linalohusika:</p>"
				+ "<code>" + clase + "</code>" + "<p>Mwanachama aliyefikiwa:</p>" + "<code>" + miembro + "</code>"
				+ "<p>Hitilafu hii mara nyingi hutokea wakati:</p>" + "<ul>"
				+ "<li>Mod ilikompiliwa kwa ajili ya toleo lingine la Minecraft.</li>"
				+ "<li>Kuna mchanganyiko wa mappings zisizoendana.</li>" + "<li>Mod imepitwa na wakati.</li>"
				+ "<li>Kipakiaji kisicho sahihi kinatumika (Fabric/Forge/NeoForge).</li>" + "</ul>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "Hitilafu Wakati wa Kupakia Datapack/Resourcepack";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Datapack au resourcepack imeshindwa kupakia.</b>" + "<p>Faili lenye shida:</p>" + "<code>" + archivo
				+ "</code>" + "<p>Pakiti:</p>" + "<code>" + pack + "</code>"
				+ "<p>Mchezo haukuweza kusindika (parse) faili hili na hilo likasababisha hitilafu za upakiaji wa rejista.</p>"
				+ "<p>Tatizo hili mara nyingi husababishwa na:</p>" + "<ul>" + "<li>JSON isiyo na muundo sahihi.</li>"
				+ "<li>Toleo la pakiti lisiloendana.</li>"
				+ "<li>Pakiti imepitwa na wakati kwa ajili ya toleo la sasa la mchezo.</li>"
				+ "<li>Mgogoro kati ya datapacks.</li>" + "</ul>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "Hitilafu ya Kukompili Shader";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Ukompilaji wa shader umeshindwa.</b>"
				+ "<p>Mchezo haukuweza kukompili moja ya shaders zinazotumika.</p>"
				+ "<p>Tatizo hili mara nyingi linahusiana na Sodium, Iris au shaderpacks zisizoendana.</p>"
				+ "<p>Mapendekezo:</p>" + "<ul>" + "<li>Jaribu shader tofauti.</li>" + "<li>Zima shaders kwa muda.</li>"
				+ "<li>Sasisha madereva ya GPU.</li>"
				+ "<li>Ikiwa tatizo litaendelea, jaribu kuanzisha mchezo bila Sodium.</li>" + "</ul>";
	}

	public String nombreErrorCreacionModelo() {
		return "Hitilafu Wakati wa Kuunda au Kupakia Modeli";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>Hitilafu imetokea wakati wa kujaribu kuunda au kupakia modeli ya Minecraft.</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>Modeli iliyoathirika: <code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>Aina hii ya hitilafu mara nyingi hutokea wakati:</p>");
		sb.append("<ul>");
		sb.append("<li>Mod ina modeli iliyosanidiwa vibaya.</li>");
		sb.append("<li>Modeli ya JSON imeharibika au haijakamilika.</li>");
		sb.append("<li>Kuna mgogoro kati ya mods zinazorekebisha modeli au urenderi.</li>");
		sb.append("<li>Resource pack au datapack ina modeli zisizoendana.</li>");
		sb.append("</ul>");
		sb.append("<p>Jaribu kutambua ni mod au pakiti ya rasilimali ipi inayotoa modeli iliyotajwa.</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>Sababu inayowezekana imegunduliwa:</b></p>");
		sb.append("<p>Shughuli ya mod <b>Cooler Animations</b> imegunduliwa katika logi.</p>");
		sb.append(
				"<p>Mod hii hubadilisha mfumo wa animations na modeli, na katika baadhi ya kesi inaweza kusababisha hitilafu za upakiaji wa modeli.</p>");
		sb.append(
				"<p>Ikiwa tatizo litaendelea, jaribu kuanzisha mchezo bila Cooler Animations kuona kama hitilafu itapotea.</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "Tatizo na Starlight";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu inayohusiana na Starlight imegunduliwa.</b>"
				+ "<p>Hitilafu ilitokea ndani ya <code>BlockStarLightEngine.initNibble</code>.</p>"
				+ "<p>Hii inaonyesha kushindwa katika injini ya mwanga ya mod <b>Starlight</b>.</p>"
				+ "<p>Starlight ni mod ambayo hubadilisha kabisa mfumo wa mwanga wa Minecraft na inajulikana kusababisha matatizo mbalimbali katika baadhi ya mazingira ya mods.</p>"
				+ "<p>Hii ni mojawapo ya hitilafu nyingi zinazojulikana zinazohusiana na Starlight.</p>"
				+ "<p>Ikiwa tatizo litaendelea, jaribu kuanzisha mchezo bila Starlight.</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "Tatizo na AAAParticles / Effekseer";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kuganda asilia (native crash) kunahusiana na Effekseer kumegunduliwa.</b>"
				+ "<p>Hitilafu ilitokea ndani ya maktaba asilia <code>EffekseerNativeForJava</code>.</p>"
				+ "<p>Maktaba hii inatumika na mod <b>AAAParticles</b> iliyotengenezwa na ChloePrime.</p>"
				+ "<p>Kuganda katika maktaba asilia mara nyingi huonyesha matatizo ndani ya mod yenyewe au katika utegemezi wake wa asilia.</p>"
				+ "<p>Ikiwa tatizo litaendelea, jaribu kuanzisha mchezo bila AAAParticles.</p>";
	}

	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Imegunduliwa crash ya asili ya mazingira ya utendaji wa Java (JVM).</b>"
				+ "<p>Aina hii ya hitilafu hutokea ndani ya Mashine Binafsi ya Java yenyewe (kwa mfano, katika <code>jvm.dll</code>, <code>libjvm.so</code>, n.k.), "
				+ "na si lazima isababishwe na mod.</p>"
				+ "<p>Ingawa kwa mara chache inaweza kusababishwa na mod zinazotumia maktaba za asili zisizolingana, "
				+ "<b>ni uwezekano mkubwa zaidi kwamba inatokana na toleo la JVM lenye kasoro, liliharibika, au la zamani</b>.</p>"
				+ "<p>Hii ni kawaida hasa ikiwa unatumia jengo la zamani au lisilo rasmi la toleo lako la Java (kwa mfano, majengo ya jamii yasiyo na usaidizi).</p>"
				+ "<p><b>Tunapendekeza kutumia JVM ya kuaminika na inayodumishwa:</b></p>" + "<ul>"
				+ "<li><b>Red Hat Build of OpenJDK</b> (thabiti, imejaribiwa vizuri, bora kwa Windows/Linux)</li>"
				+ "<li><b>OpenLogic OpenJDK</b> (usaidizi wa jukwaa nyingi, ikiwa ni pamoja na macOS Intel)</li>"
				+ "<li><b>Azul Zulu</b> (imethibitishwa, na usaidizi wa bure wa LTS)</li>"
				+ "<li><b>Oracle JDK</b> (rasmi, na sasisho za mara kwa mara)</li>" + "</ul>"
				+ "<p>Epuka majengo yasiyojulikana, yaliyobadilishwa, au ya zamani sana, kwani yanaweza kuwa na makosa muhimu katika injini ya JVM.</p>"
				+ "<p><b>Unatumia Prism Launcher au TLauncher?</b> Ni rahisi sana kusanidi JVM ya kibinafsi: "
				+ "katika Prism Launcher, nenda kwa <i>Installations</i> → <i>Edit Instance</i> → <i>Java Settings</i>; "
				+ "katika TLauncher, nenda kwa <i>Settings</i> → <i>Java Settings</i> na uchague njia ya JDK/JRE uliyopakua. "
				+ "Pia inawezekana kwamba kikusanyaji taka chako kinakumbana na matatizo; katika hali hiyo, unapaswa kubadilisha hadi ZGC."

				+ "Huhitaji kubadilisha JVM ya mfumo!</p>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "Mgogoro kati ya Paranoia na C2ME";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mgogoro kati ya mods Paranoia na C2ME umegunduliwa.</b>"
				+ "<p>Hitilafu inaonyesha kuwa <code>ThreadLocalRandom</code> ilifikiviwa kutoka kwenye msururu (thread) usio sahihi.</p>"
				+ "<p>Hii mara nyingi hutokea wakati mod <b>Paranoia</b> inatekeleza msimbo ambao hauko salamu kwa misururu mingi wakati <b>C2ME</b> inafanya uboreshaji wa multithreading.</p>"
				+ "<p>Aina hii ya mgogoro ni ya kawaida na mods zilizotengenezwa na MCreator.</p>"
				+ "<p>Ikiwa tatizo litaendelea, jaribu kuanzisha mchezo bila Paranoia au uzime C2ME.</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "Saraka ya Assets ya Minecraft Haipo";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft haikuweza kupata saraka ya assets.</b>"
				+ "<p>Lanzador ilijaribu kuanzisha mchezo na njia batili ya assets.</p>"
				+ "<p>Hii inamaanisha kuwa faili za rasilimali za mchezo (assets) hazipo au hazikusakinishwa vizuri.</p>"
				+ "<p>Tatizo hili mara nyingi hutokea na usakinishaji mbaya wa Minecraft au lanzadors zilizosanidiwa vibaya.</p>"
				+ "<p>Pia inaweza kutokea wakati wa kutumia lanzadors zisizo rasmi ambazo hushughulikia assets vibaya kama FreshCraft.</p>"
				+ "<p>Ikiwa tatizo litaendelea, jaribu kusakinisha tena modpack au kuanzisha mchezo kutoka kwenye lanzador rasmi au ya kuaminika.</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return "Sakinisha " + dependencia + " toleo " + version + " au la baadaye";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return "Badilisha " + dependencia + " na toleo kati ya " + min + " na " + max;
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "Mod " + mod + " inahitaji " + dependencia + " toleo la chini " + version;
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "Mod " + mod + " inahitaji " + dependencia + " toleo " + version;
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {

		return "Mod " + mod + " inahitaji " + dependencia + " kati ya " + min + " na " + max + " (ya sasa: " + actual
				+ ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "Toleo Lisiloendana la Cupboard";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kuganda kumesababishwa na toleo la zamani la Cupboard.</b>"
				+ "<p>Hitilafu inatokea ndani ya <code>ClientConfigCompat.setupNeoforge</code> kwa sababu "
				+ "<code>ModList.get()</code> inarudisha <code>null</code>.</p>"
				+ "<p>Hii ni tatizo linalojulikana katika matoleo ya zamani ya mod <b>Cupboard</b>.</p>"
				+ "<p>Matoleo ya zamani kama <b>3.2</b> yana bug hii.</p>"
				+ "<p><b>Suluhisho:</b> sasisha Cupboard hadi toleo <b>3.5</b> au la baadaye.</p>";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		// TODO Auto-generated method stub
		return "Ni kwa sababu uko kwenye macOS na mchezo unajaribu kutumia OpenGL, ambayo haisupportiwi na matoleo ya hivi karibuni ya macOS. "
				+ "Unahitaji kutumia toleo la Minecraft linalosupport Metal au tumia Linux ikiwa una Mac Intel au M1 au M2 lakini siyo M3+ au Neo.";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "Animation ya GeckoLib Haipatikani";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod haikuweza kupakia faili la animation ya GeckoLib.</b>" + "<p>Faili lililoathirika:</p>"
				+ "<code>" + archivo + "</code>"
				+ "<p>Hitilafu hii hutokea wakati faili la animation <code>.json</code> halipo, "
				+ "lina hitilafu za sintaksia au njina ni batili.</p>" + "<p>Sababu zinazowezekana:</p>" + "<ul>"
				+ "<li>Faili liliondolewa lakini bado linarejelewa katika msimbo.</li>"
				+ "<li>Hitilafu ya sintaksia ndani ya faili la JSON.</li>"
				+ "<li>Njina batili iliyofafanuliwa katika usajili wa mod.</li>"
				+ "<li>Migogoro ya utegemezi au toleo lisiloendana.</li>" + "</ul>";
	}

	@Override
	public String nombreAnimacionGeckoInexiste() {
		return "Animation ya GeckoLib Haipatikani";
	}

	@Override
	public String mensajeAnimacionGeckoInexiste(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod haikuweza kupata faili la animation ya GeckoLib.</b>" + "<p>Faili lililoathirika:</p>" + "<code>"
				+ archivo + "</code>"
				+ "<p>Hitilafu hii hutokea wakati GeckoLib inajaribu kupakia animation ambayo haipo katika njina iliyotajwa. "
				+ "Tofauti na hitilafu ya upakiaji (sintaksia), hitilafu hii inaonyesha kuwa faili halipo kimwili au njina ni potofu.</p>"
				+ "<p>Sababu zinazowezekana:</p>" + "<ul>"
				+ "<li>Faili <code>.json</code> liliondolewa au halikujumuishwa katika JAR ya mwisho ya mod.</li>"
				+ "<li>Hitilafu ya uandishi katika njina iliyofafanuliwa katika msimbo (kwa mfano: 'animations' vs 'animaciones').</li>"
				+ "<li>Mkutano kati ya herufi kubwa na ndogo (mfumo wa uendeshaji wa seva ni Linux (unatofautisha) na maendeleo yalifanywa kwenye Windows (hautofautishi)).</li>"
				+ "<li>Mod haijasasishwa kabisa au utegemezi wake umevunjika.</li>" + "</ul>";
	}

	@Override
	public String nombreRegistroDuplicadoObjeto() {
		return "Mgogoro wa Usajili Uliorudiwa";
	}

	@Override
	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal: Solo el texto descriptivo lleva el color de error
		String mensajeBase = "<span style='color:#" + color
				+ "'>Mgogoro mkubwa: Jaribio la kusajili kitu mara mbili limefanywa. " + "Mods </span>" + mod1
				+ "<span style='color:#" + color + "'> na </span>" + mod2 + "<span style='color:#" + color
				+ "'> wanajaribu kusajili kitu kilekile. " + "Kitu chenye mgogoro: </span>" + objeto + "<br><br>";

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Hii mara nyingi hutokea wakati mods mbili tofauti zinaongeza kitu chenye jina sawa "
				+ "katika namespace ileile, au wakati kuna hitilafu katika msimbo wa moja ya mods.<br><br>"
				+ "<b>Suluhisho linalopendekezwa:</b><br>" + "<ul>"
				+ "<li>Angalia ikiwa moja ya mods ni sasisho au tawi (fork) la nyingine.</li>"
				+ "<li>Jaribu kufuta moja ya mods mbili zenye mgogoro.</li>"
				+ "<li>Angalia faili za mipangilio za mods zote mbili kuona ikiwa unaweza kubadilisha ID ya kitu.</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreFalloFabricRenderingAPI() {
		return "Fabric Rendering API Haipo";
	}

	@Override
	public String mensajeFalloFabricRenderingAPI() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color
				+ "'>Mod (kwa kawaida Porting Lib au tegemezi zake) imeshindwa kwa sababu </span>"
				+ "Fabric Rendering API<span style='color:#" + color + "'> haipatikani.</span><br><br>";

		// Instrucciones de reparación (Actualizadas para versiones modernas donde
		// Indium es obsoleto)
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Suluhisho linalopendekezwa:</b><br>"
				+ "Ujumbe unapendekeza kusakinisha Indium, lakini mod hii imepitwa na wakati katika matoleo ya kisasa ya mchezo.<br>"
				+ "<ul>"
				+ "<li><b>Sasisha Sodium</b> hadi toleo <b>0.6.0</b> au la juu zaidi (toleo hili linajumuisha usaidizi unaohitajika).</li>"
				+ "<li>Hakikisha uma <b>Fabric API</b> imesakinishwa ikiwa bado huna.</li>"
				+ "<li>Ikiwa unatumia toleo la zamani la mchezo (1.20.6 au chini), basi sakinisha Indium.</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRestriccionesDependenciaNoCumplidas() {
		return "Vikwazo vya Utegemezi Havijatekelezwa";
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>Vikwazo </span>" + cantidad + "<span style='color:#"
				+ color + "'> vya utegemezi ambavyo havijatekelezwa vimepatikana.</span><br><br>";

		// Construcción de la lista de conflictos
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictos != null && !conflictos.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Migogoro imegunduliwa katika faili zifuatazo:</span><br><ul>");
			for (String[] par : conflictos) {
				String dep = par[0]; // Dependencia
				String jar = par[1]; // Archivo JAR
				// Variable en color por defecto, texto fijo en color error
				listaDetalle.append("<li>").append("<span style='color:#").append(color).append("'>Faili: </span>")
						.append(jar).append("<br><span style='color:#").append(color).append("'>Linahitaji: </span>")
						.append(dep).append("</li>");
			}
			listaDetalle.append("</ul><br>");
		}

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Hii hutokea wakati mods mbili au zaidi zinahitaji matoleo tofauti na yasiyoendana ya maktaba ileile ya ndani.<br><br>"
				+ "<b>Suluhisho linalopendekezwa:</b><br>" + "<ul>"
				+ "<li>Jaribu kusasisha au kufuta mods zilizoorodheshwa hapo juu kutatua kutokuendana.</li>"
				+ "<li>Ikiwa hautapata toleo linaloendana, unaweza kujaribu kuhariri kwa mikono faili <code>mods.toml</code> ndani ya faili la JAR la mod (kwa kutumia compress kama WinRAR au 7-Zip) kubadilisha au kufuta kikwazo cha toleo, ingawa hii inaweza kusababisha kutokuwa na utulivu.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>Vikwazo </span>" + cantidad + "<span style='color:#"
				+ color + "'> vya utegemezi ambavyo havijatekelezwa vimepatikana.</span><br><br>";

		// Construcción de la lista agrupada por Mod
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictosPorMod != null && !conflictosPorMod.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Mods zinazohusika na utegemezi unaohitajika:</span><br><ul>");

			for (Map.Entry<String, List<String>> entry : conflictosPorMod.entrySet()) {
				String archivo = entry.getKey();
				List<String> dependencias = entry.getValue();

				// Nombre del Mod (color por defecto)
				listaDetalle.append("<li><b>").append(archivo).append("</b>");

				// Lista de dependencias para este mod
				listaDetalle.append("<ul>");
				for (String dep : dependencias) {
					// Dependencia (color por defecto)
					listaDetalle.append("<li>").append(dep).append("</li>");
				}
				listaDetalle.append("</ul></li>");
			}
			listaDetalle.append("</ul><br>");
		} else {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Haikuweza kubainisha faili mahususi kutoka kwenye logi.</span><br>");
		}

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Hitilafu hii hutokea wakati mods zinajumuisha matoleo ya ndani ya maktaba (JarInJar) ambayo hayaoendani.<br><br>"
				+ "<b>Suluhisho linalopendekezwa:</b><br>" + "<ul>"
				+ "<li>Angalia orodha iliyo hapo juu kutambua ni mods zipi zinaomba matoleo tofauti ya maktaba ileile.</li>"
				+ "<li>Jaribu kusasisha mods zote mbili hadi matoleo yao ya hivi karibuni.</li>"
				+ "<li>Kama njia ya mwisho, unaweza kufungua faili <code>.jar</code> la mod kwa kutumia compress (kama WinRAR), kuhariri <code>META-INF/mods.toml</code> na kubadilisha kwa mikono mipango ya toleo la utegemezi, ingawa hii ni hatari na inaweza kuvunja mod.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String nombreNeruinaOcultaAdvertencia() {
		return "Neruina Inazuia Utatuzi";
	}

	@Override
	public String mensajeNeruinaOcultaAdvertencia() {
		String color = Config.obtenerInstancia().obtenerColorAdvertencia();

		// Advertencia principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Onyo:</b> Mod <b>Neruina</b> inashindwa kushughulikia hitilafu, jambo linaloficha sababu halisi ya kuganda.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Neruina mara nyingi siyo muhimu na hufanya iwe vigumu kujua ni nini kinachoshindwa hasa. Inapendekezwa kuifuta kwa ajili ya utatuzi.</span><br><br>";

		// Instrucciones de recuperación
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Maelekezo ya urejeshaji:</b><br>"
				+ "1. **MCForge**: Nenda '[jina_la_dunia]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: Nenda 'config/neoforge-server.toml'.<br>"
				+ "   *(Angalia: Katika michezo ya ndani/Singleplayer, dunia ziko kwenye folda 'saves')*.<br>"
				+ "3. Badilisha **removeErroringBlockEntities** na **removeErroringEntities** kuwa **true**.<br><br>"
				+ "<b>Chaguo nyingine:</b><br>"
				+ "- **MCEdit**: Tumia kufuta kwa mikono entity katika kuratibu zilizotajwa.<br>"
				+ "- Ikiwa hitilafu hii itaendelea, inawezekana Neruina haifanyi kazi vizuri na inaunda tu hitilafu mpya."
				+ "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreApothicAttributeSinDueño() {
		return "Hitilafu ya Apothic Attributes";
	}

	@Override
	public String mensajeApothicAttributeSinDueño(boolean chestCavityDetectado) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Apothic Attributes</b> imegundua mgogoro: <b>AttributeMap</b> ilirekebishwa bila mmiliki aliyepewa.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Hii mara nyingi hutokea wakati mod inajaribu kurekebisha sifa za entity (kama maisha, uharibifu, kasi) "
				+ "wakati usiofaa au kwa njia isiyo sahihi.</span><br><br>";

		// Nota específica sobre Chest Cavity
		String notaChestCavity = "";
		if (chestCavityDetectado) {
			notaChestCavity = "<span style='color:#" + color + "'>"
					+ "<b>Mod ya Chest Cavity imegunduliwa katika logi.</b> "
					+ "Mod hii ni chanzo cha kawaida cha hitilafu hii mahususi kutokana na jinsi inavyoshughulikia sifa za entities.</span><br><br>";
		}

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Suluhisho linalopendekezwa:</b><br>" + "<ul>"
				+ "<li>Ikiwa Chest Cavity imesakinishwa, jaribu kuisasisha au kuifuta kwa muda kuthibitisha ikiwa ndiyo chanzo.</li>"
				+ "<li>Angalia ikiwa kuna mods nyingine zinazorekebisha sifa za mobs na jaribu kuzizima.</li>"
				+ "<li>Tafuta visasisho vya <b>Apothic Attributes</b> kwani inaweza kuwa hitilafu iliyorekebishwa katika matoleo ya hivi karibuni.</li>"
				+ "</ul></span>";

		return mensajeBase + notaChestCavity + instrucciones;
	}

	@Override
	public String nombreErrorPotBlockEntity() {
		return "Hitilafu ya DecoratedPot (Cataclysm)";
	}

	@Override
	public String mensajeErrorPotBlockEntity() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Hitilafu ya kutokuendana imetokea na <b>DecoratedPotBlockEntity</b>.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Hii ni tatizo linalojulikana katika toleo 1.19.2 la mod <b>L_Enders_Cataclysm</b>, "
				+ "ambapo utekelezaji unaohitajika na mchezo haupo.</span><br><br>";

		// Solución
		String solucion = "<span style='color:#" + color + "'>" + "<b>Suluhisho linalopendekezwa:</b><br>"
				+ "Sakinisha mod <b>PotFix (Cataclysm Patch)</b> kurekebisha hitilafu hii.<br>"
				+ "Unaweza kuipakua hapa: <a href='https://www.curseforge.com/minecraft/mc-mods/potfix-cataclysm-patch'>CurseForge - PotFix</a>"
				+ "</span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorPreloadingTricks() {
		return "Hitilafu ya Preloading Tricks";
	}

	@Override
	public String mensajeErrorPreloadingTricks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Mgogoro uliosababishwa na <b>Preloading Tricks</b> umegunduliwa.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Hitilafu <i>ClassCastException: String cannot be cast to ModuleDescriptor</i> "
				+ "inaonyesha kuwa mod inabadilisha madarasa ya mfumo wa moduli wa Java kwa njia isiyo sahihi.</span><br><br>";

		// Explicación y Solución
		String explicacion = "<span style='color:#" + color + "'>"
				+ "<b>Preloading Tricks</b> ni mod iliyoundwa hasa kwa ajili ya <b>wasanidi programu</b>. "
				+ "Hufanya shughuli ngumu za kubadilisha madarasa (mixins) katika hatua ya mapema sana ya upakiaji wa mchezo, "
				+ "jambo ambalo linaweza kuvunja utulivu kwa urahisi ikiwa kuna mwingiliano mwingine.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>Suluhisho linalopendekezwa:</b><br>" + "<ul>"
				+ "<li>Ondoa mod <b>Preloading Tricks</b>. Kwa kawaida haihitajiki kwa ajili ya kucheza kwenye seva za umma au pakiti thabiti.</li>"
				+ "<li>Ikiwa wewe ni msanidi programu na unaihitaji kwa majaribio, angalia usanidi wako wa mazingira.</li>"
				+ "</ul></span>";

		return mensajeBase + explicacion;
	}

	@Override
	public String nombreErrorSimpleRadioLexiconfig() {
		return "Kutokuendana Simple Radio / Lexiconfig";
	}

	@Override
	public String mensajeErrorSimpleRadioLexiconfig() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Mgogoro kati ya <b>Simple Radio</b> na <b>Lexiconfig</b> umegunduliwa.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Hitilafu inatokea wakati wa mchakato wa 'shelveLexicons', jambo linaonyesha kutokuendana kwa binary kati ya maktaba hizi mbili.</span><br><br>";

		// Solución específica
		String solucion = "<span style='color:#" + color + "'>" + "<b>Sababu inayojulikana:</b><br>"
				+ "Simple Radio mara nyingi huundwa kwa ajili ya matoleo ya zamani ya Lexiconfig, wakati una toleo la hivi karibuni lililosakinishwa.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>Suluhisho linalopendekezwa:</b><br>" + "<ul>"
				+ "<li>Jaribu kutumia toleo la zamani la <b>Lexiconfig</b>.</li>"
				+ "<li>Inapendekezwa kujaribu toleo <b>1.3.11</b> au la zamani, ambazo mara nyingi huenda na Simple Radio.</li>"
				+ "<li>Ikiwa tatizo litaendelea, angalia ikiwa kuna sasisho la Simple Radio linalopatikana.</li>"
				+ "</ul></span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorMobAITweaks() {
		return "Hitilafu ya Mob AI Tweaks";
	}

	@Override
	public String mensajeErrorMobAITweaks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Hitilafu inayohusiana na <b>Mob AI Tweaks</b> imegunduliwa.</span><br><br>" + "<span style='color:#"
				+ color + "'>"
				+ "Hitilafu inatoka kwenye Mixin (<code>$mob-ai-tweaks$onSpawned</code>) ambayo ingilia kazi "
				+ "wakati entity inaonekana (spawns). Hii mara nyingi huonyesha mgogoro na mod nyingine "
				+ "ambayo pia hubadilisha tabia ya kuonekana kwa mobs.</span><br><br>";

		// Solución
		String solucion = "<span style='color:#" + color + "'><b>Suluhisho linalopendekezwa:</b><br>" + "<ul>"
				+ "<li>Jaribu kufuta <b>Mob AI Tweaks</b> kuona kama kutokuwa na utulivu kutapotea.</li>"
				+ "</ul></span>";

		return mensajeBase + solucion;
	}

	public String nombre_verificacion_gpu() {
		return "Uhakiki wa GPU (OpenGL / Uchaguzi wa GPU)";
	}

	public String desactivar_parche_gpu() {
		return "Zima uhakiki wa GPU";
	}

	// ==================== CRASH ====================

	public String gpu_crash_posible() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Kigunduzi cha GPU kinaweza kusababisha kufungwa kwa mchezo.</b>";
	}

	public String gpu_crash_causas() {
		return "Uhakiki ulianza lakini haukukamilika. Hii mara nyingi huonyesha kushindwa kwa OpenGL au madereva ya grafiki.<br><br>"
				+ "Sababu zinazowezekana:<br>" + "- Madereva yamepitwa na wakati au hayana utulivu<br>"
				+ "- Matatizo na OpenGL<br>" + "- GPUs za zamani au mipangilio changamano (hybrid)";
	}

	public String gpu_crash_recomendaciones() {
		return "Mapendekezo:<br>" + "- Sasisha madereva ya GPU<br>"
				+ "- Lazimisha matumizi ya GPU maalum (dedicated)<br>"
				+ "- Epuka mazingira ya mbali au yaliyowekwa virtual";
	}

	// ==================== NO ÓPTIMA ====================

	public String gpu_no_optima() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Mchezo hautumii GPU bora inayopatikana.</b>";
	}

	public String gpu_no_optima_detalles() {
		return "Hii inaweza kupunguza utendaji (FPS chini), lakini kawaida hausababishi kuganda peke yake.";
	}

	public String gpu_recomendaciones_rendimiento() {
		return "Mapendekezo:<br>" + "- Lazimisha GPU maalum katika paneli ya udhibiti<br>"
				+ "- Sanidi Java/Minecraft katika hali ya utendaji wa juu";
	}

	// ==================== GENERALES ====================

	public String gpu_nota_precision() {
		return "<b>Angalia:</b> Mfumo huu wa ugunduzi sio kamili 100%.";
	}

	public String gpu_consumo_energia() {
		return "GPUs zenye nguvu zaidi hutumia nishati nyingi na zinaweza kupunguza muda wa betri kwenye kompyuta ndogo (laptops).";
	}

	public String gpu_parche_info() {
		return "Unaweza kuzima uhakiki huu kwa kutumia kitufe cha suluhisho la haraka.";
	}

	@Override
	public String nombreVerificacionRaptorLake() {
		return "Onyo la Utulivu wa CPU Intel Kizazi cha 13/14";
	}

	@Override
	public String advertenciaRaptorLakeTitulo() {
		return "Kutokuwa na utulivu kunawezekana kwenye procesa ya Intel Raptor Lake";
	}

	@Override
	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String targetMicrocode) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Procesa " + cpu + " yenye mikrokodi "
				+ microcode + " imegunduliwa." + "</b> "
				+ "Procesa za Intel za kizazi cha 13 na 14 zimeonyesha matatizo ya kutokuwa na utulivu kutokana na voltage ya ziada inayoombwa, "
				+ "ambayo inaweza kupunguza maisha ya procesa.<br><br>"
				+ "Inapendekezwa kusasisha mikrokodi au BIOS ya bodi yako mama hadi toleo linalojumuisha mikrokodi <b>"
				+ targetMicrocode + "</b> au ya juu zaidi. "
				+ "<b>Onyo:</b> Kusasisha BIOS kuna hatari ikiwa haitafanywa vizuri.<br><br>"
				+ "<i>Angalia: Hii karibu hakika SIYO sababu ya kuganda kwako kwa sasa, ni onyo tu la habari kuhusu afya ya vifaa.</i>";
	}

	@Override
	public String desactivarVerificacionRaptorLake() {
		return "Usinionye tena kuhusu hili";
	}

	@Override
	public String verArticuloRaptorLake(String fuente) {
		return "Soma makala katika " + fuente;
	}

	@Override
	public String tituloMixins() {
		return "Kichunguzi cha Mixins";
	}

	@Override
	public String mixinsBotonLateral() {
		return "Mixins";
	}

	@Override
	public String mixinsRaiz() {
		return "Mixins Zilizopatikana";
	}

	@Override
	public String mixinsTodosLosMods() {
		return "Zote";
	}

	@Override
	public String mixinsModConMixin() {
		return "Mod yenye Mixins";
	}

	@Override
	public String mixinsTooltipCombo() {
		return "Chuja kwa mod iliyo na mixins";
	}

	@Override
	public String mixinsRecargar() {
		return "Pakia Upya";
	}

	@Override
	public String mixinsDescompilarSeleccion() {
		return "Tengua Msimbo wa Uteuzi";
	}

	@Override
	public String mixinsTargets() {
		return "Malengo";
	}

	@Override
	public String mixinsTarget() {
		return "Lengo";
	}

	@Override
	public String mixinsTargetsMetodo() {
		return "Malengo ya Njia";
	}

	@Override
	public String mixinsMetodos() {
		return "Njia";
	}

	@Override
	public String mixinsCampos() {
		return "Uga";
	}

	@Override
	public String mixinsCantidad() {
		return "Idadi ya Mixins";
	}

	@Override
	public String mixinsDetalleMixin() {
		return "Maelezo ya Mixin";
	}

	@Override
	public String mixinsDetalleTarget() {
		return "Maelezo ya Lengo";
	}

	@Override
	public String mixinsDetalleMetodo() {
		return "Maelezo ya Njia ya Mixin";
	}

	@Override
	public String mixinsDetalleCampo() {
		return "Maelezo ya Uga wa Mixin";
	}

	@Override
	public String mixinsDetalleConflicto() {
		return "Maelezo ya Mgogoro";
	}

	@Override
	public String mixinsBuscarConflictos() {
		return "Tafuta Migogoro Inayowezekana";
	}

	@Override
	public String mixinsResultadosConflictos() {
		return "Matokeo ya Migogoro";
	}

	@Override
	public String mixinsConflictosPosibles() {
		return "Migogoro Inayowezekana";
	}

	@Override
	public String mixinsErrorDescompilar() {
		return "Hitilafu Wakati wa Kutengua Msimbo";
	}

	@Override
	public String mixinsColorPanel() {
		return "Rangi ya Paneli ya Mixins";
	}

	@Override
	public String mixinsColorTexto() {
		return "Rangi ya Maandishi ya Mixins";
	}

	@Override
	public String mixinsColorTextoSuave() {
		return "Rangi ya Maandishi ya Pili ya Mixins";
	}

	@Override
	public String mixinsAyudaUso1() {
		return "Zana hii inaonyesha mods zenye mixins za SpongePowered na huruhusu kuchunguza madarasa yake, malengo, njia na uga.";
	}

	@Override
	public String mixinsAyudaUso2() {
		return "Tumia kiteuzi cha juu kuchuja kwa mod mahususi au kuonyesha mods zote zenye mixins.";
	}

	@Override
	public String mixinsAyudaUso3() {
		return "Panua mti kuona kila mixin, madarasa yake yanayolengwa, njia zilizowekewa alama na uga wa kivuli (shadow fields).";
	}

	@Override
	public String mixinsAyudaUso4() {
		return "Bofya kulia kwenye mod, mixin, lengo, njia au uga kutafuta migogoro inayowezekana na mixins nyingine.";
	}

	@Override
	public String mixinsAyudaUso5() {
		return "Unaweza kutengua msimo wa uteuzi wa sasa au matokeo ya mgogoro kuchunguza msimo unaohusiana.";
	}

	@Override
	public String mixinsColorComboFondo() {
		return "Rangi ya Mandhari ya Kiteuzi cha Mods";
	}

	@Override
	public String mixinsColorAreaContenidoFondo() {
		return "Rangi ya Mandhari ya Paneli ya Maelezo";
	}

	@Override
	public String mixinsColorSeleccionTexto() {
		return "Rangi ya Uteuzi wa Maandishi";
	}

	@Override
	public String mixinsColorSeleccionTextoActivo() {
		return "Rangi ya Maandishi Yaliyochaguliwa";
	}

	@Override
	public String mixinsColorAyudaTexto() {
		return "Rangi ya Maandishi ya Msaada";
	}

	@Override
	public String mixinsColorArbolFondo() {
		return "Rangi ya Mandhari ya Mti";
	}

	@Override
	public String mixinsColorRendererSeleccionTexto() {
		return "Rangi ya Maandishi Yaliyochaguliwa ya Mti";
	}

	@Override
	public String mixinsColorRendererSeleccionFondo() {
		return "Rangi ya Mandhari Iliyochaguliwa ya Mti";
	}

	@Override
	public String mixinsColorRendererBordeSeleccion() {
		return "Rangi ya Mpaka wa Uteuzi wa Mti";
	}

	@Override
	public String depmapTitulo() {
		return "Ramani ya Utegemezi";
	}

	@Override
	public String depmapBotonLateral() {
		return "DepMap";
	}

	@Override
	public String depmapPestanaMapa() {
		return "Ramani";
	}

	@Override
	public String depmapPestanaDependientes() {
		return "Wategemezi";
	}

	@Override
	public String depmapRecargar() {
		return "Pakia Upya";
	}

	@Override
	public String depmapDescompilarSeleccion() {
		return "Tengua Msimbo wa Uteuzi";
	}

	@Override
	public String depmapVerReferencias() {
		return "Tazama Marejeleo";
	}

	@Override
	public String depmapDependencias() {
		return "Utegemezi";
	}

	@Override
	public String depmapDependientes() {
		return "Wategemezi";
	}

	@Override
	public String depmapDependiente() {
		return "Mtegemezi";
	}

	@Override
	public String depmapSinDependencias() {
		return "Hakuna Wategemezi";
	}

	@Override
	public String depmapSeleccionarMod() {
		return "Chagua Mod";
	}

	@Override
	public String depmapSeleccionarModBase() {
		return "Mod Msingi";
	}

	@Override
	public String depmapSeleccionarDependiente() {
		return "Mod Mtegemezi";
	}

	@Override
	public String depmapSeleccionarPaquete() {
		return "Pakiti";
	}

	@Override
	public String depmapComprobarNoAlineadas() {
		return "Angalia Zisizolingana";
	}

	@Override
	public String depmapResultadoNoAlineadas() {
		return "Matokeo ya Utegemezi Usiolingana";
	}

	@Override
	public String depmapClaseInexistente() {
		return "Darasa Halipo";
	}

	@Override
	public String depmapClaseReferenciada() {
		return "Darasa Lililotajwa";
	}

	@Override
	public String depmapOrigen() {
		return "Chanzo";
	}

	@Override
	public String depmapDestino() {
		return "Lengo";
	}

	@Override
	public String depmapDependenciaDetalle() {
		return "Maelezo ya Utegemezi";
	}

	@Override
	public String depmapReferenciaDetalle() {
		return "Maelezo ya Marejeleo";
	}

	@Override
	public String depmapMetodoOrigen() {
		return "Njia Chanzo";
	}

	@Override
	public String depmapModBase() {
		return "Mod Msingi";
	}

	@Override
	public String depmapTodos() {
		return "Zote";
	}

	@Override
	public String depmapSeleccioneUnMod() {
		return "Chagua Mod";
	}

	@Override
	public String depmapSeleccioneParametrosNoAlineadas() {
		return "Chagua mod msingi, mtegemezi na pakiti";
	}

	@Override
	public String depmapSeleccioneClaseParaDescompilar() {
		return "Chagua marejeleo au ugunduzi wa kutengua msimo";
	}

	@Override
	public String depmapErrorDescompilar() {
		return "Hitilafu Wakati wa Kutengua Msimbo";
	}

	@Override
	public String depmapAyuda1() {
		return "Zana hii huunda ramani ya utegemezi kati ya mods kwa kutumia marejeleo ya madarasa kati yake.";
	}

	@Override
	public String depmapAyuda2() {
		return "Kichupo cha ramani kinaonyesha grafu ya maputo yenye kila mod ikiunganishwa na utegemezi unaoitumia.";
	}

	@Override
	public String depmapAyuda3() {
		return "Kichupo cha wategemezi kinapanga mods kutoka kwa ile yenye wategemezi wengi hadi ile isiyo na wowote.";
	}

	@Override
	public String depmapAyuda4() {
		return "Unaweza kuchunguza marejeleo mahususi kati ya mod na utegemezi wake, pamoja na kutengua misimo ya madarasa yanayohusiana.";
	}

	@Override
	public String depmapAyuda5() {
		return "Uhakiki wa utegemezi usiolingana hutafuta marejeleo ya madarasa yasiyopo ndani ya pakiti au pakiti ndogo ya mod msingi.";
	}

	@Override
	public String depmapColorPanel() {
		return "Rangi ya Paneli";
	}

	@Override
	public String depmapColorTexto() {
		return "Rangi Kuu ya Maandishi";
	}

	@Override
	public String depmapColorTextoSecundario() {
		return "Rangi ya Pili ya Maandishi";
	}

	@Override
	public String depmapColorAyudaTexto() {
		return "Rangi ya Maandishi ya Msaada";
	}

	@Override
	public String depmapColorGrafoFondo() {
		return "Rangi ya Mandhari ya Grafu";
	}

	@Override
	public String depmapColorListaFondo() {
		return "Rangi ya Mandhari ya Orodha";
	}

	@Override
	public String depmapColorArbolFondo() {
		return "Rangi ya Mandhari ya Mti";
	}

	@Override
	public String depmapColorNodo() {
		return "Rangi ya Nodi za Grafu";
	}

	@Override
	public String depmapColorEnlace() {
		return "Rangi ya Viungo vya Grafu";
	}

	@Override
	public String depmapColorSeleccion() {
		return "Rangi ya Uteuzi";
	}

	@Override
	public String depmapColorSeleccionTexto() {
		return "Rangi ya Maandishi Yaliyochaguliwa";
	}

	@Override
	public String nombreProblemaAzureLibAnimaciones() {
		return "Tatizo na Addon ya AzureLib";
	}

	@Override
	public String mensajeProblemaAzureLibAnimaciones() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu ya AzureLib imegunduliwa wakati wa kupakia animations.</b>"
				+ "<p>Mchezo umepata JSON ya animations yenye muundo usio sahihi.</p>"
				+ "<p>Tatizo hili mara nyingi husababishwa na moja ya mods au addons zinazotumia <b>AzureLib</b>.</p>"
				+ "<p><b>Mapendekezo:</b></p>" + "<ul>"
				+ "<li>Tumia <b>DepMap</b> kwenye upau wa pembeni kupata addons zote zinazotegemea AzureLib.</li>"
				+ "<li>Jaribu kuanzisha mchezo bila baadhi ya addons hizo hadi utambue ile inayoshindwa.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreProblemaDecocraftNatureEssentialPartnerMod() {
		return "Tatizo na Decocraft Nature";
	}

	@Override
	public String mensajeProblemaDecocraftNatureEssentialPartnerMod() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Tatizo lililosababishwa na Decocraft Nature limegunduliwa.</b>"
				+ "<p>Hitilafu inatokea wakati wa kuanzisha mipangilio ya mixin "
				+ "<code>com/razz/essentialpartnermod/mixins.json</code>.</p>"
				+ "<p>Tatizo hili linaweza kurekebishwa kwa kuhariri faili la JAR la mod.</p>" + "<p><b>Hatua:</b></p>"
				+ "<ul>"
				+ "<li>Fungua faili la JAR kwa kutumia kihifadhi kama File Roller, Ark, WinRAR, 7-Zip au WinZip.</li>"
				+ "<li>Nenda <code>META-INF/MANIFEST.MF</code>.</li>" + "<li>Futa mstari huu:</li>" + "</ul>"
				+ "<code>MixinConfigs: com/razz/essentialpartnermod/mixins.json</code>"
				+ "<p><b>Muhimu:</b> uhifadhi mstari mmoja tupu mwishoni mwa faili.</p>";
	}

	@Override
	public String mensajeTetraDeserializadorModeloEstatico() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu imegunduliwa katika Tetra au moja ya addons zake.</b>"
				+ "<p>Logi inaonyesha kuwa hakuna deserializer iliyopatikana kwa ajili ya aina ya modeli inayotumika na <b>Tetra</b> au mojawapo ya viambatanishi vyake.</p>"
				+ "<p><b>Hatua zinazopendekezwa:</b></p>" + "<ul>"
				+ "<li>Kwanza, ondoa au zima <b>addons za Tetra</b> na ujaribu tena.</li>"
				+ "<li>Ikiwa hitilafu itaendelea, jaribu kuondoa pia <b>Tetra</b>.</li>"
				+ "<li>Unaweza kujaribu kupata addons zinazohusiana na Tetra kwenye <b>DepMap</b>, ingawa sio mara zote zitaonekana hapo.</li>"
				+ "</ul>"
				+ "<p>Katika baadhi ya kesi tatizo linatoka kwenye addon, lakini katika nyingine linasababishwa na <b>Tetra</b> yenyewe.</p>";
	}

	@Override
	public String nombreTetraDeserializadorModeloEstatico() {
		return "Hitilafu ya Deserialization ya Modeli katika Tetra";
	}

	@Override
	public String mensajeSimpleEmotesSetupAnimTail() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu imegunduliwa katika Simple Emotes.</b>"
				+ "<p>Logi ina string <b>$simpleemotes$setupAnimTAIL</b>, ambayo inaelekeza moja kwa moja kwenye mod <b>Simple Emotes</b>.</p>"
				+ "<p><b>Chaguo zinazopendekezwa:</b></p>" + "<ul>"
				+ "<li>Ondoa au zima <b>Simple Emotes</b> na ujaribu tena.</li>"
				+ "<li>Ikiwa unatumia mods zinazobadilisha animations za mchezaji au modeli, angalia kutokuendana kunawezekana na <b>Simple Emotes</b>.</li>"
				+ "<li>Sasisha <b>Simple Emotes</b> na mods zozote zinazohusiana na animations hadi matoleo yanayoendana.</li>"
				+ "</ul>"
				+ "<p>Hitilafu hii mara nyingi huonyesha kuwa <b>Simple Emotes</b> inahusika moja kwa moja katika kushindwa.</p>";
	}

	@Override
	public String nombreSimpleEmotesSetupAnimTail() {
		return "Hitilafu katika Simple Emotes";
	}

	// En Idioma

	@Override
	public String mensajeAdvertenciaSKLauncher() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Onyo kuhusu SKLauncher.</b>"
				+ "<p>Kwa miezi iliyopita, kesi kadhaa za <b>uharibifu</b> na matatizo mengine yanayohusiana na <b>SKLauncher</b> yameonekana.</p>"
				+ "<p>Hii haimaanishi kuwa SKLauncher daima ndiyo sababu ya kuganda, lakini inaweza kuchangia tatizo.</p>"
				+ "<p><b>Ishara za uharibifu unaowezekana:</b></p>" + "<ul>"
				+ "<li>Mchezo unafunga mapema sana wakati wa kuanzishwa.</li>"
				+ "<li>Mchezo pia unashindwa hata <b>bila mods zilizosakinishwa</b>.</li>" + "</ul>"
				+ "<p>Ikiwa moja ya hizi itatokea, jaribu kutumia <b>lanzador nyingine</b> kuona kama tatizo litapotea.</p>"
				+ "<p>Angalia orodha ya lanzadors zinazopendekezwa hapa:</p>"
				+ "<p><a href='https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/docs/ingles/minecraft/Launchers.md'>Tazama nyaraka za lanzadors</a></p>";
	}

	@Override
	public String nombreAdvertenciaSKLauncher() {
		return "Onyo: Matatizo Yanayowezekana na SKLauncher";
	}

	@Override
	public String guardTitulo() {
		return "CD Guard";
	}

	@Override
	public String guardBotonLateral() {
		return "Guard";
	}

	@Override
	public String guardEscanearTodo() {
		return "Changanua Seva na Programu Hasidi";
	}

	@Override
	public String guardEscanearServidores() {
		return "Tafuta Seva";
	}

	@Override
	public String guardEscanearMalware() {
		return "Tafuta Programu Hasidi";
	}

	@Override
	public String guardTablaServidores() {
		return "Seva Zenye Shida";
	}

	@Override
	public String guardTablaMalware() {
		return "Ugunduzi wa Programu Hasidi";
	}

	@Override
	public String guardColServidor() {
		return "Seva";
	}

	@Override
	public String guardColDefinicion() {
		return "Ufafanuzi";
	}

	@Override
	public String guardColMensaje() {
		return "Ujumbe";
	}

	@Override
	public String guardColUbicacion() {
		return "Mahali";
	}

	@Override
	public String guardColClase() {
		return "Darasa";
	}

	@Override
	public String guardColCfr() {
		return "CFR";
	}

	@Override
	public String guardCfr() {
		return "Tengua Msimbo";
	}

	@Override
	public String guardCfrTitulo() {
		return "Msimbo Uliotenguliwa";
	}

	@Override
	public String guardDescripcion1() {
		return "Zana hii huruhusu kutafuta seva zenye shida na ugunduzi unaowezekana wa programu hasidi katika mods.";
	}

	@Override
	public String guardDescripcion2() {
		return "Kunaweza kuwa na chanya uwongo (false positives), hasa wakati ufafanuzi mwingine au viskansha vya programu hasidi vinapotumia nguvu.";
	}

	@Override
	public String guardDescripcion3() {
		return "Uhakiki wa seva hutumia ufafanuzi wa nje. Ikiwa hujazipakua, utahitaji kuzipakua kwanza.";
	}

	@Override
	public String guardDescripcion4() {
		return "Ikiwa tayari una ufafanuzi wa ndani, zana itakuacha uamue ikiwa unataka kuzitumia tena au kuzisasisha.";
	}

	@Override
	public String guardDescripcion5() {
		return "Katika jedwali la programu hasidi, ikiwa darasa linapatikana, utaweza kulitengua na CFR kulichunguza.";
	}

	@Override
	public String guardEstadoEscaneandoTodo() {
		return "Inachanganua seva na programu hasidi...";
	}

	@Override
	public String guardEstadoEscaneandoServidores() {
		return "Inatafuta seva zenye shida...";
	}

	@Override
	public String guardEstadoEscaneandoMalware() {
		return "Inatafuta programu hasidi...";
	}

	@Override
	public String guardEstadoListo() {
		return "Imeandaa";
	}

	@Override
	public String guardDefsNoEncontradasTitulo() {
		return "Ufafanuzi Haukupatikana";
	}

	@Override
	public String guardDefsNoEncontradasMensaje() {
		return "Haupatikani ufafanuzi wa seva. Je, unataka kuupakua sasa?";
	}

	@Override
	public String guardDefsDescargar() {
		return "Pakua";
	}

	@Override
	public String guardDefsCancelar() {
		return "Ghairi";
	}

	@Override
	public String guardDefsActualizarTitulo() {
		return "Ufafanuzi wa Seva";
	}

	@Override
	public String guardDefsActualizarMensaje() {
		return "Tayari kuna ufafanuzi wa ndani. Je, unataka kutumia kama ulivyo au kusasisha?";
	}

	@Override
	public String guardDefsUsarLocales() {
		return "Tumia za Ndani";
	}

	@Override
	public String guardDefsActualizar() {
		return "Sasisha";
	}

	@Override
	public String guardFuenteDefinicionesTLauncher() {
		return "Orodha ya TLauncher";
	}

	@Override
	public String guardErrorDescompilar() {
		return "Hitilafu Wakati wa Kutengua Msimbo";
	}

	@Override
	public String guardColorPanel() {
		return "Rangi ya Paneli";
	}

	@Override
	public String guardColorTexto() {
		return "Rangi ya Maandishi";
	}

	@Override
	public String guardColorTextoSecundario() {
		return "Rangi ya Pili ya Maandishi";
	}

	@Override
	public String guardColorTabla() {
		return "Rangi ya Jedwali";
	}

	@Override
	public String guardColorSeleccion() {
		return "Rangi ya Uteuzi";
	}

	@Override
	public String guardColorSeleccionTexto() {
		return "Rangi ya Maandishi Yaliyochaguliwa";
	}

	@Override
	public String texto_de_boton_compartir_instancia_modpack() {
		return "Shiriki Instansi/Modpack";
	}

	@Override
	public String popup_compartir_instancia_modpack() {
		return "Kipengele cha kushiriki instansi au modpack bado hakijatekelezwa.";
	}

	@Override
	public String colorBotonCompartirVerdeOscuro() {
		return "Rangi Kuu ya Kitufe cha Kushiriki";
	}

	@Override
	public String colorBotonCompartirVerdeClaro() {
		return "Rangi ya Kitufe cha Kushiriki Viungo";
	}

	@Override
	public String colorTextoBotonesCompartir() {
		return "Rangi ya Maandishi ya Vitufe vya Kushiriki";
	}

	@Override
	public String compartirInstanciaTitulo() {
		return "Shiriki Instansi";
	}

	@Override
	public String compartirInstanciaBotonLateral() {
		return "Shiriki Instansi";
	}

	@Override
	public String compartirInstanciaFormato() {
		return "Muundo";
	}

	@Override
	public String compartirInstanciaServicio() {
		return "Huduma ya Kupakia";
	}

	@Override
	public String compartirInstanciaBotonCompartir() {
		return "Funga na Kushiriki";
	}

	@Override
	public String compartirInstanciaBotonRefrescar() {
		return "Onyesha Upya";
	}

	@Override
	public String compartirInstanciaEstadoListo() {
		return "Imeandaa";
	}

	@Override
	public String compartirInstanciaEstadoEmpaquetando() {
		return "Inafunga uteuzi...";
	}

	@Override
	public String compartirInstanciaEstadoSubiendo() {
		return "Inapakia faili...";
	}

	@Override
	public String compartirInstanciaEstadoError() {
		return "Hitilafu";
	}

	@Override
	public String compartirInstanciaCodigo() {
		return "Msimbo";
	}

	@Override
	public String compartirInstanciaEnlace() {
		return "Kiungo";
	}

	@Override
	public String compartirInstanciaMantenerAbierto() {
		return "Unapaswa kuacha programu wazi ili uhamishaji ubaki upatikana.";
	}

	@Override
	public String compartirInstanciaSinSeleccion() {
		return "Hakuna folda au faili zilizochaguliwa.";
	}

	@Override
	public String compartirInstanciaFormatoNoSoportado() {
		return "Muundo huo bado hausupportiwi.";
	}

	@Override
	public String compartirInstanciaServicioNoDisponible() {
		return "Huduma iliyochaguliwa haipatikani.";
	}

	@Override
	public String compartirInstanciaSubidaCompleta() {
		return "Uhamishaji umeanza vizuri.";
	}

	@Override
	public String compartirInstanciaErrorSubir() {
		return "Haikuweza kupakia faili lililochaguliwa.";
	}

	@Override
	public String compartirInstanciaColorPanel() {
		return "Rangi ya Paneli";
	}

	@Override
	public String compartirInstanciaColorTexto() {
		return "Rangi ya Maandishi";
	}

	@Override
	public String compartirInstanciaPolitica1() {
		return "Aina zinazopendekezwa: mods, configs, saves, worlds, datapacks, resource packs na faili za chaguo. Epuka kujumuisha nyenzo za kibinafsi ambazo si muhimu.";
	}

	@Override
	public String compartirInstanciaPolitica2() {
		return "Viendelezi vinaweza kuongeza huduma zao wenyewe za kupakia. Huduma zilizojumuishwa chaguomsingi zinapaswa kuonyeshwa hapa.";
	}

	@Override
	public String compartirInstanciaPolitica3() {
		return "wormhole.app: hadi 5 GiB kama upakuaji wa kawaida; kati ya 5 na 10 GiB inahitaji mtumaji kubaki wazi. Katika utekelezaji wa sasa wa mradi, muunganisho halisi bado unasubiriwa.";
	}

	@Override
	public String compartirInstanciaPolitica4() {
		return "limewire.com: imeundwa kama huduma yenye uhifadhi wa muda mfupi. Bado haijasupportiwa na utekelezaji huu.";
	}

	@Override
	public String compartirInstanciaPolitica5() {
		return "bittorrent: njia salamu zaidi kwa kuwa ni usambazaji wa P2P wa moja kwa moja, bila hosting ya kati. Bado haijasupportiwa na utekelezaji huu.";
	}

	@Override
	public String compartirInstanciaPolitica6() {
		return "Chaguomsingi, folda na faili za kawaida zaidi za instansi huchaguliwa ili kurahisisha usaidizi wa kiufundi.";
	}

	@Override
	public String compartirInstanciaPolitica7() {
		return "Ikiwa utajumuisha folda ya ndani ya " + Statics.nombre_cd.obtener()
				+ ", mipangilio, logi na data ziada pia zitatumwa, kwa hivyo unaweza kuiondoa ikiwa si muhimu.";
	}

	@Override
	public String malware_fracturiser_detectado() {
		return "Fracturiser inawezekana imegunduliwa. Ushahidi:";
	}

	@Override
	public String malware_info_stealer_detectado() {
		return " Mwizi wa taarifa (Info Stealer) anawezekana amegunduliwa. Ushahidi:";
	}

	@Override
	public String malware_evidencia_clase_sospechosa() {
		return "Darasa linaloshukiwa:";
	}

	@Override
	public String malware_evidencia_archivo_sospechoso() {
		return "Faili linaloshukiwa:";
	}

	@Override
	public String malware_brightsdk_detectado() {
		return "Bright SDK inawezekana imegunduliwa. Ushahidi:";
	}

	@Override
	public String malware_evidencia_paquete_sospechoso() {
		return "Pakiti inayoshukiwa:";
	}

	@Override
	public String docsTituloVentana() {
		return "Kisomaji cha Nyaraka";
	}

	@Override
	public String docsAyudaDeUso() {
		return "<b>Jinsi ya kutumia kisomaji hiki</b><br>"
				+ "Chagua lugha chini kuona nyaraka zinazopatikana katika lugha hiyo.<br>"
				+ "Katika paneli ya kushoto unaweza kusogeza kati ya folda na nyaraka.<br>"
				+ "Ukibofya kwenye hati, yaliyomo yataonekana upande wa kulia.<br>"
				+ "Viungo vya ndani vyenye itifaki <b>docs://</b> hufungua nyaraka nyingine ndani ya kisomaji hiki hiki.";
	}

	@Override
	public String docsArbolTitulo() {
		return "Nyaraka";
	}

	@Override
	public String docsVisorTitulo() {
		return "Yaliyomo";
	}

	@Override
	public String docsNoHayDocumentos() {
		return "Hakuna nyaraka kwa lugha hii.";
	}

	@Override
	public String docsNoHayMarkdown() {
		return "Hakuna faili za Markdown zilizopatikana katika lugha hii.";
	}

	@Override
	public String docsDocumentoNoEncontrado() {
		return "Hati iliyoombolewa haikupatikana.";
	}

	@Override
	public String docsErrorAlAbrirDocumento() {
		return "Hitilafu wakati wa kufungua hati:";
	}

	@Override
	public String docsCargando() {
		return "Inapakia nyaraka...";
	}

	@Override
	public String docsImagenNoDisponible() {
		return "Mchoro haupatikani";
	}

	@Override
	public String colorPanelSecundario() {
		return "Rangi ya Paneli ya Pili";
	}

	@Override
	public String colorTextoSuave() {
		return "Rangi Laini ya Maandishi";
	}

	@Override
	public String colorSeleccion() {
		return "Rangi ya Uteuzi";
	}

	@Override
	public String colorFondoDocumento() {
		return "Rangi ya Mandhari ya Hati";
	}

	@Override
	public String iaTituloVentana() {
		return "AI";
	}

	@Override
	public String iaTituloPrincipal() {
		return "Uchambuzi wa AI";
	}

	@Override
	public String iaDescripcionTitulo() {
		return "Wakala wa Uchambuzi wa Kuganda";
	}

	@Override
	public String iaDescripcionCuerpo() {
		return "Zana hii hufungua wakala wa nje ambaye anaweza kukusaidia kuchambua kuganda, hitilafu na logi zinazohusiana na Minecraft.";
	}

	@Override
	public String iaDescripcionUso() {
		return "Ili kutumia mfumo huu, fungua kiungo, ingia kwa akaunti ya Baidu kisha tumia wakala kukagua kuganda kwako au logi zako.";
	}

	@Override
	public String iaAvisoCuentaBaidu() {
		return "Utahitaji akaunti ya Baidu ili kufikia wakala.";
	}

	@Override
	public String iaCopiarEnlace() {
		return "Nakili Kiungo";
	}

	@Override
	public String iaAbrirEnlace() {
		return "Fungua Kiungo";
	}

	@Override
	public String iaImagenNoDisponible() {
		return "Picha haipatikani";
	}

	@Override
	public String mensajeOculusIrisUnknownShaderVariable() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu inayowezekana ya shaders na Oculus au Iris imegunduliwa.</b>"
				+ "<p>Kumbukumbu (log) ina <b>kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal</b> "
				+ "na pia <b>java.lang.RuntimeException: Unknown variable:</b>.</p>"
				+ "<p>Mchanganyiko huu mara nyingi huonyesha tatizo katika kutathmini kibadilishaji (variable) ndani ya shader, "
				+ "mara nyingi kinachohusiana na <b>Oculus</b>, <b>Iris</b>, au <b>shader pack</b> inayotumika.</p>"
				+ "<p><b>Mpango uliopendekezwa wa kujaribu:</b></p>" + "<ul>"
				+ "<li>Kwanza, anza mchezo <b>bila shaders zikiwashwa</b>.</li>"
				+ "<li>Ikiwa tatizo litaendelea, jaribu kuanza <b>bila Oculus au Iris</b>.</li>"
				+ "<li>Sasisha <b>Oculus/Iris</b>, <b>shader pack</b> na mods za grafiki zinazohusiana.</li>"
				+ "<li>Ikiwa unatumia mods nyingine za uchoraji (rendering) au grafiki, angalia kutokubaliana kati yake.</li>"
				+ "</ul>"
				+ "<p>Kwa vitendo, hitilafu hii mara nyingi hutoka kwenye <b>shader pack</b> au mwingiliano wake na <b>Oculus/Iris</b>.</p>";
	}

	@Override
	public String nombreOculusIrisUnknownShaderVariable() {
		return "Hitilafu inayowezekana ya shaders na Oculus/Iris";
	}

	@Override
	public String mensajeItemNoExiste(String itemFaltante, String namespace) {
		String itemHtml = itemFaltante == null || itemFaltante.isEmpty() ? "(haijulikani)" : itemFaltante;
		String namespaceHtml = namespace == null || namespace.isEmpty() ? "(haijulikani)" : namespace;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Iljaribu kutumia kipengee (item) ambacho hakipo.</b>" + "<p>Kumbukumbu ina mstari <b>Item: "
				+ itemHtml + " does not exist</b>.</p>"
				+ "<p>Hii kwa kawaida inamaanisha kuwa <b>datapack</b>, <b>mod</b> au <b>mipangilio</b> fulani "
				+ "inarejelea kipengee ambacho hakipo kwenye mchezo.</p>" + "<p><b>Vitu vya kuangalia:</b></p>" + "<ul>"
				+ "<li>Angalia kama una mod iliyosakinishwa ambayo inapaswa kutoa kipengee <b>" + itemHtml
				+ "</b>.</li>"
				+ "<li>Ikiwa nayo, inaweza kuwa <b>toleo lisilo sahihi</b>, kipengee kimebadilishwa au kufutwa, "
				+ "au mod ina tatizo na ni vyema kuiondoa.</li>"
				+ "<li>Ikiwa huna mod hiyo, jaribu kui<sakinisha</b>.</li>" + "</ul>"
				+ "<p><b>Ili kujua ni mod au datapack ipi inayoomba kipengee hicho:</b></p>" + "<ul>"
				+ "<li>Tumia zana ya <b>grepr</b> kwenye upau wa pembeni.</li>" + "<li><b>Usi</b> chague folda.</li>"
				+ "<li>Washa chaguo la <b>search in archives</b>.</li>"
				+ "<li>Kwenye maandishi ya utafutaji, andika <b>namespace</b>, yaani sehemu iliyo kabla ya koloni: "
				+ "<b>" + namespaceHtml + "</b>.</li>" + "</ul>"
				+ "<p>Hii kwa kawaida husaidia kupata faili, mod au datapack inayofanya rejea isiyo sahihi.</p>";
	}

	@Override
	public String nombreItemNoExiste() {
		return "Kipengee kisichopo kimerejelewa";
	}

	@Override
	public String mensajeCobblemonPinkanIslandsRhyhornModelo() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu ya modeli imetambuliwa kwa Rhyhorn.</b>"
				+ "<p>Kumbukumbu ina mstari <b>Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn</b>.</p>"
				+ "<p>Ijapokuwa modeli inatumia namespace ya <b>Cobblemon</b>, mstari huu mara nyingi husababishwa na mod "
				+ "<b>Cobblemon: Pinkan Islands</b>, ambayo ndipo <b>Rhyhorn</b> huyo anatokana nayo.</p>"
				+ "<p><b>Vitu vya kujaribu:</b></p>" + "<ul>"
				+ "<li>Ondoa au zima <b>Cobblemon: Pinkan Islands</b> na jaribu tena.</li>"
				+ "<li>Sasisha <b>Cobblemon</b> na <b>Cobblemon: Pinkan Islands</b> kwa matoleo yanayolingana.</li>"
				+ "<li>Ikiwa tatizo lilianza baada ya kusasisha moja ya modi hizo, jaribu mchanganyiko wa matoleo tofauti.</li>"
				+ "</ul>"
				+ "<p>Hitilafu hii kwa kawaida huonyesha modeli inayokosekana, isiyoandikishwa vizuri, au isiyolingana ndani ya "
				+ "<b>Cobblemon: Pinkan Islands</b>.</p>";
	}

	@Override
	public String nombreCobblemonPinkanIslandsRhyhornModelo() {
		return "Hitilafu ya modeli ya Rhyhorn katika Cobblemon: Pinkan Islands";
	}

	@Override
	public String mensajeColdSweatInitDynamicTags() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu imetambuliwa katika Cold Sweat.</b>"
				+ "<p>Kumbukumbu ina dalili kama <b>$cold_sweat$onBuildStart</b>, "
				+ "<b>InitDynamicTagsEvent.fillTag</b> na <b>NullPointerException</b> ambapo "
				+ "rejista inaonekana kuwa null.</p>"
				+ "<p>Hii kwa kawaida huonyesha tatizo la <b>Cold Sweat</b> wakati wa kujenga au kujaza "
				+ "<b>tags zinazobadilika (dynamic tags)</b>, mara nyingi kutokana na kutokubaliana, hitilafu ya ndani ya mod "
				+ "au mchanganyiko mgongano na mod nyingine au datapack.</p>" + "<p><b>Vitu vya kujaribu:</b></p>"
				+ "<ul>" + "<li>Ondoa au zima <b>Cold Sweat</b> na jaribu tena.</li>"
				+ "<li>Sasisha <b>Cold Sweat</b> kwa toleo linalolingana na toleo lako la Minecraft na loader yako.</li>"
				+ "<li>Ikiwa unatumia datapacks au modi zinazobadilisha <b>tags</b>, <b>biomes</b>, <b>joto</b> au rejista zinazohusiana, ziangalie pia.</li>"
				+ "<li>Ikiwa hitilafu ilianza baada ya kusasisha modi, jaribu mchanganyiko wa matoleo tofauti.</li>"
				+ "</ul>" + "<p>Katika hali hii, <b>Cold Sweat</b> inahusika moja kwa moja katika hitilafu hiyo.</p>";
	}

	@Override
	public String nombreColdSweatInitDynamicTags() {
		return "Hitilafu ya Cold Sweat katika tags zinazobadilika";
	}

	@Override
	public String mensajeClassCastExceptionGeneral(String lineaClassCast) {
		String detalle = lineaClassCast == null || lineaClassCast.isEmpty() ? ""
				: "<p><b>Mstari uliotambuliwa:</b></p><p><code>" + lineaClassCast + "</code></p>";

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ClassCastException imetambuliwa.</b>"
				+ "<p>Hii inamaanisha kuwa darasa limetibiwa kana kwamba ni darasa lingine au interface isiyolingana.</p>"
				+ detalle + "<p>Aina hii ya hitilafu mara nyingi husababishwa na moja ya hali zifuatazo:</p>" + "<ul>"
				+ "<li><b>Modi mbili zisizolingana</b> kati yao.</li>"
				+ "<li><b>Mixins</b>, <b>transformers</b> au patches zinazobadilisha darasa na kusababisha sehemu nyingine ya mchezo itarajie aina tofauti.</li>"
				+ "<li>Modi nyingine zilizopo kwenye <b>stacktrace</b> ambazo zinasababisha ubadilishaji aina usio sahihi.</li>"
				+ "</ul>" + "<p><b>Vitu vya kuangalia:</b></p>" + "<ul>"
				+ "<li>Angalia mistari ya <b>stacktrace</b> inayohusiana na hitilafu hii.</li>"
				+ "<li>Weka umakini wa pekee kwa majina ya modi au madarasa yenye muundo <b>$modid$algo</b>, kwani mara nyingi huonyesha modi zinazohusika.</li>"
				+ "<li>Jaribu kusasisha, kuondoa, au kutenganisha modi zinazoonekana kuhusiana na ubadilishaji aina usio sahihi.</li>"
				+ "</ul>"
				+ "<p>Ingawa <b>ClassCastException</b> sio ya hatari kila wakati, mara nyingi huwa hivyo.</p>";
	}

	@Override
	public String nombreClassCastExceptionGeneral() {
		return "ClassCastException imetambuliwa";
	}

	@Override
	public String mensajeValkyrienSkiesTournamentLithiumPoiInjection() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена возможная несовместимость между Valkyrien Skies Tournament и Lithium/Radium.</b>"
				+ "<p>Журнал содержит <b>InvalidInjectionException</b>, где миксин "
				+ "<b>Lithium</b> для <b>POI</b> указан вместе с <b>valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager</b>.</p>"
				+ "<p>Эта проблема обычно возникает при использовании <b>старой версии Lithium</b> или "
				+ "<b>форка на базе старой версии Lithium</b>, такого как <b>Radium Reforged</b>, совместно с "
				+ "<b>VS Tournament</b>.</p>" + "<p><b>Что попробовать:</b></p>" + "<ul>"
				+ "<li>Обновите <b>Lithium</b> до более новой и совместимой версии.</li>"
				+ "<li>Если вы на <b>Forge/NeoForge</b> и используете <b>Radium Reforged</b> или другой старый форк, удалите его.</li>"
				+ "<li>Вместо него попробуйте <b>Harium</b> — современный форк Radium, синхронизированный с недавними улучшениями Lithium.</li>"
				+ "<li>Если ошибка началась после обновления модов, проверьте точное сочетание версий между <b>VS Tournament</b> и вашим модом оптимизации AI/POI.</li>"
				+ "</ul>"
				+ "<p>На практике эта ошибка обычно вызвана старой реализацией <b>Lithium/Radium</b>, которая плохо совместима с <b>VS Tournament</b>.</p>";
	}

	@Override
	public String nombreValkyrienSkiesTournamentLithiumPoiInjection() {
		return "Несовместимость VS Tournament с Lithium/Radium";
	}

	@Override
	public String mensajeVSTournamentVSConfigClassNoExiste() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Inaonekana VS Tournament ni ya zamani sana kwa toleo lako la Valkyrien Skies.</b>"
				+ "<p>Kumbukumbu ina <b>NoClassDefFoundError</b> kwa "
				+ "<b>org/valkyrienskies/core/impl/config/VSConfigClass</b> na pia mstari wa "
				+ "<b>org.valkyrienskies.tournament.TournamentMod.init(...)</b>.</p>"
				+ "<p>Hii kwa kawaida inamaanisha kuwa una <b>toleo la zamani la VS Tournament</b> linalojaribu "
				+ "kutumia madarasa ya ndani ya zamani ya <b>Valkyrien Skies</b> ambayo hayapatikani tena.</p>"
				+ "<p><b>Nini cha kufanya:</b></p>" + "<ul>" + "<li>Ondoa <b>VS Tournament</b> ya zamani.</li>"
				+ "<li>Tumia <b>VS Tournament Reforged</b> badala yake.</li>"
				+ "<li>Pia angalia kwamba toleo la <b>Valkyrien Skies</b> linalingana na toleo linalosaidiwa na nyongeza.</li>"
				+ "</ul>"
				+ "<p>Ushauri wa kubadilika kwenda <b>VS Tournament Reforged</b> unalingana na hali ya sasa ya mradi: "
				+ "toleo la awali la Tournament bado limetajwa kama mod ya zamani ya 1.18.2, wakati "
				+ "<b>VS Tournament Reforged</b> inachapishwa peke yake na kwa sasa inatangaza msaada wa Valkyrien "
				+ "2.4.9+ kwenye Forge 1.20.1.</p>";
	}

	@Override
	public String nombreVSTournamentVSConfigClassNoExiste() {
		return "VS Tournament ya zamani haiendi na Valkyrien Skies";
	}

	public String curseForgeClaveApiMundial() {
		return "Mfunguo wa API wa Kimataifa wa CurseForge";
	}

	public String curseForgeEndpoint() {
		return "Endpoint ya CurseForge";
	}

	public String tlmodsEndpoint() {
		return "Endpoint ya TLMods";
	}

	public String minecraftStorageEndpoint() {
		return "Endpoint ya MinecraftStorage";
	}

	public String autoBackupActivado() {
		return "Backup ya kiotomatiki imewashwa";
	}

	public String autoBackupFrecuencia() {
		return "Mara kwa mara ya backup ya kiotomatiki";
	}

	public String autoBackupDiasConservar() {
		return "Siku za kuhifadhi backup ya kiotomatiki";
	}

	public String autoBackupTamanoMaximoMB() {
		return "Ukubwa wa juu wa backup ya kiotomatiki (MB)";
	}

	public String actualizadorModsTitulo() {
		return "Kisasishi cha Modi";
	}

	public String actualizadorModsBotonSidebar() {
		return "Kisasishi";
	}

	public String actualizadorModsDescripcion() {
		return "Hutafuta visasisho vilivyopo kwa modi za modpack. Unaweza kusasisha zote au kutumia visasisho moja kwa moja.";
	}

	public String actualizadorModsBotonEscanear() {
		return "Tafuta visasisho";
	}

	public String actualizadorModsBotonActualizarTodo() {
		return "Sasisha zote";
	}

	public String actualizadorModsBotonActualizarUno() {
		return "Sasisha";
	}

	public String actualizadorModsEstadoListo() {
		return "Tayari";
	}

	public String actualizadorModsEstadoEscaneando() {
		return "Inatafuta visasisho...";
	}

	public String actualizadorModsEstadoActualizando() {
		return "Inasasisha...";
	}

	public String actualizadorModsEstadoSinActualizaciones() {
		return "Hakuna visasisho vilivyopo.";
	}

	public String actualizadorModsEstadoEncontradas(int n) {
		return "Visasisho vilivyopatikana: " + n;
	}

	public String actualizadorModsEstadoActualizadas(int n) {
		return "Visasisho vilivyotumika: " + n;
	}

	public String actualizadorModsEstadoError() {
		return "Hitilafu katika kusasisha.";
	}

	public String actualizadorModsSinSeleccion() {
		return "Hakuna visasisho vilivyochaguliwa.";
	}

	public String actualizadorModsColumnaMod() {
		return "Modi";
	}

	public String actualizadorModsColumnaActual() {
		return "Ya sasa";
	}

	public String actualizadorModsColumnaNueva() {
		return "Mpya";
	}

	public String actualizadorModsColumnaFuente() {
		return "Chanzo";
	}

	public String actualizadorModsColumnaLoader() {
		return "Loader";
	}

	public String actualizadorModsColumnaRuta() {
		return "Njia";
	}

	public String actualizadorModsColumnaAccion() {
		return "Kitendo";
	}

	public String actualizadorModsColorFondo() {
		return "Kisasishi: mandharinyuma";
	}

	public String actualizadorModsColorPanel() {
		return "Kisasishi: paneli";
	}

	public String actualizadorModsColorTexto() {
		return "Kisasishi: maandishi";
	}

	public String actualizadorModsColorTextoSuave() {
		return "Kisasishi: maandishi laini";
	}

	public String actualizadorModsColorBoton() {
		return "Kisasishi: kitufe";
	}

	public String actualizadorModsColorBotonTexto() {
		return "Kisasishi: maandishi ya kitufe";
	}

	public String actualizadorModsColorTabla() {
		return "Kisasishi: jedwali";
	}

	public String actualizadorModsColorSeleccion() {
		return "Kisasishi: uchaguzi";
	}

	public String importadorYumeiriTeExtraniamos() {
		return "Tunakukumbuka, Yumeiri Reyu.";
	}

	public String importadorColorFondo() {
		return "Mingiliano: mandharinyuma";
	}

	public String importadorColorPanel() {
		return "Mingiliano: paneli";
	}

	public String importadorColorTexto() {
		return "Mingiliano: maandishi";
	}

	public String importadorColorTextoSuave() {
		return "Mingiliano: maandishi laini";
	}

	public String importadorColorBoton() {
		return "Mingiliano: kitufe";
	}

	public String importadorColorBotonTexto() {
		return "Mingiliano: maandishi ya kitufe";
	}

	public String importadorColorBorde() {
		return "Mingiliano: mpaka";
	}

	public String importadorConflictoTitulo() {
		return "Mgongano wakati wa kuingiza";
	}

	public String importadorConflictoMensaje() {
		return "Tayari kuna faili katika sehemu ya lengo.";
	}

	public String importadorRuta() {
		return "Njia";
	}

	public String importadorArchivoExistente() {
		return "Faili iliyopo";
	}

	public String importadorArchivoNuevo() {
		return "Faili iliyoingizwa";
	}

	public String importadorTamano() {
		return "Ukubwa";
	}

	public String importadorFecha() {
		return "Marekebisho ya mwisho";
	}

	public String importadorInfoMod() {
		return "Taarifa za mod";
	}

	public String importadorModImportadoMasNuevo() {
		return "Mod iliyoingizwa inaonekana kuwa mpya zaidi.";
	}

	public String importadorModImportadoMasViejo() {
		return "Mod iliyoingizwa inaonekana kuwa ya zamani zaidi.";
	}

	public String importadorBotonReemplazar() {
		return "Badilisha";
	}

	public String importadorBotonSaltar() {
		return "Ruka";
	}

	public String importadorBotonRenombrar() {
		return "Badilisha jina la mpya";
	}

	public String importadorModpackTitulo() {
		return "Ingiza Modpack";
	}

	public String importadorModpackBotonSidebar() {
		return "Ingiza";
	}

	public String importadorModpackDescripcion() {
		return "Inaingiza modpack kwenye mfano wa sasa. Unaweza kuvuta faili ya .zip, .mrpack au umbizo lingine linalosaidiwa, au kuichagua kwa mkono.";
	}

	public String importadorModpackFormato() {
		return "Umbizo";
	}

	public String importadorModpackArrastraArchivo() {
		return "Vuta modpack yako hapa au chagua faili";
	}

	public String importadorModpackBotonSeleccionar() {
		return "Chagua faili";
	}

	public String importadorModpackBotonImportar() {
		return "Ingiza";
	}

	public String importadorModpackSeleccionarArchivo() {
		return "Chagua modpack";
	}

	public String importadorModpackEstadoListo() {
		return "Tayari";
	}

	public String importadorModpackEstadoImportando() {
		return "Inaingiza...";
	}

	public String importadorModpackEstadoError() {
		return "Hitilafu katika kuingiza.";
	}

	public String importadorModpackSinArchivo() {
		return "Chagua faili ya modpack kwanza.";
	}

	public String importadorModpackFormatoNoSoportado() {
		return "Umbizo hili halisaidii uingizaji.";
	}

	public String importadorModpackResultado(int copiados, int reemplazados, int saltados, int renombrados,
			int errores) {
		return "Uingizaji umekamilika.\nZilizokopiwa: " + copiados + "\nZilizobadilishwa: " + reemplazados
				+ "\nZilirukwa: " + saltados + "\nZilibadilishwa jina: " + renombrados + "\nHitilafu: " + errores;
	}

	public String importadorModpackColorFondo() {
		return "Kiingiza modpack: mandharinyuma";
	}

	public String importadorModpackColorPanel() {
		return "Kiingiza modpack: paneli";
	}

	public String importadorModpackColorTexto() {
		return "Kiingiza modpack: maandishi";
	}

	public String importadorModpackColorTextoSuave() {
		return "Kiingiza modpack: maandishi laini";
	}

	public String importadorModpackColorBoton() {
		return "Kiingiza modpack: kitufe";
	}

	public String importadorModpackColorBotonTexto() {
		return "Kiingiza modpack: maandishi ya kitufe";
	}

	public String importadorModpackColorDrop() {
		return "Kiingiza modpack: eneo la kuvuta";
	}

	public String importadorModpackColorBorde() {
		return "Kiingiza modpack: mpaka";
	}

	public String jgitTituloIzzy() {
		return "Kituo cha Git cha Izzy";
	}

	public String jgitRetratoIzzy() {
		return "Izzy";
	}

	public String jgitNoHayRetratoIzzy() {
		return "Hakuna picha ya Izzy";
	}

	public String jgitSeccionInstalacion() {
		return "Usakinishaji wa JGit";
	}

	public String jgitAbrirCarpetaInstalacion() {
		return "Fungua folda ya usakinishaji";
	}

	public String jgitAbrirPaginaDescarga() {
		return "Fungua ukurasa wa kupakua JGit";
	}

	public String jgitSeccionRepositorio() {
		return "Hazina ya ndani";
	}

	public String jgitCrearRepositorioLocal() {
		return "Unda hazina ya Git hapa";
	}

	public String jgitCommitManual() {
		return "Commit ya mkono";
	}

	public String jgitSeccionRemote() {
		return "Remote";
	}

	public String jgitForgeManual() {
		return "Mwongozo wa Forge";
	}

	public String jgitForgePersonalizado() {
		return "Forge maalum";
	}

	public String jgitEstablecerRemoteManual() {
		return "Weka remote kwa mkono";
	}

	public String jgitCrearRemoteConAPI() {
		return "Unda remote kwa kutumia API";
	}

	public String jgitPushManual() {
		return "Push ya mkono";
	}

	public String jgitSeccionAutomaticos() {
		return "Otomatiki";
	}

	public String jgitAutoCommitDespuesBackup() {
		return "Commit otomatiki baada ya backup";
	}

	public String jgitAutoPushDespuesCommit() {
		return "Push otomatiki baada ya commit";
	}

	public String jgitSeccionHerramientas() {
		return "Zana";
	}

	public String jgitAbrirGuiSwing() {
		return "Fungua kioo cha Swing cha JGit";
	}

	public String jgitEstado() {
		return "Hali";
	}

	public String jgitClasspath() {
		return "JGit katika classpath";
	}

	public String jgitTodosLosArtefactos() {
		return "Vitufe vyote vya JGit";
	}

	public String jgitRepositorio() {
		return "Hazina";
	}

	public String jgitRemote() {
		return "Remote";
	}

	public String jgitCarpetaActual() {
		return "Folda ya sasa";
	}

	public String jgitNoSePudoCrearRepo() {
		return "Haikuweza kuunda hazina.";
	}

	public String jgitEscribaRemote() {
		return "Andika URL ya remote:";
	}

	public String jgitNoSePudoGuardarRemote() {
		return "Haikuweza kuhifadhi remote.";
	}

	public String jgitApiForgeAunNoImplementada() {
		return "API ya Forge bado haijatekelezwa.";
	}

	public String jgitNoHayCambiosOError() {
		return "Hakuna mabadiliko ya commit au kulikuwa na hitilafu.";
	}

	public String jgitNoSePudoPush() {
		return "Haikuweza kufanya push.";
	}

	public String jgitTituloVentanaSwing() {
		return "Kioo cha Git";
	}

	public String jgitNoHayRepositorio() {
		return "Hakuna hazina ya Git katika folda hii.";
	}

	public String jgitArchivosModificados() {
		return "Faili zilizobadilishwa";
	}

	public String jgitArchivosNuevos() {
		return "Faili mpya";
	}

	public String jgitUltimosCommits() {
		return "Commits za hivi karibuni";
	}

	public String jgitError() {
		return "Hitilafu ya JGit";
	}

	public String si() {
		return "Ndiyo";
	}

	public String no() {
		return "Hapana";
	}

	public String jgitDescargarDependenciasFaltantes() {
		return "Pakua tegemezi zinazokosekana";
	}

	public String jgitNoFaltanDependencias() {
		return "Hakuna tegemezi za JGit zinazokosekana.";
	}

	public String jgitConfirmarDescargaDependencias(int cantidad) {
		return "Tegemezi " + cantidad + " za JGit zinakosekana. Je, unataka kuzipakua kutoka Maven Central?";
	}

	public String jgitDependenciasDescargadas(int cantidad) {
		return "Tegemezi zilizopakuliwa: " + cantidad;
	}

	public String jgitErroresDescarga() {
		return "Hitilafu za upakuaji";
	}

	public String jgitReiniciarParaCargarClasspath() {
		return "Anzisha tena " + Statics.nombre_cd.obtener() + " ili JARs mpya ziingie kwenye classpath.";
	}

	public String jgitArtefactosFaltantes() {
		return "Vitufe vinavyokosekana";
	}

	public String jgitArtefactosFaltantesClasspath() {
		return "Vitufe vinavyokosekana katika classpath";
	}

	public String jgitArtefactosFaltantesCarpeta() {
		return "Vitufe vinavyokosekana katika folda ya usakinishaji";
	}

	public String jgitDependenciasEnCarpeta() {
		return "Tegemezi zilizosakinishwa kwenye folda";
	}

	public String jgitForgeNoSeleccionada() {
		return "Hakuna forge iliyochaguliwa.";
	}

	public String jgitForgeNoRegistrada(String id) {
		return "Forge haijasajiliwa: " + id;
	}

	public String jgitEscribaUrlForge() {
		return "URL ya forge:";
	}

	public String jgitEscribaNombreRepositorio() {
		return "Jina la hazina:";
	}

	public String jgitEscribaDescripcionRepositorio() {
		return "Maelezo ya hazina:";
	}

	public String jgitEscribaNamespaceOpcional() {
		return "Namespace ya hiari:";
	}

	public String jgitEscribaTokenForge() {
		return "Token ya API ya forge:";
	}

	public String jgitErrorCrearRemote() {
		return "Hitilafu katika kuunda remote";
	}

	@Override
	public String mensajeControlifyRemoveReloadingScreen() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kutokubaliana kumegunduliwa kati ya Controlify na Remove Reloading Screen.</b>"
				+ "<p>Kumbukumbu ina mistari <b>Attempted to fetch default config before DefaultConfigManager was ready!</b> "
				+ "na <b>$rrls$init</b>, ambayo kwa kawaida huonyesha mgongano kati ya <b>Controlify</b> na "
				+ "<b>Remove Reloading Screen</b>.</p>"
				+ "<p><b>Sababu inayowezekana:</b> Remove Reloading Screen hubadilisha sehemu za skrini ya upakiaji au mchakato wa upakiaji, "
				+ "wakati Controlify inajaribu kuanzisha usanidi wake kabla mfumo uwe tayari kabisa.</p>"
				+ "<p><b>Chaguzi zinazopendekezwa:</b></p>" + "<ul>" + "<li>Ondoa <b>Remove Reloading Screen</b>.</li>"
				+ "<li>Au sasisha <b>Controlify</b> na <b>Remove Reloading Screen</b> ikiwa kuna matoleo mapya yanayopatikana.</li>"
				+ "<li>Ikiwa tatizo litaendelea, weka <b>Controlify</b> na uondoe modi yoyote inayobadilisha skrini ya upakiaji.</li>"
				+ "</ul>"
				+ "<p>Modi zinazobadilisha skrini ya upakiaji mara nyingi husababisha kutokubaliana na modi zingine, "
				+ "na kwa kawaida hutoa faida ndogo ikilinganishwa na matatizo ambayo zinaweza kusababisha.</p>";
	}

	@Override
	public String nombreControlifyRemoveReloadingScreen() {
		return "Kutokubaliana: Controlify dhidi ya Remove Reloading Screen";
	}

	@Override
	public String mensajeBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Tatizo linalowezekana na Biomes O' Plenty na maji yaliyobinafsishwa.</b>"
				+ "<p>Kumbukumbu ina hitilafu <b>class org.joml.Vector4f cannot be cast to class "
				+ "net.minecraft.client.renderer.fog.FogData</b> pamoja na rejea kwa <b>Biomes O' Plenty</b>.</p>"
				+ "<p>Hii inawezekana kuhusiana na <b>Biomes O' Plenty</b>, hasa na biomes, ukungu "
				+ "au maji yaliyobinafsishwa. Hata hivyo, si hakika kabisa kwamba Biomes O' Plenty ndio chanzo pekee.</p>"
				+ "<p><b>Chaguzi zinazopendekezwa:</b></p>" + "<ul>"
				+ "<li>Jaribu kuhariri data ya mchezaji kumhamisha hadi mahali pengine ulimwenguni.</li>"
				+ "<li>Jaribu kupakia ulimwengu bila <b>Biomes O' Plenty</b>.</li>"
				+ "<li>Ikiwa ulimwengu utapakia baada ya kumhamisha mchezaji, tatizo linawezekana kutokea katika eneo maalum, "
				+ "biome maalum au maji yaliyobinafsishwa yaliyo karibu.</li>"
				+ "<li>Pia unaweza jaribu kusasisha <b>Biomes O' Plenty</b> na modi zinazohusiana na uchoraji, ukungu, "
				+ "shaders au vipimo.</li>" + "</ul>"
				+ "<p>Ikiwa kuondoa Biomes O' Plenty kunaruhusu kuanza mchezo, angalia ikiwa mchezaji alikuwa ndani au karibu na biome "
				+ "au kiowevu kilichoongezwa na modi hiyo.</p>";
	}

	@Override
	public String nombreBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "Tatizo linalowezekana: Biomes O' Plenty na FogData";
	}

	@Override
	public String mensajeKotlinReflectionInternalErrorVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu ya ndani ya reflection ya Kotlin imegunduliwa.</b>"
				+ "<p>Kumbukumbu ina <b>kotlin.reflect.jvm.internal.KotlinReflectionInternalError</b> na ujumbe unaofanana na "
				+ "<b>Property 'none' not resolved</b>.</p>"
				+ "<p>Aina hii ya hitilafu ni ya kawaida na matoleo fulani ya <b>Fabric Language Kotlin</b> / <b>Kotlin</b>. "
				+ "Katika hali hii, darasa la <b>Inventory Profiles Next</b> linaonekana, lakini tatizo hilo hilo linaweza kutokea "
				+ "na modi nyingine zinazotumia Kotlin.</p>" + "<p><b>Chaguzi zinazopendekezwa:</b></p>" + "<ul>"
				+ "<li>Sasisha <b>Fabric Language Kotlin</b> hadi toleo <b>2.3.40</b>, ikiwa linapatikana kwa toleo lako la Minecraft.</li>"
				+ "<li>Ikiwa kusasisha hakufanyi kazi, jaribu kupunguza <b>Fabric Language Kotlin</b> hadi toleo <b>2.3.10</b>.</li>"
				+ "<li>Pia sasisha <b>Inventory Profiles Next</b> ikiwa kumbukumbu inataja madarasa ya modi hiyo.</li>"
				+ "<li>Ikiwa hitilafu inaonekana na modi nyingine, angalia ikiwa modi hiyo inategemea Kotlin na jaribu kubadilisha toleo la "
				+ "<b>Fabric Language Kotlin</b>.</li>" + "</ul>" + "<p>Marejeo ya kiufundi yanayohusiana: "
				+ "<a href='https://github.com/FabricMC/fabric-language-kotlin/issues/183'>Suala #183 la Fabric Language Kotlin</a>.</p>";
	}

	@Override
	public String nombreKotlinReflectionInternalErrorVersion() {
		return "Hitilafu ya Kotlin: reflection ya ndani";
	}

	public String tituloEscanerMCreator() {
		return "Kichunguzi cha MCreator";
	}

	public String escanerMCreatorEscaneandoMods() {
		return "Inachunguza modi...";
	}

	public String escanerMCreatorPorFavorEspera() {
		return "Tafadhali subiri.";
	}

	public String escanerMCreatorResultadosAnalisis() {
		return "Matokeo ya uchambuzi wa MCreator:";
	}

	public String escanerMCreatorNoSeEncontraronMods() {
		return "Hakuna modi za MCreator zilizopatikana.";
	}

	public String escanerMCreatorEscaneoCompletado() {
		return "Uchunguzi umekamilika.";
	}

	public String escanerMCreatorErrorDuranteEscaneo() {
		return "Hitilafu wakati wa uchunguzi:";
	}

	public String escanerMCreatorCargando() {
		return "Inapakia...";
	}

	public String escanerMCreatorCompletado() {
		return "Imekamilika";
	}

	public String escanerMCreatorError() {
		return "Hitilafu";
	}

	@Override
	public String textoNormal() {
		return "Maandishi ya kawaida";
	}

	@Override
	public String noSeEncontroConsolaParaArchivo() {
		return "Haikupata konsoli ya faili: ";
	}

	@Override
	public String lineaSeleccionadaEnLectador() {
		return "mstari uliochaguliwa katika kisomaji: ";
	}

	@Override
	public String mensajeMotionBlurBufferCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Tatizo linalowezekana na Motion Blur.</b>"
				+ "<p>Logi ina rejea ya <b>net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO</b> "
				+ "na pia kosa <b>java.lang.IllegalStateException: Buffer already closed</b>.</p>"
				+ "<p>Mistari hii inaweza kuonekana ikiwa imetenganishwa katika logi, lakini pamoja mara nyingi huonyesha kwamba tatizo linahusiana "
				+ "na mod <b>Motion Blur</b> au usimamizi wake wa shaders/buffers za grafiki.</p>"
				+ "<p><b>Chaguzi zinazopendekezwa:</b></p>" + "<ul>"
				+ "<li>Jaribu kuanza mchezo bila <b>Motion Blur</b>.</li>"
				+ "<li>Ikiwa mchezo utaanza vizuri bila mod hiyo, iacha imeondolewa au tafuta toleo la hivi karibuni.</li>"
				+ "<li>Pia unaweza kujaribu bila shaders au mod zingine za uchoraji ikiwa tatizo litaendelea.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreMotionBlurBufferCerrado() {
		return "Tatizo linalowezekana: Motion Blur";
	}

	@Override
	public String mensajeGeneratorAcceleratorOwoVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mgongano unaowezekana na Generator Accelerator.</b>"
				+ "<p>Logi ina tofauti kati ya saini za <b>Found</b> na <b>Available</b>, pamoja na darasa kutoka "
				+ "<b>Generator Accelerator</b>, kwa mfano <b>dev/sixik/generator_accelerator/common/features/FastTarget</b>.</p>"
				+ "<p>Kosa hili linaweza kusababishwa na <b>Generator Accelerator</b>. Linaweza pia kuhusiana "
				+ "na kutolingana kati ya mod hiyo na matoleo fulani ya <b>owo-lib</b>.</p>"
				+ "<p><b>Chaguzi zinazopendekezwa:</b></p>" + "<ul>"
				+ "<li>Jaribu kuanza mchezo bila <b>Generator Accelerator</b>.</li>"
				+ "<li>Ikiwa mchezo utaanza vizuri, ondoa mod hiyo au tafuta toleo lingine.</li>"
				+ "<li>Jaribu kusasisha au kubadilisha toleo la <b>owo-lib</b>, hasa ikiwa mod zingine pia zinategemea owo.</li>"
				+ "<li>Hakikisha kwamba <b>Generator Accelerator</b>, <b>owo-lib</b>, loader na toleo la Minecraft zinalingana.</li>"
				+ "</ul>"
				+ "<p>Sababu inayowezekana zaidi ni kwamba Generator Accelerator inajaribu kutumia marekebisho yenye saini "
				+ "ambayo hailingi na toleo la sasa la darasa au utegemezi.</p>";
	}

	@Override
	public String nombreGeneratorAcceleratorOwoVersion() {
		return "Mgongano unaowezekana: Generator Accelerator na owo-lib";
	}

	@Override
	public String mensajeFabricRenderingApiFaltaIndium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kukosa renderer inayolingana na Fabric Rendering API.</b>"
				+ "<p>Logi ina kosa ambapo <b>RendererAccess.getRenderer()</b> inarudisha <b>null</b>, "
				+ "na kusababisha kushindwa wakati wa kujaribu kutumia <b>Renderer.materialFinder()</b>.</p>"
				+ "<p>Tatizo hili mara nyingi hutokea wakati <b>Fabric Rendering API</b> haipatikani vizuri. "
				+ "Sababu ya kawaida ni kutumia <b>Sodium</b>, hasa matoleo ya zamani ambayo hubadilisha au kuzima sehemu "
				+ "za mfumo wa uchoraji unatarajiwa na mod zingine.</p>" + "<p><b>Suluhisho linalopendekezwa:</b></p>"
				+ "<ul>" + "<li>Sakinisha mod <b>Indium</b>.</li>"
				+ "<li>Hakikisha kwamba <b>Indium</b> inalingana na toleo lako la <b>Sodium</b>, Fabric Loader na Minecraft.</li>"
				+ "<li>Ikiwa tayari umesakinisha Indium, sasisha <b>Sodium</b>, <b>Indium</b> na <b>Fabric API</b>.</li>"
				+ "<li>Ikiwa tatizo litaendelea, jaribu kwa muda bila Sodium ili kuthibitisha kwamba kushindwa kunahusiana na renderer.</li>"
				+ "</ul>"
				+ "<p>Indium kawaida hurudisha ulinganifu na Fabric Rendering API kwa mod zinazotegemea mfumo huo "
				+ "wakati Sodium imesakinishwa.</p>";
	}

	@Override
	public String nombreFabricRenderingApiFaltaIndium() {
		return "Kukosa Indium / Fabric Rendering API";
	}

	@Override
	public String mensajeEntradaDuplicadaIdModerno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Ingizo lililorudiwa limegunduliwa katika rejista ya Minecraft.</b>"
				+ "<p>Logi ina kosa linalofanana na <b>Duplicate entry on id</b>, kwa mfano "
				+ "<b>current=maroon, previous=mint</b>.</p>"
				+ "<p>Katika matoleo ya kisasa ya Minecraft, aina hii ya kosa mara nyingi hutokea wakati mod mbili zinajaribu kusajili "
				+ "maingizo tofauti kwa kutumia ID ya ndani ileile.</p>" + "<p><b>Chaguzi zinazopendekezwa:</b></p>"
				+ "<ul>" + "<li>Ondoa moja ya mod inayosajili ingizo lililorudiwa.</li>"
				+ "<li>Ikiwa unatambua majina yaliyotajwa katika kosa, angalia ni mod ipi inayoongeza maingizo hayo na ujaribu bila mod hiyo.</li>"
				+ "<li>Ikiwa hautambui majina, tumia zana ya <b>grepr</b> kwenye upau wa pembeni.</li>"
				+ "<li>Katika <b>grepr</b>, washisha utafutaji ndani ya faili zilizoshindwa <b>.jar</b>, <b>.zip</b> na <b>.fpm</b>.</li>"
				+ "<li>Pia washisha utafutaji katika <b>faili za binary</b>, kwa sababu baadhi ya majina au ID zinaweza kuwa ndani ya darasa zilizokusanywa.</li>"
				+ "</ul>"
				+ "<p>Tafuta thamani zilizotajwa katika kosa, kama <b>maroon</b> au <b>mint</b>, ili kupata ni mod ipi inayozikata.</p>";
	}

	@Override
	public String nombreEntradaDuplicadaIdModerno() {
		return "Ingizo lililorudiwa katika ID ya mod";
	}

	@Override
	public String nombreOpenGLMemoriaInsuficiente() {
		return "OpenGL – kumbukumbu ya video haitoshi";
	}

	@Override
	public String mensajeOpenGLMemoriaInsuficiente() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft ilizalisha hitilafu ya OpenGL kwa sababu ya ukosefu wa kumbukumbu ya grafiki.</b>"
				+ "<p>Mchezo ulirusha:</p>" + "<code>GL_OUT_OF_MEMORY</code>"
				+ "<p>Hii kwa kawaida inamaanisha kuwa kadi ya grafiki au mfumo haikuweza kuhifadhi kumbukumbu ya kutosha kwa textures, shaders, modeli, buffers au athari za kuona.</p>"
				+ "<p><b>Sababu za kawaida:</b></p>" + "<ul>" + "<li>Shaders nzito sana.</li>"
				+ "<li>Vifurushi vya rasilimali (Resource packs) vya azimio la juu.</li>"
				+ "<li>Mod nyingi sana za kuona au za uchoraji.</li>"
				+ "<li>Umbali wa uchoraji (Render distance) mkubwa sana.</li>" + "<li>VRAM kidogo inayopatikana.</li>"
				+ "<li>Dereva za kadi ya video zilizopitwa na wakati au zisizo thabiti.</li>" + "</ul>"
				+ "<p><b>Suluhisho linalopendekezwa:</b></p>" + "<ul>" + "<li>Zima shaders kwa muda.</li>"
				+ "<li>Tumia vifurushi vya rasilimali vya azimio la chini.</li>"
				+ "<li>Punguza umbali wa uchoraji na usimbaji.</li>"
				+ "<li>Punguza ubora wa grafiki, vivuli, chembechembe na mipmaps.</li>"
				+ "<li>Sasisha dereva za kadi ya grafiki.</li>"
				+ "<li>Funga programu zingine zinazotumia GPU au kumbukumbu nyingi.</li>" + "</ul>"
				+ "<p>Ikiwa hitilafu ilianza baada ya kusakinisha shader, kifurushi cha texture au mod ya kuona, kuna uwezekano mkubwa kwamba hiyo ndiyo chanzo.</p>";
	}

	@Override
	public String nombreErrorVerificacionBytecode() {
		return "VerifyError – bytecode au mixin batili";
	}

	@Override
	public String mensajeErrorVerificacionBytecode(String ubicacion, String razon, String claseSospechosa) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft imepata hitilafu ya uthibitishaji wa bytecode.</b>"
				+ "<p>Tatizo hili mara nyingi hutokea wakati udhibiti wa bytecode, transformer au mixin inaposhindwa.</p>"
				+ "<p>Mchezo ulirusha:</p>" + "<code>java.lang.VerifyError</code>"
				+ (ubicacion.length() > 0 ? "<p><b>Mahali:</b></p><code>" + ubicacion + "</code>" : "")
				+ (razon.length() > 0 ? "<p><b>Sababu:</b></p><code>" + razon + "</code>" : "")
				+ "<p><b>Nini cha kutafuta:</b></p>" + "<ul>" + "<li>Tafuta katika mahali pa hitilafu.</li>"
				+ "<li>Tafuta aina iliyotajwa katika <code>Reason</code>.</li>"
				+ "<li>Kagua stack trace kwa darasa za mod zinazoshtakiwa.</li>"
				+ "<li>Tafuta majina ya darasa za mod karibu na hitilafu ili kupata mawazo.</li>" + "</ul>"
				+ "<p><b>Matumizi yanayopendekezwa ya Grepr:</b></p>" + "<ul>"
				+ "<li>Fungua <b>Grepr</b> kwenye upau wa pembeni.</li>"
				+ "<li>Washa chaguo la kutafuta ndani ya faili <code>.jar</code>, <code>.zip</code> au <code>.fpm</code>.</li>"
				+ "<li>Tafuta jina la msingi la darasa, si lazima paketi nzima.</li>" + "</ul>"
				+ "<p>Mfano: ikiwa <code>paquete.Clase</code> inaonekana, tafuta tu:</p>" + "<code>"
				+ (claseSospechosa.length() > 0 ? claseSospechosa : "Clase") + "</code>"
				+ "<p>Hii inaweza kusaidia kupata ni mod ipi inayokata au kubadilisha darasa hilo.</p>"
				+ "<p>Suluhisho za kawaida: sasisha mod ilioathirika, ondoa mod zisizolingana, angalia addons za mod kuu, au jaribu bila mod zinazotumia mixins/transformers kwenye darasa ileile.</p>";
	}

	@Override
	public String errorMetodoFinalSobrescrito(String claseQueSobrescribe, String metodoFinal) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Hitilafu ya ulinganisho: mod inajarikuandika upya njia ya final.</b>"
				+ "<p>Logi ina hitilafu ya <b>IncompatibleClassChangeError</b> yenye maandishi "
				+ "<b>overrides final method</b>.</p>" + "<p>Darasa ilioathiriwa: <code>" + claseQueSobrescribe
				+ "</code></p>" + "<p>Njia ya final ilioathiriwa: <code>" + metodoFinal + "</code></p>"
				+ "<p>Hitilafu hii mara nyingi hutokea wakati mod ilikompiliwa kwa toleo tofauti la Minecraft, "
				+ "Forge, NeoForge, Fabric, Quilt, au maktaba ya msingi.</p>" + "<p><b>Nini cha kujaribu:</b></p>"
				+ "<ul>" + "<li>Sasisha mod inayokata darasa iliyotajwa.</li>"
				+ "<li>Ikiwa tatizo lilianza baada ya kusasisha Minecraft au loader, jaribu toleo linalolingana la mod.</li>"
				+ "<li>Ikiwa darasa ni ya <b>Immersive Portals</b>, hakikisha <b>Immersive Portals</b> inalingana kabisa na toleo lako la Minecraft na loader.</li>"
				+ "<li>Epuka kuchanganya mod zilizotengenezwa kwa matoleo tofauti ya loader au Minecraft.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreErrorMetodoFinalSobrescrito() {
		return "Mod inajarikuandika upya njia ya final";
	}

	@Override
	public String errorCrashProvocadoPorComando(String comandoDetectado) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft ilifungwa na amri ya crash.</b>" + "<p>Logi inaonyesha kwamba amri <code>"
				+ comandoDetectado + "</code> ilitumika.</p>"
				+ "<p>Hii kwa kawaida inamaanisha kwamba mchezo haukufungwa kwa sababu ya hitilafu ya kawaida ya mod, bali kwa sababu mtu "
				+ "alitumia amri iliyoundwa kusababisha crash kwa makusudi.</p>" + "<p><b>Nini cha kukagua:</b></p>"
				+ "<ul>" + "<li>Kagua ni nani aliyetekeleza amri hiyo kwenye konsole au ndani ya mchezo.</li>"
				+ "<li>Ikiwa ilikuwa jaribio, unaweza kupuuza crash hii.</li>"
				+ "<li>Ikiwa ilitokea bila kukusudia, kagua command blocks, hati (scripts), datapacks, mod za utawala au ruhusa za wasimamizi.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreCrashProvocadoPorComando() {
		return "Crash iliyosababishwa na amri";
	}

	public String impactoAlto() {
		return "Juu";
	}

	public String impactoMedio() {
		return "Wastani";
	}

	public String impactoBajo() {
		return "Chini";
	}

	public String impactoBajoMedio() {
		return "Chini/Wastani";
	}

	public String riesgoAlto() {
		return "Juu";
	}

	public String riesgoMedio() {
		return "Wastani";
	}

	public String riesgoBajo() {
		return "Chini";
	}

	public String riesgoMedioAlto() {
		return "Wastani/Juu";
	}

	public String tituloCrearConfigBBE() {
		return "Unda usanidi wa Better Block Entities";
	}

	public String descripcionCrearConfigBBE() {
		return "Faili BBEConfig.json haipo.";
	}

	public String sugerenciaCrearConfigBBE() {
		return "Unda BBEConfig.json na uboreshaji kwa masanduku, shulkers, alama, vitanda, kengele na bendera.";
	}

	public String tituloActivarOptimizacionMaestraBBE() {
		return "Washa uboreshaji mkuu wa BBE";
	}

	public String descripcionActivarOptimizacionMaestraBBE() {
		return "Inaonekana Better Block Entities haijawasha uboreshaji mkuu.";
	}

	public String sugerenciaActivarOptimizacionMaestraBBE() {
		return "Ongeza {\"option\":\"optimize.master\",\"value\":true}";
	}

	public String tituloActivarCullingTextoCartelesBBE() {
		return "Washa culling ya maandishi ya alama";
	}

	public String descripcionActivarCullingTextoCartelesBBE() {
		return "Inapunguza uchoraji wa maandishi ya alama kwa mbali.";
	}

	public String sugerenciaActivarCullingTextoCartelesBBE() {
		return "Ongeza {\"option\":\"misc.sign_text_culling\",\"value\":true}";
	}

	public String tituloAumentarSleepDelayEntityCulling() {
		return "Ongeza sleepDelay ya EntityCulling";
	}

	public String descripcionAumentarSleepDelayEntityCulling() {
		return "EntityCulling itaangalia viumbe mara chache zaidi.";
	}

	public String sugerenciaAumentarSleepDelayEntityCulling() {
		return "\"sleepDelay\": 153";
	}

	public String tituloSubirLimiteHitboxEntityCulling() {
		return "Panda kikomo cha hitbox";
	}

	public String descripcionSubirLimiteHitboxEntityCulling() {
		return "Inaruhusu tabia ya culling yenye nguvu zaidi kabla ya kushuka njia za polepole.";
	}

	public String sugerenciaSubirLimiteHitboxEntityCulling() {
		return "\"hitboxLimit\": 90";
	}

	public String tituloDesactivarDatosF3EntityCulling() {
		return "Zima data ya F3 ya EntityCulling";
	}

	public String descripcionDesactivarDatosF3EntityCulling() {
		return "Inaondoa habari ya ziada ya debug ya mod.";
	}

	public String sugerenciaDesactivarDatosF3EntityCulling() {
		return "\"disableF3\": true";
	}

	public String tituloActivarBufferingSignsImmediatelyFast() {
		return "Washa buffering ya majaribio ya alama";
	}

	public String descripcionActivarBufferingSignsImmediatelyFast() {
		return "Inaweza kuboresha utendaji wakati kuna alama nyingi.";
	}

	public String sugerenciaActivarBufferingSignsImmediatelyFast() {
		return "\"experimental_sign_text_buffering\": true";
	}

	public String tituloReducirConflictosResourcePacksImmediatelyFast() {
		return "Punguza usimamizi wa migogoro ya resource packs";
	}

	public String descripcionReducirConflictosResourcePacksImmediatelyFast() {
		return "Inaweza kuondoa kazi ya ziada, lakini pia inaweza kusababisha matatizo ya kuona na resource packs.";
	}

	public String sugerenciaReducirConflictosResourcePacksImmediatelyFast() {
		return "\"experimental_disable_resource_pack_conflict_handling\": true";
	}

	public String tituloOcultarBotonNCR() {
		return "Ficha kitufe cha No Chat Reports";
	}

	public String descripcionOcultarBotonNCR() {
		return "Mabadiliko ya kiolesura; haiboreshi FPS sana, lakini inasafisha skrini.";
	}

	public String sugerenciaOcultarBotonNCR() {
		return "\"showNCRButton\": false";
	}

	public String tituloActivarMixinsExperimentalesLithium() {
		return "Washa mixins za majaribio za Lithium";
	}

	public String descripcionActivarMixinsExperimentalesLithium() {
		return "Inawasha uboreshaji wa ziada wa majaribio.";
	}

	public String sugerenciaActivarMixinsExperimentalesLithium() {
		return "mixin.experimental=true";
	}

	public String tituloUsarDetectorThreadingPequenoFerriteCore() {
		return "Tumia kigunduzi cha threading kidogo";
	}

	public String descripcionUsarDetectorThreadingPequenoFerriteCore() {
		return "Inapunguza kumbukumbu, lakini inaweza kuwa na hatari zaidi.";
	}

	public String sugerenciaUsarDetectorThreadingPequenoFerriteCore() {
		return "useSmallThreadingDetector=true";
	}

	public String tituloModernFixRecursosDinamicos() {
		return "Washa rasilimali za kisasa za ModernFix";
	}

	public String descripcionModernFixRecursosDinamicos() {
		return "Inaweza kupunguza matumizi ya kumbukumbu na kazi kwa kupakia rasilimali kwa ufanisi zaidi.";
	}

	public String tituloModernFixRenderizadoresDinamicosEntidades() {
		return "Washa vichoraji vya kisasa vya viumbe";
	}

	public String descripcionModernFixRenderizadoresDinamicosEntidades() {
		return "Inaweza kuboresha utendaji kwa kudhibiti vichoraji vya viumbe kwa ufanisi zaidi.";
	}

	public String tituloModernFixRenderizadoItemsRapido() {
		return "Washa uchoraji wa haraka wa vitu";
	}

	public String descripcionModernFixRenderizadoItemsRapido() {
		return "Inaweza kuboresha utendaji wakati wa kuchora vitu.";
	}

	public String tituloModernFixWorldgenAllocation() {
		return "Punguza mgao katika worldgen";
	}

	public String descripcionModernFixWorldgenAllocation() {
		return "Inaweza kupunguza taka ya kumbukumbu wakati wa uzalishaji wa ulimwengu.";
	}

	public String tituloModernFixDeduplicacionIngredientes() {
		return "Washa uondoaji wa nakala za viungo";
	}

	public String descripcionModernFixDeduplicacionIngredientes() {
		return "Inapunguza vitu vinavyorudiwa vinavyohusiana na mapishi na viungo.";
	}

	public String tituloSodiumRenderCielo() {
		return "Washa uboreshaji/uchoraji wa anga katika Sodium";
	}

	public String descripcionSodiumRenderCielo() {
		return "Inaweza kurekebisha tabia ya uchoraji wa anga.";
	}

	public String tituloActivarLightmapCaching() {
		return "Washa cache ya lightmap";
	}

	public String descripcionActivarLightmapCaching() {
		return "Inazuia kukokotoa upya mwanga wakati haihitajiki.";
	}

	public String sugerenciaActivarLightmapCaching() {
		return "enable_lightmap_caching: true";
	}

	public String tituloOcultarTextoF3BadOptimizations() {
		return "Ficha maandishi ya F3 ya BadOptimizations";
	}

	public String descripcionOcultarTextoF3BadOptimizations() {
		return "Inapunguza kelele ya debug kwenye skrini ya F3.";
	}

	public String sugerenciaOcultarTextoF3BadOptimizations() {
		return "show_f3_text: false";
	}

	public String tituloDesactivarLogConfigBadOptimizations() {
		return "Zima logi ya usanidi";
	}

	public String descripcionDesactivarLogConfigBadOptimizations() {
		return "Inazuia kuchapa usanidi wote wakati wa kuanza.";
	}

	public String sugerenciaDesactivarLogConfigBadOptimizations() {
		return "log_config: false";
	}

	public String tituloActivarSerializadorGCFreeC2ME() {
		return "Washa serializer ya GC-free ya C2ME";
	}

	public String descripcionActivarSerializadorGCFreeC2ME() {
		return "Inapunguza mgao wa kumbukumbu wakati wa kupakia au kuhifadhi chunks.";
	}

	public String sugerenciaActivarSerializadorGCFreeC2ME() {
		return "[ioSystem] gcFreeChunkSerializer = true";
	}

	public String tituloDesactivarSyncPlayerTicketsC2ME() {
		return "Zima syncPlayerTickets";
	}

	public String descripcionDesactivarSyncPlayerTicketsC2ME() {
		return "Inaweza kuboresha utendaji wa chunks, lakini inaweza kuathiri contraptions za kiufundi.";
	}

	public String sugerenciaDesactivarSyncPlayerTicketsC2ME() {
		return "[chunkSystem] syncPlayerTickets = false";
	}

	public String tituloUsarCullingHojasDepthMoreCulling() {
		return "Tumia culling ya majani DEPTH";
	}

	public String descripcionUsarCullingHojasDepthMoreCulling() {
		return "Inatumia hali ya culling ya majani yenye nguvu zaidi kuliko hali ya kawaida.";
	}

	public String sugerenciaUsarCullingHojasDepthMoreCulling() {
		return "leavesCullingMode = \"DEPTH\"";
	}

	public String tituloActivarEndGatewayCullingMoreCulling() {
		return "Washa culling ya End Gateway";
	}

	public String descripcionActivarEndGatewayCullingMoreCulling() {
		return "Inazuia uchoraji usiohitajika wa End Gateways.";
	}

	public String sugerenciaActivarEndGatewayCullingMoreCulling() {
		return "endGatewayCulling = true";
	}

	public String tituloActivarActivationRangeServerCore() {
		return "Washa activation range";
	}

	public String descripcionActivarActivationRangeServerCore() {
		return "Inapunguza ticks za viumbe mbali na mchezaji.";
	}

	public String sugerenciaActivarActivationRangeServerCore() {
		return "activation-range: enabled: true";
	}

	public String tituloActivarRangoVerticalServerCore() {
		return "Washa safu ya wima";
	}

	public String descripcionActivarRangoVerticalServerCore() {
		return "Inapunguza ticks za viumbe vilivyo juu sana au chini sana ya mchezaji.";
	}

	public String sugerenciaActivarRangoVerticalServerCore() {
		return "use-vertical-range: true";
	}

	public String impactoNegativoAlto() {
		return "Athari kubwa hasi";
	}

	public String advertenciaModsCulling() {
		return "Mods za culling zinaweza kusababisha kutolingana na mod zingine, kufungwa ghafla (crashes), makosa ambapo mchezo husimama kutikita vizuri na pia zinaweza kufanya mashamba ya otomatiki au viwanda vusisimame kufanya kazi.";
	}

	public String tituloModBadOptimizations() {
		return "Ongeza BadOptimizations";
	}

	public String descripcionModBadOptimizations() {
		return "Inaongeza uboreshaji mdogo wa upande wa mteja kama cache ya lightmap, cache ya anga na kupunguza wito usiohitajika.";
	}

	public String tituloModBBE() {
		return "Ongeza Better Block Entities";
	}

	public String descripcionModBBE() {
		return "Inaboresha uchoraji wa block entities kama masanduku, shulkers, vitanda, kengele, bendera na alama.";
	}

	public String tituloModC2ME() {
		return "Ongeza Concurrent Chunk Management Engine";
	}

	public String descripcionModC2ME() {
		return "Inaboresha upakiaji, uzalishaji na usimamizi wa vipande (chunks) kwa kutumia usindiki sambamba. Inaweza kuwa yenye nguvu sana, lakini pia inaweza kusababisha kutolingana katika modpacks kubwa.";
	}

	public String tituloModEntityCulling() {
		return "Ongeza EntityCulling";
	}

	public String descripcionModEntityCulling() {
		return "Inazuia uchoraji wa viumbe ambavyo havionekani. " + advertenciaModsCulling();
	}

	public String tituloModFerriteCore() {
		return "Ongeza FerriteCore";
	}

	public String descripcionModFerriteCore() {
		return "Inapunguza matumizi ya kumbukumbu kupitia kuondoa nakala na miundo ya ndani yenye ufanisi zaidi.";
	}

	public String tituloModImmediatelyFast() {
		return "Ongeza ImmediatelyFast";
	}

	public String descripcionModImmediatelyFast() {
		return "Inaboresha sehemu mbalimbali za uchoraji wa papo hapo, maandishi, buffers, ramani na kiolesura.";
	}

	public String tituloModLithium() {
		return "Ongeza Lithium";
	}

	public String descripcionModLithium() {
		return "Inaboresha mantiki ya mchezo, viumbe, bloku, fizikia na mifumo mingine bila kubadilisha sana tabia ya vanilla.";
	}

	public String tituloModModernFix() {
		return "Ongeza ModernFix";
	}

	public String descripcionModModernFix() {
		return "Inaongeza uboreshaji mwingi wa kumbukumbu, upakiaji, rasilimali na utendaji wa jumla. Zana zake zinazohusiana na atlas zinaweza kugongana na mod zinazofanya atlas kuwa ndogo.";
	}

	public String tituloModMoreCulling() {
		return "Ongeza More Culling";
	}

	public String descripcionModMoreCulling() {
		return "Inaongeza culling kwa bloku, majani, fremu za vitu, michoro, mvua, beacons na vipengele vingine. "
				+ advertenciaModsCulling();
	}

	public String tituloModScalableLux() {
		return "Ongeza ScalableLux";
	}

	public String descripcionModScalableLux() {
		return "Inaboresha hesabu zinazohusiana na mwanga na inaweza kuboresha utendaji katika ulimwengu wenye mabadiliko mengi ya mwanga.";
	}

	public String tituloModServerCore() {
		return "Ongeza ServerCore";
	}

	public String descripcionModServerCore() {
		return "Inaongeza uboreshaji upande wa seva, activation range, udhibiti wa mobcaps, kupunguza tiki na uboreshaji wa upakiaji.";
	}

	public String tituloModSodium() {
		return "Ongeza Sodium";
	}

	public String descripcionModSodium() {
		return "Mod kuu ya uboreshaji uchoraji. Kwa kawaida ni moja ya maboresho muhimu zaidi kwa FPS.";
	}

	public String tituloModVMP() {
		return "Ongeza Very Many Players";
	}

	public String descripcionModVMP() {
		return "Inaboresha mifumo ya seva ili kushughulikia wachezaji wengi. Kitambulisho cha mod kinachotarajiwa ni vmp.";
	}

	public String tituloModMCMT() {
		return "Ongeza MCMT";
	}

	public String descripcionModMCMT() {
		return "Inajaribu kufanya sehemu za seva ya Minecraft ziwe na nyuzi nyingi. Inaweza kuboresha utendaji katika hali fulani, lakini ina hatari kubwa ya kutolingana, makosa ya kutikita na tabia ya ajabu.";
	}

	public String tituloLiabilityUranium() {
		return "Ondoa Uranium";
	}

	public String descripcionLiabilityUranium() {
		return "Uranium ni mod iliyoundwa kusababisha lagg kwa makusudi mchezo. Haipaswi kusakinishwa ikiwa unataka utendaji bora.";
	}

	public String tituloAmbientalSinXmx() {
		return "Weka kumbukumbu ya juu zaidi ya Java";
	}

	public String descripcionAmbientalSinXmx(int mods, String minimo, String maximoSeguro) {
		return "-Xmx haigunduliwa katika hoja zilizotolewa. Kwa " + mods + " mod, kiwango cha chini kinapendekezwa ni "
				+ minimo + ", usizidi takriban " + maximoSeguro + ".";
	}

	public String sugerenciaAmbientalSinXmx(String minimo) {
		return "Ongeza -Xmx" + minimo.replace(" ", "");
	}

	public String tituloAmbientalDemasiadaMemoria() {
		return "Punguza kumbukumbu iliyogawiwa";
	}

	public String descripcionAmbientalDemasiadaMemoria(String xmx, String total, String maximoSeguro) {
		return "Instansi imegawiwa " + xmx + " kati ya " + total
				+ ". Haipendekezwi kugawa zaidi ya 80% ya RAM inayopatikana.";
	}

	public String sugerenciaAmbientalDemasiadaMemoria(String maximoSeguro) {
		return "Punguza -Xmx hadi " + maximoSeguro + " au chini.";
	}

	public String tituloAmbientalMemoriaInsuficiente() {
		return "Ongeza kumbukumbu iliyogawiwa";
	}

	public String descripcionAmbientalMemoriaInsuficiente(int mods, String xmx, String minimo) {
		return "Instansi ina " + xmx + " iliyogawiwa. Kwa " + mods + " mod, kiwango cha chini kinapendekezwa ni "
				+ minimo + ".";
	}

	public String sugerenciaAmbientalMemoriaInsuficiente(String minimo) {
		return "Ongeza -Xmx hadi angalau " + minimo + ".";
	}

	public String tituloAmbientalJava8GC() {
		return "Tumia G1GC au Shenandoah katika Java 8";
	}

	public String descripcionAmbientalJava8GC() {
		return "Katika Java 8, inapendekezwa kutumia G1GC au Shenandoah ili kupunguza mapumziko na kuimarisha utulivu.";
	}

	public String sugerenciaAmbientalJava8GC() {
		return "Ongeza -XX:+UseG1GC au -XX:+UseShenandoahGC.";
	}

	public String tituloAmbientalZGC() {
		return "Tumia ZGC";
	}

	public String descripcionAmbientalZGC(String ramTotal) {
		return "Kompyuta ina zaidi ya GB 12 za RAM (" + ramTotal
				+ "). Ikiwa usambazaji wa Java unaunga mkono, ZGC inaweza kupunguza mapumziko ya mkusanyaji taka.";
	}

	public String sugerenciaAmbientalZGC() {
		return "Katika Java 17 au juu zaidi, jaribu -XX:+UseZGC.";
	}

	public String tituloAmbientalAikar() {
		return "Ongeza bendera za Aikar";
	}

	public String descripcionAmbientalAikar() {
		return "Katika Java 17 au zamani, bendera za Aikar mara nyingi huboresha tabia ya G1GC kwa Minecraft.";
	}

	public String sugerenciaAmbientalAikar() {
		return "Tumia bendera za Aikar, ikiwa ni pamoja na -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200.";
	}

	public String tituloAmbientalRedHatJDK() {
		return "Tumia Red Hat JDK";
	}

	public String descripcionAmbientalRedHatJDK(int javaMayor, String os) {
		return "Kwa Java " + javaMayor + " katika " + os
				+ ", Red Hat JDK inapendekezwa kwa sababu ya utulivu na ulinganifu.";
	}

	public String sugerenciaAmbientalRedHatJDK() {
		return "Sakinisha Red Hat JDK kwa Java 8 au Java 11.";
	}

	public String tituloAmbientalAzulPrime() {
		return "Fikiria Azul Prime";
	}

	public String descripcionAmbientalAzulPrime() {
		return "Katika Linux na Java 16 au juu zaidi na zaidi ya GB 16 za RAM, Azul Prime inaweza kuwa chaguo nzuri la utendaji.";
	}

	public String sugerenciaAmbientalAzulPrime() {
		return "Jaribu Azul Prime ikiwa kompyuta ina zaidi ya GB 16 za RAM.";
	}

	public String tituloAmbientalGraalVM() {
		return "Fikiria GraalVM";
	}

	public String descripcionAmbientalGraalVM() {
		return "Na Java 16 au juu zaidi na zaidi ya GB 16 za RAM, GraalVM inaweza kuwa mbadala muhimu nje ya Linux.";
	}

	public String sugerenciaAmbientalGraalVM() {
		return "Jaribu GraalVM ikiwa kompyuta ina zaidi ya GB 16 za RAM.";
	}

	public String tituloAmbientalDiscoBajo() {
		return "Angalia nafasi iliyoachwa kwenye diski";
	}

	public String descripcionAmbientalDiscoBajo(String libre) {
		return "Diski ina nafasi kidogo iliyoachwa: " + libre
				+ ". Minecraft inaweza kushindwa, kuhifadhi polepole au kuharibu data ikiwa itakosa nafasi.";
	}

	public String sugerenciaAmbientalDiscoBajo() {
		return "Ondoa data hadi uwe na angalau GB 20 zinazopatikana.";
	}

	public String tituloAmbientalWindowsRHEL9() {
		return "Fikiria RHEL 9 kwa majaribio";
	}

	public String descripcionAmbientalWindowsRHEL9() {
		return "Katika Windows, inapendekezwa kufikiria RHEL 9 kwa sababu inajumuisha Red Hat JDK, ni thabiti, inaweza kupakuliwa bure kwa watu binafsi na ndipo majaribio mengi yanafanywa.";
	}

	public String sugerenciaAmbientalWindowsRHEL9() {
		return "Jaribu instansi katika RHEL 9 ikiwa unatafuta utulivu wa juu zaidi wa majaribio.";
	}

	public String tituloAmbientalRaptorLake() {
		return "Onyo la Intel Raptor Lake";
	}

	public String descripcionAmbientalRaptorLake() {
		return "Tatizo la Raptor Lake limegunduliwa na ukaguzi uliopo. Hii inaweza kusababisha kutokuwa na utulivu, kufungwa ghafla na makosa ambayo yanaonekana kuwa ya modpack.";
	}

	public String sugerenciaAmbientalRaptorLake() {
		return "Sasisha BIOS/microcode na angalia onyo la Raptor Lake kabla ya kulaumu modpack.";
	}

	@Override
	public String tituloAmbientalNeoForge1201Antiguo() {
		return "NeoForge 1.20.1 ya zamani imegunduliwa";
	}

	@Override
	public String descripcionAmbientalNeoForge1201Antiguo() {
		return "FancyModLoader 47 au njia inayolingana na NeoForge 1.20.1 imegunduliwa. "
				+ "NeoForge 1.20.1 ilikuwa tawi la MinecraftForge 1.20.1 na kwa kawaida inalingana kiwango cha binary, "
				+ "lakini mstari huo uliachwa mapema na unaweza kukosa uboreshaji kadhaa uliopo katika Forge.";
	}

	@Override
	public String sugerenciaAmbientalNeoForge1201Antiguo() {
		return "Kwa 1.20.1, ikiwa modpack inaruhusu, fikiria kutumia MinecraftForge 1.20.1 badala ya NeoForge 1.20.1.";
	}

	@Override
	public String tituloAmbientalGPU() {
		return "Tatizo la GPU limegunduliwa";
	}

	@Override
	public String descripcionAmbientalGPU() {
		return "Ukaguzi mwingine tayari umegundua tatizo linalowezekana la GPU, OpenGL au uchaguzi wa kadi ya grafiki.";
	}

	@Override
	public String sugerenciaAmbientalGPU() {
		return "Hakikisha Minecraft inatumia GPU sahihi, sasisha dereva na epuka usanidi usio thabiti wa hibridi.";
	}

	@Override
	public String gpuFixTitulo() {
		return "Usanidi wa GPU";
	}

	@Override
	public String gpuFixBotonSidebar() {
		return "GPU";
	}

	@Override
	public String gpuFixBotonAplicar() {
		return "Tumia usanidi";
	}

	@Override
	public String gpuFixBotonFuenteTLauncher() {
		return "Fungua mwongozo wa TLauncher";
	}

	@Override
	public String gpuFixBotonVirusTotal() {
		return "Fungua uchambuzi wa VirusTotal";
	}

	@Override
	public String gpuFixBotonOptimusLinux() {
		return "Fungua mwongozo wa NVIDIA Optimus";
	}

	@Override
	public String gpuFixTextoWindows() {
		return Statics.nombre_cd.obtener()
				+ " imegundua kwamba Minecraft huenda isitumii GPU yenye utendaji wa juu.\n\n"
				+ "Katika Windows unaweza kuweka funguo za rejista katika "
				+ "HKEY_CURRENT_USER\\\\Software\\\\Microsoft\\\\DirectX\\\\UserGpuPreferences "
				+ "kulazimisha javaw.exe kutumia GPU iliyojitolea.\n\n"
				+ "GpuPreference=0 = uamuzi wa kiotomatiki wa Windows.\n"
				+ "GpuPreference=1 = kuokoa nishati / GPU iliyojengewa ndani.\n"
				+ "GpuPreference=2 = GPU yenye utendaji wa juu.\n\n"
				+ "Sehemu ya habari hii ilipatikana shukrani kwa utafiti uliochapishwa na TLauncher na uchambuzi "
				+ "wa tabia unaopatikana kwenye VirusTotal.";
	}

	@Override
	public String gpuFixTextoLinux() {
		return Statics.nombre_cd.obtener()
				+ " imegundua tatizo linalowezekana kuhusiana na NVIDIA Optimus au PRIME.\n\n"
				+ "Kulingana na usambazaji wa Linux unatumia, inaweza kuhitajika kusanidi NVIDIA Optimus, "
				+ "nvidia-prime, switcheroo-control au mifumo mingine hibridi.\n\n"
				+ "Katika Fedora/RHEL na vitohozi, mara nyingi inapendekezwa kufuata nyaraka za RPMFusion.\n\n"
				+ "Kitufe cha chini kitafungua nyaraka rasmi zinazopendekezwa.";
	}

	@Override
	public String gpuFixTextoMac() {
		return Statics.nombre_cd.obtener() + " imegundua tatizo linalowezekana la uteuzi wa GPU.\n\n"
				+ "Katika baadhi ya mifumo ya macOS yenye GPU hibridi inawezekana kulazimisha matumizi ya GPU iliyojitolea "
				+ "kupitia mipangilio ya juu ya mfumo.\n\n"
				+ "Kitufe cha kutumia kitajaribu kutekeleza amri ili kupatia kipaumbele GPU yenye utendaji wa juu.";
	}

	@Override
	public String gpuFixTextoOtroSistema() {
		return Statics.nombre_cd.obtener() + " imegundua tatizo linalowezekana kuhusiana na GPU, "
				+ "lakini hakuna utekelezaji maalum kwa mfumo huu wa uendeshaji.";
	}

	@Override
	public String gpuFixLinuxManual() {
		return "Katika Linux kawaida usanidi lazima ufanywe kwa mikono kulingana na usambazaji, "
				+ "dereva wa NVIDIA na mfumo wa Optimus/PRIME unatumika.";
	}

	@Override
	public String gpuFixSistemaNoSoportado() {
		return "Mfumo wa uendeshaji hauungwi mkono kwa usanidi wa kiotomatiki wa GPU.";
	}

	@Override
	public String gpuFixJavaNoDetectado() {
		return "Haikuweza kugundua njia ya sasa ya javaw.exe.";
	}

	@Override
	public String gpuFixWindowsAplicado(String ruta) {
		return "Usanidi wa GPU ulitumika vizuri kwa:\n\n" + ruta + "\n\n"
				+ "GpuPreference=2 inaonyesha GPU yenye utendaji wa juu.";
	}

	@Override
	public String gpuFixErrorAplicando() {
		return "Hitilafu ilitokea wakati wa kujaribu kutumia usanidi wa GPU";
	}

	@Override
	public String gpuFixMacAplicado() {
		return "Usanidi wa GPU yenye utendaji wa juu ulitumika.";
	}

	@Override
	public String gpuFixMacError() {
		return "Haikuweza kutumia usanidi wa GPU katika macOS";
	}

	@Override
	public String rendimientoTitulo() {
		return "Msimamizi wa utendaji";
	}

	@Override
	public String rendimientoBotonSidebar() {
		return "Utendaji";
	}

	@Override
	public String rendimientoBotonAnalizar() {
		return "Chambua utendaji";
	}

	@Override
	public String rendimientoBotonAbrirGPU() {
		return "Fungua usanidi wa GPU";
	}

	@Override
	public String rendimientoDescripcion() {
		return "Paneli hii inakagua matatizo ya mazingira, mod zinazopendekezwa au zenye hatari, na chaguzi za usanidi "
				+ "ambazo zinaweza kuboresha utendaji. Si chaguzi zote hufanya kazi pamoja, si zote zinafaa kwa kila "
				+ "toleo la Minecraft na si zote zinalingana na kila modloader. Hiyo ni sawa: huhitaji "
				+ "alama kamili ya uboreshaji.";
	}

	@Override
	public String rendimientoNotaCompatibilidad() {
		return "Kumbuka: mapendekezo haya ni uwezekano, si amri ya kutumia yote. Chaguzi zingine zinaweza kugongana "
				+ "na nyingine au hazifai kwa toleo lako, lanzisha, modloader au modpack.";
	}

	@Override
	public String rendimientoPestanaResumen() {
		return "Muhtasari";
	}

	@Override
	public String rendimientoPestanaAmbiental() {
		return "Matatizo ya mazingira";
	}

	@Override
	public String rendimientoPestanaMods() {
		return "Mod zinazopendekezwa na hatari";
	}

	@Override
	public String rendimientoPestanaConfigs() {
		return "Chaguzi za usanidi";
	}

	@Override
	public String rendimientoResumenTitulo() {
		return "Muhtasari wa uchambuzi";
	}

	@Override
	public String rendimientoResumenAmbiental(int cantidad) {
		return "Matatizo ya mazingira yaliyopatikana: " + cantidad;
	}

	@Override
	public String rendimientoResumenMods(int cantidad) {
		return "Mapendekezo au hatari za mod zilizopatikana: " + cantidad;
	}

	@Override
	public String rendimientoResumenConfigs(int cantidad) {
		return "Mapendekezo ya usanidi yaliyopatikana: " + cantidad;
	}

	@Override
	public String rendimientoResumenGPU() {
		return "Tatizo la GPU limegunduliwa. Kwa sababu hiyo, kitufe cha kufungua usanidi wa GPU kimewezeshwa.";
	}

	@Override
	public String rendimientoSinHallazgos() {
		return "Hakuna mapendekezo yaliyopatikana katika sehemu hii.";
	}

	@Override
	public String rendimientoSugerencia() {
		return "Pendekezo";
	}

	@Override
	public String rendimientoColorFondo() {
		return "Utendaji - mandhari nyuma";
	}

	@Override
	public String rendimientoColorPanel() {
		return "Utendaji - paneli";
	}

	@Override
	public String rendimientoColorTexto() {
		return "Utendaji - maandishi";
	}

	@Override
	public String rendimientoColorTextoSecundario() {
		return "Utendaji - maandishi ya sekondari";
	}

	@Override
	public String rendimientoColorBoton() {
		return "Utendaji - kitufe";
	}

	@Override
	public String rendimientoColorBotonTexto() {
		return "Utendaji - maandishi ya kitufe";
	}

	@Override
	public String rendimientoColorSeleccion() {
		return "Utendaji - uteuzi";
	}

	@Override
	public String mensajeDialogoCompartirPrimitiva() {
		return "Mchezo umefungwa ghafla (crash). Ikiwa hakuna dirisha la kujitokeza lenye suluhisho, tafadhali tuma logi kwa kituo cha usaidizi.";
	}

	@Override
	public String irAModoNormalEstiloTL() {
		return "Nenda kwenye hali ya kawaida";
	}

	@Override
	public String noHayEnlacesParaCopiar() {
		return "Hakuna viungo vya kunakili.";
	}

	@Override
	public String error_inesperado() {
		return "Hitilafu isiyotarajiwa";
	}

	@Override
	public String centroDeSoporte() {
		return "Kituo cha usaidizi";
	}

	@Override
	public String noHayCentroSoporteConfigurado() {
		return "Hakuna kituo cha usaidizi kilichosanidiwa.";
	}

	public String historialMCLogs() {
		return "Historia ya MCLogs";
	}

	public String endpoint() {
		return "Mwisho wa nukta (Endpoint)";
	}

	public String slug() {
		return "Slug";
	}

	public String tokenEliminacion() {
		return "Token ya kufuta";
	}

	public String enlace() {
		return "Kiungo";
	}

	public String lineas() {
		return "Mistari";
	}

	public String errores() {
		return "Makosa";
	}

	public String eliminarRegistroMCLogs() {
		return "Futa rekodi";
	}

	public String faltanDatosParaEliminarMCLogs() {
		return "Slug au token ya kufuta haipo.";
	}

	public String confirmarEliminarMCLogs() {
		return "Je, una uhakika unataka kufuta rekodi hii ya MCLogs?";
	}

	public String registroEliminadoMCLogs() {
		return "Rekodi imefutwa kwa mafanikio.";
	}

	public String confirmar() {
		return "Thibitisha";
	}

	public String colorCampoTexto() {
		return "Rangi ya uga wa maandishi";
	}

	public String historialCDPaste() {
		return "Historia ya CDPaste";
	}

	public String enlaceRaw() {
		return "Kiungo cha Raw";
	}

	public String tamano() {
		return "Ukubwa";
	}

	public String eliminarRegistroCDPaste() {
		return "Futa rekodi ya CDPaste";
	}

	public String faltanDatosParaEliminarCDPaste() {
		return "Slug ya rekodi ya CDPaste haipo.";
	}

	public String confirmarEliminarCDPaste() {
		return "Je, una uhakika unataka kufuta rekodi hii ya CDPaste?";
	}

	public String registroEliminadoCDPaste() {
		return "Rekodi ya CDPaste imefutwa kwa mafanikio.";
	}

	public String launcherGenerico() {
		return "Jumla";
	}

	public String launcherServidorMinecraft() {
		return "Seva ya Minecraft";
	}

	public String descargandoYPreparandoEnlaces() {
		return "Inapakua na kuandaa viungo...";
	}

	public String seleccioneArchivoLog() {
		return "Chagua faili ya logi";
	}

	public String archivoNoValido() {
		return "Faili si sahihi.";
	}

	public String archivoSeleccionado() {
		return "Faili iliyochaguliwa:";
	}

	public String presioneGuardarParaAgregarAnalisis() {
		return "Bonyeza 'Hifadhi na ufunge' ili kuiongeza kwenye uchambuzi.";
	}

	public String errorAlCargarArchivoArrastrado() {
		return "Hitilafu katika kupakia faili iliyovutwa";
	}

	public String errorAlAbrirArchivo() {
		return "Hitilafu katika kufungua faili";
	}

	public String errorDosPuntos() {
		return "Hitilafu";
	}

	public String eliminarRegistros() {
		return "Futa rekodi";
	}

	public String editorConfigsMods() {
		return "Kihariri cha Mipangilio ya Mod";
	}

	public String abrirConfig() {
		return "Fungua Config";
	}

	public String guardarConfig() {
		return "Hifadhi Config";
	}

	public String recargarConfig() {
		return "Pakia upya";
	}

	public String rutaConfig() {
		return "Njia";
	}

	public String tipoConfig() {
		return "Aina";
	}

	public String claveConfig() {
		return "Ufunguo";
	}

	public String valorConfig() {
		return "Thamani";
	}

	public String buscarConfig() {
		return "Tafuta";
	}

	public String sinArchivoSeleccionado() {
		return "Hakuna faili iliyochaguliwa";
	}

	public String archivoNoSoportado() {
		return "Faili haiungwi mkono na injini yoyote iliyopo";
	}

	public String configGuardada() {
		return "Mipangilio imehifadhiwa kwa mafanikio";
	}

	public String errorCargandoConfig() {
		return "Hitilafu katika kupakia mipangilio";
	}

	public String errorGuardandoConfig() {
		return "Hitilafu katika kuhifadhi mipangilio";
	}

	public String seleccionarArchivoConfig() {
		return "Chagua faili ya mipangilio";
	}

	public String suprimirConsolaCD() {
		return "Ficha konsole ya " + Statics.nombre_cd.obtener();
	}

	public String suprimirVerificacionDeStacktrazos() {
		return "Ficha ukaguzi wa stacktraces";
	}

	public String importadorBotonFusionar() {
		return "Unganisha";
	}

	@Override
	public String mod() {
		return "Mod";
	}

	@Override
	public String version() {
		return "Toleo";
	}

	@Override
	public String claseEncontrada() {
		return "Darasa limepatikana";
	}

	@Override
	public String coincidencias() {
		return "Mlingano";
	}

	@Override
	public String resultadosDeBusqueda() {
		return "Matokeo ya utafutaji";
	}

	@Override
	public String desconocido() {
		return "haijulikani";
	}

	@Override
	public String desconocida() {
		return "haijulikani";
	}

	@Override
	public String curseForgeUrl() {
		return "URL ya CurseForge";
	}

	@Override
	public String modrinthUrl() {
		return "URL ya Modrinth";
	}

	@Override
	public String modsEncontradosPara(String clase) {
		return "Mod zilizopatikana za " + clase;
	}

	@Override
	public String entradaCarpetaInvalida(String ruta) {
		return "Ingizo la folda lisilo sahihi: " + ruta;
	}

	@Override
	public String carpetaSinHashes(String ruta) {
		return "Folda isiyo na hashes: " + ruta;
	}

	@Override
	public String noSePudoAccederCarpeta(String ruta) {
		return "Haikuweza kufikia folda: " + ruta;
	}

	@Override
	public String archivoFaltanteEnCarpeta(String ruta, String subRuta) {
		return "Faili haipo katika folda: " + ruta + "/" + subRuta;
	}

	@Override
	public String hashIncorrectoEn(String ruta, String subRuta) {
		return "Hash isiyo sahihi katika: " + ruta + "/" + subRuta;
	}

	@Override
	public String archivoNoAutorizadoEnCarpeta(String ruta, String subRuta) {
		return "Faili isiyoruhusiwa katika folda: " + ruta + "/" + subRuta;
	}

	@Override
	public String entradaArchivoInvalida(String ruta) {
		return "Ingizo la faili lisilo sahihi: " + ruta;
	}

	@Override
	public String hashFaltanteParaArchivo(String ruta) {
		return "Hash haipo kwa faili: " + ruta;
	}

	@Override
	public String archivoFaltante(String ruta) {
		return "Faili haipo: " + ruta;
	}

	@Override
	public String errorAlLeerArchivo(String ruta) {
		return "Hitilafu kusoma faili: " + ruta;
	}

	@Override
	public String hashIncorrectoParaArchivo(String ruta) {
		return "Hash isiyo sahihi kwa faili: " + ruta;
	}

	@Override
	public String listo() {
		return "Imekamilika!";
	}

	public String error_al_cargar_plantilla_desde_disco() {
		return "Hitilafu katika kupakia kiolezo kutoka kwenye diski: ";
	}

	public String no_se_encontro_plantilla_restablecer() {
		return "Kiolezo hakikupatikana. Rejesha kwa kutumia kitufe cha 'Restablecer Plantilla'.";
	}

	public String plantilla_guardada_en() {
		return "Kiolezo kimehifadhiwa katika: ";
	}

	public String plantilla_restablecida_correctamente() {
		return "Kiolezo kimerudishwa kwa mafanikio.";
	}

	public String error_al_guardar() {
		return "Hitilafu katika kuhifadhi: ";
	}

	public String error_al_restablecer() {
		return "Hitilafu katika kurudisha: ";
	}

	public String error_al_restablecer_imagen() {
		return "Hitilafu katika kurudisha picha: ";
	}

	public String no_se_encontro_imagen_en_recurso() {
		return "Picha haikupatikana katika rasilimali: ";
	}

	public String imagen_restablecida() {
		return "Picha imerudishwa: ";
	}

	public String editor_html() {
		return "Kihariri cha HTML";
	}

	public String vista_previa() {
		return "Hakiki";
	}

	public String configuracion_colores_imagenes() {
		return "Usanidi wa Rangi na Picha";
	}

	public String imagenes_con_ruta(String ruta) {
		return "Picha (" + ruta + ")";
	}

	public String enlaces_imagenes_reportes_compartidos() {
		return "Viungo vya picha (ripoti zilizoshirikiwa)";
	}

	public String enlaces_imagenes_reporte_compartido() {
		return "Viungo vya picha (ripoti iliyoshirikiwa)";
	}

	public String url_usada_en_reportes_compartidos() {
		return "URL inayotumika katika ripoti zilizoshirikiwa";
	}

	public String error_creando_codice_json() {
		return "Hitilafu katika kuunda codice.json: ";
	}

	public String error_exportando() {
		return "Hitilafu katika kusafirisha: ";
	}

	public String validacion() {
		return "Uthibitishaji";
	}

	public String ver_codigo() {
		return "Tazama Msimbo";
	}

	public String importar_instancia() {
		return "Ingiza instansi";
	}

	public String compartir_instancia() {
		return "Shiriki instansi";
	}

	public String error_al_cargar_mods() {
		return "Hitilafu katika kupakia mod.";
	}

	public String instalar() {
		return "Sakinisha";
	}

	public String mods_instalados() {
		return "Mod Zilizosakinishwa";
	}

	public String guardar_como_archivo() {
		return "Hifadhi kama faili";
	}

	public String exportando_modpack() {
		return "Inasafirisha modpack...";
	}

	public String modpack_exportado() {
		return "Modpack imesafirishwa:\n";
	}

	public String conectando() {
		return "Inaunganisha...";
	}

	public String esperando_descarga() {
		return "Inasubiri upakuzi...";
	}

	public String finalizado() {
		return "Imekamilika";
	}

	public String retener_dos_puntos() {
		return "Shikilia:";
	}

	public String descargar_deps() {
		return "Pakua deps";
	}

	public String no_faltan_dependencias() {
		return "Hakuna utegemezi unao-kosekana.";
	}

	public String descargar_nbt_para_quests() {
		return "Pakua NBT KWA AJILI YA QUESTS";
	}

	public String descargar_nbt() {
		return "Pakua NBT";
	}

	public String error_cargando_informe() {
		return "Hitilafu katika kupakia ripoti: ";
	}

	@Override
	public String exportar_modpack() {
		return "Safirisha modpack";
	}

	@Override
	public String error_exportando_modpack() {
		return "Hitilafu katika kusafirisha modpack:\n";
	}

	@Override
	public String importador_confirmar_descargar_nbt_para_quests() {
		return "Utegemezi wa NBT unaohitajika kwa ajili ya kuunganisha quests utapakuliwa.\n\n"
				+ "Baadaye inaweza kuhitajika kuanzisha upya ili ziingie kwenye classpath.";
	}

	@Override
	public String resultado_nulo() {
		return "Matokeo ni null.";
	}

	@Override
	public String dependencia_nbt_descargada_reiniciar(String nombrePrograma) {
		return "Utegemezi wa NBT umepakuliwa.\n\n" + "Anzisha upya " + nombrePrograma
				+ " ikiwa muunganisho wa SNBT bado unasema injini ya NBT haipo.";
	}

	@Override
	public String no_se_pudo_descargar_dependencia_nbt() {
		return "Haikuweza kupakua utegemezi wa NBT.";
	}

	@Override
	public String profilerTituloRendimiento() {
		return "Profaila ya utendaji";
	}

	@Override
	public String profilerEstadoActivo() {
		return "Inafanya kazi";
	}

	@Override
	public String profilerAyudaMinaly() {
		return "Mbinu zinazochelewa zaidi zinaonekana juu. Upau unaonyesha uzito wa jamaa wa muda uliojumlishwa.";
	}

	@Override
	public String profilerConfigColorPanel() {
		return "Rangi ya paneli";
	}

	@Override
	public String profilerConfigColorBarra() {
		return "Rangi ya upau";
	}

	@Override
	public String profilerConfigUsarModeloOriginal() {
		return "Tumia mfano wa asili";
	}

	@Override
	public String profilerColumnaClase() {
		return "Darasa";
	}

	@Override
	public String profilerColumnaMetodo() {
		return "Njia";
	}

	@Override
	public String profilerColumnaLlamadas() {
		return "Wito";
	}

	@Override
	public String profilerColumnaTiempoTotal() {
		return "Jumla ya muda";
	}

	@Override
	public String profilerEstadoResumen(String estado, int metodos, int top, String totalVisible) {
		return estado + " | njia: " + metodos + " | juu: " + top + " | jumla inayoonekana: " + totalVisible;
	}

	@Override
	public String samplerTituloRendimiento() {
		return "Kichukua sampuli cha utendaji";
	}

	@Override
	public String samplerAyudaEineLotta() {
		return "Njia zenye muda mwingi zimejumlishwa zinaonekana juu. Upau unaonyesha uzito wa jamaa kwa kuona.";
	}

	@Override
	public String samplerColumnaMuestras() {
		return "Sampuli";
	}

	@Override
	public String samplerColumnaPromedio() {
		return "Wastani";
	}

	@Override
	public String samplerEstadoResumen(String estado, int metodos, int top) {
		return estado + " | njia: " + metodos + " | juu: " + top;
	}

	public String mostrarSelectorAplicacionPrincipal() {
		return "Kiteua programu katika GUI kuu";
	}

	public String mostrarBotonCDLauncherPrincipal() {
		return "Kitufe cha CDLauncher katika GUI kuu";
	}

	public String mostrarBotonCDModsPrincipal() {
		return "Kitufe cha CDMods katika GUI kuu";
	}

	public String mostrarBotonIAPrincipal() {
		return "Kitufe cha AI katika GUI kuu";
	}

	@Override
	public String modsInstalados() {
		return "Mod Zilizosakinishwa";
	}

	@Override
	public String migradorLegacyBotonSidebar() {
		return "Kihamaisha";
	}

	@Override
	public String migradorLegacyTitulo() {
		return "Kihamaisha cha uchambuzi wa zamani";
	}

	@Override
	public String migradorLegacyDescripcion() {
		return "Wasaidizi huyu husaidia kuhamisha mipangilio kutoka kwa vichambuzi vya ajali vya zamani na vilivyomilikiwa kwenda kwenye mifumo ya kisasa, wazi na inayoweza kudumishwa. Kuhamisha sheria hizi hupunguza utegemezi wa zana zilizofungwa na hurahisisha ukaguzi, ushirikiano na usaidizi wa jamii. Nembo zilizoonyeshwa hapa sio nembo halisi; ni parodi tulizoziona kuwa za kuchekesha.";
	}

	@Override
	public String migradorLegacyCrashAssistant() {
		return "Wasaidizi wa Crash Assistant";
	}

	@Override
	public String migradorCrashAssistantDescripcion() {
		return "Inaingiza problematic_mods.json kutoka Crash Assistant na kuichanganya na umbizo la CrashDetector kupitia wasaidizi.";
	}

	@Override
	public String migradorEjecutar() {
		return "Tekeleza wasaidizi";
	}

	@Override
	public String migradorCompletado() {
		return "Wasaidizi amekamilika.";
	}

	@Override
	public String migradorNadaParaMigrar() {
		return "Hakuna kitu kilichopatikana cha kuhamisha.";
	}

	@Override
	public String migradorCAUsarPrimitiva() {
		return "Tumia GUI kuu ya msingi";
	}

	@Override
	public String migradorCADeshabilitarChecks() {
		return "Zima ukaguzi";
	}

	@Override
	public String migradorCAAvisoUrlSoporteWysiwyg() {
		return "Kiungo cha usaidizi hakikuhamishwa kwa sababu hutumii GUI ya msingi. Ili kubadilisha URL ya usaidizi, tumia kihariri cha WYSIWYG katika mipangilio ya CrashDetector.";
	}

	@Override
	public String migradorCAAvisoNoMigrado() {
		return "Onyo: kutokana na tofauti kati ya aina za usanidi, orodha ya kukataa ya ukaguzi haiwezi kuhamishwa kiotomatiki. Kutoka hapo, pia unaweza kuzima onyo la Intel na Mod List Diff; kama vile katika CrashDetector, hizi zinachukuliwa kama ukaguzi wa kawaida. Ikiwa huwezesha hali ya msingi, utahitaji kutumia kihariri cha HTML WYSIWYG katika mipangilio ya CrashDetector ili kuingiza viungo. Ikiwa unatumia hali ya msingi, kiungo cha usaidizi kitakiliwa kiotomatiki kutoka Crash Assistant. Ili kubadilisha rangi na mipangilio katika kiolesura cha picha (GUI), unapaswa kwenda kwenye mipangilio ya CrashDetector, kufikia eneo la CDSkinCape/GUI Editor na kuchagua aina ya GUI, pamoja na utekelezaji maalum wake. Picha zinaweza kuhaririwa kutoka eneo lifuatalo: "
				+ Statics.carpeta.resolve("imagenes").toString()
				+ ". Hati za JEXL pia hazita hamishwa. Ikiwa unahitaji tu kufanya uchambuzi wa msingi kupitia ulinganifu wa nyaraka au maneno ya kawaida, unaweza kutumia kazi ya 'Agregar Razones' katika usanidi wa shirika; ikiwa unahitaji vipengele vya hali ya juu zaidi, unapaswa kuunda ugani katika Java: https://github.com/FeatureCreepEAP/crashdetector-tutorial-extention-english";
	}

	@Override
	public String centroSoporteTituloCrash() {
		return "Programu ilifungwa ghafla.";
	}

	@Override
	public String centroSoporteTextoSuperior() {
		return "Kiolesura hiki cha grafiki hakipendekezwi kwa watumiaji wa juu (DIY); ikiwa wewe ni mtumiaji wa juu, tafadhali nenda kwa Faili -> Mipangilio -> Mipangilio ya "
				+ Statics.nombre_cd.obtener()
				+ " na ubadilisha Kiolesura Kuu kutoka \"kituo cha usaidizi\" kwenda \"mtindo wa lanzisha\". Ikiwa wewe ni mwandishi wa modpack, unaweza kubadilisha maandishi haya hapo.";
	}

	@Override
	public String centroSoporteTextoBajoTitulo() {
		return "Toa maelezo kuhusu kilichotokea kabla ya kushindwa.";
	}

	@Override
	public String centroSoporteTextoAviso() {
		return "Soma maandishi ya juu kwa makini. Picha ya skrini ya dirisha hii haina taarifa za kutosha.";
	}

	@Override
	public String centroSoporteArchivosDisponibles() {
		return "Faili za logi zinazopatikana";
	}

	@Override
	public String centroSoporteSubirTodoYCopiar() {
		return "Pakia yote na nakili ujumbe wenye viungo";
	}

	@Override
	public String centroSoportePedirAyuda() {
		return "Omba usaidizi katika kituo cha usaidizi";
	}

	@Override
	public String mostrarEnExplorador() {
		return "Onyesha katika kivinjari";
	}

	@Override
	public String subirYCopiarEnlace() {
		return "Pakia na nakili kiungo";
	}

	@Override
	public String salir() {
		return "Toka";
	}

	@Override
	public String colorTextoAviso() {
		return "Rangi ya maandishi ya onyo";
	}

	@Override
	public String colorBotonPrincipal() {
		return "Rangi ya kitufe kikuu";
	}

	@Override
	public String colorTextoBotonPrincipal() {
		return "Rangi ya maandishi ya kitufe kikuu";
	}

	@Override
	public String anchoVentana() {
		return "Upana wa dirisha";
	}

	@Override
	public String altoVentana() {
		return "Urefu wa dirisha";
	}

	@Override
	public String textoSuperiorPersonalizado() {
		return "Maandishi ya juu yaliyobinafsishwa";
	}

	@Override
	public String textoAvisoPersonalizado() {
		return "Maandishi ya onyo yaliyobinafsishwa";
	}

	@Override
	public String textoBajoTituloPersonalizado() {
		return "Maandishi chini ya kichwa yaliyobinafsishwa";
	}

	@Override
	public String urlSoporte() {
		return "URL ya usaidizi";
	}

	@Override
	public String leyProteccionDatosPersonales() {
		return "Sheria ya ulinzi wa data binafsi";
	}

	@Override
	public String altoFilaLog() {
		return "Urefu wa safu ya logi";
	}

	@Override
	public String anchoNombreArchivo() {
		return "Upana wa jina la faili";
	}

	@Override
	public String anchoBotonAbrir() {
		return "Upana wa kitufe cha kufungua";
	}

	@Override
	public String anchoBotonExplorador() {
		return "Upana wa kitufe cha kuonyesha katika kivinjari";
	}

	@Override
	public String anchoBotonSubir() {
		return "Upana wa kitufe cha kupakia";
	}

	@Override
	public String tamanoFuenteBotonPrincipal() {
		return "Ukubwa wa fonti ya kitufe kikuu";
	}

	@Override
	public String formatoBloqueLogs() {
		return "Umbizo la kizuizi cha logi";
	}

	@Override
	public String formatoHeaderMensaje() {
		return "Umbizo la kichwa cha ujumbe";
	}

	@Override
	public String formatoEstructuraMensaje() {
		return "Umbizo la muundo wa ujumbe";
	}

	@Override
	public String formatoLineaLog() {
		return "Umbizo la mstari wa logi";
	}

	@Override
	public String formatoSeparadorLogs() {
		return "Kitenganishi kati ya rekodi";
	}

	@Override
	public String formatoModlistDiff() {
		return "Umbizo la tofauti za mod";
	}

	@Override
	public String ocultarTextoAviso() {
		return "Ficha maandishi ya onyo";
	}

	@Override
	public String mostrarLogoCuadrado() {
		return "Onyesha nembo ya mraba";
	}

	@Override
	public String rutaLogoCuadrado() {
		return "Njia ya nembo ya mraba";
	}

	@Override
	public String tamanoLogoCuadrado() {
		return "Ukubwa wa nembo ya mraba";
	}

	@Override
	public String migradorCAModoPrincipalAviso() {
		return "Chagua jinsi ya kuhamisha GUI kuu. 'centro_soporte' inafanana zaidi na GUI chaguomsingi ya Crash Assistant, lakini haipendekezwi, hasa kwa wachezaji wa DIY, kwa sababu ina madirisha mengi ya pop-up na muundo duni; ipo tu kusaidia kuhamisha mazingira ya zamani ya Crash Assistant. 'primitiva' ni GUI rahisi sana inayotumika tu kwa ajili ya kushiriki; inapendekezwa tu kwa modpacks, siyo kwa watumiaji wa DIY, ikiwa utazima ukaguzi wote usiohitajika, na pia ina shida ya madirisha mengi ya pop-up. 'original' huhifadhi GUI yako kuu ya sasa na kwa kawaida ndiyo chaguo linalopendekezwa kwa watumiaji wengi.";
	}

	@Override
	public String migradorCASitioLoggingAviso() {
		return "Chagua kama unataka kuhamisha tovuti ya logi kutoka Crash Assistant au kuhifadhi ile ya sasa ya CrashDetector. Crash Assistant mara nyingi hutumia gnomebot.dev, ambayo haipendekezwi ikilinganishwa na SecureLogger, PastesDev au CDPaste. Kwa watumiaji wengi, inapendekezwa kuhifadhi tovuti ya sasa.";
	}

	@Override
	public String ideScriptTitulo() {
		return "IDE ya Uandishi wa Hati";
	}

	@Override
	public String ideScriptBotonSidebar() {
		return "Uandishi wa Hati";
	}

	@Override
	public String ideScriptProyecto() {
		return "Mradi:";
	}

	@Override
	public String ideScriptNuevo() {
		return "Mpya";
	}

	@Override
	public String ideScriptAbrirCarpeta() {
		return "Fungua folda";
	}

	@Override
	public String ideScriptAbrirArchivo() {
		return "Fungua faili";
	}

	@Override
	public String ideScriptGuardarComo() {
		return "Hifadhi kama";
	}

	@Override
	public String ideScriptDescargarDeps() {
		return "Pakua deps";
	}

	@Override
	public String ideScriptCompletar() {
		return "IntelliSense";
	}

	@Override
	public String ideScriptExplorador() {
		return "Kivinjari cha mradi";
	}

	@Override
	public String ideScriptSinArchivo() {
		return "hakuna faili";
	}

	@Override
	public String ideScriptEstado(String proyecto, String archivo) {
		return proyecto + " | " + archivo;
	}

	@Override
	public String ideScriptProyectoDeshabilitadoAviso() {
		return "Aina hii ya mradi ipo katika GUI, lakini imezimwa hadi seva yake ya lugha au kichambuzi kiwekwe.";
	}

	@Override
	public String ideScriptDeshabilitadoCorto() {
		return "(imezimwa)";
	}

	@Override
	public String ideScriptNoFaltanDependencias() {
		return "Hakuna utegemezi unao-kosekana.";
	}

	@Override
	public String ideScriptConfirmarDescargaDeps(int cantidad, String lista) {
		return "Se descargarán " + cantidad + " dependencias para el IDE de scripts:\n\n" + lista
				+ "\nDespués puede ser necesario reiniciar para que entren al classpath.";
	}

	@Override
	public String ideScriptDepsDescargadas(String mensaje) {
		return "Matokeo ya upakuzi:\n\n" + mensaje
				+ "\n\nAnzisha upya programu ikiwa darasa bado hazijaonekana kwenye classpath.";
	}

	@Override
	public String ideScriptErrorAbrirArchivo() {
		return "Hitilafu katika kufungua faili";
	}

	@Override
	public String ideScriptErrorGuardarArchivo() {
		return "Hitilafu katika kuhifadhi faili";
	}

	@Override
	public String ideScriptColorEditor() {
		return "Rangi ya kihariri";
	}

	@Override
	public String ideScriptColorKeyword() {
		return "Rangi ya maneno muhimu";
	}

	@Override
	public String ideScriptColorComentario() {
		return "Rangi ya maoni";
	}

	@Override
	public String ideScriptColorCadena() {
		return "Rangi ya tungo";
	}

	@Override
	public String ideScriptProyectoFeatureCreep() {
		return "FeatureCreep Datafied Content (DMR/JSON)";
	}

	@Override
	public String ideScriptProyectoDatapackResourcepack() {
		return "Minecraft Datapacks / ResourcePacks (JSON)";
	}

	@Override
	public String ideScriptProyectoKubeJS() {
		return "KubeJS (JS)";
	}

	@Override
	public String ideScriptProyectoZenScript() {
		return "ZenScript (CraftTweaker)";
	}

	@Override
	public String ideScriptProyectoMineFlayer() {
		return "MineFlayer (Python)";
	}

	@Override
	public String ideScriptProyectoGroovyScript() {
		return "GroovyScript";
	}

	@Override
	public String ideScriptProyectoComputerCraftLua() {
		return "ComputerCraft Lua";
	}

	@Override
	public String ideScriptProyectoWorldEditCraftScript() {
		return "WorldEdit CraftScript";
	}

	@Override
	public String ideScriptProyectoJexel3() {
		return "Jexel3";
	}

	public String mcpBotonSidebar() {
		return "MCP";
	}

	public String iaAbrirMcp() {
		return "Fungua MCP";
	}

	public String mcpTituloVentana() {
		return "CrashDetector MCP";
	}

	public String mcpTituloPrincipal() {
		return "Seva ya MCP kwa AI ya ndani";
	}

	public String mcpPuerto() {
		return "Bandari ya MCP:";
	}

	public String mcpDescargarDependencias() {
		return "Pakua utegemezi wa MCP/CFR";
	}

	public String mcpIniciarServidor() {
		return "Anzisha seva ya MCP";
	}

	public String mcpEstadoDependenciasNoCargadas() {
		return "Utegemezi wa MCP/CFR haujapakuliwa. Upakue na uanzishe upya ikiwa kitufe bado kimezimwa.";
	}

	public String mcpEstadoDependenciasCargadas() {
		return "Utegemezi wa MCP/CFR umegunduliwa. Seva ya MCP inaweza kuanzishwa.";
	}

	public String mcpDependenciasDescargadasReiniciar() {
		return "Utegemezi umepakuliwa. Ikiwa bado haujaonekana umepakuliwa, anzisha upya CrashDetector.";
	}

	public String mcpErrorDescargandoDependencias(String error) {
		return "Haikuweza kupakua utegemezi wa MCP/CFR: " + error;
	}

	public String mcpServidorIniciado(int puerto) {
		return "Seva ya MCP imeanzishwa kwenye 127.0.0.1:" + puerto + "/mcp";
	}

	public String mcpErrorIniciandoServidor(String error) {
		return "Haikuweza kuanzisha seva ya MCP: " + error;
	}

	public String mcpImagenNoDisponible() {
		return "Picha ya MCP haipatikani";
	}

	public String colorAcento() {
		return "Rangi ya alama";
	}

	public String mcpDescripcionHtml() {
		return "<b>Paneli hii inaanzisha seva ya MCP ya ndani kwa wasaidizi kama Claude Desktop, Qwen Desktop Linux, Red Hat command-line-assistant au Goose.</b>"
				+ "<br><br>"
				+ "Kwanza pakua utegemezi wa MCP/CFR. Kisha anzisha upya ikiwa darasa hazijaonekana kwenye classpath."
				+ "<br><br>" + "<b>Claude Desktop / Qwen Desktop Linux, mfano:</b>" + "<pre>{\n"
				+ "  \"mcpServers\": {\n" + "    \"crashdetector\": {\n"
				+ "      \"url\": \"http://127.0.0.1:8765/mcp\"\n" + "    }\n" + "  }\n" + "}</pre>"
				+ "<b>Goose / command-line-assistant:</b>" + "<pre>http://127.0.0.1:8765/mcp</pre>"
				+ "Kwa sasa seva hii ya MCP ni ya msingi. Baadaye zana halisi zinaweza kuongezwa kusoma logi, kuuliza CFR, kukagua mod, kutafuta darasa na kuelezea ajali.";
	}

	@Override
	public String mensajeErrorJvmDllCurseForgeG1(boolean conOverwolf) {
		String textoOverwolf = "";

		if (conOverwolf) {
			textoOverwolf = "<p>Logi pia inaonyesha dalili za <b>Overwolf</b> au DLL zinazohusiana, kama "
					+ "<b>OWClient.dll</b> au <b>OWUtils.dll</b>. Hii haithibitishi peke yake kwamba Overwolf ndio sababu, "
					+ "lakini inaashiria kwamba mchakato wa Minecraft ulikuwa ukiendeshwa katika mazingira yaliyobadilishwa zaidi "
					+ "kuliko uzinduzi wa kawaida wa vanilla.</p>";
		}

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Java ilifungwa kwa sababu ya hitilafu ya asili katika jvm.dll wakati mkusanyaji G1 alipofanya kazi katika CurseForge.</b>"
				+ "<p>Logi ina <b>EXCEPTION_ACCESS_VIOLATION</b> ndani ya <b>jvm.dll</b>, "
				+ "na thread inayofanya kazi ni <b>GCTaskThread</b>. Hii inamaanisha kwamba Java ilishindwa wakati mkusanyaji taka "
				+ "<b>G1</b> ulikuwa ukifanya kazi.</p>"
				+ "<p>Pia kuna dalili kwamba mchezo ulianzishwa kutoka kwenye mazingira yaliyosimamiwa na "
				+ "<b>CurseForge</b>, kwa mfano njia za CurseForge, <b>DCFInstanceId</b> au alama ya lanzisha ya CurseForge.</p>"
				+ textoOverwolf
				+ "<p>Haiwezi kuthibitishwa kutoka kwenye logi pekee kwamba CurseForge au Overwolf ndio sababu halisi. "
				+ "Hata hivyo, muundo huu unaonekana mara nyingi katika uzinduzi wa CurseForge na unaweza kuhusiana "
				+ "na mchanganyiko wa <b>G1 GC</b>, kumbukumbu ya asili, maktaba za Minecraft, dereva za grafiki, "
				+ "folda za asili zinazosimamiwa na CurseForge, overlay au vipengele vya nje vya lanzisha.</p>"
				+ "<p><b>Suluhisho la kawaida:</b></p>" + "<ul>"
				+ "<li>Badilisha mkusanyaji taka kwa kuongeza hoja hii ya JVM: <b>-XX:+UseShenandoahGC</b></li>"
				+ "<li>Katika CurseForge, hii kawaida huongezwa katika hoja za ziada za Java za wasifu au lanzisha.</li>"
				+ "<li>Futa kashe ya natives/bin ya wasifu wa CurseForge kulazimisha maktaba za asili zichomboleze tena.</li>"
				+ "<li>Sasisha Java na dereva za grafiki.</li>"
				+ "<li>Ikiwa unatumia overlay, programu za kurekodi, antivirus kali au zana zingine zinazoingia kwenye michezo, jaribu kuzifunga kwa muda.</li>"
				+ "</ul>" + "<p>Mafunzo yanayopendekezwa kwa Kiingereza ya kubadilisha hoja katika CurseForge: "
				+ "<a href='https://youtu.be/UKFWBOZxB2o'>https://youtu.be/UKFWBOZxB2o</a></p>"
				+ "<p><b>Kumbuka kwa Minecraft 1.16.5 au zamani:</b> matoleo hayo kawaida hutumia Java 8. "
				+ "Ikiwa uko kwenye JDK 8 na unataka kutumia Shenandoah, huenda ukahitaji kutumia "
				+ "<b>Red Hat Build of OpenJDK 8</b> na kuelekeza CurseForge kwenye usanidi huo wa Java.</p>"
				+ "<p>Mwongozo wa Red Hat wa kusakinisha OpenJDK 8 kwenye Windows: "
				+ "<a href='https://docs.redhat.com/en/documentation/red_hat_build_of_openjdk/8/html-single/installing_and_using_red_hat_build_of_openjdk_8_for_windows/index'>"
				+ "https://docs.redhat.com/en/documentation/red_hat_build_of_openjdk/8/html-single/installing_and_using_red_hat_build_of_openjdk_8_for_windows/index</a></p>";
	}

	@Override
	public String nombreErrorJvmDllCurseForgeG1() {
		return "Hitilafu ya jvm.dll ya CurseForge na G1 GC";
	}

	@Override
	public String mensajeErrorJvmDllC2Sodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Java ilifungwa kwa sababu ya hitilafu ya asili katika jvm.dll wakati ikikompili kodi ya Sodium au mod inayofanana.</b>"
				+ "<p>Logi ina <b>EXCEPTION_ACCESS_VIOLATION</b> ndani ya <b>jvm.dll</b>, "
				+ "na thread inayofanya kazi ni <b>C2 CompilerThread</b>. Hii inamaanisha kwamba hitilafu ilitokea ndani ya "
				+ "kompaila ya JIT ya Java, si kama isipokuwa ya kawaida ya Minecraft.</p>"
				+ "<p>Katika kesi hii, kazi ya kukompili inataja kodi inayohusiana na <b>Sodium</b>, "
				+ "<b>Embeddium</b>, <b>Rubidium</b> au darasa linalofanana kama "
				+ "<b>ClonedChunkSectionCache::acquire</b>.</p>" + "<p><b>Suluhisho linalopendekezwa:</b></p>" + "<ul>"
				+ "<li>Sasisha <b>Sodium</b>, <b>Embeddium</b>, <b>Rubidium</b>, <b>Oculus</b> au mod yoyote ya uchoraji inayohusiana.</li>"
				+ "<li>Sasisha Java 17. Ikiwa tayari unatumia toleo la kisasa, jaribu usambazaji mwingine kama Temurin, Zulu au Microsoft OpenJDK.</li>"
				+ "<li>Sasisha dereva za kadi ya grafiki.</li>"
				+ "<li>Jaribu kwa muda bila Sodium, Embeddium, Rubidium au Oculus ili kuthibitisha ikiwa hitilafu litapotea.</li>"
				+ "<li>Kama jaribio la juu, tumia <b>-XX:TieredStopAtLevel=1</b> kupunguza matumizi ya kompaila C2. "
				+ "Hii inaweza kupunguza utendaji, lakini husaidia kuthibitisha ikiwa hitilafu linatoka kwenye kompaila ya JIT.</li>"
				+ "</ul>" + "<p>Tatizo hili si sawa na crash ya kawaida ya mod. Pia ni tofauti na hitilafu ambapo "
				+ "<b>GCTaskThread</b> inaonekana kama thread inayofanya kazi. Hapa muundo unaelekeza zaidi kwenye kompaila C2 ya Java "
				+ "inayokompili kodi ya uchoraji iliyoboreshwa.</p>";
	}

	@Override
	public String nombreErrorJvmDllC2Sodium() {
		return "Hitilafu ya asili ya Java na Sodium / Embeddium";
	}

	@Override
	public String mensajeErrorArchivoUsadoPorOtroProceso(String archivo) {
		String textoArchivo = "";

		if (archivo != null && archivo.length() > 0) {
			textoArchivo = "<p><b>Faili imefungwa:</b> " + archivo + "</p>";
		}

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Faili inatumika na mchakato mwingine.</b>"
				+ "<p>Logi ina hitilafu ya <b>java.nio.file.FileSystemException</b> ikionyesha kuwa Windows haiwezi "
				+ "kufikia faili kwa sababu programu nyingine inaitumia.</p>" + textoArchivo
				+ "<p>Hii mara nyingi hutokea wakati Minecraft, CurseForge, kihariri cha maandishi, antivirus, OneDrive, "
				+ "programu ya usawazishaji au hata toleo lingine la mchezo limefungua faili hiyo.</p>"
				+ "<p><b>Suluhisho lililopendekezwa:</b></p>" + "<ul>" + "<li>Funga Minecraft kabisa.</li>"
				+ "<li>Funga CurseForge na uifungue tena.</li>"
				+ "<li>Angalia Meneja wa Kazi (Task Manager) na ukomeshe michakato iliyorudiwa ya <b>javaw.exe</b>, <b>java.exe</b>, Minecraft au CurseForge.</li>"
				+ "<li>Funga vihariri vya maandishi ambavyo vinaweza kuwa na faili ya usanidi wazi.</li>"
				+ "<li>Ikiwa faili iko ndani ya OneDrive, Dropbox au programu inayofanana, simamisha usawazishaji kwa muda.</li>"
				+ "<li>Ikiwa tatizo litaendelea, anzisha upya kompyuta ili kuachilia kufungwa kwa faili.</li>" + "</ul>"
				+ "<p>Baada ya kuachilia faili, anzisha tena modpack. Ikiwa faili iliyofungwa ni usanidi, "
				+ "kutengeneza nakala rudufu na kuzalisha upya faili hiyo kunaweza pia kusaidia.</p>";
	}

	@Override
	public String nombreErrorArchivoUsadoPorOtroProceso() {
		return "Faili inatumika na mchakato mwingine";
	}

}