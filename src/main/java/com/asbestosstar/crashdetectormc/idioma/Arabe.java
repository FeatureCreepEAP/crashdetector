package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Config;
import com.asbestosstar.crashdetectormc.Idioma;

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
    public String config_spongemixin_problematico(String archivo) {
        return "<b style='color:#" + config.obtenerColorError() + "'>تم اكتشاف تكوين SpongeMixin مشكلة: " + "</b>" + archivo;
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
            + "                - MultiMC/PolyMC/PrismLauncher/PollyMC/UltimMC: ملاحظة: السجلات المكتشفة تلقائيًا ليست صحيحة.\n"
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
    return "(قريبًا) إخفاء هوية السجلات";
}

@Override
public String botonDeCompartirInforme() {
    return "مشاركة التقرير وجميع السجلات المحددة";
}

@Override
public String arco() {
    return "هذا الحوار يتيح مشاركة السجلات باستخدام واجهة برمجة تطبيقات SecureLogger "
            + "في securelogger.net. عند الضغط على أزرار المشاركة، يتم رفع الملفات "
            + "إلى الموقع المحدد (افتراضي asbestosstar.egoism.jp). يمكنك مشاركة جميع السجلات المحددة "
            + "مع التقرير. إذا كنت لا تريد الرفع، فلا تستخدم هذا الحوار! نحن لا نعالج تقريرك في النقطة النهائية الرسمية (https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb); نحن فقط نزيل الروابط غير المسموح بها. يتم استخدام هذا فقط لعرض معلومات عن تعطل النظام والرابط إلى السجلات. ومع ذلك، من الممكن استخدام نقطة نهاية مخصصة قد لا تحتوي على نفس الطرق.";
}

@Override
public String enlaceDelReporte() {
    return "رابط التقرير:";
}


}
