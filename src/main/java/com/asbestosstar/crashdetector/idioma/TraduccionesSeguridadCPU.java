package com.asbestosstar.crashdetector.idioma;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.asbestosstar.crashdetector.bajo.hw.cpu.seguridad.ClavesTextoSeguridadCPU;

/**
 * Traducciones compartidas de las comprobaciones de Meltdown y Spectre.
 *
 * Generado por patch_idiomas_seguridad_cpu.py. No utiliza servicios de
 * traducción en tiempo de ejecución ni implementaciones default de Idioma.
 */
final class TraduccionesSeguridadCPU {

	private static final Map<String, Map<String, String>> TEXTOS = new HashMap<>();

	static {
		cargar_ar();
		cargar_zh();
		cargar_ko();
		cargar_eo();
		cargar_fr();
		cargar_id();
		cargar_en();
		cargar_ja();
		cargar_km();
		cargar_lo();
		cargar_ms();
		cargar_fa();
		cargar_pt();
		cargar_ru();
		cargar_sw();
		cargar_th();
		cargar_uk();
		cargar_vi();
	}

	private static void cargar_ar() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: تخفيف ثغرة المعالج");
		m.put("check_spectre", "Spectre V1/V2: تخفيف ثغرات المعالج");
		m.put("disable_meltdown", "عدم فحص Meltdown مرة أخرى");
		m.put("disable_spectre", "عدم فحص Spectre مرة أخرى");
		m.put("open_docs", "فتح الوثائق الرسمية");
		m.put("check_error", "تعذر إكمال {0}: {1}");
		m.put("title_protection", "{0}: حماية المعالج");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "الشركة المصنّعة");
		m.put("label_arch", "المعمارية");
		m.put("label_os", "نظام التشغيل");
		m.put("label_affected", "مدى تأثر وحدة المعالجة المركزية");
		m.put("label_overall", "الحالة العامة");
		m.put("label_os_mitigation", "تخفيف نظام التشغيل");
		m.put("label_microcode", "الرمز المصغر/البرنامج الثابت");
		m.put("label_revision", "المراجعة المكتشفة");
		m.put("label_note", "ملاحظة");
		m.put("label_evidence", "الدليل");
		m.put("label_action", "الإجراء الموصى به");
		m.put("state_affected", "متأثر");
		m.put("state_possible", "قد يكون متأثرًا");
		m.put("state_not_affected", "غير متأثر");
		m.put("state_unknown", "غير معروف");
		m.put("state_mitigated", "تم التخفيف");
		m.put("state_partial", "تم التخفيف جزئيًا");
		m.put("state_vulnerable", "معرّض للخطر");
		m.put("state_na", "غير منطبق");
		m.put("state_present", "موجود");
		m.put("state_absent", "غير موجود");
		m.put("state_not_needed", "غير مطلوب");
		m.put("remote_note",
				"وحدة المعالجة المركزية في السجل لا تطابق الجهاز الذي يشغّل الكاشف. لم تُنسب حالة تصحيحات الجهاز المحلي إلى السجل.");
		m.put("unknown_text", "نص أمان وحدة المعالجة المركزية غير مترجم: {0}");
		m.put("remote_evidence", "وحدة المعالجة المركزية في السجل تختلف عن المحلية؛ لم تُفحص حالة التصحيحات المحلية.");
		m.put("remote_action", "شغّل الفحص على الجهاز الذي أنشأ السجل.");
		m.put("unknown_for_log", "غير محدد للسجل");
		m.put("no_runtime_status", "لا يوفّر {0} حالة تشغيل موثوقة ومحمولة لهذه التخفيفات.");
		m.put("update_and_verify", "حدّث {0}، وثبّت أحدث برنامج ثابت أو رمز مصغر، ثم أعد التشغيل وتحقق من: {1}.");
		m.put("kernel_missing", "النواة لا تعرض {0}.");
		m.put("update_kernel",
				"حدّث النواة والبرنامج الثابت أو الرمز المصغر للمعالج، ثم أعد التشغيل وافحص {0} مجددًا.");
		m.put("no_action", "لا يلزم أي إجراء للمتغيرات الأصلية التي أُبلغ عن تخفيفها.");
		m.put("cpu_not_affected", "يفيد نظام التشغيل بأن هذا المعالج غير متأثر.");
		m.put("mitigation_active", "تخفيف نظام التشغيل نشط.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "فعّل أو حدّث {0}، ثم أعد التشغيل وتحقق منه مجددًا.");
		m.put("cannot_read", "تعذرت قراءة {0}.");
		m.put("partial_status", "يبدو أن تأكيد {0} جزئي فقط؛ تحقق من {1}.");
		m.put("not_exposed", "غير معروض بواسطة {0}");
		m.put("update_os_firmware_verify", "حدّث {0} و{1}، ثم أعد التشغيل وتحقق باستخدام {2}.");
		TEXTOS.put("ar", Collections.unmodifiableMap(m));
	}

	private static void cargar_zh() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown：处理器缓解措施");
		m.put("check_spectre", "Spectre V1/V2：处理器缓解措施");
		m.put("disable_meltdown", "不再检查 Meltdown");
		m.put("disable_spectre", "不再检查 Spectre");
		m.put("open_docs", "打开官方文档");
		m.put("check_error", "无法完成 {0}：{1}");
		m.put("title_protection", "{0}：处理器防护");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "制造商");
		m.put("label_arch", "体系结构");
		m.put("label_os", "操作系统");
		m.put("label_affected", "CPU 受影响情况");
		m.put("label_overall", "总体状态");
		m.put("label_os_mitigation", "操作系统缓解措施");
		m.put("label_microcode", "微码/固件");
		m.put("label_revision", "检测到的修订版本");
		m.put("label_note", "备注");
		m.put("label_evidence", "证据");
		m.put("label_action", "建议操作");
		m.put("state_affected", "受影响");
		m.put("state_possible", "可能受影响");
		m.put("state_not_affected", "不受影响");
		m.put("state_unknown", "未知");
		m.put("state_mitigated", "已缓解");
		m.put("state_partial", "部分缓解");
		m.put("state_vulnerable", "存在漏洞");
		m.put("state_na", "不适用");
		m.put("state_present", "存在");
		m.put("state_absent", "缺失");
		m.put("state_not_needed", "不需要");
		m.put("remote_note", "日志中的 CPU 与运行检测器的计算机不匹配。未将本机的补丁状态归入该日志。");
		m.put("unknown_text", "未翻译的 CPU 安全文本：{0}");
		m.put("remote_evidence", "日志中的 CPU 与本机 CPU 不同；未查询本机补丁状态。");
		m.put("remote_action", "请在生成该日志的计算机上运行检查。");
		m.put("unknown_for_log", "无法根据日志确定");
		m.put("no_runtime_status", "{0} 未提供可靠且可移植的运行时缓解状态。");
		m.put("update_and_verify", "更新 {0}，安装当前固件或微码，重新启动，然后验证：{1}。");
		m.put("kernel_missing", "内核未公开 {0}。");
		m.put("update_kernel", "更新内核以及 CPU 固件或微码，重新启动，然后再次检查 {0}。");
		m.put("no_action", "对于内核报告已缓解的原始变体，无需执行操作。");
		m.put("cpu_not_affected", "操作系统报告此处理器不受影响。");
		m.put("mitigation_active", "操作系统缓解措施已启用。");
		m.put("feature_status", "{0}：{1}。");
		m.put("feature_update", "启用或更新 {0}，重新启动，然后再次验证。");
		m.put("cannot_read", "无法读取 {0}。");
		m.put("partial_status", "{0} 似乎仅得到部分确认；请验证 {1}。");
		m.put("not_exposed", "{0} 未公开此信息");
		m.put("update_os_firmware_verify", "更新 {0} 和 {1}，重新启动，然后使用 {2} 验证。");
		TEXTOS.put("zh", Collections.unmodifiableMap(m));
	}

	private static void cargar_ko() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: 프로세서 완화 조치");
		m.put("check_spectre", "Spectre V1/V2: 프로세서 완화 조치");
		m.put("disable_meltdown", "Meltdown을 다시 검사하지 않음");
		m.put("disable_spectre", "Spectre를 다시 검사하지 않음");
		m.put("open_docs", "공식 문서 열기");
		m.put("check_error", "{0}을(를) 완료할 수 없음: {1}");
		m.put("title_protection", "{0}: 프로세서 보호");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "제조업체");
		m.put("label_arch", "아키텍처");
		m.put("label_os", "운영 체제");
		m.put("label_affected", "CPU 영향 여부");
		m.put("label_overall", "전체 상태");
		m.put("label_os_mitigation", "운영 체제 완화 조치");
		m.put("label_microcode", "마이크로코드/펌웨어");
		m.put("label_revision", "감지된 리비전");
		m.put("label_note", "참고");
		m.put("label_evidence", "근거");
		m.put("label_action", "권장 조치");
		m.put("state_affected", "영향받음");
		m.put("state_possible", "영향받을 가능성 있음");
		m.put("state_not_affected", "영향받지 않음");
		m.put("state_unknown", "알 수 없음");
		m.put("state_mitigated", "완화됨");
		m.put("state_partial", "부분적으로 완화됨");
		m.put("state_vulnerable", "취약함");
		m.put("state_na", "해당 없음");
		m.put("state_present", "있음");
		m.put("state_absent", "없음");
		m.put("state_not_needed", "필요하지 않음");
		m.put("remote_note", "로그의 CPU가 검사기를 실행하는 컴퓨터와 일치하지 않습니다. 로컬 컴퓨터의 패치 상태를 로그에 적용하지 않았습니다.");
		m.put("unknown_text", "번역되지 않은 CPU 보안 문구: {0}");
		m.put("remote_evidence", "로그의 CPU가 로컬 CPU와 다르므로 로컬 패치 상태를 조회하지 않았습니다.");
		m.put("remote_action", "로그를 생성한 컴퓨터에서 검사를 실행하십시오.");
		m.put("unknown_for_log", "로그에서 확인할 수 없음");
		m.put("no_runtime_status", "{0}에서는 이러한 완화 조치의 신뢰할 수 있고 이식 가능한 런타임 상태를 제공하지 않습니다.");
		m.put("update_and_verify", "{0}을(를) 업데이트하고 최신 펌웨어 또는 마이크로코드를 설치한 뒤 재시작하여 다음을 확인하십시오: {1}.");
		m.put("kernel_missing", "커널에서 {0}을(를) 제공하지 않습니다.");
		m.put("update_kernel", "커널과 CPU 펌웨어 또는 마이크로코드를 업데이트하고 재시작한 뒤 {0}을(를) 다시 확인하십시오.");
		m.put("no_action", "완화된 것으로 보고된 원래 변형에는 추가 조치가 필요하지 않습니다.");
		m.put("cpu_not_affected", "운영 체제에서 이 프로세서는 영향을 받지 않는다고 보고합니다.");
		m.put("mitigation_active", "운영 체제 완화 조치가 활성화되어 있습니다.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "{0}을(를) 활성화하거나 업데이트하고 재시작한 뒤 다시 확인하십시오.");
		m.put("cannot_read", "{0}을(를) 읽을 수 없습니다.");
		m.put("partial_status", "{0}은(는) 부분적으로만 확인되었습니다. {1}을(를) 확인하십시오.");
		m.put("not_exposed", "{0}에서 제공되지 않음");
		m.put("update_os_firmware_verify", "{0}과(와) {1}을(를) 업데이트하고 재시작한 뒤 {2}(으)로 확인하십시오.");
		TEXTOS.put("ko", Collections.unmodifiableMap(m));
	}

	private static void cargar_eo() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: mildigo de la procesoro");
		m.put("check_spectre", "Spectre V1/V2: mildigo de la procesoro");
		m.put("disable_meltdown", "Ne plu kontroli Meltdown");
		m.put("disable_spectre", "Ne plu kontroli Spectre");
		m.put("open_docs", "Malfermi la oficialan dokumentaron");
		m.put("check_error", "Ne eblis fini {0}: {1}");
		m.put("title_protection", "{0}: protekto de la procesoro");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "Fabrikanto");
		m.put("label_arch", "Arkitekturo");
		m.put("label_os", "Operaciumo");
		m.put("label_affected", "Afektiĝo de la CPU");
		m.put("label_overall", "Ĝenerala stato");
		m.put("label_os_mitigation", "Mildigo de la operaciumo");
		m.put("label_microcode", "Mikrokodo/firmvaro");
		m.put("label_revision", "Detektita revizio");
		m.put("label_note", "Noto");
		m.put("label_evidence", "Indico");
		m.put("label_action", "Rekomendita ago");
		m.put("state_affected", "Afektita");
		m.put("state_possible", "Eble afektita");
		m.put("state_not_affected", "Ne afektita");
		m.put("state_unknown", "Nekonata");
		m.put("state_mitigated", "Mildigita");
		m.put("state_partial", "Parte mildigita");
		m.put("state_vulnerable", "Vundebla");
		m.put("state_na", "Ne aplikebla");
		m.put("state_present", "Ĉeesta");
		m.put("state_absent", "Foresta");
		m.put("state_not_needed", "Ne bezonata");
		m.put("remote_note",
				"La CPU en la protokolo ne kongruas kun la komputilo, kiu rulas la detektilon. La stato de la lokaj flikaĵoj ne estis atribuita al la protokolo.");
		m.put("unknown_text", "Netradukita teksto pri CPU-sekureco: {0}");
		m.put("remote_evidence",
				"La CPU en la protokolo diferencas de la loka CPU; la stato de lokaj flikaĵoj ne estis kontrolita.");
		m.put("remote_action", "Rulu la kontrolon en la komputilo, kiu produktis la protokolon.");
		m.put("unknown_for_log", "Ne determinita por la protokolo");
		m.put("no_runtime_status", "{0} ne liveras fidindan porteblan rultempan staton por ĉi tiuj mildigoj.");
		m.put("update_and_verify",
				"Ĝisdatigu {0}, instalu aktualan firmvaron aŭ mikrokodon, restartigu kaj kontrolu: {1}.");
		m.put("kernel_missing", "La kerno ne elmontras {0}.");
		m.put("update_kernel",
				"Ĝisdatigu la kernon kaj la firmvaron aŭ mikrokodon de la CPU, restartigu kaj denove kontrolu {0}.");
		m.put("no_action", "Neniu ago necesas por la originalaj variantoj raportitaj kiel mildigitaj.");
		m.put("cpu_not_affected", "La operaciumo raportas, ke ĉi tiu procesoro ne estas afektita.");
		m.put("mitigation_active", "La mildigo de la operaciumo estas aktiva.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "Ŝaltu aŭ ĝisdatigu {0}, restartigu kaj denove kontrolu ĝin.");
		m.put("cannot_read", "Ne eblis legi {0}.");
		m.put("partial_status", "{0} ŝajnas nur parte konfirmita; kontrolu {1}.");
		m.put("not_exposed", "Ne elmontrita de {0}");
		m.put("update_os_firmware_verify", "Ĝisdatigu {0} kaj {1}, restartigu kaj kontrolu per {2}.");
		TEXTOS.put("eo", Collections.unmodifiableMap(m));
	}

	private static void cargar_fr() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown : atténuation au niveau du processeur");
		m.put("check_spectre", "Spectre V1/V2 : atténuation au niveau du processeur");
		m.put("disable_meltdown", "Ne plus vérifier Meltdown");
		m.put("disable_spectre", "Ne plus vérifier Spectre");
		m.put("open_docs", "Ouvrir la documentation officielle");
		m.put("check_error", "Impossible de terminer {0} : {1}");
		m.put("title_protection", "{0} : protection du processeur");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "Fabricant");
		m.put("label_arch", "Architecture");
		m.put("label_os", "Système d’exploitation");
		m.put("label_affected", "Exposition du CPU");
		m.put("label_overall", "État général");
		m.put("label_os_mitigation", "Atténuation du système");
		m.put("label_microcode", "Microcode/micrologiciel");
		m.put("label_revision", "Révision détectée");
		m.put("label_note", "Remarque");
		m.put("label_evidence", "Éléments de preuve");
		m.put("label_action", "Action recommandée");
		m.put("state_affected", "Affecté");
		m.put("state_possible", "Potentiellement affecté");
		m.put("state_not_affected", "Non affecté");
		m.put("state_unknown", "Inconnu");
		m.put("state_mitigated", "Atténué");
		m.put("state_partial", "Partiellement atténué");
		m.put("state_vulnerable", "Vulnérable");
		m.put("state_na", "Sans objet");
		m.put("state_present", "Présent");
		m.put("state_absent", "Absent");
		m.put("state_not_needed", "Non requis");
		m.put("remote_note",
				"Le CPU indiqué dans le journal ne correspond pas à la machine exécutant le détecteur. L’état des correctifs de la machine locale n’a pas été attribué au journal.");
		m.put("unknown_text", "Texte de sécurité CPU non traduit : {0}");
		m.put("remote_evidence",
				"Le CPU du journal diffère du CPU local ; l’état des correctifs locaux n’a pas été interrogé.");
		m.put("remote_action", "Exécutez la vérification sur la machine qui a produit le journal.");
		m.put("unknown_for_log", "Non déterminé pour le journal");
		m.put("no_runtime_status",
				"{0} n’expose pas d’état d’exécution portable et fiable pour ces mesures d’atténuation.");
		m.put("update_and_verify",
				"Mettez à jour {0}, installez le micrologiciel ou microcode actuel, redémarrez, puis vérifiez : {1}.");
		m.put("kernel_missing", "Le noyau n’expose pas {0}.");
		m.put("update_kernel",
				"Mettez à jour le noyau ainsi que le micrologiciel ou microcode du CPU, redémarrez, puis vérifiez de nouveau {0}.");
		m.put("no_action", "Aucune action n’est requise pour les variantes d’origine signalées comme atténuées.");
		m.put("cpu_not_affected", "Le système d’exploitation indique que ce processeur n’est pas affecté.");
		m.put("mitigation_active", "La mesure d’atténuation du système d’exploitation est active.");
		m.put("feature_status", "{0} : {1}.");
		m.put("feature_update", "Activez ou mettez à jour {0}, redémarrez, puis vérifiez de nouveau.");
		m.put("cannot_read", "Impossible de lire {0}.");
		m.put("partial_status", "{0} ne semble que partiellement confirmé ; vérifiez {1}.");
		m.put("not_exposed", "Non exposé par {0}");
		m.put("update_os_firmware_verify", "Mettez à jour {0} et {1}, redémarrez, puis vérifiez avec {2}.");
		TEXTOS.put("fr", Collections.unmodifiableMap(m));
	}

	private static void cargar_id() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: mitigasi prosesor");
		m.put("check_spectre", "Spectre V1/V2: mitigasi prosesor");
		m.put("disable_meltdown", "Jangan periksa Meltdown lagi");
		m.put("disable_spectre", "Jangan periksa Spectre lagi");
		m.put("open_docs", "Buka dokumentasi resmi");
		m.put("check_error", "Tidak dapat menyelesaikan {0}: {1}");
		m.put("title_protection", "{0}: perlindungan prosesor");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "Produsen");
		m.put("label_arch", "Arsitektur");
		m.put("label_os", "Sistem operasi");
		m.put("label_affected", "Dampak pada CPU");
		m.put("label_overall", "Status keseluruhan");
		m.put("label_os_mitigation", "Mitigasi sistem operasi");
		m.put("label_microcode", "Mikrokode/firmware");
		m.put("label_revision", "Revisi yang terdeteksi");
		m.put("label_note", "Catatan");
		m.put("label_evidence", "Bukti");
		m.put("label_action", "Tindakan yang disarankan");
		m.put("state_affected", "Terdampak");
		m.put("state_possible", "Mungkin terdampak");
		m.put("state_not_affected", "Tidak terdampak");
		m.put("state_unknown", "Tidak diketahui");
		m.put("state_mitigated", "Telah dimitigasi");
		m.put("state_partial", "Dimitigasi sebagian");
		m.put("state_vulnerable", "Rentan");
		m.put("state_na", "Tidak berlaku");
		m.put("state_present", "Tersedia");
		m.put("state_absent", "Tidak tersedia");
		m.put("state_not_needed", "Tidak diperlukan");
		m.put("remote_note",
				"CPU dalam log tidak cocok dengan komputer yang menjalankan pendeteksi. Status tambalan komputer lokal tidak diterapkan pada log.");
		m.put("unknown_text", "Teks keamanan CPU belum diterjemahkan: {0}");
		m.put("remote_evidence", "CPU dalam log berbeda dari CPU lokal; status tambalan lokal tidak diperiksa.");
		m.put("remote_action", "Jalankan pemeriksaan pada komputer yang menghasilkan log.");
		m.put("unknown_for_log", "Tidak dapat ditentukan dari log");
		m.put("no_runtime_status", "{0} tidak menyediakan status runtime portabel yang andal untuk mitigasi ini.");
		m.put("update_and_verify",
				"Perbarui {0}, pasang firmware atau mikrokode terbaru, mulai ulang, lalu verifikasi: {1}.");
		m.put("kernel_missing", "Kernel tidak mengekspos {0}.");
		m.put("update_kernel",
				"Perbarui kernel dan firmware atau mikrokode CPU, mulai ulang, lalu periksa kembali {0}.");
		m.put("no_action", "Tidak diperlukan tindakan untuk varian asli yang dilaporkan telah dimitigasi.");
		m.put("cpu_not_affected", "Sistem operasi melaporkan bahwa prosesor ini tidak terdampak.");
		m.put("mitigation_active", "Mitigasi sistem operasi aktif.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "Aktifkan atau perbarui {0}, mulai ulang, lalu verifikasi kembali.");
		m.put("cannot_read", "Tidak dapat membaca {0}.");
		m.put("partial_status", "{0} tampaknya baru dikonfirmasi sebagian; verifikasi {1}.");
		m.put("not_exposed", "Tidak diekspos oleh {0}");
		m.put("update_os_firmware_verify", "Perbarui {0} dan {1}, mulai ulang, lalu verifikasi dengan {2}.");
		TEXTOS.put("id", Collections.unmodifiableMap(m));
	}

	private static void cargar_en() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: processor mitigation");
		m.put("check_spectre", "Spectre V1/V2: processor mitigation");
		m.put("disable_meltdown", "Do not check Meltdown again");
		m.put("disable_spectre", "Do not check Spectre again");
		m.put("open_docs", "Open official documentation");
		m.put("check_error", "Could not complete {0}: {1}");
		m.put("title_protection", "{0}: processor protection");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "Manufacturer");
		m.put("label_arch", "Architecture");
		m.put("label_os", "Operating system");
		m.put("label_affected", "CPU exposure");
		m.put("label_overall", "Overall status");
		m.put("label_os_mitigation", "OS mitigation");
		m.put("label_microcode", "Microcode/firmware");
		m.put("label_revision", "Detected revision");
		m.put("label_note", "Note");
		m.put("label_evidence", "Evidence");
		m.put("label_action", "Recommended action");
		m.put("state_affected", "Affected");
		m.put("state_possible", "Possibly affected");
		m.put("state_not_affected", "Not affected");
		m.put("state_unknown", "Unknown");
		m.put("state_mitigated", "Mitigated");
		m.put("state_partial", "Partially mitigated");
		m.put("state_vulnerable", "Vulnerable");
		m.put("state_na", "Not applicable");
		m.put("state_present", "Present");
		m.put("state_absent", "Absent");
		m.put("state_not_needed", "Not required");
		m.put("remote_note",
				"The CPU in the log does not match the machine running the detector. The local machine's patch status was not assigned to the log.");
		m.put("unknown_text", "Untranslated CPU security text: {0}");
		m.put("remote_evidence", "The CPU in the log differs from the local CPU; local patch status was not queried.");
		m.put("remote_action", "Run the check on the machine that produced the log.");
		m.put("unknown_for_log", "Not determined for the log");
		m.put("no_runtime_status", "{0} does not expose a reliable portable runtime status for these mitigations.");
		m.put("update_and_verify", "Update {0}, install current firmware or microcode, restart, and verify: {1}.");
		m.put("kernel_missing", "The kernel does not expose {0}.");
		m.put("update_kernel", "Update the kernel and the CPU firmware or microcode, restart, and check {0} again.");
		m.put("no_action", "No action is required for the original variants reported as mitigated.");
		m.put("cpu_not_affected", "The operating system reports that this processor is not affected.");
		m.put("mitigation_active", "The operating-system mitigation is active.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "Enable or update {0}, restart, and verify it again.");
		m.put("cannot_read", "Could not read {0}.");
		m.put("partial_status", "{0} appears only partially confirmed; verify {1}.");
		m.put("not_exposed", "Not exposed by {0}");
		m.put("update_os_firmware_verify", "Update {0} and {1}, restart, and verify with {2}.");
		TEXTOS.put("en", Collections.unmodifiableMap(m));
	}

	private static void cargar_ja() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown：プロセッサー緩和策");
		m.put("check_spectre", "Spectre V1/V2：プロセッサー緩和策");
		m.put("disable_meltdown", "Meltdown を今後確認しない");
		m.put("disable_spectre", "Spectre を今後確認しない");
		m.put("open_docs", "公式ドキュメントを開く");
		m.put("check_error", "{0} を完了できませんでした：{1}");
		m.put("title_protection", "{0}：プロセッサー保護");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "製造元");
		m.put("label_arch", "アーキテクチャ");
		m.put("label_os", "オペレーティングシステム");
		m.put("label_affected", "CPU の影響状況");
		m.put("label_overall", "全体の状態");
		m.put("label_os_mitigation", "OS の緩和策");
		m.put("label_microcode", "マイクロコード/ファームウェア");
		m.put("label_revision", "検出されたリビジョン");
		m.put("label_note", "注記");
		m.put("label_evidence", "根拠");
		m.put("label_action", "推奨される操作");
		m.put("state_affected", "影響あり");
		m.put("state_possible", "影響を受ける可能性あり");
		m.put("state_not_affected", "影響なし");
		m.put("state_unknown", "不明");
		m.put("state_mitigated", "緩和済み");
		m.put("state_partial", "一部緩和済み");
		m.put("state_vulnerable", "脆弱");
		m.put("state_na", "該当なし");
		m.put("state_present", "あり");
		m.put("state_absent", "なし");
		m.put("state_not_needed", "不要");
		m.put("remote_note", "ログ内の CPU は、検出器を実行しているコンピューターと一致しません。ローカルコンピューターのパッチ状態はログに適用されていません。");
		m.put("unknown_text", "未翻訳の CPU セキュリティテキスト：{0}");
		m.put("remote_evidence", "ログ内の CPU はローカル CPU と異なるため、ローカルのパッチ状態は照会されませんでした。");
		m.put("remote_action", "そのログを生成したコンピューターで確認を実行してください。");
		m.put("unknown_for_log", "ログからは判定できません");
		m.put("no_runtime_status", "{0} は、これらの緩和策について信頼できる移植可能な実行時状態を公開していません。");
		m.put("update_and_verify", "{0} を更新し、最新のファームウェアまたはマイクロコードを導入して再起動し、次を確認してください：{1}。");
		m.put("kernel_missing", "カーネルは {0} を公開していません。");
		m.put("update_kernel", "カーネルと CPU のファームウェアまたはマイクロコードを更新して再起動し、{0} を再確認してください。");
		m.put("no_action", "緩和済みと報告された元の亜種については、追加の操作は不要です。");
		m.put("cpu_not_affected", "オペレーティングシステムは、このプロセッサーが影響を受けないと報告しています。");
		m.put("mitigation_active", "OS の緩和策は有効です。");
		m.put("feature_status", "{0}：{1}。");
		m.put("feature_update", "{0} を有効化または更新して再起動し、もう一度確認してください。");
		m.put("cannot_read", "{0} を読み取れませんでした。");
		m.put("partial_status", "{0} は一部しか確認されていないようです。{1} を確認してください。");
		m.put("not_exposed", "{0} では公開されていません");
		m.put("update_os_firmware_verify", "{0} と {1} を更新して再起動し、{2} で確認してください。");
		TEXTOS.put("ja", Collections.unmodifiableMap(m));
	}

	private static void cargar_km() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown៖ វិធានការកាត់បន្ថយហានិភ័យរបស់អង្គដំណើរការ");
		m.put("check_spectre", "Spectre V1/V2៖ វិធានការកាត់បន្ថយហានិភ័យរបស់អង្គដំណើរការ");
		m.put("disable_meltdown", "កុំត្រួតពិនិត្យ Meltdown ម្តងទៀត");
		m.put("disable_spectre", "កុំត្រួតពិនិត្យ Spectre ម្តងទៀត");
		m.put("open_docs", "បើកឯកសារផ្លូវការ");
		m.put("check_error", "មិនអាចបញ្ចប់ {0} បានទេ៖ {1}");
		m.put("title_protection", "{0}៖ ការការពារអង្គដំណើរការ");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "ក្រុមហ៊ុនផលិត");
		m.put("label_arch", "ស្ថាបត្យកម្ម");
		m.put("label_os", "ប្រព័ន្ធប្រតិបត្តិការ");
		m.put("label_affected", "ស្ថានភាពរងផលប៉ះពាល់របស់ CPU");
		m.put("label_overall", "ស្ថានភាពទូទៅ");
		m.put("label_os_mitigation", "វិធានការកាត់បន្ថយរបស់ប្រព័ន្ធប្រតិបត្តិការ");
		m.put("label_microcode", "មីក្រូកូដ/កម្មវិធីបង្កប់");
		m.put("label_revision", "កំណែដែលបានរកឃើញ");
		m.put("label_note", "ចំណាំ");
		m.put("label_evidence", "ភស្តុតាង");
		m.put("label_action", "សកម្មភាពដែលបានណែនាំ");
		m.put("state_affected", "រងផលប៉ះពាល់");
		m.put("state_possible", "អាចរងផលប៉ះពាល់");
		m.put("state_not_affected", "មិនរងផលប៉ះពាល់");
		m.put("state_unknown", "មិនស្គាល់");
		m.put("state_mitigated", "បានកាត់បន្ថយ");
		m.put("state_partial", "បានកាត់បន្ថយមួយផ្នែក");
		m.put("state_vulnerable", "ងាយរងគ្រោះ");
		m.put("state_na", "មិនអនុវត្ត");
		m.put("state_present", "មាន");
		m.put("state_absent", "អវត្តមាន");
		m.put("state_not_needed", "មិនចាំបាច់");
		m.put("remote_note",
				"CPU ក្នុងកំណត់ហេតុមិនត្រូវនឹងម៉ាស៊ីនដែលកំពុងដំណើរការឧបករណ៍រកឃើញទេ។ ស្ថានភាពបំណះរបស់ម៉ាស៊ីនមូលដ្ឋានមិនត្រូវបានភ្ជាប់ទៅកំណត់ហេតុនោះទេ។");
		m.put("unknown_text", "អត្ថបទសុវត្ថិភាព CPU ដែលមិនទាន់បកប្រែ៖ {0}");
		m.put("remote_evidence", "CPU ក្នុងកំណត់ហេតុខុសពី CPU មូលដ្ឋាន ដូច្នេះមិនបានពិនិត្យស្ថានភាពបំណះមូលដ្ឋានទេ។");
		m.put("remote_action", "ដំណើរការការត្រួតពិនិត្យលើម៉ាស៊ីនដែលបានបង្កើតកំណត់ហេតុ។");
		m.put("unknown_for_log", "មិនអាចកំណត់ពីកំណត់ហេតុ");
		m.put("no_runtime_status",
				"{0} មិនបង្ហាញស្ថានភាពពេលដំណើរការដែលអាចទុកចិត្តបាន និងអាចផ្ទេរបានសម្រាប់វិធានការទាំងនេះទេ។");
		m.put("update_and_verify",
				"ធ្វើបច្ចុប្បន្នភាព {0} ដំឡើងកម្មវិធីបង្កប់ ឬមីក្រូកូដថ្មី បើកម៉ាស៊ីនឡើងវិញ ហើយផ្ទៀងផ្ទាត់៖ {1}។");
		m.put("kernel_missing", "ខឺណែលមិនបង្ហាញ {0} ទេ។");
		m.put("update_kernel",
				"ធ្វើបច្ចុប្បន្នភាពខឺណែល និងកម្មវិធីបង្កប់ ឬមីក្រូកូដ CPU បើកម៉ាស៊ីនឡើងវិញ ហើយពិនិត្យ {0} ម្តងទៀត។");
		m.put("no_action", "មិនចាំបាច់ធ្វើសកម្មភាពសម្រាប់វ៉ារ្យ៉ង់ដើមដែលបានរាយការណ៍ថាបានកាត់បន្ថយទេ។");
		m.put("cpu_not_affected", "ប្រព័ន្ធប្រតិបត្តិការរាយការណ៍ថាអង្គដំណើរការនេះមិនរងផលប៉ះពាល់ទេ។");
		m.put("mitigation_active", "វិធានការកាត់បន្ថយរបស់ប្រព័ន្ធប្រតិបត្តិការកំពុងសកម្ម។");
		m.put("feature_status", "{0}៖ {1}។");
		m.put("feature_update", "បើក ឬធ្វើបច្ចុប្បន្នភាព {0} បើកម៉ាស៊ីនឡើងវិញ ហើយផ្ទៀងផ្ទាត់ម្តងទៀត។");
		m.put("cannot_read", "មិនអាចអាន {0} បានទេ។");
		m.put("partial_status", "{0} ហាក់ដូចជាត្រូវបានបញ្ជាក់តែផ្នែកខ្លះប៉ុណ្ណោះ សូមផ្ទៀងផ្ទាត់ {1}។");
		m.put("not_exposed", "មិនត្រូវបានបង្ហាញដោយ {0}");
		m.put("update_os_firmware_verify",
				"ធ្វើបច្ចុប្បន្នភាព {0} និង {1} បើកម៉ាស៊ីនឡើងវិញ ហើយផ្ទៀងផ្ទាត់ដោយប្រើ {2}។");
		TEXTOS.put("km", Collections.unmodifiableMap(m));
	}

	private static void cargar_lo() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: ການບັນເທົາຊ່ອງໂຫວ່ຂອງໂປຣເຊສເຊີ");
		m.put("check_spectre", "Spectre V1/V2: ການບັນເທົາຊ່ອງໂຫວ່ຂອງໂປຣເຊສເຊີ");
		m.put("disable_meltdown", "ບໍ່ຕ້ອງກວດ Meltdown ອີກ");
		m.put("disable_spectre", "ບໍ່ຕ້ອງກວດ Spectre ອີກ");
		m.put("open_docs", "ເປີດເອກະສານທາງການ");
		m.put("check_error", "ບໍ່ສາມາດສຳເລັດ {0}: {1}");
		m.put("title_protection", "{0}: ການປ້ອງກັນໂປຣເຊສເຊີ");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "ຜູ້ຜະລິດ");
		m.put("label_arch", "ສະຖາປັດຕະຍະກຳ");
		m.put("label_os", "ລະບົບປະຕິບັດການ");
		m.put("label_affected", "ສະຖານະການໄດ້ຮັບຜົນຂອງ CPU");
		m.put("label_overall", "ສະຖານະລວມ");
		m.put("label_os_mitigation", "ການບັນເທົາຂອງລະບົບປະຕິບັດການ");
		m.put("label_microcode", "ໄມໂຄຣໂຄດ/ເຟີມແວ");
		m.put("label_revision", "ສະບັບທີ່ກວດພົບ");
		m.put("label_note", "ໝາຍເຫດ");
		m.put("label_evidence", "ຫຼັກຖານ");
		m.put("label_action", "ການດຳເນີນການທີ່ແນະນຳ");
		m.put("state_affected", "ໄດ້ຮັບຜົນ");
		m.put("state_possible", "ອາດໄດ້ຮັບຜົນ");
		m.put("state_not_affected", "ບໍ່ໄດ້ຮັບຜົນ");
		m.put("state_unknown", "ບໍ່ຮູ້");
		m.put("state_mitigated", "ໄດ້ບັນເທົາແລ້ວ");
		m.put("state_partial", "ໄດ້ບັນເທົາບາງສ່ວນ");
		m.put("state_vulnerable", "ມີຊ່ອງໂຫວ່");
		m.put("state_na", "ບໍ່ນຳໃຊ້");
		m.put("state_present", "ມີ");
		m.put("state_absent", "ບໍ່ມີ");
		m.put("state_not_needed", "ບໍ່ຈຳເປັນ");
		m.put("remote_note",
				"CPU ໃນບັນທຶກບໍ່ກົງກັບເຄື່ອງທີ່ກຳລັງເປີດຕົວກວດ. ສະຖານະແພັດຂອງເຄື່ອງທ້ອງຖິ່ນບໍ່ໄດ້ນຳໄປໃຊ້ກັບບັນທຶກ.");
		m.put("unknown_text", "ຂໍ້ຄວາມຄວາມປອດໄພ CPU ທີ່ຍັງບໍ່ໄດ້ແປ: {0}");
		m.put("remote_evidence", "CPU ໃນບັນທຶກແຕກຕ່າງຈາກ CPU ທ້ອງຖິ່ນ; ບໍ່ໄດ້ກວດສະຖານະແພັດທ້ອງຖິ່ນ.");
		m.put("remote_action", "ໃຫ້ເປີດການກວດໃນເຄື່ອງທີ່ສ້າງບັນທຶກ.");
		m.put("unknown_for_log", "ບໍ່ສາມາດກຳນົດຈາກບັນທຶກ");
		m.put("no_runtime_status", "{0} ບໍ່ສະແດງສະຖານະຂະນະເຮັດວຽກທີ່ເຊື່ອຖືໄດ້ແລະພົກພາໄດ້ສຳລັບການບັນເທົາເຫຼົ່ານີ້.");
		m.put("update_and_verify", "ອັບເດດ {0}, ຕິດຕັ້ງເຟີມແວຫຼືໄມໂຄຣໂຄດປັດຈຸບັນ, ເລີ່ມໃໝ່ ແລະກວດສອບ: {1}.");
		m.put("kernel_missing", "ເຄີເນວບໍ່ສະແດງ {0}.");
		m.put("update_kernel", "ອັບເດດເຄີເນວແລະເຟີມແວຫຼືໄມໂຄຣໂຄດ CPU, ເລີ່ມໃໝ່ ແລະກວດ {0} ອີກຄັ້ງ.");
		m.put("no_action", "ບໍ່ຈຳເປັນຕ້ອງດຳເນີນການສຳລັບຕົວແປດັ້ງເດີມທີ່ລາຍງານວ່າໄດ້ບັນເທົາແລ້ວ.");
		m.put("cpu_not_affected", "ລະບົບປະຕິບັດການລາຍງານວ່າໂປຣເຊສເຊີນີ້ບໍ່ໄດ້ຮັບຜົນ.");
		m.put("mitigation_active", "ການບັນເທົາຂອງລະບົບປະຕິບັດການກຳລັງເຮັດວຽກ.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "ເປີດໃຊ້ຫຼືອັບເດດ {0}, ເລີ່ມໃໝ່ ແລະກວດສອບອີກຄັ້ງ.");
		m.put("cannot_read", "ບໍ່ສາມາດອ່ານ {0}.");
		m.put("partial_status", "{0} ເບິ່ງຄືວ່າຢືນຢັນໄດ້ພຽງບາງສ່ວນ; ໃຫ້ກວດສອບ {1}.");
		m.put("not_exposed", "ບໍ່ຖືກສະແດງໂດຍ {0}");
		m.put("update_os_firmware_verify", "ອັບເດດ {0} ແລະ {1}, ເລີ່ມໃໝ່ ແລະກວດດ້ວຍ {2}.");
		TEXTOS.put("lo", Collections.unmodifiableMap(m));
	}

	private static void cargar_ms() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: mitigasi pemproses");
		m.put("check_spectre", "Spectre V1/V2: mitigasi pemproses");
		m.put("disable_meltdown", "Jangan periksa Meltdown lagi");
		m.put("disable_spectre", "Jangan periksa Spectre lagi");
		m.put("open_docs", "Buka dokumentasi rasmi");
		m.put("check_error", "Tidak dapat menyelesaikan {0}: {1}");
		m.put("title_protection", "{0}: perlindungan pemproses");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "Pengilang");
		m.put("label_arch", "Seni bina");
		m.put("label_os", "Sistem pengendalian");
		m.put("label_affected", "Kesan terhadap CPU");
		m.put("label_overall", "Status keseluruhan");
		m.put("label_os_mitigation", "Mitigasi sistem pengendalian");
		m.put("label_microcode", "Mikrokod/perisian tegar");
		m.put("label_revision", "Semakan yang dikesan");
		m.put("label_note", "Nota");
		m.put("label_evidence", "Bukti");
		m.put("label_action", "Tindakan yang disyorkan");
		m.put("state_affected", "Terjejas");
		m.put("state_possible", "Mungkin terjejas");
		m.put("state_not_affected", "Tidak terjejas");
		m.put("state_unknown", "Tidak diketahui");
		m.put("state_mitigated", "Telah dimitigasi");
		m.put("state_partial", "Dimitigasi sebahagian");
		m.put("state_vulnerable", "Terdedah");
		m.put("state_na", "Tidak berkenaan");
		m.put("state_present", "Hadir");
		m.put("state_absent", "Tiada");
		m.put("state_not_needed", "Tidak diperlukan");
		m.put("remote_note",
				"CPU dalam log tidak sepadan dengan komputer yang menjalankan pengesan. Status tampalan komputer setempat tidak dikaitkan dengan log tersebut.");
		m.put("unknown_text", "Teks keselamatan CPU belum diterjemahkan: {0}");
		m.put("remote_evidence",
				"CPU dalam log berbeza daripada CPU setempat; status tampalan setempat tidak diperiksa.");
		m.put("remote_action", "Jalankan pemeriksaan pada komputer yang menghasilkan log.");
		m.put("unknown_for_log", "Tidak dapat ditentukan daripada log");
		m.put("no_runtime_status",
				"{0} tidak mendedahkan status masa jalan mudah alih yang boleh dipercayai untuk mitigasi ini.");
		m.put("update_and_verify",
				"Kemas kini {0}, pasang perisian tegar atau mikrokod semasa, mulakan semula dan sahkan: {1}.");
		m.put("kernel_missing", "Kernel tidak mendedahkan {0}.");
		m.put("update_kernel",
				"Kemas kini kernel dan perisian tegar atau mikrokod CPU, mulakan semula dan semak {0} sekali lagi.");
		m.put("no_action", "Tiada tindakan diperlukan untuk varian asal yang dilaporkan telah dimitigasi.");
		m.put("cpu_not_affected", "Sistem pengendalian melaporkan bahawa pemproses ini tidak terjejas.");
		m.put("mitigation_active", "Mitigasi sistem pengendalian aktif.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "Dayakan atau kemas kini {0}, mulakan semula dan sahkan sekali lagi.");
		m.put("cannot_read", "Tidak dapat membaca {0}.");
		m.put("partial_status", "{0} nampaknya hanya disahkan sebahagian; sahkan {1}.");
		m.put("not_exposed", "Tidak didedahkan oleh {0}");
		m.put("update_os_firmware_verify", "Kemas kini {0} dan {1}, mulakan semula dan sahkan dengan {2}.");
		TEXTOS.put("ms", Collections.unmodifiableMap(m));
	}

	private static void cargar_fa() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: کاهش مخاطرهٔ پردازنده");
		m.put("check_spectre", "Spectre V1/V2: کاهش مخاطرهٔ پردازنده");
		m.put("disable_meltdown", "دیگر Meltdown بررسی نشود");
		m.put("disable_spectre", "دیگر Spectre بررسی نشود");
		m.put("open_docs", "باز کردن مستندات رسمی");
		m.put("check_error", "تکمیل {0} ممکن نشد: {1}");
		m.put("title_protection", "{0}: حفاظت پردازنده");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "سازنده");
		m.put("label_arch", "معماری");
		m.put("label_os", "سیستم‌عامل");
		m.put("label_affected", "میزان تأثیرپذیری CPU");
		m.put("label_overall", "وضعیت کلی");
		m.put("label_os_mitigation", "کاهش مخاطرهٔ سیستم‌عامل");
		m.put("label_microcode", "میکروکد/میان‌افزار");
		m.put("label_revision", "بازبینی شناسایی‌شده");
		m.put("label_note", "یادداشت");
		m.put("label_evidence", "شواهد");
		m.put("label_action", "اقدام پیشنهادی");
		m.put("state_affected", "تأثیرپذیر");
		m.put("state_possible", "احتمالاً تأثیرپذیر");
		m.put("state_not_affected", "تأثیرناپذیر");
		m.put("state_unknown", "نامشخص");
		m.put("state_mitigated", "کاهش‌یافته");
		m.put("state_partial", "تا حدی کاهش‌یافته");
		m.put("state_vulnerable", "آسیب‌پذیر");
		m.put("state_na", "قابل اعمال نیست");
		m.put("state_present", "موجود");
		m.put("state_absent", "ناموجود");
		m.put("state_not_needed", "لازم نیست");
		m.put("remote_note",
				"CPU موجود در گزارش با دستگاهی که آشکارساز را اجرا می‌کند یکسان نیست. وضعیت وصله‌های دستگاه محلی به گزارش نسبت داده نشد.");
		m.put("unknown_text", "متن ترجمه‌نشدهٔ امنیت CPU: {0}");
		m.put("remote_evidence", "CPU گزارش با CPU محلی متفاوت است؛ وضعیت وصله‌های محلی بررسی نشد.");
		m.put("remote_action", "بررسی را روی دستگاهی اجرا کنید که گزارش را تولید کرده است.");
		m.put("unknown_for_log", "برای گزارش تعیین نشد");
		m.put("no_runtime_status",
				"{0} وضعیت زمان اجرای قابل اعتماد و قابل‌حملی برای این کاهش مخاطره‌ها ارائه نمی‌کند.");
		m.put("update_and_verify",
				"{0} را به‌روزرسانی کنید، میان‌افزار یا میکروکد فعلی را نصب کنید، راه‌اندازی مجدد کنید و سپس بررسی کنید: {1}.");
		m.put("kernel_missing", "هسته {0} را ارائه نمی‌کند.");
		m.put("update_kernel",
				"هسته و میان‌افزار یا میکروکد CPU را به‌روزرسانی کنید، راه‌اندازی مجدد کنید و دوباره {0} را بررسی کنید.");
		m.put("no_action", "برای گونه‌های اصلی که کاهش‌یافته گزارش شده‌اند اقدامی لازم نیست.");
		m.put("cpu_not_affected", "سیستم‌عامل گزارش می‌کند که این پردازنده تأثیرپذیر نیست.");
		m.put("mitigation_active", "کاهش مخاطرهٔ سیستم‌عامل فعال است.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "{0} را فعال یا به‌روزرسانی کنید، راه‌اندازی مجدد کنید و دوباره بررسی کنید.");
		m.put("cannot_read", "خواندن {0} ممکن نشد.");
		m.put("partial_status", "به نظر می‌رسد {0} فقط تا حدی تأیید شده است؛ {1} را بررسی کنید.");
		m.put("not_exposed", "توسط {0} ارائه نشده است");
		m.put("update_os_firmware_verify", "{0} و {1} را به‌روزرسانی کنید، راه‌اندازی مجدد کنید و با {2} بررسی کنید.");
		TEXTOS.put("fa", Collections.unmodifiableMap(m));
	}

	private static void cargar_pt() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: mitigação do processador");
		m.put("check_spectre", "Spectre V1/V2: mitigação do processador");
		m.put("disable_meltdown", "Não verificar Meltdown novamente");
		m.put("disable_spectre", "Não verificar Spectre novamente");
		m.put("open_docs", "Abrir a documentação oficial");
		m.put("check_error", "Não foi possível concluir {0}: {1}");
		m.put("title_protection", "{0}: proteção do processador");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "Fabricante");
		m.put("label_arch", "Arquitetura");
		m.put("label_os", "Sistema operacional");
		m.put("label_affected", "Exposição da CPU");
		m.put("label_overall", "Status geral");
		m.put("label_os_mitigation", "Mitigação do sistema operacional");
		m.put("label_microcode", "Microcódigo/firmware");
		m.put("label_revision", "Revisão detectada");
		m.put("label_note", "Observação");
		m.put("label_evidence", "Evidência");
		m.put("label_action", "Ação recomendada");
		m.put("state_affected", "Afetado");
		m.put("state_possible", "Possivelmente afetado");
		m.put("state_not_affected", "Não afetado");
		m.put("state_unknown", "Desconhecido");
		m.put("state_mitigated", "Mitigado");
		m.put("state_partial", "Parcialmente mitigado");
		m.put("state_vulnerable", "Vulnerável");
		m.put("state_na", "Não se aplica");
		m.put("state_present", "Presente");
		m.put("state_absent", "Ausente");
		m.put("state_not_needed", "Não necessário");
		m.put("remote_note",
				"A CPU no log não corresponde ao computador que está executando o detector. O status de correções do computador local não foi atribuído ao log.");
		m.put("unknown_text", "Texto de segurança da CPU não traduzido: {0}");
		m.put("remote_evidence",
				"A CPU no log é diferente da CPU local; o status das correções locais não foi consultado.");
		m.put("remote_action", "Execute a verificação no computador que gerou o log.");
		m.put("unknown_for_log", "Não determinado para o log");
		m.put("no_runtime_status", "{0} não expõe um status de execução portátil e confiável para essas mitigações.");
		m.put("update_and_verify", "Atualize {0}, instale o firmware ou microcódigo atual, reinicie e verifique: {1}.");
		m.put("kernel_missing", "O kernel não expõe {0}.");
		m.put("update_kernel",
				"Atualize o kernel e o firmware ou microcódigo da CPU, reinicie e verifique {0} novamente.");
		m.put("no_action", "Nenhuma ação é necessária para as variantes originais informadas como mitigadas.");
		m.put("cpu_not_affected", "O sistema operacional informa que este processador não é afetado.");
		m.put("mitigation_active", "A mitigação do sistema operacional está ativa.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "Ative ou atualize {0}, reinicie e verifique novamente.");
		m.put("cannot_read", "Não foi possível ler {0}.");
		m.put("partial_status", "{0} parece estar apenas parcialmente confirmado; verifique {1}.");
		m.put("not_exposed", "Não exposto por {0}");
		m.put("update_os_firmware_verify", "Atualize {0} e {1}, reinicie e verifique com {2}.");
		TEXTOS.put("pt", Collections.unmodifiableMap(m));
	}

	private static void cargar_ru() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: защита процессора");
		m.put("check_spectre", "Spectre V1/V2: защита процессора");
		m.put("disable_meltdown", "Больше не проверять Meltdown");
		m.put("disable_spectre", "Больше не проверять Spectre");
		m.put("open_docs", "Открыть официальную документацию");
		m.put("check_error", "Не удалось завершить {0}: {1}");
		m.put("title_protection", "{0}: защита процессора");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "Производитель");
		m.put("label_arch", "Архитектура");
		m.put("label_os", "Операционная система");
		m.put("label_affected", "Подверженность CPU");
		m.put("label_overall", "Общее состояние");
		m.put("label_os_mitigation", "Защита операционной системы");
		m.put("label_microcode", "Микрокод/прошивка");
		m.put("label_revision", "Обнаруженная ревизия");
		m.put("label_note", "Примечание");
		m.put("label_evidence", "Свидетельство");
		m.put("label_action", "Рекомендуемое действие");
		m.put("state_affected", "Подвержен");
		m.put("state_possible", "Возможно подвержен");
		m.put("state_not_affected", "Не подвержен");
		m.put("state_unknown", "Неизвестно");
		m.put("state_mitigated", "Защищён");
		m.put("state_partial", "Частично защищён");
		m.put("state_vulnerable", "Уязвим");
		m.put("state_na", "Не применимо");
		m.put("state_present", "Присутствует");
		m.put("state_absent", "Отсутствует");
		m.put("state_not_needed", "Не требуется");
		m.put("remote_note",
				"CPU в журнале не совпадает с компьютером, на котором запущен детектор. Состояние исправлений локального компьютера не было отнесено к журналу.");
		m.put("unknown_text", "Непереведённый текст безопасности CPU: {0}");
		m.put("remote_evidence",
				"CPU в журнале отличается от локального CPU; состояние локальных исправлений не проверялось.");
		m.put("remote_action", "Запустите проверку на компьютере, который создал журнал.");
		m.put("unknown_for_log", "Не определено для журнала");
		m.put("no_runtime_status",
				"{0} не предоставляет надёжного переносимого состояния этих мер защиты во время выполнения.");
		m.put("update_and_verify",
				"Обновите {0}, установите актуальную прошивку или микрокод, перезагрузите систему и проверьте: {1}.");
		m.put("kernel_missing", "Ядро не предоставляет {0}.");
		m.put("update_kernel",
				"Обновите ядро и прошивку или микрокод CPU, перезагрузите систему и снова проверьте {0}.");
		m.put("no_action", "Для исходных вариантов, отмеченных как защищённые, дополнительных действий не требуется.");
		m.put("cpu_not_affected", "Операционная система сообщает, что этот процессор не подвержен.");
		m.put("mitigation_active", "Защита операционной системы активна.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "Включите или обновите {0}, перезагрузите систему и проверьте ещё раз.");
		m.put("cannot_read", "Не удалось прочитать {0}.");
		m.put("partial_status", "{0} подтверждено лишь частично; проверьте {1}.");
		m.put("not_exposed", "Не предоставляется через {0}");
		m.put("update_os_firmware_verify", "Обновите {0} и {1}, перезагрузите систему и проверьте с помощью {2}.");
		TEXTOS.put("ru", Collections.unmodifiableMap(m));
	}

	private static void cargar_sw() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: upunguzaji wa hatari wa kichakataji");
		m.put("check_spectre", "Spectre V1/V2: upunguzaji wa hatari wa kichakataji");
		m.put("disable_meltdown", "Usikague Meltdown tena");
		m.put("disable_spectre", "Usikague Spectre tena");
		m.put("open_docs", "Fungua nyaraka rasmi");
		m.put("check_error", "Haikuwezekana kukamilisha {0}: {1}");
		m.put("title_protection", "{0}: ulinzi wa kichakataji");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "Mtengenezaji");
		m.put("label_arch", "Usanifu");
		m.put("label_os", "Mfumo wa uendeshaji");
		m.put("label_affected", "Hali ya kuathirika kwa CPU");
		m.put("label_overall", "Hali ya jumla");
		m.put("label_os_mitigation", "Upunguzaji wa hatari wa mfumo wa uendeshaji");
		m.put("label_microcode", "Msimbo mdogo/firmware");
		m.put("label_revision", "Toleo lililogunduliwa");
		m.put("label_note", "Dokezo");
		m.put("label_evidence", "Ushahidi");
		m.put("label_action", "Hatua inayopendekezwa");
		m.put("state_affected", "Imeathirika");
		m.put("state_possible", "Huenda imeathirika");
		m.put("state_not_affected", "Haijaathirika");
		m.put("state_unknown", "Haijulikani");
		m.put("state_mitigated", "Imepunguzwa hatari");
		m.put("state_partial", "Imepunguzwa kwa sehemu");
		m.put("state_vulnerable", "Ni dhaifu");
		m.put("state_na", "Haitumiki");
		m.put("state_present", "Ipo");
		m.put("state_absent", "Haipo");
		m.put("state_not_needed", "Haihitajiki");
		m.put("remote_note",
				"CPU iliyo kwenye kumbukumbu hailingani na kompyuta inayoendesha kigunduzi. Hali ya viraka vya kompyuta ya ndani haikuhusishwa na kumbukumbu hiyo.");
		m.put("unknown_text", "Maandishi ya usalama wa CPU ambayo hayajatafsiriwa: {0}");
		m.put("remote_evidence",
				"CPU iliyo kwenye kumbukumbu ni tofauti na CPU ya ndani; hali ya viraka vya ndani haikukaguliwa.");
		m.put("remote_action", "Endesha ukaguzi kwenye kompyuta iliyotengeneza kumbukumbu.");
		m.put("unknown_for_log", "Haijabainishwa kwa kumbukumbu");
		m.put("no_runtime_status",
				"{0} haitoi hali ya wakati wa utekelezaji inayotegemewa na inayohamishika kwa hatua hizi za kupunguza hatari.");
		m.put("update_and_verify",
				"Sasisha {0}, sakinisha firmware au msimbo mdogo wa sasa, anzisha upya, kisha uthibitishe: {1}.");
		m.put("kernel_missing", "Kernel haitoi {0}.");
		m.put("update_kernel",
				"Sasisha kernel na firmware au msimbo mdogo wa CPU, anzisha upya, kisha ukague {0} tena.");
		m.put("no_action", "Hakuna hatua inayohitajika kwa lahaja za awali zilizoripotiwa kuwa zimepunguzwa hatari.");
		m.put("cpu_not_affected", "Mfumo wa uendeshaji unaripoti kuwa kichakataji hiki hakijaathirika.");
		m.put("mitigation_active", "Hatua ya kupunguza hatari ya mfumo wa uendeshaji inatumika.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "Washa au sasisha {0}, anzisha upya, kisha uthibitishe tena.");
		m.put("cannot_read", "Haikuwezekana kusoma {0}.");
		m.put("partial_status", "{0} inaonekana kuthibitishwa kwa sehemu tu; thibitisha {1}.");
		m.put("not_exposed", "Haijaonyeshwa na {0}");
		m.put("update_os_firmware_verify", "Sasisha {0} na {1}, anzisha upya, kisha uthibitishe kwa kutumia {2}.");
		TEXTOS.put("sw", Collections.unmodifiableMap(m));
	}

	private static void cargar_th() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: การบรรเทาช่องโหว่ของหน่วยประมวลผล");
		m.put("check_spectre", "Spectre V1/V2: การบรรเทาช่องโหว่ของหน่วยประมวลผล");
		m.put("disable_meltdown", "ไม่ต้องตรวจสอบ Meltdown อีก");
		m.put("disable_spectre", "ไม่ต้องตรวจสอบ Spectre อีก");
		m.put("open_docs", "เปิดเอกสารอย่างเป็นทางการ");
		m.put("check_error", "ไม่สามารถดำเนินการ {0} ให้เสร็จได้: {1}");
		m.put("title_protection", "{0}: การป้องกันหน่วยประมวลผล");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "ผู้ผลิต");
		m.put("label_arch", "สถาปัตยกรรม");
		m.put("label_os", "ระบบปฏิบัติการ");
		m.put("label_affected", "สถานะการได้รับผลกระทบของ CPU");
		m.put("label_overall", "สถานะโดยรวม");
		m.put("label_os_mitigation", "การบรรเทาของระบบปฏิบัติการ");
		m.put("label_microcode", "ไมโครโค้ด/เฟิร์มแวร์");
		m.put("label_revision", "รีวิชันที่ตรวจพบ");
		m.put("label_note", "หมายเหตุ");
		m.put("label_evidence", "หลักฐาน");
		m.put("label_action", "การดำเนินการที่แนะนำ");
		m.put("state_affected", "ได้รับผลกระทบ");
		m.put("state_possible", "อาจได้รับผลกระทบ");
		m.put("state_not_affected", "ไม่ได้รับผลกระทบ");
		m.put("state_unknown", "ไม่ทราบ");
		m.put("state_mitigated", "บรรเทาแล้ว");
		m.put("state_partial", "บรรเทาบางส่วน");
		m.put("state_vulnerable", "มีช่องโหว่");
		m.put("state_na", "ไม่เกี่ยวข้อง");
		m.put("state_present", "มี");
		m.put("state_absent", "ไม่มี");
		m.put("state_not_needed", "ไม่จำเป็น");
		m.put("remote_note",
				"CPU ในบันทึกไม่ตรงกับเครื่องที่กำลังเรียกใช้ตัวตรวจจับ จึงไม่ได้ใช้สถานะแพตช์ของเครื่องภายในกับบันทึกนั้น");
		m.put("unknown_text", "ข้อความความปลอดภัย CPU ที่ยังไม่ได้แปล: {0}");
		m.put("remote_evidence", "CPU ในบันทึกแตกต่างจาก CPU ภายใน จึงไม่ได้ตรวจสอบสถานะแพตช์ภายใน");
		m.put("remote_action", "เรียกใช้การตรวจสอบบนเครื่องที่สร้างบันทึกนี้");
		m.put("unknown_for_log", "ไม่สามารถระบุจากบันทึก");
		m.put("no_runtime_status", "{0} ไม่แสดงสถานะขณะทำงานที่เชื่อถือได้และพกพาได้สำหรับมาตรการบรรเทาเหล่านี้");
		m.put("update_and_verify", "อัปเดต {0} ติดตั้งเฟิร์มแวร์หรือไมโครโค้ดปัจจุบัน รีสตาร์ต แล้วตรวจสอบ: {1}");
		m.put("kernel_missing", "เคอร์เนลไม่แสดง {0}");
		m.put("update_kernel", "อัปเดตเคอร์เนลและเฟิร์มแวร์หรือไมโครโค้ดของ CPU รีสตาร์ต แล้วตรวจสอบ {0} อีกครั้ง");
		m.put("no_action", "ไม่ต้องดำเนินการเพิ่มเติมสำหรับตัวแปรดั้งเดิมที่ระบบรายงานว่าบรรเทาแล้ว");
		m.put("cpu_not_affected", "ระบบปฏิบัติการรายงานว่าหน่วยประมวลผลนี้ไม่ได้รับผลกระทบ");
		m.put("mitigation_active", "การบรรเทาของระบบปฏิบัติการทำงานอยู่");
		m.put("feature_status", "{0}: {1}");
		m.put("feature_update", "เปิดใช้หรืออัปเดต {0} รีสตาร์ต แล้วตรวจสอบอีกครั้ง");
		m.put("cannot_read", "ไม่สามารถอ่าน {0}");
		m.put("partial_status", "{0} ดูเหมือนยืนยันได้เพียงบางส่วน โปรดตรวจสอบ {1}");
		m.put("not_exposed", "{0} ไม่ได้เปิดเผยข้อมูลนี้");
		m.put("update_os_firmware_verify", "อัปเดต {0} และ {1} รีสตาร์ต แล้วตรวจสอบด้วย {2}");
		TEXTOS.put("th", Collections.unmodifiableMap(m));
	}

	private static void cargar_uk() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: захист процесора");
		m.put("check_spectre", "Spectre V1/V2: захист процесора");
		m.put("disable_meltdown", "Більше не перевіряти Meltdown");
		m.put("disable_spectre", "Більше не перевіряти Spectre");
		m.put("open_docs", "Відкрити офіційну документацію");
		m.put("check_error", "Не вдалося завершити {0}: {1}");
		m.put("title_protection", "{0}: захист процесора");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "Виробник");
		m.put("label_arch", "Архітектура");
		m.put("label_os", "Операційна система");
		m.put("label_affected", "Схильність CPU до вразливості");
		m.put("label_overall", "Загальний стан");
		m.put("label_os_mitigation", "Захист операційної системи");
		m.put("label_microcode", "Мікрокод/прошивка");
		m.put("label_revision", "Виявлена ревізія");
		m.put("label_note", "Примітка");
		m.put("label_evidence", "Доказ");
		m.put("label_action", "Рекомендована дія");
		m.put("state_affected", "Уражений");
		m.put("state_possible", "Можливо уражений");
		m.put("state_not_affected", "Не уражений");
		m.put("state_unknown", "Невідомо");
		m.put("state_mitigated", "Захищено");
		m.put("state_partial", "Частково захищено");
		m.put("state_vulnerable", "Вразливий");
		m.put("state_na", "Не застосовується");
		m.put("state_present", "Присутній");
		m.put("state_absent", "Відсутній");
		m.put("state_not_needed", "Не потрібний");
		m.put("remote_note",
				"CPU в журналі не відповідає комп’ютеру, на якому запущено детектор. Стан виправлень локального комп’ютера не було віднесено до журналу.");
		m.put("unknown_text", "Неперекладений текст безпеки CPU: {0}");
		m.put("remote_evidence",
				"CPU в журналі відрізняється від локального CPU; стан локальних виправлень не перевірявся.");
		m.put("remote_action", "Запустіть перевірку на комп’ютері, який створив журнал.");
		m.put("unknown_for_log", "Не визначено для журналу");
		m.put("no_runtime_status", "{0} не надає надійного переносного стану виконання для цих засобів захисту.");
		m.put("update_and_verify",
				"Оновіть {0}, установіть актуальну прошивку або мікрокод, перезавантажте систему та перевірте: {1}.");
		m.put("kernel_missing", "Ядро не надає {0}.");
		m.put("update_kernel",
				"Оновіть ядро та прошивку або мікрокод CPU, перезавантажте систему й знову перевірте {0}.");
		m.put("no_action", "Для початкових варіантів, позначених як захищені, додаткові дії не потрібні.");
		m.put("cpu_not_affected", "Операційна система повідомляє, що цей процесор не уражений.");
		m.put("mitigation_active", "Захист операційної системи активний.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "Увімкніть або оновіть {0}, перезавантажте систему та перевірте ще раз.");
		m.put("cannot_read", "Не вдалося прочитати {0}.");
		m.put("partial_status", "{0} підтверджено лише частково; перевірте {1}.");
		m.put("not_exposed", "Не надається через {0}");
		m.put("update_os_firmware_verify", "Оновіть {0} і {1}, перезавантажте систему та перевірте за допомогою {2}.");
		TEXTOS.put("uk", Collections.unmodifiableMap(m));
	}

	private static void cargar_vi() {
		Map<String, String> m = new HashMap<>();
		m.put("check_meltdown", "Meltdown: biện pháp giảm thiểu cho bộ xử lý");
		m.put("check_spectre", "Spectre V1/V2: biện pháp giảm thiểu cho bộ xử lý");
		m.put("disable_meltdown", "Không kiểm tra Meltdown nữa");
		m.put("disable_spectre", "Không kiểm tra Spectre nữa");
		m.put("open_docs", "Mở tài liệu chính thức");
		m.put("check_error", "Không thể hoàn tất {0}: {1}");
		m.put("title_protection", "{0}: bảo vệ bộ xử lý");
		m.put("label_cpu", "CPU");
		m.put("label_vendor", "Nhà sản xuất");
		m.put("label_arch", "Kiến trúc");
		m.put("label_os", "Hệ điều hành");
		m.put("label_affected", "Mức độ ảnh hưởng của CPU");
		m.put("label_overall", "Trạng thái tổng thể");
		m.put("label_os_mitigation", "Biện pháp giảm thiểu của hệ điều hành");
		m.put("label_microcode", "Vi mã/phần sụn");
		m.put("label_revision", "Bản sửa đổi được phát hiện");
		m.put("label_note", "Ghi chú");
		m.put("label_evidence", "Bằng chứng");
		m.put("label_action", "Hành động được đề xuất");
		m.put("state_affected", "Bị ảnh hưởng");
		m.put("state_possible", "Có thể bị ảnh hưởng");
		m.put("state_not_affected", "Không bị ảnh hưởng");
		m.put("state_unknown", "Không xác định");
		m.put("state_mitigated", "Đã giảm thiểu");
		m.put("state_partial", "Đã giảm thiểu một phần");
		m.put("state_vulnerable", "Dễ bị tấn công");
		m.put("state_na", "Không áp dụng");
		m.put("state_present", "Có");
		m.put("state_absent", "Không có");
		m.put("state_not_needed", "Không cần thiết");
		m.put("remote_note",
				"CPU trong nhật ký không khớp với máy đang chạy trình phát hiện. Trạng thái bản vá của máy cục bộ không được gán cho nhật ký.");
		m.put("unknown_text", "Văn bản bảo mật CPU chưa được dịch: {0}");
		m.put("remote_evidence",
				"CPU trong nhật ký khác với CPU cục bộ; trạng thái bản vá cục bộ không được truy vấn.");
		m.put("remote_action", "Chạy kiểm tra trên máy đã tạo nhật ký.");
		m.put("unknown_for_log", "Không thể xác định từ nhật ký");
		m.put("no_runtime_status",
				"{0} không cung cấp trạng thái thời gian chạy đáng tin cậy và có tính di động cho các biện pháp giảm thiểu này.");
		m.put("update_and_verify",
				"Cập nhật {0}, cài đặt phần sụn hoặc vi mã hiện tại, khởi động lại rồi xác minh: {1}.");
		m.put("kernel_missing", "Hạt nhân không cung cấp {0}.");
		m.put("update_kernel", "Cập nhật hạt nhân và phần sụn hoặc vi mã CPU, khởi động lại rồi kiểm tra lại {0}.");
		m.put("no_action", "Không cần hành động đối với các biến thể ban đầu được báo cáo là đã giảm thiểu.");
		m.put("cpu_not_affected", "Hệ điều hành báo cáo rằng bộ xử lý này không bị ảnh hưởng.");
		m.put("mitigation_active", "Biện pháp giảm thiểu của hệ điều hành đang hoạt động.");
		m.put("feature_status", "{0}: {1}.");
		m.put("feature_update", "Bật hoặc cập nhật {0}, khởi động lại rồi xác minh lại.");
		m.put("cannot_read", "Không thể đọc {0}.");
		m.put("partial_status", "{0} dường như mới chỉ được xác nhận một phần; hãy xác minh {1}.");
		m.put("not_exposed", "Không được {0} cung cấp");
		m.put("update_os_firmware_verify", "Cập nhật {0} và {1}, khởi động lại rồi xác minh bằng {2}.");
		TEXTOS.put("vi", Collections.unmodifiableMap(m));
	}

	private TraduccionesSeguridadCPU() {
	}

	static String texto(String idioma, String clave, String... argumentos) {
		Map<String, String> mapa = TEXTOS.get(normalizar(idioma));
		if (mapa == null) {
			throw new IllegalArgumentException("Idioma de seguridad CPU no registrado: " + idioma);
		}

		String plantilla = mapa.get(clave);
		if (plantilla == null) {
			throw new IllegalArgumentException("Texto de seguridad CPU no registrado: " + clave + " para " + idioma);
		}

		return sustituir(plantilla, argumentos);
	}

	static String estadoAfectacion(String idioma, String estado) {
		if ("AFECTADO".equals(estado))
			return texto(idioma, "state_affected");
		if ("POSIBLEMENTE_AFECTADO".equals(estado))
			return texto(idioma, "state_possible");
		if ("NO_AFECTADO".equals(estado))
			return texto(idioma, "state_not_affected");
		return texto(idioma, "state_unknown");
	}

	static String estadoMitigacion(String idioma, String estado) {
		if ("MITIGADO".equals(estado))
			return texto(idioma, "state_mitigated");
		if ("PARCIAL".equals(estado))
			return texto(idioma, "state_partial");
		if ("VULNERABLE".equals(estado))
			return texto(idioma, "state_vulnerable");
		if ("NO_APLICA".equals(estado))
			return texto(idioma, "state_na");
		return texto(idioma, "state_unknown");
	}

	static String estadoComponente(String idioma, String estado) {
		if ("PRESENTE".equals(estado))
			return texto(idioma, "state_present");
		if ("AUSENTE".equals(estado))
			return texto(idioma, "state_absent");
		if ("NO_NECESARIO".equals(estado))
			return texto(idioma, "state_not_needed");
		return texto(idioma, "state_unknown");
	}

	static String mensaje(String idioma, String clave, String... argumentos) {
		String a0 = argumento(argumentos, 0);

		switch (clave) {
		case ClavesTextoSeguridadCPU.REGISTRO_CPU_DISTINTO_EVIDENCIA:
			return texto(idioma, "remote_evidence");
		case ClavesTextoSeguridadCPU.REGISTRO_CPU_DISTINTO_ACCION:
			return texto(idioma, "remote_action");
		case ClavesTextoSeguridadCPU.SO_NO_DETERMINADO_REGISTRO:
			return texto(idioma, "unknown_for_log");

		case ClavesTextoSeguridadCPU.DRAGONFLY_SIN_INTERFAZ:
			return texto(idioma, "no_runtime_status", "DragonFly BSD");
		case ClavesTextoSeguridadCPU.DRAGONFLY_ACCION:
			return texto(idioma, "update_os_firmware_verify", "DragonFly BSD", "firmware/microcode", "dmesg");
		case ClavesTextoSeguridadCPU.ZOS_SIN_INTERFAZ:
			return texto(idioma, "no_runtime_status", "z/OS");
		case ClavesTextoSeguridadCPU.ZOS_ACCION:
			return texto(idioma, "update_os_firmware_verify", "z/OS APAR/PTF", "CPC firmware", "SMP/E");
		case ClavesTextoSeguridadCPU.IBMI_SIN_INTERFAZ:
			return texto(idioma, "no_runtime_status", "IBM i");
		case ClavesTextoSeguridadCPU.IBMI_ACCION:
			return texto(idioma, "update_os_firmware_verify", "IBM i PTF groups", "Power firmware",
					"IBM Navigator/ASMI");
		case ClavesTextoSeguridadCPU.HPUX_SIN_INTERFAZ:
			return texto(idioma, "no_runtime_status", "HP-UX");
		case ClavesTextoSeguridadCPU.HPUX_ACCION:
			return texto(idioma, "update_os_firmware_verify", "HP-UX Quality Pack", "platform firmware",
					"HPE security bulletin");
		case ClavesTextoSeguridadCPU.SO_SIN_INTERFAZ:
			return texto(idioma, "no_runtime_status", texto(idioma, "label_os"));
		case ClavesTextoSeguridadCPU.SO_SIN_INTERFAZ_ACCION:
			return texto(idioma, "update_os_firmware_verify", texto(idioma, "label_os"), "BIOS/UEFI/firmware",
					texto(idioma, "open_docs"));

		case ClavesTextoSeguridadCPU.LINUX_MELTDOWN_SIN_SYSFS:
			return texto(idioma, "kernel_missing", "/sys/devices/system/cpu/vulnerabilities/meltdown");
		case ClavesTextoSeguridadCPU.LINUX_SPECTRE_SIN_SYSFS:
			return texto(idioma, "kernel_missing", "spectre_v1 / spectre_v2 (sysfs)");
		case ClavesTextoSeguridadCPU.LINUX_MELTDOWN_SIN_SYSFS_ACCION:
		case ClavesTextoSeguridadCPU.LINUX_REVISAR_SYSFS_ACCION:
			return texto(idioma, "update_kernel", "sysfs");
		case ClavesTextoSeguridadCPU.LINUX_SPECTRE_SIN_SYSFS_ACCION:
		case ClavesTextoSeguridadCPU.LINUX_SPECTRE_ACTUALIZAR_ACCION:
			return texto(idioma, "update_kernel", "spectre_v1 / spectre_v2");
		case ClavesTextoSeguridadCPU.LINUX_SPECTRE_MITIGADO_ACCION:
			return texto(idioma, "no_action");
		case ClavesTextoSeguridadCPU.LINUX_CPU_NO_AFECTADO:
			return texto(idioma, "cpu_not_affected");
		case ClavesTextoSeguridadCPU.LINUX_MITIGACION_ACTIVA:
			return texto(idioma, "mitigation_active");
		case ClavesTextoSeguridadCPU.LINUX_KERNEL_VULNERABLE_ACCION:
			return texto(idioma, "update_kernel", "kernel boot parameters / sysfs");
		case ClavesTextoSeguridadCPU.NO_DISPONIBLE:
			return texto(idioma, "state_unknown");

		case ClavesTextoSeguridadCPU.WINDOWS_SPECULATIONCONTROL_AUSENTE:
			return texto(idioma, "cannot_read", "PowerShell SpeculationControl");
		case ClavesTextoSeguridadCPU.WINDOWS_SPECULATIONCONTROL_ACCION:
			return texto(idioma, "update_os_firmware_verify", "Windows", "BIOS/UEFI", "Get-SpeculationControlSettings");
		case ClavesTextoSeguridadCPU.WINDOWS_MELTDOWN_HARDWARE:
			return texto(idioma, "feature_status", "RDCL/KVA", texto(idioma, "state_not_needed"));
		case ClavesTextoSeguridadCPU.WINDOWS_KVA_ACTIVO:
			return texto(idioma, "feature_status", "Kernel VA Shadow", texto(idioma, "state_present"));
		case ClavesTextoSeguridadCPU.WINDOWS_KVA_ACTUALIZAR:
			return texto(idioma, "feature_update", "Kernel VA Shadow");
		case ClavesTextoSeguridadCPU.WINDOWS_REVISAR_MELTDOWN:
			return texto(idioma, "feature_update", "Get-SpeculationControlSettings / CVE-2017-5754");
		case ClavesTextoSeguridadCPU.WINDOWS_BTI_ACTIVO:
			return texto(idioma, "feature_status", "BTI", texto(idioma, "state_present"));
		case ClavesTextoSeguridadCPU.WINDOWS_BTI_ACTUALIZAR:
			return texto(idioma, "update_os_firmware_verify", "Windows", "BIOS/UEFI", "Get-SpeculationControlSettings");
		case ClavesTextoSeguridadCPU.WINDOWS_REVISAR_SPECTRE:
			return texto(idioma, "feature_update", "Get-SpeculationControlSettings / CVE-2017-5715");

		case ClavesTextoSeguridadCPU.MACOS_VERSION_NO_INTERPRETADA:
			return texto(idioma, "cannot_read", "sw_vers / os.version");
		case ClavesTextoSeguridadCPU.MACOS_ACTUALIZAR:
		case ClavesTextoSeguridadCPU.MACOS_MELTDOWN_ANTIGUO_ACCION:
		case ClavesTextoSeguridadCPU.MACOS_SPECTRE_ACTUALIZAR:
			return texto(idioma, "update_os_firmware_verify", "macOS", "Apple firmware", "Software Update");
		case ClavesTextoSeguridadCPU.MACOS_POSTERIOR_PARCHES_ORIGINALES:
			return texto(idioma, "feature_status", "Meltdown/Spectre", texto(idioma, "state_mitigated"));
		case ClavesTextoSeguridadCPU.MACOS_MELTDOWN_10132:
			return texto(idioma, "feature_status", "macOS 10.13.2 / Meltdown", texto(idioma, "state_present"));
		case ClavesTextoSeguridadCPU.MACOS_SECURITY_UPDATE_EVIDENCIA:
		case ClavesTextoSeguridadCPU.MACOS_SECURITY_UPDATE_INSTALADO:
			return texto(idioma, "feature_status", "Security Update 2018-001", texto(idioma, "state_present"));
		case ClavesTextoSeguridadCPU.MACOS_SPECTRE_SUPLEMENTAL:
			return texto(idioma, "feature_status", "macOS 10.13.2 Supplemental Update", texto(idioma, "state_present"));
		case ClavesTextoSeguridadCPU.MACOS_SPECTRE_SAFARI_PARCIAL:
			return texto(idioma, "partial_status", "Safari/WebKit", "kernel/firmware");
		case ClavesTextoSeguridadCPU.MACOS_SPECTRE_SAFARI_1102:
			return texto(idioma, "feature_status", "Safari 11.0.2", texto(idioma, "state_present"));

		case ClavesTextoSeguridadCPU.FREEBSD_PTI_HABILITAR:
			return texto(idioma, "feature_update", "PTI");
		case ClavesTextoSeguridadCPU.FREEBSD_PTI_NO_LEIDO:
			return texto(idioma, "cannot_read", "vm.pmap.pti");
		case ClavesTextoSeguridadCPU.FREEBSD_PTI_REVISAR:
			return texto(idioma, "update_os_firmware_verify", "FreeBSD", "microcode",
					"dmesg / vm.pmap.pti / FreeBSD-SA-18:03");
		case ClavesTextoSeguridadCPU.FREEBSD_SPECTRE_MITIGADO:
			return texto(idioma, "partial_status", "Spectre V2", "Spectre V1");
		case ClavesTextoSeguridadCPU.FREEBSD_IBRS_DESHABILITADO:
			return texto(idioma, "feature_status", "IBRS", texto(idioma, "state_absent"));
		case ClavesTextoSeguridadCPU.FREEBSD_IBRS_INACTIVO:
			return texto(idioma, "partial_status", "IBRS", "Retpoline / dmesg");
		case ClavesTextoSeguridadCPU.FREEBSD_IBRS_NO_ENCONTRADO:
			return texto(idioma, "cannot_read", "IBRS / machdep.mitigations.*");
		case ClavesTextoSeguridadCPU.FREEBSD_IBRS_REVISAR:
			return texto(idioma, "update_os_firmware_verify", "FreeBSD", "microcode",
					"dmesg / hw.ibrs_active / machdep.mitigations.*");

		case ClavesTextoSeguridadCPU.NETBSD_SVS_HABILITAR:
			return texto(idioma, "feature_update", "SVS");
		case ClavesTextoSeguridadCPU.NETBSD_SVS_NO_LEIDO:
			return texto(idioma, "cannot_read", "machdep.svs.enabled");
		case ClavesTextoSeguridadCPU.NETBSD_SVS_REVISAR:
			return texto(idioma, "update_os_firmware_verify", "NetBSD", "microcode", "machdep.svs.enabled");
		case ClavesTextoSeguridadCPU.NETBSD_SPECTRE_MITIGADO:
			return texto(idioma, "partial_status", "Spectre V2", "Spectre V1");
		case ClavesTextoSeguridadCPU.NETBSD_SPECTRE_HABILITAR:
			return texto(idioma, "feature_update", "machdep.spectre_v2.*");
		case ClavesTextoSeguridadCPU.NETBSD_SPECTRE_NO_LEIDO:
			return texto(idioma, "cannot_read", "machdep.spectre_v2.*");
		case ClavesTextoSeguridadCPU.NETBSD_SPECTRE_REVISAR:
			return texto(idioma, "update_os_firmware_verify", "NetBSD", "microcode", "machdep.spectre_v2.*");

		case ClavesTextoSeguridadCPU.OPENBSD_VERSION_NO_LEIDA:
			return texto(idioma, "cannot_read", "OpenBSD version");
		case ClavesTextoSeguridadCPU.OPENBSD_ACTUALIZAR:
		case ClavesTextoSeguridadCPU.OPENBSD_ANTIGUO_ACCION:
			return texto(idioma, "update_os_firmware_verify", "OpenBSD", "firmware/microcode", "syspatch / dmesg");
		case ClavesTextoSeguridadCPU.OPENBSD_MELTDOWN_INCLUIDO:
			return texto(idioma, "feature_status", "Meltdown x86", texto(idioma, "state_mitigated"));
		case ClavesTextoSeguridadCPU.OPENBSD_SPECTRE_PARCIAL:
			return texto(idioma, "partial_status", "Retpoline/SpectreRSB", "Spectre V1/V2 runtime status");

		case ClavesTextoSeguridadCPU.SOLARIS_SXADM_AUSENTE:
			return texto(idioma, "cannot_read", "sxadm");
		case ClavesTextoSeguridadCPU.SOLARIS_SXADM_ACCION:
			return texto(idioma, "update_os_firmware_verify", "Solaris SRU/CPU", "system firmware", "sxadm status");
		case ClavesTextoSeguridadCPU.SOLARIS_RDCL_HARDWARE:
			return texto(idioma, "feature_status", "RDCL_NO", texto(idioma, "state_present"));
		case ClavesTextoSeguridadCPU.SOLARIS_KPTI_ACTIVO:
			return texto(idioma, "feature_status", "KPTI", texto(idioma, "state_present"));
		case ClavesTextoSeguridadCPU.SOLARIS_KPTI_ACTIVAR:
			return texto(idioma, "feature_update", "KPTI");
		case ClavesTextoSeguridadCPU.SOLARIS_MELTDOWN_REVISAR:
			return texto(idioma, "feature_update", "sxadm status: KPTI / RDCL_NO");
		case ClavesTextoSeguridadCPU.SOLARIS_V2_ACTIVO:
			return texto(idioma, "feature_status", "HW_BTI / IBRS / IBPB", texto(idioma, "state_present"));
		case ClavesTextoSeguridadCPU.SOLARIS_EXTENSION_ACTIVAR:
			return texto(idioma, "feature_update", "HW_BTI / IBRS / IBPB");
		case ClavesTextoSeguridadCPU.SOLARIS_V2_REVISAR:
			return texto(idioma, "update_os_firmware_verify", "Solaris", "system firmware",
					"sxadm status: HW_BTI / IBRS / IBPB");

		case ClavesTextoSeguridadCPU.AIX_ACTUALIZAR:
			return texto(idioma, "update_os_firmware_verify", "AIX/VIOS", "Power firmware", "oslevel / lsmcode");
		case ClavesTextoSeguridadCPU.AIX_CONTROL_ESPECULATIVO_REVISAR:
			return texto(idioma, "partial_status", "AIX/VIOS", "Speculative Execution Control in HMC/ASMI");

		case ClavesTextoSeguridadCPU.XINUOS_SIN_ESTADO_RUNTIME:
			return texto(idioma, "no_runtime_status", "UnixWare/OpenServer");
		case ClavesTextoSeguridadCPU.XINUOS_ACCION_PATCHCK:
			return texto(idioma, "update_os_firmware_verify", "UnixWare/OpenServer", "BIOS/firmware",
					"patchck -u / pkginfo / Xinuos CVE support");
		case ClavesTextoSeguridadCPU.XINUOS_CPU_NO_AFECTADO:
			return texto(idioma, "cpu_not_affected");

		case ClavesTextoSeguridadCPU.DESCONOCIDO:
		case ClavesTextoSeguridadCPU.DESCONOCIDA:
			return texto(idioma, "state_unknown");
		case ClavesTextoSeguridadCPU.FABRICANTE_ARM_COMPATIBLE:
			return "Arm";
		case ClavesTextoSeguridadCPU.FABRICANTE_MIPS_COMPATIBLE:
			return "MIPS";
		case ClavesTextoSeguridadCPU.ARQUITECTURA_X86_REGISTRO:
			return "x86/x64";
		case ClavesTextoSeguridadCPU.NO_EXPUESTO_PROC_CPUINFO:
			return texto(idioma, "not_exposed", "/proc/cpuinfo");
		case ClavesTextoSeguridadCPU.NO_EXPUESTO_SO:
			return texto(idioma, "not_exposed", texto(idioma, "label_os"));
		case ClavesTextoSeguridadCPU.NO_EXPUESTO_APPLE:
			return texto(idioma, "not_exposed", "Apple");
		case ClavesTextoSeguridadCPU.NO_DISPONIBLE_AIX:
			return texto(idioma, "cannot_read", "AIX Diagnostics");
		case ClavesTextoSeguridadCPU.SIN_INTERFAZ_PORTATIL:
			return texto(idioma, "no_runtime_status", "platform");
		case ClavesTextoSeguridadCPU.NO_EXPUESTO_REGISTRO:
			return texto(idioma, "not_exposed", "Windows Registry");
		case ClavesTextoSeguridadCPU.NO_EXPUESTO_REGISTRO_REMOTO:
			return texto(idioma, "unknown_for_log");
		case ClavesTextoSeguridadCPU.ERROR_LEER_RUTA:
			return texto(idioma, "cannot_read", a0);
		case ClavesTextoSeguridadCPU.TEXTO_DESCONOCIDO:
			return texto(idioma, "unknown_text", a0);
		default:
			return texto(idioma, "unknown_text", clave);
		}
	}

	private static String sustituir(String plantilla, String... argumentos) {
		String resultado = plantilla;
		if (argumentos == null)
			return resultado;
		for (int i = 0; i < argumentos.length; i++) {
			String valor = argumentos[i] == null ? "" : argumentos[i];
			resultado = resultado.replace("{" + i + "}", valor);
		}
		return resultado;
	}

	private static String argumento(String[] argumentos, int indice) {
		if (argumentos == null || indice < 0 || indice >= argumentos.length || argumentos[indice] == null)
			return "";
		return argumentos[indice];
	}

	private static String normalizar(String codigo) {
		return codigo == null ? "" : codigo.trim().toLowerCase(Locale.ROOT);
	}

}
