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
}
