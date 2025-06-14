package com.asbestosstar.crashdetector.idioma;

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
public String noRegistroDeLauncher() {
    return "未找到启动器日志！这可能会使调查变得复杂。\n"
            + "                \n"
            + "                获取正确日志的方法：\n"
            + "                - MultiMC/PolyMC/PrismLauncher/: 注意：自动检测到的日志不正确。\n"
            + "                  1. 打开实例界面\n"
            + "                  2. 转到“Minecraft Log”部分\n"
            + "                  3. 右键单击并复制内容\n"
            + "                - CurseForgeApp:\n"
            + "                  1. 不跳过启动器，重新启动游戏\n"
            + "                  \n";
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








}
