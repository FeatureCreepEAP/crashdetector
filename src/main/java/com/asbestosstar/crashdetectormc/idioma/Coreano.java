package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Config;
import com.asbestosstar.crashdetectormc.Idioma;

public class Coreano implements Idioma {
    private final Config config = Config.obtenerInstancia();

    @Override
    public String carpeta_de_mods_no_valido() {
        return "<span style='color:#" + config.obtenerColorError() + "'>잘못된 mods 폴더</span>";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "<span style='color:#" + config.obtenerColorError() + "'>CrashDetector의 JAR 파일을 찾을 수 없습니다</span>";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>PID 검색 중: " + String.valueOf(pid) + "</span>";
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid) + ") 종료되었습니다!</span>";
    }

    @Override
    public String no_tenemos_jvm() {
        return "<span style='color:#" + config.obtenerColorError() + "'>JVM 없음</span>";
    }

@Override
public String problema_con_graficas_ati() {
    return "<span style='color:#" + config.obtenerColorError() + "'>ATI/AMD 드라이버를 업데이트하면 도움이 될 수 있습니다. 이를 해결하려면 다음 가이드를 읽으세요: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>드라이버 업데이트 가이드</a> https://www.amd.com/ko/support/download/drivers.html 다운로드 </span>";
}

    @Override
    public String probelma_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>일부旧버전에서 Nouveau 그래픽 카드의 초기 로드 화면에서때때로문제가 발생합니다.</span>";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>그래픽 카드 드라이버에 문제가 있습니다. AMD/ATI GPU 또는 APU를 사용하는 경우 AMD 그래픽 드라이버를 업데이트하십시오. NVIDIA 그래픽 카드를 사용하는 경우 게임 및 모든 javaw.exe 인스턴스를獨立显卡로 설정하십시오. 이 가이드를 읽으십시오: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>드라이버 업데이트 가이드</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>FML 초기 창 로드에 실패했습니다. 수정하려면 (.minecraft/config/fml.toml)로 이동하여 earlyWindowProvider를 \"none\"으로 설정하십시오. Mac M1을 사용하는 경우 Intel x64 버전이 아닌 ARM 버전 Java를 사용 중인지 확인하십시오. 이것은 또한 드라이버가 오래된 경우의 일반적인 문제입니다. Windows를 사용 중이고 이 설정을 사용하지 않으면 이 가이드를 참조하십시오: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>드라이버 업데이트 가이드</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "<span style='color:#" + config.obtenerColorError() + "'>필요한 의존성이 없습니다:</span>";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>" + linea.replace("Requested by", "요청자").replace("Expected range", "예상 범위") + "</span>";
    }

    @Override
    public String local_headless(String archivo) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>CrashDetector 보고서는 여기에서 확인할 수 있습니다 <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>보고서 보기</a></span>";
    }

    @Override
    public String texto_de_gui() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>CrashDetector의 GUI 인터페이스입니다. 게임이 정상적으로 종료된 경우 이 인터페이스를 무시하십시오.</span>";
    }

    @Override
    public String texto_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorBoton() + "'>보고서 보기</span>";
    }

    @Override
    public String texto_debajo_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>브라우저에서 로컬 보고서를 확인합니다.</span>";
    }

    @Override
    public String texto_de_buton_compartir_enlance() {
        return "보고서 공유";
    }

    @Override
    public String texto_debajo_de_buton_compartir_enlance() {
        return "보고서 공유. 로그가 securelogger.net에 업로드되고 보고서가 다른 사이트에 업로드됩니다.";
    }

    @Override
    public String problematico_jar() {
        return "<b style='color:#" + config.obtenerColorError() + "'>문제 있는 JAR 파일 감지(FATAL 우선, 그 다음은 고우선순위 및 低우선순위):</b>";
    }

    @Override
    public String nivel() {
        return "<b style='color:#" + config.obtenerColorError() + "'> 등급:</b> ";
    }

    @Override
    public String possibladad_fatal() {
        return "<b style='color:#" + config.obtenerColorError() + "'>치명적일 수 있음:</b> ";
    }

    @Override
    public String modids_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>문제 있는 ModID 감지(FATAL 우선, 그 다음은 低우선순위 및 低우선순위):</b>";
    }

    @Override
    public String packages_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>문제 있는 패키지 감지(FATAL 우선, 그 다음은 低우선순위 및 低우선순위):</b>";
    }

    @Override
    public String faltar_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>누락된 중요 클래스 감지:</b>";
    }

    @Override
    public String corchetes_ondulados() {
        return "<b style='color:#" + config.obtenerColorError() + "'>{} 내용(가장 중요한 내용이 상단에 있으며 처음 20개만 표시됨):</b>";
    }

    @Override
    public String config_spongemixin_problematico(String archivo) {
        return "<b style='color:#" + config.obtenerColorError() + "'>문제 있는 SpongeMixin 구성 감지: " + "</b>" + archivo;
    }

    @Override
    public String module_resolution_exception(String modules, String paquete) {
        return "<span style='color:#" + config.obtenerColorError() + "'>Mods에 중복 패키지가 있습니다: " + modules + " 중복 패키지 " + paquete.replace(".", "/") + ". jar의 패키지(폴더)를 삭제하여 해결할 수 있습니다. WinRAR 또는 7z와 같은 파일 소프트웨어로 jar를 열거나 파일 확장명을 .jar에서 zip으로 변경한 후 폴더를 삭제하고 다시 .jar로 변경할 수 있습니다.</span>";
    }

    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>중복 Mods 감지</b> " + linea.replace("from mod files", "mod 파일에서");
    }

    @Override
    public String mcforge_mod_suspechoso() {
        return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge에서 의심스러운 mod 문제 감지:</b> ";
    }

    @Override
    public String lithostichctov() {
        return "<b style='color:#" + config.obtenerColorError() + "'>CTOV에는 lithostitched가 필요합니다. 여기에서 설치할 수 있습니다: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#" + config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
    }

    @Override
    public String necesitasSodiumParaIris() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Iris 셰이더 또는 Oculus를 사용하려면 SODIUM 또는 다른 로더의 호환 버전(Rubidium, Embedium, Bedium)이 필요합니다</b>";
    }

    @Override
    public String kubeJSResourcePack(String mod_nombre) {
        return "<b style='color:#" + config.obtenerColorError() + "'>KubeJS 확장에 문제가 있습니다 </b>" + mod_nombre;
    }
    
    
    
    @Override
public String problema_con_graficas_nvidia_windows_viejo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "Windows 11 이전 버전에서 NVIDIA 드라이버 문제 감지됨."
            + "</span><br/><br/>"
            + "Minecraft(및 현재 JVM)이 전용 NVIDIA GPU를 사용하도록 하려면 다음 단계를 따르세요:<br/><br/>"
            + "1. <strong>Java 실행 파일 식별하기:</strong><br/>"
            + "   - 이 프로그램은 다음 Java 실행 파일을 사용 중입니다: "
            + obtenerRutaJava() + "<br/>"
            + "   - 특정 경로가 보이지 않는 경우, 시스템에서 'java.exe'를 검색하여 Java 실행 파일을 찾을 수 있습니다.<br/><br/>"
            + "2. <strong>NVIDIA 제어판 열기:</strong><br/>"
            + "   - 바탕 화면에서 마우스 오른쪽 버튼을 클릭하고 'NVIDIA 제어판'을 선택하세요.<br/><br/>"
            + "3. <strong>선호하는 GPU 설정하기:</strong><br/>"
            + "   - NVIDIA 제어판에서 '3D 설정 관리'로 이동합니다.<br/>"
            + "   - '프로그램 설정' 옵션을 선택합니다.<br/>"
            + "   - '추가'를 클릭하고 앞서 식별한 Java 실행 파일(ex.: 'java.exe')을 찾습니다.<br/>"
            + "   - '고성능 NVIDIA 프로세서'를 사용하도록 설정되었는지 확인합니다.<br/><br/>"
            + "4. <strong>변경 사항 저장하기:</strong><br/>"
            + "   - 변경 사항을 적용하고 NVIDIA 제어판을 닫습니다.<br/><br/>"
            + "5. <strong>Minecraft 재시작하기:</strong><br/>"
            + "   - Minecraft를 재시작하여 변경 사항을 적용합니다.<br/><br/>"
            + "Windows Server 2022 또는 Windows 10을 사용 중인 경우, 최신 NVIDIA 드라이버가 설치되어 있는 한 이러한 단계는 유효합니다.<br/><br/>"
            + "참고: NVIDIA 제어판을 찾을 수 없는 경우, NVIDIA 드라이버가 올바르게 설치되어 있는지 확인하세요.";
}






@Override
public String problema_con_graficas_nvidia_windows_nuevo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "Windows 11/Server 2025 이후 버전에서 NVIDIA 드라이버 문제 감지됨."
            + "</span><br/><br/>"
            + "Minecraft(및 현재 JVM)이 전용 NVIDIA GPU를 사용하도록 하려면 다음 단계를 따르세요:<br/><br/>"
            + "1. <strong>Java 실행 파일 식별하기:</strong><br/>"
            + "   - 이 프로그램은 다음 Java 실행 파일을 사용 중입니다: "
            + obtenerRutaJava() + "<br/>"
            + "   - 특정 경로가 보이지 않는 경우, 시스템에서 'java.exe'를 검색하여 Java 실행 파일을 찾을 수 있습니다.<br/><br/>"
            + "2. <strong>설정 앱 열기:</strong><br/>"
            + "   - <code>Win + I</code> 키를 눌러 설정 앱을 엽니다.<br/>"
            + "   - <strong>시스템 > 디스플레이 > 그래픽</strong>으로 이동합니다.<br/><br/>"
            + "3. <strong>선호하는 GPU 설정하기:</strong><br/>"
            + "   - '그래픽' 섹션에서 '기본 그래픽 설정'을 클릭합니다.<br/>"
            + "   - '데스크톱 앱'을 선택하고 '찾아보기'를 클릭합니다.<br/>"
            + "   - 앞서 식별한 Java 실행 파일(ex.: 'java.exe')을 찾아 선택합니다.<br/>"
            + "   - 추가 후, 목록에서 Java 애플리케이션을 선택하고 '고성능(NVIDIA)'을 사용하도록 설정합니다.<br/><br/>"
            + "4. <strong>변경 사항 저장하기:</strong><br/>"
            + "   - 변경 사항을 적용하고 설정 앱을 닫습니다.<br/><br/>"
            + "5. <strong>Minecraft 재시작하기:</strong><br/>"
            + "   - Minecraft를 재시작하여 변경 사항을 적용합니다.<br/><br/>"
            + "Windows 11 또는 Windows Server 2025+를 사용 중인 경우, 최신 NVIDIA 드라이버가 설치되어 있는 한 이러한 단계는 유효합니다.<br/><br/>"
            + "참고: 그래픽 설정 옵션을 찾을 수 없는 경우, NVIDIA 드라이버가 올바르게 설치되어 있는지 확인하세요.";
}



@Override
public String segundo60Tick() {
    return "<b style='color:#" + config.obtenerColorError() + "'>서버 또는 월드의 틱이 60초 이상입니다. 이는 모드가 서버를 느리게 만들거나 하드웨어가 너무 약한 경우일 수 있습니다.</b>";
}




@Override
public String noTieneMemoria() {
    return "<b style='color:#" + config.obtenerColorError() + "'>충분한 RAM/메모리가 없습니다. 더 많은 메모리를 할당해야 합니다.</b>";
}


@Override
public String theseus() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Theseus/ModrinthApp를 사용 중이라면 도와드릴 수 있는 게 많지 않습니다. Theseus에는 런처 콘솔이 없기 때문입니다. Theseus는 또한 오래된 모드 로더 버전, 스파이웨어, 잘못된 로그 등의 문제도 가지고 있습니다. Modrinth 회사는 정직하지 않으며, 모드 개발자들이 다운로드 수를 부풀리기 위해 봇을 사용한다고 허위 주장을 하고 있으며, 수익화 관련 주장도 여러 번 변경했습니다.</b>";
}




}
