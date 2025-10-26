package com.asbestosstar.crashdetector.idioma;

import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;

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
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") 終了しました！</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>JVMがありません</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>ATI/AMDのドライバーを更新すると問題が解決するかもしれません。このガイドを読んで修正してください: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>ドライバー更新ガイド</a> https://www.amd.com/ja/support/download/drivers.html ダウンロード </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>一部の旧バージョンでは、Nouveauグラフィックスカードの初期ロード画面で問題が発生することがあります。</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>グラフィックスドライバーに問題があります。AMD/ATIのGPUまたはAPUを使用している場合は、AMDグラフィックスドライバーを更新してください。NVIDIAグラフィックスカードを使用している場合は、ゲームとすべてのjavaw.exeインスタンスが独立グラフィックスカードを使用するように設定されていることを確認してください。このガイドを参照してください: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>ドライバー更新ガイド</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>FML初期ウィンドウの読み込みに失敗しました。修正するには、(.minecraft/config/fml.toml)に移動し、earlyWindowProviderを\"none\"に設定してください。Mac M1を使用している場合は、Intel x64版ではなくARM版Javaを使用していることを確認してください。また、ドライバーが古いことが原因である可能性があります。Windowsでこの設定を無効にしても改善しない場合は、このガイドを参照してください: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>ドライバー更新ガイド</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError() + "'>必要な依存関係が不足しています:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "要求元").replace("Expected range", "期待範囲") + "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>CrashDetectorレポートはこちら <a href='" + archivo
				+ "' style='color:#" + config.obtenerColorEnlace() + "'>レポートを見る</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>これはCrashDetectorのGUIインターフェースです。ゲームが正常に終了した場合は、この画面を無視してください。</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>レポートを見る</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>ブラウザでローカルレポートを表示します。</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "レポートを共有";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
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
	public String posibilidad_fatal() {
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
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>致命的なクラス (FATAL) が存在します。非常に深刻な問題です。原因は不正な CoreMod や致命的な依存関係などです。QuickFix を使って致命的クラスを持つ MOD を検索できます。検出された欠落している致命的クラス：</b>";
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
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>重複したパッケージを含むMODがあります。JARファイルから重複したパッケージ（フォルダ）を削除することで解決できます。WinRARや7zなどのアーカイブソフトでJARファイルを開くか、拡張子を.jarから.zipに変更してフォルダを削除し、再度.jarに戻すことも可能です。</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>重複するModsが検出されました</b> "
				+ linea.replace("from mod files", "modファイルから");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForgeが疑わしいmodに問題があることを検出しました:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOVにはlithostitchedが必要です。こちらからインストールできます: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>IrisシェーダーまたはOculusを使用するには、SODIUMまたは他のローダーの互換バージョン（Rubidium、Embedium、Bedium）が必要です</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>KubeJS拡張に問題があります </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Windows 11より前のバージョンでNVIDIAドライバーに関する問題が検出されました。" + "</span><br/><br/>"
				+ "Minecraft（および現在のJVM）が専用のNVIDIA GPUを使用するようにするには、次の手順に従ってください：<br/><br/>"
				+ "1. <strong>Java実行可能ファイルを特定します：</strong><br/>" + "   - このプログラムは次のJava実行可能ファイルを使用しています： "
				+ obtenerRutaJava() + "<br/>"
				+ "   - 特定のパスが表示されない場合、システム内で'java.exe'を検索してJava実行可能ファイルを見つけることができます。<br/><br/>"
				+ "2. <strong>NVIDIAコントロールパネルを開きます：</strong><br/>"
				+ "   - デスクトップを右クリックし、'NVIDIAコントロールパネル'を選択します。<br/><br/>" + "3. <strong>優先GPUを設定します：</strong><br/>"
				+ "   - NVIDIAコントロールパネルで、'3D設定の管理'に移動します。<br/>" + "   - 'プログラム設定'オプションを選択します。<br/>"
				+ "   - '追加'をクリックし、前述のJava実行可能ファイル（例：'java.exe'）を探します。<br/>"
				+ "   - '高性能NVIDIAプロセッサ'を使用するように設定されていることを確認します。<br/><br/>" + "4. <strong>変更を保存します：</strong><br/>"
				+ "   - 変更を適用し、NVIDIAコントロールパネルを閉じます。<br/><br/>" + "5. <strong>Minecraftを再起動します：</strong><br/>"
				+ "   - Minecraftを再起動して変更を有効にします。<br/><br/>"
				+ "Windows Server 2022またはWindows 10を使用している場合、最新のNVIDIAドライバーがインストールされている限り、これらの手順は有効です。<br/><br/>"
				+ "注：NVIDIAコントロールパネルが見つからない場合は、NVIDIAドライバーが正しくインストールされていることを確認してください。";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Windows 11/Server 2025以降でNVIDIAドライバーに関する問題が検出されました。" + "</span><br/><br/>"
				+ "Minecraft（および現在のJVM）が専用のNVIDIA GPUを使用するようにするには、次の手順に従ってください：<br/><br/>"
				+ "1. <strong>Java実行可能ファイルを特定します：</strong><br/>" + "   - このプログラムは次のJava実行可能ファイルを使用しています： "
				+ obtenerRutaJava() + "<br/>"
				+ "   - 特定のパスが表示されない場合、システム内で'java.exe'を検索してJava実行可能ファイルを見つけることができます。<br/><br/>"
				+ "2. <strong>設定アプリを開きます：</strong><br/>" + "   - <code>Win + I</code>キーを押して設定アプリを開きます。<br/>"
				+ "   - <strong>システム > ディスプレイ > グラフィックス</strong>に移動します。<br/><br/>"
				+ "3. <strong>優先GPUを設定します：</strong><br/>" + "   - 'グラフィックス'セクションで、'デフォルトのグラフィックス設定'をクリックします。<br/>"
				+ "   - 'デスクトップアプリ'を選択し、'参照'をクリックします。<br/>" + "   - 前述のJava実行可能ファイル（例：'java.exe'）を探して選択します。<br/>"
				+ "   - 追加後、リストからJavaアプリケーションを選択し、'高性能（NVIDIA）'を使用するように設定します。<br/><br/>"
				+ "4. <strong>変更を保存します：</strong><br/>" + "   - 変更を適用し、設定アプリを閉じます。<br/><br/>"
				+ "5. <strong>Minecraftを再起動します：</strong><br/>" + "   - Minecraftを再起動して変更を有効にします。<br/><br/>"
				+ "Windows 11またはWindows Server 2025+を使用している場合、最新のNVIDIAドライバーがインストールされている限り、これらの手順は有効です。<br/><br/>"
				+ "注：グラフィックス設定オプションが見つからない場合、NVIDIAドライバーが正しくインストールされていることを確認してください。";
	}

	@Override
	public String segundo60Tick() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>あなたのサーバーまたはワールドで60秒を超えるティックが発生しています。これは、MODがサーバーを遅くしているか、ハードウェアが弱すぎる可能性があります。</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError() + "'>十分なRAM/メモリがありません。より多くのメモリを割り当てる必要があります。</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Theseus/ModrinthAppを使用している場合、Theseusにはランチャーコンソールがないため、あまりお手伝いできません。Theseusには他にも問題があり、古いModローダーのバージョン、スパイウェア、不正なログなどがあります。また、Modrinth社も誠実ではありません。彼らはMod開発者がダウンロード数を増やすためにボットを使用していると虚偽の主張をし、収益化に関する声明を何度も変更しています。</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>launcher_log.txtがありません。エラーを検索するためにこのファイルが必要です。これは「ランチャースタートを省略」オプションによるものです。無効にしてください。</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>警告：クラスが見つかりません（警告レベル）。致命的なエラーとは異なり、通常は問題ありませんが、場合によっては影響が出ることもあります。原因として不正な CoreMod や不足した依存関係が考えられます。QuickFix を使用して、不足しているクラスを持つ MOD を検索できます。検出された不足クラス：</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>結果なし</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>ログの場所:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ここにあなたのチェック結果を示します。スタックトレースの上部を修正することが最優先です。ゆっくり進めてください。正しい原因は通常チェック1または2にあります。それ以外（エラー3以上）は確認用には使えますが、多くの場合連鎖的なエラーなので無視しても構いません。エラーは層状に発生するため、今日この特定のエラーを解決しても、明日まったく関係のない新しいエラーが現れる可能性があります。あるエラーが別のエラーの表示を妨げていることがよくあるからです。</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>1.17-1.20.4の場合はJava 17を使用し、それ以降のバージョンの場合はJava 21を使用してください。古いバージョンの場合はJava 8を使用してください。[ガイド](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). 問題が解決しない場合、一部のModにファイルが新しすぎたり古すぎたりすることが原因かもしれません。</b>";// TODO
																																																												// incluir
																																																												// el
																																																												// enalce
																																																												// japones
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22以降は、ASMが古いことにより、1.20.5未満のMinecraftバージョンではほとんどのModローダーで動作しません。</b>" + versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Javaは古いです </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>JPMSモジュール " + mod_necesitas + " を " + submod
				+ " から取得する必要があります</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + metodo + " を呼び出せません。理由: " + objeto
				+ " が null です</b>";
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
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado()
				+ " を使用しています。個別のログ名の隣にある共有ボタンを押すことで、レポートなしで個々のログを共有することもできます。ログは選択されたログサイトに送られます。CrashDetector には、ユーザー名、UUID、アクセストークン、セッションID、IPアドレス、その他のデータを削除しようとするデフォルトのログ匿名化機能がありますが、完全ではありません。また、Modpackの作者が無効にすることもできます。この機能は画面下部のチェックボックスで有効または無効にできます。あなた自身がデータ管理者であり、どこにデータをアップロードするかを決定します。ログサイトは所有者がプライバシーのためにしばしば隠されている第三者によって運営されています。あなたは自分のデータの管理とそれに伴うリスクについて完全な責任を負います。CrashDetector の共有ダイアログは、それを管理するためのインターフェースに過ぎません。GDPR および ARCO について理解しておくことが重要です。";
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
				+ "これをレポートの共有中に遭遇した場合、スクリーンショットを添付し、" + "古いJava 8バージョンと互換性のあるログサイトを選択してください。";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "互換性のない JavaFML のバージョン: 必要なのは " + requerido
				+ " ですが、見つかったのは " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "注意！ JavaFML は特定のバージョンの Minecraft Forge を必要とします</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JARファイル '" + archivoJar + "' は、言語プロバイダー '"
				+ proveedor + "' のバージョン '" + requerido + "' 以上を必要としますが、見つかったのはバージョン '" + encontrado + "' です。</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Mod '" + idMod
				+ "' は、必要なクラスが見つからないため失敗しました: '" + claseNoEncontrada + "'. すべての依存関係が正しくインストールされていることを確認してください。</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ModLauncher サービスを読み込めませんでした: </b>" + servicio
				+ "。";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JARファイル '" + archivoJar + "' からのJSONファイル '"
				+ recurso + "' の解析中にエラーが発生しました。" + "登録に問題があります。</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "エラー: モッド '" + modId + "' は '" + dependencia
				+ "' のバージョン '" + requerido + "' 以上を必要としますが、'" + actual + "' が見つかりました。" + "</span>";
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
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "32ビットJVMが検出されました: 4GB以上のRAMを使用できません。 "
				+ "利用可能なすべてのメモリを利用するには、64ビットJVMをインストールしてください。</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "PermGenメモリの重大なエラーです。永続メモリ領域を増やしたり、クラスのロードを減らしてください。</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Java 8と最新バージョンの間での互換性エラー</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+はサポートされていません - 古いForgeバージョンにはJava 8を使用してください</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Java 8が必要です（バージョン52.0）。更新するか、正しく設定してください</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "重大な互換性エラー: このバージョンではブロックがサポートされていません。 "
				+ "MODとサーバーのバージョンが互換性があるか確認してください</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "モニター設定エラー: " + "画面モードを設定できませんでした。 "
				+ "確認してください:</b>" + "<br>- マルチモニター設定" + "<br>- グラフィックカードドライバーが最新かどうか" + "<br>- システムでサポートされている解像度";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Javaオプションエラー: " + "ガベージコレクターオプションが競合しています。 "
				+ "複数のGCアルゴリズムをJVMパラメータに組み合わせていないか確認してください</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Forge設定の重大なエラー: " + "設定ファイルが破損または不完全です。 "
				+ "'config'フォルダを削除してゲームを再起動してください</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Intel HD Graphicsドライバーエラーが検出されました。解決策:</b>"
				+ "<br>1. <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a>からIntelドライバーを更新してください（最小バージョン15.xx.xx.xx）"
				+ "<br>2. Minecraftで: オプション → 動画 → 'Enable VBOs'と'VSync'を有効化" + "<br>3. ランチャーでゲームに1GB-2GBのRAMを割り当て"
				+ "<br>4. 更新中に一時的にアンチウイルス/ファイアウォールを無効化";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "警告: 欠落しているクラスが検出されました";
	}

	public String nombre_de_bloque_teselado() {
		return "ブロックのレンダリングエラー";
	}

	public String nombre_de_contenido_de_stacktrace() {
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
	public String ajustesCrashDetector() {
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
		return "WaifuNeoForgeデータベースを検索してMODを見つけることができます。ゲームのバージョン、MODローダー、クラスを選択してください。最も近い組み合わせを使用してください。1分間に1回検索できます。";
	}

	@Override
	public String solucionFaltasClases() {
		return "WaifuNeoForgeデータベースを検索してMODを見つけることができます。ゲームのバージョン、MODローダー、クラスを選択してください。最も近い組み合わせを使用してください。1分間に1回検索できます。";
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "両方のランチャーには正しいJavaバージョンがありますが、すべてではありません。システムのパッケージマネージャーまたはボタンを使用して、正しいJavaバージョンをインストールできます。";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>アニメーションが不足しているMOD: " + "</b>";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException（要素なし例外）アニメーション不足";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "削除するMODが見つかりませんでした";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "競合するGCオプションを-XX:+UseG1GCに置き換える";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "-Xmxオプションを使用してヒープメモリのサイズを増加させます。";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "-XX:MaxPermSizeオプションを使用してパーマネント領域メモリのサイズを増加させます。";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "利用可能なメモリを増やすために64ビットJVMを使用します。";
	}

	@Override
	public String optimizarCodigo() {
		return "コードを最適化し、メモリ使用量を減らしてパフォーマンスを向上させます。";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "アプリケーションの一時停止を減らすため、効率的なガベージコレクターを使用します。";
	}

	@Override
	public String modulos() {
		return "モジュール";
	}

	@Override
	public String paquete() {
		return "パッケージ";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "IDが不足しています。よくある原因はMODの欠落やアイテムデータの欠損です。一般的なデータフォルダは datafiedcontents/ や kubejs/、または他のMODフォルダです。";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "不一致なレコード";
	}

	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError() + "'>プラグイン 'AuthMe' のロードに失敗し、サーバーが終了しました。</b> ";
	}

	public String nombreProblemaCierreAuthMe() {
		return "AuthMeによるシャットダウン問題";
	}

	public String solucionCierreAuthMe() {
		return "'stopServer'ルールが'true'に変更されました。";
	}

	public String solucionConfigurarPluginAuthMe() {
		return "プラグイン AuthMe を設定する (plugins/AuthMe/config.yml)";
	}

	public String solucionInstalarVersionDiferenteAuthMe() {
		return "'AuthMe' プラグインの別のバージョンをインストールする";
	}

	public String solucionEliminarPluginAuthMe() {
		return "プラグイン 'AuthMe' を削除する";
	}

	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ワールド '" + nombreMundo
				+ "' はエラーを含み、破損している可能性があるためロードできませんでした。</b> ";
	}

	@Override
	public String nombreProblemaCargaMultiverso() {
		return "Multiverseワールド読み込みエラー";
	}

	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "'" + nombreMundo + "' ワールドを修復してください。例として Minecraft Region Fixer や MCEdit を使用できます。";
	}

	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "'" + nombreMundo + "' ワールドフォルダを削除してください。";
	}

	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError() + "'>拡張機能 'PermissionsEx' の設定が無効です。</b> ";
	}

	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "PermissionsEx 設定の問題";
	}

	@Override
	public String solucionConfigurarPermissionsEx() {
		return "プラグイン PermissionsEx を設定する (plugins/PermissionsEx/permissions.yml)";
	}

	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "プラグイン 'PermissionsEx' を削除する";
	}

	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>プラグイン名 '" + nombrePlugin + "' に該当するファイルが複数あります: '"
				+ primerPath + "' と '" + segundoPath + "'。</b> ";
	}

	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "プラグイン名の競合問題";
	}

	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "プラグイン '" + nombrePlugin + "' を削除してください。";
	}

	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ワールドのチャンク読み込み中に例外が発生しました。</b> ";
	}

	@Override
	public String nombreProblemaCargaChunk() {
		return "チャンク読み込みエラー";
	}

	@Override
	public String solucionEliminarChunk() {
		return "問題のあるチャンクを削除してください。例として MCEdit を使うか、リージョンファイルを削除します。";
	}

	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>プラグイン '" + nombrePlugin + "' はコマンド '/" + comando
				+ "' を実行できません。</b> ";
	}

	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "プラグインコマンド実行時の例外";
	}

	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "'" + nombrePlugin + "' プラグインの別バージョンをインストールする";
	}

	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>プラグイン '" + nombrePlugin + "' は依存性 '" + dependencia
				+ "' を必要とします。</b> ";
	}

	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>プラグイン '" + nombrePlugin + "' は次の依存性が不足しています: "
				+ deps.toString() + "。</b> ";
	}

	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "不足しているプラグイン依存性";
	}

	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "'" + nombrePlugin + "' プラグインをインストールしてください。";
	}

	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>プラグイン '" + nombrePlugin
				+ "' は現在のサーバーと互換性のない API バージョン '" + versionAPI + "' を必要とします。</b> ";
	}

	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "API バージョンが互換性なし";
	}

	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "サーバーソフトウェアのバージョン '" + version + "' をインストールしてください。";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ワールド '" + nombreMundo
				+ "' は他のワールドと重複しており、読み込めません。</b> ";
	}

	@Override
	public String nombreProblemaMundoDuplicado() {
		return "重複するワールド";
	}

	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "'" + nombreMundo + "' ワールド内の 'uid.dat' ファイルを削除してください。";
	}

	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "'" + nombreMundo + "' ワールドフォルダを削除してください。";
	}

	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "<b style='color:#" + config.obtenerColorError() + "'>座標 " + coords + " のブロックエンティティ '" + nombre
				+ "'（タイプ: '" + tipo + "'）がティッキング中にエラーを引き起こしています。</b> ";
	}

	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "問題のあるブロックエンティティ";
	}

	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "MCEdit または直接編集で、座標 " + coords + " の '" + nombre + "' エンティティを削除してください。";
	}

	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>モッド '" + nombreMod
				+ "' に複数のバージョンがインストールされています。</b>";
	}

	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "Fabric 内の重複するモッド";
	}

	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "重複したモッドファイルを削除してください: " + rutaMod;
	}

	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>モッド '" + primerMod + "' と '" + segundoMod
				+ "' は互いに非互換です。</b>";
	}

	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "Fabric 内の非互換モッド";
	}

	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "'" + nombreMod + "' モッドを削除してください";
	}

	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>モッド '" + nombreMod + "' に重大なエラーが発生し、実行できません。</b> ";
	}

	@Override
	public String nombreProblemaModFatal() {
		return "重大なエラーを含むモッド";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>次のモッドが必要ですがインストールされていません: " + deps.toString()
				+ "。</b>";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>モッド '" + nombreMod + "' は '" + dependencia
					+ "' モッドの依存があります。</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>モッド '" + nombreMod + "' は '" + dependencia
					+ "' モッドのバージョン " + version + " を必要とします。</b>";
		}
	}

	@Override
	public String nombreProblemaDependenciaMod() {
		return "不足しているモッドの依存関係";
	}

	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "'" + nombreMod + "' モッドをインストールしてください";
	}

	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "'" + nombreMod + "' モッドのバージョン " + version + " をインストールしてください";
	}

	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>プラグイン '" + nombrePlugin
				+ "' は Folia のリージョナルティッキングと非互換です。</b> ";
	}

	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("以下のプラグインは Folia のリージョナルティッキングと非互換です: ");

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
		return "リージョナルティッキング非対応プラグイン";
	}

	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "リージョナルティッキングなしのソフトウェアをインストールしてください。例: " + software;
	}

	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>'" + nombreMod + "' モッドがデータパックにありません。</b>";
	}

	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("次のモッドがデータパックにありません: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" および ");
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
		return "データパック内の不足しているモッド";
	}

	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombreMod + " モッドがエラーを発生させました。</b>";
	}

	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("以下のモッドがエラーを発生させました：");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" および ");
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
		return "Forge モッドの例外";
	}

	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "'" + nombreMod + "' モッドの別のバージョンをインストールしてください";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombreMod + "' モッドは Minecraft " + versionMC
				+ " と非互換です。</b>";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("以下のモッドは対応する Minecraft バージョンと非互換です：");

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
		return "Minecraft と非互換なモッド";
	}

	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "Minecraft " + versionMC + " 対応の Forge をインストールしてください";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>モッド '" + nombreMod
				+ "' が見つかりません。世界やプラグインのロードに必要です。</b>";
	}

	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "不足しているモッド";
	}

	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>この世界は '" + nombreMod
				+ "' モッドを使用して保存されていますが、現在見つかりません。</b>";
	}

	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("この世界は以下のモッドで保存されていますが、それらが見つかりません: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" および ");
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
	public String nombreProblemaWorldModFaltante() {
		return "世界内の不足モッド";
	}

	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>この世界は '" + nombreMod + "' モッドのバージョン "
				+ versionEsperada + " で保存されていますが、現在使われているのはバージョン " + versionActual + " です。</b>";
	}

	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("保存された世界に含まれるモッドのバージョンが一致しません: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" および ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append("（保存時: ").append(versionesEsperadas.get(i)).append(", 現行: ").append(versionesActuales.get(i))
					.append("）");
		}

		sb.append("。</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaVersionModMundo() {
		return "保存された世界のモッドバージョン不一致";
	}

	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>あなたはより新しいバージョンの Minecraft で作成された世界を読み込もうとしています。</b>";
	}

	@Override
	public String nombreProblemaVersionDowngrade() {
		return "新しいバージョンの世界をロードしようとした";
	}

	@Override
	public String solucionVersionDowngrade() {
		return "最新バージョンの Minecraft をインストールしてください。";
	}

	@Override
	public String solucionGenerarNuevoMundo() {
		return "新しい世界を生成してください。";
	}

	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>プラグイン '" + nombrePlugin + "' は以下の依存があります：'"
				+ dependencia + "'。</b>";
	}

	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("以下のプラグインは未インストールの依存があります：");

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
		return "不足しているプラグイン依存性";
	}

	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>プラグイン '" + nombrePlugin
				+ "' はサーバーの現在のバージョンと非互換です。</b>";
	}

	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("以下のプラグインはサーバーの現在のバージョンと非互換です：");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" および ");
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
		return "PocketMine-MP と非互換なプラグイン";
	}

	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>プラグイン '" + nombrePlugin
				+ "' が実行中にエラーを発生させました。</b>";
	}

	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("以下のプラグインが実行中にエラーを発生させました: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" および ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append("。</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaPluginEjecucion() {
		return "実行エラーのあるプラグイン";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "マルチスレッド LegacyRandomSource";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>複数のスレッドが LegacyRandomSource クラスにアクセスしています。詳細は 'Unsafe World Random Access Detector' または 'C2ME' モッドで確認できます。</b>";
	}

	@Override
	public String desdeUltimoExito() {
		return "前回の成功以降";
	}

	@Override
	public String noHayCambios() {
		return "変更なし";
	}

	@Override
	public String desdeUltimoIntento() {
		return "前回の試行以降";
	}

	@Override
	public String fallo() {
		return "失敗";
	}

	@Override
	public String diferentesDeLasMods() {
		return "MODと異なる";
	}

	@Override
	public String historialDeMods() {
		return "MOD履歴";
	}

	@Override
	public String archivo0() {
		return "ファイル0";
	}

	@Override
	public String archivo1() {
		return "ファイル1";
	}

	@Override
	public String comparar() {
		return "比較";
	}

	@Override
	public String seleccionarDosArchivos() {
		return "2つのファイルを選択してください";
	}

	@Override
	public String archivoExito() {
		return "成功したファイル";
	}

	@Override
	public String archivoFalla() {
		return "失敗したファイル";
	}

	@Override
	public String errorComparandoArchivos() {
		return "ファイルの比較中にエラーが発生しました";
	}

	@Override
	public String comparando() {
		return "比較中";
	}

	@Override
	public String con() {
		return "と";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>" + "<p><b>MOD履歴比較パネル</b></p>"
				+ "<p>このパネルでは、異なるセッションのMODリストを比較できます。左側からファイル、右側からもう一つのファイルを選択し、変更点を確認してください。</p>"

				+ "<h3>使い方:</h3>" + "<ol>" + "<li><b>ファイル選択:</b> ファイル横のラジオボタンをクリックします。"
				+ " <span style='color: #4CAF50; font-weight: bold;'>.suceso</span> で終わるファイルは成功したセッション、"
				+ " <span style='color: #F44336; font-weight: bold;'>.falla</span> は失敗したセッションです。</li>"

				+ "<li><b>自動比較:</b>「Compare」ボタンを押すと、システムが両方のリストを解析して追加(+)または削除(-)されたMODを表示します。</li>"

				+ "<li><b>結果の表示:</b> 結果は色分けされたHTML形式で表示されます: " + "<ul>"
				+ "<li><span style='color: green;'>+ 追加されたMOD</span></li>"
				+ "<li><span style='color: red;'>- 削除されたMOD</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>主な機能:</h3>" + "<ul>" + "<li>成功/失敗ファイルの任意の組み合わせに対応</li>" + "<li>正確な追跡のための双方向差分表示</li>"
				+ "<li>長いMODリスト用にスクロール機能付き</li>" + "<li>視覚的理解を促進する説明画像付き</li>" + "</ul>"

				+ "<p>設定内の変更を追跡できるよう <3️ を込めて開発しました。</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "IPv6 に関連する問題が発生している可能性があります。" + "解決策は以下の2つです："
				+ "1) ランチャーに JVM 引数 <code>-Djava.net.preferIPv4Stack=true</code> を追加するか、"
				+ "2) CrashDetector の 'QuickFix' ボタンをクリックして自動的にこの設定を有効にするパッチを適用してください。" + "</b>";
	}

	@Override
	public String parcheIPv4() {
		return "IPv4/6 パッチ";
	}

	@Override
	public String carpetaHMCL() {
		return "HMCL フォルダ (HelloMineCraftLauncher 専用)";
	}

	@Override
	public String descripcionCurseforge() {
		return "注意: 設定 > Minecraft で「ランチャーをスキップ」が有効になっているとログは生成されません。";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/派生ソフト: インスタンスを右クリックし、「インスタンスの編集」を選択します。開いたウィンドウ内で「Minecraft ログ」や類似するセクションを探してください。<br>"
				+ "これらのログにはエラー診断に不可欠な標準出力 (STDOUT) が含まれています。";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher): HMCL のインストール先フォルダを選択し、その中の 「.hmcl」 フォルダを選んでください。HMCL のログはここに保存されており、重要なエラー情報を含みます。<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix：ランチャーには詳細ログを表示する開発者タブがあります。ランチャー設定メニュー内にあります。";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher：ログはポップアップウィンドウに表示されます。コピーおよびアップロードボタンがあり、ゲーム起動時に自動生成される重要な診断情報が含まれています。";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher：インスタンスを右クリックし、「設定」を選択します。その後、ログセクションから標準出力 (STDOUT) の重要な情報を確認できます。";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "Markdown形式のリンク：Markdown形式のログが含まれるリンクを貼り付けてください。システムがログファイルへのリンク(latest.log, launcher_log.txt, debug.logなど)を自動抽出して解析します。";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "ランチャーログが見つかりません";
	}

	@Override
	public String imagenNoEncontrada() {
		return "画像が見つからない";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "汎用：使用しているランチャーの種類を選択してください。ランチャーログ(launcher_log.txt, stdoutなど)は latest.log に現れない重大なエラー情報を含みます。CrashDetectorがあなたのランチャーログを読み込めない可能性があります — ログファイルが生成されていない場合、手動でログを貼り付けなくてはいけません。<br>"
				+ "詳細については <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">この問題</a> を参照してください。これらのログには多くのエラー診断に必要な標準出力 (STDOUT) が含まれます。";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Create からクラスが見つかりません。Create は大幅に変更され、多くのクラスが削除されました。特に Create 6（2025年2月）以降、古いバージョンの Create 用アドオンは動作しません。QuickFix ではこの問題に対処できません。Create のアドオンを更新するか、不要なアドオンを削除するか、使用したいアドオンに合った正しい Create バージョンを使用する必要があります。</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>EpicFight からクラスが見つかりません。EpicFight は大幅に変更され、多くのクラスが削除されました。QuickFix ではこの問題に対処できません。EpicFight のアドオンを更新するか、不要なアドオンを削除するか、使用したいアドオンに合った正しい EpicFight バージョンを使用する必要があります。</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>OpenJ9 を使用していますが、このアプリケーションは OpenJ9 をサポートしていません。多くのアプリ（このアプリも含む）は JVM 実装の違いにより OpenJ9 に対応していません。QuickFix ではこの問題を自動的に解決できません。Oracle JDK、OpenJDK Hotspot、またはその他の推奨代替 JDK をインストールする必要があります。</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>このアプリのバージョンは正常に動作するために Java 11 (JDK 11) が必要です。現在、互換性のない古いバージョンの Java を使用しています。QuickFix では Java のアップグレードは自動的に行えません。解決策に記載されたリンクから JDK 11 またはそれ以上の互換性のあるバージョンをダウンロード・インストールする必要があります。</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>メモリを過剰に割り当てており、システムリソースが不足しています。これは、システムの実装容量を超える RAM を指定した場合や、大容量メモリを扱えない 32 ビット JVM を使用している場合に発生します。</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>この問題を解決するには、アプリに割り当てるメモリ量を減らしてください。推奨される最大値はシステムによりますが、一般的に全 RAM の 70～80％を超えないようにしてください。32 ビット JVM を使用している場合、物理 RAM の量に関係なく、上限は約 2～3 GB です。</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>アプリに割り当てられたヒープメモリを減らすには、ランチャーの設定を開き、RAM オプションを探します。最大値 (Xmx) を適切な量に下げてください。例：8GB の RAM がある場合、3～4GB を割り当てます。16GB の場合、6～8GB が適切です。OS や他のプログラムのために十分なメモリを残すことを忘れないでください。</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Forgeの必須ファイルが不足しています。ファイル '" + archivo
				+ "' がインストール内に見つかりません。これは通常、Forgeのインストールが中断されたか、重要なファイルが削除された場合に発生します。QuickFixではこれらのファイルを自動復元できません。公式インストーラーを使用してForgeを再インストールする必要があります。</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Forgeが必要なMinecraftバージョンを見つけられません。バージョン " + version
				+ " が必要ですが、ファイル '" + archivo
				+ "' にありません。これはMinecraftバージョンと使用中のForgeバージョンが一致しない場合に発生します。Minecraftバージョンに合った正しいForgeバージョンをダウンロードしてください。</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge起動に必要な 'fmlclient' ターゲットが見つかりません。これはForgeのインストールが不完全または破損していることを示しています。Forgeの重要なファイルが正しくインストールされていない可能性があります。公式インストーラーを使用してForgeを再インストールする必要があります。</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>クラスローダーにMinecraftのメインクラスが見つかりません。これは通常、Forgeのインストールが不完全か、他のMODと競合していることを示します。Forgeのインストール中にMinecraftのファイルが破損した可能性があります。Forgeを正しく再インストールする必要があります。</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forgeのインストールが完了していません。インストールが中断された、ファイルが削除された、またはMinecraftのバージョンと互換性がない場合に発生します。Forgeは正常に動作するために特定のファイルが必要ですが、現在のインストールから一部が欠けています。</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "Forgeのインストールが不完全";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>この問題を解決するには、Forgeを正しく再インストールしてください。Minecraftのバージョンに合った正しいバージョンをダウンロードし、中断せずにインストール手順を完了してください。</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "公式サイトからForgeをダウンロード";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "Forgeを正しく再インストールする方法";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>Forge再インストール手順：</h3>" + "<ol>"
				+ "<li>公式サイトから正しいForgeインストーラーをダウンロード（Minecraftバージョンに合った推奨バージョン）</li>" + "<li>Minecraftランチャーを完全に終了</li>"
				+ "<li>管理者権限でForgeインストーラーを実行</li>" + "<li>'Installer'を選択（'Installer (run client)' ではない）</li>"
				+ "<li>ランチャー内のMinecraftプロファイルフォルダを選択</li>" + "<li>'OK'をクリックし、インストール完了まで待機</li>"
				+ "<li>ランチャーを再起動し、Forgeがプロファイル一覧に表示されることを確認</li>" + "</ol>"
				+ "<p><b>重要：</b>カスタムランチャーを使用する場合、正しいプロファイルフォルダを選択してください。</p>" + "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "Forge再インストール手順";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError() + "'>リンクエラー：ライブラリ " + nombreBiblioteca
				+ " の読み込みに失敗しました。考えられる解決策：<br/><br/>"
				+ "a) 共有ライブラリを含むディレクトリを -Djava.library.path または -Dorg.lwjgl.librarypath に追加します。<br/>"
				+ "b) 共有ライブラリを含む JAR ファイルを classpath に追加します。<br/><br/>"
				+ "このエラーは、Minecraft が実行に必要な重要なファイルを見つけられない場合に発生します。" + "インストールが不完全であるか、システムの権限に問題があることが原因です。</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "リンクエラー";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>ライブラリの読み込みに失敗しました。考えられる解決策：<br/><br/>"
				+ "a) 共有ライブラリを含むディレクトリを -Djava.library.path または -Dorg.lwjgl.librarypath に追加します。<br/>"
				+ "b) 共有ライブラリを含む JAR ファイルを classpath に追加します。<br/><br/>"
				+ "これらの技術的解決策は上級者向けです。ほとんどのユーザーは、これらのパラメータを変更する前に " + "Minecraft を再インストールすることをお勧めします。</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>IDの衝突: ID <strong>" + id + "</strong> は、<strong>"
				+ modOrigen + "</strong> によって既に使用されており、<strong>" + modDestino
				+ "</strong> を追加しようとしたときに発生します。これは、2つのMODが異なる要素に同じIDを使用しようとした場合に起こります。</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>許可されたIDの最大範囲を超えました。MODがMinecraftのバージョンで許可されていない範囲のIDでブロックやアイテムを登録しようとしたときに発生します。</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "IDの衝突";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>この問題を解決するには、Minecraft 1.12.2に <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a> をインストールしてください。1.7.10の場合は <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a> を使用してください。</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>IDの衝突を解決するには、<a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> や <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> などのツールを使用してください。</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "JustEnoughIDsをインストール";
	}

	@Override
	public String instalar_endlessids() {
		return "EndlessIDsをインストール";
	}

	@Override
	public String usar_idfix_minus() {
		return "IdFix MinusまたはIdFixを使用";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "Minecraft-ID-Resolverを使用";
	}

	@Override
	public String ver_documentacion_jp() {
		return "日本語ドキュメントを表示";
	}

	@Override
	public String escanearDeMCreator() {
		return "MCreatorをスキャン";
	}

	@Override
	public String tituloArbolDeMods() {
		return "モジュールとクラスのツリー";
	}

	@Override
	public String tipoBusqueda() {
		return "タイプ";
	}

	@Override
	public String filtroTodos() {
		return "すべて";
	}

	@Override
	public String filtroPaquetes() {
		return "パッケージ";
	}

	@Override
	public String filtroClases() {
		return "クラス";
	}

	@Override
	public String filtroMetodos() {
		return "メソッド";
	}

	@Override
	public String filtroCampos() {
		return "フィールド";
	}

	@Override
	public String filtroReferenciasCampo() {
		return "フィールド参照";
	}

	@Override
	public String filtroReferenciasMetodo() {
		return "メソッド参照";
	}

	@Override
	public String filtroReferenciasClase() {
		return "クラス参照";
	}

	@Override
	public String tipBuscar() {
		return "ここに文字を入力してモッドツリーを検索";
	}

	@Override
	public String botonBuscar() {
		return "検索";
	}

	@Override
	public String botonResetearArbol() {
		return "ツリーをリセット";
	}

	@Override
	public String modsCargados() {
		return "ロード済みのモッド";
	}

	@Override
	public String clases() {
		return "クラス";
	}

	@Override
	public String metodos() {
		return "メソッド";
	}

	@Override
	public String campos() {
		return "フィールド";
	}

	@Override
	public String referencias() {
		return "参照";
	}

	@Override
	public String resultadosBusqueda() {
		return "検索結果";
	}

	@Override
	public String buscarReferencias() {
		return "参照を検索";
	}

	@Override
	public String referenciasMod() {
		return "モッドの参照";
	}

	@Override
	public String referenciasClase() {
		return "クラスの参照";
	}

	@Override
	public String referenciasMetodo() {
		return "メソッドの参照";
	}

	@Override
	public String referenciasCampo() {
		return "フィールドの参照";
	}

	@Override
	public String noSeEncontraronReferencias() {
		return "参照が見つかりません";
	}

	@Override
	public String detalleMod() {
		return "モッドの詳細:";
	}

	@Override
	public String ubicacion() {
		return "場所";
	}

	@Override
	public String nombres() {
		return "名前";
	}

	@Override
	public String modulo() {
		return "モジュール";
	}

	@Override
	public String detalleClase() {
		return "クラスの詳細:";
	}

	@Override
	public String detalleMetodo() {
		return "メソッドの詳細:";
	}

	@Override
	public String detalleCampo() {
		return "フィールドの詳細:";
	}

	@Override
	public String clase() {
		return "クラス";
	}

	@Override
	public String tipo() {
		return "タイプ";
	}

	@Override
	public String referenciasAMetodos() {
		return "メソッドへの参照:";
	}

	@Override
	public String referenciasACampos() {
		return "フィールドへの参照:";
	}

	@Override
	public String arbolDeMods() {
		return "モッドツリー";
	}

	@Override
	public String metodo() {
		return "メソッド";
	}

	@Override
	public String campo() {
		return "フィールド";
	}

	@Override
	public String descompilar() {
		return "デコンパイル";
	}

	@Override
	public String exportar() {
		return "エクスポート";
	}

	@Override
	public String importar() {
		return "インポート";
	}

	@Override
	public String errorImportar() {
		return "インポートエラー";
	}

	@Override
	public String estructuraImportada() {
		return "構造をインポートしました";
	}

	@Override
	public String estructuraExportada() {
		return "構造をエクスポートしました";
	}

	@Override
	public String errorExportar() {
		return "エクスポートエラー";
	}

	@Override
	public String exportando() {
		return "エクスポート中";
	}

	@Override
	public String exportacionTardara() {
		return "エクスポートに時間がかかる場合があります";
	}

	@Override
	public String porFavorEspere() {
		return "お待ちください";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>VLCのバイナリがありません。WaterMediaはVLCのバイナリを必要とします。https://www.videolan.org/vlc/ から手動でインストールする必要があります。  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "VLCをダウンロード";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>重大エラー：モジュール名 '" + nombreModulo
				+ "' に無効な文字が含まれています。'" + parteInvalida
				+ "' は有効なJava識別子ではありません。Javaの予約語（例：'true', 'class'）や名前に使用できない文字をモッドが使用している場合に発生します。</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "モッド名に無効な文字が含まれています";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "モッド名 '" + nombreModulo + "' は '" + parteInvalida + "' を含むため無効です。これはJavaの予約語または無効な文字です。 "
				+ "ログを確認して、この名前に対応するモッドを特定してください（通常はJARファイル名）";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "モッドのフォルダを開き、<b>/META-INF/</b> フォルダ内の <b>mods.toml</b> ファイルを編集してください。 "
				+ "<b>modId</b> の値を、Javaの予約語を使わず、英数字とアンダースコアのみで構成されるように変更してください";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "有効な名前の例：'true.shot.enchantment' の代わりに 'truemod_shot_enchantment' を使用。 "
				+ "モッド名にはドット、ハイフン、Javaの予約語（'true'、'false'、'class'など）は使用できません";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>モッド '" + nombreJar
				+ "' に重大エラー：依存関係に必須フィールド 'mandatory' がありません。mods.toml ファイルが依存関係が必須かどうかを指定していない場合に発生します。</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "モッド依存関係に必須フィールドが不足";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "問題のあるモッドは：<b>" + nombreJar + "</b> です。このファイルの依存設定にエラーがあります";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "<b>" + nombreJar + "</b> モッドの <b>/META-INF/</b> フォルダ内にある <b>mods.toml</b> ファイルを開いてください";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "依存関係セクションで、各エントリに <b>mandatory=true</b> または <b>mandatory=false</b> が含まれていることを確認してください（例：modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" ）";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>重大エラー：モッド '" + nombreJar
				+ "' のaccess transformer設定が無効です。設定ファイルの構文が誤っているか、存在しないクラス/メソッドを参照している場合に発生します。</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "無効な Access Transformer";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "問題のあるモッドは：<b>" + nombreJar + "</b> です。このモッドには無効なaccess transformer設定が含まれています";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "モッド <b>" + nombreJar + "</b> 内の <b>accessTransformer.cfg</b> ファイルを開いてください（通常はJARファイルのルートフォルダにあります）";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "access transformerの構文を修正してください。各行は <b>access class.method</b> の形式に従う必要があります（例：public net.minecraft.world.entity.Entity.func_200560_a）。現在のMinecraftバージョンに存在しないクラスやメソッドを参照している行は削除してください";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>重大エラー：@ModアノテーションのモッドIDとmods.tomlファイルのIDが一致していません。'" + nombreMod + "' モッドはIDの不一致により読み込めません。</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "@Modとmods.tomlのID不一致";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "開発中のモッド '" + nombreMod + "' は、<b>@Mod</b> アノテーションと <b>mods.toml</b> のIDが一致していません";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "メインクラスのIDが <b>/META-INF/mods.toml</b> ファイルの <b>modId</b> 値と一致しているか確認してください。例： <b>@Mod(\"mymod\")</b> は <b>modId=\"mymod\"</b> と一致する必要があります";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "Gradleを使用している場合は、変更後に <b>clean</b> を実行してリソースが正しく更新されるようにしてください。古いファイルがbuildフォルダに残っていることがあります";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "クライアント" : "サーバー";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "サーバー" : "クライアント";

		return "<b style='color:#" + config.obtenerColorError() + "'>重大エラー: クラス '" + nombreClase + "' を " + plataforma
				+ " 環境で読み込もうとしていますが、これは " + plataformaOpuesta
				+ " 向けに設計されています。<b>サイドバーの「Modツリー」機能を使って、このクラスを読み込もうとしているModを特定してください</b>。"
				+ "Modは特定のプラットフォーム専用であり、他では動作しません。</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "誤ったプラットフォームのMod";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "右側の <b>Modツリー</b> タブで、<b>" + nombreClase + "</b> クラスへの参照を検索し、問題を引き起こしているModを特定してください";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "クライアント" : "サーバー";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "サーバー" : "クライアント";

		return "特定されたModは <b>" + plataformaOpuesta + "</b> 向けであり、" + plataforma + " 環境では使用すべきではありません。";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "問題のあるModを <b>mods</b> フォルダから削除してください。このプラットフォームで同様の機能が必要な場合は、"
				+ "<b>クライアント</b> または <b>サーバー</b> 専用に設計された代替Modを検索してください";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("重大エラー: modid '").append(modIdFaltante).append("'.のメタデータがありません。 ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("以下のModが原因である可能性があります: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", その他...");
			mensaje.append("</b>. ");
		}

		mensaje.append("Modが未インストールまたはmods.tomlファイルが正しくない場合に発生します。");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "mods.toml メタデータの欠落";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("以下のModが '").append(modIdFaltante).append("'.に依存しています: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", その他...");
			paso.append("</b>. <b>Modツリー</b> 機能で問題のModを確認してください");
			return paso.toString();
		}
		return "Modが '".concat(modIdFaltante)
				.concat("' に依存しようとしていますが、このModはインストールされていません。<b>Modツリー</b> 機能で問題のModを特定してください");
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "以下の2つの選択肢があります:<br/>"
				+ "1. <b>不足しているModをインストール</b>: IDが '".concat(modIdFaltante).concat("' のModを検索・インストールしてください<br/>")
				+ ("2. <b>依存Modを削除</b>: 機能が不要な場合、'").concat(modIdFaltante).concat("' に依存するModを削除してください");
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "'".concat(modIdFaltante).concat("' がライブラリ（例: 'forge', 'minecraft', 'curios'）の場合、")
				+ "正しいバージョンのMinecraftとForgeがインストールされているか確認してください。" + "通常のModの場合は、ダウンロードページで必要な前提条件を確認してください";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>警告：サウンドシステムの初期化に失敗しました。サウンドと音楽は無効になっています。このエラーは一般的にSoundPhysicsModに関連しており、他のサウンドライブラリとの競合が原因である可能性があります。</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "サウンドシステムエラー";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "このエラーは通常<b>SoundPhysicsMod</b>に関連しています。使用しているMinecraftバージョンと互換性のある最新バージョンをインストールしているか確認してください";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "他のサウンドMOD（Sound Filters、Dynamic Surroundingsなど）を使用している場合、一時的にSoundPhysicsModを削除して競合が解消されるか確認してください";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "<b>logs</b>フォルダを確認し、LWJGLやOpenALに関する追加メッセージを調べてください。これらは基盤となるサウンドライブラリに問題がある可能性を示しています";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("重大エラー：クラス'").append(nombreClase).append("'はイベントリスナーとして登録されていますが、有効なメソッドがありません。 ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("このクラスは以下のMODにあります：<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", その他...");
			mensaje.append("</b>. ");
		}

		mensaje.append("@SubscribeEventアノテーションのついたメソッドを持たずに、イベントリスナーとしてクラスを登録した場合に発生します。");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "イベントリスナーなしで登録されたクラス";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("問題のあるクラスは以下のMODにあります：<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", その他...");
			paso.append("</b>。これらのMODは有効なメソッドなしにイベントを登録しようとしています");
			return paso.toString();
		}
		return "クラス<b>" + nombreClase
				+ "</b>はイベントをリッスンするように登録されましたが、<b>@SubscribeEvent</b>アノテーションのついたメソッドがありません。<b>MODツリー</b>機能を使用して、このクラスを含むMODを特定してください";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "ソースコード内で、クラス<b>" + nombreClase + "</b>が以下の形式のメソッドを少なくとも1つ持っていることを確認してください："
				+ "<b>@SubscribeEvent public void メソッド名(特定のイベント イベント) { ... }</b>。 " + "内部クラスの場合、staticでないことを確認してください";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("識別されたMODについて(<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", など");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("このMODの開発者に連絡して問題を修正してもらってください。 ");
			} else {
				paso.append("これらのMODの開発者に連絡して問題を修正してもらってください。 ");
			}
		}

		paso.append("あなたが開発者の場合、EventBusからのこのクラスの登録を削除するか、有効な@SubscribeEventメソッドを追加してください");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>重大エラー: ファイル '" + nombreArchivo
				+ "' を処理中に 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' 例外が発生しました。このエラーは、ランチャーがモッドパックのファイルを正しくダウンロードまたは展開できなかったことを示しています。 "
				+ "'zip END header not found' というメッセージは、JARファイルが不完全または破損していることを示しており、大容量ファイルのダウンロードを適切に処理できないランチャーで非常に一般的です。 "
				+ "この問題は主にTwitch/CurseForge、Technic Launcher、特にLuna Pixelのユーザーに影響を与えます。これらのランチャーは、ダウンロードされたファイルの完全な整合性を検証できないことがよくあります。 "
				+ "Luna Pixelユーザーは、ファイルの整合性をより適切に処理し、この特定のエラーを回避できるATLauncherへの移行を検討すべきです。 "
				+ "ZIP形式が破損しているため、システムはモッドを読み込めず、Forgeがゲーム起動に必要なリソースを読み取るのを妨げます。</b>";
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "UnionFileSystemエラー - ファイルが破損";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		return "モッドパックを最初から完全に再インストールしてください";
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "Luna Pixelを使用している場合は、ATLauncherに切り替えてください";
	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "再インストール前にインターネット接続とディスク空き容量を確認してください";
	}

	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "ProxySysOutSysErrを有効にしますか？\n\n"
				+ "このオプションにより、ランチャーがログを提供しない場合でもCrashDetectorがSystem.outとSystem.errにアクセスできるようになります。\n\n"
				+ "手動でログを貼り付けられない場合にのみ有効にしてください。\n\n" + "警告：一部のMODやランチャーと干渉する可能性があります。\n\n"
				+ "変更を適用するには、ゲーム／アプリの再起動が必要です。";
	}

	@Override
	public String confirmacionTitulo() {
		return "確認";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErrが正常に有効化されました。\n\n" + "変更を適用するには、CrashDetectorを再起動する必要があります。";
	}

	@Override
	public String informacionTitulo() {
		return "情報";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("重大エラー: AzureLib と GeckoLib が早すぎるタイミングで初期化されました！ ");
		} else if (azureLibError) {
			mensaje.append("重大エラー: AzureLib が早すぎるタイミングで初期化されました！ ");
		} else if (geckoLibError) {
			mensaje.append("重大エラー: GeckoLib が早すぎるタイミングで初期化されました！ ");
		}

		mensaje.append("このエラーは、Fabric モッドを非Fabric版のライブラリで使用しようとしたときに発生します。 ");

		if (connectorPresente) {
			mensaje.append(
					"互換性モッド（Sinytra Connector または specialcompatibilityoperation）が検出され、Forge や FeatureCreep 環境で Fabric モッドを実行しようとしていることが示されています。 ");
			mensaje.append("ログの 'FabricMC 初期化エラー' を確認して、問題を引き起こしている特定のモッドを特定してください。 ");
		}

		mensaje.append("AzureLib と GeckoLib はアニメーションモッドに不可欠ですが、正しいプラットフォーム（Fabric または Forge）と一致している必要があります。 ");
		mensaje.append("この初期化の競合により、ゲームはアニメーションモッドを正しく読み込めません。");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "ライブラリの初期化が早すぎます";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "ログで 'FabricMC 初期化エラー' を確認し、問題のあるモッドを特定してください";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "プラットフォーム（Forge または Fabric）に合った正しいバージョンの AzureLib/GeckoLib を使用しているか確認してください";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError() + "'>重大エラー: C2ME と接続モッドの非互換性。 "
				+ "このエラーは、C2ME が Sinytra Connector や specialcompatibilityoperation などの接続モッド環境で制限されている Java の内部コンポーネントにアクセスしようとしたために発生します。 "
				+ "<b>C2ME はこれらの環境と互換性がありませんが、<a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> が推奨される代替手段</b>であり、接続モッドと正常に動作します。 "
				+ "Java のセキュリティ権限の競合により、ゲームを起動できません。</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "C2ME と接続モッドの非互換性";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "mods フォルダから C2ME を削除してください";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "代わりに <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> をダウンロードしてインストールしてください（Sinytra Connector に対応）";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "すべての接続モッド（Sinytra Connector など）が最新バージョンに更新されていることを確認してください";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError() + "'>重大なエラー: モッド '" + modId
				+ "' のJEIプラグインの読み込みに失敗しました。クラス '" + nombreClase + "' (プラグインID: '" + pluginId
				+ "') がエラーを発生させ、ゲーム起動中にクラッシュしています。 " + "この問題は、ゲームの初期化を妨げる互換性のないまたは破損したJEI統合を持つモッドがある場合に発生します。</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "JEIプラグイン失敗 - クラッシュを引き起こす";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "モッド <b>" + modId
				+ "</b> にクラッシュを引き起こす破損したJEIプラグインが含まれています。<b>Modツリー</b> 機能を使用して、問題を引き起こしているモッドを確認してください";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "クラッシュが解決するか確認するために、一時的に mods フォルダからモッド <b>" + modId + "</b> を削除してください";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "モッド <b>" + modId + "</b> の更新を確認するか、開発者にJEIプラグインの問題を報告してください。 " + "その間は、ゲームを起動するためにはモッドを削除する必要があります";
	}

	@Override
	public String tituloLectador() {
		return "ログリーダー - クラッシュ検出器";
	}

	@Override
	public String obtenerTituloLeyenda() {
		return "色の凡例";
	}

	@Override
	public String obtenerErrorEnLeyenda() {
		return "重大なエラー";
	}

	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "スタックトレース";
	}

	@Override
	public String obtenerTituloError() {
		return "エラー";
	}

	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "選択した行の処理中にエラーが発生しました";
	}

	@Override
	public String obtenerNombreError() {
		return "エラー名";
	}

	@Override
	public String obtenerDescripcionError() {
		return "詳細な説明";
	}

	@Override
	public String obtenerSeleccionarConsola() {
		return "ログを選択";
	}

	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "未確認のエラー";
	}

	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "ログに重大なエラーが検出されました。 " + "根本原因を特定するには、スタックトレースを確認してください。";
	}

	@Override
	public String obtenerErrorLecturaArchivo() {
		return "ログファイルを読み取れませんでした。 " + "ファイルが存在し、読み取り権限があるか確認してください。";
	}

	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "ログ分析ツール";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("重大エラー: モッド '").append(modId).append("' の自動イベントサブスクライバーの登録に失敗しました。 ");

		mensaje.append("問題のあるクラス: <b>").append(nombreClase).append("</b>。 ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("このクラスは以下の場所にあります: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", その他...");
			mensaje.append("</b>。 ");
		}

		mensaje.append("モッドがクラスを自動的にイベント購読者として登録しようとしたが、クラスを読み込めなかった場合にこのエラーが発生します。 ");
		mensaje.append("<b>ログ内のこのメッセージより前の他のエラーを確認してください。根本原因は以前の読み込み失敗である可能性があります</b>。");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "自動サブスクライバー登録失敗";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "モッド <b>" + modId + "</b> がクラス <b>" + nombreClase
				+ "</b> を自動サブスクライバーとして登録しようとしていますが、失敗しています。このクラスが存在し、アクセス可能か確認してください";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("問題のあるクラス <b>" + nombreClase + "</b> はこれらのファイルにあります: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", その他...");
			paso.append("</b>。 ");
			paso.append("<b>Modツリー</b> 機能を使って、問題のあるクラスを含む正確なファイルを確認してください");
			return paso.toString();
		}
		return "クラス <b>" + nombreClase + "</b> がどのモッドファイルにも見つかりません。モッド <b>" + modId
				+ "</b> が正しくインストールされているか確認してください。<b>Modツリー</b> 機能を使って問題を特定してください";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "モッド <b>" + modId + "</b> をお使いのMinecraftおよびForgeバージョンと互換性のある最新バージョンに更新してください。 "
				+ "問題が続く場合は、開発者に問題のクラスを含めてエラーを報告してください";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "<b>ログ内のこのメッセージより前の他のエラー</b>を確認してください。実際の問題は以前の読み込み失敗にある可能性があります。 "
				+ "場合によっては、以前のエラーがイベント登録に必要なクラスの読み込みを妨げることがあります";
	}

	@Override
	public String limpiado() {
		// TODO Auto-generated method stub
		return "クリア済";
	}

	@Override
	public String original() {
		// TODO Auto-generated method stub
		return "オリジナル";
	}

	@Override
	public String verEnConsola() {
		// TODO Auto-generated method stub
		return "ログで表示";
	}

	@Override
	public String advertencia() {
		// TODO Auto-generated method stub
		return "警告";
	}

	@Override
	public String fatal() {
		// TODO Auto-generated method stub
		return "重大";
	}

	@Override
	public String noRegistroDeBattly() {
		// TODO Auto-generated method stub
		return "BattlyLauncher にはコピー可能なログやコンソールがありません。ゲームを再起動して ProxySysOutSysErr を使用し、STDOUT と STDERR を傍受できますが、STDOUT や STDERR を変更するMODと衝突する可能性があります";
	}

	@Override
	public String noRegistroDeNightWorld() {
		// TODO Auto-generated method stub
		return "ランチャーログを取得するには、NightWorld の設定でデバッグモードを有効にする必要があります。特に STDOUT と STDERR を含むため、非常に重要です";
	}

	@Override
	public String noRegistroDeMCServidor() {
		// TODO Auto-generated method stub
		return "サーバーの端末内容を保存または貼り付ける必要があります。他のログにない情報（STDOUT、STDERR、その他のエラーなど）が含まれているためです。直近のセッション内容を貼り付けてください。今後は、起動コマンド後に >> cd_launcherlog を追加して、端末出力を cd_launcherlog というファイルに保存できます（画像参照）。これにより端末への表示はされず、出力はこのファイルにのみ記録されますのでご注意ください。";
	}

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("重大なエラー: NeoForge環境でLexForgeのトランスフォーマーが検出されました。 ");
		sb.append("</b>");

		sb.append("対象クラス: <b>").append(claseReceptora).append("</b>。 ");
		sb.append("影響を受けるインターフェースは <b>").append(interfazObjetivo).append("</b> で、");
		sb.append("不足しているメソッドは <b>").append(firmaMetodoFaltante).append("</b> です。 ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("このクラスは以下の場所にあります: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", その他...");
			sb.append("</b>。 ");
		} else {
			sb.append("このクラスを含むJARファイルが見つかりません。シェイディングされているか、jar-in-jar形式で埋め込まれている可能性があります。 ");
		}

		sb.append("MinecraftForge/LexForge向けにコンパイルされたModLauncherのトランスフォーマー/サービスが、");
		sb.append("互換性のないModLauncher APIバージョンとともにNeoForge上で読み込まれると、このエラーが発生します。 ");
		sb.append("NeoForge用のコンポーネントに更新または置き換えてください。");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "NeoForgeで使用されたLexForgeトランスフォーマー";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "非互換のトランスフォーマーを特定してください: <b>" + claseReceptora + "</b>。 " + "期待されるAPIは <b>" + interfazObjetivo
				+ "</b> で、欠落しているメソッドは <b>" + firmaMetodoFaltante + "</b> です。 "
				+ "MODが <b>META-INF/services</b> にこのクラスを登録していないか確認し、NeoForgeで削除または無効にしてください。";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("関係するMODの場所: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", その他...");
			sb.append("</b>。 ");
		} else {
			sb.append("クラスを含むJARが見つかりませんでした。jar-in-jarやシェイドされた依存関係を確認してください。 ");
		}
		sb.append("一時的にこれらのJARを削除するか、NeoForge対応バージョンを使用して原因を確認してください。");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "コンポーネントをNeoForge専用バージョンに置き換えるか、NeoForgeが使用するModLauncherバージョンに対して再コンパイルしてください。 "
				+ "古いLexForge/MinecraftForgeのバイナリは避けてください。";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "modsフォルダをクリーンアップし、重複したjar-in-jarエントリを削除してください。必要であればランチャーキャッシュを消去し、"
				+ "再起動してLexForgeのトランスフォーマーが読み込まれていないことを確認してください。";
	}

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMediaを起動できません：Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("は互換性がありません。</b> ");
		sb.append("Xenonを削除し、代わりにEmbeddiumまたはSodiumを使用してください。 ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("検出場所：<b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", その他...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMediaとXenonの互換性エラー";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return label + " がWaterMediaと互換性がないことが判明しました。プロファイルから削除してください。";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("場所：<b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", その他...");
			sb.append("</b>。そのJARファイルを削除してください。");
			return sb.toString();
		}
		return "JARファイルが見つかりません。modsフォルダを確認し、Xenonを削除してください。";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "EmbeddiumまたはSodiumを代替としてインストールし、ゲームを再起動してください。";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "圧縮エラー (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>TACZリソースのコピー中にDeflaterが閉じられました。</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("関連するもの: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", その他");
			sb.append("</b>. ");
		}
		sb.append("<br/><b>解決策:</b> <code>tacz/tacz-pre.toml</code> で <code>DefaultPackDebug=true</code> に設定してください。")
				.append("必要に応じて、まずマップを生成してから有効化してください。");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "tacz/tacz-pre.toml で DefaultPackDebug=true に設定してください。必要であれば、まずマップを生成してから有効化してください。";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "バインドされていない密度関数";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>レジストリに密度関数がありません。</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("不足しているもの: ");
			for (int i = 0; i < Math.min(4, claves.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append("<code>").append(claves.get(i)).append("</code>");
			}
			if (claves.size() > 4)
				sb.append(", …");
			sb.append(". ");
		}
		sb.append("<br/><b>解決策:</b> これらの関数を定義するMOD/データパックをインストールまたは有効化し、再起動してください。");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "これらの関数を提供するMOD/データパックをインストールまたは有効化し、ゲームを再起動してください。";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// モジュールを明示的に言及し、エラー色の短いメッセージ
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("レジストリエントリが存在しません: ").append(claveFaltante).append(". ");
		sb.append("Create 6 向けの Steam & Railways アルファ版でよく発生します。");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (アルファ)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "Create 6 向けの Steam & Railways アルファ版を互換性のあるバージョンに置き換えるか削除してください。";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// 短く、エラー色で、直接的な推奨を含む
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("読み込み衝突: Multiworld と Sodium/Embeddium/Rubidium を併用すると ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance) が発生します。 ")
				.append("提案: Multiworld またはパフォーマンスMODを削除するか、互換性のあるバージョンを使用してください。");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "衝突: Multiworld とパフォーマンスMOD";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Multiworld または Sodium/Embeddium/Rubidium をアンインストールするか、互換性のあるバージョンに更新してください。";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Sodiumが互換性のないグラフィックドライバーを検出しました。 "
				+ "GPUドライバーを最低要件まで更新するか、Sodiumのガイドに従ってください。" + "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "OpenGLコンテキストエラー";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OpenGLに失敗しました：現在のコンテキストがありません、またはこのコンテキストでは機能が利用できません。 " + "ビデオドライバーの問題である可能性もあります。" + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "GPUドライバーを更新・再インストールして再起動してください。オーバーレイを無効にし、パフォーマンスMODなしで試してみてください。";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "リンクがクリップボードにコピーされました。";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		return "圧縮ファイル内を検索（.zip/.jar/.war/.ear/.fpm/.rar（Java 用）*）";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "テクスチャ解像度エラー: ロードできません " + recurso + " - サイズ: "
				+ tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "テクスチャ解像度エラー";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "このエラーは、テクスチャが大きすぎるか、リソースパックが多すぎる場合に発生します。" + "解像度の低いリソースパックを使用するか、いくつかのリソースパックを削除してください。"
				+ "許可された解像度を超えるカスタムテクスチャを追加していないか確認してください。";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ModLauncher サービスエラー：パスに無効な文字が含まれています。 "
				+ "ModLauncher サービスは、ASCII以外や特殊文字を含むパスを処理できません。 "
				+ "問題のある文字には ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요 があり、特に名前の末尾にある '\"' 文字は深刻です。 "
				+ "一般的な ModLauncher サービスコンポーネントには、CrashDetector、" + Config.obtenerInstancia().obtenerNombreCD()
				+ "、FeatureCreep、Vivicraft、Optifine、Sodium、clonos、Iris Shaders/Oculus、MixerLogger、CrashAssistant、Sintrya Connector などがあります。 "
				+ "すべてのサービスを削除できますが、パス名自体が原因で他の問題が発生する可能性があります。 "
				+ "解決策：インスタンス名を ASCII 文字（a-z, A-Z, 0-9）のみを使用するように変更し、スペースや特殊文字は避けてください。</b>";
	}

	@Override
	public String nombre_error_modlauncher_path() {
		return "ModLauncher パスエラー";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "このエラーは、インスタンスのパスにASCII以外の文字や特殊文字が含まれている場合に発生します。 " + "ModLauncherのサービスはこれらのパスを処理できません。 "
				+ "解決策：インスタンス名をASCII文字（a-z, A-Z, 0-9）のみに変更し、スペースや特殊文字を避けてください。 "
				+ "特に名前の末尾にある '\"' 文字は非常に問題となるため、注意してください。";
	}

	@Override
	public String tituloEditorCodice() {
		return "Codiceエディター";
	}

	@Override
	public String nuevo() {
		return "新規";
	}

	@Override
	public String actualizarSeleccionado() {
		return "選択項目を更新";
	}

	@Override
	public String eliminarSeleccionado() {
		return "選択項目を削除";
	}

	@Override
	public String exportarJSON() {
		return "JSONをエクスポート...";
	}

	@Override
	public String guardarTodo() {
		return "すべて保存";
	}

	@Override
	public String general() {
		return "一般";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "検索テキスト";
	}

	@Override
	public String filtro() {
		return "フィルター (ID)";
	}

	@Override
	public String criticalidad() {
		return "重要度 (警告/エラー/致命的)";
	}

	@Override
	public String prioridad() {
		return "優先度";
	}

	@Override
	public String lista() {
		return "チェック項目";
	}

	@Override
	public String colIdioma() {
		return "言語";
	}

	@Override
	public String colNombre() {
		return "名前";
	}

	@Override
	public String colResultado() {
		return "結果";
	}

	@Override
	public String vistaJson() {
		return "JSONプレビュー";
	}

	@Override
	public String idiomas() {
		return "言語 (すべて必須)";
	}

	@Override
	public String elegirFiltro() {
		return "選択...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "フィルターを選択してください";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "利用可能なフィルター";
	}

	@Override
	public String faltanCampos() {
		return "すべての必須一般項目を入力してください。";
	}

	@Override
	public String critInvalida() {
		return "無効な重要度です。警告、エラー、または致命的を使用してください。";
	}

	@Override
	public String filtroNoExiste() {
		return "指定されたフィルターは存在しません。";
	}

	@Override
	public String faltanIdiomas() {
		return "すべての言語について名前と結果を入力してください：";
	}

	@Override
	public String verificacionInvalida() {
		return "チェック項目が無効です。フィールドを確認してください。";
	}

	@Override
	public String guardadoOk() {
		return "正常に保存されました。";
	}

	@Override
	public String editorCodiceBoton() {
		return "理由を追加";
	}

	@Override
	public String descripcionEditorCodice() {
		return "ここに理由を登録できます。IDが必要で、これは特殊文字・アクセント記号・スペースを含まない文字列である必要があります。フィルターには、「行に含まれる」で特定の文字列を1行内から検索、「全体に含まれる」でログ全体に文字列があるか確認、「行正規表現」で1行が正規表現に一致するかチェック、「全体正規表現」でログ全体を正規表現で検索（行単位のバージョンの使用を推奨）できます。色分けのために、重大度としてFATAL（致命的）、ERROR（エラー）、ADVERTENCIA（警告）のいずれかを設定する必要があります。すべての言語について、画面に表示される名前と結果を入力してください。他の検査項目を追加または削除できます。完了時に保存されます。";
	}

	@Override
	public String descartarCambios() {
		return "現在のチェックで保存されていない変更を破棄しますか？";
	}

	@Override
	public String confirmacion() {
		return "確認";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "終了する前に変更を保存しますか？";
	}

	@Override
	public String salirSinGuardar() {
		return "保存せずに終了";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("重大なエラー: modlauncher サービス (IDependencyLocator) の読み込みに失敗しました。<br>");
		sb.append("🔹 <b>問題のあるクラス:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>影響を受けるMOD:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append("🔸 <b>MODが特定できません。</b> 最近インストールしたMOD、開発版MOD、または不適切にパッケージされたMODを確認してください。<br>");
		}

		sb.append("🔸 <b>原因:</b> MODの <code>META-INF/services/...</code> ファイルが破損しているか、");
		sb.append("現在のForge/NeoForgeバージョンと互換性がないか、間違ったバージョン用のMODです。<br>");
		sb.append("🔸 <b>結果:</b> Forge/NeoForgeがMODの依存関係を登録できず、");
		sb.append("ゲームの起動が妨げられます。<br>");
		sb.append("🔸 <b>解決策:</b> 問題のあるMODを更新、再インストール、または削除してください。");
		sb.append("開発版MODを使用している場合は、正確なForge/NeoForgeバージョン向けにコンパイルされていることを確認してください。");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "サービス構成エラー (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. 原因となるMODを特定します：最近インストールしたMODや開発版MODを確認してください。";
		}
		return "1. 問題のあるMODは： " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. MODを更新、再インストール、または削除してください。Forge/NeoForgeと互換性のあるバージョンを使用していることを確認してください。";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>重大なエラー：メソッドが存在しません。</b><br>" + "MODがメソッド <b style='color:#"
				+ colorCodigo + "'>" + metodo + "</b> を呼び出そうとしましたが、" + "このメソッドは現在のゲームバージョンまたは他のMODに存在しません。<br>"
				+ "<span style='color:#" + colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta)
				+ "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "メソッドが存在しません (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. このエラーは、MODが現在のゲームバージョンまたは他のMODと互換性がない場合に発生します。";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. 関係するすべてのMODを更新してください。問題が続く場合は、影響を受けたMODの作者に報告してください。";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>重大なエラー：フィールドが存在しません。</b><br>" + "MODがフィールド <b style='color:#"
				+ colorCodigo + "'>" + campo + "</b> にアクセスしようとしたが、" + "このフィールドは現在のゲームバージョンまたは他のMODに存在しません。<br>"
				+ "<span style='color:#" + colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta)
				+ "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "フィールドが存在しません (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. このエラーは、MODが現在のゲームバージョンまたは他のMODと互換性がない場合に発生します。";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. 影響を受けるすべてのMODを更新してください。問題が解決しない場合は、エラーを引き起こしたMODの作者に連絡してください。";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = MonitorDePID.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png")
				.toAbsolutePath().toUri().toString();

		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>助けが必要ですか？</strong><br>"
				+ "  修正方法がわからない場合や、ここに理由が載っていない場合は、ソーシャルネットワークでサポートを受けられます。" + "  ボタン <img src='" + iconoCompartir
				+ "' alt='共有' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>共有</strong> を使って、ログと解析結果のリンクをチームに送信できます。"
				+ "  Modpack作成者または企業の方は、<code>crash_detector/plantilla.htm</code> を編集して、" + "  チーム専用のリンクをカスタマイズしてください。"
				+ "</div>";
	}

	@Override
	public String restablecerPlantilla() {
	    return "テンプレートをリセット";
	}

	@Override
	public String restablecer() {
	    return "リセット";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
	    return nombreImagen + " を既定値にリセットしますか？";
	}

	@Override
	public String restablecerPlantillaMensaje() {
	    return "テンプレートを既定値にリセットしますか？";
	}
	
	@Override
	public String faltar_de_clases_azurelib() {
	    return "<b style='color:#" + config.obtenerColorError()
	           + "'>AzureLibのクラスが不足しています。すでにAzureLibを導入している場合は、2025年10月8日以前のバージョンをインストールしてください。これはよくある問題です。AzureLibを導入していない場合は、最新バージョンをインストールしてください。</b>";
	}
	@Override
	public String errorHealightINT() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "MOD <code>healight</code> が重大なエラーを引き起こしています: <code>java.lang.NoSuchFieldError: INT</code>。 "
	           + "このエラーは、MODがMCForge 47.10のMinecraft 1.20+では存在しなくなったフィールドにアクセスしようとしたために発生します。 "
	           + "この問題のため、ゲームを起動できません。</b>";
	}

	@Override
	public String solucionHealightINT() {
	    return "• MOD <code>healight</code> を削除または更新してください。 "
	           + "現在のバージョンは1.20.1用のMinecraftForge 47.10と互換性がありません。 "
	           + "MODの最新版を探すか、代替案の使用を検討してください。";
	}

	@Override
	public String nombreErrorHealightINT() {
	    return "重大なエラー：healight - フィールド 'INT' が見つかりません";
	}
	
	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz, String origen) {
	    String colorError = config.obtenerColorError();
	    StringBuilder sb = new StringBuilder();
	    sb.append("<b style='color:#").append(colorError).append(";'>")
	      .append("クラス <code>").append(clase).append("</code> が必須メソッドを実装していません：<br>")
	      .append("<code>").append(metodo).append("</code><br>")
	      .append("インターフェース <code>").append(interfaz).append("</code> から。");

	    if (!origen.isEmpty()) {
	        sb.append("<br><br>問題のあるMODまたはファイル: <code>").append(origen).append("</code>。");
	    }

	    sb.append("</b>");
	    return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
	    return "• このエラーは、MODがインターフェースを実装しているが必須メソッドを省略した場合に発生します。"
	           + "<b>両方のMOD</b> を更新してください（インターフェースを定義しているものとそれを実装しているもの）。"
	           + "どれかわからない場合は、エラーメッセージに表示されている名前を確認してください。";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
	    return "インターフェースのメソッドが未実装 (AbstractMethodError)";
	}
	
	@Override
	public String errorMetadataAnimacionEnServidor() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "MODが<b>専用サーバー</b>上で<b>クライアント側</b>のクラス "
	           + "(<code>AnimationMetadataSection</code>)を読み込もうとしていますが、これは不可能です。 "
	           + "このエラーは、MODがクライアントとサーバーのコードを正しく分離していない場合に発生します。 "
	           + "<code>ModernFix</code> の存在がこの問題を表面化する可能性がありますが、直接の原因ではありません。</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
	    return "• <b>即時対応：</b><code>ModernFix</code> を一時的に削除してサーバーが起動するか確認してください。 "
	           + "起動できた場合、別のMODがサーバー上でクライアントクラスを読み込んでいることが原因です。<br>"
	           + "• <b>根本的な解決策：</b>問題のあるMOD（アニメーションリソース、カスタムテクスチャ、グラフィックライブラリを使用しているMODなど）を特定し、更新または削除してください。<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
	    return "サーバーでクライアント側のクラスが読み込まれた (AnimationMetadataSection)";
	}
	
	@Override
	public String errorConfiguracionConnectorCorrupta() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "<code>Sinytra Connector</code> のMODの設定ファイルが破損しています。 "
	           + "これは、ゲームの予期しない終了、書き込みエラー、またはMOD間の競合により、"
	           + "ファイルにヌル文字 (<code>\\u0000</code>) が詰まってしまうことが原因で発生します。</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
	    return "• Minecraftインスタンスの <code>config/</code> フォルダへ移動してください。<br>"
	           + "• connector関連のMODの設定ファイルを検索し、削除してください。<br>"
	           + "• ゲームを再起動してください：Sinytra Connectorが新しいクリーンな設定ファイルを生成します。";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
	    return "Sinytra Connector 設定ファイル破損";
	}
	
	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "ファイル <code>" + nombreJar + "</code> が破損しているか不完全です。<br>"
	           + "最終的なZIPファイルのヘッダーが欠落しているため、システムはその内容を読み取れません。<br>"
	           + "このエラーは、ダウンロードが中断された場合やランチャーの障害後に発生することが多いです。</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
	    return "破損したJARファイル（特定の名前付き）";
	}

	@Override
	public String solucionJarCorrupto() {
	    return "• <b>破損したファイルを削除し</b>、公式サイト（CurseForge、MinecraftStorageなど）から再ダウンロードしてください。<br>"
	           + "• CurseForge、Technic、Luna Pixelなどのランチャーを使用している場合は、"
	           + "ファイルの整合性をより適切に確認できる<b>ATLauncher</b>または<b>Prism Launcher</b>への切り替えを検討してください。<br>"
	           + "• ダウンロード中はインターネット接続が安定していることを確認してください。";
	}
	
	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "NBTファイルの一つが破損しているため、ワールドを読み込めません "
	           + "(例: <code>level.dat</code>、<code>playerdata/*.dat</code>、またはチャンクデータ)。<br>"
	           + "具体的なエラーは: <code>UTFDataFormatException: バイト " + byteCorrupto + " 付近で不正な入力形式</code>です。<br><br>"
	           + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
	           + "⚠️ 修復を試みる前に、ワールドフォルダ全体を完全にバックアップしてください。</b><br><br>"
	           + "破損したファイルは、<a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>のような<b>NBTエディタ</b>を使用して修復できます。<br>"
	           + "深刻な損傷の場合は、<b>16進数エディタ</b>(HxDなど)を使って無効なバイトを検査・修正できます "
	           + "(ただし、NBTフォーマットに精通している場合のみ推奨)。<br>"
	           + "最終手段として、バックアップから復元するか、<code>FTB Backup</code>などのMODが提供する<i>ワールド修復</i>機能を使用してください。</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
	    return "• <b>修復を試みる前に、ワールドフォルダを完全にバックアップしてください</b>。<br>"
	           + "• NBTエディタ(NBT Studioなど)を使用して、破損したファイルを開き修正します。<br>"
	           + "• 失敗した場合は、壊れたバイトの位置で16進数エディタでファイルを確認します。<br>"
	           + "• 経験がなければ、最近のバックアップから復元してください。";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
	    return "ワールド破損：NBTデータの読み込みエラー";
	}
	
	@Override
	public String problema_con_openAL() {
	    return "<span style='color:#" + config.obtenerColorError()
	           + "'>OpenALに問題があります。Nouveauドライバーが原因の場合もありますが、アプリケーションに同梱されたOpenALのバージョンがディストリビューションのものと互換性がない場合もあり、その場合はディストリ自身のOpenALを使用する必要があります。詳細な対処法は以下のガイドを参照してください：<a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>LinuxでMinecraftの音声問題を修正する方法</a>。</span>";
	}
	
	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "他のプロセスがワールドファイルをロックしているため、サーバーを起動できません。<br>"
	           + "これは以下のいずれかの場合に発生します：<br>"
	           + "• すでにサーバーのインスタンスが実行中です。<br>"
	           + "• ウイルス対策ソフトやファイルエクスプローラーがワールドフォルダを開いています。<br>"
	           + "• 前回のプロセスが正常に終了せず、ファイルがロックされたままになっています。</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
	    return "• <b>すべてのサーバーインスタンスを終了してください</b>（javaw.exeなどのバックグラウンドプロセスも含む）。<br>"
	           + "• ホスティングパネル（Multicraftなど）を使用している場合は、パネルからサーバーを完全に再起動してください。<br>"
	           + "• ファイルをブロックしている可能性がある場合は、<b>ウイルス対策ソフトを一時的に無効にしてください</b>。<br>"
	           + "• ローカルシステムでは、ワールドフォルダを表示しているWindowsエクスプローラーのウィンドウをすべて閉じてください。<br>"
	           + "• 問題が解決しない場合、ワールドフォルダ内の <code>session.lock</code> ファイルを手動で削除してください（別のサーバーが動作していないことを確認してから）。";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
	    return "他のプロセスによってロックされたワールドファイル";
	}
	
	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "MODがクラス <code>" + clasePadreFinal + "</code> を継承しようとしましたが、"
	           + "このクラスは現在<b>final</b>として定義されており、継承できません。<br>"
	           + "問題のあるクラスは：<code>" + claseHija + "</code>です。<br><br>"
	           + "これは通常、Minecraftまたは他の基本MODの古いバージョン向けにコンパイルされたMODで発生します。"
	           + "これらの基本MODは最近のバージョンでクラスを<code>final</code>にしています。</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
	    return "• <b>関係するすべてのMODを更新してください</b>。特に言及されている基本MODに関連している可能性があるMODに注意。<br>"
	           + "• 問題が解決しない場合は、現在のMinecraftバージョンとその依存関係と互換性のあるMODのバージョンを探してください。<br>"
	           + "• 場合によっては、子クラスを含むMODを一時的に削除することで原因を特定できます。";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
	    return "finalクラスからの継承試行";
	}
	
	@Override
	public String errorRubidiumObsoletoConIris() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "<b>Rubidium</b>（Forge用のSodiumの廃止されたフォーク）を<b>IrisまたはOculus</b>と一緒に使用しています。<br>"
	           + "最近のMinecraftバージョン（1.19.2以降）では、"
	           + "RubidiumはSodiumの進化に追いついておらず、依存関係に問題が生じています。<br><br>"
	           + "パフォーマンスMOD（Sodium、Rubidium、Embeddium、Bedium、Xeoniumなど）またはIris Shadersと他のMODとの間で競合がある場合にも、このエラーが発生する可能性があります。</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
	    return "• <code>mods</code>フォルダから<b>Rubidiumを削除してください</b>。<br>"
	           + "• <b><a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a>をインストールしてください</b>、"
	           + "これは1.20+でIris/Oculusをサポートする、アクティブで互換性のあるForge用Sodiumのフォークです。<br>"
	           + "• 同時に複数のSodiumフォーク（例：RubidiumとEmbeddium）をインストールしないようにしてください。<br>"
	           + "• IrisではなくOculusを使用している場合は、それもあなたのForgeおよびEmbeddiumのバージョンと互換性があるか確認してください。";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
	    return "非推奨のRubidiumとIris/Oculus併用（OptionInstanceはfinal）";
	}
	
	@Override
	public String errorVoiceChatPuertoOcupado() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "<code>Simple Voice Chat</code> MODは、UDPポートが既に使用中であるか、設定されたIPアドレスが無効なため、音声サーバーを開始できません。<br>"
	           + "これはゲームの起動を妨げませんが、音声チャット機能が無効になります。</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
	    return "• <b>Minecraftの他のインスタンスや</b> UDPポート24454を使用しているアプリケーションを終了してください。<br>"
	           + "• サーバー上で動作している場合は、<b>他のサービスが</b>そのポートを使用していないことを確認してください。<br>"
	           + "• MODの設定 (<code>config/voicechat/</code>) で、UDPポートを空いているもの（例：24455）に変更してください。<br>"
	           + "• カスタムIPアドレスを使用している場合は、正しいか確認するか、デフォルトを使用するために空白のままにしてください。";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
	    return "ボイスチャット：UDPポートが使用中または無効なIP";
	}
	
	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "BlockItem <code>" + nombreBlockItem + "</code> がnullのブロックを参照しています。<br>"
	           + "このエラーは通常、<b>Createのアドオン</b>（例：<code>dndecor</code>、<code>createdeco</code>）で発生し、"
	           + "<code>Amendments</code>や<code>Moonshine</code>との競合、またはブロックの初期化ミスが原因です。<br>"
	           + "<b>注：</b>これはAmendments自体の直接的なエラーではなく、レジストリ読み込みにおけるより深い問題の症状です。</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
	    return "• <b>関連するすべてのMODを更新してください：</b>Create、Amendments、Moonshine、およびアドオン（特に<code>dndecor</code>と<code>createdeco</code>）を更新します。<br>"
	           + "• 問題が解決しない場合、<b>Createアドオンを一時的に1つずつ削除して</b>原因を特定してください。<br>"
	           + "• <b>AmendmentsとMoonshineが使用しているCreateおよびForgeのバージョンと互換性があるか</b>確認してください。<br>"
	           + "• 問題のあるアドオンにベータ版または更新されたフォークがあるか確認してください。";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
	    return "Createアドオン内のnull BlockItem";
	}
	
	
	
	
	
}
