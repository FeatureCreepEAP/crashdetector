package com.asbestosstar.crashdetectormc.idioma;
 
import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
 
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
public String problema_con_graficas_ati() {
    return "<span style='color:#" + config.obtenerColorError() + "'>به‌روزرسانی درایورهای ATI/AMD شما ممکن است کمک کند. برای رفع مشکل این راهنما را بخوانید: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>راهنمای به‌روزرسانی درایورها</a> https://www.amd.com/es/support/download/drivers.html دانلود </span>";
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
    
    
    @Override
public String problema_con_graficas_nvidia_windows_viejo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "مشکلاتی با درایورهای NVIDIA در نسخه‌های قبل از Windows 11 شناسایی شده است."
            + "</span><br/><br/>"
            + "برای اطمینان از اینکه Minecraft (و JVM فعلی) از کارت گرافیک اختصاصی NVIDIA استفاده کند، مراحل زیر را دنبال کنید:<br/><br/>"
            + "1. <strong>فایل اجرایی Java را شناسایی کنید:</strong><br/>"
            + "   - این برنامه از فایل اجرایی زیر برای Java استفاده می‌کند: "
            + obtenerRutaJava() + "<br/>"
            + "   - اگر مسیر خاصی مشاهده نمی‌کنید، می‌توانید فایل اجرایی Java را با جستجوی 'java.exe' در سیستم پیدا کنید.<br/><br/>"
            + "2. <strong>پنل کنترل NVIDIA را باز کنید:</strong><br/>"
            + "   - روی دسکتاپ راست کلیک کرده و گزینه 'پنل کنترل NVIDIA' را انتخاب کنید.<br/><br/>"
            + "3. <strong>GPU مورد نظر را تنظیم کنید:</strong><br/>"
            + "   - در پنل کنترل NVIDIA، به 'مدیریت تنظیمات 3D' بروید.<br/>"
            + "   - گزینه 'تنظیمات برنامه خاص' را انتخاب کنید.<br/>"
            + "   - روی 'افزودن' کلیک کنید و فایل اجرایی Java شناسایی شده قبلی (مثل: 'java.exe') را پیدا کنید.<br/>"
            + "   - مطمئن شوید که تنظیم شده است تا از 'پردازنده با عملکرد بالا (NVIDIA)' استفاده کند.<br/><br/>"
            + "4. <strong>تغییرات را ذخیره کنید:</strong><br/>"
            + "   - تغییرات را اعمال کرده و پنل کنترل NVIDIA را ببندید.<br/><br/>"
            + "5. <strong>Minecraft را مجدداً راه‌اندازی کنید:</strong><br/>"
            + "   - Minecraft را مجدداً راه‌اندازی کنید تا تغییرات اعمال شوند.<br/><br/>"
            + "اگر از Windows Server 2022 یا Windows 10 استفاده می‌کنید، این مراحل در صورتی معتبر هستند که آخرین درایورهای NVIDIA را نصب کرده باشید.<br/><br/>"
            + "توجه: اگر نتوانستید پنل کنترل NVIDIA را پیدا کنید، مطمئن شوید که درایورهای NVIDIA به درستی نصب شده‌اند.";
}



@Override
public String problema_con_graficas_nvidia_windows_nuevo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "مشکلاتی با درایورهای NVIDIA در Windows 11/Server 2025 یا جدیدتر شناسایی شده است."
            + "</span><br/><br/>"
            + "برای اطمینان از اینکه Minecraft (و JVM فعلی) از کارت گرافیک اختصاصی NVIDIA استفاده کند، مراحل زیر را دنبال کنید:<br/><br/>"
            + "1. <strong>فایل اجرایی Java را شناسایی کنید:</strong><br/>"
            + "   - این برنامه از فایل اجرایی زیر برای Java استفاده می‌کند: "
            + obtenerRutaJava() + "<br/>"
            + "   - اگر مسیر خاصی مشاهده نمی‌کنید، می‌توانید فایل اجرایی Java را با جستجوی 'java.exe' در سیستم پیدا کنید.<br/><br/>"
            + "2. <strong>برنامه تنظیمات را باز کنید:</strong><br/>"
            + "   - کلیدهای <code>Win + I</code> را فشار دهید تا برنامه تنظیمات باز شود.<br/>"
            + "   - به <strong>سیستم > نمایش > گرافیک</strong> بروید.<br/><br/>"
            + "3. <strong>GPU مورد نظر را تنظیم کنید:</strong><br/>"
            + "   - در بخش 'گرافیک'، روی 'تنظیمات گرافیک پیش‌فرض' کلیک کنید.<br/>"
            + "   - گزینه 'برنامه‌های دسکتاپ' را انتخاب کرده و سپس روی 'مرور' کلیک کنید.<br/>"
            + "   - فایل اجرایی Java شناسایی شده قبلی (مثل: 'java.exe') را پیدا کرده و انتخاب کنید.<br/>"
            + "   - پس از اضافه شدن، برنامه Java را در لیست انتخاب کرده و آن را برای استفاده از 'عملکرد بالا (NVIDIA)' تنظیم کنید.<br/><br/>"
            + "4. <strong>تغییرات را ذخیره کنید:</strong><br/>"
            + "   - تغییرات را اعمال کرده و برنامه تنظیمات را ببندید.<br/><br/>"
            + "5. <strong>Minecraft را مجدداً راه‌اندازی کنید:</strong><br/>"
            + "   - Minecraft را مجدداً راه‌اندازی کنید تا تغییرات اعمال شوند.<br/><br/>"
            + "اگر از Windows 11 یا Windows Server 2025+ استفاده می‌کنید، این مراحل در صورتی معتبر هستند که آخرین درایورهای NVIDIA را نصب کرده باشید.<br/><br/>"
            + "توجه: اگر نتوانستید گزینه تنظیمات گرافیک را پیدا کنید، مطمئن شوید که درایورهای NVIDIA به درستی نصب شده‌اند.";
}





@Override
public String segundo60Tick() {
    return "<b style='color:#" + config.obtenerColorError() + "'>سرور یا دنیای شما تیک‌های بیش از 60 ثانیه دارد. این ممکن است به دلیل آن باشد که مودها سرور را کندتر می‌کنند یا سخت‌افزار خیلی ضعیف است.</b>";
}



@Override
public String noTieneMemoria() {
    return "<b style='color:#" + config.obtenerColorError() + "'>RAM/حافظه کافی ندارید. باید حافظه بیشتری اختصاص دهید.</b>";
}



@Override
public String theseus() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>اگر از Theseus/ModrinthApp استفاده می‌کنید، نمی‌توانیم کمک زیادی به شما کنیم زیرا Theseus فاقد کنسول لانچر است. Theseus همچنین مشکلات دیگری دارد، از جمله نسخه‌های قدیمی بارگذارهای مود، نرم‌افزار جاسوس، لاگ‌های بد و موارد دیگر. شرکت Modrinth هم صادقانه عمل نمی‌کند. آنها اتهامات کاذب می‌زنند که توسعه‌دهندگان مودها از ربات‌ها برای افزایش دانلودها استفاده می‌کنند و ادعاهای خود درباره درآمدزایی را چندین بار تغییر داده‌اند.</b>";
}

@Override
public String noTieneConsolaDeLauncherCursedForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>شما فایل launcher_log.txt را ندارید. ما به این فایل برای جستجوی خطاها نیاز داریم. این به دلیل گزینه \"رد کردن شروع برنامه‌ریز\" است. آن را غیرفعال کنید.</b>";
}
    
@Override
public String faltar_de_clases_advertencia() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>هشدار: کلاس‌های گم‌شده شناسایی شدند:</b>";
}


@Override
public String noResultos() {
    return "<b style='color:#" + config.obtenerColorError() + "'>نتیجه‌ای یافت نشد</b>";
}

@Override
public String ubicacionesDeLogs() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>مکان‌های لاگ‌ها:</b>";
}

@Override
public String infoDeVerificaciones() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>اینجا نتایج بررسی‌های شما هستند. تعمیر بخش‌های بالایی لاگ‌ها اولویت اول است. آن را به آرامی انجام دهید.</b>";
}


@Override
public String versionDeJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>لطفاً برای نسخه‌های 1.17-1.20.4 از Java 17 و برای نسخه‌های جدیدتر از Java 21 استفاده کنید. برای نسخه‌های قدیمی‌تر از Java 8 استفاده کنید. [راهنما](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). اگر همچنان مشکل دارید، ممکن است به دلیل این باشد که برخی از مودها فایل‌های خیلی قدیمی یا خیلی جدید دارند.</b>";
}

@Override
public String java22() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java 22 و بالاتر در نسخه‌های Minecraft زیر 1.20.5 برای بیشتر بارگذارهای مود کار نمی‌کند زیرا ASM منسوخ شده است.</b>" + versionDeJava();
}

@Override
public String javaObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java منسوخ شده است </b>" + versionDeJava();
}

@Override
public String jpms_modules_faltas(String mod_necesitas, String submod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>شما به ماژول JPMS " + mod_necesitas + " از " + submod + " نیاز دارید</b>";
}

@Override
public String null_pointer_error(String metodo, String objeto) {
    return "<b style='color:#" + config.obtenerColorError() + "'>نمی‌توان " + metodo + " را فراخوانی کرد زیرا " + objeto + " برابر با null است</b>";
}

@Override
public String analisisAvanzado() {
    return "تحلیل پیشرفته";
}

@Override
public String seleccionarCarpeta() {
    return "انتخاب پوشه";
}

@Override
public String cadenaBusqueda() {
    return "رشته جستجو";
}

@Override
public String usarRegex() {
    return "استفاده از عبارت منظم (Regex)";
}

@Override
public String ignorarMayusculas() {
    return "نادیده گرفتن حروف بزرگ";
}

@Override
public String buscar() {
    return "جستجو";
}

@Override
public String busquedaEnProgreso() {
    return "جستجو در حال انجام است";
}

@Override
public String noSeEncontraronResultados() {
    return "نتیجه‌ای یافت نشد";
}

@Override
public String errorBusqueda() {
    return "خطا در جستجو";
}

@Override
public String noRegistroDeLauncher() {
    return "هیچ لاگی از لانچر پیدا نشد! این ممکن است بررسی را پیچیده‌تر کند.\n"
            + "                \n"
            + "                برای دریافت لاگ‌های صحیح:\n"
            + "                - MultiMC/PolyMC/PrismLauncher/PollyMC/UltimMC: توجه: لاگ‌های شناسایی‌شده به‌صورت خودکار صحیح نیستند.\n"
            + "                  1. رابط نمونه را باز کنید\n"
            + "                  2. به بخش \"Minecraft Log\" بروید\n"
            + "                  3. روی آن راست‌کلیک کنید و محتوای آن را کپی کنید\n"
            + "                - CurseForgeApp:\n"
            + "                  1. بدون رد کردن لانچر، بازی را دوباره راه‌اندازی کنید\n"
            + "                  \n";
}

@Override
public String omitirYCerrar() {
    return "رد کردن و بستن";
}

@Override
public String guardarYCerrar() {
    return "ذخیره و بستن";
}

@Override
public String pegaLosRegistrosAqui() {
    return "لاگ‌ها را اینجا قرار دهید";
}

@Override
public String archivo() {
    return "فایل";
}

@Override
public String incluir() {
    return "شامل شدن";
}

@Override
public String abrir() {
    return "باز کردن";
}

@Override
public String endpointDeInforme() {
    return "نقاط پایانی گزارش";
}

@Override
public String sitoDeLogging() {
    return "وب‌سایت ثبت لاگ:";
}

@Override
public String apiDeLogging() {
    return "API ثبت لاگ:";
}

@Override
public String anonimizarRegistros() {
    return "بی‌هویت کردن لاگ‌ها (بتا)";
}

@Override
public String botonDeCompartirInforme() {
    return "اشتراک‌گذاری گزارش و تمام لاگ‌های انتخاب‌شده";
}

@Override
public String arco() {
    return "این گفتگو به شما امکان می‌دهد لاگ‌ها را با استفاده از API SecureLogger در securelogger.net به اشتراک بگذارید. "
            + "هنگام فشار دادن دکمه‌های اشتراک‌گذاری، فایل‌ها به سایت انتخاب‌شده (پیش‌فرض asbestosstar.egoism.jp) آپلود می‌شوند. شما می‌توانید تمام لاگ‌های انتخاب‌شده را همراه با گزارش به اشتراک بگذارید. اگر نمی‌خواهید آپلود کنید، از این گفتگو استفاده نکنید! ما گزارش شما را در نقاط پایانی رسمی (https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb) پردازش نمی‌کنیم؛ فقط لینک‌های غیرمجاز حذف می‌شوند. کد اینجا قرار دارد: https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb. این فقط برای نمایش اطلاعات درباره خرابی و لینک به لاگ‌ها استفاده می‌شود. با این حال، می‌توانید از یک نقطه پایانی سفارشی استفاده کنید که ممکن است شامل همان روش‌ها نباشد. شما از سایت گزارش‌ها " 
            + Config.obtenerInstancia().obtenerSitoDeInformes() + " و سایت لاگ‌ها " 
            + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + " استفاده می‌کنید. CrashDetector به طور پیش‌فرض دارای بی‌هویت‌سازی لاگ‌هاست، که سعی می‌کند نام‌های کاربری، UUIDها، توکن‌های دسترسی، شناسه‌های جلسه، آدرس‌های IP و داده‌های دیگر را حذف کند. با این حال، این فرآیند کامل نیست. با این حال، نویسنده مجموعه افزونه‌ها می‌تواند آن را غیرفعال کند. می‌توان آن را با جعبه تیک در پایین این صفحه فعال یا غیرفعال کرد.";
}

@Override
public String enlaceDelReporte() {
    return "لینک گزارش:";
}


@Override
public String guardarConfigDeCompartir() {
    return "ذخیره تنظیمات اشتراک‌گذاری";
}


@Override
public String registroDemasiadoGrande() {
    return "لاگ بسیار بزرگ است برای این سایت ثبت لاگ. لطفاً سایت دیگری انتخاب کنید و دوباره تلاش کنید.";
}

@Override
public String errorConPublicarRegistro(String error) {
    return "خطا در انتشار لاگ " + error;
}

@Override
public String apiDeRegistroNoExiste() {
    return "API ثبت لاگ وجود ندارد. لطفاً API ثبت لاگ را در تنظیمات تغییر دهید.";
}

@Override
public String errorSSL() {
    return "خطای SSL دارید. این موضوع در نسخه‌های قدیمی Java رایج است، "
            + "از جمله نسخه‌های Java 8 در لانچر پیش‌فرض Minecraft و نسخه‌های موجود در sun.com و java.com. "
            + "این مشکل بر بسیاری از جنبه‌ها تأثیر می‌گذارد، از جمله فایل‌های JAR نصب‌کننده MinecraftForge، "
            + "عملکرد اشتراک‌گذاری گزارش‌های CrashDetector هنگام استفاده از نقطه پایانی پیش‌فرض، "
            + "برخی افزونه‌هایی که به اینترنت نیاز دارند و برخی سایت‌های ثبت لاگ. "
            + "اگر این مشکل را هنگام تلاش برای اشتراک‌گذاری گزارش داشتید، "
            + "کافی است یک عکس از صفحه ضمیمه کنید و سایت ثبت لاگ سازگار با نسخه‌های قدیمی Java 8 را انتخاب کنید.";
}



@Override
public String errorJavaFMLVersion(String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "نسخه ناسازگار JavaFML: نیاز به نسخه " + requerido 
         + "، پیدا شده " + encontrado + "</b>";
}

@Override
public String errorJavaFML_MCForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "توجه! JavaFML به نسخه خاصی از Minecraft Forge نیاز دارد</b>";
}

@Override
public String errorProveedorVersion(String proveedor, String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "ارائه‌دهنده " + proveedor + " ناسازگار: "
         + "نیاز به " + requerido + "، پیدا شده " + encontrado + "</b>";
}

@Override
public String advertenciaMalwareFalso() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "هشدار! Crash Assistant یک تشخیص‌دهنده بدافزار جعلی است. این برنامه به‌طور عمدی بازی را مسدود می‌کند و آزادی شما را برای ادامه بازی با افزونه‌های هدف‌گیری‌شده در نظر نمی‌گیرد. "
         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/02caebaaac98e3e226337e27b226ead568363815/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java'>مشاهده کد MalwareMod.java</a>   "
         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>مشاهده کد JarInJarHelper.java</a></b>";
}





}
