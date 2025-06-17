package com.asbestosstar.crashdetector.idioma;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;

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
    public String config_spongemixin_problematico() {
        return "<b style='color:#" + config.obtenerColorError() + "'>問題のあるSpongeMixin設定が検出されました: " + "</b>";
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

@Override
public String faltar_de_clases_advertencia() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>警告: 欠落しているクラスが検出されました:</b>";
}


@Override
public String noResultos() {
    return "<b style='color:#" + config.obtenerColorError() + "'>結果なし</b>";
}

@Override
public String ubicacionesDeLogs() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>ログの場所:</b>";
}

@Override
public String infoDeVerificaciones() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>こちらが検証結果です。ログの上部を修正することが最優先事項です。ゆっくり行ってください。</b>";
}

@Override
public String versionDeJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>1.17-1.20.4の場合はJava 17を使用し、それ以降のバージョンの場合はJava 21を使用してください。古いバージョンの場合はJava 8を使用してください。[ガイド](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). 問題が解決しない場合、一部のModにファイルが新しすぎたり古すぎたりすることが原因かもしれません。</b>";//TODO incluir el enalce japones
}

@Override
public String java22() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java 22以降は、ASMが古いことにより、1.20.5未満のMinecraftバージョンではほとんどのModローダーで動作しません。</b>" + versionDeJava();
}

@Override
public String javaObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Javaは古いです </b>" + versionDeJava();
}

@Override
public String jpms_modules_faltas(String mod_necesitas, String submod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>JPMSモジュール " + mod_necesitas + " を " + submod + " から取得する必要があります</b>";
}

@Override
public String null_pointer_error(String metodo, String objeto) {
    return "<b style='color:#" + config.obtenerColorError() + "'>" + metodo + " を呼び出せません。理由: " + objeto + " が null です</b>";
}

@Override
public String analisisAvanzado() {
    return "高度な分析";
}


@Override
public String seleccionarCarpeta() {
    return "フォルダを選択";
}

@Override
public String cadenaBusqueda() {
    return "検索文字列";
}

@Override
public String usarRegex() {
    return "正規表現を使用";
}

@Override
public String ignorarMayusculas() {
    return "大文字を無視";
}

@Override
public String buscar() {
    return "検索";
}

@Override
public String busquedaEnProgreso() {
    return "検索進行中";
}

@Override
public String noSeEncontraronResultados() {
    return "結果が見つかりませんでした";
}

@Override
public String errorBusqueda() {
    return "検索エラー";
}


@Override
public String noRegistroDeLauncher() {
    return "ランチャーログが見つかりませんでした！これにより調査が複雑になる可能性があります。\n"
            + "                \n"
            + "                正しいログを取得するには：\n"
            + "                - MultiMC/PolyMC/PrismLauncher: 注意: 自動検出されたログは正しくありません。\n"
            + "                  1. インスタンスインターフェースを開く\n"
            + "                  2. 「Minecraft Log」セクションに移動\n"
            + "                  3. 右クリックして内容をコピー\n"
            + "                - CurseForgeApp:\n"
            + "                  1. ランチャーをスキップせずにゲームを再起動\n"
            + "                  \n";
}

@Override
public String omitirYCerrar() {
    return "スキップして閉じる";
}

@Override
public String guardarYCerrar() {
    return "保存して閉じる";
}

@Override
public String pegaLosRegistrosAqui() {
    return "ログをここに貼り付け";
}

@Override
public String archivo() {
    return "ファイル";
}

@Override
public String incluir() {
    return "含める";
}

@Override
public String abrir() {
    return "開く";
}

@Override
public String endpointDeInforme() {
    return "レポートエンドポイント";
}

@Override
public String sitoDeLogging() {
    return "ログサイト：";
}

@Override
public String apiDeLogging() {
    return "ログAPI：";
}

@Override
public String anonimizarRegistros() {
    return "ログを匿名化 (ベータ)";
}

@Override
public String botonDeCompartirInforme() {
    return "レポートと選択したすべてのログを共有";
}

@Override
public String arco() {
    return "このダイアログでは、SecureLogger API を使用して securelogger.net にログを共有できます。"
            + "レポート共有ボタンを押すと、レポートは選択されたエンドポイント（デフォルトは asbestosstar.egoism.jp）に送信されます（下部で変更可能）。選択したすべてのログをレポートと一緒に共有できます。"
            + "アップロードしたくない場合は、このダイアログを使用しないでください！公式エンドポイント（https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb）ではレポートを処理せず、許可されていないリンクのみ削除します。コードはこちらにあります: https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb。これはクラッシュに関する情報とログへのリンクを表示するためにのみ使用されます。ただし、同じメソッドを持たないカスタムエンドポイントを使用することも可能です。現在、あなたはレポートサイト " 
            + Config.obtenerInstancia().obtenerSitoDeInformes() + " とログサイト " 
            + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + " を使用しています。個別のログ名の隣にある共有ボタンを押すことで、レポートなしで個々のログを共有することもできます。ログは選択されたログサイトに送られます。CrashDetector には、ユーザー名、UUID、アクセストークン、セッションID、IPアドレス、その他のデータを削除しようとするデフォルトのログ匿名化機能がありますが、完全ではありません。また、Modpackの作者が無効にすることもできます。この機能は画面下部のチェックボックスで有効または無効にできます。あなた自身がデータ管理者であり、どこにデータをアップロードするかを決定します。ログサイトは所有者がプライバシーのためにしばしば隠されている第三者によって運営されています。あなたは自分のデータの管理とそれに伴うリスクについて完全な責任を負います。CrashDetector の共有ダイアログは、それを管理するためのインターフェースに過ぎません。GDPR および ARCO について理解しておくことが重要です。";
}

@Override
public String enlaceDelReporte() {
    return "レポートリンク：";
}

@Override
public String guardarConfigDeCompartir() {
    return "共有設定を保存";
}



@Override
public String registroDemasiadoGrande() {
    return "ログがこのログサイトでは大きすぎます。別のサイトを選んで再度お試しください。";
}

@Override
public String errorConPublicarRegistro(String error) {
    return "ログの公開中にエラーが発生しました " + error;
}

@Override
public String apiDeRegistroNoExiste() {
    return "ログAPIが存在しません。設定でログAPIを変更してください。";
}

@Override
public String errorSSL() {
    return "SSLエラーが発生しています。これは古いバージョンのJavaで一般的であり、"
            + "デフォルトのMinecraftランチャー内のJava 8バージョンやsun.com、java.comのバージョンに含まれます。"
            + "このエラーは、MinecraftForgeインストーラーのJARファイル、デフォルトエンドポイントを使用した"
            + "CrashDetectorレポートの共有機能、インターネットを必要とする一部のMOD、およびいくつかのログサイトに影響を与えます。"
            + "これをレポートの共有中に遭遇した場合、スクリーンショットを添付し、"
            + "古いJava 8バージョンと互換性のあるログサイトを選択してください。";
}

@Override
public String errorJavaFMLVersion(String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "互換性のない JavaFML のバージョン: 必要なのは " + requerido 
         + " ですが、見つかったのは " + encontrado + "</b>";
}

@Override
public String errorJavaFML_MCForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "注意！ JavaFML は特定のバージョンの Minecraft Forge を必要とします</b>";
}

@Override
public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "JARファイル '" + archivoJar + "' は、言語プロバイダー '" + proveedor + "' のバージョン '"
         + requerido + "' 以上を必要としますが、見つかったのはバージョン '" + encontrado + "' です。</b>";
}

//@Override
//public String advertenciaMalwareFalso() {
//    return "<b style='color:#" + config.obtenerColorError() + "'>"
//         + "警告！ Crash Assistant は偽のマルウェア検出ツールです。意図的にゲームの起動をブロックし、ターゲットとなる MOD を使用してプレイする自由を無視します。 "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>MalwareMod.java のコードを見る</a>   "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>JarInJarHelper.java のコードを見る</a>. "
//         + "現時点ではこの MOD だけがリストに載っており、彼らは実際にはデフォルトのログサイトのみを対象としています。このサイトはユーザーが変更可能で、組み込みのログ共有機能を明示的に選択した場合にのみ動作します。CrashAssistant はどのログサイトが設定されているかチェックせず、また変更方法（共有ダイアログの下部にあるドロップダウン）を説明しません。設定されているサイトに関係なく、CrashAssistant はゲームの起動をブロックします。メッセージの中で彼らは独自の調査をするように言っていますが、その通りにしてください。CrashDetector と Crash Assistant のコードを調べ、何をしているのか理解してください。権威への信頼に頼らないでください。</b>";
//}

@Override
public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Mod '" + idMod + "' は、必要なクラスが見つからないため失敗しました: '"
         + claseNoEncontrada + "'. すべての依存関係が正しくインストールされていることを確認してください。</b>";
}


@Override
public String waterMediaTL() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Watermedia は TLauncher を使用したプレイをブロックしました。</b>";
}

@Override
public String optifineObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "あなたは古いバージョンのMinecraft用のOptifineを使用しています。使用しているMinecraftのバージョンに対応するOptifineを使用する必要があります。</b>";
}


@Override
public String servicioMLNoPudoCargar(String servicio) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "ModLauncher サービスを読み込めませんでした: </b>" + servicio + "。";
}


@Override
public String errorConJSONDeRegistro(String archivoJar, String recurso) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "JARファイル '" + archivoJar + "' からのJSONファイル '" + recurso + "' の解析中にエラーが発生しました。"
         + "登録に問題があります。</b>";
}

@Override
public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
    return "<span style='color:#" + config.obtenerColorError() + "'>" 
        + "エラー: モッド '" + modId + "' は '" + dependencia + "' のバージョン '" + requerido 
        + "' 以上を必要としますが、'" + actual + "' が見つかりました。"
        + "</span>";
}


@Override
public String gpu_no_compatible() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "お使いのGPUは、このゲームバージョンで必要なOpenGLのバージョンをサポートしていません。ドライバーを更新するか、グラフィックカードを変更してください。</b>";
}

@Override
public String recomendacionMemoria() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "ゲームに割り当てるメモリを増やしたり、MODやプラグインの使用を減らしてください。</b>";
}

@Override
public String error32BitMemoria() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "32ビットJVMが検出されました: 4GB以上のRAMを使用できません。 "
         + "利用可能なすべてのメモリを利用するには、64ビットJVMをインストールしてください。</b>";
}

@Override
public String permGenError() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "PermGenメモリの重大なエラーです。永続メモリ領域を増やしたり、クラスのロードを減らしてください。</b>";
}


public String errorCompatibilidadJava8() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 8と最新バージョンの間での互換性エラー</b>";
}

public String errorJava9NoSoportado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 9+はサポートされていません - 古いForgeバージョンにはJava 8を使用してください</b>";
}

public String errorJava8Requerido() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 8が必要です（バージョン52.0）。更新するか、正しく設定してください</b>";
}


@Override
public String errorDeBloqueTeselado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "重大な互換性エラー: このバージョンではブロックがサポートされていません。 "
         + "MODとサーバーのバージョンが互換性があるか確認してください</b>";
}

@Override
public String errorMonitorLWJGL() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "モニター設定エラー: "
         + "画面モードを設定できませんでした。 "
         + "確認してください:</b>"
         + "<br>- マルチモニター設定"
         + "<br>- グラフィックカードドライバーが最新かどうか"
         + "<br>- システムでサポートされている解像度";
}

@Override
public String errorOpcionesGCJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Javaオプションエラー: "
         + "ガベージコレクターオプションが競合しています。 "
         + "複数のGCアルゴリズムをJVMパラメータに組み合わせていないか確認してください</b>";
}

@Override
public String errorConfigMCForge() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Forge設定の重大なエラー: "
         + "設定ファイルが破損または不完全です。 "
         + "'config'フォルダを削除してゲームを再起動してください</b>";
}

@Override
public String problema_con_graficas_intel() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Intel HD Graphicsドライバーエラーが検出されました。解決策:</b>"
         + "<br>1. <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a>からIntelドライバーを更新してください（最小バージョン15.xx.xx.xx）"
         + "<br>2. Minecraftで: オプション → 動画 → 'Enable VBOs'と'VSync'を有効化"
         + "<br>3. ランチャーでゲームに1GB-2GBのRAMを割り当て"
         + "<br>4. 更新中に一時的にアンチウイルス/ファイアウォールを無効化";
}


public String nombre_de_faltar_de_clases_advertencia() {
    return "警告: 欠落しているクラスが検出されました";
}

public String nombre_de_bloque_teselado() {
    return "ブロックのレンダリングエラー";
}

public String nombre_de_contento_de_stacktrace() {
    return "スタックトレース解析";
}

public String nombre_de_cursed_consola() {
    return "不完全なCurseForgeコンソール";
}

public String nombre_de_early_window() {
    return "早期ウィンドウエラー (FMLEarlyWindow)";
}

public String nombre_de_drivers() {
    return "ビデオドライバの問題";
}

public String nombre_de_error_de_config_mcforge() {
    return "MCForge設定が破損しています";
}

public String nombre_de_error_de_monitor_lwjgl() {
    return "ディスプレイモードの失敗 (LWJGL)";
}

public String nombre_de_fabricmc_runtime_error_provided_by() {
    return "FabricMC初期化エラー";
}

public String nombre_de_falta_module_jmps() {
    return "欠落しているJPMSモジュール";
}

public String nombre_de_faltar_de_clases() {
    return "重大な欠落クラス";
}

public String nombre_de_faltas_dependencias_de_modlauncher() {
    return "ModLauncher依存関係が欠落しています";
}

public String nombre_de_java_versiones() {
    return "互換性のないJavaバージョン";
}

public String nombre_de_faltar_de_kubejs_resourcepack() {
    return "KubeJSリソースエラー";
}

public String nombre_de_lenguaje_proveedor_check() {
    return "互換性のない言語プロバイダ";
}

public String nombre_de_faltar_de_liyhostictchctov() {
    return "Litchhostエラー";
}

public String nombre_de_malware_falso_crash_assistant() {
    return "誤ったマルウェア検出";
}

public String nombre_de_mcforge_mod_sespechoso() {
    return "疑わしいMODが検出されました";
}

public String nombre_de_mods_duplicados_modlauncher() {
    return "ModLauncher内の重複したMOD";
}

public String nombre_de_modules_duplicados_jmps() {
    return "JPMSモジュールの競合";
}

public String nombre_de_necesitas_sodium() {
    return "IrisにはSodiumが必要です";
}

public String nombre_de_no_puede_analizar_json_de_registro() {
    return "JSONレジストリを解析できません";
}

public String nombre_de_no_tiene_memoria() {
    return "メモリ不足";
}

public String nombre_de_null_pointer() {
    return "ヌルポインターエラー (NullPointerException)";
}

public String nombre_de_opciones_java_gc_invalidas() {
    return "無効なJava GCオプション";
}

public String nombre_de_optifine_obsoleta() {
    return "古い/互換性のないOptiFine";
}

public String nombre_de_60_segundo_trick() {
    return "クリティカルサーバーティック (60秒)";
}

public String nombre_de_servicio_de_modlauncher_no_funciona() {
    return "ModLauncherサービスが失敗しました";
}

public String nombre_de_spongemixin_configs_problematicos() {
    return "問題のあるSpongeMixing設定";
}

public String nombre_de_theseus() {
    return "Theseusは互換性がありません";
}

public String nombre_de_watermedia_tl() {
    return "TLauncherはWATERMeDIAでサポートされていません";
}

@Override
public String auditorias_transformer() {
    return "トランスフォーマーオーディット";
}

@Override
public String auditorias_transformer_detectadas() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "これらはVanillaランチャーでのトランスフォーマーオーディットの内容結果です。一般的にStackTraceアナライザーほど正確ではありませんが、Vanillaランチャーには常に{}の内容があるとは限りません</b>";
}

@Override
public String descripcionEscanerMCreator() {
    return "これは、あなたのMODの中でMCreatorで作成されたものを検索します。ほとんどのMCreator製のMODは問題なく、優れたMODも多いですが、時々問題が発生し、悪い評判を持つことがあります。これにより、それらを特定するのに役立ちます。評価が高いものでも必ずしもMCreator製とは限らないことに注意してください。例えば、MCreatorとの統合が含まれている場合があります。";
}

@Override
public String escanear() {
    return "スキャン";
}

@Override
public String cargando() {
    return "読み込み中";
}

@Override
public String codigo() {
	// TODO Auto-generated method stub
	return "ja";
}

@Override
public String inicioApp() {
    return "ゲーム/アプリの開始";
}

@Override
public String adjustesCrashDetector() {
    return "クラッシュ検出設定";
}

@Override
public String confidencialidad() {
    return "プライバシー";
}

@Override
public String tooltip() {
    return "ツールチップ";
}

@Override
public String config() {
    return "設定";
}

@Override
public String abrirCarpeta() {
    return "フォルダを開く";
}

@Override
public String actualizar() {
    return "更新";
}

@Override
public String anadirRegistro() {
    return "ログを追加";
}

@Override
public String usarIdiomaDelSistema() {
    return "システム言語を使用";
}

@Override
public String volver() {
    return "戻る";
}

@Override
public String colorFondo() {
    return "背景色 (#RRGGBB):";
}

@Override
public String colorTexto() {
    return "テキストの色 (#RRGGBB):";
}

@Override
public String colorBoton() {
    return "ボタンの色 (#RRGGBB):";
}

@Override
public String colorCajaTexto() {
    return "テキストボックスの色 (#RRGGBB):";
}

@Override
public String colorEnlace() {
    return "リンクの色 (#RRGGBB):";
}

@Override
public String colorTitulosConsolas() {
    return "コンソールタイトルの色 (#RRGGBB):";
}

@Override
public String colorError() {
    return "エラーの色 (#RRGGBB):";
}

@Override
public String colorAdvertencia() {
    return "警告の色 (#RRGGBB):";
}

@Override
public String colorInfo() {
    return "情報の色 (#RRGGBB):";
}

@Override
public String colorTitulo() {
    return "タイトルの色 (#RRGGBB):";
}

@Override
public String colorEnlaceTexto() {
    return "リンクテキストの色 (#RRGGBB):";
}

@Override
public String transformacionDeMinecraftCodigo0() {
    return "失敗時にのみCrashDetectorを開く";
}

@Override
public String activar_parche() {
    return "パッチを有効化";
}

@Override
public String noHaySolucionDisponible() {
    return "利用可能な解決策はありません";
}

@Override
public String error() {
    return "エラー";
}

@Override
public String error_al_eliminar_jar() {
    return "Jarの削除中にエラーが発生しました";
}

@Override
public String jar_eliminado_exitosamente() {
    return "Jarが正常に削除されました";
}

@Override
public String exito() {
    return "成功";
}

@Override
public String eliminar() {
    return "削除";
}

@Override
public String error_al_eliminar_paquete() {
    return "パッケージの削除中にエラーが発生しました";
}

@Override
public String paquete_eliminado_exitosamente() {
    return "パッケージが正常に削除されました";
}

@Override
public String eliminar_paquete() {
    return "パッケージを削除";
}

@Override
public String no_se_encontraron_mods_con_paquete() {
    return "パッケージ付きのMODが見つかりませんでした";
}

@Override
public String no_se_puede_eliminar_paquete() {
    return "パッケージを削除できません";
}

@Override
public String eliminar_jar() {
    return "Jarを削除";
}

@Override
public String no_se_encontraron_mods_duplicados() {
    return "重複したMODは見つかりませんでした";
}


@Override
public String archivo_no_encontrado() {
    return "ファイルが見つかりません";
}

@Override
public String directorio_eliminado() {
    return "ディレクトリが削除されました";
}

@Override
public String error_al_eliminar_jar_anidado() {
    return "ネストされたJarの削除中にエラーが発生しました";
}

@Override
public String archivo_interno_no_encontrado() {
    return "内部ファイルが見つかりません";
}

@Override
public String archivo_eliminado() {
    return "ファイルが削除されました";
}

@Override
public String error_al_eliminar_archivo() {
    return "ファイルの削除中にエラーが発生しました";
}

@Override
public String archivo_externo_no_valido() {
    return "無効な外部ファイルです";
}

@Override
public String elemento_mod_eliminado() {
    return "Mod要素が削除されました";
}

@Override
public String error_al_reemplazar_jar_externo() {
    return "外部Jarの置き換え中にエラーが発生しました";
}

@Override
public String error_al_eliminar_elemento_mod() {
    return "Mod要素の削除中にエラーが発生しました";
}

@Override
public String error_al_eliminar_directorio() {
    return "ディレクトリの削除中にエラーが発生しました";
}

@Override
public String formato_invalido_para_jar_anidado() {
    return "ネストされたJarに無効な形式です";
}

@Override
public String jar_anidado_eliminado() {
    return "ネストされたJarが削除されました";
}

@Override
public String error_al_limpiar_temporales() {
    return "一時ファイルのクリア中にエラーが発生しました";
}


@Override
public String mensaje_de_trace_fatal_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>致命的なトレースメッセージ最後 (未翻訳):</b> ";
}

@Override
public String mensaje_de_trace_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>トレースメッセージ最後 (未翻訳):</b> ";
}

@Override
public String solucionParaAdvertenciaFaltasClases() {
    return "クラスが不足しています（警告）。一般的には問題ありませんが、常にそうとは限りません。不良なコアモッドや不足している依存関係がこの問題の一般的な原因です。";
}

@Override
public String solucionFaltasClases() {
    return "クラスが不足しています（致命的）。これは非常に重要です。不良なコアモッドや不足している依存関係がこの問題の一般的な原因です。";
}

@Override
public String solucionParaJavaInstallar() {
    return "両方のランチャーには正しいJavaバージョンがありますが、すべてではありません。システムのパッケージマネージャーまたはボタンを使用して、正しいJavaバージョンをインストールできます。";
}







}
