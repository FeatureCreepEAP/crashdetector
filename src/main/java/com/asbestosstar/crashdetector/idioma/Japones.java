package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Japones implements Idioma {
	private final Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "ja";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "japones";
	}

	@Override
	public String nombre_del_idioma() {
		return "日本語";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_japon.png");
	}

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>無効なmodsフォルダー</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + Statics.nombre_cd.obtener()
				+ "のJARファイルが見つかりません</span>";
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
		return "<span style='color:#" + config.obtenerColorInfo() + "'>" + Statics.nombre_cd.obtener()
				+ "レポートはこちら <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace()
				+ "'>レポートを見る</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>これは" + Statics.nombre_cd.obtener()
				+ "のGUIインターフェースです。ゲームが正常に終了した場合は、この画面を無視してください。</span>";
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
				+ "'>「ランチャー起動をスキップ」（CurseForgeアプリ）を使用しています。これにより、検出が難しい問題が発生することがあります。これは、古いバージョンまたは新しいバージョンのCurseForgeアプリにある「ランチャー起動をスキップ」オプションによるものです。これを無効化し、CurseForge設定で「Mojang Launcher」を使用してください。Claws of Berkの英語動画（1分11秒から）を"
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>こちら</a>で確認できます。</b>";
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
		return "このダイアログでは、SecureLogger APIを使用してログを共有できます。"
				+ "APIは <a href=\"https://securelogger.net\">securelogger.net</a> にあります。レポートを共有するボタンを押すと、"
				+ "レポートは選択したエンドポイント（デフォルト: asbestosstar.egoism.jp）に送信されます（下部で変更可能）。"
				+ "選択したすべてのログをレポートと一緒に共有できます。アップロードを希望しない場合は、このダイアログを使用しないでください！"
				+ "公式エンドポイント（<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\">https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb</a>）では"
				+ "レポートを処理しません；許可されていないリンクを削除するのみです。コードはこちら: <a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">ソースコード</a>。"
				+ "これはクラッシュに関する情報とログへのリンクを表示するためのみに使用されます。ただし、同じ方法を使用しないカスタムエンドポイントを使用する場合もあります。" + "現在使用しているレポートサイト: "
				+ Config.obtenerInstancia().obtenerSitoDeInformes() + "、ログサイト: "
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + "。"
				+ "個々のログ名の横にある共有ボタンを押すことで、レポートなしで個々のログを共有することもできます；" + "ログは選択されたログサイトに送信されます。"
				+ Statics.nombre_cd.obtener() + " にはデフォルトのログ匿名化機能があり、ユーザー名、UUID、"
				+ "アクセストークン、セッションID、IPアドレス、その他のデータを削除しようとします。ただし、完全ではありません。それでも、modpackの作者はこれを無効にすることができます。"
				+ "この画面の下部にあるチェックボックスで有効または無効にできます。あなたは自分のデータの管理者です；どこにデータをアップロードするかはあなたが決めます。"
				+ "ログサイトはサードパーティが所有しており、プライバシー上の理由から所有者が非公開の場合が多いです。データの管理と関連リスクについては、すべて自己責任となります。"
				+ Statics.nombre_cd.obtener() + " の共有ダイアログは、これを管理するためのインターフェースに過ぎません。" + "GDPRおよびARCOについて認識しておくことが重要です。"
				+ "ヨーロッパにお住まいの場合は、HetznerによってドイツにホストされているEU版の <a href=\"https://securelogger.top\">securelogger.top</a> をご利用いただけます。"
				+ "法的情報の詳細については、以下のリンクを参照してください: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>、"
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">GDPR</a>、"
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">個人情報保護に関する基本方针（日本）</a>。";
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
				+ "このエラーは、MinecraftForgeインストーラーのJARファイル、デフォルトエンドポイントを使用した" + Statics.nombre_cd.obtener()
				+ "レポートの共有機能、インターネットを必要とする一部のMOD、およびいくつかのログサイトに影響を与えます。" + "これをレポートの共有中に遭遇した場合、スクリーンショットを添付し、"
				+ "古いJava 8バージョンと互換性のあるログサイトを選択してください。";
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
				+ "現時点ではこの MOD だけがリストに載っており、彼らは実際にはデフォルトのログサイトのみを対象としています。このサイトはユーザーが変更可能で、組み込みのログ共有機能を明示的に選択した場合にのみ動作します。CrashAssistant はどのログサイトが設定されているかチェックせず、また変更方法（共有ダイアログの下部にあるドロップダウン）を説明しません。設定されているサイトに関係なく、CrashAssistant はゲームの起動をブロックします。メッセージの中で彼らは独自の調査をするように言っていますが、その通りにしてください。"
				+ Statics.nombre_cd.obtener() + " と Crash Assistant のコードを調べ、何をしているのか理解してください。権威への信頼に頼らないでください。</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "NightConfig/Forge 設定の致命的エラー: "
				+ "設定ファイルが破損または不完全です。"
				+ "これは、古いバージョンやカスタム版の NightConfig において、'config' フォルダ内の空の設定ファイル（多くの場合 0 バイト）によって引き起こされることがあります。"
				+ "ほとんどのバージョンでは Night Config Fixes で問題を解決できますが、非互換またはカスタム版の NightConfig を使用している場合は、手動で設定ファイルを削除する必要があります。"
				+ "この問題は、NightConfig を内蔵する古い MC Forge バージョンや、NightConfig を同梱する古い FabricMC モッドでより一般的ですが、一部のカスタム NightConfig ビルドでも発生する可能性があります。"
				+ "解決策の詳細は <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a> でご確認ください。</b>";
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
		return "失敗時にのみ" + Statics.nombre_cd.obtener() + "を開く";
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

	/**
	 * 問題のあるエンティティまたはブロックエンティティのエラーメッセージを返し、 プラットフォームに応じた復旧手順を詳細に説明します。
	 */
	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		String color = config.obtenerColorError();

		// メインメッセージ：説明テキストのみエラー色を適用
		String mensajeBase = "<span style='color:#" + color + "'>エンティティまたはブロックエンティティ '</span>" + nombre
				+ "<span style='color:#" + color + "'>' タイプ '</span>" + tipo + "<span style='color:#" + color
				+ "'>' 位置 </span>" + coords + "<span style='color:#" + color
				+ "'> で ticking エラーを引き起こしています。</span><br><br>";

		// 修復手順
		String instrucciones = "<span style='color:#" + color + "'>" + "復旧手順:<br>"
				+ "1. **MCForge**: '[nombre_del_mundo]/serverconfig/forge-server.toml' に移動します。<br>"
				+ "2. **NeoForge**: 'config/neoforge-server.toml' に移動します。<br>"
				+ "   *（注：ローカルゲーム/Singleplayer では、ワールドは 'saves' フォルダ内にあります）*。<br>"
				+ "3. **removeErroringBlockEntities** と **removeErroringEntities** を **true** に変更します。<br><br>"
				+ "その他のオプション:<br>" + "- **MCEdit**: 指定された座標でエンティティを手動で削除するために使用します。<br>"
				+ "- **Neruina (Mod)**: クラッシュを防ぐ可能性がありますが、常に機能するとは限らず、インストールするとデバッグが困難になる場合があります。" + "</span>";

		return mensajeBase + instrucciones;
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
				+ "1) ランチャーに JVM 引数 <code>-Djava.net.preferIPv4Stack=true</code> を追加するか、" + "2) "
				+ Statics.nombre_cd.obtener() + " の 'QuickFix' ボタンをクリックして自動的にこの設定を有効にするパッチを適用してください。" + "</b>";
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
		return "汎用：使用しているランチャーの種類を選択してください。ランチャーログ(launcher_log.txt, stdoutなど)は latest.log に現れない重大なエラー情報を含みます。"
				+ Statics.nombre_cd.obtener()
				+ "があなたのランチャーログを読み込めない可能性があります — ログファイルが生成されていない場合、手動でログを貼り付けなくてはいけません。<br>"
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
		return "ProxySysOutSysErrを有効にしますか？\n\n" + "このオプションにより、ランチャーがログを提供しない場合でも" + Statics.nombre_cd.obtener()
				+ "がSystem.outとSystem.errにアクセスできるようになります。\n\n" + "手動でログを貼り付けられない場合にのみ有効にしてください。\n\n"
				+ "警告：一部のMODやランチャーと干渉する可能性があります。\n\n" + "変更を適用するには、ゲーム／アプリの再起動が必要です。";
	}

	@Override
	public String confirmacionTitulo() {
		return "確認";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErrが正常に有効化されました。\n\n" + "変更を適用するには、" + Statics.nombre_cd.obtener() + "を再起動する必要があります。";
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
				+ "一般的な ModLauncher サービスコンポーネントには、" + Statics.nombre_cd.obtener() + "、"
				+ Config.obtenerInstancia().obtenerNombreCD()
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
		return "OptiFine を専用サーバーから削除してください。OptiFine はクライアント側にのみインストールされるべきです。この問題は、間違った Minecraft バージョン向けの OptiFine をインストールしていたり、他の Mod と OptiFine が競合している場合にもよく発生します。";
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
				.append(new java.io.File(com.asbestosstar.crashdetector.Statics.carpeta_mundial_como_archivo, "cfr/")
						.getAbsolutePath())
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
		return "これらのランチャーは、" + Statics.nombre_cd.obtener() + " が良好と推奨するものです。";
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
		return "ここに非推奨 mod を登録することで、インストールされている場合に " + Statics.nombre_cd.obtener() + " が検出します。";
	}

	@Override
	public String anularNormal() {
		return "通常モードを無効化";
	}

	@Override
	public String anularNormalDescripcion() {
		return "クラッシュが発生していなくても、" + Statics.nombre_cd.obtener() + " は警告を出すべきです。";
	}

	@Override
	public String modsRecomendadosAviso() {
		return Statics.nombre_cd.obtener() + " が推奨する mod を登録します。不足している場合、" + Statics.nombre_cd.obtener()
				+ " が警告を出すことがあります。";
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
				+ "• 問題解決に不要な質問に答えることを拒否する権利。\n" + "• チャット内でガイダンスを受ける権利。\n" + "• " + Statics.nombre_cd.obtener()
				+ " に内蔵されたログ匿名化機能を使用する権利。\n\n"

				+ "このテキストは HTML コンテンツをサポートします。";
	}

	@Override
	public String editar() {
		return "編集";
	}

	@Override
	public String advertenciaHashLento() {
		return "警告: 大きなファイルを多数追加すると、検証に数分かかることがあります。" + Statics.nombre_cd.obtener()
				+ " は続行前に各ファイルのハッシュを計算する必要があります。厳密に必要なファイルのみを保護することをお勧めします。";
	}

	@Override
	public String agregarArchivo() {
		return "ファイルを追加";
	}

	@Override
	public String agregarCarpeta() {
		return "フォルダを追加";
	}

	@Override
	public String quitar() {
		return "削除";
	}

	@Override
	public String rutaArchivo() {
		return "ファイルパス";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "選択されたパスは現在のゲームディレクトリの外にあります。現在のディレクトリまたはそのサブディレクトリ内のファイルおよびフォルダのみが許可されています。";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>" + "Sylent Bell の意見やコメントは必ずしも私たちの見解と一致するわけではありません；"
				+ "ただ、ここに置いたら面白いと思ったのです。" + Statics.nombre_cd.obtener() + " は世俗的です。" + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GML（Groovy ModLoader）mod はこれらの変更を必要とし、この問題の最も一般的な原因です。</b>";
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
				+ "<i> Flywheel</i> の使用が検出されました。</b>"
				+ "<p><b> Flywheel は非推奨（deprecated）</b>であり、最新バージョンでは使用すべきではありません。</p>"
				+ "<p>現在の <b>Create</b> バージョンは<b>Flywheel を既に内蔵</b>しているため、別途インストールすると " + "互換性の衝突や読み込みエラーが発生します。</p>"
				+ "<p> Flywheel に明示的に依存する一部の mod は " + "<b>動作しない</b>か<b>不安定に動作</b>する可能性があります。"
				+ "特定の高度なケースでは、<b><code>mods.toml</code> ファイルを手動で編集</b>してバージョン範囲を調整すれば "
				+ "動作するかもしれませんが、これは<b>推奨されません</b>。</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>Flywheel を参照している検出済み mod:</b></p>" + "<ul>" + listaMods.toString() + "</ul>")
				+ "<p>推奨される解決策は、<b> Flywheel を削除</b>し、Create に同梱されているバージョンのみを使用することです。</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "独立型 Flywheel";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "<i>Floral Enchantments</i> モッドに関連するエラーが検出されました。</b>"
				+ "<p>クラッシュは、ゲームデータを処理中にモッド内で発生した内部障害によって引き起こされ、" + "実行中に <b>NullPointerException</b> を発生させます。</p>"
				+ "<p>この問題は通常、モッドを更新または削除することで解決します。</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "Floral Enchantments エラー";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "MixinExtras の NeoForge 版と通常版の両方がインストールされています。MinecraftForge を使用している場合、<a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>このリンク</a>から修正 mod をインストールできます。</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Iris のシェーダー使用時に地形の影にエラーが検出されました。</b>" + "<p>この問題は地形のレンダリング中に発生します。</p>"
				+ "<p><b>シェーダーを無効にしてゲームを試す</b>か、グラフィック品質を下げることを推奨します。" + "特に<b>Ultra</b>設定時です。</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "地形の影 (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "サーバーのティックが異常に長くなっています。</b>"
				+ "<p>これは、ゲームが1ティック内で長時間フリーズしたことを示しています。</p>"
				+ "<p>原因を特定するため、ログに生成された<b>スレッドダンプ(thread dump)を確認</b>することをお勧めします。</p>"
				+ "<p><b>スタックトレース分析(Stack Trace Analysis)</b>により、フリーズの原因を特定できます。</p>"
				+ "<p>また、<b>ログで表示</b>ボタンは、問題の原因となりうるmodを赤色で強調表示し、"
				+ "<code>$modid$</code>で囲まれたエントリも表示します（これらは通常、問題の発生源を示します）。リアルタイムでの解析には、VisualVM の CPU サンプラーの使用を推奨します。すべてのmodが正常に動作していても、数が多すぎるとこの問題が発生する可能性があるため、サーバーやPCの性能が使用中のmodを十分に処理できるか確認してください。</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "サーバーティックの長時間停止";
	}

	@Override
	public String tituloLFPDPPP() {
		return "個人が保有する個人情報の保護に関する連邦法";
	}

	@Override
	public String aceptarPermanentemente() {
		return "永続的に同意";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "韓国語翻訳には、法律を遵守するために避けるべき南部のスラング用語が含まれています。" + "外国語、特に南側由来の言葉の使用は、「平壌文化語保護法」により厳しく禁止されています。";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "詳細については、法令の公式文書をご参照ください："
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>平壌文化語保護法</a>";
	}

	public String leerLeyCompleta() {
		return "全文を読む";
	}

	public String errorAbriendoEnlace() {
		return "リンクを開く際にエラーが発生しました";
	}

	public String actaProteccionIdiomaCultural() {
		return "平壌文化語保護法";
	}

	@Override
	public String canarioTitulo() {
		return "司法命令カナリア";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — 監視モニター";
	}

	@Override
	public String revisar() {
		return "確認";
	}

	@Override
	public String cerrar() {
		return "閉じる";
	}

	@Override
	public String canarioTodoSeguro() {
		return "すべてのサービスが安全な状態を報告しています。";
	}

	@Override
	public String canarioComprometido(int c) {
		return "警告: " + c + " 個のサービスが安全でない状態を報告しています。";
	}

	@Override
	public String colorAlerta() {
		return "警告色";
	}

	public String opcionesMunidiales() {
		return "ムニダルオプション";
	}

	public String consentimientoLFPDPPP() {
		return "LFPDPPP同意";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "ReLauncher向けにHandoffでアクセストークンのハンドオフを有効化（非推奨）";
	}

	public String consolaDesarrollo() {
		return "開発コンソール";
	}

	public String mundial() {
		return "グローバル";
	}

	public String ningun() {
		return "なし";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "開発者コンソール";
	}

	public String bajar() {
		return "ダウンロード";
	}

	public String logsSoporte() {
		return "サポート用ログ";
	}

	public String detenerProceso() {
		return "プロセスを停止";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "選択範囲をコピー";
	}

	public String seleccionarTodo() {
		return "すべて選択";
	}

	public String copiarTodo() {
		return "すべてコピー";
	}

	public String guardarTodoComoArchivo() {
		return "すべてをファイルとして保存";
	}

	public String obtenerEnlaceSoporte() {
		return "サポートリンクを取得";
	}

	public String borrarTodo() {
		return "すべてクリア";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "コンソールの背景色";
	}

	public String colorTextoConsola() {
		return "コンソールの文字色";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "同意が確認されました。\nログ共有機能はここに実装されます。";
	}

	@Override
	public String usarSakuraOriginal() {
		return "オリジナルの Sakura Riddle 画像を使用";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "「ワラントカナリア（warrant canary）」は透明性を確保する仕組みです。\n\n" + "このメッセージが存在し、サービスが安全と表示されている限り、"
				+ "プロジェクトは秘密の司法命令や検閲要求、" + "合法的な監視要請を受けていないことを意味します。\n\n" + "いずれかのカナリアが消えたり、非安全とマークされた場合、"
				+ "それは法的状況に変化があったことを示します。\n\n" + "このパネルはシステムに登録されたすべてのカナリアを確認し、" + "現在の状態を表示します。\n\n"
				+ "「確認」を押して状態を更新してください。";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "すべてのオプションをデフォルト値にリセットしますか？";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "オプションなし";
	}

	@Override
	public String seleccionaColor() {
		return "色を選択";
	}

	@Override
	public String botonMostrarGUI() {
		return "GUIを表示";
	}

	@Override
	public String botonGuardarTodo() {
		return "すべて保存";
	}

	@Override
	public String botonRestablecerTodo() {
		return "すべてリセット";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms が読み込まれていません";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "LuckPerms API へのアクセス中にエラーが検出されました。</b>"
				+ "<p>このメッセージは、別のプラグインが LuckPerms を使用しようとした際に、<b>LuckPerms がまだ読み込まれていなかった</b>ことを示しています。</p>"
				+ "<p><b>考えられる原因:</b></p>" + "<ul>"
				+ "<li><b>LuckPerms プラグインがインストールされていない</b>か、<b>起動に失敗した</b>可能性があります。</li>"
				+ "<li>別のプラグインが LuckPerms に <b>早すぎるタイミング</b> または <b>不適切な方法</b> でアクセスしている可能性があります。</li>" + "</ul>"
				+ "<p>リンクから<b>コンソールを確認し</b>、" + "LuckPerms を呼び出しているプラグインを特定し、互換性を検証することをお勧めします。</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Iris シェーダーパックが読み込まれていません";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "不明" : shaderpack;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Iris/Oculus でシェーダーパックを読み込む際にエラーが検出されました。</b>" + "<p><b>影響を受けたシェーダーパック:</b> " + nombre + "</p>"
				+ "<p>Minecraft はシェーダーパックのファイルを開けませんでした（FileSystemNotFoundException）。</p>" + "<p><b>考えられる解決策:</b></p>"
				+ "<ul>" + "<li>シェーダーパックが <b>shaderpacks</b> フォルダーに正しくインストールされているか確認してください。</li>"
				+ "<li>ファイルが破損している可能性があるため、シェーダーパックを再ダウンロードしてください。</li>"
				+ "<li>問題が続く場合は、Iris の設定をリセットするため <b>config/iris.properties</b> ファイルを削除してください。</li>" + "</ul>"
				+ "<p>変更を適用した後、ゲームを再起動してください。</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "設定ファイルを書き込めませんでした";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "不明" : ruta;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "設定ファイルの保存中にエラーが発生しました。</b>"
				+ "<p><b>影響を受けたファイル:</b> " + archivo + "</p>"
				+ "<p>Minecraft はアトミック書き込み（REPLACE_ATOMIC）でファイルを書き込むことができませんでした。</p>"
				+ "<p><b>これは通常、以下の原因で発生します:</b></p>" + "<ul>" + "<li>フォルダーやファイルの権限が正しくない。</li>"
				+ "<li>ファイルが読み取り専用としてマークされている。</li>" + "<li>他のプログラム（ウイルス対策ソフト、バックアップツール、エディタなど）がファイルをロックしている。</li>"
				+ "</ul>" + "<p><b>推奨対応:</b></p>" + "<ul>" + "<li>フォルダーに書き込み権限があるか確認してください。</li>"
				+ "<li>ファイルの読み取り専用属性を解除してください。</li>" + "<li>このファイルを使用している可能性のあるプログラムを終了してください。</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "バックアップ作成時にアクセスが拒否されました";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "不明" : origen;
		String dst = backup == null || backup.isEmpty() ? "不明" : backup;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "設定ファイルのバックアップ作成中に権限エラーが発生しました。</b>" + "<p><b>元のファイル:</b> " + src + "</p>" + "<p><b>バックアップファイル:</b> "
				+ dst + "</p>" + "<p>ファイル保存中にオペレーティングシステムがアクセスをブロックしました。</p>" + "<p><b>これは通常、以下の原因で発生します:</b></p>"
				+ "<ul>" + "<li>フォルダーの権限が不十分です。</li>" + "<li>ファイルが読み取り専用としてマークされています。</li>"
				+ "<li>他のプログラム（ウイルス対策ソフト、同期ツール、エディタ）がファイルを使用中です。</li>" + "</ul>" + "<p><b>推奨対応:</b></p>" + "<ul>"
				+ "<li><b>config</b> フォルダーの権限を確認してください。</li>" + "<li>このファイルにアクセスしている可能性のあるプログラムを終了してください。</li>"
				+ "<li>ランチャーや Minecraft を管理者として実行してみてください。</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		return "コンソールを有効化";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>デバッグツール</b><br><br>" + "ここでは、" + Statics.nombre_cd.obtener()
				+ " やゲームのデバッグに役立つ高度な機能を有効化できます。<br><br>" + "分析中に詳細な情報、トレース、診断データを得るため、開発用コンソールの有効化を推奨します。<br><br>"
				+ "オンラインモードでマルチプレイヤーサーバーをテストする必要がある場合、プライバシー設定から " + Statics.nombre_cd.obtener()
				+ " プロセスへアクセストークン（token de acceso）の転送を許可する必要があるかもしれません。" + "ただし、これは通常、他のケースでは<b>推奨されません</b>。<br><br>"
				+ "詳細な手順: <a href='https://example.com'>リンク！</a>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "非互換性：Simple Clouds とシェーダー";
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Simple Clouds とシェーダーの間に非互換性が検出されました。</b>"
				+ "<p>Distant Horizons がインストールされている場合、Simple Clouds は影モッド（Iris/Oculus）と互換性がありません。</p>"
				+ "<p><b>推奨される選択肢：</b></p>" + "<ul>" + "<li>シェーダーを使用したい場合は、<b>Simple Clouds</b> を削除してください。</li>"
				+ "<li>または、Simple Clouds を維持したい場合は、<b>Iris または Oculus</b> をアンインストールしてください。</li>" + "</ul>"
				+ "<p>この制限は Simple Clouds モッド自体に由来しており、コードを修正せずに解決することはできません。</p>";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "サイドバーのボタン色";
	}

	@Override
	public String profilerTitulo() {
		return "プロファイラー";
	}

	@Override
	public String profilerDescripcion() {
		return "インストルメンテーションとサンプリングに基づくパフォーマンス分析ツールです。";
	}

	@Override
	public String profilerIniciar() {
		return "開始";
	}

	@Override
	public String profilerDetener() {
		return "停止";
	}

	@Override
	public String profilerLimpiar() {
		return "クリア";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "プロファイラーを開始しました。";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "プロファイラーを停止しました。";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "スレッドからサンプルを受信しました: " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "ボトルネックやデッドロックを検出するための、スタックの定期的なサンプリング。";
	}

	@Override
	public String entrarAlJuego() {
		return "ゲームに入る";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "無効なパス：使用不可の文字が含まれています";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "システムパスエラーを検出しました。</b>"
				+ "<p>フォルダー名に不正な文字が含まれているため、Minecraft を起動できませんでした。</p>" + "<p>パス内に無効な文字（例：「:」やその他の特殊記号）が検出されました。</p>"
				+ "<p><b>推奨される解決策：</b></p>" + "<ul>" + "<li>インスタンスまたはプロファイルのフォルダー名を変更してください。</li>"
				+ "<li>基本 ASCII 文字（A–Z、a–z、0–9）のみを使用してください。</li>"
				+ "<li>アクセント記号、特殊記号、問題のあるスペース、絵文字（emoji）は使用しないでください。</li>" + "</ul>"
				+ "<p>有効な例: <b>MiInstancia1</b></p>" + "<p>無効な例: <b>Instancia🔥</b> または <b>Instancia:Mod</b></p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "クラッシュ: Twilight Forest + インテルドライバー";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Intel GPU で Twilight Forest のシェーダーに障害が検出されました。</b>"
				+ "<p>このエラーは、Twilight Forest モッドのシェーダーを読み込む際の Intel グラフィックスドライバーに関連しています。</p>"
				+ "<p>クラッシュはドライバー内部（igxelpicd64）で発生しており、モッドや Minecraft 自体の直接的な問題ではありません。</p>"
				+ "<p><b>推奨される解決策：</b></p>" + "<ul>" + "<li>Intel ドライバーを最新の利用可能バージョンに更新してください。</li>"
				+ "<li>特に、この問題が報告されていないバージョン 31.0.101.8331 または 31.0.101.8247 WHQL を試してください。</li>" + "</ul>"
				+ "<p>公式の問題追跡ページ：</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>注:</b> 一部の古い Intel GPU は、この問題を修正する更新を受け取らない可能性があります。</p>";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "Forge: 言語プロバイダーの読み込みに失敗しました";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "不明なプロバイダー" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Forge が言語プロバイダーを読み込めませんでした。</b>" + "<p>IModLanguageProvider の初期化中にエラーが発生しました。</p>"
				+ "<p><b>失敗したプロバイダー:</b> " + providerTexto + "</p>" + "<p>この問題は通常、以下の状況で発生します:</p>" + "<ul>"
				+ "<li>必要な依存関係（例: Kotlin for Forge）が不足している。</li>" + "<li>Mod のバージョンがお使いの Forge バージョンと互換性がない。</li>"
				+ "<li>Mod ファイルが破損している。</li>" + "</ul>" + "<p><b>推奨される解決策:</b></p>" + "<ul>"
				+ "<li>該当する Mod を再インストールしてください。</li>" + "<li>すべての依存関係がインストールされているか確認してください。</li>"
				+ "<li>現在の Forge と互換性のあるバージョンを使用していることを確認してください。</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "クラッシュ: Lets Do Compat（RecipeManager インターセプト）";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Lets Do Compat (interceptApply) でクラッシュを検出しました。</b>" + "<p>このエラーは、Lets Do Compat が実行したメソッド "
				+ "<b>RecipeManager.interceptApply</b> の変換中に発生しています。</p>" + "<p>これは通常、以下を示しています:</p>" + "<ul>"
				+ "<li>Lets Do Compat とレシピを変更する他の Mod 間の非互換性。</li>"
				+ "<li>Minecraft のバージョンに合わない Mod バージョンを使用していること。</li>" + "<li>トランスフォーマー（mixin/coremod）間の競合。</li>"
				+ "</ul>" + "<p><b>推奨される解決策:</b></p>" + "<ul>" + "<li>競合を確認するため、Lets Do Compat を一時的に削除してみてください。</li>"
				+ "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI: アイテムグループクラッシュ（非互換プラグイン）";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "不明なプラグイン" : String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "JEI がアイテムグループの構築中にクラッシュを検出しました。</b>" + "<p>JEI が材料リストを生成している際に、1つ以上のプラグインがエラーを引き起こしました。</p>"
				+ "<p><b>影響を受けたグループ／プラグイン:</b> " + listaPlugins + "</p>" + "<p>この問題は以下の状況でよく発生します:</p>" + "<ul>"
				+ "<li>JEI プラグインが不適切に実装されている、または古くなっている。</li>" + "<li>現在の JEI バージョンと互換性がない。</li>"
				+ "<li>Fabric API を使用しており、Mod が Item Group を誤って登録している。</li>" + "</ul>" + "<p><b>推奨される解決策:</b></p>"
				+ "<ul>" + "<li>JEI および該当 Mod を更新してください。</li>" + "<li>競合を確認するため、影響を受けたプラグインを一時的に削除してください。</li>"
				+ "<li>関連 Mod の開発者にこのエラーを報告してください。</li>" + "</ul>" + "<p>この問題が修正されるまで、これらのグループのアイテムは材料リストに表示されません。</p>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "無効なModバージョン（SemVer）";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "不明" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "Modの場所を特定できませんでした" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "無効なModバージョンが検出されました。</b>"
				+ "<p>バージョン <b>" + v + "</b> は有効なSemVer形式に準拠していません。</p>" + "<p>このエラーは、空のプレリリース（末尾が「+」）を示しています。</p>"
				+ "<p><b>問題のあるMod：</b><br>" + u + "</p>" + "<p><b>推奨される解決策：</b></p>" + "<ul>"
				+ "<li>Modファイルを編集し、バージョンを修正してください。</li>" + "<li>後続のメタデータがない場合は、末尾の「+」を削除してください。</li>"
				+ "<li>Modを修正済みのバージョンに更新してください。</li>" + "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS：モジュール間の不正アクセス";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "モジュール間の不正アクセス（JPMS）を検出しました。</b>" + "<p>Java モジュールシステム（JPMS）がクラス間のアクセスをブロックしました。</p>"
				+ "<p><b>アクセスを試みたクラス：</b><br>" + claseOrigen + "（モジュール：" + moduloOrigen + "）</p>"
				+ "<p><b>ブロックされたクラス：</b><br>" + claseDestino + "（モジュール：" + moduloDestino + "）</p>"
				+ "<p>このエラーは、Mod が module-info.java で " + "exports または opens を正しく宣言していない場合に発生します。</p>"
				+ "<p><b>考えられる原因：</b></p>" + "<ul>" + "<li>モジュールが必要なパッケージをエクスポートしていない。</li>"
				+ "<li>リフレクション用の <b>opens</b> ディレクティブが不足している。</li>" + "<li>Mod が JPMS 用に適切に設定されていない。</li>" + "</ul>"
				+ "<p>この問題は Mod 開発者が修正する必要があります。</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin：クラスが mixin パッケージ内に誤って配置されています";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "クラスが Mixin パッケージ内に誤って配置されました。</b>" + "<p>通常のクラスが、mixin として宣言されたパッケージ内に置かれています。</p>"
				+ "<p><b>競合しているクラス：</b><br>" + clase + "</p>" + "<p><b>宣言された mixin パッケージ：</b><br>" + paquete + "</p>"
				+ "<p><b>関連する mixins ファイル：</b><br>" + archivoMixin + "</p>"
				+ "<p>通常のクラスは mixins.json で定義されたパッケージ内に置いてはいけません。</p>"
				+ "<p>そのパッケージには、mixin としてアノテーションされたクラスのみを置くべきです。</p>"
				+ "<p><b>開発者向け解決策：</b>通常のクラスを mixin パッケージの外に移動するか、" + "mixins.json の設定を修正してください。</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Matrox GPUドライバーに問題を検出しました。</b>" + "<p>ログによると、クラッシュはMatroxドライバーのライブラリ内で発生しています。</p>"
				+ "<p>Matrox GPU（特にサーバーで使われるG200/G400モデル）は、"
				+ "現代的な3Dレンダリング向けに設計されておらず、Minecraftが要求するOpenGLバージョンをサポートしていない可能性があります。</p>" + "<p><b>推奨される解決策：</b></p>"
				+ "<ul>" + "<li>Matroxドライバーを最新の利用可能バージョンに更新してください。</li>"
				+ "<li>汎用的なシステムドライバーではなく、公式ドライバーをインストールしてください。</li>"
				+ "<li>ハードウェアが古い場合は、OpenGL 3.2以上に対応したGPUを使用してください。</li>" + "</ul>"
				+ "<p>サーバーでは、これらのGPUは通常、基本的なビデオ出力専用であり、" + "Minecraftのような3Dアプリケーションには適していません。</p>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod: 非対応GPU";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod が互換性のある GPU を検出できませんでした。</b>"
				+ "<p>Mod <b>VulkanMod</b> は Vulkan を使用して起動しようとしましたが、適切に Vulkan をサポートする GPU が見つかりませんでした。</p>"
				+ "<p>これは通常、以下の状況で発生します:</p>" + "<ul>" + "<li>GPU が Vulkan をサポートしていない。</li>"
				+ "<li>GPU ドライバーが古いか、インストールされていない。</li>" + "<li>誤ったグラフィックアダプターを使用している（例：専用 GPU ではなく統合 GPU）。</li>"
				+ "</ul>" + "<p><b>推奨される解決策:</b></p>" + "<ul>" + "<li>GPU ドライバーを最新バージョンに更新してください。</li>"
				+ "<li>お使いの GPU が Vulkan をサポートしているか確認してください。</li>"
				+ "<li>GPU が2台ある場合、Minecraft で専用 GPU を強制的に使用してください。</li>"
				+ "<li>GPU が Vulkan をサポートしていない場合は、VulkanMod をアンインストールしてください。</li>" + "</ul>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "アウトラインに無効な RenderType が使用されました";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		String base = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod が非互換の RenderType にアウトラインを適用しようとしました。</b>" + "<p>エラー内容:</p>"
				+ "<code>Can't render an outline for this rendertype!</code>";

		if (enchantDetectado) {
			base += "<p><b>レポート内で enchant-outline / better-enchants Mod が検出されました。</b></p>"
					+ "<p>この Mod は、最近の Minecraft バージョンでこの問題を引き起こすことが知られています。</p>"
					+ "<p><b>推奨される解決策:</b> enchant-outline を削除または更新してください。</p>";
		} else {
			base += "<p>この問題は通常、レンダリングを変更する Mod "
					+ "（Entity Model Features、Entity Texture Features、Visuality、または Sodium との競合）に関連しています。</p>"
					+ "<p><b>推奨される解決策:</b> レンダリング Mod を1つずつ更新または無効化して確認してください。</p>";
		}

		return base;
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – DimensionalInventory が null";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG が null の DimensionalInventory を保存しようとしました。</b>" + "<p>ゲームが以下のエラーをスローしました:</p>"
				+ "<code>Cannot invoke DimensionalInventory.saveInventory(...) because \"d\" is null</code>"
				+ "<p>これは Vethean インベントリシステムに関連する DivineRPG の既知のバグです。</p>" + "<p><b>推奨される解決策:</b></p>" + "<ul>"
				+ "<li>DivineRPG の設定ファイルを開いてください。</li>" + "<li><code>saferVetheanInventory=true</code> に設定してください。</li>"
				+ "<li>保存してゲームを再起動してください。</li>" + "</ul>" + "<p>より新しいバージョンが利用可能であれば、DivineRPG の更新も推奨します。</p>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "レンダーパスの競合";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "レンダリングシステムで競合が検出されました。</b>"
				+ "<p>ゲームが以下のエラーをスローしました:</p>"
				+ "<code>Close the existing render pass before performing additional commands</code>"
				+ "<p>このエラーは通常、Iris や OptiFine、VulkanMod など、グラフィックスパイプラインを変更する Mod 間の競合に関連しています。</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "Feather Client 内部クラッシュ";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Feather Client の内部クラッシュが検出されました。</b>" + "<p>ゲームが以下のエラーをスローしました:</p>"
				+ "<code>NoClassDefFoundError: feather/lib/sentry/Sentry</code>"
				+ "<p>このエラーは Feather Client によって引き起こされています。</p>" + "<p>Feather は以下の理由から推奨されません:</p>" + "<ul>"
				+ "<li>人気 Mod の独自改変版を使用している。</li>" + "<li>標準的な Fabric との互換性を損なう。</li>"
				+ "<li>内部で改変されたプリインストール型 Modpack として動作する。</li>" + "<li>Sodium や他のパフォーマンス Mod と競合することが多い。</li>" + "</ul>"
				+ "<p>Feather の代わりに、標準の Fabric インストールを使用することを推奨します。</p>";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "Iris + Flywheel の競合（Create 6）";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Create 6 で Iris と Flywheel の間の競合が検出されました。</b>" + "<p>ゲームが以下のエラーをスローしました:</p>"
				+ "<code>NoSuchFieldError: TESSELATION_SHADERS</code>" + "<p>内部参照 <code>$irisflw$</code> が検出され、"
				+ "これは Iris と Flywheel の競合を示しています。</p>"
				+ "<p>Create 6 向けの Iris Flywheel 2.0 は、公式に NeoForge のみに対応しています。</p>"
				+ "<p>Forge や Fabric を使用している場合、この組み合わせがこのエラーを引き起こす可能性があります。</p>";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "GeckoLib モデルが見つかりません";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod が GeckoLib モデルを見つけられませんでした。</b>" + "<p>影響を受けたモデル:</p>" + "<code>" + modelo + "</code>"
				+ "<p>このエラーは、<code>.geo.json</code> ファイルが存在しないか、" + "Mod 内で誤って設定されている場合に発生します。</p>" + "<p>考えられる原因:</p>"
				+ "<ul>" + "<li>モデルが削除されたが、まだ参照されている。</li>" + "<li>ファイルパスのエラー。</li>" + "<li>JAR 内にファイルが含まれていない。</li>"
				+ "<li>Mod のバージョンが非互換。</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – 存在しないアニメーション";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon が存在しないアニメーションを再生しようとしました。</b>" + "<p>アニメーション:</p>" + "<code>" + animacion + "</code>"
				+ "<p>グループ:</p>" + "<code>" + grupo + "</code>" + "<p>このエラーは通常、以下の状況で発生します:</p>" + "<ul>"
				+ "<li>Cobblemon の非互換バージョンが混在している。</li>" + "<li>アドオンがインストール済みのバージョンと一致していない。</li>"
				+ "<li>内部リソースやアニメーションが不足している。</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "Lunar Client 内部クラッシュ";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Lunar Client の内部クラッシュが検出されました。</b>" + "<p>このエラーは Lunar Client 自体に起因しており、ベースゲームや Mod とは無関係です。</p>"
				+ "<p>Lunar Client は内部的にカスタム改造を施しており、これが " + "Mod や特定の設定との非互換性を引き起こす可能性があります。</p>"
				+ "<p>クライアント自体に起因する問題を除外するため、標準の Minecraft インストールでテストすることを推奨します。</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "メソッドまたはフィールドへの不正アクセス";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod が protected/private メソッドまたはフィールドにアクセスしようとしました。</b>" + "<p>問題のクラス:</p>" + "<code>" + clase
				+ "</code>" + "<p>アクセスされたメンバー:</p>" + "<code>" + miembro + "</code>" + "<p>このエラーは通常、以下の状況で発生します:</p>"
				+ "<ul>" + "<li>Mod が別の Minecraft バージョン向けにコンパイルされている。</li>" + "<li>非互換な mappings が混在している。</li>"
				+ "<li>Mod が古くなっている。</li>" + "<li>誤ったローダー（Fabric/Forge/NeoForge）を使用している。</li>" + "</ul>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "datapack/resourcepack の読み込みエラー";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "データパックまたはリソースパックの読み込みに失敗しました。</b>" + "<p>問題のあるファイル:</p>" + "<code>" + archivo + "</code>"
				+ "<p>パック:</p>" + "<code>" + pack + "</code>" + "<p>ゲームがこのファイルを解析できず、レジストリの読み込みエラーが発生しました。</p>"
				+ "<p>この問題は通常、以下の原因で発生します:</p>" + "<ul>" + "<li>不正な JSON 形式。</li>" + "<li>パックのバージョンが非互換。</li>"
				+ "<li>現在のゲームバージョンに対してパックが古くなっている。</li>" + "<li>複数のデータパック間の競合。</li>" + "</ul>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "シェーダーのコンパイルエラー";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "シェーダーのコンパイルに失敗しました。</b>"
				+ "<p>ゲームが有効なシェーダーのいずれかをコンパイルできませんでした。</p>" + "<p>この問題は通常、Sodium や Iris、または非互換のシェーダーパックに関連しています。</p>"
				+ "<p>推奨対応:</p>" + "<ul>" + "<li>別のシェーダーを試してください。</li>" + "<li>一時的にシェーダーを無効にしてください。</li>"
				+ "<li>GPU ドライバーを更新してください。</li>" + "<li>問題が続く場合は、Sodium を使わずにゲームを起動してみてください。</li>" + "</ul>";
	}

	public String nombreErrorCreacionModelo() {
		return "モデルの作成または読み込みエラー";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>Minecraft モデルの作成または読み込み中にエラーが発生しました。</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>影響を受けたモデル：<code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>この種のエラーは通常、以下の状況で発生します:</p>");
		sb.append("<ul>");
		sb.append("<li>Mod に誤って設定されたモデルが含まれている。</li>");
		sb.append("<li>JSON モデルが破損している、または不完全である。</li>");
		sb.append("<li>モデルやレンダリングを変更する Mod 間で競合が発生している。</li>");
		sb.append("<li>リソースパックまたはデータパックに非互換なモデルが含まれている。</li>");
		sb.append("</ul>");
		sb.append("<p>指定されたモデルを提供している Mod またはリソースパックを特定してください。</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>検出された可能性のある原因:</b></p>");
		sb.append("<p>ログ内で <b>Cooler Animations</b> Mod の活動が検出されました。</p>");
		sb.append("<p>この Mod はアニメーションおよびモデルシステムを変更し、場合によってはモデル読み込みエラーを引き起こすことがあります。</p>");
		sb.append("<p>問題が続く場合は、Cooler Animations を無効にしてゲームを起動し、エラーが解消するか確認してください。</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "Starlight の問題";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Starlight に関連するエラーが検出されました。</b>"
				+ "<p>エラーは <code>BlockStarLightEngine.initNibble</code> 内で発生しました。</p>"
				+ "<p>これは <b>Starlight</b> Mod の照明エンジンに障害が発生したことを示しています。</p>"
				+ "<p>Starlight は Minecraft の照明システムを完全に変更する Mod であり、特定の Mod 環境でさまざまな問題を引き起こすことが知られています。</p>"
				+ "<p>これは Starlight に関連する複数の既知のエラーのうちの1つにすぎません。</p>"
				+ "<p>問題が続く場合は、Starlight を無効にしてゲームを起動してみてください。</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "AAAParticles / Effekseer の問題";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Effekseer に関連するネイティブクラッシュが検出されました。</b>"
				+ "<p>エラーはネイティブライブラリ <code>EffekseerNativeForJava</code> 内で発生しました。</p>"
				+ "<p>このライブラリは、ChloePrime 氏が開発した <b>AAAParticles</b> Mod によって使用されています。</p>"
				+ "<p>ネイティブライブラリでのクラッシュは、通常、Mod 自体またはそのネイティブ依存関係に問題があることを示します。</p>"
				+ "<p>問題が続く場合は、AAAParticles を無効にしてゲームを起動してみてください。</p>";
	}

	// Japanese (日本語)
	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Java ランタイム環境 (JVM) のネイティブ クラッシュが検出されました。</b>"
				+ "<p>この種類のエラーは Java 仮想マシン自体内部（例：<code>jvm.dll</code>、<code>libjvm.so</code> など）で発生し、 "
				+ "必ずしも mod が原因ではありません。</p>" + "<p>まれに、互換性のないネイティブ ライブラリを使用する mod が原因であることもありますが、 "
				+ "<b>JVM の欠陥のある、破損した、または古くなったバージョンが原因である可能性の方がはるかに高いです</b>。</p>"
				+ "<p>これは、特に Java バージョンの古いビルドや非公式ビルド（例：サポートのないコミュニティ ビルド）を使用している場合に一般的です。</p>"
				+ "<p><b>信頼でき、メンテナンスされている JVM の使用を推奨します：</b></p>" + "<ul>"
				+ "<li><b>Red Hat Build of OpenJDK</b>（安定性、よくテスト済み、Windows/Linux に最適）</li>"
				+ "<li><b>OpenLogic OpenJDK</b>（クロスプラットフォーム対応、macOS Intel を含む）</li>"
				+ "<li><b>Azul Zulu</b>（認証済み、無料の LTS サポート付き）</li>" + "<li><b>Oracle JDK</b>（公式、定期的なアップデートあり）</li>"
				+ "</ul>" + "<p>不明な、カスタム、または非常に古いビルドは避けてください。JVM エンジンに重大なエラーが含まれている可能性があります。</p>"
				+ "<p><b>Prism Launcher または TLauncher を使用していますか？</b> カスタム JVM の設定は非常に簡単です： "
				+ "Prism Launcher では、<i>Instalaciones</i> → <i>Editar instancia</i> → <i>Configuración de Java</i> に移動します； "
				+ "TLauncher では、<i>Settings</i> → <i>Java Settings</i> に移動し、ダウンロードした JDK/JRE のパスを選択します。 "
				+ "ガベージ コレクタに問題が発生している可能性もあります；その場合は、ZGC に切り替える必要があります。"

				+ "システム JVM を変更する必要はありません！</p>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "Paranoia と C2ME の競合";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Paranoia Mod と C2ME Mod の間に競合が検出されました。</b>"
				+ "<p>エラーは、<code>ThreadLocalRandom</code> が不適切なスレッドからアクセスされたことを示しています。</p>"
				+ "<p>これは通常、<b>Paranoia</b> がスレッドセーフでないコードを実行している間に、<b>C2ME</b> がマルチスレッド最適化を行っている場合に発生します。</p>"
				+ "<p>このような競合は、MCreator で作成された Mod でよく見られます。</p>"
				+ "<p>問題が続く場合は、Paranoia を無効にしてゲームを起動するか、C2ME を無効にしてください。</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "Minecraft の assets ディレクトリが見つかりません";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft が assets ディレクトリを見つけられませんでした。</b>" + "<p>ランチャーが無効な assets パスでゲームを起動しようとしました。</p>"
				+ "<p>これは、ゲームのリソースファイル（assets）が存在しないか、正しくインストールされていないことを意味します。</p>"
				+ "<p>この問題は、通常、Minecraft の不完全なインストールやランチャーの誤設定によって発生します。</p>"
				+ "<p>FreshCraft のように assets を誤って処理する非公式ランチャーを使用している場合にも発生することがあります。</p>"
				+ "<p>問題が続く場合は、Modpack を再インストールするか、公式または信頼できるランチャーからゲームを起動してください。</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return dependencia + " のバージョン " + version + " 以降をインストールしてください";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return dependencia + " を " + min + " から " + max + " の間のバージョンに置き換えてください";
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "Mod " + mod + " は " + dependencia + " の最低バージョン " + version + " が必要です";
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "Mod " + mod + " は " + dependencia + " のバージョン " + version + " が必要です";
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {

		return "Mod " + mod + " は " + dependencia + " のバージョンが " + min + " から " + max + " の間である必要があります" + " (現在："
				+ actual + ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "Cupboard の互換性のないバージョン";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "古い Cupboard バージョンが原因のクラッシュが検出されました。</b>"
				+ "<p>エラーは <code>ClientConfigCompat.setupNeoforge</code> 内部で発生しており、"
				+ "<code>ModList.get()</code> が <code>null</code> を返すことが原因です。</p>"
				+ "<p>これは古い <b>Cupboard</b> モッドの既知の問題です。</p>" + "<p><b>3.2</b> などの古いバージョンにはこのバグが含まれています。</p>"
				+ "<p><b>解決策：</b>Cupboard をバージョン <b>3.5</b> 以降に更新してください。</p>";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		return "これは、macOS を使用しており、ゲームが OpenGL を使用しようとしているためです。しかし、最新の macOS バージョンでは OpenGL はサポートされていません。"
				+ "Metal をサポートする Minecraft のバージョンを使用するか、Intel、M1、または M2 チュップを搭載した Mac（M3+ または Neo を除く）を使用している場合は、Linux を使用する必要があります。";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "GeckoLib アニメーションが見つかりません";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ある Mod が GeckoLib のアニメーションファイルを読み込めませんでした。</b>" + "<p>影響を受けたファイル：</p>" + "<code>" + archivo
				+ "</code>" + "<p>このエラーは、アニメーション用の <code>.json</code> ファイルが存在しないか、"
				+ "構文エラーがある、またはパスが正しくない場合に発生します。</p>" + "<p>考えられる原因：</p>" + "<ul>"
				+ "<li>ファイルは削除されましたが、コード内でまだ参照されています。</li>" + "<li>JSON ファイル内に構文エラーがあります。</li>"
				+ "<li>Mod レジストリで定義されたパスが正しくありません。</li>" + "<li>依存関係の競合または互換性のないバージョン。</li>" + "</ul>";
	}

	@Override
	public String nombreAnimacionGeckoInexiste() {
		return "GeckoLib アニメーションが見つかりません";
	}

	@Override
	public String mensajeAnimacionGeckoInexiste(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod が GeckoLib のアニメーションファイルを見つけられませんでした。</b>" + "<p>影響を受けたファイル:</p>" + "<code>" + archivo + "</code>"
				+ "<p>このエラーは、GeckoLib が指定されたパスに存在しないアニメーションを読み込もうとした場合に発生します。 "
				+ "読み込みエラー（構文）とは異なり、このエラーはファイルが物理的に欠落しているか、パスが誤っていることを示します。</p>" + "<p>考えられる原因:</p>" + "<ul>"
				+ "<li><code>.json</code> ファイルが削除されたか、Mod の最終 JAR に含まれていません。</li>"
				+ "<li>コードで定義されたパスのタイポ（例：'animations' 対 'animaciones'）。</li>"
				+ "<li>大文字と小文字の不一致（サーバーの OS は Linux（区別する）で、開発は Windows（区別しない）でした）。</li>"
				+ "<li>Mod が完全に更新されていないか、依存関係が壊れています。</li>" + "</ul>";
	}

	@Override
	public String nombreRegistroDuplicadoObjeto() {
		return "重複登録の競合";
	}

	@Override
	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// メインメッセージ：説明テキストのみエラー色を適用
		String mensajeBase = "<span style='color:#" + color + "'>重大な競合：オブジェクトの登録が二重に試みられました。 " + "Mod </span>" + mod1
				+ "<span style='color:#" + color + "'> と </span>" + mod2 + "<span style='color:#" + color
				+ "'> が同じオブジェクトを登録しようとしています。 " + "競合オブジェクト： </span>" + objeto + "<br><br>";

		// 修復手順
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "これは通常、異なる 2 つの Mod が同じ namespace 内に同名のオブジェクトを追加した場合、 " + "またはどちらかの Mod のコードにエラーがある場合に発生します。<br><br>"
				+ "<b>推奨される解決策：</b><br>" + "<ul>" + "<li>一方の Mod がもう一方の更新版またはフォークかどうか確認してください。</li>"
				+ "<li>競合する 2 つの Mod のうち一方を削除してみてください。</li>"
				+ "<li>両方の Mod の設定ファイルを確認し、オブジェクトの ID を変更できるか検討してください。</li>" + "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreFalloFabricRenderingAPI() {
		return "Fabric Rendering API が見つかりません";
	}

	@Override
	public String mensajeFalloFabricRenderingAPI() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// メインメッセージ
		String mensajeBase = "<span style='color:#" + color + "'>Mod（通常は Porting Lib またはその依存関係）が失敗しました。理由は </span>"
				+ "Fabric Rendering API<span style='color:#" + color + "'> が利用できないためです。</span><br><br>";

		// 修復手順（現代バージョンでは Indium が廃れているため更新済み）
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>推奨される解決策：</b><br>"
				+ "メッセージは Indium のインストールを提案していますが、この Mod は現代のゲームバージョンでは廃れています。<br>" + "<ul>"
				+ "<li><b>Sodium</b> をバージョン <b>0.6.0</b> 以上に更新してください（このバージョンには必要なサポートが含まれています）。</li>"
				+ "<li>まだインストールしていない場合は、<b>Fabric API</b> がインストールされていることを確認してください。</li>"
				+ "<li>古いゲームバージョン（1.20.6 以下）を使用している場合は、Indium をインストールしてください。</li>" + "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRestriccionesDependenciaNoCumplidas() {
		return "依存関係の制限が満たされていません";
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// メインメッセージ
		String mensajeBase = "<span style='color:#" + color + "'>満たされていない依存関係の制限が </span>" + cantidad
				+ "<span style='color:#" + color + "'> 件見つかりました。</span><br><br>";

		// 競合リストの構築
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictos != null && !conflictos.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color).append("'>以下のファイルで競合が検出されました:</span><br><ul>");
			for (String[] par : conflictos) {
				String dep = par[0]; // 依存関係
				String jar = par[1]; // JAR ファイル
				// 変数はデフォルト色、固定テキストはエラー色
				listaDetalle.append("<li>").append("<span style='color:#").append(color).append("'>ファイル: </span>")
						.append(jar).append("<br><span style='color:#").append(color).append("'>必要要件: </span>")
						.append(dep).append("</li>");
			}
			listaDetalle.append("</ul><br>");
		}

		// 修復手順
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "これは、2 つ以上の Mod が同じ内部ライブラリの異なる非互換バージョンを必要とする場合に発生します。<br><br>" + "<b>推奨される解決策：</b><br>" + "<ul>"
				+ "<li>上記にリストされた Mod を更新または削除して、非互換性を解決してみてください。</li>"
				+ "<li>互換バージョンが見つからない場合は、Mod の JAR ファイル内の <code>mods.toml</code> ファイルを手動で編集（WinRAR や 7-Zip などの圧縮ツールを使用）してバージョン制限を変更または削除できますが、これにより不安定になる可能性があります。</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// メインメッセージ
		String mensajeBase = "<span style='color:#" + color + "'>満たされていない依存関係の制限が </span>" + cantidad
				+ "<span style='color:#" + color + "'> 件見つかりました。</span><br><br>";

		// Mod 別にグループ化されたリストの構築
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictosPorMod != null && !conflictosPorMod.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color).append("'>関連する Mod と要求された依存関係:</span><br><ul>");

			for (Map.Entry<String, List<String>> entry : conflictosPorMod.entrySet()) {
				String archivo = entry.getKey();
				List<String> dependencias = entry.getValue();

				// Mod 名（デフォルト色）
				listaDetalle.append("<li><b>").append(archivo).append("</b>");

				// この Mod の依存関係リスト
				listaDetalle.append("<ul>");
				for (String dep : dependencias) {
					// 依存関係（デフォルト色）
					listaDetalle.append("<li>").append(dep).append("</li>");
				}
				listaDetalle.append("</ul></li>");
			}
			listaDetalle.append("</ul><br>");
		} else {
			listaDetalle.append("<span style='color:#").append(color).append("'>ログから特定のファイルを特定できませんでした。</span><br>");
		}

		// 修復手順
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "このエラーは、Mod がお互いに非互換なライブラリの内部バージョン（JarInJar）を含んでいる場合に発生します。<br><br>" + "<b>推奨される解決策：</b><br>" + "<ul>"
				+ "<li>上記のリストを確認し、どの Mod が同じライブラリの異なるバージョンを要求しているかを特定してください。</li>"
				+ "<li>両方の Mod を最新バージョンに更新してみてください。</li>"
				+ "<li>最後の手段として、圧縮ツール（WinRAR など）を使用して Mod の <code>.jar</code> ファイルを開き、<code>META-INF/mods.toml</code> を編集して依存関係のバージョン範囲を手動で変更できますが、これは危険であり Mod を壊す可能性があります。</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String nombreNeruinaOcultaAdvertencia() {
		return "Neruina がデバッグを妨げています";
	}

	@Override
	public String mensajeNeruinaOcultaAdvertencia() {
		String color = Config.obtenerInstancia().obtenerColorAdvertencia();

		// 主要警告
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>警告：</b><b>Neruina</b> Mod がエラーの処理を試みて失敗し、クラッシュの真の原因を隠しています。</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Neruina は多くの場合不要であり、実際に何が失敗しているのかを把握しにくくします。デバッグのために削除することを推奨します。</span><br><br>";

		// 復旧手順
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>復旧手順:</b><br>"
				+ "1. **MCForge**: '[nombre_del_mundo]/serverconfig/forge-server.toml' に移動します。<br>"
				+ "2. **NeoForge**: 'config/neoforge-server.toml' に移動します。<br>"
				+ "   *（注：ローカルゲーム/Singleplayer では、ワールドは 'saves' フォルダ内にあります）*。<br>"
				+ "3. **removeErroringBlockEntities** と **removeErroringEntities** を **true** に変更します。<br><br>"
				+ "<b>その他のオプション:</b><br>" + "- **MCEdit**: 指定された座標でエンティティを手動で削除するために使用します。<br>"
				+ "- このエラーが持続する場合、Neruina が正しく機能しておらず、単に新しいエラーを生成している可能性があります。" + "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreApothicAttributeSinDueño() {
		return "Apothic Attributes エラー";
	}

	@Override
	public String mensajeApothicAttributeSinDueño(boolean chestCavityDetectado) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// メインメッセージ
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Apothic Attributes</b> が競合を検出しました：<b>AttributeMap</b> が割り当てられた所有者なしで変更されました。</span><br><br>"
				+ "<span style='color:#" + color + "'>" + "これは通常、Mod がエンティティの属性（体力、ダメージ、速度など）を不適切なタイミングまたは誤った方法で "
				+ "変更しようとした場合に発生します。</span><br><br>";

		// Chest Cavity に関する特定の注記
		String notaChestCavity = "";
		if (chestCavityDetectado) {
			notaChestCavity = "<span style='color:#" + color + "'>" + "<b>ログで Chest Cavity Mod が検出されました。</b> "
					+ "この Mod はエンティティ属性の処理方法により、この特定のエラーの一般的な原因です。</span><br><br>";
		}

		// 修復手順
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>推奨される解決策：</b><br>" + "<ul>"
				+ "<li>Chest Cavity がインストールされている場合は、原因かどうかを確認するために更新するか一時的に削除してみてください。</li>"
				+ "<li>モブの属性を変更する他の Mod がないか確認し、無効化してテストしてみてください。</li>"
				+ "<li><b>Apothic Attributes</b> の更新がないか確認してください。このエラーは最近のバージョンで修正されている可能性があります。</li>" + "</ul></span>";

		return mensajeBase + notaChestCavity + instrucciones;
	}

	@Override
	public String nombreErrorPotBlockEntity() {
		return "DecoratedPot エラー (Cataclysm)";
	}

	@Override
	public String mensajeErrorPotBlockEntity() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// メインメッセージ
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>DecoratedPotBlockEntity</b> に関連する互換性エラーが発生しました。</span><br><br>" + "<span style='color:#" + color
				+ "'>" + "これは Mod <b>L_Enders_Cataclysm</b> のバージョン 1.19.2 における既知の問題であり、"
				+ "ゲームが必要とする実装が欠落しています。</span><br><br>";

		// 解決策
		String solucion = "<span style='color:#" + color + "'>" + "<b>推奨される解決策：</b><br>"
				+ "このエラーを修正するには、<b>PotFix (Cataclysm Patch)</b> Mod をインストールしてください。<br>"
				+ "ここからダウンロードできます：<a href='https://www.curseforge.com/minecraft/mc-mods/potfix-cataclysm-patch'>CurseForge - PotFix</a>"
				+ "</span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorPreloadingTricks() {
		return "Preloading Tricks エラー";
	}

	@Override
	public String mensajeErrorPreloadingTricks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// メインメッセージ
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Preloading Tricks</b> による競合が検出されました。</span><br><br>" + "<span style='color:#" + color + "'>"
				+ "エラー <i>ClassCastException: String cannot be cast to ModuleDescriptor</i> "
				+ "は、Mod が Java モジュールシステムのクラスを誤って操作していることを示しています。</span><br><br>";

		// 説明と解決策
		String explicacion = "<span style='color:#" + color + "'>"
				+ "<b>Preloading Tricks</b> は主に <b>開発者</b> 向けに設計された Mod です。 "
				+ "ゲーム読み込みの非常に早い段階で複雑なクラス変更操作（mixins）を実行するため、 " + "他の相互作用がある場合、簡単に安定性を損なう可能性があります。</span><br><br>"
				+ "<span style='color:#" + color + "'><b>推奨される解決策：</b><br>" + "<ul>"
				+ "<li><b>Preloading Tricks</b> Mod を削除してください。通常、公開サーバーや安定したパックでプレイする際には不要です。</li>"
				+ "<li>開発者としてテストに必要である場合は、環境設定を確認してください。</li>" + "</ul></span>";

		return mensajeBase + explicacion;
	}

	@Override
	public String nombreErrorSimpleRadioLexiconfig() {
		return "Simple Radio / Lexiconfig 互換性エラー";
	}

	@Override
	public String mensajeErrorSimpleRadioLexiconfig() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// メインメッセージ
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Simple Radio</b> と <b>Lexiconfig</b> の間に競合が検出されました。</span><br><br>" + "<span style='color:#"
				+ color + "'>" + "エラーは 'shelveLexicons' プロセス中に発生しており、両ライブラリ間にバイナリ非互換性があることを示しています。</span><br><br>";

		// 特定の解決策
		String solucion = "<span style='color:#" + color + "'>" + "<b>既知の原因：</b><br>"
				+ "Simple Radio は通常、Lexiconfig の旧バージョン向けに設計されていますが、より新しいバージョンがインストールされています。</span><br><br>"
				+ "<span style='color:#" + color + "'><b>推奨される解決策：</b><br>" + "<ul>"
				+ "<li><b>Lexiconfig</b> の古いバージョンを使用してみてください。</li>"
				+ "<li>バージョン <b>1.3.11</b> 以前を試すことを推奨します。これらは通常、Simple Radio と互換性があります。</li>"
				+ "<li>問題が持続する場合は、Simple Radio の利用可能な更新がないか確認してください。</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorMobAITweaks() {
		return "Mob AI Tweaks エラー";
	}

	@Override
	public String mensajeErrorMobAITweaks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// メインメッセージ
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Mob AI Tweaks</b> に関連するエラーが検出されました。</span><br><br>" + "<span style='color:#" + color + "'>"
				+ "このエラーは Mixin（<code>$mob-ai-tweaks$onSpawned</code>）から発生しており、 " + "エンティティがスポーンする際に介入します。これは通常、 "
				+ "モブのスポーン行動も変更する別の Mod との競合を示しています。</span><br><br>";

		// 解決策
		String solucion = "<span style='color:#" + color + "'><b>推奨される解決策：</b><br>" + "<ul>"
				+ "<li><b>Mob AI Tweaks</b> を削除して、不安定性が解消されるか確認してみてください。</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	public String nombre_verificacion_gpu() {
		return "GPU 検証（OpenGL / GPU 選択）";
	}

	public String desactivar_parche_gpu() {
		return "GPU 検証を無効にする";
	}

	// ==================== CRASH ====================

	public String gpu_crash_posible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>GPU 検証ツールがゲームの終了を引き起こした可能性があります。</b>";
	}

	public String gpu_crash_causas() {
		return "検証は開始されましたが完了しませんでした。これは通常、OpenGL またはグラフィックスドライバーの障害を示しています。<br><br>" + "考えられる原因:<br>"
				+ "- 古いまたは不安定なドライバー<br>" + "- OpenGL に関する問題<br>" + "- 古い GPU またはハイブリッド構成";
	}

	public String gpu_crash_recomendaciones() {
		return "推奨事項:<br>" + "- GPU ドライバーを更新する<br>" + "- 専用 GPU の使用を強制する<br>" + "- 遠隔または仮想化環境を避ける";
	}

	// ==================== NO ÓPTIMA ====================

	public String gpu_no_optima() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>ゲームが利用可能な最適な GPU を使用していません。</b>";
	}

	public String gpu_no_optima_detalles() {
		return "これによりパフォーマンスが低下する（低フレームレート）可能性がありますが、通常、単独ではクラッシュの原因にはなりません。";
	}

	public String gpu_recomendaciones_rendimiento() {
		return "推奨事項:<br>" + "- コントロールパネルで専用 GPU の使用を強制する<br>" + "- Java/Minecraft を高性能モードに設定する";
	}

	// ==================== GENERALES ====================

	public String gpu_nota_precision() {
		return "<b>注記：</b>この検出システムは 100% 完璧ではありません。";
	}

	public String gpu_consumo_energia() {
		return "より強力な GPU はより多くの電力を消費し、ノートパソコンではバッテリー持続時間を短縮させる可能性があります。";
	}

	public String gpu_parche_info() {
		return "クイックフィックスボタンを使用して、この検証を無効にできます。";
	}

	@Override
	public String nombreVerificacionRaptorLake() {
		return "Intel 第 13/14 世代 CPU 安定性警告";
	}

	@Override
	public String advertenciaRaptorLakeTitulo() {
		return "Intel Raptor Lake プロセッサに不安定性の可能性";
	}

	@Override
	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String targetMicrocode) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "プロセッサ " + cpu + "、マイクロコード " + microcode
				+ " が検出されました。" + "</b> " + "Intel 第 13 世代および第 14 世代プロセッサは、過剰な電圧要求により不安定性の問題を経験しており、"
				+ "これがプロセッサの寿命を短縮する可能性があります。<br><br>" + "マイクロコードまたはマザーボードの BIOS を、マイクロコード <b>" + targetMicrocode
				+ "</b> 以降を含むバージョンに更新することを推奨します。" + "<b>警告：</b>BIOS の更新は、正しく行われない場合リスクを伴います。<br><br>"
				+ "<i>注記：これは現在のクラッシュの原因である可能性はほぼありません。ハードウェアの健康状態に関する情報提供のお知らせです。</i>";
	}

	@Override
	public String desactivarVerificacionRaptorLake() {
		return "これについて再度警告しない";
	}

	@Override
	public String verArticuloRaptorLake(String fuente) {
		return fuente + " で記事を読む";
	}

	@Override
	public String tituloMixins() {
		return "Mixins エクスプローラー";
	}

	@Override
	public String mixinsBotonLateral() {
		return "Mixins";
	}

	@Override
	public String mixinsRaiz() {
		return "見つかった Mixins";
	}

	@Override
	public String mixinsTodosLosMods() {
		return "すべて";
	}

	@Override
	public String mixinsModConMixin() {
		return "mixins を含む Mod";
	}

	@Override
	public String mixinsTooltipCombo() {
		return "mixins を含む Mod でフィルタリング";
	}

	@Override
	public String mixinsRecargar() {
		return "再読み込み";
	}

	@Override
	public String mixinsDescompilarSeleccion() {
		return "選択内容を逆コンパイル";
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
		return "メソッドの Targets";
	}

	@Override
	public String mixinsMetodos() {
		return "メソッド";
	}

	@Override
	public String mixinsCampos() {
		return "フィールド";
	}

	@Override
	public String mixinsCantidad() {
		return "Mixins 数";
	}

	@Override
	public String mixinsDetalleMixin() {
		return "Mixin 詳細";
	}

	@Override
	public String mixinsDetalleTarget() {
		return "Target 詳細";
	}

	@Override
	public String mixinsDetalleMetodo() {
		return "Mixin メソッド詳細";
	}

	@Override
	public String mixinsDetalleCampo() {
		return "Mixin フィールド詳細";
	}

	@Override
	public String mixinsDetalleConflicto() {
		return "競合詳細";
	}

	@Override
	public String mixinsBuscarConflictos() {
		return "可能性のある競合を検索";
	}

	@Override
	public String mixinsResultadosConflictos() {
		return "競合結果";
	}

	@Override
	public String mixinsConflictosPosibles() {
		return "可能性のある競合";
	}

	@Override
	public String mixinsErrorDescompilar() {
		return "逆コンパイルエラー";
	}

	@Override
	public String mixinsColorPanel() {
		return "Mixins パネルの色";
	}

	@Override
	public String mixinsColorTexto() {
		return "Mixins テキストの色";
	}

	@Override
	public String mixinsColorTextoSuave() {
		return "Mixins 補助テキストの色";
	}

	@Override
	public String mixinsAyudaUso1() {
		return "このツールは SpongePowered のミックスインを含む Mod を表示し、それらのクラス、targets、メソッド、フィールドを検査できます。";
	}

	@Override
	public String mixinsAyudaUso2() {
		return "上部のセレクターを使用して、特定の Mod でフィルタリングするか、ミックスインを含むすべての Mod を表示します。";
	}

	@Override
	public String mixinsAyudaUso3() {
		return "ツリーを展開して、各 mixin、そのターゲットクラス、注釈付きメソッド、shadow フィールドを表示します。";
	}

	@Override
	public String mixinsAyudaUso4() {
		return "Mod、mixin、target、メソッド、またはフィールドを右クリックして、他の mixins との潜在的な競合を検索します。";
	}

	@Override
	public String mixinsAyudaUso5() {
		return "関連コードを検査するために、現在の選択内容または競合結果を逆コンパイルできます。";
	}

	@Override
	public String mixinsColorComboFondo() {
		return "Mod 選択機の背景色";
	}

	@Override
	public String mixinsColorAreaContenidoFondo() {
		return "詳細パネルの背景色";
	}

	@Override
	public String mixinsColorSeleccionTexto() {
		return "テキスト選択色";
	}

	@Override
	public String mixinsColorSeleccionTextoActivo() {
		return "選択されたテキストの色";
	}

	@Override
	public String mixinsColorAyudaTexto() {
		return "ヘルプテキストの色";
	}

	@Override
	public String mixinsColorArbolFondo() {
		return "ツリーの背景色";
	}

	@Override
	public String mixinsColorRendererSeleccionTexto() {
		return "ツリーの選択されたテキストの色";
	}

	@Override
	public String mixinsColorRendererSeleccionFondo() {
		return "ツリーの選択された背景の色";
	}

	@Override
	public String mixinsColorRendererBordeSeleccion() {
		return "ツリーの選択枠の色";
	}

	@Override
	public String depmapTitulo() {
		return "依存関係マップ";
	}

	@Override
	public String depmapBotonLateral() {
		return "DepMap";
	}

	@Override
	public String depmapPestanaMapa() {
		return "マップ";
	}

	@Override
	public String depmapPestanaDependientes() {
		return "依存先";
	}

	@Override
	public String depmapRecargar() {
		return "再読み込み";
	}

	@Override
	public String depmapDescompilarSeleccion() {
		return "選択内容を逆コンパイル";
	}

	@Override
	public String depmapVerReferencias() {
		return "参照を表示";
	}

	@Override
	public String depmapDependencias() {
		return "依存関係";
	}

	@Override
	public String depmapDependientes() {
		return "依存先";
	}

	@Override
	public String depmapDependiente() {
		return "依存先";
	}

	@Override
	public String depmapSinDependencias() {
		return "依存先なし";
	}

	@Override
	public String depmapSeleccionarMod() {
		return "Mod を選択";
	}

	@Override
	public String depmapSeleccionarModBase() {
		return "基本 Mod";
	}

	@Override
	public String depmapSeleccionarDependiente() {
		return "依存 Mod";
	}

	@Override
	public String depmapSeleccionarPaquete() {
		return "パッケージ";
	}

	@Override
	public String depmapComprobarNoAlineadas() {
		return "未整合を確認";
	}

	@Override
	public String depmapResultadoNoAlineadas() {
		return "未整合な依存関係の結果";
	}

	@Override
	public String depmapClaseInexistente() {
		return "存在しないクラス";
	}

	@Override
	public String depmapClaseReferenciada() {
		return "参照されたクラス";
	}

	@Override
	public String depmapOrigen() {
		return "元";
	}

	@Override
	public String depmapDestino() {
		return "宛先";
	}

	@Override
	public String depmapDependenciaDetalle() {
		return "依存関係の詳細";
	}

	@Override
	public String depmapReferenciaDetalle() {
		return "参照の詳細";
	}

	@Override
	public String depmapMetodoOrigen() {
		return "元のメソッド";
	}

	@Override
	public String depmapModBase() {
		return "基本 Mod";
	}

	@Override
	public String depmapTodos() {
		return "すべて";
	}

	@Override
	public String depmapSeleccioneUnMod() {
		return "Mod を選択してください";
	}

	@Override
	public String depmapSeleccioneParametrosNoAlineadas() {
		return "基本 Mod、依存 Mod、およびパッケージを選択してください";
	}

	@Override
	public String depmapSeleccioneClaseParaDescompilar() {
		return "逆コンパイルする参照または発見項目を選択してください";
	}

	@Override
	public String depmapErrorDescompilar() {
		return "逆コンパイルエラー";
	}

	@Override
	public String depmapAyuda1() {
		return "このツールは、Mod 間のクラス参照を使用して Mod 間の依存関係マップを構築します。";
	}

	@Override
	public String depmapAyuda2() {
		return "マップタブは、各 Mod をそれが使用する依存関係にリンクしたバブルグラフを表示します。";
	}

	@Override
	public String depmapAyuda3() {
		return "依存先タブは、依存先が最も多い Mod から全くない Mod まで並べ替えます。";
	}

	@Override
	public String depmapAyuda4() {
		return "Mod とその依存関係間の特定の参照を検査し、関連するクラスを逆コンパイルできます。";
	}

	@Override
	public String depmapAyuda5() {
		return "未整合な依存関係のチェックは、基本 Mod のパッケージまたはサブパッケージ内にある存在しないクラスへの参照を探します。";
	}

	@Override
	public String depmapColorPanel() {
		return "パネルの色";
	}

	@Override
	public String depmapColorTexto() {
		return "メインテキストの色";
	}

	@Override
	public String depmapColorTextoSecundario() {
		return "補助テキストの色";
	}

	@Override
	public String depmapColorAyudaTexto() {
		return "ヘルプテキストの色";
	}

	@Override
	public String depmapColorGrafoFondo() {
		return "グラフの背景色";
	}

	@Override
	public String depmapColorListaFondo() {
		return "リストの背景色";
	}

	@Override
	public String depmapColorArbolFondo() {
		return "ツリーの背景色";
	}

	@Override
	public String depmapColorNodo() {
		return "グラフノードの色";
	}

	@Override
	public String depmapColorEnlace() {
		return "グラフリーンの色";
	}

	@Override
	public String depmapColorSeleccion() {
		return "選択色";
	}

	@Override
	public String depmapColorSeleccionTexto() {
		return "選択されたテキストの色";
	}

	@Override
	public String nombreProblemaAzureLibAnimaciones() {
		return "AzureLib アドオンの問題";
	}

	@Override
	public String mensajeProblemaAzureLibAnimaciones() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "アニメーションの読み込み中に AzureLib エラーが検出されました。</b>" + "<p>ゲームが形式が正しくないアニメーション JSON を見つけました。</p>"
				+ "<p>この問題は通常、<b>AzureLib</b> を使用している Mod またはアドオンの 1 つが原因です。</p>" + "<p><b>推奨事項：</b></p>" + "<ul>"
				+ "<li>サイドバーの <b>DepMap</b> を使用して、AzureLib に依存するすべてのアドオンを探してください。</li>"
				+ "<li>故障しているものを見つけるまで、それらのアドオンの一部を無効にしてゲームを起動してみてください。</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaDecocraftNatureEssentialPartnerMod() {
		return "Decocraft Nature の問題";
	}

	@Override
	public String mensajeProblemaDecocraftNatureEssentialPartnerMod() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Decocraft Nature による問題が検出されました。</b>" + "<p>このエラーは、mixin 設定 "
				+ "<code>com/razz/essentialpartnermod/mixins.json</code> の初期化中に発生します。</p>"
				+ "<p>この問題は、Mod の JAR ファイルを編集することで修正できます。</p>" + "<p><b>手順：</b></p>" + "<ul>"
				+ "<li>File Roller、Ark、WinRAR、7-Zip、または WinZip などのアーカイバで JAR ファイルを開きます。</li>"
				+ "<li><code>META-INF/MANIFEST.MF</code> に移動します。</li>" + "<li>この行を削除します：</li>" + "</ul>"
				+ "<code>MixinConfigs: com/razz/essentialpartnermod/mixins.json</code>"
				+ "<p><b>重要：</b>ファイル末尾の空行 1 つはそのまま残してください。</p>";
	}

	@Override
	public String mensajeTetraDeserializadorModeloEstatico() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Tetra またはそのアドオンでエラーが検出されました。</b>"
				+ "<p>ログには、<b>Tetra</b> またはその補完機能によって使用されるモデルタイプのデシリアライザが見つからなかったことが示されています。</p>"
				+ "<p><b>推奨される手順:</b></p>" + "<ul>" + "<li>まず、<b>Tetraのアドオン</b>を削除または無効にして、再度お試しください。</li>"
				+ "<li>エラーが続く場合は、<b>Tetra</b>自体も削除してみてください。</li>"
				+ "<li><b>DepMap</b>でTetra関連のアドオンを探してみることができますが、常にそこに表示されるとは限りません。</li>" + "</ul>"
				+ "<p>場合によっては問題がアドオンに起因することもありますが、他のケースでは<b>Tetra</b>自体が原因となっていることがあります。</p>";
	}

	@Override
	public String nombreTetraDeserializadorModeloEstatico() {
		return "Tetraにおけるモデルのデシリアライズエラー";
	}

	@Override
	public String mensajeSimpleEmotesSetupAnimTail() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Simple Emotesでエラーが検出されました。</b>"
				+ "<p>ログには文字列 <b>$simpleemotes$setupAnimTAIL</b> が含まれており、これは直接 <b>Simple Emotes</b> モッドを指しています。</p>"
				+ "<p><b>推奨オプション:</b></p>" + "<ul>" + "<li><b>Simple Emotes</b> を削除または無効にして、再度お試しください。</li>"
				+ "<li>プレイヤーやモデルのアニメーションを変更するモッドを使用している場合は、<b>Simple Emotes</b> との潜在的な非互換性を確認してください。</li>"
				+ "<li><b>Simple Emotes</b> およびアニメーション関連のすべてのモッドを互換性のあるバージョンに更新してください。</li>" + "</ul>"
				+ "<p>このエラーは通常、<b>Simple Emotes</b> が障害に直接関与していることを示します。</p>";
	}

	@Override
	public String nombreSimpleEmotesSetupAnimTail() {
		return "Simple Emotesのエラー";
	}

	@Override
	public String mensajeAdvertenciaSKLauncher() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "SKLauncherに関する警告。</b>" + "<p>過去数ヶ月間、<b>SKLauncher</b>に関連する<b>破損</b>やその他の問題が複数件観察されています。</p>"
				+ "<p>これは、SKLauncherが常にエラーの原因であるという意味ではありませんが、問題の一因となっている可能性があります。</p>" + "<p><b>破損の兆候:</b></p>"
				+ "<ul>" + "<li>ゲームが起動過程のごく早期に終了する。</li>" + "<li><b>Modがインストールされていない状態</b>でもゲームがクラッシュする。</li>" + "</ul>"
				+ "<p>これらのいずれかのケースが発生した場合は、<b>他のランチャー</b>を使用して問題が解消するか確認してください。</p>"
				+ "<p>推奨されるランチャーのリストは こちら を参照してください:</p>"
				+ "<p><a href='https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/docs/ingles/minecraft/Launchers.md'>ランチャーのドキュメントを表示</a></p>";
	}

	@Override
	public String nombreAdvertenciaSKLauncher() {
		return "警告: SKLauncherの潜在的な問題";
	}

	@Override
	public String guardTitulo() {
		return "CD Guard";
	}

	@Override
	public String guardBotonLateral() {
		return "ガード";
	}

	@Override
	public String guardEscanearTodo() {
		return "サーバーとマルウェアをスキャン";
	}

	@Override
	public String guardEscanearServidores() {
		return "サーバーを検索";
	}

	@Override
	public String guardEscanearMalware() {
		return "マルウェアを検索";
	}

	@Override
	public String guardTablaServidores() {
		return "問題のあるサーバー";
	}

	@Override
	public String guardTablaMalware() {
		return "マルウェアの検出結果";
	}

	@Override
	public String guardColServidor() {
		return "サーバー";
	}

	@Override
	public String guardColDefinicion() {
		return "定義";
	}

	@Override
	public String guardColMensaje() {
		return "メッセージ";
	}

	@Override
	public String guardColUbicacion() {
		return "場所";
	}

	@Override
	public String guardColClase() {
		return "クラス";
	}

	@Override
	public String guardColCfr() {
		return "CFR";
	}

	@Override
	public String guardCfr() {
		return "デコンパイル";
	}

	@Override
	public String guardCfrTitulo() {
		return "デコンパイルされたコード";
	}

	@Override
	public String guardDescripcion1() {
		return "このツールは、Mod内の問題のあるサーバーや潜在的なマルウェアの検出結果を検索することができます。";
	}

	@Override
	public String guardDescripcion2() {
		return "他の定義やマルウェアスキャナーが攻撃的な場合、特に誤検知（false positives）が発生する可能性があります。";
	}

	@Override
	public String guardDescripcion3() {
		return "サーバーチェックは外部定義を使用します。ダウンロードしていない場合は、まずダウンロードする必要があります。";
	}

	@Override
	public String guardDescripcion4() {
		return "すでにローカル定義がある場合、ツールはそれを再利用するか更新するかを決定することを許可します。";
	}

	@Override
	public String guardDescripcion5() {
		return "マルウェア表でクラスが利用可能な場合、CFRを使用してデコンパイルし、検査することができます。";
	}

	@Override
	public String guardEstadoEscaneandoTodo() {
		return "サーバーとマルウェアをスキャン中...";
	}

	@Override
	public String guardEstadoEscaneandoServidores() {
		return "問題のあるサーバーを検索中...";
	}

	@Override
	public String guardEstadoEscaneandoMalware() {
		return "マルウェアを検索中...";
	}

	@Override
	public String guardEstadoListo() {
		return "準備完了";
	}

	@Override
	public String guardDefsNoEncontradasTitulo() {
		return "定義が見つかりません";
	}

	@Override
	public String guardDefsNoEncontradasMensaje() {
		return "サーバー定義が見つかりません。今すぐダウンロードしますか？";
	}

	@Override
	public String guardDefsDescargar() {
		return "ダウンロード";
	}

	@Override
	public String guardDefsCancelar() {
		return "キャンセル";
	}

	@Override
	public String guardDefsActualizarTitulo() {
		return "サーバー定義";
	}

	@Override
	public String guardDefsActualizarMensaje() {
		return "ローカル定義が既に存在します。そのまま使用しますか、それとも更新しますか？";
	}

	@Override
	public String guardDefsUsarLocales() {
		return "ローカルを使用";
	}

	@Override
	public String guardDefsActualizar() {
		return "更新";
	}

	@Override
	public String guardFuenteDefinicionesTLauncher() {
		return "TLauncherリスト";
	}

	@Override
	public String guardErrorDescompilar() {
		return "デコンパイルエラー";
	}

	@Override
	public String guardColorPanel() {
		return "パネルの色";
	}

	@Override
	public String guardColorTexto() {
		return "テキストの色";
	}

	@Override
	public String guardColorTextoSecundario() {
		return "補助テキストの色";
	}

	@Override
	public String guardColorTabla() {
		return "テーブルの色";
	}

	@Override
	public String guardColorSeleccion() {
		return "選択色";
	}

	@Override
	public String guardColorSeleccionTexto() {
		return "選択されたテキストの色";
	}

	@Override
	public String texto_de_boton_compartir_instancia_modpack() {
		return "インスタンス/Modパックを共有";
	}

	@Override
	public String popup_compartir_instancia_modpack() {
		return "インスタンスまたはModパックを共有する機能はまだ実装されていません。";
	}

	@Override
	public String colorBotonCompartirVerdeOscuro() {
		return "メイン共有ボタンの色";
	}

	@Override
	public String colorBotonCompartirVerdeClaro() {
		return "リンク共有ボタンの色";
	}

	@Override
	public String colorTextoBotonesCompartir() {
		return "共有ボタンのテキスト色";
	}

	@Override
	public String compartirInstanciaTitulo() {
		return "インスタンスを共有";
	}

	@Override
	public String compartirInstanciaBotonLateral() {
		return "インスタンスを共有";
	}

	@Override
	public String compartirInstanciaFormato() {
		return "形式";
	}

	@Override
	public String compartirInstanciaServicio() {
		return "アップロードサービス";
	}

	@Override
	public String compartirInstanciaBotonCompartir() {
		return "パッケージ化して共有";
	}

	@Override
	public String compartirInstanciaBotonRefrescar() {
		return "更新";
	}

	@Override
	public String compartirInstanciaEstadoListo() {
		return "準備完了";
	}

	@Override
	public String compartirInstanciaEstadoEmpaquetando() {
		return "選択内容をパッケージ化中...";
	}

	@Override
	public String compartirInstanciaEstadoSubiendo() {
		return "ファイルをアップロード中...";
	}

	@Override
	public String compartirInstanciaEstadoError() {
		return "エラー";
	}

	@Override
	public String compartirInstanciaCodigo() {
		return "コード";
	}

	@Override
	public String compartirInstanciaEnlace() {
		return "リンク";
	}

	@Override
	public String compartirInstanciaMantenerAbierto() {
		return "転送を利用可能にするには、アプリケーションを開いたままにする必要があります。";
	}

	@Override
	public String compartirInstanciaSinSeleccion() {
		return "フォルダまたはファイルが選択されていません。";
	}

	@Override
	public String compartirInstanciaFormatoNoSoportado() {
		return "その形式はまだサポートされていません。";
	}

	@Override
	public String compartirInstanciaServicioNoDisponible() {
		return "選択したサービスは利用できません。";
	}

	@Override
	public String compartirInstanciaSubidaCompleta() {
		return "転送が正常に開始されました。";
	}

	@Override
	public String compartirInstanciaErrorSubir() {
		return "選択したファイルをアップロードできませんでした。";
	}

	@Override
	public String compartirInstanciaColorPanel() {
		return "パネルの色";
	}

	@Override
	public String compartirInstanciaColorTexto() {
		return "テキストの色";
	}

	@Override
	public String compartirInstanciaPolitica1() {
		return "推奨されるタイプ: mods、configs、saves、worlds、datapacks、リソースパック、オプションファイル。不要な個人情報は含めないでください。";
	}

	@Override
	public String compartirInstanciaPolitica2() {
		return "拡張機能は独自のアップロードサービスを追加できます。デフォルトで統合されているサービスはここに表示されます。";
	}

	@Override
	public String compartirInstanciaPolitica3() {
		return "wormhole.app: 通常のアップロードとして最大 5 GiBまで可能; 5〜10 GiBの場合は送信側を開いたままにする必要があります。現在のプロジェクト実装では、実際の統合はまだ保留中です。";
	}

	@Override
	public String compartirInstanciaPolitica4() {
		return "limewire.com: 一時保持を目的としたサービスです。この実装ではまだサポートされていません。";
	}

	@Override
	public String compartirInstanciaPolitica5() {
		return "bittorrent: 中央ホスティングのない直接P2P配布であるため、最も安全なモードです。この実装ではまだサポートされていません。";
	}

	@Override
	public String compartirInstanciaPolitica6() {
		return "技術サポートを容易にするために、デフォルトでインスタンスの最も一般的なフォルダとファイルが選択されます。";
	}

	@Override
	public String compartirInstanciaPolitica7() {
		return Statics.nombre_cd.obtener() + "の内部フォルダを含めると、設定、ログ、および補助データも転送されるため、不要であれば選択を解除できます。";
	}

	@Override
	public String malware_fracturiser_detectado() {
		return "Fracturiserの可能性があります。証拠:";
	}

	@Override
	public String malware_info_stealer_detectado() {
		return "情報窃取の可能性があります。証拠:";
	}

	@Override
	public String malware_evidencia_clase_sospechosa() {
		return "不審なクラス:";
	}

	@Override
	public String malware_evidencia_archivo_sospechoso() {
		return "不審なファイル:";
	}

	@Override
	public String malware_brightsdk_detectado() {
		return "Bright SDKの可能性があります。証拠:";
	}

	@Override
	public String malware_evidencia_paquete_sospechoso() {
		return "不審なパッケージ:";
	}

	@Override
	public String docsTituloVentana() {
		return "ドキュメントリーダー";
	}

	@Override
	public String docsAyudaDeUso() {
		return "<b>このリーダーの使い方</b><br>" + "下部で言語を選択すると、その言語で利用可能なドキュメントが表示されます。<br>"
				+ "左側のパネルでフォルダやドキュメントをナビゲートできます。<br>" + "ドキュメントをクリックすると、右側にその内容が表示されます。<br>"
				+ "<b>docs://</b> プロトコルの内部リンクは、このリーダー内で他のドキュメントを開きます。";
	}

	@Override
	public String docsArbolTitulo() {
		return "ドキュメント";
	}

	@Override
	public String docsVisorTitulo() {
		return "コンテンツ";
	}

	@Override
	public String docsNoHayDocumentos() {
		return "この言語のドキュメントはありません。";
	}

	@Override
	public String docsNoHayMarkdown() {
		return "この言語のMarkdownファイルが見つかりません。";
	}

	@Override
	public String docsDocumentoNoEncontrado() {
		return "要求されたドキュメントが見つかりません。";
	}

	@Override
	public String docsErrorAlAbrirDocumento() {
		return "ドキュメントを開く際のエラー：";
	}

	@Override
	public String docsCargando() {
		return "ドキュメントを読み込み中...";
	}

	@Override
	public String docsImagenNoDisponible() {
		return "イラストは利用できません";
	}

	// Correction for Japanese natural flow
	@Override
	public String colorPanelSecundario() {
		return "補助パネルの色";
	}

	@Override
	public String colorTextoSuave() {
		return "柔らかいテキストの色";
	}

	@Override
	public String colorSeleccion() {
		return "選択色";
	}

	@Override
	public String colorFondoDocumento() {
		return "ドキュメントの背景色";
	}

	@Override
	public String iaTituloVentana() {
		return "AI";
	}

	@Override
	public String iaTituloPrincipal() {
		return "AI分析";
	}

	@Override
	public String iaDescripcionTitulo() {
		return "クラッシュ分析エージェント";
	}

	@Override
	public String iaDescripcionCuerpo() {
		return "このツールは、Minecraft関連のクラッシュ、エラー、ログの分析をサポートする外部エージェントを開きます。";
	}

	@Override
	public String iaDescripcionUso() {
		return "このシステムを使用するには、リンクを開き、Baiduアカウントでログインしてから、エージェントを使用してクラッシュレポートやログを確認してください。";
	}

	@Override
	public String iaAvisoCuentaBaidu() {
		return "エージェントにアクセスするにはBaiduアカウントが必要です。";
	}

	@Override
	public String iaCopiarEnlace() {
		return "リンクをコピー";
	}

	@Override
	public String iaAbrirEnlace() {
		return "リンクを開く";
	}

	@Override
	public String iaImagenNoDisponible() {
		return "画像は利用できません";
	}

	@Override
	public String mensajeOculusIrisUnknownShaderVariable() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "OculusまたはIrisに関するシェーダーのエラーが検出されました。</b>"
				+ "<p>ログには <b>kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal</b> "
				+ "および <b>java.lang.RuntimeException: Unknown variable:</b> が含まれています。</p>"
				+ "<p>この組み合わせは、シェーダー内の変数評価中に問題が発生したことを示しており、"
				+ "多くの場合 <b>Oculus</b>、<b>Iris</b>、または使用中の<b>シェーダーパック</b>に関連しています。</p>" + "<p><b>推奨されるテスト順序：</b></p>"
				+ "<ul>" + "<li>まず、<b>シェーダーを無効にした状態</b>でゲームを起動してください。</li>"
				+ "<li>問題が続く場合は、<b>OculusまたはIrisなし</b>で起動してみてください。</li>"
				+ "<li><b>Oculus/Iris</b>、<b>シェーダーパック</b>、および関連するグラフィックMODを更新してください。</li>"
				+ "<li>他のレンダリングやグラフィックMODを使用している場合は、それらの間に互換性の問題がないか確認してください。</li>" + "</ul>"
				+ "<p>実際には、この障害は通常<b>シェーダーパック</b>、またはそれが<b>Oculus/Iris</b>と相互作用することに起因します。</p>";
	}

	@Override
	public String nombreOculusIrisUnknownShaderVariable() {
		return "Oculus/Irisに関するシェーダーのエラーの可能性";
	}

	@Override
	public String mensajeItemNoExiste(String itemFaltante, String namespace) {
		String itemHtml = itemFaltante == null || itemFaltante.isEmpty() ? "(不明)" : itemFaltante;
		String namespaceHtml = namespace == null || namespace.isEmpty() ? "(不明)" : namespace;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "存在しないアイテムを使用しようとしました。</b>"
				+ "<p>ログには <b>Item: " + itemHtml + " does not exist</b> という行が含まれています。</p>"
				+ "<p>これは通常、一部の <b>データパック</b>、<b>MOD</b>、または <b>設定</b> が " + "ゲーム内に存在しないアイテムを参照していることを意味します。</p>"
				+ "<p><b>確認事項：</b></p>" + "<ul>" + "<li>アイテム <b>" + itemHtml
				+ "</b> を提供すべき MOD がインストールされているか確認してください。</li>"
				+ "<li>インストールされている場合、<b>バージョンが間違っている</b>か、アイテムが変更または削除されたか、" + "MOD に問題があり、削除した方が良い可能性があります。</li>"
				+ "<li>その MOD がない場合は、<b>インストール</b>してみてください。</li>" + "</ul>"
				+ "<p><b>どの MOD やデータパックがそのアイテムを要求しているかを確認するには：</b></p>" + "<ul>"
				+ "<li>サイドバーの <b>grepr</b> ユーティリティを使用します。</li>" + "<li>フォルダを <b>選択しないで</b>ください。</li>"
				+ "<li><b>search in archives</b> オプションを有効にします。</li>" + "<li>検索テキストに <b>namespace</b>、つまりコロン前の部分を入力します： "
				+ "<b>" + namespaceHtml + "</b>。</li>" + "</ul>"
				+ "<p>これにより、無効な参照を行っているファイル、MOD、またはデータパックを見つけるのに役立ちます。</p>";
	}

	@Override
	public String nombreItemNoExiste() {
		return "存在しないアイテムが参照されました";
	}

	@Override
	public String mensajeCobblemonPinkanIslandsRhyhornModelo() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Rhyhorn のモデルエラーが検出されました。</b>"
				+ "<p>ログには <b>Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn</b> という行が含まれています。</p>"
				+ "<p>モデルは <b>Cobblemon</b> の名前空間を使用していますが、この行は通常、"
				+ "その <b>Rhyhorn</b> の由来である <b>Cobblemon: Pinkan Islands</b> モッドによって引き起こされます。</p>"
				+ "<p><b>試すべきこと：</b></p>" + "<ul>" + "<li><b>Cobblemon: Pinkan Islands</b> を削除または無効にして、再試行してください。</li>"
				+ "<li><b>Cobblemon</b> と <b>Cobblemon: Pinkan Islands</b> を、互いに互換性のあるバージョンに更新してください。</li>"
				+ "<li>問題がこれらのモッドのいずれかを更新した後に始まった場合は、異なるバージョンの組み合わせを試してください。</li>" + "</ul>"
				+ "<p>この障害は通常、<b>Cobblemon: Pinkan Islands</b> 内の欠落、登録不良、または互換性のないモデルを示しています。</p>";
	}

	@Override
	public String nombreCobblemonPinkanIslandsRhyhornModelo() {
		return "Cobblemon: Pinkan Islands における Rhyhorn のモデルエラー";
	}

	@Override
	public String mensajeColdSweatInitDynamicTags() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cold Sweat でエラーが検出されました。</b>" + "<p>ログには <b>$cold_sweat$onBuildStart</b>、"
				+ "<b>InitDynamicTagsEvent.fillTag</b>、およびレジストリが null として表示される <b>NullPointerException</b> などの兆候が含まれています。</p>"
				+ "<p>これは通常、<b>Cold Sweat</b> が " + "<b>動的タグ（dynamic tags）</b>を構築または充填する際の問題を示しており、"
				+ "通常は非互換性、モッドの内部エラー、" + "または他のモッドやデータパックとの競合する組み合わせが原因です。</p>" + "<p><b>試すべきこと：</b></p>" + "<ul>"
				+ "<li><b>Cold Sweat</b> を削除または無効にして、再試行してください。</li>"
				+ "<li><b>Cold Sweat</b> を、あなたの Minecraft バージョンおよびローダー（loader）と互換性のあるバージョンに更新してください。</li>"
				+ "<li><b>タグ（tags）</b>、<b>バイオーム（biomes）</b>、<b>温度</b>、または関連するレジストリを変更するデータパックやモッドを使用している場合は、それらも確認してください。</li>"
				+ "<li>エラーがモッドの更新後に始まった場合は、異なるバージョンの組み合わせを試してください。</li>" + "</ul>"
				+ "<p>この場合、<b>Cold Sweat</b> が障害に直接関与しています。</p>";
	}

	@Override
	public String nombreColdSweatInitDynamicTags() {
		return "Cold Sweat の動的タグにおけるエラー";
	}

	@Override
	public String mensajeClassCastExceptionGeneral(String lineaClassCast) {
		String detalle = lineaClassCast == null || lineaClassCast.isEmpty() ? ""
				: "<p><b>検出された行：</b></p><p><code>" + lineaClassCast + "</code></p>";

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ClassCastException が検出されました。</b>" + "<p>これは、あるクラスが互換性のない別のクラスまたはインターフェースとして扱われたことを意味します。</p>"
				+ detalle + "<p>この種のエラーは通常、次のいずれかの状況によって引き起こされます：</p>" + "<ul>" + "<li>相互に<b>互換性のない 2 つの MOD</b>。</li>"
				+ "<li>クラスを変更し、ゲームの他の部分が異なる型を期待するようにする <b>Mixins</b>、<b>transformers</b>、またはパッチ。</li>"
				+ "<li>間違った型変換（miscast）を引き起こしている <b>stacktrace</b> 内の他の MOD。</li>" + "</ul>" + "<p><b>確認事項：</b></p>"
				+ "<ul>" + "<li>このエラーに関連する <b>stacktrace</b> の行を確認してください。</li>"
				+ "<li><b>$modid$algo</b> 形式の MOD 名またはクラス名に特に注意してください。これらは通常、関係する MOD を示しています。</li>"
				+ "<li>無効な変換に関連していると思われる MOD の更新、削除、または分離を試してください。</li>" + "</ul>"
				+ "<p><b>ClassCastException</b> は常に致命的とは限りませんが、多くの場合致命的です。</p>";
	}

	@Override
	public String nombreClassCastExceptionGeneral() {
		return "ClassCastException が検出されました";
	}

	@Override
	public String mensajeValkyrienSkiesTournamentLithiumPoiInjection() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Valkyrien Skies Tournament と Lithium/Radium の間に不互換性が検出されました。</b>"
				+ "<p>ログには <b>InvalidInjectionException</b> が含まれており、"
				+ "<b>POI</b> に関する <b>Lithium</b> の mixin が <b>valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager</b> と共に表示されています。</p>"
				+ "<p>この問題は、<b>古いバージョンの Lithium</b> または " + "<b>旧 Lithium ベースのフォーク</b>（<b>Radium Reforged</b> など）を "
				+ "<b>VS Tournament</b> と併用した場合に発生することが多いです。</p>" + "<p><b>試すべきこと：</b></p>" + "<ul>"
				+ "<li><b>Lithium</b> を新しい互換バージョンに更新してください。</li>"
				+ "<li><b>Forge/NeoForge</b> 環境で <b>Radium Reforged</b> やその他の古いフォークを使用している場合は、削除してください。</li>"
				+ "<li>代わりに <b>Harium</b> を試してください。これは Radium の現代版フォークであり、Lithium の最近の改善点と同期されています。</li>"
				+ "<li>MOD 更新後に問題が発生した場合は、<b>VS Tournament</b> と AI/POI 最適化 MOD の正確な組み合わせを確認してください。</li>" + "</ul>"
				+ "<p>実際には、この障害は通常、<b>VS Tournament</b> と相性の良くない <b>Lithium/Radium</b> の古い実装に起因します。</p>";
	}

	@Override
	public String nombreValkyrienSkiesTournamentLithiumPoiInjection() {
		return "VS Tournament と Lithium/Radium の不互換性";
	}

	@Override
	public String mensajeVSTournamentVSConfigClassNoExiste() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VS Tournament のバージョンが、お使いの Valkyrien Skies に対して古すぎるようです。</b>"
				+ "<p>ログには <b>NoClassDefFoundError</b> が含まれており、"
				+ "<b>org/valkyrienskies/core/impl/config/VSConfigClass</b> および "
				+ "<b>org.valkyrienskies.tournament.TournamentMod.init(...)</b> からの行が記録されています。</p>"
				+ "<p>これは通常、<b>古いバージョンの VS Tournament</b> を使用しており、"
				+ "もう存在しない <b>Valkyrien Skies</b> の内部クラスを呼び出そうとしていることを意味します。</p>" + "<p><b>対処方法：</b></p>" + "<ul>"
				+ "<li>古い <b>VS Tournament</b> を削除してください。</li>"
				+ "<li>代わりに <b>VS Tournament Reforged</b> を使用してください。</li>"
				+ "<li>また、<b>Valkyrien Skies</b> のバージョンがアドオンでサポートされているバージョンと一致しているか確認してください。</li>" + "</ul>"
				+ "<p><b>VS Tournament Reforged</b> への切り替えを推奨するのは、プロジェクトの現状に合致するためです。"
				+ "元の Tournament バージョンは依然として 1.18.2 用の旧版 MOD としてリストされていますが、"
				+ "<b>VS Tournament Reforged</b> は別個に公開されており、現在 Forge 1.20.1 上の Valkyrien "
				+ "2.4.9+ へのサポートをアナウンスしています。</p>";
	}

	@Override
	public String nombreVSTournamentVSConfigClassNoExiste() {
		return "旧版 VS Tournament は Valkyrien Skies と非互換";
	}

	public String curseForgeClaveApiMundial() {
		return "CurseForge グローバル API キー";
	}

	public String curseForgeEndpoint() {
		return "CurseForge エンドポイント";
	}

	public String tlmodsEndpoint() {
		return "TLMods エンドポイント";
	}

	public String minecraftStorageEndpoint() {
		return "MinecraftStorage エンドポイント";
	}

	public String autoBackupActivado() {
		return "自動バックアップ有効";
	}

	public String autoBackupFrecuencia() {
		return "自動バックアップ頻度";
	}

	public String autoBackupDiasConservar() {
		return "自動バックアップ保持日数";
	}

	public String autoBackupTamanoMaximoMB() {
		return "自動バックアップ最大サイズ (MB)";
	}

	public String actualizadorModsTitulo() {
		return "MOD アップデーター";
	}

	public String actualizadorModsBotonSidebar() {
		return "更新";
	}

	public String actualizadorModsDescripcion() {
		return "モッドパック内の MOD の利用可能な更新を検索します。すべて更新するか、個別に更新を適用できます。";
	}

	public String actualizadorModsBotonEscanear() {
		return "更新を確認";
	}

	public String actualizadorModsBotonActualizarTodo() {
		return "すべて更新";
	}

	public String actualizadorModsBotonActualizarUno() {
		return "更新";
	}

	public String actualizadorModsEstadoListo() {
		return "準備完了";
	}

	public String actualizadorModsEstadoEscaneando() {
		return "更新を検索中...";
	}

	public String actualizadorModsEstadoActualizando() {
		return "更新中...";
	}

	public String actualizadorModsEstadoSinActualizaciones() {
		return "利用可能な更新はありません。";
	}

	public String actualizadorModsEstadoEncontradas(int n) {
		return "見つかった更新: " + n;
	}

	public String actualizadorModsEstadoActualizadas(int n) {
		return "適用された更新: " + n;
	}

	public String actualizadorModsEstadoError() {
		return "更新中にエラーが発生しました。";
	}

	public String actualizadorModsSinSeleccion() {
		return "更新が選択されていません。";
	}

	public String actualizadorModsColumnaMod() {
		return "MOD";
	}

	public String actualizadorModsColumnaActual() {
		return "現在";
	}

	public String actualizadorModsColumnaNueva() {
		return "最新";
	}

	public String actualizadorModsColumnaFuente() {
		return "ソース";
	}

	public String actualizadorModsColumnaLoader() {
		return "ローダー";
	}

	public String actualizadorModsColumnaRuta() {
		return "パス";
	}

	public String actualizadorModsColumnaAccion() {
		return "アクション";
	}

	public String actualizadorModsColorFondo() {
		return "更新ツール: 背景";
	}

	public String actualizadorModsColorPanel() {
		return "更新ツール: パネル";
	}

	public String actualizadorModsColorTexto() {
		return "更新ツール: テキスト";
	}

	public String actualizadorModsColorTextoSuave() {
		return "更新ツール: 控えめなテキスト";
	}

	public String actualizadorModsColorBoton() {
		return "更新ツール: ボタン";
	}

	public String actualizadorModsColorBotonTexto() {
		return "更新ツール: ボタンテキスト";
	}

	public String actualizadorModsColorTabla() {
		return "更新ツール: テーブル";
	}

	public String actualizadorModsColorSeleccion() {
		return "更新ツール: 選択";
	}

	public String importadorYumeiriTeExtraniamos() {
		return "夢璃レユ、あなたを懐かしく思います。";
	}

	public String importadorColorFondo() {
		return "インポーター: 背景";
	}

	public String importadorColorPanel() {
		return "インポーター: パネル";
	}

	public String importadorColorTexto() {
		return "インポーター: テキスト";
	}

	public String importadorColorTextoSuave() {
		return "インポーター: 控えめなテキスト";
	}

	public String importadorColorBoton() {
		return "インポーター: ボタン";
	}

	public String importadorColorBotonTexto() {
		return "インポーター: ボタンテキスト";
	}

	public String importadorColorBorde() {
		return "インポーター: 境界線";
	}

	public String importadorConflictoTitulo() {
		return "インポートの競合";
	}

	public String importadorConflictoMensaje() {
		return "宛先にファイルが既に存在します。";
	}

	public String importadorRuta() {
		return "パス";
	}

	public String importadorArchivoExistente() {
		return "既存のファイル";
	}

	public String importadorArchivoNuevo() {
		return "インポートされたファイル";
	}

	public String importadorTamano() {
		return "サイズ";
	}

	public String importadorFecha() {
		return "最終更新日";
	}

	public String importadorInfoMod() {
		return "MOD 情報";
	}

	public String importadorModImportadoMasNuevo() {
		return "インポートされた MOD はより新しいようです。";
	}

	public String importadorModImportadoMasViejo() {
		return "インポートされた MOD はより古いようです。";
	}

	public String importadorBotonReemplazar() {
		return "置換";
	}

	public String importadorBotonSaltar() {
		return "スキップ";
	}

	public String importadorBotonRenombrar() {
		return "新規ファイルの名前変更";
	}

	public String importadorModpackTitulo() {
		return "MODパックのインポート";
	}

	public String importadorModpackBotonSidebar() {
		return "インポート";
	}

	public String importadorModpackDescripcion() {
		return "MODパックを現在のインスタンスにインポートします。.zip、.mrpack、または他のサポートされている形式のファイルをドラッグ＆ドロップするか、手動で選択できます。";
	}

	public String importadorModpackFormato() {
		return "形式";
	}

	public String importadorModpackArrastraArchivo() {
		return "ここにMODパックをドラッグするか、ファイルを選択してください";
	}

	public String importadorModpackBotonSeleccionar() {
		return "ファイルを選択";
	}

	public String importadorModpackBotonImportar() {
		return "インポート";
	}

	public String importadorModpackSeleccionarArchivo() {
		return "MODパックを選択";
	}

	public String importadorModpackEstadoListo() {
		return "準備完了";
	}

	public String importadorModpackEstadoImportando() {
		return "インポート中...";
	}

	public String importadorModpackEstadoError() {
		return "インポート中にエラーが発生しました。";
	}

	public String importadorModpackSinArchivo() {
		return "まずMODパックファイルを選択してください。";
	}

	public String importadorModpackFormatoNoSoportado() {
		return "この形式はインポートをサポートしていません。";
	}

	public String importadorModpackResultado(int copiados, int reemplazados, int saltados, int renombrados,
			int errores) {
		return "インポート完了。\nコピー済み: " + copiados + "\n置換済み: " + reemplazados + "\nスキップ済み: " + saltados + "\n名前変更済み: "
				+ renombrados + "\nエラー: " + errores;
	}

	public String importadorModpackColorFondo() {
		return "MODパックインポーター: 背景";
	}

	public String importadorModpackColorPanel() {
		return "MODパックインポーター: パネル";
	}

	public String importadorModpackColorTexto() {
		return "MODパックインポーター: テキスト";
	}

	public String importadorModpackColorTextoSuave() {
		return "MODパックインポーター: 控えめなテキスト";
	}

	public String importadorModpackColorBoton() {
		return "MODパックインポーター: ボタン";
	}

	public String importadorModpackColorBotonTexto() {
		return "MODパックインポーター: ボタンテキスト";
	}

	public String importadorModpackColorDrop() {
		return "MODパックインポーター: ドロップゾーン";
	}

	public String importadorModpackColorBorde() {
		return "MODパックインポーター: 境界線";
	}

	public String jgitTituloIzzy() {
		return "IzzyのGitセンター";
	}

	public String jgitRetratoIzzy() {
		return "Izzy";
	}

	public String jgitNoHayRetratoIzzy() {
		return "Izzyの肖像はありません";
	}

	public String jgitSeccionInstalacion() {
		return "JGit インストール";
	}

	public String jgitAbrirCarpetaInstalacion() {
		return "インストールフォルダを開く";
	}

	public String jgitAbrirPaginaDescarga() {
		return "JGit ページを開く";
	}

	public String jgitSeccionRepositorio() {
		return "ローカルリポジトリ";
	}

	public String jgitCrearRepositorioLocal() {
		return "ここにGitリポジトリを作成";
	}

	public String jgitCommitManual() {
		return "手動Commit";
	}

	public String jgitSeccionRemote() {
		return "リモート";
	}

	public String jgitForgeManual() {
		return "手動";
	}

	public String jgitForgePersonalizado() {
		return "カスタムForge";
	}

	public String jgitEstablecerRemoteManual() {
		return "手動でリモートを設定";
	}

	public String jgitCrearRemoteConAPI() {
		return "APIでリモートを作成";
	}

	public String jgitPushManual() {
		return "手動Push";
	}

	public String jgitSeccionAutomaticos() {
		return "自動化";
	}

	public String jgitAutoCommitDespuesBackup() {
		return "バックアップ後に自動Commit";
	}

	public String jgitAutoPushDespuesCommit() {
		return "Commit後に自動Push";
	}

	public String jgitSeccionHerramientas() {
		return "ツール";
	}

	public String jgitAbrirGuiSwing() {
		return "JGit Swingビューアを開く";
	}

	public String jgitEstado() {
		return "状態";
	}

	public String jgitClasspath() {
		return "クラスパス上のJGit";
	}

	public String jgitTodosLosArtefactos() {
		return "すべてのJGitアーティファクト";
	}

	public String jgitRepositorio() {
		return "リポジトリ";
	}

	public String jgitRemote() {
		return "リモート";
	}

	public String jgitCarpetaActual() {
		return "現在のフォルダ";
	}

	public String jgitNoSePudoCrearRepo() {
		return "リポジトリを作成できませんでした。";
	}

	public String jgitEscribaRemote() {
		return "リモートURLを入力してください:";
	}

	public String jgitNoSePudoGuardarRemote() {
		return "リモートを保存できませんでした。";
	}

	public String jgitApiForgeAunNoImplementada() {
		return "Forge APIはまだ実装されていません。";
	}

	public String jgitNoHayCambiosOError() {
		return "Commitする変更がないか、エラーが発生しました。";
	}

	public String jgitNoSePudoPush() {
		return "Pushできませんでした。";
	}

	public String jgitTituloVentanaSwing() {
		return "Gitビューア";
	}

	public String jgitNoHayRepositorio() {
		return "このフォルダにGitリポジトリがありません。";
	}

	public String jgitArchivosModificados() {
		return "変更されたファイル";
	}

	public String jgitArchivosNuevos() {
		return "新しいファイル";
	}

	public String jgitUltimosCommits() {
		return "最新のCommits";
	}

	public String jgitError() {
		return "JGitエラー";
	}

	public String si() {
		return "はい";
	}

	public String no() {
		return "いいえ";
	}

	public String jgitDescargarDependenciasFaltantes() {
		return "欠落している依存関係のダウンロード";
	}

	public String jgitNoFaltanDependencias() {
		return "欠落している JGit の依存関係はありません。";
	}

	public String jgitConfirmarDescargaDependencias(int cantidad) {
		return "JGit の依存関係が " + cantidad + " 個欠落しています。Maven Central からダウンロードしますか？";
	}

	public String jgitDependenciasDescargadas(int cantidad) {
		return "ダウンロードされた依存関係: " + cantidad;
	}

	public String jgitErroresDescarga() {
		return "ダウンロードエラー";
	}

	public String jgitReiniciarParaCargarClasspath() {
		return "新しい JAR ファイルをクラスパスに反映させるため、" + Statics.nombre_cd.obtener() + " を再起動してください。";
	}

	public String jgitArtefactosFaltantes() {
		return "欠落しているアーティファクト";
	}

	public String jgitArtefactosFaltantesClasspath() {
		return "クラスパス上の欠落アーティファクト";
	}

	public String jgitArtefactosFaltantesCarpeta() {
		return "インストールフォルダ内の欠落アーティファクト";
	}

	public String jgitDependenciasEnCarpeta() {
		return "フォルダにインストールされた依存関係";
	}

	public String jgitForgeNoSeleccionada() {
		return "Forge が選択されていません。";
	}

	public String jgitForgeNoRegistrada(String id) {
		return "Forge が登録されていません: " + id;
	}

	public String jgitEscribaUrlForge() {
		return "Forge URL:";
	}

	public String jgitEscribaNombreRepositorio() {
		return "リポジトリ名:";
	}

	public String jgitEscribaDescripcionRepositorio() {
		return "リポジトリの説明:";
	}

	public String jgitEscribaNamespaceOpcional() {
		return "オプションの Namespace:";
	}

	public String jgitEscribaTokenForge() {
		return "Forge API トークン:";
	}

	public String jgitErrorCrearRemote() {
		return "Remote の作成エラー";
	}

	@Override
	public String mensajeControlifyRemoveReloadingScreen() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Controlify と Remove Reloading Screen の間に非互換性が検出されました。</b>"
				+ "<p>ログには <b>Attempted to fetch default config before DefaultConfigManager was ready!</b> "
				+ "および <b>$rrls$init</b> という行が含まれており、これは通常 <b>Controlify</b> と "
				+ "<b>Remove Reloading Screen</b> の間の競合を示しています。</p>"
				+ "<p><b>考えられる原因：</b>Remove Reloading Screen は読み込み画面または読み込みプロセスの一部を変更しますが、 "
				+ "Controlify はシステムが完全に準備できる前に構成を初期化しようとします。</p>" + "<p><b>推奨されるオプション：</b></p>" + "<ul>"
				+ "<li><b>Remove Reloading Screen</b> を削除してください。</li>"
				+ "<li>新しいバージョンが利用可能な場合は、<b>Controlify</b> と <b>Remove Reloading Screen</b> を更新してください。</li>"
				+ "<li>問題が続く場合は、<b>Controlify</b> を維持し、読み込み画面を変更するすべての MOD を削除してください。</li>" + "</ul>"
				+ "<p>読み込み画面を変更する MOD は、他の MOD との非互換性を引き起こすことが多く、 " + "引き起こす可能性のある問題と比較して、実用的な利点はほとんどありません。</p>";
	}

	@Override
	public String nombreControlifyRemoveReloadingScreen() {
		return "非互換性: Controlify 対 Remove Reloading Screen";
	}

	@Override
	public String mensajeBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Biomes O' Plenty とカスタム液体の間に問題が発生している可能性があります。</b>"
				+ "<p>ログにはエラー <b>class org.joml.Vector4f cannot be cast to class "
				+ "net.minecraft.client.renderer.fog.FogData</b> および <b>Biomes O' Plenty</b> への参照が含まれています。</p>"
				+ "<p>これはおそらく <b>Biomes O' Plenty</b>、特にバイオーム、霧 "
				+ "またはカスタム液体に関連しています。ただし、Biomes O' Plenty が唯一の原因であるとは限りません。</p>" + "<p><b>推奨されるオプション：</b></p>" + "<ul>"
				+ "<li>プレイヤーデータを編集して、ワールド内の別の場所に移動させてみてください。</li>"
				+ "<li><b>Biomes O' Plenty</b> なしでワールドを読み込んでみてください。</li>" + "<li>プレイヤーを移動させた後にワールドが読み込まれる場合、問題は特定のエリア、 "
				+ "特定のバイオーム、または近くのカスタム液体で発生している可能性があります。</li>" + "<li><b>Biomes O' Plenty</b> およびレンダリング、霧、 "
				+ "シェーダー、またはディメンションに関連する MOD を更新してみてください。</li>" + "</ul>"
				+ "<p>Biomes O' Plenty を削除するとゲームが起動する場合、プレイヤーがその MOD で追加されたバイオーム "
				+ "または流体の内部または近くにいたかどうかを確認してください。</p>";
	}

	@Override
	public String nombreBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "潜在的な問題: Biomes O' Plenty と FogData";
	}

	@Override
	public String mensajeKotlinReflectionInternalErrorVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Kotlin の内部リフレクションエラーが検出されました。</b>"
				+ "<p>ログには <b>kotlin.reflect.jvm.internal.KotlinReflectionInternalError</b> が含まれており、メッセージは "
				+ "<b>Property 'none' not resolved</b> に似ています。</p>"
				+ "<p>この種のエラーは、特定のバージョンの <b>Fabric Language Kotlin</b> / <b>Kotlin</b> で一般的です。 "
				+ "この場合、<b>Inventory Profiles Next</b> のクラスが表示されていますが、同じ問題は Kotlin を使用する "
				+ "他の MOD でも発生する可能性があります。</p>" + "<p><b>推奨されるオプション：</b></p>" + "<ul>"
				+ "<li>Minecraft のバージョンで利用可能な場合、<b>Fabric Language Kotlin</b> をバージョン <b>2.3.40</b> に更新してください。</li>"
				+ "<li>更新しても解決しない場合は、<b>Fabric Language Kotlin</b> をバージョン <b>2.3.10</b> にダウングレードしてみてください。</li>"
				+ "<li>ログにその MOD のクラス言及がある場合は、<b>Inventory Profiles Next</b> も更新してください。</li>"
				+ "<li>エラーが他の MOD で発生する場合は、その MOD が Kotlin に依存しているか確認し、 "
				+ "<b>Fabric Language Kotlin</b> のバージョンを変更してみてください。</li>" + "</ul>" + "<p>関連する技術参照： "
				+ "<a href='https://github.com/FabricMC/fabric-language-kotlin/issues/183'>Fabric Language Kotlin の問題 #183</a>。</p>";
	}

	@Override
	public String nombreKotlinReflectionInternalErrorVersion() {
		return "Kotlin エラー：内部リフレクション";
	}

	public String tituloEscanerMCreator() {
		return "MCreator スキャナー";
	}

	public String escanerMCreatorEscaneandoMods() {
		return "MOD をスキャン中...";
	}

	public String escanerMCreatorPorFavorEspera() {
		return "お待ちください。";
	}

	public String escanerMCreatorResultadosAnalisis() {
		return "MCreator 分析結果:";
	}

	public String escanerMCreatorNoSeEncontraronMods() {
		return "MCreator MOD が見つかりません。";
	}

	public String escanerMCreatorEscaneoCompletado() {
		return "スキャン完了。";
	}

	public String escanerMCreatorErrorDuranteEscaneo() {
		return "スキャン中のエラー:";
	}

	public String escanerMCreatorCargando() {
		return "読み込み中...";
	}

	public String escanerMCreatorCompletado() {
		return "完了";
	}

	public String escanerMCreatorError() {
		return "エラー";
	}

	// Japanese (日本語)
	@Override
	public String textoNormal() {
		return "通常のテキスト";
	}

	@Override
	public String noSeEncontroConsolaParaArchivo() {
		return "ファイルのコンソールが見つかりません：";
	}

	@Override
	public String lineaSeleccionadaEnLectador() {
		return "リーダーで選択された行：";
	}

	// Japanese (日本語)
	@Override
	public String mensajeMotionBlurBufferCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Motion Blur に問題がある可能性があります。</b>"
				+ "<p>ログには <b>net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO</b> への参照と、"
				+ "エラー <b>java.lang.IllegalStateException: Buffer already closed</b> が含まれています。</p>"
				+ "<p>これらの行はログで別々に表示される場合がありますが、一緒に表示される場合は通常、問題が "
				+ "<b>Motion Blur</b> mod またはそのグラフィック shader/buffer の処理に関連していることを示しています。</p>" + "<p><b>推奨オプション：</b></p>"
				+ "<ul>" + "<li><b>Motion Blur</b> なしでゲームを開始してみてください。</li>"
				+ "<li>その mod なしでゲームが正常に開始する場合は、アンインストールしたままにするか、最新バージョンを探してください。</li>"
				+ "<li>問題が続く場合は、shaders や他のレンダリング mod なしで試すこともできます。</li>" + "</ul>";
	}

	@Override
	public String nombreMotionBlurBufferCerrado() {
		return "潜在的な問題: Motion Blur";
	}

	// Japanese (日本語)
	@Override
	public String mensajeGeneratorAcceleratorOwoVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Generator Accelerator との潜在的な衝突。</b>" + "<p>ログには <b>Found</b> と <b>Available</b> の署名間の不一致と、"
				+ "<b>Generator Accelerator</b> のクラス（例：<b>dev/sixik/generator_accelerator/common/features/FastTarget</b>）が含まれています。</p>"
				+ "<p>このエラーはおそらく <b>Generator Accelerator</b> が原因です。また、その mod と特定のバージョンの <b>owo-lib</b> の間の非互換性に関連している可能性もあります。</p>"
				+ "<p><b>推奨オプション：</b></p>" + "<ul>" + "<li><b>Generator Accelerator</b> なしでゲームを開始してみてください。</li>"
				+ "<li>ゲームが正常に開始する場合は、その mod をアンインストールしたままにするか、別のバージョンを探してください。</li>"
				+ "<li>他の mod も owo に依存している場合は、<b>owo-lib</b> のバージョンを更新または変更してみてください。</li>"
				+ "<li><b>Generator Accelerator</b>、<b>owo-lib</b>、loader、および Minecraft のバージョンが互いにcompatibleであることを確認してください。</li>"
				+ "</ul>" + "<p>最も可能性の高い原因は、Generator Accelerator が現在のクラスまたは依存関係のバージョンと一致しない署名で修正を適用しようとしていることです。</p>";
	}

	@Override
	public String nombreGeneratorAcceleratorOwoVersion() {
		return "潜在的な衝突: Generator Accelerator と owo-lib";
	}

	// Japanese (日本語)
	@Override
	public String mensajeFabricRenderingApiFaltaIndium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Fabric Rendering API と互換性のあるレンダラーがありません。</b>"
				+ "<p>ログには、<b>RendererAccess.getRenderer()</b> が <b>null</b> を返し、 "
				+ "<b>Renderer.materialFinder()</b> の使用を試みた際に失敗するエラーが含まれています。</p>"
				+ "<p>この問題は通常、<b>Fabric Rendering API</b> が正しく利用できない場合に発生します。 "
				+ "一般的な原因は <b>Sodium</b> の使用であり、特に他の mods が期待するレンダリングシステムの一部を置き換えたり無効にしたりする旧バージョンです。</p>"
				+ "<p><b>推奨される解決策：</b></p>" + "<ul>" + "<li>mod <b>Indium</b> をインストールしてください。</li>"
				+ "<li><b>Indium</b> が使用中の <b>Sodium</b>、Fabric Loader、および Minecraft のバージョンと互換性があることを確認してください。</li>"
				+ "<li>すでに Indium がインストールされている場合は、<b>Sodium</b>、<b>Indium</b>、および <b>Fabric API</b> を更新してください。</li>"
				+ "<li>問題が続く場合は、一時的に Sodium なしで試して、障害がレンダラーに関連していることを確認してください。</li>" + "</ul>"
				+ "<p>Indium は通常、Sodium がインストールされている間、そのシステムに依存する mods のために Fabric Rendering API との互換性を復元します。</p>";
	}

	@Override
	public String nombreFabricRenderingApiFaltaIndium() {
		return "Indium 不足 / Fabric Rendering API";
	}

	// Japanese (日本語)
	@Override
	public String mensajeEntradaDuplicadaIdModerno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft のレジストリで重複エントリが検出されました。</b>" + "<p>ログには <b>Duplicate entry on id</b> に似たエラーが含まれています。例えば "
				+ "<b>current=maroon, previous=mint</b> です。</p>"
				+ "<p>最新の Minecraft バージョンでは、この種のエラーは通常、2つの mod が同じ内部 ID を使用して " + "異なるエントリを登録しようとした場合に発生します。</p>"
				+ "<p><b>推奨オプション：</b></p>" + "<ul>" + "<li>重複エントリを登録している mod のいずれかを削除してください。</li>"
				+ "<li>エラーで言及されている名前を認識している場合は、どの mod がそれらのエントリを追加しているかを確認し、その mod なしで試してください。</li>"
				+ "<li>名前を認識していない場合は、サイドバーの <b>grepr</b> ユーティリティを使用してください。</li>"
				+ "<li><b>grepr</b> で、圧縮ファイル <b>.jar</b>、<b>.zip</b>、および <b>.fpm</b> 内の検索を有効にしてください。</li>"
				+ "<li>一部の名前や ID はコンパイル済みクラス内にある可能性があるため、<b>バイナリファイル</b> の検索も有効にしてください。</li>" + "</ul>"
				+ "<li>エラーで言及されている <b>maroon</b> や <b>mint</b> などの値を検索して、それらを含む mod を見つけてください。</li>";
	}

	@Override
	public String nombreEntradaDuplicadaIdModerno() {
		return "Mod ID の重複エントリ";
	}

	// Japanese (日本語)
	@Override
	public String nombreOpenGLMemoriaInsuficiente() {
		return "OpenGL – ビデオメモリ不足";
	}

	@Override
	public String mensajeOpenGLMemoriaInsuficiente() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft がグラフィックメモリ不足により OpenGL エラーを生成しました。</b>" + "<p>ゲームがスローしたエラー:</p>"
				+ "<code>GL_OUT_OF_MEMORY</code>"
				+ "<p>これは通常、グラフィックカードまたはシステムがテクスチャ、シェーダー、モデル、バッファ、または視覚効果のために十分なメモリを割り当てられなかったことを意味します。</p>"
				+ "<p><b>一般的な原因:</b></p>" + "<ul>" + "<li>重すぎるシェーダー。</li>" + "<li>高解像度のリソースパック。</li>"
				+ "<li>多すぎる視覚的またはレンダリング関連の mod。</li>" + "<li>レンダリング距離が高すぎる。</li>" + "<li>利用可能な VRAM が少ない。</li>"
				+ "<li>古いか不安定なビデオドライバ。</li>" + "</ul>" + "<p><b>推奨される解決策:</b></p>" + "<ul>"
				+ "<li>シェーダーを一時的に無効にする。</li>" + "<li>低解像度のリソースパックを使用する。</li>" + "<li>レンダリング距離とシミュレーション距離を下げる。</li>"
				+ "<li>グラフィック品質、影、パーティクル、ミップマップを減らす。</li>" + "<li>グラフィックカードのドライバを更新する。</li>"
				+ "<li>GPU や大量のメモリを使用している他のプログラムを閉じる。</li>" + "</ul>"
				+ "<p>シェーダー、テクスチャパック、または視覚的な mod をインストールした後にエラーが始まった場合、それが原因である可能性が高いです。</p>";
	}

	// Japanese (日本語)
	@Override
	public String nombreErrorVerificacionBytecode() {
		return "VerifyError – 無効な bytecode または mixin";
	}

	@Override
	public String mensajeErrorVerificacionBytecode(String ubicacion, String razon, String claseSospechosa) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft が bytecode 検証エラーを検出しました。</b>"
				+ "<p>この問題は通常、bytecode の操作、変換器 (transformer)、または mixin が失敗したときに発生します。</p>" + "<p>ゲームがスローしました:</p>"
				+ "<code>java.lang.VerifyError</code>"
				+ (ubicacion.length() > 0 ? "<p><b>場所:</b></p><code>" + ubicacion + "</code>" : "")
				+ (razon.length() > 0 ? "<p><b>理由:</b></p><code>" + razon + "</code>" : "") + "<p><b>確認すべき事項:</b></p>"
				+ "<ul>" + "<li>エラーの場所を確認してください。</li>" + "<li><code>Reason</code> で言及されている型を探してください。</li>"
				+ "<li>スタックトレースを確認し、疑わしい mod クラスを探してください。</li>" + "<li>ヒントを得るために、エラー周辺の mod クラス名を検索してください。</li>"
				+ "</ul>" + "<p><b>Grepr の推奨使用方法:</b></p>" + "<ul>" + "<li>サイドバーで <b>Grepr</b> を開きます。</li>"
				+ "<li><code>.jar</code>、<code>.zip</code>、または <code>.fpm</code> ファイル内を検索するオプションを有効にします。</li>"
				+ "<li>完全なパッケージ名ではなく、クラスの基本名を検索してください。</li>" + "</ul>"
				+ "<p>例: <code>paquete.Clase</code> が表示される場合は、次のみ検索してください:</p>" + "<code>"
				+ (claseSospechosa.length() > 0 ? claseSospechosa : "Clase") + "</code>"
				+ "<p>これにより、そのクラスを含むか変更する mod を見つけるのに役立ちます。</p>"
				+ "<p>一般的な解決策: 影響を受けた mod を更新する、互換性のない mod を削除する、主要な mod のアドオンを確認する、または同じクラスに mixins/transformers を使用する mod なしで試す。</p>";
	}

	// Japanese (日本語)
	@Override
	public String errorMetodoFinalSobrescrito(String claseQueSobrescribe, String metodoFinal) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "互換性エラー: mod が final メソッドをオーバーライドしようとしています。</b>"
				+ "<p>ログにはテキスト <b>overrides final method</b> を伴う <b>IncompatibleClassChangeError</b> が含まれています。</p>"
				+ "<p>影響を受けたクラス: <code>" + claseQueSobrescribe + "</code></p>" + "<p>影響を受けた final メソッド: <code>"
				+ metodoFinal + "</code></p>"
				+ "<p>このエラーは通常、mod が異なるバージョンの Minecraft、Forge、NeoForge、Fabric、Quilt、または基本ライブラリ用にコンパイルされた場合に発生します。</p>"
				+ "<p><b>試すべきこと:</b></p>" + "<ul>" + "<li>指定されたクラスを含む mod を更新してください。</li>"
				+ "<li>Minecraft またはローダーの更新後に問題が発生した場合は、mod の互換バージョンを試してください。</li>"
				+ "<li>クラスが <b>Immersive Portals</b> に属している場合は、<b>Immersive Portals</b> が使用している Minecraft およびローダーのバージョンと正確に一致しているか確認してください。</li>"
				+ "<li>ローダーまたは Minecraft の異なるバージョン用に作成された mod を混在させないでください。</li>" + "</ul>";
	}

	@Override
	public String nombreErrorMetodoFinalSobrescrito() {
		return "mod が final メソッドをオーバーライドしようとしています";
	}

	// Japanese (日本語)
	@Override
	public String errorCrashProvocadoPorComando(String comandoDetectado) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft がクラッシュコマンドによって終了しました。</b>" + "<p>ログには、コマンド <code>" + comandoDetectado
				+ "</code> が実行されたことが示されています。</p>" + "<p>これは通常、ゲームが通常の mod エラーで終了したのではなく、誰かが "
				+ "手動でクラッシュを引き起こすように設計されたコマンドを使用したことを意味します。</p>" + "<p><b>確認すべき事項:</b></p>" + "<ul>"
				+ "<li>コンソールまたはゲーム内で誰がコマンドを実行したかを確認してください。</li>" + "<li>テストだった場合は、このクラッシュを無視できます。</li>"
				+ "<li>意図せずに発生した場合は、コマンドブロック、スクリプト、データパック、管理用 mod、またはオペレーター権限を確認してください。</li>" + "</ul>";
	}

	@Override
	public String nombreCrashProvocadoPorComando() {
		return "コマンドによるクラッシュ";
	}

	// Japanese (日本語)
	public String impactoAlto() {
		return "高";
	}

	public String impactoMedio() {
		return "中";
	}

	public String impactoBajo() {
		return "低";
	}

	public String impactoBajoMedio() {
		return "低/中";
	}

	public String riesgoAlto() {
		return "高";
	}

	public String riesgoMedio() {
		return "中";
	}

	public String riesgoBajo() {
		return "低";
	}

	public String riesgoMedioAlto() {
		return "中/高";
	}

	public String tituloCrearConfigBBE() {
		return "Better Block Entities 設定の作成";
	}

	public String descripcionCrearConfigBBE() {
		return "BBEConfig.json ファイルが存在しません。";
	}

	public String sugerenciaCrearConfigBBE() {
		return "チェスト、シュルカー、看板、ベッド、鐘、旗の最適化を含む BBEConfig.json を作成します。";
	}

	public String tituloActivarOptimizacionMaestraBBE() {
		return "BBE マスター最適化の有効化";
	}

	public String descripcionActivarOptimizacionMaestraBBE() {
		return "Better Block Entities にメインの最適化が有効になっていないようです。";
	}

	public String sugerenciaActivarOptimizacionMaestraBBE() {
		return "追加: {\"option\":\"optimize.master\",\"value\":true}";
	}

	public String tituloActivarCullingTextoCartelesBBE() {
		return "看板テキストのコーリング (Culling) を有効化";
	}

	public String descripcionActivarCullingTextoCartelesBBE() {
		return "遠くの看板テキストのレンダリングを削減します。";
	}

	public String sugerenciaActivarCullingTextoCartelesBBE() {
		return "追加: {\"option\":\"misc.sign_text_culling\",\"value\":true}";
	}

	public String tituloAumentarSleepDelayEntityCulling() {
		return "EntityCulling の sleepDelay を増加";
	}

	public String descripcionAumentarSleepDelayEntityCulling() {
		return "EntityCulling がエンティティをチェックする頻度を下げます。";
	}

	public String sugerenciaAumentarSleepDelayEntityCulling() {
		return "\"sleepDelay\": 153";
	}

	public String tituloSubirLimiteHitboxEntityCulling() {
		return "Hitbox 制限の引き上げ";
	}

	public String descripcionSubirLimiteHitboxEntityCulling() {
		return "低速パスにフォールバックする前に、より積極的なコーリング動作を許可します。";
	}

	public String sugerenciaSubirLimiteHitboxEntityCulling() {
		return "\"hitboxLimit\": 90";
	}

	public String tituloDesactivarDatosF3EntityCulling() {
		return "EntityCulling の F3 データを無効化";
	}

	public String descripcionDesactivarDatosF3EntityCulling() {
		return "mod の追加デバッグ情報を削除します。";
	}

	public String sugerenciaDesactivarDatosF3EntityCulling() {
		return "\"disableF3\": true";
	}

	public String tituloActivarBufferingSignsImmediatelyFast() {
		return "看板の実験的バッファリングを有効化";
	}

	public String descripcionActivarBufferingSignsImmediatelyFast() {
		return "看板が多い場合にパフォーマンスを向上させる可能性があります。";
	}

	public String sugerenciaActivarBufferingSignsImmediatelyFast() {
		return "\"experimental_sign_text_buffering\": true";
	}

	public String tituloReducirConflictosResourcePacksImmediatelyFast() {
		return "リソースパックの競合処理を削減";
	}

	public String descripcionReducirConflictosResourcePacksImmediatelyFast() {
		return "追加の処理を減らせますが、リソースパックで視覚的な問題を引き起こす可能性もあります。";
	}

	public String sugerenciaReducirConflictosResourcePacksImmediatelyFast() {
		return "\"experimental_disable_resource_pack_conflict_handling\": true";
	}

	public String tituloOcultarBotonNCR() {
		return "No Chat Reports ボタンを非表示";
	}

	public String descripcionOcultarBotonNCR() {
		return "UI の変更です。FPS は大きく向上しませんが、画面をすっきりさせます。";
	}

	public String sugerenciaOcultarBotonNCR() {
		return "\"showNCRButton\": false";
	}

	public String tituloActivarMixinsExperimentalesLithium() {
		return "Lithium の実験的 mixins を有効化";
	}

	public String descripcionActivarMixinsExperimentalesLithium() {
		return "追加の実験的最適化を有効にします。";
	}

	public String sugerenciaActivarMixinsExperimentalesLithium() {
		return "mixin.experimental=true";
	}

	public String tituloUsarDetectorThreadingPequenoFerriteCore() {
		return "小型スレッド検出器を使用";
	}

	public String descripcionUsarDetectorThreadingPequenoFerriteCore() {
		return "メモリを削減しますが、リスクが高まる可能性があります。";
	}

	public String sugerenciaUsarDetectorThreadingPequenoFerriteCore() {
		return "useSmallThreadingDetector=true";
	}

	public String tituloModernFixRecursosDinamicos() {
		return "ModernFix の動的リソースを有効化";
	}

	public String descripcionModernFixRecursosDinamicos() {
		return "リソースをより効率的に読み込むことで、メモリ使用量とワークロードを削減する可能性があります。";
	}

	public String tituloModernFixRenderizadoresDinamicosEntidades() {
		return "エンティティ動的レンダラーを有効化";
	}

	public String descripcionModernFixRenderizadoresDinamicosEntidades() {
		return "エンティティレンダラーをより効率的に処理することで、パフォーマンスを向上させる可能性があります。";
	}

	public String tituloModernFixRenderizadoItemsRapido() {
		return "アイテム高速レンダリングを有効化";
	}

	public String descripcionModernFixRenderizadoItemsRapido() {
		return "アイテムのレンダリング時にパフォーマンスを向上させる可能性があります。";
	}

	public String tituloModernFixWorldgenAllocation() {
		return "ワールド生成時の割り当てを削減";
	}

	public String descripcionModernFixWorldgenAllocation() {
		return "ワールド生成中のメモリゴミを削減する可能性があります。";
	}

	public String tituloModernFixDeduplicacionIngredientes() {
		return "材料の重複排除を有効化";
	}

	public String descripcionModernFixDeduplicacionIngredientes() {
		return "レシピと材料に関連する重複オブジェクトを削減します。";
	}

	public String tituloSodiumRenderCielo() {
		return "Sodium で空の最適化/レンダリングを有効化";
	}

	public String descripcionSodiumRenderCielo() {
		return "空のレンダリング動作を調整する可能性があります。";
	}

	public String tituloActivarLightmapCaching() {
		return "ライトマップキャッシュを有効化";
	}

	public String descripcionActivarLightmapCaching() {
		return "不要な場合に照明の再計算を回避します。";
	}

	public String sugerenciaActivarLightmapCaching() {
		return "enable_lightmap_caching: true";
	}

	public String tituloOcultarTextoF3BadOptimizations() {
		return "BadOptimizations の F3 テキストを非表示";
	}

	public String descripcionOcultarTextoF3BadOptimizations() {
		return "F3 画面のデバッグノイズを削減します。";
	}

	public String sugerenciaOcultarTextoF3BadOptimizations() {
		return "show_f3_text: false";
	}

	public String tituloDesactivarLogConfigBadOptimizations() {
		return "構成ログを無効化";
	}

	public String descripcionDesactivarLogConfigBadOptimizations() {
		return "起動時にすべての構成が出力されるのを防ぎます。";
	}

	public String sugerenciaDesactivarLogConfigBadOptimizations() {
		return "log_config: false";
	}

	public String tituloActivarSerializadorGCFreeC2ME() {
		return "C2ME の GC フリーシリアライザを有効化";
	}

	public String descripcionActivarSerializadorGCFreeC2ME() {
		return "チャンクの読み込みまたは保存時のメモリ割り当てを削減します。";
	}

	public String sugerenciaActivarSerializadorGCFreeC2ME() {
		return "[ioSystem] gcFreeChunkSerializer = true";
	}

	public String tituloDesactivarSyncPlayerTicketsC2ME() {
		return "syncPlayerTickets を無効化";
	}

	public String descripcionDesactivarSyncPlayerTicketsC2ME() {
		return "チャンクのパフォーマンスを向上させる可能性がありますが、技術的な構造物に影響を与える場合があります。";
	}

	public String sugerenciaDesactivarSyncPlayerTicketsC2ME() {
		return "[chunkSystem] syncPlayerTickets = false";
	}

	public String tituloUsarCullingHojasDepthMoreCulling() {
		return "DEPTH 葉コーリングを使用";
	}

	public String descripcionUsarCullingHojasDepthMoreCulling() {
		return "通常モードよりも積極的な葉のコーリングモードを使用します。";
	}

	public String sugerenciaUsarCullingHojasDepthMoreCulling() {
		return "leavesCullingMode = \"DEPTH\"";
	}

	public String tituloActivarEndGatewayCullingMoreCulling() {
		return "エンドゲートウェイコーリングを有効化";
	}

	public String descripcionActivarEndGatewayCullingMoreCulling() {
		return "エンドゲートウェイの不要なレンダリングを防ぎます。";
	}

	public String sugerenciaActivarEndGatewayCullingMoreCulling() {
		return "endGatewayCulling = true";
	}

	public String tituloActivarActivationRangeServerCore() {
		return "activation range を有効化";
	}

	public String descripcionActivarActivationRangeServerCore() {
		return "プレイヤーから遠いエンティティのティックを削減します。";
	}

	public String sugerenciaActivarActivationRangeServerCore() {
		return "activation-range: enabled: true";
	}

	public String tituloActivarRangoVerticalServerCore() {
		return "垂直範囲を有効化";
	}

	public String descripcionActivarRangoVerticalServerCore() {
		return "プレイヤーの非常に上または下にあるエンティティのティックを削減します。";
	}

	public String sugerenciaActivarRangoVerticalServerCore() {
		return "use-vertical-range: true";
	}

	// Japanese (日本語)
	public String impactoNegativoAlto() {
		return "高い負の影響";
	}

	public String advertenciaModsCulling() {
		return "コーリング（culling）系 mod は他の mod との非互換性、クラッシュ、ゲームの tick が正常に行われなくなるエラーを引き起こす可能性があり、自動農場や工場が動作しなくなることもあります。";
	}

	public String tituloModBadOptimizations() {
		return "BadOptimizations の追加";
	}

	public String descripcionModBadOptimizations() {
		return "ライトマップキャッシュ、空のキャッシュ、不要な呼び出しの削減などのクライアント側マイクロ最適化を追加します。";
	}

	public String tituloModBBE() {
		return "Better Block Entities の追加";
	}

	public String descripcionModBBE() {
		return "チェスト、シュルカー、ベッド、鐘、旗、看板などのブロックエンティティのレンダリングを最適化します。";
	}

	public String tituloModC2ME() {
		return "Concurrent Chunk Management Engine の追加";
	}

	public String descripcionModC2ME() {
		return "並列処理を使用してチャンクの読み込み、生成、管理を改善します。非常に強力ですが、大規模な modpack では非互換性を引き起こす可能性があります。";
	}

	public String tituloModEntityCulling() {
		return "EntityCulling の追加";
	}

	public String descripcionModEntityCulling() {
		return "表示されていないエンティティのレンダリングを防ぎます。" + advertenciaModsCulling();
	}

	public String tituloModFerriteCore() {
		return "FerriteCore の追加";
	}

	public String descripcionModFerriteCore() {
		return "重複排除とより効率的な内部構造により、メモリ使用量を削減します。";
	}

	public String tituloModImmediatelyFast() {
		return "ImmediatelyFast の追加";
	}

	public String descripcionModImmediatelyFast() {
		return "即時レンダリング、テキスト、バッファ、マップ、UI のさまざまな部分を最適化します。";
	}

	public String tituloModLithium() {
		return "Lithium の追加";
	}

	public String descripcionModLithium() {
		return "バニラの動作を大幅に変更することなく、ゲームロジック、エンティティ、ブロック、物理演算、その他のシステムを最適化します。";
	}

	public String tituloModModernFix() {
		return "ModernFix の追加";
	}

	public String descripcionModModernFix() {
		return "メモリ、読み込み、リソース、全般的なパフォーマンスに関する多数の最適化を追加します。アトラス関連のツールは、アトラスを小さくする mod と競合する可能性があります。";
	}

	public String tituloModMoreCulling() {
		return "More Culling の追加";
	}

	public String descripcionModMoreCulling() {
		return "ブロック、葉、額縁、絵画、雨、ビーコンなどの要素にコーリングを追加します。" + advertenciaModsCulling();
	}

	public String tituloModScalableLux() {
		return "ScalableLux の追加";
	}

	public String descripcionModScalableLux() {
		return "照明関連の計算を最適化し、光の変化が多いワールドでパフォーマンスを向上させる可能性があります。";
	}

	public String tituloModServerCore() {
		return "ServerCore の追加";
	}

	public String descripcionModServerCore() {
		return "サーバー側最適化、アクティベーション範囲、モブキャップ制御、tick 削減、読み込み改善を追加します。";
	}

	public String tituloModSodium() {
		return "Sodium の追加";
	}

	public String descripcionModSodium() {
		return "主要なレンダリング最適化 mod。通常、FPS 向上において最も重要な mod の一つです。";
	}

	public String tituloModVMP() {
		return "Very Many Players の追加";
	}

	public String descripcionModVMP() {
		return "多くのプレイヤーを処理できるようにサーバーシステムを最適化します。想定される mod ID は vmp です。";
	}

	public String tituloModMCMT() {
		return "MCMT の追加";
	}

	public String descripcionModMCMT() {
		return "Minecraft サーバーの一部をマルチスレッド化しようとします。場合によってはパフォーマンスを向上させることができますが、非互換性、tick エラー、異常な動作のリスクが高くなります。";
	}

	public String tituloLiabilityUranium() {
		return "Uranium の削除";
	}

	public String descripcionLiabilityUranium() {
		return "Uranium は意図的にゲームをラグらせるように設計された mod です。パフォーマンスを重視する場合はインストールしないでください。";
	}

	// Japanese (日本語)
	public String tituloAmbientalSinXmx() {
		return "Java の最大メモリを設定";
	}

	public String descripcionAmbientalSinXmx(int mods, String minimo, String maximoSeguro) {
		return "提供された引数に -Xmx が検出されませんでした。" + mods + " 個の mod の場合、推奨される最小値は " + minimo + " で、約 " + maximoSeguro
				+ " を超えないようにしてください。";
	}

	public String sugerenciaAmbientalSinXmx(String minimo) {
		return "-Xmx" + minimo.replace(" ", "") + " を追加";
	}

	public String tituloAmbientalDemasiadaMemoria() {
		return "割り当てられたメモリを減らす";
	}

	public String descripcionAmbientalDemasiadaMemoria(String xmx, String total, String maximoSeguro) {
		return "インスタンスには " + total + " 中 " + xmx + " が割り当てられています。利用可能な RAM の 80% 以上を割り当てることは推奨されません。";
	}

	public String sugerenciaAmbientalDemasiadaMemoria(String maximoSeguro) {
		return "-Xmx を " + maximoSeguro + " 以下に減らしてください。";
	}

	public String tituloAmbientalMemoriaInsuficiente() {
		return "割り当てられたメモリを増やす";
	}

	public String descripcionAmbientalMemoriaInsuficiente(int mods, String xmx, String minimo) {
		return "インスタンスには " + xmx + " が割り当てられています。" + mods + " 個の mod の場合、推奨される最小値は " + minimo + " です。";
	}

	public String sugerenciaAmbientalMemoriaInsuficiente(String minimo) {
		return "-Xmx を少なくとも " + minimo + " に増やしてください。";
	}

	public String tituloAmbientalJava8GC() {
		return "Java 8 で G1GC または Shenandoah を使用";
	}

	public String descripcionAmbientalJava8GC() {
		return "Java 8 では、一時停止を減らし安定性を向上させるために G1GC または Shenandoah を使用することが推奨されます。";
	}

	public String sugerenciaAmbientalJava8GC() {
		return "-XX:+UseG1GC または -XX:+UseShenandoahGC を追加";
	}

	public String tituloAmbientalZGC() {
		return "ZGC を使用";
	}

	public String descripcionAmbientalZGC(String ramTotal) {
		return "マシンには 12 GB 以上の RAM（" + ramTotal
				+ "）があります。Java ディストリビューションがサポートしている場合、ZGC はガベージコレクションの一時停止を減らすことができます。";
	}

	public String sugerenciaAmbientalZGC() {
		return "Java 17 以降では、-XX:+UseZGC を試してください。";
	}

	public String tituloAmbientalAikar() {
		return "Aikar のフラグを追加";
	}

	public String descripcionAmbientalAikar() {
		return "Java 17 以前では、Aikar のフラグは通常 Minecraft の G1GC の動作を改善します。";
	}

	public String sugerenciaAmbientalAikar() {
		return "-XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200 を含む Aikar のフラグを使用";
	}

	public String tituloAmbientalRedHatJDK() {
		return "Red Hat JDK を使用";
	}

	public String descripcionAmbientalRedHatJDK(int javaMayor, String os) {
		return os + " の Java " + javaMayor + " では、安定性と互換性のため Red Hat JDK が推奨されます。";
	}

	public String sugerenciaAmbientalRedHatJDK() {
		return "Java 8 または Java 11 用 Red Hat JDK をインストール";
	}

	public String tituloAmbientalAzulPrime() {
		return "Azul Prime を検討";
	}

	public String descripcionAmbientalAzulPrime() {
		return "Linux で Java 16 以降および 16 GB 以上の RAM を使用する場合、Azul Prime は良いパフォーマンスの選択肢となり得ます。";
	}

	public String sugerenciaAmbientalAzulPrime() {
		return "マシンに 16 GB 以上の RAM がある場合は Azul Prime を試してください。";
	}

	public String tituloAmbientalGraalVM() {
		return "GraalVM を検討";
	}

	public String descripcionAmbientalGraalVM() {
		return "Java 16 以降および 16 GB 以上の RAM を使用する場合、GraalVM は Linux 以外で有用な代替案となり得ます。";
	}

	public String sugerenciaAmbientalGraalVM() {
		return "マシンに 16 GB 以上の RAM がある場合は GraalVM を試してください。";
	}

	public String tituloAmbientalDiscoBajo() {
		return "ディスクの空き容量を確認";
	}

	public String descripcionAmbientalDiscoBajo(String libre) {
		return "ディスクの空き容量が不足しています: " + libre + "。スペースが不足すると、Minecraft が失敗したり、保存が遅くなったり、データが破損したりする可能性があります。";
	}

	public String sugerenciaAmbientalDiscoBajo() {
		return "少なくとも 20 GB の空き容量を確保してください。";
	}

	public String tituloAmbientalWindowsRHEL9() {
		return "テスト用に RHEL 9 を検討";
	}

	public String descripcionAmbientalWindowsRHEL9() {
		return "Windows では、Red Hat JDK を含み、安定しており、個人で無料でダウンロードでき、ほとんどのテストが行われる場所であるため、RHEL 9 を検討することが推奨されます。";
	}

	public String sugerenciaAmbientalWindowsRHEL9() {
		return "最大のテスト安定性を求める場合は、RHEL 9 でインスタンスを試してください。";
	}

	public String tituloAmbientalRaptorLake() {
		return "Intel Raptor Lake 警告";
	}

	public String descripcionAmbientalRaptorLake() {
		return "既存のチェックでマークされた Raptor Lake の問題が検出されました。これは不安定さ、クラッシュ、および modpack が原因のように見えるエラーを引き起こす可能性があります。";
	}

	public String sugerenciaAmbientalRaptorLake() {
		return "modpack を責める前に、BIOS/マイクロコードを更新し、Raptor Lake の警告を確認してください。";
	}

	// Japanese (日本語)
	@Override
	public String tituloAmbientalNeoForge1201Antiguo() {
		return "古い NeoForge 1.20.1 が検出されました";
	}

	@Override
	public String descripcionAmbientalNeoForge1201Antiguo() {
		return "FancyModLoader 47 または NeoForge 1.20.1 と互換性のあるパスが検出されました。"
				+ "NeoForge 1.20.1 は MinecraftForge 1.20.1 のフォークであり、通常はバイナリレベルで互換性がありますが、"
				+ "そのラインは早期に放棄されており、Forge で利用可能な多くの最適化が欠けている可能性があります。";
	}

	@Override
	public String sugerenciaAmbientalNeoForge1201Antiguo() {
		return "1.20.1 の場合、modpack が許可するなら、NeoForge 1.20.1 の代わりに MinecraftForge 1.20.1 の使用を検討してください。";
	}

	@Override
	public String tituloAmbientalGPU() {
		return "GPU の問題が検出されました";
	}

	@Override
	public String descripcionAmbientalGPU() {
		return "別のチェックですでに潜在的な GPU、OpenGL、またはグラフィックスカードの選択問題が検出されています。";
	}

	@Override
	public String sugerenciaAmbientalGPU() {
		return "Minecraft が正しい GPU を使用しているか確認し、ドライバを更新し、不安定なハイブリッド構成を避けてください。";
	}

	// Japanese (日本語)
	@Override
	public String gpuFixTitulo() {
		return "GPU 設定";
	}

	@Override
	public String gpuFixBotonSidebar() {
		return "GPU";
	}

	@Override
	public String gpuFixBotonAplicar() {
		return "設定を適用";
	}

	@Override
	public String gpuFixBotonFuenteTLauncher() {
		return "TLauncher ガイドを開く";
	}

	@Override
	public String gpuFixBotonVirusTotal() {
		return "VirusTotal 分析を開く";
	}

	@Override
	public String gpuFixBotonOptimusLinux() {
		return "NVIDIA Optimus ガイドを開く";
	}

	@Override
	public String gpuFixTextoWindows() {
		return Statics.nombre_cd.obtener() + " は、Minecraft が高性能 GPU を使用していない可能性があることを検出しました。\n\n" + "Windows では、"
				+ "HKEY_CURRENT_USER\\\\Software\\\\Microsoft\\\\DirectX\\\\UserGpuPreferences "
				+ "にレジストリキーを設定して、javaw.exe に専用 GPU を使用させることができます。\n\n" + "GpuPreference=0 = Windows の自動決定。\n"
				+ "GpuPreference=1 = 省電力 / 統合 GPU。\n" + "GpuPreference=2 = 高性能 GPU。\n\n"
				+ "この情報の一部は、TLauncher によって公開された研究および VirusTotal で利用可能な動作分析から得られました。";
	}

	@Override
	public String gpuFixTextoLinux() {
		return Statics.nombre_cd.obtener() + " は、NVIDIA Optimus または PRIME に関連する潜在的な問題を検出しました。\n\n"
				+ "使用している Linux ディストリビューションによっては、NVIDIA Optimus、"
				+ "nvidia-prime、switcheroo-control、または他のハイブリッドシステムを構成する必要がある場合があります。\n\n"
				+ "Fedora/RHEL およびその派生版では、通常 RPMFusion のドキュメントに従うことが推奨されます。\n\n" + "下のボタンは、推奨される公式ドキュメントを開きます。";
	}

	@Override
	public String gpuFixTextoMac() {
		return Statics.nombre_cd.obtener() + " は、潜在的な GPU 選択問題を検出しました。\n\n"
				+ "ハイブリッド GPU を搭載した一部の macOS システムでは、高度なシステム設定を通じて専用 GPU の使用を強制することができます。\n\n"
				+ "適用ボタンは、高性能 GPU を優先するためのコマンドを実行しようとします。";
	}

	@Override
	public String gpuFixTextoOtroSistema() {
		return Statics.nombre_cd.obtener() + " は GPU に関連する潜在的な問題を検出しましたが、" + "このオペレーティングシステム用の特定の実装はありません。";
	}

	@Override
	public String gpuFixLinuxManual() {
		return "Linux では、通常、ディストリビューション、" + "NVIDIA ドライバー、および使用されている Optimus/PRIME システムに応じて手動で構成する必要があります。";
	}

	@Override
	public String gpuFixSistemaNoSoportado() {
		return "自動 GPU 構成をサポートしていないオペレーティングシステムです。";
	}

	@Override
	public String gpuFixJavaNoDetectado() {
		return "javaw.exe の現在のパスを検出できませんでした。";
	}

	@Override
	public String gpuFixWindowsAplicado(String ruta) {
		return "GPU 設定が次のパスに正常に適用されました:\n\n" + ruta + "\n\n" + "GpuPreference=2 は高性能 GPU を示します。";
	}

	@Override
	public String gpuFixErrorAplicando() {
		return "GPU 設定を適用しようとしている間にエラーが発生しました";
	}

	@Override
	public String gpuFixMacAplicado() {
		return "高性能 GPU 設定が適用されました。";
	}

	@Override
	public String gpuFixMacError() {
		return "macOS で GPU 設定を適用できませんでした";
	}

	// Japanese (日本語)
	@Override
	public String rendimientoTitulo() {
		return "パフォーマンスマネージャー";
	}

	@Override
	public String rendimientoBotonSidebar() {
		return "パフォーマンス";
	}

	@Override
	public String rendimientoBotonAnalizar() {
		return "パフォーマンスを分析";
	}

	@Override
	public String rendimientoBotonAbrirGPU() {
		return "GPU設定を開く";
	}

	@Override
	public String rendimientoDescripcion() {
		return "このパネルは、環境問題、推奨または危険なmod、およびパフォーマンスを向上させることができる構成オプションをチェックします。すべてのオプションが同時に機能するわけではなく、すべてのMinecraftバージョンに適しているわけでも、すべてのmodローダーと互換性があるわけでもありません。それで構いません：完璧な最適化スコアは必要ありません。";
	}

	@Override
	public String rendimientoNotaCompatibilidad() {
		return "注意：これらの提案は可能性であり、すべてを適用する命令ではありません。一部のオプションは互いに競合したり、バージョン、ランチャー、modローダー、またはmodpackに適していない場合があります。";
	}

	@Override
	public String rendimientoPestanaResumen() {
		return "概要";
	}

	@Override
	public String rendimientoPestanaAmbiental() {
		return "環境問題";
	}

	@Override
	public String rendimientoPestanaMods() {
		return "推奨modおよびリスク項目";
	}

	@Override
	public String rendimientoPestanaConfigs() {
		return "構成オプション";
	}

	@Override
	public String rendimientoResumenTitulo() {
		return "分析の概要";
	}

	@Override
	public String rendimientoResumenAmbiental(int cantidad) {
		return "発見された環境問題: " + cantidad;
	}

	@Override
	public String rendimientoResumenMods(int cantidad) {
		return "発見されたmodの提案またはリスク項目: " + cantidad;
	}

	@Override
	public String rendimientoResumenConfigs(int cantidad) {
		return "発見された構成の提案: " + cantidad;
	}

	@Override
	public String rendimientoResumenGPU() {
		return "GPUの問題が検出されました。そのため、GPU設定を開くボタンが有効になりました。";
	}

	@Override
	public String rendimientoSinHallazgos() {
		return "このセクションで提案は見つかりませんでした。";
	}

	@Override
	public String rendimientoSugerencia() {
		return "提案";
	}

	@Override
	public String rendimientoColorFondo() {
		return "パフォーマンス - 背景";
	}

	@Override
	public String rendimientoColorPanel() {
		return "パフォーマンス - パネル";
	}

	@Override
	public String rendimientoColorTexto() {
		return "パフォーマンス - テキスト";
	}

	@Override
	public String rendimientoColorTextoSecundario() {
		return "パフォーマンス - 副テキスト";
	}

	@Override
	public String rendimientoColorBoton() {
		return "パフォーマンス - ボタン";
	}

	@Override
	public String rendimientoColorBotonTexto() {
		return "パフォーマンス - ボタンテキスト";
	}

	@Override
	public String rendimientoColorSeleccion() {
		return "パフォーマンス - 選択";
	}

	// Japanese (日本語)
	@Override
	public String mensajeDialogoCompartirPrimitiva() {
		return "クラッシュが発生しました。解決策を含むポップアップウィンドウが表示されない場合は、ログをサポートセンターに送信してください。";
	}

	@Override
	public String irAModoNormalEstiloTL() {
		return "通常モードへ移動";
	}

	@Override
	public String noHayEnlacesParaCopiar() {
		return "コピーするリンクはありません。";
	}

	@Override
	public String error_inesperado() {
		return "予期せぬエラー";
	}

	@Override
	public String centroDeSoporte() {
		return "サポートセンター";
	}

	@Override
	public String noHayCentroSoporteConfigurado() {
		return "サポートセンターが構成されていません。";
	}

	// Japanese (日本語)
	public String historialMCLogs() {
		return "MCLogs 履歴";
	}

	public String endpoint() {
		return "エンドポイント";
	}

	public String slug() {
		return "スラグ";
	}

	public String tokenEliminacion() {
		return "削除トークン";
	}

	public String enlace() {
		return "リンク";
	}

	public String lineas() {
		return "行数";
	}

	public String errores() {
		return "エラー";
	}

	public String eliminarRegistroMCLogs() {
		return "記録を削除";
	}

	public String faltanDatosParaEliminarMCLogs() {
		return "スラグまたは削除トークンがありません。";
	}

	public String confirmarEliminarMCLogs() {
		return "この MCLogs の記録を削除してもよろしいですか？";
	}

	public String registroEliminadoMCLogs() {
		return "記録が正常に削除されました。";
	}

	public String confirmar() {
		return "確認";
	}

	public String colorCampoTexto() {
		return "テキストフィールドの色";
	}

	// Japanese (日本語)
	public String historialCDPaste() {
		return "CDPaste 履歴";
	}

	public String enlaceRaw() {
		return "Raw リンク";
	}

	public String tamano() {
		return "サイズ";
	}

	public String eliminarRegistroCDPaste() {
		return "CDPaste 記録を削除";
	}

	public String faltanDatosParaEliminarCDPaste() {
		return "CDPaste 記録の識別子 (slug) がありません。";
	}

	public String confirmarEliminarCDPaste() {
		return "この CDPaste の記録を削除してもよろしいですか？";
	}

	public String registroEliminadoCDPaste() {
		return "CDPaste 記録が正常に削除されました。";
	}

	// Japanese (日本語)
	public String launcherGenerico() {
		return "一般";
	}

	public String launcherServidorMinecraft() {
		return "Minecraft サーバー";
	}

	public String descargandoYPreparandoEnlaces() {
		return "リンクをダウンロードして準備中...";
	}

	public String seleccioneArchivoLog() {
		return "ログファイルを選択";
	}

	public String archivoNoValido() {
		return "ファイルが無効です。";
	}

	public String archivoSeleccionado() {
		return "選択されたファイル：";
	}

	public String presioneGuardarParaAgregarAnalisis() {
		return "保存して閉じるを押して分析に追加してください。";
	}

	public String errorAlCargarArchivoArrastrado() {
		return "ドラッグされたファイルの読み込みエラー";
	}

	public String errorAlAbrirArchivo() {
		return "ファイルを開くエラー";
	}

	public String errorDosPuntos() {
		return "エラー";
	}

	// Japanese (日本語)
	public String eliminarRegistros() {
		return "記録を削除";
	}

	// Japanese (日本語)
	public String editorConfigsMods() {
		return "Mod 設定エディタ";
	}

	public String abrirConfig() {
		return "設定を開く";
	}

	public String guardarConfig() {
		return "設定を保存";
	}

	public String recargarConfig() {
		return "再読み込み";
	}

	public String rutaConfig() {
		return "パス";
	}

	public String tipoConfig() {
		return "タイプ";
	}

	public String claveConfig() {
		return "キー";
	}

	public String valorConfig() {
		return "値";
	}

	public String buscarConfig() {
		return "検索";
	}

	public String sinArchivoSeleccionado() {
		return "ファイルが選択されていません";
	}

	public String archivoNoSoportado() {
		return "利用可能なエンジンでサポートされていないファイルです";
	}

	public String configGuardada() {
		return "設定が正常に保存されました";
	}

	public String errorCargandoConfig() {
		return "設定の読み込み中にエラーが発生しました";
	}

	public String errorGuardandoConfig() {
		return "設定の保存中にエラーが発生しました";
	}

	public String seleccionarArchivoConfig() {
		return "設定ファイルを選択";
	}

	// Japanese (日本語)
	public String suprimirConsolaCD() {
		return "Hide " + Statics.nombre_cd.obtener() + " consoleを非表示";
	}

	public String suprimirVerificacionDeStacktrazos() {
		return "スタックトレースチェックを非表示";
	}

	// Japanese (日本語)
	public String importadorBotonFusionar() {
		return "マージ";
	}

	// Japanese (日本語)
	@Override
	public String mod() {
		return "Mod";
	}

	@Override
	public String version() {
		return "バージョン";
	}

	@Override
	public String claseEncontrada() {
		return "クラスが見つかりました";
	}

	@Override
	public String coincidencias() {
		return "一致";
	}

	@Override
	public String resultadosDeBusqueda() {
		return "検索結果";
	}

	@Override
	public String desconocido() {
		return "不明";
	}

	@Override
	public String desconocida() {
		return "不明";
	}

	@Override
	public String curseForgeUrl() {
		return "CurseForge URL";
	}

	@Override
	public String modrinthUrl() {
		return "Modrinth URL";
	}

	@Override
	public String modsEncontradosPara(String clase) {
		return clase + " に対して見つかった Mod";
	}

	// Japanese (日本語)
	@Override
	public String entradaCarpetaInvalida(String ruta) {
		return "無効なフォルダエントリ: " + ruta;
	}

	@Override
	public String carpetaSinHashes(String ruta) {
		return "ハッシュ値のないフォルダ: " + ruta;
	}

	@Override
	public String noSePudoAccederCarpeta(String ruta) {
		return "フォルダにアクセスできませんでした: " + ruta;
	}

	@Override
	public String archivoFaltanteEnCarpeta(String ruta, String subRuta) {
		return "フォルダ内のファイルが見つかりません: " + ruta + "/" + subRuta;
	}

	@Override
	public String hashIncorrectoEn(String ruta, String subRuta) {
		return "ハッシュ値が正しくありません: " + ruta + "/" + subRuta;
	}

	@Override
	public String archivoNoAutorizadoEnCarpeta(String ruta, String subRuta) {
		return "フォルダ内に許可されていないファイルがあります: " + ruta + "/" + subRuta;
	}

	@Override
	public String entradaArchivoInvalida(String ruta) {
		return "無効なファイルエントリ: " + ruta;
	}

	@Override
	public String hashFaltanteParaArchivo(String ruta) {
		return "ファイルのハッシュ値がありません: " + ruta;
	}

	@Override
	public String archivoFaltante(String ruta) {
		return "ファイルが見つかりません: " + ruta;
	}

	@Override
	public String errorAlLeerArchivo(String ruta) {
		return "ファイルの読み込みエラー: " + ruta;
	}

	@Override
	public String hashIncorrectoParaArchivo(String ruta) {
		return "ファイルのハッシュ値が正しくありません: " + ruta;
	}

	// Japanese (日本語)
	@Override
	public String listo() {
		return "完了！";
	}

	public String error_al_cargar_plantilla_desde_disco() {
		return "ディスクからテンプレートを読み込む際にエラーが発生しました: ";
	}

	public String no_se_encontro_plantilla_restablecer() {
		return "テンプレートが見つかりません。「テンプレートをリセット」ボタンを使用してリセットしてください。";
	}

	public String plantilla_guardada_en() {
		return "テンプレートが保存された場所: ";
	}

	public String plantilla_restablecida_correctamente() {
		return "テンプレートが正常にリセットされました。";
	}

	public String error_al_guardar() {
		return "保存中にエラーが発生しました: ";
	}

	public String error_al_restablecer() {
		return "リセット中にエラーが発生しました: ";
	}

	public String error_al_restablecer_imagen() {
		return "画像のリセット中にエラーが発生しました: ";
	}

	public String no_se_encontro_imagen_en_recurso() {
		return "リソース内に画像が見つかりません: ";
	}

	public String imagen_restablecida() {
		return "画像がリセットされました: ";
	}

	public String editor_html() {
		return "HTML エディタ";
	}

	public String vista_previa() {
		return "プレビュー";
	}

	public String configuracion_colores_imagenes() {
		return "色と画像の設定";
	}

	public String imagenes_con_ruta(String ruta) {
		return "画像 (" + ruta + ")";
	}

	public String enlaces_imagenes_reportes_compartidos() {
		return "画像リンク（共有レポート）";
	}

	public String enlaces_imagenes_reporte_compartido() {
		return "画像リンク（共有レポート）";
	}

	public String url_usada_en_reportes_compartidos() {
		return "共有レポートで使用される URL";
	}

	public String error_creando_codice_json() {
		return "codice.json の作成中にエラーが発生しました: ";
	}

	public String error_exportando() {
		return "エクスポート中にエラーが発生しました: ";
	}

	public String validacion() {
		return "検証";
	}

	public String ver_codigo() {
		return "コードを表示";
	}

	// Japanese (日本語)
	public String importar_instancia() {
		return "インスタンスをインポート";
	}

	public String compartir_instancia() {
		return "インスタンスを共有";
	}

	public String error_al_cargar_mods() {
		return "Mod の読み込み中にエラーが発生しました。";
	}

	public String instalar() {
		return "インストール";
	}

	public String mods_instalados() {
		return "インストール済み Mod";
	}

	public String guardar_como_archivo() {
		return "ファイルとして保存";
	}

	public String exportando_modpack() {
		return "Modpack をエクスポート中...";
	}

	public String modpack_exportado() {
		return "Modpack がエクスポートされました:\n";
	}

	public String conectando() {
		return "接続中...";
	}

	public String esperando_descarga() {
		return "ダウンロードを待機中...";
	}

	public String finalizado() {
		return "完了";
	}

	public String retener_dos_puntos() {
		return "保持:";
	}

	public String descargar_deps() {
		return "依存関係をダウンロード";
	}

	public String no_faltan_dependencias() {
		return "欠落している依存関係はありません。";
	}

	public String descargar_nbt_para_quests() {
		return "クエスト用 NBT をダウンロード";
	}

	public String descargar_nbt() {
		return "NBT をダウンロード";
	}

	public String error_cargando_informe() {
		return "レポートの読み込み中にエラー: ";
	}

	@Override
	public String exportar_modpack() {
		return "Modpack をエクスポート";
	}

	@Override
	public String error_exportando_modpack() {
		return "Modpack のエクスポート中にエラー:\n";
	}

	@Override
	public String importador_confirmar_descargar_nbt_para_quests() {
		return "クエストのマージに必要な NBT 依存関係がダウンロードされます。\n\n" + "その後、クラスパスに反映させるために再起動が必要になる場合があります。";
	}

	@Override
	public String resultado_nulo() {
		return "結果が null です。";
	}

	@Override
	public String dependencia_nbt_descargada_reiniciar(String nombrePrograma) {
		return "NBT 依存関係がダウンロードされました。\n\n" + "SNBT マージで依然として NBT エンジンが欠落していると表示される場合は、" + nombrePrograma
				+ " を再起動してください。";
	}

	@Override
	public String no_se_pudo_descargar_dependencia_nbt() {
		return "NBT 依存関係をダウンロードできませんでした。";
	}

	// Japanese (日本語)
	@Override
	public String profilerTituloRendimiento() {
		return "パフォーマンスプロファイラー";
	}

	@Override
	public String profilerEstadoActivo() {
		return "アクティブ";
	}

	@Override
	public String profilerAyudaMinaly() {
		return "最も遅いメソッドが上部に表示されます。バーは累積時間の相対的な重みを示します。";
	}

	@Override
	public String profilerConfigColorPanel() {
		return "パネルの色";
	}

	@Override
	public String profilerConfigColorBarra() {
		return "バーの色";
	}

	@Override
	public String profilerConfigUsarModeloOriginal() {
		return "元のモデルを使用";
	}

	@Override
	public String profilerColumnaClase() {
		return "クラス";
	}

	@Override
	public String profilerColumnaMetodo() {
		return "メソッド";
	}

	@Override
	public String profilerColumnaLlamadas() {
		return "呼び出し回数";
	}

	@Override
	public String profilerColumnaTiempoTotal() {
		return "合計時間";
	}

	@Override
	public String profilerEstadoResumen(String estado, int metodos, int top, String totalVisible) {
		return estado + " | メソッド数: " + metodos + " | 上位: " + top + " | 表示総数: " + totalVisible;
	}

	// Japanese (日本語)
	@Override
	public String samplerTituloRendimiento() {
		return "パフォーマンスサンプラー";
	}

	@Override
	public String samplerAyudaEineLotta() {
		return "累積時間が最も多いメソッドが上部に表示されます。バーは相対的な重みを視覚的に示します。";
	}

	@Override
	public String samplerColumnaMuestras() {
		return "サンプル数";
	}

	@Override
	public String samplerColumnaPromedio() {
		return "平均";
	}

	@Override
	public String samplerEstadoResumen(String estado, int metodos, int top) {
		return estado + " | メソッド数: " + metodos + " | 上位: " + top;
	}

	// Japanese (日本語)
	public String mostrarSelectorAplicacionPrincipal() {
		return "メインGUIのアプリケーションセレクター";
	}

	public String mostrarBotonCDLauncherPrincipal() {
		return "メインGUIのCDLauncherボタン";
	}

	public String mostrarBotonCDModsPrincipal() {
		return "メインGUIのCDModsボタン";
	}

	public String mostrarBotonIAPrincipal() {
		return "メインGUIのAIボタン";
	}

	// Japanese (日本語)
	@Override
	public String modsInstalados() {
		return "インストール済み Mod";
	}

}
