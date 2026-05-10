package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Khmer implements Idioma {
	Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "km";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "khmer";
	}

	@Override
	public String nombre_del_idioma() {
		return "ខ្មែរ";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_camboya.png");
	}

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>មិនមែនជាថតម៉ូដដែលមាននូវសុពលភាព</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>មិនដឹងថាឯកសារ JAR របស់ CrashDetector នៅទីណាទេ</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>កំពុងស្វែងរក PID: " + String.valueOf(pid)
				+ "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") បានបិទហើយ!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>មិនមាន JVM</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>ធ្វើបច្ចុប្បន្នភាព ដ្រាយ ATI/AMD របស់អ្នក អាចជួយអ្នក។ សូមអានមគ្គុទេសក៍ដើម្បីដោះស្រាយ៖ <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>មគ្គុទេសក៍ធ្វើបច្ចុប្បន្នភាពកម្មវិធីបញ្ជា</a> https://www.amd.com/es/support/download/drivers.html ទាញយក </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>កំណែចាស់ខ្លះពេលខ្លះមាន បញ្ហាខ្លះ ជាមួយក្រាហ្វិក Nouveau ខ្លះនៅលើអេក្រង់ផ្ទុកដំបូង។</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមាន បញ្ហា ជាមួយដ្រាយក្រាហ្វិក់របស់អ្នក។ ប្រសិនបើអ្នកមាន GPU ឬ APU AMD/ATI សូមធ្វើបច្ចុប្បន្នភាពដ្រាយក្រាហ្វិក់ AMD របស់អ្នក។ ប្រសិនបើអ្នកមាន កាតក្រាហ្វិក NVIDIA សូមធានាថាបានសម្គាល់ហ្គេម និងឧបករណ៍ javaw.exe ទាំងអស់ដើម្បីប្រើ កាតក្រាហ្វិក ដែលបានផ្តាច់។ សូមអានមគ្គុទេសក៍៖ <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>មគ្គុទេសក៍ធ្វើបច្ចុប្បន្នភាពកម្មវិធីបញ្ជា</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>វីនដូ FML Early របស់អ្នកបរាជ័យ។ ដើម្បីផ្លាស់ប្តូរនេះ ទៅ (.minecraft/config/fml.toml) ហើយកែ earlyWindowProvider ដូច្នេះវា earlyWindowProvider=\"none\"។ ប្រសិនបើអ្នកស្ថិត ក្នុង Mac M1 សូមធានាថាអ្នកកំពុងប្រើ កំណែ ARM នៃ Java មិនមែន កំណែ Intel x64។ នេះក៏ជា បញ្ហា ទូទៅប្រសិនបើអ្នកមាននូវដ្រាយដែលលែងប្រើ។ ពិគ្រោះយោបល់មគ្គុទេសក៍នេះប្រសិនបើអ្នកស្ថិត ក្នុង Windows ហើយការបិទវាមិនដំណើរការ៖ <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>មគ្គុទេសក៍ធ្វើបច្ចុប្បន្នភាពកម្មវិធីបញ្ជា</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError() + "'>អ្នកមិនមានការពឹងផ្អែកចាំបាច់ទាំងអស់ទេ៖</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "បានស្នើដោយ").replace("Expected range", "ជួរដែលរំពឹងទុក") + "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>របាយការណ៍ CrashDetector របស់អ្នកនៅទីនេះ <a href='" + archivo + "' style='color:#"
				+ config.obtenerColorEnlace() + "'>មើលរបាយការណ៍</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>នេះគឺជា GUI របស់ CrashDetector។ បើហ្គេមបិទដោយគ្មានបញ្ហា សូមមិនអើពើវា។</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>មើលរបាយការណ៍</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>មើលរបាយការណ៍មូលដ្ឋានក្នុងកម្មវិធីរុករករបស់អ្នក។</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "ចែករំលែករបាយការណ៍";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "ចែករំលែករបាយការណ៍. កំណត់ហេតុរបស់អ្នកនឹងត្រូវផ្ទុកឡើងទៅ securelogger.net ហើយរបាយការណ៍របស់អ្នកនឹងត្រូវផ្ទុកទៅគេហទំព័រផ្សេង។";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ឯកសារ JAR មានបញ្ហាបានរកឃើញ (ពិចារណាលើ FATAL ដំបូង បន្ទាប់មក កម្រិត ខ្ពស់ និង ខ្សែ ទាប)៖</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> កម្រិត៖</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>អាចធ្ងន់ធ្ងរ:</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>បានរកឃើញ ModIDs មានបញ្ហា (ពិចារណាលើ FATAL ដំបូង បន្ទាប់មក កម្រិត ទាប និង ខ្សែ ទាប)៖</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>កញ្ចប់ មានបញ្ហាបានរកឃើញ (ពិចារណាលើ FATAL ដំបូង បន្ទាប់មក កម្រិត ទាប និង ខ្សែ ទាប)៖</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមាន Classes ធ្ងន់ធ្ងរ (FATAL) វាមាននូវសារៈសំខាន់ ដែលបណ្តាលមកពី Coremods មិនល្អ ឬ Fatals Dependencias។ អ្នកអាចប្រើ QuickFix ដើម្បីស្វែងរក mods ដែលមាន classes ធ្ងន់ធ្ងរ។ បានរកឃើញថ្នាក់ធ្ងន់ធ្ងរដែលបាត់៖</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ខ្លឹមសារក្នុង {} (សំខាន់បំផុតខាងលើ មានតែ 20 ដំបូង)៖</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ការកំណត់ មានបញ្ហា SpongeMixin បានរកឃើញ៖ " + "</b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមាន Mods ដែលមាន Packages/កញ្ចប់ដដែលៗ។ អ្នកអាចដោះស្រាយវាដោយលុបកញ្ចប់ (ថត) ពីឈ្មោះ jar អ្នកអាចបើក jar នៅក្នុងសូលុយស្យុង archivo ដូចជា WinRAR ឬ 7z អ្នកក៏អាចផ្លាស់ប្តូរផ្នែកបន្ថែម ឯកសារពី .jar ទៅ zip ហើយបន្ទាប់មកលុប ថត ហើយបន្ទាប់មកផ្លាស់ប្តូរវាត្រឡប់ទៅឯកសារ .jar។</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>អ្នកមាន Mods ស្ទួន</b> "
				+ linea.replace("from mod files", "ពីឯកសារ mod ");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge ដែលសង្ស័យថាម៉ូដនេះមានបញ្ហា៖</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV ត្រូវការលីថូស្ទីច អ្នកអាចដំឡើងវាដែលនេះ៖ <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ដើម្បីប្រើ Iris Shaders ឬ Oculus អ្នកត្រូវការ SODIUM ឬលម្អិត សម្រាប់ loader ផ្សេង (Rubidium, Embedium, Bedium)</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>បញ្ហា ជាមួយលម្អិត KubeJS </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "បញ្ហាបានរកឃើញ ជាមួយដ្រាយ NVIDIA នៅក្នុងកំណែចាស់ជាង Windows 11។" + "</span><br/><br/>"
				+ "ដើម្បីប្រាកដថា Minecraft (និង JVM បច្ចុប្បន្ន) ប្រើកាតក្រាហ្វិក NVIDIA ដាច់ដោយឡែក សូមអនុវត្តតាមជំហានទាំងនេះ៖<br/><br/>"
				+ "1. <strong>រកឯកសារ Java executable:</strong><br/>"
				+ "   - កម្មវិធីនេះកំពុងប្រើឯកសារ Java ដូចខាងក្រោម៖ " + obtenerRutaJava() + "<br/>"
				+ "   - ប្រសិនបើអ្នកមិនឃើញផ្លូវជាក់លាក់ អ្នកអាចរកឯកសារ Java ដោយស្វែងរក 'java.exe' នៅលើប្រព័ន្ធរបស់អ្នក។<br/><br/>"
				+ "2. <strong>បើកផ្ទាំងបញ្ជា NVIDIA៖</strong><br/>"
				+ "   - ចុចស្តាំលើ desktop ហើយជ្រើសរើស 'Panel de control de NVIDIA'។<br/><br/>"
				+ "3. <strong>កំណត់ GPU ដែលចង់ប្រើ៖</strong><br/>"
				+ "   - នៅក្នុង Panel de Control de NVIDIA ទៅ 'Administrar configuración 3D'។<br/>"
				+ "   - ជ្រើសរើសជម្រើស 'Programa específico'។<br/>"
				+ "   - ចុច 'Agregar' ហើយស្វែងរកឯកសារ Java ដែលបានរកឃើញខាងលើ (ឧ.: 'java.exe')។<br/>"
				+ "   - ធានាថាវាត្រូវបានកំណត់ដើម្បីប្រើ 'Procesador de alto rendimiento (NVIDIA)'។<br/><br/>"
				+ "4. <strong>រក្សាទុកការផ្លាស់ប្តូរ៖</strong><br/>"
				+ "   - អនុវត្តការផ្លាស់ប្តូរ ហើយបិទ Panel de Control de NVIDIA។<br/><br/>"
				+ "5. <strong>ចាប់ផ្ដើម Minecraft ឡើងវិញ៖</strong><br/>"
				+ "   - ចាប់ផ្ដើម Minecraft ឡើងវិញ ដើម្បីឱ្យការផ្លាស់ប្តូរមានប្រសិទ្ធភាព។<br/><br/>"
				+ "ប្រសិនបើអ្នកប្រើ Windows Server 2022 ឬ Windows 10 ជំហាននេះមានសុពលភាព ដរាបណាអ្នកបានដំឡើងដ្រាយ NVIDIA ប្រកបដោយផ្នែកថ្មីបំផុត។<br/><br/>"
				+ "ចំណាំ៖ ប្រសិនបើអ្នកមិនអាចរក Panel de Control de NVIDIA សូមធានាថាដ្រាយ NVIDIA ត្រូវបានដំឡើងឱ្យបានត្រឹមត្រូវ។";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "បញ្ហាបានរកឃើញ ជាមួយដ្រាយ NVIDIA នៅក្នុង Windows 11/Server 2025 ឬក្រោយ។" + "</span><br/><br/>"
				+ "ដើម្បីប្រាកដថា Minecraft (និង JVM បច្ចុប្បន្ន) ប្រើកាតក្រាហ្វិក NVIDIA ដាច់ដោយឡែក សូមអនុវត្តតាមជំហានទាំងនេះ៖<br/><br/>"
				+ "1. <strong>រកឯកសារ Java executable:</strong><br/>"
				+ "   - កម្មវិធីនេះកំពុងប្រើឯកសារ Java ដូចខាងក្រោម៖ " + obtenerRutaJava() + "<br/>"
				+ "   - ប្រសិនបើអ្នកមិនឃើញផ្លូវជាក់លាក់ អ្នកអាចរកឯកសារ Java ដោយស្វែងរក 'java.exe' នៅលើប្រព័ន្ធរបស់អ្នក។<br/><br/>"
				+ "2. <strong>បើក ការកំណត់៖</strong><br/>" + "   - ចុច <code>Win + I</code> ដើម្បីបើក ការកំណត់។<br/>"
				+ "   - រើកងាយទៅ <strong>Sistema > Pantalla > Gráficos</strong>។<br/><br/>"
				+ "3. <strong>កំណត់ GPU ដែលចង់ប្រើ៖</strong><br/>"
				+ "   - នៅក្នុងផ្នែក 'Gráficos' ចុច 'ការកំណត់ de gráficos predeterminada'។<br/>"
				+ "   - ជ្រើសរើស 'Aplicaciones de escritorio' ហើយបន្ទាប់មកចុច 'Examinar'។<br/>"
				+ "   - ស្វែងរក ហើយជ្រើសរើសឯកសារ Java ដែលបានរកឃើញខាងលើ (ឧ.: 'java.exe')។<br/>"
				+ "   - នៅពេលបានបន្ថែម ជ្រើសរើស កម្មវិធី Java នៅក្នុងតារាង ហើយកំណត់វាដើម្បីប្រើ 'Rendimiento alto (NVIDIA)'។<br/><br/>"
				+ "4. <strong>រក្សាទុកការផ្លាស់ប្តូរ៖</strong><br/>"
				+ "   - អនុវត្តការផ្លាស់ប្តូរ ហើយបិទ ការកំណត់។<br/><br/>"
				+ "5. <strong>ចាប់ផ្ដើម Minecraft ឡើងវិញ៖</strong><br/>"
				+ "   - ចាប់ផ្ដើម Minecraft ឡើងវិញ ដើម្បីឱ្យការផ្លាស់ប្តូរមានប្រសិទ្ធភាព។<br/><br/>"
				+ "ប្រសិនបើអ្នកប្រើ Windows 11 ឬ Windows Server 2025+ ជំហាននេះមានសុពលភាព ដរាបណាអ្នកបានដំឡើងដ្រាយ NVIDIA ប្រកបដោយផ្នែកថ្មីបំផុត។<br/><br/>"
				+ "ប្រសិនបើអ្នកមិនឃើញជម្រើស ការកំណត់ក្រាហ្វិក សូមធានាថាដ្រាយ NVIDIA ត្រូវបានដំឡើងឱ្យបានត្រឹមត្រូវ។";
	}

	@Override
	public String segundo60Tick() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ម៉ាស៊ីនមេ ឬ ពិភព របស់អ្នកមាន ticks ច្រើនលើ 60 វិនាទី។ នេះប្រហែលជាដោយសារ mods ធ្វើឱ្យម៉ាស៊ីនមេយឺត ឬ hardware ខ្សោយពេក។</b>";
	}

	@Override
	public String noTieneMemoria() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមិនមាន RAM/Memory គ្រប់គ្រាន់ទេ។ អ្នកត្រូវបែងចែកបន្ថែម។</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Theseus ក៏មាន បញ្ហាច្រើនទៀត រួមទាំងការបរាជ័យនៅពេលលុប mods នៅពេលអ្នកព្យាយាម។ ប្រសិនបើអ្នកត្រូវការលេង ឯកសារ mrpack អ្នកអាចប្រើ launcher ផ្សេងទៀតដូចជា Prism Launcher (សម្រាប់ modrinth.com ប៉ុណ្ណោះ) ATLauncher (សម្រាប់ modrinth.com ប៉ុណ្ណោះ) ឬ Hello Minecraft Launcher (ឆបគ្នាជាមួយ modrinth.com និង bbsmc.net)។</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>អ្នកកំពុងប្រើ \"Omitir inicio Launcher\" (CurseForge App)។ ពេលខ្លះនេះបណ្តាលឱ្យ បញ្ហា ពិបាករកឃើញ។ នេះគឺដោយសារជម្រើស \"Omitir inicio Launcher\" នៅក្នុងកំណែចាស់នៃ CurseForge App ឬក្នុង កំណែ ថ្មី។ បិទវា ហើយប្រើជម្រើស \"Mojang Launcher\" នៅក្នុងការកំណត់ CurseForge។ អ្នកអាចមើល វីដេអូ នេះជាភាសាអង់គ្លេស ពី Claws of Berk (ទៅ 1:11) "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>នៅទីនេះ</a>។</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>ព្រមាន៖ អ្នកមាន faltas classes ព្រមាន ជាទូទៅវាល្អ ប៉ុន្តែមិនមែនទេ វាខុសគ្នាពី faltas classes ធ្ងន់ធ្ងរ។ Coremods មិនល្អ ឬ faltas dependencias គឺហេតុផលទូទៅសម្រាប់ បញ្ហា នេះ។ អ្នកអាចប្រើ QuickFix ដើម្បីស្វែងរក mods ដែលមាន classes។ Classes បាត់បានរកឃើញ៖</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>គ្មានលទ្ធផល</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>ទីតាំង Log:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>នេះគឺជាលទ្ធផលនៃការត្រួតពិនិត្យរបស់អ្នក។ សូមពិនិត្យយឺតៗ ជាទូទៅ មូលហេតុត្រឹមត្រូវស្ថិតនៅក្នុងការត្រួតពិនិត្យទី 1 ឬ 2 ខណៈដែលកំហុសផ្សេងៗ (កំហុស 3+) អាចប្រើសម្រាប់បញ្ជាក់ ប៉ុន្តែជាទូទៅវាជាកំហុសបន្តជាច្រើន ហើយអាចមិនអើពើបាន។ កំហុសកើតឡើងជាលំដាប់ថ្នាក់ ដូច្នេះការដោះស្រាយបញ្ហាត្រឹមត្រូវនឹងដោះស្រាយកំហុសនេះសម្រាប់ពេលនេះ ប៉ុន្តែអាចមានកំហុសថ្មីមួយកើតឡើងនៅពេលក្រោយ ដែលមិនទាក់ទងនឹងកំហុសបច្ចុប្បន្ន ព្រោះជាញឹកញាប់ កំហុសមួយអាចរារាំងមិនឲ្យកំហុសផ្សេងទៀតបង្ហាញនៅក្នុង console បាន។</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>សូមប្រើ Java 17 សម្រាប់កំណែ 1.17-1.20.4 និង Java 21 សម្រាប់កំណែថ្មីជាងនេះ។ ប្រើ Java 8 សម្រាប់កំណែចាស់ជាងនេះ។ [Guía](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Si aún tienes បញ្ហាs, podría ser ព្រោះ algún mod tiene archivos demasiado nuevos o antiguos.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 និងខ្ពស់ជាងនេះ មិនដំណើរការជាមួយកំណែ Minecraft ទាបជាង 1.20.5 សម្រាប់ភាគច្រើននៃ mod loaders ទេ ពីព្រោះ ASM មិនទាន់បានធ្វើបច្ចុប្បន្នភាព។</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java ហួសសម័យ </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>អ្នកត្រូវការ JPMS Module " + mod_necesitas + " ពី "
				+ submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>មិនអាចហៅ " + metodo + " ព្រោះ " + objeto
				+ " មានតម្លៃ null</b>";
	}

	@Override
	public String analisisAvanzado() {
		return "វិភាគ កម្រិតខ្ពស់";
	}

	@Override
	public String seleccionarCarpeta() {
		return "ជ្រើសរើសថត";
	}

	@Override
	public String cadenaBusqueda() {
		return "ខ្សែស្វែងរក";
	}

	@Override
	public String usarRegex() {
		return "ប្រើ Regex";
	}

	@Override
	public String ignorarMayusculas() {
		return "មិនប្រកាន់អក្សរធំតូច";
	}

	@Override
	public String buscar() {
		return "ស្វែងរក";
	}

	@Override
	public String busquedaEnProgreso() {
		return "កំពុងស្វែងរក";
	}

	@Override
	public String noSeEncontraronResultados() {
		return "រកមិនឃើញលទ្ធផល";
	}

	@Override
	public String errorBusqueda() {
		return "កំហុសក្នុងការស្វែងរក";
	}

//@Override
//public String noRegistroDeLauncher() {
//	// TODO Auto-generated method stub
//	return new String ("¡No se encontraron កំណត់ហេតុ del launcher! Esto puede dificultar la investigación.\n"
//			+ "                \n"
//			+ "                Para obtener los កំណត់ហេតុ correctos:\n"
//			+ "                - MultiMC/PolyMC/PrismLauncher/PollyMC/UltimMC: NOTA: Los កំណត់ហេតុ detectados automáticamente NO son los correctos.\n"
//			+ "                  1. Abre la interfaz de la instancia\n"
//			+ "                  2. Ve a la sección \"Minecraft Log\"\n"
//			+ "                  3. Haz clic derecho y copia el contenido\n"
//			+ "                \n"
//			+ "                - LauncherFenix:\n"
//			+ "                  1. Abre la \"Consola de Desarrollador\"\n"
//			+ "                  2. Copia el contenido completo\n"
//			+ "                \n"
//			+ "                - CurseForgeApp:\n"
//			+ "                  1. Reinicia el juego SIN saltar el launcher\n"
//			+ "                  \n"
//			+ "                ");
//}

	@Override
	public String omitirYCerrar() {
		// TODO Auto-generated method stub
		return "រំលង និង បិទ";
	}

	@Override
	public String guardarYCerrar() {
		// TODO Auto-generated method stub
		return "រក្សាទុក និង បិទ";
	}

	@Override
	public String pegaLosRegistrosAqui() {
		// TODO Auto-generated method stub
		return "បិទភ្ជាប់កំណត់ហេតុនៅទីនេះ";
	}

	@Override
	public String archivo() {
		return "ឯកសារ";
	}

	@Override
	public String incluir() {
		return "រួមបញ្ចូល";
	}

	@Override
	public String abrir() {
		return "បើក";
	}

	@Override
	public String endpointDeInforme() {
		// TODO Auto-generated method stub
		return "ចំណុចបញ្ចប់របាយការណ៍";
	}

	@Override
	public String sitoDeLogging() {
		// TODO Auto-generated method stub
		return "គេហទំព័រកំណត់ហេតុ:";
	}

	@Override
	public String apiDeLogging() {
		// TODO Auto-generated method stub
		return "API កំណត់ហេតុ:";
	}

	@Override
	public String anonimizarRegistros() {
		// TODO Auto-generated method stub
		return "លុបអត្តសញ្ញាណកំណត់ហេតុ (បេតា)";
	}

	@Override
	public String botonDeCompartirInforme() {
		// TODO Auto-generated method stub
		return "ចែករំលែករបាយការណ៍ និងកំណត់ហេតុដែលបានជ្រើសទាំងអស់";
	}

	@Override
	public String arco() {
		return "ប្រអប់សន្ទនានេះអនុញ្ញាតឱ្យអ្នកចែករំលែកកំណត់ហេតុ ដោយប្រើ API របស់ SecureLogger "
				+ "នៅ <a href=\"https://securelogger.net\">securelogger.net</a>។ នៅពេលចុចប៊ូតុងចែករំលែករបាយការណ៍ "
				+ "របាយការណ៍របស់អ្នកនឹងត្រូវបានផ្ញើទៅកាន់ endpoint ដែលបានជ្រើស (លំនាំដើមគឺ asbestosstar.egoism.jp) "
				+ "(អាចផ្លាស់ប្តូរបាននៅផ្នែកខាងក្រោម)។ "
				+ "អ្នកអាចចែករំលែកកំណត់ហេតុដែលបានជ្រើសទាំងអស់ជាមួយរបាយការណ៍ផងដែរ។ ប្រសិនបើអ្នកមិនចង់ផ្ទុកឡើងទេ សូមកុំប្រើប្រអប់សន្ទនានេះ! "
				+ "យើងមិនដំណើរការរបាយការណ៍របស់អ្នកនៅ endpoint ផ្លូវការ "
				+ "(<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\">https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb</a>) ទេ; "
				+ "យើងគ្រាន់តែលុបតំណដែលមិនត្រូវបានអនុញ្ញាតប៉ុណ្ណោះ។ កូដស្ថិតនៅទីនេះ៖ "
				+ "<a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">source code</a>។ "
				+ "វាត្រូវបានប្រើតែសម្រាប់បង្ហាញព័ត៌មានអំពីការគាំងរបស់អ្នក និងតំណទៅកាន់កំណត់ហេតុប៉ុណ្ណោះ។ ទោះយ៉ាងណា "
				+ "វាអាចប្រើ endpoint ផ្ទាល់ខ្លួនដែលប្រហែលជាមិនមានវិធីសាស្ត្រដូចគ្នានោះទេ។ "
				+ "អ្នកកំពុងប្រើ site របាយការណ៍ " + Config.obtenerInstancia().obtenerSitoDeInformes()
				+ " និង site កំណត់ហេតុ " + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + "។ "
				+ "អ្នកក៏អាចចែករំលែកកំណត់ហេតុនីមួយៗដោយគ្មានរបាយការណ៍បានផងដែរ ដោយចុចប៊ូតុងចែករំលែកនៅជាប់ឈ្មោះកំណត់ហេតុនីមួយៗ; "
				+ "កំណត់ហេតុទាំងនោះនឹងត្រូវបានផ្ញើទៅកាន់ site កំណត់ហេតុដែលបានជ្រើស។ "
				+ "CrashDetector មានការលាក់អត្តសញ្ញាណកំណត់ហេតុលំនាំដើម ដែលព្យាយាមលុបឈ្មោះអ្នកប្រើ UUID "
				+ "access tokens, session IDs, អាសយដ្ឋាន IP និងទិន្នន័យផ្សេងទៀត។ ទោះជាយ៉ាងណា វាមិនល្អឥតខ្ចោះទេ។ "
				+ "លើសពីនេះទៀត អ្នកនិពន្ធ modpack អាចបិទវាបាន។ "
				+ "វាអាចបើក ឬបិទបានតាមរយៈប្រអប់ធីកនៅផ្នែកខាងក្រោមនៃអេក្រង់នេះ។ "
				+ "អ្នកគឺជាអ្នកគ្រប់គ្រងទិន្នន័យរបស់ខ្លួនឯង; អ្នកជាអ្នកសម្រេចថានឹងផ្ទុកទិន្នន័យរបស់អ្នកទៅទីណា។ "
				+ "site កំណត់ហេតុជាកម្មសិទ្ធិរបស់ភាគីទីបី ដែលជាញឹកញាប់លាក់អត្តសញ្ញាណម្ចាស់ដោយសារហេតុផលឯកជនភាព។ "
				+ "អ្នកទទួលខុសត្រូវទាំងស្រុងក្នុងការគ្រប់គ្រងទិន្នន័យរបស់អ្នក និងហានិភ័យដែលពាក់ព័ន្ធ។ "
				+ "ប្រអប់សន្ទនា ចែករំលែក របស់ CrashDetector គ្រាន់តែជាចំណុចប្រទាក់មួយដែលអនុញ្ញាតឱ្យអ្នកគ្រប់គ្រងរឿងនោះប៉ុណ្ណោះ។ "
				+ "វាសំខាន់ណាស់ដែលអ្នកត្រូវដឹងអំពី RGPD និង ARCO។ "
				+ "ប្រសិនបើអ្នកស្ថិតនៅអឺរ៉ុប អ្នកអាចប្រើ <a href=\"https://securelogger.top\">securelogger.top</a> "
				+ "ដែលបង្ហោះនៅប្រទេសអាល្លឺម៉ង់ដោយ Hetzner។ " + "សម្រាប់ព័ត៌មានផ្នែកច្បាប់បន្ថែម សូមមើលតំណខាងក្រោម៖ "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>, "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">RGPD</a>, "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">គោលនយោបាយមូលដ្ឋានស្តីពីការការពារទិន្នន័យផ្ទាល់ខ្លួននៅជប៉ុន</a>។";
	}

	@Override
	public String enlaceDelReporte() {
		// TODO Auto-generated method stub
		return "តំណរបាយការណ៍:";
	}

	@Override
	public String guardarConfigDeCompartir() {
		return "រក្សាទុកការកំណត់ចែករំលែក";
	}

	@Override
	public String registroDemasiadoGrande() {
		return "កំណត់ហេតុនេះធំពេកសម្រាប់គេហទំព័រកំណត់ហេតុនេះ។ សូមជ្រើសរើសគេហទំព័រផ្សេង ហើយព្យាយាមម្តងទៀត។";
	}

	@Override
	public String errorConPublicarRegistro(String error) {
		return "កំហុស publicando កំណត់ហេតុ " + error;
	}

	@Override
	public String apiDeRegistroNoExiste() {
		return "API កំណត់ហេតុមិនមានទេ។ សូមប្តូរ API កំណត់ហេតុនៅក្នុងការកំណត់។";
	}

	@Override
	public String errorSSL() {
		return "អ្នកមានកំហុស SSL។ វាជារឿងធម្មតាជាមួយកំណែ Java ចាស់ៗ "
				+ "រួមទាំងកំណែ Java 8 នៅក្នុង Minecraft Launcher លំនាំដើម "
				+ "និងកំណែដែលមាននៅ sun.com និង java.com។ វាប៉ះពាល់ដល់អ្វីជាច្រើន "
				+ "ដូចជា​ឯកសារ JAR របស់ MinecraftForge installer, មុខងារចែករំលែករបាយការណ៍ "
				+ "របស់ CrashDetector នៅពេលប្រើ endpoint លំនាំដើម, mods មួយចំនួនដែលត្រូវការអ៊ីនធឺណិត "
				+ "និង site កំណត់ហេតុមួយចំនួន។ ប្រសិនបើរឿងនេះកើតឡើងនៅពេលអ្នកព្យាយាមចែករំលែករបាយការណ៍ "
				+ "គ្រាន់តែភ្ជាប់រូបថតអេក្រង់មួយ ហើយជ្រើសរើស site កំណត់ហេតុដែលអាចប្រើបាន " + "ជាមួយកំណែ Java 8 ចាស់ៗ។";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JavaFML មិនឆបគ្នា៖ ត្រូវការកំណែ " + requerido
				+ ", បានរកឃើញ " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "យកចិត្តទុកដាក់! JavaFML ត្រូវការកំណែជាក់លាក់នៃ Minecraft Forge</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ឯកសារ JAR '" + archivoJar
				+ "' ត្រូវការអ្នកផ្គត់ផ្គង់ភាសា '" + proveedor + "' កំណែ '" + requerido
				+ "' ឬខ្ពស់ជាងនេះ ប៉ុន្តែបានរកឃើញតែ​កំណែ '" + encontrado + "'.</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ការព្រមាន! Crash Assistant គឺជាឧបករណ៍រកមេរោគក្លែងក្លាយ។ វាទប់ស្កាត់ដោយចេតនាមិនឲ្យហ្គេមចាប់ផ្តើម ដោយមិនគោរពសេរីភាពរបស់អ្នកក្នុងការបន្តលេងជាមួយ mods ដែលវាកំពុងចោទប្រកាន់។ "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>មើលកូដ MalwareMod.java</a>   "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>មើលកូដ JarInJarHelper.java</a>។ "
				+ "បច្ចុប្បន្ននេះ មានតែ mod នេះប៉ុណ្ណោះដែលស្ថិតនៅក្នុងបញ្ជីរបស់វា ហើយអ្វីដែលពួកគេកំពុងផ្តោតពិតប្រាកដគឺ site កំណត់ហេតុ លំនាំដើម ដែលអ្នកប្រើអាចផ្លាស់ប្តូរបាន ហើយវាកើតឡើងតែប៉ុណ្ណោះ ប្រសិនបើអ្នកជ្រើសដោយច្បាស់លាស់ប្រើមុខងារចែករំលែកកំណត់ហេតុដែលមានស្រាប់។ CrashAssistant មិនបានធ្វើការត្រួតពិនិត្យណាមួយដើម្បីកំណត់ថា site កំណត់ហេតុ ណាកំពុងត្រូវបានប្រើ ឬពន្យល់ពីរបៀបផ្លាស់ប្តូរវាទេ (មានម៉ឺនុយទម្លាក់ចុះនៅផ្នែកខាងក្រោមនៃប្រអប់សន្ទនាចែករំលែក) ហើយមិនថាអ្នកបានកំណត់ site ណាទេ CrashAssistant នឹងទប់ស្កាត់ការចាប់ផ្តើមហ្គេម។ នៅក្នុងសាររបស់ពួកគេ ពួកគេនិយាយឲ្យអ្នកធ្វើការស្រាវជ្រាវដោយខ្លួនឯង ចូរធ្វើវា សូមសិក្សាកូដរបស់ CrashDetector និង Crash Assistant ហើយយល់ថាវាធ្វើអ្វីខ្លះ កុំពឹងផ្អែកលើការអះអាងពីអំណាច។</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ម៉ូដ '" + idMod
				+ "' បានបរាជ័យ ពីព្រោះរកមិនឃើញថ្នាក់ដែលត្រូវការ: '" + claseNoEncontrada
				+ "'. សូមប្រាកដថា dependencies ទាំងអស់ត្រូវបានដំឡើងត្រឹមត្រូវ។</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Watermedia បានទប់ស្កាត់ការលេងជាមួយ TLauncher។</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "អ្នកកំពុងប្រើកំណែ Optifine សម្រាប់កំណែ Minecraft ដែលហួសសម័យ។ អ្នកត្រូវប្រើកំណែ Optifine ដែលត្រូវនឹងកំណែ Minecraft ដែលអ្នកកំពុងប្រើ។</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "មិនអាចផ្ទុកសេវា ModLauncher បានទេ៖ </b>"
				+ servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "កំហុសក្នុងការវិភាគឯកសារ JSON '" + recurso
				+ "' ពីឯកសារ JAR '" + archivoJar + "'. វាមានបញ្ហាជាមួយកំណត់ហេតុ។</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "កំហុស៖ ម៉ូដ '" + modId + "' ត្រូវការកំណែ '"
				+ requerido + "' ឬខ្ពស់ជាងនេះនៃ '" + dependencia + "', ប៉ុន្តែបានរកឃើញ '" + actual + "'." + "</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GPU របស់អ្នកមិនគាំទ្រកំណែ OpenGL ដែលត្រូវការដោយកំណែហ្គេមនេះទេ។ សូមធ្វើបច្ចុប្បន្នភាព drivers របស់អ្នក ឬប្ដូរកាតក្រាហ្វិក</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "បង្កើនអង្គចងចាំដែលបានបែងចែកឲ្យហ្គេម ឬកាត់បន្ថយការប្រើប្រាស់របស់ mods/plugins</b>";
	}

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "បានរកឃើញ JVM 32-bit៖ វាមិនអាចប្រើ RAM លើសពី 4GB បានទេ។ "
				+ "សូមដំឡើង JVM 64-bit ដើម្បីប្រើអង្គចងចាំដែលមានទាំងអស់របស់អ្នក</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "កំហុសធ្ងន់ធ្ងរផ្នែកអង្គចងចាំ PermGen។ សូមបង្កើនទំហំអង្គចងចាំអចិន្ត្រៃយ៍ ឬកាត់បន្ថយការផ្ទុក classes</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "កំហុសភាពឆបគ្នារវាង Java 8 និងកំណែទំនើបៗ</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+ មិនត្រូវគ្នាទេ - សូមប្រើ Java 8 សម្រាប់ Forge ចាស់</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ត្រូវការ Java 8 (version 52.0)។ សូមធ្វើបច្ចុប្បន្នភាព ឬកំណត់រចនាសម្ព័ន្ធឲ្យត្រឹមត្រូវ</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "កំហុសធ្ងន់ធ្ងរផ្នែកភាពឆបគ្នា៖ ប្លុកមិនត្រូវបានគាំទ្រនៅក្នុងកំណែនេះទេ។ "
				+ "សូមពិនិត្យថាកំណែ mods និង server ត្រូវគ្នា</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "កំហុសក្នុងការកំណត់រចនាសម្ព័ន្ធ monitor៖ "
				+ "មិនអាចកំណត់ screen mode បានទេ។ " + "សូមពិនិត្យ៖</b>" + "<br>- ការកំណត់ monitor ច្រើន"
				+ "<br>- drivers កាតក្រាហ្វិកដែលបានធ្វើបច្ចុប្បន្នភាព" + "<br>- resolution ដែលប្រព័ន្ធគាំទ្រ";
	}

	@Override
	public String errorOpcionesGCJava() {
		// TODO Auto-generated method stub

		return "<b style='color:#" + config.obtenerColorError() + "'>" + "កំហុសក្នុងជម្រើស Java៖ "
				+ "ជម្រើស garbage collector មានការប៉ះទង្គិចគ្នា។ "
				+ "សូមពិនិត្យថាអ្នកមិនបានបញ្ចូល algorithms GC ច្រើនក្នុង JVM parameters ក្នុងពេលតែមួយ</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "កំហុសធ្ងន់ធ្ងរនៃការកំណត់ NightConfig/Forge៖ "
				+ "ឯកសារកំណត់រចនាសម្ព័ន្ធខូច ឬមិនពេញលេញ។ "
				+ "វាអាចបណ្តាលមកពីឯកសារកំណត់រចនាសម្ព័ន្ធទទេ (ជាញឹកញាប់មានទំហំ 0 bytes) នៅក្នុងថត 'config' នៅលើកំណែ NightConfig ចាស់ៗ ឬកំណែដែលបានកែប្រែ។ "
				+ "សម្រាប់កំណែភាគច្រើន Night Config Fixes នឹងដោះស្រាយបញ្ហានេះ ប៉ុន្តែប្រសិនបើអ្នកកំពុងប្រើកំណែដែលមិនគាំទ្រ ឬកំណែ NightConfig ផ្ទាល់ខ្លួន អ្នកនឹងត្រូវលុបឯកសារកំណត់រចនាសម្ព័ន្ធដោយដៃ។ "
				+ "បញ្ហានេះជាញឹកញាប់កើតមាននៅក្នុងកំណែចាស់ៗនៃ MC Forge (ដែលភ្ជាប់មកជាមួយ NightConfig) និង mods FabricMC ចាស់ៗដែលបញ្ចូល NightConfig មកជាមួយ ប៉ុន្តែវាក៏អាចមាននៅក្នុងកំណែ NightConfig ផ្ទាល់ខ្លួនខ្លះផងដែរ។ "
				+ "ព័ត៌មានបន្ថែមអំពីដំណោះស្រាយមាននៅ <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a>។</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុស driver Intel HD Graphics។ ដំណោះស្រាយ៖</b>"
				+ "<br>1. ធ្វើបច្ចុប្បន្នភាព drivers Intel ពី <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a> (កំណែអប្បបរមា 15.xx.xx.xx)"
				+ "<br>2. នៅក្នុង Minecraft: Options → Video → បើក 'Enable VBOs' និង 'VSync'"
				+ "<br>3. បែងចែក RAM 1GB-2GB ទៅឱ្យហ្គេមនៅក្នុង launcher"
				+ "<br>4. បិទ antivirus/firewall ជាបណ្តោះអាសន្ននៅពេលធ្វើបច្ចុប្បន្នភាព";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "ការព្រមាន៖ បានរកឃើញ classes បាត់";
	}

	public String nombre_de_bloque_teselado() {
		return "កំហុសក្នុងការរេនឌ័រប្លុក";
	}

	public String nombre_de_contenido_de_stacktrace() {
		return "ការវិភាគ stack trace";
	}

	public String nombre_de_cursed_consola() {
		return "Consola CurseForge មិនពេញលេញ";
	}

	public String nombre_de_early_window() {
		return "កំហុសបង្អួចដំបូង (FMLEarlyWindow)";
	}

	public String nombre_de_drivers() {
		return "បញ្ហាជាមួយ drivers វីដេអូ";
	}

	public String nombre_de_error_de_config_mcforge() {
		return "ការកំណត់ MCForge ខូច";
	}

	public String nombre_de_error_de_monitor_lwjgl() {
		return "បរាជ័យក្នុង display mode (LWJGL)";
	}

	public String nombre_de_fabricmc_runtime_error_provided_by() {
		return "កំហុសចាប់ផ្តើមដំណើរការ FabricMC";
	}

	public String nombre_de_falta_module_jmps() {
		return "ម៉ូឌុល JPMS ដែលបាត់";
	}

	public String nombre_de_faltar_de_clases() {
		return "classes សំខាន់ៗបានបាត់";
	}

	public String nombre_de_faltas_dependencias_de_modlauncher() {
		return "dependencies របស់ ModLauncher បានបាត់";
	}

	public String nombre_de_java_versiones() {
		return "កំណែ Java មិនត្រូវគ្នា";
	}

	public String nombre_de_faltar_de_kubejs_resourcepack() {
		return "កំហុសធនធាន KubeJS";
	}

	public String nombre_de_lenguaje_proveedor_check() {
		return "អ្នកផ្តល់ភាសាមិនត្រូវគ្នា";
	}

	public String nombre_de_faltar_de_liyhostictchctov() {
		return "Litchhost";
	}

	public String nombre_de_malware_falso_crash_assistant() {
		return "បានរកឃើញមេរោគក្លែងក្លាយ false positive";
	}

	public String nombre_de_mcforge_mod_sespechoso() {
		return "បានរកឃើញ mod គួរឱ្យសង្ស័យ";
	}

	public String nombre_de_mods_duplicados_modlauncher() {
		return "mods ស្ទួននៅក្នុង ModLauncher";
	}

	public String nombre_de_modules_duplicados_jmps() {
		return "ការប៉ះទង្គិចម៉ូឌុល JPMS";
	}

	public String nombre_de_necesitas_sodium() {
		return "ត្រូវការ Sodium សម្រាប់ Iris";
	}

	public String nombre_de_no_puede_analizar_json_de_registro() {
		return "កំហុសក្នុងការវិភាគ JSON នៃកំណត់ហេតុ";
	}

	public String nombre_de_no_tiene_memoria() {
		return "អង្គចងចាំមិនគ្រប់គ្រាន់";
	}

	public String nombre_de_null_pointer() {
		return "កំហុស null pointer (NullPointerException)";
	}

	public String nombre_de_opciones_java_gc_invalidas() {
		return "ជម្រើស Java GC មិនត្រឹមត្រូវ";
	}

	public String nombre_de_optifine_obsoleta() {
		return "OptiFine ហួសសម័យ/មិនត្រូវគ្នា";
	}

	public String nombre_de_60_segundo_trick() {
		return "server tick ធ្ងន់ធ្ងរ (60s)";
	}

	public String nombre_de_servicio_de_modlauncher_no_funciona() {
		return "សេវា ModLauncher បរាជ័យ";
	}

	public String nombre_de_spongemixin_configs_problematicos() {
		return "ការកំណត់ SpongeMixing មានបញ្ហា";
	}

	public String nombre_de_theseus() {
		return "Theseus មិនត្រូវគ្នា";
	}

	public String nombre_de_watermedia_tl() {
		return "launcher TLauncher មិនត្រូវបានគាំទ្រដោយ WATERMeDIA";
	}

	@Override
	public String auditorias_transformer() {
		return "ការត្រួតពិនិត្យ Transformer";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ទាំងនេះគឺជាលទ្ធផលនៃមាតិកាពីការត្រួតពិនិត្យ Transformer នៅក្នុង Launcher Vanilla។ ជាទូទៅ វាមានភាពត្រឹមត្រូវទាបជាងកម្មវិធីវិភាគ StackTrace ប៉ុន្តែ Launcher Vanilla មិនតែងតែមានមាតិកា {} ទេ។</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "វាស្វែងរកនៅក្នុង mods របស់អ្នកសម្រាប់ mods ដែលបានបង្កើតដោយ MCreator។ ទោះបីជា mods MCreator ភាគច្រើនល្អ ហើយមាន mods ល្អៗជាច្រើនដែលបានបង្កើតដោយ MCreator ក៏ដោយ ពេលខ្លះវាអាចមានបញ្ហា ហើយមានកេរ្តិ៍ឈ្មោះមិនសូវល្អ។ វាជួយកំណត់អត្តសញ្ញាណពួកវា។ សូមចំណាំថា សូម្បីតែ mods ដែលត្រូវបានវាយតម្លៃខ្ពស់បំផុត ក៏ប្រហែលជាមិនមែនជារបស់ MCreator ពិតប្រាកដដែរ; ឧទាហរណ៍ វាអាចមានការរួមបញ្ចូលជាមួយ MCreator។";
	}

	@Override
	public String escanear() {
		return "ស្កេន";
	}

	@Override
	public String cargando() {
		return "កំពុងផ្ទុក";
	}

	@Override
	public String inicioApp() {
		// TODO Auto-generated method stub
		return "ការចាប់ផ្ដើមហ្គេម/App";
	}

	@Override
	public String ajustesCrashDetector() {
		// TODO Auto-generated method stub
		return "ការកំណត់ CrashDetector";
	}

	@Override
	public String confidencialidad() {
		// TODO Auto-generated method stub
		return "ភាពឯកជន";
	}

	@Override
	public String tooltip() {
		// TODO Auto-generated method stub
		return "ព័ត៌មានជំនួយ";
	}

	@Override
	public String config() {
		// TODO Auto-generated method stub
		return "ការកំណត់";
	}

	@Override
	public String abrirCarpeta() {
		// TODO Auto-generated method stub
		return "បើកថត";
	}

	@Override
	public String actualizar() {
		// TODO Auto-generated method stub
		return "ធ្វើបច្ចុប្បន្នភាព";
	}

	@Override
	public String anadirRegistro() {
		// TODO Auto-generated method stub
		return "បន្ថែមកំណត់ហេតុ";
	}

	@Override
	public String usarIdiomaDelSistema() {
		// TODO Auto-generated method stub
		return "ប្រើភាសាប្រព័ន្ធ";
	}

	@Override
	public String volver() {
		// TODO Auto-generated method stub
		return "ត្រឡប់";
	}

	@Override
	public String colorFondo() {
		return "ពណ៌ផ្ទៃក្រោយ (#RRGGBB):";
	}

	@Override
	public String colorTexto() {
		return "ពណ៌អត្ថបទ (#RRGGBB):";
	}

	@Override
	public String colorBoton() {
		return "ពណ៌ប៊ូតុង (#RRGGBB):";
	}

	@Override
	public String colorCajaTexto() {
		return "ពណ៌ប្រអប់អត្ថបទ (#RRGGBB):";
	}

	@Override
	public String colorEnlace() {
		return "ពណ៌តំណ (#RRGGBB):";
	}

	@Override
	public String colorTitulosConsolas() {
		return "ពណ៌ចំណងជើងកុងសូល (#RRGGBB):";
	}

	@Override
	public String colorError() {
		return "ពណ៌កំហុស (#RRGGBB):";
	}

	@Override
	public String colorAdvertencia() {
		return "ពណ៌ព្រមាន (#RRGGBB):";
	}

	@Override
	public String colorInfo() {
		return "ពណ៌ព័ត៌មាន (#RRGGBB):";
	}

	@Override
	public String colorTitulo() {
		return "ពណ៌ចំណងជើង (#RRGGBB):";
	}

	@Override
	public String colorEnlaceTexto() {
		return "ពណ៌អត្ថបទតំណ (#RRGGBB):";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "បើក CrashDetector តែពេលបរាជ័យ";
	}

	@Override
	public String activar_parche() {
		return "បើកបំណះ";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "មិនមានដំណោះស្រាយ";
	}

	@Override
	public String error() {
		return "កំហុស";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "កំហុស al លុប Jar";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "បានលុប JAR ដោយជោគជ័យ";
	}

	@Override
	public String exito() {
		return "ជោគជ័យ";
	}

	@Override
	public String eliminar() {
		return "លុប";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "កំហុសក្នុងការលុបកញ្ចប់";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "បានលុបកញ្ចប់ដោយជោគជ័យ";
	}

	@Override
	public String eliminar_paquete() {
		return "លុបកញ្ចប់";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "មិនបានរកឃើញ mods ដែលមានកញ្ចប់នេះទេ";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "មិនអាចលុបកញ្ចប់នេះបានទេ";
	}

	@Override
	public String eliminar_jar() {
		return "លុប JAR";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "មិនបានរកឃើញ mods ស្ទួនទេ";
	}

	@Override
	public String archivo_no_encontrado() {
		return "រកមិនឃើញឯកសារ";
	}

	@Override
	public String directorio_eliminado() {
		return "បានលុបថត";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "កំហុសក្នុងការលុប JAR ដែលស្ថិតនៅខាងក្នុង";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "រកមិនឃើញឯកសារខាងក្នុង";
	}

	@Override
	public String archivo_eliminado() {
		return "បានលុបឯកសារ";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "កំហុសក្នុងការលុបឯកសារ";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "ឯកសារខាងក្រៅមិនត្រឹមត្រូវ";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "បានលុបធាតុ mod";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "កំហុសក្នុងការជំនួស JAR ខាងក្រៅ";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "កំហុសក្នុងការលុបធាតុ mod";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "កំហុសក្នុងការលុបថត";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "ទ្រង់ទ្រាយមិនត្រឹមត្រូវសម្រាប់ JAR ដែលស្ថិតនៅខាងក្នុង";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "បានលុប JAR ដែលស្ថិតនៅខាងក្នុង";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "កំហុសក្នុងការសម្អាតឯកសារបណ្ដោះអាសន្ន";
	}

	@Override
	public String mensaje_de_trace_fatal_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>សារ Trace Fatal ចុងក្រោយ (មិនបានបកប្រែ):</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>សារ Trace ចុងក្រោយ (មិនបានបកប្រែ):</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "អ្នកអាចស្វែងរក mods នៅក្នុងមូលដ្ឋានទិន្នន័យ WaifuNeoForge បាន។ ជ្រើសកំណែហ្គេម, mod loader និង class។ ប្រើការផ្គូផ្គងដែលស្រដៀងបំផុត។ អ្នកអាចស្វែងរកម្តងក្នុងមួយនាទី។";
	}

	@Override
	public String solucionFaltasClases() {
		return "អ្នកអាចស្វែងរក mods នៅក្នុងមូលដ្ឋានទិន្នន័យ WaifuNeoForge បាន។ ជ្រើសកំណែហ្គេម, mod loader និង class។ ប្រើការផ្គូផ្គងដែលស្រដៀងបំផុត។ អ្នកអាចស្វែងរកម្តងក្នុងមួយនាទី។";
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "launcher ទាំងពីរមានកំណែ Java ត្រឹមត្រូវខ្លះៗ ប៉ុន្តែមិនមែនទាំងអស់ទេ។ អ្នកអាចដំឡើងកំណែ Java ត្រឹមត្រូវពី package manager នៅលើប្រព័ន្ធរបស់អ្នក ឬដោយប្រើប៊ូតុងទាំងនេះ។";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>mod ដែលបាត់ animation: " + "</b>";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException (ករណីលើកលែងមិនមានធាតុ) បាត់ Animation";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "មិនបានរកឃើញ mods សម្រាប់លុបទេ";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "ជំនួសជម្រើស GC ដែលប៉ះទង្គិចគ្នា ដោយប្រើ -XX:+UseG1GC";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "បង្កើនទំហំ heap memory ដោយប្រើជម្រើស -Xmx។";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "បង្កើនទំហំ permgen memory ដោយប្រើជម្រើស -XX:MaxPermSize។";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "ប្រើ JVM 64-bit ដើម្បីបង្កើនអង្គចងចាំដែលអាចប្រើបាន។";
	}

	@Override
	public String optimizarCodigo() {
		return "ធ្វើឱ្យកូដមានប្រសិទ្ធភាពឡើង ដើម្បីកាត់បន្ថយការប្រើអង្គចងចាំ និងបង្កើនល្បឿន។";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "ប្រើ garbage collector ដែលមានប្រសិទ្ធភាព ដើម្បីកាត់បន្ថយការផ្អាករបស់កម្មវិធី។";
	}

	@Override
	public String modulos() {
		return "ម៉ូឌុល";
	}

	@Override
	public String paquete() {
		return "កញ្ចប់";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "មាន IDs បាត់។ មូលហេតុទូទៅរួមមាន mods បាត់ ឬទិន្នន័យ item បាត់។ ថតទិន្នន័យទូទៅមានដូចជា datafiedcontents/ និង kubejs/ ឬថតផ្សេងទៀតរបស់ mods។";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "កំណត់ហេតុដែលបាន map មិនត្រឹមត្រូវ";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ការបិទដែលបណ្តាលមកពី AuthMe។
	 */
	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>plugin 'AuthMe' បានបរាជ័យក្នុងការផ្ទុក ហើយបានបិទ server។</b> ";
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញនៅក្នុង interface។
	 */
	public String nombreProblemaCierreAuthMe() {
		return "បញ្ហាបិទដោយ AuthMe";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់ការបិទ server ដោយ AuthMe។
	 */
	public String solucionCierreAuthMe() {
		return "ច្បាប់ 'stopServer' បានប្តូរទៅជា 'true'។";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់ការកំណត់រចនាសម្ព័ន្ធ plugin AuthMe។
	 */
	public String solucionConfigurarPluginAuthMe() {
		return "កំណត់រចនាសម្ព័ន្ធ plugin AuthMe (plugins/AuthMe/config.yml)";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់ការដំឡើងកំណែផ្សេងទៀតនៃ plugin AuthMe។
	 */
	public String solucionInstalarVersionDiferenteAuthMe() {
		return "ដំឡើងកំណែផ្សេងទៀតនៃ plugin 'AuthMe'";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់ការលុប plugin AuthMe។
	 */
	public String solucionEliminarPluginAuthMe() {
		return "លុប plugin 'AuthMe'";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ពិភពដែលខូចដោយសារ Multiverse។
	 */
	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ពិភព '" + nombreMundo
				+ "' មិនអាចផ្ទុកបានទេ ពីព្រោះវាមានកំហុស ហើយទំនងជាខូច។</b> ";
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញនៅក្នុង interface។
	 */
	@Override
	public String nombreProblemaCargaMultiverso() {
		return "បញ្ហាក្នុងការផ្ទុក Multiverse";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់ការជួសជុលពិភពលោក។
	 */
	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "ជួសជុលពិភពលោក '" + nombreMundo + "' ឧទាហរណ៍ដោយប្រើ Minecraft Region Fixer ឬ MCEdit។";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់ការលុបថតពិភពលោក។
	 */
	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "លុបថតពិភពលោក '" + nombreMundo + "'។";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ការកំណត់រចនាសម្ព័ន្ធ PermissionsEx មិនត្រឹមត្រូវ។
	 */
	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ការកំណត់រចនាសម្ព័ន្ធនៃ extension 'PermissionsEx' មិនត្រឹមត្រូវ។</b> ";
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញនៅក្នុង interface។
	 */
	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "បញ្ហាការកំណត់រចនាសម្ព័ន្ធ PermissionsEx";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់ការកំណត់រចនាសម្ព័ន្ធ PermissionsEx។
	 */
	@Override
	public String solucionConfigurarPermissionsEx() {
		return "កំណត់រចនាសម្ព័ន្ធ plugin PermissionsEx (plugins/PermissionsEx/permissions.yml)";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់ការលុប plugin PermissionsEx។
	 */
	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "លុប plugin 'PermissionsEx'";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ plugin ដែលមានឈ្មោះស្រដៀងគ្នា។
	 */
	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>មានឯកសារ plugin ច្រើនសម្រាប់ plugin ដែលមានឈ្មោះ '"
				+ nombrePlugin + "': '" + primerPath + "' និង '" + segundoPath + "'.</b> ";
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញនៅក្នុង interface។
	 */
	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "បញ្ហាឈ្មោះ plugin មិនច្បាស់លាស់";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់ការលុប plugin ជាក់លាក់មួយ។
	 */
	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "លុប plugin '" + nombrePlugin + "'";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ករណីលើកលែងពេលផ្ទុក chunks។
	 */
	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>មានករណីលើកលែងមួយកើតឡើង ខណៈពេលពិភពលោកកំពុងផ្ទុក chunks។ ប្រសិនបើវាមានសម្រាប់ platform របស់អ្នក FeatureRecycler អាចអាចដោះស្រាយបញ្ហានេះបាន។ https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញនៅក្នុង interface។
	 */
	@Override
	public String nombreProblemaCargaChunk() {
		return "ករណីលើកលែងពេលផ្ទុក chunks";
	}

	/**
	 * Devuelve la solución de eliminar el chunk problemático.
	 */
	@Override
	public String solucionEliminarChunk() {
		return "ដក chunk ដែលមានបញ្ហាចេញពីពិភពលោក ឧទាហរណ៍ដោយប្រើ MCEdit ឬលុបឯកសារតំបន់។";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ករណីលើកលែងពេលប្រតិបត្តិពាក្យបញ្ជារបស់ plugins។
	 */
	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>plugin '" + nombrePlugin
				+ "' មិនអាចប្រតិបត្តិពាក្យបញ្ជា '/" + comando + "' បានទេ។</b> ";
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញនៅក្នុង interface។
	 */
	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "ករណីលើកលែងពេលប្រតិបត្តិពាក្យបញ្ជា plugin";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់ការដំឡើងកំណែផ្សេងទៀតនៃ plugin។
	 */
	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "ដំឡើងកំណែផ្សេងទៀតនៃ plugin '" + nombrePlugin + "'";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ dependencies តែមួយ។
	 */
	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>plugin '" + nombrePlugin
				+ "' ត្រូវការផ្នែកបន្ថែម '" + dependencia + "' ជា dependency។</b> ";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ dependencies ច្រើន។
	 */
	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>plugin '" + nombrePlugin
				+ "' ខ្វះ plugins ដែលត្រូវការដូចខាងក្រោម " + deps.toString() + ".</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "បាត់ការពឹងផ្អែករបស់ plugin";
	}

	/**
	 * Devuelve la solución de instalar un plugin específico.
	 */
	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "ដំឡើង plugin '" + nombrePlugin + "'";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់កំណែ API មិនត្រូវគ្នា។
	 */
	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>plugin '" + nombrePlugin + "' ត្រូវការកំណែ API '"
				+ versionAPI + "' ដែលមិនត្រូវគ្នាជាមួយ server បច្ចុប្បន្ន។</b> ";
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញនៅក្នុង interface។
	 */
	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "កំណែ API មិនត្រូវគ្នា";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់ដំឡើងកំណែ server ជាក់លាក់។
	 */
	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "ដំឡើងកំណែ '" + version + "' នៃកម្មវិធីម៉ាស៊ីនមេរបស់អ្នក។";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ពិភព '" + nombreMundo
				+ "' ជាស្ទួននៃពិភពផ្សេង ហើយមិនអាចផ្ទុកបានទេ។</b> ";
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញនៅក្នុង interface។
	 */
	@Override
	public String nombreProblemaMundoDuplicado() {
		return "ពិភពស្ទួន";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់លុបឯកសារ 'uid.dat' នៃពិភព។
	 */
	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "លុបឯកសារ 'uid.dat' នៅក្នុងពិភព '" + nombreMundo + "'";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់លុបថតពិភពទាំងមូល។
	 */
	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "លុបថតពិភពលោក '" + nombreMundo + "'";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ entities ឬ block entities ដែលមានបញ្ហា។
	 */
	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		String color = config.obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>Entity ឬ Block Entity '</span>" + nombre
				+ "<span style='color:#" + color + "'>' ប្រភេទ '</span>" + tipo + "<span style='color:#" + color
				+ "'>' នៅទីតាំង </span>" + coords + "<span style='color:#" + color
				+ "'> កំពុងបង្កកំហុស ticking។</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'>" + "សេចក្ដីណែនាំសម្រាប់ការស្ដារឡើងវិញ៖<br>"
				+ "1. **MCForge**: ទៅ '[nombre_del_mundo]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: ទៅ 'config/neoforge-server.toml'.<br>"
				+ "   *(ចំណាំ៖ នៅក្នុង Singleplayer ពិភពស្ថិតនៅក្នុងថត 'saves')*.<br>"
				+ "3. ប្តូរ **removeErroringBlockEntities** និង **removeErroringEntities** ទៅជា **true**.<br><br>"
				+ "ជម្រើសផ្សេងៗ៖<br>" + "- **MCEdit**: ប្រើវាដើម្បីលុប entity ដោយដៃនៅទីតាំងដែលបានបញ្ជាក់។<br>"
				+ "- **Neruina (Mod)**: អាចជួយទប់ស្កាត់ crash ប៉ុន្តែមិនតែងតែដំណើរការ និងអាចធ្វើឱ្យការដោះស្រាយបញ្ហាពិបាក។"
				+ "</span>";

		return mensajeBase + instrucciones;
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហា។
	 */
	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "Block Entity មានបញ្ហា";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់លុប block entity។
	 */
	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "លុប entity '" + nombre + "' នៅទីតាំង " + coords + " ដោយប្រើ MCEdit ឬកែសម្រួលពិភពដោយផ្ទាល់។";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ mods ស្ទួនក្នុង Fabric។
	 */
	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ម៉ូដ '" + nombreMod
				+ "' មានកំណែច្រើនបានដំឡើង។</b> ";
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហា។
	 */
	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "ម៉ូដស្ទួនក្នុង Fabric";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់លុប mod ស្ទួន។
	 */
	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "លុបឯកសារ mod ស្ទួន៖ " + rutaMod;
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ mods មិនឆបគ្នា។
	 */
	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>mods '" + primerMod + "' និង '" + segundoMod
				+ "' មិនឆបគ្នានឹងគ្នាទេ។</b> ";
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញនៅក្នុង interface។
	 */
	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "ម៉ូដមិនឆបគ្នាក្នុង Fabric";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយសម្រាប់ការលុប mod មិនឆបគ្នាទីមួយ។
	 */
	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "លុប mod '" + nombreMod + "'";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ mods ដែលមានបញ្ហាធ្ងន់ធ្ងរ។
	 */
	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ម៉ូដ '" + nombreMod
				+ "' មានកំហុសធ្ងន់ធ្ងរ ហើយមិនអាចដំណើរការបានទេ។</b> ";
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញនៅក្នុង interface។
	 */
	@Override
	public String nombreProblemaModFatal() {
		return "ម៉ូដមានកំហុសធ្ងន់ធ្ងរ";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ dependencies បាត់ក្នុង mods (ពហុវចនៈ)។
	 */
	@Override
	public String mensajeModDependenciaPlural(List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0) {
				deps.append(", ");
			}
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>mods ខាងក្រោមត្រូវការ ប៉ុន្តែមិនបានដំឡើងទេ៖ "
				+ deps.toString() + ".</b>";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ dependency mod ដែលបាត់ (ឯកវចនៈ)។
	 */
	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>ម៉ូដ '" + nombreMod + "' ត្រូវការ mod '"
					+ dependencia + "' ជា dependency។</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>ម៉ូដ '" + nombreMod + "' ត្រូវការ mod '"
					+ dependencia + "' ជាមួយកំណែ " + version + "។</b>";
		}
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaDependenciaMod() {
		return "បាត់ការពឹងផ្អែករបស់ម៉ូដ";
	}

	/**
	 * Devuelve la solución de instalar un mod específico.
	 */
	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "ដំឡើងម៉ូដ '" + nombreMod + "'";
	}

	/**
	 * Devuelve la solución de instalar un mod con versión específica.
	 */
	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "ដំឡើងម៉ូដ '" + nombreMod + "' ជាមួយកំណែ " + version;
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ plugin ដែលមិនគាំទ្រ regional ticking (ឯកវចនៈ)។
	 */
	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' មិនឆបគ្នាជាមួយ regional ticking របស់ Folia ទេ។</b> ";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ plugins ច្រើនដែលមិនគាំទ្រ regional ticking (ពហុវចនៈ)។
	 */
	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Plugin ខាងក្រោមមិនឆបគ្នាជាមួយ regional ticking របស់ Folia ទេ៖ ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញក្នុងចំណុចប្រទាក់។
	 */
	@Override
	public String nombreProblemaPluginTickingRegional() {
		return "Plugin មិនឆបគ្នាជាមួយ Regional Ticking";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយដោយដំឡើង software ដែលគ្មាន regional ticking។
	 */
	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "ដំឡើង software ដែលគ្មាន regional ticking ដូចជា " + software;
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ mod មួយដែលបាត់នៅក្នុង datapack (ឯកវចនៈ)។
	 */
	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ម៉ូដ '" + nombreMod
				+ "' កំពុងបាត់នៅក្នុង datapack។</b>";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ mods ច្រើនដែលបាត់នៅក្នុង datapack (ពហុវចនៈ)។
	 */
	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ម៉ូដខាងក្រោមកំពុងបាត់នៅក្នុង datapack៖ ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" និង ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញក្នុងចំណុចប្រទាក់។
	 */
	@Override
	public String nombreProblemaModFaltanteEnDatapack() {
		return "ម៉ូដខ្វះនៅក្នុង datapack";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ mod មួយដែលបានបង្កើតកំហុស (ឯកវចនៈ)។
	 */
	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ម៉ូដ '" + nombreMod + "' បានបង្កើតកំហុសមួយ។</b>";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ mods ច្រើនដែលបានបង្កើតកំហុស (ពហុវចនៈ)។
	 */
	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ម៉ូដខាងក្រោមបានបង្កើតកំហុស៖ ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" និង ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញក្នុងចំណុចប្រទាក់។
	 */
	@Override
	public String nombreProblemaModExcepcion() {
		return "ម៉ូដ Forge មានកំហុស";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយដោយដំឡើងកំណែផ្សេងនៃ mod។
	 */
	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "ដំឡើងកំណែផ្សេងនៃម៉ូដ '" + nombreMod + "'";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ mod មួយដែលមិនឆបគ្នាជាមួយកំណែ Minecraft (ឯកវចនៈ)។
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ម៉ូដ '" + nombreMod
				+ "' មិនឆបគ្នាជាមួយកំណែ Minecraft " + versionMC + ".</b>";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ mods ច្រើនដែលមិនឆបគ្នាជាមួយ Minecraft (ពហុវចនៈ)។
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ម៉ូដខាងក្រោមមិនឆបគ្នាជាមួយកំណែ Minecraft ទេ៖ ");

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

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញក្នុងចំណុចប្រទាក់។
	 */
	@Override
	public String nombreProblemaModIncompatibleConMinecraft() {
		return "ម៉ូដមិនឆបគ្នាជាមួយ Minecraft";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយដោយដំឡើងកំណែ Forge ដែលឆបគ្នា។
	 */
	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "ដំឡើងកំណែ Forge ដែលឆបគ្នាជាមួយ Minecraft " + versionMC;
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ mod មួយដែលបាត់ (ឯកវចនៈ)។
	 */
	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ម៉ូដ '" + nombreMod
				+ "' កំពុងបាត់ ហើយត្រូវការ​សម្រាប់បើកពិភពលោក ឬ plugin។</b>";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "ម៉ូដខ្វះ";
	}

	/**
	 * Devuelve el mensaje de error para un mod faltante en el mundo (singular).
	 */
	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ពិភពនេះត្រូវបានរក្សាទុកជាមួយម៉ូដ '" + nombreMod
				+ "' ដែលហាក់ដូចជាបាត់។</b>";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ mods ច្រើនដែលបាត់នៅក្នុងពិភពលោក (ពហុវចនៈ)។
	 */
	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ពិភពលោកនេះត្រូវបានរក្សាទុកជាមួយម៉ូដខាងក្រោម ដែលឥឡូវនេះហាក់ដូចជាបាត់៖ ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" និង ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញក្នុងចំណុចប្រទាក់។
	 */
	@Override
	public String nombreProblemaWorldModFaltante() {
		return "ម៉ូដខ្វះនៅក្នុងពិភពលោក";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ភាពខុសគ្នានៃកំណែ mod នៅក្នុងពិភពលោកមួយ (ឯកវចនៈ)។
	 */
	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ពិភពលោកនេះត្រូវបានរក្សាទុកជាមួយម៉ូដ '" + nombreMod
				+ "' កំណែ " + versionEsperada + " ប៉ុន្តែឥឡូវនេះវាស្ថិតនៅកំណែ " + versionActual + "។</b>";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ភាពខុសគ្នានៃកំណែ mod ច្រើន (ពហុវចនៈ)។
	 */
	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ម៉ូដខាងក្រោមមានភាពខុសគ្នានៃកំណែនៅក្នុងពិភពលោកដែលបានរក្សាទុក៖ ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" និង ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (បានរក្សាទុក៖ ").append(versionesEsperadas.get(i)).append(", បច្ចុប្បន្ន៖ ")
					.append(versionesActuales.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញក្នុងចំណុចប្រទាក់។
	 */
	@Override
	public String nombreProblemaVersionModMundo() {
		return "កំណែ mod នៅក្នុងពិភពលោកដែលបានរក្សាទុក";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ការព្យាយាមបើកពិភពលោកពីកំណែថ្មីជាង។
	 */
	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកបានព្យាយាមបើកពិភពលោកដែលបានបង្កើតដោយកំណែ Minecraft ថ្មីជាងនេះ។</b>";
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញក្នុងចំណុចប្រទាក់។
	 */
	@Override
	public String nombreProblemaVersionDowngrade() {
		return "ការព្យាយាមបើកពិភពលោកពីកំណែចាស់ជាង";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយដោយដំឡើងកំណែ Minecraft ថ្មីជាង។
	 */
	@Override
	public String solucionVersionDowngrade() {
		return "ដំឡើងកំណែ Minecraft ថ្មីជាង។";
	}

	/**
	 * ផ្តល់ដំណោះស្រាយដោយបង្កើតពិភពលោកថ្មី។
	 */
	@Override
	public String solucionGenerarNuevoMundo() {
		return "បង្កើតពិភពលោកថ្មី។";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ plugin មួយដែលខ្វះ dependency (ឯកវចនៈ)។
	 */
	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin + "' ត្រូវការ plugin '"
				+ dependencia + "' ជា dependency។</b>";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ plugins ច្រើនដែលខ្វះ dependencies (ពហុវចនៈ)។
	 */
	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Plugin ខាងក្រោមត្រូវការ dependencies ដែលមិនទាន់បានដំឡើង៖ ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(plugins.get(i)).append("' (").append(dependencias.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញក្នុងចំណុចប្រទាក់។
	 */
	@Override
	public String nombreProblemaDependenciaPluginFaltante() {
		return "Plugin ខ្វះ dependency";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ plugin មួយដែលមិនឆបគ្នា (ឯកវចនៈ)។
	 */
	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' មិនឆបគ្នាជាមួយកំណែបច្ចុប្បន្នរបស់ម៉ាស៊ីនមេ។</b>";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ plugins ច្រើនដែលមិនឆបគ្នា (ពហុវចនៈ)។
	 */
	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Plugin ខាងក្រោមមិនឆបគ្នាជាមួយកំណែបច្ចុប្បន្នរបស់ម៉ាស៊ីនមេទេ៖ ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" និង ");
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
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញក្នុងចំណុចប្រទាក់។
	 */
	@Override
	public String nombreProblemaPluginIncompatible() {
		return "Plugin មិនឆបគ្នាជាមួយ PocketMine-MP";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ plugin មួយដែលមានកំហុសពេលដំណើរការ (ឯកវចនៈ)។
	 */
	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' បានបង្កើតកំហុសកំឡុងពេលដំណើរការ។</b>";
	}

	/**
	 * ផ្តល់សារកំហុសសម្រាប់ plugins ច្រើនដែលមានកំហុសពេលដំណើរការ (ពហុវចនៈ)។
	 */
	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Plugin ខាងក្រោមបានបង្កើតកំហុសកំឡុងពេលដំណើរការ៖ ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" និង ");
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
	 * ផ្តល់ឈ្មោះបញ្ហាសម្រាប់បង្ហាញក្នុងចំណុចប្រទាក់។
	 */
	@Override
	public String nombreProblemaPluginEjecucion() {
		return "Plugin មានកំហុសពេលដំណើរការ";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "LegacyRandomSource ពហុខ្សែស្រឡាយ";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមានបញ្ហាជាមួយខ្សែស្រឡាយជាច្រើនដែលកំពុងចូលប្រើថ្នាក់ LegacyRandomSource។ អ្នកអាចទទួលបានព័ត៌មានបន្ថែមដោយប្រើ mod Unsafe World Random Access Detector ឬ mod C2ME។</b> ";
	}

	@Override
	public String desdeUltimoExito() {
		return "ចាប់តាំងពីជោគជ័យចុងក្រោយ";
	}

	@Override
	public String noHayCambios() {
		return "គ្មានការផ្លាស់ប្តូរ";
	}

	@Override
	public String desdeUltimoIntento() {
		return "ចាប់តាំងពីការព្យាយាមចុងក្រោយ";
	}

	@Override
	public String fallo() {
		return "បរាជ័យ";
	}

	@Override
	public String diferentesDeLasMods() {
		return "ខុសពីម៉ូដផ្សេងៗ";
	}

	@Override
	public String historialDeMods() {
		return "ប្រវត្តិម៉ូដ";
	}

	@Override
	public String archivo0() {
		return "ឯកសារ 0";
	}

	@Override
	public String archivo1() {
		return "ឯកសារ 1";
	}

	@Override
	public String comparar() {
		return "ប្រៀបធៀប";
	}

	@Override
	public String seleccionarDosArchivos() {
		return "ជ្រើសរើសឯកសារពីរ";
	}

	@Override
	public String archivoExito() {
		return "ឯកសារជោគជ័យ";
	}

	@Override
	public String archivoFalla() {
		return "ឯកសារបរាជ័យ";
	}

	@Override
	public String errorComparandoArchivos() {
		return "កំហុសក្នុងការប្រៀបធៀបឯកសារ";
	}

	@Override
	public String comparando() {
		return "កំពុងប្រៀបធៀប";
	}

	@Override
	public String con() {
		return "ជាមួយ";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
				+ "<p><b>ផ្ទាំងប្រៀបធៀបប្រវត្តិម៉ូដ</b></p>"
				+ "<p>ផ្ទាំងនេះអនុញ្ញាតឱ្យអ្នកប្រៀបធៀបបញ្ជីម៉ូដ (modules) ពីសម័យដំណើរការផ្សេងៗគ្នា។ "
				+ "សូមជ្រើសរើសឯកសារមួយពីជួរឈរខាងឆ្វេង និងមួយទៀតពីជួរឈរខាងស្តាំ ដើម្បីមើលការផ្លាស់ប្តូររវាងកំណែទាំងពីរ។</p>"

				+ "<h3>របៀបប្រើ៖</h3>" + "<ol>" + "<li><b>ការជ្រើសរើសឯកសារ៖</b> ចុចលើប៊ូតុងវិទ្យុដែលនៅជាប់ឈ្មោះឯកសារ។ "
				+ "ឯកសារដែលបញ្ចប់ដោយ <span style='color: #4CAF50; font-weight: bold;'>.exito</span> បង្ហាញពីសម័យជោគជ័យ "
				+ "ខណៈឯកសារដែលបញ្ចប់ដោយ <span style='color: #F44336; font-weight: bold;'>.falla</span> បង្ហាញពីការបរាជ័យ។</li>"

				+ "<li><b>ការប្រៀបធៀបដោយស្វ័យប្រវត្តិ៖</b> នៅពេលចុចប៊ូតុង 'Compare' ប្រព័ន្ធនឹងវិភាគបញ្ជីទាំងពីរដែលបានជ្រើស "
				+ "ហើយបង្ហាញម៉ូដដែលត្រូវបានបន្ថែម (+) ឬដកចេញ (-) ក្នុងទិសដៅនីមួយៗ។</li>"

				+ "<li><b>ការបង្ហាញលទ្ធផល៖</b> លទ្ធផលត្រូវបានបង្ហាញជាទម្រង់ HTML ជាមួយពណ៌សម្គាល់៖ " + "<ul>"
				+ "<li><span style='color: green;'>+ ម៉ូដដែលបានបន្ថែម</span></li>"
				+ "<li><span style='color: red;'>- ម៉ូដដែលបានដកចេញ</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>លក្ខណៈសំខាន់ៗ៖</h3>" + "<ul>" + "<li>គាំទ្រការជ្រើសរើសឯកសារប្រភេទណាក៏បាន (ជោគជ័យ/បរាជ័យ)។</li>"
				+ "<li>បង្ហាញភាពខុសគ្នាពីរទិស ដើម្បីកំណត់ការផ្លាស់ប្តូរបានច្បាស់លាស់។</li>"
				+ "<li>មាន scroll សម្រាប់រុករកបញ្ជីម៉ូដវែងៗ។</li>"
				+ "<li>រួមបញ្ចូលរូបភាពពន្យល់ ដើម្បីជួយបង្កើនការយល់ឃើញតាមរូបភាព។</li>" + "</ul>"

				+ "<p>បង្កើតឡើងដោយក្តីស្រឡាញ់ ដើម្បីជួយអ្នកតាមដានការផ្លាស់ប្តូរនៅក្នុងការកំណត់រចនាសម្ព័ន្ធរបស់អ្នក។</p>"
				+ "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "អ្នកអាចកំពុងមានបញ្ហាដែលពាក់ព័ន្ធនឹង IPv6។ "
				+ "មានដំណោះស្រាយពីរ៖ "
				+ "1) បន្ថែម argument JVM <code>-Djava.net.preferIPv4Stack=true</code> ទៅក្នុង launcher របស់អ្នក ឬ "
				+ "2) ប្រើប៊ូតុង 'QuickFix' នៅក្នុង CrashDetector ដើម្បីអនុវត្ត patch ដែលបើកការកំណត់នេះដោយស្វ័យប្រវត្តិ។"
				+ "</b>";
	}

	@Override
	public String parcheIPv4() {
		return "ប៉ាច់ IPV4/6";
	}

	@Override
	public String carpetaHMCL() {
		return "ថត HMCL (សម្រាប់ HelloMineCraftLauncher ប៉ុណ្ណោះ)";
	}

	@Override
	public String descripcionCurseforge() {
		return "ចំណាំ៖ មិនបង្កើតកំណត់ហេតុទេ ប្រសិនបើបានបើកជម្រើស \"Saltar Launcher\" នៅក្នុង ការកំណត់ > Minecraft";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/ផ្សេងៗ៖ ចុចស្តាំលើ instance ហើយជ្រើស \"Editar Instancia\"។ នៅក្នុងបង្អួចដែលបើកឡើង ស្វែងរកផ្នែក \"Registros de Minecraft\" ឬស្រដៀង។<br>"
				+ "កំណត់ហេតុទាំងនេះមាន STDOUT ដែលសំខាន់សម្រាប់វិនិច្ឆ័យកំហុស។";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher): អ្នកត្រូវជ្រើសថតដែល HMCL ត្រូវបានដំឡើង ហើយជ្រើសថត \".hcml\"។ កំណត់ហេតុ HMCL ត្រូវបានរក្សាទុកនៅទីនេះ ហើយមានព័ត៌មានសំខាន់អំពីកំហុស។<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix៖ Launcher មានផ្ទាំង developer ដែលបង្ហាញកំណត់ហេតុលម្អិត។ អ្នកអាចរកឃើញវានៅក្នុងម៉ឺនុយជម្រើសរបស់ launcher។";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher៖ មាន dialog popup ដែលបង្ហាញកំណត់ហេតុ។ វាមានប៊ូតុងសម្រាប់ចម្លង និង upload កំណត់ហេតុ។ កំណត់ហេតុត្រូវបានបង្កើតដោយស្វ័យប្រវត្តិពេលចាប់ផ្តើមហ្គេម និងមានព័ត៌មានសំខាន់សម្រាប់វិនិច្ឆ័យ។";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher៖ ចុចស្តាំលើ instance ហើយជ្រើស \"ការកំណត់\"។ បន្ទាប់មកទៅផ្នែក Registros ដើម្បីមើលព័ត៌មានសំខាន់ពី STDOUT។";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "តំណ Markdown៖ បិទភ្ជាប់តំណណាមួយដែលមានកំណត់ហេតុក្នុងទម្រង់ Markdown នៅទីនេះ។ ប្រព័ន្ធនឹងព្យាយាមដកយកតំណកំណត់ហេតុ (latest.log, launcher_log.txt, debug.log...) ដោយស្វ័យប្រវត្តិ ហើយវិភាគវា។";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "មិនរកឃើញកំណត់ហេតុ Launcher";
	}

	@Override
	public String imagenNoEncontrada() {
		return "រកមិនឃើញរូបភាព";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "ទូទៅ៖ សូមជ្រើសប្រភេទ launcher ដែលអ្នកកំពុងប្រើ។ កំណត់ហេតុ launcher (launcher_log.txt, stdout...) មានព័ត៌មានសំខាន់អំពីកំហុសដែលមិនបង្ហាញនៅក្នុង latest.log។ CrashDetector មិនអាចអានកំណត់ហេតុ launcher របស់អ្នកបានទេ អាចជាអ្នកមិនមានឯកសារកំណត់ហេតុ ហើយត្រូវបិទភ្ជាប់វាដោយដៃ។<br>"
				+ "សម្រាប់ព័ត៌មានបន្ថែម សូមមើល <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">បញ្ហានេះ</a>។ កំណត់ហេតុទាំងនេះមាន STDOUT ដែលចាំបាច់សម្រាប់វិនិច្ឆ័យកំហុសជាច្រើន។";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមាន class ខ្វះពី Create។ Create បានផ្លាស់ប្តូរច្រើន ហើយ class ជាច្រើនត្រូវបានលុប។ ជាពិសេសចាប់ពី Create 6 (កុម្ភៈ 2025) addon ចាស់មិនដំណើរការទៀត។ QuickFix មិនមានដំណោះស្រាយសម្រាប់បញ្ហានេះទេ។ អ្នកត្រូវ update addon ឬលុបអ្វីដែលចាស់ ឬប្រើកំណែត្រឹមត្រូវ។</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមាន class ខ្វះពី EpicFight។ EpicFight បានផ្លាស់ប្តូរច្រើន ហើយ class ជាច្រើនត្រូវបានលុប។ QuickFix មិនអាចដោះស្រាយបញ្ហានេះដោយស្វ័យប្រវត្តិទេ។ អ្នកត្រូវ update addon ឬលុបអ្វីដែលចាស់ ឬប្រើកំណែត្រឹមត្រូវ។</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកកំពុងប្រើ OpenJ9 ដែលមិនត្រូវបានគាំទ្រ។ App ជាច្រើនមិនគាំទ្រ OpenJ9 ដោយសារភាពខុសគ្នានៃ JVM។ QuickFix មិនអាចដោះស្រាយដោយស្វ័យប្រវត្តិទេ។ អ្នកត្រូវដំឡើង JDK ផ្សេងដូចជា Oracle JDK ឬ OpenJDK Hotspot។</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>កម្មវិធីនេះត្រូវការ Java 11 (JDK 11) ដើម្បីដំណើរការ។ អ្នកកំពុងប្រើកំណែចាស់ដែលមិនឆបគ្នា។ QuickFix មិនអាច update Java ដោយស្វ័យប្រវត្តិបានទេ។ សូមដំឡើង JDK 11 ឬកំណែថ្មីជាង។</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកបានកំណត់ RAM ច្រើនពេក ដែលបណ្តាលឲ្យប្រព័ន្ធខ្វះធនធាន។ វាអាចកើតឡើងពេលកំណត់ RAM លើសពីមាន ឬប្រើ JVM 32-bit។</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>ដើម្បីដោះស្រាយបញ្ហានេះ សូមកាត់បន្ថយ RAM។ 通常មិនគួរលើស 70-80% នៃ RAM ទាំងមូល។ JVM 32-bit មានកំណត់ប្រហែល 2-3GB ប៉ុណ្ណោះ។</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ដើម្បីកាត់បន្ថយ heap memory សូមបើកការកំណត់ launcher ហើយកែ Xmx។ ឧទាហរណ៍៖ 8GB → 3-4GB, 16GB → 6-8GB។ ទុក RAM សម្រាប់ OS ផង។</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ឯកសារសំខាន់ៗរបស់ Forge កំពុងបាត់។ ឯកសារ '"
				+ archivo
				+ "' មិនមាននៅក្នុងការដំឡើងរបស់អ្នកទេ។ វាជាញឹកញាប់កើតឡើងនៅពេលការដំឡើង Forge ត្រូវបានផ្អាក ឬឯកសារសំខាន់ៗត្រូវបានលុប។ QuickFix មិនអាចស្តារឯកសារទាំងនេះដោយស្វ័យប្រវត្តិបានទេ។ អ្នកត្រូវដំឡើង Forge ឡើងវិញឱ្យបានត្រឹមត្រូវពីកម្មវិធីដំឡើងផ្លូវការ។</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge មិនអាចរកឃើញកំណែ Minecraft ដែលត្រូវការបានទេ។ ត្រូវការកំណែ " + version
				+ " ប៉ុន្តែមិនមាននៅក្នុងឯកសារ '" + archivo
				+ "' ទេ។ វាកើតឡើងនៅពេលមានភាពមិនឆបគ្នារវាងកំណែ Minecraft និងកំណែ Forge ដែលអ្នកកំពុងប្រើ។ សូមប្រាកដថាអ្នកបានទាញយកកំណែ Forge ត្រឹមត្រូវដែលត្រូវនឹងកំណែ Minecraft របស់អ្នក។</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>មិនអាចរកឃើញ target 'fmlclient' ដែលចាំបាច់សម្រាប់ចាប់ផ្តើម Forge បានទេ។ នេះបង្ហាញថាការដំឡើង Forge មិនពេញលេញ ឬខូច។ ប្រហែលជាឯកសារសំខាន់ៗរបស់ Forge មិនត្រូវបានដំឡើងត្រឹមត្រូវ។ អ្នកត្រូវដំឡើង Forge ឡើងវិញដោយប្រើកម្មវិធីដំឡើងផ្លូវការ។</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>មិនអាចរកឃើញ class មេរបស់ Minecraft នៅក្នុង class loader បានទេ។ វាជាញឹកញាប់បង្ហាញថាការដំឡើង Forge មិនពេញលេញ ឬមានការប៉ះទង្គិចជាមួយ mods ផ្សេងទៀត។ ប្រហែលជាឯកសារ Minecraft ត្រូវបានខូចអំឡុងពេលដំឡើង Forge។ អ្នកត្រូវដំឡើង Forge ឡើងវិញឱ្យបានត្រឹមត្រូវ។</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ការដំឡើង Forge មិនទាន់ពេញលេញទេ។ វាអាចបណ្តាលមកពីការដំឡើងត្រូវបានផ្អាក ឯកសារត្រូវបានលុប ឬមានភាពមិនឆបគ្នាជាមួយកំណែ Minecraft របស់អ្នក។ Forge ត្រូវការឯកសារជាក់លាក់ដើម្បីដំណើរការបានត្រឹមត្រូវ ហើយមួយចំនួនកំពុងបាត់នៅក្នុងការដំឡើងបច្ចុប្បន្នរបស់អ្នក។</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "ការដំឡើង Forge មិនពេញលេញ";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ដើម្បីដោះស្រាយបញ្ហានេះ អ្នកត្រូវដំឡើង Forge ឡើងវិញឱ្យបានត្រឹមត្រូវ។ សូមប្រាកដថាអ្នកបានទាញយកកំណែដែលត្រឹមត្រូវសម្រាប់កំណែ Minecraft របស់អ្នក ហើយអនុវត្តដំណើរការដំឡើងឱ្យពេញលេញដោយមិនផ្អាក។</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "ទាញយក Forge ផ្លូវការ";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "របៀបដំឡើង Forge ឡើងវិញឱ្យបានត្រឹមត្រូវ";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>ការណែនាំសម្រាប់ដំឡើង Forge ឡើងវិញ៖</h3>" + "<ol>"
				+ "<li>ទាញយកកម្មវិធីដំឡើង Forge ត្រឹមត្រូវពីគេហទំព័រផ្លូវការ (កំណែដែលណែនាំសម្រាប់កំណែ Minecraft របស់អ្នក)</li>"
				+ "<li>បិទ launcher Minecraft របស់អ្នកឱ្យអស់ទាំងស្រុង</li>"
				+ "<li>ដំណើរការកម្មវិធីដំឡើង Forge ជា administrator</li>"
				+ "<li>ជ្រើសជម្រើស 'Installer' (មិនមែន 'Installer (run client)')</li>"
				+ "<li>ជ្រើសថត profile Minecraft របស់អ្នកនៅក្នុង launcher</li>"
				+ "<li>ចុច 'OK' ហើយរង់ចាំរហូតដល់ការដំឡើងចប់</li>"
				+ "<li>ចាប់ផ្តើម launcher ឡើងវិញ ហើយពិនិត្យថា Forge បង្ហាញនៅក្នុងបញ្ជី profiles</li>" + "</ol>"
				+ "<p><b>ចំណាំសំខាន់៖</b> ប្រសិនបើអ្នកប្រើ launcher ផ្ទាល់ខ្លួន សូមប្រាកដថាអ្នកបានជ្រើសថត profile ត្រឹមត្រូវ។</p>"
				+ "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "ការណែនាំសម្រាប់ដំឡើង Forge ឡើងវិញ";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError() + "'>កំហុស unsatisfied link: មិនអាចផ្ទុកបណ្ណាល័យ "
				+ nombreBiblioteca + " បានទេ។ ដំណោះស្រាយអាចមាន៖<br/><br/>"
				+ "a) បន្ថែមថតដែលមាន shared library ទៅក្នុង -Djava.library.path ឬ -Dorg.lwjgl.librarypath។<br/>"
				+ "b) បន្ថែមឯកសារ JAR ដែលមាន shared library ទៅក្នុង classpath។<br/><br/>"
				+ "កំហុសនេះកើតឡើងនៅពេល Minecraft មិនអាចរកឃើញឯកសារសំខាន់ៗសម្រាប់ដំណើរការ។ "
				+ "វាជាញឹកញាប់បណ្តាលមកពីការដំឡើង Minecraft មិនពេញលេញ ឬបញ្ហាសិទ្ធិរបស់ប្រព័ន្ធ។</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "កំហុស unsatisfied link";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>មិនអាចផ្ទុកបណ្ណាល័យមួយបានទេ។ ដំណោះស្រាយអាចមាន៖<br/><br/>"
				+ "a) បន្ថែមថតដែលមាន shared library ទៅក្នុង -Djava.library.path ឬ -Dorg.lwjgl.librarypath។<br/>"
				+ "b) បន្ថែមឯកសារ JAR ដែលមាន shared library ទៅក្នុង classpath។<br/><br/>"
				+ "ដំណោះស្រាយទាំងនេះសម្រាប់អ្នកប្រើកម្រិតខ្ពស់។ អ្នកប្រើភាគច្រើនគួរតែព្យាយាមដំឡើង Minecraft ឡើងវិញ មុនពេលកែប្រែប៉ារ៉ាម៉ែត្រទាំងនេះ។</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ការប៉ះទង្គិច ID៖ ID <strong>" + id
				+ "</strong> ត្រូវបានប្រើដោយ <strong>" + modOrigen + "</strong> រួចហើយ ខណៈពេលព្យាយាមបន្ថែម <strong>"
				+ modDestino + "</strong>។ វាកើតឡើងនៅពេលម៉ូដពីរប្រើ ID ដូចគ្នាសម្រាប់ធាតុខុសៗគ្នា។</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>បានលើសចំនួនអតិបរមានៃ ID ដែលអនុញ្ញាត។ វាកើតឡើងនៅពេលម៉ូដព្យាយាមចុះឈ្មោះ block ឬ item ដោយប្រើ ID ខាងក្រៅជួរដែលអនុញ្ញាតដោយកំណែ Minecraft របស់អ្នក។</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "ការប៉ះទង្គិច ID";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ដើម្បីដោះស្រាយបញ្ហានេះនៅក្នុង Minecraft 1.12.2 សូមដំឡើង <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>។ សម្រាប់ 1.7.10 ប្រើ <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>។</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ប្រើឧបករណ៍ដូចជា <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> ឬ <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> ដើម្បីដោះស្រាយការប៉ះទង្គិច ID។</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "ដំឡើង JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "ដំឡើង EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "ប្រើ IdFix Minus ឬ IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "ប្រើ Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "មើលឯកសារណែនាំភាសាជប៉ុន";
	}

	@Override
	public String escanearDeMCreator() {
		return "ស្កេន MCreator";
	}

	/**
	 * Obtiene el título de la ventana del árbol de mods.
	 */
	@Override
	public String tituloArbolDeMods() {
		return "មែកធាងម៉ូដ និងថ្នាក់";
	}

	/**
	 * Obtiene el texto para la etiqueta de tipo de búsqueda.
	 */
	@Override
	public String tipoBusqueda() {
		return "ប្រភេទ";
	}

	/**
	 * Obtiene el texto para el filtro "Todos".
	 */
	@Override
	public String filtroTodos() {
		return "ទាំងអស់";
	}

	/**
	 * Obtiene el texto para el filtro "Paquetes".
	 */
	@Override
	public String filtroPaquetes() {
		return "កញ្ចប់";
	}

	/**
	 * Obtiene el texto para el filtro "Clases".
	 */
	@Override
	public String filtroClases() {
		return "ថ្នាក់";
	}

	/**
	 * Obtiene el texto para el filtro "Métodos".
	 */
	@Override
	public String filtroMetodos() {
		return "វិធីសាស្ត្រ";
	}

	/**
	 * Obtiene el texto para el filtro "Campos".
	 */
	@Override
	public String filtroCampos() {
		return "វាល";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Campo".
	 */
	@Override
	public String filtroReferenciasCampo() {
		return "យោងទៅកាន់វាល";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Método".
	 */
	@Override
	public String filtroReferenciasMetodo() {
		return "យោងទៅកាន់វិធីសាស្ត្រ";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Clase".
	 */
	@Override
	public String filtroReferenciasClase() {
		return "យោងទៅកាន់ថ្នាក់";
	}

	/**
	 * Obtiene el texto para el tooltip del campo de búsqueda.
	 */
	@Override
	public String tipBuscar() {
		return "វាយនៅទីនេះដើម្បីស្វែងរកក្នុងមែកធាងម៉ូដ";
	}

	/**
	 * Obtiene el texto para el botón de búsqueda.
	 */
	@Override
	public String botonBuscar() {
		return "ស្វែងរក";
	}

	/**
	 * Obtiene el texto para el botón de resetear árbol.
	 */
	@Override
	public String botonResetearArbol() {
		return "កំណត់ឡើងវិញមែកធាង";
	}

	/**
	 * Obtiene el texto para indicar los mods cargados.
	 */
	@Override
	public String modsCargados() {
		return "ម៉ូដដែលបានផ្ទុក";
	}

	/**
	 * Obtiene el texto para la categoría de clases.
	 */
	@Override
	public String clases() {
		return "ថ្នាក់";
	}

	/**
	 * Obtiene el texto para la categoría de métodos.
	 */
	@Override
	public String metodos() {
		return "វិធីសាស្ត្រ";
	}

	/**
	 * Obtiene el texto para la categoría de campos.
	 */
	@Override
	public String campos() {
		return "វាល";
	}

	/**
	 * Obtiene el texto para la categoría de referencias.
	 */
	@Override
	public String referencias() {
		return "យោង";
	}

	/**
	 * Obtiene el texto para los resultados de búsqueda.
	 */
	@Override
	public String resultadosBusqueda() {
		return "លទ្ធផលស្វែងរក";
	}

	/**
	 * Obtiene el texto para la opción de buscar referencias.
	 */
	@Override
	public String buscarReferencias() {
		return "ស្វែងរកការយោង";
	}

	/**
	 * Obtiene el texto para las referencias de mod.
	 */
	@Override
	public String referenciasMod() {
		return "ការយោងរបស់ម៉ូដ";
	}

	/**
	 * Obtiene el texto para las referencias de clase.
	 */
	@Override
	public String referenciasClase() {
		return "ការយោងរបស់ថ្នាក់";
	}

	/**
	 * Obtiene el texto para las referencias de método.
	 */
	@Override
	public String referenciasMetodo() {
		return "ការយោងរបស់វិធីសាស្ត្រ";
	}

	/**
	 * Obtiene el texto para las referencias de campo.
	 */
	@Override
	public String referenciasCampo() {
		return "ការយោងរបស់វាល";
	}

	/**
	 * Obtiene el texto cuando no se encuentran referencias.
	 */
	@Override
	public String noSeEncontraronReferencias() {
		return "មិនរកឃើញការយោង";
	}

	/**
	 * Obtiene el texto para el detalle de mod.
	 */
	@Override
	public String detalleMod() {
		return "ព័ត៌មានលម្អិតម៉ូដ៖";
	}

	/**
	 * Obtiene el texto para la ubicación.
	 */
	@Override
	public String ubicacion() {
		return "ទីតាំង";
	}

	/**
	 * Obtiene el texto para los nombres.
	 */
	@Override
	public String nombres() {
		return "ឈ្មោះ";
	}

	/**
	 * Obtiene el texto para el módulo.
	 */
	@Override
	public String modulo() {
		return "ម៉ូឌុល";
	}

	/**
	 * Obtiene el texto para el detalle de clase.
	 */
	@Override
	public String detalleClase() {
		return "ព័ត៌មានលម្អិតថ្នាក់៖";
	}

	/**
	 * Obtiene el texto para el detalle de método.
	 */
	@Override
	public String detalleMetodo() {
		return "ព័ត៌មានលម្អិតវិធីសាស្ត្រ៖";
	}

	/**
	 * Obtiene el texto para el detalle de campo.
	 */
	@Override
	public String detalleCampo() {
		return "ព័ត៌មានលម្អិតវាល៖";
	}

	/**
	 * Obtiene el texto para la clase.
	 */
	@Override
	public String clase() {
		return "ថ្នាក់";
	}

	/**
	 * Obtiene el texto para el tipo.
	 */
	@Override
	public String tipo() {
		return "ប្រភេទ";
	}

	/**
	 * Obtiene el texto para las referencias a métodos.
	 */
	@Override
	public String referenciasAMetodos() {
		return "ការយោងទៅវិធីសាស្ត្រ៖";
	}

	/**
	 * Obtiene el texto para las referencias a campos.
	 */
	@Override
	public String referenciasACampos() {
		return "ការយោងទៅវាល៖";
	}

	/**
	 * Obtiene el texto para el botón de árbol de mods.
	 */
	@Override
	public String arbolDeMods() {
		return "មែកធាងម៉ូដ";
	}

	/**
	 * Obtiene el texto para método.
	 */
	@Override
	public String metodo() {
		return "វិធីសាស្ត្រ";
	}

	/**
	 * Obtiene el texto para campo.
	 */
	@Override
	public String campo() {
		return "វាល";
	}

	@Override
	public String descompilar() {
		return "បំបែកកូដ";
	}

	@Override
	public String exportar() {
		return "នាំចេញ";
	}

	@Override
	public String importar() {
		return "នាំចូល";
	}

	@Override
	public String errorImportar() {
		return "កំហុសក្នុងការនាំចូល";
	}

	@Override
	public String estructuraImportada() {
		return "រចនាសម្ព័ន្ធដែលបាននាំចូល";
	}

	@Override
	public String estructuraExportada() {
		return "រចនាសម្ព័ន្ធដែលបាននាំចេញ";
	}

	@Override
	public String errorExportar() {
		return "កំហុសក្នុងការនាំចេញ";
	}

	@Override
	public String exportando() {
		return "កំពុងនាំចេញ";
	}

	@Override
	public String exportacionTardara() {
		return "ការនាំចេញនេះនឹងចំណាយពេលបន្តិច";
	}

	@Override
	public String porFavorEspere() {
		return "សូមរង់ចាំ";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>អ្នកមិនមានឯកសារ binary របស់ VLC ទេ។ អ្នកត្រូវការឯកសារ binary របស់ VLC សម្រាប់ WaterMedia។ អ្នកត្រូវដំឡើងវាដោយដៃពី https://www.videolan.org/vlc/។</b>";
	}

	@Override
	public String descargar_vlc() {
		return "ទាញយក VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>កំហុសធ្ងន់ធ្ងរ៖ ឈ្មោះម៉ូឌុល '" + nombreModulo
				+ "' មានតួអក្សរមិនត្រឹមត្រូវ។ ផ្នែក '" + parteInvalida
				+ "' មិនមែនជា Java identifier ត្រឹមត្រូវទេ។ វាកើតឡើងនៅពេល mod មួយប្រើពាក្យបម្រុងរបស់ Java (ដូចជា 'true', 'class') ឬតួអក្សរដែលមិនត្រូវបានអនុញ្ញាតនៅក្នុងឈ្មោះរបស់វា។</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "តួអក្សរមិនត្រឹមត្រូវក្នុងឈ្មោះ Mod";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "ឈ្មោះ mod '" + nombreModulo + "' មិនត្រឹមត្រូវ ព្រោះវាមាន '" + parteInvalida
				+ "' ដែលជា​ពាក្យបម្រុងរបស់ Java ឬតួអក្សរមិនត្រូវបានអនុញ្ញាត។ "
				+ "ស្វែងរកក្នុង logs ថា mod ណាដែលត្រូវនឹងឈ្មោះនេះ (ជាទូទៅគឺឈ្មោះឯកសារ JAR)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "ចូលទៅកាន់ថតរបស់ mod ហើយកែសម្រួលឯកសារ <b>mods.toml</b> នៅក្នុងថត <b>/META-INF/</b>។ "
				+ "ប្ដូរតម្លៃ <b>modId</b> ដើម្បីឱ្យវាប្រើតែអក្សរ លេខ និងសញ្ញា underscore ប៉ុណ្ណោះ ដោយគ្មានពាក្យបម្រុងរបស់ Java";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "ឧទាហរណ៍នៃឈ្មោះត្រឹមត្រូវ៖ 'truemod_shot_enchantment' ជំនួសឱ្យ 'true.shot.enchantment'។ "
				+ "សូមចងចាំថា ឈ្មោះ mod មិនអាចមានសញ្ញាចុច (.) សញ្ញា hyphen (-) ឬពាក្យបម្រុងរបស់ Java ដូចជា 'true', 'false' ឬ 'class' បានទេ";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>កំហុសធ្ងន់ធ្ងរជាមួយ mod: '" + nombreJar
				+ "'. បាត់ field ចាំបាច់ 'mandatory' នៅក្នុង dependencies របស់វា។ វាកើតឡើងនៅពេលឯកសារ mods.toml មិនបានបញ្ជាក់ថា dependency នោះជាកាតព្វកិច្ចឬអត់។</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "Dependency របស់ Mod ដែលបាត់ Field ចាំបាច់";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "Mod ដែលមានបញ្ហាគឺ៖ <b>" + nombreJar + "</b>។ ឯកសារនេះមានកំហុសនៅក្នុងការកំណត់ dependencies របស់វា";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "បើកឯកសារ <b>mods.toml</b> នៅក្នុងថត <b>/META-INF/</b> របស់ mod <b>" + nombreJar + "</b>";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "នៅក្នុងផ្នែក dependencies សូមប្រាកដថា entry នីមួយៗមាន <b>mandatory=true</b> ឬ <b>mandatory=false</b> (ឧ. modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>កំហុសធ្ងន់ធ្ងរជាមួយ mod: '" + nombreJar
				+ "'. ការកំណត់ access transformer មិនត្រឹមត្រូវ។ វាកើតឡើងនៅពេលឯកសារកំណត់មាន syntax ខុស ឬយោងទៅកាន់ classes/methods ដែលមិនមាន។</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Access Transformer មិនត្រឹមត្រូវ";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "ម៉ូដដែលមានបញ្ហាគឺ៖ <b>" + nombreJar + "</b>។ ម៉ូដនេះមានការកំណត់ access transformer មិនត្រឹមត្រូវ";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "បើកឯកសារ <b>accessTransformer.cfg</b> នៅក្នុងម៉ូដ <b>" + nombreJar
				+ "</b> (ជាទូទៅនៅក្នុងថត root នៃឯកសារ JAR)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "កែសម្រួល syntax របស់ access transformer។ បន្ទាត់ត្រូវតែមានទម្រង់៖ <b>access class.method</b> "
				+ "(ឧ. public net.minecraft.world.entity.Entity.func_200560_a)។ "
				+ "លុបបន្ទាត់ដែលយោងទៅកាន់ classes ឬ methods ដែលមិនមាននៅក្នុងកំណែ Minecraft របស់អ្នក";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>កំហុសធ្ងន់ធ្ងរ៖ មានភាពមិនត្រូវគ្នារវាង ID របស់ mod ក្នុង annotation @Mod និងឯកសារ mods.toml។ ម៉ូដ '"
				+ nombreMod + "' មិនអាចផ្ទុកបាន ព្រោះ IDs មិនត្រូវគ្នា។</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "ភាពមិនត្រូវគ្នារវាង @Mod និង mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "ម៉ូដកំពុងអភិវឌ្ឍ '" + nombreMod
				+ "' មានភាពមិនត្រូវគ្នារវាង ID ក្នុង annotation <b>@Mod</b> និងតម្លៃនៅក្នុង <b>mods.toml</b>";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "ពិនិត្យឱ្យប្រាកដថា ID នៅក្នុងថ្នាក់ចម្បងរបស់អ្នកត្រូវគ្នាជាមួយតម្លៃ <b>modId</b> នៅក្នុងឯកសារ <b>/META-INF/mods.toml</b>។ "
				+ "ឧទាហរណ៍៖ <b>@Mod(\"mimod\")</b> ត្រូវតែដូចគ្នានឹង <b>modId=\"mimod\"</b>";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "បើអ្នកប្រើ Gradle សូមដំណើរការ <b>clean</b> បន្ទាប់ពីធ្វើការកែប្រែ ដើម្បីធានាថាធនធានត្រូវបានអាប់ដេតត្រឹមត្រូវ។ "
				+ "ពេលខ្លះឯកសារចាស់នៅតែស្ថិតក្នុងថត build";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "ម៉ាស៊ីនភ្ញៀវ" : "ម៉ាស៊ីនបម្រើ";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "ម៉ាស៊ីនបម្រើ" : "ម៉ាស៊ីនភ្ញៀវ";

		return "<b style='color:#" + config.obtenerColorError() + "'>កំហុសធ្ងន់ធ្ងរ៖ កំពុងព្យាយាមផ្ទុកថ្នាក់ '"
				+ nombreClase + "' នៅក្នុងបរិស្ថាន " + plataforma + " ប៉ុន្តែវាត្រូវបានរចនាសម្រាប់ " + plataformaOpuesta
				+ "។ <b>ប្រើមុខងារ 'Arbol de Mods' នៅខាងស្តាំ ដើម្បីស្វែងរកថា mod ណាដែលកំពុងផ្ទុកថ្នាក់នេះ</b>។ "
				+ "ម៉ូដត្រូវបានបង្កើតជាក់លាក់សម្រាប់វេទិកាមួយ ហើយមិនដំណើរការនៅលើវេទិកាផ្សេងទៀតទេ។</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "ម៉ូដនៅលើវេទិកាមិនត្រឹមត្រូវ";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "នៅក្នុងផ្ទាំង <b>Arbol de Mods</b> ស្វែងរកការយោងទៅកាន់ថ្នាក់ <b>" + nombreClase
				+ "</b> ដើម្បីកំណត់ថា mod មួយណាកំពុងបង្កបញ្ហា";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "ម៉ាស៊ីនភ្ញៀវ" : "ម៉ាស៊ីនបម្រើ";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "ម៉ាស៊ីនបម្រើ" : "ម៉ាស៊ីនភ្ញៀវ";

		return "ម៉ូដដែលបានកំណត់គឺសម្រាប់ <b>" + plataformaOpuesta + "</b> ហើយមិនគួរប្រើនៅក្នុងបរិស្ថាន " + plataforma
				+ " របស់អ្នកទេ។";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "លុបម៉ូដដែលមានបញ្ហាចេញពីថត <b>mods</b>។ បើអ្នកត្រូវការមុខងារដូចគ្នា "
				+ "សូមស្វែងរកម៉ូដជំនួសដែលត្រូវបានរចនាសម្រាប់ <b>ម៉ាស៊ីនភ្ញៀវ</b> ឬ <b>ម៉ាស៊ីនបម្រើ</b>";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("កំហុសធ្ងន់ធ្ងរ៖ ខ្វះ metadata សម្រាប់ modid '").append(modIdFaltante).append("'។ ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("ម៉ូដខាងក្រោមអាចជាមូលហេតុនៃបញ្ហា៖ <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", និងផ្សេងទៀត...");
			mensaje.append("</b>។ ");
		}

		mensaje.append("វាកើតឡើងនៅពេល mod មួយពឹងផ្អែកលើ mod ផ្សេងទៀតដែលមិនបានដំឡើង ឬមានឯកសារ mods.toml មិនត្រឹមត្រូវ។");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "ខ្វះ Metadata mods.toml";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("ម៉ូដខាងក្រោមពឹងផ្អែកលើ '").append(modIdFaltante).append("': <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", និងផ្សេងទៀត...");
			paso.append("</b>។ ប្រើមុខងារ <b>Arbol de Mods</b> ដើម្បីបញ្ជាក់ថា mod មួយណាកំពុងបង្កបញ្ហា");
			return paso.toString();
		}
		return "មាន mod មួយកំពុងពឹងផ្អែកលើ '" + modIdFaltante
				+ "' ប៉ុន្តែ mod នេះមិនបានដំឡើង។ ប្រើមុខងារ <b>Arbol de Mods</b> ដើម្បីរកឃើញ mod ដែលបង្កបញ្ហា";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "អ្នកមានជម្រើសពីរ៖<br/>" + "1. <b>ដំឡើង mod ដែលខ្វះ</b>: ស្វែងរក និងដំឡើង mod ដែលមាន ID '"
				+ modIdFaltante + "'<br/>"
				+ "2. <b>លុប mod ដែលពឹងផ្អែក</b>: ប្រសិនបើអ្នកមិនត្រូវការ មុខងារនេះ សូមលុប mod ដែលពឹងផ្អែកលើ '"
				+ modIdFaltante + "'";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "បើ mod '" + modIdFaltante + "' ជាបណ្ណាល័យ (ដូចជា 'forge', 'minecraft', 'curios') "
				+ "សូមប្រាកដថាអ្នកបានដំឡើងកំណែ Minecraft និង Forge ត្រឹមត្រូវ។ "
				+ "បើវាជា mod ធម្មតា សូមពិនិត្យទំព័រទាញយករបស់វាសម្រាប់តម្រូវការជាមុន";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>ការព្រមាន៖ មានកំហុសនៅពេលចាប់ផ្តើមប្រព័ន្ធសំឡេង។ សំឡេង និងតន្ត្រីត្រូវបានបិទ។ "
				+ "កំហុសនេះជាញឹកញាប់ទាក់ទងនឹង mod SoundPhysicsMod ហើយអាចបណ្តាលមកពីការប៉ះទង្គិចជាមួយបណ្ណាល័យសំឡេងផ្សេងៗ។</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "កំហុសប្រព័ន្ធសំឡេង";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "កំហុសនេះភាគច្រើនទាក់ទងនឹង <b>SoundPhysicsMod</b>។ ពិនិត្យមើលថាតើអ្នកមានកំណែថ្មីបំផុតដែលត្រូវគ្នាជាមួយ Minecraft របស់អ្នកឬទេ";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "បើអ្នកប្រើ mods សំឡេងផ្សេងទៀត (ដូចជា Sound Filters, Dynamic Surroundings) សូមសាកល្បងលុប SoundPhysicsMod ជាបណ្ដោះអាសន្ន ដើម្បីពិនិត្យថាវាដោះស្រាយបញ្ហាឬអត់";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "ពិនិត្យថត <b>logs</b> ដើម្បីរកសារ​បន្ថែមដែលទាក់ទងនឹង LWJGL ឬ OpenAL ដែលអាចបង្ហាញពីបញ្ហាបណ្ណាល័យសំឡេង";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("កំហុសធ្ងន់ធ្ងរ៖ ថ្នាក់ '").append(nombreClase)
				.append("' ត្រូវបានចុះបញ្ជីជាអ្នកស្តាប់ព្រឹត្តិការណ៍ ប៉ុន្តែមិនមានវិធីសាស្ត្រត្រឹមត្រូវទេ។ ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("ថ្នាក់នេះមាននៅក្នុងម៉ូដខាងក្រោម៖ <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", និងផ្សេងទៀត...");
			mensaje.append("</b>។ ");
		}

		mensaje.append("វាកើតឡើងនៅពេលថ្នាក់មួយត្រូវបានចុះបញ្ជីសម្រាប់ស្តាប់ព្រឹត្តិការណ៍ "
				+ "ប៉ុន្តែមិនមានវិធីសាស្ត្រដែលមាន annotation @SubscribeEvent ទេ។");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "ថ្នាក់ដែលបានចុះបញ្ជីដោយគ្មាន Event Listeners";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("ថ្នាក់ដែលមានបញ្ហាស្ថិតនៅក្នុងម៉ូដទាំងនេះ៖ <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", និងផ្សេងទៀត...");
			paso.append("</b>។ ម៉ូដទាំងនេះកំពុងព្យាយាមចុះបញ្ជីព្រឹត្តិការណ៍ដោយគ្មានវិធីសាស្ត្រត្រឹមត្រូវ");
			return paso.toString();
		}
		return "ថ្នាក់ <b>" + nombreClase
				+ "</b> ត្រូវបានចុះបញ្ជីសម្រាប់ស្តាប់ព្រឹត្តិការណ៍ ប៉ុន្តែមិនមានវិធីសាស្ត្រដែលមាន annotation <b>@SubscribeEvent</b> ទេ។ ប្រើមុខងារ <b>Arbol de Mods</b> ដើម្បីរកមើលថា mod មួយណាមានថ្នាក់នេះ";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "ក្នុងកូដប្រភព សូមពិនិត្យថាថ្នាក់ <b>" + nombreClase + "</b> មានយ៉ាងហោចណាស់វិធីសាស្ត្រមួយដែលមាន៖ "
				+ "<b>@SubscribeEvent public void nombreMetodo(EventoEspecifico evento) { ... }</b>។ "
				+ "បើវាជាថ្នាក់ខាងក្នុង សូមប្រាកដថាវាមិនត្រូវបានសម្គាល់ជា static ទេ";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("សម្រាប់ម៉ូដដែលបានកំណត់ (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", ជាដើម");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("ទាក់ទងអ្នកអភិវឌ្ឍន៍របស់ម៉ូដនេះដើម្បីជួសជុលបញ្ហា។ ");
			} else {
				paso.append("ទាក់ទងអ្នកអភិវឌ្ឍន៍របស់ម៉ូដទាំងនេះដើម្បីជួសជុលបញ្ហា។ ");
			}
		}

		paso.append(
				"បើអ្នកជាអ្នកអភិវឌ្ឍន៍ សូមលុបការចុះបញ្ជីថ្នាក់នេះពី EventBus ឬបន្ថែមវិធីសាស្ត្រ @SubscribeEvent ត្រឹមត្រូវ");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		String mensaje = "<b style='color:#" + config.obtenerColorError()
				+ "'>កំហុសធ្ងន់ធ្ងរ៖ មានកំហុសនៅក្នុង UnionFileSystem ពេលដំណើរការ '" + nombreArchivo + "'. ";

		mensaje += "កំហុសនេះជាញឹកញាប់កើតឡើងនៅក្នុង modpacks ដែលបានកំណត់ជាមុន ហើយទាក់ទងដោយផ្ទាល់នឹងបញ្ហារបស់ launcher។ ";

		mensaje += "ប្រព័ន្ធមិនអាចអានឯកសារម៉ូដបានត្រឹមត្រូវ ព្រោះវាខូច ឬមិនពេញលេញ។</b>";

		return mensaje;
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "កំហុស UnionFileSystem - ឯកសារខូច";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		String paso = "រកឃើញកំហុសជាក់លាក់ <b>cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException</b> ជាមួយឯកសារ <b>"
				+ nombreArchivo + "</b>.";

		paso += " នេះគឺជាកំហុសដែលគេស្គាល់នៅក្នុង launchers ពេលឯកសារមិនបានទាញយកពេញលេញ។";

		return paso;
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "ដំឡើង modpack ឡើងវិញទាំងស្រុង។ កំហុសនេះភាគច្រើនកើតឡើងពេល launcher មិនទាញយកឯកសារទាំងអស់បានពេញលេញ។ "
				+ "បើអ្នកកំពុងប្រើ <b>Luna Pixel</b> យើងណែនាំឲ្យប្រើ <b>ATLauncher</b> ជំនួស "
				+ "ព្រោះវាអាចគ្រប់គ្រងឯកសារម៉ូដបានល្អជាង និងជៀសវាងកំហុសនេះ។";
	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "បើបញ្ហានៅតែបន្តបន្ទាប់ពីដំឡើងឡើងវិញ៖ <br/>" + "1. <b>ប្ដូរទៅ launcher ផ្សេង</b><br/>"
				+ "2. បើប្រើ <b>Luna Pixel</b> សូមប្រើ <b>ATLauncher</b> ដែលមានស្ថេរភាពជាង<br/>"
				+ "3. ពិនិត្យការតភ្ជាប់អ៊ីនធឺណិត និងទំហំថាសមុនពេលដំឡើងឡើងវិញ";
	}

	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "តើចង់បើក ProxySysOutSysErr មែនទេ?\n\n"
				+ "ជម្រើសនេះអនុញ្ញាតឲ្យ CrashDetector ចូលប្រើ System.out និង System.err នៅពេល launcher មិនផ្តល់ log។\n\n"
				+ "គួរតែប្រើតែពេលអ្នកមិនអាចបញ្ចូល log ដោយដៃបាន។\n\n"
				+ "ព្រមាន៖ វាអាចប៉ះពាល់ដល់ mods ឬ launchers មួយចំនួន។\n\n"
				+ "ត្រូវចាប់ផ្តើមកម្មវិធីឡើងវិញដើម្បីអនុវត្តការផ្លាស់ប្តូរ។";
	}

	@Override
	public String confirmacionTitulo() {
		return "ការបញ្ជាក់";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr បានបើកដោយជោគជ័យ។\n\n"
				+ "ត្រូវចាប់ផ្តើម CrashDetector ឡើងវិញដើម្បីអនុវត្តការផ្លាស់ប្តូរ។";
	}

	@Override
	public String informacionTitulo() {
		return "ព័ត៌មាន";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("កំហុសធ្ងន់ធ្ងរ៖ AzureLib និង GeckoLib ត្រូវបានចាប់ផ្តើមលឿនពេក! ");
		} else if (azureLibError) {
			mensaje.append("កំហុសធ្ងន់ធ្ងរ៖ AzureLib ត្រូវបានចាប់ផ្តើមលឿនពេក! ");
		} else if (geckoLibError) {
			mensaje.append("កំហុសធ្ងន់ធ្ងរ៖ GeckoLib ត្រូវបានចាប់ផ្តើមលឿនពេក! ");
		}

		mensaje.append("វាកើតឡើងនៅពេលប្រើ mods Fabric ជាមួយ library ដែលមិនមែនសម្រាប់ Fabric។ ");

		if (connectorPresente) {
			mensaje.append("រកឃើញ mod ភ្ជាប់ ដែលបង្ហាញថាអ្នកកំពុងប្រើ mods Fabric នៅក្នុងបរិស្ថាន Forge។ ");
			mensaje.append("ពិនិត្យ log ដើម្បីរក mod ដែលបង្កបញ្ហា។ ");
		}

		mensaje.append("AzureLib និង GeckoLib ត្រូវតែផ្គូផ្គងជាមួយវេទិកាត្រឹមត្រូវ។ ");
		mensaje.append("ហ្គេមមិនអាចផ្ទុក mods បានដោយសារកំហុសនេះ។");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "Library ចាប់ផ្តើមលឿនពេក";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "ពិនិត្យ log ដើម្បីរក mod ដែលបង្កបញ្ហា";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "ប្រាកដថាប្រើកំណែ library ត្រឹមត្រូវសម្រាប់វេទិកា";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>កំហុសធ្ងន់ធ្ងរ៖ C2ME មិនត្រូវគ្នាជាមួយ mods ភ្ជាប់។ "
				+ "C2ME មិនអាចដំណើរការជាមួយ Sinytra Connector ឬ mods ស្រដៀងគ្នា។ "
				+ "<b>ប្រើ C3ME ជាជម្រើស</b> ដែលអាចដំណើរការបានត្រឹមត្រូវ។</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "C2ME មិនត្រូវគ្នា";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "លុប C2ME ចេញពីថត mods";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "ដំឡើង C3ME ជំនួស";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "អាប់ដេត mods ភ្ជាប់ទាំងអស់ទៅកំណែថ្មី";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError() + "'>កំហុសធ្ងន់ធ្ងរ៖ Plugin JEI របស់ mod '" + modId
				+ "' បរាជ័យ។ ថ្នាក់ '" + nombreClase + "' បង្កកំហុស និងបណ្តាលឲ្យហ្គេម crash។</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "JEI Plugin បរាជ័យ";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "ម៉ូដ <b>" + modId + "</b> មាន JEI plugin ខូច";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "លុបម៉ូដនេះជាបណ្ដោះអាសន្ន";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "ស្វែងរក update ឬទាក់ទងអ្នកអភិវឌ្ឍន៍";
	}

	@Override
	public String tituloLectador() {
		return "កម្មវិធីអាន Console - Crash Detector";
	}

	@Override
	public String obtenerTituloLeyenda() {
		return "សញ្ញាពណ៌";
	}

	@Override
	public String obtenerErrorEnLeyenda() {
		return "កំហុសធ្ងន់ធ្ងរ";
	}

	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "Stacktrace";
	}

	/**
	 * Obtiene el título para ventanas de error
	 * 
	 * @return Título estándar para mensajes de error
	 */
	@Override
	public String obtenerTituloError() {
		return "កំហុស";
	}

	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "មានកំហុសកើតឡើងនៅពេលដំណើរការបន្ទាត់ដែលបានជ្រើស";
	}

	@Override
	public String obtenerNombreError() {
		return "ឈ្មោះកំហុស";
	}

	@Override
	public String obtenerDescripcionError() {
		return "ពិពណ៌នាលម្អិត";
	}

	@Override
	public String obtenerSeleccionarConsola() {
		return "ជ្រើសរើសកុងសូល";
	}

	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "កំហុសមិនស្គាល់";
	}

	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "បានរកឃើញកំហុសធ្ងន់ធ្ងរនៅក្នុងកំណត់ហេតុ។ " + "សូមពិនិត្យ stack trace ដើម្បីស្វែងរកមូលហេតុពិតប្រាកដ។";
	}

	@Override
	public String obtenerErrorLecturaArchivo() {
		return "មិនអាចអានឯកសារ log បានទេ។ " + "សូមពិនិត្យថាឯកសារមាន និងមានសិទ្ធិអាន។";
	}

	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "កម្មវិធីវិភាគ Log";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("កំហុសធ្ងន់ធ្ងរ៖ បរាជ័យក្នុងការចុះបញ្ជីអ្នកទទួលសញ្ញាដោយស្វ័យប្រវត្តិសម្រាប់ mod '").append(modId)
				.append("'. ");

		mensaje.append("ថ្នាក់ដែលមានបញ្ហា៖ <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("ថ្នាក់នេះមាននៅក្នុង៖ <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", និងផ្សេងទៀត...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"វាកើតឡើងនៅពេល mod មួយព្យាយាមចុះបញ្ជីថ្នាក់ជាអ្នកទទួលសញ្ញា ប៉ុន្តែមិនអាចផ្ទុកថ្នាក់បានត្រឹមត្រូវ។ ");
		mensaje.append("<b>សូមពិនិត្យកំហុសផ្សេងៗនៅក្នុង log ព្រោះបញ្ហាពិតអាចស្ថិតនៅកន្លែងផ្សេង</b>។");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "បរាជ័យក្នុងការចុះបញ្ជីអ្នកទទួលសញ្ញា";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "ម៉ូដ <b>" + modId + "</b> កំពុងព្យាយាមចុះបញ្ជីថ្នាក់ <b>" + nombreClase
				+ "</b> ជាអ្នកទទួលសញ្ញា ប៉ុន្តែបរាជ័យ។ សូមពិនិត្យថាថ្នាក់នេះមាន និងអាចចូលប្រើបាន";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder(
					"ថ្នាក់ដែលមានបញ្ហា <b>" + nombreClase + "</b> មាននៅក្នុងឯកសារទាំងនេះ៖ <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", និងផ្សេងទៀត...");
			paso.append("</b>. ");
			paso.append("ប្រើមុខងារ <b>Arbol de Mods</b> ដើម្បីបញ្ជាក់ថាឯកសារណាដែលមានថ្នាក់នេះ");
			return paso.toString();
		}
		return "ថ្នាក់ <b>" + nombreClase + "</b> មិនត្រូវបានរកឃើញក្នុង mod ណាមួយទេ។ " + "សូមពិនិត្យថា mod <b>" + modId
				+ "</b> ត្រូវបានដំឡើងត្រឹមត្រូវ។ ប្រើ <b>Arbol de Mods</b> ដើម្បីជួយរកបញ្ហា";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "អាប់ដេតម៉ូដ <b>" + modId + "</b> ទៅកំណែថ្មីដែលត្រូវគ្នាជាមួយ Minecraft និង Forge។ "
				+ "បើបញ្ហានៅតែមាន សូមទាក់ទងអ្នកអភិវឌ្ឍន៍";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "សូមពិនិត្យ <b>កំហុសផ្សេងៗនៅក្នុង log</b> មុនសារ​នេះ ព្រោះបញ្ហាពិតអាចស្ថិតនៅកន្លែងផ្សេង។ "
				+ "កំហុសមុនៗអាចរារាំងការផ្ទុកថ្នាក់បានត្រឹមត្រូវ";
	}

	@Override
	public String limpiado() {
		return "បានសម្អាត";
	}

	@Override
	public String original() {
		return "ដើម";
	}

	@Override
	public String verEnConsola() {
		return "មើលនៅក្នុងកុងសូល";
	}

	@Override
	public String advertencia() {
		// TODO Auto-generated method stub
		return "ព្រមាន";
	}

	@Override
	public String fatal() {
		return "ធ្ងន់ធ្ងរ";
	}

	@Override
	public String noRegistroDeBattly() {
		return "BattlyLauncher មិនមានកំណត់ហេតុ ឬកុងសូលសម្រាប់ចម្លងទេ។ អ្នកអាចប្រើ ProxySysOutSysErr ដើម្បីស្ទាក់ចាប់ STDOUT និង STDERR ហើយបន្ទាប់មកចាប់ផ្តើមហ្គេមឡើងវិញ ប៉ុន្តែ ProxySysOutSysErr អាចប៉ះទង្គិចជាមួយ mods ដែលកែប្រែ STDOUT ឬ STDERR។";
	}

	@Override
	public String noRegistroDeNightWorld() {
		return "អ្នកត្រូវបើករបៀប debugging នៅក្នុងការកំណត់របស់ NightWorld ដើម្បីទទួលបានកំណត់ហេតុ launcher។ វាសំខាន់ណាស់ ជាពិសេសព្រោះវាមាន STDOUT និង STDERR។";
	}

	@Override
	public String noRegistroDeMCServidor() {
		return "អ្នកត្រូវរក្សាទុក ឬបិទភ្ជាប់មាតិកានៃ Terminal របស់ server របស់អ្នក ព្រោះវាមានព័ត៌មានដែលមិនមាននៅក្នុងកំណត់ហេតុផ្សេងទៀត រួមទាំង STDOUT, STDERR និងកំហុសផ្សេងៗ។ សូមបិទភ្ជាប់មាតិកាពី session ចុងក្រោយ។ សម្រាប់ពេលក្រោយ អ្នកអាចរក្សាទុកមាតិកា terminal ទៅក្នុងឯកសារ cd_launcherlog។ ដើម្បីជៀសវាងការបិទភ្ជាប់ដោយដៃ សូមបន្ថែម >> cd_launcherlog បន្ទាប់ពី command ដូចបង្ហាញក្នុងរូបភាព។ សូមចំណាំថា វានឹងមិនបង្ហាញនៅក្នុង terminal ទៀតទេ; បន្ទាប់ពីធ្វើដូច្នេះ វានឹងបង្ហាញតែនៅក្នុងឯកសារនោះប៉ុណ្ណោះ។";
	}

	// Métodos para Idioma relacionados con la verificación
	// LexForgeMLTransformerEnNeoForge

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("កំហុសធ្ងន់ធ្ងរ៖ បានរកឃើញ transformador de LexForge នៅក្នុងបរិស្ថាន NeoForge។ ");
		sb.append("</b>");

		sb.append("ថ្នាក់ដែលពាក់ព័ន្ធគឺ <b>").append(claseReceptora).append("</b>។ ");
		sb.append("interface ដែលរងផលប៉ះពាល់គឺ <b>").append(interfazObjetivo).append("</b> ");
		sb.append("ហើយខ្វះវិធីសាស្ត្រ <b>").append(firmaMetodoFaltante).append("</b>។ ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("ថ្នាក់នេះត្រូវបានរកឃើញនៅក្នុង៖ <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", និងផ្សេងទៀត...");
			sb.append("</b>។ ");
		} else {
			sb.append("មិនបានរកឃើញ JAR ណាដែលមានថ្នាក់នេះទេ; វាអាចត្រូវបាន shaded ឬបញ្ចូលជា jar-in-jar។ ");
		}

		sb.append(
				"បញ្ហានេះកើតឡើងនៅពេល transformador ឬសេវា ModLauncher ដែលបាន compile សម្រាប់ MinecraftForge/LexForge ");
		sb.append("ត្រូវបានផ្ទុកក្រោម NeoForge ជាមួយកំណែ ModLauncher API ដែលមិនឆបគ្នា។ ");
		sb.append("សូមអាប់ដេត ឬជំនួស component នេះសម្រាប់ NeoForge។");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "Transformador de LexForge ដែលប្រើក្នុង NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "កំណត់អត្តសញ្ញាណ transformador ដែលមិនឆបគ្នា៖ <b>" + claseReceptora + "</b>។ " + "API ដែលត្រូវការគឺ <b>"
				+ interfazObjetivo + "</b> ហើយខ្វះ <b>" + firmaMetodoFaltante + "</b>។ "
				+ "ពិនិត្យមើលថាតើ mod បានចុះបញ្ជីថ្នាក់នេះនៅក្នុង <b>META-INF/services</b> ឬអត់ ហើយលុប ឬបិទវានៅក្នុង NeoForge។";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("ទីតាំងរបស់ mod ដែលពាក់ព័ន្ធ៖ <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", និងផ្សេងទៀត...");
			sb.append("</b>។ ");
		} else {
			sb.append("មិនបានរកឃើញ JAR ដែលមានថ្នាក់នេះទេ។ សូមពិនិត្យ jar-in-jar និង shaded dependencies។ ");
		}
		sb.append("សូមដក JAR ទាំងនេះចេញជាបណ្ដោះអាសន្ន ឬប្រើកំណែដែលឆបគ្នាជាមួយ NeoForge ដើម្បីបញ្ជាក់ប្រភពបញ្ហា។");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "ជំនួស component នេះដោយកំណែជាក់លាក់សម្រាប់ NeoForge ឬ compile វាឡើងវិញឱ្យត្រូវនឹងកំណែ ModLauncher ដែល NeoForge កំពុងប្រើ។ ជៀសវាង binaries ចាស់របស់ LexForge/MinecraftForge។";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "សម្អាតថត mods ហើយលុប jar-in-jar ស្ទួនៗ។ សម្អាត cache របស់ launcher ប្រសិនបើចាំបាច់ ហើយចាប់ផ្តើមឡើងវិញ ដើម្បីពិនិត្យថា transformadores របស់ LexForge មិនត្រូវបានផ្ទុកទៀតទេ។";
	}
	// En tu clase de idioma:

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia មិនអាចចាប់ផ្តើមបានទេ៖ Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("មិនឆបគ្នាទេ។</b> ");
		sb.append("សូមដក Xenon ចេញ ហើយប្រើ Embeddium ឬ Sodium ជំនួស។ ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("រកឃើញនៅ៖ <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", និងផ្សេងទៀត...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia មិនឆបគ្នាជាមួយ Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "បានរកឃើញ " + label + " ដែលមិនឆបគ្នាជាមួយ WaterMedia។ សូមដកវាចេញពី profile។";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("ទីតាំង៖ <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", និងផ្សេងទៀត...");
			sb.append("</b>។ សូមលុប JAR នោះចេញ។");
			return sb.toString();
		}
		return "មិនបានរកឃើញ JAR ទេ។ សូមពិនិត្យថត mods ហើយលុប Xenon ចេញ។";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "ដំឡើង Embeddium ឬ Sodium ជំនួស ហើយចាប់ផ្តើមហ្គេមឡើងវិញ។";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "កំហុសពេលបង្ហាប់ (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>Deflater ត្រូវបានបិទកំឡុងពេលចម្លងធនធានរបស់ TACZ។</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("ពាក់ព័ន្ធនឹង៖ <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", និងផ្សេងទៀត");
			sb.append("</b>. ");
		}
		sb.append(
				"<br/><b>ដំណោះស្រាយ៖</b> នៅក្នុង <code>tacz/tacz-pre.toml</code> កំណត់ <code>DefaultPackDebug=true</code>។ ")
				.append("បើចាំបាច់ សូមបង្កើត map មួយជាមុនសិន ហើយបន្ទាប់មកបើកវា។");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "នៅក្នុង tacz/tacz-pre.toml កំណត់ DefaultPackDebug=true។ បើចាំបាច់ សូមបង្កើត map មួយជាមុនសិន ហើយបន្ទាប់មកបើកវា។";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "មុខងារ density ដែលមិនទាន់បានភ្ជាប់";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>ខ្វះមុខងារ density នៅក្នុងកំណត់ហេតុ។</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("ខ្វះ៖ ");
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
				"<br/><b>ដំណោះស្រាយ៖</b> ដំឡើង ឬបើក mod/datapack ដែលកំណត់មុខងារទាំងនេះ ហើយចាប់ផ្តើមឡើងវិញ។ មូលហេតុទូទៅមួយទៀតសម្រាប់បញ្ហានេះ គឺអ្នកមាន mod ដែលត្រូវការ ប៉ុន្តែវាមានបញ្ហា ឬប៉ះទង្គិចជាមួយ mod ផ្សេងទៀត។ ឧទាហរណ៍ Terralith មានបញ្ហាច្រើន ហើយអាចបង្កកំហុសនេះ និងកំហុសផ្សេងៗ រួមទាំងកំហុស JSON ផងដែរ។");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "ដំឡើង ឬបើក mod/datapack ដែលផ្ដល់មុខងារទាំងនេះ ហើយចាប់ផ្តើមហ្គេមឡើងវិញ។";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("មិនមានធាតុកំណត់ហេតុ៖ ").append(claveFaltante).append(". ");
		sb.append("ជាញឹកញាប់កើតឡើងជាមួយ alpha របស់ Steam & Railways សម្រាប់ Create 6។");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (alpha)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "ដក alpha របស់ Steam & Railways សម្រាប់ Create 6 ចេញ ឬជំនួសដោយកំណែដែលឆបគ្នា។";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("មានការប៉ះទង្គិចពេលផ្ទុក៖ Multiworld ជាមួយ Sodium/Embeddium/Rubidium បង្ក ")
				.append("IncompatibleClassChangeកំហុស (FabricLoader.getInstance). ")
				.append("សំណើ៖ ដក Multiworld ឬ mod បង្កើនល្បឿនចេញ ឬប្រើកំណែដែលឆបគ្នា។");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "ការប៉ះទង្គិច៖ Multiworld ជាមួយ mods បង្កើនល្បឿន";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "លុប Multiworld ឬ Sodium/Embeddium/Rubidium ចេញ ឬអាប់ដេតទៅកំណែដែលឆបគ្នា។";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Sodium បានរកឃើញ driver ក្រាហ្វិកដែលមិនឆបគ្នា។ "
				+ "សូមអាប់ដេត driver GPU របស់អ្នកទៅកម្រិតអប្បបរមាដែលត្រូវការ ឬធ្វើតាមការណែនាំរបស់ Sodium។" + "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "កំហុស OpenGL context";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OpenGL បានបរាជ័យ៖ មិនមាន context បច្ចុប្បន្ន ឬ function មិនមាននៅក្នុង context នេះទេ។ "
				+ "វាក៏អាចជាបញ្ហាជាមួយ video drivers ផងដែរ។" + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "អាប់ដេត ឬដំឡើងឡើងវិញនូវ driver GPU ហើយចាប់ផ្តើមឡើងវិញ; បិទ overlays ហើយសាកល្បងដោយគ្មាន mods បង្កើនល្បឿន។";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "តំណត្រូវបានចម្លងទៅ clipboard ហើយ។";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		return "ស្វែងរកនៅក្នុងឯកសារបង្ហាប់ (.zip/.jar/.war/.ear/.fpm/.rar de Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "កំហុស resolution របស់ texture៖ មិនអាចកែសម្រួល " + recurso + " - ទំហំ៖ " + tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "កំហុស Resolution របស់ Texture";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "កំហុសនេះកើតឡើងនៅពេលដែលរូបភាព texture មានទំហំធំពេក ឬមាន resource packs ច្រើនពេក។ "
				+ "សូមព្យាយាមប្រើ resource packs ដែលមាន resolution ទាបជាងនេះ ឬលុប resource packs មួយចំនួនចេញ។ "
				+ "សូមពិនិត្យថាអ្នកមិនបានបន្ថែម textures ផ្ទាល់ខ្លួនដែលមាន resolution ខ្ពស់ជាងកំណត់ទេ។";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "កំហុសសេវាកម្ម ModLauncher: ផ្លូវមានតួអក្សរមិនត្រឹមត្រូវ។ "
				+ "សេវាកម្ម ModLauncher មិនអាចដំណើរការផ្លូវដែលមានតួអក្សរ non-ASCII ឬតួអក្សរពិសេសបានទេ។ "
				+ "តួអក្សរដែលមានបញ្ហារួមមាន: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요 និងជាពិសេសតួអក្សរ '\"' នៅចុងឈ្មោះ។ "
				+ "សមាសធាតុសេវាកម្មធម្មតានៅក្នុង ModLauncher រួមមាន CrashDetector, "
				+ Config.obtenerInstancia().obtenerNombreCD()
				+ ", FeatureCreep, Vivicraft, Optifine, Sodium, clonos, Iris Shaders/Oculus, MixerLogger, CrashAssistant និង Sintrya Connector។ "
				+ "អ្នកអាចលុបសេវាកម្មទាំងអស់បាន ប៉ុន្តែបញ្ហាផ្សេងទៀតអាចកើតឡើងដោយសារឈ្មោះផ្លូវ។ "
				+ "ដំណោះស្រាយ៖ ប្តូរឈ្មោះ instance ដើម្បីប្រើតែតួអក្សរ ASCII (a-z, A-Z, 0-9) ប៉ុណ្ណោះ ដោយគ្មានចន្លោះ ឬតួអក្សរពិសេស។</b>";
	}// TODO incluye un Buscardor para mods con servicios

	@Override
	public String nombre_error_modlauncher_path() {
		return "កំហុសផ្លូវ ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "កំហុសនេះកើតឡើងនៅពេលដែលផ្លូវ instance មានតួអក្សរ non-ASCII ឬតួអក្សរពិសេស។ "
				+ "សេវាកម្ម ModLauncher មិនអាចដំណើរការផ្លូវទាំងនេះបានទេ។ "
				+ "ដំណោះស្រាយ៖ ប្តូរឈ្មោះ instance ដើម្បីប្រើតែតួអក្សរ ASCII (a-z, A-Z, 0-9) និងជៀសវាងចន្លោះ និងតួអក្សរពិសេស។ "
				+ "យកចិត្តទុកដាក់ជាពិសេសលើតួអក្សរ '\"' ដែលមានបញ្ហាខ្លាំង ជាពិសេសនៅចុងឈ្មោះ។";
	}

	@Override
	public String tituloEditorCodice() {
		return "កម្មវិធីកែសម្រួលកូដ";
	}

	@Override
	public String nuevo() {
		return "ថ្មី";
	}

	@Override
	public String actualizarSeleccionado() {
		return "ធ្វើបច្ចុប្បន្នភាពដែលបានជ្រើសរើស";
	}

	@Override
	public String eliminarSeleccionado() {
		return "លុបដែលបានជ្រើសរើស";
	}

	@Override
	public String exportarJSON() {
		return "នាំចេញ JSON...";
	}

	@Override
	public String guardarTodo() {
		return "រក្សាទុកទាំងអស់";
	}

	@Override
	public String general() {
		return "ទូទៅ";
	}

	@Override
	public String id() {
		return "អត្តសញ្ញាណ (ID)";
	}

	@Override
	public String paraBuscar() {
		return "អត្ថបទសម្រាប់ស្វែងរក";
	}

	@Override
	public String filtro() {
		return "តម្រង (id)";
	}

	@Override
	public String criticalidad() {
		return "កម្រិតសារៈសំខាន់ (ព្រមាន/កំហុស/ធ្ងន់ធ្ងរ)";
	}

	@Override
	public String prioridad() {
		return "អាទិភាព";
	}

	@Override
	public String lista() {
		return "បញ្ជីត្រួតពិនិត្យ";
	}

	@Override
	public String colIdioma() {
		return "ភាសា";
	}

	@Override
	public String colNombre() {
		return "ឈ្មោះ";
	}

	@Override
	public String colResultado() {
		return "លទ្ធផល";
	}

	@Override
	public String vistaJson() {
		return "មើលជាមុន JSON";
	}

	@Override
	public String idiomas() {
		return "ភាសា (ទាំងអស់ត្រូវការ)";
	}

	@Override
	public String elegirFiltro() {
		return "ជ្រើសរើស...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "សូមជ្រើសរើសតម្រងមួយ";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "តម្រងដែលមាន";
	}

	@Override
	public String faltanCampos() {
		return "សូមបំពេញវាលទូទៅដែលត្រូវការទាំងអស់។";
	}

	@Override
	public String critInvalida() {
		return "កម្រិតសារៈសំខាន់មិនត្រឹមត្រូវ។ ប្រើ ADVERTENCIA, ERROR ឬ FATAL។";
	}

	@Override
	public String filtroNoExiste() {
		return "តម្រងដែលបានបញ្ជាក់មិនមានទេ។";
	}

	@Override
	public String faltanIdiomas() {
		return "សូមបំពេញឈ្មោះ និងលទ្ធផលសម្រាប់ភាសាទាំងអស់៖";
	}

	@Override
	public String verificacionInvalida() {
		return "ការផ្ទៀងផ្ទាត់មិនត្រឹមត្រូវ។ សូមពិនិត្យវាលទាំងអស់។";
	}

	@Override
	public String guardadoOk() {
		return "រក្សាទុកបានជោគជ័យ។";
	}

	@Override
	public String editorCodiceBoton() {
		return "បន្ថែមហេតុផល";
	}

	@Override
	public String descripcionEditorCodice() {
		return "អ្នកអាចកត់ត្រាហេតុផលនៅទីនេះ។ អ្នកត្រូវការមាន ID មួយ ដែលជាអក្សរដែលគ្មានតួអក្សរពិសេស ឬអក្សរដែលមានសញ្ញាសំឡេង ឬចន្លោះ។ "
				+ "សម្រាប់តម្រង អ្នកអាចប្រើ \"linea contiene\" ដើម្បីស្វែងរកអត្ថបទនៅក្នុងបន្ទាត់មួយ, \"todo contiene\" ប្រសិនបើកំណត់ហេតុមានអត្ថបទមួយ, "
				+ "\"regex linea\" ប្រសិនបើបន្ទាត់មាន regex និង \"regex todos\" (ផ្តល់អនុសាសន៍ឲ្យប្រើប្រភេទ linea)។ "
				+ "អ្នកត្រូវកំណត់កម្រិតសារៈសំខាន់ជា FATAL, ERROR ឬ ADVERTENCIA សម្រាប់ពណ៌។ "
				+ "សម្រាប់ភាសាទាំងអស់ អ្នកត្រូវសរសេរឈ្មោះ និងការឆ្លើយតបសម្រាប់បង្ហាញលើអេក្រង់។ "
				+ "អ្នកអាចបន្ថែមការផ្ទៀងផ្ទាត់ថ្មី ឬលុបការផ្ទៀងផ្ទាត់ចាស់។ " + "រក្សាទុកនៅពេលបំពេញរួច។";
	}

	@Override
	public String descartarCambios() {
		return "តើអ្នកចង់បោះបង់ការផ្លាស់ប្តូរដែលមិនបានរក្សាទុកក្នុងការផ្ទៀងផ្ទាត់បច្ចុប្បន្នទេ?";
	}

	@Override
	public String confirmacion() {
		return "ការបញ្ជាក់";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "តើអ្នកចង់រក្សាទុកការផ្លាស់ប្តូរមុនពេលចាកចេញទេ?";
	}

	@Override
	public String salirSinGuardar() {
		return "ចាកចេញដោយមិនរក្សាទុក";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("កំហុសធ្ងន់ធ្ងរ: មិនអាចផ្ទុកសេវាកម្ម modlauncher (IDependencyLocator) បាន។<br>");
		sb.append("🔹 <b>ថ្នាក់មានបញ្ហា:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>ម៉ូដដែលរងផលប៉ះពាល់:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append("🔸 <b>មិនអាចកំណត់ម៉ូដបាន។</b> សូមពិនិត្យម៉ូដថ្មី ឬម៉ូដដែលកំពុងអភិវឌ្ឍ។<br>");
		}

		sb.append(
				"🔸 <b>មូលហេតុ:</b> ឯកសារ <code>META-INF/services/...</code> របស់ម៉ូដខូច ឬមិនឆបគ្នាជាមួយ Forge/NeoForge ឬជាវើសិនខុស។<br>");
		sb.append(
				"🔸 <b>ផលវិបាក:</b> Forge/NeoForge មិនអាចចុះឈ្មោះ dependency បាន ដែលបណ្ដាលឲ្យហ្គេមមិនអាចចាប់ផ្តើមបាន។<br>");
		sb.append("🔸 <b>ដំណោះស្រាយ:</b> ធ្វើបច្ចុប្បន្នភាព ដំឡើងឡើងវិញ ឬលុបម៉ូដដែលមានបញ្ហា។ ");
		sb.append("បើអ្នកប្រើម៉ូដអភិវឌ្ឍ សូមប្រាកដថាវាត្រូវបានកំពង់ compile សម្រាប់វើសិនត្រឹមត្រូវ។");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "កំហុសការកំណត់សេវាកម្ម (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. កំណត់ម៉ូដដែលបង្កបញ្ហា ដោយពិនិត្យម៉ូដដែលបានដំឡើងថ្មី។";
		}
		return "1. ម៉ូដដែលមានបញ្ហាគឺ: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. ធ្វើបច្ចុប្បន្នភាព ដំឡើងឡើងវិញ ឬលុបម៉ូដ ហើយប្រាកដថាវាឆបគ្នាជាមួយ Forge/NeoForge។";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>កំហុសធ្ងន់ធ្ងរ: វាលមិនមាន។</b><br>"
				+ "ម៉ូដព្យាយាមចូលប្រើវាល <b style='color:#" + colorCodigo + "'>" + campo + "</b> ដែលមិនមាន។<br>"
				+ "<span style='color:#" + colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta)
				+ "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "វាលមិនមាន (NoSuchField)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. កំហុសនេះកើតឡើងពេលម៉ូដមិនឆបគ្នាជាមួយវើសិនបច្ចុប្បន្ន។";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. ធ្វើបច្ចុប្បន្នភាពម៉ូដទាំងអស់ ឬទាក់ទងអ្នកអភិវឌ្ឍម៉ូដ។";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>កំហុសធ្ងន់ធ្ងរ: មេធតមិនមាន។</b><br>"
				+ "ម៉ូដព្យាយាមហៅមេធត <b style='color:#" + colorCodigo + "'>" + metodo + "</b> ដែលមិនមាន។<br>"
				+ "<span style='color:#" + colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta)
				+ "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "មេធតមិនមាន (NoSuchMethod)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. កំហុសនេះកើតឡើងពេលម៉ូដមិនឆបគ្នា។";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. ធ្វើបច្ចុប្បន្នភាពម៉ូដទាំងអស់ ឬរាយការណ៍ទៅអ្នកអភិវឌ្ឍ។";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();

		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>ត្រូវការជំនួយ?</strong><br>"
				+ "  ប្រសិនបើអ្នកមិនដឹងរបៀបដោះស្រាយ អ្នកអាចទទួលជំនួយតាមបណ្ដាញសង្គម។ " + "  ប្រើប៊ូតុង <img src='"
				+ iconoCompartir + "' alt='ចែករំលែក' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>ចែករំលែក</strong> ដើម្បីផ្ញើកំណត់ហេតុ និងលទ្ធផល។ "
				+ "  ប្រសិនបើអ្នកជាអ្នកបង្កើត modpack អ្នកអាចកែសម្រួល <code>crash_detector/plantilla.htm</code> "
				+ "  ដើម្បីកំណត់តំណផ្ទាល់ខ្លួន។" + "</div>";
	}

	@Override
	public String restablecerPlantilla() {
		// TODO Auto-generated method stub
		return "ស្តារពុម្ពឡើងវិញ";
	}

	@Override
	public String restablecer() {
		// TODO Auto-generated method stub
		return "ស្តារឡើងវិញ";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		// TODO Auto-generated method stub
		return "ស្តារ " + nombreImagen + " ទៅតម្លៃលំនាំដើមឡើងវិញ?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		// TODO Auto-generated method stub
		return "ស្តារពុម្ពទៅតម្លៃលំនាំដើមឡើងវិញ?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>បាត់ថ្នាក់ AzureLib។ បើអ្នកមាន AzureLib រួចហើយ សូមដំឡើងកំណែមុនថ្ងៃទី 8 ខែតុលា ឆ្នាំ 2025។ នេះធ្លាប់កើតឡើងញឹកញាប់។ បើអ្នកមិនទាន់មាន AzureLib ទេ សូមដំឡើងកំណែបច្ចុប្បន្ន។</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ម៉ូដ <code>healight</code> កំពុងបង្កឱ្យមានកំហុសធ្ងន់ធ្ងរ៖ <code>java.lang.NoSuchFieldកំហុស: INT</code>. "
				+ "កំហុសនេះកើតឡើងព្រោះម៉ូដព្យាយាមចូលប្រើ field មួយដែលមិនមានទៀតហើយនៅក្នុងកំណែ MCForge 47.10 Minecraft 1.20+ ។ "
				+ "ហ្គេមមិនអាចចាប់ផ្តើមបានដោយសារបញ្ហានេះ។</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• លុប ឬធ្វើបច្ចុប្បន្នភាពម៉ូដ <code>healight</code>។ "
				+ "កំណែបច្ចុប្បន្នមិនឆបគ្នាជាមួយ MinecraftForge 47.10 សម្រាប់ 1.20.1 ទេ។ "
				+ "ស្វែងរកកំណែថ្មីជាងនេះរបស់ម៉ូដ ឬពិចារណាប្រើជម្រើសផ្សេង។";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "កំហុសធ្ងន់ធ្ងរ៖ healight - មិនរកឃើញ field 'INT'";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("ថ្នាក់ <code>").append(clase)
				.append("</code> មិនបានអនុវត្ត method ដែលត្រូវការ៖<br>").append("<code>").append(metodo)
				.append("</code><br>").append("ពី interface <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br><br>ម៉ូដ ឬឯកសារដែលគួរសង្ស័យ៖ <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• កំហុសនេះកើតឡើងនៅពេលម៉ូដមួយអនុវត្ត interface មួយ ប៉ុន្តែមិនបានដាក់ method ដែលចាំបាច់។ "
				+ "ធ្វើបច្ចុប្បន្នភាព <b>ម៉ូដទាំងពីរ</b> ដែលពាក់ព័ន្ធ (ម៉ូដដែលកំណត់ interface និងម៉ូដដែលអនុវត្តវា)។ "
				+ "បើអ្នកមិនដឹងថាជាម៉ូដណាខ្លះទេ សូមរកមើលឈ្មោះដែលបង្ហាញក្នុងសារកំហុស។";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "មេធតអ៊ីនធើហ្វេសមិនបានអនុវត្ត (AbstractMethodកំហុស)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ម៉ូដមួយកំពុងព្យាយាមផ្ទុកថ្នាក់ពី <b>ផ្នែក client</b> "
				+ "(<code>AnimationMetadataSection</code>) នៅក្នុង <b>server dedicated</b> ដែលមិនអាចធ្វើទៅបាន។ "
				+ "កំហុសនេះជាញឹកញាប់កើតឡើងនៅពេលម៉ូដមិនបំបែកកូដរវាង client និង server ឲ្យត្រឹមត្រូវ។ "
				+ "ការមាន <code>ModernFix</code> អាចបង្ហាញបញ្ហានេះ ប៉ុន្តែមិនមែនជាមូលហេតុផ្ទាល់ទេ។</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>ជម្រើសលឿន:</b> លុប <code>ModernFix</code> ជាបណ្តោះអាសន្ន ដើម្បីពិនិត្យថា server អាចចាប់ផ្តើមបានឬអត់។ "
				+ "បើដំណើរការ បញ្ហាស្ថិតនៅក្នុងម៉ូដផ្សេង។<br>"
				+ "• <b>ដំណោះស្រាយអចិន្រ្តៃយ៍:</b> កំណត់ម៉ូដដែលបង្កបញ្ហា (ម៉ូដដែលមាន animation, textures ឬ graphics libraries) ហើយធ្វើបច្ចុប្បន្នភាព ឬលុបវា។<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "ថ្នាក់ client ត្រូវបានផ្ទុកនៅក្នុង server (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ឯកសារកំណត់រចនាសម្ព័ន្ធរបស់ម៉ូដ <code>Sinytra Connector</code> ខូច។ "
				+ "វាធម្មតាកើតឡើងនៅពេលឯកសារមានតួអក្សរ null (<code>\\u0000</code>) "
				+ "ដោយសារបិទហ្គេមភ្លាមៗ កំហុសសរសេរ ឬការប៉ះទង្គិចម៉ូដ។</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• ចូលទៅកាន់ថត <code>config/</code> នៃ Minecraft instance របស់អ្នក។<br>"
				+ "• ស្វែងរក និងលុប config របស់ connector mods។<br>"
				+ "• ចាប់ផ្តើមហ្គេមឡើងវិញ៖ Sinytra Connector នឹងបង្កើតឯកសារថ្មី។";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "ការកំណត់ Sinytra Connector ខូច";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "ឯកសារ <code>" + nombreJar
				+ "</code> ខូច ឬមិនពេញលេញ។<br>" + "ប្រព័ន្ធមិនអាចអានបាន ព្រោះបាត់ header ចុងក្រោយនៃ ZIP file។<br>"
				+ "កំហុសនេះជាញឹកញាប់កើតឡើងក្រោយការទាញយកផ្អាក ឬកំហុស launcher។</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "ឯកសារ JAR ខូច (មានឈ្មោះជាក់លាក់)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>លុបឯកសារខូច</b> ហើយទាញយកឡើងវិញពីប្រភពផ្លូវការ (CurseForge, MinecraftStorage...)។<br>"
				+ "• បើអ្នកប្រើ launcher ដូចជា CurseForge ឬ Technic អាចពិចារណាប្រើ <b>ATLauncher</b> ឬ <b>Prism Launcher</b>។<br>"
				+ "• ប្រាកដថាការតភ្ជាប់អ៊ីនធឺណិតមានស្ថិរភាព។";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "មិនអាចផ្ទុកពិភពលោកបាន ព្រោះឯកសារ NBT ខូច "
				+ "(ឧ. <code>level.dat</code>, <code>playerdata/*.dat</code>)។<br>"
				+ "កំហុស៖ <code>UTFDataFormatException: malformed input around byte " + byteCorrupto
				+ "</code>.<br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ សូមបម្រុងទុកពិភពលោកមុនពេលជួសជុល។</b><br><br>"
				+ "អ្នកអាចប្រើ <b>NBT editor</b> ដូចជា <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>។<br>"
				+ "បើខូចខ្លាំង ប្រើ hex editor (HxD)។<br>" + "ជាជម្រើសចុងក្រោយ ស្តារពី backup ឬប្រើ mod ជួសជុល។</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>បម្រុងទុកពិភពលោក</b> មុនជួសជុល។<br>" + "• ប្រើ NBT editor ដើម្បីកែឯកសារ។<br>"
				+ "• ប្រើ hex editor ប្រសិនបើចាំបាច់។<br>" + "• ប្រសិនបើមិនមានបទពិសោធន៍ ស្តារពី backup។";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "ពិភពលោកខូច៖ កំហុសផ្ទុក NBT";
	}

	@Override
	public String problema_con_openAL() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមានបញ្ហាជាមួយ OpenAL។ ពេលខ្លះ Nouveau drivers ឬកំណែ OpenAL មិនឆបគ្នា។ "
				+ "វាជារឿងធម្មតានៅលើ Linux (ជាពិសេស Red Hat)។ "
				+ "មើលការណែនាំ៖ <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>ដោះស្រាយបញ្ហាសម្លេង</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Server មិនអាចចាប់ផ្តើមបាន ព្រោះឯកសារត្រូវបានចាក់សោដោយ process ផ្សេង។<br>" + "មូលហេតុអាចជា៖<br>"
				+ "• មាន server កំពុងដំណើរការ។<br>" + "• Antivirus ឬ file explorer កំពុងប្រើឯកសារ។<br>"
				+ "• Process មុនមិនបានបិទត្រឹមត្រូវ។</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>បិទ instance ទាំងអស់របស់ server</b> (រួមទាំង process នៅខាងក្រោយដូចជា javaw.exe)។<br>"
				+ "• បើអ្នកប្រើ panel hosting (ដូចជា Multicraft) សូម restart server ពេញលេញពី panel។<br>"
				+ "• <b>បិទ antivirus ជាបណ្តោះអាសន្ន</b> ប្រសិនបើអ្នកសង្ស័យថាវាកំពុងបិទឯកសារ។<br>"
				+ "• នៅលើប្រព័ន្ធ local សូមបិទ window Explorer ទាំងអស់ដែលបង្ហាញថត world។<br>"
				+ "• បើបញ្ហានៅតែបន្ត សូមលុបឯកសារ <code>session.lock</code> ដោយដៃ (តែប៉ុណ្ណោះបើអ្នកប្រាកដថាមិនមាន server ផ្សេងកំពុងដំណើរការ)។";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "ឯកសារ world ត្រូវបានចាក់សោដោយ process ផ្សេង";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "ម៉ូដព្យាយាមបន្ត class <code>"
				+ clasePadreFinal + "</code>, " + "ប៉ុន្តែ class នេះឥឡូវជា <b>final</b> ហើយមិនអាចស្នងបានទៀតទេ។<br>"
				+ "class ដែលមានបញ្ហាគឺ: <code>" + claseHija + "</code>.<br><br>"
				+ "វាជាញឹកញាប់កើតឡើងនៅពេលម៉ូដត្រូវបាន compile សម្រាប់កំណែចាស់នៃ Minecraft ឬ mod មូលដ្ឋាន។</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>ធ្វើបច្ចុប្បន្នភាពម៉ូដទាំងអស់</b> ដែលពាក់ព័ន្ធ។<br>"
				+ "• បើបញ្ហានៅតែបន្ត សូមស្វែងរកកំណែដែលឆបគ្នា។<br>"
				+ "• ក្នុងករណីខ្លះ លុបម៉ូដជាបណ្តោះអាសន្នអាចជួយកំណត់មូលហេតុ។";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "ព្យាយាមស្នងពី class final";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "អ្នកកំពុងប្រើ <b>Rubidium</b> ជាមួយ <b>Iris/Oculus</b>។<br>"
				+ "Rubidium គឺជាកំណែចាស់ និងមានបញ្ហាជាមួយ Minecraft 1.19.2+។<br><br>"
				+ "បញ្ហានេះក៏អាចកើតឡើងពីការប៉ះទង្គិចរវាង performance mods ផ្សេងៗ។</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>លុប Rubidium</b>។<br>" + "• <b>ដំឡើង Embeddium</b> ដែលឆបគ្នា។<br>"
				+ "• កុំដំឡើង Sodium forks ច្រើនក្នុងពេលតែមួយ។<br>" + "• ពិនិត្យភាពឆបគ្នានៃ Oculus/Iris។";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium មិនទាន់សម័យជាមួយ Iris/Oculus";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ម៉ូដ <code>Simple Voice Chat</code> មិនអាចចាប់ផ្តើមបាន ព្រោះ port UDP ត្រូវបានប្រើរួច។<br>"
				+ "វាមិនប៉ះពាល់ដល់ការចាប់ផ្តើមហ្គេម ប៉ុន្តែ voice chat នឹងមិនដំណើរការ។</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• បិទកម្មវិធីផ្សេងដែលប្រើ port 24454។<br>"
				+ "• ផ្លាស់ប្តូរ port នៅក្នុង <code>config/voicechat/</code>។<br>" + "• ពិនិត្យ IP configuration។";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Voice Chat: port UDP ត្រូវបានប្រើរួច";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "BlockItem <code>" + nombreBlockItem
				+ "</code> មាន block ទទេ។<br>" + "វាជាញឹកញាប់កើតឡើងនៅក្នុង addons របស់ Create។</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• ធ្វើបច្ចុប្បន្នភាព Create និង addons ទាំងអស់។<br>" + "• លុប addons មួយៗដើម្បីរកមូលហេតុ។<br>"
				+ "• ពិនិត្យភាពឆបគ្នា។";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "BlockItem ទទេក្នុង Create addon";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>")
				.append("មានម៉ូដមិនឆបគ្នាជាមួយ platform បច្ចុប្បន្ន៖<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>វាជាញឹកញាប់កើតឡើងពេលលាយ Forge និង Fabric ឬកំណែខុស។</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• ប្រើតែ Forge ឬ Fabric មួយប៉ុណ្ណោះ។<br>" + "• លុបម៉ូដមិនស្គាល់។<br>" + "• ពិនិត្យការកំណត់ launcher។";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "ម៉ូដមិនឆបគ្នា";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "មិនអាចបង្កើត model <code>" + modid + ":"
				+ nombreModelo + "</code> បាន។</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• ធ្វើបច្ចុប្បន្នភាពម៉ូដ។<br>" + "• ដំឡើងឡើងវិញ។<br>" + "• រាយការណ៍ទៅអ្នកអភិវឌ្ឍ។";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "បង្កើត model មិនបាន";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "មានការប៉ះទង្គិចរវាង <code>Moonlight</code> និង <code>Iceberg</code>។<br>"
				+ "វាបណ្ដាលឲ្យកំហុស OpenGL។</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• ធ្វើបច្ចុប្បន្នភាពម៉ូដទាំងពីរ។<br>" + "• លុប Iceberg ជាបណ្តោះអាសន្ន។<br>" + "• ពិនិត្យការចម្លងម៉ូដ។";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "ការប៉ះទង្គិចធ្ងន់ធ្ងរ៖ Moonlight ទល់នឹង Iceberg (OpenGL គ្មាន context)";
	}

	@Override
	public String instantanea() {
		// TODO Auto-generated method stub
		return "រូបថតចម្លង";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		// TODO Auto-generated method stub
		return "ចាប់តាំងពីរូបថតចម្លងចុងក្រោយ";
	}

	@Override
	public String seleccionarUnArchivo() {
		// TODO Auto-generated method stub
		return "ជ្រើសរើសឯកសារមួយ";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		// TODO Auto-generated method stub
		return "បានបង្កើតរូបថតចម្លងដោយជោគជ័យ";
	}

	@Override
	public String errorCreandoInstantanea() {
		// TODO Auto-generated method stub
		return "កំហុសក្នុងការបង្កើតរូបថតចម្លង";
	}

	@Override
	public String consejo() {
		// TODO Auto-generated method stub
		return "ដំបូន្មាន";
	}

	@Override
	public String resultadoMuestra() {
		// TODO Auto-generated method stub
		return "លទ្ធផលគំរូ";
	}

	@Override
	public String historaDeModsDesc() {
		// TODO Auto-generated method stub
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>ដំបូន្មាន៖</b> ជ្រើសរើសឯកសារប្រវត្តិពីរដើម្បីប្រៀបធៀបបញ្ជី mods។ "
				+ "  លទ្ធផលបង្ហាញ <span style='color:%s;'>ដែលបានបន្ថែម (+)</span> និង "
				+ "  <span style='color:%s;'>ដែលបានលុប (&#8722;)</span> ដោយផ្អែកលើឈ្មោះដែលបានធ្វើឱ្យស្តង់ដារ។ "
				+ "  ប្រើប៊ូតុង 'រូបថតចម្លង' ដើម្បីបង្កើតច្បាប់ចម្លងនៃឯកសារដែលមានស្រាប់ជាមួយនឹងកន្ទុយឯកសារ .instantanea។"
				+ "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		// TODO Auto-generated method stub
		return "ទទួលបានតំណទៅកំណត់ហេតុជា Markdown ដោយគ្មានរបាយការណ៍";
	}

	@Override
	public String titulo_configuracion() {
		return "ការកំណត់";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "កំហុសមិនបានរំពឹងទុកពេលចែករំលែក។";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "កំហុសមិនបានរំពឹងទុកពេលបង្កើតតំណ។";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "កំហុសមិនបានរំពឹងទុកពេលដំណើរការប៊ូតុង។";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "មិនមានឯកសារដែលភ្ជាប់សម្រាប់បើកទេ។";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "ឯកសារមិនមានទេ៖\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "មិនអាចបើកក្នុងកម្មវិធីកែសម្រួលបានទេ។\nផ្លូវនឹងត្រូវបានចម្លងទៅ clipboard។";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "មិនអាចបើកឯកសារបានទេ; ផ្លូវត្រូវបានចម្លងទៅ clipboard។";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "Desktop មិនត្រូវបានគាំទ្រ; ផ្លូវត្រូវបានចម្លងទៅ clipboard។";
	}

	@Override
	public String limite_de_solicitudes() {
		return "អ្នកកំពុងជួបការកំណត់សំណើ។ សូមសាកល្បងប្រើគេហទំព័រកំណត់ហេតុផ្សេង ឬ API កំណត់ហេតុផ្សេង។";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		// TODO Auto-generated method stub
		return "ចែករំលែកតំណ";
	}

	@Override
	public String infoDeTrazos() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ការជួសជុលផ្នែកខាងលើនៃ traces គឺជាអាទិភាពដំបូង។ " + "ទ្រង់ទ្រាយគឺ កម្រិត, បន្ទាត់។ "
				+ "កំណត់ហេតុទាំងអស់មានប្រព័ន្ធលេខរៀង។ " + Verificaciones.nl_html
				+ "ជាទូទៅ អ្នកត្រូវស្វែងរកនៅកម្រិតទាបជាងក្នុងកំណត់ហេតុទាំងអស់; traces ដែលមានកម្រិតខ្ពស់ជាទូទៅជាលទ្ធផលវិជ្ជមានក្លែងក្លាយ។ "
				+ "វាសំខាន់ណាស់ក្នុងការប្រើសមត្ថភាពរបស់អ្នកក្នុងការមើល console ព្រោះការវិភាគ traces មិនល្អឥតខ្ចោះទេ នៅពេលមាន traces ច្រើន។"
				+ "</b>";
	}

	// --- Buscador de Canario de Orden (Warrant Canary) ---

	@Override
	public String buscador_canario_de_orden_label() {
		return "កម្មវិធីស្វែងរក canario de orden";
	}

	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "មុខងារនេះនឹងមានក្នុងពេលឆាប់ៗនេះ។";
	}

	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "មកដល់ឆាប់ៗនេះ";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		return "ម៉ូដមិនឆបគ្នាជាមួយ Crash Assistant (មិនត្រឹមត្រូវ)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		return "ម៉ូដមិនឆបគ្នាជាមួយកញ្ចប់ម៉ូដ (Modpack) ដោយប្រើ CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant មានបញ្ជីម៉ូដដែលវានិយាយថាមិនឆបគ្នា ប៉ុន្តែមិនមានភស្តុតាងថាវាមិនឆបគ្នាទេ ហើយកំហុសមានតែភាសាអង់គ្លេស។ "
				+ "បើអ្នកចង់លេងជាមួយម៉ូដទាំងនេះ អ្នកអាចកែឯកសារ <code>config/crash_assistant/config.toml</code> "
				+ "ហើយប្តូរ <code>enabled = true</code> ទៅជា <code>enabled = false</code> នៅក្នុងផ្នែក [compatibility]។</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant អាចសម្គាល់ម៉ូដថាមិនឆបគ្នា ប៉ុន្តែម្តងម្កាលវាមិនត្រឹមត្រូវ ហើយសារកំហុសមានតែភាសាអង់គ្លេស។ "
				+ "បើអ្នកចង់លេងជាមួយម៉ូដទាំងនេះ អ្នកអាចកែឯកសារ <code>config/crash_assistant/problematic_mods_config.json</code> "
				+ "ហើយប្តូរ <code>should_crash_on_startup</code> ពី <code>true</code> ទៅជា <code>false</code>។</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "កំហុស: ម៉ូដ '" + modId + "' ត្រូវការម៉ូដ '"
				+ dependencia + "'។ បច្ចុប្បន្ន " + actual + "។" + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "កំហុស: ម៉ូដ '" + modId + "' ត្រូវការកំណែ '"
				+ requerido + "' ឬខ្ពស់ជាងនេះនៃ '" + dependencia + "' ប៉ុន្តែមិនបានដំឡើងម៉ូដ។" + "</span>";
	}

	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "កំហុស: ម៉ូដ '" + modId
				+ "' មិនឆបគ្នាជាមួយកំណែបច្ចុប្បន្ននៃ '" + dependencia + "'។ "
				+ "អ្នកត្រូវលុបម៉ូដ 'Iris/Oculus & GeckoLib Compat' ព្រោះវាមិនឆបគ្នាជាមួយ Superb Warfare។ "
				+ "កំណែបច្ចុប្បន្ន: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "កំហុស: មិនអាចដំណើរការភារកិច្ចសម្រាប់ថ្នាក់ '" + clase + "' បាន។ "
				+ "វាជាញឹកញាប់កើតឡើងដោយសារម៉ូដមិនឆបគ្នា។";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "បរាជ័យក្នុងការដំណើរការភារកិច្ច";
	}

	public String recomendacion_fallos_ejecucion() {
		return "ប្រភេទកំហុសនេះជាញឹកញាប់កើតឡើងដោយសារម៉ូដមិនឆបគ្នា។";
	}

	public String info_clase_problematica() {
		return "ថ្នាក់ដែលមានបញ្ហា៖";
	}

	public String no_se_encontraron_clases_problema() {
		return "មិនមានថ្នាក់ជាក់លាក់ដែលមានបញ្ហាត្រូវបានរកឃើញទេ។";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "រកឃើញការប៉ះទង្គិចរវាង OptiFine និង Entity Model Features (EMF)។ " + "ម៉ូដទាំងនេះមិនឆបគ្នាទេ។</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "ការប៉ះទង្គិច OptiFine និង EMF";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "លុប OptiFine ឬ Entity Model Features ព្រោះវាមិនឆបគ្នា។";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "រកឃើញការប៉ះទង្គិចរវាង OptiFine និង Fusion។ "
				+ "ម៉ូដទាំងនេះមិនឆបគ្នាទេ។</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "ការប៉ះទង្គិច OptiFine និង Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "លុប OptiFine ឬ Fusion ព្រោះវាមិនឆបគ្នា។";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel ត្រូវការ Sodium 0.6.0-beta.2 ឬខ្ពស់ជាងនេះ។ " + "សូមប្រើ Embeddium ជាជម្រើស។</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "ការប៉ះទង្គិច Flywheel និងកំណែ Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "ធ្វើបច្ចុប្បន្នភាព Sodium ឬប្រើ Embeddium។";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "រកឃើញការប៉ះទង្គិចរវាង OptiFine និង Epic Fight។</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "ការប៉ះទង្គិច OptiFine និង Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "លុប OptiFine ឬ Epic Fight។";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "រកឃើញការប៉ះទង្គិចរវាង OptiFine និង Rubidium។</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "ការប៉ះទង្គិច OptiFine និង Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "លុប OptiFine ឬ Rubidium។";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam មិនអាចដំណើរការនៅលើ server បានទេ (client-only mod)។ " + "សូមលុបវាចេញពី server។</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam នៅលើ server dedicated";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "លុប FreeCam ចេញពី server dedicated ព្រោះវាគួរតែត្រូវបានដំឡើងតែនៅលើ client ប៉ុណ្ណោះ។";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) កំពុងព្យាយាមផ្ទុកនៅលើ server dedicated ប៉ុន្តែវាឆបគ្នាតែជាមួយ client ប៉ុណ្ណោះ។ "
				+ "លុប ETF ចេញពី server ឬប្រាកដថាវាត្រូវបានដំឡើងតែនៅលើ client ប៉ុណ្ណោះ។" + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features នៅលើ server dedicated";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "លុប Entity Texture Features ចេញពី server dedicated ព្រោះវាគួរតែត្រូវបានដំឡើងតែនៅលើ client ប៉ុណ្ណោះ។";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "អ្នកត្រូវទទួលយក EULA របស់ Minecraft ដើម្បីដំណើរការ server។ "
				+ "កែសម្រួលឯកសារ eula.txt ហើយប្តូរ 'eula=false' ទៅជា 'eula=true'។" + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "មិនបានទទួលយក EULA របស់ Minecraft";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "កែសម្រួលឯកសារ eula.txt នៅក្នុងថត server ហើយប្តូរ 'eula=false' ទៅជា 'eula=true'។";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine កំពុងព្យាយាមផ្ទុកនៅលើ server dedicated ប៉ុន្តែវាឆបគ្នាតែជាមួយ client ប៉ុណ្ណោះ។ "
				+ "លុប OptiFine ចេញពី server ឬប្រាកដថាវាត្រូវបានដំឡើងតែនៅលើ client ប៉ុណ្ណោះ។" + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine នៅលើ server dedicated";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "លុប OptiFine ចេញពី server dedicated ព្រោះវាគួរតែត្រូវបានដំឡើងតែនៅលើ client ប៉ុណ្ណោះ។ បញ្ហានេះក៏ជាញឹកញាប់បណ្តាលមកពីការដំឡើងកំណែ OptiFine ខុសសម្រាប់កំណែ Minecraft របស់អ្នក ឬពីការប៉ះទង្គិចជាមួយម៉ូដផ្សេង និង OptiFine។";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks ត្រូវបានសម្គាល់មិនត្រឹមត្រូវសម្រាប់ 1.20.1 ប៉ុន្តែប្រើ method របស់ 1.21.1។ "
				+ "ម៉ូដកំពុងព្យាយាមប្រើ ResourceLocation.fromNamespaceAndPath ដែលមិនមាននៅក្នុង 1.20.1។" + "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "កំហុសកំណែនៅក្នុង Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "ប្រាកដថាអ្នកកំពុងប្រើកំណែត្រឹមត្រូវរបស់ Iron's Spellbooks ដែលឆបគ្នាជាមួយកំណែ Minecraft របស់អ្នក។";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "រកឃើញការប៉ះទង្គិចធ្ងន់ធ្ងររវាង OptiFine និង Embeddium។ "
				+ "ម៉ូដទាំងនេះមិនឆបគ្នាទេ ហើយបណ្តាលឲ្យមានកំហុស injection ដែលរារាំងការចាប់ផ្តើមហ្គេម។" + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "ការប៉ះទង្គិចរវាង OptiFine និង Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "លុប OptiFine ឬ Embeddium ព្រោះវាមិនឆបគ្នាជាមួយគ្នាទេ។";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>វាជារឿងធម្មតាជាមួយម៉ូដបង្កើតពិភពលោកដែលប៉ះទង្គិចគ្នា ជាពិសេស Terralinth, AmplifiedNether, Nullscape និង Incendium និងម៉ូដបង្កើតពិភពលោកផ្សេងទៀត។ ក៏អាចទៅរួចដែរថាអ្នកត្រូវដំឡើងម៉ូដដែលបាត់មួយ។</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable កំពុងព្យាយាមផ្ទុកនៅលើ server dedicated ប៉ុន្តែវាឆបគ្នាតែជាមួយ client ប៉ុណ្ណោះ។ "
				+ "លុប Controllable ចេញពី server ឬប្រាកដថាវាត្រូវបានដំឡើងតែនៅលើ client ប៉ុណ្ណោះ។" + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable នៅលើ server dedicated";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "លុប Controllable ចេញពី server dedicated ព្រោះវាគួរតែត្រូវបានដំឡើងតែនៅលើ client ប៉ុណ្ណោះ។";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries កំពុងបង្កកំហុសដែលរារាំងការផ្ទុក server។ "
				+ "ម៉ូដនេះមានបញ្ហាជាមួយកំណត់ហេតុអំពីអាកប្បកិរិយាភ្លើង ដែលបណ្តាលឲ្យបរាជ័យអំឡុងពេលផ្ទុក datapacks។"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries រារាំងការផ្ទុក server";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "សូមព្យាយាមធ្វើបច្ចុប្បន្នភាព Supplementaries ទៅកំណែចុងក្រោយបំផុត ឬបិទវាជាបណ្តោះអាសន្ន ដើម្បីអនុញ្ញាតឱ្យ server ផ្ទុកបាន។";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) បានរកឃើញបញ្ហាជាមួយម៉ូឌុល Jackson ដែលបាត់។ "
				+ "ម៉ូដខ្លះដូចជា Valkyrien Skies អាចបណ្តាលឱ្យមានកំហុសនេះ ដោយសារមិនបានរួមបញ្ចូល dependencies ទាំងអស់ដែលត្រូវការ។"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "ម៉ូឌុល Jackson បាត់នៅក្នុង Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "លុប Groovy Modloader និងម៉ូដដែលពាក់ព័ន្ធដូចជា Valkyrien Skies ដែលអាចបង្កការប៉ះទង្គិច dependency។";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Every Compat បានរកឃើញឈ្មោះ block ឈើមិនត្រឹមត្រូវ។ " + "Every Compat ជាទូទៅមានបញ្ហាច្រើន។ កុំប្រើវា!"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "ឈ្មោះមិនត្រឹមត្រូវនៅក្នុង Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "ពិនិត្យ resource packs ឬម៉ូដដែលប្រើ Every Compat ព្រោះវាអាចមានឈ្មោះ block មិនត្រឹមត្រូវ។";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "បានរកឃើញកូដកំហុស (-1073741819) ដែលអាចបណ្តាលមកពី overlays ដូចជា GameCaster របស់ Razer, Discord, OBS Studio ឬបញ្ហាជាមួយ drivers NVIDIA។"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "កូដកំហុស -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "ព្យាយាមបិទ overlays ដូចជា GameCaster, Discord ឬ OBS Studio ហើយពិនិត្យថា drivers NVIDIA របស់អ្នកត្រូវបានធ្វើបច្ចុប្បន្នភាព។";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips ត្រូវការ Immersive Messages ជា dependency ប៉ុន្តែមិនបានដំឡើង។ "
				+ "ដំឡើង Immersive Messages ដើម្បីឲ្យ Immersive Tooltips ដំណើរការបានត្រឹមត្រូវ។" + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips ខ្វះ dependency";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "ដំឡើង Immersive Messages ព្រោះវាជា dependency ចាំបាច់សម្រាប់ Immersive Tooltips។";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins មានបញ្ហាភាពឆបគ្នាជាមួយ Apoli Mod ដែល ItemStack មិនអាចបម្លែងទៅជា EntityLinkedItemStack បាន។ "
				+ "វាជាញឹកញាប់កើតឡើងនៅកំណែខ្ពស់ជាង 6.6.0។ សូមពិចារណាប្រើកំណែចាស់ ឬប្តូររវាង Fabric និង Forge។" + "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "កំហុសបម្លែងប្រភេទក្នុង Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "ប្រើកំណែ Medieval Origins 6.6.0 ឬចាស់ជាងនេះ ឬព្យាយាមប្តូររវាង Fabric និង Forge។";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether កំពុងបង្កកំហុសដោយសារ Registry Object មិនមាននៅក្នុង MusicManager។ "
				+ "បញ្ហានេះពាក់ព័ន្ធនឹង mixin របស់ MusicManager ក្នុង Reign of Nether។" + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "កំហុស MusicManager ក្នុង Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "ធ្វើបច្ចុប្បន្នភាព Reign of Nether ឬលុបវាជាបណ្តោះអាសន្ន ដើម្បីដោះស្រាយកំហុស។";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel គាំទ្រតែ server YSM នៅលើ Linux ឬ Android ប៉ុណ្ណោះ។ "
				+ "បញ្ហានេះត្រូវបានជួសជុលក្នុងកំណែថ្មីៗបន្ទាប់ពីថ្ងៃទី 23 វិច្ឆិកា 2025 នៅលើ Modrinth។" + "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel មិនឆបគ្នាជាមួយ Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "ធ្វើបច្ចុប្បន្នភាព YesSteveModel ទៅកំណែថ្មីពី Modrinth ព្រោះបញ្ហានេះត្រូវបានជួសជុលរួច។";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "រកឃើញការប៉ះទង្គិចធ្ងន់ធ្ងររវាង Moving Elevators និង OptiFine។ "
				+ "ម៉ូដទាំងនេះមិនឆបគ្នា ហើយបណ្តាលឲ្យមានកំហុស injection ដែលរារាំងការចាប់ផ្តើមហ្គេម។" + "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "ការប៉ះទង្គិច Moving Elevators និង OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "លុប OptiFine ឬ Moving Elevators ព្រោះវាមិនឆបគ្នា។";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "រកឃើញការប៉ះទង្គិចធ្ងន់ធ្ងររវាង Fabric API (fabric-resource-loader-v0) និង OptiFine។ "
				+ "ម៉ូដទាំងនេះមិនឆបគ្នា ហើយបណ្តាលឲ្យមានកំហុស injection ដែលរារាំងការចាប់ផ្តើមហ្គេម។" + "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "ការប៉ះទង្គិច Fabric API និង OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "លុប OptiFine ឬធ្វើបច្ចុប្បន្នភាព Fabric API ទៅកំណែដែលឆបគ្នា។";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "មានម៉ូដមួយដែលមាន ITransformationService ខូច ដែលមិនអាចបង្កើតបាន៖ " + claseProveedor
				+ ". ម៉ូដនេះត្រូវតែលុបចេញ ដើម្បីអនុញ្ញាតឲ្យហ្គេមផ្ទុកបាន។" + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "ITransformationService ខូច";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "លុបម៉ូដដែលមានថ្នាក់ " + claseProveedor + " ព្រោះវាមាន ITransformationService ខូច។";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError() + "'>ម៉ូដមួយមានការកំណត់កំណែមិនត្រឹមត្រូវ។ "
				+ "កំណែត្រូវតែស្ថិតនៅក្នុងសញ្ញាក្រចក []។ "
				+ "អ្នកអាចប្រើឧបករណ៍ grep/greprf នៅផ្នែកខាងឆ្វេង ដើម្បីស្វែងរកកំណែ </span>" + version
				+ "<span style='color:#" + config.obtenerColorError() + "'> ហើយកំណត់ថាម៉ូដណាដែលមានបញ្ហា។</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "កំណែមិនត្រឹមត្រូវក្នុងម៉ូដ";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "ប្រើឧបករណ៍ grep/greprf នៅផ្នែកខាងឆ្វេង ដើម្បីស្វែងរកកំណែមានបញ្ហា និងរកម៉ូដដែលមានវា។";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុស stack smashing ដែលបានបញ្ចប់ដំណើរការ។ "
				+ "វាអាចបណ្តាលមកពីបញ្ហាជាមួយ Early Window នៅក្នុង Forge/NeoForge/PillowMC ឬជាមួយ LWJGL 3.2.2 និងកំណែថ្មីៗ។"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "រកឃើញ Stack Smashing";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "ពិនិត្យការកំណត់ Early Window ហើយពិចារណាប្រើកំណែផ្សេងនៃ LWJGL ឬពិនិត្យម៉ូដដែលពាក់ព័ន្ធនឹង window ដំបូង។";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore គឺសម្រាប់ modpack ជាក់លាក់ប៉ុណ្ណោះ ហើយមិនគួរប្រើនៅក្នុងការដំឡើងទូទៅទេ ព្រោះវាបង្កបញ្ហា។"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore ជាមួយកំណែ Java មិនឆបគ្នា";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "លុប GregTechEasyCore ព្រោះវាសម្រាប់ modpack ជាក់លាក់ប៉ុណ្ណោះ និងមិនឆបគ្នាជាមួយការដំឡើងទូទៅរបស់អ្នក។";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "រកឃើញការប៉ះទង្គិចរវាង MoniLabs និង Connector Extras ដែលពាក់ព័ន្ធនឹងការកែប្រែ KubeJS។ "
				+ "ម៉ូដទាំងនេះមិនឆបគ្នា នៅក្នុងការកែប្រែ KubeJS របស់ពួកវា។" + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "ការប៉ះទង្គិច MoniLabs និង Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "ព្យាយាមលុបមួយក្នុងចំណោមម៉ូដ (MoniLabs ឬ Connector Extras) ព្រោះវាមានការប៉ះទង្គិចក្នុងការកែប្រែ KubeJS។";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris ត្រូវការ Distant Horizons [2.0.4] ឬ DH API កំណែ [1.1.0] ឬថ្មីជាងនេះ។ "
				+ "សូមមើលមគ្គុទេសក៍ភាពឆបគ្នានៅ https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e ដើម្បីដោះស្រាយបញ្ហា។"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "ភាពឆបគ្នា Iris និង Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "មើលមគ្គុទេសក៍ភាពឆបគ្នា ហើយធ្វើបច្ចុប្បន្នភាព Iris និង Distant Horizons ទៅកំណែដែលឆបគ្នា។";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមាន classes របស់ Minecraft ខ្វះ។ មូលហេតុអាចជា៖</b>" + "<ul>"
				+ "<li>អ្នកមានម៉ូដសម្រាប់កំណែផ្សេងនៃហ្គេម។ អ្នកអាចប្រើ <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> ដើម្បីពិនិត្យថា class មាននៅក្នុងកំណែរបស់អ្នកឬអត់។</li>"
				+ "<li>ការដំឡើង Minecraft មានបញ្ហា (ជាញឹកញាប់ជាមួយ CurseForge App, ModrinthApp/Theseus/Astralrinth និង launcher ផ្សេងៗ)។ <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>មើលវីដេអូ</a> ដើម្បីដោះស្រាយបញ្ហា CurseForge។</li>"
				+ "<li>មាន coremod ខូច (បើ coremod ខូច វាអាចរារាំងការផ្ទុក class)។</li>" + "</ul>"
				+ "<p>ចំណាំ៖ អ្នកអាចប្រើឧបករណ៍ <b>grepr/fgrepr</b> នៅផ្នែកខាងឆ្វេង ដើម្បីរកម៉ូដដែលយោងទៅ classes ដែលខ្វះ។</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមាន classes របស់ DangerZone ខ្វះ។ មូលហេតុអាចជា៖</b>" + "<ul>"
				+ "<li>អ្នកមានម៉ូដសម្រាប់កំណែផ្សេងនៃហ្គេម។</li>" + "<li>អ្នកមាន coremods ខូច។</li>"
				+ "<li>launcher ឬការដំឡើងមានបញ្ហា។</li>" + "</ul>"
				+ "<p>ចំណាំ៖ អ្នកអាចប្រើឧបករណ៍ <b>grepr/fgrepr</b> នៅផ្នែកខាងឆ្វេង ដើម្បីរកម៉ូដដែលយោងទៅ classes ដែលខ្វះ។</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមាន classes របស់ FeatureCreep ខ្វះ។ មូលហេតុអាចជា៖</b>" + "<ul>"
				+ "<li>អ្នកមានម៉ូដសម្រាប់កំណែផ្សេងនៃ FeatureCreep (ឧ. ESR ទល់នឹង Nightly ឬ v4 ទល់នឹង v12)។</li>"
				+ "<li>អ្នកអាចដំឡើង FeatureCreep ពី CurseForge ឬ MinecraftStorage។</li>" + "</ul>"
				+ "<p>ចំណាំ៖ អ្នកអាចប្រើឧបករណ៍ <b>grepr/fgrepr</b> នៅផ្នែកខាងឆ្វេង ដើម្បីរកម៉ូដដែលយោងទៅ classes ដែលខ្វះ។</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមាន classes របស់ ModLauncher ខ្វះ។ មូលហេតុអាចជា៖</b>" + "<ul>"
				+ "<li>ម៉ូដរបស់អ្នកសម្រាប់ build ផ្សេងនៃ MinecraftForge, PillowMC ឬ NeoForge។</li>"
				+ "<li>មានការធ្វើបច្ចុប្បន្នភាព modloader ច្រើនសម្រាប់កំណែ Minecraft មួយ។</li>"
				+ "<li>launcher ឬការដំឡើងមានបញ្ហា (ជាញឹកញាប់ជាមួយ CurseForge App, ModrinthApp/Theseus/Astralrinth)។ <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>មើលវីដេអូ</a> ដើម្បីដោះស្រាយបញ្ហា។</li>"
				+ "</ul>"
				+ "<p>ចំណាំ៖ អ្នកអាចប្រើឧបករណ៍ <b>grepr/fgrepr</b> នៅផ្នែកខាងឆ្វេង ដើម្បីរកម៉ូដដែលយោងទៅ classes ដែលខ្វះ។</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមាន classes របស់ Minecraft Forge ខ្វះ។ មូលហេតុអាចជា៖</b>" + "<ul>"
				+ "<li>ម៉ូដរបស់អ្នកសម្រាប់ build ផ្សេងនៃ MinecraftForge។</li>"
				+ "<li>មានការធ្វើបច្ចុប្បន្នភាព modloader ច្រើនសម្រាប់កំណែ Minecraft មួយ។</li>"
				+ "<li>ការដំឡើងមានបញ្ហា (ជាញឹកញាប់ជាមួយ CurseForge App, ModrinthApp/Theseus/Astralrinth)។ <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>មើលវីដេអូ</a> ដើម្បីដោះស្រាយបញ្ហា។</li>"
				+ "</ul>"
				+ "<p>ចំណាំ៖ អ្នកអាចប្រើឧបករណ៍ <b>grepr/fgrepr</b> នៅផ្នែកខាងឆ្វេង ដើម្បីរកម៉ូដដែលយោងទៅ classes ដែលខ្វះ។</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមាន classes របស់ NeoForge ខ្វះ។ មូលហេតុអាចជា៖</b>" + "<ul>"
				+ "<li>ម៉ូដរបស់អ្នកសម្រាប់ build ផ្សេងនៃ NeoForge។</li>"
				+ "<li>មានការធ្វើបច្ចុប្បន្នភាព modloader ច្រើនសម្រាប់កំណែ Minecraft មួយ។</li>"
				+ "<li>ការដំឡើងមានបញ្ហា (ជាញឹកញាប់ជាមួយ CurseForge App, ModrinthApp/Theseus/Astralrinth)។ <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>មើលវីដេអូ</a> ដើម្បីដោះស្រាយបញ្ហា។</li>"
				+ "</ul>"
				+ "<p>ចំណាំ៖ អ្នកអាចប្រើឧបករណ៍ <b>grepr/fgrepr</b> នៅផ្នែកខាងឆ្វេង ដើម្បីរកម៉ូដដែលយោងទៅ classes ដែលខ្វះ។</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes clases faltantes de Fabric Loader. Posibles razones:</b>" + "<ul>"
				+ "<li>Tus mods son para una build diferente de Fabric Loader.</li>"
				+ "<li>Hay muchas actualizaciones de modloaders para una កំណែ de Minecraft.</li>"
				+ "<li>Tienes una instalación defectuosa (común con CurseForge App, ModrinthApp/Theseus/Astralrinth y otros lanzadores de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Ver tutorial</a> para solucionar បញ្ហាs con CurseForge.</li>"
				+ "<li>Muchos mods requieren Fabric API. Por favor, instala Fabric API si es necesario.</li>" + "</ul>"
				+ "<p>Nota: Puedes usar la herramienta <b>grepr/fgrepr</b> en la barra lateral para encontrar los mods que hacen referencia a las clases faltantes, siempre que uses '/' en los nombres.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>អ្នកមាន classes របស់ PillowMC ខ្វះ។ មូលហេតុអាចជា៖</b>" + "<ul>"
				+ "<li>ម៉ូដរបស់អ្នកសម្រាប់ build ផ្សេងនៃ PillowMC។</li>"
				+ "<li>មានការធ្វើបច្ចុប្បន្នភាព modloader ច្រើនសម្រាប់កំណែ Minecraft មួយ។</li>"
				+ "<li>ការដំឡើងមានបញ្ហា (ជាញឹកញាប់ជាមួយ CurseForge App, ModrinthApp/Theseus/Astralrinth និង launcher ផ្សេងៗ)។ <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>មើលវីដេអូ</a> ដើម្បីដោះស្រាយបញ្ហា CurseForge។</li>"
				+ "</ul>"
				+ "<p>ចំណាំ៖ អ្នកអាចប្រើឧបករណ៍ <b>grepr/fgrepr</b> នៅផ្នែកខាងឆ្វេង ដើម្បីរកម៉ូដដែលយោងទៅ classes ដែលខ្វះ។</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "អ្នកមានម៉ូដមួយដែលបង្ក lag ដោយចេតនា។ Uranium គឺជាម៉ូដបង្ក lag។ វាមិនតែងតែបណ្តាលឱ្យ crash ទេ ប៉ុន្តែអាចបណ្តាលបាននៅពេលក្រោយ។"
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack ត្រូវបានសម្គាល់ថាឆបគ្នាជាមួយ 1.19.* ប៉ុន្តែវាជាសម្រាប់ 1.20.* ដែលបណ្តាលឱ្យមានកំហុស class មិនរកឃើញ។ "
				+ "ម៉ូដកំពុងព្យាយាមប្រើ DamageSources ដែលមិនមាននៅក្នុងកំណែ Minecraft បច្ចុប្បន្ន។" + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "កំហុសកំណែក្នុង Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "ប្រាកដថាអ្នកប្រើកំណែត្រឹមត្រូវរបស់ Falling Attack ដែលឆបគ្នាជាមួយកំណែ Minecraft របស់អ្នក។";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>").append("អ្នកត្រូវដំឡើង CFR (Class File Reader) ដើម្បីប្រើមុខងារនេះ។<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append("នៅលើ Linux, NetBSD ឬ FreeBSD អ្នកអាចដំឡើង CFR តាម package manager របស់អ្នក។<br>").append(
					"ស្វែងរក package នៅទីនេះ៖ <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append("ជាជម្រើសផ្សេង អ្នកអាចទាញយកកំណែដែលបានកែប្រែដែល FabricMC ប្រើពី៖<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("រក្សាទុកវានៅក្នុងថតខាងក្រោម៖<br>").append("<b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>សំខាន់៖</b> បន្ទាប់ពីដំឡើង CFR អ្នកត្រូវចាប់ផ្តើម mod ឡើងវិញ ដើម្បីឲ្យវាត្រូវបានស្គាល់។")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "មិនមានរូបភាពទេ";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "មិនអាចរកឃើញ class: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "កម្មវិធី decompile CFR – Sakura Riddle (មិនផ្លូវការ)";
	}

	@Override
	public String cfrClaseActual() {
		return "class បច្ចុប្បន្ន";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "រូបភាព Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "កំហុសក្នុងការផ្ទុករូបភាព";
	}

	public String noticiaLegalCFR() {
		return "កម្មវិធី GUI នេះសម្រាប់ decompile ម៉ូដ ត្រូវបានបង្កើតឡើងដើម្បីជួយអ្នកប្រើរកមូលហេតុនៃកំហុសក្នុង software។ "
				+ "ទោះយ៉ាងណា ការប្រើ code ដែលបាន decompile អាចប៉ះពាល់ដល់ច្បាប់រក្សាសិទ្ធិ ដូច្នេះអ្នកគួរប្រុងប្រយ័ត្ន។ "
				+ "សូមពិនិត្យ license របស់ម៉ូដមុនពេលប្រើ code។ "
				+ "ជាញឹកញាប់ ម៉ូដផ្តល់ source code ផ្លូវការ ដែលស្អាត និងងាយយល់ជាង code ដែលបាន decompile។ "
				+ "ការគោរពច្បាប់រក្សាសិទ្ធិ និង license គឺសំខាន់សម្រាប់សហគមន៍។ " + "អ្នកអាចមើលច្បាប់នៅទីនេះ៖ "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Ley Federal de Derechos de Autor (ខ្មែរ)</a> "
				+ "និងកំណែអង់គ្លេសនៅទីនេះ៖ <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (English)</a>។ "
				+ "ក៏មានច្បាប់សហរដ្ឋអាមេរិកនៅទីនេះ៖ "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>។ "
				+ "សម្រាប់ការវិភាគកម្រិតខ្ពស់ អ្នកអាចប្រើ Enigma ពី FabricMC នៅ "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a> " + "ឬប្រើ Recaf នៅ "
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">គេហទំព័រ</a>។";
	}

	@Override
	public String botonDescargarCfr() {
		return "ទាញយក CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "បើកថតដំឡើង";
	}

	@Override
	public String colorFondoPrincipal() {
		return "ពណ៌ផ្ទៃខាងក្រោយសំខាន់";
	}

	@Override
	public String colorTextoBotonReset() {
		return "ពណ៌អក្សរប៊ូតុងកំណត់ឡើងវិញ";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "ពណ៌អក្សរក្នុងប្រអប់ស្វែងរក";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "ពណ៌អក្សរនៅក្នុងម៉ឺនុយជ្រើសរើស";
	}

	@Override
	public String colorTextoRenderer() {
		return "ពណ៌អក្សរនៃ renderer";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "ពណ៌អក្សរនៃ loading overlay";
	}

	@Override
	public String colorBorde() {
		return "ពណ៌ស៊ុម";
	}

	@Override
	public String colorFondoRetrato() {
		return "ពណ៌ផ្ទៃខាងក្រោយក្នុងរបៀប portrait";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "ពណ៌តំណចែករំលែក";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "ពណ៌ផ្ទៃខាងក្រោយនៃប្រអប់ចែករំលែក";
	}

	@Override
	public String rosaFondo() {
		return "ផ្កាឈូកផ្ទៃខាងក្រោយ";
	}

	@Override
	public String rosaSuave() {
		return "ផ្កាឈូកស្រាល";
	}

	@Override
	public String moradoAcento() {
		return "ពណ៌ស្វាយសំឡេងសំខាន់";
	}

	@Override
	public String textoOscuro() {
		return "អក្សរពណ៌ងងឹត";
	}

	@Override
	public String bordeSuave() {
		return "ស៊ុមស្រាល";
	}

	@Override
	public String fondoCampo() {
		return "ផ្ទៃខាងក្រោយប្រអប់";
	}

	@Override
	public String fondoVistaPrevia() {
		return "ផ្ទៃខាងក្រោយនៃការមើលជាមុន";
	}

	@Override
	public String sintaxisConstructor() {
		return "ពណ៌វាក្យសម្ព័ន្ធ៖ constructor";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "ពណ៌វាក្យសម្ព័ន្ធ៖ សារជំនួយ";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "ពណ៌វាក្យសម្ព័ន្ធ៖ ស្លាក HTML";
	}

	@Override
	public String colorFondoVentana() {
		return "ពណ៌ផ្ទៃខាងក្រោយនៃបង្អួច";
	}

	@Override
	public String colorPanel() {
		return "ពណ៌របស់ផ្ទាំង";
	}

	@Override
	public String colorBotonTexto() {
		return "ពណ៌អក្សរនៃប៊ូតុង";
	}

	@Override
	public String colorCampo() {
		return "ពណ៌របស់ប្រអប់";
	}

	@Override
	public String colorBordeDestacado() {
		return "ពណ៌ស៊ុមដែលបានរំលេច";
	}

	@Override
	public String colorSeleccionTexto() {
		return "ពណ៌ផ្ទៃខាងក្រោយនៃការជ្រើសរើសអត្ថបទ";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "ពណ៌អត្ថបទដែលបានជ្រើសរើស";
	}

	@Override
	public String colorEstadoExito() {
		return "ពណ៌ស្ថានភាព៖ ជោគជ័យ";
	}

	@Override
	public String colorEstadoFallo() {
		return "ពណ៌ស្ថានភាព៖ បរាជ័យ";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "ពណ៌ស្ថានភាព៖ រូបថតចម្លង";
	}

	@Override
	public String colorResultadoAnadido() {
		return "ពណ៌លទ្ធផលដែលបានបន្ថែម";
	}

	@Override
	public String colorResultadoEliminado() {
		return "ពណ៌លទ្ធផលដែលបានលុប";
	}

	@Override
	public String colorBordeScroll() {
		return "ពណ៌ស៊ុមរបស់ scroll";
	}

	@Override
	public String colorFondoPanel() {
		return "ពណ៌ផ្ទៃខាងក្រោយរបស់ផ្ទាំង";
	}

	@Override
	public String colorBeigeListas() {
		return "ពណ៌ beige នៃបញ្ជី";
	}

	@Override
	public String colorTextoListas() {
		return "ពណ៌អត្ថបទនៅក្នុងបញ្ជី";
	}

	@Override
	public String colorBordeListas() {
		return "ពណ៌ស៊ុមនៃបញ្ជី";
	}

	@Override
	public String colorBotonFondo() {
		return "ពណ៌ផ្ទៃខាងក្រោយនៃប៊ូតុង";
	}

	@Override
	public String colorBordeBoton() {
		return "ពណ៌ស៊ុមនៃប៊ូតុង";
	}

	@Override
	public String colorDoradoTexto() {
		return "ពណ៌មាសនៃអត្ថបទ";
	}

	@Override
	public String colorPila() {
		return "ពណ៌នៃ stack trace";
	}

	@Override
	public String colorTextoPanel() {
		return "ពណ៌អត្ថបទរបស់ផ្ទាំង";
	}

	@Override
	public String colorTextoNegro() {
		return "ពណ៌អត្ថបទខ្មៅ";
	}

	@Override
	public String colorTextoPrincipal() {
		return "ពណ៌អត្ថបទសំខាន់";
	}

	@Override
	public String colorFondoResultados() {
		return "ពណ៌ផ្ទៃខាងក្រោយនៃលទ្ធផល";
	}

	@Override
	public String colorEstado() {
		return "ពណ៌ស្ថានភាព";
	}

	@Override
	public String colorTextoDescripcion() {
		return "ពណ៌អត្ថបទពិពណ៌នា";
	}

	@Override
	public String colorTextoEstado() {
		return "ពណ៌អត្ថបទស្ថានភាព";
	}

	@Override
	public String colorTextoExtra() {
		return "ពណ៌អត្ថបទបន្ថែម";
	}

	@Override
	public String colorSeparador() {
		return "ពណ៌បន្ទាត់បំបែក";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "បានរកឃើញកំហុស native <code>StubRoutines::SafeFetch32</code>។ "
				+ "បញ្ហានេះកើតឡើងនៅលើ macOS ជាមួយ JDK 17.0.9 ហើយត្រូវបានជួសជុលនៅក្នុង JDK 17.0.10 ឬកំណែថ្មីជាងនេះ។ https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "កំហុស native SafeFetch32 នៅក្នុង JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "ធ្វើបច្ចុប្បន្នភាព JDK របស់អ្នកទៅកំណែ 17.0.10 ឬខ្ពស់ជាងនេះ (ឧទាហរណ៍ 17.0.15)។";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "ប្រសិនបើអ្នកប្រើ launcher ដូចជា MultiMC, Prism Launcher ឬ TLauncher សូមកំណត់ឲ្យវាប្រើ JDK កំណែថ្មីជាងនេះ។ "
				+ "ខ្លះៗមាន JDK 17.0.15 រួមបញ្ចូលរួចហើយ។";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "ម៉ូដ Spark ក៏អាចបង្កឲ្យមានបញ្ហានេះផងដែរ។ ពិចារណាបិទវាបណ្តោះអាសន្ន។ https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ម៉ូដ MCEF (Chromium Embedded Framework) កំពុងបង្កឲ្យកម្មវិធីគាំងដោយគ្មានសារ។</b>" + "<ul>"
				+ "<li>MCEF កំពុងចាប់ផ្តើមនៅចុងបញ្ចប់ log ដែលជាសញ្ញាថាកម្មវិធីបានគាំងក្នុងពេលផ្ទុក។</li>"
				+ "<li>ម៉ូដនេះត្រូវបានគេស្គាល់ថាបង្កបញ្ហានៅលើ Linux, macOS ឬជាមួយ Java កំណែខ្លះៗ។</li>"
				+ "<li>មិនតែងតែមានសារកំហុសច្បាស់ទេ ប៉ុន្តែកម្មវិធីមិនចូលដល់ម៉ឺនុយមេឡើយ។</li>" + "</ul>"
				+ "<p>ប្រសិនបើអ្នកមិនត្រូវការមុខងារ browser ក្នុងហ្គេមទេ សូមលុបម៉ូដនេះ។</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "បញ្ហាក្នុងការចាប់ផ្តើម MCEF (ម៉ូដ browser ដែលបានបង្កប់)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "លុបឯកសារ MCEF (ស្វែងរកពាក្យ 'mcef' នៅក្នុងឈ្មោះឯកសារ) ពីថត 'mods'។";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "ប្រសិនបើអ្នកត្រូវការវា សូមប្រាកដថាប្រើកំណែដែលត្រូវគ្នាជាមួយប្រព័ន្ធ និង Minecraft របស់អ្នក។";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "បានរកឃើញការប៉ះទង្គិចរវាង <b>OptiFine</b> និង <b>Iris/Oculus</b>។</b>" + "<ul>"
				+ "<li>OptiFine កែប្រែ rendering របស់ Minecraft ដែលមិនត្រូវគ្នាជាមួយ Iris ឬ Oculus។</li>"
				+ "<li>កំហុស <code>MixinLevelRenderer failed injection check</code> មកពី <code>mixins.iris.json</code> ឬ <code>mixins.oculus.json</code>។</li>"
				+ "</ul>"
				+ "<p>ម៉ូដទាំងនេះមិនអាចប្រើជាមួយគ្នាបានទេ។ លុប OptiFine ដើម្បីប្រើ shaders ជាមួយ Iris ឬ Oculus។</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "ការប៉ះទង្គិចរវាង OptiFine និង Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "លុបឯកសារ OptiFine ពីថត 'mods'។";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "ប្រើ Iris ឬ Oculus ដោយមិនមាន OptiFine សម្រាប់ shaders ទំនើប។";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "បានរកឃើញការប៉ះទង្គិចរវាង <b>ModernFix</b> និង <b>OptiFine</b>។</b>" + "<ul>"
				+ "<li>ModernFix មិនត្រូវគ្នាជាមួយ OptiFine ព្រោះវាធ្វើឲ្យមុខងារ Forge ខូច និងធ្វើឲ្យការចាប់ផ្តើមយឺត។</li>"
				+ "<li>ModernFix ផ្ទាល់បានព្រមានថា: <i>\"Use of ModernFix with OptiFine is not supported\"</i>។</li>"
				+ "</ul>" + "<p>អ្នកត្រូវលុបមួយក្នុងចំណោមម៉ូដទាំងពីរ។</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "ការប៉ះទង្គិចរវាង ModernFix និង OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "លុប OptiFine ឬ ModernFix ពីថត 'mods'។ មិនអាចប្រើជាមួយគ្នាបានទេ។";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "ប្រសិនបើអ្នកត្រូវការការកែលម្អប្រសិទ្ធភាព សូមប្រើតែ OptiFine ឬជំនួស ModernFix ដោយ FerriteCore ឬ EntityCulling។";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "កំហុស៖ clave កំណត់ហេតុមិនត្រឹមត្រូវ។</b>"
				+ "<ul>" + "<li><b>Clave:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>នៅក្នុង Minecraft claves ត្រូវតែជាអក្សរតូចប៉ុណ្ណោះ និងប្រើតែអក្សរ លេខ '_' '-' និង '/'។</li>"
				+ "<li>បញ្ហានេះជាទូទៅបណ្តាលមកពី mod ឬ datapack ខូច។</li>" + "</ul>"
				+ "<p>ប្រើ <b>grepr</b> ឬ <b>fgrepr</b> ហើយបើកជម្រើស \"ស្វែងរកក្នុង JAR\" ដើម្បីរកមូលហេតុ។</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "Clave កំណត់ហេតុមានអក្សរធំ ឬមិនត្រឹមត្រូវ";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "ប្រើ 'grepr' ឬ 'fgrepr' ដើម្បីស្វែងរក mod ដែលបង្កបញ្ហា។";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "ប្រសិនបើមិនអាចរកឃើញបាន សូមលុប mods ថ្មីៗ ជាពិសេសដែលបន្ថែម blocks ឬ items។";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>កំហុសក្នុងការផ្ទុកម៉ូដ <b>" + escapeHtml(modNombre)
				+ "</b>។</b>" + "<ul>" + "<li>ម៉ូដមិនអាចចាប់ផ្តើមបានត្រឹមត្រូវ។</li>"
				+ "<li>ជាទូទៅបណ្តាលមកពីការមិនត្រូវគ្នាជាមួយ Minecraft ឬ mods ផ្សេង។</li>" + "</ul>"
				+ "<p>សូមលុប ឬធ្វើបច្ចុប្បន្នភាពម៉ូដនេះ។</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "កំហុសការចាប់ផ្តើមម៉ូដ (Fabric)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "លុបម៉ូដ '" + modNombre + "' ពីថត 'mods'។";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "ធ្វើបច្ចុប្បន្នភាពម៉ូដ '" + modNombre + "' ទៅកំណែដែលត្រូវគ្នា។";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុសដែលពាក់ព័ន្ធនឹងម៉ូដ <b>En Garde!</b>។</b>" + "<ul>"
				+ "<li>ម៉ូដនេះបន្ថែមប្រព័ន្ធប្រយុទ្ធ melee។</li>"
				+ "<li>កំហុសជាទូទៅបណ្តាលមកពីការប៉ះទង្គិចជាមួយ mods ផ្សេង។</li>" + "</ul>"
				+ "<p>ពិចារណាលុបវាបើមិនចាំបាច់។</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "កំហុសក្នុងម៉ូដ En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "ប្រើកំណែ En Garde! ដែលត្រូវគ្នាជាមួយ Minecraft និង loader របស់អ្នក។";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "បិទ mods ប្រយុទ្ធផ្សេងៗ ឬលុប En Garde! ដើម្បីជៀសវាងការប៉ះទង្គិច។";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "បានរកឃើញកំហុសពីម៉ូដ <b>IdleTweaks</b>។</b>"
				+ "<ul>" + "<li>បានព្យាយាមបិទ network channel មិនមាន។</li>"
				+ "<li>ជាទូទៅកើតឡើងនៅកំណែចាស់ ឬ server មិនត្រឹមត្រូវ។</li>" + "</ul>"
				+ "<p>ពិចារណាធ្វើបច្ចុប្បន្នភាព ឬលុបវា។</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "កំហុស IdleTweaks (channel មិនស្គាល់)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "ធ្វើបច្ចុប្បន្នភាព IdleTweaks ទៅកំណែថ្មី។";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "លុប IdleTweaks ពីថត 'mods' ប្រសិនបើមិនចាំបាច់។";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុស authentication (HTTP 401) នៅពេលចូល Minecraft។</b>"
				+ "<p>វាបង្ហាញថាអ្នកប្រើគណនីមិនត្រឹមត្រូវ (pirata)។</p>"
				+ "<p>ការគាំទ្រផ្លូវការ មិនអាចជួយបានក្នុងករណីនេះ។</p>"
				+ "<p>អាចបិទការត្រួតពិនិត្យនេះនៅក្នុង config ប៉ុន្តែមិនត្រឹមត្រូវជានិច្ច។</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>សិទ្ធិ Miranda ប្រសិនបើអ្នកព្យាយាមសុំជំនួយ:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Minecraft មិនមានអាជ្ញាប័ណ្ណ";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "បិទការផ្ទៀងផ្ទាត់ប្រឆាំងការលួចចម្លង";
	}

	@Override
	public String comprarMC() {
		return "ទិញ Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "អ្នកកំពុងប្រើ launcher <code>" + id
				+ "</code> ដែល <b>មិនស្ថិតក្នុងបញ្ជី launcher ដែលណែនាំ</b>។</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>បើទោះបីជាវាអាចដំណើរការបានក៏ដោយ launchers ដែលមិនត្រូវបានណែនាំជាញឹកញាប់បង្កឱ្យមាន៖</p>" + "<ul>"
				+ "<li>ការដំឡើង mods ឬ App ខូច។</li>" + "<li>ហ្គេមមិនចាប់ផ្តើម ឬគាំងដោយគ្មានសារកំហុសច្បាស់។</li>"
				+ "<li>រចនាសម្ព័ន្ធថតមិនធម្មតា (ធ្វើឱ្យការវិនិច្ឆ័យពិបាក)។</li>"
				+ "<li>អាកប្បកិរិយាមិនអាចទាយទុកជាមុនបានជាមួយ Java, memory ឬ mods។</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "សម្រាប់បទពិសោធន៍ល្អជាងនេះ សូមប្រើ launcher មួយក្នុងចំណោម launchers ដែលបានណែនាំទាំងនេះ៖";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Launcher មិនត្រូវបានណែនាំ";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "ប្តូរទៅ launcher មួយពីបញ្ជីដែលបានណែនាំ។";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "អ្នកកំពុងប្រើ <b>launcher ដែលមិនត្រូវបានណែនាំ</b>: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>launcher ដែលមិនត្រូវបានណែនាំអាចបង្កឱ្យមាន៖</p>" + "<ul>" + "<li>ការដំឡើង App ឬ mods ខូច។</li>"
				+ "<li>ហ្គេមមិនចាប់ផ្តើម ឬគាំងដោយគ្មានសារ។</li>"
				+ "<li>រចនាសម្ព័ន្ធឯកសារមិនធម្មតា (ពិបាកដោះស្រាយបញ្ហា)។</li>"
				+ "<li>មិនច្បាស់ថាវាគ្រប់គ្រង mods, Java ឬ memory ដូចម្តេច។</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "សូមណែនាំយ៉ាងខ្លាំងឱ្យប្រើ launcher មួយក្នុងចំណោម launchers ខាងក្រោម៖";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Launcher មិនត្រូវបានណែនាំ";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "ប្តូរទៅ launcher ដែលបានណែនាំ ដើម្បីទទួលបានការគាំទ្រ។";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ខ្វះ mods ដែលបានណែនាំសម្រាប់បរិយាកាសនេះ។</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "ខ្វះ mods ដែលបានណែនាំ";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "ដំឡើង mods ដែលបានណែនាំ ដើម្បីទទួលបានបទពិសោធន៍ល្អបំផុត។";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "បានរកឃើញ mods ដែលមិនត្រូវបានណែនាំនៅក្នុងការដំឡើងរបស់អ្នក។</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "បានរកឃើញ mods មិនត្រូវបានណែនាំ";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "លុប mods ដែលមិនត្រូវបានណែនាំ ដើម្បីជៀសវាងបញ្ហា។";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "បានរកឃើញការកែប្រែដោយគ្មានការអនុញ្ញាតនៅក្នុងឯកសារសំខាន់ៗ។ អ្នកបានកែប្រែឯកសារ ឬកំពុងប្រើ launcher មិនទុកចិត្តបាន។</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "បានរកឃើញការកែប្រែ";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "ដំឡើងឡើងវិញឯកសារដើម ដើម្បីស្ដារភាពត្រឹមត្រូវ។";
	}

	@Override
	public String configuracionCorporativa() {
		return "ការកំណត់សម្រាប់ក្រុមហ៊ុន";
	}

	@Override
	public String idiomaRespaldo() {
		return "ភាសាបម្រុង";
	}

	@Override
	public String buscardorHabilitado() {
		return "បើកមុខងារស្វែងរក";
	}

	@Override
	public String nombreHerramienta() {
		return "ឈ្មោះឧបករណ៍";
	}

	@Override
	public String condenarPirateria() {
		return "បដិសេធការលួចចម្លង";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "Launchers ដែលបានណែនាំ";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "Launchers មិនត្រូវបានណែនាំ";
	}

	@Override
	public String modsRecomendados() {
		return "Mods ដែលបានណែនាំ";
	}

	@Override
	public String modsDesaconsejados() {
		return "Mods មិនត្រូវបានណែនាំ";
	}

	@Override
	public String antiTamper() {
		return "ការការពារការកែប្រែ";
	}

	@Override
	public String proximamente() {
		return "ឆាប់ៗនេះ";
	}

	@Override
	public String informacion() {
		return "ព័ត៌មាន";
	}

	@Override
	public String errorCargandoImagen() {
		return "កំហុសក្នុងការផ្ទុករូបភាព";
	}

	@Override
	public String configuracionBasica() {
		return "ការកំណត់មូលដ្ឋាន";
	}

	@Override
	public String funcionalidades() {
		return "មុខងារ";
	}

	@Override
	public String derechosMiranda() {
		return "សិទ្ធិ Miranda (ណែនាំខ្លាំង)";
	}

	@Override
	public String gestionVerificaciones() {
		return "ការគ្រប់គ្រងការផ្ទៀងផ្ទាត់";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "ឈ្មោះ";
	}

	@Override
	public String codigoVerificacion() {
		return "កូដ";
	}

	@Override
	public String documentacionVerificacion() {
		return "ឯកសារពន្យល់";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "ការផ្ទៀងផ្ទាត់ដែលបានបើក៖";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "ការផ្ទៀងផ្ទាត់ដែលបានបិទ៖";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "បិទទាំងអស់ដែលមិនមែនសម្រាប់ក្រុមហ៊ុន";
	}

	@Override
	public String verCodigo() {
		return "មើលកូដ";
	}

	@Override
	public String verDocumentacion() {
		return "មើលឯកសារ";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "សូមជ្រើសការផ្ទៀងផ្ទាត់ដើម្បីបិទ។";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "សូមជ្រើសការផ្ទៀងផ្ទាត់ដើម្បីបើក។";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "បានបិទការផ្ទៀងផ្ទាត់ %d ដែលមិនសមស្របសម្រាប់ក្រុមហ៊ុន។";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "មិនមានការផ្ទៀងផ្ទាត់ដែលមិនមែនសម្រាប់ក្រុមហ៊ុនទេ។";
	}

	@Override
	public String operacionCompletada() {
		return "ប្រតិបត្តិការបានបញ្ចប់";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "យើងនឹក Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "ពណ៌នៃការផ្ទៀងផ្ទាត់សម្រាប់ក្រុមហ៊ុន";
	}

	// Métodos para la gestión de lanzadores
	@Override
	public String nombreLanzador() {
		return "ឈ្មោះ Launcher";
	}

	@Override
	public String motivo() {
		return "មូលហេតុ";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Launchers មិនត្រូវបានណែនាំ";
	}

	@Override
	public String moverADesaconsejados() {
		return "ផ្លាស់ទីទៅបញ្ជីមិនត្រូវបានណែនាំ";
	}

	@Override
	public String moverARecomendados() {
		return "ផ្លាស់ទីទៅបញ្ជីដែលបានណែនាំ";
	}

	@Override
	public String guardarCambios() {
		return "រក្សាទុកការផ្លាស់ប្តូរ";
	}

	@Override
	public String cancelar() {
		return "បោះបង់";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "សូមជ្រើស launcher មួយដើម្បីផ្លាស់ទី។";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "ការផ្លាស់ប្តូរត្រូវបានរក្សាទុកដោយជោគជ័យ!";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEs(String nombreLanzador) {
		return "launcher នេះមិនត្រូវបានណែនាំទេ ដោយសារមានបញ្ហាសុវត្ថិភាព និងស្ថេរភាពដែលគេស្គាល់។";
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
		return "មូលហេតុច្រើន";
	}

	@Override
	public String agregarLanzador() {
		return "បន្ថែម launcher";
	}

	@Override
	public String quitarLanzador() {
		return "ដក launcher ចេញ";
	}

	@Override
	public String editarRazones() {
		return "កែសម្រួលមូលហេតុ";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "ជ្រើស launcher មួយដើម្បីដកចេញ។";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "ជ្រើស launcher មួយដើម្បីកែសម្រួល។";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "កែសម្រួលមូលហេតុសម្រាប់ " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "បន្ថែមភាសាថ្មី";
	}

	@Override
	public String aceptar() {
		return "យល់ព្រម";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "ជ្រើសភាសា";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "launchers ទាំងនេះគឺជាអ្វីដែល CrashDetector ណែនាំថាល្អ។";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "លទ្ធផលត្រឹមត្រូវ";
	}

	public String modsNoRecomendados() {
		return "Mods មិនត្រូវបានណែនាំ";
	}

	public String agregarMod() {
		return "បន្ថែម mod";
	}

	public String quitarMod() {
		return "ដក mod ចេញ";
	}

	public String modId() {
		return "Mod ID/ឈ្មោះ JBoss Modules";
	}

	public String rutaMod() {
		return "ផ្លូវ / ឯកសាររបស់ mod";
	}

	public String errorDebeIndicarMod() {
		return "អ្នកត្រូវបញ្ជាក់យ៉ាងហោចណាស់ modid ឬផ្លូវរបស់ mod។";
	}

	public String modsNoRecomendadosAviso() {
		return "នៅទីនេះអ្នកអាចកត់ត្រា mods ដែលមិនត្រូវបានណែនាំ ដើម្បីឲ្យ CrashDetector អាចរកឃើញពេលវាត្រូវបានដំឡើង។";
	}

	@Override
	public String anularNormal() {
		// TODO Auto-generated method stub
		return "បដិសេធធម្មតា";
	}

	@Override
	public String anularNormalDescripcion() {
		// TODO Auto-generated method stub
		return "CrashDetector គួរតែព្រមាន ទោះបីជាមិន crash ក៏ដោយ";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "កត់ត្រា mods ដែល CrashDetector ណែនាំ។ ប្រសិនបើវាខ្វះ CrashDetector អាចជូនដំណឹងបាន។";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "ប្រសិនបើអ្នកសម្រេចចិត្តបើកការព្រមានប្រឆាំងការលួចចម្លង សូមណែនាំឲ្យកំណត់នៅទីនេះ "
				+ "សិទ្ធិរបស់បុគ្គលដែលស្នើសុំជំនួយ ជាវិធានការការពារ។\n\n"

				+ "ផ្ទុយពីការជឿជាក់ជាទូទៅ សហគមន៍ និងបណ្តាញជំនួយពេញនិយមជាច្រើន "
				+ "មិនត្រូវការបើកការព្រមានប្រឆាំងការលួចចម្លងដើម្បីផ្តល់ជំនួយទេ។ ទោះយ៉ាងណា "
				+ "ការកត់ត្រាសិទ្ធិទាំងនេះអាចមានប្រយោជន៍ ប្រសិនបើបុគ្គលម្នាក់ចូលទៅកាន់បណ្តាញជំនួយ " + "យ៉ាងណាក៏ដោយ។\n\n"

				+ "អ្នកអាចយកគំរូពីឯកសារផ្លូវការដូចជា Cartilla de Derechos Básicos del Detenido " + "នៅម៉ិកស៊ិក៖\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "ក៏ដូចជាគោលការណ៍ច្បាប់ស្រដៀងគ្នាដែលប្រើនៅក្នុងប្រទេសផ្សេងៗ រួមទាំង "
				+ "សហរដ្ឋអាមេរិក សហព័ន្ធរុស្ស៊ី សាធារណរដ្ឋប្រជាមានិតចិន សាធារណរដ្ឋអ៊ីស្លាម "
				+ "អ៊ីរ៉ង់ និងសាធារណរដ្ឋប្រជាមានិតប្រជាធិបតេយ្យកូរ៉េ។\n\n"

				+ "ឧទាហរណ៍ខ្លះនៃសិទ្ធិដែលអាចរួមបញ្ចូលមាន៖\n"
				+ "• សិទ្ធិមិនផ្តល់ព័ត៌មានដែលមិនចាំបាច់សម្រាប់ការគាំទ្រ ដូចជា launcher ដែលបានប្រើ "
				+ "ឈ្មោះអ្នកប្រើ ឬ UUID។\n" + "• សិទ្ធិមិនបញ្ជាក់ខ្លួនឯងថាមានកំហុស។\n"
				+ "• សិទ្ធិបដិសេធឆ្លើយសំណួរដែលមិនចាំបាច់សម្រាប់ដោះស្រាយបញ្ហា។\n"
				+ "• សិទ្ធិទទួលបានការណែនាំនៅក្នុង chat។\n"
				+ "• សិទ្ធិប្រើការលុបអត្តសញ្ញាណកំណត់ហេតុ (logs) ដែលមានស្រាប់នៅក្នុង CrashDetector។\n\n"

				+ "អត្ថបទនេះទទួលយក HTML។";
	}

	@Override
	public String editar() {
		// TODO Auto-generated method stub
		return "កែសម្រួល";
	}

	@Override
	public String advertenciaHashLento() {
		return "ព្រមាន៖ ការបន្ថែមឯកសារធំៗជាច្រើន អាចធ្វើឲ្យការផ្ទៀងផ្ទាត់ "
				+ "ចំណាយពេលច្រើននាទី។ CrashDetector នឹងត្រូវគណនា hash របស់ឯកសារនីមួយៗ "
				+ "មុននឹងបន្ត។ សូមណែនាំឲ្យការពារតែឯកសារដែលចាំបាច់ប៉ុណ្ណោះ។";
	}

	@Override
	public String agregarArchivo() {
		return "បន្ថែមឯកសារ";
	}

	@Override
	public String agregarCarpeta() {
		return "បន្ថែមថត";
	}

	@Override
	public String quitar() {
		return "ដកចេញ";
	}

	@Override
	public String rutaArchivo() {
		return "ផ្លូវឯកសារ";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "ផ្លូវដែលបានជ្រើសរើសស្ថិតក្រៅថតហ្គេមបច្ចុប្បន្ន។ "
				+ "អនុញ្ញាតតែឯកសារ និងថតនៅក្នុងថតបច្ចុប្បន្ន ឬថតរងប៉ុណ្ណោះ។";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>"
				+ "មតិ និងការពិភាក្សារបស់ Sylent Bell មិនចាំបាច់ត្រូវគ្នាជាមួយរបស់យើងទេ; "
				+ "យើងគ្រាន់តែគិតថាវាសប្បាយក្នុងការដាក់វាទីនេះ។ CrashDetector គឺជាសេគ្យុលា។" + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ម៉ូដ GML (Groovy ModLoader) ត្រូវការការផ្លាស់ប្តូរទាំងនេះ ហើយជាមូលហេតុធម្មតាបំផុតនៃបញ្ហានេះ។</b>";
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
				+ "បានរកឃើញការប្រើប្រាស់ <i>Independiente Flywheel</i>។</b>"
				+ "<p><b>Independiente Flywheel គឺចាស់ (deprecated)</b> ហើយមិនគួរប្រើនៅក្នុងកំណែទំនើបទេ។</p>"
				+ "<p>កំណែថ្មីៗនៃ <b>Create</b> <b>មាន Flywheel រួចហើយ</b> ដូច្នេះការដំឡើងវាដោយឡែក "
				+ "នឹងបង្កបញ្ហាភាពឆបគ្នា និងកំហុសពេលផ្ទុក។</p>" + "<p>ម៉ូដខ្លះដែលពឹងផ្អែកលើ Independiente Flywheel អាច "
				+ "<b>មិនដំណើរការ</b> ឬ <b>ដំណើរការមិនស្ថិរភាព</b>។ " + "ក្នុងករណីខ្លះ អាចដំណើរការបាន ប្រសិនបើ "
				+ "<b>កែប្រែឯកសារ <code>mods.toml</code> ដោយដៃ</b> ដើម្បីកែសម្រួលកំណែ "
				+ "ប៉ុន្តែនេះ <b>មិនត្រូវបានណែនាំ</b> ទេ។</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>ម៉ូដដែលបានរកឃើញដែលពាក់ព័ន្ធនឹង Flywheel:</b></p><ul>" + listaMods.toString() + "</ul>")
				+ "<p>ដំណោះស្រាយដែលបានណែនាំគឺ <b>លុប Independiente Flywheel</b> ហើយប្រើតែ "
				+ "កំណែដែលមានក្នុង Create ប៉ុណ្ណោះ។</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Flywheel ដោយឡែក";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុសពាក់ព័ន្ធនឹងម៉ូដ <i>Floral Enchantments</i>។</b>"
				+ "<p>ការរលំ (crash) បណ្តាលមកពីកំហុសខាងក្នុងរបស់ម៉ូដក្នុងការគ្រប់គ្រងទិន្នន័យហ្គេម "
				+ "ដែលបណ្តាលឱ្យមាន <b>NullPointerException</b> ពេលដំណើរការ។</p>"
				+ "<p>បញ្ហានេះភាគច្រើនអាចដោះស្រាយដោយធ្វើបច្ចុប្បន្នភាពម៉ូដ ឬលុបវាចេញ។</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "កំហុស Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "អ្នកមានកំណែ NeoForge និងកំណែធម្មតារបស់ MixinExtras។ ប្រសិនបើអ្នកប្រើ MinecraftForge អ្នកអាចដំឡើង "
				+ "<a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>តំណនេះ</a> ដើម្បីដោះស្រាយ។</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុសក្នុងស្រមោលដីជាមួយ shaders (Iris)។</b>"
				+ "<p>បញ្ហានេះកើតឡើងក្នុងអំឡុងពេលរេនដឺរ (rendering) ដី។</p>"
				+ "<p>សូមព្យាយាមលេងដោយមិនប្រើ shaders ឬបន្ថយគុណភាពក្រាហ្វិក " + "ជាពិសេសនៅការកំណត់ <b>Ultra</b>។</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "ស្រមោលដី (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញ tick ម៉ាស៊ីនបម្រើយូរពេក។</b>" + "<p>នេះមានន័យថាហ្គេមបានជាប់នៅក្នុង tick មួយយូរពេក។</p>"
				+ "<p>សូមពិនិត្យ <b>thread dump</b> នៅក្នុង log ដើម្បីស្វែងរកមូលហេតុ។</p>"
				+ "<p>ការវិភាគ <b>Stack Trace</b> អាចជួយរកប្រភពបញ្ហា។</p>"
				+ "<p>ប៊ូតុង <b>Ver en log</b> នឹងបង្ហាញម៉ូដដែលអាចជាមូលហេតុជាពណ៌ក្រហម "
				+ "រួមទាំង entries ដែលមាន <code>$modid$</code> ដែលជាញឹកញាប់បង្ហាញប្រភពបញ្ហា។ "
				+ "សម្រាប់ការត្រួតពិនិត្យពេលវេលាពិត សូមប្រើ CPU sampler ក្នុង VisualVM។ "
				+ "ត្រូវប្រាកដថាកុំព្យូទ័រ ឬម៉ាស៊ីនបម្រើរបស់អ្នកមានសមត្ថភាពគ្រប់គ្រាន់សម្រាប់ម៉ូដដែលអ្នកប្រើ។</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Tick ម៉ាស៊ីនបម្រើយូរ";
	}

	@Override
	public String tituloLFPDPPP() {
		return "ច្បាប់ការពារទិន្នន័យផ្ទាល់ខ្លួន";
	}

	@Override
	public String aceptarPermanentemente() {
		return "យល់ព្រមជាអចិន្រ្តៃយ៍";
	}

	public String actaProteccionIdiomaCultural() {
		return "ច្បាប់ការពារភាសាវប្បធម៌ទីក្រុងព្យុងយ៉ាង";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "ការបកប្រែកូរ៉េមានពាក្យ slang ដែលមិនគួរប្រើ។ "
				+ "ការប្រើភាសាបរទេស ជាពិសេសពីភាគខាងត្បូង ត្រូវបានហាមឃាត់យ៉ាងតឹងរ៉ឹង។";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "សម្រាប់ព័ត៌មានបន្ថែម សូមមើលឯកសារផ្លូវការ៖ "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>ឯកសារ</a>";
	}

	public String leerLeyCompleta() {
		return "អានច្បាប់ពេញលេញ";
	}

	public String errorAbriendoEnlace() {
		return "កំហុសក្នុងការបើកតំណ";
	}

	@Override
	public String canarioTitulo() {
		return "Canario នៃការបញ្ជារតុលាការ";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — ម៉ូនីទ័រការតាមដាន";
	}

	@Override
	public String revisar() {
		return "ពិនិត្យឡើងវិញ";
	}

	@Override
	public String cerrar() {
		return "បិទ";
	}

	@Override
	public String canarioTodoSeguro() {
		return "សេវាកម្មទាំងអស់រាយការណ៍ថាសុវត្ថិភាព។";
	}

	@Override
	public String canarioComprometido(int c) {
		return "ការជូនដំណឹង: " + c + " សេវាកម្មរាយការណ៍ថាមិនមានសុវត្ថិភាព។";
	}

	@Override
	public String colorAlerta() {
		return "ពណ៌ការជូនដំណឹង";
	}

	public String opcionesMunidiales() {
		return "ជម្រើសសកល";
	}

	public String consentimientoLFPDPPP() {
		return "ការយល់ព្រម LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "អនុញ្ញាតការផ្ទេរ token ចូលប្រើក្នុង Handoff សម្រាប់ ReLauncher (មិនណែនាំ)";
	}

	public String consolaDesarrollo() {
		return "កុងសូលអភិវឌ្ឍន៍";
	}

	public String mundial() {
		return "សកល";
	}

	public String ningun() {
		return "គ្មាន";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "កុងសូលអ្នកអភិវឌ្ឍន៍";
	}

	public String bajar() {
		return "ទាញចុះ";
	}

	public String logsSoporte() {
		return "Logs សម្រាប់ការគាំទ្រ";
	}

	public String detenerProceso() {
		return "បញ្ឈប់ដំណើរការ";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "ចម្លងការជ្រើសរើស";
	}

	public String seleccionarTodo() {
		return "ជ្រើសរើសទាំងអស់";
	}

	public String copiarTodo() {
		return "ចម្លងទាំងអស់";
	}

	public String guardarTodoComoArchivo() {
		return "រក្សាទុកទាំងអស់ជាឯកសារ";
	}

	public String obtenerEnlaceSoporte() {
		return "ទទួលបានតំណគាំទ្រ";
	}

	public String borrarTodo() {
		return "លុបទាំងអស់";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "ពណ៌ផ្ទៃខាងក្រោយកុងសូល";
	}

	public String colorTextoConsola() {
		return "ពណ៌អត្ថបទកុងសូល";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "ការយល់ព្រមត្រូវបានបញ្ជាក់។\nមុខងារចែករំលែក logs នឹងត្រូវអនុវត្តនៅទីនេះ។";
	}

	@Override
	public String usarSakuraOriginal() {
		return "ប្រើរូបភាព Sakura Riddle ដើម";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "“warrant canary” គឺជាវិធីសាស្ត្របង្ហាញភាពថ្លៃថ្នូរ។\n\n"
				+ "បើសារនេះនៅតែមាន និងសេវាកម្មទាំងអស់ត្រូវបានបង្ហាញថាសុវត្ថិភាព "
				+ "វាមានន័យថាគម្រោងនេះមិនបានទទួលការបញ្ជាសម្ងាត់ "
				+ "ការស្នើសុំត្រួតពិនិត្យ ឬការទាមទារផ្លូវច្បាប់ណាមួយទេ។\n\n"
				+ "បើ canary ណាមួយបាត់ ឬបង្ហាញថាមិនសុវត្ថិភាព " + "វាមានន័យថាមានការផ្លាស់ប្តូរផ្លូវច្បាប់។\n\n"
				+ "ផ្ទាំងនេះពិនិត្យ canary ទាំងអស់នៅក្នុងប្រព័ន្ធ និងបង្ហាញស្ថានភាពបច្ចុប្បន្ន។\n\n"
				+ "ចុច \"ពិនិត្យឡើងវិញ\" ដើម្បីធ្វើបច្ចុប្បន្នភាពស្ថានភាព។";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "តើអ្នកចង់កំណត់ជម្រើសទាំងអស់ត្រឡប់ទៅលំនាំដើមទេ?";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "គ្មានជម្រើស";
	}

	@Override
	public String seleccionaColor() {
		return "ជ្រើសរើសពណ៌";
	}

	@Override
	public String botonMostrarGUI() {
		return "បង្ហាញ GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "រក្សាទុកទាំងអស់";
	}

	@Override
	public String botonRestablecerTodo() {
		return "កំណត់ឡើងវិញទាំងអស់";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms មិនបានផ្ទុក";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុសក្នុងការចូលប្រើ API របស់ LuckPerms។</b>"
				+ "<p>សារនេះបង្ហាញថា <b>LuckPerms មិនទាន់បានផ្ទុក</b> នៅពេលដែល plugin មួយផ្សេងទៀតព្យាយាមប្រើវា។</p>"
				+ "<p><b>មូលហេតុអាចមាន:</b></p><ul>"
				+ "<li><b>LuckPerms មិនបានដំឡើង</b> ឬ <b>បរាជ័យពេលចាប់ផ្តើម</b>។</li>"
				+ "<li>Plugin ផ្សេងកំពុងព្យាយាមចូលប្រើ LuckPerms លឿនពេក ឬមិនត្រឹមត្រូវ។</li>" + "</ul>"
				+ "<p>សូមពិនិត្យ <b>កុងសូល</b> ដោយប្រើតំណ ដើម្បីស្វែងរក plugin ដែលហៅ LuckPerms និងពិនិត្យភាពឆបគ្នា។</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Shaderpack របស់ Iris មិនបានផ្ទុក";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "មិនស្គាល់" : shaderpack;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុសក្នុងការផ្ទុក shaderpack ជាមួយ Iris/Oculus។</b>" + "<p><b>Shaderpack ដែលមានបញ្ហា:</b> "
				+ nombre + "</p>" + "<p>Minecraft មិនអាចបើកឯកសារ shaderpack បាន (FileSystemNotFoundException)។</p>"
				+ "<p><b>ដំណោះស្រាយអាចធ្វើបាន:</b></p><ul>"
				+ "<li>ពិនិត្យថា shaderpack ត្រូវបានដំឡើងត្រឹមត្រូវនៅក្នុងថត <b>shaderpacks</b>។</li>"
				+ "<li>ទាញយក shaderpack ម្តងទៀត ព្រោះឯកសារអាចខូច។</li>"
				+ "<li>បើបញ្ហានៅតែមាន សូមលុបឯកសារ <b>config/iris.properties</b> ដើម្បីកំណត់ Iris ឡើងវិញ។</li>" + "</ul>"
				+ "<p>បន្ទាប់ពីធ្វើការកែប្រែ សូមបើកហ្គេមឡើងវិញ។</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "មិនអាចសរសេរឯកសារកំណត់បាន";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "មិនស្គាល់" : ruta;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានកើតមានកំហុសក្នុងការរក្សាទុកឯកសារកំណត់។</b>" + "<p><b>ឯកសារដែលមានបញ្ហា:</b> " + archivo + "</p>"
				+ "<p>Minecraft មិនអាចសរសេរឯកសារដោយប្រើការសរសេរប្រភេទ atomic (REPLACE_ATOMIC) បាន។</p>"
				+ "<p><b>ជាធម្មតាកើតឡើងដោយសារ:</b></p><ul>"
				+ "<li>សិទ្ធិ (permissions) មិនត្រឹមត្រូវនៅក្នុងថត ឬឯកសារ។</li>"
				+ "<li>ឯកសារត្រូវបានកំណត់ជា read-only។</li>"
				+ "<li>កម្មវិធីផ្សេង (antivirus, backup, editor) កំពុងប្រើឯកសារ។</li>" + "</ul>"
				+ "<p><b>ការណែនាំ:</b></p><ul>" + "<li>ពិនិត្យសិទ្ធិសរសេរនៅក្នុងថត។</li>"
				+ "<li>ដកលក្ខណៈ read-only ចេញពីឯកសារ។</li>" + "<li>បិទកម្មវិធីដែលអាចកំពុងប្រើឯកសារ។</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "បដិសេធការចូលប្រើពេលបង្កើតចម្លងបម្រុង";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "មិនស្គាល់" : origen;
		String dst = backup == null || backup.isEmpty() ? "មិនស្គាល់" : backup;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានកើតមានកំហុសសិទ្ធិពេលបង្កើតចម្លងបម្រុងឯកសារកំណត់។</b>" + "<p><b>ឯកសារដើម:</b> " + src + "</p>"
				+ "<p><b>ឯកសារបម្រុង:</b> " + dst + "</p>"
				+ "<p>ប្រព័ន្ធប្រតិបត្តិការបានទប់ស្កាត់ការចូលប្រើនៅពេលរក្សាទុកឯកសារ។</p>"
				+ "<p><b>ជាធម្មតាកើតឡើងដោយសារ:</b></p><ul>" + "<li>សិទ្ធិមិនគ្រប់គ្រាន់នៅក្នុងថត។</li>"
				+ "<li>ឯកសារត្រូវបានកំណត់ជា read-only។</li>" + "<li>កម្មវិធីផ្សេងកំពុងប្រើឯកសារ។</li>" + "</ul>"
				+ "<p><b>ការណែនាំ:</b></p><ul>" + "<li>ពិនិត្យសិទ្ធិនៅក្នុងថត <b>config</b>។</li>"
				+ "<li>បិទកម្មវិធីដែលអាចកំពុងប្រើឯកសារ។</li>"
				+ "<li>សាកល្បងដំណើរការ launcher ឬ Minecraft ជា administrator។</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		return "បើកកុងសូល";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>ឧបករណ៍ពិនិត្យកំហុស</b><br><br>"
				+ "នៅទីនេះ អ្នកអាចបើកមុខងារកម្រិតខ្ពស់សម្រាប់វិភាគ CrashDetector និងហ្គេមរបស់អ្នក។<br><br>"
				+ "យើងណែនាំឱ្យបើកកុងសូលអភិវឌ្ឍន៍ ដើម្បីទទួលបានព័ត៌មានលម្អិត និងការវិភាគ។<br><br>"
				+ "ប្រសិនបើអ្នកត្រូវសាកល្បងម៉ាស៊ីនបម្រើ multiplayer អាចត្រូវអនុញ្ញាតការផ្ទេរ token ចូលប្រើ។ "
				+ "វាមិនត្រូវបានណែនាំនៅក្នុងករណីទូទៅ។<br><br>"
				+ "សេចក្ដីណែនាំពេញលេញ: <a href='https://example.com'>តំណ</a>";
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "រកឃើញភាពមិនឆបគ្នារវាង Simple Clouds និង shaders។</b>"
				+ "<p>Simple Clouds មិនឆបគ្នាជាមួយ mods ស្រមោល (Iris/Oculus) នៅពេលមាន Distant Horizons។</p>"
				+ "<p><b>ជម្រើសណែនាំ:</b></p><ul>" + "<li>លុប <b>Simple Clouds</b> ប្រសិនបើអ្នកចង់ប្រើ shaders។</li>"
				+ "<li>ឬលុប <b>Iris/Oculus</b> ប្រសិនបើអ្នកចង់រក្សា Simple Clouds។</li>" + "</ul>"
				+ "<p>ការកំណត់នេះមកពីម៉ូដ និងមិនអាចដោះស្រាយដោយគ្មានកែ code។</p>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "ភាពមិនឆបគ្នា: Simple Clouds និង Shaders";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "ពណ៌ប៊ូតុងផ្នែកខាងជ្រុង";
	}

	@Override
	public String profilerTitulo() {
		return "Profiler";
	}

	@Override
	public String profilerDescripcion() {
		return "ឧបករណ៍វិភាគប្រសិទ្ធភាពដោយប្រើការវាស់វែង និងសំណាក។";
	}

	@Override
	public String profilerIniciar() {
		return "ចាប់ផ្តើម";
	}

	@Override
	public String profilerDetener() {
		return "បញ្ឈប់";
	}

	@Override
	public String profilerLimpiar() {
		return "សម្អាត";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "Profiler បានចាប់ផ្តើម។";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "Profiler បានបញ្ឈប់។";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "បានទទួលគំរូពី thread: " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "ការយកគំរូ stack ជាប្រចាំ ដើម្បីរក bottleneck និងការចាក់សោ។";
	}

	@Override
	public String entrarAlJuego() {
		return "ចូលទៅក្នុងហ្គេម";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "រកឃើញកំហុសនៅក្នុងផ្លូវប្រព័ន្ធ។</b>"
				+ "<p>Minecraft មិនអាចចាប់ផ្តើមបាន ដោយសារតួអក្សរមិនត្រឹមត្រូវក្នុងឈ្មោះថត។</p>"
				+ "<p>ប្រព័ន្ធបានរកឃើញតួអក្សរមិនត្រឹមត្រូវ (ឧទាហរណ៍ ':' ឬសញ្ញាពិសេសផ្សេងៗ)។</p>"
				+ "<p><b>ដំណោះស្រាយណែនាំ:</b></p><ul>" + "<li>ប្តូរឈ្មោះថត instance ឬ profile។</li>"
				+ "<li>ប្រើតែតួអក្សរ ASCII (A-Z, a-z, 0-9)។</li>" + "<li>កុំប្រើសញ្ញាពិសេស អក្សរមានសំឡេង ឬ emoji។</li>"
				+ "</ul>" + "<p>ឧទាហរណ៍ត្រឹមត្រូវ: <b>MiInstancia1</b></p>"
				+ "<p>ឧទាហរណ៍មិនត្រឹមត្រូវ: <b>Instancia🔥</b> ឬ <b>Instancia:Mod</b></p>";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "ផ្លូវមិនត្រឹមត្រូវ: តួអក្សរមិនអនុញ្ញាត";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "រកឃើញការបរាជ័យក្នុង shaders របស់ Twilight Forest ជាមួយ GPU Intel។</b>"
				+ "<p>កំហុសនេះពាក់ព័ន្ធនឹង driver ក្រាហ្វិក Intel ពេលផ្ទុក shaders។</p>"
				+ "<p>បញ្ហានេះកើតឡើងនៅក្នុង driver (igxelpicd64) មិនមែនពី mod ឬ Minecraft ទេ។</p>"
				+ "<p><b>ដំណោះស្រាយណែនាំ:</b></p><ul>" + "<li>ធ្វើបច្ចុប្បន្នភាព driver Intel ទៅកំណែថ្មីបំផុត។</li>"
				+ "<li>សាកល្បងកំណែ 31.0.101.8331 ឬ 31.0.101.8247 WHQL។</li>" + "</ul>" + "<p>តាមដានបញ្ហា:</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>ចំណាំ:</b> GPU Intel ចាស់ខ្លះអាចមិនទទួលបានការអាប់ដេត។</p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "Crash: Twilight Forest + Intel Drivers";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "Forge: មិនអាចផ្ទុក language provider";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "មិនស្គាល់" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Forge មិនអាចផ្ទុក language provider មួយបាន។</b>"
				+ "<p>កំហុសកើតឡើងពេលចាប់ផ្តើម IModLanguageProvider។</p>" + "<p><b>Provider:</b> " + providerTexto
				+ "</p>" + "<p>ជាធម្មតាកើតឡើងពេល:</p><ul>" + "<li>ខ្វះ dependency (ឧ. Kotlin for Forge)</li>"
				+ "<li>mod មិនឆបគ្នាជាមួយ Forge</li>" + "<li>ឯកសារម៉ូដខូច</li>" + "</ul>"
				+ "<p><b>ដំណោះស្រាយ:</b></p><ul>" + "<li>ដំឡើងម៉ូដឡើងវិញ</li>" + "<li>ពិនិត្យ dependencies</li>"
				+ "<li>ប្រើកំណែឆបគ្នា</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "Crash: Lets Do Compat (RecipeManager intercept)";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "រកឃើញ crash នៅ Lets Do Compat (interceptApply)।</b>"
				+ "<p>កំហុសកើតឡើងនៅក្នុង RecipeManager.interceptApply។</p>" + "<p>ជាធម្មតាមានន័យថា:</p><ul>"
				+ "<li>មិនឆបគ្នាជាមួយម៉ូដផ្សេង</li>" + "<li>កំណែមិនត្រឹមត្រូវ</li>" + "<li>conflict mixin/coremod</li>"
				+ "</ul>" + "<p><b>ដំណោះស្រាយ:</b></p><ul>" + "<li>លុប Lets Do Compat ជាបណ្ដោះអាសន្ន</li>" + "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI: បរាជ័យ Item Group";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "មិនស្គាល់" : String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "JEI រកឃើញបញ្ហាពេលបង្កើត Item Group។</b>" + "<p>plugin មួយឬច្រើនបង្កកំហុស។</p>"
				+ "<p><b>Plugins:</b> " + listaPlugins + "</p>" + "<p>ជាធម្មតាកើតឡើងពេល:</p><ul>"
				+ "<li>plugin មិនត្រឹមត្រូវ</li>" + "<li>មិនឆបគ្នាជាមួយ JEI</li>" + "<li>register មិនត្រឹមត្រូវ</li>"
				+ "</ul>" + "<p><b>ដំណោះស្រាយ:</b></p><ul>" + "<li>អាប់ដេត JEI និងម៉ូដ</li>"
				+ "<li>លុប plugin ដើម្បីតេស្ត</li>" + "<li>រាយការណ៍ទៅអ្នកអភិវឌ្ឍន៍</li>" + "</ul>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "កំណែមិនត្រឹមត្រូវ (SemVer)";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "មិនស្គាល់" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "មិនអាចរកឃើញម៉ូដ" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "រកឃើញកំណែមិនត្រឹមត្រូវនៃម៉ូដ។</b>" + "<p>កំណែ <b>" + v
				+ "</b> មិនត្រូវតាមទ្រង់ទ្រាយ SemVer ត្រឹមត្រូវ។</p>"
				+ "<p>កំហុសបង្ហាញថា pre-release ទទេ (បញ្ចប់ដោយ '+')។</p>" + "<p><b>ម៉ូដដែលពាក់ព័ន្ធ:</b><br>" + u
				+ "</p>" + "<p><b>ដំណោះស្រាយណែនាំ:</b></p><ul>" + "<li>កែប្រែឯកសារម៉ូដ និងកំណែឲ្យត្រឹមត្រូវ។</li>"
				+ "<li>លុប '+' ចុងក្រោយ ប្រសិនបើគ្មាន metadata។</li>" + "<li>ធ្វើបច្ចុប្បន្នភាពម៉ូដទៅកំណែថ្មី។</li>"
				+ "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS: ការចូលប្រើខុសច្បាប់រវាងម៉ូឌុល";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "រកឃើញការចូលប្រើខុសច្បាប់រវាងម៉ូឌុល (JPMS)។</b>"
				+ "<p>ប្រព័ន្ធម៉ូឌុល Java (JPMS) បានទប់ស្កាត់ការចូលប្រើរវាង classes។</p>" + "<p><b>Class ប្រភព:</b><br>"
				+ claseOrigen + " (ម៉ូឌុល: " + moduloOrigen + ")</p>" + "<p><b>Class គោលដៅ:</b><br>" + claseDestino
				+ " (ម៉ូឌុល: " + moduloDestino + ")</p>"
				+ "<p>កំហុសនេះកើតឡើងពេលម៉ូដមិនបានកំណត់ exports ឬ opens ត្រឹមត្រូវក្នុង module-info.java។</p>"
				+ "<p><b>មូលហេតុអាចមាន:</b></p><ul>" + "<li>ម៉ូឌុលមិនបាន export package ត្រឹមត្រូវ</li>"
				+ "<li>ខ្វះ directive <b>opens</b></li>" + "<li>ម៉ូដមិនបានកំណត់សម្រាប់ JPMS ត្រឹមត្រូវ</li>" + "</ul>"
				+ "<p>បញ្ហានេះត្រូវតែជួសជុលដោយអ្នកអភិវឌ្ឍន៍ម៉ូដ។</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin: class នៅក្នុង package មិនត្រឹមត្រូវ";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Class ត្រូវបានដាក់នៅក្នុង package Mixin មិនត្រឹមត្រូវ។</b>"
				+ "<p>Class ធម្មតាត្រូវបានដាក់ក្នុង package mixin។</p>" + "<p><b>Class:</b><br>" + clase + "</p>"
				+ "<p><b>Package mixin:</b><br>" + paquete + "</p>" + "<p><b>ឯកសារ mixins:</b><br>" + archivoMixin
				+ "</p>" + "<p>Class ធម្មតាមិនគួរនៅក្នុង package mixin ទេ។</p>"
				+ "<p><b>ដំណោះស្រាយ:</b> ផ្លាស់ទី class ឬកែ mixins.json។</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "រកឃើញបញ្ហាជាមួយ driver GPU Matrox។</b>" + "<p>កំហុសកើតឡើងក្នុង driver Matrox។</p>"
				+ "<p>GPU Matrox មិនគាំទ្រ OpenGL ទំនើប។</p>" + "<p><b>ដំណោះស្រាយ:</b></p><ul>"
				+ "<li>ធ្វើបច្ចុប្បន្នភាព driver</li>" + "<li>ប្រើ driver ផ្លូវការ</li>"
				+ "<li>ប្រើ GPU ដែលគាំទ្រ OpenGL 3.2+</li>" + "</ul>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod: GPU មិនគាំទ្រ";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod មិនអាចរកឃើញ GPU ដែលគាំទ្រ។</b>" + "<p>ម៉ូដ <b>VulkanMod</b> មិនអាចប្រើ Vulkan បាន។</p>"
				+ "<p>មូលហេតុអាចមាន:</p><ul>" + "<li>GPU មិនគាំទ្រ Vulkan</li>" + "<li>driver ចាស់</li>"
				+ "<li>ប្រើ GPU ខុស</li>" + "</ul>" + "<p><b>ដំណោះស្រាយ:</b></p><ul>" + "<li>អាប់ដេត driver</li>"
				+ "<li>ពិនិត្យ Vulkan support</li>" + "<li>ប្រើ GPU dedicated</li>"
				+ "<li>លុប VulkanMod ប្រសិនបើមិនគាំទ្រ</li>" + "</ul>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "RenderType មិនត្រឹមត្រូវ";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		String base = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ម៉ូដមួយបានប្រើ outline ជាមួយ RenderType មិនត្រឹមត្រូវ។</b>"
				+ "<p><code>Can't render an outline for this rendertype!</code></p>";

		if (enchantDetectado) {
			base += "<p>បានរកឃើញ mod enchant-outline។</p>" + "<p><b>ដំណោះស្រាយ:</b> លុប ឬធ្វើបច្ចុប្បន្នភាពម៉ូដ។</p>";
		} else {
			base += "<p>ពាក់ព័ន្ធនឹងម៉ូដ rendering។</p>"
					+ "<p><b>ដំណោះស្រាយ:</b> ធ្វើបច្ចុប្បន្នភាព ឬបិទម៉ូដម្តងមួយ។</p>";
		}

		return base;
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – Inventory ទទេ";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG បានប្រើ DimensionalInventory ទទេ។</b>"
				+ "<p><code>Cannot invoke DimensionalInventory.saveInventory(...)</code></p>"
				+ "<p><b>ដំណោះស្រាយ:</b></p><ul>" + "<li>កំណត់ saferVetheanInventory=true</li>"
				+ "<li>រក្សាទុក ហើយចាប់ផ្តើមហ្គេមឡើងវិញ</li>" + "</ul>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "បញ្ហា Render Pass";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "រកឃើញបញ្ហាក្នុងការរេនដឺ។</b>" + "<p><code>Close the existing render pass...</code></p>"
				+ "<p>ពាក់ព័ន្ធនឹងម៉ូដ rendering។</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "បញ្ហាផ្ទៃក្នុង Feather Client";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុសផ្ទៃក្នុងនៅក្នុង Feather Client។</b>" + "<p>ហ្គេមបានបង្ហាញ:</p>"
				+ "<code>NoClassDefFoundError: feather/lib/sentry/Sentry</code>"
				+ "<p>កំហុសនេះបង្កឡើងដោយ Feather Client។</p>" + "<p>Feather មិនត្រូវបានណែនាំ ព្រោះ:</p>" + "<ul>"
				+ "<li>ប្រើកំណែដែលបានកែប្រែ និងមានសិទ្ធិផ្តាច់មុខនៃម៉ូដពេញនិយម។</li>"
				+ "<li>បំផ្លាញភាពឆបគ្នាជាមួយ Fabric ស្តង់ដារ។</li>" + "<li>ដំណើរការដូចជា modpack ដែលបានកំណត់ជាមុន។</li>"
				+ "<li>ធ្វើឱ្យមានជម្លោះជាមួយ Sodium និងម៉ូដប្រសិទ្ធភាពផ្សេងៗ។</li>" + "</ul>"
				+ "<p>សូមប្រើ Fabric ស្តង់ដារជំនួស Feather។</p>";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "ជម្លោះ Iris + Flywheel (Create 6)";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញជម្លោះរវាង Iris និង Flywheel ក្នុង Create 6។</b>" + "<p>ហ្គេមបានបង្ហាញ:</p>"
				+ "<code>NoSuchFieldError: TESSELATION_SHADERS</code>"
				+ "<p>បានរកឃើញការយោង <code>$irisflw$</code> ដែលបង្ហាញពីជម្លោះ។</p>"
				+ "<p>Iris Flywheel 2.0 សម្រាប់ Create 6 គាំទ្រផ្លូវការតែ NeoForge ប៉ុណ្ណោះ។</p>"
				+ "<p>ប្រសិនបើប្រើ Forge ឬ Fabric វាអាចបង្កកំហុសនេះ។</p>";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "រកមិនឃើញម៉ូដែល GeckoLib";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ម៉ូដមួយមិនអាចរកម៉ូដែល GeckoLib បាន។</b>" + "<p>ម៉ូដែល:</p><code>" + modelo + "</code>"
				+ "<p>កំហុសនេះកើតឡើងពេលឯកសារ <code>.geo.json</code> មិនមាន ឬកំណត់ខុស។</p>" + "<p>មូលហេតុអាចមាន:</p><ul>"
				+ "<li>ម៉ូដែលត្រូវបានលុប ប៉ុន្តែត្រូវបានយោង។</li>" + "<li>ផ្លូវឯកសារខុស។</li>"
				+ "<li>ឯកសារបាត់នៅក្នុង JAR។</li>" + "<li>កំណែមិនឆបគ្នា។</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – គ្មានអានីមេសិន";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon ព្យាយាមចាក់អានីមេសិនដែលមិនមាន។</b>" + "<p>អានីមេសិន:</p><code>" + animacion + "</code>"
				+ "<p>ក្រុម:</p><code>" + grupo + "</code>" + "<p>កំហុសនេះកើតឡើងពេល:</p><ul>"
				+ "<li>ប្រើកំណែមិនឆបគ្នា។</li>" + "<li>Addon មិនត្រូវនឹងកំណែដែលបានដំឡើង។</li>"
				+ "<li>បាត់ធនធាន ឬអានីមេសិន។</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "កំហុសផ្ទៃក្នុង Lunar Client";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុសផ្ទៃក្នុង Lunar Client។</b>"
				+ "<p>កំហុសនេះមកពី client ខ្លួនវា មិនមែនពីហ្គេម ឬម៉ូដទេ។</p>"
				+ "<p>Lunar Client ប្រើការកែប្រែផ្ទាល់ខ្លួន ដែលអាចបង្កភាពមិនឆបគ្នា។</p>"
				+ "<p>សូមសាកល្បងប្រើ Minecraft ស្តង់ដារ។</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "ការចូលប្រើមិនអនុញ្ញាត";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ម៉ូដមួយបានព្យាយាមចូលប្រើ method ឬ field ដែលត្រូវបានការពារ។</b>" + "<p>Class:</p><code>" + clase
				+ "</code>" + "<p>Member:</p><code>" + miembro + "</code>" + "<p>មូលហេតុអាចមាន:</p><ul>"
				+ "<li>ម៉ូដត្រូវបានបង្កើតសម្រាប់កំណែផ្សេង។</li>" + "<li>Mappings មិនឆបគ្នា។</li>" + "<li>ម៉ូដចាស់។</li>"
				+ "<li>ប្រើ loader ខុស។</li>" + "</ul>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "កំហុសក្នុងការផ្ទុក datapack/resourcepack";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "datapack ឬ resourcepack មិនអាចផ្ទុកបាន។</b>" + "<p>ឯកសារ:</p><code>" + archivo + "</code>"
				+ "<p>Pack:</p><code>" + pack + "</code>" + "<p>ហ្គេមមិនអាច parse ឯកសារនេះបាន។</p>"
				+ "<p>មូលហេតុអាចមាន:</p><ul>" + "<li>JSON ខូច។</li>" + "<li>កំណែមិនឆបគ្នា។</li>" + "<li>Pack ចាស់។</li>"
				+ "<li>ជម្លោះរវាង datapacks។</li>" + "</ul>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "កំហុសក្នុងការកុំពൈល shader";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ការកុំពൈល shader បានបរាជ័យ។</b>" + "<p>ហ្គេមមិនអាចកុំពൈល shader មួយបាន។</p>"
				+ "<p>ជាញឹកញាប់ពាក់ព័ន្ធនឹង Sodium, Iris ឬ shaderpack មិនឆបគ្នា។</p>" + "<p>ដំណោះស្រាយ:</p><ul>"
				+ "<li>សាកល្បង shader ផ្សេង។</li>" + "<li>បិទ shader ជាបណ្ដោះអាសន្ន។</li>"
				+ "<li>ធ្វើបច្ចុប្បន្នភាព driver GPU។</li>" + "<li>បើនៅតែមានបញ្ហា សាកល្បងបិទ Sodium។</li>" + "</ul>";
	}

	public String nombreErrorCreacionModelo() {
		return "កំហុសក្នុងការបង្កើត ឬផ្ទុកម៉ូដែល";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>មានកំហុសនៅពេលបង្កើត ឬផ្ទុកម៉ូដែល Minecraft។</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>ម៉ូដែល: <code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>មូលហេតុអាចមាន:</p>");
		sb.append("<ul>");
		sb.append("<li>ម៉ូដែលកំណត់ខុស។</li>");
		sb.append("<li>JSON ខូច ឬមិនពេញលេញ។</li>");
		sb.append("<li>ជម្លោះរវាងម៉ូដ។</li>");
		sb.append("<li>resource pack ឬ datapack មិនឆបគ្នា។</li>");
		sb.append("</ul>");
		sb.append("<p>ពិនិត្យម៉ូដ ឬ resource ដែលផ្តល់ម៉ូដែលនេះ។</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>មូលហេតុអាចមាន:</b></p>");
		sb.append("<p>បានរកឃើញ mod <b>Cooler Animations</b> នៅក្នុង logs។</p>");
		sb.append("<p>ម៉ូដនេះអាចបង្កកំហុសក្នុងម៉ូដែល និងអានីមេសិន។</p>");
		sb.append("<p>សាកល្បងបិទវា ដើម្បីពិនិត្យបញ្ហា។</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "បញ្ហាជាមួយ Starlight";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុសដែលពាក់ព័ន្ធនឹង Starlight។</b>"
				+ "<p>កំហុសបានកើតឡើងក្នុង <code>BlockStarLightEngine.initNibble</code>។</p>"
				+ "<p>នេះបង្ហាញពីបញ្ហានៅក្នុងម៉ាស៊ីនភ្លើងពន្លឺរបស់ម៉ូដ <b>Starlight</b>។</p>"
				+ "<p>Starlight គឺជាម៉ូដដែលកែប្រែប្រព័ន្ធភ្លើងពន្លឺរបស់ Minecraft ទាំងស្រុង ហើយអាចបង្កបញ្ហានានានៅក្នុងបរិយាកាសម៉ូដខ្លះ។</p>"
				+ "<p>នេះគឺជាមួយក្នុងចំណោមកំហុសជាច្រើនដែលទាក់ទងនឹង Starlight។</p>"
				+ "<p>បើបញ្ហានៅតែបន្ត សាកល្បងដំណើរការហ្គេមដោយមិនប្រើ Starlight។</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "បញ្ហាជាមួយ AAAParticles / Effekseer";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញ crash នៃ native ដែលពាក់ព័ន្ធនឹង Effekseer។</b>"
				+ "<p>កំហុសបានកើតឡើងក្នុងបណ្ណាល័យ native <code>EffekseerNativeForJava</code>។</p>"
				+ "<p>បណ្ណាល័យនេះត្រូវបានប្រើដោយម៉ូដ <b>AAAParticles</b>។</p>"
				+ "<p>crash នៅក្នុង native ជាទូទៅបង្ហាញពីបញ្ហានៅក្នុងម៉ូដ ឬ dependency របស់វា។</p>"
				+ "<p>បើបញ្ហានៅតែបន្ត សាកល្បងដំណើរការហ្គេមដោយមិនប្រើ AAAParticles។</p>";
	}

	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញ crash នៃ JVM (Java Virtual Machine)។</b>"
				+ "<p>កំហុសប្រភេទនេះកើតឡើងនៅក្នុង JVM ខ្លួនវា (ឧ. <code>jvm.dll</code>, <code>libjvm.so</code>) មិនមែនតែងតែដោយសារម៉ូដទេ។</p>"
				+ "<p>ភាគច្រើនវាបណ្តាលមកពីកំណែ JVM ខូច ចាស់ ឬមិនមានការគាំទ្រ។</p>"
				+ "<p>វាជារឿងធម្មតា ប្រសិនបើអ្នកប្រើ build មិនផ្លូវការ ឬចាស់។</p>"
				+ "<p><b>សូមប្រើ JVM ដែលទុកចិត្តបាន:</b></p><ul>" + "<li><b>Red Hat OpenJDK</b></li>"
				+ "<li><b>OpenLogic OpenJDK</b></li>" + "<li><b>Azul Zulu</b></li>" + "<li><b>Oracle JDK</b></li>"
				+ "</ul>" + "<p>ជៀសវាង build មិនស្គាល់ ឬចាស់ពេក។</p>"
				+ "<p>អ្នកអាចកំណត់ JVM ផ្ទាល់ខ្លួនក្នុង launcher ដោយមិនប៉ះពាល់ប្រព័ន្ធ។</p>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "ជម្លោះរវាង Paranoia និង C2ME";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញជម្លោះរវាង Paranoia និង C2ME។</b>"
				+ "<p>កំហុសបង្ហាញថា <code>ThreadLocalRandom</code> ត្រូវបានប្រើពី thread ខុស។</p>"
				+ "<p>នេះកើតឡើងពេល Paranoia មិន thread-safe ខណៈ C2ME ប្រើ multithreading។</p>"
				+ "<p>ជាទូទៅកើតឡើងជាមួយម៉ូដ MCreator។</p>" + "<p>សាកល្បងបិទ Paranoia ឬ C2ME។</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "បាត់ថត assets របស់ Minecraft";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft មិនអាចរកឃើញថត assets។</b>" + "<p>ផ្លូវ assets មិនត្រឹមត្រូវ ឬឯកសារមិនមាន។</p>"
				+ "<p>ជាទូទៅកើតឡើងដោយការដំឡើងខុស ឬ launcher មិនត្រឹមត្រូវ។</p>"
				+ "<p>អាចកើតឡើងជាមួយ launcher មិនផ្លូវការ។</p>" + "<p>សាកល្បងដំឡើងឡើងវិញ ឬប្រើ launcher ផ្លូវការ។</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return "ដំឡើង " + dependencia + " កំណែ " + version + " ឬថ្មីជាងនេះ";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return "ជំនួស " + dependencia + " ដោយកំណែចន្លោះ " + min + " ដល់ " + max;
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "ម៉ូដ " + mod + " ត្រូវការ " + dependencia + " កំណែអប្បបរមា " + version;
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "ម៉ូដ " + mod + " ត្រូវការ " + dependencia + " កំណែ " + version;
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {
		return "ម៉ូដ " + mod + " ត្រូវការ " + dependencia + " ចន្លោះ " + min + " ដល់ " + max + " (បច្ចុប្បន្ន: "
				+ actual + ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "កំណែមិនឆបគ្នា Cupboard";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញ crash ដោយសារកំណែចាស់របស់ Cupboard។</b>"
				+ "<p>កំហុសកើតឡើងក្នុង <code>ClientConfigCompat.setupNeoforge</code>។</p>"
				+ "<p>នេះជាបញ្ហាដែលស្គាល់ក្នុងកំណែចាស់។</p>"
				+ "<p><b>ដំណោះស្រាយ:</b> ធ្វើបច្ចុប្បន្នភាពទៅកំណែ 3.5 ឬថ្មីជាងនេះ។</p>";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		return "វាកើតឡើងព្រោះ macOS មិនគាំទ្រ OpenGL ក្នុងកំណែថ្មីៗ។ "
				+ "ត្រូវប្រើ Minecraft ដែលគាំទ្រ Metal ឬប្រើ Linux សម្រាប់ Mac ដែលសមស្រប។";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "រកមិនឃើញអានីមេសិន GeckoLib";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ម៉ូដមួយមិនអាចផ្ទុកឯកសារអានីមេសិន GeckoLib បាន។</b>" + "<p>ឯកសារ:</p><code>" + archivo + "</code>"
				+ "<p>កំហុសនេះកើតឡើងពេលឯកសារ <code>.json</code> មិនមាន មានកំហុស syntax ឬផ្លូវមិនត្រឹមត្រូវ។</p>"
				+ "<p>មូលហេតុអាចមាន:</p><ul>" + "<li>ឯកសារត្រូវបានលុប ប៉ុន្តែត្រូវបានយោងនៅក្នុងកូដ។</li>"
				+ "<li>កំហុស syntax ក្នុង JSON។</li>" + "<li>ផ្លូវឯកសារខុស។</li>"
				+ "<li>ជម្លោះ dependency ឬកំណែមិនឆបគ្នា។</li>" + "</ul>";
	}

	@Override
	public String nombreAnimacionGeckoInexiste() {
		return "រកមិនឃើញអានីមេសិន GeckoLib";
	}

	@Override
	public String mensajeAnimacionGeckoInexiste(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ម៉ូដមួយមិនអាចរកឃើញឯកសារអានីមេសិន GeckoLib បាន។</b>" + "<p>ឯកសារ:</p><code>" + archivo + "</code>"
				+ "<p>កំហុសនេះកើតឡើងពេលអានីមេសិនមិនមាននៅក្នុងផ្លូវដែលបានកំណត់។</p>" + "<p>មូលហេតុអាចមាន:</p><ul>"
				+ "<li>ឯកសារ <code>.json</code> មិនមានក្នុង JAR។</li>" + "<li>កំហុសក្នុងការសរសេរផ្លូវ។</li>"
				+ "<li>ខុសអក្សរធំ/តូច (Linux vs Windows)।</li>"
				+ "<li>ម៉ូដ ឬ dependency ខូច ឬមិនទាន់ធ្វើបច្ចុប្បន្នភាព។</li>" + "</ul>";
	}

	@Override
	public String nombreRegistroDuplicadoObjeto() {
		return "ជម្លោះការចុះបញ្ជីទ្វេដង";
	}

	@Override
	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto) {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color
				+ "'>ជម្លោះធ្ងន់ធ្ងរ: វត្ថុត្រូវបានចុះបញ្ជីពីរដង។ ម៉ូដ </span>" + mod1 + "<span style='color:#" + color
				+ "'> និង </span>" + mod2 + "<span style='color:#" + color
				+ "'> កំពុងចុះបញ្ជីវត្ថុដូចគ្នា។ វត្ថុ៖ </span>" + objeto + "<br><br>";

		String instrucciones = "<span style='color:#" + color + "'>"
				+ "វាកើតឡើងពេលម៉ូដពីរបន្ថែមវត្ថុមានឈ្មោះដូចគ្នា ឬមានកំហុសក្នុងកូដ។<br><br>"
				+ "<b>ដំណោះស្រាយ:</b><br><ul>" + "<li>ពិនិត្យថាម៉ូដមួយជាកំណែថ្មីនៃមួយផ្សេងទៀតឬអត់។</li>"
				+ "<li>លុបម៉ូដមួយក្នុងចំណោមទាំងពីរ។</li>" + "<li>ផ្លាស់ប្តូរ ID ក្នុង config ប្រសិនបើអាចធ្វើបាន។</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreFalloFabricRenderingAPI() {
		return "បាត់ Fabric Rendering API";
	}

	@Override
	public String mensajeFalloFabricRenderingAPI() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color
				+ "'>ម៉ូដមួយបានបរាជ័យ ព្រោះ Fabric Rendering API មិនមាន។</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'>" + "<b>ដំណោះស្រាយ:</b><br>" + "<ul>"
				+ "<li>ធ្វើបច្ចុប្បន្នភាព Sodium ទៅ 0.6.0 ឬថ្មីជាងនេះ។</li>"
				+ "<li>ដំឡើង Fabric API ប្រសិនបើមិនទាន់មាន។</li>" + "<li>សម្រាប់កំណែចាស់ អាចត្រូវការ Indium។</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRestriccionesDependenciaNoCumplidas() {
		return "មិនបំពេញលក្ខខណ្ឌ dependency";
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos) {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>រកឃើញ </span>" + cantidad + "<span style='color:#"
				+ color + "'> បញ្ហា dependency។</span><br><br>";

		StringBuilder listaDetalle = new StringBuilder();
		if (conflictos != null && !conflictos.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color).append("'>ឯកសារដែលមានបញ្ហា:</span><br><ul>");
			for (String[] par : conflictos) {
				listaDetalle.append("<li><span style='color:#").append(color).append("'>ឯកសារ: </span>").append(par[1])
						.append("<br><span style='color:#").append(color).append("'>ត្រូវការ: </span>").append(par[0])
						.append("</li>");
			}
			listaDetalle.append("</ul><br>");
		}

		String instrucciones = "<span style='color:#" + color + "'>" + "វាកើតឡើងពេលម៉ូដត្រូវការកំណែខុសគ្នា។<br><br>"
				+ "<b>ដំណោះស្រាយ:</b><ul>" + "<li>ធ្វើបច្ចុប្បន្នភាព ឬលុបម៉ូដដែលមានបញ្ហា។</li>"
				+ "<li>អាចកែ mods.toml ដោយដៃ (ហានិភ័យខ្ពស់)។</li>" + "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod) {

		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>រកឃើញ </span>" + cantidad + "<span style='color:#"
				+ color + "'> បញ្ហា dependency។</span><br><br>";

		StringBuilder listaDetalle = new StringBuilder();
		if (conflictosPorMod != null && !conflictosPorMod.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color).append("'>ម៉ូដ និង dependency:</span><br><ul>");

			for (Map.Entry<String, List<String>> entry : conflictosPorMod.entrySet()) {
				listaDetalle.append("<li><b>").append(entry.getKey()).append("</b><ul>");
				for (String dep : entry.getValue()) {
					listaDetalle.append("<li>").append(dep).append("</li>");
				}
				listaDetalle.append("</ul></li>");
			}
			listaDetalle.append("</ul><br>");
		} else {
			listaDetalle.append("<span style='color:#").append(color).append("'>មិនអាចកំណត់ឯកសារ។</span><br>");
		}

		String instrucciones = "<span style='color:#" + color + "'>" + "បញ្ហានេះកើតឡើងដោយ JarInJar conflict។<br><br>"
				+ "<b>ដំណោះស្រាយ:</b><ul>" + "<li>ធ្វើបច្ចុប្បន្នភាពម៉ូដទាំងអស់។</li>"
				+ "<li>កែ .jar ប្រសិនបើចាំបាច់ (ហានិភ័យ)។</li>" + "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String nombreNeruinaOcultaAdvertencia() {
		return "Neruina រារាំងការវិភាគ";
	}

	@Override
	public String mensajeNeruinaOcultaAdvertencia() {
		String color = Config.obtenerInstancia().obtenerColorAdvertencia();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>ព្រមាន:</b> ម៉ូដ Neruina កំពុងលាក់កំហុសពិត។</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'>" + "<b>ដំណោះស្រាយ:</b><br>"
				+ "កំណត់ removeErroringEntities = true និង removeErroringBlockEntities = true<br>"
				+ "ឬលុប Neruina។</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreApothicAttributeSinDueño() {
		return "កំហុស Apothic Attributes";
	}

	@Override
	public String mensajeApothicAttributeSinDueño(boolean chestCavityDetectado) {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Apothic Attributes</b> បានរកឃើញជម្លោះ៖ <b>AttributeMap</b> ត្រូវបានកែប្រែដោយគ្មានម្ចាស់។</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "វាជាធម្មតាកើតឡើងពេលម៉ូដព្យាយាមកែប្រែ attributes (ជីវិត កម្លាំង ល្បឿន) នៅពេលមិនត្រឹមត្រូវ។</span><br><br>";

		String notaChestCavity = "";
		if (chestCavityDetectado) {
			notaChestCavity = "<span style='color:#" + color + "'>" + "<b>បានរកឃើញម៉ូដ Chest Cavity នៅក្នុង log។</b> "
					+ "ម៉ូដនេះជាមូលហេតុធម្មតានៃកំហុសនេះ។</span><br><br>";
		}

		String instrucciones = "<span style='color:#" + color + "'><b>ដំណោះស្រាយ:</b><br><ul>"
				+ "<li>ធ្វើបច្ចុប្បន្នភាព ឬលុប Chest Cavity ជាបណ្តោះអាសន្ន។</li>"
				+ "<li>បិទម៉ូដផ្សេងៗដែលកែប្រែ attributes។</li>" + "<li>ធ្វើបច្ចុប្បន្នភាព Apothic Attributes។</li>"
				+ "</ul></span>";

		return mensajeBase + notaChestCavity + instrucciones;
	}

	@Override
	public String nombreErrorPotBlockEntity() {
		return "កំហុស DecoratedPot (Cataclysm)";
	}

	@Override
	public String mensajeErrorPotBlockEntity() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "មានកំហុសមិនឆបគ្នាជាមួយ <b>DecoratedPotBlockEntity</b>។</span><br><br>" + "<span style='color:#"
				+ color + "'>" + "នេះជាបញ្ហាដែលស្គាល់ក្នុងកំណែ 1.19.2 នៃ <b>L_Enders_Cataclysm</b>។</span><br><br>";

		String solucion = "<span style='color:#" + color + "'><b>ដំណោះស្រាយ:</b><br>"
				+ "ដំឡើង <b>PotFix (Cataclysm Patch)</b> ដើម្បីជួសជុល។<br>"
				+ "<a href='https://www.curseforge.com/minecraft/mc-mods/potfix-cataclysm-patch'>ទាញយក</a>" + "</span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorPreloadingTricks() {
		return "កំហុស Preloading Tricks";
	}

	@Override
	public String mensajeErrorPreloadingTricks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "បានរកឃើញជម្លោះដោយ <b>Preloading Tricks</b>។</span><br><br>" + "<span style='color:#" + color + "'>"
				+ "កំហុស ClassCastException បង្ហាញថាម៉ូដកំពុងប្រើ Java modules មិនត្រឹមត្រូវ។</span><br><br>";

		String explicacion = "<span style='color:#" + color + "'>"
				+ "ម៉ូដនេះត្រូវបានរចនាសម្រាប់អ្នកអភិវឌ្ឍន៍ ហើយអាចបង្កអស្ថិរភាព។</span><br><br>" + "<span style='color:#"
				+ color + "'><b>ដំណោះស្រាយ:</b><ul>" + "<li>លុប Preloading Tricks។</li>"
				+ "<li>ប្រើតែបើចាំបាច់សម្រាប់ការអភិវឌ្ឍ។</li>" + "</ul></span>";

		return mensajeBase + explicacion;
	}

	@Override
	public String nombreErrorSimpleRadioLexiconfig() {
		return "ជម្លោះ Simple Radio / Lexiconfig";
	}

	@Override
	public String mensajeErrorSimpleRadioLexiconfig() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "បានរកឃើញជម្លោះរវាង <b>Simple Radio</b> និង <b>Lexiconfig</b>។</span><br><br>";

		String solucion = "<span style='color:#" + color + "'><b>មូលហេតុ:</b><br>"
				+ "Simple Radio មិនឆបគ្នាជាមួយកំណែថ្មី Lexiconfig។</span><br><br>" + "<span style='color:#" + color
				+ "'><b>ដំណោះស្រាយ:</b><ul>" + "<li>ប្រើ Lexiconfig កំណែចាស់ (ឧ. 1.3.11)។</li>"
				+ "<li>ធ្វើបច្ចុប្បន្នភាព Simple Radio។</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorMobAITweaks() {
		return "កំហុស Mob AI Tweaks";
	}

	@Override
	public String mensajeErrorMobAITweaks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "បានរកឃើញកំហុសដែលពាក់ព័ន្ធនឹង <b>Mob AI Tweaks</b>។</span><br><br>" + "<span style='color:#" + color
				+ "'>" + "កំហុសមកពី Mixin ពេល spawn entity ដែលបង្ហាញជម្លោះជាមួយម៉ូដផ្សេង។</span><br><br>";

		String solucion = "<span style='color:#" + color + "'><b>ដំណោះស្រាយ:</b><ul>"
				+ "<li>លុប Mob AI Tweaks ដើម្បីសាកល្បង។</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	public String nombre_verificacion_gpu() {
		return "ការត្រួតពិនិត្យ GPU (OpenGL / ជ្រើស GPU)";
	}

	public String desactivar_parche_gpu() {
		return "បិទការត្រួតពិនិត្យ GPU";
	}

	// ==================== CRASH ====================

	public String gpu_crash_posible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ការត្រួតពិនិត្យ GPU អាចបណ្តាលឱ្យហ្គេមបិទ។</b>";
	}

	public String gpu_crash_causas() {
		return "ការត្រួតពិនិត្យបានចាប់ផ្តើម ប៉ុន្តែមិនបានបញ្ចប់។ វាជាធម្មតាបង្ហាញពីបញ្ហា OpenGL ឬ driver ក្រាហ្វិក។<br><br>"
				+ "មូលហេតុអាចមាន:<br>" + "- Driver ចាស់ ឬមិនស្ថេរភាព<br>" + "- បញ្ហា OpenGL<br>"
				+ "- GPU ចាស់ ឬការកំណត់ hybrid";
	}

	public String gpu_crash_recomendaciones() {
		return "អនុសាសន៍:<br>" + "- ធ្វើបច្ចុប្បន្នភាព driver GPU<br>" + "- បង្ខំប្រើ GPU ខ្លាំង (dedicated)<br>"
				+ "- ជៀសវាងបរិស្ថាន remote ឬ virtual";
	}

	// ==================== NO ÓPTIMA ====================

	public String gpu_no_optima() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>ហ្គេមមិនបានប្រើ GPU ល្អបំផុត។</b>";
	}

	public String gpu_no_optima_detalles() {
		return "វាអាចបន្ថយ FPS ប៉ុន្តែជាទូទៅមិនបណ្តាល crash ទេ។";
	}

	public String gpu_recomendaciones_rendimiento() {
		return "អនុសាសន៍:<br>" + "- បង្ខំប្រើ GPU dedicated នៅក្នុង control panel<br>"
				+ "- កំណត់ Java/Minecraft ទៅ High Performance";
	}

	// ==================== GENERALES ====================

	public String gpu_nota_precision() {
		return "<b>ចំណាំ:</b> ប្រព័ន្ធនេះមិនត្រឹមត្រូវ 100% ទេ។";
	}

	public String gpu_consumo_energia() {
		return "GPU ខ្លាំងប្រើថាមពលច្រើន និងអាចបន្ថយអាយុកាលថ្មលើ laptop។";
	}

	public String gpu_parche_info() {
		return "អ្នកអាចបិទការត្រួតពិនិត្យនេះដោយប្រើប៊ូតុងដំណោះស្រាយរហ័ស។";
	}

	@Override
	public String nombreVerificacionRaptorLake() {
		return "ព្រមានស្ថេរភាព CPU Intel 13/14 Gen";
	}

	@Override
	public String advertenciaRaptorLakeTitulo() {
		return "អាចមានបញ្ហាស្ថេរភាព Intel Raptor Lake";
	}

	@Override
	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String targetMicrocode) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "បានរកឃើញ CPU " + cpu
				+ " ជាមួយ microcode " + microcode + "។</b>"
				+ " CPU Intel 13/14 Gen មានបញ្ហា voltage ខ្ពស់ ដែលអាចប៉ះពាល់អាយុកាល។<br><br>"
				+ "សូមធ្វើបច្ចុប្បន្នភាព BIOS ឬ microcode ទៅ " + targetMicrocode + " ឬខ្ពស់ជាងនេះ។<br>"
				+ "<b>ព្រមាន:</b> ការអាប់ដេត BIOS មានហានិភ័យ។<br><br>"
				+ "<i>នេះមិនមែនជាមូលហេតុ crash បច្ចុប្បន្នទេ គ្រាន់តែជាព័ត៌មាន។</i>";
	}

	@Override
	public String desactivarVerificacionRaptorLake() {
		return "កុំបង្ហាញការព្រមាននេះម្តងទៀត";
	}

	@Override
	public String verArticuloRaptorLake(String fuente) {
		return "អានអត្ថបទនៅ " + fuente;
	}

	@Override
	public String tituloMixins() {
		return "កម្មវិធីស្វែងរក Mixins";
	}

	@Override
	public String mixinsBotonLateral() {
		return "Mixins";
	}

	@Override
	public String mixinsRaiz() {
		return "Mixins ដែលបានរកឃើញ";
	}

	@Override
	public String mixinsTodosLosMods() {
		return "ទាំងអស់";
	}

	@Override
	public String mixinsModConMixin() {
		return "ម៉ូដដែលមាន mixins";
	}

	@Override
	public String mixinsTooltipCombo() {
		return "តម្រងតាមម៉ូដដែលមាន mixins";
	}

	@Override
	public String mixinsRecargar() {
		return "ផ្ទុកឡើងវិញ";
	}

	@Override
	public String mixinsDescompilarSeleccion() {
		return "បំបែកកូដដែលបានជ្រើស";
	}

	@Override
	public String mixinsTargets() {
		return "Targets";
	}

	@Override
	public String mixinsTarget() {
		return "Target";
	}

	@Override
	public String mixinsTargetsMetodo() {
		return "Targets នៃ method";
	}

	@Override
	public String mixinsMetodos() {
		return "Methods";
	}

	@Override
	public String mixinsCampos() {
		return "Fields";
	}

	@Override
	public String mixinsCantidad() {
		return "ចំនួន mixins";
	}

	@Override
	public String mixinsDetalleMixin() {
		return "ព័ត៌មានលម្អិត mixin";
	}

	@Override
	public String mixinsDetalleTarget() {
		return "ព័ត៌មានលម្អិត target";
	}

	@Override
	public String mixinsDetalleMetodo() {
		return "ព័ត៌មានលម្អិត method mixin";
	}

	@Override
	public String mixinsDetalleCampo() {
		return "ព័ត៌មានលម្អិត field mixin";
	}

	@Override
	public String mixinsDetalleConflicto() {
		return "ព័ត៌មានលម្អិតជម្លោះ";
	}

	@Override
	public String mixinsBuscarConflictos() {
		return "ស្វែងរកជម្លោះអាចមាន";
	}

	@Override
	public String mixinsResultadosConflictos() {
		return "លទ្ធផលជម្លោះ";
	}

	@Override
	public String mixinsConflictosPosibles() {
		return "ជម្លោះអាចមាន";
	}

	@Override
	public String mixinsErrorDescompilar() {
		return "កំហុសក្នុងការបំបែកកូដ";
	}

	@Override
	public String mixinsColorPanel() {
		return "ពណ៌ផ្ទាំង mixins";
	}

	@Override
	public String mixinsColorTexto() {
		return "ពណ៌អក្សរ mixins";
	}

	@Override
	public String mixinsColorTextoSuave() {
		return "ពណ៌អក្សររង mixins";
	}

	@Override
	public String mixinsAyudaUso1() {
		return "ឧបករណ៍នេះបង្ហាញម៉ូដដែលមាន mixins និងអនុញ្ញាតឱ្យពិនិត្យ class, target, method និង field។";
	}

	@Override
	public String mixinsAyudaUso2() {
		return "ប្រើ selector ខាងលើដើម្បីតម្រងម៉ូដ ឬបង្ហាញទាំងអស់។";
	}

	@Override
	public String mixinsAyudaUso3() {
		return "ពង្រីក tree ដើម្បីមើល mixin និងធាតុរបស់វា។";
	}

	@Override
	public String mixinsAyudaUso4() {
		return "ចុចស្ដាំដើម្បីស្វែងរកជម្លោះជាមួយ mixins ផ្សេង។";
	}

	@Override
	public String mixinsAyudaUso5() {
		return "អាចបំបែកកូដដើម្បីពិនិត្យ។";
	}

	@Override
	public String mixinsColorComboFondo() {
		return "ពណ៌ផ្ទៃខាងក្រោយ selector";
	}

	@Override
	public String mixinsColorAreaContenidoFondo() {
		return "ពណ៌ផ្ទៃខាងក្រោយនៃផ្ទាំងព័ត៌មានលម្អិត";
	}

	@Override
	public String mixinsColorSeleccionTexto() {
		return "ពណ៌ការជ្រើសអក្សរ";
	}

	@Override
	public String mixinsColorSeleccionTextoActivo() {
		return "ពណ៌អក្សរដែលបានជ្រើស";
	}

	@Override
	public String mixinsColorAyudaTexto() {
		return "ពណ៌អក្សរជំនួយ";
	}

	@Override
	public String mixinsColorArbolFondo() {
		return "ពណ៌ផ្ទៃខាងក្រោយរបស់ដើមឈើ";
	}

	@Override
	public String mixinsColorRendererSeleccionTexto() {
		return "ពណ៌អក្សរដែលបានជ្រើសនៅក្នុងដើមឈើ";
	}

	@Override
	public String mixinsColorRendererSeleccionFondo() {
		return "ពណ៌ផ្ទៃខាងក្រោយដែលបានជ្រើសនៅក្នុងដើមឈើ";
	}

	@Override
	public String mixinsColorRendererBordeSeleccion() {
		return "ពណ៌ស៊ុមជ្រើសនៅក្នុងដើមឈើ";
	}

	@Override
	public String depmapTitulo() {
		return "ផែនទីភាពអាស្រ័យ";
	}

	@Override
	public String depmapBotonLateral() {
		return "DepMap";
	}

	@Override
	public String depmapPestanaMapa() {
		return "ផែនទី";
	}

	@Override
	public String depmapPestanaDependientes() {
		return "អ្នកអាស្រ័យ";
	}

	@Override
	public String depmapRecargar() {
		return "ផ្ទុកឡើងវិញ";
	}

	@Override
	public String depmapDescompilarSeleccion() {
		return "បំបែកកូដដែលបានជ្រើស";
	}

	@Override
	public String depmapVerReferencias() {
		return "មើលការយោង";
	}

	@Override
	public String depmapDependencias() {
		return "ភាពអាស្រ័យ";
	}

	@Override
	public String depmapDependientes() {
		return "អ្នកអាស្រ័យ";
	}

	@Override
	public String depmapDependiente() {
		return "អ្នកអាស្រ័យ";
	}

	@Override
	public String depmapSinDependencias() {
		return "គ្មានអ្នកអាស្រ័យ";
	}

	@Override
	public String depmapSeleccionarMod() {
		return "ជ្រើសរើស mod";
	}

	@Override
	public String depmapSeleccionarModBase() {
		return "mod មូលដ្ឋាន";
	}

	@Override
	public String depmapSeleccionarDependiente() {
		return "mod អាស្រ័យ";
	}

	@Override
	public String depmapSeleccionarPaquete() {
		return "កញ្ចប់";
	}

	@Override
	public String depmapComprobarNoAlineadas() {
		return "ពិនិត្យភាពអាស្រ័យមិនត្រឹមត្រូវ";
	}

	@Override
	public String depmapResultadoNoAlineadas() {
		return "លទ្ធផលភាពអាស្រ័យមិនត្រឹមត្រូវ";
	}

	@Override
	public String depmapClaseInexistente() {
		return "ថ្នាក់មិនមាន";
	}

	@Override
	public String depmapClaseReferenciada() {
		return "ថ្នាក់ដែលបានយោង";
	}

	@Override
	public String depmapOrigen() {
		return "ប្រភព";
	}

	@Override
	public String depmapDestino() {
		return "គោលដៅ";
	}

	@Override
	public String depmapDependenciaDetalle() {
		return "ព័ត៌មានលម្អិតភាពអាស្រ័យ";
	}

	@Override
	public String depmapReferenciaDetalle() {
		return "ព័ត៌មានលម្អិតការយោង";
	}

	@Override
	public String depmapMetodoOrigen() {
		return "មេធដប្រភព";
	}

	@Override
	public String depmapModBase() {
		return "mod មូលដ្ឋាន";
	}

	@Override
	public String depmapTodos() {
		return "ទាំងអស់";
	}

	@Override
	public String depmapSeleccioneUnMod() {
		return "ជ្រើសរើស mod មួយ";
	}

	@Override
	public String depmapSeleccioneParametrosNoAlineadas() {
		return "ជ្រើសរើស mod មូលដ្ឋាន, mod អាស្រ័យ និងកញ្ចប់";
	}

	@Override
	public String depmapSeleccioneClaseParaDescompilar() {
		return "ជ្រើសរើសការយោង ឬលទ្ធផលមួយដើម្បីបំបែកកូដ";
	}

	@Override
	public String depmapErrorDescompilar() {
		return "កំហុសក្នុងការបំបែកកូដ";
	}

	@Override
	public String depmapAyuda1() {
		return "ឧបករណ៍នេះបង្កើតផែនទីភាពអាស្រ័យរវាង mods ដោយប្រើការយោងថ្នាក់។";
	}

	@Override
	public String depmapAyuda2() {
		return "ផ្ទាំងផែនទីបង្ហាញក្រាហ្វដែលភ្ជាប់ mod នីមួយៗជាមួយភាពអាស្រ័យរបស់វា។";
	}

	@Override
	public String depmapAyuda3() {
		return "ផ្ទាំងអ្នកអាស្រ័យរៀបចំ mods ពីអ្នកមានអ្នកអាស្រ័យច្រើនបំផុត ទៅអ្នកគ្មាន។";
	}

	@Override
	public String depmapAyuda4() {
		return "អ្នកអាចពិនិត្យការយោងរវាង mod និងភាពអាស្រ័យ និងបំបែកកូដថ្នាក់ដែលពាក់ព័ន្ធ។";
	}

	@Override
	public String depmapAyuda5() {
		return "ការពិនិត្យភាពអាស្រ័យមិនត្រឹមត្រូវស្វែងរកការយោងទៅថ្នាក់ដែលមិនមានក្នុងកញ្ចប់ mod។";
	}

	@Override
	public String depmapColorPanel() {
		return "ពណ៌ផ្ទាំង";
	}

	@Override
	public String depmapColorTexto() {
		return "ពណ៌អក្សរចម្បង";
	}

	@Override
	public String depmapColorTextoSecundario() {
		return "ពណ៌អក្សររង";
	}

	@Override
	public String depmapColorAyudaTexto() {
		return "ពណ៌អក្សរជំនួយ";
	}

	@Override
	public String depmapColorGrafoFondo() {
		return "ពណ៌ផ្ទៃខាងក្រោយក្រាហ្វ";
	}

	@Override
	public String depmapColorListaFondo() {
		return "ពណ៌ផ្ទៃខាងក្រោយបញ្ជី";
	}

	@Override
	public String depmapColorArbolFondo() {
		return "ពណ៌ផ្ទៃខាងក្រោយដើមឈើ";
	}

	@Override
	public String depmapColorNodo() {
		return "ពណ៌នូដក្រាហ្វ";
	}

	@Override
	public String depmapColorEnlace() {
		return "ពណ៌ខ្សែក្រាហ្វ";
	}

	@Override
	public String depmapColorSeleccion() {
		return "ពណ៌ការជ្រើស";
	}

	@Override
	public String depmapColorSeleccionTexto() {
		return "ពណ៌អក្សរដែលបានជ្រើស";
	}

	@Override
	public String nombreProblemaAzureLibAnimaciones() {
		return "បញ្ហាជាមួយ addon AzureLib";
	}

	@Override
	public String mensajeProblemaAzureLibAnimaciones() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "រកឃើញកំហុស AzureLib នៅពេលផ្ទុក animation។</b>"
				+ "<p>ហ្គេមបានរកឃើញ JSON animation មានទម្រង់មិនត្រឹមត្រូវ។</p>"
				+ "<p>បញ្ហានេះជាទូទៅបណ្តាលមកពី mod ឬ addon ដែលប្រើ <b>AzureLib</b>។</p>" + "<p><b>អនុសាសន៍:</b></p><ul>"
				+ "<li>ប្រើ <b>DepMap</b> ដើម្បីរក addons ដែលអាស្រ័យលើ AzureLib។</li>"
				+ "<li>សាកល្បងបើកហ្គេមដោយដក addons មួយចំនួនចេញ ដើម្បីរកមូលហេតុ។</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaDecocraftNatureEssentialPartnerMod() {
		return "បញ្ហាជាមួយ Decocraft Nature";
	}

	@Override
	public String mensajeProblemaDecocraftNatureEssentialPartnerMod() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "រកឃើញបញ្ហាដែលបណ្តាលដោយ Decocraft Nature។</b>" + "<p>កំហុសកើតឡើងនៅពេលចាប់ផ្តើម mixin config "
				+ "<code>com/razz/essentialpartnermod/mixins.json</code>។</p>"
				+ "<p>បញ្ហានេះអាចដោះស្រាយដោយកែសម្រួលឯកសារ JAR របស់ mod។</p>" + "<p><b>ជំហាន:</b></p><ul>"
				+ "<li>បើកឯកសារ JAR ដោយប្រើកម្មវិធីបង្ហាប់ដូចជា File Roller, Ark, WinRAR, 7-Zip ឬ WinZip។</li>"
				+ "<li>ចូលទៅ <code>META-INF/MANIFEST.MF</code>។</li>" + "<li>លុបបន្ទាត់នេះ:</li></ul>"
				+ "<code>MixinConfigs: com/razz/essentialpartnermod/mixins.json</code>"
				+ "<p><b>សំខាន់:</b> ទុកបន្ទាត់ទទេមួយនៅចុងឯកសារ។</p>";
	}

	@Override
	public String mensajeTetraDeserializadorModeloEstatico() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "រកឃើញកំហុសនៅក្នុង Tetra ឬ addon របស់វា។</b>"
				+ "<p>log បង្ហាញថាមិនមាន deserializer សម្រាប់ប្រភេទម៉ូដែលដែលប្រើដោយ <b>Tetra</b> ឬ addon មួយ។</p>"
				+ "<p><b>ជំហានណែនាំ:</b></p><ul>" + "<li>ដំបូង បិទ ឬលុប <b>addons Tetra</b> ហើយសាកល្បងម្តងទៀត។</li>"
				+ "<li>បើបញ្ហានៅតែមាន សាកល្បងលុប <b>Tetra</b> ផងដែរ។</li>"
				+ "<li>អ្នកអាចប្រើ <b>DepMap</b> ដើម្បីរក addons ដែលពាក់ព័ន្ធ។</li>" + "</ul>"
				+ "<p>ខ្លះៗបញ្ហាមកពី addon ប៉ុន្តែក៏អាចមកពី <b>Tetra</b> ផ្ទាល់ផងដែរ។</p>";
	}

	@Override
	public String nombreTetraDeserializadorModeloEstatico() {
		return "កំហុសក្នុងការបំបែកទិន្នន័យម៉ូដែលនៅក្នុង Tetra";
	}

	@Override
	public String mensajeSimpleEmotesSetupAnimTail() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "រកឃើញកំហុសនៅក្នុង Simple Emotes។</b>"
				+ "<p>log មានខ្សែអក្សរ <b>$simpleemotes$setupAnimTAIL</b> ដែលបង្ហាញទៅ mod <b>Simple Emotes</b> ដោយផ្ទាល់។</p>"
				+ "<p><b>ជម្រើសណែនាំ:</b></p><ul>" + "<li>ដក ឬបិទ <b>Simple Emotes</b> ហើយសាកល្បងម្តងទៀត។</li>"
				+ "<li>បើអ្នកប្រើ mods ដែលកែប្រែ animation សូមពិនិត្យភាពមិនឆបគ្នាជាមួយ <b>Simple Emotes</b>។</li>"
				+ "<li>ធ្វើបច្ចុប្បន្នភាព <b>Simple Emotes</b> និង mods ដែលពាក់ព័ន្ធទៅកំណែដែលឆបគ្នា។</li>" + "</ul>"
				+ "<p>កំហុសនេះជាទូទៅបង្ហាញថា <b>Simple Emotes</b> ពាក់ព័ន្ធដោយផ្ទាល់នឹងបញ្ហា។</p>";
	}

	@Override
	public String nombreSimpleEmotesSetupAnimTail() {
		return "កំហុសនៅក្នុង Simple Emotes";
	}

	// En Idioma

	@Override
	public String mensajeAdvertenciaSKLauncher() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "ព្រមានអំពី SKLauncher។</b>"
				+ "<p>ក្នុងខែចុងក្រោយៗនេះ មានករណីជាច្រើននៃ <b>ការខូចខាតទិន្នន័យ</b> និងបញ្ហាផ្សេងៗដែលពាក់ព័ន្ធនឹង <b>SKLauncher</b>។</p>"
				+ "<p>វាមិនមានន័យថា SKLauncher ជាមូលហេតុនៃកំហុសជានិច្ចទេ ប៉ុន្តែវាអាចជាផ្នែកមួយនៃបញ្ហា។</p>"
				+ "<p><b>សញ្ញានៃការខូចខាតអាចមាន:</b></p><ul>" + "<li>ហ្គេមបិទយ៉ាងឆាប់រហ័សនៅពេលចាប់ផ្តើម។</li>"
				+ "<li>ហ្គេមបរាជ័យសូម្បីតែមិនមាន mods ត្រូវបានដំឡើង។</li>" + "</ul>"
				+ "<p>បើមានស្ថានភាពទាំងនេះ សាកល្បងប្រើ <b>launcher ផ្សេង</b> ដើម្បីពិនិត្យថាបញ្ហាត្រូវបានដោះស្រាយឬអត់។</p>"
				+ "<p>មើលបញ្ជី launcher ដែលណែនាំនៅទីនេះ:</p>"
				+ "<p><a href='https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/docs/ingles/minecraft/Launchers.md'>មើលឯកសារ launchers</a></p>";
	}

	@Override
	public String nombreAdvertenciaSKLauncher() {
		return "ព្រមាន: បញ្ហាអាចកើតមានជាមួយ SKLauncher";
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
		return "ស្កេនម៉ាស៊ីនមេ និង malware";
	}

	@Override
	public String guardEscanearServidores() {
		return "ស្វែងរកម៉ាស៊ីនមេ";
	}

	@Override
	public String guardEscanearMalware() {
		return "ស្វែងរក malware";
	}

	@Override
	public String guardTablaServidores() {
		return "ម៉ាស៊ីនមេមានបញ្ហា";
	}

	@Override
	public String guardTablaMalware() {
		return "ការរកឃើញ malware";
	}

	@Override
	public String guardColServidor() {
		return "ម៉ាស៊ីនមេ";
	}

	@Override
	public String guardColDefinicion() {
		return "និយមន័យ";
	}

	@Override
	public String guardColMensaje() {
		return "សារ";
	}

	@Override
	public String guardColUbicacion() {
		return "ទីតាំង";
	}

	@Override
	public String guardColClase() {
		return "ថ្នាក់";
	}

	@Override
	public String guardColCfr() {
		return "CFR";
	}

	@Override
	public String guardCfr() {
		return "បំបែកកូដ";
	}

	@Override
	public String guardCfrTitulo() {
		return "កូដដែលបានបំបែក";
	}

	@Override
	public String guardDescripcion1() {
		return "ឧបករណ៍នេះអនុញ្ញាតឱ្យស្វែងរកម៉ាស៊ីនមេមានបញ្ហា និងការរកឃើញ malware ក្នុង mods។";
	}

	@Override
	public String guardDescripcion2() {
		return "អាចមានលទ្ធផលមិនត្រឹមត្រូវ (false positives) ជាពិសេសនៅពេលដែលការស្កេនមានភាពតឹងរ៉ឹង។";
	}

	@Override
	public String guardDescripcion3() {
		return "ការពិនិត្យម៉ាស៊ីនមេប្រើនិយមន័យក្រៅ។ បើមិនទាន់ទាញយក អ្នកត្រូវទាញយកជាមុន។";
	}

	@Override
	public String guardDescripcion4() {
		return "បើអ្នកមាននិយមន័យក្នុងមូលដ្ឋានរួច អ្នកអាចជ្រើសប្រើឡើងវិញ ឬធ្វើបច្ចុប្បន្នភាព។";
	}

	@Override
	public String guardDescripcion5() {
		return "នៅក្នុងតារាង malware បើមានថ្នាក់ អ្នកអាចបំបែកវាជាមួយ CFR ដើម្បីពិនិត្យ។";
	}

	@Override
	public String guardEstadoEscaneandoTodo() {
		return "កំពុងស្កេនម៉ាស៊ីនមេ និង malware...";
	}

	@Override
	public String guardEstadoEscaneandoServidores() {
		return "កំពុងស្វែងរកម៉ាស៊ីនមេមានបញ្ហា...";
	}

	@Override
	public String guardEstadoEscaneandoMalware() {
		return "កំពុងស្វែងរក malware...";
	}

	@Override
	public String guardEstadoListo() {
		return "រួចរាល់";
	}

	@Override
	public String guardDefsNoEncontradasTitulo() {
		return "រកមិនឃើញនិយមន័យ";
	}

	@Override
	public String guardDefsNoEncontradasMensaje() {
		return "រកមិនឃើញនិយមន័យម៉ាស៊ីនមេ។ តើអ្នកចង់ទាញយកឥឡូវនេះទេ?";
	}

	@Override
	public String guardDefsDescargar() {
		return "ទាញយក";
	}

	@Override
	public String guardDefsCancelar() {
		return "បោះបង់";
	}

	@Override
	public String guardDefsActualizarTitulo() {
		return "និយមន័យម៉ាស៊ីនមេ";
	}

	@Override
	public String guardDefsActualizarMensaje() {
		return "មាននិយមន័យក្នុងមូលដ្ឋានរួចហើយ។ តើអ្នកចង់ប្រើវា ឬធ្វើបច្ចុប្បន្នភាព?";
	}

	@Override
	public String guardDefsUsarLocales() {
		return "ប្រើក្នុងមូលដ្ឋាន";
	}

	@Override
	public String guardDefsActualizar() {
		return "ធ្វើបច្ចុប្បន្នភាព";
	}

	@Override
	public String guardFuenteDefinicionesTLauncher() {
		return "បញ្ជី TLauncher";
	}

	@Override
	public String guardErrorDescompilar() {
		return "កំហុសក្នុងការបំបែកកូដ";
	}

	@Override
	public String guardColorPanel() {
		return "ពណ៌ផ្ទាំង";
	}

	@Override
	public String guardColorTexto() {
		return "ពណ៌អក្សរ";
	}

	@Override
	public String guardColorTextoSecundario() {
		return "ពណ៌អក្សររង";
	}

	@Override
	public String guardColorTabla() {
		return "ពណ៌តារាង";
	}

	@Override
	public String guardColorSeleccion() {
		return "ពណ៌ការជ្រើស";
	}

	@Override
	public String guardColorSeleccionTexto() {
		return "ពណ៌អក្សរដែលបានជ្រើស";
	}

	@Override
	public String texto_de_boton_compartir_instancia_modpack() {
		return "ចែករំលែក instancia/modpack";
	}

	@Override
	public String popup_compartir_instancia_modpack() {
		return "មុខងារចែករំលែក instancia ឬ modpack មិនទាន់អនុវត្តនៅឡើយ។";
	}

	@Override
	public String colorBotonCompartirVerdeOscuro() {
		return "ពណ៌ប៊ូតុងចែករំលែកសំខាន់";
	}

	@Override
	public String colorBotonCompartirVerdeClaro() {
		return "ពណ៌ប៊ូតុងតំណភ្ជាប់ចែករំលែក";
	}

	@Override
	public String colorTextoBotonesCompartir() {
		return "ពណ៌អក្សរប៊ូតុងចែករំលែក";
	}

	@Override
	public String compartirInstanciaTitulo() {
		return "ចែករំលែក instancia";
	}

	@Override
	public String compartirInstanciaBotonLateral() {
		return "ចែករំលែក instancia";
	}

	@Override
	public String compartirInstanciaFormato() {
		return "ទ្រង់ទ្រាយ";
	}

	@Override
	public String compartirInstanciaServicio() {
		return "សេវាអាប់ឡូដ";
	}

	@Override
	public String compartirInstanciaBotonCompartir() {
		return "វេចខ្ចប់ និងចែករំលែក";
	}

	@Override
	public String compartirInstanciaBotonRefrescar() {
		return "ផ្ទុកឡើងវិញ";
	}

	@Override
	public String compartirInstanciaEstadoListo() {
		return "រួចរាល់";
	}

	@Override
	public String compartirInstanciaEstadoEmpaquetando() {
		return "កំពុងវេចខ្ចប់ជម្រើស...";
	}

	@Override
	public String compartirInstanciaEstadoSubiendo() {
		return "កំពុងអាប់ឡូដឯកសារ...";
	}

	@Override
	public String compartirInstanciaEstadoError() {
		return "កំហុស";
	}

	@Override
	public String compartirInstanciaCodigo() {
		return "កូដ";
	}

	@Override
	public String compartirInstanciaEnlace() {
		return "តំណភ្ជាប់";
	}

	@Override
	public String compartirInstanciaMantenerAbierto() {
		return "អ្នកត្រូវរក្សាអាប់កម្មវិធីឲ្យបើក ដើម្បីបន្តការផ្ទេរ។";
	}

	@Override
	public String compartirInstanciaSinSeleccion() {
		return "មិនមានថត ឬឯកសារដែលបានជ្រើស។";
	}

	@Override
	public String compartirInstanciaFormatoNoSoportado() {
		return "ទ្រង់ទ្រាយនេះមិនទាន់គាំទ្រ។";
	}

	@Override
	public String compartirInstanciaServicioNoDisponible() {
		return "សេវាដែលបានជ្រើសមិនមាន។";
	}

	@Override
	public String compartirInstanciaSubidaCompleta() {
		return "ការផ្ទេរបានចាប់ផ្តើមដោយជោគជ័យ។";
	}

	@Override
	public String compartirInstanciaErrorSubir() {
		return "មិនអាចអាប់ឡូដឯកសារដែលបានជ្រើស។";
	}

	@Override
	public String compartirInstanciaColorPanel() {
		return "ពណ៌ផ្ទាំង";
	}

	@Override
	public String compartirInstanciaColorTexto() {
		return "ពណ៌អក្សរ";
	}

	@Override
	public String compartirInstanciaPolitica1() {
		return "ប្រភេទណែនាំ៖ mods, configs, saves, worlds, datapacks, resource packs និងឯកសារជម្រើស។ ជៀសវាងបញ្ចូលទិន្នន័យផ្ទាល់ខ្លួនដែលមិនចាំបាច់។";
	}

	@Override
	public String compartirInstanciaPolitica2() {
		return "បន្ថែមអាចផ្តល់សេវាអាប់ឡូដផ្ទាល់ខ្លួន។ សេវាដែលភ្ជាប់មកជាមុនគួរតែបង្ហាញនៅទីនេះ។";
	}

	@Override
	public String compartirInstanciaPolitica3() {
		return "wormhole.app: រហូតដល់ 5 GiB ជាការអាប់ឡូដធម្មតា; ចន្លោះ 5–10 GiB ត្រូវរក្សាអ្នកផ្ញើឲ្យបើក។ ការរួមបញ្ចូលពិតប្រាកដនៅមិនទាន់អនុវត្ត។";
	}

	@Override
	public String compartirInstanciaPolitica4() {
		return "limewire.com: សេវាផ្ទុកជាបណ្តោះអាសន្ន។ មិនទាន់គាំទ្រ។";
	}

	@Override
	public String compartirInstanciaPolitica5() {
		return "bittorrent: របៀបមានសុវត្ថិភាពជាង ដោយចែកចាយ P2P ដោយផ្ទាល់។ មិនទាន់គាំទ្រ។";
	}

	@Override
	public String compartirInstanciaPolitica6() {
		return "តាមលំនាំដើម ប្រព័ន្ធជ្រើសថត និងឯកសារដែលគេប្រើជាញឹកញាប់ ដើម្បីជួយការគាំទ្រ។";
	}

	@Override
	public String compartirInstanciaPolitica7() {
		return "បើរួមបញ្ចូលថត CrashDetector ខាងក្នុង នឹងរួមទាំងការកំណត់, log និងទិន្នន័យជំនួយ។ អ្នកអាចដកវាចេញបើមិនចាំបាច់។";
	}

	@Override
	public String malware_fracturiser_detectado() {
		return "រកឃើញ Fracturiser អាចមាន។ ភស្តុតាង:";
	}

	@Override
	public String malware_info_stealer_detectado() {
		return "រកឃើញកម្មវិធីលួចព័ត៌មានអាចមាន។ ភស្តុតាង:";
	}

	@Override
	public String malware_evidencia_clase_sospechosa() {
		return "ថ្នាក់សង្ស័យ:";
	}

	@Override
	public String malware_evidencia_archivo_sospechoso() {
		return "ឯកសារសង្ស័យ:";
	}

	@Override
	public String malware_brightsdk_detectado() {
		return "រកឃើញ Bright SDK អាចមាន។ ភស្តុតាង:";
	}

	@Override
	public String malware_evidencia_paquete_sospechoso() {
		return "កញ្ចប់សង្ស័យ:";
	}

	@Override
	public String docsTituloVentana() {
		return "កម្មវិធីអានឯកសារ";
	}

	@Override
	public String docsAyudaDeUso() {
		return "<b>របៀបប្រើកម្មវិធីអាននេះ</b><br>" + "ជ្រើសភាសានៅផ្នែកខាងក្រោម ដើម្បីមើលឯកសារដែលមាន។<br>"
				+ "នៅផ្ទាំងខាងឆ្វេង អ្នកអាចរុករកថត និងឯកសារ។<br>" + "ពេលចុចលើឯកសារ មាតិកានឹងបង្ហាញខាងស្តាំ។<br>"
				+ "តំណដែលមានប្រូតូកូល <b>docs://</b> នឹងបើកឯកសារផ្សេងក្នុងកម្មវិធីនេះ។";
	}

	@Override
	public String docsArbolTitulo() {
		return "ឯកសារ";
	}

	@Override
	public String docsVisorTitulo() {
		return "មាតិកា";
	}

	@Override
	public String docsNoHayDocumentos() {
		return "មិនមានឯកសារសម្រាប់ភាសានេះទេ។";
	}

	@Override
	public String docsNoHayMarkdown() {
		return "រកមិនឃើញឯកសារ Markdown សម្រាប់ភាសានេះ។";
	}

	@Override
	public String docsDocumentoNoEncontrado() {
		return "រកមិនឃើញឯកសារដែលបានស្នើ។";
	}

	@Override
	public String docsErrorAlAbrirDocumento() {
		return "កំហុសក្នុងការបើកឯកសារ:";
	}

	@Override
	public String docsCargando() {
		return "កំពុងផ្ទុកឯកសារ...";
	}

	@Override
	public String docsImagenNoDisponible() {
		return "រូបភាពមិនមាន";
	}

	@Override
	public String colorPanelSecundario() {
		return "ពណ៌ផ្ទាំងរង";
	}

	@Override
	public String colorTextoSuave() {
		return "ពណ៌អក្សរទន់";
	}

	@Override
	public String colorSeleccion() {
		return "ពណ៌ការជ្រើស";
	}

	@Override
	public String colorFondoDocumento() {
		return "ពណ៌ផ្ទៃខាងក្រោយឯកសារ";
	}

	@Override
	public String iaTituloVentana() {
		return "IA";
	}

	@Override
	public String iaTituloPrincipal() {
		return "វិភាគដោយ IA";
	}

	@Override
	public String iaDescripcionTitulo() {
		return "ភ្នាក់ងារវិភាគ crash";
	}

	@Override
	public String iaDescripcionCuerpo() {
		return "ឧបករណ៍នេះបើកភ្នាក់ងារខាងក្រៅ ដែលអាចជួយអ្នកវិភាគ crash បញ្ហា និង log ទាក់ទងនឹង Minecraft។";
	}

	@Override
	public String iaDescripcionUso() {
		return "ដើម្បីប្រើប្រព័ន្ធនេះ សូមបើកតំណ ហើយចូលដោយប្រើគណនី Baidu បន្ទាប់មកប្រើភ្នាក់ងារដើម្បីវិភាគ crash ឬ log របស់អ្នក។";
	}

	@Override
	public String iaAvisoCuentaBaidu() {
		return "អ្នកត្រូវការគណនី Baidu ដើម្បីចូលប្រើមុខងារនេះ។";
	}

	@Override
	public String iaCopiarEnlace() {
		return "ចម្លងតំណ";
	}

	@Override
	public String iaAbrirEnlace() {
		return "បើកតំណ";
	}

	@Override
	public String iaImagenNoDisponible() {
		return "រូបភាពមិនមាន";
	}

	@Override
	public String mensajeOculusIrisUnknownShaderVariable() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុស Shader ដែលអាចកើតមានជាមួយ Oculus ឬ Iris។</b>"
				+ "<p>ប្រវត្តិកំណត់ត្រា (Log) មានផ្ទុកទាំង <b>kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal</b> "
				+ "និង <b>java.lang.RuntimeException: Unknown variable:</b>។</p>"
				+ "<p>ការបញ្ចូលគ្នានេះជាធម្មតាបង្ហាញពីបញ្ហាក្នុងការវាយតម្លៃអថេរនៅក្នុង Shader，"
				+ "ដែលជាញឹកញាប់ទាក់ទងនឹង <b>Oculus</b>, <b>Iris</b>, ឬ <b>កញ្ចប់ Shader (shader pack)</b> ដែលកំពុងប្រើ។</p>"
				+ "<p><b>លំដាប់នៃការសាកល្បងដែលណែនាំ៖</b></p>" + "<ul>"
				+ "<li>ជាដំបូង，ចាប់ផ្តើមហ្គេម <b>ដោយមិនបើកដំណើរការ Shaders</b>។</li>"
				+ "<li>ប្រសិនបើបញ្ហានៅតែបន្ត，សូមសាកល្បងចាប់ផ្តើម <b>ដោយគ្មាន Oculus ឬ Iris</b>។</li>"
				+ "<li>ធ្វើបច្ចុប្បន្នភាព <b>Oculus/Iris</b>, <b>កញ្ចប់ Shader</b> និង Mods គ្រាហ្វិកដែលពាក់ព័ន្ធ។</li>"
				+ "<li>ប្រសិនបើអ្នកប្រើ Mods បង្ហាញរូបភាព ឬគ្រាហ្វិកផ្សេងទៀត，សូមពិនិត្យមើលភាពមិនឆបគ្នារវាងពួកវា។</li>"
				+ "</ul>"
				+ "<p>ជាការអនុវត្តជាក់ស្តែង，កំហុសនេះជាធម្មតាកើតចេញពី <b>កញ្ចប់ Shader</b> ឬពីអន្តរកម្មរបស់វាជាមួយ <b>Oculus/Iris</b>។</p>";
	}

	@Override
	public String nombreOculusIrisUnknownShaderVariable() {
		return "កំហុស Shader ដែលអាចកើតមានជាមួយ Oculus/Iris";
	}

	@Override
	public String mensajeItemNoExiste(String itemFaltante, String namespace) {
		String itemHtml = itemFaltante == null || itemFaltante.isEmpty() ? "(មិនស្គាល់)" : itemFaltante;
		String namespaceHtml = namespace == null || namespace.isEmpty() ? "(មិនស្គាល់)" : namespace;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានព្យាយាមប្រើធាតុដែលមិនមាន។</b>" + "<p>ប្រវត្តិកំណត់ត្រា (Log) មានបន្ទាត់ <b>Item: " + itemHtml
				+ " does not exist</b>។</p>"
				+ "<p>ជាធម្មតា នេះមានន័យថា <b>datapack</b>, <b>mod</b> ឬ <b>ការកំណត់រចនាសម្ព័ន្ធ</b> ខ្លះ "
				+ "កំពុងយោងទៅលើធាតុដែលមិនមាននៅក្នុងហ្គេម។</p>" + "<p><b>អ្វីដែលត្រូវពិនិត្យ៖</b></p>" + "<ul>"
				+ "<li>ពិនិត្យមើលថាតើអ្នកបានដំឡើង mod ដែលគួរតែផ្តល់ធាតុ <b>" + itemHtml + "</b> ឬនៅ។</li>"
				+ "<li>ប្រសិនបើមាន វាអាចជា <b>កំណែមិនត្រឹមត្រូវ</b> ធាតុត្រូវបានផ្លាស់ប្តូរ ឬលុបចោល "
				+ "ឬ mod នោះមានបញ្ហា ហើយគួរតែដកវាចេញ។</li>"
				+ "<li>ប្រសិនបើអ្នកមិនមាន mod នោះទេ សូមព្យាយាម <b>ដំឡើងវា</b>។</li>" + "</ul>"
				+ "<p><b>ដើម្បីដឹងថា mod ឬ datapack ណាដែលកំពុងស្នើសុំធាតុនោះ៖</b></p>" + "<ul>"
				+ "<li>ប្រើឧបករណ៍ <b>grepr</b> នៅក្នុងរបារចំហៀង។</li>" + "<li><b>កុំ</b> ជ្រើសរើសថត (folder)។</li>"
				+ "<li>បើកជម្រើស <b>search in archives</b>។</li>"
				+ "<li>នៅក្នុងអត្ថបទស្វែងរក សូមវាយបញ្ចូល <b>namespace</b> ពោលគឺផ្នែកខាងមុខនៃសញ្ញាពីរចំណុច៖ " + "<b>"
				+ namespaceHtml + "</b>។</li>" + "</ul>"
				+ "<p>ជាធម្មតា វាជួយស្វែងរកថាតើឯកសារ mod ឬ datapack ណាដែលកំពុងធ្វើការយោងមិនត្រឹមត្រូវ។</p>";
	}

	@Override
	public String nombreItemNoExiste() {
		return "ធាតុដែលមិនមានត្រូវបានយោង";
	}

	@Override
	public String mensajeCobblemonPinkanIslandsRhyhornModelo() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុសគំរូសម្រាប់ Rhyhorn។</b>"
				+ "<p>ប្រវត្តិកំណត់ត្រា (Log) មានបន្ទាត់ <b>Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn</b>។</p>"
				+ "<p>ទោះបីជាគំរូប្រើ namespace របស់ <b>Cobblemon</b> ក៏ដោយ បន្ទាត់នេះជាធម្មតាបណ្តាលមកពី mod "
				+ "<b>Cobblemon: Pinkan Islands</b> ដែលជាប្រភពនៃ <b>Rhyhorn</b> នោះ។</p>"
				+ "<p><b>អ្វីដែលគួរសាកល្បង៖</b></p>" + "<ul>"
				+ "<li>ដកចេញ ឬបិទដំណើរការ <b>Cobblemon: Pinkan Islands</b> ហើយសាកល្បងម្តងទៀត។</li>"
				+ "<li>ធ្វើបច្ចុប្បន្នភាព <b>Cobblemon</b> និង <b>Cobblemon: Pinkan Islands</b> ទៅជាកំណែដែលឆបគ្នា។</li>"
				+ "<li>ប្រសិនបើបញ្ហាបានចាប់ផ្តើមបន្ទាប់ពីធ្វើបច្ចុប្បន្នភាព mod ណាមួយក្នុងចំណោមនោះ សូមសាកល្បងបន្សំកំណែផ្សេងទៀត។</li>"
				+ "</ul>" + "<p>កំហុសនេះជាធម្មតាបង្ហាញពីគំរូដែលបាត់ ចុះបញ្ជីមិនត្រឹមត្រូវ ឬមិនឆបគ្នានៅក្នុង "
				+ "<b>Cobblemon: Pinkan Islands</b>។</p>";
	}

	@Override
	public String nombreCobblemonPinkanIslandsRhyhornModelo() {
		return "កំហុសគំរូ Rhyhorn នៅក្នុង Cobblemon: Pinkan Islands";
	}

	@Override
	public String mensajeColdSweatInitDynamicTags() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុសនៅក្នុង Cold Sweat។</b>"
				+ "<p>ប្រវត្តិកំណត់ត្រា (Log) មានសញ្ញាដូចជា <b>$cold_sweat$onBuildStart</b>, "
				+ "<b>InitDynamicTagsEvent.fillTag</b> និង <b>NullPointerException</b> ដែល "
				+ "បញ្ជីឈ្មោះ (registry) លេចឡើងជា null។</p>"
				+ "<p>នេះជាធម្មតាបង្ហាញពីបញ្ហានៃ <b>Cold Sweat</b> នៅពេលសាងសង់ ឬបំពេញ "
				+ "<b>ស្លាក динаមិក (dynamic tags)</b> ជាធម្មតាដោយសារតែភាពមិនឆបគ្នា កំហុសខាងក្នុងនៃ mod "
				+ "ឬការបញ្ចូលគ្នាដែលមានជម្លោះជាមួយ mod ឬ datapack ផ្សេងទៀត។</p>" + "<p><b>អ្វីដែលគួរសាកល្បង៖</b></p>"
				+ "<ul>" + "<li>ដកចេញ ឬបិទដំណើរការ <b>Cold Sweat</b> ហើយសាកល្បងម្តងទៀត។</li>"
				+ "<li>ធ្វើបច្ចុប្បន្នភាព <b>Cold Sweat</b> ទៅជាកំណែដែលឆបគ្នាជាមួយកំណែ Minecraft និង loader របស់អ្នក។</li>"
				+ "<li>ប្រសិនបើអ្នកប្រើ datapacks ឬ mods ដែលផ្លាស់ប្តូរ <b>ស្លាក (tags)</b>, <b>ជីវចម្រុះ (biomes)</b>, <b>សីតុណ្ហភាព</b> ឬបញ្ជីឈ្មោះដែលពាក់ព័ន្ធ សូមពិនិត្យមើលវាផងដែរ។</li>"
				+ "<li>ប្រសិនបើកំហុសបានចាប់ផ្តើមបន្ទាប់ពីធ្វើបច្ចុប្បន្នភាព mods សូមសាកល្បងបន្សំកំណែផ្សេងទៀត។</li>"
				+ "</ul>" + "<p>ក្នុងករណីនេះ <b>Cold Sweat</b> ពាក់ព័ន្ធដោយផ្ទាល់នៅក្នុងកំហុសនេះ។</p>";
	}

	@Override
	public String nombreColdSweatInitDynamicTags() {
		return "កំហុស Cold Sweat នៅក្នុងស្លាក динаមិក";
	}

	@Override
	public String mensajeClassCastExceptionGeneral(String lineaClassCast) {
		String detalle = lineaClassCast == null || lineaClassCast.isEmpty() ? ""
				: "<p><b>បន្ទាត់ដែលបានរកឃើញ៖</b></p><p><code>" + lineaClassCast + "</code></p>";

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញ ClassCastException។</b>"
				+ "<p>នេះមានន័យថា ថ្នាក់ (class) មួយត្រូវបានចាត់ទុកដូចជាថ្នាក់ ឬចំណុចប្រទាក់ (interface) ផ្សេងទៀតដែលមិនឆបគ្នា។</p>"
				+ detalle + "<p>ប្រភេទកំហុសនេះជាធម្មតាបណ្តាលមកពីស្ថានភាពណាមួយដូចខាងក្រោម៖</p>" + "<ul>"
				+ "<li><b>ម៉ូដ (mods) ពីរ</b> ដែលមិនឆបគ្នាគ្នាទៅវិញទៅមក។</li>"
				+ "<li><b>Mixins</b>, <b>transformers</b> ឬ patches ដែលកែប្រែថ្នាក់មួយ ហើយធ្វើឱ្យផ្នែកផ្សេងទៀតនៃហ្គេមរំពឹងទុកប្រភេទផ្សេង។</li>"
				+ "<li>ម៉ូដផ្សេងទៀតដែលមាននៅក្នុង <b>stacktrace</b> ដែលបណ្តាលឱ្យមានការបម្លែងប្រភេទខុស (miscast)។</li>"
				+ "</ul>" + "<p><b>អ្វីដែលត្រូវពិនិត្យ៖</b></p>" + "<ul>"
				+ "<li>មើលបន្ទាត់ <b>stacktrace</b> ដែលទាក់ទងនឹងកំហុសនេះ។</li>"
				+ "<li>សូមយកចិត្តទុកដាក់ជាពិសេសលើឈ្មោះម៉ូដ ឬថ្នាក់ដែលមានទម្រង់ <b>$modid$algo</b> ព្រោះវាជាធម្មតាបង្ហាញពីម៉ូដដែលពាក់ព័ន្ធ។</li>"
				+ "<li>សាកល្បងធ្វើបច្ចុប្បន្នភាព ដកចេញ ឬដាច់ដោយឡែកម៉ូដដែលហាក់ដូចជាទាក់ទងនឹងការបម្លែងមិនត្រឹមត្រូវ។</li>"
				+ "</ul>"
				+ "<p>ទោះបីជា <b>ClassCastException</b> មិនតែងតែបណ្តាលឱ្យខូច (fatal) ក៏ដោយ ប៉ុន្តែជាញឹកញាប់វាពិតជាបែបនោះមែន។</p>";
	}

	@Override
	public String nombreClassCastExceptionGeneral() {
		return "បានរកឃើញ ClassCastException";
	}

	@Override
	public String mensajeValkyrienSkiesTournamentLithiumPoiInjection() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញភាពមិនឆបគ្នាដែលអាចកើតមានរវាង Valkyrien Skies Tournament និង Lithium/Radium។</b>"
				+ "<p>ប្រវត្តិកំណត់ត្រា (Log) មាន <b>InvalidInjectionException</b> ដែលបង្ហាញ mixin របស់ "
				+ "<b>Lithium</b> លើ <b>POI</b> ព្រមជាមួយនឹង <b>valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager</b>។</p>"
				+ "<p>បញ្ហានេះជាធម្មតាកើតឡើងនៅពេលប្រើ <b>កំណែចាស់របស់ Lithium</b> ឬ "
				+ "<b>fork ដែលផ្អែកលើ Lithium ចាស់</b> ដូចជា <b>Radium Reforged</b> រួមជាមួយ "
				+ "<b>VS Tournament</b>។</p>" + "<p><b>អ្វីដែលគួរសាកល្បង៖</b></p>" + "<ul>"
				+ "<li>ធ្វើបច្ចុប្បន្នភាព <b>Lithium</b> ទៅជាកំណែថ្មីជាងនេះ និងឆបគ្នា។</li>"
				+ "<li>ប្រសិនបើអ្នកនៅលើ <b>Forge/NeoForge</b> ហើយកំពុងប្រើ <b>Radium Reforged</b> ឬ fork ចាស់ផ្សេងទៀត សូមដកវាចេញ។</li>"
				+ "<li>ជំនួសវិញ សូមសាកល្បង <b>Harium</b> ដែលជា fork ទំនើបរបស់ Radium ដែលបានធ្វើសមកាលកម្មជាមួយការកែលម្អថ្មីៗរបស់ Lithium។</li>"
				+ "<li>ប្រសិនបើបញ្ហាបានចាប់ផ្តើមបន្ទាប់ពីធ្វើបច្ចុប្បន្នភាព mods សូមពិនិត្យមើលបន្សំជាក់លាក់រវាង <b>VS Tournament</b> និង mod បង្កើនល្បឿន AI/POI របស់អ្នក។</li>"
				+ "</ul>"
				+ "<p>ជាការអនុវត្តជាក់ស្តែង កំហុសនេះជាធម្មតាកើតចេញពីការអនុវត្តចាស់របស់ <b>Lithium/Radium</b> ដែលមិនសូវស៊ីគ្នាជាមួយ <b>VS Tournament</b>។</p>";
	}

	@Override
	public String nombreValkyrienSkiesTournamentLithiumPoiInjection() {
		return "ភាពមិនឆបគ្នារបស់ VS Tournament ជាមួយ Lithium/Radium";
	}

	@Override
	public String mensajeVSTournamentVSConfigClassNoExiste() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VS Tournament ហាក់ដូចជាចាស់ពេកសម្រាប់កំណែ Valkyrien Skies របស់អ្នក។</b>"
				+ "<p>ប្រវត្តិកំណត់ត្រា (Log) មាន <b>NoClassDefFoundError</b> សម្រាប់ "
				+ "<b>org/valkyrienskies/core/impl/config/VSConfigClass</b> ព្រមទាំងបន្ទាត់មកពី "
				+ "<b>org.valkyrienskies.tournament.TournamentMod.init(...)</b>។</p>"
				+ "<p>ជាទូទៅ នេះមានន័យថាអ្នកកំពុងប្រើ <b>កំណែចាស់របស់ VS Tournament</b> ដែលព្យាយាម "
				+ "ប្រើប្រាស់ថ្នាក់ខាងក្នុងចាស់ៗរបស់ <b>Valkyrien Skies</b> ដែលលែងមានទៀតហើយ។</p>"
				+ "<p><b>អ្វីដែលត្រូវធ្វើ៖</b></p>" + "<ul>" + "<li>ដកចេញ <b>VS Tournament</b> ចាស់។</li>"
				+ "<li>ប្រើ <b>VS Tournament Reforged</b> ជំនួសវិញ។</li>"
				+ "<li>ពិនិត្យមើលផងដែរថាតើកំណែរបស់ <b>Valkyrien Skies</b> ត្រូវគ្នានឹងកំណែដែល addon គាំទ្រដែរឬទេ។</li>"
				+ "</ul>"
				+ "<p>ការណែនាំឱ្យផ្លាស់ប្តូរទៅប្រើ <b>VS Tournament Reforged</b> គឺស្របជាមួយស្ថានភាពបច្ចុប្បន្ននៃគម្រោង៖ "
				+ "កំណែដើមរបស់ Tournament នៅតែត្រូវបានចុះបញ្ជីជា mod ចាស់សម្រាប់ 1.18.2 ខណៈដែល "
				+ "<b>VS Tournament Reforged</b> ត្រូវបានបោះផ្សាយដាច់ដោយឡែក និងបច្ចុប្បន្នបានប្រកាសពីការគាំទ្រសម្រាប់ Valkyrien "
				+ "2.4.9+ នៅលើ Forge 1.20.1។</p>";
	}

	@Override
	public String nombreVSTournamentVSConfigClassNoExiste() {
		return "VS Tournament ចាស់មិនឆបគ្នាជាមួយ Valkyrien Skies";
	}

	public String curseForgeClaveApiMundial() {
		return "គន្លឹះ API សកលរបស់ CurseForge";
	}

	public String curseForgeEndpoint() {
		return "ចំណុចចុង CurseForge";
	}

	public String tlmodsEndpoint() {
		return "ចំណុចចុង TLMods";
	}

	public String minecraftStorageEndpoint() {
		return "ចំណុចចុង MinecraftStorage";
	}

	public String autoBackupActivado() {
		return "បានបើកការបម្រុងទុកស្វ័យប្រវត្តិ";
	}

	public String autoBackupFrecuencia() {
		return "ប្រេកង់នៃការបម្រុងទុកស្វ័យប្រវត្តិ";
	}

	public String autoBackupDiasConservar() {
		return "ថ្ងៃរក្សាទុកការបម្រុងទុកស្វ័យប្រវត្តិ";
	}

	public String autoBackupTamanoMaximoMB() {
		return "ទំហំអតិបរមានៃការបម្រុងទុកស្វ័យប្រវត្តិ (MB)";
	}

	public String actualizadorModsTitulo() {
		return "ឧបករណ៍ធ្វើបច្ចុប្បន្នភាព Mod";
	}

	public String actualizadorModsBotonSidebar() {
		return "ធ្វើបច្ចុប្បន្នភាព";
	}

	public String actualizadorModsDescripcion() {
		return "ស្វែងរកការធ្វើបច្ចុប្បន្នភាពដែលមានសម្រាប់ mod ក្នុង modpack។ អ្នកអាចធ្វើបច្ចុប្បន្នភាពទាំងអស់ ឬអនុវត្តការធ្វើបច្ចុប្បន្នភាពម្តងៗ។";
	}

	public String actualizadorModsBotonEscanear() {
		return "ពិនិត្យការធ្វើបច្ចុប្បន្នភាព";
	}

	public String actualizadorModsBotonActualizarTodo() {
		return "ធ្វើបច្ចុប្បន្នភាពទាំងអស់";
	}

	public String actualizadorModsBotonActualizarUno() {
		return "ធ្វើបច្ចុប្បន្នភាព";
	}

	public String actualizadorModsEstadoListo() {
		return "រួចរាល់";
	}

	public String actualizadorModsEstadoEscaneando() {
		return "កំពុងស្វែងរកការធ្វើបច្ចុប្បន្នភាព...";
	}

	public String actualizadorModsEstadoActualizando() {
		return "កំពុងធ្វើបច្ចុប្បន្នភាព...";
	}

	public String actualizadorModsEstadoSinActualizaciones() {
		return "គ្មានការធ្វើបច្ចុប្បន្នភាពដែលអាចប្រើបានទេ។";
	}

	public String actualizadorModsEstadoEncontradas(int n) {
		return "រកឃើញការធ្វើបច្ចុប្បន្នភាព៖ " + n;
	}

	public String actualizadorModsEstadoActualizadas(int n) {
		return "បានអនុវត្តការធ្វើបច្ចុប្បន្នភាព៖ " + n;
	}

	public String actualizadorModsEstadoError() {
		return "មានកំហុសក្នុងការធ្វើបច្ចុប្បន្នភាព។";
	}

	public String actualizadorModsSinSeleccion() {
		return "មិនមានការធ្វើបច្ចុប្បន្នភាពត្រូវបានជ្រើសរើសទេ។";
	}

	public String actualizadorModsColumnaMod() {
		return "Mod";
	}

	public String actualizadorModsColumnaActual() {
		return "បច្ចុប្បន្ន";
	}

	public String actualizadorModsColumnaNueva() {
		return "ថ្មី";
	}

	public String actualizadorModsColumnaFuente() {
		return "ប្រភព";
	}

	public String actualizadorModsColumnaLoader() {
		return "កម្មវិធីផ្ទុក";
	}

	public String actualizadorModsColumnaRuta() {
		return "ផ្លូវ";
	}

	public String actualizadorModsColumnaAccion() {
		return "សកម្មភាព";
	}

	public String actualizadorModsColorFondo() {
		return "ឧបករណ៍ធ្វើបច្ចុប្បន្នភាព៖ ផ្ទៃ";
	}

	public String actualizadorModsColorPanel() {
		return "ឧបករណ៍ធ្វើបច្ចុប្បន្នភាព៖ ផ្ទាំង";
	}

	public String actualizadorModsColorTexto() {
		return "ឧបករណ៍ធ្វើបច្ចុប្បន្នភាព៖ អត្ថបទ";
	}

	public String actualizadorModsColorTextoSuave() {
		return "ឧបករណ៍ធ្វើបច្ចុប្បន្នភាព៖ អត្ថបទស្រាល";
	}

	public String actualizadorModsColorBoton() {
		return "ឧបករណ៍ធ្វើបច្ចុប្បន្នភាព៖ ប៊ូតុង";
	}

	public String actualizadorModsColorBotonTexto() {
		return "ឧបករណ៍ធ្វើបច្ចុប្បន្នភាព៖ អត្ថបទប៊ូតុង";
	}

	public String actualizadorModsColorTabla() {
		return "ឧបករណ៍ធ្វើបច្ចុប្បន្នភាព៖ តារាង";
	}

	public String actualizadorModsColorSeleccion() {
		return "ឧបករណ៍ធ្វើបច្ចុប្បន្នភាព៖ ការជ្រើសរើស";
	}

	public String importadorYumeiriTeExtraniamos() {
		return "យើងនឹករលឹកអ្នក, Yumeiri Reyu។";
	}

	public String importadorColorFondo() {
		return "ឧបករណ៍នាំចូល: ផ្ទៃ";
	}

	public String importadorColorPanel() {
		return "ឧបករណ៍នាំចូល: ផ្ទាំង";
	}

	public String importadorColorTexto() {
		return "ឧបករណ៍នាំចូល: អត្ថបទ";
	}

	public String importadorColorTextoSuave() {
		return "ឧបករណ៍នាំចូល: អត្ថបទស្រាល";
	}

	public String importadorColorBoton() {
		return "ឧបករណ៍នាំចូល: ប៊ូតុង";
	}

	public String importadorColorBotonTexto() {
		return "ឧបករណ៍នាំចូល: អត្ថបទប៊ូតុង";
	}

	public String importadorColorBorde() {
		return "ឧបករណ៍នាំចូល: គែម";
	}

	public String importadorConflictoTitulo() {
		return "ជម្លោះក្នុងការនាំចូល";
	}

	public String importadorConflictoMensaje() {
		return "មានឯកសាររួចហើយនៅកន្លែងដំណាក់។";
	}

	public String importadorRuta() {
		return "ផ្លូវ";
	}

	public String importadorArchivoExistente() {
		return "ឯកសារដែលមានស្រាប់";
	}

	public String importadorArchivoNuevo() {
		return "ឯកសារដែលបាននាំចូល";
	}

	public String importadorTamano() {
		return "ទំហំ";
	}

	public String importadorFecha() {
		return "ការកែប្រែចុងក្រោយ";
	}

	public String importadorInfoMod() {
		return "ព័ត៌មាន Mod";
	}

	public String importadorModImportadoMasNuevo() {
		return "Mod ដែលបាននាំចូលហាក់ដូចជាថ្មីជាង។";
	}

	public String importadorModImportadoMasViejo() {
		return "Mod ដែលបាននាំចូលហាក់ដូចជាចាស់ជាង។";
	}

	public String importadorBotonReemplazar() {
		return "ជំនួស";
	}

	public String importadorBotonSaltar() {
		return "រំលង";
	}

	public String importadorBotonRenombrar() {
		return "ប្តូរឈ្មោះថ្មី";
	}

	public String importadorModpackTitulo() {
		return "នាំចូល Modpack";
	}

	public String importadorModpackBotonSidebar() {
		return "នាំចូល";
	}

	public String importadorModpackDescripcion() {
		return "នាំចូល modpack ទៅកាន់ instance បច្ចុប្បន្ន។ អ្នកអាចអូសឯកសារ .zip, .mrpack ឬទម្រង់ផ្សេងទៀតដែលគាំទ្រ ឬជ្រើសរើសវាដោយដៃ។";
	}

	public String importadorModpackFormato() {
		return "ទម្រង់";
	}

	public String importadorModpackArrastraArchivo() {
		return "អូស modpack របស់អ្នកមកទីនេះ ឬជ្រើសរើសឯកសារ";
	}

	public String importadorModpackBotonSeleccionar() {
		return "ជ្រើសរើសឯកសារ";
	}

	public String importadorModpackBotonImportar() {
		return "នាំចូល";
	}

	public String importadorModpackSeleccionarArchivo() {
		return "ជ្រើសរើស Modpack";
	}

	public String importadorModpackEstadoListo() {
		return "រួចរាល់";
	}

	public String importadorModpackEstadoImportando() {
		return "កំពុងនាំចូល...";
	}

	public String importadorModpackEstadoError() {
		return "មានកំហុសក្នុងការនាំចូល។";
	}

	public String importadorModpackSinArchivo() {
		return "សូមជ្រើសរើសឯកសារ modpack ជាមុនសិន។";
	}

	public String importadorModpackFormatoNoSoportado() {
		return "ទម្រង់នេះមិនគាំទ្រការនាំចូលទេ។";
	}

	public String importadorModpackResultado(int copiados, int reemplazados, int saltados, int renombrados,
			int errores) {
		return "ការនាំចូលបានបញ្ចប់។\nបានចម្លង: " + copiados + "\nបានជំនួស: " + reemplazados + "\nបានរំលង: " + saltados
				+ "\nបានប្តូរឈ្មោះ: " + renombrados + "\nកំហុស: " + errores;
	}

	public String importadorModpackColorFondo() {
		return "ឧបករណ៍នាំចូល Modpack: ផ្ទៃ";
	}

	public String importadorModpackColorPanel() {
		return "ឧបករណ៍នាំចូល Modpack: ផ្ទាំង";
	}

	public String importadorModpackColorTexto() {
		return "ឧបករណ៍នាំចូល Modpack: អត្ថបទ";
	}

	public String importadorModpackColorTextoSuave() {
		return "ឧបករណ៍នាំចូល Modpack: អត្ថបទស្រាល";
	}

	public String importadorModpackColorBoton() {
		return "ឧបករណ៍នាំចូល Modpack: ប៊ូតុង";
	}

	public String importadorModpackColorBotonTexto() {
		return "ឧបករណ៍នាំចូល Modpack: អត្ថបទប៊ូតុង";
	}

	public String importadorModpackColorDrop() {
		return "ឧបករណ៍នាំចូល Modpack: តំបន់អូស";
	}

	public String importadorModpackColorBorde() {
		return "ឧបករណ៍នាំចូល Modpack: គែម";
	}

	public String jgitTituloIzzy() {
		return "មជ្ឈមណ្ឌល Git របស់ Izzy";
	}

	public String jgitRetratoIzzy() {
		return "Izzy";
	}

	public String jgitNoHayRetratoIzzy() {
		return "គ្មានរូបភាព Izzy";
	}

	public String jgitSeccionInstalacion() {
		return "ការដំឡើង JGit";
	}

	public String jgitAbrirCarpetaInstalacion() {
		return "បើកថតដំឡើង";
	}

	public String jgitAbrirPaginaDescarga() {
		return "បើកទំព័រ JGit";
	}

	public String jgitSeccionRepositorio() {
		return "ឃ្លាំងមូលដ្ឋាន";
	}

	public String jgitCrearRepositorioLocal() {
		return "បង្កើតឃ្លាំង Git នៅទីនេះ";
	}

	public String jgitCommitManual() {
		return "Commit ដោយដៃ";
	}

	public String jgitSeccionRemote() {
		return "Remote";
	}

	public String jgitForgeManual() {
		return "ដោយដៃ";
	}

	public String jgitForgePersonalizado() {
		return "Forge ផ្ទាល់ខ្លួន";
	}

	public String jgitEstablecerRemoteManual() {
		return "កំណត់ remote ដោយដៃ";
	}

	public String jgitCrearRemoteConAPI() {
		return "បង្កើត remote ជាមួយ API";
	}

	public String jgitPushManual() {
		return "Push ដោយដៃ";
	}

	public String jgitSeccionAutomaticos() {
		return "ស្វ័យប្រវត្តិកម្ម";
	}

	public String jgitAutoCommitDespuesBackup() {
		return "Commit ស្វ័យប្រវត្តិបន្ទាប់ពីបម្រុងទុក";
	}

	public String jgitAutoPushDespuesCommit() {
		return "Push ស្វ័យប្រវត្តិបន្ទាប់ពី commit";
	}

	public String jgitSeccionHerramientas() {
		return "ឧបករណ៍";
	}

	public String jgitAbrirGuiSwing() {
		return "បើកកម្មវិធីមើល Swing JGit";
	}

	public String jgitEstado() {
		return "ស្ថានភាព";
	}

	public String jgitClasspath() {
		return "JGit នៅក្នុង classpath";
	}

	public String jgitTodosLosArtefactos() {
		return "អាតិផាក់ JGit ទាំងអស់";
	}

	public String jgitRepositorio() {
		return "ឃ្លាំង";
	}

	public String jgitRemote() {
		return "Remote";
	}

	public String jgitCarpetaActual() {
		return "ថតបច្ចុប្បន្ន";
	}

	public String jgitNoSePudoCrearRepo() {
		return "មិនអាចបង្កើតឃ្លាំងបានទេ។";
	}

	public String jgitEscribaRemote() {
		return "សូមបញ្ចូល URL remote:";
	}

	public String jgitNoSePudoGuardarRemote() {
		return "មិនអាចរក្សាទុក remote បានទេ។";
	}

	public String jgitApiForgeAunNoImplementada() {
		return "API របស់ forge មិនទាន់ត្រូវបានអនុវត្តនៅឡើយទេ។";
	}

	public String jgitNoHayCambiosOError() {
		return "គ្មានការផ្លាស់ប្តូរសម្រាប់ commit ឬមានកំហុសកើតឡើង។";
	}

	public String jgitNoSePudoPush() {
		return "មិនអាច push បានទេ។";
	}

	public String jgitTituloVentanaSwing() {
		return "កម្មវិធីមើល Git";
	}

	public String jgitNoHayRepositorio() {
		return "គ្មានឃ្លាំង Git នៅក្នុងថតនេះទេ។";
	}

	public String jgitArchivosModificados() {
		return "ឯកសារដែលបានកែប្រែ";
	}

	public String jgitArchivosNuevos() {
		return "ឯកសារថ្មី";
	}

	public String jgitUltimosCommits() {
		return "Commits ចុងក្រោយ";
	}

	public String jgitError() {
		return "កំហុស JGit";
	}

	public String si() {
		return "បាទ/ចាស";
	}

	public String no() {
		return "ទេ";
	}

	public String jgitDescargarDependenciasFaltantes() {
		return "ទាញយកភាពអាស្រ័យដែលបាត់";
	}

	public String jgitNoFaltanDependencias() {
		return "គ្មានភាពអាស្រ័យ JGit ដែលបាត់ទេ។";
	}

	public String jgitConfirmarDescargaDependencias(int cantidad) {
		return "ខ្វះភាពអាស្រ័យ JGitចំនួន " + cantidad + "។ តើអ្នកចង់ទាញយកវាពី Maven Central ទេ?";
	}

	public String jgitDependenciasDescargadas(int cantidad) {
		return "ភាពអាស្រ័យបានទាញយក: " + cantidad;
	}

	public String jgitErroresDescarga() {
		return "កំហុសក្នុងការទាញយក";
	}

	public String jgitReiniciarParaCargarClasspath() {
		return "សូមចាប់ផ្តើម CrashDetector ឡើងវិញ ដើម្បីឱ្យឯកសារ JAR ថ្មីៗត្រូវបានបញ្ចូលទៅក្នុង classpath។";
	}

	public String jgitArtefactosFaltantes() {
		return "អាតិផាក់ដែលបាត់";
	}

	public String jgitArtefactosFaltantesClasspath() {
		return "អាតិផាក់ដែលបាត់នៅក្នុង classpath";
	}

	public String jgitArtefactosFaltantesCarpeta() {
		return "អាតិផាក់ដែលបាត់នៅក្នុងថតដំឡើង";
	}

	public String jgitDependenciasEnCarpeta() {
		return "ភាពអាស្រ័យដែលបានដំឡើងនៅក្នុងថត";
	}

	public String jgitForgeNoSeleccionada() {
		return "មិនមាន Forge ដែលបានជ្រើសរើសទេ។";
	}

	public String jgitForgeNoRegistrada(String id) {
		return "Forge មិនបានចុះបញ្ជីទេ: " + id;
	}

	public String jgitEscribaUrlForge() {
		return "URL Forge:";
	}

	public String jgitEscribaNombreRepositorio() {
		return "ឈ្មោះឃ្លាំង:";
	}

	public String jgitEscribaDescripcionRepositorio() {
		return "ការពិពណ៌នាឃ្លាំង:";
	}

	public String jgitEscribaNamespaceOpcional() {
		return "Namespace ជាជម្រើស:";
	}

	public String jgitEscribaTokenForge() {
		return "Token API Forge:";
	}

	public String jgitErrorCrearRemote() {
		return "កំហុសក្នុងការបង្កើត remote";
	}

	@Override
	public String mensajeControlifyRemoveReloadingScreen() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញភាពមិនឆបគ្នារវាង Controlify និង Remove Reloading Screen។</b>"
				+ "<p>ប្រវត្តិកំណត់ត្រា (Log) មានបន្ទាត់ <b>Attempted to fetch default config before DefaultConfigManager was ready!</b> "
				+ "និង <b>$rrls$init</b> ដែលជាធម្មតាបង្ហាញពីជម្លោះរវាង <b>Controlify</b> និង "
				+ "<b>Remove Reloading Screen</b>។</p>"
				+ "<p><b>មូលហេតុដែលអាចកើតមាន៖</b> Remove Reloading Screen កែប្រែផ្នែកខ្លះនៃអេក្រង់ផ្ទុក ឬដំណើរការផ្ទុក, "
				+ "ខណៈដែល Controlify ព្យាយាមចាប់ផ្តើមការកំណត់របស់វាមុនពេលប្រព័ន្ធបានរួចរាល់ជាស្ថាពរ។</p>"
				+ "<p><b>ជម្រើសដែលណែនាំ៖</b></p>" + "<ul>" + "<li>ដកចេញ <b>Remove Reloading Screen</b>។</li>"
				+ "<li>ឬធ្វើបច្ចុប្បន្នភាព <b>Controlify</b> និង <b>Remove Reloading Screen</b> ប្រសិនបើមានកំណែថ្មី។</li>"
				+ "<li>ប្រសិនបើបញ្ហានៅតែបន្ត សូមរក្សាទុក <b>Controlify</b> ហើយដកចេញ mod ណាដែលកែប្រែអេក្រង់ផ្ទុក។</li>"
				+ "</ul>" + "<p>Mod ដែលកែប្រែអេក្រង់ផ្ទុកជាញឹកញាប់បណ្តាលឱ្យមានភាពមិនឆបគ្នាជាមួយ mod ផ្សេងទៀត, "
				+ "ហើយជាធម្មតាផ្តល់នូវអត្ថប្រយោជន៍ជាក់ស្តែងតិចតួចបើធៀបនឹងបញ្ហាដែលវាអាចបណ្តាលឱ្យកើតឡើង។</p>";
	}

	@Override
	public String nombreControlifyRemoveReloadingScreen() {
		return "ភាពមិនឆបគ្នា: Controlify ប្រឆាំងនឹង Remove Reloading Screen";
	}

	@Override
	public String mensajeBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បញ្ហាដែលអាចកើតមានជាមួយ Biomes O' Plenty និងអង្គធាតុរាវផ្ទាល់ខ្លួន។</b>"
				+ "<p>ប្រវត្តិកំណត់ត្រា (Log) មានកំហុស <b>class org.joml.Vector4f cannot be cast to class "
				+ "net.minecraft.client.renderer.fog.FogData</b> ព្រមទាំងការយោងទៅលើ <b>Biomes O' Plenty</b>។</p>"
				+ "<p>នេះប្រហែលជាទាក់ទងនឹង <b>Biomes O' Plenty</b> ជាពិសេសជាមួយជីវចម្រុះ (biomes), អ័ព្ទ "
				+ "ឬអង្គធាតុរាវផ្ទាល់ខ្លួន។ ទោះយ៉ាងណាក៏ដោយ វាមិនច្បាស់លាស់ទេថា Biomes O' Plenty គឺជាមូលហេតុតែមួយគត់។</p>"
				+ "<p><b>ជម្រើសដែលណែនាំ៖</b></p>" + "<ul>"
				+ "<li>ព្យាយាមកែសម្រួលទិន្នន័យរបស់អ្នកលេង ដើម្បីផ្លាស់ទីពួកគេទៅកាន់ទីតាំងផ្សេងទៀតនៅក្នុងពិភពលោក។</li>"
				+ "<li>ព្យាយាមផ្ទុកពិភពលោកដោយគ្មាន <b>Biomes O' Plenty</b>។</li>"
				+ "<li>ប្រសិនបើពិភពលោកអាចផ្ទុកបានបន្ទាប់ពីផ្លាស់ទីអ្នកលេង នោះបញ្ហាប្រហែលជាកើតឡើងនៅក្នុងតំបន់ជាក់លាក់, "
				+ "ជីវចម្រុះជាក់លាក់ ឬអង្គធាតុរាវផ្ទាល់ខ្លួននៅជិតនោះ។</li>"
				+ "<li>អ្នកក៏អាចព្យាយាមធ្វើបច្ចុប្បន្នភាព <b>Biomes O' Plenty</b> និង mods ដែលទាក់ទងនឹងការបង្ហាញ, អ័ព្ទ, "
				+ "shaders ឬវិមាត្រ។</li>" + "</ul>"
				+ "<p>ប្រសិនបើការដកចេញ Biomes O' Plenty អនុញ្ញាតឱ្យហ្គេមចាប់ផ្តើម សូមពិនិត្យមើលថាតើអ្នកលេងស្ថិតនៅក្នុង ឬនៅជិតជីវចម្រុះ "
				+ "ឬអង្គធាតុរាវដែលបានបន្ថែមដោយ mod នោះដែរឬទេ។</p>";
	}

	@Override
	public String nombreBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "បញ្ហាដែលអាចកើតមាន: Biomes O' Plenty និង FogData";
	}

	@Override
	public String mensajeKotlinReflectionInternalErrorVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញកំហុសផ្ទៃក្នុងនៃការឆ្លុះបញ្ចាំង Kotlin។</b>"
				+ "<p>ប្រវត្តិកំណត់ត្រា (Log) មាន <b>kotlin.reflect.jvm.internal.KotlinReflectionInternalError</b> ជាមួយសារស្រដៀងនឹង "
				+ "<b>Property 'none' not resolved</b>។</p>"
				+ "<p>ប្រភេទកំហុសនេះជាទូទៅកើតឡើងជាមួយកំណែជាក់លាក់នៃ <b>Fabric Language Kotlin</b> / <b>Kotlin</b>។ "
				+ "ក្នុងករណីនេះ ថ្នាក់ពី <b>Inventory Profiles Next</b> បានលេចឡើង ប៉ុន្តែបញ្ហាដូចគ្នាក៏អាចកើតឡើងផងដែរ "
				+ "ជាមួយ mods ផ្សេងទៀតដែលប្រើ Kotlin។</p>" + "<p><b>ជម្រើសដែលណែនាំ៖</b></p>" + "<ul>"
				+ "<li>ធ្វើបច្ចុប្បន្នភាព <b>Fabric Language Kotlin</b> ទៅកាន់កំណែ <b>2.3.40</b> ប្រសិនបើវាមានសម្រាប់កំណែ Minecraft របស់អ្នក។</li>"
				+ "<li>ប្រសិនបើការធ្វើបច្ចុប្បន្នភាពមិនដំណើរការ សូមព្យាយាមបន្ថយ <b>Fabric Language Kotlin</b> ទៅកាន់កំណែ <b>2.3.10</b>។</li>"
				+ "<li>ធ្វើបច្ចុប្បន្នភាព <b>Inventory Profiles Next</b> ផងដែរ ប្រសិនបើ log បានលើកឡើងពីថ្នាក់របស់ mod នោះ។</li>"
				+ "<li>ប្រសិនបើកំហុសលេចឡើងជាមួយ mod ផ្សេងទៀត សូមពិនិត្យមើលថាតើ mod នោះពឹងផ្អែកលើ Kotlin ឬអត់ ហើយព្យាយាមផ្លាស់ប្តូរកំណែនៃ "
				+ "<b>Fabric Language Kotlin</b>។</li>" + "</ul>" + "<p>ឯកសារយោងបច្ចេកទេសដែលពាក់ព័ន្ធ៖ "
				+ "<a href='https://github.com/FabricMC/fabric-language-kotlin/issues/183'>បញ្ហា Fabric Language Kotlin #183</a>។</p>";
	}

	@Override
	public String nombreKotlinReflectionInternalErrorVersion() {
		return "កំហុស Kotlin: ការឆ្លុះបញ្ចាំងផ្ទៃក្នុង";
	}

	public String tituloEscanerMCreator() {
		return "ឧបករណ៍ស្កេន MCreator";
	}

	public String escanerMCreatorEscaneandoMods() {
		return "កំពុងស្កេន mod...";
	}

	public String escanerMCreatorPorFavorEspera() {
		return "សូមរង់ចាំ។";
	}

	public String escanerMCreatorResultadosAnalisis() {
		return "លទ្ធផលវិភាគ MCreator:";
	}

	public String escanerMCreatorNoSeEncontraronMods() {
		return "រកមិនឃើញ mod MCreator ទេ។";
	}

	public String escanerMCreatorEscaneoCompletado() {
		return "ការស្កេនបានបញ្ចប់។";
	}

	public String escanerMCreatorErrorDuranteEscaneo() {
		return "មានកំហុសក្នុងអំឡុងពេលស្កេន:";
	}

	public String escanerMCreatorCargando() {
		return "កំពុងផ្ទុក...";
	}

	public String escanerMCreatorCompletado() {
		return "បានបញ្ចប់";
	}

	public String escanerMCreatorError() {
		return "កំហុស";
	}

	// Khmer (ភាសាខ្មែរ)
	@Override
	public String textoNormal() {
		return "អត្ថបទធម្មតា";
	}

	@Override
	public String noSeEncontroConsolaParaArchivo() {
		return "មិនបានរកឃើញកុងសូលសម្រាប់ឯកសារ៖ ";
	}

	@Override
	public String lineaSeleccionadaEnLectador() {
		return "ជួរដែលបានជ្រើសរើសនៅក្នុងឧបករណ៍អាន៖ ";
	}

	// Khmer (ភាសាខ្មែរ)
	@Override
	public String mensajeMotionBlurBufferCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បញ្ហាដែលអាចកើតមានជាមួយ Motion Blur។</b>"
				+ "<p>កំណត់ហេតុមានការយោងទៅ <b>net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO</b> "
				+ "និងកំហុស <b>java.lang.IllegalStateException: Buffer already closed</b>។</p>"
				+ "<p>បន្ទាត់ទាំងនេះអាចលេចឡើងដាច់ដោយឡែកនៅក្នុងកំណត់ហេតុ ប៉ុន្តែជាមួយគ្នាវាជាធម្មតាបង្ហាញថាបញ្ហាទាក់ទង "
				+ "នឹង mod <b>Motion Blur</b> ឬការគ្រប់គ្រងរបស់វាលើ graphical shaders/buffers។</p>"
				+ "<p><b>ជម្រើសដែលបានណែនាំ៖</b></p>" + "<ul>"
				+ "<li>សាកល្បងចាប់ផ្តើមហ្គេមដោយគ្មាន <b>Motion Blur</b>។</li>"
				+ "<li>ប្រសិនបើហ្គេមចាប់ផ្តើមបានត្រឹមត្រូវដោយគ្មាន mod នោះ សូមរក្សាវាឱ្យនៅដោះឬស្វែងរកកំណែថ្មីជាងនេះ។</li>"
				+ "<li>អ្នកក៏អាចសាកល្បងដោយគ្មាន shaders ឬ mods rendering ផ្សេងទៀតប្រសិនបើបញ្ហានៅតែបន្ត។</li>" + "</ul>";
	}

	@Override
	public String nombreMotionBlurBufferCerrado() {
		return "បញ្ហាដែលអាចកើតមាន: Motion Blur";
	}

	// Khmer (ភាសាខ្មែរ)
	@Override
	public String mensajeGeneratorAcceleratorOwoVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ជំលោះដែលអាចកើតមានជាមួយ Generator Accelerator។</b>"
				+ "<p>កំណត់ហេតុមានភាពខុសគ្នារវាងហត្ថលេខា <b>Found</b> និង <b>Available</b> ព្រមទាំងថ្នាក់ពី "
				+ "<b>Generator Accelerator</b> ឧទាហរណ៍ <b>dev/sixik/generator_accelerator/common/features/FastTarget</b>។</p>"
				+ "<p>កំហុសនេះប្រហែលជាបណ្តាលមកពី <b>Generator Accelerator</b>។ វាក៏អាចទាក់ទង "
				+ "នឹងភាពមិនស៊ីសង្វាក់គ្នារវាង mod នោះ និងកំណែជាក់លាក់នៃ <b>owo-lib</b>។</p>"
				+ "<p><b>ជម្រើសដែលបានណែនាំ៖</b></p>" + "<ul>"
				+ "<li>សាកល្បងចាប់ផ្តើមហ្គេមដោយគ្មាន <b>Generator Accelerator</b>។</li>"
				+ "<li>ប្រសិនបើហ្គេមចាប់ផ្តើមបានត្រឹមត្រូវ សូមរក្សា mod នោះឱ្យនៅដោះ ឬស្វែងរកកំណែផ្សេង។</li>"
				+ "<li>សាកល្បងធ្វើបច្ចុប្បន្នភាព ឬផ្លាស់ប្តូរកំណែនៃ <b>owo-lib</b> ជាពិសេសប្រសិនបើ mods ផ្សេងទៀតក៏ពឹងផ្អែកលើ owo ដែរ។</li>"
				+ "<li>ផ្ទៀងផ្ទាត់ថា <b>Generator Accelerator</b>, <b>owo-lib</b>, loader និងកំណែ Minecraft ស៊ីសង្វាក់គ្នា។</li>"
				+ "</ul>"
				+ "<p>មូលហេតុដែលទំនងបំផុតគឺ Generator Accelerator កំពុងព្យាយាមអនុវត្តការកែប្រែដោយប្រើហត្ថលេខា "
				+ "ដែលមិនត្រូវគ្នានឹងកំណែបច្ចុប្បន្ននៃថ្នាក់ ឬភាពអាស្រ័យ។</p>";
	}

	@Override
	public String nombreGeneratorAcceleratorOwoVersion() {
		return "ជំលោះដែលអាចកើតមាន: Generator Accelerator និង owo-lib";
	}

	// Khmer (ភាសាខ្មែរ)
	@Override
	public String mensajeFabricRenderingApiFaltaIndium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ខ្វះ renderer ដែលស៊ីសង្វាក់គ្នាជាមួយ Fabric Rendering API។</b>"
				+ "<p>កំណត់ហេតុមានកំហុសដែល <b>RendererAccess.getRenderer()</b> ត្រឡប់ <b>null</b> វិញ, "
				+ "បណ្តាលឱ្យមានការបរាជ័យនៅពេលព្យាយាមប្រើ <b>Renderer.materialFinder()</b>។</p>"
				+ "<p>បញ្ហានេះជាធម្មតាកើតឡើងនៅពេលដែល <b>Fabric Rendering API</b> មិនអាចប្រើបានត្រឹមត្រូវ។ "
				+ "មូលហេតុទូទៅគឺការប្រើប្រាស់ <b>Sodium</b> ជាពិសេសកំណែចាស់ៗដែលជំនួស ឬបិទដំណើរការផ្នែកខ្លះ "
				+ "នៃប្រព័ន្ធបង្ហាញដែល mods ផ្សេងទៀតរំពឹងថានឹងមាន។</p>" + "<p><b>ដំណោះស្រាយដែលបានណែនាំ៖</b></p>"
				+ "<ul>" + "<li>ដំឡើង mod <b>Indium</b>។</li>"
				+ "<li>ធានាថា <b>Indium</b> ស៊ីសង្វាក់គ្នាជាមួយកំណែ <b>Sodium</b>, Fabric Loader និង Minecraft របស់អ្នក។</li>"
				+ "<li>ប្រសិនបើអ្នកបានដំឡើង Indium រួចហើយ សូមធ្វើបច្ចុប្បន្នភាព <b>Sodium</b>, <b>Indium</b> និង <b>Fabric API</b>។</li>"
				+ "<li>ប្រសិនបើបញ្ហានៅតែបន្ត សូមសាកល្បងបណ្តោះអាសន្នដោយគ្មាន Sodium ដើម្បីបញ្ជាក់ថាកំហុសទាក់ទងនឹង renderer។</li>"
				+ "</ul>"
				+ "<p>Indium ជាធម្មតាស្តារភាពស៊ីសង្វាក់គ្នាជាមួយ Fabric Rendering API សម្រាប់ mods ដែលពឹងផ្អែកលើប្រព័ន្ធនោះ "
				+ "ខណៈពេលដែល Sodium ត្រូវបានដំឡើង។</p>";
	}

	@Override
	public String nombreFabricRenderingApiFaltaIndium() {
		return "ខ្វះ Indium / Fabric Rendering API";
	}

	// Khmer (ភាសាខ្មែរ)
	@Override
	public String mensajeEntradaDuplicadaIdModerno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "បានរកឃើញការបញ្ចូលទ្វេដងនៅក្នុងបញ្ជីឈ្មោះរបស់ Minecraft។</b>"
				+ "<p>កំណត់ហេតុមានកំហុសស្រដៀងនឹង <b>Duplicate entry on id</b> ឧទាហរណ៍ "
				+ "<b>current=maroon, previous=mint</b>។</p>"
				+ "<p>នៅក្នុងកំណែថ្មីៗនៃ Minecraft ប្រភេទកំហុសនេះជាធម្មតាកើតឡើងនៅពេលដែល mods ពីរព្យាយាមចុះបញ្ជី "
				+ "ការបញ្ចូលផ្សេងគ្នាដោយប្រើ ID ខាងក្នុងតែមួយ។</p>" + "<p><b>ជម្រើសដែលបានណែនាំ៖</b></p>" + "<ul>"
				+ "<li>លុប mod មួយក្នុងចំណោម mods ដែលកំពុងចុះបញ្ជីការបញ្ចូលទ្វេដង។</li>"
				+ "<li>ប្រសិនបើអ្នកស្គាល់ឈ្មោះដែលបានលើកឡើងនៅក្នុងកំហុស សូមពិនិត្យមើលថា mod ណាដែលបន្ថែមការបញ្ចូលទាំងនោះ ហើយសាកល្បងដោយគ្មាន mod នោះ។</li>"
				+ "<li>ប្រសិនបើអ្នកមិនស្គាល់ឈ្មោះទេ សូមប្រើឧបករណ៍ <b>grepr</b> នៅក្នុងរបារចំហៀង។</li>"
				+ "<li>នៅក្នុង <b>grepr</b> សូមបើកការស្វែងរកនៅក្នុងឯកសារដែលបានបង្ហាប់ <b>.jar</b>, <b>.zip</b> និង <b>.fpm</b>។</li>"
				+ "<li>បើកការស្វែងរកនៅក្នុង <b>ឯកសារ binary</b> ផងដែរ ព្រោះឈ្មោះ ឬ ID មួយចំនួនអាចស្ថិតនៅក្នុងថ្នាក់ដែលបានបង្កើតរួច។</li>"
				+ "</ul>"
				+ "<li>ស្វែងរកតម្លៃដែលបានលើកឡើងនៅក្នុងកំហុស ដូចជា <b>maroon</b> ឬ <b>mint</b> ដើម្បីរក mod ដែលមានវា។</li>";
	}

	@Override
	public String nombreEntradaDuplicadaIdModerno() {
		return "ការបញ្ចូលទ្វេដងនៅក្នុង ID mod";
	}

}
