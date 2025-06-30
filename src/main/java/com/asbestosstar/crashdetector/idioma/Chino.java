package com.asbestosstar.crashdetector.idioma;

import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;

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
    public String config_spongemixin_problematico() {
        return "<b style='color:#" + config.obtenerColorError() + "'>检测到有问题的SpongeMixin配置： " + "</b>";
    }

    @Override
    public String module_resolution_exception() {
        return "<span style='color:#" + config.obtenerColorError() + "'>你有包含重复包的模组。你可以通过从 JAR 文件中删除重复的包（文件夹）来解决此问题。你可以使用 WinRAR 或 7z 等压缩软件打开 JAR 文件，也可以将文件扩展名从 .jar 改为 .zip，然后删除文件夹，再将其改回 .jar 扩展名。</span>";
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
    
@Override
public String faltar_de_clases_advertencia() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>警告：检测到缺失的类：</b>";
}
@Override
public String noResultos() {
    return "<b style='color:#" + config.obtenerColorError() + "'>无结果</b>";
}

@Override
public String ubicacionesDeLogs() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>日志位置:</b>";
}

@Override
public String infoDeVerificaciones() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>以下是您的检查结果。修复日志的上半部分是首要任务。请慢慢来。</b>";
}


@Override
public String versionDeJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>请使用Java 17用于1.17-1.20.4版本，使用Java 21用于更新的版本。对于更旧的版本，请使用Java 8。[指南](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). 如果仍然有问题，可能是因为某些模组文件太新或太旧。</b>";
}

@Override
public String java22() {
    return "<b style='color:#" + config.obtenerColorError() + "'>由于ASM过时，Java 22及以上版本在1.20.5以下的Minecraft版本中无法与大多数模组加载器兼容。</b>" + versionDeJava();
}

@Override
public String javaObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java已过时 </b>" + versionDeJava();
}

@Override
public String jpms_modules_faltas(String mod_necesitas, String submod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>您需要从 " + submod + " 获取 JPMS 模块 " + mod_necesitas + "</b>";
}
    
@Override
public String null_pointer_error(String metodo, String objeto) {
    return "<b style='color:#" + config.obtenerColorError() + "'>无法调用 " + metodo + "，因为 " + objeto + " 为 null</b>";
}    

@Override
public String analisisAvanzado() {
    return "高级分析";
}


@Override
public String seleccionarCarpeta() {
    return "选择文件夹";
}

@Override
public String cadenaBusqueda() {
    return "搜索字符串";
}

@Override
public String usarRegex() {
    return "使用正则表达式";
}

@Override
public String ignorarMayusculas() {
    return "忽略大小写";
}

@Override
public String buscar() {
    return "搜索";
}

@Override
public String busquedaEnProgreso() {
    return "搜索进行中";
}

@Override
public String noSeEncontraronResultados() {
    return "未找到结果";
}

@Override
public String errorBusqueda() {
    return "搜索错误";
}

@Override
public String omitirYCerrar() {
    return "跳过并关闭";
}

@Override
public String guardarYCerrar() {
    return "保存并关闭";
}

@Override
public String pegaLosRegistrosAqui() {
    return "将日志粘贴到这里";
}

@Override
public String archivo() {
    return "文件";
}

@Override
public String incluir() {
    return "包含";
}

@Override
public String abrir() {
    return "打开";
}

@Override
public String endpointDeInforme() {
    return "报告端点";
}

@Override
public String sitoDeLogging() {
    return "日志站点：";
}

@Override
public String apiDeLogging() {
    return "日志API：";
}

@Override
public String anonimizarRegistros() {
    return "匿名化日志 (测试版)";
}

@Override
public String botonDeCompartirInforme() {
    return "分享报告及所有选定日志";
}

@Override
public String arco() {
    return "此对话框允许您使用 SecureLogger API 在 securelogger.net 上共享日志。"
            + "按下共享报告按钮时，您的报告将被发送到选定的端点（默认为 asbestosstar.egoism.jp）（可在底部更改）。您可以共享所有选定的日志以及报告。"
            + "如果您不想上传，请不要使用此对话框！我们不会在官方端点（https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb）处理您的报告；我们只会删除不允许的链接。代码在这里：https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb。这仅用于显示有关崩溃的信息和日志链接。然而，可以使用可能没有相同方法的自定义端点。您当前正在使用的报告站点是 " 
            + Config.obtenerInstancia().obtenerSitoDeInformes() + "，日志站点是 " 
            + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + "。您还可以通过单击单个日志名称旁边的共享按钮来共享单个日志而无需报告；日志将发送到选定的日志站点。CrashDetector 具有默认的日志匿名化功能，该功能会尝试删除用户名、UUID、访问令牌、会话 ID、IP 地址和其他数据。然而，它并不完美。此外，模组包作者可以禁用它。可以通过屏幕底部的复选框启用或禁用它。您是自己的数据控制者；您决定将数据上传到哪里。日志站点由第三方拥有，其所有权通常因隐私原因而隐藏。您需要对管理您的数据及相关的风险负全责。CrashDetector 共享对话框只是一个允许您管理这些内容的界面。了解 GDPR 和 ARCO 非常重要。";
}

@Override
public String enlaceDelReporte() {
    return "报告链接：";
}


@Override
public String guardarConfigDeCompartir() {
    return "保存分享配置";
}

@Override
public String registroDemasiadoGrande() {
    return "日志对于此日志站点来说太大了。请选择另一个并重试。";
}

@Override
public String errorConPublicarRegistro(String error) {
    return "发布日志时出错 " + error;
}

@Override
public String apiDeRegistroNoExiste() {
    return "日志API不存在。请在设置中更改日志API。";
}

@Override
public String errorSSL() {
    return "您遇到了SSL错误。这在旧版本的Java中很常见，"
            + "包括默认Minecraft启动器中的Java 8版本以及sun.com和java.com上的版本。"
            + "这会影响许多方面，例如MinecraftForge安装程序的JAR文件，"
            + "使用默认端点时分享CrashDetector报告的功能，需要互联网连接的一些模组，"
            + "以及一些日志站点。如果在尝试分享报告时遇到此问题，"
            + "请附上屏幕截图并选择与旧版Java 8兼容的日志站点。";
}


@Override
public String errorJavaFMLVersion(String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "不兼容的 JavaFML 版本：需要 " + requerido 
         + "，但找到的是 " + encontrado + "</b>";
}

@Override
public String errorJavaFML_MCForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "注意！JavaFML 需要特定版本的 Minecraft Forge</b>";
}

@Override
public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "JAR 文件 '" + archivoJar + "' 需要语言提供程序 '" + proveedor + "' 的版本 '"
         + requerido + "' 或更高版本，但仅找到版本 '" + encontrado + "'。</b>";
}

//@Override
//public String advertenciaMalwareFalso() {
//    return "<b style='color:#" + config.obtenerColorError() + "'>"
//         + "警告！Crash Assistant 是一个伪造的恶意软件检测器。它故意阻止游戏启动，无视您继续使用目标模组进行游戏的自由。 "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>查看 MalwareMod.java 代码</a>   "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>查看 JarInJarHelper.java 代码</a>. "
//         + "目前只有这个模组在他们的列表中，并且他们实际上只针对默认的日志站点，而该站点可以由用户更改，只有在明确选择使用内置日志共享功能时才会生效。CrashAssistant 不会检查当前使用的是哪个日志站点，也不会解释如何更改它（共享对话框底部有一个下拉菜单），无论配置了哪个站点，CrashAssistant 都会阻止游戏启动。在他们的消息中，他们说要自己做研究，那就去做吧，查看 CrashDetector 和 Crash Assistant 的代码，了解它们的作用，不要依赖权威的呼吁。</b>";
//}


@Override
public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "模组 '" + idMod + "' 因未找到所需类而失败: '"
         + claseNoEncontrada + "'. 请确保所有依赖项已正确安装。</b>";
}


@Override
public String waterMediaTL() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Watermedia 已阻止使用 TLauncher 进行游戏。</b>";
}

@Override
public String optifineObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "您正在使用适用于过时版本的Minecraft的Optifine版本。您需要使用与您当前使用的Minecraft版本对应的Optifine版本。</b>";
}

@Override
public String servicioMLNoPudoCargar(String servicio) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "无法加载 ModLauncher 服务: </b>" + servicio + "。";
}

@Override
public String errorConJSONDeRegistro(String archivoJar, String recurso) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "解析 JAR 文件 '" + archivoJar + "' 中的 JSON 文件 '" + recurso + "' 时出错。"
         + "注册存在问题。</b>";
}

@Override
public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
    return "<span style='color:#" + config.obtenerColorError() + "'>" 
        + "错误：模组 '" + modId + "' 需要 '" + dependencia + "' 的版本 '" + requerido 
        + "' 或更高版本，但找到的是 '" + actual + "'。"
        + "</span>";
}

@Override
public String gpu_no_compatible() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "您的GPU不支持此游戏版本所需的OpenGL版本。请更新驱动程序或更换显卡。</b>";
}


@Override
public String recomendacionMemoria() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "增加分配给游戏的内存或减少模组/插件的使用。</b>";
}

@Override
public String error32BitMemoria() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "检测到32位JVM：无法使用超过4GB的RAM。 "
         + "安装64位JVM以充分利用您的可用内存。</b>";
}

@Override
public String permGenError() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "PermGen内存严重错误。增加永久内存空间或减少类加载。</b>";
}


public String errorCompatibilidadJava8() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 8与现代版本之间的兼容性错误</b>";
}

public String errorJava9NoSoportado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "不支持Java 9+ - 使用Java 8以适配旧版Forge</b>";
}

public String errorJava8Requerido() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "需要Java 8（版本52.0）。请更新或正确配置</b>";
}


@Override
public String errorDeBloqueTeselado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "严重兼容性错误：此版本不支持方块。 "
         + "请确保模组和服务器版本兼容</b>";
}

@Override
public String errorMonitorLWJGL() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "显示器配置错误: "
         + "无法设置屏幕模式。 "
         + "请检查：</b>"
         + "<br>- 多显示器配置"
         + "<br>- 图形卡驱动程序是否已更新"
         + "<br>- 系统支持的分辨率";
}

@Override
public String errorOpcionesGCJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java选项错误: "
         + "垃圾回收器选项冲突。 "
         + "请确保未在JVM参数中组合多个GC算法</b>";
}

@Override
public String errorConfigMCForge() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Forge配置严重错误: "
         + "配置文件损坏或不完整。 "
         + "删除'config'文件夹并重新启动游戏</b>";
}

@Override
public String problema_con_graficas_intel() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "检测到Intel HD Graphics驱动程序错误。解决方案：</b>"
         + "<br>1. 从<a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a>更新Intel驱动程序（最低版本15.xx.xx.xx）"
         + "<br>2. 在Minecraft中：选项 → 视频 → 启用'Enable VBOs'和'VSync'"
         + "<br>3. 在启动器中为游戏分配1GB-2GB的RAM"
         + "<br>4. 更新期间临时禁用杀毒软件/防火墙";
}

public String nombre_de_faltar_de_clases_advertencia() {
    return "警告：检测到缺失的类";
}

public String nombre_de_bloque_teselado() {
    return "方块渲染错误";
}

public String nombre_de_contento_de_stacktrace() {
    return "堆栈跟踪分析";
}

public String nombre_de_cursed_consola() {
    return "不完整的CurseForge控制台";
}

public String nombre_de_early_window() {
    return "早期窗口错误 (FMLEarlyWindow)";
}

public String nombre_de_drivers() {
    return "显卡驱动问题";
}

public String nombre_de_error_de_config_mcforge() {
    return "MCForge配置损坏";
}

public String nombre_de_error_de_monitor_lwjgl() {
    return "显示模式失败 (LWJGL)";
}

public String nombre_de_fabricmc_runtime_error_provided_by() {
    return "FabricMC初始化错误";
}

public String nombre_de_falta_module_jmps() {
    return "缺少JPMS模块";
}

public String nombre_de_faltar_de_clases() {
    return "严重缺失的类";
}

public String nombre_de_faltas_dependencias_de_modlauncher() {
    return "缺少ModLauncher依赖";
}

public String nombre_de_java_versiones() {
    return "不兼容的Java版本";
}

public String nombre_de_faltar_de_kubejs_resourcepack() {
    return "KubeJS资源错误";
}

public String nombre_de_lenguaje_proveedor_check() {
    return "不兼容的语言提供者";
}

public String nombre_de_faltar_de_liyhostictchctov() {
    return "Litchhost错误";
}

public String nombre_de_malware_falso_crash_assistant() {
    return "虚假恶意软件检测";
}

public String nombre_de_mcforge_mod_sespechoso() {
    return "检测到可疑模组";
}

public String nombre_de_mods_duplicados_modlauncher() {
    return "ModLauncher中的重复模组";
}

public String nombre_de_modules_duplicados_jmps() {
    return "JPMS模块冲突";
}

public String nombre_de_necesitas_sodium() {
    return "Iris需要Sodium";
}

public String nombre_de_no_puede_analizar_json_de_registro() {
    return "无法解析JSON注册表";
}

public String nombre_de_no_tiene_memoria() {
    return "内存不足";
}

public String nombre_de_null_pointer() {
    return "空指针错误 (NullPointerException)";
}

public String nombre_de_opciones_java_gc_invalidas() {
    return "无效的Java GC选项";
}

public String nombre_de_optifine_obsoleta() {
    return "过时/不兼容的OptiFine";
}

public String nombre_de_60_segundo_trick() {
    return "关键服务器刻 (60秒)";
}

public String nombre_de_servicio_de_modlauncher_no_funciona() {
    return "ModLauncher服务失败";
}

public String nombre_de_spongemixin_configs_problematicos() {
    return "有问题的SpongeMixing配置";
}

public String nombre_de_theseus() {
    return "Theseus不兼容";
}

public String nombre_de_watermedia_tl() {
    return "TLauncher不被WATERMeDIA支持";
}

@Override
public String auditorias_transformer() {
    return "Transformer 审计";
}

@Override
public String auditorias_transformer_detectadas() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "这些是Vanilla启动器中Transformer审计内容的结果。通常它不如StackTrace分析器精确，但Vanilla启动器并不总是有{}的内容</b>";
}

@Override
public String descripcionEscanerMCreator() {
    return "这会扫描你的模组，查找由MCreator创建的模组。尽管大多数MCreator模组都没有问题，并且有许多优秀的MCreator模组，但有时它们会出现问题并带来不良声誉。这有助于识别它们。请注意，即使是评分很高的模组也可能并非真正由MCreator制作；例如，它们可能与MCreator有集成关系。";
}

@Override
public String escanear() {
    return "扫描";
}

@Override
public String cargando() {
    return "加载中";
}

@Override
public String codigo() {
	// TODO Auto-generated method stub
	return "zh";
}



@Override
public String inicioApp() {
    return "游戏/应用启动";
}

@Override
public String adjustesCrashDetector() {
    return "崩溃检测器设置";
}

@Override
public String confidencialidad() {
    return "隐私";
}

@Override
public String tooltip() {
    return "工具提示";
}

@Override
public String config() {
    return "配置";
}

@Override
public String abrirCarpeta() {
    return "打开文件夹";
}

@Override
public String actualizar() {
    return "更新";
}

@Override
public String anadirRegistro() {
    return "添加日志";
}

@Override
public String usarIdiomaDelSistema() {
    return "使用系统语言";
}

@Override
public String volver() {
    return "返回";
}

@Override
public String colorFondo() {
    return "背景颜色 (#RRGGBB):";
}

@Override
public String colorTexto() {
    return "文字颜色 (#RRGGBB):";
}

@Override
public String colorBoton() {
    return "按钮颜色 (#RRGGBB):";
}

@Override
public String colorCajaTexto() {
    return "文本框颜色 (#RRGGBB):";
}

@Override
public String colorEnlace() {
    return "链接颜色 (#RRGGBB):";
}

@Override
public String colorTitulosConsolas() {
    return "控制台标题颜色 (#RRGGBB):";
}

@Override
public String colorError() {
    return "错误颜色 (#RRGGBB):";
}

@Override
public String colorAdvertencia() {
    return "警告颜色 (#RRGGBB):";
}

@Override
public String colorInfo() {
    return "信息颜色 (#RRGGBB):";
}

@Override
public String colorTitulo() {
    return "标题颜色 (#RRGGBB):";
}

@Override
public String colorEnlaceTexto() {
    return "链接文字颜色 (#RRGGBB):";
}

@Override
public String transformacionDeMinecraftCodigo0() {
    return "仅在失败时打开CrashDetector";
}


@Override
public String activar_parche() {
    return "激活补丁";
}

@Override
public String noHaySolucionDisponible() {
    return "没有可用的解决方案";
}

@Override
public String error() {
    return "错误";
}

@Override
public String error_al_eliminar_jar() {
    return "删除Jar时出错";
}

@Override
public String jar_eliminado_exitosamente() {
    return "Jar删除成功";
}

@Override
public String exito() {
    return "成功";
}

@Override
public String eliminar() {
    return "删除";
}

@Override
public String error_al_eliminar_paquete() {
    return "删除包时出错";
}

@Override
public String paquete_eliminado_exitosamente() {
    return "包删除成功";
}

@Override
public String eliminar_paquete() {
    return "删除包";
}

@Override
public String no_se_encontraron_mods_con_paquete() {
    return "未找到带有包的模组";
}

@Override
public String no_se_puede_eliminar_paquete() {
    return "无法删除包";
}

@Override
public String eliminar_jar() {
    return "删除Jar";
}

@Override
public String no_se_encontraron_mods_duplicados() {
    return "未找到重复的模组";
}


@Override
public String archivo_no_encontrado() {
    return "文件未找到";
}

@Override
public String directorio_eliminado() {
    return "目录已删除";
}

@Override
public String error_al_eliminar_jar_anidado() {
    return "删除嵌套Jar时出错";
}

@Override
public String archivo_interno_no_encontrado() {
    return "未找到内部文件";
}

@Override
public String archivo_eliminado() {
    return "文件已删除";
}

@Override
public String error_al_eliminar_archivo() {
    return "删除文件时出错";
}

@Override
public String archivo_externo_no_valido() {
    return "无效的外部文件";
}

@Override
public String elemento_mod_eliminado() {
    return "模组元素已删除";
}

@Override
public String error_al_reemplazar_jar_externo() {
    return "替换外部Jar时出错";
}

@Override
public String error_al_eliminar_elemento_mod() {
    return "删除模组元素时出错";
}

@Override
public String error_al_eliminar_directorio() {
    return "删除目录时出错";
}

@Override
public String formato_invalido_para_jar_anidado() {
    return "嵌套Jar格式无效";
}

@Override
public String jar_anidado_eliminado() {
    return "嵌套Jar已删除";
}

@Override
public String error_al_limpiar_temporales() {
    return "清理临时文件时出错";
}

@Override
public String mensaje_de_trace_fatal_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>致命跟踪消息最后 (未翻译):</b> ";
}

@Override
public String mensaje_de_trace_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>跟踪消息最后 (未翻译):</b> ";
}


@Override
public String solucionParaAdvertenciaFaltasClases() {
    return "您有缺失的类（警告），通常情况下没有问题，但并非总是如此。错误的核心模组或缺失的依赖是这个问题的常见原因。";
}

@Override
public String solucionFaltasClases() {
    return "您有缺失的类（严重），这非常重要。错误的核心模组或缺失的依赖是这个问题的常见原因。";
}

@Override
public String solucionParaJavaInstallar() {
    return "两个启动器都有正确的Java版本，但并非所有版本都正确；您可以从系统中的包管理器或通过按钮安装正确的Java版本。";
}

@Override
public String error_animacion_no_encontrada() {
    return "<b style='color:#" + config.obtenerColorError() + "'>缺少动画的模组: " + "</b>";
}

@Override
public String nombre_de_error_animacion_minecraft() {
    return "NoSuchElementException（无元素异常）动画缺失";
}

@Override
public String no_se_encontraron_mods_para_eliminar() {
    return "未找到可删除的模组";
}

@Override
public String opcionesGCInvalidas() {
    return "将冲突的垃圾回收选项替换为 -XX:+UseG1GC";
}

@Override
public String aumentarMemoriaHeap() {
    return "使用 -Xmx 选项增加堆内存大小。";
}

@Override
public String aumentarMemoriaPermgen() {
    return "使用 -XX:MaxPermSize 选项增加永久代内存大小。";
}

@Override
public String utilizarJVM64Bits() {
    return "使用 64 位 JVM 来增加可用内存。";
}

@Override
public String optimizarCodigo() {
    return "优化代码以减少内存使用并提高性能。";
}

@Override
public String utilizarRecolectorBasuraEficiente() {
    return "使用高效的垃圾收集器来减少应用程序暂停。";
}

@Override
public String modulos() {
    return "模块";
}

@Override
public String paquete() {
    return "包";
}

@Override
public String solucionRegistrosMalMapeados() {
    return "存在缺失的ID。常见原因包括缺少模组或物品数据缺失。常见的数据文件夹有 datafiedcontents/ 和 kubejs/ 或其他模组文件夹。";
}

@Override
public String nombre_de_registros_mal_mapeados() {
    return "映射错误的记录";
}

public String mensajeCierreAuthMe() {
    return "<b style='color:#" + config.obtenerColorError() + "'>插件 'AuthMe' 加载失败并导致服务器关闭。</b> ";
}

public String nombreProblemaCierreAuthMe() {
    return "由 AuthMe 引起的关闭问题";
}

public String solucionCierreAuthMe() {
    return "'stopServer' 规则已更改为 'true'。";
}

public String solucionConfigurarPluginAuthMe() {
    return "配置插件 AuthMe (plugins/AuthMe/config.yml)";
}

public String solucionInstalarVersionDiferenteAuthMe() {
    return "安装不同版本的 'AuthMe' 插件";
}

public String solucionEliminarPluginAuthMe() {
    return "移除插件 'AuthMe'";
}

@Override
public String mensajeProblemaCargaMultiverso(String nombreMundo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>世界 '" + nombreMundo + "' 无法加载，因为它包含错误且可能已损坏。</b> ";
}

@Override
public String nombreProblemaCargaMultiverso() {
    return "Multiverse 世界加载问题";
}

@Override
public String solucionRepararMundo(String nombreMundo) {
    return "修复世界 '" + nombreMundo + "'，例如使用 Minecraft Region Fixer 或 MCEdit。";
}

@Override
public String solucionEliminarCarpetaMundo(String nombreMundo) {
    return "删除世界文件夹 '" + nombreMundo + "'。";
}

@Override
public String mensajeConfiguracionPermissionsEx() {
    return "<b style='color:#" + config.obtenerColorError() + ">'PermissionsEx' 插件的配置无效。</b>";
}

@Override
public String nombreProblemaConfiguracionPermissionsEx() {
    return "PermissionsEx 配置问题";
}

@Override
public String solucionConfigurarPermissionsEx() {
    return "配置插件 PermissionsEx (plugins/PermissionsEx/permissions.yml)";
}

@Override
public String solucionEliminarPluginPermissionsEx() {
    return "删除插件 'PermissionsEx'";
}

@Override
public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
    return "<b style='color:#" + config.obtenerColorError() + "'>发现多个同名插件 '" + nombrePlugin + "': '" + primerPath + "' 和 '" + segundoPath + "'。</b> ";
}

@Override
public String nombreProblemaNombrePluginAmbiguo() {
    return "插件名称冲突问题";
}

@Override
public String solucionEliminarPlugin(String nombrePlugin) {
    return "删除插件 '" + nombrePlugin + "'";
}

@Override
public String mensajeCargaChunk() {
    return "<b style='color:#" + config.obtenerColorError() + "'>加载世界区块时发生异常。</b> ";
}

@Override
public String nombreProblemaCargaChunk() {
    return "区块加载异常";
}

@Override
public String solucionEliminarChunk() {
    return "使用 MCEdit 或删除区域文件来移除有问题的区块。";
}


@Override
public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
    return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + "' 无法执行命令 '/" + comando + "'。</b>";
}

@Override
public String nombreProblemaExcepcionComandoPlugin() {
    return "执行插件命令时发生异常";
}

@Override
public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
    return "安装插件 '" + nombrePlugin + "' 的其他版本";
}

@Override
public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
    return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + 
           "' 需要依赖 '" + dependencia + "'。</b>";
}

@Override
public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
    StringBuilder deps = new StringBuilder();
    for (int i = 0; i < dependencias.size(); i++) {
        if (i > 0) deps.append(", ");
        deps.append("'").append(dependencias.get(i)).append("'");
    }
    return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + 
           "' 缺少以下必需依赖: " + deps.toString() + "。</b>";
}

@Override
public String nombreProblemaDependenciaPlugin() {
    return "缺失插件依赖";
}

@Override
public String solucionInstalarPlugin(String nombrePlugin) {
    return "安装插件 '" + nombrePlugin + "'";
}

@Override
public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
    return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + 
           "' 需要版本为 '" + versionAPI + "' 的 API，与当前服务器不兼容。</b>";
}

@Override
public String nombreProblemaVersionAPIIncompatible() {
    return "API 版本不兼容";
}

@Override
public String solucionInstalarVersionServidor(String version) {
    return "安装服务器软件的 '" + version + "' 版本。";
}

@Override
public String mensajeMundoDuplicado(String nombreMundo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>世界 '" + nombreMundo + 
           "' 是另一个世界的副本，无法加载。</b> ";
}

@Override
public String nombreProblemaMundoDuplicado() {
    return "重复的世界";
}

@Override
public String solucionEliminarUID(String nombreMundo) {
    return "删除世界 '" + nombreMundo + "' 中的 'uid.dat' 文件";
}

@Override
public String solucionEliminarMundo(String nombreMundo) {
    return "删除世界文件夹 '" + nombreMundo + "'";
}


@Override
public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
    String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
    return "<b style='color:#" + config.obtenerColorError() + "'>位于 " + coords + " 的区块实体 '" + nombre + 
           "'（类型为 '" + tipo + "'）在tick时发生错误。</b> ";
}

@Override
public String nombreProblemaTickingEntidadBloque() {
    return "问题区块实体";
}

@Override
public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
    String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
    return "使用 MCEdit 或直接编辑世界文件，删除位于 " + coords + " 的 '" + nombre + "' 实体。";
}

@Override
public String mensajeModDuplicadoFabric(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + nombreMod + "' 安装了多个版本。</b> ";
}

@Override
public String nombreProblemaModDuplicadoFabric() {
    return "Fabric 中的重复模组";
}

@Override
public String solucionEliminarModDuplicado(String rutaMod) {
    return "删除重复的模组文件：" + rutaMod;
}

@Override
public String mensajeModIncompatible(String primerMod, String segundoMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + primerMod + 
           "' 和 '" + segundoMod + "' 彼此不兼容。</b> ";
}

@Override
public String nombreProblemaModIncompatibleFabric() {
    return "Fabric 中的不兼容模组";
}

@Override
public String solucionEliminarMod(String nombreMod) {
    return "删除模组 '" + nombreMod + "'";
}

@Override
public String mensajeModFatal(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + nombreMod + "' 出现严重错误，无法运行。</b>";
}

@Override
public String nombreProblemaModFatal() {
    return "模组出现致命错误";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>以下模组是必需的但未安装：" + deps.toString() + "。</b>";
}

@Override
public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
    if (version == null || version.isEmpty()) {
        return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + nombreMod + 
               "' 需要依赖模组 '" + dependencia + "'。</b>";
    } else {
        return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + nombreMod + 
               "' 需要依赖模组 '" + dependencia + "' 的版本 " + version + "。</b>";
    }
}

@Override
public String nombreProblemaDependenciaMod() {
    return "缺少模组依赖";
}

@Override
public String solucionInstalarMod(String nombreMod) {
    return "安装模组 '" + nombreMod + "'";
}

@Override
public String solucionInstalarModConVersion(String nombreMod, String version) {
    return "安装模组 '" + nombreMod + "' 的版本 " + version;
}

@Override
public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
    return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + 
           "' 不兼容 Folia 的区域性 Tick。</b> ";
}

@Override
public String mensajePluginTickingRegionalPlural(List<String> plugins) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("以下插件不兼容 Folia 的区域性 Tick：");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            sb.append(", ");
        }
        sb.append("'").append(plugins.get(i)).append("'");
    }
    
    sb.append("。</b>");
    return sb.toString();
}

@Override
public String nombreProblemaPluginTickingRegional() {
    return "插件与区域性 Tick 不兼容";
}

@Override
public String solucionInstalarSoftwareSinTickingRegional(String software) {
    return "安装一个没有区域性 Tick 的版本，例如 " + software;
}

@Override
public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + nombreMod + 
           "' 在数据包中缺失。</b>";
}

@Override
public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("以下模组在数据包中缺失：");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" 和 ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
    }
    
    sb.append("。</b>");
    return sb.toString();
}

@Override
public String nombreProblemaModFaltanteEnDatapack() {
    return "数据包中缺少模组";
}

@Override
public String mensajeModExcepcionSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + nombreMod + "' 引发了错误。</b>";
}

@Override
public String mensajeModExcepcionPlural(List<String> mods) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("以下模组引发了错误：");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" 和 ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
    }
    
    sb.append("。</b>");
    return sb.toString();
}

@Override
public String nombreProblemaModExcepcion() {
    return "Forge 模组引发异常";
}

@Override
public String solucionInstalarVersionDiferenteMod(String nombreMod) {
    return "安装模组 '" + nombreMod + "' 的其他版本";
}

@Override
public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
    return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + nombreMod + 
           "' 不兼容 Minecraft 版本 " + versionMC + "。</b>";
}

@Override
public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("以下模组不兼容对应的 Minecraft 版本：");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            sb.append(", ");
        }
        sb.append("'").append(mods.get(i)).append("'");
        sb.append("（Minecraft ").append(versionesMC.get(i)).append("）");
    }
    
    sb.append("。</b>");
    return sb.toString();
}

@Override
public String nombreProblemaModIncompatibleConMinecraft() {
    return "模组与 Minecraft 不兼容";
}

@Override
public String solucionInstalarVersionForge(String versionMC) {
    return "安装适用于 Minecraft " + versionMC + " 的 Forge 版本";
}

@Override
public String mensajeDependenciaModFaltante(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>" + nombreMod + " 模组缺失，无法加载世界或插件。</b>";
}

@Override
public String nombreProblemaDependenciaModFaltante() {
    return "缺失模组";
}

@Override
public String mensajeWorldModFaltanteSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>该世界保存时包含模组 '" + nombreMod + 
           "'，但现在找不到该模组。</b>";
}

@Override
public String mensajeWorldModFaltantePlural(List<String> mods) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("该世界保存时包含以下模组，但现在这些模组都找不到了：");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" 和 ");
            } else {
                sb.append("、");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
    }
    
    sb.append("。</b>");
    return sb.toString();
}

@Override
public String nombreProblemaWorldModFaltante() {
    return "世界中缺少模组";
}

@Override
public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
    return "<b style='color:#" + config.obtenerColorError() + "'>世界保存时使用的是模组 '" + nombreMod + 
           "' 的版本 " + versionEsperada + "，但现在运行的是版本 " + versionActual + "。</b>";
}

@Override
public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas, List<String> versionesActuales) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("以下模组在保存的世界中存在版本差异：");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" 和 ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
        sb.append("（保存版本：").append(versionesEsperadas.get(i)).append("，当前版本：").append(versionesActuales.get(i)).append("）");
    }
    
    sb.append("。</b>");
    return sb.toString();
}

@Override
public String nombreProblemaVersionModMundo() {
    return "保存的世界中的模组版本不一致";
}

@Override
public String mensajeVersionDowngrade() {
    return "<b style='color:#" + config.obtenerColorError() + "'>你尝试加载了一个由更新版 Minecraft 创建的世界。</b>";
}

@Override
public String nombreProblemaVersionDowngrade() {
    return "尝试从较新版本加载世界";
}

@Override
public String solucionVersionDowngrade() {
    return "安装更新的 Minecraft 版本。";
}

@Override
public String solucionGenerarNuevoMundo() {
    return "生成一个新世界。";
}

@Override
public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
    return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + 
           "' 需要依赖插件 '" + dependencia + "'。</b>";
}

@Override
public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("以下插件需要未安装的依赖项：");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            sb.append(", ");
        }
        sb.append("'").append(plugins.get(i)).append("'（").append(dependencias.get(i)).append("）");
    }
    
    sb.append("。</b>");
    return sb.toString();
}

@Override
public String nombreProblemaDependenciaPluginFaltante() {
    return "插件缺少依赖";
}

@Override
public String mensajePluginIncompatibleSingular(String nombrePlugin) {
    return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + 
           "' 与当前服务器版本不兼容。</b>";
}

@Override
public String mensajePluginIncompatiblePlural(List<String> plugins) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("以下插件与当前服务器版本不兼容：");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            if (i == plugins.size() - 1) {
                sb.append(" 和 ");
            } else {
                sb.append("、");
            }
        }
        sb.append("'").append(plugins.get(i)).append("'");
    }
    
    sb.append("。</b>");
    return sb.toString();
}

@Override
public String nombreProblemaPluginIncompatible() {
    return "插件与 PocketMine-MP 不兼容";
}

@Override
public String mensajePluginEjecucionSingular(String nombrePlugin) {
    return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + "' 在运行期间引发错误。</b>";
}

@Override
public String mensajePluginEjecucionPlural(List<String> plugins) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("以下插件在运行期间引发错误：");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            if (i == plugins.size() - 1) {
                sb.append(" 和 ");
            } else {
                sb.append("、");
            }
        }
        sb.append("'").append(plugins.get(i)).append("'");
    }
    
    sb.append("。</b>");
    return sb.toString();
}

@Override
public String nombreProblemaPluginEjecucion() {
    return "运行期间出错的插件";
}

@Override
public String nombreLegacyRandomSourceMultiHilos() {
    return "LegacyRandomSource 多线程";
}

@Override
public String mensajeLegacyRandomSourceMultiHilos() {
    return "<b style='color:#" + config.obtenerColorError() + "'>检测到多个线程访问 LegacyRandomSource 类。你可以通过安装 'Unsafe World Random Access Detector' 或 'C2ME' 模组获取更多信息。</b>";
}

@Override
public String desdeUltimoExito() {
    return "自上次成功以来";
}

@Override
public String noHayCambios() {
    return "无更改";
}

@Override
public String desdeUltimoIntento() {
    return "自上次尝试以来";
}

@Override
public String fallo() {
    return "失败";
}

@Override
public String diferentesDeLasMods() {
    return "与模组不同";
}

@Override
public String historialDeMods() {
    return "模组历史记录";
}

@Override
public String archivo0() {
    return "文件0";
}

@Override
public String archivo1() {
    return "文件1";
}

@Override
public String comparar() {
    return "比较";
}

@Override
public String seleccionarDosArchivos() {
    return "选择两个文件";
}

@Override
public String archivoExito() {
    return "成功文件";
}

@Override
public String archivoFalla() {
    return "失败文件";
}

@Override
public String errorComparandoArchivos() {
    return "比较文件时出错";
}

@Override
public String comparando() {
    return "正在比较";
}

@Override
public String con() {
    return "与";
}


@Override
public String descripcionPanelHistoriaMods() {
    return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
        + "<p><b>模组历史比较面板</b></p>"
        + "<p>此面板允许您对比不同运行会话的两个模组列表。从左侧选择一个文件，右侧选择另一个，以查看它们之间的变化。</p>"
        
        + "<h3>使用方法：</h3>"
        + "<ol>"
        + "<li><b>选择文件：</b>点击文件名旁边的单选按钮。"
        + "以<span style='color: #4CAF50; font-weight: bold;'>.exito</span>结尾的文件表示成功会话，"
        + "而以<span style='color: #F44336; font-weight: bold;'>.falla</span>结尾的文件则代表失败。</li>"
        
        + "<li><b>自动对比：</b>按下'Compare'按钮后系统将分析所选列表，并显示添加(+)或删除(-)的模组。</li>"
        
        + "<li><b>结果展示：</b>结果以颜色编码的HTML格式呈现："
        + "<ul>"
        + "<li><span style='color: green;'>+ 添加的模组</span></li>"
        + "<li><span style='color: red;'>- 删除的模组</span></li>"
        + "</ul></li>"
        + "</ol>"
        
        + "<h3>主要功能：</h3>"
        + "<ul>"
        + "<li>支持任意组合的成功/失败文件对比。</li>"
        + "<li>双向差异展示以便精准跟踪变更。</li>"
        + "<li>支持长模组列表滚动浏览。</li>"
        + "<li>集成示意图提升视觉理解。</li>"
        + "</ul>"
        
        + "<p>用心开发，助您追踪配置中的更改 <3</p>"
        + "</body></html>";
}


@Override
public String tieneErrorIPV6() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
           + "您可能遇到了与 IPv6 相关的问题。"
           + "有两种解决方案："
           + "1) 在启动器中添加 JVM 参数 <code>-Djava.net.preferIPv4Stack=true</code>，或者"
           + "2) 在 CrashDetector 中使用 'QuickFix' 按钮自动启用此设置的补丁。"
           + "</b>";
}

@Override
public String parcheIPv4() {
    return "IPv4/6 补丁";
}

@Override
public String carpetaHMCL() {
    return "HMCL 文件夹（仅限 HelloMineCraftLauncher）";
}

@Override
public String descripcionCurseforge() {
    return "注意：如果在 设置 > Minecraft 中启用了“跳过启动器”，将不会生成日志";
}

@Override
public String descripcionPrism() {
    return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/衍生版本：右键实例，选择“编辑实例”。在打开的窗口中查找“Minecraft 日志”或类似部分。<br>" +
           "这些日志包含标准输出（STDOUT），对于诊断错误至关重要。";
}

@Override
public String descripcionHMCL() {
    return "HMCL（HelloMinecraftLauncher）：请选择 HMCL 的安装目录，并选择其中的「.hmcl」文件夹。HMCL 的日志保存在这里，包含重要的错误信息。<br>";
}

@Override
public String descripcionFenix() {
    return "LauncherFenix：启动器有一个开发标签页显示详细日志。您可以在启动器设置菜单中找到该标签页。";
}

@Override
public String descripcionATLauncher() {
    return "ATLauncher：日志显示在一个弹出窗口中，有复制和上传按钮。游戏启动时会自动生成日志，包含关键的诊断信息。";
}

@Override
public String descripcionGDLauncher() {
    return "GDLauncher：右键点击实例并选择「设置」。然后进入日志选项卡查看标准输出（STDOUT）中的重要信息。";
}

@Override
public String descripcionLinksMarkdown() {
    return "Markdown 链接：在此粘贴任何包含 Markdown 格式日志的链接。系统将尝试自动提取日志链接（如 latest.log、launcher_log.txt、debug.log 等）并进行分析。";
}

@Override
public String noRegistroLauncherTitulo() {
    return "未找到启动器日志";
}

@Override
public String imagenNoEncontrada() {
    return "图片未找到";
}

@Override
public String noRegistroDeLauncher() {
    return "通用：请选择你正在使用的启动器类型。启动器日志（launcher_log.txt、stdout 等）包含了 latest.log 中没有的关键错误信息。CrashDetector 无法读取你的启动器日志 —— 可能是没有生成日志文件，你需要手动粘贴日志内容。<br>" +
           "更多信息请参考 <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">此问题</a>。这些日志包含标准输出（STDOUT），是诊断许多错误所必需的。";
}






}
