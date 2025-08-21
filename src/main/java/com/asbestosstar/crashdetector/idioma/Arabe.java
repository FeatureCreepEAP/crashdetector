package com.asbestosstar.crashdetector.idioma;

import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;

public class Arabe implements Idioma {
    private final Config config = Config.obtenerInstancia();

    @Override
    public String carpeta_de_mods_no_valido() {
        return "<span style='color:#" + config.obtenerColorError() + "'>مجلد mods غير صالح</span>";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "<span style='color:#" + config.obtenerColorError() + "'>لا يمكن إيجاد ملف JAR الخاص بـCrashDetector</span>";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>البحث عن PID: " + String.valueOf(pid) + "</span>";
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid) + ") انتهى!</span>";
    }

    @Override
    public String no_tenemos_jvm() {
        return "<span style='color:#" + config.obtenerColorError() + "'>لا يوجد JVM</span>";
    }

@Override
public String problema_con_graficas_ati() {
    return "<span style='color:#" + config.obtenerColorError() + "'>قد يساعدك تحديث برامج تشغيل ATI/AMD الخاصة بك. اقرأ هذا الدليل لحل المشكلة: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>دليل تحديث التعريفات</a> https://www.amd.com/es/support/download/drivers.html تنزيل </span>";
}

    @Override
    public String problema_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>بعض الإصدارات القديمة تواجه أحيانًا مشكلات مع واجهة تحميل Nouveau المبكرة.</span>";
    }

    @Override
    public String problema_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>هناك مشكلة في تعريفات بطاقة الرسوميات الخاصة بك. إذا كنت تستخدم GPU/APU AMD/ATI، يرجى تحديث تعريفات AMD. إذا كنت تستخدم NVIDIA، تأكد من ضبط جميع حالات javaw.exe واللعبة لاستخدام البطاقة المستقلة. اقرأ هذا الدليل: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>دليل تحديث التعريفات</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>فشل تحميل نافذة FML المبكرة. لإصلاح هذا، انتقل إلى (.minecraft/config/fml.toml) وقم بتعيين earlyWindowProvider إلى \"none\". إذا كنت تستخدم Mac M1، تأكد من استخدام Java ARM وليس Intel x64. هذه مشكلة شائعة أيضًا مع التعريفات القديمة. إذا كنت تستخدم Windows ولم يفلح تعطيل هذا الإعداد، يرجى الرجوع إلى هذا الدليل: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>دليل تحديث التعريفات</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesarias() {
        return "<span style='color:#" + config.obtenerColorError() + "'>التبعيات المطلوبة مفقودة:</span>";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>" + linea.replace("Requested by", "طلب بواسطة").replace("Expected range", "النطاق المتوقع") + "</span>";
    }

    @Override
    public String local_headless(String archivo) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>تقرير CrashDetector الخاص بك هنا <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>عرض التقرير</a></span>";
    }

    @Override
    public String texto_de_gui() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>هذه هي واجهة المستخدم الرسومية لـCrashDetector. إذا أغلقت اللعبة بشكل طبيعي، يرجى تجاهل هذه الواجهة.</span>";
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
        return "<b style='color:#" + config.obtenerColorError() + "'>تم اكتشاف ملفات JAR مشكلة (الأولوية للـFATAL ثم عالية ومنخفضة):</b>";
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
        return "<b style='color:#" + config.obtenerColorError() + "'>تم اكتشاف ModID مشكلة (الأولوية للـFATAL ثم عالية ومنخفضة):</b>";
    }

    @Override
    public String packages_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>تم اكتشاف حزم مشكلة (الأولوية للـFATAL ثم عالية ومنخفضة):</b>";
    }

    @Override
    public String falta_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>لديك فئات قاتلة (FATAL)، وهي مشكلة خطيرة. الأسباب الشائعة تشمل CoreMods تالفة أو تبعيات قاتلة. يمكنك استخدام QuickFix للبحث عن المودات التي تحتوي على فئات قاتلة. الفئات القاتلة المفقودة التي تم اكتشافها:</b>";
    }

    @Override
    public String corchetes_ondulados() {
        return "<b style='color:#" + config.obtenerColorError() + "'>المحتوى داخل {} (الأهم في الأعلى، يعرض أول 20 فقط):</b>";
    }

    @Override
    public String config_spongemixin_problematico() {
        return "<b style='color:#" + config.obtenerColorError() + "'>تم اكتشاف تكوين SpongeMixin مشكلة: " + "</b>";
    }

    @Override
    public String module_resolution_exception() {
        return "<span style='color:#" + config.obtenerColorError() + "'>لديك تعديلات تحتوي على حزم مكررة. يمكنك إصلاح هذا عن طريق حذف الحزمة (المجلد) من ملف الـ JAR، يمكنك فتح ملف الـ JAR باستخدام برنامج أرشيف مثل WinRAR أو 7z، كما يمكنك أيضًا تغيير امتداد الملف من .jar إلى zip ثم حذف المجلد ومن ثم إعادة تسميته مرة أخرى إلى ملف .jar.</span>";
    }

    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>تم اكتشاف Mods مكررة</b> " + linea.replace("from mod files", "من ملفات mod");
    }

    @Override
    public String mcforge_mod_sospechoso() {
        return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge اكتشف mod مشكوك فيه:</b> ";
    }

    @Override
    public String lithostichctov() {
        return "<b style='color:#" + config.obtenerColorError() + "'>CTOV يحتاج إلى lithostitched، يمكنك تثبيته من هنا: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#" + config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
    }

    @Override
    public String necesitasSodiumParaIris() {
        return "<b style='color:#" + config.obtenerColorError() + "'>لاستخدام Iris shaders أو Oculus، تحتاج إلى إصدار متوافق من SODIUM أو محمل آخر (Rubidium, Embedium, Bedium)</b>";
    }

    @Override
    public String kubeJSResourcePack(String mod_nombre) {
        return "<b style='color:#" + config.obtenerColorError() + "'>مشكلة في امتداد KubeJS </b>" + mod_nombre;
    }
    
    
    @Override
public String problema_con_graficas_nvidia_windows_viejo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "تم اكتشاف مشكلات مع برامج تشغيل NVIDIA في الإصدارات السابقة من Windows 11."
            + "</span><br/><br/>"
            + "لضمان أن Minecraft (والـ JVM الحالية) يستخدمان وحدة معالجة الرسوميات المخصصة NVIDIA، اتبع الخطوات التالية:<br/><br/>"
            + "1. <strong>حدد ملف تنفيذي Java:</strong><br/>"
            + "   - يستخدم هذا البرنامج الملف التنفيذي التالي لـ Java: "
            + obtenerRutaJava() + "<br/>"
            + "   - إذا لم ترَ مسارًا محددًا، يمكنك العثور على الملف التنفيذي لـ Java بالبحث عن 'java.exe' في نظامك.<br/><br/>"
            + "2. <strong>افتح لوحة تحكم NVIDIA:</strong><br/>"
            + "   - انقر بزر الماوس الأيمن على سطح المكتب وحدد 'لوحة تحكم NVIDIA'.<br/><br/>"
            + "3. <strong>قم بتكوين وحدة معالجة الرسوميات المفضلة:</strong><br/>"
            + "   - في لوحة تحكم NVIDIA، انتقل إلى 'إدارة إعدادات 3D'.<br/>"
            + "   - حدد خيار 'إعدادات برنامج محدد'.<br/>"
            + "   - انقر على 'إضافة' وابحث عن الملف التنفيذي لـ Java الذي تم تحديده سابقًا (مثل: 'java.exe').<br/>"
            + "   - تأكد من أنه مُعد لاستخدام 'معالج عالي الأداء (NVIDIA)'.<br/><br/>"
            + "4. <strong>احفظ التغييرات:</strong><br/>"
            + "   - طبق التغييرات وأغلق لوحة تحكم NVIDIA.<br/><br/>"
            + "5. <strong>أعد تشغيل Minecraft:</strong><br/>"
            + "   - أعد تشغيل Minecraft لتصبح التغييرات نافذة.<br/><br/>"
            + "إذا كنت تستخدم Windows Server 2022 أو Windows 10، فإن هذه الخطوات صالحة طالما لديك أحدث برامج تشغيل NVIDIA مثبتة.<br/><br/>"
            + "ملاحظة: إذا لم تتمكن من العثور على لوحة تحكم NVIDIA، تأكد من أن برامج تشغيل NVIDIA مثبتة بشكل صحيح.";
}

@Override
public String problema_con_graficas_nvidia_windows_nuevo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "تم اكتشاف مشكلات مع برامج تشغيل NVIDIA في Windows 11/Server 2025 أو أحدث."
            + "</span><br/><br/>"
            + "لضمان أن Minecraft (والـ JVM الحالية) يستخدمان وحدة معالجة الرسوميات المخصصة NVIDIA، اتبع الخطوات التالية:<br/><br/>"
            + "1. <strong>حدد ملف تنفيذي Java:</strong><br/>"
            + "   - يستخدم هذا البرنامج الملف التنفيذي التالي لـ Java: "
            + obtenerRutaJava() + "<br/>"
            + "   - إذا لم ترَ مسارًا محددًا، يمكنك العثور على الملف التنفيذي لـ Java بالبحث عن 'java.exe' في نظامك.<br/><br/>"
            + "2. <strong>افتح تطبيق الإعدادات:</strong><br/>"
            + "   - اضغط على المفاتيح <code>Win + I</code> لفتح تطبيق الإعدادات.<br/>"
            + "   - انتقل إلى <strong>النظام > الشاشة > الرسوميات</strong>.<br/><br/>"
            + "3. <strong>قم بتكوين وحدة معالجة الرسوميات المفضلة:</strong><br/>"
            + "   - في قسم 'الرسوميات', انقر على 'إعدادات الرسوميات الافتراضية'.<br/>"
            + "   - حدد 'تطبيقات سطح المكتب' ثم انقر على 'استعراض'.<br/>"
            + "   - ابحث وحدد الملف التنفيذي لـ Java الذي تم تحديده سابقًا (مثل: 'java.exe').<br/>"
            + "   - بمجرد الإضافة، حدد تطبيق Java في القائمة وقم بتكوينه لاستخدام 'الأداء العالي (NVIDIA)'.<br/><br/>"
            + "4. <strong>احفظ التغييرات:</strong><br/>"
            + "   - طبق التغييرات وأغلق تطبيق الإعدادات.<br/><br/>"
            + "5. <strong>أعد تشغيل Minecraft:</strong><br/>"
            + "   - أعد تشغيل Minecraft لتصبح التغييرات نافذة.<br/><br/>"
            + "إذا كنت تستخدم Windows 11 أو Windows Server 2025+، فإن هذه الخطوات صالحة طالما لديك أحدث برامج تشغيل NVIDIA مثبتة.<br/><br/>"
            + "ملاحظة: إذا لم تتمكن من العثور على خيار إعدادات الرسوميات، تأكد من أن برامج تشغيل NVIDIA مثبتة بشكل صحيح.";
}









@Override
public String segundo60Tick() {
    return "<b style='color:#" + config.obtenerColorError() + "'>خادمك أو عالمك لديه تicks تتجاوز 60 ثانية. قد يكون ذلك بسبب التعديلات التي تبطئ الخادم أو أن الأجهزة ضعيفة جدًا.</b>";
}


@Override
public String noTieneMemoria() {
    return "<b style='color:#" + config.obtenerColorError() + "'>لا تملك ذاكرة RAM/ذاكرة كافية. تحتاج إلى تخصيص المزيد.</b>";
}


@Override
public String theseus() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>إذا كنت تستخدم Theseus/ModrinthApp، لا يمكننا مساعدتك كثيرًا لأن Theseus ليس لديه وحدة تحكم في المشغل (Launcher). لدى Theseus أيضًا مشاكل أخرى، بما في ذلك إصدارات قديمة من برامج تحميل التعديلات، برمجيات تجسس، سجلات سيئة، وأكثر. شركة Modrinth ليست صادقة أيضًا. يقومون بتهم كاذبة بأن مطوري التعديلات يستخدمون الروبوتات لزيادة تنزيلاتهم وقد غيروا ادعاءاتهم حول تحقيق الدخل عدة مرات.</b>";
}



@Override
public String noTieneConsolaDeLauncherCursedForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>ليس لديك ملف launcher_log.txt. نحن بحاجة إلى هذا الملف للبحث عن الأخطاء. وهذا بسبب خيار \"تخطي بدء تشغيل المشغل\". قم بتعطيله.</b>";
}

@Override
public String faltar_de_clases_advertencia() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>تحذير: تم اكتشاف فئات مفقودة (تحذير). عادةً ليست خطيرة، ولكنها قد تسبب مشاكل — تختلف عن الأخطاء القاتلة. الأسباب الشائعة تشمل CoreMods تالفة أو تبعيات مفقودة. يمكنك استخدام QuickFix للبحث عن المودات التي تحتوي على فئات ناقصة. الفئات المفقودة المكتشفة:</b>";
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
    return "<b style='color:#" + config.obtenerColorInfo() + "'>هذه هي نتائج التحقق الخاصة بك. إصلاح الأجزاء العليا من السجلات هو الأولوية الأولى. قم بذلك ببطء.</b>";
}


@Override
public String versionDeJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>يرجى استخدام Java 17 للإصدارات 1.17-1.20.4 وJava 21 لأي إصدار أحدث. استخدم Java 8 لأي إصدار أقدم. [دليل](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). إذا كنت لا تزال تواجه مشاكل، فقد يكون ذلك لأن بعض التعديلات تحتوي على ملفات قديمة جدًا أو جديدة جدًا.</b>";
}

@Override
public String java22() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java 22 وما فوقه لا يعمل على إصدارات Minecraft الأقل من 1.20.5 لمعظم مُحمّلات التعديلات بسبب أن ASM قديم.</b>" + versionDeJava();
}

@Override
public String javaObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java قديم </b>" + versionDeJava();
}

@Override
public String jpms_modules_faltas(String mod_necesitas, String submod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>تحتاج إلى وحدة JPMS " + mod_necesitas + " من " + submod + "</b>";
}

@Override
public String null_pointer_error(String metodo, String objeto) {
    return "<b style='color:#" + config.obtenerColorError() + "'>لا يمكن استدعاء " + metodo + " لأن " + objeto + " هو قيمة خالية</b>";
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
    return "!لم يتم العثور على سجلات المشغل. قد يؤدي هذا إلى تعقيد التحقيق\n"
            + "                \n"
            + "                للحصول على السجلات الصحيحة:\n"
            + "                - MultiMC/PolyMC/PrismLauncher/: ملاحظة: السجلات المكتشفة تلقائيًا ليست صحيحة.\n"
            + "                  1. افتح واجهة المثيل\n"
            + "                  2. انتقل إلى قسم \"Minecraft Log\"\n"
            + "                  3. انقر بزر الماوس الأيمن وانسخ المحتوى\n"
            + "                - CurseForgeApp:\n"
            + "                  1. أعد تشغيل اللعبة دون تخطي المشغل\n"
            + "                  \n";
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
            + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". يمكنك أيضًا مشاركة السجلات الفردية بدون تقرير عن طريق الضغط على أزرار المشاركة بجانب أسماء السجلات الفردية؛ سيتم إرسال السجلات إلى موقع السجلات المحدد. يحتوي CrashDetector على تجهيل افتراضي للسجلات، والذي يحاول إزالة أسماء المستخدمين، UUIDs، رموز الوصول، معرّفات الجلسة، عناوين IP، وغيرها من البيانات. ومع ذلك، فهو ليس مثاليًا. ومع ذلك، يمكن للمؤلف الحزمة تعطيله. يمكن تمكينه أو تعطيله باستخدام خانة الاختيار في الجزء السفلي من هذه الشاشة. أنت المتحكم في بياناتك الخاصة؛ أنت تقرر أين تقوم بتحميل بياناتك. مواقع السجلات مملوكة لأطراف ثالثة غالبًا ما تكون ملكيتها مخفية لأسباب تتعلق بالخصوصية. أنت تتحمل المسؤولية الكاملة لإدارة بياناتك والمخاطر المتعلقة بها. حوار مشاركة CrashDetector هو مجرد واجهة تتيح لك إدارة ذلك. من المهم أن تكون على دراية بـ GDPR وARCO.";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "إصدار JavaFML غير متوافق: مطلوب الإصدار " + requerido 
         + "، تم اكتشاف " + encontrado + "</b>";
}

@Override
public String errorJavaFML_MCForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "انتباه! JavaFML يتطلب إصدارًا محددًا من Minecraft Forge</b>";
}

@Override
public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "ملف JAR '" + archivoJar + "' يتطلب مزود اللغة '" + proveedor + "' الإصدار '"
         + requerido + "' أو أحدث، ولكن تم العثور فقط على الإصدار '" + encontrado + "'.</b>";
}

//@Override
//public String advertenciaMalwareFalso() {
//    return "<b style='color:#" + config.obtenerColorError() + "'>"
//         + "تنبيه! Crash Assistant هو كاشف برامج ضارة مزيف. يقوم بحظر اللعبة عن التشغيل بشكل متعمد، مع تجاهل حريتك في متابعة اللعب باستخدام التعديلات التي يستهدفها. "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>عرض كود MalwareMod.java</a>   "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>عرض كود JarInJarHelper.java</a>. "
//         + "هذا التعديل فقط هو الموجود في قائمتهم حالياً، وهم يستهدفون فقط موقع تسجيل السجلات الافتراضي، والذي يمكن للمستخدم تغييره، وهذا يحدث فقط إذا اخترت صراحة استخدام ميزة مشاركة السجلات المدمجة. CrashAssistant لا يقوم بأي فحوصات لتحديد موقع التسجيل المستخدم ولا يشرح كيفية تغييره (هناك قائمة منسدلة في أسفل مربع الحوار الخاص بالمشاركة)، وبغض النظر عن الموقع الذي قمت بتكوينه، فإن CrashAssistant سيمنع تشغيل اللعبة. في رسالتهم يقولون أن تقوم بإجراء بحثك الخاص، افعل ذلك، انظر إلى كود CrashDetector وCrash Assistant وافهم ما يقومون به، لا تعتمد على استدعاء السلطة.</b>";
//}


@Override
public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "فشل المود '" + idMod + "' لأن الصنف المطلوب لم يتم العثور عليه: '"
         + claseNoEncontrada + "'. تأكد من أن جميع التبعيات مثبتة بشكل صحيح.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "لم يتمكن من تحميل خدمة ModLauncher: </b>" + servicio + ".";
}

@Override
public String errorConJSONDeRegistro(String archivoJar, String recurso) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "خطأ في تحليل ملف JSON '" + recurso + "' من ملف JAR '" + archivoJar
         + "'. هناك مشاكل في التسجيل.</b>";
}


@Override
public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
    return "<span style='color:#" + config.obtenerColorError() + "'>" 
        + "خطأ: المود '" + modId + "' يتطلب الإصدار '" + requerido 
        + "' أو أحدث من '" + dependencia + "'، لكن تم العثور على '" + actual + "'."
        + "</span>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "خطأ في تكوين الشاشات: "
         + "تعذر تعيين وضع الشاشة. "
         + "تحقق من:</b>"
         + "<br>- تكوين شاشات متعددة"
         + "<br>- برامج تشغيل بطاقة الرسوميات المحدثة"
         + "<br>- الدقة المدعومة من النظام";
}

@Override
public String errorOpcionesGCJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "خطأ في خيارات Java: "
         + "خيارات جامع القمامة متعارضة. "
         + "تأكد من عدم دمج عدة خوارزميات GC في معلمات JVM</b>";
}

@Override
public String errorConfigMCForge() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "خطأ حرج في تكوين Forge: "
         + "ملف التكوين تالف أو غير مكتمل. "
         + "احذف مجلد 'config' وأعد تشغيل اللعبة</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>فشل تحميل الإضافة 'AuthMe' وأدت إلى إيقاف الخادم.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>لم يمكن تحميل العالم '" + nombreMundo + "' لأنه يحتوي على أخطاء وربما يكون مُهترئًا.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>هناك عدة ملفات إضافات تحمل نفس الاسم '" + nombrePlugin + "': '" + primerPath + "' و'" + segundoPath + "'.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>حدث خطأ أثناء تحميل الكتل (chunks) في العالم.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>لا يمكن للإضافة '" + nombrePlugin + "' تنفيذ الأمر '/" + comando + "'.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>تتطلب الإضافة '" + nombrePlugin + 
           "' الاعتماد على الإضافة '" + dependencia + "'.</b> ";
}

@Override
public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
    StringBuilder deps = new StringBuilder();
    for (int i = 0; i < dependencias.size(); i++) {
        if (i > 0) deps.append(", ");
        deps.append("'").append(dependencias.get(i)).append("'");
    }
    return "<b style='color:#" + config.obtenerColorError() + "'>تتطلب الإضافة '" + nombrePlugin + 
           "' الاعتماد على الإضافات التالية: " + deps.toString() + ".</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>الإضافة '" + nombrePlugin + 
           "' تحتاج إلى إصدار واجهة برمجية '" + versionAPI + "' غير متوافق مع الخادم الحالي.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>المعالم '" + nombreMundo + 
           "' هو نسخة مكررة من عالم آخر ولا يمكن تحميله.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>الكيان الكتلي '" + nombre + 
           "' من النوع '" + tipo + "' في الموقع " + coords + " يسبب أخطاء أثناء التحديثات.</b> ";
}

@Override
public String nombreProblemaTickingEntidadBloque() {
    return "كيان كتلي مشكل";
}

@Override
public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
    String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
    return "قم بإزالة الكيان '" + nombre + "' الموجود في الموقع " + coords + " باستخدام MCEdit أو عن طريق تحرير العالم مباشرة.";
}

@Override
public String mensajeModDuplicadoFabric(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>يحتوي المود '" + nombreMod + "' على عدة إصدارات مثبتة.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>المودان '" + primerMod + 
           "' و'" + segundoMod + "' غير متوافقين مع بعضهما البعض.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>يحتوي المود '" + nombreMod + "' على خطأ حرج ولا يمكن تنفيذه.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>المودات التالية مطلوبة ولكن لم يتم تثبيتها: " + deps.toString() + ".</b>";
}

@Override
public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
    if (version == null || version.isEmpty()) {
        return "<b style='color:#" + config.obtenerColorError() + "'>يحتاج المود '" + nombreMod + 
               "' إلى المود '" + dependencia + "' كاعتمادية.</b>";
    } else {
        return "<b style='color:#" + config.obtenerColorError() + "'>يحتاج المود '" + nombreMod + 
               "' إلى المود '" + dependencia + "' بالإصدار " + version + ".</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>الإضافة '" + nombrePlugin + 
           "' غير متوافقة مع التحديث الإقليمي في Folia.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>المود '" + nombreMod + 
           "' مفقود من الحزمة.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>المود '" + nombreMod + 
           "' غير متوافق مع إصدار ماينكرافت " + versionMC + ".</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>المود '" + nombreMod + "' مفقود وهو مطلوب لتحميل العالم أو الإضافة.</b>";
}

@Override
public String nombreProblemaDependenciaModFaltante() {
    return "مود مفقود";
}

@Override
public String mensajeWorldModFaltanteSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>تم حفظ العالم مع المود '" + nombreMod + 
           "' الذي يبدو أنه مفقود.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>تم حفظ العالم باستخدام المود '" + nombreMod + 
           "' بالإصدار " + versionEsperada + "، وهو الآن يعمل بالإصدار " + versionActual + ".</b>";
}

@Override
public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas, List<String> versionesActuales) {
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
        sb.append(" (محفوظ: ").append(versionesEsperadas.get(i)).append(", حاليًا: ").append(versionesActuales.get(i)).append(")");
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
    return "<b style='color:#" + config.obtenerColorError() + "'>لقد حاولت تحميل عالم تم إنشاؤه بإصدار أحدث من ماينكرافت.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>الإضافة '" + nombrePlugin + 
           "' تحتاج إلى الإضافة التالية كاعتماد: '" + dependencia + "'.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>الإضافة '" + nombrePlugin + "' غير متوافقة مع إصدار الخادم الحالي.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>الإضافة '" + nombrePlugin + "' تسببت في خطأ أثناء التشغيل.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>لديك مشكلة مع عدة خيوط تصل إلى فئة LegacyRandomSource. يمكنك الحصول على المزيد من المعلومات باستخدام تعديل 'Unsafe World Random Access Detector' أو التعديل 'C2ME'.</b>";
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
        
        + "<h3>كيفية الاستخدام:</h3>"
        + "<ol>"
        + "<li><b>اختيار الملفات:</b> انقر على أزرار الاختيار بجانب أسماء الملفات. "
        + "الملفات التي تنتهي بـ <span style='color: #4CAF50; font-weight: bold;'>.suceso</span> تشير إلى جلسات ناجحة، "
        + "بينما تشير ملفات الـ <span style='color: #F44336; font-weight: bold;'>.falla</span> إلى فشل أو خطأ.</li>"
        
        + "<li><b>المقارنة التلقائية:</b> عند الضغط على زر 'Compare'، سيقوم النظام بتحليل القوائم واكتشاف المودات المضافة (+) أو المحذوفة (-).</li>"
        
        + "<li><b>عرض النتائج:</b> يتم عرض النتائج بصيغة HTML مع ترميز بالألوان: "
        + "<ul>"
        + "<li><span style='color: green;'>+ مود مضاف</span></li>"
        + "<li><span style='color: red;'>- مود محذوف</span></li>"
        + "</ul></li>"
        + "</ol>"
        
        + "<h3>المميزات الرئيسية:</h3>"
        + "<ul>"
        + "<li>يدعم أي مزيج من الملفات (ناجحة/فاشلة).</li>"
        + "<li>يُظهر الفروقات في كلا الاتجاهين للحصول على تعقب دقيق.</li>"
        + "<li>يحتوي على إمكانية التمرير لقائمة طويلة من المودات.</li>"
        + "<li>متكامل مع صور شارحة لتحسين الفهم البصري.</li>"
        + "</ul>"
        
        + "<p>تم تطويره بـ 3> لمساعدتك على تتبع التعديلات في إعداداتك.</p>"
        + "</body></html>";
}

@Override
public String tieneErrorIPV6() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
           + "من المحتمل أنك تواجه مشكلة متعلقة بـ IPv6. "
           + "توجد حلان: "
           + "1) أضف المعامل <code>-Djava.net.preferIPv4Stack=true</code> إلى برنامج التشغيل الخاص بك، أو "
           + "2) استخدم زر 'QuickFix' في CrashDetector لتثبيت إصلاح يفعّل هذا الإعداد تلقائيًا."
           + "</b>";
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
    return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/أو نسخ مشتقة منها: انقر بزر الماوس الأيمن على النسخة واختر \"تحرير النسخة\". في النافذة التي تفتح، ابحث عن قسم \"سجلات ماينكرافت\" أو ما شابه.<br>" +
           "تحتوي هذه السجلات على الإخراج القياسي (STDOUT)، وهو ضروري لتشخيص الأخطاء.";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>تم اكتشاف فئات مفقودة من Create. لقد تغير Create كثيرًا — تم إزالة العديد من الفئات. خاصةً منذ Create 6 (فبراير 2025)، الإضافات الخاصة بالإصدارات القديمة من Create لم تعد تعمل. لا يمكن لـ QuickFix حل هذه المشكلة. يجب تحديث إضافات Create، أو إزالة القديمة منها، أو استخدام الإصدار الصحيح من Create للإضافات التي ترغب في استخدامها.</b>";
}

@Override
public String faltar_de_clases_epicfight() {
    return "<b style='color:#" + config.obtenerColorError() + "'>تم اكتشاف فئات مفقودة من EpicFight. لقد تغير EpicFight كثيرًا — تم إزالة العديد من الفئات. لا يمكن لـ QuickFix حل هذه المشكلة. يجب تحديث إضافات EpicFight، أو إزالة القديمة منها، أو استخدام الإصدار الصحيح من EpicFight للإضافات التي ترغب في استخدامها.</b>";
}

@Override
public String openJ9NoSoportado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>أنت تستخدم OpenJ9، وهو غير مدعوم من قبل هذا التطبيق. العديد من التطبيقات، بما في ذلك هذا، لا تدعم OpenJ9 بسبب اختلافات في تنفيذ JVM. لا يمكن لـ QuickFix حل هذه المشكلة تلقائيًا. يجب عليك تثبيت JDK متوافق مثل Oracle JDK أو OpenJDK Hotspot أو بدائل موصى بها أخرى.</b>";
}

@Override
public String necesitasJDK11() {
    return "<b style='color:#" + config.obtenerColorError() + "'>تتطلب هذه النسخة من التطبيق Java 11 (JDK 11) للعمل بشكل صحيح. أنت تستخدم إصدارًا قديمًا وغير متوافق من Java. لا يمكن لـ QuickFix تحديث Java تلقائيًا. يجب تنزيل وتثبيت JDK 11 أو إصدار متوافق أحدث من الروابط المقدمة في الحل.</b>";
}

@Override
public String memoriaExcesiva() {
    return "<b style='color:#" + config.obtenerColorError() + "'>لقد خصصت ذاكرة زائدة، مما يؤدي إلى نقص الموارد في النظام. يحدث هذا غالبًا عند تحديد كمية ذاكرة أكبر من المتوفرة، أو عند استخدام JVM من نوع 32 بت لا يمكنه التعامل مع كميات كبيرة من الذاكرة.</b>";
}

@Override
public String recomendacionMemoriaExcesiva() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>لحل هذه المشكلة، قم بتقليل كمية الذاكرة المخصصة للتطبيق. الحد الأقصى الموصى به يعتمد على نظامك، لكنه عادةً لا يجب أن يتجاوز 70-80٪ من إجمالي ذاكرة RAM. إذا كنت تستخدم JVM 32 بت، فإن الحد الأقصى هو حوالي 2-3 جيجابايت بغض النظر عن كمية الذاكرة الفعلية.</b>";
}

@Override
public String disminuirMemoriaHeap() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>لتقليل ذاكرة heap المخصصة للتطبيق، افتح إعدادات اللانشر وابحث عن خيار الذاكرة. قلل القيمة القصوى (Xmx) إلى كمية مناسبة. على سبيل المثال، إذا كانت لديك 8 جيجابايت من RAM، خصص 3-4 جيجابايت للتطبيق. إذا كانت لديك 16 جيجابايت، يمكنك تخصيص 6-8 جيجابايت. تأكد من ترك ذاكرة كافية للنظام والبرامج الأخرى.</b>";
}

@Override
public String forgeArchivosFaltantes(String archivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>مفقودة ملفات أساسية من Forge. الملف '" + archivo + "' غير موجود في تثبيتك. يحدث هذا عادةً عند توقف تثبيت Forge أو حذف ملفات مهمة. لا يمكن لـ QuickFix استعادة هذه الملفات تلقائيًا. يجب عليك إعادة تثبيت Forge بشكل صحيح باستخدام المثبت الرسمي.</b>";
}

@Override
public String forgeVersionNoEncontrada(String version, String archivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>لا يمكن لـ Forge العثور على إصدار ماينكرافت المطلوب. الإصدار " + version + " مطلوب لكنه غير موجود في الملف '" + archivo + "'. يحدث هذا عند عدم التوافق بين إصدار ماينكرافت وإصدار Forge. تأكد من تحميل الإصدار الصحيح من Forge المطابق لإصدار ماينكرافت لديك.</b>";
}

@Override
public String forgeTargetFmlclientNoEncontrado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>تعذر العثور على الهدف 'fmlclient' اللازم لتشغيل Forge. هذا يشير إلى تثبيت غير مكتمل أو تالف. ربما لم تُثبت ملفات Forge الأساسية بشكل صحيح. يجب عليك إعادة تثبيت Forge باستخدام المثبت الرسمي.</b>";
}

@Override
public String forgeClaseMinecraftFaltante() {
    return "<b style='color:#" + config.obtenerColorError() + "'>تعذر العثور على الفئة الرئيسية لماينكرافت في محمل الفئات. هذا يشير عادةً إلى تثبيت Forge غير مكتمل أو تعارض مع مودات أخرى. قد تكون ملفات ماينكرافت تالفة بسبب تثبيت Forge. يجب إعادة تثبيت Forge بشكل صحيح.</b>";
}

@Override
public String forgeInstallacionNoCompleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>تثبيت Forge غير مكتمل. قد يكون بسبب تثبيت متوقف، أو ملفات محذوفة، أو عدم توافق مع إصدار ماينكرافت لديك. يحتاج Forge إلى ملفات محددة للعمل، وبعضها مفقود من التثبيت الحالي.</b>";
}

@Override
public String nombre_de_forge_instalacion_no_completa() {
    return "تثبيت Forge غير مكتمل";
}

@Override
public String solucion_para_forge_instalacion_no_completa() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>لحل هذه المشكلة، أعد تثبيت Forge بشكل صحيح. تأكد من تنزيل الإصدار المناسب لإصدار ماينكرافت الخاص بك، واتبع عملية التثبيت بالكامل دون مقاطعة.</b>";
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
    return "<html><body style='width: 500px;'>" +
           "<h3 style='color:#" + config.obtenerColorTitulo() + "'>تعليمات لإعادة تثبيت Forge:</h3>" +
           "<ol>" +
           "<li>قم بتنزيل مثبت Forge الصحيح من الموقع الرسمي (الإصدار الموصى به لإصدار ماينكرافت الخاص بك)</li>" +
           "<li>أغلق بالكامل برنامج تشغيل ماينكرافت</li>" +
           "<li>شغّل مثبت Forge كمسؤول</li>" +
           "<li>اختر خيار 'Installer' (وليس 'Installer (run client)')</li>" +
           "<li>اختر مجلد ملف تعريف ماينكرافت في البرنامج</li>" +
           "<li>اضغط 'موافق' وانتظر اكتمال التثبيت</li>" +
           "<li>أعد تشغيل البرنامج وتأكد من ظهور Forge في قائمة الملفات الشخصية</li>" +
           "</ol>" +
           "<p><b>ملاحظة مهمة:</b> إذا كنت تستخدم برنامج تشغيل مخصصًا، تأكد من اختيار المجلد الصحيح للملف الشخصي.</p>" +
           "</body></html>";
}

@Override
public String titulo_instrucciones_reinstaler_mcforge() {
    return "تعليمات لإعادة تثبيت Forge";
}

@Override
public String error_enlace_insatisfecho(String nombreBiblioteca) {
    return "<b style='color:#" + config.obtenerColorError() + "'>خطأ في الربط: فشل في تحميل المكتبة " + nombreBiblioteca + ". الحلول الممكنة:<br/><br/>" +
           "أ) أضف الدليل الذي يحتوي على المكتبة المشتركة إلى -Djava.library.path أو -Dorg.lwjgl.librarypath.<br/>" +
           "ب) أضف ملف JAR الذي يحتوي على المكتبة المشتركة إلى classpath.<br/><br/>" +
           "يحدث هذا الخطأ عندما لا يستطيع ماينكرافت العثور على الملفات الأساسية للتشغيل. " +
           "وهو ناتج عادةً عن تثبيت ماينكرافت غير مكتمل أو مشاكل في أذونات النظام.</b>";
}

@Override
public String nombre_de_error_enlace_insatisfecho() {
    return "خطأ في الربط";
}

@Override
public String solucion_para_error_enlace_insatisfecho() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>فشل في تحميل مكتبة. الحلول الممكنة:<br/><br/>" +
           "أ) أضف الدليل الذي يحتوي على المكتبة المشتركة إلى -Djava.library.path أو -Dorg.lwjgl.librarypath.<br/>" +
           "ب) أضف ملف JAR الذي يحتوي على المكتبة المشتركة إلى classpath.<br/><br/>" +
           "هذه الحلول التقنية مخصصة للمستخدمين المتقدمين. معظم المستخدمين يجب أن يحاولوا " +
           "إعادة تثبيت ماينكرافت قبل تعديل هذه المعلمات.</b>";
}

@Override public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) { return "<b style='color:#" + config.obtenerColorError() + "'>تضارب في المعرفات (IDs): المعرف <strong>" + id + "</strong> مستخدم بالفعل بواسطة <strong>" + modOrigen + "</strong> عند محاولة إضافة <strong>" + modDestino + "</strong>. يحدث هذا عندما يحاول مودان استخدام نفس المعرف لعناصر مختلفة.</b>"; } @Override public String conflicto_id_maximo() { return "<b style='color:#" + config.obtenerColorError() + "'>تم تجاوز الحد الأقصى المسموح به للمعرفات. يحدث هذا عندما يحاول المودات تسجيل كتل أو عناصر باستخدام معرفات خارج النطاق المدعوم من إصدار ماينكرافت الخاص بك.</b>"; } @Override public String nombre_de_conflicto_ids() { return "تضارب في المعرفات"; } @Override public String solucion_maximo_rango() { return "<b style='color:#" + config.obtenerColorTexto() + "'>لحل هذه المشكلة في ماينكرافت 1.12.2، قم بتثبيت <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. أما في 1.7.10، استخدم <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>"; } @Override public String solucion_colision_id() { return "<b style='color:#" + config.obtenerColorTexto() + "'>استخدم أدوات مثل <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> أو <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> لحل تضارب المعرفات.</b>"; } @Override public String instalar_justenoughids() { return "تثبيت JustEnoughIDs"; } @Override public String instalar_endlessids() { return "تثبيت EndlessIDs"; } @Override public String usar_idfix_minus() { return "استخدام IdFix Minus أو IdFix"; } @Override public String usar_minecraft_id_resolver() { return "استخدام Minecraft-ID-Resolver"; } @Override public String ver_documentacion_jp() { return "عرض الوثائق اليابانية"; }

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




}
