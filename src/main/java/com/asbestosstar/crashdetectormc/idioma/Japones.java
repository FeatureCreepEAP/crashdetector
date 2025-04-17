package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Config;
import com.asbestosstar.crashdetectormc.Idioma;

public class Japones implements Idioma {
    private final Config config = Config.obtenerInstancia();

    @Override
    public String carpeta_de_mods_no_valido() {
        return "<span style='color:#" + config.obtenerColorError() + "'>無効なmodsフォルダー</span>";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "<span style='color:#" + config.obtenerColorError() + "'>CrashDetectorのJARファイルが見つかりません</span>";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>PIDを検索中: " + String.valueOf(pid) + "</span>";
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid) + ") 終了しました！</span>";
    }

    @Override
    public String no_tenemos_jvm() {
        return "<span style='color:#" + config.obtenerColorError() + "'>JVMがありません</span>";
    }

    @Override
    public String probelma_con_graficas_ati() {
        return "<span style='color:#" + config.obtenerColorError() + "'>ドライバーの更新が役立つかもしれません。ドライバーが破損している場合、通常の更新方法では更新が見つからないことがあります。必ずリンクされたガイドに従ってください。重要: NVIDIAグラフィックスカードを使用している場合は、Windows設定とNVIDIAコントロールパネルで、Minecraft関連（Javaやランチャーなど）の設定をすべて高性能優先に設定してください。このガイドを参照してください: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>ドライバー更新ガイド</a></span>";
    }

    @Override
    public String probelma_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>一部の旧バージョンでは、Nouveauグラフィックスカードの初期ロード画面で問題が発生することがあります。</span>";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>グラフィックスドライバーに問題があります。AMD/ATIのGPUまたはAPUを使用している場合は、AMDグラフィックスドライバーを更新してください。NVIDIAグラフィックスカードを使用している場合は、ゲームとすべてのjavaw.exeインスタンスが独立グラフィックスカードを使用するように設定されていることを確認してください。このガイドを参照してください: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>ドライバー更新ガイド</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>FML初期ウィンドウの読み込みに失敗しました。修正するには、(.minecraft/config/fml.toml)に移動し、earlyWindowProviderを\"none\"に設定してください。Mac M1を使用している場合は、Intel x64版ではなくARM版Javaを使用していることを確認してください。また、ドライバーが古いことが原因である可能性があります。Windowsでこの設定を無効にしても改善しない場合は、このガイドを参照してください: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>ドライバー更新ガイド</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "<span style='color:#" + config.obtenerColorError() + "'>必要な依存関係が不足しています:</span>";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>" + linea.replace("Requested by", "要求元").replace("Expected range", "期待範囲") + "</span>";
    }

    @Override
    public String local_headless(String archivo) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>CrashDetectorレポートはこちら <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>レポートを見る</a></span>";
    }

    @Override
    public String texto_de_gui() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>これはCrashDetectorのGUIインターフェースです。ゲームが正常に終了した場合は、この画面を無視してください。</span>";
    }

    @Override
    public String texto_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorBoton() + "'>レポートを見る</span>";
    }

    @Override
    public String texto_debajo_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>ブラウザでローカルレポートを表示します。</span>";
    }

    @Override
    public String texto_de_buton_compartir_enlance() {
        return "レポートを共有";
    }

    @Override
    public String texto_debajo_de_buton_compartir_enlance() {
        return "レポートを共有します。ログはsecurelogger.netにアップロードされ、レポートは他のサイトにアップロードされます。";
    }

    @Override
    public String problematico_jar() {
        return "<b style='color:#" + config.obtenerColorError() + "'>問題のあるJARファイルが検出されました（FATALを優先し、次に高優先度と低優先度）:</b>";
    }

    @Override
    public String nivel() {
        return "<b style='color:#" + config.obtenerColorError() + "'> レベル:</b> ";
    }

    @Override
    public String possibladad_fatal() {
        return "<b style='color:#" + config.obtenerColorError() + "'>致命的な可能性:</b> ";
    }

    @Override
    public String modids_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>問題のあるModIDが検出されました（FATALを優先し、次に低優先度と低優先度）:</b>";
    }

    @Override
    public String packages_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>問題のあるパッケージが検出されました（FATALを優先し、次に低優先度と低優先度）:</b>";
    }

    @Override
    public String faltar_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>重要なクラスが不足していることが検出されました:</b>";
    }

    @Override
    public String corchetes_ondulados() {
        return "<b style='color:#" + config.obtenerColorError() + "'>{}内の内容（最も重要なものが上にあり、最初の20個のみ表示）:</b>";
    }

    @Override
    public String config_spongemixin_problematico(String archivo) {
        return "<b style='color:#" + config.obtenerColorError() + "'>問題のあるSpongeMixin設定が検出されました: " + "</b>" + archivo;
    }

    @Override
    public String module_resolution_exception(String modules, String paquete) {
        return "<span style='color:#" + config.obtenerColorError() + "'>Modsに重複するパッケージがあります: " + modules + " 重複パッケージ " + paquete.replace(".", "/") + "。WinRARや7zなどのファイルソフトウェアを使用してjar内のパッケージ（フォルダー）を削除するか、ファイル拡張子を.jarからzipに変更してフォルダーを削除し、再び.jarに戻すことで解決できます。</span>";
    }

    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>重複するModsが検出されました</b> " + linea.replace("from mod files", "modファイルから");
    }

    @Override
    public String mcforge_mod_suspechoso() {
        return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForgeが疑わしいmodに問題があることを検出しました:</b> ";
    }

    @Override
    public String lithostichctov() {
        return "<b style='color:#" + config.obtenerColorError() + "'>CTOVにはlithostitchedが必要です。こちらからインストールできます: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#" + config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
    }

    @Override
    public String necesitasSodiumParaIris() {
        return "<b style='color:#" + config.obtenerColorError() + "'>IrisシェーダーまたはOculusを使用するには、SODIUMまたは他のローダーの互換バージョン（Rubidium、Embedium、Bedium）が必要です</b>";
    }

    @Override
    public String kubeJSResourcePack(String mod_nombre) {
        return "<b style='color:#" + config.obtenerColorError() + "'>KubeJS拡張に問題があります </b>" + mod_nombre;
    }
}
