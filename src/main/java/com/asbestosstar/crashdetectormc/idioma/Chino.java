package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Idioma;

public class Chino implements Idioma {

    @Override
    public String carpeta_de_mods_no_valido() {
        return "这不是一个有效的模组文件夹";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "我不知道CrashDetector的JAR文件在哪里";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "正在查找PID: " + String.valueOf(pid);
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "(PID: " + String.valueOf(pid) + ") 已死！";
    }

    @Override
    public String no_tenemos_jvm() {
        return "我们没有JVM";
    }
    
    @Override
    public String probelma_con_graficas_ati() {
        return "更新你的驱动程序可能会有所帮助。请注意，当驱动程序处于损坏状态时，通过常规方式查找更新将找不到任何内容，因此请务必遵循链接中的指南。重要提示：如果你使用的是Nvidia显卡，请确保在Windows设置和Nvidia控制面板中，将所有与Minecraft相关的程序（如Java和启动器）配置为优先使用高性能图形。阅读此指南以解决问题：https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }

    @Override
    public String probelma_con_graficas_nouveau() {
        return "某些旧版本有时会在早期加载屏幕上出现一些与Nouveau显卡相关的问题。";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "你的显卡驱动程序存在问题。如果你使用的是AMD/ATI GPU或APU，请更新你的AMD显卡驱动程序。如果你使用的是NVIDIA显卡，请确保将游戏以及所有javaw.exe实例设置为使用独立显卡。阅读此指南：https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }

    @Override
    public String fmlEarlyWindow() {
        return "你的FML Early窗口正在失败。"
                + "要更改此设置，请前往 (.)minecraft/config/fml.toml "
                + "将earlyWindowProvider修改为 earlyWindowProvider=\"none\" "
                + "如果你使用的是M1 Mac，请确保你使用的是ARM版本的Java，而不是Intel x64版本。"
                + "如果你的驱动程序过时，这也是一个常见问题。如果在Windows上禁用此功能后仍无法解决问题，请参考此指南：https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "你缺少必要的依赖项：";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return linea.replace("Requested by", "请求方").replace("Expected range", "预期范围");
    }
    
}
