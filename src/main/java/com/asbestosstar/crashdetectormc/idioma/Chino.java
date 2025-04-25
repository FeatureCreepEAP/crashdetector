package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Config;
import com.asbestosstar.crashdetectormc.Idioma;

public class Chino implements Idioma {
    private final Config config = Config.obtenerInstancia();

    @Override
    public String carpeta_de_mods_no_valido() {
        return "<span style='color:#" + config.obtenerColorError() + "'>无效的mods文件夹</span>";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "<span style='color:#" + config.obtenerColorError() + "'>找不到CrashDetector的JAR文件</span>";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>正在查找PID: " + String.valueOf(pid) + "</span>";
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid) + ") 已终止！</span>";
    }

    @Override
    public String no_tenemos_jvm() {
        return "<span style='color:#" + config.obtenerColorError() + "'>没有JVM</span>";
    }

@Override
public String problema_con_graficas_ati() {
    return "<span style='color:#" + config.obtenerColorError() + "'>更新您的 ATI/AMD 驱动程序可能会有所帮助。请阅读此指南以解决问题: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>驱动程序更新指南</a> https://www.amd.com/zh-cn/support/download/drivers.html 下载 </span>";
}

    @Override
    public String probelma_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>某些旧版本在Nouveau显卡的早期加载界面有时会出现问题。</span>";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>您的显卡驱动存在问题。如果您使用AMD/ATI的GPU或APU，请更新AMD显卡驱动。如果您使用NVIDIA显卡，请确保将游戏和所有javaw.exe实例设置为使用独立显卡。阅读此指南： <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>驱动程序更新指南</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>您的FML早期窗口加载失败。要修复此问题，请转到(.minecraft/config/fml.toml)并将earlyWindowProvider设置为\"none\"。如果您使用的是Mac M1，请确保使用的是ARM版Java，而非Intel x64版。这也是驱动程序过时的常见问题。如果您使用的是Windows且禁用此设置无效，请查阅此指南： <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>驱动程序更新指南</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "<span style='color:#" + config.obtenerColorError() + "'>缺少必要的依赖项：</span>";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>" + linea.replace("Requested by", "请求自").replace("Expected range", "预期范围") + "</span>";
    }

    @Override
    public String local_headless(String archivo) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>您的CrashDetector报告在此 <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>查看报告</a></span>";
    }

    @Override
    public String texto_de_gui() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>这是CrashDetector的GUI界面。如果游戏正常关闭，请忽略此界面。</span>";
    }

    @Override
    public String texto_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorBoton() + "'>查看报告</span>";
    }

    @Override
    public String texto_debajo_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>在浏览器中查看本地报告。</span>";
    }

    @Override
    public String texto_de_buton_compartir_enlance() {
        return "分享报告";
    }

    @Override
    public String texto_debajo_de_buton_compartir_enlance() {
        return "分享报告。您的日志将上传至securelogger.net，报告将上传至其他站点。";
    }

    @Override
    public String problematico_jar() {
        return "<b style='color:#" + config.obtenerColorError() + "'>检测到有问题的JAR文件（优先处理FATAL，然后是高优先级和低优先级）：</b>";
    }

    @Override
    public String nivel() {
        return "<b style='color:#" + config.obtenerColorError() + "'> 等级：</b> ";
    }

    @Override
    public String possibladad_fatal() {
        return "<b style='color:#" + config.obtenerColorError() + "'>可能致命：</b> ";
    }

    @Override
    public String modids_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>检测到有问题的ModID（优先处理FATAL，然后是低优先级和低优先级）：</b>";
    }

    @Override
    public String packages_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>检测到有问题的包（优先处理FATAL，然后是低优先级和低优先级）：</b>";
    }

    @Override
    public String faltar_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>检测到缺失的关键类：</b>";
    }

    @Override
    public String corchetes_ondulados() {
        return "<b style='color:#" + config.obtenerColorError() + "'>在{}中的内容（最重要的在上方，仅显示前20个）：</b>";
    }

    @Override
    public String config_spongemixin_problematico(String archivo) {
        return "<b style='color:#" + config.obtenerColorError() + "'>检测到有问题的SpongeMixin配置： " + "</b>" + archivo;
    }

    @Override
    public String module_resolution_exception(String modules, String paquete) {
        return "<span style='color:#" + config.obtenerColorError() + "'>您的Mods存在重复的包： " + modules + " 重复包 " + paquete.replace(".", "/") + "。您可以通过删除jar中的包（文件夹）来解决此问题，可以使用WinRAR或7z等文件软件打开jar，也可以将文件扩展名从.jar改为zip后删除文件夹，再改回.jar。</span>";
    }

    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>检测到重复的Mods</b> " + linea.replace("from mod files", "来自mod文件");
    }

    @Override
    public String mcforge_mod_suspechoso() {
        return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge检测到可疑mod存在问题：</b> ";
    }

    @Override
    public String lithostichctov() {
        return "<b style='color:#" + config.obtenerColorError() + "'>CTOV需要lithostitched，您可以在此安装： <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#" + config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
    }

    @Override
    public String necesitasSodiumParaIris() {
        return "<b style='color:#" + config.obtenerColorError() + "'>要使用Iris着色器或Oculus，您需要SODIUM或其他加载器的兼容版本（Rubidium, Embedium, Bedium）</b>";
    }

    @Override
    public String kubeJSResourcePack(String mod_nombre) {
        return "<b style='color:#" + config.obtenerColorError() + "'>KubeJS扩展存在问题 </b>" + mod_nombre;
    }
    
    
    @Override
public String problema_con_graficas_nvidia_windows_viejo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "检测到Windows 11之前版本的NVIDIA驱动程序存在问题。"
            + "</span><br/><br/>"
            + "为确保Minecraft（以及当前的JVM）使用专用的NVIDIA GPU，请按照以下步骤操作：<br/><br/>"
            + "1. <strong>识别Java可执行文件：</strong><br/>"
            + "   - 此程序正在使用以下Java可执行文件： "
            + obtenerRutaJava() + "<br/>"
            + "   - 如果没有看到具体路径，可以通过在系统中搜索'java.exe'找到Java可执行文件。<br/><br/>"
            + "2. <strong>打开NVIDIA控制面板：</strong><br/>"
            + "   - 右键单击桌面并选择'NVIDIA控制面板'。<br/><br/>"
            + "3. <strong>配置首选GPU：</strong><br/>"
            + "   - 在NVIDIA控制面板中，转到'管理3D设置'。<br/>"
            + "   - 选择'程序设置'选项。<br/>"
            + "   - 点击'添加'并找到之前识别的Java可执行文件（例如：'java.exe'）。<br/>"
            + "   - 确保它已设置为使用'高性能NVIDIA处理器'。<br/><br/>"
            + "4. <strong>保存更改：</strong><br/>"
            + "   - 应用更改并关闭NVIDIA控制面板。<br/><br/>"
            + "5. <strong>重新启动Minecraft：</strong><br/>"
            + "   - 重新启动Minecraft以使更改生效。<br/><br/>"
            + "如果您使用的是Windows Server 2022或Windows 10，只要安装了最新的NVIDIA驱动程序，这些步骤均有效。<br/><br/>"
            + "注意：如果找不到NVIDIA控制面板，请确保NVIDIA驱动程序已正确安装。";
}



@Override
public String problema_con_graficas_nvidia_windows_nuevo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "检测到Windows 11/Server 2025或更高版本的NVIDIA驱动程序存在问题。"
            + "</span><br/><br/>"
            + "为确保Minecraft（以及当前的JVM）使用专用的NVIDIA GPU，请按照以下步骤操作：<br/><br/>"
            + "1. <strong>识别Java可执行文件：</strong><br/>"
            + "   - 此程序正在使用以下Java可执行文件： "
            + obtenerRutaJava() + "<br/>"
            + "   - 如果没有看到具体路径，可以通过在系统中搜索'java.exe'找到Java可执行文件。<br/><br/>"
            + "2. <strong>打开设置应用：</strong><br/>"
            + "   - 按下<code>Win + I</code>键打开设置应用。<br/>"
            + "   - 导航至<strong>系统 > 显示 > 图形</strong>。<br/><br/>"
            + "3. <strong>配置首选GPU：</strong><br/>"
            + "   - 在'图形'部分，点击'默认图形设置'。<br/>"
            + "   - 选择'桌面应用'，然后点击'浏览'。<br/>"
            + "   - 找到并选择之前识别的Java可执行文件（例如：'java.exe'）。<br/>"
            + "   - 添加后，在列表中选择Java应用程序，并将其配置为使用'高性能（NVIDIA）'。<br/><br/>"
            + "4. <strong>保存更改：</strong><br/>"
            + "   - 应用更改并关闭设置应用。<br/><br/>"
            + "5. <strong>重新启动Minecraft：</strong><br/>"
            + "   - 重新启动Minecraft以使更改生效。<br/><br/>"
            + "如果您使用的是Windows 11或Windows Server 2025+，只要安装了最新的NVIDIA驱动程序，这些步骤均有效。<br/><br/>"
            + "注意：如果找不到图形设置选项，请确保NVIDIA驱动程序已正确安装。";
}



@Override
public String segundo60Tick() {
    return "<b style='color:#" + config.obtenerColorError() + "'>您的服务器或世界有超过60秒的卡顿。这可能是由于模组导致服务器变慢，或者硬件性能不足。</b>";
}



@Override
public String noTieneMemoria() {
    return "<b style='color:#" + config.obtenerColorError() + "'>您没有足够的RAM/内存。需要分配更多内存。</b>";
}


@Override
public String theseus() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>如果您正在使用Theseus/ModrinthApp，我们无法提供太多帮助，因为Theseus没有启动器控制台。Theseus还存在其他问题，包括过时的Mod加载器版本、间谍软件、错误日志等。Modrinth公司也不诚实，他们虚假指控Mod开发者使用机器人来增加下载量，并且多次更改了他们的盈利声明。</b>";
}


@Override
public String noTieneConsolaDeLauncherCursedForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>您没有launcher_log.txt文件。我们需要这个文件来查找错误。这是由于启用了“跳过启动器启动”选项。请禁用它。</b>";
}
    
    
    
    
    
}
