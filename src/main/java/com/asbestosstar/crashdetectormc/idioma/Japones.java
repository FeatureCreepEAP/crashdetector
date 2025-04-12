package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Idioma;

public class Japones implements Idioma {

    @Override
    public String carpeta_de_mods_no_valido() {
        return "これは有効なMODフォルダではありません";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "CrashDetectorのJARファイルの場所がわかりません";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "PIDを検索中: " + String.valueOf(pid);
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "(PID: " + String.valueOf(pid) + ") は終了しています！";
    }

    @Override
    public String no_tenemos_jvm() {
        return "JVMがありません";
    }
    
    @Override
public String probelma_con_graficas_ati() {
    return "ドライバーを更新すると問題が解決する可能性があります。ただし、ドライバーが破損している場合、通常の方法で更新を見つけることはできないため、リンク先のガイドに従うことが重要です。重要: Nvidiaグラフィックを使用している場合、Windowsの設定とNvidiaコントロールパネルで、Minecraftに関連するすべての項目（Javaやランチャーなど）を高性能グラフィックに設定してください。この問題を解決するには、次のガイドを参照してください: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
}

@Override
public String probelma_con_graficas_nouveau() {
    return "一部の古いバージョンでは、初期ロード画面でNouveauグラフィックに関連する問題が発生することがあります。";
}

@Override
public String probelma_con_graficas_general() {
    return "グラフィックドライバーに問題があります。AMD/ATI GPUまたはAPUを使用している場合は、AMDグラフィックドライバーを更新してください。NVIDIAグラフィックカードを使用している場合は、ゲームおよびjavaw.exeのすべてのインスタンスが専用グラフィックカードを使用するように設定されていることを確認してください。次のガイドを参照してください: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
}

@Override
public String fmlEarlyWindow() {
    return "FML Earlyウィンドウが失敗しています。"
            + "これを変更するには、(.)minecraft/config/fml.toml に移動して、"
            + "earlyWindowProvider を earlyWindowProvider=\"none\" に編集してください。"
            + "M1 Macを使用している場合は、Intel x64版ではなくARM版のJavaを使用していることを確認してください。"
            + "これは、ドライバーが古くなっている場合にもよく発生する問題です。Windowsでこれが機能しない場合は、次のガイドを参照してください: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
}

@Override
public String no_tienes_las_dependencias_necesitas() {
    return "必要な依存関係がすべて揃っていません:";
}

@Override
public String linea_de_dependencia(String linea) {
    return linea.replace("Requested by", "リクエスト元").replace("Expected range", "予想範囲");
}

    @Override
    public String local_headless(String archivo) {
        return "クラッシュレポートはこちらに保存されました：" + archivo;
    }
    
     @Override
    // 日本語GUIテキスト
    public String texto_de_gui() {
        return "これはCrashDetectorのGUIです。ゲームが正常に終了した場合は無視してください。";
    }
    
    
        @Override
    // 日本語版
    public String texto_de_buton_local_enlance() {
        return "レポートを表示";
    }

    @Override
    // ローカルボタンの説明
    public String texto_debajo_de_buton_local_enlance() {
        return "ブラウザでローカルレポートを表示";
    }

    @Override
    // 共有ボタンのテキスト
    public String texto_de_buton_compartir_enlance() {
        return "レポートを共有";
    }

    @Override
    // 共有ボタンの詳細
    public String texto_debajo_de_buton_compartir_enlance() {
        return "ログはsecurelogger.netにアップロードされ、レポートは3日間他サイトに保存されます";
    }
    
        @Override
public String problematico_jar() {
    return "<b>問題のある可能性のあるJARファイル（FATAL優先 > レベル高 > 行番号低）：</b>";
}

@Override
public String nivel() {
    return "<b>lvl: </b>";
}

@Override
public String possibladad_fatal() {
    return "<b>致命的問題の可能性:</b> ";
}

@Override
public String modids_problematicos() {
    return "<b>問題のある可能性のあるmodid（FATAL優先 > レベル低 > 行番号低）：</b>";
}

@Override
public String packages_problematicos() {
    return "<b>問題のある可能性のあるパッケージ（FATAL優先 > レベル低 > 行番号低）：</b>";
}

@Override
public String faltar_de_clases_fatales() {
    return "<b>致命的なクラスが不足しています：</b>";
}

@Override
public String corchetes_ondulados() {
    return "<b>{}内のコンテンツ（上位が重要、最初の20件のみ表示）：</b>";
}

@Override
public String config_spongemixin_problematico(String archivo) {
    return "<b>問題のあるSpongeMixin設定が検出されました： " + archivo + "</b>";
}


@Override
public String module_resolution_exception(String modules, String paquete) {
    return "重複したパッケージを持つModがあります: " + modules + " 重複したパッケージ " + paquete.replace(".", "/") + " この問題は、jarファイルからパッケージ（フォルダ）を削除することで解決できます。File-Roller,WinRARや7-Zipなどのアーカイブソフトウェアでjarファイルを開くか、ファイル拡張子を.jarから.zipに変更してフォルダを削除し、再度.jarファイルに戻すことができます。";
}

@Override
public String modlauncher_mods_duplicado(String linea) {
    return "<b>重複したModがあります</b> " + linea.replace("from mod files", "Modファイルから");
}

@Override
public String mcforge_mod_suspechoso() {
    return "<b>MinecraftForge が怪しい: このModに問題があります:</b> ";
}

@Override
public String lithostichctov() {
    return "<b>CTOV には lithostitched が必要です。ここからインストールできます https://www.curseforge.com/minecraft/mc-mods/lithostitched</b>";
}


}
