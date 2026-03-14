package com.asbestosstar.crashdetector.idioma;

import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

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
				+ "'>Theseus همچنین مشکلات دیگری دارد، از جمله عدم توانایی در حذف مودها وقتی که سعی می‌کنید. اگر نیاز به اجرای فایل‌های mrpack دارید، می‌توانید از راه‌اندازهای دیگری مانند Prism Launcher (فقط برای modrinth.com)، ATLauncher (فقط برای modrinth.com) یا Hello Minecraft Launcher (پشتیبانی از modrinth.com و bbsmc.net) استفاده کنید.</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>شما از گزینهٔ «رد کردن شروع راه‌انداز» (برنامهٔ CurseForge) استفاده می‌کنید. گاهی این باعث مشکلاتی می‌شود که تشخیص آن‌ها دشوار است. این به دلیل گزینهٔ «رد کردن شروع راه‌انداز» در نسخه‌های قدیمی یا جدید برنامهٔ CurseForge است. آن را غیرفعال کنید و در تنظیمات CurseForge از گزینهٔ «Mojang Launcher» استفاده نمایید. می‌توانید این ویدیوی انگلیسی از Claws of Berk (در 1:11) را "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>اینجا</a> ببینید.</b>";
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
				+ "'>اینجا نتایج بررسی‌های شما آمده است. آرام پیش بروید؛ معمولاً علت درست در بررسی ۱ یا ۲ قرار دارد. بقیه (خطاهای ۳ به بعد) را می‌توان برای تأیید استفاده کرد، اما معمولاً خطاهای زنجیره‌ای هستند که می‌توانید نادیده بگیرید. خطاها به صورت لایه‌ای رخ می‌دهند، بنابراین حل مشکل اصلی این خطا را رفع می‌کند. با این حال، ممکن است فردا خطای جدیدی که با خطای فعلی رابطه‌ای ندارد ظاهر شود، چون اغلب یک خطا مانع نمایش خطای دیگر در کنسول می‌شود.</b>";
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
		return "این دیالوگ به شما امکان می‌دهد با استفاده از API SecureLogger "
				+ "در <a href=\"https://securelogger.net\">securelogger.net</a> لاگ‌ها را به اشتراک بگذارید. با فشردن دکمهٔ اشتراک‌گذاری گزارش، "
				+ "گزارش شما به نقطهٔ پایانی انتخاب‌شده (پیش‌فرض: asbestosstar.egoism.jp) ارسال می‌شود (قابل تغییر در پایین). "
				+ "می‌توانید تمام لاگ‌های انتخاب‌شده را همراه با گزارش به اشتراک بگذارید. اگر نمی‌خواهید آپلود کنید، از این دیالوگ استفاده نکنید! "
				+ "ما گزارش شما را در نقطهٔ پایانی رسمی پردازش نمی‌کنیم (<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\">https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb</a>)؛ "
				+ "فقط لینک‌های غیرمجاز را حذف می‌کنیم. کد منبع اینجا است: <a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">کد منبع</a>. "
				+ "این فقط برای نمایش اطلاعات مربوط به crash شما و لینک لاگ‌ها استفاده می‌شود. با این حال، می‌توانید از یک نقطهٔ پایانی سفارشی استفاده کنید که ممکن است همان روش‌ها را نداشته باشد. "
				+ "شما از سایت گزارش " + Config.obtenerInstancia().obtenerSitoDeInformes() + " و سایت لاگ "
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + " استفاده می‌کنید. "
				+ "همچنین می‌توانید با فشردن دکمه‌های اشتراک‌گذاری کنار نام لاگ‌های فردی، لاگ‌های جداگانه را بدون گزارش به اشتراک بگذارید؛ "
				+ "لاگ‌ها به سایت لاگ انتخاب‌شده ارسال می‌شوند. CrashDetector دارای ناشناس‌سازی پیش‌فرض لاگ است که سعی می‌کند نام کاربری، UUID، "
				+ "توکن‌های دسترسی، IDهای نشست، آدرس‌های IP و سایر داده‌ها را حذف کند. با این حال، کامل نیست. با این وجود، سازندهٔ modpack می‌تواند آن را غیرفعال کند. "
				+ "این قابلیت را می‌توان با کادر علامت‌گذاری در پایین این صفحه فعال یا غیرفعال کرد. شما کنترل‌کنندهٔ داده‌های خود هستید؛ شما تصمیم می‌گیرید داده‌هایتان را کجا آپلود کنید. "
				+ "سایت‌های لاگ متعلق به شخص ثالث هستند که مالکیت آن‌ها اغلب به دلایل حریم خصوصی پنهان است. شما مسئولیت کامل مدیریت داده‌های خود و ریسک‌های مرتبط را بر عهده دارید. "
				+ "دیالوگ اشتراک‌گذاری CrashDetector تنها یک رابط است که به شما امکان مدیریت این موارد را می‌دهد. "
				+ "آگاهی از GDPR و ARCO بسیار مهم است. "
				+ "اگر در اروپا هستید، می‌توانید از <a href=\"https://securelogger.top\">securelogger.top</a> که توسط Hetzner در آلمان میزبانی می‌شود، استفاده کنید. "
				+ "برای اطلاعات حقوقی بیشتر، لینک‌های زیر را مشاهده کنید: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>، "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">GDPR</a>، "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">سیاست اساسی حفاظت از داده‌های شخصی در ژاپن</a>.";
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

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "هشدار! Crash Assistant یک تشخیص‌دهنده بدافزار جعلی است. این برنامه به‌طور عمدی بازی را از اجرای شروع مسدود می‌کند و آزادی شما برای ادامه بازی با افزونه‌های هدف‌گیری‌شده را نادیده می‌گیرد. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>مشاهده کد MalwareMod.java</a>   "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>مشاهده کد JarInJarHelper.java</a>. "
				+ "در حال حاضر فقط این افزونه در لیست آنها قرار دارد و در واقع فقط به سایت ثبت لاگ پیش‌فرض حمله می‌کنند که می‌تواند توسط کاربر تغییر کند و این فقط زمانی اتفاق می‌افتد که به‌صورت صریح از ویژگی مشارکت لاگ داخلی استفاده کنید. CrashAssistant هیچ بررسی‌ای برای حتی تعیین اینکه کدام سایت ثبت لاگ استفاده می‌شود انجام نمی‌دهد و نحوه تغییر آن را توضیح نمی‌دهد (یک منوی کشویی در پایین جعبه محاوره‌ای اشتراک وجود دارد) و صرف‌نظر از اینکه چه سایتی تنظیم کرده‌اید، CrashAssistant اجرای بازی را مسدود خواهد کرد. در پیام آنها اشاره شده که تحقیقات خود را انجام دهید، این کار را انجام دهید، به کد CrashDetector و Crash Assistant نگاه کنید و درک کنید که چه کاری انجام می‌دهند، به استدلال اقتباس به مقام اعتماد نکنید.</b>";
	}

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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطای بحرانی پیکربندی NightConfig/Forge: "
				+ "فایل پیکربندی خراب یا ناقص است. "
				+ "این ممکن است به دلیل فایل‌های پیکربندی خالی (اغلب ۰ بایت) در پوشهٔ 'config' در نسخه‌های قدیمی یا سفارشی NightConfig رخ دهد. "
				+ "برای اکثر نسخه‌ها، Night Config Fixes این مشکل را حل می‌کند، اما اگر از نسخهٔ ناسازگار یا سفارشی NightConfig استفاده می‌کنید، باید فایل‌های پیکربندی را به صورت دستی حذف کنید. "
				+ "این مشکل در نسخه‌های قدیمی MC Forge (که شامل NightConfig هستند) و در افزونه‌های قدیمی FabricMC که NightConfig را همراه دارند، رایج‌تر است، اما ممکن است در برخی نسخه‌های سفارشی NightConfig نیز وجود داشته باشد. "
				+ "اطلاعات بیشتر دربارهٔ راه‌حل‌ها در <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a> موجود است.</b>";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>هنگام بارگذاری چنک‌ها (chunks) توسط دنیا، یک استثنا رخ داد. اگر برای پلتفرم شما موجود باشد، ممکن است FeatureRecycler بتواند این مشکل را حل کند. https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
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
		StringBuilder sb = new StringBuilder("<b>توابع چگالی در رجیستری وجود ندارند.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("مفقود شده‌ها: ");
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
				"<br/><b>راه‌حل:</b> mod/datapackی را که این توابع را تعریف می‌کند، نصب یا فعال کنید و مجدداً راه‌اندازی نمایید. دلیل دیگر رایج این مشکل این است که شما mod لازم را دارید اما آن mod دچار مشکل است یا با mod دیگری در تداخل است؛ به‌عنوان مثال، Terralith مشکلات زیادی دارد و می‌تواند این خطا و سایر خطاها از جمله خطاهای JSON را ایجاد کند.");
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
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();

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

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "مود <code>healight</code> باعث خطای بحرانی می‌شود: <code>java.lang.NoSuchFieldError: INT</code>. "
				+ "این خطا زمانی رخ می‌دهد که مود سعی کند به فیلدی دسترسی پیدا کند که در نسخه MCForge 47.10 برای ماینکرفت 1.20+ دیگر وجود ندارد. "
				+ "به دلیل این مشکل، بازی قابل راه‌اندازی نیست.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• مود <code>healight</code> را حذف یا به‌روزرسانی کنید. "
				+ "نسخه فعلی با MinecraftForge 47.10 برای 1.20.1 سازگار نیست. "
				+ "نسخه جدیدتری از مود را پیدا کنید یا از یک جایگزین استفاده کنید.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "خطای بحرانی: healight - فیلد 'INT' یافت نشد";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("کلاس <code>").append(clase)
				.append("</code> متد الزامی زیر را پیاده‌سازی نکرده است:<br>").append("<code>").append(metodo)
				.append("</code><br>").append("از رابط <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br><br>مود یا فایل مشکوک: <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• این خطا زمانی رخ می‌دهد که یک مود یک رابط را پیاده‌سازی کند اما یک متد ضروری را فراموش کند. "
				+ "<b>هر دو مود</b> درگیر را به‌روزرسانی کنید (مودی که رابط را تعریف می‌کند و مودی که آن را پیاده‌سازی می‌کند). "
				+ "اگر نمی‌دانید کدام هستند، به نام‌هایی که در پیام خطا نشان داده شده نگاه کنید.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "متد رابط پیاده‌سازی نشده (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "یک مود در حال تلاش برای بارگیری یک کلاس <b>طرف کلاینت</b> "
				+ "(<code>AnimationMetadataSection</code>) روی یک <b>سرور اختصاصی</b> است، که غیرممکن است. "
				+ "این خطا معمولاً زمانی رخ می‌دهد که یک مود کد خود را به درستی بین کلاینت و سرور جدا نکند. "
				+ "وجود <code>ModernFix</code> ممکن است این مشکل را آشکار کند، هرچند علت مستقیم نیست.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>راه‌حل سریع:</b> برای اطمینان از راه‌اندازی سرور، موقتاً <code>ModernFix</code> را حذف کنید. "
				+ "اگر سرور راه‌اندازی شد، مشکل از مود دیگری است که کلاس‌های کلاینت را روی سرور بارگیری می‌کند.<br>"
				+ "• <b>راه‌حل دائمی:</b> مود مقصر را شناسایی کنید (به دنبال مودهایی با منابع انیمیشنی، بافت‌های سفارشی یا کتابخانه‌های گرافیکی باشید) و آن را به‌روزرسانی یا حذف کنید.<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "بارگیری کلاس کلاینت روی سرور (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "فایل پیکربندی یک مود <code>Sinytra Connector</code> خراب است. "
				+ "این معمولاً زمانی اتفاق می‌افتد که فایل با کاراکترهای خالی (<code>\\u0000</code>) پر شود "
				+ "به دلیل خاموشی غیرمنتظره بازی، خطا در نوشتن یا تداخل مودها.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• به پوشه <code>config/</code> نمونه بازی ماینکرفت خود بروید.<br>"
				+ "• تنظیمات مودهای connector را پیدا کرده و حذف کنید.<br>"
				+ "• بازی را دوباره راه‌اندازی کنید: Sinytra Connector یک فایل پیکربندی جدید و تمیز ایجاد خواهد کرد.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "پیکربندی خراب Sinytra Connector";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "فایل <code>" + nombreJar
				+ "</code> خراب یا ناقص است.<br>"
				+ "سیستم نمی‌تواند محتوای آن را بخواند زیرا هدر پایانی فایل ZIP گم شده است.<br>"
				+ "این خطا معمولاً پس از دانلود قطع شده یا خرابی راه‌انداز اتفاق می‌افتد.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "فایل JAR خراب (با نام مشخص)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>فایل خراب را حذف کنید</b> و دوباره از منبع رسمی (CurseForge، MinecraftStorage و غیره) دانلود کنید.<br>"
				+ "• اگر از راه‌اندازهایی مانند CurseForge، Technic یا Luna Pixel استفاده می‌کنید، بهتر است به <b>ATLauncher</b> یا <b>Prism Launcher</b> تغییر دهید، "
				+ "که صحت فایل‌ها را بهتر بررسی می‌کنند.<br>"
				+ "• مطمئن شوید در طول دانلود، اتصال اینترنت شما پایدار باشد.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ناتوانی در بارگیری دنیا به دلیل خرابی یکی از فایل‌های NBT آن "
				+ "(مثلاً: <code>level.dat</code>, <code>playerdata/*.dat</code>، یا چنک‌ها).<br>"
				+ "خطای خاص: <code>UTFDataFormatException: ورودی معیوب در اطراف بایت " + byteCorrupto
				+ "</code>.<br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ قبل از هر تلاش برای تعمیر، یک کپی پشتیبان کامل از پوشه دنیا بگیرید.</b><br><br>"
				+ "می‌توانید سعی کنید فایل خراب را با یک <b>ویرایشگر NBT</b> مانند <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a> تعمیر کنید.<br>"
				+ "اگر آسیب جدی باشد، از یک <b>ویرایشگر هگزادسیمال</b> (مانند HxD) برای بررسی و اصلاح بایت‌های نامعتبر استفاده کنید "
				+ "(فقط در صورتی که با فرمت NBT تجربه داشته باشید).<br>"
				+ "در آخرین حالت، از یک پشتیبان بازیابی کنید یا از قابلیت <i>تعمیر دنیا</i> در مودهایی مانند <code>FTB Backup</code> استفاده کنید.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>قبل از هر تلاش برای تعمیر، یک کپی پشتیبان کامل از پوشه دنیا بگیرید</b>.<br>"
				+ "• از یک ویرایشگر NBT (مانند NBT Studio) برای باز کردن و تعمیر فایل خراب استفاده کنید.<br>"
				+ "• اگر شکست خورد، فایل را با یک ویرایشگر هگز در موقعیت بایت خراب بررسی کنید.<br>"
				+ "• اگر تجربه ندارید، از یک پشتیبان اخیر بازیابی کنید.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "دنیای خراب: خطای بارگیری داده‌های NBT";
	}

	@Override
	public String problema_con_openAL() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>مشکلی با OpenAL دارید. گاهی اوقات درایورهای Nouveau این مشکل را ایجاد می‌کنند، اما گاهی هم نسخهٔ OpenAL که با برنامه بسته‌بندی شده با نسخه موجود در توزیع شما سازگار نیست و باید از نسخه توزیع خود استفاده کنید. این موضوع به ویژه در توزیع‌های مبتنی بر Red Hat و با مودهای صدا مانند Sound Physics Remastered رایج است. برای راهنمایی بیشتر به این راهنما مراجعه کنید: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>چگونه مشکلات صدا در ماینکرفت روی لینوکس را رفع کنیم</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "سرور نمی‌تواند راه‌اندازی شود زیرا یک فایل دنیا توسط فرآیند دیگری قفل شده است.<br>"
				+ "این معمولاً در صورتی اتفاق می‌افتد که:<br>" + "• قبلاً یک نمونه سرور در حال اجرا باشد.<br>"
				+ "• آنتی‌ویروس یا مرورگر فایل، پوشه دنیا را باز کرده باشد.<br>"
				+ "• فرآیند قبلی به درستی بسته نشده و فایل‌ها قفل مانده‌اند.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>تمام نمونه‌های سرور را ببندید</b> (شامل فرآیندهای پس‌زمینه مثل javaw.exe).<br>"
				+ "• اگر از پنل هاستینگ (مثل Multicraft) استفاده می‌کنید، از طریق پنل سرور را کاملاً ریستارت کنید.<br>"
				+ "• <b>موقتاً آنتی‌ویروس خود را غیرفعال کنید</b> اگر فکر می‌کنید فایل‌ها را مسدود کرده است.<br>"
				+ "• در سیستم‌های محلی، تمام پنجره‌های اکسپلورر ویندوز که پوشه دنیا را نشان می‌دهند را ببندید.<br>"
				+ "• اگر مشکل باقی ماند، فایل <code>session.lock</code> را درون پوشه دنیا حذف کنید (فقط در صورتی که مطمئن باشید سرور دیگری فعال نیست).";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "فایل دنیا توسط فرآیند دیگری قفل شده است";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "مود سعی کرد کلاس <code>" + clasePadreFinal
				+ "</code> را گسترش دهد، " + "اما این کلاس اکنون <b>نهایی</b> است و نمی‌تواند به ارث برده شود.<br>"
				+ "کلاس مشکل‌ساز: <code>" + claseHija + "</code>.<br><br>"
				+ "این معمولاً زمانی اتفاق می‌افتد که یک مود برای نسخه قبلی ماینکرفت یا یک مود پایه دیگر کامپایل شده باشد، "
				+ "که در نسخه‌های اخیر کلاس‌ها را به عنوان <code>final</code> علامت‌گذاری کرده است.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>تمام مودهای درگیر را به‌روزرسانی کنید</b>، به خصوص آن‌هایی که ممکن است به مود پایه ذکر شده مربوط باشند.<br>"
				+ "• اگر مشکل باقی ماند، به دنبال نسخه‌ای از مود بگردید که با نسخه فعلی ماینکرفت و وابستگی‌هایش سازگار باشد.<br>"
				+ "• در برخی موارد، حذف موقت مودی که شامل کلاس فرزند است می‌تواند علت را تأیید کند.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "تلاش برای ارث‌بری از یک کلاس نهایی";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "شما از <b>Rubidium</b> (یک نسخه قدیمی شده از Sodium برای Forge) همراه با <b>Iris یا Oculus</b> استفاده می‌کنید.<br>"
				+ "در نسخه‌های اخیر ماینکرفت (1.19.2 به بعد)، "
				+ "Rubidium دیگر با Sodium هماهنگ نیست و وابستگی‌های آن مشکلاتی داشته‌اند.<br><br>"
				+ "این خطا همچنین زمانی رخ می‌دهد که تداخلی بین مودهای عملکردی (Sodium، Rubidium، Embeddium، Bedium، Xeonium و غیره) یا Iris Shaders و یک مود دیگر وجود داشته باشد.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>Rubidium را از پوشه <code>mods</code> حذف کنید</b>.<br>"
				+ "• <b><a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> را نصب کنید</b>، "
				+ "این نسخه جایگزین فعال و سازگار Sodium برای Forge است که در 1.20+ از Iris/Oculus پشتیبانی می‌کند.<br>"
				+ "• مطمئن شوید بیش از یک نسخه از Sodium را همزمان نصب نکرده باشید (مثلاً Rubidium + Embeddium).<br>"
				+ "• اگر به جای Iris از Oculus استفاده می‌کنید، مطمئن شوید که با نسخه‌های Forge و Embeddium شما سازگار است.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium منسوخ با Iris/Oculus (OptionInstance نهایی است)";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "مود <code>Simple Voice Chat</code> نمی‌تواند سرور صدا خود را راه‌اندازی کند زیرا "
				+ "پورت UDP قبلاً در حال استفاده است یا آدرس IP تنظیم شده معتبر نیست.<br>"
				+ "این موضوع باعث جلوگیری از راه‌اندازی بازی نمی‌شود، اما عملکرد چت صوتی را غیرفعال می‌کند.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• <b>هر نمونه دیگری از ماینکرفت یا برنامه‌ای که از پورت UDP 24454 استفاده می‌کند را ببندید</b>.<br>"
				+ "• اگر روی یک سرور هستید، مطمئن شوید <b>هیچ سرویس دیگری</b> از آن پورت استفاده نمی‌کند.<br>"
				+ "• در تنظیمات مود (<code>config/voicechat/</code>)، پورت UDP را به یک پورت آزاد تغییر دهید (مثلاً 24455).<br>"
				+ "• اگر از یک آدرس IP شخصی استفاده می‌کنید، صحت آن را بررسی کنید یا برای استفاده از مقدار پیش‌فرض آن را خالی بگذارید.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "چت صوتی: پورت UDP اشغال شده یا IP نامعتبر";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "BlockItem <code>" + nombreBlockItem
				+ "</code> دارای بلوک تهی است.<br>"
				+ "این خطا معمولاً در <b>افزونه‌های Create</b> (مانند <code>dndecor</code>, <code>createdeco</code>) رخ می‌دهد "
				+ "وقتی تداخلی با <code>Amendments</code>, <code>Moonshine</code> وجود داشته باشد یا مقداردهی اولیه بلوک‌ها اشتباه باشد.<br>"
				+ "<b>توجه:</b> این خطا مستقیماً از Amendments نیست، بلکه مشکل عمیق‌تری در بارگیری ثبت‌هاست.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• <b>تمام مودهای مرتبط را به‌روزرسانی کنید:</b> Create، Amendments، Moonshine و هر افزونه‌ای (به خصوص <code>dndecor</code> و <code>createdeco</code>).<br>"
				+ "• اگر مشکل باقی ماند، <b>افزونه‌های Create را موقتاً یکی یکی حذف کنید</b> تا عامل را پیدا کنید.<br>"
				+ "• مطمئن شوید <b>Amendments و Moonshine با نسخه Create و Forge شما سازگار باشند</b>.<br>"
				+ "• بررسی کنید آیا نسخه بتا یا فورک‌های به‌روز شده‌ای از افزونه‌های مشکل‌دار وجود دارد.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "BlockItem تهی در افزونه Create";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>")
				.append("مد/هایی که به هیچ پلتفرم فعالی (مثل Forge، Fabric و غیره) تعلق ندارند پیدا شدند:<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>این معمولاً زمانی اتفاق می‌افتد که:<br>")
				.append("• مد/های <b>Fabric و Forge</b> در یک پوشه با هم مخلوط شده باشند.<br>")
				.append("• مد برای نسخه‌ای ناسازگار از ماینکرفت نصب شده باشد.<br>")
				.append("• مد خراب باشد یا یک فایل JAR معتبر نباشد.</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>اطمینان حاصل کنید همه مد/ها برای یک پلتفرم هستند</b> (Forge <b>یا</b> Fabric، نه هر دو).<br>"
				+ "• از <b>درخت مد/ها</b> برای تشخیص پلتفرم هر فایل استفاده کنید.<br>"
				+ "• هر مدی که نمی‌شناسید یا برای پلتفرم دیگری است را حذف کنید.<br>"
				+ "• اگر از راه‌اندازی مثل CurseForge یا Prism استفاده می‌کنید، مطمئن شوید پروفایل به درستی تنظیم شده باشد.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "مد با بارگیرنده فعال ناسازگار است";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "ایجاد مدل <code>" + modid + ":"
				+ nombreModelo + "</code> ناموفق بود.<br>" + "این نشان می‌دهد که مود <code>" + modid
				+ "</code> دارای منابع خراب، گم شده، " + "یا ناسازگار با نسخه ماینکرفت شماست.</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>مود را به آخرین نسخه سازگار با نمونه خود به‌روزرسانی کنید</b>.<br>"
				+ "• اگر از نسخه توسعه یا سفارشی استفاده می‌کنید، به نسخه رسمی برگردید.<br>"
				+ "• مطمئن شوید فایل JAR خراب نیست (دوباره نصب کنید).<br>"
				+ "• اگر مشکل باقی ماند، خطای را همراه با این لاگ به نویسنده مود گزارش دهید.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "خطا در ایجاد مدل منبع";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "تضاد بحرانی بین مودهای <code>Moonlight</code> و <code>Iceberg</code> تشخیص داده شده است.<br>"
				+ "هر دو سعی در ثبت سیستم‌های بازبارگیری منابع به شکل ناسازگار دارند، "
				+ "که باعث خرابی OpenGL به دلیل عدم وجود یک کانتکست گرافیکی معتبر می‌شود.<br>"
				+ "این مشکل زمانی رایج است که از نسخه‌های Forge که شامل آداپتورهای مود Fabric هستند استفاده می‌کنید.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>هر دو مود را به جدیدترین نسخه‌های سازگار با نسخه Forge خود به‌روزرسانی کنید</b>.<br>"
				+ "• اگر مشکل باقی ماند، <b>به طور موقت Iceberg را حذف کنید</b>، زیرا Moonlight معمولاً یک وابستگی مهم‌تر برای مودهای دیگر است.<br>"
				+ "• مطمئن شوید نسخه‌های تکراری یا ترکیبی Forge/Fabric از این مودها ندارید.<br>"
				+ "• بررسی کنید آیا مود دیگری (مثل Supplementaries، Citadel و غیره) قبلاً عملکرد Iceberg را داخلی دارد.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "تضاد بحرانی: Moonlight در مقابل Iceberg (OpenGL بدون کانتکست)";
	}

	@Override
	public String instantanea() {
		return "تصویر لحظه‌ای";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "از آخرین تصویر لحظه‌ای";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "یک فایل انتخاب کنید";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "تصویر لحظه‌ای با موفقیت ایجاد شد";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "خطا در ایجاد تصویر لحظه‌ای";
	}

	@Override
	public String consejo() {
		return "نکته";
	}

	@Override
	public String resultadoMuestra() {
		return "نمایش نتیجه";
	}

	@Override
	public String historaDeModsDesc() {
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>نکته:</b> دو فایل تاریخچه را برای مقایسه لیست مودها انتخاب کنید. "
				+ "  نتیجه <span style='color:%s;'>افزوده شده‌ها (+)</span> و "
				+ "  <span style='color:%s;'>حذف شده‌ها (&#8722;)</span> را بر اساس نام‌های استاندارد شده نشان می‌دهد. "
				+ "  از دکمه «تصویر لحظه‌ای» برای ایجاد کپی از یک فایل موجود با پسوند .instantanea استفاده کنید."
				+ "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		return "دریافت پیوندهای سیاهه به صورت مارک‌داون بدون گزارش";
	}

	@Override
	public String titulo_configuracion() {
		return "پیکربندی";
	}

	@Override
	public String columna_url() {
		return "آدرس URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "خطای غیرمنتظره‌ای هنگام اشتراک‌گذاری رخ داد.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "خطای غیرمنتظره‌ای هنگام تولید پیوندها رخ داد.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "خطای غیرمنتظره‌ای هنگام پردازش دکمه رخ داد.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "فایلی برای باز کردن وجود ندارد.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "فایل وجود ندارد:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "در ویرایشگر باز نشد.\nمسیر به حافظه موقت کپی خواهد شد.";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "عدم توانایی در باز کردن فایل؛ مسیر به حافظه موقت کپی شد.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "دسکتاپ پشتیبانی نمی‌شود؛ مسیر به حافظه موقت کپی شد.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "به محدودیت درخواست رسیده‌اید. سعی کنید از یک سایت ثبت لاگ دیگر یا یک API ثبت لاگ دیگر استفاده کنید.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		return "اشتراک‌گذاری پیوند";
	}

	@Override
	public String infoDeTrazos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "رفع مشکل بالای تنه‌ها اولویت اول است. "
				+ "فرمت به صورت سطح، خط است. " + "همه سیاهه‌ها یک سیستم شماره‌گذاری دارند. " + Verificaciones.nl_html
				+ "به طور کلی باید در همه سیاهه‌ها به دنبال پایین‌ترین سطوح باشید؛ ردپاها با سطوح بالا معمولاً مثبت کاذب هستند. "
				+ "استفاده از توانایی خود برای مشاهده کنسول مهم است، زیرا تحلیل ردپاها وقتی تعداد زیادی ردپا وجود داشته باشد کامل نیست."
				+ "</b>";
	}

	// --- جستجوگر کاناری دستور (Warrant Canary) ---
	/**
	 * متن دکمه برای جستجوگر کاناری دستور. مثال: "جستجوگر کاناری دستور"
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "جستجوگر کاناری دستور";
	}

	/**
	 * پیامی که در کادر محاوره‌ای نمایش داده می‌شود و اطلاع می‌دهد که این قابلیت به
	 * زودی در دسترس خواهد بود.
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "این قابلیت به زودی در دسترس خواهد بود.";
	}

	/**
	 * عنوان کادر محاوره‌ای که دربارهٔ در دسترس بودن آیندهٔ جستجوگر کاناری دستور
	 * اطلاع می‌دهد.
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "به زودی";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		return "مودهای ناسازگار با Crash Assistant (غلط)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		return "مود ناسازگار با مودپک با استفاده از CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant لیستی از مودهایی دارد که می‌گوید ناسازگار هستند، اما ما شواهدی نداریم و خطای آن فقط به انگلیسی است. اگر می‌خواهید با این مودها بازی کنید، می‌توانید فایل <code>config/crash_assistant/config.toml</code> را ویرایش کرده و <code>enabled = true</code> در بخش [compatibility] را به <code>enabled = false</code> تغییر دهید.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant توانایی گفتن ناسازگاری مودها را دارد، اما گاهی این موضوع اشتباه است و خطای آن فقط به یک زبان است. اگر می‌خواهید با این مودها بازی کنید، می‌توانید فایل <code>config/crash_assistant/problematic_mods_config.json</code> را ویرایش کرده و <code>should_crash_on_startup</code> را از <code>true</code> به <code>false</code> تغییر دهید.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "خطا: مود '" + modId + "' به مود '"
				+ dependencia + "' نیاز دارد. در حال حاضر، " + actual + " است." + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "خطا: مود '" + modId + "' به نسخهٔ '"
				+ requerido + "' یا بالاتر از '" + dependencia + "' نیاز دارد، اما این مود نصب نشده است." + "</span>";
	}

	// در کلاس MonitorDePID.idioma (این متد را اضافه کنید)
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "خطا: مود '" + modId + "' با نسخه فعلی '"
				+ dependencia + "' سازگار نیست. "
				+ "باید مود 'Iris/Oculus & GeckoLib Compat' را حذف کنید زیرا با Superb Warfare سازگار نیست و با آخرین نسخه GeckoLib کار نمی‌کند. "
				+ "نسخه فعلی: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "خطا: اجرای وظیفه برای کلاس '" + clase + "' ناموفق بود. "
				+ "این خطا معمولاً با مودهایی که با هم سازگار نیستند یا با سایر مودهای نصب شده تداخل دارند رخ می‌دهد.";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "شکست در اجرای وظایف";
	}

	public String recomendacion_fallos_ejecucion() {
		return "این نوع خطا معمولاً زمانی رخ می‌دهد که بین مودها عدم سازگاری وجود داشته باشد. "
				+ "به خصوص برای مودهایی که به درستی با ConnectorMod کار نمی‌کنند، رایج است.";
	}

	public String info_clase_problematica() {
		return "کلاس مشکل‌دار:";
	}

	public String ver_en_log() {
		return "مشاهده در سیاهه";
	}

	public String no_se_encontraron_clases_problema() {
		return "هیچ کلاس خاصی با مشکلات اجرایی پیدا نشد.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تضاد بحرانی بین OptiFine و Entity Model Features (EMF) شناسایی شد. "
				+ "این دو مود با هم سازگار نیستند و باعث خرابی درج (injection failure) شده و از راه‌اندازی بازی جلوگیری می‌کنند."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "تضاد OptiFine و Entity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "یکی از OptiFine یا Entity Model Features را حذف کنید، زیرا آنها با هم سازگار نیستند.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تضاد بحرانی بین OptiFine و Fusion شناسایی شد. "
				+ "این دو مود با هم سازگار نیستند و باعث خرابی درج (injection failure) شده و از راه‌اندازی بازی جلوگیری می‌کنند."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "تضاد OptiFine و Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "OptiFine یا Fusion را حذف کنید، زیرا آنها با هم سازگار نیستند.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel (که توسط Create نیاز است) به Sodium 0.6.0-beta.2 یا بالاتر نیاز دارد. Rubidium نسخه 0.5.3 است. "
				+ "استفاده از <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> را به عنوان جایگزین در نظر بگیرید."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "تضاد Flywheel و نسخه Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "Sodium را به نسخه 0.6.0-beta.2 یا بالاتر بروزرسانی کنید، یا <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> را به عنوان جایگزین سازگار نصب کنید.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تضاد بحرانی بین OptiFine و Epic Fight شناسایی شد. "
				+ "این دو مود با هم سازگار نیستند و باعث خرابی درج (injection failure) شده و از راه‌اندازی بازی جلوگیری می‌کنند."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "تضاد OptiFine و Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "OptiFine یا Epic Fight را حذف کنید، زیرا آنها با هم سازگار نیستند.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تضاد بحرانی بین OptiFine و Rubidium تشخیص داده شده است. "
				+ "این افزونه‌ها ناسازگار هستند و باعث شکست تزریق می‌شوند که جلوی راه‌اندازی بازی را می‌گیرد." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "تضاد OptiFine و Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "OptiFine یا Rubidium را حذف کنید، زیرا با یکدیگر سازگار نیستند.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam در تلاش برای بارگذاری در سرور اختصاصی است، اما تنها با کلاینت سازگار است. "
				+ "FreeCam را از سرور حذف کنید یا مطمئن شوید که تنها در کلاینت نصب شده است." + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam در سرور اختصاصی";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "FreeCam را از سرور اختصاصی حذف کنید، زیرا باید فقط در کلاینت نصب شود.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) در تلاش برای بارگذاری در سرور اختصاصی است، اما تنها با کلاینت سازگار است. "
				+ "ETF را از سرور حذف کنید یا مطمئن شوید که تنها در کلاینت نصب شده است." + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features در سرور اختصاصی";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features را از سرور اختصاصی حذف کنید، زیرا باید فقط در کلاینت نصب شود.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "برای اجرای سرور، باید EULA ماینکرافت را بپذیرید. "
				+ "فایل eula.txt را ویرایش کرده و 'eula=false' را به 'eula=true' تغییر دهید." + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "EULA ماینکرافت پذیرفته نشده";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "فایل eula.txt را در پوشه سرور ویرایش کرده و 'eula=false' را به 'eula=true' تغییر دهید.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine در تلاش برای بارگذاری در سرور اختصاصی است، اما تنها با کلاینت سازگار است. "
				+ "OptiFine را از سرور حذف کنید یا مطمئن شوید که تنها در کلاینت نصب شده است." + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine در سرور اختصاصی";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "OptiFine را از سرور اختصاصی حذف کنید، زیرا تنها باید در سمت کلاینت نصب شود. این مشکل همچنین اغلب به دلیل نصب نسخه‌ای از OptiFine برای نسخهٔ اشتباه ماينکرفت یا تداخل آن با مود دیگری رخ می‌دهد.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks به اشتباه برای 1.20.1 علامت‌گذاری شده اما از متدهای 1.21.1 استفاده می‌کند. "
				+ "این افزونه در حال تلاش برای استفاده از ResourceLocation.fromNamespaceAndPath است که در 1.20.1 وجود ندارد."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "خطای نسخه Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "مطمئن شوید که نسخه صحیح Iron's Spellbooks سازگار با نسخه ماینکرافت خود را استفاده می‌کنید.";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تضاد بحرانی بین OptiFine و Embeddium تشخیص داده شده است. "
				+ "این افزونه‌ها ناسازگار هستند و باعث شکست تزریق می‌شوند که جلوی راه‌اندازی بازی را می‌گیرد." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "تضاد OptiFine و Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "OptiFine یا Embeddium را حذف کنید، زیرا با یکدیگر سازگار نیستند.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>این مسئله معمولاً با افزونه‌های تولید دنیای متناقض رخ می‌دهد، به‌ویژه Terralinth، AmplifiedNether، Nullscape و Incendium و سایر افزونه‌های تولید دنیا. همچنین ممکن است نیاز باشد یک افزونهٔ گم‌شده را نصب کنید.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable が専用サーバーでの読み込みを試みていますが、これはクライアントでのみ互換性があります。 "
				+ "サーバーから Controllable を削除するか、クライアントにのみインストールされていることを確認してください。" + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "専用サーバー上の Controllable";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "Controllable はクライアントにのみインストールする必要がありますので、専用サーバーから削除してください。";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries باعث خطایی شده که از بارگذاری سرور جلوگیری می‌کند. "
				+ "این افزونه در ثبت رفتارهای آتش مشکل دارد که طی بارگذاری datapackها باعث شکست می‌شود." + "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries مانع از بارگذاری سرور شده است";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "تلاش کنید Supplementaries را به آخرین نسخه بروزرسانی کنید یا به طور موقت آن را غیرفعال کنید تا سرور بتواند بارگذاری شود.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) با مشکلی در مورد ماژول‌های گم‌شده Jackson مواجه شد. "
				+ "برخی افزونه‌ها مانند Valkyrien Skies ممکن است به دلیل عدم ارائهٔ تمام وابستگی‌های لازم، این خطا را ایجاد کنند."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "ماژول Jackson در Groovy Modloader گم شده است";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "Groovy Modloader و افزونه‌های مرتبط مانند Valkyrien Skies که ممکن است باعث تداخل وابستگی‌ها شوند را حذف کنید.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Every Compat نام نامعتبر بلوک چوبی را پیدا کرد. "
				+ "Every Compat معمولاً مشکلات زیادی دارد. از آن استفاده نکنید!" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "نام نامعتبر در Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "پک‌های منابع یا افزونه‌هایی را که از Every Compat استفاده می‌کنند بررسی کنید، زیرا ممکن است حاوی نام‌های نامعتبر بلوک باشند.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "کد خطای (-1073741819) شناسایی شد که ممکن است ناشی از overlayهایی مانند GameCasterِ Razer، Discord، OBS Studio یا مشکلات درایورهای NVIDIA باشد."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "کد خطا -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "سعی کنید overlayهایی مانند GameCaster، Discord یا OBS Studio را غیرفعال کنید و مطمئن شوید که درایورهای NVIDIA شما به‌روز هستند.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips به Immersive Messages به عنوان یک dependencia نیاز دارد اما نصب نشده است. "
				+ "برای اینکه Immersive Tooltips به درستی کار کند، Immersive Messages را نصب کنید." + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips بدون dependencia";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Messages را نصب کنید، زیرا این یک dependencia ضروری برای Immersive Tooltips است.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins با Apoli Mod مشکل سازگاری دارد که در آن ItemStack نمی‌تواند به EntityLinkedItemStack cast شود. "
				+ "این مسئله در نسخه‌های بالاتر از 6.6.0 رایج است. استفاده از نسخه‌ی قدیمی‌تر یا تعویض بین نسخه‌های Fabric و Forge را در نظر بگیرید."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "خطای cast در Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "از نسخه‌ی 6.6.0 یا قدیمی‌تر Medieval Origins استفاده کنید، یا بین نسخه‌های Fabric و Forge این افزونه تغییر دهید.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether باعث خطایی شده است که مربوط به یک Registry Object غایب در MusicManager است. "
				+ "این مشکل مرتبط با mixinِ MusicManager از Reign of Nether می‌باشد." + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "خطای MusicManager در Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "برای رفع این خطا، Reign of Nether را به‌روزرسانی کنید یا به طور موقت آن را حذف نمایید.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel فقط از سرور YSM در Linux یا Android پشتیبانی می‌کند. "
				+ "این مشکل از نسخه‌های جدیدتر از ۲۳ نوامبر ۲۰۲۵ در Modrinth رفع شده است." + "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel با Linux سازگار نیست";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "YesSteveModel را از Modrinth به نسخه‌ای جدیدتر بروزرسانی کنید، زیرا این مشکل پس از ۲۳ نوامبر رفع شده است.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تضاد بحرانی بین Moving Elevators و OptiFine تشخیص داده شده است. "
				+ "این افزونه‌ها ناسازگار هستند و باعث شکست تزریق می‌شوند که جلوی راه‌اندازی بازی را می‌گیرد." + "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "تضاد Moving Elevators و OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "Moving Elevators یا OptiFine را حذف کنید، زیرا با یکدیگر سازگار نیستند.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تضاد بحرانی بین Fabric API (fabric-resource-loader-v0) و OptiFine تشخیص داده شده است. "
				+ "این افزونه‌ها ناسازگار هستند و باعث شکست تزریق می‌شوند که جلوی راه‌اندازی بازی را می‌گیرد." + "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "تضاد Fabric API و OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "OptiFine را حذف کنید یا Fabric API را به نسخه‌ای سازگار بروزرسانی نمایید.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "یک افزونه دارای ITransformationService معیوبی است که نمی‌تواند نمونه‌سازی شود: " + claseProveedor
				+ ". " + "برای امکان بارگذاری بازی، این افزونه باید حذف شود." + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "ITransformationService معیوب";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "افزونه‌ای که کلاس " + claseProveedor
				+ " را دارد حذف کنید، زیرا دارای ITransformationService معیوبی است.";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError() + "'>یک افزونه دارای مشخصات نسخهٔ نامعتبری است. "
				+ "نسخه باید با براکت مربعی احاطه شود. "
				+ "می‌توانید از ابزار grep/greprf در پنل کناری برای جستجوی نسخهٔ </span>" + version
				+ "<span style='color:#" + config.obtenerColorError()
				+ "'> استفاده کنید تا مشخص کنید کدام افزونه مشکل دارد.</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "نسخهٔ نامعتبر در افزونه";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "از ابزار grep/greprf در پنل کناری استفاده کنید تا نسخهٔ مشکل‌دار را جستجو کرده و افزونه‌ای که آن را دارد پیدا کنید.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطای stack smashing شناسایی شد که باعث پایان فرآیند شد. "
				+ "این ممکن است ناشی از مشکلات Early Window در Forge/NeoForge/PillowMC یا LWJGL نسخه 3.2.2 و جدیدتر باشد."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "Stack Smashing شناسایی شد";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "تنظیمات Early Window را بررسی کنید و نسخهٔ دیگری از LWJGL را امتحان کنید یا افزونه‌های مرتبط با پنجرهٔ اولیه را بررسی نمایید.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore فقط برای یک modpack خاص است و نباید در نصب‌های عمومی استفاده شود، زیرا باعث ایجاد مشکل می‌شود."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore با نسخهٔ ناسازگار Java";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore را حذف کنید، زیرا فقط برای یک modpack خاص است و با نصب عمومی شما سازگار نیست.";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تعارضی بین MoniLabs و Connector Extras در ارتباط با اصلاحات KubeJS شناسایی شد. "
				+ "این افزونه‌ها در اصلاحات KubeJS خود ناسازگار هستند." + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "تعارض MoniLabs و Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "یکی از افزونه‌ها (MoniLabs یا Connector Extras) را حذف کنید، زیرا اصلاحات KubeJS آن‌ها با هم تداخل دارد.";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris نیازمند Distant Horizons [2.0.4] یا DH API نسخهٔ [1.1.0] یا جدیدتر است. "
				+ "برای رفع این مشکل، راهنمای سازگاری را در https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e مشاهده کنید."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "سازگاری Iris و Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "راهنمای سازگاری را در https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e بررسی کرده و Iris و Distant Horizons را به نسخه‌های سازگار بروزرسانی کنید.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>شما کلاس‌هایی از Minecraft را از دست داده‌اید. دلایل احتمالی:</b>" + "<ul>"
				+ "<li>افزونه‌هایی برای نسخه‌های دیگر بازی دارید. می‌توانید از <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> برای بررسی وجود کلاس در نسخهٔ خود استفاده کنید.</li>"
				+ "<li>نصب‌ شما از Minecraft خراب است (رایج در CurseForge App، ModrinthApp/Theseus/Astralrinth و سایر راه‌اندازهای modpack). برای رفع مشکلات CurseForge، <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>آموزش را ببینید</a>.</li>"
				+ "<li>coremod معیوبی دارید (در صورت شکست coremod، ممکن است بارگذاری کلاس مسدود شود).</li>" + "</ul>"
				+ "<p>نکته: می‌توانید از ابزار <b>grepr/fgrepr</b> در نوار کناری برای یافتن افزونه‌هایی که به کلاس‌های گم‌شده ارجاع می‌دهند استفاده کنید، به شرطی که در نام‌ها از '/' استفاده کنید.</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>شما کلاس‌هایی از DangerZone را از دست داده‌اید. دلایل احتمالی:</b>" + "<ul>"
				+ "<li>افزونه‌هایی برای نسخه‌های دیگر بازی دارید.</li>" + "<li>coremodهای معیوبی دارید.</li>"
				+ "<li>راه‌انداز یا نصب‌ شما خراب است.</li>" + "</ul>"
				+ "<p>نکته: می‌توانید از ابزار <b>grepr/fgrepr</b> در نوار کناری برای یافتن افزونه‌هایی که به کلاس‌های گم‌شده ارجاع می‌دهند استفاده کنید، به شرطی که در نام‌ها از '/' استفاده کنید.</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>شما کلاس‌هایی از FeatureCreep را از دست داده‌اید. دلایل احتمالی:</b>" + "<ul>"
				+ "<li>افزونه‌هایی برای نسخه‌های دیگر FeatureCreep دارید (مثلاً: ESR در مقابل Nightly یا v4 در مقابل v12).</li>"
				+ "<li>می‌توانید FeatureCreep را از CurseForge یا MinecraftStorage نصب کنید.</li>" + "</ul>"
				+ "<p>نکته: می‌توانید از ابزار <b>grepr/fgrepr</b> در نوار کناری برای یافتن افزونه‌هایی که به کلاس‌های گم‌شده ارجاع می‌دهند استفاده کنید، به شرطی که در نام‌ها از '/' استفاده کنید.</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>شما کلاس‌هایی از ModLauncher را از دست داده‌اید. دلایل احتمالی:</b>" + "<ul>"
				+ "<li>افزونه‌های شما برای build متفاوتی از MinecraftForge، PillowMC یا NeoForge هستند (ModLauncher با این بارگذارها استفاده می‌شود).</li>"
				+ "<li>برای هر نسخهٔ Minecraft، بروزرسانی‌های زیادی از modloader وجود دارد.</li>"
				+ "<li>نصب‌ راه‌انداز شما خراب است (رایج در CurseForge App، ModrinthApp/Theseus/Astralrinth و سایر راه‌اندازهای modpack). برای رفع مشکلات CurseForge، <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>آموزش را ببینید</a>.</li>"
				+ "</ul>"
				+ "<p>نکته: می‌توانید از ابزار <b>grepr/fgrepr</b> در نوار کناری برای یافتن افزونه‌هایی که به کلاس‌های گم‌شده ارجاع می‌دهند استفاده کنید، به شرطی که در نام‌ها از '/' استفاده کنید.</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>شما کلاس‌هایی از Minecraft Forge را از دست داده‌اید. دلایل احتمالی:</b>" + "<ul>"
				+ "<li>افزونه‌های شما برای build متفاوتی از MinecraftForge هستند.</li>"
				+ "<li>برای هر نسخهٔ Minecraft، بروزرسانی‌های زیادی از modloader وجود دارد.</li>"
				+ "<li>نصب‌ شما خراب است (رایج در CurseForge App، ModrinthApp/Theseus/Astralrinth و سایر راه‌اندازهای modpack). برای رفع مشکلات CurseForge، <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>آموزش را ببینید</a>.</li>"
				+ "</ul>"
				+ "<p>نکته: می‌توانید از ابزار <b>grepr/fgrepr</b> در نوار کناری برای یافتن افزونه‌هایی که به کلاس‌های گم‌شده ارجاع می‌دهند استفاده کنید، به شرطی که در نام‌ها از '/' استفاده کنید.</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>شما کلاس‌هایی از NeoForge را از دست داده‌اید. دلایل احتمالی:</b>" + "<ul>"
				+ "<li>افزونه‌های شما برای build متفاوتی از NeoForge هستند.</li>"
				+ "<li>برای هر نسخهٔ Minecraft، بروزرسانی‌های زیادی از modloader وجود دارد.</li>"
				+ "<li>نصب‌ شما خراب است (رایج در CurseForge App، ModrinthApp/Theseus/Astralrinth و سایر راه‌اندازهای modpack). برای رفع مشکلات CurseForge، <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>آموزش را ببینید</a>.</li>"
				+ "</ul>"
				+ "<p>نکته: می‌توانید از ابزار <b>grepr/fgrepr</b> در نوار کناری برای یافتن افزونه‌هایی که به کلاس‌های گم‌شده ارجاع می‌دهند استفاده کنید، به شرطی که در نام‌ها از '/' استفاده کنید.</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>شما کلاس‌هایی از Fabric Loader را از دست داده‌اید. دلایل احتمالی:</b>" + "<ul>"
				+ "<li>افزونه‌های شما برای build متفاوتی از Fabric Loader هستند.</li>"
				+ "<li>برای هر نسخهٔ Minecraft، بروزرسانی‌های زیادی از modloader وجود دارد.</li>"
				+ "<li>نصب‌ شما خراب است (رایج در CurseForge App، ModrinthApp/Theseus/Astralrinth و سایر راه‌اندازهای modpack). برای رفع مشکلات CurseForge، <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>آموزش را ببینید</a>.</li>"
				+ "<li>بسیاری از افزونه‌ها به Fabric API نیاز دارند. در صورت نیاز، Fabric API را نصب کنید.</li>"
				+ "</ul>"
				+ "<p>نکته: می‌توانید از ابزار <b>grepr/fgrepr</b> در نوار کناری برای یافتن افزونه‌هایی که به کلاس‌های گم‌شده ارجاع می‌دهند استفاده کنید، به شرطی که در نام‌ها از '/' استفاده کنید.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>شما کلاس‌هایی از PillowMC را از دست داده‌اید. دلایل احتمالی:</b>" + "<ul>"
				+ "<li>افزونه‌های شما برای build متفاوتی از PillowMC هستند.</li>"
				+ "<li>برای هر نسخهٔ Minecraft، بروزرسانی‌های زیادی از modloader وجود دارد.</li>"
				+ "<li>نصب‌ شما خراب است (رایج در CurseForge App، ModrinthApp/Theseus/Astralrinth و سایر راه‌اندازهای modpack). برای رفع مشکلات CurseForge، <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>آموزش را ببینید</a>.</li>"
				+ "</ul>"
				+ "<p>نکته: می‌توانید از ابزار <b>grepr/fgrepr</b> در نوار کناری برای یافتن افزونه‌هایی که به کلاس‌های گم‌شده ارجاع می‌دهند استفاده کنید، به شرطی که در نام‌ها از '/' استفاده کنید.</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "شما افزونه‌ای دارید که عمداً باعث تأخیر (لاگ) می‌شود. Uranium یک افزونهٔ لاگ است. همیشه باعث crash نمی‌شود، اما ممکن است در نهایت این اتفاق بیفتد."
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack به عنوان سازگار با 1.19.* علامت‌گذاری شده اما در واقع مربوط به 1.20.* است که باعث خطای «کلاس یافت نشد» می‌شود. "
				+ "این افزونه سعی می‌کند از DamageSources‌هایی استفاده کند که در نسخهٔ فعلی Minecraft شما وجود ندارند."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "خطای نسخه Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "مطمئن شوید که از نسخهٔ درست Falling Attack که با نسخهٔ Minecraft شما سازگار است، استفاده می‌کنید.";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>")
				.append("برای استفاده از این قابلیت، باید CFR (Class File Reader) را نصب کنید.<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append("در سیستم‌های Linux، NetBSD یا FreeBSD، می‌توانید CFR را از طریق مدیر بسته نصب کنید.<br>")
					.append("بسته را در اینجا جستجو کنید: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append(
				"در غیر این صورت، می‌توانید نسخهٔ اصلاح‌شده‌ای که FabricMC از آن استفاده می‌کند را از اینجا دانلود کنید:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("آن را در پوشهٔ زیر ذخیره کنید:<br>").append("<b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>مهم:</b> پس از نصب CFR، باید mod را مجدداً راه‌اندازی کنید تا به درستی شناسایی شود.")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "پرتره‌ای در دسترس نیست";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "کلاس یافت نشد: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "دیکامپایلر CFR – Sakura Riddle (غیررسمی)";
	}

	@Override
	public String cfrClaseActual() {
		return "کلاس فعلی";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "پرترهٔ Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "خطا در بارگذاری پرتره";
	}

	public String noticiaLegalCFR() {
		return "این رابط کاربری گرافیکی (GUI) برای دیکامپایل افزونه‌ها به منظور کمک به کاربران در شناسایی علت خرابی‌های نرم‌افزاری طراحی شده است. "
				+ "با این حال، گاهی دیکامپایل افزونه‌ها ضروری است، اما کاربران باید مراقب باشند که از کد تولیدشده برای نقض قانون حق تکثیر استفاده نکنند. "
				+ "قبل از استفاده از هر کدی، بررسی مجوز افزونهٔ مربوطه توصیه می‌شود. علاوه بر این، بسیاری از افزونه‌ها کد منبع خود را به صورت رسمی ارائه می‌دهند، "
				+ "که معمولاً تمیزتر و قابل‌درک‌تر از کد دیکامپایل‌شده است. رعایت مالکیت فکری و مجوزهای استفاده، اصلی‌ترین ارزش در جامعهٔ توسعه‌دهندگان افزونه است. "
				+ "می‌توانید قانون فدرال حق تکثیر مکزیک را در این لینک مشاهده کنید: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Ley Federal de Derechos de Autor (اسپانیایی)</a> "
				+ "و نسخهٔ انگلیسی آن را اینجا ببینید: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (English)</a>. "
				+ "با توجه به اینکه شما در CurseForge هستید، لینک قانون حق تکثیر ایالات متحده را نیز فراهم کرده‌ایم: "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>. "
				+ "همچنین توصیه می‌شود کاربران قوانین جاری در کشور خود را بررسی کنند. "
				+ "این GUI فقط برای بررسی‌های ساده است؛ برای تحلیل پیشرفته، از انشعاب Enigma توسط FabricMC در "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a> استفاده کنید. اگر نیاز به ویرایش مستقیم فایل‌های JAR برای ایجاد پچ دارید (در صورت عدم دسترسی به کد منبع)، می‌توانید از Recaf در "
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">وب‌سایتش</a> استفاده کنید.";
	}

	@Override
	public String botonDescargarCfr() {
		return "دانلود CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "باز کردن پوشهٔ نصب";
	}

	@Override
	public String colorFondoPrincipal() {
		return "رنگ پس‌زمینهٔ اصلی";
	}

	@Override
	public String colorTextoBotonReset() {
		return "رنگ متن دکمهٔ بازنشانی";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "رنگ متن فیلد جستجو";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "رنگ متن منوی کشویی فیلتر";
	}

	@Override
	public String colorTextoRenderer() {
		return "رنگ متن رندرکننده";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "رنگ متن پوشش بارگذاری";
	}

	@Override
	public String colorBorde() {
		return "رنگ حاشیه";
	}

	@Override
	public String colorFondoRetrato() {
		return "رنگ پس‌زمینه در حالت پرتره";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "رنگ پیوند اشتراک‌گذاری";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "رنگ پس‌زمینهٔ فیلد اشتراک‌گذاری";
	}

	@Override
	public String rosaFondo() {
		return "صورتی پس‌زمینه";
	}

	@Override
	public String rosaSuave() {
		return "صورتی ملایم";
	}

	@Override
	public String moradoAcento() {
		return "بنفش برجسته";
	}

	@Override
	public String textoOscuro() {
		return "متن تیره";
	}

	@Override
	public String bordeSuave() {
		return "حاشیهٔ ملایم";
	}

	@Override
	public String fondoCampo() {
		return "پس‌زمینهٔ فیلد";
	}

	@Override
	public String fondoVistaPrevia() {
		return "پس‌زمینهٔ پیش‌نمایش";
	}

	@Override
	public String sintaxisConstructor() {
		return "رنگ نحو: سازنده";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "رنگ نحو: پیام راهنما";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "رنگ نحو: برچسب‌های HTML";
	}

	@Override
	public String colorFondoVentana() {
		return "رنگ پس‌زمینهٔ پنجره";
	}

	@Override
	public String colorPanel() {
		return "رنگ پانل";
	}

	@Override
	public String colorBotonTexto() {
		return "رنگ متن دکمه";
	}

	@Override
	public String colorCampo() {
		return "رنگ فیلد";
	}

	@Override
	public String colorBordeDestacado() {
		return "رنگ حاشیهٔ برجسته";
	}

	@Override
	public String colorSeleccionTexto() {
		return "رنگ پس‌زمینهٔ انتخاب متن";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "رنگ متن انتخاب‌شده";
	}

	@Override
	public String colorEstadoExito() {
		return "رنگ وضعیت: موفقیت";
	}

	@Override
	public String colorEstadoFallo() {
		return "رنگ وضعیت: شکست";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "رنگ وضعیت: لحظه‌ای";
	}

	@Override
	public String colorResultadoAnadido() {
		return "رنگ نتیجهٔ اضافه‌شده";
	}

	@Override
	public String colorResultadoEliminado() {
		return "رنگ نتیجهٔ حذف‌شده";
	}

	@Override
	public String colorBordeScroll() {
		return "رنگ حاشیهٔ نوار پیمایش";
	}

	@Override
	public String colorFondoPanel() {
		return "رنگ پس‌زمینهٔ پانل";
	}

	@Override
	public String colorBeigeListas() {
		return "بیژ فهرست‌ها";
	}

	@Override
	public String colorTextoListas() {
		return "رنگ متن فهرست‌ها";
	}

	@Override
	public String colorBordeListas() {
		return "رنگ حاشیهٔ فهرست‌ها";
	}

	@Override
	public String colorBotonFondo() {
		return "رنگ پس‌زمینهٔ دکمه";
	}

	@Override
	public String colorBordeBoton() {
		return "رنگ حاشیهٔ دکمه";
	}

	@Override
	public String colorDoradoTexto() {
		return "رنگ طلایی متن";
	}

	@Override
	public String colorPila() {
		return "رنگ ردیابی پشته (stack trace)";
	}

	@Override
	public String colorTextoPanel() {
		return "رنگ متن پانل";
	}

	@Override
	public String colorTextoNegro() {
		return "رنگ متن سیاه";
	}

	@Override
	public String colorTextoPrincipal() {
		return "رنگ متن اصلی";
	}

	@Override
	public String colorFondoResultados() {
		return "رنگ پس‌زمینهٔ نتایج";
	}

	@Override
	public String colorEstado() {
		return "رنگ وضعیت";
	}

	@Override
	public String colorTextoDescripcion() {
		return "رنگ متن توضیحات";
	}

	@Override
	public String colorTextoEstado() {
		return "رنگ متن وضعیت";
	}

	@Override
	public String colorTextoExtra() {
		return "رنگ متن اضافی";
	}

	@Override
	public String colorSeparador() {
		return "رنگ جداکننده";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "خطای بومی <code>StubRoutines::SafeFetch32</code> شناسایی شد. "
				+ "این مشکل در macOS با JDK 17.0.9 رخ می‌دهد و در JDK 17.0.10 یا جدیدتر رفع شده است. https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "خطای بومی SafeFetch32 در JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "JDK خود را به نسخهٔ 17.0.10 یا بالاتر (مثلاً 17.0.15) به‌روزرسانی کنید.";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "اگر از راه‌اندازی مانند MultiMC، Prism Launcher یا TLauncher استفاده می‌کنید، آن را طوری پیکربندی کنید که از JDK جدیدتری استفاده کند. "
				+ "برخی از آن‌ها از پیش JDK 17.0.15 را دارند.";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "افزونهٔ Spark نیز ممکن است به این خطا کمک کند. غیرفعال کردن موقت آن را در نظر بگیرید. https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "افزونهٔ MCEF (Chromium Embedded Framework) باعث قفل خاموش می‌شود.</b>" + "<ul>"
				+ "<li>MCEF در انتهای لاگ‌ها در حال راه‌اندازی است که معمولاً نشان‌دهندهٔ هَنگ شدن بازی در حین بارگذاری است.</li>"
				+ "<li>این افزونه به دلیل ایجاد crash در سیستم‌های Linux، macOS یا با نسخه‌های خاصی از Java شناخته شده است.</li>"
				+ "<li>همیشه خطای صریحی ظاهر نمی‌شود، اما بازی هرگز به منوی اصلی نمی‌رسد.</li>" + "</ul>"
				+ "<p>اگر به قابلیت مرورگر درون‌بازی (مثل نقشه‌های وب یا صفحات جاسازی‌شده) نیاز ندارید، این افزونه را حذف کنید.</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "مشکل راه‌اندازی MCEF (افزونهٔ مرورگر جاسازی‌شده)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "فایل افزونهٔ MCEF (فایلی که نامش شامل 'mcef' است) را از پوشهٔ 'mods' حذف کنید.";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "اگر واقعاً به آن نیاز دارید، مطمئن شوید که از نسخه‌ای استفاده می‌کنید که با سیستم‌عامل و نسخهٔ Minecraft شما سازگار است.";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تعارضی بین <b>OptiFine</b> و <b>Iris/Oculus</b> شناسایی شده است.</b>" + "<ul>"
				+ "<li>OptiFine رندرینگ Minecraft را به شیوه‌ای تغییر می‌دهد که با Iris یا Oculus ناسازگار است.</li>"
				+ "<li>خطای <code>MixinLevelRenderer failed injection check</code> از <code>mixins.iris.json</code> یا <code>mixins.oculus.json</code> نشأت می‌گیرد.</li>"
				+ "</ul>"
				+ "<p>این افزونه‌ها نمی‌توانند همزمان استفاده شوند. برای استفاده از shaders با Iris یا Oculus، OptiFine را حذف کنید.</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "تعارض بین OptiFine و Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "فایل OptiFine را از پوشهٔ 'mods' حذف کنید.";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "برای shaders مدرن، Iris یا Oculus را بدون OptiFine استفاده کنید.";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "تعارضی بین <b>ModernFix</b> و <b>OptiFine</b> شناسایی شده است.</b>" + "<ul>"
				+ "<li>ModernFix با OptiFine سازگار نیست، زیرا عملکردهای Forge را خراب کرده و راه‌اندازی را کند می‌کند.</li>"
				+ "<li>خود ModernFix هشدار می‌دهد: <i>\"Use of ModernFix with OptiFine is not supported\"</i>.</li>"
				+ "</ul>" + "<p>برای کارکرد صحیح بازی، باید یکی از این دو افزونه را حذف کنید.</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "تعارض بین ModernFix و OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "OptiFine یا ModernFix را از پوشهٔ 'mods' حذف کنید. این دو را نمی‌توان همزمان استفاده کرد.";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "اگر به بهینه‌سازی نیاز دارید، فقط از OptiFine استفاده کنید، یا ModernFix را با افزونه‌های سبک‌تری مانند FerriteCore یا EntityCulling جایگزین کنید.";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطا: کلید ثبت نامعتبر با نویسه‌های غیرمجاز.</b>" + "<ul>" + "<li><b>کلید شناسایی‌شده:</b> <code>"
				+ escapeHtml(clave) + "</code></li>"
				+ "<li>در Minecraft، تمام کلیدهای ثبت (برچسب‌ها، دستورها، پیشرفت‌ها و غیره) باید <b>کوچک‌شده</b> باشند و تنها از حروف، اعداد، زیرخط، خط‌تیره و اسلش استفاده کنند.</li>"
				+ "<li>این خطا معمولاً به دلیل یک افزونهٔ بدکدنویسی‌شده یا یک datapack معیوب رخ می‌دهد.</li>" + "</ul>"
				+ "<p><b>نکتهٔ مهم:</b> از ابزار <b>grepr</b> یا <b>fgrepr</b> در نوار کناری استفاده کنید و گزینهٔ <b>«جستجو در فایل‌های JAR»</b> را فعال کنید تا متوجه شوید کدام افزونه این کلید نامعتبر را دارد.</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "کلید ثبت با حروف بزرگ یا نویسه‌های نامعتبر";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "از 'grepr' یا 'fgrepr' همراه با «جستجو در فایل‌های JAR» برای یافتن افزونهٔ مقصر استفاده کنید.";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "اگر نتوانستید افزونه را شناسایی کنید، افزونه‌های اخیر را حذف کنید، به‌ویژه آن‌هایی که بلوک، آیتم یا ابزار اضافه می‌کنند.";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "خطا در بارگذاری افزونهٔ <b>"
				+ escapeHtml(modNombre) + "</b>.</b>" + "<ul>"
				+ "<li>این افزونه در راه‌اندازی یکی از مؤلفه‌هایش (مثل منوی پیکربندی) شکست خورد.</li>"
				+ "<li>این معمولاً به دلیل ناسازگاری با نسخهٔ Minecraft، Fabric یا سایر افزونه‌ها رخ می‌دهد.</li>"
				+ "</ul>" + "<p>اگر خطا ادامه یافت، افزونهٔ <b>" + escapeHtml(modNombre)
				+ "</b> را حذف یا به‌روزرسانی کنید.</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "خطای راه‌اندازی افزونه (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "افزونهٔ '" + modNombre + "' را از پوشهٔ 'mods' حذف کنید.";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "افزونهٔ '" + modNombre + "' را به نسخه‌ای سازگار با نصب شما به‌روزرسانی کنید.";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطایی مرتبط با افزونهٔ <b>En Garde!</b> شناسایی شد.</b>" + "<ul>"
				+ "<li>این افزونه مکانیک‌های رزم نزدیک (مثل پری، بلاک و غیره) را اضافه می‌کند.</li>"
				+ "<li>این خطا معمولاً به دلیل ناسازگاری با سایر افزونه‌های رزم (مثل Epic Fight، DualRiders و غیره) یا استفاده از نسخهٔ نامناسب برای Minecraft شما رخ می‌دهد.</li>"
				+ "</ul>"
				+ "<p>اگر از سیستم رزم پیشرفته استفاده نمی‌کنید، برای جلوگیری از تداخل، En Garde! را حذف کنید.</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "خطا در افزونهٔ En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "مطمئن شوید که از نسخهٔ En Garde! سازگار با نسخهٔ Minecraft و بارگذار شما (Fabric/Forge) استفاده می‌کنید.";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "اگر از افزونه‌های رزم دیگری (مثل Epic Fight، Caelus و غیره) استفاده می‌کنید، آن‌ها را غیرفعال کنید یا En Garde! را حذف نمایید تا از تداخل جلوگیری شود.";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطایی ناشی از افزونهٔ <b>IdleTweaks</b> شناسایی شد.</b>" + "<ul>"
				+ "<li>IdleTweaks سعی کرد کانال شبکه‌ای را آزاد کند که دیگر وجود ندارد (<code>Tried to release unknown channel</code>).</li>"
				+ "<li>این خطا معمولاً در نسخه‌های قدیمی افزونه یا هنگام استفاده در سرورهای بدپیکربندی‌شده رخ می‌دهد.</li>"
				+ "</ul>"
				+ "<p>IdleTweaks یک افزونهٔ رفاهی است، اما ممکن است باعث ناپایداری شود. به‌روزرسانی یا حذف آن را در نظر بگیرید.</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "خطای IdleTweaks (کانال شبکهٔ ناشناخته)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "IdleTweaks را به جدیدترین نسخه‌ای که با Minecraft شما سازگار است، به‌روزرسانی کنید.";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "اگر به آن نیازی ندارید، IdleTweaks را از پوشهٔ 'mods' حذف کنید.";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "خطای احراز هویت (HTTP 401) هنگام تلاش برای ورود به Minecraft شناسایی شد.</b>"
				+ "<p>این خطا <b>به ندرت علت مستقیم crash است</b>، اما نشان می‌دهد که از یک حساب غیرمجاز (نسخهٔ غیرقانونی) استفاده می‌کنید.</p>"
				+ "<p>کانال‌های پشتیبانی رسمی (پروژه‌های شرکتی، VTuberها، سازندگان modpack و غیره) <b>نمی‌توانند به شما کمک کنند</b> اگر از نسخهٔ غیرقانونی استفاده کنید، "
				+ "به دلیل محدودیت‌های قوانین چت، قراردادها، توافقات با Mojang/Microsoft یا سیاست‌های اعتبار.</p>"
				+ "<p>این بررسی را می‌توان در <b>تنظیمات شرکتی</b> تشخیص‌دهنده غیرفعال کرد. "
				+ "اخطار: سیستم تشخیص غیرقانونی بودن <b>کامل نیست</b> و ممکن است در محیط‌های توسعه، با اینترنت ناپایدار یا استفاده از راه‌اندازهای اصلاح‌شده فعال شود.</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>حقوق میراندا در صورت تلاش برای پیوستن به پشتیبانی با وجود این هشدار:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Minecraft غیرقانونی";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "غیرفعال‌سازی بررسی غیرقانونی بودن";
	}

	@Override
	public String comprarMC() {
		return "خرید Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "شما از راه‌انداز <code>" + id
				+ "</code> استفاده می‌کنید که <b>در لیست راه‌اندازهای توصیه‌شده نیست</b>.</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>اگرچه ممکن است کار کند، اما راه‌اندازهای غیرتوصیه‌شده معمولاً باعث می‌شوند:</p>" + "<ul>"
				+ "<li>نصب‌های خراب‌شدهٔ افزونه‌ها یا اپلیکیشن.</li>"
				+ "<li>بازی شروع نشود یا بدون خطای مشخص هَنگ کند.</li>"
				+ "<li>ساختار غیرمعمول پوشه‌ها (تشخیص را دشوار می‌کند).</li>"
				+ "<li>رفتار غیرقابل‌پیش‌بینی با جاوا، حافظه یا افزونه‌ها.</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "برای تجربهٔ بهتر، یکی از راه‌اندازهای توصیه‌شدهٔ زیر را استفاده کنید:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "راه‌انداز غیرتوصیه‌شده";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "به راه‌اندازی از لیست توصیه‌شده تغییر دهید.";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "شما از یک <b>راه‌انداز نامناسب</b> استفاده می‌کنید: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>راه‌اندازهای نامناسب ممکن است باعث شوند:</p>" + "<ul>"
				+ "<li>نصب‌های خراب‌شدهٔ اپلیکیشن یا افزونه‌ها.</li>"
				+ "<li>بازی شروع نشود یا به‌صورت ساکت شکست بخورد.</li>"
				+ "<li>سازمان‌دهی غیرمعمول فایل‌ها (اشکال‌زدایی را دشوار می‌کند).</li>"
				+ "<li>عدم اطمینان از نحوهٔ مدیریت افزونه‌ها، جاوا یا حافظه توسط آن.</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "استفاده از یکی از راه‌اندازهای زیر به‌شدت توصیه می‌شود:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "راه‌انداز نامناسب";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "برای دریافت پشتیبانی، به راه‌انداز توصیه‌شده تغییر دهید.";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "برای این محیط، افزونه‌های توصیه‌شده وجود ندارند.</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "افزونه‌های توصیه‌شده گم شده‌اند";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "برای تجربهٔ بهینه، افزونه‌های توصیه‌شده را نصب کنید.";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "افزونه‌های نامناسب در نصب شما شناسایی شدند.</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "افزونه‌های نامناسب شناسایی شدند";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "برای جلوگیری از مشکلات، افزونه‌های نامناسب را حذف کنید.";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "دستکاری غیرمجاز در فایل‌های حیاتی شناسایی شد. یا فایل‌ها را دستی ویرایش کرده‌اید یا از یک راه‌انداز نامعتبر استفاده می‌کنید.</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "دستکاری شناسایی شد";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "برای بازگرداندن یکپارچگی، فایل‌های اصلی را مجدداً نصب کنید.";
	}

	@Override
	public String configuracionCorporativa() {
		return "تنظیمات شرکتی";
	}

	@Override
	public String idiomaRespaldo() {
		return "زبان پشتیبان";
	}

	@Override
	public String buscardorHabilitado() {
		return "فعال‌سازی جستجوگر";
	}

	@Override
	public String nombreHerramienta() {
		return "نام ابزار";
	}

	@Override
	public String condenarPirateria() {
		return "محکومیت دزدی نرم‌افزاری";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "راه‌اندازهای توصیه‌شده";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "راه‌اندازهای نامناسب";
	}

	@Override
	public String modsRecomendados() {
		return "افزونه‌های توصیه‌شده";
	}

	@Override
	public String modsDesaconsejados() {
		return "افزونه‌های نامناسب";
	}

	@Override
	public String antiTamper() {
		return "ضد دستکاری";
	}

	@Override
	public String proximamente() {
		return "به زودی";
	}

	@Override
	public String informacion() {
		return "اطلاعات";
	}

	@Override
	public String errorCargandoImagen() {
		return "خطا در بارگذاری تصویر";
	}

	@Override
	public String configuracionBasica() {
		return "تنظیمات پایه";
	}

	@Override
	public String funcionalidades() {
		return "امکانات";
	}

	@Override
	public String derechosMiranda() {
		return "حقوق میراندا (به‌شدت توصیه می‌شود)";
	}

	@Override
	public String gestionVerificaciones() {
		return "مدیریت تأییدها";
	}

	@Override
	public String idVerificacion() {
		return "شناسه";
	}

	@Override
	public String nombreVerificacion() {
		return "نام";
	}

	@Override
	public String codigoVerificacion() {
		return "کد";
	}

	@Override
	public String documentacionVerificacion() {
		return "مستندات";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "تأییدهای فعال‌شده:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "تأییدهای غیرفعال‌شده:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "غیرفعال‌سازی همهٔ موارد غیرشرکتی";
	}

	@Override
	public String verCodigo() {
		return "مشاهدهٔ کد";
	}

	@Override
	public String verDocumentacion() {
		return "مشاهدهٔ مستندات";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "تأییدی را برای غیرفعال‌سازی انتخاب کنید.";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "تأییدی را برای فعال‌سازی انتخاب کنید.";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "%d تأیید که برای استفادهٔ شرکتی توصیه نمی‌شوند، غیرفعال شدند.";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "تأیید غیرشرکتی برای غیرفعال‌سازی وجود ندارد.";
	}

	@Override
	public String operacionCompletada() {
		return "عملیات کامل شد";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "ما تو را دوست داریم، Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "رنگ تأیید شرکتی";
	}

	@Override
	public String nombreLanzador() {
		return "نام راه‌انداز";
	}

	@Override
	public String motivo() {
		return "دلیل";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "راه‌اندازهای نامناسب";
	}

	@Override
	public String moverADesaconsejados() {
		return "غیرتوصیه‌شده کردن";
	}

	@Override
	public String moverARecomendados() {
		return "توصیه‌شده کردن";
	}

	@Override
	public String guardarCambios() {
		return "ذخیرهٔ تغییرات";
	}

	@Override
	public String cancelar() {
		return "لغو";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "لطفاً راه‌اندازی را برای جابه‌جایی انتخاب کنید.";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "تغییرات با موفقیت ذخیره شد!";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEs(String nombreLanzador) {
		return "Este lanzador no es recomendado debido a problemas de seguridad y estabilidad conocidos.";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEn(String nombreLanzador) {
		return "This launcher is not recommended due to known security and stability issues.";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoPt(String nombreLanzador) {
		return "Este lançador não é recomendado devido a problemas conhecidos de segurança e estabilidade.";
	}

	@Override
	public String razones() {
		return "دلایل";
	}

	@Override
	public String agregarLanzador() {
		return "افزودن راه‌انداز";
	}

	@Override
	public String quitarLanzador() {
		return "حذف راه‌انداز";
	}

	@Override
	public String editarRazones() {
		return "ویرایش دلایل";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "راه‌اندازی را برای حذف انتخاب کنید.";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "راه‌اندازی را برای ویرایش انتخاب کنید.";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "ویرایش دلایل برای " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "افزودن زبان جدید";
	}

	@Override
	public String aceptar() {
		return "تأیید";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "زبان را انتخاب کنید";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "این راه‌اندازها همان‌هایی هستند که CrashDetector به عنوان گزینه‌های خوب پیشنهاد می‌کند.";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "نتیجهٔ صحیح";
	}

	public String modsNoRecomendados() {
		return "افزونه‌های نامناسب";
	}

	public String agregarMod() {
		return "افزودن افزونه";
	}

	public String quitarMod() {
		return "حذف افزونه";
	}

	public String modId() {
		return "شناسهٔ افزونه / نام JBoss Modules";
	}

	public String rutaMod() {
		return "مسیر / فایل افزونه";
	}

	public String errorDebeIndicarMod() {
		return "باید حداقل شناسهٔ افزونه یا مسیر آن را مشخص کنید.";
	}

	public String modsNoRecomendadosAviso() {
		return "در اینجا می‌توانید افزونه‌های نامناسب را ثبت کنید تا CrashDetector در صورت نصب بودن آن‌ها، آن‌ها را شناسایی کند.";
	}

	@Override
	public String anularNormal() {
		return "لغو حالت عادی";
	}

	@Override
	public String anularNormalDescripcion() {
		return "CrashDetector باید حتی اگر crash واقعی رخ ندهد، هشدار دهد.";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "افزونه‌هایی را که CrashDetector توصیه می‌کند ثبت کنید. اگر وجود نداشته باشند، CrashDetector ممکن است هشدار دهد.";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "اگر تصمیم گرفتید هشدار ضد دزدی نرم‌افزاری را فعال کنید، به عنوان یک اقدام پیشگیرانه توصیه می‌شود "
				+ "حقوق فرد درخواست‌کنندهٔ پشتیبانی را در اینجا تعریف کنید.\n\n"

				+ "برخلاف باور رایج، بسیاری از جوامع و کانال‌های محبوب پشتیبانی "
				+ "برای ارائهٔ کمک، فعال‌سازی هشدارهای ضد دزدی را الزامی نمی‌دانند. با این حال، "
				+ "مستندسازی این حقوق در صورتی که فردی به هر حال به کانال پشتیبانی دسترسی پیدا کند، مفید خواهد بود.\n\n"

				+ "می‌توانید از سند رسمی مانند «کتابچهٔ حقوق پایهٔ بازداشت‌شده» در مکزیک الهام بگیرید:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "همچنین از اصول حقوقی مشابهی که در کشورهای دیگر از جمله "
				+ "ایالات متحده، فدراسیون روسیه، جمهوری خلق چین، جمهوری اسلامی ایران و "
				+ "جمهوری دموکراتیک خلق کره استفاده می‌شود.\n\n"

				+ "نمونه‌هایی از حقوقی که می‌توان در نظر گرفت عبارتند از:\n"
				+ "• حق عدم ارائهٔ اطلاعات غیرضروری برای پشتیبانی، مانند راه‌انداز استفاده‌شده، "
				+ "نام کاربری یا UUID.\n" + "• حق عدم خوداتهام.\n"
				+ "• حق رد پاسخ به سؤالاتی که برای حل مشکل ضروری نیستند.\n" + "• حق دریافت راهنمایی درون چت.\n"
				+ "• حق استفاده از قابلیت ناشناس‌سازی لاگ‌های داخلی CrashDetector.\n\n"

				+ "این متن از محتوای HTML پشتیبانی می‌کند.";
	}

	@Override
	public String editar() {
		return "ویرایش";
	}

	@Override
	public String advertenciaHashLento() {
		return "هشدار: افزودن تعداد زیادی فایل بزرگ ممکن است باعث شود بررسی چند دقیقه طول بکشد. CrashDetector باید قبل از ادامه، هش هر فایل را محاسبه کند. توصیه می‌شود تنها فایل‌های ضروری را محافظت کنید.";
	}

	@Override
	public String agregarArchivo() {
		return "افزودن فایل";
	}

	@Override
	public String agregarCarpeta() {
		return "افزودن پوشه";
	}

	@Override
	public String quitar() {
		return "حذف";
	}

	@Override
	public String rutaArchivo() {
		return "مسیر فایل";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "مسیر انتخاب‌شده خارج از دایرکتوری فعلی بازی است. تنها فایل‌ها و پوشه‌هایی که در دایرکتوری فعلی یا زیرپوشه‌های آن قرار دارند، مجاز هستند.";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>"
				+ "نظرات و توضیحات Sylent Bell لزوماً با ما هم‌خوانی ندارد؛ "
				+ "فقط فکر کردیم جالب باشد که او را اینجا بگذاریم. CrashDetector سکولار است." + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "افزونهٔ GML (Groovy ModLoader) به این تغییرات نیاز دارد و رایج‌ترین منشأ این مشکل است.</b>";
	}

	@Override
	public String mensajeIndependenteFlywheel(Set<String> mods) {
		StringBuilder listaMods = new StringBuilder();
		if (!mods.isEmpty()) {
			for (String mod : mods) {
				listaMods.append("<li>").append(mod).append("</li>");
			}
		}

		String mensaje = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "استفاده از <i>Flywheel</i> شناسایی شد.</b>"
				+ "<p><b>Flywheel منسوخ‌شده (deprecated) است</b> و نباید در نسخه‌های مدرن استفاده شود.</p>"
				+ "<p>نسخه‌های فعلی <b>Create</b> <b>از پیش Flywheel را شامل می‌شوند</b>، بنابراین نصب جداگانهٔ آن "
				+ "باعث تداخل سازگاری و خطاهای بارگذاری می‌شود.</p>"
				+ "<p>برخی افزونه‌هایی که به‌صورت صریح به Flywheel وابسته‌اند ممکن است "
				+ "<b>کار نکنند</b> یا <b>ناپایدار عمل کنند</b>. "
				+ "در برخی موارد پیشرفته، این افزونه‌ها ممکن است با <b>ویرایش دستی فایل <code>mods.toml</code></b> "
				+ "برای تنظیم محدودهٔ نسخه‌ها کار کنند، هرچند این کار <b>توصیه نمی‌شود</b>.</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>افزونه‌های شناسایی‌شده که به Flywheel ارجاع می‌دهند:</b></p>" + "<ul>"
								+ listaMods.toString() + "</ul>")
				+ "<p>راه‌حل پیشنهادی، <b>حذف Flywheel</b> و استفادهٔ انحصاری از نسخهٔ داخلی Create است.</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Flywheel مستقل";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خطایی مرتبط با افزونهٔ <i>Floral Enchantments</i> شناسایی شد.</b>"
				+ "<p>این crash به دلیل خطا در داخل افزونه هنگام پردازش داده‌های بازی رخ داده و "
				+ "در حین اجرا باعث <b>NullPointerException</b> می‌شود.</p>"
				+ "<p>این مشکل معمولاً با به‌روزرسانی یا حذف افزونه برطرف می‌شود.</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "خطای Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "شما هم نسخهٔ NeoForge و هم نسخهٔ معمولی MixinExtras را دارید. اگر از MinecraftForge استفاده می‌کنید، می‌توانید با نصب <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>این لینک</a> مشکل را حل کنید.</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خطایی در سایه‌های زمین هنگام استفاده از شیدرها (shaders) با Iris شناسایی شد.</b>"
				+ "<p>این مشکل در حین رندر زمین رخ می‌دهد.</p>"
				+ "<p>توصیه می‌شود <b>بازی را بدون شیدر امتحان کنید</b> یا کیفیت گرافیکی را کاهش دهید، "
				+ "به‌ویژه در تنظیمات <b>Ultra</b>.</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "سایه‌های زمین (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "یک تیک سرور بسیار طولانی شناسایی شد.</b>"
				+ "<p>این نشان می‌دهد که بازی برای مدت طولانی در یک تیک گیر کرده است.</p>"
				+ "<p>توصیه می‌شود <b>thread dump</b> ایجادشده در لاگ را برای شناسایی علت بررسی کنید.</p>"
				+ "<p><b>تحلیل Stack Trace</b> می‌تواند به شما در یافتن منشأ گیر کمک کند.</p>"
				+ "<p>همچنین، دکمهٔ <b>مشاهده در لاگ</b> مودهای احتمالی مسئول را به رنگ قرمز برجسته می‌کند، "
				+ "و همچنین ورودی‌هایی که با <code>$modid$</code> احاطه شده‌اند (که معمولاً منشأ مشکل را نشان می‌دهند). برای اسکن بلادرنگ، استفاده از نمونه‌بردار CPU در VisualVM توصیه می‌شود. مطمئن شوید سرور یا رایانهٔ شما قدرت کافی برای مدیریت مودهایی که استفاده می‌کنید دارد — ممکن است همهٔ مودها به‌درستی کار کنند، اما تعدادشان بیش از حد باشد.</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "تیک طولانی سرور";
	}

	@Override
	public String tituloLFPDPPP() {
		return "個人が保有する個人情報の保護に関する連邦法";
	}

	@Override
	public String aceptarPermanentemente() {
		return "永続的に同意";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "韓国語翻訳には、法律を遵守するために避けるべき南部のスラング用語が含まれています。" + "外国語、特に南側由来の言葉の使用は、「平壌文化語保護法」により厳しく禁止されています。";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "詳細については、法令の公式文書をご参照ください："
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>平壌文化語保護法</a>";
	}

	public String leerLeyCompleta() {
		return "全文を読む";
	}

	public String errorAbriendoEnlace() {
		return "リンクを開く際にエラーが発生しました";
	}

	public String actaProteccionIdiomaCultural() {
		return "قانون حمایت از زبان فرهنگی پیونگ‌یانگ";
	}

	@Override
	public String canarioTitulo() {
		return "قناری دستور قضایی";
	}

	@Override
	public String canario1984Titulo() {
		return "۱۹۸۴ — نظارت‌گر نظارتی";
	}

	@Override
	public String revisar() {
		return "بررسی";
	}

	@Override
	public String cerrar() {
		return "بستن";
	}

	@Override
	public String canarioTodoSeguro() {
		return "همهٔ سرویس‌ها وضعیت امن را گزارش کرده‌اند.";
	}

	@Override
	public String canarioComprometido(int c) {
		return "هشدار: " + c + " سرویس، وضعیت ناامن را گزارش کرده‌اند.";
	}

	@Override
	public String colorAlerta() {
		return "رنگ هشدار";
	}

	public String opcionesMunidiales() {
		return "گزینه‌های مونیدیال";
	}

	public String consentimientoLFPDPPP() {
		return "رضایت‌نامهٔ LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "فعال‌سازی انتقال توکن دسترسی در Handoff برای ReLauncher (توصیه نمی‌شود).";
	}

	public String consolaDesarrollo() {
		return "کنسول توسعه";
	}

	public String mundial() {
		return "جهانی";
	}

	public String ningun() {
		return "هیچ‌کدام";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "کنسول توسعه‌دهنده";
	}

	public String bajar() {
		return "دانلود";
	}

	public String logsSoporte() {
		return "لاگ‌های پشتیبانی";
	}

	public String detenerProceso() {
		return "توقف فرآیند";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "کپی انتخاب‌شده";
	}

	public String seleccionarTodo() {
		return "انتخاب همه";
	}

	public String copiarTodo() {
		return "کپی همه";
	}

	public String guardarTodoComoArchivo() {
		return "ذخیرهٔ همه به عنوان فایل";
	}

	public String obtenerEnlaceSoporte() {
		return "دریافت لینک پشتیبانی";
	}

	public String borrarTodo() {
		return "پاک‌سازی همه";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "رنگ پس‌زمینهٔ کنسول";
	}

	public String colorTextoConsola() {
		return "رنگ متن کنسول";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "رضایت تأیید شد.\nامکان اشتراک‌گذاری لاگ‌ها در اینجا پیاده‌سازی خواهد شد.";
	}

	@Override
	public String usarSakuraOriginal() {
		return "استفاده از تصویر اصلی Sakura Riddle";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "«قناری دستور قضایی» (warrant canary) یک مکانیزم شفافیت است.\n\n"
				+ "تا زمانی که این پیام وجود دارد و سرویس‌ها به عنوان امن نمایش داده می‌شوند، "
				+ "به این معناست که پروژه دستور قضایی محرمانه، "
				+ "درخواست سانسور یا درخواست قانونی نظارتی دریافت نکرده است.\n\n"
				+ "اگر هر یک از قناری‌ها ناپدید شود یا به عنوان ناامن علامت‌گذاری شود، "
				+ "این نشان‌دهندهٔ تغییری قانونی است.\n\n" + "این پنل تمام قناری‌های ثبت‌شده در سیستم را بررسی کرده و "
				+ "وضعیت فعلی آن‌ها را نمایش می‌دهد.\n\n" + "برای به‌روزرسانی وضعیت‌ها، «بررسی» را فشار دهید.";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "همهٔ گزینه‌ها به مقادیر پیش‌فرض بازنشانی شوند؟";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "بدون گزینه";
	}

	@Override
	public String seleccionaColor() {
		return "انتخاب رنگ";
	}

	@Override
	public String botonMostrarGUI() {
		return "نمایش GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "ذخیرهٔ همه";
	}

	@Override
	public String botonRestablecerTodo() {
		return "بازنشانی همه";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms بارگذاری نشده است";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خطایی هنگام دسترسی به رابط برنامه‌نویسی LuckPerms شناسایی شد.</b>"
				+ "<p>این پیام نشان می‌دهد که <b>LuckPerms در زمانی که یک افزونهٔ دیگر سعی در استفاده از آن کرد، بارگذاری نشده بود</b>.</p>"
				+ "<p><b>دلایل احتمالی:</b></p>" + "<ul>"
				+ "<li>افزونهٔ <b>LuckPerms نصب نشده است</b> یا <b>در هنگام راه‌اندازی با شکست مواجه شده است</b>.</li>"
				+ "<li>افزونهٔ دیگری در حال دسترسی به LuckPerms <b>خیلی زود</b> یا <b>به‌صورت نادرست</b> است.</li>"
				+ "</ul>" + "<p>توصیه می‌شود با استفاده از پیوند، <b>کنسول را بررسی کنید</b> تا "
				+ "افزونه‌ای که LuckPerms را فراخوانی می‌کند را شناسایی کرده و سازگاری آن را تأیید نمایید.</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "بستهٔ سایه‌پرداز Iris بارگذاری نشده است";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "ناشناخته" : shaderpack;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خطایی هنگام بارگیری بستهٔ سایه‌پرداز با Iris/Oculus شناسایی شد.</b>"
				+ "<p><b>بستهٔ سایه‌پرداز تحت تأثیر:</b> " + nombre + "</p>"
				+ "<p>ماینکرفت نتوانست فایل بستهٔ سایه‌پرداز را باز کند (FileSystemNotFoundException).</p>"
				+ "<p><b>راه‌حل‌های احتمالی:</b></p>" + "<ul>"
				+ "<li>بررسی کنید که بستهٔ سایه‌پرداز به‌درستی در پوشهٔ <b>shaderpacks</b> نصب شده باشد.</li>"
				+ "<li>بستهٔ سایه‌پرداز را دوباره بارگیری کنید، زیرا ممکن است فایل خراب شده باشد.</li>"
				+ "<li>اگر مشکل ادامه یافت، برای بازنشانی پیکربندی Iris، فایل <b>config/iris.properties</b> را حذف کنید.</li>"
				+ "</ul>" + "<p>پس از اعمال تغییرات، بازی را دوباره راه‌اندازی کنید.</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "امکان نوشتن فایل پیکربندی وجود ندارد";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "ناشناخته" : ruta;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "هنگام ذخیرهٔ فایل پیکربندی خطایی رخ داد.</b>" + "<p><b>فایل تحت تأثیر:</b> " + archivo + "</p>"
				+ "<p>ماینکرفت نتوانست فایل را با استفاده از نوشتن اتمی (REPLACE_ATOMIC) بنویسد.</p>"
				+ "<p><b>این معمولاً به دلایل زیر رخ می‌دهد:</b></p>" + "<ul>"
				+ "<li>مجوزهای نادرست روی پوشه یا فایل.</li>" + "<li>فایل به‌صورت فقط‌خواندنی علامت‌گذاری شده است.</li>"
				+ "<li>برنامهٔ دیگری (آنتی‌ویروس، پشتیبان‌گیری، ویرایشگر) در حال قفل کردن فایل است.</li>" + "</ul>"
				+ "<p><b>توصیه‌ها:</b></p>" + "<ul>" + "<li>بررسی کنید که مجوز نوشتن در پوشه را دارید.</li>"
				+ "<li>ویژگی فقط‌خواندنی را از فایل بردارید.</li>"
				+ "<li>برنامه‌هایی را که ممکن است از این فایل استفاده کنند، ببندید.</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "دسترسی هنگام ایجاد پشتیبان رد شد";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "ناشناخته" : origen;
		String dst = backup == null || backup.isEmpty() ? "ناشناخته" : backup;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خطای مجوز هنگام ایجاد پشتیبان از فایل پیکربندی رخ داد.</b>" + "<p><b>فایل اصلی:</b> " + src + "</p>"
				+ "<p><b>فایل پشتیبان:</b> " + dst + "</p>"
				+ "<p>سیستم‌عامل در حین ذخیرهٔ فایل، دسترسی را مسدود کرد.</p>"
				+ "<p><b>این معمولاً به دلایل زیر رخ می‌دهد:</b></p>" + "<ul>" + "<li>مجوزهای ناکافی روی پوشه.</li>"
				+ "<li>فایل به‌صورت فقط‌خواندنی علامت‌گذاری شده است.</li>"
				+ "<li>برنامهٔ دیگری (آنتی‌ویروس، ابزار همگام‌سازی، ویرایشگر) در حال استفاده از فایل است.</li>"
				+ "</ul>" + "<p><b>توصیه‌ها:</b></p>" + "<ul>" + "<li>مجوزهای پوشهٔ <b>config</b> را بررسی کنید.</li>"
				+ "<li>برنامه‌هایی را که ممکن است به فایل دسترسی داشته باشند، ببندید.</li>"
				+ "<li>سعی کنید لانچر یا ماینکرفت را به‌عنوان مدیر اجرا کنید.</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		return "فعال‌سازی کنسول";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>ابزارهای اشکال‌زدایی</b><br><br>"
				+ "در اینجا می‌توانید ویژگی‌های پیشرفته‌ای را برای اشکال‌زدایی CrashDetector و بازی‌های خود فعال کنید.<br><br>"
				+ "برای دریافت اطلاعات دقیق، ردیابی‌ها و تشخیص‌های لازم در حین تحلیل، فعال‌سازی کنسول توسعه توصیه می‌شود.<br><br>"
				+ "اگر نیاز دارید سرور چندنفره را در حالت آنلاین آزمایش کنید، ممکن است لازم باشد انتقال توکن دسترسی (token de acceso) به فرآیند CrashDetector را از تنظیمات حریم خصوصی مجاز کنید. "
				+ "این کار معمولاً در موارد دیگر <b>توصیه نمی‌شود</b>.<br><br>"
				+ "دستورالعمل کامل: <a href='https://example.com'>پیوند!</a>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "ناسازگاری: Simple Clouds در برابر شیدرها";
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ناسازگاری بین Simple Clouds و شیدرها شناسایی شد.</b>"
				+ "<p>هنگامی که Distant Horizons نصب باشد، Simple Clouds با مودهای سایه (Iris/Oculus) سازگار نیست.</p>"
				+ "<p><b>گزینه‌های پیشنهادی:</b></p>" + "<ul>"
				+ "<li>اگر می‌خواهید از شیدرها استفاده کنید، <b>Simple Clouds</b> را حذف کنید.</li>"
				+ "<li>یا اگر ترجیح می‌دهید Simple Clouds را نگه دارید، <b>Iris یا Oculus</b> را حذف نصب کنید.</li>"
				+ "</ul>" + "<p>این محدودیت از خود مود Simple Clouds نشأت می‌گیرد و بدون تغییر کد آن قابل حل نیست.</p>";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "رنگ دکمه نوار کناری";
	}

	@Override
	public String profilerTitulo() {
		return "تحلیلگر عملکرد";
	}

	@Override
	public String profilerDescripcion() {
		return "ابزار تحلیل عملکرد مبتنی بر ابزار دقیق‌سازی و نمونه‌برداری.";
	}

	@Override
	public String profilerIniciar() {
		return "شروع";
	}

	@Override
	public String profilerDetener() {
		return "توقف";
	}

	@Override
	public String profilerLimpiar() {
		return "پاک‌سازی";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "تحلیلگر عملکرد شروع شد.";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "تحلیلگر عملکرد متوقف شد.";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "نمونه از رشته دریافت شد: " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "نمونه‌برداری دوره‌ای از پشته‌ها برای شناسایی گلوگاه‌ها و قفل‌ها.";
	}

	@Override
	public String entrarAlJuego() {
		return "ورود به بازی";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "مسیر نامعتبر: شامل کاراکترهای غیرمجاز";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خطای مسیر سیستم شناسایی شد.</b>"
				+ "<p>ماينکرفت به دلیل وجود کاراکترهای غیرقانونی در نام یک پوشه، اجرا نشد.</p>"
				+ "<p>سیستم یک کاراکتر نامعتبر در مسیر شناسایی کرد (مثلاً «:» یا سایر نمادهای ویژه).</p>"
				+ "<p><b>راه‌حل پیشنهادی:</b></p>" + "<ul>" + "<li>نام پوشهٔ نمونه یا پروفایل را تغییر دهید.</li>"
				+ "<li>فقط از کاراکترهای پایهٔ ASCII (A-Z، a-z، 0-9) استفاده کنید.</li>"
				+ "<li>از علائم تشدید، نمادهای ویژه، فاصله‌های مشکل‌ساز و ایموجی‌ها استفاده نکنید.</li>" + "</ul>"
				+ "<p>مثال معتبر: <b>MiInstancia1</b></p>"
				+ "<p>مثال نامعتبر: <b>Instancia🔥</b> یا <b>Instancia:Mod</b></p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "خرابی: Twilight Forest + درایورهای اینتل";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خطای سایه‌پرداز Twilight Forest با GPU اینتل شناسایی شد.</b>"
				+ "<p>این خطا مربوط به درایورهای گرافیکی اینتل هنگام بارگیری سایه‌پردازهای مود Twilight Forest است.</p>"
				+ "<p>این خرابی درون درایور (igxelpicd64) رخ می‌دهد و مشکل مستقیم مود یا ماینکرفت نیست.</p>"
				+ "<p><b>راه‌حل‌های پیشنهادی:</b></p>" + "<ul>"
				+ "<li>درایورهای اینتل را به آخرین نسخهٔ موجود به‌روزرسانی کنید.</li>"
				+ "<li>به‌طور خاص نسخه‌های 31.0.101.8331 یا 31.0.101.8247 WHQL را امتحان کنید که طبق گزارش‌ها این مشکل را ندارند.</li>"
				+ "</ul>" + "<p>پیگیری رسمی مشکل:</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>نکته:</b> برخی GPUهای قدیمی‌تر اینتل ممکن است به‌روزرسانی‌هایی برای رفع این مشکل دریافت نکنند.</p>";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "فورج: ارائه‌دهندهٔ زبان بارگیری نشد";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "ارائه‌دهندهٔ ناشناخته" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "فورج نتوانست یک ارائه‌دهندهٔ زبان را بارگیری کند.</b>"
				+ "<p>هنگام مقداردهی اولیهٔ IModLanguageProvider خطایی رخ داد.</p>" + "<p><b>ارائه‌دهندهٔ ناموفق:</b> "
				+ providerTexto + "</p>" + "<p>این مشکل معمولاً در موارد زیر رخ می‌دهد:</p>" + "<ul>"
				+ "<li>وابستگی مورد نیاز وجود ندارد (مثلاً Kotlin for Forge).</li>"
				+ "<li>نسخهٔ مود با نسخهٔ فورج شما سازگار نیست.</li>" + "<li>فایل مود خراب است.</li>" + "</ul>"
				+ "<p><b>راه‌حل‌های پیشنهادی:</b></p>" + "<ul>" + "<li>مود مربوطه را دوباره نصب کنید.</li>"
				+ "<li>بررسی کنید که همهٔ وابستگی‌های آن نصب شده باشند.</li>"
				+ "<li>مطمئن شوید که از نسخه‌های سازگار با فورج فعلی خود استفاده می‌کنید.</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "خرابی: Lets Do Compat (اعتراض RecipeManager)";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خرابی در Lets Do Compat (interceptApply) شناسایی شد.</b>" + "<p>این خطا درون تبدیلی از متد "
				+ "<b>RecipeManager.interceptApply</b> که توسط Lets Do Compat انجام شده، رخ می‌دهد.</p>"
				+ "<p>این معمولاً نشان‌دهندهٔ موارد زیر است:</p>" + "<ul>"
				+ "<li>ناسازگاری بین Lets Do Compat و مود دیگری که دستورها (recipes) را تغییر می‌دهد.</li>"
				+ "<li>نسخهٔ نامناسب برای نسخهٔ ماينکرفت شما.</li>" + "<li>تضاد بین مبدل‌ها (mixin/coremod).</li>"
				+ "</ul>" + "<p><b>راه‌حل‌های پیشنهادی:</b></p>" + "<ul>"
				+ "<li>برای تأیید تداخل، Lets Do Compat را به‌صورت موقت حذف کنید.</li>" + "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI: خرابی در گروه آیتم (پلاگین ناسازگار)";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "پلاگین ناشناخته" : String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "JEI هنگام ساخت یک گروه آیتم، خرابی را شناسایی کرد.</b>"
				+ "<p>یک یا چند پلاگین هنگام تولید لیست مواد توسط JEI باعث خطا شدند.</p>"
				+ "<p><b>گروه‌ها/پلاگین‌های تحت تأثیر:</b> " + listaPlugins + "</p>"
				+ "<p>این مشکل معمولاً در موارد زیر رخ می‌دهد:</p>" + "<ul>"
				+ "<li>پلاگین JEI به‌درستی پیاده‌سازی نشده یا قدیمی است.</li>"
				+ "<li>ناسازگاری با نسخهٔ فعلی JEI وجود دارد.</li>"
				+ "<li>از Fabric API استفاده می‌شود و یک مود، Item Group خود را به‌صورت نادرست ثبت کرده است.</li>"
				+ "</ul>" + "<p><b>راه‌حل‌های پیشنهادی:</b></p>" + "<ul>"
				+ "<li>JEI و مودهای ذکرشده را به‌روزرسانی کنید.</li>"
				+ "<li>برای تأیید تداخل، پلاگین‌های تحت تأثیر را به‌صورت موقت حذف کنید.</li>"
				+ "<li>خطا را به توسعه‌دهندهٔ مود مربوطه گزارش دهید.</li>" + "</ul>"
				+ "<p>آیتم‌های این گروه‌ها تا زمان رفع مشکل در لیست مواد ظاهر نخواهند شد.</p>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "نسخهٔ نامعتبر مود (SemVer)";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "ناشناخته" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "مود یافت نشد" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "نسخهٔ نامعتبر مود شناسایی شد.</b>" + "<p>نسخهٔ <b>" + v
				+ "</b> با قالب معتبر SemVer سازگار نیست.</p>"
				+ "<p>این خطا نشان‌دهندهٔ پیش‌انتشار خالی است (با '+' تمام می‌شود).</p>" + "<p><b>مود مسئول:</b><br>"
				+ u + "</p>" + "<p><b>راه‌حل پیشنهادی:</b></p>" + "<ul>"
				+ "<li>فایل مود را ویرایش کرده و نسخه را اصلاح کنید.</li>"
				+ "<li>اگر متادیتای بعدی وجود ندارد، '+' پایانی را حذف کنید.</li>"
				+ "<li>مود را به نسخهٔ اصلاح‌شده به‌روزرسانی کنید.</li>" + "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS: دسترسی غیرمجاز بین ماژول‌ها";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "دسترسی غیرمجاز بین ماژول‌ها (JPMS) شناسایی شد.</b>"
				+ "<p>سیستم ماژول جاوا (JPMS) دسترسی بین کلاس‌ها را مسدود کرد.</p>"
				+ "<p><b>کلاس در حال تلاش برای دسترسی:</b><br>" + claseOrigen + " (ماژول: " + moduloOrigen + ")</p>"
				+ "<p><b>کلاس مسدودشده:</b><br>" + claseDestino + " (ماژول: " + moduloDestino + ")</p>"
				+ "<p>این خطا هنگامی رخ می‌دهد که یک مود، exports یا opens را "
				+ "در فایل module-info.java خود به‌درستی اعلام نکرده باشد.</p>" + "<p><b>علل احتمالی:</b></p>" + "<ul>"
				+ "<li>ماژول بستهٔ مورد نیاز را export نمی‌کند.</li>"
				+ "<li>دستور <b>opens</b> برای انعکاس (reflection) وجود ندارد.</li>"
				+ "<li>مود به‌درستی برای JPMS پیکربندی نشده است.</li>" + "</ul>"
				+ "<p>این مشکل باید توسط توسعه‌دهندهٔ مود رفع شود.</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin: کلاس در بستهٔ mixin به‌صورت نادرست قرار گرفته است";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "کلاسی به‌صورت نادرست در بستهٔ Mixin قرار گرفته است.</b>"
				+ "<p>یک کلاس معمولی درون بسته‌ای قرار گرفته که به‌عنوان mixin اعلام شده است.</p>"
				+ "<p><b>کلاس در تعارض:</b><br>" + clase + "</p>" + "<p><b>بستهٔ mixin اعلام‌شده:</b><br>" + paquete
				+ "</p>" + "<p><b>فایل mixins مسئول:</b><br>" + archivoMixin + "</p>"
				+ "<p>کلاس‌های معمولی نباید در بسته‌ای باشند که در mixins.json تعریف شده‌اند.</p>"
				+ "<p>فقط کلاس‌هایی که به‌عنوان mixin نشان‌گذاری شده‌اند باید در آن بسته وجود داشته باشند.</p>"
				+ "<p><b>راه‌حل برای توسعه‌دهنده:</b> کلاس‌های معمولی را از بستهٔ mixin خارج کنید "
				+ "یا پیکربندی فایل mixins.json را اصلاح کنید.</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "مشکلی با درایورهای GPU Matrox شناسایی شد.</b>"
				+ "<p>لاگ نشان می‌دهد که خرابی درون یک کتابخانهٔ درایور Matrox رخ داده است.</p>"
				+ "<p>GPUهای Matrox (به‌ویژه مدل‌های G200/G400 که در سرورها استفاده می‌شوند) "
				+ "برای رندرینگ سه‌بعدی مدرن طراحی نشده‌اند و ممکن است از نسخه‌های OpenGL مورد نیاز ماينکرفت پشتیبانی نکنند.</p>"
				+ "<p><b>راه‌حل‌های پیشنهادی:</b></p>" + "<ul>"
				+ "<li>درایور Matrox را به آخرین نسخهٔ موجود به‌روزرسانی کنید.</li>"
				+ "<li>به‌جای درایورهای عمومی سیستم، درایورهای رسمی را نصب کنید.</li>"
				+ "<li>اگر سخت‌افزار قدیمی است، از GPUای استفاده کنید که با OpenGL 3.2 یا بالاتر سازگار باشد.</li>"
				+ "</ul>" + "<p>در سرورها، این GPUها معمولاً فقط برای خروجی ویدیویی پایه طراحی شده‌اند "
				+ "و برای برنامه‌های سه‌بعدی مانند ماينکرفت مناسب نیستند.</p>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod: GPU ناسازگار";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod نتوانست GPU سازگاری را شناسایی کند.</b>"
				+ "<p>مود <b>VulkanMod</b> تلاش کرد با استفاده از Vulkan شروع به کار کند، اما GPUای با پشتیبانی مناسب از Vulkan پیدا نکرد.</p>"
				+ "<p>این معمولاً در موارد زیر رخ می‌دهد:</p>" + "<ul>" + "<li>GPU از Vulkan پشتیبانی نمی‌کند.</li>"
				+ "<li>درایورهای GPU قدیمی یا ناقص هستند.</li>"
				+ "<li>از آداپتور گرافیکی نادرست استفاده می‌شود (مثلاً GPU مجتمع به‌جای اختصاصی).</li>" + "</ul>"
				+ "<p><b>راه‌حل‌های پیشنهادی:</b></p>" + "<ul>"
				+ "<li>درایورهای GPU را به آخرین نسخه به‌روزرسانی کنید.</li>"
				+ "<li>بررسی کنید که GPU شما از Vulkan پشتیبانی می‌کند.</li>"
				+ "<li>اگر دو GPU دارید، استفاده از GPU اختصاصی را برای ماينکرفت اجباری کنید.</li>"
				+ "<li>اگر GPU شما از Vulkan پشتیبانی نمی‌کند، VulkanMod را حذف نصب کنید.</li>" + "</ul>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "RenderType نامعتبر برای outline";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		String base = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "یک مود سعی کرد outline را روی RenderType ناسازگار اعمال کند.</b>" + "<p>خطا این بود:</p>"
				+ "<code>Can't render an outline for this rendertype!</code>";

		if (enchantDetectado) {
			base += "<p><b>مود enchant-outline / better-enchants در گزارش شناسایی شد.</b></p>"
					+ "<p>این مود به‌دلیل ایجاد این مشکل در نسخه‌های اخیر ماينکرفت شناخته شده است.</p>"
					+ "<p><b>راه‌حل پیشنهادی:</b> enchant-outline را حذف یا به‌روزرسانی کنید.</p>";
		} else {
			base += "<p>این مشکل معمولاً مربوط به مودهایی است که رندر را تغییر می‌دهند "
					+ "(مانند Entity Model Features، Entity Texture Features، Visuality یا تداخل با Sodium).</p>"
					+ "<p><b>راه‌حل پیشنهادی:</b> مودهای رندر را یکی‌یکی به‌روزرسانی یا غیرفعال کنید.</p>";
		}

		return base;
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – DimensionalInventory خالی";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG سعی کرد یک DimensionalInventory خالی را ذخیره کند.</b>"
				+ "<p>بازی خطای زیر را پرتاب کرد:</p>"
				+ "<code>Cannot invoke DimensionalInventory.saveInventory(...) because \"d\" is null</code>"
				+ "<p>این یک باگ شناخته‌شده در DivineRPG است که با سیستم انبار Vethean مرتبط است.</p>"
				+ "<p><b>راه‌حل پیشنهادی:</b></p>" + "<ul>" + "<li>به فایل پیکربندی DivineRPG بروید.</li>"
				+ "<li><code>saferVetheanInventory=true</code> را تنظیم کنید.</li>"
				+ "<li>ذخیره کرده و بازی را دوباره راه‌اندازی کنید.</li>" + "</ul>"
				+ "<p>در صورت وجود نسخهٔ جدیدتر، به‌روزرسانی DivineRPG نیز توصیه می‌شود.</p>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "تضاد در Render Pass";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "تضادی در سیستم رندر شناسایی شد.</b>" + "<p>بازی خطای زیر را پرتاب کرد:</p>"
				+ "<code>Close the existing render pass before performing additional commands</code>"
				+ "<p>این خطا معمولاً به دلیل تداخل بین مودهای رندر مانند "
				+ "Iris، OptiFine، VulkanMod یا سایر مودهایی است که خط لولهٔ گرافیکی را تغییر می‌دهند.</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "خرابی داخلی Feather Client";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خرابی داخلی در Feather Client شناسایی شد.</b>" + "<p>بازی خطای زیر را پرتاب کرد:</p>"
				+ "<code>NoClassDefFoundError: feather/lib/sentry/Sentry</code>"
				+ "<p>این خطا توسط Feather Client ایجاد شده است.</p>" + "<p>Feather به دلایل زیر توصیه نمی‌شود:</p>"
				+ "<ul>" + "<li>از نسخه‌های اختصاصی و اصلاح‌شدهٔ مودهای محبوب استفاده می‌کند.</li>"
				+ "<li>سازگاری با Fabric استاندارد را مختل می‌کند.</li>"
				+ "<li>به‌عنوان یک مودپک از پیش ساخته‌شده با تغییرات داخلی عمل می‌کند.</li>"
				+ "<li>معمولاً با Sodium و سایر مودهای عملکرد تداخل ایجاد می‌کند.</li>" + "</ul>"
				+ "<p>استفاده از نصب استاندارد Fabric به‌جای Feather توصیه می‌شود.</p>";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "تضاد Iris + Flywheel (Create 6)";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "تضادی بین Iris و Flywheel در Create 6 شناسایی شد.</b>" + "<p>بازی خطای زیر را پرتاب کرد:</p>"
				+ "<code>NoSuchFieldError: TESSELATION_SHADERS</code>"
				+ "<p>مرجع‌های داخلی <code>$irisflw$</code> شناسایی شدند که "
				+ "نشان‌دهندهٔ تداخل بین Iris و Flywheel است.</p>"
				+ "<p>Iris Flywheel 2.0 برای Create 6 فقط به‌صورت رسمی با NeoForge سازگار است.</p>"
				+ "<p>اگر از Forge یا Fabric استفاده می‌کنید، این ترکیب ممکن است این خطا را ایجاد کند.</p>";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "مدل GeckoLib یافت نشد";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "یک مود نتوانست مدل GeckoLib را پیدا کند.</b>" + "<p>مدل تحت تأثیر:</p>" + "<code>" + modelo
				+ "</code>" + "<p>این خطا هنگامی رخ می‌دهد که فایل <code>.geo.json</code> وجود نداشته باشد "
				+ "یا درون مود به‌درستی پیکربندی نشده باشد.</p>" + "<p>علل احتمالی:</p>" + "<ul>"
				+ "<li>مدل حذف شده اما هنوز به آن ارجاع داده می‌شود.</li>" + "<li>خطا در مسیر فایل.</li>"
				+ "<li>فایل درون JAR وجود ندارد.</li>" + "<li>نسخهٔ ناسازگار مود.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – انیمیشن وجود ندارد";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon سعی کرد انیمیشنی را پخش کند که وجود ندارد.</b>" + "<p>انیمیشن:</p>" + "<code>" + animacion
				+ "</code>" + "<p>گروه:</p>" + "<code>" + grupo + "</code>"
				+ "<p>این خطا معمولاً در موارد زیر رخ می‌دهد:</p>" + "<ul>"
				+ "<li>نسخه‌های ناسازگار Cobblemon با هم ترکیب شده‌اند.</li>"
				+ "<li>افزونه با نسخهٔ نصب‌شده هماهنگ نیست.</li>" + "<li>منابع یا انیمیشن‌های داخلی وجود ندارند.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "خرابی داخلی Lunar Client";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خرابی داخلی در Lunar Client شناسایی شد.</b>"
				+ "<p>این خطا از خود Lunar Client نشأت می‌گیرد و مربوط به بازی پایه یا مودها نیست.</p>"
				+ "<p>Lunar Client از تغییرات داخلی و سفارشی استفاده می‌کند که ممکن است "
				+ "با مودها یا پیکربندی‌های خاص ناسازگاری ایجاد کند.</p>"
				+ "<p>برای رد کردن مشکلات ناشی از خود کلاینت، توصیه می‌شود با نصب استاندارد ماينکرفت آزمایش کنید.</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "دسترسی غیرمجاز به متد یا فیلد";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "یک مود سعی کرد به یک متد یا فیلد protected/private دسترسی پیدا کند.</b>" + "<p>کلاس مسئول:</p>"
				+ "<code>" + clase + "</code>" + "<p>عضو دسترسی‌شده:</p>" + "<code>" + miembro + "</code>"
				+ "<p>این خطا معمولاً در موارد زیر رخ می‌دهد:</p>" + "<ul>"
				+ "<li>مود برای نسخهٔ دیگری از ماينکرفت کامپایل شده است.</li>"
				+ "<li>ترکیبی از mappings ناسازگار وجود دارد.</li>" + "<li>مود قدیمی است.</li>"
				+ "<li>از لودر اشتباه استفاده شده است (Fabric/Forge/NeoForge).</li>" + "</ul>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "خطا در بارگیری datapack/resourcepack";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "بارگیری یک datapack یا resourcepack با شکست مواجه شد.</b>" + "<p>فایل مشکل‌دار:</p>" + "<code>"
				+ archivo + "</code>" + "<p>پک:</p>" + "<code>" + pack + "</code>"
				+ "<p>بازی نتوانست این فایل را تجزیه کند و این امر باعث خطاهای بارگیری registry شد.</p>"
				+ "<p>این مشکل معمولاً به دلایل زیر رخ می‌دهد:</p>" + "<ul>" + "<li>JSON بدفرمت.</li>"
				+ "<li>نسخهٔ ناسازگار پک.</li>" + "<li>پک برای نسخهٔ فعلی بازی قدیمی است.</li>"
				+ "<li>تضاد بین datapackها.</li>" + "</ul>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "خطای کامپایل شیدر";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "کامپایل شیدر شکست خورد.</b>" + "<p>بازی نتوانست یکی از شیدرهای فعال را کامپایل کند.</p>"
				+ "<p>این مشکل معمولاً مربوط به Sodium، Iris یا شیدرپک‌های ناسازگار است.</p>"
				+ "<p>راه‌حل‌های پیشنهادی:</p>" + "<ul>" + "<li>شیدر دیگری را امتحان کنید.</li>"
				+ "<li>به‌صورت موقت شیدرها را غیرفعال کنید.</li>" + "<li>درایورهای GPU خود را به‌روزرسانی کنید.</li>"
				+ "<li>اگر مشکل ادامه یافت، بازی را بدون Sodium اجرا کنید.</li>" + "</ul>";
	}

	public String nombreErrorCreacionModelo() {
		return "خطا در ایجاد یا بارگیری مدل";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>هنگام تلاش برای ایجاد یا بارگیری یک مدل ماينکرفت خطایی رخ داد.</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>مدل تحت تأثیر: <code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>این نوع خطا معمولاً در موارد زیر رخ می‌دهد:</p>");
		sb.append("<ul>");
		sb.append("<li>یک مود دارای مدلی با پیکربندی نادرست است.</li>");
		sb.append("<li>مدل JSON خراب یا ناقص است.</li>");
		sb.append("<li>تضادی بین مودهایی که مدل‌ها یا رندر را تغییر می‌دهند وجود دارد.</li>");
		sb.append("<li>یک resource pack یا datapack حاوی مدل‌های ناسازگار است.</li>");
		sb.append("</ul>");
		sb.append("<p>سعی کنید تشخیص دهید کدام مود یا بستهٔ منابع، مدل ذکرشده را فراهم می‌کند.</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>علت احتمالی شناسایی شد:</b></p>");
		sb.append("<p>فعالیت مود <b>Cooler Animations</b> در لاگ شناسایی شد.</p>");
		sb.append(
				"<p>این مود سیستم انیمیشن و مدل را تغییر می‌دهد و در برخی موارد ممکن است باعث خطاهای بارگیری مدل شود.</p>");
		sb.append(
				"<p>اگر مشکل ادامه یافت، بازی را بدون Cooler Animations اجرا کنید تا بررسی کنید آیا خطا برطرف می‌شود.</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "مشکل با Starlight";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خطایی مرتبط با Starlight شناسایی شد.</b>"
				+ "<p>این خطا درون <code>BlockStarLightEngine.initNibble</code> رخ داده است.</p>"
				+ "<p>این نشان‌دهندهٔ شکست در موتور نورپردازی مود <b>Starlight</b> است.</p>"
				+ "<p>Starlight مودی است که سیستم نورپردازی ماينکرفت را کاملاً تغییر می‌دهد و به‌دلیل ایجاد انواع مشکلات در برخی محیط‌های مود شناخته شده است.</p>"
				+ "<p>این تنها یکی از چندین خطا‌ی شناخته‌شدهٔ مرتبط با Starlight است.</p>"
				+ "<p>اگر مشکل ادامه یافت، بازی را بدون Starlight اجرا کنید.</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "مشکل با AAAParticles / Effekseer";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خرابی بومی مرتبط با Effekseer شناسایی شد.</b>"
				+ "<p>این خطا درون کتابخانهٔ بومی <code>EffekseerNativeForJava</code> رخ داده است.</p>"
				+ "<p>این کتابخانه توسط مود <b>AAAParticles</b> توسعه‌یافته توسط ChloePrime استفاده می‌شود.</p>"
				+ "<p>خرابی‌های کتابخانه‌های بومی معمولاً نشان‌دهندهٔ مشکلاتی در خود مود یا وابستگی‌های بومی آن هستند.</p>"
				+ "<p>اگر مشکل ادامه یافت، بازی را بدون AAAParticles اجرا کنید.</p>";
	}

	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "خرابی بومی در محیط اجرای جاوا (JVM) شناسایی شد.</b>"
				+ "<p>این نوع خطا درون خود ماشین مجازی جاوا رخ می‌دهد (مثلاً در <code>jvm.dll</code> یا <code>libjvm.so</code>)، "
				+ "و لزوماً به‌دلیل یک مود نیست.</p>"
				+ "<p>هرچند گاهی ممکن است از مودهایی با کتابخانه‌های بومی ناسازگار ناشی شود، "
				+ "<b>اما بسیار احتمال‌تر است که ناشی از نسخه‌ای معیوب، خراب یا قدیمی از JVM باشد</b>.</p>"
				+ "<p>این موضوع به‌ویژه زمانی رایج است که از نسخه‌های قدیمی یا غیررسمی جاوا استفاده می‌کنید (مثل نسخه‌های جامعه بدون پشتیبانی).</p>"
				+ "<p><b>توصیه می‌کنیم از JVM معتبر و پشتیبانی‌شده استفاده کنید:</b></p>" + "<ul>"
				+ "<li><b>Red Hat Build of OpenJDK</b> (پایدار، خوب آزمایش‌شده، ایده‌آل برای ویندوز/لینوکس)</li>"
				+ "<li><b>OpenLogic OpenJDK</b> (پشتیبانی چندسکویی، شامل macOS Intel)</li>"
				+ "<li><b>Azul Zulu</b> (مجوزدار، با پشتیبانی رایگان LTS)</li>"
				+ "<li><b>Oracle JDK</b> (رسمی، با به‌روزرسانی‌های منظم)</li>" + "</ul>"
				+ "<p>از نسخه‌های ناشناخته، سفارشی یا بسیار قدیمی اجتناب کنید، زیرا ممکن است حاوی خطاهای بحرانی در موتور JVM باشند.</p>"
				+ "<p><b>آیا از Prism Launcher یا TLauncher استفاده می‌کنید؟</b> پیکربندی JVM سفارشی بسیار آسان است: "
				+ "در Prism Launcher، به <i>نصب‌ها</i> ← <i>ویرایش نمونه</i> ← <i>تنظیمات جاوا</i> بروید؛ "
				+ "در TLauncher، به <i>Settings</i> ← <i>Java Settings</i> رفته و مسیر JDK/JRE دانلودشده خود را مشخص کنید. "
				+ "نیازی به تغییر JVM سیستم نیست!</p>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "تضاد بین Paranoia و C2ME";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "تضادی بین مودهای Paranoia و C2ME شناسایی شد.</b>"
				+ "<p>خطا نشان می‌دهد که <code>ThreadLocalRandom</code> از یک رشتهٔ نادرست دسترسی شده است.</p>"
				+ "<p>این معمولاً زمانی رخ می‌دهد که مود <b>Paranoia</b> کدی را اجرا می‌کند که برای چند رشته ایمن نیست، در حالی که <b>C2ME</b> در حال انجام بهینه‌سازی‌های چندرشته‌ای است.</p>"
				+ "<p>این نوع تداخل در مودهای ساخته‌شده با MCreator رایج است.</p>"
				+ "<p>اگر مشکل ادامه یافت، بازی را بدون Paranoia اجرا کنید یا C2ME را غیرفعال کنید.</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "دایرکتوری assets ماينکرفت یافت نشد";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ماينکرفت نتوانست دایرکتوری assets را پیدا کند.</b>"
				+ "<p>لانچر سعی کرد با مسیر assets نامعتبر، بازی را شروع کند.</p>"
				+ "<p>این بدان معناست که فایل‌های منابع بازی (assets) وجود ندارند یا به‌درستی نصب نشده‌اند.</p>"
				+ "<p>این مشکل معمولاً در نصب‌های نادرست ماينکرفت یا لانچرهای بدپیکربندی‌شده رخ می‌دهد.</p>"
				+ "<p>همچنین ممکن است هنگام استفاده از لانچرهای غیررسمی که assets را به‌درستی مدیریت نمی‌کنند (مانند FreshCraft) اتفاق بیفتد.</p>"
				+ "<p>اگر مشکل ادامه یافت، مودپک را دوباره نصب کنید یا بازی را از طریق لانچر رسمی یا قابل‌اعتماد اجرا کنید.</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return "نصب " + dependencia + " نسخه " + version + " یا جدیدتر";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return "جایگزینی " + dependencia + " با نسخه‌ای بین " + min + " و " + max;
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "مود " + mod + " به " + dependencia + " با حداقل نسخه " + version + " نیاز دارد";
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "مود " + mod + " به " + dependencia + " نسخه " + version + " نیاز دارد";
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {

		return "مود " + mod + " به " + dependencia + " بین نسخه‌های " + min + " و " + max + " نیاز دارد"
				+ " (نسخه فعلی: " + actual + ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "نسخه ناسازگار Cupboard";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "یک خرابی ناشی از نسخه قدیمی Cupboard تشخیص داده شد.</b>"
				+ "<p>خطا در داخل <code>ClientConfigCompat.setupNeoforge</code> رخ می‌دهد زیرا "
				+ "<code>ModList.get()</code> مقدار <code>null</code> را برمی‌گرداند.</p>"
				+ "<p>این یک مشکل شناخته شده در نسخه‌های قدیمی ماد <b>Cupboard</b> است.</p>"
				+ "<p>نسخه‌های قدیمی مانند <b>3.2</b> دارای این اشکال هستند.</p>"
				+ "<p><b>راه حل:</b> Cupboard را به نسخه <b>3.5</b> یا جدیدتر به‌روزرسانی کنید.</p>";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		return "زیرا شما در macOS هستید و بازی سعی می‌کند از OpenGL استفاده کند، که با نسخه‌های اخیر macOS سازگار نیست. "
				+ "باید از نسخه‌ای از Minecraft که از Metal پشتیبانی می‌کند استفاده کنید، یا اگر مک اینتل، M1 یا M2 دارید (نه M3+ یا Neo)، از لینوکس استفاده نمایید.";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "انیمیشن GeckoLib یافت نشد";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "یک ماد نتوانست فایل انیمیشن GeckoLib را بارگذاری کند.</b>" + "<p>فایل آسیب‌دیده:</p>" + "<code>"
				+ archivo + "</code>"
				+ "<p>این خطا زمانی رخ می‌دهد که فایل انیمیشن <code>.json</code> وجود نداشته باشد، "
				+ "دارای خطای نحو (سینتکس) باشد یا مسیر آن نادرست باشد.</p>" + "<p>علل احتمالی:</p>" + "<ul>"
				+ "<li>فایل حذف شده است اما هنوز در کد مرجع داده شده است.</li>" + "<li>خطای نحو در داخل فایل JSON.</li>"
				+ "<li>مسیر نادرست در ثبت ماد تعریف شده است.</li>" + "<li>تضاد وابستگی‌ها یا نسخه ناسازگار.</li>"
				+ "</ul>";
	}

}
