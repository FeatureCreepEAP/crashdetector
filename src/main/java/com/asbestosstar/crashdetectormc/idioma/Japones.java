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
}
