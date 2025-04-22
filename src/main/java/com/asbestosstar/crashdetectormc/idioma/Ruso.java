package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Config;
import com.asbestosstar.crashdetectormc.Idioma;

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
    
    
    
}
