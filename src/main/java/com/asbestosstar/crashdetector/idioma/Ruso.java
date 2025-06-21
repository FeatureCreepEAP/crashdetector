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

public String nombre_de_contento_de_stacktrace() {
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
public String adjustesCrashDetector() {
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
    return "У вас отсутствуют классы (Предупреждение), обычно это нормально, но не всегда. Плохие коремоды или отсутствующие зависимости — распространенные причины этой проблемы.";
}

@Override
public String solucionFaltasClases() {
    return "У вас отсутствуют классы (ФАТАЛЬНО), это очень важно. Плохие коремоды или отсутствующие зависимости — распространенные причины этой проблемы.";
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









}
