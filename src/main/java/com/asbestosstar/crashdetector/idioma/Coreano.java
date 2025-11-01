package com.asbestosstar.crashdetector.idioma;

import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;

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
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") 종료되었습니다!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>JVM 없음</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>ATI/AMD 드라이버를 업데이트하면 도움이 될 수 있습니다. 이를 해결하려면 다음 가이드를 읽으세요: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>드라이버 업데이트 가이드</a> https://www.amd.com/ko/support/download/drivers.html 다운로드 </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>일부旧버전에서 Nouveau 그래픽 카드의 초기 로드 화면에서때때로문제가 발생합니다.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>그래픽 카드 드라이버에 문제가 있습니다. AMD/ATI GPU 또는 APU를 사용하는 경우 AMD 그래픽 드라이버를 업데이트하십시오. NVIDIA 그래픽 카드를 사용하는 경우 게임 및 모든 javaw.exe 인스턴스를獨立显卡로 설정하십시오. 이 가이드를 읽으십시오: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>드라이버 업데이트 가이드</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>FML 초기 창 로드에 실패했습니다. 수정하려면 (.minecraft/config/fml.toml)로 이동하여 earlyWindowProvider를 \"none\"으로 설정하십시오. Mac M1을 사용하는 경우 Intel x64 버전이 아닌 ARM 버전 Java를 사용 중인지 확인하십시오. 이것은 또한 드라이버가 오래된 경우의 일반적인 문제입니다. Windows를 사용 중이고 이 설정을 사용하지 않으면 이 가이드를 참조하십시오: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>드라이버 업데이트 가이드</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError() + "'>필요한 의존성이 없습니다:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "요청자").replace("Expected range", "예상 범위") + "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>CrashDetector 보고서는 여기에서 확인할 수 있습니다 <a href='"
				+ archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>보고서 보기</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>CrashDetector의 GUI 인터페이스입니다. 게임이 정상적으로 종료된 경우 이 인터페이스를 무시하십시오.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>보고서 보기</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>브라우저에서 로컬 보고서를 확인합니다.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "보고서 공유";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "보고서 공유. 로그가 securelogger.net에 업로드되고 보고서가 다른 사이트에 업로드됩니다.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>문제 있는 JAR 파일 감지(FATAL 우선, 그 다음은 고우선순위 및 低우선순위):</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> 등급:</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>치명적일 수 있음:</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>문제 있는 ModID 감지(FATAL 우선, 그 다음은 低우선순위 및 低우선순위):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>문제 있는 패키지 감지(FATAL 우선, 그 다음은 低우선순위 및 低우선순위):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>치명적인 클래스(FATAL) 오류가 있습니다. 매우 심각한 문제이며, 주로 잘못된 CoreMod 또는 치명적인 의존성 문제에서 발생합니다. QuickFix를 사용하여 치명적 클래스를 가진 모드를 탐지할 수 있습니다. 감지된 누락된 치명적 클래스:</b>";
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
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>중복된 패키지를 가진 모드가 있습니다. JAR 파일에서 중복된 패키지(폴더)를 삭제하여 해결할 수 있습니다. WinRAR나 7z 같은 압축 프로그램으로 JAR 파일을 열 수도 있고, 파일 확장자를 .jar에서 .zip으로 바꾼 후 폴더를 삭제하고 다시 .jar 확장자로 변경할 수도 있습니다.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>중복 Mods 감지</b> "
				+ linea.replace("from mod files", "mod 파일에서");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge에서 의심스러운 mod 문제 감지:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV에는 lithostitched가 필요합니다. 여기에서 설치할 수 있습니다: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Iris 셰이더 또는 Oculus를 사용하려면 SODIUM 또는 다른 로더의 호환 버전(Rubidium, Embedium, Bedium)이 필요합니다</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>KubeJS 확장에 문제가 있습니다 </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Windows 11 이전 버전에서 NVIDIA 드라이버 문제 감지됨."
				+ "</span><br/><br/>" + "Minecraft(및 현재 JVM)이 전용 NVIDIA GPU를 사용하도록 하려면 다음 단계를 따르세요:<br/><br/>"
				+ "1. <strong>Java 실행 파일 식별하기:</strong><br/>" + "   - 이 프로그램은 다음 Java 실행 파일을 사용 중입니다: "
				+ obtenerRutaJava() + "<br/>"
				+ "   - 특정 경로가 보이지 않는 경우, 시스템에서 'java.exe'를 검색하여 Java 실행 파일을 찾을 수 있습니다.<br/><br/>"
				+ "2. <strong>NVIDIA 제어판 열기:</strong><br/>"
				+ "   - 바탕 화면에서 마우스 오른쪽 버튼을 클릭하고 'NVIDIA 제어판'을 선택하세요.<br/><br/>"
				+ "3. <strong>선호하는 GPU 설정하기:</strong><br/>" + "   - NVIDIA 제어판에서 '3D 설정 관리'로 이동합니다.<br/>"
				+ "   - '프로그램 설정' 옵션을 선택합니다.<br/>" + "   - '추가'를 클릭하고 앞서 식별한 Java 실행 파일(ex.: 'java.exe')을 찾습니다.<br/>"
				+ "   - '고성능 NVIDIA 프로세서'를 사용하도록 설정되었는지 확인합니다.<br/><br/>" + "4. <strong>변경 사항 저장하기:</strong><br/>"
				+ "   - 변경 사항을 적용하고 NVIDIA 제어판을 닫습니다.<br/><br/>" + "5. <strong>Minecraft 재시작하기:</strong><br/>"
				+ "   - Minecraft를 재시작하여 변경 사항을 적용합니다.<br/><br/>"
				+ "Windows Server 2022 또는 Windows 10을 사용 중인 경우, 최신 NVIDIA 드라이버가 설치되어 있는 한 이러한 단계는 유효합니다.<br/><br/>"
				+ "참고: NVIDIA 제어판을 찾을 수 없는 경우, NVIDIA 드라이버가 올바르게 설치되어 있는지 확인하세요.";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Windows 11/Server 2025 이후 버전에서 NVIDIA 드라이버 문제 감지됨." + "</span><br/><br/>"
				+ "Minecraft(및 현재 JVM)이 전용 NVIDIA GPU를 사용하도록 하려면 다음 단계를 따르세요:<br/><br/>"
				+ "1. <strong>Java 실행 파일 식별하기:</strong><br/>" + "   - 이 프로그램은 다음 Java 실행 파일을 사용 중입니다: "
				+ obtenerRutaJava() + "<br/>"
				+ "   - 특정 경로가 보이지 않는 경우, 시스템에서 'java.exe'를 검색하여 Java 실행 파일을 찾을 수 있습니다.<br/><br/>"
				+ "2. <strong>설정 앱 열기:</strong><br/>" + "   - <code>Win + I</code> 키를 눌러 설정 앱을 엽니다.<br/>"
				+ "   - <strong>시스템 > 디스플레이 > 그래픽</strong>으로 이동합니다.<br/><br/>"
				+ "3. <strong>선호하는 GPU 설정하기:</strong><br/>" + "   - '그래픽' 섹션에서 '기본 그래픽 설정'을 클릭합니다.<br/>"
				+ "   - '데스크톱 앱'을 선택하고 '찾아보기'를 클릭합니다.<br/>" + "   - 앞서 식별한 Java 실행 파일(ex.: 'java.exe')을 찾아 선택합니다.<br/>"
				+ "   - 추가 후, 목록에서 Java 애플리케이션을 선택하고 '고성능(NVIDIA)'을 사용하도록 설정합니다.<br/><br/>"
				+ "4. <strong>변경 사항 저장하기:</strong><br/>" + "   - 변경 사항을 적용하고 설정 앱을 닫습니다.<br/><br/>"
				+ "5. <strong>Minecraft 재시작하기:</strong><br/>" + "   - Minecraft를 재시작하여 변경 사항을 적용합니다.<br/><br/>"
				+ "Windows 11 또는 Windows Server 2025+를 사용 중인 경우, 최신 NVIDIA 드라이버가 설치되어 있는 한 이러한 단계는 유효합니다.<br/><br/>"
				+ "참고: 그래픽 설정 옵션을 찾을 수 없는 경우, NVIDIA 드라이버가 올바르게 설치되어 있는지 확인하세요.";
	}

	@Override
	public String segundo60Tick() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>서버 또는 월드의 틱이 60초 이상입니다. 이는 모드가 서버를 느리게 만들거나 하드웨어가 너무 약한 경우일 수 있습니다.</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError() + "'>충분한 RAM/메모리가 없습니다. 더 많은 메모리를 할당해야 합니다.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Theseus/ModrinthApp를 사용 중이라면 도와드릴 수 있는 게 많지 않습니다. Theseus에는 런처 콘솔이 없기 때문입니다. Theseus는 또한 오래된 모드 로더 버전, 스파이웨어, 잘못된 로그 등의 문제도 가지고 있습니다. Modrinth 회사는 정직하지 않으며, 모드 개발자들이 다운로드 수를 부풀리기 위해 봇을 사용한다고 허위 주장을 하고 있으며, 수익화 관련 주장도 여러 번 변경했습니다.</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>launcher_log.txt 파일이 없습니다. 오류를 검색하려면 이 파일이 필요합니다. 이는 \"런처 시작 건너뛰기\" 옵션 때문입니다. 비활성화하세요.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>경고: 누락된 클래스가 감지되었습니다(경고 수준). 일반적으로 괜찮지만 항상 안전한 것은 아닙니다. 치명적인 클래스 오류와는 다릅니다. 주요 원인으로는 잘못된 CoreMod 또는 누락된 의존성 등이 있습니다. QuickFix를 사용하여 누락된 클래스가 있는 모드를 탐지할 수 있습니다. 감지된 누락된 클래스:</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>결과 없음</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>로그 위치:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>여기에 귀하의 검사 결과가 있습니다. 트레이스 상단 부분을 수정하는 것이 최우선입니다. 천천히 진행하세요. 일반적으로 올바른 원인은 검사 1번 또는 2번에 있습니다. 나머지(오류 3번 이상)는 확인용으로 사용할 수 있지만, 대부분 연쇄 오류이므로 무시해도 됩니다. 오류는 여러 계층에서 발생하므로 올바른 문제를 해결하면 오늘 이 특정 오류는 사라지지만, 내일 또 다른 관련 없는 새로운 오류가 나타날 수 있습니다. 한 오류가 콘솔에 다른 오류의 출력을 막는 경우가 많기 때문입니다.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>1.17-1.20.4 버전은 Java 17을 사용하고, 그 이상의 버전은 Java 21을 사용하세요. 이전 버전은 Java 8을 사용하세요. [가이드](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). 여전히 문제가 있다면 일부 모드 파일이 너무 오래되었거나 너무 최신일 수 있습니다.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 이상은 ASM이 오래되어 1.20.5 미만의 Minecraft 버전에서는 대부분의 모드 로더에서 작동하지 않습니다.</b>" + versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java는 오래되었습니다 </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>JPMS 모듈 " + mod_necesitas + " 을(를) " + submod
				+ " 에서 가져와야 합니다</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + metodo + "을(를) 호출할 수 없습니다. 이유: " + objeto
				+ "이(가) null입니다</b>";
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
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado()
				+ "를 사용하고 있습니다. 개별 로그 이름 옆에 있는 공유 버튼을 눌러 보고서 없이 개별 로그를 공유할 수도 있습니다. 로그는 선택한 로그 사이트로 전송됩니다. CrashDetector에는 기본적으로 사용자 이름, UUID, 액세스 토큰, 세션 ID, IP 주소 및 기타 데이터를 제거하려고 시도하는 로그 익명화 기능이 있지만 완벽하지는 않습니다. 또한 모드팩 작성자가 이를 비활성화할 수 있습니다. 하단의 체크박스를 통해 활성화 또는 비활성화할 수 있습니다. 당신은 자신의 데이터 관리자입니다. 어디에 데이터를 업로드할지 결정하는 것은 당신입니다. 로그 사이트는 소유권이 종종 개인 정보 보호를 위해 숨겨져 있는 제3자 소유입니다. 데이터 관리 및 관련된 위험에 대한 모든 책임은 귀하에게 있습니다. CrashDetector 공유 대화상자는 단순히 이를 관리하기 위한 인터페이스입니다. GDPR 및 ARCO에 대해 잘 알고 있어야 합니다.";
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
				+ "보고서를 공유하려 할 때 이 문제가 발생하면 스크린샷을 첨부하고 " + "Java 8 이전 버전과 호환되는 로그 사이트를 선택하세요.";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "호환되지 않는 JavaFML 버전: 필요한 버전은 " + requerido
				+ "이지만 발견된 버전은 " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "주의! JavaFML은 특정 버전의 Minecraft Forge를 필요로 합니다</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JAR 파일 '" + archivoJar + "'은(는) 언어 공급자 '"
				+ proveedor + "'의 버전 '" + requerido + "' 이상이 필요하지만, 발견된 버전은 '" + encontrado + "'입니다.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "모드 '" + idMod + "'가 필요한 클래스를 찾지 못해 실패했습니다: '"
				+ claseNoEncontrada + "'. 모든 종속성이 올바르게 설치되었는지 확인하십시오.</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Watermedia가 TLauncher로 플레이하는 것을 차단했습니다.</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "사용 중인 Optifine 버전이 Minecraft의 오래된 버전용입니다. 현재 사용 중인 Minecraft 버전에 맞는 Optifine 버전을 사용해야 합니다.</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ModLauncher 서비스를 로드하지 못했습니다: </b>" + servicio
				+ ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JAR 파일 '" + archivoJar + "'의 JSON 파일 '"
				+ recurso + "'을(를) 분석하는 중 오류가 발생했습니다. " + "등록에 문제가 있습니다.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "오류: 모드 '" + modId + "'는 '" + dependencia
				+ "'의 버전 '" + requerido + "' 이상이 필요하지만 '" + actual + "'이(가) 발견되었습니다." + "</span>";
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
				+ "32비트 JVM이 감지되었습니다: 4GB 이상의 RAM을 사용할 수 없습니다. " + "사용 가능한 모든 메모리를 활용하려면 64비트 JVM을 설치하세요.</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "PermGen 메모리에 심각한 오류가 발생했습니다. 영구 메모리 공간을 늘리거나 클래스 로드를 줄이세요.</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Java 8과 최신 버전 간의 호환성 오류</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "치명적인 호환성 오류: 이 버전에서는 블록이 지원되지 않습니다. "
				+ "모드 및 서버 버전이 호환되는지 확인하세요</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "모니터 설정 오류: " + "화면 모드를 설정할 수 없습니다. "
				+ "확인하세요:</b>" + "<br>- 다중 모니터 설정" + "<br>- 최신 그래픽 카드 드라이버" + "<br>- 시스템에서 지원하는 해상도";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Java 옵션 오류: " + "충돌하는 가비지 컬렉터 옵션입니다. "
				+ "JVM 매개변수에서 여러 GC 알고리즘을 결합하지 않았는지 확인하세요</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Forge 설정의 치명적인 오류: " + "설정 파일이 손상되거나 불완전합니다. "
				+ "'config' 폴더를 삭제하고 게임을 재시작하세요</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Intel HD Graphics 드라이버 오류가 감지되었습니다. 해결책:</b>"
				+ "<br>1. <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a>에서 Intel 드라이버를 업데이트하세요 (최소 버전 15.xx.xx.xx)"
				+ "<br>2. Minecraft에서: 옵션 → 비디오 → 'Enable VBOs' 및 'VSync' 활성화" + "<br>3. 런처에서 게임에 1GB-2GB의 RAM 할당"
				+ "<br>4. 업데이트 중 방화벽/안티바이러스를 일시적으로 비활성화";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "경고: 누락된 클래스가 감지됨";
	}

	public String nombre_de_bloque_teselado() {
		return "블록 렌더링 오류";
	}

	public String nombre_de_contenido_de_stacktrace() {
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
	public String ajustesCrashDetector() {
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
		return "WaifuNeoForge 데이터베이스에서 모드를 검색할 수 있습니다. 게임 버전, 모드 로더 및 클래스를 선택하세요. 가장 유사한 조합을 사용하세요. 1분에 한 번만 검색할 수 있습니다.";
	}

	@Override
	public String solucionFaltasClases() {
		return "WaifuNeoForge 데이터베이스에서 모드를 검색할 수 있습니다. 게임 버전, 모드 로더 및 클래스를 선택하세요. 가장 유사한 조합을 사용하세요. 1분에 한 번만 검색할 수 있습니다.";
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

	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError() + "'>플러그인 'AuthMe'의 로드 실패로 인해 서버가 종료되었습니다.</b> ";
	}

	public String nombreProblemaCierreAuthMe() {
		return "AuthMe로 인한 종료 문제";
	}

	public String solucionCierreAuthMe() {
		return "'stopServer' 규칙이 'true'로 변경됨.";
	}

	public String solucionConfigurarPluginAuthMe() {
		return "플러그인 AuthMe 설정 (plugins/AuthMe/config.yml)";
	}

	public String solucionInstalarVersionDiferenteAuthMe() {
		return "'AuthMe' 플러그인의 다른 버전 설치";
	}

	public String solucionEliminarPluginAuthMe() {
		return "플러그인 'AuthMe' 제거";
	}

	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>세계 '" + nombreMundo
				+ "'은(는) 오류가 포함되어 있고 손상되었을 가능성이 있어 로드할 수 없습니다.</b> ";
	}

	@Override
	public String nombreProblemaCargaMultiverso() {
		return "Multiverse 세계 로드 문제";
	}

	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "'" + nombreMundo + "' 세계를 복구하세요. 예: Minecraft Region Fixer 또는 MCEdit 사용.";
	}

	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "'" + nombreMundo + "' 세계 폴더 삭제.";
	}

	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError() + "'>확장기능 'PermissionsEx'의 설정이 잘못되었습니다.</b> ";
	}

	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "PermissionsEx 설정 문제";
	}

	@Override
	public String solucionConfigurarPermissionsEx() {
		return "PermissionsEx 플러그인 설정 (plugins/PermissionsEx/permissions.yml)";
	}

	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "플러그인 'PermissionsEx' 삭제";
	}

	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>동일한 이름 '" + nombrePlugin
				+ "'을 가진 플러그인 파일이 여러 개 있습니다: '" + primerPath + "' 및 '" + segundoPath + "'.</b> ";
	}

	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "모호한 플러그인 이름 문제";
	}

	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "'" + nombrePlugin + "' 플러그인 제거";
	}

	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError() + "'>세계 청크를 로드하는 중 오류가 발생했습니다.</b> ";
	}

	@Override
	public String nombreProblemaCargaChunk() {
		return "청크 로딩 오류";
	}

	@Override
	public String solucionEliminarChunk() {
		return "MCEdit 사용 또는 지역 파일 삭제를 통해 문제가 있는 청크를 제거하십시오.";
	}

	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>/" + comando + "' 명령을 실행할 수 없습니다. 플러그인: '"
				+ nombrePlugin + "'</b>";
	}

	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "플러그인 명령 실행 오류";
	}

	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "'" + nombrePlugin + "' 플러그인의 다른 버전 설치";
	}

	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombrePlugin + "' 플러그인은 '" + dependencia
				+ "' 의존성을 필요로 합니다.</b> ";
	}

	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombrePlugin + "' 플러그인은 다음 의존성이 부족합니다: "
				+ deps.toString() + ".</b> ";
	}

	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "부족한 플러그인 의존성";
	}

	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "'" + nombrePlugin + "' 플러그인 설치";
	}

	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombrePlugin + "' 플러그인은 서버와 호환되지 않는 API 버전 '"
				+ versionAPI + "'을(를) 필요로 합니다.</b> ";
	}

	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "호환되지 않는 API 버전";
	}

	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "서버 소프트웨어의 '" + version + "' 버전을 설치하세요.";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombreMundo
				+ "' 세계는 다른 세계와 중복되어 불러올 수 없습니다.</b> ";
	}

	@Override
	public String nombreProblemaMundoDuplicado() {
		return "중복된 세계";
	}

	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "'" + nombreMundo + "' 세계에서 'uid.dat' 파일 삭제";
	}

	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "'" + nombreMundo + "' 세계 폴더 삭제";
	}

	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "<b style='color:#" + config.obtenerColorError() + "'>" + coords + " 위치의 블록 엔티티 '" + nombre + "'(유형: '"
				+ tipo + "')가 틱 중에 오류를 일으키고 있습니다.</b> ";
	}

	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "문제 있는 블록 엔티티";
	}

	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "'" + coords + " 위치의 '" + nombre + "' 엔티티를 MCEdit 또는 직접 편집하여 삭제하십시오.";
	}

	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>모드 '" + nombreMod + "'이(가) 여러 버전 설치되어 있습니다.</b>";
	}

	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "Fabric에서 중복된 모드";
	}

	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "중복된 모드 파일 삭제: " + rutaMod;
	}

	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>모드 '" + primerMod + "'과(와) '" + segundoMod
				+ "'은(는) 서로 호환되지 않습니다.</b>";
	}

	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "Fabric에서 호환되지 않는 모드";
	}

	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "'" + nombreMod + "' 모드 삭제";
	}

	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>모드 '" + nombreMod
				+ "'에 치명적인 오류가 있어 실행할 수 없습니다.</b>";
	}

	@Override
	public String nombreProblemaModFatal() {
		return "치명적 오류를 가진 모드";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>다음 모드들이 필요하지만 설치되지 않았습니다: " + deps.toString()
				+ ".</b>";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>" + nombreMod + "' 모드는 '" + dependencia
					+ "' 모드 의존성이 필요합니다.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>" + nombreMod + "' 모드는 '" + dependencia
					+ "' 모드의 버전 " + version + "이(가) 필요합니다.</b>";
		}
	}

	@Override
	public String nombreProblemaDependenciaMod() {
		return "부족한 모드 의존성";
	}

	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "'" + nombreMod + "' 모드를 설치하세요";
	}

	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "'" + nombreMod + "' 모드의 버전 " + version + "을(를) 설치하세요";
	}

	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombrePlugin
				+ "' 플러그인은 Folia의 지역 틱킹과 호환되지 않습니다.</b> ";
	}

	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("다음 플러그인들은 Folia의 지역 틱킹과 호환되지 않습니다: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaPluginTickingRegional() {
		return "지역 틱킹과 호환되지 않는 플러그인";
	}

	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "지역 틱킹이 없는 버전을 설치하세요. 예: " + software;
	}

	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>/" + nombreMod + "' 모드가 데이터팩에 없습니다.</b>";
	}

	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("데이터팩에서 다음 모드를 찾을 수 없습니다: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" 및 ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaModFaltanteEnDatapack() {
		return "데이터팩에 모드가 없음";
	}

	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombreMod + " 모드에서 오류 발생</b>";
	}

	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("다음 모드에서 오류가 발생했습니다: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" 및 ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaModExcepcion() {
		return "Forge 모드에 예외 발생";
	}

	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "'" + nombreMod + "' 모드의 다른 버전 설치";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombreMod + "' 모드는 마인크래프트 " + versionMC
				+ "과(와) 호환되지 않습니다.</b>";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("다음 모드는 해당 마인크래프트 버전과 호환되지 않음: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (마인크래프트 ").append(versionesMC.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaModIncompatibleConMinecraft() {
		return "마인크래프트와 호환되지 않는 모드";
	}

	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "Minecraft " + versionMC + "과(와) 호환되는 Forge 버전을 설치하십시오.";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombreMod
				+ " 모드가 없습니다. 세계 또는 플러그인 로드에 필요합니다.</b>";
	}

	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "부족한 모드";
	}

	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>세계를 저장할 때 '" + nombreMod
				+ "' 모드가 사용되었으나, 지금은 찾을 수 없습니다.</b>";
	}

	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("세계를 저장할 때 다음 모드들이 사용되었으나, 지금은 찾을 수 없습니다: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" 및 ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaWorldModFaltante() {
		return "세계에서 부족한 모드";
	}

	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombreMod + "' 모드가 버전 " + versionEsperada
				+ "으로 저장되었으나 현재 실행 중인 버전은 " + versionActual + "입니다.</b>";
	}

	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("다음 모드들은 저장된 세계와 버전이 맞지 않습니다: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" 및 ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (저장됨: ").append(versionesEsperadas.get(i)).append(", 현재: ").append(versionesActuales.get(i))
					.append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaVersionModMundo() {
		return "저장된 세계의 모드 버전 불일치";
	}

	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError() + "'>더 최신 버전의 마인크래프트로 생성된 세계를 로드하려고 했습니다.</b>";
	}

	@Override
	public String nombreProblemaVersionDowngrade() {
		return "더 최신 버전의 세계를 로드하려는 시도";
	}

	@Override
	public String solucionVersionDowngrade() {
		return "더 최신 버전의 마인크래프트를 설치하세요.";
	}

	@Override
	public String solucionGenerarNuevoMundo() {
		return "새로운 세계를 생성하세요.";
	}

	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombrePlugin + "' 플러그인은 '" + dependencia
				+ "' 플러그인 의존성이 필요합니다.</b>";
	}

	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("다음 플러그인들은 설치되지 않은 의존성들이 있습니다: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(plugins.get(i)).append("' (").append(dependencias.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaDependenciaPluginFaltante() {
		return "부족한 플러그인 의존성";
	}

	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombrePlugin
				+ "' 플러그인은 서버의 현재 버전과 호환되지 않습니다.</b>";
	}

	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("다음 플러그인들은 서버의 현재 버전과 호환되지 않습니다: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" 및 ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaPluginIncompatible() {
		return "플러그인이 PocketMine-MP와 호환되지 않음";
	}

	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + nombrePlugin + " 플러그인 실행 도중 오류 발생</b>";
	}

	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("다음 플러그인들이 실행 중에 오류를 발생시켰습니다: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" 및 ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaPluginEjecucion() {
		return "실행 오류가 있는 플러그인";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "여러 스레드 사용하는 LegacyRandomSource";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>여러 스레드가 LegacyRandomSource 클래스에 접근하고 있습니다. 'Unsafe World Random Access Detector' 또는 'C2ME' 모드로 자세한 정보를 확인할 수 있습니다.</b>";
	}

	@Override
	public String desdeUltimoExito() {
		return "마지막 성공 이후";
	}

	@Override
	public String noHayCambios() {
		return "변경 사항 없음";
	}

	@Override
	public String desdeUltimoIntento() {
		return "마지막 시도 이후";
	}

	@Override
	public String fallo() {
		return "실패";
	}

	@Override
	public String diferentesDeLasMods() {
		return "모드와 다름";
	}

	@Override
	public String historialDeMods() {
		return "모드 기록";
	}

	@Override
	public String archivo0() {
		return "파일0";
	}

	@Override
	public String archivo1() {
		return "파일1";
	}

	@Override
	public String comparar() {
		return "비교하기";
	}

	@Override
	public String seleccionarDosArchivos() {
		return "두 파일 선택";
	}

	@Override
	public String archivoExito() {
		return "성공한 파일";
	}

	@Override
	public String archivoFalla() {
		return "실패한 파일";
	}

	@Override
	public String errorComparandoArchivos() {
		return "파일 비교 중 오류";
	}

	@Override
	public String comparando() {
		return "비교 중";
	}

	@Override
	public String con() {
		return "와(과)";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>" + "<p><b>모드 이력 비교 패널</b></p>"
				+ "<p>이 패널을 사용하면 서로 다른 실행 세션의 두 모드 목록을 비교할 수 있습니다. 좌측에서 하나, 우측에서 다른 파일을 선택해 변경된 내용을 확인하세요.</p>"

				+ "<h3>사용 방법:</h3>" + "<ol>" + "<li><b>파일 선택:</b> 파일 이름 옆의 라디오 버튼을 클릭하세요. "
				+ "<span style='color: #4CAF50; font-weight: bold;'>.성공</span>으로 끝나는 파일은 성공한 세션입니다. "
				+ "<span style='color: #F44336; font-weight: bold;'>.실패</span>는 오류가 발생한 세션입니다.</li>"

				+ "<li><b>자동 비교:</b> '비교' 버튼을 누르면 시스템이 두 목록을 분석하여 추가(+) 또는 제거(-)된 모드를 보여줍니다.</li>"

				+ "<li><b>결과 보기:</b> 결과는 색상 코드로 구분된 HTML 형식으로 표시됩니다: " + "<ul>"
				+ "<li><span style='color: green;'>+ 추가된 모드</span></li>"
				+ "<li><span style='color: red;'>- 제거된 모드</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>주요 기능:</h3>" + "<ul>" + "<li>성공/실패 파일 조합 지원</li>" + "<li>정확한 추적을 위한 양방향 차이점 표시</li>"
				+ "<li>긴 모드 목록을 위한 스크롤 지원</li>" + "<li>시각적 이해를 돕는 설명 이미지 통합</li>" + "</ul>"

				+ "<p>설정 변경 사항을 추적할 수 있도록 <3️와 함께 개발되었습니다.</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "IPv6 관련 문제가 있을 수 있습니다. " + "해결 방법은 두 가지입니다: "
				+ "1) 런처에 JVM 인수 <code>-Djava.net.preferIPv4Stack=true</code>를 추가하거나, "
				+ "2) CrashDetector에서 'QuickFix' 버튼을 눌러 자동으로 설정을 활성화하는 패치를 적용하세요." + "</b>";
	}

	@Override
	public String parcheIPv4() {
		return "IPv4/6 패치";
	}

	@Override
	public String carpetaHMCL() {
		return "HMCL 폴더 (HelloMineCraftLauncher 전용)";
	}

	@Override
	public String descripcionCurseforge() {
		return "주의: 설정 > 마인크래프트에서 '런처 건너뛰기'가 활성화되면 로그 파일이 생성되지 않습니다.";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/파생된 런처: 인스턴스를 우클릭하고 '인스턴스 편집'을 선택합니다. 열린 창에서 '마인크래프트 로그' 또는 유사한 항목을 찾으세요.<br>"
				+ "이러한 로그는 오류 진단에 필수적인 표준 출력(STDOUT)을 포함합니다.";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher): HMCL이 설치된 폴더를 선택하고 '.hmcl' 폴더를 지정해야 합니다. HMCL 로그는 이 위치에 저장되며 중요한 오류 정보를 담고 있습니다.<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: 런처에는 상세한 로그를 보여주는 개발 탭이 있습니다. 런처 설정 메뉴에서 이 탭을 찾을 수 있습니다.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: 팝업 창에 로그가 표시됩니다. 복사 및 업로드 버튼이 있으며, 게임 시작 시 자동으로 생성되고 중요한 진단 정보를 담고 있습니다.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: 인스턴스를 우클릭하여 '설정'을 클릭한 다음 로그 섹션에서 표준 출력(STDOUT)의 중요한 정보를 확인하세요.";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "Markdown 링크: Markdown 형식의 로그를 포함하는 모든 링크를 여기에 붙여넣으세요. 시스템은 로그 링크(latest.log, launcher_log.txt, debug.log 등)를 자동으로 추출하고 분석할 것입니다.";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "런처 로그를 찾을 수 없음";
	}

	@Override
	public String imagenNoEncontrada() {
		return "이미지를 찾을 수 없음";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "일반: 사용 중인 런처 종류를 선택하세요. 런처 로그(launcher_log.txt, stdout 등)는 latest.log에 없는 중요한 오류 정보를 포함합니다. CrashDetector가 런처 로그를 읽지 못할 수도 있는데, 로그 파일이 생성되지 않았을 수 있으므로 수동으로 로그를 붙여넣어야 할 수도 있습니다.<br>"
				+ "자세한 내용은 <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">이 문제를 참조하세요</a>. 이러한 로그는 표준 출력(STDOUT)을 포함하며, 많은 오류를 진단하는 데 필요합니다.";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Create에서 누락된 클래스가 감지되었습니다. Create는 많은 변경이 있었으며, 많은 클래스가 제거되었습니다. 특히 Create 6(2025년 2월) 이후로 오래된 Create 버전의 애드온은 더 이상 작동하지 않습니다. QuickFix는 이 문제에 대한 해결책을 제공하지 않습니다. Create 애드온을 업데이트하거나, 더 이상 사용하지 않는 애드온을 제거하거나, 사용하려는 애드온에 맞는 올바른 Create 버전을 사용해야 합니다.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>EpicFight에서 누락된 클래스가 감지되었습니다. EpicFight는 많은 변경이 있었으며, 많은 클래스가 제거되었습니다. QuickFix는 이 문제에 대한 해결책을 제공하지 않습니다. EpicFight 애드온을 업데이트하거나, 더 이상 사용하지 않는 애드온을 제거하거나, 사용하려는 애드온에 맞는 올바른 EpicFight 버전을 사용해야 합니다.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>OpenJ9를 사용하고 계신데, 이 애플리케이션은 OpenJ9를 지원하지 않습니다. 이 앱을 포함한 많은 앱들이 JVM 구현 방식의 차이로 인해 OpenJ9를 지원하지 않습니다. QuickFix로는 이 문제를 자동으로 해결할 수 없습니다. Oracle JDK, OpenJDK Hotspot 또는 기타 권장되는 호환 가능한 JDK를 설치해야 합니다.</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>이 앱의 버전은 정상 작동을 위해 Java 11(JDK 11)이 필요합니다. 현재 사용 중인 Java 버전이 오래되어 호환되지 않습니다. QuickFix는 Java를 자동으로 업데이트할 수 없습니다. 해결 방법에 제공된 링크에서 JDK 11 이상의 호환 가능한 버전을 다운로드하여 설치해야 합니다.</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>너무 많은 메모리를 할당하여 시스템 리소스가 부족해졌습니다. 일반적으로 시스템에 있는 RAM보다 더 많은 메모리를 지정했거나, 대용량 메모리를 처리할 수 없는 32비트 JVM을 사용할 때 발생합니다.</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>이 문제를 해결하려면 앱에 할당된 메모리 양을 줄여야 합니다. 추천 최대치는 시스템에 따라 다릅니다만, 일반적으로 전체 RAM의 70~80%를 초과하지 않아야 합니다. 32비트 JVM을 사용하는 경우 물리적 RAM 양과 관계없이 약 2~3GB가 최대치입니다.</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>앱의 힙(heap) 메모리를 줄이려면, 런처 설정을 열고 RAM 옵션을 찾으세요. 최대값(Xmx)을 더 적절한 수치로 줄이세요. 예를 들어, 8GB RAM을 가진 경우 3~4GB를, 16GB를 가진 경우 6~8GB를 할당할 수 있습니다. 운영체제와 다른 프로그램을 위해 충분한 메모리를 남겨두는 것을 잊지 마세요.</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Forge의 필수 파일이 없습니다. '" + archivo
				+ "' 파일이 설치 폴더에 존재하지 않습니다. 일반적으로 Forge 설치가 중단되거나 중요한 파일이 삭제되었을 때 발생합니다. QuickFix는 이 파일들을 자동으로 복구할 수 없습니다. 공식 설치 프로그램을 사용하여 Forge를 다시 설치해야 합니다.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Forge가 필요한 마인크래프트 버전을 찾을 수 없습니다. 버전 " + version
				+ "이 필요하지만 '" + archivo
				+ "' 파일에서 찾을 수 없습니다. 마인크래프트 버전과 사용 중인 Forge 버전이 맞지 않을 때 발생합니다. 마인크래프트 버전에 맞는 올바른 Forge 버전을 다운로드했는지 확인하세요.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge를 시작하는 데 필요한 'fmlclient' 타겟을 찾을 수 없습니다. 이는 Forge 설치가 불완전하거나 손상되었음을 의미합니다. Forge의 핵심 파일이 제대로 설치되지 않았을 수 있습니다. 공식 설치 프로그램을 사용하여 Forge를 다시 설치해야 합니다.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>클래스 로더에서 마인크래프트 메인 클래스를 찾을 수 없습니다. 이는 Forge 설치가 불완전하거나 다른 모드와 충돌했음을 나타냅니다. Forge 설치 중 마인크래프트 파일이 손상되었을 수 있습니다. Forge를 다시 올바르게 설치해야 합니다.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge 설치가 완료되지 않았습니다. 설치 도중 중단되었거나 파일이 삭제되었거나 마인크래프트 버전과 호환되지 않을 수 있습니다. Forge는 정상 작동을 위해 특정 파일이 필요하며, 일부 파일이 현재 설치에 누락되어 있습니다.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "Forge 설치 미완료";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>이 문제를 해결하려면 Forge를 다시 올바르게 설치하세요. 마인크래프트 버전에 맞는 올바른 버전을 다운로드하고 설치 과정을 중단하지 말고 완료하세요.</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "공식 사이트에서 Forge 다운로드";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "Forge를 올바르게 다시 설치하는 방법";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>Forge 다시 설치 방법:</h3>" + "<ol>" + "<li>공식 사이트에서 마인크래프트 버전에 맞는 Forge 설치 프로그램을 다운로드하세요</li>"
				+ "<li>마인크래프트 런처를 완전히 종료하세요</li>" + "<li>관리자 권한으로 Forge 설치 프로그램을 실행하세요</li>"
				+ "<li>'Installer' 옵션을 선택하세요 ('Installer (run client)' 아님)</li>" + "<li>런처에서 마인크래프트 프로필 폴더를 선택하세요</li>"
				+ "<li>'OK'를 누르고 설치가 완료될 때까지 기다리세요</li>" + "<li>런처를 재시작하고 Forge가 프로필 목록에 나타나는지 확인하세요</li>" + "</ol>"
				+ "<p><b>중요:</b> 사용자 정의 런처를 사용하는 경우 올바른 프로필 폴더를 선택했는지 확인하세요.</p>" + "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "Forge 다시 설치 방법";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError() + "'>링크 오류: 라이브러리 " + nombreBiblioteca
				+ "을(를) 로드하지 못했습니다. 가능한 해결 방법:<br/><br/>"
				+ "a) 공유 라이브러리가 있는 디렉터리를 -Djava.library.path 또는 -Dorg.lwjgl.librarypath에 추가하세요.<br/>"
				+ "b) 공유 라이브러리를 포함하는 JAR 파일을 classpath에 추가하세요.<br/><br/>"
				+ "이 오류는 마인크래프트가 실행에 필요한 핵심 파일을 찾을 수 없을 때 발생합니다. " + "대부분 마인크래프트 설치가 불완전하거나 시스템 권한 문제로 인해 발생합니다.</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "링크 오류";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>라이브러리 로드 실패. 가능한 해결 방법:<br/><br/>"
				+ "a) 공유 라이브러리가 있는 디렉터리를 -Djava.library.path 또는 -Dorg.lwjgl.librarypath에 추가하세요.<br/>"
				+ "b) 공유 라이브러리를 포함하는 JAR 파일을 classpath에 추가하세요.<br/><br/>"
				+ "이러한 기술적 해결책은 고급 사용자를 위한 것입니다. 대부분의 사용자는 이러한 매개변수를 수정하기 전에 " + "마인크래프트를 재설치하는 것이 좋습니다.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ID 충돌: <strong>" + id + "</strong> 번 ID가 <strong>"
				+ modOrigen + "</strong>에 의해 이미 사용 중이며, <strong>" + modDestino
				+ "</strong>을(를) 추가하려고 할 때 발생합니다. 두 개의 모드가 서로 다른 요소에 동일한 ID를 사용하려 할 때 이 문제가 발생합니다.</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>허용된 최대 ID 범위를 초과했습니다. 모드가 마인크래프트 버전에서 허용하는 범위를 벗어난 ID로 블록이나 아이템을 등록하려 할 때 발생합니다.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "ID 충돌";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>이 문제를 해결하려면 마인크래프트 1.12.2에 <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>를 설치하세요. 1.7.10의 경우 <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>를 사용하세요.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ID 충돌을 해결하려면 <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> 또는 <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a>와 같은 도구를 사용하세요.</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "JustEnoughIDs 설치";
	}

	@Override
	public String instalar_endlessids() {
		return "EndlessIDs 설치";
	}

	@Override
	public String usar_idfix_minus() {
		return "IdFix Minus 또는 IdFix 사용";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "Minecraft-ID-Resolver 사용";
	}

	@Override
	public String ver_documentacion_jp() {
		return "일본어 문서 보기";
	}

	@Override
	public String escanearDeMCreator() {
		return "MCreator 스캔";
	}

	@Override
	public String tituloArbolDeMods() {
		return "모듈 및 클래스 트리";
	}

	@Override
	public String tipoBusqueda() {
		return "유형";
	}

	@Override
	public String filtroTodos() {
		return "전체";
	}

	@Override
	public String filtroPaquetes() {
		return "패키지";
	}

	@Override
	public String filtroClases() {
		return "클래스";
	}

	@Override
	public String filtroMetodos() {
		return "메서드";
	}

	@Override
	public String filtroCampos() {
		return "필드";
	}

	@Override
	public String filtroReferenciasCampo() {
		return "필드 참조";
	}

	@Override
	public String filtroReferenciasMetodo() {
		return "메서드 참조";
	}

	@Override
	public String filtroReferenciasClase() {
		return "클래스 참조";
	}

	@Override
	public String tipBuscar() {
		return "모드 트리에서 검색하려면 여기에 입력하세요";
	}

	@Override
	public String botonBuscar() {
		return "검색";
	}

	@Override
	public String botonResetearArbol() {
		return "트리 초기화";
	}

	@Override
	public String modsCargados() {
		return "로드된 모드";
	}

	@Override
	public String clases() {
		return "클래스";
	}

	@Override
	public String metodos() {
		return "메서드";
	}

	@Override
	public String campos() {
		return "필드";
	}

	@Override
	public String referencias() {
		return "참조";
	}

	@Override
	public String resultadosBusqueda() {
		return "검색 결과";
	}

	@Override
	public String buscarReferencias() {
		return "참조 찾기";
	}

	@Override
	public String referenciasMod() {
		return "모드 참조";
	}

	@Override
	public String referenciasClase() {
		return "클래스 참조";
	}

	@Override
	public String referenciasMetodo() {
		return "메서드 참조";
	}

	@Override
	public String referenciasCampo() {
		return "필드 참조";
	}

	@Override
	public String noSeEncontraronReferencias() {
		return "참조를 찾을 수 없습니다";
	}

	@Override
	public String detalleMod() {
		return "모드 정보:";
	}

	@Override
	public String ubicacion() {
		return "위치";
	}

	@Override
	public String nombres() {
		return "이름";
	}

	@Override
	public String modulo() {
		return "모듈";
	}

	@Override
	public String detalleClase() {
		return "클래스 정보:";
	}

	@Override
	public String detalleMetodo() {
		return "메서드 정보:";
	}

	@Override
	public String detalleCampo() {
		return "필드 정보:";
	}

	@Override
	public String clase() {
		return "클래스";
	}

	@Override
	public String tipo() {
		return "유형";
	}

	@Override
	public String referenciasAMetodos() {
		return "메서드 참조:";
	}

	@Override
	public String referenciasACampos() {
		return "필드 참조:";
	}

	@Override
	public String arbolDeMods() {
		return "모드 트리";
	}

	@Override
	public String metodo() {
		return "메서드";
	}

	@Override
	public String campo() {
		return "필드";
	}

	@Override
	public String descompilar() {
		return "디컴파일";
	}

	@Override
	public String exportar() {
		return "내보내기";
	}

	@Override
	public String importar() {
		return "가져오기";
	}

	@Override
	public String errorImportar() {
		return "Ошибка импорта";
	}

	@Override
	public String estructuraImportada() {
		return "Структура импортирована";
	}

	@Override
	public String estructuraExportada() {
		return "Структура экспортирована";
	}

	@Override
	public String errorExportar() {
		return "Ошибка экспорта";
	}

	@Override
	public String exportando() {
		return "내보내는 중";
	}

	@Override
	public String exportacionTardara() {
		return "내보내기에 시간이 걸릴 수 있음";
	}

	@Override
	public String porFavorEspere() {
		return "잠시 기다려 주세요";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>VLC 바이너리가 없습니다. WaterMedia는 VLC 바이너리가 필요합니다. https://www.videolan.org/vlc/ 에서 수동으로 설치해야 합니다.  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "VLC 다운로드";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>치명적 오류: 모듈 이름 '" + nombreModulo
				+ "'에 잘못된 문자가 포함되어 있습니다. '" + parteInvalida
				+ "' 부분은 유효한 Java 식별자가 아닙니다. 자바 예약어('true', 'class' 등)나 이름에 사용할 수 없는 문자를 모드가 사용할 경우 발생합니다.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "모드 이름에 유효하지 않은 문자 포함";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "모드 이름 '" + nombreModulo + "'은(는) '" + parteInvalida
				+ "'을(를) 포함하고 있어 유효하지 않습니다. 이는 자바 예약어이거나 허용되지 않는 문자입니다. "
				+ "로그에서 이 이름과 일치하는 모드를 찾으세요 (일반적으로 JAR 파일 이름)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "모드 폴더로 이동하여 <b>/META-INF/</b> 폴더 내 <b>mods.toml</b> 파일을 편집하세요. "
				+ "<b>modId</b> 값을 자바 예약어 없이 문자, 숫자, 밑줄만 사용하도록 변경하세요";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "유효한 이름 예시: 'true.shot.enchantment' 대신 'truemod_shot_enchantment' 사용. "
				+ "모드 이름에는 점, 하이픈, 자바 예약어('true', 'false', 'class')를 포함할 수 없습니다";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>모드 '" + nombreJar
				+ "'에서 치명적 오류 발생: 의존성에 필수 필드 'mandatory'가 없습니다. mods.toml 파일이 의존성이 필수인지 명시하지 않을 때 발생합니다.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "의존성 모드에 필수 필드 누락";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "문제 있는 모드는 <b>" + nombreJar + "</b>입니다. 이 파일의 의존성 설정에 오류가 있습니다";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "<b>" + nombreJar + "</b> 모드의 <b>/META-INF/</b> 폴더 내 <b>mods.toml</b> 파일을 열기";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "의존성 섹션에서 각 항목이 <b>mandatory=true</b> 또는 <b>mandatory=false</b>를 포함하는지 확인하세요 (예: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>심각한 오류: '" + nombreJar
				+ "' 모드의 access transformer 설정이 잘못되었습니다. 설정 파일의 구문이 잘못되었거나 존재하지 않는 클래스/메서드를 참조할 때 발생합니다.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "잘못된 Access Transformer";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "문제 있는 모드는 <b>" + nombreJar + "</b>입니다. 이 모드는 잘못된 access transformer 설정을 포함하고 있습니다";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "<b>" + nombreJar + "</b> 모드 내부의 <b>accessTransformer.cfg</b> 파일을 엽니다 (일반적으로 JAR 파일의 루트 폴더에 있음)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "access transformer 구문을 수정하세요. 각 줄은 <b>access class.method</b> 형식을 따라야 합니다 (예: public net.minecraft.world.entity.Entity.func_200560_a). 현재 마인크래프트 버전에 존재하지 않는 클래스나 메서드를 참조하는 줄은 삭제하세요";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>심각한 오류: @Mod 주석의 모드 ID와 mods.toml 파일의 ID가 일치하지 않습니다. '" + nombreMod
				+ "' 모드는 ID 불일치로 인해 로드할 수 없습니다.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "@Mod와 mods.toml 간 ID 불일치";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "개발 중인 모드 '" + nombreMod + "'에서 <b>@Mod</b> 주석과 <b>mods.toml</b>의 ID가 일치하지 않습니다";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "기본 클래스의 ID가 <b>/META-INF/mods.toml</b> 파일의 <b>modId</b> 값과 일치하는지 확인하세요. 예: <b>@Mod(\"mymod\")</b>는 <b>modId=\"mymod\"</b>과 일치해야 합니다";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "Gradle을 사용 중이라면 변경 후 <b>clean</b>을 실행하여 리소스가 올바르게 업데이트되도록 하세요. 때때로 이전 파일이 build 폴더에 남아 있을 수 있습니다";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "클라이언트" : "서버";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "서버" : "클라이언트";

		return "<b style='color:#" + config.obtenerColorError() + "'>심각한 오류: '" + nombreClase + "' 클래스를 " + plataforma
				+ " 환경에서 로드하려 하지만, 이 클래스는 " + plataformaOpuesta
				+ "용으로 설계되었습니다. <b>사이드바의 '모드 트리' 기능을 사용해 이 클래스를 로드하려는 모드를 찾으세요</b>. "
				+ "모드는 특정 플랫폼 전용으로 제작되며 다른 플랫폼에서는 작동하지 않습니다.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "잘못된 플랫폼의 모드";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "<b>모드 트리</b> 탭(오른쪽)에서 <b>" + nombreClase + "</b> 클래스에 대한 참조를 검색해 문제를 일으키는 모드를 식별하세요";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "클라이언트" : "서버";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "서버" : "클라이언트";

		return "식별된 모드는 <b>" + plataformaOpuesta + "</b> 전용 모드이며, " + plataforma + " 환경에서 사용하면 안 됩니다.";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "문제 있는 모드를 <b>mods</b> 폴더에서 제거하세요. 이 플랫폼에서 유사한 기능이 필요하다면, "
				+ "<b>클라이언트</b> 또는 <b>서버</b> 전용으로 설계된 대체 모드를 찾아보세요";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("심각한 오류: modid '").append(modIdFaltante).append("'.에 대한 메타데이터가 없습니다. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("다음 모드가 문제를 일으킬 수 있습니다: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", 기타...");
			mensaje.append("</b>. ");
		}

		mensaje.append("모드가 설치되지 않았거나 mods.toml 파일이 잘못된 경우 발생합니다.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "누락된 mods.toml 메타데이터";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("다음 모드가 '").append(modIdFaltante).append("'.에 의존합니다: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", 기타...");
			paso.append("</b>. <b>모드 트리</b> 기능을 사용해 문제 모드를 확인하세요");
			return paso.toString();
		}
		return "'".concat(modIdFaltante)
				.concat("'에 의존하려는 모드가 있지만, 해당 모드가 설치되지 않았습니다. <b>모드 트리</b> 기능을 사용해 문제 모드를 식별하세요");
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "두 가지 선택지가 있습니다:<br/>"
				+ "1. <b>누락된 모드 설치</b>: ID가 '".concat(modIdFaltante).concat("'인 모드를 찾아 설치하세요<br/>")
				+ "2. <b>의존 모드 제거</b>: 기능이 필요하지 않다면, '".concat(modIdFaltante).concat("'에 의존하는 모드를 제거하세요");
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "'".concat(modIdFaltante).concat("'가 라이브러리(예: 'forge', 'minecraft', 'curios')라면, ")
				+ "올바른 버전의 마인크래프트와 포지가 설치되어 있는지 확인하세요. " + "일반 모드라면 다운로드 페이지에서 필수 전제 조건을 확인하세요";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>경고: 사운드 시스템 초기화 실패. 소리와 음악이 비활성화되었습니다. 이 오류는 일반적으로 SoundPhysicsMod과 관련이 있으며 다른 사운드 라이브러리와의 충돌로 인해 발생할 수 있습니다.</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "사운드 시스템 오류";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "이 오류는 일반적으로 <b>SoundPhysicsMod</b>과 관련이 있습니다. 사용 중인 마인크래프트 버전과 호환되는 최신 버전을 설치했는지 확인하세요";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "다른 사운드 모드(예: Sound Filters, Dynamic Surroundings 등)를 사용 중이라면, SoundPhysicsMod을 임시로 제거하여 충돌이 해결되는지 확인해 보세요";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "<b>logs</b> 폴더를 확인하여 LWJGL 또는 OpenAL 관련 추가 메시지를 찾아보세요. 이는 기본 사운드 라이브러리에 문제가 있음을 나타낼 수 있습니다";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("치명적 오류: '").append(nombreClase).append("' 클래스가 이벤트 리스너로 등록되었지만 유효한 메서드가 없습니다. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("이 클래스는 다음 모드에 있습니다: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", 기타...");
			mensaje.append("</b>. ");
		}

		mensaje.append("이벤트를 수신하도록 클래스를 등록했지만 @SubscribeEvent로 주석 처리된 메서드가 없을 때 발생합니다.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "이벤트 리스너 없이 등록된 클래스";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("문제 있는 클래스는 다음 모드에 있습니다: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", 기타...");
			paso.append("</b>. 이 모드들은 유효한 메서드 없이 이벤트를 등록하려고 시도하고 있습니다");
			return paso.toString();
		}
		return "클래스 <b>" + nombreClase
				+ "</b>가 이벤트 수신을 위해 등록되었지만 <b>@SubscribeEvent</b> 주석이 붙은 메서드가 없습니다. <b>모드 트리</b> 기능을 사용하여 이 클래스를 포함한 모드를 식별하세요";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "소스 코드에서 클래스 <b>" + nombreClase + "</b>가 다음 형식의 메서드를 최소한 하나 포함하는지 확인하세요: "
				+ "<b>@SubscribeEvent public void 메서드이름(특정이벤트 이벤트) { ... }</b>. " + "내부 클래스라면 static으로 표시되지 않았는지 확인하세요";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("식별된 모드들에 대해 (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", 등");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("해당 모드 개발자에게 연락하여 문제를 해결하도록 하세요. ");
			} else {
				paso.append("이 모드들의 개발자들에게 연락하여 문제를 해결하도록 하세요. ");
			}
		}

		paso.append("개발자라면 EventBus에서 이 클래스의 등록을 제거하거나 유효한 @SubscribeEvent 메서드를 추가하세요");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>치명적 오류: '" + nombreArchivo
				+ "' 파일 처리 중 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' 예외가 발생했습니다. 이 오류는 런처가 모드팩 파일을 제대로 다운로드하거나 추출하지 못했음을 의미합니다. "
				+ "'zip END header not found' 메시지는 JAR 파일이 불완전하거나 손상되었음을 나타내며, 대용량 파일 다운로드를 제대로 처리하지 못하는 런처에서 매우 흔히 발생합니다. "
				+ "이 문제는 주로 Twitch/CurseForge, Technic Launcher 사용자에게 영향을 미치며, 특히 Luna Pixel 사용자에게 심각합니다. 이러한 런처는 종종 다운로드된 파일의 전체 무결성을 확인하지 못합니다. "
				+ "Luna Pixel 사용자는 ATLauncher로 전환하는 것이 좋습니다. ATLauncher는 파일 무결성을 더 잘 관리하며 이 특정 오류를 방지할 수 있는 더 안정적인 대안입니다. "
				+ "ZIP 형식이 손상되어 시스템이 모드를 로드할 수 없으며, Forge가 게임 시작에 필요한 리소스를 읽는 것을 방해합니다.</b>";
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "UnionFileSystem 오류 - 손상된 파일";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		return "모드팩을 처음부터 완전히 재설치하세요";
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "Luna Pixel을 사용 중이라면 ATLauncher로 전환하세요";
	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "재설치 전에 인터넷 연결 상태와 디스크 여유 공간을 확인하세요";
	}

	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "ProxySysOutSysErr를 활성화하시겠습니까?\n\n"
				+ "이 옵션은 런처가 로그를 제공하지 않을 때 CrashDetector가 System.out과 System.err에 접근할 수 있게 해줍니다.\n\n"
				+ "수동으로 로그를 붙여넣을 수 없을 경우에만 활성화해야 합니다.\n\n" + "경고: 일부 모드나 런처와 충돌할 수 있습니다.\n\n"
				+ "변경 사항을 적용하려면 게임/앱을 다시 시작해야 합니다.";
	}

	@Override
	public String confirmacionTitulo() {
		return "확인";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr가 성공적으로 활성화되었습니다.\n\n" + "변경 사항을 적용하려면 CrashDetector를 다시 시작해야 합니다.";
	}

	@Override
	public String informacionTitulo() {
		return "정보";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("치명적 오류: AzureLib 및 GeckoLib이(가) 너무 일찍 초기화되었습니다! ");
		} else if (azureLibError) {
			mensaje.append("치명적 오류: AzureLib이(가) 너무 일찍 초기화되었습니다! ");
		} else if (geckoLibError) {
			mensaje.append("치명적 오류: GeckoLib이(가) 너무 일찍 초기화되었습니다! ");
		}

		mensaje.append("이 오류는 Fabric 모드를 Fabric이 아닌 버전의 라이브러리로 사용하려 할 때 발생합니다. ");

		if (connectorPresente) {
			mensaje.append(
					"호환성 모드(Sinytra Connector 또는 specialcompatibilityoperation)가 감지되어 Forge 또는 FeatureCreep 환경에서 Fabric 모드를 실행하려 한다는 것을 의미합니다. ");
			mensaje.append("문제를 일으키는 특정 모드를 식별하려면 로그에서 'FabricMC 초기화 오류'를 확인하세요. ");
		}

		mensaje.append("AzureLib과 GeckoLib은 애니메이션 모드에 필수적이지만 올바른 플랫폼(Fabric 또는 Forge)과 일치해야 합니다. ");
		mensaje.append("이 초기화 충돌로 인해 게임이 애니메이션 모드를 제대로 로드할 수 없습니다.");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "라이브러리가 너무 일찍 초기화됨";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "로그에서 'FabricMC 초기화 오류'를 확인하여 문제 있는 모드를 식별하세요";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "플랫폼(Forge 또는 Fabric)에 맞는 올바른 버전의 AzureLib/GeckoLib을 사용하고 있는지 확인하세요";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError() + "'>치명적 오류: C2ME와 연결 모드 간의 호환성 문제. "
				+ "이 오류는 C2ME가 Sinytra Connector 또는 specialcompatibilityoperation과 같은 연결 모드 환경에서 제한된 Java 내부 구성 요소에 액세스하려 할 때 발생합니다. "
				+ "<b>C2ME는 이러한 환경과 호환되지 않지만, <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a>는 추천 대체 옵션</b>으로, 연결 모드와 정상적으로 작동합니다. "
				+ "Java 보안 권한 충돌로 인해 게임을 시작할 수 없습니다.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "C2ME와 연결 모드의 호환성 문제";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "mods 폴더에서 C2ME를 제거하세요";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "대신 <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a>를 다운로드하여 설치하세요 (Sinytra Connector와 호환됨)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "모든 연결 모드(Sinytra Connector 등)가 최신 버전으로 업데이트되었는지 확인하세요";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError() + "'>치명적 오류: '" + modId + "' 모드의 JEI 플러그인 로딩 실패. 클래스 '"
				+ nombreClase + "' (플러그인 ID: '" + pluginId + "') 가 오류를 발생시켜 게임 시작 중 충돌이 발생하고 있습니다. "
				+ "게임 초기화를 방해하는 호환되지 않거나 손상된 JEI 통합을 모드가 가지고 있을 때 이 문제가 발생합니다.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "JEI 플러그인 실패 - 충돌 유발";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "모드 <b>" + modId
				+ "</b> 에 문제가 있는 JEI 플러그인이 포함되어 충돌을 일으키고 있습니다. <b>모드 트리</b> 기능을 사용하여 문제를 일으키는 모드를 확인하세요";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "충돌이 해결되는지 확인하기 위해 모드 폴더에서 일시적으로 모드 <b>" + modId + "</b> 를 제거하세요";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "모드 <b>" + modId + "</b> 의 업데이트를 찾거나 JEI 플러그인 문제를 개발자에게 보고하세요. " + "그동안 게임을 시작하려면 모드를 제거해야 합니다";
	}

	@Override
	public String tituloLectador() {
		return "로그 리더 - 충돌 탐지기";
	}

	@Override
	public String obtenerTituloLeyenda() {
		return "색상 범례";
	}

	@Override
	public String obtenerErrorEnLeyenda() {
		return "치명적 오류";
	}

	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "스택 트레이스";
	}

	@Override
	public String obtenerTituloError() {
		return "오류";
	}

	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "선택한 줄을 처리하는 도중 오류 발생";
	}

	@Override
	public String obtenerNombreError() {
		return "오류 이름";
	}

	@Override
	public String obtenerDescripcionError() {
		return "자세한 설명";
	}

	@Override
	public String obtenerSeleccionarConsola() {
		return "로그 선택";
	}

	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "미확인 오류";
	}

	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "기록에서 치명적인 오류가 감지되었습니다. " + "근본 원인을 파악하려면 스택 트레이스를 확인하세요.";
	}

	@Override
	public String obtenerErrorLecturaArchivo() {
		return "기록 파일을 읽을 수 없습니다. " + "파일이 존재하고 읽기 권한이 있는지 확인하세요.";
	}

	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "로그 분석기";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("치명적 오류: '").append(modId).append("' 모드의 자동 이벤트 구독자 등록에 실패했습니다. ");

		mensaje.append("문제 있는 클래스: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("이 클래스는 다음 위치에 있습니다: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", 기타...");
			mensaje.append("</b>. ");
		}

		mensaje.append("모드가 이벤트 구독자로 클래스를 자동 등록하려 하지만 클래스를 로드할 수 없을 때 이 오류가 발생합니다. ");
		mensaje.append("<b>로그에서 이 메시지 이전의 다른 오류를 확인하세요. 실제 원인은 이전 로드 실패일 수 있습니다</b>.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "자동 구독자 등록 실패";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "모드 <b>" + modId + "</b>가 클래스 <b>" + nombreClase
				+ "</b>를 자동 구독자로 등록하려 하지만 실패했습니다. 이 클래스가 존재하고 접근 가능한지 확인하세요";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("문제 있는 클래스 <b>" + nombreClase + "</b>는 다음 파일에 있습니다: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", 기타...");
			paso.append("</b>. ");
			paso.append("<b>모드 트리</b> 기능을 사용해 문제 있는 클래스를 포함한 정확한 파일을 확인하세요");
			return paso.toString();
		}
		return "클래스 <b>" + nombreClase + "</b>가 어떤 모드 파일에도 없습니다. 모드 <b>" + modId
				+ "</b>가 올바르게 설치되었는지 확인하세요. <b>모드 트리</b> 기능을 사용해 문제를 찾으세요";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "모드 <b>" + modId + "</b>를 사용 중인 마인크래프트 및 포지 버전과 호환되는 최신 버전으로 업데이트하세요. "
				+ "문제가 지속되면 개발자에게 문제 클래스와 함께 오류를 보고하세요";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "<b>로그의 이 메시지 이전 오류들</b>을 확인하세요. 실제 문제는 이전 로드 실패일 수 있습니다. "
				+ "때때로 이전 오류가 이벤트 등록에 필요한 클래스 로드를 방해할 수 있습니다";
	}

	@Override
	public String limpiado() {
		// TODO Auto-generated method stub
		return "정리됨";
	}

	@Override
	public String original() {
		// TODO Auto-generated method stub
		return "원본";
	}

	@Override
	public String verEnConsola() {
		// TODO Auto-generated method stub
		return "로그에서 보기";
	}

	@Override
	public String advertencia() {
		// TODO Auto-generated method stub
		return "경고";
	}

	@Override
	public String fatal() {
		// TODO Auto-generated method stub
		return "치명적";
	}

	@Override
	public String noRegistroDeBattly() {
		// TODO Auto-generated method stub
		return "BattlyLauncher에는 복사할 수 있는 로그나 콘솔이 없습니다. 게임을 재시작하면서 STDOUT과 STDERR를 가로채기 위해 ProxySysOutSysErr를 사용할 수 있지만, STDOUT이나 STDERR를 수정하는 모드와 충돌할 수 있습니다.";
	}

	@Override
	public String noRegistroDeNightWorld() {
		// TODO Auto-generated method stub
		return "NightWorld 설정에서 디버그 모드를 활성화하여 런처 로그를 확보해야 합니다. 특히 STDOUT과 STDERR를 포함하기 때문에 매우 중요합니다.";
	}

	@Override
	public String noRegistroDeMCServidor() {
		// TODO Auto-generated method stub
		return "서버 터미널의 내용을 저장하거나 붙여넣어야 합니다. 다른 로그에는 없는 정보(예: STDOUT, STDERR 및 기타 오류)가 포함되어 있기 때문입니다. 최근 세션의 내용을 붙여넣어 주세요. 앞으로는 매번 붙여넣지 않도록 하려면 명령어 뒤에 >> cd_launcherlog를 추가하여 터미널 출력을 파일에 저장할 수 있습니다(이미지 참조). 이렇게 하면 터미널에 표시되지 않고 해당 파일에만 기록됩니다.";
	}

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("치명적 오류: NeoForge 환경에서 LexForge 변환기가 감지되었습니다. ");
		sb.append("</b>");

		sb.append("관련 클래스: <b>").append(claseReceptora).append("</b>. ");
		sb.append("영향을 받는 인터페이스는 <b>").append(interfazObjetivo).append("</b>이며, ");
		sb.append("다음 메서드가 누락되었습니다: <b>").append(firmaMetodoFaltante).append("</b>. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("클래스 위치: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", 기타...");
			sb.append("</b>. ");
		} else {
			sb.append("해당 클래스를 포함하는 JAR 파일을 찾을 수 없습니다. 그림자 처리되었거나 jar-in-jar로 포함되었을 수 있습니다. ");
		}

		sb.append("MinecraftForge/LexForge용으로 컴파일된 ModLauncher 변환기/서비스가 ");
		sb.append("호환되지 않는 ModLauncher API 버전과 함께 NeoForge에서 로드될 때 이 오류가 발생합니다. ");
		sb.append("NeoForge 전용 버전으로 구성 요소를 업데이트하거나 교체하세요.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "NeoForge에서 사용된 LexForge 변환기";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "비호환 변환기를 식별하세요: <b>" + claseReceptora + "</b>. " + "기대되는 API는 <b>" + interfazObjetivo
				+ "</b>이며, 다음이 누락되었습니다: <b>" + firmaMetodoFaltante + "</b>. "
				+ "Mod가 <b>META-INF/services</b>에 이 클래스를 등록했는지 확인하고, NeoForge에서 제거하거나 비활성화하세요.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("관련 모드 위치: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", 기타...");
			sb.append("</b>. ");
		} else {
			sb.append("해당 클래스를 포함하는 JAR를 찾을 수 없습니다. jar-in-jar 및 쉐도잉된 종속성을 확인하세요. ");
		}
		sb.append("일시적으로 해당 JAR를 제거하거나 NeoForge 호환 버전을 사용하여 원인을 확인하세요.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "구성 요소를 NeoForge 전용 버전으로 교체하거나, NeoForge에서 사용하는 ModLauncher 버전을 기준으로 다시 컴파일하세요. "
				+ "오래된 LexForge/MinecraftForge 바이너리를 피하세요.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "Mods 폴더를 정리하고 중복된 jar-in-jar 항목을 제거하세요. 필요 시 런처 캐시를 삭제하고 " + "재시작하여 LexForge 변환기가 더 이상 로드되지 않는지 확인하세요.";
	}

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia를 시작할 수 없습니다: Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("과 호환되지 않습니다.</b> ");
		sb.append("Xenon을 제거하고 Embeddium 또는 Sodium을 사용하세요. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("발견 위치: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", 기타...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia와 Xenon 불호환";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return label + "이(가) WaterMedia와 호환되지 않습니다. 프로필에서 제거하세요.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("위치: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", 기타...");
			sb.append("</b>. 해당 JAR 파일을 삭제하세요.");
			return sb.toString();
		}
		return "JAR 파일을 찾을 수 없습니다. mods 폴더를 확인하고 Xenon을 제거하세요.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "Embeddium 또는 Sodium을 대체로 설치하고 게임을 재시작하세요.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "압축 오류 (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>TACZ 리소스 복사 도중 Deflater가 종료되었습니다.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("관련된 항목: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", 기타");
			sb.append("</b>. ");
		}
		sb.append("<br/><b>해결 방법:</b> <code>tacz/tacz-pre.toml</code>에서 <code>DefaultPackDebug=true</code>로 설정하세요. ")
				.append("필요시 먼저 맵을 생성한 후 활성화하세요.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "tacz/tacz-pre.toml에서 DefaultPackDebug=true로 설정하세요. 필요하면 먼저 맵을 생성하고 활성화하세요.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "밀도 함수 미바인딩";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>등록부에 밀도 함수가 누락되었습니다.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("누락된 항목: ");
			for (int i = 0; i < Math.min(4, claves.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append("<code>").append(claves.get(i)).append("</code>");
			}
			if (claves.size() > 4)
				sb.append(", …");
			sb.append(". ");
		}
		sb.append("<br/><b>해결 방법:</b> 해당 함수를 정의하는 모드/데이터팩을 설치하거나 활성화하고 재시작하세요.");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "해당 함수를 제공하는 모드/데이터팩을 설치하거나 활성화하고 게임을 재시작하세요.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// 짧고 오류 색상의 메시지로 모드를 명시적으로 언급
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("등록 항목이 존재하지 않음: ").append(claveFaltante).append(". ");
		sb.append("Create 6용 Steam & Railways 알파 버전에서 자주 발생합니다.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (알파)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "Create 6용 Steam & Railways 알파 버전을 호환 가능한 버전으로 제거하거나 교체하세요.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// 짧고 오류 색상, 직접적인 권장사항 포함
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("로딩 충돌: Multiworld와 Sodium/Embeddium/Rubidium 함께 사용 시 ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance) 발생. ")
				.append("권장: Multiworld 또는 성능 모드 중 하나를 제거하거나 호환되는 버전을 사용하세요.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "충돌: Multiworld과 성능 모드";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Multiworld 또는 Sodium/Embeddium/Rubidium을 제거하거나, 서로 호환되는 버전으로 업데이트하세요.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Sodium이 호환되지 않는 그래픽 드라이버를 감지했습니다. "
				+ "GPU 드라이버를 최소 요구 사양 이상으로 업데이트하거나 Sodium 가이드를 따르세요." + "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "OpenGL 컨텍스트 오류";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OpenGL 실패: 현재 컨텍스트가 없거나 이 컨텍스트에서 해당 기능을 사용할 수 없습니다. " + "비디오 드라이버 문제일 수도 있습니다." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "GPU 드라이버를 업데이트하거나 재설치하고 재시작하세요. 오버레이 기능을 비활성화하고 성능 모드 없이 실행해 보세요.";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "링크가 클립보드에 복사되었습니다.";
	}

//Coreano
	@Override
	public String buscarDentroDeComprimidos() {
		return "압축 파일 내부 검색 (.zip/.jar/.war/.ear/.fpm/.rar(자바용)*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "텍스처 해상도 오류: 다음을 조정할 수 없음 " + recurso
				+ " - 크기: " + tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "텍스처 해상도 오류";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "이 오류는 텍스처가 너무 크거나 리소스 팩이 지나치게 많을 때 발생합니다. " + "해상도가 낮은 리소스 팩을 사용하거나 일부 리소스 팩을 제거해 보세요. "
				+ "허용된 해상도를 초과하는 맞춤형 텍스처를 추가하지 않았는지 확인하세요.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ModLauncher 서비스 오류: 잘못된 문자가 포함된 경로. "
				+ "ModLauncher 서비스는 ASCII 이외의 문자나 특수 문자를 포함한 경로를 처리할 수 없습니다. "
				+ "문제가 되는 문자에는 ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요 가 있으며, 특히 이름 끝에 있는 '\"' 문자는 매우 치명적입니다. "
				+ "ModLauncher에서 흔히 사용되는 서비스 컴포넌트로는 CrashDetector, " + Config.obtenerInstancia().obtenerNombreCD()
				+ ", FeatureCreep, Vivicraft, Optifine, Sodium, clonos, Iris Shaders/Oculus, MixerLogger, CrashAssistant 및 Sintrya Connector 등이 있습니다. "
				+ "모든 서비스를 제거할 수는 있지만, 경로 이름 자체로 인해 다른 문제가 발생할 수 있습니다. "
				+ "해결 방법: 공백이나 특수 문자 없이 ASCII 문자(a-z, A-Z, 0-9)만 사용하도록 인스턴스 이름을 변경하세요.</b>";
	}

	@Override
	public String nombre_error_modlauncher_path() {
		return "ModLauncher 경로 오류";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "인스턴스 경로에 ASCII가 아닌 문자나 특수 문자가 포함될 경우 이 오류가 발생합니다. " + "ModLauncher 서비스는 이러한 경로를 처리할 수 없습니다. "
				+ "해결 방법: 인스턴스 이름을 ASCII 문자(a-z, A-Z, 0-9)만 사용하도록 바꾸고, 공백과 특수 문자는 피하세요. "
				+ "특히 이름 끝에 있는 '\"' 문자는 매우 문제를 일으키므로 주의 깊게 확인하세요.";
	}

	@Override
	public String tituloEditorCodice() {
		return "코디체 편집기";
	}

	@Override
	public String nuevo() {
		return "새로 만들기";
	}

	@Override
	public String actualizarSeleccionado() {
		return "선택 항목 업데이트";
	}

	@Override
	public String eliminarSeleccionado() {
		return "선택 항목 삭제";
	}

	@Override
	public String exportarJSON() {
		return "JSON 내보내기...";
	}

	@Override
	public String guardarTodo() {
		return "모두 저장";
	}

	@Override
	public String general() {
		return "일반";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "검색할 텍스트";
	}

	@Override
	public String filtro() {
		return "필터 (ID)";
	}

	@Override
	public String criticalidad() {
		return "중요도 (경고/오류/치명적)";
	}

	@Override
	public String prioridad() {
		return "우선순위";
	}

	@Override
	public String lista() {
		return "검사 목록";
	}

	@Override
	public String colIdioma() {
		return "언어";
	}

	@Override
	public String colNombre() {
		return "이름";
	}

	@Override
	public String colResultado() {
		return "결과";
	}

	@Override
	public String vistaJson() {
		return "JSON 미리보기";
	}

	@Override
	public String idiomas() {
		return "언어 (모두 필수)";
	}

	@Override
	public String elegirFiltro() {
		return "선택...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "필터를 선택하세요";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "사용 가능한 필터";
	}

	@Override
	public String faltanCampos() {
		return "모든 필수 일반 항목을 입력하세요.";
	}

	@Override
	public String critInvalida() {
		return "잘못된 중요도입니다. ADVERTENCIA(경고), ERROR(오류), FATAL(치명적) 중 하나를 사용하세요.";
	}

	@Override
	public String filtroNoExiste() {
		return "지정한 필터가 존재하지 않습니다.";
	}

	@Override
	public String faltanIdiomas() {
		return "모든 언어에 대해 이름과 결과를 입력하세요:";
	}

	@Override
	public String verificacionInvalida() {
		return "검사 항목이 잘못되었습니다. 필드를 확인하세요.";
	}

	@Override
	public String guardadoOk() {
		return "저장 완료.";
	}

	@Override
	public String editorCodiceBoton() {
		return "이유 추가";
	}

	@Override
	public String descripcionEditorCodice() {
		return "여기에 이유를 등록할 수 있습니다. ID가 필요하며, 특수 문자, 악센트 기호, 공백 없이 문자열로 작성해야 합니다. 필터에는 \"줄 포함\"을 사용하여 줄 내 문자열 검색, \"전체 포함\"은 로그 전체에 특정 문자열이 있는지 확인, \"정규식 줄\"은 줄 단위 정규식 매칭, \"정규식 전체\"는 전체 로그에서 정규식 검색(줄 단위 버전 사용 권장)을 할 수 있습니다. 색상을 지정하려면 FATAL(치명적), ERROR(오류), ADVERTENCIA(경고) 중 하나의 중요도를 설정해야 합니다. 모든 언어에 대해 화면에 표시될 이름과 결과를 입력해야 합니다. 검사 항목을 추가하거나 삭제할 수 있으며, 완료 시 저장됩니다.";
	}

	@Override
	public String descartarCambios() {
		return "현재 검사에서 저장되지 않은 변경 사항을 취소하시겠습니까?";
	}

	@Override
	public String confirmacion() {
		return "확인";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "나가기 전에 변경 사항을 저장하시겠습니까?";
	}

	@Override
	public String salirSinGuardar() {
		return "저장하지 않고 나가기";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("치명적 오류: modlauncher 서비스(IDependencyLocator) 로딩 실패.<br>");
		sb.append("🔹 <b>문제 클래스:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>영향받은 모드:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append("🔸 <b>모드를 식별할 수 없습니다.</b> 최근 설치한 모드, 개발 중인 모드 또는 잘못 패키징된 모드를 확인하세요.<br>");
		}

		sb.append("🔸 <b>원인:</b> 모드의 <code>META-INF/services/...</code> 파일이 손상되었거나, ");
		sb.append("현재 Forge/NeoForge 버전과 호환되지 않거나, 잘못된 버전용 모드입니다.<br>");
		sb.append("🔸 <b>결과:</b> Forge/NeoForge가 모드의 의존성을 등록할 수 없어 ");
		sb.append("게임 시작이 차단됩니다.<br>");
		sb.append("🔸 <b>해결책:</b> 문제 있는 모드를 업데이트, 재설치 또는 삭제하세요. ");
		sb.append("개발 중인 모드를 사용하는 경우 정확한 Forge/NeoForge 버전에 맞게 컴파일되었는지 확인하세요.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "서비스 구성 오류 (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. 원인 모드 식별: 최근 설치하거나 개발 중인 모드를 확인하세요.";
		}
		return "1. 문제가 있는 모드는: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. 모드를 업데이트, 재설치 또는 삭제하세요. Forge/NeoForge와 호환되는 버전을 사용하고 있는지 확인하세요.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>치명적 오류: 존재하지 않는 메서드.</b><br>" + "모드가 <b style='color:#"
				+ colorCodigo + "'>" + metodo + "</b> 메서드를 호출하려 했지만, " + "이 메서드는 현재 게임 또는 다른 모드 버전에 존재하지 않습니다.<br>"
				+ "<span style='color:#" + colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta)
				+ "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "존재하지 않는 메서드 (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. 이 오류는 모드가 현재 게임 버전이나 다른 모드와 호환되지 않을 때 발생합니다.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. 관련된 모든 모드를 업데이트하세요. 지속되면 영향을 받은 모드의 제작자에게 오류를 보고하세요.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>치명적 오류: 존재하지 않는 필드.</b><br>" + "모드가 <b style='color:#"
				+ colorCodigo + "'>" + campo + "</b> 필드에 접근하려 했지만, " + "이 필드는 현재 게임 또는 다른 모드 버전에 존재하지 않습니다.<br>"
				+ "<span style='color:#" + colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta)
				+ "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "존재하지 않는 필드 (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. 이 오류는 일반적으로 모드가 현재 게임 버전이나 다른 모드와 호환되지 않을 때 발생합니다.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. 영향받은 모든 모드를 업데이트하세요. 문제가 계속되면 오류를 유발한 모드의 개발자에게 문의하세요.";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = MonitorDePID.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png")
				.toAbsolutePath().toUri().toString();
		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>도움이 필요하십니까?</strong><br>"
				+ "  해결 방법을 모르거나 여기에 원인이 없으면 소셜 미디어를 통해 도움을 받을 수 있습니다. " + "  <img src='" + iconoCompartir
				+ "' alt='공유' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>공유</strong> 버튼을 사용하여 로그와 분석 결과 링크를 팀에 전달하세요. "
				+ "  모드팩 제작자나 기관이라면 <code>crash_detector/plantilla.htm</code> 파일을 수정하여 " + "  팀 전용 링크를 맞춤 설정할 수 있습니다."
				+ "</div>";
	}
	
	@Override
	public String restablecerPlantilla() {
	    return "템플릿 초기화";
	}

	@Override
	public String restablecer() {
	    return "초기화";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
	    return nombreImagen + "을(를) 기본값으로 초기화하시겠습니까?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
	    return "템플릿을 기본값으로 초기화하시겠습니까?";
	}
	
	@Override
	public String faltar_de_clases_azurelib() {
	    return "<b style='color:#" + config.obtenerColorError()
	           + "'>AzureLib 클래스가 누락되었습니다. 이미 AzureLib이 있다면, 2025년 10월 8일 이전 버전을 설치하세요. 흔히 발생하는 문제입니다. AzureLib이 없다면 최신 버전을 설치하세요.</b>";
	}
	
	@Override
	public String errorHealightINT() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "모드 <code>healight</code> 가 치명적인 오류를 발생시키고 있습니다: <code>java.lang.NoSuchFieldError: INT</code>. "
	           + "이 오류는 모드가 MCForge 47.10 이상의 마인크래프트 1.20+ 버전에서는 더 이상 존재하지 않는 필드에 접근하려 할 때 발생합니다. "
	           + "이 문제로 인해 게임을 시작할 수 없습니다.</b>";
	}

	@Override
	public String solucionHealightINT() {
	    return "• 모드 <code>healight</code> 를 삭제하거나 업데이트하세요. "
	           + "현재 버전은 1.20.1용 MinecraftForge 47.10과 호환되지 않습니다. "
	           + "모드의 최신 버전을 찾거나 대체 옵션을 고려하세요.";
	}

	@Override
	public String nombreErrorHealightINT() {
	    return "치명적 오류: healight - 'INT' 필드를 찾을 수 없음";
	}
	
	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz, String origen) {
	    String colorError = config.obtenerColorError();
	    StringBuilder sb = new StringBuilder();
	    sb.append("<b style='color:#").append(colorError).append(";'>")
	      .append("클래스 <code>").append(clase).append("</code>가 다음 필수 메서드를 구현하지 않았습니다:<br>")
	      .append("<code>").append(metodo).append("</code><br>")
	      .append("인터페이스 <code>").append(interfaz).append("</code>에서 정의됨.");

	    if (!origen.isEmpty()) {
	        sb.append("<br><br>의심되는 모드 또는 파일: <code>").append(origen).append("</code>.");
	    }

	    sb.append("</b>");
	    return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
	    return "• 이 오류는 모드가 인터페이스를 구현하지만 필수 메서드를 생략할 때 발생합니다. "
	           + "<b>두 모드 모두</b> 업데이트하세요 (인터페이스를 정의하는 모드와 이를 구현하는 모드). "
	           + "어떤 모드인지 모르겠다면 오류 메시지에 나타난 이름을 찾아보세요.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
	    return "구현되지 않은 인터페이스 메서드 (AbstractMethodError)";
	}
	
	@Override
	public String errorMetadataAnimacionEnServidor() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "모드가 <b>전용 서버</b>에서 <b>클라이언트 측</b> 클래스인 "
	           + "(<code>AnimationMetadataSection</code>)를 로드하려 시도하고 있으나, 이는 불가능합니다. "
	           + "이 오류는 모드가 클라이언트와 서버 코드를 제대로 분리하지 않았을 때 자주 발생합니다. "
	           + "<code>ModernFix</code>가 설치되어 있으면 이 문제를 드러낼 수 있지만, 직접적인 원인은 아닙니다.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
	    return "• <b>빠른 해결책:</b> <code>ModernFix</code>를 임시로 제거하여 서버가 시작되는지 확인하세요. "
	           + "시작된다면 문제는 다른 모드가 서버에 클라이언트 클래스를 로드하고 있기 때문입니다.<br>"
	           + "• <b>영구적 해결책:</b> 문제의 원인이 되는 모드(애니메이션 리소스, 맞춤 텍스처 또는 그래픽 라이브러리를 사용하는 모드)를 찾아 업데이트하거나 삭제하세요.<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
	    return "서버에서 클라이언트 클래스 로드됨 (AnimationMetadataSection)";
	}
	
	@Override
	public String errorConfiguracionConnectorCorrupta() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "<code>Sinytra Connector</code> 모드의 설정 파일이 손상되었습니다. "
	           + "게임이 예기치 않게 종료되거나, 쓰기 오류, 모드 충돌로 인해 파일에 널 문자(<code>\\u0000</code>)가 가득 차는 경우 이 문제가 발생합니다.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
	    return "• 마인크래프트 인스턴스의 <code>config/</code> 폴더로 이동하세요.<br>"
	           + "• connector 관련 모드의 설정 파일을 찾아 삭제하세요.<br>"
	           + "• 게임을 다시 시작하세요: Sinytra Connector가 새롭고 깨끗한 설정 파일을 생성할 것입니다.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
	    return "Sinytra Connector 설정 손상";
	}
	
	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "파일 <code>" + nombreJar + "</code>이(가) 손상되었거나 불완전합니다.<br>"
	           + "ZIP 파일의 마지막 헤더가 누락되어 시스템이 내용을 읽을 수 없습니다.<br>"
	           + "이 오류는 일반적으로 다운로드 중단이나 런처 오류 후에 발생합니다.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
	    return "손상된 JAR 파일 (특정 이름 포함)";
	}

	@Override
	public String solucionJarCorrupto() {
	    return "• <b>손상된 파일을 삭제</b>하고 공식 출처(CurseForge, MinecraftStorage 등)에서 다시 다운로드하세요.<br>"
	           + "• CurseForge, Technic 또는 Luna Pixel 같은 런처를 사용한다면, "
	           + "파일 무결성을 더 잘 검사하는 <b>ATLauncher</b> 또는 <b>Prism Launcher</b>로 전환하는 것을 고려하세요.<br>"
	           + "• 다운로드 중 인터넷 연결이 안정적인지 확인하세요.";
	}
	
	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "세계를 불러올 수 없습니다. NBT 파일 중 하나가 손상되었기 때문입니다 "
	           + "(예: <code>level.dat</code>, <code>playerdata/*.dat</code>, 또는 청크 데이터).<br>"
	           + "구체적인 오류는 다음과 같습니다: <code>UTFDataFormatException: 바이트 " + byteCorrupto + " 근처에서 잘못된 입력 형식</code>.<br><br>"
	           + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
	           + "⚠️ 복구 시도 전에 반드시 세계 폴더 전체를 백업하세요.</b><br><br>"
	           + "손상된 파일은 <b>NBT 편집기</b>(예: <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>)를 사용해 복구할 수 있습니다.<br>"
	           + "심각하게 손상된 경우, <b>헥사 에디터</b>(HxD 등)로 무효 바이트를 검사하고 수정할 수 있습니다 "
	           + "(단, NBT 형식에 익숙한 경우에만 권장).<br>"
	           + "마지막 수단으로 백업본에서 복원하거나, <code>FTB Backup</code> 등의 MOD에서 제공하는 '월드 복구' 기능을 사용하세요.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
	    return "• <b>복구 시도 전에 세계 폴더를 완전히 백업하세요</b>.<br>"
	           + "• NBT 편집기(NBT Studio 등)를 사용하여 손상된 파일을 열고 수정하세요.<br>"
	           + "• 실패하면 헥사 에디터로 손상된 바이트 위치를 확인하세요.<br>"
	           + "• 경험이 없다면 최근 백업본에서 복원하세요.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
	    return "세계 손상: NBT 데이터 로드 오류";
	}
	
	@Override
	public String problema_con_openAL() {
	    return "<span style='color:#" + config.obtenerColorError()
	           + "'>OpenAL에 문제가 있습니다. 가끔 Nouveau 드라이버가 원인일 수 있지만, 때로는 애플리케이션의 OpenAL 버전이 배포판의 버전과 호환되지 않아 배포판에서 제공하는 버전을 사용해야 할 수도 있습니다. 더 많은 도움을 받으려면 다음 가이드를 참조하세요: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>리눅스에서 마인크래프트 사운드 문제 해결 방법</a>.</span>";
	}
	
	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "서버를 시작할 수 없습니다. 세계의 파일이 다른 프로세스에 의해 잠겨 있기 때문입니다.<br>"
	           + "다음과 같은 경우에 발생합니다:<br>"
	           + "• 이미 서버 인스턴스가 실행 중입니다.<br>"
	           + "• 바이러스 백신 프로그램이나 파일 탐색기가 세계 폴더를 열고 있습니다.<br>"
	           + "• 이전 프로세스가 정상적으로 종료되지 않아 파일이 잠긴 상태입니다.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
	    return "• <b>모든 서버 인스턴스를 종료하세요</b> (javaw.exe와 같은 백그라운드 프로세스 포함).<br>"
	           + "• 호스팅 패널(Multicraft 등)을 사용한다면 패널에서 서버를 완전히 재시작하세요.<br>"
	           + "• 파일을 잠그고 있다고 의심되면 <b>바이러스 백신을 일시적으로 비활성화하세요</b>.<br>"
	           + "• 로컬 시스템에서는 세계 폴더를 표시하는 모든 Windows 탐색기 창을 닫으세요.<br>"
	           + "• 문제가 지속되면, 세계 폴더 내부의 <code>session.lock</code> 파일을 수동으로 삭제하세요 (다른 서버가 실행 중이지 않다는 확신이 있을 때만).";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
	    return "다른 프로세스에 의해 잠긴 세계 파일";
	}
	
	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "모드가 <code>" + clasePadreFinal + "</code> 클래스를 확장하려 했지만, "
	           + "이 클래스는 이제 <b>final</b>로 설정되어 상속할 수 없습니다.<br>"
	           + "문제가 있는 클래스는: <code>" + claseHija + "</code>입니다.<br><br>"
	           + "이는 일반적으로 모드가 마인크래프트 또는 다른 기본 모드의 이전 버전용으로 컴파일되었으며, "
	           + "해당 기본 모드가 최근 버전에서 특정 클래스를 <code>final</code>로 지정한 경우에 발생합니다.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
	    return "• <b>관련된 모든 모드를 업데이트하세요</b>, 특히 언급된 기본 모드와 관련이 있을 수 있는 모드들.<br>"
	           + "• 문제가 지속되면 현재 마인크래프트 버전과 종속성과 호환되는 모드 버전을 찾아보세요.<br>"
	           + "• 일부 경우, 자식 클래스를 포함한 모드를 일시적으로 제거하면 원인을 확인할 수 있습니다.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
	    return "final 클래스 상속 시도";
	}
	
	@Override
	public String errorRubidiumObsoletoConIris() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "<b>Rubidium</b>(포지용 소듐 포크 중 폐기된 버전)을 <b>Iris 또는 Oculus</b>와 함께 사용하고 있습니다.<br>"
	           + "최근 마인크래프트 버전(1.19.2 이상)에서 "
	           + "Rubidium은 소듐의 업데이트를 따라가지 못했고 관련 모드에 문제가 발생했습니다.<br><br>"
	           + "성능 향상 모드(Sodium, Rubidium, Embeddium, Bedium, Xeonium 등) 또는 Iris Shaders가 다른 모드와 충돌할 때도 이 오류가 발생할 수 있습니다.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
	    return "• <b>mods</b> 폴더에서 <b>Rubidium을 제거하세요</b>.<br>"
	           + "• <b><a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a>을 설치하세요</b>, "
	           + "이것은 1.20+ 버전에서 Iris/Oculus를 지원하는, 포지용으로 활발히 유지보수되는 소듐 포크입니다.<br>"
	           + "• 동시에 여러 개의 소듐 포크를 설치하지 않도록 하세요(Rubidium과 Embeddium 같이 설치 금지).<br>"
	           + "• Iris 대신 Oculus를 사용한다면, 그것도 당신의 포지 및 Embeddium 버전과 호환되는지 확인하세요.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
	    return "사용 중단된 Rubidium과 Iris/Oculus 함께 사용 (OptionInstance는 final임)";
	}
	
	@Override
	public String errorVoiceChatPuertoOcupado() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "لا يمكن للتعديل <code>Simple Voice Chat</code> تشغيل خادم الصوت الخاص به لأن "
	           + "منفذ UDP مستخدم بالفعل أو عنوان IP المُعد غير صالح.<br>"
	           + "هذا لا يمنع بدء اللعبة، لكنه يعطل وظيفة الدردشة الصوتية.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
	    return "• <b>أغلق أي نسخة أخرى من ماينكرافت</b> أو تطبيق يستخدم المنفذ UDP 24454.<br>"
	           + "• إذا كنت على خادم، تأكد من أن <b>أي خدمة أخرى</b> لا تستخدم هذا المنفذ.<br>"
	           + "• في إعدادات التعديل (<code>config/voicechat/</code>)، غيّر منفذ UDP إلى منفذ حر (مثلاً 24455).<br>"
	           + "• إذا كنت تستخدم عنوان IP مخصصًا، فتحقق من صحته أو اتركه فارغًا لاستخدام الإعداد الافتراضي.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
	    return "الدردشة الصوتية: منفذ UDP مشغول أو عنوان IP غير صالح";
	}
	
	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "블록 아이템 <code>" + nombreBlockItem + "</code>이(가) 블록을 null로 참조하고 있습니다.<br>"
	           + "이 오류는 일반적으로 <b>Create 애드온</b>(예: <code>dndecor</code>, <code>createdeco</code>)에서 발생하며, "
	           + "<code>Amendments</code>, <code>Moonshine</code>과의 충돌 또는 블록 초기화 오류로 인해 나타납니다.<br>"
	           + "<b>참고:</b> 이 오류는 Amendments 자체의 문제가 아니라 등록 로드 과정의 더 깊은 문제를 나타냅니다.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
	    return "• <b>관련된 모든 모드를 업데이트하세요:</b> Create, Amendments, Moonshine 및 모든 애드온(<code>dndecor</code>, <code>createdeco</code> 포함).<br>"
	           + "• 문제가 지속되면 <b>Create 애드온을 일시적으로 하나씩 제거</b>하여 원인을 찾아보세요.<br>"
	           + "• <b>Amendments와 Moonshine이 사용 중인 Create 및 Forge 버전과 호환되는지</b> 확인하세요.<br>"
	           + "• 문제가 있는 애드온의 베타 버전이나 최신 포크가 있는지 확인하세요.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
	    return "Create 애드온에서의 null 블록 아이템";
	}
	
	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
	    StringBuilder sb = new StringBuilder();
	    sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>")
	      .append("활성화된 플랫폼(Forge, Fabric 등)에 속하지 않는 모드가 발견되었습니다:<ul>");
	    for (String mod : mods) {
	        sb.append("<li><code>").append(mod).append("</code></li>");
	    }
	    sb.append("</ul>이 문제는 일반적으로 다음 경우에 발생합니다:<br>")
	      .append("• 같은 폴더에 <b>Fabric와 Forge</b> 모드를 섞었을 때.<br>")
	      .append("• Minecraft의 호환되지 않는 버전용 모드를 설치했을 때.<br>")
	      .append("• 모드가 손상되었거나 유효한 JAR 파일이 아닐 때.</b>");
	    return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
	    return "• <b>모든 모드가 동일한 플랫폼용인지 확인하세요</b> (Forge <b>또는</b> Fabric, 둘 다 아님).<br>"
	           + "• 각 파일이 어떤 플랫폼으로 인식되는지 확인하려면 <b>모드 트리</b>를 사용하세요.<br>"
	           + "• 알 수 없거나 다른 플랫폼용인 모드는 삭제하세요.<br>"
	           + "• CurseForge나 Prism 같은 런처를 사용한다면 프로필 설정이 올바른지 확인하세요.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
	    return "활성 로더와 호환되지 않는 모드";
	}
	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "모델 <code>" + modid + ":" + nombreModelo + "</code>을(를) 생성할 수 없습니다.<br>"
	           + "이는 모드 <code>" + modid + "</code>의 리소스가 손상되었거나 누락되었거나 "
	           + "사용 중인 마인크래프트 버전과 호환되지 않음을 의미합니다.</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
	    return "• <b>모드를 업데이트하세요</b>, 인스턴스와 호환되는 최신 버전으로.<br>"
	           + "• 개발 중이거나 사용자 정의된 버전을 사용 중이라면 공식 버전으로 되돌리세요.<br>"
	           + "• JAR 파일이 손상되지 않았는지 확인하세요(다시 설치).<br>"
	           + "• 문제가 지속되면 이 로그를 첨부하여 모드 제작자에게 오류를 보고하세요.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
	    return "리소스 모델 생성 실패";
	}
	
	@Override
	public String conflictoMoonlightIceberg() {
	    return "<b style='color:#" + config.obtenerColorError() + ";'>"
	           + "<code>Moonlight</code>과 <code>Iceberg</code> 모드 사이에 치명적인 충돌이 감지되었습니다.<br>"
	           + "두 모드 모두 자원 재로딩 시스템을 호환되지 않는 방식으로 등록하려 하며, "
	           + "이는 유효한 그래픽 컨텍스트가 없어 OpenGL 오류를 유발합니다.<br>"
	           + "이 문제는 Fabric 모드 어댑터를 포함하는 Forge 버전을 사용할 때 흔히 발생합니다.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
	    return "• <b>두 모드 모두</b> 현재 Forge 버전과 호환되는 최신 버전으로 업데이트하세요.<br>"
	           + "• 문제가 지속되면 <b>일시적으로 Iceberg를 제거하세요</b>. Moonlight은 다른 많은 모드의 핵심 종속성인 경우가 많습니다.<br>"
	           + "• 중복되거나 혼합된 Forge/Fabric 버전을 설치하지 않았는지 확인하세요.<br>"
	           + "• 다른 모드(Supplementaries, Citadel 등)가 이미 Iceberg 기능을 내장하고 있는지 확인하세요.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
	    return "치명적 충돌: Moonlight 대 Iceberg (OpenGL 컨텍스트 없음)";
	}
	@Override
	public String instantanea() {
	    return "스냅샷";
	}

	@Override
	public Object desdeUltimaInstantanea() {
	    return "마지막 스냅샷 이후";
	}

	@Override
	public String seleccionarUnArchivo() {
	    return "파일 선택";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
	    return "스냅샷이 성공적으로 생성되었습니다";
	}

	@Override
	public String errorCreandoInstantanea() {
	    return "스냅샷 생성 중 오류 발생";
	}
	
	@Override
	public String consejo() {
	    return "팁";
	}

	@Override
	public String resultadoMuestra() {
	    return "결과 보기";
	}
	
	
	@Override
	public String historaDeModsDesc() {
	    return "<html>" + 
	           "<body style='font-family: sans-serif; font-size: 12px;'>" +
	           "  <b>팁:</b> 두 개의 기록 파일을 선택하여 모드 목록을 비교하세요. " +
	           "  결과는 정규화된 이름을 기준으로 <span style='color:%s;'>추가됨 (+)</span>과 " +
	           "  <span style='color:%s;'>제거됨 (&#8722;)</span>을 표시합니다. " +
	           "  '스냅샷' 버튼을 사용하면 기존 파일의 .instantanea 확장자 복사본을 만들 수 있습니다." +
	           "</body>" + 
	           "</html>";
	}
	
	@Override
	public String texto_de_boton_compartir_markdown() {
	    return "로그 링크를 마크다운 형식으로 가져오기 (보고서 없음)";
	}
	
	
	
	
	
	
	
	
	
	

}
