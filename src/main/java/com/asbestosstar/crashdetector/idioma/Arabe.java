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
    public String probelma_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>بعض الإصدارات القديمة تواجه أحيانًا مشكلات مع واجهة تحميل Nouveau المبكرة.</span>";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>هناك مشكلة في تعريفات بطاقة الرسوميات الخاصة بك. إذا كنت تستخدم GPU/APU AMD/ATI، يرجى تحديث تعريفات AMD. إذا كنت تستخدم NVIDIA، تأكد من ضبط جميع حالات javaw.exe واللعبة لاستخدام البطاقة المستقلة. اقرأ هذا الدليل: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>دليل تحديث التعريفات</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>فشل تحميل نافذة FML المبكرة. لإصلاح هذا، انتقل إلى (.minecraft/config/fml.toml) وقم بتعيين earlyWindowProvider إلى \"none\". إذا كنت تستخدم Mac M1، تأكد من استخدام Java ARM وليس Intel x64. هذه مشكلة شائعة أيضًا مع التعريفات القديمة. إذا كنت تستخدم Windows ولم يفلح تعطيل هذا الإعداد، يرجى الرجوع إلى هذا الدليل: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>دليل تحديث التعريفات</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
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
    public String texto_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorBoton() + "'>عرض التقرير</span>";
    }

    @Override
    public String texto_debajo_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>عرض التقرير المحلي في المتصفح.</span>";
    }

    @Override
    public String texto_de_buton_compartir_enlance() {
        return "مشاركة التقرير";
    }

    @Override
    public String texto_debajo_de_buton_compartir_enlance() {
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
    public String possibladad_fatal() {
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
    public String faltar_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>تم اكتشاف فئات رئيسية مفقودة:</b>";
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
    public String mcforge_mod_suspechoso() {
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
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>تحذير: تم اكتشاف فئات مفقودة:</b>";
}


@Override
public String noResultos() {
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

public String nombre_de_contento_de_stacktrace() {
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
public String adjustesCrashDetector() {
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
    return "توجد لديك فصول مفقودة (تحذير)، عادةً يكون هذا جيدًا ولكن ليس دائمًا. التعديلات الأساسية السيئة أو الاعتمادات المفقودة هي أسباب شائعة لهذه المشكلة.";
}

@Override
public String solucionFaltasClases() {
    return "توجد لديك فصول مفقودة (قاتل)، هذا أمر مهم جدًا. التعديلات الأساسية السيئة أو الاعتمادات المفقودة هي أسباب شائعة لهذه المشكلة.";
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








}
