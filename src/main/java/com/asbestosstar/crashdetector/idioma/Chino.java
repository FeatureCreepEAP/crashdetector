package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Chino implements Idioma {
	private final Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "zh";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "chino";
	}

	@Override
	public String nombre_del_idioma() {
		return "中文";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_china.png");
	}

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
				+ "'>你正在使用“跳过启动器启动”（CurseForge 应用）。这有时会导致难以检测的问题。此选项存在于旧版 CurseForge 应用或新版中。请禁用它，并在 CurseForge 设置中改用“Mojang 启动器”。你可以在 Claws of Berk 的英文视频中查看说明（跳转至 1:11）"
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>点击此处</a>.</b>";
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
		return "此对话框允许你通过 SecureLogger API "
				+ "在 <a href=\"https://securelogger.net\">securelogger.net</a> 分享日志。点击“分享报告”按钮后，"
				+ "你的报告将发送至所选端点（默认为 asbestosstar.egoism.jp）（可在底部更改）。" + "你可以将所有选中的日志与报告一并分享。如果你不想上传，请勿使用此对话框！"
				+ "我们不会在官方端点处理你的报告（<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\">https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb</a>）；"
				+ "仅会移除不允许的链接。源代码在此：<a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">源代码</a>。"
				+ "此功能仅用于显示你的崩溃信息及日志链接。但你也可以使用自定义端点，其处理方式可能不同。" + "你当前使用的报告站点为 "
				+ Config.obtenerInstancia().obtenerSitoDeInformes() + "，日志站点为 "
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + "。"
				+ "你也可以点击单个日志名称旁的分享按钮来单独分享日志（无需报告）；" + "日志将上传至所选日志站点。CrashDetector 默认启用日志匿名化功能，会尝试移除用户名、UUID、"
				+ "访问令牌、会话 ID、IP 地址等数据。但该功能并非完美，且整合包作者可将其禁用。" + "你可通过本页面底部的复选框启用或禁用该功能。你是自身数据的控制者，由你决定上传位置。"
				+ "日志站点由第三方运营，其所有权常因隐私原因而隐藏。你需自行承担数据管理及相关风险的全部责任。" + "CrashDetector 的分享对话框仅是一个供你管理这些操作的界面。"
				+ "请务必了解 GDPR 和 ARCO 的相关规定。"
				+ "如果你位于欧洲，可使用由 Hetzner 在德国托管的 <a href=\"https://securelogger.top\">securelogger.top</a>。"
				+ "更多法律信息，请参阅以下链接："
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>、"
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">GDPR</a>、"
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">日本个人数据保护基本政策</a>。";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "NightConfig/Forge 配置严重错误：" + "配置文件已损坏或不完整。"
				+ "这通常由 'config' 文件夹中的空配置文件（常为 0 字节）引起，多见于旧版或自定义版 NightConfig。"
				+ "对于大多数版本，Night Config Fixes 可解决此问题；但若你使用的是不兼容或自定义的 NightConfig 版本，则需手动删除配置文件。"
				+ "此问题在旧版 MC Forge（自带 NightConfig）和打包了 NightConfig 的旧版 FabricMC 模组中尤为常见，但也可能出现在某些自定义 NightConfig 版本中。"
				+ "更多解决方案详见 <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a>。</b>";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>世界加载区块（chunks）时发生异常。如果你的平台有对应版本，FeatureRecycler 或许能解决此问题。https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
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

	/**
	 * 返回有关问题实体或方块实体的错误消息， 并根据平台详细说明恢复步骤。
	 */
	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		String color = config.obtenerColorError();

		// 主要消息：仅描述性文本使用错误颜色
		String mensajeBase = "<span style='color:#" + color + "'>实体或方块实体 '</span>" + nombre + "<span style='color:#"
				+ color + "'>' 类型 '</span>" + tipo + "<span style='color:#" + color + "'>' 位于位置 </span>" + coords
				+ "<span style='color:#" + color + "'> 正在导致 ticking 错误。</span><br><br>";

		// 修复说明
		String instrucciones = "<span style='color:#" + color + "'>" + "恢复说明:<br>"
				+ "1. **MCForge**: 前往 '[nombre_del_mundo]/serverconfig/forge-server.toml'。<br>"
				+ "2. **NeoForge**: 前往 'config/neoforge-server.toml'。<br>"
				+ "   *（注意：在本地游戏/Singleplayer 中，世界文件位于 'saves' 文件夹内）*。<br>"
				+ "3. 将 **removeErroringBlockEntities** 和 **removeErroringEntities** 设置为 **true**。<br><br>"
				+ "其他选项:<br>" + "- **MCEdit**: 用于手动删除指定坐标处的实体。<br>"
				+ "- **Neruina (Mod)**: 可能避免崩溃，但并不总是有效，且安装后可能增加调试难度。" + "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRegistroDuplicadoObjeto() {
		return "重复注册冲突";
	}

	@Override
	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// 主要消息：仅描述性文本使用错误颜色
		String mensajeBase = "<span style='color:#" + color + "'>严重冲突：尝试重复注册同一对象。 " + "模组 </span>" + mod1
				+ "<span style='color:#" + color + "'> 和 </span>" + mod2 + "<span style='color:#" + color
				+ "'> 正在尝试注册相同的对象。 " + "冲突对象： </span>" + objeto + "<br><br>";

		// 修复说明
		String instrucciones = "<span style='color:#" + color + "'>" + "这通常发生在两个不同的模组在同一个 namespace 中添加了同名的对象， "
				+ "或者其中一个模组的代码存在错误时。<br><br>" + "<b>推荐解决方案：</b><br>" + "<ul>" + "<li>检查其中一个模组是否为另一个模组的更新版本或分支。</li>"
				+ "<li>尝试移除两个冲突模组中的一个。</li>" + "<li>检查两个模组的配置文件，查看是否可以更改对象的 ID。</li>" + "</ul></span>";

		return mensajeBase + instrucciones;
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
		sb.append(
				"<br/><b>解决方案：</b>安装或启用定义这些函数的 mod/datapack 并重启。另一个常见原因是：虽然你安装了所需的 mod，但它自身存在问题或与其他 mod 冲突；例如，Terralith 存在许多问题，可能引发此错误及其他问题，包括 JSON 错误。");
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
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant 有一个标记为不兼容的模组列表，但我们没有证据表明它们真的不兼容，且该错误提示仅以英文显示。如果你想使用这些模组，可以编辑文件 <code>config/crash_assistant/config.toml</code>，将 [compatibility] 部分中的 <code>enabled = true</code> 修改为 <code>enabled = false</code>。</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant 具有判断模组不兼容的能力，但有时并不准确，且错误信息仅为英文。如果你想使用这些模组，可以编辑文件 <code>config/crash_assistant/problematic_mods_config.json</code>，将 <code>should_crash_on_startup</code> 从 <code>true</code> 改为 <code>false</code>。</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "错误：模组 '" + modId + "' 需要模组 '" + dependencia
				+ "'。当前，" + actual + "。" + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "错误：模组 '" + modId + "' 需要 '" + dependencia
				+ "' 的 '" + requerido + "' 或更高版本，但该模组未安装。" + "</span>";
	}

	// 在类 MonitorDePID.idioma 中（添加此方法）
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "错误：模组 '" + modId + "' 与 '" + dependencia
				+ "' 的当前版本不兼容。"
				+ "你必须删除模组 'Iris/Oculus & GeckoLib Compat'，因为它与 Superb Warfare 不兼容，并且无法与最新版 GeckoLib 正常工作。" + "当前版本："
				+ versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "错误：无法为类 '" + clase + "' 执行任务。" + "当模组之间不兼容或与其他已安装的模组发生冲突时，通常会出现此错误。";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "任务执行失败";
	}

	public String recomendacion_fallos_ejecucion() {
		return "此类错误通常是由于模组之间的不兼容性引起的。" + "特别是那些在使用 ConnectorMod 时不能正常工作的模组。";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "检测到 OptiFine 和实体模型功能 (EMF) 之间存在严重冲突。"
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "检测到 OptiFine 和 Fusion 之间存在严重冲突。"
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
				+ "请考虑使用 <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> 作为替代方案。"
				+ "</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "检测到 OptiFine 和 Epic Fight 之间存在严重冲突。"
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "检测到 OptiFine 和 Rubidium 之间的严重冲突。 "
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "FreeCam 正试图在专用服务器上加载，但它仅与客户端兼容。 "
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
				+ "Entity Texture Features (ETF) 正试图在专用服务器上加载，但它仅与客户端兼容。 " + "请从服务器中移除 ETF，或确保它仅安装在客户端上。" + "</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "必须接受 Minecraft 的 EULA 才能运行服务器。 "
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "OptiFine 正试图在专用服务器上加载，但它仅与客户端兼容。 "
				+ "请从服务器中移除 OptiFine，或确保它仅安装在客户端上。" + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine 在专用服务器上";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "请从专用服务器中移除 OptiFine，因为它仅应安装在客户端。此问题通常也源于安装了与 Minecraft 版本不匹配的 OptiFine 版本，或 OptiFine 与其他模组发生冲突。";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "检测到 OptiFine 和 Embeddium 之间的严重冲突。 "
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Controllable 正试图在专用服务器上加载，但它仅与客户端兼容。 "
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Supplementaries 正在引发一个错误，导致服务器无法加载。 "
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Groovy Modloader (GML) 发现缺少 Jackson 模块的问题。 "
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Every Compat 发现了一个无效的木头方块名称。 "
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
				+ "YesSteveModel 仅支持 Linux 或 Android 上的 YSM 服务器。 " + "此问题已在 2025 年 11 月 23 日之后 Modrinth 上的更新版本中修复。"
				+ "</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "检测到 Moving Elevators 和 OptiFine 之间的严重冲突。 "
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
				+ "检测到 Fabric API（fabric-resource-loader-v0）与 OptiFine 之间的严重冲突。 " + "这些模组不兼容，会导致注入失败，使游戏无法启动。" + "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "Fabric API 与 OptiFine 冲突";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "卸载 OptiFine，或将 Fabric API 更新到兼容版本。";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "某个模组包含一个有缺陷的 ITransformationService，无法实例化："
				+ claseProveedor + "。" + "必须移除此模组才能加载游戏。" + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "有缺陷的 ITransformationService";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "移除包含类 " + claseProveedor + " 的模组，因为它包含一个有缺陷的 ITransformationService。";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError() + "'>某个模组的版本声明无效。 " + "版本号必须用方括号括起来。 "
				+ "你可以使用侧边栏中的 grep/greprf 工具搜索版本号 </span>" + version + "<span style='color:#"
				+ config.obtenerColorError() + "'>，以确定是哪个模组存在问题。</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "模组中的无效版本";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "使用侧边栏中的 grep/greprf 工具搜索有问题的版本号，找到包含它的模组。";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "检测到 stack smashing 错误，导致进程终止。 "
				+ "这可能是由 Forge/NeoForge/PillowMC 中的 Early Window 问题，或 LWJGL 3.2.2 及更高版本引起的。" + "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "检测到 Stack Smashing";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "检查 Early Window 设置，并考虑使用其他版本的 LWJGL，或检查与早期窗口相关的模组。";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore 仅适用于特定的整合包（modpack），不应在通用安装中使用，否则会导致问题。" + "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore 与 Java 版本不兼容";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "请移除 GregTechEasyCore，因为它仅适用于特定整合包，与你的通用安装不兼容。";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "检测到 MoniLabs 与 Connector Extras 之间存在与 KubeJS 修改相关的冲突。 " + "这些模组在 KubeJS 修改方面不兼容。" + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "MoniLabs 与 Connector Extras 冲突";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "尝试卸载其中一个模组（MoniLabs 或 Connector Extras），因为它们的 KubeJS 修改存在冲突。";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris 需要 Distant Horizons [2.0.4] 或 DH API 版本 [1.1.0] 或更高版本。"
				+ "请查阅兼容性指南：https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e 以解决问题。" + "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Iris 与 Distant Horizons 兼容性";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "请查阅兼容性指南 https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e，并将 Iris 和 Distant Horizons 更新至兼容版本。";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError() + "'>你缺少 Minecraft 的类文件。可能原因：</b>" + "<ul>"
				+ "<li>你安装了适用于其他游戏版本的模组。你可以使用 <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> 来确认该类是否存在于你的版本中。</li>"
				+ "<li>你的 Minecraft 安装损坏（常见于 CurseForge App、ModrinthApp/Theseus/Astralrinth 及其他整合包启动器）。<a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>查看教程</a> 以解决 CurseForge 问题。</li>"
				+ "<li>你有一个损坏的 coremod（coremod 失败可能会阻止类的加载）。</li>" + "</ul>"
				+ "<p>注意：只要在名称中使用 '/'，你就可以使用侧边栏中的 <b>grepr/fgrepr</b> 工具来查找引用缺失类的模组。</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError() + "'>你缺少 DangerZone 的类文件。可能原因：</b>" + "<ul>"
				+ "<li>你安装了适用于其他游戏版本的模组。</li>" + "<li>你有损坏的 coremod。</li>" + "<li>你的启动器或安装损坏。</li>" + "</ul>"
				+ "<p>注意：只要在名称中使用 '/'，你就可以使用侧边栏中的 <b>grepr/fgrepr</b> 工具来查找引用缺失类的模组。</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError() + "'>你缺少 FeatureCreep 的类文件。可能原因：</b>" + "<ul>"
				+ "<li>你安装了适用于其他版本 FeatureCreep 的模组（例如：ESR 与 Nightly，或 v4 与 v12）。</li>"
				+ "<li>你可以从 CurseForge 或 MinecraftStorage 安装 FeatureCreep。</li>" + "</ul>"
				+ "<p>注意：只要在名称中使用 '/'，你就可以使用侧边栏中的 <b>grepr/fgrepr</b> 工具来查找引用缺失类的模组。</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError() + "'>你缺少 ModLauncher 的类文件。可能原因：</b>" + "<ul>"
				+ "<li>你的模组适用于不同版本的 MinecraftForge、PillowMC 或 NeoForge（ModLauncher 用于这些加载器）。</li>"
				+ "<li>Minecraft 的每个版本都有大量 modloader 更新。</li>"
				+ "<li>你的启动器安装损坏（常见于 CurseForge App、ModrinthApp/Theseus/Astralrinth 等整合包启动器）。<a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>查看教程</a> 以解决 CurseForge 问题。</li>"
				+ "</ul>" + "<p>注意：只要在名称中使用 '/'，你就可以使用侧边栏中的 <b>grepr/fgrepr</b> 工具来查找引用缺失类的模组。</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>你缺少 Minecraft Forge 的类文件。可能原因：</b>" + "<ul>"
				+ "<li>你的模组适用于不同版本的 MinecraftForge。</li>" + "<li>Minecraft 的每个版本都有大量 modloader 更新。</li>"
				+ "<li>你的安装损坏（常见于 CurseForge App、ModrinthApp/Theseus/Astralrinth 等整合包启动器）。<a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>查看教程</a> 以解决 CurseForge 问题。</li>"
				+ "</ul>" + "<p>注意：只要在名称中使用 '/'，你就可以使用侧边栏中的 <b>grepr/fgrepr</b> 工具来查找引用缺失类的模组。</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError() + "'>你缺少 NeoForge 的类文件。可能原因：</b>" + "<ul>"
				+ "<li>你的模组适用于不同版本的 NeoForge。</li>" + "<li>Minecraft 的每个版本都有大量 modloader 更新。</li>"
				+ "<li>你的安装损坏（常见于 CurseForge App、ModrinthApp/Theseus/Astralrinth 等整合包启动器）。<a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>查看教程</a> 以解决 CurseForge 问题。</li>"
				+ "</ul>" + "<p>注意：只要在名称中使用 '/'，你就可以使用侧边栏中的 <b>grepr/fgrepr</b> 工具来查找引用缺失类的模组。</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError() + "'>你缺少 Fabric Loader 的类文件。可能原因：</b>" + "<ul>"
				+ "<li>你的模组适用于不同版本的 Fabric Loader。</li>" + "<li>Minecraft 的每个版本都有大量 modloader 更新。</li>"
				+ "<li>你的安装损坏（常见于 CurseForge App、ModrinthApp/Theseus/Astralrinth 等整合包启动器）。<a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>查看教程</a> 以解决 CurseForge 问题。</li>"
				+ "<li>许多模组需要 Fabric API。如需要，请安装 Fabric API。</li>" + "</ul>"
				+ "<p>注意：只要在名称中使用 '/'，你就可以使用侧边栏中的 <b>grepr/fgrepr</b> 工具来查找引用缺失类的模组。</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError() + "'>你缺少 PillowMC 的类文件。可能原因：</b>" + "<ul>"
				+ "<li>你的模组适用于不同版本的 PillowMC。</li>" + "<li>Minecraft 的每个版本都有大量 modloader 更新。</li>"
				+ "<li>你的安装损坏（常见于 CurseForge App、ModrinthApp/Theseus/Astralrinth 等整合包启动器）。<a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>查看教程</a> 以解决 CurseForge 问题。</li>"
				+ "</ul>" + "<p>注意：只要在名称中使用 '/'，你就可以使用侧边栏中的 <b>grepr/fgrepr</b> 工具来查找引用缺失类的模组。</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "你安装了一个故意造成卡顿的模组。Uranium 是一个卡顿模组。它并非总会导致崩溃，但最终可能会。" + "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack 被标记为兼容 1.19.*，但实际是为 1.20.* 设计的，导致出现“类未找到”错误。"
				+ "该模组尝试使用当前 Minecraft 版本中不存在的 DamageSources。" + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Falling Attack 版本错误";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "请确保你使用的 Falling Attack 版本与你的 Minecraft 版本兼容。";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>").append("你需要安装 CFR (Class File Reader) 才能使用此功能。<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append("在 Linux、NetBSD 或 FreeBSD 系统上，你可以通过包管理器安装 CFR。<br>").append(
					"在此搜索包：<a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append("或者，你可以从以下地址下载 FabricMC 使用的修改版：<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("将其保存到以下文件夹：<br>").append("<b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>").append("⚠️ <b>重要：</b>安装 CFR 后，必须重启 mod 才能正确识别。").append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "无可用头像";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "无法找到类：" + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "CFR 反编译器 – Sakura Riddle（非官方）";
	}

	@Override
	public String cfrClaseActual() {
		return "当前类";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "Sakura Riddle 头像";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "加载头像时出错";
	}

	public String noticiaLegalCFR() {
		return "本图形用户界面（GUI）反编译工具旨在帮助用户诊断模组崩溃的根本原因。" + "然而，反编译模组可能存在法律风险，用户务必确保不利用生成的代码侵犯《联邦著作权法》。"
				+ "建议在使用任何反编译代码前，仔细查阅对应模组的许可证。此外，许多模组开发者会主动公开源代码，"
				+ "这类官方源码通常比反编译结果更清晰、易读。请始终尊重知识产权及软件许可，这是模组开发社区的核心原则。" + "您可通过以下链接查阅墨西哥《联邦著作权法》："
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">联邦著作权法（西班牙语）</a>，"
				+ "英文版在此：<a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (English)</a>。"
				+ "鉴于您正在 CurseForge 平台，我们同时提供美国版权法链接："
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>。"
				+ "我们建议用户主动了解所在地适用的法律法规。" + "本 GUI 仅适用于简单诊断；如需深度分析，请使用 FabricMC 维护的 Enigma 分支："
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>。若需在无源码情况下直接编辑 JAR 文件打补丁，可使用 Recaf："
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">官方网站</a>。";
	}

	@Override
	public String botonDescargarCfr() {
		return "下载 CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "打开安装文件夹";
	}

	@Override
	public String colorFondoPrincipal() {
		return "主背景颜色";
	}

	@Override
	public String colorTextoBotonReset() {
		return "重置按钮文字颜色";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "搜索框文字颜色";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "筛选下拉菜单文字颜色";
	}

	@Override
	public String colorTextoRenderer() {
		return "渲染器文字颜色";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "加载覆盖层文字颜色";
	}

	@Override
	public String colorBorde() {
		return "边框颜色";
	}

	@Override
	public String colorFondoRetrato() {
		return "人像模式背景颜色";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "分享链接颜色";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "分享字段背景颜色";
	}

	@Override
	public String rosaFondo() {
		return "背景粉红色";
	}

	@Override
	public String rosaSuave() {
		return "柔和粉红";
	}

	@Override
	public String moradoAcento() {
		return "强调紫色";
	}

	@Override
	public String textoOscuro() {
		return "深色文字";
	}

	@Override
	public String bordeSuave() {
		return "柔和边框";
	}

	@Override
	public String fondoCampo() {
		return "字段背景";
	}

	@Override
	public String fondoVistaPrevia() {
		return "预览背景";
	}

	@Override
	public String sintaxisConstructor() {
		return "语法颜色：构造函数";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "语法颜色：帮助消息";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "语法颜色：HTML 标签";
	}

	@Override
	public String colorFondoVentana() {
		return "窗口背景颜色";
	}

	@Override
	public String colorPanel() {
		return "面板颜色";
	}

	@Override
	public String colorBotonTexto() {
		return "按钮文字颜色";
	}

	@Override
	public String colorCampo() {
		return "字段颜色";
	}

	@Override
	public String colorBordeDestacado() {
		return "高亮边框颜色";
	}

	@Override
	public String colorSeleccionTexto() {
		return "文本选中背景色";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "选中文本颜色";
	}

	@Override
	public String colorEstadoExito() {
		return "状态颜色：成功";
	}

	@Override
	public String colorEstadoFallo() {
		return "状态颜色：失败";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "状态颜色：即时";
	}

	@Override
	public String colorResultadoAnadido() {
		return "已添加结果颜色";
	}

	@Override
	public String colorResultadoEliminado() {
		return "已删除结果颜色";
	}

	@Override
	public String colorBordeScroll() {
		return "滚动条边框颜色";
	}

	@Override
	public String colorFondoPanel() {
		return "面板背景颜色";
	}

	@Override
	public String colorBeigeListas() {
		return "列表米色";
	}

	@Override
	public String colorTextoListas() {
		return "列表文字颜色";
	}

	@Override
	public String colorBordeListas() {
		return "列表边框颜色";
	}

	@Override
	public String colorBotonFondo() {
		return "按钮背景颜色";
	}

	@Override
	public String colorBordeBoton() {
		return "按钮边框颜色";
	}

	@Override
	public String colorDoradoTexto() {
		return "金色文字颜色";
	}

	@Override
	public String colorPila() {
		return "堆栈跟踪颜色 (stack trace)";
	}

	@Override
	public String colorTextoPanel() {
		return "面板文字颜色";
	}

	@Override
	public String colorTextoNegro() {
		return "黑色文字颜色";
	}

	@Override
	public String colorTextoPrincipal() {
		return "主文字颜色";
	}

	@Override
	public String colorFondoResultados() {
		return "结果背景颜色";
	}

	@Override
	public String colorEstado() {
		return "状态颜色";
	}

	@Override
	public String colorTextoDescripcion() {
		return "描述文字颜色";
	}

	@Override
	public String colorTextoEstado() {
		return "状态文字颜色";
	}

	@Override
	public String colorTextoExtra() {
		return "额外文字颜色";
	}

	@Override
	public String colorSeparador() {
		return "分隔线颜色";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "检测到原生错误 <code>StubRoutines::SafeFetch32</code>。"
				+ "此问题出现在 macOS 上使用 JDK 17.0.9 时，已在 JDK 17.0.10 及更高版本中修复。https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "JDK 17.0.9 (macOS) 中的 SafeFetch32 原生错误";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "请将 JDK 更新至 17.0.10 或更高版本（例如 17.0.15）。";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "如果你使用 MultiMC、Prism Launcher 或 TLauncher 等启动器，请将其配置为使用更新的 JDK。" + "部分启动器已内置 JDK 17.0.15。";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "Spark 模组也可能引发此错误。建议暂时禁用它。https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "MCEF 模组（Chromium Embedded Framework）正在导致静默卡死。</b>" + "<ul>"
				+ "<li>MCEF 在日志末尾初始化，通常意味着游戏在加载过程中卡死。</li>" + "<li>该模组已知会在 Linux、macOS 系统或某些 Java 版本下引发崩溃。</li>"
				+ "<li>不一定出现明确错误，但游戏始终无法进入主菜单。</li>" + "</ul>" + "<p>如果你不需要游戏内的浏览器功能（如网页地图或嵌入式页面），请移除该模组。</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "MCEF（嵌入式浏览器模组）初始化问题";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "从 'mods' 文件夹中删除 MCEF 模组文件（文件名中包含 'mcef'）。";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "如果确实需要此功能，请确保使用与你的操作系统和 Minecraft 版本兼容的 MCEF 版本。";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "检测到 <b>OptiFine</b> 与 <b>Iris/Oculus</b> 之间的冲突。</b>" + "<ul>"
				+ "<li>OptiFine 以与 Iris 或 Oculus 不兼容的方式修改了 Minecraft 的渲染。</li>"
				+ "<li>错误 <code>MixinLevelRenderer failed injection check</code> 来源于 <code>mixins.iris.json</code> 或 <code>mixins.oculus.json</code>。</li>"
				+ "</ul>" + "<p>这些模组不能同时使用。请移除 OptiFine，以便通过 Iris 或 Oculus 使用光影（shaders）。</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "OptiFine 与 Iris/Oculus 冲突";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "从 'mods' 文件夹中删除 OptiFine 文件。";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "使用 Iris 或 Oculus（不搭配 OptiFine）以获得现代光影效果。";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "检测到 <b>ModernFix</b> 与 <b>OptiFine</b> 之间的冲突。</b>" + "<ul>"
				+ "<li>ModernFix 与 OptiFine 不兼容，因为它会破坏 Forge 功能并拖慢启动速度。</li>"
				+ "<li>ModernFix 官方明确警告：<i>\"Use of ModernFix with OptiFine is not supported\"</i>。</li>" + "</ul>"
				+ "<p>你必须删除其中一个模组，才能让游戏正常运行。</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "ModernFix 与 OptiFine 冲突";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "从 'mods' 文件夹中删除 OptiFine 或 ModernFix。二者不能共存。";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "如果需要性能优化，可考虑仅使用 OptiFine，或将 ModernFix 替换为更轻量的模组，如 FerriteCore 或 EntityCulling。";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "错误：注册键包含非法字符。</b>" + "<ul>"
				+ "<li><b>检测到的键：</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>在 Minecraft 中，所有注册键（标签、配方、成就等）必须使用<b>小写字母</b>，且仅可包含字母、数字、下划线、连字符和斜杠。</li>"
				+ "<li>此错误通常由编写不当的模组或有缺陷的 datapack 引起。</li>" + "</ul>"
				+ "<p><b>重要提示：</b>使用侧边栏中的 <b>grepr</b> 或 <b>fgrepr</b> 工具，并启用 <b>“在 JAR 文件中搜索”</b> 选项，以找出包含此无效键的模组。</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "注册键包含大写字母或非法字符";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "使用 'grepr' 或 'fgrepr' 并开启“在 JAR 文件中搜索”以定位问题模组。";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "如果无法确定具体模组，请移除最近安装的模组，尤其是那些添加方块、物品或工具的模组。";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "加载模组 <b>" + escapeHtml(modNombre)
				+ "</b> 时出错。</b>" + "<ul>" + "<li>该模组在初始化某个组件（例如配置菜单）时失败。</li>"
				+ "<li>这通常是因为与 Minecraft、Fabric 或其他模组的版本不兼容。</li>" + "</ul>" + "<p>如果错误持续存在，请删除或更新模组 <b>"
				+ escapeHtml(modNombre) + "</b>。</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "模组初始化错误（Fabric Entrypoint）";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "从 'mods' 文件夹中删除模组 '" + modNombre + "'。";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "将模组 '" + modNombre + "' 更新为与你当前安装兼容的版本。";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "检测到与模组 <b>En Garde!</b> 相关的错误。</b>" + "<ul>"
				+ "<li>此模组添加了近战机制（如格挡、招架等）。</li>"
				+ "<li>该错误通常由与其他战斗模组（如 Epic Fight、DualRiders 等）不兼容，或使用了与你的 Minecraft 不匹配的版本引起。</li>" + "</ul>"
				+ "<p>如果你不使用高级战斗系统，建议移除 En Garde! 以避免冲突。</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "En Garde! 模组错误";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "请确保你使用的 En Garde! 版本与你的 Minecraft 版本及加载器（Fabric/Forge）兼容。";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "如果你使用了其他战斗模组（如 Epic Fight、Caelus 等），请禁用它们，或移除 En Garde! 以避免冲突。";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "检测到由模组 <b>IdleTweaks</b> 引起的错误。</b>" + "<ul>"
				+ "<li>IdleTweaks 尝试释放一个已不存在的网络通道（<code>Tried to release unknown channel</code>）。</li>"
				+ "<li>此错误通常出现在该模组的旧版本中，或在配置不当的服务器上使用时。</li>" + "</ul>"
				+ "<p>IdleTweaks 是一个生活质量模组，但可能导致不稳定。建议更新或移除它。</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "IdleTweaks 错误（未知网络通道）";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "将 IdleTweaks 更新至与你的 Minecraft 兼容的最新版本。";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "如果不需要，从 'mods' 文件夹中删除 IdleTweaks。";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "尝试登录 Minecraft 时检测到身份验证错误 (HTTP 401)。</b>"
				+ "<p>此错误<b>极少直接导致崩溃</b>，但它表明你正在使用未验证的账户（盗版）。</p>" + "<p>官方支持渠道（企业项目、VTuber、整合包作者等）<b>无法为你提供帮助</b>，"
				+ "因为受其聊天规则、合同、与 Mojang/Microsoft 的协议或声誉政策的限制。</p>" + "<p>此检查可在检测器的<b>企业设置中禁用</b>。"
				+ "警告：反盗版检测<b>并非完美</b>，可能在开发环境、网络不稳定或使用修改版启动器时被触发。</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>如果你仍尝试加入支持频道，请注意以下权利告知：</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Minecraft 盗版";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "禁用反盗版验证";
	}

	@Override
	public String comprarMC() {
		return "购买 Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "你正在使用启动器 <code>" + id
				+ "</code>，该启动器<b>不在推荐列表中</b>。</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>尽管它可能可以运行，但非推荐启动器通常会导致：</p>" + "<ul>" + "<li>模组或 App 安装损坏。</li>" + "<li>游戏无法启动或无明确错误地卡死。</li>"
				+ "<li>文件夹结构异常（难以诊断）。</li>" + "<li>Java、内存或模组行为不可预测。</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "为获得更好的体验，请使用以下推荐启动器之一：";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "非推荐启动器";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "切换到推荐列表中的启动器。";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "你正在使用一个<b>不建议使用的启动器</b>：<code>" + id
				+ "</code>。</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>不建议使用的启动器可能导致：</p>" + "<ul>" + "<li>App 或模组安装损坏。</li>" + "<li>游戏无法启动或静默崩溃。</li>"
				+ "<li>文件组织异常（难以调试）。</li>" + "<li>无法确定其如何管理模组、Java 或内存。</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "强烈建议使用以下启动器之一：";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "不建议使用的启动器";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "切换到推荐启动器以获得支持。";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "当前环境缺少推荐的模组。</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "缺少推荐模组";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "安装推荐模组以获得最佳体验。";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "在你的安装中检测到不建议使用的模组。</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "检测到不建议使用的模组";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "移除不建议使用的模组以避免问题。";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "检测到对关键文件的未授权修改。你可能已手动编辑文件，或正在使用不可靠的启动器。</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "检测到篡改";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "重新安装原始文件以恢复完整性。";
	}

	@Override
	public String configuracionCorporativa() {
		return "企业设置";
	}

	@Override
	public String idiomaRespaldo() {
		return "备用语言";
	}

	@Override
	public String buscardorHabilitado() {
		return "启用搜索器";
	}

	@Override
	public String nombreHerramienta() {
		return "工具名称";
	}

	@Override
	public String condenarPirateria() {
		return "谴责盗版";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "推荐启动器";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "不建议的启动器";
	}

	@Override
	public String modsRecomendados() {
		return "推荐模组";
	}

	@Override
	public String modsDesaconsejados() {
		return "不建议的模组";
	}

	@Override
	public String antiTamper() {
		return "防篡改";
	}

	@Override
	public String proximamente() {
		return "即将推出";
	}

	@Override
	public String informacion() {
		return "信息";
	}

	@Override
	public String errorCargandoImagen() {
		return "加载图像时出错";
	}

	@Override
	public String configuracionBasica() {
		return "基本设置";
	}

	@Override
	public String funcionalidades() {
		return "功能";
	}

	@Override
	public String derechosMiranda() {
		return "米兰达权利（强烈推荐）";
	}

	@Override
	public String gestionVerificaciones() {
		return "验证管理";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "名称";
	}

	@Override
	public String codigoVerificacion() {
		return "代码";
	}

	@Override
	public String documentacionVerificacion() {
		return "文档";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "已启用的验证：";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "已禁用的验证：";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "禁用所有非企业级验证";
	}

	@Override
	public String verCodigo() {
		return "查看代码";
	}

	@Override
	public String verDocumentacion() {
		return "查看文档";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "请选择一个验证以禁用。";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "请选择一个验证以启用。";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "已禁用 %d 项不推荐用于企业环境的验证。";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "没有可禁用的非企业级验证。";
	}

	@Override
	public String operacionCompletada() {
		return "操作完成";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "我们想念你，Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "企业验证颜色";
	}

	@Override
	public String nombreLanzador() {
		return "启动器名称";
	}

	@Override
	public String motivo() {
		return "原因";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "不建议的启动器";
	}

	@Override
	public String moverADesaconsejados() {
		return "设为不建议";
	}

	@Override
	public String moverARecomendados() {
		return "设为推荐";
	}

	@Override
	public String guardarCambios() {
		return "保存更改";
	}

	@Override
	public String cancelar() {
		return "取消";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "请选择一个启动器进行移动。";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "更改已成功保存！";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEs(String nombreLanzador) {
		return "Este lanzador no es recomendado debido a problemas de seguridad y estabilidad conocidos.";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEn(String nombreLanzador) {
		return "This launcher is not recommended due to known security and stability issues.";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoPt(String nombreLanzador) {
		return "Este lançador não é recomendado devido a problemas conhecidos de segurança e estabilidade.";
	}

	@Override
	public String razones() {
		return "原因";
	}

	@Override
	public String agregarLanzador() {
		return "添加启动器";
	}

	@Override
	public String quitarLanzador() {
		return "移除启动器";
	}

	@Override
	public String editarRazones() {
		return "编辑原因";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "请选择一个启动器以移除。";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "请选择一个启动器以编辑。";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "编辑 " + idLanzador + " 的原因";
	}

	@Override
	public String agregarNuevoIdioma() {
		return "添加新语言";
	}

	@Override
	public String aceptar() {
		return "确定";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "选择语言";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "这些启动器是 CrashDetector 推荐的优质选择。";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "结果正确";
	}

	public String modsNoRecomendados() {
		return "不建议的模组";
	}

	public String agregarMod() {
		return "添加模组";
	}

	public String quitarMod() {
		return "移除模组";
	}

	public String modId() {
		return "Mod ID / JBoss Modules 名称";
	}

	public String rutaMod() {
		return "模组路径 / 文件";
	}

	public String errorDebeIndicarMod() {
		return "必须至少提供 modid 或模组路径。";
	}

	public String modsNoRecomendadosAviso() {
		return "你可以在此注册不建议的模组，以便 CrashDetector 在它们被安装时进行检测。";
	}

	@Override
	public String anularNormal() {
		return "禁用常规模式";
	}

	@Override
	public String anularNormalDescripcion() {
		return "即使没有崩溃，CrashDetector 也应发出警告。";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "注册 CrashDetector 推荐的模组。如果缺失，CrashDetector 可能会发出警告。";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "如果你决定启用反盗版警告，建议在此处定义请求支持者的权利，作为一种预防措施。\n\n"

				+ "与普遍认知相反，许多热门社区和支持频道并不要求启用反盗版警告才提供帮助。然而，" + "若用户仍尝试加入支持频道，提前记录这些权利可能很有用。\n\n"

				+ "你可以参考官方文件，例如墨西哥《被拘留者基本权利手册》：\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "以及美国、俄罗斯联邦、中华人民共和国、伊朗伊斯兰共和国和朝鲜民主主义人民共和国等国家采用的类似法律原则。\n\n"

				+ "可包含的权利示例包括：\n" + "• 有权不提供与支持无关的信息，例如所用启动器、用户名或 UUID。\n" + "• 有权不自证其罪。\n" + "• 有权拒绝回答与问题解决无关的问题。\n"
				+ "• 有权在聊天中获得引导。\n" + "• 有权使用 CrashDetector 内置的日志匿名化功能。\n\n"

				+ "此文本支持 HTML 内容。";
	}

	@Override
	public String editar() {
		return "编辑";
	}

	@Override
	public String advertenciaHashLento() {
		return "警告：添加大量大文件可能导致验证耗时数分钟。CrashDetector 需要先为每个文件计算哈希值才能继续。建议仅保护严格必需的文件。";
	}

	@Override
	public String agregarArchivo() {
		return "添加文件";
	}

	@Override
	public String agregarCarpeta() {
		return "添加文件夹";
	}

	@Override
	public String quitar() {
		return "移除";
	}

	@Override
	public String rutaArchivo() {
		return "文件路径";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "所选路径位于当前游戏目录之外。仅允许使用当前目录或其子目录中的文件和文件夹。";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>" + "Sylent Bell 的观点和评论不一定代表我们的立场；"
				+ "我们只是觉得放在这里挺有趣的。CrashDetector 是世俗的（非宗教的）。" + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GML 模组（Groovy ModLoader）需要这些更改，且这是该问题最常见的根源。</b>";
	}

	@Override
	public String mensajeIndependenteFlywheel(Set<String> mods) {
		StringBuilder listaMods = new StringBuilder();
		if (!mods.isEmpty()) {
			for (String mod : mods) {
				listaMods.append("<li>").append(mod).append("</li>");
			}
		}

		String mensaje = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到正在使用 <i> Flywheel</i>。</b>" + "<p><b> Flywheel 已过时（deprecated）</b>，不应在现代版本中使用。</p>"
				+ "<p>当前版本的 <b>Create</b> <b>已内置 Flywheel</b>，因此单独安装会导致兼容性冲突和加载错误。</p>"
				+ "<p>某些明确依赖  Flywheel 的模组可能<b>无法运行</b>或<b>运行不稳定</b>。"
				+ "在某些高级情况下，若<b>手动编辑 <code>mods.toml</code> 文件</b>以调整版本范围，这些模组或许能工作，" + "但<b>不建议这样做</b>。</p>"
				+ (mods.isEmpty() ? "" : "<p><b>检测到引用 Flywheel 的模组：</b></p>" + "<ul>" + listaMods.toString() + "</ul>")
				+ "<p>推荐的解决方案是<b>移除  Flywheel</b>，仅使用 Create 内置的版本。</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "独立 Flywheel";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到与模组 <i>Floral Enchantments</i> 相关的错误。</b>" + "<p>崩溃是由该模组在处理游戏数据时的内部故障引起的，"
				+ "导致执行过程中出现 <b>NullPointerException</b>。</p>" + "<p>此问题通常可通过更新或移除该模组解决。</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "Floral Enchantments 错误";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "你同时安装了 MixinExtras 的 NeoForge 版本和普通版本。如果你使用的是 MinecraftForge，可以安装 <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>此链接</a>中的修复模组。</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到使用 Iris 的光影（shaders）时地形阴影出现错误。</b>" + "<p>该问题发生在地形渲染期间。</p>" + "<p>建议<b>尝试关闭光影运行游戏</b>，或降低图形质量，"
				+ "尤其是在<b>Ultra</b>画质设置下。</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "地形阴影（Iris）";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到服务器 tick 过长。</b>"
				+ "<p>这表示游戏在单个 tick 中卡住的时间过长。</p>" + "<p>建议<b>检查日志中生成的 thread dump</b>以确定原因。</p>"
				+ "<p><b>Stack Trace 分析</b>可帮助你定位卡顿源头。</p>" + "<p>此外，<b>在日志中查看</b>按钮会将可能的问题模组标红，"
				+ "以及被 <code>$modid$</code> 包围的条目（通常指示问题来源）。对于实时分析，建议使用 VisualVM 的 CPU 采样器。请确保你的服务器或电脑性能足够运行你所使用的模组——即使所有模组都正常工作，数量过多也可能导致此问题。</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "服务器 Tick 过长";
	}

	@Override
	public String tituloLFPDPPP() {
		return "个人持有个人信息联邦保护法";
	}

	@Override
	public String aceptarPermanentemente() {
		return "永久接受";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "韩语翻译包含来自南方的俚语，为遵守法律必须避免使用。" + "根据《平壤文化语言保护法》，严格禁止使用外语，尤其是来自南方的语言。";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "更多信息，请参阅该法律的官方文件："
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>平壤文化语言保护法</a>";
	}

	public String leerLeyCompleta() {
		return "阅读全文";
	}

	public String errorAbriendoEnlace() {
		return "打开链接时出错";
	}

	public String actaProteccionIdiomaCultural() {
		return "قانون حماية اللغة الثقافية في بيونغ يانغ";
	}

	@Override
	public String canarioTitulo() {
		return "司法命令金丝雀";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — 监控监视器";
	}

	@Override
	public String revisar() {
		return "检查";
	}

	@Override
	public String cerrar() {
		return "关闭";
	}

	@Override
	public String canarioTodoSeguro() {
		return "所有服务均报告安全状态。";
	}

	@Override
	public String canarioComprometido(int c) {
		return "警报：" + c + " 个服务报告不安全状态。";
	}

	@Override
	public String colorAlerta() {
		return "警报颜色";
	}

	public String opcionesMunidiales() {
		return "Munidial 选项";
	}

	public String consentimientoLFPDPPP() {
		return "LFPDPPP 同意";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "在 Handoff 中启用 ReLauncher 的访问令牌传输（不建议）。";
	}

	public String consolaDesarrollo() {
		return "开发控制台";
	}

	public String mundial() {
		return "全球";
	}

	public String ningun() {
		return "无";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "开发者控制台";
	}

	public String bajar() {
		return "下载";
	}

	public String logsSoporte() {
		return "支持日志";
	}

	public String detenerProceso() {
		return "终止进程";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "复制选中内容";
	}

	public String seleccionarTodo() {
		return "全选";
	}

	public String copiarTodo() {
		return "复制全部";
	}

	public String guardarTodoComoArchivo() {
		return "另存为文件";
	}

	public String obtenerEnlaceSoporte() {
		return "获取支持链接";
	}

	public String borrarTodo() {
		return "清空全部";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "控制台背景色";
	}

	public String colorTextoConsola() {
		return "控制台文字颜色";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "已确认同意。\n日志分享功能将在此处实现。";
	}

	@Override
	public String usarSakuraOriginal() {
		return "使用原始 Sakura Riddle 图像";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "“法律传票金丝雀”（warrant canary）是一种透明度机制。\n\n" + "只要此消息存在且所有服务显示为安全，" + "即表示该项目未收到任何秘密司法命令、"
				+ "审查要求或合法监控请求。\n\n" + "如果某个金丝雀消失或标记为不安全，" + "则表明法律状况已发生变化。\n\n" + "本面板会检查系统中所有已注册的金丝雀，并显示"
				+ "其当前状态。\n\n" + "点击“检查”以更新状态。";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "是否将所有选项重置为默认值？";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "无选项";
	}

	@Override
	public String seleccionaColor() {
		return "选择颜色";
	}

	@Override
	public String botonMostrarGUI() {
		return "显示 GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "保存全部";
	}

	@Override
	public String botonRestablecerTodo() {
		return "全部重置";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms 未加载";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到访问 LuckPerms API 时出错。</b>" + "<p>消息表明，当另一个插件尝试使用 LuckPerms 时，<b>LuckPerms 尚未加载</b>。</p>"
				+ "<p><b>可能原因：</b></p>" + "<ul>" + "<li><b>LuckPerms 插件未安装</b>，或 <b>启动失败</b>。</li>"
				+ "<li>另一个插件在 <b>过早</b> 或 <b>不正确</b> 的时机尝试访问 LuckPerms。</li>" + "</ul>"
				+ "<p>建议通过链接 <b>查看控制台日志</b>，以识别 " + "调用 LuckPerms 的插件并验证其兼容性。</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Iris 着色器包未加载";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "未知" : shaderpack;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到使用 Iris/Oculus 加载着色器包时出错。</b>" + "<p><b>受影响的着色器包：</b> " + nombre + "</p>"
				+ "<p>Minecraft 无法打开着色器包文件（FileSystemNotFoundException）。</p>" + "<p><b>可能的解决方案：</b></p>" + "<ul>"
				+ "<li>请确认着色器包已正确安装至 <b>shaderpacks</b> 文件夹中。</li>" + "<li>重新下载该着色器包，因为文件可能已损坏。</li>"
				+ "<li>如果问题仍然存在，请删除 <b>config/iris.properties</b> 文件以重置 Iris 配置。</li>" + "</ul>"
				+ "<p>应用更改后，请重新启动游戏。</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "无法写入配置文件";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "未知" : ruta;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "保存配置文件时发生错误。</b>"
				+ "<p><b>受影响的文件：</b> " + archivo + "</p>" + "<p>Minecraft 无法使用原子写入（REPLACE_ATOMIC）写入该文件。</p>"
				+ "<p><b>通常原因包括：</b></p>" + "<ul>" + "<li>文件夹或文件的权限不正确。</li>" + "<li>文件被标记为只读。</li>"
				+ "<li>其他程序（杀毒软件、备份工具、编辑器）正在锁定该文件。</li>" + "</ul>" + "<p><b>建议操作：</b></p>" + "<ul>"
				+ "<li>确认你对文件夹拥有写入权限。</li>" + "<li>移除文件的只读属性。</li>" + "<li>关闭可能正在使用该文件的程序。</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "创建备份时访问被拒绝";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "未知" : origen;
		String dst = backup == null || backup.isEmpty() ? "未知" : backup;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "创建配置文件备份时发生权限错误。</b>"
				+ "<p><b>原始文件：</b> " + src + "</p>" + "<p><b>备份文件：</b> " + dst + "</p>" + "<p>操作系统在保存文件期间阻止了访问。</p>"
				+ "<p><b>通常原因包括：</b></p>" + "<ul>" + "<li>文件夹权限不足。</li>" + "<li>文件被标记为只读。</li>"
				+ "<li>其他程序（杀毒软件、同步工具、编辑器）正在使用该文件。</li>" + "</ul>" + "<p><b>建议操作：</b></p>" + "<ul>"
				+ "<li>检查 <b>config</b> 文件夹的权限。</li>" + "<li>关闭可能正在访问该文件的程序。</li>"
				+ "<li>尝试以管理员身份启动启动器或 Minecraft。</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		return "启用控制台";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>调试工具</b><br><br>" + "在此处，你可以启用高级功能来调试 CrashDetector 和你的游戏。<br><br>"
				+ "建议启用开发控制台，以便在分析过程中获取详细信息、堆栈跟踪和诊断数据。<br><br>"
				+ "如果你需要在在线模式下测试多人服务器，可能需要从隐私设置中允许将访问令牌（token de acceso）传递给 CrashDetector 进程。"
				+ "在其他情况下，这通常<b>不建议</b>。<br><br>" + "完整说明：<a href='https://example.com'>链接！</a>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "不兼容：Simple Clouds 与着色器（Shaders）";
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到 Simple Clouds 与着色器（shaders）之间存在不兼容问题。</b>"
				+ "<p>当安装了 Distant Horizons 时，Simple Clouds 与光影模组（Iris/Oculus）不兼容。</p>" + "<p><b>推荐选项：</b></p>" + "<ul>"
				+ "<li>若要使用着色器，请移除 <b>Simple Clouds</b>。</li>"
				+ "<li>或者，若希望保留 Simple Clouds，请卸载 <b>Iris 或 Oculus</b>。</li>" + "</ul>"
				+ "<p>此限制源于 Simple Clouds 模组本身，若不修改其代码则无法解决。</p>";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "侧边栏按钮颜色";
	}

	@Override
	public String profilerTitulo() {
		return "性能分析器";
	}

	@Override
	public String profilerDescripcion() {
		return "基于插桩和采样的性能分析工具。";
	}

	@Override
	public String profilerIniciar() {
		return "启动";
	}

	@Override
	public String profilerDetener() {
		return "停止";
	}

	@Override
	public String profilerLimpiar() {
		return "清除";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "性能分析器已启动。";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "性能分析器已停止。";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "已收到来自线程的样本：" + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "定期对调用栈进行采样，以检测性能瓶颈和死锁。";
	}

	@Override
	public String entrarAlJuego() {
		return "进入游戏";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "路径无效：包含非法字符";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到系统路径错误。</b>"
				+ "<p>Minecraft 无法启动，因为文件夹名称中包含非法字符。</p>" + "<p>系统在路径中检测到无效字符（例如：“:”或其他特殊符号）。</p>"
				+ "<p><b>推荐解决方案：</b></p>" + "<ul>" + "<li>重命名实例或配置文件夹。</li>" + "<li>仅使用基本 ASCII 字符（A-Z、a-z、0-9）。</li>"
				+ "<li>不要使用重音符号、特殊符号、问题空格或表情符号。</li>" + "</ul>" + "<p>有效示例：<b>MiInstancia1</b></p>"
				+ "<p>无效示例：<b>Instancia🔥</b> 或 <b>Instancia:Mod</b></p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "崩溃：Twilight Forest + 英特尔驱动程序";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到 Twilight Forest 的着色器在英特尔 GPU 上出现故障。</b>" + "<p>此错误与英特尔显卡驱动程序加载 Twilight Forest 模组的着色器有关。</p>"
				+ "<p>故障发生在驱动内部（igxelpicd64），并非模组或 Minecraft 本身的直接问题。</p>" + "<p><b>推荐解决方案：</b></p>" + "<ul>"
				+ "<li>将英特尔驱动程序更新至最新可用版本。</li>" + "<li>特别尝试版本 31.0.101.8331 或 31.0.101.8247 WHQL，据反馈这些版本不存在此问题。</li>"
				+ "</ul>" + "<p>问题官方跟踪链接：</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>注意：</b>部分较旧的英特尔 GPU 可能不会收到修复此问题的更新。</p>";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "Forge：语言提供程序未能加载";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "未知提供程序" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "Forge 无法加载语言提供程序。</b>"
				+ "<p>初始化 IModLanguageProvider 时发生错误。</p>" + "<p><b>失败的提供程序：</b> " + providerTexto + "</p>"
				+ "<p>此问题通常发生在以下情况：</p>" + "<ul>" + "<li>缺少必需的依赖项（例如 Kotlin for Forge）。</li>"
				+ "<li>模组版本与你的 Forge 版本不兼容。</li>" + "<li>模组文件已损坏。</li>" + "</ul>" + "<p><b>推荐解决方案：</b></p>" + "<ul>"
				+ "<li>重新安装相关模组。</li>" + "<li>确认所有依赖项均已安装。</li>" + "<li>确保使用的版本与当前 Forge 兼容。</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "崩溃：Lets Do Compat（RecipeManager 拦截）";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "在 Lets Do Compat (interceptApply) 中检测到崩溃。</b>" + "<p>错误发生在 Lets Do Compat 对方法 "
				+ "<b>RecipeManager.interceptApply</b> 所做的转换中。</p>" + "<p>这通常表明：</p>" + "<ul>"
				+ "<li>Lets Do Compat 与另一个修改配方的模组不兼容。</li>" + "<li>使用的版本与你的 Minecraft 版本不匹配。</li>"
				+ "<li>转换器之间存在冲突（mixin/coremod）。</li>" + "</ul>" + "<p><b>推荐解决方案：</b></p>" + "<ul>"
				+ "<li>尝试临时移除 Lets Do Compat 以确认冲突。</li>" + "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI：物品组崩溃（插件不兼容）";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "未知插件" : String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "JEI 在构建物品组时检测到故障。</b>"
				+ "<p>一个或多个插件在 JEI 生成物品列表时引发了错误。</p>" + "<p><b>受影响的物品组/插件：</b> " + listaPlugins + "</p>"
				+ "<p>此问题通常出现在以下情况：</p>" + "<ul>" + "<li>JEI 插件实现有误或已过时。</li>" + "<li>与当前 JEI 版本不兼容。</li>"
				+ "<li>使用了 Fabric API，但某个模组错误地注册了其 Item Group。</li>" + "</ul>" + "<p><b>推荐解决方案：</b></p>" + "<ul>"
				+ "<li>更新 JEI 及所列模组。</li>" + "<li>临时移除受影响的插件以确认冲突。</li>" + "<li>向相关模组开发者报告此错误。</li>" + "</ul>"
				+ "<p>在问题修复前，这些物品组中的物品将不会出现在物品列表中。</p>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "模组版本无效（SemVer）";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "未知" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "无法定位模组" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到无效的模组版本。</b>"
				+ "<p>版本 <b>" + v + "</b> 不符合有效的 SemVer 格式。</p>" + "<p>错误表明预发布部分为空（以 '+' 结尾）。</p>"
				+ "<p><b>问题模组：</b><br>" + u + "</p>" + "<p><b>推荐解决方案：</b></p>" + "<ul>" + "<li>编辑模组文件并修正版本号。</li>"
				+ "<li>如果后面没有元数据，请删除末尾的 '+'。</li>" + "<li>将模组更新至已修复的版本。</li>" + "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS：模块间非法访问";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到模块间非法访问（JPMS）。</b>"
				+ "<p>Java 模块系统（JPMS）阻止了类之间的访问。</p>" + "<p><b>尝试访问的类：</b><br>" + claseOrigen + "（模块：" + moduloOrigen
				+ "）</p>" + "<p><b>被阻止的类：</b><br>" + claseDestino + "（模块：" + moduloDestino + "）</p>"
				+ "<p>此类错误通常发生在模组未在其 module-info.java 中正确声明 " + "exports 或 opens 时。</p>" + "<p><b>可能原因：</b></p>"
				+ "<ul>" + "<li>模块未导出所需包。</li>" + "<li>缺少用于反射的 <b>opens</b> 指令。</li>" + "<li>模组未针对 JPMS 正确配置。</li>"
				+ "</ul>" + "<p>此问题需由模组开发者修复。</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin：类被错误放置在 mixin 包中";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "类被错误地放置在 Mixin 包中。</b>"
				+ "<p>一个普通类被放入了声明为 mixin 的包中。</p>" + "<p><b>冲突类：</b><br>" + clase + "</p>"
				+ "<p><b>声明的 mixin 包：</b><br>" + paquete + "</p>" + "<p><b>相关的 mixins 配置文件：</b><br>" + archivoMixin
				+ "</p>" + "<p>普通类不应位于 mixins.json 中定义的包内。</p>" + "<p>只有标注为 mixin 的类才应存在于该包中。</p>"
				+ "<p><b>开发者解决方案：</b>将普通类移出 mixin 包，" + "或修正 mixins.json 文件的配置。</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到 Matrox GPU 驱动程序问题。</b>"
				+ "<p>日志表明，故障发生在 Matrox 驱动程序的某个库中。</p>" + "<p>Matrox 显卡（尤其是服务器中使用的 G200/G400 型号）"
				+ "并非为现代 3D 渲染而设计，可能不支持 Minecraft 所需的 OpenGL 版本。</p>" + "<p><b>推荐解决方案：</b></p>" + "<ul>"
				+ "<li>将 Matrox 驱动程序更新至最新可用版本。</li>" + "<li>安装官方驱动程序，而非系统自带的通用驱动。</li>"
				+ "<li>如果硬件较旧，请使用支持 OpenGL 3.2 或更高版本的 GPU。</li>" + "</ul>" + "<p>在服务器上，此类 GPU 通常仅用于基本视频输出，"
				+ "并不适用于 Minecraft 等 3D 应用程序。</p>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod：GPU 不兼容";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod 未能检测到兼容的 GPU。</b>" + "<p>模组 <b>VulkanMod</b> 尝试使用 Vulkan 启动，但未找到能正确支持 Vulkan 的 GPU。</p>"
				+ "<p>这通常发生在以下情况：</p>" + "<ul>" + "<li>GPU 不支持 Vulkan。</li>" + "<li>GPU 驱动程序已过时或缺失。</li>"
				+ "<li>使用了错误的图形适配器（例如，使用了集成显卡而非独立显卡）。</li>" + "</ul>" + "<p><b>推荐解决方案：</b></p>" + "<ul>"
				+ "<li>将 GPU 驱动程序更新至最新版本。</li>" + "<li>确认你的 GPU 支持 Vulkan。</li>"
				+ "<li>如果你有两块 GPU，请强制 Minecraft 使用独立显卡。</li>" + "<li>如果你的 GPU 不支持 Vulkan，请卸载 VulkanMod。</li>" + "</ul>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "轮廓渲染使用了无效的 RenderType";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		String base = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "某个模组尝试对不兼容的 RenderType 应用轮廓（outline）。</b>" + "<p>错误信息为：</p>"
				+ "<code>Can't render an outline for this rendertype!</code>";

		if (enchantDetectado) {
			base += "<p><b>报告中检测到 enchant-outline / better-enchants 模组。</b></p>"
					+ "<p>该模组在较新版本的 Minecraft 中已知会引发此问题。</p>" + "<p><b>推荐解决方案：</b>移除或更新 enchant-outline。</p>";
		} else {
			base += "<p>此问题通常与修改渲染的模组有关 "
					+ "（如 Entity Model Features、Entity Texture Features、Visuality，或与 Sodium 的冲突）。</p>"
					+ "<p><b>推荐解决方案：</b>逐一更新或禁用渲染类模组以排查问题。</p>";
		}

		return base;
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – DimensionalInventory 为空";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG 尝试保存一个空的 DimensionalInventory。</b>" + "<p>游戏抛出以下错误：</p>"
				+ "<code>Cannot invoke DimensionalInventory.saveInventory(...) because \"d\" is null</code>"
				+ "<p>这是 DivineRPG 中一个已知的与 Vethean 库存系统相关的 bug。</p>" + "<p><b>推荐解决方案：</b></p>" + "<ul>"
				+ "<li>打开 DivineRPG 的配置文件。</li>" + "<li>设置 <code>saferVetheanInventory=true</code></li>"
				+ "<li>保存并重启游戏。</li>" + "</ul>" + "<p>如果存在更新版本，也建议更新 DivineRPG。</p>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "Render Pass 冲突";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到渲染系统冲突。</b>"
				+ "<p>游戏抛出以下错误：</p>"
				+ "<code>Close the existing render pass before performing additional commands</code>"
				+ "<p>此错误通常与 Iris、OptiFine、VulkanMod 等修改图形渲染管线的模组之间存在冲突有关。</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "Feather Client 内部故障";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到 Feather Client 内部故障。</b>" + "<p>游戏抛出以下错误：</p>"
				+ "<code>NoClassDefFoundError: feather/lib/sentry/Sentry</code>" + "<p>此错误由 Feather Client 引起。</p>"
				+ "<p>不推荐使用 Feather，原因如下：</p>" + "<ul>" + "<li>它使用流行模组的私有修改版本。</li>" + "<li>破坏与标准 Fabric 的兼容性。</li>"
				+ "<li>本质上是一个预配置的模组包，包含内部修改。</li>" + "<li>常与 Sodium 及其他性能模组发生冲突。</li>" + "</ul>"
				+ "<p>建议使用标准 Fabric 安装，而非 Feather。</p>";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "Iris + Flywheel 冲突（Create 6）";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "在 Create 6 中检测到 Iris 与 Flywheel 之间的冲突。</b>" + "<p>游戏抛出以下错误：</p>"
				+ "<code>NoSuchFieldError: TESSELATION_SHADERS</code>" + "<p>检测到内部引用 <code>$irisflw$</code>，"
				+ "表明 Iris 与 Flywheel 存在冲突。</p>" + "<p>Create 6 的 Iris Flywheel 2.0 仅官方支持 NeoForge。</p>"
				+ "<p>如果你使用的是 Forge 或 Fabric，此组合可能引发该错误。</p>";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "未找到 GeckoLib 模型";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "某个模组未能找到 GeckoLib 模型。</b>"
				+ "<p>受影响的模型：</p>" + "<code>" + modelo + "</code>" + "<p>此错误发生在 <code>.geo.json</code> 文件不存在 "
				+ "或在模组内配置错误时。</p>" + "<p>可能原因：</p>" + "<ul>" + "<li>模型已被删除，但仍被引用。</li>" + "<li>文件路径错误。</li>"
				+ "<li>JAR 内缺少该文件。</li>" + "<li>模组版本不兼容。</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – 动画不存在";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon 尝试播放一个不存在的动画。</b>" + "<p>动画：</p>" + "<code>" + animacion + "</code>" + "<p>组：</p>"
				+ "<code>" + grupo + "</code>" + "<p>此错误通常发生在以下情况：</p>" + "<ul>" + "<li>混用了不兼容的 Cobblemon 版本。</li>"
				+ "<li>附加模组（addon）与已安装版本不匹配。</li>" + "<li>缺少内部资源或动画文件。</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "Lunar Client 内部故障";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到 Lunar Client 内部故障。</b>"
				+ "<p>此错误源自 Lunar Client 本身，而非原版游戏或模组。</p>" + "<p>Lunar Client 使用了内部定制的修改，可能会与某些模组或特定配置产生不兼容。</p>"
				+ "<p>建议使用标准版 Minecraft 进行测试，以排除客户端自身的问题。</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "非法访问方法或字段";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "某个模组尝试访问受保护/私有方法或字段。</b>"
				+ "<p>责任类：</p>" + "<code>" + clase + "</code>" + "<p>被访问的成员：</p>" + "<code>" + miembro + "</code>"
				+ "<p>此错误通常发生在以下情况：</p>" + "<ul>" + "<li>该模组是为其他版本的 Minecraft 编译的。</li>" + "<li>混用了不兼容的 mappings。</li>"
				+ "<li>模组已过时。</li>" + "<li>使用了错误的加载器（Fabric/Forge/NeoForge）。</li>" + "</ul>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "加载 datapack/resourcepack 时出错";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "某个数据包（datapack）或资源包（resourcepack）加载失败。</b>" + "<p>问题文件：</p>" + "<code>" + archivo + "</code>"
				+ "<p>包：</p>" + "<code>" + pack + "</code>" + "<p>游戏无法解析此文件，导致注册表（registry）加载错误。</p>"
				+ "<p>此问题通常由以下原因引起：</p>" + "<ul>" + "<li>JSON 格式不正确。</li>" + "<li>包的版本不兼容。</li>"
				+ "<li>包未适配当前游戏版本。</li>" + "<li>多个数据包之间存在冲突。</li>" + "</ul>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "着色器编译错误";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "着色器编译失败。</b>"
				+ "<p>游戏无法编译当前启用的某个着色器。</p>" + "<p>此问题通常与 Sodium、Iris 或不兼容的着色器包有关。</p>" + "<p>建议操作：</p>" + "<ul>"
				+ "<li>尝试使用其他着色器。</li>" + "<li>暂时禁用着色器。</li>" + "<li>更新 GPU 驱动程序。</li>"
				+ "<li>如果问题持续，尝试在不使用 Sodium 的情况下启动游戏。</li>" + "</ul>";
	}

	public String nombreErrorCreacionModelo() {
		return "创建或加载模型时出错";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>尝试创建或加载 Minecraft 模型时发生错误。</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>受影响的模型：<code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>此类错误通常发生在以下情况：</p>");
		sb.append("<ul>");
		sb.append("<li>某个模组的模型配置错误。</li>");
		sb.append("<li>JSON 模型文件已损坏或不完整。</li>");
		sb.append("<li>多个修改模型或渲染的模组之间存在冲突。</li>");
		sb.append("<li>资源包或数据包包含不兼容的模型。</li>");
		sb.append("</ul>");
		sb.append("<p>请尝试确定是哪个模组或资源包提供了所提示的模型。</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>检测到可能原因：</b></p>");
		sb.append("<p>日志中检测到 <b>Cooler Animations</b> 模组的活动。</p>");
		sb.append("<p>该模组会修改动画和模型系统，在某些情况下可能导致模型加载错误。</p>");
		sb.append("<p>如果问题持续，请尝试在不使用 Cooler Animations 的情况下启动游戏，以确认错误是否消失。</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "Starlight 相关问题";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到与 Starlight 相关的错误。</b>"
				+ "<p>错误发生在 <code>BlockStarLightEngine.initNibble</code> 内部。</p>"
				+ "<p>这表明模组 <b>Starlight</b> 的光照引擎出现故障。</p>"
				+ "<p>Starlight 是一个完全修改 Minecraft 光照系统的模组，在某些模组环境中已知会引发各种问题。</p>" + "<p>这只是与 Starlight 相关的多个已知错误之一。</p>"
				+ "<p>如果问题持续，请尝试在不使用 Starlight 的情况下启动游戏。</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "AAAParticles / Effekseer 相关问题";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到与 Effekseer 相关的原生崩溃。</b>" + "<p>错误发生在原生库 <code>EffekseerNativeForJava</code> 内部。</p>"
				+ "<p>该库由 ChloePrime 开发的模组 <b>AAAParticles</b> 使用。</p>" + "<p>原生库中的崩溃通常表明模组本身或其原生依赖项存在问题。</p>"
				+ "<p>如果问题持续，请尝试在不使用 AAAParticles 的情况下启动游戏。</p>";
	}

	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到 Java 虚拟机（JVM）原生崩溃。</b>"
				+ "<p>此类错误发生在 Java 虚拟机内部（例如 <code>jvm.dll</code>、<code>libjvm.so</code> 等），" + "并不一定由模组引起。</p>"
				+ "<p>虽然极少数情况可能源于使用了不兼容原生库的模组，" + "<b>但更可能是因为你使用的 JVM 版本存在缺陷、损坏或已过时</b>。</p>"
				+ "<p>如果你使用的是非官方或较旧的 Java 构建（例如无支持的社区版本），这种情况尤为常见。</p>" + "<p><b>我们推荐使用可靠且维护良好的 JVM：</b></p>" + "<ul>"
				+ "<li><b>Red Hat Build of OpenJDK</b>（稳定、经过充分测试，适用于 Windows/Linux）</li>"
				+ "<li><b>OpenLogic OpenJDK</b>（跨平台支持，包括 macOS Intel）</li>"
				+ "<li><b>Azul Zulu</b>（经过认证，提供免费 LTS 支持）</li>" + "<li><b>Oracle JDK</b>（官方版本，定期更新）</li>" + "</ul>"
				+ "<p>请避免使用未知、自定义或非常老旧的构建，它们可能包含 JVM 引擎中的严重错误。</p>"
				+ "<p><b>你使用的是 Prism Launcher 或 TLauncher 吗？</b> 配置自定义 JVM 非常简单："
				+ "在 Prism Launcher 中，进入 <i>安装</i> → <i>编辑实例</i> → <i>Java 设置</i>；"
				+ "在 TLauncher 中，进入 <i>Settings</i> → <i>Java Settings</i> 并指定你下载的 JDK/JRE 路径。" + "无需更改系统 JVM！</p>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "Paranoia 与 C2ME 冲突";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到 Paranoia 与 C2ME 模组之间的冲突。</b>" + "<p>错误表明 <code>ThreadLocalRandom</code> 被从错误的线程访问。</p>"
				+ "<p>这通常发生在 <b>Paranoia</b> 执行非线程安全代码的同时，<b>C2ME</b> 正在进行多线程优化。</p>"
				+ "<p>此类冲突在使用 MCreator 创建的模组中很常见。</p>" + "<p>如果问题持续，请尝试在不启用 Paranoia 的情况下启动游戏，或禁用 C2ME。</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "Minecraft 资源目录缺失";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft 无法找到资源（assets）目录。</b>" + "<p>启动器尝试使用无效的资源路径启动游戏。</p>"
				+ "<p>这意味着游戏的资源文件（assets）不存在或未正确安装。</p>" + "<p>此问题通常由 Minecraft 安装不完整或启动器配置错误引起。</p>"
				+ "<p>使用非官方启动器（如 FreshCraft）时也可能发生，因其对 assets 处理不当。</p>" + "<p>如果问题持续，请尝试重新安装整合包，或使用官方/可靠的启动器启动游戏。</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return "安装 " + dependencia + " 版本 " + version + " 或更高";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return "将 " + dependencia + " 替换为 " + min + " 到 " + max + " 之间的版本";
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "模组 " + mod + " 需要 " + dependencia + " 最低版本 " + version;
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "模组 " + mod + " 需要 " + dependencia + " 版本 " + version;
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {

		return "模组 " + mod + " 需要 " + dependencia + " 版本在 " + min + " 到 " + max + " 之间" + " (当前：" + actual + ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "Cupboard 版本不兼容";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到由旧版 Cupboard 引起的崩溃。</b>"
				+ "<p>错误发生在 <code>ClientConfigCompat.setupNeoforge</code> 内部，原因是 "
				+ "<code>ModList.get()</code> 返回了 <code>null</code>。</p>" + "<p>这是旧版 <b>Cupboard</b> 模组的一个已知问题。</p>"
				+ "<p>像 <b>3.2</b> 这样的旧版本包含此缺陷。</p>" + "<p><b>解决方案：</b>请将 Cupboard 更新至 <b>3.5</b> 或更高版本。</p>";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		return "这是因为您正在使用 macOS，而游戏试图使用 OpenGL，这在最新版本的 macOS 上已不再支持。"
				+ "您需要使用支持 Metal 的 Minecraft 版本，或者如果您使用的是 Intel、M1 或 M2 芯片的 Mac（但不包括 M3+ 或 Neo），请改用 Linux。";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "未找到 GeckoLib 动画";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "某个模组无法加载 GeckoLib 动画文件。</b>" + "<p>受影响的文件：</p>" + "<code>" + archivo + "</code>"
				+ "<p>当动画 <code>.json</code> 文件不存在、" + "包含语法错误或路径不正确时，会发生此错误。</p>" + "<p>可能的原因：</p>" + "<ul>"
				+ "<li>文件已被删除，但代码中仍在引用它。</li>" + "<li>JSON 文件内部存在语法错误。</li>" + "<li>模组注册中定义的路径不正确。</li>"
				+ "<li>依赖冲突或版本不兼容。</li>" + "</ul>";
	}

	@Override
	public String nombreAnimacionGeckoInexiste() {
		return "未找到 GeckoLib 动画";
	}

	@Override
	public String mensajeAnimacionGeckoInexiste(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "某个模组无法找到 GeckoLib 动画文件。</b>" + "<p>受影响文件:</p>" + "<code>" + archivo + "</code>"
				+ "<p>当 GeckoLib 尝试加载指定路径中不存在的动画时，会发生此错误。 " + "与加载错误（语法）不同，此错误表明文件物理缺失或路径错误。</p>" + "<p>可能的原因:</p>"
				+ "<ul>" + "<li><code>.json</code> 文件已被删除或未包含在模组的最终 JAR 文件中。</li>"
				+ "<li>代码中定义的路径存在拼写错误（例如：'animations' 与 'animaciones'）。</li>"
				+ "<li>大小写不一致（服务器操作系统为 Linux（区分大小写），而开发环境为 Windows（不区分））。</li>" + "<li>模组未完全更新或其依赖项已损坏。</li>" + "</ul>";
	}

	@Override
	public String nombreFalloFabricRenderingAPI() {
		return "缺少 Fabric Rendering API";
	}

	@Override
	public String mensajeFalloFabricRenderingAPI() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// 主要消息
		String mensajeBase = "<span style='color:#" + color + "'>某个模组（通常是 Porting Lib 或其依赖项）失败，因为 </span>"
				+ "Fabric Rendering API<span style='color:#" + color + "'> 不可用。</span><br><br>";

		// 修复说明（针对现代版本更新，Indium 已过时）
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>推荐解决方案：</b><br>"
				+ "消息建议安装 Indium，但该模组在现代游戏版本中已过时。<br>" + "<ul>"
				+ "<li>将 <b>Sodium</b> 更新至 <b>0.6.0</b> 或更高版本（此版本包含所需支持）。</li>"
				+ "<li>如果尚未安装，请确保已安装 <b>Fabric API</b>。</li>" + "<li>如果您使用的是旧版游戏（1.20.6 或更低版本），则安装 Indium。</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRestriccionesDependenciaNoCumplidas() {
		return "依赖项限制未满足";
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// 主要消息
		String mensajeBase = "<span style='color:#" + color + "'>发现 </span>" + cantidad + "<span style='color:#" + color
				+ "'> 个未满足的依赖项限制。</span><br><br>";

		// 构建冲突列表
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictos != null && !conflictos.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color).append("'>在以下文件中检测到冲突:</span><br><ul>");
			for (String[] par : conflictos) {
				String dep = par[0]; // 依赖项
				String jar = par[1]; // JAR 文件
				// 变量使用默认颜色，固定文本使用错误颜色
				listaDetalle.append("<li>").append("<span style='color:#").append(color).append("'>文件: </span>")
						.append(jar).append("<br><span style='color:#").append(color).append("'>需要: </span>")
						.append(dep).append("</li>");
			}
			listaDetalle.append("</ul><br>");
		}

		// 修复说明
		String instrucciones = "<span style='color:#" + color + "'>" + "当两个或更多模组需要同一内部库的不同且不兼容的版本时，会发生这种情况。<br><br>"
				+ "<b>推荐解决方案：</b><br>" + "<ul>" + "<li>尝试更新或删除上面列出的模组以解决不兼容问题。</li>"
				+ "<li>如果找不到兼容版本，您可以尝试手动编辑模组 JAR 文件内的 <code>mods.toml</code> 文件（使用 WinRAR 或 7-Zip 等压缩软件）来更改或删除版本限制，但这可能会导致不稳定。</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// 主要消息
		String mensajeBase = "<span style='color:#" + color + "'>发现 </span>" + cantidad + "<span style='color:#" + color
				+ "'> 个未满足的依赖项限制。</span><br><br>";

		// 按模组构建分组列表
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictosPorMod != null && !conflictosPorMod.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color).append("'>涉及的模组及请求的依赖项:</span><br><ul>");

			for (Map.Entry<String, List<String>> entry : conflictosPorMod.entrySet()) {
				String archivo = entry.getKey();
				List<String> dependencias = entry.getValue();

				// 模组名称（默认颜色）
				listaDetalle.append("<li><b>").append(archivo).append("</b>");

				// 该模组的依赖项列表
				listaDetalle.append("<ul>");
				for (String dep : dependencias) {
					// 依赖项（默认颜色）
					listaDetalle.append("<li>").append(dep).append("</li>");
				}
				listaDetalle.append("</ul></li>");
			}
			listaDetalle.append("</ul><br>");
		} else {
			listaDetalle.append("<span style='color:#").append(color).append("'>无法从日志中确定具体文件。</span><br>");
		}

		// 修复说明
		String instrucciones = "<span style='color:#" + color + "'>" + "当模组包含彼此不兼容的内部库版本（JarInJar）时，会发生此错误。<br><br>"
				+ "<b>推荐解决方案：</b><br>" + "<ul>" + "<li>查看上方列表以识别哪些模组请求同一库的不同版本。</li>" + "<li>尝试将两个模组都更新到最新版本。</li>"
				+ "<li>作为最后的手段，您可以使用压缩软件（如 WinRAR）打开模组的 <code>.jar</code> 文件，编辑 <code>META-INF/mods.toml</code> 并手动修改依赖项的版本范围，但这有风险且可能破坏模组。</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String nombreNeruinaOcultaAdvertencia() {
		return "Neruina 阻碍调试";
	}

	@Override
	public String mensajeNeruinaOcultaAdvertencia() {
		String color = Config.obtenerInstancia().obtenerColorAdvertencia();

		// 主要警告
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>警告：</b>模组 <b>Neruina</b> 在处理错误时失败，从而隐藏了崩溃的真正原因。</span><br><br>" + "<span style='color:#" + color
				+ "'>" + "Neruina 通常并非必需，且会阻碍确定实际故障所在。建议将其移除以便调试。</span><br><br>";

		// 恢复说明
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>恢复说明:</b><br>"
				+ "1. **MCForge**: 前往 '[nombre_del_mundo]/serverconfig/forge-server.toml'。<br>"
				+ "2. **NeoForge**: 前往 'config/neoforge-server.toml'。<br>"
				+ "   *（注意：在本地游戏/Singleplayer 中，世界文件位于 'saves' 文件夹内）*。<br>"
				+ "3. 将 **removeErroringBlockEntities** 和 **removeErroringEntities** 设置为 **true**。<br><br>"
				+ "<b>其他选项:</b><br>" + "- **MCEdit**: 用于手动删除指定坐标处的实体。<br>" + "- 如果此错误持续存在，Neruina 可能未正常工作，而只是在生成新的错误。"
				+ "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreApothicAttributeSinDueño() {
		return "Apothic Attributes 错误";
	}

	@Override
	public String mensajeApothicAttributeSinDueño(boolean chestCavityDetectado) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// 主要消息
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Apothic Attributes</b> 检测到冲突：<b>AttributeMap</b> 在未分配所有者的情况下被修改。</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "这通常发生在模组尝试在不适当的时间或以不正确的方式修改实体属性（如生命值、伤害、速度）时。</span><br><br>";

		// 关于 Chest Cavity 的特定说明
		String notaChestCavity = "";
		if (chestCavityDetectado) {
			notaChestCavity = "<span style='color:#" + color + "'>" + "<b>日志中检测到模组 Chest Cavity。</b> "
					+ "由于其处理实体属性的方式，该模组是导致此特定错误的常见原因。</span><br><br>";
		}

		// 修复说明
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>推荐解决方案：</b><br>" + "<ul>"
				+ "<li>如果已安装 Chest Cavity，请尝试更新或临时移除它以验证是否为原因所在。</li>" + "<li>检查是否有其他修改生物属性的模组，并尝试禁用它们。</li>"
				+ "<li>查找 <b>Apothic Attributes</b> 的更新，因为此错误可能已在近期版本中修复。</li>" + "</ul></span>";

		return mensajeBase + notaChestCavity + instrucciones;
	}

	@Override
	public String nombreErrorPotBlockEntity() {
		return "DecoratedPot 错误 (Cataclysm)";
	}

	@Override
	public String mensajeErrorPotBlockEntity() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// 主要消息
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "与 <b>DecoratedPotBlockEntity</b> 发生了兼容性错误。</span><br><br>" + "<span style='color:#" + color + "'>"
				+ "这是模组 <b>L_Enders_Cataclysm</b> 在 1.19.2 版本中的已知问题，" + "缺少游戏所需的实现。</span><br><br>";

		// 解决方案
		String solucion = "<span style='color:#" + color + "'>" + "<b>推荐解决方案：</b><br>"
				+ "安装模组 <b>PotFix (Cataclysm Patch)</b> 以修复此错误。<br>"
				+ "您可以在此下载：<a href='https://www.curseforge.com/minecraft/mc-mods/potfix-cataclysm-patch'>CurseForge - PotFix</a>"
				+ "</span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorPreloadingTricks() {
		return "Preloading Tricks 错误";
	}

	@Override
	public String mensajeErrorPreloadingTricks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// 主要消息
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "检测到由 <b>Preloading Tricks</b> 引起的冲突。</span><br><br>" + "<span style='color:#" + color + "'>"
				+ "错误 <i>ClassCastException: String cannot be cast to ModuleDescriptor</i> "
				+ "表明该模组正在以不正确的方式操作 Java 模块系统的类。</span><br><br>";

		// 解释和解决方案
		String explicacion = "<span style='color:#" + color + "'>"
				+ "<b>Preloading Tricks</b> 是一个主要为 <b>开发者</b> 设计的模组。 " + "它在游戏加载的极早期阶段执行复杂的类修改操作（mixins）， "
				+ "如果存在其他交互，很容易破坏稳定性。</span><br><br>" + "<span style='color:#" + color + "'><b>推荐解决方案：</b><br>" + "<ul>"
				+ "<li>移除模组 <b>Preloading Tricks</b>。通常在公共服务器或稳定整合包中游玩时不需要它。</li>"
				+ "<li>如果您是开发者且需要它进行测试，请检查您的环境配置。</li>" + "</ul></span>";

		return mensajeBase + explicacion;
	}

	@Override
	public String nombreErrorSimpleRadioLexiconfig() {
		return "Simple Radio / Lexiconfig 不兼容";
	}

	@Override
	public String mensajeErrorSimpleRadioLexiconfig() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// 主要消息
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "检测到 <b>Simple Radio</b> 与 <b>Lexiconfig</b> 之间存在冲突。</span><br><br>" + "<span style='color:#" + color
				+ "'>" + "错误发生在 'shelveLexicons' 过程中，表明这两个库之间存在二进制不兼容性。</span><br><br>";

		// 特定解决方案
		String solucion = "<span style='color:#" + color + "'>" + "<b>已知原因：</b><br>"
				+ "Simple Radio 通常是为旧版 Lexiconfig 设计的，而您安装的是较新版本。</span><br><br>" + "<span style='color:#" + color
				+ "'><b>推荐解决方案：</b><br>" + "<ul>" + "<li>尝试使用较旧版本的 <b>Lexiconfig</b>。</li>"
				+ "<li>建议尝试版本 <b>1.3.11</b> 或更早版本，这些版本通常与 Simple Radio 兼容。</li>"
				+ "<li>如果问题仍然存在，请检查是否有 Simple Radio 的可用更新。</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorMobAITweaks() {
		return "Mob AI Tweaks 错误";
	}

	@Override
	public String mensajeErrorMobAITweaks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// 主要消息
		String mensajeBase = "<span style='color:#" + color + "'>" + "检测到与 <b>Mob AI Tweaks</b> 相关的错误。</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "该错误源自一个 Mixin（<code>$mob-ai-tweaks$onSpawned</code>），它在实体生成（spawnea）时介入。 "
				+ "这通常表明与另一个同样修改生物生成行为的模组存在冲突。</span><br><br>";

		// 解决方案
		String solucion = "<span style='color:#" + color + "'><b>推荐解决方案：</b><br>" + "<ul>"
				+ "<li>尝试移除 <b>Mob AI Tweaks</b> 以验证不稳定问题是否消失。</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	public String nombre_verificacion_gpu() {
		return "GPU 验证（OpenGL / GPU 选择）";
	}

	public String desactivar_parche_gpu() {
		return "禁用 GPU 验证";
	}

	// ==================== CRASH ====================

	public String gpu_crash_posible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>GPU 验证器可能已导致游戏关闭。</b>";
	}

	public String gpu_crash_causas() {
		return "验证已开始但未完成。这通常表明 OpenGL 或显卡驱动程序出现故障。<br><br>" + "可能的原因:<br>" + "- 驱动程序过时或不稳定<br>" + "- OpenGL 问题<br>"
				+ "- 旧款 GPU 或混合配置";
	}

	public String gpu_crash_recomendaciones() {
		return "建议:<br>" + "- 更新 GPU 驱动程序<br>" + "- 强制使用独立显卡<br>" + "- 避免远程或虚拟化环境";
	}

	// ==================== NO ÓPTIMA ====================

	public String gpu_no_optima() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>游戏未使用可用的最佳 GPU。</b>";
	}

	public String gpu_no_optima_detalles() {
		return "这可能会降低性能（低帧率），但通常本身不会导致崩溃。";
	}

	public String gpu_recomendaciones_rendimiento() {
		return "建议:<br>" + "- 在控制面板中强制使用独立显卡<br>" + "- 将 Java/Minecraft 配置为高性能模式";
	}

	// ==================== GENERALES ====================

	public String gpu_nota_precision() {
		return "<b>注意：</b>此检测系统并非 100% 精确。";
	}

	public String gpu_consumo_energia() {
		return "更强大的 GPU 消耗更多电量，可能会缩短笔记本电脑的电池续航时间。";
	}

	public String gpu_parche_info() {
		return "您可以使用快速解决按钮禁用此验证。";
	}

	@Override
	public String nombreVerificacionRaptorLake() {
		return "Intel 第 13/14 代 CPU 稳定性警告";
	}

	@Override
	public String advertenciaRaptorLakeTitulo() {
		return "Intel Raptor Lake 处理器可能存在不稳定性";
	}

	@Override
	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String targetMicrocode) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "检测到处理器 " + cpu + "，微码版本为 " + microcode
				+ "。" + "</b> " + "Intel 第 13 代和第 14 代处理器因请求电压过高而出现稳定性问题，" + "这可能会缩短处理器的使用寿命。<br><br>"
				+ "建议将主板的微码或 BIOS 更新至包含微码 <b>" + targetMicrocode + "</b> 或更高版本的版本。"
				+ "<b>警告：</b>如果操作不当，更新 BIOS 存在风险。<br><br>" + "<i>注意：这几乎肯定不是您当前崩溃的原因，这仅是关于硬件健康的资讯性提醒。</i>";
	}

	@Override
	public String desactivarVerificacionRaptorLake() {
		return "不再就此向我发出警告";
	}

	@Override
	public String verArticuloRaptorLake(String fuente) {
		return "在 " + fuente + " 上阅读文章";
	}

	@Override
	public String tituloMixins() {
		return "Mixins 浏览器";
	}

	@Override
	public String mixinsBotonLateral() {
		return "Mixins";
	}

	@Override
	public String mixinsRaiz() {
		return "已找到的 Mixins";
	}

	@Override
	public String mixinsTodosLosMods() {
		return "全部";
	}

	@Override
	public String mixinsModConMixin() {
		return "包含 mixins 的模组";
	}

	@Override
	public String mixinsTooltipCombo() {
		return "按包含 mixins 的模组筛选";
	}

	@Override
	public String mixinsRecargar() {
		return "重新加载";
	}

	@Override
	public String mixinsDescompilarSeleccion() {
		return "反编译所选内容";
	}

	@Override
	public String mixinsTargets() {
		return "Targets";
	}

	@Override
	public String mixinsTarget() {
		return "Target";
	}

	@Override
	public String mixinsTargetsMetodo() {
		return "方法的 Targets";
	}

	@Override
	public String mixinsMetodos() {
		return "方法";
	}

	@Override
	public String mixinsCampos() {
		return "字段";
	}

	@Override
	public String mixinsCantidad() {
		return "Mixins 数量";
	}

	@Override
	public String mixinsDetalleMixin() {
		return "Mixin 详情";
	}

	@Override
	public String mixinsDetalleTarget() {
		return "Target 详情";
	}

	@Override
	public String mixinsDetalleMetodo() {
		return "Mixin 方法详情";
	}

	@Override
	public String mixinsDetalleCampo() {
		return "Mixin 字段详情";
	}

	@Override
	public String mixinsDetalleConflicto() {
		return "冲突详情";
	}

	@Override
	public String mixinsBuscarConflictos() {
		return "搜索可能的冲突";
	}

	@Override
	public String mixinsResultadosConflictos() {
		return "冲突结果";
	}

	@Override
	public String mixinsConflictosPosibles() {
		return "可能的冲突";
	}

	@Override
	public String mixinsErrorDescompilar() {
		return "反编译错误";
	}

	@Override
	public String mixinsColorPanel() {
		return "Mixins 面板颜色";
	}

	@Override
	public String mixinsColorTexto() {
		return "Mixins 文本颜色";
	}

	@Override
	public String mixinsColorTextoSuave() {
		return "Mixins 次要文本颜色";
	}

	@Override
	public String mixinsAyudaUso1() {
		return "此工具显示带有 SpongePowered mixins 的模组，并允许检查它们的类、targets、方法和字段。";
	}

	@Override
	public String mixinsAyudaUso2() {
		return "使用顶部选择器按特定模组筛选，或显示所有带有 mixins 的模组。";
	}

	@Override
	public String mixinsAyudaUso3() {
		return "展开树形结构以查看每个 mixin、其目标类、注解方法和 shadow 字段。";
	}

	@Override
	public String mixinsAyudaUso4() {
		return "右键单击模组、mixin、target、方法或字段，以搜索与其他 mixins 的可能冲突。";
	}

	@Override
	public String mixinsAyudaUso5() {
		return "你可以反编译当前选择或冲突结果，以检查相关代码。";
	}

	@Override
	public String mixinsColorComboFondo() {
		return "模组选择器背景色";
	}

	@Override
	public String mixinsColorAreaContenidoFondo() {
		return "详情面板背景色";
	}

	@Override
	public String mixinsColorSeleccionTexto() {
		return "文本选择颜色";
	}

	@Override
	public String mixinsColorSeleccionTextoActivo() {
		return "选中文本颜色";
	}

	@Override
	public String mixinsColorAyudaTexto() {
		return "帮助文本颜色";
	}

	@Override
	public String mixinsColorArbolFondo() {
		return "树形视图背景色";
	}

	@Override
	public String mixinsColorRendererSeleccionTexto() {
		return "树形视图选中文本颜色";
	}

	@Override
	public String mixinsColorRendererSeleccionFondo() {
		return "树形视图选中背景颜色";
	}

	@Override
	public String mixinsColorRendererBordeSeleccion() {
		return "树形视图选中边框颜色";
	}

	@Override
	public String depmapTitulo() {
		return "依赖关系图";
	}

	@Override
	public String depmapBotonLateral() {
		return "DepMap";
	}

	@Override
	public String depmapPestanaMapa() {
		return "地图";
	}

	@Override
	public String depmapPestanaDependientes() {
		return "依赖者";
	}

	@Override
	public String depmapRecargar() {
		return "重新加载";
	}

	@Override
	public String depmapDescompilarSeleccion() {
		return "反编译所选内容";
	}

	@Override
	public String depmapVerReferencias() {
		return "查看引用";
	}

	@Override
	public String depmapDependencias() {
		return "依赖项";
	}

	@Override
	public String depmapDependientes() {
		return "依赖者";
	}

	@Override
	public String depmapDependiente() {
		return "依赖者";
	}

	@Override
	public String depmapSinDependencias() {
		return "无依赖者";
	}

	@Override
	public String depmapSeleccionarMod() {
		return "选择模组";
	}

	@Override
	public String depmapSeleccionarModBase() {
		return "基础模组";
	}

	@Override
	public String depmapSeleccionarDependiente() {
		return "依赖模组";
	}

	@Override
	public String depmapSeleccionarPaquete() {
		return "包";
	}

	@Override
	public String depmapComprobarNoAlineadas() {
		return "检查未对齐";
	}

	@Override
	public String depmapResultadoNoAlineadas() {
		return "未对齐依赖项结果";
	}

	@Override
	public String depmapClaseInexistente() {
		return "不存在的类";
	}

	@Override
	public String depmapClaseReferenciada() {
		return "被引用的类";
	}

	@Override
	public String depmapOrigen() {
		return "来源";
	}

	@Override
	public String depmapDestino() {
		return "目标";
	}

	@Override
	public String depmapDependenciaDetalle() {
		return "依赖项详情";
	}

	@Override
	public String depmapReferenciaDetalle() {
		return "引用详情";
	}

	@Override
	public String depmapMetodoOrigen() {
		return "来源方法";
	}

	@Override
	public String depmapModBase() {
		return "基础模组";
	}

	@Override
	public String depmapTodos() {
		return "全部";
	}

	@Override
	public String depmapSeleccioneUnMod() {
		return "请选择一个模组";
	}

	@Override
	public String depmapSeleccioneParametrosNoAlineadas() {
		return "请选择基础模组、依赖模组和包";
	}

	@Override
	public String depmapSeleccioneClaseParaDescompilar() {
		return "请选择一个引用或发现项进行反编译";
	}

	@Override
	public String depmapErrorDescompilar() {
		return "反编译错误";
	}

	@Override
	public String depmapAyuda1() {
		return "此工具使用模组间的类引用构建模组依赖关系图。";
	}

	@Override
	public String depmapAyuda2() {
		return "地图标签页显示一个气泡图，将每个模组与其使用的依赖项连接起来。";
	}

	@Override
	public String depmapAyuda3() {
		return "依赖者标签页按依赖者数量从多到少对模组进行排序。";
	}

	@Override
	public String depmapAyuda4() {
		return "你可以检查模组与其依赖项之间的具体引用，并反编译相关类。";
	}

	@Override
	public String depmapAyuda5() {
		return "未对齐依赖项检查会在基础模组的包或子包中查找对不存在类的引用。";
	}

	@Override
	public String depmapColorPanel() {
		return "面板颜色";
	}

	@Override
	public String depmapColorTexto() {
		return "主文本颜色";
	}

	@Override
	public String depmapColorTextoSecundario() {
		return "次要文本颜色";
	}

	@Override
	public String depmapColorAyudaTexto() {
		return "帮助文本颜色";
	}

	@Override
	public String depmapColorGrafoFondo() {
		return "图表背景色";
	}

	@Override
	public String depmapColorListaFondo() {
		return "列表背景色";
	}

	@Override
	public String depmapColorArbolFondo() {
		return "树形视图背景色";
	}

	@Override
	public String depmapColorNodo() {
		return "图表节点颜色";
	}

	@Override
	public String depmapColorEnlace() {
		return "图表链接颜色";
	}

	@Override
	public String depmapColorSeleccion() {
		return "选择颜色";
	}

	@Override
	public String depmapColorSeleccionTexto() {
		return "选中文本颜色";
	}

	@Override
	public String nombreProblemaAzureLibAnimaciones() {
		return "AzureLib 插件问题";
	}

	@Override
	public String mensajeProblemaAzureLibAnimaciones() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "加载动画时检测到 AzureLib 错误。</b>"
				+ "<p>游戏发现了一个格式错误的动画 JSON 文件。</p>" + "<p>此问题通常由使用 <b>AzureLib</b> 的某个模组或插件引起。</p>" + "<p><b>建议：</b></p>"
				+ "<ul>" + "<li>使用侧边栏中的 <b>DepMap</b> 查找所有依赖于 AzureLib 的插件。</li>"
				+ "<li>尝试在不加载部分插件的情况下启动游戏，直到找到出错的那个。</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaDecocraftNatureEssentialPartnerMod() {
		return "Decocraft Nature 问题";
	}

	@Override
	public String mensajeProblemaDecocraftNatureEssentialPartnerMod() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到由 Decocraft Nature 引起的问题。</b>" + "<p>错误发生在初始化 mixin 配置 "
				+ "<code>com/razz/essentialpartnermod/mixins.json</code> 时。</p>" + "<p>可以通过编辑模组的 JAR 文件来修复此问题。</p>"
				+ "<p><b>步骤：</b></p>" + "<ul>" + "<li>使用归档工具（如 File Roller、Ark、WinRAR、7-Zip 或 WinZip）打开 JAR 文件。</li>"
				+ "<li>进入 <code>META-INF/MANIFEST.MF</code>。</li>" + "<li>删除此行：</li>" + "</ul>"
				+ "<code>MixinConfigs: com/razz/essentialpartnermod/mixins.json</code>"
				+ "<p><b>重要：</b>请保留文件末尾的唯一空行。</p>";
	}

	@Override
	public String mensajeTetraDeserializadorModeloEstatico() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "在 Tetra 或其某个插件中检测到错误。</b>"
				+ "<p>日志表明，未找到用于 <b>Tetra</b> 或其某个插件所使用的模型类型的反序列化器。</p>" + "<p><b>建议步骤：</b></p>" + "<ul>"
				+ "<li>首先，移除或禁用 <b>Tetra 插件</b> 并重试。</li>" + "<li>如果错误仍然存在，请尝试也移除 <b>Tetra</b>。</li>"
				+ "<li>你可以尝试在 <b>DepMap</b> 中查找与 Tetra 相关的插件，尽管它们并不总会出现在那里。</li>" + "</ul>"
				+ "<p>在某些情况下，问题源于某个插件，但在其他情况下，则是由 <b>Tetra</b> 本身引起的。</p>";
	}

	@Override
	public String nombreTetraDeserializadorModeloEstatico() {
		return "Tetra 模型反序列化错误";
	}

	@Override
	public String mensajeSimpleEmotesSetupAnimTail() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "在 Simple Emotes 中检测到错误。</b>"
				+ "<p>日志包含字符串 <b>$simpleemotes$setupAnimTAIL</b>，这直接指向 <b>Simple Emotes</b> 模组。</p>"
				+ "<p><b>建议选项：</b></p>" + "<ul>" + "<li>移除或禁用 <b>Simple Emotes</b> 并重试。</li>"
				+ "<li>如果你使用了更改玩家或模型动画的模组，请检查与 <b>Simple Emotes</b> 的潜在不兼容性。</li>"
				+ "<li>将 <b>Simple Emotes</b> 和任何与动画相关的模组更新到兼容版本。</li>" + "</ul>"
				+ "<p>此错误通常表明 <b>Simple Emotes</b> 直接导致了故障。</p>";
	}

	@Override
	public String nombreSimpleEmotesSetupAnimTail() {
		return "Simple Emotes 错误";
	}

	@Override
	public String mensajeAdvertenciaSKLauncher() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "关于 SKLauncher 的警告。</b>" + "<p>在过去几个月中，已观察到多起与 <b>SKLauncher</b> 相关的<b>文件损坏</b>及其他问题。</p>"
				+ "<p>这并不意味着 SKLauncher 总是错误的原因，但它可能会导致问题。</p>" + "<p><b>可能损坏的迹象：</b></p>" + "<ul>"
				+ "<li>游戏在启动早期阶段就关闭。</li>" + "<li>即使<b>没有安装任何模组</b>，游戏也会崩溃。</li>" + "</ul>"
				+ "<p>如果发生上述任何一种情况，请尝试使用<b>其他启动器</b>来检查问题是否消失。</p>" + "<p>在此处查看推荐的启动器列表：</p>"
				+ "<p><a href='https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/docs/ingles/minecraft/Launchers.md'>查看启动器文档</a></p>";
	}

	@Override
	public String nombreAdvertenciaSKLauncher() {
		return "警告：SKLauncher 可能存在的问题";
	}

	@Override
	public String guardTitulo() {
		return "CD Guard";
	}

	@Override
	public String guardBotonLateral() {
		return "防护";
	}

	@Override
	public String guardEscanearTodo() {
		return "扫描服务器和恶意软件";
	}

	@Override
	public String guardEscanearServidores() {
		return "搜索服务器";
	}

	@Override
	public String guardEscanearMalware() {
		return "搜索恶意软件";
	}

	@Override
	public String guardTablaServidores() {
		return "有问题的服务器";
	}

	@Override
	public String guardTablaMalware() {
		return "恶意软件发现";
	}

	@Override
	public String guardColServidor() {
		return "服务器";
	}

	@Override
	public String guardColDefinicion() {
		return "定义";
	}

	@Override
	public String guardColMensaje() {
		return "消息";
	}

	@Override
	public String guardColUbicacion() {
		return "位置";
	}

	@Override
	public String guardColClase() {
		return "类";
	}

	@Override
	public String guardColCfr() {
		return "CFR";
	}

	@Override
	public String guardCfr() {
		return "反编译";
	}

	@Override
	public String guardCfrTitulo() {
		return "反编译代码";
	}

	@Override
	public String guardDescripcion1() {
		return "此工具允许搜索模组中有问题的服务器和潜在的恶意软件发现。";
	}

	@Override
	public String guardDescripcion2() {
		return "可能会出现误报，特别是当其他定义或恶意软件扫描程序过于激进时。";
	}

	@Override
	public String guardDescripcion3() {
		return "服务器检查使用外部定义。如果你尚未下载它们，则需要先进行下载。";
	}

	@Override
	public String guardDescripcion4() {
		return "如果你已有本地定义，工具将允许你决定是重用它们还是进行更新。";
	}

	@Override
	public String guardDescripcion5() {
		return "在恶意软件表中，如果某个类可用，你可以使用 CFR 对其进行反编译以进行检查。";
	}

	@Override
	public String guardEstadoEscaneandoTodo() {
		return "正在扫描服务器和恶意软件...";
	}

	@Override
	public String guardEstadoEscaneandoServidores() {
		return "正在搜索有问题的服务器...";
	}

	@Override
	public String guardEstadoEscaneandoMalware() {
		return "正在搜索恶意软件...";
	}

	@Override
	public String guardEstadoListo() {
		return "就绪";
	}

	@Override
	public String guardDefsNoEncontradasTitulo() {
		return "未找到定义";
	}

	@Override
	public String guardDefsNoEncontradasMensaje() {
		return "未找到服务器定义。是否现在下载？";
	}

	@Override
	public String guardDefsDescargar() {
		return "下载";
	}

	@Override
	public String guardDefsCancelar() {
		return "取消";
	}

	@Override
	public String guardDefsActualizarTitulo() {
		return "服务器定义";
	}

	@Override
	public String guardDefsActualizarMensaje() {
		return "已存在本地定义。你是想直接使用它们还是进行更新？";
	}

	@Override
	public String guardDefsUsarLocales() {
		return "使用本地定义";
	}

	@Override
	public String guardDefsActualizar() {
		return "更新";
	}

	@Override
	public String guardFuenteDefinicionesTLauncher() {
		return "TLauncher 列表";
	}

	@Override
	public String guardErrorDescompilar() {
		return "反编译错误";
	}

	@Override
	public String guardColorPanel() {
		return "面板颜色";
	}

	@Override
	public String guardColorTexto() {
		return "文本颜色";
	}

	@Override
	public String guardColorTextoSecundario() {
		return "次要文本颜色";
	}

	@Override
	public String guardColorTabla() {
		return "表格颜色";
	}

	@Override
	public String guardColorSeleccion() {
		return "选中颜色";
	}

	@Override
	public String guardColorSeleccionTexto() {
		return "选中文本颜色";
	}

	@Override
	public String texto_de_boton_compartir_instancia_modpack() {
		return "分享实例/整合包";
	}

	@Override
	public String popup_compartir_instancia_modpack() {
		return "分享实例或整合包的功能尚未实现。";
	}

	@Override
	public String colorBotonCompartirVerdeOscuro() {
		return "主分享按钮颜色";
	}

	@Override
	public String colorBotonCompartirVerdeClaro() {
		return "链接分享按钮颜色";
	}

	@Override
	public String colorTextoBotonesCompartir() {
		return "分享按钮文本颜色";
	}

	@Override
	public String compartirInstanciaTitulo() {
		return "分享实例";
	}

	@Override
	public String compartirInstanciaBotonLateral() {
		return "分享实例";
	}

	@Override
	public String compartirInstanciaFormato() {
		return "格式";
	}

	@Override
	public String compartirInstanciaServicio() {
		return "上传服务";
	}

	@Override
	public String compartirInstanciaBotonCompartir() {
		return "打包并分享";
	}

	@Override
	public String compartirInstanciaBotonRefrescar() {
		return "刷新";
	}

	@Override
	public String compartirInstanciaEstadoListo() {
		return "就绪";
	}

	@Override
	public String compartirInstanciaEstadoEmpaquetando() {
		return "正在打包所选内容...";
	}

	@Override
	public String compartirInstanciaEstadoSubiendo() {
		return "正在上传文件...";
	}

	@Override
	public String compartirInstanciaEstadoError() {
		return "错误";
	}

	@Override
	public String compartirInstanciaCodigo() {
		return "代码";
	}

	@Override
	public String compartirInstanciaEnlace() {
		return "链接";
	}

	@Override
	public String compartirInstanciaMantenerAbierto() {
		return "你必须保持应用程序开启，以便传输继续可用。";
	}

	@Override
	public String compartirInstanciaSinSeleccion() {
		return "未选择任何文件夹或文件。";
	}

	@Override
	public String compartirInstanciaFormatoNoSoportado() {
		return "该格式目前不受支持。";
	}

	@Override
	public String compartirInstanciaServicioNoDisponible() {
		return "所选服务不可用。";
	}

	@Override
	public String compartirInstanciaSubidaCompleta() {
		return "传输已成功启动。";
	}

	@Override
	public String compartirInstanciaErrorSubir() {
		return "无法上传所选文件。";
	}

	@Override
	public String compartirInstanciaColorPanel() {
		return "面板颜色";
	}

	@Override
	public String compartirInstanciaColorTexto() {
		return "文本颜色";
	}

	@Override
	public String compartirInstanciaPolitica1() {
		return "推荐类型：mods、configs、saves、worlds、datapacks、资源包和选项文件。避免包含不必要的私人材料。";
	}

	@Override
	public String compartirInstanciaPolitica2() {
		return "扩展可以添加自己的上传服务。默认集成的服务应显示在此处。";
	}

	@Override
	public String compartirInstanciaPolitica3() {
		return "wormhole.app：正常上传可达 5 GiB；5 到 10 GiB 之间需要保持发送端开启。在当前项目实现中，实际集成仍在待定状态。";
	}

	@Override
	public String compartirInstanciaPolitica4() {
		return "limewire.com：旨在作为临时保留服务。此实现尚不支持。";
	}

	@Override
	public String compartirInstanciaPolitica5() {
		return "bittorrent：由于是直接 P2P 分发，无中央托管，因此是最安全的模式。此实现尚不支持。";
	}

	@Override
	public String compartirInstanciaPolitica6() {
		return "默认情况下，会选择实例中最常见的文件夹和文件，以方便技术支持。";
	}

	@Override
	public String compartirInstanciaPolitica7() {
		return "如果你包含 CrashDetector 的内部文件夹，配置、日志和辅助数据也将被传输，因此如果不需要，你可以取消选择它。";
	}

	@Override
	public String malware_fracturiser_detectado() {
		return "检测到可能的 Fracturiser。证据：";
	}

	@Override
	public String malware_info_stealer_detectado() {
		return "检测到可能的信息窃取程序。证据：";
	}

	@Override
	public String malware_evidencia_clase_sospechosa() {
		return "可疑类：";
	}

	@Override
	public String malware_evidencia_archivo_sospechoso() {
		return "可疑文件：";
	}

	@Override
	public String malware_brightsdk_detectado() {
		return "检测到可能的 Bright SDK。证据：";
	}

	@Override
	public String malware_evidencia_paquete_sospechoso() {
		return "可疑包：";
	}

	@Override
	public String docsTituloVentana() {
		return "文档阅读器";
	}

	@Override
	public String docsAyudaDeUso() {
		return "<b>如何使用此阅读器</b><br>" + "在底部选择一种语言以查看该语言的可用文档。<br>" + "在左侧面板中，您可以浏览文件夹和文档。<br>" + "点击文档后，其内容将显示在右侧。<br>"
				+ "使用 <b>docs://</b> 协议的内部链接将在本阅读器中打开其他文档。";
	}

	@Override
	public String docsArbolTitulo() {
		return "文档";
	}

	@Override
	public String docsVisorTitulo() {
		return "内容";
	}

	@Override
	public String docsNoHayDocumentos() {
		return "此语言没有文档。";
	}

	@Override
	public String docsNoHayMarkdown() {
		return "在此语言中未找到 Markdown 文件。";
	}

	@Override
	public String docsDocumentoNoEncontrado() {
		return "未找到请求的文档。";
	}

	@Override
	public String docsErrorAlAbrirDocumento() {
		return "打开文档时出错：";
	}

	@Override
	public String docsCargando() {
		return "正在加载文档...";
	}

	@Override
	public String docsImagenNoDisponible() {
		return "插图不可用";
	}

	@Override
	public String colorPanelSecundario() {
		return "次要面板颜色";
	}

	@Override
	public String colorTextoSuave() {
		return "柔和文本颜色";
	}

	@Override
	public String colorSeleccion() {
		return "选中颜色";
	}

	@Override
	public String colorFondoDocumento() {
		return "文档背景颜色";
	}

	@Override
	public String iaTituloVentana() {
		return "人工智能";
	}

	@Override
	public String iaTituloPrincipal() {
		return "AI 分析";
	}

	@Override
	public String iaDescripcionTitulo() {
		return "崩溃分析代理";
	}

	@Override
	public String iaDescripcionCuerpo() {
		return "此工具打开一个外部代理，可以帮助你分析与 Minecraft 相关的崩溃、错误和日志。";
	}

	@Override
	public String iaDescripcionUso() {
		return "要使用此系统，请打开链接，使用百度账号登录，然后使用该代理检查你的崩溃报告或日志。";
	}

	@Override
	public String iaAvisoCuentaBaidu() {
		return "你需要一个百度账号才能访问该代理。";
	}

	@Override
	public String iaCopiarEnlace() {
		return "复制链接";
	}

	@Override
	public String iaAbrirEnlace() {
		return "打开链接";
	}

	@Override
	public String iaImagenNoDisponible() {
		return "图片不可用";
	}

	@Override
	public String mensajeOculusIrisUnknownShaderVariable() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到 Oculus 或 Iris 可能存在着色器（Shader）错误。</b>"
				+ "<p>日志中同时包含 <b>kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal</b> "
				+ "和 <b>java.lang.RuntimeException: Unknown variable:</b>。</p>" + "<p>这种组合通常表明在评估着色器内的变量时出现问题，"
				+ "通常与 <b>Oculus</b>、<b>Iris</b> 或正在使用的<b>着色器包（shader pack）</b>有关。</p>" + "<p><b>建议的测试顺序：</b></p>"
				+ "<ul>" + "<li>首先，在<b>未启用着色器</b>的情况下启动游戏。</li>"
				+ "<li>如果问题仍然存在，尝试在<b>未安装 Oculus 或 Iris</b>的情况下启动。</li>"
				+ "<li>更新 <b>Oculus/Iris</b>、<b>着色器包</b> 以及相关的图形模组（mods）。</li>"
				+ "<li>如果您使用了其他渲染或图形模组，请检查它们之间是否存在兼容性冲突。</li>" + "</ul>"
				+ "<p>实际上，此故障通常源于<b>着色器包</b>或其与 <b>Oculus/Iris</b> 的交互。</p>";
	}

	@Override
	public String nombreOculusIrisUnknownShaderVariable() {
		return "Oculus/Iris 可能的着色器错误";
	}

	@Override
	public String mensajeItemNoExiste(String itemFaltante, String namespace) {
		String itemHtml = itemFaltante == null || itemFaltante.isEmpty() ? "(未知)" : itemFaltante;
		String namespaceHtml = namespace == null || namespace.isEmpty() ? "(未知)" : namespace;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "尝试使用一个不存在的物品。</b>"
				+ "<p>日志中包含行 <b>Item: " + itemHtml + " does not exist</b>。</p>"
				+ "<p>这通常意味着某个 <b>数据包 (datapack)</b>、<b>模组 (mod)</b> 或 <b>配置</b> " + "引用了游戏中不存在的物品。</p>"
				+ "<p><b>需要检查的内容：</b></p>" + "<ul>" + "<li>检查是否已安装应该提供物品 <b>" + itemHtml + "</b> 的模组。</li>"
				+ "<li>如果已安装，可能是 <b>版本错误</b>，物品已被更改或删除，" + "或者模组存在问题，建议移除。</li>" + "<li>如果没有该模组，请尝试 <b>安装它</b>。</li>"
				+ "</ul>" + "<p><b>要找出哪个模组或数据包正在请求该物品：</b></p>" + "<ul>" + "<li>使用侧边栏中的 <b>grepr</b> 工具。</li>"
				+ "<li><b>不要</b> 选择文件夹。</li>" + "<li>启用 <b>search in archives</b> 选项。</li>"
				+ "<li>在搜索文本中，输入 <b>namespace</b>，即冒号前的部分： " + "<b>" + namespaceHtml + "</b>。</li>" + "</ul>"
				+ "<p>这通常有助于找到进行无效引用的文件、模组或数据包。</p>";
	}

	@Override
	public String nombreItemNoExiste() {
		return "引用了不存在的物品";
	}

	@Override
	public String mensajeCobblemonPinkanIslandsRhyhornModelo() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到 Rhyhorn 的模型错误。</b>"
				+ "<p>日志中包含行 <b>Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn</b>。</p>"
				+ "<p>尽管该模型使用 <b>Cobblemon</b> 的命名空间，但此行通常由模组 "
				+ "<b>Cobblemon: Pinkan Islands</b> 引起，该 <b>Rhyhorn</b> 即来源于此模组。</p>" + "<p><b>建议尝试：</b></p>" + "<ul>"
				+ "<li>移除或禁用 <b>Cobblemon: Pinkan Islands</b> 并再次尝试。</li>"
				+ "<li>将 <b>Cobblemon</b> 和 <b>Cobblemon: Pinkan Islands</b> 更新为相互兼容的版本。</li>"
				+ "<li>如果问题是在更新其中一个模组后开始的，请尝试不同的版本组合。</li>" + "</ul>"
				+ "<p>此故障通常表明 <b>Cobblemon: Pinkan Islands</b> 内部存在缺失、注册错误或不相容的模型。</p>";
	}

	@Override
	public String nombreCobblemonPinkanIslandsRhyhornModelo() {
		return "Cobblemon: Pinkan Islands 中的 Rhyhorn 模型错误";
	}

	@Override
	public String mensajeColdSweatInitDynamicTags() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到 Cold Sweat 错误。</b>"
				+ "<p>日志中包含诸如 <b>$cold_sweat$onBuildStart</b>、"
				+ "<b>InitDynamicTagsEvent.fillTag</b> 以及 <b>NullPointerException</b> 等迹象，其中 " + "注册表显示为 null。</p>"
				+ "<p>这通常表明 <b>Cold Sweat</b> 在构建或填充 " + "<b>动态标签（dynamic tags）</b>时出现问题，通常是由于不兼容、模组内部错误 "
				+ "或与其他模组或数据包存在冲突组合所致。</p>" + "<p><b>建议尝试：</b></p>" + "<ul>" + "<li>移除或禁用 <b>Cold Sweat</b> 并再次尝试。</li>"
				+ "<li>将 <b>Cold Sweat</b> 更新为与您的 Minecraft 版本和加载器（loader）兼容的版本。</li>"
				+ "<li>如果您使用了修改 <b>标签（tags）</b>、<b>生物群系（biomes）</b>、<b>温度</b> 或相关注册表的数据包或模组，也请检查它们。</li>"
				+ "<li>如果错误是在更新模组后开始的，请尝试不同的版本组合。</li>" + "</ul>" + "<p>在这种情况下，<b>Cold Sweat</b> 直接导致了故障。</p>";
	}

	@Override
	public String nombreColdSweatInitDynamicTags() {
		return "Cold Sweat 动态标签错误";
	}

	@Override
	public String mensajeClassCastExceptionGeneral(String lineaClassCast) {
		String detalle = lineaClassCast == null || lineaClassCast.isEmpty() ? ""
				: "<p><b>检测到的行：</b></p><p><code>" + lineaClassCast + "</code></p>";

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到 ClassCastException。</b>" + "<p>这意味着一个类被当作另一个不兼容的类或接口来处理。</p>" + detalle
				+ "<p>此类错误通常由以下情况之一引起：</p>" + "<ul>" + "<li><b>两个相互不兼容的模组</b>。</li>"
				+ "<li>修改了某个类的 <b>Mixins</b>、<b>transformers</b> 或补丁，导致游戏其他部分期望不同的类型。</li>"
				+ "<li><b>stacktrace</b> 中出现的其他模组导致了错误的类型转换（miscast）。</li>" + "</ul>" + "<p><b>需要检查的内容：</b></p>"
				+ "<ul>" + "<li>查看与此错误相关的 <b>stacktrace</b> 行。</li>"
				+ "<li>特别注意格式为 <b>$modid$algo</b> 的模组或类名，因为它们通常指示涉及到的模组。</li>" + "<li>尝试更新、移除或隔离似乎与无效转换相关的模组。</li>"
				+ "</ul>" + "<p>虽然 <b>ClassCastException</b> 并不总是致命的，但很多时候确实是。</p>";
	}

	@Override
	public String nombreClassCastExceptionGeneral() {
		return "检测到 ClassCastException";
	}

	@Override
	public String mensajeValkyrienSkiesTournamentLithiumPoiInjection() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到 Valkyrien Skies Tournament 与 Lithium/Radium 之间可能存在不兼容。</b>"
				+ "<p>日志中包含 <b>InvalidInjectionException</b>，其中出现了 "
				+ "<b>Lithium</b> 针对 <b>POI</b> 的 mixin 以及 <b>valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager</b>。</p>"
				+ "<p>此问题通常在使用 <b>旧版 Lithium</b> 或基于旧版 Lithium 的 " + "<b>分支（fork）</b>（如 <b>Radium Reforged</b>）与 "
				+ "<b>VS Tournament</b> 一起使用时发生。</p>" + "<p><b>建议尝试：</b></p>" + "<ul>"
				+ "<li>将 <b>Lithium</b> 更新为更新且兼容的版本。</li>"
				+ "<li>如果您在 <b>Forge/NeoForge</b> 上并使用 <b>Radium Reforged</b> 或其他旧版分支，请将其移除。</li>"
				+ "<li>请改用 <b>Harium</b>，它是 Radium 的现代分支，已与 Lithium 的最新改进同步。</li>"
				+ "<li>如果问题是在更新模组后开始的，请仔细检查 <b>VS Tournament</b> 与您的 AI/POI 优化模组之间的具体版本组合。</li>" + "</ul>"
				+ "<p>实际上，此故障通常源于 <b>Lithium/Radium</b> 的旧版实现，无法与 <b>VS Tournament</b> 良好配合。</p>";
	}

	@Override
	public String nombreValkyrienSkiesTournamentLithiumPoiInjection() {
		return "VS Tournament 与 Lithium/Radium 不兼容";
	}

	@Override
	public String mensajeVSTournamentVSConfigClassNoExiste() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VS Tournament 的版本对于您当前的 Valkyrien Skies 版本来说过于陈旧。</b>" + "<p>日志中包含 <b>NoClassDefFoundError</b>，涉及 "
				+ "<b>org/valkyrienskies/core/impl/config/VSConfigClass</b> 以及来自 "
				+ "<b>org.valkyrienskies.tournament.TournamentMod.init(...)</b> 的相关行。</p>"
				+ "<p>这通常意味着您正在使用一个 <b>旧版的 VS Tournament</b>，它试图 " + "调用 <b>Valkyrien Skies</b> 中已不存在的旧内部类。</p>"
				+ "<p><b>建议操作：</b></p>" + "<ul>" + "<li>移除旧版的 <b>VS Tournament</b>。</li>"
				+ "<li>改用 <b>VS Tournament Reforged</b>。</li>"
				+ "<li>同时请确保 <b>Valkyrien Skies</b> 的版本与该附加组件支持的版本相匹配。</li>" + "</ul>"
				+ "<p>建议更换为 <b>VS Tournament Reforged</b> 符合该项目的当前状态：" + "原始 Tournament 版本仍被列为适用于 1.18.2 的旧版模组，而 "
				+ "<b>VS Tournament Reforged</b> 则作为独立项目发布，目前宣布支持 Valkyrien " + "2.4.9+（基于 Forge 1.20.1）。</p>";
	}

	@Override
	public String nombreVSTournamentVSConfigClassNoExiste() {
		return "旧版 VS Tournament 与 Valkyrien Skies 不兼容";
	}

	public String curseForgeClaveApiMundial() {
		return "CurseForge 全局 API 密钥";
	}

	public String curseForgeEndpoint() {
		return "CurseForge 端点";
	}

	public String tlmodsEndpoint() {
		return "TLMods 端点";
	}

	public String minecraftStorageEndpoint() {
		return "MinecraftStorage 端点";
	}

	public String autoBackupActivado() {
		return "已启用自动备份";
	}

	public String autoBackupFrecuencia() {
		return "自动备份频率";
	}

	public String autoBackupDiasConservar() {
		return "自动备份保留天数";
	}

	public String autoBackupTamanoMaximoMB() {
		return "自动备份最大大小 (MB)";
	}

	public String actualizadorModsTitulo() {
		return "模组更新器";
	}

	public String actualizadorModsBotonSidebar() {
		return "更新器";
	}

	public String actualizadorModsDescripcion() {
		return "搜索模组包中可用的模组更新。您可以全部更新或单独应用更新。";
	}

	public String actualizadorModsBotonEscanear() {
		return "检查更新";
	}

	public String actualizadorModsBotonActualizarTodo() {
		return "全部更新";
	}

	public String actualizadorModsBotonActualizarUno() {
		return "更新";
	}

	public String actualizadorModsEstadoListo() {
		return "就绪";
	}

	public String actualizadorModsEstadoEscaneando() {
		return "正在搜索更新...";
	}

	public String actualizadorModsEstadoActualizando() {
		return "正在更新...";
	}

	public String actualizadorModsEstadoSinActualizaciones() {
		return "没有可用的更新。";
	}

	public String actualizadorModsEstadoEncontradas(int n) {
		return "找到更新: " + n;
	}

	public String actualizadorModsEstadoActualizadas(int n) {
		return "已应用更新: " + n;
	}

	public String actualizadorModsEstadoError() {
		return "更新时出错。";
	}

	public String actualizadorModsSinSeleccion() {
		return "未选择任何更新。";
	}

	public String actualizadorModsColumnaMod() {
		return "模组";
	}

	public String actualizadorModsColumnaActual() {
		return "当前";
	}

	public String actualizadorModsColumnaNueva() {
		return "新版";
	}

	public String actualizadorModsColumnaFuente() {
		return "来源";
	}

	public String actualizadorModsColumnaLoader() {
		return "加载器";
	}

	public String actualizadorModsColumnaRuta() {
		return "路径";
	}

	public String actualizadorModsColumnaAccion() {
		return "操作";
	}

	public String actualizadorModsColorFondo() {
		return "更新器: 背景";
	}

	public String actualizadorModsColorPanel() {
		return "更新器: 面板";
	}

	public String actualizadorModsColorTexto() {
		return "更新器: 文本";
	}

	public String actualizadorModsColorTextoSuave() {
		return "更新器: 次要文本";
	}

	public String actualizadorModsColorBoton() {
		return "更新器: 按钮";
	}

	public String actualizadorModsColorBotonTexto() {
		return "更新器: 按钮文本";
	}

	public String actualizadorModsColorTabla() {
		return "更新器: 表格";
	}

	public String actualizadorModsColorSeleccion() {
		return "更新器: 选中项";
	}

	public String importadorYumeiriTeExtraniamos() {
		return "我们想念你，梦璃雷尤。";
	}

	public String importadorColorFondo() {
		return "导入器: 背景";
	}

	public String importadorColorPanel() {
		return "导入器: 面板";
	}

	public String importadorColorTexto() {
		return "导入器: 文本";
	}

	public String importadorColorTextoSuave() {
		return "导入器: 次要文本";
	}

	public String importadorColorBoton() {
		return "导入器: 按钮";
	}

	public String importadorColorBotonTexto() {
		return "导入器: 按钮文本";
	}

	public String importadorColorBorde() {
		return "导入器: 边框";
	}

	public String importadorConflictoTitulo() {
		return "导入冲突";
	}

	public String importadorConflictoMensaje() {
		return "目标位置已存在文件。";
	}

	public String importadorRuta() {
		return "路径";
	}

	public String importadorArchivoExistente() {
		return "现有文件";
	}

	public String importadorArchivoNuevo() {
		return "导入的文件";
	}

	public String importadorTamano() {
		return "大小";
	}

	public String importadorFecha() {
		return "最后修改时间";
	}

	public String importadorInfoMod() {
		return "模组信息";
	}

	public String importadorModImportadoMasNuevo() {
		return "导入的模组似乎更新。";
	}

	public String importadorModImportadoMasViejo() {
		return "导入的模组似乎更旧。";
	}

	public String importadorBotonReemplazar() {
		return "替换";
	}

	public String importadorBotonSaltar() {
		return "跳过";
	}

	public String importadorBotonRenombrar() {
		return "重命名新文件";
	}

	public String importadorModpackTitulo() {
		return "导入模组包";
	}

	public String importadorModpackBotonSidebar() {
		return "导入";
	}

	public String importadorModpackDescripcion() {
		return "将模组包导入当前实例。您可以拖放 .zip、.mrpack 或其他支持的文件格式，或手动选择。";
	}

	public String importadorModpackFormato() {
		return "格式";
	}

	public String importadorModpackArrastraArchivo() {
		return "将模组包拖放到此处或选择文件";
	}

	public String importadorModpackBotonSeleccionar() {
		return "选择文件";
	}

	public String importadorModpackBotonImportar() {
		return "导入";
	}

	public String importadorModpackSeleccionarArchivo() {
		return "选择模组包";
	}

	public String importadorModpackEstadoListo() {
		return "就绪";
	}

	public String importadorModpackEstadoImportando() {
		return "正在导入...";
	}

	public String importadorModpackEstadoError() {
		return "导入时出错。";
	}

	public String importadorModpackSinArchivo() {
		return "请先选择一个模组包文件。";
	}

	public String importadorModpackFormatoNoSoportado() {
		return "此格式不支持导入。";
	}

	public String importadorModpackResultado(int copiados, int reemplazados, int saltados, int renombrados,
			int errores) {
		return "导入完成。\n已复制: " + copiados + "\n已替换: " + reemplazados + "\n已跳过: " + saltados + "\n已重命名: " + renombrados
				+ "\n错误: " + errores;
	}

	public String importadorModpackColorFondo() {
		return "模组包导入器: 背景";
	}

	public String importadorModpackColorPanel() {
		return "模组包导入器: 面板";
	}

	public String importadorModpackColorTexto() {
		return "模组包导入器: 文本";
	}

	public String importadorModpackColorTextoSuave() {
		return "模组包导入器: 次要文本";
	}

	public String importadorModpackColorBoton() {
		return "模组包导入器: 按钮";
	}

	public String importadorModpackColorBotonTexto() {
		return "模组包导入器: 按钮文本";
	}

	public String importadorModpackColorDrop() {
		return "模组包导入器: 拖放区";
	}

	public String importadorModpackColorBorde() {
		return "模组包导入器: 边框";
	}

	public String jgitTituloIzzy() {
		return "Izzy 的 Git 中心";
	}

	public String jgitRetratoIzzy() {
		return "Izzy";
	}

	public String jgitNoHayRetratoIzzy() {
		return "没有 Izzy 的肖像";
	}

	public String jgitSeccionInstalacion() {
		return "JGit 安装";
	}

	public String jgitAbrirCarpetaInstalacion() {
		return "打开安装文件夹";
	}

	public String jgitAbrirPaginaDescarga() {
		return "打开 JGit 页面";
	}

	public String jgitSeccionRepositorio() {
		return "本地仓库";
	}

	public String jgitCrearRepositorioLocal() {
		return "在此处创建 Git 仓库";
	}

	public String jgitCommitManual() {
		return "手动 Commit";
	}

	public String jgitSeccionRemote() {
		return "远程仓库";
	}

	public String jgitForgeManual() {
		return "手动";
	}

	public String jgitForgePersonalizado() {
		return "自定义 Forge";
	}

	public String jgitEstablecerRemoteManual() {
		return "手动设置远程仓库";
	}

	public String jgitCrearRemoteConAPI() {
		return "通过 API 创建远程仓库";
	}

	public String jgitPushManual() {
		return "手动 Push";
	}

	public String jgitSeccionAutomaticos() {
		return "自动化";
	}

	public String jgitAutoCommitDespuesBackup() {
		return "备份后自动 Commit";
	}

	public String jgitAutoPushDespuesCommit() {
		return "Commit 后自动 Push";
	}

	public String jgitSeccionHerramientas() {
		return "工具";
	}

	public String jgitAbrirGuiSwing() {
		return "打开 JGit Swing 查看器";
	}

	public String jgitEstado() {
		return "状态";
	}

	public String jgitClasspath() {
		return "Classpath 中的 JGit";
	}

	public String jgitTodosLosArtefactos() {
		return "所有 JGit 构件";
	}

	public String jgitRepositorio() {
		return "仓库";
	}

	public String jgitRemote() {
		return "远程";
	}

	public String jgitCarpetaActual() {
		return "当前文件夹";
	}

	public String jgitNoSePudoCrearRepo() {
		return "无法创建仓库。";
	}

	public String jgitEscribaRemote() {
		return "输入远程仓库 URL：";
	}

	public String jgitNoSePudoGuardarRemote() {
		return "无法保存远程仓库。";
	}

	public String jgitApiForgeAunNoImplementada() {
		return "Forge API 尚未实现。";
	}

	public String jgitNoHayCambiosOError() {
		return "没有可 Commit 的更改或发生错误。";
	}

	public String jgitNoSePudoPush() {
		return "无法 Push。";
	}

	public String jgitTituloVentanaSwing() {
		return "Git 查看器";
	}

	public String jgitNoHayRepositorio() {
		return "此文件夹中没有 Git 仓库。";
	}

	public String jgitArchivosModificados() {
		return "已修改的文件";
	}

	public String jgitArchivosNuevos() {
		return "新文件";
	}

	public String jgitUltimosCommits() {
		return "最近的 Commits";
	}

	public String jgitError() {
		return "JGit 错误";
	}

	public String si() {
		return "是";
	}

	public String no() {
		return "否";
	}

	public String jgitDescargarDependenciasFaltantes() {
		return "下载缺失的依赖项";
	}

	public String jgitNoFaltanDependencias() {
		return "没有缺失的 JGit 依赖项。";
	}

	public String jgitConfirmarDescargaDependencias(int cantidad) {
		return "缺少 " + cantidad + " 个 JGit 依赖项。您想从 Maven Central 下载它们吗？";
	}

	public String jgitDependenciasDescargadas(int cantidad) {
		return "已下载依赖项: " + cantidad;
	}

	public String jgitErroresDescarga() {
		return "下载错误";
	}

	public String jgitReiniciarParaCargarClasspath() {
		return "请重启 CrashDetector，以便将新的 JAR 文件加载到 classpath 中。";
	}

	public String jgitArtefactosFaltantes() {
		return "缺失的构件";
	}

	public String jgitArtefactosFaltantesClasspath() {
		return "Classpath 中缺失的构件";
	}

	public String jgitArtefactosFaltantesCarpeta() {
		return "安装文件夹中缺失的构件";
	}

	public String jgitDependenciasEnCarpeta() {
		return "文件夹中已安装的依赖项";
	}

	public String jgitForgeNoSeleccionada() {
		return "未选择 Forge。";
	}

	public String jgitForgeNoRegistrada(String id) {
		return "Forge 未注册: " + id;
	}

	public String jgitEscribaUrlForge() {
		return "Forge URL：";
	}

	public String jgitEscribaNombreRepositorio() {
		return "仓库名称：";
	}

	public String jgitEscribaDescripcionRepositorio() {
		return "仓库描述：";
	}

	public String jgitEscribaNamespaceOpcional() {
		return "可选 Namespace：";
	}

	public String jgitEscribaTokenForge() {
		return "Forge API Token：";
	}

	public String jgitErrorCrearRemote() {
		return "创建 Remote 时出错";
	}

	@Override
	public String mensajeControlifyRemoveReloadingScreen() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "检测到 Controlify 与 Remove Reloading Screen 之间存在不兼容。</b>"
				+ "<p>日志中包含行 <b>Attempted to fetch default config before DefaultConfigManager was ready!</b> "
				+ "和 <b>$rrls$init</b>，这通常表明 <b>Controlify</b> 与 " + "<b>Remove Reloading Screen</b> 之间存在冲突。</p>"
				+ "<p><b>可能原因：</b>Remove Reloading Screen 修改了加载屏幕或加载过程的部分内容， " + "而 Controlify 尝试在系统完全就绪之前初始化其配置。</p>"
				+ "<p><b>建议选项：</b></p>" + "<ul>" + "<li>移除 <b>Remove Reloading Screen</b>。</li>"
				+ "<li>如果有新版本可用，请更新 <b>Controlify</b> 和 <b>Remove Reloading Screen</b>。</li>"
				+ "<li>如果问题仍然存在，请保留 <b>Controlify</b> 并移除任何修改加载屏幕的模组。</li>" + "</ul>" + "<p>修改加载屏幕的模组通常会导致与其他模组的不兼容， "
				+ "与其可能引起的问题相比，它们提供的实际好处通常很少。</p>";
	}

	@Override
	public String nombreControlifyRemoveReloadingScreen() {
		return "不兼容：Controlify 与 Remove Reloading Screen";
	}

	@Override
	public String mensajeBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Biomes O' Plenty 与自定义液体可能存在冲突。</b>"
				+ "<p>日志中包含错误 <b>class org.joml.Vector4f cannot be cast to class "
				+ "net.minecraft.client.renderer.fog.FogData</b> 以及对 <b>Biomes O' Plenty</b> 的引用。</p>"
				+ "<p>这可能与 <b>Biomes O' Plenty</b> 有关，特别是其生物群系、雾气 " + "或自定义液体。然而，不能完全确定 Biomes O' Plenty 是唯一的原因。</p>"
				+ "<p><b>建议选项：</b></p>" + "<ul>" + "<li>尝试编辑玩家数据以将其移动到世界的其他位置。</li>"
				+ "<li>尝试在没有 <b>Biomes O' Plenty</b> 的情况下加载世界。</li>" + "<li>如果移动玩家后世界能够加载，问题可能发生在特定区域、 "
				+ "特定生物群系或附近的自定义液体处。</li>" + "<li>您还可以尝试更新 <b>Biomes O' Plenty</b> 以及与渲染、雾气、 "
				+ "着色器（shaders）或维度相关的模组。</li>" + "</ul>" + "<p>如果移除 Biomes O' Plenty 后游戏可以启动，请检查玩家是否位于该模组添加的生物群系 "
				+ "或流体内部或附近。</p>";
	}

	@Override
	public String nombreBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "潜在问题：Biomes O' Plenty 与 FogData";
	}

	@Override
	public String mensajeKotlinReflectionInternalErrorVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "检测到 Kotlin 内部反射错误。</b>"
				+ "<p>日志中包含 <b>kotlin.reflect.jvm.internal.KotlinReflectionInternalError</b>，消息类似于 "
				+ "<b>Property 'none' not resolved</b>。</p>"
				+ "<p>此类错误在某些版本的 <b>Fabric Language Kotlin</b> / <b>Kotlin</b> 中很常见。 "
				+ "在这种情况下，出现了 <b>Inventory Profiles Next</b> 的类，但同样的问题也可能发生 " + "在使用 Kotlin 的其他模组上。</p>"
				+ "<p><b>建议选项：</b></p>" + "<ul>"
				+ "<li>将 <b>Fabric Language Kotlin</b> 更新到版本 <b>2.3.40</b>（如果您的 Minecraft 版本支持）。</li>"
				+ "<li>如果更新无效，请尝试将 <b>Fabric Language Kotlin</b> 降级到版本 <b>2.3.10</b>。</li>"
				+ "<li>如果日志提到了该模组的类，也请更新 <b>Inventory Profiles Next</b>。</li>"
				+ "<li>如果错误出现在其他模组上，请检查该模组是否依赖 Kotlin，并尝试更改 " + "<b>Fabric Language Kotlin</b> 的版本。</li>" + "</ul>"
				+ "<p>相关技术参考： "
				+ "<a href='https://github.com/FabricMC/fabric-language-kotlin/issues/183'>Fabric Language Kotlin 问题 #183</a>。</p>";
	}

	@Override
	public String nombreKotlinReflectionInternalErrorVersion() {
		return "Kotlin 错误：内部反射";
	}

	public String tituloEscanerMCreator() {
		return "MCreator 扫描器";
	}

	public String escanerMCreatorEscaneandoMods() {
		return "正在扫描模组...";
	}

	public String escanerMCreatorPorFavorEspera() {
		return "请稍候。";
	}

	public String escanerMCreatorResultadosAnalisis() {
		return "MCreator 分析结果：";
	}

	public String escanerMCreatorNoSeEncontraronMods() {
		return "未找到 MCreator 模组。";
	}

	public String escanerMCreatorEscaneoCompletado() {
		return "扫描完成。";
	}

	public String escanerMCreatorErrorDuranteEscaneo() {
		return "扫描期间出错：";
	}

	public String escanerMCreatorCargando() {
		return "加载中...";
	}

	public String escanerMCreatorCompletado() {
		return "已完成";
	}

	public String escanerMCreatorError() {
		return "错误";
	}

	// Chinese (Simplified) (中文)
	@Override
	public String textoNormal() {
		return "普通文本";
	}

	@Override
	public String noSeEncontroConsolaParaArchivo() {
		return "未找到文件的控制台：";
	}

	@Override
	public String lineaSeleccionadaEnLectador() {
		return "读取器中选定的行：";
	}

	// Chinese (Simplified) (中文)
	@Override
	public String mensajeMotionBlurBufferCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "Motion Blur 可能存在问题。</b>"
				+ "<p>日志中包含对 <b>net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO</b> 的引用，"
				+ "以及错误 <b>java.lang.IllegalStateException: Buffer already closed</b>。</p>"
				+ "<p>这些行在日志中可能分开出现，但一起出现通常表明问题与 <b>Motion Blur</b> mod " + "或其对图形 shader/buffer 的处理有关。</p>"
				+ "<p><b>建议选项：</b></p>" + "<ul>" + "<li>尝试在不安装 <b>Motion Blur</b> 的情况下启动游戏。</li>"
				+ "<li>如果游戏在没有该 mod 的情况下正常启动，请保持卸载状态或寻找最新版本。</li>" + "<li>如果问题仍然存在，您也可以尝试禁用 shaders 或其他渲染 mods。</li>"
				+ "</ul>";
	}

	@Override
	public String nombreMotionBlurBufferCerrado() {
		return "潜在问题：Motion Blur";
	}

	// Chinese (Simplified) (中文)
	@Override
	public String mensajeGeneratorAcceleratorOwoVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "可能与 Generator Accelerator 存在冲突。</b>" + "<p>日志包含 <b>Found</b> 和 <b>Available</b> 签名之间的差异，以及来自 "
				+ "<b>Generator Accelerator</b> 的类，例如 <b>dev/sixik/generator_accelerator/common/features/FastTarget</b>。</p>"
				+ "<p>此错误很可能由 <b>Generator Accelerator</b> 引起。也可能与该 mod 和某些版本的 <b>owo-lib</b> 之间的不兼容有关。</p>"
				+ "<p><b>建议选项：</b></p>" + "<ul>" + "<li>尝试在不安装 <b>Generator Accelerator</b> 的情况下启动游戏。</li>"
				+ "<li>如果游戏正常启动，请保持该 mod 卸载或寻找其他版本。</li>" + "<li>尝试更新或更改 <b>owo-lib</b> 的版本，特别是如果有其他 mod 也依赖 owo。</li>"
				+ "<li>验证 <b>Generator Accelerator</b>、<b>owo-lib</b>、loader 和 Minecraft 版本之间是否兼容。</li>" + "</ul>"
				+ "<p>最可能的原因是 Generator Accelerator 试图应用一个签名与当前类或依赖项版本不匹配的修改。</p>";
	}

	@Override
	public String nombreGeneratorAcceleratorOwoVersion() {
		return "潜在冲突：Generator Accelerator 和 owo-lib";
	}

	// Chinese (Simplified) (中文)
	@Override
	public String mensajeFabricRenderingApiFaltaIndium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "缺少与 Fabric Rendering API 兼容的渲染器。</b>"
				+ "<p>日志包含一个错误，其中 <b>RendererAccess.getRenderer()</b> 返回 <b>null</b>， "
				+ "导致在尝试使用 <b>Renderer.materialFinder()</b> 时失败。</p>"
				+ "<p>当 <b>Fabric Rendering API</b> 未正确可用时，通常会发生此问题。 "
				+ "常见原因是使用了 <b>Sodium</b>，尤其是旧版本，它们会替换或禁用其他 mods 预期的渲染系统部分。</p>" + "<p><b>推荐解决方案：</b></p>" + "<ul>"
				+ "<li>安装 mod <b>Indium</b>。</li>"
				+ "<li>确保 <b>Indium</b> 与您的 <b>Sodium</b>、Fabric Loader 和 Minecraft 版本兼容。</li>"
				+ "<li>如果已安装 Indium，请更新 <b>Sodium</b>、<b>Indium</b> 和 <b>Fabric API</b>。</li>"
				+ "<li>如果问题仍然存在，请暂时在不使用 Sodium 的情况下进行测试，以确认故障与渲染器有关。</li>" + "</ul>"
				+ "<p>Indium 通常会在安装 Sodium 的同时，为依赖该系统的 mods 恢复与 Fabric Rendering API 的兼容性。</p>";
	}

	@Override
	public String nombreFabricRenderingApiFaltaIndium() {
		return "缺少 Indium / Fabric Rendering API";
	}

	// Chinese (Simplified) (中文)
	@Override
	public String mensajeEntradaDuplicadaIdModerno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "在 Minecraft 注册表中检测到重复条目。</b>" + "<p>日志包含类似于 <b>Duplicate entry on id</b> 的错误，例如 "
				+ "<b>current=maroon, previous=mint</b>。</p>" + "<p>在现代版本的 Minecraft 中，此类错误通常发生在两个 mod 尝试使用相同的内部 ID 注册 "
				+ "不同条目时。</p>" + "<p><b>建议选项：</b></p>" + "<ul>" + "<li>删除其中一个注册了重复条目的 mod。</li>"
				+ "<li>如果您识别出错误中提到的名称，请检查哪个 mod 添加了这些条目，并尝试在不安装该 mod 的情况下运行。</li>"
				+ "<li>如果您不认识这些名称，请使用侧边栏中的 <b>grepr</b> 工具。</li>"
				+ "<li>在 <b>grepr</b> 中，启用在压缩文件 <b>.jar</b>、<b>.zip</b> 和 <b>.fpm</b> 中的搜索。</li>"
				+ "<li>同时启用在 <b>二进制文件</b> 中的搜索，因为某些名称或 ID 可能位于编译后的类文件中。</li>" + "</ul>"
				+ "<p>搜索错误中提到的值，如 <b>maroon</b> 或 <b>mint</b>，以找到包含它们的 mod。</p>";
	}

	@Override
	public String nombreEntradaDuplicadaIdModerno() {
		return "Mod ID 中的重复条目";
	}

	// Chinese (Simplified) (中文)
	@Override
	public String nombreOpenGLMemoriaInsuficiente() {
		return "OpenGL – 显存不足";
	}

	@Override
	public String mensajeOpenGLMemoriaInsuficiente() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft 因显存不足产生了 OpenGL 错误。</b>" + "<p>游戏抛出：</p>" + "<code>GL_OUT_OF_MEMORY</code>"
				+ "<p>这通常意味着显卡或系统无法为纹理、着色器、模型、缓冲区或视觉效果预留足够的内存。</p>" + "<p><b>常见原因：</b></p>" + "<ul>"
				+ "<li>着色器（Shaders）过于繁重。</li>" + "<li>高分辨率资源包。</li>" + "<li>过多的视觉或渲染类 mod。</li>" + "<li>渲染距离过高。</li>"
				+ "<li>可用显存（VRAM）不足。</li>" + "<li>显卡驱动程序过时或不稳定。</li>" + "</ul>" + "<p><b>推荐解决方案：</b></p>" + "<ul>"
				+ "<li>暂时禁用着色器。</li>" + "<li>使用较低分辨率的资源包。</li>" + "<li>降低渲染和模拟距离。</li>"
				+ "<li>降低图形质量、阴影、粒子和 Mipmap。</li>" + "<li>更新显卡驱动程序。</li>" + "<li>关闭其他占用 GPU 或大量内存的程序。</li>" + "</ul>"
				+ "<p>如果错误是在安装着色器、纹理包或视觉 mod 后开始的，那么它很可能是罪魁祸首。</p>";
	}

	// Chinese (Simplified) (中文)
	@Override
	public String nombreErrorVerificacionBytecode() {
		return "VerifyError – 无效的 bytecode 或 mixin";
	}

	@Override
	public String mensajeErrorVerificacionBytecode(String ubicacion, String razon, String claseSospechosa) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft 发现了 bytecode 验证错误。</b>" + "<p>此问题通常发生在 bytecode 操作、转换器 (transformer) 或 mixin 失败时。</p>"
				+ "<p>游戏抛出：</p>" + "<code>java.lang.VerifyError</code>"
				+ (ubicacion.length() > 0 ? "<p><b>位置：</b></p><code>" + ubicacion + "</code>" : "")
				+ (razon.length() > 0 ? "<p><b>原因：</b></p><code>" + razon + "</code>" : "") + "<p><b>需要查找的内容：</b></p>"
				+ "<ul>" + "<li>检查错误位置。</li>" + "<li>查找 <code>Reason</code> 中提到的类型。</li>"
				+ "<li>检查堆栈跟踪以寻找可疑的 mod 类。</li>" + "<li>搜索错误附近的 mod 类名以获取线索。</li>" + "</ul>"
				+ "<p><b>Grepr 的推荐用法：</b></p>" + "<ul>" + "<li>在侧边栏打开 <b>Grepr</b>。</li>"
				+ "<li>启用在 <code>.jar</code>、<code>.zip</code> 或 <code>.fpm</code> 文件中搜索的选项。</li>"
				+ "<li>搜索类的基本名称，而不一定是完整包名。</li>" + "</ul>" + "<p>例如：如果出现 <code>paquete.Clase</code>，仅搜索：</p>" + "<code>"
				+ (claseSospechosa.length() > 0 ? claseSospechosa : "Clase") + "</code>" + "<p>这有助于找到包含或修改该类的 mod。</p>"
				+ "<p>常见解决方案：更新受影响的 mod，移除不兼容的 mod，检查主 mod 的附加组件，或尝试禁用对同一类使用 mixins/transformers 的 mod。</p>";
	}

}
