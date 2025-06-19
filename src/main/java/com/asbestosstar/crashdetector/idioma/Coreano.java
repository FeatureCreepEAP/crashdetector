package com.asbestosstar.crashdetector.idioma;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;

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
    public String config_spongemixin_problematico() {
        return "<b style='color:#" + config.obtenerColorError() + "'>문제 있는 SpongeMixin 구성 감지: " + "</b>";
    }

    @Override
    public String module_resolution_exception() {
        return "<span style='color:#" + config.obtenerColorError() + "'>중복된 패키지를 가진 모드가 있습니다. JAR 파일에서 중복된 패키지(폴더)를 삭제하여 해결할 수 있습니다. WinRAR나 7z 같은 압축 프로그램으로 JAR 파일을 열 수도 있고, 파일 확장자를 .jar에서 .zip으로 바꾼 후 폴더를 삭제하고 다시 .jar 확장자로 변경할 수도 있습니다.</span>";
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

@Override
public String noTieneConsolaDeLauncherCursedForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>launcher_log.txt 파일이 없습니다. 오류를 검색하려면 이 파일이 필요합니다. 이는 \"런처 시작 건너뛰기\" 옵션 때문입니다. 비활성화하세요.</b>";
}


@Override
public String faltar_de_clases_advertencia() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>경고: 누락된 클래스가 감지되었습니다:</b>";
}


@Override
public String noResultos() {
    return "<b style='color:#" + config.obtenerColorError() + "'>결과 없음</b>";
}

@Override
public String ubicacionesDeLogs() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>로그 위치:</b>";
}

@Override
public String infoDeVerificaciones() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>여기에 검증 결과가 있습니다. 로그의 상단 부분을 수정하는 것이 첫 번째 우선 순위입니다. 천천히 진행하세요.</b>";
}

@Override
public String versionDeJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>1.17-1.20.4 버전은 Java 17을 사용하고, 그 이상의 버전은 Java 21을 사용하세요. 이전 버전은 Java 8을 사용하세요. [가이드](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). 여전히 문제가 있다면 일부 모드 파일이 너무 오래되었거나 너무 최신일 수 있습니다.</b>";
}

@Override
public String java22() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java 22 이상은 ASM이 오래되어 1.20.5 미만의 Minecraft 버전에서는 대부분의 모드 로더에서 작동하지 않습니다.</b>" + versionDeJava();
}

@Override
public String javaObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java는 오래되었습니다 </b>" + versionDeJava();
}

@Override
public String jpms_modules_faltas(String mod_necesitas, String submod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>JPMS 모듈 " + mod_necesitas + " 을(를) " + submod + " 에서 가져와야 합니다</b>";
}

@Override
public String null_pointer_error(String metodo, String objeto) {
    return "<b style='color:#" + config.obtenerColorError() + "'>" + metodo + "을(를) 호출할 수 없습니다. 이유: " + objeto + "이(가) null입니다</b>";
}

@Override
public String analisisAvanzado() {
    return "고급 분석";
}



@Override
public String seleccionarCarpeta() {
    return "폴더 선택";
}

@Override
public String cadenaBusqueda() {
    return "검색 문자열";
}

@Override
public String usarRegex() {
    return "정규식 사용";
}

@Override
public String ignorarMayusculas() {
    return "대소문자 무시";
}

@Override
public String buscar() {
    return "검색";
}

@Override
public String busquedaEnProgreso() {
    return "검색 진행 중";
}

@Override
public String noSeEncontraronResultados() {
    return "결과를 찾을 수 없음";
}

@Override
public String errorBusqueda() {
    return "검색 오류";
}

@Override
public String noRegistroDeLauncher() {
    return "런처 로그를 찾을 수 없습니다! 이로 인해 조사가 복잡해질 수 있습니다.\n"
            + "                \n"
            + "                올바른 로그를 얻으려면:\n"
            + "                - MultiMC/PolyMC/PrismLauncher: 참고: 자동으로 감지된 로그는 올바르지 않습니다.\n"
            + "                  1. 인스턴스 인터페이스 열기\n"
            + "                  2. \"Minecraft Log\" 섹션으로 이동\n"
            + "                  3. 마우스 오른쪽 버튼 클릭 후 내용 복사\n"
            + "                - CurseForgeApp:\n"
            + "                  1. 런처를 건너뛰지 않고 게임 재시작\n"
            + "                  \n";
}

@Override
public String omitirYCerrar() {
    return "건너뛰고 닫기";
}

@Override
public String guardarYCerrar() {
    return "저장하고 닫기";
}

@Override
public String pegaLosRegistrosAqui() {
    return "로그를 여기에 붙여넣기";
}

@Override
public String archivo() {
    return "파일";
}

@Override
public String incluir() {
    return "포함";
}

@Override
public String abrir() {
    return "열기";
}

@Override
public String endpointDeInforme() {
    return "보고서 엔드포인트";
}

@Override
public String sitoDeLogging() {
    return "로그 기록 사이트:";
}

@Override
public String apiDeLogging() {
    return "로그 API:";
}

@Override
public String anonimizarRegistros() {
    return "로그 익명화 (베타)";
}

@Override
public String botonDeCompartirInforme() {
    return "보고서 및 선택한 모든 로그 공유";
}

@Override
public String arco() {
    return "이 대화상자는 SecureLogger API를 사용하여 securelogger.net에 로그를 공유할 수 있게 해줍니다. "
            + "보고서 공유 버튼을 누르면 보고서는 선택된 엔드포인트(기본값 asbestosstar.egoism.jp)(하단에서 변경 가능)로 전송됩니다. 선택한 모든 로그를 보고서와 함께 공유할 수 있습니다. "
            + "업로드하고 싶지 않다면 이 대화상자를 사용하지 마세요! 우리는 공식 엔드포인트(https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb)에서 보고서를 처리하지 않습니다. 허용되지 않은 링크만 제거합니다. 코드는 여기에 있습니다: https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb. 이것은 크래시 정보와 로그 링크를 표시하는 데만 사용됩니다. 그러나 동일한 메서드를 가지고 있지 않을 수 있는 사용자 정의 엔드포인트를 사용할 수도 있습니다. 현재 신고 사이트 " 
            + Config.obtenerInstancia().obtenerSitoDeInformes() + "와 로그 사이트 " 
            + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + "를 사용하고 있습니다. 개별 로그 이름 옆에 있는 공유 버튼을 눌러 보고서 없이 개별 로그를 공유할 수도 있습니다. 로그는 선택한 로그 사이트로 전송됩니다. CrashDetector에는 기본적으로 사용자 이름, UUID, 액세스 토큰, 세션 ID, IP 주소 및 기타 데이터를 제거하려고 시도하는 로그 익명화 기능이 있지만 완벽하지는 않습니다. 또한 모드팩 작성자가 이를 비활성화할 수 있습니다. 하단의 체크박스를 통해 활성화 또는 비활성화할 수 있습니다. 당신은 자신의 데이터 관리자입니다. 어디에 데이터를 업로드할지 결정하는 것은 당신입니다. 로그 사이트는 소유권이 종종 개인 정보 보호를 위해 숨겨져 있는 제3자 소유입니다. 데이터 관리 및 관련된 위험에 대한 모든 책임은 귀하에게 있습니다. CrashDetector 공유 대화상자는 단순히 이를 관리하기 위한 인터페이스입니다. GDPR 및 ARCO에 대해 잘 알고 있어야 합니다.";
}

@Override
public String enlaceDelReporte() {
    return "보고서 링크:";
}


@Override
public String guardarConfigDeCompartir() {
    return "공유 설정 저장";
}


@Override
public String registroDemasiadoGrande() {
    return "로그가 이 로그 사이트에 너무 큽니다. 다른 사이트를 선택하고 다시 시도하십시오.";
}

@Override
public String errorConPublicarRegistro(String error) {
    return "로그 게시 중 오류 발생 " + error;
}

@Override
public String apiDeRegistroNoExiste() {
    return "로그 API가 존재하지 않습니다. 설정에서 로그 API를 변경하십시오.";
}

@Override
public String errorSSL() {
    return "SSL 오류가 발생했습니다. 이는 구형 Java 버전에서 흔히 발생하며, "
            + "기본 Minecraft 런처의 Java 8 버전 및 sun.com과 java.com의 버전에도 포함됩니다. "
            + "이 문제는 MinecraftForge 설치 프로그램의 JAR 파일, 기본 엔드포인트를 사용한 "
            + "CrashDetector 보고서 공유 기능, 인터넷이 필요한 일부 모드 및 일부 로그 사이트에 영향을 미칩니다. "
            + "보고서를 공유하려 할 때 이 문제가 발생하면 스크린샷을 첨부하고 "
            + "Java 8 이전 버전과 호환되는 로그 사이트를 선택하세요.";
}


@Override
public String errorJavaFMLVersion(String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "호환되지 않는 JavaFML 버전: 필요한 버전은 " + requerido 
         + "이지만 발견된 버전은 " + encontrado + "</b>";
}

@Override
public String errorJavaFML_MCForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "주의! JavaFML은 특정 버전의 Minecraft Forge를 필요로 합니다</b>";
}

@Override
public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "JAR 파일 '" + archivoJar + "'은(는) 언어 공급자 '" + proveedor + "'의 버전 '"
         + requerido + "' 이상이 필요하지만, 발견된 버전은 '" + encontrado + "'입니다.</b>";
}

//@Override
//public String advertenciaMalwareFalso() {
//    return "<b style='color:#" + config.obtenerColorError() + "'>"
//         + "경고! Crash Assistant는 가짜 악성코드 감지기입니다. 이 프로그램은 의도적으로 게임 실행을 차단하며, 타겟팅된 모드로 계속 플레이할 자유를 무시합니다. "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>MalwareMod.java 코드 보기</a>   "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>JarInJarHelper.java 코드 보기</a>. "
//         + "현재 리스트에는 이 모드만 있으며, 실제로는 사용자가 변경할 수 있는 기본 로그 사이트만을 대상으로 합니다. 이 기능은 내장된 로그 공유 기능을 명시적으로 선택한 경우에만 작동합니다. CrashAssistant는 어떤 로그 사이트가 설정되었는지 확인하지 않으며, 변경 방법(공유 대화 상자의 하단에 드롭다운 메뉴 있음)도 설명하지 않습니다. 설정된 사이트와 관계없이 CrashAssistant는 게임 실행을 차단합니다. 그들의 메시지에서는 직접 조사하라고 하지만, 실제로 그렇게 하십시오. CrashDetector와 Crash Assistant의 코드를 살펴보고 무엇을 하는지 이해하십시오. 권위에 호소하는 것에 의존하지 마십시오.</b>";
//}

@Override
public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "모드 '" + idMod + "'가 필요한 클래스를 찾지 못해 실패했습니다: '"
         + claseNoEncontrada + "'. 모든 종속성이 올바르게 설치되었는지 확인하십시오.</b>";
}


@Override
public String waterMediaTL() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Watermedia가 TLauncher로 플레이하는 것을 차단했습니다.</b>";
}

@Override
public String optifineObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "사용 중인 Optifine 버전이 Minecraft의 오래된 버전용입니다. 현재 사용 중인 Minecraft 버전에 맞는 Optifine 버전을 사용해야 합니다.</b>";
}

@Override
public String servicioMLNoPudoCargar(String servicio) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "ModLauncher 서비스를 로드하지 못했습니다: </b>" + servicio + ".";
}

@Override
public String errorConJSONDeRegistro(String archivoJar, String recurso) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "JAR 파일 '" + archivoJar + "'의 JSON 파일 '" + recurso + "'을(를) 분석하는 중 오류가 발생했습니다. "
         + "등록에 문제가 있습니다.</b>";
}

@Override
public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
    return "<span style='color:#" + config.obtenerColorError() + "'>" 
        + "오류: 모드 '" + modId + "'는 '" + dependencia + "'의 버전 '" + requerido 
        + "' 이상이 필요하지만 '" + actual + "'이(가) 발견되었습니다."
        + "</span>";
}

@Override
public String gpu_no_compatible() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "당신의 GPU는 이 게임 버전에서 요구하는 OpenGL 버전을 지원하지 않습니다. 드라이버를 업데이트하거나 그래픽 카드를 변경하세요.</b>";
}

@Override
public String recomendacionMemoria() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "게임에 할당된 메모리를 늘리거나 모드/플러그인 사용량을 줄이세요.</b>";
}

@Override
public String error32BitMemoria() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "32비트 JVM이 감지되었습니다: 4GB 이상의 RAM을 사용할 수 없습니다. "
         + "사용 가능한 모든 메모리를 활용하려면 64비트 JVM을 설치하세요.</b>";
}

@Override
public String permGenError() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "PermGen 메모리에 심각한 오류가 발생했습니다. 영구 메모리 공간을 늘리거나 클래스 로드를 줄이세요.</b>";
}


public String errorCompatibilidadJava8() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 8과 최신 버전 간의 호환성 오류</b>";
}

public String errorJava9NoSoportado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 9+는 지원되지 않음 - 이전 Forge 버전에는 Java 8을 사용하세요</b>";
}

public String errorJava8Requerido() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 8이 필요합니다 (버전 52.0). 업데이트하거나 올바르게 설정하세요</b>";
}


@Override
public String errorDeBloqueTeselado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "치명적인 호환성 오류: 이 버전에서는 블록이 지원되지 않습니다. "
         + "모드 및 서버 버전이 호환되는지 확인하세요</b>";
}

@Override
public String errorMonitorLWJGL() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "모니터 설정 오류: "
         + "화면 모드를 설정할 수 없습니다. "
         + "확인하세요:</b>"
         + "<br>- 다중 모니터 설정"
         + "<br>- 최신 그래픽 카드 드라이버"
         + "<br>- 시스템에서 지원하는 해상도";
}

@Override
public String errorOpcionesGCJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 옵션 오류: "
         + "충돌하는 가비지 컬렉터 옵션입니다. "
         + "JVM 매개변수에서 여러 GC 알고리즘을 결합하지 않았는지 확인하세요</b>";
}

@Override
public String errorConfigMCForge() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Forge 설정의 치명적인 오류: "
         + "설정 파일이 손상되거나 불완전합니다. "
         + "'config' 폴더를 삭제하고 게임을 재시작하세요</b>";
}

@Override
public String problema_con_graficas_intel() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Intel HD Graphics 드라이버 오류가 감지되었습니다. 해결책:</b>"
         + "<br>1. <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a>에서 Intel 드라이버를 업데이트하세요 (최소 버전 15.xx.xx.xx)"
         + "<br>2. Minecraft에서: 옵션 → 비디오 → 'Enable VBOs' 및 'VSync' 활성화"
         + "<br>3. 런처에서 게임에 1GB-2GB의 RAM 할당"
         + "<br>4. 업데이트 중 방화벽/안티바이러스를 일시적으로 비활성화";
}

public String nombre_de_faltar_de_clases_advertencia() {
    return "경고: 누락된 클래스가 감지됨";
}

public String nombre_de_bloque_teselado() {
    return "블록 렌더링 오류";
}

public String nombre_de_contento_de_stacktrace() {
    return "스택 트레이스 분석";
}

public String nombre_de_cursed_consola() {
    return "불완전한 CurseForge 콘솔";
}

public String nombre_de_early_window() {
    return "초기 윈도우 오류 (FMLEarlyWindow)";
}

public String nombre_de_drivers() {
    return "비디오 드라이버 문제";
}

public String nombre_de_error_de_config_mcforge() {
    return "MCForge 구성 손상";
}

public String nombre_de_error_de_monitor_lwjgl() {
    return "디스플레이 모드 실패 (LWJGL)";
}

public String nombre_de_fabricmc_runtime_error_provided_by() {
    return "FabricMC 초기화 오류";
}

public String nombre_de_falta_module_jmps() {
    return "누락된 JPMS 모듈";
}

public String nombre_de_faltar_de_clases() {
    return "치명적인 누락 클래스";
}

public String nombre_de_faltas_dependencias_de_modlauncher() {
    return "ModLauncher 의존성 누락";
}

public String nombre_de_java_versiones() {
    return "호환되지 않는 Java 버전";
}

public String nombre_de_faltar_de_kubejs_resourcepack() {
    return "KubeJS 리소스 오류";
}

public String nombre_de_lenguaje_proveedor_check() {
    return "호환되지 않는 언어 제공자";
}

public String nombre_de_faltar_de_liyhostictchctov() {
    return "조선민주주의인민공화국(Litchhost) 관련 오류"; // Referencing DPRK [[1]]
}

public String nombre_de_malware_falso_crash_assistant() {
    return "거짓 악성 소프트웨어 탐지";
}

public String nombre_de_mcforge_mod_sespechoso() {
    return "의심스러운 모드 감지됨";
}

public String nombre_de_mods_duplicados_modlauncher() {
    return "ModLauncher에서 중복된 모드";
}

public String nombre_de_modules_duplicados_jmps() {
    return "JPMS 모듈 충돌";
}

public String nombre_de_necesitas_sodium() {
    return "Iris를 위해 Sodium이 필요함";
}

public String nombre_de_no_puede_analizar_json_de_registro() {
    return "JSON 등록 정보를 분석할 수 없음";
}

public String nombre_de_no_tiene_memoria() {
    return "메모리 부족";
}

public String nombre_de_null_pointer() {
    return "널 포인터 오류 (NullPointerException)";
}

public String nombre_de_opciones_java_gc_invalidas() {
    return "잘못된 Java GC 옵션";
}

public String nombre_de_optifine_obsoleta() {
    return "오래된/호환되지 않는 OptiFine";
}

public String nombre_de_60_segundo_trick() {
    return "위험한 서버 틱 (60초)";
}

public String nombre_de_servicio_de_modlauncher_no_funciona() {
    return "ModLauncher 서비스 실패";
}

public String nombre_de_spongemixin_configs_problematicos() {
    return "문제가 있는 SpongeMixing 구성";
}

public String nombre_de_theseus() {
    return "Theseus 호환되지 않음";
}

public String nombre_de_watermedia_tl() {
    return "TLauncher는 WATERMeDIA에서 지원되지 않음";
}


@Override
public String auditorias_transformer() {
    return "트랜스포머 감사";
}

@Override
public String auditorias_transformer_detectadas() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "이것은 바닐라 런처에서 트랜스포머 감사 내용의 결과입니다. 일반적으로 StackTrace 분석기보다 덜 정확하지만, 바닐라 런처에는 항상 {}의 내용이 있는 것은 아닙니다</b>";
}

@Override
public String descripcionEscanerMCreator() {
    return "이 기능은 MCreator로 만든 모드를 검색합니다. 대부분의 MCreator 모드는 괜찮고 훌륭한 모드도 많지만, 때로는 문제가 발생하고 나쁜 평판을 가질 수 있습니다. 이를 통해 해당 모드를 식별할 수 있습니다. 참고로 매우 높은 평가를 받은 모드라도 반드시 MCreator로 제작된 것은 아닐 수 있습니다. 예를 들어 MCreator와의 통합을 포함할 수 있습니다.";
}

@Override
public String escanear() {
    return "검사";
}

@Override
public String cargando() {
    return "로딩 중";
}

@Override
public String codigo() {
	// TODO Auto-generated method stub
	return "ko";
}

@Override
public String inicioApp() {
    return "게임/앱 시작";
}

@Override
public String adjustesCrashDetector() {
    return "크래시 감지기 설정";
}

@Override
public String confidencialidad() {
    return "개인정보 보호";
}

@Override
public String tooltip() {
    return "툴팁";
}

@Override
public String config() {
    return "설정";
}

@Override
public String abrirCarpeta() {
    return "폴더 열기";
}

@Override
public String actualizar() {
    return "업데이트";
}

@Override
public String anadirRegistro() {
    return "로그 추가";
}

@Override
public String usarIdiomaDelSistema() {
    return "시스템 언어 사용";
}

@Override
public String volver() {
    return "뒤로";
}

@Override
public String colorFondo() {
    return "배경색 (#RRGGBB):";
}

@Override
public String colorTexto() {
    return "텍스트 색상 (#RRGGBB):";
}

@Override
public String colorBoton() {
    return "버튼 색상 (#RRGGBB):";
}

@Override
public String colorCajaTexto() {
    return "텍스트 상자 색상 (#RRGGBB):";
}

@Override
public String colorEnlace() {
    return "링크 색상 (#RRGGBB):";
}

@Override
public String colorTitulosConsolas() {
    return "콘솔 제목 색상 (#RRGGBB):";
}

@Override
public String colorError() {
    return "오류 색상 (#RRGGBB):";
}

@Override
public String colorAdvertencia() {
    return "경고 색상 (#RRGGBB):";
}

@Override
public String colorInfo() {
    return "정보 색상 (#RRGGBB):";
}

@Override
public String colorTitulo() {
    return "제목 색상 (#RRGGBB):";
}

@Override
public String colorEnlaceTexto() {
    return "링크 텍스트 색상 (#RRGGBB):";
}

@Override
public String transformacionDeMinecraftCodigo0() {
    return "오류 발생 시에만 CrashDetector 열기";
}

@Override
public String activar_parche() {
    return "패치 활성화";
}

@Override
public String noHaySolucionDisponible() {
    return "사용 가능한 솔루션이 없습니다";
}

@Override
public String error() {
    return "오류";
}

@Override
public String error_al_eliminar_jar() {
    return "Jar 삭제 중 오류 발생";
}

@Override
public String jar_eliminado_exitosamente() {
    return "Jar이 성공적으로 삭제되었습니다";
}

@Override
public String exito() {
    return "성공";
}

@Override
public String eliminar() {
    return "삭제";
}

@Override
public String error_al_eliminar_paquete() {
    return "패키지 삭제 중 오류 발생";
}

@Override
public String paquete_eliminado_exitosamente() {
    return "패키지가 성공적으로 삭제되었습니다";
}

@Override
public String eliminar_paquete() {
    return "패키지 삭제";
}

@Override
public String no_se_encontraron_mods_con_paquete() {
    return "패키지가 포함된 모드를 찾을 수 없습니다";
}

@Override
public String no_se_puede_eliminar_paquete() {
    return "패키지를 삭제할 수 없습니다";
}

@Override
public String eliminar_jar() {
    return "Jar 삭제";
}

@Override
public String no_se_encontraron_mods_duplicados() {
    return "중복된 모드를 찾을 수 없습니다";
}

@Override
public String archivo_no_encontrado() {
    return "파일을 찾을 수 없음";
}

@Override
public String directorio_eliminado() {
    return "디렉토리 삭제됨";
}

@Override
public String error_al_eliminar_jar_anidado() {
    return "중첩된 Jar 삭제 중 오류 발생";
}

@Override
public String archivo_interno_no_encontrado() {
    return "내부 파일을 찾을 수 없음";
}

@Override
public String archivo_eliminado() {
    return "파일 삭제됨";
}

@Override
public String error_al_eliminar_archivo() {
    return "파일 삭제 중 오류 발생";
}

@Override
public String archivo_externo_no_valido() {
    return "유효하지 않은 외부 파일";
}

@Override
public String elemento_mod_eliminado() {
    return "모드 요소 삭제됨";
}

@Override
public String error_al_reemplazar_jar_externo() {
    return "외부 Jar 교체 중 오류 발생";
}

@Override
public String error_al_eliminar_elemento_mod() {
    return "모드 요소 삭제 중 오류 발생";
}

@Override
public String error_al_eliminar_directorio() {
    return "디렉토리 삭제 중 오류 발생";
}

@Override
public String formato_invalido_para_jar_anidado() {
    return "중첩된 Jar에 유효하지 않은 형식";
}

@Override
public String jar_anidado_eliminado() {
    return "중첩된 Jar 삭제됨";
}

@Override
public String error_al_limpiar_temporales() {
    return "임시 파일 정리 중 오류 발생";
}

@Override
public String mensaje_de_trace_fatal_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>치명적 트레이스 메시지 마지막 (번역되지 않음):</b> ";
}

@Override
public String mensaje_de_trace_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>트레이스 메시지 마지막 (번역되지 않음):</b> ";
}

@Override
public String solucionParaAdvertenciaFaltasClases() {
    return "누락된 클래스가 있습니다 (경고). 일반적으로는 괜찮지만 항상 그런 것은 아닙니다. 잘못된 코어 모드 또는 누락된 종속성은 이 문제의 일반적인 원인입니다.";
}

@Override
public String solucionFaltasClases() {
    return "누락된 클래스가 있습니다 (치명적). 매우 중요합니다. 잘못된 코어 모드 또는 누락된 종속성은 이 문제의 일반적인 원인입니다.";
}

@Override
public String solucionParaJavaInstallar() {
    return "두 런처 모두 올바른 Java 버전을 가지고 있지만 모든 버전이 있는 것은 아닙니다. 시스템의 패키지 관리자나 버튼을 사용하여 올바른 Java 버전을 설치할 수 있습니다.";
}
@Override
public String error_animacion_no_encontrada() {
    return "<b style='color:#" + config.obtenerColorError() + "'>애니메이션이 없는 모드: " + "</b>";
}

@Override
public String nombre_de_error_animacion_minecraft() {
    return "NoSuchElementException(요소 없음 예외) 애니메이션 부재";
}

@Override
public String no_se_encontraron_mods_para_eliminar() {
    return "삭제할 모드를 찾을 수 없음";
}

@Override
public String opcionesGCInvalidas() {
    return "상충되는 GC 옵션을 -XX:+UseG1GC로 교체하기";
}

@Override
public String aumentarMemoriaHeap() {
    return "-Xmx 옵션을 사용하여 힙 메모리 크기 증가시키기.";
}

@Override
public String aumentarMemoriaPermgen() {
    return "-XX:MaxPermSize 옵션을 사용하여 영구 생성 영역 메모리 크기 증가시키기.";
}

@Override
public String utilizarJVM64Bits() {
    return "사용 가능한 메모리를 늘리기 위해 64비트 JVM 사용하기.";
}

@Override
public String optimizarCodigo() {
    return "메모리 사용량을 줄이고 성능을 개선하기 위해 코드 최적화하기.";
}

@Override
public String utilizarRecolectorBasuraEficiente() {
    return "애플리케이션의 일시 중지를 줄이기 위해 효율적인 가비지 컬렉터 사용하기.";
}

@Override
public String modulos() {
    return "모듈들";
}

@Override
public String paquete() {
    return "패키지";
}

@Override
public String solucionRegistrosMalMapeados() {
    return "ID가 누락되었습니다. 흔한 원인으로는 누락된 모드나 아이템 데이터 부족이 있습니다. 일반적인 데이터 폴더는 datafiedcontents/와 kubejs/ 또는 다른 모드 폴더입니다.";
}

@Override
public String nombre_de_registros_mal_mapeados() {
    return "잘못 매핑된 기록들";
}








}
