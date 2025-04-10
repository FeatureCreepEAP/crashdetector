package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Idioma;

public class Ruso implements Idioma {

    @Override
    public String carpeta_de_mods_no_valido() {
        return "Это не является допустимой папкой для модов";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "Я не знаю, где находится JAR-файл CrashDetector";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "Поиск PID: " + String.valueOf(pid);
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "(PID: " + String.valueOf(pid) + ") мёртв!";
    }

    @Override
    public String no_tenemos_jvm() {
        return "У нас нет JVM";
    }
    
    @Override
public String probelma_con_graficas_ati() {
    return "Обновление драйверов может помочь. Учтите, что поиск обновлений обычным способом не найдет их, если драйверы находятся в поврежденном состоянии, поэтому важно следовать прикрепленному руководству. Важно: Если у вас графика Nvidia, убедитесь, что все, связанное с Minecraft (например, Java и лаунчеры), настроено на использование высокой производительности как в настройках Windows, так и в панели управления Nvidia. Прочитайте это руководство для решения проблемы: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
}

@Override
public String probelma_con_graficas_nouveau() {
    return "Некоторые старые версии иногда имеют проблемы с графикой Nouveau на экране ранней загрузки.";
}

@Override
public String probelma_con_graficas_general() {
    return "У вас есть проблема с графическими драйверами. Если у вас GPU или APU AMD/ATI, обновите свои графические драйверы AMD. Если у вас видеокарта NVIDIA, убедитесь, что игра и все экземпляры javaw.exe настроены на использование выделенной видеокарты. Прочитайте это руководство: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
}

@Override
public String fmlEarlyWindow() {
    return "Ваше FML Early окно не работает. "
            + "Чтобы изменить это, перейдите в (.)minecraft/config/fml.toml "
            + "Измените earlyWindowProvider на earlyWindowProvider=\"none\" "
            + "Если вы используете Mac M1, убедитесь, что вы используете версию Java для ARM, а не Intel x64. "
            + "Это также распространенная проблема, если у вас устаревшие драйверы. Если вы используете Windows и отключение этого не помогает, ознакомьтесь с этим руководством: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
}

@Override
public String no_tienes_las_dependencias_necesitas() {
    return "У вас нет всех необходимых зависимостей:";
}

@Override
public String linea_de_dependencia(String linea) {
    return linea.replace("Requested by", "Запрошено").replace("Expected range", "Ожидаемый диапазон");
}


    @Override
    public String local_headless(String archivo) {
        return "Ваш отчет CrashDetector сохранен здесь: " + archivo;
    }


 @Override
    // Русский текст графического интерфейса
    public String texto_de_gui() {
        return "Это графический интерфейс CrashDetector. Если игра закрывается без проблем, игнорируйте это.";
    }



    @Override
    // Русская версия
    public String texto_de_buton_local_enlance() {
        return "Просмотреть отчет";
    }

    @Override
    // Подпись под кнопкой просмотра
    public String texto_debajo_de_buton_local_enlance() {
        return "Просмотреть локальный отчет в браузере";
    }

    @Override
    // Текст кнопки отправки
    public String texto_de_buton_compartir_enlance() {
        return "Поделиться отчетом";
    }

    @Override
    // Подпись под кнопкой отправки
    public String texto_debajo_de_buton_compartir_enlance() {
        return "Логи будут загружены на securelogger.net и сохранены на другом сайте на 3 дня";
    }




}
