package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;

/**
 * TODO implementar completamente el idioma ucraniano de Majnovschina.
 */
public class Ucraniano implements Idioma // ucraniano de Majnovschina. ¡NO SOMOS VYSHYVATNIKS!
{

	private final Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "uk";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "ucraniano";
	}

	@Override
	public String nombre_del_idioma() {
		return "Українська";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_majnovschina.png");// Utilizamos la versión sencilla
																						// de la bandera porque a
																						// algunas personas podría
																						// resultarles inquietante.
	}
	// TODO

	// Переклад українською (Махновщина)

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>це не є дійсною папкою модів</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Не знаю, де знаходиться JAR-файл "
				+ Statics.nombre_cd.obtener() + "</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Пошук для PID: " + String.valueOf(pid)
				+ "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") неактивний!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>У нас немає JVM</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Оновлення драйверів ATI/AMD може допомогти. Прочитайте цей посібник для вирішення проблеми: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>Посібник з оновлення драйверів</a> https://www.amd.com/es/support/download/drivers.html Завантажити </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Деякі старі версії іноді мають проблеми з графікою Nouveau на ранньому етапі завантаження.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>У вас проблема з графічними драйверами. Якщо у вас GPU або APU AMD/ATI — оновіть драйвери AMD. Якщо у вас відеокарта NVIDIA — переконайтеся, що гра та всі процеси javaw.exe використовують дискретну відеокарту. Прочитайте цей посібник: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Посібник з оновлення драйверів</a></span>";
	}

	// Переклад українською (Махновщина)

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Ваше вікно FML Early дає збій. Щоб це виправити, перейдіть до (.minecraft/config/fml.toml) і змініть earlyWindowProvider на earlyWindowProvider=\"none\". Якщо ви використовуєте Mac M1, також переконайтеся, що у вас встановлена ARM-версія Java, а не Intel x64. Це також поширена проблема при застарілих драйверах. Перегляньте цей посібник, якщо ви на Windows і вимкнення цього не допомагає: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Посібник з оновлення драйверів</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>У вас відсутні всі необхідні залежності:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "Запитано").replace("Expected range", "очікуваний діапазон")
				+ "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Ваш звіт " + Statics.nombre_cd.obtener()
				+ " знаходиться тут <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace()
				+ "'>переглянути звіт</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Це GUI " + Statics.nombre_cd.obtener()
				+ ". Якщо гра закривається без проблем, ігноруйте це.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>Переглянути звіт</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>Переглянути локальний звіт у вашому браузері.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "Поділитися звітом";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "Поділитися звітом. Ваші логи будуть завантажені на securelogger.net, а сам звіт — на інший сайт.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Виявлено проблемні JAR-файли (спочатку FATAL, потім високий рівень і нижні рядки):</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> рівень:</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Можливо критично (FATAL):</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Виявлено проблемні ModID (спочатку FATAL, потім низький рівень і нижні рядки):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Виявлено проблемні пакети (спочатку FATAL, потім низький рівень і нижні рядки):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>У вас є критичні класи (FATAL) — це дуже важливо. Часті причини: погані coremods або критичні відсутні залежності. Ви можете скористатися QuickFix, щоб знайти моди з цими класами. Виявлено відсутні критичні класи:</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Вміст у {} (найважливіше зверху, лише перші 20):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Виявлено проблемну конфігурацію SpongeMixin: "
				+ "</b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>У вас є моди з дубльованими пакетами. Ви можете виправити це, видаливши пакет (папку) з JAR-файлу. Відкрийте JAR у програмі для архівів, як WinRAR або 7z, або змініть розширення файлу з .jar на .zip, видаліть папку і потім поверніть розширення назад на .jar.</span>";
	}

	// Переклад українською (Махновщина)

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>У вас є дубльовані моди</b> "
				+ linea.replace("from mod files", "з файлів модів ");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Підозрілий MinecraftForge мод — цей мод має проблему:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV потребує lithostitched, ви можете встановити його тут: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Щоб використовувати Iris Shaders або Oculus, потрібен SODIUM або його аналог для інших завантажувачів (Rubidium, Embedium, Bedium)</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Проблема з розширенням KubeJS </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Виявлено проблеми з драйверами NVIDIA на версіях Windows до Windows 11." + "</span><br/><br/>"
				+ "Щоб переконатися, що Minecraft (і поточна JVM) використовує дискретну відеокарту NVIDIA, виконайте ці кроки:<br/><br/>"
				+ "1. <strong>Знайдіть виконуваний файл Java:</strong><br/>"
				+ "   - Ця програма використовує наступний виконуваний файл Java: " + obtenerRutaJava() + "<br/>"
				+ "   - Якщо шлях не відображається, знайдіть 'java.exe' у вашій системі.<br/><br/>"
				+ "2. <strong>Відкрийте панель керування NVIDIA:</strong><br/>"
				+ "   - Клацніть правою кнопкою миші на робочому столі та виберіть 'Панель керування NVIDIA'.<br/><br/>"
				+ "3. <strong>Налаштуйте пріоритет GPU:</strong><br/>"
				+ "   - У панелі керування NVIDIA відкрийте 'Керування параметрами 3D'.<br/>"
				+ "   - Оберіть 'Налаштування для програми'.<br/>"
				+ "   - Натисніть 'Додати' і виберіть виконуваний файл Java (наприклад, 'java.exe').<br/>"
				+ "   - Встановіть 'Високопродуктивний процесор (NVIDIA)'.<br/><br/>"
				+ "4. <strong>Збережіть зміни:</strong><br/>" + "   - Застосуйте зміни та закрийте панель.<br/><br/>"
				+ "5. <strong>Перезапустіть Minecraft:</strong><br/>"
				+ "   - Перезапустіть гру, щоб зміни набрали чинності.<br/><br/>"
				+ "Якщо ви використовуєте Windows Server 2022 або Windows 10, ці кроки також актуальні за наявності актуальних драйверів NVIDIA.<br/><br/>"
				+ "Примітка: якщо панель NVIDIA відсутня, перевірте встановлення драйверів.";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Виявлено проблеми з драйверами NVIDIA у Windows 11/Server 2025 або новіших." + "</span><br/><br/>"
				+ "Щоб переконатися, що Minecraft (і поточна JVM) використовує дискретну відеокарту NVIDIA, виконайте ці кроки:<br/><br/>"
				+ "1. <strong>Знайдіть виконуваний файл Java:</strong><br/>"
				+ "   - Ця програма використовує наступний виконуваний файл Java: " + obtenerRutaJava() + "<br/>"
				+ "   - Якщо шлях не відображається, знайдіть 'java.exe' у вашій системі.<br/><br/>"
				+ "2. <strong>Відкрийте Налаштування:</strong><br/>" + "   - Натисніть <code>Win + I</code>.<br/>"
				+ "   - Перейдіть до <strong>Система > Дисплей > Графіка</strong>.<br/><br/>"
				+ "3. <strong>Налаштуйте пріоритет GPU:</strong><br/>"
				+ "   - Виберіть 'Параметри графіки за замовчуванням'.<br/>"
				+ "   - Оберіть 'Настільні програми' → 'Огляд'.<br/>" + "   - Додайте 'java.exe'.<br/>"
				+ "   - Встановіть 'Висока продуктивність (NVIDIA)'.<br/><br/>"
				+ "4. <strong>Збережіть зміни:</strong><br/>" + "   - Застосуйте зміни.<br/><br/>"
				+ "5. <strong>Перезапустіть Minecraft:</strong><br/>" + "   - Перезапустіть гру.<br/><br/>"
				+ "Для Windows 11 або Server 2025+ ці кроки працюють за умови актуальних драйверів.<br/><br/>"
				+ "Примітка: якщо налаштування графіки відсутні — перевірте драйвери.";
	}

	// Переклад українською (Махновщина)

	@Override
	public String segundo60Tick() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Ваш сервер або світ має тики понад 60 секунд. Це може бути через те, що моди уповільнюють сервер або апаратне забезпечення занадто слабке.</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>У вас недостатньо RAM/пам’яті. Потрібно виділити більше.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Theseus також має інші проблеми, включаючи збої при видаленні модів. Якщо вам потрібно працювати з файлами mrpack, використовуйте інші лаунчери, такі як Prism Launcher (лише modrinth.com), ATLauncher (лише modrinth.com) або Hello Minecraft Launcher (підтримує modrinth.com і bbsmc.net).</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Ви використовуєте \"Пропустити запуск лаунчера\" (CurseForge App). Іноді це викликає складні для виявлення проблеми. Це пов’язано з цією опцією у старих або нових версіях CurseForge. Вимкніть її та використовуйте \"Mojang Launcher\" у налаштуваннях. Подивіться це відео (англійською, 1:11) "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>тут</a>.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Попередження: у вас відсутні класи (Warning). Зазвичай це не критично, але не завжди. Це відрізняється від критично відсутніх класів (FATAL). Часті причини — погані coremods або відсутні залежності. Можна використати QuickFix для пошуку відповідних модів. Виявлено відсутні класи:</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Немає результатів</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Розташування логів:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Ось результати перевірок. Переглядайте їх повільно — зазвичай правильна причина знаходиться у перевірках 1 або 2. Інші (3+) можна використовувати для підтвердження, але часто це каскадні помилки, які можна ігнорувати. Помилки виникають шарами: виправлення основної проблеми може вирішити поточну, але згодом може з’явитися нова, не пов’язана з попередньою.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Будь ласка, використовуйте Java 17 для версій 1.17–1.20.4, Java 21 — для новіших версій, і Java 8 — для старих. [Посібник](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Якщо проблема залишається, можливо, деякі моди несумісні за версією.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 і новіші не працюють із Minecraft нижче 1.20.5 у більшості модлоадерів через застарілий ASM.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java застаріла </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Потрібен модуль JPMS " + mod_necesitas + " з "
				+ submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Неможливо викликати " + metodo + ", тому що "
				+ objeto + " є null</b>";
	}

	@Override
	public String analisisAvanzado() {
		return "Розширений аналіз";
	}

	@Override
	public String seleccionarCarpeta() {
		return "Вибрати папку";
	}

	@Override
	public String cadenaBusqueda() {
		return "Рядок пошуку";
	}

	@Override
	public String usarRegex() {
		return "Використовувати Regex";
	}

	@Override
	public String ignorarMayusculas() {
		return "Ігнорувати регістр";
	}

	@Override
	public String buscar() {
		return "Шукати";
	}

	@Override
	public String busquedaEnProgreso() {
		return "Пошук триває";
	}

	@Override
	public String noSeEncontraronResultados() {
		return "Результатів не знайдено";
	}

	@Override
	public String errorBusqueda() {
		return "Помилка пошуку";
	}

	// Переклад українською (Махновщина)

	@Override
	public String omitirYCerrar() {
		return "Пропустити і закрити";
	}

	@Override
	public String guardarYCerrar() {
		return "Зберегти і закрити";
	}

	@Override
	public String pegaLosRegistrosAqui() {
		return "Вставте логи тут";
	}

	@Override
	public String archivo() {
		return "Файл";
	}

	@Override
	public String incluir() {
		return "Включити";
	}

	@Override
	public String abrir() {
		return "Відкрити";
	}

	@Override
	public String endpointDeInforme() {
		return "Кінцева точка звіту";
	}

	@Override
	public String sitoDeLogging() {
		return "Сайт логування:";
	}

	@Override
	public String apiDeLogging() {
		return "API логування:";
	}

	@Override
	public String anonimizarRegistros() {
		return "Анонімізувати логи (Бета)";
	}

	@Override
	public String botonDeCompartirInforme() {
		return "Поділитися звітом і всіма вибраними логами";
	}

	@Override
	public String arco() {
		return "Це діалогове вікно дозволяє ділитися логами через API SecureLogger "
				+ "на <a href=\"https://securelogger.net\">securelogger.net</a>. Натиснувши кнопку спільного доступу, "
				+ "ваш звіт буде відправлений до вибраної кінцевої точки (за замовчуванням asbestosstar.egoism.jp) (можна змінити внизу). "
				+ "Ви можете поділитися всіма вибраними логами разом зі звітом. Якщо не хочете нічого завантажувати — не використовуйте цей діалог! "
				+ "Ми не обробляємо ваш звіт на офіційній кінцевій точці (<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\">посилання</a>); "
				+ "ми лише видаляємо заборонені посилання. Код доступний тут: <a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">джерело</a>. "
				+ "Це використовується лише для відображення інформації про збій і посилання на логи. Однак можна використовувати власну кінцеву точку, "
				+ "яка може працювати інакше. Ви використовуєте сайт звітів "
				+ Config.obtenerInstancia().obtenerSitoDeInformes() + " і сайт логів "
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". "
				+ "Ви також можете ділитися окремими логами без звіту, використовуючи кнопки поруч із ними. "
				+ Statics.nombre_cd.obtener()
				+ " має вбудовану анонімізацію логів, яка намагається приховати імена користувачів, UUID, "
				+ "токени доступу, ID сесій, IP-адреси та інші дані. Проте вона не ідеальна і може бути вимкнена автором модпака. "
				+ "Її можна увімкнути або вимкнути у нижній частині цього вікна. Ви самі контролюєте свої дані та вирішуєте, куди їх надсилати. "
				+ "Сайти логування належать третім сторонам, власники яких можуть бути приховані. Ви несете повну відповідальність за свої дані та ризики. "
				+ "Діалог " + Statics.nombre_cd.obtener() + " — це лише інтерфейс для управління цим процесом. "
				+ "Важливо знати про RGPD і ARCO. "
				+ "Якщо ви в Європі, можете використовувати <a href=\"https://securelogger.top\">securelogger.top</a> (Німеччина, Hetzner). "
				+ "Для правової інформації дивіться: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>, "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">RGPD</a>, "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">Політика захисту даних Японії</a>.";
	}

	// Переклад українською (Махновщина)

	@Override
	public String enlaceDelReporte() {
		return "Посилання на звіт:";
	}

	@Override
	public String guardarConfigDeCompartir() {
		return "Зберегти налаштування обміну";
	}

	@Override
	public String registroDemasiadoGrande() {
		return "Лог занадто великий для цього сайту. Оберіть інший і спробуйте знову.";
	}

	@Override
	public String errorConPublicarRegistro(String error) {
		return "Помилка при публікації логу: " + error;
	}

	@Override
	public String apiDeRegistroNoExiste() {
		return "API логування не існує. Будь ласка, змініть API у налаштуваннях.";
	}

	@Override
	public String errorSSL() {
		return "У вас SSL помилка. Це часто трапляється зі старими версіями Java, "
				+ "включаючи Java 8 у стандартному Minecraft Launcher, а також версії з sun.com і java.com. "
				+ "Це впливає на різні функції, включаючи встановлення MinecraftForge, обмін звітами "
				+ Statics.nombre_cd.obtener() + ", "
				+ "моди, що потребують інтернету, та сайти логування. Якщо це стається під час обміну звітом, "
				+ "зробіть скріншот і оберіть сайт логування, сумісний зі старими версіями Java.";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Несумісний JavaFML: потрібно версія "
				+ requerido + ", знайдено " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Увага! JavaFML потребує певну версію Minecraft Forge</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Файл JAR '" + archivoJar
				+ "' вимагає провайдера мови '" + proveedor + "' версії '" + requerido
				+ "' або новішої, але знайдено лише '" + encontrado + "'.</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "УВАГА! Crash Assistant є фальшивим детектором шкідливого ПЗ. Він навмисно блокує запуск гри, "
				+ "ігноруючи ваше право використовувати моди. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Переглянути код</a> "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Переглянути код</a>. "
				+ "Перевіряйте код самостійно та не покладайтеся лише на авторитет.</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Мод '" + idMod
				+ "' не запустився, оскільки не знайдено клас: '" + claseNoEncontrada
				+ "'. Переконайтеся, що всі залежності встановлені.</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Watermedia заблокував запуск із TLauncher.</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Ви використовуєте застарілу версію Optifine. Встановіть версію, що відповідає вашій версії Minecraft.</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Не вдалося завантажити сервіс ModLauncher: </b>" + servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Помилка при обробці JSON '" + recurso
				+ "' у JAR '" + archivoJar + "'. Проблема з логуванням.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Помилка: мод '" + modId
				+ "' потребує версію '" + requerido + "' або новішу для '" + dependencia + "', але знайдено '" + actual
				+ "'." + "</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Ваша GPU не підтримує необхідну версію OpenGL. Оновіть драйвери або змініть відеокарту</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Збільште виділену пам’ять або зменшіть кількість модів/плагінів</b>";
	}

	// Переклад українською (Махновщина)

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Виявлено 32-бітну JVM: вона не може використовувати більше 4 ГБ RAM. "
				+ "Встановіть 64-бітну JVM, щоб використовувати всю доступну пам’ять</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Критична помилка пам’яті PermGen. Збільште простір постійної пам’яті або зменште навантаження від класів</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Помилка сумісності між Java 8 і сучасними версіями</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+ не підтримується — використовуйте Java 8 для старого Forge</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Потрібна Java 8 (версія 52.0). Оновіть або правильно налаштуйте її</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Критична помилка сумісності: блоки не підтримуються в цій версії. "
				+ "Перевірте, чи сумісні версії модів і сервера</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Помилка конфігурації моніторів: "
				+ "Не вдалося встановити режим екрана. " + "Перевірте:</b>" + "<br>- Налаштування кількох моніторів"
				+ "<br>- Оновлені драйвери відеокарти" + "<br>- Чи підтримується ця роздільна здатність системою";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Помилка в параметрах Java: "
				+ "Конфліктні опції збирача сміття. "
				+ "Перевірте, чи не поєднуєте ви кілька алгоритмів GC у параметрах JVM</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Критична помилка конфігурації NightConfig/Forge: " + "Файл конфігурації пошкоджений або неповний. "
				+ "Це може бути спричинено порожніми файлами конфігурації (часто 0 байт) у папці 'config' у старих або кастомних версіях NightConfig. "
				+ "Для більшості версій проблему вирішує Night Config Fixes, але якщо ви використовуєте несумісну або кастомну версію NightConfig, вам доведеться видалити файли конфігурації вручну. "
				+ "Ця проблема частіше зустрічається у старих версіях MC Forge (які включають NightConfig) і в старих модах FabricMC, що пакують NightConfig, але також може існувати в деяких кастомних версіях NightConfig. "
				+ "Детальніше про рішення: <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a>.</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Виявлено помилку драйвера Intel HD Graphics. Рішення:</b>"
				+ "<br>1. Оновіть драйвери Intel з <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a> (мінімальна версія 15.xx.xx.xx)"
				+ "<br>2. У Minecraft: Опції → Відео → увімкніть 'Enable VBOs' і 'VSync'"
				+ "<br>3. Виділіть грі 1–2 ГБ RAM у лаунчері"
				+ "<br>4. Тимчасово вимкніть антивірус/фаєрвол під час оновлення";
	}

	// Переклад українською (Махновщина)

	public String nombre_de_faltar_de_clases_advertencia() {
		return "Попередження: виявлено відсутні класи";
	}

	public String nombre_de_bloque_teselado() {
		return "Помилка рендерингу блоків";
	}

	public String nombre_de_contenido_de_stacktrace() {
		return "Аналіз stack trace";
	}

	public String nombre_de_cursed_consola() {
		return "Неповна консоль CurseForge";
	}

	public String nombre_de_early_window() {
		return "Помилка раннього вікна (FMLEarlyWindow)";
	}

	public String nombre_de_drivers() {
		return "Проблеми з відеодрайверами";
	}

	public String nombre_de_error_de_config_mcforge() {
		return "Пошкоджена конфігурація MCForge";
	}

	public String nombre_de_error_de_monitor_lwjgl() {
		return "Збій режиму відображення (LWJGL)";
	}

	public String nombre_de_fabricmc_runtime_error_provided_by() {
		return "Помилка ініціалізації FabricMC";
	}

	public String nombre_de_falta_module_jmps() {
		return "Відсутні модулі JPMS";
	}

	public String nombre_de_faltar_de_clases() {
		return "Критично відсутні класи";
	}

	public String nombre_de_faltas_dependencias_de_modlauncher() {
		return "Відсутні залежності ModLauncher";
	}

	public String nombre_de_java_versiones() {
		return "Несумісні версії Java";
	}

	public String nombre_de_faltar_de_kubejs_resourcepack() {
		return "Помилка ресурсів KubeJS";
	}

	public String nombre_de_lenguaje_proveedor_check() {
		return "Несумісний мовний провайдер";
	}

	public String nombre_de_faltar_de_liyhostictchctov() {
		return "Litchhost";
	}

	public String nombre_de_malware_falso_crash_assistant() {
		return "Виявлено хибне спрацювання антивірусу";
	}

	public String nombre_de_mcforge_mod_sespechoso() {
		return "Виявлено підозрілий мод";
	}

	public String nombre_de_mods_duplicados_modlauncher() {
		return "Дубльовані моди в ModLauncher";
	}

	public String nombre_de_modules_duplicados_jmps() {
		return "Конфлікти модулів JPMS";
	}

	public String nombre_de_necesitas_sodium() {
		return "Потрібен Sodium для Iris";
	}

	public String nombre_de_no_puede_analizar_json_de_registro() {
		return "Помилка аналізу JSON логів";
	}

	public String nombre_de_no_tiene_memoria() {
		return "Недостатньо пам’яті";
	}

	public String nombre_de_null_pointer() {
		return "Помилка нульового посилання (NullPointerException)";
	}

	public String nombre_de_opciones_java_gc_invalidas() {
		return "Некоректні GC параметри Java";
	}

	public String nombre_de_optifine_obsoleta() {
		return "Застарілий/несумісний OptiFine";
	}

	public String nombre_de_60_segundo_trick() {
		return "Критичний тик сервера (60с)";
	}

	public String nombre_de_servicio_de_modlauncher_no_funciona() {
		return "Сервіси ModLauncher не працюють";
	}

	public String nombre_de_spongemixin_configs_problematicos() {
		return "Проблемні конфігурації SpongeMixing";
	}

	public String nombre_de_theseus() {
		return "Theseus несумісний";
	}

	public String nombre_de_watermedia_tl() {
		return "TLauncher не підтримується WATERMeDIA";
	}

	@Override
	public String auditorias_transformer() {
		return "Аудит Transformer";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Ось результати аудитів Transformer у ванільному лаунчері. Зазвичай менш точні, ніж аналіз StackTrace, але лаунчер не завжди має {} дані</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "Цей інструмент шукає моди, створені в MCreator. Більшість таких модів нормальні, але іноді мають проблеми. Це допомагає їх виявити.";
	}

	@Override
	public String escanear() {
		return "Сканувати";
	}

	@Override
	public String cargando() {
		return "Завантаження";
	}

	@Override
	public String inicioApp() {
		return "Запуск гри/додатку";
	}

	@Override
	public String ajustesCrashDetector() {
		return "Налаштування " + Statics.nombre_cd.obtener();
	}

	@Override
	public String confidencialidad() {
		return "Конфіденційність";
	}

	@Override
	public String tooltip() {
		return "Підказка";
	}

	@Override
	public String config() {
		return "Налаштування";
	}

	@Override
	public String abrirCarpeta() {
		return "Відкрити папку";
	}

	@Override
	public String actualizar() {
		return "Оновити";
	}

	@Override
	public String anadirRegistro() {
		return "Додати лог";
	}

	@Override
	public String usarIdiomaDelSistema() {
		return "Використовувати мову системи";
	}

	@Override
	public String volver() {
		return "Назад";
	}

	@Override
	public String colorFondo() {
		return "Колір фону (#RRGGBB):";
	}

	@Override
	public String colorTexto() {
		return "Колір тексту (#RRGGBB):";
	}

	@Override
	public String colorBoton() {
		return "Колір кнопки (#RRGGBB):";
	}

	@Override
	public String colorCajaTexto() {
		return "Колір текстового поля (#RRGGBB):";
	}

	@Override
	public String colorEnlace() {
		return "Колір посилання (#RRGGBB):";
	}

	@Override
	public String colorTitulosConsolas() {
		return "Колір заголовків консолі (#RRGGBB):";
	}

	@Override
	public String colorError() {
		return "Колір помилки (#RRGGBB):";
	}

	@Override
	public String colorAdvertencia() {
		return "Колір попередження (#RRGGBB):";
	}

	@Override
	public String colorInfo() {
		return "Колір інформації (#RRGGBB):";
	}

	@Override
	public String colorTitulo() {
		return "Колір заголовка (#RRGGBB):";
	}

	@Override
	public String colorEnlaceTexto() {
		return "Колір тексту посилання (#RRGGBB):";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "Відкривати " + Statics.nombre_cd.obtener() + " тільки при помилці";
	}

	@Override
	public String activar_parche() {
		return "Активувати патч";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "Рішення недоступне";
	}

	@Override
	public String error() {
		return "помилка";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "Помилка видалення JAR";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "JAR успішно видалено";
	}

	@Override
	public String exito() {
		return "успіх";
	}

	@Override
	public String eliminar() {
		return "видалити";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "Помилка видалення пакета";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "Пакет успішно видалено";
	}

	@Override
	public String eliminar_paquete() {
		return "Видалити пакет";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "Моди з таким пакетом не знайдено";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "Неможливо видалити пакет";
	}

	@Override
	public String eliminar_jar() {
		return "Видалити JAR";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "Дубльовані моди не знайдено";
	}

	@Override
	public String archivo_no_encontrado() {
		return "Файл не знайдено";
	}

	@Override
	public String directorio_eliminado() {
		return "Директорію видалено";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "Помилка видалення вкладеного JAR";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "Внутрішній файл не знайдено";
	}

	@Override
	public String archivo_eliminado() {
		return "файл видалено";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "помилка видалення файлу";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "некоректний зовнішній файл";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "Елемент мода видалено";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "Помилка заміни зовнішнього JAR";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "помилка видалення елемента мода";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "помилка видалення директорії";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "некоректний формат вкладеного JAR";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "вкладений JAR видалено";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "помилка очищення тимчасових файлів";
	}

	@Override
	public String mensaje_de_trace_fatal_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Останнє повідомлення FATAL trace (не перекладено):</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Останнє повідомлення trace (не перекладено):</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "Ви можете використовувати базу даних WaifuNeoForge для пошуку модів. Оберіть версію гри, модлоадер і клас.";
	}

	@Override
	public String solucionFaltasClases() {
		return "Ви можете використовувати базу даних WaifuNeoForge для пошуку модів. Оберіть версію гри, модлоадер і клас.";
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "Встановіть відповідну версію Java через менеджер пакетів або лаунчер.";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод: відсутня анімація: </b>";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException — відсутня анімація";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "Моди для видалення не знайдено";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "Замініть GC параметри на -XX:+UseG1GC";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "Збільште розмір heap пам’яті через -Xmx";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "Збільште PermGen пам’ять через -XX:MaxPermSize";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "Використовуйте 64-бітну JVM";
	}

	@Override
	public String optimizarCodigo() {
		return "Оптимізуйте код для зменшення використання пам’яті";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "Використовуйте ефективний збирач сміття";
	}

	@Override
	public String modulos() {
		return "Модулі";
	}

	@Override
	public String paquete() {
		return "Пакет";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "Відсутні ID. Можливі причини: відсутні моди або дані.";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "некоректно відображені логи";
	}

	// Переклад українською (Махновщина)

	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Плагін 'AuthMe' не зміг завантажитися і завершив роботу сервера.</b> ";
	}

	public String nombreProblemaCierreAuthMe() {
		return "Проблема завершення через AuthMe";
	}

	public String solucionCierreAuthMe() {
		return "Параметр 'stopServer' змінено на 'true'.";
	}

	public String solucionConfigurarPluginAuthMe() {
		return "Налаштуйте плагін AuthMe (plugins/AuthMe/config.yml)";
	}

	public String solucionInstalarVersionDiferenteAuthMe() {
		return "Встановіть іншу версію плагіна 'AuthMe'";
	}

	public String solucionEliminarPluginAuthMe() {
		return "Видаліть плагін 'AuthMe'";
	}

	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Світ '" + nombreMundo
				+ "' не вдалося завантажити — він містить помилки та, ймовірно, пошкоджений.</b> ";
	}

	@Override
	public String nombreProblemaCargaMultiverso() {
		return "Проблема завантаження Multiverse";
	}

	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "Відновіть світ '" + nombreMundo + "', наприклад за допомогою Minecraft Region Fixer або MCEdit.";
	}

	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "Видаліть папку світу '" + nombreMundo + "'.";
	}

	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Конфігурація плагіна 'PermissionsEx' є некоректною.</b> ";
	}

	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "Проблема конфігурації PermissionsEx";
	}

	@Override
	public String solucionConfigurarPermissionsEx() {
		return "Налаштуйте плагін PermissionsEx (plugins/PermissionsEx/permissions.yml)";
	}

	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "Видаліть плагін 'PermissionsEx'";
	}

	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Існує кілька файлів плагіна з назвою '"
				+ nombrePlugin + "': '" + primerPath + "' і '" + segundoPath + "'.</b> ";
	}

	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "Проблема неоднозначної назви плагіна";
	}

	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "Видаліть плагін '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Сталася помилка під час завантаження чанків світу. Якщо доступно для вашої платформи, мод FeatureRecycler може допомогти: https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
	}

	@Override
	public String nombreProblemaCargaChunk() {
		return "Помилка завантаження чанків";
	}

	@Override
	public String solucionEliminarChunk() {
		return "Видаліть проблемний чанк, наприклад через MCEdit або видаливши файл регіону.";
	}

	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагін '" + nombrePlugin
				+ "' не може виконати команду '/" + comando + "'.</b> ";
	}

	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "Помилка виконання команди плагіна";
	}

	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "Встановіть іншу версію плагіна '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагін '" + nombrePlugin
				+ "' потребує залежність '" + dependencia + "'.</b> ";
	}

	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагіну '" + nombrePlugin
				+ "' бракує таких залежностей: " + deps.toString() + ".</b> ";
	}

	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "Відсутня залежність плагіна";
	}

	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "Встановіть плагін '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагін '" + nombrePlugin
				+ "' потребує версію API '" + versionAPI + "', яка несумісна з поточним сервером.</b> ";
	}

	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "Несумісна версія API";
	}

	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "Встановіть версію '" + version + "' серверного ПЗ.";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Світ '" + nombreMundo
				+ "' є дублікатом іншого і не може бути завантажений.</b> ";
	}

	@Override
	public String nombreProblemaMundoDuplicado() {
		return "Дубльований світ";
	}

	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "Видаліть файл 'uid.dat' у світі '" + nombreMundo + "'";
	}

	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "Видаліть папку світу '" + nombreMundo + "'";
	}

	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		String color = config.obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>Сутність або блок-сутність '</span>" + nombre
				+ "<span style='color:#" + color + "'>' типу '</span>" + tipo + "<span style='color:#" + color
				+ "'>' за координатами </span>" + coords + "<span style='color:#" + color
				+ "'> викликає помилки ticking.</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'>" + "Інструкції відновлення:<br>"
				+ "1. **MCForge**: '[назва_світу]/serverconfig/forge-server.toml'<br>"
				+ "2. **NeoForge**: 'config/neoforge-server.toml'<br>"
				+ "3. Увімкніть **removeErroringBlockEntities** і **removeErroringEntities** = true<br><br>"
				+ "Альтернатива:<br>" + "- MCEdit: видаліть сутність вручну<br>"
				+ "- Neruina: може допомогти, але не завжди" + "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "Проблемна сутність";
	}

	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "Видаліть сутність '" + nombre + "' за координатами " + coords;
	}

	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod
				+ "' встановлений у кількох версіях.</b> ";
	}

	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "Дубльований мод (Fabric)";
	}

	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "Видаліть дубльований файл: " + rutaMod;
	}

	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Моди '" + primerMod + "' і '" + segundoMod
				+ "' несумісні.</b> ";
	}

	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "Несумісні моди";
	}

	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "Видаліть мод '" + nombreMod + "'";
	}

	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod
				+ "' має критичну помилку і не може працювати.</b> ";
	}

	@Override
	public String nombreProblemaModFatal() {
		return "Критична помилка мода";
	}

	@Override
	public String mensajeModDependenciaPlural(List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>Потрібні такі моди: " + deps.toString() + ".</b>";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + "' потребує '"
					+ dependencia + "'.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + "' потребує '"
					+ dependencia + "' версії " + version + ".</b>";
		}
	}

	@Override
	public String nombreProblemaDependenciaMod() {
		return "Відсутня залежність мода";
	}

	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "Встановіть мод '" + nombreMod + "'";
	}

	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "Встановіть мод '" + nombreMod + "' версії " + version;
	}

	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагін '" + nombrePlugin
				+ "' не підтримує регіональний ticking (Folia).</b> ";
	}

	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Ці плагіни не підтримують регіональний ticking (Folia): ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0)
				sb.append(", ");
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaPluginTickingRegional() {
		return "Несумісний плагін (Folia)";
	}

	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "Встановіть сервер без регіонального ticking, наприклад " + software;
	}

	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + "' відсутній у datapack.</b>";
	}

	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Відсутні моди в datapack: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1)
					sb.append(" і ");
				else
					sb.append(", ");
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaModFaltanteEnDatapack() {
		return "Відсутній мод у datapack";
	}

	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + "' спричинив помилку.</b>";
	}

	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Ці моди спричинили помилки: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1)
					sb.append(" і ");
				else
					sb.append(", ");
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	// Переклад українською (Махновщина)

	@Override
	public String nombreProblemaModExcepcion() {
		return "Мод Forge з помилкою";
	}

	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "Встановіть іншу версію мода '" + nombreMod + "'";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod
				+ "' не сумісний з версією Minecraft " + versionMC + ".</b>";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Ці моди не сумісні з версіями Minecraft: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0)
				sb.append(", ");
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (Minecraft ").append(versionesMC.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaModIncompatibleConMinecraft() {
		return "Мод несумісний з Minecraft";
	}

	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "Встановіть версію Forge, сумісну з Minecraft " + versionMC;
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod
				+ "' відсутній і є необхідним для запуску світу або плагіна.</b>";
	}

	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "Відсутній мод";
	}

	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Світ було збережено з модом '" + nombreMod
				+ "', який зараз відсутній.</b>";
	}

	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Світ було збережено з такими модами, які зараз відсутні: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1)
					sb.append(" і ");
				else
					sb.append(", ");
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaWorldModFaltante() {
		return "Відсутній мод у світі";
	}

	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Світ було збережено з модом '" + nombreMod
				+ "' версії " + versionEsperada + ", а зараз встановлена версія " + versionActual + ".</b>";
	}

	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("У цих модах є невідповідність версій у збереженому світі: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1)
					sb.append(" і ");
				else
					sb.append(", ");
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (Було: ").append(versionesEsperadas.get(i)).append(", Зараз: ").append(versionesActuales.get(i))
					.append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaVersionModMundo() {
		return "Версія мода у збереженому світі";
	}

	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Ви намагаєтесь завантажити світ, створений у новішій версії Minecraft.</b>";
	}

	@Override
	public String nombreProblemaVersionDowngrade() {
		return "Спроба завантаження старішою версією";
	}

	@Override
	public String solucionVersionDowngrade() {
		return "Встановіть новішу версію Minecraft.";
	}

	@Override
	public String solucionGenerarNuevoMundo() {
		return "Створіть новий світ.";
	}

	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагін '" + nombrePlugin
				+ "' потребує залежність '" + dependencia + "'.</b>";
	}

	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Ці плагіни потребують відсутні залежності: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0)
				sb.append(", ");
			sb.append("'").append(plugins.get(i)).append("' (").append(dependencias.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaDependenciaPluginFaltante() {
		return "Відсутня залежність плагіна";
	}

	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагін '" + nombrePlugin
				+ "' несумісний з поточною версією сервера.</b>";
	}

	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Ці плагіни несумісні з поточною версією сервера: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1)
					sb.append(" і ");
				else
					sb.append(", ");
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaPluginIncompatible() {
		return "Несумісний плагін";
	}

	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагін '" + nombrePlugin
				+ "' викликав помилку під час виконання.</b>";
	}

	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Ці плагіни викликали помилки під час виконання: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1)
					sb.append(" і ");
				else
					sb.append(", ");
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaPluginEjecucion() {
		return "Помилка виконання плагіна";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "LegacyRandomSource багатопотоковість";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Проблема з кількома потоками, що звертаються до LegacyRandomSource. Спробуйте моди Unsafe World Random Access Detector або C2ME.</b> ";
	}

	@Override
	public String desdeUltimoExito() {
		return "З останнього успіху";
	}

	@Override
	public String noHayCambios() {
		return "Немає змін";
	}

	@Override
	public String desdeUltimoIntento() {
		return "З останньої спроби";
	}

	@Override
	public String fallo() {
		return "Збій";
	}

	@Override
	public String diferentesDeLasMods() {
		return "Відмінності у модах";
	}

	// Переклад українською (Махновщина)

	@Override
	public String historialDeMods() {
		return "Історія модів";
	}

	@Override
	public String archivo0() {
		return "Файл 0";
	}

	@Override
	public String archivo1() {
		return "Файл 1";
	}

	@Override
	public String comparar() {
		return "Порівняти";
	}

	@Override
	public String seleccionarDosArchivos() {
		return "Обрати два файли";
	}

	@Override
	public String archivoExito() {
		return "Успішний файл";
	}

	@Override
	public String archivoFalla() {
		return "Файл із помилкою";
	}

	@Override
	public String errorComparandoArchivos() {
		return "Помилка під час порівняння файлів";
	}

	@Override
	public String comparando() {
		return "Порівняння";
	}

	@Override
	public String con() {
		return "з";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
				+ "<p><b>Панель порівняння історії модів</b></p>"
				+ "<p>Ця панель дозволяє порівнювати два списки модів з різних запусків. "
				+ "Оберіть файл зліва і справа, щоб побачити зміни між версіями.</p>"

				+ "<h3>Як використовувати:</h3><ol>"
				+ "<li><b>Вибір файлів:</b> Натисніть радіокнопки біля назв файлів. "
				+ "Файли з <span style='color: #4CAF50; font-weight: bold;'>.exito</span> — успішні, "
				+ "з <span style='color: #F44336; font-weight: bold;'>.falla</span> — з помилками.</li>"

				+ "<li><b>Порівняння:</b> Натисніть 'Порівняти', щоб побачити додані (+) і видалені (-) моди.</li>"

				+ "<li><b>Результати:</b> Відображаються з кольорами:"
				+ "<ul><li><span style='color: green;'>+ Додано</span></li>"
				+ "<li><span style='color: red;'>- Видалено</span></li></ul></li></ol>"

				+ "<h3>Особливості:</h3><ul>" + "<li>Підтримка будь-яких комбінацій файлів</li>"
				+ "<li>Двостороннє порівняння</li>" + "<li>Прокрутка для довгих списків</li>"
				+ "<li>Візуальні підказки</li></ul>"

				+ "<p>Створено для зручного аналізу змін у модах.</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Можлива проблема з IPv6. Рішення: "
				+ "1) Додайте JVM аргумент <code>-Djava.net.preferIPv4Stack=true</code>, або "
				+ "2) Використайте 'QuickFix' у " + Statics.nombre_cd.obtener() + "." + "</b>";
	}

	@Override
	public String parcheIPv4() {
		return "Патч IPv4/IPv6";
	}

	@Override
	public String carpetaHMCL() {
		return "Папка HMCL (HelloMinecraftLauncher)";
	}

	@Override
	public String descripcionCurseforge() {
		return "Примітка: лог не створюється, якщо увімкнено \"Пропустити лаунчер\".";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC: ПКМ по інстансу → \"Редагувати\" → \"Логи Minecraft\" (STDOUT).";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL: оберіть папку .hmcl — там зберігаються логи.";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: вкладка розробника містить логи.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: використовує вбудоване вікно логів.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: ПКМ → \"Налаштування\" → \"Логи\".";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "Вставте Markdown-посилання — система автоматично знайде логи.";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "Лог лаунчера не знайдено";
	}

	@Override
	public String imagenNoEncontrada() {
		return "Зображення не знайдено";
	}

	// Переклад українською (Махновщина)

	@Override
	public String noRegistroDeLauncher() {
		return "ЗАГАЛЬНЕ: Оберіть тип лаунчера, який ви використовуєте. Логи лаунчера (launcher_log.txt, stdout тощо) містять критично важливу інформацію про помилки, яка не відображається в latest.log. "
				+ Statics.nombre_cd.obtener()
				+ " не може читати логи вашого лаунчера, тому вам може знадобитися вставити їх вручну.<br>"
				+ "Для деталей див. <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663\">це обговорення</a>. Ці логи містять STDOUT, необхідний для діагностики багатьох помилок.";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Відсутні класи Create. Починаючи з Create 6 багато класів видалено, і старі аддони більше не працюють. Потрібно оновити аддони або використовувати сумісну версію.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Відсутні класи EpicFight. Багато змін і видалених класів. Потрібно оновити аддони або використовувати правильну версію.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>OpenJ9 не підтримується. Використовуйте Oracle JDK або OpenJDK HotSpot.</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Потрібен Java 11 (JDK 11). Поточна версія Java не сумісна.</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Виділено занадто багато пам’яті, що перевантажує систему.</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Зменште використання RAM до ~70–80% від загальної. Для 32-біт — максимум ~2–3 ГБ.</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Зменште параметр -Xmx у налаштуваннях лаунчера.</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Відсутній файл Forge: '" + archivo
				+ "'. Перевстановіть Forge.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Forge не знаходить версію Minecraft " + version
				+ " у файлі '" + archivo + "'.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Не знайдено 'fmlclient'. Перевстановіть Forge.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Не знайдено основний клас Minecraft. Перевстановіть Forge.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Інсталяція Forge неповна або пошкоджена.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "Неповна інсталяція Forge";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Перевстановіть Forge з офіційного джерела.</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "Завантажити Forge офіційно";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "Як перевстановити Forge";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body><h3>Інструкція:</h3><ol>" + "<li>Завантажте Forge</li>" + "<li>Закрийте лаунчер</li>"
				+ "<li>Запустіть інсталятор</li>" + "<li>Оберіть Installer</li>" + "<li>Виберіть папку</li>"
				+ "<li>Завершіть</li>" + "<li>Перезапустіть лаунчер</li></ol></body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "Інструкції Forge";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Не вдалося завантажити бібліотеку "
				+ nombreBiblioteca + ".</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "Помилка бібліотеки";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Спробуйте перевстановити Minecraft або налаштувати library path.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Конфлікт ID " + id + " між " + modOrigen + " і "
				+ modDestino + ".</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Перевищено допустимий діапазон ID.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "Конфлікт ID";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Встановіть JustEnoughIDs або EndlessIDs.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Використайте IDFix або Minecraft-ID-Resolver.</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "Встановити JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "Встановити EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "Використати IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "Використати Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "Переглянути японську документацію";
	}

	// Переклад українською (Махновщина)

	@Override
	public String escanearDeMCreator() {
		return "Сканувати MCreator";
	}

	/**
	 * Отримує заголовок вікна дерева модів.
	 */
	@Override
	public String tituloArbolDeMods() {
		return "Дерево модів і класів";
	}

	/**
	 * Текст для типу пошуку.
	 */
	@Override
	public String tipoBusqueda() {
		return "Тип";
	}

	@Override
	public String filtroTodos() {
		return "Усі";
	}

	@Override
	public String filtroPaquetes() {
		return "Пакети";
	}

	@Override
	public String filtroClases() {
		return "Класи";
	}

	@Override
	public String filtroMetodos() {
		return "Методи";
	}

	@Override
	public String filtroCampos() {
		return "Поля";
	}

	@Override
	public String filtroReferenciasCampo() {
		return "Посилання на поля";
	}

	@Override
	public String filtroReferenciasMetodo() {
		return "Посилання на методи";
	}

	@Override
	public String filtroReferenciasClase() {
		return "Посилання на класи";
	}

	@Override
	public String tipBuscar() {
		return "Введіть текст для пошуку в дереві модів";
	}

	@Override
	public String botonBuscar() {
		return "Пошук";
	}

	@Override
	public String botonResetearArbol() {
		return "Скинути дерево";
	}

	@Override
	public String modsCargados() {
		return "Завантажені моди";
	}

	@Override
	public String clases() {
		return "Класи";
	}

	@Override
	public String metodos() {
		return "Методи";
	}

	@Override
	public String campos() {
		return "Поля";
	}

	@Override
	public String referencias() {
		return "Посилання";
	}

	@Override
	public String resultadosBusqueda() {
		return "Результати пошуку";
	}

	@Override
	public String buscarReferencias() {
		return "Шукати посилання";
	}

	@Override
	public String referenciasMod() {
		return "Посилання мода";
	}

	@Override
	public String referenciasClase() {
		return "Посилання класу";
	}

	@Override
	public String referenciasMetodo() {
		return "Посилання методу";
	}

	@Override
	public String referenciasCampo() {
		return "Посилання поля";
	}

	@Override
	public String noSeEncontraronReferencias() {
		return "Посилань не знайдено";
	}

	@Override
	public String detalleMod() {
		return "Деталі мода:";
	}

	@Override
	public String ubicacion() {
		return "Розташування";
	}

	@Override
	public String nombres() {
		return "Назви";
	}

	@Override
	public String modulo() {
		return "Модуль";
	}

	@Override
	public String detalleClase() {
		return "Деталі класу:";
	}

	@Override
	public String detalleMetodo() {
		return "Деталі методу:";
	}

	@Override
	public String detalleCampo() {
		return "Деталі поля:";
	}

	@Override
	public String clase() {
		return "Клас";
	}

	@Override
	public String tipo() {
		return "Тип";
	}

	@Override
	public String referenciasAMetodos() {
		return "Посилання на методи:";
	}

	@Override
	public String referenciasACampos() {
		return "Посилання на поля:";
	}

	@Override
	public String arbolDeMods() {
		return "Дерево модів";
	}

	@Override
	public String metodo() {
		return "Метод";
	}

	@Override
	public String campo() {
		return "Поле";
	}

	@Override
	public String descompilar() {
		return "Декомпілювати";
	}

	@Override
	public String exportar() {
		return "Експортувати";
	}

	@Override
	public String importar() {
		return "Імпортувати";
	}

	@Override
	public String errorImportar() {
		return "Помилка імпорту";
	}

	@Override
	public String estructuraImportada() {
		return "Імпортована структура";
	}

	@Override
	public String estructuraExportada() {
		return "Експортована структура";
	}

	@Override
	public String errorExportar() {
		return "Помилка експорту";
	}

	@Override
	public String exportando() {
		return "Експорт...";
	}

	@Override
	public String exportacionTardara() {
		return "Експорт може зайняти час";
	}

	@Override
	public String porFavorEspere() {
		return "Будь ласка, зачекайте";
	}

	// Переклад українською (Махновщина)

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>У вас відсутні бінарні файли VLC. Вони необхідні для WaterMedia. "
				+ "Встановіть VLC вручну з https://www.videolan.org/vlc/.</b>";
	}

	@Override
	public String descargar_vlc() {
		return "Завантажити VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Критична помилка: ім'я моду '" + nombreModulo
				+ "' містить недопустимі символи. Частина '" + parteInvalida
				+ "' не є коректним Java-ідентифікатором.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "Недопустимі символи в імені моду";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "Мод '" + nombreModulo + "' має некоректне ім'я через '" + parteInvalida
				+ "'. Перевірте логи, щоб визначити відповідний JAR-файл.";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "Відкрийте <b>mods.toml</b> у <b>/META-INF/</b> і змініть <b>modId</b> на коректний (лише літери, цифри, _).";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "Приклад: 'truemod_shot_enchantment' замість 'true.shot.enchantment'.";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Критична помилка в моді '" + nombreJar
				+ "': відсутнє поле 'mandatory' у залежностях.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "Відсутнє обов'язкове поле залежності";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "Проблемний мод: <b>" + nombreJar + "</b>.";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "Відкрийте <b>mods.toml</b> у моді <b>" + nombreJar + "</b>.";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "Додайте <b>mandatory=true/false</b> до кожної залежності.";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Критична помилка в моді '" + nombreJar
				+ "': некоректний access transformer.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Некоректний Access Transformer";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "Проблемний мод: <b>" + nombreJar + "</b>.";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "Відкрийте <b>accessTransformer.cfg</b> у моді.";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "Виправте синтаксис або видаліть неіснуючі посилання.";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Критична помилка: невідповідність ID моду '"
				+ nombreMod + "'.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "Невідповідність ID моду";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "ID у @Mod і mods.toml не збігаються.";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "Переконайтесь, що modId однаковий у коді та mods.toml.";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "Запустіть Gradle clean після змін.";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "клієнт" : "сервер";
		String opuesta = entornoInvalido.equals("CLIENT") ? "сервер" : "клієнт";

		return "<b style='color:#" + config.obtenerColorError() + "'>Критична помилка: клас '" + nombreClase
				+ "' призначений для " + opuesta + ", але запускається на " + plataforma + ".</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "Невірна платформа моду";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "Знайдіть мод через 'Дерево модів'.";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		return "Видаліть мод або використовуйте правильну версію.";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "Встановіть сумісний мод.";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Відсутня metadata для '" + modIdFaltante
				+ "'.</b>";
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "Відсутня metadata mods.toml";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		return "Деякі моди залежать від '" + modIdFaltante + "'.";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Встановіть або видаліть залежний мод.";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Перевірте версію Forge/Minecraft.";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Помилка звуку. Звук вимкнено.</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "Помилка аудіо";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "Перевірте SoundPhysicsMod.";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "Спробуйте видалити конфліктні моди.";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "Перевірте папку logs.";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Клас '" + nombreClase
				+ "' не має @SubscribeEvent.</b>";
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "Клас без listeners";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		return "Знайдіть мод через дерево модів.";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "Додайте метод з @SubscribeEvent.";
	}

	// Переклад українською (Махновщина)

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("Для виявлених модів (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", тощо");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("зв’яжіться з розробником цього моду для виправлення. ");
			} else {
				paso.append("зв’яжіться з розробниками цих модів для виправлення. ");
			}
		}

		paso.append(
				"Якщо ви розробник — видаліть реєстрацію цього класу з EventBus або додайте коректні методи @SubscribeEvent");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		String mensaje = "<b style='color:#" + config.obtenerColorError()
				+ "'>Критична помилка: помилка UnionFileSystem при обробці '" + nombreArchivo + "'. ";

		mensaje += "Це дуже поширена проблема у модпаках і зазвичай пов’язана з лаунчером. ";
		mensaje += "Файли модів пошкоджені або неповністю завантажені.</b>";

		return mensaje;
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "UnionFileSystem — пошкоджений файл";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		return "Виявлено помилку <b>UnionFileSystem$UncheckedIOException</b> з файлом <b>" + nombreArchivo
				+ "</b> — файл завантажено не повністю.";
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "Повністю перевстановіть модпак. Рекомендується використовувати <b>ATLauncher</b> замість Luna Pixel.";
	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "Якщо проблема лишається:<br/>" + "1. Змініть лаунчер<br/>" + "2. Використовуйте ATLauncher<br/>"
				+ "3. Перевірте інтернет і місце на диску";
	}

	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "Увімкнути ProxySysOutSysErr?\n\n" + "Це дає доступ до System.out/System.err.\n\n"
				+ "Використовуйте лише якщо немає логів.\n\n" + "Попередження: може викликати конфлікти.\n\n"
				+ "Потрібен перезапуск.";
	}

	@Override
	public String confirmacionTitulo() {
		return "Підтвердження";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr увімкнено.\n\nПотрібен перезапуск " + Statics.nombre_cd.obtener() + ".";
	}

	@Override
	public String informacionTitulo() {
		return "Інформація";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {

		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("Критична помилка: AzureLib і GeckoLib ініціалізовані занадто рано! ");
		} else if (azureLibError) {
			mensaje.append("Критична помилка: AzureLib ініціалізований занадто рано! ");
		} else if (geckoLibError) {
			mensaje.append("Критична помилка: GeckoLib ініціалізований занадто рано! ");
		}

		mensaje.append("Це відбувається при змішуванні Fabric і Forge модів. ");

		if (connectorPresente) {
			mensaje.append("Виявлено мод сумісності — ви запускаєте Fabric моди на Forge. ");
		}

		mensaje.append("Бібліотеки повинні відповідати платформі.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "Бібліотека ініціалізована занадто рано";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "Знайдіть проблемний мод у логах.";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "Перевірте правильну версію бібліотеки.";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Критична помилка: C2ME несумісний з модами сумісності. Використовуйте C3ME.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "Несумісність C2ME";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "Видаліть C2ME.";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "Встановіть C3ME.";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "Оновіть моди.";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Критична помилка: JEI plugin у моді '" + modId
				+ "' викликав crash.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "Помилка JEI плагіна";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "Мод <b>" + modId + "</b> спричиняє crash.";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "Тимчасово видаліть мод.";
	}

	// Переклад українською (Махновщина)

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "Перевірте оновлення моду <b>" + modId
				+ "</b> або зверніться до розробника. Тимчасово мод слід видалити.";
	}

	/**
	 * Заголовок застосунку
	 */
	@Override
	public String tituloLectador() {
		return "Аналізатор консолі - Crash Detector";
	}

	/**
	 * Заголовок легенди кольорів
	 */
	@Override
	public String obtenerTituloLeyenda() {
		return "ЛЕГЕНДА КОЛЬОРІВ";
	}

	@Override
	public String obtenerErrorEnLeyenda() {
		return "Критичні помилки";
	}

	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "Стек-трейси";
	}

	@Override
	public String obtenerTituloError() {
		return "Помилка";
	}

	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "Помилка при обробці вибраного рядка";
	}

	@Override
	public String obtenerNombreError() {
		return "НАЗВА ПОМИЛКИ";
	}

	@Override
	public String obtenerDescripcionError() {
		return "ДЕТАЛЬНИЙ ОПИС";
	}

	@Override
	public String obtenerSeleccionarConsola() {
		return "ОБРАТИ КОНСОЛЬ";
	}

	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "Невідома помилка";
	}

	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "Виявлено критичну помилку. Перегляньте стек для деталей.";
	}

	@Override
	public String obtenerErrorLecturaArchivo() {
		return "Не вдалося прочитати файл логів. Перевірте доступ.";
	}

	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "Аналіз логів";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Критична помилка: збій реєстрації авто-підписників у моді '").append(modId).append("'. ");

		mensaje.append("Клас: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Знаходиться в: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", та інші...");
			mensaje.append("</b>. ");
		}

		mensaje.append("Клас не може бути завантажений або некоректний.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "Помилка авто-підписників";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "Мод <b>" + modId + "</b> не може зареєструвати клас <b>" + nombreClase + "</b>.";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {

		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("Клас <b>" + nombreClase + "</b> знаходиться у: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", та інші...");
			paso.append("</b>. Використайте 'Дерево модів' для перевірки.");
			return paso.toString();
		}

		return "Клас не знайдено. Перевірте встановлення моду.";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "Оновіть мод <b>" + modId + "</b> або зверніться до розробника.";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "Перевірте інші помилки у логах — причина може бути раніше.";
	}

	// Переклад українською (Махновщина)

	@Override
	public String limpiado() {
		return "Очищено";
	}

	@Override
	public String original() {
		return "Оригінал";
	}

	@Override
	public String verEnConsola() {
		return "Переглянути в консолі";
	}

	@Override
	public String advertencia() {
		return "Попередження";
	}

	@Override
	public String fatal() {
		return "Фатальна помилка";
	}

	@Override
	public String noRegistroDeBattly() {
		return "BattlyLauncher не має логів або консолі. Можна використати ProxySysOutSysErr, але можливі конфлікти.";
	}

	@Override
	public String noRegistroDeNightWorld() {
		return "Увімкніть режим налагодження в NightWorld для отримання логів (STDOUT/STDERR).";
	}

	@Override
	public String noRegistroDeMCServidor() {
		return "Збережіть або вставте лог із терміналу сервера (містить STDOUT/STDERR).";
	}

	// LexForge → NeoForge

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {

		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("Критична помилка: трансформер LexForge у середовищі NeoForge.</b> ");

		sb.append("Клас: <b>").append(claseReceptora).append("</b>. ");
		sb.append("Інтерфейс: <b>").append(interfazObjetivo).append("</b>. ");
		sb.append("Відсутній метод: <b>").append(firmaMetodoFaltante).append("</b>. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Знайдено в: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", та інші...");
			sb.append("</b>. ");
		}

		sb.append("Несумісність Forge ↔ NeoForge.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "LexForge трансформер у NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "Знайдіть трансформер: <b>" + claseReceptora + "</b>.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		return "Тимчасово видаліть підозрілі моди.";
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "Використовуйте NeoForge-сумісну версію.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "Очистіть кеш і перезапустіть.";
	}

	// WaterMedia / Xenon

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		return "<b style='color:#" + config.obtenerColorError() + "'>WaterMedia не працює з Xenon. Видаліть Xenon.</b>";
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "Несумісність WaterMedia / Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		return "Видаліть Xenon.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		return "Перевірте папку mods.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "Встановіть Embeddium або Sodium.";
	}

	// TACZ

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "Помилка TACZ";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		return "<b>Deflater закрито.</b> Увімкніть DefaultPackDebug.";
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "Увімкніть DefaultPackDebug=true.";
	}

	// Density functions

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "Відсутні функції щільності";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		return "<b>Відсутні функції щільності.</b>";
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "Встановіть потрібний datapack/mod.";
	}

	// Create Railways

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Помилка Create Railways alpha.</b>";
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create Railways alpha";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "Видаліть alpha-версію.";
	}

	// Multiworld conflict

	@Override
	public String errorConflictoMultiworldRendimiento() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Конфлікт Multiworld з модами продуктивності.</b>";
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "Конфлікт Multiworld";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Видаліть один із модів.";
	}

	// Sodium GPU

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Несумісний GPU драйвер для Sodium.</b>";
	}

	// OpenGL

	@Override
	public String nombreErrorContextoOpenGL() {
		return "Помилка OpenGL";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Помилка OpenGL.</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "Оновіть драйвери.";
	}

	// Misc

	@Override
	public String copiadoAlPortapapeles() {
		return "Скопійовано в буфер обміну.";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		return "Пошук у архівах (.zip/.jar)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Помилка текстури: " + recurso + " (" + tamaño
				+ ")</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "Помилка текстур";
	}

	// Переклад українською (Махновщина)

	@Override
	public String solucion_resolucion_textura() {
		return "Ця помилка виникає, коли текстури занадто великі або підключено забагато ресурс-паків. "
				+ "Спробуйте використовувати пакети з меншою роздільною здатністю або видаліть частину ресурс-паків. "
				+ "Перевірте, чи не додали ви власні текстури з надто великою роздільною здатністю.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Помилка ModLauncher: шлях містить недопустимі символи. "
				+ "Сервіси ModLauncher не можуть обробляти шляхи з не-ASCII або спеціальними символами. "
				+ "Проблемні символи: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요, а також символ '\"' наприкінці імені. "
				+ "Рішення: перейменуйте інстанс, використовуючи лише ASCII (a-z, A-Z, 0-9), без пробілів і спецсимволів.</b>";
	}

	@Override
	public String nombre_error_modlauncher_path() {
		return "Помилка шляху ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "Помилка виникає через не-ASCII або спеціальні символи в шляху. "
				+ "Рішення: використовуйте лише ASCII (a-z, A-Z, 0-9), без пробілів і спецсимволів.";
	}

	@Override
	public String tituloEditorCodice() {
		return "Редактор Codice";
	}

	@Override
	public String nuevo() {
		return "Новий";
	}

	@Override
	public String actualizarSeleccionado() {
		return "Оновити вибране";
	}

	@Override
	public String eliminarSeleccionado() {
		return "Видалити вибране";
	}

	@Override
	public String exportarJSON() {
		return "Експортувати JSON...";
	}

	@Override
	public String guardarTodo() {
		return "Зберегти все";
	}

	@Override
	public String general() {
		return "Загальні";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "Текст для пошуку";
	}

	@Override
	public String filtro() {
		return "Фільтр (id)";
	}

	@Override
	public String criticalidad() {
		return "Критичність (ПОПЕРЕДЖЕННЯ/ПОМИЛКА/ФАТАЛЬНО)";
	}

	@Override
	public String prioridad() {
		return "Пріоритет";
	}

	@Override
	public String lista() {
		return "Перевірки";
	}

	@Override
	public String colIdioma() {
		return "Мова";
	}

	@Override
	public String colNombre() {
		return "Назва";
	}

	@Override
	public String colResultado() {
		return "Результат";
	}

	@Override
	public String vistaJson() {
		return "Попередній перегляд JSON";
	}

	@Override
	public String idiomas() {
		return "Мови (обов’язкові)";
	}

	@Override
	public String elegirFiltro() {
		return "Обрати...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "Оберіть фільтр";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "Доступні фільтри";
	}

	@Override
	public String faltanCampos() {
		return "Заповніть усі обов’язкові поля.";
	}

	@Override
	public String critInvalida() {
		return "Невірна критичність. Використовуйте ПОПЕРЕДЖЕННЯ, ПОМИЛКА або ФАТАЛЬНО.";
	}

	@Override
	public String filtroNoExiste() {
		return "Вказаний фільтр не існує.";
	}

	@Override
	public String faltanIdiomas() {
		return "Заповніть назву та результат для всіх мов:";
	}

	@Override
	public String verificacionInvalida() {
		return "Перевірка недійсна. Перевірте поля.";
	}

	@Override
	public String guardadoOk() {
		return "Успішно збережено.";
	}

	@Override
	public String editorCodiceBoton() {
		return "Додати причини";
	}

	@Override
	public String descripcionEditorCodice() {
		return "Тут можна додавати причини. Потрібен ID без спецсимволів. "
				+ "Фільтри: \"linea contiene\", \"todo contiene\", \"regex linea\", \"regex todos\". "
				+ "Вкажіть критичність: ФАТАЛЬНО, ПОМИЛКА або ПОПЕРЕДЖЕННЯ.";
	}

	@Override
	public String descartarCambios() {
		return "Скасувати незбережені зміни?";
	}

	@Override
	public String confirmacion() {
		return "Підтвердження";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "Зберегти перед виходом?";
	}

	@Override
	public String salirSinGuardar() {
		return "Вийти без збереження";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Критична помилка: не вдалося завантажити сервіс ModLauncher.<br>");
		sb.append("Клас: <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("Мод: ").append(String.join(", ", mods)).append("<br>");
		}

		sb.append("Причина: пошкоджений або несумісний META-INF/services.<br>");
		sb.append("Рішення: оновіть або видаліть проблемний мод.</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "Помилка конфігурації сервісу";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		return mods == null || mods.isEmpty() ? "Знайдіть проблемний мод."
				: "Проблемний мод: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "Оновіть або видаліть мод.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Критична помилка: поле не існує.</b><br>"
				+ "Поле: " + campo + "<br>" + "<span style='font-family:monospace;'>" + escapeHtml(lineaCompleta)
				+ "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "Поле не існує (NoSuchFieldError)";
	}

	// Переклад українською (Махновщина)

	@Override
	public String paso1_campo_inexistente() {
		return "1. Ця помилка зазвичай виникає, коли мод несумісний з поточною версією гри або іншого мода.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. Оновіть усі моди. Якщо проблема не зникне — зверніться до автора мода.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Критична помилка: метод не існує.</b><br>"
				+ "Метод: " + metodo + "<br>" + "<span style='font-family:monospace;'>" + escapeHtml(lineaCompleta)
				+ "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "Метод не існує (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. Помилка через несумісність модів.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. Оновіть моди або зверніться до автора.";
	}

	@Override
	public String mensajeAyudar() {
		return "<div>Потрібна допомога? Скористайтесь кнопкою «Поділитися» для отримання підтримки.</div>";
	}

	@Override
	public String restablecerPlantilla() {
		return "Скинути шаблон";
	}

	@Override
	public String restablecer() {
		return "Скинути";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		return "Скинути " + nombreImagen + "?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		return "Скинути шаблон?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Відсутні класи AzureLib. Встановіть сумісну версію.</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Мод healight викликає помилку NoSuchFieldError: INT.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "Оновіть або видаліть мод healight.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "Помилка healight";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		return "<b>Клас " + clase + " не реалізує метод " + metodo + ".</b>";
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "Оновіть моди.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "AbstractMethodError";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b>Клієнтський клас завантажено на сервері.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "Оновіть або видаліть проблемний мод.";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "Клієнтський клас на сервері";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b>Конфігурація Connector пошкоджена.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "Видаліть config файл і перезапустіть.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "Connector config error";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b>JAR файл пошкоджений: " + nombreJar + "</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "Пошкоджений JAR";
	}

	@Override
	public String solucionJarCorrupto() {
		return "Перевстановіть файл.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b>Світ пошкоджений (NBT, байт " + byteCorrupto + ")</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "Відновіть із резервної копії.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "Пошкоджений світ";
	}

	@Override
	public String problema_con_openAL() {
		return "<span>Проблема з OpenAL.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b>Файл заблокований іншим процесом.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "Закрийте всі процеси.";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "Файл заблоковано";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b>Спроба успадкувати final клас.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "Оновіть моди.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "Final class error";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b>Rubidium застарілий з Iris.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "Використовуйте Embeddium.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium conflict";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b>Порт зайнятий.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "Змініть порт.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Voice chat port error";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b>BlockItem null: " + nombreBlockItem + "</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "Оновіть моди Create.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "BlockItem null";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		return "<b>Несумісні моди.</b>";
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "Використовуйте одну платформу.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "Несумісні моди";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b>Помилка моделі: " + modid + ":" + nombreModelo + "</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "Оновіть мод.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "Model error";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b>Конфлікт Moonlight / Iceberg.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "Оновіть або видаліть один мод.";
	}

	// Переклад українською (Махновщина)

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "Критичний конфлікт: Moonlight vs Iceberg (OpenGL без контексту)";
	}

	@Override
	public String instantanea() {
		return "Знімок";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "З останнього знімка";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "Обрати файл";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "Знімок створено успішно";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "Помилка створення знімка";
	}

	@Override
	public String consejo() {
		return "Порада";
	}

	@Override
	public String resultadoMuestra() {
		return "Результат";
	}

	@Override
	public String historaDeModsDesc() {
		return "<html><body style='font-family:sans-serif;font-size:12px;'>"
				+ "<b>Порада:</b> Оберіть два файли історії для порівняння модів." + "</body></html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		return "Отримати посилання як Markdown";
	}

	@Override
	public String titulo_configuracion() {
		return "Налаштування";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "Неочікувана помилка при поширенні.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "Помилка генерації посилань.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "Помилка обробки кнопки.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "Немає файлу для відкриття.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "Файл не існує:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "Не вдалося відкрити. Шлях скопійовано.";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "Не вдалося відкрити файл. Шлях скопійовано.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "Робоче середовище не підтримується.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "Досягнуто ліміту запитів.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		return "Поділитися посиланням";
	}

	@Override
	public String infoDeTrazos() {
		return "<b>Аналіз логів: перевіряйте нижчі рівні трасування.</b>";
	}

	@Override
	public String buscador_canario_de_orden_label() {
		return "Пошук canary";
	}

	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "Незабаром доступно.";
	}

	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "Скоро";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		return "Несумісні моди (Crash Assistant)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		return "Несумісний модпак";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		return "<b>Crash Assistant може помилятися щодо несумісності модів.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b>Можливі хибні несумісності модів.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "Мод '" + modId + "' потребує '" + dependencia + "'.";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "Мод '" + modId + "' потребує '" + dependencia + "' версії " + requerido + "+.";
	}

	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "Несумісність '" + modId + "' з '" + dependencia + "'.";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "Помилка виконання задачі: " + clase;
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "Помилки виконання";
	}

	public String recomendacion_fallos_ejecucion() {
		return "Перевірте сумісність модів.";
	}

	public String info_clase_problematica() {
		return "Проблемний клас:";
	}

	public String no_se_encontraron_clases_problema() {
		return "Проблемні класи не знайдені.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b>Конфлікт OptiFine та EMF.</b>";
	}

	// Переклад українською (Махновщина)

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "Конфлікт OptiFine та Entity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "Видаліть OptiFine або Entity Model Features — вони несумісні.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Критичний конфлікт OptiFine і Fusion.</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "Конфлікт OptiFine і Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "Видаліть OptiFine або Fusion.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Flywheel потребує Sodium 0.6.0+ або використовуйте Embeddium.</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "Конфлікт Flywheel і Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "Оновіть Sodium або встановіть Embeddium.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Конфлікт OptiFine і Epic Fight.</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "Конфлікт OptiFine і Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "Видаліть один із модів.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Конфлікт OptiFine і Rubidium.</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "Конфлікт OptiFine і Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "Видаліть один із модів.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b>FreeCam не працює на сервері.</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam на сервері";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "Видаліть FreeCam із сервера.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b>ETF не працює на сервері.</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "ETF на сервері";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "Видаліть ETF із сервера.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b>Потрібно прийняти EULA.</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "EULA не прийнято";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "Змініть eula=false на eula=true.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b>OptiFine не працює на сервері.</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine на сервері";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "Видаліть OptiFine із сервера.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b>Невірна версія Iron's Spellbooks.</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "Помилка версії Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "Встановіть правильну версію мода.";
	}

	// Переклад українською (Махновщина)

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Критичний конфлікт OptiFine і Embeddium.</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "Конфлікт OptiFine і Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "Видаліть OptiFine або Embeddium.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span>Проблема з модами генерації світу або відсутньою залежністю.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b>Controllable не працює на сервері.</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable на сервері";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "Видаліть Controllable із сервера.";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b>Supplementaries викликає помилку завантаження.</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Помилка Supplementaries";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "Оновіть або вимкніть мод.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b>Відсутній модуль Jackson (Groovy Modloader).</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "Відсутній модуль Jackson";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "Видаліть Groovy Modloader або пов’язані моди.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b>Невірне ім’я блоку (Every Compat).</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "Помилка Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "Перевірте моди або ресурси.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b>Помилка -1073741819 (оверлеї або драйвери).</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "Код помилки -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "Вимкніть оверлеї та оновіть драйвери.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b>Immersive Tooltips потребує Immersive Messages.</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Відсутня залежність";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "Встановіть Immersive Messages.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b>Помилка сумісності Medieval Origins.</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "Помилка Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "Використовуйте іншу версію.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b>Помилка MusicManager (Reign of Nether).</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "Помилка Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "Оновіть або видаліть мод.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b>YesSteveModel не підтримується.</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "Оновіть мод.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b>Конфлікт Moving Elevators і OptiFine.</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "Moving Elevators vs OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "Видаліть один мод.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b>Конфлікт Fabric API і OptiFine.</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "Fabric API vs OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "Видаліть або оновіть моди.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b>Помилка ITransformationService: " + claseProveedor + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "Помилка TransformationService";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "Видаліть мод із класом " + claseProveedor;
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span>Невірна версія мода: " + version + "</span>";
	}

	// Переклад українською (Махновщина)

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "Невірна версія мода";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "Використай утиліту grep/greprf на бічній панелі, щоб знайти проблемну версію та визначити мод.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Виявлено помилку stack smashing, що завершила процес. "
				+ "Причиною можуть бути проблеми з Early Window у Forge/NeoForge/PillowMC або з LWJGL 3.2.2 і новіше."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "Виявлено stack smashing";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "Перевір налаштування Early Window і спробуй іншу версію LWJGL або перевір моди, пов’язані з раннім вікном.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore призначений лише для конкретного модпака і не підходить для звичайних інсталяцій."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "Несумісна версія GregTechEasyCore";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "Видали GregTechEasyCore, він не сумісний із загальною інсталяцією.";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Конфлікт між MoniLabs і Connector Extras (модифікації KubeJS)." + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "Конфлікт MoniLabs і Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "Видали один із модів (MoniLabs або Connector Extras).";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris потребує Distant Horizons 2.0.4 або DH API 1.1.0+." + "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Сумісність Iris і Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "Онови Iris і Distant Horizons до сумісних версій.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Відсутні класи Minecraft. Причини:</b><ul>"
				+ "<li>Моди для іншої версії гри</li>" + "<li>Пошкоджена інсталяція</li>" + "<li>Зламаний coremod</li>"
				+ "</ul>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Відсутні класи DangerZone.</b>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Відсутні класи FeatureCreep.</b>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Відсутні класи ModLauncher.</b>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Відсутні класи Minecraft Forge.</b>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Відсутні класи NeoForge.</b>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Відсутні класи Fabric Loader.</b>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Відсутні класи PillowMC.</b>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "У тебе мод, який навмисно викликає лаги (Uranium)." + "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack має неправильну версію (1.19 vs 1.20)." + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Помилка версії Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "Встанови правильну версію мода.";
	}

	@Override
	public String necesitasInstalarCfr() {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>").append("Потрібно встановити CFR для використання цієї функції.<br><br>")
				.append("Завантаження:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">CFR</a><br><br>")
				.append("Поклади у:<br><b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>").append("Перезапусти мод після встановлення.").append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "Портрета нема";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "Клясу не знайдено: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "CFR-декомпілятор – Sakura Riddle (неофіційний варіант)";
	}

	@Override
	public String cfrClaseActual() {
		return "Нинішня кляса";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "Портрет Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "Лихо при завантаженні портрета";
	}

	public String noticiaLegalCFR() {
		return "Ся програма (GUI) для декомпіляції модів зроблена, аби люди могли зрозуміти, чого ламається програма. "
				+ "Та все ж декомпіляція — діло обережне, бо не варто брати код і ламати закон про авторське право. "
				+ "Ліпше спершу глянути на ліцензію моду, перш ніж щось уживати. Часто самі моди дають свій код, "
				+ "і він чистіший та зрозуміліший, ніж той, що витягнутий із декомпіляції. Пам’ятайте: шанувати чужу працю — "
				+ "то важлива річ для всієї спільноти. Закон про авторське право можна глянути тут: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Закон (іспанською)</a> "
				+ "і англійською тут: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law</a>. "
				+ "А ще є закон США: "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>. "
				+ "Кожен нехай дивиться, які закони діють у нього. "
				+ "Наш GUI — лиш для простих перевірок; для глибшого розбору беріть Enigma від FabricMC на "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>. "
				+ "А як треба правити JAR без вихідного коду — є Recaf на "
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">їхньому сайті</a>.";
	}

	@Override
	public String botonDescargarCfr() {
		return "Завантажити CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "Відкрити теку";
	}

	@Override
	public String colorFondoPrincipal() {
		return "Головний колір тла";
	}

	@Override
	public String colorTextoBotonReset() {
		return "Колір напису на кнопці скидання";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "Колір тексту пошуку";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "Колір тексту в списку фільтра";
	}

	@Override
	public String colorTextoRenderer() {
		return "Колір тексту рендеру";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "Колір тексту завантаження";
	}

	@Override
	public String colorBorde() {
		return "Колір краю";
	}

	@Override
	public String colorFondoRetrato() {
		return "Колір тла в портретному вигляді";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "Колір посилання";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "Колір тла поля";
	}

	@Override
	public String rosaFondo() {
		return "Рожеве тло";
	}

	@Override
	public String rosaSuave() {
		return "М’який рожевий";
	}

	@Override
	public String moradoAcento() {
		return "Фіолетовий наголос";
	}

	@Override
	public String textoOscuro() {
		return "Темний текст";
	}

	@Override
	public String bordeSuave() {
		return "М’який край";
	}

	@Override
	public String fondoCampo() {
		return "Тло поля";
	}

	@Override
	public String fondoVistaPrevia() {
		return "Тло перегляду";
	}

	@Override
	public String sintaxisConstructor() {
		return "Синтаксис: конструктор";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "Синтаксис: помічне повідомлення";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "Синтаксис: HTML-теги";
	}

	@Override
	public String colorFondoVentana() {
		return "Тло вікна";
	}

	@Override
	public String colorPanel() {
		return "Колір панелі";
	}

	@Override
	public String colorBotonTexto() {
		return "Колір тексту кнопки";
	}

	@Override
	public String colorCampo() {
		return "Колір поля";
	}

	@Override
	public String colorBordeDestacado() {
		return "Колір виділеного краю";
	}

	@Override
	public String colorSeleccionTexto() {
		return "Тло виділення тексту";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "Колір виділеного тексту";
	}

	@Override
	public String colorEstadoExito() {
		return "Стан: вдалось";
	}

	@Override
	public String colorEstadoFallo() {
		return "Стан: не вдалось";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "Стан: мить";
	}

	@Override
	public String colorResultadoAnadido() {
		return "Доданий результат";
	}

	@Override
	public String colorResultadoEliminado() {
		return "Видалений результат";
	}

	@Override
	public String colorBordeScroll() {
		return "Край прокрутки";
	}

	@Override
	public String colorFondoPanel() {
		return "Тло панелі";
	}

	@Override
	public String colorBeigeListas() {
		return "Беж списків";
	}

	@Override
	public String colorTextoListas() {
		return "Текст у списках";
	}

	@Override
	public String colorBordeListas() {
		return "Край списків";
	}

	@Override
	public String colorBotonFondo() {
		return "Тло кнопки";
	}

	@Override
	public String colorBordeBoton() {
		return "Край кнопки";
	}

	@Override
	public String colorDoradoTexto() {
		return "Золотий текст";
	}

	@Override
	public String colorPila() {
		return "Колір стосу (stack trace)";
	}

	@Override
	public String colorTextoPanel() {
		return "Текст панелі";
	}

	@Override
	public String colorTextoNegro() {
		return "Чорний текст";
	}

	@Override
	public String colorTextoPrincipal() {
		return "Головний текст";
	}

	@Override
	public String colorFondoResultados() {
		return "Тло результатів";
	}

	@Override
	public String colorEstado() {
		return "Колір стану";
	}

	@Override
	public String colorTextoDescripcion() {
		return "Колір тексту опису";
	}

	@Override
	public String colorTextoEstado() {
		return "Колір тексту стану";
	}

	@Override
	public String colorTextoExtra() {
		return "Колір додаткового тексту";
	}

	@Override
	public String colorSeparador() {
		return "Колір роздільника";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "Виявлено рідну помилку <code>StubRoutines::SafeFetch32</code>. "
				+ "Ся біда трапляється на macOS із JDK 17.0.9 та виправлена в JDK 17.0.10 або новіших версіях. https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "Рідна помилка SafeFetch32 у JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "Онови свій JDK до версії 17.0.10 або новішої (наприклад, 17.0.15).";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "Коли користуєшся пускачем, як-от MultiMC, Prism Launcher чи TLauncher, налаштуй його на новіший JDK. "
				+ "Деякі вже мають у собі вбудований JDK 17.0.15.";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "Мод Spark теж може докладатися до сеї біди. Подумай, щоби тимчасом його вимкнути. https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Мод MCEF (Chromium Embedded Framework) спричиняє тихий завис.</b>" + "<ul>"
				+ "<li>MCEF заводиться під кінець логу, а се зазвичай значить, що гра повисла під час завантаження.</li>"
				+ "<li>Сей мод знаний тим, що може робити біду на Linux, macOS або з деякими версіями Java.</li>"
				+ "<li>Явної помилки не завше видно, але гра так і не доходить до головного меню.</li>" + "</ul>"
				+ "<p>Коли тобі не треба браузерної справи всередині гри (як-от вебмапи чи вбудовані сторінки), прибери сей мод.</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "Біда з запуском MCEF (мод із вбудованим браузером)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "Прибери файл моду MCEF (шукай 'mcef' у назві файлу) з теки 'mods'.";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "Коли він тобі справді потрібен, то впевнись, що версія годиться до твоєї системи й версії Minecraft.";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Виявлено гризню між <b>OptiFine</b> та <b>Iris/Oculus</b>.</b>" + "<ul>"
				+ "<li>OptiFine міняє рендер Minecraft так, що воно не ладить з Iris чи Oculus.</li>"
				+ "<li>Помилка <code>MixinLevelRenderer failed injection check</code> йде з <code>mixins.iris.json</code> або <code>mixins.oculus.json</code>.</li>"
				+ "</ul>"
				+ "<p>Сі моди разом не ходять. Прибери OptiFine, коли хочеш уживати шейдери з Iris чи Oculus.</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "Гризня між OptiFine та Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "Прибери файл OptiFine з теки 'mods'.";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "Уживай Iris або Oculus без OptiFine для новітніх шейдерів.";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Виявлено гризню між <b>ModernFix</b> та <b>OptiFine</b>.</b>" + "<ul>"
				+ "<li>ModernFix не ладить з OptiFine, бо ламає частину Forge-справ і ще й сповільнює запуск.</li>"
				+ "<li>Сам ModernFix застерігає: <i>\"Use of ModernFix with OptiFine is not supported\"</i>.</li>"
				+ "</ul>" + "<p>Треба прибрати один із сих двох модів, аби гра ходила як слід.</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "Гризня між ModernFix та OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "Прибери OptiFine або ModernFix із теки 'mods'. Разом вони не ходять.";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "Коли тобі потрібні полегші та покращення, подумай уживати лиш OptiFine або замінити ModernFix на легші моди, як FerriteCore чи EntityCulling.";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Помилка: хибний реєстровий ключ із недозволеними знаками.</b>" + "<ul>"
				+ "<li><b>Виявлений ключ:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>У Minecraft усі реєстрові ключі (мітки, рецепти, досягнення й таке інше) мусять бути <b>малими літерами</b> та мати лише букви, числа, підкреслення, дефіси й косі риски.</li>"
				+ "<li>Ся біда зазвичай іде від кепсько зробленого моду або хибного datapack’а.</li>" + "</ul>"
				+ "<p><b>Важлива порада:</b> Уживай знаряддя <b>grepr</b> або <b>fgrepr</b> на бічній панелі та ввімкни опцію <b>\"Шукати в JAR-файлах\"</b>, аби знайти, котрий мод тримає сей хибний ключ.</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "Реєстровий ключ із великими літерами або хибними знаками";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "Уживай 'grepr' або 'fgrepr' з опцією \"Шукати в JAR-файлах\", аби знайти винний мод.";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "Коли не можеш упізнати мод, прибери недавні моди, надто ті, що додають блоки, речі чи знаряддя.";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Біда при запуску моду <b>"
				+ escapeHtml(modNombre) + "</b>.</b>" + "<ul>"
				+ "<li>Мод не зміг підняти один зі своїх вузлів (скажімо, меню налаштувань).</li>"
				+ "<li>Найчастіше се буває через нелад із версією Minecraft, Fabric або з іншими модами.</li>" + "</ul>"
				+ "<p>Коли біда не минає, прибери або онови мод <b>" + escapeHtml(modNombre) + "</b>.</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "Біда запуску моду (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "Прибери мод '" + modNombre + "' з теки 'mods'.";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "Онови мод '" + modNombre + "' до версії, що годиться до твоєї збірки.";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Виявлено біду з модом <b>En Garde!</b>.</b>"
				+ "<ul>" + "<li>Сей мод додає ближній бій (парирування, блок і таке інше).</li>"
				+ "<li>Біда часто з’являється через гризню з іншими бойовими модами (Epic Fight, DualRiders тощо) або невірну версію під твою гру.</li>"
				+ "</ul>" + "<p>Коли не користуєшся розширеним боєм, краще прибери En Garde!, аби не було лиха.</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "Біда в моді En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "Глянь, аби En Garde! був тієї версії, що пасує до твого Minecraft і завантажувача (Fabric/Forge).";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "Коли маєш інші бойові моди (Epic Fight, Caelus тощо), вимкни їх або прибери En Garde!, щоб не було гризні.";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Виявлено біду від моду <b>IdleTweaks</b>.</b>"
				+ "<ul>"
				+ "<li>IdleTweaks пробував відпустити мережевий канал, якого вже нема (<code>Tried to release unknown channel</code>).</li>"
				+ "<li>Се часто стається на старих версіях моду або на криво налаштованих серверах.</li>" + "</ul>"
				+ "<p>IdleTweaks — мод для зручності, та може хитати стабільність. Подумай оновити його або прибрати.</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "Біда в IdleTweaks (невідомий мережевий канал)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "Онови IdleTweaks до найновішої версії, що годиться до твого Minecraft.";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "Прибери IdleTweaks із теки 'mods', коли він тобі не потрібен.";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Виявлено біду з входом (HTTP 401) при спробі зайти в Minecraft.</b>"
				+ "<p>Ся біда <b>рідко є прямою причиною падіння</b>, але значить, що ти користуєшся неавтентифікованим (піратським) обліком.</p>"
				+ "<p>Офіційні канали помочі (проєкти, творці модпаків тощо) <b>не допоможуть</b>, коли маєш піратку, "
				+ "через їхні правила, угоди з Mojang/Microsoft та інші обмеження.</p>"
				+ "<p>Сю перевірку можна <b>вимкнути в налаштуваннях</b> детектора. "
				+ "Засторога: вона <b>не завжди точна</b> і може спрацьовувати при розробці, поганому інтернеті або змінених пускачах.</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>\"Права Міранди\", як підеш по допомогу:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Піратський Minecraft";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "Вимкнути антипіратську перевірку";
	}

	@Override
	public String comprarMC() {
		return "Купити Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Користуєшся пускачем <code>" + id
				+ "</code>, що <b>не входить до радних</b>.</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>Хоч він і може ходити, такі пускачі часто приносять біду:</p>" + "<ul>"
				+ "<li>Криві встановлення модів чи самої гри.</li>"
				+ "<li>Гра не заводиться або висне без ясної причини.</li>"
				+ "<li>Чудна структура тек (важко розбиратися).</li>"
				+ "<li>Непевна поведінка з Java, пам’яттю чи модами.</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "Для кращого ходу вживай один із сих радних пускачів:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Нерадний пускач";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "Перейди на пускач із радного списку.";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Користуєшся <b>нерадним пускачем</b>: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>Такі пускачі часто тягнуть за собою біду:</p>" + "<ul>" + "<li>Криві встановлення гри чи модів.</li>"
				+ "<li>Гра не заводиться або тихо валиться.</li>" + "<li>Чудна побудова тек (важко розібратись).</li>"
				+ "<li>Непевно поводиться з Java, пам’яттю чи модами.</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "Щиро радиться вживати один із сих пускачів:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Нерадний пускач";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "Перейди на радний пускач, аби мати поміч.";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Бракує радних модів для сього середовища.</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "Бракує радних модів";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "Постав радні моди, аби все ходило як слід.";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Знайдено нерадні моди у твоїй збірці.</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "Знайдено нерадні моди";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "Прибери нерадні моди, аби не було лиха.";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Знайдено втручання в важливі файли. Ти змінював їх або вживаєш непевний пускач.</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "Втручання виявлено";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "Перестав оригінальні файли, аби вернути лад.";
	}

	@Override
	public String configuracionCorporativa() {
		return "Корпоративний лад";
	}

	@Override
	public String idiomaRespaldo() {
		return "Запасна мова";
	}

	@Override
	public String buscardorHabilitado() {
		return "Увімкнути шукач";
	}

	@Override
	public String nombreHerramienta() {
		return "Назва знаряддя";
	}

	@Override
	public String condenarPirateria() {
		return "Осуд піратства";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "Радні пускачі";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "Нерадні пускачі";
	}

	@Override
	public String modsRecomendados() {
		return "Радні моди";
	}

	@Override
	public String modsDesaconsejados() {
		return "Нерадні моди";
	}

	@Override
	public String antiTamper() {
		return "Анти-втручання";
	}

	@Override
	public String proximamente() {
		return "Скоро буде";
	}

	@Override
	public String informacion() {
		return "Вісті";
	}

	@Override
	public String errorCargandoImagen() {
		return "Лихо при завантаженні образу";
	}

	@Override
	public String configuracionBasica() {
		return "Основний лад";
	}

	@Override
	public String funcionalidades() {
		return "Можливості";
	}

	@Override
	public String derechosMiranda() {
		return "\"Права Міранди\" (дуже радні)";
	}

	@Override
	public String gestionVerificaciones() {
		return "Керування перевірками";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "Назва";
	}

	@Override
	public String codigoVerificacion() {
		return "Код";
	}

	@Override
	public String documentacionVerificacion() {
		return "Опис";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "Увімкнені перевірки:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "Вимкнені перевірки:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "Вимкнути всі не-корпоративні";
	}

	@Override
	public String verCodigo() {
		return "Глянути код";
	}

	@Override
	public String verDocumentacion() {
		return "Глянути опис";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "Обери перевірку, щоби вимкнути.";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "Обери перевірку, щоби ввімкнути.";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "Вимкнено %d нерадних для корпорацій перевірок.";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "Нема не-корпоративних перевірок для вимкнення.";
	}

	@Override
	public String operacionCompletada() {
		return "Діло зроблено";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "Бракує нам Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "Колір корпоративної перевірки";
	}

	// Métodos para la gestión de lanzadores
	@Override
	public String nombreLanzador() {
		return "Назва пускача";
	}

	@Override
	public String motivo() {
		return "Причина";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Нерадні пускачі";
	}

	@Override
	public String moverADesaconsejados() {
		return "Зробити нерадним";
	}

	@Override
	public String moverARecomendados() {
		return "Зробити радним";
	}

	@Override
	public String guardarCambios() {
		return "Зберегти зміни";
	}

	@Override
	public String cancelar() {
		return "Скасувати";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "Обери пускач, котрий хочеш пересунути.";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "Зміни щасливо збережено!";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEs(String nombreLanzador) {
		return "Сей пускач не радиться через знані біди з безпекою та стійкістю.";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEn(String nombreLanzador) {
		return "Сей пускач не радиться через знані біди з безпекою та стійкістю.";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoPt(String nombreLanzador) {
		return "Сей пускач не радиться через знані біди з безпекою та стійкістю.";
	}

	@Override
	public String razones() {
		return "Причини";
	}

	@Override
	public String agregarLanzador() {
		return "Додати пускач";
	}

	@Override
	public String quitarLanzador() {
		return "Прибрати пускач";
	}

	@Override
	public String editarRazones() {
		return "Правити причини";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "Обери пускач, котрий треба прибрати.";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "Обери пускач, для котрого треба правити причини.";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "Правити причини для " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "Додати нову мову";
	}

	@Override
	public String aceptar() {
		return "Прийняти";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "Обери мову";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "Се ті пускачі, котрі " + Statics.nombre_cd.obtener() + " вважає добрими.";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "Правильний результат";
	}

	public String modsNoRecomendados() {
		return "Нерадні моди";
	}

	public String agregarMod() {
		return "Додати мод";
	}

	public String quitarMod() {
		return "Прибрати мод";
	}

	public String modId() {
		return "Mod ID / назва JBoss Modules";
	}

	public String rutaMod() {
		return "Шлях / файл моду";
	}

	public String errorDebeIndicarMod() {
		return "Треба вказати бодай modid або шлях до моду.";
	}

	public String modsNoRecomendadosAviso() {
		return "Тут можна записати нерадні моди, аби " + Statics.nombre_cd.obtener()
				+ " знаходив їх, коли вони стоять.";
	}

	@Override
	public String anularNormal() {
		return "Звичайне скасування";
	}

	@Override
	public String anularNormalDescripcion() {
		return Statics.nombre_cd.obtener() + " має попередити, навіть коли ще не впало";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "Записуй сюди моди, котрі " + Statics.nombre_cd.obtener() + " радить. Коли їх бракує, "
				+ Statics.nombre_cd.obtener() + " може про се сказати.";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "Коли надумаєш увімкнути антипіратське застереження, тут радиться вписати "
				+ "права людини, що просить помочі, як запобіжний захід.\n\n"

				+ "Супроти поширеної гадки, багато громад і знаних каналів помочі "
				+ "НЕ вимагають увімкнених антипіратських застережень, аби подати раду. Та все ж "
				+ "описати сі права може бути корисно, коли людина все одно зайде до каналу " + "по поміч.\n\n"

				+ "Можеш опертись на урядові документи, як-от Cartilla de Derechos Básicos del Detenido "
				+ "в Мексиці:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "А також на схожі правні засади, що вживаються в інших державах, зокрема "
				+ "в Сполучених Штатах, Російській Федерації, Китайській Народній Республіці, Ісламській Республіці "
				+ "Іран та Корейській Народно-Демократичній Республіці.\n\n"

				+ "Ось кілька прикладів прав, які можна сюди вписати:\n"
				+ "• Право не давати зайвих відомостей для помочі, як-от який пускач уживається, "
				+ "ім’я користувача чи UUID.\n" + "• Право не свідчити проти себе самого.\n"
				+ "• Право відмовитись відповідати на питання, що не потрібні для розв’язання біди.\n"
				+ "• Право дістати пораду просто в чаті.\n" + "• Право вживати знеособлення логів, вбудоване в "
				+ Statics.nombre_cd.obtener() + ".\n\n"

				+ "Сей текст приймає HTML-вміст.";
	}

	@Override
	public String editar() {
		return "Правити";
	}

	@Override
	public String advertenciaHashLento() {
		return "Засторога: коли додаси багато великих файлів, перевірка може тягтися " + "кілька хвилин. "
				+ Statics.nombre_cd.obtener() + " мусить порахувати геш кожного файлу "
				+ "перед тим, як рушити далі. Радиться боронити лише ті файли, що справді потрібні.";
	}

	@Override
	public String agregarArchivo() {
		return "Додати файл";
	}

	@Override
	public String agregarCarpeta() {
		return "Додати теку";
	}

	@Override
	public String quitar() {
		return "Прибрати";
	}

	@Override
	public String rutaArchivo() {
		return "Шлях до файлу";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "Вибраний шлях лежить поза поточним ігровим каталогом. "
				+ "Дозволені лиш файли й теки в межах поточного каталогу або його підкаталогів.";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>"
				+ "Думки й слова Sylent Bell не конче сходяться з нашими; "
				+ "ми лиш гадали, що буде смішно вставити її сюди. " + Statics.nombre_cd.obtener() + " є світський."
				+ "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Мод GML (Groovy ModLoader) потребує сих змін і найчастіше є причиною сеї проблеми.</b>";
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
				+ "Виявлено вжиток <i>окремого Flywheel</i>.</b>"
				+ "<p><b>Окремий Flywheel застарів (deprecated)</b> і не годиться для нових версій.</p>"
				+ "<p>Сучасні версії <b>Create</b> <b>вже містять Flywheel</b>, тож окрема установка "
				+ "спричиняє конфлікти сумісності й помилки завантаження.</p>"
				+ "<p>Деякі моди, що прямо залежать від окремого Flywheel, можуть "
				+ "<b>не працювати</b> або <b>працювати нестійко</b>. "
				+ "У поодиноких складних випадках їх можна запустити, якщо "
				+ "<b>вручну змінити файл <code>mods.toml</code></b>, підладнавши версії, "
				+ "але се <b>не радиться</b>.</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>Виявлені моди, що посилаються на Flywheel:</b></p>" + "<ul>" + listaMods.toString()
								+ "</ul>")
				+ "<p>Найліпше рішення — <b>вилучити окремий Flywheel</b> і користати лише "
				+ "тим, що вбудований у Create.</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Окремий Flywheel";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено помилку, пов’язану з модом <i>Floral Enchantments</i>.</b>"
				+ "<p>Крах викликаний внутрішнім збоєм мода при обробці даних гри, "
				+ "що веде до <b>NullPointerException</b> під час виконання.</p>"
				+ "<p>Звичайно се лагодиться оновленням мода або його вилученням.</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "Помилка Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "У тебе є NeoForge-версія MixinExtras і звичайна водночас. Якщо ти на MinecraftForge, можеш установити "
				+ "<a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>сей виправ</a>.</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено помилку в тінях рельєфу зі шейдерами (Iris).</b>"
				+ "<p>Проблема виникає під час малювання рельєфу.</p>"
				+ "<p>Радиться <b>спробувати гру без шейдерів</b> або зменшити графіку, "
				+ "особливо в режимі <b>Ultra</b>.</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "Тіні рельєфу (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено надто довгий серверний тік.</b>"
				+ "<p>Се означає, що гра зависла на одному тіці задовго.</p>"
				+ "<p>Радиться <b>переглянути thread dump</b> у логах, аби знайти причину.</p>"
				+ "<p><b>Аналіз Stack Trace</b> може допомогти знайти джерело зависання.</p>"
				+ "<p>Кнопка <b>Переглянути в логах</b> підсвітить червоним можливі моди-винуватці, "
				+ "а також записи в <code>$modid$</code>, що часто вказують на джерело біди. "
				+ "Для живого аналізу радиться вживати CPU sampler у VisualVM. Переконайся, що твоя машина "
				+ "досить потужна для всіх модів, бо іноді вони всі справні, але їх просто забагато.</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Довгий серверний тік";
	}

	@Override
	public String tituloLFPDPPP() {
		return "ФЕДЕРАЛЬНИЙ ЗАКОН ПРО ЗАХИСТ ПЕРСОНАЛЬНИХ ДАНИХ У ВОЛОДІННІ ПРИВАТНИХ ОСІБ";
	}

	@Override
	public String aceptarPermanentemente() {
		return "Прийняти назавжди";
	}

	public String actaProteccionIdiomaCultural() {
		return "Акт захисту культурної мови Пхеньяна";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "Корейський переклад містить південний сленг, якого слід уникати. "
				+ "Уживання чужих слів, особливо з Півдня, строго заборонене "
				+ "за Актом захисту культурної мови Пхеньяна.";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "Для докладнішого дивись урядовий документ: "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>Акт захисту культурної мови Пхеньяна</a>";
	}

	public String leerLeyCompleta() {
		return "Читати закон повністю";
	}

	public String errorAbriendoEnlace() {
		return "Помилка при відкритті посилання";
	}

	@Override
	public String canarioTitulo() {
		return "Судовий канарейка";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — Наглядовий наглядач";
	}

	@Override
	public String revisar() {
		return "Переглянути";
	}

	@Override
	public String cerrar() {
		return "Закрити";
	}

	@Override
	public String canarioTodoSeguro() {
		return "Усі служби звітують про безпечний стан.";
	}

	@Override
	public String canarioComprometido(int c) {
		return "ТРИВОГА: " + c + " служба(и) подають небезпечний стан.";
	}

	@Override
	public String colorAlerta() {
		return "Колір тривоги";
	}

	public String opcionesMunidiales() {
		return "Світові налаштування";
	}

	public String consentimientoLFPDPPP() {
		return "Згода LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "Увімкнути передачу токена доступу через Handoff для ReLauncher (не радиться).";
	}

	public String consolaDesarrollo() {
		return "Розробнича консоля";
	}

	public String mundial() {
		return "Світовий";
	}

	public String ningun() {
		return "Жоден";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "Консоль розробника";
	}

	public String bajar() {
		return "Спустити";
	}

	public String logsSoporte() {
		return "Логи для помочі";
	}

	public String detenerProceso() {
		return "Спинити процес";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "Копіювати вибране";
	}

	public String seleccionarTodo() {
		return "Вибрати все";
	}

	public String copiarTodo() {
		return "Копіювати все";
	}

	public String guardarTodoComoArchivo() {
		return "Зберегти все як файл";
	}

	public String obtenerEnlaceSoporte() {
		return "Добути посилання для помочі";
	}

	public String borrarTodo() {
		return "Затерти все";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "Колір тла консолі";
	}

	public String colorTextoConsola() {
		return "Колір тексту консолі";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "Згоду підтверджено.\nВбудова поширення логів буде зроблена тут.";
	}

	@Override
	public String usarSakuraOriginal() {
		return "Вживати оригінальний образ Sakura Riddle";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "\"warrant canary\" — се засіб прозорості.\n\n"
				+ "Поки сей напис стоїть і служби значаться як безпечні, "
				+ "се значить, що проєкт НЕ дістав таємних судових наказів, "
				+ "цензурних приписів чи правних вимог наглядати.\n\n"
				+ "Коли котрийсь канарейка щезне або покаже небезпечний стан, "
				+ "се значить, що щось змінилося по праву.\n\n"
				+ "Ся панель переглядає всіх канарейок, записаних у системі, і показує " + "їхній теперішній стан.\n\n"
				+ "Тисни \"Переглянути\", аби оновити стани.";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "Чи відновити всі опції до первісних значень?";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "Без опцій";
	}

	@Override
	public String seleccionaColor() {
		return "Обрати колір";
	}

	@Override
	public String botonMostrarGUI() {
		return "Показати GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "Зберегти все";
	}

	@Override
	public String botonRestablecerTodo() {
		return "Відновити все";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms не завантажився";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено біду при доступі до API LuckPerms.</b>"
				+ "<p>Повідомлення каже, що <b>LuckPerms не був завантажений</b> у той час, коли інший плагін намагався ним скористатись.</p>"
				+ "<p><b>Можливі причини:</b></p>" + "<ul>"
				+ "<li>Плагін <b>LuckPerms не встановлений</b> або <b>зірвався при запуску</b>.</li>"
				+ "<li>Інший плагін пробує дістатись до LuckPerms надто рано або криво.</li>" + "</ul>"
				+ "<p>Радиться <b>переглянути консоль</b> через посилання, аби впізнати "
				+ "плагін, що кличе LuckPerms, і перевірити його сумісність.</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Шейдерпак Iris не завантажився";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "невідомий" : shaderpack;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено біду при завантаженні шейдерпаку через Iris/Oculus.</b>" + "<p><b>Уражений шейдерпак:</b> "
				+ nombre + "</p>" + "<p>Minecraft не зміг відкрити файл шейдерпаку (FileSystemNotFoundException).</p>"
				+ "<p><b>Можливі ради:</b></p>" + "<ul>"
				+ "<li>Перевір, чи шейдерпак добре лежить у теці <b>shaderpacks</b>.</li>"
				+ "<li>Скачай шейдерпак наново, бо файл міг попсуватись.</li>"
				+ "<li>Коли біда не минає, прибери файл <b>config/iris.properties</b>, аби скинути лад Iris.</li>"
				+ "</ul>" + "<p>Після сих змін заведи гру знову.</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "Не вдалося записати конфігураційний файл";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "невідомий" : ruta;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Сталася біда при збереженні конфігураційного файлу.</b>" + "<p><b>Уражений файл:</b> " + archivo
				+ "</p>" + "<p>Minecraft не зміг записати файл через атомарний запис (REPLACE_ATOMIC).</p>"
				+ "<p><b>Найчастіше се буває через:</b></p>" + "<ul>" + "<li>Хибні дозволи на теку або файл.</li>"
				+ "<li>Файл позначено як лише для читання.</li>"
				+ "<li>Інша програма (антивірус, backup, редактор) тримає файл і не пускає.</li>" + "</ul>"
				+ "<p><b>Що радиться:</b></p>" + "<ul>" + "<li>Перевір, чи маєш право писати в сю теку.</li>"
				+ "<li>Зніми з файлу познаку \"лише для читання\".</li>"
				+ "<li>Позачиняй програми, що можуть тримати сей файл.</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "Відмовлено в доступі при створенні резервної копії";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "невідомий" : origen;
		String dst = backup == null || backup.isEmpty() ? "невідомий" : backup;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Сталася помилка дозволів при створенні резервної копії конфігураційного файлу.</b>"
				+ "<p><b>Початковий файл:</b> " + src + "</p>" + "<p><b>Резервний файл:</b> " + dst + "</p>"
				+ "<p>Операційна система заблокувала доступ під час збереження файлу.</p>"
				+ "<p><b>Звичайні причини:</b></p>" + "<ul>" + "<li>Недостатні дозволи на теку.</li>"
				+ "<li>Файл позначений як лише для читання.</li>"
				+ "<li>Інша програма (антивірус, синхронізація, редактор) користується файлом.</li>" + "</ul>"
				+ "<p><b>Радиться:</b></p>" + "<ul>" + "<li>Перевірити дозволи теки <b>config</b>.</li>"
				+ "<li>Позакривати програми, що можуть тримати файл.</li>"
				+ "<li>Спробувати запустити лаунчер або Minecraft як адміністратор.</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		return "Увімкнути консоль";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>Знаряддя налагодження</b><br><br>"
				+ "Тут можна ввімкнути розширені можливості для налагодження " + Statics.nombre_cd.obtener()
				+ " і гри.<br><br>"
				+ "Радиться увімкнути розробничу консоль, аби дістати докладні відомості, трасування й діагностику.<br><br>"
				+ "Коли треба випробувати багатокористувацький сервер у режимі online, може знадобитись дозволити передачу токена доступу до процесу "
				+ Statics.nombre_cd.obtener() + " з налаштувань приватності. "
				+ "Звичайно се <b>не радиться</b> в інших випадках.<br><br>"
				+ "Повні настанови: <a href='https://example.com'>Посилання</a>";
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено несумісність між Simple Clouds і шейдерами.</b>"
				+ "<p>Simple Clouds не сумісний із модами тіней (Iris/Oculus), коли встановлено Distant Horizons.</p>"
				+ "<p><b>Рекомендовані варіанти:</b></p>" + "<ul>"
				+ "<li>Вилучити <b>Simple Clouds</b>, якщо хочеш користати шейдери.</li>"
				+ "<li>Або вилучити <b>Iris чи Oculus</b>, якщо бажаєш лишити Simple Clouds.</li>" + "</ul>"
				+ "<p>Се обмеження самого мода Simple Clouds і не може бути усунене без зміни його коду.</p>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "Несумісність: Simple Clouds проти шейдерів";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "Колір кнопки бічної панелі";
	}

	@Override
	public String profilerTitulo() {
		return "Профайлер";
	}

	@Override
	public String profilerDescripcion() {
		return "Знаряддя аналізу продуктивності на основі інструментування та вибірки.";
	}

	@Override
	public String profilerIniciar() {
		return "Почати";
	}

	@Override
	public String profilerDetener() {
		return "Спинити";
	}

	@Override
	public String profilerLimpiar() {
		return "Очистити";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "Профайлер почато.";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "Профайлер спинено.";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "Отримано зразок із потоку: " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "Періодичне вибирання стеків для виявлення вузьких місць і зависань.";
	}

	@Override
	public String entrarAlJuego() {
		return "Увійти в гру";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено помилку в системному шляху.</b>"
				+ "<p>Minecraft не зміг запуститись через недозволені символи в назві теки.</p>"
				+ "<p>Система знайшла недійсний знак у шляху (наприклад ':' чи інші символи).</p>"
				+ "<p><b>Радиться:</b></p>" + "<ul>" + "<li>Перейменувати теку інстанції або профілю.</li>"
				+ "<li>Вживати лише прості ASCII-символи (A-Z, a-z, 0-9).</li>"
				+ "<li>Не вживати наголосів, особливих знаків, дивних пробілів чи емоджі.</li>" + "</ul>"
				+ "<p>Дійсний приклад: <b>MiInstancia1</b></p>"
				+ "<p>Недійсний приклад: <b>Instancia🔥</b> або <b>Instancia:Mod</b></p>";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "Недійсний шлях: заборонені символи";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено збій шейдерів Twilight Forest на GPU Intel.</b>"
				+ "<p>Ся помилка пов’язана з драйверами Intel при завантаженні шейдерів мода Twilight Forest.</p>"
				+ "<p>Збій стається в самому драйвері (igxelpicd64), а не прямо в моді чи Minecraft.</p>"
				+ "<p><b>Радиться:</b></p>" + "<ul>" + "<li>Оновити драйвери Intel до найновішої версії.</li>"
				+ "<li>Спробувати версії 31.0.101.8331 або 31.0.101.8247 WHQL, що, за відгуками, не мають сеї біди.</li>"
				+ "</ul>" + "<p>Офіційне обговорення:</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>Примітка:</b> Деякі старі GPU Intel можуть не дістати оновлення для виправлення сеї проблеми.</p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "Крах: Twilight Forest + драйвери Intel";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "Forge: постачальник мови не завантажився";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "Невідомий постачальник" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Forge не зміг завантажити постачальника мови.</b>"
				+ "<p>Сталася помилка при ініціалізації IModLanguageProvider.</p>"
				+ "<p><b>Постачальник, що зірвався:</b> " + providerTexto + "</p>" + "<p>Се зазвичай буває, коли:</p>"
				+ "<ul>" + "<li>Бракує потрібної залежності (наприклад, Kotlin for Forge).</li>"
				+ "<li>Версія мода не сходиться з твоєю версією Forge.</li>" + "<li>Файл мода пошкоджений.</li>"
				+ "</ul>" + "<p><b>Радиться:</b></p>" + "<ul>" + "<li>Перевстановити відповідний мод.</li>"
				+ "<li>Перевірити всі залежності.</li>" + "<li>Вжити сумісні версії з твоїм Forge.</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "Крах: Lets Do Compat (RecipeManager intercept)";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено крах у Lets Do Compat (interceptApply).</b>"
				+ "<p>Помилка виникає в перетворенні методу <b>RecipeManager.interceptApply</b>.</p>"
				+ "<p>Звичайно се значить:</p>" + "<ul>"
				+ "<li>Несумісність між Lets Do Compat і іншим модом рецептів.</li>"
				+ "<li>Хибна версія для Minecraft.</li>" + "<li>Конфлікт mixin/coremod.</li>" + "</ul>"
				+ "<p><b>Радиться:</b></p>" + "<ul>" + "<li>Тимчасово вилучити Lets Do Compat, аби впевнитися.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI: збій групи предметів (несумісний плагін)";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "Невідомий плагін" : String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "JEI виявив помилку при створенні групи предметів.</b>"
				+ "<p>Один чи більше плагінів спричинили збій.</p>" + "<p><b>Зачеплені плагіни:</b> " + listaPlugins
				+ "</p>" + "<p>Се буває, коли:</p>" + "<ul>" + "<li>Плагін JEI застарілий або криво написаний.</li>"
				+ "<li>Несумісність із версією JEI.</li>" + "<li>Fabric API реєструє Item Group неправильно.</li>"
				+ "</ul>" + "<p><b>Радиться:</b></p>" + "<ul>" + "<li>Оновити JEI і моди.</li>"
				+ "<li>Тимчасово вилучити плагіни.</li>" + "<li>Повідомити розробника.</li>" + "</ul>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "Недійсна версія мода (SemVer)";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "Невідома" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "Мод не знайдено" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено недійсну версію мода.</b>" + "<p>Версія <b>" + v + "</b> не відповідає SemVer.</p>"
				+ "<p><b>Мод:</b><br>" + u + "</p>" + "<p><b>Радиться:</b></p>" + "<ul>" + "<li>Виправити версію.</li>"
				+ "<li>Забрати зайвий '+'.</li>" + "<li>Оновити мод.</li>" + "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS: незаконний доступ між модулями";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено незаконний доступ між модулями.</b>" + "<p><b>Джерело:</b> " + claseOrigen + " ("
				+ moduloOrigen + ")</p>" + "<p><b>Ціль:</b> " + claseDestino + " (" + moduloDestino + ")</p>"
				+ "<p>Мод не правильно оголосив exports/opens.</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin: клас не в тім пакеті";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Клас розміщено неправильно в mixin-пакеті.</b>" + "<p><b>Клас:</b> " + clase + "</p>"
				+ "<p><b>Пакет:</b> " + paquete + "</p>" + "<p><b>Файл:</b> " + archivoMixin + "</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Проблема з драйверами Matrox GPU.</b>" + "<p>Matrox не підтримує сучасний 3D.</p>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod: GPU не підтримується";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod не знайшов придатну GPU.</b>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "Недійсний RenderType для outline";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Неможливо відмалювати outline для цього RenderType.</b>";
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – нульовий інвентар";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG зірвався через null інвентар.</b>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "Конфлікт у Render Pass";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено конфлікт у системі відмалювання.</b>" + "<p>Гра видала:</p>"
				+ "<code>Close the existing render pass before performing additional commands</code>"
				+ "<p>Звичайно се пов’язане з конфліктами між модами рендеру "
				+ "(Iris, OptiFine, VulkanMod чи инші, що змінюють графічний пайплайн).</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "Внутрішній збій Feather Client";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено внутрішній збій Feather Client.</b>" + "<p>Гра видала:</p>"
				+ "<code>NoClassDefFoundError: feather/lib/sentry/Sentry</code>"
				+ "<p>Сю помилку спричиняє сам Feather Client.</p>" + "<p>Feather не радиться, бо:</p>" + "<ul>"
				+ "<li>Вживає власні, змінені версії популярних модів.</li>"
				+ "<li>Ламає сумісність зі стандартним Fabric.</li>"
				+ "<li>Є наперед зібраним модпаком з внутрішніми змінами.</li>"
				+ "<li>Часто конфліктує з Sodium та іншими модами продуктивності.</li>" + "</ul>"
				+ "<p>Ліпше вжити звичайну установу Fabric замість Feather.</p>";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "Конфлікт Iris + Flywheel (Create 6)";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено конфлікт між Iris і Flywheel у Create 6.</b>" + "<p>Гра видала:</p>"
				+ "<code>NoSuchFieldError: TESSELATION_SHADERS</code>"
				+ "<p>Знайдено внутрішні посилання <code>$irisflw$</code>, що вказують на конфлікт.</p>"
				+ "<p>Iris Flywheel 2.0 для Create 6 офіційно підтримується лише в NeoForge.</p>"
				+ "<p>При вживанні Forge або Fabric може виникати сей збій.</p>";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "Модель GeckoLib не знайдена";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Мод не знайшов модель GeckoLib.</b>" + "<p>Модель:</p>" + "<code>" + modelo + "</code>"
				+ "<p>Се буває, коли файл <code>.geo.json</code> відсутній або криво налаштований.</p>"
				+ "<p>Можливі причини:</p>" + "<ul>" + "<li>Модель вилучено, але ще згадується.</li>"
				+ "<li>Помилка в шляху.</li>" + "<li>Файл відсутній у JAR.</li>" + "<li>Несумісна версія мода.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – відсутня анімація";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon спробував відтворити неіснуючу анімацію.</b>" + "<p>Анімація:</p><code>" + animacion
				+ "</code>" + "<p>Група:</p><code>" + grupo + "</code>" + "<p>Се буває, коли:</p>" + "<ul>"
				+ "<li>Змішані несумісні версії Cobblemon.</li>" + "<li>Аддон не сходиться з версією.</li>"
				+ "<li>Бракує ресурсів або анімацій.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "Внутрішній збій Lunar Client";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено внутрішній збій Lunar Client.</b>" + "<p>Помилка походить із самого клієнта Lunar.</p>"
				+ "<p>Він уживає власні зміни, що можуть давати несумісності.</p>"
				+ "<p>Радиться перевірити гру на звичайній установі Minecraft.</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "Незаконний доступ до методу чи поля";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Мод намагався доступитись до захищеного/приватного члена.</b>" + "<p>Клас:</p><code>" + clase
				+ "</code>" + "<p>Член:</p><code>" + miembro + "</code>"
				+ "<p>Се буває, коли мод застарілий або несумісний.</p>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "Помилка завантаження datapack/resourcepack";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Datapack або resourcepack не зміг завантажитись.</b>" + "<p>Файл:</p><code>" + archivo + "</code>"
				+ "<p>Пак:</p><code>" + pack + "</code>"
				+ "<p>Звичайно через помилки JSON або несумісність версій.</p>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "Помилка компіляції шейдера";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Не вдалося скомпілювати шейдер.</b>"
				+ "<p>Можливі причини: несумісні shaderpacks або драйвери GPU.</p>";
	}

	public String nombreErrorCreacionModelo() {
		return "Помилка створення або завантаження моделі";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>Сталася помилка при створенні або завантаженні моделі.</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>Модель: <code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>Звичайно через конфлікти модів або пошкоджені JSON.</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>Можлива причина:</b></p>");
		sb.append("<p>Виявлено мод <b>Cooler Animations</b>.</p>");
		sb.append("<p>Може спричиняти помилки моделей.</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "Проблема зі Starlight";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено збій Starlight.</b>" + "<p>Помилка в <code>BlockStarLightEngine.initNibble</code>.</p>"
				+ "<p>Радиться запустити без Starlight.</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "Проблема AAAParticles / Effekseer";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено нативний збій Effekseer.</b>" + "<p>Радиться запустити без AAAParticles.</p>";
	}

	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено нативний краш середовища виконання Java (JVM).</b>"
				+ "<p>Цей тип помилки виникає всередині самої віртуальної машини Java (наприклад, у <code>jvm.dll</code>, <code>libjvm.so</code> тощо), "
				+ "і не обов'язково викликаний модом.</p>"
				+ "<p>Хоча в рідкісних випадках це може бути спричинено модами, які використовують несумісні нативні бібліотеки, "
				+ "<b>набагато ймовірніше, що це пов'язано з дефектною, пошкодженою або застарілою версією JVM</b>.</p>"
				+ "<p>Це особливо поширено, якщо ви використовуєте стару або неофіційну збірку вашої версії Java (наприклад, community-збірки без підтримки).</p>"
				+ "<p><b>Рекомендуємо використовувати надійну та підтримувану JVM:</b></p>" + "<ul>"
				+ "<li><b>Red Hat Build of OpenJDK</b> (стабільна, добре протестована, ідеальна для Windows/Linux)</li>"
				+ "<li><b>OpenLogic OpenJDK</b> (кросплатформна підтримка, включаючи macOS Intel)</li>"
				+ "<li><b>Azul Zulu</b> (сертифікована, з безкоштовною підтримкою LTS)</li>"
				+ "<li><b>Oracle JDK</b> (офіційна, з регулярними оновленнями)</li>" + "</ul>"
				+ "<p>Уникайте невідомих, кастомних або дуже старих збірок, оскільки вони можуть містити критичні помилки в рушію JVM.</p>"
				+ "<p><b>Використовуєте Prism Launcher або TLauncher?</b> Налаштувати користувацьку JVM дуже просто: "
				+ "у Prism Launcher перейдіть до <i>Installations</i> → <i>Edit Instance</i> → <i>Java Settings</i>; "
				+ "у TLauncher перейдіть до <i>Settings</i> → <i>Java Settings</i> і виберіть шлях до завантаженого JDK/JRE. "
				+ "Також можливо, що ваш збирач сміття має проблеми; у такому разі вам слід перейти на ZGC."

				+ "Вам не потрібно змінювати системну JVM!</p>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "Конфлікт між Paranoia та C2ME";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено конфлікт між модами Paranoia та C2ME.</b>"
				+ "<p>Помилка вказує, що <code>ThreadLocalRandom</code> був використаний не в тому потоці.</p>"
				+ "<p>Зазвичай це трапляється, коли мод <b>Paranoia</b> виконує код, що не є потокобезпечним, поки <b>C2ME</b> проводить оптимізацію багатопотоковості.</p>"
				+ "<p>Такі конфлікти часто зустрічаються в модах, створених через MCreator.</p>"
				+ "<p>Якщо проблема не зникає — спробуй запустити гру без Paranoia або вимкнути C2ME.</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "Відсутній каталог ресурсів Minecraft";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft не зміг знайти каталог ресурсів.</b>"
				+ "<p>Лаунчер намагався запустити гру з некоректним шляхом до assets.</p>"
				+ "<p>Це означає, що файли ресурсів гри відсутні або встановлені неправильно.</p>"
				+ "<p>Зазвичай це буває через криву установку Minecraft або неправильно налаштований лаунчер.</p>"
				+ "<p>Також часто трапляється з неофіційними лаунчерами, які неправильно працюють з ресурсами, як-от FreshCraft.</p>"
				+ "<p>Якщо проблема не зникає — перевстанови модпак або запусти гру через нормальний лаунчер.</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return "Постав " + dependencia + " версії " + version + " або новішу";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return "Замінити " + dependencia + " на версію від " + min + " до " + max;
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "Мод " + mod + " вимагає " + dependencia + " мінімум версії " + version;
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "Мод " + mod + " вимагає " + dependencia + " версії " + version;
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {
		return "Мод " + mod + " вимагає " + dependencia + " від " + min + " до " + max + " (зараз: " + actual + ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "Несумісна версія Cupboard";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено краш через стару версію Cupboard.</b>"
				+ "<p>Помилка виникає у <code>ClientConfigCompat.setupNeoforge</code>, бо "
				+ "<code>ModList.get()</code> повертає <code>null</code>.</p>"
				+ "<p>Це відомий баг старих версій моду <b>Cupboard</b>.</p>"
				+ "<p>Наприклад, версія <b>3.2</b> має цю проблему.</p>"
				+ "<p><b>Рішення:</b> онови Cupboard до версії <b>3.5</b> або новішої.</p>";
	}

	@Override
	public String nombreErrorPreloadingTricks() {
		return "Збій через Preloading Tricks";
	}

	@Override
	public String mensajeErrorPreloadingTricks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Виявлено конфлікт через <b>Preloading Tricks</b>.</span><br><br>" + "<span style='color:#" + color
				+ "'>" + "Помилка <i>ClassCastException: String cannot be cast to ModuleDescriptor</i> "
				+ "означає, що мод криво лізе в систему модулів Java і все ламає.</span><br><br>";

		String explicacion = "<span style='color:#" + color + "'>"
				+ "<b>Preloading Tricks</b> — це мод більше для <b>розробників</b>. "
				+ "Він лізе в класи ще на дуже ранньому етапі запуску гри і мутить там свої міксини, "
				+ "через що все легко розвалюється, якщо є інші моди.</span><br><br>" + "<span style='color:#" + color
				+ "'><b>Що робити:</b><br><ul>"
				+ "<li>Просто викинь <b>Preloading Tricks</b>. Для звичайної гри він не потрібен.</li>"
				+ "<li>Якщо ти розробник — перевір своє середовище і налаштування.</li>" + "</ul></span>";

		return mensajeBase + explicacion;
	}

	@Override
	public String nombreErrorSimpleRadioLexiconfig() {
		return "Конфлікт Simple Radio / Lexiconfig";
	}

	@Override
	public String mensajeErrorSimpleRadioLexiconfig() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Знайдено конфлікт між <b>Simple Radio</b> та <b>Lexiconfig</b>.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Помилка виникає під час 'shelveLexicons', що означає несумісність версій цих бібліотек.</span><br><br>";

		String solucion = "<span style='color:#" + color + "'><b>Причина:</b><br>"
				+ "Simple Radio зазвичай заточений під старі версії Lexiconfig, а в тебе стоїть новіша.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>Що робити:</b><br><ul>"
				+ "<li>Постав старішу версію <b>Lexiconfig</b>.</li>"
				+ "<li>Краще за все підійде <b>1.3.11</b> або нижче.</li>"
				+ "<li>Або перевір, чи є оновлення для Simple Radio.</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorMobAITweaks() {
		return "Збій через Mob AI Tweaks";
	}

	@Override
	public String mensajeErrorMobAITweaks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Виявлено проблему з <b>Mob AI Tweaks</b>.</span><br><br>" + "<span style='color:#" + color + "'>"
				+ "Помилка йде з Mixin (<code>$mob-ai-tweaks$onSpawned</code>), який втручається "
				+ "під час появи мобів. Зазвичай це означає конфлікт з іншим модом, "
				+ "що теж змінює спавн.</span><br><br>";

		String solucion = "<span style='color:#" + color + "'><b>Що робити:</b><br><ul>"
				+ "<li>Спробуй прибрати <b>Mob AI Tweaks</b> і перевір, чи зникне проблема.</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	public String nombre_verificacion_gpu() {
		return "Перевірка GPU (OpenGL / вибір відеокарти)";
	}

	public String desactivar_parche_gpu() {
		return "Вимкнути перевірку GPU";
	}

	public String gpu_crash_posible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Перевірка GPU могла завалити гру.</b>";
	}

	public String gpu_crash_causas() {
		return "Перевірка почалась, але не завершилась. Зазвичай це означає проблему з OpenGL або драйверами.<br><br>"
				+ "Можливі причини:<br>" + "- Старі або криві драйвери<br>" + "- Проблеми з OpenGL<br>"
				+ "- Стара відеокарта або гібридна графіка";
	}

	public String gpu_crash_recomendaciones() {
		return "Що робити:<br>" + "- Оновити драйвери GPU<br>" + "- Примусово включити дискретну відеокарту<br>"
				+ "- Не використовувати віддалені або віртуальні середовища";
	}

	// ==================== НЕ ОПТИМАЛЬНО ====================

	public String gpu_no_optima() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Гра не використовує найкращу доступну відеокарту.</b>";
	}

	public String gpu_no_optima_detalles() {
		return "Це може знизити продуктивність (низький FPS), але саме по собі зазвичай не викликає крашів.";
	}

	public String gpu_recomendaciones_rendimiento() {
		return "Що зробити для кращої продуктивності:<br>" + "- Увімкнути дискретну GPU в панелі керування<br>"
				+ "- Поставити Java/Minecraft у режим високої продуктивності";
	}

	// ==================== ЗАГАЛЬНЕ ====================

	public String gpu_nota_precision() {
		return "<b>Примітка:</b> Ця система визначення не є на 100% точною.";
	}

	public String gpu_consumo_energia() {
		return "Потужні відеокарти споживають більше енергії і швидше садять батарею на ноутбуках.";
	}

	public String gpu_parche_info() {
		return "Цю перевірку можна вимкнути через кнопку швидкого виправлення.";
	}

	@Override
	public String nombreVerificacionRaptorLake() {
		return "Попередження про стабільність CPU Intel 13/14 покоління";
	}

	@Override
	public String advertenciaRaptorLakeTitulo() {
		return "Можлива нестабільність процесора Intel Raptor Lake";
	}

	@Override
	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String targetMicrocode) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Виявлено процесор " + cpu
				+ " з мікрокодом " + microcode + "." + "</b> "
				+ "Процесори Intel 13-го і 14-го покоління можуть бути нестабільні через завищену напругу, "
				+ "що може скоротити строк служби процесора.<br><br>"
				+ "Рекомендується оновити мікрокод або BIOS материнської плати до версії з мікрокодом <b>"
				+ targetMicrocode + "</b> або новішим. "
				+ "<b>Увага:</b> Оновлення BIOS — ризикована операція, якщо зробити неправильно.<br><br>"
				+ "<i>Примітка: Це майже точно НЕ причина твого крашу, це просто попередження про стан заліза.</i>";
	}

	@Override
	public String desactivarVerificacionRaptorLake() {
		return "Більше не показувати це попередження";
	}

	@Override
	public String verArticuloRaptorLake(String fuente) {
		return "Читати статтю на " + fuente;
	}

	@Override
	public String tituloMixins() {
		return "Переглядач Mixins";
	}

	@Override
	public String mixinsBotonLateral() {
		return "Mixins";
	}

	@Override
	public String mixinsRaiz() {
		return "Знайдені Mixins";
	}

	@Override
	public String mixinsTodosLosMods() {
		return "Усі";
	}

	@Override
	public String mixinsModConMixin() {
		return "Мод з mixins";
	}

	@Override
	public String mixinsTooltipCombo() {
		return "Фільтр по модах, що мають mixins";
	}

	@Override
	public String mixinsRecargar() {
		return "Перезавантажити";
	}

	@Override
	public String mixinsDescompilarSeleccion() {
		return "Декомпілювати вибране";
	}

	@Override
	public String mixinsTargets() {
		return "Цілі";
	}

	@Override
	public String mixinsTarget() {
		return "Ціль";
	}

	@Override
	public String mixinsTargetsMetodo() {
		return "Цілі методу";
	}

	@Override
	public String mixinsMetodos() {
		return "Методи";
	}

	@Override
	public String mixinsCampos() {
		return "Поля";
	}

	@Override
	public String mixinsCantidad() {
		return "Кількість mixins";
	}

	@Override
	public String mixinsDetalleMixin() {
		return "Деталі mixin";
	}

	@Override
	public String mixinsDetalleTarget() {
		return "Деталі цілі";
	}

	@Override
	public String mixinsDetalleMetodo() {
		return "Деталі методу mixin";
	}

	@Override
	public String mixinsDetalleCampo() {
		return "Деталі поля mixin";
	}

	@Override
	public String mixinsDetalleConflicto() {
		return "Деталі конфлікту";
	}

	@Override
	public String mixinsBuscarConflictos() {
		return "Знайти можливі конфлікти";
	}

	@Override
	public String mixinsResultadosConflictos() {
		return "Результати конфліктів";
	}

	@Override
	public String mixinsConflictosPosibles() {
		return "Можливі конфлікти";
	}

	@Override
	public String mixinsErrorDescompilar() {
		return "Помилка декомпіляції";
	}

	@Override
	public String mixinsColorPanel() {
		return "Колір панелі mixins";
	}

	@Override
	public String mixinsColorTexto() {
		return "Колір тексту mixins";
	}

	@Override
	public String mixinsColorTextoSuave() {
		return "Колір другорядного тексту mixins";
	}

	@Override
	public String mixinsAyudaUso1() {
		return "Цей інструмент показує моди з mixins (SpongePowered) і дозволяє дивитися їх класи, цілі, методи та поля.";
	}

	@Override
	public String mixinsAyudaUso2() {
		return "Використовуй верхній список, щоб відфільтрувати конкретний мод або показати всі моди з mixins.";
	}

	@Override
	public String mixinsAyudaUso3() {
		return "Розкрий дерево, щоб побачити кожен mixin, його цільові класи, методи з анотаціями і shadow-поля.";
	}

	@Override
	public String mixinsAyudaUso4() {
		return "Клік правою кнопкою по моду, mixin, цілі, методу або полю, щоб знайти можливі конфлікти з іншими mixins.";
	}

	@Override
	public String mixinsAyudaUso5() {
		return "Можеш декомпілювати вибране або результат конфлікту, щоб подивитися код.";
	}

	@Override
	public String mixinsColorComboFondo() {
		return "Колір фону списку модів";
	}

	@Override
	public String mixinsColorAreaContenidoFondo() {
		return "Колір фону панелі деталей";
	}

	@Override
	public String mixinsColorSeleccionTexto() {
		return "Колір виділення тексту";
	}

	@Override
	public String mixinsColorSeleccionTextoActivo() {
		return "Колір активного виділеного тексту";
	}

	@Override
	public String mixinsColorAyudaTexto() {
		return "Колір тексту допомоги";
	}

	@Override
	public String mixinsColorArbolFondo() {
		return "Колір фону дерева";
	}

	@Override
	public String mixinsColorRendererSeleccionTexto() {
		return "Колір тексту виділення в дереві";
	}

	@Override
	public String mixinsColorRendererSeleccionFondo() {
		return "Колір фону виділення в дереві";
	}

	@Override
	public String mixinsColorRendererBordeSeleccion() {
		return "Колір рамки виділення в дереві";
	}

	@Override
	public String depmapTitulo() {
		return "Мапа залежностей";
	}

	@Override
	public String depmapBotonLateral() {
		return "DepMap";
	}

	@Override
	public String depmapPestanaMapa() {
		return "Мапа";
	}

	@Override
	public String depmapPestanaDependientes() {
		return "Залежні";
	}

	@Override
	public String depmapRecargar() {
		return "Перезавантажити";
	}

	@Override
	public String depmapDescompilarSeleccion() {
		return "Декомпілювати вибране";
	}

	@Override
	public String depmapVerReferencias() {
		return "Дивитись посилання";
	}

	@Override
	public String depmapDependencias() {
		return "Залежності";
	}

	@Override
	public String depmapDependientes() {
		return "Залежні";
	}

	@Override
	public String depmapDependiente() {
		return "Залежний";
	}

	@Override
	public String depmapSinDependencias() {
		return "Нема залежних";
	}

	@Override
	public String depmapSeleccionarMod() {
		return "Обрати мод";
	}

	@Override
	public String depmapSeleccionarModBase() {
		return "Базовий мод";
	}

	@Override
	public String depmapSeleccionarDependiente() {
		return "Залежний мод";
	}

	@Override
	public String depmapSeleccionarPaquete() {
		return "Пакет";
	}

	@Override
	public String depmapComprobarNoAlineadas() {
		return "Перевірити неузгоджені";
	}

	@Override
	public String depmapResultadoNoAlineadas() {
		return "Результати неузгоджених залежностей";
	}

	@Override
	public String depmapClaseInexistente() {
		return "Клас не існує";
	}

	@Override
	public String depmapClaseReferenciada() {
		return "Згаданий клас";
	}

	@Override
	public String depmapOrigen() {
		return "Джерело";
	}

	@Override
	public String depmapDestino() {
		return "Призначення";
	}

	@Override
	public String depmapDependenciaDetalle() {
		return "Деталі залежності";
	}

	@Override
	public String depmapReferenciaDetalle() {
		return "Деталі посилання";
	}

	@Override
	public String depmapMetodoOrigen() {
		return "Метод джерела";
	}

	@Override
	public String depmapModBase() {
		return "Базовий мод";
	}

	@Override
	public String depmapTodos() {
		return "Усі";
	}

	@Override
	public String depmapSeleccioneUnMod() {
		return "Обери мод";
	}

	@Override
	public String depmapSeleccioneParametrosNoAlineadas() {
		return "Обери базовий мод, залежний мод і пакет";
	}

	@Override
	public String depmapSeleccioneClaseParaDescompilar() {
		return "Обери посилання або результат для декомпіляції";
	}

	@Override
	public String depmapErrorDescompilar() {
		return "Помилка декомпіляції";
	}

	@Override
	public String depmapAyuda1() {
		return "Цей інструмент будує мапу залежностей між модами, використовуючи посилання між їх класами.";
	}

	@Override
	public String depmapAyuda2() {
		return "Вкладка мапи показує граф із модами і залежностями між ними.";
	}

	@Override
	public String depmapAyuda3() {
		return "Вкладка залежних сортує моди від найбільш залежного до тих, що ні від кого не залежать.";
	}

	@Override
	public String depmapAyuda4() {
		return "Можеш дивитися конкретні зв'язки між модами і навіть декомпілювати відповідні класи.";
	}

	@Override
	public String depmapAyuda5() {
		return "Перевірка неузгоджених залежностей шукає посилання на неіснуючі класи в пакеті базового моду.";
	}

	@Override
	public String depmapColorPanel() {
		return "Колір панелей";
	}

	@Override
	public String depmapColorTexto() {
		return "Колір основного тексту";
	}

	@Override
	public String depmapColorTextoSecundario() {
		return "Колір другорядного тексту";
	}

	@Override
	public String depmapColorAyudaTexto() {
		return "Колір тексту допомоги";
	}

	@Override
	public String depmapColorGrafoFondo() {
		return "Колір фону графа";
	}

	@Override
	public String depmapColorListaFondo() {
		return "Колір фону списків";
	}

	@Override
	public String depmapColorArbolFondo() {
		return "Колір фону дерева";
	}

	@Override
	public String depmapColorNodo() {
		return "Колір вузлів графа";
	}

	@Override
	public String depmapColorEnlace() {
		return "Колір зв'язків графа";
	}

	@Override
	public String depmapColorSeleccion() {
		return "Колір виділення";
	}

	@Override
	public String depmapColorSeleccionTexto() {
		return "Колір виділеного тексту";
	}

	@Override
	public String nombreProblemaAzureLibAnimaciones() {
		return "Проблема з аддоном AzureLib";
	}

	@Override
	public String mensajeProblemaAzureLibAnimaciones() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено помилку AzureLib при завантаженні анімацій.</b>"
				+ "<p>Гра знайшла JSON анімацій з неправильним форматом.</p>"
				+ "<p>Зазвичай це викликано одним із модів або аддонів, що використовують <b>AzureLib</b>.</p>"
				+ "<p><b>Що робити:</b></p><ul>"
				+ "<li>Використай <b>DepMap</b>, щоб знайти всі аддони, що залежать від AzureLib.</li>"
				+ "<li>Запускай гру без них по одному, щоб знайти проблемний.</li></ul>";
	}

	@Override
	public String nombreProblemaDecocraftNatureEssentialPartnerMod() {
		return "Проблема з Decocraft Nature";
	}

	@Override
	public String mensajeProblemaDecocraftNatureEssentialPartnerMod() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено проблему через Decocraft Nature.</b>"
				+ "<p>Помилка виникає при ініціалізації mixin конфігурації "
				+ "<code>com/razz/essentialpartnermod/mixins.json</code>.</p>"
				+ "<p>Це можна виправити, відредагувавши JAR файл моду.</p>" + "<p><b>Що робити:</b></p><ul>"
				+ "<li>Відкрий JAR через File Roller, Ark, WinRAR, 7-Zip або WinZip.</li>"
				+ "<li>Зайди в <code>META-INF/MANIFEST.MF</code>.</li>" + "<li>Видали цей рядок:</li></ul>"
				+ "<code>MixinConfigs: com/razz/essentialpartnermod/mixins.json</code>"
				+ "<p><b>Важливо:</b> залиш один порожній рядок в кінці файлу.</p>";
	}

	@Override
	public String mensajeTetraDeserializadorModeloEstatico() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено помилку в Tetra або її аддонах.</b>"
				+ "<p>Лог показує, що не знайдено десеріалізатор для моделі, яку використовує <b>Tetra</b> або її доповнення.</p>"
				+ "<p><b>Що робити:</b></p><ul>"
				+ "<li>Спочатку вимкни або видали <b>аддони Tetra</b> і перевір ще раз.</li>"
				+ "<li>Якщо не допомогло — прибери і сам <b>Tetra</b>.</li>"
				+ "<li>Можеш спробувати знайти аддони через <b>DepMap</b>, але вони там не завжди видно.</li>" + "</ul>"
				+ "<p>Іноді винен аддон, але буває, що і сам <b>Tetra</b> ламає гру.</p>";
	}

	@Override
	public String nombreTetraDeserializadorModeloEstatico() {
		return "Помилка десеріалізації моделі в Tetra";
	}

	@Override
	public String mensajeSimpleEmotesSetupAnimTail() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Знайдено помилку в Simple Emotes.</b>"
				+ "<p>Лог містить рядок <b>$simpleemotes$setupAnimTAIL</b>, що прямо вказує на мод <b>Simple Emotes</b>.</p>"
				+ "<p><b>Що робити:</b></p><ul>" + "<li>Вимкни або видали <b>Simple Emotes</b> і перевір.</li>"
				+ "<li>Якщо є моди, що змінюють анімації — перевір їх сумісність з <b>Simple Emotes</b>.</li>"
				+ "<li>Онови <b>Simple Emotes</b> і всі моди анімацій до сумісних версій.</li>" + "</ul>"
				+ "<p>Ця помилка майже завжди означає, що <b>Simple Emotes</b> прямо причетний до крашу.</p>";
	}

	@Override
	public String nombreSimpleEmotesSetupAnimTail() {
		return "Помилка Simple Emotes";
	}

	// Мова

	@Override
	public String mensajeAdvertenciaSKLauncher() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Попередження про SKLauncher.</b>"
				+ "<p>Останнім часом часто трапляються випадки <b>пошкодження даних</b> та інших проблем, пов'язаних із <b>SKLauncher</b>.</p>"
				+ "<p>Це не означає, що він завжди винен, але може створювати проблеми.</p>"
				+ "<p><b>Ознаки проблем:</b></p><ul>" + "<li>Гра вилітає одразу при запуску.</li>"
				+ "<li>Гра падає навіть <b>без модів</b>.</li></ul>"
				+ "<p>Якщо таке є — спробуй інший лаунчер і перевір, чи зникне проблема.</p>"
				+ "<p>Список нормальних лаунчерів тут:</p>"
				+ "<p><a href='https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/docs/ingles/minecraft/Launchers.md'>Дивитись список</a></p>";
	}

	@Override
	public String nombreAdvertenciaSKLauncher() {
		return "Попередження: можливі проблеми з SKLauncher";
	}

	@Override
	public String guardTitulo() {
		return "CD Guard";
	}

	@Override
	public String guardBotonLateral() {
		return "Guard";
	}

	@Override
	public String guardEscanearTodo() {
		return "Сканувати сервери і malware";
	}

	@Override
	public String guardEscanearServidores() {
		return "Шукати сервери";
	}

	@Override
	public String guardEscanearMalware() {
		return "Шукати malware";
	}

	@Override
	public String guardTablaServidores() {
		return "Проблемні сервери";
	}

	@Override
	public String guardTablaMalware() {
		return "Знайдений malware";
	}

	@Override
	public String guardColServidor() {
		return "Сервер";
	}

	@Override
	public String guardColDefinicion() {
		return "Опис";
	}

	@Override
	public String guardColMensaje() {
		return "Повідомлення";
	}

	@Override
	public String guardColUbicacion() {
		return "Розташування";
	}

	@Override
	public String guardColClase() {
		return "Клас";
	}

	@Override
	public String guardColCfr() {
		return "CFR";
	}

	@Override
	public String guardCfr() {
		return "Декомпілювати";
	}

	@Override
	public String guardCfrTitulo() {
		return "Декомпільований код";
	}

	@Override
	public String guardDescripcion1() {
		return "Цей інструмент шукає проблемні сервери і можливий malware у модах.";
	}

	@Override
	public String guardDescripcion2() {
		return "Можливі хибні спрацювання, особливо якщо антивірусні визначення занадто агресивні.";
	}

	@Override
	public String guardDescripcion3() {
		return "Перевірка серверів використовує зовнішні списки. Якщо їх нема — доведеться завантажити.";
	}

	@Override
	public String guardDescripcion4() {
		return "Якщо списки вже є — можеш вибрати: використовувати їх або оновити.";
	}

	@Override
	public String guardDescripcion5() {
		return "У таблиці malware, якщо клас доступний, можна його декомпілювати через CFR і подивитись код.";
	}

	@Override
	public String guardEstadoEscaneandoTodo() {
		return "Сканування серверів і malware...";
	}

	@Override
	public String guardEstadoEscaneandoServidores() {
		return "Пошук проблемних серверів...";
	}

	@Override
	public String guardEstadoEscaneandoMalware() {
		return "Пошук malware...";
	}

	@Override
	public String guardEstadoListo() {
		return "Готово";
	}

	@Override
	public String guardDefsNoEncontradasTitulo() {
		return "Не знайдено визначень";
	}

	@Override
	public String guardDefsNoEncontradasMensaje() {
		return "Не знайдено списків серверів. Завантажити зараз?";
	}

	@Override
	public String guardDefsDescargar() {
		return "Завантажити";
	}

	@Override
	public String guardDefsCancelar() {
		return "Скасувати";
	}

	@Override
	public String guardDefsActualizarTitulo() {
		return "Списки серверів";
	}

	@Override
	public String guardDefsActualizarMensaje() {
		return "Локальні списки вже є. Використати їх або оновити?";
	}

	@Override
	public String guardDefsUsarLocales() {
		return "Використати локальні";
	}

	@Override
	public String guardDefsActualizar() {
		return "Оновити";
	}

	@Override
	public String guardFuenteDefinicionesTLauncher() {
		return "Список TLauncher";
	}

	@Override
	public String guardErrorDescompilar() {
		return "Помилка декомпіляції";
	}

	@Override
	public String guardColorPanel() {
		return "Колір панелі";
	}

	@Override
	public String guardColorTexto() {
		return "Колір тексту";
	}

	@Override
	public String guardColorTextoSecundario() {
		return "Колір другорядного тексту";
	}

	@Override
	public String guardColorTabla() {
		return "Колір таблиць";
	}

	@Override
	public String guardColorSeleccion() {
		return "Колір виділення";
	}

	@Override
	public String guardColorSeleccionTexto() {
		return "Колір виділеного тексту";
	}

	@Override
	public String texto_de_boton_compartir_instancia_modpack() {
		return "Поділитися інстансом / модпаком";
	}

	@Override
	public String popup_compartir_instancia_modpack() {
		return "Функція поширення інстансу або модпака ще не зроблена.";
	}

	@Override
	public String colorBotonCompartirVerdeOscuro() {
		return "Колір головної кнопки поширення";
	}

	@Override
	public String colorBotonCompartirVerdeClaro() {
		return "Колір кнопки посилань";
	}

	@Override
	public String colorTextoBotonesCompartir() {
		return "Колір тексту кнопок поширення";
	}

	@Override
	public String compartirInstanciaTitulo() {
		return "Поділитися інстансом";
	}

	@Override
	public String compartirInstanciaBotonLateral() {
		return "Поділитися інстансом";
	}

	@Override
	public String compartirInstanciaFormato() {
		return "Формат";
	}

	@Override
	public String compartirInstanciaServicio() {
		return "Сервіс завантаження";
	}

	@Override
	public String compartirInstanciaBotonCompartir() {
		return "Запакувати і поділитися";
	}

	@Override
	public String compartirInstanciaBotonRefrescar() {
		return "Оновити";
	}

	@Override
	public String compartirInstanciaEstadoListo() {
		return "Готово";
	}

	@Override
	public String compartirInstanciaEstadoEmpaquetando() {
		return "Пакування вибраного...";
	}

	@Override
	public String compartirInstanciaEstadoSubiendo() {
		return "Завантаження файлу...";
	}

	@Override
	public String compartirInstanciaEstadoError() {
		return "Помилка";
	}

	@Override
	public String compartirInstanciaCodigo() {
		return "Код";
	}

	@Override
	public String compartirInstanciaEnlace() {
		return "Посилання";
	}

	@Override
	public String compartirInstanciaMantenerAbierto() {
		return "Тримай програму відкритою, інакше передача зникне.";
	}

	@Override
	public String compartirInstanciaSinSeleccion() {
		return "Нічого не вибрано.";
	}

	@Override
	public String compartirInstanciaFormatoNoSoportado() {
		return "Цей формат поки не підтримується.";
	}

	@Override
	public String compartirInstanciaServicioNoDisponible() {
		return "Обраний сервіс недоступний.";
	}

	@Override
	public String compartirInstanciaSubidaCompleta() {
		return "Передача успішно почалась.";
	}

	@Override
	public String compartirInstanciaErrorSubir() {
		return "Не вдалося завантажити файл.";
	}

	@Override
	public String compartirInstanciaColorPanel() {
		return "Колір панелі";
	}

	@Override
	public String compartirInstanciaColorTexto() {
		return "Колір тексту";
	}

	@Override
	public String compartirInstanciaPolitica1() {
		return "Рекомендується: mods, configs, saves, worlds, datapacks, resource packs і налаштування. Не кидай зайві приватні файли.";
	}

	@Override
	public String compartirInstanciaPolitica2() {
		return "Розширення можуть додавати свої сервіси. Стандартні повинні бути тут.";
	}

	@Override
	public String compartirInstanciaPolitica3() {
		return "wormhole.app: до 5 GiB звичайно; 5–10 GiB — треба тримати програму відкритою. У цій версії ще не реалізовано.";
	}

	@Override
	public String compartirInstanciaPolitica4() {
		return "limewire.com: тимчасове зберігання. Поки не підтримується.";
	}

	@Override
	public String compartirInstanciaPolitica5() {
		return "bittorrent: безпечніше через P2P без центрального сервера. Поки не підтримується.";
	}

	@Override
	public String compartirInstanciaPolitica6() {
		return "За замовчуванням вибираються основні файли інстансу для зручної допомоги.";
	}

	@Override
	public String compartirInstanciaPolitica7() {
		return "Якщо включиш " + Statics.nombre_cd.obtener()
				+ " — підуть і конфіги, логи і дані. Можеш прибрати, якщо не треба.";
	}

	@Override
	public String malware_fracturiser_detectado() {
		return "Можливий Fracturiser знайдено. Докази:";
	}

	@Override
	public String malware_info_stealer_detectado() {
		return "Можливий крадій даних знайдено. Докази:";
	}

	@Override
	public String malware_evidencia_clase_sospechosa() {
		return "Підозрілий клас:";
	}

	@Override
	public String malware_evidencia_archivo_sospechoso() {
		return "Підозрілий файл:";
	}

	@Override
	public String malware_brightsdk_detectado() {
		return "Можливий Bright SDK знайдено. Докази:";
	}

	@Override
	public String malware_evidencia_paquete_sospechoso() {
		return "Підозрілий пакет:";
	}

	@Override
	public String docsTituloVentana() {
		return "Перегляд документів";
	}

	@Override
	public String docsAyudaDeUso() {
		return "<b>Як користуватись</b><br>" + "Обери мову внизу, щоб побачити документи.<br>"
				+ "Зліва — папки і файли.<br>" + "Натисни документ — побачиш його справа.<br>"
				+ "Посилання <b>docs://</b> відкривають інші документи тут же.";
	}

	@Override
	public String docsArbolTitulo() {
		return "Документи";
	}

	@Override
	public String docsVisorTitulo() {
		return "Вміст";
	}

	@Override
	public String docsNoHayDocumentos() {
		return "Нема документів для цієї мови.";
	}

	@Override
	public String docsNoHayMarkdown() {
		return "Не знайдено Markdown-файлів.";
	}

	@Override
	public String docsDocumentoNoEncontrado() {
		return "Документ не знайдено.";
	}

	@Override
	public String docsErrorAlAbrirDocumento() {
		return "Помилка відкриття документа:";
	}

	@Override
	public String docsCargando() {
		return "Завантаження документів...";
	}

	@Override
	public String docsImagenNoDisponible() {
		return "Зображення недоступне";
	}

	@Override
	public String colorPanelSecundario() {
		return "Колір додаткової панелі";
	}

	@Override
	public String colorTextoSuave() {
		return "М'який колір тексту";
	}

	@Override
	public String colorSeleccion() {
		return "Колір виділення";
	}

	@Override
	public String colorFondoDocumento() {
		return "Колір фону документа";
	}

	@Override
	public String iaTituloVentana() {
		return "ШІ";
	}

	@Override
	public String iaTituloPrincipal() {
		return "Аналіз через ШІ";
	}

	@Override
	public String iaDescripcionTitulo() {
		return "Агент аналізу крашів";
	}

	@Override
	public String iaDescripcionCuerpo() {
		return "Цей інструмент відкриває зовнішнього агента для аналізу крашів, помилок і логів Minecraft.";
	}

	@Override
	public String iaDescripcionUso() {
		return "Відкрий посилання, увійди через Baidu і використай агента для аналізу логів або крашу.";
	}

	@Override
	public String iaAvisoCuentaBaidu() {
		return "Потрібен акаунт Baidu.";
	}

	@Override
	public String iaCopiarEnlace() {
		return "Скопіювати посилання";
	}

	@Override
	public String iaAbrirEnlace() {
		return "Відкрити посилання";
	}

	@Override
	public String iaImagenNoDisponible() {
		return "Зображення недоступне";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		return "Це відбувається тому, що ви використовуєте macOS, а гра намагається використовувати OpenGL, який не підтримується в останніх версіях macOS. "
				+ "Потрібно використовувати версію Minecraft з підтримкою Metal або використовувати Linux, якщо у вас Mac з Intel, M1 або M2 (але не M3+ або Neo).";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "Анімацію GeckoLib не знайдено";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Мод не зміг завантажити файл анімації GeckoLib.</b>" + "<p>Файл:</p>" + "<code>" + archivo
				+ "</code>"
				+ "<p>Ця помилка виникає, коли файл анімації <code>.json</code> відсутній, має синтаксичні помилки або шлях неправильний.</p>"
				+ "<p>Можливі причини:</p><ul>" + "<li>Файл було видалено, але він усе ще використовується в коді.</li>"
				+ "<li>Синтаксична помилка у JSON-файлі.</li>" + "<li>Неправильно вказаний шлях у моді.</li>"
				+ "<li>Конфлікти залежностей або несумісні версії.</li>" + "</ul>";
	}

	@Override
	public String nombreAnimacionGeckoInexiste() {
		return "Анімацію GeckoLib не знайдено";
	}

	@Override
	public String mensajeAnimacionGeckoInexiste(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Мод не зміг знайти файл анімації GeckoLib.</b>" + "<p>Файл:</p>" + "<code>" + archivo + "</code>"
				+ "<p>Ця помилка означає, що файл фізично відсутній або шлях неправильний.</p>"
				+ "<p>Можливі причини:</p><ul>" + "<li>Файл <code>.json</code> не включений у JAR мод.</li>"
				+ "<li>Помилка в написанні шляху (наприклад: 'animations' vs 'animaciones').</li>"
				+ "<li>Різниця у регістрі (Linux чутливий до регістру, Windows — ні).</li>"
				+ "<li>Мод або його залежності пошкоджені чи застарілі.</li>" + "</ul>";
	}

	@Override
	public String nombreRegistroDuplicadoObjeto() {
		return "Конфлікт дубльованої реєстрації";
	}

	@Override
	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto) {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color
				+ "'>Критичний конфлікт: об’єкт зареєстровано двічі. Моди </span>" + mod1 + "<span style='color:#"
				+ color + "'> та </span>" + mod2 + "<span style='color:#" + color
				+ "'> намагаються зареєструвати один і той самий об’єкт. Об’єкт: </span>" + objeto + "<br><br>";

		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Це зазвичай відбувається, коли два моди використовують однаковий ID або namespace.<br><br>"
				+ "<b>Рішення:</b><br><ul>" + "<li>Перевірте, чи один мод не є оновленням іншого.</li>"
				+ "<li>Видаліть один із конфліктуючих модів.</li>"
				+ "<li>Спробуйте змінити ID об’єкта в конфігурації.</li>" + "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreFalloFabricRenderingAPI() {
		return "Відсутня Fabric Rendering API";
	}

	@Override
	public String mensajeFalloFabricRenderingAPI() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color
				+ "'>Мод (зазвичай Porting Lib) не зміг працювати, бо </span>"
				+ "Fabric Rendering API<span style='color:#" + color + "'> відсутній.</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'><b>Рішення:</b><br>" + "<ul>"
				+ "<li>Оновіть Sodium до версії 0.6.0 або новішої.</li>" + "<li>Встановіть Fabric API.</li>"
				+ "<li>Для старих версій гри встановіть Indium.</li>" + "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRestriccionesDependenciaNoCumplidas() {
		return "Порушені залежності";
	}

	@Override
	public String nombreNeruinaOcultaAdvertencia() {
		return "Neruina приховує помилки";
	}

	@Override
	public String mensajeNeruinaOcultaAdvertencia() {
		String color = Config.obtenerInstancia().obtenerColorAdvertencia();

		return "<span style='color:#" + color + "'>"
				+ "<b>Попередження:</b> Мод Neruina приховує реальну причину крашу.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Рекомендується видалити його для коректної діагностики.</span>";
	}

	@Override
	public String nombreApothicAttributeSinDueño() {
		return "Помилка Apothic Attributes";
	}

	@Override
	public String nombreErrorPotBlockEntity() {
		return "Помилка DecoratedPot (Cataclysm)";
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos) {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>Знайдено </span>" + cantidad + "<span style='color:#"
				+ color + "'> невиконаних обмежень залежностей.</span><br><br>";

		StringBuilder listaDetalle = new StringBuilder();
		if (conflictos != null && !conflictos.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Виявлено конфлікти у таких файлах:</span><br><ul>");
			for (String[] par : conflictos) {
				String dep = par[0];
				String jar = par[1];
				listaDetalle.append("<li>").append("<span style='color:#").append(color).append("'>Файл: </span>")
						.append(jar).append("<br><span style='color:#").append(color).append("'>Потребує: </span>")
						.append(dep).append("</li>");
			}
			listaDetalle.append("</ul><br>");
		}

		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Це відбувається, коли два або більше модів вимагають різні несумісні версії однієї внутрішньої бібліотеки.<br><br>"
				+ "<b>Рекомендоване рішення:</b><br><ul>"
				+ "<li>Спробуйте оновити або видалити моди зі списку вище.</li>"
				+ "<li>Якщо сумісної версії немає, можна вручну відредагувати файл <code>mods.toml</code> всередині JAR (через WinRAR або 7-Zip), але це може призвести до нестабільності.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod) {

		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>Знайдено </span>" + cantidad + "<span style='color:#"
				+ color + "'> невиконаних обмежень залежностей.</span><br><br>";

		StringBuilder listaDetalle = new StringBuilder();
		if (conflictosPorMod != null && !conflictosPorMod.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Задіяні моди та запитувані залежності:</span><br><ul>");

			for (Map.Entry<String, List<String>> entry : conflictosPorMod.entrySet()) {
				String archivo = entry.getKey();
				List<String> dependencias = entry.getValue();

				listaDetalle.append("<li><b>").append(archivo).append("</b><ul>");
				for (String dep : dependencias) {
					listaDetalle.append("<li>").append(dep).append("</li>");
				}
				listaDetalle.append("</ul></li>");
			}
			listaDetalle.append("</ul><br>");
		} else {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Не вдалося визначити конкретні файли з логів.</span><br>");
		}

		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Ця помилка виникає, коли моди містять внутрішні бібліотеки (JarInJar), які несумісні між собою.<br><br>"
				+ "<b>Рекомендоване рішення:</b><br><ul>"
				+ "<li>Перевірте список вище, щоб визначити конфліктуючі залежності.</li>"
				+ "<li>Оновіть обидва моди до останніх версій.</li>"
				+ "<li>У крайньому випадку відредагуйте <code>META-INF/mods.toml</code> у JAR, але це ризиковано.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeApothicAttributeSinDueño(boolean chestCavityDetectado) {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Apothic Attributes</b>: конфлікт — AttributeMap змінено без власника.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Це трапляється, коли мод некоректно змінює атрибути сутностей.</span><br><br>";

		String notaChestCavity = "";
		if (chestCavityDetectado) {
			notaChestCavity = "<span style='color:#" + color + "'>"
					+ "<b>Виявлено мод Chest Cavity.</b> Часта причина цієї помилки.</span><br><br>";
		}

		String instrucciones = "<span style='color:#" + color + "'><b>Рішення:</b><br><ul>"
				+ "<li>Оновіть або тимчасово видаліть Chest Cavity.</li>"
				+ "<li>Вимкніть інші моди, що змінюють атрибути.</li>" + "<li>Оновіть Apothic Attributes.</li>"
				+ "</ul></span>";

		return mensajeBase + notaChestCavity + instrucciones;
	}

	@Override
	public String mensajeErrorPotBlockEntity() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Виявлено несумісність з <b>DecoratedPotBlockEntity</b>.</span><br><br>" + "<span style='color:#"
				+ color + "'>" + "Це відома проблема в моді <b>L_Enders_Cataclysm</b> (версія 1.19.2).</span><br><br>";

		String solucion = "<span style='color:#" + color + "'><b>Рішення:</b><br>" + "Встановіть мод <b>PotFix</b>: "
				+ "<a href='https://www.curseforge.com/minecraft/mc-mods/potfix-cataclysm-patch'>CurseForge</a>"
				+ "</span>";

		return mensajeBase + solucion;
	}

	@Override
	public String mensajeOculusIrisUnknownShaderVariable() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено можливу помилку шейдерів з Oculus або Iris.</b>"
				+ "<p>Журнал містить як <b>kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal</b>, "
				+ "так і <b>java.lang.RuntimeException: Unknown variable:</b>.</p>"
				+ "<p>Ця комбінація зазвичай вказує на проблему під час обчислення змінної всередині шейдера, "
				+ "що часто пов'язано з <b>Oculus</b>, <b>Iris</b> або використовуваним <b>паком шейдерів</b>.</p>"
				+ "<p><b>Рекомендований порядок дій:</b></p>" + "<ul>"
				+ "<li>Спочатку запустіть гру <b>без увімкнених шейдерів</b>.</li>"
				+ "<li>Якщо проблема не зникне, спробуйте запустити гру <b>без Oculus або Iris</b>.</li>"
				+ "<li>Оновіть <b>Oculus/Iris</b>, <b>пак шейдерів</b> та пов'язані графічні моди.</li>"
				+ "<li>Якщо ви використовуєте інші моди для рендерингу чи графіки, перевірте їх на сумісність.</li>"
				+ "</ul>"
				+ "<p>На практиці ця помилка зазвичай виникає через <b>пак шейдерів</b> або його взаємодію з <b>Oculus/Iris</b>.</p>";
	}

	@Override
	public String nombreOculusIrisUnknownShaderVariable() {
		return "Можлива помилка шейдерів з Oculus/Iris";
	}

	@Override
	public String mensajeItemNoExiste(String itemFaltante, String namespace) {
		String itemHtml = itemFaltante == null || itemFaltante.isEmpty() ? "(невідомо)" : itemFaltante;
		String namespaceHtml = namespace == null || namespace.isEmpty() ? "(невідомо)" : namespace;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Спроба використати предмет, якого не існує.</b>" + "<p>Журнал містить рядок <b>Item: " + itemHtml
				+ " does not exist</b>.</p>"
				+ "<p>Це зазвичай означає, що якийсь <b>датапак</b>, <b>мод</b> або <b>конфігурація</b> "
				+ "посилається на предмет, якого немає в грі.</p>" + "<p><b>Що перевірити:</b></p>" + "<ul>"
				+ "<li>Перевірте, чи встановлено мод, який має додавати предмет <b>" + itemHtml + "</b>.</li>"
				+ "<li>Якщо він встановлений, можливо, це <b>неправильна версія</b>, предмет було змінено або видалено, "
				+ "або в моді є помилка, і його краще видалити.</li>"
				+ "<li>Якщо цього мода немає, спробуйте <b>встановити його</b>.</li>" + "</ul>"
				+ "<p><b>Щоб дізнатися, який мод або датапак запитує цей предмет:</b></p>" + "<ul>"
				+ "<li>Використовуйте утиліту <b>grepr</b> на бічній панелі.</li>"
				+ "<li><b>Не</b> вибирайте папку.</li>" + "<li>Увімкніть опцію <b>search in archives</b>.</li>"
				+ "<li>У полі пошуку введіть <b>namespace</b>, тобто частину перед двокрапкою: " + "<b>" + namespaceHtml
				+ "</b>.</li>" + "</ul>"
				+ "<p>Це зазвичай допомагає знайти файл, мод або датапак, що містить недійсне посилання.</p>";
	}

	@Override
	public String nombreItemNoExiste() {
		return "Посилання на неіснуючий предмет";
	}

	@Override
	public String mensajeCobblemonPinkanIslandsRhyhornModelo() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено помилку моделі для Rhyhorn.</b>"
				+ "<p>Журнал містить рядок <b>Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn</b>.</p>"
				+ "<p>Хоча модель використовує простір імен (namespace) <b>Cobblemon</b>, цей рядок зазвичай викликаний модом "
				+ "<b>Cobblemon: Pinkan Islands</b>, звідки походить цей <b>Rhyhorn</b>.</p>"
				+ "<p><b>Що спробувати:</b></p>" + "<ul>"
				+ "<li>Видаліть або вимкніть <b>Cobblemon: Pinkan Islands</b> і спробуйте знову.</li>"
				+ "<li>Оновіть <b>Cobblemon</b> та <b>Cobblemon: Pinkan Islands</b> до сумісних версій.</li>"
				+ "<li>Якщо проблема почалася після оновлення одного з цих модів, спробуйте іншу комбінацію версій.</li>"
				+ "</ul>"
				+ "<p>Ця помилка зазвичай вказує на відсутню, неправильно зареєстровану або несумісну модель всередині "
				+ "<b>Cobblemon: Pinkan Islands</b>.</p>";
	}

	@Override
	public String nombreCobblemonPinkanIslandsRhyhornModelo() {
		return "Помилка моделі Rhyhorn у Cobblemon: Pinkan Islands";
	}

	@Override
	public String mensajeColdSweatInitDynamicTags() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено помилку в Cold Sweat.</b>"
				+ "<p>Журнал містить такі ознаки, як <b>$cold_sweat$onBuildStart</b>, "
				+ "<b>InitDynamicTagsEvent.fillTag</b> та <b>NullPointerException</b>, де "
				+ "реєстр відображається як null.</p>"
				+ "<p>Це зазвичай вказує на проблему <b>Cold Sweat</b> під час побудови або заповнення "
				+ "<b>динамічних тегів</b>, часто через несумісність, внутрішню помилку мода "
				+ "або конфліктну комбінацію з іншим модом або датапаком.</p>" + "<p><b>Що спробувати:</b></p>" + "<ul>"
				+ "<li>Видаліть або вимкніть <b>Cold Sweat</b> і спробуйте знову.</li>"
				+ "<li>Оновіть <b>Cold Sweat</b> до версії, сумісної з вашою версією Minecraft та завантажувачем.</li>"
				+ "<li>Якщо ви використовуєте датапаки або моди, що змінюють <b>теги</b>, <b>біоми</b>, <b>температури</b> або пов'язані реєстри, перевірте їх також.</li>"
				+ "<li>Якщо помилка почалася після оновлення модів, спробуйте іншу комбінацію версій.</li>" + "</ul>"
				+ "<p>У цьому випадку <b>Cold Sweat</b> безпосередньо залучений до збою.</p>";
	}

	@Override
	public String nombreColdSweatInitDynamicTags() {
		return "Помилка Cold Sweat у динамічних тегах";
	}

	@Override
	public String mensajeClassCastExceptionGeneral(String lineaClassCast) {
		String detalle = lineaClassCast == null || lineaClassCast.isEmpty() ? ""
				: "<p><b>Виявлений рядок:</b></p><p><code>" + lineaClassCast + "</code></p>";

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено ClassCastException.</b>"
				+ "<p>Це означає, що клас було сприйнято як інший, несумісний клас або інтерфейс.</p>" + detalle
				+ "<p>Такий тип помилки зазвичай викликаний однією з наступних ситуацій:</p>" + "<ul>"
				+ "<li><b>Два несумісні моди</b> між собою.</li>"
				+ "<li><b>Mixins</b>, <b>transformers</b> або патчі, які змінюють клас, через що інша частина гри очікує інший тип даних.</li>"
				+ "<li>Інші моди, присутні у <b>stacktrace</b>, які викликають некоректне приведення типів.</li>"
				+ "</ul>" + "<p><b>Що перевірити:</b></p>" + "<ul>"
				+ "<li>Перегляньте рядки <b>stacktrace</b>, пов'язані з цією помилкою.</li>"
				+ "<li>Зверніть особливу увагу на назви модів або класів у форматі <b>$modid$algo</b>, оскільки вони зазвичай вказують на залучені моди.</li>"
				+ "<li>Спробуйте оновити, видалити або відокремити моди, які, ймовірно, пов'язані з неправильним приведенням типів.</li>"
				+ "</ul>"
				+ "<p>Хоча <b>ClassCastException</b> не завжди є фатальним, дуже часто він призводить до крашу.</p>";
	}

	@Override
	public String nombreClassCastExceptionGeneral() {
		return "Виявлено ClassCastException";
	}

	@Override
	public String mensajeValkyrienSkiesTournamentLithiumPoiInjection() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено можливу несумісність між Valkyrien Skies Tournament та Lithium/Radium.</b>"
				+ "<p>Журнал містить <b>InvalidInjectionException</b>, де миксин "
				+ "<b>Lithium</b> для <b>POI</b> вказано разом із <b>valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager</b>.</p>"
				+ "<p>Ця проблема зазвичай виникає при використанні <b>старої версії Lithium</b> або "
				+ "<b>форка на базі старої версії Lithium</b>, такого як <b>Radium Reforged</b>, разом із "
				+ "<b>VS Tournament</b>.</p>" + "<p><b>Що спробувати:</b></p>" + "<ul>"
				+ "<li>Оновіть <b>Lithium</b> до новішої та сумісної версії.</li>"
				+ "<li>Якщо ви на <b>Forge/NeoForge</b> і використовуєте <b>Radium Reforged</b> або інший старий форк, видаліть його.</li>"
				+ "<li>Замість нього спробуйте <b>Harium</b> — сучасний форк Radium, синхронізований з останніми покращеннями Lithium.</li>"
				+ "<li>Якщо помилка з'явилася після оновлення модів, перевірте точну комбінацію версій між <b>VS Tournament</b> та вашим модом оптимізації AI/POI.</li>"
				+ "</ul>"
				+ "<p>На практиці ця помилка зазвичай викликана старою реалізацією <b>Lithium/Radium</b>, яка погано сумісна з <b>VS Tournament</b>.</p>";
	}

	@Override
	public String nombreValkyrienSkiesTournamentLithiumPoiInjection() {
		return "Несумісність VS Tournament з Lithium/Radium";
	}

	@Override
	public String mensajeVSTournamentVSConfigClassNoExiste() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Схоже, VS Tournament занадто старий для вашої версії Valkyrien Skies.</b>"
				+ "<p>Журнал містить <b>NoClassDefFoundError</b> для "
				+ "<b>org/valkyrienskies/core/impl/config/VSConfigClass</b>, а також рядок від "
				+ "<b>org.valkyrienskies.tournament.TournamentMod.init(...)</b>.</p>"
				+ "<p>Зазвичай це означає, що у вас <b>стара версія VS Tournament</b>, яка намагається "
				+ "використати старі внутрішні класи <b>Valkyrien Skies</b>, яких більше не існує.</p>"
				+ "<p><b>Що зробити:</b></p>" + "<ul>" + "<li>Видаліть старий <b>VS Tournament</b>.</li>"
				+ "<li>Натомість використовуйте <b>VS Tournament Reforged</b>.</li>"
				+ "<li>Також перевірте, чи версія <b>Valkyrien Skies</b> відповідає версії, що підтримується аддоном.</li>"
				+ "</ul>"
				+ "<p>Рекомендація перейти на <b>VS Tournament Reforged</b> відповідає поточному стану проєкту: "
				+ "оригінальна версія Tournament досі значиться як старий мод для 1.18.2, тоді як "
				+ "<b>VS Tournament Reforged</b> публікується окремо і наразі заявляє підтримку Valkyrien "
				+ "2.4.9+ на Forge 1.20.1.</p>";
	}

	@Override
	public String nombreVSTournamentVSConfigClassNoExiste() {
		return "Старий VS Tournament несумісний з Valkyrien Skies";
	}

	public String curseForgeClaveApiMundial() {
		return "Глобальний ключ API CurseForge";
	}

	public String curseForgeEndpoint() {
		return "Endpoint CurseForge";
	}

	public String tlmodsEndpoint() {
		return "Endpoint TLMods";
	}

	public String minecraftStorageEndpoint() {
		return "Endpoint MinecraftStorage";
	}

	public String autoBackupActivado() {
		return "Автоматичне резервне копіювання увімкнено";
	}

	public String autoBackupFrecuencia() {
		return "Частота автоматичного резервного копіювання";
	}

	public String autoBackupDiasConservar() {
		return "Днів зберігання автоматичних резервних копій";
	}

	public String autoBackupTamanoMaximoMB() {
		return "Максимальний розмір автоматичних резервних копій (МБ)";
	}

	public String actualizadorModsTitulo() {
		return "Оновлення модів";
	}

	public String actualizadorModsBotonSidebar() {
		return "Оновлення";
	}

	public String actualizadorModsDescripcion() {
		return "Перевіряє наявність оновлень для модів зі збірки. Ви можете оновити всі або застосувати окремі оновлення.";
	}

	public String actualizadorModsBotonEscanear() {
		return "Перевірити оновлення";
	}

	public String actualizadorModsBotonActualizarTodo() {
		return "Оновити все";
	}

	public String actualizadorModsBotonActualizarUno() {
		return "Оновити";
	}

	public String actualizadorModsEstadoListo() {
		return "Готово";
	}

	public String actualizadorModsEstadoEscaneando() {
		return "Пошук оновлень...";
	}

	public String actualizadorModsEstadoActualizando() {
		return "Оновлення...";
	}

	public String actualizadorModsEstadoSinActualizaciones() {
		return "Доступних оновлень немає.";
	}

	public String actualizadorModsEstadoEncontradas(int n) {
		return "Знайдено оновлень: " + n;
	}

	public String actualizadorModsEstadoActualizadas(int n) {
		return "Застосовано оновлень: " + n;
	}

	public String actualizadorModsEstadoError() {
		return "Помилка оновлення.";
	}

	public String actualizadorModsSinSeleccion() {
		return "Не вибрано жодного оновлення.";
	}

	public String actualizadorModsColumnaMod() {
		return "Мод";
	}

	public String actualizadorModsColumnaActual() {
		return "Поточна";
	}

	public String actualizadorModsColumnaNueva() {
		return "Нова";
	}

	public String actualizadorModsColumnaFuente() {
		return "Джерело";
	}

	public String actualizadorModsColumnaLoader() {
		return "Loader";
	}

	public String actualizadorModsColumnaRuta() {
		return "Шлях";
	}

	public String actualizadorModsColumnaAccion() {
		return "Дія";
	}

	public String actualizadorModsColorFondo() {
		return "Оновлення: фон";
	}

	public String actualizadorModsColorPanel() {
		return "Оновлення: панель";
	}

	public String actualizadorModsColorTexto() {
		return "Оновлення: текст";
	}

	public String actualizadorModsColorTextoSuave() {
		return "Оновлення: приглушений текст";
	}

	public String actualizadorModsColorBoton() {
		return "Оновлення: кнопка";
	}

	public String actualizadorModsColorBotonTexto() {
		return "Оновлення: текст кнопки";
	}

	public String actualizadorModsColorTabla() {
		return "Оновлення: таблиця";
	}

	public String actualizadorModsColorSeleccion() {
		return "Оновлення: виділення";
	}

	public String importadorYumeiriTeExtraniamos() {
		return "Ми сумуємо за тобою, Юмейрі Рею.";
	}

	public String importadorColorFondo() {
		return "Імпорт: фон";
	}

	public String importadorColorPanel() {
		return "Імпорт: панель";
	}

	public String importadorColorTexto() {
		return "Імпорт: текст";
	}

	public String importadorColorTextoSuave() {
		return "Імпорт: приглушений текст";
	}

	public String importadorColorBoton() {
		return "Імпорт: кнопка";
	}

	public String importadorColorBotonTexto() {
		return "Імпорт: текст кнопки";
	}

	public String importadorColorBorde() {
		return "Імпорт: рамка";
	}

	public String importadorConflictoTitulo() {
		return "Конфлікт під час імпорту";
	}

	public String importadorConflictoMensaje() {
		return "Файл вже існує в цільовій теці.";
	}

	public String importadorRuta() {
		return "Шлях";
	}

	public String importadorArchivoExistente() {
		return "Наявний файл";
	}

	public String importadorArchivoNuevo() {
		return "Імпортований файл";
	}

	public String importadorTamano() {
		return "Розмір";
	}

	public String importadorFecha() {
		return "Остання зміна";
	}

	public String importadorInfoMod() {
		return "Інформація про мод";
	}

	public String importadorModImportadoMasNuevo() {
		return "Імпортований мод здається новішим.";
	}

	public String importadorModImportadoMasViejo() {
		return "Імпортований мод здається старішим.";
	}

	public String importadorBotonReemplazar() {
		return "Замінити";
	}

	public String importadorBotonSaltar() {
		return "Пропустити";
	}

	public String importadorBotonRenombrar() {
		return "Перейменувати новий";
	}

	public String importadorModpackTitulo() {
		return "Імпорт модпаку";
	}

	public String importadorModpackBotonSidebar() {
		return "Імпорт";
	}

	public String importadorModpackDescripcion() {
		return "Імпортує модпак у поточний інстанс. Ви можете перетягнути файл .zip, .mrpack або інший підтримуваний формат, або вибрати його вручну.";
	}

	public String importadorModpackFormato() {
		return "Формат";
	}

	public String importadorModpackArrastraArchivo() {
		return "Перетягніть сюди ваш модпак або виберіть файл";
	}

	public String importadorModpackBotonSeleccionar() {
		return "Вибрати файл";
	}

	public String importadorModpackBotonImportar() {
		return "Імпортувати";
	}

	public String importadorModpackSeleccionarArchivo() {
		return "Вибрати модпак";
	}

	public String importadorModpackEstadoListo() {
		return "Готово";
	}

	public String importadorModpackEstadoImportando() {
		return "Імпорт...";
	}

	public String importadorModpackEstadoError() {
		return "Помилка імпорту.";
	}

	public String importadorModpackSinArchivo() {
		return "Спочатку виберіть файл модпаку.";
	}

	public String importadorModpackFormatoNoSoportado() {
		return "Цей формат не підтримує імпорт.";
	}

	public String importadorModpackResultado(int copiados, int reemplazados, int saltados, int renombrados,
			int errores) {
		return "Імпорт завершено.\nСкопійовано: " + copiados + "\nЗамінено: " + reemplazados + "\nПропущено: "
				+ saltados + "\nПерейменовано: " + renombrados + "\nПомилки: " + errores;
	}

	public String importadorModpackColorFondo() {
		return "Імпорт модпаків: фон";
	}

	public String importadorModpackColorPanel() {
		return "Імпорт модпаків: панель";
	}

	public String importadorModpackColorTexto() {
		return "Імпорт модпаків: текст";
	}

	public String importadorModpackColorTextoSuave() {
		return "Імпорт модпаків: приглушений текст";
	}

	public String importadorModpackColorBoton() {
		return "Імпорт модпаків: кнопка";
	}

	public String importadorModpackColorBotonTexto() {
		return "Імпорт модпаків: текст кнопки";
	}

	public String importadorModpackColorDrop() {
		return "Імпорт модпаків: зона перетягування";
	}

	public String importadorModpackColorBorde() {
		return "Імпорт модпаків: рамка";
	}

	public String jgitTituloIzzy() {
		return "Git-центр Izzy";
	}

	public String jgitRetratoIzzy() {
		return "Izzy";
	}

	public String jgitNoHayRetratoIzzy() {
		return "Портрет Izzy відсутній";
	}

	public String jgitSeccionInstalacion() {
		return "Встановлення JGit";
	}

	public String jgitAbrirCarpetaInstalacion() {
		return "Відкрити теку встановлення";
	}

	public String jgitAbrirPaginaDescarga() {
		return "Відкрити сторінку завантаження JGit";
	}

	public String jgitSeccionRepositorio() {
		return "Локальний репозиторій";
	}

	public String jgitCrearRepositorioLocal() {
		return "Створити Git-репозиторій тут";
	}

	public String jgitCommitManual() {
		return "Ручний коміт";
	}

	public String jgitSeccionRemote() {
		return "Віддалений репозиторій";
	}

	public String jgitForgeManual() {
		return "Посібник Forge";
	}

	public String jgitForgePersonalizado() {
		return "Користувацький Forge";
	}

	public String jgitEstablecerRemoteManual() {
		return "Налаштувати віддалений репозиторій вручну";
	}

	public String jgitCrearRemoteConAPI() {
		return "Створити віддалений репозиторій через API";
	}

	public String jgitPushManual() {
		return "Ручний push";
	}

	public String jgitSeccionAutomaticos() {
		return "Автоматизація";
	}

	public String jgitAutoCommitDespuesBackup() {
		return "Авто-коміт після бекапу";
	}

	public String jgitAutoPushDespuesCommit() {
		return "Авто-push після коміту";
	}

	public String jgitSeccionHerramientas() {
		return "Інструменти";
	}

	public String jgitAbrirGuiSwing() {
		return "Відкрити Swing-переглядач JGit";
	}

	public String jgitEstado() {
		return "Статус";
	}

	public String jgitClasspath() {
		return "JGit у classpath";
	}

	public String jgitTodosLosArtefactos() {
		return "Усі артефакти JGit";
	}

	public String jgitRepositorio() {
		return "Репозиторій";
	}

	public String jgitRemote() {
		return "Віддалений репозиторій";
	}

	public String jgitCarpetaActual() {
		return "Поточна тека";
	}

	public String jgitNoSePudoCrearRepo() {
		return "Не вдалося створити репозиторій.";
	}

	public String jgitEscribaRemote() {
		return "Введіть URL віддаленого репозиторію:";
	}

	public String jgitNoSePudoGuardarRemote() {
		return "Не вдалося зберегти віддалений репозиторій.";
	}

	public String jgitApiForgeAunNoImplementada() {
		return "API Forge ще не реалізовано.";
	}

	public String jgitNoHayCambiosOError() {
		return "Немає змін для коміту або сталася помилка.";
	}

	public String jgitNoSePudoPush() {
		return "Не вдалося виконати push.";
	}

	public String jgitTituloVentanaSwing() {
		return "Переглядач Git";
	}

	public String jgitNoHayRepositorio() {
		return "У цій теці немає Git-репозиторію.";
	}

	public String jgitArchivosModificados() {
		return "Змінені файли";
	}

	public String jgitArchivosNuevos() {
		return "Нові файли";
	}

	public String jgitUltimosCommits() {
		return "Останні коміти";
	}

	public String jgitError() {
		return "Помилка JGit";
	}

	public String si() {
		return "Так";
	}

	public String no() {
		return "Ні";
	}

	public String jgitDescargarDependenciasFaltantes() {
		return "Завантажити відсутні залежності";
	}

	public String jgitNoFaltanDependencias() {
		return "Відсутні залежності JGit не виявлено.";
	}

	public String jgitConfirmarDescargaDependencias(int cantidad) {
		return "Відсутня кількість залежностей JGit: " + cantidad + ". Бажаєте завантажити їх з Maven Central?";
	}

	public String jgitDependenciasDescargadas(int cantidad) {
		return "Залежностей завантажено: " + cantidad;
	}

	public String jgitErroresDescarga() {
		return "Помилки завантаження";
	}

	public String jgitReiniciarParaCargarClasspath() {
		return "Перезапустіть " + Statics.nombre_cd.obtener() + ", щоб нові JAR-файли потрапили до classpath.";
	}

	public String jgitArtefactosFaltantes() {
		return "Відсутні артефакти";
	}

	public String jgitArtefactosFaltantesClasspath() {
		return "Відсутні артефакти у classpath";
	}

	public String jgitArtefactosFaltantesCarpeta() {
		return "Відсутні артефакти у теці встановлення";
	}

	public String jgitDependenciasEnCarpeta() {
		return "Залежності, встановлені в теку";
	}

	public String jgitForgeNoSeleccionada() {
		return "Forge не вибрано.";
	}

	public String jgitForgeNoRegistrada(String id) {
		return "Forge не зареєстровано: " + id;
	}

	public String jgitEscribaUrlForge() {
		return "URL Forge:";
	}

	public String jgitEscribaNombreRepositorio() {
		return "Назва репозиторію:";
	}

	public String jgitEscribaDescripcionRepositorio() {
		return "Опис репозиторію:";
	}

	public String jgitEscribaNamespaceOpcional() {
		return "Необов'язковий namespace:";
	}

	public String jgitEscribaTokenForge() {
		return "API-токен Forge:";
	}

	public String jgitErrorCrearRemote() {
		return "Помилка створення remote";
	}

	@Override
	public String mensajeControlifyRemoveReloadingScreen() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено несумісність між Controlify та Remove Reloading Screen.</b>"
				+ "<p>Журнал містить рядки <b>Attempted to fetch default config before DefaultConfigManager was ready!</b> "
				+ "та <b>$rrls$init</b>, що зазвичай вказує на конфлікт між <b>Controlify</b> та "
				+ "<b>Remove Reloading Screen</b>.</p>"
				+ "<p><b>Ймовірна причина:</b> Remove Reloading Screen змінює частини екрана завантаження або процесу завантаження, "
				+ "тоді як Controlify намагається ініціалізувати свою конфігурацію до повної готовності системи.</p>"
				+ "<p><b>Рекомендовані дії:</b></p>" + "<ul>" + "<li>Видаліть <b>Remove Reloading Screen</b>.</li>"
				+ "<li>Або оновіть <b>Controlify</b> та <b>Remove Reloading Screen</b>, якщо доступні нові версії.</li>"
				+ "<li>Якщо проблема persists, залиште <b>Controlify</b> і видаліть будь-які моди, що змінюють екран завантаження.</li>"
				+ "</ul>" + "<p>Моди, що змінюють екран завантаження, часто викликають несумісність з іншими модами, "
				+ "і зазвичай приносять мало практичної користі порівняно з проблемами, які вони можуть спричинити.</p>";
	}

	@Override
	public String nombreControlifyRemoveReloadingScreen() {
		return "Несумісність: Controlify проти Remove Reloading Screen";
	}

	@Override
	public String mensajeBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Можлива проблема з Biomes O' Plenty та користувацькими рідинами.</b>"
				+ "<p>Журнал містить помилку <b>class org.joml.Vector4f cannot be cast to class "
				+ "net.minecraft.client.renderer.fog.FogData</b> разом із посиланням на <b>Biomes O' Plenty</b>.</p>"
				+ "<p>Це, ймовірно, пов'язано з <b>Biomes O' Plenty</b>, особливо з біомами, туманом "
				+ "або користувацькими рідинами. Однак не зовсім впевнено, що Biomes O' Plenty є єдиною причиною.</p>"
				+ "<p><b>Рекомендовані дії:</b></p>" + "<ul>"
				+ "<li>Спробуйте відредагувати дані гравця, щоб перемістити його в інше місце світу.</li>"
				+ "<li>Спробуйте завантажити світ без <b>Biomes O' Plenty</b>.</li>"
				+ "<li>Якщо світ завантажується після переміщення гравця, проблема, ймовірно, виникає в певній зоні, "
				+ "певному біомі або nearby користувацькій рідині.</li>"
				+ "<li>Ви також можете спробувати оновити <b>Biomes O' Plenty</b> та моди, пов'язані з рендерингом, туманом, "
				+ "шейдерами або вимірами.</li>" + "</ul>"
				+ "<p>Якщо видалення Biomes O' Plenty дозволяє запустити гру, перевірте, чи перебував гравець всередині або поруч з біомом "
				+ "або рідиною, доданою цим модом.</p>";
	}

	@Override
	public String nombreBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "Можлива проблема: Biomes O' Plenty та FogData";
	}

	@Override
	public String mensajeKotlinReflectionInternalErrorVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено внутрішню помилку рефлексії Kotlin.</b>"
				+ "<p>Журнал містить <b>kotlin.reflect.jvm.internal.KotlinReflectionInternalError</b> з повідомленням, схожим на "
				+ "<b>Property 'none' not resolved</b>.</p>"
				+ "<p>Цей тип помилки поширений з певними версіями <b>Fabric Language Kotlin</b> / <b>Kotlin</b>. "
				+ "У цьому випадку з'являється клас з <b>Inventory Profiles Next</b>, але така ж проблема може виникати "
				+ "і з іншими модами, що використовують Kotlin.</p>" + "<p><b>Рекомендовані дії:</b></p>" + "<ul>"
				+ "<li>Оновіть <b>Fabric Language Kotlin</b> до версії <b>2.3.40</b>, якщо вона доступна для вашої версії Minecraft.</li>"
				+ "<li>Якщо оновлення не допомагає, спробуйте понизити версію <b>Fabric Language Kotlin</b> до <b>2.3.10</b>.</li>"
				+ "<li>Також оновіть <b>Inventory Profiles Next</b>, якщо в журналі згадуються класи цього моду.</li>"
				+ "<li>Якщо помилка з'являється з іншим модом, перевірте, чи залежить цей мод від Kotlin, і спробуйте змінити версію "
				+ "<b>Fabric Language Kotlin</b>.</li>" + "</ul>" + "<p>Пов'язана технічна довідка: "
				+ "<a href='https://github.com/FabricMC/fabric-language-kotlin/issues/183'>Проблема #183 Fabric Language Kotlin</a>.</p>";
	}

	@Override
	public String nombreKotlinReflectionInternalErrorVersion() {
		return "Помилка Kotlin: внутрішня рефлексія";
	}

	public String tituloEscanerMCreator() {
		return "Сканер MCreator";
	}

	public String escanerMCreatorEscaneandoMods() {
		return "Сканування модів...";
	}

	public String escanerMCreatorPorFavorEspera() {
		return "Будь ласка, зачекайте.";
	}

	public String escanerMCreatorResultadosAnalisis() {
		return "Результати аналізу MCreator:";
	}

	public String escanerMCreatorNoSeEncontraronMods() {
		return "Моди MCreator не знайдено.";
	}

	public String escanerMCreatorEscaneoCompletado() {
		return "Сканування завершено.";
	}

	public String escanerMCreatorErrorDuranteEscaneo() {
		return "Помилка під час сканування:";
	}

	public String escanerMCreatorCargando() {
		return "Завантаження...";
	}

	public String escanerMCreatorCompletado() {
		return "Завершено";
	}

	public String escanerMCreatorError() {
		return "Помилка";
	}

	@Override
	public String textoNormal() {
		return "Звичайний текст";
	}

	@Override
	public String noSeEncontroConsolaParaArchivo() {
		return "Консоль для файлу не знайдено: ";
	}

	@Override
	public String lineaSeleccionadaEnLectador() {
		return "вибраний рядок у зчитувачі: ";
	}

	@Override
	public String mensajeMotionBlurBufferCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Можлива проблема з Motion Blur.</b>"
				+ "<p>Лог містить посилання на <b>net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO</b> "
				+ "а також помилку <b>java.lang.IllegalStateException: Buffer already closed</b>.</p>"
				+ "<p>Ці рядки можуть з'являтися в лозі окремо, але разом вони зазвичай вказують на те, що проблема пов'язана "
				+ "з модом <b>Motion Blur</b> або з його обробкою шейдерів/графічних буферів.</p>"
				+ "<p><b>Рекомендовані дії:</b></p>" + "<ul>"
				+ "<li>Спробуйте запустити гру без <b>Motion Blur</b>.</li>"
				+ "<li>Якщо гра запускається нормально без цього моду, залиште його видаленим або знайдіть новішу версію.</li>"
				+ "<li>Ви також можете спробувати вимкнути шейдери або інші моди рендерингу, якщо проблема persists.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreMotionBlurBufferCerrado() {
		return "Можлива проблема: Motion Blur";
	}

	@Override
	public String mensajeGeneratorAcceleratorOwoVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Можливий конфлікт з Generator Accelerator.</b>"
				+ "<p>Лог містить різницю між підписами <b>Found</b> та <b>Available</b>, разом із класами з "
				+ "<b>Generator Accelerator</b>, наприклад <b>dev/sixik/generator_accelerator/common/features/FastTarget</b>.</p>"
				+ "<p>Ця помилка, ймовірно, викликана <b>Generator Accelerator</b>. Вона також може бути пов'язана "
				+ "з несумісністю цього моду з певними версіями <b>owo-lib</b>.</p>"
				+ "<p><b>Рекомендовані дії:</b></p>" + "<ul>"
				+ "<li>Спробуйте запустити гру без <b>Generator Accelerator</b>.</li>"
				+ "<li>Якщо гра запускається нормально, залиште цей мод видаленим або знайдіть іншу версію.</li>"
				+ "<li>Спробуйте оновити або змінити версію <b>owo-lib</b>, особливо якщо інші моди також залежать від owo.</li>"
				+ "<li>Переконайтеся, що <b>Generator Accelerator</b>, <b>owo-lib</b>, завантажувач та версія Minecraft сумісні між собою.</li>"
				+ "</ul>"
				+ "<p>Найімовірніша причина полягає в тому, що Generator Accelerator намагається застосувати модифікацію з підписом, "
				+ "який не відповідає поточній версії класу або залежності.</p>";
	}

	@Override
	public String nombreGeneratorAcceleratorOwoVersion() {
		return "Можливий конфлікт: Generator Accelerator та owo-lib";
	}

	@Override
	public String mensajeFabricRenderingApiFaltaIndium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Відсутній рендерер, сумісний із Fabric Rendering API.</b>"
				+ "<p>Лог містить помилку, де <b>RendererAccess.getRenderer()</b> повертає <b>null</b>, "
				+ "що призводить до збою при спробі використати <b>Renderer.materialFinder()</b>.</p>"
				+ "<p>Ця проблема зазвичай виникає, коли <b>Fabric Rendering API</b> недоступний належним чином. "
				+ "Поширеною причиною є використання <b>Sodium</b>, особливо старих версій, які замінюють або вимикають частини "
				+ "системи рендерингу, очікуваної іншими модами.</p>" + "<p><b>Рекомендоване рішення:</b></p>" + "<ul>"
				+ "<li>Встановіть мод <b>Indium</b>.</li>"
				+ "<li>Переконайтеся, що <b>Indium</b> сумісний з вашою версією <b>Sodium</b>, Fabric Loader та Minecraft.</li>"
				+ "<li>Якщо Indium вже встановлено, оновіть <b>Sodium</b>, <b>Indium</b> та <b>Fabric API</b>.</li>"
				+ "<li>Якщо проблема persists, тимчасово спробуйте запустити гру без Sodium, щоб підтвердити, що збой пов'язаний з рендерером.</li>"
				+ "</ul>"
				+ "<p>Indium зазвичай відновлює сумісність із Fabric Rendering API для модів, які залежать від цієї системи, "
				+ "поки встановлено Sodium.</p>";
	}

	@Override
	public String nombreFabricRenderingApiFaltaIndium() {
		return "Відсутній Indium / Fabric Rendering API";
	}

	@Override
	public String mensajeEntradaDuplicadaIdModerno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Виявлено дубльований запис у реєстрі Minecraft.</b>"
				+ "<p>Лог містить помилку, схожу на <b>Duplicate entry on id</b>, наприклад "
				+ "<b>current=maroon, previous=mint</b>.</p>"
				+ "<p>У сучасних версіях Minecraft така помилка зазвичай виникає, коли два моди намагаються зареєструвати "
				+ "різні записи, використовуючи той самий внутрішній ID.</p>" + "<p><b>Рекомендовані дії:</b></p>"
				+ "<ul>" + "<li>Видаліть один із модів, який реєструє дубльований запис.</li>"
				+ "<li>Якщо ви впізнаєте імена, згадані в помилці, перевірте, який мод додає ці записи, і спробуйте запустити гру без нього.</li>"
				+ "<li>Якщо ви не впізнаєте імена, скористайтеся утилітою <b>grepr</b> на бічній панелі.</li>"
				+ "<li>У <b>grepr</b> увімкніть пошук всередині стиснених файлів <b>.jar</b>, <b>.zip</b> та <b>.fpm</b>.</li>"
				+ "<li>Також увімкніть пошук у <b>бінарних файлах</b>, оскільки деякі імена або ID можуть знаходитися всередині скомпільованих класів.</li>"
				+ "</ul>"
				+ "<p>Шукайте значення, згадані в помилці, такі як <b>maroon</b> або <b>mint</b>, щоб знайти, який мод їх містить.</p>";
	}

	@Override
	public String nombreEntradaDuplicadaIdModerno() {
		return "Дубльований запис в ID моду";
	}

	@Override
	public String nombreOpenGLMemoriaInsuficiente() {
		return "OpenGL – недостатньо відеопам'яті";
	}

	@Override
	public String mensajeOpenGLMemoriaInsuficiente() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft видав помилку OpenGL через нестачу графічної пам'яті.</b>" + "<p>Гра повідомила:</p>"
				+ "<code>GL_OUT_OF_MEMORY</code>"
				+ "<p>Це зазвичай означає, що відеокарта або система не змогли виділити достатньо пам'яті для текстур, шейдерів, моделей, буферів або візуальних ефектів.</p>"
				+ "<p><b>Поширені причини:</b></p>" + "<ul>" + "<li>Занадто важкі шейдери.</li>"
				+ "<li>Паки ресурсів (Resource packs) високої роздільної здатності.</li>"
				+ "<li>Занадто багато візуальних модів або модів на рендеринг.</li>"
				+ "<li>Занадто велика дальність промальовування.</li>" + "<li>Мало доступної VRAM.</li>"
				+ "<li>Застарілі або нестабільні драйвери відеокарти.</li>" + "</ul>"
				+ "<p><b>Рекомендоване рішення:</b></p>" + "<ul>" + "<li>Тимчасово вимкнути шейдери.</li>"
				+ "<li>Використовувати паки ресурсів нижчої роздільної здатності.</li>"
				+ "<li>Зменшити дальність промальовування та симуляції.</li>"
				+ "<li>Знизити якість графіки, тіні, частинки та міпмапи.</li>"
				+ "<li>Оновити драйвери відеокарти.</li>"
				+ "<li>Закрити інші програми, які використовують GPU або багато пам'яті.</li>" + "</ul>"
				+ "<p>Якщо помилка почалася після встановлення шейдера, пакету текстур або візуального моду, швидше за все, причина в них.</p>";
	}

	@Override
	public String nombreErrorVerificacionBytecode() {
		return "VerifyError – недійсний байткод або міксин";
	}

	@Override
	public String mensajeErrorVerificacionBytecode(String ubicacion, String razon, String claseSospechosa) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft виявив помилку перевірки байткоду.</b>"
				+ "<p>Ця проблема зазвичай виникає, коли маніпуляція з байткодом, трансформер або міксин завершуються з помилкою.</p>"
				+ "<p>Гра повідомила:</p>" + "<code>java.lang.VerifyError</code>"
				+ (ubicacion.length() > 0 ? "<p><b>Місцезнаходження:</b></p><code>" + ubicacion + "</code>" : "")
				+ (razon.length() > 0 ? "<p><b>Причина:</b></p><code>" + razon + "</code>" : "")
				+ "<p><b>Що шукати:</b></p>" + "<ul>" + "<li>Шукати в місцезнаходженні помилки.</li>"
				+ "<li>Шукати тип, згаданий у <code>Reason</code>.</li>"
				+ "<li>Перевірити трасування стека на наявність підозрілих класів модів.</li>"
				+ "<li>Шукати імена класів модів навколо помилки для отримання ідей.</li>" + "</ul>"
				+ "<p><b>Рекомендоване використання Grepr:</b></p>" + "<ul>"
				+ "<li>Відкрити <b>Grepr</b> на бічній панелі.</li>"
				+ "<li>Увімкнути опцію пошуку всередині файлів <code>.jar</code>, <code>.zip</code> або <code>.fpm</code>.</li>"
				+ "<li>Шукати базове ім'я класу, а не обов'язково повний пакет.</li>" + "</ul>"
				+ "<p>Приклад: якщо з'являється <code>paquete.Clase</code>, шукати лише:</p>" + "<code>"
				+ (claseSospechosa.length() > 0 ? claseSospechosa : "Clase") + "</code>"
				+ "<p>Це може допомогти знайти, який мод містить або змінює цей клас.</p>"
				+ "<p>Поширені рішення: оновити пошкоджений мод, видалити несумісні моди, перевірити аддони основного моду або спробувати запустити гру без модів, які використовують міксини/трансформери для того ж класу.</p>";
	}

	@Override
	public String errorMetodoFinalSobrescrito(String claseQueSobrescribe, String metodoFinal) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Помилка сумісності: мод намагається перевизначити фінальний метод.</b>"
				+ "<p>Лог містить помилку <b>IncompatibleClassChangeError</b> з текстом "
				+ "<b>overrides final method</b>.</p>" + "<p>Вражений клас: <code>" + claseQueSobrescribe
				+ "</code></p>" + "<p>Вражений фінальний метод: <code>" + metodoFinal + "</code></p>"
				+ "<p>Ця помилка зазвичай виникає, коли мод було скомпільовано для іншої версії Minecraft, "
				+ "Forge, NeoForge, Fabric, Quilt або базової бібліотеки.</p>" + "<p><b>Що спробувати:</b></p>" + "<ul>"
				+ "<li>Оновіть мод, що містить вказаний клас.</li>"
				+ "<li>Якщо проблема почалася після оновлення Minecraft або завантажувача, спробуйте сумісну версію моду.</li>"
				+ "<li>Якщо клас належить до <b>Immersive Portals</b>, переконайтеся, що <b>Immersive Portals</b> точно відповідає вашій версії Minecraft та завантажувача.</li>"
				+ "<li>Уникайте змішування модів, створених для різних версій завантажувача або Minecraft.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreErrorMetodoFinalSobrescrito() {
		return "Мод намагається перевизначити фінальний метод";
	}

	@Override
	public String errorCrashProvocadoPorComando(String comandoDetectado) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft було закрито через команду крашу.</b>" + "<p>Лог вказує, що було виконано команду <code>"
				+ comandoDetectado + "</code>.</p>"
				+ "<p>Це зазвичай означає, що гра закрилася не через звичайну помилку модів, а тому що хтось "
				+ "використав команду, призначену для навмисного виклику крашу.</p>" + "<p><b>Що перевірити:</b></p>"
				+ "<ul>" + "<li>Перевірте, хто виконав команду в консолі або всередині гри.</li>"
				+ "<li>Якщо це було тестування, ви можете ігнорувати цей краш.</li>"
				+ "<li>Якщо це сталося ненавмисно, перевірте командні блоки, скрипти, датапаки, адміністративні моди або права операторів.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreCrashProvocadoPorComando() {
		return "Краш, спричинений командою";
	}

	public String impactoAlto() {
		return "Високий";
	}

	public String impactoMedio() {
		return "Середній";
	}

	public String impactoBajo() {
		return "Низький";
	}

	public String impactoBajoMedio() {
		return "Низький/Середній";
	}

	public String riesgoAlto() {
		return "Високий";
	}

	public String riesgoMedio() {
		return "Середній";
	}

	public String riesgoBajo() {
		return "Низький";
	}

	public String riesgoMedioAlto() {
		return "Середній/Високий";
	}

	public String tituloCrearConfigBBE() {
		return "Створити конфігурацію Better Block Entities";
	}

	public String descripcionCrearConfigBBE() {
		return "Файл BBEConfig.json відсутній.";
	}

	public String sugerenciaCrearConfigBBE() {
		return "Створити BBEConfig.json з оптимізаціями для скринь, shulkers, табличок, ліжок, дзвонів та прапорів.";
	}

	public String tituloActivarOptimizacionMaestraBBE() {
		return "Увімкнути основну оптимізацію BBE";
	}

	public String descripcionActivarOptimizacionMaestraBBE() {
		return "Схоже, у Better Block Entities не ввімкнена основна оптимізація.";
	}

	public String sugerenciaActivarOptimizacionMaestraBBE() {
		return "Додати {\"option\":\"optimize.master\",\"value\":true}";
	}

	public String tituloActivarCullingTextoCartelesBBE() {
		return "Увімкнути відсіювання (culling) тексту табличок";
	}

	public String descripcionActivarCullingTextoCartelesBBE() {
		return "Зменшує рендер тексту табличок на відстані.";
	}

	public String sugerenciaActivarCullingTextoCartelesBBE() {
		return "Додати {\"option\":\"misc.sign_text_culling\",\"value\":true}";
	}

	public String tituloAumentarSleepDelayEntityCulling() {
		return "Збільшити sleepDelay у EntityCulling";
	}

	public String descripcionAumentarSleepDelayEntityCulling() {
		return "EntityCulling перевірятиме сутності рідше.";
	}

	public String sugerenciaAumentarSleepDelayEntityCulling() {
		return "\"sleepDelay\": 153";
	}

	public String tituloSubirLimiteHitboxEntityCulling() {
		return "Збільшити ліміт hitbox";
	}

	public String descripcionSubirLimiteHitboxEntityCulling() {
		return "Дозволяє більш агресивне відсіювання до переходу на повільніші шляхи.";
	}

	public String sugerenciaSubirLimiteHitboxEntityCulling() {
		return "\"hitboxLimit\": 90";
	}

	public String tituloDesactivarDatosF3EntityCulling() {
		return "Вимкнути дані F3 у EntityCulling";
	}

	public String descripcionDesactivarDatosF3EntityCulling() {
		return "Прибирає зайву налагоджувальну інформацію моду.";
	}

	public String sugerenciaDesactivarDatosF3EntityCulling() {
		return "\"disableF3\": true";
	}

	public String tituloActivarBufferingSignsImmediatelyFast() {
		return "Увімкнути експериментальну буферизацію табличок";
	}

	public String descripcionActivarBufferingSignsImmediatelyFast() {
		return "Може покращити продуктивність за великої кількості табличок.";
	}

	public String sugerenciaActivarBufferingSignsImmediatelyFast() {
		return "\"experimental_sign_text_buffering\": true";
	}

	public String tituloReducirConflictosResourcePacksImmediatelyFast() {
		return "Зменшити обробку конфліктів resource pack";
	}

	public String descripcionReducirConflictosResourcePacksImmediatelyFast() {
		return "Може прибрати зайве навантаження, але також може викликати візуальні проблеми з resource pack.";
	}

	public String sugerenciaReducirConflictosResourcePacksImmediatelyFast() {
		return "\"experimental_disable_resource_pack_conflict_handling\": true";
	}

	public String tituloOcultarBotonNCR() {
		return "Приховати кнопку No Chat Reports";
	}

	public String descripcionOcultarBotonNCR() {
		return "Зміна інтерфейсу; не сильно підвищує FPS, але очищає екран.";
	}

	public String sugerenciaOcultarBotonNCR() {
		return "\"showNCRButton\": false";
	}

	public String tituloActivarMixinsExperimentalesLithium() {
		return "Увімкнути експериментальні міксини Lithium";
	}

	public String descripcionActivarMixinsExperimentalesLithium() {
		return "Вмикає додаткові експериментальні оптимізації.";
	}

	public String sugerenciaActivarMixinsExperimentalesLithium() {
		return "mixin.experimental=true";
	}

	public String tituloUsarDetectorThreadingPequenoFerriteCore() {
		return "Використовувати малий детектор багатопотоковості";
	}

	public String descripcionUsarDetectorThreadingPequenoFerriteCore() {
		return "Знижує споживання пам'яті, але може бути ризикованішим.";
	}

	public String sugerenciaUsarDetectorThreadingPequenoFerriteCore() {
		return "useSmallThreadingDetector=true";
	}

	public String tituloModernFixRecursosDinamicos() {
		return "Увімкнути динамічні ресурси ModernFix";
	}

	public String descripcionModernFixRecursosDinamicos() {
		return "Може знизити споживання пам'яті та навантаження завдяки ефективнішому завантаженню ресурсів.";
	}

	public String tituloModernFixRenderizadoresDinamicosEntidades() {
		return "Увімкнути динамічні рендерери сутностей";
	}

	public String descripcionModernFixRenderizadoresDinamicosEntidades() {
		return "Може покращити продуктивність за рахунок ефективнішого управління рендерерами сутностей.";
	}

	public String tituloModernFixRenderizadoItemsRapido() {
		return "Увімкнути швидкий рендер предметів";
	}

	public String descripcionModernFixRenderizadoItemsRapido() {
		return "Може покращити продуктивність під час рендеру предметів.";
	}

	public String tituloModernFixWorldgenAllocation() {
		return "Зменшити виділення пам'яті у worldgen";
	}

	public String descripcionModernFixWorldgenAllocation() {
		return "Може зменшити збір сміття в пам'яті під час генерації світу.";
	}

	public String tituloModernFixDeduplicacionIngredientes() {
		return "Увімкнути дедуплікацію інгредієнтів";
	}

	public String descripcionModernFixDeduplicacionIngredientes() {
		return "Зменшує кількість дублікатів об'єктів, пов'язаних з рецептами та інгредієнтами.";
	}

	public String tituloSodiumRenderCielo() {
		return "Увімкнути оптимізацію/рендер неба в Sodium";
	}

	public String descripcionSodiumRenderCielo() {
		return "Може налаштувати поведінку рендеру неба.";
	}

	public String tituloActivarLightmapCaching() {
		return "Увімкнути кешування lightmap";
	}

	public String descripcionActivarLightmapCaching() {
		return "Запобігає перерахунку освітлення, коли це не потрібно.";
	}

	public String sugerenciaActivarLightmapCaching() {
		return "enable_lightmap_caching: true";
	}

	public String tituloOcultarTextoF3BadOptimizations() {
		return "Приховати текст F3 у BadOptimizations";
	}

	public String descripcionOcultarTextoF3BadOptimizations() {
		return "Зменшує налагоджувальний шум на екрані F3.";
	}

	public String sugerenciaOcultarTextoF3BadOptimizations() {
		return "show_f3_text: false";
	}

	public String tituloDesactivarLogConfigBadOptimizations() {
		return "Вимкнути лог конфігурації";
	}

	public String descripcionDesactivarLogConfigBadOptimizations() {
		return "Запобігає виведенню всієї конфігурації під час запуску.";
	}

	public String sugerenciaDesactivarLogConfigBadOptimizations() {
		return "log_config: false";
	}

	public String tituloActivarSerializadorGCFreeC2ME() {
		return "Увімкнути серіалізатор без GC у C2ME";
	}

	public String descripcionActivarSerializadorGCFreeC2ME() {
		return "Знижує виділення пам'яті під час завантаження або збереження чанків.";
	}

	public String sugerenciaActivarSerializadorGCFreeC2ME() {
		return "[ioSystem] gcFreeChunkSerializer = true";
	}

	public String tituloDesactivarSyncPlayerTicketsC2ME() {
		return "Вимкнути syncPlayerTickets";
	}

	public String descripcionDesactivarSyncPlayerTicketsC2ME() {
		return "Може покращити продуктивність чанків, але може вплинути на технічні конструкції.";
	}

	public String sugerenciaDesactivarSyncPlayerTicketsC2ME() {
		return "[chunkSystem] syncPlayerTickets = false";
	}

	public String tituloUsarCullingHojasDepthMoreCulling() {
		return "Використовувати відсіювання листя DEPTH";
	}

	public String descripcionUsarCullingHojasDepthMoreCulling() {
		return "Використовує більш агресивний режим відсіювання листя порівняно зі звичайним.";
	}

	public String sugerenciaUsarCullingHojasDepthMoreCulling() {
		return "leavesCullingMode = \"DEPTH\"";
	}

	public String tituloActivarEndGatewayCullingMoreCulling() {
		return "Увімкнути відсіювання End Gateway";
	}

	public String descripcionActivarEndGatewayCullingMoreCulling() {
		return "Запобігає непотрібному рендеру End Gateway.";
	}

	public String sugerenciaActivarEndGatewayCullingMoreCulling() {
		return "endGatewayCulling = true";
	}

	public String tituloActivarActivationRangeServerCore() {
		return "Увімкнути activation range";
	}

	public String descripcionActivarActivationRangeServerCore() {
		return "Знижує тіки сутностей далеко від гравця.";
	}

	public String sugerenciaActivarActivationRangeServerCore() {
		return "activation-range: enabled: true";
	}

	public String tituloActivarRangoVerticalServerCore() {
		return "Увімкнути вертикальний діапазон";
	}

	public String descripcionActivarRangoVerticalServerCore() {
		return "Знижує тіки сутностей, що знаходяться значно вище або нижче гравця.";
	}

	public String sugerenciaActivarRangoVerticalServerCore() {
		return "use-vertical-range: true";
	}

	public String impactoNegativoAlto() {
		return "Високий негативний вплив";
	}

	public String advertenciaModsCulling() {
		return "Модифікації на culling можуть викликати несумісність із деякими модами, краші, помилки, через які гра перестає коректно тікати, а також можуть зламати автоматичні ферми чи фабрики.";
	}

	public String tituloModBadOptimizations() {
		return "Додати BadOptimizations";
	}

	public String descripcionModBadOptimizations() {
		return "Додає мікрооптимізації для клієнта, такі як кеш lightmap, кеш неба та зменшення непотрібних викликів.";
	}

	public String tituloModBBE() {
		return "Додати Better Block Entities";
	}

	public String descripcionModBBE() {
		return "Оптимізує рендер block entities: скринь, shulkers, ліжок, дзвонів, прапорів та табличок.";
	}

	public String tituloModC2ME() {
		return "Додати Concurrent Chunk Management Engine";
	}

	public String descripcionModC2ME() {
		return "Покращує завантаження, генерацію та управління чанками за допомогою паралельної обробки. Може бути дуже потужним, але також викликати несумісність у великих збірках.";
	}

	public String tituloModEntityCulling() {
		return "Додати EntityCulling";
	}

	public String descripcionModEntityCulling() {
		return "Запобігає рендеру сутностей, які не видно. " + advertenciaModsCulling();
	}

	public String tituloModFerriteCore() {
		return "Додати FerriteCore";
	}

	public String descripcionModFerriteCore() {
		return "Знижує споживання пам'яті завдяки дедуплікації та ефективнішим внутрішнім структурам.";
	}

	public String tituloModImmediatelyFast() {
		return "Додати ImmediatelyFast";
	}

	public String descripcionModImmediatelyFast() {
		return "Оптимізує різні аспекти рендеру в immediate mode, тексту, буферів, мап та інтерфейсу.";
	}

	public String tituloModLithium() {
		return "Додати Lithium";
	}

	public String descripcionModLithium() {
		return "Оптимізує ігрову логіку, сутностей, блоки, фізику та інші системи, не сильно змінюючи ванільну поведінку.";
	}

	public String tituloModModernFix() {
		return "Додати ModernFix";
	}

	public String descripcionModModernFix() {
		return "Додає багато оптимізацій пам'яті, завантаження, ресурсів та загальної продуктивності. Його інструменти, пов'язані з atlas, можуть конфліктувати з модами, що зменшують розмір atlas.";
	}

	public String tituloModMoreCulling() {
		return "Додати More Culling";
	}

	public String descripcionModMoreCulling() {
		return "Додає culling для блоків, листя, рамок для предметів, картин, дощу, маяків та інших елементів. "
				+ advertenciaModsCulling();
	}

	public String tituloModScalableLux() {
		return "Додати ScalableLux";
	}

	public String descripcionModScalableLux() {
		return "Оптимізує обчислення, пов'язані з освітленням, і може покращити продуктивність у світах із частими змінами світла.";
	}

	public String tituloModServerCore() {
		return "Додати ServerCore";
	}

	public String descripcionModServerCore() {
		return "Додає оптимізації на стороні сервера: activation range, контроль лімітів мобів, зниження тіків та покращення завантаження.";
	}

	public String tituloModSodium() {
		return "Додати Sodium";
	}

	public String descripcionModSodium() {
		return "Основний мод для оптимізації рендеру. Зазвичай це одне з найважливіших покращень для FPS.";
	}

	public String tituloModVMP() {
		return "Додати Very Many Players";
	}

	public String descripcionModVMP() {
		return "Оптимізує серверні системи для обробки великої кількості гравців. Очікуваний ID моду: vmp.";
	}

	public String tituloModMCMT() {
		return "Додати MCMT";
	}

	public String descripcionModMCMT() {
		return "Намагається багатопотоково обробити частини сервера Minecraft. Може покращити продуктивність у деяких випадках, але несе високий ризик несумісності, помилок тіків та дивної поведінки.";
	}

	public String tituloLiabilityUranium() {
		return "Видалити Uranium";
	}

	public String descripcionLiabilityUranium() {
		return "Uranium — це мод, спеціально розроблений для створення лагів у грі. Не повинен бути встановлений, якщо важлива продуктивність.";
	}

	public String tituloAmbientalSinXmx() {
		return "Налаштувати максимальну пам'ять Java";
	}

	public String descripcionAmbientalSinXmx(int mods, String minimo, String maximoSeguro) {
		return "-Xmx не виявлено в наданих аргументах. Для " + mods + " модів рекомендований мінімум становить "
				+ minimo + ", не перевищуючи приблизно " + maximoSeguro + ".";
	}

	public String sugerenciaAmbientalSinXmx(String minimo) {
		return "Додати -Xmx" + minimo.replace(" ", "");
	}

	public String tituloAmbientalDemasiadaMemoria() {
		return "Зменшити виділену пам'ять";
	}

	public String descripcionAmbientalDemasiadaMemoria(String xmx, String total, String maximoSeguro) {
		return "Інстансу виділено " + xmx + " з " + total
				+ ". Не рекомендується виділяти більше ніж 80% доступної оперативної пам'яті.";
	}

	public String sugerenciaAmbientalDemasiadaMemoria(String maximoSeguro) {
		return "Зменшити -Xmx до " + maximoSeguro + " або менше.";
	}

	public String tituloAmbientalMemoriaInsuficiente() {
		return "Збільшити виділену пам'ять";
	}

	public String descripcionAmbientalMemoriaInsuficiente(int mods, String xmx, String minimo) {
		return "Інстансу виділено " + xmx + ". Для " + mods + " модів рекомендований мінімум становить " + minimo + ".";
	}

	public String sugerenciaAmbientalMemoriaInsuficiente(String minimo) {
		return "Збільшити -Xmx принаймні до " + minimo + ".";
	}

	public String tituloAmbientalJava8GC() {
		return "Використовувати G1GC або Shenandoah у Java 8";
	}

	public String descripcionAmbientalJava8GC() {
		return "У Java 8 рекомендується використовувати G1GC або Shenandoah для зменшення пауз і підвищення стабільності.";
	}

	public String sugerenciaAmbientalJava8GC() {
		return "Додати -XX:+UseG1GC або -XX:+UseShenandoahGC.";
	}

	public String tituloAmbientalZGC() {
		return "Використовувати ZGC";
	}

	public String descripcionAmbientalZGC(String ramTotal) {
		return "Комп'ютер має більше ніж 12 ГБ ОЗП (" + ramTotal
				+ "). Якщо дистрибутив Java підтримує, ZGC може зменшити паузи збирача сміття.";
	}

	public String sugerenciaAmbientalZGC() {
		return "У Java 17 або вище спробуйте -XX:+UseZGC.";
	}

	public String tituloAmbientalAikar() {
		return "Додати прапорці Aikar";
	}

	public String descripcionAmbientalAikar() {
		return "У Java 17 або нижче прапорці Aikar зазвичай покращують поведінку G1GC для Minecraft.";
	}

	public String sugerenciaAmbientalAikar() {
		return "Використовувати прапорці Aikar, включаючи -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200.";
	}

	public String tituloAmbientalRedHatJDK() {
		return "Використовувати Red Hat JDK";
	}

	public String descripcionAmbientalRedHatJDK(int javaMayor, String os) {
		return "Для Java " + javaMayor + " у " + os + " рекомендується Red Hat JDK через стабільність і сумісність.";
	}

	public String sugerenciaAmbientalRedHatJDK() {
		return "Встановити Red Hat JDK для Java 8 або Java 11.";
	}

	public String tituloAmbientalAzulPrime() {
		return "Розглянути Azul Prime";
	}

	public String descripcionAmbientalAzulPrime() {
		return "У Linux з Java 16 або вище та більше ніж 16 ГБ ОЗП Azul Prime може бути хорошим вибором для продуктивності.";
	}

	public String sugerenciaAmbientalAzulPrime() {
		return "Спробувати Azul Prime, якщо комп'ютер має більше ніж 16 ГБ ОЗП.";
	}

	public String tituloAmbientalGraalVM() {
		return "Розглянути GraalVM";
	}

	public String descripcionAmbientalGraalVM() {
		return "З Java 16 або вище та більше ніж 16 ГБ ОЗП GraalVM може бути корисною альтернативою поза Linux.";
	}

	public String sugerenciaAmbientalGraalVM() {
		return "Спробувати GraalVM, якщо комп'ютер має більше ніж 16 ГБ ОЗП.";
	}

	public String tituloAmbientalDiscoBajo() {
		return "Перевірити вільне місце на диску";
	}

	public String descripcionAmbientalDiscoBajo(String libre) {
		return "На диску мало вільного місця: " + libre
				+ ". Minecraft може вилітати, повільно зберігатися або пошкоджувати дані, якщо місце закінчиться.";
	}

	public String sugerenciaAmbientalDiscoBajo() {
		return "Звільнити місце, щоб було доступно щонайменше 20 ГБ.";
	}

	public String tituloAmbientalWindowsRHEL9() {
		return "Розглянути RHEL 9 для тестування";
	}

	public String descripcionAmbientalWindowsRHEL9() {
		return "У Windows рекомендується розглянути RHEL 9, оскільки він включає Red Hat JDK, є стабільним, безкоштовним для приватних осіб і є основною платформою для тестування.";
	}

	public String sugerenciaAmbientalWindowsRHEL9() {
		return "Протестувати інстанс у RHEL 9, якщо потрібна максимальна стабільність тестування.";
	}

	public String tituloAmbientalRaptorLake() {
		return "Попередження Intel Raptor Lake";
	}

	public String descripcionAmbientalRaptorLake() {
		return "Виявлено проблему Raptor Lake, позначену наявною перевіркою. Це може викликати нестабільність, вильоти та помилки, які здаються пов'язаними з модпаком.";
	}

	public String sugerenciaAmbientalRaptorLake() {
		return "Оновіть BIOS/мікрокод і ознайомтеся з попередженням про Raptor Lake, перш ніж звинувачувати модпак.";
	}

	@Override
	public String tituloAmbientalNeoForge1201Antiguo() {
		return "Виявлено стару версію NeoForge 1.20.1";
	}

	@Override
	public String descripcionAmbientalNeoForge1201Antiguo() {
		return "Виявлено FancyModLoader 47 або шлях, сумісний із NeoForge 1.20.1. "
				+ "NeoForge 1.20.1 був форком MinecraftForge 1.20.1 і зазвичай бінарно сумісний, "
				+ "але ця гілка була залишена раніше і може не мати кількох оптимізацій, доступних у Forge.";
	}

	@Override
	public String sugerenciaAmbientalNeoForge1201Antiguo() {
		return "Для версії 1.20.1, якщо модпак дозволяє, розгляньте використання MinecraftForge 1.20.1 замість NeoForge 1.20.1.";
	}

	@Override
	public String tituloAmbientalGPU() {
		return "Виявлено проблему з GPU";
	}

	@Override
	public String descripcionAmbientalGPU() {
		return "Інша перевірка вже виявила можливу проблему з GPU, OpenGL або вибором відеокарти.";
	}

	@Override
	public String sugerenciaAmbientalGPU() {
		return "Переконайтеся, що Minecraft використовує правильну відеокарту, оновіть драйвери та уникайте нестабільних гібридних конфігурацій.";
	}

	@Override
	public String gpuFixTitulo() {
		return "Налаштування GPU";
	}

	@Override
	public String gpuFixBotonSidebar() {
		return "GPU";
	}

	@Override
	public String gpuFixBotonAplicar() {
		return "Застосувати налаштування";
	}

	@Override
	public String gpuFixBotonFuenteTLauncher() {
		return "Відкрити посібник TLauncher";
	}

	@Override
	public String gpuFixBotonVirusTotal() {
		return "Відкрити аналіз VirusTotal";
	}

	@Override
	public String gpuFixBotonOptimusLinux() {
		return "Відкрити посібник NVIDIA Optimus";
	}

	@Override
	public String gpuFixTextoWindows() {
		return Statics.nombre_cd.obtener()
				+ " виявив, що Minecraft, можливо, не використовує високопродуктивний GPU.\n\n"
				+ "У Windows можна встановити ключі реєстру в "
				+ "HKEY_CURRENT_USER\\\\Software\\\\Microsoft\\\\DirectX\\\\UserGpuPreferences "
				+ "щоб примусити javaw.exe використовувати дискретну відеокарту.\n\n"
				+ "GpuPreference=0 = автоматичне рішення Windows.\n"
				+ "GpuPreference=1 = енергозбереження / інтегрована графіка.\n"
				+ "GpuPreference=2 = високопродуктивний GPU.\n\n"
				+ "Частина цієї інформації була отримана завдяки дослідженню, опублікованому TLauncher, та аналізу "
				+ "поведінки, доступному на VirusTotal.";
	}

	@Override
	public String gpuFixTextoLinux() {
		return Statics.nombre_cd.obtener() + " виявив можливу проблему, пов'язану з NVIDIA Optimus або PRIME.\n\n"
				+ "Залежно від використовуваного дистрибутива Linux може знадобитися налаштування NVIDIA Optimus, "
				+ "nvidia-prime, switcheroo-control або інших гібридних систем.\n\n"
				+ "У Fedora/RHEL та похідних зазвичай рекомендується дотримуватися документації RPMFusion.\n\n"
				+ "Нижня кнопка відкриє рекомендовану офіційну документацію.";
	}

	@Override
	public String gpuFixTextoMac() {
		return Statics.nombre_cd.obtener() + " виявив можливу проблему вибору GPU.\n\n"
				+ "У деяких системах macOS з гібридною графікою можна примусово використовувати дискретну відеокарту "
				+ "через розширені налаштування системи.\n\n"
				+ "Кнопка застосування спробує виконати команду для пріоритизації високопродуктивного GPU.";
	}

	@Override
	public String gpuFixTextoOtroSistema() {
		return Statics.nombre_cd.obtener() + " виявив можливу проблему, пов'язану з GPU, "
				+ "але для цієї операційної системи немає конкретної реалізації.";
	}

	@Override
	public String gpuFixLinuxManual() {
		return "У Linux налаштування зазвичай має виконуватися вручну залежно від дистрибутива, "
				+ "драйвера NVIDIA та використовуваної системи Optimus/PRIME.";
	}

	@Override
	public String gpuFixSistemaNoSoportado() {
		return "Операційна система не підтримується для автоматичного налаштування GPU.";
	}

	@Override
	public String gpuFixJavaNoDetectado() {
		return "Не вдалося виявити поточний шлях до javaw.exe.";
	}

	@Override
	public String gpuFixWindowsAplicado(String ruta) {
		return "Налаштування GPU було успішно застосовано для:\n\n" + ruta + "\n\n"
				+ "GpuPreference=2 вказує на високопродуктивний GPU.";
	}

	@Override
	public String gpuFixErrorAplicando() {
		return "Сталася помилка під час спроби застосувати налаштування GPU";
	}

	@Override
	public String gpuFixMacAplicado() {
		return "Налаштування високопродуктивного GPU було застосовано.";
	}

	@Override
	public String gpuFixMacError() {
		return "Не вдалося застосувати налаштування GPU у macOS";
	}

	@Override
	public String rendimientoTitulo() {
		return "Менеджер продуктивності";
	}

	@Override
	public String rendimientoBotonSidebar() {
		return "Продуктивність";
	}

	@Override
	public String rendimientoBotonAnalizar() {
		return "Аналіз продуктивності";
	}

	@Override
	public String rendimientoBotonAbrirGPU() {
		return "Відкрити налаштування GPU";
	}

	@Override
	public String rendimientoDescripcion() {
		return "Ця панель перевіряє проблеми середовища, рекомендовані або ризиковані моди та параметри конфігурації, "
				+ "які можуть покращити продуктивність. Не всі опції працюють разом, не всі підходять для кожної "
				+ "версії Minecraft і не всі сумісні з кожним завантажувачем модів. Це нормально: вам не потрібен "
				+ "ідеальний рейтинг оптимізації.";
	}

	@Override
	public String rendimientoNotaCompatibilidad() {
		return "Примітка: ці пропозиції є можливостями, а не наказом застосовувати все. Деякі опції можуть конфліктувати "
				+ "між собою або не підходити для вашої версії, лаунчера, завантажувача модів або модпака.";
	}

	@Override
	public String rendimientoPestanaResumen() {
		return "Зведення";
	}

	@Override
	public String rendimientoPestanaAmbiental() {
		return "Проблеми середовища";
	}

	@Override
	public String rendimientoPestanaMods() {
		return "Рекомендовані моди та ризики";
	}

	@Override
	public String rendimientoPestanaConfigs() {
		return "Параметри конфігурації";
	}

	@Override
	public String rendimientoResumenTitulo() {
		return "Зведення аналізу";
	}

	@Override
	public String rendimientoResumenAmbiental(int cantidad) {
		return "Знайдено проблем середовища: " + cantidad;
	}

	@Override
	public String rendimientoResumenMods(int cantidad) {
		return "Знайдено пропозицій або ризиків щодо модів: " + cantidad;
	}

	@Override
	public String rendimientoResumenConfigs(int cantidad) {
		return "Знайдено пропозицій щодо конфігурації: " + cantidad;
	}

	@Override
	public String rendimientoResumenGPU() {
		return "Виявлено проблему з GPU. Тому кнопку відкриття налаштувань GPU було ввімкнено.";
	}

	@Override
	public String rendimientoSinHallazgos() {
		return "У цьому розділі пропозицій не знайдено.";
	}

	@Override
	public String rendimientoSugerencia() {
		return "Пропозиція";
	}

	@Override
	public String rendimientoColorFondo() {
		return "Продуктивність - фон";
	}

	@Override
	public String rendimientoColorPanel() {
		return "Продуктивність - панель";
	}

	@Override
	public String rendimientoColorTexto() {
		return "Продуктивність - текст";
	}

	@Override
	public String rendimientoColorTextoSecundario() {
		return "Продуктивність - вторинний текст";
	}

	@Override
	public String rendimientoColorBoton() {
		return "Продуктивність - кнопка";
	}

	@Override
	public String rendimientoColorBotonTexto() {
		return "Продуктивність - текст кнопки";
	}

	@Override
	public String rendimientoColorSeleccion() {
		return "Продуктивність - виділення";
	}

	@Override
	public String mensajeDialogoCompartirPrimitiva() {
		return "Стався краш гри. Якщо не з'явиться спливаюче вікно з рішенням, будь ласка, надішліть логи до центру підтримки.";
	}

	@Override
	public String irAModoNormalEstiloTL() {
		return "Перейти до звичайного режиму";
	}

	@Override
	public String noHayEnlacesParaCopiar() {
		return "Немає посилань для копіювання.";
	}

	@Override
	public String error_inesperado() {
		return "Неочікувана помилка";
	}

	@Override
	public String centroDeSoporte() {
		return "Центр підтримки";
	}

	@Override
	public String noHayCentroSoporteConfigurado() {
		return "Центр підтримки не налаштовано.";
	}

	public String historialMCLogs() {
		return "Історія MCLogs";
	}

	public String endpoint() {
		return "Ендпоінт";
	}

	public String slug() {
		return "Слаг";
	}

	public String tokenEliminacion() {
		return "Токен видалення";
	}

	public String enlace() {
		return "Посилання";
	}

	public String lineas() {
		return "Рядки";
	}

	public String errores() {
		return "Помилки";
	}

	public String eliminarRegistroMCLogs() {
		return "Видалити запис";
	}

	public String faltanDatosParaEliminarMCLogs() {
		return "Відсутній слаг або токен видалення.";
	}

	public String confirmarEliminarMCLogs() {
		return "Ви впевнені, що хочете видалити цей запис MCLogs?";
	}

	public String registroEliminadoMCLogs() {
		return "Запис успішно видалено.";
	}

	public String confirmar() {
		return "Підтвердити";
	}

	public String colorCampoTexto() {
		return "Колір текстового поля";
	}

	public String historialCDPaste() {
		return "Історія CDPaste";
	}

	public String enlaceRaw() {
		return "Сире посилання";
	}

	public String tamano() {
		return "Розмір";
	}

	public String eliminarRegistroCDPaste() {
		return "Видалити запис CDPaste";
	}

	public String faltanDatosParaEliminarCDPaste() {
		return "Відсутній слаг запису CDPaste.";
	}

	public String confirmarEliminarCDPaste() {
		return "Ви впевнені, що хочете видалити цей запис CDPaste?";
	}

	public String registroEliminadoCDPaste() {
		return "Запис CDPaste успішно видалено.";
	}

	public String launcherGenerico() {
		return "Загальний";
	}

	public String launcherServidorMinecraft() {
		return "Сервер Minecraft";
	}

	public String descargandoYPreparandoEnlaces() {
		return "Завантаження та підготовка посилань...";
	}

	public String seleccioneArchivoLog() {
		return "Виберіть файл логу";
	}

	public String archivoNoValido() {
		return "Файл недійсний.";
	}

	public String archivoSeleccionado() {
		return "Вибраний файл:";
	}

	public String presioneGuardarParaAgregarAnalisis() {
		return "Натисніть 'Зберегти та закрити', щоб додати його до аналізу.";
	}

	public String errorAlCargarArchivoArrastrado() {
		return "Помилка при завантаженні перетягнутого файлу";
	}

	public String errorAlAbrirArchivo() {
		return "Помилка при відкритті файлу";
	}

	public String errorDosPuntos() {
		return "Помилка";
	}

	public String eliminarRegistros() {
		return "Видалити записи";
	}

	public String editorConfigsMods() {
		return "Редактор конфігурацій модів";
	}

	public String abrirConfig() {
		return "Відкрити Config";
	}

	public String guardarConfig() {
		return "Зберегти Config";
	}

	public String recargarConfig() {
		return "Перезавантажити";
	}

	public String rutaConfig() {
		return "Шлях";
	}

	public String tipoConfig() {
		return "Тип";
	}

	public String claveConfig() {
		return "Ключ";
	}

	public String valorConfig() {
		return "Значення";
	}

	public String buscarConfig() {
		return "Пошук";
	}

	public String sinArchivoSeleccionado() {
		return "Файл не вибрано";
	}

	public String archivoNoSoportado() {
		return "Файл не підтримується жодним доступним рушієм";
	}

	public String configGuardada() {
		return "Конфігурацію успішно збережено";
	}

	public String errorCargandoConfig() {
		return "Помилка завантаження конфігурації";
	}

	public String errorGuardandoConfig() {
		return "Помилка збереження конфігурації";
	}

	public String seleccionarArchivoConfig() {
		return "Вибрати файл конфігурації";
	}

	public String suprimirConsolaCD() {
		return "Приховати консоль " + Statics.nombre_cd.obtener();
	}

	public String suprimirVerificacionDeStacktrazos() {
		return "Приховати перевірку стектрейсів";
	}

	public String importadorBotonFusionar() {
		return "Об'єднати";
	}

}
