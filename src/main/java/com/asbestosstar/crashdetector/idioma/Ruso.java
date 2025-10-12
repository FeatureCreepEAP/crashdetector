package com.asbestosstar.crashdetector.idioma;

import java.util.List;

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
    public String problema_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>Некоторые старые версии могут конфликтовать с Nouveau-драйверами на ранних этапах загрузки.</span>";
    }

    @Override
    public String problema_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Проблема с видеодрайверами. Для AMD/ATI обновите драйверы, для NVIDIA установите дискретную видеокарту для всех javaw.exe. Инструкция: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Руководство по обновлению драйверов</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Ошибка загрузки FML early window. Исправьте в (.minecraft/config/fml.toml) earlyWindowProvider на \"none\". Для Mac M1 используйте ARM-версию Java. Также проверьте драйверы. Для Windows, если не помогает, читайте: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Руководство по обновлению драйверов</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesarias() {
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
        return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены проблемные JAR-файлы (приоритет: FATAL > HIGH > LOW):</b>";
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
        return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены проблемные ModID (приоритет: FATAL > LOW > LOW):</b>";
    }

    @Override
    public String packages_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены проблемные пакеты (приоритет: FATAL > LOW > LOW):</b>";
    }

    @Override
    public String falta_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены фатальные классы (FATAL), это очень серьёзно. Частые причины — плохие CoreMods или фатальные зависимости. Используйте QuickFix для поиска модов с фатальными классами. Обнаруженные отсутствующие фатальные классы:</b>";
    }

    @Override
    public String corchetes_ondulados() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Содержимое {} (важнейшие вверху, показано первые 20):</b>";
    }

    @Override
    public String config_spongemixin_problematico() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Проблемная конфигурация SpongeMixin: </b>";
    }

    @Override
    public String module_resolution_exception() {
        return "<span style='color:#" + config.obtenerColorError() + "'>У вас есть моды с дублирующимися пакетами. Это можно исправить, удалив дублирующийся пакет (папку) из JAR-файла. Вы можете открыть JAR в архиваторе, таком как WinRAR или 7z, также можно изменить расширение файла с .jar на .zip, удалить папку, а затем снова переименовать его обратно в .jar файл.</span>";
    }

    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены дублирующиеся моды</b> " + linea.replace("from mod files", "из файлов мода");
    }

    @Override
    public String mcforge_mod_sospechoso() {
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
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Предупреждение: Обнаружены отсутствующие классы (уровень предупреждения). Обычно не критично, но может вызывать проблемы — отличается от фатальных ошибок. Частые причины: плохие CoreMods или отсутствующие зависимости. Используйте QuickFix для поиска модов с отсутствующими классами. Обнаруженные отсутствующие классы:</b>";
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
    return "<b style='color:#" + config.obtenerColorInfo() + "'>Вот результаты ваших проверок. Приоритет №1 — исправление верхних частей стектрейсов. Действуйте медленно: обычно корень проблемы находится в проверке 1 или 2; остальные (ошибки 3 и выше) можно использовать для подтверждения, но зачастую это каскадные ошибки, которые можно игнорировать. Сбои возникают слоями, поэтому устранение правильной проблемы решит именно эту конкретную ошибку сегодня, но завтра может появиться новая, не связанная с текущей ошибка, так как одна ошибка часто мешает появлению другой в консоли.</b>";
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
public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Файл JAR '" + archivoJar + "' требует языкового провайдера '" + proveedor + "' версии '"
         + requerido + "' или выше, но найдена только версия '" + encontrado + "'.</b>";
}

//@Override
//public String advertenciaMalwareFalso() {
//    return "<b style='color:#" + config.obtenerColorError() + "'>"
//         + "ВНИМАНИЕ! Crash Assistant — это поддельный детектор вредоносного ПО. Он намеренно блокирует запуск игры, игнорируя вашу свободу продолжать играть с целевыми модификациями. "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Посмотреть код MalwareMod.java</a>   "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Посмотреть код JarInJarHelper.java</a>. "
//         + "На данный момент в их списке только этот мод, и они фактически нацелены только на сайт записи по умолчанию, который может быть изменён пользователем, и это происходит только если вы явно выбираете использовать встроенную функцию обмена логами. CrashAssistant НЕ проводит никаких проверок, чтобы определить, какой сайт записи используется, и не объясняет, как его изменить (есть выпадающее меню внизу диалогового окна обмена), и независимо от настроенного сайта, CrashAssistant заблокирует запуск игры. В их сообщении говорится, чтобы вы провели своё собственное исследование, СДЕЛАЙТЕ ЭТО, изучите код CrashDetector и Crash Assistant и поймите, что они делают, НЕ полагайтесь на обращение к авторитету.</b>";
//}

@Override
public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Мод '" + idMod + "' не смог загрузиться, так как требуемый класс не был найден: '"
         + claseNoEncontrada + "'. Убедитесь, что все зависимости установлены правильно.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Не удалось загрузить службу ModLauncher: </b>" + servicio + ".";
}

@Override
public String errorConJSONDeRegistro(String archivoJar, String recurso) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Ошибка при разборе JSON-файла '" + recurso + "' из JAR-файла '" + archivoJar
         + "'. Проблемы с регистрацией.</b>";
}

@Override
public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
    return "<span style='color:#" + config.obtenerColorError() + "'>" 
        + "Ошибка: Мод '" + modId + "' требует версию '" + requerido 
        + "' или выше для '" + dependencia + "', но найдена версия '" + actual + "'."
        + "</span>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Ошибка настройки мониторов: "
         + "Не удалось установить режим экрана. "
         + "Проверьте:</b>"
         + "<br>- Настройку нескольких мониторов"
         + "<br>- Обновленные драйверы видеокарты"
         + "<br>- Разрешение, поддерживаемое системой";
}

@Override
public String errorOpcionesGCJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Ошибка в параметрах Java: "
         + "Конфликтующие параметры сборщика мусора. "
         + "Убедитесь, что вы не комбинируете несколько алгоритмов GC в параметрах JVM</b>";
}

@Override
public String errorConfigMCForge() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Критическая ошибка конфигурации Forge: "
         + "Файл конфигурации поврежден или неполон. "
         + "Удалите папку 'config' и перезапустите игру</b>";
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
public String codigo() {
	// TODO Auto-generated method stub
	return "ru";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Фатальное сообщение трассировки последнее (Не переведено):</b> ";
}

@Override
public String mensaje_de_trace_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Сообщение трассировки последнее (Не переведено):</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Плагин 'AuthMe' не загрузился и остановил сервер.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Мир '" + nombreMundo + "' не может быть загружен, так как содержит ошибки и, вероятно, повреждён.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Конфигурация расширения 'PermissionsEx' недействительна.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Найдено несколько файлов плагинов с именем '" + nombrePlugin + "': '" + primerPath + "' и '" + segundoPath + "'.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Во время загрузки чанков мира произошла ошибка.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin + "' не может выполнить команду '/" + comando + "'.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin + 
           "' требует зависимость '" + dependencia + "'.</b> ";
}

@Override
public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
    StringBuilder deps = new StringBuilder();
    for (int i = 0; i < dependencias.size(); i++) {
        if (i > 0) deps.append(", ");
        deps.append("'").append(dependencias.get(i)).append("'");
    }
    return "<b style='color:#" + config.obtenerColorError() + "'>Плагину '" + nombrePlugin + 
           "' не хватает следующих зависимостей: " + deps.toString() + ".</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin + 
           "' требует версию API '" + versionAPI + "', которая несовместима с текущим сервером.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Мир '" + nombreMundo + 
           "' является дубликатом другого мира и не может быть загружен.</b> ";
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

@Override
public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
    String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
    return "<b style='color:#" + config.obtenerColorError() + "'>Блочная сущность '" + nombre + 
           "' типа '" + tipo + "' по координатам " + coords + " вызывает ошибки при тиках.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + "' имеет несколько установленных версий.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Моды '" + primerMod + 
           "' и '" + segundoMod + "' несовместимы друг с другом.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + "' содержит критическую ошибку и не может быть запущен.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Следующие моды обязательны, но не установлены: " + deps.toString() + ".</b>";
}

@Override
public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
    if (version == null || version.isEmpty()) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + 
               "' требует зависимость '" + dependencia + "'.</b>";
    } else {
        return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + 
               "' требует версию '" + dependencia + "' версии " + version + ".</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin + 
           "' несовместим с региональным тикингом Folia.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + 
           "' отсутствует в датапаке.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + 
           "' несовместим с версией Minecraft " + versionMC + ".</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Мод '" + nombreMod + "' отсутствует, но требуется для загрузки мира или плагина.</b>";
}

@Override
public String nombreProblemaDependenciaModFaltante() {
    return "Отсутствующий мод";
}

@Override
public String mensajeWorldModFaltanteSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Мир был сохранён с использованием мода '" + nombreMod + 
           "', который сейчас отсутствует.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Мир был сохранён с модом '" + nombreMod + 
           "' версии " + versionEsperada + ", а сейчас используется версия " + versionActual + ".</b>";
}

@Override
public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas, List<String> versionesActuales) {
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
        sb.append(" (Сохранено: ").append(versionesEsperadas.get(i)).append(", Текущая: ").append(versionesActuales.get(i)).append(")");
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Вы попытались загрузить мир, созданный в более новой версии Minecraft.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin + 
           "' требует следующую зависимость: '" + dependencia + "'.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin + "' несовместим с текущей версией сервера.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Плагин '" + nombrePlugin + "' вызвал ошибку во время выполнения.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружено многопоточное обращение к классу LegacyRandomSource. Для получения дополнительной информации используйте моды 'Unsafe World Random Access Detector' или 'C2ME'.</b>";
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
        
        + "<h3>Как использовать:</h3>"
        + "<ol>"
        + "<li><b>Выбор файлов:</b> Нажмите на радиокнопки рядом с именами файлов. "
        + "Файлы с окончанием <span style='color: #4CAF50; font-weight: bold;'>.успех</span> — успешные сеансы, "
        + "файлы <span style='color: #F44336; font-weight: bold;'>.провал</span> — сеансы с ошибками.</li>"
        
        + "<li><b>Автоматическое сравнение:</b> После нажатия 'Сравнить' система проанализирует оба списка и покажет добавленные (+) или удалённые (-) моды.</li>"
        
        + "<li><b>Просмотр результатов:</b> Результат отображается в формате HTML с цветовым кодированием: "
        + "<ul>"
        + "<li><span style='color: green;'>+ Добавленный мод</span></li>"
        + "<li><span style='color: red;'>- Удалённый мод</span></li>"
        + "</ul></li>"
        + "</ol>"
        
        + "<h3>Основные возможности:</h3>"
        + "<ul>"
        + "<li>Поддерживает любую комбинацию файлов (успех/провал).</li>"
        + "<li>Двустороннее сравнение для точного отслеживания изменений.</li>"
        + "<li>Прокрутка для длинных списков модов.</li>"
        + "<li>Интеграция с поясняющими изображениями для лучшего понимания.</li>"
        + "</ul>"
        
        + "<p>Разработано с <3️ для отслеживания изменений в ваших настройках.</p>"
        + "</body></html>";
}

@Override
public String tieneErrorIPV6() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
           + "Возможно, у вас есть проблемы, связанные с IPv6. "
           + "Есть два решения: "
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
    return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/Производные: Щелкните правой кнопкой мыши по инстансу и выберите \"Изменить инстанс\". В открывшемся окне найдите раздел \"Логи Minecraft\" или подобный ему.<br>" +
           "Эти логи содержат стандартный вывод (STDOUT), критически важный для диагностики ошибок.";
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
    return "ОБЩИЙ: Выберите тип используемого лаунчера. Журналы лаунчера (launcher_log.txt, stdout и т. д.) содержат критическую информацию об ошибках, которых нет в latest.log. CrashDetector не может прочитать журналы вашего лаунчера — возможно, он не создаёт файл журнала, и вам нужно вставить журналы вручную.<br>" +
           "Для получения дополнительной информации см. <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">этот вопрос</a>. Эти журналы содержат стандартный вывод (STDOUT), необходимый для диагностики многих типов ошибок.";
}

@Override
public String faltar_de_clases_create() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены отсутствующие классы из Create. Create сильно изменился — многие классы были удалены. Начиная с Create 6 (февраль 2025), аддоны для старых версий Create больше не работают. QuickFix не может решить эту проблему. Вам нужно обновить аддоны Create, удалить устаревшие или использовать правильную версию Create для нужных аддонов.</b>";
}

@Override
public String faltar_de_clases_epicfight() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Обнаружены отсутствующие классы из EpicFight. EpicFight сильно изменился — многие классы были удалены. QuickFix не может решить эту проблему. Вам нужно обновить аддоны EpicFight, удалить устаревшие или использовать правильную версию EpicFight для нужных аддонов.</b>";
}

@Override
public String openJ9NoSoportado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Вы используете OpenJ9, который не поддерживается этим приложением. Многие приложения, включая это, не поддерживают OpenJ9 из-за различий в реализации JVM. QuickFix не может автоматически решить эту проблему. Вам нужно установить совместимый JDK, например Oracle JDK, OpenJDK Hotspot или другие рекомендуемые альтернативы.</b>";
}

@Override
public String necesitasJDK11() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Для корректной работы этой версии приложения требуется Java 11 (JDK 11). Вы используете устаревшую, несовместимую версию Java. QuickFix не может обновить Java автоматически. Вам нужно скачать и установить JDK 11 или более новую совместимую версию по ссылкам, указанным в решении.</b>";
}

@Override
public String memoriaExcesiva() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Вы выделили слишком много памяти, из-за чего системе не хватает ресурсов. Это происходит, когда указано больше RAM, чем доступно, или при использовании 32-битной JVM, не способной обрабатывать большие объёмы памяти.</b>";
}

@Override
public String recomendacionMemoriaExcesiva() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Чтобы решить проблему, уменьшите объём памяти, выделенной приложению. Рекомендуемый максимум зависит от системы, но обычно не должен превышать 70–80% от общего объёма RAM. При использовании 32-битной JVM лимит составляет около 2–3 ГБ, независимо от объёма физической памяти.</b>";
}

@Override
public String disminuirMemoriaHeap() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Чтобы уменьшить выделенную кучу (heap), откройте настройки лаунчера и найдите параметр памяти. Уменьшите максимальное значение (Xmx) до подходящего. Например, при 8 ГБ ОЗУ выделите 3–4 ГБ; при 16 ГБ — 6–8 ГБ. Оставьте достаточно памяти для ОС и других программ.</b>";
}

@Override
public String forgeArchivosFaltantes(String archivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Отсутствуют важные файлы Forge. Файл '" + archivo + "' не найден в вашей установке. Это происходит, если установка Forge была прервана или важные файлы были удалены. QuickFix не может восстановить эти файлы автоматически. Вам нужно переустановить Forge с помощью официального установщика.</b>";
}

@Override
public String forgeVersionNoEncontrada(String version, String archivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Forge не может найти требуемую версию Minecraft. Требуется версия " + version + ", но она не найдена в файле '" + archivo + "'. Это происходит при несоответствии версии Minecraft и версии Forge. Убедитесь, что вы скачали правильную версию Forge, совместимую с вашей версией Minecraft.</b>";
}

@Override
public String forgeTargetFmlclientNoEncontrado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Не удалось найти целевой объект 'fmlclient', необходимый для запуска Forge. Это указывает на неполную или повреждённую установку Forge. Ключевые файлы Forge, возможно, не были установлены корректно. Вам нужно переустановить Forge с помощью официального установщика.</b>";
}

@Override
public String forgeClaseMinecraftFaltante() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Основной класс Minecraft не найден в загрузчике классов. Это обычно означает неполную установку Forge или конфликт с другими модами. Файлы Minecraft могли повредиться при установке Forge. Вам нужно правильно переустановить Forge.</b>";
}

@Override
public String forgeInstallacionNoCompleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Установка Forge неполная. Это может быть вызвано прерванной установкой, удалёнными файлами или несовместимостью с вашей версией Minecraft. Forge требует определённых файлов для работы, и некоторые из них отсутствуют в текущей установке.</b>";
}

@Override
public String nombre_de_forge_instalacion_no_completa() {
    return "Неполная установка Forge";
}

@Override
public String solucion_para_forge_instalacion_no_completa() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Чтобы решить проблему, переустановите Forge. Убедитесь, что вы скачали версию, совместимую с вашей версией Minecraft, и завершите установку без перерывов.</b>";
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
    return "<html><body style='width: 500px;'>" +
           "<h3 style='color:#" + config.obtenerColorTitulo() + "'>Инструкции по переустановке Forge:</h3>" +
           "<ol>" +
           "<li>Скачайте правильный установщик Forge с официального сайта (рекомендуемая версия для вашего Minecraft)</li>" +
           "<li>Полностью закройте лаунчер Minecraft</li>" +
           "<li>Запустите установщик Forge от имени администратора</li>" +
           "<li>Выберите опцию 'Installer' (не 'Installer (run client)')</li>" +
           "<li>Укажите папку профиля Minecraft в лаунчере</li>" +
           "<li>Нажмите 'OK' и дождитесь завершения установки</li>" +
           "<li>Перезапустите лаунчер и проверьте, появился ли Forge в списке профилей</li>" +
           "</ol>" +
           "<p><b>Важно:</b> При использовании кастомного лаунчера убедитесь, что выбрана правильная папка профиля.</p>" +
           "</body></html>";
}

@Override
public String titulo_instrucciones_reinstaler_mcforge() {
    return "Инструкции по переустановке Forge";
}

@Override
public String error_enlace_insatisfecho(String nombreBiblioteca) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Ошибка неудовлетворённой ссылки: не удалось загрузить библиотеку " + nombreBiblioteca + ". Возможные решения:<br/><br/>" +
           "a) Добавьте каталог с общей библиотекой в -Djava.library.path или -Dorg.lwjgl.librarypath.<br/>" +
           "b) Добавьте JAR-файл с общей библиотекой в classpath.<br/><br/>" +
           "Эта ошибка возникает, когда Minecraft не может найти необходимые файлы для работы. " +
           "Обычно вызвана неполной установкой Minecraft или проблемами с правами доступа в системе.</b>";
}

@Override
public String nombre_de_error_enlace_insatisfecho() {
    return "Ошибка неудовлетворённой ссылки";
}

@Override
public String solucion_para_error_enlace_insatisfecho() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Не удалось загрузить библиотеку. Возможные решения:<br/><br/>" +
           "a) Добавьте каталог с общей библиотекой в -Djava.library.path или -Dorg.lwjgl.librarypath.<br/>" +
           "b) Добавьте JAR-файл с общей библиотекой в classpath.<br/><br/>" +
           "Эти технические решения предназначены для опытных пользователей. Большинству пользователей следует попробовать " +
           "переустановить Minecraft перед изменением этих параметров.</b>";
}

@Override public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) { return "<b style='color:#" + config.obtenerColorError() + "'>Конфликт ID: ID <strong>" + id + "</strong> уже занят модом <strong>" + modOrigen + "</strong>, при попытке добавить <strong>" + modDestino + "</strong>. Происходит, когда два мода пытаются использовать один и тот же ID для разных элементов.</b>"; } @Override public String conflicto_id_maximo() { return "<b style='color:#" + config.obtenerColorError() + "'>Превышен максимальный допустимый диапазон ID. Происходит, когда моды пытаются зарегистрировать блоки или предметы с ID за пределами диапазона, поддерживаемого вашей версией Minecraft.</b>"; } @Override public String nombre_de_conflicto_ids() { return "Конфликт ID"; } @Override public String solucion_maximo_rango() { return "<b style='color:#" + config.obtenerColorTexto() + "'>Чтобы решить это в Minecraft 1.12.2, установите <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. Для 1.7.10 используйте <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>"; } @Override public String solucion_colision_id() { return "<b style='color:#" + config.obtenerColorTexto() + "'>Используйте такие инструменты, как <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> или <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a>, чтобы устранить конфликты ID.</b>"; } @Override public String instalar_justenoughids() { return "Установить JustEnoughIDs"; } @Override public String instalar_endlessids() { return "Установить EndlessIDs"; } @Override public String usar_idfix_minus() { return "Использовать IdFix Minus или IdFix"; } @Override public String usar_minecraft_id_resolver() { return "Использовать Minecraft-ID-Resolver"; } @Override public String ver_documentacion_jp() { return "Просмотреть японскую документацию"; }


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
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>У вас отсутствуют бинарные файлы VLC. WaterMedia требует бинарные файлы VLC. Установите их вручную с сайта https://www.videolan.org/vlc/.  </b>";
}

@Override
public String descargar_vlc() {
    return "Скачать VLC";
}

@Override
public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Критическая ошибка: Имя модуля '" + nombreModulo + 
           "' содержит недопустимые символы. Часть '" + parteInvalida + 
           "' не является допустимым идентификатором Java. Это происходит, когда мод использует зарезервированные слова Java (например, 'true', 'class') или недопустимые символы в своём имени.</b>";
}

@Override
public String nombre_de_error_caracteres_invalidos() {
    return "Недопустимые символы в имени мода";
}

@Override
public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
    return "Имя мода '" + nombreModulo + "' недопустимо, потому что содержит '" + parteInvalida + 
           "', которое является зарезервированным словом Java или недопустимым символом. " +
           "Найдите в логах, какому моду соответствует это имя (обычно имя JAR-файла)";
}

@Override
public String paso2_caracteres_invalidos(String nombreModulo) {
    return "Перейдите в папку мода и отредактируйте файл <b>mods.toml</b> в папке <b>/META-INF/</b>. " +
           "Измените значение <b>modId</b>, чтобы использовать только буквы, цифры и подчёркивания, без зарезервированных слов Java";
}

@Override
public String paso3_caracteres_invalidos() {
    return "Пример допустимого имени: 'truemod_shot_enchantment' вместо 'true.shot.enchantment'. " +
           "Помните, что имена модов не могут содержать точки, дефисы или зарезервированные слова Java, такие как 'true', 'false' или 'class'";
}

@Override
public String errorDependenciaModFaltante(String nombreJar) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Критическая ошибка с модом: '" + nombreJar + "'. Отсутствует обязательное поле 'mandatory' в зависимостях. Происходит, когда файл mods.toml не указывает, является ли зависимость обязательной.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Критическая ошибка с модом: '" + nombreJar + "'. Неверная конфигурация access transformer. Происходит, когда файл конфигурации имеет неправильный синтаксис или ссылается на несуществующие классы/методы.</b>";
}

@Override
public String nombre_de_error_access_transformer_invalido() {
    return "Недопустимый Access Transformer";
}

@Override
public String paso1_access_transformer_invalido(String nombreJar) {
    return "Проблемный мод: <b>" + nombreJar + "</b>. Этот мод содержит недопустимую конфигурацию access transformer";
}

@Override
public String paso2_access_transformer_invalido(String nombreJar) {
    return "Откройте файл <b>accessTransformer.cfg</b> внутри мода <b>" + nombreJar + "</b> (обычно в корневой папке JAR-файла)";
}

@Override
public String paso3_access_transformer_invalido() {
    return "Исправьте синтаксис access transformer. Строки должны следовать формату: <b>access class.method</b> (напр.: public net.minecraft.world.entity.Entity.func_200560_a). Удалите строки, ссылающиеся на классы или методы, которых нет в вашей версии Minecraft";
}

@Override
public String errorDiscrepanciaModID(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Критическая ошибка: Расхождение между ID мода в аннотации @Mod и файлом mods.toml. Мод '" + nombreMod + "' не может быть загружен, так как ID не совпадают.</b>";
}

@Override
public String nombre_de_error_discrepancia_mod_id() {
    return "Расхождение между @Mod и mods.toml";
}

@Override
public String paso1_discrepancia_mod_id(String nombreMod) {
    return "Мод в разработке '" + nombreMod + "' имеет расхождение между ID в аннотации <b>@Mod</b> и значением в <b>mods.toml</b>";
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
    
    return "<b style='color:#" + config.obtenerColorError() + "'>Критическая ошибка: Попытка загрузить класс '" + nombreClase + 
           "' в среде " + plataforma + ", но он предназначен для " + plataformaOpuesta + 
           ". <b>Используйте функцию «Дерево модов» на боковой панели, чтобы найти, какой мод пытается загрузить этот класс</b>. " +
           "Моды создаются специально для одной платформы и не работают на другой.</b>";
}

@Override
public String nombre_de_error_mod_plataforma_incorrecta() {
    return "Мод на неправильной платформе";
}

@Override
public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
    return "На вкладке <b>Дерево модов</b> (справа) найдите ссылки на класс <b>" + nombreClase + 
           "</b>, чтобы определить, какой мод вызывает проблему";
}

@Override
public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
    String plataforma = entornoInvalido.equals("CLIENT") ? "клиент" : "сервер";
    String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "сервер" : "клиент";
    
    return "Обнаруженный мод — это мод для <b>" + plataformaOpuesta + "</b>, и его нельзя использовать в вашей среде " + plataforma + ".";
}

@Override
public String paso3_mod_plataforma_incorrecta() {
    return "Удалите проблемный мод из папки <b>mods</b>. Если вам нужна аналогичная функциональность для этой платформы, " +
           "найдите альтернативный мод, специально разработанный для <b>клиента</b> или <b>сервера</b> соответственно";
}

@Override
public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
    StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
    mensaje.append("Критическая ошибка: Отсутствуют метаданные для modid '").append(modIdFaltante).append("'. ");
    
    if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
        mensaje.append("Следующие моды могут быть причиной проблемы: <b>");
        for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
            mensaje.append(modsPotenciales.get(i));
            if (i < modsPotenciales.size() - 1 && i < 2) mensaje.append(", ");
        }
        if (modsPotenciales.size() > 3) mensaje.append(", и другие...");
        mensaje.append("</b>. ");
    }
    
    mensaje.append("Это происходит, когда мод зависит от другого мода, который не установлен или имеет неверный файл mods.toml.");
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
        StringBuilder paso = new StringBuilder("Следующие моды зависят от '").append(modIdFaltante).append("': <b>");
        for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
            paso.append(modsPotenciales.get(i));
            if (i < modsPotenciales.size() - 1 && i < 2) paso.append(", ");
        }
        if (modsPotenciales.size() > 3) paso.append(", и другие...");
        paso.append("</b>. Используйте функцию <b>Дерево модов</b>, чтобы подтвердить, какой мод вызывает проблему");
        return paso.toString();
    }
    return "Один из модов пытается зависеть от '" + modIdFaltante + "', но этот мод не установлен. Используйте функцию <b>Дерево модов</b>, чтобы определить проблемный мод";
}

@Override
public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
    return "У вас два варианта:<br/>" +
           "1. <b>Установите недостающий мод</b>: Найдите и установите мод с ID '" + modIdFaltante + "'<br/>" +
           "2. <b>Удалите зависимый мод</b>: Если функциональность не нужна, удалите мод, зависящий от '" + modIdFaltante + "'";
}

@Override
public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
    return "Если мод '" + modIdFaltante + "' — это библиотека (например, 'forge', 'minecraft', 'curios'), " +
           "убедитесь, что у вас установлена правильная версия Minecraft и Forge. " +
           "Если это обычный мод, проверьте его страницу загрузки на предмет необходимых зависимостей";
}


@Override
public String errorSistemaSonido() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Предупреждение: Ошибка инициализации звуковой системы. Звуки и музыка отключены. Эта ошибка обычно связана с модом SoundPhysicsMod и может быть вызвана конфликтами с другими звуковыми библиотеками.</b>";
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
    mensaje.append("Критическая ошибка: Класс '").append(nombreClase).append("' зарегистрирован как слушатель событий, но не содержит допустимых методов. ");
    
    if (!modsUbicacion.isEmpty()) {
        mensaje.append("Этот класс находится в следующих модах: <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            mensaje.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) mensaje.append(", ");
        }
        if (modsUbicacion.size() > 3) mensaje.append(", и другие...");
        mensaje.append("</b>. ");
    }
    
    mensaje.append("Это происходит, когда класс регистрируется для прослушивания событий, но не имеет методов с аннотацией @SubscribeEvent.");
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
            if (i < modsUbicacion.size() - 1 && i < 2) paso.append(", ");
        }
        if (modsUbicacion.size() > 3) paso.append(", и другие...");
        paso.append("</b>. Эти моды пытаются зарегистрировать события без допустимых методов");
        return paso.toString();
    }
    return "Класс <b>" + nombreClase + "</b> был зарегистрирован для прослушивания событий, но не содержит методов с аннотацией <b>@SubscribeEvent</b>. Используйте функцию <b>Дерево модов</b>, чтобы определить, какой мод содержит этот класс";
}

@Override
public String paso2_sin_listeners_en_clase(String nombreClase) {
    return "В исходном коде убедитесь, что класс <b>" + nombreClase + "</b> содержит хотя бы один метод с: " +
           "<b>@SubscribeEvent public void имяМетода(СобытиеОпределенногоТипа событие) { ... }</b>. " +
           "Если это внутренний класс, убедитесь, что он не помечен как static";
}

@Override
public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
    StringBuilder paso = new StringBuilder();
    
    if (!modsUbicacion.isEmpty()) {
        paso.append("Для идентифицированных модов (<b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
            paso.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 1) paso.append(", ");
        }
        if (modsUbicacion.size() > 2) paso.append(", и т. д.");
        paso.append("</b>): ");
        
        if (modsUbicacion.size() == 1) {
            paso.append("свяжитесь с разработчиком этого мода, чтобы он исправил проблему. ");
        } else {
            paso.append("свяжитесь с разработчиками этих модов, чтобы они исправили проблему. ");
        }
    }
    
    paso.append("Если вы разработчик, удалите регистрацию этого класса в EventBus или добавьте допустимые методы @SubscribeEvent");
    
    return paso.toString();
}

@Override
public String errorUnionFileSystemCorrupto(String nombreArchivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Критическая ошибка: Произошло исключение 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' при обработке файла '" + 
           nombreArchivo + "'. Эта ошибка указывает на то, что лаунчер не смог корректно загрузить или распаковать файлы модпака. " +
           "Сообщение 'zip END header not found' означает, что JAR-файл неполный или повреждён, что крайне часто встречается у лаунчеров, плохо управляющих загрузкой больших файлов. " +
           "Проблема в основном затрагивает пользователей Twitch/CurseForge, Technic Launcher и особенно Luna Pixel, поскольку эти лаунчеры часто не проверяют целостность загруженных файлов. " +
           "Пользователям Luna Pixel рекомендуется перейти на ATLauncher — более стабильную альтернативу, которая лучше обрабатывает целостность файлов и избегает этой ошибки. " +
           "Система не может загрузить моды, так как формат ZIP повреждён, что мешает Forge прочитать необходимые ресурсы для запуска игры.</b>";
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
    return "Включить ProxySysOutSysErr?\n\n" +
           "Эта опция позволяет CrashDetector получать доступ к System.out и System.err, когда лаунчер не предоставляет логи.\n\n" +
           "Следует включать только если вы не можете вручную вставить лог.\n\n" +
           "Внимание: это может конфликтовать с некоторыми модами или лаунчерами.\n\n" +
           "Требуется перезапуск игры/приложения для применения изменений.";
}

@Override
public String confirmacionTitulo() {
    return "Подтверждение";
}

@Override
public String proxyHabilitadoMensaje() {
    return "ProxySysOutSysErr успешно включён.\n\n" +
           "Требуется перезапуск CrashDetector для применения изменений.";
}

@Override
public String informacionTitulo() {
    return "Информация";
}

@Override
public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError, boolean connectorPresente) {
    StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
    
    if (azureLibError && geckoLibError) {
        mensaje.append("Критическая ошибка: AzureLib и GeckoLib инициализированы слишком рано! ");
    } else if (azureLibError) {
        mensaje.append("Критическая ошибка: AzureLib инициализирован слишком рано! ");
    } else if (geckoLibError) {
        mensaje.append("Критическая ошибка: GeckoLib инициализирован слишком рано! ");
    }
    
    mensaje.append("Эта ошибка возникает при попытке использовать моды Fabric с не-Fabric-версиями этих библиотек. ");
    
    if (connectorPresente) {
        mensaje.append("Обнаружен мод совместимости (Sinytra Connector или specialcompatibilityoperation), что указывает на попытку запуска модов Fabric в среде Forge или FeatureCreep. ");
        mensaje.append("Проверьте ошибку 'Ошибка инициализации FabricMC' в логах, чтобы определить конкретный проблемный мод. ");
    }
    
    mensaje.append("AzureLib и GeckoLib — ключевые библиотеки для модов анимации, но они должны соответствовать правильной платформе (Fabric или Forge). ");
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Критическая ошибка: Несовместимость между C2ME и модами совместимости. " +
           "Эта ошибка возникает, потому что C2ME пытается получить доступ к внутренним компонентам Java, которые ограничены в средах с " +
           "Sinytra Connector или specialcompatibilityoperation, или другими модами совместимости Fabric/Forge. " +
           "<b>C2ME несовместим с такими средами, но <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> — рекомендуемая альтернатива</b>, которая корректно работает " +
           "с модами подключения. Игра не может запуститься из-за конфликта прав безопасности Java.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Критическая ошибка: Не удалось загрузить плагин JEI для мода '" + modId + 
           "'. Класс '" + nombreClase + "' (ID плагина: '" + pluginId + 
           "') вызвал ошибку, из-за которой игра аварийно завершается при запуске. " +
           "Эта проблема возникает, когда мод имеет несовместимую или сломанную интеграцию с JEI, мешающую инициализации игры.</b>";
}

@Override
public String nombre_de_error_jei_plugin_fallido() {
    return "Сбой плагина JEI — вызывает крах";
}

@Override
public String paso1_jei_plugin_fallido(String modId) {
    return "Мод <b>" + modId + "</b> содержит сломанный плагин JEI, вызывающий крах. Используйте функцию <b>Дерево модов</b>, чтобы точно определить, какой мод вызывает проблему";
}

@Override
public String paso2_jei_plugin_fallido(String modId) {
    return "Временно удалите мод <b>" + modId + "</b> из папки mods, чтобы проверить, устранит ли это крах";
}

@Override
public String paso3_jei_plugin_fallido(String modId) {
    return "Найдите обновления для мода <b>" + modId + "</b> или свяжитесь с его разработчиком, сообщив о проблеме с плагином JEI. " +
           "Пока что мод нужно удалить, чтобы игра запускалась";
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
    return "В логах обнаружена критическая ошибка. " +
           "Проверьте стектрейс, чтобы определить первопричину.";
}

@Override
public String obtenerErrorLecturaArchivo() {
    return "Не удалось прочитать файл лога. " +
           "Убедитесь, что файл существует и доступен для чтения.";
}

@Override
public String obtenerEtiquetaBotonLectador() {
    return "Анализатор логов";
}

@Override
public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
    StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
    mensaje.append("Критическая ошибка: Не удалось зарегистрировать автоматических подписчиков для мода '").append(modId).append("'. ");
    
    mensaje.append("Проблемный класс: <b>").append(nombreClase).append("</b>. ");
    
    if (!modsUbicacion.isEmpty()) {
        mensaje.append("Этот класс находится в: <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            mensaje.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) mensaje.append(", ");
        }
        if (modsUbicacion.size() > 3) mensaje.append(", и других...");
        mensaje.append("</b>. ");
    }
    
    mensaje.append("Эта ошибка возникает, когда мод пытается автоматически зарегистрировать класс как подписчика событий, но класс не может быть загружен. ");
    mensaje.append("<b>Проверьте другие ошибки в логе — реальная причина может быть в предыдущей неудачной загрузке</b>.");
    mensaje.append("</b>");
    
    return mensaje.toString();
}

@Override
public String nombre_de_error_registro_suscriptores_automaticos() {
    return "Ошибка регистрации автоматических подписчиков";
}

@Override
public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
    return "Мод <b>" + modId + "</b> пытается зарегистрировать класс <b>" + nombreClase + 
           "</b> как автоматического подписчика, но это не удалось. Убедитесь, что класс существует и доступен";
}

@Override
public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase, List<String> modsUbicacion) {
    if (!modsUbicacion.isEmpty()) {
        StringBuilder paso = new StringBuilder("Проблемный класс <b>" + nombreClase + "</b> находится в этих файлах: <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            paso.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) paso.append(", ");
        }
        if (modsUbicacion.size() > 3) paso.append(", и других...");
        paso.append("</b>. ");
        paso.append("Используйте функцию <b>Дерево модов</b>, чтобы определить, в каком файле находится проблемный класс");
        return paso.toString();
    }
    return "Класс <b>" + nombreClase + "</b> не найден ни в одном файле мода. Убедитесь, что мод <b>" + modId + 
           "</b> установлен правильно. Используйте функцию <b>Дерево модов</b> для поиска проблемы";
}

@Override
public String paso3_registro_suscriptores_automaticos(String modId) {
    return "Обновите мод <b>" + modId + "</b> до последней версии, совместимой с вашей версией Minecraft и Forge. " +
           "Если проблема останется, свяжитесь с разработчиком мода и сообщите об ошибке с указанием проблемного класса";
}

@Override
public String paso4_registro_suscriptores_automaticos() {
    return "Проверьте <b>другие ошибки в логе</b> перед этим сообщением — реальная причина может быть в предыдущем сбое загрузки. " +
           "Иногда предыдущая ошибка мешает загрузке необходимых классов для регистрации событий";
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
public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora,
                                                   String interfazObjetivo,
                                                   String firmaMetodoFaltante,
                                                   List<String> modsUbicacion) {
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
            if (i < modsUbicacion.size() - 1 && i < 2) sb.append(", ");
        }
        if (modsUbicacion.size() > 3) sb.append(", и другие...");
        sb.append("</b>. ");
    } else {
        sb.append("JAR-файлы с этим классом не найдены; возможно, он затенён или встроен как jar-in-jar. ");
    }

    sb.append("Эта ошибка возникает, когда трансформер/сервис ModLauncher, скомпилированный для MinecraftForge/LexForge, ");
    sb.append("загружается в NeoForge с несовместимой версией API ModLauncher. ");
    sb.append("Обновите или замените компонент на версию для NeoForge.");
    return sb.toString();
}

@Override
public String nombre_de_LexForgeMLTransformerEnNeoForge() {
    return "Использование трансформера LexForge в NeoForge";
}

@Override
public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora,
                                                    String interfazObjetivo,
                                                    String firmaMetodoFaltante) {
    return "Определите несовместимый трансформер: <b>" + claseReceptora + "</b>. "
         + "Ожидаемое API — <b>" + interfazObjetivo + "</b>, отсутствует метод <b>" + firmaMetodoFaltante + "</b>. "
         + "Проверьте, регистрирует ли мод этот класс в <b>META-INF/services</b>, и удалите или отключите его в NeoForge.";
}

@Override
public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
    StringBuilder sb = new StringBuilder();
    if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
        sb.append("Расположение мода(ов): <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            sb.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) sb.append(", ");
        }
        if (modsUbicacion.size() > 3) sb.append(", и другие...");
        sb.append("</b>. ");
    } else {
        sb.append("JAR-файлы с этим классом не найдены. Проверьте вложенные jar-in-jar и затенённые зависимости. ");
    }
    sb.append("Временно удалите эти JAR-файлы или используйте версии, совместимые с NeoForge, чтобы подтвердить источник.");
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
    if (modNombre != null && !modNombre.isEmpty()) sb.append("[").append(modNombre).append("] ");
    sb.append("несовместим.</b> ");
    sb.append("Удалите Xenon и используйте вместо него Embeddium или Sodium. ");
    if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
        sb.append("Обнаружен в: <b>");
        for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
            if (i > 0) sb.append(", ");
            sb.append(modsUbicacion.get(i));
        }
        if (modsUbicacion.size() > 3) sb.append(", и другие...");
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
    if (modNombre != null && !modNombre.isEmpty()) label += " [" + modNombre + "]";
    return "Обнаружен " + label + ", несовместимый с WaterMedia. Удалите его из профиля.";
}

@Override
public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
    if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
        StringBuilder sb = new StringBuilder("Местоположения: <b>");
        for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
            if (i > 0) sb.append(", ");
            sb.append(modsUbicacion.get(i));
        }
        if (modsUbicacion.size() > 3) sb.append(", и другие...");
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
            if (i > 0) sb.append(", ");
            sb.append(modsUbicacion.get(i));
        }
        if (modsUbicacion.size() > 3) sb.append(", и другие");
        sb.append("</b>. ");
    }
    sb.append("<br/><b>Решение:</b> в файле <code>tacz/tacz-pre.toml</code> установите <code>DefaultPackDebug=true</code>. ")
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
    StringBuilder sb = new StringBuilder("<b>Функции плотности отсутствуют в реестре.</b> ");
    if (claves != null && !claves.isEmpty()) {
        sb.append("Отсутствуют: ");
        for (int i = 0; i < Math.min(4, claves.size()); i++) {
            if (i > 0) sb.append(", ");
            sb.append("<code>").append(claves.get(i)).append("</code>");
        }
        if (claves.size() > 4) sb.append(", …");
        sb.append(". ");
    }
    sb.append("<br/><b>Решение:</b> установите или включите мод/датапак, определяющий эти функции, и перезапустите.");
    return sb.toString();
}

@Override
public String pasoFuncionesDeDensidadNoVinculadas() {
    return "Установите или включите мод/датапак, предоставляющий эти функции, и перезапустите игру.";
}

@Override
public String errorRailwaysCreate6Alfa(String claveFaltante) {
    // Краткое сообщение цветом ошибки с явным упоминанием мода
    StringBuilder sb = new StringBuilder("<b style='color:#")
            .append(config.obtenerColorError())
            .append("'>");
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
    StringBuilder sb = new StringBuilder("<b style='color:#")
            .append(config.obtenerColorError())
            .append("'>");
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
public String nombreErrorContextoOpenGL() { return "Ошибка контекста OpenGL"; }

@Override
public String errorContextoOpenGL() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Сбой OpenGL: отсутствует текущий контекст или функция недоступна в этом контексте. "
         + "Также может быть проблема с драйверами видеокарты."
         + "</b>";
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
            + "Распространённые компоненты служб в ModLauncher включают CrashDetector, "+ Config.obtenerInstancia().obtenerNombreCD() +", FeatureCreep, Vivicraft, Optifine, Sodium, clonos, Iris Shaders/Oculus, MixerLogger, CrashAssistant и Sintrya Connector. "
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
        sb.append("🔸 <b>Мод не определён.</b> Проверьте недавно установленные, разрабатываемые или неправильно упакованные моды.<br>");
    }
    
    sb.append("🔸 <b>Причина:</b> Файл <code>META-INF/services/...</code> мода повреждён, ");
    sb.append("несовместим с этой версией Forge/NeoForge или предназначен для неверной версии.<br>");
    sb.append("🔸 <b>Последствие:</b> Forge/NeoForge не может зарегистрировать зависимости мода, ");
    sb.append("что блокирует запуск игры.<br>");
    sb.append("🔸 <b>Решение:</b> Обновите, переустановите или удалите проблемный мод. ");
    sb.append("Если вы используете моды в стадии разработки, убедитесь, что они скомпилированы под точную версию вашего Forge/NeoForge.");
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

    return "<b style='color:#" + colorError + "'>Критическая ошибка: Метод не существует.</b><br>" +
           "Мод попытался вызвать метод <b style='color:#" + colorCodigo + "'>" + metodo + "</b>, " +
           "который отсутствует в этой версии игры или другого мода.<br>" +
           "<span style='color:#" + colorCodigo + "; font-family:monospace;'>" + 
           escapeHtml(lineaCompleta) + "</span>";
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

    return "<b style='color:#" + colorError + "'>Критическая ошибка: Поле не существует.</b><br>" +
           "Мод попытался получить доступ к полю <b style='color:#" + colorCodigo + "'>" + campo + "</b>, " +
           "которое отсутствует в этой версии игры или другого мода.<br>" +
           "<span style='color:#" + colorCodigo + "; font-family:monospace;'>" + 
           escapeHtml(lineaCompleta) + "</span>";
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










}
