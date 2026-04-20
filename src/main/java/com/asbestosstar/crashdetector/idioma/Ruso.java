package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Ruso implements Idioma {
	private final Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "ru";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "ruso";
	}

	@Override
	public String nombre_del_idioma() {
		return "Русский";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_rusia.png");
	}

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Некорректная папка mods</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError() + "'>JAR-файл CrashDetector не найден</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Поиск PID: " + String.valueOf(pid) + "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") завершен!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>JVM не обнаружена</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Обновление драйверов ATI/AMD может помочь. Прочитайте это руководство для исправления: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>Руководство по обновлению драйверов</a> https://www.amd.com/es/support/download/drivers.html Скачать </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Некоторые старые версии могут конфликтовать с Nouveau-драйверами на ранних этапах загрузки.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Проблема с видеодрайверами. Для AMD/ATI обновите драйверы, для NVIDIA установите дискретную видеокарту для всех javaw.exe. Инструкция: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Руководство по обновлению драйверов</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Ошибка загрузки FML early window. Исправьте в (.minecraft/config/fml.toml) earlyWindowProvider на \"none\". Для Mac M1 используйте ARM-версию Java. Также проверьте драйверы. Для Windows, если не помогает, читайте: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Руководство по обновлению драйверов</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Отсутствуют необходимые зависимости:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "Запрошено").replace("Expected range", "Ожидаемый диапазон")
				+ "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Отчет CrashDetector доступен здесь: <a href='"
				+ archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>Просмотреть отчет</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>Это GUI CrashDetector. Если игра закрылась нормально, игнорируйте это окно.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>Просмотреть отчет</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Просмотреть локальный отчет в браузере.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "Поделиться отчетом";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "Отправить отчет. Логи загрузятся на securelogger.net, отчет на другие сайты.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Обнаружены проблемные JAR-файлы (приоритет: FATAL > HIGH > LOW):</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> Уровень:</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Потенциально фатально:</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Обнаружены проблемные ModID (приоритет: FATAL > LOW > LOW):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Обнаружены проблемные пакеты (приоритет: FATAL > LOW > LOW):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Обнаружены фатальные классы (FATAL), это очень серьёзно. Частые причины — плохие CoreMods или фатальные зависимости. Используйте QuickFix для поиска модов с фатальными классами. Обнаруженные отсутствующие фатальные классы:</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Содержимое {} (важнейшие вверху, показано первые 20):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Проблемная конфигурация SpongeMixin: </b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>У вас есть моды с дублирующимися пакетами. Это можно исправить, удалив дублирующийся пакет (папку) из JAR-файла. Вы можете открыть JAR в архиваторе, таком как WinRAR или 7z, также можно изменить расширение файла с .jar на .zip, удалить папку, а затем снова переименовать его обратно в .jar файл.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены дублирующиеся моды</b> "
				+ linea.replace("from mod files", "из файлов мода");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge обнаружил подозрительный мод:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV требует Lithostitched, установите: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Для Iris/Oculus нужен SODIUM или совместимый загрузчик (Rubidium, Embedium, Bedium)</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Проблема с расширением KubeJS </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружены проблемы с драйверами NVIDIA в версиях до Windows 11." + "</span><br/><br/>"
				+ "Чтобы убедиться, что Minecraft (и текущая JVM) использует выделенную видеокарту NVIDIA, следуйте этим шагам:<br/><br/>"
				+ "1. <strong>Определите исполняемый файл Java:</strong><br/>"
				+ "   - Эта программа использует следующий исполняемый файл Java: " + obtenerRutaJava() + "<br/>"
				+ "   - Если вы не видите конкретного пути, вы можете найти исполняемый файл Java, выполнив поиск по запросу 'java.exe' в системе.<br/><br/>"
				+ "2. <strong>Откройте панель управления NVIDIA:</strong><br/>"
				+ "   - Щелкните правой кнопкой мыши на рабочем столе и выберите 'Панель управления NVIDIA'.<br/><br/>"
				+ "3. <strong>Настройте предпочтительную GPU:</strong><br/>"
				+ "   - В панели управления NVIDIA перейдите к 'Управление параметрами 3D'.<br/>"
				+ "   - Выберите опцию 'Настройки программы'.<br/>"
				+ "   - Нажмите 'Добавить' и найдите ранее определенный исполняемый файл Java (например, 'java.exe').<br/>"
				+ "   - Убедитесь, что он настроен на использование 'Процессора высокой производительности (NVIDIA)'.<br/><br/>"
				+ "4. <strong>Сохраните изменения:</strong><br/>"
				+ "   - Примените изменения и закройте панель управления NVIDIA.<br/><br/>"
				+ "5. <strong>Перезапустите Minecraft:</strong><br/>"
				+ "   - Перезапустите Minecraft, чтобы изменения вступили в силу.<br/><br/>"
				+ "Если вы используете Windows Server 2022 или Windows 10, эти шаги действительны при условии, что установлены последние драйверы NVIDIA.<br/><br/>"
				+ "Примечание: Если вы не можете найти панель управления NVIDIA, убедитесь, что драйверы NVIDIA правильно установлены.";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружены проблемы с драйверами NVIDIA в Windows 11/Server 2025 или более поздних версиях."
				+ "</span><br/><br/>"
				+ "Чтобы убедиться, что Minecraft (и текущая JVM) использует выделенную видеокарту NVIDIA, следуйте этим шагам:<br/><br/>"
				+ "1. <strong>Определите исполняемый файл Java:</strong><br/>"
				+ "   - Эта программа использует следующий исполняемый файл Java: " + obtenerRutaJava() + "<br/>"
				+ "   - Если вы не видите конкретного пути, вы можете найти исполняемый файл Java, выполнив поиск по запросу 'java.exe' в системе.<br/><br/>"
				+ "2. <strong>Откройте приложение Параметры:</strong><br/>"
				+ "   - Нажмите клавиши <code>Win + I</code>, чтобы открыть приложение Параметры.<br/>"
				+ "   - Перейдите к <strong>Система > Дисплей > Графика</strong>.<br/><br/>"
				+ "3. <strong>Настройте предпочтительную GPU:</strong><br/>"
				+ "   - В разделе 'Графика' нажмите 'Параметры графики по умолчанию'.<br/>"
				+ "   - Выберите 'Приложения рабочего стола', затем нажмите 'Обзор'.<br/>"
				+ "   - Найдите и выберите ранее определенный исполняемый файл Java (например, 'java.exe').<br/>"
				+ "   - После добавления выберите приложение Java в списке и настройте его для использования 'Высокой производительности (NVIDIA)'.<br/><br/>"
				+ "4. <strong>Сохраните изменения:</strong><br/>"
				+ "   - Примените изменения и закройте приложение Параметры.<br/><br/>"
				+ "5. <strong>Перезапустите Minecraft:</strong><br/>"
				+ "   - Перезапустите Minecraft, чтобы изменения вступили в силу.<br/><br/>"
				+ "Если вы используете Windows 11 или Windows Server 2025+, эти шаги действительны при условии, что установлены последние драйверы NVIDIA.<br/><br/>"
				+ "Примечание: Если вы не можете найти параметры графики, убедитесь, что драйверы NVIDIA правильно установлены.";
	}

	@Override
	public String segundo60Tick() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Ваш сервер или мир имеет тики дольше 60 секунд. Это может быть связано с модами, замедляющими сервер, или слишком слабым оборудованием.</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>У вас недостаточно RAM/памяти. Нужно выделить больше.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>У Theseus есть и другие проблемы, включая сбои при удалении модов. Если вам нужно запускать файлы mrpack, вы можете использовать альтернативные лаунчеры, такие как Prism Launcher (только для modrinth.com), ATLauncher (только для modrinth.com) или Hello Minecraft Launcher (поддерживает modrinth.com и bbsmc.net).</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Вы используете опцию «Пропустить запуск лаунчера» (приложение CurseForge). Иногда это вызывает трудноуловимые проблемы. Это связано с опцией «Пропустить запуск лаунчера» в старых или новых версиях приложения CurseForge. Отключите её и выберите «Mojang Launcher» в настройках CurseForge. Вы можете посмотреть это видео на английском от Claws of Berk (перейдите к 1:11) "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>здесь</a>.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Предупреждение: Обнаружены отсутствующие классы (уровень предупреждения). Обычно не критично, но может вызывать проблемы — отличается от фатальных ошибок. Частые причины: плохие CoreMods или отсутствующие зависимости. Используйте QuickFix для поиска модов с отсутствующими классами. Обнаруженные отсутствующие классы:</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Нет результатов</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Расположение логов:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Вот результаты ваших проверок. Действуйте медленно: обычно правильная причина находится в проверке 1 или 2. Остальные (ошибки 3 и выше) можно использовать для подтверждения, но это, как правило, каскадные ошибки, которые можно игнорировать. Сбои происходят слоями, поэтому устранение корневой проблемы решит именно эту конкретную ошибку. Однако завтра может появиться новая ошибка, не связанная с текущей, так как одна ошибка часто мешает появлению другой в консоли.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Пожалуйста, используйте Java 17 для версий 1.17-1.20.4 и Java 21 для более новых версий. Для старых версий используйте Java 8. [Руководство](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Если у вас все еще есть проблемы, это может быть связано с тем, что некоторые моды имеют слишком новые или старые файлы.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 и выше не работают в версиях Minecraft ниже 1.20.5 для большинства загрузчиков модов из-за устаревшего ASM.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java устарела </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Вам нужен JPMS модуль " + mod_necesitas + " из "
				+ submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Невозможно вызвать " + metodo + ", так как "
				+ objeto + " равен null</b>";
	}

	@Override
	public String analisisAvanzado() {
		return "Расширенный анализ";
	}

	@Override
	public String seleccionarCarpeta() {
		return "Выбрать папку";
	}

	@Override
	public String cadenaBusqueda() {
		return "Строка поиска";
	}

	@Override
	public String usarRegex() {
		return "Использовать регулярные выражения";
	}

	@Override
	public String ignorarMayusculas() {
		return "Игнорировать регистр";
	}

	@Override
	public String buscar() {
		return "Поиск";
	}

	@Override
	public String busquedaEnProgreso() {
		return "Поиск в процессе";
	}

	@Override
	public String noSeEncontraronResultados() {
		return "Результаты не найдены";
	}

	@Override
	public String errorBusqueda() {
		return "Ошибка поиска";
	}

	@Override
	public String omitirYCerrar() {
		return "Пропустить и Закрыть";
	}

	@Override
	public String guardarYCerrar() {
		return "Сохранить и Закрыть";
	}

	@Override
	public String pegaLosRegistrosAqui() {
		return "Вставьте логи сюда";
	}

	@Override
	public String archivo() {
		return "Файл";
	}

	@Override
	public String incluir() {
		return "Включить";
	}

	@Override
	public String abrir() {
		return "Открыть";
	}

	@Override
	public String endpointDeInforme() {
		return "Конечная точка отчета";
	}

	@Override
	public String sitoDeLogging() {
		return "Сайт логирования:";
	}

	@Override
	public String apiDeLogging() {
		return "API логирования:";
	}

	@Override
	public String anonimizarRegistros() {
		return "Анонимизация логов (Бета)";
	}

	@Override
	public String botonDeCompartirInforme() {
		return "Поделиться отчетом и всеми выбранными логами";
	}

	@Override
	public String arco() {
		return "Этот диалог позволяет вам делиться логами с помощью API SecureLogger "
				+ "на <a href=\"https://securelogger.net\">securelogger.net</a>. При нажатии кнопки отправки отчёта "
				+ "ваш отчёт отправляется на выбранный эндпоинт (по умолчанию: asbestosstar.egoism.jp) (можно изменить внизу). "
				+ "Вы можете отправить все выбранные логи вместе с отчётом. Если вы не хотите загружать данные, не используйте этот диалог! "
				+ "Мы не обрабатываем ваш отчёт на официальном эндпоинте (<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\">https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb</a>); "
				+ "мы лишь удаляем запрещённые ссылки. Исходный код доступен здесь: <a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">исходный код</a>. "
				+ "Это используется исключительно для отображения информации о вашем сбое и ссылки на логи. Однако вы можете использовать кастомный эндпоинт, который может работать иначе. "
				+ "Вы используете сайт отчётов " + Config.obtenerInstancia().obtenerSitoDeInformes() + " и сайт логов "
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". "
				+ "Вы также можете делиться отдельными логами без отчёта, нажав кнопки «Поделиться» рядом с именами отдельных логов; "
				+ "логи будут отправлены на выбранный сайт логов. CrashDetector по умолчанию анонимизирует логи, пытаясь удалить имена пользователей, UUID, "
				+ "токены доступа, ID сессий, IP-адреса и другие данные. Однако эта функция не идеальна. Кроме того, автор модпака может её отключить. "
				+ "Её можно включить или отключить с помощью флажка внизу этого окна. Вы — контролёр своих данных; именно вы решаете, куда их загружать. "
				+ "Сайты логов принадлежат третьим лицам, чья собственность часто скрыта из соображений приватности. Вы несёте полную ответственность за управление своими данными и связанные с этим риски. "
				+ "Диалог отправки CrashDetector — это просто интерфейс, позволяющий вам управлять этим процессом. "
				+ "Важно, чтобы вы знали о GDPR и ARCO. "
				+ "Если вы находитесь в Европе, вы можете использовать <a href=\"https://securelogger.top\">securelogger.top</a>, размещённый в Германии компанией Hetzner. "
				+ "Для дополнительной юридической информации см. следующие ссылки: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>, "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">GDPR</a>, "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">Базовая политика защиты персональных данных в Японии</a>.";
	}

	@Override
	public String enlaceDelReporte() {
		return "Ссылка на отчет:";
	}

	@Override
	public String guardarConfigDeCompartir() {
		return "Сохранить настройки обмена";
	}

	@Override
	public String registroDemasiadoGrande() {
		return "Лог слишком большой для этого сайта логов. Выберите другой и повторите попытку.";
	}

	@Override
	public String errorConPublicarRegistro(String error) {
		return "Ошибка при публикации лога " + error;
	}

	@Override
	public String apiDeRegistroNoExiste() {
		return "API логов не существует. Пожалуйста, измените API логов в настройках.";
	}

	@Override
	public String errorSSL() {
		return "У вас ошибка SSL. Это часто встречается в старых версиях Java, "
				+ "включая версии Java 8 в стандартном лаунчере Minecraft и версии с sun.com и java.com. "
				+ "Это влияет на многие аспекты, такие как JAR-файлы установщика MinecraftForge, "
				+ "функция для отправки отчетов CrashDetector при использовании стандартного конечного пункта, "
				+ "некоторые моды, требующие интернета, и некоторые сайты для записи логов. "
				+ "Если это произошло при попытке поделиться отчетом, просто прикрепите скриншот "
				+ "и выберите сайт для записи логов, совместимый со старыми версиями Java 8.";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Несовместимая версия JavaFML: требуется "
				+ requerido + ", но найдена " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Внимание! JavaFML требует определённой версии Minecraft Forge</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Файл JAR '" + archivoJar
				+ "' требует языкового провайдера '" + proveedor + "' версии '" + requerido
				+ "' или выше, но найдена только версия '" + encontrado + "'.</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ВНИМАНИЕ! Crash Assistant — это поддельный детектор вредоносного ПО. Он намеренно блокирует запуск игры, игнорируя вашу свободу продолжать играть с целевыми модификациями. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Посмотреть код MalwareMod.java</a>   "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Посмотреть код JarInJarHelper.java</a>. "
				+ "На данный момент в их списке только этот мод, и они фактически нацелены только на сайт записи по умолчанию, который может быть изменён пользователем, и это происходит только если вы явно выбираете использовать встроенную функцию обмена логами. CrashAssistant НЕ проводит никаких проверок, чтобы определить, какой сайт записи используется, и не объясняет, как его изменить (есть выпадающее меню внизу диалогового окна обмена), и независимо от настроенного сайта, CrashAssistant заблокирует запуск игры. В их сообщении говорится, чтобы вы провели своё собственное исследование, СДЕЛАЙТЕ ЭТО, изучите код CrashDetector и Crash Assistant и поймите, что они делают, НЕ полагайтесь на обращение к авторитету.</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Мод '" + idMod
				+ "' не смог загрузиться, так как требуемый класс не был найден: '" + claseNoEncontrada
				+ "'. Убедитесь, что все зависимости установлены правильно.</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Watermedia заблокировал игру с помощью TLauncher.</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Вы используете версию Optifine для устаревшей версии Minecraft. Вам нужно использовать версию Optifine, соответствующую версии Minecraft, которую вы используете.</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Не удалось загрузить службу ModLauncher: </b>"
				+ servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Ошибка при разборе JSON-файла '" + recurso
				+ "' из JAR-файла '" + archivoJar + "'. Проблемы с регистрацией.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Ошибка: Мод '" + modId
				+ "' требует версию '" + requerido + "' или выше для '" + dependencia + "', но найдена версия '"
				+ actual + "'." + "</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Ваша видеокарта не поддерживает требуемую версию OpenGL для этой версии игры. Обновите драйверы или замените видеокарту.</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Увеличьте объем памяти, выделенной для игры, или уменьшите использование модов/плагинов.</b>";
	}

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Обнаружена 32-битная JVM: невозможно использовать более 4 ГБ оперативной памяти. "
				+ "Установите 64-битную JVM, чтобы использовать всю доступную память.</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Критическая ошибка PermGen памяти. Увеличьте объем постоянной памяти или уменьшите загрузку классов.</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Ошибка совместимости между Java 8 и современными версиями</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+ не поддерживается - Используйте Java 8 для старых версий Forge</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Требуется Java 8 (версия 52.0). Обновите или настройте правильно</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Критическая ошибка совместимости: Блоки не поддерживаются в этой версии. "
				+ "Убедитесь, что версии модов и сервера совместимы</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Ошибка настройки мониторов: "
				+ "Не удалось установить режим экрана. " + "Проверьте:</b>" + "<br>- Настройку нескольких мониторов"
				+ "<br>- Обновленные драйверы видеокарты" + "<br>- Разрешение, поддерживаемое системой";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Ошибка в параметрах Java: "
				+ "Конфликтующие параметры сборщика мусора. "
				+ "Убедитесь, что вы не комбинируете несколько алгоритмов GC в параметрах JVM</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Критическая ошибка конфигурации NightConfig/Forge: " + "Файл конфигурации повреждён или неполон. "
				+ "Это может быть вызвано пустыми файлами конфигурации (часто размером 0 байт) в папке 'config' в старых или кастомных версиях NightConfig. "
				+ "В большинстве случаев проблема решается модом Night Config Fixes, но если вы используете несовместимую или кастомную версию NightConfig, вам нужно будет удалить файлы конфигурации вручную. "
				+ "Эта проблема чаще встречается в старых версиях MC Forge (в которых уже есть NightConfig) и в старых модах FabricMC, включающих NightConfig, но также может возникать в некоторых кастомных сборках NightConfig. "
				+ "Больше информации о решениях доступно на странице <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a>.</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружена ошибка драйвера Intel HD Graphics. Решения:</b>"
				+ "<br>1. Обновите драйверы Intel с <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a> (минимальная версия 15.xx.xx.xx)"
				+ "<br>2. В Minecraft: Параметры → Видео → Включите 'Enable VBOs' и 'VSync'"
				+ "<br>3. Выделите 1GB-2GB RAM для игры в лаунчере"
				+ "<br>4. Временно отключите антивирус/брандмауэр во время обновления";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "Предупреждение: Обнаружены отсутствующие классы";
	}

	public String nombre_de_bloque_teselado() {
		return "Ошибка рендеринга блоков";
	}

	public String nombre_de_contenido_de_stacktrace() {
		return "Анализ стека вызовов (stack trace)";
	}

	public String nombre_de_cursed_consola() {
		return "Неполная консоль CurseForge";
	}

	public String nombre_de_early_window() {
		return "Ошибка раннего окна (FMLEarlyWindow)";
	}

	public String nombre_de_drivers() {
		return "Проблемы с видеодрайверами";
	}

	public String nombre_de_error_de_config_mcforge() {
		return "Поврежденная конфигурация MCForge";
	}

	public String nombre_de_error_de_monitor_lwjgl() {
		return "Сбой режима отображения (LWJGL)";
	}

	public String nombre_de_fabricmc_runtime_error_provided_by() {
		return "Ошибка инициализации FabricMC";
	}

	public String nombre_de_falta_module_jmps() {
		return "Отсутствующие модули JPMS";
	}

	public String nombre_de_faltar_de_clases() {
		return "Критически отсутствующие классы";
	}

	public String nombre_de_faltas_dependencias_de_modlauncher() {
		return "Отсутствующие зависимости ModLauncher";
	}

	public String nombre_de_java_versiones() {
		return "Несовместимые версии Java";
	}

	public String nombre_de_faltar_de_kubejs_resourcepack() {
		return "Ошибка ресурсов KubeJS";
	}

	public String nombre_de_lenguaje_proveedor_check() {
		return "Несовместимый языковой провайдер";
	}

	public String nombre_de_faltar_de_liyhostictchctov() {
		return "Ошибка Litchhost";
	}

	public String nombre_de_malware_falso_crash_assistant() {
		return "Ложное обнаружение вредоносного ПО";
	}

	public String nombre_de_mcforge_mod_sespechoso() {
		return "Обнаружен подозрительный мод";
	}

	public String nombre_de_mods_duplicados_modlauncher() {
		return "Дублированные моды в ModLauncher";
	}

	public String nombre_de_modules_duplicados_jmps() {
		return "Конфликты модулей JPMS";
	}

	public String nombre_de_necesitas_sodium() {
		return "Для Iris требуется Sodium";
	}

	public String nombre_de_no_puede_analizar_json_de_registro() {
		return "Не удалось проанализировать JSON реестра";
	}

	public String nombre_de_no_tiene_memoria() {
		return "Недостаточно памяти";
	}

	public String nombre_de_null_pointer() {
		return "Ошибка пустого указателя (NullPointerException)";
	}

	public String nombre_de_opciones_java_gc_invalidas() {
		return "Недопустимые параметры сборки мусора Java (GC)";
	}

	public String nombre_de_optifine_obsoleta() {
		return "Устаревшая/несовместимая OptiFine";
	}

	public String nombre_de_60_segundo_trick() {
		return "Критический тик сервера (60 секунд)";
	}

	public String nombre_de_servicio_de_modlauncher_nao_funciona() {
		return "Сбой сервиса ModLauncher";
	}

	public String nombre_de_spongemixin_configs_problematicos() {
		return "Проблемные конфигурации SpongeMixing";
	}

	public String nombre_de_theseus() {
		return "Theseus несовместим";
	}

	public String nombre_de_watermedia_tl() {
		return "TLauncher не поддерживается WATERMeDIA";
	}

	@Override
	public String nombre_de_servicio_de_modlauncher_no_funciona() {
		return "Сервис ModLauncher не работает";
	}

	@Override
	public String auditorias_transformer() {
		return "Аудиты Transformer";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Это результаты содержимого аудитов Transformer в Vanilla-запускалке. Обычно это менее точно, чем анализатор StackTrace, но в Vanilla-запускалке не всегда есть содержимое для {}</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "Это просматривает ваши моды в поисках модов, созданных с помощью MCreator. Хотя большинство модов MCreator вполне работоспособны и есть много отличных модов, созданных с помощью MCreator, иногда у них возникают проблемы, и они имеют плохую репутацию. Это помогает их идентифицировать. Обратите внимание, что даже высоко оцененные моды могут на самом деле не быть созданы MCreator; например, у них может быть интеграция с MCreator.";
	}

	@Override
	public String escanear() {
		return "Сканировать";
	}

	@Override
	public String cargando() {
		return "Загрузка";
	}

	@Override
	public String inicioApp() {
		return "Запуск игры/приложения";
	}

	@Override
	public String ajustesCrashDetector() {
		return "Настройки детектора сбоев";
	}

	@Override
	public String confidencialidad() {
		return "Конфиденциальность";
	}

	@Override
	public String tooltip() {
		return "Подсказка";
	}

	@Override
	public String config() {
		return "Настройка";
	}

	@Override
	public String abrirCarpeta() {
		return "Открыть папку";
	}

	@Override
	public String actualizar() {
		return "Обновить";
	}

	@Override
	public String anadirRegistro() {
		return "Добавить лог";
	}

	@Override
	public String usarIdiomaDelSistema() {
		return "Использовать системный язык";
	}

	@Override
	public String volver() {
		return "Назад";
	}

	@Override
	public String colorFondo() {
		return "Цвет фона (#RRGGBB):";
	}

	@Override
	public String colorTexto() {
		return "Цвет текста (#RRGGBB):";
	}

	@Override
	public String colorBoton() {
		return "Цвет кнопки (#RRGGBB):";
	}

	@Override
	public String colorCajaTexto() {
		return "Цвет текстового поля (#RRGGBB):";
	}

	@Override
	public String colorEnlace() {
		return "Цвет ссылки (#RRGGBB):";
	}

	@Override
	public String colorTitulosConsolas() {
		return "Цвет заголовков консолей (#RRGGBB):";
	}

	@Override
	public String colorError() {
		return "Цвет ошибки (#RRGGBB):";
	}

	@Override
	public String colorAdvertencia() {
		return "Цвет предупреждения (#RRGGBB):";
	}

	@Override
	public String colorInfo() {
		return "Цвет информации (#RRGGBB):";
	}

	@Override
	public String colorTitulo() {
		return "Цвет заголовка (#RRGGBB):";
	}

	@Override
	public String colorEnlaceTexto() {
		return "Цвет текста ссылки (#RRGGBB):";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "Открывать CrashDetector только при сбое";
	}

	@Override
	public String activar_parche() {
		return "Активировать патч";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "Нет доступных решений";
	}

	@Override
	public String error() {
		return "ошибка";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "Ошибка при удалении Jar";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "Jar успешно удален";
	}

	@Override
	public String exito() {
		return "успех";
	}

	@Override
	public String eliminar() {
		return "удалить";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "Ошибка при удалении пакета";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "Пакет успешно удален";
	}

	@Override
	public String eliminar_paquete() {
		return "Удалить пакет";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "Моды с пакетом не найдены";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "Невозможно удалить пакет";
	}

	@Override
	public String eliminar_jar() {
		return "Удалить Jar";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "Дублирующиеся моды не найдены";
	}

	@Override
	public String archivo_no_encontrado() {
		return "Файл не найден";
	}

	@Override
	public String directorio_eliminado() {
		return "Каталог удален";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "Ошибка при удалении вложенного Jar";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "Внутренний файл не найден";
	}

	@Override
	public String archivo_eliminado() {
		return "файл удален";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "ошибка при удалении файла";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "недопустимый внешний файл";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "Элемент мода удален";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "Ошибка при замене внешнего Jar";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "ошибка при удалении элемента мода";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "ошибка при удалении каталога";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "неверный формат для вложенного Jar";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "вложенный Jar удален";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "ошибка при очистке временных файлов";
	}

	@Override
	public String mensaje_de_trace_fatal_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Фатальное сообщение трассировки последнее (Не переведено):</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Сообщение трассировки последнее (Не переведено):</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "Вы можете выполнить поиск в базе данных WaifuNeoForge, чтобы найти моды. Выберите версию игры, загрузчик модов и класс. Используйте наиболее подходящую комбинацию. Поиск можно выполнять один раз в минуту.";
	}

	@Override
	public String solucionFaltasClases() {
		return "Вы можете выполнить поиск в базе данных WaifuNeoForge, чтобы найти моды. Выберите версию игры, загрузчик модов и класс. Используйте наиболее подходящую комбинацию. Поиск можно выполнять один раз в минуту.";
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "Оба лаунчера имеют правильные версии Java, но не все из них; вы можете установить правильную версию Java через менеджер пакетов в вашей системе или с помощью кнопок.";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод без анимации: " + "</b>";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException (Ошибка отсутствия элемента) Отсутствует анимация";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "Не найдено модов для удаления";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "Заменить конфликтующие параметры GC на -XX:+UseG1GC";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "Увеличьте размер кучи с помощью опции -Xmx.";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "Увеличьте размер permgen с помощью опции -XX:MaxPermSize.";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "Используйте 64-битную JVM для увеличения доступной памяти.";
	}

	@Override
	public String optimizarCodigo() {
		return "Оптимизируйте код, чтобы уменьшить использование памяти и повысить производительность.";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "Используйте эффективный сборщик мусора, чтобы уменьшить паузы в работе приложения.";
	}

	@Override
	public String modulos() {
		return "Модули";
	}

	@Override
	public String paquete() {
		return "Пакет";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "Некоторые ID отсутствуют. Обычные причины — отсутствие модов или данных об элементах. Распространённые папки с данными: datafiedcontents/ и kubejs/, а также другие папки модов.";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "неправильно сопоставлённые записи";
	}

	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Плагин 'AuthMe' не загрузился и остановил сервер.</b> ";
	}

	public String nombreProblemaCierreAuthMe() {
		return "Проблема остановки из-за AuthMe";
	}

	public String solucionCierreAuthMe() {
		return "Правило 'stopServer' было изменено на 'true'.";
	}

	public String solucionConfigurarPluginAuthMe() {
		return "Настройте плагин AuthMe (plugins/AuthMe/config.yml)";
	}

	public String solucionInstalarVersionDiferenteAuthMe() {
		return "Установите другую версию плагина 'AuthMe'";
	}

	public String solucionEliminarPluginAuthMe() {
		return "Удалите плагин 'AuthMe'";
	}

	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мир '" + nombreMundo
				+ "' не может быть загружен, так как содержит ошибки и, вероятно, повреждён.</b> ";
	}

	@Override
	public String nombreProblemaCargaMultiverso() {
		return "Ошибка загрузки мира Multiverse";
	}

	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "Восстановите мир '" + nombreMundo + "', например, с помощью Minecraft Region Fixer или MCEdit.";
	}

	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "Удалите папку мира '" + nombreMundo + "'.";
	}

	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Конфигурация расширения 'PermissionsEx' недействительна.</b> ";
	}

	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "Ошибка конфигурации PermissionsEx";
	}

	@Override
	public String solucionConfigurarPermissionsEx() {
		return "Настройте плагин PermissionsEx (plugins/PermissionsEx/permissions.yml)";
	}

	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "Удалите плагин 'PermissionsEx'";
	}

	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Найдено несколько файлов плагинов с именем '"
				+ nombrePlugin + "': '" + primerPath + "' и '" + segundoPath + "'.</b> ";
	}

	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "Проблема с неоднозначным именем плагина";
	}

	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "Удалите плагин '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Ocorreu uma exceção enquanto o mundo carregava os chunks. Se existir para sua plataforma, o FeatureRecycler poderá resolver o problema. https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
	}

	@Override
	public String nombreProblemaCargaChunk() {
		return "Ошибка загрузки чанка";
	}

	@Override
	public String solucionEliminarChunk() {
		return "Удалите проблемный чанк из мира, например с помощью MCEdit или удалив файл региона.";
	}

	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin
				+ "' не может выполнить команду '/" + comando + "'.</b> ";
	}

	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "Ошибка при выполнении команды плагина";
	}

	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "Установите другую версию плагина '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin
				+ "' требует зависимость '" + dependencia + "'.</b> ";
	}

	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагину '" + nombrePlugin
				+ "' не хватает следующих зависимостей: " + deps.toString() + ".</b> ";
	}

	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "Отсутствующая зависимость плагина";
	}

	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "Установите плагин '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin + "' требует версию API '"
				+ versionAPI + "', которая несовместима с текущим сервером.</b> ";
	}

	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "Несовместимая версия API";
	}

	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "Установите версию '" + version + "' программного обеспечения вашего сервера.";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мир '" + nombreMundo
				+ "' является дубликатом другого мира и не может быть загружен.</b> ";
	}

	@Override
	public String nombreProblemaMundoDuplicado() {
		return "Дублированный мир";
	}

	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "Удалите файл 'uid.dat' из мира '" + nombreMundo + "'";
	}

	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "Удалите папку мира '" + nombreMundo + "'";
	}

	/**
	 * Возвращает сообщение об ошибке для проблемных сущностей или блочных
	 * сущностей, подробно описывая шаги восстановления в зависимости от платформы.
	 */
	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		String color = config.obtenerColorError();

		// Основное сообщение: Только описательный текст имеет цвет ошибки
		String mensajeBase = "<span style='color:#" + color + "'>Сущность или блочная сущность '</span>" + nombre
				+ "<span style='color:#" + color + "'>' типа '</span>" + tipo + "<span style='color:#" + color
				+ "'>' в местоположении </span>" + coords + "<span style='color:#" + color
				+ "'> вызывает ошибки ticking.</span><br><br>";

		// Инструкции по исправлению
		String instrucciones = "<span style='color:#" + color + "'>" + "Инструкции по восстановлению:<br>"
				+ "1. **MCForge**: Перейдите в '[nombre_del_mundo]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: Перейдите в 'config/neoforge-server.toml'.<br>"
				+ "   *(Примечание: В локальных играх/Singleplayer миры находятся в папке 'saves')*.<br>"
				+ "3. Измените **removeErroringBlockEntities** и **removeErroringEntities** на **true**.<br><br>"
				+ "Другие варианты:<br>"
				+ "- **MCEdit**: Используйте для ручного удаления сущности по указанным координатам.<br>"
				+ "- **Neruina (Mod)**: Может предотвратить вылет, но не всегда работает и может усложнить отладку при установке."
				+ "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "Проблемная блочная сущность";
	}

	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "Удалите сущность '" + nombre + "' по координатам " + coords + " с помощью MCEdit или напрямую в мире.";
	}

	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod
				+ "' имеет несколько установленных версий.</b> ";
	}

	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "Дублированный мод в Fabric";
	}

	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "Удалите дубликат файла мода: " + rutaMod;
	}

	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Моды '" + primerMod + "' и '" + segundoMod
				+ "' несовместимы друг с другом.</b> ";
	}

	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "Несовместимый мод в Fabric";
	}

	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "Удалите мод '" + nombreMod + "'";
	}

	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod
				+ "' содержит критическую ошибку и не может быть запущен.</b> ";
	}

	@Override
	public String nombreProblemaModFatal() {
		return "Мод с критической ошибкой";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>Следующие моды обязательны, но не установлены: "
				+ deps.toString() + ".</b>";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + "' требует зависимость '"
					+ dependencia + "'.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + "' требует версию '"
					+ dependencia + "' версии " + version + ".</b>";
		}
	}

	@Override
	public String nombreProblemaDependenciaMod() {
		return "Отсутствующая зависимость мода";
	}

	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "Установите мод '" + nombreMod + "'";
	}

	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "Установите мод '" + nombreMod + "' версии " + version;
	}

	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin
				+ "' несовместим с региональным тикингом Folia.</b> ";
	}

	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Следующие плагины несовместимы с региональным тикингом Folia: ");

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
		return "Плагин несовместим с региональным тикингом";
	}

	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "Установите версию без регионального тикинга, например, " + software;
	}

	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod
				+ "' отсутствует в датапаке.</b>";
	}

	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Следующие моды отсутствуют в датапаке: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" и ");
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
		return "Мод отсутствует в датапаке";
	}

	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + "' вызвал ошибку.</b>";
	}

	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Следующие моды вызвали ошибки: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" и ");
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
		return "Мод Forge с исключением";
	}

	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "Установите другую версию мода '" + nombreMod + "'";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod
				+ "' несовместим с версией Minecraft " + versionMC + ".</b>";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Следующие моды несовместимы с соответствующими версиями Minecraft: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (Minecraft ").append(versionesMC.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaModIncompatibleConMinecraft() {
		return "Мод несовместим с Minecraft";
	}

	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "Установите версию Forge, совместимую с Minecraft " + versionMC;
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod
				+ "' отсутствует, но требуется для загрузки мира или плагина.</b>";
	}

	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "Отсутствующий мод";
	}

	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мир был сохранён с использованием мода '"
				+ nombreMod + "', который сейчас отсутствует.</b>";
	}

	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Мир был сохранён с использованием следующих модов, которые сейчас отсутствуют: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" и ");
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
		return "Мод отсутствует в мире";
	}

	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Мир был сохранён с модом '" + nombreMod
				+ "' версии " + versionEsperada + ", а сейчас используется версия " + versionActual + ".</b>";
	}

	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Следующие моды имеют несоответствие версий в сохранённом мире: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" и ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (Сохранено: ").append(versionesEsperadas.get(i)).append(", Текущая: ")
					.append(versionesActuales.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaVersionModMundo() {
		return "Несоответствие версий модов в сохранённом мире";
	}

	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Вы попытались загрузить мир, созданный в более новой версии Minecraft.</b>";
	}

	@Override
	public String nombreProblemaVersionDowngrade() {
		return "Попытка загрузки мира из более новой версии";
	}

	@Override
	public String solucionVersionDowngrade() {
		return "Установите более новую версию Minecraft.";
	}

	@Override
	public String solucionGenerarNuevoMundo() {
		return "Создайте новый мир.";
	}

	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin
				+ "' требует следующую зависимость: '" + dependencia + "'.</b>";
	}

	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Следующие плагины требуют незафиксированные зависимости: ");

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
		return "Плагин с отсутствующей зависимостью";
	}

	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin
				+ "' несовместим с текущей версией сервера.</b>";
	}

	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Следующие плагины несовместимы с текущей версией сервера: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" и ");
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
		return "Плагин несовместим с PocketMine-MP";
	}

	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin
				+ "' вызвал ошибку во время выполнения.</b>";
	}

	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Следующие плагины вызвали ошибки при выполнении: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" и ");
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
		return "Плагин с ошибкой выполнения";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "LegacyRandomSource Многопоточность";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Обнаружено многопоточное обращение к классу LegacyRandomSource. Для получения дополнительной информации используйте моды 'Unsafe World Random Access Detector' или 'C2ME'.</b>";
	}

	@Override
	public String desdeUltimoExito() {
		return "С момента последнего успеха";
	}

	@Override
	public String noHayCambios() {
		return "Нет изменений";
	}

	@Override
	public String desdeUltimoIntento() {
		return "С момента последней попытки";
	}

	@Override
	public String fallo() {
		return "Ошибка";
	}

	@Override
	public String diferentesDeLasMods() {
		return "Отличается от модов";
	}

	@Override
	public String historialDeMods() {
		return "История модов";
	}

	@Override
	public String archivo0() {
		return "Файл0";
	}

	@Override
	public String archivo1() {
		return "Файл1";
	}

	@Override
	public String comparar() {
		return "Сравнить";
	}

	@Override
	public String seleccionarDosArchivos() {
		return "Выберите два файла";
	}

	@Override
	public String archivoExito() {
		return "Файл успешного запуска";
	}

	@Override
	public String archivoFalla() {
		return "Файл сбоя";
	}

	@Override
	public String errorComparandoArchivos() {
		return "Ошибка сравнения файлов";
	}

	@Override
	public String comparando() {
		return "Сравнивается";
	}

	@Override
	public String con() {
		return "с";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
				+ "<p><b>Панель сравнения истории модов</b></p>"
				+ "<p>Эта панель позволяет сравнивать списки модов из разных сеансов запуска. "
				+ "Выберите один файл слева и другой справа, чтобы увидеть изменения между ними.</p>"

				+ "<h3>Как использовать:</h3>" + "<ol>"
				+ "<li><b>Выбор файлов:</b> Нажмите на радиокнопки рядом с именами файлов. "
				+ "Файлы с окончанием <span style='color: #4CAF50; font-weight: bold;'>.успех</span> — успешные сеансы, "
				+ "файлы <span style='color: #F44336; font-weight: bold;'>.провал</span> — сеансы с ошибками.</li>"

				+ "<li><b>Автоматическое сравнение:</b> После нажатия 'Сравнить' система проанализирует оба списка и покажет добавленные (+) или удалённые (-) моды.</li>"

				+ "<li><b>Просмотр результатов:</b> Результат отображается в формате HTML с цветовым кодированием: "
				+ "<ul>" + "<li><span style='color: green;'>+ Добавленный мод</span></li>"
				+ "<li><span style='color: red;'>- Удалённый мод</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>Основные возможности:</h3>" + "<ul>"
				+ "<li>Поддерживает любую комбинацию файлов (успех/провал).</li>"
				+ "<li>Двустороннее сравнение для точного отслеживания изменений.</li>"
				+ "<li>Прокрутка для длинных списков модов.</li>"
				+ "<li>Интеграция с поясняющими изображениями для лучшего понимания.</li>" + "</ul>"

				+ "<p>Разработано с <3️ для отслеживания изменений в ваших настройках.</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Возможно, у вас есть проблемы, связанные с IPv6. " + "Есть два решения: "
				+ "1) Добавьте аргумент JVM <code>-Djava.net.preferIPv4Stack=true</code> в свой лаунчер, или "
				+ "2) Используйте кнопку 'QuickFix' в программе CrashDetector для автоматического применения патча с этой настройкой."
				+ "</b>";
	}

	@Override
	public String parcheIPv4() {
		return "Патч IPv4/6";
	}

	@Override
	public String carpetaHMCL() {
		return "Папка HMCL (только для HelloMineCraftLauncher)";
	}

	@Override
	public String descripcionCurseforge() {
		return "Примечание: Лог не создаётся, если в настройках > Minecraft включён параметр \"Пропустить лаунчер\"";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/Производные: Щелкните правой кнопкой мыши по инстансу и выберите \"Изменить инстанс\". В открывшемся окне найдите раздел \"Логи Minecraft\" или подобный ему.<br>"
				+ "Эти логи содержат стандартный вывод (STDOUT), критически важный для диагностики ошибок.";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher): Выберите папку, где установлен HMCL, и укажите папку \".hmcl\". Логи HMCL сохраняются здесь и содержат важную информацию об ошибках.<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: Лаунчер имеет вкладку разработчика с детализированными логами. Эта вкладка находится в меню настроек лаунчера.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: Существует всплывающее окно с логами. Оно содержит кнопки копирования и загрузки. Логи генерируются автоматически при запуске игры и содержат критичную диагностическую информацию.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: Щелкните правой кнопкой мыши по инстансу и выберите \"Настройки\". Затем перейдите в раздел логов, чтобы просмотреть важную информацию из стандартного вывода (STDOUT).";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "Ссылки в формате Markdown: Вставьте любую ссылку, содержащую логи в формате Markdown. Система попытается автоматически извлечь ссылки на логи (latest.log, launcher_log.txt, debug.log и т. д.) и проанализировать их.";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "Журнал лаунчера не найден";
	}

	@Override
	public String imagenNoEncontrada() {
		return "Изображение не найдено";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "ОБЩИЙ: Выберите тип используемого лаунчера. Журналы лаунчера (launcher_log.txt, stdout и т. д.) содержат критическую информацию об ошибках, которых нет в latest.log. CrashDetector не может прочитать журналы вашего лаунчера — возможно, он не создаёт файл журнала, и вам нужно вставить журналы вручную.<br>"
				+ "Для получения дополнительной информации см. <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">этот вопрос</a>. Эти журналы содержат стандартный вывод (STDOUT), необходимый для диагностики многих типов ошибок.";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Обнаружены отсутствующие классы из Create. Create сильно изменился — многие классы были удалены. Начиная с Create 6 (февраль 2025), аддоны для старых версий Create больше не работают. QuickFix не может решить эту проблему. Вам нужно обновить аддоны Create, удалить устаревшие или использовать правильную версию Create для нужных аддонов.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Обнаружены отсутствующие классы из EpicFight. EpicFight сильно изменился — многие классы были удалены. QuickFix не может решить эту проблему. Вам нужно обновить аддоны EpicFight, удалить устаревшие или использовать правильную версию EpicFight для нужных аддонов.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Вы используете OpenJ9, который не поддерживается этим приложением. Многие приложения, включая это, не поддерживают OpenJ9 из-за различий в реализации JVM. QuickFix не может автоматически решить эту проблему. Вам нужно установить совместимый JDK, например Oracle JDK, OpenJDK Hotspot или другие рекомендуемые альтернативы.</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Для корректной работы этой версии приложения требуется Java 11 (JDK 11). Вы используете устаревшую, несовместимую версию Java. QuickFix не может обновить Java автоматически. Вам нужно скачать и установить JDK 11 или более новую совместимую версию по ссылкам, указанным в решении.</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Вы выделили слишком много памяти, из-за чего системе не хватает ресурсов. Это происходит, когда указано больше RAM, чем доступно, или при использовании 32-битной JVM, не способной обрабатывать большие объёмы памяти.</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Чтобы решить проблему, уменьшите объём памяти, выделенной приложению. Рекомендуемый максимум зависит от системы, но обычно не должен превышать 70–80% от общего объёма RAM. При использовании 32-битной JVM лимит составляет около 2–3 ГБ, независимо от объёма физической памяти.</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Чтобы уменьшить выделенную кучу (heap), откройте настройки лаунчера и найдите параметр памяти. Уменьшите максимальное значение (Xmx) до подходящего. Например, при 8 ГБ ОЗУ выделите 3–4 ГБ; при 16 ГБ — 6–8 ГБ. Оставьте достаточно памяти для ОС и других программ.</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Отсутствуют важные файлы Forge. Файл '" + archivo
				+ "' не найден в вашей установке. Это происходит, если установка Forge была прервана или важные файлы были удалены. QuickFix не может восстановить эти файлы автоматически. Вам нужно переустановить Forge с помощью официального установщика.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge не может найти требуемую версию Minecraft. Требуется версия " + version
				+ ", но она не найдена в файле '" + archivo
				+ "'. Это происходит при несоответствии версии Minecraft и версии Forge. Убедитесь, что вы скачали правильную версию Forge, совместимую с вашей версией Minecraft.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Не удалось найти целевой объект 'fmlclient', необходимый для запуска Forge. Это указывает на неполную или повреждённую установку Forge. Ключевые файлы Forge, возможно, не были установлены корректно. Вам нужно переустановить Forge с помощью официального установщика.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Основной класс Minecraft не найден в загрузчике классов. Это обычно означает неполную установку Forge или конфликт с другими модами. Файлы Minecraft могли повредиться при установке Forge. Вам нужно правильно переустановить Forge.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Установка Forge неполная. Это может быть вызвано прерванной установкой, удалёнными файлами или несовместимостью с вашей версией Minecraft. Forge требует определённых файлов для работы, и некоторые из них отсутствуют в текущей установке.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "Неполная установка Forge";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Чтобы решить проблему, переустановите Forge. Убедитесь, что вы скачали версию, совместимую с вашей версией Minecraft, и завершите установку без перерывов.</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "Скачать Forge официально";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "Как правильно переустановить Forge";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>Инструкции по переустановке Forge:</h3>" + "<ol>"
				+ "<li>Скачайте правильный установщик Forge с официального сайта (рекомендуемая версия для вашего Minecraft)</li>"
				+ "<li>Полностью закройте лаунчер Minecraft</li>"
				+ "<li>Запустите установщик Forge от имени администратора</li>"
				+ "<li>Выберите опцию 'Installer' (не 'Installer (run client)')</li>"
				+ "<li>Укажите папку профиля Minecraft в лаунчере</li>"
				+ "<li>Нажмите 'OK' и дождитесь завершения установки</li>"
				+ "<li>Перезапустите лаунчер и проверьте, появился ли Forge в списке профилей</li>" + "</ol>"
				+ "<p><b>Важно:</b> При использовании кастомного лаунчера убедитесь, что выбрана правильная папка профиля.</p>"
				+ "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "Инструкции по переустановке Forge";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Ошибка неудовлетворённой ссылки: не удалось загрузить библиотеку " + nombreBiblioteca
				+ ". Возможные решения:<br/><br/>"
				+ "a) Добавьте каталог с общей библиотекой в -Djava.library.path или -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Добавьте JAR-файл с общей библиотекой в classpath.<br/><br/>"
				+ "Эта ошибка возникает, когда Minecraft не может найти необходимые файлы для работы. "
				+ "Обычно вызвана неполной установкой Minecraft или проблемами с правами доступа в системе.</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "Ошибка неудовлетворённой ссылки";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Не удалось загрузить библиотеку. Возможные решения:<br/><br/>"
				+ "a) Добавьте каталог с общей библиотекой в -Djava.library.path или -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Добавьте JAR-файл с общей библиотекой в classpath.<br/><br/>"
				+ "Эти технические решения предназначены для опытных пользователей. Большинству пользователей следует попробовать "
				+ "переустановить Minecraft перед изменением этих параметров.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Конфликт ID: ID <strong>" + id
				+ "</strong> уже занят модом <strong>" + modOrigen + "</strong>, при попытке добавить <strong>"
				+ modDestino
				+ "</strong>. Происходит, когда два мода пытаются использовать один и тот же ID для разных элементов.</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Превышен максимальный допустимый диапазон ID. Происходит, когда моды пытаются зарегистрировать блоки или предметы с ID за пределами диапазона, поддерживаемого вашей версией Minecraft.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "Конфликт ID";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Чтобы решить это в Minecraft 1.12.2, установите <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. Для 1.7.10 используйте <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Используйте такие инструменты, как <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> или <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a>, чтобы устранить конфликты ID.</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "Установить JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "Установить EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "Использовать IdFix Minus или IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "Использовать Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "Просмотреть японскую документацию";
	}

	@Override
	public String escanearDeMCreator() {
		return "Сканировать MCreator";
	}

	@Override
	public String tituloArbolDeMods() {
		return "Дерево модулей и классов";
	}

	@Override
	public String tipoBusqueda() {
		return "Тип";
	}

	@Override
	public String filtroTodos() {
		return "Все";
	}

	@Override
	public String filtroPaquetes() {
		return "Пакеты";
	}

	@Override
	public String filtroClases() {
		return "Классы";
	}

	@Override
	public String filtroMetodos() {
		return "Методы";
	}

	@Override
	public String filtroCampos() {
		return "Поля";
	}

	@Override
	public String filtroReferenciasCampo() {
		return "Ссылки на поля";
	}

	@Override
	public String filtroReferenciasMetodo() {
		return "Ссылки на методы";
	}

	@Override
	public String filtroReferenciasClase() {
		return "Ссылки на классы";
	}

	@Override
	public String tipBuscar() {
		return "Введите для поиска в дереве модов";
	}

	@Override
	public String botonBuscar() {
		return "Поиск";
	}

	@Override
	public String botonResetearArbol() {
		return "Сбросить дерево";
	}

	@Override
	public String modsCargados() {
		return "Загруженные моды";
	}

	@Override
	public String clases() {
		return "Классы";
	}

	@Override
	public String metodos() {
		return "Методы";
	}

	@Override
	public String campos() {
		return "Поля";
	}

	@Override
	public String referencias() {
		return "Ссылки";
	}

	@Override
	public String resultadosBusqueda() {
		return "Результаты поиска";
	}

	@Override
	public String buscarReferencias() {
		return "Найти ссылки";
	}

	@Override
	public String referenciasMod() {
		return "Ссылки мода";
	}

	@Override
	public String referenciasClase() {
		return "Ссылки класса";
	}

	@Override
	public String referenciasMetodo() {
		return "Ссылки метода";
	}

	@Override
	public String referenciasCampo() {
		return "Ссылки поля";
	}

	@Override
	public String noSeEncontraronReferencias() {
		return "Ссылки не найдены";
	}

	@Override
	public String detalleMod() {
		return "Детали мода:";
	}

	@Override
	public String ubicacion() {
		return "Расположение";
	}

	@Override
	public String nombres() {
		return "Имена";
	}

	@Override
	public String modulo() {
		return "Модуль";
	}

	@Override
	public String detalleClase() {
		return "Детали класса:";
	}

	@Override
	public String detalleMetodo() {
		return "Детали метода:";
	}

	@Override
	public String detalleCampo() {
		return "Детали поля:";
	}

	@Override
	public String clase() {
		return "Класс";
	}

	@Override
	public String tipo() {
		return "Тип";
	}

	@Override
	public String referenciasAMetodos() {
		return "Ссылки на методы:";
	}

	@Override
	public String referenciasACampos() {
		return "Ссылки на поля:";
	}

	@Override
	public String arbolDeMods() {
		return "Дерево модов";
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
		return "Декомпилировать";
	}

	@Override
	public String exportar() {
		return "Экспорт";
	}

	@Override
	public String importar() {
		return "Импорт";
	}

	@Override
	public String errorImportar() {
		return "Ошибка импорта";
	}

	@Override
	public String estructuraImportada() {
		return "Структура импортирована";
	}

	@Override
	public String estructuraExportada() {
		return "Структура экспортирована";
	}

	@Override
	public String errorExportar() {
		return "Ошибка экспорта";
	}

	@Override
	public String exportando() {
		return "Экспорт";
	}

	@Override
	public String exportacionTardara() {
		return "Экспорт может занять время";
	}

	@Override
	public String porFavorEspere() {
		return "Пожалуйста, подождите";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>У вас отсутствуют бинарные файлы VLC. WaterMedia требует бинарные файлы VLC. Установите их вручную с сайта https://www.videolan.org/vlc/.  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "Скачать VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Критическая ошибка: Имя модуля '" + nombreModulo
				+ "' содержит недопустимые символы. Часть '" + parteInvalida
				+ "' не является допустимым идентификатором Java. Это происходит, когда мод использует зарезервированные слова Java (например, 'true', 'class') или недопустимые символы в своём имени.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "Недопустимые символы в имени мода";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "Имя мода '" + nombreModulo + "' недопустимо, потому что содержит '" + parteInvalida
				+ "', которое является зарезервированным словом Java или недопустимым символом. "
				+ "Найдите в логах, какому моду соответствует это имя (обычно имя JAR-файла)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "Перейдите в папку мода и отредактируйте файл <b>mods.toml</b> в папке <b>/META-INF/</b>. "
				+ "Измените значение <b>modId</b>, чтобы использовать только буквы, цифры и подчёркивания, без зарезервированных слов Java";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "Пример допустимого имени: 'truemod_shot_enchantment' вместо 'true.shot.enchantment'. "
				+ "Помните, что имена модов не могут содержать точки, дефисы или зарезервированные слова Java, такие как 'true', 'false' или 'class'";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Критическая ошибка с модом: '" + nombreJar
				+ "'. Отсутствует обязательное поле 'mandatory' в зависимостях. Происходит, когда файл mods.toml не указывает, является ли зависимость обязательной.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "Зависимость мода без обязательного поля";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "Проблемный мод: <b>" + nombreJar + "</b>. У этого файла ошибка в конфигурации зависимостей";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "Откройте файл <b>mods.toml</b> в папке <b>/META-INF/</b> мода <b>" + nombreJar + "</b>";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "В разделе зависимостей убедитесь, что каждая запись содержит <b>mandatory=true</b> или <b>mandatory=false</b> (например: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Критическая ошибка с модом: '" + nombreJar
				+ "'. Неверная конфигурация access transformer. Происходит, когда файл конфигурации имеет неправильный синтаксис или ссылается на несуществующие классы/методы.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Недопустимый Access Transformer";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "Проблемный мод: <b>" + nombreJar
				+ "</b>. Этот мод содержит недопустимую конфигурацию access transformer";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "Откройте файл <b>accessTransformer.cfg</b> внутри мода <b>" + nombreJar
				+ "</b> (обычно в корневой папке JAR-файла)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "Исправьте синтаксис access transformer. Строки должны следовать формату: <b>access class.method</b> (напр.: public net.minecraft.world.entity.Entity.func_200560_a). Удалите строки, ссылающиеся на классы или методы, которых нет в вашей версии Minecraft";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Критическая ошибка: Расхождение между ID мода в аннотации @Mod и файлом mods.toml. Мод '"
				+ nombreMod + "' не может быть загружен, так как ID не совпадают.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "Расхождение между @Mod и mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "Мод в разработке '" + nombreMod
				+ "' имеет расхождение между ID в аннотации <b>@Mod</b> и значением в <b>mods.toml</b>";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "Убедитесь, что ID в основном классе совпадает со значением <b>modId</b> в файле <b>/META-INF/mods.toml</b>. Пример: <b>@Mod(\"mymod\")</b> должно совпадать с <b>modId=\"mymod\"</b>";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "Если вы используете Gradle, выполните <b>clean</b> после изменений, чтобы гарантировать корректное обновление ресурсов. Иногда старые файлы остаются в папке build";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "клиент" : "сервер";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "сервер" : "клиент";

		return "<b style='color:#" + config.obtenerColorError() + "'>Критическая ошибка: Попытка загрузить класс '"
				+ nombreClase + "' в среде " + plataforma + ", но он предназначен для " + plataformaOpuesta
				+ ". <b>Используйте функцию «Дерево модов» на боковой панели, чтобы найти, какой мод пытается загрузить этот класс</b>. "
				+ "Моды создаются специально для одной платформы и не работают на другой.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "Мод на неправильной платформе";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "На вкладке <b>Дерево модов</b> (справа) найдите ссылки на класс <b>" + nombreClase
				+ "</b>, чтобы определить, какой мод вызывает проблему";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "клиент" : "сервер";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "сервер" : "клиент";

		return "Обнаруженный мод — это мод для <b>" + plataformaOpuesta
				+ "</b>, и его нельзя использовать в вашей среде " + plataforma + ".";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "Удалите проблемный мод из папки <b>mods</b>. Если вам нужна аналогичная функциональность для этой платформы, "
				+ "найдите альтернативный мод, специально разработанный для <b>клиента</b> или <b>сервера</b> соответственно";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Критическая ошибка: Отсутствуют метаданные для modid '").append(modIdFaltante).append("'. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("Следующие моды могут быть причиной проблемы: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", и другие...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Это происходит, когда мод зависит от другого мода, который не установлен или имеет неверный файл mods.toml.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "Отсутствуют метаданные mods.toml";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("Следующие моды зависят от '").append(modIdFaltante)
					.append("': <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", и другие...");
			paso.append(
					"</b>. Используйте функцию <b>Дерево модов</b>, чтобы подтвердить, какой мод вызывает проблему");
			return paso.toString();
		}
		return "Один из модов пытается зависеть от '" + modIdFaltante
				+ "', но этот мод не установлен. Используйте функцию <b>Дерево модов</b>, чтобы определить проблемный мод";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "У вас два варианта:<br/>" + "1. <b>Установите недостающий мод</b>: Найдите и установите мод с ID '"
				+ modIdFaltante + "'<br/>"
				+ "2. <b>Удалите зависимый мод</b>: Если функциональность не нужна, удалите мод, зависящий от '"
				+ modIdFaltante + "'";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Если мод '" + modIdFaltante + "' — это библиотека (например, 'forge', 'minecraft', 'curios'), "
				+ "убедитесь, что у вас установлена правильная версия Minecraft и Forge. "
				+ "Если это обычный мод, проверьте его страницу загрузки на предмет необходимых зависимостей";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Предупреждение: Ошибка инициализации звуковой системы. Звуки и музыка отключены. Эта ошибка обычно связана с модом SoundPhysicsMod и может быть вызвана конфликтами с другими звуковыми библиотеками.</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "Ошибка звуковой системы";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "Ошибка обычно связана с <b>SoundPhysicsMod</b>. Проверьте, установлена ли у вас последняя версия, совместимая с вашей версией Minecraft";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "Если вы используете другие звуковые моды (например, Sound Filters, Dynamic Surroundings и т. д.), попробуйте временно удалить SoundPhysicsMod, чтобы проверить, устранит ли это конфликт";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "Проверьте папку <b>logs</b> на наличие дополнительных сообщений, связанных с LWJGL или OpenAL, которые могут указывать на проблемы с базовыми звуковыми библиотеками";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Критическая ошибка: Класс '").append(nombreClase)
				.append("' зарегистрирован как слушатель событий, но не содержит допустимых методов. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Этот класс находится в следующих модах: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", и другие...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Это происходит, когда класс регистрируется для прослушивания событий, но не имеет методов с аннотацией @SubscribeEvent.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "Класс зарегистрирован без слушателей событий";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("Проблемный класс находится в этих модах: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", и другие...");
			paso.append("</b>. Эти моды пытаются зарегистрировать события без допустимых методов");
			return paso.toString();
		}
		return "Класс <b>" + nombreClase
				+ "</b> был зарегистрирован для прослушивания событий, но не содержит методов с аннотацией <b>@SubscribeEvent</b>. Используйте функцию <b>Дерево модов</b>, чтобы определить, какой мод содержит этот класс";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "В исходном коде убедитесь, что класс <b>" + nombreClase + "</b> содержит хотя бы один метод с: "
				+ "<b>@SubscribeEvent public void имяМетода(СобытиеОпределенногоТипа событие) { ... }</b>. "
				+ "Если это внутренний класс, убедитесь, что он не помечен как static";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("Для идентифицированных модов (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", и т. д.");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("свяжитесь с разработчиком этого мода, чтобы он исправил проблему. ");
			} else {
				paso.append("свяжитесь с разработчиками этих модов, чтобы они исправили проблему. ");
			}
		}

		paso.append(
				"Если вы разработчик, удалите регистрацию этого класса в EventBus или добавьте допустимые методы @SubscribeEvent");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Критическая ошибка: Произошло исключение 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' при обработке файла '"
				+ nombreArchivo
				+ "'. Эта ошибка указывает на то, что лаунчер не смог корректно загрузить или распаковать файлы модпака. "
				+ "Сообщение 'zip END header not found' означает, что JAR-файл неполный или повреждён, что крайне часто встречается у лаунчеров, плохо управляющих загрузкой больших файлов. "
				+ "Проблема в основном затрагивает пользователей Twitch/CurseForge, Technic Launcher и особенно Luna Pixel, поскольку эти лаунчеры часто не проверяют целостность загруженных файлов. "
				+ "Пользователям Luna Pixel рекомендуется перейти на ATLauncher — более стабильную альтернативу, которая лучше обрабатывает целостность файлов и избегает этой ошибки. "
				+ "Система не может загрузить моды, так как формат ZIP повреждён, что мешает Forge прочитать необходимые ресурсы для запуска игры.</b>";
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "Ошибка UnionFileSystem — Повреждённый файл";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		return "Полностью переустановите модпак с нуля";
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "Если вы используете Luna Pixel, перейдите на ATLauncher";
	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "Проверьте подключение к интернету и свободное место на диске перед переустановкой";
	}

	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "Включить ProxySysOutSysErr?\n\n"
				+ "Эта опция позволяет CrashDetector получать доступ к System.out и System.err, когда лаунчер не предоставляет логи.\n\n"
				+ "Следует включать только если вы не можете вручную вставить лог.\n\n"
				+ "Внимание: это может конфликтовать с некоторыми модами или лаунчерами.\n\n"
				+ "Требуется перезапуск игры/приложения для применения изменений.";
	}

	@Override
	public String confirmacionTitulo() {
		return "Подтверждение";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr успешно включён.\n\n"
				+ "Требуется перезапуск CrashDetector для применения изменений.";
	}

	@Override
	public String informacionTitulo() {
		return "Информация";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("Критическая ошибка: AzureLib и GeckoLib инициализированы слишком рано! ");
		} else if (azureLibError) {
			mensaje.append("Критическая ошибка: AzureLib инициализирован слишком рано! ");
		} else if (geckoLibError) {
			mensaje.append("Критическая ошибка: GeckoLib инициализирован слишком рано! ");
		}

		mensaje.append(
				"Эта ошибка возникает при попытке использовать моды Fabric с не-Fabric-версиями этих библиотек. ");

		if (connectorPresente) {
			mensaje.append(
					"Обнаружен мод совместимости (Sinytra Connector или specialcompatibilityoperation), что указывает на попытку запуска модов Fabric в среде Forge или FeatureCreep. ");
			mensaje.append(
					"Проверьте ошибку 'Ошибка инициализации FabricMC' в логах, чтобы определить конкретный проблемный мод. ");
		}

		mensaje.append(
				"AzureLib и GeckoLib — ключевые библиотеки для модов анимации, но они должны соответствовать правильной платформе (Fabric или Forge). ");
		mensaje.append("Игра не может корректно загрузить моды анимации из-за конфликта инициализации.");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "Библиотека инициализирована слишком рано";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "Проверьте ошибку 'Ошибка инициализации FabricMC' в логах, чтобы найти проблемный мод";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "Убедитесь, что вы используете правильную версию AzureLib/GeckoLib для вашей платформы (Forge или Fabric)";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Критическая ошибка: Несовместимость между C2ME и модами совместимости. "
				+ "Эта ошибка возникает, потому что C2ME пытается получить доступ к внутренним компонентам Java, которые ограничены в средах с "
				+ "Sinytra Connector или specialcompatibilityoperation, или другими модами совместимости Fabric/Forge. "
				+ "<b>C2ME несовместим с такими средами, но <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> — рекомендуемая альтернатива</b>, которая корректно работает "
				+ "с модами подключения. Игра не может запуститься из-за конфликта прав безопасности Java.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "Несовместимость C2ME с модами подключения";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "Удалите C2ME из папки mods";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "Скачайте и установите <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> вместо него (совместимо с Sinytra Connector)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "Убедитесь, что все моды подключения (например, Sinytra Connector) обновлены до последней версии";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Критическая ошибка: Не удалось загрузить плагин JEI для мода '" + modId + "'. Класс '"
				+ nombreClase + "' (ID плагина: '" + pluginId
				+ "') вызвал ошибку, из-за которой игра аварийно завершается при запуске. "
				+ "Эта проблема возникает, когда мод имеет несовместимую или сломанную интеграцию с JEI, мешающую инициализации игры.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "Сбой плагина JEI — вызывает крах";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "Мод <b>" + modId
				+ "</b> содержит сломанный плагин JEI, вызывающий крах. Используйте функцию <b>Дерево модов</b>, чтобы точно определить, какой мод вызывает проблему";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "Временно удалите мод <b>" + modId + "</b> из папки mods, чтобы проверить, устранит ли это крах";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "Найдите обновления для мода <b>" + modId
				+ "</b> или свяжитесь с его разработчиком, сообщив о проблеме с плагином JEI. "
				+ "Пока что мод нужно удалить, чтобы игра запускалась";
	}

	@Override
	public String tituloLectador() {
		return "Читатель логов - Обнаружение сбоев";
	}

	@Override
	public String obtenerTituloLeyenda() {
		return "ЦВЕТОВАЯ ЛЕГЕНДА";
	}

	@Override
	public String obtenerErrorEnLeyenda() {
		return "Критические ошибки";
	}

	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "Стектрейсы";
	}

	@Override
	public String obtenerTituloError() {
		return "Ошибка";
	}

	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "Произошла ошибка при обработке выбранной строки";
	}

	@Override
	public String obtenerNombreError() {
		return "НАЗВАНИЕ ОШИБКИ";
	}

	@Override
	public String obtenerDescripcionError() {
		return "ПОДРОБНОЕ ОПИСАНИЕ";
	}

	@Override
	public String obtenerSeleccionarConsola() {
		return "ВЫБЕРИТЕ ЛОГ";
	}

	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "Ошибка не определена";
	}

	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "В логах обнаружена критическая ошибка. " + "Проверьте стектрейс, чтобы определить первопричину.";
	}

	@Override
	public String obtenerErrorLecturaArchivo() {
		return "Не удалось прочитать файл лога. " + "Убедитесь, что файл существует и доступен для чтения.";
	}

	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "Анализатор логов";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Критическая ошибка: Не удалось зарегистрировать автоматических подписчиков для мода '")
				.append(modId).append("'. ");

		mensaje.append("Проблемный класс: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Этот класс находится в: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", и других...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Эта ошибка возникает, когда мод пытается автоматически зарегистрировать класс как подписчика событий, но класс не может быть загружен. ");
		mensaje.append(
				"<b>Проверьте другие ошибки в логе — реальная причина может быть в предыдущей неудачной загрузке</b>.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "Ошибка регистрации автоматических подписчиков";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "Мод <b>" + modId + "</b> пытается зарегистрировать класс <b>" + nombreClase
				+ "</b> как автоматического подписчика, но это не удалось. Убедитесь, что класс существует и доступен";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder(
					"Проблемный класс <b>" + nombreClase + "</b> находится в этих файлах: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", и других...");
			paso.append("</b>. ");
			paso.append(
					"Используйте функцию <b>Дерево модов</b>, чтобы определить, в каком файле находится проблемный класс");
			return paso.toString();
		}
		return "Класс <b>" + nombreClase + "</b> не найден ни в одном файле мода. Убедитесь, что мод <b>" + modId
				+ "</b> установлен правильно. Используйте функцию <b>Дерево модов</b> для поиска проблемы";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "Обновите мод <b>" + modId + "</b> до последней версии, совместимой с вашей версией Minecraft и Forge. "
				+ "Если проблема останется, свяжитесь с разработчиком мода и сообщите об ошибке с указанием проблемного класса";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "Проверьте <b>другие ошибки в логе</b> перед этим сообщением — реальная причина может быть в предыдущем сбое загрузки. "
				+ "Иногда предыдущая ошибка мешает загрузке необходимых классов для регистрации событий";
	}

	@Override
	public String limpiado() {
		// TODO Auto-generated method stub
		return "Очищено";
	}

	@Override
	public String original() {
		// TODO Auto-generated method stub
		return "Оригинал";
	}

	@Override
	public String verEnConsola() {
		// TODO Auto-generated method stub
		return "Просмотреть в логе";
	}

	@Override
	public String advertencia() {
		// TODO Auto-generated method stub
		return "Предупреждение";
	}

	@Override
	public String fatal() {
		// TODO Auto-generated method stub
		return "Фатально";
	}

	@Override
	public String noRegistroDeBattly() {
		// TODO Auto-generated method stub
		return "У BattlyLauncher нет доступного лога или консоли для копирования. Вы можете использовать ProxySysOutSysErr, чтобы перехватывать STDOUT и STDERR, перезапустив игру, но ProxySysOutSysErr может конфликтовать с модами, изменяющими STDOUT или STDERR";
	}

	@Override
	public String noRegistroDeNightWorld() {
		// TODO Auto-generated method stub
		return "Вам нужно включить режим отладки в настройках NightWorld, чтобы получить лог лаунчера. Это очень важно, особенно потому что он включает STDOUT и STDERR";
	}

	@Override
	public String noRegistroDeMCServidor() {
		// TODO Auto-generated method stub
		return "Вам нужно сохранить или вставить содержимое терминала вашего сервера, так как оно содержит информацию, отсутствующую в других логах, включая STDOUT, STDERR и другие ошибки. Пожалуйста, вставьте содержимое последней сессии. В будущем вы можете сохранять вывод терминала в файл под названием cd_launcherlog. Чтобы избежать необходимости вставки, добавьте >> cd_launcherlog после команды запуска, как показано на изображении. Обратите внимание: это предотвратит отображение в терминале; вся информация будет записываться только в этот файл.";
	}

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("Критическая ошибка: обнаружен трансформер LexForge в среде NeoForge. ");
		sb.append("</b>");

		sb.append("Задействованный класс: <b>").append(claseReceptora).append("</b>. ");
		sb.append("Затронутый интерфейс — <b>").append(interfazObjetivo).append("</b>, ");
		sb.append("отсутствующий метод — <b>").append(firmaMetodoFaltante).append("</b>. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Класс найден в: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", и другие...");
			sb.append("</b>. ");
		} else {
			sb.append("JAR-файлы с этим классом не найдены; возможно, он затенён или встроен как jar-in-jar. ");
		}

		sb.append(
				"Эта ошибка возникает, когда трансформер/сервис ModLauncher, скомпилированный для MinecraftForge/LexForge, ");
		sb.append("загружается в NeoForge с несовместимой версией API ModLauncher. ");
		sb.append("Обновите или замените компонент на версию для NeoForge.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "Использование трансформера LexForge в NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "Определите несовместимый трансформер: <b>" + claseReceptora + "</b>. " + "Ожидаемое API — <b>"
				+ interfazObjetivo + "</b>, отсутствует метод <b>" + firmaMetodoFaltante + "</b>. "
				+ "Проверьте, регистрирует ли мод этот класс в <b>META-INF/services</b>, и удалите или отключите его в NeoForge.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Расположение мода(ов): <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", и другие...");
			sb.append("</b>. ");
		} else {
			sb.append("JAR-файлы с этим классом не найдены. Проверьте вложенные jar-in-jar и затенённые зависимости. ");
		}
		sb.append(
				"Временно удалите эти JAR-файлы или используйте версии, совместимые с NeoForge, чтобы подтвердить источник.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "Замените компонент на специальную версию для NeoForge или перекомпилируйте его под версию ModLauncher, "
				+ "используемую NeoForge. Избегайте старых бинарников от LexForge/MinecraftForge.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "Очистите папку mods и удалите дублированные jar-in-jar-файлы. При необходимости очистите кэш лаунчера "
				+ "и перезапустите, чтобы убедиться, что трансформеры LexForge больше не загружаются.";
	}

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia не может запуститься: Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("несовместим.</b> ");
		sb.append("Удалите Xenon и используйте вместо него Embeddium или Sodium. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Обнаружен в: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", и другие...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia несовместим с Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "Обнаружен " + label + ", несовместимый с WaterMedia. Удалите его из профиля.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("Местоположения: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", и другие...");
			sb.append("</b>. Удалите этот JAR-файл.");
			return sb.toString();
		}
		return "JAR-файлы не найдены. Проверьте папку mods и удалите Xenon.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "Установите Embeddium или Sodium как замену и перезапустите игру.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "Ошибка сжатия (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>Deflater закрыт во время копирования ресурсов TACZ.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Связано с: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", и другие");
			sb.append("</b>. ");
		}
		sb.append(
				"<br/><b>Решение:</b> в файле <code>tacz/tacz-pre.toml</code> установите <code>DefaultPackDebug=true</code>. ")
				.append("При необходимости сначала создайте карту, затем активируйте её.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "В файле tacz/tacz-pre.toml установите DefaultPackDebug=true. При необходимости сначала создайте карту, затем активируйте её.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "Несвязанные функции плотности";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>В реестре отсутствуют функции плотности.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("Отсутствуют: ");
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
				"<br/><b>Решение:</b> установите или активируйте мод/datapack, определяющий эти функции, и перезапустите. Другая распространённая причина этой проблемы — наличие необходимого мода, но с ошибкой или конфликтом с другим модом; например, Terralith часто вызывает множество проблем и может привести к этой ошибке и другим, включая ошибки JSON.");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "Установите или включите мод/датапак, предоставляющий эти функции, и перезапустите игру.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// Краткое сообщение цветом ошибки с явным упоминанием мода
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Запись в реестре отсутствует: ").append(claveFaltante).append(". ");
		sb.append("Часто встречается в альфа-версии Steam & Railways для Create 6.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (альфа)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "Удалите или замените альфа-версию Steam & Railways для Create 6 на совместимую версию.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// Кратко, цветом ошибки, с прямой рекомендацией
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Конфликт загрузки: Multiworld вместе с Sodium/Embeddium/Rubidium вызывает ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance). ")
				.append("Рекомендация: удалите Multiworld или мод производительности, либо используйте совместимые версии.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "Конфликт: Multiworld с модами производительности";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Удалите Multiworld или Sodium/Embeddium/Rubidium, либо обновитесь до взаимно совместимых версий.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Sodium обнаружил несовместимый графический драйвер. "
				+ "Обновите драйвер вашей видеокарты до минимально требуемой версии или следуйте руководству по Sodium."
				+ "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "Ошибка контекста OpenGL";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Сбой OpenGL: отсутствует текущий контекст или функция недоступна в этом контексте. "
				+ "Также может быть проблема с драйверами видеокарты." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "Обновите/переустановите драйверы GPU и перезагрузитесь; отключите оверлеи и попробуйте запустить без модов производительности.";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "Ссылка скопирована в буфер обмена.";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		return "Искать внутри архивов (.zip/.jar/.war/.ear/.fpm/.rar для Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Ошибка разрешения текстуры: невозможно загрузить " + recurso + " — размер: " + tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "Ошибка разрешения текстуры";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "Эта ошибка возникает, когда текстуры слишком большие или слишком много ресурс-паков. "
				+ "Попробуйте использовать ресурс-паки с более низким разрешением или удалите некоторые из них. "
				+ "Убедитесь, что вы не добавили пользовательские текстуры с разрешением выше допустимого.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Ошибка службы ModLauncher: путь содержит недопустимые символы. "
				+ "Службы ModLauncher не могут обрабатывать пути, содержащие не-ASCII или специальные символы. "
				+ "Проблемные символы включают: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요, и особенно символ '\"', если он находится в конце имени. "
				+ "Распространённые компоненты служб в ModLauncher включают CrashDetector, "
				+ Config.obtenerInstancia().obtenerNombreCD()
				+ ", FeatureCreep, Vivicraft, Optifine, Sodium, clonos, Iris Shaders/Oculus, MixerLogger, CrashAssistant и Sintrya Connector. "
				+ "Вы можете удалить все службы, но другие проблемы могут возникнуть из-за имени пути. "
				+ "Решение: переименуйте экземпляр, используя только ASCII-символы (a-z, A-Z, 0-9), без пробелов и специальных символов.</b>";
	}

	@Override
	public String nombre_error_modlauncher_path() {
		return "Ошибка пути в ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "Эта ошибка возникает, когда путь к экземпляру содержит не-ASCII или специальные символы. "
				+ "Службы ModLauncher не могут обрабатывать такие пути. "
				+ "Решение: переименуйте экземпляр, используя только ASCII-символы (a-z, A-Z, 0-9), избегайте пробелов и специальных символов. "
				+ "Особое внимание уделите символу '\"', который вызывает серьёзные проблемы, особенно в конце имени.";
	}

	@Override
	public String tituloEditorCodice() {
		return "Редактор Codice";
	}

	@Override
	public String nuevo() {
		return "Новый";
	}

	@Override
	public String actualizarSeleccionado() {
		return "Обновить выбранное";
	}

	@Override
	public String eliminarSeleccionado() {
		return "Удалить выбранное";
	}

	@Override
	public String exportarJSON() {
		return "Экспортировать JSON...";
	}

	@Override
	public String guardarTodo() {
		return "Сохранить всё";
	}

	@Override
	public String general() {
		return "Общее";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "Текст для поиска";
	}

	@Override
	public String filtro() {
		return "Фильтр (id)";
	}

	@Override
	public String criticalidad() {
		return "Критичность (ПРЕДУПРЕЖДЕНИЕ/ОШИБКА/ФАТАЛЬНО)";
	}

	@Override
	public String prioridad() {
		return "Приоритет";
	}

	@Override
	public String lista() {
		return "Проверки";
	}

	@Override
	public String colIdioma() {
		return "Язык";
	}

	@Override
	public String colNombre() {
		return "Имя";
	}

	@Override
	public String colResultado() {
		return "Результат";
	}

	@Override
	public String vistaJson() {
		return "Просмотр JSON";
	}

	@Override
	public String idiomas() {
		return "Языки (все обязательны)";
	}

	@Override
	public String elegirFiltro() {
		return "Выбрать...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "Выберите фильтр";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "Доступные фильтры";
	}

	@Override
	public String faltanCampos() {
		return "Заполните все обязательные общие поля.";
	}

	@Override
	public String critInvalida() {
		return "Недопустимая критичность. Используйте ПРЕДУПРЕЖДЕНИЕ, ОШИБКА или ФАТАЛЬНО.";
	}

	@Override
	public String filtroNoExiste() {
		return "Указанный фильтр не существует.";
	}

	@Override
	public String faltanIdiomas() {
		return "Заполните имя и результат для всех языков:";
	}

	@Override
	public String verificacionInvalida() {
		return "Одна из проверок недействительна. Проверьте поля.";
	}

	@Override
	public String guardadoOk() {
		return "Успешно сохранено.";
	}

	@Override
	public String editorCodiceBoton() {
		return "Добавить причины";
	}

	@Override
	public String descripcionEditorCodice() {
		return "Здесь можно зарегистрировать причины. Требуется ID — строка без специальных символов, акцентов и пробелов. Для фильтров доступны: \"строка содержит\" — поиск строки в одной строке лога, \"всё содержит\" — если лог содержит строку, \"регулярное выражение в строке\" — совпадение строки с регулярным выражением, \"регулярное выражение во всём\" (рекомендуется использовать построчные версии). Необходимо указать уровень критичности: FATAL, ERROR или ADVERTENCIA — для раскраски. Для каждого языка нужно ввести имя и результат, которые будут отображаться на экране. Можно добавлять новые проверки или удалять существующие. Сохраняется после завершения.";
	}

	@Override
	public String descartarCambios() {
		return "Отменить несохранённые изменения в текущей проверке?";
	}

	@Override
	public String confirmacion() {
		return "Подтверждение";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "Сохранить изменения перед выходом?";
	}

	@Override
	public String salirSinGuardar() {
		return "Выйти без сохранения";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Критическая ошибка: не удалось загрузить сервис modlauncher (IDependencyLocator).<br>");
		sb.append("🔹 <b>Проблемный класс:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>Затронутый мод:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append(
					"🔸 <b>Мод не определён.</b> Проверьте недавно установленные, разрабатываемые или неправильно упакованные моды.<br>");
		}

		sb.append("🔸 <b>Причина:</b> Файл <code>META-INF/services/...</code> мода повреждён, ");
		sb.append("несовместим с этой версией Forge/NeoForge или предназначен для неверной версии.<br>");
		sb.append("🔸 <b>Последствие:</b> Forge/NeoForge не может зарегистрировать зависимости мода, ");
		sb.append("что блокирует запуск игры.<br>");
		sb.append("🔸 <b>Решение:</b> Обновите, переустановите или удалите проблемный мод. ");
		sb.append(
				"Если вы используете моды в стадии разработки, убедитесь, что они скомпилированы под точную версию вашего Forge/NeoForge.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "Ошибка конфигурации службы (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. Определите виновный мод: проверьте недавно установленные или разрабатываемые моды.";
		}
		return "1. Проблемный мод: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. Обновите, переустановите или удалите мод. Убедитесь, что используется версия, совместимая с вашим Forge/NeoForge.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>Критическая ошибка: Метод не существует.</b><br>"
				+ "Мод попытался вызвать метод <b style='color:#" + colorCodigo + "'>" + metodo + "</b>, "
				+ "который отсутствует в этой версии игры или другого мода.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "Метод не существует (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. Эта ошибка возникает, когда мод несовместим с текущей версией игры или другим модом.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. Обновите все затронутые моды. Если проблема сохраняется, сообщите об ошибке автору затронутого мода.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>Критическая ошибка: Поле не существует.</b><br>"
				+ "Мод попытался получить доступ к полю <b style='color:#" + colorCodigo + "'>" + campo + "</b>, "
				+ "которое отсутствует в этой версии игры или другого мода.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "Поле не существует (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. Эта ошибка обычно возникает, когда мод несовместим с текущей версией игры или другим модом.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. Обновите все затронутые моды. Если проблема сохраняется, свяжитесь с автором мода, вызвавшего ошибку.";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();

		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>Нужна помощь?</strong><br>"
				+ "  Если вы не знаете, как исправить проблему, или причина отсутствует здесь, вы можете получить помощь через наши социальные сети. "
				+ "  Нажмите кнопку <img src='" + iconoCompartir
				+ "' alt='Поделиться' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>Поделиться</strong>, чтобы получить ссылки на логи и результаты для нашей команды. "
				+ "  Если вы создатель модпака или организация, отредактируйте файл <code>crash_detector/plantilla.htm</code>, "
				+ "  чтобы настроить ссылки вашей команды." + "</div>";
	}

	@Override
	public String restablecerPlantilla() {
		return "Сбросить шаблон";
	}

	@Override
	public String restablecer() {
		return "Сбросить";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		return "Сбросить " + nombreImagen + " до значений по умолчанию?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		return "Сбросить шаблон до значений по умолчанию?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Отсутствуют классы AzureLib. Если у вас уже установлен AzureLib, установите версию до 8 октября 2025 года. Это была распространённая проблема. Если у вас нет AzureLib, установите актуальную версию.</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Мод <code>healight</code> вызывает критическую ошибку: <code>java.lang.NoSuchFieldError: INT</code>. "
				+ "Эта ошибка возникает, потому что мод пытается получить доступ к полю, которое больше не существует в MCForge 47.10 для Minecraft 1.20+. "
				+ "Из-за этой проблемы игра не может запуститься.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• Удалите или обновите мод <code>healight</code>. "
				+ "Текущая версия несовместима с MinecraftForge 47.10 для 1.20.1. "
				+ "Найдите более новую версию мода или рассмотрите использование альтернативы.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "Критическая ошибка: healight — Поле 'INT' не найдено";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("Класс <code>").append(clase)
				.append("</code> не реализует требуемый метод:<br>").append("<code>").append(metodo)
				.append("</code><br>").append("из интерфейса <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br><br>Подозрительный мод или файл: <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• Эта ошибка возникает, когда мод реализует интерфейс, но пропускает обязательный метод. "
				+ "Обновите <b>оба мода</b>, участвующих в этом (тот, что определяет интерфейс, и тот, что его реализует). "
				+ "Если вы не знаете, какие именно, посмотрите имена в сообщении об ошибке.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "Метод интерфейса не реализован (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Мод пытается загрузить <b>клиентский</b> класс "
				+ "(<code>AnimationMetadataSection</code>) на <b>выделенном сервере</b>, что невозможно. "
				+ "Эта ошибка обычно возникает, когда мод неправильно разделяет код для клиента и сервера. "
				+ "Наличие <code>ModernFix</code> может выявить эту проблему, хотя и не является её прямой причиной.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>Быстрое решение:</b> Временно удалите <code>ModernFix</code>, чтобы проверить, запускается ли сервер. "
				+ "Если да, значит, проблема в другом моде, который загружает клиентские классы на сервер.<br>"
				+ "• <b>Постоянное решение:</b> Найдите виновный мод (ищите моды с анимированными ресурсами, кастомными текстурами или графическими библиотеками) и обновите или удалите его.<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "Клиентский класс загружен на сервере (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Файл конфигурации мода <code>Sinytra Connector</code> повреждён. "
				+ "Обычно это происходит, когда файл заполняется нулевыми символами (<code>\\u0000</code>) "
				+ "из-за неожиданного завершения игры, сбоев записи или конфликтов модов.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• Перейдите в папку <code>config/</code> вашего экземпляра Minecraft.<br>"
				+ "• Найдите и удалите конфигурационные файлы модов connector.<br>"
				+ "• Перезапустите игру: Sinytra Connector создаст новый чистый файл конфигурации.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "Повреждённая конфигурация Sinytra Connector";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Файл <code>" + nombreJar
				+ "</code> повреждён или неполный.<br>"
				+ "Система не может прочитать его содержимое, так как отсутствует конечный заголовок ZIP-файла.<br>"
				+ "Эта ошибка обычно возникает после прерванной загрузки или сбоя лаунчера.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "Повреждённый JAR-файл (с указанием имени)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>Удалите повреждённый файл</b> и повторно загрузите его с официального источника (CurseForge, MinecraftStorage и т.д.).<br>"
				+ "• Если вы используете лаунчер вроде CurseForge, Technic или Luna Pixel, рассмотрите переход на <b>ATLauncher</b> или <b>Prism Launcher</b>, "
				+ "которые лучше проверяют целостность файлов.<br>"
				+ "• Убедитесь, что ваше интернет-соединение стабильно во время загрузки.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Не удаётся загрузить мир, так как один из его NBT-файлов повреждён "
				+ "(например: <code>level.dat</code>, <code>playerdata/*.dat</code> или чанки).<br>"
				+ "Конкретная ошибка: <code>UTFDataFormatException: некорректный ввод около байта " + byteCorrupto
				+ "</code>.<br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ Перед любой попыткой восстановления сделайте полную резервную копию папки мира.</b><br><br>"
				+ "Вы можете попробовать починить повреждённый файл с помощью <b>редактора NBT</b>, например <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>.<br>"
				+ "Если повреждение серьёзное, используйте <b>шестнадцатеричный редактор</b> (например, HxD), чтобы проверить и исправить недопустимые байты "
				+ "(только если вы разбираетесь в формате NBT).<br>"
				+ "В крайнем случае восстановите мир из резервной копии или используйте функцию <i>восстановления мира</i> модов, таких как <code>FTB Backup</code>.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>Сделайте полную резервную копию папки мира</b> перед попыткой восстановления.<br>"
				+ "• Используйте редактор NBT (например, NBT Studio), чтобы открыть и исправить повреждённый файл.<br>"
				+ "• Если не получится, проверьте файл шестнадцатеричным редактором в позиции повреждённого байта.<br>"
				+ "• При отсутствии опыта восстановите мир из последней резервной копии.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "Повреждённый мир: ошибка загрузки данных NBT";
	}

	@Override
	public String problema_con_openAL() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>У вас возникла проблема с OpenAL. Иногда её вызывают драйверы Nouveau, но иногда виной тому версия OpenAL, поставляемая с приложением, которая несовместима с версией в вашем дистрибутиве, и вам нужно использовать версию из вашего дистрибутива. Это особенно часто встречается в дистрибутивах на базе Red Hat и с модами на звук, такими как Sound Physics Remastered. См. это руководство для получения дополнительной помощи: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>Как исправить проблемы со звуком в Minecraft на Linux</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Сервер не может запуститься, потому что файл мира заблокирован другим процессом.<br>"
				+ "Это обычно происходит, если:<br>" + "• Уже запущен другой экземпляр сервера.<br>"
				+ "• Антивирус или проводник имеет открытой папку мира.<br>"
				+ "• Предыдущий процесс завершился некорректно и оставил файлы заблокированными.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>Закройте все экземпляры сервера</b> (включая фоновые процессы вроде javaw.exe).<br>"
				+ "• Если вы используете хостинг-панель (например, Multicraft), полностью перезапустите сервер через панель.<br>"
				+ "• <b>Временно отключите антивирус</b>, если подозреваете, что он блокирует файлы.<br>"
				+ "• На локальных системах закройте все окна Проводника, показывающие папку мира.<br>"
				+ "• Если проблема сохраняется, вручную удалите файл <code>session.lock</code> внутри папки мира (только если вы уверены, что нет активного сервера).";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "Файл мира заблокирован другим процессом";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Мод попытался расширить класс <code>"
				+ clasePadreFinal + "</code>, "
				+ "но этот класс теперь помечен как <b>final</b> и не может быть унаследован.<br>"
				+ "Проблемный класс: <code>" + claseHija + "</code>.<br><br>"
				+ "Это обычно происходит, когда мод скомпилирован для более старой версии Minecraft или другого базового мода, "
				+ "который в недавних версиях пометил классы как <code>final</code>.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>Обновите все затронутые моды</b>, особенно те, которые могут быть связаны с упомянутым базовым модом.<br>"
				+ "• Если проблема сохраняется, найдите версию мода, совместимую с вашей текущей версией Minecraft и её зависимостями.<br>"
				+ "• В некоторых случаях временное удаление мода, содержащего дочерний класс, поможет подтвердить причину.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "Попытка наследования от final-класса";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Вы используете <b>Rubidium</b> (устаревшую ветку Sodium для Forge) вместе с <b>Iris или Oculus</b>.<br>"
				+ "В последних версиях Minecraft (1.19.2 и выше) "
				+ "Rubidium не успел за обновлениями Sodium, и у его зависимостей возникли проблемы.<br><br>"
				+ "Эта ошибка также может возникнуть из-за конфликта между модами производительности (Sodium, Rubidium, Embeddium, Bedium, Xeonium и т.д.) или Iris Shaders и другим модом.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>Удалите Rubidium</b> из папки <code>mods</code>.<br>"
				+ "• <b>Установите <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a></b>, "
				+ "активную и совместимую ветку Sodium для Forge, которая поддерживает Iris/Oculus на 1.20+.<br>"
				+ "• Убедитесь, что у вас не установлено более одной ветки Sodium одновременно (например: Rubidium + Embeddium).<br>"
				+ "• Если вы используете Oculus вместо Iris, проверьте его совместимость с вашей версией Forge и Embeddium.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Устаревший Rubidium с Iris/Oculus (OptionInstance является final)";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Мод <code>Simple Voice Chat</code> не может запустить свой голосовой сервер, потому что "
				+ "UDP-порт уже используется или настроенный IP-адрес недействителен.<br>"
				+ "Это не мешает запуску игры, но отключает функцию голосового чата.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• <b>Закройте любые другие экземпляры Minecraft</b> или приложение, использующее UDP-порт 24454.<br>"
				+ "• Если вы находитесь на сервере, убедитесь, что <b>ни одна другая служба</b> не использует этот порт.<br>"
				+ "• В настройках мода (<code>config/voicechat/</code>) измените UDP-порт на свободный (например, 24455).<br>"
				+ "• Если вы используете пользовательский IP-адрес, проверьте его правильность или оставьте пустым для использования значения по умолчанию.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Голосовой чат: UDP-порт занят или неверный IP";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "У BlockItem <code>" + nombreBlockItem
				+ "</code> блок равен null.<br>"
				+ "Эта ошибка обычно возникает в <b>аддонах для Create</b> (например, <code>dndecor</code>, <code>createdeco</code>) "
				+ "при конфликтах с <code>Amendments</code>, <code>Moonshine</code> или неправильной инициализации блоков.<br>"
				+ "<b>Примечание:</b> Это не прямая ошибка Amendments, а симптом более глубокой проблемы при загрузке реестра.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• <b>Обновите все связанные моды:</b> Create, Amendments, Moonshine и любые аддоны (особенно <code>dndecor</code> и <code>createdeco</code>).<br>"
				+ "• Если проблема сохраняется, <b>временно удалите аддоны для Create</b> по одному, чтобы найти виновника.<br>"
				+ "• Убедитесь, что <b>Amendments и Moonshine совместимы</b> с вашей версией Create и Forge.<br>"
				+ "• Проверьте, есть ли бета-версии или обновлённые форки проблемных аддонов.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "Null BlockItem в аддоне Create";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>")
				.append("Найдены моды, не принадлежащие ни одной активной платформе (Forge, Fabric и т.д.):<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>Это обычно происходит, когда:<br>")
				.append("• Моды от <b>Fabric и Forge</b> смешаны в одной папке.<br>")
				.append("• Установлен мод для несовместимой версии Minecraft.<br>")
				.append("• Мод повреждён или не является корректным JAR-файлом.</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>Убедитесь, что все моды предназначены для одной платформы</b> (только Forge <b>или</b> только Fabric, а не обе сразу).<br>"
				+ "• Используйте <b>дерево модов</b>, чтобы определить, какую платформу распознаёт каждый файл.<br>"
				+ "• Удалите любой мод, который вы не узнаёте или который предназначен для другой платформы.<br>"
				+ "• Если вы используете лаунчер, например CurseForge или Prism, убедитесь, что профиль настроен правильно.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "Мод несовместим с активным загрузчиком";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Не удалось создать модель <code>" + modid
				+ ":" + nombreModelo + "</code>.<br>" + "Это означает, что мод <code>" + modid
				+ "</code> имеет повреждённые, отсутствующие "
				+ "или несовместимые ресурсы для вашей версии Minecraft.</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>Обновите мод</b> до последней версии, совместимой с вашим экземпляром.<br>"
				+ "• Если вы используете сборку для разработки или кастомную, вернитесь к официальной версии.<br>"
				+ "• Убедитесь, что файл JAR не повреждён (переустановите его).<br>"
				+ "• Если проблема сохраняется, сообщите об ошибке автору мода, приложив этот лог.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "Не удалось создать модель ресурса";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Обнаружен критический конфликт между модами <code>Moonlight</code> и <code>Iceberg</code>.<br>"
				+ "Оба пытаются зарегистрировать системы перезагрузки ресурсов несовместимым способом, "
				+ "что приводит к сбою OpenGL из-за отсутствия валидного графического контекста.<br>"
				+ "Эта проблема часто возникает при использовании версий Forge, включающих адаптеры для модов Fabric.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>Обновите оба мода</b> до последних версий, совместимых с вашей версией Forge.<br>"
				+ "• Если проблема сохраняется, <b>временно удалите Iceberg</b>, так как Moonlight обычно является более важной зависимостью для других модов.<br>"
				+ "• Убедитесь, что у вас нет дублирующихся или смешанных версий Forge/Fabric этих модов.<br>"
				+ "• Проверьте, не включает ли другой мод (например, Supplementaries, Citadel и т.д.) функциональность Iceberg внутри себя.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "Критический конфликт: Moonlight против Iceberg (OpenGL без контекста)";
	}

	@Override
	public String instantanea() {
		return "Снимок";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "С последнего снимка";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "Выберите файл";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "Снимок успешно создан";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "Ошибка при создании снимка";
	}

	@Override
	public String consejo() {
		return "Совет";
	}

	@Override
	public String resultadoMuestra() {
		return "Показать результат";
	}

	@Override
	public String historaDeModsDesc() {
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>Совет:</b> Выберите два файла журнала, чтобы сравнить список модов. "
				+ "  Результат показывает <span style='color:%s;'>добавленные (+)</span> и "
				+ "  <span style='color:%s;'>удалённые (&#8722;)</span> на основе нормализованных имён. "
				+ "  Используйте кнопку «Снимок», чтобы создать копию существующего файла с расширением .instantanea."
				+ "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		return "Получить ссылки на логи в формате Markdown без отчёта";
	}

	@Override
	public String titulo_configuracion() {
		return "Настройки";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "Неожиданная ошибка при отправке.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "Неожиданная ошибка при генерации ссылок.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "Неожиданная ошибка при обработке кнопки.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "Нет связанного файла для открытия.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "Файл не существует:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "Не удалось открыть в редакторе.\nПуть будет скопирован в буфер обмена.";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "Не удалось открыть файл; путь был скопирован в буфер обмена.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "Рабочий стол не поддерживается; путь был скопирован в буфер обмена.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "Вы достигли лимита запросов. Попробуйте использовать другой сайт логов или другое API для логирования.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		return "Поделиться ссылкой";
	}

	@Override
	public String infoDeTrazos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Исправление верхних частей брёвен — главный приоритет. " + "Формат: Уровень, Строка. "
				+ "Все логи имеют систему нумерации. " + Verificaciones.nl_html
				+ "Как правило, нужно искать на самых нижних уровнях во всех логах; трассировки с высоким уровнем обычно являются ложноположительными. "
				+ "Важно использовать вашу способность читать консоль, так как анализ трассировок не является идеальным при большом их количестве."
				+ "</b>";
	}

	// --- Поисковик «Канарейки ордера» (Warrant Canary) ---
	/**
	 * Текст на кнопке для поисковика «канарейки ордера». Пример: "Поисковик
	 * канарейки ордера"
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "Поисковик канарейки ордера";
	}

	/**
	 * Сообщение, отображаемое в диалоговом окне, информирующее, что функция скоро
	 * будет доступна.
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "Эта функция станет доступной в ближайшее время.";
	}

	/**
	 * Заголовок диалогового окна, информирующего о скором появлении поисковика
	 * «канарейки ордера».
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "Скоро будет";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		return "Моды, несовместимые с Crash Assistant (Ложные)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		return "Мод несовместим с модпаком, использующим CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant имеет список модов, которые он считает несовместимыми, но у нас нет доказательств этому, и ошибка отображается только на английском языке. Если вы хотите играть с этими модами, вы можете отредактировать файл <code>config/crash_assistant/config.toml</code> и изменить <code>enabled = true</code> в разделе [compatibility] на <code>enabled = false</code>.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant может помечать моды как несовместимые, но иногда это неверно, и сообщение об ошибке доступно только на одном языке. Если вы хотите использовать эти моды, вы можете отредактировать файл <code>config/crash_assistant/problematic_mods_config.json</code> и изменить значение <code>should_crash_on_startup</code> с <code>true</code> на <code>false</code>.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Ошибка: Мод '" + modId + "' требует мод '"
				+ dependencia + "'. В настоящее время, " + actual + "." + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Ошибка: Моду '" + modId
				+ "' требуется версия '" + requerido + "' или выше мода '" + dependencia
				+ "', но этот мод не установлен." + "</span>";
	}

	// В классе MonitorDePID.idioma (добавить этот метод)
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Ошибка: Мод '" + modId
				+ "' несовместим с текущей версией '" + dependencia + "'. "
				+ "Вы должны удалить мод 'Iris/Oculus & GeckoLib Compat', так как он несовместим с Superb Warfare и не работает с последней версией GeckoLib. "
				+ "Текущая версия: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "Ошибка: Не удалось выполнить задачу для класса '" + clase + "'. "
				+ "Эта ошибка часто возникает с модами, которые несовместимы друг с другом или имеют конфликты с другими установленными модами.";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "Сбои при выполнении задач";
	}

	public String recomendacion_fallos_ejecucion() {
		return "Такой тип ошибки обычно возникает из-за несовместимости между модами. "
				+ "Особенно распространено с модами, которые неправильно работают с ConnectorMod.";
	}

	public String info_clase_problematica() {
		return "Проблемный класс:";
	}

	public String ver_en_log() {
		return "Просмотреть в логе";
	}

	public String no_se_encontraron_clases_problema() {
		return "Не найдено конкретных классов с проблемами выполнения.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружен критический конфликт между OptiFine и Entity Model Features (EMF). "
				+ "Эти моды несовместимы и вызывают сбой внедрения, который мешает запуску игры." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "Конфликт между OptiFine и Entity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "Удалите либо OptiFine, либо Entity Model Features, так как они несовместимы друг с другом.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружен критический конфликт между OptiFine и Fusion. "
				+ "Эти моды несовместимы и вызывают сбой внедрения, который мешает запуску игры." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "Конфликт между OptiFine и Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "Удалите либо OptiFine, либо Fusion, так как они несовместимы друг с другом.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel (требуется для Create) нуждается в Sodium версии 0.6.0-beta.2 или выше. Rubidium — это 0.5.3. "
				+ "Рассмотрите возможность использования <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> в качестве альтернативы."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "Конфликт Flywheel и версии Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "Обновите Sodium до версии 0.6.0-beta.2 или выше, либо установите <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> в качестве совместимой альтернативы.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружен критический конфликт между OptiFine и Epic Fight. "
				+ "Эти моды несовместимы и вызывают сбой внедрения, который мешает запуску игры." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "Конфликт между OptiFine и Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "Удалите либо OptiFine, либо Epic Fight, так как они несовместимы друг с другом.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружен критический конфликт между OptiFine и Rubidium. "
				+ "Эти моды несовместимы и вызывают сбой внедрения, препятствующий запуску игры." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "Конфликт OptiFine и Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "Удалите OptiFine или Rubidium, так как они несовместимы друг с другом.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam пытается загрузиться на выделенном сервере, но совместим только с клиентом. "
				+ "Удалите FreeCam с сервера или убедитесь, что он установлен только на клиенте." + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam на выделенном сервере";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "Удалите FreeCam с выделенного сервера, так как он должен быть установлен только на клиенте.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) пытается загрузиться на выделенном сервере, но совместим только с клиентом. "
				+ "Удалите ETF с сервера или убедитесь, что он установлен только на клиенте." + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features на выделенном сервере";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "Удалите Entity Texture Features с выделенного сервера, так как он должен быть установлен только на клиенте.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Вы должны принять EULA Minecraft, чтобы запустить сервер. "
				+ "Отредактируйте файл eula.txt и измените 'eula=false' на 'eula=true'." + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "EULA Minecraft не принята";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "Отредактируйте файл eula.txt в папке сервера и измените 'eula=false' на 'eula=true'.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine пытается загрузиться на выделенном сервере, но совместим только с клиентом. "
				+ "Удалите OptiFine с сервера или убедитесь, что он установлен только на клиенте." + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine на выделенном сервере";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "Удалите OptiFine с выделенного сервера, так как он должен быть установлен только на клиенте. Эта проблема также часто возникает из-за установки версии OptiFine, предназначенной для неправильной версии Minecraft, или из-за конфликта между другим модом и OptiFine.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks неправильно помечен для 1.20.1, но использует методы из 1.21.1. "
				+ "Мод пытается использовать ResourceLocation.fromNamespaceAndPath, которого не существует в 1.20.1."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "Ошибка версии Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "Убедитесь, что вы используете правильную версию Iron's Spellbooks, совместимую с вашей версией Minecraft.";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружен критический конфликт между OptiFine и Embeddium. "
				+ "Эти моды несовместимы и вызывают сбой внедрения, препятствующий запуску игры." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "Конфликт OptiFine и Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "Удалите OptiFine или Embeddium, так как они несовместимы друг с другом.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Это часто происходит из-за конфликтующих модов генерации мира, особенно Terralinth, AmplifiedNether, Nullscape и Incendium, а также других модов генерации мира. Возможно, вам также нужно установить отсутствующий мод.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable пытается загрузиться на выделенном сервере, но совместим только с клиентом. "
				+ "Удалите Controllable с сервера или убедитесь, что он установлен только на клиенте." + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable на выделенном сервере";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "Удалите Controllable с выделенного сервера, так как он должен быть установлен только на клиенте.";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries вызывает ошибку, которая мешает загрузке сервера. "
				+ "Мод имеет проблемы с реестром поведения огня, что приводит к сбою во время загрузки datapack'ов."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries мешает загрузке сервера";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "Попробуйте обновить Supplementaries до последней версии или временно отключите его, чтобы разрешить загрузку сервера.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) обнаружил проблему с отсутствующими модулями Jackson. "
				+ "Некоторые моды, такие как Valkyrien Skies, могут вызывать эту ошибку, если не включают все необходимые зависимости."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "Отсутствует модуль Jackson в Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "Удалите Groovy Modloader и связанные моды, такие как Valkyrien Skies, которые могут вызывать конфликты зависимостей.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Every Compat обнаружил недопустимое имя деревянного блока. "
				+ "Every Compat обычно вызывает множество проблем. Не используйте его!" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "Недопустимое имя в Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "Проверьте ресурспаки или моды, использующие Every Compat, так как они могут содержать недопустимые имена блоков.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружен код ошибки (-1073741819), который может быть вызван оверлеями, такими как GameCaster от Razer, Discord, OBS Studio, или проблемами с драйверами NVIDIA."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "Код ошибки -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "Попробуйте отключить оверлеи, такие как GameCaster, Discord или OBS Studio, и убедитесь, что драйверы NVIDIA обновлены.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips требует Immersive Messages в качестве зависимости, но она не установлена. "
				+ "Установите Immersive Messages, чтобы Immersive Tooltips работал корректно." + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips без зависимости";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "Установите Immersive Messages, так как это необходимая зависимость для Immersive Tooltips.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins имеет проблему совместимости с Apoli Mod: ItemStack не может быть приведён (cast) к EntityLinkedItemStack. "
				+ "Это часто встречается в версиях новее 6.6.0. Рассмотрите возможность использования более ранней версии или переключения между версиями для Fabric и Forge."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "Ошибка приведения типов (cast) в Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "Используйте Medieval Origins версии 6.6.0 или более раннюю, либо попробуйте переключиться между версиями мода для Fabric и Forge.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether вызывает ошибку из-за отсутствующего Registry Object в MusicManager. "
				+ "Эта проблема связана с mixin MusicManager из Reign of Nether." + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "Ошибка MusicManager в Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "Попробуйте обновить Reign of Nether или временно удалить его, чтобы устранить ошибку.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel поддерживает сервер YSM только на Linux или Android. "
				+ "Эта проблема была исправлена в более новых версиях начиная с 23 ноября 2025 года на Modrinth."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel несовместим с Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "Обновите YesSteveModel до более новой версии с Modrinth, так как проблема была исправлена после 23 ноября.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружен критический конфликт между Moving Elevators и OptiFine. "
				+ "Эти моды несовместимы и вызывают сбой внедрения, препятствующий запуску игры." + "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "Конфликт Moving Elevators и OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "Удалите OptiFine или Moving Elevators, так как они несовместимы друг с другом.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружен критический конфликт между Fabric API (fabric-resource-loader-v0) и OptiFine. "
				+ "Эти моды несовместимы и вызывают сбой внедрения, препятствующий запуску игры." + "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "Конфликт Fabric API и OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "Удалите OptiFine или обновите Fabric API до совместимой версии.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "В одном из модов обнаружен неисправный ITransformationService, который невозможно создать: "
				+ claseProveedor + ". " + "Этот мод необходимо удалить, чтобы игра смогла загрузиться." + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "Неисправный ITransformationService";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "Удалите мод, содержащий класс " + claseProveedor
				+ ", так как он содержит неисправный ITransformationService.";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Один из модов содержит недопустимую спецификацию версии. "
				+ "Версия должна быть заключена в квадратные скобки. "
				+ "Вы можете использовать утилиту grep/greprf на боковой панели для поиска версии </span>" + version
				+ "<span style='color:#" + config.obtenerColorError()
				+ "'>, чтобы определить, в каком моде проблема.</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "Недопустимая версия в моде";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "Используйте утилиту grep/greprf на боковой панели, чтобы найти проблемную версию и определить, в каком моде она находится.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружена ошибка stack smashing, которая завершила процесс. "
				+ "Это может быть вызвано проблемами с Early Window в Forge/NeoForge/PillowMC или с LWJGL 3.2.2 и новее."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "Обнаружен Stack Smashing";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "Проверьте настройки Early Window и рассмотрите возможность использования другой версии LWJGL или проверки модов, связанных с ранним окном.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore предназначен только для определённого модпака и не должен использоваться в обычных установках, так как вызывает проблему."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore с несовместимой версией Java";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "Удалите GregTechEasyCore, так как он предназначен только для определённого модпака и несовместим с вашей обычной установкой.";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружен конфликт между MoniLabs и Connector Extras, связанный с изменениями в KubeJS. "
				+ "Эти моды несовместимы в своих модификациях KubeJS." + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "Конфликт MoniLabs и Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "Попробуйте удалить один из модов (MoniLabs или Connector Extras), так как их изменения в KubeJS конфликтуют.";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris требует Distant Horizons [2.0.4] или DH API версии [1.1.0] или новее. "
				+ "Ознакомьтесь с руководством по совместимости по адресу https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e, чтобы устранить проблему."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Совместимость Iris и Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "Ознакомьтесь с руководством по совместимости по адресу https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e и обновите Iris и Distant Horizons до совместимых версий.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Отсутствуют классы Minecraft. Возможные причины:</b>" + "<ul>"
				+ "<li>У вас установлены моды для других версий игры. Вы можете воспользоваться <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a>, чтобы проверить, существует ли класс в вашей версии.</li>"
				+ "<li>У вас повреждённая установка Minecraft (часто встречается при использовании CurseForge App, ModrinthApp/Theseus/Astralrinth и других лаунчеров модпаков). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Смотрите руководство</a> по устранению проблем с CurseForge.</li>"
				+ "<li>У вас неисправный coremod (при сбое coremod может блокировать загрузку класса).</li>" + "</ul>"
				+ "<p>Примечание: вы можете использовать инструмент <b>grepr/fgrepr</b> на боковой панели, чтобы найти моды, ссылающиеся на отсутствующие классы, при условии, что в именах используется '/'.</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Отсутствуют классы DangerZone. Возможные причины:</b>" + "<ul>"
				+ "<li>У вас установлены моды для других версий игры.</li>" + "<li>У вас неисправные coremod'ы.</li>"
				+ "<li>У вас повреждён лаунчер или установка.</li>" + "</ul>"
				+ "<p>Примечание: вы можете использовать инструмент <b>grepr/fgrepr</b> на боковой панели, чтобы найти моды, ссылающиеся на отсутствующие классы, при условии, что в именах используется '/'.</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Отсутствуют классы FeatureCreep. Возможные причины:</b>" + "<ul>"
				+ "<li>У вас установлены моды для других версий FeatureCreep (например: ESR против Nightly или v4 против v12).</li>"
				+ "<li>Вы можете установить FeatureCreep с CurseForge или MinecraftStorage.</li>" + "</ul>"
				+ "<p>Примечание: вы можете использовать инструмент <b>grepr/fgrepr</b> на боковой панели, чтобы найти моды, ссылающиеся на отсутствующие классы, при условии, что в именах используется '/'.</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Отсутствуют классы ModLauncher. Возможные причины:</b>" + "<ul>"
				+ "<li>Ваши моды предназначены для другой сборки MinecraftForge, PillowMC или NeoForge (ModLauncher используется с этими загрузчиками).</li>"
				+ "<li>Для одной версии Minecraft существует множество обновлений modloader'ов.</li>"
				+ "<li>У вас повреждённая установка лаунчера (часто встречается при использовании CurseForge App, ModrinthApp/Theseus/Astralrinth и других лаунчеров модпаков). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Смотрите руководство</a> по устранению проблем с CurseForge.</li>"
				+ "</ul>"
				+ "<p>Примечание: вы можете использовать инструмент <b>grepr/fgrepr</b> на боковой панели, чтобы найти моды, ссылающиеся на отсутствующие классы, при условии, что в именах используется '/'.</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Отсутствуют классы Minecraft Forge. Возможные причины:</b>" + "<ul>"
				+ "<li>Ваши моды предназначены для другой сборки MinecraftForge.</li>"
				+ "<li>Для одной версии Minecraft существует множество обновлений modloader'ов.</li>"
				+ "<li>У вас повреждённая установка (часто встречается при использовании CurseForge App, ModrinthApp/Theseus/Astralrinth и других лаунчеров модпаков). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Смотрите руководство</a> по устранению проблем с CurseForge.</li>"
				+ "</ul>"
				+ "<p>Примечание: вы можете использовать инструмент <b>grepr/fgrepr</b> на боковой панели, чтобы найти моды, ссылающиеся на отсутствующие классы, при условии, что в именах используется '/'.</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Отсутствуют классы NeoForge. Возможные причины:</b>" + "<ul>"
				+ "<li>Ваши моды предназначены для другой сборки NeoForge.</li>"
				+ "<li>Для одной версии Minecraft существует множество обновлений modloader'ов.</li>"
				+ "<li>У вас повреждённая установка (часто встречается при использовании CurseForge App, ModrinthApp/Theseus/Astralrinth и других лаунчеров модпаков). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Смотрите руководство</a> по устранению проблем с CurseForge.</li>"
				+ "</ul>"
				+ "<p>Примечание: вы можете использовать инструмент <b>grepr/fgrepr</b> на боковой панели, чтобы найти моды, ссылающиеся на отсутствующие классы, при условии, что в именах используется '/'.</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Отсутствуют классы Fabric Loader. Возможные причины:</b>" + "<ul>"
				+ "<li>Ваши моды предназначены для другой сборки Fabric Loader.</li>"
				+ "<li>Для одной версии Minecraft существует множество обновлений modloader'ов.</li>"
				+ "<li>У вас повреждённая установка (часто встречается при использовании CurseForge App, ModrinthApp/Theseus/Astralrinth и других лаунчеров модпаков). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Смотрите руководство</a> по устранению проблем с CurseForge.</li>"
				+ "<li>Многим модам требуется Fabric API. Установите Fabric API, если это необходимо.</li>" + "</ul>"
				+ "<p>Примечание: вы можете использовать инструмент <b>grepr/fgrepr</b> на боковой панели, чтобы найти моды, ссылающиеся на отсутствующие классы, при условии, что в именах используется '/'.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Отсутствуют классы PillowMC. Возможные причины:</b>" + "<ul>"
				+ "<li>Ваши моды предназначены для другой сборки PillowMC.</li>"
				+ "<li>Для одной версии Minecraft существует множество обновлений modloader'ов.</li>"
				+ "<li>У вас повреждённая установка (часто встречается при использовании CurseForge App, ModrinthApp/Theseus/Astralrinth и других лаунчеров модпаков). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Смотрите руководство</a> по устранению проблем с CurseForge.</li>"
				+ "</ul>"
				+ "<p>Примечание: вы можете использовать инструмент <b>grepr/fgrepr</b> на боковой панели, чтобы найти моды, ссылающиеся на отсутствующие классы, при условии, что в именах используется '/'.</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "У вас установлен мод, который намеренно вызывает лаги. Uranium — это мод для создания лагов. Он не всегда приводит к сбоям, но со временем может вызвать их."
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack помечен как совместимый с 1.19.*, но на самом деле предназначен для 1.20.*, что вызывает ошибку «класс не найден». "
				+ "Мод пытается использовать DamageSources, которых нет в вашей текущей версии Minecraft." + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Ошибка версии Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "Убедитесь, что вы используете правильную версию Falling Attack, совместимую с вашей версией Minecraft.";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>")
				.append("Для использования этой функции необходимо установить CFR (Class File Reader).<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append("В системах Linux, NetBSD или FreeBSD вы можете установить CFR через менеджер пакетов.<br>")
					.append("Найдите пакет здесь: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append("Либо вы можете скачать изменённую версию, используемую FabricMC, по ссылке:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("Сохраните её в следующую папку:<br>").append("<b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>Важно:</b> после установки CFR необходимо перезапустить мод, чтобы он был корректно распознан.")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "Портрет недоступен";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "Не удалось найти класс: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "Декомпилятор CFR – Sakura Riddle (Неофициальный)";
	}

	@Override
	public String cfrClaseActual() {
		return "Текущий класс";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "Портрет Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "Ошибка загрузки портрета";
	}

	public String noticiaLegalCFR() {
		return "Этот графический интерфейс (GUI) для декомпиляции модов предназначен для помощи пользователям в выявлении причин сбоев программного обеспечения. "
				+ "Однако декомпиляция модов может быть необходима, и пользователям следует избегать использования полученного кода для нарушения Федерального закона об авторском праве. "
				+ "Рекомендуется ознакомиться с лицензией соответствующего мода перед использованием любого полученного кода. Кроме того, многие моды официально предоставляют исходный код, "
				+ "который, как правило, чище и проще для понимания, чем декомпилированный. Помните: уважение интеллектуальной собственности и лицензионных условий — основа "
				+ "сообщества разработчиков модов. Федеральный закон об авторском праве Мексики можно посмотреть по ссылке: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Ley Federal de Derechos de Autor (на испанском)</a> "
				+ "и на английском языке здесь: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (English)</a>. "
				+ "Поскольку вы находитесь на CurseForge, мы также предоставляем ссылку на закон США об авторском праве: "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>. "
				+ "Кроме того, пользователям рекомендуется изучить законы, действующие в их стране. "
				+ "Наш GUI предназначен только для простой диагностики; для углублённого анализа рекомендуем использовать форк Enigma от FabricMC, доступный на "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>. Если вам нужно редактировать JAR-файлы для создания патчей при отсутствии исходного кода, используйте Recaf на "
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">его сайте</a>.";
	}

	@Override
	public String botonDescargarCfr() {
		return "Скачать CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "Открыть папку установки";
	}

	@Override
	public String colorFondoPrincipal() {
		return "Основной цвет фона";
	}

	@Override
	public String colorTextoBotonReset() {
		return "Цвет текста кнопки сброса";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "Цвет текста поля поиска";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "Цвет текста выпадающего меню фильтра";
	}

	@Override
	public String colorTextoRenderer() {
		return "Цвет текста рендерера";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "Цвет текста оверлея загрузки";
	}

	@Override
	public String colorBorde() {
		return "Цвет границы";
	}

	@Override
	public String colorFondoRetrato() {
		return "Цвет фона в портретном режиме";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "Цвет ссылки для публикации";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "Цвет фона поля публикации";
	}

	@Override
	public String rosaFondo() {
		return "Розовый фон";
	}

	@Override
	public String rosaSuave() {
		return "Нежно-розовый";
	}

	@Override
	public String moradoAcento() {
		return "Акцентный фиолетовый";
	}

	@Override
	public String textoOscuro() {
		return "Тёмный текст";
	}

	@Override
	public String bordeSuave() {
		return "Мягкая граница";
	}

	@Override
	public String fondoCampo() {
		return "Фон поля";
	}

	@Override
	public String fondoVistaPrevia() {
		return "Фон предпросмотра";
	}

	@Override
	public String sintaxisConstructor() {
		return "Цвет синтаксиса: конструктор";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "Цвет синтаксиса: справочное сообщение";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "Цвет синтаксиса: HTML-теги";
	}

	@Override
	public String colorFondoVentana() {
		return "Цвет фона окна";
	}

	@Override
	public String colorPanel() {
		return "Цвет панели";
	}

	@Override
	public String colorBotonTexto() {
		return "Цвет текста кнопки";
	}

	@Override
	public String colorCampo() {
		return "Цвет поля";
	}

	@Override
	public String colorBordeDestacado() {
		return "Цвет выделенной границы";
	}

	@Override
	public String colorSeleccionTexto() {
		return "Цвет фона выделенного текста";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "Цвет выделенного текста";
	}

	@Override
	public String colorEstadoExito() {
		return "Цвет состояния: успех";
	}

	@Override
	public String colorEstadoFallo() {
		return "Цвет состояния: ошибка";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "Цвет состояния: мгновенное";
	}

	@Override
	public String colorResultadoAnadido() {
		return "Цвет добавленного результата";
	}

	@Override
	public String colorResultadoEliminado() {
		return "Цвет удалённого результата";
	}

	@Override
	public String colorBordeScroll() {
		return "Цвет границы полосы прокрутки";
	}

	@Override
	public String colorFondoPanel() {
		return "Цвет фона панели";
	}

	@Override
	public String colorBeigeListas() {
		return "Бежевый списков";
	}

	@Override
	public String colorTextoListas() {
		return "Цвет текста в списках";
	}

	@Override
	public String colorBordeListas() {
		return "Цвет границ списков";
	}

	@Override
	public String colorBotonFondo() {
		return "Цвет фона кнопки";
	}

	@Override
	public String colorBordeBoton() {
		return "Цвет границы кнопки";
	}

	@Override
	public String colorDoradoTexto() {
		return "Золотой цвет текста";
	}

	@Override
	public String colorPila() {
		return "Цвет трассировки стека (stack trace)";
	}

	@Override
	public String colorTextoPanel() {
		return "Цвет текста панели";
	}

	@Override
	public String colorTextoNegro() {
		return "Цвет чёрного текста";
	}

	@Override
	public String colorTextoPrincipal() {
		return "Цвет основного текста";
	}

	@Override
	public String colorFondoResultados() {
		return "Цвет фона результатов";
	}

	@Override
	public String colorEstado() {
		return "Цвет состояния";
	}

	@Override
	public String colorTextoDescripcion() {
		return "Цвет текста описания";
	}

	@Override
	public String colorTextoEstado() {
		return "Цвет текста состояния";
	}

	@Override
	public String colorTextoExtra() {
		return "Цвет дополнительного текста";
	}

	@Override
	public String colorSeparador() {
		return "Цвет разделителя";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "Обнаружена нативная ошибка <code>StubRoutines::SafeFetch32</code>. "
				+ "Эта проблема возникает в macOS с JDK 17.0.9 и исправлена в JDK 17.0.10 и новее. https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "Нативная ошибка SafeFetch32 в JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "Обновите JDK до версии 17.0.10 или выше (например, 17.0.15).";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "Если вы используете лаунчер (MultiMC, Prism Launcher или TLauncher), настройте его на использование более новой версии JDK. "
				+ "Некоторые из них уже включают JDK 17.0.15.";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "Мод Spark также может вызывать эту ошибку. Попробуйте временно отключить его. https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Мод MCEF (Chromium Embedded Framework) вызывает «тихое» зависание.</b>" + "<ul>"
				+ "<li>MCEF инициализируется в конце лога, что обычно означает, что игра зависла во время загрузки.</li>"
				+ "<li>Известно, что этот мод вызывает сбои в Linux, macOS или с определёнными версиями Java.</li>"
				+ "<li>Явная ошибка появляется не всегда, но игра так и не доходит до главного меню.</li>" + "</ul>"
				+ "<p>Если вам не нужна функциональность встроенного браузера (например, веб-карты или встроенные страницы), удалите мод.</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "Проблема инициализации MCEF (мод встроенного браузера)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "Удалите файл мода MCEF (ищите 'mcef' в имени файла) из папки 'mods'.";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "Если он вам действительно нужен, убедитесь, что используете версию, совместимую с вашей операционной системой и версией Minecraft.";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружен конфликт между <b>OptiFine</b> и <b>Iris/Oculus</b>.</b>" + "<ul>"
				+ "<li>OptiFine изменяет рендеринг Minecraft несовместимым образом с Iris или Oculus.</li>"
				+ "<li>Ошибка <code>MixinLevelRenderer failed injection check</code> исходит из <code>mixins.iris.json</code> или <code>mixins.oculus.json</code>.</li>"
				+ "</ul>"
				+ "<p>Эти моды нельзя использовать одновременно. Удалите OptiFine, чтобы использовать шейдеры с Iris или Oculus.</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "Конфликт между OptiFine и Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "Удалите файл OptiFine из папки 'mods'.";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "Используйте Iris или Oculus без OptiFine для современных шейдеров.";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружен конфликт между <b>ModernFix</b> и <b>OptiFine</b>.</b>" + "<ul>"
				+ "<li>ModernFix несовместим с OptiFine, так как ломает функциональность Forge и замедляет запуск.</li>"
				+ "<li>Сам ModernFix предупреждает: <i>\"Use of ModernFix with OptiFine is not supported\"</i>.</li>"
				+ "</ul>" + "<p>Вы должны удалить один из двух модов, чтобы игра работала корректно.</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "Конфликт между ModernFix и OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "Удалите OptiFine или ModernFix из папки 'mods'. Их нельзя использовать одновременно.";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "Если вам нужны оптимизации, рассмотрите использование только OptiFine или замените ModernFix на более лёгкие моды, такие как FerriteCore или EntityCulling.";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Ошибка: недопустимый ключ реестра с запрещёнными символами.</b>" + "<ul>"
				+ "<li><b>Обнаруженный ключ:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>В Minecraft все ключи реестра (теги, рецепты, достижения и т. д.) должны быть <b>строчными</b> и содержать только буквы, цифры, подчёркивания, дефисы и слэши.</li>"
				+ "<li>Эта ошибка обычно вызвана некачественно написанным модом или неисправным datapack'ом.</li>"
				+ "</ul>"
				+ "<p><b>Важный совет:</b> используйте инструмент <b>grepr</b> или <b>fgrepr</b> на боковой панели и включите опцию <b>«Искать в JAR-файлах»</b>, чтобы найти мод, содержащий этот недопустимый ключ.</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "Ключ реестра с заглавными или недопустимыми символами";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "Используйте 'grepr' или 'fgrepr' с опцией «Искать в JAR-файлах», чтобы найти виновный мод.";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "Если не удаётся определить мод, удалите недавно установленные моды, особенно те, что добавляют блоки, предметы или инструменты.";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Ошибка при загрузке мода <b>"
				+ escapeHtml(modNombre) + "</b>.</b>" + "<ul>"
				+ "<li>Мод не смог инициализировать один из своих компонентов (например, меню настроек).</li>"
				+ "<li>Это обычно происходит из-за несовместимости с версией Minecraft, Fabric или другими модами.</li>"
				+ "</ul>" + "<p>Если ошибка сохраняется, удалите или обновите мод <b>" + escapeHtml(modNombre)
				+ "</b>.</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "Ошибка инициализации мода (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "Удалите мод '" + modNombre + "' из папки 'mods'.";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "Обновите мод '" + modNombre + "' до версии, совместимой с вашей установкой.";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружена ошибка, связанная с модом <b>En Garde!</b>.</b>" + "<ul>"
				+ "<li>Этот мод добавляет механики ближнего боя (парирование, блокировка и т. д.).</li>"
				+ "<li>Ошибка обычно возникает из-за несовместимости с другими боевыми модами (например, Epic Fight, DualRiders и др.) или из-за использования неподходящей версии для вашего Minecraft.</li>"
				+ "</ul>"
				+ "<p>Если вы не используете продвинутый бой, рекомендуем удалить En Garde!, чтобы избежать конфликтов.</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "Ошибка в моде En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "Убедитесь, что используете версию En Garde!, совместимую с вашей версией Minecraft и загрузчиком (Fabric/Forge).";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "Если вы используете другие боевые моды (Epic Fight, Caelus и др.), отключите их или удалите En Garde!, чтобы избежать конфликтов.";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружена ошибка, вызванная модом <b>IdleTweaks</b>.</b>" + "<ul>"
				+ "<li>IdleTweaks попытался освободить сетевой канал, который больше не существует (<code>Tried to release unknown channel</code>).</li>"
				+ "<li>Эта ошибка обычно возникает в старых версиях мода или при использовании на неправильно настроенных серверах.</li>"
				+ "</ul>"
				+ "<p>IdleTweaks — это мод для улучшения качества жизни, но он может вызывать нестабильность. Рассмотрите возможность его обновления или удаления.</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "Ошибка IdleTweaks (неизвестный сетевой канал)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "Обновите IdleTweaks до последней версии, совместимой с вашим Minecraft.";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "Удалите IdleTweaks из папки 'mods', если он вам не нужен.";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um erro de autenticação (HTTP 401) ao tentar fazer login no Minecraft.</b>"
				+ "<p>Esse erro <b>raramente é a causa direta da falha</b>, mas indica que você está usando uma conta não autenticada (pirata).</p>"
				+ "<p>Canais oficiais de suporte (projetos corporativos, VTubers, criadores de modpacks, etc.) <b>não podem ajudá-lo</b> se você usar uma cópia pirata, "
				+ "devido a restrições em suas regras de chat, contratos, acordos com Mojang/Microsoft ou políticas de reputação.</p>"
				+ "<p>Essa verificação pode ser <b>desativada nas configurações corporativas</b> do detector. "
				+ "Aviso: a detecção antipirataria <b>não é perfeita</b> e pode ser acionada em ambientes de desenvolvimento, com internet instável ou ao usar launchers modificados.</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>Direitos Miranda se você tentar entrar no suporte mesmo assim:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Minecraft pirata";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "Desativar verificação antipirataria";
	}

	@Override
	public String comprarMC() {
		return "Comprar Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Вы используете лаунчер <code>" + id
				+ "</code>, который <b>отсутствует в списке рекомендованных</b>.</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>Хотя он может работать, нерекомендуемые лаунчеры часто вызывают:</p>" + "<ul>"
				+ "<li>Повреждённые установки модов или приложения.</li>"
				+ "<li>Игра не запускается или зависает без понятной ошибки.</li>"
				+ "<li>Нестандартную структуру папок (затрудняет диагностику).</li>"
				+ "<li>Непредсказуемое поведение с Java, памятью или модами.</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "Для лучшего опыта используйте один из следующих рекомендованных лаунчеров:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Нерекомендуемый лаунчер";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "Переключитесь на лаунчер из рекомендованного списка.";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Вы используете <b>не рекомендуемый лаунчер</b>: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>Не рекомендуемые лаунчеры могут вызывать:</p>" + "<ul>"
				+ "<li>Повреждённые установки приложения или модов.</li>"
				+ "<li>Игра не запускается или завершается без ошибки.</li>"
				+ "<li>Нестандартную организацию файлов (сложно отлаживать).</li>"
				+ "<li>Неопределённость в том, как он управляет модами, Java или памятью.</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "Настоятельно рекомендуется использовать один из следующих лаунчеров:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Не рекомендуемый лаунчер";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "Переключитесь на рекомендуемый лаунчер, чтобы получить поддержку.";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Для этой среды отсутствуют рекомендуемые моды.</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "Отсутствуют рекомендуемые моды";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "Установите рекомендуемые моды для оптимального опыта.";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "В вашей установке обнаружены нерекомендуемые моды.</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "Обнаружены нерекомендуемые моды";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "Удалите нерекомендуемые моды, чтобы избежать проблем.";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Обнаружено несанкционированное вмешательство в критические файлы. Вы либо вручную редактировали файлы, либо используете ненадёжный лаунчер.</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "Обнаружено вмешательство";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "Переустановите оригинальные файлы, чтобы восстановить целостность.";
	}

	@Override
	public String configuracionCorporativa() {
		return "Configurações Corporativas";
	}

	@Override
	public String idiomaRespaldo() {
		return "Idioma de Fallback";
	}

	@Override
	public String buscardorHabilitado() {
		return "Habilitar Buscador";
	}

	@Override
	public String nombreHerramienta() {
		return "Nome da Ferramenta";
	}

	@Override
	public String condenarPirateria() {
		return "Condenar Pirataria";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "Launchers Recomendados";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "Launchers Desaconselhados";
	}

	@Override
	public String modsRecomendados() {
		return "Mods Recomendados";
	}

	@Override
	public String modsDesaconsejados() {
		return "Mods Desaconselhados";
	}

	@Override
	public String antiTamper() {
		return "AntiManipulação";
	}

	@Override
	public String proximamente() {
		return "Em breve";
	}

	@Override
	public String informacion() {
		return "Informação";
	}

	@Override
	public String errorCargandoImagen() {
		return "Erro ao carregar imagem";
	}

	@Override
	public String configuracionBasica() {
		return "Базовые настройки";
	}

	@Override
	public String funcionalidades() {
		return "Функции";
	}

	@Override
	public String derechosMiranda() {
		return "Права Миранды (НАСТОЯТЕЛЬНО рекомендуется)";
	}

	@Override
	public String gestionVerificaciones() {
		return "Управление проверками";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "Название";
	}

	@Override
	public String codigoVerificacion() {
		return "Код";
	}

	@Override
	public String documentacionVerificacion() {
		return "Документация";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "Включённые проверки:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "Отключённые проверки:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "Отключить все некорпоративные";
	}

	@Override
	public String verCodigo() {
		return "Просмотреть код";
	}

	@Override
	public String verDocumentacion() {
		return "Просмотреть документацию";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "Выберите проверку для отключения.";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "Выберите проверку для включения.";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "Отключено %d проверок, не рекомендуемых для корпоративного использования.";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "Нет некорпоративных проверок для отключения.";
	}

	@Override
	public String operacionCompletada() {
		return "Операция завершена";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "Мы скучаем по тебе, Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "Цвет корпоративной проверки";
	}

	@Override
	public String nombreLanzador() {
		return "Название лаунчера";
	}

	@Override
	public String motivo() {
		return "Причина";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Не рекомендуемые лаунчеры";
	}

	@Override
	public String moverADesaconsejados() {
		return "Отметить как нерекомендуемый";
	}

	@Override
	public String moverARecomendados() {
		return "Отметить как рекомендуемый";
	}

	@Override
	public String guardarCambios() {
		return "Сохранить изменения";
	}

	@Override
	public String cancelar() {
		return "Отмена";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "Пожалуйста, выберите лаунчер для перемещения.";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "Изменения успешно сохранены!";
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
		return "Причины";
	}

	@Override
	public String agregarLanzador() {
		return "Добавить лаунчер";
	}

	@Override
	public String quitarLanzador() {
		return "Удалить лаунчер";
	}

	@Override
	public String editarRazones() {
		return "Редактировать причины";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "Выберите лаунчер для удаления.";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "Выберите лаунчер для редактирования.";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "Редактировать причины для " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "Добавить новый язык";
	}

	@Override
	public String aceptar() {
		return "Принять";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "Выберите язык";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "Эти лаунчеры рекомендованы CrashDetector как надёжные.";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "Правильный результат";
	}

	public String modsNoRecomendados() {
		return "Не рекомендуемые моды";
	}

	public String agregarMod() {
		return "Добавить мод";
	}

	public String quitarMod() {
		return "Удалить мод";
	}

	public String modId() {
		return "ID мода / Имя JBoss Modules";
	}

	public String rutaMod() {
		return "Путь / файл мода";
	}

	public String errorDebeIndicarMod() {
		return "Необходимо указать хотя бы modid или путь к моду.";
	}

	public String modsNoRecomendadosAviso() {
		return "Здесь вы можете зарегистрировать нерекомендуемые моды, чтобы CrashDetector обнаруживал их при установке.";
	}

	@Override
	public String anularNormal() {
		return "Отключить обычный режим";
	}

	@Override
	public String anularNormalDescripcion() {
		return "CrashDetector должен предупреждать, даже если сбой не произошёл.";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "Зарегистрируйте моды, рекомендованные CrashDetector. Если они отсутствуют, CrashDetector может предупредить вас.";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "Если вы решите включить предупреждение о пиратстве, рекомендуется определить здесь "
				+ "права лица, запрашивающего поддержку, в качестве превентивной меры.\n\n"

				+ "Вопреки распространённому мнению, многие популярные сообщества и каналы поддержки "
				+ "НЕ требуют включения антипиратских предупреждений для оказания помощи. Однако "
				+ "фиксация этих прав может быть полезна, если пользователь всё же обратится в канал поддержки.\n\n"

				+ "Вы можете ориентироваться на официальные документы, такие как «Памятка основных прав задержанного» "
				+ "в Мексике:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "А также на сопоставимые правовые принципы, применяемые в других странах, включая "
				+ "США, Российскую Федерацию, Китайскую Народную Республику, Исламскую Республику "
				+ "Иран и Корейскую Народно-Демократическую Республику.\n\n"

				+ "Примеры прав, которые можно включить:\n"
				+ "• Право не предоставлять ненужную информацию для поддержки, такую как используемый лаунчер, "
				+ "имя пользователя или UUID.\n" + "• Право не свидетельствовать против себя.\n"
				+ "• Право отказаться отвечать на вопросы, не относящиеся к решению проблемы.\n"
				+ "• Право получать руководство внутри чата.\n"
				+ "• Право использовать встроенную в CrashDetector функцию анонимизации логов.\n\n"

				+ "Этот текст поддерживает HTML-контент.";
	}

	@Override
	public String editar() {
		return "Редактировать";
	}

	@Override
	public String advertenciaHashLento() {
		return "Предупреждение: добавление множества больших файлов может привести к тому, что проверка займёт несколько минут. CrashDetector должен вычислить хеш каждого файла перед продолжением. Рекомендуется защищать только строго необходимые файлы.";
	}

	@Override
	public String agregarArchivo() {
		return "Добавить файл";
	}

	@Override
	public String agregarCarpeta() {
		return "Добавить папку";
	}

	@Override
	public String quitar() {
		return "Удалить";
	}

	@Override
	public String rutaArchivo() {
		return "Путь к файлу";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "Выбранный путь находится вне текущей директории игры. Разрешены только файлы и папки внутри текущей директории или её подкаталогов.";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>"
				+ "Мнения и комментарии Sylent Bell не обязательно совпадают с нашими; "
				+ "мы просто подумали, что будет забавно разместить её здесь. CrashDetector является светским."
				+ "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Мод GML (Groovy ModLoader) требует этих изменений и является наиболее частой причиной данной проблемы.</b>";
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
				+ "Обнаружено использование <i> Flywheel</i>.</b>"
				+ "<p><b>Independent Flywheel устарел (deprecated)</b> и не должен использоваться в современных версиях.</p>"
				+ "<p>Текущие версии <b>Create</b> <b>уже включают Flywheel</b>, поэтому его отдельная установка "
				+ "вызывает конфликты совместимости и ошибки загрузки.</p>"
				+ "<p>Некоторые моды, явно зависящие от  Flywheel, могут "
				+ "<b>не работать</b> или <b>работать нестабильно</b>. "
				+ "В некоторых продвинутых случаях они могут заработать, если вы "
				+ "<b>вручную отредактируете файл <code>mods.toml</code></b>, чтобы настроить диапазоны версий, "
				+ "хотя это <b>не рекомендуется</b>.</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>Обнаруженные моды, ссылающиеся на Flywheel:</b></p>" + "<ul>" + listaMods.toString()
								+ "</ul>")
				+ "<p>Рекомендуемое решение — <b>удалить  Flywheel</b> и использовать только "
				+ "версию, встроенную в Create.</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Независимый Flywheel";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Foi detectado um erro relacionado ao mod <i>Floral Enchantments</i>.</b>"
				+ "<p>O crash é causado por uma falha interna do mod ao manipular dados do jogo, "
				+ "o que gera uma <b>NullPointerException</b> durante a execução.</p>"
				+ "<p>Esse problema geralmente é resolvido atualizando ou removendo o mod.</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "Erro do Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "У вас установлены обе версии MixinExtras: NeoForge и обычная. Если вы используете MinecraftForge, вы можете установить исправление по <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>этой ссылке</a>.</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена ошибка в тенях ландшафта при использовании шейдеров (Iris).</b>"
				+ "<p>Проблема возникает во время рендеринга ландшафта.</p>"
				+ "<p>Рекомендуется <b>попробовать запустить игру без шейдеров</b> или снизить графическое качество, "
				+ "особенно в настройках <b>Ultra</b>.</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "Тени ландшафта (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружен чрезмерно долгий тик сервера.</b>"
				+ "<p>Это означает, что игра зависла на слишком долгое время в одном тике.</p>"
				+ "<p>Рекомендуется <b>проверить thread dump</b>, сгенерированный в логе, чтобы определить причину.</p>"
				+ "<p><b>Анализ стека вызовов (Stack Trace)</b> поможет вам найти источник зависания.</p>"
				+ "<p>Кроме того, кнопка <b>Просмотр в логе</b> выделит красным цветом возможные проблемные моды, "
				+ "а также записи, окружённые <code>$modid$</code>, которые обычно указывают на источник проблемы. Для анализа в реальном времени рекомендуем использовать CPU-сэмплер в VisualVM. Убедитесь, что ваш сервер или компьютер достаточно мощный для запуска используемых модов — возможно, все они работают корректно, но их просто слишком много.</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Долгий тик сервера";
	}

	@Override
	public String tituloLFPDPPP() {
		return "Федеральный закон о защите персональных данных, находящихся в распоряжении частных лиц";
	}

	@Override
	public String aceptarPermanentemente() {
		return "Принять навсегда";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "Корейский перевод содержит южнокорейский сленг, который необходимо избегать в соответствии с законом. "
				+ "Использование иностранной лексики, особенно из Юга, строго запрещено "
				+ "в соответствии с Законом о защите культурного языка Пхеньяна.";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "Для получения дополнительной информации ознакомьтесь с официальным текстом закона: "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>Закон о защите культурного языка Пхеньяна</a>";
	}

	public String leerLeyCompleta() {
		return "Прочитать полный текст закона";
	}

	public String errorAbriendoEnlace() {
		return "Ошибка при открытии ссылки";
	}

	public String actaProteccionIdiomaCultural() {
		return "Закон о защите культурного языка Пхеньяна";
	}

	@Override
	public String canarioTitulo() {
		return "Канарейка судебного приказа";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — Монитор наблюдения";
	}

	@Override
	public String revisar() {
		return "Проверить";
	}

	@Override
	public String cerrar() {
		return "Закрыть";
	}

	@Override
	public String canarioTodoSeguro() {
		return "Все службы сообщают о безопасном состоянии.";
	}

	@Override
	public String canarioComprometido(int c) {
		return "ВНИМАНИЕ: " + c + " служб(а/ы) сообщают о небезопасном состоянии.";
	}

	@Override
	public String colorAlerta() {
		return "Цвет оповещения";
	}

	public String opcionesMunidiales() {
		return "Opções Munidial";
	}

	public String consentimientoLFPDPPP() {
		return "Consentimento LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "Включить передачу токена доступа в Handoff для ReLauncher (не рекомендуется).";
	}

	public String consolaDesarrollo() {
		return "Console de desenvolvimento";
	}

	public String mundial() {
		return "Глобальный";
	}

	public String ningun() {
		return "Nenhum";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "Консоль разработчика";
	}

	public String bajar() {
		return "Скачать";
	}

	public String logsSoporte() {
		return "Логи поддержки";
	}

	public String detenerProceso() {
		return "Остановить процесс";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "Копировать выделенное";
	}

	public String seleccionarTodo() {
		return "Выделить всё";
	}

	public String copiarTodo() {
		return "Копировать всё";
	}

	public String guardarTodoComoArchivo() {
		return "Сохранить всё как файл";
	}

	public String obtenerEnlaceSoporte() {
		return "Получить ссылку поддержки";
	}

	public String borrarTodo() {
		return "Очистить всё";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "Цвет фона консоли";
	}

	public String colorTextoConsola() {
		return "Цвет текста консоли";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "Согласие подтверждено.\nИнтеграция для обмена логами будет реализована здесь.";
	}

	@Override
	public String usarSakuraOriginal() {
		return "Использовать оригинальное изображение Sakura Riddle";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "«Канарейка судебного ордера» (warrant canary) — это механизм прозрачности.\n\n"
				+ "Пока это сообщение существует и службы отображаются как безопасные, "
				+ "это означает, что проект НЕ получал секретных судебных постановлений, "
				+ "требований о цензуре или законных запросов на наблюдение.\n\n"
				+ "Если какая-либо канарейка исчезнет или будет помечена как небезопасная, "
				+ "это укажет на изменение правового статуса.\n\n"
				+ "Эта панель проверяет все зарегистрированные в системе канарейки и отображает "
				+ "их текущий статус.\n\n" + "Нажмите «Проверить», чтобы обновить статусы.";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "Сбросить все параметры к значениям по умолчанию?";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "Нет настроек";
	}

	@Override
	public String seleccionaColor() {
		return "Выбрать цвет";
	}

	@Override
	public String botonMostrarGUI() {
		return "Показать GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "Сохранить всё";
	}

	@Override
	public String botonRestablecerTodo() {
		return "Сбросить всё";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms не загружен";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена ошибка при обращении к API LuckPerms.</b>"
				+ "<p>Сообщение указывает, что <b>LuckPerms не был загружен</b> в момент, когда другой плагин попытался его использовать.</p>"
				+ "<p><b>Возможные причины:</b></p>" + "<ul>"
				+ "<li>Плагин <b>LuckPerms не установлен</b> или <b>не смог запуститься</b>.</li>"
				+ "<li>Другой плагин пытается получить доступ к LuckPerms <b>слишком рано</b> или <b>некорректно</b>.</li>"
				+ "</ul>" + "<p>Рекомендуется <b>проверить консоль</b> по ссылке, чтобы определить "
				+ "плагин, вызывающий LuckPerms, и убедиться в его совместимости.</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Шейдерпак Iris не загружен";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "неизвестен" : shaderpack;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена ошибка при загрузке шейдерпака через Iris/Oculus.</b>"
				+ "<p><b>Затронутый шейдерпак:</b> " + nombre + "</p>"
				+ "<p>Minecraft не смог открыть файл шейдерпака (FileSystemNotFoundException).</p>"
				+ "<p><b>Возможные решения:</b></p>" + "<ul>"
				+ "<li>Убедитесь, что шейдерпак правильно установлен в папку <b>shaderpacks</b>.</li>"
				+ "<li>Повторно скачайте шейдерпак — файл может быть повреждён.</li>"
				+ "<li>Если проблема сохраняется, удалите файл <b>config/iris.properties</b>, чтобы сбросить настройки Iris.</li>"
				+ "</ul>" + "<p>После внесения изменений перезапустите игру.</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "Не удалось записать файл конфигурации";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "неизвестен" : ruta;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Произошла ошибка при сохранении файла конфигурации.</b>" + "<p><b>Затронутый файл:</b> " + archivo
				+ "</p>"
				+ "<p>Minecraft не смог записать файл с использованием атомической записи (REPLACE_ATOMIC).</p>"
				+ "<p><b>Это обычно происходит из-за:</b></p>" + "<ul>"
				+ "<li>Неправильных прав доступа к папке или файлу.</li>"
				+ "<li>Файл помечен как «только для чтения».</li>"
				+ "<li>Другая программа (антивирус, резервное копирование, редактор) блокирует файл.</li>" + "</ul>"
				+ "<p><b>Рекомендации:</b></p>" + "<ul>" + "<li>Убедитесь, что у вас есть права на запись в папку.</li>"
				+ "<li>Снимите атрибут «только для чтения» с файла.</li>"
				+ "<li>Закройте программы, которые могут использовать этот файл.</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "Доступ запрещён при создании резервной копии";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "неизвестен" : origen;
		String dst = backup == null || backup.isEmpty() ? "неизвестен" : backup;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Произошла ошибка прав доступа при создании резервной копии файла конфигурации.</b>"
				+ "<p><b>Исходный файл:</b> " + src + "</p>" + "<p><b>Резервный файл:</b> " + dst + "</p>"
				+ "<p>Операционная система заблокировала доступ во время сохранения файла.</p>"
				+ "<p><b>Это обычно происходит из-за:</b></p>" + "<ul>" + "<li>Недостаточных прав доступа к папке.</li>"
				+ "<li>Файл помечен как «только для чтения».</li>"
				+ "<li>Другая программа (антивирус, инструмент синхронизации, редактор) использует файл.</li>" + "</ul>"
				+ "<p><b>Рекомендации:</b></p>" + "<ul>" + "<li>Проверьте права доступа к папке <b>config</b>.</li>"
				+ "<li>Закройте программы, которые могут использовать этот файл.</li>"
				+ "<li>Попробуйте запустить лаунчер или Minecraft от имени администратора.</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		return "Включить консоль";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>Инструменты отладки</b><br><br>"
				+ "Здесь вы можете включить расширенные функции для отладки CrashDetector и ваших игр.<br><br>"
				+ "Рекомендуется включить консоль разработчика, чтобы получать подробную информацию, трассировки и диагностику во время анализа.<br><br>"
				+ "Если вам нужно протестировать многопользовательский сервер в онлайн-режиме, может потребоваться разрешить передачу токена доступа процессу CrashDetector через настройки конфиденциальности. "
				+ "Это, как правило, <b>не рекомендуется</b> в других случаях.<br><br>"
				+ "Полная инструкция: <a href='https://example.com'>Ссылка!</a>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "Несовместимость: Simple Clouds и шейдеры";
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена несовместимость между Simple Clouds и шейдерами.</b>"
				+ "<p>Simple Clouds несовместим с модами теней (Iris/Oculus), если установлен Distant Horizons.</p>"
				+ "<p><b>Рекомендуемые варианты:</b></p>" + "<ul>"
				+ "<li>Удалите <b>Simple Clouds</b>, если хотите использовать шейдеры.</li>"
				+ "<li>Либо удалите <b>Iris или Oculus</b>, если предпочитаете оставить Simple Clouds.</li>" + "</ul>"
				+ "<p>Это ограничение исходит от самого мода Simple Clouds и не может быть устранено без изменения его кода.</p>";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "Цвет кнопки боковой панели";
	}

	@Override
	public String profilerTitulo() {
		return "Профайлер";
	}

	@Override
	public String profilerDescripcion() {
		return "Инструмент анализа производительности на основе инструментирования и сэмплирования.";
	}

	@Override
	public String profilerIniciar() {
		return "Запустить";
	}

	@Override
	public String profilerDetener() {
		return "Остановить";
	}

	@Override
	public String profilerLimpiar() {
		return "Очистить";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "Профайлер запущен.";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "Профайлер остановлен.";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "Получена выборка из потока: " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "Периодическая выборка стеков для обнаружения узких мест и взаимоблокировок.";
	}

	@Override
	public String entrarAlJuego() {
		return "Войти в игру";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "Недопустимый путь: запрещённые символы";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена ошибка в системном пути.</b>"
				+ "<p>Minecraft не удалось запустить из-за недопустимых символов в имени папки.</p>"
				+ "<p>Система обнаружила недопустимый символ в пути (например: «:» или другие специальные символы).</p>"
				+ "<p><b>Рекомендуемое решение:</b></p>" + "<ul>"
				+ "<li>Переименуйте папку экземпляра или профиля.</li>"
				+ "<li>Используйте только базовые ASCII-символы (A–Z, a–z, 0–9).</li>"
				+ "<li>Не используйте диакритические знаки, специальные символы, проблемные пробелы и эмодзи.</li>"
				+ "</ul>" + "<p>Допустимый пример: <b>MiInstancia1</b></p>"
				+ "<p>Недопустимый пример: <b>Instancia🔥</b> или <b>Instancia:Mod</b></p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "Сбой: Twilight Forest + драйверы Intel";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена ошибка шейдеров Twilight Forest на GPU Intel.</b>"
				+ "<p>Эта ошибка связана с графическими драйверами Intel при загрузке шейдеров мода Twilight Forest.</p>"
				+ "<p>Сбой происходит внутри драйвера (igxelpicd64) и не является прямой проблемой мода или Minecraft.</p>"
				+ "<p><b>Рекомендуемые решения:</b></p>" + "<ul>"
				+ "<li>Обновите драйверы Intel до последней доступной версии.</li>"
				+ "<li>Попробуйте конкретно версии 31.0.101.8331 или 31.0.101.8247 WHQL, которые, согласно отзывам, не вызывают эту проблему.</li>"
				+ "</ul>" + "<p>Официальное отслеживание проблемы:</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>Примечание:</b> Некоторые старые GPU Intel могут не получать обновления, исправляющие эту проблему.</p>";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "Forge: не удалось загрузить поставщика языка";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "Неизвестный поставщик" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Forge не удалось загрузить поставщика языка.</b>"
				+ "<p>Произошла ошибка при инициализации IModLanguageProvider.</p>"
				+ "<p><b>Неудавшийся поставщик:</b> " + providerTexto + "</p>"
				+ "<p>Эта проблема обычно возникает, когда:</p>" + "<ul>"
				+ "<li>Отсутствует требуемая зависимость (например, Kotlin for Forge).</li>"
				+ "<li>Версия мода несовместима с вашей версией Forge.</li>" + "<li>Файл мода повреждён.</li>" + "</ul>"
				+ "<p><b>Рекомендуемые решения:</b></p>" + "<ul>" + "<li>Переустановите соответствующий мод.</li>"
				+ "<li>Убедитесь, что все его зависимости установлены.</li>"
				+ "<li>Проверьте, что используете версии, совместимые с вашим текущим Forge.</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "Сбой: Lets Do Compat (перехват RecipeManager)";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружен сбой в Lets Do Compat (interceptApply).</b>"
				+ "<p>Ошибка возникает внутри преобразования метода "
				+ "<b>RecipeManager.interceptApply</b>, выполненного Lets Do Compat.</p>"
				+ "<p>Это обычно указывает на:</p>" + "<ul>"
				+ "<li>Несовместимость между Lets Do Compat и другим модом, изменяющим рецепты.</li>"
				+ "<li>Неправильную версию для вашей версии Minecraft.</li>"
				+ "<li>Конфликт между трансформерами (mixin/coremod).</li>" + "</ul>"
				+ "<p><b>Рекомендуемые решения:</b></p>" + "<ul>"
				+ "<li>Попробуйте временно удалить Lets Do Compat, чтобы подтвердить конфликт.</li>" + "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI: сбой в Item Group (несовместимый плагин)";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "Неизвестный плагин"
				: String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "JEI обнаружил сбой при построении группы предметов.</b>"
				+ "<p>Один или несколько плагинов вызвали ошибку во время генерации списка ингредиентов JEI.</p>"
				+ "<p><b>Затронутые группы/плагины:</b> " + listaPlugins + "</p>"
				+ "<p>Эта проблема часто возникает, когда:</p>" + "<ul>"
				+ "<li>Плагин JEI реализован некорректно или устарел.</li>"
				+ "<li>Существует несовместимость с текущей версией JEI.</li>"
				+ "<li>Используется Fabric API, и какой-то мод неправильно регистрирует свой Item Group.</li>" + "</ul>"
				+ "<p><b>Рекомендуемые решения:</b></p>" + "<ul>" + "<li>Обновите JEI и перечисленные моды.</li>"
				+ "<li>Временно удалите затронутые плагины, чтобы подтвердить конфликт.</li>"
				+ "<li>Сообщите об ошибке разработчику соответствующего мода.</li>" + "</ul>"
				+ "<p>Предметы из этих групп не будут отображаться в списке ингредиентов до устранения проблемы.</p>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "Недопустимая версия мода (SemVer)";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "Неизвестна" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "Не удалось найти мод" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена недопустимая версия мода.</b>" + "<p>Версия <b>" + v
				+ "</b> не соответствует допустимому формату SemVer.</p>"
				+ "<p>Ошибка указывает на пустой предрелиз (оканчивается на '+').</p>"
				+ "<p><b>Ответственный мод:</b><br>" + u + "</p>" + "<p><b>Рекомендуемое решение:</b></p>" + "<ul>"
				+ "<li>Отредактируйте файл мода и исправьте версию.</li>"
				+ "<li>Удалите завершающий '+', если за ним не следует метаданных.</li>"
				+ "<li>Обновите мод до исправленной версии.</li>" + "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS: Недопустимый доступ между модулями";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружен недопустимый доступ между модулями (JPMS).</b>"
				+ "<p>Система модулей Java (JPMS) заблокировала доступ между классами.</p>"
				+ "<p><b>Класс, попытавшийся получить доступ:</b><br>" + claseOrigen + " (модуль: " + moduloOrigen
				+ ")</p>" + "<p><b>Заблокированный класс:</b><br>" + claseDestino + " (модуль: " + moduloDestino
				+ ")</p>" + "<p>Эта ошибка возникает, когда мод не объявляет корректно "
				+ "exports или opens в своём module-info.java.</p>" + "<p><b>Возможные причины:</b></p>" + "<ul>"
				+ "<li>Модуль не экспортирует требуемый пакет.</li>"
				+ "<li>Отсутствует директива <b>opens</b> для рефлексии.</li>"
				+ "<li>Мод неправильно настроен для JPMS.</li>" + "</ul>"
				+ "<p>Эту проблему должен исправить разработчик мода.</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin: класс неправильно размещен в пакете mixin";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Класс некорректно размещен в пакете Mixin.</b>"
				+ "<p>Обычный класс был помещён в пакет, объявленный как mixin.</p>"
				+ "<p><b>Конфликтующий класс:</b><br>" + clase + "</p>" + "<p><b>Объявленный пакет mixin:</b><br>"
				+ paquete + "</p>" + "<p><b>Ответственный файл mixins:</b><br>" + archivoMixin + "</p>"
				+ "<p>Обычные классы не должны находиться в пакете, определённом в mixins.json.</p>"
				+ "<p>В этом пакете должны присутствовать только классы, аннотированные как mixin.</p>"
				+ "<p><b>Решение для разработчиков:</b> Переместите обычные классы за пределы пакета mixin "
				+ "или исправьте конфигурацию в файле mixins.json.</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена проблема с драйверами GPU Matrox.</b>"
				+ "<p>Журнал указывает, что сбой произошёл внутри библиотеки драйвера Matrox.</p>"
				+ "<p>Видеокарты Matrox (особенно модели G200/G400, используемые на серверах) "
				+ "не предназначены для современного 3D-рендеринга и могут не поддерживать "
				+ "версии OpenGL, требуемые Minecraft.</p>" + "<p><b>Рекомендуемые решения:</b></p>" + "<ul>"
				+ "<li>Обновите драйвер Matrox до последней доступной версии.</li>"
				+ "<li>Установите официальные драйверы вместо универсальных системных.</li>"
				+ "<li>Если оборудование устарело, используйте видеокарту, совместимую с OpenGL 3.2 или выше.</li>"
				+ "</ul>" + "<p>На серверах такие GPU обычно предназначены только для базового вывода видео "
				+ "и не подходят для 3D-приложений, таких как Minecraft.</p>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod: несовместимая видеокарта";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod не удалось обнаружить совместимую видеокарту.</b>"
				+ "<p>Мод <b>VulkanMod</b> попытался запуститься через Vulkan, но не нашёл видеокарту с надлежащей поддержкой Vulkan.</p>"
				+ "<p>Это обычно происходит, когда:</p>" + "<ul>" + "<li>Видеокарта не поддерживает Vulkan.</li>"
				+ "<li>Драйверы видеокарты устарели или отсутствуют.</li>"
				+ "<li>Используется неправильный графический адаптер (например, интегрированная вместо дискретной видеокарты).</li>"
				+ "</ul>" + "<p><b>Рекомендуемые решения:</b></p>" + "<ul>"
				+ "<li>Обновите драйверы видеокарты до последней версии.</li>"
				+ "<li>Убедитесь, что ваша видеокарта поддерживает Vulkan.</li>"
				+ "<li>Если у вас две видеокарты, принудительно используйте дискретную для Minecraft.</li>"
				+ "<li>Если ваша видеокарта не поддерживает Vulkan, удалите VulkanMod.</li>" + "</ul>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "Недопустимый RenderType для контура";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		String base = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Мод попытался применить контур к несовместимому RenderType.</b>" + "<p>Ошибка:</p>"
				+ "<code>Can't render an outline for this rendertype!</code>";

		if (enchantDetectado) {
			base += "<p><b>В отчёте обнаружен мод enchant-outline / better-enchants.</b></p>"
					+ "<p>Известно, что этот мод вызывает данную проблему в последних версиях Minecraft.</p>"
					+ "<p><b>Рекомендуемое решение:</b> удалите или обновите enchant-outline.</p>";
		} else {
			base += "<p>Эта проблема обычно связана с модами, изменяющими рендеринг "
					+ "(Entity Model Features, Entity Texture Features, Visuality или конфликты с Sodium).</p>"
					+ "<p><b>Рекомендуемое решение:</b> обновляйте или отключайте моды рендеринга по одному.</p>";
		}

		return base;
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – пустой DimensionalInventory";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG попытался сохранить пустой DimensionalInventory.</b>" + "<p>Игра выдала ошибку:</p>"
				+ "<code>Cannot invoke DimensionalInventory.saveInventory(...) because \"d\" is null</code>"
				+ "<p>Это известная ошибка в DivineRPG, связанная с системой инвентаря Vethean.</p>"
				+ "<p><b>Рекомендуемое решение:</b></p>" + "<ul>" + "<li>Откройте файл конфигурации DivineRPG.</li>"
				+ "<li>Установите <code>saferVetheanInventory=true</code></li>"
				+ "<li>Сохраните и перезапустите игру.</li>" + "</ul>"
				+ "<p>Также рекомендуется обновить DivineRPG, если доступна более новая версия.</p>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "Конфликт в Render Pass";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружен конфликт в системе рендеринга.</b>" + "<p>Игра выдала ошибку:</p>"
				+ "<code>Close the existing render pass before performing additional commands</code>"
				+ "<p>Эта ошибка обычно связана с конфликтами между модами рендеринга, такими как "
				+ "Iris, OptiFine, VulkanMod или другими, изменяющими графический конвейер.</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "Внутренний сбой Feather Client";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружен внутренний сбой в Feather Client.</b>" + "<p>Игра выдала ошибку:</p>"
				+ "<code>NoClassDefFoundError: feather/lib/sentry/Sentry</code>"
				+ "<p>Эта ошибка вызвана Feather Client.</p>" + "<p>Feather не рекомендуется, потому что:</p>" + "<ul>"
				+ "<li>Использует проприетарные и изменённые версии популярных модов.</li>"
				+ "<li>Нарушает совместимость со стандартным Fabric.</li>"
				+ "<li>Работает как предсобранный модпак с внутренними изменениями.</li>"
				+ "<li>Часто вызывает конфликты с Sodium и другими модами производительности.</li>" + "</ul>"
				+ "<p>Рекомендуется использовать стандартную установку Fabric вместо Feather.</p>";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "Конфликт Iris + Flywheel (Create 6)";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружен конфликт между Iris и Flywheel в Create 6.</b>" + "<p>Игра выдала ошибку:</p>"
				+ "<code>NoSuchFieldError: TESSELATION_SHADERS</code>"
				+ "<p>Обнаружены внутренние ссылки <code>$irisflw$</code>, "
				+ "что указывает на конфликт между Iris и Flywheel.</p>"
				+ "<p>Iris Flywheel 2.0 для Create 6 официально совместим только с NeoForge.</p>"
				+ "<p>Если вы используете Forge или Fabric, эта комбинация может вызвать данную ошибку.</p>";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "Модель GeckoLib не найдена";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Мод не смог найти модель GeckoLib.</b>" + "<p>Затронутая модель:</p>" + "<code>" + modelo + "</code>"
				+ "<p>Эта ошибка возникает, когда файл <code>.geo.json</code> отсутствует "
				+ "или неправильно настроен внутри мода.</p>" + "<p>Возможные причины:</p>" + "<ul>"
				+ "<li>Модель была удалена, но всё ещё используется.</li>" + "<li>Ошибка в пути к файлу.</li>"
				+ "<li>Файл отсутствует внутри JAR.</li>" + "<li>Несовместимая версия мода.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – отсутствующая анимация";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon попытался воспроизвести несуществующую анимацию.</b>" + "<p>Анимация:</p>" + "<code>"
				+ animacion + "</code>" + "<p>Группа:</p>" + "<code>" + grupo + "</code>"
				+ "<p>Эта ошибка обычно возникает, когда:</p>" + "<ul>"
				+ "<li>Смешиваются несовместимые версии Cobblemon.</li>"
				+ "<li>Аддон не соответствует установленной версии.</li>"
				+ "<li>Отсутствуют внутренние ресурсы или анимации.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "Внутренний сбой Lunar Client";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружен внутренний сбой в Lunar Client.</b>"
				+ "<p>Ошибка исходит от самого Lunar Client, а не от базовой игры или модов.</p>"
				+ "<p>Lunar Client использует внутренние и кастомные модификации, которые могут "
				+ "вызывать несовместимости с модами или определёнными настройками.</p>"
				+ "<p>Рекомендуется протестировать со стандартной установкой Minecraft, "
				+ "чтобы исключить проблемы, вызванные самим клиентом.</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "Недопустимый доступ к методу или полю";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Мод попытался получить доступ к защищённому/приватному методу или полю.</b>"
				+ "<p>Ответственный класс:</p>" + "<code>" + clase + "</code>" + "<p>Доступный член:</p>" + "<code>"
				+ miembro + "</code>" + "<p>Эта ошибка обычно возникает, когда:</p>" + "<ul>"
				+ "<li>Мод был скомпилирован для другой версии Minecraft.</li>"
				+ "<li>Смешаны несовместимые mappings.</li>" + "<li>Мод устарел.</li>"
				+ "<li>Используется неправильный загрузчик (Fabric/Forge/NeoForge).</li>" + "</ul>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "Ошибка загрузки datapack/resourcepack";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Сбой при загрузке datapack или resourcepack.</b>" + "<p>Проблемный файл:</p>" + "<code>" + archivo
				+ "</code>" + "<p>Пакет:</p>" + "<code>" + pack + "</code>"
				+ "<p>Игра не смогла разобрать этот файл, что вызвало ошибки загрузки реестра (registry).</p>"
				+ "<p>Эта проблема обычно вызвана:</p>" + "<ul>" + "<li>Некорректным JSON.</li>"
				+ "<li>Несовместимой версией пакета.</li>" + "<li>Устаревшим пакетом для текущей версии игры.</li>"
				+ "<li>Конфликтом между datapack'ами.</li>" + "</ul>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "Ошибка компиляции шейдера";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Не удалось скомпилировать шейдер.</b>"
				+ "<p>Игра не смогла скомпилировать один из активных шейдеров.</p>"
				+ "<p>Эта проблема обычно связана с Sodium, Iris или несовместимыми шейдерпаками.</p>"
				+ "<p>Рекомендации:</p>" + "<ul>" + "<li>Попробуйте другой шейдер.</li>"
				+ "<li>Временно отключите шейдеры.</li>" + "<li>Обновите драйверы видеокарты.</li>"
				+ "<li>Если проблема сохраняется, попробуйте запустить игру без Sodium.</li>" + "</ul>";
	}

	public String nombreErrorCreacionModelo() {
		return "Ошибка создания или загрузки модели";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>Произошла ошибка при попытке создать или загрузить модель Minecraft.</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>Затронутая модель: <code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>Эта ошибка обычно возникает, когда:</p>");
		sb.append("<ul>");
		sb.append("<li>Мод содержит неправильно настроенную модель.</li>");
		sb.append("<li>Файл JSON модели повреждён или неполон.</li>");
		sb.append("<li>Возникает конфликт между модами, изменяющими модели или рендеринг.</li>");
		sb.append("<li>В ресурспаке или датапаке содержатся несовместимые модели.</li>");
		sb.append("</ul>");
		sb.append("<p>Попробуйте определить, какой мод или ресурспак предоставляет указанную модель.</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>Обнаружена возможная причина:</b></p>");
		sb.append("<p>В журнале обнаружена активность мода <b>Cooler Animations</b>.</p>");
		sb.append(
				"<p>Этот мод изменяет систему анимаций и моделей и в некоторых случаях может вызывать ошибки загрузки моделей.</p>");
		sb.append(
				"<p>Если проблема сохраняется, попробуйте запустить игру без Cooler Animations, чтобы проверить, исчезает ли ошибка.</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "Проблема со Starlight";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена ошибка, связанная со Starlight.</b>"
				+ "<p>Ошибка произошла внутри <code>BlockStarLightEngine.initNibble</code>.</p>"
				+ "<p>Это указывает на сбой в движке освещения мода <b>Starlight</b>.</p>"
				+ "<p>Starlight — это мод, полностью перерабатывающий систему освещения Minecraft, и известно, что он вызывает различные проблемы в определённых модовых средах.</p>"
				+ "<p>Это лишь одна из нескольких известных ошибок, связанных со Starlight.</p>"
				+ "<p>Если проблема сохраняется, попробуйте запустить игру без Starlight.</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "Проблема с AAAParticles / Effekseer";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружен нативный сбой, связанный с Effekseer.</b>"
				+ "<p>Ошибка произошла внутри нативной библиотеки <code>EffekseerNativeForJava</code>.</p>"
				+ "<p>Эта библиотека используется модом <b>AAAParticles</b>, разработанным ChloePrime.</p>"
				+ "<p>Сбои в нативных библиотеках обычно указывают на проблемы в самом моде или его нативных зависимостях.</p>"
				+ "<p>Если проблема сохраняется, попробуйте запустить игру без AAAParticles.</p>";
	}

	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружен нативный сбой в среде выполнения Java (JVM).</b>"
				+ "<p>Эта ошибка возникает внутри самой виртуальной машины Java (например, в <code>jvm.dll</code>, <code>libjvm.so</code> и т.д.) "
				+ "и не обязательно вызвана модом.</p>"
				+ "<p>Хотя в редких случаях она может быть связана с модами, использующими несовместимые нативные библиотеки, "
				+ "<b>гораздо вероятнее, что причина — повреждённая, устаревшая или дефектная JVM</b>.</p>"
				+ "<p>Это особенно часто происходит при использовании старых или неофициальных сборок Java (например, community-сборок без поддержки).</p>"
				+ "<p><b>Мы рекомендуем использовать надёжную и поддерживаемую JVM:</b></p>" + "<ul>"
				+ "<li><b>Red Hat Build of OpenJDK</b> (стабильная, хорошо протестированная, идеальна для Windows/Linux)</li>"
				+ "<li><b>OpenLogic OpenJDK</b> (поддержка мультиплатформенности, включая macOS Intel)</li>"
				+ "<li><b>Azul Zulu</b> (сертифицированная, с бесплатной поддержкой LTS)</li>"
				+ "<li><b>Oracle JDK</b> (официальная, с регулярными обновлениями)</li>" + "</ul>"
				+ "<p>Избегайте неизвестных, кастомных или очень старых сборок — они могут содержать критические ошибки в движке JVM.</p>"
				+ "<p><b>Вы используете Prism Launcher или TLauncher?</b> Настроить собственную JVM очень просто: "
				+ "в Prism Launcher перейдите в <i>Установки</i> → <i>Редактировать экземпляр</i> → <i>Настройки Java</i>; "
				+ "в TLauncher откройте <i>Settings</i> → <i>Java Settings</i> и укажите путь к скачанному JDK/JRE. "
				+ "Вам не нужно менять системную JVM!</p>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "Конфликт между Paranoia и C2ME";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружен конфликт между модами Paranoia и C2ME.</b>"
				+ "<p>Ошибка указывает, что <code>ThreadLocalRandom</code> был вызван из неправильного потока.</p>"
				+ "<p>Это обычно происходит, когда мод <b>Paranoia</b> выполняет код, не являющийся потокобезопасным, в то время как <b>C2ME</b> проводит многопоточные оптимизации.</p>"
				+ "<p>Такой конфликт часто встречается в модах, созданных с помощью MCreator.</p>"
				+ "<p>Если проблема сохраняется, попробуйте запустить игру без Paranoia или отключите C2ME.</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "Отсутствует директория assets Minecraft";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft не удалось найти директорию assets.</b>"
				+ "<p>Лаунчер попытался запустить игру с недопустимым путём к assets.</p>"
				+ "<p>Это означает, что файлы ресурсов игры (assets) отсутствуют или были установлены некорректно.</p>"
				+ "<p>Эта проблема обычно возникает при неправильной установке Minecraft или из-за ошибочной настройки лаунчера.</p>"
				+ "<p>Она также может возникнуть при использовании неофициальных лаунчеров, которые некорректно обрабатывают assets, например FreshCraft.</p>"
				+ "<p>Если проблема сохраняется, попробуйте переустановить модпак или запустите игру через официальный или проверенный лаунчер.</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return "Установить " + dependencia + " версии " + version + " или новее";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return "Заменить " + dependencia + " на версию от " + min + " до " + max;
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "Мод " + mod + " требует " + dependencia + " минимальной версии " + version;
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "Мод " + mod + " требует " + dependencia + " версии " + version;
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {

		return "Мод " + mod + " требует " + dependencia + " версии от " + min + " до " + max + " (текущая: " + actual
				+ ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "Несовместимая версия Cupboard";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружен сбой, вызванный старой версией Cupboard.</b>"
				+ "<p>Ошибка возникает внутри <code>ClientConfigCompat.setupNeoforge</code>, так как "
				+ "<code>ModList.get()</code> возвращает <code>null</code>.</p>"
				+ "<p>Это известная проблема в старых версиях мода <b>Cupboard</b>.</p>"
				+ "<p>Старые версии, такие как <b>3.2</b>, содержат эту ошибку.</p>"
				+ "<p><b>Решение:</b> обновите Cupboard до версии <b>3.5</b> или новее.</p>";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		return "Это потому, что вы используете macOS, и игра пытается использовать OpenGL, который не совместим с последними версиями macOS. "
				+ "Вам нужно использовать версию Minecraft, поддерживающую Metal, или использовать Linux, если у вас Mac на Intel, M1 или M2, но не на M3+ или Neo.";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "Анимация GeckoLib не найдена";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Мод не смог загрузить файл анимации GeckoLib.</b>" + "<p>Затронутый файл:</p>" + "<code>" + archivo
				+ "</code>" + "<p>Эта ошибка возникает, когда файл анимации <code>.json</code> не существует, "
				+ "содержит синтаксические ошибки или путь указан неверно.</p>" + "<p>Возможные причины:</p>" + "<ul>"
				+ "<li>Файл был удален, но все еще ссылается в коде.</li>"
				+ "<li>Синтаксическая ошибка внутри файла JSON.</li>"
				+ "<li>Неверный путь, определенный в реестре мода.</li>"
				+ "<li>Конфликты зависимостей или несовместимая версия.</li>" + "</ul>";
	}

	@Override
	public String nombreAnimacionGeckoInexiste() {
		return "Анимация GeckoLib не найдена";
	}

	@Override
	public String mensajeAnimacionGeckoInexiste(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Мод не смог найти файл анимации GeckoLib.</b>" + "<p>Затронутый файл:</p>" + "<code>" + archivo
				+ "</code>"
				+ "<p>Эта ошибка возникает, когда GeckoLib пытается загрузить анимацию, которая не существует по указанному пути. "
				+ "В отличие от ошибки загрузки (синтаксис), эта ошибка указывает на то, что файл физически отсутствует или путь неверен.</p>"
				+ "<p>Возможные причины:</p>" + "<ul>"
				+ "<li>Файл <code>.json</code> был удалён или не включён в итоговый JAR модификации.</li>"
				+ "<li>Опечатка в пути, определённом в коде (например: 'animations' против 'animaciones').</li>"
				+ "<li>Несоответствие регистра (операционная система сервера — Linux (чувствительна), а разработка велась на Windows (нечувствительна)).</li>"
				+ "<li>Модификация обновлена не полностью или её зависимости нарушены.</li>" + "</ul>";
	}

	@Override
	public String nombreRegistroDuplicadoObjeto() {
		return "Конфликт дублирующей регистрации";
	}

	@Override
	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Основное сообщение: Только описательный текст имеет цвет ошибки
		String mensajeBase = "<span style='color:#" + color
				+ "'>Критический конфликт: Предпринята попытка зарегистрировать объект дважды. " + "Модификации </span>"
				+ mod1 + "<span style='color:#" + color + "'> и </span>" + mod2 + "<span style='color:#" + color
				+ "'> пытаются зарегистрировать один и тот же объект. " + "Конфликтующий объект: </span>" + objeto
				+ "<br><br>";

		// Инструкции по исправлению
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Это обычно происходит, когда две разные модификации добавляют объект с одинаковым именем "
				+ "в одном и том же namespace, или когда в коде одной из модификаций есть ошибка.<br><br>"
				+ "<b>Рекомендуемое решение:</b><br>" + "<ul>"
				+ "<li>Проверьте, является ли одна из модификаций обновлением или форком другой.</li>"
				+ "<li>Попробуйте удалить одну из двух конфликтующих модификаций.</li>"
				+ "<li>Просмотрите файлы конфигурации обеих модификаций, чтобы узнать, можно ли изменить идентификатор (ID) объекта.</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreFalloFabricRenderingAPI() {
		return "Отсутствует Fabric Rendering API";
	}

	@Override
	public String mensajeFalloFabricRenderingAPI() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Основное сообщение
		String mensajeBase = "<span style='color:#" + color
				+ "'>Модификация (обычно Porting Lib или её зависимости) не работает, потому что </span>"
				+ "Fabric Rendering API<span style='color:#" + color + "'> недоступна.</span><br><br>";

		// Инструкции по исправлению (Обновлено для современных версий, где Indium
		// устарел)
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Рекомендуемое решение:</b><br>"
				+ "Сообщение предлагает установить Indium, но эта модификация устарела в современных версиях игры.<br>"
				+ "<ul>"
				+ "<li><b>Обновите Sodium</b> до версии <b>0.6.0</b> или выше (эта версия включает необходимую поддержку).</li>"
				+ "<li>Убедитесь, что у вас установлен <b>Fabric API</b>, если его ещё нет.</li>"
				+ "<li>Если вы используете старую версию игры (1.20.6 или ниже), тогда установите Indium.</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRestriccionesDependenciaNoCumplidas() {
		return "Невыполненные ограничения зависимостей";
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Основное сообщение
		String mensajeBase = "<span style='color:#" + color + "'>Найдено </span>" + cantidad + "<span style='color:#"
				+ color + "'> невыполненных ограничений зависимостей.</span><br><br>";

		// Построение списка конфликтов
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictos != null && !conflictos.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Конфликты обнаружены в следующих файлах:</span><br><ul>");
			for (String[] par : conflictos) {
				String dep = par[0]; // Зависимость
				String jar = par[1]; // Файл JAR
				// Переменная в цвете по умолчанию, фиксированный текст в цвете ошибки
				listaDetalle.append("<li>").append("<span style='color:#").append(color).append("'>Файл: </span>")
						.append(jar).append("<br><span style='color:#").append(color).append("'>Требуется: </span>")
						.append(dep).append("</li>");
			}
			listaDetalle.append("</ul><br>");
		}

		// Инструкции по исправлению
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Это происходит, когда две или более модификации требуют разные и несовместимые версии одной и той же внутренней библиотеки.<br><br>"
				+ "<b>Рекомендуемое решение:</b><br>" + "<ul>"
				+ "<li>Попробуйте обновить или удалить модификации, перечисленные выше, чтобы устранить несовместимость.</li>"
				+ "<li>Если вы не можете найти совместимую версию, можно попробовать вручную отредактировать файл <code>mods.toml</code> внутри JAR-файла модификации (используя архиватор, такой как WinRAR или 7-Zip), чтобы изменить или удалить ограничение версии, хотя это может привести к нестабильности.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Основное сообщение
		String mensajeBase = "<span style='color:#" + color + "'>Найдено </span>" + cantidad + "<span style='color:#"
				+ color + "'> невыполненных ограничений зависимостей.</span><br><br>";

		// Построение списка, сгруппированного по модификации
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictosPorMod != null && !conflictosPorMod.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Задействованные модификации и запрошенные зависимости:</span><br><ul>");

			for (Map.Entry<String, List<String>> entry : conflictosPorMod.entrySet()) {
				String archivo = entry.getKey();
				List<String> dependencias = entry.getValue();

				// Название модификации (цвет по умолчанию)
				listaDetalle.append("<li><b>").append(archivo).append("</b>");

				// Список зависимостей для этой модификации
				listaDetalle.append("<ul>");
				for (String dep : dependencias) {
					// Зависимость (цвет по умолчанию)
					listaDetalle.append("<li>").append(dep).append("</li>");
				}
				listaDetalle.append("</ul></li>");
			}
			listaDetalle.append("</ul><br>");
		} else {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Не удалось определить конкретные файлы из лога.</span><br>");
		}

		// Инструкции по исправлению
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Эта ошибка возникает, когда модификации включают внутренние версии библиотек (JarInJar), которые несовместимы друг с другом.<br><br>"
				+ "<b>Рекомендуемое решение:</b><br>" + "<ul>"
				+ "<li>Просмотрите список выше, чтобы определить, какие модификации запрашивают разные версии одной и той же библиотеки.</li>"
				+ "<li>Попробуйте обновить обе модификации до их последних версий.</li>"
				+ "<li>В крайнем случае, вы можете открыть файл <code>.jar</code> модификации с помощью архиватора (например, WinRAR), отредактировать <code>META-INF/mods.toml</code> и вручную изменить диапазон версий зависимости, хотя это рискованно и может сломать модификацию.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String nombreNeruinaOcultaAdvertencia() {
		return "Neruina препятствует отладке";
	}

	@Override
	public String mensajeNeruinaOcultaAdvertencia() {
		String color = Config.obtenerInstancia().obtenerColorAdvertencia();

		// Основное предупреждение
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Предупреждение:</b> Модификация <b>Neruina</b> не справляется с обработкой ошибки, скрывая истинную причину вылета.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Neruina часто не является необходимой и затрудняет понимание того, что именно не работает. Рекомендуется удалить её для отладки.</span><br><br>";

		// Инструкции по восстановлению
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Инструкции по восстановлению:</b><br>"
				+ "1. **MCForge**: Перейдите в '[nombre_del_mundo]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: Перейдите в 'config/neoforge-server.toml'.<br>"
				+ "   *(Примечание: В локальных играх/Singleplayer миры находятся в папке 'saves')*.<br>"
				+ "3. Измените **removeErroringBlockEntities** и **removeErroringEntities** на **true**.<br><br>"
				+ "<b>Другие варианты:</b><br>"
				+ "- **MCEdit**: Используйте для ручного удаления сущности по указанным координатам.<br>"
				+ "- Если эта ошибка сохраняется, возможно, Neruina работает некорректно и просто генерирует новые ошибки."
				+ "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreApothicAttributeSinDueño() {
		return "Ошибка Apothic Attributes";
	}

	@Override
	public String mensajeApothicAttributeSinDueño(boolean chestCavityDetectado) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Основное сообщение
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Apothic Attributes</b> обнаружила конфликт: <b>AttributeMap</b> был изменён без назначенного владельца.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Это обычно происходит, когда модификация пытается изменить атрибуты сущности (например, здоровье, урон, скорость) "
				+ "в неподходящий момент или неправильным образом.</span><br><br>";

		// Специфическое примечание о Chest Cavity
		String notaChestCavity = "";
		if (chestCavityDetectado) {
			notaChestCavity = "<span style='color:#" + color + "'>"
					+ "<b>В логе обнаружена модификация Chest Cavity.</b> "
					+ "Эта модификация является частой причиной данной конкретной ошибки из-за того, как она обрабатывает атрибуты сущностей.</span><br><br>";
		}

		// Инструкции по исправлению
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Рекомендуемое решение:</b><br>" + "<ul>"
				+ "<li>Если Chest Cavity установлена, попробуйте обновить её или временно удалить, чтобы проверить, является ли она причиной.</li>"
				+ "<li>Проверьте, есть ли другие модификации, изменяющие атрибуты мобов, и попробуйте отключить их.</li>"
				+ "<li>Поиските обновления для <b>Apothic Attributes</b>, так как эта ошибка могла быть исправлена в последних версиях.</li>"
				+ "</ul></span>";

		return mensajeBase + notaChestCavity + instrucciones;
	}

	@Override
	public String nombreErrorPotBlockEntity() {
		return "Ошибка DecoratedPot (Cataclysm)";
	}

	@Override
	public String mensajeErrorPotBlockEntity() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Основное сообщение
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Произошла ошибка несовместимости с <b>DecoratedPotBlockEntity</b>.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Это известная проблема в версии 1.19.2 модификации <b>L_Enders_Cataclysm</b>, "
				+ "где отсутствует реализация, требуемая игрой.</span><br><br>";

		// Решение
		String solucion = "<span style='color:#" + color + "'>" + "<b>Рекомендуемое решение:</b><br>"
				+ "Установите модификацию <b>PotFix (Cataclysm Patch)</b> для исправления этой ошибки.<br>"
				+ "Вы можете скачать её здесь: <a href='https://www.curseforge.com/minecraft/mc-mods/potfix-cataclysm-patch'>CurseForge - PotFix</a>"
				+ "</span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorPreloadingTricks() {
		return "Ошибка Preloading Tricks";
	}

	@Override
	public String mensajeErrorPreloadingTricks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Основное сообщение
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Обнаружен конфликт, вызванный <b>Preloading Tricks</b>.</span><br><br>" + "<span style='color:#"
				+ color + "'>" + "Ошибка <i>ClassCastException: String cannot be cast to ModuleDescriptor</i> "
				+ "указывает на то, что модификация неправильно манипулирует классами модульной системы Java.</span><br><br>";

		// Объяснение и решение
		String explicacion = "<span style='color:#" + color + "'>"
				+ "<b>Preloading Tricks</b> — это модификация, предназначенная в первую очередь для <b>разработчиков</b>. "
				+ "Она выполняет сложные операции изменения классов (mixins) на очень ранней стадии загрузки игры, "
				+ "что может легко нарушить стабильность при наличии других взаимодействий.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>Рекомендуемое решение:</b><br>" + "<ul>"
				+ "<li>Удалите модификацию <b>Preloading Tricks</b>. Обычно она не требуется для игры на публичных серверах или в стабильных сборках.</li>"
				+ "<li>Если вы разработчик и нуждаетесь в ней для тестирования, проверьте конфигурацию вашей среды.</li>"
				+ "</ul></span>";

		return mensajeBase + explicacion;
	}

	@Override
	public String nombreErrorSimpleRadioLexiconfig() {
		return "Несовместимость Simple Radio / Lexiconfig";
	}

	@Override
	public String mensajeErrorSimpleRadioLexiconfig() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Основное сообщение
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Обнаружен конфликт между <b>Simple Radio</b> и <b>Lexiconfig</b>.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Ошибка происходит в процессе 'shelveLexicons', что указывает на бинарную несовместимость между обеими библиотеками.</span><br><br>";

		// Специфическое решение
		String solucion = "<span style='color:#" + color + "'>" + "<b>Известная причина:</b><br>"
				+ "Simple Radio обычно предназначен для старых версий Lexiconfig, тогда как у вас установлена более новая версия.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>Рекомендуемое решение:</b><br>" + "<ul>"
				+ "<li>Попробуйте использовать более старую версию <b>Lexiconfig</b>.</li>"
				+ "<li>Рекомендуется попробовать версию <b>1.3.11</b> или более ранние, которые обычно совместимы с Simple Radio.</li>"
				+ "<li>Если проблема сохраняется, проверьте, доступно ли обновление для Simple Radio.</li>"
				+ "</ul></span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorMobAITweaks() {
		return "Ошибка Mob AI Tweaks";
	}

	@Override
	public String mensajeErrorMobAITweaks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Основное сообщение
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Обнаружена ошибка, связанная с <b>Mob AI Tweaks</b>.</span><br><br>" + "<span style='color:#" + color
				+ "'>" + "Ошибка исходит из Mixin (<code>$mob-ai-tweaks$onSpawned</code>), который вмешивается "
				+ "при появлении сущности (spawnea). Это обычно указывает на конфликт с другой модификацией, "
				+ "которая также изменяет поведение появления мобов.</span><br><br>";

		// Решение
		String solucion = "<span style='color:#" + color + "'><b>Рекомендуемое решение:</b><br>" + "<ul>"
				+ "<li>Попробуйте удалить <b>Mob AI Tweaks</b>, чтобы проверить, исчезнет ли нестабильность.</li>"
				+ "</ul></span>";

		return mensajeBase + solucion;
	}

	public String nombre_verificacion_gpu() {
		return "Проверка GPU (OpenGL / Выбор GPU)";
	}

	public String desactivar_parche_gpu() {
		return "Отключить проверку GPU";
	}

	// ==================== CRASH ====================

	public String gpu_crash_posible() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Верификатор GPU мог стать причиной закрытия игры.</b>";
	}

	public String gpu_crash_causas() {
		return "Проверка началась, но не завершилась. Это обычно указывает на сбой в OpenGL или в графических драйверах.<br><br>"
				+ "Возможные причины:<br>" + "- Устаревшие или нестабильные драйверы<br>" + "- Проблемы с OpenGL<br>"
				+ "- Старые GPU или гибридные конфигурации";
	}

	public String gpu_crash_recomendaciones() {
		return "Рекомендации:<br>" + "- Обновить драйверы GPU<br>"
				+ "- Принудительно использовать дискретную видеокарту<br>"
				+ "- Избегать удалённых или виртуализированных сред";
	}

	// ==================== NO ÓPTIMA ====================

	public String gpu_no_optima() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Игра не использует лучшую доступную видеокарту.</b>";
	}

	public String gpu_no_optima_detalles() {
		return "Это может снизить производительность (низкий FPS), но обычно само по себе не вызывает вылетов.";
	}

	public String gpu_recomendaciones_rendimiento() {
		return "Рекомендации:<br>" + "- Принудительно использовать дискретную видеокарту в панели управления<br>"
				+ "- Настроить Java/Minecraft в режиме высокой производительности";
	}

	// ==================== GENERALES ====================

	public String gpu_nota_precision() {
		return "<b>Примечание:</b> Данная система обнаружения не является 100% точной.";
	}

	public String gpu_consumo_energia() {
		return "Более мощные видеокарты потребляют больше энергии и могут сокращать время работы от батареи на ноутбуках.";
	}

	public String gpu_parche_info() {
		return "Вы можете отключить эту проверку с помощью кнопки быстрого решения.";
	}

	@Override
	public String nombreVerificacionRaptorLake() {
		return "Предупреждение о стабильности CPU Intel 13/14 поколения";
	}

	@Override
	public String advertenciaRaptorLakeTitulo() {
		return "Возможная нестабильность процессора Intel Raptor Lake";
	}

	@Override
	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String targetMicrocode) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Обнаружен процессор " + cpu
				+ " с микрокодом " + microcode + "." + "</b> "
				+ "Процессоры Intel 13-го и 14-го поколений сталкивались с проблемами нестабильности из-за запроса избыточного напряжения, "
				+ "что может сократить срок службы процессора.<br><br>"
				+ "Рекомендуется обновить микрокод или BIOS вашей материнской платы до версии, включающей микрокод <b>"
				+ targetMicrocode + "</b> или новее. "
				+ "<b>Предупреждение:</b> Обновление BIOS сопряжено с рисками, если выполняется неправильно.<br><br>"
				+ "<i>Примечание: Это почти наверняка НЕ является причиной вашего текущего вылета, это всего лишь информационное уведомление о состоянии оборудования.</i>";
	}

	@Override
	public String desactivarVerificacionRaptorLake() {
		return "Больше не предупреждать меня об этом";
	}

	@Override
	public String verArticuloRaptorLake(String fuente) {
		return "Читать статью на " + fuente;
	}

	@Override
	public String tituloMixins() {
		return "Обозреватель Mixins";
	}

	@Override
	public String mixinsBotonLateral() {
		return "Mixins";
	}

	@Override
	public String mixinsRaiz() {
		return "Найденные Mixins";
	}

	@Override
	public String mixinsTodosLosMods() {
		return "Все";
	}

	@Override
	public String mixinsModConMixin() {
		return "Модификация с mixins";
	}

	@Override
	public String mixinsTooltipCombo() {
		return "Фильтровать по модификации, содержащей mixins";
	}

	@Override
	public String mixinsRecargar() {
		return "Перезагрузить";
	}

	@Override
	public String mixinsDescompilarSeleccion() {
		return "Декомпилировать выделенное";
	}

	@Override
	public String mixinsTargets() {
		return "Targets";
	}

	@Override
	public String mixinsTarget() {
		return "Target";
	}

	@Override
	public String mixinsTargetsMetodo() {
		return "Targets метода";
	}

	@Override
	public String mixinsMetodos() {
		return "Методы";
	}

	@Override
	public String mixinsCampos() {
		return "Поля";
	}

	@Override
	public String mixinsCantidad() {
		return "Количество mixins";
	}

	@Override
	public String mixinsDetalleMixin() {
		return "Детали mixin";
	}

	@Override
	public String mixinsDetalleTarget() {
		return "Детали target";
	}

	@Override
	public String mixinsDetalleMetodo() {
		return "Детали метода mixin";
	}

	@Override
	public String mixinsDetalleCampo() {
		return "Детали поля mixin";
	}

	@Override
	public String mixinsDetalleConflicto() {
		return "Детали конфликта";
	}

	@Override
	public String mixinsBuscarConflictos() {
		return "Поиск возможных конфликтов";
	}

	@Override
	public String mixinsResultadosConflictos() {
		return "Результаты конфликтов";
	}

	@Override
	public String mixinsConflictosPosibles() {
		return "Возможные конфликты";
	}

	@Override
	public String mixinsErrorDescompilar() {
		return "Ошибка декомпиляции";
	}

	@Override
	public String mixinsColorPanel() {
		return "Цвет панели mixins";
	}

	@Override
	public String mixinsColorTexto() {
		return "Цвет текста mixins";
	}

	@Override
	public String mixinsColorTextoSuave() {
		return "Цвет вторичного текста mixins";
	}

	@Override
	public String mixinsAyudaUso1() {
		return "Этот инструмент показывает моды с миксинами от SpongePowered и позволяет inspectовать их классы, targets, методы и поля.";
	}

	@Override
	public String mixinsAyudaUso2() {
		return "Используйте верхний селектор для фильтрации по конкретной модификации или отображения всех модов с миксинами.";
	}

	@Override
	public String mixinsAyudaUso3() {
		return "Разверните дерево, чтобы увидеть каждый mixin, его целевые классы, аннотированные методы и shadow поля.";
	}

	@Override
	public String mixinsAyudaUso4() {
		return "Щёлкните правой кнопкой мыши по моду, mixin, target, методу или полю, чтобы найти возможные конфликты с другими миксинами.";
	}

	@Override
	public String mixinsAyudaUso5() {
		return "Вы можете декомпилировать текущий выбор или результат конфликта, чтобы изучить соответствующий код.";
	}

	@Override
	public String mixinsColorComboFondo() {
		return "Цвет фона селектора модов";
	}

	@Override
	public String mixinsColorAreaContenidoFondo() {
		return "Цвет фона панели деталей";
	}

	@Override
	public String mixinsColorSeleccionTexto() {
		return "Цвет выделения текста";
	}

	@Override
	public String mixinsColorSeleccionTextoActivo() {
		return "Цвет выбранного текста";
	}

	@Override
	public String mixinsColorAyudaTexto() {
		return "Цвет справочного текста";
	}

	@Override
	public String mixinsColorArbolFondo() {
		return "Цвет фона дерева";
	}

	@Override
	public String mixinsColorRendererSeleccionTexto() {
		return "Цвет выбранного текста в дереве";
	}

	@Override
	public String mixinsColorRendererSeleccionFondo() {
		return "Цвет выбранного фона в дереве";
	}

	@Override
	public String mixinsColorRendererBordeSeleccion() {
		return "Цвет рамки выделения в дереве";
	}

	@Override
	public String depmapTitulo() {
		return "Карта зависимостей";
	}

	@Override
	public String depmapBotonLateral() {
		return "DepMap";
	}

	@Override
	public String depmapPestanaMapa() {
		return "Карта";
	}

	@Override
	public String depmapPestanaDependientes() {
		return "Зависимые";
	}

	@Override
	public String depmapRecargar() {
		return "Перезагрузить";
	}

	@Override
	public String depmapDescompilarSeleccion() {
		return "Декомпилировать выделенное";
	}

	@Override
	public String depmapVerReferencias() {
		return "Просмотреть ссылки";
	}

	@Override
	public String depmapDependencias() {
		return "Зависимости";
	}

	@Override
	public String depmapDependientes() {
		return "Зависимые";
	}

	@Override
	public String depmapDependiente() {
		return "Зависимый";
	}

	@Override
	public String depmapSinDependencias() {
		return "Нет зависимых";
	}

	@Override
	public String depmapSeleccionarMod() {
		return "Выбрать модификацию";
	}

	@Override
	public String depmapSeleccionarModBase() {
		return "Базовая модификация";
	}

	@Override
	public String depmapSeleccionarDependiente() {
		return "Зависимая модификация";
	}

	@Override
	public String depmapSeleccionarPaquete() {
		return "Пакет";
	}

	@Override
	public String depmapComprobarNoAlineadas() {
		return "Проверить несогласованные";
	}

	@Override
	public String depmapResultadoNoAlineadas() {
		return "Результаты несогласованных зависимостей";
	}

	@Override
	public String depmapClaseInexistente() {
		return "Несуществующий класс";
	}

	@Override
	public String depmapClaseReferenciada() {
		return "Ссылочный класс";
	}

	@Override
	public String depmapOrigen() {
		return "Источник";
	}

	@Override
	public String depmapDestino() {
		return "Назначение";
	}

	@Override
	public String depmapDependenciaDetalle() {
		return "Детали зависимости";
	}

	@Override
	public String depmapReferenciaDetalle() {
		return "Детали ссылки";
	}

	@Override
	public String depmapMetodoOrigen() {
		return "Исходный метод";
	}

	@Override
	public String depmapModBase() {
		return "Базовая модификация";
	}

	@Override
	public String depmapTodos() {
		return "Все";
	}

	@Override
	public String depmapSeleccioneUnMod() {
		return "Выберите модификацию";
	}

	@Override
	public String depmapSeleccioneParametrosNoAlineadas() {
		return "Выберите базовую модификацию, зависимую и пакет";
	}

	@Override
	public String depmapSeleccioneClaseParaDescompilar() {
		return "Выберите ссылку или находку для декомпиляции";
	}

	@Override
	public String depmapErrorDescompilar() {
		return "Ошибка декомпиляции";
	}

	@Override
	public String depmapAyuda1() {
		return "Этот инструмент строит карту зависимостей между модификациями, используя ссылки на классы между ними.";
	}

	@Override
	public String depmapAyuda2() {
		return "Вкладка карты показывает пузырьковый график, связывающий каждую модификацию с используемыми ею зависимостями.";
	}

	@Override
	public String depmapAyuda3() {
		return "Вкладка зависимых сортирует модификации от тех, у кого больше всего зависимых, до тех, у кого их нет.";
	}

	@Override
	public String depmapAyuda4() {
		return "Вы можете проверить конкретные ссылки между модификацией и её зависимостями, а также декомпилировать связанные классы.";
	}

	@Override
	public String depmapAyuda5() {
		return "Проверка несогласованных зависимостей ищет ссылки на несуществующие классы внутри пакета или подпакета базовой модификации.";
	}

	@Override
	public String depmapColorPanel() {
		return "Цвет панелей";
	}

	@Override
	public String depmapColorTexto() {
		return "Цвет основного текста";
	}

	@Override
	public String depmapColorTextoSecundario() {
		return "Цвет вторичного текста";
	}

	@Override
	public String depmapColorAyudaTexto() {
		return "Цвет справочного текста";
	}

	@Override
	public String depmapColorGrafoFondo() {
		return "Цвет фона графика";
	}

	@Override
	public String depmapColorListaFondo() {
		return "Цвет фона списков";
	}

	@Override
	public String depmapColorArbolFondo() {
		return "Цвет фона дерева";
	}

	@Override
	public String depmapColorNodo() {
		return "Цвет узлов графика";
	}

	@Override
	public String depmapColorEnlace() {
		return "Цвет связей графика";
	}

	@Override
	public String depmapColorSeleccion() {
		return "Цвет выделения";
	}

	@Override
	public String depmapColorSeleccionTexto() {
		return "Цвет выбранного текста";
	}

	@Override
	public String nombreProblemaAzureLibAnimaciones() {
		return "Проблема с аддоном AzureLib";
	}

	@Override
	public String mensajeProblemaAzureLibAnimaciones() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена ошибка AzureLib при загрузке анимаций.</b>"
				+ "<p>Игра обнаружила JSON-файл анимации с неверным форматом.</p>"
				+ "<p>Эта проблема обычно вызвана одной из модификаций или аддонов, использующих <b>AzureLib</b>.</p>"
				+ "<p><b>Рекомендация:</b></p>" + "<ul>"
				+ "<li>Используйте <b>DepMap</b> на боковой панели, чтобы найти все аддоны, зависящие от AzureLib.</li>"
				+ "<li>Попробуйте запустить игру без некоторых из этих аддонов, пока не найдёте тот, который вызывает ошибку.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreProblemaDecocraftNatureEssentialPartnerMod() {
		return "Проблема с Decocraft Nature";
	}

	@Override
	public String mensajeProblemaDecocraftNatureEssentialPartnerMod() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена проблема, вызванная Decocraft Nature.</b>"
				+ "<p>Ошибка возникает при инициализации конфигурации mixin "
				+ "<code>com/razz/essentialpartnermod/mixins.json</code>.</p>"
				+ "<p>Эту проблему можно исправить, отредактировав JAR-файл модификации.</p>" + "<p><b>Шаги:</b></p>"
				+ "<ul>"
				+ "<li>Откройте JAR-файл с помощью архиватора, такого как File Roller, Ark, WinRAR, 7-Zip или WinZip.</li>"
				+ "<li>Перейдите в <code>META-INF/MANIFEST.MF</code>.</li>" + "<li>Удалите эту строку:</li>" + "</ul>"
				+ "<code>MixinConfigs: com/razz/essentialpartnermod/mixins.json</code>"
				+ "<p><b>Важно:</b> сохраните единственную пустую строку в конце файла.</p>";
	}

	@Override
	public String mensajeTetraDeserializadorModeloEstatico() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена ошибка в Tetra или одном из его дополнений.</b>"
				+ "<p>Журнал указывает на то, что не удалось найти десериализатор для типа модели, используемого <b>Tetra</b> или одним из его компонентов.</p>"
				+ "<p><b>Рекомендуемые действия:</b></p>" + "<ul>"
				+ "<li>Сначала удалите или отключите <b>дополнения Tetra</b> и попробуйте снова.</li>"
				+ "<li>Если ошибка сохраняется, попробуйте также удалить <b>Tetra</b>.</li>"
				+ "<li>Вы можете попытаться найти дополнения, связанные с Tetra, в <b>DepMap</b>, хотя они не всегда там отображаются.</li>"
				+ "</ul>"
				+ "<p>В некоторых случаях проблема исходит от дополнения, но в других она вызвана самим <b>Tetra</b>.</p>";
	}

	@Override
	public String nombreTetraDeserializadorModeloEstatico() {
		return "Ошибка десериализации модели в Tetra";
	}

	@Override
	public String mensajeSimpleEmotesSetupAnimTail() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена ошибка в Simple Emotes.</b>"
				+ "<p>Журнал содержит строку <b>$simpleemotes$setupAnimTAIL</b>, которая напрямую указывает на мод <b>Simple Emotes</b>.</p>"
				+ "<p><b>Рекомендуемые действия:</b></p>" + "<ul>"
				+ "<li>Удалите или отключите <b>Simple Emotes</b> и попробуйте снова.</li>"
				+ "<li>Если вы используете моды, изменяющие анимации игрока или модели, проверьте наличие возможных несовместимостей с <b>Simple Emotes</b>.</li>"
				+ "<li>Обновите <b>Simple Emotes</b> и любые моды, связанные с анимацией, до совместимых версий.</li>"
				+ "</ul>"
				+ "<p>Эта ошибка обычно указывает на то, что <b>Simple Emotes</b> непосредственно участвует в сбое.</p>";
	}

	@Override
	public String nombreSimpleEmotesSetupAnimTail() {
		return "Ошибка в Simple Emotes";
	}

	@Override
	public String mensajeAdvertenciaSKLauncher() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Предупреждение о SKLauncher.</b>"
				+ "<p>За последние несколько месяцев было зафиксировано несколько случаев <b>повреждения данных</b> и других проблем, связанных с <b>SKLauncher</b>.</p>"
				+ "<p>Это не означает, что SKLauncher всегда является причиной ошибки, но он может способствовать возникновению проблемы.</p>"
				+ "<p><b>Признаки возможного повреждения:</b></p>" + "<ul>"
				+ "<li>Игра закрывается на очень раннем этапе запуска.</li>"
				+ "<li>Игра также вылетает даже <b>без установленных модов</b>.</li>" + "</ul>"
				+ "<p>Если происходит что-либо из перечисленного, попробуйте использовать <b>другой лаунчер</b>, чтобы проверить, исчезнет ли проблема.</p>"
				+ "<p>Список рекомендованных лаунчеров смотрите здесь:</p>"
				+ "<p><a href='https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/docs/ingles/minecraft/Launchers.md'>Посмотреть документацию по лаунчерам</a></p>";
	}

	@Override
	public String nombreAdvertenciaSKLauncher() {
		return "Предупреждение: возможные проблемы с SKLauncher";
	}

	@Override
	public String guardTitulo() {
		return "CD Guard";
	}

	@Override
	public String guardBotonLateral() {
		return "Защита";
	}

	@Override
	public String guardEscanearTodo() {
		return "Сканировать серверы и вредоносное ПО";
	}

	@Override
	public String guardEscanearServidores() {
		return "Поиск серверов";
	}

	@Override
	public String guardEscanearMalware() {
		return "Поиск вредоносного ПО";
	}

	@Override
	public String guardTablaServidores() {
		return "Проблемные серверы";
	}

	@Override
	public String guardTablaMalware() {
		return "Обнаруженное вредоносное ПО";
	}

	@Override
	public String guardColServidor() {
		return "Сервер";
	}

	@Override
	public String guardColDefinicion() {
		return "Определение";
	}

	@Override
	public String guardColMensaje() {
		return "Сообщение";
	}

	@Override
	public String guardColUbicacion() {
		return "Расположение";
	}

	@Override
	public String guardColClase() {
		return "Класс";
	}

	@Override
	public String guardColCfr() {
		return "CFR";
	}

	@Override
	public String guardCfr() {
		return "Декомпилировать";
	}

	@Override
	public String guardCfrTitulo() {
		return "Декомпилированный код";
	}

	@Override
	public String guardDescripcion1() {
		return "Этот инструмент позволяет искать проблемные серверы и потенциальные обнаружения вредоносного ПО в модах.";
	}

	@Override
	public String guardDescripcion2() {
		return "Возможны ложные срабатывания, особенно когда другие определения или сканеры вредоносного ПО работают агрессивно.";
	}

	@Override
	public String guardDescripcion3() {
		return "Проверка серверов использует внешние определения. Если вы их не скачали, вам нужно сделать это в первую очередь.";
	}

	@Override
	public String guardDescripcion4() {
		return "Если у вас уже есть локальные определения, инструмент позволит вам решить, хотите ли вы использовать их или обновить.";
	}

	@Override
	public String guardDescripcion5() {
		return "В таблице вредоносного ПО, если класс доступен, вы можете декомпилировать его с помощью CFR для инспекции.";
	}

	@Override
	public String guardEstadoEscaneandoTodo() {
		return "Сканирование серверов и вредоносного ПО...";
	}

	@Override
	public String guardEstadoEscaneandoServidores() {
		return "Поиск проблемных серверов...";
	}

	@Override
	public String guardEstadoEscaneandoMalware() {
		return "Поиск вредоносного ПО...";
	}

	@Override
	public String guardEstadoListo() {
		return "Готово";
	}

	@Override
	public String guardDefsNoEncontradasTitulo() {
		return "Определения не найдены";
	}

	@Override
	public String guardDefsNoEncontradasMensaje() {
		return "Определения серверов не найдены. Хотите скачать их сейчас?";
	}

	@Override
	public String guardDefsDescargar() {
		return "Скачать";
	}

	@Override
	public String guardDefsCancelar() {
		return "Отмена";
	}

	@Override
	public String guardDefsActualizarTitulo() {
		return "Определения серверов";
	}

	@Override
	public String guardDefsActualizarMensaje() {
		return "Локальные определения уже существуют. Вы хотите использовать их как есть или обновить?";
	}

	@Override
	public String guardDefsUsarLocales() {
		return "Использовать локальные";
	}

	@Override
	public String guardDefsActualizar() {
		return "Обновить";
	}

	@Override
	public String guardFuenteDefinicionesTLauncher() {
		return "Список TLauncher";
	}

	@Override
	public String guardErrorDescompilar() {
		return "Ошибка декомпиляции";
	}

	@Override
	public String guardColorPanel() {
		return "Цвет панели";
	}

	@Override
	public String guardColorTexto() {
		return "Цвет текста";
	}

	@Override
	public String guardColorTextoSecundario() {
		return "Цвет вторичного текста";
	}

	@Override
	public String guardColorTabla() {
		return "Цвет таблиц";
	}

	@Override
	public String guardColorSeleccion() {
		return "Цвет выделения";
	}

	@Override
	public String guardColorSeleccionTexto() {
		return "Цвет выделенного текста";
	}

	@Override
	public String texto_de_boton_compartir_instancia_modpack() {
		return "Поделиться экземпляром/модпаком";
	}

	@Override
	public String popup_compartir_instancia_modpack() {
		return "Функция обмена экземпляром или модпаком еще не реализована.";
	}

	@Override
	public String colorBotonCompartirVerdeOscuro() {
		return "Цвет основной кнопки «Поделиться»";
	}

	@Override
	public String colorBotonCompartirVerdeClaro() {
		return "Цвет кнопки «Поделиться ссылкой»";
	}

	@Override
	public String colorTextoBotonesCompartir() {
		return "Цвет текста кнопок «Поделиться»";
	}

	@Override
	public String compartirInstanciaTitulo() {
		return "Поделиться экземпляром";
	}

	@Override
	public String compartirInstanciaBotonLateral() {
		return "Поделиться экземпляром";
	}

	@Override
	public String compartirInstanciaFormato() {
		return "Формат";
	}

	@Override
	public String compartirInstanciaServicio() {
		return "Сервис загрузки";
	}

	@Override
	public String compartirInstanciaBotonCompartir() {
		return "Упаковать и поделиться";
	}

	@Override
	public String compartirInstanciaBotonRefrescar() {
		return "Обновить";
	}

	@Override
	public String compartirInstanciaEstadoListo() {
		return "Готово";
	}

	@Override
	public String compartirInstanciaEstadoEmpaquetando() {
		return "Упаковка выбранного...";
	}

	@Override
	public String compartirInstanciaEstadoSubiendo() {
		return "Загрузка файла...";
	}

	@Override
	public String compartirInstanciaEstadoError() {
		return "Ошибка";
	}

	@Override
	public String compartirInstanciaCodigo() {
		return "Код";
	}

	@Override
	public String compartirInstanciaEnlace() {
		return "Ссылка";
	}

	@Override
	public String compartirInstanciaMantenerAbierto() {
		return "Вы должны держать приложение открытым, чтобы передача оставалась доступной.";
	}

	@Override
	public String compartirInstanciaSinSeleccion() {
		return "Папки или файлы не выбраны.";
	}

	@Override
	public String compartirInstanciaFormatoNoSoportado() {
		return "Этот формат пока не поддерживается.";
	}

	@Override
	public String compartirInstanciaServicioNoDisponible() {
		return "Выбранный сервис недоступен.";
	}

	@Override
	public String compartirInstanciaSubidaCompleta() {
		return "Передача успешно начата.";
	}

	@Override
	public String compartirInstanciaErrorSubir() {
		return "Не удалось загрузить выбранный файл.";
	}

	@Override
	public String compartirInstanciaColorPanel() {
		return "Цвет панели";
	}

	@Override
	public String compartirInstanciaColorTexto() {
		return "Цвет текста";
	}

	@Override
	public String compartirInstanciaPolitica1() {
		return "Рекомендуемые типы: mods, configs, saves, worlds, datapacks, пакеты ресурсов и файлы настроек. Избегайте включения ненужных личных материалов.";
	}

	@Override
	public String compartirInstanciaPolitica2() {
		return "Расширения могут добавлять свои собственные сервисы загрузки. Встроенные по умолчанию сервисы должны отображаться здесь.";
	}

	@Override
	public String compartirInstanciaPolitica3() {
		return "wormhole.app: до 5 ГиБ как обычная загрузка; от 5 до 10 ГиБ требует держать отправителя открытым. В текущей реализации проекта фактическая интеграция все еще ожидается.";
	}

	@Override
	public String compartirInstanciaPolitica4() {
		return "limewire.com: предназначен как сервис с временным хранением. Пока не поддерживается этой реализацией.";
	}

	@Override
	public String compartirInstanciaPolitica5() {
		return "bittorrent: самый безопасный режим, так как это прямое P2P-распределение без центрального хостинга. Пока не поддерживается этой реализацией.";
	}

	@Override
	public String compartirInstanciaPolitica6() {
		return "По умолчанию выбираются наиболее распространенные папки и файлы экземпляра для облегчения технической поддержки.";
	}

	@Override
	public String compartirInstanciaPolitica7() {
		return "Если вы включите внутреннюю папку CrashDetector, также будут переданы конфигурации, журналы и вспомогательные данные, поэтому вы можете снять выбор, если это не нужно.";
	}

	@Override
	public String malware_fracturiser_detectado() {
		return "Обнаружен возможный Fracturiser. Улики:";
	}

	@Override
	public String malware_info_stealer_detectado() {
		return "Обнаружен возможный похититель информации. Улики:";
	}

	@Override
	public String malware_evidencia_clase_sospechosa() {
		return "Подозрительный класс:";
	}

	@Override
	public String malware_evidencia_archivo_sospechoso() {
		return "Подозрительный файл:";
	}

	@Override
	public String malware_brightsdk_detectado() {
		return "Обнаружен возможный Bright SDK. Улики:";
	}

	@Override
	public String malware_evidencia_paquete_sospechoso() {
		return "Подозрительный пакет:";
	}

	@Override
	public String docsTituloVentana() {
		return "Просмотр документов";
	}

	@Override
	public String docsAyudaDeUso() {
		return "<b>Как использовать эту программу просмотра</b><br>"
				+ "Выберите язык внизу, чтобы увидеть документацию, доступную на этом языке.<br>"
				+ "На левой панели вы можете перемещаться по папкам и документам.<br>"
				+ "При нажатии на документ его содержимое появится справа.<br>"
				+ "Внутренние ссылки с протоколом <b>docs://</b> открывают другие документы в этой же программе просмотра.";
	}

	@Override
	public String docsArbolTitulo() {
		return "Документы";
	}

	@Override
	public String docsVisorTitulo() {
		return "Содержимое";
	}

	@Override
	public String docsNoHayDocumentos() {
		return "Нет документов для этого языка.";
	}

	@Override
	public String docsNoHayMarkdown() {
		return "Файлы Markdown на этом языке не найдены.";
	}

	@Override
	public String docsDocumentoNoEncontrado() {
		return "Запрошенный документ не найден.";
	}

	@Override
	public String docsErrorAlAbrirDocumento() {
		return "Ошибка при открытии документа:";
	}

	@Override
	public String docsCargando() {
		return "Загрузка документов...";
	}

	@Override
	public String docsImagenNoDisponible() {
		return "Иллюстрация недоступна";
	}

	@Override
	public String colorPanelSecundario() {
		return "Цвет вторичной панели";
	}

	@Override
	public String colorTextoSuave() {
		return "Цвет мягкого текста";
	}

	@Override
	public String colorSeleccion() {
		return "Цвет выделения";
	}

	@Override
	public String colorFondoDocumento() {
		return "Цвет фона документа";
	}

	@Override
	public String iaTituloVentana() {
		return "ИИ";
	}

	@Override
	public String iaTituloPrincipal() {
		return "Анализ с помощью ИИ";
	}

	@Override
	public String iaDescripcionTitulo() {
		return "Агент анализа сбоев";
	}

	@Override
	public String iaDescripcionCuerpo() {
		return "Этот инструмент открывает внешнего агента, который может помочь вам проанализировать сбои, ошибки и журналы, связанные с Minecraft.";
	}

	@Override
	public String iaDescripcionUso() {
		return "Чтобы использовать эту систему, откройте ссылку, войдите в аккаунт Baidu, а затем используйте агента для просмотра вашего отчета о сбое или журналов.";
	}

	@Override
	public String iaAvisoCuentaBaidu() {
		return "Для доступа к агенту вам понадобится аккаунт Baidu.";
	}

	@Override
	public String iaCopiarEnlace() {
		return "Копировать ссылку";
	}

	@Override
	public String iaAbrirEnlace() {
		return "Открыть ссылку";
	}

	@Override
	public String iaImagenNoDisponible() {
		return "Изображение недоступно";
	}
	
	@Override
	public String mensajeOculusIrisUnknownShaderVariable() {
	    return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
	            + "Обнаружена возможная ошибка шейдеров с Oculus или Iris.</b>"
	            + "<p>Журнал ошибок содержит как <b>kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal</b>, "
	            + "так и <b>java.lang.RuntimeException: Unknown variable:</b>.</p>"
	            + "<p>Эта комбинация обычно указывает на проблему при вычислении переменной внутри шейдера, "
	            + "часто связанную с <b>Oculus</b>, <b>Iris</b> или используемым <b>набором шейдеров (shader pack)</b>.</p>"
	            + "<p><b>Рекомендуемый порядок действий:</b></p>"
	            + "<ul>"
	            + "<li>Сначала запустите игру <b>без включенных шейдеров</b>.</li>"
	            + "<li>Если проблема сохранится, попробуйте запустить игру <b>без Oculus или Iris</b>.</li>"
	            + "<li>Обновите <b>Oculus/Iris</b>, <b>набор шейдеров</b> и связанные графические моды.</li>"
	            + "<li>Если вы используете другие моды на рендеринг или графику, проверьте их на совместимость.</li>"
	            + "</ul>"
	            + "<p>На практике эта ошибка обычно возникает из-за <b>набора шейдеров</b> или его взаимодействия с <b>Oculus/Iris</b>.</p>";
	}

	@Override
	public String nombreOculusIrisUnknownShaderVariable() {
	    return "Возможная ошибка шейдеров с Oculus/Iris";
	}
	@Override
	public String mensajeItemNoExiste(String itemFaltante, String namespace) {
		String itemHtml = itemFaltante == null || itemFaltante.isEmpty() ? "(неизвестно)" : itemFaltante;
		String namespaceHtml = namespace == null || namespace.isEmpty() ? "(неизвестно)" : namespace;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Попытка использовать несуществующий предмет.</b>"
				+ "<p>Журнал содержит строку <b>Item: " + itemHtml + " does not exist</b>.</p>"
				+ "<p>Это обычно означает, что какой-то <b>датапак</b>, <b>мод</b> или <b>конфигурация</b> "
				+ "ссылается на предмет, которого нет в игре.</p>"
				+ "<p><b>Что проверить:</b></p>"
				+ "<ul>"
				+ "<li>Проверьте, установлен ли мод, который должен добавлять предмет <b>" + itemHtml + "</b>.</li>"
				+ "<li>Если он установлен, возможно, это <b>неправильная версия</b>, предмет был изменён или удалён, "
				+ "или в моде есть ошибка, и его лучше удалить.</li>"
				+ "<li>Если этого мода нет, попробуйте <b>установить его</b>.</li>"
				+ "</ul>"
				+ "<p><b>Чтобы узнать, какой мод или датапак запрашивает этот предмет:</b></p>"
				+ "<ul>"
				+ "<li>Используйте утилиту <b>grepr</b> на боковой панели.</li>"
				+ "<li><b>Не</b> выбирайте папку.</li>"
				+ "<li>Включите опцию <b>search in archives</b>.</li>"
				+ "<li>В поле поиска введите <b>namespace</b>, то есть часть перед двоеточием: "
				+ "<b>" + namespaceHtml + "</b>.</li>"
				+ "</ul>"
				+ "<p>Это обычно помогает найти файл, мод или датапак, содержащий неверную ссылку.</p>";
	}

	@Override
	public String nombreItemNoExiste() {
		return "Ссылка на несуществующий предмет";
	}
	
	@Override
	public String mensajeCobblemonPinkanIslandsRhyhornModelo() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена ошибка модели для Rhyhorn.</b>"
				+ "<p>Журнал содержит строку <b>Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn</b>.</p>"
				+ "<p>Хотя модель использует пространство имен (namespace) <b>Cobblemon</b>, эта строка обычно вызвана модом "
				+ "<b>Cobblemon: Pinkan Islands</b>, откуда и происходит этот <b>Rhyhorn</b>.</p>"
				+ "<p><b>Что попробовать:</b></p>"
				+ "<ul>"
				+ "<li>Удалите или отключите <b>Cobblemon: Pinkan Islands</b> и попробуйте снова.</li>"
				+ "<li>Обновите <b>Cobblemon</b> и <b>Cobblemon: Pinkan Islands</b> до совместимых версий.</li>"
				+ "<li>Если проблема началась после обновления одного из этих модов, попробуйте другую комбинацию версий.</li>"
				+ "</ul>"
				+ "<p>Эта ошибка обычно указывает на отсутствующую, неправильно зарегистрированную или несовместимую модель внутри "
				+ "<b>Cobblemon: Pinkan Islands</b>.</p>";
	}

	@Override
	public String nombreCobblemonPinkanIslandsRhyhornModelo() {
		return "Ошибка модели Rhyhorn в Cobblemon: Pinkan Islands";
	}
	@Override
	public String mensajeColdSweatInitDynamicTags() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Обнаружена ошибка в Cold Sweat.</b>"
				+ "<p>Журнал содержит такие признаки, как <b>$cold_sweat$onBuildStart</b>, "
				+ "<b>InitDynamicTagsEvent.fillTag</b> и <b>NullPointerException</b>, где "
				+ "регистр отображается как null.</p>"
				+ "<p>Это обычно указывает на проблему <b>Cold Sweat</b> при построении или заполнении "
				+ "<b>динамических тегов</b>, часто из-за несовместимости, внутренней ошибки мода "
				+ "или конфликтной комбинации с другим модом или датапаком.</p>"
				+ "<p><b>Что попробовать:</b></p>"
				+ "<ul>"
				+ "<li>Удалите или отключите <b>Cold Sweat</b> и попробуйте снова.</li>"
				+ "<li>Обновите <b>Cold Sweat</b> до версии, совместимой с вашей версией Minecraft и загрузчиком.</li>"
				+ "<li>Если вы используете датапаки или моды, изменяющие <b>теги</b>, <b>биомы</b>, <b>температуры</b> или связанные регистры, проверьте их также.</li>"
				+ "<li>Если ошибка началась после обновления модов, попробуйте другую комбинацию версий.</li>"
				+ "</ul>"
				+ "<p>В данном случае <b>Cold Sweat</b> напрямую вовлечен в сбой.</p>";
	}

	@Override
	public String nombreColdSweatInitDynamicTags() {
		return "Ошибка Cold Sweat в динамических тегах";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
