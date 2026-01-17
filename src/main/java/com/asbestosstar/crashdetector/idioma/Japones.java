package com.asbestosstar.crashdetector.idioma;

import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

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
				+ "'>Theseusには、削除を試みてもMODを削除できないなどの追加の問題があります。mrpackファイルを使用する必要がある場合は、Prism Launcher（modrinth.com専用）、ATLauncher（modrinth.com専用）、Hello Minecraft Launcher（modrinth.comおよびbbsmc.net対応）などの他のランチャーをご利用ください。</b>";
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
				+ "'>ここに検証結果を示します。ゆっくりと確認してください。通常、正しい原因はチェック1または2にあります。それ以降（エラー3以上）は確認用に使えますが、一般的には連鎖的なエラーなので無視して構いません。障害は層状に発生するため、根本的な問題を解決すればこの特定のエラーは解消されます。ただし、あるエラーが別のエラーのコンソール表示を妨げることが多いため、明日になって関係のない新しいエラーが現れる可能性があります。</b>";
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

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "警告！ Crash Assistant は偽のマルウェア検出ツールです。意図的にゲームの起動をブロックし、ターゲットとなる MOD を使用してプレイする自由を無視します。 "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>MalwareMod.java のコードを見る</a>   "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>JarInJarHelper.java のコードを見る</a>. "
				+ "現時点ではこの MOD だけがリストに載っており、彼らは実際にはデフォルトのログサイトのみを対象としています。このサイトはユーザーが変更可能で、組み込みのログ共有機能を明示的に選択した場合にのみ動作します。CrashAssistant はどのログサイトが設定されているかチェックせず、また変更方法（共有ダイアログの下部にあるドロップダウン）を説明しません。設定されているサイトに関係なく、CrashAssistant はゲームの起動をブロックします。メッセージの中で彼らは独自の調査をするように言っていますが、その通りにしてください。CrashDetector と Crash Assistant のコードを調べ、何をしているのか理解してください。権威への信頼に頼らないでください。</b>";
	}

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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ワールドがチャンクを読み込んでいる際に例外が発生しました。お使いのプラットフォーム向けに存在する場合、FeatureRecycler で問題を解決できる可能性があります。https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
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
		StringBuilder sb = new StringBuilder("<b>レジストリに密度関数が不足しています。</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("不足しているもの: ");
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
				"<br/><b>解決方法:</b> これらの関数を定義する mod/datapack をインストールまたは有効にして再起動してください。この問題のもう一つのよくある原因は、必要な mod はインストールされているものの、その mod に問題があるか、他の mod と競合している場合です。例えば、Terralith は多くの問題を抱えており、このエラーや JSON エラーを含むさまざまな問題を引き起こす可能性があります。");
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
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();

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
		return "• MOD <code>healight</code> を削除または更新してください。 " + "現在のバージョンは1.20.1用のMinecraftForge 47.10と互換性がありません。 "
				+ "MODの最新版を探すか、代替案の使用を検討してください。";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "重大なエラー：healight - フィールド 'INT' が見つかりません";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("クラス <code>").append(clase)
				.append("</code> が必須メソッドを実装していません：<br>").append("<code>").append(metodo).append("</code><br>")
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
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "MODが<b>専用サーバー</b>上で<b>クライアント側</b>のクラス "
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
				+ "<code>Sinytra Connector</code> のMODの設定ファイルが破損しています。 " + "これは、ゲームの予期しない終了、書き込みエラー、またはMOD間の競合により、"
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
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "ファイル <code>" + nombreJar
				+ "</code> が破損しているか不完全です。<br>" + "最終的なZIPファイルのヘッダーが欠落しているため、システムはその内容を読み取れません。<br>"
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
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "NBTファイルの一つが破損しているため、ワールドを読み込めません "
				+ "(例: <code>level.dat</code>、<code>playerdata/*.dat</code>、またはチャンクデータ)。<br>"
				+ "具体的なエラーは: <code>UTFDataFormatException: バイト " + byteCorrupto + " 付近で不正な入力形式</code>です。<br><br>"
				+ "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ 修復を試みる前に、ワールドフォルダ全体を完全にバックアップしてください。</b><br><br>"
				+ "破損したファイルは、<a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>のような<b>NBTエディタ</b>を使用して修復できます。<br>"
				+ "深刻な損傷の場合は、<b>16進数エディタ</b>(HxDなど)を使って無効なバイトを検査・修正できます " + "(ただし、NBTフォーマットに精通している場合のみ推奨)。<br>"
				+ "最終手段として、バックアップから復元するか、<code>FTB Backup</code>などのMODが提供する<i>ワールド修復</i>機能を使用してください。</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>修復を試みる前に、ワールドフォルダを完全にバックアップしてください</b>。<br>" + "• NBTエディタ(NBT Studioなど)を使用して、破損したファイルを開き修正します。<br>"
				+ "• 失敗した場合は、壊れたバイトの位置で16進数エディタでファイルを確認します。<br>" + "• 経験がなければ、最近のバックアップから復元してください。";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "ワールド破損：NBTデータの読み込みエラー";
	}

	@Override
	public String problema_con_openAL() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>You have an issue with OpenAL. Sometimes Nouveau drivers can cause this, but sometimes the application's bundled OpenAL version is incompatible with the one in your distribution and you need to use your distro's version. This is especially common with Red Hat-based distributions and sound mods like Sound Physics Remastered. See this guide for more help: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>How to fix Minecraft sound problems using Linux</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "他のプロセスがワールドファイルをロックしているため、サーバーを起動できません。<br>"
				+ "これは以下のいずれかの場合に発生します：<br>" + "• すでにサーバーのインスタンスが実行中です。<br>"
				+ "• ウイルス対策ソフトやファイルエクスプローラーがワールドフォルダを開いています。<br>" + "• 前回のプロセスが正常に終了せず、ファイルがロックされたままになっています。</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "MODがクラス <code>" + clasePadreFinal
				+ "</code> を継承しようとしましたが、" + "このクラスは現在<b>final</b>として定義されており、継承できません。<br>" + "問題のあるクラスは：<code>"
				+ claseHija + "</code>です。<br><br>" + "これは通常、Minecraftまたは他の基本MODの古いバージョン向けにコンパイルされたMODで発生します。"
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
				+ "最近のMinecraftバージョン（1.19.2以降）では、" + "RubidiumはSodiumの進化に追いついておらず、依存関係に問題が生じています。<br><br>"
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
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "BlockItem <code>" + nombreBlockItem
				+ "</code> がnullのブロックを参照しています。<br>"
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

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>")
				.append("アクティブなプラットフォーム（Forge、Fabricなど）に属さないMODが見つかりました：<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>これは通常、以下のいずれかの状況で発生します：<br>").append("• 同じフォルダ内に<b>FabricとForge</b>のMODが混在している。<br>")
				.append("• 非互換なバージョンのMinecraft向けMODをインストールしている。<br>").append("• MODが破損しているか、有効なJARファイルではない。</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>すべてのMODが同じプラットフォーム用であることを確認してください</b>（Forge<b>または</b>Fabric、両方ではない）。<br>"
				+ "• <b>MODツリー</b>を使用して、各ファイルがどのプラットフォームとして検出されているかを確認してください。<br>"
				+ "• 見慣れないMODや異なるプラットフォーム向けのMODは削除してください。<br>"
				+ "• CurseForgeやPrismなどのランチャーを使用している場合は、プロファイルが正しく設定されていることを確認してください。";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "アクティブローダーと互換性のないMOD";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "モデル <code>" + modid + ":" + nombreModelo
				+ "</code> の作成に失敗しました。<br>" + "これは、モド <code>" + modid + "</code> のリソースが破損しているか、不足しているか、"
				+ "または使用中のMinecraftバージョンと互換性がないことを示しています。</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>MODを更新してください</b>、あなたのインスタンスと互換性のある最新バージョンへ。<br>" + "• 開発版やカスタムビルドを使用している場合は、公式リリースに戻してください。<br>"
				+ "• JARファイルが破損していないか確認してください（再インストール）。<br>" + "• 問題が解決しない場合は、このログを添えてMOD制作者にエラーを報告してください。";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "リソースモデルの作成に失敗";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "<code>Moonlight</code> と <code>Iceberg</code> モドの間で重大な競合が検出されました。<br>"
				+ "両方とも互換性のない方法でリソース再読み込みシステムを登録しようとしており、" + "有効なグラフィックコンテキストがないため、OpenGLエラーが発生します。<br>"
				+ "これは、Fabric MOD用アダプターを含むForgeバージョンを使用している場合に一般的な問題です。</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>両方のMODを</b> あなたのForgeバージョンと互換性のある最新版に更新してください。<br>"
				+ "• 問題が解決しない場合は、<b>一時的にIcebergを削除してください</b>。Moonlightは他の多くのMODにとって重要な依存関係であることが多いからです。<br>"
				+ "• これらのMODの重複または混在したForge/Fabricバージョンをインストールしていないことを確認してください。<br>"
				+ "• 別のMOD（Supplementaries、Citadelなど）がすでにIcebergの機能を内部に組み込んでいるかどうかを確認してください。";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "重大な衝突：Moonlight 対 Iceberg (OpenGLコンテキストなし)";
	}

	@Override
	public String instantanea() {
		return "スナップショット";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "前回のスナップショット以降";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "ファイルを選択";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "スナップショットが正常に作成されました";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "スナップショットの作成中にエラーが発生しました";
	}

	@Override
	public String consejo() {
		return "ヒント";
	}

	@Override
	public String resultadoMuestra() {
		return "結果を表示";
	}

	@Override
	public String historaDeModsDesc() {
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>ヒント:</b> 2つの履歴ファイルを選択して、MODリストを比較します。 "
				+ "  結果は正規化された名前に基づいて、<span style='color:%s;'>追加された (+)</span>と "
				+ "  <span style='color:%s;'>削除された (&#8722;)</span> を表示します。 "
				+ "  「スナップショット」ボタンを使用して、拡張子が .instantanea の既存ファイルのコピーを作成できます。" + "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		return "レポートなしでログリンクをMarkdown形式で取得";
	}

	@Override
	public String titulo_configuracion() {
		return "設定";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "共有中に予期しないエラーが発生しました。";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "リンク生成中に予期しないエラーが発生しました。";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "ボタン処理中に予期しないエラーが発生しました。";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "開くファイルが関連付けられていません。";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "ファイルが存在しません：\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "エディタで開けませんでした。\nパスがクリップボードにコピーされます。";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "ファイルを開けませんでした。パスはクリップボードにコピーされました。";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "デスクトップがサポートされていません。パスはクリップボードにコピーされました。";
	}

	@Override
	public String limite_de_solicitudes() {
		return "リクエスト制限に達しています。別のログサイトまたは別のログAPIを使用してください。";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		return "リンクを共有";
	}

	@Override
	public String infoDeTrazos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ログの上部を修正することが最優先事項です。 " + "形式は「レベル、行」です。 "
				+ "すべてのログには番号体系があります。 " + Verificaciones.nl_html
				+ "一般的に、すべてのログで最も低いレベルを探る必要があります。高いレベルのトレースは、多くの場合誤検出です。 "
				+ "トレースが多数ある場合、トレース分析は完璧ではないため、コンソールを直接読む能力を使うことが重要です。" + "</b>";
	}

	// --- 検索用ワラント・キャナリー (Warrant Canary) ---
	/**
	 * 検索用ワラント・キャナリーのボタンに表示されるテキスト。 例：「検索用ワラント・キャナリー」
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "検索用ワラント・キャナリー";
	}

	/**
	 * 機能が近日中に利用可能になることを通知する ダイアログボックスに表示されるメッセージ。
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "この機能は近日中にご利用いただけます。";
	}

	/**
	 * 検索用ワラント・キャナリーの 近日提供について通知するダイアログボックスのタイトル。
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "近日リリース";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		return "Crash Assistantと互換性のないMOD（誤検出）";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		return "CrashAssistantを使用するMODパックとの互換性エラー";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistantは互換性がないとしているMODのリストを持っていますが、実際に互換性がないという証拠はなく、エラーメッセージも英語のみです。これらのMODでプレイしたい場合は、<code>config/crash_assistant/config.toml</code>ファイルを編集し、[compatibility]セクションの<code>enabled = true</code>を<code>enabled = false</code>に変更できます。</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash AssistantはMODを互換性なしと判定する機能がありますが、時には誤った判定であり、エラーメッセージは1か国語しかありません。これらのMODを使用したい場合は、<code>config/crash_assistant/problematic_mods_config.json</code>ファイルを編集して、<code>should_crash_on_startup</code>を<code>true</code>から<code>false</code>に変更してください。</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "エラー：MOD '" + modId + "' は MOD '"
				+ dependencia + "' を必要とします。現在、" + actual + "。" + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "エラー：MOD '" + modId + "' は '" + dependencia
				+ "' のバージョン '" + requerido + "' 以上を必要としますが、そのMODはインストールされていません。" + "</span>";
	}

	// クラス MonitorDePID.idioma に（このメソッドを追加）
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "エラー: モド '" + modId + "' は '" + dependencia
				+ "' の現在のバージョンと互換性がありません。 "
				+ "'Iris/Oculus & GeckoLib Compat' モドを削除する必要があります。これは Superb Warfare と互換性がなく、最新版の GeckoLib では動作しません。 "
				+ "現在のバージョン: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "エラー: クラス '" + clase + "' のタスクを実行できませんでした。 " + "このエラーは、互換性のないMODや他のインストール済みMODと競合するMODでよく発生します。";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "タスク実行失敗";
	}

	public String recomendacion_fallos_ejecucion() {
		return "この種のエラーは、MOD間の互換性の問題が原因で発生することが多いです。 " + "特にConnectorModと正しく動作しないMODで一般的です。";
	}

	public String info_clase_problematica() {
		return "問題のあるクラス：";
	}

	public String ver_en_log() {
		return "ログで表示";
	}

	public String no_se_encontraron_clases_problema() {
		return "実行に問題がある特定のクラスは見つかりませんでした。";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFineとEntity Model Features (EMF)の間に重大な競合が検出されました。"
				+ "これらのMODは互換性がなく、ゲームの起動を妨げるインジェクションエラーを引き起こします。" + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "OptiFineとEntity Model Featuresの衝突";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "OptiFineまたはEntity Model Featuresのいずれかをアンインストールしてください。これらは互いに互換性がありません。";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "OptiFineとFusionの間に重大な競合が検出されました。 "
				+ "これらのMODは互換性がなく、ゲームの起動を妨げるインジェクションエラーを引き起こします。" + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "OptiFineとFusionの衝突";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "OptiFineまたはFusionをアンインストールしてください。両者は互いに互換性がありません。";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel（Createが必要とするもの）はSodium 0.6.0-beta.2以上を必要とします。Rubidiumは0.5.3です。"
				+ "代替として<a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a>の使用を検討してください。"
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "FlywheelとSodiumバージョンの衝突";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "Sodiumを0.6.0-beta.2以上に更新するか、互換性のある代替として<a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a>をインストールしてください。";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "OptiFineとEpic Fightの間に重大な競合が検出されました。 "
				+ "これらのMODは互換性がなく、ゲームの起動を妨げるインジェクションエラーを引き起こします。" + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "OptiFineとEpic Fightの衝突";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "OptiFineまたはEpic Fightをアンインストールしてください。両者は互いに互換性がありません。";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "OptiFine と Rubidium の間に深刻な競合が検出されました。 "
				+ "これらのモッドは互換性がなく、インジェクションの失敗を引き起こし、ゲームの起動を妨げます。" + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "OptiFine と Rubidium の競合";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "OptiFine または Rubidium をアンインストールしてください。両者は互換性がありません。";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam が専用サーバーでの読み込みを試みていますが、これはクライアントでのみ互換性があります。 "
				+ "サーバーから FreeCam を削除するか、クライアントにのみインストールされていることを確認してください。" + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "専用サーバー上の FreeCam";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "FreeCam はクライアントにのみインストールする必要がありますので、専用サーバーから削除してください。";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) が専用サーバーでの読み込みを試みていますが、これはクライアントでのみ互換性があります。 "
				+ "サーバーから ETF を削除するか、クライアントにのみインストールされていることを確認してください。" + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "専用サーバー上の Entity Texture Features";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features はクライアントにのみインストールする必要がありますので、専用サーバーから削除してください。";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "サーバーを起動するには、MinecraftのEULAに同意する必要があります。 "
				+ "eula.txtファイルを編集して、'eula=false'を'eula=true'に変更してください。" + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "Minecraft EULA 未承認";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "サーバーフォルダ内の eula.txt ファイルを編集し、'eula=false'を'eula=true'に変更してください。";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine が専用サーバーでの読み込みを試みていますが、これはクライアントでのみ互換性があります。 "
				+ "サーバーから OptiFine を削除するか、クライアントにのみインストールされていることを確認してください。" + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "専用サーバー上の OptiFine";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "OptiFine はクライアントにのみインストールする必要がありますので、専用サーバーから削除してください。";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks が誤って 1.20.1 向けとマークされていますが、1.21.1 のメソッドを使用しています。 "
				+ "モッドは 1.20.1 に存在しない ResourceLocation.fromNamespaceAndPath を使用しようとしています。" + "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "Iron's Spellbooks のバージョンエラー";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "使用している Minecraft バージョンと互換性のある正しいバージョンの Iron's Spellbooks を使用していることを確認してください。";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "OptiFine と Embeddium の間に深刻な競合が検出されました。 "
				+ "これらのモッドは互換性がなく、インジェクションの失敗を引き起こし、ゲームの起動を妨げます。" + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "OptiFine と Embeddium の競合";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "OptiFine または Embeddium をアンインストールしてください。両者は互換性がありません。";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>これは、特に Terralinth、AmplifiedNether、Nullscape、Incendium などの世界生成系 mod 同士の競合でよく発生します。不足している mod をインストールする必要がある場合もあります。</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable が専用サーバーでの読み込みを試みていますが、これはクライアントでのみ互換性があります。 "
				+ "サーバーから Controllable を削除するか、クライアントにのみインストールされていることを確認してください。" + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "専用サーバー上の Controllable";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "Controllable はクライアントにのみインストールする必要がありますので、専用サーバーから削除してください。";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries がエラーを引き起こしており、サーバーの読み込みを妨げています。 " + "この mod は火の挙動の登録で問題を抱えており、datapack の読み込み中に失敗します。"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries がサーバー読み込みを妨害";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "Supplementaries を最新バージョンに更新するか、一時的に無効にしてサーバーの読み込みを可能にしてください。";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) は、不足している Jackson モジュールに関する問題を検出しました。 "
				+ "Valkyrien Skies のような一部の mod は、必要な依存関係をすべて含んでいないため、このエラーを引き起こす可能性があります。" + "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "Groovy Modloader で Jackson モジュールが不足";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "依存関係の競合を引き起こす可能性のある Groovy Modloader や Valkyrien Skies などの関連 mod を削除してください。";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Every Compat が無効な木材ブロック名を検出しました。 "
				+ "Every Compat は通常、多くの問題を抱えています。使用しないでください！" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "Every Compat の無効な名前";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "Every Compat を使用しているリソースパックや mod を確認してください。無効なブロック名が含まれている可能性があります。";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "エラーコード (-1073741819) が検出されました。これは Razer の GameCaster、Discord、OBS Studio などのオーバーレイや、NVIDIA ドライバーの問題によって引き起こされる可能性があります。"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "エラーコード -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "GameCaster、Discord、OBS Studio などのオーバーレイを無効化し、NVIDIA ドライバーが最新であることを確認してください。";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips は dependencia として Immersive Messages を必要としますが、インストールされていません。 "
				+ "Immersive Tooltips を正しく動作させるには、Immersive Messages をインストールしてください。" + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "dependencia のない Immersive Tooltips";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips に必要な dependencia である Immersive Messages をインストールしてください。";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins は Apoli Mod との互換性に問題があり、ItemStack を EntityLinkedItemStack にキャストできません。 "
				+ "これはバージョン 6.6.0 より新しいものでよく発生します。以前のバージョンを使用するか、Fabric と Forge バージョンを切り替えることを検討してください。" + "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "Medieval Origins のキャストエラー";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "Medieval Origins の 6.6.0 以前のバージョンを使用するか、mod の Fabric と Forge バージョンを切り替えてみてください。";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether が、MusicManager に存在しない Registry Object に関連するエラーを引き起こしています。 "
				+ "この問題は Reign of Nether の MusicManager mixin に関連しています。" + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "Reign of Nether の MusicManager エラー";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "このエラーを解決するには、Reign of Nether を更新するか、一時的に削除してみてください。";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel は Linux または Android 上の YSM サーバーのみをサポートしています。 "
				+ "この問題は、2025年11月23日以降に Modrinth で公開された新しいバージョンで修正されています。" + "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel が Linux と非互換";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "この問題は 11月23日以降に修正されているため、Modrinth から YesSteveModel を最新版に更新してください。";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Moving Elevators と OptiFine の間に深刻な競合が検出されました。 " + "これらのモッドは互換性がなく、インジェクションの失敗を引き起こし、ゲームの起動を妨げます。"
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "Moving Elevators と OptiFine の競合";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "OptiFine または Moving Elevators をアンインストールしてください。両者は互換性がありません。";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Fabric API（fabric-resource-loader-v0）と OptiFine の間に深刻な競合が検出されました。 "
				+ "これらのモッドは互換性がなく、インジェクションの失敗を引き起こし、ゲームの起動を妨げます。" + "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "Fabric API と OptiFine の競合";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "OptiFine をアンインストールするか、Fabric API を互換性のあるバージョンに更新してください。";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ある mod にインスタンス化できない欠陥のある ITransformationService があります: " + claseProveedor + "。"
				+ "ゲームを読み込めるようにするには、この mod を削除する必要があります。" + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "欠陥のある ITransformationService";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "欠陥のある ITransformationService を含む " + claseProveedor + " クラスを含む mod を削除してください。";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError() + "'>ある mod が無効なバージョン指定を持っています。 "
				+ "バージョンは角括弧で囲む必要があります。 " + "サイドパネルの grep/greprf ユーティリティを使用して、バージョン </span>" + version
				+ "<span style='color:#" + config.obtenerColorError() + "'> を検索し、問題のある mod を特定できます。</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "mod 内の無効なバージョン";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "サイドパネルの grep/greprf ユーティリティを使用して問題のバージョンを検索し、それを含む mod を特定してください。";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "stack smashing エラーが検出され、プロセスが終了しました。 "
				+ "これは、Forge/NeoForge/PillowMC の Early Window の問題、または LWJGL 3.2.2 以降のバージョンに起因する可能性があります。" + "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "Stack Smashing 検出";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "Early Window の設定を確認し、別のバージョンの LWJGL を使用するか、早期ウィンドウ関連の mod を見直してください。";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore は特定の modpack 専用であり、汎用インストールで使用してはいけません。使用すると問題が発生します。" + "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "Java バージョンと非互換の GregTechEasyCore";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore を削除してください。これは特定の modpack 専用であり、あなたの汎用インストールと互換性がありません。";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "MoniLabs と Connector Extras の間で、KubeJS の改造に関連する競合が検出されました。 " + "これらの mod は、KubeJS の改造内容が互換性ありません。"
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "MoniLabs と Connector Extras の競合";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "KubeJS の改造内容が競合しているため、MoniLabs または Connector Extras のいずれかをアンインストールしてください。";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris には Distant Horizons [2.0.4] または DH API バージョン [1.1.0] 以降が必要です。"
				+ "問題を解決するには、互換性ガイドをご確認ください: https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Iris と Distant Horizons の互換性";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "互換性ガイド（https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e）を確認し、Iris および Distant Horizons を互換バージョンに更新してください。";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Minecraft のクラスが不足しています。考えられる原因:</b>" + "<ul>"
				+ "<li>ゲームの他のバージョン向けの mod を使用しています。<a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> を使って、そのクラスがあなたのバージョンに存在するか確認できます。</li>"
				+ "<li>Minecraft のインストールが破損しています（CurseForge App、ModrinthApp/Theseus/Astralrinth などの modpack ランチャーでよく発生します）。CurseForge の問題解決には<a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>チュートリアルを視聴</a>してください。</li>"
				+ "<li>不具合のある coremod があります（coremod が失敗すると、クラスの読み込みをブロックする可能性があります）。</li>" + "</ul>"
				+ "<p>注: 名前に '/' を使用している場合、サイドバーの <b>grepr/fgrepr</b> ツールで不足しているクラスを参照している mod を特定できます。</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError() + "'>DangerZone のクラスが不足しています。考えられる原因:</b>" + "<ul>"
				+ "<li>ゲームの他のバージョン向けの mod を使用しています。</li>" + "<li>不具合のある coremod があります。</li>"
				+ "<li>ランチャーやインストールが破損しています。</li>" + "</ul>"
				+ "<p>注: 名前に '/' を使用している場合、サイドバーの <b>grepr/fgrepr</b> ツールで不足しているクラスを参照している mod を特定できます。</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError() + "'>FeatureCreep のクラスが不足しています。考えられる原因:</b>" + "<ul>"
				+ "<li>他のバージョンの FeatureCreep 向けの mod を使用しています（例: ESR と Nightly、または v4 と v12）。</li>"
				+ "<li>CurseForge または MinecraftStorage から FeatureCreep をインストールできます。</li>" + "</ul>"
				+ "<p>注: 名前に '/' を使用している場合、サイドバーの <b>grepr/fgrepr</b> ツールで不足しているクラスを参照している mod を特定できます。</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ModLauncher のクラスが不足しています。考えられる原因:</b>" + "<ul>"
				+ "<li>mod が MinecraftForge、PillowMC、または NeoForge の別のビルド向けになっています（ModLauncher はこれらのローダーで使用されます）。</li>"
				+ "<li>Minecraft の1つのバージョンに対し、多数の modloader 更新があります。</li>"
				+ "<li>ランチャーのインストールが破損しています（CurseForge App、ModrinthApp/Theseus/Astralrinth などの modpack ランチャーでよく発生します）。CurseForge の問題解決には<a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>チュートリアルを視聴</a>してください。</li>"
				+ "</ul>" + "<p>注: 名前に '/' を使用している場合、サイドバーの <b>grepr/fgrepr</b> ツールで不足しているクラスを参照している mod を特定できます。</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Minecraft Forge のクラスが不足しています。考えられる原因:</b>" + "<ul>"
				+ "<li>mod が MinecraftForge の別のビルド向けになっています。</li>"
				+ "<li>Minecraft の1つのバージョンに対し、多数の modloader 更新があります。</li>"
				+ "<li>インストールが破損しています（CurseForge App、ModrinthApp/Theseus/Astralrinth などの modpack ランチャーでよく発生します）。CurseForge の問題解決には<a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>チュートリアルを視聴</a>してください。</li>"
				+ "</ul>" + "<p>注: 名前に '/' を使用している場合、サイドバーの <b>grepr/fgrepr</b> ツールで不足しているクラスを参照している mod を特定できます。</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError() + "'>NeoForge のクラスが不足しています。考えられる原因:</b>" + "<ul>"
				+ "<li>mod が NeoForge の別のビルド向けになっています。</li>" + "<li>Minecraft の1つのバージョンに対し、多数の modloader 更新があります。</li>"
				+ "<li>インストールが破損しています（CurseForge App、ModrinthApp/Theseus/Astralrinth などの modpack ランチャーでよく発生します）。CurseForge の問題解決には<a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>チュートリアルを視聴</a>してください。</li>"
				+ "</ul>" + "<p>注: 名前に '/' を使用している場合、サイドバーの <b>grepr/fgrepr</b> ツールで不足しているクラスを参照している mod を特定できます。</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Fabric Loader のクラスが不足しています。考えられる原因:</b>" + "<ul>"
				+ "<li>mod が Fabric Loader の別のビルド向けになっています。</li>"
				+ "<li>Minecraft の1つのバージョンに対し、多数の modloader 更新があります。</li>"
				+ "<li>インストールが破損しています（CurseForge App、ModrinthApp/Theseus/Astralrinth などの modpack ランチャーでよく発生します）。CurseForge の問題解決には<a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>チュートリアルを視聴</a>してください。</li>"
				+ "<li>多くの mod は Fabric API を必要とします。必要に応じて Fabric API をインストールしてください。</li>" + "</ul>"
				+ "<p>注: 名前に '/' を使用している場合、サイドバーの <b>grepr/fgrepr</b> ツールで不足しているクラスを参照している mod を特定できます。</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError() + "'>PillowMC のクラスが不足しています。考えられる原因:</b>" + "<ul>"
				+ "<li>mod が PillowMC の別のビルド向けになっています。</li>" + "<li>Minecraft の1つのバージョンに対し、多数の modloader 更新があります。</li>"
				+ "<li>インストールが破損しています（CurseForge App、ModrinthApp/Theseus/Astralrinth などの modpack ランチャーでよく発生します）。CurseForge の問題解決には<a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>チュートリアルを視聴</a>してください。</li>"
				+ "</ul>" + "<p>注: 名前に '/' を使用している場合、サイドバーの <b>grepr/fgrepr</b> ツールで不足しているクラスを参照している mod を特定できます。</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "意図的にラグを引き起こす mod がインストールされています。Uranium はラグ mod です。常にクラッシュするわけではありませんが、最終的にはクラッシュする可能性があります。"
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack は 1.19.* 互換と表示されていますが、実際には 1.20.* 用のため、「クラスが見つかりません」エラーが発生します。"
				+ "この mod は、現在使用中の Minecraft バージョンに存在しない DamageSources を使用しようとします。" + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Falling Attack のバージョンエラー";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "使用中の Minecraft バージョンと互換性のある正しいバージョンの Falling Attack を使用していることを確認してください。";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>").append("この機能を使用するには、CFR (Class File Reader) をインストールする必要があります。<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append("Linux、NetBSD、FreeBSD システムでは、パッケージマネージャーから CFR をインストールできます。<br>").append(
					"こちらでパッケージを検索してください: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append("または、FabricMC が使用している修正版を以下からダウンロードできます:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("以下のフォルダに保存してください:<br>").append("<b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>").append("⚠️ <b>重要:</b> CFR をインストール後、mod を再起動しないと正しく認識されません。").append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "利用可能な肖像画なし";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "クラスが見つかりません: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "CFR デコンパイラ – Sakura Riddle（非公式）";
	}

	@Override
	public String cfrClaseActual() {
		return "現在のクラス";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "Sakura Riddle の肖像画";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "肖像画の読み込みエラー";
	}

	public String noticiaLegalCFR() {
		return "この mod の逆コンパイル用グラフィカル・ユーザー・インターフェース（GUI）は、ソフトウェアのクラッシュ原因を特定するためのものです。"
				+ "ただし mod の逆コンパイルは場合によって必要となることがありますが、生成されたコードを用いて著作権法に違反しないようご注意ください。"
				+ "コードを使用する前に、該当 mod のライセンスを確認することを推奨します。多くの mod は公式にソースコードを公開しており、"
				+ "これは逆コンパイル結果と比べて通常、より明確で理解しやすいです。知的財産権およびライセンスへの配慮は、"
				+ "mod 開発コミュニティにとって不可欠です。メキシコ連邦著作権法については以下をご参照ください："
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Ley Federal de Derechos de Autor（スペイン語）</a>、"
				+ "英語版はこちら：<a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (English)</a>。"
				+ "CurseForge 上での利用を考慮し、米国著作権法も以下に示します："
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>。"
				+ "また、ユーザーご自身の居住地に適用される法律を必ずご確認ください。" + "本 GUI は簡易診断専用です。詳細な解析には FabricMC 版 Enigma をご利用ください："
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>。ソースコードが公開されていない場合に JAR ファイルを直接編集してパッチを作るには、Recaf をご検討ください："
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">公式サイト</a>。";
	}

	@Override
	public String botonDescargarCfr() {
		return "CFR をダウンロード";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "インストールフォルダを開く";
	}

	@Override
	public String colorFondoPrincipal() {
		return "メイン背景色";
	}

	@Override
	public String colorTextoBotonReset() {
		return "リセットボタンの文字色";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "検索フィールドの文字色";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "フィルターのドロップダウンメニュー文字色";
	}

	@Override
	public String colorTextoRenderer() {
		return "レンダラーの文字色";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "ロードオーバーレイの文字色";
	}

	@Override
	public String colorBorde() {
		return "枠線の色";
	}

	@Override
	public String colorFondoRetrato() {
		return "ポートレートモードの背景色";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "共有リンクの色";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "共有フィールドの背景色";
	}

	@Override
	public String rosaFondo() {
		return "背景ピンク";
	}

	@Override
	public String rosaSuave() {
		return "ソフトピンク";
	}

	@Override
	public String moradoAcento() {
		return "アクセントパープル";
	}

	@Override
	public String textoOscuro() {
		return "ダークテキスト";
	}

	@Override
	public String bordeSuave() {
		return "ソフトボーダー";
	}

	@Override
	public String fondoCampo() {
		return "フィールド背景";
	}

	@Override
	public String fondoVistaPrevia() {
		return "プレビュー背景";
	}

	@Override
	public String sintaxisConstructor() {
		return "構文色: コンストラクタ";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "構文色: ヘルプメッセージ";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "構文色: HTMLタグ";
	}

	@Override
	public String colorFondoVentana() {
		return "ウィンドウ背景色";
	}

	@Override
	public String colorPanel() {
		return "パネル色";
	}

	@Override
	public String colorBotonTexto() {
		return "ボタン文字色";
	}

	@Override
	public String colorCampo() {
		return "フィールド色";
	}

	@Override
	public String colorBordeDestacado() {
		return "強調枠線色";
	}

	@Override
	public String colorSeleccionTexto() {
		return "テキスト選択背景色";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "選択されたテキストの色";
	}

	@Override
	public String colorEstadoExito() {
		return "ステータス色: 成功";
	}

	@Override
	public String colorEstadoFallo() {
		return "ステータス色: 失敗";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "ステータス色: 即時";
	}

	@Override
	public String colorResultadoAnadido() {
		return "追加された結果の色";
	}

	@Override
	public String colorResultadoEliminado() {
		return "削除された結果の色";
	}

	@Override
	public String colorBordeScroll() {
		return "スクロールバーの枠線色";
	}

	@Override
	public String colorFondoPanel() {
		return "パネル背景色";
	}

	@Override
	public String colorBeigeListas() {
		return "リストベージュ";
	}

	@Override
	public String colorTextoListas() {
		return "リストの文字色";
	}

	@Override
	public String colorBordeListas() {
		return "リストの枠線色";
	}

	@Override
	public String colorBotonFondo() {
		return "ボタンの背景色";
	}

	@Override
	public String colorBordeBoton() {
		return "ボタンの枠線色";
	}

	@Override
	public String colorDoradoTexto() {
		return "金色の文字色";
	}

	@Override
	public String colorPila() {
		return "スタックトレースの色";
	}

	@Override
	public String colorTextoPanel() {
		return "パネルの文字色";
	}

	@Override
	public String colorTextoNegro() {
		return "黒色文字";
	}

	@Override
	public String colorTextoPrincipal() {
		return "メイン文字色";
	}

	@Override
	public String colorFondoResultados() {
		return "結果の背景色";
	}

	@Override
	public String colorEstado() {
		return "ステータス色";
	}

	@Override
	public String colorTextoDescripcion() {
		return "説明文の文字色";
	}

	@Override
	public String colorTextoEstado() {
		return "ステータス文字色";
	}

	@Override
	public String colorTextoExtra() {
		return "追加文字色";
	}

	@Override
	public String colorSeparador() {
		return "区切り線の色";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "ネイティブエラー <code>StubRoutines::SafeFetch32</code> が検出されました。"
				+ "この問題は macOS で JDK 17.0.9 を使用時に発生し、JDK 17.0.10 以降で修正されています。https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "JDK 17.0.9 (macOS) での SafeFetch32 ネイティブエラー";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "JDK を 17.0.10 以降（例: 17.0.15）に更新してください。";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "MultiMC、Prism Launcher、TLauncher などのランチャーを使用している場合、新しい JDK を使うよう設定してください。"
				+ "一部のランチャーはすでに JDK 17.0.15 を内蔵しています。";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "Spark mod もこのエラーの一因となる可能性があります。一時的に無効化を検討してください。https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "MCEF モッド（Chromium Embedded Framework）が無音フリーズを引き起こしています。</b>" + "<ul>"
				+ "<li>MCEF がログの最後で初期化されています。これは通常、ロード中にゲームがフリーズしたことを意味します。</li>"
				+ "<li>この mod は Linux や macOS、または特定の Java バージョンでクラッシュを引き起こすことが知られています。</li>"
				+ "<li>明確なエラーが常に表示されるわけではありませんが、ゲームはメインメニューに到達しません。</li>" + "</ul>"
				+ "<p>ゲーム内ブラウザ機能（Web マップや埋め込みページなど）が不要であれば、この mod を削除してください。</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "MCEF 初期化問題（埋め込みブラウザ mod）";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "'mods' フォルダから MCEF mod ファイル（ファイル名に 'mcef' を含む）を削除してください。";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "どうしても必要な場合は、お使いの OS と Minecraft バージョンと互換性のある MCEF バージョンを使用してください。";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "<b>OptiFine</b> と <b>Iris/Oculus</b> の間に競合が検出されました。</b>" + "<ul>"
				+ "<li>OptiFine は、Iris や Oculus と非互換な方法で Minecraft のレンダリングを変更します。</li>"
				+ "<li>エラー <code>MixinLevelRenderer failed injection check</code> は <code>mixins.iris.json</code> または <code>mixins.oculus.json</code> に由来します。</li>"
				+ "</ul>" + "<p>これらの mod は同時に使用できません。Iris または Oculus でシェーダーを使用するには、OptiFine を削除してください。</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "OptiFine と Iris/Oculus の競合";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "'mods' フォルダから OptiFine ファイルを削除してください。";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "最新のシェーダーを楽しむには、OptiFine を使用せず Iris または Oculus を利用してください。";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "<b>ModernFix</b> と <b>OptiFine</b> の間に競合が検出されました。</b>" + "<ul>"
				+ "<li>ModernFix は Forge の機能を破壊し起動を遅くするため、OptiFine と互換性がありません。</li>"
				+ "<li>ModernFix 自体が警告しています: <i>\"Use of ModernFix with OptiFine is not supported\"</i>。</li>" + "</ul>"
				+ "<p>ゲームを正常に動作させるには、どちらか一方の mod を削除する必要があります。</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "ModernFix と OptiFine の競合";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "'mods' フォルダから OptiFine または ModernFix を削除してください。両立できません。";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "最適化が必要な場合は、OptiFine のみを使用するか、ModernFix を FerriteCore や EntityCulling のような軽量 mod に置き換えてください。";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "エラー: 許可されていない文字を含む無効なレジストリキーです。</b>" + "<ul>"
				+ "<li><b>検出されたキー:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>Minecraft では、すべてのレジストリキー（タグ、レシピ、実績など）は<b>小文字</b>で、英数字、アンダースコア、ハイフン、スラッシュのみを使用する必要があります。</li>"
				+ "<li>このエラーは、通常、不適切にコーディングされた mod または欠陥のある datapack によって発生します。</li>" + "</ul>"
				+ "<p><b>重要ヒント:</b> サイドバーの <b>grepr</b> または <b>fgrepr</b> ツールを使い、<b>「JAR ファイル内を検索」</b>オプションを有効にして、この無効なキーを含む mod を特定してください。</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "大文字または無効な文字を含むレジストリキー";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "「JAR ファイル内を検索」オプション付きで 'grepr' または 'fgrepr' を使用し、問題の mod を特定してください。";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "mod を特定できない場合は、特にブロック、アイテム、ツールを追加する最近導入した mod を削除してみてください。";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "mod <b>" + escapeHtml(modNombre)
				+ "</b> の読み込み中にエラーが発生しました。</b>" + "<ul>" + "<li>この mod はコンポーネント（例: 設定メニュー）の初期化に失敗しました。</li>"
				+ "<li>これは通常、Minecraft や Fabric、または他の mod とのバージョン不整合によって発生します。</li>" + "</ul>" + "<p>エラーが続く場合は、mod <b>"
				+ escapeHtml(modNombre) + "</b> を削除または更新してください。</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "mod 初期化エラー (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "'mods' フォルダから mod '" + modNombre + "' を削除してください。";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "mod '" + modNombre + "' を現在の環境と互換性のあるバージョンに更新してください。";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "<b>En Garde!</b> モッドに関連するエラーが検出されました。</b>"
				+ "<ul>" + "<li>この mod は白兵戦のメカニクス（パリィ、ブロッキングなど）を追加します。</li>"
				+ "<li>このエラーは、通常、他の戦闘 mod（Epic Fight、DualRiders など）との非互換、または Minecraft に合わないバージョンの使用によって発生します。</li>"
				+ "</ul>" + "<p>高度な戦闘システムを使用しない場合は、競合を避けるために En Garde! を削除することを検討してください。</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "En Garde! モッドエラー";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "使用中の Minecraft バージョンおよびローダー（Fabric/Forge）と互換性のある En Garde! のバージョンを使用していることを確認してください。";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "他の戦闘 mod（Epic Fight、Caelus など）を使用している場合は、それらを無効化するか、En Garde! を削除して競合を回避してください。";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "<b>IdleTweaks</b> モッドによって引き起こされたエラーが検出されました。</b>" + "<ul>"
				+ "<li>IdleTweaks は、もはや存在しないネットワークチャネルの解放を試みました (<code>Tried to release unknown channel</code>)。</li>"
				+ "<li>このエラーは通常、モッドの古いバージョンや、設定が不適切なサーバーで発生します。</li>" + "</ul>"
				+ "<p>IdleTweaks はクオリティ・オブ・ライフ系 mod ですが、不安定さを引き起こすことがあります。更新または削除を検討してください。</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "IdleTweaks エラー（不明なネットワークチャネル）";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "IdleTweaks を、使用中の Minecraft と互換性のある最新バージョンに更新してください。";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "不要であれば、'mods' フォルダから IdleTweaks を削除してください。";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Minecraft へのログイン中に認証エラー (HTTP 401) が検出されました。</b>"
				+ "<p>このエラーは<b>クラッシュの直接的原因となることは稀</b>ですが、認証されていないアカウント（海賊版）を使用していることを示しています。</p>"
				+ "<p>公式サポートチャンネル（企業プロジェクト、VTuber、modpack 作成者など）は、海賊版を使用している場合、<b>サポートできません</b>。"
				+ "これはチャットルール、契約、Mojang/Microsoft との協定、または評判ポリシーによる制限のためです。</p>"
				+ "<p>このチェックは検出器の<b>企業向け設定で無効化</b>できます。"
				+ "警告：海賊版検出機能は<b>完璧ではありません</b>。開発環境、不安定なインターネット接続、改造ランチャー使用時に誤検出されることがあります。</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>それでもサポートに参加しようとする場合のミランダ警告:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "海賊版 Minecraft";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "海賊版検出を無効化";
	}

	@Override
	public String comprarMC() {
		return "Minecraft を購入";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "あなたは推奨リストに<b>含まれていない</b>ランチャー <code>"
				+ id + "</code> を使用しています。</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>動作する場合もありますが、非推奨ランチャーは次のような問題を引き起こすことがあります:</p>" + "<ul>" + "<li>mod やアプリの破損したインストール。</li>"
				+ "<li>ゲームが起動しない、または明確なエラーなしにフリーズ。</li>" + "<li>異常なフォルダ構成（診断が困難）。</li>"
				+ "<li>Java、メモリ、mod に関する予測不能な動作。</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "より良い体験のために、以下の推奨ランチャーのいずれかをご利用ください:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "非推奨ランチャー";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "推奨リストにあるランチャーに切り替えてください。";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "あなたは<b>使用が推奨されないランチャー</b>を使用しています: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>使用が推奨されないランチャーは、次のような問題を引き起こす可能性があります:</p>" + "<ul>" + "<li>アプリや mod のインストールが破損。</li>"
				+ "<li>ゲームが起動しない、またはサイレントクラッシュ。</li>" + "<li>異常なファイル構成（デバッグが困難）。</li>"
				+ "<li>mod や Java、メモリの管理方法が不明確。</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "以下のランチャーのいずれかの使用を強く推奨します:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "使用が推奨されないランチャー";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "サポートを受けるには、推奨ランチャーに切り替えてください。";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "この環境に推奨される mod が不足しています。</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "推奨 mod が不足";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "最適な体験のために、推奨 mod をインストールしてください。";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "インストールに推奨されない mod が検出されました。</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "推奨されない mod が検出されました";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "問題を回避するには、推奨されない mod を削除してください。";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "重要なファイルに不正な改変が検出されました。手動でファイルを編集したか、信頼できないランチャーを使用している可能性があります。</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "改変を検出";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "整合性を回復するには、元のファイルを再インストールしてください。";
	}

	@Override
	public String configuracionCorporativa() {
		return "企業向け設定";
	}

	@Override
	public String idiomaRespaldo() {
		return "フォールバック言語";
	}

	@Override
	public String buscardorHabilitado() {
		return "検索機能を有効化";
	}

	@Override
	public String nombreHerramienta() {
		return "ツール名";
	}

	@Override
	public String condenarPirateria() {
		return "海賊版を非難";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "推奨ランチャー";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "非推奨ランチャー";
	}

	@Override
	public String modsRecomendados() {
		return "推奨 mod";
	}

	@Override
	public String modsDesaconsejados() {
		return "非推奨 mod";
	}

	@Override
	public String antiTamper() {
		return "改ざん防止";
	}

	@Override
	public String proximamente() {
		return "近日公開";
	}

	@Override
	public String informacion() {
		return "情報";
	}

	@Override
	public String errorCargandoImagen() {
		return "画像の読み込みエラー";
	}

	@Override
	public String configuracionBasica() {
		return "基本設定";
	}

	@Override
	public String funcionalidades() {
		return "機能";
	}

	@Override
	public String derechosMiranda() {
		return "ミランダ警告（強く推奨）";
	}

	@Override
	public String gestionVerificaciones() {
		return "検証管理";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "名前";
	}

	@Override
	public String codigoVerificacion() {
		return "コード";
	}

	@Override
	public String documentacionVerificacion() {
		return "ドキュメント";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "有効な検証:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "無効な検証:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "非企業向け検証をすべて無効化";
	}

	@Override
	public String verCodigo() {
		return "コードを表示";
	}

	@Override
	public String verDocumentacion() {
		return "ドキュメントを表示";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "無効化する検証を選択してください。";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "有効化する検証を選択してください。";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "企業利用に推奨されない検証 %d 件が無効化されました。";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "無効化可能な非企業向け検証はありません。";
	}

	@Override
	public String operacionCompletada() {
		return "操作が完了しました";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "Amane Kanata、会いたいよ";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "企業検証の色";
	}

	@Override
	public String nombreLanzador() {
		return "ランチャー名";
	}

	@Override
	public String motivo() {
		return "理由";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "非推奨ランチャー";
	}

	@Override
	public String moverADesaconsejados() {
		return "非推奨に設定";
	}

	@Override
	public String moverARecomendados() {
		return "推奨に設定";
	}

	@Override
	public String guardarCambios() {
		return "変更を保存";
	}

	@Override
	public String cancelar() {
		return "キャンセル";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "移動するランチャーを選択してください。";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "変更が正常に保存されました！";
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
		return "理由";
	}

	@Override
	public String agregarLanzador() {
		return "ランチャーを追加";
	}

	@Override
	public String quitarLanzador() {
		return "ランチャーを削除";
	}

	@Override
	public String editarRazones() {
		return "理由を編集";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "削除するランチャーを選択してください。";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "編集するランチャーを選択してください。";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return idLanzador + " の理由を編集";
	}

	@Override
	public String agregarNuevoIdioma() {
		return "新しい言語を追加";
	}

	@Override
	public String aceptar() {
		return "OK";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "言語を選択";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "これらのランチャーは、CrashDetector が良好と推奨するものです。";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "正常な結果";
	}

	public String modsNoRecomendados() {
		return "非推奨 mod";
	}

	public String agregarMod() {
		return "mod を追加";
	}

	public String quitarMod() {
		return "mod を削除";
	}

	public String modId() {
		return "Mod ID / JBoss Modules 名称";
	}

	public String rutaMod() {
		return "mod のパス / ファイル";
	}

	public String errorDebeIndicarMod() {
		return "modid または mod のパスのいずれかを少なくとも指定してください。";
	}

	public String modsNoRecomendadosAviso() {
		return "ここに非推奨 mod を登録することで、インストールされている場合に CrashDetector が検出します。";
	}

	@Override
	public String anularNormal() {
		return "通常モードを無効化";
	}

	@Override
	public String anularNormalDescripcion() {
		return "クラッシュが発生していなくても、CrashDetector は警告を出すべきです。";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "CrashDetector が推奨する mod を登録します。不足している場合、CrashDetector が警告を出すことがあります。";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "海賊版警告を有効にする場合、予防措置としてサポートを求める人の権利をここで定義することをお勧めします。\n\n"

				+ "一般的な誤解とは異なり、多くの人気コミュニティやサポートチャンネルは、" + "海賊版警告を有効にしなくても助けを提供します。ただし、"
				+ "ユーザーがサポートチャンネルにアクセスした場合に備えて、これらの権利を文書化しておくと役立ちます。\n\n"

				+ "メキシコの『被拘束者の基本的権利ハンドブック』などの公式文書を参考にできます:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "また、アメリカ合衆国、ロシア連邦、中華人民共和国、イラン・イスラム共和国、" + "朝鮮民主主義人民共和国などで採用されている類似の法的原則も参考になります。\n\n"

				+ "含めることができる権利の例:\n" + "• サポートに不要な情報を提供しない権利（例: 使用中のランチャー、ユーザー名、UUID）。\n" + "• 自己負罪拒否権。\n"
				+ "• 問題解決に不要な質問に答えることを拒否する権利。\n" + "• チャット内でガイダンスを受ける権利。\n"
				+ "• CrashDetector に内蔵されたログ匿名化機能を使用する権利。\n\n"

				+ "このテキストは HTML コンテンツをサポートします。";
	}

	@Override
	public String editar() {
		return "編集";
	}

}
