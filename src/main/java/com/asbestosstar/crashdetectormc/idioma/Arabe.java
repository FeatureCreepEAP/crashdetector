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
    public String probelma_con_graficas_ati() {
        return "<span style='color:#" + config.obtenerColorError() + "'>قد يساعد تحديث التعريفات. يرجى ملاحظة أنه عندما تكون التعريفات تالفة، قد لا يجد التحديث العادي التحديثات، لذا يرجى اتباع دليل الرابط. هام: إذا كنت تستخدم بطاقة Nvidia، تأكد من ضبط جميع إعدادات Minecraft ذات الصلة (مثل Java والمنشئ) على أولوية الأداء العالي في إعدادات Windows ولوحة تحكم Nvidia. اقرأ هذا الدليل: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>دليل تحديث التعريفات</a></span>";
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
}
