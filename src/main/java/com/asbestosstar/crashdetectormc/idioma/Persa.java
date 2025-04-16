package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Idioma;

public class Persa implements Idioma {

    @Override
    public String carpeta_de_mods_no_valido() {
        return "این یک پوشه معتبر برای افزونه‌ها نیست";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "من نمی‌دانم فایل JAR CrashDetector کجاست";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "در حال جستجوی PID: " + String.valueOf(pid);
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "(PID: " + String.valueOf(pid) + ") مرده است!";
    }

    @Override
    public String no_tenemos_jvm() {
        return "ما JVM نداریم";
    }
    
    @Override
public String probelma_con_graficas_ati() {
    return "به‌روزرسانی درایورهای شما ممکن است کمک کند. لطفاً توجه داشته باشید که وقتی درایورها در وضعیت خراب هستند، جستجوی به‌روزرسانی به روش معمول هیچ نتیجه‌ای پیدا نخواهد کرد، بنابراین مهم است که از راهنمای پیوند داده شده پیروی کنید. مهم: اگر کارت گرافیک Nvidia دارید، مطمئن شوید که هر چیزی مرتبط با Minecraft (مانند Java و برنامه‌های راه‌انداز) را برای اولویت دادن به عملکرد بالا در تنظیمات Windows و پنل کنترل Nvidia تنظیم کرده‌اید. برای حل این مشکل، این راهنما را بخوانید: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
}

@Override
public String probelma_con_graficas_nouveau() {
    return "برخی از نسخه‌های قدیمی گاهی مشکلاتی با گرافیک Nouveau در صفحه بارگذاری اولیه دارند.";
}

@Override
public String probelma_con_graficas_general() {
    return "شما مشکلی با درایورهای گرافیکی خود دارید. اگر GPU یا APU از نوع AMD/ATI دارید، درایورهای گرافیکی AMD خود را به‌روزرسانی کنید. اگر کارت گرافیک NVIDIA دارید، مطمئن شوید که بازی و تمام نمونه‌های javaw.exe را برای استفاده از کارت گرافیک اختصاصی تنظیم کرده‌اید. این راهنما را بخوانید: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
}

@Override
public String fmlEarlyWindow() {
    return "پنجره FML Early شما دچار مشکل شده است. "
            + "برای تغییر این موضوع، به (.)minecraft/config/fml.toml بروید "
            + "و earlyWindowProvider را به earlyWindowProvider=\"none\" تغییر دهید. "
            + "اگر از Mac M1 استفاده می‌کنید، مطمئن شوید که از نسخه ARM Java استفاده می‌کنید و نه نسخه Intel x64. "
            + "این مشکل همچنین اگر درایورها قدیمی باشند، شایع است. اگر در ویندوز هستید و غیرفعال کردن این گزینه کار نکرد، این راهنما را بررسی کنید: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
}

@Override
public String no_tienes_las_dependencias_necesitas() {
    return "همه وابستگی‌های لازم را ندارید:";
}

@Override
public String linea_de_dependencia(String linea) {
    return linea.replace("Requested by", "درخواست شده توسط").replace("Expected range", "محدوده مورد انتظار");
}


    @Override
    public String local_headless(String archivo) {
        return "گزارش خرابی CrashDetector اینجا ذخیره شده است: " + archivo;
    }


    @Override
    // متن فارسی رابط گرافیکی
    public String texto_de_gui() {
        return "این رابط گرافیکی CrashDetector است. اگر بازی بدون مشکل بسته شود، آن را نادیده بگیرید.";
    }


    @Override
    // نسخه فارسی
    public String texto_de_buton_local_enlance() {
        return "مشاهده گزارش";
    }

    @Override
    // توضیحات دکمه مشاهده
    public String texto_debajo_de_buton_local_enlance() {
        return "گزارش محلی را در مرورگر مشاهده کنید";
    }

    @Override
    // متن دکمه اشتراک
    public String texto_de_buton_compartir_enlance() {
        return "اشتراک گزارش";
    }

    @Override
    // توضیحات دکمه اشتراک
    public String texto_debajo_de_buton_compartir_enlance() {
        return "گزارش شما به securelogger.net آپلود شده و برای 3 روز در سایت دیگری ذخیره می‌شود";
    }



@Override
public String problematico_jar() {
    return "<b>فایل‌های JAR مشکل‌دار یافت شد (اولویت: FATAL، سطح بالاتر، خط پایین‌تر):</b>";
}

@Override
public String nivel() {
    return "<b>lvl: </b>";
}

@Override
public String possibladad_fatal() {
    return "<b>احتمالاً فاتال:</b> ";
}

@Override
public String modids_problematicos() {
    return "<b>modidهای مشکل‌دار یافت شد (اولویت: FATAL، سطح پایین‌تر، خط پایین‌تر):</b>";
}

@Override
public String packages_problematicos() {
    return "<b>پکیج‌های مشکل‌دار یافت شد (اولویت: FATAL، سطح پایین‌تر، خط پایین‌تر):</b>";
}

@Override
public String faltar_de_clases_fatales() {
    return "<b>کلاس‌های فاتال گم‌شده یافت شد:</b>";
}

@Override
public String corchetes_ondulados() {
    return "<b>محتوای داخل {} (مهمترین در بالا، فقط 20 اول):</b>";
}

@Override
public String config_spongemixin_problematico(String archivo) {
    return "<b>پیکربندی مشکل‌دار SpongeMixin شناسایی شد: " + archivo + "</b>";
}
@Override
public String module_resolution_exception(String modules, String paquete) {
    return "شما ماژول‌هایی با بسته‌های تکراری دارید: " + modules + " بسته تکراری " + paquete.replace(".", "/") + ". شما می‌توانید این مشکل را با حذف بسته (پوشه) از فایل jar حل کنید. شما می‌توانید فایل jar را با استفاده از نرم‌افزارهای فشرده‌سازی مثل WinRAR,File-Roller, یا 7-Zip باز کنید، یا پسوند فایل را از .jar به .zip تغییر دهید، پوشه را حذف کنید و سپس آن را دوباره به فایل .jar تبدیل کنید.";
}

@Override
public String modlauncher_mods_duplicado(String linea) {
    return "<b>شما ماژول‌های تکراری دارید</b> " + linea.replace("from mod files", "از فایل‌های ماژول");
}

@Override
public String mcforge_mod_suspechoso() {
    return "<b>MinecraftForge مشکوک: این ماژول دارای مشکل است:</b> ";
}

@Override
public String lithostichctov() {
    return "<b>CTOV به lithostitched نیاز دارد، می‌توانید آن را از اینجا نصب کنید https://www.curseforge.com/minecraft/mc-mods/lithostitched</b>";
}

@Override
public String necesitasSodiumParaIris() {
    return "<b>برای استفاده از Iris Shaders یا Oculus، به Sodium یا یک کپی برای بارگذار دیگر (Rubidium، Embedium، Bedium) نیاز دارید</b>";
}


}
