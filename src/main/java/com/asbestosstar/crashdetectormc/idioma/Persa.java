package com.asbestosstar.crashdetectormc.idioma;
 
import com.asbestosstar.crashdetectormc.Config;
import com.asbestosstar.crashdetectormc.Idioma;
 
public class Persa implements Idioma {
    private final Config config = Config.obtenerInstancia();
 
    @Override
    public String carpeta_de_mods_no_valido() {
        return "<span style='color:#" + config.obtenerColorError() + "'>پوشه mods نامعتبر</span>";
    }
 
    @Override
    public String no_se_donde_esta_jar() {
        return "<span style='color:#" + config.obtenerColorError() + "'>فایل JAR CrashDetector پیدا نشد</span>";
    }
 
    @Override
    public String buscando_para_pid(long pid) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>در حال جستجو برای PID: " + String.valueOf(pid) + "</span>";
    }
 
    @Override
    public String pid_esta_muerto(long pid) {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid) + ") متوقف شده است!</span>";
    }
 
    @Override
    public String no_tenemos_jvm() {
        return "<span style='color:#" + config.obtenerColorError() + "'>JVM وجود ندارد</span>";
    }
 
    @Override
    public String probelma_con_graficas_ati() {
        return "<span style='color:#" + config.obtenerColorError() + "'>به روز رسانی درایور ممکن است کمک کند. لطفا توجه داشته باشید که وقتی درایور خراب است، روش های معمولی به روز رسانی ممکن است به روز رسانی را پیدا نکنند، بنابراین لطفاً به راهنمای پیوند داده شده عمل کنید. نکته مهم: اگر از کارت گرافیک Nvidia استفاده می کنید، مطمئن شوید که تمام تنظیمات مربوط به Minecraft (مانند Java و لانچر) در تنظیمات Windows و پنل کنترل Nvidia به عنوان پرکاربرد تنظیم شده اند. این راهنما را بخوانید: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>راهنمای به روز رسانی درایور</a></span>";
    }
 
    @Override
    public String probelma_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>برخی از نسخه های قدیمی در رابط بارگذاری اولیه کارت گرافیک Nouveau گاهی اوقات دچار مشکل می شوند.</span>";
    }
 
    @Override
    public String probelma_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>شما مشکل درایور کارت گرافیک دارید. اگر از GPU یا APU AMD/ATI استفاده می کنید، لطفاً درایور کارت گرافیک AMD خود را به روز کنید. اگر از کارت گرافیک NVIDIA استفاده می کنید، مطمئن شوید که بازی و تمام نمونه های javaw.exe برای استفاده از کارت گرافیک مجزا تنظیم شده اند. این راهنما را بخوانید: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>راهنمای به روز رسانی درایور</a></span>";
    }
 
    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>بارگذاری پنجره اولیه FML شما شکست خورد. برای رفع این مشکل، به (.minecraft/config/fml.toml) بروید و earlyWindowProvider را به \"none\" تنظیم کنید. اگر از Mac M1 استفاده می کنید، مطمئن شوید که از نسخه ARM Java استفاده می کنید، نه نسخه Intel x64. این همچنین یک مشکل رایج درایور قدیمی است. اگر از ویندوز استفاده می کنید و غیرفعال کردن این تنظیمات بی فایده بود، این راهنما را مطالعه کنید: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>راهنمای به روز رسانی درایور</a></span>";
    }
 
    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "<span style='color:#" + config.obtenerColorError() + "'>وابستگی های لازم را ندارید:</span>";
    }
 
    @Override
    public String linea_de_dependencia(String linea) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>" + linea.replace("Requested by", "درخواست شده توسط").replace("Expected range", "محدوده انتظاری") + "</span>";
    }
 
    @Override
    public String local_headless(String archivo) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>گزارش CrashDetector شما در اینجا است <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>مشاهده گزارش</a></span>";
    }
 
    @Override
    public String texto_de_gui() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>این رابط کاربری GUI CrashDetector است. اگر بازی به طور معمول بسته شد، لطفا این رابط را نادیده بگیرید.</span>";
    }
 
    @Override
    public String texto_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorBoton() + "'>مشاهده گزارش</span>";
    }
 
    @Override
    public String texto_debajo_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>گزارش محلی را در مرورگر مشاهده کنید.</span>";
    }
 
    @Override
    public String texto_de_buton_compartir_enlance() {
        return "به اشتراک گذاشتن گزارش";
    }
 
    @Override
    public String texto_debajo_de_buton_compartir_enlance() {
        return "گزارش را به اشتراک بگذارید. لاگ های شما به securelogger.net و گزارش به سایت های دیگر آپلود می شود.";
    }
 
    @Override
    public String problematico_jar() {
        return "<b style='color:#" + config.obtenerColorError() + "'>فایل JAR مشکوک شناسایی شد (اولویت با FATAL، سپس با اولویت بالا و کم):</b>";
    }
 
    @Override
    public String nivel() {
        return "<b style='color:#" + config.obtenerColorError() + "'> سطح:</b> ";
    }
 
    @Override
    public String possibladad_fatal() {
        return "<b style='color:#" + config.obtenerColorError() + "'>احتمالاً کشنده:</b> ";
    }
 
    @Override
    public String modids_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>ModID های مشکوک شناسایی شدند (اولویت با FATAL، سپس با اولویت کم و کم):</b>";
    }
 
    @Override
    public String packages_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>بسته های مشکوک شناسایی شدند (اولویت با FATAL، سپس با اولویت کم و کم):</b>";
    }
 
    @Override
    public String faltar_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>کلاس های کلیدی گمشده شناسایی شدند:</b>";
    }
 
    @Override
    public String corchetes_ondulados() {
        return "<b style='color:#" + config.obtenerColorError() + "'>محتوای {} (مهم ترین در بالا، فقط 20 مورد اول نمایش داده می شود):</b>";
    }
 
    @Override
    public String config_spongemixin_problematico(String archivo) {
        return "<b style='color:#" + config.obtenerColorError() + "'>پیکربندی SpongeMixin مشکوک شناسایی شد: " + "</b>" + archivo;
    }
 
    @Override
    public String module_resolution_exception(String modules, String paquete) {
        return "<span style='color:#" + config.obtenerColorError() + "'>Mods شما بسته های تکراری دارند: " + modules + " بسته تکراری " + paquete.replace(".", "/") + " . شما می توانید با حذف پوشه (ها) از jar (با استفاده از نرم افزار هایی مانند WinRAR یا 7z برای باز کردن jar) یا تغییر پسوند فایل از .jar به zip، حذف پوشه و تغییر مجدد پسوند به .jar، این مشکل را حل کنید.</span>";
    }
 
    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Mods تکراری شناسایی شدند</b> " + linea.replace("from mod files", "از فایل های mod");
    }
 
    @Override
    public String mcforge_mod_suspechoso() {
        return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge mod مشکوک شناسایی کرد:</b> ";
    }
 
    @Override
    public String lithostichctov() {
        return "<b style='color:#" + config.obtenerColorError() + "'>CTOV به lithostitched نیاز دارد، شما می توانید آن را از اینجا نصب کنید: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#" + config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
    }
 
    @Override
    public String necesitasSodiumParaIris() {
        return "<b style='color:#" + config.obtenerColorError() + "'>برای استفاده از Iris shaders یا Oculus، شما به نسخه سازگار SODIUM یا بارگذارهای دیگر (Rubidium, Embedium, Bedium) نیاز دارید</b>";
    }
 
    @Override
    public String kubeJSResourcePack(String mod_nombre) {
        return "<b style='color:#" + config.obtenerColorError() + "'>مشکل در افزونه KubeJS </b>" + mod_nombre;
    }
}
