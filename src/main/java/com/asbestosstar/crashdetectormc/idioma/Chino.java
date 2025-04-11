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
    
        @Override
    public String local_headless(String archivo) {
        return "崩溃检测报告在此处生成：" + archivo;
    }
    
     @Override
    // 中文图形界面文本
    public String texto_de_gui() {
        return "这是CrashDetector的图形界面。如果游戏正常关闭，请忽略。";
    }
    
        @Override
    // 中文翻译
    public String texto_de_buton_local_enlance() {
        return "查看报告";
    }

    @Override
    // 本地查看按钮下方文字
    public String texto_debajo_de_buton_local_enlance() {
        return "在浏览器中查看本地报告";
    }

    @Override
    // 分享按钮文本
    public String texto_de_buton_compartir_enlance() {
        return "分享报告";
    }

    @Override
    // 分享按钮下方说明
    public String texto_debajo_de_buton_compartir_enlance() {
        return "分享报告，您的日志将上传到 securelogger.net 并在其他站点保存 3 天";
    }
    
    
    
      @Override
public String problematico_jar() {
    return "<b>发现潜在问题的JAR文件（优先级：FATAL > 较高级别 > 较低行号）：</b>";
}

@Override
public String nivel() {
    return "<b>lvl: </b>";
}

@Override
public String possibladad_fatal() {
    return "<b>可能致命：</b> ";
}

@Override
public String modids_problematicos() {
    return "<b>发现潜在问题的modid（优先级：FATAL > 较低级别 > 较低行号）：</b>";
}

@Override
public String packages_problematicos() {
    return "<b>发现潜在问题的包（优先级：FATAL > 较低级别 > 较低行号）：</b>";
}

@Override
public String faltar_de_clases_fatales() {
    return "<b>发现缺失的致命类：</b>";
}

@Override
public String corchetes_ondulados() {
    return "<b>{}中的内容（重要性从高到低，仅显示前20）：</b>";
}

@Override
public String config_spongemixin_problematico(String archivo) {
    return "<b>检测到有问题的SpongeMixin配置： " + archivo + "</b>";
}

@Override
public String module_resolution_exception(String modules, String paquete) {
    return "你有一些包含重复包的模组: " + modules + " 重复的包 " + paquete.replace(".", "/") + "。你可以通过从 jar 文件中删除该包（文件夹）来解决此问题。你可以使用压缩软件（如 File-Roller,WinRAR 或 7-Zip）打开 jar 文件，或者将文件扩展名从 .jar 改为 .zip，然后删除文件夹，最后再将扩展名改回 .jar。";
}

@Override
public String modlauncher_mods_duplicado(String linea) {
    return "<b>你有重复的模组</b> " + linea.replace("from mod files", "来自模组文件");
}

@Override
public String mcforge_mod_suspechoso() {
    return "<b>MinecraftForge 可疑: 此模组存在问题:</b> ";
}

    
}
