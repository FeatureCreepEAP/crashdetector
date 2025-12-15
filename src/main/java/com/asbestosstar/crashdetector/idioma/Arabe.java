package com.asbestosstar.crashdetector.idioma;

import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Arabe implements Idioma {
	private final Config config = Config.obtenerInstancia();

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>مجلد mods غير صالح</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>لا يمكن إيجاد ملف JAR الخاص بـCrashDetector</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>البحث عن PID: " + String.valueOf(pid)
				+ "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") انتهى!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>لا يوجد JVM</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>قد يساعدك تحديث برامج تشغيل ATI/AMD الخاصة بك. اقرأ هذا الدليل لحل المشكلة: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>دليل تحديث التعريفات</a> https://www.amd.com/es/support/download/drivers.html تنزيل </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>بعض الإصدارات القديمة تواجه أحيانًا مشكلات مع واجهة تحميل Nouveau المبكرة.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>هناك مشكلة في تعريفات بطاقة الرسوميات الخاصة بك. إذا كنت تستخدم GPU/APU AMD/ATI، يرجى تحديث تعريفات AMD. إذا كنت تستخدم NVIDIA، تأكد من ضبط جميع حالات javaw.exe واللعبة لاستخدام البطاقة المستقلة. اقرأ هذا الدليل: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>دليل تحديث التعريفات</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>فشل تحميل نافذة FML المبكرة. لإصلاح هذا، انتقل إلى (.minecraft/config/fml.toml) وقم بتعيين earlyWindowProvider إلى \"none\". إذا كنت تستخدم Mac M1، تأكد من استخدام Java ARM وليس Intel x64. هذه مشكلة شائعة أيضًا مع التعريفات القديمة. إذا كنت تستخدم Windows ولم يفلح تعطيل هذا الإعداد، يرجى الرجوع إلى هذا الدليل: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>دليل تحديث التعريفات</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError() + "'>التبعيات المطلوبة مفقودة:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "طلب بواسطة").replace("Expected range", "النطاق المتوقع") + "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>تقرير CrashDetector الخاص بك هنا <a href='"
				+ archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>عرض التقرير</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>هذه هي واجهة المستخدم الرسومية لـCrashDetector. إذا أغلقت اللعبة بشكل طبيعي، يرجى تجاهل هذه الواجهة.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>عرض التقرير</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>عرض التقرير المحلي في المتصفح.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "مشاركة التقرير";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "مشاركة التقرير. سيتم تحميل سجلاتك إلى securelogger.net والتقرير إلى مواقع أخرى.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>تم اكتشاف ملفات JAR مشكلة (الأولوية للـFATAL ثم عالية ومنخفضة):</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> المستوى:</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>إمكانية أن يكون قاتلاً:</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>تم اكتشاف ModID مشكلة (الأولوية للـFATAL ثم عالية ومنخفضة):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>تم اكتشاف حزم مشكلة (الأولوية للـFATAL ثم عالية ومنخفضة):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لديك فئات قاتلة (FATAL)، وهي مشكلة خطيرة. الأسباب الشائعة تشمل CoreMods تالفة أو تبعيات قاتلة. يمكنك استخدام QuickFix للبحث عن المودات التي تحتوي على فئات قاتلة. الفئات القاتلة المفقودة التي تم اكتشافها:</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>المحتوى داخل {} (الأهم في الأعلى، يعرض أول 20 فقط):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError() + "'>تم اكتشاف تكوين SpongeMixin مشكلة: " + "</b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>لديك تعديلات تحتوي على حزم مكررة. يمكنك إصلاح هذا عن طريق حذف الحزمة (المجلد) من ملف الـ JAR، يمكنك فتح ملف الـ JAR باستخدام برنامج أرشيف مثل WinRAR أو 7z، كما يمكنك أيضًا تغيير امتداد الملف من .jar إلى zip ثم حذف المجلد ومن ثم إعادة تسميته مرة أخرى إلى ملف .jar.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>تم اكتشاف Mods مكررة</b> "
				+ linea.replace("from mod files", "من ملفات mod");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge اكتشف mod مشكوك فيه:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV يحتاج إلى lithostitched، يمكنك تثبيته من هنا: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لاستخدام Iris shaders أو Oculus، تحتاج إلى إصدار متوافق من SODIUM أو محمل آخر (Rubidium, Embedium, Bedium)</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>مشكلة في امتداد KubeJS </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "تم اكتشاف مشكلات مع برامج تشغيل NVIDIA في الإصدارات السابقة من Windows 11." + "</span><br/><br/>"
				+ "لضمان أن Minecraft (والـ JVM الحالية) يستخدمان وحدة معالجة الرسوميات المخصصة NVIDIA، اتبع الخطوات التالية:<br/><br/>"
				+ "1. <strong>حدد ملف تنفيذي Java:</strong><br/>"
				+ "   - يستخدم هذا البرنامج الملف التنفيذي التالي لـ Java: " + obtenerRutaJava() + "<br/>"
				+ "   - إذا لم ترَ مسارًا محددًا، يمكنك العثور على الملف التنفيذي لـ Java بالبحث عن 'java.exe' في نظامك.<br/><br/>"
				+ "2. <strong>افتح لوحة تحكم NVIDIA:</strong><br/>"
				+ "   - انقر بزر الماوس الأيمن على سطح المكتب وحدد 'لوحة تحكم NVIDIA'.<br/><br/>"
				+ "3. <strong>قم بتكوين وحدة معالجة الرسوميات المفضلة:</strong><br/>"
				+ "   - في لوحة تحكم NVIDIA، انتقل إلى 'إدارة إعدادات 3D'.<br/>"
				+ "   - حدد خيار 'إعدادات برنامج محدد'.<br/>"
				+ "   - انقر على 'إضافة' وابحث عن الملف التنفيذي لـ Java الذي تم تحديده سابقًا (مثل: 'java.exe').<br/>"
				+ "   - تأكد من أنه مُعد لاستخدام 'معالج عالي الأداء (NVIDIA)'.<br/><br/>"
				+ "4. <strong>احفظ التغييرات:</strong><br/>" + "   - طبق التغييرات وأغلق لوحة تحكم NVIDIA.<br/><br/>"
				+ "5. <strong>أعد تشغيل Minecraft:</strong><br/>"
				+ "   - أعد تشغيل Minecraft لتصبح التغييرات نافذة.<br/><br/>"
				+ "إذا كنت تستخدم Windows Server 2022 أو Windows 10، فإن هذه الخطوات صالحة طالما لديك أحدث برامج تشغيل NVIDIA مثبتة.<br/><br/>"
				+ "ملاحظة: إذا لم تتمكن من العثور على لوحة تحكم NVIDIA، تأكد من أن برامج تشغيل NVIDIA مثبتة بشكل صحيح.";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "تم اكتشاف مشكلات مع برامج تشغيل NVIDIA في Windows 11/Server 2025 أو أحدث." + "</span><br/><br/>"
				+ "لضمان أن Minecraft (والـ JVM الحالية) يستخدمان وحدة معالجة الرسوميات المخصصة NVIDIA، اتبع الخطوات التالية:<br/><br/>"
				+ "1. <strong>حدد ملف تنفيذي Java:</strong><br/>"
				+ "   - يستخدم هذا البرنامج الملف التنفيذي التالي لـ Java: " + obtenerRutaJava() + "<br/>"
				+ "   - إذا لم ترَ مسارًا محددًا، يمكنك العثور على الملف التنفيذي لـ Java بالبحث عن 'java.exe' في نظامك.<br/><br/>"
				+ "2. <strong>افتح تطبيق الإعدادات:</strong><br/>"
				+ "   - اضغط على المفاتيح <code>Win + I</code> لفتح تطبيق الإعدادات.<br/>"
				+ "   - انتقل إلى <strong>النظام > الشاشة > الرسوميات</strong>.<br/><br/>"
				+ "3. <strong>قم بتكوين وحدة معالجة الرسوميات المفضلة:</strong><br/>"
				+ "   - في قسم 'الرسوميات', انقر على 'إعدادات الرسوميات الافتراضية'.<br/>"
				+ "   - حدد 'تطبيقات سطح المكتب' ثم انقر على 'استعراض'.<br/>"
				+ "   - ابحث وحدد الملف التنفيذي لـ Java الذي تم تحديده سابقًا (مثل: 'java.exe').<br/>"
				+ "   - بمجرد الإضافة، حدد تطبيق Java في القائمة وقم بتكوينه لاستخدام 'الأداء العالي (NVIDIA)'.<br/><br/>"
				+ "4. <strong>احفظ التغييرات:</strong><br/>" + "   - طبق التغييرات وأغلق تطبيق الإعدادات.<br/><br/>"
				+ "5. <strong>أعد تشغيل Minecraft:</strong><br/>"
				+ "   - أعد تشغيل Minecraft لتصبح التغييرات نافذة.<br/><br/>"
				+ "إذا كنت تستخدم Windows 11 أو Windows Server 2025+، فإن هذه الخطوات صالحة طالما لديك أحدث برامج تشغيل NVIDIA مثبتة.<br/><br/>"
				+ "ملاحظة: إذا لم تتمكن من العثور على خيار إعدادات الرسوميات، تأكد من أن برامج تشغيل NVIDIA مثبتة بشكل صحيح.";
	}

	@Override
	public String segundo60Tick() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>خادمك أو عالمك لديه تicks تتجاوز 60 ثانية. قد يكون ذلك بسبب التعديلات التي تبطئ الخادم أو أن الأجهزة ضعيفة جدًا.</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لا تملك ذاكرة RAM/ذاكرة كافية. تحتاج إلى تخصيص المزيد.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>يواجه Theseus أيضًا مشاكل أخرى، بما في ذلك فشله في إزالة التعديلات عند محاولة ذلك. إذا كنت بحاجة إلى تشغيل ملفات mrpack، يمكنك استخدام مشغلات أخرى مثل Prism Launcher (مخصص لـ modrinth.com فقط)، ATLauncher (مخصص لـ modrinth.com فقط)، أو Hello Minecraft Launcher (يدعم modrinth.com و bbsmc.net).</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>ليس لديك ملف launcher_log.txt. نحن بحاجة إلى هذا الملف للبحث عن الأخطاء. وهذا بسبب خيار \"تخطي بدء تشغيل المشغل\". قم بتعطيله.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>تحذير: تم اكتشاف فئات مفقودة (تحذير). عادةً ليست خطيرة، ولكنها قد تسبب مشاكل — تختلف عن الأخطاء القاتلة. الأسباب الشائعة تشمل CoreMods تالفة أو تبعيات مفقودة. يمكنك استخدام QuickFix للبحث عن المودات التي تحتوي على فئات ناقصة. الفئات المفقودة المكتشفة:</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>لا نتائج</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>مواقع السجلات:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>إليك نتائج عمليات التحقق الخاصة بك. اعمل ببطء؛ عادةً، السبب الصحيح يكون في الفحص الأول أو الثاني. يمكنك استخدام البقية (الأخطاء من 3 فما فوق) للتأكيد، لكنها غالبًا أخطاء متسلسلة يمكن تجاهلها. الأعطال تحدث بشكل طبقي، لذا فإن إصلاح المشكلة الأساسية سيحل هذا الخطأ المحدد. ومع ذلك، قد يظهر غدًا خطأ جديد غير مرتبط بالخطأ الحالي، لأن خطأً واحدًا غالبًا ما يمنع ظهور آخر في وحدة التحكم.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>يرجى استخدام Java 17 للإصدارات 1.17-1.20.4 وJava 21 لأي إصدار أحدث. استخدم Java 8 لأي إصدار أقدم. [دليل](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). إذا كنت لا تزال تواجه مشاكل، فقد يكون ذلك لأن بعض التعديلات تحتوي على ملفات قديمة جدًا أو جديدة جدًا.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 وما فوقه لا يعمل على إصدارات Minecraft الأقل من 1.20.5 لمعظم مُحمّلات التعديلات بسبب أن ASM قديم.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java قديم </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>تحتاج إلى وحدة JPMS " + mod_necesitas + " من "
				+ submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>لا يمكن استدعاء " + metodo + " لأن " + objeto
				+ " هو قيمة خالية</b>";
	}

	@Override
	public String analisisAvanzado() {
		return "التحليل المتقدم";
	}

	@Override
	public String seleccionarCarpeta() {
		return "اختيار المجلد";
	}

	@Override
	public String cadenaBusqueda() {
		return "سلسلة البحث";
	}

	@Override
	public String usarRegex() {
		return "استخدام التعبيرات النمطية";
	}

	@Override
	public String ignorarMayusculas() {
		return "تجاهل حالة الأحرف";
	}

	@Override
	public String buscar() {
		return "بحث";
	}

	@Override
	public String busquedaEnProgreso() {
		return "البحث قيد التنفيذ";
	}

	@Override
	public String noSeEncontraronResultados() {
		return "لم يتم العثور على نتائج";
	}

	@Override
	public String errorBusqueda() {
		return "خطأ في البحث";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "!لم يتم العثور على سجلات المشغل. قد يؤدي هذا إلى تعقيد التحقيق\n" + "                \n"
				+ "                للحصول على السجلات الصحيحة:\n"
				+ "                - MultiMC/PolyMC/PrismLauncher/: ملاحظة: السجلات المكتشفة تلقائيًا ليست صحيحة.\n"
				+ "                  1. افتح واجهة المثيل\n" + "                  2. انتقل إلى قسم \"Minecraft Log\"\n"
				+ "                  3. انقر بزر الماوس الأيمن وانسخ المحتوى\n" + "                - CurseForgeApp:\n"
				+ "                  1. أعد تشغيل اللعبة دون تخطي المشغل\n" + "                  \n";
	}

	@Override
	public String omitirYCerrar() {
		return "تخطي وإغلاق";
	}

	@Override
	public String guardarYCerrar() {
		return "حفظ وإغلاق";
	}

	@Override
	public String pegaLosRegistrosAqui() {
		return "الصق السجلات هنا";
	}

	@Override
	public String archivo() {
		return "ملف";
	}

	@Override
	public String incluir() {
		return "تضمين";
	}

	@Override
	public String abrir() {
		return "فتح";
	}

	@Override
	public String endpointDeInforme() {
		return "نقطة نهاية التقرير";
	}

	@Override
	public String sitoDeLogging() {
		return "موقع تسجيل:";
	}

	@Override
	public String apiDeLogging() {
		return "واجهة برمجة تطبيقات تسجيل:";
	}

	@Override
	public String anonimizarRegistros() {
		return "إخفاء هوية السجلات (بيتا)";
	}

	@Override
	public String botonDeCompartirInforme() {
		return "مشاركة التقرير وجميع السجلات المحددة";
	}

	@Override
	public String arco() {
		return "يتيح لك هذا الحوار مشاركة السجلات باستخدام واجهة برمجة تطبيقات SecureLogger "
				+ "على securelogger.net. عند الضغط على زر مشاركة التقرير، يتم إرسال تقريرك إلى "
				+ "نقطة النهاية المحددة (افتراضيًا asbestosstar.egoism.jp) (يمكن تغييرها في الأسفل). يمكنك مشاركة جميع السجلات المحددة "
				+ "مع التقرير. إذا كنت لا ترغب في الرفع، فلا تستخدم هذا الحوار! لا نقوم بمعالجة تقريرك في نقطة النهاية الرسمية (https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb)؛ نحن فقط نزيل الروابط غير المصرح بها. الكود موجود هنا: https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb. يُستخدم هذا فقط لعرض معلومات حول تعطل نظامك والرابط إلى السجلات. ومع ذلك، من الممكن استخدام نقطة نهاية مخصصة قد لا تحتوي على نفس الطرق. أنت تستخدم موقع التقارير "
				+ Config.obtenerInstancia().obtenerSitoDeInformes() + " وموقع السجلات "
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado()
				+ ". يمكنك أيضًا مشاركة السجلات الفردية بدون تقرير عن طريق الضغط على أزرار المشاركة بجانب أسماء السجلات الفردية؛ سيتم إرسال السجلات إلى موقع السجلات المحدد. يحتوي CrashDetector على تجهيل افتراضي للسجلات، والذي يحاول إزالة أسماء المستخدمين، UUIDs، رموز الوصول، معرّفات الجلسة، عناوين IP، وغيرها من البيانات. ومع ذلك، فهو ليس مثاليًا. ومع ذلك، يمكن للمؤلف الحزمة تعطيله. يمكن تمكينه أو تعطيله باستخدام خانة الاختيار في الجزء السفلي من هذه الشاشة. أنت المتحكم في بياناتك الخاصة؛ أنت تقرر أين تقوم بتحميل بياناتك. مواقع السجلات مملوكة لأطراف ثالثة غالبًا ما تكون ملكيتها مخفية لأسباب تتعلق بالخصوصية. أنت تتحمل المسؤولية الكاملة لإدارة بياناتك والمخاطر المتعلقة بها. حوار مشاركة CrashDetector هو مجرد واجهة تتيح لك إدارة ذلك. من المهم أن تكون على دراية بـ GDPR وARCO.";
	}

	@Override
	public String enlaceDelReporte() {
		return "رابط التقرير:";
	}

	@Override
	public String guardarConfigDeCompartir() {
		return "حفظ إعدادات المشاركة";
	}

	@Override
	public String registroDemasiadoGrande() {
		return "السجل كبير جدًا لهذا الموقع. يرجى اختيار موقع آخر والمحاولة مرة أخرى.";
	}

	@Override
	public String errorConPublicarRegistro(String error) {
		return "خطأ في نشر السجل " + error;
	}

	@Override
	public String apiDeRegistroNoExiste() {
		return "واجهة برمجة تطبيقات السجل غير موجودة. يرجى تغيير واجهة برمجة التطبيقات في الإعدادات.";
	}

	@Override
	public String errorSSL() {
		return "لديك خطأ في بروتوكول طبقة المقابس الآمنة (SSL). هذا شائع مع إصدارات قديمة من Java، "
				+ "بما في ذلك إصدارات Java 8 في مشغل Minecraft الافتراضي والإصدارات الموجودة على sun.com و java.com. "
				+ "يؤثر هذا على العديد من الجوانب، مثل ملفات JAR الخاصة بمثبت MinecraftForge، "
				+ "وظيفة مشاركة تقارير CrashDetector عند استخدام نقطة النهاية الافتراضية، وبعض الإضافات التي تتطلب الإنترنت "
				+ "وبعض مواقع التسجيل. إذا حدث هذا لك أثناء محاولة مشاركة تقرير، "
				+ "ما عليك سوى إرفاق لقطة شاشة واختيار موقع تسجيل متوافق مع إصدارات Java 8 القديمة.";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "إصدار JavaFML غير متوافق: مطلوب الإصدار "
				+ requerido + "، تم اكتشاف " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "انتباه! JavaFML يتطلب إصدارًا محددًا من Minecraft Forge</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ملف JAR '" + archivoJar
				+ "' يتطلب مزود اللغة '" + proveedor + "' الإصدار '" + requerido
				+ "' أو أحدث، ولكن تم العثور فقط على الإصدار '" + encontrado + "'.</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تنبيه! Crash Assistant هو كاشف برامج ضارة مزيف. يقوم بحظر اللعبة عن التشغيل بشكل متعمد، مع تجاهل حريتك في متابعة اللعب باستخدام التعديلات التي يستهدفها. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>عرض كود MalwareMod.java</a>   "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>عرض كود JarInJarHelper.java</a>. "
				+ "هذا التعديل فقط هو الموجود في قائمتهم حالياً، وهم يستهدفون فقط موقع تسجيل السجلات الافتراضي، والذي يمكن للمستخدم تغييره، وهذا يحدث فقط إذا اخترت صراحة استخدام ميزة مشاركة السجلات المدمجة. CrashAssistant لا يقوم بأي فحوصات لتحديد موقع التسجيل المستخدم ولا يشرح كيفية تغييره (هناك قائمة منسدلة في أسفل مربع الحوار الخاص بالمشاركة)، وبغض النظر عن الموقع الذي قمت بتكوينه، فإن CrashAssistant سيمنع تشغيل اللعبة. في رسالتهم يقولون أن تقوم بإجراء بحثك الخاص، افعل ذلك، انظر إلى كود CrashDetector وCrash Assistant وافهم ما يقومون به، لا تعتمد على استدعاء السلطة.</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "فشل المود '" + idMod
				+ "' لأن الصنف المطلوب لم يتم العثور عليه: '" + claseNoEncontrada
				+ "'. تأكد من أن جميع التبعيات مثبتة بشكل صحيح.</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Watermedia قد حظر اللعب باستخدام TLauncher.</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "أنت تستخدم إصدارًا من Optifine لنسخة قديمة من Minecraft. تحتاج إلى استخدام إصدار Optifine المخصص للإصدار الذي تستخدمه من Minecraft.</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "لم يتمكن من تحميل خدمة ModLauncher: </b>"
				+ servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطأ في تحليل ملف JSON '" + recurso
				+ "' من ملف JAR '" + archivoJar + "'. هناك مشاكل في التسجيل.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "خطأ: المود '" + modId + "' يتطلب الإصدار '"
				+ requerido + "' أو أحدث من '" + dependencia + "'، لكن تم العثور على '" + actual + "'." + "</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "بطاقة الرسومات الخاصة بك لا تدعم إصدار OpenGL المطلوب لهذه نسخة اللعبة. قم بتحديث برامج التشغيل الخاص بك أو استبدل بطاقة الرسومات الخاصة بك.</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "قم بزيادة الذاكرة المخصصة للعبة أو قلل من استخدام التعديلات/الإضافات</b>";
	}

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "تم اكتشاف JVM بـ 32 بت: لا يمكن استخدام أكثر من 4 جيجابايت من ذاكرة الوصول العشوائي. "
				+ "قم بتثبيت JVM بـ 64 بت للاستفادة من كل ذاكرتك المتاحة</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطأ حرج في ذاكرة PermGen. قم بزيادة مساحة الذاكرة الدائمة أو قلل من تحميل الفئات</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطأ في التوافق بين Java 8 والإصدارات الحديثة</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+ غير مدعوم - استخدم Java 8 لنسخ Forge القديمة</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 8 مطلوب (الإصدار 52.0). قم بالتحديث أو التهيئة بشكل صحيح</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطأ حرج في التوافق: الكتل غير مدعومة في هذه النسخة. "
				+ "تأكد من أن إصدارات التعديلات والخادم متوافقة</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطأ في تكوين الشاشات: "
				+ "تعذر تعيين وضع الشاشة. " + "تحقق من:</b>" + "<br>- تكوين شاشات متعددة"
				+ "<br>- برامج تشغيل بطاقة الرسوميات المحدثة" + "<br>- الدقة المدعومة من النظام";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطأ في خيارات Java: "
				+ "خيارات جامع القمامة متعارضة. " + "تأكد من عدم دمج عدة خوارزميات GC في معلمات JVM</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطأ حرج في تكوين Forge: "
				+ "ملف التكوين تالف أو غير مكتمل. " + "احذف مجلد 'config' وأعد تشغيل اللعبة</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تم اكتشاف خطأ في برنامج تشغيل Intel HD Graphics. الحلول:</b>"
				+ "<br>1. قم بتحديث برامج تشغيل Intel من <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a> (الإصدار الأدنى 15.xx.xx.xx)"
				+ "<br>2. في Minecraft: الخيارات → الفيديو → قم بتفعيل 'Enable VBOs' و 'VSync'"
				+ "<br>3. خصص 1GB-2GB من ذاكرة الوصول العشوائي للعبة في المشغل"
				+ "<br>4. قم بإيقاف تشغيل مضاد الفيروسات/جدار الحماية مؤقتًا أثناء التحديث";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "تحذير: اكتشاف فئات مفقودة";
	}

	public String nombre_de_bloque_teselado() {
		return "خطأ في عرض الكتل";
	}

	public String nombre_de_contenido_de_stacktrace() {
		return "تحليل سجل الأخطاء (stack trace)";
	}

	public String nombre_de_cursed_consola() {
		return "وحدة تحكم CurseForge غير مكتملة";
	}

	public String nombre_de_early_window() {
		return "خطأ نافذة مبكرة (FMLEarlyWindow)";
	}

	public String nombre_de_drivers() {
		return "مشكلات برامج تشغيل الفيديو";
	}

	public String nombre_de_error_de_config_mcforge() {
		return "تكوين MCForge تالف";
	}

	public String nombre_de_error_de_monitor_lwjgl() {
		return "فشل وضع العرض (LWJGL)";
	}

	public String nombre_de_fabricmc_runtime_error_provided_by() {
		return "خطأ بدء تشغيل FabricMC";
	}

	public String nombre_de_falta_module_jmps() {
		return "وحدات JPMS مفقودة";
	}

	public String nombre_de_faltar_de_clases() {
		return "فئات مفقودة حرجة";
	}

	public String nombre_de_faltas_dependencias_de_modlauncher() {
		return "تراكمات ModLauncher مفقودة";
	}

	public String nombre_de_java_versiones() {
		return "إصدارات Java غير متوافقة";
	}

	public String nombre_de_faltar_de_kubejs_resourcepack() {
		return "خطأ موارد KubeJS";
	}

	public String nombre_de_lenguaje_proveedor_check() {
		return "مزود اللغة غير متوافق";
	}

	public String nombre_de_faltar_de_liyhostictchctov() {
		return "خطأ Litchhost";
	}

	public String nombre_de_malware_falso_crash_assistant() {
		return "اكتشاف برامج ضارة كاذبة";
	}

	public String nombre_de_mcforge_mod_sespechoso() {
		return "اكتشاف تعديل مشبوه";
	}

	public String nombre_de_mods_duplicados_modlauncher() {
		return "تعديلات مكررة في ModLauncher";
	}

	public String nombre_de_modules_duplicados_jmps() {
		return "تعارض وحدات JPMS";
	}

	public String nombre_de_necesitas_sodium() {
		return "يتطلب Sodium لـ Iris";
	}

	public String nombre_de_no_puede_analizar_json_de_registro() {
		return "فشل في تحليل ملف JSON للسجل";
	}

	public String nombre_de_no_tiene_memoria() {
		return "ذاكرة غير كافية";
	}

	public String nombre_de_null_pointer() {
		return "خطأ مؤشر فارغ (NullPointerException)";
	}

	public String nombre_de_opciones_java_gc_invalidas() {
		return "خيارات جمع القمامة (GC) غير صالحة";
	}

	public String nombre_de_optifine_obsoleta() {
		return "OptiFine قديمة/غير متوافقة";
	}

	public String nombre_de_60_segundo_trick() {
		return "نقرة خادم حرجة (60 ثانية)";
	}

	public String nombre_de_servicio_de_modlauncher_no_funciona() {
		return "خدمات ModLauncher معطلة";
	}

	public String nombre_de_spongemixin_configs_problematicos() {
		return "تكوينات SpongeMixing مشكلة";
	}

	public String nombre_de_theseus() {
		return "Theseus غير متوافق";
	}

	public String nombre_de_watermedia_tl() {
		return "TLauncher غير مدعوم من WATERMeDIA";
	}

	@Override
	public String auditorias_transformer() {
		return "تدقيقات التحويل";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "هذه هي نتائج محتويات تدقيقات التحويل في مشغل الفانيليا. عادةً ما تكون أقل دقة من محلل StackTrace، لكن مشغل الفانيليا لا يحتوي دائمًا على محتوى لـ {}</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "يبحث هذا في إضافاتك عن الإضافات المصنوعة باستخدام MCreator. على الرغم من أن معظم إضافات MCreator جيدة وهناك العديد من الإضافات الرائعة التي تم إنشاؤها باستخدام MCreator، إلا أنها أحيانًا تحتوي على مشاكل ولديها سمعة سيئة. هذا يساعد في تحديدها. لاحظ أن حتى الإضافات ذات التقييم العالي قد لا تكون بالضرورة من MCreator؛ على سبيل المثال، قد تكون لديها تكامل مع MCreator.";
	}

	@Override
	public String escanear() {
		return "فحص";
	}

	@Override
	public String cargando() {
		return "جارٍ التحميل";
	}

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "ar";
	}

	@Override
	public String inicioApp() {
		return "بدء اللعبة/التطبيق";
	}

	@Override
	public String ajustesCrashDetector() {
		return "إعدادات كاشف الأعطال";
	}

	@Override
	public String confidencialidad() {
		return "الخصوصية";
	}

	@Override
	public String tooltip() {
		return "تلميح الأدوات";
	}

	@Override
	public String config() {
		return "الإعدادات";
	}

	@Override
	public String abrirCarpeta() {
		return "فتح المجلد";
	}

	@Override
	public String actualizar() {
		return "تحديث";
	}

	@Override
	public String anadirRegistro() {
		return "إضافة سجل";
	}

	@Override
	public String usarIdiomaDelSistema() {
		return "استخدام لغة النظام";
	}

	@Override
	public String volver() {
		return "رجوع";
	}

	@Override
	public String colorFondo() {
		return "لون الخلفية (#RRGGBB):";
	}

	@Override
	public String colorTexto() {
		return "لون النص (#RRGGBB):";
	}

	@Override
	public String colorBoton() {
		return "لون الزر (#RRGGBB):";
	}

	@Override
	public String colorCajaTexto() {
		return "لون مربع النص (#RRGGBB):";
	}

	@Override
	public String colorEnlace() {
		return "لون الرابط (#RRGGBB):";
	}

	@Override
	public String colorTitulosConsolas() {
		return "لون عناوين الكونسول (#RRGGBB):";
	}

	@Override
	public String colorError() {
		return "لون الخطأ (#RRGGBB):";
	}

	@Override
	public String colorAdvertencia() {
		return "لون التحذير (#RRGGBB):";
	}

	@Override
	public String colorInfo() {
		return "لون المعلومات (#RRGGBB):";
	}

	@Override
	public String colorTitulo() {
		return "لون العنوان (#RRGGBB):";
	}

	@Override
	public String colorEnlaceTexto() {
		return "لون نص الرابط (#RRGGBB):";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "فتح CrashDetector فقط عند الفشل";
	}

	@Override
	public String activar_parche() {
		return "تفعيل التصحيح";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "لا توجد حلول متاحة";
	}

	@Override
	public String error() {
		return "خطأ";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "خطأ أثناء حذف Jar";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "تم حذف Jar بنجاح";
	}

	@Override
	public String exito() {
		return "نجاح";
	}

	@Override
	public String eliminar() {
		return "حذف";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "خطأ أثناء حذف الحزمة";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "تم حذف الحزمة بنجاح";
	}

	@Override
	public String eliminar_paquete() {
		return "حذف الحزمة";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "لم يتم العثور على تعديلات مع الحزمة";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "لا يمكن حذف الحزمة";
	}

	@Override
	public String eliminar_jar() {
		return "حذف Jar";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "لم يتم العثور على تعديلات مكررة";
	}

	@Override
	public String archivo_no_encontrado() {
		return "الملف غير موجود";
	}

	@Override
	public String directorio_eliminado() {
		return "المجلد محذوف";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "خطأ في حذف ملف Jar المتداخل";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "لم يتم العثور على الملف الداخلي";
	}

	@Override
	public String archivo_eliminado() {
		return "تم حذف الملف";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "خطأ في حذف الملف";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "الملف الخارجي غير صالح";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "تم حذف عنصر التعديل";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "خطأ في استبدال ملف Jar الخارجي";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "خطأ في حذف عنصر التعديل";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "خطأ في حذف المجلد";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "صيغة غير صالحة لملف Jar المتداخل";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "تم حذف ملف Jar المتداخل";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "خطأ في تنظيف الملفات المؤقتة";
	}

	@Override
	public String mensaje_de_trace_fatal_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>رسالة تتبع قاتلة أخيرة (غير مترجمة):</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>رسالة تتبع أخيرة (غير مترجمة):</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "يمكنك البحث في قاعدة بيانات WaifuNeoForge للعثور على المودات. اختر إصدار اللعبة، ومحمل المودات، واسم الفئة. استخدم أقرب تطابق ممكن. يمكنك البحث مرة واحدة كل دقيقة.";
	}

	@Override
	public String solucionFaltasClases() {
		return "يمكنك البحث في قاعدة بيانات WaifuNeoForge للعثور على المودات. اختر إصدار اللعبة، ومحمل المودات، واسم الفئة. استخدم أقرب تطابق ممكن. يمكنك البحث مرة واحدة كل دقيقة.";
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "يحتوي كل من مشغّلي الألعاب على إصدارات صحيحة من جافا ولكن ليس جميعها؛ يمكنك تثبيت الإصدار الصحيح من جافا من مدير الحزم في نظامك أو باستخدام الأزرار.";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>التعديل الذي يفتقر إلى الرسوم المتحركة: " + "</b>";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException (استثناء عدم وجود عنصر) الرسوم المتحركة مفقودة";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "لم يتم العثور على تعديلات لحذفها";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "استبدال خيارات جمع القمامة المتعارضة بـ -XX:+UseG1GC";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "زيادة حجم ذاكرة الكومة باستخدام الخيار -Xmx.";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "زيادة حجم ذاكرة permgen باستخدام الخيار -XX:MaxPermSize.";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "استخدام ماchine الظاهرية جافا 64 بت لزيادة الذاكرة المتاحة.";
	}

	@Override
	public String optimizarCodigo() {
		return "تحسين الكود لتقليل استخدام الذاكرة وتحسين الأداء.";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "استخدام جامع قمامة فعال لتقليل توقف التطبيق.";
	}

	@Override
	public String modulos() {
		return "وحدات";
	}

	@Override
	public String paquete() {
		return "حزمة";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "هناك بعض المعرفات الناقصة. الأسباب الشائعة هي تعديلات مفقودة أو بيانات عناصر مفقودة. المجلدات الشائعة للبيانات هي datafiedcontents/ و kubejs/ أو مجلدات أخرى خاصة بالتعديلات.";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "سجلات تم ربطها بشكل خاطئ";
	}

	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>فشل تحميل الإضافة 'AuthMe' وأدت إلى إيقاف الخادم.</b> ";
	}

	public String nombreProblemaCierreAuthMe() {
		return "مشكلة إيقاف الخادم بسبب AuthMe";
	}

	public String solucionCierreAuthMe() {
		return "تغيرت القاعدة 'stopServer' إلى 'true'.";
	}

	public String solucionConfigurarPluginAuthMe() {
		return "قم بتكوين الإضافة AuthMe (plugins/AuthMe/config.yml)";
	}

	public String solucionInstalarVersionDiferenteAuthMe() {
		return "ثبت نسخة مختلفة من الإضافة 'AuthMe'";
	}

	public String solucionEliminarPluginAuthMe() {
		return "احذف الإضافة 'AuthMe'";
	}

	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>لم يمكن تحميل العالم '" + nombreMundo
				+ "' لأنه يحتوي على أخطاء وربما يكون مُهترئًا.</b> ";
	}

	@Override
	public String nombreProblemaCargaMultiverso() {
		return "مشكلة في تحميل عوالم Multiverse";
	}

	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "قم بإصلاح العالم '" + nombreMundo + "', على سبيل المثال باستخدام Minecraft Region Fixer أو MCEdit.";
	}

	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "احذف مجلد العالم '" + nombreMundo + "'.";
	}

	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError() + "'>إعدادات الإضافة 'PermissionsEx' غير صالحة.</b> ";
	}

	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "مشكلة في إعدادات PermissionsEx";
	}

	@Override
	public String solucionConfigurarPermissionsEx() {
		return "قم بتكوين الإضافة PermissionsEx (plugins/PermissionsEx/permissions.yml)";
	}

	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "احذف الإضافة 'PermissionsEx'";
	}

	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>هناك عدة ملفات إضافات تحمل نفس الاسم '"
				+ nombrePlugin + "': '" + primerPath + "' و'" + segundoPath + "'.</b> ";
	}

	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "مشكلة اسم إضافة مُبهم";
	}

	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "احذف الإضافة '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>حدث خطأ أثناء تحميل الكتل (chunks) في العالم.</b> ";
	}

	@Override
	public String nombreProblemaCargaChunk() {
		return "خطأ عند تحميل الكتل";
	}

	@Override
	public String solucionEliminarChunk() {
		return "قم بإزالة الكتلة المشكلة من العالم، على سبيل المثال باستخدام MCEdit أو عن طريق حذف ملف المنطقة.";
	}

	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>لا يمكن للإضافة '" + nombrePlugin
				+ "' تنفيذ الأمر '/" + comando + "'.</b> ";
	}

	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "استثناء أثناء تنفيذ أمر الإضافة";
	}

	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "ثبت نسخة مختلفة من الإضافة '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>تتطلب الإضافة '" + nombrePlugin
				+ "' الاعتماد على الإضافة '" + dependencia + "'.</b> ";
	}

	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>تتطلب الإضافة '" + nombrePlugin
				+ "' الاعتماد على الإضافات التالية: " + deps.toString() + ".</b> ";
	}

	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "الاعتماد على إضافة مفقودة";
	}

	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "ثبت الإضافة '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>الإضافة '" + nombrePlugin
				+ "' تحتاج إلى إصدار واجهة برمجية '" + versionAPI + "' غير متوافق مع الخادم الحالي.</b> ";
	}

	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "إصدار واجهة برمجية غير متوافق";
	}

	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "ثبت الإصدار '" + version + "' من برنامج خادمك.";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>المعالم '" + nombreMundo
				+ "' هو نسخة مكررة من عالم آخر ولا يمكن تحميله.</b> ";
	}

	@Override
	public String nombreProblemaMundoDuplicado() {
		return "عالم مكرر";
	}

	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "احذف ملف 'uid.dat' من العالم '" + nombreMundo + "'";
	}

	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "احذف مجلد العالم '" + nombreMundo + "'";
	}

	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "<b style='color:#" + config.obtenerColorError() + "'>الكيان الكتلي '" + nombre + "' من النوع '" + tipo
				+ "' في الموقع " + coords + " يسبب أخطاء أثناء التحديثات.</b> ";
	}

	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "كيان كتلي مشكل";
	}

	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "قم بإزالة الكيان '" + nombre + "' الموجود في الموقع " + coords
				+ " باستخدام MCEdit أو عن طريق تحرير العالم مباشرة.";
	}

	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>يحتوي المود '" + nombreMod
				+ "' على عدة إصدارات مثبتة.</b> ";
	}

	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "مود مكرر في Fabric";
	}

	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "احذف ملف المود المكرر: " + rutaMod;
	}

	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>المودان '" + primerMod + "' و'" + segundoMod
				+ "' غير متوافقين مع بعضهما البعض.</b> ";
	}

	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "مود غير متوافق في Fabric";
	}

	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "احذف المود '" + nombreMod + "'";
	}

	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>يحتوي المود '" + nombreMod
				+ "' على خطأ حرج ولا يمكن تنفيذه.</b> ";
	}

	@Override
	public String nombreProblemaModFatal() {
		return "مود يحتوي على خطأ جسيم";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>المودات التالية مطلوبة ولكن لم يتم تثبيتها: "
				+ deps.toString() + ".</b>";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>يحتاج المود '" + nombreMod + "' إلى المود '"
					+ dependencia + "' كاعتمادية.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>يحتاج المود '" + nombreMod + "' إلى المود '"
					+ dependencia + "' بالإصدار " + version + ".</b>";
		}
	}

	@Override
	public String nombreProblemaDependenciaMod() {
		return "الاعتماد على مود غير مثبت";
	}

	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "ثبت المود '" + nombreMod + "'";
	}

	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "ثبت المود '" + nombreMod + "' مع الإصدار " + version;
	}

	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>الإضافة '" + nombrePlugin
				+ "' غير متوافقة مع التحديث الإقليمي في Folia.</b> ";
	}

	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("الإضافات التالية غير متوافقة مع التحديث الإقليمي في Folia: ");

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
		return "إضافة غير متوافقة مع التحديث الإقليمي";
	}

	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "ثبت إصدارًا بدون تحديث إقليمي، مثل " + software;
	}

	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>المود '" + nombreMod + "' مفقود من الحزمة.</b>";
	}

	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("المودات التالية مفقودة من الحزمة: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" و");
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
		return "مود مفقود من الحزمة";
	}

	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>المود '" + nombreMod + "' تسبب في خطأ.</b>";
	}

	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("المودات التالية تسببت في أخطاء: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" و");
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
		return "مود Forge مع استثناء";
	}

	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "ثبت نسخة مختلفة من المود '" + nombreMod + "'";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>المود '" + nombreMod
				+ "' غير متوافق مع إصدار ماينكرافت " + versionMC + ".</b>";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("المودات التالية غير متوافقة مع إصدارات ماينكرافت: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (ماينكرافت ").append(versionesMC.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaModIncompatibleConMinecraft() {
		return "مود غير متوافق مع ماينكرافت";
	}

	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "ثبت إصدارًا متوافقًا من Forge مع ماينكرافت " + versionMC;
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>المود '" + nombreMod
				+ "' مفقود وهو مطلوب لتحميل العالم أو الإضافة.</b>";
	}

	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "مود مفقود";
	}

	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>تم حفظ العالم مع المود '" + nombreMod
				+ "' الذي يبدو أنه مفقود.</b>";
	}

	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("تم حفظ العالم مع المودات التالية التي تبدو مفقودة: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" و");
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
		return "مود مفقود في العالم";
	}

	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>تم حفظ العالم باستخدام المود '" + nombreMod
				+ "' بالإصدار " + versionEsperada + "، وهو الآن يعمل بالإصدار " + versionActual + ".</b>";
	}

	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("المودات التالية لديها تناقض في الإصدار في العالم المحفوظ: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" و");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (محفوظ: ").append(versionesEsperadas.get(i)).append(", حاليًا: ")
					.append(versionesActuales.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaVersionModMundo() {
		return "مود غير متوافق مع إصدار العالم";
	}

	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لقد حاولت تحميل عالم تم إنشاؤه بإصدار أحدث من ماينكرافت.</b>";
	}

	@Override
	public String nombreProblemaVersionDowngrade() {
		return "محاولة تحميل عالم من إصدار أحدث";
	}

	@Override
	public String solucionVersionDowngrade() {
		return "ثبت إصدارًا أحدث من ماينكرافت.";
	}

	@Override
	public String solucionGenerarNuevoMundo() {
		return "أنشئ عالمًا جديدًا.";
	}

	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>الإضافة '" + nombrePlugin
				+ "' تحتاج إلى الإضافة التالية كاعتماد: '" + dependencia + "'.</b>";
	}

	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("الإضافات التالية تحتاج إلى اعتمادات غير مثبتة: ");

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
		return "إضافة تحتوي على اعتماد مفقود";
	}

	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>الإضافة '" + nombrePlugin
				+ "' غير متوافقة مع إصدار الخادم الحالي.</b>";
	}

	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("الإضافات التالية غير متوافقة مع إصدار الخادم الحالي: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" و");
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
		return "الإضافة غير متوافقة مع PocketMine-MP";
	}

	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>الإضافة '" + nombrePlugin
				+ "' تسببت في خطأ أثناء التشغيل.</b>";
	}

	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("الإضافات التالية تسببت في أخطاء أثناء التشغيل: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" و");
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
		return "إضافة تحتوي على خطأ في التنفيذ";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "LegacyRandomSource متعدد الخيوط";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لديك مشكلة مع عدة خيوط تصل إلى فئة LegacyRandomSource. يمكنك الحصول على المزيد من المعلومات باستخدام تعديل 'Unsafe World Random Access Detector' أو التعديل 'C2ME'.</b>";
	}

	@Override
	public String desdeUltimoExito() {
		return "منذ آخر نجاح";
	}

	@Override
	public String noHayCambios() {
		return "لا تغييرات";
	}

	@Override
	public String desdeUltimoIntento() {
		return "منذ آخر محاولة";
	}

	@Override
	public String fallo() {
		return "فشل";
	}

	@Override
	public String diferentesDeLasMods() {
		return "مختلف عن المودات";
	}

	@Override
	public String historialDeMods() {
		return "سجل المودات";
	}

	@Override
	public String archivo0() {
		return "ملف0";
	}

	@Override
	public String archivo1() {
		return "ملف1";
	}

	@Override
	public String comparar() {
		return "مقارنة";
	}

	@Override
	public String seleccionarDosArchivos() {
		return "حدد ملفين";
	}

	@Override
	public String archivoExito() {
		return "ملف النجاح";
	}

	@Override
	public String archivoFalla() {
		return "ملف الفشل";
	}

	@Override
	public String errorComparandoArchivos() {
		return "خطأ في مقارنة الملفات";
	}

	@Override
	public String comparando() {
		return "يتم المقارنة";
	}

	@Override
	public String con() {
		return "مع";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;' dir='rtl'>"
				+ "<p><b>لوحة مقارنة سجل المودات</b></p>"
				+ "<p>تتيح لك هذه اللوحة مقارنة قائمتين من المودات من جلسات مختلفة. اختر ملفًا من العمود الأيسر وآخر من الأيمن لرؤية التغييرات بين الإصدارين.</p>"

				+ "<h3>كيفية الاستخدام:</h3>" + "<ol>"
				+ "<li><b>اختيار الملفات:</b> انقر على أزرار الاختيار بجانب أسماء الملفات. "
				+ "الملفات التي تنتهي بـ <span style='color: #4CAF50; font-weight: bold;'>.suceso</span> تشير إلى جلسات ناجحة، "
				+ "بينما تشير ملفات الـ <span style='color: #F44336; font-weight: bold;'>.falla</span> إلى فشل أو خطأ.</li>"

				+ "<li><b>المقارنة التلقائية:</b> عند الضغط على زر 'Compare'، سيقوم النظام بتحليل القوائم واكتشاف المودات المضافة (+) أو المحذوفة (-).</li>"

				+ "<li><b>عرض النتائج:</b> يتم عرض النتائج بصيغة HTML مع ترميز بالألوان: " + "<ul>"
				+ "<li><span style='color: green;'>+ مود مضاف</span></li>"
				+ "<li><span style='color: red;'>- مود محذوف</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>المميزات الرئيسية:</h3>" + "<ul>" + "<li>يدعم أي مزيج من الملفات (ناجحة/فاشلة).</li>"
				+ "<li>يُظهر الفروقات في كلا الاتجاهين للحصول على تعقب دقيق.</li>"
				+ "<li>يحتوي على إمكانية التمرير لقائمة طويلة من المودات.</li>"
				+ "<li>متكامل مع صور شارحة لتحسين الفهم البصري.</li>" + "</ul>"

				+ "<p>تم تطويره بـ 3> لمساعدتك على تتبع التعديلات في إعداداتك.</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "من المحتمل أنك تواجه مشكلة متعلقة بـ IPv6. "
				+ "توجد حلان: "
				+ "1) أضف المعامل <code>-Djava.net.preferIPv4Stack=true</code> إلى برنامج التشغيل الخاص بك، أو "
				+ "2) استخدم زر 'QuickFix' في CrashDetector لتثبيت إصلاح يفعّل هذا الإعداد تلقائيًا." + "</b>";
	}

	@Override
	public String parcheIPv4() {
		return "تصحيح IPv4/6";
	}

	@Override
	public String carpetaHMCL() {
		return "مجلد HMCL (لـ HelloMineCraftLauncher فقط)";
	}

	@Override
	public String descripcionCurseforge() {
		return "ملاحظة: لا يتم إنشاء سجل إذا كانت خاصية 'تخطي اللانشر' مفعلة في الإعدادات > ماينكرافت";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/أو نسخ مشتقة منها: انقر بزر الماوس الأيمن على النسخة واختر \"تحرير النسخة\". في النافذة التي تفتح، ابحث عن قسم \"سجلات ماينكرافت\" أو ما شابه.<br>"
				+ "تحتوي هذه السجلات على الإخراج القياسي (STDOUT)، وهو ضروري لتشخيص الأخطاء.";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher): يجب أن تختار المجلد الذي تم تثبيت HMCL فيه واختر المجلد \".hmcl\". تحتفظ HMCL بالسجلات هنا وتحتوي على معلومات مهمة عن الأخطاء.<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: يحتوي اللانشر على علامة تبويب للتطوير تعرض سجلات مفصلة. يمكنك العثور على هذه العلامة في قائمة إعدادات اللانشر.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: يوجد نافذة منبثقة تحتوي على السجلات. بها أزرار لنسخ وتحميل السجلات. يتم إنشاء السجلات تلقائيًا عند بدء اللعبة وتتضمن معلومات حيوية للتشخيص.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: انقر بزر الماوس الأيمن على النسخة واختر \"إعدادات\". ثم انتقل إلى قسم السجلات لعرض المعلومات المهمة من الإخراج القياسي (STDOUT).";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "روابط Markdown: الصق أي روابط تحتوي على سجلات بصيغة Markdown هنا. سيحاول النظام استخراج الروابط الخاصة بالسجلات (latest.log, launcher_log.txt, debug.log، إلخ) وتحليلها.";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "لم يتم العثور على سجل اللانشر";
	}

	@Override
	public String imagenNoEncontrada() {
		return "الصورة غير موجودة";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>تم اكتشاف فئات مفقودة من Create. لقد تغير Create كثيرًا — تم إزالة العديد من الفئات. خاصةً منذ Create 6 (فبراير 2025)، الإضافات الخاصة بالإصدارات القديمة من Create لم تعد تعمل. لا يمكن لـ QuickFix حل هذه المشكلة. يجب تحديث إضافات Create، أو إزالة القديمة منها، أو استخدام الإصدار الصحيح من Create للإضافات التي ترغب في استخدامها.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>تم اكتشاف فئات مفقودة من EpicFight. لقد تغير EpicFight كثيرًا — تم إزالة العديد من الفئات. لا يمكن لـ QuickFix حل هذه المشكلة. يجب تحديث إضافات EpicFight، أو إزالة القديمة منها، أو استخدام الإصدار الصحيح من EpicFight للإضافات التي ترغب في استخدامها.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>أنت تستخدم OpenJ9، وهو غير مدعوم من قبل هذا التطبيق. العديد من التطبيقات، بما في ذلك هذا، لا تدعم OpenJ9 بسبب اختلافات في تنفيذ JVM. لا يمكن لـ QuickFix حل هذه المشكلة تلقائيًا. يجب عليك تثبيت JDK متوافق مثل Oracle JDK أو OpenJDK Hotspot أو بدائل موصى بها أخرى.</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>تتطلب هذه النسخة من التطبيق Java 11 (JDK 11) للعمل بشكل صحيح. أنت تستخدم إصدارًا قديمًا وغير متوافق من Java. لا يمكن لـ QuickFix تحديث Java تلقائيًا. يجب تنزيل وتثبيت JDK 11 أو إصدار متوافق أحدث من الروابط المقدمة في الحل.</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لقد خصصت ذاكرة زائدة، مما يؤدي إلى نقص الموارد في النظام. يحدث هذا غالبًا عند تحديد كمية ذاكرة أكبر من المتوفرة، أو عند استخدام JVM من نوع 32 بت لا يمكنه التعامل مع كميات كبيرة من الذاكرة.</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>لحل هذه المشكلة، قم بتقليل كمية الذاكرة المخصصة للتطبيق. الحد الأقصى الموصى به يعتمد على نظامك، لكنه عادةً لا يجب أن يتجاوز 70-80٪ من إجمالي ذاكرة RAM. إذا كنت تستخدم JVM 32 بت، فإن الحد الأقصى هو حوالي 2-3 جيجابايت بغض النظر عن كمية الذاكرة الفعلية.</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>لتقليل ذاكرة heap المخصصة للتطبيق، افتح إعدادات اللانشر وابحث عن خيار الذاكرة. قلل القيمة القصوى (Xmx) إلى كمية مناسبة. على سبيل المثال، إذا كانت لديك 8 جيجابايت من RAM، خصص 3-4 جيجابايت للتطبيق. إذا كانت لديك 16 جيجابايت، يمكنك تخصيص 6-8 جيجابايت. تأكد من ترك ذاكرة كافية للنظام والبرامج الأخرى.</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>مفقودة ملفات أساسية من Forge. الملف '" + archivo
				+ "' غير موجود في تثبيتك. يحدث هذا عادةً عند توقف تثبيت Forge أو حذف ملفات مهمة. لا يمكن لـ QuickFix استعادة هذه الملفات تلقائيًا. يجب عليك إعادة تثبيت Forge بشكل صحيح باستخدام المثبت الرسمي.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لا يمكن لـ Forge العثور على إصدار ماينكرافت المطلوب. الإصدار " + version
				+ " مطلوب لكنه غير موجود في الملف '" + archivo
				+ "'. يحدث هذا عند عدم التوافق بين إصدار ماينكرافت وإصدار Forge. تأكد من تحميل الإصدار الصحيح من Forge المطابق لإصدار ماينكرافت لديك.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>تعذر العثور على الهدف 'fmlclient' اللازم لتشغيل Forge. هذا يشير إلى تثبيت غير مكتمل أو تالف. ربما لم تُثبت ملفات Forge الأساسية بشكل صحيح. يجب عليك إعادة تثبيت Forge باستخدام المثبت الرسمي.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>تعذر العثور على الفئة الرئيسية لماينكرافت في محمل الفئات. هذا يشير عادةً إلى تثبيت Forge غير مكتمل أو تعارض مع مودات أخرى. قد تكون ملفات ماينكرافت تالفة بسبب تثبيت Forge. يجب إعادة تثبيت Forge بشكل صحيح.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>تثبيت Forge غير مكتمل. قد يكون بسبب تثبيت متوقف، أو ملفات محذوفة، أو عدم توافق مع إصدار ماينكرافت لديك. يحتاج Forge إلى ملفات محددة للعمل، وبعضها مفقود من التثبيت الحالي.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "تثبيت Forge غير مكتمل";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>لحل هذه المشكلة، أعد تثبيت Forge بشكل صحيح. تأكد من تنزيل الإصدار المناسب لإصدار ماينكرافت الخاص بك، واتبع عملية التثبيت بالكامل دون مقاطعة.</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "تنزيل Forge رسميًا";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "كيفية إعادة تثبيت Forge بشكل صحيح";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>تعليمات لإعادة تثبيت Forge:</h3>" + "<ol>"
				+ "<li>قم بتنزيل مثبت Forge الصحيح من الموقع الرسمي (الإصدار الموصى به لإصدار ماينكرافت الخاص بك)</li>"
				+ "<li>أغلق بالكامل برنامج تشغيل ماينكرافت</li>" + "<li>شغّل مثبت Forge كمسؤول</li>"
				+ "<li>اختر خيار 'Installer' (وليس 'Installer (run client)')</li>"
				+ "<li>اختر مجلد ملف تعريف ماينكرافت في البرنامج</li>" + "<li>اضغط 'موافق' وانتظر اكتمال التثبيت</li>"
				+ "<li>أعد تشغيل البرنامج وتأكد من ظهور Forge في قائمة الملفات الشخصية</li>" + "</ol>"
				+ "<p><b>ملاحظة مهمة:</b> إذا كنت تستخدم برنامج تشغيل مخصصًا، تأكد من اختيار المجلد الصحيح للملف الشخصي.</p>"
				+ "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "تعليمات لإعادة تثبيت Forge";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError() + "'>خطأ في الربط: فشل في تحميل المكتبة "
				+ nombreBiblioteca + ". الحلول الممكنة:<br/><br/>"
				+ "أ) أضف الدليل الذي يحتوي على المكتبة المشتركة إلى -Djava.library.path أو -Dorg.lwjgl.librarypath.<br/>"
				+ "ب) أضف ملف JAR الذي يحتوي على المكتبة المشتركة إلى classpath.<br/><br/>"
				+ "يحدث هذا الخطأ عندما لا يستطيع ماينكرافت العثور على الملفات الأساسية للتشغيل. "
				+ "وهو ناتج عادةً عن تثبيت ماينكرافت غير مكتمل أو مشاكل في أذونات النظام.</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "خطأ في الربط";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>فشل في تحميل مكتبة. الحلول الممكنة:<br/><br/>"
				+ "أ) أضف الدليل الذي يحتوي على المكتبة المشتركة إلى -Djava.library.path أو -Dorg.lwjgl.librarypath.<br/>"
				+ "ب) أضف ملف JAR الذي يحتوي على المكتبة المشتركة إلى classpath.<br/><br/>"
				+ "هذه الحلول التقنية مخصصة للمستخدمين المتقدمين. معظم المستخدمين يجب أن يحاولوا "
				+ "إعادة تثبيت ماينكرافت قبل تعديل هذه المعلمات.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>تضارب في المعرفات (IDs): المعرف <strong>" + id
				+ "</strong> مستخدم بالفعل بواسطة <strong>" + modOrigen + "</strong> عند محاولة إضافة <strong>"
				+ modDestino + "</strong>. يحدث هذا عندما يحاول مودان استخدام نفس المعرف لعناصر مختلفة.</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>تم تجاوز الحد الأقصى المسموح به للمعرفات. يحدث هذا عندما يحاول المودات تسجيل كتل أو عناصر باستخدام معرفات خارج النطاق المدعوم من إصدار ماينكرافت الخاص بك.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "تضارب في المعرفات";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>لحل هذه المشكلة في ماينكرافت 1.12.2، قم بتثبيت <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. أما في 1.7.10، استخدم <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>استخدم أدوات مثل <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> أو <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> لحل تضارب المعرفات.</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "تثبيت JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "تثبيت EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "استخدام IdFix Minus أو IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "استخدام Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "عرض الوثائق اليابانية";
	}

	@Override
	public String escanearDeMCreator() {
		return "مسح MCreator";
	}

	@Override
	public String tituloArbolDeMods() {
		return "شجرة الوحدات والطبقات";
	}

	@Override
	public String tipoBusqueda() {
		return "النوع";
	}

	@Override
	public String filtroTodos() {
		return "الكل";
	}

	@Override
	public String filtroPaquetes() {
		return "الحزم";
	}

	@Override
	public String filtroClases() {
		return "الطبقات";
	}

	@Override
	public String filtroMetodos() {
		return "الطرق";
	}

	@Override
	public String filtroCampos() {
		return "الحقول";
	}

	@Override
	public String filtroReferenciasCampo() {
		return "مراجع الحقول";
	}

	@Override
	public String filtroReferenciasMetodo() {
		return "مراجع الطرق";
	}

	@Override
	public String filtroReferenciasClase() {
		return "مراجع الطبقات";
	}

	@Override
	public String tipBuscar() {
		return "اكتب هنا للبحث في شجرة المودات";
	}

	@Override
	public String botonBuscar() {
		return "بحث";
	}

	@Override
	public String botonResetearArbol() {
		return "إعادة تعيين الشجرة";
	}

	@Override
	public String modsCargados() {
		return "المودات المحملة";
	}

	@Override
	public String clases() {
		return "الطبقات";
	}

	@Override
	public String metodos() {
		return "الطرق";
	}

	@Override
	public String campos() {
		return "الحقول";
	}

	@Override
	public String referencias() {
		return "المراجع";
	}

	@Override
	public String resultadosBusqueda() {
		return "نتائج البحث";
	}

	@Override
	public String buscarReferencias() {
		return "ابحث عن المراجع";
	}

	@Override
	public String referenciasMod() {
		return "مراجع المود";
	}

	@Override
	public String referenciasClase() {
		return "مراجع الطبقة";
	}

	@Override
	public String referenciasMetodo() {
		return "مراجع الطريقة";
	}

	@Override
	public String referenciasCampo() {
		return "مراجع الحقل";
	}

	@Override
	public String noSeEncontraronReferencias() {
		return "لم يتم العثور على مراجع";
	}

	@Override
	public String detalleMod() {
		return "تفاصيل المود:";
	}

	@Override
	public String ubicacion() {
		return "الموقع";
	}

	@Override
	public String nombres() {
		return "الأسماء";
	}

	@Override
	public String modulo() {
		return "الوحدة";
	}

	@Override
	public String detalleClase() {
		return "تفاصيل الطبقة:";
	}

	@Override
	public String detalleMetodo() {
		return "تفاصيل الطريقة:";
	}

	@Override
	public String detalleCampo() {
		return "تفاصيل الحقل:";
	}

	@Override
	public String clase() {
		return "الطبقة";
	}

	@Override
	public String tipo() {
		return "النوع";
	}

	@Override
	public String referenciasAMetodos() {
		return "مراجع إلى الطرق:";
	}

	@Override
	public String referenciasACampos() {
		return "مراجع إلى الحقول:";
	}

	@Override
	public String arbolDeMods() {
		return "شجرة المودات";
	}

	@Override
	public String metodo() {
		return "الطريقة";
	}

	@Override
	public String campo() {
		return "الحقل";
	}

	@Override
	public String descompilar() {
		return "فك التجميع";
	}

	@Override
	public String exportar() {
		return "تصدير";
	}

	@Override
	public String importar() {
		return "استيراد";
	}

	@Override
	public String errorImportar() {
		return "خطأ في الاستيراد";
	}

	@Override
	public String estructuraImportada() {
		return "الهيكلة المستوردة";
	}

	@Override
	public String estructuraExportada() {
		return "الهيكلة المصدرة";
	}

	@Override
	public String errorExportar() {
		return "خطأ في التصدير";
	}

	@Override
	public String exportando() {
		return "جاري التصدير";
	}

	@Override
	public String exportacionTardara() {
		return "قد يستغرق التصدير وقتًا";
	}

	@Override
	public String porFavorEspere() {
		return "الرجاء الانتظار";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>ليس لديك ملفات VLC الثنائية. WaterMedia يحتاج إلى ملفات VLC الثنائية. يجب تثبيتها يدويًا من https://www.videolan.org/vlc/.  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "تنزيل VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>خطأ حرج: اسم الوحدة '" + nombreModulo
				+ "' يحتوي على أحرف غير صالحة. الجزء '" + parteInvalida
				+ "' ليس معرفًا صالحًا في جافا. يحدث هذا عندما يستخدم مود كلمات محجوزة في جافا (مثل 'true', 'class') أو أحرف غير مسموح بها في الاسم.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "أحرف غير صالحة في اسم المود";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "اسم المود '" + nombreModulo + "' غير صالح لأنه يحتوي على '" + parteInvalida
				+ "', وهي كلمة محجوزة في جافا أو حرف غير مسموح به. "
				+ "ابحث في السجلات عن المود الذي يطابق هذا الاسم (عادةً اسم ملف JAR)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "اذهب إلى مجلد المود وقم بتحرير ملف <b>mods.toml</b> الموجود داخل مجلد <b>/META-INF/</b>. "
				+ "غيّر قيمة <b>modId</b> لاستخدام الحروف والأرقام والشرطات السفلية فقط، دون كلمات محجوزة في جافا";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "مثال على اسم صالح: 'truemod_shot_enchantment' بدلاً من 'true.shot.enchantment'. "
				+ "تذكر أن أسماء المودات لا يمكن أن تحتوي على نقاط، شرطات، أو كلمات محجوزة في جافا مثل 'true'، 'false'، أو 'class'";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>خطأ حرج في المود: '" + nombreJar
				+ "'. يفتقد إلى الحقل الإلزامي 'mandatory' في تبعياته. يحدث هذا عندما لا يحدد ملف mods.toml ما إذا كانت التبعية إلزامية أم لا.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "تبعية مود بدون حقل إلزامي";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "المود المسبب للمشكلة هو: <b>" + nombreJar + "</b>. هذا الملف يحتوي على خطأ في تهيئة التبعيات";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "افتح ملف <b>mods.toml</b> الموجود داخل مجلد <b>/META-INF/</b> الخاص بالمود <b>" + nombreJar + "</b>";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "في قسم التبعيات، تأكد من أن كل إدخال يحتوي على <b>mandatory=true</b> أو <b>mandatory=false</b> (مثال: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>خطأ حرج في المود: '" + nombreJar
				+ "'. تهيئة غير صالحة لـ access transformer. يحدث هذا عندما يكون ملف التهيئة به بنية غير صحيحة أو يشير إلى فئات/طرق غير موجودة.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Access Transformer غير صالح";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "المود المسبب للمشكلة هو: <b>" + nombreJar
				+ "</b>. هذا المود يحتوي على تهيئة غير صالحة لـ access transformer";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "افتح ملف <b>accessTransformer.cfg</b> داخل المود <b>" + nombreJar
				+ "</b> (عادةً في المجلد الجذري لملف JAR)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "عدّل بنية access transformer. يجب أن تتبع الأسطر التنسيق التالي: <b>access class.method</b> (مثال: public net.minecraft.world.entity.Entity.func_200560_a). احذف الأسطر التي تشير إلى فئات أو طرق غير موجودة في إصدار ماينكرافت الخاص بك";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>خطأ حرج: تناقض بين معرف المود في التعليق @Mod وملف mods.toml. لا يمكن تحميل المود '" + nombreMod
				+ "' لأن المعرفات غير متطابقة.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "تناقض بين @Mod و mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "المود قيد التطوير '" + nombreMod
				+ "' يحتوي على تناقض بين المعرف في التعليق <b>@Mod</b> والقيمة في <b>mods.toml</b>";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "تحقق من أن المعرف في فصلك الرئيسي مطابق لقيمة <b>modId</b> في ملف <b>/META-INF/mods.toml</b>. مثال: <b>@Mod(\"mymod\")</b> يجب أن يطابق <b>modId=\"mymod\"</b>";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "إذا كنت تستخدم Gradle، قم بتشغيل <b>clean</b> بعد التعديلات لضمان تحديث الموارد بشكل صحيح. أحيانًا تبقى الملفات القديمة في مجلد build";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "العميل" : "الخادم";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "الخادم" : "العميل";

		return "<b style='color:#" + config.obtenerColorError() + "'>خطأ حرج: يتم محاولة تحميل الصنف '" + nombreClase
				+ "' في بيئة " + plataforma + "، لكنه مصمم لـ " + plataformaOpuesta
				+ ". <b>استخدم وظيفة 'شجرة المودات' في الشريط الجانبي للعثور على المود الذي يحاول تحميل هذا الصنف</b>. "
				+ "المودات مبنية خصيصًا لمنصة معينة ولا تعمل على الأخرى.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "مود على منصة خاطئة";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "في علامة تبويب <b>شجرة المودات</b> (على اليمين)، ابحث عن مراجع للصنف <b>" + nombreClase
				+ "</b> لتحديد المود المسبب للمشكلة";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "العميل" : "الخادم";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "الخادم" : "العميل";

		return "المود المحدد هو مود لـ <b>" + plataformaOpuesta + "</b> ولا ينبغي استخدامه في بيئة " + plataforma + ".";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "أزل المود المسبب للمشكلة من مجلد <b>المودات</b>. إذا كنت بحاجة لوظيفة مشابهة لهذه المنصة، "
				+ "ابحث عن مود بديل مصمم خصيصًا لـ <b>العميل</b> أو <b>الخادم</b> حسب الحاجة";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("خطأ حرج: الميتاداتا مفقودة للـ modid '").append(modIdFaltante).append("'. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("قد تكون المودات التالية هي المسببة للمشكلة: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", وآخرين...");
			mensaje.append("</b>. ");
		}

		mensaje.append("يحدث هذا عندما يعتمد مود على مود آخر غير مثبت أو لديه ملف mods.toml غير صحيح.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "ميتاداتا mods.toml مفقودة";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("تعتمد المودات التالية على '").append(modIdFaltante)
					.append("': <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", وآخرين...");
			paso.append("</b>. استخدم وظيفة <b>شجرة المودات</b> للتحقق من المود المسبب للمشكلة");
			return paso.toString();
		}
		return "مود يحاول الاعتماد على '" + modIdFaltante
				+ "'، لكن هذا المود غير مثبت. استخدم وظيفة <b>شجرة المودات</b> لتحديد المود المسبب للمشكلة";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "لديك خياران:<br/>" + "1. <b>ثبت المود المفقود</b>: ابحث وثبت المود ذا المعرف '" + modIdFaltante
				+ "'<br/>" + "2. <b>أزل المود المعتمد</b>: إذا لم تكن بحاجة للوظيفة، أزل المود الذي يعتمد على '"
				+ modIdFaltante + "'";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "إذا كان المود '" + modIdFaltante + "' مكتبة (مثل 'forge', 'minecraft', 'curios')، "
				+ "تأكد من أن إصدارات ماينكرافت و Forge صحيحة. "
				+ "إذا كان مودًا عاديًا، تحقق من صفحة التنزيل الخاصة به لمعرفة المتطلبات المسبقة";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>تحذير: فشل في تهيئة نظام الصوت. تم تعطيل الأصوات والموسيقى. يرتبط هذا الخطأ عادةً بتعديل SoundPhysicsMod وقد يكون ناتجًا عن تعارضات مع مكتبات صوتية أخرى.</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "خطأ في نظام الصوت";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "الخطأ مرتبط عادةً بـ <b>SoundPhysicsMod</b>. تحقق مما إذا كنت تستخدم أحدث إصدار متوافق مع إصدار ماينكرافت الخاص بك";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "إذا كنت تستخدم تعديلات صوت أخرى (مثل Sound Filters، Dynamic Surroundings، إلخ)، جرب إزالة SoundPhysicsMod مؤقتًا لمعرفة ما إذا كان ذلك يحل التعارض";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "تحقق من مجلد <b>logs</b> للعثور على رسائل إضافية تتعلق بـ LWJGL أو OpenAL، والتي قد تشير إلى مشاكل في مكتبات الصوت الأساسية";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("خطأ حرج: الصنف '").append(nombreClase)
				.append("' مسجل كـ listener للأحداث لكنه لا يحتوي على أساليب صالحة. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("يوجد هذا الصنف في التعديلات التالية: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", وآخرين...");
			mensaje.append("</b>. ");
		}

		mensaje.append("يحدث هذا عندما يتم تسجيل صنف للاستماع إلى الأحداث دون وجود أساليب معلمة بـ @SubscribeEvent.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "صنف مسجل بدون مستمعي أحداث";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("الصنف المشكل موجود في هذه التعديلات: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", وآخرين...");
			paso.append("</b>. هذه التعديلات تحاول تسجيل أحداث دون أساليب صالحة");
			return paso.toString();
		}
		return "تم تسجيل الصنف <b>" + nombreClase
				+ "</b> للاستماع إلى الأحداث لكنه لا يحتوي على أساليب معلمة بـ <b>@SubscribeEvent</b>. استخدم وظيفة <b>شجرة المودات</b> لتحديد التعديل الذي يحتوي على هذا الصنف";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "في الكود المصدري، تأكد من أن الصنف <b>" + nombreClase + "</b> يحتوي على أسلوب واحد على الأقل بـ: "
				+ "<b>@SubscribeEvent public void اسم_الأسلوب(حدث_محدد حدث) { ... }</b>. "
				+ "إذا كان صنفًا داخليًا، تأكد أنه ليس ثابتًا (static)";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("بالنسبة للتعديلات المعروفة (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", إلخ.");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("اتصل بمطوّر هذا التعديل لإصلاح المشكلة. ");
			} else {
				paso.append("اتصل بمطوري هذه التعديلات لإصلاح المشكلة. ");
			}
		}

		paso.append("إذا كنت المطور، احذف تسجيل هذا الصنف في EventBus أو أضف أساليب @SubscribeEvent صالحة");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>خطأ حرج: حدث استثناء 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' أثناء معالجة الملف '"
				+ nombreArchivo
				+ "'. يشير هذا الخطأ إلى أن البرنامج المُشغل فشل في تنزيل أو استخراج ملفات الحزمة بشكل صحيح. "
				+ "الرسالة 'zip END header not found' تُظهر أن ملف JAR ناقص أو تالف، وهو أمر شائع جدًا في البرامج التي لا تُدار جيدًا عند تنزيل ملفات كبيرة. "
				+ "يؤثر هذا المشكلة بشكل رئيسي على مستخدمي Twitch/CurseForge، Technic Launcher، وخصوصًا مستخدمي Luna Pixel، لأن هذه البرامج غالبًا ما تفشل في التحقق من سلامة الملفات المحملة. "
				+ "ينبغي على مستخدمي Luna Pixel التفكير في استخدام ATLauncher كبديل أكثر استقرارًا، حيث يتعامل بشكل أفضل مع سلامة الملفات ويتجنب هذا الخطأ تحديدًا. "
				+ "لا يمكن للنظام تحميل التعديلات لأن تنسيق ZIP تالف، مما يمنع Forge من قراءة الموارد اللازمة لبدء اللعبة.</b>";
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "خطأ UnionFileSystem - ملف تالف";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		return "أعد تثبيت الحزمة بالكامل من البداية";
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "إذا كنت تستخدم Luna Pixel، فانتقل إلى ATLauncher";
	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "تحقق من اتصال الإنترنت ومساحة القرص قبل إعادة التثبيت";
	}

	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "هل ترغب في تمكين ProxySysOutSysErr؟\n\n"
				+ "يمنح هذا الخيار برنامج CrashDetector وصولاً إلى System.out و System.err عندما لا يوفر المشغل سجلات.\n\n"
				+ "يجب تمكينه فقط عندما لا تتمكن من لصق السجل يدويًا.\n\n"
				+ "تحذير: قد يتسبب هذا في تداخل مع بعض التعديلات أو المشغلات.\n\n"
				+ "يجب إعادة تشغيل اللعبة/التطبيق لتصبح التغييرات سارية.";
	}

	@Override
	public String confirmacionTitulo() {
		return "تأكيد";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "تم تمكين ProxySysOutSysErr بنجاح.\n\n" + "يجب إعادة تشغيل CrashDetector لتصبح التغييرات سارية.";
	}

	@Override
	public String informacionTitulo() {
		return "معلومات";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("خطأ حرج: تم تهيئة AzureLib وGeckoLib مبكرًا جدًا! ");
		} else if (azureLibError) {
			mensaje.append("خطأ حرج: تم تهيئة AzureLib مبكرًا جدًا! ");
		} else if (geckoLibError) {
			mensaje.append("خطأ حرج: تم تهيئة GeckoLib مبكرًا جدًا! ");
		}

		mensaje.append("يحدث هذا الخطأ عند محاولة استخدام تعديلات Fabric مع إصدارات غير Fabric من هذه المكتبات. ");

		if (connectorPresente) {
			mensaje.append(
					"تم اكتشاف تعديل توافق (Sinytra Connector أو specialcompatibilityoperation)، مما يشير إلى أنك تحاول تشغيل تعديلات Fabric في بيئة Forge أو FeatureCreep. ");
			mensaje.append("تحقق من خطأ 'فشل تهيئة FabricMC' في السجلات لتحديد التعديل المسبب للمشكلة. ");
		}

		mensaje.append(
				"AzureLib وGeckoLib مكتبات أساسية لتعديلات الرسوم المتحركة، لكن يجب أن تتطابق مع النظام الأساسي الصحيح (Fabric أو Forge). ");
		mensaje.append("لا يمكن للعبة تحميل تعديلات الرسوم المتحركة بشكل صحيح بسبب هذا التعارض في التهيئة.");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "تهيئة المكتبة مبكرًا جدًا";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "تحقق من خطأ 'فشل تهيئة FabricMC' في السجلات لتحديد التعديل المسبب للمشكلة";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "تأكد من استخدامك للإصدار الصحيح من AzureLib/GeckoLib لنظامك (Forge أو Fabric)";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError() + "'>خطأ حرج: عدم التوافق بين C2ME وتعديلات الاتصال. "
				+ "يحدث هذا الخطأ لأن C2ME يحاول الوصول إلى مكونات داخلية في جافا مقيدة في البيئات التي تحتوي على "
				+ "Sinytra Connector أو specialcompatibilityoperation أو تعديلات توافق Fabric/Forge أخرى. "
				+ "<b>C2ME غير متوافق مع هذه البيئات، ولكن <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> هو البديل الموصى به</b> الذي يعمل بشكل صحيح "
				+ "مع تعديلات الاتصال. لا يمكن للعبة البدء بسبب تعارض أذونات الأمان في جافا.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "عدم توافق C2ME مع تعديلات الاتصال";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "أزل C2ME من مجلد التعديلات";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "قم بتنزيل وتثبيت <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> بدلاً منه (متوافق مع Sinytra Connector)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "تأكد من تحديث جميع تعديلات الاتصال (مثل Sinytra Connector) إلى أحدث إصدار";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError() + "'>خطأ حرج: فشل تحميل إضافة JEI للتعديل '" + modId
				+ "'. الفئة '" + nombreClase + "' (معرّف الإضافة: '" + pluginId
				+ "') تسببت في خطأ يؤدي إلى تعطل اللعبة أثناء البدء. "
				+ "تحدث هذه المشكلة عندما يكون لدى تعديل ما تكامل JEI غير متوافق أو معطوب يعطل عملية تهيئة اللعبة.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "إضافة JEI فاشلة - تسبب تعطّل";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "التعديل <b>" + modId
				+ "</b> يحتوي على إضافة JEI معطوبة تسبب التعطّل. استخدم وظيفة <b>شجرة التعديلات</b> لتأكيد التعديل المسبب للمشكلة";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "أزل مؤقتًا التعديل <b>" + modId + "</b> من مجلد التعديلات للتحقق مما إذا كان سيحل مشكلة التعطّل";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "ابحث عن تحديثات للتعديل <b>" + modId
				+ "</b> أو اتصل بمطوّره لتقديم تقرير عن المشكلة المتعلقة بإضافة JEI. "
				+ "في الوقت الحالي، يجب إزالة التعديل لتمكين بدء تشغيل اللعبة";
	}

	@Override
	public String tituloLectador() {
		return "قارئ السجلات - كاشف التوقف";
	}

	@Override
	public String obtenerTituloLeyenda() {
		return "أساطير الألوان";
	}

	@Override
	public String obtenerErrorEnLeyenda() {
		return "أخطاء حرجة";
	}

	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "تتبعات المكدس";
	}

	@Override
	public String obtenerTituloError() {
		return "خطأ";
	}

	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "حدث خطأ أثناء معالجة السطر المحدد";
	}

	@Override
	public String obtenerNombreError() {
		return "اسم الخطأ";
	}

	@Override
	public String obtenerDescripcionError() {
		return "وصف تفصيلي";
	}

	@Override
	public String obtenerSeleccionarConsola() {
		return "اختر السجل";
	}

	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "خطأ غير معروف";
	}

	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "تم اكتشاف خطأ حرج في السجلات. " + "تحقق من تتبع المكدس لتحديد السبب الجذري.";
	}

	@Override
	public String obtenerErrorLecturaArchivo() {
		return "تعذر قراءة ملف السجل. " + "تأكد من وجود الملف وامتلاك أذونات القراءة.";
	}

	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "محلل السجلات";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("خطأ حرج: فشل في تسجيل المشتركين التلقائيين للتعديل '").append(modId).append("'. ");

		mensaje.append("الصنف المشكل: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("يوجد هذا الصنف في: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", وآخرين...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"يحدث هذا الخطأ عندما يحاول تعديل تسجيل صنف كمشترك في الأحداث تلقائيًا، لكن لا يمكن تحميل الصنف. ");
		mensaje.append("<b>تحقق من أخطاء أخرى في السجل، فقد يكون السبب الحقيقي في تحميل سابق فاشل</b>.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "فشل في تسجيل المشتركين التلقائيين";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "التعديل <b>" + modId + "</b> يحاول تسجيل الصنف <b>" + nombreClase
				+ "</b> كمشترك تلقائي، لكن العملية فشلت. تحقق من وجود هذا الصنف ووصوله";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("الصنف المشكل <b>" + nombreClase + "</b> موجود في هذه الملفات: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", وآخرين...");
			paso.append("</b>. ");
			paso.append("استخدم وظيفة <b>شجرة المودات</b> لتحديد الملف الدقيق الذي يحتوي الصنف المشكل");
			return paso.toString();
		}
		return "الصنف <b>" + nombreClase + "</b> غير موجود في أي ملف تعديل. تحقق من تثبيت التعديل <b>" + modId
				+ "</b> بشكل صحيح. استخدم وظيفة <b>شجرة المودات</b> للمساعدة في تحديد المشكلة";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "حدث التعديل <b>" + modId + "</b> إلى أحدث إصدار متوافق مع إصدار ماينكرافت و Forge لديك. "
				+ "إذا استمرت المشكلة، اتصل بمطوّر التعديل وبلغه بالخطأ مع ذكر الصنف المشكل";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "راجع <b>أخطاء أخرى في السجل</b> قبل هذه الرسالة، فقد يكون السبب الحقيقي في تحميل سابق فاشل. "
				+ "أحيانًا يمنع خطأ سابق تحميل الصنوف اللازمة لتسجيل الأحداث";
	}

	@Override
	public String limpiado() {
		// TODO Auto-generated method stub
		return "تم التنظيف";
	}

	@Override
	public String original() {
		// TODO Auto-generated method stub
		return "أصلي";
	}

	@Override
	public String verEnConsola() {
		// TODO Auto-generated method stub
		return "عرض في السجل";
	}

	@Override
	public String advertencia() {
		// TODO Auto-generated method stub
		return "تحذير";
	}

	@Override
	public String fatal() {
		// TODO Auto-generated method stub
		return "مortal";
	}

	@Override
	public String noRegistroDeBattly() {
		// TODO Auto-generated method stub
		return "BattlyLauncher no tiene un registro ni una consola para copiando. Puedes usar ProxySysOutSysErr para interceptar STDOUT y STDERR y reiniciar el juego pero ProxySysOutSysErr puede conflictar con mods modificando STDOUT o STDERR";
	}

	@Override
	public String noRegistroDeNightWorld() {
		// TODO Auto-generated method stub
		return "Necesitas hablicar modo de depuración en la configuracion de NightWorld para obtener un registro de lanzer. Es muy importante especialemente por que tiene STDOUT y STDERR";
	}

	@Override
	public String noRegistroDeMCServidor() {
		// TODO Auto-generated method stub
		return "Necesitas guardar o pegar el contenido de la Terminal de tu servidor porque tiene informacion no en otro registros incluyendo STDOUT, STDERR, y otras errores. Por favor pegar el contenido de la ultima sesion. Para la future, puedes guardar el contenido de la terminal al archivo cd_launcherlog Para evitar tener que pegarlo, añade >> cd_launcherlog después del comando, como se muestra en la imagen. Ten en cuenta que esto impedirá que se muestre en la terminal; solo aparecerá en ese archivo una vez hecho esto.";
	}

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("خطأ حرج: تم اكتشاف محول LexForge في بيئة NeoForge. ");
		sb.append("</b>");

		sb.append("الصفحة المتورطة: <b>").append(claseReceptora).append("</b>. ");
		sb.append("الواجهة المتأثرة هي <b>").append(interfazObjetivo).append("</b> ");
		sb.append("ويتعذر العثور على الطريقة <b>").append(firmaMetodoFaltante).append("</b>. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("تم العثور على الصف في: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", وآخرين...");
			sb.append("</b>. ");
		} else {
			sb.append("لم يتم العثور على ملفات JAR تحتوي هذه الصفحة؛ قد تكون مُظلَّلة أو مضمنة كـ jar-in-jar. ");
		}

		sb.append("يحدث هذا الفشل عندما يتم تحميل محول/خدمة من ModLauncher تم تجميعها لـ MinecraftForge/LexForge ");
		sb.append("ضمن بيئة NeoForge باستخدام إصدار غير متوافق من واجهة برمجة تطبيقات ModLauncher. ");
		sb.append("قم بتحديث المكوّن أو استبدله بإصدار مخصص لـ NeoForge.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "محوّل LexForge مستخدم في NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "حدد المحول غير المتوافق: <b>" + claseReceptora + "</b>. " + "الواجهة المتوقعة هي <b>" + interfazObjetivo
				+ "</b> ويتعذر العثور على الطريقة <b>" + firmaMetodoFaltante + "</b>. "
				+ "تحقق مما إذا كان التعديل يسجل هذه الصفحة في <b>META-INF/services</b>، ثم قم بحذفها أو تعطيلها في NeoForge.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("موقع المود(التعديلات) المتورط(ة): <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", وآخرين...");
			sb.append("</b>. ");
		} else {
			sb.append(
					"لم يتم العثور على ملفات JAR تحتوي هذه الصفحة. تحقق من التبعيات المضمّنة (jar-in-jar) والمظللة. ");
		}
		sb.append("أزل مؤقتًا تلك الملفات أو استخدم إصدارات متوافقة مع NeoForge للتأكد من المصدر.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "استبدل المكوّن بإصدار مخصص لـ NeoForge أو أعد ترجمته باستخدام "
				+ "إصدار ModLauncher الذي يستخدمه NeoForge. تجنب الملفات الثنائية القديمة من LexForge/MinecraftForge.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "نظّف مجلد التعديلات وأزل التكرارات من نوع jar-in-jar. امسح ذاكرة التخزين المؤقت للرامب إن لزم الأمر "
				+ "وأعد التشغيل للتحقق من عدم تحميل أي محولات من LexForge.";
	}

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("لا يمكن لـ WaterMedia البدء: Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("غير متوافق.</b> ");
		sb.append("أزل Xenon واستخدم Embeddium أو Sodium. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("تم اكتشافه في: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", وآخرين...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia غير متوافق مع Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "تم اكتشاف " + label + " غير متوافق مع WaterMedia. قم بإزالته من الملف الشخصي.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("المواقع: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", وآخرين...");
			sb.append("</b>. قم بحذف هذا الملف JAR.");
			return sb.toString();
		}
		return "لم يتم العثور على ملفات JAR. تحقق من مجلد التعديلات وأزل Xenon.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "ثبّت Embeddium أو Sodium كبديل وأعد تشغيل اللعبة.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "خطأ في الضغط (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>تم إغلاق Deflater أثناء نسخ موارد TACZ.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("مرتبط بـ: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", وآخرين");
			sb.append("</b>. ");
		}
		sb.append("<br/><b>الحل:</b> في <code>tacz/tacz-pre.toml</code> عيّن <code>DefaultPackDebug=true</code>. ")
				.append("إذا لزم الأمر، قم أولاً بإنشاء خريطة ثم فعّلها.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "في ملف tacz/tacz-pre.toml عيّن DefaultPackDebug=true. إذا لزم الأمر، قم بإنشاء خريطة أولاً ثم فعّلها.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "وظائف الكثافة غير المرتبطة";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>تَنْصُر دوال الكثافة مفقودة في السجل.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("المفقودة: ");
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
				"<br/><b>الحل:</b> قم بتثبيت أو تفعيل الـ mod/datapack الذي يُعرِّف تلك الدوال ثم أعد التشغيل. سبب آخر شائع لهذه المشكلة هو أنك تمتلك الـ mod المطلوب، لكنه يعاني من خلل أو تعارض مع mod آخر؛ على سبيل المثال، يعاني Terralith من مشاكل كثيرة ويمكن أن يسبب هذا الخطأ وغيره، بما في ذلك أخطاء JSON.");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "قم بتثبيت أو تفعيل المود/حزمة البيانات التي توفر هذه الوظائف وأعد تشغيل اللعبة.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// رسالة قصيرة، بلون الخطأ، تشير صراحة إلى المود
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("مُدخل التسجيل غير موجود: ").append(claveFaltante).append(". ");
		sb.append("شائع مع إصدار ألفا من Steam & Railways لـ Create 6.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (ألفا)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "أزل أو استبدل إصدار ألفا من Steam & Railways لـ Create 6 بإصدار متوافق.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// قصير، بلون الخطأ ونصيحة مباشرة
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("تضارب في التحميل: استخدام Multiworld مع Sodium/Embeddium/Rubidium يتسبب في ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance). ")
				.append("اقتراح: أزل Multiworld أو أحد تعديلات الأداء، أو استخدم إصدارات متوافقة.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "تضارب: Multiworld مع تعديلات الأداء";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "أزل Multiworld أو Sodium/Embeddium/Rubidium، أو حدّث إلى إصدارات متوافقة مع بعضها.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "كشف Sodium عن برنامج تشغيل رسومي غير متوافق. "
				+ "قم بتحديث برنامج تشغيل وحدة معالجة الرسوميات (GPU) إلى الحد الأدنى المطلوب أو اتبع دليل Sodium."
				+ "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "خطأ في سياق OpenGL";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "فشل OpenGL: لا يوجد سياق حالي أو أن الوظيفة غير متوفرة في هذا السياق. "
				+ "قد يكون أيضًا مشكلة في تعريفات بطاقة الفيديو." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "قم بتحديث/إعادة تثبيت تعريفات وحدة معالجة الرسوميات (GPU) وأعد التشغيل؛ عطل أي طبقات علوية وجرب اللعب بدون تعديلات الأداء.";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "تم نسخ الرابط إلى الحافظة.";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		return "البحث داخل الأرشيفات (.zip/.jar/.war/.ear/.fpm/.rar لـ Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطأ في دقة الملمس: لا يمكن تحميل " + recurso
				+ " - الحجم: " + tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "خطأ في دقة الملمس";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "يحدث هذا الخطأ عندما تكون الملمسات كبيرة جدًا أو عند وجود عدد كبير جدًا من حزم الموارد. "
				+ "حاول استخدام حزم موارد ذات دقة أقل أو قم بإزالة بعض حزم الموارد. "
				+ "تأكد من أنك لم تقم بإضافة ملمسات مخصصة بدقة أعلى من المسموح بها.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطأ في خدمات ModLauncher: المسار يحتوي على أحرف غير صالحة. "
				+ "لا يمكن لخدمات ModLauncher معالجة المسارات التي تحتوي على أحرف غير ASCII أو أحرف خاصة. "
				+ "من الأحرف المسببة للمشاكل: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요، وخصوصًا الرمز '\"' عندما يكون في نهاية الاسم. "
				+ "تشمل مكونات الخدمات الشائعة في ModLauncher كلاً من CrashDetector، "
				+ Config.obtenerInstancia().obtenerNombreCD()
				+ "، FeatureCreep، Vivicraft، Optifine، Sodium، clonos، Iris Shaders/Oculus، MixerLogger، CrashAssistant وSintrya Connector. "
				+ "يمكنك إزالة جميع الخدمات، لكن قد تظهر مشاكل أخرى بسبب اسم المسار. "
				+ "الحل: أعد تسمية المجلد ليحتوي فقط أحرف ASCII (a-z, A-Z, 0-9)، دون مسافات أو رموز خاصة.</b>";
	}

	@Override
	public String nombre_error_modlauncher_path() {
		return "خطأ في مسار ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "يحدث هذا الخطأ عندما يحتوي مسار المثيل على أحرف غير ASCII أو رموز خاصة. "
				+ "لا يمكن لخدمات ModLauncher التعامل مع هذه المسارات. "
				+ "الحل: قم بإعادة تسمية المثيل باستخدام أحرف ASCII فقط (a-z, A-Z, 0-9)، وتجنب المسافات والرموز الخاصة. "
				+ "ولاحظ بعناية الرمز '\"' فهو مشكلة كبيرة، خصوصًا عند وضعه في نهاية الاسم.";
	}

	@Override
	public String tituloEditorCodice() {
		return "محرر كوديس";
	}

	@Override
	public String nuevo() {
		return "جديد";
	}

	@Override
	public String actualizarSeleccionado() {
		return "تحديث المحدد";
	}

	@Override
	public String eliminarSeleccionado() {
		return "حذف المحدد";
	}

	@Override
	public String exportarJSON() {
		return "تصدير JSON...";
	}

	@Override
	public String guardarTodo() {
		return "حفظ الكل";
	}

	@Override
	public String general() {
		return "عام";
	}

	@Override
	public String id() {
		return "المعرف";
	}

	@Override
	public String paraBuscar() {
		return "النص للبحث";
	}

	@Override
	public String filtro() {
		return "عامل التصفية (المعرف)";
	}

	@Override
	public String criticalidad() {
		return "الأهمية (تحذير/خطأ/مميت)";
	}

	@Override
	public String prioridad() {
		return "الأولوية";
	}

	@Override
	public String lista() {
		return "التحقق";
	}

	@Override
	public String colIdioma() {
		return "اللغة";
	}

	@Override
	public String colNombre() {
		return "الاسم";
	}

	@Override
	public String colResultado() {
		return "النتيجة";
	}

	@Override
	public String vistaJson() {
		return "معاينة JSON";
	}

	@Override
	public String idiomas() {
		return "اللغات (جميعها إلزامية)";
	}

	@Override
	public String elegirFiltro() {
		return "اختر...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "اختر عامل تصفية";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "عوامل التصفية المتاحة";
	}

	@Override
	public String faltanCampos() {
		return "أكمل جميع الحقول العامة الإلزامية.";
	}

	@Override
	public String critInvalida() {
		return "أهمية غير صالحة. استخدم: تحذير، خطأ أو مميت.";
	}

	@Override
	public String filtroNoExiste() {
		return "عامل التصفية المحدد غير موجود.";
	}

	@Override
	public String faltanIdiomas() {
		return "أكمل الاسم والنتيجة لكل اللغات:";
	}

	@Override
	public String verificacionInvalida() {
		return "فحص غير صالح. راجع الحقول.";
	}

	@Override
	public String guardadoOk() {
		return "تم الحفظ بنجاح.";
	}

	@Override
	public String editorCodiceBoton() {
		return "إضافة أسباب";
	}

	@Override
	public String descripcionEditorCodice() {
		return "يمكنك تسجيل الأسباب هنا. تحتاج إلى معرف (ID)، وهو نص بدون رموز خاصة أو أحرف مشوّهة أو فراغات. بالنسبة للمرشحات، يمكنك استخدام \"السطر يحتوي\" للبحث عن نص داخل سطر، و\"الكل يحتوي\" إذا كان السجل يحتوي على نص معين، و\"تعبير عادي في السطر\" إذا كان السطر يطابق تعبيرًا منتظمًا، و\"تعبير عادي في الكل\" (نُوصي باستخدام إصدارات السطر). يجب تحديد المستوى: FATAL (قاتلة)، ERROR (خطأ) أو ADVERTENCIA (تحذير) لتحديد الألوان. لكل لغة، يجب كتابة اسم ونتيجة ستظهر على الشاشة. يمكنك إضافة المزيد من عمليات التحقق أو حذف البعض. يتم الحفظ عند الإكمال.";
	}

	@Override
	public String descartarCambios() {
		return "هل تريد تجاهل التغييرات غير المحفوظة في التحقق الحالي؟";
	}

	@Override
	public String confirmacion() {
		return "تأكيد";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "هل ترغب في حفظ التغييرات قبل الخروج؟";
	}

	@Override
	public String salirSinGuardar() {
		return "الخروج بدون حفظ";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("خطأ حرج: فشل في تحميل خدمة من modlauncher (IDependencyLocator).<br>");
		sb.append("🔹 <b>الصف المسبب للمشكلة:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>التعديل المتضرر:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append(
					"🔸 <b>التعديل غير معروف.</b> راجع التعديلات المثبتة حديثًا أو الخاصة بالتطوير أو ذات الحزمة السيئة.<br>");
		}

		sb.append("🔸 <b>السبب:</b> ملف <code>META-INF/services/...</code> الخاص بالتعديل تالف، ");
		sb.append("أو غير متوافق مع إصدار Forge/NeoForge الحالي، أو أن التعديل مخصص لإصدار خاطئ.<br>");
		sb.append("🔸 <b>النتيجة:</b> لا يمكن لـ Forge/NeoForge تسجيل تبعيات التعديل، ");
		sb.append("مما يمنع بدء تشغيل اللعبة.<br>");
		sb.append("🔸 <b>الحل:</b> حدّث أو أعد تثبيت أو احذف التعديل المسبب للمشكلة. ");
		sb.append("إذا كنت تستخدم تعديلات قيد التطوير، فتأكد من أنها تم تجميعها للإصدار الدقيق من Forge/NeoForge.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "خطأ في تهيئة الخدمة (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. حدّد التعديل المسبب: راجع التعديلات المثبتة مؤخرًا أو تلك الخاصة بالتطوير.";
		}
		return "1. التعديل المسبب هو: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. حدّث أو أعد تثبيت أو احذف التعديل. تأكد من استخدام إصدار متوافق مع إصدار Forge/NeoForge الخاص بك.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>خطأ حرج: طريقة غير موجودة.</b><br>"
				+ "حاول التعديل استدعاء الطريقة <b style='color:#" + colorCodigo + "'>" + metodo + "</b>، "
				+ "والتي لا توجد في هذا الإصدار من اللعبة أو تعديل آخر.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "طريقة غير موجودة (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. يحدث هذا الخطأ عندما يكون التعديل غير متوافق مع إصدار اللعبة الحالي أو مع تعديل آخر.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. حدّث جميع التعديلات المعنية. إذا استمر الخطأ، قم بالإبلاغ عنه لمطوّر التعديل المتأثر.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>خطأ حرج: حقل غير موجود.</b><br>"
				+ "حاول التعديل الوصول إلى الحقل <b style='color:#" + colorCodigo + "'>" + campo + "</b>، "
				+ "الذي لا يوجد في هذا الإصدار من اللعبة أو تعديل آخر.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "حقل غير موجود (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. يحدث هذا الخطأ عادةً عندما يكون التعديل غير متوافق مع إصدار اللعبة الحالي أو مع تعديل آخر.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. حدّث جميع التعديلات المتضررة. إذا استمرت المشكلة، اتصل بمطوّر التعديل الذي تسبب في الخطأ.";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();

		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>هل تحتاج مساعدة؟</strong><br>"
				+ "  إذا كنت لا تعرف كيفية الإصلاح أو لم تُذكر المشكلة هنا، يمكنك الحصول على مساعدة عبر شبكاتنا الاجتماعية. "
				+ "  استخدم الزر <img src='" + iconoCompartir
				+ "' alt='مشاركة' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>مشاركة</strong> للحصول على روابط للسجلات ونتائج التحليل لفريق الدعم. "
				+ "  إذا كنت منشئ حزمة تعديلات أو شركة، فقم بتعديل <code>crash_detector/plantilla.htm</code> "
				+ "  لتخصيص الروابط الخاصة بفريقك." + "</div>";
	}

	@Override
	public String restablecerPlantilla() {
		return "إعادة تعيين القالب";
	}

	@Override
	public String restablecer() {
		return "إعادة تعيين";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		return "هل تريد إعادة تعيين " + nombreImagen + " إلى الإعدادات الافتراضية؟";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		return "هل تريد إعادة تعيين القالب إلى الإعدادات الافتراضية؟";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>تتعذر العثور على بعض كلاسات AzureLib. إذا كنت تمتلك بالفعل AzureLib، يُرجى تثبيت إصدار سابق من AzureLib قبل 8 أكتوبر 2025. كان هذا شائعاً. إذا لم يكن لديك AzureLib، فثبّت الإصدار الحالي.</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "التعديل <code>healight</code> يتسبب في خطأ حرج: <code>java.lang.NoSuchFieldError: INT</code>. "
				+ "يحدث هذا الخطأ لأن التعديل يحاول الوصول إلى حقل لم يعد موجودًا في إصدار MCForge 47.10 وما فوق من ماينكرافت 1.20. "
				+ "لا يمكن للعبة البدء بسبب هذه المشكلة.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• احذف أو حدّث التعديل <code>healight</code>. "
				+ "الإصدار الحالي غير متوافق مع MinecraftForge 47.10 لإصدار 1.20.1. "
				+ "ابحث عن إصدار أحدث من التعديل أو فكّر في استخدام بديل.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "خطأ حرج: healight - الحقل 'INT' غير موجود";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("الصف <code>").append(clase)
				.append("</code> لا يُنفذ الطريقة المطلوبة:<br>").append("<code>").append(metodo).append("</code><br>")
				.append("من الواجهة <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br><br>التعديل أو الملف المشبوه: <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• يحدث هذا الخطأ عندما يُعدّل تعديلاً واجهة لكنه يتجاهل طريقة إلزامية. "
				+ "قم بتحديث <b>كلا التعديلين</b> المتورطين (الذي يعرّف الواجهة والذي يُنفّذها). "
				+ "إذا كنت لا تعرف أيهما، ابحث عن الأسماء الظاهرة في رسالة الخطأ.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "طريقة واجهة غير منفذة (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "يحاول تعديل تحميل صنف من <b>جانب العميل</b> "
				+ "(<code>AnimationMetadataSection</code>) على <b>خادم مخصص</b>، وهو أمر غير ممكن. "
				+ "غالبًا ما يظهر هذا الخطأ عندما لا يقوم التعديل بفصل الكود بشكل صحيح بين العميل والخادم. "
				+ "قد يكشف وجود <code>ModernFix</code> عن هذه المشكلة، رغم أنه ليس السبب المباشر.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>حل سريع:</b> احذف مؤقتًا <code>ModernFix</code> للتأكد من أن الخادم يمكنه البدء. "
				+ "إذا نجح ذلك، فالمشكلة في تعديل آخر يقوم بتحميل أصناف العميل على الخادم.<br>"
				+ "• <b>الحل الدائم:</b> حدّد التعديل المسؤول (ابحث عن تعديلات تحتوي على موارد متحركة أو قوام مخصصة أو مكتبات رسومية) وحدّثه أو احذفه.<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "تم تحميل كلاس عميل على الخادم (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ملف تهيئة أحد تعديلات <code>Sinytra Connector</code> تالف. "
				+ "غالبًا ما يحدث هذا عندما يمتلئ الملف بحروف فارغة (<code>\\u0000</code>) "
				+ "نتيجة لإغلاق غير متوقع للعبة، أو أخطاء في الكتابة، أو تعارض بين التعديلات.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• انتقل إلى مجلد <code>config/</code> في نسختك من ماينكرافت.<br>"
				+ "• ابحث عن ملفات تهيئة تعديلات connector واحذفها.<br>"
				+ "• أعد تشغيل اللعبة: سيقوم Sinytra Connector بإنشاء ملف تهيئة جديد ونظيف.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "تهيئة Sinytra Connector تالفة";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "الملف <code>" + nombreJar
				+ "</code> تالف أو ناقص.<br>" + "لا يمكن للنظام قراءة محتواه لأن رأس الملف النهائي لـ ZIP مفقود.<br>"
				+ "غالبًا ما يحدث هذا الخطأ بعد تنزيل متقطع أو فشل في عمل المُشغل.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "ملف JAR تالف (مع اسم محدد)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>احذف الملف التالف</b> وقم بتنزيله مرة أخرى من المصدر الرسمي (مثل CurseForge، MinecraftStorage، إلخ).<br>"
				+ "• إذا كنت تستخدم مشغلًا مثل CurseForge أو Technic أو Luna Pixel، ففكر في التحول إلى <b>ATLauncher</b> أو <b>Prism Launcher</b>، "
				+ "اللذين يقومان بالتحقق بشكل أفضل من سلامة الملفات.<br>"
				+ "• تأكد من أن اتصال الإنترنت لديك مستقر أثناء التنزيل.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "تعذر تحميل العالم لأن أحد ملفات NBT الخاصة به تالفة "
				+ "(مثلًا: <code>level.dat</code>, <code>playerdata/*.dat</code>، أو القطع).<br>"
				+ "الخطأ المحدد هو: <code>UTFDataFormatException: مدخل غير سليم حول البايت " + byteCorrupto
				+ "</code>.<br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ قبل محاولة أي إصلاح، قم بعمل نسخة احتياطية كاملة من مجلد العالم.</b><br><br>"
				+ "يمكنك محاولة إصلاح الملف التالف باستخدام <b>محرر NBT</b> مثل <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>.<br>"
				+ "إذا كان التلف شديدًا، استخدم <b>محررًا ثنائيًا</b> (مثل HxD) لفحص وتصحيح البايتات غير الصالحة "
				+ "(فقط إذا كنت لديك خبرة في تنسيق NBT).<br>"
				+ "كملاذ أخير، قم باستعادة النسخة الاحتياطية أو استخدم أداة إصلاح العوالم من تعديلات مثل <code>FTB Backup</code>.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>قم بعمل نسخة احتياطية كاملة من مجلد العالم</b> قبل محاولة أي إصلاح.<br>"
				+ "• استخدم محرر NBT (مثل NBT Studio) لفتح الملف التالف وإصلاحه.<br>"
				+ "• إذا فشل ذلك، فاحصل على الملف باستخدام محرر ثنائي عند موقع البايت التالف.<br>"
				+ "• إذا لم تكن لديك خبرة، قم باستعادة العالم من نسخة احتياطية حديثة.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "عالم تالف: خطأ في تحميل بيانات NBT";
	}

	@Override
	public String problema_con_openAL() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>لديك مشكلة في OpenAL. أحيانًا يمكن أن تسببها تعريفات Nouveau، ولكن في بعض الأحيان تكون المشكلة هي أن إصدار OpenAL المضمن مع التطبيق غير متوافق مع الإصدار الموجود في توزيعتك، وتحتاج إلى استخدام الإصدار الخاص بتوزيعتك. هذا شائع بشكل خاص مع توزيعات Red Hat ومع تعديلات الصوت مثل Sound Physics Remastered. راجع هذا الدليل للحصول على مساعدة إضافية: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>كيفية إصلاح مشاكل الصوت في ماينكرافت على لينكس</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "لا يمكن تشغيل الخادم لأن ملفًا من عالمه محجوب بواسطة عملية أخرى.<br>" + "عادةً ما يحدث هذا إذا:<br>"
				+ "• هناك بالفعل نسخة من الخادم قيد التشغيل.<br>"
				+ "• برنامج مكافحة فيروسات أو مستعرض ملفات لديه مجلد العالم مفتوحًا.<br>"
				+ "• العملية السابقة لم تُغلق بشكل صحيح وتركت ملفات محجوبة.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>أغلق جميع نسخ الخادم</b> (بما في ذلك العمليات في الخلفية مثل javaw.exe).<br>"
				+ "• إذا كنت تستخدم لوحة استضافة (مثل Multicraft)، أعد تشغيل الخادم تمامًا من خلال اللوحة.<br>"
				+ "• <b>عطّل مؤقتًا برنامج مكافحة الفيروسات</b> إذا شككت في أنه يحجب الملفات.<br>"
				+ "• على الأنظمة المحلية، أغلق أي نافذة مستعرض ملفات تعرض مجلد العالم.<br>"
				+ "• إذا استمرت المشكلة، احذف يدويًا ملف <code>session.lock</code> داخل مجلد العالم (فقط إذا كنت متأكدًا من عدم وجود خادم آخر يعمل).";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "ملف العالم محجوب بواسطة عملية أخرى";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "حاول التعديل توسيع الصنف <code>"
				+ clasePadreFinal + "</code>، " + "لكن هذا الصنف أصبح الآن <b>نهائيًا</b> ولا يمكن الوراثة منه.<br>"
				+ "الصنف المسبب للمشكلة هو: <code>" + claseHija + "</code>.<br><br>"
				+ "يحدث هذا عادة عندما يكون التعديل مُجمَّع لإصدار سابق من ماينكرافت أو تعديل أساسي آخر "
				+ "قد قام بوضع وسم <code>final</code> على بعض الأصناف في الإصدارات الحديثة.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>حدّث جميع التعديلات المتورطة</b>، خاصة تلك التي قد تكون مرتبطة بالتعديل الأساسي المذكور.<br>"
				+ "• إذا استمرت المشكلة، ابحث عن إصدار من التعديل يتوافق مع إصدار ماينكرافت الحالي واعتمادياته.<br>"
				+ "• في بعض الحالات، قد يساعد حذف مؤقت للتعديل الذي يحتوي الصنف الفرعي في تأكيد السبب.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "محاولة وراثة صنف نهائي";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "أنت تستخدم <b>Rubidium</b> (نسخة منسوخة قديمة من Sodium لنظام Forge) مع <b>Iris أو Oculus</b>.<br>"
				+ "في الإصدارات الحديثة من ماينكرافت (1.19.2 فما فوق)، "
				+ "لم يواصل Rubidium مواكبة Sodium، وظهرت مشكلات في التبعيات.<br><br>"
				+ "قد يحدث هذا الخطأ أيضًا بسبب تعارض بين تعديلات الأداء (Sodium، Rubidium، Embeddium، Bedium، Xeonium، إلخ) أو Iris Shaders وتعديل آخر.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>احذف Rubidium</b> من مجلد <code>mods</code> الخاص بك.<br>"
				+ "• <b>ثبّت <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a></b>, "
				+ "النسخة المطورة والمضمنة من Sodium لنظام Forge والتي تدعم Iris/Oculus في 1.20+.<br>"
				+ "• تأكد من عدم تثبيت أكثر من نسخة من Sodium في نفس الوقت (مثل: Rubidium + Embeddium).<br>"
				+ "• إذا كنت تستخدم Oculus بدلًا من Iris، تحقق من أنه متوافق مع إصدار Forge وEmbeddium لديك.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium قديم مع Iris/Oculus (OptionInstance هو نهائي)";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "لا يمكن للتعديل <code>Simple Voice Chat</code> تشغيل خادم الصوت الخاص به لأن "
				+ "منفذ UDP مستخدم بالفعل أو عنوان IP المُعد غير صالح.<br>"
				+ "هذا لا يمنع بدء اللعبة، لكنه يعطل وظيفة الدردشة الصوتية.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• <b>أغلق أي نسخة أخرى من ماينكرافت</b> أو تطبيق يستخدم المنفذ UDP 24454.<br>"
				+ "• إذا كنت على خادم، تأكد من أن <b>أي خدمة أخرى</b> لا تستخدم هذا المنفذ.<br>"
				+ "• في إعدادات التعديل (<code>config/voicechat/</code>)، غيّر منفذ UDP إلى منفذ حر (مثلاً 24455).<br>"
				+ "• إذا كنت تستخدم عنوان IP مخصصًا، فتحقق من صحته أو اتركه فارغًا لاستخدام الإعداد الافتراضي.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "الدردشة الصوتية: منفذ UDP مشغول أو عنوان IP غير صالح";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "عنصر الكتلة <code>" + nombreBlockItem
				+ "</code> يشير إلى كتلة فارغة.<br>"
				+ "يحدث هذا الخطأ عادة في <b>إضافات Create</b> (مثل <code>dndecor</code>, <code>createdeco</code>) "
				+ "عندما تكون هناك تعارضات مع <code>Amendments</code>، <code>Moonshine</code>، أو عند تهيئة الكتل بشكل خاطئ.<br>"
				+ "<b>ملاحظة:</b> هذا ليس خطأ من <code>Amendments</code> مباشرة، بل هو عرضة لمشكلة أعمق في تحميل السجلات.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• <b>حدّث جميع التعديلات المرتبطة:</b> Create و Ammendments و Moonshine وأي إضافة (خاصةً <code>dndecor</code> و <code>createdeco</code>).<br>"
				+ "• إذا استمرت المشكلة، <b>احذف مؤقتًا إضافات Create</b> واحدة تلو الأخرى لتحديد المسبب.<br>"
				+ "• تأكد من أن <b>Amendments و Moonshine متوافقان</b> مع إصدارك من Create و Forge.<br>"
				+ "• ابحث عن إصدارات تجريبية أو نسخ معدلة حديثة من الإضافات المعيبة.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "عنصر كتلة فارغ في إضافة Create";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>")
				.append("تم العثور على تعديلات لا تنتمي إلى أي منصة نشطة (Forge، Fabric، إلخ):<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>يحدث هذا عادة عندما:<br>").append("• يتم مزج تعديلات <b>Fabric و Forge</b> في نفس المجلد.<br>")
				.append("• يتم تثبيت تعديل لإصدار غير متوافق من ماينكرافت.<br>")
				.append("• التعديل تالف أو ليس ملف JAR صالحًا.</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>تحقق من أن جميع التعديلات مخصصة لنفس المنصة</b> (Forge <b>أو</b> Fabric، وليس كليهما).<br>"
				+ "• استخدم <b>هيكل التعديلات</b> لتحديد المنصة التي يكتشفها كل ملف.<br>"
				+ "• احذف أي تعديل لا تتعرف عليه أو يكون لمنصة مختلفة.<br>"
				+ "• إذا كنت تستخدم مشغلًا مثل CurseForge أو Prism، فتأكد من ضبط الملف الشخصي بشكل صحيح.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "تعديل غير متوافق مع المحمل النشط";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "تعذر إنشاء النموذج <code>" + modid + ":"
				+ nombreModelo + "</code>.<br>" + "هذا يشير إلى أن التعديل <code>" + modid
				+ "</code> يحتوي على موارد تالفة أو ناقصة " + "أو غير متوافقة مع إصدار ماينكرافت الخاص بك.</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>حدّث التعديل</b> إلى أحدث إصدار متوافق مع بيئة اللعب الخاصة بك.<br>"
				+ "• إذا كنت تستخدم إصدارًا تجريبيًا أو معدلًا، فعد إلى الإصدار الرسمي.<br>"
				+ "• تأكد من أن ملف JAR ليس تالفًا (أعد تثبيته).<br>"
				+ "• إذا استمرت المشكلة، قم بالإبلاغ عن الخطأ لمطوّر التعديل مع تضمين هذا السجل.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "فشل في إنشاء نموذج المورد";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "تم اكتشاف تعارض حرج بين التعديلات <code>Moonlight</code> و <code>Iceberg</code>.<br>"
				+ "كلاهما يحاول تسجيل أنظمة إعادة تحميل الموارد بشكل غير متوافق، "
				+ "مما يؤدي إلى فشل في OpenGL بسبب عدم وجود سياق رسومي صالح.<br>"
				+ "هذه المشكلة شائعة عند استخدام إصدارات من Forge تتضمن محولات لتعديلات Fabric.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>حدّث كلا التعديلين</b> إلى أحدث إصداراتهما المتوافقة مع إصدار Forge الخاص بك.<br>"
				+ "• إذا استمرت المشكلة، <b>احذف مؤقتًا Iceberg</b>، لأن Moonlight غالبًا ما يكون تبعية أساسية للعديد من التعديلات الأخرى.<br>"
				+ "• تأكد من عدم وجود نسخ مكررة أو مختلطة من Forge/Fabric لهذه التعديلات.<br>"
				+ "• تحقق مما إذا كان أي تعديل آخر (مثل Supplementaries أو Citadel) يتضمن بالفعل وظائف Iceberg داخليًا.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "تعارض حرج: Moonlight مقابل Iceberg (فشل OpenGL - لا يوجد سياق)";
	}

	@Override
	public String instantanea() {
		return "لقطة";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "من آخر لقطة";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "اختر ملفًا";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "تم إنشاء اللقطة بنجاح";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "خطأ أثناء إنشاء اللقطة";
	}

	@Override
	public String consejo() {
		return "نصيحة";
	}

	@Override
	public String resultadoMuestra() {
		return "عرض النتيجة";
	}

	@Override
	public String historaDeModsDesc() {
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>نصيحة:</b> اختر ملفين من السجل للمقارنة بين قائمة التعديلات. "
				+ "  يُظهر الناتج العناصر <span style='color:%s;'>المضافة (+)</span> و"
				+ "  <span style='color:%s;'>المحذوفة (&#8722;)</span> استنادًا إلى أسماء موحّدة. "
				+ "  استخدم زر 'لقطة' لإنشاء نسخة من ملف موجود بامتداد .instantanea." + "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		return "الحصول على روابط السجلات كنص Markdown بدون تقرير";
	}

	@Override
	public String titulo_configuracion() {
		return "الإعدادات";
	}

	@Override
	public String columna_url() {
		return "الرابط";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "حدث خطأ غير متوقع أثناء المشاركة.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "حدث خطأ غير متوقع أثناء إنشاء الروابط.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "حدث خطأ غير متوقع أثناء معالجة الزر.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "لا يوجد ملف مرتبط للفتح.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "الملف غير موجود:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "تعذر فتح الملف في المحرر.\nسيتم نسخ المسار إلى الحافظة.";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "تعذر فتح الملف؛ تم نسخ المسار إلى الحافظة.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "سطح المكتب غير مدعوم؛ تم نسخ المسار إلى الحافظة.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "تواجه حدًا على عدد الطلبات. حاول استخدام موقع تسجيل آخر أو واجهة برمجة تطبيقات (API) أخرى للتسجيل.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		return "مشاركة الرابط";
	}

	@Override
	public String infoDeTrazos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "إصلاح أجزاء قمم الجذوع هي الأولوية القصوى. "
				+ "التنسيق هو المستوى، السطر. " + "جميع السجلات لها نظام ترقيم. " + Verificaciones.nl_html
				+ "بشكل عام، تحتاج إلى البحث في المستويات الأدنى في جميع السجلات؛ فالمسارات ذات المستويات العالية تكون غالبًا إيجابيات زائفة. "
				+ "من المهم استخدام مهارتك في فهم وحدة التحكم، لأن تحليل المسارات ليس دقيقًا تمامًا عند وجود العديد من المسارات."
				+ "</b>";
	}

	// --- أداة بحث كاناري للطلب (Warrant Canary) ---
	/**
	 * نص الزر الخاص بأداة بحث كاناري للطلب. مثال: "أداة بحث كاناري للطلب"
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "مُبحِث كاناري للطلب";
	}

	/**
	 * الرسالة المعروضة في مربع الحوار لإعلام أن الميزة ستكون متاحة قريبًا.
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "ستصبح هذه الميزة متاحة قريبًا.";
	}

	/**
	 * عنوان مربع الحوار الذي يُعلم بتوفر أداة بحث كاناري للطلب في المستقبل.
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "قريبًا";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		return "التعديلات غير المتوافقة مع Crash Assistant (خطأ)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		return "حزمة تعديلات غير متوافقة مع CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>يحتوي Crash Assistant على قائمة بتعديلات يقول إنها غير متوافقة، لكن ليس لدينا أدلة على ذلك، والخطأ باللغة الإنجليزية فقط. إذا أردت اللعب باستخدام هذه التعديلات، يمكنك تحرير الملف <code>config/crash_assistant/config.toml</code> وتغيير <code>enabled = true</code> في القسم [compatibility] إلى <code>enabled = false</code>.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>يمكن لـ Crash Assistant أن يُصنّف التعديلات على أنها غير متوافقة، لكن في بعض الأحيان يكون هذا التصنيف خاطئًا، والرسالة باللغة الإنجليزية فقط. إذا أردت استخدام هذه التعديلات، يمكنك تحرير الملف <code>config/crash_assistant/problematic_mods_config.json</code> وتغيير قيمة <code>should_crash_on_startup</code> من <code>true</code> إلى <code>false</code>.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "خطأ: التعديل '" + modId
				+ "' يتطلب التعديل '" + dependencia + "'. حاليًا، " + actual + "." + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "خطأ: التعديل '" + modId
				+ "' يتطلب الإصدار '" + requerido + "' أو أحدث من '" + dependencia + "'، لكن التعديل غير مثبت."
				+ "</span>";
	}

	// في كلاس MonitorDePID.idioma (إضافة هذه الطريقة)
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "خطأ: التعديل '" + modId
				+ "' غير متوافق مع الإصدار الحالي من '" + dependencia + "'. "
				+ "يجب عليك حذف التعديل 'Iris/Oculus & GeckoLib Compat' لأنه غير متوافق مع Superb Warfare ولا يعمل مع أحدث إصدار من GeckoLib. "
				+ "الإصدار الحالي: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "خطأ: تعذر تنفيذ المهمة للصنف '" + clase + "'. "
				+ "هذا الخطأ شائع مع التعديلات التي لا تكون متوافقة فيما بينها أو التي لديها تعارضات مع تعديلات أخرى مثبتة.";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "فشل تنفيذ المهام";
	}

	public String recomendacion_fallos_ejecucion() {
		return "يحدث هذا النوع من الأخطاء عادةً بسبب التعارضات بين التعديلات. "
				+ "وهو شائع بشكل خاص مع التعديلات التي لا تعمل بشكل صحيح مع ConnectorMod.";
	}

	public String info_clase_problematica() {
		return "الصنف المسبب للمشكلة:";
	}

	public String ver_en_log() {
		return "عرض في السجل";
	}

	public String no_se_encontraron_clases_problema() {
		return "لم يتم العثور على أصناف محددة تعاني من مشاكل في التنفيذ.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تم اكتشاف تعارض حرج بين OptiFine وEntity Model Features (EMF). "
				+ "هذان التعديلان غير متوافقين ويؤديان إلى فشل في الإدخال يمنع بدء اللعبة." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "تعارض بين OptiFine وEntity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "أزل إما OptiFine أو Entity Model Features، لأنهما غير متوافقين مع بعضهما البعض.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "تم اكتشاف تعارض حرج بين OptiFine وFusion. "
				+ "هذان التعديلان غير متوافقين ويؤديان إلى فشل في الإدخال يمنع بدء اللعبة." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "تعارض بين OptiFine وFusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "أزل إما OptiFine أو Fusion، لأنهما غير متوافقين مع بعضهما البعض.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "يحتاج Flywheel (مطلوب من قبل Create) إلى Sodium 0.6.0-beta.2 أو أحدث. إصدار Rubidium هو 0.5.3. "
				+ "فكر في استخدام <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> كبديل."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "تعارض Flywheel وإصدار Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "حدّث Sodium إلى إصدار 0.6.0-beta.2 أو أحدث، أو قم بتثبيت <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> كبديل متوافق.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تم اكتشاف تعارض حرج بين OptiFine وEpic Fight. "
				+ "هذان التعديلان غير متوافقين ويؤديان إلى فشل في الإدخال يمنع بدء اللعبة." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "تعارض بين OptiFine وEpic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "أزل إما OptiFine أو Epic Fight، لأنهما غير متوافقين مع بعضهما البعض.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "تم اكتشاف تعارض حرج بين OptiFine و Rubidium. "
				+ "هذه الوحدات غير متوافقة وتسبب فشلاً في الحقن يمنع تشغيل اللعبة." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "التعارض بين OptiFine و Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "قم بإزالة OptiFine أو Rubidium، لأنهما غير متوافقين مع بعضهما.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "يحاول FreeCam التحميل على خادم مخصص، لكنه متوافق فقط مع العميل. "
				+ "أزل FreeCam من الخادم أو تأكد من أنه مثبت فقط على العميل." + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam على خادم مخصص";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "أزل FreeCam من الخادم المخصص، لأنه يجب تثبيته فقط على العميل.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) يحاول التحميل على خادم مخصص، لكنه متوافق فقط مع العميل. "
				+ "أزل ETF من الخادم أو تأكد من أنه مثبت فقط على العميل." + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features على خادم مخصص";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "أزل Entity Texture Features من الخادم المخصص، لأنه يجب تثبيته فقط على العميل.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "يجب قبول EULA الخاص بـ Minecraft لتشغيل الخادم. "
				+ "قم بتحرير ملف eula.txt وغيّر 'eula=false' إلى 'eula=true'." + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "EULA الخاص بـ Minecraft غير مقبول";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "قم بتحرير ملف eula.txt في مجلد الخادم وغيّر 'eula=false' إلى 'eula=true'.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine يحاول التحميل على خادم مخصص، لكنه متوافق فقط مع العميل. "
				+ "أزل OptiFine من الخادم أو تأكد من أنه مثبت فقط على العميل." + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine على خادم مخصص";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "أزل OptiFine من الخادم المخصص، لأنه يجب تثبيته فقط على العميل.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تم تمييز Iron's Spellbooks بشكل خاطئ على أنه مخصص لـ 1.20.1 لكنه يستخدم طرق 1.21.1. "
				+ "المود يحاول استخدام ResourceLocation.fromNamespaceAndPath، وهو غير موجود في 1.20.1." + "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "خطأ في إصدار Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "تأكد من استخدامك للإصدار الصحيح من Iron's Spellbooks المتوافق مع إصدار ماينكرافت الخاص بك.";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تم اكتشاف تعارض حرج بين OptiFine و Embeddium. "
				+ "هذه الوحدات غير متوافقة وتسبب فشلاً في الحقن يمنع تشغيل اللعبة." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "التعارض بين OptiFine و Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "قم بإزالة OptiFine أو Embeddium، لأنهما غير متوافقين مع بعضهما.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>هذا شائع مع وحدات تعديل توليد العالم المتعارضة، خاصةً Terralinth وAmplifiedNether وNullscape وIncendium وغيرها من وحدات تعديل توليد العالم. قد تحتاج أيضًا إلى تثبيت وحدة تعديل مفقودة.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable يحاول التحميل على خادم مخصص، لكنه متوافق فقط مع العميل. "
				+ "أزل Controllable من الخادم أو تأكد من أنه مثبت فقط على العميل." + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable على خادم مخصص";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "أزل Controllable من الخادم المخصص، لأنه يجب تثبيته فقط على العميل.";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ي causing Supplementaries خطأً يمنع تحميل الخادم. "
				+ "يواجه المود مشاكل في تسجيل سلوكيات النار تؤدي إلى فشل أثناء تحميل حزم البيانات (datapacks)."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries يمنع تحميل الخادم";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "حاول تحديث Supplementaries إلى أحدث إصدار أو قم بتعطيله مؤقتًا للسماح بتحميل الخادم.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "واجه Groovy Modloader (GML) مشكلة بسبب وحدات Jackson المفقودة. "
				+ "قد يسبب بعض الوحدات مثل Valkyrien Skies هذا الخطأ لعدم تضمين جميع التبعيات المطلوبة." + "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "وحدة Jackson مفقودة في Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "قم بإزالة Groovy Modloader والوحدات المرتبطة مثل Valkyrien Skies التي قد تسبب تعارضات في التبعيات.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Every Compat وجد اسم كتلة خشب غير صالح. "
				+ "عادةً ما يعاني Every Compat من مشاكل كثيرة. لا تستخدمه!" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "اسم غير صالح في Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "تحقق من حزم الموارد أو الوحدات التي تستخدم Every Compat، فقد تحتوي على أسماء كتل غير صالحة.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تم اكتشاف رمز خطأ (-1073741819) قد يكون ناتجًا عن طبقات العرض (overlays) مثل GameCaster من Razer أو Discord أو OBS Studio أو مشاكل في تعريفات NVIDIA."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "رمز الخطأ -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "حاول تعطيل طبقات العرض مثل GameCaster أو Discord أو OBS Studio، وتأكد من أن تعريفات NVIDIA الخاصة بك محدثة.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips يتطلب Immersive Messages ك(dependencia) لكنه غير مثبت. "
				+ "قم بتثبيت Immersive Messages لكي يعمل Immersive Tooltips بشكل صحيح." + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips بدون dependencia";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "قم بتثبيت Immersive Messages، لأنه dependencia ضرورية لـ Immersive Tooltips.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins tiene un problema de compatibilidad con Apoli Mod donde ItemStack no puede ser casteado a EntityLinkedItemStack. "
				+ "Esto es común en versiones superiores a 6.6.0. Considera usar una versión anterior o cambiar entre versiones de Fabric y Forge."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "Error de casteo en Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "Usa una versión de Medieval Origins 6.6.0 o anterior, o intenta cambiar entre versiones de Fabric y Forge del mod.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "يسبب Reign of Nether خطأً مع Registry Object غير موجود في MusicManager. "
				+ "يرتبط هذا المشكل بمixin الخاص بـ MusicManager من Reign of Nether." + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "خطأ MusicManager في Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "حاول تحديث Reign of Nether أو إزالته مؤقتًا لحل هذا الخطأ.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "يدعم YesSteveModel خادم YSM فقط على Linux أو Android. "
				+ "تم إصلاح هذه المشكلة في الإصدارات الأحدث منذ 23 نوفمبر 2025 على Modrinth." + "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel غير متوافق مع Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "قم بتحديث YesSteveModel إلى إصدار أحدث من Modrinth، حيث تم إصلاح المشكلة بعد 23 نوفمبر.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تم اكتشاف تعارض حرج بين Moving Elevators و OptiFine. "
				+ "هذه الوحدات غير متوافقة وتسبب فشلاً في الحقن يمنع تشغيل اللعبة." + "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "التعارض بين Moving Elevators و OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "قم بإزالة OptiFine أو Moving Elevators، لأنهما غير متوافقين مع بعضهما.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تم اكتشاف تعارض حرج بين Fabric API (fabric-resource-loader-v0) و OptiFine. "
				+ "هذه الوحدات غير متوافقة وتسبب فشلاً في الحقن يمنع تشغيل اللعبة." + "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "التعارض بين Fabric API و OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "قم بإزالة OptiFine أو حدّث Fabric API إلى إصدار متوافق.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "يحتوي أحد الوحدات على ITransformationService معطوب لا يمكن إنشاؤه: " + claseProveedor + ". "
				+ "يجب إزالة هذا المود لتمكين تحميل اللعبة." + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "ITransformationService معطوب";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "أزل المود الذي يحتوي على الصنف " + claseProveedor + "، لأنه يحتوي على ITransformationService معطوب.";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError() + "'>يحتوي أحد الوحدات على مواصفات إصدار غير صالحة. "
				+ "يجب أن يكون الإصدار محاطًا بأقواس مربعة. "
				+ "يمكنك استخدام أداة grep/greprf من اللوحة الجانبية بالبحث عن الإصدار </span>" + version
				+ "<span style='color:#" + config.obtenerColorError() + "'> لتحديد الوحدة التي بها المشكلة.</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "إصدار غير صالح في الوحدة";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "استخدم أداة grep/greprf من اللوحة الجانبية للبحث عن الإصدار المشكل وتحديد الوحدة التي تحتويه.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تم اكتشاف خطأ stack smashing أدى إلى إنهاء العملية. "
				+ "قد يكون هذا ناتجًا عن مشاكل في Early Window في Forge/NeoForge/PillowMC أو مع LWJGL 3.2.2 والإصدارات الأحدث."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "تم اكتشاف Stack Smashing";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "تحقق من إعدادات Early Window وفكّر في استخدام إصدار مختلف من LWJGL أو مراجعة الوحدات المرتبطة بالنوافذ المبكرة.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore مخصص لـ modpack معين فقط ولا يجب استخدامه في تثبيتات عامة، لأنه يسبب مشكلة."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore بإصدار Java غير متوافق";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "قم بإزالة GregTechEasyCore، لأنه مخصص لـ modpack معين فقط وغير متوافق مع تثبيتك العامة.";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تم اكتشاف تعارض بين MoniLabs و Connector Extras متعلق بتعديلات KubeJS. "
				+ "هذه الوحدات غير متوافقة في تعديلاتها لـ KubeJS." + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "تعارض بين MoniLabs و Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "حاول إزالة أحد الوحدات (MoniLabs أو Connector Extras) لأن لديهما تعارضات في تعديلات KubeJS.";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "يتطلب Iris وجود Distant Horizons [2.0.4] أو DH API إصدار [1.1.0] أو أحدث. "
				+ "راجع دليل التوافق على https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e لحل المشكلة."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "توافق Iris و Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "راجع دليل التوافق على https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e وقم بتحديث Iris و Distant Horizons إلى إصدارات متوافقة.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لديك فئات (classes) مفقودة من Minecraft. الأسباب المحتملة:</b>" + "<ul>"
				+ "<li>لديك وحدات لversions أخرى من اللعبة. يمكنك استخدام <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> للتحقق مما إذا كانت الفئة موجودة في إصدارك.</li>"
				+ "<li>لديك تثبيت تالف لـ Minecraft (شائع مع تطبيق CurseForge وModrinthApp/Theseus/Astralrinth وغيرها من مشغّلات modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>شاهد البرنامج التعليمي</a> لحل مشاكل CurseForge.</li>"
				+ "<li>لديك coremod تالف (إذا فشل coremod، فقد يمنع تحميل الفئة).</li>" + "</ul>"
				+ "<p>ملاحظة: يمكنك استخدام أداة <b>grepr/fgrepr</b> في الشريط الجانبي للعثور على الوحدات التي تشير إلى الفئات المفقودة، طالما تستخدم '/' في الأسماء.</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لديك فئات مفقودة من DangerZone. الأسباب المحتملة:</b>" + "<ul>"
				+ "<li>لديك وحدات لversions أخرى من اللعبة.</li>" + "<li>لديك coremods تالفة.</li>"
				+ "<li>لديك مشغّل أو تثبيت تالف.</li>" + "</ul>"
				+ "<p>ملاحظة: يمكنك استخدام أداة <b>grepr/fgrepr</b> في الشريط الجانبي للعثور على الوحدات التي تشير إلى الفئات المفقودة، طالما تستخدم '/' في الأسماء.</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لديك فئات مفقودة من FeatureCreep. الأسباب المحتملة:</b>" + "<ul>"
				+ "<li>لديك وحدات لإصدارات مختلفة من FeatureCreep (مثال: ESR مقابل Nightly أو v4 مقابل v12).</li>"
				+ "<li>يمكنك تثبيت FeatureCreep من CurseForge أو MinecraftStorage.</li>" + "</ul>"
				+ "<p>ملاحظة: يمكنك استخدام أداة <b>grepr/fgrepr</b> في الشريط الجانبي للعثور على الوحدات التي تشير إلى الفئات المفقودة، طالما تستخدم '/' في الأسماء.</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لديك فئات مفقودة من ModLauncher. الأسباب المحتملة:</b>" + "<ul>"
				+ "<li>وحداتك مخصصة لbuild مختلف من MinecraftForge أو PillowMC أو NeoForge (يُستخدم ModLauncher مع هذه المحملات).</li>"
				+ "<li>هناك العديد من تحديثات محملات الوحدات لكل إصدار من Minecraft.</li>"
				+ "<li>لديك تثبيت تالف للمشغل (شائع مع تطبيق CurseForge وModrinthApp/Theseus/Astralrinth وغيرها). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>شاهد البرنامج التعليمي</a> لحل مشاكل CurseForge.</li>"
				+ "</ul>"
				+ "<p>ملاحظة: يمكنك استخدام أداة <b>grepr/fgrepr</b> في الشريط الجانبي للعثور على الوحدات التي تشير إلى الفئات المفقودة، طالما تستخدم '/' في الأسماء.</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لديك فئات مفقودة من Minecraft Forge. الأسباب المحتملة:</b>" + "<ul>"
				+ "<li>وحداتك مخصصة لbuild مختلف من MinecraftForge.</li>"
				+ "<li>هناك العديد من تحديثات محملات الوحدات لكل إصدار من Minecraft.</li>"
				+ "<li>لديك تثبيت تالف (شائع مع تطبيق CurseForge وModrinthApp/Theseus/Astralrinth وغيرها). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>شاهد البرنامج التعليمي</a> لحل مشاكل CurseForge.</li>"
				+ "</ul>"
				+ "<p>ملاحظة: يمكنك استخدام أداة <b>grepr/fgrepr</b> في الشريط الجانبي للعثور على الوحدات التي تشير إلى الفئات المفقودة، طالما تستخدم '/' في الأسماء.</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لديك فئات مفقودة من NeoForge. الأسباب المحتملة:</b>" + "<ul>"
				+ "<li>وحداتك مخصصة لbuild مختلف من NeoForge.</li>"
				+ "<li>هناك العديد من تحديثات محملات الوحدات لكل إصدار من Minecraft.</li>"
				+ "<li>لديك تثبيت تالف (شائع مع تطبيق CurseForge وModrinthApp/Theseus/Astralrinth وغيرها). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>شاهد البرنامج التعليمي</a> لحل مشاكل CurseForge.</li>"
				+ "</ul>"
				+ "<p>ملاحظة: يمكنك استخدام أداة <b>grepr/fgrepr</b> في الشريط الجانبي للعثور على الوحدات التي تشير إلى الفئات المفقودة، طالما تستخدم '/' في الأسماء.</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لديك فئات مفقودة من Fabric Loader. الأسباب المحتملة:</b>" + "<ul>"
				+ "<li>وحداتك مخصصة لbuild مختلف من Fabric Loader.</li>"
				+ "<li>هناك العديد من تحديثات محملات الوحدات لكل إصدار من Minecraft.</li>"
				+ "<li>لديك تثبيت تالف (شائع مع تطبيق CurseForge وModrinthApp/Theseus/Astralrinth وغيرها). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>شاهد البرنامج التعليمي</a> لحل مشاكل CurseForge.</li>"
				+ "<li>العديد من الوحدات تتطلب Fabric API. الرجاء تثبيت Fabric API إذا لزم الأمر.</li>" + "</ul>"
				+ "<p>ملاحظة: يمكنك استخدام أداة <b>grepr/fgrepr</b> في الشريط الجانبي للعثور على الوحدات التي تشير إلى الفئات المفقودة، طالما تستخدم '/' في الأسماء.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لديك فئات مفقودة من PillowMC. الأسباب المحتملة:</b>" + "<ul>"
				+ "<li>وحداتك مخصصة لbuild مختلف من PillowMC.</li>"
				+ "<li>هناك العديد من تحديثات محملات الوحدات لكل إصدار من Minecraft.</li>"
				+ "<li>لديك تثبيت تالف (شائع مع تطبيق CurseForge وModrinthApp/Theseus/Astralrinth وغيرها). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>شاهد البرنامج التعليمي</a> لحل مشاكل CurseForge.</li>"
				+ "</ul>"
				+ "<p>ملاحظة: يمكنك استخدام أداة <b>grepr/fgrepr</b> في الشريط الجانبي للعثور على الوحدات التي تشير إلى الفئات المفقودة، طالما تستخدم '/' في الأسماء.</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "لديك وحدة تتسبب عمداً في التباطؤ (lag). Uranium هو وحدة تباطؤ. قد لا تسبب أعطالاً دائماً، لكنها قد تفعل ذلك في النهاية."
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack está marcado como compatible con 1.19.* pero es para 1.20.*, lo que causa un error de clase no encontrada. "
				+ "El mod intenta usar DamageSources que no existen en la versión de Minecraft actual." + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Error de versión en Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "Asegúrate de usar la versión correcta de Falling Attack compatible con tu versión de Minecraft.";
	}
	
	@Override
	public String necesitasInstalarCfr() {
	    String sistema = System.getProperty("os.name").toLowerCase();
	    StringBuilder mensaje = new StringBuilder();
	    mensaje.append("<html>")
	           .append("تحتاج إلى تثبيت CFR (Class File Reader) لاستخدام هذه الميزة.<br><br>");

	    if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
	        mensaje.append("في أنظمة Linux أو NetBSD أو FreeBSD، يمكنك تثبيت CFR من مدير الحزم الخاص بك.<br>")
	               .append("ابحث عن الحزمة هنا: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
	    }

	    mensaje.append("بدلاً من ذلك، يمكنك تنزيل النسخة المعدّلة التي يستخدمها FabricMC من:<br>")
	           .append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
	           .append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
	           .append("احفظه في المجلد التالي:<br>")
	           .append("<b>")
	           .append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
	           .append("</b><br><br>")
	           .append("⚠️ <b>مهم:</b> بعد تثبيت CFR، يجب إعادة تشغيل الـ mod ليتم التعرف عليه بشكل صحيح.")
	           .append("</html>");
	    return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
	    return "لا يوجد صورة متاحة";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
	    return "تعذر العثور على الفئة: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
	    return "مفكك CFR – Sakura Riddle (غير رسمي)";
	}

	@Override
	public String cfrClaseActual() {
	    return "الفئة الحالية";
	}

	@Override
	public String cfrRetratoDeSakura() {
	    return "صورة Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
	    return "خطأ أثناء تحميل الصورة";
	}
	
	public String noticiaLegalCFR() {
	    return "تم تصميم برنامج واجهة المستخدم الرسومية هذا (GUI) لتحليل تعديلات الألعاب (mods) بهدف مساعدة المستخدمين على تحديد أسباب أعطال البرمجيات. "
	            + "ومع ذلك، قد تكون عملية التفكيك (decompilation) ضرورية، ويجب على المستخدمين توخي الحذر بعدم استخدام الكود الناتج لانتهاك قانون حقوق الملكية الفكرية. "
	            + "يوصى بمراجعة ترخيص التعديل ذي الصلة قبل استخدام أي كود تم الحصول عليه. علاوةً على ذلك، غالبًا ما يوفّر المطورون مصدر الكود رسميًا، "
	            + "وهو عادةً أكثر وضوحًا وسهولة في الفهم مقارنةً بالكود المفكّك. تذكّر أن احترام الملكية الفكرية وشروط الترخيص أمر جوهري لمجتمع تطوير التعديلات. "
	            + "يمكنك الاطلاع على قانون حقوق المؤلف المكسيكي عبر هذا الرابط: "
	            + "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Ley Federal de Derechos de Autor (بالإسبانية)</a> "
	            + "والنسخة الإنجليزية هنا: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (بالإنجليزية)</a>. "
	            + "وبما أنك تستخدم CurseForge، فقد أضفنا أيضًا رابط قانون حقوق النشر الأمريكي: "
	            + "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>. "
	            + "نوصي كذلك بأن يُطلِع المستخدمون أنفسهم على القوانين السارية في بلد إقامتهم. "
	            + "تُعد واجهتنا أداة لفحوصات بسيطة فقط؛ ولتحليل متقدم، ننصح باستخدام نسخة FabricMC المعدّلة من Enigma المتوفرة على "
	            + "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>. إذا أردت تعديل ملفات JAR للتصحيح دون توفر الكود المصدري، يمكنك استخدام Recaf من "
	            + "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">موقعه الرسمي</a>.";
	}
	
	@Override
	public String botonDescargarCfr() {
	    return "تنزيل CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
	    return "فتح مجلد التثبيت";
	}

}
