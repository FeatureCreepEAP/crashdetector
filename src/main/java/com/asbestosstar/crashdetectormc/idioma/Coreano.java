package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Idioma;

public class Coreano implements Idioma {

    @Override
    public String carpeta_de_mods_no_valido() {
        return "이것은 유효한 모드 폴더가 아닙니다";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "CrashDetector의 JAR 파일 위치를 알 수 없습니다";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "PID 검색 중: " + String.valueOf(pid);
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "(PID: " + String.valueOf(pid) + ") 종료되었습니다!";
    }

    @Override
    public String no_tenemos_jvm() {
        return "JVM이 없습니다";
    }
    
    @Override
    public String probelma_con_graficas_ati() {
        return "드라이버를 업데이트하면 도움이 될 수 있습니다. 드라이버가 손상된 상태에서는 일반적인 방법으로 업데이트를 찾을 수 없으므로 첨부된 가이드를 따르는 것이 중요합니다. 중요: Nvidia 그래픽을 사용하는 경우 Windows 설정과 Nvidia 제어판에서 Minecraft 관련 항목(예: Java 및 실행기)을 고성능 그래픽으로 우선 설정해야 합니다. 문제를 해결하려면 다음 가이드를 읽으십시오: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }

    @Override
    public String probelma_con_graficas_nouveau() {
        return "일부 이전 버전에서는 초기 로딩 화면에서 Nouveau 그래픽과 관련된 문제가 발생할 수 있습니다.";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "그래픽 드라이버에 문제가 있습니다. AMD/ATI GPU 또는 APU를 사용하는 경우 AMD 그래픽 드라이버를 업데이트하십시오. NVIDIA 그래픽 카드를 사용하는 경우 게임 및 모든 javaw.exe 인스턴스를 전용 그래픽 카드를 사용하도록 설정하십시오. 다음 가이드를 읽으십시오: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }

    @Override
    public String fmlEarlyWindow() {
        return "FML Early 창이 실패하고 있습니다. "
                + "이를 변경하려면 (.)minecraft/config/fml.toml로 이동하여 "
                + "earlyWindowProvider를 earlyWindowProvider=\"none\"으로 수정하십시오. "
                + "M1 Mac을 사용하는 경우 ARM 버전의 Java를 사용하고 Intel x64 버전을 사용하지 않는지 확인하십시오. "
                + "드라이버가 오래된 경우에도 흔히 발생하는 문제입니다. Windows에서 이를 비활성화해도 작동하지 않는 경우 다음 가이드를 참조하십시오: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "필요한 모든 종속성을 가지고 있지 않습니다:";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return linea.replace("Requested by", "요청자").replace("Expected range", "예상 범위");
    }
    
        @Override
    public String local_headless(String archivo) {
        return "크래시 리포트가 여기에 저장되었습니다: " + archivo;
    }
    
        @Override
    // 한국어 GUI 메시지
    public String texto_de_gui() {
        return "이는 CrashDetector의 GUI입니다. 게임이 문제 없이 종료되면 무시하세요.";
    }
    
        @Override
    // 한국어 버전
    public String texto_de_buton_local_enlance() {
        return "보고서 보기";
    }

    @Override
    // 로컬 링크 버튼 설명
    public String texto_debajo_de_buton_local_enlance() {
        return "브라우저에서 로컬 보고서를 확인하세요";
    }

    @Override
    // 공유 버튼 텍스트
    public String texto_de_buton_compartir_enlance() {
        return "보고서 공유";
    }

    @Override
    // 공유 버튼 설명
    public String texto_debajo_de_buton_compartir_enlance() {
        return "로그가 securelogger.net에 업로드되며 보고서는 다른 사이트에 3일간 저장됩니다";
    }
    
    
     @Override
public String problematico_jar() {
    return "<b>Trovitaj potence problemaj JAR-dosieroj (Prioritato: FATAL > pli alta nivelo > pli malalta linio):</b>";
}

@Override
public String nivel() {
    return "<b>lvl: </b>";
}

@Override
public String possibladad_fatal() {
    return "<b>Eble fatale:</b> ";
}

@Override
public String modids_problematicos() {
    return "<b>Trovitaj problemaj modid (Prioritato: FATAL > pli malalta nivelo > pli malalta linio):</b>";
}

@Override
public String packages_problematicos() {
    return "<b>Trovitaj problemaj pakaĵoj (Prioritato: FATAL > pli malalta nivelo > pli malalta linio):</b>";
}

@Override
public String faltar_de_clases_fatales() {
    return "<b>Mankantaj fatalaj klasoj trovitaj:</b>";
}

@Override
public String corchetes_ondulados() {
    return "<b>Enhavo en {} (Plej grava supre, nur unuaj 20):</b>";
}

@Override
public String config_spongemixin_problematico(String archivo) {
    return "<b>Trovita problemata SpongeMixin agordo: " + archivo + "</b>";
}

@Override
public String module_resolution_exception(String modules, String paquete) {
    return "중복된 패키지를 가진 모드가 있습니다: " + modules + " 중복된 패키지 " + paquete.replace(".", "/") + ". 이 문제는 jar 파일에서 해당 패키지(폴더)를 삭제하여 해결할 수 있습니다. FileRoller,WinRAR, 7-Zip 같은 압축 소프트웨어로 jar 파일을 열거나, 파일 확장자를 .jar에서 .zip으로 변경한 후 폴더를 삭제하고 다시 .jar로 변경하면 됩니다.";
}

@Override
public String modlauncher_mods_duplicado(String linea) {
    return "<b>You have duplicate Mods</b> " + linea.replace("from mod files", "from mod files");
}

@Override
public String mcforge_mod_suspechoso() {
    return "<b>MinecraftForge 의심스러움: 이 모드에 문제가 있습니다:</b> ";
}

@Override
public String lithostichctov() {
    return "<b>CTOV는 lithostitched가 필요합니다. 여기에서 설치할 수 있습니다: https://www.curseforge.com/minecraft/mc-mods/lithostitched</b>";
}
    
}
