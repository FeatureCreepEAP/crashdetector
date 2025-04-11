package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Idioma;

public class Arabe implements Idioma {

    @Override
    public String carpeta_de_mods_no_valido() {
        return "ليست مجلد تعديلات صالح";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "أنا لا أعرف أين ملف JAR الخاص بـ CrashDetector";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "البحث عن PID: " + String.valueOf(pid);
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "(PID: " + String.valueOf(pid) + ") ميت!";
    }

    @Override
    public String no_tenemos_jvm() {
        return "ليس لدينا JVM";
    }
    
    @Override
    public String probelma_con_graficas_ati() {
        return "تحديث برامج التشغيل الخاصة بك قد يساعدك. ضع في اعتبارك أن البحث عن التحديثات بالطرق المعتادة لن يجد أي تحديثات عندما تكون برامج التشغيل في حالة تالفة، لذلك من المهم أن تتبع الدليل المرتبط. مهم: إذا كنت تمتلك بطاقات رسومات Nvidia، تأكد من تكوين كل شيء متعلق بمinecraft (مثل Java والمشغلات) ليعطي الأولوية للأداء العالي في كل من إعدادات Windows ولوحة تحكم Nvidia. اقرأ هذا الدليل لإصلاح المشكلة: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }

    @Override
    public String probelma_con_graficas_nouveau() {
        return "بعض الإصدارات القديمة أحيانًا يكون لديها بعض المشكلات مع الرسومات Nouveau في شاشة التحميل المبكرة.";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "لديك مشكلة في برامج تشغيل الرسومات الخاصة بك. إذا كنت تمتلك بطاقة رسومات AMD/ATI أو وحدة معالجة مركزية مدمجة، قم بتحديث برامج تشغيل الرسومات الخاصة بـ AMD. إذا كنت تمتلك بطاقة رسومات NVIDIA، تأكد من وضع علامة على اللعبة وكل نسخ من javaw.exe لاستخدام بطاقة الرسومات المخصصة. اقرأ هذا الدليل: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }

    @Override
    public String fmlEarlyWindow() {
        return "نافذة FML Early لديك تفشل. "
                + "لتغيير هذا، اذهب إلى (.)minecraft/config/fml.toml "
                + "قم بتعديل earlyWindowProvider ليصبح earlyWindowProvider=\"none\" "
                + "إذا كنت تستخدم جهاز Mac M1، تأكد أيضًا من استخدام إصدار ARM لـ Java وليس إصدار Intel x64. "
                + "هذه أيضًا مشكلة شائعة إذا كانت لديك برامج تشغيل قديمة. راجع هذا الدليل إذا كنت على نظام Windows ولم يعمل تعطيل هذا الخيار. https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "ليس لديك جميع التبعيات المطلوبة:";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return linea.replace("Requested by", "مطلوب بواسطة").replace("Expected range", "النطاق المتوقع");
    }
    
        @Override
    public String local_headless(String archivo) {
        return "تقرير أخطاء CrashDetector متاح هنا: " + archivo;
    }
    
     @Override
    // النص العربي لواجهة المستخدم
    public String texto_de_gui() {
        return "هذه واجهة CrashDetector الرسومية. إذا أغلق اللعبة بدون مشاكل، تجاهله.";
    }
    
        @Override
    // النصوص العربية
    public String texto_de_buton_local_enlance() {
        return "عرض التقرير";
    }

    @Override
    // تفاصيل الزر الأول
    public String texto_debajo_de_buton_local_enlance() {
        return "عرض تقرير محلي في المتصفح";
    }

    @Override
    // نص الزر الثاني
    public String texto_de_buton_compartir_enlance() {
        return "مشاركة التقرير";
    }

    @Override
    // تفاصيل الزر الثاني
    public String texto_debajo_de_buton_compartir_enlance() {
        return "مشاركة التقرير، سيتم تحميل السجلات إلى securelogger.net وسيتم تخزين التقرير في موقع آخر لمدة 3 أيام";
    }
    
    
    @Override
    public String problematico_jar() {
        return "<b>تم العثور على ملفات JAR محتملة المشكلات (FATAL أولًا ثم الأعلى lvl ثم الأقل ln): </b>";
    }

    @Override
    public String nivel() {
        return "<b>lvl: </b>";
    }

    @Override
    public String possibladad_fatal() {
        return "<b>مشكلة قاتلة محتملة: </b> ";
    }

    @Override
    public String modids_problematicos() {
        return "<b>تم العثور على ModIDs مشكلة (FATAL أولًا ثم الأقل lvl ثم الأقل ln): </b>";
    }

    @Override
    public String packages_problematicos() {
        return "<b>تم العثور على حزم مشكلة (FATAL أولًا ثم الأقل lvl ثم الأقل ln): </b>";
    }

    @Override
    public String faltar_de_clases_fatales() {
        return "<b>تم العثور على فئات قاتلة مفقودة: </b>";
    }

    @Override
    public String corchetes_ondulados() {
        return "<b>المحتوى داخل {} (الأهم في الأعلى، أول 20 فقط): </b>";
    }

    @Override
    public String config_spongemixin_problematico(String archivo) {
        return "<b>تم الكشف عن تكوين SpongeMixin مشكلة:</b> " + archivo;
    }

    @Override
public String module_resolution_exception(String modules, String paquete){
return "Tienes Mods con Packages/Paquetes duplicados: " + modules + " package duplicado " + paquete.replace(".","/") + "puedes solucionarlo eliminando el paquete (carpeta) del jar, puedes abrir el jar en un software de archivo como filler-roller, winrar o 7z, también puedes cambiar la extensión del archivo de .jar a zip y luego eliminar la carpeta y luego cambiarla nuevamente a un archivo .jar.";
}

@Override
public String modlauncher_mods_duplicado(String linea) {
    return "<b>لديك تعديلات مكررة</b> " + linea.replace("from mod files", "من ملفات التعديلات");
}

@Override
public String mcforge_mod_suspechoso() {
    return "<b>MinecraftForge مشبوه: هذا التعديل يحتوي على مشكلة:</b> ";
}
    
    
}
