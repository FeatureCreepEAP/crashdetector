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
public String problema_con_graficas_ati() {
    return "<span style='color:#" + config.obtenerColorError() + "'>ATI/AMDのドライバーを更新すると問題が解決するかもしれません。このガイドを読んで修正してください: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>ドライバー更新ガイド</a> https://www.amd.com/ja/support/download/drivers.html ダウンロード </span>";
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
    
    @Override
public String problema_con_graficas_nvidia_windows_viejo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "Windows 11より前のバージョンでNVIDIAドライバーに関する問題が検出されました。"
            + "</span><br/><br/>"
            + "Minecraft（および現在のJVM）が専用のNVIDIA GPUを使用するようにするには、次の手順に従ってください：<br/><br/>"
            + "1. <strong>Java実行可能ファイルを特定します：</strong><br/>"
            + "   - このプログラムは次のJava実行可能ファイルを使用しています： "
            + obtenerRutaJava() + "<br/>"
            + "   - 特定のパスが表示されない場合、システム内で'java.exe'を検索してJava実行可能ファイルを見つけることができます。<br/><br/>"
            + "2. <strong>NVIDIAコントロールパネルを開きます：</strong><br/>"
            + "   - デスクトップを右クリックし、'NVIDIAコントロールパネル'を選択します。<br/><br/>"
            + "3. <strong>優先GPUを設定します：</strong><br/>"
            + "   - NVIDIAコントロールパネルで、'3D設定の管理'に移動します。<br/>"
            + "   - 'プログラム設定'オプションを選択します。<br/>"
            + "   - '追加'をクリックし、前述のJava実行可能ファイル（例：'java.exe'）を探します。<br/>"
            + "   - '高性能NVIDIAプロセッサ'を使用するように設定されていることを確認します。<br/><br/>"
            + "4. <strong>変更を保存します：</strong><br/>"
            + "   - 変更を適用し、NVIDIAコントロールパネルを閉じます。<br/><br/>"
            + "5. <strong>Minecraftを再起動します：</strong><br/>"
            + "   - Minecraftを再起動して変更を有効にします。<br/><br/>"
            + "Windows Server 2022またはWindows 10を使用している場合、最新のNVIDIAドライバーがインストールされている限り、これらの手順は有効です。<br/><br/>"
            + "注：NVIDIAコントロールパネルが見つからない場合は、NVIDIAドライバーが正しくインストールされていることを確認してください。";
}



@Override
public String problema_con_graficas_nvidia_windows_nuevo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "Windows 11/Server 2025以降でNVIDIAドライバーに関する問題が検出されました。"
            + "</span><br/><br/>"
            + "Minecraft（および現在のJVM）が専用のNVIDIA GPUを使用するようにするには、次の手順に従ってください：<br/><br/>"
            + "1. <strong>Java実行可能ファイルを特定します：</strong><br/>"
            + "   - このプログラムは次のJava実行可能ファイルを使用しています： "
            + obtenerRutaJava() + "<br/>"
            + "   - 特定のパスが表示されない場合、システム内で'java.exe'を検索してJava実行可能ファイルを見つけることができます。<br/><br/>"
            + "2. <strong>設定アプリを開きます：</strong><br/>"
            + "   - <code>Win + I</code>キーを押して設定アプリを開きます。<br/>"
            + "   - <strong>システム > ディスプレイ > グラフィックス</strong>に移動します。<br/><br/>"
            + "3. <strong>優先GPUを設定します：</strong><br/>"
            + "   - 'グラフィックス'セクションで、'デフォルトのグラフィックス設定'をクリックします。<br/>"
            + "   - 'デスクトップアプリ'を選択し、'参照'をクリックします。<br/>"
            + "   - 前述のJava実行可能ファイル（例：'java.exe'）を探して選択します。<br/>"
            + "   - 追加後、リストからJavaアプリケーションを選択し、'高性能（NVIDIA）'を使用するように設定します。<br/><br/>"
            + "4. <strong>変更を保存します：</strong><br/>"
            + "   - 変更を適用し、設定アプリを閉じます。<br/><br/>"
            + "5. <strong>Minecraftを再起動します：</strong><br/>"
            + "   - Minecraftを再起動して変更を有効にします。<br/><br/>"
            + "Windows 11またはWindows Server 2025+を使用している場合、最新のNVIDIAドライバーがインストールされている限り、これらの手順は有効です。<br/><br/>"
            + "注：グラフィックス設定オプションが見つからない場合、NVIDIAドライバーが正しくインストールされていることを確認してください。";
}




@Override
public String segundo60Tick() {
    return "<b style='color:#" + config.obtenerColorError() + "'>あなたのサーバーまたはワールドで60秒を超えるティックが発生しています。これは、MODがサーバーを遅くしているか、ハードウェアが弱すぎる可能性があります。</b>";
}


@Override
public String noTieneMemoria() {
    return "<b style='color:#" + config.obtenerColorError() + "'>十分なRAM/メモリがありません。より多くのメモリを割り当てる必要があります。</b>";
}

@Override
public String theseus() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Theseus/ModrinthAppを使用している場合、Theseusにはランチャーコンソールがないため、あまりお手伝いできません。Theseusには他にも問題があり、古いModローダーのバージョン、スパイウェア、不正なログなどがあります。また、Modrinth社も誠実ではありません。彼らはMod開発者がダウンロード数を増やすためにボットを使用していると虚偽の主張をし、収益化に関する声明を何度も変更しています。</b>";
}


@Override
public String noTieneConsolaDeLauncherCursedForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>launcher_log.txtがありません。エラーを検索するためにこのファイルが必要です。これは「ランチャースタートを省略」オプションによるものです。無効にしてください。</b>";
}






}
