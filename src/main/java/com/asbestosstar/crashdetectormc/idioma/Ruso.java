package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;

public class Ruso implements Idioma {
    private final Config config = Config.obtenerInstancia();

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
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid) + ") завершен!</span>";
    }

    @Override
    public String no_tenemos_jvm() {
        return "<span style='color:#" + config.obtenerColorError() + "'>JVM не обнаружена</span>";
    }

@Override
public String problema_con_graficas_ati() {
    return "<span style='color:#" + config.obtenerColorError() + "'>Обновление драйверов ATI/AMD может помочь. Прочитайте это руководство для исправления: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Руководство по обновлению драйверов</a> https://www.amd.com/es/support/download/drivers.html Скачать </span>";
}

    @Override
    public String probelma_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>Некоторые старые версии могут конфликтовать с Nouveau-драйверами на ранних этапах загрузки.</span>";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Проблема с видеодрайверами. Для AMD/ATI обновите драйверы, для NVIDIA установите дискретную видеокарту для всех javaw.exe. Инструкция: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Руководство по обновлению драйверов</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Ошибка загрузки FML early window. Исправьте в (.minecraft/config/fml.toml) earlyWindowProvider на \"none\". Для Mac M1 используйте ARM-версию Java. Также проверьте драйверы. Для Windows, если не помогает, читайте: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Руководство по обновлению драйверов</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Отсутствуют необходимые зависимости:</span>";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>" + linea.replace("Requested by", "Запрошено").replace("Expected range", "Ожидаемый диапазон") + "</span>";
    }

    @Override
    public String local_headless(String archivo) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Отчет CrashDetector доступен здесь: <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>Просмотреть отчет</a></span>";
    }

    @Override
    public String texto_de_gui() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Это GUI CrashDetector. Если игра закрылась нормально, игнорируйте это окно.</span>";
    }

    @Override
    public String texto_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorBoton() + "'>Просмотреть отчет</span>";
    }

    @Override
    public String texto_debajo_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Просмотреть локальный отчет в браузере.</span>";
    }

    @Override
    public String texto_de_buton_compartir_enlance() {
        return "Поделиться отчетом";
    }

    @Override
    public String texto_debajo_de_buton_compartir_enlance() {
        return "Отправить отчет. Логи загрузятся на securelogger.net, отчет на другие сайты.";
    }

    @Override
    public String problematico_jar() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены проблемные JAR-файлы (приоритет: FATAL > HIGH > LOW):</b>";
    }

    @Override
    public String nivel() {
        return "<b style='color:#" + config.obtenerColorError() + "'> Уровень:</b> ";
    }

    @Override
    public String possibladad_fatal() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Потенциально фатально:</b> ";
    }

    @Override
    public String modids_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены проблемные ModID (приоритет: FATAL > LOW > LOW):</b>";
    }

    @Override
    public String packages_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены проблемные пакеты (приоритет: FATAL > LOW > LOW):</b>";
    }

    @Override
    public String faltar_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены отсутствующие критические классы:</b>";
    }

    @Override
    public String corchetes_ondulados() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Содержимое {} (важнейшие вверху, показано первые 20):</b>";
    }

    @Override
    public String config_spongemixin_problematico(String archivo) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Проблемная конфигурация SpongeMixin: </b>" + archivo;
    }

    @Override
    public String module_resolution_exception(String modules, String paquete) {
        return "<span style='color:#" + config.obtenerColorError() + "'>Дублирующиеся пакеты в модах: " + modules + " дублируют " + paquete.replace(".", "/") + ". Удалите папки из JAR через WinRAR/7z или переименовав JAR в ZIP.</span>";
    }

    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены дублирующиеся моды</b> " + linea.replace("from mod files", "из файлов мода");
    }

    @Override
    public String mcforge_mod_suspechoso() {
        return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge обнаружил подозрительный мод:</b> ";
    }

    @Override
    public String lithostichctov() {
        return "<b style='color:#" + config.obtenerColorError() + "'>CTOV требует Lithostitched, установите: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#" + config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
    }

    @Override
    public String necesitasSodiumParaIris() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Для Iris/Oculus нужен SODIUM или совместимый загрузчик (Rubidium, Embedium, Bedium)</b>";
    }

    @Override
    public String kubeJSResourcePack(String mod_nombre) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Проблема с расширением KubeJS </b>" + mod_nombre;
    }
    
    
    @Override
public String problema_con_graficas_nvidia_windows_viejo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "Обнаружены проблемы с драйверами NVIDIA в версиях до Windows 11."
            + "</span><br/><br/>"
            + "Чтобы убедиться, что Minecraft (и текущая JVM) использует выделенную видеокарту NVIDIA, следуйте этим шагам:<br/><br/>"
            + "1. <strong>Определите исполняемый файл Java:</strong><br/>"
            + "   - Эта программа использует следующий исполняемый файл Java: "
            + obtenerRutaJava() + "<br/>"
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
            + "   - Эта программа использует следующий исполняемый файл Java: "
            + obtenerRutaJava() + "<br/>"
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Ваш сервер или мир имеет тики дольше 60 секунд. Это может быть связано с модами, замедляющими сервер, или слишком слабым оборудованием.</b>";
}



@Override
public String noTieneMemoria() {
    return "<b style='color:#" + config.obtenerColorError() + "'>У вас недостаточно RAM/памяти. Нужно выделить больше.</b>";
}
    
@Override
public String theseus() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Если вы используете Theseus/ModrinthApp, мы не можем оказать большую помощь, так как у Theseus нет консоли запуска. У Theseus также есть другие проблемы, включая устаревшие версии загрузчиков модов, шпионское ПО, плохие логи и многое другое. Компания Modrinth также нечестна. Они делают ложные обвинения, что разработчики модов используют ботов для увеличения количества загрузок, и неоднократно меняли свои заявления о монетизации.</b>";
}
    
@Override
public String noTieneConsolaDeLauncherCursedForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>У вас нет файла launcher_log.txt. Нам нужен этот файл для поиска ошибок. Это связано с опцией \"Пропустить запуск лаунчера\". Отключите её.</b>";
}
    

@Override
public String faltar_de_clases_advertencia() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Предупреждение: Обнаружены отсутствующие классы:</b>";
}


@Override
public String noResultos() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Нет результатов</b>";
}

@Override
public String ubicacionesDeLogs() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>Расположение логов:</b>";
}

@Override
public String infoDeVerificaciones() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>Вот результаты ваших проверок. Исправление верхних частей логов является первоочередной задачей. Делайте это медленно.</b>";
}


@Override
public String versionDeJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Пожалуйста, используйте Java 17 для версий 1.17-1.20.4 и Java 21 для более новых версий. Для старых версий используйте Java 8. [Руководство](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Если у вас все еще есть проблемы, это может быть связано с тем, что некоторые моды имеют слишком новые или старые файлы.</b>";
}

@Override
public String java22() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java 22 и выше не работают в версиях Minecraft ниже 1.20.5 для большинства загрузчиков модов из-за устаревшего ASM.</b>" + versionDeJava();
}

@Override
public String javaObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java устарела </b>" + versionDeJava();
}

@Override
public String jpms_modules_faltas(String mod_necesitas, String submod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Вам нужен JPMS модуль " + mod_necesitas + " из " + submod + "</b>";
}

@Override
public String null_pointer_error(String metodo, String objeto) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Невозможно вызвать " + metodo + ", так как " + objeto + " равен null</b>";
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
public String noRegistroDeLauncher() {
    return "Логи лаунчера не найдены! Это может осложнить расследование.\n"
            + "                \n"
            + "                Чтобы получить правильные логи:\n"
            + "                - MultiMC/PolyMC/PrismLauncher: ПРИМЕЧАНИЕ: Автоматически обнаруженные логи НЕ являются правильными.\n"
            + "                  1. Откройте интерфейс экземпляра\n"
            + "                  2. Перейдите в раздел \"Minecraft Log\"\n"
            + "                  3. Щелкните правой кнопкой мыши и скопируйте содержимое\n"
            + "                - CurseForgeApp:\n"
            + "                  1. Перезапустите игру, НЕ пропуская лаунчер\n"
            + "                  \n";
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
    return "Этот диалог позволяет вам делиться логами с использованием API SecureLogger "
            + "на securelogger.net. При нажатии на кнопку отправки отчета ваш отчет отправляется на "
            + "выбранный конечный пункт (по умолчанию asbestosstar.egoism.jp) (можно изменить внизу). Вы можете поделиться всеми выбранными логами "
            + "вместе с отчетом. Если вы не хотите загружать данные, не используйте этот диалог! Мы не обрабатываем ваш отчет на официальном конечном пункте (https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb); мы только удаляем запрещенные ссылки. Код находится здесь: https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb. Это используется исключительно для отображения информации о вашем сбое и ссылки на логи. Однако можно использовать пользовательский конечный пункт, который может не иметь тех же методов. Вы используете сайт отчетов " 
            + Config.obtenerInstancia().obtenerSitoDeInformes() + " и сайт логов " 
            + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". Вы также можете делиться отдельными логами без отчета, нажимая кнопки «Поделиться» рядом с названиями логов; логи будут отправлены на выбранный сайт логов. CrashDetector имеет функцию анонимизации логов по умолчанию, которая пытается удалить имена пользователей, UUID, токены доступа, идентификаторы сессий, IP-адреса и другие данные. Однако она не идеальна. Тем не менее, автор модпака может её отключить. Её можно включить или отключить с помощью флажка в нижней части этого экрана. Вы являетесь контроллером своих данных; вы решаете, куда загружать свои данные. Сайты логов принадлежат третьим сторонам, чье владение часто скрыто из соображений конфиденциальности. Вы полностью отвечаете за управление своими данными и связанными с этим рисками. Диалог обмена CrashDetector — это просто интерфейс, позволяющий вам управлять этим. Важно, чтобы вы были осведомлены о GDPR и ARCO.";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Несовместимая версия JavaFML: требуется " + requerido 
         + ", но найдена " + encontrado + "</b>";
}

@Override
public String errorJavaFML_MCForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "Внимание! JavaFML требует определённой версии Minecraft Forge</b>";
}

@Override
public String errorProveedorVersion(String proveedor, String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Несовместимая версия поставщика " + proveedor + ": "
         + "требуется " + requerido + ", но найдена " + encontrado + "</b>";
}

@Override
public String advertenciaMalwareFalso() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "ВНИМАНИЕ! Crash Assistant — это поддельный детектор вредоносного ПО. Он намеренно блокирует запуск игры, игнорируя вашу свободу продолжать играть с целевыми модификациями. "
         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Посмотреть код MalwareMod.java</a>   "
         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Посмотреть код JarInJarHelper.java</a>. "
         + "На данный момент в их списке только этот мод, и они фактически нацелены только на сайт записи по умолчанию, который может быть изменён пользователем, и это происходит только если вы явно выбираете использовать встроенную функцию обмена логами. CrashAssistant НЕ проводит никаких проверок, чтобы определить, какой сайт записи используется, и не объясняет, как его изменить (есть выпадающее меню внизу диалогового окна обмена), и независимо от настроенного сайта, CrashAssistant заблокирует запуск игры. В их сообщении говорится, чтобы вы провели своё собственное исследование, СДЕЛАЙТЕ ЭТО, изучите код CrashDetector и Crash Assistant и поймите, что они делают, НЕ полагайтесь на обращение к авторитету.</b>";
}





}
