package com.asbestosstar.crashdetector.idioma;

import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

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
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") 已终止！</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>没有JVM</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>更新您的 ATI/AMD 驱动程序可能会有所帮助。请阅读此指南以解决问题: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>驱动程序更新指南</a> https://www.amd.com/zh-cn/support/download/drivers.html 下载 </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>某些旧版本在Nouveau显卡的早期加载界面有时会出现问题。</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>您的显卡驱动存在问题。如果您使用AMD/ATI的GPU或APU，请更新AMD显卡驱动。如果您使用NVIDIA显卡，请确保将游戏和所有javaw.exe实例设置为使用独立显卡。阅读此指南： <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>驱动程序更新指南</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>您的FML早期窗口加载失败。要修复此问题，请转到(.minecraft/config/fml.toml)并将earlyWindowProvider设置为\"none\"。如果您使用的是Mac M1，请确保使用的是ARM版Java，而非Intel x64版。这也是驱动程序过时的常见问题。如果您使用的是Windows且禁用此设置无效，请查阅此指南： <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>驱动程序更新指南</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError() + "'>缺少必要的依赖项：</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "请求自").replace("Expected range", "预期范围") + "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>您的CrashDetector报告在此 <a href='" + archivo
				+ "' style='color:#" + config.obtenerColorEnlace() + "'>查看报告</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>这是CrashDetector的GUI界面。如果游戏正常关闭，请忽略此界面。</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>查看报告</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>在浏览器中查看本地报告。</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "分享报告";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
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
	public String posibilidad_fatal() {
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
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>检测到致命类错误（FATAL），问题非常严重。常见原因包括错误的 CoreMod 或致命的依赖项。您可以使用 QuickFix 扫描包含致命类的模组。检测到缺失的致命类：</b>";
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
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>你有包含重复包的模组。你可以通过从 JAR 文件中删除重复的包（文件夹）来解决此问题。你可以使用 WinRAR 或 7z 等压缩软件打开 JAR 文件，也可以将文件扩展名从 .jar 改为 .zip，然后删除文件夹，再将其改回 .jar 扩展名。</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>检测到重复的Mods</b> "
				+ linea.replace("from mod files", "来自mod文件");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge检测到可疑mod存在问题：</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV需要lithostitched，您可以在此安装： <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>要使用Iris着色器或Oculus，您需要SODIUM或其他加载器的兼容版本（Rubidium, Embedium, Bedium）</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>KubeJS扩展存在问题 </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "检测到Windows 11之前版本的NVIDIA驱动程序存在问题。"
				+ "</span><br/><br/>" + "为确保Minecraft（以及当前的JVM）使用专用的NVIDIA GPU，请按照以下步骤操作：<br/><br/>"
				+ "1. <strong>识别Java可执行文件：</strong><br/>" + "   - 此程序正在使用以下Java可执行文件： " + obtenerRutaJava() + "<br/>"
				+ "   - 如果没有看到具体路径，可以通过在系统中搜索'java.exe'找到Java可执行文件。<br/><br/>"
				+ "2. <strong>打开NVIDIA控制面板：</strong><br/>" + "   - 右键单击桌面并选择'NVIDIA控制面板'。<br/><br/>"
				+ "3. <strong>配置首选GPU：</strong><br/>" + "   - 在NVIDIA控制面板中，转到'管理3D设置'。<br/>" + "   - 选择'程序设置'选项。<br/>"
				+ "   - 点击'添加'并找到之前识别的Java可执行文件（例如：'java.exe'）。<br/>" + "   - 确保它已设置为使用'高性能NVIDIA处理器'。<br/><br/>"
				+ "4. <strong>保存更改：</strong><br/>" + "   - 应用更改并关闭NVIDIA控制面板。<br/><br/>"
				+ "5. <strong>重新启动Minecraft：</strong><br/>" + "   - 重新启动Minecraft以使更改生效。<br/><br/>"
				+ "如果您使用的是Windows Server 2022或Windows 10，只要安装了最新的NVIDIA驱动程序，这些步骤均有效。<br/><br/>"
				+ "注意：如果找不到NVIDIA控制面板，请确保NVIDIA驱动程序已正确安装。";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "检测到Windows 11/Server 2025或更高版本的NVIDIA驱动程序存在问题。" + "</span><br/><br/>"
				+ "为确保Minecraft（以及当前的JVM）使用专用的NVIDIA GPU，请按照以下步骤操作：<br/><br/>" + "1. <strong>识别Java可执行文件：</strong><br/>"
				+ "   - 此程序正在使用以下Java可执行文件： " + obtenerRutaJava() + "<br/>"
				+ "   - 如果没有看到具体路径，可以通过在系统中搜索'java.exe'找到Java可执行文件。<br/><br/>" + "2. <strong>打开设置应用：</strong><br/>"
				+ "   - 按下<code>Win + I</code>键打开设置应用。<br/>" + "   - 导航至<strong>系统 > 显示 > 图形</strong>。<br/><br/>"
				+ "3. <strong>配置首选GPU：</strong><br/>" + "   - 在'图形'部分，点击'默认图形设置'。<br/>" + "   - 选择'桌面应用'，然后点击'浏览'。<br/>"
				+ "   - 找到并选择之前识别的Java可执行文件（例如：'java.exe'）。<br/>"
				+ "   - 添加后，在列表中选择Java应用程序，并将其配置为使用'高性能（NVIDIA）'。<br/><br/>" + "4. <strong>保存更改：</strong><br/>"
				+ "   - 应用更改并关闭设置应用。<br/><br/>" + "5. <strong>重新启动Minecraft：</strong><br/>"
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
	    return "<b style='color:#" + config.obtenerColorAdvertencia()
	           + "'>Theseus 还存在其他问题，包括在尝试删除模组时会失败。如果你需要运行 mrpack 文件，可以使用其他启动器，例如 Prism Launcher（仅支持 modrinth.com）、ATLauncher（仅支持 modrinth.com）或 Hello Minecraft Launcher（支持 modrinth.com 和 bbsmc.net）。</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>您没有launcher_log.txt文件。我们需要这个文件来查找错误。这是由于启用了“跳过启动器启动”选项。请禁用它。</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>警告：检测到缺失的类（警告级别）。通常问题不大，但并非总是安全，与“致命类缺失”不同。常见原因包括错误的 CoreMod 或缺失依赖项。您可以使用 QuickFix 扫描包含缺失类的模组。检测到的缺失类：</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>无结果</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>日志位置:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>这是你的检查结果。请慢慢分析，通常真正的原因在检查1或2中。后面的（错误3及以上）可用于确认，但通常是连锁错误，可以忽略。故障是分层发生的，因此解决根本问题会修复此特定错误。然而，明天可能会出现一个与当前错误无关的新错误，因为一个错误常常会阻止另一个错误在控制台中显示。</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>请使用Java 17用于1.17-1.20.4版本，使用Java 21用于更新的版本。对于更旧的版本，请使用Java 8。[指南](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). 如果仍然有问题，可能是因为某些模组文件太新或太旧。</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>由于ASM过时，Java 22及以上版本在1.20.5以下的Minecraft版本中无法与大多数模组加载器兼容。</b>" + versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java已过时 </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>您需要从 " + submod + " 获取 JPMS 模块 " + mod_necesitas
				+ "</b>";
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
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado()
				+ "。您还可以通过单击单个日志名称旁边的共享按钮来共享单个日志而无需报告；日志将发送到选定的日志站点。CrashDetector 具有默认的日志匿名化功能，该功能会尝试删除用户名、UUID、访问令牌、会话 ID、IP 地址和其他数据。然而，它并不完美。此外，模组包作者可以禁用它。可以通过屏幕底部的复选框启用或禁用它。您是自己的数据控制者；您决定将数据上传到哪里。日志站点由第三方拥有，其所有权通常因隐私原因而隐藏。您需要对管理您的数据及相关的风险负全责。CrashDetector 共享对话框只是一个允许您管理这些内容的界面。了解 GDPR 和 ARCO 非常重要。";
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
		return "您遇到了SSL错误。这在旧版本的Java中很常见，" + "包括默认Minecraft启动器中的Java 8版本以及sun.com和java.com上的版本。"
				+ "这会影响许多方面，例如MinecraftForge安装程序的JAR文件，" + "使用默认端点时分享CrashDetector报告的功能，需要互联网连接的一些模组，"
				+ "以及一些日志站点。如果在尝试分享报告时遇到此问题，" + "请附上屏幕截图并选择与旧版Java 8兼容的日志站点。";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "不兼容的 JavaFML 版本：需要 " + requerido + "，但找到的是 "
				+ encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "注意！JavaFML 需要特定版本的 Minecraft Forge</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JAR 文件 '" + archivoJar + "' 需要语言提供程序 '"
				+ proveedor + "' 的版本 '" + requerido + "' 或更高版本，但仅找到版本 '" + encontrado + "'。</b>";
	}

@Override
public String advertenciaMalwareFalso() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "警告！Crash Assistant 是一个伪造的恶意软件检测器。它故意阻止游戏启动，无视您继续使用目标模组进行游戏的自由。 "
         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>查看 MalwareMod.java 代码</a>   "
         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>查看 JarInJarHelper.java 代码</a>. "
         + "目前只有这个模组在他们的列表中，并且他们实际上只针对默认的日志站点，而该站点可以由用户更改，只有在明确选择使用内置日志共享功能时才会生效。CrashAssistant 不会检查当前使用的是哪个日志站点，也不会解释如何更改它（共享对话框底部有一个下拉菜单），无论配置了哪个站点，CrashAssistant 都会阻止游戏启动。在他们的消息中，他们说要自己做研究，那就去做吧，查看 CrashDetector 和 Crash Assistant 的代码，了解它们的作用，不要依赖权威的呼吁。</b>";
}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "模组 '" + idMod + "' 因未找到所需类而失败: '"
				+ claseNoEncontrada + "'. 请确保所有依赖项已正确安装。</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Watermedia 已阻止使用 TLauncher 进行游戏。</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "您正在使用适用于过时版本的Minecraft的Optifine版本。您需要使用与您当前使用的Minecraft版本对应的Optifine版本。</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "无法加载 ModLauncher 服务: </b>" + servicio + "。";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "解析 JAR 文件 '" + archivoJar + "' 中的 JSON 文件 '"
				+ recurso + "' 时出错。" + "注册存在问题。</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "错误：模组 '" + modId + "' 需要 '" + dependencia
				+ "' 的版本 '" + requerido + "' 或更高版本，但找到的是 '" + actual + "'。" + "</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "您的GPU不支持此游戏版本所需的OpenGL版本。请更新驱动程序或更换显卡。</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "增加分配给游戏的内存或减少模组/插件的使用。</b>";
	}

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "检测到32位JVM：无法使用超过4GB的RAM。 "
				+ "安装64位JVM以充分利用您的可用内存。</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "PermGen内存严重错误。增加永久内存空间或减少类加载。</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Java 8与现代版本之间的兼容性错误</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "不支持Java 9+ - 使用Java 8以适配旧版Forge</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "需要Java 8（版本52.0）。请更新或正确配置</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "严重兼容性错误：此版本不支持方块。 " + "请确保模组和服务器版本兼容</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "显示器配置错误: " + "无法设置屏幕模式。 " + "请检查：</b>"
				+ "<br>- 多显示器配置" + "<br>- 图形卡驱动程序是否已更新" + "<br>- 系统支持的分辨率";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Java选项错误: " + "垃圾回收器选项冲突。 "
				+ "请确保未在JVM参数中组合多个GC算法</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Forge配置严重错误: " + "配置文件损坏或不完整。 "
				+ "删除'config'文件夹并重新启动游戏</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "检测到Intel HD Graphics驱动程序错误。解决方案：</b>"
				+ "<br>1. 从<a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a>更新Intel驱动程序（最低版本15.xx.xx.xx）"
				+ "<br>2. 在Minecraft中：选项 → 视频 → 启用'Enable VBOs'和'VSync'" + "<br>3. 在启动器中为游戏分配1GB-2GB的RAM"
				+ "<br>4. 更新期间临时禁用杀毒软件/防火墙";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "警告：检测到缺失的类";
	}

	public String nombre_de_bloque_teselado() {
		return "方块渲染错误";
	}

	public String nombre_de_contenido_de_stacktrace() {
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
	public String ajustesCrashDetector() {
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
		return "您可以在 WaifuNeoForge 数据库中搜索以查找模组。请选择游戏版本、模组加载器和类名。使用最接近的匹配组合。每分钟只能搜索一次。";
	}

	@Override
	public String solucionFaltasClases() {
		return "您可以在 WaifuNeoForge 数据库中搜索以查找模组。请选择游戏版本、模组加载器和类名。使用最接近的匹配组合。每分钟只能搜索一次。";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>发现多个同名插件 '" + nombrePlugin + "': '" + primerPath
				+ "' 和 '" + segundoPath + "'。</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + "' 无法执行命令 '/" + comando
				+ "'。</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + "' 需要依赖 '" + dependencia
				+ "'。</b>";
	}

	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + "' 缺少以下必需依赖: "
				+ deps.toString() + "。</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + "' 需要版本为 '" + versionAPI
				+ "' 的 API，与当前服务器不兼容。</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>世界 '" + nombreMundo + "' 是另一个世界的副本，无法加载。</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>位于 " + coords + " 的区块实体 '" + nombre + "'（类型为 '"
				+ tipo + "'）在tick时发生错误。</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + primerMod + "' 和 '" + segundoMod
				+ "' 彼此不兼容。</b> ";
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
			return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + nombreMod + "' 需要依赖模组 '" + dependencia
					+ "'。</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + nombreMod + "' 需要依赖模组 '" + dependencia
					+ "' 的版本 " + version + "。</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin
				+ "' 不兼容 Folia 的区域性 Tick。</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + nombreMod + "' 在数据包中缺失。</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + nombreMod + "' 不兼容 Minecraft 版本 "
				+ versionMC + "。</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>该世界保存时包含模组 '" + nombreMod + "'，但现在找不到该模组。</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>世界保存时使用的是模组 '" + nombreMod + "' 的版本 "
				+ versionEsperada + "，但现在运行的是版本 " + versionActual + "。</b>";
	}

	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
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
			sb.append("（保存版本：").append(versionesEsperadas.get(i)).append("，当前版本：").append(versionesActuales.get(i))
					.append("）");
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
		return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + "' 需要依赖插件 '" + dependencia
				+ "'。</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>插件 '" + nombrePlugin + "' 与当前服务器版本不兼容。</b>";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>检测到多个线程访问 LegacyRandomSource 类。你可以通过安装 'Unsafe World Random Access Detector' 或 'C2ME' 模组获取更多信息。</b>";
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
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>" + "<p><b>模组历史比较面板</b></p>"
				+ "<p>此面板允许您对比不同运行会话的两个模组列表。从左侧选择一个文件，右侧选择另一个，以查看它们之间的变化。</p>"

				+ "<h3>使用方法：</h3>" + "<ol>" + "<li><b>选择文件：</b>点击文件名旁边的单选按钮。"
				+ "以<span style='color: #4CAF50; font-weight: bold;'>.exito</span>结尾的文件表示成功会话，"
				+ "而以<span style='color: #F44336; font-weight: bold;'>.falla</span>结尾的文件则代表失败。</li>"

				+ "<li><b>自动对比：</b>按下'Compare'按钮后系统将分析所选列表，并显示添加(+)或删除(-)的模组。</li>"

				+ "<li><b>结果展示：</b>结果以颜色编码的HTML格式呈现：" + "<ul>" + "<li><span style='color: green;'>+ 添加的模组</span></li>"
				+ "<li><span style='color: red;'>- 删除的模组</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>主要功能：</h3>" + "<ul>" + "<li>支持任意组合的成功/失败文件对比。</li>" + "<li>双向差异展示以便精准跟踪变更。</li>"
				+ "<li>支持长模组列表滚动浏览。</li>" + "<li>集成示意图提升视觉理解。</li>" + "</ul>"

				+ "<p>用心开发，助您追踪配置中的更改 <3</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "您可能遇到了与 IPv6 相关的问题。" + "有两种解决方案："
				+ "1) 在启动器中添加 JVM 参数 <code>-Djava.net.preferIPv4Stack=true</code>，或者"
				+ "2) 在 CrashDetector 中使用 'QuickFix' 按钮自动启用此设置的补丁。" + "</b>";
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
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/衍生版本：右键实例，选择“编辑实例”。在打开的窗口中查找“Minecraft 日志”或类似部分。<br>"
				+ "这些日志包含标准输出（STDOUT），对于诊断错误至关重要。";
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
		return "通用：请选择你正在使用的启动器类型。启动器日志（launcher_log.txt、stdout 等）包含了 latest.log 中没有的关键错误信息。CrashDetector 无法读取你的启动器日志 —— 可能是没有生成日志文件，你需要手动粘贴日志内容。<br>"
				+ "更多信息请参考 <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">此问题</a>。这些日志包含标准输出（STDOUT），是诊断许多错误所必需的。";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>检测到来自 Create 的缺失类。Create 已发生重大变更，许多类已被移除。特别是从 Create 6（2025年2月）开始，旧版本的 Create 附加模组将无法正常工作。QuickFix 无法解决此问题。您需要更新 Create 附加模组、删除过时的模组，或使用与附加模组兼容的正确 Create 版本。</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>检测到来自 EpicFight 的缺失类。EpicFight 已发生重大变更，许多类已被移除。QuickFix 无法解决此问题。您需要更新 EpicFight 附加模组、删除过时的模组，或使用与附加模组兼容的正确 EpicFight 版本。</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>您正在使用 OpenJ9，而本应用不支持该 JVM。许多应用（包括此应用）由于 JVM 实现差异而不支持 OpenJ9。QuickFix 无法自动解决此问题。您需要安装兼容的 JDK，例如 Oracle JDK、OpenJDK Hotspot 或其他推荐的替代方案。</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>此版本的应用需要 Java 11 (JDK 11) 才能正常运行。您正在使用不兼容的旧版 Java。QuickFix 无法自动升级您的 Java。您需要从解决方案中提供的链接下载并安装 JDK 11 或更高兼容版本。</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>您分配了过多内存，导致系统资源不足。通常发生在设置的 RAM 超出系统可用容量，或使用无法处理大内存分配的 32 位 JVM 时。</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>请减少分配给应用的内存。推荐最大值取决于您的系统，通常不应超过总内存的 70-80%。若使用 32 位 JVM，最大限制约为 2-3 GB，与物理内存大小无关。</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>要减少应用的堆内存，请打开启动器设置并找到内存选项。将最大值 (Xmx) 调整为更合适的数值。例如，8 GB 内存可分配 3-4 GB，16 GB 可分配 6-8 GB。请为操作系统和其他程序保留足够内存。</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Forge 缺少必要文件。文件 '" + archivo
				+ "' 在您的安装中未找到。这通常发生在 Forge 安装中断或重要文件被删除时。QuickFix 无法自动恢复这些文件。您需要使用官方安装程序重新正确安装 Forge。</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Forge 无法找到所需的 Minecraft 版本。需要版本 " + version
				+ "，但在文件 '" + archivo
				+ "' 中未找到。这通常是因为您使用的 Minecraft 版本与 Forge 版本不兼容。请确保下载与您的 Minecraft 版本匹配的正确 Forge 版本。</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>无法找到启动 Forge 所需的 'fmlclient' 目标。这表明 Forge 安装不完整或已损坏。Forge 的关键文件可能未正确安装。您需要使用官方安装程序重新安装 Forge。</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>类加载器中找不到 Minecraft 主类。这通常表示 Forge 安装不完整，或与其他模组存在冲突。在安装 Forge 时，Minecraft 文件可能已损坏。您需要重新正确安装 Forge。</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge 安装不完整。可能由于安装中断、文件被删除，或与您的 Minecraft 版本不兼容。Forge 需要特定文件才能正常运行，而您的当前安装缺少其中一些文件。</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "Forge 安装不完整";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>要解决此问题，请重新正确安装 Forge。确保下载与您 Minecraft 版本匹配的版本，并完整执行安装过程，不要中断。</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "从官方渠道下载 Forge";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "如何正确重新安装 Forge";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>重新安装 Forge 的说明：</h3>" + "<ol>" + "<li>从官方网站下载正确的 Forge 安装程序（推荐与您 Minecraft 版本匹配的版本）</li>"
				+ "<li>完全关闭您的 Minecraft 启动器</li>" + "<li>以管理员身份运行 Forge 安装程序</li>"
				+ "<li>选择 'Installer' 选项（不要选 'Installer (run client)'）</li>" + "<li>在启动器中选择您的 Minecraft 配置文件文件夹</li>"
				+ "<li>点击 'OK' 并等待安装完成</li>" + "<li>重启启动器，并确认 Forge 出现在配置文件列表中</li>" + "</ol>"
				+ "<p><b>重要提示：</b> 如果您使用的是自定义启动器，请确保选择正确的配置文件目录。</p>" + "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "重新安装 Forge 的说明";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError() + "'>链接错误：无法加载库 " + nombreBiblioteca
				+ "。可能的解决方案：<br/><br/>" + "a) 将包含共享库的目录添加到 -Djava.library.path 或 -Dorg.lwjgl.librarypath。<br/>"
				+ "b) 将包含共享库的 JAR 文件添加到 classpath。<br/><br/>" + "此错误发生在 Minecraft 无法找到运行所需的关键文件时。"
				+ "通常由 Minecraft 安装不完整或系统权限问题引起。</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "链接错误";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>无法加载库。可能的解决方案：<br/><br/>"
				+ "a) 将包含共享库的目录添加到 -Djava.library.path 或 -Dorg.lwjgl.librarypath。<br/>"
				+ "b) 将包含共享库的 JAR 文件添加到 classpath。<br/><br/>" + "这些技术方案适用于高级用户。大多数用户应在修改这些参数前尝试重新安装 Minecraft。</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ID 冲突：ID <strong>" + id + "</strong> 已被 <strong>"
				+ modOrigen + "</strong> 占用，无法为 <strong>" + modDestino
				+ "</strong> 分配。当两个模组试图为不同元素使用相同 ID 时会发生此问题。</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>已超出允许的最大 ID 范围。当模组尝试注册超出 Minecraft 版本支持范围的区块或物品 ID 时会发生此问题。</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "ID 冲突";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>解决方法：在 Minecraft 1.12.2 中安装 <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>，1.7.10 版本请使用 <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>。</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>使用 <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> 或 <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> 等工具解决 ID 冲突。</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "安装 JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "安装 EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "使用 IdFix Minus 或 IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "使用 Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "查看日文文档";
	}

	@Override
	public String escanearDeMCreator() {
		return "扫描 MCreator";
	}

	@Override
	public String tituloArbolDeMods() {
		return "模块与类树";
	}

	@Override
	public String tipoBusqueda() {
		return "类型";
	}

	@Override
	public String filtroTodos() {
		return "全部";
	}

	@Override
	public String filtroPaquetes() {
		return "包";
	}

	@Override
	public String filtroClases() {
		return "类";
	}

	@Override
	public String filtroMetodos() {
		return "方法";
	}

	@Override
	public String filtroCampos() {
		return "字段";
	}

	@Override
	public String filtroReferenciasCampo() {
		return "字段引用";
	}

	@Override
	public String filtroReferenciasMetodo() {
		return "方法引用";
	}

	@Override
	public String filtroReferenciasClase() {
		return "类引用";
	}

	@Override
	public String tipBuscar() {
		return "在此输入以在模组树中搜索";
	}

	@Override
	public String botonBuscar() {
		return "搜索";
	}

	@Override
	public String botonResetearArbol() {
		return "重置树";
	}

	@Override
	public String modsCargados() {
		return "已加载的模组";
	}

	@Override
	public String clases() {
		return "类";
	}

	@Override
	public String metodos() {
		return "方法";
	}

	@Override
	public String campos() {
		return "字段";
	}

	@Override
	public String referencias() {
		return "引用";
	}

	@Override
	public String resultadosBusqueda() {
		return "搜索结果";
	}

	@Override
	public String buscarReferencias() {
		return "查找引用";
	}

	@Override
	public String referenciasMod() {
		return "模组引用";
	}

	@Override
	public String referenciasClase() {
		return "类引用";
	}

	@Override
	public String referenciasMetodo() {
		return "方法引用";
	}

	@Override
	public String referenciasCampo() {
		return "字段引用";
	}

	@Override
	public String noSeEncontraronReferencias() {
		return "未找到引用";
	}

	@Override
	public String detalleMod() {
		return "模组详情:";
	}

	@Override
	public String ubicacion() {
		return "位置";
	}

	@Override
	public String nombres() {
		return "名称";
	}

	@Override
	public String modulo() {
		return "模块";
	}

	@Override
	public String detalleClase() {
		return "类详情:";
	}

	@Override
	public String detalleMetodo() {
		return "方法详情:";
	}

	@Override
	public String detalleCampo() {
		return "字段详情:";
	}

	@Override
	public String clase() {
		return "类";
	}

	@Override
	public String tipo() {
		return "类型";
	}

	@Override
	public String referenciasAMetodos() {
		return "方法引用:";
	}

	@Override
	public String referenciasACampos() {
		return "字段引用:";
	}

	@Override
	public String arbolDeMods() {
		return "模组树";
	}

	@Override
	public String metodo() {
		return "方法";
	}

	@Override
	public String campo() {
		return "字段";
	}

	@Override
	public String descompilar() {
		return "反编译";
	}

	@Override
	public String exportar() {
		return "导出";
	}

	@Override
	public String importar() {
		return "导入";
	}

	@Override
	public String errorImportar() {
		return "导入错误";
	}

	@Override
	public String estructuraImportada() {
		return "结构已导入";
	}

	@Override
	public String estructuraExportada() {
		return "结构已导出";
	}

	@Override
	public String errorExportar() {
		return "导出错误";
	}

	@Override
	public String exportando() {
		return "正在导出";
	}

	@Override
	public String exportacionTardara() {
		return "导出可能需要一些时间";
	}

	@Override
	public String porFavorEspere() {
		return "请稍候";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>您缺少 VLC 二进制文件。WaterMedia 需要 VLC 二进制文件。请从 https://www.videolan.org/vlc/ 手动安装。  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "下载 VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>严重错误：模块名称 '" + nombreModulo + "' 包含无效字符。其中 '"
				+ parteInvalida + "' 不是有效的 Java 标识符。当模组在其名称中使用 Java 保留字（如 'true'、'class'）或不允许的字符时会发生此问题。</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "模组名称包含无效字符";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "模组名称 '" + nombreModulo + "' 无效，因为它包含 '" + parteInvalida + "'，这是 Java 保留字或不允许的字符。"
				+ "在日志中查找对应此名称的模组（通常是 JAR 文件名）";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "进入模组文件夹，编辑 <b>/META-INF/</b> 文件夹内的 <b>mods.toml</b> 文件。"
				+ "将 <b>modId</b> 的值改为仅使用字母、数字和下划线，避免使用 Java 保留字";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "有效名称示例：'truemod_shot_enchantment' 代替 'true.shot.enchantment'。"
				+ "请记住，模组名称不能包含点号、连字符或 Java 保留字如 'true'、'false' 或 'class'";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>模组 '" + nombreJar
				+ "' 存在严重错误：其依赖项缺少必需的 'mandatory' 字段。当 mods.toml 文件未指明依赖是否为必需时会发生此问题。</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "模组依赖项缺少必需字段";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "问题模组为：<b>" + nombreJar + "</b>。此文件的依赖配置有误";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "打开模组 <b>" + nombreJar + "</b> 的 <b>/META-INF/</b> 文件夹中的 <b>mods.toml</b> 文件";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "在依赖项部分，确保每个条目都包含 <b>mandatory=true</b> 或 <b>mandatory=false</b>（例如：modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" ）";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>严重错误：模组 '" + nombreJar
				+ "' 的访问转换器（access transformer）配置无效。当配置文件语法错误或引用了不存在的类/方法时会发生此问题。</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "无效的访问转换器";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "问题模组为：<b>" + nombreJar + "</b>。此模组包含无效的 access transformer 配置";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "打开模组 <b>" + nombreJar + "</b> 内的 <b>accessTransformer.cfg</b> 文件（通常位于 JAR 文件根目录）";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "修复 access transformer 的语法。每行应遵循格式：<b>access class.method</b>（例如：public net.minecraft.world.entity.Entity.func_200560_a）。删除引用了当前 Minecraft 版本中不存在的类或方法的行";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>严重错误：@Mod 注解中的模组 ID 与 mods.toml 文件中的 ID 不匹配。模组 '"
				+ nombreMod + "' 无法加载，因为 ID 不一致。</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "@Mod 与 mods.toml 中的 ID 不匹配";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "正在开发的模组 '" + nombreMod + "' 在 <b>@Mod</b> 注解和 <b>mods.toml</b> 文件中的 ID 存在不一致";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "检查主类中的 ID 是否与 <b>/META-INF/mods.toml</b> 文件中的 <b>modId</b> 值一致。例如：<b>@Mod(\"mymod\")</b> 必须与 <b>modId=\"mymod\"</b> 匹配";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "如果使用 Gradle，在修改后运行 <b>clean</b> 以确保资源正确更新。有时旧文件会残留在 build 文件夹中";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "客户端" : "服务端";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "服务端" : "客户端";

		return "<b style='color:#" + config.obtenerColorError() + "'>严重错误：正在尝试在" + plataforma + "环境中加载类 '" + nombreClase
				+ "'，但该类是为" + plataformaOpuesta + "设计的。" + "<b>使用侧边栏中的“模组树”功能查找是哪个模组尝试加载此类</b>。"
				+ "模组是为特定平台构建的，不能在另一平台运行。</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "模组平台错误";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "在右侧的 <b>模组树</b> 选项卡中，搜索对类 <b>" + nombreClase + "</b> 的引用，以识别导致问题的模组";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "客户端" : "服务端";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "服务端" : "客户端";

		return "识别出的模组是用于 <b>" + plataformaOpuesta + "</b> 的，不应在您的" + plataforma + "环境中使用。";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "从您的 <b>mods</b> 文件夹中删除问题模组。如果需要此平台的类似功能，" + "请寻找专为 <b>客户端</b> 或 <b>服务端</b> 设计的替代模组";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("严重错误：缺少 modid '").append(modIdFaltante).append("' 的元数据。 ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("以下模组可能导致此问题：<b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", 等等...");
			mensaje.append("</b>。 ");
		}

		mensaje.append("当某个模组依赖于未安装的模组或 mods.toml 文件错误时会发生此问题。");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "缺少 mods.toml 元数据";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("以下模组依赖 '").append(modIdFaltante).append("': <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", 等等...");
			paso.append("</b>。使用 <b>模组树</b> 功能确认哪个模组导致问题");
			return paso.toString();
		}
		return "某个模组试图依赖 '" + modIdFaltante + "'，但该模组未安装。使用 <b>模组树</b> 功能识别问题模组";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "您有两个选择：<br/>" + "1. <b>安装缺失的模组</b>：查找并安装 ID 为 '" + modIdFaltante + "' 的模组<br/>"
				+ "2. <b>删除依赖模组</b>：如果不需要该功能，请删除依赖 '" + modIdFaltante + "' 的模组";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "如果 '" + modIdFaltante + "' 是库文件（如 'forge', 'minecraft', 'curios'），" + "请确保已安装正确版本的 Minecraft 和 Forge。"
				+ "如果是普通模组，请查看其下载页面以确认前置依赖";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>警告：声音系统初始化失败。声音和音乐已被禁用。此错误通常与 SoundPhysicsMod 模组相关，可能由与其他声音库的冲突引起。</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "声音系统错误";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "该错误通常与 <b>SoundPhysicsMod</b> 相关。请检查是否安装了与您的 Minecraft 版本兼容的最新版本";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "如果您使用其他声音模组（如 Sound Filters、Dynamic Surroundings 等），请尝试暂时移除 SoundPhysicsMod 以查看是否解决冲突";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "检查 <b>logs</b> 文件夹，查找与 LWJGL 或 OpenAL 相关的额外信息，这些可能表明底层声音库存在问题";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("严重错误：类 '").append(nombreClase).append("' 已注册为事件监听器，但不包含有效方法。 ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("此类位于以下模组中： <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", 等等...");
			mensaje.append("</b>. ");
		}

		mensaje.append("当一个类被注册用于监听事件，但没有包含 @SubscribeEvent 注解的方法时，会发生此问题。");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "类已注册但无事件监听器";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("问题类位于这些模组中：<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", 等等...");
			paso.append("</b>。这些模组试图注册事件但没有有效方法");
			return paso.toString();
		}
		return "类 <b>" + nombreClase + "</b> 已被注册用于监听事件，但未包含 <b>@SubscribeEvent</b> 注解的方法。请使用 <b>模组树</b> 功能识别包含此类的模组";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "在源代码中，确保类 <b>" + nombreClase + "</b> 至少包含一个如下格式的方法： "
				+ "<b>@SubscribeEvent public void 方法名(特定事件 事件) { ... }</b>。 " + "如果是内部类，请确保其未标记为 static";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("对于已识别的模组 (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", 等等");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("请联系该模组开发者修复问题。 ");
			} else {
				paso.append("请联系这些模组的开发者修复问题。 ");
			}
		}

		paso.append("如果您是开发者，请从 EventBus 中移除此类的注册，或添加有效的 @SubscribeEvent 方法");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>严重错误：处理文件 '" + nombreArchivo
				+ "' 时发生 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' 异常。此错误表明启动器未能正确下载或解压整合包文件。"
				+ "错误信息 'zip END header not found' 表明 JAR 文件不完整或已损坏，这在无法妥善处理大文件下载的启动器中极为常见。"
				+ "此问题主要影响 Twitch/CurseForge、Technic Launcher 用户，尤其是 Luna Pixel 用户，因为这些启动器通常无法完整验证已下载文件的完整性。"
				+ "Luna Pixel 用户应考虑改用 ATLauncher 作为更稳定的替代方案，它能更好地处理文件完整性并避免此特定错误。"
				+ "系统无法加载模组，因为 ZIP 格式已损坏，导致 Forge 无法读取启动游戏所需的资源。</b>";
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "UnionFileSystem 错误 - 文件损坏";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		return "从头开始完全重新安装整合包";
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "如果你使用 Luna Pixel，请切换到 ATLauncher";
	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "重新安装前请检查网络连接和磁盘空间";
	}

	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "是否启用 ProxySysOutSysErr？\n\n" + "此选项允许 CrashDetector 在启动器未提供日志时访问 System.out 和 System.err。\n\n"
				+ "仅在无法手动粘贴日志时启用。\n\n" + "警告：可能与某些模组或启动器冲突。\n\n" + "需要重启游戏/应用以使更改生效。";
	}

	@Override
	public String confirmacionTitulo() {
		return "确认";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr 已成功启用。\n\n" + "需要重启 CrashDetector 以使更改生效。";
	}

	@Override
	public String informacionTitulo() {
		return "信息";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("严重错误：AzureLib 和 GeckoLib 初始化过早！ ");
		} else if (azureLibError) {
			mensaje.append("严重错误：AzureLib 初始化过早！ ");
		} else if (geckoLibError) {
			mensaje.append("严重错误：GeckoLib 初始化过早！ ");
		}

		mensaje.append("当尝试在非 Fabric 环境下使用 Fabric 版本的这些库时，会发生此错误。 ");

		if (connectorPresente) {
			mensaje.append(
					"检测到兼容性模组（Sinytra Connector 或 specialcompatibilityoperation），表明你正在 Forge 或 FeatureCreep 环境中运行 Fabric 模组。 ");
			mensaje.append("请在日志中检查 'FabricMC 初始化错误'，以确定具体是哪个模组导致问题。 ");
		}

		mensaje.append("AzureLib 和 GeckoLib 是动画模组的关键库，但必须与正确的平台（Fabric 或 Forge）匹配。 ");
		mensaje.append("由于初始化冲突，游戏无法正确加载动画模组。");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "库初始化过早";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "检查日志中的 'FabricMC 初始化错误' 以确定问题模组";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "确保你使用的是适用于你平台（Forge 或 Fabric）的正确版本的 AzureLib/GeckoLib";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError() + "'>严重错误：C2ME 与连接模组不兼容。 "
				+ "此错误发生的原因是 C2ME 尝试访问 Java 内部组件，而这些组件在 Sinytra Connector 或 specialcompatibilityoperation 等 Fabric/Forge 兼容性模组环境中受到限制。 "
				+ "<b>C2ME 不兼容这些环境，但 <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> 是推荐的替代方案</b>，可与连接模组正常协作。 "
				+ "由于 Java 安全权限冲突，游戏无法启动。</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "C2ME 与连接模组不兼容";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "从 mods 文件夹中删除 C2ME";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "下载并安装 <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> 作为替代（兼容 Sinytra Connector）";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "确保所有连接模组（如 Sinytra Connector）均已更新至最新版本";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError() + "'>严重错误：加载模组 '" + modId + "' 的 JEI 插件失败。类 '"
				+ nombreClase + "' (插件ID: '" + pluginId + "') 抛出错误，导致游戏在启动时崩溃。"
				+ "当某个模组的 JEI 集成不兼容或损坏，干扰游戏初始化时，就会出现此问题。</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "JEI 插件失败 - 导致崩溃";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "模组 <b>" + modId + "</b> 包含一个损坏的 JEI 插件，导致崩溃。使用 <b>模组树</b> 功能确认是哪个模组引发问题";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "暂时从 mods 文件夹中移除模组 <b>" + modId + "</b>，检查是否能解决崩溃问题";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "查找模组 <b>" + modId + "</b> 的更新，或联系其开发者报告 JEI 插件问题。" + "同时，必须移除该模组才能正常启动游戏";
	}

	@Override
	public String tituloLectador() {
		return "日志阅读器 - 崩溃检测";
	}

	@Override
	public String obtenerTituloLeyenda() {
		return "颜色图例";
	}

	@Override
	public String obtenerErrorEnLeyenda() {
		return "严重错误";
	}

	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "堆栈跟踪";
	}

	@Override
	public String obtenerTituloError() {
		return "错误";
	}

	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "处理所选行时发生错误";
	}

	@Override
	public String obtenerNombreError() {
		return "错误名称";
	}

	@Override
	public String obtenerDescripcionError() {
		return "详细描述";
	}

	@Override
	public String obtenerSeleccionarConsola() {
		return "选择日志";
	}

	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "未识别的错误";
	}

	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "在日志中检测到严重错误。 " + "请检查堆栈跟踪以确定根本原因。";
	}

	@Override
	public String obtenerErrorLecturaArchivo() {
		return "无法读取日志文件。 " + "请确认文件存在且具有读取权限。";
	}

	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "日志分析器";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("严重错误：无法为模组 '").append(modId).append("' 注册自动事件订阅者。 ");

		mensaje.append("问题类： <b>").append(nombreClase).append("</b>。 ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("此类位于：<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", 等等...");
			mensaje.append("</b>。 ");
		}

		mensaje.append("当模组尝试自动注册一个类为事件订阅者，但该类无法加载时，会发生此错误。 ");
		mensaje.append("<b>请检查日志中此消息之前的其他错误，真正原因可能是之前的加载失败</b>。");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "自动订阅者注册失败";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "模组 <b>" + modId + "</b> 正在尝试将类 <b>" + nombreClase + "</b> 注册为自动订阅者，但失败了。请检查此类是否存在且可访问";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("问题类 <b>" + nombreClase + "</b> 位于这些文件中：<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", 等等...");
			paso.append("</b>。 ");
			paso.append("使用 <b>模组树</b> 功能确认哪个文件包含问题类");
			return paso.toString();
		}
		return "类 <b>" + nombreClase + "</b> 在任何模组文件中都未找到。请检查模组 <b>" + modId + "</b> 是否正确安装。使用 <b>模组树</b> 功能帮助定位问题";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "将模组 <b>" + modId + "</b> 更新至与你的 Minecraft 和 Forge 版本兼容的最新版本。 " + "如果问题仍然存在，请联系模组开发者并报告包含问题类的错误";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "请检查此消息之前的 <b>日志中的其他错误</b>，因为真正的问题可能出现在之前的加载失败中。 " + "有时先前的错误会阻止必要类的加载，导致事件注册失败";
	}

	@Override
	public String limpiado() {
		// TODO Auto-generated method stub
		return "已清理";
	}

	@Override
	public String original() {
		// TODO Auto-generated method stub
		return "原始";
	}

	@Override
	public String verEnConsola() {
		// TODO Auto-generated method stub
		return "在日志中查看";
	}

	@Override
	public String advertencia() {
		// TODO Auto-generated method stub
		return "警告";
	}

	@Override
	public String fatal() {
		// TODO Auto-generated method stub
		return "致命";
	}

	@Override
	public String noRegistroDeBattly() {
		// TODO Auto-generated method stub
		return "BattlyLauncher 没有可供复制的日志或控制台。你可以使用 ProxySysOutSysErr 来拦截 STDOUT 和 STDERR 并重启游戏，但 ProxySysOutSysErr 可能与修改 STDOUT 或 STDERR 的模组发生冲突";
	}

	@Override
	public String noRegistroDeNightWorld() {
		// TODO Auto-generated method stub
		return "你需要在 NightWorld 设置中启用调试模式以获取启动器日志。这非常重要，因为它包含 STDOUT 和 STDERR";
	}

	@Override
	public String noRegistroDeMCServidor() {
		// TODO Auto-generated method stub
		return "你需要保存或粘贴你服务器终端的内容，因为它包含其他日志中没有的信息，包括 STDOUT、STDERR 和其他错误。请粘贴最近一次会话的内容。今后，你可以将终端输出保存到文件 cd_launcherlog 中。为避免手动粘贴，在启动命令后添加 >> cd_launcherlog（如图所示）。请注意，这样做将阻止信息显示在终端上；所有内容只会记录到该文件中。";
	}

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("严重错误：在 NeoForge 环境中检测到 LexForge 转换器。 ");
		sb.append("</b>");

		sb.append("涉及的类： <b>").append(claseReceptora).append("</b>。 ");
		sb.append("受影响的接口是 <b>").append(interfazObjetivo).append("</b>，");
		sb.append("缺少的方法为 <b>").append(firmaMetodoFaltante).append("</b>。 ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("该类位于：<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", 等等...");
			sb.append("</b>。 ");
		} else {
			sb.append("未找到包含此类的 JAR 文件；可能被阴影化或以 jar-in-jar 形式嵌入。 ");
		}

		sb.append("当为 MinecraftForge/LexForge 编译的 ModLauncher 转换器/服务 ");
		sb.append("在 NeoForge 中使用不兼容的 ModLauncher API 版本加载时，会出现此错误。 ");
		sb.append("请更新或替换适用于 NeoForge 的组件。");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "在 NeoForge 中使用了 LexForge 转换器";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "识别不兼容的转换器：<b>" + claseReceptora + "</b>。 " + "预期的 API 是 <b>" + interfazObjetivo + "</b>，缺少方法 <b>"
				+ firmaMetodoFaltante + "</b>。 " + "检查模组是否在 <b>META-INF/services</b> 中注册了此类，并在 NeoForge 中移除或禁用它。";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("相关模组位置：<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", 等等...");
			sb.append("</b>。 ");
		} else {
			sb.append("未找到包含该类的 JAR 文件。请检查 jar-in-jar 和阴影依赖项。 ");
		}
		sb.append("暂时移除这些 JAR 文件，或使用支持 NeoForge 的版本来确认问题来源。");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "将组件替换为 NeoForge 专用版本，或针对 NeoForge 所使用的 ModLauncher 版本重新编译。" + "避免使用旧版 LexForge/MinecraftForge 二进制文件。";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "清理 mods 文件夹并删除重复的 jar-in-jar 文件。如有必要清空启动器缓存，" + "然后重启以确认不再加载 LexForge 转换器。";
	}

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia 无法启动：Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("不兼容。</b> ");
		sb.append("请移除 Xenon，改用 Embeddium 或 Sodium。 ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("检测到位置：<b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", 等等...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia 与 Xenon 不兼容";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "检测到 " + label + " 与 WaterMedia 不兼容。请从配置中移除它。";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("位置：<b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", 等等...");
			sb.append("</b>。删除该 JAR 文件。");
			return sb.toString();
		}
		return "未找到 JAR 文件。检查 mods 文件夹并删除 Xenon。";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "安装 Embeddium 或 Sodium 作为替代，并重启游戏。";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "压缩错误 (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>TACZ 资源复制过程中 Deflater 已关闭。</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("相关文件: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", 等等");
			sb.append("</b>. ");
		}
		sb.append("<br/><b>解决方案：</b>在 <code>tacz/tacz-pre.toml</code> 中设置 <code>DefaultPackDebug=true</code>。")
				.append("如有需要，请先生成地图再激活。");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "在 tacz/tacz-pre.toml 中设置 DefaultPackDebug=true。如有需要，请先生成地图再激活。";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "未绑定的密度函数";
	}

@Override
public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
    StringBuilder sb = new StringBuilder("<b>注册表中缺少密度函数。</b> ");
    if (claves != null && !claves.isEmpty()) {
        sb.append("缺失项：");
        for (int i = 0; i < Math.min(4, claves.size()); i++) {
            if (i > 0)
                sb.append(", ");
            sb.append("<code>").append(claves.get(i)).append("</code>");
        }
        if (claves.size() > 4)
            sb.append(", …");
        sb.append("。");
    }
    sb.append("<br/><b>解决方案：</b>安装或启用定义这些函数的 mod/datapack 并重启。另一个常见原因是：虽然你安装了所需的 mod，但它自身存在问题或与其他 mod 冲突；例如，Terralith 存在许多问题，可能引发此错误及其他问题，包括 JSON 错误。");
    return sb.toString();
}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "安装或启用提供这些函数的模组/数据包，并重新启动游戏。";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// 简短错误消息，用错误颜色显示，并明确提及模组
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("注册表项不存在: ").append(claveFaltante).append(". ");
		sb.append("常见于 Create 6 的 Steam & Railways 测试版（Alpha）。");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (测试版)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "移除或替换 Create 6 的 Steam & Railways 测试版为兼容版本。";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// 简短，使用错误颜色并直接提供建议
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("加载冲突：Multiworld 与 Sodium/Embeddium/Rubidium 同时使用会导致 ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance)。")
				.append("建议：移除 Multiworld 或性能模组，或使用相互兼容的版本。");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "冲突：Multiworld 与性能模组";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "卸载 Multiworld 或 Sodium/Embeddium/Rubidium，或更新为彼此兼容的版本。";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Sodium 检测到不兼容的显卡驱动程序。"
				+ "请将您的 GPU 驱动更新至最低要求版本，或参考 Sodium 指南进行操作。" + "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "OpenGL 上下文错误";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "OpenGL 失败：无当前上下文，或该函数在此上下文中不可用。"
				+ "也可能是显卡驱动问题。" + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "更新或重新安装 GPU 驱动并重启；关闭所有覆盖层，并尝试在不使用性能模组的情况下运行。";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "链接已复制到剪贴板。";
	}

//Chino
	@Override
	public String buscarDentroDeComprimidos() {
		return "在压缩包内搜索 (.zip/.jar/.war/.ear/.fpm/.rar（用于 Java）*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "纹理分辨率错误：无法加载资源 " + recurso + " — 尺寸：" + tamaño
				+ "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "纹理分辨率错误";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "当纹理过大或资源包过多时会发生此错误。" + "请尝试使用较低分辨率的资源包，或删除部分资源包。" + "检查是否添加了超出允许尺寸的自定义纹理。";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ModLauncher 服务错误：路径包含无效字符。 "
				+ "ModLauncher 服务无法处理包含非 ASCII 字符或特殊字符的路径。 "
				+ "问题字符包括：¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요，尤其是名称末尾的 '\"' 字符。 "
				+ "常见的 ModLauncher 服务组件包括 CrashDetector、" + Config.obtenerInstancia().obtenerNombreCD()
				+ "、FeatureCreep、Vivicraft、Optifine、Sodium、clonos、Iris Shaders/Oculus、MixerLogger、CrashAssistant 和 Sintrya Connector。 "
				+ "你可以移除所有服务，但路径名称仍可能导致其他问题。 " + "解决方案：将实例重命名为仅使用 ASCII 字符（a-z, A-Z, 0-9），避免空格和特殊字符。</b>";
	}

	@Override
	public String nombre_error_modlauncher_path() {
		return "ModLauncher 路径错误";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "当实例路径包含非 ASCII 字符或特殊字符时会发生此错误。 " + "ModLauncher 服务无法处理此类路径。 "
				+ "解决方案：将实例重命名为仅使用 ASCII 字符（a-z, A-Z, 0-9），避免空格和特殊字符。 " + "特别注意 '\"' 字符，它非常容易引发问题，尤其是在名称末尾时。";
	}

	@Override
	public String tituloEditorCodice() {
		return "Codice 编辑器";
	}

	@Override
	public String nuevo() {
		return "新建";
	}

	@Override
	public String actualizarSeleccionado() {
		return "更新选中项";
	}

	@Override
	public String eliminarSeleccionado() {
		return "删除选中项";
	}

	@Override
	public String exportarJSON() {
		return "导出 JSON...";
	}

	@Override
	public String guardarTodo() {
		return "保存全部";
	}

	@Override
	public String general() {
		return "通用";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "搜索文本";
	}

	@Override
	public String filtro() {
		return "过滤器 (ID)";
	}

	@Override
	public String criticalidad() {
		return "严重性 (警告/错误/致命)";
	}

	@Override
	public String prioridad() {
		return "优先级";
	}

	@Override
	public String lista() {
		return "检查项";
	}

	@Override
	public String colIdioma() {
		return "语言";
	}

	@Override
	public String colNombre() {
		return "名称";
	}

	@Override
	public String colResultado() {
		return "结果";
	}

	@Override
	public String vistaJson() {
		return "JSON 预览";
	}

	@Override
	public String idiomas() {
		return "语言 (全部必填)";
	}

	@Override
	public String elegirFiltro() {
		return "选择...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "请选择一个过滤器";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "可用过滤器";
	}

	@Override
	public String faltanCampos() {
		return "请填写所有必填的通用字段。";
	}

	@Override
	public String critInvalida() {
		return "严重性无效。请使用 警告、错误 或 致命。";
	}

	@Override
	public String filtroNoExiste() {
		return "指定的过滤器不存在。";
	}

	@Override
	public String faltanIdiomas() {
		return "请为所有语言填写名称和结果：";
	}

	@Override
	public String verificacionInvalida() {
		return "某项检查无效，请检查字段。";
	}

	@Override
	public String guardadoOk() {
		return "保存成功。";
	}

	@Override
	public String editorCodiceBoton() {
		return "添加原因";
	}

	@Override
	public String descripcionEditorCodice() {
		return "你可以在此注册原因。你需要一个ID，即不含特殊字符、重音符号或空格的字符串。对于过滤器，可使用“行包含”来搜索某行中的字符串，“全文包含”用于判断日志是否包含某字符串，“行正则”用于匹配某行的正则表达式，“全文正则”（建议优先使用行级版本）。你必须设置严重性：FATAL（致命）、ERROR（错误）或ADVERTENCIA（警告），以决定颜色显示。每种语言都需填写名称和结果，这些内容将显示在界面上。你可以添加更多检查项或删除已有项。完成时会自动保存。";
	}

	@Override
	public String descartarCambios() {
		return "是否放弃当前检查中的未保存更改？";
	}

	@Override
	public String confirmacion() {
		return "确认";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "您想在退出前保存更改吗？";
	}

	@Override
	public String salirSinGuardar() {
		return "退出不保存";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("严重错误：加载 modlauncher 服务 (IDependencyLocator) 失败。<br>");
		sb.append("🔹 <b>问题类：</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>受影响的模组：</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append("🔸 <b>模组未识别。</b> 请检查最近安装的、开发中的或打包错误的模组。<br>");
		}

		sb.append("🔸 <b>原因：</b> 模组的 <code>META-INF/services/...</code> 文件已损坏，");
		sb.append("与当前版本的 Forge/NeoForge 不兼容，或该模组适用于错误版本。<br>");
		sb.append("🔸 <b>后果：</b> Forge/NeoForge 无法注册模组依赖项，");
		sb.append("导致游戏无法启动。<br>");
		sb.append("🔸 <b>解决方案：</b> 更新、重新安装或删除有问题的模组。");
		sb.append("如果使用开发版模组，请确保其编译版本与你的 Forge/NeoForge 版本完全匹配。");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "服务配置错误 (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. 确定导致问题的模组：检查最近安装或正在开发的模组。";
		}
		return "1. 有问题的模组是：" + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. 更新、重新安装或删除该模组。请确保使用与你的 Forge/NeoForge 兼容的版本。";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>严重错误：方法不存在。</b><br>" + "该模组试图调用方法 <b style='color:#" + colorCodigo
				+ "'>" + metodo + "</b>，" + "但该方法在当前游戏版本或其他模组中不存在。<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "方法不存在 (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. 此错误通常发生在模组与当前游戏版本或其他模组不兼容时。";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. 更新所有相关模组。如果问题仍然存在，请向受影响模组的作者报告此错误。";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>严重错误：字段不存在。</b><br>" + "该模组试图访问字段 <b style='color:#" + colorCodigo
				+ "'>" + campo + "</b>，" + "但该字段在当前游戏版本或其他模组中不存在。<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "字段不存在 (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. 此错误通常发生在模组与当前游戏版本或其他模组不兼容时。";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. 更新所有受影响的模组。如果问题持续存在，请联系引发错误的模组作者。";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png")
				.toAbsolutePath().toUri().toString();
		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>需要帮助吗？</strong><br>"
				+ "  如果你不知道如何修复，或此处未列出原因，可通过我们的社交网络获得帮助。" + "  使用 <img src='" + iconoCompartir
				+ "' alt='分享' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>分享</strong> 按钮获取日志和分析结果的链接，以便提交给我们的团队。"
				+ "  如果你是整合包作者或组织，请编辑 <code>crash_detector/plantilla.htm</code> " + "  来自定义你的团队链接。" + "</div>";
	}

	@Override
	public String restablecerPlantilla() {
		return "重置模板";
	}

	@Override
	public String restablecer() {
		return "重置";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		return "将 " + nombreImagen + " 重置为默认设置？";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		return "将模板重置为默认设置？";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>缺少 AzureLib 的类。如果你已安装 AzureLib，请安装 2025 年 10 月 8 日之前的版本（此问题较为常见）。若未安装 AzureLib，请安装当前最新版本。</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "模组 <code>healight</code> 导致严重错误：<code>java.lang.NoSuchFieldError: INT</code>。"
				+ "此错误是因为该模组试图访问在 MCForge 47.10 及更高版本的 Minecraft 1.20+ 中已不存在的字段。" + "由于此问题，游戏无法启动。</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• 删除或更新 <code>healight</code> 模组。" + "当前版本与适用于 1.20.1 的 MinecraftForge 47.10 不兼容。"
				+ "寻找该模组的更新版本，或考虑使用替代方案。";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "严重错误：healight - 找不到 'INT' 字段";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("类 <code>").append(clase)
				.append("</code> 未实现必需的方法：<br>").append("<code>").append(metodo).append("</code><br>")
				.append("来自接口 <code>").append(interfaz).append("</code>。");

		if (!origen.isEmpty()) {
			sb.append("<br><br>可疑的模组或文件：<code>").append(origen).append("</code>。");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• 此错误发生在某个模组实现了接口但遗漏了必需方法时。" + "请更新<b>两个相关模组</b>（定义接口的和实现接口的）。" + "如果不清楚是哪些模组，请查看错误消息中出现的名称。";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "接口方法未实现 (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "一个模组试图在<b>专用服务器</b>上加载属于<b>客户端</b>的类 "
				+ "(<code>AnimationMetadataSection</code>)，这是不可能的。" + "当模组未正确分离客户端和服务器代码时，通常会出现此错误。"
				+ "安装 <code>ModernFix</code> 可能会暴露此问题，但并非直接原因。</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>快速解决：</b>临时移除 <code>ModernFix</code> 以确认服务器能否启动。" + "如果可以，则说明是其他模组在服务器端加载了客户端类。<br>"
				+ "• <b>永久解决方案：</b>找出有问题的模组（查找包含动画资源、自定义纹理或图形库的模组）并更新或删除它。<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "在服务器上加载了客户端类 (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Sinytra Connector 某个模组的配置文件已损坏。"
				+ "这通常是因为游戏意外关闭、写入失败或模组冲突导致文件中充满了空字符（<code>\\u0000</code>）所致。</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• 进入你的 Minecraft 实例的 <code>config/</code> 文件夹。<br>" + "• 查找并删除与 connector 相关的模组配置文件。<br>"
				+ "• 重新启动游戏：Sinytra Connector 将生成一个全新的干净配置文件。";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "Sinytra Connector 配置损坏";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "文件 <code>" + nombreJar
				+ "</code> 已损坏或不完整。<br>" + "系统无法读取其内容，因为ZIP文件的末尾头部丢失。<br>" + "此错误通常发生在下载中断或启动器故障后。</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "JAR文件损坏（含具体名称）";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>删除损坏的文件</b>，并从官方来源（如CurseForge、MinecraftStorage等）重新下载。<br>"
				+ "• 如果你使用的是CurseForge、Technic或Luna Pixel等启动器，请考虑改用<b>ATLauncher</b>或<b>Prism Launcher</b>，"
				+ "它们对文件完整性的校验更完善。<br>" + "• 确保下载期间你的网络连接稳定。";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "无法加载世界，因为其 NBT 文件之一已损坏 "
				+ "(例如：<code>level.dat</code>、<code>playerdata/*.dat</code> 或区块数据)。<br>"
				+ "具体错误为：<code>UTFDataFormatException: 在字节 " + byteCorrupto + " 附近输入格式错误</code>。<br><br>"
				+ "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ 在尝试任何修复前，请先完整备份整个世界文件夹。</b><br><br>"
				+ "你可以使用 <b>NBT 编辑器</b>（如 <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>）来尝试修复损坏的文件。<br>"
				+ "如果损坏严重，可使用<b>十六进制编辑器</b>（如 HxD）检查并修正无效字节 " + "（仅适用于熟悉 NBT 格式者）。<br>"
				+ "最后手段是：从备份中恢复，或使用 <code>FTB Backup</code> 等模组提供的“世界修复”功能。</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>在尝试修复前，请完整备份世界文件夹</b>。<br>" + "• 使用 NBT 编辑器（如 NBT Studio）打开并修复损坏的文件。<br>"
				+ "• 如果失败，在损坏字节位置用十六进制编辑器检查文件。<br>" + "• 若无经验，请从最近的备份中恢复。";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "世界损坏：加载 NBT 数据出错";
	}

	@Override
	public String problema_con_openAL() {
	    return "<span style='color:#" + config.obtenerColorError()
	           + "'>你的 OpenAL 出现问题。有时是 Nouveau 驱动导致的，但有时是因为应用程序自带的 OpenAL 版本与你系统发行版中的版本不兼容，你需要使用发行版提供的 OpenAL 版本。这在 Red Hat 系发行版和 Sound Physics Remastered 等声音模组中尤为常见。请参考此指南获取更多帮助：<a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>如何修复 Linux 上 Minecraft 的声音问题</a>。</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "服务器无法启动，因为世界的一个文件被其他进程锁定。<br>"
				+ "通常在以下情况下发生：<br>" + "• 已有一个服务器实例正在运行。<br>" + "• 杀毒软件或文件资源管理器打开了世界文件夹。<br>"
				+ "• 前一个进程未正确关闭并留下锁定的文件。</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>关闭所有服务器实例</b>（包括后台进程如 javaw.exe）。<br>" + "• 如果你使用的是托管面板（如 Multicraft），请通过面板完全重启服务器。<br>"
				+ "• <b>临时禁用你的杀毒软件</b>，如果你怀疑它锁定了文件。<br>" + "• 在本地系统上，关闭任何显示世界文件夹的Windows资源管理器窗口。<br>"
				+ "• 如果问题仍然存在，请手动删除世界文件夹内的 <code>session.lock</code> 文件（仅当你确定没有其他服务器在运行时）。";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "世界文件被另一进程锁定";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "حاول التعديل توسيع الصنف <code>"
				+ clasePadreFinal + "</code>، " + "لكن هذا الصنف أصبح الآن <b>نهائيًا</b> ولا يمكن الوراثة منه.<br>"
				+ "الصنف المسبب للمشكلة هو: <code>" + claseHija + "</code>.<br><br>"
				+ "يحدث هذا عادة عندما يكون التعديل مُجمَّع لإصدار سابق من ماينكرافت أو تعديل أساسي آخر "
				+ "قد قام بوضع وسم <code>final</code> على بعض الأصناف في الإصدارات الحديثة.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>حدّث جميع التعديلات المتورطة</b>، خاصة تلك التي قد تكون مرتبطة بالتعديل الأساسي المذكور.<br>"
				+ "• إذا استمرت المشكلة، ابحث عن إصدار من التعديل يتوافق مع إصدار ماينكرافت الحالي واعتمادياته.<br>"
				+ "• في بعض الحالات، قد يساعد حذف مؤقت للتعديل الذي يحتوي الصنف الفرعي في تأكيد السبب.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "محاولة وراثة صنف نهائي";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "你正在将 <b>Rubidium</b>（一个已过时的用于 Forge 的 Sodium 分支）与 <b>Iris 或 Oculus</b> 一起使用。<br>"
				+ "在较新版本的 Minecraft（1.19.2 及以上）中，" + "Rubidium 未能跟上 Sodium 的更新步伐，其依赖项出现了问题。<br><br>"
				+ "如果性能模组（Sodium、Rubidium、Embeddium、Bedium、Xeonium 等）或 Iris Shaders 与其他模组发生冲突，也可能导致此错误。</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• 从你的 <code>mods</code> 文件夹中<b>删除 Rubidium</b>。<br>"
				+ "• <b>安装 <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a></b>，"
				+ "这是一个活跃且兼容的、用于 Forge 的 Sodium 分支，支持在 1.20+ 版本中使用 Iris/Oculus。<br>"
				+ "• 确保不要同时安装多个 Sodium 分支（例如：Rubidium 和 Embeddium）。<br>"
				+ "• 如果你使用的是 Oculus 而不是 Iris，请确认它也与你的 Forge 和 Embeddium 版本兼容。";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium 已过时，与 Iris/Oculus 不兼容（OptionInstance 是 final）";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "لا يمكن للتعديل <code>Simple Voice Chat</code> تشغيل خادم الصوت الخاص به لأن "
				+ "منفذ UDP مستخدم بالفعل أو عنوان IP المُعد غير صالح.<br>"
				+ "هذا لا يمنع بدء اللعبة، لكنه يعطل وظيفة الدردشة الصوتية.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• <b>أغلق أي نسخة أخرى من ماينكرافت</b> أو تطبيق يستخدم المنفذ UDP 24454.<br>"
				+ "• إذا كنت على خادم، تأكد من أن <b>أي خدمة أخرى</b> لا تستخدم هذا المنفذ.<br>"
				+ "• في إعدادات التعديل (<code>config/voicechat/</code>)، غيّر منفذ UDP إلى منفذ حر (مثلاً 24455).<br>"
				+ "• إذا كنت تستخدم عنوان IP مخصصًا، فتحقق من صحته أو اتركه فارغًا لاستخدام الإعداد الافتراضي.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "الدردشة الصوتية: منفذ UDP مشغول أو عنوان IP غير صالح";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "方块物品 <code>" + nombreBlockItem
				+ "</code> 指向了一个空方块。<br>"
				+ "此错误通常发生在 <b>Create 的附加模组</b>（如 <code>dndecor</code>、<code>createdeco</code>）中，"
				+ "当与 <code>Amendments</code>、<code>Moonshine</code> 存在冲突或方块初始化不正确时出现。<br>"
				+ "<b>注意：</b> 这并非 Amendments 直接导致的错误，而是注册表加载深层问题的症状。</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• <b>更新所有相关模组：</b>包括 Create、Amendments、Moonshine 以及任何附加模组（特别是 <code>dndecor</code> 和 <code>createdeco</code>）。<br>"
				+ "• 如果问题仍然存在，<b>暂时逐个移除 Create 的附加模组</b>以找出罪魁祸首。<br>"
				+ "• 确保 <b>Amendments 和 Moonshine 与你的 Create 及 Forge 版本兼容</b>。<br>" + "• 检查问题模组是否有更新的测试版或分支版本。";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "Create 附加模组中的空方块物品";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>")
				.append("发现了一些不属于任何激活平台（Forge、Fabric等）的模组：<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>这通常发生在以下情况：<br>").append("• 在同一文件夹中混合了 <b>Fabric 和 Forge</b> 模组。<br>")
				.append("• 安装了与 Minecraft 版本不兼容的模组。<br>").append("• 模组已损坏或不是有效的 JAR 文件。</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>请确认所有模组都适用于同一平台</b>（仅 Forge <b>或</b> 仅 Fabric，不可混用）。<br>" + "• 使用<b>模组树</b>来识别每个文件对应的平台。<br>"
				+ "• 删除任何你不认识或属于不同平台的模组。<br>" + "• 如果你使用的是 CurseForge 或 Prism 等启动器，请确保配置文件设置正确。";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "模组与当前加载器不兼容";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "无法创建模型 <code>" + modid + ":" + nombreModelo
				+ "</code>。<br>" + "这表明模组 <code>" + modid + "</code> 的资源已损坏、缺失，" + "或与你当前的 Minecraft 版本不兼容。</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>更新该模组</b>至与你的实例兼容的最新版本。<br>" + "• 如果你使用的是开发版或自定义版本，请切换回官方发布版。<br>" + "• 检查 JAR 文件是否损坏（重新安装）。<br>"
				+ "• 如果问题仍然存在，请将此日志附上并报告给模组作者。";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "创建资源模型失败";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "检测到 <code>Moonlight</code> 和 <code>Iceberg</code> 模组之间存在严重冲突。<br>" + "两者都试图以不兼容的方式注册资源重载系统，"
				+ "导致因缺少有效的图形上下文而引发 OpenGL 错误。<br>" + "当使用包含 Fabric 模组适配器的 Forge 版本时，此问题很常见。</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>将两个模组都更新</b>到与你当前 Forge 版本兼容的最新版本。<br>"
				+ "• 如果问题仍然存在，<b>暂时移除 Iceberg</b>，因为 Moonlight 通常是其他模组的关键依赖。<br>"
				+ "• 确保没有重复或混合安装这些模组的 Forge/Fabric 版本。<br>"
				+ "• 检查是否有其他模组（如 Supplementaries、Citadel 等）已内置了 Iceberg 的功能。";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "严重冲突：Moonlight 与 Iceberg 冲突 (OpenGL 无上下文)";
	}

	@Override
	public String instantanea() {
		return "快照";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "自上次快照以来";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "选择一个文件";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "快照创建成功";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "创建快照时出错";
	}

	@Override
	public String consejo() {
		return "提示";
	}

	@Override
	public String resultadoMuestra() {
		return "结果预览";
	}

	@Override
	public String historaDeModsDesc() {
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>" + "  <b>提示：</b>选择两个历史记录文件来比较模组列表。"
				+ "  结果会根据标准化的名称显示<span style='color:%s;'>新增的 (+)</span>和"
				+ "  <span style='color:%s;'>已移除的 (&#8722;)</span>。" + "  使用“快照”按钮为现有文件创建一个扩展名为 .instantanea 的副本。"
				+ "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		return "获取日志链接（Markdown格式，不含报告）";
	}

	@Override
	public String titulo_configuracion() {
		return "设置";
	}

	@Override
	public String columna_url() {
		return "网址";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "分享时发生意外错误。";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "生成链接时发生意外错误。";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "处理按钮时发生意外错误。";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "没有关联的文件可供打开。";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "文件不存在：\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "无法在编辑器中打开。\n路径将被复制到剪贴板。";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "无法打开文件；路径已复制到剪贴板。";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "桌面操作不受支持；路径已复制到剪贴板。";
	}

	@Override
	public String limite_de_solicitudes() {
		return "您遇到了请求频率限制。请尝试使用其他日志网站或其他日志API。";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		return "分享链接";
	}

	@Override
	public String infoDeTrazos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "修复原木顶部问题是第一优先级。" + "格式为：层级，行号。"
				+ "所有日志都有编号系统。" + Verificaciones.nl_html + "通常你需要在所有日志中查找最底层的堆栈（低层级），高层数的堆栈通常是误报。"
				+ "使用你的控制台阅读能力很重要，因为当堆栈过多时，堆栈分析并不完美。" + "</b>";
	}

	// --- 令状蜜罐搜索器 (Warrant Canary) ---
	/**
	 * 令状蜜罐搜索器按钮上的文本。 示例：“令状蜜罐搜索器”
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "令状蜜罐搜索器";
	}

	/**
	 * 对话框中显示的消息，提示 该功能即将上线。
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "此功能即将上线。";
	}

	/**
	 * 提示令状蜜罐搜索器功能 即将推出的对话框标题。
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "即将推出";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
	    return "与 Crash Assistant 不兼容的模组（误报）";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
	    return "使用 CrashAssistant 的整合包存在不兼容的模组";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>Crash Assistant 有一个标记为不兼容的模组列表，但我们没有证据表明它们真的不兼容，且该错误提示仅以英文显示。如果你想使用这些模组，可以编辑文件 <code>config/crash_assistant/config.toml</code>，将 [compatibility] 部分中的 <code>enabled = true</code> 修改为 <code>enabled = false</code>。</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>Crash Assistant 具有判断模组不兼容的能力，但有时并不准确，且错误信息仅为英文。如果你想使用这些模组，可以编辑文件 <code>config/crash_assistant/problematic_mods_config.json</code>，将 <code>should_crash_on_startup</code> 从 <code>true</code> 改为 <code>false</code>。</b>";
	}
	
	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
	    return "<span style='color:#" + config.obtenerColorError() + "'>" +
	           "错误：模组 '" + modId + "' 需要模组 '" + dependencia + "'。当前，" + actual + "。" +
	           "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
	    return "<span style='color:#" + config.obtenerColorError() + "'>" +
	           "错误：模组 '" + modId + "' 需要 '" + dependencia + "' 的 '" + requerido + "' 或更高版本，但该模组未安装。" +
	           "</span>";
	}
	
	// 在类 MonitorDePID.idioma 中（添加此方法）
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
	    return "<span style='color:#" + config.obtenerColorError() + "'>" +
	           "错误：模组 '" + modId + "' 与 '" + dependencia + "' 的当前版本不兼容。" +
	           "你必须删除模组 'Iris/Oculus & GeckoLib Compat'，因为它与 Superb Warfare 不兼容，并且无法与最新版 GeckoLib 正常工作。" +
	           "当前版本：" + versionActual +
	           "</span>";
	}
	
	public String fallo_ejecucion_tarea_descripcion(String clase) {
	    return "错误：无法为类 '" + clase + "' 执行任务。" +
	           "当模组之间不兼容或与其他已安装的模组发生冲突时，通常会出现此错误。";
	}

	public String nombre_fallos_ejecucion_tareas() {
	    return "任务执行失败";
	}

	public String recomendacion_fallos_ejecucion() {
	    return "此类错误通常是由于模组之间的不兼容性引起的。" +
	           "特别是那些在使用 ConnectorMod 时不能正常工作的模组。";
	}

	public String info_clase_problematica() {
	    return "问题类：";
	}

	public String ver_en_log() {
	    return "在日志中查看";
	}

	public String no_se_encontraron_clases_problema() {
	    return "未找到存在执行问题的具体类。";
	}
	@Override
	public String errorConflictoOptiFineEMF() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	           + "检测到 OptiFine 和实体模型功能 (EMF) 之间存在严重冲突。"
	           + "这两个模组不兼容，会导致注入失败，从而阻止游戏启动。" + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
	    return "OptiFine 与实体模型功能冲突";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
	    return "卸载 OptiFine 或 实体模型功能 (EMF)，因为它们彼此不兼容。";
	}
	
	@Override
	public String errorConflictoOptiFineFusion() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	           + "检测到 OptiFine 和 Fusion 之间存在严重冲突。"
	           + "这两个模组不兼容，会导致注入失败，从而阻止游戏启动。" + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
	    return "OptiFine 与 Fusion 冲突";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
	    return "卸载 OptiFine 或 Fusion，因为它们彼此不兼容。";
	}
	
	@Override
	public String errorConflictoFlywheelSodium() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "Flywheel（由 Create 所需）需要 Sodium 0.6.0-beta.2 或更高版本。Rubidium 是 0.5.3。"
	            + "请考虑使用 <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> 作为替代方案。" + "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
	    return "Flywheel 与 Sodium 版本冲突";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
	    return "将 Sodium 更新至 0.6.0-beta.2 或更高版本，或安装 <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> 作为兼容的替代品。";
	}
	
	@Override
	public String errorConflictoOptiFineEpicFight() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "检测到 OptiFine 和 Epic Fight 之间存在严重冲突。"
	            + "这两个模组不兼容，会导致注入失败，从而阻止游戏启动。" + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
	    return "OptiFine 与 Epic Fight 冲突";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
	    return "卸载 OptiFine 或 Epic Fight，因为它们彼此不兼容。";
	}
	
	@Override
	public String errorConflictoOptiFineRubidium() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "检测到 OptiFine 和 Rubidium 之间的严重冲突。 "
	            + "这些模组不兼容，会导致注入失败，使游戏无法启动。" + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
	    return "OptiFine 和 Rubidium 冲突";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
	    return "卸载 OptiFine 或 Rubidium，因为它们彼此不兼容。";
	}
	
	@Override
	public String errorFreeCamServidor() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "FreeCam 正试图在专用服务器上加载，但它仅与客户端兼容。 "
	            + "请从服务器中移除 FreeCam，或确保它仅安装在客户端上。" + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
	    return "FreeCam 在专用服务器上";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
	    return "请从专用服务器中移除 FreeCam，因为它只能安装在客户端上。";
	}
	
	@Override
	public String errorEntityTextureFeaturesServidor() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "Entity Texture Features (ETF) 正试图在专用服务器上加载，但它仅与客户端兼容。 "
	            + "请从服务器中移除 ETF，或确保它仅安装在客户端上。" + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
	    return "Entity Texture Features 在专用服务器上";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
	    return "请从专用服务器中移除 Entity Texture Features，因为它只能安装在客户端上。";
	}
	
	@Override
	public String errorEULANoAceptado() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "必须接受 Minecraft 的 EULA 才能运行服务器。 "
	            + "编辑 eula.txt 文件，将 'eula=false' 改为 'eula=true'。" + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
	    return "Minecraft 的 EULA 未接受";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
	    return "编辑服务器文件夹中的 eula.txt 文件，将 'eula=false' 改为 'eula=true'。";
	}
	
	@Override
	public String errorOptiFineServidor() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "OptiFine 正试图在专用服务器上加载，但它仅与客户端兼容。 "
	            + "请从服务器中移除 OptiFine，或确保它仅安装在客户端上。" + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
	    return "OptiFine 在专用服务器上";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
	    return "请从专用服务器中移除 OptiFine，因为它只能安装在客户端上。";
	}
	
	@Override
	public String errorIronSpellbooksVersion() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "Iron's Spellbooks 被错误地标记为 1.20.1 版本，但使用了 1.21.1 的方法。 "
	            + "该模组正尝试使用在 1.20.1 中不存在的 ResourceLocation.fromNamespaceAndPath。" + "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
	    return "Iron's Spellbooks 版本错误";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
	    return "请确保使用与你的 Minecraft 版本兼容的 Iron's Spellbooks 正确版本。";
	}
	
	@Override
	public String errorConflictoOptiFineEmbeddium() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "检测到 OptiFine 和 Embeddium 之间的严重冲突。 "
	            + "这些模组不兼容，会导致注入失败，使游戏无法启动。" + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
	    return "OptiFine 和 Embeddium 冲突";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
	    return "卸载 OptiFine 或 Embeddium，因为它们彼此不兼容。";
	}
	
	@Override
	public String noPuedeAnalizarJSON() {
	    return "<span style='color:#" + config.obtenerColorError()
	        + "'>这通常由世界生成模组冲突引起，尤其是 Terralinth、AmplifiedNether、Nullscape 和 Incendium 等世界生成类模组。也可能是因为缺少某个必需的模组。</span>";
	}
	@Override
	public String errorControllableServidor() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "Controllable 正试图在专用服务器上加载，但它仅与客户端兼容。 "
	            + "请从服务器中移除 Controllable，或确保它仅安装在客户端上。" + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
	    return "Controllable 在专用服务器上";
	}

	@Override
	public String pasoErrorControllableServidor() {
	    return "请从专用服务器中移除 Controllable，因为它只能安装在客户端上。";
	}
	
	@Override
	public String errorSupplementariesCargaServidor() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "Supplementaries 正在引发一个错误，导致服务器无法加载。 "
	            + "该模组在注册火焰行为时存在问题，会在 datapacks 加载期间引发故障。" + "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
	    return "Supplementaries 阻止服务器加载";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
	    return "请尝试将 Supplementaries 更新到最新版本，或暂时禁用它以允许服务器加载。";
	}
	
	@Override
	public String errorGroovyModloaderModuloFaltante() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "Groovy Modloader (GML) 发现缺少 Jackson 模块的问题。 "
	            + "某些模组（如 Valkyrien Skies）可能因未包含所有必要依赖而引发此错误。" + "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
	    return "Groovy Modloader 缺少 Jackson 模块";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
	    return "移除 Groovy Modloader 以及可能引发依赖冲突的相关模组（如 Valkyrien Skies）。";
	}
	
	@Override
	public String errorEveryCompatNombreInvalido() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "Every Compat 发现了一个无效的木头方块名称。 "
	            + "Every Compat 通常存在很多问题。请不要使用它！" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
	    return "Every Compat 中的无效名称";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
	    return "检查使用 Every Compat 的资源包或模组，它们可能包含无效的方块名称。";
	}
	
	

	@Override
	public String errorCodigo1073741819() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "检测到错误代码 (-1073741819)，可能由 Razer 的 GameCaster、Discord、OBS Studio 等 overlay 或 NVIDIA 驱动问题引起。" + "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
	    return "错误代码 -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
	    return "尝试禁用 GameCaster、Discord 或 OBS Studio 等 overlay，并确保你的 NVIDIA 驱动程序已更新。";
	}
	
	@Override
	public String errorImmersiveTooltipsSinDependencia() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "Immersive Tooltips 需要依赖 Immersive Messages，但未安装。 "
	            + "请安装 Immersive Messages 以确保 Immersive Tooltips 正常工作。" + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
	    return "Immersive Tooltips 缺少依赖";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
	    return "安装 Immersive Messages，因为它是 Immersive Tooltips 所需的依赖。";
	}
	
	@Override
	public String errorMedievalOriginsCast() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "Medieval Origins 与 Apoli Mod 存在兼容性问题，其中 ItemStack 无法转换为 EntityLinkedItemStack。 "
	            + "此问题常见于 6.6.0 以上版本。建议使用更早版本，或尝试在 Fabric 和 Forge 版本之间切换。" + "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
	    return "Medieval Origins 类型转换错误";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
	    return "请使用 Medieval Origins 6.6.0 或更早版本，或尝试切换该 mod 的 Fabric 与 Forge 版本。";
	}
	
	@Override
	public String errorReignOfNetherMusicManager() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "Reign of Nether 正在引发一个与 MusicManager 中缺失的 Registry Object 相关的错误。 "
	            + "此问题与 Reign of Nether 的 MusicManager mixin 有关。" + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
	    return "Reign of Nether 中的 MusicManager 错误";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
	    return "尝试更新 Reign of Nether，或临时移除它以解决此错误。";
	}
	
	@Override
	public String errorYesSteveModelLinux() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "YesSteveModel 仅支持 Linux 或 Android 上的 YSM 服务器。 "
	            + "此问题已在 2025 年 11 月 23 日之后 Modrinth 上的更新版本中修复。" + "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
	    return "YesSteveModel 与 Linux 不兼容";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
	    return "请从 Modrinth 更新 YesSteveModel 至更新的版本，该问题已在 11 月 23 日之后修复。";
	}
	
	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "检测到 Moving Elevators 和 OptiFine 之间的严重冲突。 "
	            + "这些模组不兼容，会导致注入失败，使游戏无法启动。" + "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
	    return "Moving Elevators 与 OptiFine 冲突";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
	    return "卸载 OptiFine 或 Moving Elevators，因为它们彼此不兼容。";
	}
	
	@Override
	public String errorConflictoFabricAPIOptiFine() {
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	            + "检测到 Fabric API（fabric-resource-loader-v0）与 OptiFine 之间的严重冲突。 "
	            + "这些模组不兼容，会导致注入失败，使游戏无法启动。" + "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
	    return "Fabric API 与 OptiFine 冲突";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
	    return "卸载 OptiFine，或将 Fabric API 更新到兼容版本。";
	}
	
	
	
	
	
	
	
	
	
}
