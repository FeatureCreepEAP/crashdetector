package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Vietnamita implements Idioma {
	Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "vi";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "vietnamita";
	}

	@Override
	public String nombre_del_idioma() {
		return "Tiếng Việt";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_vietnam.png");
	}

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>không phải là thư mục mod hợp lệ</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Không tìm thấy tệp JAR của "
				+ Statics.nombre_cd.obtener() + "</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Đang tìm PID: " + String.valueOf(pid)
				+ "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") đã chết!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Không có JVM</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Cập nhật trình điều khiển ATI/AMD có thể giúp ích.Đọc hướng dẫn này để khắc phục: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>Hướng dẫn cập nhật trình điều khiển</a> https://www.amd.com/es/support/download/drivers.html Tải xuống </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Một số phiên bản cũ đôi khi gặp vấn đề với đồ họa Nouveau ở màn hình tải sớm.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang gặp sự cố với trình điều khiển đồ họa. Nếu bạn có GPU hoặc APU AMD/ATI, hãy cập nhật trình điều khiển đồ họa AMD. Nếu bạn có card đồ họa NVIDIA, hãy đảm bảo đặt trò chơi và mọi tiến trình javaw.exe dùng GPU rời. Đọc hướng dẫn này: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Hướng dẫn cập nhật trình điều khiển</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Cửa sổ FML Early của bạn đang gặp lỗi. Để thay đổi điều này, hãy vào (.minecraft/config/fml.toml) và chỉnh earlyWindowProvider thành earlyWindowProvider=\"none\". Nếu bạn dùng Mac M1, cũng hãy đảm bảo bạn đang dùng bản Java ARM chứ không phải Intel x64. Đây cũng là lỗi thường gặp nếu trình điều khiển của bạn đã quá cũ. Hãy xem hướng dẫn này nếu bạn đang dùng Windows và việc tắt tính năng này không hiệu quả: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Hướng dẫn cập nhật trình điều khiển</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Bạn không có đầy đủ các phụ thuộc cần thiết:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "Được yêu cầu bởi").replace("Expected range", "phạm vi mong đợi")
				+ "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>báo cáo " + Statics.nombre_cd.obtener()
				+ " của bạn ở đây <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace()
				+ "'>xem báo cáo</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Đây là giao diện " + Statics.nombre_cd.obtener()
				+ ". Nếu trò chơi đóng bình thường, hãy bỏ qua.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>Xem báo cáo</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>Xem báo cáo cục bộ trong trình duyệt của bạn.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "Chia sẻ báo cáo";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "Chia sẻ báo cáo. Nhật ký của bạn sẽ được tải lên securelogger.net và báo cáo sẽ được tải lên một trang khác.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Đã phát hiện tệp JAR có vấn đề (ưu tiên FATAL, sau đó mức cao và dòng thấp):</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> mức:</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Có thể gây lỗi nghiêm trọng:</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Đã phát hiện ModID có vấn đề (ưu tiên FATAL, sau đó mức thấp và dòng thấp):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Đã phát hiện gói có vấn đề (ưu tiên FATAL, sau đó mức thấp và dòng thấp):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn có các lớp bị thiếu nghiêm trọng (FATAL), điều này rất quan trọng; nguyên nhân phổ biến là coremod xấu hoặc thiếu phụ thuộc nghiêm trọng. Bạn có thể dùng QuickFix để tìm mod có các lớp nghiêm trọng đó. Đã phát hiện các lớp nghiêm trọng bị thiếu:</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Nội dung trong {} (mục quan trọng nhất ở trên cùng, chỉ 20 mục đầu):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Đã phát hiện cấu hình SpongeMixin có vấn đề: "
				+ "</b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Bạn có mod với package/gói bị trùng lặp. Bạn có thể khắc phục bằng cách xóa package (thư mục) khỏi jar; có thể mở jar bằng phần mềm nén như WinRAR hoặc 7z, hoặc đổi đuôi .jar thành .zip, xóa thư mục rồi đổi lại thành .jar.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Bạn có mod bị trùng lặp</b> "
				+ linea.replace("from mod files", "từ các tệp mod ");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge nghi ngờ mod này có vấn đề:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV cần lithostitched, bạn có thể cài tại đây: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Để dùng Iris Shaders hoặc Oculus, bạn cần SODIUM hoặc bản tương đương cho loader khác (Rubidium, Embeddium, Bedium)</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Lỗi với tiện ích mở rộng KubeJS </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện sự cố với trình điều khiển NVIDIA trên các phiên bản trước Windows 11."
				+ "</span><br/><br/>"
				+ "Để đảm bảo Minecraft (và JVM hiện tại) dùng GPU rời NVIDIA, hãy làm theo các bước sau:<br/><br/>"
				+ "1. <strong>Xác định tệp thực thi Java:</strong><br/>"
				+ "   - Chương trình này đang sử dụng tệp thực thi Java sau: " + obtenerRutaJava() + "<br/>"
				+ "   - Nếu bạn không thấy đường dẫn cụ thể, hãy tìm 'java.exe' trong hệ thống của bạn.<br/><br/>"
				+ "2. <strong>Mở Bảng điều khiển NVIDIA:</strong><br/>"
				+ "   - Nhấp chuột phải vào màn hình nền và chọn 'NVIDIA Control Panel'.<br/><br/>"
				+ "3. <strong>Đặt GPU ưu tiên:</strong><br/>"
				+ "   - Trong NVIDIA Control Panel, vào 'Manage 3D settings'.<br/>"
				+ "   - Chọn tùy chọn 'Program Settings'.<br/>"
				+ "   - Nhấn 'Add' và tìm tệp thực thi Java đã xác định ở trên (ví dụ: 'java.exe').<br/>"
				+ "   - Đảm bảo nó được đặt dùng 'High-performance NVIDIA processor'.<br/><br/>"
				+ "4. <strong>Lưu thay đổi:</strong><br/>"
				+ "   - Áp dụng thay đổi và đóng NVIDIA Control Panel.<br/><br/>"
				+ "5. <strong>Khởi động lại Minecraft:</strong><br/>"
				+ "   - Khởi động lại Minecraft để áp dụng thay đổi.<br/><br/>"
				+ "Nếu bạn dùng Windows Server 2022 hoặc Windows 10, các bước này vẫn áp dụng miễn là bạn đã cài trình điều khiển NVIDIA mới nhất.<br/><br/>"
				+ "Lưu ý: Nếu bạn không thấy NVIDIA Control Panel, hãy đảm bảo trình điều khiển NVIDIA đã được cài đúng.";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện sự cố với trình điều khiển NVIDIA trên Windows 11/Server 2025 hoặc mới hơn."
				+ "</span><br/><br/>"
				+ "Để đảm bảo Minecraft (và JVM hiện tại) dùng GPU rời NVIDIA, hãy làm theo các bước sau:<br/><br/>"
				+ "1. <strong>Xác định tệp thực thi Java:</strong><br/>"
				+ "   - Chương trình này đang sử dụng tệp thực thi Java sau: " + obtenerRutaJava() + "<br/>"
				+ "   - Nếu bạn không thấy đường dẫn cụ thể, hãy tìm 'java.exe' trong hệ thống của bạn.<br/><br/>"
				+ "2. <strong>Mở ứng dụng Cài đặt:</strong><br/>"
				+ "   - Nhấn <code>Win + I</code> để mở ứng dụng Cài đặt.<br/>"
				+ "   - Vào <strong>Hệ thống > Màn hình > Đồ họa</strong>.<br/><br/>"
				+ "3. <strong>Đặt GPU ưu tiên:</strong><br/>"
				+ "   - Trong phần 'Đồ họa', hãy nhấp 'Cài đặt đồ họa mặc định'.<br/>"
				+ "   - Chọn 'Ứng dụng máy tính để bàn' rồi nhấp 'Duyệt'.<br/>"
				+ "   - Tìm và chọn tệp thực thi Java đã xác định ở trên (ví dụ: 'java.exe').<br/>"
				+ "   - Sau khi thêm, chọn ứng dụng Java trong danh sách và đặt thành 'Hiệu năng cao (NVIDIA)'.<br/><br/>"
				+ "4. <strong>Lưu thay đổi:</strong><br/>" + "   - Áp dụng thay đổi và đóng ứng dụng Cài đặt.<br/><br/>"
				+ "5. <strong>Khởi động lại Minecraft:</strong><br/>"
				+ "   - Khởi động lại Minecraft để áp dụng thay đổi.<br/><br/>"
				+ "Nếu bạn dùng Windows 11 hoặc Windows Server 2025+, các bước này vẫn áp dụng miễn là bạn đã cài trình điều khiển NVIDIA mới nhất.<br/><br/>"
				+ "Lưu ý: Nếu bạn không tìm thấy tùy chọn cài đặt đồ họa, hãy đảm bảo trình điều khiển NVIDIA đã được cài đúng.";
	}

	@Override
	public String segundo60Tick() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Máy chủ hoặc thế giới của bạn có tick dài hơn 60 giây. Điều này có thể do mod làm máy chủ chậm lại hoặc phần cứng quá yếu.</b>";
	}

	@Override
	public String noTieneMemoria() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn không có đủ RAM/Bộ nhớ. Bạn cần cấp phát nhiều hơn.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Theseus cũng có thêm nhiều vấn đề, bao gồm lỗi khi xóa mod. Nếu bạn cần chơi với tệp mrpack, bạn có thể dùng launcher khác như Prism Launcher (chỉ cho modrinth.com), ATLauncher (chỉ cho modrinth.com) hoặc Hello Minecraft Launcher (hỗ trợ modrinth.com và bbsmc.net).</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Bạn đang dùng \"Bỏ qua khởi động Launcher\" (ứng dụng CurseForge). Điều này đôi khi gây ra các lỗi khó phát hiện. Nguyên nhân là tùy chọn \"Bỏ qua khởi động Launcher\" trong các phiên bản cũ của ứng dụng CurseForge hoặc trong phiên bản mới. Hãy tắt nó và dùng tùy chọn \"Mojang Launcher\" trong phần cài đặt của CurseForge. Bạn có thể xem video tiếng Anh này của Claws of Berk (tới mốc 1:11) "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>ở đây</a>.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Cảnh báo: Bạn có lớp bị thiếu (mức cảnh báo), thường thì không sao nhưng không phải lúc nào cũng vậy; nó khác với lớp bị thiếu nghiêm trọng. Coremod lỗi hoặc thiếu phụ thuộc là nguyên nhân phổ biến. Bạn có thể dùng QuickFix để tìm mod chứa các lớp đó. Đã phát hiện các lớp bị thiếu:</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Không có kết quả</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Vị trí nhật ký:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Đây là kết quả kiểm tra của bạn. Hãy đọc chậm; thường nguyên nhân đúng nằm ở kiểm tra 1 hoặc 2. Các mục khác (lỗi 3+) có thể dùng để xác nhận, nhưng thường là lỗi dây chuyền và có thể bỏ qua. Lỗi xảy ra theo từng lớp, nên sửa đúng nguyên nhân sẽ giải quyết lỗi hiện tại hôm nay, nhưng ngày mai có thể xuất hiện lỗi mới không liên quan vì một lỗi thường che khuất lỗi khác trong console.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vui lòng dùng Java 17 cho phiên bản 1.17-1.20.4 và Java 21 cho mọi phiên bản mới hơn. Dùng Java 8 cho các phiên bản cũ hơn. [Hướng dẫn](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Nếu bạn vẫn gặp sự cố, có thể là do một số mod có tệp quá mới hoặc quá cũ.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 trở lên không hoạt động trên các phiên bản Minecraft thấp hơn 1.20.5 đối với hầu hết bộ nạp mod vì ASM đã lỗi thời.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java đã lỗi thời </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>Bạn cần mô-đun JPMS " + mod_necesitas + " từ "
				+ submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Không thể gọi " + metodo + " vì " + objeto
				+ " là null</b>";
	}

	@Override
	public String analisisAvanzado() {
		return "Phân tích nâng cao";
	}

	@Override
	public String seleccionarCarpeta() {
		return "Chọn thư mục";
	}

	@Override
	public String cadenaBusqueda() {
		return "Chuỗi tìm kiếm";
	}

	@Override
	public String usarRegex() {
		return "Dùng Regex";
	}

	@Override
	public String ignorarMayusculas() {
		return "Không phân biệt hoa thường";
	}

	@Override
	public String buscar() {
		return "Tìm kiếm";
	}

	@Override
	public String busquedaEnProgreso() {
		return "Đang tìm kiếm";
	}

	@Override
	public String noSeEncontraronResultados() {
		return "Không tìm thấy kết quả";
	}

	@Override
	public String errorBusqueda() {
		return "Lỗi tìm kiếm";
	}

//@Override
//public String noRegistroDeLauncher() {
//	// TODO Auto-generated method stub
//	return new String ("¡No se encontraron nhật ký del launcher! Esto puede dificultar la investigación.\n"
//			+ "                \n"
//			+ "                Para obtener los nhật ký correctos:\n"
//			+ "                - MultiMC/PolyMC/PrismLauncher/PollyMC/UltimMC: NOTA: Los nhật ký detectados automáticamente NO son los correctos.\n"
//			+ "                  1. Abre la interfaz de la instancia\n"
//			+ "                  2. Ve a la sección \"Minecraft Log\"\n"
//			+ "                  3. Haz clic derecho y copia el contenido\n"
//			+ "                \n"
//			+ "                - LauncherFenix:\n"
//			+ "                  1. Abre la \"Consola de Desarrollador\"\n"
//			+ "                  2. Copia el contenido completo\n"
//			+ "                \n"
//			+ "                - CurseForgeApp:\n"
//			+ "                  1. Reinicia el juego SIN saltar el launcher\n"
//			+ "                  \n"
//			+ "                ");
//}

	@Override
	public String omitirYCerrar() {
		// TODO Auto-generated method stub
		return "Bỏ qua và đóng";
	}

	@Override
	public String guardarYCerrar() {
		// TODO Auto-generated method stub
		return "Lưu y Đóng";
	}

	@Override
	public String pegaLosRegistrosAqui() {
		// TODO Auto-generated method stub
		return "Dán nhật ký vào đây";
	}

	@Override
	public String archivo() {
		return "Tệp";
	}

	@Override
	public String incluir() {
		return "Bao gồm";
	}

	@Override
	public String abrir() {
		return "Mở";
	}

	@Override
	public String endpointDeInforme() {
		// TODO Auto-generated method stub
		return "Điểm cuối báo cáo";
	}

	@Override
	public String sitoDeLogging() {
		// TODO Auto-generated method stub
		return "Trang ghi nhật ký:";
	}

	@Override
	public String apiDeLogging() {
		// TODO Auto-generated method stub
		return "API ghi nhật ký:";
	}

	@Override
	public String anonimizarRegistros() {
		// TODO Auto-generated method stub
		return "Ẩn danh nhật ký";
	}

	@Override
	public String botonDeCompartirInforme() {
		// TODO Auto-generated method stub
		return "Chia sẻ báo cáo và tất cả nhật ký đã chọn";
	}

	@Override
	public String arco() {
		return "Hộp thoại này cho phép bạn chia sẻ nhật ký bằng API SecureLogger "
				+ "tại <a href=\"https://securelogger.net\">securelogger.net</a>. Khi bạn nhấn nút chia sẻ báo cáo, "
				+ "báo cáo của bạn sẽ được gửi đến điểm cuối đã chọn (mặc định là asbestosstar.egoism.jp) (có thể thay đổi ở phía dưới). "
				+ "Bạn có thể chia sẻ tất cả nhật ký đã chọn cùng với báo cáo. Nếu bạn không muốn tải lên, đừng dùng hộp thoại này! "
				+ "Chúng tôi không xử lý báo cáo của bạn tại điểm cuối chính thức (<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_máy chủ.rb\">https://asbestosstar.egoism.jp/crash_detector/crash_detector_máy chủ.rb</a>); "
				+ "chúng tôi chỉ xóa các liên kết không được phép. Mã nguồn có tại đây: <a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_máy chủ.rb\">mã nguồn</a>. "
				+ "Tính năng này chỉ được dùng để hiển thị thông tin về sự cố của bạn và liên kết đến các nhật ký. Tuy nhiên, bạn cũng có thể dùng một điểm cuối tùy chỉnh có thể không dùng cùng phương thức. "
				+ "Bạn đang dùng trang báo cáo " + Config.obtenerInstancia().obtenerSitoDeInformes()
				+ " và trang nhật ký " + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". "
				+ "Bạn cũng có thể chia sẻ từng nhật ký riêng lẻ mà không cần báo cáo bằng cách nhấn các nút chia sẻ cạnh tên từng nhật ký; "
				+ "các nhật ký sẽ được gửi đến trang nhật ký đã chọn. " + Statics.nombre_cd.obtener()
				+ " có tính năng ẩn danh nhật ký mặc định, cố gắng xóa tên người dùng, UUID, "
				+ "mã truy cập, ID phiên, địa chỉ IP và dữ liệu khác. Tuy nhiên, nó không hoàn hảo. Dù vậy, tác giả modpack có thể tắt nó đi. "
				+ "Có thể bật hoặc tắt nó bằng ô đánh dấu ở cuối màn hình này. Bạn là người kiểm soát dữ liệu của chính mình; bạn quyết định nơi tải dữ liệu của mình lên. "
				+ "Các trang nhật ký thuộc sở hữu của bên thứ ba, và thông tin sở hữu thường bị ẩn vì lý do riêng tư. Bạn tự chịu hoàn toàn trách nhiệm quản lý dữ liệu của mình và các rủi ro liên quan. "
				+ "Hộp thoại Chia sẻ của " + Statics.nombre_cd.obtener()
				+ " chỉ đơn giản là giao diện giúp bạn quản lý việc đó. "
				+ "Điều quan trọng là bạn nên biết về GDPR và ARCO. "
				+ "Nếu bạn ở châu Âu, bạn có thể dùng <a href=\"https://securelogger.top\">securelogger.top</a> được lưu trữ tại Đức bởi Hetzner. "
				+ "Để biết thêm thông tin pháp lý, hãy xem các liên kết sau: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>, "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">RGPD</a>, "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">Política Básica de Protección de Datos en Japón</a>.";
	}

	@Override
	public String enlaceDelReporte() {
		// TODO Auto-generated method stub
		return "Liên kết báo cáo:";
	}

	@Override
	public String guardarConfigDeCompartir() {
		return "Lưu cấu hình chia sẻ";
	}

	@Override
	public String registroDemasiadoGrande() {
		return "Nhật ký quá lớn đối với trang ghi nhật ký này. Hãy chọn trang khác và thử lại.";
	}

	@Override
	public String errorConPublicarRegistro(String error) {
		return "Lỗi khi đăng nhật ký " + error;
	}

	@Override
	public String apiDeRegistroNoExiste() {
		return "API ghi nhật ký không tồn tại. Vui lòng đổi API ghi nhật ký trong phần cài đặt.";
	}

	@Override
	public String errorSSL() {
		return "Bạn gặp lỗi SSL. Điều này thường xảy ra với các phiên bản Java cũ, "
				+ "bao gồm các bản Java 8 trong Minecraft Launcher mặc định "
				+ "và các phiên bản trên sun.com và java.com. Điều này ảnh hưởng đến nhiều thứ, "
				+ "như các tệp JAR của trình cài đặt MinecraftForge, tính năng chia sẻ báo cáo " + "của "
				+ Statics.nombre_cd.obtener() + " khi dùng điểm cuối mặc định, một số mod yêu cầu Internet "
				+ "và một số trang ghi nhật ký. Nếu điều này xảy ra khi bạn đang cố chia sẻ báo cáo, "
				+ "chỉ cần đính kèm ảnh chụp màn hình và chọn một trang ghi nhật ký tương thích "
				+ "với các phiên bản Java 8 cũ hơn.";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JavaFML không tương thích: yêu cầu phiên bản "
				+ requerido + ", , phát hiện " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Chú ý! JavaFML yêu cầu một phiên bản Minecraft Forge cụ thể</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Tệp JAR '" + archivoJar
				+ "' yêu cầu nhà cung cấp ngôn ngữ '" + proveedor + "' phiên bản '" + requerido
				+ "' trở lên, nhưng chỉ tìm thấy phiên bản '" + encontrado + "'.</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "CẢNH BÁO! Crash Assistant là một trình phát hiện phần mềm độc hại giả. Nó cố ý chặn việc khởi chạy trò chơi, bỏ qua quyền của bạn trong việc tiếp tục chơi với các mod mà nó nhắm tới. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Xem mã MalwareMod.java</a>   "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Xem mã JarInJarHelper.java</a>. "
				+ "Hiện tại chỉ có mod này nằm trong danh sách của nó và trên thực tế nó chỉ đang nhắm vào trang ghi nhật ký mặc định, vốn có thể được người dùng thay đổi, và điều đó chỉ xảy ra nếu bạn chủ động chọn dùng tính năng chia sẻ nhật ký tích hợp. CrashAssistant KHÔNG thực hiện bất kỳ kiểm tra nào để xác định trang ghi nhật ký nào đang được sử dụng và cũng không giải thích cách thay đổi nó (có một menu thả xuống ở cuối hộp thoại chia sẻ), và bất kể bạn đã cấu hình trang nào, CrashAssistant vẫn sẽ chặn trò chơi khởi chạy. Trong thông báo của họ họ nói rằng bạn nên tự nghiên cứu, HÃY LÀM ĐIỀU ĐÓ, hãy xem mã của "
				+ Statics.nombre_cd.obtener()
				+ " và Crash Assistant và hiểu chúng làm gì, ĐỪNG chỉ dựa vào lời kêu gọi thẩm quyền.</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Mod '" + idMod
				+ "' đã gặp lỗi vì không tìm thấy lớp yêu cầu: '" + claseNoEncontrada
				+ "'. Hãy đảm bảo rằng tất cả các phụ thuộc đã được cài đặt đúng cách.</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Watermedia đã chặn việc chơi với TLauncher.</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Bạn đang dùng một phiên bản Optifine dành cho phiên bản Minecraft đã lỗi thời. Bạn cần dùng phiên bản Optifine phù hợp với phiên bản Minecraft mà bạn đang dùng.</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Không thể tải Dịch vụ ModLauncher: </b>"
				+ servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Lỗi khi phân tích tệp JSON '" + recurso
				+ "' del tệp JAR '" + archivoJar + "'. Tệp này có vấn đề với phần ghi nhật ký.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Lỗi: Mod '" + modId
				+ "' yêu cầu phiên bản '" + requerido + "' hoặc cao hơn của '" + dependencia + "', nhưng tìm thấy '"
				+ actual + "'." + "</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GPU của bạn không hỗ trợ phiên bản OpenGL mà phiên bản trò chơi này yêu cầu. Hãy cập nhật driver hoặc thay card đồ họa.</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Hãy tăng bộ nhớ được cấp cho trò chơi hoặc giảm mức sử dụng của mod/plugin</b>";
	}

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Đã phát hiện JVM 32-bit: không thể sử dụng hơn 4GB RAM. "
				+ "Hãy cài đặt JVM 64-bit để tận dụng toàn bộ bộ nhớ hiện có của bạn</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Lỗi bộ nhớ PermGen nghiêm trọng. Hãy tăng không gian bộ nhớ vĩnh viễn hoặc giảm tải lớp.</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Lỗi tương thích giữa Java 8 và các phiên bản hiện đại</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+ không được hỗ trợ - Hãy dùng Java 8 cho Forge cũ</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Yêu cầu Java 8 (phiên bản 52.0). Hãy cập nhật hoặc cấu hình đúng</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Lỗi tương thích nghiêm trọng: Khối không được hỗ trợ trong phiên bản này. "
				+ "Hãy kiểm tra rằng các phiên bản mod và máy chủ tương thích với nhau</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Lỗi cấu hình màn hình: "
				+ "Không thể thiết lập chế độ hiển thị. " + "Hãy kiểm tra:</b>" + "<br>- Cấu hình nhiều màn hình"
				+ "<br>- Trình điều khiển card đồ họa đã được cập nhật" + "<br>- Độ phân giải được hệ thống hỗ trợ";
	}

	@Override
	public String errorOpcionesGCJava() {
		// TODO Auto-generated method stub

		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Lỗi trong tùy chọn Java: "
				+ "Các tùy chọn trình gom rác bị xung đột. "
				+ "Hãy kiểm tra rằng bạn không kết hợp nhiều thuật toán GC trong tham số JVM</b>";

	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Lỗi crítico de configuración NightCấu hình/Forge: " + "Tệp de configuración corrupto o incompleto. "
				+ "Esto puede ser causado por tệps de configuración vacíos (a menudo de 0 bytes) en la carpeta 'config' en các phiên bản anteriores o personalizadas de NightCấu hình. "
				+ "Para la mayoría de las các phiên bản, Night Cấu hình Fixes resolverá el problema, pero si estás utilizando una phiên bản no compatible o una phiên bản personalizada de NightCấu hình, deberás xóa los tệps de configuración manualmente. "
				+ "Este problema es más común en các phiên bản antiguas de MC Forge (que viene con NightCấu hình) y en mods de FabricMC antiguos que empaquetan NightCấu hình, pero también puede existir en algunas các phiên bản personalizadas de NightCấu hình. "
				+ "Más información sobre las soluciones está disponible en <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Cấu hình Fixes</a>.</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi trình điều khiển Intel HD Graphics. Giải pháp:</b>"
				+ "<br>1. Cập nhật trình điều khiển Intel từ <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a> (phiên bản tối thiểu 15.xx.xx.xx)"
				+ "<br>2. Trong Minecraft: Tùy chọn → Video → Bật 'Enable VBOs' và 'VSync'"
				+ "<br>3. Cấp 1GB-2GB RAM cho trò chơi trong launcher"
				+ "<br>4. Tạm thời tắt antivirus/firewall trong khi cập nhật";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "Cảnh báo: Đã phát hiện thiếu lớp";
	}

	public String nombre_de_bloque_teselado() {
		return "Lỗi kết xuất khối";
	}

	public String nombre_de_contenido_de_stacktrace() {
		return "Phân tích stack trace";
	}

	public String nombre_de_cursed_consola() {
		return "Bảng điều khiển CurseForge không đầy đủ";
	}

	public String nombre_de_early_window() {
		return "Lỗi cửa sổ khởi động sớm (FMLEarlyWindow)";
	}

	public String nombre_de_drivers() {
		return "Sự cố với trình điều khiển video";
	}

	public String nombre_de_error_de_config_mcforge() {
		return "Cấu hình MCForge bị hỏng";
	}

	public String nombre_de_error_de_monitor_lwjgl() {
		return "Lỗi chế độ hiển thị (LWJGL)";
	}

	public String nombre_de_fabricmc_runtime_error_provided_by() {
		return "Lỗi khởi tạo FabricMC";
	}

	public String nombre_de_falta_module_jmps() {
		return "Thiếu mô-đun JPMS";
	}

	public String nombre_de_faltar_de_clases() {
		return "Thiếu các lớp quan trọng";
	}

	public String nombre_de_faltas_dependencias_de_modlauncher() {
		return "Thiếu phụ thuộc của ModLauncher";
	}

	public String nombre_de_java_versiones() {
		return "Phiên bản Java không tương thích";
	}

	public String nombre_de_faltar_de_kubejs_resourcepack() {
		return "Lỗi tài nguyên KubeJS";
	}

	public String nombre_de_lenguaje_proveedor_check() {
		return "Nhà cung cấp ngôn ngữ không tương thích";
	}

	public String nombre_de_faltar_de_liyhostictchctov() {
		return "Litchhost";
	}

	public String nombre_de_malware_falso_crash_assistant() {
		return "Đã phát hiện phần mềm độc hại giả";
	}

	public String nombre_de_mcforge_mod_sespechoso() {
		return "Đã phát hiện mod đáng ngờ";
	}

	public String nombre_de_mods_duplicados_modlauncher() {
		return "Mod trùng lặp trong ModLauncher";
	}

	public String nombre_de_modules_duplicados_jmps() {
		return "Xung đột mô-đun JPMS";
	}

	public String nombre_de_necesitas_sodium() {
		return "Iris yêu cầu Sodium";
	}

	public String nombre_de_no_puede_analizar_json_de_registro() {
		return "Lỗi khi phân tích JSON nhật ký";
	}

	public String nombre_de_no_tiene_memoria() {
		return "Không đủ bộ nhớ";
	}

	public String nombre_de_null_pointer() {
		return "Lỗi con trỏ null (NullPointerException)";
	}

	public String nombre_de_opciones_java_gc_invalidas() {
		return "Tùy chọn GC của Java không hợp lệ";
	}

	public String nombre_de_optifine_obsoleta() {
		return "OptiFine đã lỗi thời/không tương thích";
	}

	public String nombre_de_60_segundo_trick() {
		return "Tick máy chủ nghiêm trọng (60 giây)";
	}

	public String nombre_de_servicio_de_modlauncher_no_funciona() {
		return "Các dịch vụ ModLauncher bị lỗi";
	}

	public String nombre_de_spongemixin_configs_problematicos() {
		return "Cấu hình SpongeMixin có vấn đề";
	}

	public String nombre_de_theseus() {
		return "Theseus không tương thích";
	}

	public String nombre_de_watermedia_tl() {
		return "Launcher TLauncher không được WATERMeDIA hỗ trợ";
	}

	@Override
	public String auditorias_transformer() {
		return "Kiểm tra Transformer";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đây là kết quả từ nội dung của các cuộc kiểm tra Transformer trong Vanilla Launcher. Nói chung, cách này kém chính xác hơn trình phân tích StackTrace, nhưng Vanilla Launcher không phải lúc nào cũng có nội dung của {}</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "Phần này tìm trong các mod của bạn những mod được tạo bằng MCreator. Mặc dù phần lớn các mod MCreator đều ổn và có nhiều mod rất hay được làm bằng MCreator, đôi khi chúng có vấn đề và có tiếng xấu. Công cụ này giúp nhận diện chúng. Lưu ý rằng ngay cả những mod được đánh giá tốt nhất cũng có thể không thực sự là từ MCreator; ví dụ, chúng có thể chỉ có tích hợp với MCreator.";
	}

	@Override
	public String escanear() {
		return "Quét";
	}

	@Override
	public String cargando() {
		return "Đang tải";
	}

	@Override
	public String inicioApp() {
		// TODO Auto-generated method stub
		return "Khởi động Trò chơi/Ứng dụng";
	}

	@Override
	public String ajustesCrashDetector() {
		// TODO Auto-generated method stub
		return "Cài đặt " + Statics.nombre_cd.obtener();
	}

	@Override
	public String confidencialidad() {
		// TODO Auto-generated method stub
		return "Quyền riêng tư";
	}

	@Override
	public String tooltip() {
		// TODO Auto-generated method stub
		return "Chú giải công cụ";
	}

	@Override
	public String config() {
		// TODO Auto-generated method stub
		return "Cấu hình";
	}

	@Override
	public String abrirCarpeta() {
		// TODO Auto-generated method stub
		return "Mở thư mục";
	}

	@Override
	public String actualizar() {
		// TODO Auto-generated method stub
		return "Cập nhật";
	}

	@Override
	public String anadirRegistro() {
		// TODO Auto-generated method stub
		return "Thêm nhật ký";
	}

	@Override
	public String usarIdiomaDelSistema() {
		// TODO Auto-generated method stub
		return "Sử dụng ngôn ngữ hệ thống";
	}

	@Override
	public String volver() {
		// TODO Auto-generated method stub
		return "Quay lại";
	}

	@Override
	public String colorFondo() {
		return "Màu nền (#RRGGBB):";
	}

	@Override
	public String colorTexto() {
		return "Màu chữ (#RRGGBB):";
	}

	@Override
	public String colorBoton() {
		return "Màu nút (#RRGGBB):";
	}

	@Override
	public String colorCajaTexto() {
		return "Màu ô văn bản (#RRGGBB):";
	}

	@Override
	public String colorEnlace() {
		return "Màu liên kết (#RRGGBB):";
	}

	@Override
	public String colorTitulosConsolas() {
		return "Màu tiêu đề bảng điều khiển (#RRGGBB):";
	}

	@Override
	public String colorError() {
		return "Màu lỗi (#RRGGBB):";
	}

	@Override
	public String colorAdvertencia() {
		return "Màu cảnh báo (#RRGGBB):";
	}

	@Override
	public String colorInfo() {
		return "Màu thông tin (#RRGGBB):";
	}

	@Override
	public String colorTitulo() {
		return "Màu tiêu đề (#RRGGBB):";
	}

	@Override
	public String colorEnlaceTexto() {
		return "Màu chữ liên kết (#RRGGBB):";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "Chỉ mở " + Statics.nombre_cd.obtener() + " khi xảy ra lỗi";
	}

	@Override
	public String activar_parche() {
		return "Kích hoạt bản vá";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "Không có giải pháp khả dụng";
	}

	@Override
	public String error() {
		return "lỗi";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "Lỗi khi xóa JAR";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "Đã xóa JAR thành công";
	}

	@Override
	public String exito() {
		return "thành công";
	}

	@Override
	public String eliminar() {
		return "Xóa";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "Lỗi khi xóa gói";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "Đã xóa gói thành công";
	}

	@Override
	public String eliminar_paquete() {
		return "Xóa gói";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "Không tìm thấy mod nào với gói này";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "Không thể xóa gói";
	}

	@Override
	public String eliminar_jar() {
		return "Xóa JAR";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "Không tìm thấy mod trùng lặp";
	}

	@Override
	public String archivo_no_encontrado() {
		return "Không tìm thấy tệp";
	}

	@Override
	public String directorio_eliminado() {
		return "Đã xóa thư mục";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "Lỗi khi xóa JAR lồng nhau";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "Không tìm thấy tệp nội bộ";
	}

	@Override
	public String archivo_eliminado() {
		return "Đã xóa tệp";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "Lỗi khi xóa tệp";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "Tệp bên ngoài không hợp lệ";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "Đã xóa phần tử mod";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "Lỗi khi thay thế JAR bên ngoài";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "Lỗi khi xóa phần tử mod";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "Lỗi khi xóa thư mục";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "Định dạng không hợp lệ cho JAR lồng nhau";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "Đã xóa JAR lồng nhau";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "Lỗi khi dọn dẹp tệp tạm";
	}

	@Override
	public String mensaje_de_trace_fatal_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Thông báo Trace Fatal cuối cùng (chưa được dịch):</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Thông báo Trace cuối cùng (chưa được dịch):</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "Bạn có thể tìm mod trong cơ sở dữ liệu WaifuNeoForge. Hãy chọn phiên bản trò chơi, bộ nạp mod và lớp. Hãy dùng tổ hợp gần giống nhất. Bạn có thể tìm một lần mỗi phút.";
	}

	@Override
	public String solucionFaltasClases() {
		return "Bạn có thể tìm mod trong cơ sở dữ liệu WaifuNeoForge. Hãy chọn phiên bản trò chơi, bộ nạp mod và lớp. Hãy dùng tổ hợp gần giống nhất. Bạn có thể tìm một lần mỗi phút.";
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "Cả hai launcher đều có các phiên bản Java phù hợp nhưng không đầy đủ. Bạn có thể cài đặt phiên bản Java phù hợp từ trình quản lý gói của hệ thống hoặc bằng các nút.";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod thiếu hoạt ảnh: " + "</b>";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException (Không tìm thấy phần tử) Thiếu hoạt ảnh";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "Không tìm thấy mod nào để xóa";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "Thay thế các tùy chọn GC xung đột bằng -XX:+UseG1GC";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "Tăng kích thước bộ nhớ heap bằng tùy chọn -Xmx.";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "Tăng kích thước bộ nhớ permgen bằng tùy chọn -XX:MaxPermSize.";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "Sử dụng JVM 64-bit để tăng bộ nhớ khả dụng.";
	}

	@Override
	public String optimizarCodigo() {
		return "Tối ưu hóa mã để giảm sử dụng bộ nhớ và cải thiện hiệu suất.";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "Sử dụng bộ thu gom rác hiệu quả để giảm thời gian tạm dừng của ứng dụng.";
	}

	@Override
	public String modulos() {
		return "Mô-đun";
	}

	@Override
	public String paquete() {
		return "Gói";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "Có ID bị thiếu. Nguyên nhân phổ biến là thiếu mod hoặc thiếu dữ liệu vật phẩm. Các thư mục dữ liệu phổ biến là datafiedcontents/, kubejs/ hoặc các thư mục mod khác.";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "nhật ký ánh xạ sai";
	}

	/**
	 * Devuelve el mensaje de error para el cierre causado por AuthMe.
	 */
	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Plugin 'AuthMe' không tải được và đã làm máy chủ đóng lại.</b> ";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	public String nombreProblemaCierreAuthMe() {
		return "Sự cố đóng bởi AuthMe";
	}

	/**
	 * Trả về giải pháp cho việc máy chủ bị đóng bởi AuthMe.
	 */
	public String solucionCierreAuthMe() {
		return "Quy tắc 'stopServer' đã được đổi thành 'true'.";
	}

	/**
	 * Trả về giải pháp cấu hình plugin AuthMe.
	 */
	public String solucionConfigurarPluginAuthMe() {
		return "Cấu hình plugin AuthMe (plugins/AuthMe/config.yml)";
	}

	/**
	 * Trả về giải pháp cài đặt một phiên bản khác của plugin AuthMe.
	 */
	public String solucionInstalarVersionDiferenteAuthMe() {
		return "Cài đặt một phiên bản khác của plugin 'AuthMe'";
	}

	/**
	 * Trả về giải pháp xóa plugin AuthMe.
	 */
	public String solucionEliminarPluginAuthMe() {
		return "Xóa plugin 'AuthMe'";
	}

	/**
	 * Trả về thông báo lỗi cho thế giới bị hỏng do Multiverse.
	 */
	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Thế giới '" + nombreMundo
				+ "' không thể tải vì chứa lỗi và có lẽ đã bị hỏng.</b> ";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaCargaMultiverso() {
		return "Sự cố tải Multiverse";
	}

	/**
	 * Trả về giải pháp sửa chữa thế giới.
	 */
	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "Sửa thế giới '" + nombreMundo + "', ví dụ bằng Minecraft Region Fixer hoặc MCEdit.";
	}

	/**
	 * Trả về giải pháp xóa thư mục thế giới.
	 */
	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "Xóa thư mục thế giới '" + nombreMundo + "'.";
	}

	/**
	 * Trả về thông báo lỗi cho cấu hình PermissionsEx không hợp lệ.
	 */
	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Cấu hình của tiện ích mở rộng 'PermissionsEx' không hợp lệ.</b> ";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "Sự cố cấu hình PermissionsEx";
	}

	/**
	 * Trả về giải pháp cấu hình PermissionsEx.
	 */
	@Override
	public String solucionConfigurarPermissionsEx() {
		return "Cấu hình plugin PermissionsEx (plugins/PermissionsEx/permissions.yml)";
	}

	/**
	 * Trả về giải pháp xóa plugin PermissionsEx.
	 */
	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "Xóa plugin 'PermissionsEx'";
	}

	/**
	 * Trả về thông báo lỗi cho plugin có tên không rõ ràng.
	 */
	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Có nhiều tệp plugin cho plugin có tên '"
				+ nombrePlugin + "': '" + primerPath + "' và '" + segundoPath + "'.</b> ";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "Sự cố tên plugin không rõ ràng";
	}

	/**
	 * Trả về giải pháp xóa một plugin cụ thể.
	 */
	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "Xóa plugin '" + nombrePlugin + "'";
	}

	/**
	 * Devuelve el mensaje de error para excepciones al cargar chunks.
	 */
	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Đã có ngoại lệ khi thế giới đang tải các chunk. Nếu có sẵn cho nền tảng của bạn, FeatureRecycler có thể giải quyết vấn đề này. https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaCargaChunk() {
		return "Ngoại lệ khi tải chunk";
	}

	/**
	 * Trả về giải pháp xóa chunk có vấn đề.
	 */
	@Override
	public String solucionEliminarChunk() {
		return "Hãy xóa chunk có vấn đề khỏi thế giới, ví dụ bằng MCEdit hoặc bằng cách xóa tệp vùng.";
	}

	/**
	 * Trả về thông báo lỗi cho ngoại lệ khi thực thi lệnh của plugin.
	 */
	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' không thể thực thi lệnh '/" + comando + "'.</b> ";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "Ngoại lệ khi thực thi lệnh plugin";
	}

	/**
	 * Devuelve la solución de instalar otra versión del plugin.
	 */
	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "Cài đặt một phiên bản khác của plugin '" + nombrePlugin + "'";
	}

	/**
	 * Trả về thông báo lỗi cho phụ thuộc đơn lẻ.
	 */
	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' cần plugin phụ thuộc '" + dependencia + "'.</b> ";
	}

	/**
	 * Trả về thông báo lỗi cho nhiều phụ thuộc.
	 */
	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' thiếu các plugin cần thiết sau: " + deps.toString() + ".</b> ";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "Thiếu phụ thuộc plugin";
	}

	/**
	 * Trả về giải pháp cài đặt một plugin cụ thể.
	 */
	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "Cài đặt plugin '" + nombrePlugin + "'";
	}

	/**
	 * Trả về thông báo lỗi cho phiên bản API không tương thích.
	 */
	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' yêu cầu phiên bản API '" + versionAPI + "' không tương thích với máy chủ hiện tại.</b> ";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "Phiên bản API không tương thích";
	}

	/**
	 * Trả về giải pháp cài đặt phiên bản máy chủ cụ thể.
	 */
	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "Hãy cài đặt phiên bản '" + version + "' của phần mềm máy chủ.";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Thế giới '" + nombreMundo
				+ "' là bản sao của một thế giới khác và không thể tải.</b> ";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaMundoDuplicado() {
		return "Thế giới trùng lặp";
	}

	/**
	 * Trả về giải pháp xóa tệp 'uid.dat' của thế giới.
	 */
	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "Xóa tệp 'uid.dat' khỏi thế giới '" + nombreMundo + "'";
	}

	/**
	 * Trả về giải pháp xóa toàn bộ thư mục thế giới.
	 */
	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "Xóa thư mục thế giới '" + nombreMundo + "'";
	}

	/**
	 * Trả về thông báo lỗi cho entity hoặc block entity gây lỗi ticking.
	 */
	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		String color = config.obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>Entity hoặc Block Entity '</span>" + nombre
				+ "<span style='color:#" + color + "'>' loại '</span>" + tipo + "<span style='color:#" + color
				+ "'>' tại vị trí </span>" + coords + "<span style='color:#" + color
				+ "'> đang gây lỗi ticking.</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'>" + "Hướng dẫn khắc phục:<br>"
				+ "1. **MCForge**: Đi đến '[tên_thế_giới]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: Đi đến 'config/neoforge-server.toml'.<br>"
				+ "   *(Lưu ý: Trong chế độ chơi đơn, các thế giới nằm trong thư mục 'saves')*.<br>"
				+ "3. Đặt **removeErroringBlockEntities** và **removeErroringEntities** thành **true**.<br><br>"
				+ "Tùy chọn khác:<br>" + "- **MCEdit**: Dùng để xóa thủ công entity tại tọa độ указан.<br>"
				+ "- **Neruina (Mod)**: Có thể tránh crash, nhưng không phải lúc nào cũng hiệu quả và có thể làm khó việc debug."
				+ "</span>";

		return mensajeBase + instrucciones;
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "Block Entity có vấn đề";
	}

	/**
	 * Trả về giải pháp xóa block entity.
	 */
	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "Xóa entity '" + nombre + "' tại vị trí " + coords + " bằng MCEdit hoặc chỉnh sửa trực tiếp thế giới.";
	}

	/**
	 * Trả về thông báo lỗi cho mod trùng lặp trong Fabric.
	 */
	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' có nhiều phiên bản đã được cài đặt.</b> ";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "Mod trùng lặp trong Fabric";
	}

	/**
	 * Trả về giải pháp xóa file trùng lặp.
	 */
	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "Xóa tệp mod trùng lặp: " + rutaMod;
	}

	/**
	 * Trả về thông báo lỗi cho mod không tương thích.
	 */
	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Các mod '" + primerMod + "' và '" + segundoMod
				+ "' không tương thích với nhau.</b> ";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "Mod không tương thích trong Fabric";
	}

	/**
	 * Trả về giải pháp xóa mod không tương thích.
	 */
	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "Xóa mod '" + nombreMod + "'";
	}

	/**
	 * Trả về thông báo lỗi cho mod bị lỗi nghiêm trọng.
	 */
	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' gặp lỗi nghiêm trọng và không thể chạy.</b> ";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaModFatal() {
		return "Mod lỗi nghiêm trọng";
	}

	/**
	 * Trả về thông báo lỗi cho phụ thuộc mod (nhiều).
	 */
	@Override
	public String mensajeModDependenciaPlural(List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0) {
				deps.append(", ");
			}
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>Các mod sau là bắt buộc nhưng chưa được cài đặt: "
				+ deps.toString() + ".</b>";
	}

	/**
	 * Trả về thông báo lỗi cho phụ thuộc mod (đơn).
	 */
	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod + "' cần mod phụ thuộc '"
					+ dependencia + "'.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod + "' cần mod '"
					+ dependencia + "' với phiên bản " + version + ".</b>";
		}
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaDependenciaMod() {
		return "Thiếu phụ thuộc mod";
	}

	/**
	 * Trả về giải pháp cài đặt mod.
	 */
	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "Cài đặt mod '" + nombreMod + "'";
	}

	/**
	 * Trả về giải pháp cài đặt mod với phiên bản cụ thể.
	 */
	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "Cài đặt mod '" + nombreMod + "' với phiên bản " + version;
	}

	/**
	 * Trả về thông báo lỗi cho plugin không hỗ trợ ticking regional (đơn).
	 */
	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' không tương thích với ticking regional của Folia.</b> ";
	}

	/**
	 * Trả về thông báo lỗi cho nhiều plugin không hỗ trợ ticking regional.
	 */
	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Các plugin sau không tương thích với ticking regional của Folia: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaPluginTickingRegional() {
		return "Plugin không tương thích với Ticking Regional";
	}

	/**
	 * Trả về giải pháp cài đặt phần mềm không có ticking regional.
	 */
	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "Cài đặt phần mềm không có ticking regional, như " + software;
	}

	/**
	 * Trả về thông báo lỗi cho một mod bị thiếu trong datapack (số ít).
	 */
	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Thiếu mod '" + nombreMod + "' trong datapack.</b>";
	}

	/**
	 * Trả về thông báo lỗi cho nhiều mod bị thiếu trong datapack (số nhiều).
	 */
	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Các mod sau đang bị thiếu trong datapack: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" và ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaModFaltanteEnDatapack() {
		return "Thiếu mod trong datapack";
	}

	/**
	 * Trả về thông báo lỗi cho một mod đã gây ra ngoại lệ (số ít).
	 */
	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod + "' đã gây ra lỗi.</b>";
	}

	/**
	 * Trả về thông báo lỗi cho nhiều mod đã gây ra ngoại lệ (số nhiều).
	 */
	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Các mod sau đã gây ra lỗi: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" và ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaModExcepcion() {
		return "Mod Forge gây ngoại lệ";
	}

	/**
	 * Trả về giải pháp cài đặt một phiên bản khác của mod.
	 */
	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "Cài đặt một phiên bản khác của mod '" + nombreMod + "'";
	}

	/**
	 * Trả về thông báo lỗi cho một mod không tương thích với phiên bản Minecraft
	 * (số ít).
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' không tương thích với phiên bản Minecraft " + versionMC + ".</b>";
	}

	/**
	 * Trả về thông báo lỗi cho nhiều mod không tương thích với Minecraft (số
	 * nhiều).
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Các mod sau không tương thích với các phiên bản Minecraft: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (Minecraft ").append(versionesMC.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaModIncompatibleConMinecraft() {
		return "Mod không tương thích với Minecraft";
	}

	/**
	 * Devuelve la solución de instalar una versión diferente de Forge.
	 */
	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "Cài một phiên bản Forge tương thích với Minecraft " + versionMC;
	}

	/**
	 * Devuelve el mensaje de error para un mod faltante (singular).
	 */
	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod
				+ "' đang thiếu và là bắt buộc để tải thế giới hoặc plugin.</b>";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "Thiếu mod";
	}

	/**
	 * Trả về thông báo lỗi cho một mod bị thiếu trong thế giới (số ít).
	 */
	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Thế giới đã được lưu với mod '" + nombreMod
				+ "' nhưng mod này hiện đang bị thiếu.</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods faltantes en el mundo
	 * (plural).
	 */
	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Thế giới đã được lưu với các mod sau đây dường như đang thiếu: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" y ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaWorldModFaltante() {
		return "Thiếu mod trong thế giới";
	}

	/**
	 * Trả về thông báo lỗi cho sự khác biệt phiên bản mod trong thế giới (số ít).
	 */
	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Thế giới đã được lưu với mod '" + nombreMod
				+ "' phiên bản " + versionEsperada + ", nhưng hiện tại đang ở phiên bản " + versionActual + ".</b>";
	}

	/**
	 * Trả về thông báo lỗi cho nhiều mod có sự khác biệt phiên bản (số nhiều).
	 */
	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Các mod sau có sự khác biệt phiên bản trong thế giới đã lưu: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" và ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (Đã lưu: ").append(versionesEsperadas.get(i)).append(", Hiện tại: ")
					.append(versionesActuales.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaVersionModMundo() {
		return "Phiên bản mod trong thế giới đã lưu";
	}

	/**
	 * Trả về thông báo lỗi khi cố tải thế giới từ phiên bản mới hơn.
	 */
	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang cố tải một thế giới được tạo bằng phiên bản Minecraft mới hơn.</b>";
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaVersionDowngrade() {
		return "Tải thế giới từ phiên bản thấp hơn";
	}

	/**
	 * Trả về giải pháp cài đặt phiên bản Minecraft mới hơn.
	 */
	@Override
	public String solucionVersionDowngrade() {
		return "Cài đặt phiên bản Minecraft mới hơn.";
	}

	/**
	 * Trả về giải pháp tạo một thế giới mới.
	 */
	@Override
	public String solucionGenerarNuevoMundo() {
		return "Tạo một thế giới mới.";
	}

	/**
	 * Trả về thông báo lỗi cho plugin thiếu phụ thuộc (số ít).
	 */
	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' cần plugin phụ thuộc '" + dependencia + "'.</b>";
	}

	/**
	 * Trả về thông báo lỗi cho nhiều plugin thiếu phụ thuộc (số nhiều).
	 */
	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Các plugin sau cần những phụ thuộc chưa được cài đặt: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(plugins.get(i)).append("' (").append(dependencias.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaDependenciaPluginFaltante() {
		return "Plugin thiếu phụ thuộc";
	}

	/**
	 * Trả về thông báo lỗi cho một plugin không tương thích (số ít).
	 */
	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' không tương thích với phiên bản máy chủ hiện tại.</b>";
	}

	/**
	 * Trả về thông báo lỗi cho nhiều plugin không tương thích (số nhiều).
	 */
	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Các plugin sau không tương thích với phiên bản máy chủ hiện tại: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" và ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaPluginIncompatible() {
		return "Plugin không tương thích với PocketMine-MP";
	}

	/**
	 * Trả về thông báo lỗi cho một plugin có lỗi khi chạy (số ít).
	 */
	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' đã gây ra lỗi trong quá trình thực thi.</b>";
	}

	/**
	 * Trả về thông báo lỗi cho nhiều plugin có lỗi khi chạy (số nhiều).
	 */
	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Các plugin sau đã gây ra lỗi trong quá trình thực thi: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" và ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Trả về tên của vấn đề để hiển thị trong giao diện.
	 */
	@Override
	public String nombreProblemaPluginEjecucion() {
		return "Plugin có lỗi thực thi";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		// TODO Auto-generated method stub
		return "LegacyRandomSource đa luồng";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang gặp sự cố do nhiều luồng cùng truy cập lớp LegacyRandomSource. Bạn có thể lấy thêm thông tin bằng mod Unsafe World Random Access Detector hoặc mod C2ME.</b> ";
	}

	@Override
	public String desdeUltimoExito() {
		// TODO Auto-generated method stub
		return "Kể từ lần thành công cuối cùng";
	}

	@Override
	public String noHayCambios() {
		// TODO Auto-generated method stub
		return "Không có thay đổi";
	}

	@Override
	public String desdeUltimoIntento() {
		// TODO Auto-generated method stub
		return "Kể từ lần thử cuối cùng";
	}

	@Override
	public String fallo() {
		// TODO Auto-generated method stub
		return "Thất bại";
	}

	@Override
	public String diferentesDeLasMods() {
		// TODO Auto-generated method stub
		return "Khác với các mod";
	}

	@Override
	public String historialDeMods() {
		// TODO Auto-generated method stub
		return "Lịch sử mod";
	}

	@Override
	public String archivo0() {
		// TODO Auto-generated method stub
		return "Tệp 0";
	}

	@Override
	public String archivo1() {
		// TODO Auto-generated method stub
		return "Tệp 1";
	}

	@Override
	public String comparar() {
		// TODO Auto-generated method stub
		return "So sánh";
	}

	@Override
	public String seleccionarDosArchivos() {
		// TODO Auto-generated method stub
		return "Chọn hai tệp";
	}

	@Override
	public String archivoExito() {
		// TODO Auto-generated method stub
		return "Tệp thành công";
	}

	@Override
	public String archivoFalla() {
		// TODO Auto-generated method stub
		return "Tệp lỗi";
	}

	@Override
	public String errorComparandoArchivos() {
		// TODO Auto-generated method stub
		return "Lỗi khi so sánh tệp";
	}

	@Override
	public String comparando() {
		// TODO Auto-generated method stub
		return "Đang so sánh";
	}

	@Override
	public String con() {
		// TODO Auto-generated method stub
		return "với";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
				+ "<p><b>Bảng so sánh lịch sử mod</b></p>"
				+ "<p>Bảng này cho phép bạn so sánh hai danh sách mod từ các lần chạy khác nhau. "
				+ "Chọn một tệp ở cột bên trái và một tệp ở cột bên phải để xem sự thay đổi giữa hai phiên bản.</p>"

				+ "<h3>Cách sử dụng:</h3>" + "<ol>" + "<li><b>Chọn tệp:</b> Nhấp vào các nút radio bên cạnh tên tệp. "
				+ "Các tệp kết thúc bằng <span style='color: #4CAF50; font-weight: bold;'>.exito</span> biểu thị phiên thành công, "
				+ "trong khi <span style='color: #F44336; font-weight: bold;'>.falla</span> biểu thị lỗi.</li>"

				+ "<li><b>So sánh tự động:</b> Khi nhấn nút 'So sánh', hệ thống sẽ phân tích hai danh sách đã chọn "
				+ "và hiển thị các mod được thêm (+) hoặc bị xóa (-).</li>"

				+ "<li><b>Hiển thị kết quả:</b> Kết quả được hiển thị với màu sắc: " + "<ul>"
				+ "<li><span style='color: green;'>+ Mod được thêm</span></li>"
				+ "<li><span style='color: red;'>- Mod bị xóa</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>Tính năng chính:</h3>" + "<ul>" + "<li>Hỗ trợ mọi kết hợp tệp (thành công/lỗi).</li>"
				+ "<li>Hiển thị khác biệt hai chiều để xác định thay đổi chính xác.</li>"
				+ "<li>Có thanh cuộn để duyệt danh sách dài.</li>"
				+ "<li>Tích hợp hình ảnh minh họa giúp dễ hiểu hơn.</li>" + "</ul>"

				+ "<p>Được phát triển với ❤️ để giúp bạn theo dõi thay đổi cấu hình.</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Có thể bạn đang gặp vấn đề liên quan đến IPv6. Có hai cách giải quyết: "
				+ "1) Thêm tham số JVM <code>-Djava.net.preferIPv4Stack=true</code> vào launcher, hoặc "
				+ "2) Sử dụng nút 'QuickFix' trong " + Statics.nombre_cd.obtener() + " để áp dụng bản vá tự động."
				+ "</b>";
	}

	@Override
	public String parcheIPv4() {
		// TODO Auto-generated method stub
		return "Bản vá IPv4/6";
	}

	@Override
	public String carpetaHMCL() {
		// TODO Auto-generated method stub
		return "Thư mục HMCL (chỉ dành cho HelloMinecraftLauncher)";
	}

	@Override
	public String descripcionCurseforge() {
		return "Lưu ý: sẽ không tạo nhật ký nếu tùy chọn \"Bỏ qua Launcher\" được bật trong Cài đặt > Minecraft";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/các bản dẫn xuất: Nhấp chuột phải vào instance và chọn \"Chỉnh sửa Instance\". Trong cửa sổ mở ra, tìm phần \"Nhật ký Minecraft\" hoặc tương tự.<br>"
				+ "Các nhật ký này chứa đầu ra chuẩn (STDOUT), rất quan trọng để chẩn đoán lỗi.";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher): Bạn phải chọn thư mục nơi HMCL được cài đặt và chọn thư mục \".hmcl\". Nhật ký của HMCL được lưu ở vị trí này và chứa thông tin quan trọng về lỗi.<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: Launcher có một tab phát triển hiển thị nhật ký chi tiết. Bạn có thể tìm tab này trong menu tùy chọn của launcher.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: Có một hộp thoại bật lên chứa nhật ký. Nó có các nút để sao chép và tải nhật ký lên. Nhật ký được tạo tự động khi khởi động trò chơi và chứa thông tin quan trọng để chẩn đoán.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: Nhấp chuột phải vào instance và chọn \"Cấu hình\". Sau đó vào phần Nhật ký để xem thông tin quan trọng chứa trong đầu ra chuẩn (STDOUT).";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "Liên kết Markdown: Dán vào đây bất kỳ liên kết nào có chứa nhật ký ở định dạng Markdown. Hệ thống sẽ cố tự động trích xuất các liên kết tới nhật ký (latest.log, launcher_log.txt, debug.log, v.v.) và phân tích chúng.";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "Không tìm thấy nhật ký launcher";
	}

	@Override
	public String imagenNoEncontrada() {
		return "Không tìm thấy hình ảnh";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "CHUNG: Hãy chọn loại launcher bạn đang sử dụng. Nhật ký của launcher (launcher_log.txt, stdout, v.v.) chứa thông tin cực kỳ quan trọng về lỗi mà không xuất hiện trong latest.log. "
				+ Statics.nombre_cd.obtener()
				+ " không thể đọc nhật ký launcher của bạn; có thể launcher không có tệp nhật ký và bạn sẽ cần dán nhật ký thủ công.<br>"
				+ "Để biết thêm thông tin, hãy xem <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">vấn đề này</a>. Các nhật ký này chứa đầu ra chuẩn (STDOUT), cần thiết để chẩn đoán nhiều loại lỗi.";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang thiếu các lớp của Create. Create đã thay đổi rất nhiều: nhiều lớp đã bị xóa. Đặc biệt từ Create 6 (tháng 2 năm 2025), các addon cho các phiên bản Create cũ không còn hoạt động. QuickFix không có giải pháp cho vấn đề này. Bạn cần cập nhật các addon của Create, xóa những addon đã lỗi thời, hoặc dùng đúng phiên bản Create cho các addon mà bạn muốn sử dụng.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang thiếu các lớp của EpicFight. EpicFight đã thay đổi rất nhiều: nhiều lớp đã bị xóa. QuickFix không có giải pháp cho vấn đề này. Bạn cần cập nhật các addon của EpicFight, xóa những addon đã lỗi thời, hoặc dùng đúng phiên bản EpicFight cho các addon mà bạn muốn sử dụng.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang sử dụng OpenJ9, không tương thích với ứng dụng này. Nhiều ứng dụng, bao gồm cả ứng dụng này, không hỗ trợ OpenJ9 do sự khác biệt trong triển khai JVM. QuickFix không thể tự động giải quyết vấn đề này. Bạn cần cài đặt một JDK tương thích như Oracle JDK, OpenJDK Hotspot hoặc các lựa chọn thay thế được khuyến nghị khác.</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Phiên bản ứng dụng này yêu cầu Java 11 (JDK 11) để hoạt động đúng. Bạn đang sử dụng phiên bản Java cũ hơn không tương thích. QuickFix không thể tự động cập nhật Java. Bạn cần tải xuống và cài đặt JDK 11 hoặc phiên bản tương thích cao hơn từ các liên kết được cung cấp.</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đã cấp phát quá nhiều bộ nhớ, khiến hệ thống không còn đủ tài nguyên để hoạt động ổn định. Điều này thường xảy ra khi chỉ định dung lượng RAM vượt quá khả năng hệ thống hoặc khi sử dụng JVM 32-bit không thể xử lý lượng bộ nhớ lớn.</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Để khắc phục, hãy giảm lượng bộ nhớ được cấp phát cho ứng dụng. Thông thường không nên vượt quá 70–80% tổng RAM. Nếu bạn dùng JVM 32-bit, giới hạn tối đa khoảng 2–3 GB bất kể RAM thực tế.</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Để giảm heap, mở cài đặt launcher và tìm mục RAM. Giảm giá trị Xmx xuống mức phù hợp. Ví dụ: RAM 8 GB → cấp 3–4 GB; RAM 16 GB → cấp 6–8 GB. Luôn để lại đủ bộ nhớ cho hệ điều hành và ứng dụng khác.</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Thiếu các tệp thiết yếu của Forge. Tệp '" + archivo
				+ "' không có trong cài đặt của bạn. Thường do cài đặt bị gián đoạn hoặc tệp quan trọng bị xóa. QuickFix không thể khôi phục tự động. Hãy cài đặt lại Forge từ trình cài đặt chính thức.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge không tìm thấy phiên bản Minecraft cần thiết. Yêu cầu phiên bản " + version
				+ " nhưng không có trong tệp '" + archivo
				+ "'. Có thể do không khớp giữa phiên bản Minecraft và Forge. Hãy tải đúng phiên bản Forge phù hợp.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Không tìm thấy target 'fmlclient' để khởi chạy Forge. Điều này cho thấy cài đặt Forge bị thiếu hoặc hỏng. Hãy cài đặt lại Forge bằng trình cài đặt chính thức.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Không tìm thấy lớp chính của Minecraft trong classloader. Thường do cài đặt Forge chưa đầy đủ hoặc xung đột mod. Có thể tệp Minecraft bị hỏng. Hãy cài đặt lại Forge.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Cài đặt Forge chưa hoàn tất. Có thể do cài đặt bị gián đoạn, tệp bị xóa hoặc không tương thích phiên bản Minecraft. Forge cần các tệp cụ thể để hoạt động và hiện đang thiếu.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "Cài đặt Forge chưa hoàn tất";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Để khắc phục, hãy cài đặt lại Forge đúng cách. Tải đúng phiên bản tương ứng với Minecraft và hoàn tất quá trình cài đặt.</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "Tải xuống Forge chính thức";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "Cách cài đặt lại Forge đúng cách";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>Hướng dẫn cài đặt lại Forge:</h3>" + "<ol>"
				+ "<li>Tải trình cài đặt Forge từ trang chính thức (đúng phiên bản Minecraft)</li>"
				+ "<li>Đóng hoàn toàn launcher Minecraft</li>" + "<li>Chạy trình cài đặt Forge với quyền quản trị</li>"
				+ "<li>Chọn 'Installer' (không chọn 'Installer (run client)')</li>"
				+ "<li>Chọn thư mục profile Minecraft</li>" + "<li>Nhấn 'OK' và chờ hoàn tất</li>"
				+ "<li>Khởi động lại launcher và kiểm tra Forge trong danh sách profile</li>" + "</ol>"
				+ "<p><b>Lưu ý:</b> Nếu dùng launcher tùy chỉnh, hãy chọn đúng thư mục profile.</p>" + "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "Hướng dẫn cài đặt lại Forge";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Lỗi liên kết không thỏa mãn: Không thể tải thư viện " + nombreBiblioteca + ". Giải pháp:<br/><br/>"
				+ "a) Thêm thư mục chứa thư viện vào -Djava.library.path hoặc -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Thêm tệp JAR chứa thư viện vào classpath.<br/><br/>"
				+ "Lỗi này xảy ra khi Minecraft không tìm thấy tệp cần thiết để chạy.</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "Lỗi liên kết không thỏa mãn";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Không thể tải thư viện. Giải pháp:<br/><br/>"
				+ "a) Thêm thư mục thư viện vào -Djava.library.path hoặc -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Thêm tệp JAR vào classpath.<br/><br/>"
				+ "Người dùng thông thường nên thử cài đặt lại Minecraft trước.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Xung đột ID: ID <strong>" + id
				+ "</strong> đã được sử dụng bởi <strong>" + modOrigen + "</strong> khi thêm <strong>" + modDestino
				+ "</strong>. Hai mod đang dùng cùng một ID.</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Đã vượt quá phạm vi ID tối đa cho phép. Các mod đang đăng ký ID ngoài giới hạn của phiên bản Minecraft.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "Xung đột ID";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Để khắc phục trong Minecraft 1.12.2, hãy cài đặt <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. Đối với 1.7.10, sử dụng <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Sử dụng các công cụ như <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> hoặc <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> để giải quyết xung đột ID.</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "Cài đặt JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "Cài đặt EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "Sử dụng IdFix Minus hoặc IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "Sử dụng Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "Xem tài liệu tiếng Nhật";
	}

	@Override
	public String escanearDeMCreator() {
		// TODO Auto-generated method stub
		return "Quét MCreator";
	}

	/**
	 * Obtiene el título de la ventana del árbol de mods.
	 * 
	 * @return Título de la ventana
	 */
	@Override
	public String tituloArbolDeMods() {
		return "Cây Mô-đun và Lớp";
	}

	/**
	 * Obtiene el texto para la etiqueta de tipo de búsqueda.
	 * 
	 * @return Texto de la etiqueta
	 */
	@Override
	public String tipoBusqueda() {
		return "Loại";
	}

	/**
	 * Obtiene el texto para el filtro "Todos".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroTodos() {
		return "Tất cả";
	}

	/**
	 * Obtiene el texto para el filtro "Paquetes".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroPaquetes() {
		return "Gói";
	}

	/**
	 * Obtiene el texto para el filtro "Clases".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroClases() {
		return "Lớp";
	}

	/**
	 * Obtiene el texto para el filtro "Métodos".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroMetodos() {
		return "Phương thức";
	}

	/**
	 * Obtiene el texto para el filtro "Campos".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroCampos() {
		return "Trường";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Campo".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroReferenciasCampo() {
		return "Tham chiếu trường";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Método".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroReferenciasMetodo() {
		return "Tham chiếu phương thức";
	}

	@Override
	public String filtroReferenciasClase() {
		return "Tham chiếu lớp";
	}

	@Override
	public String tipBuscar() {
		return "Nhập vào đây để tìm kiếm trong cây mod";
	}

	@Override
	public String botonBuscar() {
		return "Tìm kiếm";
	}

	@Override
	public String botonResetearArbol() {
		return "Đặt lại cây";
	}

	@Override
	public String modsCargados() {
		return "Mod đã tải";
	}

	@Override
	public String clases() {
		return "Lớp";
	}

	@Override
	public String metodos() {
		return "Phương thức";
	}

	@Override
	public String campos() {
		return "Trường";
	}

	@Override
	public String referencias() {
		return "Tham chiếu";
	}

	@Override
	public String resultadosBusqueda() {
		return "Kết quả tìm kiếm";
	}

	@Override
	public String buscarReferencias() {
		return "Tìm kiếm tham chiếu";
	}

	@Override
	public String referenciasMod() {
		return "Tham chiếu của mod";
	}

	@Override
	public String referenciasClase() {
		return "Tham chiếu lớp";
	}

	@Override
	public String referenciasMetodo() {
		return "Tham chiếu phương thức";
	}

	@Override
	public String referenciasCampo() {
		return "Tham chiếu trường";
	}

	@Override
	public String noSeEncontraronReferencias() {
		return "Không tìm thấy tham chiếu";
	}

	@Override
	public String detalleMod() {
		return "Chi tiết mod:";
	}

	@Override
	public String ubicacion() {
		return "Vị trí";
	}

	@Override
	public String nombres() {
		return "Tên";
	}

	@Override
	public String modulo() {
		return "Mô-đun";
	}

	@Override
	public String detalleClase() {
		return "Chi tiết lớp:";
	}

	@Override
	public String detalleMetodo() {
		return "Chi tiết phương thức:";
	}

	@Override
	public String detalleCampo() {
		return "Chi tiết trường:";
	}

	@Override
	public String clase() {
		return "Lớp";
	}

	@Override
	public String tipo() {
		return "Loại";
	}

	@Override
	public String referenciasAMetodos() {
		return "Tham chiếu đến phương thức:";
	}

	@Override
	public String referenciasACampos() {
		return "Tham chiếu đến trường:";
	}

	@Override
	public String arbolDeMods() {
		return "Cây mod";
	}

	@Override
	public String metodo() {
		return "Phương thức";
	}

	@Override
	public String campo() {
		return "Trường";
	}

	@Override
	public String descompilar() {
		return "Giải mã";
	}

	@Override
	public String exportar() {
		return "Xuất";
	}

	@Override
	public String importar() {
		return "Nhập";
	}

	@Override
	public String errorImportar() {
		return "Lỗi nhập";
	}

	@Override
	public String estructuraImportada() {
		return "Cấu trúc đã nhập";
	}

	@Override
	public String estructuraExportada() {
		// TODO Auto-generated method stub
		return "Cấu trúc đã xuất";
	}

	@Override
	public String errorExportar() {
		// TODO Auto-generated method stub
		return "Lỗi xuất";
	}

	@Override
	public String exportando() {
		// TODO Auto-generated method stub
		return "Đang xuất";
	}

	@Override
	public String exportacionTardara() {
		// TODO Auto-generated method stub
		return "Việc xuất sẽ mất thời gian";
	}

	@Override
	public String porFavorEspere() {
		// TODO Auto-generated method stub
		return "Vui lòng chờ";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Bạn không có các tệp nhị phân của VLC. WaterMedia cần các tệp nhị phân của VLC. Bạn cần cài đặt thủ công từ https://www.videolan.org/vlc/.  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "Tải xuống VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Lỗi nghiêm trọng: Tên mô-đun '" + nombreModulo
				+ "' chứa ký tự không hợp lệ. Phần '" + parteInvalida
				+ "' không phải là định danh Java hợp lệ. Điều này xảy ra khi một mod sử dụng từ khóa dành riêng của Java (như 'true', 'class') hoặc các ký tự không được phép trong tên.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "Ký tự không hợp lệ trong tên mod";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "Tên mod '" + nombreModulo + "' không hợp lệ vì chứa '" + parteInvalida
				+ "', là từ khóa dành riêng của Java hoặc một ký tự không được phép. "
				+ "Hãy tìm trong log xem mod nào tương ứng với tên này (thường là tên tệp JAR).";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "Truy cập thư mục mod và chỉnh sửa tệp <b>mods.toml</b> bên trong thư mục <b>/META-INF/</b>. "
				+ "Thay đổi giá trị <b>modId</b> để chỉ dùng chữ cái, số và dấu gạch dưới, không dùng từ khóa dành riêng của Java.";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "Ví dụ tên hợp lệ: 'truemod_shot_enchantment' thay vì 'true.shot.enchantment'. "
				+ "Hãy nhớ rằng tên mod không được chứa dấu chấm, dấu gạch nối hoặc các từ khóa dành riêng của Java như 'true', 'false' hoặc 'class'.";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Lỗi nghiêm trọng với mod: '" + nombreJar
				+ "'. Thiếu trường bắt buộc 'mandatory' trong phần phụ thuộc. Điều này xảy ra khi tệp mods.toml không chỉ rõ liệu phụ thuộc đó có bắt buộc hay không.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "Phụ thuộc mod thiếu trường bắt buộc";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "Mod gặp vấn đề là: <b>" + nombreJar + "</b>. Tệp này có lỗi trong cấu hình phụ thuộc";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "Mở tệp <b>mods.toml</b> bên trong thư mục <b>/META-INF/</b> của mod <b>" + nombreJar + "</b>";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "Trong phần dependencies, hãy đảm bảo mỗi mục đều có <b>mandatory=true</b> hoặc <b>mandatory=false</b> (ví dụ: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Lỗi nghiêm trọng với mod: '" + nombreJar
				+ "'. Cấu hình access transformer không hợp lệ. Điều này xảy ra khi tệp cấu hình có cú pháp sai hoặc tham chiếu đến lớp/phương thức không tồn tại.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Access Transformer không hợp lệ";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "Mod gặp vấn đề là: <b>" + nombreJar + "</b>. Mod này có cấu hình access transformer không hợp lệ";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "Mở tệp <b>accessTransformer.cfg</b> trong mod <b>" + nombreJar
				+ "</b> (thường nằm trong thư mục gốc của tệp JAR)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "Sửa cú pháp access transformer. Các dòng phải theo định dạng: <b>access class.method</b> (ví dụ: public net.minecraft.world.entity.Entity.func_200560_a). Xóa các dòng tham chiếu đến lớp hoặc phương thức không tồn tại trong phiên bản Minecraft của bạn";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Lỗi nghiêm trọng: Không khớp ID mod giữa annotation @Mod và tệp mods.toml. Mod '" + nombreMod
				+ "' không thể tải vì các ID không trùng khớp.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "Không khớp giữa @Mod và mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "Mod đang phát triển '" + nombreMod
				+ "' có sự không khớp giữa ID trong annotation <b>@Mod</b> và giá trị trong <b>mods.toml</b>";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "Đảm bảo ID trong lớp chính trùng với giá trị <b>modId</b> trong tệp <b>/META-INF/mods.toml</b>. Ví dụ: <b>@Mod(\"mimod\")</b> phải trùng với <b>modId=\"mimod\"</b>";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "Nếu bạn dùng Gradle, hãy chạy <b>clean</b> sau khi thay đổi để đảm bảo tài nguyên được cập nhật. Đôi khi tệp cũ vẫn còn trong thư mục build";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "client" : "máy chủ";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "máy chủ" : "client";

		return "<b style='color:#" + config.obtenerColorError() + "'>Lỗi nghiêm trọng: Đang cố tải lớp '" + nombreClase
				+ "' trong môi trường " + plataforma + ", nhưng lớp này được thiết kế cho " + plataformaOpuesta
				+ ". <b>Hãy dùng chức năng 'Cây Mod' để tìm mod gây lỗi</b>. Các mod chỉ hoạt động trên nền tảng tương ứng.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "Mod sai nền tảng";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "Trong tab <b>Cây Mod</b>, tìm các tham chiếu đến lớp <b>" + nombreClase
				+ "</b> để xác định mod gây lỗi";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "client" : "máy chủ";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "máy chủ" : "client";

		return "Mod được xác định là mod dành cho <b>" + plataformaOpuesta + "</b> và không nên dùng trong môi trường "
				+ plataforma + ".";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "Xóa mod gây lỗi khỏi thư mục <b>mods</b>. Nếu cần, hãy tìm mod thay thế phù hợp với <b>client</b> hoặc <b>máy chủ</b>";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Lỗi nghiêm trọng: Thiếu metadata cho modid '").append(modIdFaltante).append("'. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("Các mod sau có thể gây ra lỗi: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", và các mod khác...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Lỗi này xảy ra khi một mod phụ thuộc vào mod khác chưa được cài đặt hoặc có tệp mods.toml không đúng.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "Thiếu metadata mods.toml";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("Các mod sau phụ thuộc vào '").append(modIdFaltante)
					.append("': <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", và các mod khác...");
			paso.append("</b>. Hãy dùng chức năng <b>Cây Mod</b> để xác định mod gây lỗi");
			return paso.toString();
		}
		return "Một mod đang phụ thuộc vào '" + modIdFaltante
				+ "', nhưng mod này chưa được cài đặt. Hãy dùng chức năng <b>Cây Mod</b> để xác định mod gây lỗi";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Bạn có hai lựa chọn:<br/>" + "1. <b>Cài đặt mod còn thiếu</b>: tìm và cài mod có ID '" + modIdFaltante
				+ "'<br/>" + "2. <b>Xóa mod phụ thuộc</b>: nếu không cần, hãy xóa mod phụ thuộc vào '" + modIdFaltante
				+ "'";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Nếu mod '" + modIdFaltante + "' là thư viện (như 'forge', 'minecraft', 'curios'), "
				+ "hãy đảm bảo bạn cài đúng phiên bản Minecraft và Forge. "
				+ "Nếu là mod thông thường, hãy kiểm tra yêu cầu trên trang tải xuống";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Cảnh báo: Lỗi khi khởi tạo hệ thống âm thanh. Âm thanh và nhạc đã bị tắt. Lỗi này thường liên quan đến SoundPhysicsMod và có thể do xung đột thư viện âm thanh.</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "Lỗi hệ thống âm thanh";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "Lỗi thường liên quan đến <b>SoundPhysicsMod</b>. Hãy kiểm tra xem bạn đã cài phiên bản mới nhất tương thích chưa";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "Nếu dùng các mod âm thanh khác, hãy thử gỡ SoundPhysicsMod tạm thời để kiểm tra xung đột";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "Kiểm tra thư mục <b>logs</b> để tìm lỗi liên quan đến LWJGL hoặc OpenAL";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Lỗi nghiêm trọng: Lớp '").append(nombreClase)
				.append("' được đăng ký làm listener sự kiện nhưng không chứa phương thức hợp lệ. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Lớp này nằm trong các mod sau: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", và các mod khác...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Lỗi này xảy ra khi một lớp được đăng ký lắng nghe sự kiện nhưng không có phương thức với @SubscribeEvent.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "Lớp đăng ký nhưng không có listener sự kiện";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("Lớp lỗi nằm trong các mod: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", và các mod khác...");
			paso.append("</b>. Các mod này đang đăng ký sự kiện nhưng không có phương thức hợp lệ");
			return paso.toString();
		}
		return "Lớp <b>" + nombreClase
				+ "</b> đã được đăng ký lắng nghe sự kiện nhưng không có phương thức với annotation <b>@SubscribeEvent</b>. Hãy dùng <b>Cây Mod</b> để xác định mod chứa lớp này";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "Trong mã nguồn, kiểm tra lớp <b>" + nombreClase + "</b> có ít nhất một phương thức dạng: "
				+ "<b>@SubscribeEvent public void method(Event event) { ... }</b>. "
				+ "Nếu là lớp nội bộ, đảm bảo không khai báo static sai";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("Đối với các mod (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", ...");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("hãy liên hệ nhà phát triển mod này để sửa lỗi. ");
			} else {
				paso.append("hãy liên hệ các nhà phát triển mod này để sửa lỗi. ");
			}
		}

		paso.append(
				"Nếu bạn là lập trình viên, hãy xóa đăng ký lớp khỏi EventBus hoặc thêm phương thức @SubscribeEvent hợp lệ");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		String mensaje = "<b style='color:#" + config.obtenerColorError()
				+ "'>Lỗi nghiêm trọng: Lỗi UnionFileSystem khi xử lý '" + nombreArchivo + "'. ";

		mensaje += "Lỗi này rất phổ biến trong modpack cấu hình sẵn và liên quan trực tiếp đến launcher. ";

		mensaje += "Hệ thống không thể đọc tệp mod vì chúng bị hỏng hoặc không đầy đủ.</b>";

		return mensaje;
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "Lỗi UnionFileSystem - Tệp bị hỏng";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		String paso = "Phát hiện lỗi <b>cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException</b> với tệp <b>"
				+ nombreArchivo + "</b>.";

		paso += " Đây là lỗi phổ biến khi launcher không tải đủ tệp.";

		return paso;
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "Hãy cài đặt lại toàn bộ modpack. Lỗi này thường xảy ra khi launcher không tải đầy đủ tệp. "
				+ "Nếu bạn dùng <b>Luna Pixel</b>, nên chuyển sang <b>ATLauncher</b> vì ổn định hơn.";
	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "Nếu vẫn lỗi sau khi cài lại:<br/>" + "1. <b>Đổi launcher</b><br/>"
				+ "2. Nếu dùng <b>Luna Pixel</b>, hãy dùng <b>ATLauncher</b><br/>"
				+ "3. Kiểm tra mạng và dung lượng ổ đĩa";
	}

	/**
	 * Obtiene el mensaje de confirmación para habilitar el proxy
	 */
	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "Bạn có muốn bật ProxySysOutSysErr?\n\n" + "Tùy chọn này cho phép " + Statics.nombre_cd.obtener()
				+ " truy cập System.out và System.err khi launcher không cung cấp nhật ký.\n\n"
				+ "Chỉ bật khi bạn không thể dán nhật ký thủ công.\n\n"
				+ "Cảnh báo: Có thể gây xung đột với một số mod hoặc launcher.\n\n"
				+ "Cần khởi động lại ứng dụng để áp dụng.";
	}

	@Override
	public String confirmacionTitulo() {
		return "Xác nhận";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr đã được bật.\n\n" + "Cần khởi động lại " + Statics.nombre_cd.obtener()
				+ " để áp dụng.";
	}

	@Override
	public String informacionTitulo() {
		return "Thông tin";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("Lỗi nghiêm trọng: AzureLib và GeckoLib đã được khởi tạo quá sớm! ");
		} else if (azureLibError) {
			mensaje.append("Lỗi nghiêm trọng: AzureLib đã được khởi tạo quá sớm! ");
		} else if (geckoLibError) {
			mensaje.append("Lỗi nghiêm trọng: GeckoLib đã được khởi tạo quá sớm! ");
		}

		mensaje.append(
				"Lỗi này xảy ra khi bạn cố sử dụng mod Fabric với phiên bản không phải Fabric của các thư viện này. ");

		if (connectorPresente) {
			mensaje.append(
					"Phát hiện mod tương thích (Sinytra Connector hoặc specialcompatibilityoperation), cho thấy bạn đang dùng mod Fabric trong môi trường Forge hoặc FeatureCreep. ");
			mensaje.append("Hãy kiểm tra lỗi 'Lỗi khởi tạo FabricMC' trong log để xác định mod gây lỗi. ");
		}

		mensaje.append(
				"AzureLib và GeckoLib là thư viện quan trọng cho mod animation nhưng phải đúng nền tảng (Fabric hoặc Forge). ");
		mensaje.append("Trò chơi không thể tải mod animation do xung đột khởi tạo này.");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "Thư viện khởi tạo quá sớm";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "Kiểm tra lỗi 'Lỗi khởi tạo FabricMC' để xác định mod gây lỗi";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "Đảm bảo bạn dùng đúng phiên bản AzureLib/GeckoLib cho nền tảng (Forge hoặc Fabric)";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Lỗi nghiêm trọng: Không tương thích giữa C2ME và mod kết nối. "
				+ "C2ME truy cập các thành phần Java bị hạn chế khi dùng với Sinytra Connector hoặc mod tương tự. "
				+ "<b>C2ME không tương thích, nhưng <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> là lựa chọn thay thế phù hợp</b>. "
				+ "Trò chơi không thể khởi động do xung đột bảo mật Java.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "Không tương thích C2ME";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "Xóa C2ME khỏi thư mục mods";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "Cài đặt <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> thay thế (tương thích với Sinytra Connector)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "Đảm bảo các mod kết nối được cập nhật phiên bản mới nhất";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Lỗi nghiêm trọng: Plugin JEI của mod '" + modId
				+ "' bị lỗi. Lớp '" + nombreClase + "' (plugin ID: '" + pluginId
				+ "') gây crash khi khởi động. Điều này xảy ra khi tích hợp JEI bị lỗi hoặc không tương thích.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "Plugin JEI lỗi";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "Mod <b>" + modId + "</b> có plugin JEI lỗi. Dùng <b>Cây Mod</b> để xác định chính xác";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "Tạm thời xóa mod <b>" + modId + "</b> khỏi thư mục mods để kiểm tra";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "Tìm bản cập nhật của mod <b>" + modId
				+ "</b> hoặc báo lỗi cho nhà phát triển. Hiện tại cần xóa mod để chạy game";
	}

	@Override
	public String tituloLectador() {
		return "Trình đọc Console - Crash Detector";
	}

	@Override
	public String obtenerTituloLeyenda() {
		return "CHÚ GIẢI MÀU";
	}

	@Override
	public String obtenerErrorEnLeyenda() {
		return "Lỗi nghiêm trọng";
	}

	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "Stacktrace";
	}

	@Override
	public String obtenerTituloError() {
		return "Lỗi";
	}

	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "Đã xảy ra lỗi khi xử lý dòng được chọn";
	}

	@Override
	public String obtenerNombreError() {
		return "TÊN LỖI";
	}

	/**
	 * Obtiene el título para el área de descripción de error
	 * 
	 * @return Título del panel de descripción de error
	 */
	@Override
	public String obtenerDescripcionError() {
		return "MÔ TẢ CHI TIẾT";
	}

	/**
	 * Obtiene el título para el selector de consolas
	 * 
	 * @return Título del combobox de selección
	 */
	@Override
	public String obtenerSeleccionarConsola() {
		return "CHỌN BẢNG ĐIỀU KHIỂN";
	}

	/**
	 * Obtiene el nombre predeterminado para errores
	 * 
	 * @return Nombre genérico para errores
	 */
	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "Lỗi chưa được xác định";
	}

	/**
	 * Obtiene la descripción predeterminada para errores
	 * 
	 * @return Descripción genérica para errores
	 */
	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "Đã phát hiện lỗi nghiêm trọng trong nhật ký. " + "Hãy xem stack trace để xác định nguyên nhân gốc.";
	}

	/**
	 * Obtiene el mensaje para errores de lectura de archivos
	 * 
	 * @return Mensaje específico para lỗi en lectura
	 */
	@Override
	public String obtenerErrorLecturaArchivo() {
		return "Không thể đọc tệp nhật ký. " + "Hãy kiểm tra xem tệp có tồn tại và có quyền đọc hay không.";
	}

	/**
	 * Obtiene la etiqueta para el botón en la barra lateral
	 * 
	 * @return Texto que aparecerá en el botón lateral
	 */
	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "Trình phân tích nhật ký";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Lỗi nghiêm trọng: Không thể đăng ký subscriber tự động cho mod '").append(modId).append("'. ");

		mensaje.append("Lớp có vấn đề: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Lớp này nằm trong: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", và các mod khác...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Lỗi này xảy ra khi một mod cố đăng ký một lớp làm subscriber tự động nhưng lớp đó không thể được tải đúng cách. ");
		mensaje.append(
				"<b>Hãy kiểm tra các lỗi khác trong log, vì nguyên nhân thực sự có thể nằm ở phần khác của nhật ký</b>.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "Lỗi đăng ký subscriber tự động";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "Mod <b>" + modId + "</b> đang cố đăng ký lớp <b>" + nombreClase
				+ "</b> làm subscriber tự động nhưng đã thất bại. Hãy kiểm tra xem lớp này có tồn tại và có thể truy cập được không";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder(
					"Lớp có vấn đề <b>" + nombreClase + "</b> nằm trong các tệp sau: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", và các mod khác...");
			paso.append("</b>. ");
			paso.append("Hãy dùng chức năng <b>Cây Mod</b> để xác nhận tệp cụ thể nào chứa lớp có vấn đề");
			return paso.toString();
		}
		return "Lớp <b>" + nombreClase + "</b> không được tìm thấy trong bất kỳ tệp mod nào. Hãy kiểm tra xem mod <b>"
				+ modId + "</b> đã được cài đặt đúng chưa. Hãy dùng chức năng <b>Cây Mod</b> để hỗ trợ xác định vấn đề";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "Hãy cập nhật mod <b>" + modId
				+ "</b> lên phiên bản mới nhất tương thích với phiên bản Minecraft và Forge của bạn. "
				+ "Nếu lỗi vẫn còn, hãy liên hệ với nhà phát triển mod và báo lỗi cùng với lớp có vấn đề";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "Hãy kiểm tra <b>các lỗi khác trong log</b> trước thông báo này, vì nguyên nhân thực sự có thể nằm ở phần khác của nhật ký. "
				+ "Đôi khi một lỗi trước đó ngăn cản việc tải đúng các lớp cần thiết cho subscriber";
	}

	@Override
	public String limpiado() {
		return "Đã làm sạch";
	}

	@Override
	public String original() {
		return "Gốc";
	}

	@Override
	public String verEnConsola() {
		return "Xem trong console";
	}

	@Override
	public String advertencia() {
		return "Cảnh báo";
	}

	@Override
	public String fatal() {
		return "Lỗi nghiêm trọng";
	}

	@Override
	public String noRegistroDeBattly() {
		return "BattlyLauncher không có log hoặc console để sao chép. Bạn có thể dùng ProxySysOutSysErr để bắt STDOUT và STDERR rồi khởi động lại game, nhưng ProxySysOutSysErr có thể xung đột với một số mod";
	}

	@Override
	public String noRegistroDeNightWorld() {
		return "Bạn cần bật chế độ debug trong cài đặt NightWorld để lấy log launcher. Điều này rất quan trọng vì bao gồm STDOUT và STDERR";
	}

	@Override
	public String noRegistroDeMCServidor() {
		return "Bạn cần lưu hoặc dán nội dung terminal của máy chủ vì nó chứa thông tin không có trong log khác, bao gồm STDOUT, STDERR và lỗi khác. "
				+ "Hãy dán nội dung phiên cuối. Trong tương lai, bạn có thể lưu log bằng cách thêm >> cd_launcherlog vào lệnh chạy";
	}

	// Métodos para Idioma relacionados con LexForgeMLTransformerEnNeoForge

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("Lỗi nghiêm trọng: phát hiện transformador LexForge trong môi trường NeoForge. ");
		sb.append("</b>");

		sb.append("Lớp liên quan: <b>").append(claseReceptora).append("</b>. ");
		sb.append("Interface: <b>").append(interfazObjetivo).append("</b> ");
		sb.append("thiếu phương thức <b>").append(firmaMetodoFaltante).append("</b>. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Phát hiện trong: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", và các mod khác...");
			sb.append("</b>. ");
		} else {
			sb.append("Không tìm thấy JAR chứa lớp này; có thể bị đóng gói hoặc jar-in-jar. ");
		}

		sb.append(
				"Lỗi này xảy ra khi transformador hoặc service của ModLauncher được build cho MinecraftForge/LexForge ");
		sb.append("được chạy trên NeoForge với API không tương thích. ");
		sb.append("Hãy cập nhật hoặc thay thế bằng phiên bản phù hợp NeoForge.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "Transformador LexForge trên NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "Xác định transformador không tương thích: <b>" + claseReceptora + "</b>. " + "API yêu cầu <b>"
				+ interfazObjetivo + "</b> nhưng thiếu <b>" + firmaMetodoFaltante + "</b>. "
				+ "Kiểm tra META-INF/services và vô hiệu hóa trong NeoForge";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Vị trí mod: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", và các mod khác...");
			sb.append("</b>. ");
		} else {
			sb.append("Không tìm thấy JAR chứa lớp. Kiểm tra jar-in-jar hoặc dependency bị nhúng. ");
		}
		sb.append("Tạm thời gỡ mod hoặc dùng phiên bản tương thích NeoForge để kiểm tra.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "Thay thế bằng phiên bản dành cho NeoForge hoặc build lại theo ModLauncher của NeoForge. "
				+ "Tránh dùng binary cũ của LexForge/MinecraftForge";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "Dọn thư mục mods, xóa jar trùng lặp, xóa cache launcher nếu cần và chạy lại để kiểm tra";
	}

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia không thể khởi động: Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("không tương thích.</b> ");
		sb.append("Hãy gỡ Xenon và dùng Embeddium hoặc Sodium. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Phát hiện tại: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", và các mod khác...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia không tương thích Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "Phát hiện " + label + " không tương thích với WaterMedia. Hãy gỡ khỏi profile";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("Vị trí: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", và các mod khác...");
			sb.append("</b>. Hãy xóa JAR này.");
			return sb.toString();
		}
		return "Không tìm thấy JAR. Kiểm tra thư mục mods và xóa Xenon.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "Cài đặt Embeddium hoặc Sodium thay thế và khởi động lại game.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "Lỗi nén (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>Deflater bị đóng khi sao chép tài nguyên TACZ.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Liên quan: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", và các mod khác");
			sb.append("</b>. ");
		}
		sb.append(
				"<br/><b>Giải pháp:</b> trong <code>tacz/tacz-pre.toml</code> đặt <code>DefaultPackDebug=true</code>. ")
				.append("Nếu cần, tạo world trước rồi bật lại.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "Trong tacz/tacz-pre.toml đặt DefaultPackDebug=true. Nếu cần, tạo world trước rồi bật lại.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "Thiếu density functions";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>Thiếu density functions trong log.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("Thiếu: ");
			for (int i = 0; i < Math.min(4, claves.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append("<code>").append(claves.get(i)).append("</code>");
			}
			if (claves.size() > 4)
				sb.append(", …");
			sb.append(". ");
		}
		sb.append("<br/><b>Giải pháp:</b> cài hoặc bật mod/datapack cung cấp các chức năng này và khởi động lại. "
				+ "Một nguyên nhân phổ biến khác là xung đột mod (ví dụ Terralith).");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "Cài hoặc bật mod/datapack cung cấp các chức năng này rồi khởi động lại game.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Thiếu mục log: ").append(claveFaltante).append(". ");
		sb.append("Thường gặp với bản alpha Steam & Railways cho Create 6.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (alpha)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "Gỡ hoặc thay thế bản alpha Steam & Railways bằng phiên bản tương thích.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Xung đột: Multiworld với Sodium/Embeddium/Rubidium gây lỗi IncompatibleClassChangeError. ");
		sb.append("Giải pháp: gỡ một trong các mod hoặc dùng phiên bản tương thích.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "Xung đột Multiworld và mod hiệu năng";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Gỡ Multiworld hoặc Sodium/Embeddium/Rubidium hoặc cập nhật các mod tương thích.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Sodium phát hiện driver GPU không tương thích. "
				+ "Hãy cập nhật driver hoặc làm theo hướng dẫn của Sodium." + "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "Lỗi ngữ cảnh OpenGL";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OpenGL lỗi: không có context hợp lệ hoặc driver gặp sự cố." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "Cập nhật hoặc cài lại driver GPU, tắt overlay và thử chạy không có mod hiệu năng.";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "Đã sao chép vào clipboard.";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		return "Tìm kiếm trong file nén (.zip/.jar/.war/.ear/.fpm/.rar Java)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Lỗi độ phân giải texture: Không thể xử lý "
				+ recurso + " - kích thước: " + tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "Lỗi độ phân giải texture";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "Lỗi xảy ra khi texture quá lớn hoặc có quá nhiều resource pack. "
				+ "Hãy giảm độ phân giải hoặc loại bớt resource pack.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Lỗi ModLauncher: đường dẫn chứa ký tự không hợp lệ. "
				+ "Giải pháp: đổi tên thư mục chỉ dùng ký tự ASCII (a-z, A-Z, 0-9), không dấu, không ký tự đặc biệt.</b>";
	}

	@Override
	public String nombre_error_modlauncher_path() {
		return "Lỗi đường dẫn ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "Đổi tên đường dẫn instance sang ký tự ASCII, tránh dấu và ký tự đặc biệt.";
	}

	@Override
	public String tituloEditorCodice() {
		return "Trình chỉnh sửa mã";
	}

	@Override
	public String nuevo() {
		return "Mới";
	}

	@Override
	public String actualizarSeleccionado() {
		return "Cập nhật mục đã chọn";
	}

	@Override
	public String eliminarSeleccionado() {
		return "Xóa mục đã chọn";
	}

	@Override
	public String exportarJSON() {
		return "Xuất JSON...";
	}

	@Override
	public String guardarTodo() {
		return "Lưu tất cả";
	}

	@Override
	public String general() {
		return "Chung";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "Văn bản cần tìm";
	}

	@Override
	public String filtro() {
		return "Bộ lọc (id)";
	}

	@Override
	public String criticalidad() {
		return "Mức độ (CẢNH BÁO/LỖI/NGHIÊM TRỌNG)";
	}

	@Override
	public String prioridad() {
		return "Ưu tiên";
	}

	@Override
	public String lista() {
		return "Danh sách kiểm tra";
	}

	@Override
	public String colIdioma() {
		return "Ngôn ngữ";
	}

	@Override
	public String colNombre() {
		return "Tên";
	}

	@Override
	public String colResultado() {
		return "Kết quả";
	}

	@Override
	public String vistaJson() {
		return "Xem trước JSON";
	}

	@Override
	public String idiomas() {
		return "Ngôn ngữ (bắt buộc tất cả)";
	}

	@Override
	public String elegirFiltro() {
		return "Chọn...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "Chọn một bộ lọc";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "Bộ lọc khả dụng";
	}

	@Override
	public String faltanCampos() {
		return "Vui lòng điền đầy đủ các trường bắt buộc.";
	}

	@Override
	public String critInvalida() {
		return "Mức độ không hợp lệ. Dùng ADVERTENCIA, ERROR hoặc FATAL.";
	}

	@Override
	public String filtroNoExiste() {
		return "Bộ lọc không tồn tại.";
	}

	@Override
	public String faltanIdiomas() {
		return "Hoàn thành tên và kết quả cho tất cả ngôn ngữ:";
	}

	@Override
	public String verificacionInvalida() {
		return "Một kiểm tra không hợp lệ. Hãy kiểm tra lại.";
	}

	@Override
	public String guardadoOk() {
		return "Đã lưu thành công.";
	}

	@Override
	public String editorCodiceBoton() {
		return "Thêm lý do";
	}

	@Override
	public String descripcionEditorCodice() {
		return "Bạn có thể thêm lý do tại đây. Cần một ID (không ký tự đặc biệt, dấu hoặc khoảng trắng). "
				+ "Bộ lọc: \"linea contiene\" (tìm trong dòng), \"todo contiene\" (toàn bộ log), "
				+ "\"regex linea\" hoặc \"regex todos\" (khuyến nghị dùng linea). "
				+ "Chọn mức độ: FATAL, ERROR hoặc ADVERTENCIA. "
				+ "Mỗi ngôn ngữ cần tên và kết quả hiển thị. Bạn có thể thêm hoặc xóa kiểm tra.";
	}

	@Override
	public String descartarCambios() {
		return "Bỏ các thay đổi chưa lưu?";
	}

	@Override
	public String confirmacion() {
		return "Xác nhận";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "Bạn có muốn lưu trước khi thoát?";
	}

	@Override
	public String salirSinGuardar() {
		return "Thoát không lưu";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Lỗi nghiêm trọng: Không thể tải service ModLauncher (IDependencyLocator).<br>");
		sb.append("🔹 <b>Lớp lỗi:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>Mod liên quan:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append("🔸 <b>Không xác định mod.</b> Kiểm tra mod mới hoặc mod dev.<br>");
		}

		sb.append("🔸 <b>Nguyên nhân:</b> file <code>META-INF/services</code> bị lỗi hoặc không tương thích.<br>");
		sb.append("🔸 <b>Hậu quả:</b> game không thể khởi động.<br>");
		sb.append("🔸 <b>Giải pháp:</b> cập nhật, cài lại hoặc xóa mod lỗi.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "Lỗi cấu hình service (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. Xác định mod gây lỗi (mod mới hoặc mod dev).";
		}
		return "1. Mod lỗi: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. Cập nhật hoặc xóa mod. Đảm bảo tương thích với Forge/NeoForge.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String color = config.obtenerColorError();
		return "<b style='color:#" + color + "'>Lỗi: Trường không tồn tại.</b><br>" + "Mod truy cập trường <b>" + campo
				+ "</b> không tồn tại.<br>" + "<code>" + escapeHtml(lineaCompleta) + "</code>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "Trường không tồn tại (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. Mod không tương thích với phiên bản hiện tại.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. Cập nhật mod hoặc liên hệ tác giả.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String color = config.obtenerColorError();
		return "<b style='color:#" + color + "'>Lỗi: Phương thức không tồn tại.</b><br>" + "Mod gọi phương thức <b>"
				+ metodo + "</b> không tồn tại.<br>" + "<code>" + escapeHtml(lineaCompleta) + "</code>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "Phương thức không tồn tại (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. Mod không tương thích.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. Cập nhật mod hoặc báo lỗi cho tác giả.";
	}

	@Override
	public String mensajeAyudar() {
		return "<div><b>Cần trợ giúp?</b><br>" + "Dùng nút chia sẻ để gửi log cho đội hỗ trợ.</div>";
	}

	@Override
	public String restablecerPlantilla() {
		return "Khôi phục mẫu";
	}

	@Override
	public String restablecer() {
		return "Khôi phục";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		return "Khôi phục " + nombreImagen + "?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		return "Khôi phục mẫu mặc định?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Thiếu lớp AzureLib. Hãy cài đúng phiên bản phù hợp.</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Mod healight gây lỗi: NoSuchFieldError INT. Không tương thích.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "Xóa hoặc cập nhật mod healight.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "Lỗi healight INT";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b>Lớp <code>").append(clase).append("</code> không triển khai phương thức:<br>").append("<code>")
				.append(metodo).append("</code><br>").append("từ <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br>Mod nghi ngờ: <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• Lỗi này xảy ra khi một mod triển khai interface nhưng thiếu phương thức bắt buộc. "
				+ "Hãy cập nhật <b>cả hai mod</b> (mod định nghĩa interface và mod triển khai). "
				+ "Nếu không rõ mod nào, hãy xem tên trong thông báo lỗi.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "Phương thức interface chưa được triển khai (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Một mod đang cố tải lớp phía <b>client</b> "
				+ "(<code>AnimationMetadataSection</code>) trên <b>máy chủ</b>, điều này không thể xảy ra. "
				+ "Lỗi này xuất hiện khi mod không tách riêng code client/server đúng cách. "
				+ "<code>ModernFix</code> có thể làm lộ lỗi này nhưng không phải nguyên nhân trực tiếp.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>Cách nhanh:</b> Tạm thời gỡ <code>ModernFix</code> để kiểm tra server có chạy không.<br>"
				+ "• <b>Cách chính:</b> Xác định mod gây lỗi (mod animation, texture, thư viện đồ họa) và cập nhật hoặc xóa.";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "Lớp client chạy trên server (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "File cấu hình của mod <code>Sinytra Connector</code> bị hỏng "
				+ "(thường chứa ký tự null <code>\\u0000</code>). "
				+ "Nguyên nhân: crash, lỗi ghi file hoặc xung đột mod.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• Vào thư mục <code>config/</code>.<br>" + "• Xóa các file config liên quan connector.<br>"
				+ "• Khởi động lại game để tạo file mới.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "Cấu hình Sinytra Connector bị hỏng";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "File <code>" + nombreJar
				+ "</code> bị hỏng hoặc tải chưa hoàn tất.<br>" + "Thiếu phần kết thúc của file ZIP.<br>"
				+ "Nguyên nhân thường do tải xuống bị gián đoạn.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "File JAR bị hỏng";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• Xóa file và tải lại từ nguồn chính thức.<br>"
				+ "• Dùng launcher đáng tin cậy (ATLauncher, Prism).<br>" + "• Kiểm tra kết nối mạng ổn định.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Không thể tải world vì file NBT bị hỏng.<br>"
				+ "Lỗi: <code>UTFDataFormatException tại byte " + byteCorrupto + "</code>.<br><br>"
				+ "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ Hãy sao lưu world trước khi sửa.</b><br><br>" + "Dùng <b>NBT Studio</b> hoặc editor hex để sửa. "
				+ "Hoặc khôi phục từ backup.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>Hãy sao lưu toàn bộ thư mục world</b> trước khi sửa.<br>"
				+ "• Dùng NBT editor (NBT Studio) để sửa file bị lỗi.<br>"
				+ "• Nếu cần, kiểm tra bằng hex editor tại vị trí byte lỗi.<br>"
				+ "• Nếu không chắc, hãy khôi phục từ bản sao lưu.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "World bị hỏng: lỗi dữ liệu NBT";
	}

	@Override
	public String problema_con_openAL() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Lỗi OpenAL: driver hoặc thư viện không tương thích. "
				+ "Hãy cập nhật driver hoặc dùng OpenAL của hệ điều hành.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Server không thể khởi động vì file world bị khóa.<br>"
				+ "Nguyên nhân: server khác đang chạy, antivirus hoặc crash trước đó.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• Đóng tất cả instance server.<br>" + "• Kiểm tra process java còn chạy.<br>"
				+ "• Tắt antivirus nếu cần.<br>" + "• Xóa <code>session.lock</code> nếu chắc chắn không còn server.";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "File world bị khóa";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Mod cố kế thừa lớp final <code>"
				+ clasePadreFinal + "</code>.<br>" + "Lớp lỗi: <code>" + claseHija + "</code>.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• Cập nhật mod.<br>" + "• Dùng phiên bản tương thích.<br>" + "• Tạm xóa mod để kiểm tra.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "Kế thừa lớp final";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Rubidium lỗi thời xung đột với Iris/Oculus.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• Xóa Rubidium.<br>" + "• Dùng Embeddium.<br>" + "• Không dùng nhiều fork Sodium.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium lỗi thời";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Voice Chat không khởi động: port đang dùng.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• Đóng app dùng port.<br>" + "• Đổi port (24455).";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Voice Chat port bận";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "BlockItem null: <code>" + nombreBlockItem
				+ "</code>.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• Cập nhật Create và addon.<br>" + "• Tắt addon từng cái để tìm lỗi.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "BlockItem null";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b>Mod không tương thích:</b><ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• Dùng cùng nền tảng (Forge hoặc Fabric).<br>" + "• Xóa mod sai nền tảng.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "Mod không tương thích";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Không thể tạo model " + modid + ":"
				+ nombreModelo + ".</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• Cập nhật mod.<br>" + "• Tải lại file.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "Lỗi tạo model";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Xung đột Moonlight và Iceberg.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• Cập nhật mod.<br>" + "• Gỡ Iceberg nếu cần.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "Xung đột Moonlight/Iceberg";
	}

	@Override
	public String instantanea() {
		return "Snapshot";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "Từ snapshot trước";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "Chọn file";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "Tạo snapshot thành công";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "Lỗi tạo snapshot";
	}

	@Override
	public String consejo() {
		return "Gợi ý";
	}

	@Override
	public String resultadoMuestra() {
		return "Kết quả mẫu";
	}

	@Override
	public String historaDeModsDesc() {
		// TODO Auto-generated method stub
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>Mẹo:</b> Chọn hai tệp lịch sử để so sánh danh sách mod. "
				+ "  Kết quả hiển thị <span style='color:%s;'>các mod được thêm (+)</span> và "
				+ "  <span style='color:%s;'>các mod bị xóa (&#8722;)</span> dựa trên tên đã được chuẩn hóa. "
				+ "  Dùng nút 'Ảnh chụp nhanh' để tạo bản sao của một tệp hiện có với phần mở rộng .instantanea."
				+ "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		// TODO Auto-generated method stub
		return "Lấy liên kết đến nhật ký dưới dạng Markdown mà không có báo cáo";
	}

	@Override
	public String titulo_configuracion() {
		return "Cấu hình";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "Lỗi không mong muốn khi chia sẻ.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "Lỗi không mong muốn khi tạo liên kết.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "Lỗi không mong muốn khi xử lý nút.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "Không có tệp liên kết để mở.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "Tệp không tồn tại:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "Không thể mở trong trình soạn thảo.\nĐường dẫn sẽ được sao chép vào bảng tạm.";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "Không thể mở tệp; đường dẫn đã được sao chép vào bảng tạm.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "Môi trường desktop không được hỗ trợ; đường dẫn đã được sao chép vào bảng tạm.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "Bạn đang gặp giới hạn số lượng yêu cầu. Hãy thử dùng trang nhật ký khác hoặc API nhật ký khác.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		// TODO Auto-generated method stub
		return "Chia sẻ liên kết";
	}

	@Override
	public String infoDeTrazos() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Việc sửa các phần đầu của stack trace là ưu tiên hàng đầu. " + "Định dạng là Cấp độ, Dòng. "
				+ "Tất cả các nhật ký đều có hệ thống đánh số. " + Verificaciones.nl_html
				+ "Thông thường bạn cần tìm ở các cấp thấp hơn trong tất cả các nhật ký; các trace ở cấp cao thường là dương tính giả. "
				+ "Điều quan trọng là dùng khả năng quan sát của bạn trong bảng điều khiển, vì việc phân tích trace không hoàn hảo khi có quá nhiều trace."
				+ "</b>";
	}

	// --- Buscador de Canario de Orden (Warrant Canary) ---

	/**
	 * Texto del botón para el buscador de canario de orden. Ejemplo: "Buscador de
	 * canario de orden"
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "Trình tìm kiếm warrant canary";
	}

	/**
	 * Mensaje mostrado en el cuadro de diálogo informando que la función estará
	 * disponible próximamente.
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "Tính năng này sẽ sớm khả dụng.";
	}

	/**
	 * Título del cuadro de diálogo que informa sobre la disponibilidad futura del
	 * buscador de canario de orden.
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "Sắp ra mắt";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		// TODO Auto-generated method stub
		return "Mods không tương thích với Crash Assistant (có thể sai)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		// TODO Auto-generated method stub
		return "Mod không tương thích với gói mod (modpack) khi dùng CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant có một danh sách mod mà nó cho là không tương thích, nhưng chúng tôi không có bằng chứng rằng điều đó đúng, và thông báo lỗi chỉ có bằng tiếng Anh. Nếu bạn muốn chơi với các mod này, bạn có thể chỉnh sửa tệp <code>config/crash_assistant/config.toml</code> và đổi <code>enabled = true</code> trong phần [compatibility] thành <code>enabled = false</code>.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant có khả năng đánh dấu các mod là không tương thích, nhưng đôi khi điều này không chính xác và thông báo lỗi chỉ có bằng tiếng Anh. Nếu bạn muốn chơi với các mod này, bạn có thể chỉnh sửa tệp <code>config/crash_assistant/problematic_mods_config.json</code> và đổi <code>should_crash_on_startup</code> từ <code>true</code> thành <code>false</code>.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Lỗi: Mod '" + modId + "' yêu cầu mod '"
				+ dependencia + "'. Hiện tại là " + actual + "." + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Lỗi: Mod '" + modId
				+ "' yêu cầu phiên bản '" + requerido + "' hoặc cao hơn của '" + dependencia
				+ "', nhưng mod đó chưa được cài đặt." + "</span>";
	}

	// En la clase MonitorDePID.idioma (añadir este método)
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Lỗi: Mod '" + modId
				+ "' không tương thích với phiên bản hiện tại của '" + dependencia + "'. "
				+ "Bạn phải xóa mod 'Iris/Oculus & GeckoLib Compat' vì nó không tương thích với Superb Warfare và không hoạt động với phiên bản GeckoLib mới nhất. "
				+ "Phiên bản hiện tại: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "Lỗi: Không thể thực thi tác vụ cho lớp '" + clase + "'. "
				+ "Lỗi này thường gặp với các mod không tương thích với nhau hoặc xung đột với các mod khác đã cài đặt.";
	}

	public String nombre_lỗi_ejecucion_tareas() {
		return "Lỗi khi thực thi tác vụ";
	}

	public String recomendacion_lỗi_ejecucion() {
		return "Loại lỗi này thường xảy ra khi có sự không tương thích giữa các mod. "
				+ "Đặc biệt phổ biến với các mod không hoạt động đúng với ConnectorMod.";
	}

	public String info_clase_problematica() {
		return "Lớp có vấn đề:";
	}

	public String no_se_encontraron_clases_problema() {
		return "Không tìm thấy lớp cụ thể nào có vấn đề khi thực thi.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột nghiêm trọng giữa OptiFine và Entity Model Features (EMF). "
				+ "Các mod này không tương thích với nhau và gây ra lỗi injection khiến trò chơi không thể khởi động."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "Xung đột giữa OptiFine và Entity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "Gỡ cài đặt OptiFine hoặc Entity Model Features, vì chúng không tương thích với nhau.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột nghiêm trọng giữa OptiFine và Fusion. "
				+ "Các mod này không tương thích và gây ra lỗi tiêm (injection) khiến trò chơi không thể khởi động."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "Xung đột giữa OptiFine và Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "Gỡ cài đặt OptiFine hoặc Fusion, vì chúng không tương thích với nhau.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel (yêu cầu bởi Create) cần Sodium phiên bản 0.6.0-beta.2 hoặc cao hơn. Rubidium là phiên bản 0.5.3. "
				+ "Hãy cân nhắc sử dụng <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> như một giải pháp thay thế."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "Xung đột giữa Flywheel và phiên bản Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "Cập nhật Sodium lên phiên bản 0.6.0-beta.2 hoặc cao hơn, hoặc cài đặt <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> như một giải pháp thay thế tương thích.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột nghiêm trọng giữa OptiFine và Epic Fight. "
				+ "Các mod này không tương thích và gây ra lỗi tiêm (injection) khiến trò chơi không thể khởi động."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "Xung đột giữa OptiFine và Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "Gỡ cài đặt OptiFine hoặc Epic Fight, vì chúng không tương thích với nhau.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột nghiêm trọng giữa OptiFine và Rubidium. "
				+ "Các mod này không tương thích và gây ra lỗi tiêm (injection) khiến trò chơi không thể khởi động."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "Xung đột giữa OptiFine và Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "Gỡ cài đặt OptiFine hoặc Rubidium, vì chúng không tương thích với nhau.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam đang cố gắng tải trên máy chủ chuyên dụng, nhưng chỉ tương thích với phía client. "
				+ "Hãy xóa FreeCam khỏi máy chủ hoặc đảm bảo nó chỉ được cài đặt ở phía client." + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam trên máy chủ chuyên dụng";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "Xóa FreeCam khỏi máy chủ chuyên dụng, vì nó chỉ nên được cài đặt ở phía client.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) đang cố gắng tải trên máy chủ chuyên dụng, nhưng chỉ tương thích với phía client. "
				+ "Hãy xóa ETF khỏi máy chủ hoặc đảm bảo nó chỉ được cài đặt ở phía client." + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features trên máy chủ chuyên dụng";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "Xóa Entity Texture Features khỏi máy chủ chuyên dụng, vì nó chỉ nên được cài đặt ở phía client.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Bạn phải chấp nhận EULA của Minecraft để chạy máy chủ. "
				+ "Hãy chỉnh sửa tệp eula.txt và đổi 'eula=false' thành 'eula=true'." + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "Chưa chấp nhận EULA của Minecraft";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "Chỉnh sửa tệp eula.txt trong thư mục máy chủ và đổi 'eula=false' thành 'eula=true'.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine đang cố gắng tải trên máy chủ chuyên dụng, nhưng chỉ tương thích với phía client. "
				+ "Hãy xóa OptiFine khỏi máy chủ hoặc đảm bảo nó chỉ được cài đặt ở phía client." + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine trên máy chủ chuyên dụng";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "Xóa OptiFine khỏi máy chủ chuyên dụng, vì nó chỉ nên được cài đặt ở phía client. Vấn đề này cũng thường do cài đặt phiên bản OptiFine không đúng với phiên bản Minecraft hoặc do xung đột với mod khác.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks được đánh dấu sai cho phiên bản 1.20.1 nhưng lại sử dụng các phương thức của 1.21.1. "
				+ "Mod đang cố sử dụng ResourceLocation.fromNamespaceAndPath, phương thức này không tồn tại trong 1.20.1."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "Lỗi de phiên bản en Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "Hãy đảm bảo bạn đang sử dụng đúng phiên bản Iron's Spellbooks tương thích với phiên bản Minecraft của mình.";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột nghiêm trọng giữa OptiFine và Embeddium. "
				+ "Các mod này không tương thích và gây ra lỗi tiêm (injection) khiến trò chơi không thể khởi động."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "Xung đột giữa OptiFine và Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "Gỡ cài đặt OptiFine hoặc Embeddium, vì chúng không tương thích với nhau.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Điều này thường xảy ra với các mod tạo thế giới bị xung đột, đặc biệt là Terralinth, AmplifiedNether, Nullscape, Incendium và các mod tạo thế giới khác. Cũng có thể bạn cần cài đặt một mod còn thiếu.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable đang cố gắng tải trên máy chủ chuyên dụng, nhưng chỉ tương thích với phía client. "
				+ "Hãy xóa Controllable khỏi máy chủ hoặc đảm bảo nó chỉ được cài đặt ở phía client." + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable trên máy chủ chuyên dụng";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "Xóa Controllable khỏi máy chủ chuyên dụng, vì nó chỉ nên được cài đặt ở phía client.";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries đang gây ra lỗi ngăn máy chủ tải. "
				+ "Mod này gặp sự cố với nhật ký hành vi lửa, gây ra lỗi trong quá trình tải datapack." + "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries ngăn máy chủ tải";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "Hãy thử cập nhật Supplementaries lên phiên bản mới nhất hoặc tạm thời vô hiệu hóa nó để cho phép máy chủ tải.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) đã gặp sự cố với các mô-đun Jackson bị thiếu. "
				+ "Một số mod như Valkyrien Skies có thể gây ra lỗi này do không bao gồm đầy đủ các phần phụ thuộc cần thiết."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "Thiếu mô-đun Jackson trong Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "Hãy xóa Groovy Modloader và các mod liên quan như Valkyrien Skies có thể gây ra xung đột phụ thuộc.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Every Compat đã phát hiện một tên khối gỗ không hợp lệ. "
				+ "Every Compat thường gặp rất nhiều vấn đề. Đừng dùng nó!" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "Tên không hợp lệ trong Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "Hãy kiểm tra các gói tài nguyên hoặc mod sử dụng Every Compat, vì chúng có thể có tên khối không hợp lệ.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện mã lỗi (-1073741819), có thể do các lớp phủ như GameCaster của Razer, Discord, OBS Studio hoặc do sự cố với driver NVIDIA gây ra."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "Mã lỗi -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "Hãy thử tắt các lớp phủ như GameCaster, Discord hoặc OBS Studio, và kiểm tra xem driver NVIDIA của bạn đã được cập nhật chưa.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips yêu cầu Immersive Messages làm phần phụ thuộc nhưng hiện chưa được cài đặt. "
				+ "Hãy cài đặt Immersive Messages để Immersive Tooltips hoạt động chính xác." + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips thiếu phần phụ thuộc";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "Hãy cài đặt Immersive Messages, vì đây là phần phụ thuộc cần thiết cho Immersive Tooltips.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins gặp sự cố tương thích với Apoli Mod khi ItemStack không thể được ép kiểu thành EntityLinkedItemStack. "
				+ "Điều này thường xảy ra ở các phiên bản cao hơn 6.6.0. Hãy cân nhắc dùng phiên bản cũ hơn hoặc chuyển đổi giữa các phiên bản Fabric và Forge."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "Lỗi ép kiểu trong Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "Hãy dùng Medieval Origins phiên bản 6.6.0 hoặc cũ hơn, hoặc thử chuyển đổi giữa các phiên bản Fabric và Forge của mod.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether đang gây ra lỗi với một Registry Object không có trong MusicManager. "
				+ "Sự cố này liên quan đến mixin MusicManager của Reign of Nether." + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "Lỗi MusicManager trong Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "Hãy thử cập nhật Reign of Nether hoặc tạm thời xóa nó để giải quyết lỗi.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel chỉ hỗ trợ máy chủ YSM trên Linux hoặc Android. "
				+ "Vấn đề này đã được sửa trong các phiên bản mới hơn từ ngày 23 tháng 11 năm 2025 trên Modrinth."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel không tương thích với Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "Hãy cập nhật YesSteveModel lên phiên bản mới hơn từ Modrinth, vì sự cố này đã được sửa sau ngày 23 tháng 11.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột nghiêm trọng giữa Moving Elevators và OptiFine. "
				+ "Các mod này không tương thích và gây ra lỗi tiêm (injection) khiến trò chơi không thể khởi động."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "Xung đột giữa Moving Elevators và OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "Gỡ cài đặt OptiFine hoặc Moving Elevators, vì chúng không tương thích với nhau.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột nghiêm trọng giữa Fabric API (fabric-resource-loader-v0) và OptiFine. "
				+ "Các mod này không tương thích và gây ra lỗi tiêm (injection) khiến trò chơi không thể khởi động."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "Xung đột giữa Fabric API và OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "Gỡ cài đặt OptiFine hoặc cập nhật Fabric API lên phiên bản tương thích.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Một mod có ITransformationService bị lỗi và không thể được khởi tạo: " + claseProveedor + ". "
				+ "Mod này phải được gỡ bỏ để trò chơi có thể tải." + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "ITransformationService bị lỗi";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "Gỡ mod chứa lớp " + claseProveedor + ", vì nó có ITransformationService bị lỗi.";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError() + "'>Một mod có đặc tả phiên bản không hợp lệ. "
				+ "Phiên bản phải được đặt trong dấu ngoặc vuông. "
				+ "Bạn có thể dùng tiện ích grep/greprf ở bảng bên cạnh để tìm phiên bản </span>" + version
				+ "<span style='color:#" + config.obtenerColorError()
				+ "'> nhằm xác định mod nào đang gặp sự cố.</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "Phiên bản không hợp lệ trong mod";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "Dùng tiện ích grep/greprf ở bảng bên cạnh để tìm phiên bản có vấn đề và xác định mod chứa nó.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi stack smashing khiến tiến trình bị chấm dứt. "
				+ "Điều này có thể do sự cố với Early Window trong Forge/NeoForge/PillowMC hoặc với LWJGL 3.2.2 và các phiên bản mới hơn."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "Đã phát hiện Stack Smashing";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "Kiểm tra cài đặt Early Window và cân nhắc dùng phiên bản LWJGL khác hoặc xem lại các mod liên quan đến cửa sổ khởi động sớm.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore chỉ dành cho một modpack cụ thể và không nên dùng trong các bản cài đặt thông thường vì nó gây ra sự cố."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore với phiên bản Java không tương thích";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "Gỡ GregTechEasyCore, vì nó chỉ dành cho một modpack cụ thể và không tương thích với bản cài đặt thông thường của bạn.";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột giữa MoniLabs và Connector Extras liên quan đến các chỉnh sửa của KubeJS. "
				+ "Các mod này không tương thích trong phần chỉnh sửa KubeJS của chúng." + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "Xung đột giữa MoniLabs và Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "Hãy thử gỡ một trong hai mod (MoniLabs hoặc Connector Extras), vì chúng có xung đột với các chỉnh sửa KubeJS.";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris yêu cầu Distant Horizons [2.0.4] hoặc DH API phiên bản [1.1.0] trở lên. "
				+ "Hãy tham khảo hướng dẫn tương thích tại https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e để khắc phục sự cố."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Tương thích giữa Iris và Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "Hãy tham khảo hướng dẫn tương thích tại https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e và cập nhật Iris cùng Distant Horizons lên các phiên bản tương thích.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang thiếu các lớp của Minecraft. Các lý do có thể là:</b>" + "<ul>"
				+ "<li>Bạn có mod dành cho các phiên bản khác của trò chơi. Bạn có thể dùng <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> để kiểm tra xem lớp đó có tồn tại trong phiên bản của bạn không.</li>"
				+ "<li>Bạn có bản cài đặt Minecraft bị lỗi (thường gặp với CurseForge App, ModrinthApp/Theseus/Astralrinth và các launcher modpack khác). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Xem hướng dẫn</a> để khắc phục sự cố với CurseForge.</li>"
				+ "<li>Bạn có một coremod bị lỗi (nếu coremod lỗi, nó có thể chặn việc tải lớp).</li>" + "</ul>"
				+ "<p>Lưu ý: Bạn có thể dùng công cụ <b>grepr/fgrepr</b> ở thanh bên để tìm các mod tham chiếu đến những lớp bị thiếu, miễn là bạn dùng '/' trong tên.</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang thiếu các lớp của DangerZone. Các lý do có thể là:</b>" + "<ul>"
				+ "<li>Bạn có mod dành cho các phiên bản khác của trò chơi.</li>" + "<li>Bạn có coremod bị lỗi.</li>"
				+ "<li>Bạn có launcher hoặc bản cài đặt bị lỗi.</li>" + "</ul>"
				+ "<p>Lưu ý: Bạn có thể dùng công cụ <b>grepr/fgrepr</b> ở thanh bên để tìm các mod tham chiếu đến những lớp bị thiếu, miễn là bạn dùng '/' trong tên.</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang thiếu các lớp của FeatureCreep. Các lý do có thể là:</b>" + "<ul>"
				+ "<li>Bạn có mod dành cho các phiên bản khác của FeatureCreep (ví dụ: ESR so với Nightly hoặc v4 so với v12).</li>"
				+ "<li>Bạn có thể cài đặt FeatureCreep từ CurseForge hoặc MinecraftStorage.</li>" + "</ul>"
				+ "<p>Lưu ý: Bạn có thể dùng công cụ <b>grepr/fgrepr</b> ở thanh bên để tìm các mod tham chiếu đến những lớp bị thiếu, miễn là bạn dùng '/' trong tên.</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang thiếu các lớp của ModLauncher. Các lý do có thể là:</b>" + "<ul>"
				+ "<li>Mod của bạn dành cho một bản build khác của MinecraftForge, PillowMC hoặc NeoForge (ModLauncher được dùng với các loader này).</li>"
				+ "<li>Có rất nhiều bản cập nhật modloader cho một phiên bản Minecraft.</li>"
				+ "<li>Bạn có bản cài đặt launcher bị lỗi (thường gặp với CurseForge App, ModrinthApp/Theseus/Astralrinth và các launcher modpack khác). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Xem hướng dẫn</a> để khắc phục sự cố với CurseForge.</li>"
				+ "</ul>"
				+ "<p>Lưu ý: Bạn có thể dùng công cụ <b>grepr/fgrepr</b> ở thanh bên để tìm các mod tham chiếu đến những lớp bị thiếu, miễn là bạn dùng '/' trong tên.</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang thiếu các lớp của Minecraft Forge. Các lý do có thể là:</b>" + "<ul>"
				+ "<li>Mod của bạn dành cho một bản build khác của MinecraftForge.</li>"
				+ "<li>Có rất nhiều bản cập nhật modloader cho một phiên bản Minecraft.</li>"
				+ "<li>Bạn có bản cài đặt bị lỗi (thường gặp với CurseForge App, ModrinthApp/Theseus/Astralrinth và các launcher modpack khác). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Xem hướng dẫn</a> để khắc phục sự cố với CurseForge.</li>"
				+ "</ul>"
				+ "<p>Lưu ý: Bạn có thể dùng công cụ <b>grepr/fgrepr</b> ở thanh bên để tìm các mod tham chiếu đến những lớp bị thiếu, miễn là bạn dùng '/' trong tên.</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang thiếu các lớp của NeoForge. Các lý do có thể là:</b>" + "<ul>"
				+ "<li>Mod của bạn dành cho một bản build khác của NeoForge.</li>"
				+ "<li>Có rất nhiều bản cập nhật modloader cho một phiên bản Minecraft.</li>"
				+ "<li>Bạn có bản cài đặt bị lỗi (thường gặp với CurseForge App, ModrinthApp/Theseus/Astralrinth và các launcher modpack khác). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Xem hướng dẫn</a> để khắc phục sự cố với CurseForge.</li>"
				+ "</ul>"
				+ "<p>Lưu ý: Bạn có thể dùng công cụ <b>grepr/fgrepr</b> ở thanh bên để tìm các mod tham chiếu đến những lớp bị thiếu, miễn là bạn dùng '/' trong tên.</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang thiếu các lớp của Fabric Loader. Các lý do có thể là:</b>" + "<ul>"
				+ "<li>Mod của bạn dành cho một bản build khác của Fabric Loader.</li>"
				+ "<li>Có rất nhiều bản cập nhật modloader cho một phiên bản Minecraft.</li>"
				+ "<li>Bạn có bản cài đặt bị lỗi (thường gặp với CurseForge App, ModrinthApp/Theseus/Astralrinth và các launcher modpack khác). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Xem hướng dẫn</a> để khắc phục sự cố với CurseForge.</li>"
				+ "<li>Nhiều mod yêu cầu Fabric API. Vui lòng cài đặt Fabric API nếu cần.</li>" + "</ul>"
				+ "<p>Lưu ý: Bạn có thể dùng công cụ <b>grepr/fgrepr</b> ở thanh bên để tìm các mod tham chiếu đến những lớp bị thiếu, miễn là bạn dùng '/' trong tên.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Bạn đang thiếu các lớp của PillowMC. Các lý do có thể là:</b>" + "<ul>"
				+ "<li>Mod của bạn dành cho một bản build khác của PillowMC.</li>"
				+ "<li>Có rất nhiều bản cập nhật modloader cho một phiên bản Minecraft.</li>"
				+ "<li>Bạn có bản cài đặt bị lỗi (thường gặp với CurseForge App, ModrinthApp/Theseus/Astralrinth và các launcher modpack khác). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Xem hướng dẫn</a> để khắc phục sự cố với CurseForge.</li>"
				+ "</ul>"
				+ "<p>Lưu ý: Bạn có thể dùng công cụ <b>grepr/fgrepr</b> ở thanh bên để tìm các mod tham chiếu đến những lớp bị thiếu, miễn là bạn dùng '/' trong tên.</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Bạn có một mod cố ý gây lag. Uranium là một mod gây lag. Nó không phải lúc nào cũng gây lỗi, nhưng sớm muộn gì cũng có thể gây ra."
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack được đánh dấu là tương thích với 1.19.* nhưng thực ra dành cho 1.20.*, điều này gây ra lỗi không tìm thấy lớp. "
				+ "Mod này cố sử dụng DamageSources không tồn tại trong phiên bản Minecraft hiện tại." + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Lỗi phiên bản trong Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "Hãy đảm bảo bạn đang sử dụng đúng phiên bản Falling Attack tương thích với phiên bản Minecraft của mình.";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>").append("Bạn cần cài đặt CFR (Class File Reader) để sử dụng chức năng này.<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append(
					"Trên hệ thống Linux, NetBSD hoặc FreeBSD, bạn có thể cài đặt CFR từ trình quản lý gói của mình.<br>")
					.append("Tìm gói tại: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append("Ngoài ra, bạn có thể tải xuống phiên bản đã chỉnh sửa được FabricMC sử dụng từ:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("Hãy lưu nó vào thư mục sau:<br>").append("<b>")
				.append(new java.io.File(com.asbestosstar.crashdetector.Statics.carpeta_mundial_como_archivo, "cfr/")
						.getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>Quan trọng:</b> sau khi cài đặt CFR, bạn phải khởi động lại mod để nó nhận diện chính xác.")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "Không có chân dung";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "Không thể tìm thấy lớp: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "Bộ dịch ngược CFR – Sakura Riddle (Không chính thức)";
	}

	@Override
	public String cfrClaseActual() {
		return "Lớp hiện tại";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "Chân dung của Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "Lỗi khi tải chân dung";
	}

	public String noticiaLegalCFR() {
		return "Chương trình giao diện đồ họa (GUI) này để dịch ngược mod được thiết kế nhằm giúp người dùng xác định nguyên nhân gây lỗi trong phần mềm. "
				+ "Tuy nhiên, việc dịch ngược mod có thể là cần thiết, và người dùng phải cẩn thận không sử dụng mã được tạo ra để vi phạm Luật Bản quyền Liên bang. "
				+ "Bạn nên xem lại giấy phép của mod tương ứng trước khi sử dụng bất kỳ mã nào thu được. Ngoài ra, các mod thường cung cấp mã nguồn chính thức, "
				+ "và mã đó nhìn chung sạch hơn, dễ hiểu hơn mã đã dịch ngược. Hãy nhớ rằng việc tôn trọng quyền sở hữu trí tuệ và giấy phép sử dụng là điều rất quan trọng đối với "
				+ "cộng đồng phát triển mod. Bạn có thể xem Luật Bản quyền Liên bang tại liên kết sau: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Luật Bản quyền Liên bang (Tiếng Tây Ban Nha)</a> "
				+ "và phiên bản tiếng Anh tại đây: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (English)</a>. "
				+ "Vì chúng tôi đang ở trên CurseForge, chúng tôi cũng cung cấp liên kết đến Luật Bản quyền Hoa Kỳ bằng tiếng Anh: "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>. "
				+ "Ngoài ra, người dùng cũng nên tìm hiểu mình đang ở đâu và nắm rõ các luật áp dụng tại nơi mình sinh sống. "
				+ "GUI của chúng tôi chỉ dành cho các kiểm tra đơn giản; để phân tích nâng cao hơn, bạn nên dùng Fork Enigma của FabricMC tại "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>. Nếu bạn muốn chỉnh sửa các tệp JAR để tạo bản vá khi không có mã nguồn, bạn có thể sử dụng Recaf tại "
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">trang web của họ</a>.";
	}

	@Override
	public String botonDescargarCfr() {
		return "Tải xuống CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "Mở thư mục cài đặt";
	}

	@Override
	public String colorFondoPrincipal() {
		return "Màu nền chính";
	}

	@Override
	public String colorTextoBotonReset() {
		return "Màu chữ của nút đặt lại";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "Màu chữ của ô tìm kiếm";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "Màu chữ của menu thả xuống bộ lọc";
	}

	@Override
	public String colorTextoRenderer() {
		return "Màu chữ của trình hiển thị";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "Màu chữ của lớp phủ tải";
	}

	@Override
	public String colorBorde() {
		return "Màu viền";
	}

	@Override
	public String colorFondoRetrato() {
		return "Màu nền ở chế độ chân dung";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "Màu của liên kết chia sẻ";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "Màu nền của ô chia sẻ";
	}

	@Override
	public String rosaFondo() {
		return "Hồng nền";
	}

	@Override
	public String rosaSuave() {
		return "Hồng nhạt";
	}

	@Override
	public String moradoAcento() {
		return "Tím nhấn";
	}

	@Override
	public String textoOscuro() {
		return "Chữ tối màu";
	}

	@Override
	public String bordeSuave() {
		return "Viền mềm";
	}

	@Override
	public String fondoCampo() {
		return "Nền của ô";
	}

	@Override
	public String fondoVistaPrevia() {
		return "Nền xem trước";
	}

	@Override
	public String sintaxisConstructor() {
		return "Màu cú pháp: constructor";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "Màu cú pháp: thông báo trợ giúp";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "Màu cú pháp: thẻ HTML";
	}

	@Override
	public String colorFondoVentana() {
		return "Màu nền của cửa sổ";
	}

	@Override
	public String colorPanel() {
		return "Màu của panel";
	}

	@Override
	public String colorBotonTexto() {
		return "Màu chữ của nút";
	}

	@Override
	public String colorCampo() {
		return "Màu của ô";
	}

	@Override
	public String colorBordeDestacado() {
		return "Màu viền nổi bật";
	}

	@Override
	public String colorSeleccionTexto() {
		return "Màu nền khi chọn văn bản";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "Màu chữ được chọn";
	}

	@Override
	public String colorEstadoExito() {
		return "Màu trạng thái: thành công";
	}

	@Override
	public String colorEstadoFallo() {
		return "Màu trạng thái: lỗi";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "Màu trạng thái: ảnh chụp nhanh";
	}

	@Override
	public String colorResultadoAnadido() {
		return "Màu của kết quả được thêm";
	}

	@Override
	public String colorResultadoEliminado() {
		return "Màu của kết quả bị xóa";
	}

	@Override
	public String colorBordeScroll() {
		return "Màu viền của thanh cuộn";
	}

	@Override
	public String colorFondoPanel() {
		return "Màu nền của panel";
	}

	@Override
	public String colorBeigeListas() {
		return "Màu be của danh sách";
	}

	@Override
	public String colorTextoListas() {
		return "Màu chữ trong danh sách";
	}

	@Override
	public String colorBordeListas() {
		return "Màu viền của danh sách";
	}

	@Override
	public String colorBotonFondo() {
		return "Màu nền của nút";
	}

	@Override
	public String colorBordeBoton() {
		return "Màu viền của nút";
	}

	@Override
	public String colorDoradoTexto() {
		return "Màu vàng của chữ";
	}

	@Override
	public String colorPila() {
		return "Màu của ngăn xếp (stack trace)";
	}

	@Override
	public String colorTextoPanel() {
		return "Màu chữ của panel";
	}

	@Override
	public String colorTextoNegro() {
		return "Màu chữ đen";
	}

	@Override
	public String colorTextoPrincipal() {
		return "Màu chữ chính";
	}

	@Override
	public String colorFondoResultados() {
		return "Màu nền của kết quả";
	}

	@Override
	public String colorEstado() {
		return "Màu trạng thái";
	}

	@Override
	public String colorTextoDescripcion() {
		return "Màu chữ mô tả";
	}

	@Override
	public String colorTextoEstado() {
		return "Màu chữ trạng thái";
	}

	@Override
	public String colorTextoExtra() {
		return "Màu chữ bổ sung";
	}

	@Override
	public String colorSeparador() {
		return "Màu của đường phân cách";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "Đã phát hiện lỗi gốc <code>StubRoutines::SafeFetch32</code>. "
				+ "Sự cố này xảy ra trên macOS với JDK 17.0.9 và đã được sửa trong JDK 17.0.10 hoặc các phiên bản mới hơn. https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "Lỗi gốc SafeFetch32 trong JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "Cập nhật JDK của bạn lên phiên bản 17.0.10 hoặc cao hơn (ví dụ: 17.0.15).";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "Nếu bạn đang dùng launcher như MultiMC, Prism Launcher hoặc TLauncher, hãy cấu hình nó để dùng JDK mới hơn. "
				+ "Một số launcher đã tích hợp sẵn JDK 17.0.15.";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "Mod Spark cũng có thể góp phần gây ra lỗi này. Hãy cân nhắc tạm thời vô hiệu hóa nó. https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mod MCEF (Chromium Embedded Framework) đang gây ra hiện tượng treo im lặng.</b>" + "<ul>"
				+ "<li>MCEF đang được khởi tạo ở cuối nhật ký, điều này thường có nghĩa là trò chơi đã bị treo trong quá trình tải.</li>"
				+ "<li>Mod này được biết là thường gây lỗi trên Linux, macOS hoặc với một số phiên bản Java nhất định.</li>"
				+ "<li>Không phải lúc nào cũng xuất hiện lỗi rõ ràng, nhưng trò chơi sẽ không bao giờ vào được menu chính.</li>"
				+ "</ul>"
				+ "<p>Nếu bạn không cần chức năng trình duyệt trong trò chơi (như bản đồ web hoặc trang tích hợp), hãy gỡ mod này.</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "Sự cố khởi tạo MCEF (mod trình duyệt nhúng)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "Gỡ tệp mod MCEF (tìm 'mcef' trong tên tệp) khỏi thư mục 'mods'.";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "Nếu bạn thực sự cần nó, hãy đảm bảo đang dùng phiên bản tương thích với hệ điều hành và phiên bản Minecraft của bạn.";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột giữa <b>OptiFine</b> và <b>Iris/Oculus</b>.</b>" + "<ul>"
				+ "<li>OptiFine thay đổi hệ thống render của Minecraft theo cách không tương thích với Iris hoặc Oculus.</li>"
				+ "<li>Lỗi <code>MixinLevelRenderer failed injection check</code> đến từ <code>mixins.iris.json</code> hoặc <code>mixins.oculus.json</code>.</li>"
				+ "</ul>"
				+ "<p>Các mod này không thể dùng cùng nhau. Hãy gỡ OptiFine để sử dụng shader với Iris hoặc Oculus.</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "Xung đột giữa OptiFine và Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "Gỡ tệp OptiFine khỏi thư mục 'mods'.";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "Sử dụng Iris hoặc Oculus mà không có OptiFine để dùng shader hiện đại.";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột giữa <b>ModernFix</b> và <b>OptiFine</b>.</b>" + "<ul>"
				+ "<li>ModernFix không tương thích với OptiFine vì nó phá vỡ chức năng của Forge và làm chậm quá trình khởi động.</li>"
				+ "<li>Bản thân ModernFix cũng cảnh báo: <i>\"Use of ModernFix with OptiFine is not supported\"</i>.</li>"
				+ "</ul>" + "<p>Bạn phải gỡ một trong hai mod để trò chơi hoạt động bình thường.</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "Xung đột giữa ModernFix và OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "Gỡ OptiFine hoặc ModernFix khỏi thư mục 'mods'. Không thể dùng cùng nhau.";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "Nếu bạn cần tối ưu hóa, hãy cân nhắc chỉ dùng OptiFine hoặc thay ModernFix bằng các mod nhẹ hơn như FerriteCore hoặc EntityCulling.";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Lỗi: khóa registry không hợp lệ với ký tự không được phép.</b>" + "<ul>"
				+ "<li><b>Khóa phát hiện:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>Trong Minecraft, tất cả khóa registry (tag, công thức, thành tựu, v.v.) phải viết <b>chữ thường</b> và chỉ dùng chữ cái, số, dấu gạch dưới, dấu gạch ngang và dấu gạch chéo.</li>"
				+ "<li>Lỗi này thường do mod viết sai hoặc datapack bị lỗi.</li>" + "</ul>"
				+ "<p><b>Mẹo quan trọng:</b> Dùng công cụ <b>grepr</b> hoặc <b>fgrepr</b> ở thanh bên và bật tùy chọn <b>\"Tìm kiếm trong tệp JAR\"</b> để tìm mod chứa khóa sai này.</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "Khóa registry có chữ hoa hoặc ký tự không hợp lệ";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "Dùng 'grepr' hoặc 'fgrepr' với \"Tìm kiếm trong tệp JAR\" để xác định mod gây lỗi.";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "Nếu không xác định được mod, hãy gỡ các mod mới cài, đặc biệt là mod thêm block, item hoặc công cụ.";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Lỗi khi tải mod <b>" + escapeHtml(modNombre)
				+ "</b>.</b>" + "<ul>" + "<li>Mod không thể khởi tạo một thành phần (ví dụ: menu cấu hình).</li>"
				+ "<li>Điều này thường xảy ra do không tương thích với phiên bản Minecraft, Fabric hoặc mod khác.</li>"
				+ "</ul>" + "<p>Nếu lỗi tiếp tục, hãy gỡ hoặc cập nhật mod <b>" + escapeHtml(modNombre) + "</b>.</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "Lỗi khởi tạo mod (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "Gỡ mod '" + modNombre + "' khỏi thư mục 'mods'.";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "Cập nhật mod '" + modNombre + "' lên phiên bản tương thích.";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi liên quan đến mod <b>En Garde!</b>.</b>" + "<ul>"
				+ "<li>Mod này thêm cơ chế chiến đấu cận chiến (parry, block, v.v.).</li>"
				+ "<li>Lỗi thường xảy ra do xung đột với các mod chiến đấu khác (Epic Fight, DualRiders, v.v.) hoặc do dùng sai phiên bản Minecraft.</li>"
				+ "</ul>" + "<p>Nếu bạn không cần chiến đấu nâng cao, hãy gỡ En Garde! để tránh xung đột.</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "Lỗi trong mod En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "Đảm bảo bạn đang dùng phiên bản En Garde! tương thích với Minecraft và mod loader (Fabric/Forge).";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "Nếu bạn dùng các mod chiến đấu khác (Epic Fight, Caelus, v.v.), hãy tắt chúng hoặc gỡ En Garde! để tránh xung đột.";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi do mod <b>IdleTweaks</b>.</b>" + "<ul>"
				+ "<li>IdleTweaks cố giải phóng một kênh mạng không tồn tại (<code>Tried to release unknown channel</code>).</li>"
				+ "<li>Lỗi này thường xảy ra ở phiên bản cũ hoặc khi dùng trên máy chủ cấu hình sai.</li>" + "</ul>"
				+ "<p>IdleTweaks là mod tiện ích nhưng có thể gây mất ổn định. Hãy cân nhắc cập nhật hoặc gỡ nó.</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "Lỗi IdleTweaks (kênh mạng không xác định)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "Cập nhật IdleTweaks lên phiên bản mới nhất tương thích với Minecraft.";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "Gỡ IdleTweaks khỏi thư mục 'mods' nếu không cần thiết.";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi xác thực (HTTP 401) khi đăng nhập Minecraft.</b>"
				+ "<p>Lỗi này <b>hiếm khi là nguyên nhân trực tiếp gây crash</b>, nhưng cho thấy bạn đang dùng tài khoản chưa xác thực (bản crack).</p>"
				+ "<p>Các kênh hỗ trợ chính thức (dự án, nhà phát triển modpack, v.v.) <b>không thể hỗ trợ</b> nếu bạn dùng bản crack, "
				+ "do quy định và chính sách của họ.</p>"
				+ "<p>Kiểm tra này có thể <b>tắt trong cấu hình</b> của hệ thống phát hiện. "
				+ "Cảnh báo: hệ thống phát hiện không hoàn hảo và có thể kích hoạt sai trong môi trường phát triển, mạng không ổn định hoặc launcher tùy chỉnh.</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>Quyền Miranda nếu bạn vẫn cố gắng liên hệ hỗ trợ:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Minecraft bản crack";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "Tắt kiểm tra chống vi phạm bản quyền";
	}

	@Override
	public String comprarMC() {
		return "Mua Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Bạn đang sử dụng launcher <code>" + id
				+ "</code>, không nằm trong danh sách launcher được khuyến nghị.</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>Mặc dù có thể hoạt động, các launcher không được khuyến nghị thường gây ra:</p>" + "<ul>"
				+ "<li>Cài đặt mod hoặc ứng dụng bị lỗi.</li>"
				+ "<li>Game không khởi động hoặc bị treo mà không có lỗi rõ ràng.</li>"
				+ "<li>Cấu trúc thư mục bất thường (khó chẩn đoán).</li>"
				+ "<li>Hành vi không ổn định với Java, bộ nhớ hoặc mod.</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "Để có trải nghiệm tốt hơn, hãy sử dụng một trong các launcher được khuyến nghị:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Launcher không được khuyến nghị";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "Chuyển sang launcher trong danh sách khuyến nghị.";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Bạn đang sử dụng <b>launcher không được khuyến nghị</b>: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>Các launcher không được khuyến nghị có thể gây ra:</p>" + "<ul>"
				+ "<li>Cài đặt ứng dụng hoặc mod bị lỗi.</li>" + "<li>Game không khởi động hoặc bị lỗi âm thầm.</li>"
				+ "<li>Cấu trúc tệp bất thường (khó debug).</li>"
				+ "<li>Không rõ cách quản lý mod, Java hoặc bộ nhớ.</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "Rất khuyến khích sử dụng một trong các launcher sau:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Launcher không được khuyến nghị";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "Chuyển sang launcher được khuyến nghị để nhận hỗ trợ.";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Thiếu các mod được khuyến nghị cho môi trường này.</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "Thiếu mod được khuyến nghị";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "Cài đặt các mod được khuyến nghị để có trải nghiệm tối ưu.";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Phát hiện mod không được khuyến nghị trong cài đặt của bạn.</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "Phát hiện mod không được khuyến nghị";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "Gỡ các mod không được khuyến nghị để tránh lỗi.";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Phát hiện can thiệp trái phép vào các tệp quan trọng. Bạn đã chỉnh sửa tệp hoặc đang sử dụng launcher không đáng tin cậy.</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "Phát hiện can thiệp";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "Cài đặt lại các tệp gốc để khôi phục tính toàn vẹn.";
	}

	@Override
	public String configuracionCorporativa() {
		return "Cấu hình doanh nghiệp";
	}

	@Override
	public String idiomaRespaldo() {
		return "Ngôn ngữ dự phòng";
	}

	@Override
	public String buscardorHabilitado() {
		return "Bật công cụ tìm kiếm";
	}

	@Override
	public String nombreHerramienta() {
		return "Tên công cụ";
	}

	@Override
	public String condenarPirateria() {
		return "Lên án vi phạm bản quyền";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "Launcher được khuyến nghị";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "Launcher không được khuyến nghị";
	}

	@Override
	public String modsRecomendados() {
		return "Mod được khuyến nghị";
	}

	@Override
	public String modsDesaconsejados() {
		return "Mod không được khuyến nghị";
	}

	@Override
	public String antiTamper() {
		return "Chống can thiệp";
	}

	@Override
	public String proximamente() {
		return "Sắp ra mắt";
	}

	@Override
	public String informacion() {
		return "Thông tin";
	}

	@Override
	public String errorCargandoImagen() {
		return "Lỗi khi tải hình ảnh";
	}

	@Override
	public String configuracionBasica() {
		return "Cấu hình cơ bản";
	}

	@Override
	public String funcionalidades() {
		return "Tính năng";
	}

	@Override
	public String derechosMiranda() {
		return "Quyền Miranda (rất khuyến nghị)";
	}

	@Override
	public String gestionVerificaciones() {
		return "Quản lý kiểm tra";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "Tên";
	}

	@Override
	public String codigoVerificacion() {
		return "Mã";
	}

	@Override
	public String documentacionVerificacion() {
		return "Tài liệu";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "Kiểm tra đã bật:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "Kiểm tra đã tắt:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "Tắt tất cả kiểm tra không dành cho doanh nghiệp";
	}

	@Override
	public String verCodigo() {
		return "Xem mã";
	}

	@Override
	public String verDocumentacion() {
		return "Xem tài liệu";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "Chọn một kiểm tra để tắt.";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "Chọn một kiểm tra để bật.";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "Đã tắt %d kiểm tra không được khuyến nghị cho môi trường doanh nghiệp.";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "Không có kiểm tra không dành cho doanh nghiệp để tắt.";
	}

	@Override
	public String operacionCompletada() {
		return "Thao tác hoàn tất";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "Chúng tôi nhớ bạn, Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "Màu của kiểm tra doanh nghiệp";
	}

	// Métodos para la gestión de lanzadores
	@Override
	public String nombreLanzador() {
		return "Tên launcher";
	}

	@Override
	public String motivo() {
		return "Lý do";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Launcher không được khuyến nghị";
	}

	@Override
	public String moverADesaconsejados() {
		return "Chuyển sang không khuyến nghị";
	}

	@Override
	public String moverARecomendados() {
		return "Chuyển sang khuyến nghị";
	}

	@Override
	public String guardarCambios() {
		return "Lưu thay đổi";
	}

	@Override
	public String cancelar() {
		return "Hủy";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "Vui lòng chọn một launcher để di chuyển.";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "Các thay đổi đã được lưu thành công!";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEs(String nombreLanzador) {
		return "Launcher này không được khuyến nghị do các vấn đề bảo mật và ổn định đã biết.";
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
		return "Lý do";
	}

	@Override
	public String agregarLanzador() {
		return "Thêm launcher";
	}

	@Override
	public String quitarLanzador() {
		return "Xóa launcher";
	}

	@Override
	public String editarRazones() {
		return "Chỉnh sửa lý do";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "Chọn một launcher để xóa.";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "Chọn một launcher để chỉnh sửa.";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "Chỉnh sửa lý do cho " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "Thêm ngôn ngữ mới";
	}

	@Override
	public String aceptar() {
		return "Chấp nhận";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "Chọn ngôn ngữ";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "Đây là các launcher mà " + Statics.nombre_cd.obtener() + " đề xuất là tốt.";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "Kết quả đúng";
	}

	public String modsNoRecomendados() {
		return "Mods không được khuyến nghị";
	}

	public String agregarMod() {
		return "Thêm mod";
	}

	public String quitarMod() {
		return "Gỡ mod";
	}

	public String modId() {
		return "ID mod/Tên JBoss Modules";
	}

	public String rutaMod() {
		return "Đường dẫn / tệp của mod";
	}

	public String errorDebeIndicarMod() {
		return "Bạn phải chỉ định ít nhất modid hoặc đường dẫn của mod.";
	}

	public String modsNoRecomendadosAviso() {
		return "Tại đây bạn có thể đăng ký các mod không được khuyến nghị để " + Statics.nombre_cd.obtener()
				+ " phát hiện nếu chúng được cài đặt.";
	}

	@Override
	public String anularNormal() {
		return "Ghi đè bình thường";
	}

	@Override
	public String anularNormalDescripcion() {
		return Statics.nombre_cd.obtener() + " nên cảnh báo ngay cả khi không bị crash";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "Đăng ký các mod mà " + Statics.nombre_cd.obtener() + " khuyến nghị. Nếu thiếu chúng, "
				+ Statics.nombre_cd.obtener() + " có thể cảnh báo.";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "Nếu bạn quyết định bật cảnh báo chống vi phạm bản quyền, bạn nên xác định ở đây "
				+ "các quyền của người yêu cầu hỗ trợ như một biện pháp phòng ngừa.\n\n"

				+ "Trái với suy nghĩ phổ biến, nhiều cộng đồng và kênh hỗ trợ phổ biến "
				+ "KHÔNG yêu cầu bật cảnh báo chống vi phạm bản quyền để cung cấp trợ giúp. Tuy nhiên, "
				+ "việc ghi lại những quyền này có thể hữu ích trong trường hợp một người vẫn truy cập vào kênh "
				+ "hỗ trợ.\n\n"

				+ "Bạn có thể tham khảo các tài liệu chính thức như Sổ tay Quyền Cơ bản của Người bị Tạm giữ "
				+ "ở Mexico:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "Cũng như các nguyên tắc pháp lý tương tự được sử dụng ở những quốc gia khác, bao gồm "
				+ "Hoa Kỳ, Liên bang Nga, Cộng hòa Nhân dân Trung Hoa, Cộng hòa Hồi giáo "
				+ "Iran và Cộng hòa Dân chủ Nhân dân Triều Tiên.\n\n"

				+ "Một số ví dụ về các quyền có thể được bao gồm là:\n"
				+ "• Quyền không cung cấp thông tin không cần thiết cho việc hỗ trợ, như launcher đang dùng, "
				+ "tên người dùng hoặc UUID.\n" + "• Quyền không tự buộc tội chính mình.\n"
				+ "• Quyền từ chối trả lời các câu hỏi không cần thiết để giải quyết vấn đề.\n"
				+ "• Quyền được nhận hướng dẫn ngay trong cuộc trò chuyện.\n"
				+ "• Quyền sử dụng tính năng ẩn danh nhật ký (logs) được tích hợp trong " + Statics.nombre_cd.obtener()
				+ ".\n\n"

				+ "Văn bản này chấp nhận nội dung HTML.";
	}

	@Override
	public String editar() {
		return "Chỉnh sửa";
	}

	@Override
	public String advertenciaHashLento() {
		return "Cảnh báo: thêm nhiều tệp lớn có thể khiến quá trình kiểm tra " + "mất vài phút. "
				+ Statics.nombre_cd.obtener() + " sẽ phải tính hash của từng tệp "
				+ "trước khi tiếp tục. Chỉ nên bảo vệ những tệp thật sự cần thiết.";
	}

	@Override
	public String agregarArchivo() {
		return "Thêm tệp";
	}

	@Override
	public String agregarCarpeta() {
		return "Thêm thư mục";
	}

	@Override
	public String quitar() {
		return "Gỡ";
	}

	@Override
	public String rutaArchivo() {
		return "Đường dẫn tệp";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "Đường dẫn đã chọn nằm ngoài thư mục hiện tại của trò chơi. "
				+ "Chỉ cho phép các tệp và thư mục nằm trong thư mục hiện tại hoặc các thư mục con của nó.";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>"
				+ "Ý kiến và bình luận của Sylent Bell không nhất thiết trùng với quan điểm của chúng tôi; "
				+ "chúng tôi chỉ nghĩ rằng đặt cô ấy ở đây sẽ buồn cười. " + Statics.nombre_cd.obtener()
				+ " là thế tục." + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mod GML (Groovy ModLoader) yêu cầu các thay đổi này và là nguyên nhân phổ biến nhất của vấn đề này.</b>";
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
				+ "Đã phát hiện việc sử dụng <i>Flywheel độc lập</i>.</b>"
				+ "<p><b>Flywheel độc lập đã lỗi thời (deprecated)</b> và không nên được sử dụng trong các phiên bản hiện đại.</p>"
				+ "<p>Các phiên bản hiện tại của <b>Create</b> <b>đã bao gồm Flywheel</b>, vì vậy việc cài riêng nó "
				+ "sẽ gây ra xung đột tương thích và lỗi tải.</p>"
				+ "<p>Một số mod phụ thuộc rõ ràng vào Flywheel độc lập có thể "
				+ "<b>không hoạt động</b> hoặc <b>hoạt động không ổn định</b>. "
				+ "Trong một số trường hợp nâng cao, các mod này có thể hoạt động nếu "
				+ "<b>chỉnh sửa thủ công tệp <code>mods.toml</code></b> để điều chỉnh phạm vi phiên bản, "
				+ "mặc dù điều này <b>không được khuyến nghị</b>.</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>Các mod được phát hiện có tham chiếu đến Flywheel:</b></p>" + "<ul>"
								+ listaMods.toString() + "</ul>")
				+ "<p>Giải pháp được khuyến nghị là <b>gỡ Flywheel độc lập</b> và chỉ sử dụng "
				+ "phiên bản đi kèm với Create.</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Flywheel độc lập";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi liên quan đến mod <i>Floral Enchantments</i>.</b>"
				+ "<p>Sự cố xảy ra do lỗi nội bộ của mod khi xử lý dữ liệu trò chơi, "
				+ "dẫn đến <b>NullPointerException</b> trong lúc chạy.</p>"
				+ "<p>Vấn đề này thường được giải quyết bằng cách cập nhật mod hoặc gỡ nó đi.</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "Lỗi của Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Bạn đang có phiên bản NeoForge của MixinExtras và cả phiên bản thường. Nếu bạn đang dùng MinecraftForge, bạn có thể cài <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>liên kết này</a> để khắc phục.</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi ở phần bóng đổ địa hình khi dùng shader (Iris).</b>"
				+ "<p>Vấn đề xảy ra trong quá trình render địa hình.</p>"
				+ "<p>Khuyến nghị <b>thử chạy game không dùng shader</b> hoặc giảm chất lượng đồ họa, "
				+ "đặc biệt với các thiết lập <b>Ultra</b>.</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "Bóng đổ địa hình (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện một tick của máy chủ kéo dài bất thường.</b>"
				+ "<p>Điều này cho thấy trò chơi đã bị kẹt quá lâu trong một tick duy nhất.</p>"
				+ "<p>Khuyến nghị <b>xem thread dump</b> được tạo trong log để xác định nguyên nhân.</p>"
				+ "<p><b>Phân tích Stack Trace</b> có thể giúp bạn tìm ra nguồn gốc của việc bị treo.</p>"
				+ "<p>Ngoài ra, nút <b>Xem trong log</b> sẽ tô đỏ các mod có khả năng gây ra sự cố, "
				+ "cũng như các mục được bao quanh bởi <code>$modid$</code>, vốn thường cho biết nguồn gốc của vấn đề. Đối với quét thời gian thực, chúng tôi khuyến nghị dùng CPU sampler trong VisualVM. Hãy đảm bảo máy chủ hoặc máy tính của bạn đủ mạnh để xử lý các mod đang dùng, vì có thể tất cả mod của bạn đều hoạt động đúng, nhưng số lượng mod lại quá nhiều.</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Tick máy chủ kéo dài";
	}

	@Override
	public String tituloLFPDPPP() {
		return "LUẬT LIÊN BANG VỀ BẢO VỆ DỮ LIỆU CÁ NHÂN DO CÁC CÁ NHÂN NẮM GIỮ";
	}

	@Override
	public String aceptarPermanentemente() {
		return "Chấp nhận vĩnh viễn";
	}

	// Métodos para la Acta de Protección del Idioma Cultural de Pyongyang
	public String actaProteccionIdiomaCultural() {
		return "Đạo luật Bảo vệ Ngôn ngữ Văn hóa Bình Nhưỡng";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "Bản dịch tiếng Hàn chứa các thuật ngữ tiếng lóng miền Nam cần tránh để tuân thủ pháp luật. "
				+ "Việc sử dụng ngôn ngữ nước ngoài, đặc biệt từ miền Nam, bị nghiêm cấm "
				+ "theo Đạo luật Bảo vệ Ngôn ngữ Văn hóa Bình Nhưỡng.";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "Để biết thêm thông tin, vui lòng tham khảo tài liệu chính thức của luật: "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>Đạo luật Bảo vệ Ngôn ngữ Văn hóa Bình Nhưỡng</a>";
	}

	public String leerLeyCompleta() {
		return "Đọc toàn bộ luật";
	}

	public String errorAbriendoEnlace() {
		return "Lỗi khi mở liên kết";
	}

	@Override
	public String canarioTitulo() {
		return "Canary lệnh tòa";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — Giám sát theo dõi";
	}

	@Override
	public String revisar() {
		return "Kiểm tra";
	}

	@Override
	public String cerrar() {
		return "Đóng";
	}

	@Override
	public String canarioTodoSeguro() {
		return "Tất cả dịch vụ đều báo trạng thái an toàn.";
	}

	@Override
	public String canarioComprometido(int c) {
		return "CẢNH BÁO: " + c + " dịch vụ báo trạng thái không an toàn.";
	}

	@Override
	public String colorAlerta() {
		return "Màu cảnh báo";
	}

	public String opcionesMunidiales() {
		return "Tùy chọn toàn cục";
	}

	public String consentimientoLFPDPPP() {
		return "Chấp thuận LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "Bật chuyển token truy cập qua Handoff cho ReLauncher (không khuyến nghị).";
	}

	public String consolaDesarrollo() {
		return "Console phát triển";
	}

	public String mundial() {
		return "Toàn cầu";
	}

	public String ningun() {
		return "Không có";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "Console nhà phát triển";
	}

	public String bajar() {
		return "Cuộn xuống";
	}

	public String logsSoporte() {
		return "Logs hỗ trợ";
	}

	public String detenerProceso() {
		return "Dừng tiến trình";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "Sao chép vùng chọn";
	}

	public String seleccionarTodo() {
		return "Chọn tất cả";
	}

	public String copiarTodo() {
		return "Sao chép tất cả";
	}

	public String guardarTodoComoArchivo() {
		return "Lưu tất cả dưới dạng tệp";
	}

	public String obtenerEnlaceSoporte() {
		return "Lấy liên kết hỗ trợ";
	}

	public String borrarTodo() {
		return "Xóa tất cả";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "Màu nền console";
	}

	public String colorTextoConsola() {
		return "Màu chữ console";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "Đã xác nhận chấp thuận.\nTính năng chia sẻ logs sẽ được triển khai tại đây.";
	}

	@Override
	public String usarSakuraOriginal() {
		return "Sử dụng hình Sakura Riddle gốc";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "Một \"warrant canary\" là cơ chế minh bạch.\n\n"
				+ "Khi thông báo này tồn tại và các dịch vụ hiển thị an toàn, "
				+ "điều đó có nghĩa là dự án KHÔNG nhận được lệnh tòa bí mật, "
				+ "yêu cầu kiểm duyệt hoặc yêu cầu giám sát.\n\n"
				+ "Nếu bất kỳ canary nào biến mất hoặc báo không an toàn, "
				+ "điều đó cho thấy đã có thay đổi pháp lý.\n\n"
				+ "Bảng này kiểm tra tất cả canary trong hệ thống và hiển thị " + "trạng thái hiện tại.\n\n"
				+ "Nhấn \"Kiểm tra\" để cập nhật trạng thái.";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "Khôi phục tất cả tùy chọn về mặc định?";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "Không có tùy chọn";
	}

	@Override
	public String seleccionaColor() {
		return "Chọn màu";
	}

	@Override
	public String botonMostrarGUI() {
		return "Hiển thị GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "Lưu tất cả";
	}

	@Override
	public String botonRestablecerTodo() {
		return "Đặt lại tất cả";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms chưa được tải";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi khi truy cập API LuckPerms.</b>"
				+ "<p>Thông báo cho biết <b>LuckPerms chưa được tải</b> khi plugin khác cố sử dụng nó.</p>"
				+ "<p><b>Nguyên nhân có thể:</b></p>" + "<ul>"
				+ "<li>Plugin <b>LuckPerms chưa được cài</b> hoặc <b>khởi động thất bại</b>.</li>"
				+ "<li>Một plugin khác đang truy cập LuckPerms quá sớm hoặc sai cách.</li>" + "</ul>"
				+ "<p>Nên <b>kiểm tra console</b> bằng liên kết để xác định plugin gây lỗi "
				+ "và kiểm tra tính tương thích.</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Shaderpack Iris chưa được tải";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "không xác định" : shaderpack;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi khi tải shaderpack với Iris/Oculus.</b>" + "<p><b>Shaderpack bị ảnh hưởng:</b> "
				+ nombre + "</p>" + "<p>Minecraft không thể mở tệp shaderpack (FileSystemNotFoundException).</p>"
				+ "<p><b>Cách khắc phục:</b></p>" + "<ul>"
				+ "<li>Kiểm tra shaderpack đã được cài đúng trong thư mục <b>shaderpacks</b>.</li>"
				+ "<li>Tải lại shaderpack vì tệp có thể bị lỗi.</li>"
				+ "<li>Nếu vẫn lỗi, hãy xóa tệp <b>config/iris.properties</b> để reset cấu hình Iris.</li>" + "</ul>"
				+ "<p>Sau khi thực hiện, hãy khởi động lại game.</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "Không thể ghi tệp cấu hình";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "không xác định" : ruta;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã xảy ra lỗi khi lưu tệp cấu hình.</b>" + "<p><b>Tệp bị ảnh hưởng:</b> " + archivo + "</p>"
				+ "<p>Minecraft không thể ghi tệp bằng phương thức ghi nguyên tử (REPLACE_ATOMIC).</p>"
				+ "<p><b>Nguyên nhân thường gặp:</b></p>" + "<ul>"
				+ "<li>Quyền truy cập không đúng trên thư mục hoặc tệp.</li>"
				+ "<li>Tệp được đặt ở chế độ chỉ đọc.</li>"
				+ "<li>Một chương trình khác (antivirus, backup, editor) đang khóa tệp.</li>" + "</ul>"
				+ "<p><b>Khuyến nghị:</b></p>" + "<ul>" + "<li>Kiểm tra quyền ghi trong thư mục.</li>"
				+ "<li>Bỏ thuộc tính chỉ đọc của tệp.</li>" + "<li>Đóng các chương trình đang sử dụng tệp đó.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "Bị từ chối truy cập khi tạo bản sao lưu";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "không xác định" : origen;
		String dst = backup == null || backup.isEmpty() ? "không xác định" : backup;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã xảy ra lỗi quyền khi tạo bản sao lưu tệp cấu hình.</b>" + "<p><b>Tệp gốc:</b> " + src + "</p>"
				+ "<p><b>Tệp sao lưu:</b> " + dst + "</p>"
				+ "<p>Hệ điều hành đã chặn truy cập trong quá trình lưu tệp.</p>"
				+ "<p><b>Nguyên nhân thường gặp:</b></p>" + "<ul>" + "<li>Quyền thư mục không đủ.</li>"
				+ "<li>Tệp được đặt ở chế độ chỉ đọc.</li>"
				+ "<li>Một chương trình khác (antivirus, đồng bộ, editor) đang sử dụng tệp.</li>" + "</ul>"
				+ "<p><b>Khuyến nghị:</b></p>" + "<ul>" + "<li>Kiểm tra quyền thư mục <b>config</b>.</li>"
				+ "<li>Đóng các chương trình đang truy cập tệp.</li>"
				+ "<li>Thử chạy launcher hoặc Minecraft với quyền quản trị viên.</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		return "Bật console";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>Công cụ debug</b><br><br>" + "Tại đây bạn có thể bật các chức năng nâng cao để debug "
				+ Statics.nombre_cd.obtener() + " và trò chơi.<br><br>"
				+ "Khuyến nghị bật console phát triển để nhận thông tin chi tiết, trace và chẩn đoán trong quá trình phân tích.<br><br>"
				+ "Nếu cần thử máy chủ multiplayer ở chế độ online, có thể cần cho phép chuyển token truy cập sang tiến trình "
				+ Statics.nombre_cd.obtener() + " từ cài đặt quyền riêng tư. "
				+ "Điều này thường <b>không được khuyến nghị</b> trong các trường hợp khác.<br><br>"
				+ "Hướng dẫn đầy đủ: <a href='https://example.com'>Liên kết!</a>";
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện không tương thích giữa Simple Clouds và shaders.</b>"
				+ "<p>Simple Clouds không tương thích với các mod shader (Iris/Oculus) khi có Distant Horizons.</p>"
				+ "<p><b>Lựa chọn khuyến nghị:</b></p>" + "<ul>"
				+ "<li>Gỡ <b>Simple Clouds</b> nếu bạn muốn dùng shader.</li>"
				+ "<li>Hoặc gỡ <b>Iris/Oculus</b> nếu muốn giữ Simple Clouds.</li>" + "</ul>"
				+ "<p>Hạn chế này đến từ chính mod Simple Clouds và không thể khắc phục nếu không sửa mã nguồn.</p>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "Không tương thích: Simple Clouds vs Shaders";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "Màu nút thanh bên";
	}

	@Override
	public String profilerTitulo() {
		return "Profiler";
	}

	@Override
	public String profilerDescripcion() {
		return "Công cụ phân tích hiệu năng dựa trên instrument và sampling.";
	}

	@Override
	public String profilerIniciar() {
		return "Bắt đầu";
	}

	@Override
	public String profilerDetener() {
		return "Dừng";
	}

	@Override
	public String profilerLimpiar() {
		return "Xóa";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "Profiler đã bắt đầu.";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "Profiler đã dừng.";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "Mẫu nhận từ luồng: " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "Lấy mẫu stack định kỳ để phát hiện bottleneck và deadlock.";
	}

	@Override
	public String entrarAlJuego() {
		return "Vào game";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện lỗi đường dẫn hệ thống.</b>"
				+ "<p>Minecraft không thể khởi động do có ký tự không hợp lệ trong tên thư mục.</p>"
				+ "<p>Hệ thống phát hiện ký tự không hợp lệ trong đường dẫn (ví dụ: ':' hoặc ký tự đặc biệt khác).</p>"
				+ "<p><b>Giải pháp khuyến nghị:</b></p>" + "<ul>"
				+ "<li>Đổi tên thư mục của instance hoặc profile.</li>"
				+ "<li>Chỉ sử dụng ký tự ASCII cơ bản (A-Z, a-z, 0-9).</li>"
				+ "<li>Không dùng dấu, ký tự đặc biệt, khoảng trắng lỗi hoặc emoji.</li>" + "</ul>"
				+ "<p>Ví dụ hợp lệ: <b>MiInstancia1</b></p>"
				+ "<p>Ví dụ không hợp lệ: <b>Instancia🔥</b> hoặc <b>Instancia:Mod</b></p>";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "Đường dẫn không hợp lệ: ký tự bị cấm";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện lỗi shader Twilight Forest với GPU Intel.</b>"
				+ "<p>Lỗi này liên quan đến driver đồ họa Intel khi tải shader của mod Twilight Forest.</p>"
				+ "<p>Sự cố xảy ra trong driver (igxelpicd64), không phải lỗi trực tiếp của mod hay Minecraft.</p>"
				+ "<p><b>Giải pháp khuyến nghị:</b></p>" + "<ul>"
				+ "<li>Cập nhật driver Intel lên phiên bản mới nhất.</li>"
				+ "<li>Thử các phiên bản 31.0.101.8331 hoặc 31.0.101.8247 WHQL, được báo là ổn định.</li>" + "</ul>"
				+ "<p>Theo dõi chính thức:</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>Lưu ý:</b> Một số GPU Intel cũ có thể không được cập nhật sửa lỗi này.</p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "Crash: Twilight Forest + Driver Intel";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "Forge: không thể tải language provider";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "Không xác định" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Forge không thể tải language provider.</b>"
				+ "<p>Đã xảy ra lỗi khi khởi tạo IModLanguageProvider.</p>" + "<p><b>Provider lỗi:</b> " + providerTexto
				+ "</p>" + "<p>Lỗi này thường xảy ra khi:</p>" + "<ul>"
				+ "<li>Thiếu dependency (ví dụ: Kotlin for Forge).</li>"
				+ "<li>Phiên bản mod không tương thích với Forge.</li>" + "<li>Tệp mod bị hỏng.</li>" + "</ul>"
				+ "<p><b>Giải pháp:</b></p>" + "<ul>" + "<li>Cài lại mod.</li>" + "<li>Kiểm tra dependency đầy đủ.</li>"
				+ "<li>Đảm bảo dùng phiên bản tương thích.</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "Crash: Lets Do Compat (RecipeManager intercept)";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện crash trong Lets Do Compat (interceptApply).</b>"
				+ "<p>Lỗi xảy ra trong method <b>RecipeManager.interceptApply</b> do mod Lets Do Compat sửa đổi.</p>"
				+ "<p>Thường do:</p>" + "<ul>" + "<li>Xung đột với mod khác chỉnh recipe.</li>"
				+ "<li>Phiên bản không đúng.</li>" + "<li>Xung đột mixin/coremod.</li>" + "</ul>"
				+ "<p><b>Giải pháp:</b></p>" + "<ul>" + "<li>Thử gỡ Lets Do Compat để kiểm tra.</li>" + "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI: lỗi Item Group (plugin không tương thích)";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "Plugin không xác định"
				: String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "JEI phát hiện lỗi khi tạo nhóm item.</b>"
				+ "<p>Một hoặc nhiều plugin gây lỗi khi JEI tạo danh sách ingredient.</p>"
				+ "<p><b>Plugin bị ảnh hưởng:</b> " + listaPlugins + "</p>" + "<p>Thường do:</p>" + "<ul>"
				+ "<li>Plugin lỗi hoặc outdated.</li>" + "<li>Không tương thích với JEI.</li>"
				+ "<li>Mod đăng ký Item Group sai.</li>" + "</ul>" + "<p><b>Giải pháp:</b></p>" + "<ul>"
				+ "<li>Cập nhật JEI và mod.</li>" + "<li>Gỡ plugin để test.</li>" + "<li>Báo lỗi cho dev.</li>"
				+ "</ul>" + "<p>Các item sẽ không hiển thị cho đến khi fix.</p>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "Phiên bản mod không hợp lệ (SemVer)";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "Không xác định" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "Không tìm thấy mod" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện phiên bản mod không hợp lệ.</b>" + "<p>Phiên bản <b>" + v
				+ "</b> không đúng chuẩn SemVer.</p>" + "<p>Lỗi do pre-release rỗng (kết thúc bằng '+').</p>"
				+ "<p><b>Mod:</b><br>" + u + "</p>" + "<p><b>Giải pháp:</b></p>" + "<ul>"
				+ "<li>Sửa version trong tệp mod.</li>" + "<li>Xóa dấu '+' cuối.</li>" + "<li>Cập nhật mod.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS: truy cập trái phép giữa module";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện truy cập trái phép giữa module (JPMS).</b>"
				+ "<p>Hệ thống module Java đã chặn truy cập.</p>" + "<p><b>Class truy cập:</b><br>" + claseOrigen
				+ " (module: " + moduloOrigen + ")</p>" + "<p><b>Class bị chặn:</b><br>" + claseDestino + " (module: "
				+ moduloDestino + ")</p>" + "<p>Lỗi này xảy ra khi mod không khai báo đúng exports/opens.</p>"
				+ "<p><b>Nguyên nhân:</b></p>" + "<ul>" + "<li>Không export package.</li>"
				+ "<li>Thiếu opens cho reflection.</li>" + "<li>Cấu hình JPMS sai.</li>" + "</ul>"
				+ "<p>Dev mod cần sửa lỗi này.</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin: class đặt sai package";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Class đặt sai trong package mixin.</b>" + "<p>Class thường nằm trong package mixin.</p>"
				+ "<p><b>Class:</b><br>" + clase + "</p>" + "<p><b>Package mixin:</b><br>" + paquete + "</p>"
				+ "<p><b>File mixin:</b><br>" + archivoMixin + "</p>"
				+ "<p>Chỉ class mixin mới được nằm trong package này.</p>"
				+ "<p><b>Fix:</b> di chuyển class hoặc sửa mixins.json.</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện lỗi driver GPU Matrox.</b>" + "<p>Lỗi xảy ra trong driver Matrox.</p>"
				+ "<p>GPU Matrox (G200/G400) không hỗ trợ render 3D hiện đại.</p>" + "<p><b>Giải pháp:</b></p>" + "<ul>"
				+ "<li>Cập nhật driver.</li>" + "<li>Dùng driver chính thức.</li>"
				+ "<li>Dùng GPU hỗ trợ OpenGL 3.2+.</li>" + "</ul>" + "<p>GPU này chỉ phù hợp hiển thị cơ bản.</p>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod: không có GPU tương thích";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod không tìm thấy GPU hỗ trợ.</b>" + "<p>Không có GPU hỗ trợ Vulkan.</p>"
				+ "<p>Nguyên nhân:</p>" + "<ul>" + "<li>GPU không hỗ trợ Vulkan.</li>" + "<li>Driver lỗi hoặc cũ.</li>"
				+ "<li>Dùng GPU sai.</li>" + "</ul>" + "<p><b>Giải pháp:</b></p>" + "<ul>" + "<li>Cập nhật driver.</li>"
				+ "<li>Kiểm tra hỗ trợ Vulkan.</li>" + "<li>Dùng GPU rời.</li>"
				+ "<li>Gỡ VulkanMod nếu không hỗ trợ.</li>" + "</ul>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "RenderType không hợp lệ cho outline";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		String base = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod cố áp dụng outline cho RenderType không hợp lệ.</b>" + "<p>Lỗi:</p>"
				+ "<code>Can't render an outline for this rendertype!</code>";

		if (enchantDetectado) {
			base += "<p><b>Phát hiện mod enchant-outline / better-enchants.</b></p>"
					+ "<p>Mod này gây lỗi ở phiên bản mới.</p>"
					+ "<p><b>Giải pháp:</b> gỡ hoặc cập nhật enchant-outline.</p>";
		} else {
			base += "<p>Thường do mod render (Entity Model Features, Visuality...).</p>"
					+ "<p><b>Giải pháp:</b> tắt từng mod render.</p>";
		}

		return base;
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – DimensionalInventory null";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG đã cố lưu một DimensionalInventory null.</b>" + "<p>Trò chơi báo:</p>"
				+ "<code>Cannot invoke DimensionalInventory.saveInventory(...) because \"d\" is null</code>"
				+ "<p>Đây là lỗi đã biết của DivineRPG liên quan đến hệ thống túi đồ Vethean.</p>"
				+ "<p><b>Giải pháp khuyến nghị:</b></p>" + "<ul>" + "<li>Vào tệp cấu hình của DivineRPG.</li>"
				+ "<li>Đặt <code>saferVetheanInventory=true</code></li>" + "<li>Lưu và khởi động lại trò chơi.</li>"
				+ "</ul>" + "<p>Cũng nên cập nhật DivineRPG nếu có phiên bản mới hơn.</p>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "Xung đột Render Pass";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột trong hệ thống render.</b>" + "<p>Trò chơi báo:</p>"
				+ "<code>Close the existing render pass before performing additional commands</code>"
				+ "<p>Lỗi này thường liên quan đến xung đột giữa các mod render "
				+ "như Iris, OptiFine, VulkanMod hoặc các mod khác thay đổi pipeline đồ họa.</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "Lỗi nội bộ của Feather Client";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi nội bộ của Feather Client.</b>" + "<p>Trò chơi báo:</p>"
				+ "<code>NoClassDefFoundError: feather/lib/sentry/Sentry</code>"
				+ "<p>Lỗi này do Feather Client gây ra.</p>" + "<p>Feather không được khuyến nghị vì:</p>" + "<ul>"
				+ "<li>Nó dùng các phiên bản độc quyền và đã chỉnh sửa của mod phổ biến.</li>"
				+ "<li>Nó phá vỡ khả năng tương thích với Fabric chuẩn.</li>"
				+ "<li>Nó hoạt động như một modpack dựng sẵn với các chỉnh sửa nội bộ.</li>"
				+ "<li>Nó thường gây xung đột với Sodium và các mod hiệu năng khác.</li>" + "</ul>"
				+ "<p>Khuyến nghị dùng bản cài Fabric tiêu chuẩn thay vì Feather.</p>";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "Xung đột Iris + Flywheel (Create 6)";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột giữa Iris và Flywheel trong Create 6.</b>" + "<p>Trò chơi báo:</p>"
				+ "<code>NoSuchFieldError: TESSELATION_SHADERS</code>"
				+ "<p>Đã phát hiện tham chiếu nội bộ <code>$irisflw$</code>, "
				+ "điều này cho thấy có xung đột giữa Iris và Flywheel.</p>"
				+ "<p>Iris Flywheel 2.0 cho Create 6 hiện chỉ tương thích chính thức với NeoForge.</p>"
				+ "<p>Nếu bạn đang dùng Forge hoặc Fabric, tổ hợp này có thể gây ra lỗi này.</p>";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "Không tìm thấy model GeckoLib";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Một mod không thể tìm thấy model GeckoLib.</b>" + "<p>Model bị ảnh hưởng:</p>" + "<code>" + modelo
				+ "</code>" + "<p>Lỗi này xảy ra khi tệp <code>.geo.json</code> không tồn tại "
				+ "hoặc được cấu hình sai bên trong mod.</p>" + "<p>Nguyên nhân có thể:</p>" + "<ul>"
				+ "<li>Model đã bị xóa nhưng vẫn còn được tham chiếu.</li>" + "<li>Lỗi trong đường dẫn tệp.</li>"
				+ "<li>Thiếu tệp trong JAR.</li>" + "<li>Phiên bản mod không tương thích.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – Hoạt ảnh không tồn tại";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon đã cố phát một hoạt ảnh không tồn tại.</b>" + "<p>Hoạt ảnh:</p>" + "<code>" + animacion
				+ "</code>" + "<p>Nhóm:</p>" + "<code>" + grupo + "</code>" + "<p>Lỗi này thường xảy ra khi:</p>"
				+ "<ul>" + "<li>Bạn trộn các phiên bản Cobblemon không tương thích.</li>"
				+ "<li>Một addon không khớp với phiên bản đã cài.</li>"
				+ "<li>Thiếu tài nguyên hoặc hoạt ảnh nội bộ.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "Lỗi nội bộ của Lunar Client";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi nội bộ của Lunar Client.</b>"
				+ "<p>Lỗi đến từ chính Lunar Client chứ không phải từ game gốc hay các mod.</p>"
				+ "<p>Lunar Client sử dụng các chỉnh sửa nội bộ và tùy biến riêng có thể "
				+ "gây ra xung đột với mod hoặc với một số cấu hình cụ thể.</p>"
				+ "<p>Khuyến nghị thử với bản cài Minecraft tiêu chuẩn "
				+ "để loại trừ lỗi do chính client gây ra.</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "Truy cập trái phép vào phương thức hoặc trường";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Một mod đã cố truy cập vào một phương thức hoặc trường được bảo vệ/riêng tư.</b>"
				+ "<p>Lớp gây ra:</p>" + "<code>" + clase + "</code>" + "<p>Thành viên bị truy cập:</p>" + "<code>"
				+ miembro + "</code>" + "<p>Lỗi này thường xảy ra khi:</p>" + "<ul>"
				+ "<li>Mod được biên dịch cho phiên bản Minecraft khác.</li>"
				+ "<li>Có sự trộn lẫn giữa các mapping không tương thích.</li>" + "<li>Mod đã lỗi thời.</li>"
				+ "<li>Đang dùng sai loader (Fabric/Forge/NeoForge).</li>" + "</ul>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "Lỗi khi tải datapack/resourcepack";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Một datapack hoặc resourcepack đã tải thất bại.</b>" + "<p>Tệp có vấn đề:</p>" + "<code>" + archivo
				+ "</code>" + "<p>Pack:</p>" + "<code>" + pack + "</code>"
				+ "<p>Trò chơi không thể phân tích tệp này và điều đó đã gây ra lỗi khi tải registry.</p>"
				+ "<p>Vấn đề này thường do:</p>" + "<ul>" + "<li>JSON sai định dạng.</li>"
				+ "<li>Phiên bản pack không tương thích.</li>"
				+ "<li>Pack đã lỗi thời với phiên bản trò chơi hiện tại.</li>" + "<li>Xung đột giữa các datapack.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "Lỗi biên dịch shader";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Việc biên dịch một shader đã thất bại.</b>"
				+ "<p>Trò chơi không thể biên dịch một trong các shader đang hoạt động.</p>"
				+ "<p>Vấn đề này thường liên quan đến Sodium, Iris hoặc shaderpack không tương thích.</p>"
				+ "<p>Khuyến nghị:</p>" + "<ul>" + "<li>Thử một shader khác.</li>" + "<li>Tạm thời tắt shader.</li>"
				+ "<li>Cập nhật driver GPU.</li>"
				+ "<li>Nếu lỗi vẫn tiếp tục, hãy thử khởi động trò chơi mà không có Sodium.</li>" + "</ul>";
	}

	public String nombreErrorCreacionModelo() {
		return "Lỗi khi tạo hoặc tải model";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>Đã xảy ra lỗi khi cố tạo hoặc tải một model của Minecraft.</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>Model bị ảnh hưởng: <code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>Loại lỗi này thường xảy ra khi:</p>");
		sb.append("<ul>");
		sb.append("<li>Một mod có model được cấu hình sai.</li>");
		sb.append("<li>Một model JSON bị hỏng hoặc không đầy đủ.</li>");
		sb.append("<li>Có xung đột giữa các mod chỉnh sửa model hoặc render.</li>");
		sb.append("<li>Một resource pack hoặc datapack chứa các model không tương thích.</li>");
		sb.append("</ul>");
		sb.append("<p>Hãy thử xác định mod hoặc gói tài nguyên nào cung cấp model được nêu ở trên.</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>Nguyên nhân có thể được phát hiện:</b></p>");
		sb.append("<p>Đã phát hiện hoạt động của mod <b>Cooler Animations</b> trong nhật ký.</p>");
		sb.append(
				"<p>Mod này thay đổi hệ thống hoạt ảnh và mô hình, và trong một số trường hợp có thể gây lỗi khi tải mô hình.</p>");
		sb.append(
				"<p>Nếu sự cố vẫn tiếp diễn, hãy thử khởi động trò chơi mà không có Cooler Animations để kiểm tra xem lỗi có biến mất không.</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "Sự cố với Starlight";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi liên quan đến Starlight.</b>"
				+ "<p>Lỗi xảy ra trong <code>BlockStarLightEngine.initNibble</code>.</p>"
				+ "<p>Điều này cho thấy có sự cố trong hệ thống chiếu sáng của mod <b>Starlight</b>.</p>"
				+ "<p>Starlight là mod thay đổi hoàn toàn hệ thống ánh sáng của Minecraft và được biết là có thể gây ra nhiều vấn đề trong một số môi trường mod.</p>"
				+ "<p>Đây chỉ là một trong nhiều lỗi đã biết liên quan đến Starlight.</p>"
				+ "<p>Nếu sự cố vẫn tiếp diễn, hãy thử chạy trò chơi mà không có Starlight.</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "Sự cố với AAAParticles / Effekseer";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện crash native liên quan đến Effekseer.</b>"
				+ "<p>Lỗi xảy ra trong thư viện native <code>EffekseerNativeForJava</code>.</p>"
				+ "<p>Thư viện này được sử dụng bởi mod <b>AAAParticles</b> do ChloePrime phát triển.</p>"
				+ "<p>Các crash trong thư viện native thường cho thấy vấn đề nằm trong chính mod hoặc các phụ thuộc native của nó.</p>"
				+ "<p>Nếu sự cố vẫn tiếp diễn, hãy thử chạy trò chơi mà không có AAAParticles.</p>";
	}

	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện sự cố crash native của môi trường thực thi Java (JVM).</b>"
				+ "<p>Loại lỗi này xảy ra bên trong chính Máy ảo Java (ví dụ: trong <code>jvm.dll</code>, <code>libjvm.so</code>, v.v.), "
				+ "và không nhất thiết do mod gây ra.</p>"
				+ "<p>Mặc dù trong những trường hợp hiếm hoi có thể bắt nguồn từ các mod sử dụng thư viện native không tương thích, "
				+ "<b>nhưng khả năng cao hơn là do phiên bản JVM bị lỗi, hỏng hoặc lỗi thời</b>.</p>"
				+ "<p>Điều này đặc biệt phổ biến nếu bạn đang sử dụng bản build cũ hoặc không chính thức của phiên bản Java (ví dụ: các bản build cộng đồng không được hỗ trợ).</p>"
				+ "<p><b>Chúng tôi khuyên dùng một JVM đáng tin cậy và được duy trì:</b></p>" + "<ul>"
				+ "<li><b>Red Hat Build of OpenJDK</b> (ổn định, đã được kiểm tra kỹ, lý tưởng cho Windows/Linux)</li>"
				+ "<li><b>OpenLogic OpenJDK</b> (hỗ trợ đa nền tảng, bao gồm macOS Intel)</li>"
				+ "<li><b>Azul Zulu</b> (được chứng nhận, với hỗ trợ LTS miễn phí)</li>"
				+ "<li><b>Oracle JDK</b> (chính thức, với các bản cập nhật thường xuyên)</li>" + "</ul>"
				+ "<p>Tránh các bản build không rõ nguồn gốc, tùy chỉnh hoặc quá cũ, vì chúng có thể chứa các lỗi nghiêm trọng trong động cơ JVM.</p>"
				+ "<p><b>Bạn dùng Prism Launcher hoặc TLauncher?</b> Rất dễ để cấu hình một JVM tùy chỉnh: "
				+ "trong Prism Launcher, hãy vào <i>Installations</i> → <i>Edit Instance</i> → <i>Java Settings</i>; "
				+ "trong TLauncher, hãy vào <i>Settings</i> → <i>Java Settings</i> và chọn đường dẫn đến JDK/JRE đã tải xuống. "
				+ "Ngoài ra, bộ thu gom rác (garbage collector) của bạn có thể gặp sự cố; trong trường hợp đó, bạn nên chuyển sang ZGC."

				+ "Bạn không cần thay đổi JVM của hệ thống!</p>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "Xung đột giữa Paranoia và C2ME";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện xung đột giữa các mod Paranoia và C2ME.</b>"
				+ "<p>Lỗi cho thấy <code>ThreadLocalRandom</code> bị truy cập từ một luồng không hợp lệ.</p>"
				+ "<p>Điều này thường xảy ra khi mod <b>Paranoia</b> thực thi mã không an toàn đa luồng trong khi <b>C2ME</b> tối ưu hóa đa luồng.</p>"
				+ "<p>Loại xung đột này phổ biến với các mod được tạo bằng MCreator.</p>"
				+ "<p>Nếu sự cố vẫn tiếp diễn, hãy thử chạy trò chơi mà không có Paranoia hoặc tắt C2ME.</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "Thiếu thư mục assets của Minecraft";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft không thể tìm thấy thư mục assets.</b>"
				+ "<p>Launcher đã cố gắng khởi động trò chơi với đường dẫn assets không hợp lệ.</p>"
				+ "<p>Điều này có nghĩa là các tệp tài nguyên của trò chơi (assets) không tồn tại hoặc chưa được cài đặt đúng cách.</p>"
				+ "<p>Sự cố này thường xảy ra do cài đặt Minecraft không chính xác hoặc launcher cấu hình sai.</p>"
				+ "<p>Nó cũng có thể xảy ra khi sử dụng các launcher không chính thức xử lý assets không đúng, như FreshCraft.</p>"
				+ "<p>Nếu sự cố vẫn tiếp diễn, hãy thử cài đặt lại modpack hoặc chạy trò chơi từ launcher chính thức hoặc đáng tin cậy.</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return "Cài đặt " + dependencia + " phiên bản " + version + " hoặc mới hơn";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return "Thay thế " + dependencia + " bằng phiên bản từ " + min + " đến " + max;
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "Mod " + mod + " yêu cầu " + dependencia + " phiên bản tối thiểu " + version;
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "Mod " + mod + " yêu cầu " + dependencia + " phiên bản " + version;
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {
		return "Mod " + mod + " yêu cầu " + dependencia + " từ " + min + " đến " + max + " (hiện tại: " + actual + ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "Phiên bản Cupboard không tương thích";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện crash do phiên bản Cupboard cũ.</b>"
				+ "<p>Lỗi xảy ra trong <code>ClientConfigCompat.setupNeoforge</code> do "
				+ "<code>ModList.get()</code> trả về <code>null</code>.</p>"
				+ "<p>Đây là lỗi đã biết trong các phiên bản cũ của mod <b>Cupboard</b>.</p>"
				+ "<p>Các phiên bản cũ như <b>3.2</b> chứa lỗi này.</p>"
				+ "<p><b>Giải pháp:</b> cập nhật Cupboard lên phiên bản <b>3.5</b> hoặc mới hơn.</p>";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		return "Điều này xảy ra vì bạn đang sử dụng macOS và trò chơi đang cố sử dụng OpenGL, điều này không còn được hỗ trợ trong các phiên bản macOS mới. "
				+ "Bạn cần sử dụng phiên bản Minecraft hỗ trợ Metal hoặc dùng Linux nếu bạn có Mac Intel, M1 hoặc M2 (không hỗ trợ M3+ hoặc Neo).";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "Không tìm thấy hoạt ảnh GeckoLib";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Một mod không thể tải tệp hoạt ảnh GeckoLib.</b>" + "<p>Tệp bị ảnh hưởng:</p><code>" + archivo
				+ "</code>"
				+ "<p>Lỗi này xảy ra khi tệp <code>.json</code> không tồn tại, có lỗi cú pháp hoặc đường dẫn sai.</p>"
				+ "<p>Nguyên nhân có thể:</p><ul>" + "<li>Tệp đã bị xóa nhưng vẫn được tham chiếu trong mã.</li>"
				+ "<li>Lỗi cú pháp trong tệp JSON.</li>" + "<li>Đường dẫn sai được định nghĩa trong nhật ký mod.</li>"
				+ "<li>Xung đột phụ thuộc hoặc phiên bản không tương thích.</li>" + "</ul>";
	}

	@Override
	public String nombreAnimacionGeckoInexiste() {
		return "Không tìm thấy hoạt ảnh GeckoLib";
	}

	@Override
	public String mensajeAnimacionGeckoInexiste(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Một mod không thể tìm thấy tệp hoạt ảnh GeckoLib.</b>" + "<p>Tệp bị ảnh hưởng:</p><code>" + archivo
				+ "</code>"
				+ "<p>Lỗi này xảy ra khi GeckoLib cố tải một hoạt ảnh không tồn tại tại đường dẫn đã chỉ định.</p>"
				+ "<p>Khác với lỗi cú pháp, lỗi này cho thấy tệp không tồn tại hoặc đường dẫn sai.</p>"
				+ "<p>Nguyên nhân có thể:</p><ul>"
				+ "<li>Tệp <code>.json</code> không được включ vào JAR hoặc đã bị xóa.</li>"
				+ "<li>Lỗi chính tả trong đường dẫn (ví dụ: 'animations' vs 'animaciones').</li>"
				+ "<li>Khác biệt chữ hoa/thường giữa Linux và Windows.</li>"
				+ "<li>Mod chưa được cập nhật đầy đủ hoặc phụ thuộc bị lỗi.</li>" + "</ul>";
	}

	@Override
	public String nombreRegistroDuplicadoObjeto() {
		return "Xung đột đăng ký đối tượng";
	}

	@Override
	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto) {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color
				+ "'>Xung đột nghiêm trọng: Một đối tượng đã được đăng ký hai lần. Các mod </span>" + mod1
				+ "<span style='color:#" + color + "'> và </span>" + mod2 + "<span style='color:#" + color
				+ "'> đang cố đăng ký cùng một đối tượng. Đối tượng bị xung đột: </span>" + objeto + "<br><br>";

		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Điều này thường xảy ra khi hai mod khác nhau thêm một đối tượng có cùng tên "
				+ "trong cùng namespace, hoặc do lỗi trong mã của một trong các mod.<br><br>"
				+ "<b>Giải pháp đề xuất:</b><br><ul>"
				+ "<li>Kiểm tra xem một mod có phải là bản cập nhật hoặc fork của mod kia không.</li>"
				+ "<li>Thử xóa một trong hai mod gây xung đột.</li>"
				+ "<li>Kiểm tra tệp cấu hình của các mod để thay đổi ID đối tượng nếu có thể.</li>" + "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreFalloFabricRenderingAPI() {
		return "Thiếu Fabric Rendering API";
	}

	@Override
	public String mensajeFalloFabricRenderingAPI() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color
				+ "'>Một mod (thường là Porting Lib hoặc các phụ thuộc của nó) đã gặp lỗi vì </span>"
				+ "Fabric Rendering API<span style='color:#" + color + "'> không khả dụng.</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Giải pháp đề xuất:</b><br>"
				+ "Thông báo có thể đề xuất cài Indium, nhưng mod này đã lỗi thời trong các phiên bản game hiện đại.<br>"
				+ "<ul>"
				+ "<li><b>Cập nhật Sodium</b> lên phiên bản <b>0.6.0</b> hoặc mới hơn (phiên bản này đã bao gồm hỗ trợ cần thiết).</li>"
				+ "<li>Đảm bảo bạn đã cài <b>Fabric API</b>.</li>"
				+ "<li>Nếu bạn dùng phiên bản game cũ (1.20.6 trở xuống), hãy cài Indium.</li>" + "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRestriccionesDependenciaNoCumplidas() {
		return "Không đáp ứng yêu cầu phụ thuộc";
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos) {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>Đã phát hiện </span>" + cantidad
				+ "<span style='color:#" + color + "'> yêu cầu phụ thuộc không được đáp ứng.</span><br><br>";

		StringBuilder listaDetalle = new StringBuilder();
		if (conflictos != null && !conflictos.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Phát hiện xung đột trong các tệp sau:</span><br><ul>");
			for (String[] par : conflictos) {
				String dep = par[0];
				String jar = par[1];

				listaDetalle.append("<li>").append("<span style='color:#").append(color).append("'>Tệp: </span>")
						.append(jar).append("<br><span style='color:#").append(color).append("'>Yêu cầu: </span>")
						.append(dep).append("</li>");
			}
			listaDetalle.append("</ul><br>");
		}

		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Lỗi này xảy ra khi nhiều mod yêu cầu các phiên bản không tương thích của cùng một thư viện.<br><br>"
				+ "<b>Giải pháp đề xuất:</b><br><ul>" + "<li>Thử cập nhật hoặc xóa các mod được liệt kê ở trên.</li>"
				+ "<li>Nếu không có phiên bản tương thích, bạn có thể chỉnh sửa tệp <code>mods.toml</code> trong file JAR "
				+ "(bằng WinRAR hoặc 7-Zip) để thay đổi yêu cầu phiên bản, nhưng việc này có thể gây lỗi.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod) {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>Đã phát hiện </span>" + cantidad
				+ "<span style='color:#" + color + "'> yêu cầu phụ thuộc không được đáp ứng.</span><br><br>";

		StringBuilder listaDetalle = new StringBuilder();
		if (conflictosPorMod != null && !conflictosPorMod.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Các mod liên quan và phụ thuộc yêu cầu:</span><br><ul>");

			for (Map.Entry<String, List<String>> entry : conflictosPorMod.entrySet()) {
				String archivo = entry.getKey();
				List<String> dependencias = entry.getValue();

				listaDetalle.append("<li><b>").append(archivo).append("</b><ul>");
				for (String dep : dependencias) {
					listaDetalle.append("<li>").append(dep).append("</li>");
				}
				listaDetalle.append("</ul></li>");
			}
			listaDetalle.append("</ul><br>");
		} else {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Không thể xác định tệp cụ thể từ log.</span><br>");
		}

		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Lỗi này xảy ra khi các mod chứa các thư viện nội bộ không tương thích với nhau (Jar-in-Jar).<br><br>"
				+ "<b>Giải pháp đề xuất:</b><br><ul>"
				+ "<li>Xác định mod nào yêu cầu phiên bản khác nhau của cùng một thư viện.</li>"
				+ "<li>Cập nhật các mod lên phiên bản mới nhất.</li>"
				+ "<li>Cuối cùng, bạn có thể chỉnh sửa <code>META-INF/mods.toml</code> trong file JAR, "
				+ "nhưng việc này có thể gây lỗi.</li>" + "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String nombreNeruinaOcultaAdvertencia() {
		return "Neruina cản trở việc gỡ lỗi";
	}

	@Override
	public String mensajeNeruinaOcultaAdvertencia() {
		String color = Config.obtenerInstancia().obtenerColorAdvertencia();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Cảnh báo:</b> Mod <b>Neruina</b> đang xử lý lỗi không đúng cách, làm che giấu nguyên nhân thực sự của crash.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Mod này thường không cần thiết và gây khó khăn cho việc xác định lỗi. Nên gỡ bỏ để kiểm tra.</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Hướng dẫn khắc phục:</b><br>"
				+ "1. **MCForge**: vào '[tên_thế_giới]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: vào 'config/neoforge-server.toml'.<br>"
				+ "3. Đặt **removeErroringBlockEntities** và **removeErroringEntities** thành **true**.<br><br>"
				+ "<b>Tùy chọn khác:</b><br>" + "- Dùng **MCEdit** để xóa entity lỗi.<br>"
				+ "- Nếu lỗi vẫn còn, có thể Neruina đang gây thêm lỗi." + "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreApothicAttributeSinDueño() {
		return "Lỗi Apothic Attributes";
	}

	@Override
	public String mensajeApothicAttributeSinDueño(boolean chestCavityDetectado) {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Apothic Attributes</b> phát hiện xung đột: một <b>AttributeMap</b> bị chỉnh sửa mà không có thực thể sở hữu.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Điều này thường xảy ra khi mod chỉnh sửa thuộc tính entity sai thời điểm hoặc sai cách.</span><br><br>";

		String notaChestCavity = "";
		if (chestCavityDetectado) {
			notaChestCavity = "<span style='color:#" + color + "'>"
					+ "<b>Phát hiện mod Chest Cavity trong log.</b> Đây là nguyên nhân phổ biến của lỗi này.</span><br><br>";
		}

		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Giải pháp đề xuất:</b><br><ul>"
				+ "<li>Nếu có Chest Cavity, hãy cập nhật hoặc gỡ tạm thời.</li>"
				+ "<li>Kiểm tra các mod chỉnh sửa thuộc tính entity khác.</li>"
				+ "<li>Cập nhật <b>Apothic Attributes</b> lên phiên bản mới nhất.</li>" + "</ul></span>";

		return mensajeBase + notaChestCavity + instrucciones;
	}

	@Override
	public String nombreErrorPotBlockEntity() {
		return "Lỗi DecoratedPot (Cataclysm)";
	}

	@Override
	public String mensajeErrorPotBlockEntity() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Đã xảy ra lỗi không tương thích với <b>DecoratedPotBlockEntity</b>.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Đây là lỗi đã biết trong phiên bản 1.19.2 của mod <b>L_Enders_Cataclysm</b>, "
				+ "do thiếu một phần triển khai cần thiết của trò chơi.</span><br><br>";

		String solucion = "<span style='color:#" + color + "'>" + "<b>Giải pháp đề xuất:</b><br>"
				+ "Cài đặt mod <b>PotFix (Cataclysm Patch)</b> để khắc phục lỗi này.<br>"
				+ "Bạn có thể tải tại: <a href='https://www.curseforge.com/minecraft/mc-mods/potfix-cataclysm-patch'>CurseForge - PotFix</a>"
				+ "</span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorPreloadingTricks() {
		return "Lỗi Preloading Tricks";
	}

	@Override
	public String mensajeErrorPreloadingTricks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Đã phát hiện xung đột do <b>Preloading Tricks</b> gây ra.</span><br><br>" + "<span style='color:#"
				+ color + "'>" + "Lỗi <i>ClassCastException: String cannot be cast to ModuleDescriptor</i> "
				+ "cho thấy mod đang xử lý sai các lớp trong hệ thống module của Java.</span><br><br>";

		String explicacion = "<span style='color:#" + color + "'>"
				+ "<b>Preloading Tricks</b> là mod chủ yếu dành cho <b>nhà phát triển</b>. "
				+ "Nó thực hiện các thay đổi phức tạp (mixins) rất sớm trong quá trình tải game, "
				+ "và có thể dễ dàng gây mất ổn định khi kết hợp với mod khác.</span><br><br>" + "<span style='color:#"
				+ color + "'><b>Giải pháp đề xuất:</b><br><ul>"
				+ "<li>Gỡ mod <b>Preloading Tricks</b>. Thường không cần thiết khi chơi hoặc dùng modpack ổn định.</li>"
				+ "<li>Nếu bạn là nhà phát triển, hãy kiểm tra lại cấu hình môi trường của bạn.</li>" + "</ul></span>";

		return mensajeBase + explicacion;
	}

	@Override
	public String nombreErrorSimpleRadioLexiconfig() {
		return "Không tương thích Simple Radio / Lexiconfig";
	}

	@Override
	public String mensajeErrorSimpleRadioLexiconfig() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Đã phát hiện xung đột giữa <b>Simple Radio</b> và <b>Lexiconfig</b>.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Lỗi xảy ra trong quá trình 'shelveLexicons', cho thấy sự không tương thích nhị phân giữa hai thư viện.</span><br><br>";

		String solucion = "<span style='color:#" + color + "'><b>Nguyên nhân đã biết:</b><br>"
				+ "Simple Radio thường được thiết kế cho các phiên bản Lexiconfig cũ, trong khi bạn đang dùng phiên bản mới hơn.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>Giải pháp đề xuất:</b><br><ul>"
				+ "<li>Thử sử dụng phiên bản cũ hơn của <b>Lexiconfig</b>.</li>"
				+ "<li>Khuyến nghị dùng phiên bản <b>1.3.11</b> hoặc cũ hơn.</li>"
				+ "<li>Nếu vẫn lỗi, kiểm tra xem có bản cập nhật cho Simple Radio không.</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorMobAITweaks() {
		return "Lỗi Mob AI Tweaks";
	}

	@Override
	public String mensajeErrorMobAITweaks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Đã phát hiện lỗi liên quan đến <b>Mob AI Tweaks</b>.</span><br><br>" + "<span style='color:#" + color
				+ "'>"
				+ "Lỗi xuất phát từ một Mixin (<code>$mob-ai-tweaks$onSpawned</code>) can thiệp khi entity được spawn. "
				+ "Điều này thường cho thấy xung đột với mod khác cũng chỉnh sửa hành vi spawn.</span><br><br>";

		String solucion = "<span style='color:#" + color + "'><b>Giải pháp đề xuất:</b><br><ul>"
				+ "<li>Thử gỡ <b>Mob AI Tweaks</b> để kiểm tra độ ổn định.</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	public String nombre_verificacion_gpu() {
		return "Kiểm tra GPU (OpenGL / lựa chọn GPU)";
	}

	public String desactivar_parche_gpu() {
		return "Tắt kiểm tra GPU";
	}

	// ==================== CRASH ====================

	public String gpu_crash_posible() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Việc kiểm tra GPU có thể đã gây ra việc đóng game.</b>";
	}

	public String gpu_crash_causas() {
		return "Quá trình kiểm tra đã bắt đầu nhưng không hoàn tất. Điều này thường cho thấy lỗi OpenGL hoặc driver GPU.<br><br>"
				+ "Nguyên nhân có thể:<br>" + "- Driver GPU lỗi thời hoặc không ổn định<br>" + "- Vấn đề với OpenGL<br>"
				+ "- GPU cũ hoặc hệ thống GPU lai";
	}

	public String gpu_crash_recomendaciones() {
		return "Khuyến nghị:<br>" + "- Cập nhật driver GPU<br>" + "- Ép sử dụng GPU rời<br>"
				+ "- Tránh môi trường remote hoặc máy ảo";
	}

	// ==================== NO ÓPTIMA ====================

	public String gpu_no_optima() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Game không đang sử dụng GPU tối ưu.</b>";
	}

	public String gpu_no_optima_detalles() {
		return "Điều này có thể làm giảm hiệu năng (FPS thấp), nhưng thường không gây crash.";
	}

	public String gpu_recomendaciones_rendimiento() {
		return "Khuyến nghị:<br>" + "- Ép dùng GPU rời trong control panel<br>"
				+ "- Cấu hình Java/Minecraft ở chế độ hiệu năng cao";
	}

	// ==================== GENERALES ====================

	public String gpu_nota_precision() {
		return "<b>Lưu ý:</b> Hệ thống phát hiện này không hoàn toàn chính xác 100%.";
	}

	public String gpu_consumo_energia() {
		return "GPU mạnh hơn tiêu thụ nhiều điện hơn và có thể làm giảm thời lượng pin laptop.";
	}

	public String gpu_parche_info() {
		return "Bạn có thể tắt kiểm tra này bằng nút sửa nhanh.";
	}

	@Override
	public String nombreVerificacionRaptorLake() {
		return "Cảnh báo ổn định CPU Intel Gen 13/14";
	}

	@Override
	public String advertenciaRaptorLakeTitulo() {
		return "Khả năng không ổn định trên CPU Intel Raptor Lake";
	}

	@Override
	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String targetMicrocode) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Đã phát hiện CPU " + cpu
				+ " với microcode " + microcode + ".</b> "
				+ "CPU Intel thế hệ 13 và 14 có thể gặp vấn đề ổn định do điện áp cao, có thể làm giảm tuổi thọ.<br><br>"
				+ "Khuyến nghị cập nhật BIOS hoặc microcode lên phiên bản chứa <b>" + targetMicrocode
				+ "</b> hoặc mới hơn. " + "<b>Cảnh báo:</b> Cập nhật BIOS có rủi ro nếu thực hiện sai.<br><br>"
				+ "<i>Lưu ý: Đây gần như không phải nguyên nhân crash hiện tại, chỉ là cảnh báo thông tin.</i>";
	}

	@Override
	public String desactivarVerificacionRaptorLake() {
		return "Không hiển thị lại cảnh báo này";
	}

	@Override
	public String verArticuloRaptorLake(String fuente) {
		return "Đọc bài viết tại " + fuente;
	}

	@Override
	public String tituloMixins() {
		return "Trình khám phá Mixins";
	}

	@Override
	public String mixinsBotonLateral() {
		return "Mixins";
	}

	@Override
	public String mixinsRaiz() {
		return "Các mixin đã phát hiện";
	}

	@Override
	public String mixinsTodosLosMods() {
		return "Tất cả";
	}

	@Override
	public String mixinsModConMixin() {
		return "Mod có mixin";
	}

	@Override
	public String mixinsTooltipCombo() {
		return "Lọc theo mod có chứa mixin";
	}

	@Override
	public String mixinsRecargar() {
		return "Tải lại";
	}

	@Override
	public String mixinsDescompilarSeleccion() {
		return "Giải mã (decompile) lựa chọn";
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
		return "Targets của phương thức";
	}

	@Override
	public String mixinsMetodos() {
		return "Phương thức";
	}

	@Override
	public String mixinsCampos() {
		return "Trường";
	}

	@Override
	public String mixinsCantidad() {
		return "Số lượng mixin";
	}

	@Override
	public String mixinsDetalleMixin() {
		return "Chi tiết mixin";
	}

	@Override
	public String mixinsDetalleTarget() {
		return "Chi tiết target";
	}

	@Override
	public String mixinsDetalleMetodo() {
		return "Chi tiết phương thức mixin";
	}

	@Override
	public String mixinsDetalleCampo() {
		return "Chi tiết trường mixin";
	}

	@Override
	public String mixinsDetalleConflicto() {
		return "Chi tiết xung đột";
	}

	@Override
	public String mixinsBuscarConflictos() {
		return "Tìm kiếm xung đột có thể xảy ra";
	}

	@Override
	public String mixinsResultadosConflictos() {
		return "Kết quả xung đột";
	}

	@Override
	public String mixinsConflictosPosibles() {
		return "Các xung đột có thể";
	}

	@Override
	public String mixinsErrorDescompilar() {
		return "Lỗi khi giải mã";
	}

	@Override
	public String mixinsColorPanel() {
		return "Màu nền panel mixin";
	}

	@Override
	public String mixinsColorTexto() {
		return "Màu chữ mixin";
	}

	@Override
	public String mixinsColorTextoSuave() {
		return "Màu chữ phụ của mixin";
	}

	@Override
	public String mixinsAyudaUso1() {
		return "Công cụ này hiển thị các mod sử dụng mixin của SpongePowered và cho phép bạn kiểm tra class, target, phương thức và trường của chúng.";
	}

	@Override
	public String mixinsAyudaUso2() {
		return "Sử dụng bộ chọn phía trên để lọc theo mod cụ thể hoặc hiển thị tất cả mod có mixin.";
	}

	@Override
	public String mixinsAyudaUso3() {
		return "Mở rộng cây để xem từng mixin, class mục tiêu, các phương thức được chú thích và các trường shadow.";
	}

	@Override
	public String mixinsAyudaUso4() {
		return "Nhấp chuột phải vào mod, mixin, target, phương thức hoặc trường để tìm các xung đột với mixin khác.";
	}

	@Override
	public String mixinsAyudaUso5() {
		return "Bạn có thể giải mã lựa chọn hiện tại hoặc kết quả xung đột để kiểm tra mã nguồn liên quan.";
	}

	@Override
	public String mixinsColorComboFondo() {
		return "Màu nền bộ chọn mod";
	}

	@Override
	public String mixinsColorAreaContenidoFondo() {
		return "Màu nền khu vực chi tiết";
	}

	@Override
	public String mixinsColorSeleccionTexto() {
		return "Màu chọn văn bản";
	}

	@Override
	public String mixinsColorSeleccionTextoActivo() {
		return "Màu chữ được chọn";
	}

	@Override
	public String mixinsColorAyudaTexto() {
		return "Màu chữ trợ giúp";
	}

	@Override
	public String mixinsColorArbolFondo() {
		return "Màu nền cây";
	}

	@Override
	public String mixinsColorRendererSeleccionTexto() {
		return "Màu chữ được chọn trong cây";
	}

	@Override
	public String mixinsColorRendererSeleccionFondo() {
		return "Màu nền được chọn trong cây";
	}

	@Override
	public String mixinsColorRendererBordeSeleccion() {
		return "Màu viền chọn trong cây";
	}

	@Override
	public String depmapTitulo() {
		return "Bản đồ phụ thuộc";
	}

	@Override
	public String depmapBotonLateral() {
		return "DepMap";
	}

	@Override
	public String depmapPestanaMapa() {
		return "Bản đồ";
	}

	@Override
	public String depmapPestanaDependientes() {
		return "Phụ thuộc ngược";
	}

	@Override
	public String depmapRecargar() {
		return "Tải lại";
	}

	@Override
	public String depmapDescompilarSeleccion() {
		return "Giải mã (decompile) lựa chọn";
	}

	@Override
	public String depmapVerReferencias() {
		return "Xem tham chiếu";
	}

	@Override
	public String depmapDependencias() {
		return "Phụ thuộc";
	}

	@Override
	public String depmapDependientes() {
		return "Phụ thuộc ngược";
	}

	@Override
	public String depmapDependiente() {
		return "Phụ thuộc ngược";
	}

	@Override
	public String depmapSinDependencias() {
		return "Không có phụ thuộc";
	}

	@Override
	public String depmapSeleccionarMod() {
		return "Chọn mod";
	}

	@Override
	public String depmapSeleccionarModBase() {
		return "Mod cơ sở";
	}

	@Override
	public String depmapSeleccionarDependiente() {
		return "Mod phụ thuộc";
	}

	@Override
	public String depmapSeleccionarPaquete() {
		return "Gói";
	}

	@Override
	public String depmapComprobarNoAlineadas() {
		return "Kiểm tra phụ thuộc không khớp";
	}

	@Override
	public String depmapResultadoNoAlineadas() {
		return "Kết quả phụ thuộc không khớp";
	}

	@Override
	public String depmapClaseInexistente() {
		return "Lớp không tồn tại";
	}

	@Override
	public String depmapClaseReferenciada() {
		return "Lớp được tham chiếu";
	}

	@Override
	public String depmapOrigen() {
		return "Nguồn";
	}

	@Override
	public String depmapDestino() {
		return "Đích";
	}

	@Override
	public String depmapDependenciaDetalle() {
		return "Chi tiết phụ thuộc";
	}

	@Override
	public String depmapReferenciaDetalle() {
		return "Chi tiết tham chiếu";
	}

	@Override
	public String depmapMetodoOrigen() {
		return "Phương thức nguồn";
	}

	@Override
	public String depmapModBase() {
		return "Mod cơ sở";
	}

	@Override
	public String depmapTodos() {
		return "Tất cả";
	}

	@Override
	public String depmapSeleccioneUnMod() {
		return "Chọn một mod";
	}

	@Override
	public String depmapSeleccioneParametrosNoAlineadas() {
		return "Chọn mod cơ sở, mod phụ thuộc và gói";
	}

	@Override
	public String depmapSeleccioneClaseParaDescompilar() {
		return "Chọn một tham chiếu hoặc kết quả để giải mã";
	}

	@Override
	public String depmapErrorDescompilar() {
		return "Lỗi khi giải mã";
	}

	@Override
	public String depmapAyuda1() {
		return "Công cụ này xây dựng bản đồ phụ thuộc giữa các mod dựa trên các tham chiếu lớp giữa chúng.";
	}

	@Override
	public String depmapAyuda2() {
		return "Tab bản đồ hiển thị đồ thị dạng bong bóng với mỗi mod liên kết tới các phụ thuộc mà nó sử dụng.";
	}

	@Override
	public String depmapAyuda3() {
		return "Tab phụ thuộc ngược sắp xếp các mod từ nhiều phụ thuộc ngược nhất đến không có phụ thuộc nào.";
	}

	@Override
	public String depmapAyuda4() {
		return "Bạn có thể kiểm tra các tham chiếu cụ thể giữa một mod và các phụ thuộc của nó, cũng như giải mã các lớp liên quan.";
	}

	@Override
	public String depmapAyuda5() {
		return "Kiểm tra phụ thuộc không khớp sẽ tìm các tham chiếu đến lớp không tồn tại trong một gói hoặc gói con của mod cơ sở.";
	}

	@Override
	public String depmapColorPanel() {
		return "Màu panel";
	}

	@Override
	public String depmapColorTexto() {
		return "Màu chữ chính";
	}

	@Override
	public String depmapColorTextoSecundario() {
		return "Màu chữ phụ";
	}

	@Override
	public String depmapColorAyudaTexto() {
		return "Màu chữ trợ giúp";
	}

	@Override
	public String depmapColorGrafoFondo() {
		return "Màu nền đồ thị";
	}

	@Override
	public String depmapColorListaFondo() {
		return "Màu nền danh sách";
	}

	@Override
	public String depmapColorArbolFondo() {
		return "Màu nền cây";
	}

	@Override
	public String depmapColorNodo() {
		return "Màu nút đồ thị";
	}

	@Override
	public String depmapColorEnlace() {
		return "Màu liên kết đồ thị";
	}

	@Override
	public String depmapColorSeleccion() {
		return "Màu chọn";
	}

	@Override
	public String depmapColorSeleccionTexto() {
		return "Màu chữ được chọn";
	}

	@Override
	public String nombreProblemaAzureLibAnimaciones() {
		return "Sự cố với addon AzureLib";
	}

	@Override
	public String mensajeProblemaAzureLibAnimaciones() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi AzureLib khi tải animation.</b>"
				+ "<p>Trò chơi phát hiện một tệp JSON animation có định dạng không hợp lệ.</p>"
				+ "<p>Vấn đề này thường do một mod hoặc addon sử dụng <b>AzureLib</b> gây ra.</p>"
				+ "<p><b>Khuyến nghị:</b></p><ul>"
				+ "<li>Sử dụng <b>DepMap</b> ở thanh bên để tìm các addon phụ thuộc vào AzureLib.</li>"
				+ "<li>Thử chạy game mà không có một số addon đó để xác định mod gây lỗi.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaDecocraftNatureEssentialPartnerMod() {
		return "Sự cố với Decocraft Nature";
	}

	@Override
	public String mensajeProblemaDecocraftNatureEssentialPartnerMod() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện sự cố do Decocraft Nature gây ra.</b>" + "<p>Lỗi xảy ra khi khởi tạo mixin config "
				+ "<code>com/razz/essentialpartnermod/mixins.json</code>.</p>"
				+ "<p>Vấn đề này có thể được khắc phục bằng cách chỉnh sửa tệp JAR của mod.</p>"
				+ "<p><b>Các bước:</b></p><ul>"
				+ "<li>Mở tệp JAR bằng công cụ như File Roller, Ark, WinRAR, 7-Zip hoặc WinZip.</li>"
				+ "<li>Truy cập <code>META-INF/MANIFEST.MF</code>.</li>" + "<li>Xóa dòng sau:</li></ul>"
				+ "<code>MixinConfigs: com/razz/essentialpartnermod/mixins.json</code>"
				+ "<p><b>Lưu ý:</b> giữ lại dòng trống duy nhất ở cuối tệp.</p>";
	}

	@Override
	public String mensajeTetraDeserializadorModeloEstatico() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi liên quan đến Tetra hoặc một addon của nó.</b>"
				+ "<p>Log cho thấy không tìm thấy deserializer cho một loại model được sử dụng bởi <b>Tetra</b> hoặc addon của nó.</p>"
				+ "<p><b>Các bước đề xuất:</b></p><ul>"
				+ "<li>Trước tiên, hãy tắt hoặc xóa các <b>addon của Tetra</b> và thử lại.</li>"
				+ "<li>Nếu vẫn lỗi, thử gỡ luôn <b>Tetra</b>.</li>"
				+ "<li>Có thể dùng <b>DepMap</b> để tìm addon liên quan, dù không phải lúc nào cũng hiển thị.</li>"
				+ "</ul>" + "<p>Trong một số trường hợp lỗi do addon, nhưng cũng có thể do chính <b>Tetra</b>.</p>";
	}

	@Override
	public String nombreTetraDeserializadorModeloEstatico() {
		return "Lỗi giải mã model trong Tetra";
	}

	@Override
	public String mensajeSimpleEmotesSetupAnimTail() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện lỗi liên quan đến Simple Emotes.</b>"
				+ "<p>Log chứa chuỗi <b>$simpleemotes$setupAnimTAIL</b>, cho thấy lỗi trực tiếp từ mod <b>Simple Emotes</b>.</p>"
				+ "<p><b>Tùy chọn đề xuất:</b></p><ul>" + "<li>Gỡ hoặc tắt <b>Simple Emotes</b> và thử lại.</li>"
				+ "<li>Nếu dùng mod thay đổi animation, kiểm tra xung đột với <b>Simple Emotes</b>.</li>"
				+ "<li>Cập nhật <b>Simple Emotes</b> và các mod liên quan lên phiên bản tương thích.</li>" + "</ul>"
				+ "<p>Lỗi này thường cho thấy <b>Simple Emotes</b> là nguyên nhân trực tiếp.</p>";
	}

	@Override
	public String nombreSimpleEmotesSetupAnimTail() {
		return "Lỗi trong Simple Emotes";
	}

	// En Idioma

	@Override
	public String mensajeAdvertenciaSKLauncher() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Cảnh báo về SKLauncher.</b>"
				+ "<p>Trong những tháng gần đây đã ghi nhận nhiều trường hợp <b>lỗi dữ liệu</b> và các vấn đề liên quan đến <b>SKLauncher</b>.</p>"
				+ "<p>Điều này không có nghĩa SKLauncher luôn là nguyên nhân gây lỗi, nhưng nó có thể góp phần gây ra sự cố.</p>"
				+ "<p><b>Dấu hiệu có thể bị lỗi dữ liệu:</b></p><ul>" + "<li>Game bị đóng rất sớm khi khởi động.</li>"
				+ "<li>Game vẫn bị lỗi ngay cả khi <b>không cài mod</b>.</li>" + "</ul>"
				+ "<p>Nếu gặp các trường hợp trên, hãy thử sử dụng <b>launcher khác</b> để kiểm tra xem lỗi có biến mất không.</p>"
				+ "<p>Xem danh sách launcher được khuyến nghị tại đây:</p>"
				+ "<p><a href='https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/docs/ingles/minecraft/Launchers.md'>Xem tài liệu launcher</a></p>";
	}

	@Override
	public String nombreAdvertenciaSKLauncher() {
		return "Cảnh báo: có thể có vấn đề với SKLauncher";
	}

	@Override
	public String guardTitulo() {
		return "CD Guard";
	}

	@Override
	public String guardBotonLateral() {
		return "Guard";
	}

	@Override
	public String guardEscanearTodo() {
		return "Quét máy chủ và malware";
	}

	@Override
	public String guardEscanearServidores() {
		return "Quét máy chủ";
	}

	@Override
	public String guardEscanearMalware() {
		return "Quét malware";
	}

	@Override
	public String guardTablaServidores() {
		return "Máy chủ có vấn đề";
	}

	@Override
	public String guardTablaMalware() {
		return "Phát hiện malware";
	}

	@Override
	public String guardColServidor() {
		return "Máy chủ";
	}

	@Override
	public String guardColDefinicion() {
		return "Định nghĩa";
	}

	@Override
	public String guardColMensaje() {
		return "Thông báo";
	}

	@Override
	public String guardColUbicacion() {
		return "Vị trí";
	}

	@Override
	public String guardColClase() {
		return "Lớp";
	}

	@Override
	public String guardColCfr() {
		return "CFR";
	}

	@Override
	public String guardCfr() {
		return "Giải mã";
	}

	@Override
	public String guardCfrTitulo() {
		return "Mã đã giải mã";
	}

	@Override
	public String guardDescripcion1() {
		return "Công cụ này cho phép tìm kiếm các máy chủ có vấn đề và phát hiện malware tiềm ẩn trong các mod.";
	}

	@Override
	public String guardDescripcion2() {
		return "Có thể xuất hiện cảnh báo sai (false positive), đặc biệt khi các định nghĩa hoặc công cụ quét malware quá nhạy.";
	}

	@Override
	public String guardDescripcion3() {
		return "Việc kiểm tra máy chủ sử dụng các định nghĩa bên ngoài. Nếu chưa tải, bạn cần tải xuống trước.";
	}

	@Override
	public String guardDescripcion4() {
		return "Nếu bạn đã có định nghĩa cục bộ, công cụ sẽ cho phép bạn chọn sử dụng lại hoặc cập nhật chúng.";
	}

	@Override
	public String guardDescripcion5() {
		return "Trong bảng malware, nếu có lớp khả dụng, bạn có thể giải mã bằng CFR để kiểm tra.";
	}

	@Override
	public String guardEstadoEscaneandoTodo() {
		return "Đang quét máy chủ và malware...";
	}

	@Override
	public String guardEstadoEscaneandoServidores() {
		return "Đang tìm máy chủ có vấn đề...";
	}

	@Override
	public String guardEstadoEscaneandoMalware() {
		return "Đang tìm malware...";
	}

	@Override
	public String guardEstadoListo() {
		return "Sẵn sàng";
	}

	@Override
	public String guardDefsNoEncontradasTitulo() {
		return "Không tìm thấy định nghĩa";
	}

	@Override
	public String guardDefsNoEncontradasMensaje() {
		return "Không tìm thấy định nghĩa máy chủ. Bạn có muốn tải xuống ngay không?";
	}

	@Override
	public String guardDefsDescargar() {
		return "Tải xuống";
	}

	@Override
	public String guardDefsCancelar() {
		return "Hủy";
	}

	@Override
	public String guardDefsActualizarTitulo() {
		return "Định nghĩa máy chủ";
	}

	@Override
	public String guardDefsActualizarMensaje() {
		return "Đã có định nghĩa cục bộ. Bạn muốn sử dụng hay cập nhật chúng?";
	}

	@Override
	public String guardDefsUsarLocales() {
		return "Sử dụng bản cục bộ";
	}

	@Override
	public String guardDefsActualizar() {
		return "Cập nhật";
	}

	@Override
	public String guardFuenteDefinicionesTLauncher() {
		return "Danh sách TLauncher";
	}

	@Override
	public String guardErrorDescompilar() {
		return "Lỗi khi giải mã";
	}

	@Override
	public String guardColorPanel() {
		return "Màu panel";
	}

	@Override
	public String guardColorTexto() {
		return "Màu chữ";
	}

	@Override
	public String guardColorTextoSecundario() {
		return "Màu chữ phụ";
	}

	@Override
	public String guardColorTabla() {
		return "Màu bảng";
	}

	@Override
	public String guardColorSeleccion() {
		return "Màu chọn";
	}

	@Override
	public String guardColorSeleccionTexto() {
		return "Màu chữ được chọn";
	}

	@Override
	public String texto_de_boton_compartir_instancia_modpack() {
		return "Chia sẻ instance/modpack";
	}

	@Override
	public String popup_compartir_instancia_modpack() {
		return "Chức năng chia sẻ instance hoặc modpack vẫn chưa được triển khai.";
	}

	@Override
	public String colorBotonCompartirVerdeOscuro() {
		return "Màu nút chia sẻ chính";
	}

	@Override
	public String colorBotonCompartirVerdeClaro() {
		return "Màu nút liên kết chia sẻ";
	}

	@Override
	public String colorTextoBotonesCompartir() {
		return "Màu chữ của các nút chia sẻ";
	}

	@Override
	public String compartirInstanciaTitulo() {
		return "Chia sẻ instance";
	}

	@Override
	public String compartirInstanciaBotonLateral() {
		return "Chia sẻ instance";
	}

	@Override
	public String compartirInstanciaFormato() {
		return "Định dạng";
	}

	@Override
	public String compartirInstanciaServicio() {
		return "Dịch vụ tải lên";
	}

	@Override
	public String compartirInstanciaBotonCompartir() {
		return "Đóng gói và chia sẻ";
	}

	@Override
	public String compartirInstanciaBotonRefrescar() {
		return "Làm mới";
	}

	@Override
	public String compartirInstanciaEstadoListo() {
		return "Sẵn sàng";
	}

	@Override
	public String compartirInstanciaEstadoEmpaquetando() {
		return "Đang đóng gói...";
	}

	@Override
	public String compartirInstanciaEstadoSubiendo() {
		return "Đang tải tệp lên...";
	}

	@Override
	public String compartirInstanciaEstadoError() {
		return "Lỗi";
	}

	@Override
	public String compartirInstanciaCodigo() {
		return "Mã";
	}

	@Override
	public String compartirInstanciaEnlace() {
		return "Liên kết";
	}

	@Override
	public String compartirInstanciaMantenerAbierto() {
		return "Bạn cần giữ ứng dụng mở để quá trình truyền tiếp tục hoạt động.";
	}

	@Override
	public String compartirInstanciaSinSeleccion() {
		return "Không có thư mục hoặc tệp nào được chọn.";
	}

	@Override
	public String compartirInstanciaFormatoNoSoportado() {
		return "Định dạng này chưa được hỗ trợ.";
	}

	@Override
	public String compartirInstanciaServicioNoDisponible() {
		return "Dịch vụ đã chọn không khả dụng.";
	}

	@Override
	public String compartirInstanciaSubidaCompleta() {
		return "Bắt đầu truyền thành công.";
	}

	@Override
	public String compartirInstanciaErrorSubir() {
		return "Không thể tải lên tệp đã chọn.";
	}

	@Override
	public String compartirInstanciaColorPanel() {
		return "Màu panel";
	}

	@Override
	public String compartirInstanciaColorTexto() {
		return "Màu chữ";
	}

	@Override
	public String compartirInstanciaPolitica1() {
		return "Các loại được khuyến nghị: mods, configs, saves, worlds, datapacks, resource packs và tệp cấu hình. Tránh bao gồm dữ liệu riêng tư không cần thiết.";
	}

	@Override
	public String compartirInstanciaPolitica2() {
		return "Các tiện ích mở rộng có thể thêm dịch vụ tải lên riêng. Các dịch vụ tích hợp mặc định nên được hiển thị tại đây.";
	}

	@Override
	public String compartirInstanciaPolitica3() {
		return "wormhole.app: tối đa 5 GiB cho tải lên thông thường; từ 5 đến 10 GiB yêu cầu giữ ứng dụng gửi luôn mở. Trong triển khai hiện tại, tích hợp thực tế vẫn chưa hoàn tất.";
	}

	@Override
	public String compartirInstanciaPolitica4() {
		return "limewire.com: được thiết kế như dịch vụ lưu trữ tạm thời. Hiện chưa được hỗ trợ trong triển khai này.";
	}

	@Override
	public String compartirInstanciaPolitica5() {
		return "bittorrent: phương thức an toàn hơn nhờ phân phối P2P trực tiếp, không có máy chủ trung tâm. Hiện chưa được hỗ trợ trong triển khai này.";
	}

	@Override
	public String compartirInstanciaPolitica6() {
		return "Mặc định, các thư mục và tệp phổ biến nhất của instance được chọn để hỗ trợ kỹ thuật dễ dàng hơn.";
	}

	@Override
	public String compartirInstanciaPolitica7() {
		return "Nếu bạn bao gồm thư mục nội bộ của " + Statics.nombre_cd.obtener()
				+ ", các cấu hình, nhật ký và dữ liệu phụ trợ cũng sẽ được gửi đi, vì vậy bạn có thể bỏ chọn nếu không cần.";
	}

	@Override
	public String malware_fracturiser_detectado() {
		return "Phát hiện Fracturiser khả nghi. Bằng chứng:";
	}

	@Override
	public String malware_info_stealer_detectado() {
		return "Phát hiện phần mềm đánh cắp thông tin khả nghi. Bằng chứng:";
	}

	@Override
	public String malware_evidencia_clase_sospechosa() {
		return "Lớp đáng ngờ:";
	}

	@Override
	public String malware_evidencia_archivo_sospechoso() {
		return "Tệp đáng ngờ:";
	}

	@Override
	public String malware_brightsdk_detectado() {
		return "Phát hiện Bright SDK khả nghi. Bằng chứng:";
	}

	@Override
	public String malware_evidencia_paquete_sospechoso() {
		return "Gói đáng ngờ:";
	}

	@Override
	public String docsTituloVentana() {
		return "Trình đọc tài liệu";
	}

	@Override
	public String docsAyudaDeUso() {
		return "<b>Cách sử dụng trình đọc</b><br>" + "Chọn ngôn ngữ ở phía dưới để xem tài liệu tương ứng.<br>"
				+ "Ở bảng bên trái, bạn có thể duyệt các thư mục và tài liệu.<br>"
				+ "Khi nhấp vào một tài liệu, nội dung sẽ hiển thị ở bên phải.<br>"
				+ "Các liên kết nội bộ với giao thức <b>docs://</b> sẽ mở tài liệu khác trong trình đọc này.";
	}

	@Override
	public String docsArbolTitulo() {
		return "Tài liệu";
	}

	@Override
	public String docsVisorTitulo() {
		return "Nội dung";
	}

	@Override
	public String docsNoHayDocumentos() {
		return "Không có tài liệu cho ngôn ngữ này.";
	}

	@Override
	public String docsNoHayMarkdown() {
		return "Không tìm thấy tệp Markdown cho ngôn ngữ này.";
	}

	@Override
	public String docsDocumentoNoEncontrado() {
		return "Không tìm thấy tài liệu yêu cầu.";
	}

	@Override
	public String docsErrorAlAbrirDocumento() {
		return "Lỗi khi mở tài liệu:";
	}

	@Override
	public String docsCargando() {
		return "Đang tải tài liệu...";
	}

	@Override
	public String docsImagenNoDisponible() {
		return "Hình ảnh không khả dụng";
	}

	@Override
	public String colorPanelSecundario() {
		return "Màu panel phụ";
	}

	@Override
	public String colorTextoSuave() {
		return "Màu chữ nhẹ";
	}

	@Override
	public String colorSeleccion() {
		return "Màu chọn";
	}

	@Override
	public String colorFondoDocumento() {
		return "Màu nền tài liệu";
	}

	@Override
	public String iaTituloVentana() {
		return "AI";
	}

	@Override
	public String iaTituloPrincipal() {
		return "Phân tích bằng AI";
	}

	@Override
	public String iaDescripcionTitulo() {
		return "Tác nhân phân tích lỗi";
	}

	@Override
	public String iaDescripcionCuerpo() {
		return "Công cụ này mở một tác nhân bên ngoài có thể giúp bạn phân tích crash, lỗi và nhật ký liên quan đến Minecraft.";
	}

	@Override
	public String iaDescripcionUso() {
		return "Để sử dụng, hãy mở liên kết, đăng nhập bằng tài khoản Baidu, sau đó dùng tác nhân để phân tích crash hoặc log của bạn.";
	}

	@Override
	public String iaAvisoCuentaBaidu() {
		return "Bạn cần tài khoản Baidu để truy cập tác nhân.";
	}

	@Override
	public String iaCopiarEnlace() {
		return "Sao chép liên kết";
	}

	@Override
	public String iaAbrirEnlace() {
		return "Mở liên kết";
	}

	@Override
	public String iaImagenNoDisponible() {
		return "Hình ảnh không khả dụng";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "Lỗi khi thực thi tác vụ";
	}

	public String recomendacion_fallos_ejecucion() {
		return "Loại lỗi này thường xảy ra do sự không tương thích giữa các mod. "
				+ "Đặc biệt phổ biến với các mod không hoạt động đúng với ConnectorMod.";
	}

	@Override
	public String mensajeOculusIrisUnknownShaderVariable() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện lỗi shader có thể xảy ra với Oculus hoặc Iris.</b>"
				+ "<p>Nhật ký lỗi (log) chứa cả <b>kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal</b> "
				+ "và <b>java.lang.RuntimeException: Unknown variable:</b>.</p>"
				+ "<p>Sự kết hợp này thường cho thấy vấn đề khi đánh giá một biến trong shader, "
				+ "thường liên quan đến <b>Oculus</b>, <b>Iris</b> hoặc <b>gói shader (shader pack)</b> đang sử dụng.</p>"
				+ "<p><b>Thứ tự khắc phục được khuyến nghị:</b></p>" + "<ul>"
				+ "<li>Trước tiên, hãy khởi động game <b>mà không bật shader</b>.</li>"
				+ "<li>Nếu vấn đề vẫn tiếp diễn, hãy thử khởi động <b>mà không có Oculus hoặc Iris</b>.</li>"
				+ "<li>Cập nhật <b>Oculus/Iris</b>, <b>gói shader</b> và các mod đồ họa liên quan.</li>"
				+ "<li>Nếu bạn dùng các mod render hoặc đồ họa khác, hãy kiểm tra xung đột giữa chúng.</li>" + "</ul>"
				+ "<p>Trong thực tế, lỗi này thường đến từ <b>gói shader</b> hoặc tương tác của nó với <b>Oculus/Iris</b>.</p>";
	}

	@Override
	public String nombreOculusIrisUnknownShaderVariable() {
		return "Lỗi shader có thể xảy ra với Oculus/Iris";
	}

	@Override
	public String mensajeItemNoExiste(String itemFaltante, String namespace) {
		String itemHtml = itemFaltante == null || itemFaltante.isEmpty() ? "(không xác định)" : itemFaltante;
		String namespaceHtml = namespace == null || namespace.isEmpty() ? "(không xác định)" : namespace;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã cố gắng sử dụng một vật phẩm không tồn tại.</b>" + "<p>Nhật ký chứa dòng <b>Item: " + itemHtml
				+ " does not exist</b>.</p>"
				+ "<p>Điều này thường có nghĩa là một <b>datapack</b>, <b>mod</b> hoặc <b>cấu hình</b> nào đó "
				+ "đang tham chiếu đến một vật phẩm không có trong trò chơi.</p>"
				+ "<p><b>Những điều cần kiểm tra:</b></p>" + "<ul>"
				+ "<li>Kiểm tra xem bạn đã cài đặt mod cung cấp vật phẩm <b>" + itemHtml + "</b> chưa.</li>"
				+ "<li>Nếu đã có, có thể đó là <b>phiên bản sai</b>, vật phẩm đã bị thay đổi hoặc xóa, "
				+ "hoặc mod bị lỗi và nên gỡ bỏ.</li>" + "<li>Nếu bạn không có mod đó, hãy thử <b>cài đặt nó</b>.</li>"
				+ "</ul>" + "<p><b>Để tìm ra mod hoặc datapack nào đang yêu cầu vật phẩm đó:</b></p>" + "<ul>"
				+ "<li>Sử dụng công cụ <b>grepr</b> ở thanh bên.</li>" + "<li><b>Không</b> chọn thư mục.</li>"
				+ "<li>Bật tùy chọn <b>search in archives</b>.</li>"
				+ "<li>Trong ô tìm kiếm, nhập <b>namespace</b>, tức là phần trước dấu hai chấm: " + "<b>"
				+ namespaceHtml + "</b>.</li>" + "</ul>"
				+ "<p>Điều này thường giúp tìm ra tệp, mod hoặc datapack đang tạo tham chiếu không hợp lệ.</p>";
	}

	@Override
	public String nombreItemNoExiste() {
		return "Tham chiếu đến vật phẩm không tồn tại";
	}

	@Override
	public String mensajeCobblemonPinkanIslandsRhyhornModelo() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện lỗi mô hình cho Rhyhorn.</b>"
				+ "<p>Nhật ký chứa dòng <b>Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn</b>.</p>"
				+ "<p>Mặc dù mô hình sử dụng namespace của <b>Cobblemon</b>, nhưng dòng này thường do mod "
				+ "<b>Cobblemon: Pinkan Islands</b> gây ra, nơi xuất phát của <b>Rhyhorn</b> này.</p>"
				+ "<p><b>Những điều nên thử:</b></p>" + "<ul>"
				+ "<li>Gỡ bỏ hoặc vô hiệu hóa <b>Cobblemon: Pinkan Islands</b> và thử lại.</li>"
				+ "<li>Cập nhật <b>Cobblemon</b> và <b>Cobblemon: Pinkan Islands</b> lên các phiên bản tương thích với nhau.</li>"
				+ "<li>Nếu vấn đề bắt đầu sau khi cập nhật một trong các mod đó, hãy thử một bộ phiên bản khác.</li>"
				+ "</ul>" + "<p>Lỗi này thường chỉ ra một mô hình bị thiếu, đăng ký sai hoặc không tương thích trong "
				+ "<b>Cobblemon: Pinkan Islands</b>.</p>";
	}

	@Override
	public String nombreCobblemonPinkanIslandsRhyhornModelo() {
		return "Lỗi mô hình Rhyhorn trong Cobblemon: Pinkan Islands";
	}

	@Override
	public String mensajeColdSweatInitDynamicTags() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện lỗi trong Cold Sweat.</b>"
				+ "<p>Nhật ký chứa các dấu hiệu như <b>$cold_sweat$onBuildStart</b>, "
				+ "<b>InitDynamicTagsEvent.fillTag</b> và một <b>NullPointerException</b> nơi "
				+ "registry xuất hiện là null.</p>"
				+ "<p>Điều này thường chỉ ra vấn đề của <b>Cold Sweat</b> khi xây dựng hoặc điền "
				+ "<b>tags động</b>, thường do không tương thích, lỗi nội bộ của mod "
				+ "hoặc sự kết hợp xung đột với mod hoặc datapack khác.</p>" + "<p><b>Những điều nên thử:</b></p>"
				+ "<ul>" + "<li>Gỡ bỏ hoặc vô hiệu hóa <b>Cold Sweat</b> và thử lại.</li>"
				+ "<li>Cập nhật <b>Cold Sweat</b> lên phiên bản tương thích với phiên bản Minecraft và loader của bạn.</li>"
				+ "<li>Nếu bạn dùng datapacks hoặc mod thay đổi <b>tags</b>, <b>quần xã (biomes)</b>, <b>nhiệt độ</b> hoặc các registry liên quan, hãy kiểm tra chúng.</li>"
				+ "<li>Nếu lỗi bắt đầu sau khi cập nhật mod, hãy thử một bộ phiên bản khác.</li>" + "</ul>"
				+ "<p>Trong trường hợp này, <b>Cold Sweat</b> liên quan trực tiếp đến sự cố.</p>";
	}

	@Override
	public String nombreColdSweatInitDynamicTags() {
		return "Lỗi Cold Sweat trong tags động";
	}

	@Override
	public String mensajeClassCastExceptionGeneral(String lineaClassCast) {
		String detalle = lineaClassCast == null || lineaClassCast.isEmpty() ? ""
				: "<p><b>Dòng được phát hiện:</b></p><p><code>" + lineaClassCast + "</code></p>";

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Đã phát hiện ClassCastException.</b>"
				+ "<p>Điều này có nghĩa là một lớp đã được xử lý như thể nó là một lớp hoặc giao diện khác không tương thích.</p>"
				+ detalle + "<p>Loại lỗi này thường do một trong các tình huống sau gây ra:</p>" + "<ul>"
				+ "<li><b>Hai mod không tương thích</b> với nhau.</li>"
				+ "<li><b>Mixins</b>, <b>transformers</b> hoặc các bản vá thay đổi một lớp và khiến một phần khác của game mong đợi một kiểu dữ liệu khác.</li>"
				+ "<li>Các mod khác xuất hiện trong <b>stacktrace</b> đang gây ra việc ép kiểu sai.</li>" + "</ul>"
				+ "<p><b>Những điều cần kiểm tra:</b></p>" + "<ul>"
				+ "<li>Xem các dòng trong <b>stacktrace</b> liên quan đến lỗi này.</li>"
				+ "<li>Đặc biệt chú ý đến tên mod hoặc lớp có định dạng <b>$modid$algo</b>, vì chúng thường chỉ ra các mod liên quan.</li>"
				+ "<li>Thử cập nhật, gỡ bỏ hoặc tách riêng các mod có vẻ liên quan đến việc ép kiểu không hợp lệ.</li>"
				+ "</ul>"
				+ "<p>Mặc dù <b>ClassCastException</b> không phải lúc nào cũng gây crash, nhưng rất thường xuyên là có.</p>";
	}

	@Override
	public String nombreClassCastExceptionGeneral() {
		return "Đã phát hiện ClassCastException";
	}

	@Override
	public String mensajeValkyrienSkiesTournamentLithiumPoiInjection() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện khả năng xung đột giữa Valkyrien Skies Tournament và Lithium/Radium.</b>"
				+ "<p>Nhật ký chứa <b>InvalidInjectionException</b> nơi mixin của "
				+ "<b>Lithium</b> liên quan đến <b>POI</b> xuất hiện cùng với <b>valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager</b>.</p>"
				+ "<p>Lỗi này thường xảy ra khi sử dụng <b>Lithium phiên bản cũ</b> hoặc một "
				+ "<b>fork dựa trên Lithium cũ</b>, chẳng hạn như <b>Radium Reforged</b>, cùng với "
				+ "<b>VS Tournament</b>.</p>" + "<p><b>Những điều nên thử:</b></p>" + "<ul>"
				+ "<li>Cập nhật <b>Lithium</b> lên phiên bản mới hơn và tương thích.</li>"
				+ "<li>Nếu bạn đang dùng <b>Forge/NeoForge</b> và sử dụng <b>Radium Reforged</b> hoặc fork cũ khác, hãy gỡ bỏ nó.</li>"
				+ "<li>Thay vào đó, hãy thử <b>Harium</b>, một fork hiện đại của Radium được đồng bộ với các cải tiến gần đây của Lithium.</li>"
				+ "<li>Nếu lỗi bắt đầu sau khi cập nhật mod, hãy kiểm tra kỹ bộ phiên bản giữa <b>VS Tournament</b> và mod tối ưu AI/POI của bạn.</li>"
				+ "</ul>"
				+ "<p>Trên thực tế, lỗi này thường đến từ việc triển khai <b>Lithium/Radium</b> phiên bản cũ không tương thích tốt với <b>VS Tournament</b>.</p>";
	}

	@Override
	public String nombreValkyrienSkiesTournamentLithiumPoiInjection() {
		return "Xung đột giữa VS Tournament và Lithium/Radium";
	}

	@Override
	public String mensajeVSTournamentVSConfigClassNoExiste() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Có vẻ như VS Tournament quá cũ so với phiên bản Valkyrien Skies của bạn.</b>"
				+ "<p>Nhật ký chứa <b>NoClassDefFoundError</b> cho "
				+ "<b>org/valkyrienskies/core/impl/config/VSConfigClass</b> và cả một dòng từ "
				+ "<b>org.valkyrienskies.tournament.TournamentMod.init(...)</b>.</p>"
				+ "<p>Điều này thường có nghĩa là bạn đang dùng <b>phiên bản cũ của VS Tournament</b> cố gắng "
				+ "sử dụng các lớp nội bộ cũ của <b>Valkyrien Skies</b> đã không còn tồn tại.</p>"
				+ "<p><b>Việc cần làm:</b></p>" + "<ul>" + "<li>Gỡ bỏ <b>VS Tournament</b> phiên bản cũ.</li>"
				+ "<li>Thay vào đó, hãy dùng <b>VS Tournament Reforged</b>.</li>"
				+ "<li>Ngoài ra, hãy kiểm tra xem phiên bản <b>Valkyrien Skies</b> có khớp với phiên bản được addon hỗ trợ không.</li>"
				+ "</ul>"
				+ "<p>Khuyến nghị chuyển sang <b>VS Tournament Reforged</b> phù hợp với trạng thái hiện tại của dự án: "
				+ "phiên bản gốc của Tournament vẫn được liệt kê là mod cũ cho 1.18.2, trong khi "
				+ "<b>VS Tournament Reforged</b> được phát hành riêng và hiện thông báo hỗ trợ Valkyrien "
				+ "2.4.9+ trên Forge 1.20.1.</p>";
	}

	@Override
	public String nombreVSTournamentVSConfigClassNoExiste() {
		return "VS Tournament cũ không tương thích với Valkyrien Skies";
	}

	public String curseForgeClaveApiMundial() {
		return "Khóa API Toàn cầu của CurseForge";
	}

	public String curseForgeEndpoint() {
		return "Endpoint của CurseForge";
	}

	public String tlmodsEndpoint() {
		return "Endpoint của TLMods";
	}

	public String minecraftStorageEndpoint() {
		return "Endpoint của MinecraftStorage";
	}

	public String autoBackupActivado() {
		return "Đã bật sao lưu tự động";
	}

	public String autoBackupFrecuencia() {
		return "Tần suất sao lưu tự động";
	}

	public String autoBackupDiasConservar() {
		return "Số ngày lưu giữ bản sao lưu tự động";
	}

	public String autoBackupTamanoMaximoMB() {
		return "Kích thước tối đa của bản sao lưu tự động (MB)";
	}

	public String actualizadorModsTitulo() {
		return "Trình cập nhật mod";
	}

	public String actualizadorModsBotonSidebar() {
		return "Cập nhật";
	}

	public String actualizadorModsDescripcion() {
		return "Tìm kiếm bản cập nhật có sẵn cho các mod trong modpack. Bạn có thể cập nhật tất cả hoặc áp dụng từng bản riêng lẻ.";
	}

	public String actualizadorModsBotonEscanear() {
		return "Tìm bản cập nhật";
	}

	public String actualizadorModsBotonActualizarTodo() {
		return "Cập nhật tất cả";
	}

	public String actualizadorModsBotonActualizarUno() {
		return "Cập nhật";
	}

	public String actualizadorModsEstadoListo() {
		return "Sẵn sàng";
	}

	public String actualizadorModsEstadoEscaneando() {
		return "Đang tìm bản cập nhật...";
	}

	public String actualizadorModsEstadoActualizando() {
		return "Đang cập nhật...";
	}

	public String actualizadorModsEstadoSinActualizaciones() {
		return "Không có bản cập nhật mới.";
	}

	public String actualizadorModsEstadoEncontradas(int n) {
		return "Tìm thấy bản cập nhật: " + n;
	}

	public String actualizadorModsEstadoActualizadas(int n) {
		return "Đã áp dụng bản cập nhật: " + n;
	}

	public String actualizadorModsEstadoError() {
		return "Lỗi khi cập nhật.";
	}

	public String actualizadorModsSinSeleccion() {
		return "Chưa chọn bản cập nhật nào.";
	}

	public String actualizadorModsColumnaMod() {
		return "Mod";
	}

	public String actualizadorModsColumnaActual() {
		return "Hiện tại";
	}

	public String actualizadorModsColumnaNueva() {
		return "Mới";
	}

	public String actualizadorModsColumnaFuente() {
		return "Nguồn";
	}

	public String actualizadorModsColumnaLoader() {
		return "Loader";
	}

	public String actualizadorModsColumnaRuta() {
		return "Đường dẫn";
	}

	public String actualizadorModsColumnaAccion() {
		return "Hành động";
	}

	public String actualizadorModsColorFondo() {
		return "Trình cập nhật: nền";
	}

	public String actualizadorModsColorPanel() {
		return "Trình cập nhật: bảng";
	}

	public String actualizadorModsColorTexto() {
		return "Trình cập nhật: văn bản";
	}

	public String actualizadorModsColorTextoSuave() {
		return "Trình cập nhật: văn bản nhạt";
	}

	public String actualizadorModsColorBoton() {
		return "Trình cập nhật: nút";
	}

	public String actualizadorModsColorBotonTexto() {
		return "Trình cập nhật: văn bản nút";
	}

	public String actualizadorModsColorTabla() {
		return "Trình cập nhật: bảng";
	}

	public String actualizadorModsColorSeleccion() {
		return "Trình cập nhật: lựa chọn";
	}

	public String importadorYumeiriTeExtraniamos() {
		return "Chúng mình nhớ cậu, Yumeiri Reyu.";
	}

	public String importadorColorFondo() {
		return "Nhập liệu: nền";
	}

	public String importadorColorPanel() {
		return "Nhập liệu: bảng";
	}

	public String importadorColorTexto() {
		return "Nhập liệu: văn bản";
	}

	public String importadorColorTextoSuave() {
		return "Nhập liệu: văn bản nhạt";
	}

	public String importadorColorBoton() {
		return "Nhập liệu: nút";
	}

	public String importadorColorBotonTexto() {
		return "Nhập liệu: văn bản nút";
	}

	public String importadorColorBorde() {
		return "Nhập liệu: viền";
	}

	public String importadorConflictoTitulo() {
		return "Xung đột khi nhập";
	}

	public String importadorConflictoMensaje() {
		return "Đã tồn tại một tệp trong thư mục đích.";
	}

	public String importadorRuta() {
		return "Đường dẫn";
	}

	public String importadorArchivoExistente() {
		return "Tệp hiện có";
	}

	public String importadorArchivoNuevo() {
		return "Tệp đã nhập";
	}

	public String importadorTamano() {
		return "Kích thước";
	}

	public String importadorFecha() {
		return "Sửa đổi lần cuối";
	}

	public String importadorInfoMod() {
		return "Thông tin mod";
	}

	public String importadorModImportadoMasNuevo() {
		return "Mod được nhập có vẻ mới hơn.";
	}

	public String importadorModImportadoMasViejo() {
		return "Mod được nhập có vẻ cũ hơn.";
	}

	public String importadorBotonReemplazar() {
		return "Thay thế";
	}

	public String importadorBotonSaltar() {
		return "Bỏ qua";
	}

	public String importadorBotonRenombrar() {
		return "Đổi tên tệp mới";
	}

	public String importadorModpackTitulo() {
		return "Nhập modpack";
	}

	public String importadorModpackBotonSidebar() {
		return "Nhập";
	}

	public String importadorModpackDescripcion() {
		return "Nhập một modpack vào instance hiện tại. Bạn có thể kéo thả file .zip, .mrpack hoặc định dạng hỗ trợ khác, hoặc chọn thủ công.";
	}

	public String importadorModpackFormato() {
		return "Định dạng";
	}

	public String importadorModpackArrastraArchivo() {
		return "Kéo modpack của bạn vào đây hoặc chọn một file";
	}

	public String importadorModpackBotonSeleccionar() {
		return "Chọn file";
	}

	public String importadorModpackBotonImportar() {
		return "Nhập";
	}

	public String importadorModpackSeleccionarArchivo() {
		return "Chọn modpack";
	}

	public String importadorModpackEstadoListo() {
		return "Sẵn sàng";
	}

	public String importadorModpackEstadoImportando() {
		return "Đang nhập...";
	}

	public String importadorModpackEstadoError() {
		return "Lỗi khi nhập.";
	}

	public String importadorModpackSinArchivo() {
		return "Vui lòng chọn file modpack trước.";
	}

	public String importadorModpackFormatoNoSoportado() {
		return "Định dạng này không hỗ trợ nhập.";
	}

	public String importadorModpackResultado(int copiados, int reemplazados, int saltados, int renombrados,
			int errores) {
		return "Nhập xong.\nĐã sao chép: " + copiados + "\nĐã thay thế: " + reemplazados + "\nĐã bỏ qua: " + saltados
				+ "\nĐã đổi tên: " + renombrados + "\nLỗi: " + errores;
	}

	public String importadorModpackColorFondo() {
		return "Nhập modpack: nền";
	}

	public String importadorModpackColorPanel() {
		return "Nhập modpack: bảng";
	}

	public String importadorModpackColorTexto() {
		return "Nhập modpack: văn bản";
	}

	public String importadorModpackColorTextoSuave() {
		return "Nhập modpack: văn bản nhạt";
	}

	public String importadorModpackColorBoton() {
		return "Nhập modpack: nút";
	}

	public String importadorModpackColorBotonTexto() {
		return "Nhập modpack: văn bản nút";
	}

	public String importadorModpackColorDrop() {
		return "Nhập modpack: vùng kéo thả";
	}

	public String importadorModpackColorBorde() {
		return "Nhập modpack: viền";
	}

	public String jgitTituloIzzy() {
		return "Trung tâm Git của Izzy";
	}

	public String jgitRetratoIzzy() {
		return "Izzy";
	}

	public String jgitNoHayRetratoIzzy() {
		return "Không có chân dung Izzy";
	}

	public String jgitSeccionInstalacion() {
		return "Cài đặt JGit";
	}

	public String jgitAbrirCarpetaInstalacion() {
		return "Mở thư mục cài đặt";
	}

	public String jgitAbrirPaginaDescarga() {
		return "Mở trang tải xuống JGit";
	}

	public String jgitSeccionRepositorio() {
		return "Kho lưu trữ cục bộ";
	}

	public String jgitCrearRepositorioLocal() {
		return "Tạo kho lưu trữ Git tại đây";
	}

	public String jgitCommitManual() {
		return "Commit thủ công";
	}

	public String jgitSeccionRemote() {
		return "Remote";
	}

	public String jgitForgeManual() {
		return "Hướng dẫn Forge";
	}

	public String jgitForgePersonalizado() {
		return "Forge tùy chỉnh";
	}

	public String jgitEstablecerRemoteManual() {
		return "Thiết lập remote thủ công";
	}

	public String jgitCrearRemoteConAPI() {
		return "Tạo remote bằng API";
	}

	public String jgitPushManual() {
		return "Push thủ công";
	}

	public String jgitSeccionAutomaticos() {
		return "Tự động hóa";
	}

	public String jgitAutoCommitDespuesBackup() {
		return "Tự động commit sau khi sao lưu";
	}

	public String jgitAutoPushDespuesCommit() {
		return "Tự động push sau khi commit";
	}

	public String jgitSeccionHerramientas() {
		return "Công cụ";
	}

	public String jgitAbrirGuiSwing() {
		return "Mở trình xem Swing của JGit";
	}

	public String jgitEstado() {
		return "Trạng thái";
	}

	public String jgitClasspath() {
		return "JGit trong classpath";
	}

	public String jgitTodosLosArtefactos() {
		return "Tất cả artifact của JGit";
	}

	public String jgitRepositorio() {
		return "Kho lưu trữ";
	}

	public String jgitRemote() {
		return "Remote";
	}

	public String jgitCarpetaActual() {
		return "Thư mục hiện tại";
	}

	public String jgitNoSePudoCrearRepo() {
		return "Không thể tạo kho lưu trữ.";
	}

	public String jgitEscribaRemote() {
		return "Nhập URL của remote:";
	}

	public String jgitNoSePudoGuardarRemote() {
		return "Không thể lưu remote.";
	}

	public String jgitApiForgeAunNoImplementada() {
		return "API của Forge chưa được triển khai.";
	}

	public String jgitNoHayCambiosOError() {
		return "Không có thay đổi để commit hoặc đã xảy ra lỗi.";
	}

	public String jgitNoSePudoPush() {
		return "Không thể thực hiện push.";
	}

	public String jgitTituloVentanaSwing() {
		return "Trình xem Git";
	}

	public String jgitNoHayRepositorio() {
		return "Không có kho lưu trữ Git trong thư mục này.";
	}

	public String jgitArchivosModificados() {
		return "Tệp đã sửa đổi";
	}

	public String jgitArchivosNuevos() {
		return "Tệp mới";
	}

	public String jgitUltimosCommits() {
		return "Các commit gần nhất";
	}

	public String jgitError() {
		return "Lỗi JGit";
	}

	public String si() {
		return "Có";
	}

	public String no() {
		return "Không";
	}

	public String jgitDescargarDependenciasFaltantes() {
		return "Tải xuống các phụ thuộc thiếu";
	}

	public String jgitNoFaltanDependencias() {
		return "Không có phụ thuộc JGit nào bị thiếu.";
	}

	public String jgitConfirmarDescargaDependencias(int cantidad) {
		return "Thiếu " + cantidad + " phụ thuộc JGit. Bạn có muốn tải chúng từ Maven Central không?";
	}

	public String jgitDependenciasDescargadas(int cantidad) {
		return "Số phụ thuộc đã tải: " + cantidad;
	}

	public String jgitErroresDescarga() {
		return "Lỗi tải xuống";
	}

	public String jgitReiniciarParaCargarClasspath() {
		return "Khởi động lại " + Statics.nombre_cd.obtener() + " để các JAR mới được nạp vào classpath.";
	}

	public String jgitArtefactosFaltantes() {
		return "Các artifact thiếu";
	}

	public String jgitArtefactosFaltantesClasspath() {
		return "Các artifact thiếu trong classpath";
	}

	public String jgitArtefactosFaltantesCarpeta() {
		return "Các artifact thiếu trong thư mục cài đặt";
	}

	public String jgitDependenciasEnCarpeta() {
		return "Các phụ thuộc đã cài đặt trong thư mục";
	}

	public String jgitForgeNoSeleccionada() {
		return "Chưa chọn forge.";
	}

	public String jgitForgeNoRegistrada(String id) {
		return "Forge chưa được đăng ký: " + id;
	}

	public String jgitEscribaUrlForge() {
		return "URL của forge:";
	}

	public String jgitEscribaNombreRepositorio() {
		return "Tên kho lưu trữ:";
	}

	public String jgitEscribaDescripcionRepositorio() {
		return "Mô tả kho lưu trữ:";
	}

	public String jgitEscribaNamespaceOpcional() {
		return "Namespace tùy chọn:";
	}

	public String jgitEscribaTokenForge() {
		return "Token API của forge:";
	}

	public String jgitErrorCrearRemote() {
		return "Lỗi khi tạo remote";
	}

	@Override
	public String mensajeControlifyRemoveReloadingScreen() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện xung đột giữa Controlify và Remove Reloading Screen.</b>"
				+ "<p>Nhật ký chứa các dòng <b>Attempted to fetch default config before DefaultConfigManager was ready!</b> "
				+ "và <b>$rrls$init</b>, điều này thường chỉ ra mâu thuẫn giữa <b>Controlify</b> và "
				+ "<b>Remove Reloading Screen</b>.</p>"
				+ "<p><b>Nguyên nhân có thể:</b> Remove Reloading Screen thay đổi các phần của màn hình tải hoặc quá trình tải, "
				+ "trong khi Controlify cố gắng khởi tạo cấu hình trước khi hệ thống sẵn sàng hoàn toàn.</p>"
				+ "<p><b>Các tùy chọn khuyến nghị:</b></p>" + "<ul>" + "<li>Xóa <b>Remove Reloading Screen</b>.</li>"
				+ "<li>Hoặc cập nhật <b>Controlify</b> và <b>Remove Reloading Screen</b> nếu có phiên bản mới.</li>"
				+ "<li>Nếu vấn đề vẫn tiếp diễn, hãy giữ <b>Controlify</b> và xóa bất kỳ mod nào thay đổi màn hình tải.</li>"
				+ "</ul>" + "<p>Các mod thay đổi màn hình tải thường gây xung đột với các mod khác, "
				+ "và thường mang lại ít lợi ích thực tế so với các vấn đề chúng có thể gây ra.</p>";
	}

	@Override
	public String nombreControlifyRemoveReloadingScreen() {
		return "Xung đột: Controlify vs Remove Reloading Screen";
	}

	@Override
	public String mensajeBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Vấn đề có thể xảy ra với Biomes O' Plenty và chất lỏng tùy chỉnh.</b>"
				+ "<p>Nhật ký chứa lỗi <b>class org.joml.Vector4f cannot be cast to class "
				+ "net.minecraft.client.renderer.fog.FogData</b> cùng với tham chiếu đến <b>Biomes O' Plenty</b>.</p>"
				+ "<p>Điều này có thể liên quan đến <b>Biomes O' Plenty</b>, đặc biệt là với quần xã, sương mù "
				+ "hoặc chất lỏng tùy chỉnh. Tuy nhiên, không hoàn toàn chắc chắn rằng Biomes O' Plenty là nguyên nhân duy nhất.</p>"
				+ "<p><b>Các tùy chọn khuyến nghị:</b></p>" + "<ul>"
				+ "<li>Hãy thử chỉnh sửa dữ liệu người chơi để di chuyển họ đến vị trí khác trong thế giới.</li>"
				+ "<li>Thử tải thế giới mà không có <b>Biomes O' Plenty</b>.</li>"
				+ "<li>Nếu thế giới tải được sau khi di chuyển người chơi, vấn đề có thể xảy ra ở một khu vực cụ thể, "
				+ "quần xã cụ thể hoặc chất lỏng tùy chỉnh gần đó.</li>"
				+ "<li>Bạn cũng có thể thử cập nhật <b>Biomes O' Plenty</b> và các mod liên quan đến kết xuất, sương mù, "
				+ "shader hoặc chiều không gian.</li>" + "</ul>"
				+ "<p>Nếu việc xóa Biomes O' Plenty cho phép bắt đầu trò chơi, hãy kiểm tra xem người chơi có ở trong hoặc gần một quần xã "
				+ "hoặc chất lỏng được thêm bởi mod đó hay không.</p>";
	}

	@Override
	public String nombreBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "Vấn đề có thể xảy ra: Biomes O' Plenty và FogData";
	}

	@Override
	public String mensajeKotlinReflectionInternalErrorVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện lỗi nội bộ phản chiếu Kotlin.</b>"
				+ "<p>Nhật ký chứa <b>kotlin.reflect.jvm.internal.KotlinReflectionInternalError</b> với thông báo tương tự "
				+ "<b>Property 'none' not resolved</b>.</p>"
				+ "<p>Loại lỗi này phổ biến với một số phiên bản của <b>Fabric Language Kotlin</b> / <b>Kotlin</b>. "
				+ "Trong trường hợp này, xuất hiện một lớp từ <b>Inventory Profiles Next</b>, nhưng vấn đề tương tự cũng có thể xảy ra "
				+ "với các mod khác sử dụng Kotlin.</p>" + "<p><b>Các tùy chọn khuyến nghị:</b></p>" + "<ul>"
				+ "<li>Cập nhật <b>Fabric Language Kotlin</b> lên phiên bản <b>2.3.40</b>, nếu có sẵn cho phiên bản Minecraft của bạn.</li>"
				+ "<li>Nếu cập nhật không hiệu quả, hãy thử hạ <b>Fabric Language Kotlin</b> xuống phiên bản <b>2.3.10</b>.</li>"
				+ "<li>Cũng cập nhật <b>Inventory Profiles Next</b> nếu nhật ký đề cập đến các lớp của mod đó.</li>"
				+ "<li>Nếu lỗi xuất hiện với mod khác, hãy kiểm tra xem mod đó có phụ thuộc vào Kotlin không và thử thay đổi phiên bản của "
				+ "<b>Fabric Language Kotlin</b>.</li>" + "</ul>" + "<p>Tham khảo kỹ thuật liên quan: "
				+ "<a href='https://github.com/FabricMC/fabric-language-kotlin/issues/183'>Vấn đề #183 của Fabric Language Kotlin</a>.</p>";
	}

	@Override
	public String nombreKotlinReflectionInternalErrorVersion() {
		return "Lỗi Kotlin: phản chiếu nội bộ";
	}

	public String tituloEscanerMCreator() {
		return "Trình quét MCreator";
	}

	public String escanerMCreatorEscaneandoMods() {
		return "Đang quét mod...";
	}

	public String escanerMCreatorPorFavorEspera() {
		return "Vui lòng đợi.";
	}

	public String escanerMCreatorResultadosAnalisis() {
		return "Kết quả phân tích MCreator:";
	}

	public String escanerMCreatorNoSeEncontraronMods() {
		return "Không tìm thấy mod nào của MCreator.";
	}

	public String escanerMCreatorEscaneoCompletado() {
		return "Quét hoàn tất.";
	}

	public String escanerMCreatorErrorDuranteEscaneo() {
		return "Lỗi trong quá trình quét:";
	}

	public String escanerMCreatorCargando() {
		return "Đang tải...";
	}

	public String escanerMCreatorCompletado() {
		return "Hoàn tất";
	}

	public String escanerMCreatorError() {
		return "Lỗi";
	}

	@Override
	public String textoNormal() {
		return "Văn bản bình thường";
	}

	@Override
	public String noSeEncontroConsolaParaArchivo() {
		return "Không tìm thấy bảng điều khiển cho tệp: ";
	}

	@Override
	public String lineaSeleccionadaEnLectador() {
		return "dòng đã chọn trong trình đọc: ";
	}

	@Override
	public String mensajeMotionBlurBufferCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Vấn đề có thể xảy ra với Motion Blur.</b>"
				+ "<p>Log chứa tham chiếu đến <b>net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO</b> "
				+ "và cũng là lỗi <b>java.lang.IllegalStateException: Buffer already closed</b>.</p>"
				+ "<p>Các dòng này có thể xuất hiện riêng biệt trong log, nhưng khi cùng nhau thường chỉ ra rằng vấn đề liên quan "
				+ "đến mod <b>Motion Blur</b> hoặc cách nó xử lý shaders/buffers đồ họa.</p>"
				+ "<p><b>Các tùy chọn được khuyến nghị:</b></p>" + "<ul>"
				+ "<li>Thử khởi động game mà không có <b>Motion Blur</b>.</li>"
				+ "<li>Nếu game khởi động đúng cách mà không có mod đó, hãy giữ nó đã gỡ cài đặt hoặc tìm phiên bản mới hơn.</li>"
				+ "<li>Bạn cũng có thể thử tắt shaders hoặc các mod kết xuất khác nếu vấn đề vẫn tiếp diễn.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreMotionBlurBufferCerrado() {
		return "Vấn đề có thể xảy ra: Motion Blur";
	}

	@Override
	public String mensajeGeneratorAcceleratorOwoVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Xung đột có thể xảy ra với Generator Accelerator.</b>"
				+ "<p>Log chứa sự khác biệt giữa các chữ ký <b>Found</b> và <b>Available</b>, cùng với các lớp từ "
				+ "<b>Generator Accelerator</b>, ví dụ <b>dev/sixik/generator_accelerator/common/features/FastTarget</b>.</p>"
				+ "<p>Lỗi này có thể do <b>Generator Accelerator</b> gây ra. Nó cũng có thể liên quan "
				+ "đến sự không tương thích giữa mod đó và một số phiên bản của <b>owo-lib</b>.</p>"
				+ "<p><b>Các tùy chọn được khuyến nghị:</b></p>" + "<ul>"
				+ "<li>Thử khởi động game mà không có <b>Generator Accelerator</b>.</li>"
				+ "<li>Nếu game khởi động đúng cách, hãy giữ mod đó đã gỡ cài đặt hoặc tìm phiên bản khác.</li>"
				+ "<li>Thử cập nhật hoặc thay đổi phiên bản của <b>owo-lib</b>, đặc biệt nếu các mod khác cũng phụ thuộc vào owo.</li>"
				+ "<li>Kiểm tra xem <b>Generator Accelerator</b>, <b>owo-lib</b>, loader và phiên bản Minecraft có tương thích với nhau không.</li>"
				+ "</ul>"
				+ "<p>Nguyên nhân có khả năng nhất là Generator Accelerator đang cố gắng áp dụng một sửa đổi với chữ ký "
				+ "không khớp với phiên bản hiện tại của một lớp hoặc phụ thuộc.</p>";
	}

	@Override
	public String nombreGeneratorAcceleratorOwoVersion() {
		return "Xung đột có thể xảy ra: Generator Accelerator và owo-lib";
	}

	@Override
	public String mensajeFabricRenderingApiFaltaIndium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Thiếu trình kết xuất tương thích với Fabric Rendering API.</b>"
				+ "<p>Log chứa lỗi nơi <b>RendererAccess.getRenderer()</b> trả về <b>null</b>, "
				+ "gây ra lỗi khi cố gắng sử dụng <b>Renderer.materialFinder()</b>.</p>"
				+ "<p>Vấn đề này thường xảy ra khi <b>Fabric Rendering API</b> không khả dụng đúng cách. "
				+ "Một nguyên nhân phổ biến là sử dụng <b>Sodium</b>, đặc biệt là các phiên bản cũ thay thế hoặc vô hiệu hóa các phần "
				+ "của hệ thống kết xuất được các mod khác mong đợi.</p>" + "<p><b>Giải pháp được khuyến nghị:</b></p>"
				+ "<ul>" + "<li>Cài đặt mod <b>Indium</b>.</li>"
				+ "<li>Đảm bảo rằng <b>Indium</b> tương thích với phiên bản <b>Sodium</b>, Fabric Loader và Minecraft của bạn.</li>"
				+ "<li>Nếu bạn đã cài đặt Indium, hãy cập nhật <b>Sodium</b>, <b>Indium</b> và <b>Fabric API</b>.</li>"
				+ "<li>Nếu vấn đề vẫn tiếp diễn, hãy thử tạm thời không có Sodium để xác nhận rằng lỗi liên quan đến trình kết xuất.</li>"
				+ "</ul>"
				+ "<p>Indium thường khôi phục khả năng tương thích với Fabric Rendering API cho các mod phụ thuộc vào hệ thống đó "
				+ "trong khi Sodium được cài đặt.</p>";
	}

	@Override
	public String nombreFabricRenderingApiFaltaIndium() {
		return "Thiếu Indium / Fabric Rendering API";
	}

	@Override
	public String mensajeEntradaDuplicadaIdModerno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Phát hiện mục trùng lặp trong đăng ký của Minecraft.</b>"
				+ "<p>Log chứa lỗi tương tự như <b>Duplicate entry on id</b>, ví dụ "
				+ "<b>current=maroon, previous=mint</b>.</p>"
				+ "<p>Trong các phiên bản hiện đại của Minecraft, loại lỗi này thường xảy ra khi hai mod cố gắng đăng ký "
				+ "các mục khác nhau bằng cách sử dụng cùng một ID nội bộ.</p>"
				+ "<p><b>Các tùy chọn được khuyến nghị:</b></p>" + "<ul>"
				+ "<li>Xóa một trong các mod đang đăng ký mục trùng lặp.</li>"
				+ "<li>Nếu bạn nhận ra các tên được đề cập trong lỗi, hãy kiểm tra xem mod nào thêm các mục đó và thử chạy mà không có mod đó.</li>"
				+ "<li>Nếu bạn không nhận ra các tên, hãy sử dụng tiện ích <b>grepr</b> trong thanh bên.</li>"
				+ "<li>Trong <b>grepr</b>, bật tìm kiếm bên trong các tệp nén <b>.jar</b>, <b>.zip</b> và <b>.fpm</b>.</li>"
				+ "<li>Cũng bật tìm kiếm trong <b>tệp nhị phân</b>, vì một số tên hoặc ID có thể nằm trong các lớp đã biên dịch.</li>"
				+ "</ul>"
				+ "<p>Tìm kiếm các giá trị được đề cập trong lỗi, chẳng hạn như <b>maroon</b> hoặc <b>mint</b>, để tìm xem mod nào chứa chúng.</p>";
	}

	@Override
	public String nombreEntradaDuplicadaIdModerno() {
		return "Mục trùng lặp trong ID của mod";
	}

	@Override
	public String nombreOpenGLMemoriaInsuficiente() {
		return "OpenGL – bộ nhớ video không đủ";
	}

	@Override
	public String mensajeOpenGLMemoriaInsuficiente() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft đã tạo ra lỗi OpenGL do thiếu bộ nhớ đồ họa.</b>" + "<p>Trò chơi đã báo lỗi:</p>"
				+ "<code>GL_OUT_OF_MEMORY</code>"
				+ "<p>Điều này thường có nghĩa là card đồ họa hoặc hệ thống không thể cấp phát đủ bộ nhớ cho kết cấu (textures), shader, mô hình, bộ đệm hoặc hiệu ứng hình ảnh.</p>"
				+ "<p><b>Nguyên nhân phổ biến:</b></p>" + "<ul>" + "<li>Shader quá nặng.</li>"
				+ "<li>Gói tài nguyên (Resource packs) độ phân giải cao.</li>"
				+ "<li>Quá nhiều mod hình ảnh hoặc kết xuất.</li>"
				+ "<li>Khoảng cách kết xuất (Render distance) quá cao.</li>" + "<li>Ít VRAM khả dụng.</li>"
				+ "<li>Trình điều khiển card đồ họa cũ hoặc không ổn định.</li>" + "</ul>"
				+ "<p><b>Giải pháp được khuyến nghị:</b></p>" + "<ul>" + "<li>Tạm thời tắt shader.</li>"
				+ "<li>Sử dụng gói tài nguyên có độ phân giải thấp hơn.</li>"
				+ "<li>Giảm khoảng cách kết xuất và mô phỏng.</li>"
				+ "<li>Giảm chất lượng đồ họa, bóng đổ, hạt và mipmaps.</li>"
				+ "<li>Cập nhật trình điều khiển card đồ họa.</li>"
				+ "<li>Đóng các chương trình khác sử dụng GPU hoặc nhiều bộ nhớ.</li>" + "</ul>"
				+ "<p>Nếu lỗi bắt đầu sau khi cài đặt shader, gói kết cấu hoặc mod hình ảnh, rất có thể đó là nguyên nhân.</p>";
	}

	@Override
	public String nombreErrorVerificacionBytecode() {
		return "VerifyError – bytecode hoặc mixin không hợp lệ";
	}

	@Override
	public String mensajeErrorVerificacionBytecode(String ubicacion, String razon, String claseSospechosa) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft đã gặp lỗi xác minh bytecode.</b>"
				+ "<p>Vấn đề này thường xảy ra khi thao tác bytecode, transformer hoặc mixin thất bại.</p>"
				+ "<p>Trò chơi đã báo lỗi:</p>" + "<code>java.lang.VerifyError</code>"
				+ (ubicacion.length() > 0 ? "<p><b>Vị trí:</b></p><code>" + ubicacion + "</code>" : "")
				+ (razon.length() > 0 ? "<p><b>Lý do:</b></p><code>" + razon + "</code>" : "")
				+ "<p><b>Những điều cần tìm kiếm:</b></p>" + "<ul>" + "<li>Tìm kiếm tại vị trí lỗi.</li>"
				+ "<li>Tìm kiếm loại được đề cập trong <code>Reason</code>.</li>"
				+ "<li>Kiểm tra stack trace để tìm các lớp mod đáng ngờ.</li>"
				+ "<li>Tìm kiếm tên các lớp mod xung quanh lỗi để có ý tưởng.</li>" + "</ul>"
				+ "<p><b>Cách sử dụng Grepr được khuyến nghị:</b></p>" + "<ul>"
				+ "<li>Mở <b>Grepr</b> trong thanh bên.</li>"
				+ "<li>Bật tùy chọn tìm kiếm bên trong các tệp <code>.jar</code>, <code>.zip</code> hoặc <code>.fpm</code>.</li>"
				+ "<li>Tìm kiếm tên cơ bản của lớp, không nhất thiết phải là gói đầy đủ.</li>" + "</ul>"
				+ "<p>Ví dụ: nếu xuất hiện <code>paquete.Clase</code>, chỉ tìm kiếm:</p>" + "<code>"
				+ (claseSospechosa.length() > 0 ? claseSospechosa : "Clase") + "</code>"
				+ "<p>Điều này có thể giúp tìm ra mod nào chứa hoặc sửa đổi lớp đó.</p>"
				+ "<p>Các giải pháp phổ biến: cập nhật mod bị ảnh hưởng, xóa các mod không tương thích, kiểm tra các addon của mod chính, hoặc thử chạy mà không có các mod sử dụng mixins/transformers trên cùng một lớp.</p>";
	}

	@Override
	public String errorMetodoFinalSobrescrito(String claseQueSobrescribe, String metodoFinal) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Lỗi tương thích: một mod đang cố ghi đè một phương thức final.</b>"
				+ "<p>Log chứa lỗi <b>IncompatibleClassChangeError</b> với dòng chữ "
				+ "<b>overrides final method</b>.</p>" + "<p>Lớp bị ảnh hưởng: <code>" + claseQueSobrescribe
				+ "</code></p>" + "<p>Phương thức final bị ảnh hưởng: <code>" + metodoFinal + "</code></p>"
				+ "<p>Lỗi này thường xảy ra khi một mod được biên dịch cho phiên bản khác của Minecraft, "
				+ "Forge, NeoForge, Fabric, Quilt hoặc một thư viện nền tảng.</p>" + "<p><b>Những điều nên thử:</b></p>"
				+ "<ul>" + "<li>Cập nhật mod chứa lớp được chỉ định.</li>"
				+ "<li>Nếu sự cố bắt đầu sau khi cập nhật Minecraft hoặc trình tải, hãy thử phiên bản tương thích của mod.</li>"
				+ "<li>Nếu lớp thuộc về <b>Immersive Portals</b>, hãy kiểm tra xem <b>Immersive Portals</b> có khớp chính xác với phiên bản Minecraft và trình tải của bạn không.</li>"
				+ "<li>Tránh trộn các mod được làm cho các phiên bản khác nhau của trình tải hoặc Minecraft.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreErrorMetodoFinalSobrescrito() {
		return "Một mod cố ghi đè một phương thức final";
	}

	@Override
	public String errorCrashProvocadoPorComando(String comandoDetectado) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft đã bị đóng bởi một lệnh crash.</b>" + "<p>Log cho biết lệnh <code>" + comandoDetectado
				+ "</code> đã được thực thi.</p>"
				+ "<p>Điều này thường có nghĩa là trò chơi không đóng do lỗi mod thông thường, mà vì ai đó "
				+ "đã sử dụng một lệnh được thiết kế để gây ra crash một cách cố ý.</p>"
				+ "<p><b>Những điều cần kiểm tra:</b></p>" + "<ul>"
				+ "<li>Kiểm tra xem ai đã thực thi lệnh trong bảng điều khiển hoặc trong trò chơi.</li>"
				+ "<li>Nếu đây là một bài kiểm tra, bạn có thể bỏ qua crash này.</li>"
				+ "<li>Nếu nó xảy ra ngoài ý muốn, hãy kiểm tra command blocks, script, datapack, mod quản trị hoặc quyền của operator.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreCrashProvocadoPorComando() {
		return "Crash do lệnh gây ra";
	}

	public String impactoAlto() {
		return "Cao";
	}

	public String impactoMedio() {
		return "Trung bình";
	}

	public String impactoBajo() {
		return "Thấp";
	}

	public String impactoBajoMedio() {
		return "Thấp/Trung bình";
	}

	public String riesgoAlto() {
		return "Cao";
	}

	public String riesgoMedio() {
		return "Trung bình";
	}

	public String riesgoBajo() {
		return "Thấp";
	}

	public String riesgoMedioAlto() {
		return "Trung bình/Cao";
	}

	public String tituloCrearConfigBBE() {
		return "Tạo cấu hình Better Block Entities";
	}

	public String descripcionCrearConfigBBE() {
		return "Tệp BBEConfig.json không tồn tại.";
	}

	public String sugerenciaCrearConfigBBE() {
		return "Tạo BBEConfig.json với tối ưu hóa cho rương, shulkers, biển báo, giường, chuông và cờ.";
	}

	public String tituloActivarOptimizacionMaestraBBE() {
		return "Kích hoạt tối ưu hóa chính của BBE";
	}

	public String descripcionActivarOptimizacionMaestraBBE() {
		return "Có vẻ như Better Block Entities chưa kích hoạt tối ưu hóa chính.";
	}

	public String sugerenciaActivarOptimizacionMaestraBBE() {
		return "Thêm {\"option\":\"optimize.master\",\"value\":true}";
	}

	public String tituloActivarCullingTextoCartelesBBE() {
		return "Kích hoạt culling văn bản biển báo";
	}

	public String descripcionActivarCullingTextoCartelesBBE() {
		return "Giảm kết xuất văn bản biển báo ở khoảng cách xa.";
	}

	public String sugerenciaActivarCullingTextoCartelesBBE() {
		return "Thêm {\"option\":\"misc.sign_text_culling\",\"value\":true}";
	}

	public String tituloAumentarSleepDelayEntityCulling() {
		return "Tăng sleepDelay của EntityCulling";
	}

	public String descripcionAumentarSleepDelayEntityCulling() {
		return "EntityCulling sẽ kiểm tra entities ít thường xuyên hơn.";
	}

	public String sugerenciaAumentarSleepDelayEntityCulling() {
		return "\"sleepDelay\": 153";
	}

	public String tituloSubirLimiteHitboxEntityCulling() {
		return "Tăng giới hạn hitbox";
	}

	public String descripcionSubirLimiteHitboxEntityCulling() {
		return "Cho phép hành vi culling tích cực hơn trước khi chuyển sang các tuyến đường chậm hơn.";
	}

	public String sugerenciaSubirLimiteHitboxEntityCulling() {
		return "\"hitboxLimit\": 90";
	}

	public String tituloDesactivarDatosF3EntityCulling() {
		return "Tắt dữ liệu F3 của EntityCulling";
	}

	public String descripcionDesactivarDatosF3EntityCulling() {
		return "Loại bỏ thông tin gỡ lỗi bổ sung của mod.";
	}

	public String sugerenciaDesactivarDatosF3EntityCulling() {
		return "\"disableF3\": true";
	}

	public String tituloActivarBufferingSignsImmediatelyFast() {
		return "Kích hoạt bộ đệm thử nghiệm cho biển báo";
	}

	public String descripcionActivarBufferingSignsImmediatelyFast() {
		return "Có thể cải thiện hiệu suất khi có nhiều biển báo.";
	}

	public String sugerenciaActivarBufferingSignsImmediatelyFast() {
		return "\"experimental_sign_text_buffering\": true";
	}

	public String tituloReducirConflictosResourcePacksImmediatelyFast() {
		return "Giảm xử lý xung đột resource pack";
	}

	public String descripcionReducirConflictosResourcePacksImmediatelyFast() {
		return "Có thể loại bỏ công việc thừa, nhưng cũng có thể gây ra vấn đề hình ảnh với resource pack.";
	}

	public String sugerenciaReducirConflictosResourcePacksImmediatelyFast() {
		return "\"experimental_disable_resource_pack_conflict_handling\": true";
	}

	public String tituloOcultarBotonNCR() {
		return "Ẩn nút No Chat Reports";
	}

	public String descripcionOcultarBotonNCR() {
		return "Thay đổi giao diện; không cải thiện nhiều FPS, nhưng làm sạch màn hình.";
	}

	public String sugerenciaOcultarBotonNCR() {
		return "\"showNCRButton\": false";
	}

	public String tituloActivarMixinsExperimentalesLithium() {
		return "Kích hoạt mixins thử nghiệm của Lithium";
	}

	public String descripcionActivarMixinsExperimentalesLithium() {
		return "Bật các tối ưu hóa thử nghiệm bổ sung.";
	}

	public String sugerenciaActivarMixinsExperimentalesLithium() {
		return "mixin.experimental=true";
	}

	public String tituloUsarDetectorThreadingPequenoFerriteCore() {
		return "Sử dụng bộ phát hiện threading nhỏ";
	}

	public String descripcionUsarDetectorThreadingPequenoFerriteCore() {
		return "Giảm bộ nhớ, nhưng có thể rủi ro hơn.";
	}

	public String sugerenciaUsarDetectorThreadingPequenoFerriteCore() {
		return "useSmallThreadingDetector=true";
	}

	public String tituloModernFixRecursosDinamicos() {
		return "Kích hoạt tài nguyên động của ModernFix";
	}

	public String descripcionModernFixRecursosDinamicos() {
		return "Có thể giảm sử dụng bộ nhớ và tải bằng cách tải tài nguyên hiệu quả hơn.";
	}

	public String tituloModernFixRenderizadoresDinamicosEntidades() {
		return "Kích hoạt trình kết xuất entities động";
	}

	public String descripcionModernFixRenderizadoresDinamicosEntidades() {
		return "Có thể cải thiện hiệu suất bằng cách quản lý trình kết xuất entities hiệu quả hơn.";
	}

	public String tituloModernFixRenderizadoItemsRapido() {
		return "Kích hoạt kết xuất nhanh vật phẩm";
	}

	public String descripcionModernFixRenderizadoItemsRapido() {
		return "Có thể cải thiện hiệu suất khi kết xuất vật phẩm.";
	}

	public String tituloModernFixWorldgenAllocation() {
		return "Giảm phân bổ trong worldgen";
	}

	public String descripcionModernFixWorldgenAllocation() {
		return "Có thể giảm rác bộ nhớ trong quá trình tạo thế giới.";
	}

	public String tituloModernFixDeduplicacionIngredientes() {
		return "Kích hoạt loại bỏ trùng lặp thành phần";
	}

	public String descripcionModernFixDeduplicacionIngredientes() {
		return "Giảm các đối tượng trùng lặp liên quan đến công thức và thành phần.";
	}

	public String tituloSodiumRenderCielo() {
		return "Kích hoạt tối ưu hóa/kết xuất bầu trời trong Sodium";
	}

	public String descripcionSodiumRenderCielo() {
		return "Có thể điều chỉnh hành vi kết xuất bầu trời.";
	}

	public String tituloActivarLightmapCaching() {
		return "Kích hoạt bộ đệm lightmap";
	}

	public String descripcionActivarLightmapCaching() {
		return "Ngăn tính toán lại ánh sáng khi không cần thiết.";
	}

	public String sugerenciaActivarLightmapCaching() {
		return "enable_lightmap_caching: true";
	}

	public String tituloOcultarTextoF3BadOptimizations() {
		return "Ẩn văn bản F3 của BadOptimizations";
	}

	public String descripcionOcultarTextoF3BadOptimizations() {
		return "Giảm nhiễu gỡ lỗi trên màn hình F3.";
	}

	public String sugerenciaOcultarTextoF3BadOptimizations() {
		return "show_f3_text: false";
	}

	public String tituloDesactivarLogConfigBadOptimizations() {
		return "Tắt log cấu hình";
	}

	public String descripcionDesactivarLogConfigBadOptimizations() {
		return "Ngăn in toàn bộ cấu hình khi khởi động.";
	}

	public String sugerenciaDesactivarLogConfigBadOptimizations() {
		return "log_config: false";
	}

	public String tituloActivarSerializadorGCFreeC2ME() {
		return "Kích hoạt bộ nối tiếp GC-free của C2ME";
	}

	public String descripcionActivarSerializadorGCFreeC2ME() {
		return "Giảm phân bổ bộ nhớ khi tải hoặc lưu chunks.";
	}

	public String sugerenciaActivarSerializadorGCFreeC2ME() {
		return "[ioSystem] gcFreeChunkSerializer = true";
	}

	public String tituloDesactivarSyncPlayerTicketsC2ME() {
		return "Tắt syncPlayerTickets";
	}

	public String descripcionDesactivarSyncPlayerTicketsC2ME() {
		return "Có thể cải thiện hiệu suất chunks, nhưng có thể ảnh hưởng đến contraptions kỹ thuật.";
	}

	public String sugerenciaDesactivarSyncPlayerTicketsC2ME() {
		return "[chunkSystem] syncPlayerTickets = false";
	}

	public String tituloUsarCullingHojasDepthMoreCulling() {
		return "Sử dụng culling lá DEPTH";
	}

	public String descripcionUsarCullingHojasDepthMoreCulling() {
		return "Sử dụng chế độ culling lá tích cực hơn chế độ thông thường.";
	}

	public String sugerenciaUsarCullingHojasDepthMoreCulling() {
		return "leavesCullingMode = \"DEPTH\"";
	}

	public String tituloActivarEndGatewayCullingMoreCulling() {
		return "Kích hoạt culling End Gateway";
	}

	public String descripcionActivarEndGatewayCullingMoreCulling() {
		return "Ngăn kết xuất không cần thiết của End Gateways.";
	}

	public String sugerenciaActivarEndGatewayCullingMoreCulling() {
		return "endGatewayCulling = true";
	}

	public String tituloActivarActivationRangeServerCore() {
		return "Kích hoạt activation range";
	}

	public String descripcionActivarActivationRangeServerCore() {
		return "Giảm ticks của entities ở xa người chơi.";
	}

	public String sugerenciaActivarActivationRangeServerCore() {
		return "activation-range: enabled: true";
	}

	public String tituloActivarRangoVerticalServerCore() {
		return "Kích hoạt phạm vi dọc";
	}

	public String descripcionActivarRangoVerticalServerCore() {
		return "Giảm ticks của entities nằm quá cao hoặc quá thấp so với người chơi.";
	}

	public String sugerenciaActivarRangoVerticalServerCore() {
		return "use-vertical-range: true";
	}

	public String impactoNegativoAlto() {
		return "Tác động tiêu cực cao";
	}

	public String advertenciaModsCulling() {
		return "Các mod culling có thể gây ra xung đột với một số mod, crash, lỗi khiến game ngừng tick chính xác và cũng có thể làm cho trang trại tự động hoặc nhà máy ngừng hoạt động.";
	}

	public String tituloModBadOptimizations() {
		return "Thêm BadOptimizations";
	}

	public String descripcionModBadOptimizations() {
		return "Thêm các tối ưu vi mô phía client như bộ đệm lightmap, bộ đệm bầu trời và giảm các lệnh gọi không cần thiết.";
	}

	public String tituloModBBE() {
		return "Thêm Better Block Entities";
	}

	public String descripcionModBBE() {
		return "Tối ưu hóa việc render các block entity như rương, shulker, giường, chuông, cờ và biển báo.";
	}

	public String tituloModC2ME() {
		return "Thêm Concurrent Chunk Management Engine";
	}

	public String descripcionModC2ME() {
		return "Cải thiện việc tải, tạo và quản lý chunk bằng xử lý đồng thời. Có thể rất mạnh, nhưng cũng có thể gây xung đột trong các modpack lớn.";
	}

	public String tituloModEntityCulling() {
		return "Thêm EntityCulling";
	}

	public String descripcionModEntityCulling() {
		return "Tránh render các entity không thể nhìn thấy. " + advertenciaModsCulling();
	}

	public String tituloModFerriteCore() {
		return "Thêm FerriteCore";
	}

	public String descripcionModFerriteCore() {
		return "Giảm sử dụng bộ nhớ thông qua việc loại bỏ trùng lặp và cấu trúc nội bộ hiệu quả hơn.";
	}

	public String tituloModImmediatelyFast() {
		return "Thêm ImmediatelyFast";
	}

	public String descripcionModImmediatelyFast() {
		return "Tối ưu hóa nhiều phần của render immediate mode, văn bản, buffer, bản đồ và giao diện.";
	}

	public String tituloModLithium() {
		return "Thêm Lithium";
	}

	public String descripcionModLithium() {
		return "Tối ưu hóa logic game, entity, khối, vật lý và các hệ thống khác mà không thay đổi quá nhiều hành vi vanilla.";
	}

	public String tituloModModernFix() {
		return "Thêm ModernFix";
	}

	public String descripcionModModernFix() {
		return "Thêm nhiều tối ưu hóa về bộ nhớ, tải, tài nguyên và hiệu suất tổng thể. Các công cụ liên quan đến atlas của nó có thể xung đột với các mod làm atlas nhỏ hơn.";
	}

	public String tituloModMoreCulling() {
		return "Thêm More Culling";
	}

	public String descripcionModMoreCulling() {
		return "Thêm culling cho khối, lá, khung vật phẩm, tranh, mưa, beacon và các yếu tố khác. "
				+ advertenciaModsCulling();
	}

	public String tituloModScalableLux() {
		return "Thêm ScalableLux";
	}

	public String descripcionModScalableLux() {
		return "Tối ưu hóa các tính toán liên quan đến ánh sáng và có thể cải thiện hiệu suất trong các thế giới có nhiều thay đổi ánh sáng.";
	}

	public String tituloModServerCore() {
		return "Thêm ServerCore";
	}

	public String descripcionModServerCore() {
		return "Thêm các tối ưu hóa phía server, activation range, kiểm soát mobcaps, giảm tick và cải thiện việc tải.";
	}

	public String tituloModSodium() {
		return "Thêm Sodium";
	}

	public String descripcionModSodium() {
		return "Mod tối ưu render chính. Thường là một trong những cải thiện quan trọng nhất cho FPS.";
	}

	public String tituloModVMP() {
		return "Thêm Very Many Players";
	}

	public String descripcionModVMP() {
		return "Tối ưu hóa hệ thống server để xử lý nhiều người chơi. Mod id dự kiến là vmp.";
	}

	public String tituloModMCMT() {
		return "Thêm MCMT";
	}

	public String descripcionModMCMT() {
		return "Cố gắng đa luồng một số phần của server Minecraft. Có thể cải thiện hiệu suất trong một số trường hợp, nhưng có nguy cơ cao gây xung đột, lỗi tick và hành vi lạ.";
	}

	public String tituloLiabilityUranium() {
		return "Xóa Uranium";
	}

	public String descripcionLiabilityUranium() {
		return "Uranium là một mod được thiết kế cố ý để làm lag game. Không nên cài đặt nếu muốn hiệu suất tốt.";
	}

	public String tituloAmbientalSinXmx() {
		return "Cấu hình bộ nhớ tối đa của Java";
	}

	public String descripcionAmbientalSinXmx(int mods, String minimo, String maximoSeguro) {
		return "Không phát hiện thấy -Xmx trong các đối số đã cung cấp. Đối với " + mods
				+ " mod, mức tối thiểu được đề xuất là " + minimo + ", không vượt quá khoảng " + maximoSeguro + ".";
	}

	public String sugerenciaAmbientalSinXmx(String minimo) {
		return "Thêm -Xmx" + minimo.replace(" ", "");
	}

	public String tituloAmbientalDemasiadaMemoria() {
		return "Giảm bộ nhớ được cấp phát";
	}

	public String descripcionAmbientalDemasiadaMemoria(String xmx, String total, String maximoSeguro) {
		return "Instance đã được cấp phát " + xmx + " trên tổng số " + total
				+ ". Không nên cấp phát nhiều hơn 80% RAM khả dụng.";
	}

	public String sugerenciaAmbientalDemasiadaMemoria(String maximoSeguro) {
		return "Giảm -Xmx xuống " + maximoSeguro + " hoặc thấp hơn.";
	}

	public String tituloAmbientalMemoriaInsuficiente() {
		return "Tăng bộ nhớ được cấp phát";
	}

	public String descripcionAmbientalMemoriaInsuficiente(int mods, String xmx, String minimo) {
		return "Instance đã được cấp phát " + xmx + ". Đối với " + mods + " mod, mức tối thiểu được đề xuất là "
				+ minimo + ".";
	}

	public String sugerenciaAmbientalMemoriaInsuficiente(String minimo) {
		return "Tăng -Xmx lên ít nhất " + minimo + ".";
	}

	public String tituloAmbientalJava8GC() {
		return "Sử dụng G1GC hoặc Shenandoah trong Java 8";
	}

	public String descripcionAmbientalJava8GC() {
		return "Trong Java 8, khuyến nghị sử dụng G1GC hoặc Shenandoah để giảm thời gian dừng và cải thiện độ ổn định.";
	}

	public String sugerenciaAmbientalJava8GC() {
		return "Thêm -XX:+UseG1GC hoặc -XX:+UseShenandoahGC.";
	}

	public String tituloAmbientalZGC() {
		return "Sử dụng ZGC";
	}

	public String descripcionAmbientalZGC(String ramTotal) {
		return "Máy có hơn 12 GB RAM (" + ramTotal
				+ "). Nếu bản phân phối Java hỗ trợ, ZGC có thể giảm thời gian dừng của bộ thu gom rác.";
	}

	public String sugerenciaAmbientalZGC() {
		return "Trong Java 17 trở lên, hãy thử -XX:+UseZGC.";
	}

	public String tituloAmbientalAikar() {
		return "Thêm cờ Aikar";
	}

	public String descripcionAmbientalAikar() {
		return "Trong Java 17 hoặc cũ hơn, các cờ Aikar thường cải thiện hành vi của G1GC cho Minecraft.";
	}

	public String sugerenciaAmbientalAikar() {
		return "Sử dụng các cờ Aikar, bao gồm -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200.";
	}

	public String tituloAmbientalRedHatJDK() {
		return "Sử dụng Red Hat JDK";
	}

	public String descripcionAmbientalRedHatJDK(int javaMayor, String os) {
		return "Đối với Java " + javaMayor + " trên " + os
				+ ", khuyến nghị sử dụng Red Hat JDK vì độ ổn định và khả năng tương thích.";
	}

	public String sugerenciaAmbientalRedHatJDK() {
		return "Cài đặt Red Hat JDK cho Java 8 hoặc Java 11.";
	}

	public String tituloAmbientalAzulPrime() {
		return "Xem xét Azul Prime";
	}

	public String descripcionAmbientalAzulPrime() {
		return "Trên Linux với Java 16 trở lên và hơn 16 GB RAM, Azul Prime có thể là một lựa chọn hiệu suất tốt.";
	}

	public String sugerenciaAmbientalAzulPrime() {
		return "Thử Azul Prime nếu máy có hơn 16 GB RAM.";
	}

	public String tituloAmbientalGraalVM() {
		return "Xem xét GraalVM";
	}

	public String descripcionAmbientalGraalVM() {
		return "Với Java 16 trở lên và hơn 16 GB RAM, GraalVM có thể là một lựa chọn hữu ích ngoài Linux.";
	}

	public String sugerenciaAmbientalGraalVM() {
		return "Thử GraalVM nếu máy có hơn 16 GB RAM.";
	}

	public String tituloAmbientalDiscoBajo() {
		return "Kiểm tra dung lượng trống trên đĩa";
	}

	public String descripcionAmbientalDiscoBajo(String libre) {
		return "Đĩa còn ít dung lượng trống: " + libre
				+ ". Minecraft có thể bị lỗi, lưu chậm hoặc hỏng dữ liệu nếu hết dung lượng.";
	}

	public String sugerenciaAmbientalDiscoBajo() {
		return "Giải phóng dung lượng để có ít nhất 20 GB khả dụng.";
	}

	public String tituloAmbientalWindowsRHEL9() {
		return "Xem xét RHEL 9 để kiểm thử";
	}

	public String descripcionAmbientalWindowsRHEL9() {
		return "Trên Windows, khuyến nghị xem xét RHEL 9 vì nó bao gồm Red Hat JDK, ổn định, có thể tải xuống miễn phí cho cá nhân và là nơi thực hiện hầu hết các bài kiểm thử.";
	}

	public String sugerenciaAmbientalWindowsRHEL9() {
		return "Thử nghiệm instance trên RHEL 9 nếu muốn độ ổn định kiểm thử cao nhất.";
	}

	public String tituloAmbientalRaptorLake() {
		return "Cảnh báo Intel Raptor Lake";
	}

	public String descripcionAmbientalRaptorLake() {
		return "Đã phát hiện sự cố Raptor Lake được đánh dấu bởi kiểm tra hiện có. Điều này có thể gây ra mất ổn định, crash và các lỗi trông giống như do modpack.";
	}

	public String sugerenciaAmbientalRaptorLake() {
		return "Cập nhật BIOS/microcode và xem lại cảnh báo Raptor Lake trước khi đổ lỗi cho modpack.";
	}

	@Override
	public String tituloAmbientalNeoForge1201Antiguo() {
		return "Phát hiện NeoForge 1.20.1 cũ";
	}

	@Override
	public String descripcionAmbientalNeoForge1201Antiguo() {
		return "Đã phát hiện FancyModLoader 47 hoặc đường dẫn tương thích với NeoForge 1.20.1. "
				+ "NeoForge 1.20.1 là một nhánh của MinecraftForge 1.20.1 và thường tương thích ở mức nhị phân, "
				+ "nhưng dòng này đã bị bỏ hoang sớm hơn và có thể thiếu nhiều tối ưu hóa có sẵn trong Forge.";
	}

	@Override
	public String sugerenciaAmbientalNeoForge1201Antiguo() {
		return "Đối với 1.20.1, nếu modpack cho phép, hãy cân nhắc sử dụng MinecraftForge 1.20.1 thay vì NeoForge 1.20.1.";
	}

	@Override
	public String tituloAmbientalGPU() {
		return "Phát hiện vấn đề về GPU";
	}

	@Override
	public String descripcionAmbientalGPU() {
		return "Một kiểm tra khác đã phát hiện ra một vấn đề tiềm ẩn với GPU, OpenGL hoặc lựa chọn card đồ họa.";
	}

	@Override
	public String sugerenciaAmbientalGPU() {
		return "Kiểm tra xem Minecraft có đang sử dụng đúng GPU không, cập nhật driver và tránh các cấu hình lai không ổn định.";
	}

	@Override
	public String gpuFixTitulo() {
		return "Cấu hình GPU";
	}

	@Override
	public String gpuFixBotonSidebar() {
		return "GPU";
	}

	@Override
	public String gpuFixBotonAplicar() {
		return "Áp dụng cấu hình";
	}

	@Override
	public String gpuFixBotonFuenteTLauncher() {
		return "Mở hướng dẫn TLauncher";
	}

	@Override
	public String gpuFixBotonVirusTotal() {
		return "Mở phân tích VirusTotal";
	}

	@Override
	public String gpuFixBotonOptimusLinux() {
		return "Mở hướng dẫn NVIDIA Optimus";
	}

	@Override
	public String gpuFixTextoWindows() {
		return Statics.nombre_cd.obtener() + " phát hiện rằng Minecraft có thể không sử dụng GPU hiệu năng cao.\n\n"
				+ "Trong Windows, bạn có thể đặt các khóa registry trong "
				+ "HKEY_CURRENT_USER\\\\Software\\\\Microsoft\\\\DirectX\\\\UserGpuPreferences "
				+ "để buộc javaw.exe sử dụng GPU rời.\n\n" + "GpuPreference=0 = quyết định tự động của Windows.\n"
				+ "GpuPreference=1 = tiết kiệm năng lượng / GPU tích hợp.\n"
				+ "GpuPreference=2 = GPU hiệu năng cao.\n\n"
				+ "Một phần thông tin này có được nhờ nghiên cứu được công bố bởi TLauncher và phân tích "
				+ "hành vi có sẵn trên VirusTotal.";
	}

	@Override
	public String gpuFixTextoLinux() {
		return Statics.nombre_cd.obtener()
				+ " phát hiện một vấn đề tiềm ẩn liên quan đến NVIDIA Optimus hoặc PRIME.\n\n"
				+ "Tùy thuộc vào bản phân phối Linux được sử dụng, có thể cần cấu hình NVIDIA Optimus, "
				+ "nvidia-prime, switcheroo-control hoặc các hệ thống lai khác.\n\n"
				+ "Trong Fedora/RHEL và các biến thể, thường khuyến nghị làm theo tài liệu của RPMFusion.\n\n"
				+ "Nút bên dưới sẽ mở tài liệu chính thức được khuyến nghị.";
	}

	@Override
	public String gpuFixTextoMac() {
		return Statics.nombre_cd.obtener() + " phát hiện một vấn đề tiềm ẩn về lựa chọn GPU.\n\n"
				+ "Trong một số hệ thống macOS có GPU lai, có thể buộc sử dụng GPU rời "
				+ "thông qua các cài đặt hệ thống nâng cao.\n\n"
				+ "Nút áp dụng sẽ cố gắng thực thi một lệnh để ưu tiên GPU hiệu năng cao.";
	}

	@Override
	public String gpuFixTextoOtroSistema() {
		return Statics.nombre_cd.obtener() + " phát hiện một vấn đề tiềm ẩn liên quan đến GPU, "
				+ "nhưng không có triển khai cụ thể cho hệ điều hành này.";
	}

	@Override
	public String gpuFixLinuxManual() {
		return "Trong Linux, thông thường cấu hình phải được thực hiện thủ công tùy thuộc vào bản phân phối, "
				+ "driver NVIDIA và hệ thống Optimus/PRIME được sử dụng.";
	}

	@Override
	public String gpuFixSistemaNoSoportado() {
		return "Hệ điều hành không được hỗ trợ cho cấu hình GPU tự động.";
	}

	@Override
	public String gpuFixJavaNoDetectado() {
		return "Không thể phát hiện đường dẫn hiện tại của javaw.exe.";
	}

	@Override
	public String gpuFixWindowsAplicado(String ruta) {
		return "Cấu hình GPU đã được áp dụng thành công cho:\n\n" + ruta + "\n\n"
				+ "GpuPreference=2 chỉ ra GPU hiệu năng cao.";
	}

	@Override
	public String gpuFixErrorAplicando() {
		return "Đã xảy ra lỗi khi cố gắng áp dụng cấu hình GPU";
	}

	@Override
	public String gpuFixMacAplicado() {
		return "Cấu hình GPU hiệu năng cao đã được áp dụng.";
	}

	@Override
	public String gpuFixMacError() {
		return "Không thể áp dụng cấu hình GPU trong macOS";
	}

	@Override
	public String rendimientoTitulo() {
		return "Trình quản lý hiệu suất";
	}

	@Override
	public String rendimientoBotonSidebar() {
		return "Hiệu suất";
	}

	@Override
	public String rendimientoBotonAnalizar() {
		return "Phân tích hiệu suất";
	}

	@Override
	public String rendimientoBotonAbrirGPU() {
		return "Mở cấu hình GPU";
	}

	@Override
	public String rendimientoDescripcion() {
		return "Bảng này kiểm tra các vấn đề môi trường, các mod được khuyến nghị hoặc có rủi ro, và các tùy chọn cấu hình "
				+ "có thể cải thiện hiệu suất. Không phải tất cả các tùy chọn đều hoạt động cùng nhau, không phải tất cả đều phù hợp với mỗi "
				+ "phiên bản Minecraft và không phải tất cả đều tương thích với mỗi trình tải mod. Điều đó không sao: bạn không cần "
				+ "điểm tối ưu hóa hoàn hảo.";
	}

	@Override
	public String rendimientoNotaCompatibilidad() {
		return "Lưu ý: những gợi ý này là khả năng, không phải lệnh áp dụng mọi thứ. Một số tùy chọn có thể xung đột "
				+ "với nhau hoặc không phù hợp với phiên bản, trình khởi chạy, trình tải mod hoặc modpack của bạn.";
	}

	@Override
	public String rendimientoPestanaResumen() {
		return "Tóm tắt";
	}

	@Override
	public String rendimientoPestanaAmbiental() {
		return "Vấn đề môi trường";
	}

	@Override
	public String rendimientoPestanaMods() {
		return "Mod được khuyến nghị và rủi ro";
	}

	@Override
	public String rendimientoPestanaConfigs() {
		return "Tùy chọn cấu hình";
	}

	@Override
	public String rendimientoResumenTitulo() {
		return "Tóm tắt phân tích";
	}

	@Override
	public String rendimientoResumenAmbiental(int cantidad) {
		return "Số vấn đề môi trường tìm thấy: " + cantidad;
	}

	@Override
	public String rendimientoResumenMods(int cantidad) {
		return "Số gợi ý hoặc rủi ro về mod tìm thấy: " + cantidad;
	}

	@Override
	public String rendimientoResumenConfigs(int cantidad) {
		return "Số gợi ý cấu hình tìm thấy: " + cantidad;
	}

	@Override
	public String rendimientoResumenGPU() {
		return "Đã phát hiện sự cố GPU. Do đó, nút để mở cấu hình GPU đã được kích hoạt.";
	}

	@Override
	public String rendimientoSinHallazgos() {
		return "Không tìm thấy gợi ý nào trong phần này.";
	}

	@Override
	public String rendimientoSugerencia() {
		return "Gợi ý";
	}

	@Override
	public String rendimientoColorFondo() {
		return "Hiệu suất - nền";
	}

	@Override
	public String rendimientoColorPanel() {
		return "Hiệu suất - bảng điều khiển";
	}

	@Override
	public String rendimientoColorTexto() {
		return "Hiệu suất - văn bản";
	}

	@Override
	public String rendimientoColorTextoSecundario() {
		return "Hiệu suất - văn bản phụ";
	}

	@Override
	public String rendimientoColorBoton() {
		return "Hiệu suất - nút";
	}

	@Override
	public String rendimientoColorBotonTexto() {
		return "Hiệu suất - văn bản nút";
	}

	@Override
	public String rendimientoColorSeleccion() {
		return "Hiệu suất - lựa chọn";
	}

	@Override
	public String mensajeDialogoCompartirPrimitiva() {
		return "Trò chơi đã bị crash. Nếu không có cửa sổ bật lên với giải pháp, vui lòng gửi nhật ký (logs) đến trung tâm hỗ trợ.";
	}

	@Override
	public String irAModoNormalEstiloTL() {
		return "Chuyển sang chế độ bình thường";
	}

	@Override
	public String noHayEnlacesParaCopiar() {
		return "Không có liên kết để sao chép.";
	}

	@Override
	public String error_inesperado() {
		return "Lỗi không mong đợi";
	}

	@Override
	public String centroDeSoporte() {
		return "Trung tâm hỗ trợ";
	}

	@Override
	public String noHayCentroSoporteConfigurado() {
		return "Chưa cấu hình trung tâm hỗ trợ.";
	}

	public String historialMCLogs() {
		return "Lịch sử MCLogs";
	}

	public String endpoint() {
		return "Endpoint";
	}

	public String slug() {
		return "Slug";
	}

	public String tokenEliminacion() {
		return "Token xóa";
	}

	public String enlace() {
		return "Liên kết";
	}

	public String lineas() {
		return "Dòng";
	}

	public String errores() {
		return "Lỗi";
	}

	public String eliminarRegistroMCLogs() {
		return "Xóa bản ghi";
	}

	public String faltanDatosParaEliminarMCLogs() {
		return "Thiếu slug hoặc token xóa.";
	}

	public String confirmarEliminarMCLogs() {
		return "Bạn có chắc chắn muốn xóa bản ghi MCLogs này không?";
	}

	public String registroEliminadoMCLogs() {
		return "Đã xóa bản ghi thành công.";
	}

	public String confirmar() {
		return "Xác nhận";
	}

	public String colorCampoTexto() {
		return "Màu trường văn bản";
	}

	public String historialCDPaste() {
		return "Lịch sử CDPaste";
	}

	public String enlaceRaw() {
		return "Liên kết Raw";
	}

	public String tamano() {
		return "Kích thước";
	}

	public String eliminarRegistroCDPaste() {
		return "Xóa bản ghi CDPaste";
	}

	public String faltanDatosParaEliminarCDPaste() {
		return "Thiếu slug của bản ghi CDPaste.";
	}

	public String confirmarEliminarCDPaste() {
		return "Bạn có chắc chắn muốn xóa bản ghi CDPaste này không?";
	}

	public String registroEliminadoCDPaste() {
		return "Đã xóa bản ghi CDPaste thành công.";
	}

	public String launcherGenerico() {
		return "Chung";
	}

	public String launcherServidorMinecraft() {
		return "Máy chủ Minecraft";
	}

	public String descargandoYPreparandoEnlaces() {
		return "Đang tải xuống và chuẩn bị liên kết...";
	}

	public String seleccioneArchivoLog() {
		return "Chọn một tệp nhật ký (log)";
	}

	public String archivoNoValido() {
		return "Tệp không hợp lệ.";
	}

	public String archivoSeleccionado() {
		return "Tệp đã chọn:";
	}

	public String presioneGuardarParaAgregarAnalisis() {
		return "Nhấn 'Lưu và đóng' để thêm vào phân tích.";
	}

	public String errorAlCargarArchivoArrastrado() {
		return "Lỗi khi tải tệp được kéo thả";
	}

	public String errorAlAbrirArchivo() {
		return "Lỗi khi mở tệp";
	}

	public String errorDosPuntos() {
		return "Lỗi";
	}

	public String eliminarRegistros() {
		return "Xóa bản ghi";
	}

	public String editorConfigsMods() {
		return "Trình chỉnh sửa cấu hình Mod";
	}

	public String abrirConfig() {
		return "Mở Config";
	}

	public String guardarConfig() {
		return "Lưu Config";
	}

	public String recargarConfig() {
		return "Tải lại";
	}

	public String rutaConfig() {
		return "Đường dẫn";
	}

	public String tipoConfig() {
		return "Loại";
	}

	public String claveConfig() {
		return "Khóa";
	}

	public String valorConfig() {
		return "Giá trị";
	}

	public String buscarConfig() {
		return "Tìm kiếm";
	}

	public String sinArchivoSeleccionado() {
		return "Chưa chọn tệp nào";
	}

	public String archivoNoSoportado() {
		return "Tệp không được hỗ trợ bởi bất kỳ engine nào có sẵn";
	}

	public String configGuardada() {
		return "Cấu hình đã được lưu thành công";
	}

	public String errorCargandoConfig() {
		return "Lỗi khi tải cấu hình";
	}

	public String errorGuardandoConfig() {
		return "Lỗi khi lưu cấu hình";
	}

	public String seleccionarArchivoConfig() {
		return "Chọn tệp cấu hình";
	}

	public String suprimirConsolaCD() {
		return "Ẩn bảng điều khiển của " + Statics.nombre_cd.obtener();
	}

	public String suprimirVerificacionDeStacktrazos() {
		return "Ẩn kiểm tra stacktraces";
	}

	public String importadorBotonFusionar() {
		return "Hợp nhất";
	}

	@Override
	public String mod() {
		return "Mod";
	}

	@Override
	public String version() {
		return "Phiên bản";
	}

	@Override
	public String claseEncontrada() {
		return "Đã tìm thấy lớp";
	}

	@Override
	public String coincidencias() {
		return "Kết quả trùng khớp";
	}

	@Override
	public String resultadosDeBusqueda() {
		return "Kết quả tìm kiếm";
	}

	@Override
	public String desconocido() {
		return "không xác định";
	}

	@Override
	public String desconocida() {
		return "không xác định";
	}

	@Override
	public String curseForgeUrl() {
		return "URL CurseForge";
	}

	@Override
	public String modrinthUrl() {
		return "URL Modrinth";
	}

	@Override
	public String modsEncontradosPara(String clase) {
		return "Các mod được tìm thấy cho " + clase;
	}

	@Override
	public String entradaCarpetaInvalida(String ruta) {
		return "Đường dẫn thư mục không hợp lệ: " + ruta;
	}

	@Override
	public String carpetaSinHashes(String ruta) {
		return "Thư mục thiếu hashes: " + ruta;
	}

	@Override
	public String noSePudoAccederCarpeta(String ruta) {
		return "Không thể truy cập thư mục: " + ruta;
	}

	@Override
	public String archivoFaltanteEnCarpeta(String ruta, String subRuta) {
		return "Thiếu tệp trong thư mục: " + ruta + "/" + subRuta;
	}

	@Override
	public String hashIncorrectoEn(String ruta, String subRuta) {
		return "Hash không chính xác tại: " + ruta + "/" + subRuta;
	}

	@Override
	public String archivoNoAutorizadoEnCarpeta(String ruta, String subRuta) {
		return "Tệp không được phép trong thư mục: " + ruta + "/" + subRuta;
	}

	@Override
	public String entradaArchivoInvalida(String ruta) {
		return "Đường dẫn tệp không hợp lệ: " + ruta;
	}

	@Override
	public String hashFaltanteParaArchivo(String ruta) {
		return "Thiếu hash cho tệp: " + ruta;
	}

	@Override
	public String archivoFaltante(String ruta) {
		return "Thiếu tệp: " + ruta;
	}

	@Override
	public String errorAlLeerArchivo(String ruta) {
		return "Lỗi khi đọc tệp: " + ruta;
	}

	@Override
	public String hashIncorrectoParaArchivo(String ruta) {
		return "Hash không chính xác cho tệp: " + ruta;
	}

	@Override
	public String listo() {
		return "Xong!";
	}

	public String error_al_cargar_plantilla_desde_disco() {
		return "Lỗi khi tải mẫu từ đĩa: ";
	}

	public String no_se_encontro_plantilla_restablecer() {
		return "Không tìm thấy mẫu. Hãy khôi phục bằng nút 'Restablecer Plantilla'.";
	}

	public String plantilla_guardada_en() {
		return "Mẫu đã được lưu tại: ";
	}

	public String plantilla_restablecida_correctamente() {
		return "Khôi phục mẫu thành công.";
	}

	public String error_al_guardar() {
		return "Lỗi khi lưu: ";
	}

	public String error_al_restablecer() {
		return "Lỗi khi khôi phục: ";
	}

	public String error_al_restablecer_imagen() {
		return "Lỗi khi khôi phục hình ảnh: ";
	}

	public String no_se_encontro_imagen_en_recurso() {
		return "Không tìm thấy hình ảnh trong tài nguyên: ";
	}

	public String imagen_restablecida() {
		return "Đã khôi phục hình ảnh: ";
	}

	public String editor_html() {
		return "Trình chỉnh sửa HTML";
	}

	public String vista_previa() {
		return "Xem trước";
	}

	public String configuracion_colores_imagenes() {
		return "Cấu hình Màu sắc và Hình ảnh";
	}

	public String imagenes_con_ruta(String ruta) {
		return "Hình ảnh (" + ruta + ")";
	}

	public String enlaces_imagenes_reportes_compartidos() {
		return "Liên kết hình ảnh (báo cáo được chia sẻ)";
	}

	public String enlaces_imagenes_reporte_compartido() {
		return "Liên kết hình ảnh (báo cáo được chia sẻ)";
	}

	public String url_usada_en_reportes_compartidos() {
		return "URL được sử dụng trong báo cáo được chia sẻ";
	}

	public String error_creando_codice_json() {
		return "Lỗi khi tạo codice.json: ";
	}

	public String error_exportando() {
		return "Lỗi khi xuất: ";
	}

	public String validacion() {
		return "Xác thực";
	}

	public String ver_codigo() {
		return "Xem mã";
	}

	public String importar_instancia() {
		return "Nhập instance";
	}

	public String compartir_instancia() {
		return "Chia sẻ instance";
	}

	public String error_al_cargar_mods() {
		return "Lỗi khi tải mod.";
	}

	public String instalar() {
		return "Cài đặt";
	}

	public String mods_instalados() {
		return "Mod đã cài đặt";
	}

	public String guardar_como_archivo() {
		return "Lưu dưới dạng tệp";
	}

	public String exportando_modpack() {
		return "Đang xuất modpack...";
	}

	public String modpack_exportado() {
		return "Đã xuất modpack:\n";
	}

	public String conectando() {
		return "Đang kết nối...";
	}

	public String esperando_descarga() {
		return "Đang chờ tải xuống...";
	}

	public String finalizado() {
		return "Hoàn thành";
	}

	public String retener_dos_puntos() {
		return "Giữ lại:";
	}

	public String descargar_deps() {
		return "Tải xuống deps";
	}

	public String no_faltan_dependencias() {
		return "Không thiếu dependencies.";
	}

	public String descargar_nbt_para_quests() {
		return "Tải xuống NBT CHO QUESTS";
	}

	public String descargar_nbt() {
		return "Tải xuống NBT";
	}

	public String error_cargando_informe() {
		return "Lỗi khi tải báo cáo: ";
	}

	@Override
	public String exportar_modpack() {
		return "Xuất modpack";
	}

	@Override
	public String error_exportando_modpack() {
		return "Lỗi khi xuất modpack:\n";
	}

	@Override
	public String importador_confirmar_descargar_nbt_para_quests() {
		return "Các dependencies NBT cần thiết để hợp nhất quests sẽ được tải xuống.\n\n"
				+ "Sau đó có thể cần khởi động lại để chúng vào classpath.";
	}

	@Override
	public String resultado_nulo() {
		return "Kết quả null.";
	}

	@Override
	public String dependencia_nbt_descargada_reiniciar(String nombrePrograma) {
		return "Đã tải xuống dependency NBT.\n\n" + "Khởi động lại " + nombrePrograma
				+ " nếu việc hợp nhất SNBT vẫn báo thiếu engine NBT.";
	}

	@Override
	public String no_se_pudo_descargar_dependencia_nbt() {
		return "Không thể tải xuống dependency NBT.";
	}

	@Override
	public String profilerTituloRendimiento() {
		return "Profiler hiệu suất";
	}

	@Override
	public String profilerEstadoActivo() {
		return "Đang hoạt động";
	}

	@Override
	public String profilerAyudaMinaly() {
		return "Các phương thức chậm nhất xuất hiện ở trên cùng. Thanh hiển thị trọng số tương đối của thời gian tích lũy.";
	}

	@Override
	public String profilerConfigColorPanel() {
		return "Màu bảng điều khiển";
	}

	@Override
	public String profilerConfigColorBarra() {
		return "Màu thanh";
	}

	@Override
	public String profilerConfigUsarModeloOriginal() {
		return "Sử dụng mô hình gốc";
	}

	@Override
	public String profilerColumnaClase() {
		return "Lớp";
	}

	@Override
	public String profilerColumnaMetodo() {
		return "Phương thức";
	}

	@Override
	public String profilerColumnaLlamadas() {
		return "Lời gọi";
	}

	@Override
	public String profilerColumnaTiempoTotal() {
		return "Tổng thời gian";
	}

	@Override
	public String profilerEstadoResumen(String estado, int metodos, int top, String totalVisible) {
		return estado + " | phương thức: " + metodos + " | đầu: " + top + " | tổng hiển thị: " + totalVisible;
	}

	@Override
	public String samplerTituloRendimiento() {
		return "Sampler hiệu suất";
	}

	@Override
	public String samplerAyudaEineLotta() {
		return "Các phương thức có thời gian tích lũy nhiều nhất xuất hiện ở trên cùng. Thanh hiển thị trực quan trọng số tương đối.";
	}

	@Override
	public String samplerColumnaMuestras() {
		return "Mẫu";
	}

	@Override
	public String samplerColumnaPromedio() {
		return "Trung bình";
	}

	@Override
	public String samplerEstadoResumen(String estado, int metodos, int top) {
		return estado + " | phương thức: " + metodos + " | đầu: " + top;
	}

	public String mostrarSelectorAplicacionPrincipal() {
		return "Bộ chọn ứng dụng trong giao diện chính";
	}

	public String mostrarBotonCDLauncherPrincipal() {
		return "Nút CDLauncher trong giao diện chính";
	}

	public String mostrarBotonCDModsPrincipal() {
		return "Nút CDMods trong giao diện chính";
	}

	public String mostrarBotonIAPrincipal() {
		return "Nút AI trong giao diện chính";
	}

	@Override
	public String modsInstalados() {
		return "Mod đã cài đặt";
	}

	@Override
	public String migradorLegacyBotonSidebar() {
		return "Công cụ di chuyển";
	}

	@Override
	public String migradorLegacyTitulo() {
		return "Công cụ di chuyển bộ phân tích cũ";
	}

	@Override
	public String migradorLegacyDescripcion() {
		return "Trình hướng dẫn này giúp di chuyển cấu hình từ các bộ phân tích crash cũ và độc quyền sang các hệ thống hiện đại, mã nguồn mở và dễ bảo trì. Việc di chuyển các quy tắc này giảm sự phụ thuộc vào các công cụ đóng và tạo điều kiện thuận lợi cho việc kiểm toán, cộng tác và hỗ trợ cộng đồng. Các logo hiển thị ở đây không phải là logo thật; chúng là những bản nhái mà chúng tôi thấy vui.";
	}

	@Override
	public String migradorLegacyCrashAssistant() {
		return "Trình hướng dẫn Crash Assistant";
	}

	@Override
	public String migradorCrashAssistantDescripcion() {
		return "Nhập problematic_mods.json từ Crash Assistant và kết hợp nó với định dạng của CrashDetector thông qua trình hướng dẫn.";
	}

	@Override
	public String migradorEjecutar() {
		return "Chạy trình hướng dẫn";
	}

	@Override
	public String migradorCompletado() {
		return "Trình hướng dẫn đã hoàn thành.";
	}

	@Override
	public String migradorNadaParaMigrar() {
		return "Không tìm thấy gì để di chuyển.";
	}

	@Override
	public String migradorCAUsarPrimitiva() {
		return "Sử dụng giao diện chính nguyên thủy";
	}

	@Override
	public String migradorCADeshabilitarChecks() {
		return "Vô hiệu hóa các kiểm tra";
	}

	@Override
	public String migradorCAAvisoUrlSoporteWysiwyg() {
		return "Liên kết hỗ trợ không được di chuyển vì bạn không sử dụng giao diện nguyên thủy. Để thay đổi URL hỗ trợ, hãy sử dụng trình soạn thảo WYSIWYG trong cài đặt của CrashDetector.";
	}

	@Override
	public String migradorCAAvisoNoMigrado() {
		return "Cảnh báo: do sự khác biệt giữa các loại cấu hình, danh sách từ chối (denylist) của các kiểm tra không thể được di chuyển tự động. Từ đó, bạn cũng có thể tắt các cảnh báo của Intel và Mod List Diff; cũng như trong CrashDetector, những mục này được coi là các kiểm tra bình thường. Nếu bạn không bật chế độ nguyên thủy, bạn sẽ cần sử dụng trình soạn thảo HTML WYSIWYG trong cài đặt của CrashDetector để chèn các liên kết. Nếu bạn sử dụng chế độ nguyên thủy, liên kết trợ giúp sẽ được sao chép tự động từ Crash Assistant. Để thay đổi màu sắc và cài đặt trong giao diện đồ họa (GUI), bạn cần truy cập cài đặt của CrashDetector, vào khu vực CDSkinCape/GUI Editor và chọn loại GUI, cũng như việc triển khai cụ thể của nó. Các hình ảnh có thể được chỉnh sửa từ vị trí sau: "
				+ Statics.carpeta.resolve("imagenes").toString()
				+ ". Các kịch bản JEXL cũng sẽ không được di chuyển. Nếu bạn chỉ cần thực hiện phân tích cơ bản thông qua khớp chuỗi hoặc biểu thức chính quy, bạn có thể sử dụng chức năng 'Agregar Razones' trong cấu hình doanh nghiệp; nếu bạn cần các tính năng nâng cao hơn, bạn nên tạo một tiện ích mở rộng trong Java: https://github.com/FeatureCreepEAP/crashdetector-tutorial-extention-english";
	}

	@Override
	public String centroSoporteTituloCrash() {
		return "Ứng dụng đã đóng bất ngờ.";
	}

	@Override
	public String centroSoporteTextoSuperior() {
		return "Giao diện đồ họa này không được khuyến nghị cho người dùng nâng cao (DIY); nếu bạn là người dùng nâng cao, vui lòng vào Tệp -> Cài đặt -> Cài đặt của "
				+ Statics.nombre_cd.obtener()
				+ " và thay đổi Giao diện Chính từ \"trung tâm hỗ trợ\" thành \"kiểu trình khởi chạy\". Nếu bạn là tác giả của một modpack, bạn có thể thay đổi văn bản này ở đó.";
	}

	@Override
	public String centroSoporteTextoBajoTitulo() {
		return "Cung cấp chi tiết về những gì đã xảy ra trước khi sự cố.";
	}

	@Override
	public String centroSoporteTextoAviso() {
		return "Đọc kỹ văn bản trên. Ảnh chụp màn hình của cửa sổ này không chứa đủ thông tin.";
	}

	@Override
	public String centroSoporteArchivosDisponibles() {
		return "Các tệp nhật ký có sẵn";
	}

	@Override
	public String centroSoporteSubirTodoYCopiar() {
		return "Tải lên tất cả và sao chép tin nhắn với các liên kết";
	}

	@Override
	public String centroSoportePedirAyuda() {
		return "Yêu cầu trợ giúp từ trung tâm hỗ trợ";
	}

	@Override
	public String mostrarEnExplorador() {
		return "Hiển thị trong trình khám phá";
	}

	@Override
	public String subirYCopiarEnlace() {
		return "Tải lên và sao chép liên kết";
	}

	@Override
	public String salir() {
		return "Thoát";
	}

	@Override
	public String colorTextoAviso() {
		return "Màu văn bản cảnh báo";
	}

	@Override
	public String colorBotonPrincipal() {
		return "Màu nút chính";
	}

	@Override
	public String colorTextoBotonPrincipal() {
		return "Màu văn bản nút chính";
	}

	@Override
	public String anchoVentana() {
		return "Chiều rộng cửa sổ";
	}

	@Override
	public String altoVentana() {
		return "Chiều cao cửa sổ";
	}

	@Override
	public String textoSuperiorPersonalizado() {
		return "Văn bản trên cùng tùy chỉnh";
	}

	@Override
	public String textoAvisoPersonalizado() {
		return "Văn bản cảnh báo tùy chỉnh";
	}

	@Override
	public String textoBajoTituloPersonalizado() {
		return "Văn bản dưới tiêu đề tùy chỉnh";
	}

	@Override
	public String urlSoporte() {
		return "URL hỗ trợ";
	}

	@Override
	public String leyProteccionDatosPersonales() {
		return "Luật bảo vệ dữ liệu cá nhân";
	}

	@Override
	public String altoFilaLog() {
		return "Chiều cao hàng nhật ký";
	}

	@Override
	public String anchoNombreArchivo() {
		return "Chiều rộng tên tệp";
	}

	@Override
	public String anchoBotonAbrir() {
		return "Chiều rộng nút mở";
	}

	@Override
	public String anchoBotonExplorador() {
		return "Chiều rộng nút hiển thị trong trình khám phá";
	}

	@Override
	public String anchoBotonSubir() {
		return "Chiều rộng nút tải lên";
	}

	@Override
	public String tamanoFuenteBotonPrincipal() {
		return "Kích thước phông chữ của nút chính";
	}

	@Override
	public String formatoBloqueLogs() {
		return "Định dạng khối nhật ký";
	}

	@Override
	public String formatoHeaderMensaje() {
		return "Định dạng tiêu đề thông báo";
	}

	@Override
	public String formatoEstructuraMensaje() {
		return "Định dạng cấu trúc thông báo";
	}

	@Override
	public String formatoLineaLog() {
		return "Định dạng dòng nhật ký";
	}

	@Override
	public String formatoSeparadorLogs() {
		return "Dấu phân cách giữa các bản ghi";
	}

	@Override
	public String formatoModlistDiff() {
		return "Định dạng sự khác biệt của mod";
	}

	@Override
	public String ocultarTextoAviso() {
		return "Ẩn văn bản cảnh báo";
	}

	@Override
	public String mostrarLogoCuadrado() {
		return "Hiển thị logo hình vuông";
	}

	@Override
	public String rutaLogoCuadrado() {
		return "Đường dẫn logo hình vuông";
	}

	@Override
	public String tamanoLogoCuadrado() {
		return "Kích thước logo hình vuông";
	}

	@Override
	public String migradorCAModoPrincipalAviso() {
		return "Chọn cách di chuyển giao diện chính. 'centro_soporte' giống nhất với giao diện mặc định của Crash Assistant, nhưng không được khuyến nghị, đặc biệt là cho người dùng DIY, vì nó có quá nhiều cửa sổ bật lên và thiết kế kém; nó chỉ tồn tại để giúp di chuyển từ môi trường cũ của Crash Assistant. 'primitiva' là một giao diện rất đơn giản chỉ dùng để chia sẻ; chỉ được gợi ý cho các modpack, không phải người dùng DIY, nếu bạn tắt tất cả các kiểm tra không cần thiết, và nó cũng gặp vấn đề với nhiều cửa sổ bật lên. 'original' giữ nguyên giao diện chính hiện tại của bạn và thường là tùy chọn được khuyến nghị cho hầu hết người dùng.";
	}

	@Override
	public String migradorCASitioLoggingAviso() {
		return "Chọn xem bạn có muốn di chuyển trang web ghi log từ Crash Assistant hay giữ lại trang hiện tại của CrashDetector. Crash Assistant thường sử dụng gnomebot.dev, trang này không được khuyến nghị so với SecureLogger, PastesDev hoặc CDPaste. Đối với hầu hết người dùng, khuyến nghị giữ lại trang web hiện tại.";
	}

	@Override
	public String ideScriptTitulo() {
		return "IDE Scripting";
	}

	@Override
	public String ideScriptBotonSidebar() {
		return "Scripting";
	}

	@Override
	public String ideScriptProyecto() {
		return "Dự án:";
	}

	@Override
	public String ideScriptNuevo() {
		return "Mới";
	}

	@Override
	public String ideScriptAbrirCarpeta() {
		return "Mở thư mục";
	}

	@Override
	public String ideScriptAbrirArchivo() {
		return "Mở tệp";
	}

	@Override
	public String ideScriptGuardarComo() {
		return "Lưu dưới dạng";
	}

	@Override
	public String ideScriptDescargarDeps() {
		return "Tải xuống deps";
	}

	@Override
	public String ideScriptCompletar() {
		return "IntelliSense";
	}

	@Override
	public String ideScriptExplorador() {
		return "Trình khám phá dự án";
	}

	@Override
	public String ideScriptSinArchivo() {
		return "không có tệp";
	}

	@Override
	public String ideScriptEstado(String proyecto, String archivo) {
		return proyecto + " | " + archivo;
	}

	@Override
	public String ideScriptProyectoDeshabilitadoAviso() {
		return "Loại dự án này đã tồn tại trong giao diện, nhưng bị vô hiệu hóa cho đến khi máy chủ ngôn ngữ hoặc bộ phân tích của nó được thêm vào.";
	}

	@Override
	public String ideScriptDeshabilitadoCorto() {
		return "(đã vô hiệu hóa)";
	}

	@Override
	public String ideScriptNoFaltanDependencias() {
		return "Không thiếu dependencies.";
	}

	@Override
	public String ideScriptConfirmarDescargaDeps(int cantidad, String lista) {
		return "Se descargaran " + cantidad + " dependencias para el IDE de scripts:\n\n" + lista
				+ "\nDespues puede ser necesario reiniciar para que entren al classpath.";
	}

	@Override
	public String ideScriptDepsDescargadas(String mensaje) {
		return "Kết quả tải xuống:\n\n" + mensaje
				+ "\n\nKhởi động lại chương trình nếu các lớp vẫn chưa xuất hiện trong classpath.";
	}

	@Override
	public String ideScriptErrorAbrirArchivo() {
		return "Lỗi khi mở tệp";
	}

	@Override
	public String ideScriptErrorGuardarArchivo() {
		return "Lỗi khi lưu tệp";
	}

	@Override
	public String ideScriptColorEditor() {
		return "Màu trình soạn thảo";
	}

	@Override
	public String ideScriptColorKeyword() {
		return "Màu từ khóa";
	}

	@Override
	public String ideScriptColorComentario() {
		return "Màu chú thích";
	}

	@Override
	public String ideScriptColorCadena() {
		return "Màu chuỗi";
	}

	@Override
	public String ideScriptProyectoFeatureCreep() {
		return "FeatureCreep Datafied Content (DMR/JSON)";
	}

	@Override
	public String ideScriptProyectoDatapackResourcepack() {
		return "Minecraft Datapacks / ResourcePacks (JSON)";
	}

	@Override
	public String ideScriptProyectoKubeJS() {
		return "KubeJS (JS)";
	}

	@Override
	public String ideScriptProyectoZenScript() {
		return "ZenScript (CraftTweaker)";
	}

	@Override
	public String ideScriptProyectoMineFlayer() {
		return "MineFlayer (Python)";
	}

	@Override
	public String ideScriptProyectoGroovyScript() {
		return "GroovyScript";
	}

	@Override
	public String ideScriptProyectoComputerCraftLua() {
		return "ComputerCraft Lua";
	}

	@Override
	public String ideScriptProyectoWorldEditCraftScript() {
		return "WorldEdit CraftScript";
	}

	@Override
	public String ideScriptProyectoJexel3() {
		return "Jexel3";
	}

	public String mcpBotonSidebar() {
		return "MCP";
	}

	public String iaAbrirMcp() {
		return "Mở MCP";
	}

	public String mcpTituloVentana() {
		return "CrashDetector MCP";
	}

	public String mcpTituloPrincipal() {
		return "Máy chủ MCP cho AI cục bộ";
	}

	public String mcpPuerto() {
		return "Cổng MCP:";
	}

	public String mcpDescargarDependencias() {
		return "Tải xuống dependencies MCP/CFR";
	}

	public String mcpIniciarServidor() {
		return "Khởi động máy chủ MCP";
	}

	public String mcpEstadoDependenciasNoCargadas() {
		return "Dependencies MCP/CFR chưa được tải. Hãy tải chúng và khởi động lại nếu nút vẫn bị vô hiệu hóa.";
	}

	public String mcpEstadoDependenciasCargadas() {
		return "Đã phát hiện dependencies MCP/CFR. Máy chủ MCP có thể được khởi động.";
	}

	public String mcpDependenciasDescargadasReiniciar() {
		return "Đã tải xuống dependencies. Nếu chúng vẫn chưa xuất hiện là đã tải, hãy khởi động lại CrashDetector.";
	}

	public String mcpErrorDescargandoDependencias(String error) {
		return "Không thể tải xuống dependencies MCP/CFR: " + error;
	}

	public String mcpServidorIniciado(int puerto) {
		return "Máy chủ MCP đã khởi động tại 127.0.0.1:" + puerto + "/mcp";
	}

	public String mcpErrorIniciandoServidor(String error) {
		return "Không thể khởi động máy chủ MCP: " + error;
	}

	public String mcpImagenNoDisponible() {
		return "Hình ảnh MCP không khả dụng";
	}

	public String colorAcento() {
		return "Màu nhấn";
	}

	public String mcpDescripcionHtml() {
		return "<b>Bảng này khởi động một máy chủ MCP cục bộ cho các trợ lý như Claude Desktop, Qwen Desktop Linux, Red Hat command-line-assistant hoặc Goose.</b>"
				+ "<br><br>"
				+ "Trước tiên, hãy tải xuống dependencies MCP/CFR. Sau đó, khởi động lại nếu các lớp không xuất hiện trong classpath."
				+ "<br><br>" + "<b>Claude Desktop / Qwen Desktop Linux, ví dụ:</b>" + "<pre>{\n"
				+ "  \"mcpServers\": {\n" + "    \"crashdetector\": {\n"
				+ "      \"url\": \"http://127.0.0.1:8765/mcp\"\n" + "    }\n" + "  }\n" + "}</pre>"
				+ "<b>Goose / command-line-assistant:</b>" + "<pre>http://127.0.0.1:8765/mcp</pre>"
				+ "Hiện tại, máy chủ MCP này còn cơ bản. Sau này, có thể thêm các công cụ thực tế để đọc nhật ký, truy vấn CFR, kiểm tra mod, tìm kiếm lớp và giải thích crash.";
	}

	@Override
	public String mensajeErrorJvmDllCurseForgeG1(boolean conOverwolf) {
		String textoOverwolf = "";

		if (conOverwolf) {
			textoOverwolf = "<p>Log cũng cho thấy dấu hiệu của <b>Overwolf</b> hoặc các DLL liên quan, như "
					+ "<b>OWClient.dll</b> hoặc <b>OWUtils.dll</b>. Điều này tự nó không chứng minh rằng Overwolf là nguyên nhân, "
					+ "nhưng chỉ ra rằng quá trình Minecraft đang chạy trong một môi trường bị sửa đổi nhiều hơn "
					+ "so với một lần khởi chạy vanilla bình thường.</p>";
		}

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Java đã đóng do lỗi native trong jvm.dll trong khi bộ thu gom G1 hoạt động trên CurseForge.</b>"
				+ "<p>Log chứa <b>EXCEPTION_ACCESS_VIOLATION</b> bên trong <b>jvm.dll</b>, "
				+ "và luồng đang hoạt động là <b>GCTaskThread</b>. Điều này có nghĩa là Java đã thất bại trong khi bộ thu gom rác "
				+ "<b>G1</b> đang làm việc.</p>"
				+ "<p>Cũng có dấu hiệu cho thấy trò chơi được khởi chạy từ một môi trường được quản lý bởi "
				+ "<b>CurseForge</b>, ví dụ: đường dẫn của CurseForge, <b>DCFInstanceId</b> hoặc dấu hiệu launcher của CurseForge.</p>"
				+ textoOverwolf
				+ "<p>Không thể chứng minh chỉ từ log rằng CurseForge hoặc Overwolf là nguyên nhân chính xác. "
				+ "Tuy nhiên, mô hình này thường thấy trong các lần khởi chạy của CurseForge và có thể liên quan "
				+ "đến sự kết hợp của <b>G1 GC</b>, bộ nhớ native, thư viện Minecraft, driver đồ họa, "
				+ "thư mục native được quản lý bởi CurseForge, overlay hoặc các thành phần bên ngoài của launcher.</p>"
				+ "<p><b>Giải pháp phổ biến:</b></p>" + "<ul>"
				+ "<li>Thay đổi bộ thu gom rác bằng cách thêm đối số JVM này: <b>-XX:+UseShenandoahGC</b></li>"
				+ "<li>Trong CurseForge, điều này thường được thêm vào các đối số Java bổ sung của hồ sơ hoặc launcher.</li>"
				+ "<li>Xóa bộ nhớ cache natives/bin của hồ sơ CurseForge để buộc các thư viện native được giải nén lại.</li>"
				+ "<li>Cập nhật Java và driver đồ họa.</li>"
				+ "<li>Nếu bạn sử dụng overlay, chương trình ghi, phần mềm diệt virus mạnh hoặc các công cụ khác tiêm vào trò chơi, hãy thử đóng chúng tạm thời.</li>"
				+ "</ul>" + "<p>Hướng dẫn tiếng Anh được khuyến nghị để thay đổi đối số trong CurseForge: "
				+ "<a href='https://youtu.be/UKFWBOZxB2o'>https://youtu.be/UKFWBOZxB2o</a></p>"
				+ "<p><b>Lưu ý cho Minecraft 1.16.5 trở về trước:</b> các phiên bản đó thường sử dụng Java 8. "
				+ "Nếu bạn đang dùng JDK 8 và muốn sử dụng Shenandoah, bạn có thể cần sử dụng "
				+ "<b>Red Hat Build of OpenJDK 8</b> và trỏ CurseForge đến cài đặt Java đó.</p>"
				+ "<p>Hướng dẫn của Red Hat để cài đặt OpenJDK 8 trên Windows: "
				+ "<a href='https://docs.redhat.com/en/documentation/red_hat_build_of_openjdk/8/html-single/installing_and_using_red_hat_build_of_openjdk_8_for_windows/index'>"
				+ "https://docs.redhat.com/en/documentation/red_hat_build_of_openjdk/8/html-single/installing_and_using_red_hat_build_of_openjdk_8_for_windows/index</a></p>";
	}

	@Override
	public String nombreErrorJvmDllCurseForgeG1() {
		return "Lỗi jvm.dll của CurseForge với G1 GC";
	}

	@Override
	public String mensajeErrorJvmDllC2Sodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Java đã đóng do lỗi native trong jvm.dll trong khi biên dịch mã của Sodium hoặc mod tương tự.</b>"
				+ "<p>Log chứa <b>EXCEPTION_ACCESS_VIOLATION</b> bên trong <b>jvm.dll</b>, "
				+ "và luồng đang hoạt động là <b>C2 CompilerThread</b>. Điều này có nghĩa là lỗi xảy ra bên trong "
				+ "trình biên dịch JIT của Java, không phải là một ngoại lệ bình thường của Minecraft.</p>"
				+ "<p>Trong trường hợp này, tác vụ biên dịch đề cập đến mã liên quan đến <b>Sodium</b>, "
				+ "<b>Embeddium</b>, <b>Rubidium</b> hoặc một lớp tương tự như "
				+ "<b>ClonedChunkSectionCache::acquire</b>.</p>" + "<p><b>Giải pháp được khuyến nghị:</b></p>" + "<ul>"
				+ "<li>Cập nhật <b>Sodium</b>, <b>Embeddium</b>, <b>Rubidium</b>, <b>Oculus</b> hoặc bất kỳ mod kết xuất liên quan nào.</li>"
				+ "<li>Cập nhật Java 17. Nếu bạn đã sử dụng phiên bản hiện đại, hãy thử bản phân phối khác như Temurin, Zulu hoặc Microsoft OpenJDK.</li>"
				+ "<li>Cập nhật driver card đồ họa.</li>"
				+ "<li>Thử tạm thời không có Sodium, Embeddium, Rubidium hoặc Oculus để xác nhận xem lỗi có biến mất không.</li>"
				+ "<li>Như một bài kiểm tra nâng cao, sử dụng <b>-XX:TieredStopAtLevel=1</b> để giảm việc sử dụng trình biên dịch C2. "
				+ "Điều này có thể làm giảm hiệu suất, nhưng giúp xác nhận xem lỗi có đến từ trình biên dịch JIT không.</li>"
				+ "</ul>" + "<p>Vấn đề này không giống như một crash bình thường của mod. Nó cũng khác với các lỗi mà "
				+ "<b>GCTaskThread</b> xuất hiện là luồng đang hoạt động. Ở đây mô hình chỉ ra nhiều hơn đến trình biên dịch C2 của Java "
				+ "đang biên dịch mã kết xuất được tối ưu hóa.</p>";
	}

	@Override
	public String nombreErrorJvmDllC2Sodium() {
		return "Lỗi native của Java với Sodium / Embeddium";
	}

	@Override
	public String mensajeErrorArchivoUsadoPorOtroProceso(String archivo) {
		String textoArchivo = "";

		if (archivo != null && archivo.length() > 0) {
			textoArchivo = "<p><b>Tệp bị khóa:</b> " + archivo + "</p>";
		}

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Một tệp đang được sử dụng bởi một tiến trình khác.</b>"
				+ "<p>Nhật ký lỗi chứa lỗi <b>java.nio.file.FileSystemException</b> cho thấy Windows không thể "
				+ "truy cập vào một tệp vì một chương trình khác đang sử dụng nó.</p>" + textoArchivo
				+ "<p>Điều này thường xảy ra khi Minecraft, CurseForge, trình soạn thảo văn bản, phần mềm diệt virus, OneDrive, "
				+ "chương trình đồng bộ hóa hoặc thậm chí một phiên bản khác của trò chơi đang mở tệp đó.</p>"
				+ "<p><b>Giải pháp khuyến nghị:</b></p>" + "<ul>" + "<li>Đóng hoàn toàn Minecraft.</li>"
				+ "<li>Đóng CurseForge và mở lại.</li>"
				+ "<li>Kiểm tra Task Manager và kết thúc các tiến trình trùng lặp của <b>javaw.exe</b>, <b>java.exe</b>, Minecraft hoặc CurseForge.</li>"
				+ "<li>Đóng các trình soạn thảo văn bản có thể đang mở tệp cấu hình.</li>"
				+ "<li>Nếu tệp nằm trong OneDrive, Dropbox hoặc chương trình tương tự, hãy tạm dừng đồng bộ hóa.</li>"
				+ "<li>Nếu vấn đề vẫn tiếp diễn, hãy khởi động lại máy tính để giải phóng khóa tệp.</li>" + "</ul>"
				+ "<p>Sau khi giải phóng tệp, hãy khởi động lại modpack. Nếu tệp bị khóa là tệp cấu hình, "
				+ "việc sao lưu và tạo lại tệp đó cũng có thể hữu ích.</p>";
	}

	@Override
	public String nombreErrorArchivoUsadoPorOtroProceso() {
		return "Tệp đang được sử dụng bởi tiến trình khác";
	}

	@Override
	public String nombreErrorBetterEndPaletaChunkAgua() {
		return "Lỗi bảng màu chunk trong BetterEnd";
	}

	@Override
	public String mensajeErrorBetterEndPaletaChunkAgua() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "BetterEnd xuất hiện trong một lỗi crash khi render chunks.</b>" + "<p>Trò chơi đã báo lỗi:</p>"
				+ "<code>EntryMissingException: Missing Palette entry</code>"
				+ "<p>Lỗi xảy ra khi đang xây dựng các mesh của chunks và BetterEnd can thiệp "
				+ "vào màu nước thông qua <code>betterend$be_getWaterColor</code>.</p>"
				+ "<p>Điều này thường chỉ ra vấn đề với BetterEnd, BCLib/WorldWeaver, quá trình tạo thế giới, "
				+ "hoặc tương tác với Sodium/render chất lỏng.</p>"
				+ "<p>Hãy thử cập nhật BetterEnd và các phụ thuộc, thử nghiệm tạm thời without BetterEnd, "
				+ "và kiểm tra xem lỗi crash có chỉ xảy ra gần một số chunks hoặc quần xã sinh vật nhất định không.</p>";
	}

	@Override
	public String detectarAutomaticamente() {
		// TODO Auto-generated method stub
		return "Tự động phát hiện";
	}

	public String guardAbrirEscanerNube() {
		return "VirusTotal và MetaDefender";
	}

	public String escanerNubeBotonLateral() {
		return "Máy quét đám mây";
	}

	public String escanerNubeTitulo() {
		return "Máy quét mod với VirusTotal và MetaDefender";
	}

	public String escanerNubeMensajeRecuerdoParallelArtistProject() {
		return "Chúng tôi rất nhớ các bạn. Sự sáng tạo, năng lượng và mọi thứ các bạn chia sẻ với cộng đồng vẫn hiện diện trong dự án này. Công cụ này lưu giữ kỷ niệm về Parallel Artist Project một cách trân trọng.";
	}

	public String escanerNubeAvisoPrivacidad() {
		return "Lưu ý: khi hash không có báo cáo, tệp JAR sẽ được gửi đến nhà cung cấp đã chọn. Các mẫu tải lên dịch vụ công cộng có thể được chia sẻ với đối tác bảo mật. Xử lý song song không tăng hạn mức tài khoản và quá nhiều luồng có thể gây ra lỗi HTTP 429. Không gửi mod riêng tư hoặc bí mật mà không xem xét điều khoản tài khoản của bạn.";
	}

	public String escanerNubeClaveVirusTotal() {
		return "Khóa API của VirusTotal:";
	}

	public String escanerNubeClaveMetaDefender() {
		return "Khóa API của MetaDefender:";
	}

	public String escanerNubeNumeroHilos() {
		return "Luồng đồng thời:";
	}

	public String escanerNubeGuardarClaves() {
		return "Lưu khóa API toàn cục";
	}

	public String escanerNubeEscanearVirusTotal() {
		return "Quét với VirusTotal";
	}

	public String escanerNubeEscanearMetaDefender() {
		return "Quét với MetaDefender";
	}

	public String escanerNubeEscanearAmbos() {
		return "Quét với cả hai";
	}

	public String escanerNubeCancelar() {
		return "Hủy";
	}

	public String escanerNubeSeccionClaves() {
		return "Khóa API và thực thi";
	}

	public String escanerNubeSeccionResultados() {
		return "Kết quả phân tích";
	}

	public String escanerNubeClavesGuardadas() {
		return "Các khóa API toàn cục đã được lưu thành công.";
	}

	public String escanerNubeConfirmarSubidaTitulo() {
		return "Xác nhận phân tích từ xa";
	}

	public String escanerNubeConfirmarSubidaMensaje() {
		return "Trước tiên sẽ kiểm tra SHA-256. Các tệp không có báo cáo sẽ được tải lên nhà cung cấp đã chọn. Bạn có muốn tiếp tục không?";
	}

	public String escanerNubeFaltaClaveVirusTotal() {
		return "Thiếu khóa API của VirusTotal.";
	}

	public String escanerNubeFaltaClaveMetaDefender() {
		return "Thiếu khóa API của MetaDefender.";
	}

	public String escanerNubeSinArchivosJar() {
		return "Không tìm thấy tệp JAR có thể đọc được trong các thư mục mod đã cấu hình.";
	}

	public String escanerNubeArchivoNoLegible() {
		return "Tệp không tồn tại hoặc không thể đọc được.";
	}

	public String escanerNubeImagenNoDisponible() {
		return "Hình ảnh của Parallel Artist Project không khả dụng";
	}

	public String escanerNubeEstadoListo() {
		return "Sẵn sàng";
	}

	public String escanerNubeEstadoEscaneando(int completados, int total) {
		return "Đang phân tích: " + completados + " trên " + total;
	}

	public String escanerNubeEstadoCompletado(int completados) {
		return "Phân tích hoàn tất. Nhiệm vụ đã hoàn thành: " + completados;
	}

	public String escanerNubeEstadoCancelado() {
		return "Phân tích đã hủy";
	}

	public String escanerNubeProveedorVirusTotal() {
		return "VirusTotal";
	}

	public String escanerNubeProveedorMetaDefender() {
		return "MetaDefender";
	}

	public String escanerNubeProveedorDesconocido() {
		return "Nhà cung cấp không xác định";
	}

	public String escanerNubeResultadoSinDetecciones() {
		return "Không phát hiện";
	}

	public String escanerNubeResultadoMalicioso() {
		return "Độc hại";
	}

	public String escanerNubeResultadoSospechoso() {
		return "Đáng ngờ";
	}

	public String escanerNubeResultadoFallido() {
		return "Thất bại";
	}

	public String escanerNubeResultadoDesconocido() {
		return "Không xác định";
	}

	public String escanerNubeDetalleInformeExistente() {
		return "Kết quả thu được thông qua truy vấn hash; tệp không được tải lên lại.";
	}

	public String escanerNubeDetalleArchivoSubido() {
		return "Hash không có báo cáo và tệp đã được tải lên để phân tích.";
	}

	public String escanerNubeDetalleSinEstadisticas() {
		return "Nhà cung cấp đã hoàn thành yêu cầu nhưng không trả về thống kê sử dụng được.";
	}

	public String escanerNubeErrorClaveRechazada(String proveedor) {
		return proveedor + " đã từ chối khóa API hoặc tài khoản không có quyền cho thao tác này.";
	}

	public String escanerNubeErrorLimite(String proveedor) {
		return proveedor + " đã từ chối yêu cầu vì đã đạt giới hạn hoặc kiểm soát tần suất của tài khoản.";
	}

	public String escanerNubeErrorHttp(String proveedor, int codigo, String detalle) {
		String sufijo = detalle == null || detalle.trim().isEmpty() ? "" : " Chi tiết: " + detalle;
		return proveedor + " trả về mã HTTP " + codigo + "." + sufijo;
	}

	public String escanerNubeErrorRespuestaJson(String proveedor) {
		return proveedor + " trả về phản hồi JSON không hợp lệ.";
	}

	public String escanerNubeErrorTiempoEspera(String proveedor) {
		return "Hết thời gian chờ phân tích của " + proveedor + ".";
	}

	public String escanerNubeErrorIdAnalisis() {
		return "Nhà cung cấp không trả về ID phân tích.";
	}

	public String escanerNubeErrorUrlSubidaVirusTotal() {
		return "VirusTotal không trả về URL để tải lên tệp lớn.";
	}

	public String escanerNubeErrorUrlSubidaNoSegura() {
		return "Nhà cung cấp trả về URL tải lên không sử dụng HTTPS.";
	}

	public String escanerNubeErrorDesconocido() {
		return "Đã xảy ra lỗi không xác định trong quá trình phân tích.";
	}

	public String escanerNubeColProveedor() {
		return "Nhà cung cấp";
	}

	public String escanerNubeColArchivo() {
		return "Tệp";
	}

	public String escanerNubeColEstado() {
		return "Trạng thái";
	}

	public String escanerNubeColDetecciones() {
		return "Phát hiện";
	}

	public String escanerNubeColMotores() {
		return "Công cụ";
	}

	public String escanerNubeColSha256() {
		return "SHA-256";
	}

	public String escanerNubeColDetalle() {
		return "Chi tiết";
	}

	public String escanerNubeColorFondo() {
		return "Máy quét đám mây: màu nền";
	}

	public String escanerNubeColorPanel() {
		return "Máy quét đám mây: màu bảng điều khiển";
	}

	public String escanerNubeColorPanelClaro() {
		return "Máy quét đám mây: màu bảng điều khiển sáng";
	}

	public String escanerNubeColorTexto() {
		return "Máy quét đám mây: màu văn bản";
	}

	public String escanerNubeColorTextoSecundario() {
		return "Máy quét đám mây: màu văn bản phụ";
	}

	public String escanerNubeColorCampo() {
		return "Máy quét đám mây: màu trường";
	}

	public String escanerNubeColorCampoTexto() {
		return "Máy quét đám mây: màu văn bản trường";
	}

	public String escanerNubeColorVirusTotal() {
		return "Máy quét đám mây: nhấn mạnh VirusTotal";
	}

	public String escanerNubeColorMetaDefender() {
		return "Máy quét đám mây: nhấn mạnh MetaDefender";
	}

	public String escanerNubeColorAmbos() {
		return "Máy quét đám mây: nhấn mạnh kết hợp";
	}

	public String escanerNubeColorAdvertencia() {
		return "Máy quét đám mây: màu cảnh báo";
	}

	public String escanerNubeColorTabla() {
		return "Máy quét đám mây: màu bảng";
	}

	public String escanerNubeColorTablaTexto() {
		return "Máy quét đám mây: màu văn bản bảng";
	}

	public String escanerNubeColorSeleccion() {
		return "Máy quét đám mây: màu lựa chọn";
	}

	public String escanerNubeColorSeleccionTexto() {
		return "Máy quét đám mây: màu văn bản đã chọn";
	}

	public String escanerNubeColorBorde() {
		return "Máy quét đám mây: màu viền";
	}

	public String controlJVMDisponible() {
		return "Kênh chẩn đoán cục bộ của JVM khả dụng.";
	}

	public String controlJVMGcAceptado() {
		return "JVM đã nhận yêu cầu thu gom rác. System.gc() là một yêu cầu nỗ lực tốt nhất và JVM có thể quyết định thực hiện bao nhiêu công việc.";
	}

	public String controlJVMHeapDumpCreado(String ruta) {
		return "Heap dump đã được tạo tại:\n" + ruta;
	}

	public String controlJVMCrashAceptado() {
		return "JVM đã chấp nhận yêu cầu crash chẩn đoán. Quá trình sẽ đóng lại và HotSpot sẽ cố gắng tạo tệp hs_err_pid.";
	}

	public String controlJVMNoDisponible() {
		return "Không thể kết nối với kênh chẩn đoán cục bộ của JVM đang được quan sát. Tính năng này chỉ khả dụng khi CrashDetector được tải bên trong quá trình trò chơi.";
	}

	public String controlJVMNoAutorizado() {
		return "JVM đã từ chối yêu cầu chẩn đoán vì token cục bộ không hợp lệ.";
	}

	public String controlJVMError(String detalle) {
		return detalle == null || detalle.trim().isEmpty() ? "Thao tác chẩn đoán JVM thất bại."
				: "Thao tác chẩn đoán JVM thất bại:\n" + detalle;
	}

	public String consolaCrearHsErr() {
		return "Gây crash và tạo hs_err";
	}

	public String consolaEjecutarGc() {
		return "Yêu cầu thu gom rác";
	}

	public String consolaHeapDump() {
		return "Heap dump";
	}

	public String consolaBajar() {
		return "Đi đến cuối bảng điều khiển";
	}

	public String consolaCompartirLogs() {
		return "Chia sẻ nhật ký";
	}

	public String consolaDetenerProceso() {
		return "Dừng quá trình";
	}

	public String consolaAdvertenciaCrashHsErr() {
		return "Thao tác này sẽ cố ý gây ra lỗi gốc nghiêm trọng trong JVM của trò chơi. Quá trình sẽ kết thúc ngay lập tức và mọi tiến trình chưa lưu sẽ bị mất. HotSpot sẽ cố gắng ghi tệp hs_err_pid vào thư mục làm việc hoặc thư mục tạm. Tiếp tục?";
	}

	public String consolaGenerarHeapDump() {
		return "Tạo heap dump";
	}

	public String consolaAbrirVisorHeapDump() {
		return "Mở trình xem heap dump";
	}

	public String consolaHeapDumpAccion() {
		return "Bạn có thể tạo heap dump của JVM đang được quan sát hoặc mở trình xem để nhập một cái hiện có.";
	}

	public String consolaAdvertenciaHeapDump() {
		return "Heap dump có thể rất lớn và có thể tạm dừng trò chơi trong khi ghi. Nó cũng có thể chứa thông tin nhạy cảm hiện diện trong bộ nhớ, bao gồm tên người dùng, đường dẫn, tin nhắn, địa chỉ, khóa, mật khẩu hoặc token truy cập. Lưu và chia sẻ tệp một cách cẩn thận.";
	}

	public String consolaHeapDumpSoloVivos() {
		return "Chỉ bao gồm các đối tượng sống và có thể truy cập";
	}

	public String consolaGuardarHeapDump() {
		return "Lưu heap dump";
	}

	public String consolaHeapDumpSobrescribir(String ruta) {
		return "Tệp đã tồn tại và HotSpot không thể ghi đè lên nó:\n" + ruta
				+ "\n\nXóa nó trước khi tạo heap dump mới?";
	}

	public String consolaHeapDumpAbrirDespues() {
		return "Heap dump đã hoàn tất. Mở nó ngay bây giờ trong trình xem?";
	}

	public String consolaDiagnosticoJVM() {
		return "Chẩn đoán JVM";
	}

	public String heapVisorTitulo() {
		return "Trình xem heap dump";
	}

	public String heapVisorDescripcion() {
		return "Nhập tệp HPROF để xem lớp, gói và mod nào chiếm nhiều bộ nhớ bề mặt ước tính nhất. Phân tích nhanh không tính toán kích thước được giữ lại hoặc cây thống trị, nhưng cho phép xác định các đối tượng tiêu thụ lớn mà không cần tải từng đối tượng từ dump vào bộ nhớ.";
	}

	public String heapVisorAyudaArbol() {
		return "Tab lớp sắp xếp các loại theo bộ nhớ bề mặt ước tính. Tab mod và gói cho phép mở rộng nhiều cấp độ: JAR hoặc thư viện, gói và lớp. Các lớp được liên kết với JAR từ thư mục mod cuối cùng khi tùy chọn nhận dạng được bật.";
	}

	public String heapVisorSeleccionarArchivo() {
		return "Chọn heap dump HPROF";
	}

	public String heapVisorAnalisisEnCurso() {
		return "Đang có một phân tích heap dump đang diễn ra.";
	}

	public String heapVisorArchivoNoValido() {
		return "Tệp đã chọn không tồn tại hoặc không thể đọc được.";
	}

	public String heapVisorAnalizando(String archivo) {
		return "Đang phân tích " + archivo + "...";
	}

	public String heapVisorProgreso(int porcentaje, String detalle) {
		return "Đang phân tích heap dump: " + porcentaje + "% — " + detalle;
	}

	public String heapVisorListo(String memoria, long objetos) {
		return "Sẵn sàng: " + memoria + " bề mặt ước tính trong " + objetos + " đối tượng hoặc mảng.";
	}

	public String heapVisorCancelado() {
		return "Phân tích đã hủy.";
	}

	public String heapVisorError() {
		return "Phân tích heap dump thất bại.";
	}

	public String heapVisorErrorDetalle(String detalle) {
		return "Không thể phân tích heap dump:\n" + detalle;
	}

	public String heapVisorRaiz() {
		return "Heap";
	}

	public String heapVisorSinMod() {
		return "Không có mod được xác định";
	}

	public String heapVisorImportar() {
		return "Nhập HPROF";
	}

	public String heapVisorCancelar() {
		return "Hủy phân tích";
	}

	public String heapVisorExpandir() {
		return "Mở rộng đến 4 cấp độ";
	}

	public String heapVisorContraer() {
		return "Thu gọn tất cả";
	}

	public String heapVisorIdentificarMods() {
		return "Liên kết lớp với JAR của mod";
	}

	public String heapVisorPestanaClases() {
		return "Lớp";
	}

	public String heapVisorPestanaMods() {
		return "Mod và gói";
	}

	public String heapVisorColClase() {
		return "Lớp";
	}

	public String heapVisorColMod() {
		return "Mod hoặc thư viện";
	}

	public String heapVisorColInstancias() {
		return "Phiên bản";
	}

	public String heapVisorColMemoria() {
		return "Byte ước tính";
	}

	public String heapVisorColPorcentaje() {
		return "Phần trăm";
	}

	public String heapVisorDetalleNodo(long instancias, String memoria) {
		return instancias + " phiên bản — " + memoria;
	}

	public String heapVisorImagenAlternativa() {
		return "Lựa chọn của Iran";
	}

	public String heapVisorColorTextoClaro() {
		return "Văn bản sáng của trình xem heap";
	}

	public String heapVisorColorVerde() {
		return "Màu xanh lá của trình xem heap";
	}

	public String heapVisorColorRojo() {
		return "Màu đỏ của trình xem heap";
	}

	public String heapVisorColorTabla() {
		return "Nền bảng của trình xem heap";
	}

	public String heapVisorColorSeleccion() {
		return "Lựa chọn của trình xem heap";
	}

	public String heapVisorColorBorde() {
		return "Viền của trình xem heap";
	}

	public String heapVisorGrupoMinecraft() {
		return "Minecraft";
	}

	public String heapVisorGrupoForge() {
		return "Minecraft Forge";
	}

	public String heapVisorGrupoFabric() {
		return "Fabric";
	}

	public String heapVisorGrupoLwjgl() {
		return "LWJGL";
	}

	public String heapVisorGrupoJava() {
		return "Java";
	}

	public String heapVisorGrupoArreglos() {
		return "Mảng";
	}

	public String heapVisorClaseDesconocida() {
		return "Lớp không xác định";
	}

	public String heapVisorClaseId(String idHexadecimal) {
		return "Lớp 0x" + idHexadecimal;
	}

	public String heapVisorTipoPrimitivoDesconocido() {
		return "Nguyên thủy không xác định";
	}

	public String consolaCancelar() {
		return "Hủy";
	}

}
