package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Tailandes implements Idioma {
	Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "th";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "tailandes";
	}

	@Override
	public String nombre_del_idioma() {
		return "ไทย";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_tailandia.png");
	}

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>ไม่ใช่โฟลเดอร์ม็อดที่ถูกต้อง</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError() + "'>ไม่พบไฟล์ JAR ของ CrashDetector</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>กำลังค้นหาสำหรับ PID: " + String.valueOf(pid)
				+ "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") เสียแล้ว!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>ไม่พบ JVM</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>การอัปเดตไดรเวอร์ ATI/AMD อาจช่วยได้ อ่านคู่มือนี้เพื่อแก้ไข: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>คู่มืออัปเดตไดรเวอร์</a> https://www.amd.com/es/support/download/drivers.html ดาวน์โหลด </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>บางเวอร์ชันเก่าอาจมีปัญหากับกราฟิก Nouveau ในหน้าจอโหลดเริ่มต้น</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>มีปัญหากับไดรเวอร์กราฟิกของคุณ หากใช้ GPU หรือ APU ของ AMD/ATI ให้ทำการอัปเดตไดรเวอร์ AMD หากใช้การ์ดจอ NVIDIA ให้ตั้งค่าเกมและทุกอินสแตนซ์ของ javaw.exe ให้ใช้การ์ดจอแยก อ่านคู่มือนี้: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>คู่มืออัปเดตไดรเวอร์</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Tu ventana FML Early está fallando. Para cambiar esto, ve a (.minecraft/config/fml.toml) y edita earlyWindowProvider para que sea earlyWindowProvider=\"none\". Si estás en una Mac M1, también asegúrate de estar usando una เวอร์ชัน ARM de Java, no una เวอร์ชัน Intel x64. Este también es un ปัญหา común si tienes controladores desactualizados. Consulta esta guía si estás en Windows y desactivar esto no funciona: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>คู่มืออัปเดตไดรเวอร์</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError() + "'>คุณไม่มี dependency ที่จำเป็นทั้งหมด:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("ร้องขอโดย", "Solicitado por").replace("ช่วงเวอร์ชันที่คาดหวัง", "rango esperado")
				+ "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>รายงาน CrashDetector ของคุณอยู่ที่นี่ <a href='"
				+ archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>ดูรายงาน</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>นี่คือ GUI ของ CrashDetector หากเกมปิดได้ตามปกติ คุณสามารถเพิกเฉยได้</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>ดูรายงาน</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>ดูรายงานภายในเครื่องในเบราว์เซอร์ของคุณ</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "แชร์รายงาน";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "แชร์รายงาน. Sus บันทึกs se โหลดán en securelogger.net y tu informe se โหลดá a otro sitio.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ตรวจพบไฟล์ JAR ที่มีปัญหา (ให้ความสำคัญกับ FATAL ก่อน จากนั้นระดับสูงและบรรทัดล่าง):</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> ระดับ:</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>อาจเป็นสาเหตุร้ายแรง:</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ตรวจพบ ModID ที่มีปัญหา (ให้ความสำคัญกับ FATAL ก่อน จากนั้นระดับต่ำและบรรทัดล่าง):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ตรวจพบแพ็กเกจที่มีปัญหา (ให้ความสำคัญกับ FATAL ก่อน จากนั้นระดับต่ำและบรรทัดล่าง):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>คุณมีคลาสร้ายแรง (FATAL) ซึ่งสำคัญมาก สาเหตุทั่วไปคือ coreม็อด ที่ไม่ดีหรือ dependency ร้ายแรง คุณสามารถใช้ QuickFix เพื่อค้นหาม็อดที่มีคลาสเหล่านี้ ตรวจพบคลาสร้ายแรงที่ขาดหายไป:</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>เนื้อหาใน {} (รายการที่สำคัญที่สุดอยู่ด้านบน แสดง 20 รายการแรกเท่านั้น):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ตรวจพบการตั้งค่า SpongeMixin ที่มีปัญหา: "
				+ "</b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>คุณมีม็อดที่มีแพ็กเกจซ้ำกัน สามารถแก้ได้โดยลบแพ็กเกจ (โฟลเดอร์) ออกจากไฟล์ jar คุณสามารถเปิด jar ด้วยโปรแกรมบีบอัดไฟล์ เช่น WinRAR หรือ 7z หรือเปลี่ยนนามสกุลจาก .jar เป็น .zip ลบโฟลเดอร์ แล้วเปลี่ยนกลับเป็น .jar</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>คุณมีม็อดซ้ำ</b> "
				+ linea.replace("จากไฟล์ม็อด", "de ม็อด ไฟล์s ");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge สงสัยว่าม็อดนี้มีปัญหา:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV ต้องใช้ lithostitched คุณสามารถติดตั้งได้ที่นี่: <a href='https://www.curseforge.com/minecraft/mc-ม็อดs/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>หากต้องการใช้ Iris Shaders หรือ Oculus คุณต้องมี SODIUM หรือรุ่นพอร์ตสำหรับตัวโหลดอื่น (Rubidium, Embedium, Bedium)</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ปัญหากับส่วนขยาย KubeJS </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบปัญหากับไดรเวอร์ NVIDIA บน Windows เวอร์ชันก่อน Windows 11" + "</span><br/><br/>"
				+ "เพื่อให้แน่ใจว่า Minecraft (และ JVM ปัจจุบัน) ใช้การ์ดจอ NVIDIA แบบแยก ให้ทำตามขั้นตอนต่อไปนี้:<br/><br/>"
				+ "1. <strong>ระบุไฟล์ Java executable:</strong><br/>"
				+ "   - โปรแกรมนี้กำลังใช้ Java executable ต่อไปนี้: " + obtenerRutaJava() + "<br/>"
				+ "   - หากไม่เห็นเส้นทาง ให้ค้นหา 'java.exe' ในระบบของคุณ<br/><br/>"
				+ "2. <strong>เปิด NVIDIA Control Panel:</strong><br/>"
				+ "   - คลิกขวาที่เดสก์ท็อป แล้วเลือก 'NVIDIA Control Panel'<br/><br/>"
				+ "3. <strong>ตั้งค่า GPU ที่ต้องการ:</strong><br/>" + "   - ไปที่ 'Manage 3D settings'<br/>"
				+ "   - เลือก 'Program Settings'<br/>" + "   - คลิก 'Add' แล้วเลือกไฟล์ Java (เช่น java.exe)<br/>"
				+ "   - ตั้งค่าให้ใช้ 'High-performance NVIDIA processor'<br/><br/>"
				+ "4. <strong>บันทึกการตั้งค่า:</strong><br/>" + "   - กด Apply และปิด NVIDIA Control Panel<br/><br/>"
				+ "5. <strong>รีสตาร์ท Minecraft:</strong><br/>" + "   - เพื่อให้การตั้งค่ามีผล<br/><br/>"
				+ "ใช้ได้กับ Windows Server 2022 หรือ Windows 10 หากติดตั้งไดรเวอร์ล่าสุดแล้ว<br/><br/>"
				+ "หมายเหตุ: หากไม่พบ NVIDIA Control Panel ให้ตรวจสอบว่าไดรเวอร์ติดตั้งถูกต้อง";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบปัญหากับไดรเวอร์ NVIDIA บน Windows 11 / Server 2025 หรือใหม่กว่า" + "</span><br/><br/>"
				+ "เพื่อให้แน่ใจว่า Minecraft (และ JVM ปัจจุบัน) ใช้การ์ดจอ NVIDIA แบบแยก ให้ทำตามขั้นตอนต่อไปนี้:<br/><br/>"
				+ "1. <strong>ระบุไฟล์ Java executable:</strong><br/>"
				+ "   - โปรแกรมนี้กำลังใช้ Java executable ต่อไปนี้: " + obtenerRutaJava() + "<br/>"
				+ "   - หากไม่เห็นเส้นทาง ให้ค้นหา 'java.exe'<br/><br/>" + "2. <strong>เปิดแอป Settings:</strong><br/>"
				+ "   - กด <code>Win + I</code><br/>"
				+ "   - ไปที่ <strong>System > Display > Graphics</strong><br/><br/>"
				+ "3. <strong>ตั้งค่า GPU:</strong><br/>" + "   - เลือก 'Graphics settings'<br/>"
				+ "   - เลือก 'Desktop app' แล้วกด Browse<br/>" + "   - เลือก java.exe<br/>"
				+ "   - ตั้งค่าเป็น 'High performance (NVIDIA)'<br/><br/>" + "4. <strong>บันทึก:</strong><br/>"
				+ "   - Apply แล้วปิด Settings<br/><br/>" + "5. <strong>รีสตาร์ท Minecraft</strong><br/><br/>"
				+ "หมายเหตุ: ตรวจสอบว่าไดรเวอร์ NVIDIA เป็นเวอร์ชันล่าสุด";
	}

	@Override
	public String segundo60Tick() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>เซิร์ฟเวอร์หรือโลกของคุณมี tick เกิน 60 วินาที อาจเกิดจากม็อดหรือฮาร์ดแวร์ไม่เพียงพอ</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>หน่วยความจำ (RAM) ไม่เพียงพอ กรุณาเพิ่มการจัดสรร</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Theseus มีปัญหาเพิ่มเติม รวมถึงลบม็อดไม่สำเร็จ หากต้องใช้ไฟล์ mrpack แนะนำ Prism Launcher, ATLauncher หรือ Hello Minecraft Launcher</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>คุณกำลังใช้ \"Skip Launcher\" (CurseForge) ซึ่งอาจทำให้เกิดปัญหา แนะนำปิดและใช้ Mojang Launcher แทน "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>ดูวิดีโอ</a></b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>คำเตือน: พบคลาสหาย (ไม่ร้ายแรงเสมอไป) สาเหตุอาจมาจาก core mod หรือ dependency หาย คลาสที่พบ:</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ไม่พบผลลัพธ์</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>ตำแหน่งไฟล์ log:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>นี่คือผลการตรวจสอบ โดยทั่วไปปัญหาหลักจะอยู่ในข้อ 1 หรือ 2 ข้ออื่นอาจเป็นผลต่อเนื่องและสามารถละเว้นได้</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ใช้ Java 17 สำหรับ Minecraft 1.17–1.20.4, Java 21 สำหรับเวอร์ชันใหม่ และ Java 8 สำหรับเวอร์ชันเก่า</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22+ ไม่รองรับ Minecraft ต่ำกว่า 1.20.5 ในหลาย loader</b>" + versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java ล้าสมัย</b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ต้องใช้ JPMS Module " + mod_necesitas + " จาก "
				+ submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ไม่สามารถเรียกใช้ " + metodo + " ได้ เนื่องจาก "
				+ objeto + " เป็น null</b>";
	}

	@Override
	public String analisisAvanzado() {
		return "การวิเคราะห์ขั้นสูง";
	}

	@Override
	public String seleccionarCarpeta() {
		return "เลือกโฟลเดอร์";
	}

	@Override
	public String cadenaBusqueda() {
		return "ข้อความค้นหา";
	}

	@Override
	public String usarRegex() {
		return "ใช้ Regex";
	}

	@Override
	public String ignorarMayusculas() {
		return "ไม่สนตัวพิมพ์ใหญ่เล็ก";
	}

	@Override
	public String buscar() {
		return "ค้นหา";
	}

	@Override
	public String busquedaEnProgreso() {
		return "กำลังค้นหา";
	}

	@Override
	public String noSeEncontraronResultados() {
		return "ไม่พบผลลัพธ์";
	}

	@Override
	public String errorBusqueda() {
		return "เกิดข้อผิดพลาดในการค้นหา";
	}

//@Override
//public String noRegistroDeLauncher() {
//	// TODO Auto-generated method stub
//	return new String ("¡No se encontraron บันทึกs del launcher! Esto puede dificultar la investigación.\n"
//			+ "                \n"
//			+ "                Para obtener los บันทึกs correctos:\n"
//			+ "                - MultiMC/PolyMC/PrismLauncher/PollyMC/UltimMC: NOTA: Los บันทึกs detectados automáticamente NO son los correctos.\n"
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
		return "ข้ามและปิด";
	}

	@Override
	public String guardarYCerrar() {
		// TODO Auto-generated method stub
		return "บันทึกและปิด";
	}

	@Override
	public String pegaLosRegistrosAqui() {
		// TODO Auto-generated method stub
		return "วางบันทึกที่นี่";
	}

	@Override
	public String archivo() {
		return "ไฟล์";
	}

	@Override
	public String incluir() {
		return "รวม";
	}

	@Override
	public String abrir() {
		return "เปิด";
	}

	@Override
	public String endpointDeInforme() {
		// TODO Auto-generated method stub
		return "ปลายทางรายงาน";
	}

	@Override
	public String sitoDeLogging() {
		// TODO Auto-generated method stub
		return "เว็บไซต์บันทึก:";
	}

	@Override
	public String apiDeLogging() {
		// TODO Auto-generated method stub
		return "API บันทึก:";
	}

	@Override
	public String anonimizarRegistros() {
		// TODO Auto-generated method stub
		return "ทำให้บันทึกไม่ระบุตัวตน (Beta)";
	}

	@Override
	public String botonDeCompartirInforme() {
		return "แชร์รายงานและบันทึกที่เลือกทั้งหมด";
	}

	@Override
	public String arco() {
		return "หน้าต่างนี้ช่วยให้คุณแชร์บันทึกโดยใช้ API ของ SecureLogger "
				+ "ที่ <a href=\"https://securelogger.net\">securelogger.net</a> เมื่อกดปุ่มแชร์รายงาน "
				+ "รายงานของคุณจะถูกส่งไปยังปลายทางที่เลือก (ค่าเริ่มต้น asbestosstar.egoism.jp) "
				+ "(สามารถเปลี่ยนได้ด้านล่าง) คุณสามารถแชร์บันทึกที่เลือกทั้งหมดพร้อมรายงานได้ "
				+ "หากคุณไม่ต้องการอัปโหลด กรุณาอย่าใช้หน้าต่างนี้ "
				+ "เราไม่ได้ประมวลผลรายงานของคุณในปลายทางอย่างเป็นทางการ "
				+ "(<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_เซิร์ฟเวอร์.rb\">https://asbestosstar.egoism.jp/crash_detector/crash_detector_เซิร์ฟเวอร์.rb</a>) "
				+ "เราจะลบเฉพาะลิงก์ที่ไม่ได้รับอนุญาตเท่านั้น โค้ดอยู่ที่นี่: "
				+ "<a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_เซิร์ฟเวอร์.rb\">ซอร์สโค้ด</a> "
				+ "ใช้เพื่อแสดงข้อมูลเกี่ยวกับข้อผิดพลาดของคุณและลิงก์ไปยังบันทึกเท่านั้น "
				+ "อย่างไรก็ตาม คุณสามารถใช้ปลายทางแบบกำหนดเองซึ่งอาจไม่มีเมธอดเดียวกัน " + "คุณกำลังใช้ไซต์รายงาน "
				+ Config.obtenerInstancia().obtenerSitoDeInformes() + " และไซต์บันทึก "
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + " "
				+ "คุณยังสามารถแชร์บันทึกแต่ละรายการได้โดยไม่ต้องมีรายงาน โดยกดปุ่มแชร์ข้างชื่อบันทึก "
				+ "บันทึกจะถูกส่งไปยังไซต์ที่เลือก "
				+ "CrashDetector มีระบบทำให้บันทึกไม่ระบุตัวตนโดยค่าเริ่มต้น ซึ่งพยายามลบชื่อผู้ใช้ UUID "
				+ "โทเค็นการเข้าถึง รหัสเซสชัน ที่อยู่ IP และข้อมูลอื่น ๆ อย่างไรก็ตามไม่สมบูรณ์ "
				+ "และผู้สร้าง modpack อาจปิดการใช้งานได้ " + "สามารถเปิดหรือปิดได้ด้วยช่องทำเครื่องหมายด้านล่าง "
				+ "คุณเป็นผู้ควบคุมข้อมูลของคุณเอง และเป็นผู้ตัดสินใจว่าจะอัปโหลดไปที่ใด "
				+ "ไซต์บันทึกเป็นของบุคคลที่สาม ซึ่งเจ้าของมักถูกปกปิดเพื่อความเป็นส่วนตัว "
				+ "คุณรับผิดชอบต่อข้อมูลและความเสี่ยงทั้งหมด "
				+ "หน้าต่างแชร์ของ CrashDetector เป็นเพียงอินเทอร์เฟซสำหรับจัดการสิ่งนี้ "
				+ "ควรทราบเกี่ยวกับกฎหมาย RGPD และ ARCO "
				+ "หากคุณอยู่ในยุโรป คุณสามารถใช้ <a href=\"https://securelogger.top\">securelogger.top</a> "
				+ "ซึ่งโฮสต์ในเยอรมนีโดย Hetzner " + "สำหรับข้อมูลทางกฎหมายเพิ่มเติม ดูที่: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>, "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">RGPD</a>, "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">นโยบายคุ้มครองข้อมูลของญี่ปุ่น</a>.";
	}

	@Override
	public String enlaceDelReporte() {
		return "ลิงก์รายงาน:";
	}

	@Override
	public String guardarConfigDeCompartir() {
		return "บันทึกการตั้งค่าการแชร์";
	}

	@Override
	public String registroDemasiadoGrande() {
		return "บันทึกมีขนาดใหญ่เกินไปสำหรับเว็บไซต์นี้ โปรดลองเลือกเว็บไซต์อื่น";
	}

	@Override
	public String errorConPublicarRegistro(String error) {
		return "เกิดข้อผิดพลาดขณะเผยแพร่บันทึก " + error;
	}

	@Override
	public String apiDeRegistroNoExiste() {
		return "ไม่พบ API บันทึก กรุณาเปลี่ยน API ในการตั้งค่า";
	}

	@Override
	public String errorSSL() {
		return "คุณพบข้อผิดพลาด SSL ซึ่งมักเกิดกับ Java เวอร์ชันเก่า "
				+ "รวมถึง Java 8 ใน Launcher Minecraft ค่าเริ่มต้น "
				+ "ปัญหานี้ส่งผลต่อหลายส่วน เช่น ไฟล์ JAR ของ MinecraftForge "
				+ "การแชร์รายงานของ CrashDetector เมื่อใช้ปลายทางเริ่มต้น "
				+ "ม็อดบางตัวที่ต้องใช้อินเทอร์เน็ต และเว็บไซต์บันทึกบางแห่ง " + "หากเกิดปัญหานี้ขณะพยายามแชร์รายงาน "
				+ "ให้แนบภาพหน้าจอและเลือกเว็บไซต์บันทึกที่รองรับ Java 8 เวอร์ชันเก่า";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JavaFML ไม่เข้ากัน: ต้องการเวอร์ชัน "
				+ requerido + " แต่ตรวจพบ " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "คำเตือน! JavaFML ต้องใช้ Minecraft Forge เวอร์ชันเฉพาะ</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ไฟล์ JAR '" + archivoJar
				+ "' ต้องใช้ตัวให้บริการภาษา '" + proveedor + "' เวอร์ชัน '" + requerido
				+ "' หรือใหม่กว่า แต่พบเพียงเวอร์ชัน '" + encontrado + "'</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "คำเตือน! Crash Assistant เป็นตัวตรวจจับมัลแวร์ปลอม ซึ่งตั้งใจบล็อกการเปิดเกม "
				+ "และจำกัดอิสระในการใช้ม็อดของคุณ "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>ดูโค้ด MalwareMod.java</a> "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>ดูโค้ด JarInJarHelper.java</a>. "
				+ "ปัจจุบันมีเพียงม็อดนี้ในรายการ และมุ่งเป้าไปที่เว็บไซต์บันทึกเริ่มต้น "
				+ "ซึ่งผู้ใช้สามารถเปลี่ยนได้ CrashAssistant ไม่ตรวจสอบว่าใช้เว็บไซต์ใด "
				+ "และจะบล็อกเกมเสมอ ไม่ว่าคุณจะตั้งค่าอะไร "
				+ "พวกเขาแนะนำให้คุณค้นคว้าเอง — ควรตรวจสอบโค้ดของ CrashDetector และ Crash Assistant ด้วยตัวเอง "
				+ "อย่าเชื่อเพียงเพราะแหล่งอ้างอิง</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ม็อด '" + idMod
				+ "' ล้มเหลว เนื่องจากไม่พบคลาสที่จำเป็น: '" + claseNoEncontrada
				+ "' โปรดตรวจสอบว่าติดตั้ง dependency ครบถ้วน</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Watermedia ได้บล็อกการเล่นร่วมกับ TLauncher</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "คุณกำลังใช้ Optifine เวอร์ชันที่ไม่ตรงกับ Minecraft กรุณาใช้เวอร์ชันที่ตรงกัน</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ไม่สามารถโหลดบริการ ModLauncher: </b>"
				+ servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "เกิดข้อผิดพลาดในการวิเคราะห์ไฟล์ JSON '"
				+ recurso + "' จากไฟล์ JAR '" + archivoJar + "' มีปัญหากับบันทึก</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "ข้อผิดพลาด: ม็อด '" + modId + "' ต้องใช้ '"
				+ dependencia + "' เวอร์ชัน '" + requerido + "' หรือใหม่กว่า แต่พบ '" + actual + "'</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "การ์ดจอของคุณไม่รองรับ OpenGL เวอร์ชันที่ต้องการ กรุณาอัปเดตไดรเวอร์หรือเปลี่ยน GPU</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "เพิ่มหน่วยความจำที่จัดสรรให้เกม หรือลดการใช้ม็อด/ปลั๊กอิน</b>";
	}

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "ตรวจพบ JVM แบบ 32 บิต ไม่สามารถใช้ RAM เกิน 4GB ได้ " + "โปรดติดตั้ง JVM แบบ 64 บิต</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ข้อผิดพลาดร้ายแรงของหน่วยความจำ PermGen "
				+ "เพิ่มพื้นที่หน่วยความจำหรือ ลดการโหลดคลาส</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ปัญหาความเข้ากันได้ระหว่าง Java 8 และม็อดเวอร์ชันใหม่</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+ ไม่รองรับ ใช้ Java 8 สำหรับ Forge รุ่นเก่า</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ต้องใช้ Java 8 (เวอร์ชัน 52.0) โปรดตั้งค่าให้ถูกต้อง</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ข้อผิดพลาดร้ายแรงด้านความเข้ากันได้: บล็อกไม่รองรับในเวอร์ชันนี้ "
				+ "ตรวจสอบเวอร์ชันของม็อดและเซิร์ฟเวอร์</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ข้อผิดพลาดการตั้งค่าจอภาพ: ไม่สามารถตั้งค่าโหมดแสดงผลได้ " + "โปรดตรวจสอบ:</b>"
				+ "<br>- การตั้งค่าหลายจอภาพ" + "<br>- ไดรเวอร์การ์ดจอเป็นเวอร์ชันล่าสุด"
				+ "<br>- ความละเอียดที่ระบบรองรับ";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ข้อผิดพลาดในการตั้งค่า Java: ตัวเลือก Garbage Collector ขัดแย้งกัน "
				+ "กรุณาตรวจสอบว่าไม่ได้ใช้หลาย GC พร้อมกันในพารามิเตอร์ JVM</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ข้อผิดพลาดร้ายแรงในการตั้งค่า NightConfig/Forge: ไฟล์การตั้งค่าเสียหายหรือไม่สมบูรณ์ "
				+ "อาจเกิดจากไฟล์ config ว่าง (0 ไบต์) ในโฟลเดอร์ 'config' "
				+ "โดยทั่วไป Night Config Fixes สามารถแก้ไขได้ "
				+ "แต่หากใช้เวอร์ชันที่ไม่รองรับ คุณต้องลบไฟล์ config ด้วยตนเอง "
				+ "ปัญหานี้พบบ่อยใน Forge รุ่นเก่าและม็อด Fabric บางตัว " + "ดูข้อมูลเพิ่มเติมได้ที่ "
				+ "<a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a></b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบปัญหา Intel HD Graphics แนวทางแก้ไข:</b>"
				+ "<br>1. อัปเดตไดรเวอร์จาก <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a>"
				+ "<br>2. ใน Minecraft เปิด VBOs และ VSync" + "<br>3. จัดสรร RAM 1–2GB"
				+ "<br>4. ปิด antivirus/firewall ชั่วคราวระหว่างอัปเดต";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "คำเตือน: พบคลาสหาย";
	}

	public String nombre_de_bloque_teselado() {
		return "ข้อผิดพลาดการเรนเดอร์บล็อก";
	}

	public String nombre_de_contenido_de_stacktrace() {
		return "การวิเคราะห์ Stack Trace";
	}

	public String nombre_de_cursed_consola() {
		return "คอนโซล CurseForge ไม่สมบูรณ์";
	}

	public String nombre_de_early_window() {
		return "ข้อผิดพลาดหน้าต่างเริ่มต้น (FMLEarlyWindow)";
	}

	public String nombre_de_drivers() {
		return "ปัญหาไดรเวอร์กราฟิก";
	}

	public String nombre_de_error_de_config_mcforge() {
		return "การตั้งค่า MCForge เสียหาย";
	}

	public String nombre_de_error_de_monitor_lwjgl() {
		return "ข้อผิดพลาดโหมดแสดงผล (LWJGL)";
	}

	public String nombre_de_fabricmc_runtime_error_provided_by() {
		return "ข้อผิดพลาดการเริ่มต้น FabricMC";
	}

	public String nombre_de_falta_module_jmps() {
		return "ขาดโมดูล JPMS";
	}

	public String nombre_de_faltar_de_clases() {
		return "คลาสสำคัญหาย";
	}

	public String nombre_de_faltas_dependencias_de_modlauncher() {
		return "ขาด dependency ของ ModLauncher";
	}

	public String nombre_de_java_versiones() {
		return "เวอร์ชัน Java ไม่เข้ากัน";
	}

	public String nombre_de_faltar_de_kubejs_resourcepack() {
		return "ข้อผิดพลาด resource KubeJS";
	}

	public String nombre_de_lenguaje_proveedor_check() {
		return "ตัวให้บริการภาษาไม่เข้ากัน";
	}

	public String nombre_de_faltar_de_liyhostictchctov() {
		return "Litchhost";
	}

	public String nombre_de_malware_falso_crash_assistant() {
		return "ตรวจพบมัลแวร์ปลอม";
	}

	public String nombre_de_mcforge_mod_sespechoso() {
		return "ตรวจพบม็อดต้องสงสัย";
	}

	public String nombre_de_mods_duplicados_modlauncher() {
		return "ม็อดซ้ำใน ModLauncher";
	}

	public String nombre_de_modules_duplicados_jmps() {
		return "โมดูล JPMS ซ้ำกัน";
	}

	public String nombre_de_necesitas_sodium() {
		return "ต้องใช้ Sodium สำหรับ Iris";
	}

	public String nombre_de_no_puede_analizar_json_de_registro() {
		return "ไม่สามารถวิเคราะห์ JSON ของบันทึกได้";
	}

	public String nombre_de_no_tiene_memoria() {
		return "หน่วยความจำไม่เพียงพอ";
	}

	public String nombre_de_null_pointer() {
		return "ข้อผิดพลาด Null Pointer";
	}

	public String nombre_de_opciones_java_gc_invalidas() {
		return "ตัวเลือก GC ของ Java ไม่ถูกต้อง";
	}

	public String nombre_de_optifine_obsoleta() {
		return "OptiFine ล้าสมัยหรือไม่รองรับ";
	}

	public String nombre_de_60_segundo_trick() {
		return "Tick เซิร์ฟเวอร์ผิดปกติ (60 วินาที)";
	}

	public String nombre_de_servicio_de_modlauncher_no_funciona() {
		return "บริการ ModLauncher ล้มเหลว";
	}

	public String nombre_de_spongemixin_configs_problematicos() {
		return "การตั้งค่า SpongeMixin มีปัญหา";
	}

	public String nombre_de_theseus() {
		return "Theseus ไม่รองรับ";
	}

	public String nombre_de_watermedia_tl() {
		return "TLauncher ไม่รองรับโดย WATERMeDIA";
	}

	@Override
	public String auditorias_transformer() {
		return "การตรวจสอบ Transformer";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ผลลัพธ์การตรวจสอบ Transformer ใน Launcher ปกติ "
				+ "โดยทั่วไปมีความแม่นยำน้อยกว่า StackTrace Analyzer</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "ค้นหาม็อดที่สร้างด้วย MCreator " + "ม็อดส่วนใหญ่ใช้งานได้ดี แต่บางตัวอาจมีปัญหา "
				+ "เครื่องมือนี้ช่วยระบุ";
	}

	@Override
	public String escanear() {
		return "สแกน";
	}

	@Override
	public String cargando() {
		return "กำลังโหลด";
	}

	@Override
	public String inicioApp() {
		return "เริ่มแอป/เกม";
	}

	@Override
	public String ajustesCrashDetector() {
		return "การตั้งค่า CrashDetector";
	}

	@Override
	public String confidencialidad() {
		return "ความเป็นส่วนตัว";
	}

	@Override
	public String tooltip() {
		return "คำอธิบายเครื่องมือ";
	}

	@Override
	public String config() {
		return "การตั้งค่า";
	}

	@Override
	public String abrirCarpeta() {
		return "เปิดโฟลเดอร์";
	}

	@Override
	public String actualizar() {
		// TODO Auto-generated method stub
		return "อัปเดต";
	}

	@Override
	public String anadirRegistro() {
		// TODO Auto-generated method stub
		return "เพิ่มบันทึก";
	}

	@Override
	public String usarIdiomaDelSistema() {
		// TODO Auto-generated method stub
		return "ใช้ภาษาของระบบ";
	}

	@Override
	public String volver() {
		// TODO Auto-generated method stub
		return "ย้อนกลับ";
	}

	@Override
	public String colorFondo() {
		return "สีพื้นหลัง (#RRGGBB):";
	}

	@Override
	public String colorTexto() {
		return "สีข้อความ (#RRGGBB):";
	}

	@Override
	public String colorBoton() {
		return "สีปุ่ม (#RRGGBB):";
	}

	@Override
	public String colorCajaTexto() {
		return "สีกล่องข้อความ (#RRGGBB):";
	}

	@Override
	public String colorEnlace() {
		return "สีลิงก์ (#RRGGBB):";
	}

	@Override
	public String colorTitulosConsolas() {
		return "สีหัวข้อคอนโซล (#RRGGBB):";
	}

	@Override
	public String colorError() {
		return "สีข้อผิดพลาด (#RRGGBB):";
	}

	@Override
	public String colorAdvertencia() {
		return "สีคำเตือน (#RRGGBB):";
	}

	@Override
	public String colorInfo() {
		return "สีข้อมูล (#RRGGBB):";
	}

	@Override
	public String colorTitulo() {
		return "สีหัวเรื่อง (#RRGGBB):";
	}

	@Override
	public String colorEnlaceTexto() {
		return "สีข้อความลิงก์ (#RRGGBB):";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "เปิด CrashDetector เฉพาะเมื่อเกิดข้อขัดข้อง";
	}

	@Override
	public String activar_parche() {
		return "เปิดใช้งานแพตช์";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "ไม่มีวิธีแก้ไขที่พร้อมใช้งาน";
	}

	@Override
	public String error() {
		return "ข้อผิดพลาด";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "เกิดข้อผิดพลาดขณะลบ Jar";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "ลบ Jar สำเร็จ";
	}

	@Override
	public String exito() {
		return "สำเร็จ";
	}

	@Override
	public String eliminar() {
		return "ลบ";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "เกิดข้อผิดพลาดขณะลบ paquete";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "ลบแพ็กเกจสำเร็จ";
	}

	@Override
	public String eliminar_paquete() {
		return "ลบแพ็กเกจ";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "ไม่พบม็อดที่มีแพ็กเกจนี้";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "ไม่สามารถลบแพ็กเกจได้";
	}

	@Override
	public String eliminar_jar() {
		return "ลบไฟล์ JAR";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "ไม่พบม็อดซ้ำ";
	}

	@Override
	public String archivo_no_encontrado() {
		return "ไม่พบไฟล์";
	}

	@Override
	public String directorio_eliminado() {
		return "ลบไดเรกทอรีแล้ว";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "เกิดข้อผิดพลาดขณะลบ JAR ที่ซ้อนอยู่";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "ไม่พบไฟล์ภายใน";
	}

	@Override
	public String archivo_eliminado() {
		return "ลบไฟล์แล้ว";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "เกิดข้อผิดพลาดขณะลบไฟล์";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "ไฟล์ภายนอกไม่ถูกต้อง";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "ลบองค์ประกอบของม็อดแล้ว";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "เกิดข้อผิดพลาดขณะแทนที่ JAR ภายนอก";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "เกิดข้อผิดพลาดขณะลบองค์ประกอบของม็อด";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "เกิดข้อผิดพลาดขณะลบไดเรกทอรี";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "รูปแบบของ JAR ที่ซ้อนอยู่ไม่ถูกต้อง";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "ลบ JAR ที่ซ้อนอยู่แล้ว";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "เกิดข้อผิดพลาดขณะล้างไฟล์ชั่วคราว";
	}

	@Override
	public String mensaje_de_trace_fatal_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ข้อความ Trace ร้ายแรงล่าสุด (ยังไม่ได้แปล):</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ข้อความ Trace ล่าสุด (ยังไม่ได้แปล):</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "คุณสามารถค้นหาในฐานข้อมูล WaifuNeoForge เพื่อหาม็อดที่ต้องการ "
				+ "เลือกเวอร์ชันเกม ตัวโหลดม็อด และคลาสที่ใกล้เคียงที่สุด " + "สามารถค้นหาได้หนึ่งครั้งต่อนาที";
	}

	@Override
	public String solucionFaltasClases() {
		return "คุณสามารถค้นหาในฐานข้อมูล WaifuNeoForge เพื่อหาม็อดที่ต้องการ "
				+ "เลือกเวอร์ชันเกม ตัวโหลดม็อด และคลาสที่ใกล้เคียงที่สุด " + "สามารถค้นหาได้หนึ่งครั้งต่อนาที";
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "Launcher ทั้งสองมี Java ที่ถูกต้องบางเวอร์ชัน "
				+ "คุณสามารถติดตั้ง Java เวอร์ชันที่เหมาะสมผ่านตัวจัดการแพ็กเกจของระบบ " + "หรือใช้ปุ่มที่มีให้";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ไม่พบอนิเมชันของม็อด</b>";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException (ไม่พบองค์ประกอบ) - ขาดอนิเมชัน";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "ไม่พบม็อดให้ลบ";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "แทนที่ตัวเลือก GC ที่ขัดแย้งกันด้วย -XX:+UseG1GC";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "เพิ่มขนาดหน่วยความจำ heap โดยใช้ตัวเลือก -Xmx";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "เพิ่มขนาดหน่วยความจำ PermGen โดยใช้ตัวเลือก -XX:MaxPermSize";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "ใช้ JVM แบบ 64 บิต เพื่อเพิ่มหน่วยความจำที่ใช้งานได้";
	}

	@Override
	public String optimizarCodigo() {
		return "ปรับปรุงโค้ดเพื่อลดการใช้หน่วยความจำและเพิ่มประสิทธิภาพ";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "ใช้ตัวจัดการ Garbage Collector ที่มีประสิทธิภาพเพื่อลดเวลาหยุดของแอปพลิเคชัน";
	}

	@Override
	public String modulos() {
		return "โมดูล";
	}

	@Override
	public String paquete() {
		return "แพ็กเกจ";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "พบ ID ที่หายไป สาเหตุทั่วไปคือม็อดหรือข้อมูลไอเทมหาย "
				+ "โฟลเดอร์ข้อมูลที่พบบ่อย ได้แก่ datafiedcontents/ และ kubejs/ หรือโฟลเดอร์ม็อดอื่น ๆ";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "บันทึกที่แมปไม่ถูกต้อง";
	}

	/**
	 * Devuelve el mensaje de error para el cierre causado por AuthMe.
	 */
	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ปลั๊กอิน 'AuthMe' โหลดไม่สำเร็จ และทำให้เซิร์ฟเวอร์ปิดตัวลง</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	public String nombreProblemaCierreAuthMe() {
		return "ปัญหาการปิดเซิร์ฟเวอร์จาก AuthMe";
	}

	/**
	 * Devuelve la solución de cierre del servidor por AuthMe.
	 */
	public String solucionCierreAuthMe() {
		return "ค่า 'stopServer' ถูกตั้งเป็น 'true'";
	}

	/**
	 * Devuelve la solución de configuración del plugin AuthMe.
	 */
	public String solucionConfigurarPluginAuthMe() {
		return "ตั้งค่าปลั๊กอิน AuthMe (plugins/AuthMe/config.yml)";
	}

	/**
	 * Devuelve la solución de instalar otra versión del plugin AuthMe.
	 */
	public String solucionInstalarVersionDiferenteAuthMe() {
		return "ติดตั้งปลั๊กอิน 'AuthMe' เวอร์ชันอื่น";
	}

	/**
	 * Devuelve la solución de eliminar el plugin AuthMe.
	 */
	public String solucionEliminarPluginAuthMe() {
		return "ลบปลั๊กอิน 'AuthMe'";
	}

	/**
	 * Devuelve el mensaje de error para mundos corruptos por Multiverse.
	 */
	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>โลก '" + nombreMundo
				+ "' ไม่สามารถโหลดได้ เนื่องจากมีข้อผิดพลาดและอาจเสียหาย</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaCargaMultiverso() {
		return "ปัญหาการโหลด Multiverse";
	}

	/**
	 * Devuelve la solución de reparar el mundo.
	 */
	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "ซ่อมแซมโลก '" + nombreMundo + "' เช่น ใช้ Minecraft Region Fixer หรือ MCEdit";
	}

	/**
	 * Devuelve la solución de eliminar la carpeta del mundo.
	 */
	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "ลบโฟลเดอร์ของโลก '" + nombreMundo + "'";
	}

	/**
	 * Devuelve el mensaje de error para configuración inválida de PermissionsEx.
	 */
	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>การตั้งค่าของปลั๊กอิน 'PermissionsEx' ไม่ถูกต้อง</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "ปัญหาการตั้งค่า PermissionsEx";
	}

	/**
	 * Devuelve la solución de configurar PermissionsEx.
	 */
	@Override
	public String solucionConfigurarPermissionsEx() {
		return "ตั้งค่าปลั๊กอิน PermissionsEx (plugins/PermissionsEx/permissions.yml)";
	}

	/**
	 * Devuelve la solución de eliminar el plugin PermissionsEx.
	 */
	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "ลบปลั๊กอิน 'PermissionsEx'";
	}

	/**
	 * Devuelve el mensaje de error para plugins con nombre ambiguo.
	 */
	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>พบไฟล์ปลั๊กอินหลายไฟล์สำหรับชื่อ '" + nombrePlugin
				+ "': '" + primerPath + "' และ '" + segundoPath + "'</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "ปัญหาชื่อปลั๊กอินซ้ำ";
	}

	/**
	 * Devuelve la solución de eliminar un plugin específico.
	 */
	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "ลบปลั๊กอิน '" + nombrePlugin + "'";
	}

	/**
	 * Devuelve el mensaje de error para excepciones al cargar chunks.
	 */
	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>เกิดข้อยกเว้นขณะโหลดโลก หากรองรับบนแพลตฟอร์มของคุณ " + "FeatureRecycler อาจช่วยแก้ปัญหาได้ "
				+ "https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaCargaChunk() {
		return "ข้อยกเว้นขณะโหลด chunk";
	}

	/**
	 * Devuelve la solución de eliminar el chunk problemático.
	 */
	@Override
	public String solucionEliminarChunk() {
		return "ลบ chunk ที่มีปัญหาในโลก เช่น ใช้ MCEdit หรือ ลบไฟล์ region";
	}

	/**
	 * Devuelve el mensaje de error para excepciones al ejecutar comandos de
	 * plugins.
	 */
	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ปลั๊กอิน '" + nombrePlugin
				+ "' ไม่สามารถรันคำสั่ง '/" + comando + "' ได้</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "ข้อยกเว้นขณะรันคำสั่งปลั๊กอิน";
	}

	/**
	 * Devuelve la solución de instalar otra versión del plugin.
	 */
	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "ติดตั้งปลั๊กอิน '" + nombrePlugin + "' เวอร์ชันอื่น";
	}

	/**
	 * Devuelve el mensaje de error para dependencias individuales.
	 */
	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ปลั๊กอิน '" + nombrePlugin
				+ "' ต้องการ dependency '" + dependencia + "'</b> ";
	}

	/**
	 * Devuelve el mensaje de error para múltiples dependencias.
	 */
	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>ปลั๊กอิน '" + nombrePlugin
				+ "' ขาดปลั๊กอินที่จำเป็นต่อไปนี้ " + deps.toString() + "</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "ปลั๊กอินขาด dependency";
	}

	/**
	 * Devuelve la solución de instalar un plugin específico.
	 */
	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "ติดตั้งปลั๊กอิน '" + nombrePlugin + "'";
	}

	/**
	 * Devuelve el mensaje de error para versión de API incompatible.
	 */
	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ปลั๊กอิน '" + nombrePlugin
				+ "' ต้องใช้ API เวอร์ชัน '" + versionAPI + "' ซึ่งไม่รองรับกับเซิร์ฟเวอร์ปัจจุบัน</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "เวอร์ชัน API ไม่เข้ากัน";
	}

	/**
	 * Devuelve la solución de instalar una versión específica del servidor.
	 */
	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "ติดตั้งซอฟต์แวร์เซิร์ฟเวอร์เวอร์ชัน '" + version + "'";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>โลก '" + nombreMundo
				+ "' เป็นโลกซ้ำ และไม่สามารถโหลดได้</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaMundoDuplicado() {
		return "โลกซ้ำ";
	}

	/**
	 * Devuelve la solución de eliminar el archivo 'uid.dat' del mundo.
	 */
	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "ลบไฟล์ 'uid.dat' ของโลก '" + nombreMundo + "'";
	}

	/**
	 * Devuelve la solución de eliminar la carpeta completa del mundo.
	 */
	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "ลบโฟลเดอร์ของโลก '" + nombreMundo + "'";
	}

	/**
	 * Devuelve el mensaje de error para entidades problemáticas.
	 */
	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		String color = config.obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>เอนทิตี '" + nombre + "' ประเภท '" + tipo
				+ "' ที่ตำแหน่ง </span>" + coords + "<span style='color:#" + color
				+ "'> กำลังทำให้เกิดข้อผิดพลาด ticking</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'>วิธีแก้ไข:<br>"
				+ "1. MCForge: ไปที่ '[world]/serverconfig/forge-server.toml'<br>"
				+ "2. NeoForge: ไปที่ 'config/neoforge-server.toml'<br>"
				+ "3. ตั้งค่า removeErroringBlockEntities และ removeErroringEntities เป็น true<br><br>"
				+ "ตัวเลือกอื่น:<br>" + "- ใช้ MCEdit เพื่อลบเอนทิตี<br>" + "- ใช้ Neruina mod เพื่อลดการ crash</span>";

		return mensajeBase + instrucciones;
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "เอนทิตีมีปัญหา";
	}

	/**
	 * Devuelve la solución de eliminar la entidad de bloque.
	 */
	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "ลบเอนทิตี '" + nombre + "' ที่ตำแหน่ง " + coords;
	}

	/**
	 * Devuelve el mensaje de error para mods duplicados en Fabric.
	 */
	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ม็อด '" + nombreMod
				+ "' มีหลายเวอร์ชันติดตั้งอยู่</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "ม็อดซ้ำใน Fabric";
	}

	/**
	 * Devuelve la solución de eliminar el archivo duplicado.
	 */
	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "ลบไฟล์ม็อดที่ซ้ำ: " + rutaMod;
	}

	/**
	 * Devuelve el mensaje de error para mods incompatibles.
	 */
	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ม็อด '" + primerMod + "' และ '" + segundoMod
				+ "' ไม่เข้ากัน</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "ม็อดไม่เข้ากันใน Fabric";
	}

	/**
	 * Devuelve la solución de eliminar el primer mod incompatible.
	 */
	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "ลบม็อด '" + nombreMod + "'";
	}

	/**
	 * Devuelve el mensaje de error para mods con problemas fatales.
	 */
	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ม็อด '" + nombreMod
				+ "' มีข้อผิดพลาดร้ายแรงและไม่สามารถทำงานได้</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaModFatal() {
		return "ม็อดมีข้อผิดพลาดร้ายแรง";
	}

	/**
	 * Devuelve el mensaje de error para dependencias faltantes en mods (plural).
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
		return "<b style='color:#" + config.obtenerColorError() + "'>ม็อดต่อไปนี้จำเป็นต้องใช้แต่ยังไม่ได้ติดตั้ง: "
				+ deps.toString() + ".</b>";
	}

	/**
	 * Devuelve el mensaje de error para dependencias faltantes en mods (singular).
	 */
	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>ม็อด '" + nombreMod + "' ต้องการม็อด '"
					+ dependencia + "'</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>ม็อด '" + nombreMod + "' ต้องการม็อด '"
					+ dependencia + "' เวอร์ชัน " + version + "</b>";
		}
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaDependenciaMod() {
		return "ม็อดขาด dependency";
	}

	/**
	 * Devuelve la solución de instalar un mod específico.
	 */
	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "ติดตั้งม็อด '" + nombreMod + "'";
	}

	/**
	 * Devuelve la solución de instalar un mod con versión específica.
	 */
	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "ติดตั้งม็อด '" + nombreMod + "' เวอร์ชัน " + version;
	}

	/**
	 * Devuelve el mensaje de error para plugins que no soportan ticking regional
	 * (singular).
	 */
	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ปลั๊กอิน '" + nombrePlugin
				+ "' ไม่รองรับ regional ticking ของ Folia</b> ";
	}

	/**
	 * Devuelve el mensaje de error para múltiples plugins que no soportan ticking
	 * regional (plural).
	 */
	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ปลั๊กอินต่อไปนี้ไม่รองรับ regional ticking ของ Folia: ");

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
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaPluginTickingRegional() {
		return "ปลั๊กอินไม่รองรับ Regional Ticking";
	}

	/**
	 * Devuelve la solución de instalar un software sin ticking regional.
	 */
	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "ติดตั้งซอฟต์แวร์ที่ไม่ใช้ regional ticking เช่น " + software;
	}

	/**
	 * Devuelve el mensaje de error para un mod faltante en datapack (singular).
	 */
	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ม็อด '" + nombreMod + "' หายไปใน datapack</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods faltantes en datapack
	 * (plural).
	 */
	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ม็อดต่อไปนี้หายไปใน datapack: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" และ ");
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
	public String nombreProblemaModFaltanteEnDatapack() {
		return "ม็อดหายไปใน datapack";
	}

	/**
	 * Devuelve el mensaje de error para un mod que generó una excepción (singular).
	 */
	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ม็อด '" + nombreMod + "' เกิดข้อผิดพลาด</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods que generaron excepciones
	 * (plural).
	 */
	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ม็อดต่อไปนี้เกิดข้อผิดพลาด: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" และ ");
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
	public String nombreProblemaModExcepcion() {
		return "ม็อดเกิดข้อผิดพลาด";
	}

	/**
	 * Devuelve la solución de instalar una versión diferente del mod.
	 */
	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "ติดตั้งม็อด '" + nombreMod + "' เวอร์ชันอื่น";
	}

	/**
	 * Devuelve el mensaje de error para un mod incompatible con la versión de
	 * Minecraft (singular).
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ม็อด '" + nombreMod
				+ "' ไม่รองรับ Minecraft เวอร์ชัน " + versionMC + "</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods incompatibles con Minecraft
	 * (plural).
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ม็อดต่อไปนี้ไม่รองรับเวอร์ชันของ Minecraft: ");

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
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaModIncompatibleConMinecraft() {
		return "ม็อดไม่รองรับ Minecraft";
	}

	/**
	 * Devuelve la solución de instalar una versión diferente de Forge.
	 */
	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "ติดตั้ง Forge เวอร์ชันที่รองรับ Minecraft " + versionMC;
	}

	/**
	 * Devuelve el mensaje de error para un mod faltante (singular).
	 */
	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ม็อด '" + nombreMod
				+ "' หายไป และจำเป็นสำหรับการโหลดโลกหรือปลั๊กอิน</b>";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "ม็อดขาดหาย";
	}

	/**
	 * Devuelve el mensaje de error para un mod faltante en el mundo (singular).
	 */
	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>โลกถูกบันทึกโดยใช้ม็อด '" + nombreMod
				+ "' ซึ่งตอนนี้ไม่พบ</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods faltantes en el mundo
	 * (plural).
	 */
	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("โลกถูกบันทึกโดยใช้ม็อดต่อไปนี้ซึ่งตอนนี้ไม่พบ: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" และ ");
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
		return "ม็อดหายไปในโลก";
	}

	/**
	 * Devuelve el mensaje de error para discrepancia de versión de mod en un mundo
	 * (singular).
	 */
	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>โลกถูกบันทึกด้วยม็อด '" + nombreMod + "' เวอร์ชัน "
				+ versionEsperada + " แต่ปัจจุบันเป็นเวอร์ชัน " + versionActual + "</b>";
	}

	/**
	 * Devuelve el mensaje de error para discrepancias de versión de mods en
	 * múltiples mods (plural).
	 */
	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ม็อดต่อไปนี้มีเวอร์ชันไม่ตรงกับโลกที่บันทึกไว้: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" และ ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (บันทึก: ").append(versionesEsperadas.get(i)).append(", ปัจจุบัน: ")
					.append(versionesActuales.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaVersionModMundo() {
		return "เวอร์ชันม็อดไม่ตรงกับโลก";
	}

	/**
	 * Devuelve el mensaje de error para intentar cargar un mundo desde una versión
	 * más reciente.
	 */
	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>คุณพยายามโหลดโลกที่สร้างจาก Minecraft เวอร์ชันใหม่กว่า</b>";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaVersionDowngrade() {
		return "พยายามโหลดโลกจากเวอร์ชันที่ต่ำกว่า";
	}

	/**
	 * Devuelve la solución de instalar una versión más nueva de Minecraft.
	 */
	@Override
	public String solucionVersionDowngrade() {
		return "ติดตั้ง Minecraft เวอร์ชันที่ใหม่กว่า";
	}

	/**
	 * Devuelve la solución de generar un nuevo mundo.
	 */
	@Override
	public String solucionGenerarNuevoMundo() {
		return "สร้างโลกใหม่";
	}

	/**
	 * Devuelve el mensaje de error para un plugin con dependencia faltante
	 * (singular).
	 */
	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ปลั๊กอิน '" + nombrePlugin + "' ต้องการปลั๊กอิน '"
				+ dependencia + "'</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples plugins con dependencias
	 * faltantes (plural).
	 */
	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ปลั๊กอินต่อไปนี้ต้องการ dependency ที่ยังไม่ได้ติดตั้ง: ");

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
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaDependenciaPluginFaltante() {
		return "ปลั๊กอินขาด dependency";
	}

	/**
	 * Devuelve el mensaje de error para un plugin incompatible (singular).
	 */
	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ปลั๊กอิน '" + nombrePlugin
				+ "' ไม่รองรับเวอร์ชันปัจจุบันของเซิร์ฟเวอร์</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples plugins incompatibles (plural).
	 */
	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ปลั๊กอินต่อไปนี้ไม่รองรับเวอร์ชันปัจจุบันของเซิร์ฟเวอร์: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" และ ");
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
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaPluginIncompatible() {
		return "ปลั๊กอินไม่รองรับ PocketMine-MP";
	}

	/**
	 * Devuelve el mensaje de error para un plugin con error de ejecución
	 * (singular).
	 */
	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ปลั๊กอิน '" + nombrePlugin
				+ "' เกิดข้อผิดพลาดระหว่างการทำงาน</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples plugins con errores de ejecución
	 * (plural).
	 */
	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ปลั๊กอินต่อไปนี้เกิดข้อผิดพลาดระหว่างการทำงาน: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" และ ");
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
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaPluginEjecucion() {
		return "ปลั๊กอินเกิดข้อผิดพลาดระหว่างการทำงาน";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "LegacyRandomSource แบบหลายเธรด";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>พบปัญหาการเข้าถึงคลาส LegacyRandomSource จากหลายเธรด "
				+ "คุณสามารถตรวจสอบเพิ่มเติมได้โดยใช้ม็อด Unsafe World Random Access Detector หรือ C2ME</b> ";
	}

	@Override
	public String desdeUltimoExito() {
		return "ตั้งแต่ครั้งล่าสุดที่สำเร็จ";
	}

	@Override
	public String noHayCambios() {
		return "ไม่มีการเปลี่ยนแปลง";
	}

	@Override
	public String desdeUltimoIntento() {
		return "ตั้งแต่ความพยายามล่าสุด";
	}

	@Override
	public String fallo() {
		return "ล้มเหลว";
	}

	@Override
	public String diferentesDeLasMods() {
		return "แตกต่างจากม็อด";
	}

	@Override
	public String historialDeMods() {
		return "ประวัติม็อด";
	}

	@Override
	public String archivo0() {
		return "ไฟล์ 0";
	}

	@Override
	public String archivo1() {
		return "ไฟล์ 1";
	}

	@Override
	public String comparar() {
		return "เปรียบเทียบ";
	}

	@Override
	public String seleccionarDosArchivos() {
		return "เลือกไฟล์สองไฟล์";
	}

	@Override
	public String archivoExito() {
		return "ไฟล์สำเร็จ";
	}

	@Override
	public String archivoFalla() {
		return "ไฟล์ล้มเหลว";
	}

	@Override
	public String errorComparandoArchivos() {
		return "เกิดข้อผิดพลาดในการเปรียบเทียบไฟล์";
	}

	@Override
	public String comparando() {
		return "กำลังเปรียบเทียบ";
	}

	@Override
	public String con() {
		return "กับ";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
				+ "<p><b>แผงเปรียบเทียบประวัติม็อด</b></p>"
				+ "<p>แผงนี้ช่วยให้คุณสามารถเปรียบเทียบรายการม็อดจากการทำงานคนละช่วงเวลา "
				+ "เลือกไฟล์จากฝั่งซ้ายและขวาเพื่อดูความแตกต่างระหว่างสองเวอร์ชัน</p>"

				+ "<h3>วิธีใช้งาน:</h3><ol>" + "<li><b>เลือกไฟล์:</b> คลิกปุ่มตัวเลือกข้างชื่อไฟล์ "
				+ "ไฟล์ที่ลงท้ายด้วย <span style='color: #4CAF50; font-weight: bold;'>.exito</span> คือการทำงานสำเร็จ "
				+ "ส่วน <span style='color: #F44336; font-weight: bold;'>.falla</span> คือการล้มเหลว</li>"

				+ "<li><b>เปรียบเทียบ:</b> กดปุ่ม 'Compare' เพื่อให้ระบบวิเคราะห์ความแตกต่าง "
				+ "และแสดงม็อดที่เพิ่ม (+) หรือถูกลบ (-)</li>"

				+ "<li><b>ผลลัพธ์:</b> แสดงด้วยสี:<ul>" + "<li><span style='color: green;'>+ ม็อดที่เพิ่ม</span></li>"
				+ "<li><span style='color: red;'>- ม็อดที่ลบ</span></li>" + "</ul></li></ol>"

				+ "<h3>คุณสมบัติ:</h3><ul>" + "<li>เลือกไฟล์ได้อิสระ</li>" + "<li>แสดงความแตกต่างสองทาง</li>"
				+ "<li>รองรับรายการม็อดจำนวนมาก</li>" + "<li>มีภาพประกอบเพื่อช่วยทำความเข้าใจ</li>" + "</ul>"

				+ "<p>พัฒนาด้วยความใส่ใจเพื่อช่วยติดตามการเปลี่ยนแปลงของม็อด</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "อาจมีปัญหาเกี่ยวกับ IPv6 วิธีแก้ไขมี 2 วิธี: "
				+ "1) เพิ่ม argument JVM <code>-Djava.net.preferIPv4Stack=true</code> "
				+ "หรือ 2) ใช้ปุ่ม 'QuickFix' เพื่อแก้ไขอัตโนมัติ" + "</b>";
	}

	@Override
	public String parcheIPv4() {
		return "แพตช์ IPv4/IPv6";
	}

	@Override
	public String carpetaHMCL() {
		return "โฟลเดอร์ HMCL (เฉพาะ HelloMinecraftLauncher)";
	}

	@Override
	public String descripcionCurseforge() {
		return "หมายเหตุ: จะไม่มีการสร้างบันทึก หากเปิดใช้งานตัวเลือก \"ข้าม Launcher\"";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC และอื่น ๆ: คลิกขวาที่ instance แล้วเลือก \"แก้ไข\" "
				+ "จากนั้นดูส่วน \"บันทึก Minecraft\" ซึ่งมีข้อมูลสำคัญสำหรับการวิเคราะห์ข้อผิดพลาด";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL: เลือกโฟลเดอร์ที่ติดตั้ง HMCL และเข้าไปที่ \".hmcl\" "
				+ "ซึ่งมีบันทึกสำคัญสำหรับวิเคราะห์ข้อผิดพลาด";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: ตัว launcher มีแท็บสำหรับนักพัฒนาที่แสดงบันทึกอย่างละเอียด "
				+ "คุณสามารถเข้าถึงแท็บนี้ได้จากเมนูตัวเลือกของ launcher";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: มีหน้าต่างป๊อปอัปแสดงบันทึก พร้อมปุ่มสำหรับคัดลอกและอัปโหลดบันทึก "
				+ "บันทึกจะถูกสร้างอัตโนมัติเมื่อเริ่มเกม และมีข้อมูลสำคัญสำหรับการวิเคราะห์ปัญหา";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: คลิกขวาที่ instance แล้วเลือก \"การตั้งค่า\" "
				+ "จากนั้นไปที่ส่วนบันทึกเพื่อดูข้อมูลสำคัญจาก STDOUT";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "ลิงก์ Markdown: วางลิงก์ที่มีบันทึกในรูปแบบ Markdown "
				+ "ระบบจะพยายามดึงบันทึก (latest.log, launcher_log.txt, debug.log ฯลฯ) และวิเคราะห์ให้อัตโนมัติ";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "ไม่พบบันทึกของ Launcher";
	}

	@Override
	public String imagenNoEncontrada() {
		return "ไม่พบรูปภาพ";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "ทั่วไป: เลือกประเภท launcher ที่คุณใช้งาน "
				+ "บันทึกของ launcher (launcher_log.txt, stdout ฯลฯ) มีข้อมูลสำคัญที่อาจไม่อยู่ใน latest.log "
				+ "CrashDetector ไม่สามารถอ่านบันทึกของ launcher ได้โดยตรง คุณอาจต้องวางบันทึกด้วยตนเอง<br>"
				+ "ดูข้อมูลเพิ่มเติมที่ <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663\">ลิงก์นี้</a>";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>พบคลาสของ Create หายไป Create มีการเปลี่ยนแปลงครั้งใหญ่ "
				+ "โดยเฉพาะตั้งแต่เวอร์ชัน 6 (กุมภาพันธ์ 2025) ทำให้ addon รุ่นเก่าใช้งานไม่ได้ "
				+ "คุณต้องอัปเดต addon ลบของเก่า หรือใช้เวอร์ชันที่ถูกต้อง</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>พบคลาสของ EpicFight หายไป EpicFight มีการเปลี่ยนแปลงครั้งใหญ่ "
				+ "คุณต้องอัปเดต addon ลบของเก่า หรือใช้เวอร์ชันที่ถูกต้อง</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>คุณกำลังใช้ OpenJ9 ซึ่งไม่รองรับ "
				+ "ควรติดตั้ง JDK ที่รองรับ เช่น Oracle JDK หรือ OpenJDK Hotspot</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError() + "'>แอปนี้ต้องใช้ Java 11 (JDK 11) หรือใหม่กว่า "
				+ "โปรดดาวน์โหลดและติดตั้งเวอร์ชันที่รองรับ</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>คุณกำหนดหน่วยความจำมากเกินไป ทำให้ระบบทำงานไม่เสถียร "
				+ "อาจเกิดจากกำหนด RAM เกินที่มี หรือใช้ JVM 32 บิต</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>ควรใช้ RAM ไม่เกิน 70-80% ของทั้งหมด "
				+ "หากใช้ JVM 32 บิต จำกัดที่ประมาณ 2-3 GB</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>ลดค่า Xmx ในการตั้งค่า launcher "
				+ "เช่น RAM 8GB ใช้ 3-4GB, RAM 16GB ใช้ 6-8GB</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ไฟล์สำคัญของ Forge หาย: '" + archivo
				+ "' โปรดติดตั้ง Forge ใหม่</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ไม่พบ Minecraft เวอร์ชัน " + version + " ในไฟล์ '"
				+ archivo + "' โปรดใช้ Forge ที่ตรงกับเวอร์ชัน</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ไม่พบ target 'fmlclient' การติดตั้ง Forge ไม่สมบูรณ์</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ไม่พบคลาสหลักของ Minecraft อาจติดตั้ง Forge ไม่สมบูรณ์</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>การติดตั้ง Forge ไม่สมบูรณ์ ไฟล์บางส่วนหายไป</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "การติดตั้ง Forge ไม่สมบูรณ์";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ติดตั้ง Forge ใหม่โดยใช้เวอร์ชันที่ตรงกับ Minecraft</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "ดาวน์โหลด Forge อย่างเป็นทางการ";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "วิธีติดตั้ง Forge ใหม่อย่างถูกต้อง";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>ขั้นตอนการติดตั้ง Forge ใหม่:</h3><ol>" + "<li>ดาวน์โหลดตัวติดตั้งจากเว็บไซต์ทางการ</li>"
				+ "<li>ปิด launcher</li>" + "<li>รัน installer</li>" + "<li>เลือก 'Installer'</li>"
				+ "<li>เลือกโฟลเดอร์ Minecraft</li>" + "<li>กด OK และรอให้เสร็จ</li>" + "<li>เปิด launcher ใหม่</li>"
				+ "</ol></body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "วิธีติดตั้ง Forge ใหม่";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ข้อผิดพลาดการเชื่อมโยงไลบรารี: ไม่สามารถโหลดไลบรารี " + nombreBiblioteca
				+ " ได้ วิธีแก้ไขที่เป็นไปได้:<br/><br/>"
				+ "a) เพิ่มไดเรกทอรีที่มีไลบรารีไปยัง -Djava.library.path หรือ -Dorg.lwjgl.librarypath<br/>"
				+ "b) เพิ่มไฟล์ JAR ที่มีไลบรารีไปยัง classpath<br/><br/>"
				+ "ข้อผิดพลาดนี้เกิดขึ้นเมื่อ Minecraft ไม่สามารถหาไฟล์ที่จำเป็นได้ "
				+ "มักเกิดจากการติดตั้งไม่สมบูรณ์หรือปัญหาสิทธิ์ของระบบ</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "ข้อผิดพลาดการเชื่อมโยงไลบรารี";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>ไม่สามารถโหลดไลบรารีได้ วิธีแก้ไข:<br/><br/>"
				+ "a) เพิ่มไดเรกทอรีของไลบรารีไปยัง -Djava.library.path หรือ -Dorg.lwjgl.librarypath<br/>"
				+ "b) เพิ่มไฟล์ JAR ของไลบรารีไปยัง classpath<br/><br/>"
				+ "วิธีเหล่านี้เหมาะสำหรับผู้ใช้ขั้นสูง ผู้ใช้ทั่วไปควรลองติดตั้ง Minecraft ใหม่ก่อน</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>เกิดการชนกันของ ID: ID <strong>" + id
				+ "</strong> ถูกใช้โดย <strong>" + modOrigen + "</strong> แล้ว ขณะพยายามเพิ่ม <strong>" + modDestino
				+ "</strong></b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>เกินจำนวน ID สูงสุดที่อนุญาต ม็อดกำลังใช้ ID นอกช่วงที่รองรับโดย Minecraft เวอร์ชันนี้</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "ปัญหา ID ซ้ำ";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>สำหรับ Minecraft 1.12.2 ให้ติดตั้ง <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a> "
				+ "หรือสำหรับ 1.7.10 ใช้ <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a></b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ใช้เครื่องมือ เช่น <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> "
				+ "หรือ <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> เพื่อแก้ปัญหา ID ซ้ำ</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "ติดตั้ง JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "ติดตั้ง EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "ใช้ IdFix Minus หรือ IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "ใช้ Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "ดูเอกสารภาษาญี่ปุ่น";
	}

	@Override
	public String escanearDeMCreator() {
		return "สแกน MCreator";
	}

	/**
	 * Obtiene el título de la ventana del árbol de mods.
	 */
	@Override
	public String tituloArbolDeMods() {
		return "โครงสร้างม็อดและคลาส";
	}

	/**
	 * Obtiene el texto para la etiqueta de tipo de búsqueda.
	 */
	@Override
	public String tipoBusqueda() {
		return "ประเภท";
	}

	/**
	 * Obtiene el texto para el filtro "Todos".
	 */
	@Override
	public String filtroTodos() {
		return "ทั้งหมด";
	}

	/**
	 * Obtiene el texto para el filtro "แพ็กเกจs".
	 */
	@Override
	public String filtroPaquetes() {
		return "แพ็กเกจ";
	}

	/**
	 * Obtiene el texto para el filtro "Clases".
	 */
	@Override
	public String filtroClases() {
		return "คลาส";
	}

	/**
	 * Obtiene el texto para el filtro "Métodos".
	 */
	@Override
	public String filtroMetodos() {
		return "เมธอด";
	}

	/**
	 * Obtiene el texto para el filtro "Campos".
	 */
	@Override
	public String filtroCampos() {
		return "ฟิลด์";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Campo".
	 */
	@Override
	public String filtroReferenciasCampo() {
		return "การอ้างอิงฟิลด์";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Método".
	 */
	@Override
	public String filtroReferenciasMetodo() {
		return "การอ้างอิงเมธอด";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Clase".
	 */
	@Override
	public String filtroReferenciasClase() {
		return "การอ้างอิงคลาส";
	}

	/**
	 * Obtiene el texto para el tooltip del campo de búsqueda.
	 */
	@Override
	public String tipBuscar() {
		return "พิมพ์ที่นี่เพื่อค้นหาในโครงสร้างม็อด";
	}

	/**
	 * Obtiene el texto para el botón de búsqueda.
	 */
	@Override
	public String botonBuscar() {
		return "ค้นหา";
	}

	/**
	 * Obtiene el texto para el botón de resetear árbol.
	 */
	@Override
	public String botonResetearArbol() {
		return "รีเซ็ตโครงสร้าง";
	}

	/**
	 * Obtiene el texto para indicar los mods cargados.
	 */
	@Override
	public String modsCargados() {
		return "ม็อดที่โหลดแล้ว";
	}

	/**
	 * Obtiene el texto para la categoría de clases.
	 */
	@Override
	public String clases() {
		return "คลาส";
	}

	/**
	 * Obtiene el texto para la categoría de métodos.
	 */
	@Override
	public String metodos() {
		return "เมธอด";
	}

	/**
	 * Obtiene el texto para la categoría de campos.
	 */
	@Override
	public String campos() {
		return "ฟิลด์";
	}

	/**
	 * Obtiene el texto para la categoría de referencias.
	 */
	@Override
	public String referencias() {
		return "การอ้างอิง";
	}

	/**
	 * Obtiene el texto para los resultados de búsqueda.
	 */
	@Override
	public String resultadosBusqueda() {
		return "ผลการค้นหา";
	}

	/**
	 * Obtiene el texto para la opción de buscar referencias.
	 */
	@Override
	public String buscarReferencias() {
		return "ค้นหาการอ้างอิง";
	}

	/**
	 * Obtiene el texto para las referencias de mod.
	 */
	@Override
	public String referenciasMod() {
		return "การอ้างอิงของม็อด";
	}

	/**
	 * Obtiene el texto para las referencias de clase.
	 */
	@Override
	public String referenciasClase() {
		return "การอ้างอิงของคลาส";
	}

	/**
	 * Obtiene el texto para las referencias de método.
	 */
	@Override
	public String referenciasMetodo() {
		return "การอ้างอิงของเมธอด";
	}

	/**
	 * Obtiene el texto para las referencias de campo.
	 */
	@Override
	public String referenciasCampo() {
		return "การอ้างอิงของฟิลด์";
	}

	/**
	 * Obtiene el texto cuando no se encuentran referencias.
	 */
	@Override
	public String noSeEncontraronReferencias() {
		return "ไม่พบการอ้างอิง";
	}

	/**
	 * Obtiene el texto para el detalle de mod.
	 */
	@Override
	public String detalleMod() {
		return "รายละเอียดม็อด:";
	}

	/**
	 * Obtiene el texto para la ubicación.
	 */
	@Override
	public String ubicacion() {
		return "ตำแหน่ง";
	}

	/**
	 * Obtiene el texto para los nombres.
	 */
	@Override
	public String nombres() {
		return "ชื่อ";
	}

	/**
	 * Obtiene el texto para el módulo.
	 */
	@Override
	public String modulo() {
		return "โมดูล";
	}

	/**
	 * Obtiene el texto para el detalle de clase.
	 */
	@Override
	public String detalleClase() {
		return "รายละเอียดคลาส:";
	}

	/**
	 * Obtiene el texto para el detalle de método.
	 */
	@Override
	public String detalleMetodo() {
		return "รายละเอียดเมธอด:";
	}

	/**
	 * Obtiene el texto para el detalle de campo.
	 */
	@Override
	public String detalleCampo() {
		return "รายละเอียดฟิลด์:";
	}

	/**
	 * Obtiene el texto para la clase.
	 */
	@Override
	public String clase() {
		return "คลาส";
	}

	/**
	 * Obtiene el texto para el tipo.
	 */
	@Override
	public String tipo() {
		return "ประเภท";
	}

	/**
	 * Obtiene el texto para las referencias a métodos.
	 */
	@Override
	public String referenciasAMetodos() {
		return "การอ้างอิงถึงเมธอด:";
	}

	/**
	 * Obtiene el texto para las referencias a campos.
	 */
	@Override
	public String referenciasACampos() {
		return "การอ้างอิงถึงฟิลด์:";
	}

	/**
	 * Obtiene el texto para el botón de árbol de mods.
	 * 
	 * @return Texto del botón
	 */
	@Override
	public String arbolDeMods() {
		return "โครงสร้างม็อด";
	}

	/**
	 * Obtiene el texto para método.
	 * 
	 * @return Palabra "Método"
	 */
	@Override
	public String metodo() {
		return "เมธอด";
	}

	/**
	 * Obtiene el texto para campo.
	 * 
	 * @return Palabra "Campo"
	 */
	@Override
	public String campo() {
		return "ฟิลด์";
	}

	@Override
	public String descompilar() {
		return "ถอดรหัส (Decompile)";
	}

	@Override
	public String exportar() {
		return "ส่งออก";
	}

	@Override
	public String importar() {
		return "นำเข้า";
	}

	@Override
	public String errorImportar() {
		return "ข้อผิดพลาดในการนำเข้า";
	}

	@Override
	public String estructuraImportada() {
		return "นำเข้าโครงสร้างแล้ว";
	}

	@Override
	public String estructuraExportada() {
		return "ส่งออกโครงสร้างแล้ว";
	}

	@Override
	public String errorExportar() {
		return "ข้อผิดพลาดในการส่งออก";
	}

	@Override
	public String exportando() {
		return "กำลังส่งออก";
	}

	@Override
	public String exportacionTardara() {
		return "การส่งออกอาจใช้เวลาสักครู่";
	}

	@Override
	public String porFavorEspere() {
		return "โปรดรอสักครู่";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>ไม่พบไฟล์ไบนารีของ VLC จำเป็นต้องมี VLC สำหรับ WaterMedia กรุณาติดตั้งด้วยตนเองจาก https://www.videolan.org/vlc/</b>";
	}

	@Override
	public String descargar_vlc() {
		return "ดาวน์โหลด VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ข้อผิดพลาดร้ายแรง: ชื่อโมดูล '" + nombreModulo
				+ "' มีอักขระที่ไม่ถูกต้อง ส่วน '" + parteInvalida
				+ "' ไม่ใช่ตัวระบุ Java ที่ถูกต้อง ปัญหานี้เกิดขึ้นเมื่อม็อดใช้คำสงวนของ Java (เช่น 'true', 'class') หรืออักขระที่ไม่อนุญาต</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "ชื่อม็อดมีอักขระไม่ถูกต้อง";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "ชื่อม็อด '" + nombreModulo + "' ไม่ถูกต้อง เนื่องจากมี '" + parteInvalida
				+ "' ซึ่งเป็นคำสงวนของ Java หรืออักขระที่ไม่อนุญาต ตรวจสอบใน log เพื่อดูว่าม็อดใดเกี่ยวข้อง (โดยปกติคือชื่อไฟล์ JAR)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "ไปที่โฟลเดอร์ของม็อด และแก้ไขไฟล์ <b>mods.toml</b> ภายในโฟลเดอร์ <b>/META-INF/</b> "
				+ "เปลี่ยนค่า <b>modId</b> ให้ใช้เฉพาะตัวอักษร ตัวเลข และขีดล่าง โดยไม่ใช้คำสงวนของ Java";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "ตัวอย่างชื่อที่ถูกต้อง: 'true_mod_shot_enchantment' แทน 'true.shot.enchantment' "
				+ "ชื่อม็อดต้องไม่มีจุด ขีดกลาง หรือคำสงวนของ Java เช่น 'true', 'false', 'class'";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ข้อผิดพลาดร้ายแรงในม็อด: '" + nombreJar
				+ "'. ไม่มีฟิลด์บังคับ 'mandatory' ในการกำหนด dependency ปัญหานี้เกิดจากไฟล์ mods.toml ไม่ได้ระบุว่า dependency เป็นแบบบังคับหรือไม่</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "Dependency ของม็อดขาดฟิลด์บังคับ";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "ม็อดที่มีปัญหาคือ: <b>" + nombreJar + "</b> ไฟล์นี้มีข้อผิดพลาดในการตั้งค่า dependency";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "เปิดไฟล์ <b>mods.toml</b> ภายในโฟลเดอร์ <b>/META-INF/</b> ของม็อด <b>" + nombreJar + "</b>";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "ในส่วน dependency ให้ตรวจสอบว่าทุกรายการมี <b>mandatory=true</b> หรือ <b>mandatory=false</b> "
				+ "(ตัวอย่าง: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ข้อผิดพลาดร้ายแรงในม็อด: '" + nombreJar
				+ "'. การตั้งค่า access transformer ไม่ถูกต้อง เกิดจากไฟล์กำหนดค่ามีไวยากรณ์ผิดหรืออ้างอิงคลาส/เมธอดที่ไม่มีอยู่</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Access Transformer ไม่ถูกต้อง";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "ม็อดที่มีปัญหาคือ: <b>" + nombreJar + "</b> ม็อดนี้มีการตั้งค่า access transformer ไม่ถูกต้อง";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "เปิดไฟล์ <b>accessTransformer.cfg</b> ภายในม็อด <b>" + nombreJar
				+ "</b> (โดยปกติอยู่ในโฟลเดอร์หลักของไฟล์ JAR)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "แก้ไขไวยากรณ์ของ access transformer ให้ถูกต้อง รูปแบบควรเป็น: <b>access class.method</b> "
				+ "(เช่น public net.minecraft.world.entity.Entity.func_200560_a) "
				+ "และลบบรรทัดที่อ้างอิงคลาสหรือเมธอดที่ไม่มีอยู่ในเวอร์ชัน Minecraft ของคุณ";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ข้อผิดพลาดร้ายแรง: ID ของม็อดใน @Mod ไม่ตรงกับไฟล์ mods.toml ม็อด '" + nombreMod
				+ "' ไม่สามารถโหลดได้เนื่องจาก ID ไม่ตรงกัน</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "ID ม็อดไม่ตรงกัน (@Mod vs mods.toml)";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "ม็อด '" + nombreMod + "' มีความไม่ตรงกันระหว่าง ID ใน <b>@Mod</b> และค่าใน <b>mods.toml</b>";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "ตรวจสอบว่า ID ในคลาสหลักตรงกับค่า <b>modId</b> ในไฟล์ <b>/META-INF/mods.toml</b> "
				+ "เช่น <b>@Mod(\"mymod\")</b> ต้องตรงกับ <b>modId=\"mymod\"</b>";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "หากใช้ Gradle ให้รัน <b>clean</b> หลังแก้ไข เพื่อให้ไฟล์ถูกอัปเดตอย่างถูกต้อง "
				+ "บางครั้งไฟล์เก่าอาจยังค้างอยู่ในโฟลเดอร์ build";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "ไคลเอนต์" : "เซิร์ฟเวอร์";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "เซิร์ฟเวอร์" : "ไคลเอนต์";

		return "<b style='color:#" + config.obtenerColorError() + "'>ข้อผิดพลาดร้ายแรง: มีการพยายามโหลดคลาส '"
				+ nombreClase + "' ในสภาพแวดล้อม " + plataforma + " แต่คลาสนี้ถูกออกแบบสำหรับ " + plataformaOpuesta
				+ " <b>ใช้ฟังก์ชัน 'โครงสร้างม็อด' เพื่อค้นหาว่าม็อดใดพยายามโหลดคลาสนี้</b> "
				+ "ม็อดถูกสร้างมาสำหรับแพลตฟอร์มเฉพาะและไม่สามารถใช้ข้ามกันได้</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "ม็อดอยู่ในแพลตฟอร์มผิด";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "ในแท็บ <b>โครงสร้างม็อด</b> ให้ค้นหาคลาส <b>" + nombreClase + "</b> เพื่อดูว่าม็อดใดเป็นสาเหตุของปัญหา";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "ไคลเอนต์" : "เซิร์ฟเวอร์";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "เซิร์ฟเวอร์" : "ไคลเอนต์";

		return "ม็อดที่พบเป็นม็อดสำหรับ <b>" + plataformaOpuesta + "</b> และไม่ควรใช้ในสภาพแวดล้อม " + plataforma;
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "ลบม็อดที่มีปัญหาออกจากโฟลเดอร์ <b>mods</b> " + "และหาม็อดทางเลือกที่รองรับแพลตฟอร์มที่ถูกต้อง";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("ข้อผิดพลาดร้ายแรง: ไม่มี metadata สำหรับ modId '").append(modIdFaltante).append("' ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("ม็อดต่อไปนี้อาจเป็นสาเหตุ: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", และอื่น ๆ...");
			mensaje.append("</b> ");
		}

		mensaje.append("เกิดขึ้นเมื่อม็อดต้องพึ่งพาม็อดอื่นที่ไม่ได้ติดตั้ง หรือไฟล์ mods.toml ไม่ถูกต้อง</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "ไม่มี metadata ใน mods.toml";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		return "มีม็อดที่พยายามใช้ dependency '" + modIdFaltante
				+ "' แต่ไม่ได้ติดตั้ง ใช้ฟังก์ชัน <b>โครงสร้างม็อด</b> เพื่อตรวจสอบ";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "ตัวเลือก:<br/>" + "1. ติดตั้งม็อดที่ขาด ('" + modIdFaltante + "')<br/>"
				+ "2. ลบม็อดที่ต้องพึ่งพาม็อดนี้";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "หาก '" + modIdFaltante + "' เป็นไลบรารี (เช่น forge หรือ minecraft) ให้ตรวจสอบเวอร์ชันที่ถูกต้อง "
				+ "หรือดูหน้าดาวน์โหลดของม็อดเพื่อดู dependency ที่จำเป็น";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>คำเตือน: เกิดข้อผิดพลาดในระบบเสียง เสียงและเพลงถูกปิดใช้งาน "
				+ "มักเกี่ยวข้องกับ SoundPhysicsMod หรือความขัดแย้งของไลบรารีเสียง</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "ข้อผิดพลาดระบบเสียง";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "ตรวจสอบว่า SoundPhysicsMod เป็นเวอร์ชันที่ถูกต้อง";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "ลองลบม็อดเสียงอื่นชั่วคราวเพื่อตรวจสอบความขัดแย้ง";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "ตรวจสอบโฟลเดอร์ <b>logs</b> เพื่อดูข้อผิดพลาด LWJGL หรือ OpenAL";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("ข้อผิดพลาดร้ายแรง: คลาส '").append(nombreClase)
				.append("' ถูกลงทะเบียนเป็น event listener แต่ไม่มีเมธอดที่ถูกต้อง ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("พบในม็อด: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", และอื่น ๆ...");
			mensaje.append("</b> ");
		}

		mensaje.append("เกิดจากไม่มีเมธอดที่มี @SubscribeEvent</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "คลาสที่ลงทะเบียนไม่มี Event Listener";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("คลาสที่มีปัญหาอยู่ในม็อดเหล่านี้: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", และอื่น ๆ...");
			paso.append("</b> ม็อดเหล่านี้พยายามลงทะเบียน event โดยไม่มีเมธอดที่ถูกต้อง");
			return paso.toString();
		}
		return "คลาส <b>" + nombreClase
				+ "</b> ถูกลงทะเบียนเพื่อรับ event แต่ไม่มีเมธอดที่มี annotation <b>@SubscribeEvent</b> "
				+ "ใช้ฟังก์ชัน <b>โครงสร้างม็อด</b> เพื่อตรวจสอบว่าม็อดใดมีคลาสนี้";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "ในซอร์สโค้ด ตรวจสอบว่าคลาส <b>" + nombreClase + "</b> มีอย่างน้อยหนึ่งเมธอดที่มีรูปแบบ: "
				+ "<b>@SubscribeEvent public void nombreMetodo(Evento evento) { ... }</b> "
				+ "หากเป็นคลาสภายใน ตรวจสอบว่าไม่ได้ถูกกำหนดเป็น static";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("สำหรับม็อด (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", เป็นต้น");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("ติดต่อผู้พัฒนาม็อดเพื่อแก้ไขปัญหา ");
			} else {
				paso.append("ติดต่อผู้พัฒนาม็อดเหล่านี้เพื่อแก้ไขปัญหา ");
			}
		}

		paso.append("หากคุณเป็นผู้พัฒนา ให้ลบการลงทะเบียนคลาสนี้ใน EventBus หรือเพิ่มเมธอด @SubscribeEvent ที่ถูกต้อง");
		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		String mensaje = "<b style='color:#" + config.obtenerColorError()
				+ "'>ข้อผิดพลาดร้ายแรง: เกิดข้อผิดพลาดใน UnionFileSystem ขณะประมวลผล '" + nombreArchivo + "' ";

		mensaje += "ปัญหานี้พบได้บ่อยในม็อดแพ็กสำเร็จรูป และมักเกี่ยวข้องกับตัว launcher ";

		mensaje += "ระบบไม่สามารถอ่านไฟล์ม็อดได้เนื่องจากไฟล์เสียหายหรือดาวน์โหลดไม่สมบูรณ์</b>";

		return mensaje;
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "ข้อผิดพลาด UnionFileSystem - ไฟล์เสียหาย";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		String paso = "ตรวจพบข้อผิดพลาด <b>cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException</b> กับไฟล์ <b>"
				+ nombreArchivo + "</b> ";

		paso += "ปัญหานี้มักเกิดจากไฟล์ดาวน์โหลดไม่สมบูรณ์ใน launcher ของม็อดแพ็ก";

		return paso;
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "ติดตั้งม็อดแพ็กใหม่ทั้งหมด ปัญหานี้มักเกิดจาก launcher ดาวน์โหลดไฟล์ไม่ครบ "
				+ "หากใช้ <b>Luna Pixel</b> แนะนำให้ใช้ <b>ATLauncher</b> ซึ่งจัดการไฟล์ได้ดีกว่า";
	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "หากยังมีปัญหา:<br/>" + "1. เปลี่ยน launcher<br/>" + "2. หากใช้ Luna Pixel ให้ใช้ ATLauncher<br/>"
				+ "3. ตรวจสอบอินเทอร์เน็ตและพื้นที่ดิสก์ก่อนติดตั้งใหม่";
	}

	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "เปิดใช้งาน ProxySysOutSysErr?\n\n"
				+ "ตัวเลือกนี้ทำให้ CrashDetector เข้าถึง System.out และ System.err ได้เมื่อ launcher ไม่มี log\n\n"
				+ "ควรใช้เมื่อไม่สามารถแนบ log ได้เท่านั้น\n\n" + "คำเตือน: อาจรบกวนม็อดหรือ launcher บางตัว\n\n"
				+ "ต้องรีสตาร์ทแอปเพื่อให้มีผล";
	}

	@Override
	public String confirmacionTitulo() {
		return "ยืนยัน";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "เปิดใช้งาน ProxySysOutSysErr แล้ว\n\n" + "ต้องรีสตาร์ท CrashDetector เพื่อให้มีผล";
	}

	@Override
	public String informacionTitulo() {
		return "ข้อมูล";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {

		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("ข้อผิดพลาดร้ายแรง: AzureLib และ GeckoLib เริ่มทำงานเร็วเกินไป ");
		} else if (azureLibError) {
			mensaje.append("ข้อผิดพลาดร้ายแรง: AzureLib เริ่มทำงานเร็วเกินไป ");
		} else if (geckoLibError) {
			mensaje.append("ข้อผิดพลาดร้ายแรง: GeckoLib เริ่มทำงานเร็วเกินไป ");
		}

		mensaje.append("ปัญหานี้เกิดจากการใช้ม็อด Fabric กับไลบรารีเวอร์ชันที่ไม่ใช่ Fabric ");

		if (connectorPresente) {
			mensaje.append("ตรวจพบตัวเชื่อม (เช่น Sinytra Connector) ซึ่งบ่งชี้ว่ากำลังใช้ม็อด Fabric บน Forge ");
			mensaje.append("ตรวจสอบ log เพื่อดูว่าม็อดใดเป็นสาเหตุ ");
		}

		mensaje.append("AzureLib และ GeckoLib ต้องตรงกับแพลตฟอร์มที่ใช้งาน (Fabric หรือ Forge) ");
		mensaje.append("เกมไม่สามารถโหลดม็อดแอนิเมชันได้เนื่องจากความขัดแย้งนี้");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "ไลบรารีเริ่มทำงานเร็วเกินไป";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "ตรวจสอบข้อผิดพลาดการเริ่มต้นของ FabricMC เพื่อระบุว่าม็อดใดเป็นสาเหตุ";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "ตรวจสอบว่าใช้ AzureLib/GeckoLib เวอร์ชันที่ถูกต้องสำหรับแพลตฟอร์ม (Forge หรือ Fabric)";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ข้อผิดพลาดร้ายแรง: C2ME ไม่เข้ากันกับม็อดเชื่อมต่อ "
				+ "เกิดจาก C2ME พยายามเข้าถึงส่วนภายในของ Java ที่ถูกจำกัดในสภาพแวดล้อมที่มี Sinytra Connector "
				+ "หรือม็อดความเข้ากันได้อื่น ๆ "
				+ "<b>C2ME ไม่รองรับสภาพแวดล้อมนี้ แต่ <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> เป็นทางเลือกที่แนะนำ</b> "
				+ "เกมไม่สามารถเริ่มได้เนื่องจากข้อจำกัดด้านความปลอดภัยของ Java</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "C2ME ไม่เข้ากันกับม็อดเชื่อมต่อ";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "ลบ C2ME ออกจากโฟลเดอร์ mods";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "ดาวน์โหลดและติดตั้ง <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> แทน (รองรับ Sinytra Connector)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "ตรวจสอบว่าม็อดเชื่อมต่อทั้งหมดอัปเดตเป็นเวอร์ชันล่าสุด";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ข้อผิดพลาดร้ายแรง: ไม่สามารถโหลดปลั๊กอิน JEI สำหรับม็อด '" + modId + "' ได้ " + "คลาส '"
				+ nombreClase + "' (plugin ID: '" + pluginId + "') ทำให้เกิดข้อผิดพลาดและทำให้เกมล่มระหว่างเริ่มต้น "
				+ "มักเกิดจากการเชื่อมต่อ JEI ที่ไม่เข้ากันหรือมีข้อบกพร่อง</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "ปลั๊กอิน JEI ล้มเหลว (ทำให้เกมล่ม)";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "ม็อด <b>" + modId + "</b> มีปลั๊กอิน JEI ที่มีปัญหา ใช้ฟังก์ชัน <b>โครงสร้างม็อด</b> เพื่อตรวจสอบ";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "ลบม็อด <b>" + modId + "</b> ชั่วคราวเพื่อตรวจสอบว่าแก้ปัญหาได้หรือไม่";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "ตรวจสอบการอัปเดตของม็อด <b>" + modId + "</b> หรือแจ้งผู้พัฒนา ขณะนี้ควรลบม็อดเพื่อให้เกมเริ่มได้";
	}

	@Override
	public String tituloLectador() {
		return "ตัวอ่านคอนโซล - Crash Detector";
	}

	@Override
	public String obtenerTituloLeyenda() {
		return "คำอธิบายสี";
	}

	@Override
	public String obtenerErrorEnLeyenda() {
		return "ข้อผิดพลาดร้ายแรง";
	}

	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "สแต็กเทรซ";
	}

	@Override
	public String obtenerTituloError() {
		return "ข้อผิดพลาด";
	}

	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "เกิดข้อผิดพลาดขณะประมวลผลบรรทัดที่เลือก";
	}

	@Override
	public String obtenerNombreError() {
		return "ชื่อข้อผิดพลาด";
	}

	@Override
	public String obtenerDescripcionError() {
		return "คำอธิบายโดยละเอียด";
	}

	@Override
	public String obtenerSeleccionarConsola() {
		return "เลือกคอนโซล";
	}

	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "ข้อผิดพลาดที่ไม่ทราบสาเหตุ";
	}

	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "ตรวจพบข้อผิดพลาดร้ายแรงในบันทึก โปรดตรวจสอบสแต็กเทรซเพื่อหาสาเหตุ";
	}

	@Override
	public String obtenerErrorLecturaArchivo() {
		return "ไม่สามารถอ่านไฟล์บันทึกได้ กรุณาตรวจสอบว่าไฟล์มีอยู่และมีสิทธิ์เข้าถึง";
	}

	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "ตัววิเคราะห์ Log";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("ข้อผิดพลาดร้ายแรง: ไม่สามารถลงทะเบียนตัวรับเหตุการณ์อัตโนมัติสำหรับม็อด '").append(modId)
				.append("' ");

		mensaje.append("คลาสที่มีปัญหา: <b>").append(nombreClase).append("</b> ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("พบใน: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", และอื่น ๆ...");
			mensaje.append("</b> ");
		}

		mensaje.append("เกิดจากไม่สามารถโหลดคลาสที่ลงทะเบียนเป็น subscriber ได้ ");
		mensaje.append("<b>ตรวจสอบข้อผิดพลาดอื่นใน log เนื่องจากสาเหตุจริงอาจอยู่ส่วนอื่น</b>");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "ล้มเหลวในการลงทะเบียน Subscriber อัตโนมัติ";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "ม็อด <b>" + modId + "</b> กำลังพยายามลงทะเบียนคลาส <b>" + nombreClase
				+ "</b> เป็น subscriber อัตโนมัติ แต่ล้มเหลว ตรวจสอบว่าคลาสนี้มีอยู่และสามารถเข้าถึงได้";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {

		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("คลาสที่มีปัญหา <b>" + nombreClase + "</b> อยู่ในไฟล์เหล่านี้: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", และอื่น ๆ...");
			paso.append("</b> ");
			paso.append("ใช้ฟังก์ชัน <b>โครงสร้างม็อด</b> เพื่อตรวจสอบว่าไฟล์ใดมีคลาสนี้");
			return paso.toString();
		}

		return "ไม่พบคลาส <b>" + nombreClase + "</b> ในไฟล์ม็อดใดเลย ตรวจสอบว่าม็อด <b>" + modId
				+ "</b> ติดตั้งถูกต้อง และใช้ฟังก์ชัน <b>โครงสร้างม็อด</b> เพื่อตรวจสอบ";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "อัปเดตม็อด <b>" + modId + "</b> เป็นเวอร์ชันล่าสุดที่รองรับ Minecraft และ Forge "
				+ "หากยังมีปัญหา ให้ติดต่อผู้พัฒนา";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "ตรวจสอบข้อผิดพลาดอื่นใน log ก่อนหน้านี้ "
				+ "เนื่องจากสาเหตุจริงอาจอยู่ก่อนหน้านี้และทำให้คลาสโหลดไม่ครบ";
	}

	@Override
	public String limpiado() {
		return "ทำความสะอาดแล้ว";
	}

	@Override
	public String original() {
		return "ต้นฉบับ";
	}

	@Override
	public String verEnConsola() {
		return "ดูในคอนโซล";
	}

	@Override
	public String advertencia() {
		return "คำเตือน";
	}

	@Override
	public String fatal() {
		return "ร้ายแรง";
	}

	@Override
	public String noRegistroDeBattly() {
		return "BattlyLauncher ไม่มี log หรือคอนโซลให้คัดลอก "
				+ "คุณสามารถใช้ ProxySysOutSysErr เพื่อดักจับ STDOUT และ STDERR "
				+ "แต่ตัวเลือกนี้อาจขัดแย้งกับม็อดบางตัว";
	}

	@Override
	public String noRegistroDeNightWorld() {
		return "ต้องเปิดโหมดดีบักใน NightWorld เพื่อให้ได้ log ของ launcher " + "ซึ่งมี STDOUT และ STDERR";
	}

	@Override
	public String noRegistroDeMCServidor() {
		return "คุณต้องคัดลอกหรือบันทึกเนื้อหาจากเทอร์มินัลของเซิร์ฟเวอร์ "
				+ "เพราะมีข้อมูลสำคัญที่ไม่มีใน log อื่น เช่น STDOUT และ STDERR "
				+ "แนะนำให้บันทึกลงไฟล์โดยเพิ่ม >> cd_launcherlog หลังคำสั่ง";
	}

	// ===== LexForge / NeoForge =====

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {

		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("ข้อผิดพลาดร้ายแรง: ตรวจพบ LexForge transformer ใน NeoForge</b> ");

		sb.append("คลาส: <b>").append(claseReceptora).append("</b> ");
		sb.append("อินเทอร์เฟซ: <b>").append(interfazObjetivo).append("</b> ");
		sb.append("เมธอดที่หายไป: <b>").append(firmaMetodoFaltante).append("</b> ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("พบใน: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", และอื่น ๆ...");
			sb.append("</b> ");
		} else {
			sb.append("ไม่พบ JAR ที่มีคลาสนี้ ");
		}

		sb.append("เกิดจากการใช้ ModLauncher ที่ไม่เข้ากันกับ NeoForge " + "ควรอัปเดตหรือเปลี่ยนเวอร์ชัน");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "LexForge Transformer ใช้กับ NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {

		return "ตรวจสอบ transformer: <b>" + claseReceptora + "</b> " + "และ API <b>" + interfazObjetivo + "</b> "
				+ "เมธอดที่ขาด: <b>" + firmaMetodoFaltante + "</b>";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {

		StringBuilder sb = new StringBuilder();

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("พบในม็อด: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", และอื่น ๆ...");
			sb.append("</b> ");
		}

		sb.append("ลบหรือใช้เวอร์ชันที่รองรับ NeoForge");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "ใช้เวอร์ชันที่รองรับ NeoForge หรือคอมไพล์ใหม่";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "ล้างโฟลเดอร์ mods และ cache แล้วลองใหม่";
	}

	// ===== WaterMedia / Xenon =====

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {

		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("WaterMedia ไม่สามารถเริ่มได้: Xenon (").append(modId).append(") ");

		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");

		sb.append("ไม่เข้ากัน</b> ");

		sb.append("ลบ Xenon และใช้ Embeddium หรือ Sodium แทน ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("พบใน: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", และอื่น ๆ...");
			sb.append("</b>");
		}

		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia ไม่เข้ากันกับ Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {

		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";

		return "ตรวจพบ " + label + " ไม่เข้ากันกับ WaterMedia ให้ลบออก";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("ตำแหน่งไฟล์: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", และอื่น ๆ...");
			sb.append("</b>. ให้ลบไฟล์ JAR นี้ออก");
			return sb.toString();
		}
		return "ไม่พบไฟล์ JAR ตรวจสอบโฟลเดอร์ mods และลบ Xenon";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "ติดตั้ง Embeddium หรือ Sodium แทน แล้วรีสตาร์ทเกม";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "เกิดข้อผิดพลาดระหว่างการบีบอัด (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>ตัวบีบอัด (Deflater) ถูกปิดระหว่างการคัดลอกทรัพยากรของ TACZ</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("เกี่ยวข้องกับ: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", และอื่น ๆ");
			sb.append("</b>. ");
		}
		sb.append(
				"<br/><b>วิธีแก้:</b> ในไฟล์ <code>tacz/tacz-pre.toml</code> ตั้งค่า <code>DefaultPackDebug=true</code> ")
				.append("หากจำเป็น ให้สร้างโลกก่อนแล้วจึงเปิดใช้งาน");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "ในไฟล์ tacz/tacz-pre.toml ให้ตั้งค่า DefaultPackDebug=true และหากจำเป็นให้สร้างโลกก่อนแล้วจึงเปิดใช้งาน";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "ฟังก์ชันความหนาแน่นที่ไม่ได้เชื่อมโยง";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>ขาดฟังก์ชันความหนาแน่นในบันทึก</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("ที่ขาด: ");
			for (int i = 0; i < Math.min(4, claves.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append("<code>").append(claves.get(i)).append("</code>");
			}
			if (claves.size() > 4)
				sb.append(", …");
			sb.append(". ");
		}
		sb.append("<br/><b>วิธีแก้:</b> ติดตั้งหรือเปิดใช้งานม็อด/datapack ที่มีฟังก์ชันเหล่านี้ แล้วรีสตาร์ทเกม "
				+ "อีกสาเหตุที่พบบ่อยคือคุณมีม็อดที่จำเป็นอยู่แล้ว แต่ตัวม็อดมีปัญหาหรือขัดแย้งกับม็อดอื่น "
				+ "เช่น Terralith ซึ่งมีปัญหาหลายอย่างและอาจทำให้เกิดข้อผิดพลาดนี้ รวมถึงข้อผิดพลาดเกี่ยวกับ JSON อื่น ๆ");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "ติดตั้งหรือเปิดใช้งานม็อด/datapack ที่มีฟังก์ชันเหล่านี้ แล้วรีสตาร์ทเกม";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ไม่พบรายการในบันทึก: ").append(claveFaltante).append(". ");
		sb.append("พบได้บ่อยกับ Steam & Railways เวอร์ชันอัลฟาสำหรับ Create 6");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (alpha)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "ลบหรือแทนที่ Steam & Railways เวอร์ชันอัลฟาสำหรับ Create 6 ด้วยเวอร์ชันที่เข้ากันได้";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ข้อขัดแย้งในการโหลด: Multiworld ร่วมกับ Sodium/Embeddium/Rubidium ทำให้เกิด ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance). ")
				.append("คำแนะนำ: ลบ Multiworld หรือม็อดเพิ่มประสิทธิภาพ หรือใช้เวอร์ชันที่เข้ากันได้");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "ข้อขัดแย้ง: Multiworld กับม็อดเพิ่มประสิทธิภาพ";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "ถอนการติดตั้ง Multiworld หรือ Sodium/Embeddium/Rubidium หรืออัปเดตให้เป็นเวอร์ชันที่เข้ากันได้";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Sodium ตรวจพบไดรเวอร์กราฟิกที่ไม่เข้ากัน "
				+ "ให้อัปเดตไดรเวอร์ GPU ให้ถึงเวอร์ชันขั้นต่ำที่ต้องการ หรือทำตามคู่มือของ Sodium" + "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "ข้อผิดพลาดบริบท OpenGL";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OpenGL ล้มเหลว: ไม่มีบริบทปัจจุบัน หรือฟังก์ชันไม่พร้อมใช้งานในบริบทนี้ "
				+ "นอกจากนี้อาจเป็นปัญหาเกี่ยวกับไดรเวอร์กราฟิก" + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "อัปเดตหรือติดตั้งไดรเวอร์ GPU ใหม่ แล้วรีสตาร์ทระบบ ปิด overlay และลองรันโดยไม่มีม็อดเพิ่มประสิทธิภาพ";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "คัดลอกลิงก์ไปยังคลิปบอร์ดแล้ว";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		return "ค้นหาภายในไฟล์บีบอัด (.zip/.jar/.war/.ear/.fpm/.rar ของ Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ข้อผิดพลาดความละเอียดของเท็กซ์เจอร์: ไม่สามารถปรับ " + recurso + " - ขนาด: " + tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "ข้อผิดพลาดความละเอียดของเท็กซ์เจอร์";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "ข้อผิดพลาดนี้เกิดขึ้นเมื่อเท็กซ์เจอร์มีความละเอียดสูงเกินไป หรือมีแพ็กทรัพยากรมากเกินไป "
				+ "ลองใช้แพ็กทรัพยากรที่มีความละเอียดต่ำลง หรือลบบางแพ็กออก "
				+ "ตรวจสอบว่าคุณไม่ได้เพิ่มเท็กซ์เจอร์แบบกำหนดเองที่มีความละเอียดสูงเกินค่าที่รองรับ";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ข้อผิดพลาดบริการ ModLauncher: เส้นทางมีอักขระที่ไม่ถูกต้อง "
				+ "บริการของ ModLauncher ไม่สามารถประมวลผลเส้นทางที่มีอักขระที่ไม่ใช่ ASCII หรืออักขระพิเศษได้ "
				+ "อักขระที่มีปัญหา เช่น: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요 และโดยเฉพาะอักขระ '\"' เมื่ออยู่ท้ายชื่อ "
				+ "บริการทั่วไปของ ModLauncher ได้แก่ CrashDetector, " + Config.obtenerInstancia().obtenerNombreCD()
				+ ", FeatureCreep, Vivicraft, Optifine, Sodium, clonos, Iris Shaders/Oculus, MixerLogger, CrashAssistant และ Sintrya Connector "
				+ "คุณสามารถลบบริการทั้งหมดได้ แต่ปัญหาอื่นอาจเกิดขึ้นจากชื่อเส้นทาง "
				+ "วิธีแก้ไข: เปลี่ยนชื่ออินสแตนซ์ให้ใช้เฉพาะอักขระ ASCII (a-z, A-Z, 0-9) และหลีกเลี่ยงช่องว่างหรืออักขระพิเศษ</b>";
	}

	@Override
	public String nombre_error_modlauncher_path() {
		return "ข้อผิดพลาดเส้นทางใน ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "ข้อผิดพลาดนี้เกิดขึ้นเมื่อเส้นทางของอินสแตนซ์มีอักขระที่ไม่ใช่ ASCII หรืออักขระพิเศษ "
				+ "บริการของ ModLauncher ไม่สามารถจัดการเส้นทางเหล่านี้ได้ "
				+ "วิธีแก้ไข: เปลี่ยนชื่ออินสแตนซ์ให้ใช้เฉพาะอักขระ ASCII (a-z, A-Z, 0-9) และหลีกเลี่ยงช่องว่างหรืออักขระพิเศษ "
				+ "ควรระวังอักขระ '\"' ซึ่งมักทำให้เกิดปัญหา โดยเฉพาะเมื่ออยู่ท้ายชื่อ";
	}

	@Override
	public String tituloEditorCodice() {
		return "ตัวแก้ไขโค้ด";
	}

	@Override
	public String nuevo() {
		return "ใหม่";
	}

	@Override
	public String actualizarSeleccionado() {
		return "อัปเดตรายการที่เลือก";
	}

	@Override
	public String eliminarSeleccionado() {
		return "ลบรายการที่เลือก";
	}

	@Override
	public String exportarJSON() {
		return "ส่งออก JSON...";
	}

	@Override
	public String guardarTodo() {
		return "บันทึกทั้งหมด";
	}

	@Override
	public String general() {
		return "ทั่วไป";
	}

	@Override
	public String id() {
		return "รหัส (ID)";
	}

	@Override
	public String paraBuscar() {
		return "ข้อความสำหรับค้นหา";
	}

	@Override
	public String filtro() {
		return "ตัวกรอง (ID)";
	}

	@Override
	public String criticalidad() {
		return "ระดับความรุนแรง (ADVERTENCIA/ERROR/FATAL)";
	}

	@Override
	public String prioridad() {
		return "ลำดับความสำคัญ";
	}

	@Override
	public String lista() {
		return "รายการตรวจสอบ";
	}

	@Override
	public String colIdioma() {
		return "ภาษา";
	}

	@Override
	public String colNombre() {
		return "ชื่อ";
	}

	@Override
	public String colResultado() {
		return "ผลลัพธ์";
	}

	@Override
	public String vistaJson() {
		return "ตัวอย่าง JSON";
	}

	@Override
	public String idiomas() {
		return "ภาษา (ต้องกรอกทั้งหมด)";
	}

	@Override
	public String elegirFiltro() {
		return "เลือก...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "กรุณาเลือกตัวกรอง";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "ตัวกรองที่มีอยู่";
	}

	@Override
	public String faltanCampos() {
		return "กรุณากรอกข้อมูลทั่วไปที่จำเป็นให้ครบทั้งหมด";
	}

	@Override
	public String critInvalida() {
		return "ระดับความรุนแรงไม่ถูกต้อง ใช้ ADVERTENCIA, ERROR หรือ FATAL";
	}

	@Override
	public String filtroNoExiste() {
		return "ไม่พบตัวกรองที่ระบุ";
	}

	@Override
	public String faltanIdiomas() {
		return "กรุณากรอกชื่อและผลลัพธ์สำหรับทุกภาษา:";
	}

	@Override
	public String verificacionInvalida() {
		return "การตรวจสอบไม่ถูกต้อง โปรดตรวจสอบข้อมูล";
	}

	@Override
	public String guardadoOk() {
		return "บันทึกเรียบร้อยแล้ว";
	}

	@Override
	public String editorCodiceBoton() {
		return "เพิ่มเหตุผล";
	}

	@Override
	public String descripcionEditorCodice() {
		return "คุณสามารถเพิ่มเหตุผลได้ที่นี่ "
				+ "คุณต้องมี ID ซึ่งเป็นสตริงที่ไม่มีอักขระพิเศษ เครื่องหมายวรรคตอน หรือช่องว่าง "
				+ "สำหรับตัวกรอง คุณสามารถใช้ \"linea contiene\" เพื่อค้นหาข้อความในบรรทัด "
				+ "\"todo contiene\" หากบันทึกมีข้อความนั้น "
				+ "\"regex linea\" หากบรรทัดตรงกับ regex และ \"regex todos\" (แนะนำให้ใช้แบบบรรทัด) "
				+ "คุณต้องกำหนดระดับความรุนแรงเป็น FATAL, ERROR หรือ ADVERTENCIA สำหรับสี "
				+ "สำหรับทุกภาษา คุณต้องระบุชื่อและผลลัพธ์เพื่อแสดงบนหน้าจอ "
				+ "คุณสามารถเพิ่มหรือลบการตรวจสอบได้ และบันทึกเมื่อกรอกครบแล้ว";
	}

	@Override
	public String descartarCambios() {
		return "ละทิ้งการเปลี่ยนแปลงที่ยังไม่ได้บันทึกในการตรวจสอบนี้หรือไม่?";
	}

	@Override
	public String confirmacion() {
		return "การยืนยัน";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "คุณต้องการบันทึกการเปลี่ยนแปลงก่อนออกหรือไม่?";
	}

	@Override
	public String salirSinGuardar() {
		return "ออกโดยไม่บันทึก";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ข้อผิดพลาดร้ายแรง: ไม่สามารถโหลดบริการของ ModLauncher (IDependencyLocator)<br>");
		sb.append("🔹 <b>คลาสที่มีปัญหา:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>ม็อดที่ได้รับผลกระทบ:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append("🔸 <b>ไม่สามารถระบุชื่อม็อดได้</b> โปรดตรวจสอบม็อดที่ติดตั้งล่าสุดหรือม็อดทดลอง<br>");
		}

		sb.append("🔸 <b>สาเหตุ:</b> ไฟล์ <code>META-INF/services/...</code> ของม็อดเสียหาย ");
		sb.append("ไม่เข้ากันกับเวอร์ชัน Forge/NeoForge นี้ หรือเป็นม็อดสำหรับเวอร์ชันอื่น<br>");
		sb.append("🔸 <b>ผลกระทบ:</b> Forge/NeoForge ไม่สามารถลงทะเบียน dependency ของม็อดได้ ");
		sb.append("ทำให้เกมไม่สามารถเริ่มทำงานได้<br>");
		sb.append("🔸 <b>วิธีแก้ไข:</b> อัปเดต ติดตั้งใหม่ หรือลบม็อดที่มีปัญหา ");
		sb.append("หากเป็นม็อดที่กำลังพัฒนา ให้ตรวจสอบว่า build ตรงกับเวอร์ชัน Forge/NeoForge ที่ใช้อยู่");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "ข้อผิดพลาดการตั้งค่าบริการ (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. ระบุม็อดที่เป็นสาเหตุ โดยตรวจสอบม็อดที่ติดตั้งล่าสุดหรือม็อดทดลอง";
		}
		return "1. ม็อดที่มีปัญหาคือ: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. อัปเดต ติดตั้งใหม่ หรือลบม็อด และตรวจสอบว่าเป็นเวอร์ชันที่รองรับ Forge/NeoForge";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>ข้อผิดพลาดร้ายแรง: ฟิลด์ไม่พบ</b><br>"
				+ "ม็อดพยายามเข้าถึงฟิลด์ <b style='color:#" + colorCodigo + "'>" + campo + "</b> "
				+ "ซึ่งไม่มีอยู่ในเวอร์ชันของเกมหรือม็อดอื่น<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "ฟิลด์ไม่พบ (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. ข้อผิดพลาดนี้มักเกิดจากม็อดไม่เข้ากันกับเวอร์ชันเกมหรือม็อดอื่น";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. อัปเดตม็อดทั้งหมด หากยังมีปัญหา ให้ติดต่อผู้พัฒนาม็อด";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>ข้อผิดพลาดร้ายแรง: เมธอดไม่พบ</b><br>"
				+ "ม็อดพยายามเรียกเมธอด <b style='color:#" + colorCodigo + "'>" + metodo + "</b> "
				+ "ซึ่งไม่มีอยู่ในเวอร์ชันของเกมหรือม็อดอื่น<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "เมธอดไม่พบ (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. ข้อผิดพลาดนี้เกิดจากม็อดไม่เข้ากันกับเวอร์ชันปัจจุบัน";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. อัปเดตม็อดทั้งหมด หากยังมีปัญหา ให้รายงานไปยังผู้พัฒนา";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();

		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "<div style='color:" + colorTexto + ";'>" + "<strong>ต้องการความช่วยเหลือ?</strong><br>"
				+ "หากคุณไม่ทราบวิธีแก้ไข สามารถขอความช่วยเหลือผ่านช่องทางโซเชียลของเราได้ " + "ใช้ปุ่ม <img src='"
				+ iconoCompartir + "' style='height:12px;vertical-align:middle;'/> "
				+ "<strong>แชร์</strong> เพื่อส่งบันทึกและผลลัพธ์ให้ทีมงาน "
				+ "หากคุณเป็นผู้สร้าง modpack หรือองค์กร สามารถแก้ไข <code>crash_detector/plantilla.htm</code> "
				+ "เพื่อกำหนดลิงก์ของทีมคุณเอง</div>";
	}

	@Override
	public String restablecerPlantilla() {
		return "รีเซ็ตเทมเพลต";
	}

	@Override
	public String restablecer() {
		return "รีเซ็ต";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		return "รีเซ็ต " + nombreImagen + " เป็นค่าเริ่มต้นหรือไม่?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		return "รีเซ็ตเทมเพลตเป็นค่าเริ่มต้นหรือไม่?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ไม่พบคลาสของ AzureLib หากคุณติดตั้งแล้ว ให้ลองใช้เวอร์ชันก่อนวันที่ 8 ตุลาคม 2025 "
				+ "หากยังไม่มี ให้ติดตั้งเวอร์ชันล่าสุด</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ม็อด <code>healight</code> ทำให้เกิดข้อผิดพลาดร้ายแรง: <code>java.lang.NoSuchFieldError: INT</code> "
				+ "เนื่องจากพยายามเข้าถึงฟิลด์ที่ไม่มีอยู่ใน MinecraftForge 47.10 (1.20+) "
				+ "ทำให้เกมไม่สามารถเริ่มได้</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• ลบหรืออัปเดตม็อด <code>healight</code> เนื่องจากไม่รองรับเวอร์ชันนี้ "
				+ "ลองหาเวอร์ชันใหม่หรือใช้ม็อดทางเลือก";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "ข้อผิดพลาดร้ายแรง: healight - ไม่พบฟิลด์ INT";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {

		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();

		sb.append("<b style='color:#").append(colorError).append(";'>").append("คลาส <code>").append(clase)
				.append("</code> ไม่ได้ implement เมธอดที่จำเป็น:<br>").append("<code>").append(metodo)
				.append("</code><br>").append("จากอินเทอร์เฟซ <code>").append(interfaz).append("</code>");

		if (!origen.isEmpty()) {
			sb.append("<br><br>ม็อดหรือไฟล์ที่เกี่ยวข้อง: <code>").append(origen).append("</code>");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• ข้อผิดพลาดนี้เกิดจากม็อดที่ implement อินเทอร์เฟซแต่ขาดเมธอดที่จำเป็น "
				+ "ให้ทำการอัปเดตม็อดทั้งหมดที่เกี่ยวข้อง " + "หากไม่ทราบ ให้ตรวจสอบชื่อม็อดจากข้อความข้อผิดพลาด";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "เมธอดของอินเทอร์เฟซไม่ได้ถูก implement (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "ม็อดกำลังพยายามโหลดคลาสฝั่ง <b>ไคลเอนต์</b> "
				+ "(<code>AnimationMetadataSection</code>) บน <b>เซิร์ฟเวอร์แบบ dedicated</b> ซึ่งไม่สามารถทำได้ "
				+ "ข้อผิดพลาดนี้มักเกิดเมื่อม็อดไม่ได้แยกโค้ดระหว่างไคลเอนต์และเซิร์ฟเวอร์อย่างถูกต้อง "
				+ "การมี <code>ModernFix</code> อาจทำให้ปัญหานี้แสดงออกมา แต่ไม่ใช่สาเหตุโดยตรง</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>วิธีแก้ชั่วคราว:</b> ลบ <code>ModernFix</code> ชั่วคราวเพื่อตรวจสอบว่าเซิร์ฟเวอร์สามารถเริ่มได้หรือไม่ "
				+ "หากเริ่มได้ แสดงว่าปัญหาอยู่ที่ม็อดอื่นที่โหลดคลาสของไคลเอนต์บนเซิร์ฟเวอร์<br>"
				+ "• <b>วิธีแก้ถาวร:</b> ระบุม็อดที่เป็นสาเหตุ (ม็อดที่มีแอนิเมชัน เท็กซ์เจอร์พิเศษ หรือไลบรารีกราฟิก) แล้วอัปเดตหรือลบออก<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "โหลดคลาสของไคลเอนต์บนเซิร์ฟเวอร์ (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ไฟล์การตั้งค่าของม็อดใน <code>Sinytra Connector</code> เสียหาย "
				+ "มักเกิดจากไฟล์มีอักขระว่าง (<code>\\u0000</code>) "
				+ "เนื่องจากการปิดเกมอย่างไม่ถูกต้อง หรือข้อผิดพลาดในการเขียนไฟล์</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• ไปที่โฟลเดอร์ <code>config/</code> ของ Minecraft<br>"
				+ "• ลบไฟล์การตั้งค่าของม็อดที่เกี่ยวข้องกับ connector<br>"
				+ "• รีสตาร์ทเกมเพื่อให้ Sinytra Connector สร้างไฟล์ใหม่";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "การตั้งค่า Sinytra Connector เสียหาย";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "ไฟล์ <code>" + nombreJar
				+ "</code> เสียหายหรือไม่สมบูรณ์<br>" + "ระบบไม่สามารถอ่านได้เนื่องจากส่วนท้ายของไฟล์ ZIP หายไป<br>"
				+ "มักเกิดจากการดาวน์โหลดไม่สมบูรณ์หรือปัญหาของ launcher</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "ไฟล์ JAR เสียหาย (ระบุชื่อไฟล์)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>ลบไฟล์ที่เสียหาย</b> และดาวน์โหลดใหม่จากแหล่งที่เชื่อถือได้ (เช่น CurseForge)<br>"
				+ "• หากใช้ launcher บางตัว ให้ลองเปลี่ยนไปใช้ <b>ATLauncher</b> หรือ <b>Prism Launcher</b><br>"
				+ "• ตรวจสอบว่าอินเทอร์เน็ตมีความเสถียรระหว่างการดาวน์โหลด";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ไม่สามารถโหลดโลกได้ เนื่องจากไฟล์ NBT เสียหาย "
				+ "(เช่น <code>level.dat</code>, <code>playerdata/*.dat</code>)<br>"
				+ "ข้อผิดพลาด: <code>UTFDataFormatException: malformed input around byte " + byteCorrupto
				+ "</code><br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ ควรสำรองข้อมูลโลกก่อนทำการแก้ไข</b><br><br>"
				+ "สามารถใช้ <b>NBT editor</b> เช่น <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a><br>"
				+ "หรือใช้ hex editor (เช่น HxD) หากมีความเชี่ยวชาญ<br>"
				+ "หรือกู้คืนจาก backup หรือใช้ม็อดเช่น <code>FTB Backup</code></b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>สำรองข้อมูลโฟลเดอร์ของโลกทั้งหมด</b> ก่อนพยายามซ่อมแซม<br>"
				+ "• ใช้โปรแกรมแก้ไข NBT (เช่น NBT Studio) เพื่อเปิดและแก้ไขไฟล์ที่เสียหาย<br>"
				+ "• หากไม่สำเร็จ ให้ตรวจสอบไฟล์ด้วย hex editor ที่ตำแหน่ง byte ที่เสีย<br>"
				+ "• หากไม่มีประสบการณ์ ให้กู้คืนจาก backup ล่าสุด";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "โลกเสียหาย: ข้อผิดพลาดในการโหลดข้อมูล NBT";
	}

	@Override
	public String problema_con_openAL() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>พบปัญหากับ OpenAL บางครั้งไดรเวอร์ Nouveau อาจเป็นสาเหตุ "
				+ "หรือเวอร์ชัน OpenAL ที่รวมมากับแอปไม่เข้ากันกับระบบของคุณ "
				+ "โดยเฉพาะในระบบ Red Hat หรือเมื่อใช้ม็อดเสียง เช่น Sound Physics Remastered "
				+ "ดูคู่มือนี้เพื่อแก้ไข: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>วิธีแก้ปัญหาเสียง Minecraft บน Linux</a></span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "เซิร์ฟเวอร์ไม่สามารถเริ่มได้ เนื่องจากไฟล์ของโลกถูกใช้งานโดยโปรเซสอื่น<br>" + "อาจเกิดจาก:<br>"
				+ "• มีเซิร์ฟเวอร์อีก instance กำลังทำงานอยู่<br>"
				+ "• โปรแกรม antivirus หรือ file explorer เปิดโฟลเดอร์โลกอยู่<br>"
				+ "• โปรเซสก่อนหน้าปิดไม่สมบูรณ์และยังล็อกไฟล์อยู่</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>ปิดเซิร์ฟเวอร์ทุก instance</b> (รวมถึง javaw.exe ที่รันอยู่)<br>"
				+ "• หากใช้ panel hosting ให้รีสตาร์ทเซิร์ฟเวอร์จาก panel<br>"
				+ "• <b>ปิด antivirus ชั่วคราว</b> หากสงสัยว่าเป็นสาเหตุ<br>"
				+ "• ปิดหน้าต่าง file explorer ที่เปิดโฟลเดอร์โลกอยู่<br>"
				+ "• หากยังมีปัญหา ให้ลบไฟล์ <code>session.lock</code> (เมื่อมั่นใจว่าไม่มีเซิร์ฟเวอร์รันอยู่)";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "ไฟล์โลกถูกล็อกโดยโปรเซสอื่น";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "ม็อดพยายามสืบทอดคลาส <code>"
				+ clasePadreFinal + "</code> " + "แต่คลาสนี้เป็น <b>final</b> และไม่สามารถสืบทอดได้<br>"
				+ "คลาสที่มีปัญหา: <code>" + claseHija + "</code><br><br>"
				+ "มักเกิดจากม็อดถูกคอมไพล์สำหรับเวอร์ชันเก่าที่โครงสร้างคลาสเปลี่ยนไป</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>อัปเดตม็อดทั้งหมด</b> โดยเฉพาะที่เกี่ยวข้องกับม็อดหลัก<br>"
				+ "• หากยังมีปัญหา ให้ใช้เวอร์ชันที่เข้ากันได้กับ Minecraft ปัจจุบัน<br>"
				+ "• สามารถลบม็อดที่มีปัญหาชั่วคราวเพื่อทดสอบได้";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "พยายามสืบทอดคลาส final";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "คุณกำลังใช้ <b>Rubidium</b> ร่วมกับ <b>Iris หรือ Oculus</b><br>"
				+ "ในเวอร์ชันใหม่ Rubidium ล้าสมัยและอาจมีปัญหา<br><br>"
				+ "อาจเกิดจากความขัดแย้งระหว่างม็อดประสิทธิภาพ เช่น Sodium, Embeddium เป็นต้น</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>ลบ Rubidium</b><br>" + "• ติดตั้ง <b>Embeddium</b> แทน<br>"
				+ "• อย่าใช้ fork ของ Sodium มากกว่าหนึ่งตัวพร้อมกัน<br>" + "• ตรวจสอบความเข้ากันได้ของ Oculus/Iris";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium ล้าสมัยกับ Iris/Oculus";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Simple Voice Chat ไม่สามารถเริ่มได้ เพราะพอร์ต UDP ถูกใช้งานอยู่<br>"
				+ "ฟีเจอร์เสียงจะถูกปิดใช้งาน</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• ปิดโปรแกรมที่ใช้พอร์ต 24454<br>" + "• เปลี่ยนพอร์ตใน config เป็นพอร์ตอื่น<br>"
				+ "• ตรวจสอบ IP configuration";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Voice Chat: พอร์ตถูกใช้งาน";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "BlockItem <code>" + nombreBlockItem
				+ "</code> ไม่มี block<br>" + "มักเกิดใน addon ของ Create จากความขัดแย้งของม็อด</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• อัปเดตม็อด Create และ addon ทั้งหมด<br>" + "• ลบ addon ทีละตัวเพื่อตรวจสอบปัญหา<br>"
				+ "• ตรวจสอบ compatibility ของม็อด";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "BlockItem เป็น null ใน Create";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>")
				.append("พบม็อดที่ไม่เข้ากับ loader ปัจจุบัน:<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>" + "อาจเกิดจากการผสม Fabric กับ Forge หรือใช้เวอร์ชันไม่ตรงกัน</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• ใช้ม็อดสำหรับ platform เดียวกันเท่านั้น<br>" + "• ลบม็อดที่ไม่รู้จักหรือไม่ตรง platform<br>"
				+ "• ตรวจสอบ profile ของ launcher";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "ม็อดไม่เข้ากันกับ loader";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "ไม่สามารถสร้างโมเดล <code>" + modid + ":"
				+ nombreModelo + "</code><br>" + "ทรัพยากรของม็อดเสียหายหรือไม่เข้ากัน</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• อัปเดตม็อด<br>" + "• ตรวจสอบไฟล์ JAR<br>" + "• รายงานข้อผิดพลาดให้ผู้พัฒนา";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "สร้างโมเดลล้มเหลว";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ตรวจพบความขัดแย้งร้ายแรงระหว่างม็อด <code>Moonlight</code> และ <code>Iceberg</code><br>"
				+ "ทั้งสองพยายามจัดการระบบรีโหลดทรัพยากรแบบไม่เข้ากัน "
				+ "ทำให้เกิดข้อผิดพลาด OpenGL เนื่องจากไม่มี context กราฟิกที่ถูกต้อง<br>"
				+ "ปัญหานี้มักเกิดใน Forge ที่มีตัวแปลงม็อดจาก Fabric</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>อัปเดตม็อดทั้งสอง</b> ให้เป็นเวอร์ชันล่าสุดที่รองรับ Forge<br>"
				+ "• หากยังมีปัญหา ให้ <b>ลบ Iceberg ชั่วคราว</b> (Moonlight มักเป็น dependency หลัก)<br>"
				+ "• ตรวจสอบว่าไม่มีเวอร์ชันซ้ำหรือผสม Forge/Fabric<br>"
				+ "• ตรวจสอบว่าม็อดอื่นไม่ได้รวมฟังก์ชันของ Iceberg อยู่แล้ว";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "ความขัดแย้งร้ายแรง: Moonlight กับ Iceberg (OpenGL ไม่มี context)";
	}

	@Override
	public String instantanea() {
		return "สแนปช็อต";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "ตั้งแต่สแนปช็อตล่าสุด";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "เลือกไฟล์";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "สร้างสแนปช็อตเรียบร้อยแล้ว";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "เกิดข้อผิดพลาดขณะสร้างสแนปช็อต";
	}

	@Override
	public String consejo() {
		return "คำแนะนำ";
	}

	@Override
	public String resultadoMuestra() {
		return "ตัวอย่างผลลัพธ์";
	}

	@Override
	public String historaDeModsDesc() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
				+ "<b>คำแนะนำ:</b> เลือกไฟล์ประวัติสองไฟล์เพื่อเปรียบเทียบรายการม็อด "
				+ "ผลลัพธ์จะแสดง <span style='color:%s;'>ที่เพิ่ม (+)</span> และ "
				+ "<span style='color:%s;'>ที่ถูกลบ (&#8722;)</span> ตามชื่อที่ปรับรูปแบบแล้ว "
				+ "ใช้ปุ่ม 'สแนปช็อต' เพื่อสร้างสำเนาของไฟล์ด้วยนามสกุล .instantanea" + "</body></html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		return "รับลิงก์บันทึกเป็น Markdown โดยไม่รวมรายงาน";
	}

	@Override
	public String titulo_configuracion() {
		return "การตั้งค่า";
	}

	@Override
	public String columna_url() {
		return "ลิงก์ URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "เกิดข้อผิดพลาดที่ไม่คาดคิดขณะทำการแชร์";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "เกิดข้อผิดพลาดที่ไม่คาดคิดขณะสร้างลิงก์";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "เกิดข้อผิดพลาดที่ไม่คาดคิดขณะประมวลผลปุ่ม";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "ไม่มีไฟล์สำหรับเปิด";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "ไม่พบไฟล์:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "ไม่สามารถเปิดในตัวแก้ไขได้\nเส้นทางถูกคัดลอกไปยังคลิปบอร์ดแล้ว";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "ไม่สามารถเปิดไฟล์ได้ เส้นทางถูกคัดลอกไปยังคลิปบอร์ดแล้ว";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "ระบบเดสก์ท็อปไม่รองรับ เส้นทางถูกคัดลอกไปยังคลิปบอร์ดแล้ว";
	}

	@Override
	public String limite_de_solicitudes() {
		return "คุณถึงขีดจำกัดการส่งคำขอแล้ว โปรดลองใช้เว็บไซต์บันทึกหรือ API อื่น";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		return "แชร์ลิงก์";
	}

	@Override
	public String infoDeTrazos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ให้ความสำคัญกับส่วนต้นของ stack trace ก่อน "
				+ "รูปแบบคือ ระดับ, บรรทัด " + "บันทึกทั้งหมดมีระบบเลขลำดับ " + Verificaciones.nl_html
				+ "โดยทั่วไปควรตรวจสอบระดับล่างสุดก่อน เพราะระดับสูงมักเป็น false positive "
				+ "ควรใช้การอ่าน console ร่วมด้วย เนื่องจากการวิเคราะห์ stack trace อาจไม่แม่นยำเมื่อมีจำนวนมาก"
				+ "</b>";
	}

	// --- ตัวค้นหา Warrant Canary ---

	@Override
	public String buscador_canario_de_orden_label() {
		return "ตัวค้นหา Warrant Canary";
	}

	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "ฟีเจอร์นี้จะพร้อมใช้งานในเร็ว ๆ นี้";
	}

	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "เร็ว ๆ นี้";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		return "ม็อดไม่เข้ากันกับ Crash Assistant (อาจไม่ถูกต้อง)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		return "Modpack ไม่เข้ากันกับ Crash Assistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant อาจระบุว่าม็อดไม่เข้ากันโดยไม่มีหลักฐาน " + "และข้อความแสดงผลมีเฉพาะภาษาอังกฤษ "
				+ "หากต้องการใช้งานต่อ ให้แก้ไขไฟล์ <code>config/crash_assistant/config.toml</code> "
				+ "และเปลี่ยน <code>enabled = true</code> เป็น <code>enabled = false</code></b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Crash Assistant สามารถระบุว่าม็อดไม่เข้ากันได้ "
				+ "แต่บางครั้งอาจผิดพลาด "
				+ "คุณสามารถแก้ไขไฟล์ <code>config/crash_assistant/problematic_mods_config.json</code> "
				+ "และเปลี่ยน <code>should_crash_on_startup</code> เป็น <code>false</code></b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "ข้อผิดพลาด: ม็อด '" + modId + "' ต้องการ '"
				+ dependencia + "' แต่ปัจจุบัน " + actual + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "ข้อผิดพลาด: ม็อด '" + modId
				+ "' ต้องการเวอร์ชัน '" + requerido + "' หรือสูงกว่าของ '" + dependencia
				+ "' แต่ยังไม่ได้ติดตั้ง</span>";
	}

	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "ข้อผิดพลาด: ม็อด '" + modId
				+ "' ไม่เข้ากันกับ '" + dependencia + "' เวอร์ชันปัจจุบัน " + "ควรลบ 'Iris/Oculus & GeckoLib Compat' "
				+ "เวอร์ชันปัจจุบัน: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "ข้อผิดพลาด: ไม่สามารถรันงานของคลาส '" + clase + "' ได้ " + "มักเกิดจากม็อดไม่เข้ากันหรือมีความขัดแย้ง";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "ข้อผิดพลาดในการรันงาน";
	}

	public String recomendacion_fallos_ejecucion() {
		return "ข้อผิดพลาดนี้มักเกิดจากม็อดไม่เข้ากัน โดยเฉพาะกับ ConnectorMod";
	}

	public String info_clase_problematica() {
		return "คลาสที่มีปัญหา:";
	}

	public String no_se_encontraron_clases_problema() {
		return "ไม่พบคลาสที่มีปัญหา";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "พบความขัดแย้งร้ายแรงระหว่าง OptiFine และ Entity Model Features (EMF) "
				+ "ไม่สามารถใช้งานร่วมกันได้</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "ความขัดแย้ง OptiFine กับ EMF";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "ลบ OptiFine หรือ EMF เนื่องจากไม่สามารถใช้ร่วมกันได้";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "พบความขัดแย้งร้ายแรงระหว่าง OptiFine และ Fusion " + "ไม่สามารถใช้งานร่วมกันได้</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "ความขัดแย้ง OptiFine กับ Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "ลบ OptiFine หรือ Fusion";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel ต้องการ Sodium เวอร์ชัน 0.6.0-beta.2 ขึ้นไป " + "แนะนำใช้ Embeddium แทน</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "ความขัดแย้ง Flywheel กับเวอร์ชัน Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "อัปเดต Sodium หรือใช้ Embeddium แทน";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "พบความขัดแย้งร้ายแรงระหว่าง OptiFine และ Epic Fight " + "ไม่สามารถใช้งานร่วมกันได้</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "ความขัดแย้ง OptiFine กับ Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "ลบ OptiFine หรือ Epic Fight เนื่องจากไม่สามารถใช้งานร่วมกันได้";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบความขัดแย้งร้ายแรงระหว่าง OptiFine และ Rubidium "
				+ "ม็อดเหล่านี้ไม่สามารถใช้งานร่วมกันได้ และทำให้เกิดข้อผิดพลาดในการฉีดโค้ดจนไม่สามารถเปิดเกมได้"
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "ความขัดแย้ง OptiFine กับ Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "ลบ OptiFine หรือ Rubidium เนื่องจากไม่สามารถใช้งานร่วมกันได้";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam กำลังพยายามโหลดบนเซิร์ฟเวอร์แบบ dedicated แต่รองรับเฉพาะฝั่งไคลเอนต์เท่านั้น "
				+ "ให้ลบ FreeCam ออกจากเซิร์ฟเวอร์ หรือให้แน่ใจว่าติดตั้งเฉพาะฝั่งไคลเอนต์" + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam บนเซิร์ฟเวอร์แบบ dedicated";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "ลบ FreeCam ออกจากเซิร์ฟเวอร์แบบ dedicated เนื่องจากควรติดตั้งเฉพาะฝั่งไคลเอนต์";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) กำลังพยายามโหลดบนเซิร์ฟเวอร์แบบ dedicated แต่รองรับเฉพาะฝั่งไคลเอนต์เท่านั้น "
				+ "ให้ลบ ETF ออกจากเซิร์ฟเวอร์ หรือให้แน่ใจว่าติดตั้งเฉพาะฝั่งไคลเอนต์" + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features บนเซิร์ฟเวอร์แบบ dedicated";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "ลบ Entity Texture Features ออกจากเซิร์ฟเวอร์แบบ dedicated เนื่องจากควรติดตั้งเฉพาะฝั่งไคลเอนต์";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "คุณต้องยอมรับ EULA ของ Minecraft เพื่อรันเซิร์ฟเวอร์ "
				+ "แก้ไขไฟล์ eula.txt และเปลี่ยน 'eula=false' เป็น 'eula=true'" + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "ยังไม่ได้ยอมรับ EULA ของ Minecraft";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "แก้ไขไฟล์ eula.txt ในโฟลเดอร์เซิร์ฟเวอร์ และเปลี่ยน 'eula=false' เป็น 'eula=true'";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine กำลังพยายามโหลดบนเซิร์ฟเวอร์แบบ dedicated แต่รองรับเฉพาะฝั่งไคลเอนต์เท่านั้น "
				+ "ให้ลบ OptiFine ออกจากเซิร์ฟเวอร์ หรือให้แน่ใจว่าติดตั้งเฉพาะฝั่งไคลเอนต์" + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine บนเซิร์ฟเวอร์แบบ dedicated";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "ลบ OptiFine ออกจากเซิร์ฟเวอร์แบบ dedicated เนื่องจากควรติดตั้งเฉพาะฝั่งไคลเอนต์ "
				+ "ปัญหานี้อาจเกิดจากการใช้ OptiFine เวอร์ชันที่ไม่ตรงกับ Minecraft หรือมีความขัดแย้งกับม็อดอื่น";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks ถูกระบุเวอร์ชันไม่ถูกต้องสำหรับ 1.20.1 แต่ใช้เมธอดของ 1.21.1 "
				+ "ม็อดพยายามใช้ ResourceLocation.fromNamespaceAndPath ซึ่งไม่มีใน 1.20.1" + "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "ข้อผิดพลาดเวอร์ชันใน Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "ตรวจสอบให้แน่ใจว่าใช้เวอร์ชันของ Iron's Spellbooks ที่ตรงกับเวอร์ชัน Minecraft";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบความขัดแย้งร้ายแรงระหว่าง OptiFine และ Embeddium "
				+ "ม็อดเหล่านี้ไม่สามารถใช้งานร่วมกันได้ และทำให้เกิดข้อผิดพลาดจนไม่สามารถเปิดเกมได้" + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "ความขัดแย้ง OptiFine กับ Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "ลบ OptiFine หรือ Embeddium เนื่องจากไม่สามารถใช้งานร่วมกันได้";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>มักเกิดกับม็อดสร้างโลกที่ขัดแย้งกัน เช่น Terralinth, AmplifiedNether, Nullscape, Incendium "
				+ "หรือม็อดสร้างโลกอื่น ๆ และอาจเกิดจากม็อดที่ขาดหาย</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable กำลังพยายามโหลดบนเซิร์ฟเวอร์แบบ dedicated แต่รองรับเฉพาะฝั่งไคลเอนต์เท่านั้น "
				+ "ให้ลบ Controllable ออกจากเซิร์ฟเวอร์ หรือให้แน่ใจว่าติดตั้งเฉพาะฝั่งไคลเอนต์" + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable บนเซิร์ฟเวอร์แบบ dedicated";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "ลบ Controllable ออกจากเซิร์ฟเวอร์แบบ dedicated เนื่องจากควรติดตั้งเฉพาะฝั่งไคลเอนต์";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries ทำให้เกิดข้อผิดพลาดที่ขัดขวางการโหลดของเซิร์ฟเวอร์ "
				+ "ม็อดมีปัญหากับการลงทะเบียนพฤติกรรมไฟ ซึ่งทำให้เกิดข้อผิดพลาดระหว่างการโหลด datapack" + "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries ขัดขวางการโหลดเซิร์ฟเวอร์";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "ลองอัปเดต Supplementaries เป็นเวอร์ชันล่าสุด หรือปิดใช้งานชั่วคราวเพื่อให้เซิร์ฟเวอร์โหลดได้";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) พบปัญหากับโมดูล Jackson ที่ขาดหาย "
				+ "ม็อดบางตัว เช่น Valkyrien Skies อาจทำให้เกิดข้อผิดพลาดนี้จากการไม่มี dependency ที่จำเป็นครบถ้วน"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "โมดูล Jackson ขาดหายใน Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "ลบ Groovy Modloader และม็อดที่เกี่ยวข้อง เช่น Valkyrien Skies ซึ่งอาจทำให้เกิดความขัดแย้งของ dependency";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Every Compat พบชื่อบล็อกไม้ที่ไม่ถูกต้อง "
				+ "Every Compat มักมีปัญหาหลายอย่าง ไม่แนะนำให้ใช้งาน" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "ชื่อไม่ถูกต้องใน Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "ตรวจสอบ resource pack หรือม็อดที่ใช้ Every Compat เนื่องจากอาจมีชื่อบล็อกไม่ถูกต้อง";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบรหัสข้อผิดพลาด (-1073741819) ซึ่งอาจเกิดจาก overlay เช่น GameCaster ของ Razer, Discord, OBS Studio หรือปัญหากับไดรเวอร์ NVIDIA"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "รหัสข้อผิดพลาด -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "ลองปิด overlay เช่น GameCaster, Discord หรือ OBS Studio และตรวจสอบว่าไดรเวอร์ NVIDIA เป็นเวอร์ชันล่าสุด";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips ต้องการ Immersive Messages เป็น dependency แต่ยังไม่ได้ติดตั้ง "
				+ "ให้ติดตั้ง Immersive Messages เพื่อให้ Immersive Tooltips ทำงานได้อย่างถูกต้อง" + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips ไม่มี dependency";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "ติดตั้ง Immersive Messages ซึ่งเป็น dependency ที่จำเป็นสำหรับ Immersive Tooltips";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins มีปัญหาความเข้ากันได้กับ Apoli Mod โดยที่ ItemStack ไม่สามารถแปลงเป็น EntityLinkedItemStack ได้ "
				+ "พบได้บ่อยในเวอร์ชันที่สูงกว่า 6.6.0 แนะนำให้ใช้เวอร์ชันก่อนหน้า หรือเปลี่ยนระหว่าง Fabric และ Forge"
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "ข้อผิดพลาดการแปลงชนิดใน Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "ใช้ Medieval Origins เวอร์ชัน 6.6.0 หรือต่ำกว่า หรือทดลองเปลี่ยนเวอร์ชัน Fabric/Forge ของม็อด";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether ทำให้เกิดข้อผิดพลาดจาก Registry Object ที่ไม่มีใน MusicManager "
				+ "ปัญหานี้เกี่ยวข้องกับ mixin ของ MusicManager" + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "ข้อผิดพลาด MusicManager ใน Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "ลองอัปเดต Reign of Nether หรือปิดใช้งานชั่วคราวเพื่อแก้ไขปัญหา";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel รองรับเฉพาะเซิร์ฟเวอร์ YSM บน Linux หรือ Android "
				+ "ปัญหานี้ได้รับการแก้ไขแล้วในเวอร์ชันใหม่หลังวันที่ 23 พฤศจิกายน 2025 บน Modrinth" + "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel ไม่รองรับ Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "อัปเดต YesSteveModel เป็นเวอร์ชันล่าสุดจาก Modrinth";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบความขัดแย้งร้ายแรงระหว่าง Moving Elevators และ OptiFine " + "ไม่สามารถใช้งานร่วมกันได้"
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "ความขัดแย้ง Moving Elevators กับ OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "ลบ OptiFine หรือ Moving Elevators เนื่องจากไม่สามารถใช้งานร่วมกันได้";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบความขัดแย้งร้ายแรงระหว่าง Fabric API และ OptiFine " + "ไม่สามารถใช้งานร่วมกันได้" + "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "ความขัดแย้ง Fabric API กับ OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "ลบ OptiFine หรืออัปเดต Fabric API ให้เป็นเวอร์ชันที่เข้ากันได้";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ม็อดมี ITransformationService ที่ไม่สามารถสร้างอินสแตนซ์ได้: " + claseProveedor
				+ " ต้องลบม็อดนี้เพื่อให้เกมโหลดได้" + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "ITransformationService ผิดพลาด";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "ลบม็อดที่มีคลาส " + claseProveedor + " เนื่องจากมี ITransformationService ที่ผิดพลาด";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>ม็อดมีการกำหนดเวอร์ชันไม่ถูกต้อง ต้องใช้วงเล็บ [] "
				+ "ใช้เครื่องมือ grep/greprf เพื่อค้นหาเวอร์ชัน </span>" + version + "<span style='color:#"
				+ config.obtenerColorError() + "'> เพื่อระบุว่าม็อดใดมีปัญหา</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "เวอร์ชันม็อดไม่ถูกต้อง";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "ใช้เครื่องมือ grep/greprf เพื่อค้นหาเวอร์ชันที่มีปัญหาและระบุม็อดที่เกี่ยวข้อง";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาด stack smashing ซึ่งทำให้โปรแกรมหยุดทำงาน "
				+ "อาจเกิดจาก Early Window หรือ LWJGL เวอร์ชันใหม่" + "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "ตรวจพบ Stack Smashing";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "ตรวจสอบการตั้งค่า Early Window และลองใช้ LWJGL เวอร์ชันอื่น";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore ใช้ได้เฉพาะ modpack เฉพาะเท่านั้น และไม่ควรใช้ทั่วไป" + "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore ไม่เข้ากัน";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "ลบ GregTechEasyCore เนื่องจากไม่เหมาะกับการติดตั้งทั่วไป";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "พบความขัดแย้งระหว่าง MoniLabs และ Connector Extras" + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "ความขัดแย้ง MoniLabs กับ Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "ลบหนึ่งในม็อด (MoniLabs หรือ Connector Extras)";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris ต้องการ Distant Horizons เวอร์ชันใหม่กว่า" + "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Iris ไม่เข้ากันกับ Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "อัปเดต Iris และ Distant Horizons ให้เข้ากันได้";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError() + "'>มีคลาสของ Minecraft ขาดหาย:</b>" + "<ul>"
				+ "<li>ม็อดไม่ตรงเวอร์ชัน</li>" + "<li>การติดตั้งเสียหาย</li>" + "<li>coremod มีปัญหา</li>" + "</ul>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>มีคลาสของ DangerZone ขาดหาย สาเหตุที่เป็นไปได้:</b>" + "<ul>"
				+ "<li>ม็อดไม่ตรงกับเวอร์ชันของเกม</li>" + "<li>มี coremod ที่มีปัญหา</li>"
				+ "<li>ตัว launcher หรือการติดตั้งมีปัญหา</li>" + "</ul>"
				+ "<p>หมายเหตุ: สามารถใช้เครื่องมือ <b>grepr/fgrepr</b> ในแถบด้านข้างเพื่อค้นหาม็อดที่อ้างอิงถึงคลาสที่ขาดหาย โดยใช้ '/' ในชื่อ</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>มีคลาสของ FeatureCreep ขาดหาย สาเหตุที่เป็นไปได้:</b>" + "<ul>"
				+ "<li>ใช้เวอร์ชันของ FeatureCreep ไม่ตรงกัน (เช่น ESR กับ Nightly หรือ v4 กับ v12)</li>"
				+ "<li>สามารถติดตั้ง FeatureCreep จาก CurseForge หรือ MinecraftStorage</li>" + "</ul>"
				+ "<p>หมายเหตุ: สามารถใช้เครื่องมือ <b>grepr/fgrepr</b> เพื่อค้นหาม็อดที่เกี่ยวข้องกับคลาสที่ขาดหาย</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>มีคลาสของ ModLauncher ขาดหาย สาเหตุที่เป็นไปได้:</b>" + "<ul>"
				+ "<li>ม็อดไม่ตรงกับเวอร์ชันของ Forge / NeoForge / PillowMC</li>"
				+ "<li>มีการอัปเดต mod loader หลายครั้งในเวอร์ชันเดียวกัน</li>" + "<li>การติดตั้ง launcher มีปัญหา</li>"
				+ "</ul>" + "<p>หมายเหตุ: ใช้ <b>grepr/fgrepr</b> เพื่อค้นหาม็อดที่อ้างอิงคลาสที่ขาดหาย</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>มีคลาสของ Minecraft Forge ขาดหาย สาเหตุที่เป็นไปได้:</b>" + "<ul>"
				+ "<li>ม็อดไม่ตรงกับเวอร์ชันของ Forge</li>" + "<li>มีการอัปเดต mod loader หลายครั้ง</li>"
				+ "<li>การติดตั้งมีปัญหา</li>" + "</ul>"
				+ "<p>หมายเหตุ: ใช้ <b>grepr/fgrepr</b> เพื่อค้นหาม็อดที่เกี่ยวข้อง</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError() + "'>มีคลาสของ NeoForge ขาดหาย สาเหตุที่เป็นไปได้:</b>"
				+ "<ul>" + "<li>ม็อดไม่ตรงกับเวอร์ชันของ NeoForge</li>" + "<li>มีการอัปเดต mod loader หลายครั้ง</li>"
				+ "<li>การติดตั้งมีปัญหา</li>" + "</ul>"
				+ "<p>หมายเหตุ: ใช้ <b>grepr/fgrepr</b> เพื่อค้นหาม็อดที่เกี่ยวข้อง</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>มีคลาสของ Fabric Loader ขาดหาย สาเหตุที่เป็นไปได้:</b>" + "<ul>"
				+ "<li>ม็อดไม่ตรงกับเวอร์ชันของ Fabric Loader</li>" + "<li>มีการอัปเดต mod loader หลายครั้ง</li>"
				+ "<li>การติดตั้งมีปัญหา</li>" + "<li>บางม็อดต้องใช้ Fabric API ซึ่งอาจยังไม่ได้ติดตั้ง</li>" + "</ul>"
				+ "<p>หมายเหตุ: ใช้ <b>grepr/fgrepr</b> เพื่อค้นหาม็อดที่เกี่ยวข้อง</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError() + "'>มีคลาสของ PillowMC ขาดหาย สาเหตุที่เป็นไปได้:</b>"
				+ "<ul>" + "<li>ม็อดไม่ตรงกับเวอร์ชันของ PillowMC</li>" + "<li>มีการอัปเดต mod loader หลายครั้ง</li>"
				+ "<li>การติดตั้งมีปัญหา</li>" + "</ul>"
				+ "<p>หมายเหตุ: ใช้ <b>grepr/fgrepr</b> เพื่อค้นหาม็อดที่เกี่ยวข้อง</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "คุณมีม็อดที่ตั้งใจทำให้เกิดอาการหน่วง (lag) Uranium เป็นม็อดที่สร้าง lag "
				+ "แม้อาจไม่ทำให้เกิดข้อผิดพลาดทันที แต่ในที่สุดอาจทำให้เกิดปัญหาได้" + "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack ถูกระบุว่าใช้ได้กับ 1.19.* แต่จริง ๆ แล้วออกแบบมาสำหรับ 1.20.* "
				+ "ซึ่งทำให้เกิดข้อผิดพลาดคลาสไม่พบ "
				+ "ม็อดพยายามใช้ DamageSources ที่ไม่มีในเวอร์ชัน Minecraft ปัจจุบัน" + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "ข้อผิดพลาดเวอร์ชันใน Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "ตรวจสอบให้แน่ใจว่าใช้เวอร์ชันของ Falling Attack ที่ตรงกับเวอร์ชัน Minecraft";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>").append("คุณต้องติดตั้ง CFR (Class File Reader) เพื่อใช้ฟีเจอร์นี้<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append("บนระบบ Linux, NetBSD หรือ FreeBSD คุณสามารถติดตั้ง CFR ผ่านตัวจัดการแพ็กเกจได้<br>").append(
					"ค้นหาแพ็กเกจได้ที่: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append("หรือคุณสามารถดาวน์โหลดเวอร์ชันที่ดัดแปลงโดย FabricMC ได้จาก:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("บันทึกไฟล์ไว้ในโฟลเดอร์ต่อไปนี้:<br>").append("<b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>สำคัญ:</b> หลังจากติดตั้ง CFR แล้ว ต้องรีสตาร์ทม็อดเพื่อให้ระบบตรวจพบอย่างถูกต้อง")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "ไม่มีภาพตัวอย่าง";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "ไม่พบคลาส: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "ตัวถอดรหัส CFR – Sakura Riddle (ไม่เป็นทางการ)";
	}

	@Override
	public String cfrClaseActual() {
		return "คลาสปัจจุบัน";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "ภาพตัวอย่าง Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "เกิดข้อผิดพลาดขณะโหลดภาพตัวอย่าง";
	}

	public String noticiaLegalCFR() {
		return "โปรแกรม GUI สำหรับถอดรหัสม็อดนี้ถูกออกแบบมาเพื่อช่วยผู้ใช้ระบุสาเหตุของข้อผิดพลาดในซอฟต์แวร์ "
				+ "อย่างไรก็ตาม การถอดรหัสม็อดอาจมีข้อจำกัดทางกฎหมาย ผู้ใช้ควรระมัดระวังไม่ใช้โค้ดที่ได้ไปละเมิดลิขสิทธิ์ "
				+ "แนะนำให้ตรวจสอบใบอนุญาตของม็อดก่อนนำโค้ดไปใช้งาน "
				+ "ม็อดจำนวนมากมีซอร์สโค้ดอย่างเป็นทางการ ซึ่งมักอ่านง่ายกว่าที่ถอดรหัส "
				+ "การเคารพลิขสิทธิ์และใบอนุญาตเป็นสิ่งสำคัญต่อชุมชนผู้พัฒนาม็อด "
				+ "สามารถศึกษากฎหมายลิขสิทธิ์ได้ที่: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">กฎหมายลิขสิทธิ์ (สเปน)</a> "
				+ "และเวอร์ชันภาษาอังกฤษ: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (English)</a>. "
				+ "รวมถึงกฎหมายลิขสิทธิ์สหรัฐอเมริกา: "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>. "
				+ "ควรศึกษากฎหมายที่ใช้ในพื้นที่ของคุณด้วย "
				+ "GUI นี้เหมาะสำหรับการตรวจสอบพื้นฐาน หากต้องการวิเคราะห์ขั้นสูง แนะนำให้ใช้ Enigma (FabricMC) ที่ "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a> "
				+ "และสามารถใช้ Recaf สำหรับแก้ไขไฟล์ JAR ได้ที่ "
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">เว็บไซต์ Recaf</a>.";
	}

	@Override
	public String botonDescargarCfr() {
		return "ดาวน์โหลด CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "เปิดโฟลเดอร์ติดตั้ง";
	}

	@Override
	public String colorFondoPrincipal() {
		return "สีพื้นหลังหลัก";
	}

	@Override
	public String colorTextoBotonReset() {
		return "สีข้อความของปุ่มรีเซ็ต";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "สีข้อความในช่องค้นหา";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "สีข้อความในเมนูตัวกรอง";
	}

	@Override
	public String colorTextoRenderer() {
		return "สีข้อความของตัวแสดงผล";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "สีข้อความของ overlay การโหลด";
	}

	@Override
	public String colorBorde() {
		return "สีเส้นขอบ";
	}

	@Override
	public String colorFondoRetrato() {
		return "สีพื้นหลังโหมดภาพตัวอย่าง";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "สีลิงก์สำหรับแชร์";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "สีพื้นหลังของช่องแชร์";
	}

	@Override
	public String rosaFondo() {
		return "สีชมพูพื้นหลัง";
	}

	@Override
	public String rosaSuave() {
		return "สีชมพูอ่อน";
	}

	@Override
	public String moradoAcento() {
		return "สีม่วงเน้น";
	}

	@Override
	public String textoOscuro() {
		return "ข้อความสีเข้ม";
	}

	@Override
	public String bordeSuave() {
		return "เส้นขอบแบบนุ่ม";
	}

	@Override
	public String fondoCampo() {
		return "พื้นหลังของช่อง";
	}

	@Override
	public String fondoVistaPrevia() {
		return "พื้นหลังของหน้าตัวอย่าง";
	}

	@Override
	public String sintaxisConstructor() {
		return "สีไวยากรณ์: constructor";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "สีไวยากรณ์: ข้อความช่วยเหลือ";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "สีไวยากรณ์: แท็ก HTML";
	}

	@Override
	public String colorFondoVentana() {
		return "สีพื้นหลังหน้าต่าง";
	}

	@Override
	public String colorPanel() {
		return "สีของแผง";
	}

	@Override
	public String colorBotonTexto() {
		return "สีข้อความของปุ่ม";
	}

	@Override
	public String colorCampo() {
		return "สีของช่อง";
	}

	@Override
	public String colorBordeDestacado() {
		return "สีเส้นขอบที่เน้น";
	}

	@Override
	public String colorSeleccionTexto() {
		return "สีพื้นหลังของการเลือกข้อความ";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "สีข้อความที่ถูกเลือก";
	}

	@Override
	public String colorEstadoExito() {
		return "สีสถานะ: สำเร็จ";
	}

	@Override
	public String colorEstadoFallo() {
		return "สีสถานะ: ล้มเหลว";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "สีสถานะ: สแนปช็อต";
	}

	@Override
	public String colorResultadoAnadido() {
		return "สีของผลลัพธ์ที่เพิ่ม";
	}

	@Override
	public String colorResultadoEliminado() {
		return "สีของผลลัพธ์ที่ถูกลบ";
	}

	@Override
	public String colorBordeScroll() {
		return "สีขอบของแถบเลื่อน";
	}

	@Override
	public String colorFondoPanel() {
		return "สีพื้นหลังของแผง";
	}

	@Override
	public String colorBeigeListas() {
		return "สีเบจของรายการ";
	}

	@Override
	public String colorTextoListas() {
		return "สีข้อความในรายการ";
	}

	@Override
	public String colorBordeListas() {
		return "สีขอบของรายการ";
	}

	@Override
	public String colorBotonFondo() {
		return "สีพื้นหลังของปุ่ม";
	}

	@Override
	public String colorBordeBoton() {
		return "สีขอบของปุ่ม";
	}

	@Override
	public String colorDoradoTexto() {
		return "สีทองของข้อความ";
	}

	@Override
	public String colorPila() {
		return "สีของสแตก (stack trace)";
	}

	@Override
	public String colorTextoPanel() {
		return "สีข้อความของแผง";
	}

	@Override
	public String colorTextoNegro() {
		return "สีข้อความสีดำ";
	}

	@Override
	public String colorTextoPrincipal() {
		return "สีข้อความหลัก";
	}

	@Override
	public String colorFondoResultados() {
		return "สีพื้นหลังของผลลัพธ์";
	}

	@Override
	public String colorEstado() {
		return "สีสถานะ";
	}

	@Override
	public String colorTextoDescripcion() {
		return "สีข้อความคำอธิบาย";
	}

	@Override
	public String colorTextoEstado() {
		return "สีข้อความสถานะ";
	}

	@Override
	public String colorTextoExtra() {
		return "สีข้อความเพิ่มเติม";
	}

	@Override
	public String colorSeparador() {
		return "สีตัวแบ่ง";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "ตรวจพบข้อผิดพลาดระดับระบบ <code>StubRoutines::SafeFetch32</code> "
				+ "ปัญหานี้เกิดขึ้นบน macOS กับ JDK เวอร์ชัน 17.0.9 และได้รับการแก้ไขแล้วในเวอร์ชัน 17.0.10 หรือใหม่กว่า "
				+ "https://github.com/async-profiler/async-profiler/issues/747 "
				+ "https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "ข้อผิดพลาด SafeFetch32 ใน JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "อัปเดต JDK ของคุณเป็นเวอร์ชัน 17.0.10 หรือใหม่กว่า (เช่น 17.0.15)";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "หากคุณใช้ตัวเปิดเกม เช่น MultiMC, Prism Launcher หรือ TLauncher ให้ตั้งค่าให้ใช้ JDK เวอร์ชันใหม่กว่า "
				+ "บางตัวมี JDK 17.0.15 รวมมาให้แล้ว";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "ม็อด Spark อาจเป็นสาเหตุร่วมของข้อผิดพลาดนี้ ลองปิดใช้งานชั่วคราว "
				+ "https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ม็อด MCEF (Chromium Embedded Framework) กำลังทำให้เกมค้างโดยไม่แสดงข้อผิดพลาด</b>" + "<ul>"
				+ "<li>MCEF ถูกเริ่มต้นในช่วงท้ายของบันทึก ซึ่งมักหมายถึงเกมค้างระหว่างการโหลด</li>"
				+ "<li>ม็อดนี้มักมีปัญหาบน Linux, macOS หรือกับ Java บางเวอร์ชัน</li>"
				+ "<li>อาจไม่มีข้อความข้อผิดพลาดชัดเจน แต่เกมจะไม่เข้าสู่เมนูหลัก</li>" + "</ul>"
				+ "<p>หากไม่จำเป็นต้องใช้ฟีเจอร์เบราว์เซอร์ในเกม (เช่น แผนที่เว็บ) แนะนำให้ลบม็อดนี้</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "ปัญหาการเริ่มต้น MCEF (ม็อดเบราว์เซอร์ในตัว)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "ลบไฟล์ม็อด MCEF (ค้นหาคำว่า 'mcef' ในชื่อไฟล์) ออกจากโฟลเดอร์ 'mods'";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "หากจำเป็นต้องใช้ ตรวจสอบให้แน่ใจว่าใช้เวอร์ชันที่รองรับกับระบบปฏิบัติการและเวอร์ชัน Minecraft ของคุณ";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบความขัดแย้งระหว่าง <b>OptiFine</b> และ <b>Iris/Oculus</b></b>" + "<ul>"
				+ "<li>OptiFine ปรับเปลี่ยนระบบเรนเดอร์ของ Minecraft ซึ่งไม่เข้ากันกับ Iris หรือ Oculus</li>"
				+ "<li>ข้อผิดพลาด <code>MixinLevelRenderer failed injection check</code> มาจาก <code>mixins.iris.json</code> หรือ <code>mixins.oculus.json</code></li>"
				+ "</ul>"
				+ "<p>ม็อดเหล่านี้ไม่สามารถใช้ร่วมกันได้ ให้ลบ OptiFine หากต้องการใช้ shaders กับ Iris หรือ Oculus</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "ความขัดแย้งระหว่าง OptiFine และ Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "ลบไฟล์ OptiFine ออกจากโฟลเดอร์ 'mods'";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "ใช้ Iris หรือ Oculus โดยไม่ใช้ OptiFine สำหรับ shaders";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบความขัดแย้งระหว่าง <b>ModernFix</b> และ <b>OptiFine</b></b>" + "<ul>"
				+ "<li>ModernFix ไม่รองรับ OptiFine และอาจทำให้ Forge ทำงานผิดปกติหรือช้าลง</li>"
				+ "<li>ModernFix ระบุชัดเจนว่า: <i>\"Use of ModernFix with OptiFine is not supported\"</i></li>"
				+ "</ul>" + "<p>คุณต้องลบหนึ่งในสองม็อดนี้เพื่อให้เกมทำงานได้</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "ความขัดแย้งระหว่าง ModernFix และ OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "ลบ OptiFine หรือ ModernFix ออกจากโฟลเดอร์ 'mods' ไม่สามารถใช้ร่วมกันได้";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "หากต้องการเพิ่มประสิทธิภาพ ให้ใช้ OptiFine เพียงอย่างเดียว หรือใช้ม็อดอื่นแทน เช่น FerriteCore หรือ EntityCulling";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ข้อผิดพลาด: คีย์รีจิสทรีไม่ถูกต้อง (มีอักขระที่ไม่อนุญาต)</b>" + "<ul>"
				+ "<li><b>คีย์ที่ตรวจพบ:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>ใน Minecraft คีย์รีจิสทรีทั้งหมด (แท็ก สูตร ความสำเร็จ ฯลฯ) ต้องเป็น <b>ตัวพิมพ์เล็ก</b> และใช้เฉพาะตัวอักษร ตัวเลข ขีดล่าง ขีดกลาง และเครื่องหมายทับ</li>"
				+ "<li>ข้อผิดพลาดนี้มักเกิดจากม็อดหรือ datapack ที่มีปัญหา</li>" + "</ul>"
				+ "<p><b>คำแนะนำ:</b> ใช้เครื่องมือ <b>grepr</b> หรือ <b>fgrepr</b> และเปิดตัวเลือก <b>\"ค้นหาในไฟล์ JAR\"</b> เพื่อค้นหาม็อดที่เป็นสาเหตุ</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "คีย์รีจิสทรีมีตัวพิมพ์ใหญ่หรืออักขระไม่ถูกต้อง";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "ใช้ 'grepr' หรือ 'fgrepr' พร้อม \"ค้นหาในไฟล์ JAR\" เพื่อหาม็อดที่เป็นสาเหตุ";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "หากไม่สามารถระบุได้ ให้ลบม็อดที่เพิ่งเพิ่ม โดยเฉพาะม็อดที่เพิ่มบล็อก ไอเท็ม หรือเครื่องมือ";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "เกิดข้อผิดพลาดขณะโหลดม็อด <b>"
				+ escapeHtml(modNombre) + "</b></b>" + "<ul>"
				+ "<li>ม็อดไม่สามารถเริ่มต้นส่วนประกอบบางอย่างได้ (เช่น เมนูตั้งค่า)</li>"
				+ "<li>มักเกิดจากความไม่เข้ากันกับเวอร์ชัน Minecraft, Fabric หรือม็อดอื่น</li>" + "</ul>"
				+ "<p>หากปัญหายังคงอยู่ ให้ลบหรืออัปเดตม็อด <b>" + escapeHtml(modNombre) + "</b></p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "ข้อผิดพลาดการเริ่มต้นม็อด (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "ลบม็อด '" + modNombre + "' ออกจากโฟลเดอร์ 'mods'";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "อัปเดตม็อด '" + modNombre + "' ให้เป็นเวอร์ชันที่รองรับ";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดที่เกี่ยวข้องกับม็อด <b>En Garde!</b></b>" + "<ul>"
				+ "<li>ม็อดนี้เพิ่มระบบการต่อสู้ระยะประชิด เช่น parry และ block</li>"
				+ "<li>ข้อผิดพลาดมักเกิดจากความไม่เข้ากันกับม็อดต่อสู้อื่น หรือใช้เวอร์ชันไม่ตรงกับ Minecraft</li>"
				+ "</ul>" + "<p>หากไม่จำเป็น แนะนำให้ลบ En Garde! เพื่อลดปัญหา</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "ข้อผิดพลาดในม็อด En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "ตรวจสอบให้แน่ใจว่าใช้เวอร์ชันของ En Garde! ที่รองรับกับ Minecraft และตัวโหลด (Fabric/Forge)";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "หากใช้ม็อดต่อสู้อื่น ให้ปิดใช้งานหรือเลือกใช้เพียงตัวเดียว";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ตรวจพบข้อผิดพลาดจากม็อด <b>IdleTweaks</b></b>"
				+ "<ul>"
				+ "<li>IdleTweaks พยายามปล่อยช่องเครือข่ายที่ไม่มีอยู่ (<code>Tried to release unknown channel</code>)</li>"
				+ "<li>มักเกิดในเวอร์ชันเก่าหรือเซิร์ฟเวอร์ที่ตั้งค่าไม่ถูกต้อง</li>" + "</ul>"
				+ "<p>ม็อดนี้อาจทำให้ระบบไม่เสถียร แนะนำให้อัปเดตหรือลบหากไม่จำเป็น</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "ข้อผิดพลาด IdleTweaks (ช่องเครือข่ายไม่รู้จัก)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "อัปเดต IdleTweaks เป็นเวอร์ชันล่าสุดที่รองรับกับ Minecraft";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "ลบ IdleTweaks ออกจากโฟลเดอร์ 'mods' หากไม่จำเป็น";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดการยืนยันตัวตน (HTTP 401) ขณะเข้าสู่ระบบ Minecraft</b>"
				+ "<p>ข้อผิดพลาดนี้มักไม่ใช่สาเหตุโดยตรงของการล่ม แต่บ่งชี้ว่าคุณกำลังใช้บัญชีที่ไม่ได้รับการยืนยัน</p>"
				+ "<p>ช่องทางช่วยเหลืออย่างเป็นทางการ (โปรเจกต์ องค์กร VTuber ผู้สร้างม็อดแพ็ก ฯลฯ) "
				+ "<b>ไม่สามารถให้ความช่วยเหลือได้</b> หากใช้เวอร์ชันที่ไม่ได้รับอนุญาต "
				+ "เนื่องจากข้อกำหนด นโยบาย หรือข้อตกลงกับ Mojang/Microsoft</p>"
				+ "<p>สามารถปิดการตรวจสอบนี้ได้ในการตั้งค่าของระบบ "
				+ "คำเตือน: ระบบตรวจจับอาจไม่แม่นยำ และอาจทำงานในสภาพแวดล้อมพัฒนา อินเทอร์เน็ตไม่เสถียร หรือ launcher ที่ถูกปรับแต่ง</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>สิทธิ์พื้นฐานหากคุณยังต้องการขอความช่วยเหลือ:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Minecraft ที่ไม่ได้รับอนุญาต";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "ปิดการตรวจสอบการละเมิดลิขสิทธิ์";
	}

	@Override
	public String comprarMC() {
		return "ซื้อ Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "คุณกำลังใช้ launcher <code>" + id
				+ "</code> ซึ่ง <b>ไม่อยู่ในรายการที่แนะนำ</b></b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>แม้อาจใช้งานได้ แต่ launcher ที่ไม่แนะนำมักก่อให้เกิดปัญหา:</p>" + "<ul>"
				+ "<li>การติดตั้งม็อดหรือแอปเสียหาย</li>" + "<li>เกมไม่เปิดหรือค้างโดยไม่มีข้อผิดพลาดชัดเจน</li>"
				+ "<li>โครงสร้างโฟลเดอร์ผิดปกติ ทำให้วิเคราะห์ปัญหาได้ยาก</li>"
				+ "<li>พฤติกรรมไม่แน่นอนเกี่ยวกับ Java หน่วยความจำ หรือม็อด</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "เพื่อประสบการณ์ที่ดีกว่า แนะนำให้ใช้ launcher ต่อไปนี้:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Launcher ไม่แนะนำ";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "เปลี่ยนไปใช้ launcher ที่แนะนำ";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "คุณกำลังใช้ <b>launcher ที่ไม่แนะนำ</b>: <code>" + id + "</code></b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>launcher ที่ไม่แนะนำอาจทำให้เกิด:</p>" + "<ul>" + "<li>การติดตั้งแอปหรือม็อดเสียหาย</li>"
				+ "<li>เกมไม่เปิดหรือเกิดข้อผิดพลาดแบบเงียบ</li>" + "<li>โครงสร้างไฟล์ผิดปกติ ทำให้แก้ไขปัญหายาก</li>"
				+ "<li>การจัดการ Java หน่วยความจำ หรือม็อดไม่แน่นอน</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "แนะนำอย่างยิ่งให้ใช้ launcher ต่อไปนี้:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Launcher ไม่แนะนำ";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "เปลี่ยนไปใช้ launcher ที่แนะนำเพื่อรับการสนับสนุน";
	}

	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ขาดม็อดที่แนะนำสำหรับสภาพแวดล้อมนี้</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "ขาดม็อดที่แนะนำ";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "ติดตั้งม็อดที่แนะนำเพื่อประสบการณ์ที่ดีที่สุด";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ตรวจพบม็อดที่ไม่แนะนำในระบบของคุณ</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "พบม็อดที่ไม่แนะนำ";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "ลบม็อดที่ไม่แนะนำเพื่อหลีกเลี่ยงปัญหา";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ตรวจพบการแก้ไขไฟล์สำคัญโดยไม่ได้รับอนุญาต คุณอาจแก้ไขไฟล์หรือใช้ launcher ที่ไม่น่าเชื่อถือ</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "ตรวจพบการแก้ไขไฟล์";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "ติดตั้งไฟล์ต้นฉบับใหม่เพื่อกู้คืนความสมบูรณ์ของระบบ";
	}

	@Override
	public String configuracionCorporativa() {
		return "การตั้งค่าองค์กร";
	}

	@Override
	public String idiomaRespaldo() {
		return "ภาษาสำรอง";
	}

	@Override
	public String buscardorHabilitado() {
		return "เปิดใช้งานตัวค้นหา";
	}

	@Override
	public String nombreHerramienta() {
		return "ชื่อเครื่องมือ";
	}

	@Override
	public String condenarPirateria() {
		return "ต่อต้านการละเมิดลิขสิทธิ์";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "Launcher ที่แนะนำ";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "Launcher ที่ไม่แนะนำ";
	}

	@Override
	public String modsRecomendados() {
		return "ม็อดที่แนะนำ";
	}

	@Override
	public String modsDesaconsejados() {
		return "ม็อดที่ไม่แนะนำ";
	}

	@Override
	public String antiTamper() {
		return "ระบบป้องกันการแก้ไขไฟล์";
	}

	@Override
	public String proximamente() {
		return "เร็ว ๆ นี้";
	}

	@Override
	public String informacion() {
		return "ข้อมูล";
	}

	@Override
	public String errorCargandoImagen() {
		return "เกิดข้อผิดพลาดขณะโหลดภาพ";
	}

	@Override
	public String configuracionBasica() {
		return "การตั้งค่าพื้นฐาน";
	}

	@Override
	public String funcionalidades() {
		return "ฟังก์ชันการทำงาน";
	}

	@Override
	public String derechosMiranda() {
		return "สิทธิ์ Miranda (แนะนำอย่างยิ่ง)";
	}

	@Override
	public String gestionVerificaciones() {
		return "การจัดการการตรวจสอบ";
	}

	@Override
	public String idVerificacion() {
		return "รหัส";
	}

	@Override
	public String nombreVerificacion() {
		return "ชื่อ";
	}

	@Override
	public String codigoVerificacion() {
		return "โค้ด";
	}

	@Override
	public String documentacionVerificacion() {
		return "เอกสาร";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "การตรวจสอบที่เปิดใช้งาน:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "การตรวจสอบที่ปิดใช้งาน:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "ปิดใช้งานรายการที่ไม่ใช่องค์กรทั้งหมด";
	}

	@Override
	public String verCodigo() {
		return "ดูโค้ด";
	}

	@Override
	public String verDocumentacion() {
		return "ดูเอกสาร";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "เลือกการตรวจสอบที่จะปิดใช้งาน";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "เลือกการตรวจสอบที่จะเปิดใช้งาน";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "ปิดใช้งานการตรวจสอบที่ไม่เหมาะสำหรับองค์กร %d รายการแล้ว";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "ไม่มีการตรวจสอบที่ไม่ใช่องค์กรให้ปิดใช้งาน";
	}

	@Override
	public String operacionCompletada() {
		return "ดำเนินการเสร็จสิ้น";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "เราคิดถึง Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "สีของการตรวจสอบระดับองค์กร";
	}

	// Métodos para la gestión de lanzadores
	@Override
	public String nombreLanzador() {
		return "ชื่อ Launcher";
	}

	@Override
	public String motivo() {
		return "เหตุผล";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Launcher ที่ไม่แนะนำ";
	}

	@Override
	public String moverADesaconsejados() {
		return "ย้ายไปยังรายการไม่แนะนำ";
	}

	@Override
	public String moverARecomendados() {
		return "ย้ายไปยังรายการแนะนำ";
	}

	@Override
	public String guardarCambios() {
		return "บันทึกการเปลี่ยนแปลง";
	}

	@Override
	public String cancelar() {
		return "ยกเลิก";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "กรุณาเลือก launcher เพื่อย้าย";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "บันทึกการเปลี่ยนแปลงเรียบร้อยแล้ว";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEs(String nombreLanzador) {
		return "launcher นี้ไม่แนะนำเนื่องจากมีปัญหาด้านความปลอดภัยและความเสถียรที่ทราบอยู่แล้ว";
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
		return "เหตุผล";
	}

	@Override
	public String agregarLanzador() {
		return "เพิ่ม launcher";
	}

	@Override
	public String quitarLanzador() {
		return "ลบ launcher";
	}

	@Override
	public String editarRazones() {
		return "แก้ไขเหตุผล";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "เลือก launcher ที่ต้องการลบ";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "เลือก launcher ที่ต้องการแก้ไข";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "แก้ไขเหตุผลสำหรับ " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "เพิ่มภาษาใหม่";
	}

	@Override
	public String aceptar() {
		return "ยืนยัน";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "เลือกภาษา";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "launcher เหล่านี้เป็นรายการที่ CrashDetector แนะนำ";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "สีของผลลัพธ์ที่ถูกต้อง";
	}

	public String modsNoRecomendados() {
		return "ม็อดที่ไม่แนะนำ";
	}

	public String agregarMod() {
		return "เพิ่มม็อด";
	}

	public String quitarMod() {
		return "ลบม็อด";
	}

	public String modId() {
		return "รหัสม็อด / ชื่อ JBoss Modules";
	}

	public String rutaMod() {
		return "เส้นทาง / ไฟล์ของม็อด";
	}

	public String errorDebeIndicarMod() {
		return "ต้องระบุอย่างน้อยรหัสม็อดหรือเส้นทางของม็อด";
	}

	public String modsNoRecomendadosAviso() {
		return "ที่นี่คุณสามารถลงทะเบียนม็อดที่ไม่แนะนำ เพื่อให้ CrashDetector ตรวจพบได้หากมีการติดตั้ง";
	}

	@Override
	public String anularNormal() {
		return "แทนที่แบบปกติ";
	}

	@Override
	public String anularNormalDescripcion() {
		return "CrashDetector ควรแจ้งเตือนแม้ว่าเกมจะยังไม่ล่ม";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "ลงทะเบียนม็อดที่ CrashDetector แนะนำ หากขาดหาย CrashDetector สามารถแจ้งเตือนได้";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return ""
				+ "หากคุณเลือกเปิดใช้งานคำเตือนต่อต้านการละเมิดลิขสิทธิ์ แนะนำให้กำหนดสิทธิของผู้ที่ขอรับการช่วยเหลือไว้ที่นี่ "
				+ "เพื่อใช้เป็นมาตรการป้องกันล่วงหน้า\n\n"

				+ "ตรงกันข้ามกับความเชื่อที่พบได้บ่อย หลายชุมชนและช่องทางช่วยเหลือยอดนิยม "
				+ "ไม่ได้บังคับให้เปิดใช้งานคำเตือนต่อต้านการละเมิดลิขสิทธิ์เพื่อให้ความช่วยเหลือเสมอไป "
				+ "อย่างไรก็ตาม การบันทึกสิทธิเหล่านี้ไว้อาจเป็นประโยชน์ หากมีผู้ใช้เข้าถึงช่องทางช่วยเหลืออยู่แล้ว\n\n"

				+ "คุณสามารถอ้างอิงเอกสารทางการ เช่น เอกสารสิทธิขั้นพื้นฐานของผู้ถูกควบคุมตัว " + "ในเม็กซิโก:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "รวมถึงหลักกฎหมายที่คล้ายกันซึ่งใช้ในประเทศอื่น ๆ เช่น "
				+ "สหรัฐอเมริกา สหพันธรัฐรัสเซีย สาธารณรัฐประชาชนจีน "
				+ "สาธารณรัฐอิสลามอิหร่าน และสาธารณรัฐประชาธิปไตยประชาชนเกาหลี\n\n"

				+ "ตัวอย่างของสิทธิที่สามารถระบุไว้ ได้แก่:\n"
				+ "• สิทธิที่จะไม่ให้ข้อมูลที่ไม่จำเป็นต่อการช่วยเหลือ เช่น launcher ที่ใช้ " + "ชื่อผู้ใช้ หรือ UUID\n"
				+ "• สิทธิที่จะไม่กล่าวโทษตนเอง\n" + "• สิทธิที่จะปฏิเสธการตอบคำถามที่ไม่จำเป็นต่อการแก้ปัญหา\n"
				+ "• สิทธิที่จะได้รับคำแนะนำภายในแชต\n"
				+ "• สิทธิที่จะใช้ระบบปกปิดข้อมูลในบันทึก (logs) ที่รวมอยู่ใน CrashDetector\n\n"

				+ "ข้อความนี้รองรับเนื้อหา HTML";
	}

	@Override
	public String editar() {
		return "แก้ไข";
	}

	@Override
	public String advertenciaHashLento() {
		return "คำเตือน: การเพิ่มไฟล์ขนาดใหญ่จำนวนมากอาจทำให้การตรวจสอบใช้เวลาหลายนาที "
				+ "CrashDetector จะต้องคำนวณแฮชของแต่ละไฟล์ก่อนดำเนินการต่อ "
				+ "แนะนำให้ป้องกันเฉพาะไฟล์ที่จำเป็นจริง ๆ เท่านั้น";
	}

	@Override
	public String agregarArchivo() {
		return "เพิ่มไฟล์";
	}

	@Override
	public String agregarCarpeta() {
		return "เพิ่มโฟลเดอร์";
	}

	@Override
	public String quitar() {
		return "ลบ";
	}

	@Override
	public String rutaArchivo() {
		return "เส้นทางของไฟล์";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "เส้นทางที่เลือกอยู่นอกไดเรกทอรีปัจจุบันของเกม "
				+ "อนุญาตเฉพาะไฟล์และโฟลเดอร์ภายในไดเรกทอรีปัจจุบันหรือไดเรกทอรีย่อยเท่านั้น";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>"
				+ "ความคิดเห็นของ Sylent Bell ไม่จำเป็นต้องตรงกับความคิดเห็นของเรา "
				+ "เราแค่อยากใส่ไว้เพราะคิดว่ามันตลก CrashDetector เป็นกลางทางศาสนา" + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ม็อด GML (Groovy ModLoader) ต้องการการเปลี่ยนแปลงเหล่านี้ และเป็นสาเหตุที่พบบ่อยที่สุดของปัญหานี้</b>";
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
				+ "ตรวจพบการใช้งาน <i>Flywheel แบบแยกติดตั้ง</i></b>"
				+ "<p><b>Flywheel แบบแยกติดตั้งล้าสมัยแล้ว</b> และไม่ควรใช้กับเวอร์ชันสมัยใหม่</p>"
				+ "<p>เวอร์ชันปัจจุบันของ <b>Create</b> <b>มี Flywheel รวมมาแล้ว</b> ดังนั้นการติดตั้งแยกต่างหาก "
				+ "จะทำให้เกิดปัญหาความเข้ากันได้และข้อผิดพลาดในการโหลด</p>"
				+ "<p>ม็อดบางตัวที่อ้างอิง Flywheel แบบแยกติดตั้งโดยตรงอาจ "
				+ "<b>ไม่ทำงาน</b> หรือ <b>ทำงานไม่เสถียร</b> " + "ในบางกรณีขั้นสูง ม็อดเหล่านี้อาจพอใช้งานได้หาก "
				+ "<b>แก้ไขไฟล์ <code>mods.toml</code> ด้วยตนเอง</b> เพื่อปรับช่วงเวอร์ชัน "
				+ "แต่<b>ไม่แนะนำ</b>ให้ทำเช่นนั้น</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>ม็อดที่ตรวจพบซึ่งอ้างอิงถึง Flywheel:</b></p><ul>" + listaMods.toString() + "</ul>")
				+ "<p>วิธีแก้ที่แนะนำคือ <b>ลบ Flywheel แบบแยกติดตั้ง</b> และใช้เฉพาะ "
				+ "เวอร์ชันที่รวมมากับ Create เท่านั้น</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Flywheel แบบแยกติดตั้ง";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดที่เกี่ยวข้องกับม็อด <i>Floral Enchantments</i></b>"
				+ "<p>การล่มเกิดจากข้อผิดพลาดภายในของม็อดขณะจัดการข้อมูลของเกม "
				+ "ซึ่งทำให้เกิด <b>NullPointerException</b> ระหว่างการทำงาน</p>"
				+ "<p>ปัญหานี้มักแก้ได้โดยอัปเดตม็อดหรือลบม็อดออก</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "ข้อผิดพลาด Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "คุณมี MixinExtras เวอร์ชัน NeoForge และเวอร์ชันปกติติดตั้งพร้อมกัน "
				+ "หากคุณใช้ MinecraftForge คุณสามารถติดตั้ง <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>ลิงก์นี้</a> เพื่อแก้ไขปัญหา</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดเกี่ยวกับเงาของพื้นดินเมื่อใช้ shaders (Iris)</b>"
				+ "<p>ปัญหาเกิดขึ้นระหว่างการเรนเดอร์พื้นดิน</p>"
				+ "<p>แนะนำให้ <b>ทดลองเล่นโดยปิด shaders</b> หรือลดคุณภาพกราฟิก "
				+ "โดยเฉพาะเมื่อใช้การตั้งค่า <b>Ultra</b></p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "เงาภูมิประเทศ (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบ tick ของเซิร์ฟเวอร์ที่ยาวเกินไป</b>"
				+ "<p>ซึ่งบ่งชี้ว่าเกมค้างอยู่ใน tick เดียวเป็นเวลานานเกินไป</p>"
				+ "<p>แนะนำให้ <b>ตรวจสอบ thread dump</b> ที่ถูกสร้างใน log เพื่อหาสาเหตุ</p>"
				+ "<p><b>การวิเคราะห์ Stack Trace</b> สามารถช่วยระบุต้นตอของปัญหาได้</p>"
				+ "<p>นอกจากนี้ ปุ่ม <b>ดูใน log</b> จะไฮไลต์ม็อดที่อาจเป็นสาเหตุเป็นสีแดง "
				+ "รวมถึงรายการที่อยู่ระหว่าง <code>$modid$</code> ซึ่งมักบ่งบอกถึงต้นตอของปัญหา "
				+ "สำหรับการตรวจสอบแบบเรียลไทม์ แนะนำให้ใช้ตัวตรวจสอบ CPU ใน VisualVM "
				+ "และตรวจสอบให้แน่ใจว่าเซิร์ฟเวอร์หรือคอมพิวเตอร์ของคุณมีประสิทธิภาพเพียงพอสำหรับม็อดที่ใช้งาน "
				+ "เนื่องจากบางครั้งม็อดทั้งหมดอาจทำงานได้ถูกต้อง แต่มีจำนวนมากเกินไป</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Tick เซิร์ฟเวอร์ยาวผิดปกติ";
	}

	@Override
	public String tituloLFPDPPP() {
		return "กฎหมายว่าด้วยการคุ้มครองข้อมูลส่วนบุคคลในความครอบครองของเอกชน";
	}

	@Override
	public String aceptarPermanentemente() {
		return "ยอมรับถาวร";
	}

	// Métodos para la Acta de Protección del Idioma Cultural de Pyongyang
	public String actaProteccionIdiomaCultural() {
		return "กฎหมายคุ้มครองภาษาและวัฒนธรรมเปียงยาง";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "การแปลภาษาเกาหลีมีคำสแลงจากเกาหลีใต้ซึ่งควรหลีกเลี่ยงเพื่อให้เป็นไปตามกฎหมาย "
				+ "การใช้ภาษาต่างประเทศ โดยเฉพาะจากทางใต้ ถูกห้ามอย่างเคร่งครัด "
				+ "ตามกฎหมายคุ้มครองภาษาและวัฒนธรรมเปียงยาง";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "สำหรับข้อมูลเพิ่มเติม โปรดดูเอกสารทางการของกฎหมาย: "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>กฎหมายคุ้มครองภาษาและวัฒนธรรมเปียงยาง</a>";
	}

	public String leerLeyCompleta() {
		return "อ่านกฎหมายฉบับเต็ม";
	}

	public String errorAbriendoEnlace() {
		return "เกิดข้อผิดพลาดขณะเปิดลิงก์";
	}

	@Override
	public String canarioTitulo() {
		return "Canario ตามคำสั่งศาล";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — ระบบเฝ้าระวัง";
	}

	@Override
	public String revisar() {
		return "ตรวจสอบ";
	}

	@Override
	public String cerrar() {
		return "ปิด";
	}

	@Override
	public String canarioTodoSeguro() {
		return "บริการทั้งหมดรายงานสถานะปลอดภัย";
	}

	@Override
	public String canarioComprometido(int c) {
		return "แจ้งเตือน: มี " + c + " บริการรายงานสถานะไม่ปลอดภัย";
	}

	@Override
	public String colorAlerta() {
		return "สีแจ้งเตือน";
	}

	public String opcionesMunidiales() {
		return "ตัวเลือกทั่วโลก";
	}

	public String consentimientoLFPDPPP() {
		return "ความยินยอมตาม LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "เปิดใช้งานการส่งต่อโทเค็นการเข้าถึงผ่าน Handoff สำหรับ ReLauncher (ไม่แนะนำ)";
	}

	public String consolaDesarrollo() {
		return "คอนโซลนักพัฒนา";
	}

	public String mundial() {
		return "ทั่วโลก";
	}

	public String ningun() {
		return "ไม่มี";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "คอนโซลนักพัฒนา";
	}

	public String bajar() {
		return "เลื่อนลง";
	}

	public String logsSoporte() {
		return "บันทึกสำหรับการสนับสนุน";
	}

	public String detenerProceso() {
		return "หยุดกระบวนการ";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "คัดลอกส่วนที่เลือก";
	}

	public String seleccionarTodo() {
		return "เลือกทั้งหมด";
	}

	public String copiarTodo() {
		return "คัดลอกทั้งหมด";
	}

	public String guardarTodoComoArchivo() {
		return "บันทึกทั้งหมดเป็นไฟล์";
	}

	public String obtenerEnlaceSoporte() {
		return "รับลิงก์การสนับสนุน";
	}

	public String borrarTodo() {
		return "ลบทั้งหมด";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "สีพื้นหลังของคอนโซล";
	}

	public String colorTextoConsola() {
		return "สีข้อความของคอนโซล";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "ยืนยันความยินยอมแล้ว\nการรวมการแชร์บันทึกจะถูกเพิ่มในส่วนนี้";
	}

	@Override
	public String usarSakuraOriginal() {
		return "ใช้ภาพ Sakura Riddle ต้นฉบับ";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "\"warrant canary\" เป็นกลไกเพื่อความโปร่งใส\n\n"
				+ "ตราบใดที่ข้อความนี้ยังคงอยู่และบริการยังแสดงว่าปลอดภัย " + "แสดงว่าโครงการยังไม่ได้รับคำสั่งศาลลับ "
				+ "คำขอเซ็นเซอร์ หรือคำขอเฝ้าระวังทางกฎหมายใด ๆ\n\n" + "หาก canary ใดหายไปหรือแสดงว่าไม่ปลอดภัย "
				+ "นั่นหมายความว่ามีการเปลี่ยนแปลงทางกฎหมายเกิดขึ้น\n\n"
				+ "แผงนี้จะตรวจสอบ canary ทั้งหมดในระบบและแสดงสถานะปัจจุบัน\n\n" + "กด \"ตรวจสอบ\" เพื่ออัปเดตสถานะ";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "ต้องการรีเซ็ตตัวเลือกทั้งหมดกลับเป็นค่าเริ่มต้นหรือไม่?";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "ไม่มีตัวเลือก";
	}

	@Override
	public String seleccionaColor() {
		return "เลือกสี";
	}

	@Override
	public String botonMostrarGUI() {
		return "แสดง GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "บันทึกทั้งหมด";
	}

	@Override
	public String botonRestablecerTodo() {
		return "รีเซ็ตทั้งหมด";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms ไม่ได้โหลด";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดขณะเข้าถึง API ของ LuckPerms</b>"
				+ "<p>ข้อความระบุว่า <b>LuckPerms ยังไม่ได้โหลด</b> ในขณะที่ปลั๊กอินอื่นพยายามใช้งาน</p>"
				+ "<p><b>สาเหตุที่เป็นไปได้:</b></p>" + "<ul>"
				+ "<li>ปลั๊กอิน <b>LuckPerms ไม่ได้ติดตั้ง</b> หรือ <b>เริ่มต้นไม่สำเร็จ</b></li>"
				+ "<li>ปลั๊กอินอื่นพยายามเข้าถึง LuckPerms เร็วเกินไปหรือไม่ถูกต้อง</li>" + "</ul>"
				+ "<p>แนะนำให้ <b>ตรวจสอบคอนโซล</b> ผ่านลิงก์เพื่อระบุปลั๊กอินที่เรียกใช้ LuckPerms "
				+ "และตรวจสอบความเข้ากันได้</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "ไม่พบ Shaderpack ของ Iris";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "ไม่ทราบ" : shaderpack;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดขณะโหลด shaderpack ด้วย Iris/Oculus</b>" + "<p><b>Shaderpack ที่ได้รับผลกระทบ:</b> "
				+ nombre + "</p>" + "<p>Minecraft ไม่สามารถเปิดไฟล์ของ shaderpack ได้ (FileSystemNotFoundException)</p>"
				+ "<p><b>วิธีแก้ไขที่เป็นไปได้:</b></p>" + "<ul>"
				+ "<li>ตรวจสอบว่า shaderpack ถูกติดตั้งอย่างถูกต้องในโฟลเดอร์ <b>shaderpacks</b></li>"
				+ "<li>ดาวน์โหลด shaderpack ใหม่ เนื่องจากไฟล์อาจเสียหาย</li>"
				+ "<li>หากปัญหายังอยู่ ให้ลบไฟล์ <b>config/iris.properties</b> เพื่อรีเซ็ตการตั้งค่า Iris</li>"
				+ "</ul>" + "<p>หลังจากแก้ไขแล้ว ให้เริ่มเกมใหม่อีกครั้ง</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "ไม่สามารถเขียนไฟล์การตั้งค่าได้";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "ไม่ทราบ" : ruta;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "เกิดข้อผิดพลาดขณะบันทึกไฟล์การตั้งค่า</b>" + "<p><b>ไฟล์ที่ได้รับผลกระทบ:</b> " + archivo + "</p>"
				+ "<p>Minecraft ไม่สามารถเขียนไฟล์ด้วยการเขียนแบบอะตอม (REPLACE_ATOMIC) ได้</p>"
				+ "<p><b>สาเหตุที่พบบ่อย:</b></p>" + "<ul>" + "<li>สิทธิ์ของโฟลเดอร์หรือไฟล์ไม่ถูกต้อง</li>"
				+ "<li>ไฟล์ถูกตั้งเป็นอ่านอย่างเดียว</li>"
				+ "<li>โปรแกรมอื่น (เช่น antivirus, backup, editor) กำลังใช้งานไฟล์</li>" + "</ul>"
				+ "<p><b>คำแนะนำ:</b></p>" + "<ul>" + "<li>ตรวจสอบสิทธิ์การเขียนของโฟลเดอร์</li>"
				+ "<li>ยกเลิกสถานะอ่านอย่างเดียวของไฟล์</li>" + "<li>ปิดโปรแกรมที่อาจใช้งานไฟล์อยู่</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "การเข้าถึงถูกปฏิเสธขณะสร้างสำเนาสำรอง";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "ไม่ทราบ" : origen;
		String dst = backup == null || backup.isEmpty() ? "ไม่ทราบ" : backup;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "เกิดข้อผิดพลาดด้านสิทธิ์ขณะสร้างสำเนาสำรองของไฟล์การตั้งค่า</b>" + "<p><b>ไฟล์ต้นฉบับ:</b> " + src
				+ "</p>" + "<p><b>ไฟล์สำรอง:</b> " + dst + "</p>"
				+ "<p>ระบบปฏิบัติการบล็อกการเข้าถึงระหว่างการบันทึกไฟล์</p>" + "<p><b>สาเหตุที่พบบ่อย:</b></p>" + "<ul>"
				+ "<li>สิทธิ์ของโฟลเดอร์ไม่เพียงพอ</li>" + "<li>ไฟล์ถูกตั้งเป็นอ่านอย่างเดียว</li>"
				+ "<li>โปรแกรมอื่น (antivirus, sync, editor) กำลังใช้งานไฟล์</li>" + "</ul>" + "<p><b>คำแนะนำ:</b></p>"
				+ "<ul>" + "<li>ตรวจสอบสิทธิ์ของโฟลเดอร์ <b>config</b></li>"
				+ "<li>ปิดโปรแกรมที่อาจกำลังใช้งานไฟล์</li>"
				+ "<li>ลองรัน launcher หรือ Minecraft ด้วยสิทธิ์ผู้ดูแลระบบ</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		return "เปิดใช้งานคอนโซล";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>เครื่องมือดีบัก</b><br><br>"
				+ "ที่นี่คุณสามารถเปิดใช้งานฟีเจอร์ขั้นสูงเพื่อดีบัก CrashDetector และเกมของคุณ<br><br>"
				+ "แนะนำให้เปิดคอนโซลนักพัฒนาเพื่อรับข้อมูลโดยละเอียด, stack trace และการวิเคราะห์ระหว่างการตรวจสอบ<br><br>"
				+ "หากต้องการทดสอบเซิร์ฟเวอร์หลายผู้เล่นในโหมดออนไลน์ อาจจำเป็นต้องอนุญาตให้ส่งต่อโทเค็นการเข้าถึงไปยัง CrashDetector จากการตั้งค่าความเป็นส่วนตัว "
				+ "ซึ่งโดยทั่วไป <b>ไม่แนะนำ</b> ในกรณีอื่น<br><br>"
				+ "คำแนะนำทั้งหมด: <a href='https://example.com'>ลิงก์!</a>";
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบความไม่เข้ากันระหว่าง Simple Clouds และ shaders</b>"
				+ "<p>Simple Clouds ไม่สามารถใช้งานร่วมกับม็อด shaders (Iris/Oculus) เมื่อมี Distant Horizons ติดตั้งอยู่</p>"
				+ "<p><b>ตัวเลือกที่แนะนำ:</b></p>" + "<ul>" + "<li>ลบ <b>Simple Clouds</b> หากต้องการใช้ shaders</li>"
				+ "<li>หรือถอนการติดตั้ง <b>Iris หรือ Oculus</b> หากต้องการใช้ Simple Clouds</li>" + "</ul>"
				+ "<p>ข้อจำกัดนี้มาจากตัวม็อด Simple Clouds เอง และไม่สามารถแก้ไขได้โดยไม่แก้ไขโค้ดของม็อด</p>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "ความไม่เข้ากัน: Simple Clouds กับ Shaders";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "สีปุ่มแถบด้านข้าง";
	}

	@Override
	public String profilerTitulo() {
		return "Profiler";
	}

	@Override
	public String profilerDescripcion() {
		return "เครื่องมือวิเคราะห์ประสิทธิภาพโดยใช้การเก็บข้อมูลและการสุ่มตัวอย่าง";
	}

	@Override
	public String profilerIniciar() {
		return "เริ่ม";
	}

	@Override
	public String profilerDetener() {
		return "หยุด";
	}

	@Override
	public String profilerLimpiar() {
		return "ล้าง";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "Profiler เริ่มทำงานแล้ว";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "Profiler หยุดทำงานแล้ว";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "ได้รับตัวอย่างจากเธรด: " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "การสุ่มตัวอย่าง stack เป็นระยะเพื่อตรวจหาคอขวดและการค้าง";
	}

	@Override
	public String entrarAlJuego() {
		return "เข้าสู่เกม";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดในเส้นทางของระบบ</b>"
				+ "<p>Minecraft ไม่สามารถเริ่มต้นได้เนื่องจากมีอักขระที่ไม่ถูกต้องในชื่อโฟลเดอร์</p>"
				+ "<p>ระบบตรวจพบอักขระที่ไม่ถูกต้องในเส้นทาง (เช่น ':' หรือสัญลักษณ์พิเศษอื่น ๆ)</p>"
				+ "<p><b>วิธีแก้ไขที่แนะนำ:</b></p>" + "<ul>" + "<li>เปลี่ยนชื่อโฟลเดอร์ของอินสแตนซ์หรือโปรไฟล์</li>"
				+ "<li>ใช้อักขระ ASCII พื้นฐานเท่านั้น (A-Z, a-z, 0-9)</li>"
				+ "<li>อย่าใช้เครื่องหมายวรรณยุกต์ สัญลักษณ์พิเศษ ช่องว่างที่มีปัญหา หรืออีโมจิ</li>" + "</ul>"
				+ "<p>ตัวอย่างที่ใช้ได้: <b>MiInstancia1</b></p>"
				+ "<p>ตัวอย่างที่ใช้ไม่ได้: <b>Instancia🔥</b> หรือ <b>Instancia:Mod</b></p>";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "เส้นทางไม่ถูกต้อง: มีอักขระที่ไม่ได้รับอนุญาต";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบการล้มเหลวของ shaders ของ Twilight Forest บน GPU Intel</b>"
				+ "<p>ข้อผิดพลาดนี้เกี่ยวข้องกับไดรเวอร์กราฟิกของ Intel ขณะโหลด shaders ของม็อด Twilight Forest</p>"
				+ "<p>การล้มเหลวเกิดขึ้นภายในไดรเวอร์ (igxelpicd64) และไม่ใช่ปัญหาโดยตรงของม็อดหรือ Minecraft</p>"
				+ "<p><b>วิธีแก้ไขที่แนะนำ:</b></p>" + "<ul>" + "<li>อัปเดตไดรเวอร์ Intel เป็นเวอร์ชันล่าสุดที่มี</li>"
				+ "<li>ลองใช้เวอร์ชัน 31.0.101.8331 หรือ 31.0.101.8247 WHQL โดยเฉพาะ ซึ่งตามรายงานระบุว่าไม่มีปัญหานี้</li>"
				+ "</ul>" + "<p>หน้าติดตามปัญหาอย่างเป็นทางการ:</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>หมายเหตุ:</b> GPU Intel รุ่นเก่าบางรุ่นอาจไม่ได้รับอัปเดตที่แก้ปัญหานี้</p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "แครช: Twilight Forest + ไดรเวอร์ Intel";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "Forge: ไม่สามารถโหลดตัวให้บริการภาษาได้";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "ไม่ทราบผู้ให้บริการ" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Forge ไม่สามารถโหลดตัวให้บริการภาษาได้</b>" + "<p>เกิดข้อผิดพลาดขณะเริ่มต้น IModLanguageProvider</p>"
				+ "<p><b>ผู้ให้บริการที่ล้มเหลว:</b> " + providerTexto + "</p>" + "<p>ปัญหานี้มักเกิดเมื่อ:</p>"
				+ "<ul>" + "<li>ขาด dependency ที่จำเป็น (เช่น Kotlin for Forge)</li>"
				+ "<li>เวอร์ชันของม็อดไม่เข้ากันกับเวอร์ชัน Forge ที่ใช้งาน</li>" + "<li>ไฟล์ของม็อดเสียหาย</li>"
				+ "</ul>" + "<p><b>วิธีแก้ไขที่แนะนำ:</b></p>" + "<ul>" + "<li>ติดตั้งม็อดที่เกี่ยวข้องใหม่</li>"
				+ "<li>ตรวจสอบว่า dependency ทั้งหมดถูกติดตั้งครบ</li>"
				+ "<li>ตรวจสอบว่าใช้เวอร์ชันที่เข้ากันกับ Forge ปัจจุบัน</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "แครช: Lets Do Compat (RecipeManager intercept)";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบแครชใน Lets Do Compat (interceptApply)</b>" + "<p>ข้อผิดพลาดเกิดขึ้นภายในการแปลงเมธอด "
				+ "<b>RecipeManager.interceptApply</b> ที่ทำโดย Lets Do Compat</p>" + "<p>ซึ่งมักบ่งบอกว่า:</p>"
				+ "<ul>" + "<li>มีความไม่เข้ากันระหว่าง Lets Do Compat กับม็อดอื่นที่แก้ไขสูตร</li>"
				+ "<li>ใช้เวอร์ชันที่ไม่ตรงกับ Minecraft</li>"
				+ "<li>มีความขัดแย้งระหว่าง transformer (mixin/coremod)</li>" + "</ul>"
				+ "<p><b>วิธีแก้ไขที่แนะนำ:</b></p>" + "<ul>"
				+ "<li>ลองลบ Lets Do Compat ชั่วคราวเพื่อตรวจสอบความขัดแย้ง</li>" + "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI: ข้อผิดพลาดใน Item Group (ปลั๊กอินไม่เข้ากัน)";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "ไม่ทราบปลั๊กอิน" : String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "JEI ตรวจพบข้อผิดพลาดขณะสร้างกลุ่มไอเท็ม</b>"
				+ "<p>ปลั๊กอินหนึ่งหรือหลายตัวทำให้เกิดข้อผิดพลาดขณะ JEI สร้างรายการวัตถุดิบ</p>"
				+ "<p><b>ปลั๊กอิน/กลุ่มที่ได้รับผลกระทบ:</b> " + listaPlugins + "</p>" + "<p>ปัญหานี้มักเกิดเมื่อ:</p>"
				+ "<ul>" + "<li>ปลั๊กอิน JEI ถูกพัฒนาไม่ถูกต้องหรือเก่า</li>"
				+ "<li>ไม่เข้ากันกับเวอร์ชัน JEI ปัจจุบัน</li>"
				+ "<li>ใช้ Fabric API และมีม็อดที่ลงทะเบียน Item Group ผิด</li>" + "</ul>"
				+ "<p><b>วิธีแก้ไขที่แนะนำ:</b></p>" + "<ul>" + "<li>อัปเดต JEI และม็อดที่เกี่ยวข้อง</li>"
				+ "<li>ลบปลั๊กอินที่มีปัญหาชั่วคราวเพื่อตรวจสอบ</li>" + "<li>รายงานข้อผิดพลาดไปยังผู้พัฒนาม็อด</li>"
				+ "</ul>" + "<p>ไอเท็มในกลุ่มเหล่านี้จะไม่แสดงจนกว่าปัญหาจะถูกแก้ไข</p>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "เวอร์ชันม็อดไม่ถูกต้อง (SemVer)";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "ไม่ทราบ" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "ไม่พบตำแหน่งของม็อด" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบเวอร์ชันม็อดไม่ถูกต้อง</b>" + "<p>เวอร์ชัน <b>" + v
				+ "</b> ไม่เป็นไปตามรูปแบบ SemVer ที่ถูกต้อง</p>"
				+ "<p>ข้อผิดพลาดบ่งชี้ว่ามี pre-release ว่าง (ลงท้ายด้วย '+')</p>" + "<p><b>ม็อดที่เกี่ยวข้อง:</b><br>"
				+ u + "</p>" + "<p><b>วิธีแก้ไขที่แนะนำ:</b></p>" + "<ul>"
				+ "<li>แก้ไขไฟล์ของม็อดและปรับเวอร์ชันให้ถูกต้อง</li>" + "<li>ลบ '+' ท้ายหากไม่มี metadata ต่อท้าย</li>"
				+ "<li>อัปเดตม็อดเป็นเวอร์ชันที่แก้ไขแล้ว</li>" + "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS: การเข้าถึงระหว่างโมดูลไม่ถูกต้อง";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบการเข้าถึงระหว่างโมดูลที่ไม่ถูกต้อง (JPMS)</b>"
				+ "<p>ระบบโมดูลของ Java (JPMS) บล็อกการเข้าถึงระหว่างคลาส</p>" + "<p><b>คลาสที่พยายามเข้าถึง:</b><br>"
				+ claseOrigen + " (โมดูล: " + moduloOrigen + ")</p>" + "<p><b>คลาสที่ถูกบล็อก:</b><br>" + claseDestino
				+ " (โมดูล: " + moduloDestino + ")</p>"
				+ "<p>ข้อผิดพลาดนี้เกิดขึ้นเมื่อม็อดไม่ได้กำหนด exports หรือ opens อย่างถูกต้องใน module-info.java</p>"
				+ "<p><b>สาเหตุที่เป็นไปได้:</b></p>" + "<ul>" + "<li>โมดูลไม่ได้ export package ที่จำเป็น</li>"
				+ "<li>ขาดคำสั่ง <b>opens</b> สำหรับ reflection</li>"
				+ "<li>ม็อดไม่ได้ตั้งค่าให้รองรับ JPMS อย่างถูกต้อง</li>" + "</ul>"
				+ "<p>ปัญหานี้ต้องได้รับการแก้ไขโดยผู้พัฒนาม็อด</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin: คลาสอยู่ในแพ็กเกจ mixin ไม่ถูกต้อง";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "คลาสถูกวางไว้ในแพ็กเกจ Mixin อย่างไม่ถูกต้อง</b>"
				+ "<p>มีคลาสปกติถูกวางไว้ในแพ็กเกจที่กำหนดเป็น mixin</p>" + "<p><b>คลาสที่มีปัญหา:</b><br>" + clase
				+ "</p>" + "<p><b>แพ็กเกจ mixin:</b><br>" + paquete + "</p>"
				+ "<p><b>ไฟล์ mixins ที่เกี่ยวข้อง:</b><br>" + archivoMixin + "</p>"
				+ "<p>คลาสปกติไม่ควรอยู่ในแพ็กเกจที่กำหนดใน mixins.json</p>"
				+ "<p>เฉพาะคลาสที่เป็น mixin เท่านั้นที่ควรอยู่ในแพ็กเกจนี้</p>"
				+ "<p><b>แนวทางแก้ไขสำหรับนักพัฒนา:</b> ย้ายคลาสปกติออกจากแพ็กเกจ mixin "
				+ "หรือแก้ไขการตั้งค่าในไฟล์ mixins.json</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบปัญหากับไดรเวอร์ GPU Matrox</b>"
				+ "<p>จาก log พบว่าความผิดพลาดเกิดขึ้นภายในไลบรารีของไดรเวอร์ Matrox</p>"
				+ "<p>GPU Matrox (โดยเฉพาะรุ่น G200/G400 ที่ใช้ในเซิร์ฟเวอร์) "
				+ "ไม่ได้ออกแบบมาสำหรับการเรนเดอร์ 3D สมัยใหม่ และอาจไม่รองรับ OpenGL เวอร์ชันที่ Minecraft ต้องการ</p>"
				+ "<p><b>วิธีแก้ไขที่แนะนำ:</b></p>" + "<ul>" + "<li>อัปเดตไดรเวอร์ Matrox เป็นเวอร์ชันล่าสุด</li>"
				+ "<li>ติดตั้งไดรเวอร์อย่างเป็นทางการแทนของระบบ</li>"
				+ "<li>หากฮาร์ดแวร์เก่า ควรใช้ GPU ที่รองรับ OpenGL 3.2 ขึ้นไป</li>" + "</ul>"
				+ "<p>ในเซิร์ฟเวอร์ GPU เหล่านี้มักใช้สำหรับแสดงผลพื้นฐานเท่านั้น ไม่เหมาะกับ Minecraft</p>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod: ไม่พบ GPU ที่รองรับ";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod ไม่พบ GPU ที่รองรับ</b>"
				+ "<p>ม็อด <b>VulkanMod</b> พยายามเริ่มด้วย Vulkan แต่ไม่พบ GPU ที่รองรับ</p>" + "<p>มักเกิดเมื่อ:</p>"
				+ "<ul>" + "<li>GPU ไม่รองรับ Vulkan</li>" + "<li>ไดรเวอร์ GPU ล้าสมัยหรือหายไป</li>"
				+ "<li>กำลังใช้ GPU ผิดตัว (เช่น ใช้ integrated แทน dedicated)</li>" + "</ul>"
				+ "<p><b>วิธีแก้ไข:</b></p>" + "<ul>" + "<li>อัปเดตไดรเวอร์ GPU</li>"
				+ "<li>ตรวจสอบว่า GPU รองรับ Vulkan</li>" + "<li>บังคับให้ใช้ GPU แยก</li>"
				+ "<li>หากไม่รองรับ ให้ถอนการติดตั้ง VulkanMod</li>" + "</ul>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "RenderType ไม่ถูกต้องสำหรับ outline";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		String base = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ม็อดพยายามใช้ outline กับ RenderType ที่ไม่รองรับ</b>" + "<p>ข้อผิดพลาด:</p>"
				+ "<code>Can't render an outline for this rendertype!</code>";

		if (enchantDetectado) {
			base += "<p><b>ตรวจพบม็อด enchant-outline / better-enchants</b></p>"
					+ "<p>ม็อดนี้มักทำให้เกิดปัญหาใน Minecraft เวอร์ชันใหม่</p>"
					+ "<p><b>วิธีแก้:</b> ลบหรืออัปเดต enchant-outline</p>";
		} else {
			base += "<p>มักเกี่ยวข้องกับม็อดที่แก้ไขระบบเรนเดอร์ "
					+ "(Entity Model Features, Entity Texture Features, Visuality หรือ Sodium)</p>"
					+ "<p><b>วิธีแก้:</b> อัปเดตหรือปิดม็อดทีละตัว</p>";
		}

		return base;
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – DimensionalInventory เป็น null";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG พยายามบันทึก DimensionalInventory ที่เป็น null</b>" + "<p>ข้อผิดพลาด:</p>"
				+ "<code>Cannot invoke DimensionalInventory.saveInventory(...) because \"d\" is null</code>"
				+ "<p>เป็นบั๊กที่ทราบของ DivineRPG</p>" + "<p><b>วิธีแก้:</b></p>" + "<ul>"
				+ "<li>เปิดไฟล์ config ของ DivineRPG</li>" + "<li>ตั้งค่า <code>saferVetheanInventory=true</code></li>"
				+ "<li>บันทึกและรีสตาร์ทเกม</li>" + "</ul>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "ความขัดแย้ง Render Pass";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบความขัดแย้งในระบบเรนเดอร์</b>" + "<p>ข้อผิดพลาด:</p>"
				+ "<code>Close the existing render pass before performing additional commands</code>"
				+ "<p>มักเกิดจากม็อดเรนเดอร์ เช่น Iris, OptiFine, VulkanMod</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "ข้อผิดพลาดภายใน Feather Client";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดของ Feather Client</b>" + "<p>ข้อผิดพลาด:</p>"
				+ "<code>NoClassDefFoundError: feather/lib/sentry/Sentry</code>" + "<p>Feather ไม่แนะนำเพราะ:</p>"
				+ "<ul>" + "<li>ใช้ม็อดแบบปรับแต่งเอง</li>" + "<li>ไม่เข้ากันกับ Fabric มาตรฐาน</li>"
				+ "<li>อาจขัดแย้งกับ Sodium</li>" + "</ul>";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "ความขัดแย้ง Iris + Flywheel (Create 6)";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบความขัดแย้งระหว่าง Iris และ Flywheel</b>" + "<p>ข้อผิดพลาด:</p>"
				+ "<code>NoSuchFieldError: TESSELATION_SHADERS</code>" + "<p>พบการอ้างอิง <code>$irisflw$</code></p>";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "ไม่พบโมเดล GeckoLib";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ม็อดไม่พบโมเดล GeckoLib</b>" + "<p>โมเดล:</p><code>" + modelo + "</code>";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – ไม่พบแอนิเมชัน";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon พยายามใช้แอนิเมชันที่ไม่มีอยู่</b>" + "<p>แอนิเมชัน:</p><code>" + animacion + "</code>"
				+ "<p>กลุ่ม:</p><code>" + grupo + "</code>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "ข้อผิดพลาดภายใน Lunar Client";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดของ Lunar Client</b>" + "<p>ปัญหามาจากตัว client เอง</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "การเข้าถึง method หรือ field ที่ไม่ได้รับอนุญาต";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ม็อดพยายามเข้าถึง method/field ที่ถูกป้องกัน</b>" + "<p>คลาส:</p><code>" + clase + "</code>"
				+ "<p>สมาชิก:</p><code>" + miembro + "</code>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "เกิดข้อผิดพลาดขณะโหลด datapack/resourcepack";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "datapack หรือ resourcepack โหลดไม่สำเร็จ</b>" + "<p>ไฟล์:</p><code>" + archivo + "</code>"
				+ "<p>แพ็ก:</p><code>" + pack + "</code>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "ข้อผิดพลาดในการคอมไพล์ shader";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "การคอมไพล์ shader ล้มเหลว</b>" + "<p>เกมไม่สามารถคอมไพล์ shader ที่ใช้งานอยู่ได้</p>"
				+ "<p>ปัญหานี้มักเกี่ยวข้องกับ Sodium, Iris หรือ shaderpack ที่ไม่เข้ากัน</p>" + "<p>คำแนะนำ:</p>"
				+ "<ul>" + "<li>ลองใช้ shader อื่น</li>" + "<li>ปิด shader ชั่วคราว</li>"
				+ "<li>อัปเดตไดรเวอร์ GPU</li>" + "<li>หากยังไม่หาย ให้ลองรันเกมโดยไม่ใช้ Sodium</li>" + "</ul>";
	}

	public String nombreErrorCreacionModelo() {
		return "เกิดข้อผิดพลาดขณะสร้างหรือโหลดโมเดล";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>เกิดข้อผิดพลาดขณะพยายามสร้างหรือโหลดโมเดลของ Minecraft</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>โมเดลที่ได้รับผลกระทบ: <code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>ปัญหานี้มักเกิดเมื่อ:</p>");
		sb.append("<ul>");
		sb.append("<li>ม็อดมีโมเดลที่ตั้งค่าไม่ถูกต้อง</li>");
		sb.append("<li>ไฟล์ JSON ของโมเดลเสียหายหรือไม่สมบูรณ์</li>");
		sb.append("<li>มีความขัดแย้งระหว่างม็อดที่แก้ไขโมเดลหรือการเรนเดอร์</li>");
		sb.append("<li>resource pack หรือ datapack มีโมเดลที่ไม่เข้ากัน</li>");
		sb.append("</ul>");
		sb.append("<p>ลองตรวจสอบว่าม็อดหรือ resource pack ใดเป็นผู้ให้โมเดลนี้</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>สาเหตุที่เป็นไปได้:</b></p>");
		sb.append("<p>ตรวจพบม็อด <b>Cooler Animations</b> ใน log</p>");
		sb.append("<p>ม็อดนี้แก้ไขระบบแอนิเมชันและโมเดล และอาจทำให้เกิดข้อผิดพลาดในการโหลดโมเดล</p>");
		sb.append("<p>หากปัญหายังอยู่ ลองรันเกมโดยไม่ใช้ Cooler Animations</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "ปัญหากับ Starlight";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดที่เกี่ยวข้องกับ Starlight</b>"
				+ "<p>ข้อผิดพลาดเกิดใน <code>BlockStarLightEngine.initNibble</code></p>"
				+ "<p>แสดงว่ามีปัญหาในระบบแสงของม็อด <b>Starlight</b></p>"
				+ "<p>หากยังมีปัญหา ให้ลองรันเกมโดยไม่ใช้ Starlight</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "ปัญหา AAAParticles / Effekseer";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบ crash จาก Effekseer</b>" + "<p>ข้อผิดพลาดเกิดใน <code>EffekseerNativeForJava</code></p>"
				+ "<p>เกี่ยวข้องกับม็อด <b>AAAParticles</b></p>" + "<p>ลองรันเกมโดยไม่ใช้ AAAParticles</p>";
	}

	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" + "ตรวจพบ crash ใน JVM</b>"
				+ "<p>ข้อผิดพลาดเกิดใน Java Virtual Machine</p>" + "<p>มักเกิดจาก JVM ที่ล้าสมัยหรือเสียหาย</p>"
				+ "<p><b>แนะนำ:</b></p>" + "<ul>" + "<li>ใช้ OpenJDK หรือ Azul Zulu หรือ Oracle JDK</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "ความขัดแย้ง Paranoia และ C2ME";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบความขัดแย้งระหว่าง Paranoia และ C2ME</b>"
				+ "<p>เกี่ยวข้องกับ ThreadLocalRandom ถูกใช้ผิดเธรด</p>" + "<p>ลองปิด Paranoia หรือ C2ME</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "ไม่พบโฟลเดอร์ assets ของ Minecraft";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft ไม่พบโฟลเดอร์ assets</b>" + "<p>ไฟล์ทรัพยากรของเกมไม่ถูกติดตั้งหรือหายไป</p>"
				+ "<p>ลองติดตั้งใหม่หรือใช้ launcher ที่เชื่อถือได้</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return "ติดตั้ง " + dependencia + " เวอร์ชัน " + version + " หรือใหม่กว่า";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return "แทนที่ " + dependencia + " ด้วยเวอร์ชันระหว่าง " + min + " ถึง " + max;
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "ม็อด " + mod + " ต้องการ " + dependencia + " เวอร์ชันขั้นต่ำ " + version;
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "ม็อด " + mod + " ต้องการ " + dependencia + " เวอร์ชัน " + version;
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {
		return "ม็อด " + mod + " ต้องการ " + dependencia + " ระหว่าง " + min + " และ " + max + " (ปัจจุบัน: " + actual
				+ ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "เวอร์ชัน Cupboard ไม่เข้ากัน";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบ crash จาก Cupboard เวอร์ชันเก่า</b>" + "<p>แนะนำให้อัปเดตเป็นเวอร์ชัน 3.5 หรือใหม่กว่า</p>";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		return "macOS ไม่รองรับ OpenGL เวอร์ชันใหม่ แนะนำใช้เวอร์ชัน Minecraft ที่รองรับ Metal หรือใช้ Linux";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "ไม่พบแอนิเมชัน GeckoLib";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ม็อดไม่สามารถโหลดไฟล์แอนิเมชันของ GeckoLib ได้</b>" + "<p>ไฟล์ที่ได้รับผลกระทบ:</p>" + "<code>"
				+ archivo + "</code>" + "<p>ข้อผิดพลาดนี้เกิดขึ้นเมื่อไฟล์แอนิเมชัน <code>.json</code> ไม่มีอยู่จริง "
				+ "มีข้อผิดพลาดทางไวยากรณ์ หรือพาธไม่ถูกต้อง</p>" + "<p>สาเหตุที่เป็นไปได้:</p>" + "<ul>"
				+ "<li>ไฟล์ถูกลบออกไปแล้ว แต่ยังคงถูกอ้างอิงอยู่ในโค้ด</li>"
				+ "<li>มีข้อผิดพลาดทางไวยากรณ์ภายในไฟล์ JSON</li>" + "<li>พาธที่กำหนดไว้ใน log ของม็อดไม่ถูกต้อง</li>"
				+ "<li>เกิดความขัดแย้งของ dependency หรือเวอร์ชันที่ไม่เข้ากัน</li>" + "</ul>";
	}

	@Override
	public String nombreAnimacionGeckoInexiste() {
		return "ไม่พบแอนิเมชัน GeckoLib";
	}

	@Override
	public String mensajeAnimacionGeckoInexiste(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ม็อดไม่สามารถหาไฟล์แอนิเมชันของ GeckoLib ได้</b>" + "<p>ไฟล์ที่ได้รับผลกระทบ:</p>" + "<code>"
				+ archivo + "</code>"
				+ "<p>ข้อผิดพลาดนี้เกิดขึ้นเมื่อ GeckoLib พยายามโหลดแอนิเมชันที่ไม่มีอยู่ในพาธที่ระบุ "
				+ "ต่างจากข้อผิดพลาดระหว่างการโหลด (ด้านไวยากรณ์) ข้อผิดพลาดนี้หมายความว่าไฟล์หายไปจริง "
				+ "หรือพาธไม่ถูกต้อง</p>" + "<p>สาเหตุที่เป็นไปได้:</p>" + "<ul>"
				+ "<li>ไฟล์ <code>.json</code> ถูกลบออกไป หรือไม่ได้ถูกรวมไว้ใน JAR สุดท้ายของม็อด</li>"
				+ "<li>มีการพิมพ์พาธผิดในโค้ด (เช่น 'animations' เทียบกับ 'animaciones')</li>"
				+ "<li>ความแตกต่างระหว่างตัวพิมพ์ใหญ่และตัวพิมพ์เล็ก (ระบบปฏิบัติการของเซิร์ฟเวอร์เป็น Linux ซึ่งแยกความต่างนี้ แต่ตอนพัฒนาใช้ Windows ซึ่งไม่แยก)</li>"
				+ "<li>ม็อดไม่ได้รับการอัปเดตอย่างสมบูรณ์ หรือ dependency ของมันเสียหาย</li>" + "</ul>";
	}

	@Override
	public String nombreRegistroDuplicadoObjeto() {
		return "ความขัดแย้งจากการลงทะเบียนวัตถุซ้ำ";
	}

	@Override
	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// ข้อความหลัก: ให้เฉพาะข้อความอธิบายใช้สีของข้อผิดพลาด
		String mensajeBase = "<span style='color:#" + color
				+ "'>ความขัดแย้งร้ายแรง: มีการพยายามลงทะเบียนวัตถุเดียวกันสองครั้ง " + "ม็อด </span>" + mod1
				+ "<span style='color:#" + color + "'> และ </span>" + mod2 + "<span style='color:#" + color
				+ "'> กำลังพยายามลงทะเบียนวัตถุเดียวกัน " + "วัตถุที่ขัดแย้งกันคือ: </span>" + objeto + "<br><br>";

		// คำแนะนำในการแก้ไข
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "โดยทั่วไปปัญหานี้เกิดขึ้นเมื่อม็อดสองตัวเพิ่มวัตถุที่มีชื่อเดียวกัน "
				+ "ภายใน namespace เดียวกัน หรือเมื่อมีข้อผิดพลาดในโค้ดของม็อดตัวใดตัวหนึ่ง<br><br>"
				+ "<b>วิธีแก้ไขที่แนะนำ:</b><br>" + "<ul>"
				+ "<li>ตรวจสอบว่าม็อดตัวใดตัวหนึ่งเป็นเวอร์ชันอัปเดตหรือเป็น fork ของอีกตัวหรือไม่</li>"
				+ "<li>ลองลบหนึ่งในสองม็อดที่ขัดแย้งกันออก</li>"
				+ "<li>ตรวจสอบไฟล์การตั้งค่าของม็อดทั้งสองตัว เพื่อดูว่าสามารถเปลี่ยน ID ของวัตถุได้หรือไม่</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreFalloFabricRenderingAPI() {
		return "ขาด Fabric Rendering API";
	}

	@Override
	public String mensajeFalloFabricRenderingAPI() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// ข้อความหลัก
		String mensajeBase = "<span style='color:#" + color
				+ "'>ม็อด (โดยทั่วไปคือ Porting Lib หรือม็อดที่เกี่ยวข้อง) ทำงานล้มเหลวเนื่องจาก </span>"
				+ "Fabric Rendering API<span style='color:#" + color + "'> ไม่พร้อมใช้งาน</span><br><br>";

		// คำแนะนำการแก้ไข (อัปเดตสำหรับเวอร์ชันใหม่ที่ Indium ล้าสมัยแล้ว)
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>แนวทางแก้ไขที่แนะนำ:</b><br>"
				+ "ข้อความอาจแนะนำให้ติดตั้ง Indium แต่ในเวอร์ชันใหม่ของเกม ม็อดนี้ล้าสมัยแล้ว<br>" + "<ul>"
				+ "<li><b>อัปเดต Sodium</b> เป็นเวอร์ชัน <b>0.6.0</b> หรือใหม่กว่า (เวอร์ชันนี้มีการรองรับที่จำเป็นอยู่แล้ว)</li>"
				+ "<li>ตรวจสอบให้แน่ใจว่าคุณได้ติดตั้ง <b>Fabric API</b> แล้ว</li>"
				+ "<li>หากคุณใช้ Minecraft เวอร์ชันเก่า (1.20.6 หรือต่ำกว่า) ให้ติดตั้ง Indium</li>" + "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRestriccionesDependenciaNoCumplidas() {
		return "ข้อจำกัดของ dependency ไม่เป็นไปตามเงื่อนไข";
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// ข้อความหลัก
		String mensajeBase = "<span style='color:#" + color + "'>พบ </span>" + cantidad + "<span style='color:#" + color
				+ "'> ข้อจำกัดของ dependency ที่ไม่เป็นไปตามเงื่อนไข</span><br><br>";

		// การสร้างรายการความขัดแย้ง
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictos != null && !conflictos.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>ตรวจพบความขัดแย้งในไฟล์ต่อไปนี้:</span><br><ul>");
			for (String[] par : conflictos) {
				String dep = par[0]; // dependency
				String jar = par[1]; // ไฟล์ JAR
				listaDetalle.append("<li>").append("<span style='color:#").append(color).append("'>ไฟล์: </span>")
						.append(jar).append("<br><span style='color:#").append(color).append("'>ต้องการ: </span>")
						.append(dep).append("</li>");
			}
			listaDetalle.append("</ul><br>");
		}

		// คำแนะนำการแก้ไข
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "ปัญหานี้เกิดขึ้นเมื่อม็อดสองตัวหรือมากกว่านั้นต้องการเวอร์ชันของไลบรารีเดียวกันที่ไม่เข้ากัน<br><br>"
				+ "<b>แนวทางแก้ไขที่แนะนำ:</b><br>" + "<ul>"
				+ "<li>ลองอัปเดตหรือลบม็อดที่ระบุด้านบนเพื่อแก้ไขความไม่เข้ากัน</li>"
				+ "<li>หากไม่พบเวอร์ชันที่เข้ากันได้ คุณสามารถลองแก้ไขไฟล์ <code>mods.toml</code> ภายในไฟล์ JAR ของม็อด "
				+ "(โดยใช้โปรแกรมอย่าง WinRAR หรือ 7-Zip) เพื่อเปลี่ยนหรือลบข้อจำกัดของเวอร์ชัน แต่การทำเช่นนี้อาจทำให้ระบบไม่เสถียร</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// ข้อความหลัก
		String mensajeBase = "<span style='color:#" + color + "'>พบ </span>" + cantidad + "<span style='color:#" + color
				+ "'> ข้อจำกัดของ dependency ที่ไม่เป็นไปตามเงื่อนไข</span><br><br>";

		// การสร้างรายการแบบจัดกลุ่มตามม็อด
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictosPorMod != null && !conflictosPorMod.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>ม็อดที่เกี่ยวข้องและ dependency ที่ร้องขอ:</span><br><ul>");

			for (Map.Entry<String, List<String>> entry : conflictosPorMod.entrySet()) {
				String archivo = entry.getKey();
				List<String> dependencias = entry.getValue();

				listaDetalle.append("<li><b>").append(archivo).append("</b>");

				listaDetalle.append("<ul>");
				for (String dep : dependencias) {
					listaDetalle.append("<li>").append(dep).append("</li>");
				}
				listaDetalle.append("</ul></li>");
			}
			listaDetalle.append("</ul><br>");
		} else {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>ไม่สามารถระบุไฟล์ที่เกี่ยวข้องจาก log ได้</span><br>");
		}

		// คำแนะนำการแก้ไข
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "ข้อผิดพลาดนี้เกิดขึ้นเมื่อม็อดมีไลบรารีภายใน (Jar-in-Jar) ที่ไม่เข้ากัน<br><br>"
				+ "<b>แนวทางแก้ไขที่แนะนำ:</b><br>" + "<ul>"
				+ "<li>ตรวจสอบรายการด้านบนเพื่อดูว่าม็อดใดต้องการเวอร์ชันของไลบรารีที่ต่างกัน</li>"
				+ "<li>ลองอัปเดตม็อดทั้งหมดเป็นเวอร์ชันล่าสุด</li>"
				+ "<li>เป็นทางเลือกสุดท้าย คุณสามารถเปิดไฟล์ <code>.jar</code> ของม็อดด้วยโปรแกรมบีบอัด "
				+ "(เช่น WinRAR) แล้วแก้ไข <code>META-INF/mods.toml</code> เพื่อเปลี่ยนช่วงเวอร์ชันของ dependency "
				+ "แต่มีความเสี่ยงและอาจทำให้ม็อดใช้งานไม่ได้</li>" + "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String nombreNeruinaOcultaAdvertencia() {
		return "Neruina ขัดขวางการดีบัก";
	}

	@Override
	public String mensajeNeruinaOcultaAdvertencia() {
		String color = Config.obtenerInstancia().obtenerColorAdvertencia();

		// คำเตือนหลัก
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>คำเตือน:</b> ม็อด <b>Neruina</b> ล้มเหลวในการจัดการข้อผิดพลาด ทำให้สาเหตุที่แท้จริงของ crash ถูกซ่อนอยู่</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "ม็อด Neruina มักไม่จำเป็น และทำให้การวิเคราะห์ปัญหาที่แท้จริงยากขึ้น แนะนำให้ลบออกเพื่อทำการดีบัก</span><br><br>";

		// คำแนะนำในการกู้คืน
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>ขั้นตอนการกู้คืน:</b><br>"
				+ "1. **MCForge**: ไปที่ '[nombre_del_mundo]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: ไปที่ 'config/neoforge-server.toml'.<br>"
				+ "   *(หมายเหตุ: ในโหมดเล่นคนเดียว (Singleplayer) โลกจะอยู่ในโฟลเดอร์ 'saves')*.<br>"
				+ "3. ตั้งค่า **removeErroringBlockEntities** และ **removeErroringEntities** เป็น **true**.<br><br>"
				+ "<b>ตัวเลือกเพิ่มเติม:</b><br>"
				+ "- **MCEdit**: ใช้เพื่อลบเอนทิตีที่มีปัญหาด้วยตนเองตามพิกัดที่ระบุ<br>"
				+ "- หากข้อผิดพลาดนี้ยังคงเกิดขึ้น อาจเป็นไปได้ว่า Neruina ทำงานผิดพลาดและสร้างข้อผิดพลาดใหม่แทน"
				+ "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreApothicAttributeSinDueño() {
		return "ข้อผิดพลาด Apothic Attributes";
	}

	@Override
	public String mensajeApothicAttributeSinDueño(boolean chestCavityDetectado) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// ข้อความหลัก
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Apothic Attributes</b> ตรวจพบความขัดแย้ง: มีการแก้ไข <b>AttributeMap</b> โดยไม่มีเจ้าของกำหนดไว้</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "ปัญหานี้มักเกิดขึ้นเมื่อม็อดพยายามแก้ไขค่าคุณสมบัติของเอนทิตี (เช่น พลังชีวิต ความเสียหาย ความเร็ว) "
				+ "ในช่วงเวลาที่ไม่เหมาะสม หรือทำในลักษณะที่ไม่ถูกต้อง</span><br><br>";

		// หมายเหตุเกี่ยวกับ Chest Cavity
		String notaChestCavity = "";
		if (chestCavityDetectado) {
			notaChestCavity = "<span style='color:#" + color + "'>" + "<b>ตรวจพบม็อด Chest Cavity ใน log</b> "
					+ "ม็อดนี้เป็นสาเหตุที่พบบ่อยของข้อผิดพลาดประเภทนี้ เนื่องจากวิธีที่มันจัดการกับ attribute ของเอนทิตี</span><br><br>";
		}

		// คำแนะนำในการแก้ไข
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>แนวทางแก้ไขที่แนะนำ:</b><br>" + "<ul>"
				+ "<li>หากติดตั้ง Chest Cavity ให้ลองอัปเดตหรือถอดออกชั่วคราวเพื่อตรวจสอบว่าเป็นสาเหตุหรือไม่</li>"
				+ "<li>ตรวจสอบม็อดอื่นที่แก้ไข attribute ของ mobs และลองปิดใช้งาน</li>"
				+ "<li>ตรวจสอบว่ามีเวอร์ชันใหม่ของ <b>Apothic Attributes</b> หรือไม่ เนื่องจากอาจเป็นข้อผิดพลาดที่ถูกแก้ไขแล้ว</li>"
				+ "</ul></span>";

		return mensajeBase + notaChestCavity + instrucciones;
	}

	@Override
	public String nombreErrorPotBlockEntity() {
		return "ข้อผิดพลาด DecoratedPot (Cataclysm)";
	}

	@Override
	public String mensajeErrorPotBlockEntity() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// ข้อความหลัก
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "ตรวจพบข้อผิดพลาดด้านความเข้ากันได้กับ <b>DecoratedPotBlockEntity</b></span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "นี่เป็นปัญหาที่ทราบกันดีในม็อด <b>L_Enders_Cataclysm</b> เวอร์ชัน 1.19.2 "
				+ "ซึ่งขาดการติดตั้งส่วนที่เกมต้องใช้</span><br><br>";

		// วิธีแก้ไข
		String solucion = "<span style='color:#" + color + "'>" + "<b>วิธีแก้ไขที่แนะนำ:</b><br>"
				+ "ติดตั้งม็อด <b>PotFix (Cataclysm Patch)</b> เพื่อแก้ไขข้อผิดพลาดนี้<br>"
				+ "คุณสามารถดาวน์โหลดได้ที่นี่: <a href='https://www.curseforge.com/minecraft/mc-mods/potfix-cataclysm-patch'>CurseForge - PotFix</a>"
				+ "</span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorPreloadingTricks() {
		return "ข้อผิดพลาด Preloading Tricks";
	}

	@Override
	public String mensajeErrorPreloadingTricks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// ข้อความหลัก
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "ตรวจพบความขัดแย้งที่เกิดจาก <b>Preloading Tricks</b></span><br><br>" + "<span style='color:#" + color
				+ "'>" + "ข้อผิดพลาด <i>ClassCastException: String cannot be cast to ModuleDescriptor</i> "
				+ "บ่งชี้ว่าม็อดนี้กำลังจัดการคลาสของระบบโมดูล Java อย่างไม่ถูกต้อง</span><br><br>";

		// คำอธิบายและวิธีแก้
		String explicacion = "<span style='color:#" + color + "'>"
				+ "<b>Preloading Tricks</b> เป็นม็อดที่ออกแบบมาสำหรับ <b>นักพัฒนา</b> เป็นหลัก "
				+ "โดยมันทำงานแก้ไขคลาส (mixins) ที่ซับซ้อนมากในช่วงต้นของการโหลดเกม "
				+ "ซึ่งอาจทำให้ระบบไม่เสถียรได้ง่ายหากมีการโต้ตอบกับอย่างอื่น</span><br><br>" + "<span style='color:#"
				+ color + "'><b>วิธีแก้ไขที่แนะนำ:</b><br>" + "<ul>"
				+ "<li>ลบม็อด <b>Preloading Tricks</b> ออก โดยทั่วไปไม่จำเป็นสำหรับการเล่นบนเซิร์ฟเวอร์สาธารณะหรือ modpack ที่เสถียร</li>"
				+ "<li>หากคุณเป็นนักพัฒนาและจำเป็นต้องใช้สำหรับการทดสอบ ให้ตรวจสอบการตั้งค่าสภาพแวดล้อมของคุณ</li>"
				+ "</ul></span>";

		return mensajeBase + explicacion;
	}

	@Override
	public String nombreErrorSimpleRadioLexiconfig() {
		return "ความไม่เข้ากันของ Simple Radio / Lexiconfig";
	}

	@Override
	public String mensajeErrorSimpleRadioLexiconfig() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// ข้อความหลัก
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "ตรวจพบความขัดแย้งระหว่าง <b>Simple Radio</b> และ <b>Lexiconfig</b></span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "ข้อผิดพลาดเกิดขึ้นระหว่างกระบวนการ 'shelveLexicons' ซึ่งบ่งชี้ว่ามีความไม่เข้ากันในระดับไบนารีระหว่างไลบรารีทั้งสอง</span><br><br>";

		// วิธีแก้เฉพาะ
		String solucion = "<span style='color:#" + color + "'>" + "<b>สาเหตุที่ทราบ:</b><br>"
				+ "โดยทั่วไป Simple Radio มักถูกออกแบบมาสำหรับ Lexiconfig เวอร์ชันเก่า ขณะที่คุณติดตั้งเวอร์ชันใหม่กว่าอยู่</span><br><br>"
				+ "<span style='color:#" + color + "'><b>วิธีแก้ไขที่แนะนำ:</b><br>" + "<ul>"
				+ "<li>ลองใช้ <b>Lexiconfig</b> เวอร์ชันเก่ากว่า</li>"
				+ "<li>แนะนำให้ลองเวอร์ชัน <b>1.3.11</b> หรือต่ำกว่า ซึ่งมักเข้ากันได้กับ Simple Radio</li>"
				+ "<li>หากปัญหายังคงอยู่ ให้ตรวจสอบว่ามีการอัปเดตของ Simple Radio หรือไม่</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorMobAITweaks() {
		return "ข้อผิดพลาด Mob AI Tweaks";
	}

	@Override
	public String mensajeErrorMobAITweaks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// ข้อความหลัก
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "ตรวจพบข้อผิดพลาดที่เกี่ยวข้องกับ <b>Mob AI Tweaks</b></span><br><br>" + "<span style='color:#"
				+ color + "'>" + "ข้อผิดพลาดนี้มาจาก Mixin (<code>$mob-ai-tweaks$onSpawned</code>) ที่เข้าไปแทรกแซง "
				+ "ในเวลาที่เอนทิตีปรากฏตัว (spawn) ซึ่งมักบ่งชี้ว่ามีความขัดแย้งกับม็อดอื่น "
				+ "ที่แก้ไขพฤติกรรมการเกิดของ mobs เช่นกัน</span><br><br>";

		// วิธีแก้ไข
		String solucion = "<span style='color:#" + color + "'><b>วิธีแก้ไขที่แนะนำ:</b><br>" + "<ul>"
				+ "<li>ลองลบ <b>Mob AI Tweaks</b> ออก เพื่อตรวจสอบว่าความไม่เสถียรหายไปหรือไม่</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	public String nombre_verificacion_gpu() {
		return "การตรวจสอบ GPU (OpenGL / การเลือก GPU)";
	}

	public String desactivar_parche_gpu() {
		return "ปิดใช้งานการตรวจสอบ GPU";
	}

	// ==================== CRASH ====================

	public String gpu_crash_posible() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ตัวตรวจสอบ GPU อาจเป็นสาเหตุที่ทำให้เกมปิดตัวลง</b>";
	}

	public String gpu_crash_causas() {
		return "การตรวจสอบเริ่มขึ้นแล้วแต่ไม่เสร็จสมบูรณ์ ซึ่งมักบ่งชี้ว่ามีความล้มเหลวใน OpenGL หรือไดรเวอร์กราฟิก<br><br>"
				+ "สาเหตุที่เป็นไปได้:<br>" + "- ไดรเวอร์ล้าสมัยหรือไม่เสถียร<br>" + "- ปัญหาเกี่ยวกับ OpenGL<br>"
				+ "- GPU รุ่นเก่าหรือการตั้งค่าแบบไฮบริด";
	}

	public String gpu_crash_recomendaciones() {
		return "คำแนะนำ:<br>" + "- อัปเดตไดรเวอร์ของ GPU<br>" + "- บังคับให้ใช้ GPU แยก<br>"
				+ "- หลีกเลี่ยงสภาพแวดล้อมระยะไกลหรือระบบเสมือน";
	}

	// ==================== NO ÓPTIMA ====================

	public String gpu_no_optima() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>เกมไม่ได้ใช้ GPU ที่ดีที่สุดที่มีอยู่</b>";
	}

	public String gpu_no_optima_detalles() {
		return "สิ่งนี้อาจลดประสิทธิภาพ (FPS ต่ำ) แต่โดยปกติแล้วจะไม่ทำให้เกิด crash ด้วยตัวมันเอง";
	}

	public String gpu_recomendaciones_rendimiento() {
		return "คำแนะนำ:<br>" + "- บังคับใช้ GPU แยกในแผงควบคุม<br>"
				+ "- ตั้งค่า Java/Minecraft เป็นโหมดประสิทธิภาพสูง";
	}

	// ==================== GENERALES ====================

	public String gpu_nota_precision() {
		return "<b>หมายเหตุ:</b> ระบบตรวจจับนี้ไม่ได้แม่นยำ 100%";
	}

	public String gpu_consumo_energia() {
		return "GPU ที่แรงกว่าจะใช้พลังงานมากกว่า และอาจทำให้อายุแบตเตอรี่ของแล็ปท็อปลดลง";
	}

	public String gpu_parche_info() {
		return "คุณสามารถปิดการตรวจสอบนี้ได้โดยใช้ปุ่มแก้ไขด่วน";
	}

	@Override
	public String nombreVerificacionRaptorLake() {
		return "คำเตือนความเสถียรของ CPU Intel รุ่นที่ 13/14";
	}

	@Override
	public String advertenciaRaptorLakeTitulo() {
		return "อาจมีความไม่เสถียรในโปรเซสเซอร์ Intel Raptor Lake";
	}

	@Override
	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String targetMicrocode) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "ตรวจพบโปรเซสเซอร์ " + cpu
				+ " ที่ใช้ไมโครโค้ด " + microcode + "." + "</b> "
				+ "โปรเซสเซอร์ Intel รุ่นที่ 13 และ 14 มีรายงานปัญหาความไม่เสถียรจากการขอแรงดันไฟฟ้ามากเกินไป "
				+ "ซึ่งอาจทำให้อายุการใช้งานของโปรเซสเซอร์สั้นลง<br><br>"
				+ "แนะนำให้อัปเดตไมโครโค้ดหรือ BIOS ของเมนบอร์ดเป็นเวอร์ชันที่มีไมโครโค้ด <b>" + targetMicrocode
				+ "</b> หรือใหม่กว่า " + "<b>คำเตือน:</b> การอัปเดต BIOS มีความเสี่ยงหากทำไม่ถูกต้อง<br><br>"
				+ "<i>หมายเหตุ: แทบจะแน่นอนว่านี่ไม่ใช่สาเหตุของ crash ปัจจุบันของคุณ แต่เป็นเพียงคำเตือนด้านสุขภาพของฮาร์ดแวร์</i>";
	}

	@Override
	public String desactivarVerificacionRaptorLake() {
		return "อย่าเตือนฉันเรื่องนี้อีก";
	}

	@Override
	public String verArticuloRaptorLake(String fuente) {
		return "อ่านบทความใน " + fuente;
	}

	@Override
	public String tituloMixins() {
		return "ตัวสำรวจ Mixins";
	}

	@Override
	public String mixinsBotonLateral() {
		return "Mixins";
	}

	@Override
	public String mixinsRaiz() {
		return "Mixins ที่พบ";
	}

	@Override
	public String mixinsTodosLosMods() {
		return "ทั้งหมด";
	}

	@Override
	public String mixinsModConMixin() {
		return "ม็อดที่มี mixins";
	}

	@Override
	public String mixinsTooltipCombo() {
		return "กรองตามม็อดที่มี mixins";
	}

	@Override
	public String mixinsRecargar() {
		return "โหลดใหม่";
	}

	@Override
	public String mixinsDescompilarSeleccion() {
		return "ดีคอมไพล์ส่วนที่เลือก";
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
		return "Targets ของเมธอด";
	}

	@Override
	public String mixinsMetodos() {
		return "เมธอด";
	}

	@Override
	public String mixinsCampos() {
		return "ฟิลด์";
	}

	@Override
	public String mixinsCantidad() {
		return "จำนวน mixins";
	}

	@Override
	public String mixinsDetalleMixin() {
		return "รายละเอียดของ mixin";
	}

	@Override
	public String mixinsDetalleTarget() {
		return "รายละเอียดของ target";
	}

	@Override
	public String mixinsDetalleMetodo() {
		return "รายละเอียดของเมธอด mixin";
	}

	@Override
	public String mixinsDetalleCampo() {
		return "รายละเอียดของฟิลด์ mixin";
	}

	@Override
	public String mixinsDetalleConflicto() {
		return "รายละเอียดของความขัดแย้ง";
	}

	@Override
	public String mixinsBuscarConflictos() {
		return "ค้นหาความขัดแย้งที่เป็นไปได้";
	}

	@Override
	public String mixinsResultadosConflictos() {
		return "ผลลัพธ์ของความขัดแย้ง";
	}

	@Override
	public String mixinsConflictosPosibles() {
		return "ความขัดแย้งที่เป็นไปได้";
	}

	@Override
	public String mixinsErrorDescompilar() {
		return "เกิดข้อผิดพลาดขณะดีคอมไพล์";
	}

	@Override
	public String mixinsColorPanel() {
		return "สีของแผง mixins";
	}

	@Override
	public String mixinsColorTexto() {
		return "สีข้อความของ mixins";
	}

	@Override
	public String mixinsColorTextoSuave() {
		return "สีข้อความรองของ mixins";
	}

	@Override
	public String mixinsAyudaUso1() {
		return "เครื่องมือนี้แสดงม็อดที่มี mixins ของ SpongePowered และช่วยให้ตรวจสอบคลาส เป้าหมาย เมธอด และฟิลด์ของพวกมันได้";
	}

	@Override
	public String mixinsAyudaUso2() {
		return "ใช้ตัวเลือกด้านบนเพื่อกรองตามม็อดเฉพาะ หรือแสดงม็อดทั้งหมดที่มี mixins";
	}

	@Override
	public String mixinsAyudaUso3() {
		return "ขยายโครงสร้างต้นไม้เพื่อดู mixin แต่ละตัว คลาสเป้าหมาย เมธอดที่มี annotation และฟิลด์ shadow";
	}

	@Override
	public String mixinsAyudaUso4() {
		return "คลิกขวาที่ม็อด mixin target เมธอด หรือฟิลด์ เพื่อค้นหาความขัดแย้งที่เป็นไปได้กับ mixins อื่น";
	}

	@Override
	public String mixinsAyudaUso5() {
		return "คุณสามารถดีคอมไพล์สิ่งที่เลือกหรือผลลัพธ์ของความขัดแย้งเพื่อดูโค้ดที่เกี่ยวข้องได้";
	}

	@Override
	public String mixinsColorComboFondo() {
		return "สีพื้นหลังของตัวเลือกม็อด";
	}

	@Override
	public String mixinsColorAreaContenidoFondo() {
		return "สีพื้นหลังของแผงรายละเอียด";
	}

	@Override
	public String mixinsColorSeleccionTexto() {
		return "สีการเลือกข้อความ";
	}

	@Override
	public String mixinsColorSeleccionTextoActivo() {
		return "สีของข้อความที่ถูกเลือก";
	}

	@Override
	public String mixinsColorAyudaTexto() {
		return "สีของข้อความช่วยเหลือ";
	}

	@Override
	public String mixinsColorArbolFondo() {
		return "สีพื้นหลังของโครงสร้างต้นไม้";
	}

	@Override
	public String mixinsColorRendererSeleccionTexto() {
		return "สีข้อความที่เลือกในต้นไม้";
	}

	@Override
	public String mixinsColorRendererSeleccionFondo() {
		return "สีพื้นหลังที่เลือกในต้นไม้";
	}

	@Override
	public String mixinsColorRendererBordeSeleccion() {
		return "สีเส้นขอบการเลือกในต้นไม้";
	}

	@Override
	public String depmapTitulo() {
		return "แผนที่การพึ่งพา";
	}

	@Override
	public String depmapBotonLateral() {
		return "DepMap";
	}

	@Override
	public String depmapPestanaMapa() {
		return "แผนที่";
	}

	@Override
	public String depmapPestanaDependientes() {
		return "ตัวที่พึ่งพา";
	}

	@Override
	public String depmapRecargar() {
		return "โหลดใหม่";
	}

	@Override
	public String depmapDescompilarSeleccion() {
		return "ดีคอมไพล์ส่วนที่เลือก";
	}

	@Override
	public String depmapVerReferencias() {
		return "ดูการอ้างอิง";
	}

	@Override
	public String depmapDependencias() {
		return "การพึ่งพา";
	}

	@Override
	public String depmapDependientes() {
		return "ตัวที่พึ่งพา";
	}

	@Override
	public String depmapDependiente() {
		return "ตัวที่พึ่งพา";
	}

	@Override
	public String depmapSinDependencias() {
		return "ไม่มีตัวที่พึ่งพา";
	}

	@Override
	public String depmapSeleccionarMod() {
		return "เลือกม็อด";
	}

	@Override
	public String depmapSeleccionarModBase() {
		return "ม็อดหลัก";
	}

	@Override
	public String depmapSeleccionarDependiente() {
		return "ม็อดที่พึ่งพา";
	}

	@Override
	public String depmapSeleccionarPaquete() {
		return "แพ็กเกจ";
	}

	@Override
	public String depmapComprobarNoAlineadas() {
		return "ตรวจสอบความไม่สอดคล้อง";
	}

	@Override
	public String depmapResultadoNoAlineadas() {
		return "ผลลัพธ์ของการพึ่งพาที่ไม่สอดคล้อง";
	}

	@Override
	public String depmapClaseInexistente() {
		return "คลาสที่ไม่มีอยู่";
	}

	@Override
	public String depmapClaseReferenciada() {
		return "คลาสที่ถูกอ้างอิง";
	}

	@Override
	public String depmapOrigen() {
		return "ต้นทาง";
	}

	@Override
	public String depmapDestino() {
		return "ปลายทาง";
	}

	@Override
	public String depmapDependenciaDetalle() {
		return "รายละเอียดของการพึ่งพา";
	}

	@Override
	public String depmapReferenciaDetalle() {
		return "รายละเอียดของการอ้างอิง";
	}

	@Override
	public String depmapMetodoOrigen() {
		return "เมธอดต้นทาง";
	}

	@Override
	public String depmapModBase() {
		return "ม็อดหลัก";
	}

	@Override
	public String depmapTodos() {
		return "ทั้งหมด";
	}

	@Override
	public String depmapSeleccioneUnMod() {
		return "เลือกม็อด";
	}

	@Override
	public String depmapSeleccioneParametrosNoAlineadas() {
		return "เลือกม็อดหลัก ม็อดที่พึ่งพา และแพ็กเกจ";
	}

	@Override
	public String depmapSeleccioneClaseParaDescompilar() {
		return "เลือกการอ้างอิงหรือรายการเพื่อดีคอมไพล์";
	}

	@Override
	public String depmapErrorDescompilar() {
		return "เกิดข้อผิดพลาดขณะดีคอมไพล์";
	}

	@Override
	public String depmapAyuda1() {
		return "เครื่องมือนี้สร้างแผนที่การพึ่งพาระหว่างม็อดโดยใช้การอ้างอิงคลาสระหว่างกัน";
	}

	@Override
	public String depmapAyuda2() {
		return "แท็บแผนที่จะแสดงกราฟแบบฟองที่เชื่อมม็อดแต่ละตัวกับการพึ่งพาที่ใช้";
	}

	@Override
	public String depmapAyuda3() {
		return "แท็บตัวที่พึ่งพาจะจัดเรียงม็อดจากตัวที่มีการพึ่งพามากที่สุดไปหาน้อยที่สุด";
	}

	@Override
	public String depmapAyuda4() {
		return "คุณสามารถตรวจสอบการอ้างอิงเฉพาะระหว่างม็อดและการพึ่งพา รวมถึงดีคอมไพล์คลาสที่เกี่ยวข้อง";
	}

	@Override
	public String depmapAyuda5() {
		return "การตรวจสอบความไม่สอดคล้องจะค้นหาการอ้างอิงไปยังคลาสที่ไม่มีอยู่ภายในแพ็กเกจหรือแพ็กเกจย่อยของม็อดหลัก";
	}

	@Override
	public String depmapColorPanel() {
		return "สีของแผง";
	}

	@Override
	public String depmapColorTexto() {
		return "สีข้อความหลัก";
	}

	@Override
	public String depmapColorTextoSecundario() {
		return "สีข้อความรอง";
	}

	@Override
	public String depmapColorAyudaTexto() {
		return "สีของข้อความช่วยเหลือ";
	}

	@Override
	public String depmapColorGrafoFondo() {
		return "สีพื้นหลังของกราฟ";
	}

	@Override
	public String depmapColorListaFondo() {
		return "สีพื้นหลังของรายการ";
	}

	@Override
	public String depmapColorArbolFondo() {
		return "สีพื้นหลังของต้นไม้";
	}

	@Override
	public String depmapColorNodo() {
		return "สีของโหนดในกราฟ";
	}

	@Override
	public String depmapColorEnlace() {
		return "สีของเส้นเชื่อมในกราฟ";
	}

	@Override
	public String depmapColorSeleccion() {
		return "สีของการเลือก";
	}

	@Override
	public String depmapColorSeleccionTexto() {
		return "สีของข้อความที่ถูกเลือก";
	}

	@Override
	public String nombreProblemaAzureLibAnimaciones() {
		return "ปัญหากับแอดออน AzureLib";
	}

	@Override
	public String mensajeProblemaAzureLibAnimaciones() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดของ AzureLib ขณะโหลดแอนิเมชัน</b>"
				+ "<p>เกมพบไฟล์ JSON ของแอนิเมชันที่มีรูปแบบไม่ถูกต้อง</p>"
				+ "<p>ปัญหานี้มักเกิดจากม็อดหรือแอดออนที่ใช้ <b>AzureLib</b></p>" + "<p><b>คำแนะนำ:</b></p>" + "<ul>"
				+ "<li>ใช้ <b>DepMap</b> ในแถบด้านข้างเพื่อค้นหาแอดออนทั้งหมดที่พึ่งพา AzureLib</li>"
				+ "<li>ลองรันเกมโดยปิดแอดออนบางตัวจนกว่าจะพบตัวที่มีปัญหา</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaDecocraftNatureEssentialPartnerMod() {
		return "ปัญหากับ Decocraft Nature";
	}

	@Override
	public String mensajeProblemaDecocraftNatureEssentialPartnerMod() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบปัญหาที่เกิดจาก Decocraft Nature</b>" + "<p>ข้อผิดพลาดเกิดขึ้นขณะเริ่มต้น mixin config "
				+ "<code>com/razz/essentialpartnerMod/mixins.json</code></p>"
				+ "<p>ปัญหานี้สามารถแก้ได้โดยแก้ไขไฟล์ JAR ของม็อด</p>" + "<p><b>ขั้นตอน:</b></p>" + "<ul>"
				+ "<li>เปิดไฟล์ JAR ด้วยโปรแกรมจัดการไฟล์ เช่น File Roller, Ark, WinRAR, 7-Zip หรือ WinZip</li>"
				+ "<li>ไปที่ <code>META-INF/MANIFEST.MF</code></li>" + "<li>ลบบรรทัดนี้:</li>" + "</ul>"
				+ "<code>MixinConfigs: com/razz/essentialpartnerMod/mixins.json</code>"
				+ "<p><b>สำคัญ:</b> ให้คงบรรทัดว่างหนึ่งบรรทัดไว้ท้ายไฟล์</p>";
	}

	@Override
	public String mensajeTetraDeserializadorModeloEstatico() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดใน Tetra หรือแอดออนของมัน</b>"
				+ "<p>บันทึกแสดงว่าไม่พบ deserializer สำหรับประเภทโมเดลที่ใช้โดย <b>Tetra</b> หรือส่วนเสริมของมัน</p>"
				+ "<p><b>ขั้นตอนที่แนะนำ:</b></p>" + "<ul>"
				+ "<li>เริ่มจากลบหรือปิดใช้งาน <b>addons ของ Tetra</b> แล้วลองใหม่</li>"
				+ "<li>หากข้อผิดพลาดยังคงอยู่ ให้ลองลบ <b>Tetra</b> ด้วย</li>"
				+ "<li>คุณสามารถใช้ <b>DepMap</b> เพื่อตรวจหาแอดออนที่เกี่ยวข้อง แม้ว่าอาจไม่แสดงทั้งหมด</li>" + "</ul>"
				+ "<p>บางกรณีปัญหาเกิดจากแอดออน แต่บางครั้งก็เกิดจาก <b>Tetra</b> เอง</p>";
	}

	@Override
	public String nombreTetraDeserializadorModeloEstatico() {
		return "ข้อผิดพลาดการ deserialization ของโมเดลใน Tetra";
	}

	@Override
	public String mensajeSimpleEmotesSetupAnimTail() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดใน Simple Emotes</b>"
				+ "<p>บันทึกมีข้อความ <b>$simpleemotes$setupAnimTAIL</b> ซึ่งชี้ไปยังม็อด <b>Simple Emotes</b> โดยตรง</p>"
				+ "<p><b>ตัวเลือกที่แนะนำ:</b></p>" + "<ul>"
				+ "<li>ลบหรือปิดใช้งาน <b>Simple Emotes</b> แล้วลองใหม่</li>"
				+ "<li>หากคุณใช้ม็อดที่เปลี่ยนแอนิเมชันผู้เล่นหรือโมเดล ให้ตรวจสอบความเข้ากันได้กับ <b>Simple Emotes</b></li>"
				+ "<li>อัปเดต <b>Simple Emotes</b> และม็อดที่เกี่ยวข้องกับแอนิเมชันเป็นเวอร์ชันที่เข้ากันได้</li>"
				+ "</ul>" + "<p>ข้อผิดพลาดนี้มักบ่งชี้ว่า <b>Simple Emotes</b> มีส่วนเกี่ยวข้องโดยตรงกับปัญหา</p>";
	}

	@Override
	public String nombreSimpleEmotesSetupAnimTail() {
		return "ข้อผิดพลาดใน Simple Emotes";
	}

	// En Idioma

	@Override
	public String mensajeAdvertenciaSKLauncher() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "คำเตือนเกี่ยวกับ SKLauncher</b>"
				+ "<p>ในช่วงไม่กี่เดือนที่ผ่านมา พบกรณีของ <b>ความเสียหายของข้อมูล</b> และปัญหาอื่น ๆ ที่เกี่ยวข้องกับ <b>SKLauncher</b></p>"
				+ "<p>ไม่ได้หมายความว่า SKLauncher เป็นสาเหตุของข้อผิดพลาดเสมอไป แต่อาจมีส่วนทำให้เกิดปัญหา</p>"
				+ "<p><b>สัญญาณของความเสียหายที่เป็นไปได้:</b></p>" + "<ul>" + "<li>เกมปิดตัวเร็วมากในช่วงเริ่มต้น</li>"
				+ "<li>เกมยังคงล้มเหลวแม้ไม่มีม็อดติดตั้ง</li>" + "</ul>"
				+ "<p>หากพบอาการเหล่านี้ ลองใช้ <b>launcher อื่น</b> เพื่อตรวจสอบว่าปัญหาหายไปหรือไม่</p>"
				+ "<p>ดูรายการ launcher ที่แนะนำได้ที่นี่:</p>"
				+ "<p><a href='https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/docs/ingles/minecraft/Launchers.md'>ดูเอกสาร launcher</a></p>";
	}

	@Override
	public String nombreAdvertenciaSKLauncher() {
		return "คำเตือน: อาจมีปัญหากับ SKLauncher";
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
		return "สแกนเซิร์ฟเวอร์และมัลแวร์";
	}

	@Override
	public String guardEscanearServidores() {
		return "ค้นหาเซิร์ฟเวอร์";
	}

	@Override
	public String guardEscanearMalware() {
		return "ค้นหามัลแวร์";
	}

	@Override
	public String guardTablaServidores() {
		return "เซิร์ฟเวอร์ที่มีปัญหา";
	}

	@Override
	public String guardTablaMalware() {
		return "ผลการตรวจพบมัลแวร์";
	}

	@Override
	public String guardColServidor() {
		return "เซิร์ฟเวอร์";
	}

	@Override
	public String guardColDefinicion() {
		return "คำจำกัดความ";
	}

	@Override
	public String guardColMensaje() {
		return "ข้อความ";
	}

	@Override
	public String guardColUbicacion() {
		return "ตำแหน่ง";
	}

	@Override
	public String guardColClase() {
		return "คลาส";
	}

	@Override
	public String guardColCfr() {
		return "CFR";
	}

	@Override
	public String guardCfr() {
		return "ดีคอมไพล์";
	}

	@Override
	public String guardCfrTitulo() {
		return "โค้ดที่ถูกดีคอมไพล์";
	}

	@Override
	public String guardDescripcion1() {
		return "เครื่องมือนี้ช่วยค้นหาเซิร์ฟเวอร์ที่มีปัญหาและการตรวจพบมัลแวร์ที่เป็นไปได้ในม็อด";
	}

	@Override
	public String guardDescripcion2() {
		return "อาจมีผลบวกเท็จ โดยเฉพาะเมื่อใช้คำจำกัดความหรือเครื่องสแกนมัลแวร์ที่เข้มงวด";
	}

	@Override
	public String guardDescripcion3() {
		return "การตรวจสอบเซิร์ฟเวอร์ใช้คำจำกัดความภายนอก หากคุณยังไม่ได้ดาวน์โหลด จะต้องดาวน์โหลดก่อน";
	}

	@Override
	public String guardDescripcion4() {
		return "หากคุณมีคำจำกัดความในเครื่องอยู่แล้ว เครื่องมือจะให้คุณเลือกว่าจะใช้ของเดิมหรืออัปเดต";
	}

	@Override
	public String guardDescripcion5() {
		return "ในตารางมัลแวร์ หากมีคลาสให้ใช้งาน คุณสามารถดีคอมไพล์ด้วย CFR เพื่อตรวจสอบได้";
	}

	@Override
	public String guardEstadoEscaneandoTodo() {
		return "กำลังสแกนเซิร์ฟเวอร์และมัลแวร์...";
	}

	@Override
	public String guardEstadoEscaneandoServidores() {
		return "กำลังค้นหาเซิร์ฟเวอร์ที่มีปัญหา...";
	}

	@Override
	public String guardEstadoEscaneandoMalware() {
		return "กำลังค้นหามัลแวร์...";
	}

	@Override
	public String guardEstadoListo() {
		return "พร้อมใช้งาน";
	}

	@Override
	public String guardDefsNoEncontradasTitulo() {
		return "ไม่พบคำจำกัดความ";
	}

	@Override
	public String guardDefsNoEncontradasMensaje() {
		return "ไม่พบคำจำกัดความของเซิร์ฟเวอร์ คุณต้องการดาวน์โหลดตอนนี้หรือไม่?";
	}

	@Override
	public String guardDefsDescargar() {
		return "ดาวน์โหลด";
	}

	@Override
	public String guardDefsCancelar() {
		return "ยกเลิก";
	}

	@Override
	public String guardDefsActualizarTitulo() {
		return "คำจำกัดความของเซิร์ฟเวอร์";
	}

	@Override
	public String guardDefsActualizarMensaje() {
		return "มีคำจำกัดความในเครื่องอยู่แล้ว คุณต้องการใช้ตามเดิมหรืออัปเดต?";
	}

	@Override
	public String guardDefsUsarLocales() {
		return "ใช้ไฟล์ในเครื่อง";
	}

	@Override
	public String guardDefsActualizar() {
		return "อัปเดต";
	}

	@Override
	public String guardFuenteDefinicionesTLauncher() {
		return "รายการของ TLauncher";
	}

	@Override
	public String guardErrorDescompilar() {
		return "เกิดข้อผิดพลาดขณะดีคอมไพล์";
	}

	@Override
	public String guardColorPanel() {
		return "สีของแผง";
	}

	@Override
	public String guardColorTexto() {
		return "สีของข้อความ";
	}

	@Override
	public String guardColorTextoSecundario() {
		return "สีข้อความรอง";
	}

	@Override
	public String guardColorTabla() {
		return "สีของตาราง";
	}

	@Override
	public String guardColorSeleccion() {
		return "สีของการเลือก";
	}

	@Override
	public String guardColorSeleccionTexto() {
		return "สีของข้อความที่ถูกเลือก";
	}

	@Override
	public String texto_de_boton_compartir_instancia_modpack() {
		return "แชร์อินสแตนซ์/ม็อดแพ็ก";
	}

	@Override
	public String popup_compartir_instancia_modpack() {
		return "ฟังก์ชันสำหรับแชร์อินสแตนซ์หรือม็อดแพ็กยังไม่ได้ถูกติดตั้งใช้งาน";
	}

	@Override
	public String colorBotonCompartirVerdeOscuro() {
		return "สีของปุ่มแชร์หลัก";
	}

	@Override
	public String colorBotonCompartirVerdeClaro() {
		return "สีของปุ่มแชร์ลิงก์";
	}

	@Override
	public String colorTextoBotonesCompartir() {
		return "สีข้อความของปุ่มแชร์";
	}

	@Override
	public String compartirInstanciaTitulo() {
		return "แชร์อินสแตนซ์";
	}

	@Override
	public String compartirInstanciaBotonLateral() {
		return "แชร์อินสแตนซ์";
	}

	@Override
	public String compartirInstanciaFormato() {
		return "รูปแบบ";
	}

	@Override
	public String compartirInstanciaServicio() {
		return "บริการอัปโหลด";
	}

	@Override
	public String compartirInstanciaBotonCompartir() {
		return "แพ็กและแชร์";
	}

	@Override
	public String compartirInstanciaBotonRefrescar() {
		return "รีเฟรช";
	}

	@Override
	public String compartirInstanciaEstadoListo() {
		return "พร้อม";
	}

	@Override
	public String compartirInstanciaEstadoEmpaquetando() {
		return "กำลังแพ็กสิ่งที่เลือก...";
	}

	@Override
	public String compartirInstanciaEstadoSubiendo() {
		return "กำลังอัปโหลดไฟล์...";
	}

	@Override
	public String compartirInstanciaEstadoError() {
		return "ข้อผิดพลาด";
	}

	@Override
	public String compartirInstanciaCodigo() {
		return "รหัส";
	}

	@Override
	public String compartirInstanciaEnlace() {
		return "ลิงก์";
	}

	@Override
	public String compartirInstanciaMantenerAbierto() {
		return "คุณต้องเปิดแอปพลิเคชันไว้เพื่อให้การโอนยังคงพร้อมใช้งาน";
	}

	@Override
	public String compartirInstanciaSinSeleccion() {
		return "ไม่มีโฟลเดอร์หรือไฟล์ที่ถูกเลือก";
	}

	@Override
	public String compartirInstanciaFormatoNoSoportado() {
		return "รูปแบบนี้ยังไม่รองรับ";
	}

	@Override
	public String compartirInstanciaServicioNoDisponible() {
		return "บริการที่เลือกไม่พร้อมใช้งาน";
	}

	@Override
	public String compartirInstanciaSubidaCompleta() {
		return "เริ่มการโอนเรียบร้อยแล้ว";
	}

	@Override
	public String compartirInstanciaErrorSubir() {
		return "ไม่สามารถอัปโหลดไฟล์ที่เลือกได้";
	}

	@Override
	public String compartirInstanciaColorPanel() {
		return "สีของแผง";
	}

	@Override
	public String compartirInstanciaColorTexto() {
		return "สีของข้อความ";
	}

	@Override
	public String compartirInstanciaPolitica1() {
		return "ประเภทที่แนะนำ: ม็อด, config, saves, worlds, datapacks, resource packs และไฟล์ตัวเลือก หลีกเลี่ยงการรวมข้อมูลส่วนตัวที่ไม่จำเป็น";
	}

	@Override
	public String compartirInstanciaPolitica2() {
		return "ส่วนขยายสามารถเพิ่มบริการอัปโหลดของตนเองได้ บริการที่รวมมาโดยค่าเริ่มต้นควรแสดงที่นี่";
	}

	@Override
	public String compartirInstanciaPolitica3() {
		return "wormhole.app: รองรับได้สูงสุด 5 GiB สำหรับการอัปโหลดปกติ; ระหว่าง 5 ถึง 10 GiB ต้องเปิดฝั่งผู้ส่งไว้ ในการติดตั้งปัจจุบันของโครงการ การเชื่อมต่อจริงยังอยู่ระหว่างดำเนินการ";
	}

	@Override
	public String compartirInstanciaPolitica4() {
		return "limewire.com: ออกแบบมาเป็นบริการที่มีการเก็บรักษาชั่วคราว ยังไม่รองรับในการติดตั้งนี้";
	}

	@Override
	public String compartirInstanciaPolitica5() {
		return "bittorrent: เป็นโหมดที่ปลอดภัยกว่าเพราะเป็นการกระจายแบบ P2P โดยตรง โดยไม่มีการโฮสต์ส่วนกลาง ยังไม่รองรับในการติดตั้งนี้";
	}

	@Override
	public String compartirInstanciaPolitica6() {
		return "ตามค่าเริ่มต้น โฟลเดอร์และไฟล์ที่พบบ่อยที่สุดของอินสแตนซ์จะถูกเลือกไว้เพื่ออำนวยความสะดวกในการสนับสนุนทางเทคนิค";
	}

	@Override
	public String compartirInstanciaPolitica7() {
		return "หากคุณรวมโฟลเดอร์ภายในของ CrashDetector ไว้ด้วย การตั้งค่า, logs และข้อมูลเสริมก็จะถูกส่งไปด้วย ดังนั้นคุณสามารถยกเลิกการเลือกได้หากไม่จำเป็น";
	}

	@Override
	public String malware_fracturiser_detectado() {
		return "ตรวจพบ Fracturiser ที่เป็นไปได้ หลักฐาน:";
	}

	@Override
	public String malware_info_stealer_detectado() {
		return "ตรวจพบตัวขโมยข้อมูลที่เป็นไปได้ หลักฐาน:";
	}

	@Override
	public String malware_evidencia_clase_sospechosa() {
		return "คลาสที่น่าสงสัย:";
	}

	@Override
	public String malware_evidencia_archivo_sospechoso() {
		return "ไฟล์ที่น่าสงสัย:";
	}

	@Override
	public String malware_brightsdk_detectado() {
		return "ตรวจพบ Bright SDK ที่เป็นไปได้ หลักฐาน:";
	}

	@Override
	public String malware_evidencia_paquete_sospechoso() {
		return "แพ็กเกจที่น่าสงสัย:";
	}

	@Override
	public String docsTituloVentana() {
		return "ตัวอ่านเอกสาร";
	}

	@Override
	public String docsAyudaDeUso() {
		return "<b>วิธีใช้ตัวอ่านนี้</b><br>" + "เลือกภาษาในส่วนล่างเพื่อดูเอกสารที่มีในภาษานั้น<br>"
				+ "ในแผงด้านซ้าย คุณสามารถนำทางผ่านโฟลเดอร์และเอกสารได้<br>"
				+ "เมื่อคลิกเอกสาร เนื้อหาจะปรากฏทางด้านขวา<br>"
				+ "ลิงก์ภายในที่ใช้โปรโตคอล <b>docs://</b> จะเปิดเอกสารอื่นภายในตัวอ่านนี้";
	}

	@Override
	public String docsArbolTitulo() {
		return "เอกสาร";
	}

	@Override
	public String docsVisorTitulo() {
		return "เนื้อหา";
	}

	@Override
	public String docsNoHayDocumentos() {
		return "ไม่มีเอกสารสำหรับภาษานี้";
	}

	@Override
	public String docsNoHayMarkdown() {
		return "ไม่พบไฟล์ Markdown สำหรับภาษานี้";
	}

	@Override
	public String docsDocumentoNoEncontrado() {
		return "ไม่พบเอกสารที่ร้องขอ";
	}

	@Override
	public String docsErrorAlAbrirDocumento() {
		return "เกิดข้อผิดพลาดขณะเปิดเอกสาร:";
	}

	@Override
	public String docsCargando() {
		return "กำลังโหลดเอกสาร...";
	}

	@Override
	public String docsImagenNoDisponible() {
		return "ไม่สามารถแสดงภาพได้";
	}

	@Override
	public String colorPanelSecundario() {
		return "สีของแผงรอง";
	}

	@Override
	public String colorTextoSuave() {
		return "สีของข้อความแบบอ่อน";
	}

	@Override
	public String colorSeleccion() {
		return "สีของการเลือก";
	}

	@Override
	public String colorFondoDocumento() {
		return "สีพื้นหลังของเอกสาร";
	}

	@Override
	public String iaTituloVentana() {
		return "AI";
	}

	@Override
	public String iaTituloPrincipal() {
		return "การวิเคราะห์ด้วย AI";
	}

	@Override
	public String iaDescripcionTitulo() {
		return "เอเจนต์วิเคราะห์ crash";
	}

	@Override
	public String iaDescripcionCuerpo() {
		return "เครื่องมือนี้จะเปิดเอเจนต์ภายนอกที่สามารถช่วยวิเคราะห์ crash ข้อผิดพลาด และบันทึกที่เกี่ยวข้องกับ Minecraft";
	}

	@Override
	public String iaDescripcionUso() {
		return "เพื่อใช้ระบบนี้ ให้เปิดลิงก์ เข้าสู่ระบบด้วยบัญชี Baidu แล้วใช้เอเจนต์เพื่อตรวจสอบ crash หรือ logs ของคุณ";
	}

	@Override
	public String iaAvisoCuentaBaidu() {
		return "คุณต้องมีบัญชี Baidu เพื่อเข้าถึงเอเจนต์";
	}

	@Override
	public String iaCopiarEnlace() {
		return "คัดลอกลิงก์";
	}

	@Override
	public String iaAbrirEnlace() {
		return "เปิดลิงก์";
	}

	@Override
	public String iaImagenNoDisponible() {
		return "ไม่สามารถแสดงภาพได้";
	}

	@Override
	public String mensajeOculusIrisUnknownShaderVariable() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดที่เป็นไปได้ของชเดอร์กับ Oculus หรือ Iris</b>"
				+ "<p>บันทึกข้อผิดพลาด (Log) มีทั้ง <b>kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal</b> "
				+ "และ <b>java.lang.RuntimeException: Unknown variable:</b>.</p>"
				+ "<p>การผสมผสานนี้มักบ่งชี้ถึงปัญหาในการประเมินค่าตัวแปรภายในชเดอร์ "
				+ "ซึ่งมักเกี่ยวข้องกับ <b>Oculus</b>, <b>Iris</b> หรือ <b>ชุดชเดอร์ (shader pack)</b> ที่กำลังใช้งาน</p>"
				+ "<p><b>ลำดับการทดสอบที่แนะนำ:</b></p>" + "<ul>"
				+ "<li>ก่อนอื่น เริ่มเกมโดย <b>ไม่ได้เปิดใช้งานชเดอร์</b></li>"
				+ "<li>หากปัญหายังคงอยู่ ลองเริ่มเกมโดย <b>ไม่มี Oculus หรือ Iris</b></li>"
				+ "<li>อัปเดต <b>Oculus/Iris</b>, <b>ชุดชเดอร์</b> และมอดกราฟิกที่เกี่ยวข้อง</li>"
				+ "<li>หากคุณใช้มอดเรนเดอร์หรือกราฟิกอื่นๆ ตรวจสอบความไม่เข้ากันระหว่างมอดเหล่านั้น</li>" + "</ul>"
				+ "<p>ในทางปฏิบัติ ข้อผิดพลาดนี้มักมาจาก <b>ชุดชเดอร์</b> หรือการทำงานร่วมกันกับ <b>Oculus/Iris</b></p>";
	}

	@Override
	public String nombreOculusIrisUnknownShaderVariable() {
		return "ข้อผิดพลาดชเดอร์ที่เป็นไปได้กับ Oculus/Iris";
	}

	@Override
	public String mensajeItemNoExiste(String itemFaltante, String namespace) {
		String itemHtml = itemFaltante == null || itemFaltante.isEmpty() ? "(ไม่ทราบ)" : itemFaltante;
		String namespaceHtml = namespace == null || namespace.isEmpty() ? "(ไม่ทราบ)" : namespace;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "พยายามใช้ไอเทมที่ไม่มีอยู่</b>" + "<p>บันทึกมีบรรทัด <b>Item: " + itemHtml
				+ " does not exist</b>.</p>"
				+ "<p>สิ่งนี้มักหมายความว่า <b>datapack</b>, <b>mod</b> หรือ <b>การตั้งค่า</b> บางอย่าง "
				+ "กำลังอ้างอิงถึงไอเทมที่ไม่มีในเกม</p>" + "<p><b>สิ่งที่ควรตรวจสอบ:</b></p>" + "<ul>"
				+ "<li>ตรวจสอบว่าคุณได้ติดตั้ง mod ที่ควรมีไอเทม <b>" + itemHtml + "</b> หรือไม่</li>"
				+ "<li>หากมีแล้ว อาจเป็น <b>เวอร์ชันที่ไม่ถูกต้อง</b>, ไอเทมถูกเปลี่ยนหรือลบออก, "
				+ "หรือ mod มีปัญหาและควรลบออก</li>" + "<li>หากคุณไม่มี mod นั้น ลอง <b>ติดตั้งมัน</b></li>" + "</ul>"
				+ "<p><b>เพื่อค้นหาว่า mod หรือ datapack ใดกำลังขอไอเทมนั้น:</b></p>" + "<ul>"
				+ "<li>ใช้เครื่องมือ <b>grepr</b> ที่แถบด้านข้าง</li>" + "<li><b>อย่า</b> เลือกโฟลเดอร์</li>"
				+ "<li>เปิดใช้งานตัวเลือก <b>search in archives</b></li>"
				+ "<li>ในช่องค้นหา ให้พิมพ์ <b>namespace</b> นั่นคือส่วนก่อนเครื่องหมายโคลอน: " + "<b>" + namespaceHtml
				+ "</b></li>" + "</ul>" + "<p>สิ่งนี้มักช่วยหาไฟล์, mod หรือ datapack ที่ทำการอ้างอิงไม่ถูกต้อง</p>";
	}

	@Override
	public String nombreItemNoExiste() {
		return "อ้างอิงถึงไอเทมที่ไม่มีอยู่";
	}

	@Override
	public String mensajeCobblemonPinkanIslandsRhyhornModelo() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดของโมเดลสำหรับ Rhyhorn</b>"
				+ "<p>บันทึกมีบรรทัด <b>Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn</b></p>"
				+ "<p>แม้ว่าโมเดลจะใช้ namespace ของ <b>Cobblemon</b> แต่บรรทัดนี้มักเกิดจากมอด "
				+ "<b>Cobblemon: Pinkan Islands</b> ซึ่งเป็นแหล่งที่มาของ <b>Rhyhorn</b> นี้</p>"
				+ "<p><b>สิ่งที่ควรลอง:</b></p>" + "<ul>"
				+ "<li>ลบหรือปิดใช้งาน <b>Cobblemon: Pinkan Islands</b> แล้วลองใหม่อีกครั้ง</li>"
				+ "<li>อัปเดต <b>Cobblemon</b> และ <b>Cobblemon: Pinkan Islands</b> ให้เป็นเวอร์ชันที่เข้ากันได้</li>"
				+ "<li>หากปัญหาเริ่มเกิดขึ้นหลังจากอัปเดตมอดใดมอดหนึ่ง ลองใช้ชุดเวอร์ชันอื่น</li>" + "</ul>"
				+ "<p>ข้อผิดพลาดนี้มักบ่งชี้ว่าโมเดลหายไป ลงทะเบียนไม่ถูกต้อง หรือไม่เข้ากันภายใน "
				+ "<b>Cobblemon: Pinkan Islands</b></p>";
	}

	@Override
	public String nombreCobblemonPinkanIslandsRhyhornModelo() {
		return "ข้อผิดพลาดโมเดล Rhyhorn ใน Cobblemon: Pinkan Islands";
	}

	@Override
	public String mensajeColdSweatInitDynamicTags() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดใน Cold Sweat</b>" + "<p>บันทึกมีสัญญาณเช่น <b>$cold_sweat$onBuildStart</b>, "
				+ "<b>InitDynamicTagsEvent.fillTag</b> และ <b>NullPointerException</b> ซึ่ง "
				+ "registry ปรากฏเป็น null</p>" + "<p>สิ่งนี้มักบ่งชี้ถึงปัญหาของ <b>Cold Sweat</b> ในการสร้างหรือเติม "
				+ "<b>tags แบบไดนามิก</b> มักเกิดจากความไม่เข้ากัน ข้อผิดพลาดภายในมอด "
				+ "หรือความขัดแย้งกับมอดหรือ datapack อื่น</p>" + "<p><b>สิ่งที่ควรลอง:</b></p>" + "<ul>"
				+ "<li>ลบหรือปิดใช้งาน <b>Cold Sweat</b> แล้วลองใหม่อีกครั้ง</li>"
				+ "<li>อัปเดต <b>Cold Sweat</b> เป็นเวอร์ชันที่เข้ากันได้กับเวอร์ชัน Minecraft และ loader ของคุณ</li>"
				+ "<li>หากคุณใช้ datapacks หรือมอดที่เปลี่ยน <b>tags</b>, <b>biomes</b>, <b>อุณหภูมิ</b> หรือ registry ที่เกี่ยวข้อง ให้ตรวจสอบด้วย</li>"
				+ "<li>หากข้อผิดพลาดเริ่มเกิดขึ้นหลังอัปเดตมอด ลองใช้ชุดเวอร์ชันอื่น</li>" + "</ul>"
				+ "<p>ในกรณีนี้ <b>Cold Sweat</b> มีส่วนเกี่ยวข้องโดยตรงกับความล้มเหลว</p>";
	}

	@Override
	public String nombreColdSweatInitDynamicTags() {
		return "ข้อผิดพลาด Cold Sweat ใน tags แบบไดนามิก";
	}

	@Override
	public String mensajeClassCastExceptionGeneral(String lineaClassCast) {
		String detalle = lineaClassCast == null || lineaClassCast.isEmpty() ? ""
				: "<p><b>พบบรรทัด:</b></p><p><code>" + lineaClassCast + "</code></p>";

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบ ClassCastException</b>"
				+ "<p>ซึ่งหมายความว่าคลาสถูกจัดการเสมือนว่าเป็นคลาสหรืออินเทอร์เฟซอื่นที่ไม่เข้ากัน</p>" + detalle
				+ "<p>ข้อผิดพลาดประเภทนี้มักเกิดจากสถานการณ์ใดสถานการณ์หนึ่งดังนี้:</p>" + "<ul>"
				+ "<li><b>มอดสองตัวที่ไม่เข้ากัน</b> ระหว่างกัน</li>"
				+ "<li><b>Mixins</b>, <b>transformers</b> หรือแพตช์ที่แก้ไขคลาสและทำให้ส่วนอื่นของเกมคาดหวังประเภทข้อมูลที่แตกต่างกัน</li>"
				+ "<li>มอดอื่นๆ ที่ปรากฏใน <b>stacktrace</b> ที่กำลังทำให้เกิดการแปลงประเภทผิดพลาด</li>" + "</ul>"
				+ "<p><b>สิ่งที่ควรตรวจสอบ:</b></p>" + "<ul>"
				+ "<li>ดูบรรทัดใน <b>stacktrace</b> ที่เกี่ยวข้องกับข้อผิดพลาดนี้</li>"
				+ "<li>ให้ความสนใจเป็นพิเศษกับชื่อมอดหรือคลาสที่มีรูปแบบ <b>$modid$algo</b> เพราะมักจะชี้ไปยังมอดที่เกี่ยวข้อง</li>"
				+ "<li>ลองอัปเดต ลบ หรือแยกมอดที่ดูเหมือนจะเกี่ยวข้องกับการแปลงประเภทที่ไม่ถูกต้อง</li>" + "</ul>"
				+ "<p>แม้ว่า <b>ClassCastException</b> จะไม่ร้ายแรงเสมอไป แต่บ่อยครั้งก็มักจะทำให้เกมขัดข้อง</p>";
	}

	@Override
	public String nombreClassCastExceptionGeneral() {
		return "ตรวจพบ ClassCastException";
	}

	@Override
	public String mensajeValkyrienSkiesTournamentLithiumPoiInjection() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบความไม่เข้ากันที่อาจเกิดขึ้นระหว่าง Valkyrien Skies Tournament และ Lithium/Radium</b>"
				+ "<p>บันทึกมี <b>InvalidInjectionException</b> ซึ่งแสดง mixin ของ "
				+ "<b>Lithium</b> เกี่ยวกับ <b>POI</b> ร่วมกับ <b>valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager</b></p>"
				+ "<p>ปัญหานี้มักเกิดขึ้นเมื่อใช้ <b>Lithium เวอร์ชันเก่า</b> หรือ "
				+ "<b>fork ที่อิงจาก Lithium เวอร์ชันเก่า</b> เช่น <b>Radium Reforged</b> ร่วมกับ "
				+ "<b>VS Tournament</b></p>" + "<p><b>สิ่งที่ควรลอง:</b></p>" + "<ul>"
				+ "<li>อัปเดต <b>Lithium</b> เป็นเวอร์ชันที่ใหม่กว่าและเข้ากันได้</li>"
				+ "<li>หากคุณใช้ <b>Forge/NeoForge</b> และใช้ <b>Radium Reforged</b> หรือ fork เก่าอื่น ให้ลบออก</li>"
				+ "<li>แทนที่ด้วยการลองใช้ <b>Harium</b> ซึ่งเป็น fork สมัยใหม่ของ Radium ที่ซิงค์กับการปรับปรุงล่าสุดของ Lithium</li>"
				+ "<li>หากปัญหาเริ่มเกิดขึ้นหลังอัปเดตมอด ให้ตรวจสอบชุดเวอร์ชันที่แน่ชัดระหว่าง <b>VS Tournament</b> และมอดเพิ่มประสิทธิภาพ AI/POI ของคุณ</li>"
				+ "</ul>"
				+ "<p>โดยทั่วไปแล้ว ความล้มเหลวนี้มักมาจากการใช้งาน <b>Lithium/Radium</b> เวอร์ชันเก่าที่เข้ากันไม่ดีกับ <b>VS Tournament</b></p>";
	}

	@Override
	public String nombreValkyrienSkiesTournamentLithiumPoiInjection() {
		return "ความไม่เข้ากันของ VS Tournament กับ Lithium/Radium";
	}

	@Override
	public String mensajeVSTournamentVSConfigClassNoExiste() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ดูเหมือนว่า VS Tournament จะเก่าเกินไปสำหรับเวอร์ชัน Valkyrien Skies ของคุณ</b>"
				+ "<p>บันทึกมี <b>NoClassDefFoundError</b> สำหรับ "
				+ "<b>org/valkyrienskies/core/impl/config/VSConfigClass</b> และบรรทัดจาก "
				+ "<b>org.valkyrienskies.tournament.TournamentMod.init(...)</b></p>"
				+ "<p>สิ่งนี้มักหมายความว่า คุณมี <b>VS Tournament เวอร์ชันเก่า</b> ที่พยายาม "
				+ "ใช้คลาสภายในเก่าของ <b>Valkyrien Skies</b> ที่ไม่มีอยู่แล้ว</p>" + "<p><b>สิ่งที่ควรทำ:</b></p>"
				+ "<ul>" + "<li>ลบ <b>VS Tournament</b> เวอร์ชันเก่าออก</li>"
				+ "<li>ใช้ <b>VS Tournament Reforged</b> แทน</li>"
				+ "<li>ตรวจสอบเพิ่มเติมว่าเวอร์ชันของ <b>Valkyrien Skies</b> ตรงกับเวอร์ชันที่ addon รองรับ</li>"
				+ "</ul>"
				+ "<p>คำแนะนำในการเปลี่ยนไปใช้ <b>VS Tournament Reforged</b> สอดคล้องกับสถานะปัจจุบันของโปรเจกต์: "
				+ "เวอร์ชันดั้งเดิมของ Tournament ยังคงถูกระบุว่าเป็นมอดเก่าสำหรับ 1.18.2 ในขณะที่ "
				+ "<b>VS Tournament Reforged</b> เผยแพร่แยกต่างหาก และปัจจุบันประกาศรองรับ Valkyrien "
				+ "2.4.9+ บน Forge 1.20.1</p>";
	}

	@Override
	public String nombreVSTournamentVSConfigClassNoExiste() {
		return "VS Tournament เวอร์ชันเก่าไม่เข้ากันกับ Valkyrien Skies";
	}

	public String curseForgeClaveApiMundial() {
		return "คีย์ API สากลของ CurseForge";
	}

	public String curseForgeEndpoint() {
		return "Endpoint ของ CurseForge";
	}

	public String tlmodsEndpoint() {
		return "Endpoint ของ TLMods";
	}

	public String minecraftStorageEndpoint() {
		return "Endpoint ของ MinecraftStorage";
	}

	public String autoBackupActivado() {
		return "เปิดใช้งานการสำรองข้อมูลอัตโนมัติ";
	}

	public String autoBackupFrecuencia() {
		return "ความถี่ในการสำรองข้อมูลอัตโนมัติ";
	}

	public String autoBackupDiasConservar() {
		return "จำนวนวันที่เก็บรักษาการสำรองข้อมูลอัตโนมัติ";
	}

	public String autoBackupTamanoMaximoMB() {
		return "ขนาดสูงสุดของการสำรองข้อมูลอัตโนมัติ (MB)";
	}

	public String actualizadorModsTitulo() {
		return "ตัวอัปเดตมอด";
	}

	public String actualizadorModsBotonSidebar() {
		return "อัปเดต";
	}

	public String actualizadorModsDescripcion() {
		return "ค้นหาอัปเดตที่มีสำหรับมอดในมอดแพ็ก คุณสามารถอัปเดตทั้งหมดหรือเลือกอัปเดตทีละตัวได้";
	}

	public String actualizadorModsBotonEscanear() {
		return "ค้นหาอัปเดต";
	}

	public String actualizadorModsBotonActualizarTodo() {
		return "อัปเดตทั้งหมด";
	}

	public String actualizadorModsBotonActualizarUno() {
		return "อัปเดต";
	}

	public String actualizadorModsEstadoListo() {
		return "พร้อมแล้ว";
	}

	public String actualizadorModsEstadoEscaneando() {
		return "กำลังค้นหาอัปเดต...";
	}

	public String actualizadorModsEstadoActualizando() {
		return "กำลังอัปเดต...";
	}

	public String actualizadorModsEstadoSinActualizaciones() {
		return "ไม่มีอัปเดตใหม่";
	}

	public String actualizadorModsEstadoEncontradas(int n) {
		return "พบอัปเดต: " + n;
	}

	public String actualizadorModsEstadoActualizadas(int n) {
		return "อัปเดตสำเร็จ: " + n;
	}

	public String actualizadorModsEstadoError() {
		return "เกิดข้อผิดพลาดในการอัปเดต";
	}

	public String actualizadorModsSinSeleccion() {
		return "ไม่ได้เลือกอัปเดตใดๆ";
	}

	public String actualizadorModsColumnaMod() {
		return "มอด";
	}

	public String actualizadorModsColumnaActual() {
		return "ปัจจุบัน";
	}

	public String actualizadorModsColumnaNueva() {
		return "ใหม่";
	}

	public String actualizadorModsColumnaFuente() {
		return "แหล่งที่มา";
	}

	public String actualizadorModsColumnaLoader() {
		return "Loader";
	}

	public String actualizadorModsColumnaRuta() {
		return "เส้นทาง";
	}

	public String actualizadorModsColumnaAccion() {
		return "การดำเนินการ";
	}

	public String actualizadorModsColorFondo() {
		return "ตัวอัปเดต: พื้นหลัง";
	}

	public String actualizadorModsColorPanel() {
		return "ตัวอัปเดต: แผง";
	}

	public String actualizadorModsColorTexto() {
		return "ตัวอัปเดต: ข้อความ";
	}

	public String actualizadorModsColorTextoSuave() {
		return "ตัวอัปเดต: ข้อความจาง";
	}

	public String actualizadorModsColorBoton() {
		return "ตัวอัปเดต: ปุ่ม";
	}

	public String actualizadorModsColorBotonTexto() {
		return "ตัวอัปเดต: ข้อความปุ่ม";
	}

	public String actualizadorModsColorTabla() {
		return "ตัวอัปเดต: ตาราง";
	}

	public String actualizadorModsColorSeleccion() {
		return "ตัวอัปเดต: การเลือก";
	}

	public String importadorYumeiriTeExtraniamos() {
		return "เราคิดถึงคุณนะ Yumeiri Reyu";
	}

	public String importadorColorFondo() {
		return "นำเข้า: พื้นหลัง";
	}

	public String importadorColorPanel() {
		return "นำเข้า: แผง";
	}

	public String importadorColorTexto() {
		return "นำเข้า: ข้อความ";
	}

	public String importadorColorTextoSuave() {
		return "นำเข้า: ข้อความจาง";
	}

	public String importadorColorBoton() {
		return "นำเข้า: ปุ่ม";
	}

	public String importadorColorBotonTexto() {
		return "นำเข้า: ข้อความปุ่ม";
	}

	public String importadorColorBorde() {
		return "นำเข้า: ขอบ";
	}

	public String importadorConflictoTitulo() {
		return "ความขัดแย้งในการนำเข้า";
	}

	public String importadorConflictoMensaje() {
		return "มีไฟล์อยู่แล้วในปลายทาง";
	}

	public String importadorRuta() {
		return "เส้นทาง";
	}

	public String importadorArchivoExistente() {
		return "ไฟล์ที่มีอยู่";
	}

	public String importadorArchivoNuevo() {
		return "ไฟล์ที่นำเข้า";
	}

	public String importadorTamano() {
		return "ขนาด";
	}

	public String importadorFecha() {
		return "แก้ไขล่าสุด";
	}

	public String importadorInfoMod() {
		return "ข้อมูลของมอด";
	}

	public String importadorModImportadoMasNuevo() {
		return "มอดที่นำเข้าดูเหมือนจะใหม่กว่า";
	}

	public String importadorModImportadoMasViejo() {
		return "มอดที่นำเข้าดูเหมือนจะเก่ากว่า";
	}

	public String importadorBotonReemplazar() {
		return "แทนที่";
	}

	public String importadorBotonSaltar() {
		return "ข้าม";
	}

	public String importadorBotonRenombrar() {
		return "เปลี่ยนชื่อไฟล์ใหม่";
	}

	public String importadorModpackTitulo() {
		return "นำเข้ามอดแพ็ก";
	}

	public String importadorModpackBotonSidebar() {
		return "นำเข้า";
	}

	public String importadorModpackDescripcion() {
		return "นำเข้ามอดแพ็กไปยังอินสแตนซ์ปัจจุบัน คุณสามารถลากไฟล์ .zip, .mrpack หรือรูปแบบอื่นที่รองรับ หรือเลือกด้วยตนเอง";
	}

	public String importadorModpackFormato() {
		return "รูปแบบ";
	}

	public String importadorModpackArrastraArchivo() {
		return "ลากมอดแพ็กของคุณมาวางที่นี่ หรือเลือกไฟล์";
	}

	public String importadorModpackBotonSeleccionar() {
		return "เลือกไฟล์";
	}

	public String importadorModpackBotonImportar() {
		return "นำเข้า";
	}

	public String importadorModpackSeleccionarArchivo() {
		return "เลือกมอดแพ็ก";
	}

	public String importadorModpackEstadoListo() {
		return "พร้อมแล้ว";
	}

	public String importadorModpackEstadoImportando() {
		return "กำลังนำเข้า...";
	}

	public String importadorModpackEstadoError() {
		return "เกิดข้อผิดพลาดในการนำเข้า";
	}

	public String importadorModpackSinArchivo() {
		return "โปรดเลือกไฟล์มอดแพ็กก่อน";
	}

	public String importadorModpackFormatoNoSoportado() {
		return "รูปแบบนี้ไม่รองรับการนำเข้า";
	}

	public String importadorModpackResultado(int copiados, int reemplazados, int saltados, int renombrados,
			int errores) {
		return "การนำเข้าเสร็จสิ้น\nคัดลอกแล้ว: " + copiados + "\nแทนที่แล้ว: " + reemplazados + "\nข้ามแล้ว: "
				+ saltados + "\nเปลี่ยนชื่อแล้ว: " + renombrados + "\nข้อผิดพลาด: " + errores;
	}

	public String importadorModpackColorFondo() {
		return "นำเข้ามอดแพ็ก: พื้นหลัง";
	}

	public String importadorModpackColorPanel() {
		return "นำเข้ามอดแพ็ก: แผง";
	}

	public String importadorModpackColorTexto() {
		return "นำเข้ามอดแพ็ก: ข้อความ";
	}

	public String importadorModpackColorTextoSuave() {
		return "นำเข้ามอดแพ็ก: ข้อความจาง";
	}

	public String importadorModpackColorBoton() {
		return "นำเข้ามอดแพ็ก: ปุ่ม";
	}

	public String importadorModpackColorBotonTexto() {
		return "นำเข้ามอดแพ็ก: ข้อความปุ่ม";
	}

	public String importadorModpackColorDrop() {
		return "นำเข้ามอดแพ็ก: พื้นที่ลากวาง";
	}

	public String importadorModpackColorBorde() {
		return "นำเข้ามอดแพ็ก: ขอบ";
	}

	public String jgitTituloIzzy() {
		return "ศูนย์ Git ของ Izzy";
	}

	public String jgitRetratoIzzy() {
		return "Izzy";
	}

	public String jgitNoHayRetratoIzzy() {
		return "ไม่มีรูปของ Izzy";
	}

	public String jgitSeccionInstalacion() {
		return "การติดตั้ง JGit";
	}

	public String jgitAbrirCarpetaInstalacion() {
		return "เปิดโฟลเดอร์การติดตั้ง";
	}

	public String jgitAbrirPaginaDescarga() {
		return "เปิดหน้าดาวน์โหลด JGit";
	}

	public String jgitSeccionRepositorio() {
		return "ที่เก็บข้อมูลท้องถิ่น";
	}

	public String jgitCrearRepositorioLocal() {
		return "สร้างที่เก็บข้อมูล Git ที่นี่";
	}

	public String jgitCommitManual() {
		return "Commit ด้วยตนเอง";
	}

	public String jgitSeccionRemote() {
		return "Remote";
	}

	public String jgitForgeManual() {
		return "คู่มือ Forge";
	}

	public String jgitForgePersonalizado() {
		return "Forge แบบกำหนดเอง";
	}

	public String jgitEstablecerRemoteManual() {
		return "ตั้งค่า Remote ด้วยตนเอง";
	}

	public String jgitCrearRemoteConAPI() {
		return "สร้าง Remote ด้วย API";
	}

	public String jgitPushManual() {
		return "Push ด้วยตนเอง";
	}

	public String jgitSeccionAutomaticos() {
		return "การทำงานอัตโนมัติ";
	}

	public String jgitAutoCommitDespuesBackup() {
		return "Commit อัตโนมัติหลังสำรองข้อมูล";
	}

	public String jgitAutoPushDespuesCommit() {
		return "Push อัตโนมัติหลัง Commit";
	}

	public String jgitSeccionHerramientas() {
		return "เครื่องมือ";
	}

	public String jgitAbrirGuiSwing() {
		return "เปิดตัวดู Swing ของ JGit";
	}

	public String jgitEstado() {
		return "สถานะ";
	}

	public String jgitClasspath() {
		return "JGit ใน classpath";
	}

	public String jgitTodosLosArtefactos() {
		return "อาร์ติแฟกต์ JGit ทั้งหมด";
	}

	public String jgitRepositorio() {
		return "ที่เก็บข้อมูล";
	}

	public String jgitRemote() {
		return "Remote";
	}

	public String jgitCarpetaActual() {
		return "โฟลเดอร์ปัจจุบัน";
	}

	public String jgitNoSePudoCrearRepo() {
		return "ไม่สามารถสร้างที่เก็บข้อมูลได้";
	}

	public String jgitEscribaRemote() {
		return "ป้อน URL ของ Remote:";
	}

	public String jgitNoSePudoGuardarRemote() {
		return "ไม่สามารถบันทึก Remote ได้";
	}

	public String jgitApiForgeAunNoImplementada() {
		return "API ของ Forge ยังไม่ได้ใช้งาน";
	}

	public String jgitNoHayCambiosOError() {
		return "ไม่มีการเปลี่ยนแปลงสำหรับ Commit หรือเกิดข้อผิดพลาด";
	}

	public String jgitNoSePudoPush() {
		return "ไม่สามารถทำ Push ได้";
	}

	public String jgitTituloVentanaSwing() {
		return "ตัวดู Git";
	}

	public String jgitNoHayRepositorio() {
		return "ไม่มีที่เก็บข้อมูล Git ในโฟลเดอร์นี้";
	}

	public String jgitArchivosModificados() {
		return "ไฟล์ที่แก้ไขแล้ว";
	}

	public String jgitArchivosNuevos() {
		return "ไฟล์ใหม่";
	}

	public String jgitUltimosCommits() {
		return "Commits ล่าสุด";
	}

	public String jgitError() {
		return "ข้อผิดพลาดของ JGit";
	}

	public String si() {
		return "ใช่";
	}

	public String no() {
		return "ไม่";
	}

	public String jgitDescargarDependenciasFaltantes() {
		return "ดาวน์โหลด dependencies ที่ขาดหาย";
	}

	public String jgitNoFaltanDependencias() {
		return "ไม่มี dependencies ของ JGit ที่ขาดหาย";
	}

	public String jgitConfirmarDescargaDependencias(int cantidad) {
		return "ขาดหาย dependencies ของ JGit จำนวน " + cantidad + " รายการ ต้องการดาวน์โหลดจาก Maven Central หรือไม่?";
	}

	public String jgitDependenciasDescargadas(int cantidad) {
		return "ดาวน์โหลด dependencies แล้ว: " + cantidad;
	}

	public String jgitErroresDescarga() {
		return "ข้อผิดพลาดในการดาวน์โหลด";
	}

	public String jgitReiniciarParaCargarClasspath() {
		return "โปรดรีสตาร์ท CrashDetector เพื่อให้ JARs ใหม่เข้าสู่ classpath";
	}

	public String jgitArtefactosFaltantes() {
		return "Artifacts ที่ขาดหาย";
	}

	public String jgitArtefactosFaltantesClasspath() {
		return "Artifacts ที่ขาดหายใน classpath";
	}

	public String jgitArtefactosFaltantesCarpeta() {
		return "Artifacts ที่ขาดหายในโฟลเดอร์การติดตั้ง";
	}

	public String jgitDependenciasEnCarpeta() {
		return "Dependencies ที่ติดตั้งในโฟลเดอร์";
	}

	public String jgitForgeNoSeleccionada() {
		return "ไม่ได้เลือก Forge";
	}

	public String jgitForgeNoRegistrada(String id) {
		return "Forge ไม่ได้ลงทะเบียน: " + id;
	}

	public String jgitEscribaUrlForge() {
		return "URL ของ Forge:";
	}

	public String jgitEscribaNombreRepositorio() {
		return "ชื่อที่เก็บข้อมูล:";
	}

	public String jgitEscribaDescripcionRepositorio() {
		return "คำอธิบายที่เก็บข้อมูล:";
	}

	public String jgitEscribaNamespaceOpcional() {
		return "Namespace (ไม่บังคับ):";
	}

	public String jgitEscribaTokenForge() {
		return "Token API ของ Forge:";
	}

	public String jgitErrorCrearRemote() {
		return "ข้อผิดพลาดในการสร้าง remote";
	}

	@Override
	public String mensajeControlifyRemoveReloadingScreen() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบความไม่เข้ากันระหว่าง Controlify และ Remove Reloading Screen</b>"
				+ "<p>ล็อกมีบรรทัด <b>Attempted to fetch default config before DefaultConfigManager was ready!</b> "
				+ "และ <b>$rrls$init</b> ซึ่งมักบ่งชี้ถึงความขัดแย้งระหว่าง <b>Controlify</b> และ "
				+ "<b>Remove Reloading Screen</b></p>"
				+ "<p><b>สาเหตุที่เป็นไปได้:</b> Remove Reloading Screen แก้ไขส่วนต่างๆ ของหน้าจอโหลดหรือกระบวนการโหลด, "
				+ "ในขณะที่ Controlify พยายามเริ่มต้นการตั้งค่าก่อนที่ระบบจะพร้อมสมบูรณ์</p>"
				+ "<p><b>ตัวเลือกที่แนะนำ:</b></p>" + "<ul>" + "<li>ลบ <b>Remove Reloading Screen</b></li>"
				+ "<li>หรืออัปเดต <b>Controlify</b> และ <b>Remove Reloading Screen</b> หากมีเวอร์ชันใหม่</li>"
				+ "<li>หากปัญหายังคงอยู่ ให้เก็บ <b>Controlify</b> ไว้และลบมอดใดๆ ที่เปลี่ยนหน้าจอโหลด</li>" + "</ul>"
				+ "<p>มอดที่แก้ไขหน้าจอโหลดมักทำให้เกิดความไม่เข้ากันกับมอดอื่น, "
				+ "และมักให้ประโยชน์น้อยเมื่อเทียบกับปัญหาที่อาจเกิดขึ้น</p>";
	}

	@Override
	public String nombreControlifyRemoveReloadingScreen() {
		return "ความไม่เข้ากัน: Controlify กับ Remove Reloading Screen";
	}

	@Override
	public String mensajeBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ปัญหาที่เป็นไปได้กับ Biomes O' Plenty และของเหลวที่กำหนดเอง</b>"
				+ "<p>ล็อกมีข้อผิดพลาด <b>class org.joml.Vector4f cannot be cast to class "
				+ "net.minecraft.client.renderer.fog.FogData</b> พร้อมกับการอ้างอิงถึง <b>Biomes O' Plenty</b></p>"
				+ "<p>สิ่งนี้อาจเกี่ยวข้องกับ <b>Biomes O' Plenty</b> โดยเฉพาะกับไบโอม หมอก "
				+ "หรือของเหลวที่กำหนดเอง อย่างไรก็ตาม ไม่แน่ชัดว่า Biomes O' Plenty เป็นสาเหตุเดียว</p>"
				+ "<p><b>ตัวเลือกที่แนะนำ:</b></p>" + "<ul>"
				+ "<li>ลองแก้ไขข้อมูลผู้เล่นเพื่อย้ายไปยังตำแหน่งอื่นในโลก</li>"
				+ "<li>ลองโหลดโลกโดยไม่มี <b>Biomes O' Plenty</b></li>"
				+ "<li>หากโลกโหลดได้หลังจากย้ายผู้เล่น ปัญหาอาจเกิดขึ้นในพื้นที่เฉพาะ "
				+ "ไบโอมเฉพาะ หรือของเหลวที่กำหนดเองที่อยู่ใกล้เคียง</li>"
				+ "<li>คุณยังสามารถลองอัปเดต <b>Biomes O' Plenty</b> และมอดที่เกี่ยวข้องกับการเรนเดอร์ หมอก "
				+ "เชเดอร์ หรือมิติ</li>" + "</ul>"
				+ "<p>หากการลบ Biomes O' Plenty ทำให้เริ่มเกมได้ ตรวจสอบว่าผู้เล่นอยู่ในหรือใกล้กับไบโอม "
				+ "หรือของไหลที่เพิ่มโดยมอดนั้นหรือไม่</p>";
	}

	@Override
	public String nombreBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "ปัญหาที่เป็นไปได้: Biomes O' Plenty และ FogData";
	}

	@Override
	public String mensajeKotlinReflectionInternalErrorVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบข้อผิดพลาดภายในของการสะท้อน Kotlin</b>"
				+ "<p>ล็อกมี <b>kotlin.reflect.jvm.internal.KotlinReflectionInternalError</b> พร้อมข้อความที่คล้ายกับ "
				+ "<b>Property 'none' not resolved</b></p>"
				+ "<p>ข้อผิดพลาดประเภทนี้พบบ่อยกับบางเวอร์ชันของ <b>Fabric Language Kotlin</b> / <b>Kotlin</b> "
				+ "ในกรณีนี้ปรากฏคลาสจาก <b>Inventory Profiles Next</b> แต่ปัญหาเดียวกันนี้อาจเกิดขึ้น "
				+ "กับมอดอื่น ๆ ที่ใช้ Kotlin</p>" + "<p><b>ตัวเลือกที่แนะนำ:</b></p>" + "<ul>"
				+ "<li>อัปเดต <b>Fabric Language Kotlin</b> เป็นเวอร์ชัน <b>2.3.40</b> หากมีสำหรับเวอร์ชัน Minecraft ของคุณ</li>"
				+ "<li>หากการอัปเดตไม่ได้ผล ลองลดเวอร์ชัน <b>Fabric Language Kotlin</b> ลงเป็น <b>2.3.10</b></li>"
				+ "<li>อัปเดต <b>Inventory Profiles Next</b> ด้วยหากล็อกกล่าวถึงคลาสของมอดนั้น</li>"
				+ "<li>หากข้อผิดพลาดปรากฏกับมอดอื่น ตรวจสอบว่ามอดนั้นขึ้นอยู่กับ Kotlin และลองเปลี่ยนเวอร์ชันของ "
				+ "<b>Fabric Language Kotlin</b></li>" + "</ul>" + "<p>ข้อมูลอ้างอิงทางเทคนิคที่เกี่ยวข้อง: "
				+ "<a href='https://github.com/FabricMC/fabric-language-kotlin/issues/183'>ปัญหา #183 ของ Fabric Language Kotlin</a></p>";
	}

	@Override
	public String nombreKotlinReflectionInternalErrorVersion() {
		return "ข้อผิดพลาด Kotlin: การสะท้อนภายใน";
	}

	public String tituloEscanerMCreator() {
		return "ตัวสแกน MCreator";
	}

	public String escanerMCreatorEscaneandoMods() {
		return "กำลังสแกนมอด...";
	}

	public String escanerMCreatorPorFavorEspera() {
		return "กรุณารอสักครู่";
	}

	public String escanerMCreatorResultadosAnalisis() {
		return "ผลการวิเคราะห์ MCreator:";
	}

	public String escanerMCreatorNoSeEncontraronMods() {
		return "ไม่พบมอดของ MCreator";
	}

	public String escanerMCreatorEscaneoCompletado() {
		return "การสแกนเสร็จสิ้น";
	}

	public String escanerMCreatorErrorDuranteEscaneo() {
		return "เกิดข้อผิดพลาดระหว่างการสแกน:";
	}

	public String escanerMCreatorCargando() {
		return "กำลังโหลด...";
	}

	public String escanerMCreatorCompletado() {
		return "เสร็จสิ้น";
	}

	public String escanerMCreatorError() {
		return "ข้อผิดพลาด";
	}

	@Override
	public String textoNormal() {
		return "ข้อความปกติ";
	}

	@Override
	public String noSeEncontroConsolaParaArchivo() {
		return "ไม่พบคอนโซลสำหรับไฟล์: ";
	}

	@Override
	public String lineaSeleccionadaEnLectador() {
		return "บรรทัดที่เลือกในตัวอ่าน: ";
	}

	@Override
	public String mensajeMotionBlurBufferCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ปัญหาที่เป็นไปได้กับ Motion Blur.</b>"
				+ "<p>บันทึกมีอ้างอิงถึง <b>net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO</b> "
				+ "และข้อผิดพลาด <b>java.lang.IllegalStateException: Buffer already closed</b>.</p>"
				+ "<p>บรรทัดเหล่านี้อาจปรากฏแยกกันในบันทึก แต่เมื่ออยู่ด้วยกันมักบ่งชี้ว่าปัญหาเกี่ยวข้องกับ "
				+ "มอด <b>Motion Blur</b> หรือการจัดการ shaders/buffers กราฟิกของมัน</p>"
				+ "<p><b>ตัวเลือกที่แนะนำ:</b></p>" + "<ul>" + "<li>ลองเริ่มเกมโดยไม่มี <b>Motion Blur</b>.</li>"
				+ "<li>หากเกมเริ่มได้ถูกต้องโดยไม่มีมอดนั้น ให้ถอนการติดตั้งหรือหาเวอร์ชันล่าสุด</li>"
				+ "<li>คุณยังสามารถลองปิด shaders หรือมอดการเรนเดอร์อื่นๆ หากปัญหายังคงอยู่</li>" + "</ul>";
	}

	@Override
	public String nombreMotionBlurBufferCerrado() {
		return "ปัญหาที่เป็นไปได้: Motion Blur";
	}

	@Override
	public String mensajeGeneratorAcceleratorOwoVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ความขัดแย้งที่อาจเกิดขึ้นกับ Generator Accelerator.</b>"
				+ "<p>บันทึกมีความแตกต่างระหว่างลายเซ็น <b>Found</b> และ <b>Available</b> พร้อมคลาสจาก "
				+ "<b>Generator Accelerator</b> เช่น <b>dev/sixik/generator_accelerator/common/features/FastTarget</b>.</p>"
				+ "<p>ข้อผิดพลาดนี้น่าจะเกิดจาก <b>Generator Accelerator</b> อาจเกี่ยวข้อง "
				+ "กับความไม่เข้ากันระหว่างมอดนั้นและเวอร์ชันบางตัวของ <b>owo-lib</b>.</p>"
				+ "<p><b>ตัวเลือกที่แนะนำ:</b></p>" + "<ul>"
				+ "<li>ลองเริ่มเกมโดยไม่มี <b>Generator Accelerator</b>.</li>"
				+ "<li>หากเกมเริ่มได้ถูกต้อง ให้ถอนการติดตั้งมอดนั้นหรือหาเวอร์ชันอื่น.</li>"
				+ "<li>ลองอัปเดตหรือเปลี่ยนเวอร์ชันของ <b>owo-lib</b> โดยเฉพาะถ้ามอดอื่นพึ่งพา owo ด้วย.</li>"
				+ "<li>ตรวจสอบว่า <b>Generator Accelerator</b>, <b>owo-lib</b>, loader และเวอร์ชัน Minecraft เข้ากันได้.</li>"
				+ "</ul>" + "<p>สาเหตุที่เป็นไปได้มากที่สุดคือ Generator Accelerator พยายามใช้การแก้ไขที่มีลายเซ็น "
				+ "ไม่ตรงกับเวอร์ชันปัจจุบันของคลาสหรือการพึ่งพา.</p>";
	}

	@Override
	public String nombreGeneratorAcceleratorOwoVersion() {
		return "ความขัดแย้งที่อาจเกิดขึ้น: Generator Accelerator และ owo-lib";
	}

	@Override
	public String mensajeFabricRenderingApiFaltaIndium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ขาด renderer ที่เข้ากันได้กับ Fabric Rendering API.</b>"
				+ "<p>บันทึกมีข้อผิดพลาดที่ <b>RendererAccess.getRenderer()</b> ส่งค่า <b>null</b>, "
				+ "ทำให้เกิดความล้มเหลวเมื่อพยายามใช้ <b>Renderer.materialFinder()</b>.</p>"
				+ "<p>ปัญหานี้มักเกิดขึ้นเมื่อ <b>Fabric Rendering API</b> ไม่พร้อมใช้งานอย่างถูกต้อง "
				+ "สาเหตุทั่วไปคือการใช้ <b>Sodium</b> โดยเฉพาะเวอร์ชันเก่าที่แทนที่หรือปิดใช้งานบางส่วนของ "
				+ "ระบบเรนเดอร์ที่มอดอื่นคาดหวัง</p>" + "<p><b>วิธีแก้ที่แนะนำ:</b></p>" + "<ul>"
				+ "<li>ติดตั้งมอด <b>Indium</b>.</li>"
				+ "<li>ตรวจสอบให้แน่ใจว่า <b>Indium</b> เข้ากันได้กับเวอร์ชัน <b>Sodium</b>, Fabric Loader และ Minecraft ของคุณ.</li>"
				+ "<li>หากคุณติดตั้ง Indium แล้ว ให้อัปเดต <b>Sodium</b>, <b>Indium</b> และ <b>Fabric API</b>.</li>"
				+ "<li>หากปัญหายังคงอยู่ ลองใช้งานชั่วคราวโดยไม่มี Sodium เพื่อยืนยันว่าความล้มเหลวเกี่ยวข้องกับ renderer.</li>"
				+ "</ul>"
				+ "<p>Indium โดยปกติจะฟื้นฟูความเข้ากันได้กับ Fabric Rendering API สำหรับมอดที่พึ่งพาระบบนั้น "
				+ "ในขณะที่ติดตั้ง Sodium.</p>";
	}

	@Override
	public String nombreFabricRenderingApiFaltaIndium() {
		return "ขาด Indium / Fabric Rendering API";
	}

	@Override
	public String mensajeEntradaDuplicadaIdModerno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ตรวจพบรายการซ้ำในทะเบียนของ Minecraft.</b>"
				+ "<p>บันทึกมีข้อผิดพลาดคล้ายกับ <b>Duplicate entry on id</b> เช่น "
				+ "<b>current=maroon, previous=mint</b>.</p>"
				+ "<p>ในเวอร์ชันสมัยใหม่ของ Minecraft ข้อผิดพลาดประเภทนี้มักเกิดขึ้นเมื่อมอดสองตัวพยายามลงทะเบียน "
				+ "รายการที่แตกต่างกันโดยใช้ ID ภายในเดียวกัน</p>" + "<p><b>ตัวเลือกที่แนะนำ:</b></p>" + "<ul>"
				+ "<li>ลบมอดตัวหนึ่งที่กำลังลงทะเบียนรายการซ้ำออก.</li>"
				+ "<li>หากคุณรู้จักชื่อที่กล่าวถึงในข้อผิดพลาด ให้ตรวจสอบว่ามอดใดเพิ่มรายการเหล่านั้นและลองใช้โดยไม่มีมอดนั้น.</li>"
				+ "<li>หากคุณไม่รู้จักชื่อ ให้ใช้เครื่องมือ <b>grepr</b> ในแถบด้านข้าง.</li>"
				+ "<li>ใน <b>grepr</b> เปิดใช้งานการค้นหาภายในไฟล์บีบอัด <b>.jar</b>, <b>.zip</b> และ <b>.fpm</b>.</li>"
				+ "<li>เปิดใช้งานการค้นหาใน <b>ไฟล์ไบนารี</b> ด้วย เนื่องจากชื่อหรือ ID บางอย่างอาจอยู่ในคลาสที่คอมไพล์แล้ว.</li>"
				+ "</ul>"
				+ "<p>ค้นหาค่าที่กล่าวถึงในข้อผิดพลาด เช่น <b>maroon</b> หรือ <b>mint</b> เพื่อค้นหามอดที่มีมัน.</p>";
	}

	@Override
	public String nombreEntradaDuplicadaIdModerno() {
		return "รายการซ้ำใน ID ของมอด";
	}

	@Override
	public String nombreOpenGLMemoriaInsuficiente() {
		return "OpenGL – หน่วยความจำวิดีโอไม่เพียงพอ";
	}

	@Override
	public String mensajeOpenGLMemoriaInsuficiente() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft เกิดข้อผิดพลาด OpenGL เนื่องจากขาดหน่วยความจำกราฟิก</b>" + "<p>เกมได้รายงาน:</p>"
				+ "<code>GL_OUT_OF_MEMORY</code>"
				+ "<p>สิ่งนี้มักหมายความว่า การ์ดจอหรือระบบไม่สามารถจองหน่วยความจำได้เพียงพอสำหรับพื้นผิว (textures), เชดเดอร์ (shaders), โมเดล, บัฟเฟอร์ หรือเอฟเฟกต์ภาพ</p>"
				+ "<p><b>สาเหตุทั่วไป:</b></p>" + "<ul>" + "<li>เชดเดอร์ที่หนักเกินไป</li>"
				+ "<li>แพ็กทรัพยากร (Resource packs) ความละเอียดสูง</li>"
				+ "<li>มอดด้านภาพหรือการเรนเดอร์มากเกินไป</li>" + "<li>ระยะการเรนเดอร์ (Render distance) สูงเกินไป</li>"
				+ "<li>VRAM ที่มีอยู่เหลือน้อย</li>" + "<li>ไดรเวอร์การ์ดจอล้าสมัยหรือไม่เสถียร</li>" + "</ul>"
				+ "<p><b>วิธีแก้ที่แนะนำ:</b></p>" + "<ul>" + "<li>ปิดเชดเดอร์ชั่วคราว</li>"
				+ "<li>ใช้แพ็กทรัพยากรความละเอียดต่ำลง</li>" + "<li>ลดระยะการเรนเดอร์และการจำลอง</li>"
				+ "<li>ลดคุณภาพกราฟิก เงา อนุภาค และ mipmaps</li>" + "<li>อัปเดตไดรเวอร์การ์ดจอ</li>"
				+ "<li>ปิดโปรแกรมอื่นที่ใช้ GPU หรือหน่วยความจำมาก</li>" + "</ul>"
				+ "<p>หากข้อผิดพลาดเริ่มเกิดขึ้นหลังจากติดตั้งเชดเดอร์ แพ็กพื้นผิว หรือมอดภาพ เป็นไปได้สูงว่าสิ่งนั้นเป็นสาเหตุ</p>";
	}

	@Override
	public String nombreErrorVerificacionBytecode() {
		return "VerifyError – bytecode หรือ mixin ไม่ถูกต้อง";
	}

	@Override
	public String mensajeErrorVerificacionBytecode(String ubicacion, String razon, String claseSospechosa) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft พบข้อผิดพลาดในการตรวจสอบ bytecode</b>"
				+ "<p>ปัญหานี้มักเกิดขึ้นเมื่อการจัดการ bytecode, transformer หรือ mixin ล้มเหลว</p>"
				+ "<p>เกมได้รายงาน:</p>" + "<code>java.lang.VerifyError</code>"
				+ (ubicacion.length() > 0 ? "<p><b>ตำแหน่ง:</b></p><code>" + ubicacion + "</code>" : "")
				+ (razon.length() > 0 ? "<p><b>เหตุผล:</b></p><code>" + razon + "</code>" : "")
				+ "<p><b>สิ่งที่ควรค้นหา:</b></p>" + "<ul>" + "<li>ค้นหาในตำแหน่งของข้อผิดพลาด</li>"
				+ "<li>ค้นหาประเภทที่กล่าวถึงใน <code>Reason</code></li>"
				+ "<li>ตรวจสอบ stack trace สำหรับคลาสของมอดที่น่าสงสัย</li>"
				+ "<li>ค้นหาชื่อคลาสของมอดรอบๆ ข้อผิดพลาดเพื่อหาแนวทาง</li>" + "</ul>"
				+ "<p><b>การใช้งาน Grepr ที่แนะนำ:</b></p>" + "<ul>" + "<li>เปิด <b>Grepr</b> ในแถบด้านข้าง</li>"
				+ "<li>เปิดใช้งานตัวเลือกเพื่อค้นหาภายในไฟล์ <code>.jar</code>, <code>.zip</code> หรือ <code>.fpm</code></li>"
				+ "<li>ค้นหาชื่อพื้นฐานของคลาส ไม่จำเป็นต้องเป็นแพ็กเกจเต็ม</li>" + "</ul>"
				+ "<p>ตัวอย่าง: หากปรากฏ <code>paquete.Clase</code> ให้ค้นหาเพียง:</p>" + "<code>"
				+ (claseSospechosa.length() > 0 ? claseSospechosa : "Clase") + "</code>"
				+ "<p>สิ่งนี้อาจช่วยค้นหามอดที่มีหรือแก้ไขคลาสนั้น</p>"
				+ "<p>วิธีแก้ทั่วไป: อัปเดตมอดที่ได้รับผลกระทบ ลบมอดที่ไม่เข้ากัน ตรวจสอบ addons ของมอดหลัก หรือลองใช้โดยไม่มีมอดที่ใช้ mixins/transformers กับคลาสเดียวกัน</p>";
	}

	@Override
	public String errorMetodoFinalSobrescrito(String claseQueSobrescribe, String metodoFinal) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ข้อผิดพลาดความเข้ากันได้: มอดพยายามเขียนทับเมธอด final</b>"
				+ "<p>บันทึกมีข้อผิดพลาด <b>IncompatibleClassChangeError</b> พร้อมข้อความ "
				+ "<b>overrides final method</b></p>" + "<p>คลาสที่ได้รับผลกระทบ: <code>" + claseQueSobrescribe
				+ "</code></p>" + "<p>เมธอด final ที่ได้รับผลกระทบ: <code>" + metodoFinal + "</code></p>"
				+ "<p>ข้อผิดพลาดนี้มักเกิดขึ้นเมื่อมอดถูกคอมไพล์สำหรับเวอร์ชันอื่นของ Minecraft, "
				+ "Forge, NeoForge, Fabric, Quilt หรือไลบรารีพื้นฐาน</p>" + "<p><b>สิ่งที่ควรลอง:</b></p>" + "<ul>"
				+ "<li>อัปเดตมอดที่มีคลาสที่ระบุ</li>"
				+ "<li>หากปัญหาเริ่มขึ้นหลังจากอัปเดต Minecraft หรือตัวโหลด ให้ลองใช้เวอร์ชันของมอดที่เข้ากันได้</li>"
				+ "<li>หากคลาสเป็นของ <b>Immersive Portals</b> ให้ตรวจสอบว่า <b>Immersive Portals</b> ตรงกับเวอร์ชัน Minecraft และตัวโหลดของคุณอย่างแม่นยำ</li>"
				+ "<li>หลีกเลี่ยงการผสมมอดที่สร้างมาสำหรับเวอร์ชันต่างกันของตัวโหลดหรือ Minecraft</li>" + "</ul>";
	}

	@Override
	public String nombreErrorMetodoFinalSobrescrito() {
		return "มอดพยายามเขียนทับเมธอด final";
	}
    @Override
    public String errorCrashProvocadoPorComando(String comandoDetectado) {
        return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
                + "Minecraft ถูกปิดโดยคำสั่ง crash</b>"
                + "<p>บันทึกระบุว่ามีการดำเนินการคำสั่ง <code>" + comandoDetectado + "</code></p>"
                + "<p>สิ่งนี้มักหมายความว่าเกมไม่ได้ปิดเนื่องจากข้อผิดพลาดของมอดทั่วไป แต่เพราะมีใครบางคน "
                + "ใช้คำสั่งที่ออกแบบมาเพื่อทำให้เกิด crash โดยเจตนา</p>"
                + "<p><b>สิ่งที่ควรตรวจสอบ:</b></p>"
                + "<ul>"
                + "<li>ตรวจสอบว่าใครเป็นผู้ดำเนินการคำสั่งในคอนโซลหรือในเกม</li>"
                + "<li>หากเป็นการทดสอบ คุณสามารถละเว้น crash นี้ได้</li>"
                + "<li>หากเกิดขึ้นโดยไม่ตั้งใจ ให้ตรวจสอบ command blocks, สคริปต์, datapacks, มอดดูแลระบบ หรือสิทธิ์ของผู้ปฏิบัติการ</li>"
                + "</ul>";
    }

    @Override
    public String nombreCrashProvocadoPorComando() {
        return "Crash ที่เกิดจากคำสั่ง";
    }
	
	
	
	
	
	
	

}
