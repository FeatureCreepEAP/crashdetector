package com.asbestosstar.crashdetector.idioma;

import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;

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
		return "<span style='color:#" + config.obtenerColorInfo() + "'>در حال جستجو برای PID: " + String.valueOf(pid)
				+ "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") متوقف شده است!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>JVM وجود ندارد</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>به‌روزرسانی درایورهای ATI/AMD شما ممکن است کمک کند. برای رفع مشکل این راهنما را بخوانید: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>راهنمای به‌روزرسانی درایورها</a> https://www.amd.com/es/support/download/drivers.html دانلود </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>برخی از نسخه های قدیمی در رابط بارگذاری اولیه کارت گرافیک Nouveau گاهی اوقات دچار مشکل می شوند.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>شما مشکل درایور کارت گرافیک دارید. اگر از GPU یا APU AMD/ATI استفاده می کنید، لطفاً درایور کارت گرافیک AMD خود را به روز کنید. اگر از کارت گرافیک NVIDIA استفاده می کنید، مطمئن شوید که بازی و تمام نمونه های javaw.exe برای استفاده از کارت گرافیک مجزا تنظیم شده اند. این راهنما را بخوانید: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>راهنمای به روز رسانی درایور</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>بارگذاری پنجره اولیه FML شما شکست خورد. برای رفع این مشکل، به (.minecraft/config/fml.toml) بروید و earlyWindowProvider را به \"none\" تنظیم کنید. اگر از Mac M1 استفاده می کنید، مطمئن شوید که از نسخه ARM Java استفاده می کنید، نه نسخه Intel x64. این همچنین یک مشکل رایج درایور قدیمی است. اگر از ویندوز استفاده می کنید و غیرفعال کردن این تنظیمات بی فایده بود، این راهنما را مطالعه کنید: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>راهنمای به روز رسانی درایور</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError() + "'>وابستگی های لازم را ندارید:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "درخواست شده توسط").replace("Expected range", "محدوده انتظاری")
				+ "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>گزارش CrashDetector شما در اینجا است <a href='"
				+ archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>مشاهده گزارش</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>این رابط کاربری GUI CrashDetector است. اگر بازی به طور معمول بسته شد، لطفا این رابط را نادیده بگیرید.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>مشاهده گزارش</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>گزارش محلی را در مرورگر مشاهده کنید.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "به اشتراک گذاشتن گزارش";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "گزارش را به اشتراک بگذارید. لاگ های شما به securelogger.net و گزارش به سایت های دیگر آپلود می شود.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>فایل JAR مشکوک شناسایی شد (اولویت با FATAL، سپس با اولویت بالا و کم):</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> سطح:</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>احتمالاً کشنده:</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ModID های مشکوک شناسایی شدند (اولویت با FATAL، سپس با اولویت کم و کم):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>بسته های مشکوک شناسایی شدند (اولویت با FATAL، سپس با اولویت کم و کم):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>کلاس‌های مرگبار (FATAL) دارید، این موضوع بسیار جدی است. دلایل رایج شامل کورمدهای بد یا وابستگی‌های مرگبار است. می‌توانید از QuickFix برای یافتن مودهای دارای کلاس‌های مرگبار استفاده کنید. کلاس‌های مرگبار گمشده شناسایی شده:</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>محتوای {} (مهم ترین در بالا، فقط 20 مورد اول نمایش داده می شود):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError() + "'>پیکربندی SpongeMixin مشکوک شناسایی شد: " + "</b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>شما مد هایی با پکیج‌های تکراری دارید. می‌توانید این مشکل را با حذف کردن پوشه‌ی تکراری (پکیج) از فایل جار حل کنید. می‌توانید فایل جار را در یک نرم‌افزار فشرده‌سازی مثل وین‌رار یا 7ز باز کنید، همچنین می‌توانید پسوند فایل را از .جار به زیپ تغییر دهید، سپس پوشه را حذف کنید و دوباره آن را به فایل .جار تغییر نام دهید.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mods تکراری شناسایی شدند</b> "
				+ linea.replace("from mod files", "از فایل های mod");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge mod مشکوک شناسایی کرد:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV به lithostitched نیاز دارد، شما می توانید آن را از اینجا نصب کنید: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>برای استفاده از Iris shaders یا Oculus، شما به نسخه سازگار SODIUM یا بارگذارهای دیگر (Rubidium, Embedium, Bedium) نیاز دارید</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>مشکل در افزونه KubeJS </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "مشکلاتی با درایورهای NVIDIA در نسخه‌های قبل از Windows 11 شناسایی شده است." + "</span><br/><br/>"
				+ "برای اطمینان از اینکه Minecraft (و JVM فعلی) از کارت گرافیک اختصاصی NVIDIA استفاده کند، مراحل زیر را دنبال کنید:<br/><br/>"
				+ "1. <strong>فایل اجرایی Java را شناسایی کنید:</strong><br/>"
				+ "   - این برنامه از فایل اجرایی زیر برای Java استفاده می‌کند: " + obtenerRutaJava() + "<br/>"
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
				+ "   - این برنامه از فایل اجرایی زیر برای Java استفاده می‌کند: " + obtenerRutaJava() + "<br/>"
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>سرور یا دنیای شما تیک‌های بیش از 60 ثانیه دارد. این ممکن است به دلیل آن باشد که مودها سرور را کندتر می‌کنند یا سخت‌افزار خیلی ضعیف است.</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>RAM/حافظه کافی ندارید. باید حافظه بیشتری اختصاص دهید.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>اگر از Theseus/ModrinthApp استفاده می‌کنید، نمی‌توانیم کمک زیادی به شما کنیم زیرا Theseus فاقد کنسول لانچر است. Theseus همچنین مشکلات دیگری دارد، از جمله نسخه‌های قدیمی بارگذارهای مود، نرم‌افزار جاسوس، لاگ‌های بد و موارد دیگر. شرکت Modrinth هم صادقانه عمل نمی‌کند. آنها اتهامات کاذب می‌زنند که توسعه‌دهندگان مودها از ربات‌ها برای افزایش دانلودها استفاده می‌کنند و ادعاهای خود درباره درآمدزایی را چندین بار تغییر داده‌اند.</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>شما فایل launcher_log.txt را ندارید. ما به این فایل برای جستجوی خطاها نیاز داریم. این به دلیل گزینه \"رد کردن شروع برنامه‌ریز\" است. آن را غیرفعال کنید.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>هشدار: کلاس‌های گمشده شناسایی شدند (سطح هشدار). معمولاً مشکلی نیست، اما همیشه اینطور نیست — با خطاهای فاتال فرق دارد. دلایل رایج شامل کورمدهای بد یا وابستگی‌های گمشده است. می‌توانید از QuickFix برای جستجوی مودهای دارای کلاس‌های گمشده استفاده کنید. کلاس‌های گمشده شناسایی شده:</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>نتیجه‌ای یافت نشد</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>مکان‌های لاگ‌ها:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>در اینجا نتایج بررسی‌های شما آمده است. اولویت اول، رفع مشکلات بالای ردپای پشته است. آرام پیش بروید؛ معمولاً علت صحیح در بررسی ۱ یا ۲ است. سایر موارد (خطاهای ۳ به بعد) می‌توانند برای تأیید استفاده شوند اما اغلب خطاهای زنجیره‌ای هستند و عموماً می‌توان آنها را نادیده گرفت. خطاها به صورت لایه‌ای رخ می‌دهند، بنابراین رفع مشکل اصلی امروز این خطا را حل می‌کند، اما فردا ممکن است خطای جدیدی که ارتباطی با خطای فعلی ندارد ظاهر شود، چون اغلب یک خطا مانع نمایش خطا‌های دیگر در کنسول می‌شود.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>لطفاً برای نسخه‌های 1.17-1.20.4 از Java 17 و برای نسخه‌های جدیدتر از Java 21 استفاده کنید. برای نسخه‌های قدیمی‌تر از Java 8 استفاده کنید. [راهنما](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). اگر همچنان مشکل دارید، ممکن است به دلیل این باشد که برخی از مودها فایل‌های خیلی قدیمی یا خیلی جدید دارند.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 و بالاتر در نسخه‌های Minecraft زیر 1.20.5 برای بیشتر بارگذارهای مود کار نمی‌کند زیرا ASM منسوخ شده است.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java منسوخ شده است </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>شما به ماژول JPMS " + mod_necesitas + " از "
				+ submod + " نیاز دارید</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>نمی‌توان " + metodo + " را فراخوانی کرد زیرا "
				+ objeto + " برابر با null است</b>";
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
		return "این گفتگو به شما امکان می‌دهد که با استفاده از API SecureLogger "
				+ "در securelogger.net لاگ‌ها را به اشتراک بگذارید. هنگام فشار دادن دکمه اشتراک‌گذاری گزارش، گزارش شما به "
				+ "اپتاین پایانی انتخاب‌شده (پیش‌فرض asbestosstar.egoism.jp) (قابل تغییر در پایین) ارسال می‌شود. می‌توانید تمام لاگ‌های انتخاب‌شده را "
				+ "همراه با گزارش به اشتراک بگذارید. اگر نمی‌خواهید آپلود کنید، از این گفتگو استفاده نکنید! ما گزارش شما را در اپتاین پایانی رسمی (https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb) پردازش نمی‌کنیم؛ فقط لینک‌های غیرمجاز را حذف می‌کنیم. کد اینجا قرار دارد: https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb. این تنها برای نمایش اطلاعات درباره خرابی شما و لینک به لاگ‌ها استفاده می‌شود. با این حال، ممکن است از اپتاین پایانی سفارشی استفاده کنید که ممکن است روش‌های مشابهی نداشته باشد. شما از وبسایت گزارش‌دهی "
				+ Config.obtenerInstancia().obtenerSitoDeInformes() + " و وبسایت لاگ‌ها "
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado()
				+ " استفاده می‌کنید. همچنین می‌توانید لاگ‌های انفرادی را بدون گزارش با فشار دادن دکمه‌های اشتراک‌گذاری کنار نام‌های لاگ‌ها به اشتراک بگذارید؛ لاگ‌ها به وبسایت لاگ‌های انتخاب‌شده ارسال می‌شوند. CrashDetector دارای ناشناس‌سازی پیش‌فرض لاگ‌ها است که سعی می‌کند نام‌های کاربری، UUIDها، توکن‌های دسترسی، شناسه‌های جلسه، آدرس‌های IP و دیگر داده‌ها را حذف کند. با این حال، این عملکرد کامل نیست. با این حال، نویسنده مجموعه مودها می‌تواند آن را غیرفعال کند. می‌توان آن را با جعبه‌ای در پایین این صفحه فعال یا غیرفعال کرد. شما کنترل‌کننده داده‌های خود هستید؛ شما تصمیم می‌گیرید که داده‌هایتان را کجا آپلود کنید. وبسایت‌های لاگ‌ها متعلق به طرف‌های ثالث هستند که مالکیت آن‌ها اغلب به دلایل حریم خصوصی مخفی است. شما مسئولیت کامل مدیریت داده‌های خود و ریسک‌های مرتبط با آن را بر عهده دارید. گفتگوی اشتراک‌گذاری CrashDetector تنها یک رابط است که به شما اجازه می‌دهد آن را مدیریت کنید. مهم است که از GDPR و ARCO آگاه باشید.";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "نسخه ناسازگار JavaFML: نیاز به نسخه "
				+ requerido + "، پیدا شده " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "توجه! JavaFML به نسخه خاصی از Minecraft Forge نیاز دارد</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "فایل JAR '" + archivoJar
				+ "' به ارائه‌دهنده زبان '" + proveedor + "' نسخه '" + requerido
				+ "' یا بالاتر نیاز دارد، اما فقط نسخه '" + encontrado + "' پیدا شد.</b>";
	}

//@Override
//public String advertenciaMalwareFalso() {
//    return "<b style='color:#" + config.obtenerColorError() + "'>"
//         + "هشدار! Crash Assistant یک تشخیص‌دهنده بدافزار جعلی است. این برنامه به‌طور عمدی بازی را از اجرای شروع مسدود می‌کند و آزادی شما برای ادامه بازی با افزونه‌های هدف‌گیری‌شده را نادیده می‌گیرد. "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>مشاهده کد MalwareMod.java</a>   "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>مشاهده کد JarInJarHelper.java</a>. "
//         + "در حال حاضر فقط این افزونه در لیست آنها قرار دارد و در واقع فقط به سایت ثبت لاگ پیش‌فرض حمله می‌کنند که می‌تواند توسط کاربر تغییر کند و این فقط زمانی اتفاق می‌افتد که به‌صورت صریح از ویژگی مشارکت لاگ داخلی استفاده کنید. CrashAssistant هیچ بررسی‌ای برای حتی تعیین اینکه کدام سایت ثبت لاگ استفاده می‌شود انجام نمی‌دهد و نحوه تغییر آن را توضیح نمی‌دهد (یک منوی کشویی در پایین جعبه محاوره‌ای اشتراک وجود دارد) و صرف‌نظر از اینکه چه سایتی تنظیم کرده‌اید، CrashAssistant اجرای بازی را مسدود خواهد کرد. در پیام آنها اشاره شده که تحقیقات خود را انجام دهید، این کار را انجام دهید، به کد CrashDetector و Crash Assistant نگاه کنید و درک کنید که چه کاری انجام می‌دهند، به استدلال اقتباس به مقام اعتماد نکنید.</b>";
//}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "مود '" + idMod
				+ "' به دلیل عدم یافتن کلاس مورد نیاز شکست خورده است: '" + claseNoEncontrada
				+ "'. مطمئن شوید که تمام وابستگی‌ها به درستی نصب شده‌اند.</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Watermedia بازی با TLauncher را مسدود کرده است.</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "شما از نسخه‌ای از Optifine برای یک نسخه منسوخ شده از Minecraft استفاده می‌کنید. باید از نسخه Optifine متناسب با نسخه Minecraft که استفاده می‌کنید، استفاده کنید.</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "سرویس ModLauncher بارگذاری نشد: </b>"
				+ servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطا در تجزیه فایل JSON '" + recurso
				+ "' از فایل JAR '" + archivoJar + "'. مشکلاتی با ثبت وجود دارد.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "خطا: مود '" + modId + "' به نسخه '"
				+ requerido + "' یا بالاتر از '" + dependencia + "' نیاز دارد، اما '" + actual + "' پیدا شد."
				+ "</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "گرافیک شما از نسخه مورد نیاز OpenGL برای این نسخه بازی پشتیبانی نمی‌کند. درایورهای خود را به‌روز کنید یا کارت گرافیک خود را عوض کنید.</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "حافظه اختصاص داده شده به بازی را افزایش دهید یا مصرف مودها/پلاگین‌ها را کاهش دهید</b>";
	}

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "JVM 32 بیتی شناسایی شد: نمی‌تواند بیش از 4 گیگابایت رم استفاده کند. "
				+ "برای استفاده از تمام حافظه موجود، JVM 64 بیتی نصب کنید</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطای بحرانی در حافظه PermGen. فضای حافظه دائمی را افزایش دهید یا بارگذاری کلاس‌ها را کاهش دهید</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطای سازگاری بین Java 8 و نسخه‌های مدرن</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+ پشتیبانی نمی‌شود - برای نسخه‌های قدیمی Forge از Java 8 استفاده کنید</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 8 مورد نیاز است (نسخه 52.0). به‌روزرسانی کنید یا به درستی پیکربندی کنید</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطای بحرانی سازگاری: بلوک‌ها در این نسخه پشتیبانی نمی‌شوند. "
				+ "اطمینان حاصل کنید که نسخه‌های مودها و سرور سازگار هستند</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطا در تنظیمات مانیتور: "
				+ "تنظیم حالت صفحه نمایش امکان‌پذیر نبود. " + "بررسی کنید:</b>" + "<br>- تنظیمات چند مانیتوری"
				+ "<br>- درایورهای کارت گرافیک به‌روز شده" + "<br>- رزولوشن پشتیبانی‌شده توسط سیستم";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطا در گزینه‌های جاوا: "
				+ "گزینه‌های زباله‌جمع‌کن در تضاد هستند. "
				+ "اطمینان حاصل کنید که چندین الگوریتم GC را در پارامترهای JVM ترکیب نکنید</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطای بحرانی تنظیمات Forge: "
				+ "فایل تنظیمات خراب یا ناقص است. " + "پوشه 'config' را حذف کرده و بازی را دوباره راه‌اندازی کنید</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطای درایور Intel HD Graphics شناسایی شد. راه‌حل‌ها:</b>"
				+ "<br>1. درایورهای Intel را از <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a> به‌روز کنید (حداقل نسخه 15.xx.xx.xx)"
				+ "<br>2. در Minecraft: گزینه‌ها → ویدئو → 'Enable VBOs' و 'VSync' را فعال کنید"
				+ "<br>3. 1GB-2GB حافظه RAM را به بازی در لانچر اختصاص دهید"
				+ "<br>4. در طول به‌روزرسانی، موقتاً آنتی‌ویروس/دیوارآتش را غیرفعال کنید";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "هشدار: کلاس‌های گم‌شده شناسایی شدند";
	}

	public String nombre_de_bloque_teselado() {
		return "خطای رندر بلوک";
	}

	public String nombre_de_contenido_de_stacktrace() {
		return "تحلیل استک‌تریس";
	}

	public String nombre_de_cursed_consola() {
		return "کنسول ناقص CurseForge";
	}

	public String nombre_de_early_window() {
		return "خطای پنجره اولیه (FMLEarlyWindow)";
	}

	public String nombre_de_drivers() {
		return "مشکلات درایور ویدئو";
	}

	public String nombre_de_error_de_config_mcforge() {
		return "پیکربندی خراب MCForge";
	}

	public String nombre_de_error_de_monitor_lwjgl() {
		return "عدم موفقیت در حالت نمایش (LWJGL)";
	}

	public String nombre_de_fabricmc_runtime_error_provided_by() {
		return "خطای اجرای FabricMC";
	}

	public String nombre_de_falta_module_jmps() {
		return "ماژول‌های JPMS گم‌شده";
	}

	public String nombre_de_faltar_de_clases() {
		return "کلاس‌های گم‌شده بحرانی";
	}

	public String nombre_de_faltas_dependencias_de_modlauncher() {
		return "وابستگی‌های گم‌شده ModLauncher";
	}

	public String nombre_de_java_versiones() {
		return "نسخه‌های ناسازگار جاوا";
	}

	public String nombre_de_faltar_de_kubejs_resourcepack() {
		return "خطای منابع KubeJS";
	}

	public String nombre_de_lenguaje_proveedor_check() {
		return "زبان ارائه‌دهنده ناسازگار";
	}

	public String nombre_de_faltar_de_liyhostictchctov() {
		return "خطای Litchhost";
	}

	public String nombre_de_malware_falso_crash_assistant() {
		return "تشخیص بدافزار کاذب";
	}

	public String nombre_de_mcforge_mod_sespechoso() {
		return "شناسایی مود مشکوک";
	}

	public String nombre_de_mods_duplicados_modlauncher() {
		return "مودهای تکراری در ModLauncher";
	}

	public String nombre_de_modules_duplicados_jmps() {
		return "تعارض ماژول‌های JPMS";
	}

	public String nombre_de_necesitas_sodium() {
		return "برای Iris به Sodium نیاز دارید";
	}

	public String nombre_de_no_puede_analizar_json_de_registro() {
		return "نمی‌توان JSON ثبت‌شده را تحلیل کرد";
	}

	public String nombre_de_no_tiene_memoria() {
		return "حافظه کافی نیست";
	}

	public String nombre_de_null_pointer() {
		return "خطای اشاره‌گر خالی (NullPointerException)";
	}

	public String nombre_de_opciones_java_gc_invalidas() {
		return "گزینه‌های نامعتبر GC جاوا";
	}

	public String nombre_de_optifine_obsoleta() {
		return "OptiFine قدیمی/ناسازگار";
	}

	public String nombre_de_60_segundo_trick() {
		return "تیک سرور بحرانی (60 ثانیه)";
	}

	public String nombre_de_servicio_de_modlauncher_no_funciona() {
		return "خدمات ModLauncher با شکست مواجه شد";
	}

	public String nombre_de_spongemixin_configs_problematicos() {
		return "تنظیمات مشکل‌دار SpongeMixing";
	}

	public String nombre_de_theseus() {
		return "Theseus سازگار نیست";
	}

	public String nombre_de_watermedia_tl() {
		return "TLauncher توسط WATERMeDIA پشتیبانی نمی‌شود";
	}

	@Override
	public String auditorias_transformer() {
		return "بازرسی‌های ترانسفورمر";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "این‌ها نتایج محتوای بازرسی‌های ترانسفورمر در لانچر وانیلا هستند. معمولاً کمتر دقیق‌تر از تحلیل‌گر StackTrace است، اما لانچر وانیلا همیشه محتوای {} را ندارد</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "این ابزار می‌تواند بین مودهای شما به دنبال مودهای ساخته‌شده با MCreator بگردد. اگرچه بیشتر مودهای MCreator خوب هستند و مودهای عالی زیادی با MCreator ساخته شده‌اند، گاهی مشکلاتی دارند و شهرت بدی دارند. این ابزار به شناسایی آنها کمک می‌کند. توجه داشته باشید که حتی مودهای با امتیاز بالا ممکن است واقعاً با MCreator ساخته نشده باشند؛ به عنوان مثال، ممکن است از ادغام با MCreator استفاده کنند.";
	}

	@Override
	public String escanear() {
		return "اسکن";
	}

	@Override
	public String cargando() {
		return "در حال بارگذاری";
	}

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "fa";
	}

	@Override
	public String inicioApp() {
		return "شروع بازی/برنامه";
	}

	@Override
	public String ajustesCrashDetector() {
		return "تنظیمات تشخیص خرابی";
	}

	@Override
	public String confidencialidad() {
		return "حریم خصوصی";
	}

	@Override
	public String tooltip() {
		return "راهنمای ابزار";
	}

	@Override
	public String config() {
		return "پیکربندی";
	}

	@Override
	public String abrirCarpeta() {
		return "باز کردن پوشه";
	}

	@Override
	public String actualizar() {
		return "به‌روزرسانی";
	}

	@Override
	public String anadirRegistro() {
		return "افزودن ثبت";
	}

	@Override
	public String usarIdiomaDelSistema() {
		return "استفاده از زبان سیستم";
	}

	@Override
	public String volver() {
		return "بازگشت";
	}

	@Override
	public String colorFondo() {
		return "رنگ پس‌زمینه (#RRGGBB):";
	}

	@Override
	public String colorTexto() {
		return "رنگ متن (#RRGGBB):";
	}

	@Override
	public String colorBoton() {
		return "رنگ دکمه (#RRGGBB):";
	}

	@Override
	public String colorCajaTexto() {
		return "رنگ جعبه متن (#RRGGBB):";
	}

	@Override
	public String colorEnlace() {
		return "رنگ لینک (#RRGGBB):";
	}

	@Override
	public String colorTitulosConsolas() {
		return "رنگ عنوان کنسول (#RRGGBB):";
	}

	@Override
	public String colorError() {
		return "رنگ خطا (#RRGGBB):";
	}

	@Override
	public String colorAdvertencia() {
		return "رنگ هشدار (#RRGGBB):";
	}

	@Override
	public String colorInfo() {
		return "رنگ اطلاعات (#RRGGBB):";
	}

	@Override
	public String colorTitulo() {
		return "رنگ عنوان (#RRGGBB):";
	}

	@Override
	public String colorEnlaceTexto() {
		return "رنگ متن لینک (#RRGGBB):";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "فقط در صورت خرابی CrashDetector را باز کنید";
	}

	@Override
	public String activar_parche() {
		return "فعال‌سازی پچ";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "هیچ راه‌حلی موجود نیست";
	}

	@Override
	public String error() {
		return "خطا";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "خطا در حذف Jar";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "Jar با موفقیت حذف شد";
	}

	@Override
	public String exito() {
		return "موفقیت";
	}

	@Override
	public String eliminar() {
		return "حذف";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "خطا در حذف بسته";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "بسته با موفقیت حذف شد";
	}

	@Override
	public String eliminar_paquete() {
		return "حذف بسته";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "هیچ مودی با بسته پیدا نشد";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "امکان حذف بسته وجود ندارد";
	}

	@Override
	public String eliminar_jar() {
		return "حذف Jar";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "هیچ مود تکراری پیدا نشد";
	}

	@Override
	public String archivo_no_encontrado() {
		return "فایل پیدا نشد";
	}

	@Override
	public String directorio_eliminado() {
		return "پوشه حذف شد";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "خطا در حذف فایل Jar تو در تو";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "فایل داخلی یافت نشد";
	}

	@Override
	public String archivo_eliminado() {
		return "فایل حذف شد";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "خطا در حذف فایل";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "فایل خارجی معتبر نیست";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "عنصر مود حذف شد";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "خطا در جایگزینی فایل Jar خارجی";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "خطا در حذف عنصر مود";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "خطا در حذف پوشه";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "فرمت نامعتبر برای فایل Jar تو در تو";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "فایل Jar تو در تو حذف شد";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "خطا در پاک کردن فایل‌های موقت";
	}

	@Override
	public String mensaje_de_trace_fatal_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>پیام ردیابی فتال آخرین (ترجمه نشده):</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>پیام ردیابی آخرین (ترجمه نشده):</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "می‌توانید در پایگاه داده WaifuNeoForge جستجو کنید تا مود مناسب را پیدا کنید. نسخه بازی، بارگیر مود و کلاس را انتخاب کنید. از ترکیبی شبیه به آن استفاده کنید. فقط یک بار در دقیقه می‌توانید جستجو کنید.";
	}

	@Override
	public String solucionFaltasClases() {
		return "می‌توانید در پایگاه داده WaifuNeoForge جستجو کنید تا مود مناسب را پیدا کنید. نسخه بازی، بارگیر مود و کلاس را انتخاب کنید. از ترکیبی شبیه به آن استفاده کنید. فقط یک بار در دقیقه می‌توانید جستجو کنید.";
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "هر دو لانچر نسخه‌های درست جاوا را دارند اما نه همه آنها؛ شما می‌توانید نسخه صحیح جاوا را از مدیر بسته‌ها در سیستم خود یا با استفاده از دکمه‌ها نصب کنید.";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>مد با انیمیشن گمشده: " + "</b>";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException (خطای عناصر ناپدید شده) انیمیشن گمشده است";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "هیچ مدی برای حذف پیدا نشد";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "جایگزینی گزینه‌های GC متناقض با -XX:+UseG1GC";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "افزایش اندازه حافظه Heap با استفاده از گزینه -Xmx.";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "افزایش اندازه حافظه PermGen با استفاده از گزینه -XX:MaxPermSize.";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "استفاده از JVM 64 بیتی برای افزایش حافظه در دسترس.";
	}

	@Override
	public String optimizarCodigo() {
		return "بهینه‌سازی کد برای کاهش مصرف حافظه و بهبود عملکرد.";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "استفاده از یک جمع‌آورنده زباله کارآمد برای کاهش توقف برنامه.";
	}

	@Override
	public String modulos() {
		return "ماژول‌ها";
	}

	@Override
	public String paquete() {
		return "بسته";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "شناسه‌هایی گم شده‌اند. دلایل متداول شامل مد های گمشده یا داده‌های آیتم‌های گم‌شده است. پوشه‌های داده متداول عبارتند از datafiedcontents/ و kubejs/ یا سایر پوشه‌های مد.";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "ثبت‌های نامناسب";
	}

	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>پلاگین 'AuthMe' در هنگام بارگذاری شکست خورد و سرور را متوقف کرد.</b> ";
	}

	public String nombreProblemaCierreAuthMe() {
		return "مشکل خاموش شدن به دلیل AuthMe";
	}

	public String solucionCierreAuthMe() {
		return "قانون 'stopServer' به 'true' تغییر کرد.";
	}

	public String solucionConfigurarPluginAuthMe() {
		return "تنظیم پلاگین AuthMe (plugins/AuthMe/config.yml)";
	}

	public String solucionInstalarVersionDiferenteAuthMe() {
		return "نصب نسخه دیگری از پلاگین 'AuthMe'";
	}

	public String solucionEliminarPluginAuthMe() {
		return "حذف پلاگین 'AuthMe'";
	}

	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>جهان '" + nombreMundo
				+ "' قابل بارگذاری نیست چون شامل خطاهایی است و احتمالاً آسیب دیده است.</b> ";
	}

	@Override
	public String nombreProblemaCargaMultiverso() {
		return "مشکل در بارگذاری جهان Multiverse";
	}

	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "جهان '" + nombreMundo + "' را تعمیر کنید، مثلا با استفاده از Minecraft Region Fixer یا MCEdit.";
	}

	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "پوشه جهان '" + nombreMundo + "' را حذف کنید.";
	}

	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError() + "'>پیکربندی افزونه 'PermissionsEx' نامعتبر است.</b> ";
	}

	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "مشکل در پیکربندی PermissionsEx";
	}

	@Override
	public String solucionConfigurarPermissionsEx() {
		return "تنظیم افزونه PermissionsEx (plugins/PermissionsEx/permissions.yml)";
	}

	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "حذف افزونه 'PermissionsEx'";
	}

	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>چندین فایل افزونه با نام '" + nombrePlugin
				+ "' وجود دارد: '" + primerPath + "' و '" + segundoPath + "'.</b> ";
	}

	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "نام افزونه مبهم";
	}

	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "حذف افزونه '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError() + "'>خطایی هنگام بارگذاری چانک‌ها در جهان رخ داد.</b> ";
	}

	@Override
	public String nombreProblemaCargaChunk() {
		return "خطا در بارگذاری چانک‌ها";
	}

	@Override
	public String solucionEliminarChunk() {
		return "چانک مشکل‌دار را از جهان حذف کنید، مثلاً با استفاده از MCEdit یا حذف فایل منطقه.";
	}

	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>افزونه '" + nombrePlugin + "' نمی‌تواند دستور '/"
				+ comando + "' را اجرا کند.</b> ";
	}

	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "خطای اجرای دستور افزونه";
	}

	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "نصب نسخه دیگری از افزونه '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>افزونه '" + nombrePlugin
				+ "' به این وابستگی نیاز دارد: '" + dependencia + "'.</b> ";
	}

	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>افزونه '" + nombrePlugin
				+ "' فاقد وابستگی‌های لازم است: " + deps.toString() + ".</b> ";
	}

	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "وابستگی مفقود افزونه";
	}

	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "نصب افزونه '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>افزونه '" + nombrePlugin + "' به نسخه '"
				+ versionAPI + "' از API نیاز دارد که با سرور فعلی سازگار نیست.</b>";
	}

	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "نسخه API ناسازگار";
	}

	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "نسخه '" + version + "' از نرم‌افزار سرورتان را نصب کنید.";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>جهان '" + nombreMundo
				+ "' یک کپی از جهان دیگر است و قابل بارگذاری نیست.</b>";
	}

	@Override
	public String nombreProblemaMundoDuplicado() {
		return "جهان تکراری";
	}

	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "فایل 'uid.dat' از جهان '" + nombreMundo + "' را حذف کنید.";
	}

	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "پوشه جهان '" + nombreMundo + "' را حذف کنید.";
	}

	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "<b style='color:#" + config.obtenerColorError() + "'>نمونه بلوکی '" + nombre + "' از نوع '" + tipo
				+ "' در موقعیت " + coords + " باعث ایجاد خطا در ticking شده است.</b> ";
	}

	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "نمونه بلوکی مشکل‌دار";
	}

	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "نمونه '" + nombre + "' را در موقعیت " + coords
				+ " حذف کنید، مثلاً با استفاده از MCEdit یا ویرایش مستقیم دنیا.";
	}

	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>مود '" + nombreMod
				+ "' دارای چندین نسخه نصب شده است.</b> ";
	}

	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "مود تکراری در Fabric";
	}

	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "فایل مود تکراری را حذف کنید: " + rutaMod;
	}

	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>مودهای '" + primerMod + "' و '" + segundoMod
				+ "' با هم سازگار نیستند.</b>";
	}

	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "مود ناسازگار در Fabric";
	}

	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "مود '" + nombreMod + "' را حذف کنید";
	}

	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>مود '" + nombreMod
				+ "' دارای خطا است و نمی‌تواند اجرا شود.</b> ";
	}

	@Override
	public String nombreProblemaModFatal() {
		return "مود با خطا جدی";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>مودهای زیر ضروری هستند ولی نصب نشده‌اند: "
				+ deps.toString() + ".</b>";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>مود '" + nombreMod
					+ "' به عنوان وابستگی، مود '" + dependencia + "' را می‌خواهد.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>مود '" + nombreMod + "' مود '" + dependencia
					+ "' با نسخه " + version + " را می‌خواهد.</b>";
		}
	}

	@Override
	public String nombreProblemaDependenciaMod() {
		return "وابستگی مود گمشده";
	}

	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "مود '" + nombreMod + "' را نصب کنید";
	}

	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "مود '" + nombreMod + "' با نسخه " + version + " را نصب کنید";
	}

	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>افزونه '" + nombrePlugin
				+ "' با تیکینگ منطقه‌ای Folia سازگار نیست.</b> ";
	}

	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("افزونه‌های زیر با تیکینگ منطقه‌ای Folia سازگار نیستند: ");

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
		return "افزونه ناسازگار با تیکینگ منطقه‌ای";
	}

	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "یک نسخه بدون تیکینگ منطقه‌ای نظیر " + software + " را نصب کنید";
	}

	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>مود '" + nombreMod + "' در دیتپک یافت نشد.</b>";
	}

	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("مودهای زیر در دیتپک یافت نشدند: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" و ");
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
		return "مود یافت نشد در دیتپک";
	}

	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>مود '" + nombreMod + "' باعث یک خطا شد.</b>";
	}

	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("مودهای زیر باعث خطا شدند: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" و ");
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
		return "مود Forge دارای خطا";
	}

	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "یک نسخه دیگر از مود '" + nombreMod + "' را نصب کنید";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>مود '" + nombreMod + "' با نسخه ماين كرافت "
				+ versionMC + " سازگار نيست.</b>";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("مودهای زیر با نسخه‌های ماين كرافت سازگار نيستند: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (ماين كرافت ").append(versionesMC.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaModIncompatibleConMinecraft() {
		return "مود ناسازگار با ماين كرافت";
	}

	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "نسخه سازگار فورج برای ماين كرافت " + versionMC + " را نصب کنید";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>مود '" + nombreMod
				+ "' گمشده و برای بارگذاری دنیا یا افزونه مورد نیاز است.</b>";
	}

	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "مود گمشده";
	}

	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>دنیا با مود '" + nombreMod
				+ "' ذخیره شد که به نظر می‌رسد گمشده است.</b>";
	}

	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("دنیا با مودهای زیر ذخیره شد که به نظر می‌رسد گمشده‌اند: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" و ");
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
		return "مود گمشده در دنیا";
	}

	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>دنیا با مود '" + nombreMod + "' در نسخه "
				+ versionEsperada + " ذخیره شده است و اکنون نسخه " + versionActual + " فعال است.</b>";
	}

	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("مودهای زیر در دنیای ذخیره شده دارای اختلاف نسخه‌ای هستند: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" و ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (ذخیره شده: ").append(versionesEsperadas.get(i)).append(", فعلی: ")
					.append(versionesActuales.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaVersionModMundo() {
		return "عدم تطابق نسخه مود در دنیای ذخیره شده";
	}

	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>شما سعی کردید جهانی را بارگذاری کنید که با نسخه‌ی جدیدتری از ماين كرافت ایجاد شده است.</b>";
	}

	@Override
	public String nombreProblemaVersionDowngrade() {
		return "تلاش برای بارگذاری دنیایی از نسخه‌ی جدیدتر";
	}

	@Override
	public String solucionVersionDowngrade() {
		return "نسخه‌ی جدیدتری از ماين كرافت را نصب کنید.";
	}

	@Override
	public String solucionGenerarNuevoMundo() {
		return "دنیای جدیدی ایجاد کنید.";
	}

	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>افزونه '" + nombrePlugin + "' نیازمند افزونه '"
				+ dependencia + "' به عنوان وابستگی است.</b>";
	}

	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("افزونه‌های زیر دارای وابستگی‌های غیرنصب‌شده هستند: ");

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
		return "افزونه با وابستگی گمشده";
	}

	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>افزونه '" + nombrePlugin
				+ "' با نسخه فعلی سرور سازگار نیست.</b>";
	}

	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("افزونه‌های زیر با نسخه فعلی سرور سازگار نیستند: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" و ");
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
		return "افزونه با PocketMine-MP ناسازگار است";
	}

	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>افزونه '" + nombrePlugin
				+ "' در هنگام اجرا خطا داد.</b>";
	}

	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("افزونه‌های زیر در هنگام اجرا خطا دادند: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" و ");
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
		return "افزونه با خطا در اجرا";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "چندین رشته LegacyRandomSource";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>چندین رشته به کلاس LegacyRandomSource دسترسی دارند. برای کسب اطلاعات بیشتر از افزونه 'Unsafe World Random Access Detector' یا 'C2ME' استفاده کنید.</b>";
	}

	@Override
	public String desdeUltimoExito() {
		return "از آخرین موفقیت";
	}

	@Override
	public String noHayCambios() {
		return "بدون تغییر";
	}

	@Override
	public String desdeUltimoIntento() {
		return "از آخرین تلاش";
	}

	@Override
	public String fallo() {
		return "ناموفق";
	}

	@Override
	public String diferentesDeLasMods() {
		return "متفاوت با مودها";
	}

	@Override
	public String historialDeMods() {
		return "تاریخچه مودها";
	}

	@Override
	public String archivo0() {
		return "پرونده0";
	}

	@Override
	public String archivo1() {
		return "پرونده1";
	}

	@Override
	public String comparar() {
		return "مقایسه کردن";
	}

	@Override
	public String seleccionarDosArchivos() {
		return "انتخاب دو پرونده";
	}

	@Override
	public String archivoExito() {
		return "پرونده موفقیت";
	}

	@Override
	public String archivoFalla() {
		return "پرونده شکست";
	}

	@Override
	public String errorComparandoArchivos() {
		return "خطا در مقایسه پرونده‌ها";
	}

	@Override
	public String comparando() {
		return "در حال مقایسه";
	}

	@Override
	public String con() {
		return "با";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;' dir='rtl'>"
				+ "<p><b>پنل مقایسه تاریخچه مودها</b></p>"
				+ "<p>این پنل به شما اجازه می‌دهد دو لیست مود را از جلسات مختلف با هم مقایسه کنید. "
				+ "یک فایل از ستون چپ و یکی از ستون راست انتخاب کنید تا تغییرات مشخص شوند.</p>"

				+ "<h3>چگونه استفاده کنیم:</h3>" + "<ol>"
				+ "<li><b>انتخاب فایل:</b> روی دکمه‌های رادیویی کنار نام فایل‌ها کلیک کنید. "
				+ "فایل‌های با پسوند <span style='color: #4CAF50; font-weight: bold;'>.suceso</span> جلسات موفق هستند، "
				+ "در حالی که فایل‌های <span style='color: #F44336; font-weight: bold;'>.falla</span> مربوط به خطاها هستند.</li>"

				+ "<li><b>مقایسه خودکار:</b> با فشار دادن دکمه 'Compare' سیستم دو لیست انتخابی را بررسی کرده و مودهای اضافه شده (+) یا حذف شده (-) را نشان می‌دهد.</li>"

				+ "<li><b>مشاهده نتایج:</b> نتایج در قالب HTML با رنگ‌های مشخص شده نمایش داده می‌شوند: " + "<ul>"
				+ "<li><span style='color: green;'>+ مود اضافه شده</span></li>"
				+ "<li><span style='color: red;'>- مود حذف شده</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>ویژگی‌های مهم:</h3>" + "<ul>" + "<li>از هر ترکیبی از فایل‌ها (موفق/ناموفق) پشتیبانی می‌کند.</li>"
				+ "<li>تفاوت‌های دو طرفه را نمایش می‌دهد.</li>" + "<li>امکان اسکرول برای لیست‌های بلند وجود دارد.</li>"
				+ "<li>تصاویر توضیحی برای درک بهتر دارد.</li>" + "</ul>"

				+ "<p>توسعه یافته با 3>️ برای ردیابی تغییرات در تنظیمات شما.</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "احتمالاً شما با یک مشکل مربوط به IPv6 روبرو هستید. " + "دو راه‌حل وجود دارد: "
				+ "1) آرگومان JVM <code>-Djava.net.preferIPv4Stack=true</code> را به لانچر خود اضافه کنید، یا "
				+ "2) دکمه 'QuickFix' در نرم‌افزار CrashDetector را فشار دهید تا پچی اعمال شود که این تنظیم را به صورت خودکار فعال کند."
				+ "</b>";
	}

	@Override
	public String parcheIPv4() {
		return "پچ IPv4/6";
	}

	@Override
	public String carpetaHMCL() {
		return "پوشه HMCL (فقط برای HelloMineCraftLauncher)";
	}

	@Override
	public String descripcionCurseforge() {
		return "نکته: اگر گزینه «پرش از لانچر» در تنظیمات > ماين كرافت فعال باشد هیچ سجلی ایجاد نمی‌شود.";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/مشتقات: روی نسخه کلیک راست کنید و «ویرایش نسخه» را انتخاب کنید. در پنجره‌ای که باز می‌شود، به دنبال بخش «ثبت‌های ماين كرافت» یا چیزی شبیه آن باشید.<br>"
				+ "این ثبت‌ها شامل خروجی استاندارد (STDOUT) هستند که برای تشخیص خطاهای مختلف ضروری‌اند.";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher): باید پوشه‌ای که HMCL در آن نصب شده است را انتخاب کنید و پوشه «.hmcl» را انتخاب کنید. سجل‌های HMCL در اینجا ذخیره می‌شوند و حاوی اطلاعات مهم درباره خطاهای هستند.<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: لانچر یک زبانه توسعه دارد که سجل‌های جزئی را نمایش می‌دهد. می‌توانید این زبانه را در منوی تنظیمات لانچر پیدا کنید.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: نمایش سجل‌ها در یک پنجره منبسط شونده وجود دارد. دکمه‌هایی برای کپی و آپلود سجل‌ها وجود دارد. سجل‌ها به صورت خودکار هنگام اجرای بازی ایجاد می‌شوند و شامل اطلاعات ضروری برای تشخیص مشکلات هستند.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: روی نسخه کلیک راست کرده و «تنظیمات» را انتخاب کنید. سپس به بخش سجل‌ها بروید تا اطلاعات مهم خروجی استاندارد (STDOUT) را ببینید.";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "لینک‌های Markdown: هر لینکی که حاوی سجل‌ها در قالب Markdown باشد را اینجا بچسبانید. سیستم سعی خواهد کرد لینک‌های سجل (latest.log, launcher_log.txt, debug.log و غیره) را استخراج و تحلیل کند.";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "لاگ لانچر پیدا نشد";
	}

	@Override
	public String imagenNoEncontrada() {
		return "تصویر یافت نشد";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "عمومی: نوع لانچری که از آن استفاده می‌کنید را انتخاب کنید. سجل‌های لانچر (launcher_log.txt, stdout و غیره) شامل اطلاعات ضروری درباره خطاهایی هستند که در latest.log دیده نمی‌شوند. CrashDetector قادر به خواندن سجل‌های لانچر شما نیست — ممکن است لانچر شما فایل سجلی ایجاد نکند، بنابراین باید سجل‌ها را دستی کپی کنید.<br>"
				+ "برای کسب اطلاعات بیشتر، <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">این موضوع را ببینید</a>. این سجل‌ها شامل خروجی استاندارد (STDOUT) هستند که برای تشخیص بسیاری از انواع خطاها ضروری‌اند.";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>کلاس‌های گمشده از Create شناسایی شد. Create تغییرات زیادی داشته — بسیاری از کلاس‌ها حذف شده‌اند. به ویژه از Create 6 (فوریه ۲۰۲۵) به بعد، افزونه‌های مربوط به نسخه‌های قدیمی‌تر Create دیگر کار نمی‌کنند. QuickFix راه‌حلی برای این مشکل ندارد. باید افزونه‌های Create خود را به‌روز کنید، افزونه‌های منسوخ را حذف کنید یا از نسخه صحیح Create برای افزونه‌های مورد نظرتان استفاده کنید.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>کلاس‌های گمشده از EpicFight شناسایی شد. EpicFight تغییرات عمده‌ای داشته — بسیاری از کلاس‌ها حذف شده‌اند. QuickFix راه‌حلی برای این مشکل ندارد. باید افزونه‌های EpicFight خود را به‌روز کنید، افزونه‌های منسوخ را حذف کنید یا از نسخه صحیح EpicFight برای افزونه‌های مورد نظرتان استفاده کنید.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>فایل‌های ضروری Forge گم شده‌اند. فایل '" + archivo
				+ "' در نصب شما یافت نشد. این معمولاً وقتی اتفاق می‌افتد که نصب Forge قطع شده یا فایل‌های مهمی حذف شده باشند. QuickFix نمی‌تواند این فایل‌ها را خودکار بازیابی کند. باید Forge را با استفاده از نصب‌کننده رسمی دوباره نصب کنید.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge نمی‌تواند نسخه مورد نیاز ماين كرافت را پیدا کند. نسخه " + version + " نیاز است اما در فایل '"
				+ archivo
				+ "' یافت نشد. این زمانی رخ می‌دهد که بین نسخه ماين كرافت و نسخه Forge تونا سازگاری وجود داشته باشد. مطمئن شوید نسخه صحیح Forge را که با نسخه ماين كرافت شما سازگار است دانلود کرده‌اید.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>هدف 'fmlclient' مورد نیاز برای راه‌اندازی Forge یافت نشد. این نشان‌دهنده نصب ناقص یا خراب شده Forge است. احتمالاً فایل‌های ضروری Forge به درستی نصب نشده‌اند. باید Forge را با استفاده از نصب‌کننده رسمی دوباره نصب کنید.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>کلاس اصلی ماين كرافت در بارگیر کلاس‌ها یافت نشد. این معمولاً نشان‌دهنده نصب ناقص Forge یا تعارض با مودهای دیگر است. ممکن است فایل‌های ماين كرافت در حین نصب Forge خراب شده باشند. باید Forge را به درستی دوباره نصب کنید.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>نصب Forge کامل نیست. این ممکن است به دلیل قطع شدن نصب، حذف فایل‌ها یا عدم سازگاری با نسخه ماين كرافت شما باشد. Forge برای کارکرد به فایل‌های خاصی نیاز دارد و برخی از آن‌ها در نصب فعلی شما گم شده‌اند.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "نصب ناقص Forge";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>برای حل این مشکل، Forge را به درستی دوباره نصب کنید. مطمئن شوید نسخه مناسب را برای نسخه ماين كرافت خود دانلود کرده و فرآیند نصب را بدون قطع کردن انجام دهید.</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "دانلود رسمی Forge";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "چگونه Forge را به درستی دوباره نصب کنیم";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>دستورالعمل دوباره نصب Forge:</h3>" + "<ol>"
				+ "<li>نصب‌کننده صحیح Forge را از سایت رسمی دانلود کنید (نسخه توصیه‌شده برای نسخه ماين كرافت شما)</li>"
				+ "<li>کاملاً برنامه اجرایی ماين كرافت خود را ببندید</li>"
				+ "<li>نصب‌کننده Forge را با دسترسی مدیر اجرا کنید</li>"
				+ "<li>گزینه 'Installer' را انتخاب کنید (نه 'Installer (run client)')</li>"
				+ "<li>پوشه پروفایل ماين كرافت خود را در لانچر انتخاب کنید</li>"
				+ "<li>دکمه 'OK' را بزنید و صبر کنید تا نصب کامل شود</li>"
				+ "<li>لانچر را دوباره راه‌اندازی کنید و مطمئن شوید Forge در لیست پروفایل‌ها ظاهر شده است</li>"
				+ "</ol>"
				+ "<p><b>نکته مهم:</b> اگر از لانچر سفارشی استفاده می‌کنید، مطمئن شوید پوشه صحیح پروفایل را انتخاب کرده‌اید.</p>"
				+ "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "دستورالعمل دوباره نصب Forge";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>تداخل شناسه‌ها (ID): شناسه <strong>" + id
				+ "</strong> قبلاً توسط <strong>" + modOrigen + "</strong> اشغال شده و هنگام اضافه کردن <strong>"
				+ modDestino
				+ "</strong> با مشکل مواجه می‌شود. این زمانی رخ می‌دهد که دو مود سعی کنند از یک شناسه برای عناصر مختلف استفاده کنند.</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>بیش از حد مجاز شناسه‌ها فراتر رفته است. این زمانی اتفاق می‌افتد که مودها سعی کنند بلوک‌ها یا آیتم‌ها را با شناسه‌هایی ثبت کنند که خارج از محدوده مجاز نسخه ماين كرافت شما باشند.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "تداخل شناسه‌ها";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>برای حل این مشکل در ماين كرافت 1.12.2، <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a> را نصب کنید. برای 1.7.10، از <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a> استفاده کنید.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>از ابزارهایی مانند <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> یا <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> برای حل تداخل شناسه‌ها استفاده کنید.</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "نصب JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "نصب EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "استفاده از IdFix Minus یا IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "استفاده از Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "مشاهده مستندات ژاپنی";
	}

	@Override
	public String escanearDeMCreator() {
		return "اسکن با MCreator";
	}

	@Override
	public String tituloArbolDeMods() {
		return "درخت ماژول‌ها و کلاس‌ها";
	}

	@Override
	public String tipoBusqueda() {
		return "نوع";
	}

	@Override
	public String filtroTodos() {
		return "همه";
	}

	@Override
	public String filtroPaquetes() {
		return "بسته‌ها";
	}

	@Override
	public String filtroClases() {
		return "کلاس‌ها";
	}

	@Override
	public String filtroMetodos() {
		return "روش‌ها";
	}

	@Override
	public String filtroCampos() {
		return "فیلدها";
	}

	@Override
	public String filtroReferenciasCampo() {
		return "ارجاعات فیلد";
	}

	@Override
	public String filtroReferenciasMetodo() {
		return "ارجاعات روش";
	}

	@Override
	public String filtroReferenciasClase() {
		return "ارجاعات کلاس";
	}

	@Override
	public String tipBuscar() {
		return "اینجا بنویسید تا در درخت مودها جستجو کنید";
	}

	@Override
	public String botonBuscar() {
		return "جستجو";
	}

	@Override
	public String botonResetearArbol() {
		return "بازنشانی درخت";
	}

	@Override
	public String modsCargados() {
		return "مودهای بارگذاری‌شده";
	}

	@Override
	public String clases() {
		return "کلاس‌ها";
	}

	@Override
	public String metodos() {
		return "روش‌ها";
	}

	@Override
	public String campos() {
		return "فیلدها";
	}

	@Override
	public String referencias() {
		return "ارجاعات";
	}

	@Override
	public String resultadosBusqueda() {
		return "نتایج جستجو";
	}

	@Override
	public String buscarReferencias() {
		return "یافتن ارجاعات";
	}

	@Override
	public String referenciasMod() {
		return "ارجاعات مود";
	}

	@Override
	public String referenciasClase() {
		return "ارجاعات کلاس";
	}

	@Override
	public String referenciasMetodo() {
		return "ارجاعات روش";
	}

	@Override
	public String referenciasCampo() {
		return "ارجاعات فیلد";
	}

	@Override
	public String noSeEncontraronReferencias() {
		return "ارجاعی یافت نشد";
	}

	@Override
	public String detalleMod() {
		return "جزئیات مود:";
	}

	@Override
	public String ubicacion() {
		return "مکان";
	}

	@Override
	public String nombres() {
		return "نام‌ها";
	}

	@Override
	public String modulo() {
		return "ماژول";
	}

	@Override
	public String detalleClase() {
		return "جزئیات کلاس:";
	}

	@Override
	public String detalleMetodo() {
		return "جزئیات روش:";
	}

	@Override
	public String detalleCampo() {
		return "جزئیات فیلد:";
	}

	@Override
	public String clase() {
		return "کلاس";
	}

	@Override
	public String tipo() {
		return "نوع";
	}

	@Override
	public String referenciasAMetodos() {
		return "ارجاعات به روش‌ها:";
	}

	@Override
	public String referenciasACampos() {
		return "ارجاعات به فیلدها:";
	}

	@Override
	public String arbolDeMods() {
		return "درخت مودها";
	}

	@Override
	public String metodo() {
		return "روش";
	}

	@Override
	public String campo() {
		return "فیلد";
	}

	@Override
	public String descompilar() {
		return "دی‌کامپایل";
	}

	@Override
	public String exportar() {
		return "صدور";
	}

	@Override
	public String importar() {
		return "واردات";
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
		return "در حال خروجی‌گیری";
	}

	@Override
	public String exportacionTardara() {
		return "خروجی‌گیری ممکن است طول بکشد";
	}

	@Override
	public String porFavorEspere() {
		return "لطفاً صبر کنید";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>فایل‌های اجرایی VLC را ندارید. WaterMedia به فایل‌های اجرایی VLC نیاز دارد. باید آن‌ها را به صورت دستی از https://www.videolan.org/vlc/ نصب کنید.  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "دریافت VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>خطای بحرانی: نام ماژول '" + nombreModulo
				+ "' شامل کاراکترهای نامعتبر است. بخش '" + parteInvalida
				+ "' یک شناسهٔ معتبر جاوا نیست. این زمانی اتفاق می‌افتد که یک مود از کلمات رزرو شده جاوا (مثل 'true', 'class') یا کاراکترهای غیرمجاز در نام خود استفاده کند.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "کاراکترهای نامعتبر در نام مود";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "نام مود '" + nombreModulo + "' نامعتبر است زیرا شامل '" + parteInvalida
				+ "' است که یک کلمهٔ رزرو شده جاوا یا کاراکتر غیرمجاز است. "
				+ "در لاگ‌ها جستجو کنید تا ببینید کدام مود با این نام مطابقت دارد (معمولاً نام فایل JAR)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "به پوشه مود بروید و فایل <b>mods.toml</b> را در پوشه <b>/META-INF/</b> ویرایش کنید. "
				+ "مقدار <b>modId</b> را تغییر دهید تا فقط از حروف، اعداد و زیرخط استفاده کند و از کلمات رزرو شده جاوا دوری کند";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "مثالی از نام معتبر: 'truemod_shot_enchantment' به جای 'true.shot.enchantment'. "
				+ "به یاد داشته باشید که نام‌های مود نمی‌توانند شامل نقطه، خط تیره یا کلمات رزرو شده جاوا مثل 'true', 'false' یا 'class' باشند";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>خطای بحرانی در مود: '" + nombreJar
				+ "'. فیلد اجباری 'mandatory' در وابستگی‌های آن وجود ندارد. این زمانی اتفاق می‌افتد که فایل mods.toml تعیین نکند که وابستگی اجباری است یا نه.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "وابستگی مود با فیلد اجباری گمشده";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "مود مشکل‌دار این است: <b>" + nombreJar + "</b>. این فایل در تنظیمات وابستگی‌ها مشکل دارد";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "فایل <b>mods.toml</b> را در پوشه <b>/META-INF/</b> مود <b>" + nombreJar + "</b> باز کنید";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "در بخش وابستگی‌ها، مطمئن شوید هر ورودی شامل <b>mandatory=true</b> یا <b>mandatory=false</b> باشد (مثال: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>خطای بحرانی در مود: '" + nombreJar
				+ "'. پیکربندی نامعتبر access transformer. این زمانی اتفاق می‌افتد که فایل پیکربندی دارای نحو نادرست یا ارجاع به کلاس‌ها/روش‌های غیرموجود باشد.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "access transformer نامعتبر";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "مود مشکل‌دار: <b>" + nombreJar + "</b>. این مود حاوی پیکربندی نامعتبر access transformer است";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "فایل <b>accessTransformer.cfg</b> را درون مود <b>" + nombreJar
				+ "</b> باز کنید (معمولاً در پوشه اصلی فایل JAR)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "نحو access transformer را اصلاح کنید. خطوط باید از فرمت زیر پیروی کنند: <b>access class.method</b> (مثال: public net.minecraft.world.entity.Entity.func_200560_a). خطوطی را که به کلاس‌ها یا روش‌هایی ارجاع می‌دهند که در نسخه ماين كرافت شما وجود ندارند، حذف کنید";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>خطای بحرانی: عدم تطابق بین شناسه مود در توضیح @Mod و فایل mods.toml. مود '" + nombreMod
				+ "' نمی‌تواند بارگذاری شود زیرا شناسه‌ها مطابقت ندارند.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "عدم تطابق بین @Mod و mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "مود در حال توسعه '" + nombreMod
				+ "' دارای عدم تطابق بین شناسه در توضیح <b>@Mod</b> و مقدار در <b>mods.toml</b> است";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "مطمئن شوید شناسه در کلاس اصلی شما با مقدار <b>modId</b> در فایل <b>/META-INF/mods.toml</b> مطابقت داشته باشد. مثال: <b>@Mod(\"mymod\")</b> باید با <b>modId=\"mymod\"</b> مطابقت داشته باشد";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "اگر از Gradle استفاده می‌کنید، پس از تغییرات دستور <b>clean</b> را اجرا کنید تا اطمینان حاصل کنید منابع به درستی به‌روز شده‌اند. گاهی اوقات فایل‌های قدیمی در پوشه build باقی می‌مانند";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "کلاینت" : "سرور";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "سرور" : "کلاینت";

		return "<b style='color:#" + config.obtenerColorError() + "'>خطای بحرانی: در حال حاضر تلاش برای بارگیری کلاس '"
				+ nombreClase + "' در محیط " + plataforma + " است، اما برای " + plataformaOpuesta + " طراحی شده است. "
				+ "<b>از ویژگی 'درخت مودها' در نوار کناری برای یافتن مودی که سعی در بارگیری این کلاس دارد استفاده کنید</b>. "
				+ "مودها به طور خاص برای یک پلتفرم ساخته شده‌اند و در دیگری کار نمی‌کنند.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "مود در پلتفرم نادرست";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "در تب <b>درخت مودها</b> (سمت راست)، به دنبال مراجع به کلاس <b>" + nombreClase
				+ "</b> بگردید تا مود مسبب مشکل را شناسایی کنید";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "کلاینت" : "سرور";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "سرور" : "کلاینت";

		return "مود شناسایی‌شده یک مود <b>" + plataformaOpuesta + "</b> است و نباید در محیط " + plataforma
				+ " استفاده شود.";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "مود مشکل‌دار را از پوشه <b>مودها</b> حذف کنید. اگر به عملکرد مشابهی در این پلتفرم نیاز دارید، "
				+ "مود جایگزینی را جستجو کنید که به طور خاص برای <b>کلاینت</b> یا <b>سرور</b> طراحی شده باشد";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("خطای بحرانی: متادیتا برای modid '").append(modIdFaltante).append("' یافت نشد. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("ممکن است مودهای زیر مشکل را ایجاد کنند: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", و دیگران...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"این زمانی اتفاق می‌افتد که مودی به مود دیگری که نصب نشده یا فایل mods.toml آن اشتباه است، وابسته باشد.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "متادیتای mods.toml گمشده";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("مودهای زیر به '").append(modIdFaltante).append("' وابسته‌اند: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", و دیگران...");
			paso.append("</b>. از ویژگی <b>درخت مودها</b> برای تأیید مود مسبب مشکل استفاده کنید");
			return paso.toString();
		}
		return "مودی در حال تلاش برای وابستگی به '" + modIdFaltante
				+ "' است، اما این مود نصب نشده است. از ویژگی <b>درخت مودها</b> برای شناسایی مود مسبب استفاده کنید";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "دو گزینه دارید:<br/>" + "1. <b>نصب مود گمشده</b>: مود با شناسه '" + modIdFaltante
				+ "' را پیدا و نصب کنید<br/>"
				+ "2. <b>حذف مود وابسته</b>: اگر به این ویژگی نیاز ندارید، مود وابسته به '" + modIdFaltante
				+ "' را حذف کنید";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "اگر مود '" + modIdFaltante + "' یک کتابخانه باشد (مثل 'forge', 'minecraft', 'curios')، "
				+ "مطمئن شوید نسخه‌های صحیح ماين كرافت و Forge را دارید. "
				+ "اگر مود معمولی است، در صفحه دانلود آن، پیش‌نیازهای لازم را بررسی کنید";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>هشدار: خطای راه‌اندازی سیستم صدا. صداها و موسیقی غیرفعال شده‌اند. این خطا معمولاً با مود SoundPhysicsMod مرتبط است و ممکن است به دلیل تداخل با کتابخانه‌های صوتی دیگر رخ داده باشد.</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "خطای سیستم صدا";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "این خطا معمولاً به <b>SoundPhysicsMod</b> مربوط می‌شود. بررسی کنید که آخرین نسخه سازگار با نسخه ماين كرافت خود را نصب کرده باشید";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "اگر از مودهای صوتی دیگری (مثل Sound Filters، Dynamic Surroundings و غیره) استفاده می‌کنید، موقتاً SoundPhysicsMod را حذف کنید تا ببینید آیا تعارض رفع می‌شود یا نه";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "پوشه <b>logs</b> را بررسی کنید تا پیام‌های بیشتری درباره LWJGL یا OpenAL پیدا کنید که ممکن است نشان‌دهنده مشکل در کتابخانه‌های صوتی پایه باشند";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("خطای بحرانی: کلاس '").append(nombreClase)
				.append("' به عنوان گیرنده رویداد ثبت شده اما شامل روش معتبری نیست. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("این کلاس در مودهای زیر وجود دارد: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", و دیگران...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"این زمانی اتفاق می‌افتد که یک کلاس برای دریافت رویدادها ثبت شود اما روشی با برچسب @SubscribeEvent نداشته باشد.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "کلاس ثبت‌شده بدون گیرنده رویداد";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("کلاس مشکل‌دار در این مودها وجود دارد: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", و دیگران...");
			paso.append("</b>. این مودها در حال تلاش برای ثبت رویداد بدون روش معتبر هستند");
			return paso.toString();
		}
		return "کلاس <b>" + nombreClase
				+ "</b> برای دریافت رویداد ثبت شده اما شامل روشی با برچسب <b>@SubscribeEvent</b> نیست. از ویژگی <b>درخت مودها</b> برای شناسایی مود حاوی این کلاس استفاده کنید";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "در کد منبع، مطمئن شوید کلاس <b>" + nombreClase + "</b> حداقل یک روش با فرمت زیر داشته باشد: "
				+ "<b>@SubscribeEvent public void نام_روش(رویداد_خاص رویداد) { ... }</b>. "
				+ "اگر کلاس داخلی است، مطمئن شوید استاتیک نباشد";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("برای مودهای شناسایی‌شده (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", و غیره.");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("با توسعه‌دهنده این مود تماس بگیرید تا مشکل را رفع کند. ");
			} else {
				paso.append("با توسعه‌دهندگان این مودها تماس بگیرید تا مشکل را رفع کنند. ");
			}
		}

		paso.append(
				"اگر خود شما توسعه‌دهنده هستید، ثبت این کلاس در EventBus را حذف کنید یا روش‌های معتبر @SubscribeEvent اضافه کنید");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>خطای بحرانی: خطایی از نوع 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' هنگام پردازش فایل '"
				+ nombreArchivo
				+ "' رخ داده است. این خطا به این معنی است که برنامهٔ راه‌انداز به درستی فایل‌های مودپک را دانلود یا استخراج نکرده است. "
				+ "پیام 'zip END header not found' نشان می‌دهد فایل JAR ناقص یا خراب است که در راه‌اندازهایی که دانلود فایل‌های بزرگ را به خوبی مدیریت نمی‌کنند بسیار رایج است. "
				+ "این مشکل عمدتاً کاربران Twitch/CurseForge، Technic Launcher و به ویژه کاربران Luna Pixel را تحت تأثیر قرار می‌دهد، زیرا این راه‌اندازها اغلب در تأیید کامل صحت فایل‌های دانلود شده شکست می‌خورند. "
				+ "کاربران Luna Pixel باید ATLauncher را به عنوان گزینه‌ای پایدارتر در نظر بگیرند، زیرا این برنامه بهتر با صحت فایل‌ها کار می‌کند و از این خطا جلوگیری می‌کند. "
				+ "سیستم نمی‌تواند مودها را بارگذاری کند زیرا فرمت ZIP آسیب دیده است و از خواندن منابع لازم توسط Forge برای شروع بازی جلوگیری می‌کند.</b>";
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "خطای UnionFileSystem - فایل خراب";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		return "کاملاً مودپک را از ابتدا نصب مجدد کنید";
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "اگر از Luna Pixel استفاده می‌کنید، به ATLauncher تغییر دهید";
	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "قبل از نصب مجدد، اتصال اینترنت و فضای دیسک خود را بررسی کنید";
	}

	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "آیا ProxySysOutSysErr فعال شود؟\n\n"
				+ "این گزینه به CrashDetector اجازه دسترسی به System.out و System.err را می‌دهد زمانی که راه‌انداز لاگی ارائه نمی‌دهد.\n\n"
				+ "فقط در صورتی باید فعال شود که نتوانید به‌صورت دستی یک لاگ را جایگذاری کنید.\n\n"
				+ "هشدار: این ممکن است با برخی مودها یا راه‌اندازها تداخل داشته باشد.\n\n"
				+ "برای اعمال تغییرات، بازآغاز مجدد بازی/برنامه ضروری است.";
	}

	@Override
	public String confirmacionTitulo() {
		return "تایید";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr با موفقیت فعال شد.\n\n"
				+ "برای اعمال تغییرات، CrashDetector باید مجدداً راه‌اندازی شود.";
	}

	@Override
	public String informacionTitulo() {
		return "اطلاعات";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("خطای بحرانی: AzureLib و GeckoLib خیلی زود راه‌اندازی شدند! ");
		} else if (azureLibError) {
			mensaje.append("خطای بحرانی: AzureLib خیلی زود راه‌اندازی شد! ");
		} else if (geckoLibError) {
			mensaje.append("خطای بحرانی: GeckoLib خیلی زود راه‌اندازی شد! ");
		}

		mensaje.append(
				"این خطا زمانی اتفاق می‌افتد که سعی کنید از مودهای Fabric با نسخه‌های غیر-Fabric این کتابخانه‌ها استفاده کنید. ");

		if (connectorPresente) {
			mensaje.append(
					"یک مود سازگاری (Sinytra Connector یا specialcompatibilityoperation) شناسایی شد که نشان می‌دهد در حال استفاده از مودهای Fabric در محیط Forge یا FeatureCreep هستید. ");
			mensaje.append("خطای 'خطای راه‌اندازی FabricMC' را در لاگ‌ها بررسی کنید تا مود مشکل‌دار را پیدا کنید. ");
		}

		mensaje.append(
				"AzureLib و GeckoLib کتابخانه‌های ضروری برای مودهای انیمیشن هستند، اما باید با پلتفرم صحیح (Fabric یا Forge) مطابقت داشته باشند. ");
		mensaje.append("بازی نمی‌تواند به درستی مودهای انیمیشن را بارگذاری کند به دلیل این تعارض در راه‌اندازی.");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "کتابخانه خیلی زود راه‌اندازی شد";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "خطای 'خطای راه‌اندازی FabricMC' را در لاگ‌ها بررسی کنید تا مود مشکل‌دار را پیدا کنید";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "مطمئن شوید از نسخه صحیح AzureLib/GeckoLib برای پلتفرم خود (Forge یا Fabric) استفاده می‌کنید";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError() + "'>خطای بحرانی: عدم سازگاری بین C2ME و مودهای اتصال. "
				+ "این خطا زمانی اتفاق می‌افتد که C2ME سعی کند به مؤلفه‌های داخلی جاوا دسترسی پیدا کند که در محیط‌های دارای "
				+ "Sinytra Connector یا specialcompatibilityoperation یا سایر مودهای سازگاری Fabric/Forge محدود شده‌اند. "
				+ "<b>C2ME با این محیط‌ها سازگار نیست، اما <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> جایگزین پیشنهادی است</b> که به درستی "
				+ "با مودهای اتصال کار می‌کند. بازی به دلیل تعارض مجوزهای امنیتی جاوا نمی‌تواند شروع شود.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "عدم سازگاری C2ME با مودهای اتصال";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "C2ME را از پوشه مودها حذف کنید";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "به جای آن، <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> را دانلود و نصب کنید (سازگار با Sinytra Connector)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "مطمئن شوید تمام مودهای اتصال (مثل Sinytra Connector) به آخرین نسخه به‌روزرسانی شده‌اند";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError() + "'>خطای بحرانی: بارگذاری افزونه JEI برای مود '"
				+ modId + "' ناموفق بود. کلاس '" + nombreClase + "' (شناسه افزونه: '" + pluginId
				+ "') باعث خطایی شد که منجر به کرش شدن بازی در هنگام راه‌اندازی می‌شود. "
				+ "این مشکل زمانی رخ می‌دهد که یک مود ادغام JEI ناسازگار یا خراب داشته باشد که مانع از راه‌اندازی بازی شود.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "افزونه JEI شکست خورد - باعث کرش می‌شود";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "مود <b>" + modId
				+ "</b> حاوی یک افزونه JEI معیوب است که باعث کرش شدن شده است. از قابلیت <b>درخت مودها</b> برای تشخیص مود عامل مشکل استفاده کنید";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "مود <b>" + modId + "</b> را به‌طور موقت از پوشه مودها حذف کنید تا بررسی کنید آیا مشکل کرش حل می‌شود";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "برای مود <b>" + modId
				+ "</b> به دنبال به‌روزرسانی باشید یا مشکل افزونه JEI را به توسعه‌دهنده گزارش دهید. "
				+ "در همین حال، برای اجرای بازی باید این مود حذف شود";
	}

	@Override
	public String tituloLectador() {
		return "خواننده لاگ‌ها - تشخیص‌دهنده کرش";
	}

	@Override
	public String obtenerTituloLeyenda() {
		return "راهنمای رنگ‌ها";
	}

	@Override
	public String obtenerErrorEnLeyenda() {
		return "خطاهای بحرانی";
	}

	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "ردپای پشته";
	}

	@Override
	public String obtenerTituloError() {
		return "خطا";
	}

	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "هنگام پردازش خط انتخاب‌شده خطایی رخ داد";
	}

	@Override
	public String obtenerNombreError() {
		return "نام خطا";
	}

	@Override
	public String obtenerDescripcionError() {
		return "شرح دقیق";
	}

	@Override
	public String obtenerSeleccionarConsola() {
		return "انتخاب لاگ";
	}

	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "خطای ناشناس";
	}

	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "خطای بحرانی در لاگ‌ها شناسایی شد. " + "برای یافتن علت اصلی، ردپای پشته را بررسی کنید.";
	}

	@Override
	public String obtenerErrorLecturaArchivo() {
		return "نمی‌توان فایل لاگ را خواند. " + "مطمئن شوید فایل وجود دارد و دسترسی خواندن دارد.";
	}

	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "تحلیل‌گر لاگ";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("خطای بحرانی: ثبت مشترکان خودکار برای مود '").append(modId).append("' ناموفق بود. ");

		mensaje.append("کلاس مشکل‌دار: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("این کلاس در این فایل‌ها قرار دارد: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", و دیگران...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"این خطا زمانی رخ می‌دهد که مودی سعی کند کلاسی را به‌عنوان گیرنده رویداد به‌صورت خودکار ثبت کند، اما کلاس بارگذاری نشود. ");
		mensaje.append("<b>خطاهای دیگر در لاگ را بررسی کنید، زیرا علت اصلی ممکن است بارگیری ناموفق قبلی باشد</b>.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "ثبت مشترکان خودکار ناموفق";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "مود <b>" + modId + "</b> در حال ثبت کلاس <b>" + nombreClase
				+ "</b> به‌عنوان گیرنده خودکار است، اما شکست خورده است. مطمئن شوید این کلاس وجود دارد و قابل دسترسی است";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("کلاس مشکل‌دار <b>" + nombreClase + "</b> در این فایل‌ها است: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", و دیگران...");
			paso.append("</b>. ");
			paso.append("از ویژگی <b>درخت مودها</b> برای تشخیص فایل دقیق استفاده کنید");
			return paso.toString();
		}
		return "کلاس <b>" + nombreClase + "</b> در هیچ فایل مودی پیدا نشد. مطمئن شوید مود <b>" + modId
				+ "</b> به‌درستی نصب شده است. از ویژگی <b>درخت مودها</b> برای شناسایی مشکل استفاده کنید";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "مود <b>" + modId + "</b> را به آخرین نسخه سازگار با نسخه ماين كرافت و Forge خود به‌روزرسانی کنید. "
				+ "اگر مشکل باقی ماند، با توسعه‌دهنده مود تماس بگیرید و خطا را همراه با کلاس مشکل‌دار گزارش دهید";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "خطاهای <b>دیگر در لاگ</b> قبل از این پیام را بررسی کنید، زیرا مشکل واقعی ممکن است در بارگیری قبلی باشد. "
				+ "گاهی اوقات یک خطا قبلی بارگیری کلاس‌های لازم برای ثبت رویداد را مسدود می‌کند";
	}

	@Override
	public String limpiado() {
		// TODO Auto-generated method stub
		return "پاک‌شده";
	}

	@Override
	public String original() {
		// TODO Auto-generated method stub
		return "اصلی";
	}

	@Override
	public String verEnConsola() {
		// TODO Auto-generated method stub
		return "مشاهده در لاگ";
	}

	@Override
	public String advertencia() {
		// TODO Auto-generated method stub
		return "هشدار";
	}

	@Override
	public String fatal() {
		// TODO Auto-generated method stub
		return "کشنده";
	}

	@Override
	public String noRegistroDeBattly() {
		// TODO Auto-generated method stub
		return "BattlyLauncher لاگ یا کنسولی برای کپی کردن ندارد. می‌توانید از ProxySysOutSysErr برای درنگ کردن STDOUT و STDERR و ری‌استارت بازی استفاده کنید، اما ممکن است ProxySysOutSysErr با مودهایی که STDOUT یا STDERR را تغییر می‌دهند تداخل داشته باشد";
	}

	@Override
	public String noRegistroDeNightWorld() {
		// TODO Auto-generated method stub
		return "برای دریافت لاگ راه‌انداز، باید حالت دیباگ را در تنظیمات NightWorld فعال کنید. این موضوع بسیار مهم است، به‌ویژه چون شامل STDOUT و STDERR می‌شود";
	}

	@Override
	public String noRegistroDeMCServidor() {
		// TODO Auto-generated method stub
		return "باید محتوای ترمینال سرور خود را ذخیره یا جایگذاری کنید، زیرا حاوی اطلاعاتی است که در سایر لاگ‌ها وجود ندارد، از جمله STDOUT، STDERR و سایر خطاها. لطفاً محتوای آخرین جلسه را جایگذاری کنید. برای آینده، می‌توانید خروجی ترمینال را به فایلی به نام cd_launcherlog ذخیره کنید. برای جلوگیری از نیاز به جایگذاری، پس از دستور راه‌اندازی عبارت >> cd_launcherlog را اضافه کنید (همانطور که در تصویر نشان داده شده). توجه داشته باشید که این کار باعث می‌شود خروجی در ترمینال نمایش داده نشود و فقط در آن فایل ثبت شود.";
	}

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("خطای بحرانی: ترانسفورمر LexForge در محیط NeoForge شناسایی شد. ");
		sb.append("</b>");

		sb.append("کلاس درگیر: <b>").append(claseReceptora).append("</b>. ");
		sb.append("رابط متاثر <b>").append(interfazObjetivo).append("</b> است و ");
		sb.append("متد گمشده <b>").append(firmaMetodoFaltante).append("</b> می‌باشد. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("این کلاس در این مکان‌ها یافت شد: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", و دیگران...");
			sb.append("</b>. ");
		} else {
			sb.append(
					"هیچ JARی حاوی این کلاس پیدا نشد؛ ممکن است سایه‌زنی شده باشد یا به صورت jar-in-jar تعبیه شده باشد. ");
		}

		sb.append(
				"این خطا زمانی رخ می‌دهد که یک ترانسفورمر/سرویس ModLauncher که برای MinecraftForge/LexForge کامپایل شده، ");
		sb.append("در NeoForge با نسخه‌ای ناسازگار از API ModLauncher بارگذاری شود. ");
		sb.append("برای NeoForge، این جزء را به‌روزرسانی یا جایگزین کنید.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "ترانسفورمر LexForge در NeoForge استفاده شده";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "ترانسفورمر ناسازگار را شناسایی کنید: <b>" + claseReceptora + "</b>. " + "API مورد انتظار <b>"
				+ interfazObjetivo + "</b> است و متد گمشده <b>" + firmaMetodoFaltante + "</b> می‌باشد. "
				+ "بررسی کنید آیا مود این کلاس را در <b>META-INF/services</b> ثبت کرده است و آن را در NeoForge حذف یا غیرفعال کنید.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("مکان مود(های) درگیر: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", و دیگران...");
			sb.append("</b>. ");
		} else {
			sb.append("هیچ JARی حاوی این کلاس یافت نشد. jar-in-jar و وابستگی‌های سایه‌زنی شده را بررسی کنید. ");
		}
		sb.append(
				"موقتاً این JARها را حذف کنید یا از نسخه‌های سازگار با NeoForge استفاده کنید تا منشأ مشکل را تأیید کنید.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "این جزء را با نسخه خاص NeoForge جایگزین کنید یا آن را نسبت به نسخه ModLauncher مورد استفاده توسط NeoForge "
				+ "دوباره کامپایل کنید. از فایل‌های باینری قدیمی LexForge/MinecraftForge خودداری کنید.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "پوشه mods را پاکسازی کنید و موارد تکراری jar-in-jar را حذف کنید. در صورت نیاز کش راه‌انداز را پاک کنید "
				+ "و مجدداً راه‌اندازی کنید تا مطمئن شوید هیچ ترانسفورمر LexForgeای بارگذاری نمی‌شود.";
	}

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia نمی‌تواند راه‌اندازی شود: Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("ناسازگار است.</b> ");
		sb.append("Xenon را حذف کنید و به جای آن از Embeddium یا Sodium استفاده کنید. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("در این مسیرها پیدا شد: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", و دیگران...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "نا سازگاری WaterMedia با Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return label + " با WaterMedia ناسازگار است. آن را از پروفایل خود حذف کنید.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("مسیرها: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", و دیگران...");
			sb.append("</b>. این فایل JAR را حذف کنید.");
			return sb.toString();
		}
		return "هیچ فایل JARی پیدا نشد. پوشه mods را بررسی کرده و Xenon را حذف کنید.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "به عنوان جایگزین، Embeddium یا Sodium را نصب کرده و بازی را مجدداً راه‌اندازی کنید.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "خطای فشرده‌سازی (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>Deflater در حین کپی منابع TACZ بسته شد.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("مرتبط با: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", و دیگران");
			sb.append("</b>. ");
		}
		sb.append(
				"<br/><b>راه‌حل:</b> در <code>tacz/tacz-pre.toml</code> مقدار <code>DefaultPackDebug=true</code> را تنظیم کنید. ")
				.append("در صورت نیاز، ابتدا یک نقشه ایجاد کرده و سپس آن را فعال کنید.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "در فایل tacz/tacz-pre.toml مقدار DefaultPackDebug=true را تنظیم کنید. در صورت نیاز، ابتدا نقشه‌ای ایجاد کرده و سپس آن را فعال کنید.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "توابع چگالی متصل‌نشده";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>توابع چگالی در رجیستر گم شده‌اند.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("گم شده: ");
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
				"<br/><b>راه‌حل:</b> مود یا دیتاپکی که این توابع را تعریف می‌کند را نصب یا فعال کنید و مجدداً راه‌اندازی کنید.");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "مود یا دیتاپکی که این توابع را فراهم می‌کند را نصب یا فعال کرده و بازی را مجدداً راه‌اندازی کنید.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// پیام کوتاه با رنگ خطا، به‌طور صریح نام مود ذکر شده است
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ورودی رجیستر وجود ندارد: ").append(claveFaltante).append(". ");
		sb.append("معمولاً در نسخه آلفای Steam & Railways برای Create 6 اتفاق می‌افتد.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (آلفا)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "نسخه آلفای Steam & Railways برای Create 6 را حذف یا با نسخه سازگار جایگزین کنید.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// کوتاه، با رنگ خطا و پیشنهاد مستقیم
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("تضاد بارگذاری: استفاده همزمان Multiworld با Sodium/Embeddium/Rubidium باعث ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance) می‌شود. ")
				.append("پیشنهاد: Multiworld یا مود بهینه‌سازی عملکرد را حذف کنید، یا از نسخه‌های سازگار استفاده کنید.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "تضاد: Multiworld با مودهای عملکردی";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Multiworld یا Sodium/Embeddium/Rubidium را حذف کنید، یا به نسخه‌های سازگار به‌روزرسانی کنید.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Sodium درایور گرافیکی ناسازگاری را تشخیص داده است. "
				+ "درایور GPU خود را به حداقل نسخه مورد نیاز به‌روزرسانی کنید یا راهنمای Sodium را دنبال کنید."
				+ "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "خطای متن OpenGL";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OpenGL شکست خورد: هیچ متن فعلی وجود ندارد یا تابع در این متن در دسترس نیست. "
				+ "همچنین ممکن است مشکلی در درایورهای ویدئویی باشد." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "درایورهای GPU خود را به‌روزرسانی یا دوباره نصب کنید و مجدداً راه‌اندازی کنید؛ رویهم‌گذاری‌ها را غیرفعال کرده و بدون مودهای عملکردی امتحان کنید.";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "لینک در حافظه موقت کپی شد.";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		return "جستجو داخل بایگانی‌ها (.zip/.jar/.war/.ear/.fpm/.rar برای Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطای وضوح بافت: نمی‌توان " + recurso
				+ " را بارگذاری کرد - اندازه: " + tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "خطای وضوح بافت";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "این خطا زمانی رخ می‌دهد که بافت‌ها خیلی بزرگ باشند یا تعداد زیادی پک منابع وجود داشته باشد. "
				+ "سعی کنید از پک‌های منابع با وضوح پایین‌تر استفاده کنید یا برخی از پک‌ها را حذف کنید. "
				+ "بررسی کنید که آیا بافت سفارشی با وضوح بالاتر از حد مجاز اضافه نکرده‌اید.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطای سرویس‌های ModLauncher: مسیر شامل کاراکترهای نامعتبر است. "
				+ "سرویس‌های ModLauncher نمی‌توانند مسیرهایی که شامل کاراکترهای غیر ASCII یا کاراکترهای خاص باشند را پردازش کنند. "
				+ "کاراکترهای مشکل‌ساز شامل: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요 و به‌ویژه کاراکتر '\"' در صورتی که در انتهای نام باشد، می‌شود. "
				+ "مولفه‌های رایج سرویس در ModLauncher شامل CrashDetector، "
				+ Config.obtenerInstancia().obtenerNombreCD()
				+ "، FeatureCreep، Vivicraft، Optifine، Sodium، clonos، Iris Shaders/Oculus، MixerLogger، CrashAssistant و Sintrya Connector هستند. "
				+ "می‌توانید تمام سرویس‌ها را حذف کنید، اما ممکن است به دلیل نام مسیر، مشکلات دیگری پیش بیاید. "
				+ "راه‌حل: نام مجموعه را فقط با کاراکترهای ASCII (a-z, A-Z, 0-9) تغییر دهید و از فاصله و کاراکترهای خاص پرهیز کنید.</b>";
	}

	@Override
	public String nombre_error_modlauncher_path() {
		return "خطای مسیر ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "این خطا زمانی رخ می‌دهد که مسیر مجموعه شامل کاراکترهای غیر ASCII یا کاراکترهای خاص باشد. "
				+ "سرویس‌های ModLauncher نمی‌توانند این مسیرها را مدیریت کنند. "
				+ "راه‌حل: نام مجموعه را به گونه‌ای تغییر دهید که فقط از کاراکترهای ASCII (a-z, A-Z, 0-9) استفاده شود و از فاصله و کاراکترهای خاص اجتناب کنید. "
				+ "به کاراکتر '\"' توجه ویژه‌ای داشته باشید که بسیار مشکل‌ساز است، به‌ویژه وقتی در انتهای نام قرار دارد.";
	}

	@Override
	public String tituloEditorCodice() {
		return "ویرایشگر کدیس";
	}

	@Override
	public String nuevo() {
		return "جدید";
	}

	@Override
	public String actualizarSeleccionado() {
		return "به‌روزرسانی مورد انتخاب شده";
	}

	@Override
	public String eliminarSeleccionado() {
		return "حذف مورد انتخاب شده";
	}

	@Override
	public String exportarJSON() {
		return "صدور JSON...";
	}

	@Override
	public String guardarTodo() {
		return "ذخیره همه";
	}

	@Override
	public String general() {
		return "عمومی";
	}

	@Override
	public String id() {
		return "شناسه";
	}

	@Override
	public String paraBuscar() {
		return "متن جستجو";
	}

	@Override
	public String filtro() {
		return "فیلتر (شناسه)";
	}

	@Override
	public String criticalidad() {
		return "سطح اهمیت (هشدار/خطا/کشنده)";
	}

	@Override
	public String prioridad() {
		return "اولویت";
	}

	@Override
	public String lista() {
		return "بررسی‌ها";
	}

	@Override
	public String colIdioma() {
		return "زبان";
	}

	@Override
	public String colNombre() {
		return "نام";
	}

	@Override
	public String colResultado() {
		return "نتیجه";
	}

	@Override
	public String vistaJson() {
		return "پیش‌نمایش JSON";
	}

	@Override
	public String idiomas() {
		return "زبان‌ها (همه الزامی)";
	}

	@Override
	public String elegirFiltro() {
		return "انتخاب...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "یک فیلتر را انتخاب کنید";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "فیلترهای موجود";
	}

	@Override
	public String faltanCampos() {
		return "تمام فیلدهای عمومی ضروری را پر کنید.";
	}

	@Override
	public String critInvalida() {
		return "سطح اهمیت نامعتبر است. از گزینه‌های هشدار، خطا یا کشنده استفاده کنید.";
	}

	@Override
	public String filtroNoExiste() {
		return "فیلتر مشخص شده وجود ندارد.";
	}

	@Override
	public String faltanIdiomas() {
		return "نام و نتیجه را برای تمام زبان‌ها تکمیل کنید:";
	}

	@Override
	public String verificacionInvalida() {
		return "یک بررسی نامعتبر است. لطفاً فیلدها را بررسی کنید.";
	}

	@Override
	public String guardadoOk() {
		return "با موفقیت ذخیره شد.";
	}

	@Override
	public String editorCodiceBoton() {
		return "افزودن دلایل";
	}

	@Override
	public String descripcionEditorCodice() {
		return "می‌توانید اینجا دلایل را ثبت کنید. به یک شناسه (ID) نیاز دارید که رشته‌ای بدون کاراکترهای خاص، علامت لهجه یا فاصله باشد. برای فیلترها می‌توانید از «خط شامل» برای جستجوی یک رشته در یک خط، «همه شامل» اگر سیاهه شامل یک رشته باشد، «عبارت منظم خط» اگر یک خط با عبارت منظم تطبیق داشته باشد، و «عبارت منظم همه» استفاده کنید (پیشنهاد می‌شود از نسخه‌های خط استفاده کنید). باید سطح اهمیت را تعیین کنید: FATAL، ERROR یا ADVERTENCIA تا رنگ‌بندی انجام شود. برای تمام زبان‌ها باید یک نام و پاسخ بنویسید که روی صفحه نمایش داده می‌شود. می‌توانید بررسی‌های بیشتری اضافه یا برخی را حذف کنید. پس از تکمیل ذخیره می‌شود.";
	}

	@Override
	public String descartarCambios() {
		return "آیا می‌خواهید تغییرات ذخیره‌نشده در بررسی جاری را دور بیندازید؟";
	}

	@Override
	public String confirmacion() {
		return "تایید";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "آیا قبل از خروج، تغییرات را ذخیره می‌کنید؟";
	}

	@Override
	public String salirSinGuardar() {
		return "خروج بدون ذخیره";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("خطای بحرانی: بارگذاری سرویس modlauncher (IDependencyLocator) شکست خورد.<br>");
		sb.append("🔹 <b>کلاس مشکل‌ساز:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>مود تحت تأثیر:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append(
					"🔸 <b>مود شناسایی نشد.</b> مودهای اخیراً نصب شده، توسعه‌ای یا بد بسته‌بندی شده را بررسی کنید.<br>");
		}

		sb.append("🔸 <b>علت:</b> فایل <code>META-INF/services/...</code> مود خراب است، ");
		sb.append("با نسخه فعلی Forge/NeoForge سازگار نیست، یا مود برای نسخه اشتباهی است.<br>");
		sb.append("🔸 <b>پیامد:</b> Forge/NeoForge نمی‌تواند وابستگی‌های مود را ثبت کند، ");
		sb.append("که باعث جلوگیری از راه‌اندازی بازی می‌شود.<br>");
		sb.append("🔸 <b>راه‌حل:</b> مود مشکل‌دار را به‌روزرسانی، دوباره نصب یا حذف کنید. ");
		sb.append(
				"اگر از مودهای توسعه‌ای استفاده می‌کنید، مطمئن شوید برای نسخه دقیق Forge/NeoForge شما کامپایل شده‌اند.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "خطای پیکربندی سرویس (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. مود عامل را شناسایی کنید: مودهای اخیراً نصب شده یا در حال توسعه را بررسی کنید.";
		}
		return "1. مود مشکل‌دار این است: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. مود را به‌روزرسانی، دوباره نصب یا حذف کنید. مطمئن شوید از نسخه‌ای سازگار با Forge/NeoForge خود استفاده می‌کنید.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>خطای بحرانی: متود وجود ندارد.</b><br>"
				+ "مود سعی کرد به متود <b style='color:#" + colorCodigo + "'>" + metodo + "</b> فراخوانی کند، "
				+ "که در این نسخه از بازی یا مود دیگر وجود ندارد.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "متود وجود ندارد (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. این خطا زمانی رخ می‌دهد که یک مود با نسخه فعلی بازی یا مود دیگری ناسازگار باشد.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. تمام مودهای درگیر را به‌روزرسانی کنید. اگر مشکل باقی ماند، خطای را به نویسنده مود تحت تأثیر گزارش دهید.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>خطای بحرانی: فیلد وجود ندارد.</b><br>"
				+ "مود سعی کرد به فیلد <b style='color:#" + colorCodigo + "'>" + campo + "</b> دسترسی پیدا کند، "
				+ "که در این نسخه از بازی یا مود دیگر وجود ندارد.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "فیلد وجود ندارد (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. این خطا معمولاً زمانی رخ می‌دهد که یک مود با نسخه فعلی بازی یا مود دیگری ناسازگار باشد.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. تمام مودهای تحت تأثیر را به‌روزرسانی کنید. اگر مشکل ادامه داشت، با توسعه‌دهنده مودی که خطا را ایجاد کرده تماس بگیرید.";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = MonitorDePID.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png")
				.toAbsolutePath().toUri().toString();

		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>به کمک نیاز دارید؟</strong><br>"
				+ "  اگر نمی‌دانید چگونه آن را رفع کنید یا علت در اینجا وجود ندارد، می‌توانید از طریق شبکه‌های اجتماعی ما کمک بگیرید. "
				+ "  از دکمه <img src='" + iconoCompartir
				+ "' alt='اشتراک‌گذاری' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>اشتراک‌گذاری</strong> استفاده کنید تا پیوندهایی به سیاهه‌ها و نتایج برای تیم ما دریافت کنید. "
				+ "  اگر سازندهٔ مودپک یا یک شرکت هستید، فایل <code>crash_detector/plantilla.htm</code> را ویرایش کنید "
				+ "  تا پیوندهای تیم خود را شخصی‌سازی کنید." + "</div>";
	}

	@Override
	public String restablecerPlantilla() {
	    return "بازنشانی الگو";
	}

	@Override
	public String restablecer() {
	    return "بازنشانی";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
	    return "آیا " + nombreImagen + " را به مقادیر پیش‌فرض بازنشانی کنیم؟";
	}

	@Override
	public String restablecerPlantillaMensaje() {
	    return "آیا الگو را به مقادیر پیش‌فرض بازنشانی کنیم؟";
	}
	
	@Override
	public String faltar_de_clases_azurelib() {
	    return "<b style='color:#" + config.obtenerColorError()
	           + "'>کلاس‌های AzureLib یافت نشد. اگر قبلاً AzureLib دارید، لطفاً نسخه‌ای قبل از ۸ اکتبر ۲۰۲۵ را نصب کنید. این مشکل رایج بود. اگر AzureLib ندارید، آخرین نسخه را نصب کنید.</b>";
	}
	
	
	
}
