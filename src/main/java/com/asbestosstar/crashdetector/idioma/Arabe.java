package com.asbestosstar.crashdetector.idioma;

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
    public String module_resolution_exception(String modules, String paquete) {
        return "<span style='color:#" + config.obtenerColorError() + "'>يوجد حزم مكررة في Mods الخاصة بك: " + modules + " حزمة مكررة " + paquete.replace(".", "/") + ". يمكنك حل هذا عن طريق حذف الحزمة (المجلد) من jar، يمكنك فتح jar باستخدام WinRAR أو 7z أو تغيير الامتداد من .jar إلى zip ثم حذف المجلد وإعادة تغيير الامتداد إلى .jar.</span>";
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




}
