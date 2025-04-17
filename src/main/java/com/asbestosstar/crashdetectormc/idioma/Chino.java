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
    public String probelma_con_graficas_ati() {
        return "<span style='color:#" + config.obtenerColorError() + "'>更新驱动程序可能会有所帮助。请注意，当驱动程序损坏时，常规更新方式可能找不到更新，因此请务必按照链接的指南操作。重要提示：如果您使用Nvidia显卡，请确保在Windows设置和Nvidia控制面板中将所有与Minecraft相关（如Java和启动器）的设置设为优先高性能。阅读此指南解决： <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>驱动程序更新指南</a></span>";
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
}
