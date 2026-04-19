package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Lao implements Idioma {
	Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "lo";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "lao";
	}

	@Override
	public String nombre_del_idioma() {
		return "ລາວ";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_laos.png");
	}

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>ນີ້ບໍ່ແມ່ນໂຟນເດີ mods ທີ່ຖືກຕ້ອງ</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError() + "'>ບໍ່ພົບໄຟລ໌ JAR ຂອງ CrashDetector</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>ກຳລັງຄົ້ນຫາສຳລັບ PID: " + String.valueOf(pid)
				+ "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") ຕາຍແລ້ວ!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>ບໍ່ພົບ JVM</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>ການອັບເດດໄດເວີ ATI/AMD ອາດຊ່ວຍໄດ້. ອ່ານຄູ່ມືນີ້ເພື່ອແກ້ໄຂ: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>ຄູ່ມືອັບເດດໄດເວີ</a> https://www.amd.com/es/support/download/drivers.html ດາວໂຫລດ </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>ບາງລຸ້ນເກົ່າອາດມີບັນຫາກັບກຣາຟິກ Nouveau ໃນໜ້າຈໍໂຫລດເລີ່ມຕົ້ນ.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Tienes un problema con tus controladores gráficos. Si tienes una GPU o APU AMD/ATI, actualiza tus controladores gráficos AMD. Si tienes una tarjeta gráfica NVIDIA, asegúrate de marcar el juego y todas las instancias de javaw.exe para usar la tarjeta gráfica dedicada. Lee esta guía: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>ຄູ່ມືອັບເດດໄດເວີ</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Tu ventana FML Early está fallando. Para cambiar esto, ve a (.minecraft/config/fml.toml) y edita earlyWindowProvider para que sea earlyWindowProvider=\"none\". Si estás en una Mac M1, también asegúrate de estar usando una versión ARM de Java, no una versión Intel x64. Este también es un problema común si tienes controladores desactualizados. Consulta esta guía si estás en Windows y desactivar esto no funciona: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>ຄູ່ມືອັບເດດໄດເວີ</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError() + "'>ທ່ານບໍ່ມີ dependencies ທີ່ຈຳເປັນຄົບ:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "ຮ້ອງຂໍໂດຍ").replace("Expected range", "ຊ່ວງທີ່ຄາດໄວ້") + "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>ລາຍງານ CrashDetector ຂອງທ່ານຢູ່ນີ້ <a href='"
				+ archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>ເບິ່ງລາຍງານ</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>ນີ້ແມ່ນ GUI ຂອງ CrashDetector. ຖ້າເກມປິດໂດຍບໍ່ມີບັນຫາ ໃຫ້ລະເລີຍຂໍ້ຄວາມນີ້.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>ເບິ່ງລາຍງານ</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>ເບິ່ງລາຍງານໃນເຄື່ອງຂອງທ່ານຜ່ານບຣາວເຊີ.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "ແບ່ງປັນລາຍງານ";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "ແບ່ງປັນລາຍງານ. Sus registros se cargarán en securelogger.net y tu informe se cargará a otro sitio.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ກວດພົບໄຟລ໌ JAR ທີ່ມີບັນຫາ (ໃຫ້ຄວາມສຳຄັນ FATAL ກ່ອນ, ຈາກນັ້ນລະດັບສູງ ແລະ ໝາຍເລກບັນທັດຕ່ຳ):</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> ລະດັບ:</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ອາດຈະຮ້າຍແຮງ:</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ກວດພົບ ModIDs ທີ່ມີບັນຫາ (ໃຫ້ຄວາມສຳຄັນ FATAL ກ່ອນ, ຈາກນັ້ນລະດັບຕ່ຳ ແລະ ໝາຍເລກບັນທັດຕ່ຳ):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ກວດພົບ packages ທີ່ມີບັນຫາ (ໃຫ້ຄວາມສຳຄັນ FATAL ກ່ອນ, ຈາກນັ້ນລະດັບຕ່ຳ ແລະ ໝາຍເລກບັນທັດຕ່ຳ):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes Clases fatales (FATAL), es muy importante, causas comunes son Coremods Malos o Fatals Dependencias. Puedes Usar QuickFix para buscar para mods con las clases fatales. Clases fatales faltantes detectadas:</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ເນື້ອຫາໃນ {} (ສິ່ງທີ່ສຳຄັນສຸດຢູ່ຂ້າງເທິງ, ສະແດງພຽງ 20 ລາຍການທຳອິດ):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ກວດພົບການຕັ້ງຄ່າ SpongeMixin ທີ່ມີບັນຫາ: "
				+ "</b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError() + "'>ທ່ານມີ Mods ທີ່ມີ package ຫຼື ໂຟນເດີຊ້ຳກັນ. "
				+ "ສາມາດແກ້ໄຂໄດ້ໂດຍລົບໂຟນເດີອອກຈາກໄຟລ໌ jar. " + "ທ່ານສາມາດເປີດ jar ໂດຍໃຊ້ WinRAR ຫຼື 7z, "
				+ "ຫຼືປ່ຽນນາມສະກຸນເປັນ .zip ແລ້ວລົບໂຟນເດີ " + "ແລ້ວປ່ຽນກັບເປັນ .jar.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ທ່ານມີ Mods ຊ້ຳກັນ</b> "
				+ linea.replace("from mod files", "ຈາກໄຟລ໌ mod ");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>MinecraftForge ພົບ mod ທີ່ນ່າສົງໄສ ແລະອາດມີບັນຫາ:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV ຕ້ອງການ lithostitched, ທ່ານສາມາດຕິດຕັ້ງໄດ້ທີ່: "
				+ "<a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ເພື່ອໃຊ້ Iris Shaders ຫຼື Oculus ຈຳເປັນຕ້ອງມີ SODIUM ຫຼືຕົວແທນ (Rubidium, Embedium, Bedium)</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ມີບັນຫາກັບ KubeJS extension </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບບັນຫາກັບ driver NVIDIA ໃນ Windows ລຸ້ນເກົ່າ." + "</span><br/><br/>"
				+ "ເພື່ອໃຫ້ Minecraft (ແລະ JVM) ໃຊ້ GPU NVIDIA ທີ່ແຮງ, ໃຫ້ປະຕິບັດຕາມ:<br/><br/>"
				+ "1. <strong>ຫາໄຟລ໌ Java:</strong><br/>" + "   - ໂປຣແກຣມໃຊ້: " + obtenerRutaJava() + "<br/>"
				+ "   - ຖ້າບໍ່ເຫັນ, ຄົ້ນຫາ 'java.exe'<br/><br/>" + "2. <strong>ເປີດ NVIDIA Control Panel:</strong><br/>"
				+ "   - ຄລິກຂວາ Desktop → NVIDIA Control Panel<br/><br/>" + "3. <strong>ກຳນົດ GPU:</strong><br/>"
				+ "   - Manage 3D Settings → Program Settings<br/>" + "   - Add → ເລືອກ java.exe<br/>"
				+ "   - ເລືອກ High Performance NVIDIA<br/><br/>" + "4. <strong>ບັນທຶກ</strong><br/>"
				+ "5. <strong>Restart Minecraft</strong><br/><br/>" + "ໝາຍເຫດ: ຕ້ອງມີ driver NVIDIA ລ່າສຸດ.";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບບັນຫາກັບ driver NVIDIA ໃນ Windows 11/Server 2025+" + "</span><br/><br/>" + "ວິທີແກ້ໄຂ:<br/><br/>"
				+ "1. <strong>ຫາ java.exe:</strong><br/>" + "   - ໃຊ້: " + obtenerRutaJava() + "<br/><br/>"
				+ "2. <strong>ເປີດ Settings:</strong><br/>" + "   - Win + I → System → Display → Graphics<br/><br/>"
				+ "3. <strong>ກຳນົດ GPU:</strong><br/>" + "   - Browse → ເລືອກ java.exe<br/>"
				+ "   - ເລືອກ High Performance NVIDIA<br/><br/>" + "4. <strong>Save</strong><br/>"
				+ "5. <strong>Restart Minecraft</strong><br/><br/>" + "ໝາຍເຫດ: ກວດ driver NVIDIA ໃຫ້ຖືກຕ້ອງ.";
	}

	@Override
	public String segundo60Tick() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ເຊີບເວີ ຫຼື ໂລກຂອງທ່ານມີ tick ເກີນ 60 ວິນາທີ. "
				+ "ອາດເກີດຈາກ mods ຫຼື hardware ບໍ່ແຮງ.</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError() + "'>RAM/Memory ບໍ່ພໍ. ກະລຸນາເພີ່ມ.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Theseus ຍັງມີບັນຫາອື່ນໆ ຮວມທັງຄວາມຜິດພາດໃນການລົບ mods ເມື່ອທ່ານພະຍາຍາມ. "
				+ "ຖ້າທ່ານຕ້ອງການໃຊ້ໄຟລ໌ mrpack, ທ່ານສາມາດໃຊ້ launcher ອື່ນໆ ເຊັ່ນ Prism Launcher (modrinth.com ເທົ່ານັ້ນ), "
				+ "ATLauncher (modrinth.com ເທົ່ານັ້ນ) ຫຼື Hello Minecraft Launcher (ຮອງຮັບ modrinth.com ແລະ bbsmc.net).</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>ທ່ານກຳລັງໃຊ້ \"Omitir inicio Launcher\" (CurseForge App). "
				+ "ບາງຄັ້ງສິ່ງນີ້ອາດກ່ອນໃຫ້ເກີດບັນຫາທີ່ກວດພົບຍາກ. "
				+ "ສາເຫດແມ່ນຈາກຕົວເລືອກ \"Omitir inicio Launcher\" ໃນ CurseForge ຮຸ່ນເກົ່າ ຫຼື ຮຸ່ນໃໝ່. "
				+ "ປິດມັນແລະໃຊ້ \"Mojang Launcher\" ໃນການຕັ້ງຄ່າ. " + "ທ່ານສາມາດເບິ່ງວິດີໂອນີ້ (ພາສາອັງກິດ, ໄປ 1:11) "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>ທີ່ນີ້</a>.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>ຄຳເຕືອນ: ພົບການຂາດຫາຍຂອງ classes (ຄຳເຕືອນ). "
				+ "ສ່ວນໃຫຍ່ບໍ່ເປັນບັນຫາ ແຕ່ບໍ່ແມ່ນທຸກກໍລະນີ. " + "ມັນແຕກຕ່າງຈາກການຂາດ class ແບບຮ້າຍແຮງ. "
				+ "coremods ທີ່ບໍ່ດີ ຫຼື dependencies ທີ່ຂາດ ແມ່ນສາເຫດທົ່ວໄປ. "
				+ "ທ່ານສາມາດໃຊ້ QuickFix ເພື່ອຊອກຫາ mods ທີ່ມີ classes ເຫຼົ່ານີ້. " + "Classes ທີ່ຂາດ:</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ບໍ່ພົບຜົນລັບ</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>ຕຳແໜ່ງ Logs:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>ນີ້ແມ່ນຜົນການກວດສອບຂອງທ່ານ. "
				+ "ອ່ານຢ່າງລະມັດລະວັງ — ສາເຫດທີ່ແທ້ຈິງມັກຢູ່ໃນການກວດສອບ 1 ຫຼື 2. "
				+ "ຂໍ້ຜິດພາດອື່ນໆ (3+) ມັກເປັນ cascade ແລະສາມາດມອງຂ້າມໄດ້. "
				+ "ບັນຫາເກີດເປັນຊັ້ນໆ, ດັ່ງນັ້ນການແກ້ໄຂສາເຫດທີ່ຖືກຕ້ອງຈະແກ້ໄຂຂໍ້ຜິດພາດນີ້, "
				+ "ແຕ່ອາດມີບັນຫາໃໝ່ໃນພາຍຫຼັງເພາະບັນຫາໜຶ່ງອາດປິດບັງອີກບັນຫາໜຶ່ງ.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ກະລຸນາໃຊ້ Java 17 ສຳລັບ Minecraft 1.17-1.20.4 ແລະ Java 21 ສຳລັບຮຸ່ນໃໝ່. "
				+ "ໃຊ້ Java 8 ສຳລັບຮຸ່ນເກົ່າ. "
				+ "[ຄູ່ມື](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). "
				+ "ຖ້າຍັງມີບັນຫາ ອາດເປັນເນື່ອງຈາກ mod ທີ່ໃຊ້ໄຟລ໌ເກົ່າ ຫຼື ໃໝ່ເກີນໄປ.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 ແລະສູງກວ່າບໍ່ເຮັດວຽກກັບ Minecraft ກ່ອນ 1.20.5 " + "ເນື່ອງຈາກ ASM ບໍ່ຮອງຮັບ.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java ລ້າສະໄໝ</b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ທ່ານຈຳເປັນຕ້ອງມີ JPMS Module " + mod_necesitas
				+ " ຈາກ " + submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ບໍ່ສາມາດເອີ້ນ " + metodo + " ໄດ້ ເນື່ອງຈາກ "
				+ objeto + " ເປັນ null</b>";
	}

	@Override
	public String analisisAvanzado() {
		return "ການວິເຄາະຂັ້ນສູງ";
	}

	@Override
	public String seleccionarCarpeta() {
		return "ເລືອກໂຟນເດີ";
	}

	@Override
	public String cadenaBusqueda() {
		return "ຄຳຄົ້ນຫາ";
	}

	@Override
	public String usarRegex() {
		return "ໃຊ້ Regex";
	}

	@Override
	public String ignorarMayusculas() {
		return "ບໍ່ສົນໃຈຕົວພິມໃຫຍ່";
	}

	@Override
	public String buscar() {
		return "ຄົ້ນຫາ";
	}

	@Override
	public String busquedaEnProgreso() {
		return "ກຳລັງຄົ້ນຫາ";
	}

	@Override
	public String noSeEncontraronResultados() {
		return "ບໍ່ພົບຜົນລັບ";
	}

	@Override
	public String errorBusqueda() {
		return "ຜິດພາດໃນການຄົ້ນຫາ";
	}

	// @Override
	// public String noRegistroDeLauncher() {
//		// TODO Auto-generated method stub
//		return new String ("ບໍ່ພົບ launcher logs! ສິ່ງນີ້ອາດເຮັດໃຫ້ການກວດສອບຍາກຂຶ້ນ.\n"
//				+ "                \n"
//				+ "                ເພື່ອໄດ້ logs ທີ່ຖືກຕ້ອງ:\n"
//				+ "                - MultiMC/PolyMC/PrismLauncher/PollyMC/UltimMC: ໝາຍເຫດ: logs ທີ່ຖືກກວດພົບອັດຕະໂນມັດບໍ່ແມ່ນອັນທີ່ຖືກຕ້ອງ.\n"
//				+ "                  1. ເປີດໜ້າຕ່າງ instance\n"
//				+ "                  2. ໄປທີ່ສ່ວນ \"Minecraft Log\"\n"
//				+ "                  3. ຄລິກຂວາແລ້ວຄັດລອກເນື້ອຫາທັງໝົດ\n"
//				+ "                \n"
//				+ "                - LauncherFenix:\n"
//				+ "                  1. ເປີດ \"Developer Console\"\n"
//				+ "                  2. ຄັດລອກເນື້ອຫາທັງໝົດ\n"
//				+ "                \n"
//				+ "                - CurseForgeApp:\n"
//				+ "                  1. ເລີ່ມເກມໃໝ່ໂດຍບໍ່ຂ້າມ launcher\n"
//				+ "                  \n"
//				+ "                ");
	// }

	@Override
	public String omitirYCerrar() {
		return "ຂ້າມແລະປິດ";
	}

	@Override
	public String guardarYCerrar() {
		return "ບັນທຶກແລະປິດ";
	}

	@Override
	public String pegaLosRegistrosAqui() {
		return "ວາງ logs ທີ່ນີ້";
	}

	@Override
	public String archivo() {
		return "ໄຟລ໌";
	}

	@Override
	public String incluir() {
		return "ຮວມເຂົ້າ";
	}

	@Override
	public String abrir() {
		return "ເປີດ";
	}

	@Override
	public String endpointDeInforme() {
		return "Endpoint ຂອງລາຍງານ";
	}

	@Override
	public String sitoDeLogging() {
		return "ເວັບໄຊທ໌ Logging:";
	}

	@Override
	public String apiDeLogging() {
		return "API Logging:";
	}

	@Override
	public String anonimizarRegistros() {
		return "ປົກປິດຂໍ້ມູນໃນ logs (Beta)";
	}

	@Override
	public String botonDeCompartirInforme() {
		return "ແບ່ງປັນລາຍງານ ແລະ logs ທັງໝົດທີ່ເລືອກ";
	}

	@Override
	public String arco() {
		return "ກ່ອງຂໍ້ຄວາມນີ້ອະນຸຍາດໃຫ້ທ່ານແບ່ງປັນ logs ໂດຍໃຊ້ API SecureLogger "
				+ "ທີ່ <a href=\"https://securelogger.net\">securelogger.net</a>. "
				+ "ເມື່ອກົດປຸ່ມແບ່ງປັນລາຍງານ, ລາຍງານຂອງທ່ານຈະຖືກສົ່ງໄປຫາ endpoint ທີ່ເລືອກ "
				+ "(ຄ່າເລີ່ມຕົ້ນ asbestosstar.egoism.jp) " + "(ສາມາດປ່ຽນໄດ້ທາງດ້ານລຸ່ມ). "
				+ "ທ່ານສາມາດແບ່ງປັນ logs ທີ່ເລືອກທັງໝົດພ້ອມກັບລາຍງານ. " + "ຖ້າບໍ່ຕ້ອງການອັບໂຫຼດ ຢ່າໃຊ້ກ່ອງນີ້. "
				+ "ພວກເຮົາບໍ່ໄດ້ປະມວນຜົນລາຍງານຂອງທ່ານໃນ endpoint ທາງການ "
				+ "(<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\">link</a>); "
				+ "ພຽງແຕ່ລົບລິ້ງທີ່ບໍ່ອະນຸຍາດ. " + "ຊອດໂຄດຢູ່ນີ້: "
				+ "<a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">source</a>. "
				+ "ນີ້ໃຊ້ເພື່ອສະແດງຂໍ້ມູນກ່ຽວກັບ crash ແລະລິ້ງ logs. "
				+ "ຢ່າງໃດກໍຕາມ ທ່ານສາມາດໃຊ້ endpoint ກຳນົດເອງໄດ້. " + "ທ່ານກຳລັງໃຊ້ລາຍງານຈາກ "
				+ Config.obtenerInstancia().obtenerSitoDeInformes() + " ແລະ logs ຈາກ "
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". "
				+ "ທ່ານສາມາດແບ່ງປັນ logs ແຍກຕ່າງຫາກໄດ້ໂດຍກົດປຸ່ມແບ່ງປັນ. "
				+ "CrashDetector ມີການປົກປິດຂໍ້ມູນ logs ເພື່ອລົບ username, UUID, tokens, IP ແລະອື່ນໆ "
				+ "ແຕ່ບໍ່ແມ່ນສົມບູນ. " + "ຜູ້ສ້າງ modpack ສາມາດປິດຟັງຊັນນີ້ໄດ້. "
				+ "ທ່ານເປັນຜູ້ຄວບຄຸມຂໍ້ມູນຂອງທ່ານເອງ. " + "ເວັບໄຊ logs ເປັນຂອງ third-party. "
				+ "CrashDetector ເປັນແຄ່ interface ເທົ່ານັ້ນ. " + "ກະລຸນາຮູ້ກ່ຽວກັບ RGPD ແລະ ARCO. "
				+ "ຖ້າທ່ານຢູ່ເອີຣົບ ສາມາດໃຊ້ " + "<a href=\"https://securelogger.top\">securelogger.top</a>. "
				+ "ເບິ່ງຂໍ້ມູນກົດໝາຍເພີ່ມເຕີມ: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>, "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">RGPD</a>, "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">Japan Data Policy</a>.";
	}

	@Override
	public String enlaceDelReporte() {
		return "ລິ້ງລາຍງານ:";
	}

	@Override
	public String guardarConfigDeCompartir() {
		return "ບັນທຶກການຕັ້ງຄ່າການແບ່ງປັນ";
	}

	@Override
	public String registroDemasiadoGrande() {
		return "log ນີ້ໃຫຍ່ເກີນໄປສຳລັບເວັບໄຊນີ້. ກະລຸນາເລືອກອື່ນແລະລອງໃໝ່.";
	}

	@Override
	public String errorConPublicarRegistro(String error) {
		return "ຜິດພາດໃນການສົ່ງ log: " + error;
	}

	@Override
	public String apiDeRegistroNoExiste() {
		return "API ຂອງ log ບໍ່ມີ. ກະລຸນາປ່ຽນ API ໃນການຕັ້ງຄ່າ.";
	}

	@Override
	public String errorSSL() {
		return "ພົບ SSL Error. ມັກເກີດກັບ Java ລຸ້ນເກົ່າ " + "(ເຊັ່ນ Java 8 ໃນ Minecraft Launcher ເກົ່າ). "
				+ "ມັນສົ່ງຜົນກັບ MinecraftForge installer, "
				+ "ການແບ່ງປັນລາຍງານ, mods ທີ່ໃຊ້ internet ແລະບາງເວັບໄຊ logs. " + "ຖ້າເກີດຂຶ້ນ ໃຫ້ໃຊ້ screenshot ແທນ "
				+ "ຫຼືເລືອກເວັບໄຊ logs ທີ່ຮອງຮັບ Java 8.";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JavaFML ບໍ່ກົງກັນ: ຕ້ອງການເວີຊັນ " + requerido
				+ ", ແຕ່ພົບ " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "ຄຳເຕືອນ! JavaFML ຕ້ອງການເວີຊັນ Minecraft Forge ທີ່ກຳນົດ</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ໄຟລ໌ JAR '" + archivoJar
				+ "' ຕ້ອງການ language provider '" + proveedor + "' ເວີຊັນ '" + requerido + "' ຫຼືສູງກວ່າ, ແຕ່ພົບ '"
				+ encontrado + "'.</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ຄຳເຕືອນ! Crash Assistant ແມ່ນ malware detector ປອມ. " + "ມັນບລັອກການເປີດເກມໂດຍຈົງໃຈ. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>MalwareMod.java</a> "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>JarInJarHelper.java</a>. "
				+ "ມັນບໍ່ກວດສອບວ່າທ່ານໃຊ້ log site ໃດ. "
				+ "ກະລຸນາກວດສອບ source code ເອງ ແລະຢ່າເຊື່ອຖືພຽງແຕ່ຄຳກ່າວອ້າງ.</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "mod '" + idMod
				+ "' ລົ້ມເຫລວ ເນື່ອງຈາກບໍ່ພົບ class: '" + claseNoEncontrada + "'. ກວດສອບ dependencies.</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Watermedia ບລັອກການໃຊ້ງານກັບ TLauncher.</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ທ່ານກຳລັງໃຊ້ Optifine ທີ່ບໍ່ກົງກັບ Minecraft ຮຸ່ນນີ້.</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ບໍ່ສາມາດໂຫຼດ ModLauncher Service: </b>"
				+ servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ຜິດພາດໃນການອ່ານ JSON '" + recurso
				+ "' ຈາກ JAR '" + archivoJar + "'.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "mod '" + modId + "' ຕ້ອງການ '"
				+ dependencia + "' ເວີຊັນ '" + requerido + "' ຫຼືສູງກວ່າ, ແຕ່ພົບ '" + actual + "'.</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "GPU ຂອງທ່ານບໍ່ຮອງຮັບ OpenGL ທີ່ຕ້ອງການ. "
				+ "ອັບເດດ driver ຫຼືປ່ຽນ GPU.</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "ເພີ່ມ RAM ຫຼືຫຼຸດ mods/plugins</b>";
	}

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "ພົບ JVM 32-bit: ໃຊ້ RAM ໄດ້ສູງສຸດ 4GB. "
				+ "ຄວນໃຊ້ JVM 64-bit.</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "PermGen memory error. ເພີ່ມ memory ຫຼືຫຼຸດ classes.</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Java 8 ບໍ່ກົງກັບຮຸ່ນໃໝ່</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+ ບໍ່ຮອງຮັບ — ໃຊ້ Java 8 ສຳລັບ Forge ເກົ່າ</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ຈຳເປັນຕ້ອງໃຊ້ Java 8 (52.0)</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ບັນຫາຄວາມກົງກັນ: blocks ບໍ່ຮອງຮັບໃນຮຸ່ນນີ້. "
				+ "ກວດສອບ mods ແລະ server.</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ຜິດພາດໃນການຕັ້ງຄ່າຈໍ: "
				+ "ບໍ່ສາມາດຕັ້ງໂໝດຈໍໄດ້. " + "ກວດສອບ:</b>" + "<br>- ການຕັ້ງຄ່າຫຼາຍຈໍ" + "<br>- ອັບເດດ driver ກາດຈໍ"
				+ "<br>- ຄວາມລະອຽດທີ່ລະບົບຮອງຮັບ";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ຜິດພາດໃນການຕັ້ງຄ່າ Java: "
				+ "ມີຄວາມຂັດແຍ່ງໃນ GC options. " + "ຢ່າໃຊ້ GC algorithms ຫຼາຍອັນພ້ອມກັນໃນ JVM parameters.</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ຜິດພາດ NightConfig/Forge: "
				+ "ໄຟລ໌ config ເສຍຫາຍ ຫຼື ບໍ່ຄົບ. " + "ມັກເກີດຈາກໄຟລ໌ config ວ່າງ (0 bytes) ໃນໂຟນເດີ 'config'. "
				+ "ໃນຫຼາຍກໍລະນີ Night Config Fixes ຈະແກ້ໄດ້, " + "ແຕ່ຖ້າເປັນຮຸ່ນ custom ຈະຕ້ອງລົບໄຟລ໌ເອງ. "
				+ "ພົບໄດ້ບໍ່ຫາຍໃນ Forge ເກົ່າ ແລະ Fabric mods ເກົ່າ. " + "ລາຍລະອຽດເພີ່ມ: "
				+ "<a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a>.</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ພົບບັນຫາ Intel HD Graphics. ວິທີແກ້:</b>"
				+ "<br>1. ອັບເດດ driver ຈາກ <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a>"
				+ "<br>2. Minecraft → Video Settings → ເປີດ VBOs ແລະ VSync" + "<br>3. ກຳນົດ RAM 1GB-2GB"
				+ "<br>4. ປິດ antivirus/firewall ຊົ່ວຄາວ";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "ຄຳເຕືອນ: ພົບ class ຂາດ";
	}

	public String nombre_de_bloque_teselado() {
		return "ຜິດພາດການ render blocks";
	}

	public String nombre_de_contenido_de_stacktrace() {
		return "ການວິເຄາະ stack trace";
	}

	public String nombre_de_cursed_consola() {
		return "Consola CurseForge ບໍ່ຄົບ";
	}

	public String nombre_de_early_window() {
		return "ຜິດພາດຫນ້າຕ່າງເລີ່ມຕົ້ນ (FMLEarlyWindow)";
	}

	public String nombre_de_drivers() {
		return "ບັນຫາ driver ກາດຈໍ";
	}

	public String nombre_de_error_de_config_mcforge() {
		return "ການຕັ້ງຄ່າ MCForge ເສຍຫາຍ";
	}

	public String nombre_de_error_de_monitor_lwjgl() {
		return "ຜິດພາດໂໝດຈໍ (LWJGL)";
	}

	public String nombre_de_fabricmc_runtime_error_provided_by() {
		return "ຜິດພາດໃນການເລີ່ມ FabricMC";
	}

	public String nombre_de_falta_module_jmps() {
		return "ໂມດູນ JPMS ຂາດ";
	}

	public String nombre_de_faltar_de_clases() {
		return "class ສຳຄັນຂາດ";
	}

	public String nombre_de_faltas_dependencias_de_modlauncher() {
		return "dependencies ຂອງ ModLauncher ຂາດ";
	}

	public String nombre_de_java_versiones() {
		return "ເວີຊັນ Java ບໍ່ກົງກັນ";
	}

	public String nombre_de_faltar_de_kubejs_resourcepack() {
		return "ຜິດພາດ resource KubeJS";
	}

	public String nombre_de_lenguaje_proveedor_check() {
		return "language provider ບໍ່ກົງກັນ";
	}

	public String nombre_de_faltar_de_liyhostictchctov() {
		return "Litchhost";
	}

	public String nombre_de_malware_falso_crash_assistant() {
		return "ພົບ malware ປອມ";
	}

	public String nombre_de_mcforge_mod_sespechoso() {
		return "ພົບ mod ທີ່ນ່າສົງໄສ";
	}

	public String nombre_de_mods_duplicados_modlauncher() {
		return "mods ຊ້ຳໃນ ModLauncher";
	}

	public String nombre_de_modules_duplicados_jmps() {
		return "ການຊ້ຳກັນຂອງໂມດູນ JPMS";
	}

	public String nombre_de_necesitas_sodium() {
		return "ຕ້ອງການ Sodium ສຳລັບ Iris";
	}

	public String nombre_de_no_puede_analizar_json_de_registro() {
		return "ບໍ່ສາມາດວິເຄາະ JSON log";
	}

	public String nombre_de_no_tiene_memoria() {
		return "ຄວາມຈຳບໍ່ພໍ";
	}

	public String nombre_de_null_pointer() {
		return "NullPointerException";
	}

	public String nombre_de_opciones_java_gc_invalidas() {
		return "GC options Java ບໍ່ຖືກຕ້ອງ";
	}

	public String nombre_de_optifine_obsoleta() {
		return "OptiFine ເກົ່າ/ບໍ່ກົງກັນ";
	}

	public String nombre_de_60_segundo_trick() {
		return "server tick ວິກິດ (60s)";
	}

	public String nombre_de_servicio_de_modlauncher_no_funciona() {
		return "ModLauncher service ບໍ່ເຮັດວຽກ";
	}

	public String nombre_de_spongemixin_configs_problematicos() {
		return "config SpongeMixing ມີບັນຫາ";
	}

	public String nombre_de_theseus() {
		return "Theseus ບໍ່ກົງກັນ";
	}

	public String nombre_de_watermedia_tl() {
		return "TLauncher ບໍ່ຖືກຮອງຮັບໂດຍ WATERMeDIA";
	}

	@Override
	public String auditorias_transformer() {
		return "ການກວດສອບ Transformer";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ນີ້ແມ່ນຜົນການກວດສອບ Transformer ໃນ Vanilla Launcher. "
				+ "ມັນມັກຈະບໍ່ແມ່ນທີ່ແມ່ນຍຳເທົ່າ StackTrace analyzer.</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "ຟັງຊັນນີ້ຄົ້ນຫາ mods ທີ່ສ້າງດ້ວຍ MCreator. " + "ແມ່ນວ່າຫຼາຍ mods ຈະດີ, ແຕ່ບາງອັນອາດມີບັນຫາ. "
				+ "ຊ່ວຍໃຫ້ລະບຸໄດ້.";
	}

	@Override
	public String escanear() {
		return "ສະແກນ";
	}

	@Override
	public String cargando() {
		return "ກຳລັງໂຫລດ";
	}

	@Override
	public String inicioApp() {
		return "ເລີ່ມແອັບ/ເກມ";
	}

	@Override
	public String ajustesCrashDetector() {
		return "ການຕັ້ງຄ່າ CrashDetector";
	}

	@Override
	public String confidencialidad() {
		return "ຄວາມເປັນສ່ວນຕົວ";
	}

	@Override
	public String tooltip() {
		return "Tooltip";
	}

	@Override
	public String config() {
		return "ການຕັ້ງຄ່າ";
	}

	@Override
	public String abrirCarpeta() {
		return "ເປີດໂຟນເດີ";
	}

	@Override
	public String actualizar() {
		// TODO Auto-generated method stub
		return "ອັບເດດ";
	}

	@Override
	public String anadirRegistro() {
		// TODO Auto-generated method stub
		return "ເພີ່ມ log";
	}

	@Override
	public String usarIdiomaDelSistema() {
		// TODO Auto-generated method stub
		return "ໃຊ້ພາສາຂອງລະບົບ";
	}

	@Override
	public String volver() {
		// TODO Auto-generated method stub
		return "ກັບຄືນ";
	}

	@Override
	public String colorFondo() {
		return "ສີພື້ນຫຼັງ (#RRGGBB):";
	}

	@Override
	public String colorTexto() {
		return "ສີຂໍ້ຄວາມ (#RRGGBB):";
	}

	@Override
	public String colorBoton() {
		return "ສີປຸ່ມ (#RRGGBB):";
	}

	@Override
	public String colorCajaTexto() {
		return "ສີກ່ອງຂໍ້ຄວາມ (#RRGGBB):";
	}

	@Override
	public String colorEnlace() {
		return "ສີລິ້ງ (#RRGGBB):";
	}

	@Override
	public String colorTitulosConsolas() {
		return "ສີຫົວຂໍ້ຄອນໂຊນ (#RRGGBB):";
	}

	@Override
	public String colorError() {
		return "ສີຂໍ້ຜິດພາດ (#RRGGBB):";
	}

	@Override
	public String colorAdvertencia() {
		return "ສີຄຳເຕືອນ (#RRGGBB):";
	}

	@Override
	public String colorInfo() {
		return "ສີຂໍ້ມູນ (#RRGGBB):";
	}

	@Override
	public String colorTitulo() {
		return "ສີຫົວຂໍ້ (#RRGGBB):";
	}

	@Override
	public String colorEnlaceTexto() {
		return "ສີຂໍ້ຄວາມລິ້ງ (#RRGGBB):";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "ເປີດ CrashDetector ເມື່ອເກີດຄວາມລົ້ມເຫຼວເທົ່ານັ້ນ";
	}

	@Override
	public String activar_parche() {
		return "ເປີດໃຊ້ Patch";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "ບໍ່ມີວິທີແກ້ໄຂ";
	}

	@Override
	public String error() {
		return "ຜິດພາດ";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "ຜິດພາດໃນການລົບ Jar";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "ລົບ Jar ສຳເລັດ";
	}

	@Override
	public String exito() {
		return "ສຳເລັດ";
	}

	@Override
	public String eliminar() {
		return "ລົບ";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "ຜິດພາດໃນການລົບ package";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "ລົບ package ສຳເລັດ";
	}

	@Override
	public String eliminar_paquete() {
		return "ລົບ package";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "ບໍ່ພົບ mods ທີ່ມີ package ນີ້";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "ບໍ່ສາມາດລົບ package ໄດ້";
	}

	@Override
	public String eliminar_jar() {
		return "ລົບ jar";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "ບໍ່ພົບ mods ຊ້ຳກັນ";
	}

	@Override
	public String archivo_no_encontrado() {
		return "ບໍ່ພົບໄຟລ໌";
	}

	@Override
	public String directorio_eliminado() {
		return "ລົບໄດເຣັກທໍຣີແລ້ວ";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "ຜິດພາດໃນການລົບ jar ຊ້ອນຢູ່";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "ບໍ່ພົບໄຟລ໌ພາຍໃນ";
	}

	@Override
	public String archivo_eliminado() {
		return "ລົບໄຟລ໌ແລ້ວ";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "ຜິດພາດໃນການລົບໄຟລ໌";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "ໄຟລ໌ພາຍນອກບໍ່ຖືກຕ້ອງ";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "ລົບອົງປະກອບ mod ແລ້ວ";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "ຜິດພາດໃນການແທນທີ່ jar ພາຍນອກ";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "ຜິດພາດໃນການລົບອົງປະກອບ mod";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "ຜິດພາດໃນການລົບໄດເຣັກທໍຣີ";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "ຮູບແບບບໍ່ຖືກຕ້ອງສຳລັບ jar ຊ້ອນຢູ່";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "ລົບ jar ຊ້ອນຢູ່ແລ້ວ";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "ຜິດພາດໃນການລ້າງໄຟລ໌ຊົ່ວຄາວ";
	}

	@Override
	public String mensaje_de_trace_fatal_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ຂໍ້ຄວາມ Trace Fatal ສຸດທ້າຍ (ບໍ່ໄດ້ແປ):</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ຂໍ້ຄວາມ Trace ສຸດທ້າຍ (ບໍ່ໄດ້ແປ):</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "ທ່ານສາມາດຄົ້ນຫາໃນຖານຂໍ້ມູນ WaifuNeoForge ເພື່ອຫາ mods. " + "ເລືອກເວີຊັນເກມ, mod loader ແລະ class. "
				+ "ໃຊ້ການຈັບຄູ່ທີ່ໃກ້ຄຽງທີ່ສຸດ. " + "ທ່ານສາມາດຄົ້ນຫາໄດ້ໜຶ່ງຄັ້ງຕໍ່ນາທີ.";
	}

	@Override
	public String solucionFaltasClases() {
		return "ທ່ານສາມາດຄົ້ນຫາໃນຖານຂໍ້ມູນ WaifuNeoForge ເພື່ອຫາ mods. " + "ເລືອກເວີຊັນເກມ, mod loader ແລະ class. "
				+ "ໃຊ້ການຈັບຄູ່ທີ່ໃກ້ຄຽງທີ່ສຸດ. " + "ທ່ານສາມາດຄົ້ນຫາໄດ້ໜຶ່ງຄັ້ງຕໍ່ນາທີ.";
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "launcher ທັງສອງມີ Java ທີ່ຖືກຕ້ອງບາງສ່ວນ ແຕ່ບໍ່ຄົບທຸກເວີຊັນ. "
				+ "ທ່ານສາມາດຕິດຕັ້ງ Java ເວີຊັນທີ່ຖືກຕ້ອງຜ່ານ package manager ໃນລະບົບຂອງທ່ານ ຫຼືໃຊ້ປຸ່ມທີ່ມີໃຫ້.";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>mod ຂາດ animation: " + "</b>";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException: ຂາດ animation";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "ບໍ່ພົບ mods ທີ່ຈະລົບ";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "ແທນ GC options ທີ່ຂັດແຍ່ງດ້ວຍ -XX:+UseG1GC";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "ເພີ່ມ heap memory ໂດຍໃຊ້ option -Xmx.";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "ເພີ່ມ permgen memory ໂດຍໃຊ້ option -XX:MaxPermSize.";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "ໃຊ້ JVM 64-bit ເພື່ອເພີ່ມ memory ທີ່ໃຊ້ໄດ້.";
	}

	@Override
	public String optimizarCodigo() {
		return "ປັບແຕ່ງ code ໃຫ້ດີຂຶ້ນເພື່ອຫຼຸດການໃຊ້ memory ແລະເພີ່ມ performance.";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "ໃຊ້ garbage collector ທີ່ມີປະສິດທິພາບເພື່ອຫຼຸດການຢຸດຊົ່ວຄາວຂອງ app.";
	}

	@Override
	public String modulos() {
		return "ໂມດູນ";
	}

	@Override
	public String paquete() {
		return "Package";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "ມີ IDs ຂາດຫາຍ. ສາເຫດທົ່ວໄປແມ່ນ mods ຂາດ ຫຼື ຂໍ້ມູນ item ຂາດ. "
				+ "ໂຟນເດີຂໍ້ມູນທີ່ພົບເລື້ອຍແມ່ນ datafiedcontents/, kubejs/ ຫຼື ໂຟນເດີ mods ອື່ນໆ.";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "registros ຈັບຄູ່ຜິດ";
	}

	/**
	 * ສົ່ງຄືນຂໍ້ຄວາມຜິດພາດສຳລັບການປິດທີ່ເກີດຈາກ AuthMe.
	 */
	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>plugin 'AuthMe' ໂຫລດລົ້ມເຫລວ ແລະໄດ້ປິດ server.</b> ";
	}

	/**
	 * ສົ່ງຄືນຊື່ຂອງບັນຫາເພື່ອສະແດງໃນ interface.
	 */
	public String nombreProblemaCierreAuthMe() {
		return "ບັນຫາການປິດໂດຍ AuthMe";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂສຳລັບການປິດ server ໂດຍ AuthMe.
	 */
	public String solucionCierreAuthMe() {
		return "ຄ່າ 'stopServer' ຖືກປ່ຽນເປັນ 'true'.";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍການຕັ້ງຄ່າ plugin AuthMe.
	 */
	public String solucionConfigurarPluginAuthMe() {
		return "ຕັ້ງຄ່າ plugin AuthMe (plugins/AuthMe/config.yml)";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍຕິດຕັ້ງ plugin AuthMe ລຸ້ນອື່ນ.
	 */
	public String solucionInstalarVersionDiferenteAuthMe() {
		return "ຕິດຕັ້ງ plugin 'AuthMe' ລຸ້ນອື່ນ";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍລົບ plugin AuthMe.
	 */
	public String solucionEliminarPluginAuthMe() {
		return "ລົບ plugin 'AuthMe'";
	}

	/**
	 * ສົ່ງຄືນຂໍ້ຄວາມຜິດພາດສຳລັບໂລກທີ່ເສຍຫາຍຈາກ Multiverse.
	 */
	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ໂລກ '" + nombreMundo
				+ "' ບໍ່ສາມາດໂຫລດໄດ້ ເນື່ອງຈາກມີຂໍ້ຜິດພາດ ແລະອາດຈະເສຍຫາຍ.</b> ";
	}

	/**
	 * ສົ່ງຄືນຊື່ຂອງບັນຫາເພື່ອສະແດງໃນ interface.
	 */
	@Override
	public String nombreProblemaCargaMultiverso() {
		return "ບັນຫາການໂຫລດ Multiverse";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍການສ້ອມແປງໂລກ.
	 */
	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "ສ້ອມແປງໂລກ '" + nombreMundo + "', ເຊັ່ນໃຊ້ Minecraft Region Fixer ຫຼື MCEdit.";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍລົບໂຟນເດີຂອງໂລກ.
	 */
	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "ລົບໂຟນເດີໂລກ '" + nombreMundo + "'.";
	}

	/**
	 * ສົ່ງຄືນຂໍ້ຄວາມຜິດພາດສຳລັບການຕັ້ງຄ່າ PermissionsEx ທີ່ບໍ່ຖືກຕ້ອງ.
	 */
	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ການຕັ້ງຄ່າຂອງ plugin 'PermissionsEx' ບໍ່ຖືກຕ້ອງ.</b> ";
	}

	/**
	 * ສົ່ງຄືນຊື່ຂອງບັນຫາເພື່ອສະແດງໃນ interface.
	 */
	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "ບັນຫາການຕັ້ງຄ່າ PermissionsEx";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍການຕັ້ງຄ່າ PermissionsEx.
	 */
	@Override
	public String solucionConfigurarPermissionsEx() {
		return "ຕັ້ງຄ່າ plugin PermissionsEx (plugins/PermissionsEx/permissions.yml)";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍລົບ plugin PermissionsEx.
	 */
	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "ລົບ plugin 'PermissionsEx'";
	}

	/**
	 * ສົ່ງຄືນຂໍ້ຄວາມຜິດພາດສຳລັບ plugins ທີ່ມີຊື່ກຳກວມ.
	 */
	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ພົບໄຟລ໌ plugin ຫຼາຍອັນສຳລັບ plugin ຊື່ '"
				+ nombrePlugin + "': '" + primerPath + "' ແລະ '" + segundoPath + "'.</b> ";
	}

	/**
	 * ສົ່ງຄືນຊື່ຂອງບັນຫາເພື່ອສະແດງໃນ interface.
	 */
	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "ບັນຫາຊື່ plugin ກຳກວມ";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍລົບ plugin ທີ່ລະບຸ.
	 */
	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "ລົບ plugin '" + nombrePlugin + "'";
	}

	/**
	 * ສົ່ງຄືນຂໍ້ຄວາມຜິດພາດສຳລັບ exception ໃນຂະນະໂຫລດ chunks.
	 */
	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ເກີດ exception ໃນຂະນະທີ່ໂລກກຳລັງໂຫລດ chunks. "
				+ "ຖ້າມີສຳລັບ platform ຂອງທ່ານ, FeatureRecycler ອາດຊ່ວຍແກ້ໄຂບັນຫານີ້ໄດ້. "
				+ "https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
	}

	@Override
	public String nombreProblemaCargaChunk() {
		return "ຂໍ້ຍົກເວັ້ນຂະນະໂຫລດ chunks";
	}

	@Override
	public String solucionEliminarChunk() {
		return "ລົບ chunk ທີ່ມີບັນຫາອອກຈາກໂລກ, ເຊັ່ນໃຊ້ MCEdit ຫຼື ລົບໄຟລ໌ region.";
	}

	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>plugin '" + nombrePlugin
				+ "' ບໍ່ສາມາດເຮັດຄຳສັ່ງ '/" + comando + "' ໄດ້.</b> ";
	}

	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "ຂໍ້ຍົກເວັ້ນຂະນະດຳເນີນຄຳສັ່ງ plugin";
	}

	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "ຕິດຕັ້ງ plugin ລຸ້ນອື່ນ '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>plugin '" + nombrePlugin + "' ຕ້ອງການ dependency '"
				+ dependencia + "'.</b> ";
	}

	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>plugin '" + nombrePlugin
				+ "' ຂາດ plugins ທີ່ຈຳເປັນ: " + deps.toString() + ".</b> ";
	}

	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "ຂາດ dependency ຂອງ plugin";
	}

	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "ຕິດຕັ້ງ plugin '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>plugin '" + nombrePlugin + "' ຕ້ອງການ API ລຸ້ນ '"
				+ versionAPI + "' ທີ່ບໍ່ຮອງຮັບກັບ server ປັດຈຸບັນ.</b> ";
	}

	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "ລຸ້ນ API ບໍ່ຮອງຮັບກັນ";
	}

	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "ຕິດຕັ້ງລຸ້ນ '" + version + "' ຂອງ server.";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ໂລກ '" + nombreMundo
				+ "' ເປັນສຳເນົາຊ້ຳ ແລະບໍ່ສາມາດໂຫລດໄດ້.</b> ";
	}

	@Override
	public String nombreProblemaMundoDuplicado() {
		return "ໂລກຊ້ຳກັນ";
	}

	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "ລົບໄຟລ໌ 'uid.dat' ຂອງໂລກ '" + nombreMundo + "'";
	}

	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "ລົບໂຟນເດີໂລກ '" + nombreMundo + "'";
	}

	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		String color = config.obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>Entity ຫຼື Block Entity '</span>" + nombre
				+ "<span style='color:#" + color + "'>' ປະເພດ '</span>" + tipo + "<span style='color:#" + color
				+ "'>' ຢູ່ຕຳແໜ່ງ </span>" + coords + "<span style='color:#" + color
				+ "'> ກຳລັງເຮັດໃຫ້ເກີດບັນຫາ ticking.</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'>" + "ວິທີແກ້ໄຂ:<br>"
				+ "1. **MCForge**: ໄປທີ່ '[world]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: ໄປທີ່ 'config/neoforge-server.toml'.<br>"
				+ "   *(ໝາຍເຫດ: ໂລກ singleplayer ຢູ່ໃນໂຟນເດີ 'saves')*.<br>"
				+ "3. ປ່ຽນ **removeErroringBlockEntities** ແລະ **removeErroringEntities** ເປັນ **true**.<br><br>"
				+ "ທາງເລືອກອື່ນ:<br>" + "- **MCEdit**: ໃຊ້ເພື່ອລົບ entity ດ້ວຍມືຕາມ coordinates.<br>"
				+ "- **Neruina (Mod)**: ອາດຊ່ວຍໄດ້ ແຕ່ບໍ່ແນ່ນອນ." + "</span>";

		return mensajeBase + instrucciones;
	}

	/**
	 * ສົ່ງຄືນຊື່ຂອງບັນຫາເພື່ອສະແດງໃນ interface.
	 */
	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "Block Entity ທີ່ມີບັນຫາ";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍລົບ block entity.
	 */
	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "ລົບ entity '" + nombre + "' ທີ່ຕຳແໜ່ງ " + coords + " ໂດຍໃຊ້ MCEdit ຫຼື ແກ້ໄຂໂລກໂດຍກົງ.";
	}

	/**
	 * ສົ່ງຄືນຂໍ້ຄວາມຜິດພາດສຳລັບ mods ຊ້ຳກັນໃນ Fabric.
	 */
	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>mod '" + nombreMod
				+ "' ມີຫຼາຍເວີຊັນຖືກຕິດຕັ້ງ.</b> ";
	}

	/**
	 * ສົ່ງຄືນຊື່ຂອງບັນຫາເພື່ອສະແດງໃນ interface.
	 */
	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "Mod ຊ້ຳກັນໃນ Fabric";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍລົບໄຟລ໌ທີ່ຊ້ຳ.
	 */
	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "ລົບໄຟລ໌ mod ທີ່ຊ້ຳ: " + rutaMod;
	}

	/**
	 * ສົ່ງຄືນຂໍ້ຄວາມຜິດພາດສຳລັບ mods ທີ່ບໍ່ຮອງຮັບກັນ.
	 */
	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>mods '" + primerMod + "' ແລະ '" + segundoMod
				+ "' ບໍ່ຮອງຮັບກັນ.</b> ";
	}

	/**
	 * ສົ່ງຄືນຊື່ຂອງບັນຫາເພື່ອສະແດງໃນ interface.
	 */
	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "Mod ບໍ່ຮອງຮັບກັນໃນ Fabric";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍລົບ mod ທຳອິດທີ່ບໍ່ຮອງຮັບກັນ.
	 */
	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "ລົບ mod '" + nombreMod + "'";
	}

	/**
	 * ສົ່ງຄືນຂໍ້ຄວາມຜິດພາດສຳລັບ mods ທີ່ມີບັນຫາຮ້າຍແຮງ.
	 */
	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>mod '" + nombreMod
				+ "' ມີຂໍ້ຜິດພາດຮ້າຍແຮງ ແລະບໍ່ສາມາດເຮັດວຽກໄດ້.</b> ";
	}

	/**
	 * ສົ່ງຄືນຊື່ຂອງບັນຫາເພື່ອສະແດງໃນ interface.
	 */
	@Override
	public String nombreProblemaModFatal() {
		return "Mod ທີ່ມີຂໍ້ຜິດພາດຮ້າຍແຮງ";
	}

	/**
	 * ສົ່ງຄືນຂໍ້ຄວາມຜິດພາດສຳລັບ dependencies ຂອງ mods ທີ່ຂາດ (ພະຫູພົດ).
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
		return "<b style='color:#" + config.obtenerColorError() + "'>mods ຕໍ່ໄປນີ້ຈຳເປັນ ແຕ່ຍັງບໍ່ໄດ້ຕິດຕັ້ງ: "
				+ deps.toString() + ".</b>";
	}

	/**
	 * ສົ່ງຄືນຂໍ້ຄວາມຜິດພາດສຳລັບ dependency ຂອງ mod ທີ່ຂາດ (ເອກພົດ).
	 */
	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>mod '" + nombreMod
					+ "' ຕ້ອງການ dependency mod '" + dependencia + "'.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>mod '" + nombreMod + "' ຕ້ອງການ mod '"
					+ dependencia + "' ເວີຊັນ " + version + ".</b>";
		}
	}

	/**
	 * ສົ່ງຄືນຊື່ຂອງບັນຫາເພື່ອສະແດງໃນ interface.
	 */
	@Override
	public String nombreProblemaDependenciaMod() {
		return "ຂາດ dependency ຂອງ mod";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍຕິດຕັ້ງ mod ສະເພາະ.
	 */
	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "ຕິດຕັ້ງ mod '" + nombreMod + "'";
	}

	/**
	 * ສົ່ງຄືນວິທີແກ້ໄຂໂດຍຕິດຕັ້ງ mod ພ້ອມເວີຊັນທີ່ກຳນົດ.
	 */
	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "ຕິດຕັ້ງ mod '" + nombreMod + "' ເວີຊັນ " + version;
	}

	/**
	 * ສົ່ງຄືນຂໍ້ຄວາມຜິດພາດສຳລັບ plugin ທີ່ບໍ່ຮອງຮັບ regional ticking ຂອງ Folia
	 * (ເອກພົດ).
	 */
	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>plugin '" + nombrePlugin
				+ "' ບໍ່ຮອງຮັບ regional ticking ຂອງ Folia.</b> ";
	}

	/**
	 * ສົ່ງຄືນຂໍ້ຄວາມຜິດພາດສຳລັບ plugins ຫຼາຍອັນທີ່ບໍ່ຮອງຮັບ regional ticking ຂອງ
	 * Folia (ພະຫູພົດ).
	 */
	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("plugins ຕໍ່ໄປນີ້ບໍ່ຮອງຮັບ regional ticking ຂອງ Folia: ");

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
		return "ປລັກອິນບໍ່ຮອງຮັບ Regional Ticking";
	}

	/**
	 * Devuelve la solución de instalar un software sin ticking regional.
	 */
	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "ຕິດຕັ້ງຊອບແວທີ່ບໍ່ມີ regional ticking, ເຊັ່ນ " + software;
	}

	/**
	 * Devuelve el mensaje de error para un mod faltante en datapack (singular).
	 */
	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ໂມດ '" + nombreMod + "' ຂາດຫາຍໄປໃນ datapack.</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods faltantes en datapack
	 * (plural).
	 */
	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ໂມດຕໍ່ໄປນີ້ຂາດຫາຍໄປໃນ datapack: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" ແລະ ");
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
		return "ຂາດໂມດໃນ datapack";
	}

	/**
	 * Devuelve el mensaje de error para un mod que generó una excepción (singular).
	 */
	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ໂມດ '" + nombreMod + "' ໄດ້ສ້າງຂໍ້ຜິດພາດ.</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods que generaron excepciones
	 * (plural).
	 */
	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ໂມດຕໍ່ໄປນີ້ໄດ້ສ້າງຂໍ້ຜິດພາດ: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" ແລະ ");
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
		return "ໂມດ Forge ມີຂໍ້ຍົກເວັ້ນ";
	}

	/**
	 * Devuelve la solución de instalar una versión diferente del mod.
	 */
	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "ຕິດຕັ້ງໂມດ '" + nombreMod + "' ໃນອີກເວີຊັນໜຶ່ງ";
	}

	/**
	 * Devuelve el mensaje de error para un mod incompatible con la versión de
	 * Minecraft (singular).
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ໂມດ '" + nombreMod
				+ "' ບໍ່ເຂົ້າກັນກັບ Minecraft ເວີຊັນ " + versionMC + ".</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods incompatibles con Minecraft
	 * (plural).
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ໂມດຕໍ່ໄປນີ້ບໍ່ເຂົ້າກັນກັບເວີຊັນ Minecraft: ");

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
		return "ໂມດບໍ່ຮອງຮັບກັບ Minecraft";
	}

	/**
	 * Devuelve la solución de instalar una versión diferente de Forge.
	 */
	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "ຕິດຕັ້ງ Forge ເວີຊັນທີ່ເຂົ້າກັນກັບ Minecraft " + versionMC;
	}

	/**
	 * Devuelve el mensaje de error para un mod faltante (singular).
	 */
	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ໂມດ '" + nombreMod
				+ "' ຂາດຫາຍໄປ ແລະ ຕ້ອງການເພື່ອໂຫຼດໂລກ ຫຼື ປລັກອິນ.</b>";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "ຂາດໂມດ";
	}

	/**
	 * Devuelve el mensaje de error para un mod faltante en el mundo (singular).
	 */
	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ໂລກຖືກບັນທຶກດ້ວຍໂມດ '" + nombreMod
				+ "' ທີ່ເບິ່ງຄືວ່າຂາດຫາຍໄປ.</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods faltantes en el mundo
	 * (plural).
	 */
	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ໂລກຖືກບັນທຶກດ້ວຍໂມດຕໍ່ໄປນີ້ທີ່ເບິ່ງຄືວ່າຂາດຫາຍໄປ: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" ແລະ ");
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
		return "ຂາດໂມດໃນໂລກ";
	}

	/**
	 * Devuelve el mensaje de error para discrepancia de versión de mod en un mundo
	 * (singular).
	 */
	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ໂລກຖືກບັນທຶກດ້ວຍໂມດ '" + nombreMod + "' ເວີຊັນ "
				+ versionEsperada + ", ແລະ ປັດຈຸບັນຢູ່ໃນເວີຊັນ " + versionActual + ".</b>";
	}

	/**
	 * Devuelve el mensaje de error para discrepancias de versión de mods en
	 * múltiples mods (plural).
	 */
	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ໂມດຕໍ່ໄປນີ້ມີຄວາມຂັດແຍ່ງກັນຂອງເວີຊັນໃນໂລກທີ່ບັນທຶກໄວ້: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" ແລະ ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (ບັນທຶກ: ").append(versionesEsperadas.get(i)).append(", ປັດຈຸບັນ: ")
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
		return "ເວີຊັນໂມດໃນໂລກທີ່ບັນທຶກໄວ້";
	}

	/**
	 * Devuelve el mensaje de error para intentar cargar un mundo desde una versión
	 * más reciente.
	 */
	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ເຈົ້າພະຍາຍາມໂຫຼດໂລກທີ່ສ້າງດ້ວຍ Minecraft ເວີຊັນທີ່ໃໝ່ກວ່າ.</b>";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaVersionDowngrade() {
		return "ພະຍາຍາມໂຫຼດໂລກຈາກເວີຊັນທີ່ຕ່ຳກວ່າ";
	}

	/**
	 * Devuelve la solución de instalar una versión más nueva de Minecraft.
	 */
	@Override
	public String solucionVersionDowngrade() {
		return "ຕິດຕັ້ງ Minecraft ເວີຊັນທີ່ໃໝ່ກວ່າ.";
	}

	/**
	 * Devuelve la solución de generar un nuevo mundo.
	 */
	@Override
	public String solucionGenerarNuevoMundo() {
		return "ສ້າງໂລກໃໝ່.";
	}

	/**
	 * Devuelve el mensaje de error para un plugin con dependencia faltante
	 * (singular).
	 */
	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ປລັກອິນ '" + nombrePlugin + "' ຕ້ອງການປລັກອິນ '"
				+ dependencia + "' ເປັນຕົວແທນ.</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples plugins con dependencias
	 * faltantes (plural).
	 */
	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ປລັກອິນຕໍ່ໄປນີ້ຕ້ອງການຕົວແທນທີ່ບໍ່ໄດ້ຕິດຕັ້ງ: ");

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
		return "ປລັກອິນຂາດຕົວແທນ";
	}

	/**
	 * Devuelve el mensaje de error para un plugin incompatible (singular).
	 */
	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ສ່ວນຂະຫຍາຍ '" + nombrePlugin
				+ "' ບໍ່ເຂົ້າກັນກັບເວີຊັນປັດຈຸບັນຂອງເຊີບເວີ.</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples plugins incompatibles (plural).
	 */
	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ສ່ວນຂະຫຍາຍຕໍ່ໄປນີ້ບໍ່ເຂົ້າກັນກັບເວີຊັນປັດຈຸບັນຂອງເຊີບເວີ: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" ແລະ ");
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
		return "ປລັກອິນບໍ່ເຂົ້າກັນກັບ PocketMine-MP";
	}

	/**
	 * Devuelve el mensaje de error para un plugin con error de ejecución
	 * (singular).
	 */
	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ປລັກອິນ '" + nombrePlugin
				+ "' ໄດ້ສ້າງຂໍ້ຜິດພາດໃນລະຫວ່າງການປະຕິບັດ.</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples plugins con errores de ejecución
	 * (plural).
	 */
	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ປລັກອິນຕໍ່ໄປນີ້ໄດ້ສ້າງຂໍ້ຜິດພາດໃນລະຫວ່າງການປະຕິບັດ: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" ແລະ ");
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
		return "ປລັກອິນມີຂໍ້ຜິດພາດໃນການປະຕິບັດ";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "LegacyRandomSource ຫຼາຍເສັ້ນດ້າຍ";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ເຈົ້າມີບັນຫາກັບຫຼາຍເສັ້ນດ້າຍທີ່ເຂົ້າເຖິງຄລາສ LegacyRandomSource. ເຈົ້າສາມາດໄດ້ຮັບຂໍ້ມູນເພີ່ມເຕີມດ້ວຍໂມດ Unsafe World Random Access Detector ຫຼື ໂມດ C2ME.</b>";
	}

	@Override
	public String desdeUltimoExito() {
		return "ຕັ້ງແຕ່ຄວາມສຳເລັດຄັ້ງສຸດທ້າຍ";
	}

	@Override
	public String noHayCambios() {
		return "ບໍ່ມີການປ່ຽນແປງ";
	}

	@Override
	public String desdeUltimoIntento() {
		return "ຕັ້ງແຕ່ຄວາມພະຍາຍາມຄັ້ງສຸດທ້າຍ";
	}

	@Override
	public String fallo() {
		return "ລົ້ມເຫຼວ";
	}

	@Override
	public String diferentesDeLasMods() {
		return "ແຕກຕ່າງຈາກໂມດ";
	}

	@Override
	public String historialDeMods() {
		return "ປະຫວັດໂມດ";
	}

	@Override
	public String archivo0() {
		return "ໄຟລ໌ 0";
	}

	@Override
	public String archivo1() {
		return "ໄຟລ໌ 1";
	}

	@Override
	public String comparar() {
		return "ປຽບທຽບ";
	}

	@Override
	public String seleccionarDosArchivos() {
		return "ເລືອກສອງໄຟລ໌";
	}

	@Override
	public String archivoExito() {
		return "ໄຟລ໌ ສຳເລັດ";
	}

	@Override
	public String archivoFalla() {
		return "ໄຟລ໌ ລົ້ມເຫຼວ";
	}

	@Override
	public String errorComparandoArchivos() {
		return "ຜິດພາດໃນການປຽບທຽບໄຟລ໌";
	}

	@Override
	public String comparando() {
		return "ກຳລັງປຽບທຽບ";
	}

	@Override
	public String con() {
		return "ກັບ";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>" + "<p><b>ແຜງປຽບທຽບປະຫວັດໂມດ</b></p>"
				+ "<p>ແຜງນີ້ຊ່ວຍໃຫ້ເຈົ້າສາມາດປຽບທຽບສອງລາຍການໂມດ (modules) ຈາກການເຮັດວຽກຄົນລະຄັ້ງ. "
				+ "ເລືອກໄຟລ໌ຈາກຖັນຊ້າຍ ແລະ ອີກໄຟລ໌ໜຶ່ງຈາກຖັນຂວາ ເພື່ອເບິ່ງການປ່ຽນແປງລະຫວ່າງສອງເວີຊັນ.</p>"

				+ "<h3>ວິທີໃຊ້:</h3>" + "<ol>" + "<li><b>ການເລືອກໄຟລ໌:</b> ຄລິກປຸ່ມວິທະຍຸຂ້າງຊື່ໄຟລ໌. "
				+ "ໄຟລ໌ທີ່ລົງທ້າຍດ້ວຍ <span style='color: #4CAF50; font-weight: bold;'>.exito</span> ບົ່ງບອກເຖິງການເຮັດວຽກທີ່ສຳເລັດ, "
				+ "ໃນຂະນະທີ່ໄຟລ໌ <span style='color: #F44336; font-weight: bold;'>.falla</span> ແມ່ນການລົ້ມເຫຼວ.</li>"

				+ "<li><b>ການປຽບທຽບອັດຕະໂນມັດ:</b> ເມື່ອກົດປຸ່ມ 'ປຽບທຽບ', ລະບົບຈະວິເຄາະສອງລາຍການທີ່ເລືອກ "
				+ "ແລະ ສະແດງໂມດທີ່ຖືກເພີ່ມ (+) ຫຼື ຖືກລຶບ (-) ໃນແຕ່ລະທິດທາງ.</li>"

				+ "<li><b>ການສະແດງຜົນ:</b> ຜົນລັບຈະຖືກສະແດງໃນຮູບແບບ HTML ພ້ອມສີທີ່ແຍກແຍະ: " + "<ul>"
				+ "<li><span style='color: green;'>+ ໂມດທີ່ເພີ່ມ</span></li>"
				+ "<li><span style='color: red;'>- ໂມດທີ່ຖືກລຶບ</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>ຄຸນສົມບັດຫຼັກ:</h3>" + "<ul>"
				+ "<li>ຮອງຮັບການເລືອກໄຟລ໌ໃນທຸກການປະສົມປະສານ (ສຳເລັດ/ລົ້ມເຫຼວ).</li>"
				+ "<li>ສະແດງຄວາມແຕກຕ່າງສອງທິດທາງ ເພື່ອລະບຸການປ່ຽນແປງຢ່າງຊັດເຈນ.</li>"
				+ "<li>ມີແຖບເລື່ອນ (scroll) ເພື່ອເລື່ອນເບິ່ງລາຍການໂມດທີ່ຍາວ.</li>"
				+ "<li>ມີການປະສານງານກັບຮູບພາບອະທິບາຍ ເພື່ອເພີ່ມຄວາມເຂົ້າໃຈທາງສາຍຕາ.</li>" + "</ul>"

				+ "<p>ພັດທະນາດ້ວຍ <3️ ເພື່ອຊ່ວຍເຈົ້າຕິດຕາມການປ່ຽນແປງໃນການຕັ້ງຄ່າຂອງເຈົ້າ.</p>" + "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ເຈົ້າອາດຈະມີບັນຫາກ່ຽວກັບ IPv6. "
				+ "ມີສອງວິທີແກ້ໄຂ: "
				+ "1) ເພີ່ມອາກິວເມັນ JVM <code>-Djava.net.preferIPv4Stack=true</code> ໃນຕົວເປີດຂອງເຈົ້າ, ຫຼື "
				+ "2) ໃຊ້ປຸ່ມ 'QuickFix' ໃນ CrashDetector ເພື່ອໃຊ້ patch ທີ່ຈະເປີດການຕັ້ງຄ່ານີ້ໂດຍອັດຕະໂນມັດ." + "</b>";
	}

	@Override
	public String parcheIPv4() {
		return "Patch IPV4/6";
	}

	@Override
	public String carpetaHMCL() {
		return "ໂຟລເດີ HMCL (ສຳລັບ HelloMineCraftLauncher ເທົ່ານັ້ນ)";
	}

	@Override
	public String descripcionCurseforge() {
		return "ໝາຍເຫດ: ບໍ່ມີການສ້າງ log ຖ້າເປີດໃຊ້ຕົວເລືອກ \"ຂ້າມ Launcher\" ໃນ ການຕັ້ງຄ່າ > Minecraft";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/ແລະອື່ນໆ: ຄລິກຂວາໃສ່ instance ແລະ ເລືອກ \"Edit Instance\". ໃນໜ້າຕ່າງທີ່ເປີດຂຶ້ນ, ຊອກຫາສ່ວນ \"Minecraft Logs\" ຫຼື ທີ່ຄ້າຍຄືກັນ.<br>"
				+ "Logs ເຫຼົ່ານີ້ບັນຈຸ output ມາດຕະຖານ (STDOUT) ທີ່ສຳຄັນຫຼາຍສຳລັບການວິນິດໄສຂໍ້ຜິດພາດ.";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher): ເຈົ້າຕ້ອງເລືອກໂຟລເດີທີ່ HMCL ຕິດຕັ້ງຢູ່ ແລະ ເລືອກໂຟລເດີ \".hmcl\". Logs ຂອງ HMCL ຖືກບັນທຶກໄວ້ໃນສະຖານທີ່ນີ້ ແລະ ບັນຈຸຂໍ້ມູນສຳຄັນກ່ຽວກັບຂໍ້ຜິດພາດ.<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: ຕົວເປີດມີແຖບ development ທີ່ສະແດງ logs ລະອຽດ. ເຈົ້າສາມາດຊອກຫາແຖບນີ້ໃນ menu ຕົວເລືອກຂອງຕົວເປີດ.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: ມີໜ້າຕ່າງ popup ສະແດງ logs. ມັນມີປຸ່ມສຳລັບ copy ແລະ upload logs. Logs ຖືກສ້າງຂຶ້ນໂດຍອັດຕະໂນມັດເມື່ອເລີ່ມເກມ ແລະ ບັນຈຸຂໍ້ມູນສຳຄັນສຳລັບການວິນິດໄສ.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: ຄລິກຂວາໃສ່ instance ແລະ ເລືອກ \"ການຕັ້ງຄ່າ\". ຈາກນັ້ນໄປທີ່ສ່ວນ Logs ເພື່ອເບິ່ງຂໍ້ມູນສຳຄັນທີ່ບັນຈຸ output ມາດຕະຖານ (STDOUT).";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "Links ດ້ວຍ Markdown: ວາງ link ໃດໆທີ່ບັນຈຸ logs ໃນຮູບແບບ Markdown ທີ່ນີ້. ລະບົບຈະພະຍາຍາມ extract links ຂອງ logs (latest.log, launcher_log.txt, debug.log, ອື່ນໆ) ແລະ ວິເຄາະໂດຍອັດຕະໂນມັດ.";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "ບໍ່ພົບ Log ຂອງ Launcher";
	}

	@Override
	public String imagenNoEncontrada() {
		return "ບໍ່ພົບຮູບພາບ";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "ທົ່ວໄປ: ເລືອກປະເພດ launcher ທີ່ເຈົ້າກຳລັງໃຊ້. Logs ຂອງ launcher (launcher_log.txt, stdout, ອື່ນໆ) ບັນຈຸຂໍ້ມູນສຳຄັນກ່ຽວກັບຂໍ້ຜິດພາດທີ່ບໍ່ປາກົດໃນ latest.log. CrashDetector ບໍ່ສາມາດອ່ານ logs ຈາກ Launcher ຂອງເຈົ້າໄດ້, ມັນອາດຈະບໍ່ມີໄຟລ໌ log ແລະ ເຈົ້າຕ້ອງ paste logs ດ້ວຍຕົນເອງ.<br>"
				+ "ສຳລັບຂໍ້ມູນເພີ່ມເຕີມ, ເບິ່ງ <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">ບັນຫານີ້</a>. Logs ເຫຼົ່ານີ້ບັນຈຸ output ມາດຕະຖານ (STDOUT), ທີ່ຈຳເປັນສຳລັບການວິນິດໄສຂໍ້ຜິດພາດຫຼາຍປະເພດ.";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ເຈົ້າມີ classes ຂອງ Create ຂາດຫາຍ. Create ປ່ຽນແປງຫຼາຍ: classes ຫຼາຍຢ່າງຖືກລຶບ. ໂດຍສະເພາະຕັ້ງແຕ່ Create 6 (ກຸມພາ 2025), addons ສຳລັບເວີຊັນເກົ່າຂອງ Create ບໍ່ເຮັດວຽກ. QuickFix ບໍ່ມີວິທີແກ້ໄຂສຳລັບບັນຫານີ້. ເຈົ້າຕ້ອງອັບເດດ addons ຂອງ Create, ລຶບ ຕົວທີ່ລ້າສະໄໝ, ຫຼື ໃຊ້ເວີຊັນ Create ທີ່ຖືກຕ້ອງສຳລັບ addons ທີ່ເຈົ້າຕ້ອງການໃຊ້.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ເຈົ້າມີ classes ຂອງ EpicFight ຂາດຫາຍ. EpicFight ປ່ຽນແປງຫຼາຍ: classes ຫຼາຍຢ່າງຖືກລຶບ. QuickFix ບໍ່ມີວິທີແກ້ໄຂສຳລັບບັນຫານີ້. ເຈົ້າຕ້ອງອັບເດດ addons ຂອງ EpicFight, ລຶບ ຕົວທີ່ລ້າສະໄໝ, ຫຼື ໃຊ້ເວີຊັນ EpicFight ທີ່ຖືກຕ້ອງສຳລັບ addons ທີ່ເຈົ້າຕ້ອງການໃຊ້.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ເຈົ້າກຳລັງໃຊ້ OpenJ9, ທີ່ບໍ່ຮອງຮັບກັບແອັບນີ້. ແອັບຫຼາຍຕົວ, ລວມທັງຕົວນີ້, ບໍ່ຮອງຮັບ OpenJ9 ເນື່ອງຈາກຄວາມແຕກຕ່າງໃນການ implement JVM. QuickFix ບໍ່ສາມາດແກ້ໄຂບັນຫານີ້ໂດຍອັດຕະໂນມັດໄດ້. ເຈົ້າຕ້ອງຕິດຕັ້ງ JDK ທີ່ຮອງຮັບ ເຊັ່ນ Oracle JDK, OpenJDK Hotspot ຫຼື ຕົວແທນອື່ນໆທີ່ແນະນຳ.</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ເວີຊັນນີ້ຂອງແອັບຕ້ອງການ Java 11 (JDK 11) ເພື່ອເຮັດວຽກຢ່າງຖືກຕ້ອງ. ເຈົ້າກຳລັງໃຊ້ Java ເວີຊັນເກົ່າກວ່າທີ່ບໍ່ຮອງຮັບ. QuickFix ບໍ່ສາມາດອັບເດດ Java ຂອງເຈົ້າໂດຍອັດຕະໂນມັດໄດ້. ເຈົ້າຕ້ອງດາວໂຫຼດ ແລະ ຕິດຕັ້ງ JDK 11 ຫຼື ເວີຊັນສູງກວ່າທີ່ຮອງຮັບ ຈາກ links ທີ່ໃຫ້ໄວ້ໃນວິທີແກ້ໄຂ.</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ເຈົ້າໄດ້ຈັດສັນ memory ຫຼາຍເກີນໄປ, ເຊິ່ງເຮັດໃຫ້ລະບົບບໍ່ມີຊັບພະຍາກອນພຽງພໍທີ່ຈະເຮັດວຽກໄດ້ຢ່າງຖືກຕ້ອງ. ສິ່ງນີ້ມັກເກີດຂຶ້ນເມື່ອລະບຸປະລິມານ RAM ຫຼາຍກວ່າທີ່ມີຢູ່ໃນລະບົບຂອງເຈົ້າ ຫຼື ເມື່ອໃຊ້ JVM 32-bit ທີ່ບໍ່ສາມາດຈັດການກັບປະລິມານ memory ຫຼາຍໆໄດ້.</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>ເພື່ອແກ້ໄຂບັນຫານີ້, ເຈົ້າຕ້ອງຫຼຸດປະລິມານ memory ທີ່ຈັດສັນໃຫ້ແອັບ. ປະລິມານສູງສຸດທີ່ແນະນຳຂຶ້ນກັບລະບົບຂອງເຈົ້າ, ແຕ່ໂດຍທົ່ວໄປບໍ່ຄວນເກີນ 70-80% ຂອງ RAM ທັງໝົດຂອງເຈົ້າ. ຖ້າເຈົ້າໃຊ້ JVM 32-bit, ຂີດຈຳກັດສູງສຸດແມ່ນປະມານ 2-3 GB, ບໍ່ວ່າເຈົ້າຈະມີ RAM ທາງກາຍະພາບເທົ່າໃດ.</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ເພື່ອຫຼຸດ heap memory ທີ່ຈັດສັນໃຫ້ແອັບ, ເປີດການຕັ້ງຄ່າຂອງ launcher ຂອງເຈົ້າ ແລະ ຊອກຫາຕົວເລືອກ memory RAM. ຫຼຸດຄ່າສູງສຸດ (Xmx) ລົງໃຫ້ເໝາະສົມ. ຕົວຢ່າງ, ຖ້າເຈົ້າມີ RAM 8 GB, ຈັດສັນ 3-4 GB ໃຫ້ແອັບ. ຖ້າເຈົ້າມີ RAM 16 GB, ເຈົ້າສາມາດຈັດສັນ 6-8 GB. ຢ່າລືມວ່າຕ້ອງເຫຼືອ memory ພຽງພໍສຳລັບລະບົບປະຕິບັດການ ແລະ ໂປຣແກຣມອື່ນໆ.</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ຂາດໄຟລ໌ສຳຄັນຂອງ Forge. ໄຟລ໌ '" + archivo
				+ "' ບໍ່ພົບໃນການຕິດຕັ້ງຂອງເຈົ້າ. ສິ່ງນີ້ມັກເກີດຂຶ້ນເມື່ອການຕິດຕັ້ງ Forge ຖືກຂັດຈັງຫວະ ຫຼື ໄຟລ໌ສຳຄັນຖືກ ລົບ. QuickFix ບໍ່ສາມາດກູ້ຄືນໄຟລ໌ເຫຼົ່ານີ້ໂດຍອັດຕະໂນມັດໄດ້. ເຈົ້າຕ້ອງຕິດຕັ້ງ Forge ໃໝ່ຢ່າງຖືກຕ້ອງຈາກ installer ທາງການ.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge ບໍ່ສາມາດຊອກຫາເວີຊັນ Minecraft ທີ່ຕ້ອງການໄດ້. ຕ້ອງການເວີຊັນ " + version + " ແຕ່ບໍ່ພົບໃນໄຟລ໌ '"
				+ archivo
				+ "'. ສິ່ງນີ້ເກີດຂຶ້ນເມື່ອມີຄວາມບໍ່ເຂົ້າກັນລະຫວ່າງເວີຊັນ Minecraft ແລະ ເວີຊັນ Forge ທີ່ເຈົ້າກຳລັງໃຊ້. ໃຫ້ແນ່ໃຈວ່າເຈົ້າດາວໂຫຼດເວີຊັນ Forge ທີ່ຖືກຕ້ອງທີ່ກົງກັບເວີຊັນ Minecraft ຂອງເຈົ້າ.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ບໍ່ສາມາດຊອກຫາ target 'fmlclient' ທີ່ຈຳເປັນສຳລັບການເລີ່ມຕົ້ນ Forge. ສິ່ງນີ້ບົ່ງບອກວ່າການຕິດຕັ້ງ Forge ບໍ່ສົມບູນ ຫຼື ເສຍຫາຍ. ເປັນໄປໄດ້ວ່າໄຟລ໌ສຳຄັນຂອງ Forge ບໍ່ໄດ້ຖືກຕິດຕັ້ງຢ່າງຖືກຕ້ອງ. ເຈົ້າຕ້ອງຕິດຕັ້ງ Forge ໃໝ່ໂດຍໃຊ້ installer ທາງການ.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ບໍ່ສາມາດຊອກຫາ class ຫຼັກຂອງ Minecraft ໃນ class loader. ສິ່ງນີ້ມັກບົ່ງບອກວ່າການຕິດຕັ້ງ Forge ບໍ່ສົມບູນ ຫຼື ມີຄວາມຂັດແຍ່ງກັບໂມດອື່ນໆ. ເປັນໄປໄດ້ວ່າໄຟລ໌ Minecraft ເສຍຫາຍໃນລະຫວ່າງການຕິດຕັ້ງ Forge. ເຈົ້າຕ້ອງຕິດຕັ້ງ Forge ໃໝ່ຢ່າງຖືກຕ້ອງ.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ການຕິດຕັ້ງ Forge ບໍ່ສົມບູນ. ສິ່ງນີ້ອາດເກີດຈາກການຕິດຕັ້ງທີ່ຖືກຂັດຈັງຫວະ, ໄຟລ໌ຖືກລຶບ ຫຼື ຄວາມບໍ່ເຂົ້າກັນກັບເວີຊັນ Minecraft ຂອງເຈົ້າ. Forge ຕ້ອງການໄຟລ໌ສະເພາະເພື່ອເຮັດວຽກຢ່າງຖືກຕ້ອງ, ແລະ ບາງໄຟລ໌ຂາດຫາຍໄປໃນການຕິດຕັ້ງປັດຈຸບັນຂອງເຈົ້າ.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "ການຕິດຕັ້ງ Forge ບໍ່ສົມບູນ";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ເພື່ອແກ້ໄຂບັນຫານີ້, ເຈົ້າຕ້ອງຕິດຕັ້ງ Forge ໃໝ່ຢ່າງຖືກຕ້ອງ. ໃຫ້ແນ່ໃຈວ່າເຈົ້າດາວໂຫຼດເວີຊັນທີ່ເໝາະສົມສຳລັບເວີຊັນ Minecraft ຂອງເຈົ້າ ແລະ ດຳເນີນການຕິດຕັ້ງໃຫ້ສົມບູນໂດຍບໍ່ຂັດຈັງຫວະ.</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "ດາວໂຫຼດ Forge ທາງການ";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "ວິທີຕິດຕັ້ງ Forge ໃໝ່ຢ່າງຖືກຕ້ອງ";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>ຄຳແນະນຳສຳລັບການຕິດຕັ້ງ Forge ໃໝ່:</h3>" + "<ol>"
				+ "<li>ດາວໂຫຼດ installer ທີ່ຖືກຕ້ອງຂອງ Forge ຈາກເວັບໄຊທ໌ທາງການ (ເວີຊັນທີ່ແນະນຳສຳລັບເວີຊັນ Minecraft ຂອງເຈົ້າ)</li>"
				+ "<li>ປິດ launcher Minecraft ຂອງເຈົ້າຢ່າງສົມບູນ</li>"
				+ "<li>ເປີດໃຊ້ installer ຂອງ Forge ໃນຖານະຜູ້ບໍລິຫານ (administrator)</li>"
				+ "<li>ເລືອກຕົວເລືອກ 'Installer' (ບໍ່ແມ່ນ 'Installer (run client)')</li>"
				+ "<li>ເລືອກໂຟລເດີ profile Minecraft ຂອງເຈົ້າໃນ launcher</li>"
				+ "<li>ກົດ 'OK' ແລະ ລໍຖ້າໃຫ້ການຕິດຕັ້ງສຳເລັດ</li>"
				+ "<li>ເປີດ launcher ໃໝ່ ແລະ ກວດສອບວ່າ Forge ປາກົດໃນລາຍຊື່ profiles</li>" + "</ol>"
				+ "<p><b>ໝາຍເຫດສຳຄັນ:</b> ຖ້າເຈົ້າໃຊ້ launcher ທີ່ປັບແຕ່ງ, ໃຫ້ແນ່ໃຈວ່າເລືອກໂຟລເດີ profile ທີ່ຖືກຕ້ອງ.</p>"
				+ "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "ຄຳແນະນຳສຳລັບການຕິດຕັ້ງ Forge ໃໝ່";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ຜິດພາດການເຊື່ອມໂຍງບໍ່ສົມບູນ: ບໍ່ສາມາດໂຫຼດ library "
				+ nombreBiblioteca + ". ວິທີແກ້ໄຂທີ່ເປັນໄປໄດ້:<br/><br/>"
				+ "a) ເພີ່ມ directory ທີ່ບັນຈຸ shared library ເຂົ້າໃນ -Djava.library.path ຫຼື -Dorg.lwjgl.librarypath.<br/>"
				+ "b) ເພີ່ມໄຟລ໌ JAR ທີ່ບັນຈຸ shared library ເຂົ້າໃນ classpath.<br/><br/>"
				+ "ຜິດພາດນີ້ເກີດຂຶ້ນເມື່ອ Minecraft ບໍ່ສາມາດຊອກຫາໄຟລ໌ສຳຄັນສຳລັບການເຮັດວຽກຂອງມັນ. "
				+ "ສິ່ງນີ້ມັກເກີດຈາກການຕິດຕັ້ງ Minecraft ທີ່ບໍ່ສົມບູນ ຫຼື ບັນຫາກັບສິດທິການເຂົ້າເຖິງຂອງລະບົບ.</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "ຜິດພາດການເຊື່ອມໂຍງບໍ່ສົມບູນ";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ບໍ່ສາມາດໂຫຼດ library ໄດ້. ວິທີແກ້ໄຂທີ່ເປັນໄປໄດ້:<br/><br/>"
				+ "a) ເພີ່ມ directory ທີ່ບັນຈຸ shared library ເຂົ້າໃນ -Djava.library.path ຫຼື -Dorg.lwjgl.librarypath.<br/>"
				+ "b) ເພີ່ມໄຟລ໌ JAR ທີ່ບັນຈຸ shared library ເຂົ້າໃນ classpath.<br/><br/>"
				+ "ວິທີແກ້ໄຂທາງເຕັກນິກເຫຼົ່ານີ້ແມ່ນສຳລັບຜູ້ໃຊ້ຂັ້ນສູງ. ຜູ້ໃຊ້ສ່ວນຫຼາຍຄວນພະຍາຍາມ "
				+ "ຕິດຕັ້ງ Minecraft ໃໝ່ ກ່ອນທີ່ຈະປ່ຽນແປງ parameter ເຫຼົ່ານີ້.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ID ຊົນກັນ: ID <strong>" + id
				+ "</strong> ຖືກໃຊ້ໂດຍ <strong>" + modOrigen + "</strong> ຢູ່ແລ້ວ ໃນຂະນະທີ່ພະຍາຍາມເພີ່ມ <strong>"
				+ modDestino
				+ "</strong>. ສິ່ງນີ້ເກີດຂຶ້ນເມື່ອສອງໂມດພະຍາຍາມໃຊ້ ID ດຽວກັນສຳລັບອົງປະກອບທີ່ແຕກຕ່າງກັນ.</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ເກີນຂອບເຂດສູງສຸດຂອງ IDs ທີ່ອະນຸຍາດ. ສິ່ງນີ້ເກີດຂຶ້ນເມື່ອໂມດພະຍາຍາມລົງທະບຽນ blocks ຫຼື items ດ້ວຍ IDs ທີ່ຢູ່ນອກຂອບເຂດທີ່ອະນຸຍາດໂດຍເວີຊັນ Minecraft ຂອງເຈົ້າ.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "ID ຊົນກັນ";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ເພື່ອແກ້ໄຂສິ່ງນີ້ໃນ Minecraft 1.12.2, ຕິດຕັ້ງ <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. ສຳລັບ 1.7.10, ໃຊ້ <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>ໃຊ້ເຄື່ອງມືເຊັ່ນ <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> ຫຼື <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> ເພື່ອແກ້ໄຂການຊົນກັນຂອງ IDs.</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "ຕິດຕັ້ງ JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "ຕິດຕັ້ງ EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "ໃຊ້ IdFix Minus ຫຼື IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "ໃຊ້ Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "ເບິ່ງເອກະສານພາສາຍີ່ປຸ່ນ";
	}

	@Override
	public String escanearDeMCreator() {
		return "ສະແກນ MCreator";
	}

	/**
	 * Obtiene el título de la ventana del árbol de mods.
	 * 
	 * @return Título de la ventana
	 */
	@Override
	public String tituloArbolDeMods() {
		return "ຕົ້ນໄມ້ ໂມດ ແລະ Classes";
	}

	/**
	 * Obtiene el texto para la etiqueta de tipo de búsqueda.
	 * 
	 * @return Texto de la etiqueta
	 */
	@Override
	public String tipoBusqueda() {
		return "ປະເພດ";
	}

	/**
	 * Obtiene el texto para el filtro "Todos".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroTodos() {
		return "ທັງໝົດ";
	}

	/**
	 * Obtiene el texto para el filtro "Packages".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroPaquetes() {
		return "Packages";
	}

	/**
	 * Obtiene el texto para el filtro "Clases".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroClases() {
		return "Classes";
	}

	/**
	 * Obtiene el texto para el filtro "Métodos".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroMetodos() {
		return "Methods";
	}

	/**
	 * Obtiene el texto para el filtro "Campos".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroCampos() {
		return "Fields";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Campo".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroReferenciasCampo() {
		return "Field References";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Método".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroReferenciasMetodo() {
		return "Method References";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Clase".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroReferenciasClase() {
		return "Class References";
	}

	/**
	 * Obtiene el texto para el tooltip del campo de búsqueda.
	 * 
	 * @return Texto del tooltip
	 */
	@Override
	public String tipBuscar() {
		return "ພິມທີ່ນີ້ເພື່ອຄົ້ນຫາໃນຕົ້ນໄມ້ໂມດ";
	}

	/**
	 * Obtiene el texto para el botón de búsqueda.
	 * 
	 * @return Texto del botón
	 */
	@Override
	public String botonBuscar() {
		return "ຄົ້ນຫາ";
	}

	/**
	 * Obtiene el texto para el botón de resetear árbol.
	 * 
	 * @return Texto del botón
	 */
	@Override
	public String botonResetearArbol() {
		return "รีเซ็ตຕົ້ນໄມ້";
	}

	/**
	 * Obtiene el texto para indicar los mods cargados.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String modsCargados() {
		return "ໂມດທີ່ໂຫຼດແລ້ວ";
	}

	/**
	 * Obtiene el texto para la categoría de clases.
	 * 
	 * @return Texto de la categoría
	 */
	@Override
	public String clases() {
		return "Classes";
	}

	/**
	 * Obtiene el texto para la categoría de métodos.
	 * 
	 * @return Texto de la categoría
	 */
	@Override
	public String metodos() {
		return "Methods";
	}

	/**
	 * Obtiene el texto para la categoría de campos.
	 * 
	 * @return Texto de la categoría
	 */
	@Override
	public String campos() {
		return "Fields";
	}

	/**
	 * Obtiene el texto para la categoría de referencias.
	 * 
	 * @return Texto de la categoría
	 */
	@Override
	public String referencias() {
		return "References";
	}

	/**
	 * Obtiene el texto para los resultados de búsqueda.
	 * 
	 * @return Texto de resultados
	 */
	@Override
	public String resultadosBusqueda() {
		return "ຜົນການຄົ້ນຫາ";
	}

	/**
	 * Obtiene el texto para la opción de buscar referencias.
	 * 
	 * @return Texto de la opción
	 */
	@Override
	public String buscarReferencias() {
		return "ຄົ້ນຫາ References";
	}

	/**
	 * Obtiene el texto para las referencias de mod.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String referenciasMod() {
		return "References ຂອງໂມດ";
	}

	/**
	 * Obtiene el texto para las referencias de clase.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String referenciasClase() {
		return "References ຂອງ Class";
	}

	/**
	 * Obtiene el texto para las referencias de método.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String referenciasMetodo() {
		return "References ຂອງ Method";
	}

	/**
	 * Obtiene el texto para las referencias de campo.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String referenciasCampo() {
		return "References ຂອງ Field";
	}

	/**
	 * Obtiene el texto cuando no se encuentran referencias.
	 * 
	 * @return Mensaje de error
	 */
	@Override
	public String noSeEncontraronReferencias() {
		return "ບໍ່ພົບ References";
	}

	/**
	 * Obtiene el texto para el detalle de mod.
	 * 
	 * @return Título de detalle
	 */
	@Override
	public String detalleMod() {
		return "ລາຍລະອຽດຂອງໂມດ:";
	}

	/**
	 * Obtiene el texto para la ubicación.
	 * 
	 * @return Etiqueta de ubicación
	 */
	@Override
	public String ubicacion() {
		return "ສະຖານທີ່";
	}

	/**
	 * Obtiene el texto para los nombres.
	 * 
	 * @return Etiqueta de nombres
	 */
	@Override
	public String nombres() {
		return "ຊື່";
	}

	/**
	 * Obtiene el texto para el módulo.
	 * 
	 * @return Etiqueta de módulo
	 */
	@Override
	public String modulo() {
		return "ໂມດູນ";
	}

	/**
	 * Obtiene el texto para el detalle de clase.
	 * 
	 * @return Título de detalle
	 */
	@Override
	public String detalleClase() {
		return "ລາຍລະອຽດຂອງ Class:";
	}

	/**
	 * Obtiene el texto para el detalle de método.
	 * 
	 * @return Título de detalle
	 */
	@Override
	public String detalleMetodo() {
		return "ລາຍລະອຽດຂອງ Method:";
	}

	/**
	 * Obtiene el texto para el detalle de campo.
	 * 
	 * @return Título de detalle
	 */
	@Override
	public String detalleCampo() {
		return "ລາຍລະອຽດຂອງ Field:";
	}

	/**
	 * Obtiene el texto para la clase.
	 * 
	 * @return Etiqueta de clase
	 */
	@Override
	public String clase() {
		return "Class";
	}

	/**
	 * Obtiene el texto para el tipo.
	 * 
	 * @return Etiqueta de tipo
	 */
	@Override
	public String tipo() {
		return "ປະເພດ";
	}

	/**
	 * Obtiene el texto para las referencias a métodos.
	 * 
	 * @return Etiqueta de referencias
	 */
	@Override
	public String referenciasAMetodos() {
		return "References ຂອງ Methods:";
	}

	/**
	 * Obtiene el texto para las referencias a campos.
	 * 
	 * @return Etiqueta de referencias
	 */
	@Override
	public String referenciasACampos() {
		return "References ຂອງ Fields:";
	}

	/**
	 * Obtiene el texto para el botón de árbol de mods.
	 * 
	 * @return Texto del botón
	 */
	@Override
	public String arbolDeMods() {
		return "ຕົ້ນໄມ້ໂມດ";
	}

	/**
	 * Obtiene el texto para método.
	 * 
	 * @return Palabra "Método"
	 */
	@Override
	public String metodo() {
		return "Method";
	}

	/**
	 * Obtiene el texto para campo.
	 * 
	 * @return Palabra "Campo"
	 */
	@Override
	public String campo() {
		return "Field";
	}

	@Override
	public String descompilar() {
		return "Decompile";
	}

	@Override
	public String exportar() {
		return "Export";
	}

	@Override
	public String importar() {
		return "Import";
	}

	@Override
	public String errorImportar() {
		return "ຜິດພາດໃນການ Import";
	}

	@Override
	public String estructuraImportada() {
		return "ໂຄງສ້າງທີ່ Import ແລ້ວ";
	}

	@Override
	public String estructuraExportada() {
		return "ໂຄງສ້າງທີ່ Export ແລ້ວ";
	}

	@Override
	public String errorExportar() {
		return "ຜິດພາດໃນການ Export";
	}

	@Override
	public String exportando() {
		return "ກຳລັງ Export";
	}

	@Override
	public String exportacionTardara() {
		return "ການ Export ຈະໃຊ້ເວລາ";
	}

	@Override
	public String porFavorEspere() {
		return "ກະລຸນາລໍຖ້າ";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>ເຈົ້າບໍ່ມີ binary ຂອງ VLC. ເຈົ້າຕ້ອງການ binary ຂອງ VLC ສຳລັບ WaterMedia. ເຈົ້າຕ້ອງຕິດຕັ້ງດ້ວຍຕົນເອງຈາກ https://www.videolan.org/vlc/. </b>";
	}

	@Override
	public String descargar_vlc() {
		return "ດາວໂຫຼດ VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ຜິດພາດຮ້າຍແຮງ: ຊື່ຂອງ module '" + nombreModulo
				+ "' ມີຕົວອັກສອນທີ່ບໍ່ຖືກຕ້ອງ. ສ່ວນ '" + parteInvalida
				+ "' ບໍ່ແມ່ນ Java identifier ທີ່ຖືກຕ້ອງ. ສິ່ງນີ້ເກີດຂຶ້ນເມື່ອໂມດໃຊ້ຄຳສັງ reserved ຂອງ Java (ເຊັ່ນ 'true', 'class') ຫຼື ຕົວອັກສອນທີ່ບໍ່ອະນຸຍາດໃນຊື່ຂອງມັນ.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "ຕົວອັກສອນບໍ່ຖືກຕ້ອງໃນຊື່ໂມດ";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "ຊື່ຂອງໂມດ '" + nombreModulo + "' ບໍ່ຖືກຕ້ອງເພາະມີ '" + parteInvalida
				+ "', ເຊິ່ງແມ່ນຄຳສັງ reserved ຂອງ Java ຫຼື ຕົວອັກສອນທີ່ບໍ່ອະນຸຍາດ. "
				+ "ຊອກຫາໃນ logs ວ່າໂມດໃດກົງກັບຊື່ນີ້ (ໂດຍທົ່ວໄປແມ່ນຊື່ຂອງໄຟລ໌ JAR)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "ເຂົ້າເຖິງໂຟລເດີຂອງໂມດ ແລະ ແກ້ໄຂໄຟລ໌ <b>mods.toml</b> ພາຍໃນໂຟລເດີ <b>/META-INF/</b>. "
				+ "ປ່ຽນຄ່າຂອງ <b>modId</b> ໃຫ້ໃຊ້ພຽງແຕ່ຕົວອັກສອນ, ຕົວເລກ ແລະ ຂີດຂີດລຸ່ມ (underscore), ໂດຍບໍ່ມີຄຳສັງ reserved ຂອງ Java";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "ຕົວຢ່າງຊື່ທີ່ຖືກຕ້ອງ: 'truemod_shot_enchantment' ແທນທີ່ຈະເປັນ 'true.shot.enchantment'. "
				+ "ຢ່າລືມວ່າຊື່ຂອງໂມດບໍ່ສາມາດມີຈຸດ, ຂີດຂີດລະຫວ່າງ (hyphen), ຫຼື ຄຳສັງ reserved ຂອງ Java ເຊັ່ນ 'true', 'false' ຫຼື 'class'";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ຜິດພາດຮ້າຍແຮງກັບໂມດ: '" + nombreJar
				+ "'. ຂາດ field 'mandatory' ທີ່ຈຳເປັນໃນ dependencies ຂອງມັນ. ສິ່ງນີ້ເກີດຂຶ້ນເມື່ອໄຟລ໌ mods.toml ບໍ່ລະບຸວ່າ dependency ນັ້ນຈຳເປັນຫຼືບໍ່.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "Dependency ຂອງໂມດຂາດ Field ທີ່ຈຳເປັນ";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "ໂມດທີ່ມີບັນຫາແມ່ນ: <b>" + nombreJar + "</b>. ໄຟລ໌ນີ້ມີຂໍ້ຜິດພາດໃນການຕັ້ງຄ່າ dependencies ຂອງມັນ";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "ເປີດໄຟລ໌ <b>mods.toml</b> ພາຍໃນໂຟລເດີ <b>/META-INF/</b> ຂອງໂມດ <b>" + nombreJar + "</b>";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "ໃນສ່ວນຂອງ dependencies, ໃຫ້ແນ່ໃຈວ່າແຕ່ລະ entry ລວມມີ <b>mandatory=true</b> ຫຼື <b>mandatory=false</b> (ຕົວຢ່າງ: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>ຜິດພາດຮ້າຍແຮງກັບໂມດ: '" + nombreJar
				+ "'. ການຕັ້ງຄ່າ access transformer ບໍ່ຖືກຕ້ອງ. ສິ່ງນີ້ເກີດຂຶ້ນເມື່ອໄຟລ໌ການຕັ້ງຄ່າມີ syntax ຜິດ ຫຼື ອ້າງອີງເຖິງ classes/methods ທີ່ບໍ່ມີຢູ່.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Access Transformer ບໍ່ຖືກຕ້ອງ";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "ໂມດທີ່ມີບັນຫາແມ່ນ: <b>" + nombreJar + "</b>. ໂມດນີ້ມີການຕັ້ງຄ່າ access transformer ທີ່ບໍ່ຖືກຕ້ອງ";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "ເປີດໄຟລ໌ <b>accessTransformer.cfg</b> ພາຍໃນໂມດ <b>" + nombreJar
				+ "</b> (ໂດຍທົ່ວໄປຢູ່ໃນໂຟລເດີຮາກຂອງໄຟລ໌ JAR)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "ແກ້ໄຂ syntax ຂອງ access transformer. ແຖວຕ່າງໆຕ້ອງຕາມຮູບແບບ: <b>access class.method</b> (ຕົວຢ່າງ: public net.minecraft.world.entity.Entity.func_200560_a). ລຶບແຖວທີ່ອ້າງອີງເຖິງ classes ຫຼື methods ທີ່ບໍ່ມີຢູ່ໃນເວີຊັນ Minecraft ຂອງເຈົ້າ";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ຜິດພາດຮ້າຍແຮງ: ມີຄວາມຂັດແຍ່ງລະຫວ່າງ ID ຂອງໂມດໃນ annotation @Mod ແລະ ໄຟລ໌ mods.toml. ໂມດ '"
				+ nombreMod + "' ບໍ່ສາມາດໂຫຼດໄດ້ເພາະ IDs ບໍ່ກົງກັນ.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "ຄວາມຂັດແຍ່ງລະຫວ່າງ @Mod ແລະ mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "ໂມດທີ່ກຳລັງພັດທະນາ '" + nombreMod
				+ "' ມີຄວາມຂັດແຍ່ງລະຫວ່າງ ID ໃນ annotation <b>@Mod</b> ແລະ ຄ່າໃນ <b>mods.toml</b>";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "ກວດສອບວ່າ ID ໃນ class ຫຼັກຂອງເຈົ້າກົງກັບຄ່າ <b>modId</b> ໃນໄຟລ໌ <b>/META-INF/mods.toml</b>. ຕົວຢ່າງ: <b>@Mod(\"mimod\")</b> ຕ້ອງກົງກັບ <b>modId=\"mimod\"</b>";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "ຖ້າເຈົ້າໃຊ້ Gradle, ໃຫ້ຮัน <b>clean</b> ຫຼັງຈາກທຳການປ່ຽນແປງ ເພື່ອໃຫ້ແນ່ໃຈວ່າ resources ຖືກອັບເດດຢ່າງຖືກຕ້ອງ. ບາງຄັ້ງໄຟລ໌ເກົ່າຍັງຄົງຢູ່ໃນໂຟລເດີ build";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "client" : "server";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "server" : "client";

		return "<b style='color:#" + config.obtenerColorError() + "'>ຜິດພາດຮ້າຍແຮງ: ກຳລັງພະຍາຍາມໂຫຼດ class '"
				+ nombreClase + "' ໃນສະພາບແວດລ້ອມ " + plataforma + ", ແຕ່ມັນຖືກອອກແບບມາສຳລັບ " + plataformaOpuesta
				+ ". <b>ໃຊ້ຟັງຊັນ 'Arbol de Mods' ໃນແຖບດ້ານຂ້າງເພື່ອຊອກຫາວ່າໂມດໃດກຳລັງພະຍາຍາມໂຫຼດ class ນີ້</b>. "
				+ "ໂມດຖືກສ້າງຂຶ້ນສະເພາະສຳລັບແພລດຟອມໜຶ່ງ ແລະ ບໍ່ສາມາດເຮັດວຽກໃນອີກແພລດຟອມໜຶ່ງໄດ້.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "ໂມດຢູ່ໃນແພລດຟອມທີ່ບໍ່ຖືກຕ້ອງ";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "ໃນແຖບ <b>Arbol de Mods</b> (ທາງຂວາ), ຊອກຫາ references ຂອງ class <b>" + nombreClase
				+ "</b> ເພື່ອລະບຸວ່າໂມດໃດເປັນສາເຫດຂອງບັນຫາ";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "client" : "server";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "server" : "client";

		return "ໂມດທີ່ລະບຸໄວ້ແມ່ນໂມດສຳລັບ <b>" + plataformaOpuesta + "</b> ແລະ ບໍ່ຄວນໃຊ້ໃນສະພາບແວດລ້ອມ " + plataforma
				+ " ຂອງເຈົ້າ.";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "ລຶບໂມດທີ່ມີບັນຫາອອກຈາກໂຟລເດີ <b>mods</b> ຂອງເຈົ້າ. ຖ້າເຈົ້າຕ້ອງການຟັງຊັນທີ່ຄ້າຍຄືກັນສຳລັບແພລດຟອມນີ້, "
				+ "ໃຫ້ຊອກຫາໂມດທາງເລືອກອື່ນທີ່ອອກແບບມາສະເພາະສຳລັບ <b>client</b> ຫຼື <b>server</b> ຕາມທີ່ເໝາະສົມ";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("ຜິດພາດຮ້າຍແຮງ: ຂາດ metadata ສຳລັບ modid '").append(modIdFaltante).append("'. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("ໂມດຕໍ່ໄປນີ້ອາດຈະເປັນສາເຫດຂອງບັນຫາ: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", ແລະ ອື່ນໆ...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"ສິ່ງນີ້ເກີດຂຶ້ນເມື່ອໂມດໜຶ່ງຂຶ້ນກັບອີກໂມດໜຶ່ງທີ່ບໍ່ໄດ້ຕິດຕັ້ງ ຫຼື ມີໄຟລ໌ mods.toml ທີ່ບໍ່ຖືກຕ້ອງ.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "ຂາດ Metadata ໃນ mods.toml";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("ໂມດຕໍ່ໄປນີ້ຂຶ້ນກັບ '").append(modIdFaltante).append("': <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", ແລະ ອື່ນໆ...");
			paso.append("</b>. ໃຊ້ຟັງຊັນ <b>Arbol de Mods</b> ເພື່ອຢືນຢັນວ່າໂມດໃດເປັນສາເຫດຂອງບັນຫາ");
			return paso.toString();
		}
		return "ໂມດໜຶ່ງກຳລັງພະຍາຍາມຂຶ້ນກັບ '" + modIdFaltante
				+ "', ແຕ່ໂມດນີ້ບໍ່ໄດ້ຕິດຕັ້ງ. ໃຊ້ຟັງຊັນ <b>Arbol de Mods</b> ເພື່ອລະບຸວ່າໂມດໃດເປັນສາເຫດຂອງບັນຫາ";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "ເຈົ້າມີສອງທາງເລືອກ:<br/>" + "1. <b>ຕິດຕັ້ງໂມດທີ່ຂາດ</b>: ຊອກຫາ ແລະ ຕິດຕັ້ງໂມດທີ່ມີ ID '" + modIdFaltante
				+ "'<br/>" + "2. <b>ລຶບໂມດທີ່ຂຶ້ນກັບມັນ</b>: ຖ້າເຈົ້າບໍ່ຕ້ອງການຟັງຊັນນັ້ນ, ໃຫ້ລຶບໂມດທີ່ຂຶ້ນກັບ '"
				+ modIdFaltante + "'";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "ຖ້າໂມດ '" + modIdFaltante + "' ແມ່ນ library (ເຊັ່ນ 'forge', 'minecraft', 'curios'), "
				+ "ໃຫ້ແນ່ໃຈວ່າເຈົ້າຕິດຕັ້ງເວີຊັນ Minecraft ແລະ Forge ທີ່ຖືກຕ້ອງ. "
				+ "ຖ້າແມ່ນໂມດທຳມະດາ, ໃຫ້ຊອກຫາໃນໜ້າດາວໂຫຼດຂອງມັນເພື່ອເບິ່ງ requirements ທີ່ຈຳເປັນ";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>ຄຳເຕືອນ: ຜິດພາດໃນການເລີ່ມຕົ້ນລະບົບສຽງ. ສຽງ ແລະ ເພງຖືກປິດໃຊ້ງານ. ຜິດພາດນີ້ມັກກ່ຽວຂ້ອງກັບໂມດ SoundPhysicsMod ແລະ ອາດເກີດຈາກຄວາມຂັດແຍ່ງກັບ sound libraries ອື່ນໆ.</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "ຜິດພາດໃນລະບົບສຽງ";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "ຜິດພາດນີ້ມັກກ່ຽວຂ້ອງກັບ <b>SoundPhysicsMod</b>. ກວດສອບວ່າເຈົ້າຕິດຕັ້ງເວີຊັນລ່າສຸດທີ່ເຂົ້າກັນໄດ້ກັບເວີຊັນ Minecraft ຂອງເຈົ້າຫຼືບໍ່";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "ຖ້າເຈົ້າໃຊ້ໂມດສຽງອື່ນໆ (ເຊັ່ນ Sound Filters, Dynamic Surroundings, ອື່ນໆ), ໃຫ້ລອງລຶບ SoundPhysicsMod ຊົ່ວຄາວເພື່ອເບິ່ງວ່າແກ້ໄຂຄວາມຂັດແຍ່ງໄດ້ຫຼືບໍ່";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "ກວດສອບໂຟລເດີ <b>logs</b> ເພື່ອຊອກຫາຂໍ້ຄວາມເພີ່ມເຕີມທີ່ກ່ຽວຂ້ອງກັບ LWJGL ຫຼື OpenAL, ເຊິ່ງອາດບົ່ງບອກເຖິງບັນຫາກັບ sound libraries ພື້ນຖານ";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("ຜິດພາດຮ້າຍແຮງ: Class '").append(nombreClase)
				.append("' ຖືກລົງທະບຽນເປັນ event listener ແຕ່ບໍ່ມີ methods ທີ່ຖືກຕ້ອງ. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Class ນີ້ຢູ່ໃນໂມດຕໍ່ໄປນີ້: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", ແລະ ອື່ນໆ...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"ສິ່ງນີ້ເກີດຂຶ້ນເມື່ອລົງທະບຽນ class ເພື່ອຟັງ events ໂດຍບໍ່ມີ methods ທີ່ໃຊ້ annotation @SubscribeEvent.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "Class ທີ່ລົງທະບຽນແລ້ວບໍ່ມີ Event Listeners";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("Class ທີ່ມີບັນຫາຢູ່ໃນໂມດເຫຼົ່ານີ້: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", ແລະ ອື່ນໆ...");
			paso.append("</b>. ໂມດເຫຼົ່ານີ້ກຳລັງພະຍາຍາມລົງທະບຽນ events ໂດຍບໍ່ມີ methods ທີ່ຖືກຕ້ອງ");
			return paso.toString();
		}
		return "Class <b>" + nombreClase
				+ "</b> ຖືກລົງທະບຽນເພື່ອຟັງ events ແຕ່ບໍ່ມີ methods ທີ່ໃຊ້ annotation <b>@SubscribeEvent</b>. ໃຊ້ຟັງຊັນ <b>Arbol de Mods</b> ເພື່ອລະບຸວ່າໂມດໃດມີ class ນີ້";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "ໃນ source code, ກວດສອບວ່າ class <b>" + nombreClase + "</b> ມີຢ່າງໜ້ອຍໜຶ່ງ method ທີ່ມີ: "
				+ "<b>@SubscribeEvent public void nomeMethod(EventoSpecifico event) { ... }</b>. "
				+ "ຖ້າແມ່ນ inner class, ໃຫ້ແນ່ໃຈວ່າມັນບໍ່ໄດ້ຖືກ mark ວ່າເປັນ static";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("ສຳລັບໂມດທີ່ລະບຸໄວ້ (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", ອື່ນໆ.");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("ຕິດຕໍ່ developer ຂອງໂມດນີ້ເພື່ອໃຫ້ແກ້ໄຂບັນຫາ. ");
			} else {
				paso.append("ຕິດຕໍ່ developers ຂອງໂມດເຫຼົ່ານີ້ເພື່ອໃຫ້ແກ້ໄຂບັນຫາ. ");
			}
		}

		paso.append(
				"ຖ້າເຈົ້າແມ່ນ developer, ໃຫ້ລຶບການລົງທະບຽນຂອງ class ນີ້ໃນ EventBus ຫຼື ເພີ່ມ methods @SubscribeEvent ທີ່ຖືກຕ້ອງ");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		String mensaje = "<b style='color:#" + config.obtenerColorError()
				+ "'>ຜິດພາດຮ້າຍແຮງ: ຜິດພາດໃນ UnionFileSystem ໃນຂະນະປະມວນຜົນ '" + nombreArchivo + "'. ";

		mensaje += "ຜິດພາດນີ້ພົບເຫັນບ່ອຍໃນ modpacks ທີ່ຕັ້ງຄ່າໄວ້ແລ້ວ ແລະ ກ່ຽວຂ້ອງໂດຍກົງກັບບັນຫາຂອງ launcher. ";

		mensaje += "ລະບົບບໍ່ສາມາດອ່ານໄຟລ໌ຂອງໂມດໄດ້ຢ່າງຖືກຕ້ອງເພາະມັນເສຍຫາຍ ຫຼື ບໍ່ສົມບູນ.</b>";

		return mensaje;
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "ຜິດພາດ UnionFileSystem - ໄຟລ໌ເສຍຫາຍ";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		String paso = "ກວດພົບຜິດພາດສະເພາະ <b>cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException</b> ກັບໄຟລ໌ <b>"
				+ nombreArchivo + "</b>.";

		paso += " ນີ້ແມ່ນຜິດພາດທີ່ຮູ້ຈັກກັນດີໃນ launchers ຂອງ modpacks ເມື່ອໄຟລ໌ບໍ່ໄດ້ຖືກດາວໂຫຼດຢ່າງສົມບູນ.";

		return paso;
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "ຕິດຕັ້ງ modpack ໃໝ່ທັງໝົດ. ຜິດພາດນີ້ເກີດຂຶ້ນຫຼັກໆເມື່ອ launcher ບໍ່ສາມາດດາວໂຫຼດໄຟລ໌ທັງໝົດໄດ້ສຳເລັດ. "
				+ "ຖ້າເຈົ້າກຳລັງໃຊ້ <b>Luna Pixel</b>, ເຮົາແນະນຳຢ່າງແຮງໃຫ້ໃຊ້ <b>ATLauncher</b> ແທນ, "
				+ "ເພາະວ່າ launcher ນີ້ຈັດການໄຟລ໌ໂມດໄດ້ດີກວ່າ ແລະ ຫຼີກລ່ຽງຜິດພາດສະເພາະນີ້.";

	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "ຖ້າບັນຫາຍັງຄົງຢູ່ຫຼັງຈາກຕິດຕັ້ງໃໝ່: <br/>" + "1. <b>ປ່ຽນໄປໃຊ້ launcher ອື່ນ</b> <br/>"
				+ "2. ຖ້າເຈົ້າໃຊ້ <b>Luna Pixel</b>, <b>ໃຫ້ໃຊ້ ATLauncher</b> ເຊິ່ງໜ້າເຊື່ອຖືກວ່າໃນການຫຼີກລ່ຽງຜິດພາດສະເພາະນີ້<br/>"
				+ "3. ກວດສອບການເຊື່ອມຕໍ່ອິນເຕີເນັດ ແລະ ພື້ນທີ່ໃນດິສກຂອງເຈົ້າກ່ອນທີ່ຈະຕິດຕັ້ງ modpack ໃໝ່";

	}

	/**
	 * Obtiene el mensaje de confirmación para habilitar el proxy de
	 * System.out/System.err
	 * 
	 * @return Mensaje explicativo con advertencias y requisitos
	 */
	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "ເປີດໃຊ້ ProxySysOutSysErr?\n\n"
				+ "ຕົວເລືອກນີ້ໃຫ້ CrashDetector ເຂົ້າເຖິງ System.out ແລະ System.err ເມື່ອ launcher ບໍ່ສະໜອງ logs.\n\n"
				+ "ຄວນເປີດໃຊ້ເມື່ອເຈົ້າບໍ່ສາມາດ paste log ດ້ວຍຕົນເອງໄດ້ເທົ່ານັ້ນ.\n\n"
				+ "ຄຳເຕືອນ: ສິ່ງນີ້ອາດຈະຂັດແຍ່ງກັບໂມດ ຫຼື launchers ບາງຕົວ.\n\n"
				+ "ຕ້ອງການ restart ເກມ/app ເພື່ອໃຫ້ການປ່ຽນແປງມີຜົນ.";
	}

	/**
	 * Obtiene el título para diálogos de confirmación
	 * 
	 * @return Título en español para ventanas de confirmación
	 */
	@Override
	public String confirmacionTitulo() {
		return "ຢືນຢັນ";
	}

	/**
	 * Obtiene el mensaje de éxito tras habilitar el proxy
	 * 
	 * @return Mensaje informativo sobre el estado del proxy
	 */
	@Override
	public String proxyHabilitadoMensaje() {
		return "ເປີດໃຊ້ ProxySysOutSysErr ສຳເລັດແລ້ວ.\n\n" + "ຕ້ອງການ restart CrashDetector ເພື່ອໃຫ້ການປ່ຽນແປງມີຜົນ.";
	}

	/**
	 * Obtiene el título para diálogos informativos
	 * 
	 * @return Título en español para ventanas de información
	 */
	@Override
	public String informacionTitulo() {
		return "ຂໍ້ມູນ";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("ຜິດພາດຮ້າຍແຮງ: AzureLib ແລະ GeckoLib ເລີ່ມຕົ້ນໄວເກີນໄປ! ");
		} else if (azureLibError) {
			mensaje.append("ຜິດພາດຮ້າຍແຮງ: AzureLib ເລີ່ມຕົ້ນໄວເກີນໄປ! ");
		} else if (geckoLibError) {
			mensaje.append("ຜິດພາດຮ້າຍແຮງ: GeckoLib ເລີ່ມຕົ້ນໄວເກີນໄປ! ");
		}

		mensaje.append(
				"ຜິດພາດນີ້ເກີດຂຶ້ນເມື່ອພະຍາຍາມໃຊ້ໂມດ Fabric ກັບເວີຊັນທີ່ບໍ່ແມ່ນ Fabric ຂອງ libraries ເຫຼົ່ານີ້. ");

		if (connectorPresente) {
			mensaje.append(
					"ກວດພົບໂມດຄວາມເຂົ້າກັນໄດ້ (Sinytra Connector ຫຼື specialcompatibilityoperation), ຊີ້ບອກວ່າເຈົ້າກຳລັງພະຍາຍາມໃຊ້ໂມດ Fabric ໃນສະພາບແວດລ້ອມ Forge ຫຼື FeatureCreep. ");
			mensaje.append(
					"ກວດສອບຜິດພາດ 'Error de inicialización de FabricMC' ໃນ logs ເພື່ອລະບຸວ່າໂມດໃດເປັນສາເຫດຂອງບັນຫາ. ");
		}

		mensaje.append(
				"AzureLib ແລະ GeckoLib ແມ່ນ libraries ທີ່ສຳຄັນສຳລັບໂມດ animation, ແຕ່ຕ້ອງກົງກັບແພລດຟອມທີ່ຖືກຕ້ອງ (Fabric ຫຼື Forge). ");
		mensaje.append("ເກມບໍ່ສາມາດໂຫຼດໂມດ animation ໄດ້ຢ່າງຖືກຕ້ອງເນື່ອງຈາກຄວາມຂັດແຍ່ງໃນການເລີ່ມຕົ້ນນີ້.");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "Library ເລີ່ມຕົ້ນໄວເກີນໄປ";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "ກວດສອບຜິດພາດ 'Error de inicialización de FabricMC' ເພື່ອລະບຸໂມດທີ່ມີບັນຫາ";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "ກວດສອບວ່າເຈົ້າກຳລັງໃຊ້ເວີຊັນທີ່ຖືກຕ້ອງຂອງ AzureLib/GeckoLib ສຳລັບແພລດຟອມຂອງເຈົ້າ (Forge ຫຼື Fabric)";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ຜິດພາດຮ້າຍແຮງ: ຄວາມບໍ່ເຂົ້າກັນລະຫວ່າງ C2ME ແລະ ໂມດການເຊື່ອມຕໍ່. "
				+ "ຜິດພາດນີ້ເກີດຂຶ້ນເພາະ C2ME ພະຍາຍາມເຂົ້າເຖິງ components ພາຍໃນຂອງ Java ທີ່ຖືກຈຳກັດໃນສະພາບແວດລ້ອມທີ່ມີ "
				+ "Sinytra Connector ຫຼື specialcompatibilityoperation ຫຼື ໂມດຄວາມເຂົ້າກັນໄດ້ Fabric/Forge ອື່ນໆ. "
				+ "<b>C2ME ບໍ່ເຂົ້າກັນກັບສະພາບແວດລ້ອມເຫຼົ່ານີ້, ແຕ່ <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> ແມ່ນທາງເລືອກທີ່ແນະນຳ</b> ທີ່ເຮັດວຽກໄດ້ຢ່າງຖືກຕ້ອງ "
				+ "ກັບໂມດການເຊື່ອມຕໍ່. ເກມບໍ່ສາມາດເລີ່ມຕົ້ນໄດ້ເນື່ອງຈາກຄວາມຂັດແຍ່ງຂອງສິດທິຄວາມປອດໄພຂອງ Java ນີ້.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "C2ME ບໍ່ເຂົ້າກັນກັບໂມດການເຊື່ອມຕໍ່";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "ລຶບ C2ME ອອກຈາກໂຟລເດີ mods ຂອງເຈົ້າ";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "ດາວໂຫຼດ ແລະ ຕິດຕັ້ງ <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> ແທນ (ເຂົ້າກັນໄດ້ກັບ Sinytra Connector)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "ກວດສອບວ່າໂມດການເຊື່ອມຕໍ່ທັງໝົດ (ເຊັ່ນ Sinytra Connector) ຖືກອັບເດດເປັນເວີຊັນລ່າສຸດແລ້ວ";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ຜິດພາດຮ້າຍແຮງ: ລົ້ມເຫຼວໃນການໂຫຼດ plugin JEI ສຳລັບໂມດ '" + modId + "'. Class '" + nombreClase
				+ "' (plugin ID: '" + pluginId + "') ໄດ້ສ້າງຂໍ້ຜິດພາດທີ່ເຮັດໃຫ້ເກມ crash ໃນລະຫວ່າງການເລີ່ມຕົ້ນ. "
				+ "ບັນຫານີ້ເກີດຂຶ້ນເມື່ອໂມດມີການ integrate JEI ທີ່ບໍ່ເຂົ້າກັນ ຫຼື ມີຂໍ້ບົກພ່ອງທີ່ຂັດຂວາງການ initialize ເກມ.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "Plugin JEI ລົ້ມເຫຼວ - ເຮັດໃຫ້ Crash";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "ໂມດ <b>" + modId
				+ "</b> ມີ plugin JEI ທີ່ມີຂໍ້ບົກພ່ອງທີ່ເຮັດໃຫ້ເກີດ crash. ໃຊ້ຟັງຊັນ <b>Arbol de Mods</b> ເພື່ອຢືນຢັນວ່າໂມດໃດເປັນສາເຫດຂອງບັນຫາ";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "ລຶບໂມດ <b>" + modId + "</b> ອອກຈາກໂຟລເດີ mods ຊົ່ວຄາວ ເພື່ອກວດສອບວ່າແກ້ໄຂບັນຫາ crash ໄດ້ຫຼືບໍ່";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "ຊອກຫາການອັບເດດຂອງໂມດ <b>" + modId + "</b> ຫຼື ຕິດຕໍ່ developer ຂອງມັນໂດຍລາຍງານບັນຫາກັບ plugin JEI. "
				+ "ໃນຂະນະນີ້, ຕ້ອງລຶບໂມດນີ້ອອກເພື່ອໃຫ້ສາມາດເລີ່ມເກມໄດ້";
	}

	/**
	 * Obtiene el título de la aplicación
	 * 
	 * @return Título de la ventana principal
	 */
	@Override
	public String tituloLectador() {
		return "Console Reader - Crash Detector";
	}

	/**
	 * Obtiene el título para la leyenda de colores
	 * 
	 * @return Título de la sección de leyenda
	 */
	@Override
	public String obtenerTituloLeyenda() {
		return "ຄຳອະທິບາຍສີ";
	}

	/**
	 * Obtiene el texto para identificar errores en la leyenda
	 * 
	 * @return Texto descriptivo para errores
	 */
	@Override
	public String obtenerErrorEnLeyenda() {
		return "ຜິດພາດຮ້າຍແຮງ";
	}

	/**
	 * Obtiene el texto para identificar stacktraces en la leyenda
	 * 
	 * @return Texto descriptivo para stacktraces
	 */
	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "Stack Traces";
	}

	/**
	 * Obtiene el título para ventanas de error
	 * 
	 * @return Título estándar para mensajes de error
	 */
	@Override
	public String obtenerTituloError() {
		return "ຜິດພາດ";
	}

	/**
	 * Obtiene el mensaje para errores al procesar líneas de log
	 * 
	 * @return Mensaje de error específico
	 */
	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "ເກີດຂໍ້ຜິດພາດໃນຂະນະປະມວນຜົນແຖວທີ່ເລືອກ";
	}

	/**
	 * Obtiene el título para el área de nombre de error
	 * 
	 * @return Título del panel de nombre de error
	 */
	@Override
	public String obtenerNombreError() {
		return "ຊື່ຜິດພາດ";
	}

	/**
	 * Obtiene el título para el área de descripción de error
	 * 
	 * @return Título del panel de descripción de error
	 */
	@Override
	public String obtenerDescripcionError() {
		return "ຄຳອະທິບາຍລະອຽດ";
	}

	/**
	 * Obtiene el título para el selector de consolas
	 * 
	 * @return Título del combobox de selección
	 */
	@Override
	public String obtenerSeleccionarConsola() {
		return "ເລືອກ Console";
	}

	/**
	 * Obtiene el nombre predeterminado para errores
	 * 
	 * @return Nombre genérico para errores
	 */
	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "ຜິດພາດທີ່ບໍ່ລະບຸ";
	}

	/**
	 * Obtiene la descripción predeterminada para errores
	 * 
	 * @return Descripción genérica para errores
	 */
	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "ກວດພົບຜິດພາດຮ້າຍແຮງໃນ logs. " + "ກວດສອບ stack trace ເພື່ອລະບຸສາເຫດຫຼັກ.";
	}

	/**
	 * Obtiene el mensaje para errores de lectura de archivos
	 * 
	 * @return Mensaje específico para fallos en lectura
	 */
	@Override
	public String obtenerErrorLecturaArchivo() {
		return "ບໍ່ສາມາດອ່ານໄຟລ໌ logs ໄດ້. " + "ກວດສອບວ່າໄຟລ໌ມີຢູ່ ແລະ ມີສິດທິໃນການອ່ານ.";
	}

	/**
	 * Obtiene la etiqueta para el botón en la barra lateral
	 * 
	 * @return Texto que aparecerá en el botón lateral
	 */
	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "Log Analyzer";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("ຜິດພາດຮ້າຍແຮງ: ລົ້ມເຫຼວໃນການລົງທະບຽນ automatic subscribers ສຳລັບໂມດ '").append(modId)
				.append("'. ");

		mensaje.append("Class ທີ່ມີບັນຫາ: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Class ນີ້ຢູ່ໃນ: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", ແລະ ອື່ນໆ...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"ຜິດພາດນີ້ເກີດຂຶ້ນເມື່ອໂມດພະຍາຍາມລົງທະບຽນ class ເປັນ automatic subscriber ແຕ່ class ບໍ່ສາມາດໂຫຼດໄດ້ຢ່າງຖືກຕ້ອງ. ");
		mensaje.append("<b>ກວດສອບຜິດພາດອື່ນໆໃນ log, ເພາະບັນຫາທີ່ແທ້ຈິງອາດຢູ່ໃນສ່ວນອື່ນຂອງການລົງທະບຽນ</b>.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "ລົ້ມເຫຼວໃນການລົງທະບຽນ Automatic Subscribers";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "ໂມດ <b>" + modId + "</b> ກຳລັງພະຍາຍາມລົງທະບຽນ class <b>" + nombreClase
				+ "</b> ເປັນ automatic subscriber, ແຕ່ລົ້ມເຫຼວ. ກວດສອບວ່າ class ນີ້ມີຢູ່ ແລະ ສາມາດເຂົ້າເຖິງໄດ້";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder(
					"Class ທີ່ມີບັນຫາ <b>" + nombreClase + "</b> ຢູ່ໃນໄຟລ໌ເຫຼົ່ານີ້: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", ແລະ ອື່ນໆ...");
			paso.append("</b>. ");
			paso.append("ໃຊ້ຟັງຊັນ <b>Arbol de Mods</b> ເພື່ອຢືນຢັນວ່າໄຟລ໌ໃດມີ class ທີ່ມີບັນຫາ");
			return paso.toString();
		}
		return "Class <b>" + nombreClase + "</b> ບໍ່ພົບໃນໄຟລ໌ໂມດໃດໆ. ກວດສອບວ່າໂມດ <b>" + modId
				+ "</b> ຕິດຕັ້ງຢ່າງຖືກຕ້ອງ. ໃຊ້ຟັງຊັນ <b>Arbol de Mods</b> ເພື່ອຊ່ວຍລະບຸບັນຫາ";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "ອັບເດດໂມດ <b>" + modId + "</b> ເປັນເວີຊັນລ່າສຸດທີ່ເຂົ້າກັນໄດ້ກັບເວີຊັນ Minecraft ແລະ Forge ຂອງເຈົ້າ. "
				+ "ຖ້າບັນຫາຍັງຄົງຢູ່, ໃຫ້ຕິດຕໍ່ developer ຂອງໂມດໂດຍລາຍງານຜິດພາດກັບ class ທີ່ມີບັນຫາ";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "ກວດສອບ <b>ຜິດພາດອື່ນໆໃນ log</b> ກ່ອນຂໍ້ຄວາມນີ້, ເພາະບັນຫາທີ່ແທ້ຈິງອາດຢູ່ໃນສ່ວນອື່ນຂອງການລົງທະບຽນ. "
				+ "ບາງຄັ້ງຜິດພາດກ່ອນໜ້ານີ້ຂັດຂວາງບໍ່ໃຫ້ໂຫຼດ classes ທີ່ຈຳເປັນສຳລັບການລົງທະບຽນ subscribers ໄດ້ຢ່າງຖືກຕ້ອງ";
	}

	@Override
	public String limpiado() {
		return "ທຳຄວາມສະອາດແລ້ວ";
	}

	@Override
	public String original() {
		return "ດັ້ງເດີມ";
	}

	@Override
	public String verEnConsola() {
		return "ເບິ່ງໃນ Console";
	}

	@Override
	public String advertencia() {
		return "ຄຳເຕືອນ";
	}

	@Override
	public String fatal() {
		return "ຮ້າຍແຮງ";
	}

	@Override
	public String noRegistroDeBattly() {
		return "BattlyLauncher ບໍ່ມີ log ຫຼື console ສຳລັບ copy. ເຈົ້າສາມາດໃຊ້ ProxySysOutSysErr ເພື່ອ intercept STDOUT ແລະ STDERR ແລະ restart ເກມ, ແຕ່ ProxySysOutSysErr ອາດຂັດແຍ່ງກັບໂມດທີ່ປ່ຽນແປງ STDOUT ຫຼື STDERR";
	}

	@Override
	public String noRegistroDeNightWorld() {
		return "ເຈົ້າຕ້ອງເປີດ debug mode ໃນການຕັ້ງຄ່າຂອງ NightWorld ເພື່ອໃຫ້ໄດ້ log ຂອງ launcher. ມັນສຳຄັນຫຼາຍ ໂດຍສະເພາະເພາະມັນມີ STDOUT ແລະ STDERR";
	}

	@Override
	public String noRegistroDeMCServidor() {
		return "ເຈົ້າຕ້ອງບັນທຶກ ຫຼື paste ເນື້ອຫາຂອງ Terminal ຂອງ server ເຈົ້າ ເພາະມັນມີຂໍ້ມູນທີ່ບໍ່ມີໃນ log ອື່ນໆ ລວມທັງ STDOUT, STDERR, ແລະ ຜິດພາດອື່ນໆ. ກະລຸນາ paste ເນື້ອຫາຂອງ session ລ່າສຸດ. ສຳລັບຄັ້ງຕໍ່ໄປ, ເຈົ້າສາມາດບັນທຶກເນື້ອຫາຂອງ terminal ເປັນໄຟລ໌ cd_launcherlog. ເພື່ອຫຼີກລ່ຽງການຕ້ອງ paste, ໃຫ້ເພີ່ມ >> cd_launcherlog ຫຼັງຄຳສັ່ງ, ຕາມທີ່ສະແດງໃນຮູບ. ສັງເກດວ່າສິ່ງນີ້ຈະປ້ອງກັນບໍ່ໃຫ້ສະແດງໃນ terminal; ມັນຈະປາກົດໃນໄຟລ໌ນັ້ນເທົ່ານັ້ນ.";
	}

//Métodos para Idioma relacionados con la verificación LexForgeMLTransformerEnNeoForge

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("ຜິດພາດຮ້າຍແຮງ: ກວດພົບ transformer ຂອງ LexForge ໃນສະພາບແວດລ້ອມ NeoForge. ");
		sb.append("</b>");

		sb.append("Class ທີ່ກ່ຽວຂ້ອງ: <b>").append(claseReceptora).append("</b>. ");
		sb.append("Interface ທີ່ໄດ້ຮັບຜົນກະທົບແມ່ນ <b>").append(interfazObjetivo).append("</b> ");
		sb.append("ແລະ ຂາດ method <b>").append(firmaMetodoFaltante).append("</b>. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Class ພົບຢູ່ໃນ: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", ແລະ ອື່ນໆ...");
			sb.append("</b>. ");
		} else {
			sb.append("ບໍ່ພົບ JARs ທີ່ມີ class ນີ້; ມັນອາດຈະຖືກ shadow ຫຼື ລວມຢູ່ເປັນ jar-in-jar. ");
		}

		sb.append(
				"ຄວາມລົ້ມເຫຼວນີ້ປາກົດເມື່ອ transformer/service ຂອງ ModLauncher ທີ່ compile ສຳລັບ MinecraftForge/LexForge ");
		sb.append("ຖືກໂຫຼດພາຍໃຕ້ NeoForge ດ້ວຍເວີຊັນ API ຂອງ ModLauncher ທີ່ບໍ່ເຂົ້າກັນ. ");
		sb.append("ອັບເດດ ຫຼື ແທນທີ່ component ສຳລັບ NeoForge.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "Transformer ຂອງ LexForge ຖືກໃຊ້ໃນ NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "ລະບຸ transformer ທີ່ບໍ່ເຂົ້າກັນ: <b>" + claseReceptora + "</b>. " + "API ທີ່ຄາດຫວັງແມ່ນ <b>"
				+ interfazObjetivo + "</b> ແລະ ຂາດ <b>" + firmaMetodoFaltante + "</b>. "
				+ "ກວດສອບວ່າໂມດລົງທະບຽນ class ນີ້ໃນ <b>META-INF/services</b> ແລະ ລຶບມັນ ຫຼື ປິດໃຊ້ງານໃນ NeoForge.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("ສະຖານທີ່ຂອງໂມດທີ່ກ່ຽວຂ້ອງ: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", ແລະ ອື່ນໆ...");
			sb.append("</b>. ");
		} else {
			sb.append("ບໍ່ພົບ JARs ທີ່ມີ class. ກວດສອບ jar-in-jar ແລະ dependencies ທີ່ຖືກ shadow. ");
		}
		sb.append("ລຶບ JARs ເຫຼົ່ານັ້ນຊົ່ວຄາວ ຫຼື ໃຊ້ເວີຊັນທີ່ເຂົ້າກັນໄດ້ກັບ NeoForge ເພື່ອຢືນຢັນແຫຼ່ງທີ່ມາ.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "ແທນທີ່ component ດ້ວຍເວີຊັນສະເພາະຂອງ NeoForge ຫຼື recompile ມັນຕໍ່ກັບ "
				+ "ເວີຊັນ ModLauncher ທີ່ໃຊ້ໂດຍ NeoForge. ຫຼີກລ່ຽງ binaries ເກົ່າຂອງ LexForge/MinecraftForge.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "ທຳຄວາມສະອາດໂຟລເດີ mods ແລະ ລຶບ jar-in-jar ທີ່ຊ້ຳກັນ. ລ້າງ cache ຂອງ launcher ຖ້າຈຳເປັນ "
				+ "ແລະ ເລີ່ມໃໝ່ເພື່ອກວດສອບວ່າບໍ່ມີ transformers ຂອງ LexForge ຖືກໂຫຼດ.";
	}

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia ບໍ່ສາມາດເລີ່ມຕົ້ນໄດ້: Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("ບໍ່ເຂົ້າກັນ.</b> ");
		sb.append("ລຶບ Xenon ແລະ ໃຊ້ Embeddium ຫຼື Sodium. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("ກວດພົບໃນ: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", ແລະ ອື່ນໆ...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia ບໍ່ເຂົ້າກັນກັບ Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "ກວດພົບ " + label + " ທີ່ບໍ່ເຂົ້າກັນກັບ WaterMedia. ໃຫ້ລຶບມັນອອກຈາກ profile.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("ສະຖານທີ່: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", ແລະ ອື່ນໆ...");
			sb.append("</b>. ລຶບ JAR ນັ້ນ.");
			return sb.toString();
		}
		return "ບໍ່ພົບ JARs. ກວດສອບໂຟລເດີ mods ແລະ ລຶບ Xenon.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "ຕິດຕັ້ງ Embeddium ຫຼື Sodium ເປັນຕົວແທນ ແລະ restart ເກມ.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "ຜິດພາດໃນການບີບອັດ (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>Deflater ຖືກປິດໃນຂະນະ copy ຊັບພະຍາກອນຂອງ TACZ.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("ກ່ຽວຂ້ອງກັບ: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", ແລະ ອື່ນໆ");
			sb.append("</b>. ");
		}
		sb.append(
				"<br/><b>ວິທີແກ້ໄຂ:</b> ໃນ <code>tacz/tacz-pre.toml</code> ໃຫ້ຕັ້ງ <code>DefaultPackDebug=true</code>. ")
				.append("ຖ້າຈຳເປັນ, ໃຫ້ສ້າງ map ກ່ອນ ແລ້ວຈຶ່ງເປີດໃຊ້ງານ.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "ໃນ tacz/tacz-pre.toml ໃຫ້ຕັ້ງ DefaultPackDebug=true. ຖ້າຈຳເປັນ, ໃຫ້ສ້າງ map ກ່ອນ ແລ້ວຈຶ່ງເປີດໃຊ້ງານ.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "Density Functions ບໍ່ຖືກ link";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>ຂາດ density functions ໃນ registry.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("ຂາດ: ");
			for (int i = 0; i < Math.min(4, claves.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append("<code>").append(claves.get(i)).append("</code>");
			}
			if (claves.size() > 4)
				sb.append(", …");
			sb.append(". ");
		}
		sb.append(
				"<br/><b>ວິທີແກ້ໄຂ:</b> ຕິດຕັ້ງ ຫຼື ເປີດໃຊ້ງານ mod/datapack ທີ່ກຳນົດ functions ເຫຼົ່ານີ້ ແລ້ວ restart. ສາເຫດທົ່ວໄປອີກຢ່າງໜຶ່ງຂອງບັນຫານີ້ແມ່ນເມື່ອເຈົ້າມີ mod ທີ່ຈຳເປັນ, ແຕ່ມັນມີບັນຫາ ຫຼື ຂັດແຍ່ງກັບໂມດອື່ນ; ຕົວຢ່າງ, Terralith ມີບັນຫາຫຼາຍ ແລະ ອາດເຮັດໃຫ້ເກີດຜິດພາດນີ້ ແລະ ອື່ນໆ, ລວມທັງຜິດພາດກັບ JSON.");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "ຕິດຕັ້ງ ຫຼື ເປີດໃຊ້ງານ mod/datapack ທີ່ໃຫ້ functions ເຫຼົ່ານີ້ ແລ້ວ restart ເກມ.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// Mensaje breve, en color de error, mencionando explícitamente el mod
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Registry entry ບໍ່ມີຢູ່: ").append(claveFaltante).append(". ");
		sb.append("ພົບເຫັນບ່ອຍກັບ Steam & Railways alpha ສຳລັບ Create 6.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (alpha)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "ລຶບ ຫຼື ແທນທີ່ Steam & Railways alpha ສຳລັບ Create 6 ດ້ວຍເວີຊັນທີ່ເຂົ້າກັນໄດ້.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// Corto, con color de error y recomendación directa
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ຄວາມຂັດແຍ່ງໃນການໂຫຼດ: Multiworld ຮ່ວມກັບ Sodium/Embeddium/Rubidium ເຮັດໃຫ້ເກີດ ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance). ")
				.append("ຄຳແນະນຳ: ລຶບ Multiworld ຫຼື performance mod, ຫຼື ໃຊ້ເວີຊັນທີ່ເຂົ້າກັນໄດ້.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "ຄວາມຂັດແຍ່ງ: Multiworld ກັບ performance mods";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "ຖອນການຕິດຕັ້ງ Multiworld ຫຼື Sodium/Embeddium/Rubidium, ຫຼື ອັບເດດເປັນເວີຊັນທີ່ເຂົ້າກັນໄດ້.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Sodium ກວດພົບ GPU driver ທີ່ບໍ່ເຂົ້າກັນ. "
				+ "ອັບເດດ driver ຂອງ GPU ຂອງເຈົ້າໃຫ້ຢ່າງໜ້ອຍເທົ່າກັບຄວາມຕ້ອງການຂັ້ນຕ່ຳ ຫຼື ປະຕິບັດຕາມຄູ່ມືຂອງ Sodium."
				+ "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "ຜິດພາດ OpenGL Context";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OpenGL ລົ້ມເຫຼວ: ບໍ່ມີ context ປັດຈຸບັນ ຫຼື function ບໍ່ມີຢູ່ໃນ context ນີ້. "
				+ "ມັນອາດຈະເປັນບັນຫາຂອງ video drivers." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "ອັບເດດ/ຕິດຕັ້ງ GPU drivers ໃໝ່ ແລ້ວ restart; ປິດໃຊ້ງານ overlays ແລະ ລອງໂດຍບໍ່ມີ performance mods.";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "Link ຖືກ copy ໄປຍັງ clipboard ແລ້ວ.";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		return "ຄົ້ນຫາ ພາຍໃນໄຟລ໌ບີບອັດ (.zip/.jar/.war/.ear/.fpm/.rar ຂອງ Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ຜິດພາດຄວາມລະອຽດຂອງ texture: ບໍ່ສາມາດປັບ "
				+ recurso + " - ຂະໜາດ: " + tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "ຜິດພາດຄວາມລະອຽດຂອງ Texture";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "ຜິດພາດນີ້ເກີດຂຶ້ນເມື່ອ textures ມີຂະໜາດໃຫຍ່ເກີນໄປ ຫຼື ມີ resource packs ຫຼາຍເກີນໄປ. "
				+ "ລອງໃຊ້ resource packs ທີ່ມີຄວາມລະອຽດຕ່ຳກວ່າ ຫຼື ລຶບ resource packs ອອກບາງອັນ. "
				+ "ກວດສອບວ່າເຈົ້າບໍ່ໄດ້ເພີ່ມ custom textures ທີ່ມີຄວາມລະອຽດສູງກວ່າຂີດຈຳກັດທີ່ອະນຸຍາດ.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ຜິດພາດ ModLauncher services: ເສັ້ນທາງ (path) ມີຕົວອັກສອນທີ່ບໍ່ຖືກຕ້ອງ. "
				+ "ModLauncher services ບໍ່ສາມາດປະມວນຜົນເສັ້ນທາງທີ່ມີຕົວອັກສອນທີ່ບໍ່ແມ່ນ ASCII ຫຼື ຕົວອັກສອນພິເສດ. "
				+ "ຕົວອັກສອນທີ່ມີບັນຫາລວມມີ: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요, ແລະ ໂດຍສະເພາະຕົວອັກສອນ '\"' ເມື່ອຢູ່ທ້າຍຊື່. "
				+ "Components ຂອງ services ທົ່ວໄປໃນ ModLauncher ລວມມີ CrashDetector, "
				+ Config.obtenerInstancia().obtenerNombreCD()
				+ ", FeatureCreep, Vivicraft, Optifine, Sodium, clones, Iris Shaders/Oculus, MixerLogger, CrashAssistant ແລະ Sinytra Connector. "
				+ "ເຈົ້າສາມາດ ລົບ services ທັງໝົດໄດ້, ແຕ່ບັນຫາອື່ນໆອາດເກີດຂຶ້ນເນື່ອງຈາກຊື່ຂອງເສັ້ນທາງ. "
				+ "ວິທີແກ້ໄຂ: ປ່ຽນຊື່ instance ໃຫ້ໃຊ້ພຽງແຕ່ຕົວອັກສອນ ASCII (a-z, A-Z, 0-9), ບໍ່ມີຊ່ອງວ່າງ ຫຼື ຕົວອັກສອນພິເສດ.</b>";
	}// TODO incluye un Buscardor para mods con servicios

	@Override
	public String nombre_error_modlauncher_path() {
		return "ຜິດພາດເສັ້ນທາງໃນ ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "ຜິດພາດນີ້ເກີດຂຶ້ນເມື່ອເສັ້ນທາງຂອງ instance ມີຕົວອັກສອນທີ່ບໍ່ແມ່ນ ASCII ຫຼື ຕົວອັກສອນພິເສດ. "
				+ "ModLauncher services ບໍ່ສາມາດຈັດການກັບເສັ້ນທາງເຫຼົ່ານີ້ໄດ້. "
				+ "ວິທີແກ້ໄຂ: ປ່ຽນຊື່ instance ໃຫ້ໃຊ້ພຽງແຕ່ຕົວອັກສອນ ASCII (a-z, A-Z, 0-9) ແລະ ຫຼີກລ່ຽງຊ່ອງວ່າງ ແລະ ຕົວອັກສອນພິເສດ. "
				+ "ໃຫ້ຄວາມສຳຄັນພິເສດກັບຕົວອັກສອນ '\"' ທີ່ມີບັນຫາຫຼາຍ, ໂດຍສະເພາະເມື່ອຢູ່ທ້າຍຊື່.";
	}

	@Override
	public String tituloEditorCodice() {
		return "Code Editor";
	}

	@Override
	public String nuevo() {
		return "ໃໝ່";
	}

	@Override
	public String actualizarSeleccionado() {
		return "ອັບເດດ ທີ່ເລືອກ";
	}

	@Override
	public String eliminarSeleccionado() {
		return "ລຶບ ທີ່ເລືອກ";
	}

	@Override
	public String exportarJSON() {
		return "Export JSON...";
	}

	@Override
	public String guardarTodo() {
		return "ບັນທຶກທັງໝົດ";
	}

	@Override
	public String general() {
		return "ທົ່ວໄປ";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "ຂໍ້ຄວາມທີ່ຕ້ອງການຄົ້ນຫາ";
	}

	@Override
	public String filtro() {
		return "ຕົວກອງ (id)";
	}

	@Override
	public String criticalidad() {
		return "ລະດັບຄວາມຮ້າຍແຮງ (ADVERTENCIA/ERROR/FATAL)";
	}

	@Override
	public String prioridad() {
		return "ຄວາມສຳຄັນ";
	}

	@Override
	public String lista() {
		return "ການກວດສອບ";
	}

	@Override
	public String colIdioma() {
		return "ພາສາ";
	}

	@Override
	public String colNombre() {
		return "ຊື່";
	}

	@Override
	public String colResultado() {
		return "ຜົນລັບ";
	}

	@Override
	public String vistaJson() {
		return "ຕົວຢ່າງ JSON";
	}

	@Override
	public String idiomas() {
		return "ພາສາ (ຈຳເປັນທັງໝົດ)";
	}

	@Override
	public String elegirFiltro() {
		return "ເລືອກ...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "ກະລຸນາເລືອກຕົວກອງ";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "ຕົວກອງທີ່ມີຢູ່";
	}

	@Override
	public String faltanCampos() {
		return "ກະລຸນາຕື່ມຂໍ້ມູນໃຫ້ຄົບຖ້ວນໃນຊ່ອງທົ່ວໄປທີ່ຈຳເປັນ.";
	}

	@Override
	public String critInvalida() {
		return "ລະດັບຄວາມຮ້າຍແຮງບໍ່ຖືກຕ້ອງ. ກະລຸນາໃຊ້ ADVERTENCIA, ERROR ຫຼື FATAL.";
	}

	@Override
	public String filtroNoExiste() {
		return "ຕົວກອງທີ່ລະບຸໄວ້ບໍ່ມີຢູ່.";
	}

	@Override
	public String faltanIdiomas() {
		return "ກະລຸນາຕື່ມຊື່ ແລະ ຜົນລັບສຳລັບທຸກພາສາ:";
	}

	@Override
	public String verificacionInvalida() {
		return "ການກວດສອບບໍ່ຖືກຕ້ອງ. ກະລຸນາກວດສອບຂໍ້ມູນ.";
	}

	@Override
	public String guardadoOk() {
		return "ບັນທຶກສຳເລັດ.";
	}

	@Override
	public String editorCodiceBoton() {
		return "ເພີ່ມເຫດຜົນ";
	}

	@Override
	public String descripcionEditorCodice() {
		return "ເຈົ້າສາມາດລົງທະບຽນເຫດຜົນທີ່ນີ້. ເຈົ້າຕ້ອງການ ID, string ທີ່ບໍ່ມີຕົວອັກສອນພິເສດ, ເຄື່ອງໝາຍວັນນະຍຸດ ຫຼື ຊ່ອງວ່າງ. ສຳລັບຕົວກອງ, ເຈົ້າສາມາດໃຊ້ \"linea containe\" ເພື່ອຄົ້ນຫາ string ໃນແຖວ, \"todo containe\" ຖ້າ log ມີ string, \"regex linea\" ຖ້າແຖວມີ regex, ແລະ \"regex todos\" (ແນະນຳໃຫ້ໃຊ້ເວີຊັນແຖວ). ເຈົ້າຕ້ອງກຳນົດລະດັບຄວາມຮ້າຍແຮງ, FATAL, ERROR, ຫຼື ADVERTENCIA ສຳລັບສີ. ສຳລັບທຸກພາສາ, ເຈົ້າຕ້ອງຂຽນຊື່ ແລະ ຄຳຕອບສຳລັບການສະແດງຜົນ. ເຈົ້າສາມາດເພີ່ມການກວດສອບ ຫຼື ລົບ ອັນອື່ນໆ. ບັນທຶກເມື່ອຕື່ມຂໍ້ມູນຄົບ.";
	}

	@Override
	public String descartarCambios() {
		return "ຕ້ອງການຖິ້ມການປ່ຽນແປງທີ່ບໍ່ໄດ້ບັນທຶກໃນການກວດສອບປັດຈຸບັນບໍ່?";
	}

	@Override
	public String confirmacion() {
		return "ຢືນຢັນ";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "ຕ້ອງການບັນທຶກການປ່ຽນແປງກ່ອນອອກບໍ່?";
	}

	@Override
	public String salirSinGuardar() {
		return "ອອກໂດຍບໍ່ບັນທຶກ";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("ຜິດພາດຮ້າຍແຮງ: ລົ້ມເຫຼວໃນການໂຫຼດ modlauncher service (IDependencyLocator).<br>");
		sb.append("🔹 <b>Class ທີ່ມີບັນຫາ:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>ໂມດທີ່ໄດ້ຮັບຜົນກະທົບ:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append(
					"🔸 <b>ບໍ່ສາມາດລະບຸໂມດໄດ້.</b> ກວດສອບໂມດທີ່ຕິດຕັ້ງໃໝ່ໆ, ໂມດພັດທະນາ ຫຼື ໂມດທີ່ບັນຈຸຜິດປົກກະຕິ.<br>");
		}

		sb.append("🔸 <b>ສາເຫດ:</b> ໄຟລ໌ <code>META-INF/services/...</code> ຂອງໂມດເສຍຫາຍ, ");
		sb.append("ບໍ່ເຂົ້າກັນກັບເວີຊັນ Forge/NeoForge ນີ້, ຫຼື ໂມດນີ້ສຳລັບເວີຊັນທີ່ບໍ່ຖືກຕ້ອງ.<br>");
		sb.append("🔸 <b>ຜົນກະທົບ:</b> Forge/NeoForge ບໍ່ສາມາດລົງທະບຽນ dependencies ຂອງໂມດໄດ້, ");
		sb.append("ເຮັດໃຫ້ບໍ່ສາມາດເລີ່ມເກມໄດ້.<br>");
		sb.append("🔸 <b>ວິທີແກ້ໄຂ:</b> ອັບເດດ, ຕິດຕັ້ງໃໝ່ ຫຼື ລຶບໂມດທີ່ມີບັນຫາ. ");
		sb.append("ຖ້າເຈົ້າໃຊ້ໂມດພັດທະນາ, ໃຫ້ແນ່ໃຈວ່າມັນຖືກ compile ສຳລັບເວີຊັນ Forge/NeoForge ທີ່ແນ່ນອນຂອງເຈົ້າ.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "ຜິດພາດການຕັ້ງຄ່າ Service (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. ລະບຸໂມດທີ່ເປັນສາເຫດ: ກວດສອບໂມດທີ່ຕິດຕັ້ງໃໝ່ໆ ຫຼື ໂມດພັດທະນາ.";
		}
		return "1. ໂມດທີ່ມີບັນຫາແມ່ນ: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. ອັບເດດ, ຕິດຕັ້ງໃໝ່ ຫຼື ລຶບໂມດ. ໃຫ້ແນ່ໃຈວ່າໃຊ້ເວີຊັນທີ່ເຂົ້າກັນໄດ້ກັບ Forge/NeoForge ຂອງເຈົ້າ.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888"; // Gris neutro para nombres de clase/campo

		return "<b style='color:#" + colorError + "'>ຜິດພາດຮ້າຍແຮງ: ບໍ່ພົບ Field.</b><br>"
				+ "ໂມດພະຍາຍາມເຂົ້າເຖິງ field <b style='color:#" + colorCodigo + "'>" + campo + "</b>, "
				+ "ເຊິ່ງບໍ່ມີຢູ່ໃນເວີຊັນເກມນີ້ ຫຼື ໃນໂມດອື່ນ.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "ບໍ່ພົບ Field (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. ຜິດພາດນີ້ມັກເກີດຂຶ້ນເມື່ອໂມດບໍ່ເຂົ້າກັນກັບເວີຊັນປັດຈຸບັນຂອງເກມ ຫຼື ໂມດອື່ນ.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. ອັບເດດໂມດທັງໝົດທີ່ໄດ້ຮັບຜົນກະທົບ. ຖ້າບັນຫາຍັງຄົງຢູ່, ໃຫ້ຕິດຕໍ່ຜູ້ຂຽນໂມດທີ່ເປັນສາເຫດຂອງຜິດພາດ.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888"; // Gris para metodos/clases

		return "<b style='color:#" + colorError + "'>ຜິດພາດຮ້າຍແຮງ: ບໍ່ພົບ Method.</b><br>"
				+ "ໂມດພະຍາຍາມເອີ້ນໃຊ້ method <b style='color:#" + colorCodigo + "'>" + metodo + "</b>, "
				+ "ເຊິ່ງບໍ່ມີຢູ່ໃນເວີຊັນເກມນີ້ ຫຼື ໃນໂມດອື່ນ.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "ບໍ່ພົບ Method (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. ຜິດພາດນີ້ເກີດຂຶ້ນເມື່ອໂມດບໍ່ເຂົ້າກັນກັບເວີຊັນປັດຈຸບັນຂອງເກມ ຫຼື ໂມດອື່ນ.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. ອັບເດດໂມດທັງໝົດທີ່ກ່ຽວຂ້ອງ. ຖ້າບັນຫາຍັງຄົງຢູ່, ໃຫ້ລາຍງານຜິດພາດໃຫ້ຜູ້ຂຽນໂມດທີ່ໄດ້ຮັບຜົນກະທົບ.";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();

		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>ຕ້ອງການຄວາມຊ່ວຍເຫຼືອບໍ່?</strong><br>"
				+ "  ຖ້າເຈົ້າບໍ່ຮູ້ວິທີແກ້ໄຂ ຫຼື ສາເຫດບໍ່ມີຢູ່ທີ່ນີ້, ເຈົ້າສາມາດຮັບຄວາມຊ່ວຍເຫຼືອໃນຊ່ອງທາງ social media ຂອງເຮົາ. "
				+ "  ໃຊ້ປຸ່ມ <img src='" + iconoCompartir
				+ "' alt='Compartir' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>ແບ່ງປັນ</strong> ເພື່ອຮັບ links ຂອງ logs ແລະ ຜົນລັບສຳລັບທີມງານຂອງເຮົາ. "
				+ "  ຖ້າເຈົ້າເປັນຜູ້ສ້າງ modpack ຫຼື ບໍລິສັດ, ໃຫ້ແກ້ໄຂ <code>crash_detector/plantilla.htm</code> "
				+ "  ເພື່ອປັບແຕ່ງ links ຂອງທີມງານເຈົ້າ." + "</div>";
	}

	@Override
	public String restablecerPlantilla() {
		return "Reset Template";
	}

	@Override
	public String restablecer() {
		return "Reset";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		return "ຕ້ອງການ Reset " + nombreImagen + " ກັບຄ່າເລີ່ມຕົ້ນບໍ່?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		return "ຕ້ອງການ Reset template ກັບຄ່າເລີ່ມຕົ້ນບໍ່?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ຂາດ classes ຂອງ AzureLib. ຖ້າເຈົ້າມີ AzureLib ຢູ່ແລ້ວ, ກະລຸນາຕິດຕັ້ງເວີຊັນທີ່ເກົ່າກວ່າວັນທີ 8 ຕຸລາ 2025. ມັນເປັນເລື່ອງທຳມະດາ. ຖ້າເຈົ້າບໍ່ມີ AzureLib, ໃຫ້ຕິດຕັ້ງເວີຊັນປັດຈຸບັນ.</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ໂມດ <code>healight</code> ກຳລັງເຮັດໃຫ້ເກີດຜິດພາດຮ້າຍແຮງ: <code>java.lang.NoSuchFieldError: INT</code>. "
				+ "ຜິດພາດນີ້ເກີດຂຶ້ນເພາະໂມດພະຍາຍາມເຂົ້າເຖິງ field ທີ່ບໍ່ມີຢູ່ແລ້ວໃນເວີຊັນ MCForge 47.10 Minecraft 1.20+. "
				+ "ເກມບໍ່ສາມາດເລີ່ມຕົ້ນໄດ້ເນື່ອງຈາກບັນຫານີ້.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• ລຶບ ຫຼື ອັບເດດໂມດ <code>healight</code>. "
				+ "ເວີຊັນປັດຈຸບັນບໍ່ເຂົ້າກັນກັບ MinecraftForge 47.10 ສຳລັບ 1.20.1. "
				+ "ຊອກຫາເວີຊັນທີ່ໃໝ່ກວ່າຂອງໂມດ ຫຼື ພິຈາລະນາໃຊ້ຕົວແທນອື່ນ.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "ຜິດພາດຮ້າຍແຮງ: healight - ບໍ່ພົບ Field 'INT'";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("Class <code>").append(clase)
				.append("</code> ບໍ່ໄດ້ implement method ທີ່ຕ້ອງການ:<br>").append("<code>").append(metodo)
				.append("</code><br>").append("ຈາກ interface <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br><br>ໂມດ ຫຼື ໄຟລ໌ທີ່ນ่าສົງໄສ: <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• ຜິດພາດນີ້ເກີດຂຶ້ນເມື່ອໂມດ implement interface ແຕ່ລືມ method ທີ່ຈຳເປັນ. "
				+ "ອັບເດດ <b>ທັງສອງໂມດ</b> ທີ່ກ່ຽວຂ້ອງ (ໂມດທີ່ກຳນົດ interface ແລະ ໂມດທີ່ implement ມັນ). "
				+ "ຖ້າເຈົ້າບໍ່ຮູ້ວ່າແມ່ນໂມດໃດ, ໃຫ້ຊອກຫາຊື່ທີ່ປາກົດໃນຂໍ້ຄວາມຜິດພາດ.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "Method ຂອງ Interface ບໍ່ໄດ້ Implement (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ໂມດກຳລັງພະຍາຍາມໂຫຼດ class ຈາກ <b>ຝັ່ງ client</b> "
				+ "(<code>AnimationMetadataSection</code>) ໃນ <b>dedicated server</b>, ເຊິ່ງເປັນໄປບໍ່ໄດ້. "
				+ "ຜິດພາດນີ້ມັກປາກົດເມື່ອໂມດບໍ່ແຍກ code ລະຫວ່າງ client ແລະ server ຢ່າງຖືກຕ້ອງ. "
				+ "ການມີຢູ່ຂອງ <code>ModernFix</code> ອາດຈະເປີດເຜີຍບັນຫານີ້, ແຕ່ບໍ່ແມ່ນສາເຫດໂດຍກົງ.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>ວິທີແກ້ໄຂໄວ:</b> ລຶບ <code>ModernFix</code> ຊົ່ວຄາວເພື່ອຢືນຢັນວ່າ server ເລີ່ມຕົ້ນໄດ້. "
				+ "ຖ້າເຮັດວຽກໄດ້, ບັນຫາຢູ່ທີ່ໂມດອື່ນທີ່ໂຫຼດ classes ຂອງ client ໃນ server.<br>"
				+ "• <b>ວິທີແກ້ໄຂຖາວອນ:</b> ລະບຸໂມດທີ່ເປັນສາເຫດ (ຊອກຫາໂມດທີ່ມີ animated resources, custom textures ຫຼື graphic libraries) ແລ້ວອັບເດດ ຫຼື ລຶບມັນ.<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "Class ຂອງ Client ຖືກໂຫຼດໃນ Server (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ໄຟລ໌ການຕັ້ງຄ່າຂອງໂມດ <code>Sinytra Connector</code> ເສຍຫາຍ. "
				+ "ສິ່ງນີ້ມັກເກີດຂຶ້ນເມື່ອໄຟລ໌ຖືກເຕັມໄປດ້ວຍ null characters (<code>\\u0000</code>) "
				+ "ເນື່ອງຈາກການປິດເກມຢ່າງກະທັນຫັນ, ຜິດພາດໃນການຂຽນ ຫຼື ຄວາມຂັດແຍ່ງຂອງໂມດ.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• ໄປທີ່ໂຟລເດີ <code>config/</code> ຂອງ instance Minecraft ຂອງເຈົ້າ.<br>"
				+ "• ຊອກຫາ ແລະ ລຶບ configs ຂອງໂມດ connector.<br>"
				+ "• Restart ເກມ: Sinytra Connector ຈະສ້າງໄຟລ໌ການຕັ້ງຄ່າໃໝ່ທີ່ສະອາດ.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "ການຕັ້ງຄ່າ Sinytra Connector ເສຍຫາຍ";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "ໄຟລ໌ <code>" + nombreJar
				+ "</code> ເສຍຫາຍ ຫຼື ບໍ່ສົມບູນ.<br>" + "ລະບົບບໍ່ສາມາດອ່ານເນື້ອຫາໄດ້ເພາະຂາດ ZIP end header.<br>"
				+ "ຜິດພາດນີ້ມັກເກີດຂຶ້ນຫຼັງຈາກການດາວໂຫຼດຖືກຂັດຈັງຫວະ ຫຼື launcher ລົ້ມເຫຼວ.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "ໄຟລ໌ JAR ເສຍຫາຍ (ລະບຸຊື່)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>ລຶບໄຟລ໌ທີ່ເສຍຫາຍ</b> ແລ້ວດາວໂຫຼດໃໝ່ຈາກແຫຼ່ງທາງການ (CurseForge, MinecraftStorage, ອື່ນໆ).<br>"
				+ "• ຖ້າເຈົ້າໃຊ້ launcher ເຊັ່ນ CurseForge, Technic ຫຼື Luna Pixel, ພິຈາລະນາປ່ຽນໄປໃຊ້ <b>ATLauncher</b> ຫຼື <b>Prism Launcher</b>, "
				+ "ເຊິ່ງກວດສອບຄວາມຖືກຕ້ອງຂອງໄຟລ໌ໄດ້ດີກວ່າ.<br>"
				+ "• ໃຫ້ແນ່ໃຈວ່າການເຊື່ອມຕໍ່ອິນເຕີເນັດຂອງເຈົ້າໝັ້ນຄົງໃນລະຫວ່າງການດາວໂຫຼດ.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "ບໍ່ສາມາດໂຫຼດໂລກໄດ້ເພາະໄຟລ໌ NBT ຂອງມັນເສຍຫາຍ "
				+ "(ຕົວຢ່າງ: <code>level.dat</code>, <code>playerdata/*.dat</code>, ຫຼື chunks).<br>"
				+ "ຜິດພາດສະເພາະແມ່ນ: <code>UTFDataFormatException: malformed input around byte " + byteCorrupto
				+ "</code>.<br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ ກ່ອນທີ່ຈະພະຍາຍາມແກ້ໄຂ, ໃຫ້ສຳຮອງຂໍ້ມູນໂຟລເດີໂລກທັງໝົດ.</b><br><br>"
				+ "ເຈົ້າສາມາດພະຍາຍາມແກ້ໄຂໄຟລ໌ທີ່ເສຍຫາຍໂດຍໃຊ້ <b>NBT editor</b> ເຊັ່ນ <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>.<br>"
				+ "ຖ້າຄວາມເສຍຫາຍຮ້າຍແຮງ, ໃຊ້ <b>hexadecimal editor</b> (ເຊັ່ນ HxD) ເພື່ອກວດສອບ ແລະ ແກ້ໄຂ bytes ທີ່ບໍ່ຖືກຕ້ອງ "
				+ "(ສະເພາະຖ້າເຈົ້າມີປະສົບການກັບໂຄງສ້າງ NBT).<br>"
				+ "ເປັນວິທີສຸດທ້າຍ, ໃຫ້ກູ້ຄືນຈາກ backup ຫຼື ໃຊ້ <i>world repair</i> ຈາກໂມດເຊັ່ນ <code>FTB Backup</code>.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>ສຳຮອງຂໍ້ມູນໂຟລເດີໂລກທັງໝົດ</b> ກ່ອນທີ່ຈະພະຍາຍາມແກ້ໄຂ.<br>"
				+ "• ໃຊ້ NBT editor (ເຊັ່ນ NBT Studio) ເພື່ອເປີດ ແລະ ແກ້ໄຂໄຟລ໌ທີ່ເສຍຫາຍ.<br>"
				+ "• ຖ້າບໍ່ສຳເລັດ, ໃຫ້ກວດສອບໄຟລ໌ດ້ວຍ hexadecimal editor ທີ່ຕຳແໜ່ງ byte ທີ່ເສຍຫາຍ.<br>"
				+ "• ຖ້າເຈົ້າບໍ່ມີປະສົບການ, ໃຫ້ກູ້ຄືນຈາກ backup ລ່າສຸດ.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "ໂລກເສຍຫາຍ: ຜິດພາດໃນການໂຫຼດຂໍ້ມູນ NBT";
	}

	@Override
	public String problema_con_openAL() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>ເຈົ້າມີບັນຫາກັບ OpenAL. ບາງຄັ້ງ Nouveau drivers ອາດເປັນສາເຫດ, ແຕ່ບາງຄັ້ງເວີຊັນ OpenAL ທີ່ມາກັບແອັບບໍ່ເຂົ້າກັນກັບເວີຊັນໃນ distro ຂອງເຈົ້າ ແລະ ເຈົ້າຕ້ອງໃຊ້ເວີຊັນທີ່ distro ສະໜອງ, ໂດຍສະເພາະພົບເຫັນບ່ອຍໃນ Red Hat distros ແລະ ກັບ sound mods ເຊັ່ນ Sound Physics Remastered. ອ່ານຄູ່ມືນີ້ສຳລັບຄວາມຊ່ວຍເຫຼືອເພີ່ມເຕີມ: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>ວິທີແກ້ໄຂບັນຫາສຽງ Minecraft ໃນ Linux</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Server ບໍ່ສາມາດເລີ່ມຕົ້ນໄດ້ເພາະໄຟລ໌ໂລກຖືກລັອກໂດຍ process ອື່ນ.<br>" + "ສິ່ງນີ້ມັກເກີດຂຶ້ນຖ້າ:<br>"
				+ "• ມີ instance ຂອງ server ກຳລັງເຮັດວຽກຢູ່ແລ້ວ.<br>"
				+ "• Antivirus ຫຼື file explorer ກຳລັງເປີດໂຟລເດີໂລກຢູ່.<br>"
				+ "• Process ກ່ອນໜ້ານີ້ບໍ່ໄດ້ປິດຢ່າງຖືກຕ້ອງ ແລະ ຍັງທິ້ງໄຟລ໌ທີ່ຖືກລັອກໄວ້.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>ປິດທຸກ instances ຂອງ server</b> (ລວມທັງ background processes ເຊັ່ນ javaw.exe).<br>"
				+ "• ຖ້າເຈົ້າໃຊ້ hosting panel (ເຊັ່ນ Multicraft), ໃຫ້ restart server ຢ່າງສົມບູນຈາກ panel.<br>"
				+ "• <b>ປິດ antivirus ຊົ່ວຄາວ</b> ຖ້າສົງໄສວ່າມັນກຳລັງລັອກໄຟລ໌.<br>"
				+ "• ໃນລະບົບທ້ອງຖິ່ນ, ປິດທຸກໜ້າຕ່າງ Windows Explorer ທີ່ສະແດງໂຟລເດີໂລກ.<br>"
				+ "• ຖ້າບັນຫາຍັງຄົງຢູ່, ໃຫ້ລຶບໄຟລ໌ <code>session.lock</code> ພາຍໃນໂຟລເດີໂລກດ້ວຍຕົນເອງ (ສະເພາະຖ້າເຈົ້າແນ່ໃຈວ່າບໍ່ມີ server ອື່ນກຳລັງເຮັດວຽກ).";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "ໄຟລ໌ໂລກຖືກລັອກໂດຍ process ອື່ນ";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "ໂມດພະຍາຍາມ extend class <code>"
				+ clasePadreFinal + "</code>, " + "ແຕ່ class ນີ້ຕອນນີ້ເປັນ <b>final</b> ແລະ ບໍ່ສາມາດ inherit ໄດ້.<br>"
				+ "Class ທີ່ມີບັນຫາແມ່ນ: <code>" + claseHija + "</code>.<br><br>"
				+ "ສິ່ງນີ້ມັກເກີດຂຶ້ນເມື່ອໂມດຖືກ compile ສຳລັບເວີຊັນເກົ່າຂອງ Minecraft ຫຼື mod base ອື່ນ, "
				+ "ເຊິ່ງໄດ້ mark classes ເປັນ <code>final</code> ໃນເວີຊັນລ່າສຸດ.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>ອັບເດດໂມດທັງໝົດທີ່ກ່ຽວຂ້ອງ</b>, ໂດຍສະເພາະໂມດທີ່ອາດກ່ຽວຂ້ອງກັບ mod base ທີ່ກ່າວເຖິງ.<br>"
				+ "• ຖ້າບັນຫາຍັງຄົງຢູ່, ຊອກຫາເວີຊັນຂອງໂມດທີ່ເຂົ້າກັນໄດ້ກັບເວີຊັນ Minecraft ປັດຈຸບັນ ແລະ dependencies ຂອງມັນ.<br>"
				+ "• ໃນບາງກໍລະນີ, ການ ລົບ ໂມດທີ່ມີ class ລູກຊົ່ວຄາວອາດຊ່ວຍຢືນຢັນສາເຫດໄດ້.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "ພະຍາຍາມ inherit ຈາກ class final";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ເຈົ້າກຳລັງໃຊ້ <b>Rubidium</b> (fork ເກົ່າຂອງ Sodium ສຳລັບ Forge) ຮ່ວມກັບ <b>Iris ຫຼື Oculus</b>.<br>"
				+ "ໃນເວີຊັນລ່າສຸດຂອງ Minecraft (1.19.2+), "
				+ "Rubidium ບໍ່ໄດ້ຕາມທັນ Sodium ແລະ dependencies ຂອງມັນມີບັນຫາ.<br><br>"
				+ "ຜິດພາດນີ້ຍັງອາດເກີດຂຶ້ນຖ້າມີຄວາມຂັດແຍ່ງລະຫວ່າງ performance mods (Sodium, Rubidium, Embeddium, Bedium, Xeonium, ອື່ນໆ) ຫຼື Iris Shaders ແລະ ໂມດອື່ນ.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>ລຶບ Rubidium</b> ອອກຈາກໂຟລເດີ <code>mods</code> ຂອງເຈົ້າ.<br>"
				+ "• <b>ຕິດຕັ້ງ <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a></b>, "
				+ "fork ທີ່ active ແລະ ເຂົ້າກັນໄດ້ຂອງ Sodium ສຳລັບ Forge ທີ່ຮອງຮັບ Iris/Oculus ໃນ 1.20+.<br>"
				+ "• ໃຫ້ແນ່ໃຈວ່າເຈົ້າບໍ່ມີ Sodium fork ຫຼາຍກວ່າໜຶ່ງຕິດຕັ້ງຢູ່ພ້ອມກັນ (ຕົວຢ່າງ: Rubidium + Embeddium).<br>"
				+ "• ຖ້າເຈົ້າໃຊ້ Oculus ແທນ Iris, ໃຫ້ກວດສອບວ່າມັນເຂົ້າກັນໄດ້ກັບເວີຊັນ Forge ແລະ Embeddium ຂອງເຈົ້າ.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium ເກົ່າກັບ Iris/Oculus (OptionInstance ເປັນ final)";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ໂມດ <code>Simple Voice Chat</code> ບໍ່ສາມາດເລີ່ມຕົ້ນ voice server ໄດ້ເພາະ "
				+ "UDP port ຖືກໃຊ້ຢູ່ແລ້ວ ຫຼື IP address ທີ່ຕັ້ງຄ່າໄວ້ບໍ່ຖືກຕ້ອງ.<br>"
				+ "ສິ່ງນີ້ບໍ່ຂັດຂວາງການເລີ່ມເກມ, ແຕ່ຈະປິດໃຊ້ງານຟັງຊັນ voice chat.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• <b>ປິດ Minecraft instances ອື່ນໆ</b> ຫຼື ແອັບອື່ນໆທີ່ໃຊ້ UDP port 24454.<br>"
				+ "• ຖ້າເຈົ້າຢູ່ໃນ server, ໃຫ້ແນ່ໃຈວ່າ <b>ບໍ່ມີ service ອື່ນ</b> ໃຊ້ port ນັ້ນ.<br>"
				+ "• ໃນການຕັ້ງຄ່າຂອງໂມດ (<code>config/voicechat/</code>), ປ່ຽນ UDP port ເປັນ port ທີ່ວ່າງ (ຕົວຢ່າງ, 24455).<br>"
				+ "• ຖ້າເຈົ້າໃຊ້ custom IP address, ໃຫ້ກວດສອບວ່າຖືກຕ້ອງ ຫຼື ປ່ອຍວ່າງໄວ້ເພື່ອໃຊ້ຄ່າ default.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Voice Chat: UDP port ຖືກໃຊ້ ຫຼື IP ບໍ່ຖືກຕ້ອງ";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "BlockItem <code>" + nombreBlockItem
				+ "</code> ມີ block ທີ່ເປັນ null.<br>"
				+ "ຜິດພາດນີ້ມັກເກີດຂຶ້ນໃນ <b>Create addons</b> (ເຊັ່ນ <code>dndecor</code>, <code>createdeco</code>) "
				+ "ເມື່ອມີຄວາມຂັດແຍ່ງກັບ <code>Amendments</code>, <code>Moonshine</code>, ຫຼື ການ initialize blocks ຜິດ.<br>"
				+ "<b>ໝາຍເຫດ:</b> ນີ້ບໍ່ແມ່ນຜິດພາດຂອງ Amendments ໂດຍກົງ, ແຕ່ເປັນສັນຍານຂອງບັນຫາທີ່ເລິກກວ່າໃນການໂຫຼດ registry.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• <b>ອັບເດດໂມດທັງໝົດທີ່ກ່ຽວຂ້ອງ:</b> Create, Amendments, Moonshine, ແລະ ໂມດ addon ຕ່າງໆ (ໂດຍສະເພາະ <code>dndecor</code> ແລະ <code>createdeco</code>).<br>"
				+ "• ຖ້າບັນຫາຍັງຄົງຢູ່, <b>ລຶບ Create addons ຊົ່ວຄາວ</b> ທີລະອັນເພື່ອລະບຸຕົວທີ່ເປັນສາເຫດ.<br>"
				+ "• ໃຫ້ແນ່ໃຈວ່າ <b>Amendments ແລະ Moonshine ເຂົ້າກັນໄດ້</b> ກັບເວີຊັນ Create ແລະ Forge ຂອງເຈົ້າ.<br>"
				+ "• ກວດສອບວ່າມີເວີຊັນ beta ຫຼື forks ທີ່ອັບເດດແລ້ວຂອງ addons ທີ່ມີບັນຫາບໍ່.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "BlockItem ເປັນ null ໃນ addon ຂອງ Create";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>")
				.append("ພົບ mods ທີ່ບໍ່ໄດ້ຢູ່ໃນແພລດຟອມທີ່ກຳລັງເຮັດວຽກ (Forge, Fabric, ແລະອື່ນໆ):<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>ສິ່ງນີ້ມັກເກີດຂຶ້ນເມື່ອ:<br>")
				.append("• ປະສົມ mods ຂອງ <b>Fabric ແລະ Forge</b> ໃນໂຟລເດີດຽວກັນ.<br>")
				.append("• ຕິດຕັ້ງ mod ສຳລັບເວີຊັນ Minecraft ທີ່ບໍ່ເຂົ້າກັນ.<br>")
				.append("• Mod ເສຍຫາຍ ຫຼື ບໍ່ແມ່ນໄຟລ໌ JAR ທີ່ຖືກຕ້ອງ.</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>ກວດສອບວ່າ mods ທັງໝົດແມ່ນສຳລັບແພລດຟອມດຽວກັນ</b> (Forge <b>ຫຼື</b> Fabric, ບໍ່ແມ່ນທັງສອງ).<br>"
				+ "• ໃຊ້ <b>ຕົ້ນໄມ້ mods</b> ເພື່ອລະບຸວ່າແພລດຟອມໃດກວດພົບແຕ່ລະໄຟລ໌.<br>"
				+ "• ລຶບ mods ໃດໆທີ່ທ່ານບໍ່ຮູ້ຈັກ ຫຼື ຈາກແພລດຟອມທີ່ແຕກຕ່າງກັນ.<br>"
				+ "• ຖ້າທ່ານໃຊ້ launcher ເຊັ່ນ CurseForge ຫຼື Prism, ໃຫ້ແນ່ໃຈວ່າໂປຣໄຟລ໌ຖືກຕັ້ງຄ່າຢ່າງຖືກຕ້ອງ.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "Mod ບໍ່ເຂົ້າກັນກັບ loader ທີ່ກຳລັງເຮັດວຽກ";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "ບໍ່ສາມາດສ້າງ model <code>" + modid + ":"
				+ nombreModelo + "</code> ໄດ້.<br>" + "ສິ່ງນີ້ຊີ້ບອກວ່າ mod <code>" + modid
				+ "</code> ມີຊັບພະຍາກອນທີ່ເສຍຫາຍ, ຂາດຫາຍໄປ " + "ຫຼື ບໍ່ເຂົ້າກັນກັບເວີຊັນ Minecraft ຂອງທ່ານ.</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>ອັບເດດ mod</b> ເປັນເວີຊັນລ່າສຸດທີ່ເຂົ້າກັນໄດ້ກັບ instance ຂອງທ່ານ.<br>"
				+ "• ຖ້າທ່ານໃຊ້ເວີຊັນພັດທະນາ ຫຼື ປັບແຕ່ງ, ໃຫ້ກັບໄປໃຊ້ເວີຊັນທາງການ.<br>"
				+ "• ກວດສອບວ່າໄຟລ໌ JAR ບໍ່ເສຍຫາຍ (ຕິດຕັ້ງໃໝ່).<br>"
				+ "• ຖ້າບັນຫາຍັງຄົງຢູ່, ໃຫ້ລາຍງານຂໍ້ຜິດພາດນີ້ໃຫ້ຜູ້ຂຽນ mod ພ້ອມກັບ log ນີ້.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "ລົ້ມເຫຼວໃນການສ້າງ model ຊັບພະຍາກອນ";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງທີ່ຮ້າຍແຮງລະຫວ່າງ mods <code>Moonlight</code> ແລະ <code>Iceberg</code>.<br>"
				+ "ທັງສອງພະຍາຍາມລົງທະບຽນລະບົບໂຫຼດຊັບພະຍາກອນໃໝ່ແບບບໍ່ເຂົ້າກັນ, "
				+ "ເຊິ່ງເຮັດໃຫ້ເກີດຂໍ້ຜິດພາດ OpenGL ເນື່ອງຈາກບໍ່ມີບໍລິບົດກຣາຟິກທີ່ຖືກຕ້ອງ.<br>"
				+ "ບັນຫານີ້ພົບເລື້ອຍເມື່ອໃຊ້ເວີຊັນ Forge ທີ່ມີ adapters ຂອງ mods Fabric.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>ອັບເດດທັງສອງ mods</b> ເປັນເວີຊັນລ່າສຸດທີ່ເຂົ້າກັນໄດ້ກັບເວີຊັນ Forge ຂອງທ່ານ.<br>"
				+ "• ຖ້າບັນຫາຍັງຄົງຢູ່, ໃຫ້ <b>ລຶບ Iceberg ຊົ່ວຄາວ</b>, ເນື່ອງຈາກ Moonlight ມັກຈະເປັນ dependency ທີ່ສຳຄັນກວ່າສຳລັບ mods ອື່ນໆ.<br>"
				+ "• ໃຫ້ແນ່ໃຈວ່າທ່ານບໍ່ມີເວີຊັນຊ້ຳ ຫຼື ປະສົມ Forge/Fabric ຂອງ mods ເຫຼົ່ານີ້.<br>"
				+ "• ກວດສອບວ່າມີ mod ອື່ນໆ (ເຊັ່ນ Supplementaries, Citadel, ແລະອື່ນໆ) ທີ່ມີຟັງຊັນຂອງ Iceberg ຢູ່ແລ້ວຫຼືບໍ່.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "ຂໍ້ຂັດແຍ່ງຮ້າຍແຮງ: Moonlight ທຽບກັບ Iceberg (OpenGL ບໍ່ມີບໍລິບົດ)";
	}

	@Override
	public String instantanea() {
		return "ສະແນັບຊອດ";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "ຕັ້ງແຕ່ສະແນັບຊອດລ່າສຸດ";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "ເລືອກໄຟລ໌";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "ສ້າງສະແນັບຊອດສຳເລັດ";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "ຜິດພາດໃນການສ້າງສະແນັບຊອດ";
	}

	@Override
	public String consejo() {
		return "ຄຳແນະນຳ";
	}

	@Override
	public String resultadoMuestra() {
		return "ຜົນລັບຕົວຢ່າງ";
	}

	@Override
	public String historaDeModsDesc() {
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>ຄຳແນະນຳ:</b> ເລືອກສອງໄຟລ໌ປະຫວັດເພື່ອປຽບທຽບລາຍການ mods. "
				+ "  ຜົນລັບຈະສະແດງ <span style='color:%s;'>ທີ່ເພີ່ມ (+)</span> ແລະ "
				+ "  <span style='color:%s;'>ທີ່ຖືກລຶບ (&#8722;)</span> ໂດຍອີງໃສ່ຊື່ທີ່ຖືກປັບມາດຕະຖານ. "
				+ "  ໃຊ້ປຸ່ມ 'ສະແນັບຊອດ' ເພື່ອສ້າງສຳເນົາຂອງໄຟລ໌ທີ່ມີຢູ່ດ້ວຍນາມສະກຸນ .instantanea." + "</body>"
				+ "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		return "ຮັບລິ້ງໄປຍັງບັນທຶກເປັນ Markdown ໂດຍບໍ່ມີລາຍງານ";
	}

	@Override
	public String titulo_configuracion() {
		return "ການຕັ້ງຄ່າ";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "ເກີດຂໍ້ຜິດພາດທີ່ບໍ່ຄາດຄິດໃນການແບ່ງປັນ.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "ເກີດຂໍ້ຜິດພາດທີ່ບໍ່ຄາດຄິດໃນການສ້າງລິ້ງ.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "ເກີດຂໍ້ຜິດພາດທີ່ບໍ່ຄາດຄິດໃນການປະມວນຜົນປຸ່ມ.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "ບໍ່ມີໄຟລ໌ທີ່ກ່ຽວຂ້ອງເພື່ອເປີດ.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "ໄຟລ໌ບໍ່ມີຢູ່:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "ບໍ່ສາມາດເປີດໃນໂປຣແກຣມແກ້ໄຂໄດ້.\nຈະຄັດລອກເສັ້ນທາງໄປຍັງ clipboard.";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "ບໍ່ສາມາດເປີດໄຟລ໌ໄດ້; ເສັ້ນທາງຖືກຄັດລອກໄປຍັງ clipboard ແລ້ວ.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "Desktop ບໍ່ໄດ້ຮັບການສະໜັບສະໜູນ; ເສັ້ນທາງຖືກຄັດລອກໄປຍັງ clipboard ແລ້ວ.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "ທ່ານກຳລັງພົບກັບຂີດຈຳກັດຂອງຄຳຮ້ອງຂໍ. ລອງໃຊ້ເວັບໄຊต์ບັນທຶກອື່ນ ຫຼື API ບັນທຶກອື່ນ.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		return "ແບ່ງປັນລິ້ງ";
	}

	@Override
	public String infoDeTrazos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ການແກ້ໄຂສ່ວນເບື້ອງຕົ້ນຂອງ stack traces ແມ່ນຄວາມສຳຄັນອັນດັບໜຶ່ງ. " + "ຮູບແບບແມ່ນ Level, Line. "
				+ "ບັນທຶກທັງໝົດມີລະບົບການໃຫ້ເລກ. " + Verificaciones.nl_html
				+ "ໂດຍທົ່ວໄປທ່ານຕ້ອງຊອກຫາໃນ levels ທີ່ຕ່ຳກວ່າໃນບັນທຶກທັງໝົດ; traces ທີ່ມີ levels ສູງມັກຈະເປັນ false positives. "
				+ "ມັນສຳຄັນທີ່ຈະໃຊ້ທັກສະຂອງທ່ານໃນການເບິ່ງໃນ console, ເນື່ອງຈາກການວິເຄາະ traces ບໍ່ສົມບູນແບບເມື່ອມີ traces ຫຼາຍ."
				+ "</b>";
	}

	// --- Buscador de Canario de Orden (Warrant Canary) ---

	/**
	 * Texto del botón para el buscador de canario de orden. Ejemplo: "Buscador de
	 * canario de orden"
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "ຕົວຊອກຫາ Canary ຂອງ Order";
	}

	/**
	 * Mensaje mostrado en el cuadro de diálogo informando que la función estará
	 * disponible próximamente.
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "ຟັງຊັນນີ້ຈະມີໃຫ້ໃຊ້ໃນໄວໆນີ້.";
	}

	/**
	 * Título del cuadro de diálogo que informa sobre la disponibilidad futura del
	 * buscador de canario de orden.
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "ໄວໆນີ້";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		return "Mods ທີ່ບໍ່ເຂົ້າກັນກັບ Crash Assistant (ຜິດ)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		return "Mod ທີ່ບໍ່ເຂົ້າກັນກັບ Modpack ໂດຍໃຊ້ CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant ມີລາຍການ mods ທີ່ມັນບອກວ່າບໍ່ເຂົ້າກັນ, ແຕ່ເຮົາບໍ່ມີຫຼັກຖານວ່າເປັນແນວນັ້ນ ແລະ ຂໍ້ຜິດພາດມີພຽງແຕ່ພາສາອັງກິດ. ຖ້າທ່ານຕ້ອງການຫຼິ້ນກັບ mods ເຫຼົ່ານີ້, ທ່ານສາມາດແກ້ໄຂໄຟລ໌ <code>config/crash_assistant/config.toml</code> ແລະ ປ່ຽນ <code>enabled = true</code> ໃນສ່ວນ [compatibility] ເປັນ <code>enabled = false</code>.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant ສາມາດໝາຍ mods ວ່າບໍ່ເຂົ້າກັນໄດ້, ແຕ່ບາງຄັ້ງສິ່ງນີ້ບໍ່ຖືກຕ້ອງ ແລະ ຂໍ້ຄວາມຜິດພາດມີພຽງແຕ່ພາສາອັງກິດ. ຖ້າທ່ານຕ້ອງການຫຼິ້ນກັບ mods ເຫຼົ່ານີ້, ທ່ານສາມາດແກ້ໄຂໄຟລ໌ <code>config/crash_assistant/problematic_mods_config.json</code> ແລະ ປ່ຽນ <code>should_crash_on_startup</code> ຈາກ <code>true</code> ເປັນ <code>false</code>.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "ຜິດພາດ: Mod '" + modId + "' ຕ້ອງການ mod '"
				+ dependencia + "'. ປະຈຸບັນ, " + actual + "." + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "ຜິດພາດ: Mod '" + modId
				+ "' ຕ້ອງການເວີຊັນ '" + requerido + "' ຫຼື ສູງກວ່າຂອງ '" + dependencia
				+ "', ແຕ່ mod ນີ້ບໍ່ໄດ້ຖືກຕິດຕັ້ງ." + "</span>";
	}

	// En la clase MonitorDePID.idioma (añadir este método)
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "ຜິດພາດ: Mod '" + modId
				+ "' ບໍ່ເຂົ້າກັນກັບເວີຊັນປະຈຸບັນຂອງ '" + dependencia + "'. "
				+ "ທ່ານຕ້ອງ ລົບ mod 'Iris/Oculus & GeckoLib Compat' ເນື່ອງຈາກມັນບໍ່ເຂົ້າກັນກັບ Superb Warfare ແລະ ບໍ່ເຮັດວຽກກັບເວີຊັນລ່າສຸດຂອງ GeckoLib. "
				+ "ເວີຊັນປະຈຸບັນ: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "ຜິດພາດ: ບໍ່ສາມາດດຳເນີນການ task ສຳລັບ class '" + clase + "'. "
				+ "ຂໍ້ຜິດພາດນີ້ພົບເລື້ອຍກັບ mods ທີ່ບໍ່ເຂົ້າກັນ ຫຼື ມີຂໍ້ຂັດແຍ່ງກັບ mods ອື່ນໆທີ່ຕິດຕັ້ງໄວ້.";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "ການລົ້ມເຫຼວໃນການດຳເນີນການ tasks";
	}

	public String recomendacion_fallos_ejecucion() {
		return "ຂໍ້ຜິດພາດປະເພດນີ້ມັກເກີດຂຶ້ນເມື່ອມີຄວາມບໍ່ເຂົ້າກັນລະຫວ່າງ mods. "
				+ "ພົບເລື້ອຍໂດຍສະເພາະກັບ mods ທີ່ບໍ່ເຮັດວຽກຢ່າງຖືກຕ້ອງກັບ ConnectorMod.";
	}

	public String info_clase_problematica() {
		return "Class ທີ່ມີບັນຫາ:";
	}

	public String no_se_encontraron_clases_problema() {
		return "ບໍ່ພົບ classes ສະເພາະທີ່ມີບັນຫາໃນການດຳເນີນການ.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງຮ້າຍແຮງລະຫວ່າງ OptiFine ແລະ Entity Model Features (EMF). "
				+ "Mods ເຫຼົ່ານີ້ບໍ່ເຂົ້າກັນ ແລະ ເຮັດໃຫ້ເກີດຂໍ້ຜິດພາດ injection ທີ່ປ້ອງກັນບໍ່ໃຫ້ເກມເລີ່ມຕົ້ນ." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "ຂໍ້ຂັດແຍ່ງ OptiFine ແລະ Entity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "ຖອນການຕິດຕັ້ງ OptiFine ຫຼື Entity Model Features, ເນື່ອງຈາກພວກມັນບໍ່ເຂົ້າກັນ.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງຮ້າຍແຮງລະຫວ່າງ OptiFine ແລະ Fusion. "
				+ "Mods ເຫຼົ່ານີ້ບໍ່ເຂົ້າກັນ ແລະ ເຮັດໃຫ້ເກີດຂໍ້ຜິດພາດ injection ທີ່ປ້ອງກັນບໍ່ໃຫ້ເກມເລີ່ມຕົ້ນ." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "ຂໍ້ຂັດແຍ່ງ OptiFine ແລະ Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "ຖອນການຕິດຕັ້ງ OptiFine ຫຼື Fusion, ເນື່ອງຈາກພວກມັນບໍ່ເຂົ້າກັນ.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel (ຕ້ອງການໂດຍ Create) ຕ້ອງການ Sodium 0.6.0-beta.2 ຫຼື ສູງກວ່າ. Rubidium ແມ່ນ 0.5.3. "
				+ "ພິຈາລະນາໃຊ້ <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> ເປັນທາງເລືອກ."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "ຂໍ້ຂັດແຍ່ງ Flywheel ແລະ ເວີຊັນ Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "ອັບເດດ Sodium ເປັນ 0.6.0-beta.2 ຫຼື ສູງກວ່າ, ຫຼື ຕິດຕັ້ງ <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> ເປັນທາງເລືອກທີ່ເຂົ້າກັນໄດ້.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງຮ້າຍແຮງລະຫວ່າງ OptiFine ແລະ Epic Fight. "
				+ "Mods ເຫຼົ່ານີ້ບໍ່ເຂົ້າກັນ ແລະ ເຮັດໃຫ້ເກີດຂໍ້ຜິດພາດ injection ທີ່ປ້ອງກັນບໍ່ໃຫ້ເກມເລີ່ມຕົ້ນ." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "ຂໍ້ຂັດແຍ່ງ OptiFine ແລະ Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "ຖອນການຕິດຕັ້ງ OptiFine ຫຼື Epic Fight, ເນື່ອງຈາກພວກມັນບໍ່ເຂົ້າກັນ.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງຮ້າຍແຮງລະຫວ່າງ OptiFine ແລະ Rubidium. "
				+ "Mods ເຫຼົ່ານີ້ບໍ່ເຂົ້າກັນ ແລະ ເຮັດໃຫ້ເກີດຂໍ້ຜິດພາດ injection ທີ່ປ້ອງກັນບໍ່ໃຫ້ເກມເລີ່ມຕົ້ນ." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "ຂໍ້ຂັດແຍ່ງ OptiFine ແລະ Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "ຖອນການຕິດຕັ້ງ OptiFine ຫຼື Rubidium, ເນື່ອງຈາກພວກມັນບໍ່ເຂົ້າກັນ.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam ກຳລັງພະຍາຍາມໂຫຼດໃນ server ທີ່ແຍກຕ່າງຫາກ, ແຕ່ມັນເຂົ້າກັນໄດ້ສະເພາະກັບ client ເທົ່ານັ້ນ. "
				+ "ລຶບ FreeCam ອອກຈາກ server ຫຼື ໃຫ້ແນ່ໃຈວ່າມັນຖືກຕິດຕັ້ງສະເພາະໃນ client." + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam ໃນ server ທີ່ແຍກຕ່າງຫາກ";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "ລຶບ FreeCam ອອກຈາກ server ທີ່ແຍກຕ່າງຫາກ, ເນື່ອງຈາກມັນຄວນຖືກຕິດຕັ້ງສະເພາະໃນ client.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) ກຳລັງພະຍາຍາມໂຫຼດໃນ server ທີ່ແຍກຕ່າງຫາກ, ແຕ່ມັນເຂົ້າກັນໄດ້ສະເພາະກັບ client ເທົ່ານັ້ນ. "
				+ "ລຶບ ETF ອອກຈາກ server ຫຼື ໃຫ້ແນ່ໃຈວ່າມັນຖືກຕິດຕັ້ງສະເພາະໃນ client." + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features ໃນ server ທີ່ແຍກຕ່າງຫາກ";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "ລຶບ Entity Texture Features ອອກຈາກ server ທີ່ແຍກຕ່າງຫາກ, ເນື່ອງຈາກມັນຄວນຖືກຕິດຕັ້ງສະເພາະໃນ client.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ທ່ານຕ້ອງຍອມຮັບ EULA ຂອງ Minecraft ເພື່ອດຳເນີນການ server. "
				+ "ແກ້ໄຂໄຟລ໌ eula.txt ແລະ ປ່ຽນ 'eula=false' ເປັນ 'eula=true'." + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "ບໍ່ໄດ້ຍອມຮັບ EULA ຂອງ Minecraft";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "ແກ້ໄຂໄຟລ໌ eula.txt ໃນໂຟລເດີຂອງ server ແລະ ປ່ຽນ 'eula=false' ເປັນ 'eula=true'.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine ກຳລັງພະຍາຍາມໂຫຼດໃນ server ທີ່ແຍກຕ່າງຫາກ, ແຕ່ມັນເຂົ້າກັນໄດ້ສະເພາະກັບ client ເທົ່ານັ້ນ. "
				+ "ລຶບ OptiFine ອອກຈາກ server ຫຼື ໃຫ້ແນ່ໃຈວ່າມັນຖືກຕິດຕັ້ງສະເພາະໃນ client." + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine ໃນ server ທີ່ແຍກຕ່າງຫາກ";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "ລຶບ OptiFine ອອກຈາກ server ທີ່ແຍກຕ່າງຫາກ, ເນື່ອງຈາກມັນຄວນຖືກຕິດຕັ້ງສະເພາະໃນ client. ບັນຫານີ້ຍັງມັກເກີດຈາກການຕິດຕັ້ງ OptiFine ສຳລັບເວີຊັນ Minecraft ທີ່ຜິດ ຫຼື ມີຂໍ້ຂັດແຍ່ງກັບ mod ອື່ນ ແລະ OptiFine.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks ຖືກໝາຍຜິດວ່າເປັນ 1.20.1 ແຕ່ໃຊ້ methods ຂອງ 1.21.1. "
				+ "Mod ກຳລັງພະຍາຍາມໃຊ້ ResourceLocation.fromNamespaceAndPath, ເຊິ່ງບໍ່ມີຢູ່ໃນ 1.20.1." + "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "ຜິດພາດເວີຊັນໃນ Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "ໃຫ້ແນ່ໃຈວ່າທ່ານໃຊ້ເວີຊັນທີ່ຖືກຕ້ອງຂອງ Iron's Spellbooks ທີ່ເຂົ້າກັນໄດ້ກັບເວີຊັນ Minecraft ຂອງທ່ານ.";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງຮ້າຍແຮງລະຫວ່າງ OptiFine ແລະ Embeddium. "
				+ "Mods ເຫຼົ່ານີ້ບໍ່ເຂົ້າກັນ ແລະ ເຮັດໃຫ້ເກີດຂໍ້ຜິດພາດ injection ທີ່ປ້ອງກັນບໍ່ໃຫ້ເກມເລີ່ມຕົ້ນ." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "ຂໍ້ຂັດແຍ່ງ OptiFine ແລະ Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "ຖອນການຕິດຕັ້ງ OptiFine ຫຼື Embeddium, ເນື່ອງຈາກພວກມັນບໍ່ເຂົ້າກັນ.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>ມັນເປັນເລື່ອງທຳມະດາກັບ mods ສ້າງໂລກທີ່ມີຂໍ້ຂັດແຍ່ງ, ໂດຍສະເພາະ Terralinth, AmplifiedNether, Nullscape ແລະ Incendium, ແລະ mods ສ້າງໂລກອື່ນໆ. ມັນກໍ່ເປັນໄປໄດ້ວ່າທ່ານອາດຈະຕ້ອງຕິດຕັ້ງ mod ທີ່ຂາດຫາຍໄປ.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable ກຳລັງພະຍາຍາມໂຫຼດໃນ server ທີ່ແຍກຕ່າງຫາກ, ແຕ່ມັນເຂົ້າກັນໄດ້ສະເພາະກັບ client ເທົ່ານັ້ນ. "
				+ "ລຶບ Controllable ອອກຈາກ server ຫຼື ໃຫ້ແນ່ໃຈວ່າມັນຖືກຕິດຕັ້ງສະເພາະໃນ client." + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable ໃນ server ທີ່ແຍກຕ່າງຫາກ";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "ລຶບ Controllable ອອກຈາກ server ທີ່ແຍກຕ່າງຫາກ, ເນື່ອງຈາກມັນຄວນຖືກຕິດຕັ້ງສະເພາະໃນ client.";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries ກຳລັງເຮັດໃຫ້ເກີດ ຜິດພາດ ທີ່ປ້ອງກັນບໍ່ໃຫ້ server ໂຫຼດ. "
				+ "Mod ນີ້ມີບັນຫາກັບການລົງທະບຽນພຶດຕິກຳຂອງໄຟ ທີ່ເຮັດໃຫ້ເກີດຂໍ້ຜິດພາດໃນລະຫວ່າງການໂຫຼດ datapacks."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries ປ້ອງກັນບໍ່ໃຫ້ server ໂຫຼດ";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "ລອງອັບເດດ Supplementaries ເປັນເວີຊັນລ່າສຸດ ຫຼື ປິດການເຮັດວຽກຊົ່ວຄາວເພື່ອໃຫ້ server ສາມາດໂຫຼດໄດ້.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) ພົບບັນຫາກັບ modules Jackson ທີ່ຂາດຫາຍໄປ. "
				+ "ບາງ mods ເຊັ່ນ Valkyrien Skies ອາດຈະເຮັດໃຫ້ເກີດ ຜິດພາດ ນີ້ໂດຍບໍ່ລວມ dependencies ທີ່ຈຳເປັນທັງໝົດ."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "Module Jackson ຂາດຫາຍໄປໃນ Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "ລຶບ Groovy Modloader ແລະ mods ທີ່ກ່ຽວຂ້ອງເຊັ່ນ Valkyrien Skies ທີ່ອາດຈະເຮັດໃຫ້ເກີດຂໍ້ຂັດແຍ່ງຂອງ dependencies.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Every Compat ພົບຊື່ block ໄມ້ທີ່ບໍ່ຖືກຕ້ອງ. "
				+ "Every Compat ມັກຈະມີບັນຫາຫຼາຍ. ຢ່າໃຊ້ມັນ!" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "ຊື່ບໍ່ຖືກຕ້ອງໃນ Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "ກວດສອບ resource packs ຫຼື mods ທີ່ໃຊ້ Every Compat, ເນື່ອງຈາກພວກມັນອາດຈະມີຊື່ blocks ທີ່ບໍ່ຖືກຕ້ອງ.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບລະຫັດ ຜິດພາດ (-1073741819) ທີ່ອາດເກີດຈາກ overlays ເຊັ່ນ GameCaster ຂອງ Razer, Discord, OBS Studio ຫຼື ບັນຫາກັບ drivers ຂອງ NVIDIA."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "ລະຫັດ ຜິດພາດ -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "ລອງປິດ overlays ເຊັ່ນ GameCaster, Discord ຫຼື OBS Studio, ແລະ ກວດສອບວ່າ drivers ຂອງ NVIDIA ຂອງທ່ານຖືກອັບເດດແລ້ວ.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips ຕ້ອງການ Immersive Messages ເປັນ dependency ແຕ່ມັນບໍ່ໄດ້ຖືກຕິດຕັ້ງ. "
				+ "ຕິດຕັ້ງ Immersive Messages ເພື່ອໃຫ້ Immersive Tooltips ເຮັດວຽກໄດ້ຢ່າງຖືກຕ້ອງ." + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips ຂາດ dependency";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "ຕິດຕັ້ງ Immersive Messages, ເນື່ອງຈາກມັນເປັນ dependency ທີ່ຈຳເປັນສຳລັບ Immersive Tooltips.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins ມີບັນຫາຄວາມເຂົ້າກັນໄດ້ກັບ Apoli Mod ເຊິ່ງ ItemStack ບໍ່ສາມາດ cast ເປັນ EntityLinkedItemStack ໄດ້. "
				+ "ສິ່ງນີ້ພົບເລື້ອຍໃນເວີຊັນທີ່ສູງກວ່າ 6.6.0. ພິຈາລະນາໃຊ້ເວີຊັນເກົ່າກວ່າ ຫຼື ປ່ຽນລະຫວ່າງເວີຊັນ Fabric ແລະ Forge."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "ຜິດພາດການ cast ໃນ Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "ໃຊ້ Medieval Origins ເວີຊັນ 6.6.0 ຫຼື ຕ່ຳກວ່າ, ຫຼື ລອງປ່ຽນລະຫວ່າງເວີຊັນ Fabric ແລະ Forge ຂອງ mod.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether ກຳລັງເຮັດໃຫ້ເກີດ ຜິດພາດ ກັບ Registry Object ທີ່ບໍ່ມີຢູ່ໃນ MusicManager. "
				+ "ບັນຫານີ້ກ່ຽວຂ້ອງກັບ mixin ຂອງ MusicManager ໃນ Reign of Nether." + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "ຜິດພາດ MusicManager ໃນ Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "ລອງອັບເດດ Reign of Nether ຫຼື ລົບ ມັນຊົ່ວຄາວເພື່ອແກ້ໄຂ ຜິດພາດ.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel ສະໜັບສະໜູນສະເພາະ server YSM ໃນ Linux ຫຼື Android. "
				+ "ບັນຫານີ້ໄດ້ຖືກແກ້ໄຂແລ້ວໃນເວີຊັນລ່າສຸດຕັ້ງແຕ່ວັນທີ 23 ພະຈິກ 2025 ໃນ Modrinth." + "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel ບໍ່ເຂົ້າກັນກັບ Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "ອັບເດດ YesSteveModel ເປັນເວີຊັນລ່າສຸດຈາກ Modrinth, ເນື່ອງຈາກບັນຫາໄດ້ຖືກແກ້ໄຂແລ້ວຫຼັງຈາກວັນທີ 23 ພະຈິກ.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງຮ້າຍແຮງລະຫວ່າງ Moving Elevators ແລະ OptiFine. "
				+ "Mods ເຫຼົ່ານີ້ບໍ່ເຂົ້າກັນ ແລະ ເຮັດໃຫ້ເກີດຂໍ້ຜິດພາດ injection ທີ່ປ້ອງກັນບໍ່ໃຫ້ເກມເລີ່ມຕົ້ນ." + "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "ຂໍ້ຂັດແຍ່ງ Moving Elevators ແລະ OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "ຖອນການຕິດຕັ້ງ OptiFine ຫຼື Moving Elevators, ເນື່ອງຈາກພວກມັນບໍ່ເຂົ້າກັນ.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງຮ້າຍແຮງລະຫວ່າງ Fabric API (fabric-resource-loader-v0) ແລະ OptiFine. "
				+ "Mods ເຫຼົ່ານີ້ບໍ່ເຂົ້າກັນ ແລະ ເຮັດໃຫ້ເກີດຂໍ້ຜິດພາດ injection ທີ່ປ້ອງກັນບໍ່ໃຫ້ເກມເລີ່ມຕົ້ນ." + "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "ຂໍ້ຂັດແຍ່ງ Fabric API ແລະ OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "ຖອນການຕິດຕັ້ງ OptiFine ຫຼື ອັບເດດ Fabric API ເປັນເວີຊັນທີ່ເຂົ້າກັນໄດ້.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mod ມີ ITransformationService ທີ່ເສຍຫາຍ ທີ່ບໍ່ສາມາດ instantiate ໄດ້: " + claseProveedor + ". "
				+ "Mod ນີ້ຕ້ອງຖືກລຶບອອກເພື່ອໃຫ້ເກມສາມາດໂຫຼດໄດ້." + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "ITransformationService ເສຍຫາຍ";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "ລຶບ mod ທີ່ມີ class " + claseProveedor + ", ເນື່ອງຈາກມັນມີ ITransformationService ທີ່ເສຍຫາຍ.";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError() + "'>Mod ມີ version specification ທີ່ບໍ່ຖືກຕ້ອງ. "
				+ "Version ຕ້ອງຢູ່ໃນວົງເລັບ square brackets. "
				+ "ທ່ານສາມາດໃຊ້ utility grep/greprf ຈາກ panel ດ້ານຂ້າງໂດຍຊອກຫາ version </span>" + version
				+ "<span style='color:#" + config.obtenerColorError() + "'> ເພື່ອລະບຸວ່າ mod ໃດມີບັນຫາ.</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "Version ບໍ່ຖືກຕ້ອງໃນ mod";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "ໃຊ້ utility grep/greprf ຈາກ panel ດ້ານຂ້າງເພື່ອຊອກຫາ version ທີ່ມີບັນຫາ ແລະ ຊອກຫາ mod ທີ່ມັນຢູ່.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ພົບ ຜິດພາດ stack smashing ທີ່ຢຸດ process. "
				+ "ສິ່ງນີ້ອາດເກີດຈາກບັນຫາກັບ Early Window ໃນ Forge/NeoForge/PillowMC ຫຼື ກັບ LWJGL 3.2.2 ແລະ ເວີຊັນໃໝ່ກວ່າ."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "ພົບ Stack Smashing";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "ກວດສອບການຕັ້ງຄ່າ Early Window ແລະ ພິຈາລະນາໃຊ້ເວີຊັນ LWJGL ທີ່ແຕກຕ່າງ ຫຼື ກວດສອບ mods ທີ່ກ່ຽວຂ້ອງກັບ early window.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore ແມ່ນສຳລັບ modpack ສະເພາະເທົ່ານັ້ນ ແລະ ບໍ່ຄວນໃຊ້ໃນການຕິດຕັ້ງທົ່ວໄປ, ເນື່ອງຈາກມັນເຮັດໃຫ້ເກີດບັນຫາ."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore ມີ version Java ທີ່ບໍ່ເຂົ້າກັນ";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "ລຶບ GregTechEasyCore, ເນື່ອງຈາກມັນແມ່ນສຳລັບ modpack ສະເພາະເທົ່ານັ້ນ ແລະ ບໍ່ເຂົ້າກັນກັບການຕິດຕັ້ງທົ່ວໄປຂອງທ່ານ.";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງລະຫວ່າງ MoniLabs ແລະ Connector Extras ທີ່ກ່ຽວຂ້ອງກັບການດັດແປງ KubeJS. "
				+ "Mods ເຫຼົ່ານີ້ບໍ່ເຂົ້າກັນໃນການດັດແປງ KubeJS ຂອງພວກມັນ." + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "ຂໍ້ຂັດແຍ່ງ MoniLabs ແລະ Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "ລອງຖອນການຕິດຕັ້ງ mod ໜຶ່ງໃນສອງຕົວ (MoniLabs ຫຼື Connector Extras) ເນື່ອງຈາກພວກມັນມີຂໍ້ຂັດແຍ່ງກັບການດັດແປງ KubeJS ຂອງພວກມັນ.";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris ຕ້ອງການ Distant Horizons [2.0.4] ຫຼື DH API ເວີຊັນ [1.1.0] ຫຼື ສູງກວ່າ. "
				+ "ເບິ່ງຄູ່ມືຄວາມເຂົ້າກັນໄດ້ທີ່ https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e ເພື່ອແກ້ໄຂບັນຫາ."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "ຄວາມເຂົ້າກັນໄດ້ Iris ແລະ Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "ເບິ່ງຄູ່ມືຄວາມເຂົ້າກັນໄດ້ທີ່ https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e ແລະ ອັບເດດ Iris ແລະ Distant Horizons ເປັນເວີຊັນທີ່ເຂົ້າກັນໄດ້.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ທ່ານມີ classes ຂອງ Minecraft ທີ່ຂາດຫາຍໄປ. ເຫດຜົນທີ່ເປັນໄປໄດ້:</b>" + "<ul>"
				+ "<li>ທ່ານມີ mods ສຳລັບເວີຊັນອື່ນຂອງເກມ. ທ່ານສາມາດໃຊ້ <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> ເພື່ອກວດສອບວ່າ class ນັ້ນມີຢູ່ໃນເວີຊັນຂອງທ່ານຫຼືບໍ່.</li>"
				+ "<li>ທ່ານມີການຕິດຕັ້ງ Minecraft ທີ່ເສຍຫາຍ (ພົບເລື້ອຍກັບ CurseForge App, ModrinthApp/Theseus/Astralrinth ແລະ launchers ອື່ນໆ). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>ເບິ່ງ tutorial</a> ເພື່ອແກ້ໄຂບັນຫາກັບ CurseForge.</li>"
				+ "<li>ທ່ານມີ coremod ທີ່ເສຍຫາຍ (ຖ້າ coremod ລົ້ມເຫຼວ, ມັນອາດຈະປ້ອງກັນບໍ່ໃຫ້ໂຫຼດ class).</li>" + "</ul>"
				+ "<p>ໝາຍເຫດ: ທ່ານສາມາດໃຊ້ເຄື່ອງມື <b>grepr/fgrepr</b> ໃນ sidebar ເພື່ອຊອກຫາ mods ທີ່ອ້າງອີງເຖິງ classes ທີ່ຂາດຫາຍໄປ, ໂດຍໃຊ້ '/' ໃນຊື່.</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ທ່ານມີ classes ຂອງ DangerZone ທີ່ຂາດຫາຍໄປ. ເຫດຜົນທີ່ເປັນໄປໄດ້:</b>" + "<ul>"
				+ "<li>ທ່ານມີ mods ສຳລັບເວີຊັນອື່ນຂອງເກມ.</li>" + "<li>ທ່ານມີ coremods ທີ່ເສຍຫາຍ.</li>"
				+ "<li>ທ່ານມີ launcher ຫຼື ການຕິດຕັ້ງທີ່ເສຍຫາຍ.</li>" + "</ul>"
				+ "<p>ໝາຍເຫດ: ທ່ານສາມາດໃຊ້ເຄື່ອງມື <b>grepr/fgrepr</b> ໃນ sidebar ເພື່ອຊອກຫາ mods ທີ່ອ້າງອີງເຖິງ classes ທີ່ຂາດຫາຍໄປ, ໂດຍໃຊ້ '/' ໃນຊື່.</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ທ່ານມີ classes ຂອງ FeatureCreep ທີ່ຂາດຫາຍໄປ. ເຫດຜົນທີ່ເປັນໄປໄດ້:</b>" + "<ul>"
				+ "<li>ທ່ານມີ mods ສຳລັບເວີຊັນອື່ນຂອງ FeatureCreep (ຕົວຢ່າງ: ESR vs Nightly ຫຼື v4 vs v12).</li>"
				+ "<li>ທ່ານສາມາດຕິດຕັ້ງ FeatureCreep ຈາກ CurseForge ຫຼື MinecraftStorage.</li>" + "</ul>"
				+ "<p>ໝາຍເຫດ: ທ່ານສາມາດໃຊ້ເຄື່ອງມື <b>grepr/fgrepr</b> ໃນ sidebar ເພື່ອຊອກຫາ mods ທີ່ອ້າງອີງເຖິງ classes ທີ່ຂາດຫາຍໄປ, ໂດຍໃຊ້ '/' ໃນຊື່.</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ທ່ານມີ classes ຂອງ ModLauncher ທີ່ຂາດຫາຍໄປ. ເຫດຜົນທີ່ເປັນໄປໄດ້:</b>" + "<ul>"
				+ "<li>Mods ຂອງທ່ານແມ່ນສຳລັບ build ທີ່ແຕກຕ່າງຂອງ MinecraftForge, PillowMC ຫຼື NeoForge (ModLauncher ຖືກໃຊ້ກັບ loaders ເຫຼົ່ານີ້).</li>"
				+ "<li>ມີການອັບເດດ modloaders ຫຼາຍຄັ້ງສຳລັບເວີຊັນ Minecraft ໜຶ່ງໆ.</li>"
				+ "<li>ທ່ານມີການຕິດຕັ້ງ launcher ທີ່ເສຍຫາຍ (ພົບເລື້ອຍກັບ CurseForge App, ModrinthApp/Theseus/Astralrinth ແລະ launchers ອື່ນໆ). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>ເບິ່ງ tutorial</a> ເພື່ອແກ້ໄຂບັນຫາກັບ CurseForge.</li>"
				+ "</ul>"
				+ "<p>ໝາຍເຫດ: ທ່ານສາມາດໃຊ້ເຄື່ອງມື <b>grepr/fgrepr</b> ໃນ sidebar ເພື່ອຊອກຫາ mods ທີ່ອ້າງອີງເຖິງ classes ທີ່ຂາດຫາຍໄປ, ໂດຍໃຊ້ '/' ໃນຊື່.</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ທ່ານມີ classes ຂອງ Minecraft Forge ທີ່ຂາດຫາຍໄປ. ເຫດຜົນທີ່ເປັນໄປໄດ້:</b>" + "<ul>"
				+ "<li>Mods ຂອງທ່ານແມ່ນສຳລັບ build ທີ່ແຕກຕ່າງຂອງ MinecraftForge.</li>"
				+ "<li>ມີການອັບເດດ modloaders ຫຼາຍຄັ້ງສຳລັບເວີຊັນ Minecraft ໜຶ່ງໆ.</li>"
				+ "<li>ທ່ານມີການຕິດຕັ້ງທີ່ເສຍຫາຍ (ພົບເລື້ອຍກັບ CurseForge App, ModrinthApp/Theseus/Astralrinth ແລະ launchers ອື່ນໆ). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>ເບິ່ງ tutorial</a> ເພື່ອແກ້ໄຂບັນຫາກັບ CurseForge.</li>"
				+ "</ul>"
				+ "<p>ໝາຍເຫດ: ທ່ານສາມາດໃຊ້ເຄື່ອງມື <b>grepr/fgrepr</b> ໃນ sidebar ເພື່ອຊອກຫາ mods ທີ່ອ້າງອີງເຖິງ classes ທີ່ຂາດຫາຍໄປ, ໂດຍໃຊ້ '/' ໃນຊື່.</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ທ່ານມີ classes ຂອງ NeoForge ທີ່ຂາດຫາຍໄປ. ເຫດຜົນທີ່ເປັນໄປໄດ້:</b>" + "<ul>"
				+ "<li>Mods ຂອງທ່ານແມ່ນສຳລັບ build ທີ່ແຕກຕ່າງຂອງ NeoForge.</li>"
				+ "<li>ມີການອັບເດດ modloaders ຫຼາຍຄັ້ງສຳລັບເວີຊັນ Minecraft ໜຶ່ງໆ.</li>"
				+ "<li>ທ່ານມີການຕິດຕັ້ງທີ່ເສຍຫາຍ (ພົບເລື້ອຍກັບ CurseForge App, ModrinthApp/Theseus/Astralrinth ແລະ launchers ອື່ນໆ). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>ເບິ່ງ tutorial</a> ເພື່ອແກ້ໄຂບັນຫາກັບ CurseForge.</li>"
				+ "</ul>"
				+ "<p>ໝາຍເຫດ: ທ່ານສາມາດໃຊ້ເຄື່ອງມື <b>grepr/fgrepr</b> ໃນ sidebar ເພື່ອຊອກຫາ mods ທີ່ອ້າງອີງເຖິງ classes ທີ່ຂາດຫາຍໄປ, ໂດຍໃຊ້ '/' ໃນຊື່.</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ທ່ານມີ classes ຂອງ Fabric Loader ທີ່ຂາດຫາຍໄປ. ເຫດຜົນທີ່ເປັນໄປໄດ້:</b>" + "<ul>"
				+ "<li>Mods ຂອງທ່ານແມ່ນສຳລັບ build ທີ່ແຕກຕ່າງຂອງ Fabric Loader.</li>"
				+ "<li>ມີການອັບເດດ modloaders ຫຼາຍຄັ້ງສຳລັບເວີຊັນ Minecraft ໜຶ່ງໆ.</li>"
				+ "<li>ທ່ານມີການຕິດຕັ້ງທີ່ເສຍຫາຍ (ພົບເລື້ອຍກັບ CurseForge App, ModrinthApp/Theseus/Astralrinth ແລະ launchers ອື່ນໆ). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>ເບິ່ງ tutorial</a> ເພື່ອແກ້ໄຂບັນຫາກັບ CurseForge.</li>"
				+ "<li>Mods ຫຼາຍຕົວຕ້ອງການ Fabric API. ກະລຸນາຕິດຕັ້ງ Fabric API ຖ້າຈຳເປັນ.</li>" + "</ul>"
				+ "<p>ໝາຍເຫດ: ທ່ານສາມາດໃຊ້ເຄື່ອງມື <b>grepr/fgrepr</b> ໃນ sidebar ເພື່ອຊອກຫາ mods ທີ່ອ້າງອີງເຖິງ classes ທີ່ຂາດຫາຍໄປ, ໂດຍໃຊ້ '/' ໃນຊື່.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ທ່ານມີ classes ຂອງ PillowMC ທີ່ຂາດຫາຍໄປ. ເຫດຜົນທີ່ເປັນໄປໄດ້:</b>" + "<ul>"
				+ "<li>Mods ຂອງທ່ານແມ່ນສຳລັບ build ທີ່ແຕກຕ່າງຂອງ PillowMC.</li>"
				+ "<li>ມີການອັບເດດ modloaders ຫຼາຍຄັ້ງສຳລັບເວີຊັນ Minecraft ໜຶ່ງໆ.</li>"
				+ "<li>ທ່ານມີການຕິດຕັ້ງທີ່ເສຍຫາຍ (ພົບເລື້ອຍກັບ CurseForge App, ModrinthApp/Theseus/Astralrinth ແລະ launchers ອື່ນໆ). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>ເບິ່ງ tutorial</a> ເພື່ອແກ້ໄຂບັນຫາກັບ CurseForge.</li>"
				+ "</ul>"
				+ "<p>ໝາຍເຫດ: ທ່ານສາມາດໃຊ້ເຄື່ອງມື <b>grepr/fgrepr</b> ໃນ sidebar ເພື່ອຊອກຫາ mods ທີ່ອ້າງອີງເຖິງ classes ທີ່ຂາດຫາຍໄປ, ໂດຍໃຊ້ '/' ໃນຊື່.</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "ທ່ານມີ mod ທີ່ເຮັດໃຫ້ເກີດ lag ໂດຍເຈດຕະນາ. Uranium ແມ່ນ mod ສ້າງ lag. ມັນບໍ່ໄດ້ເຮັດໃຫ້ເກີດ crash ສະເໝີໄປ, ແຕ່ໃນທີ່ສຸດອາດຈະເຮັດ."
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack ຖືກໝາຍວ່າເຂົ້າກັນໄດ້ກັບ 1.19.* ແຕ່ແມ່ນສຳລັບ 1.20.*, ເຊິ່ງເຮັດໃຫ້ເກີດ ຜິດພາດ class not found. "
				+ "Mod ພະຍາຍາມໃຊ້ DamageSources ທີ່ບໍ່ມີຢູ່ໃນເວີຊັນ Minecraft ປະຈຸບັນ." + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "ຜິດພາດເວີຊັນໃນ Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "ໃຫ້ແນ່ໃຈວ່າທ່ານໃຊ້ເວີຊັນທີ່ຖືກຕ້ອງຂອງ Falling Attack ທີ່ເຂົ້າກັນໄດ້ກັບເວີຊັນ Minecraft ຂອງທ່ານ.";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>").append("ທ່ານຕ້ອງຕິດຕັ້ງ CFR (Class File Reader) ເພື່ອໃຊ້ຟັງຊັນນີ້.<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append("ໃນລະບົບ Linux, NetBSD ຫຼື FreeBSD, ທ່ານສາມາດຕິດຕັ້ງ CFR ຈາກ package manager ຂອງທ່ານ.<br>")
					.append("ຊອກຫາ package ທີ່: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append("ຫຼື ທ່ານສາມາດດາວໂຫຼດເວີຊັນທີ່ຖືກດັດແປງໂດຍ FabricMC ຈາກ:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("ບັນທຶກໄວ້ໃນໂຟລເດີຕໍ່ໄປນີ້:<br>").append("<b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>ສຳຄັນ:</b> ຫຼັງຈາກຕິດຕັ້ງ CFR, ທ່ານຕ້ອງ restart mod ເພື່ອໃຫ້ມັນຮັບຮູ້ຢ່າງຖືກຕ້ອງ.")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "ບໍ່ມີຮູບພອດເທຣດ";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "ບໍ່ພົບ class: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return " Decompiler CFR – Sakura Riddle (ບໍ່ເປັນທາງການ)";
	}

	@Override
	public String cfrClaseActual() {
		return "Class ປະຈຸບັນ";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "ຮູບພອດເທຣດຂອງ Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "ຜິດພາດໃນການໂຫຼດຮູບພອດເທຣດ";
	}

	public String noticiaLegalCFR() {
		return "ໂປຣແກຣມ GUI ນີ້ສຳລັບ decompile mods ຖືກອອກແບບມາເພື່ອຊ່ວຍໃຫ້ຜູ້ໃຊ້ລະບຸສາເຫດຂອງຂໍ້ຜິດພາດໃນຊອບແວ. "
				+ "ເຖິງຢ່າງໃດກໍ່ຕາມ, ການ decompile mods ອາດຈະຈຳເປັນ, ແລະ ຜູ້ໃຊ້ຄວນລະມັດລະວັງບໍ່ໃຫ້ໃຊ້ code ທີ່ຖືກສ້າງຂຶ້ນເພື່ອລະເມີດກົດໝາຍລິຂະສິດ. "
				+ "ແນະນຳໃຫ້ກວດສອບໃບອະນຸຍາດ (license) ຂອງ mod ກ່ອນທີ່ຈະໃຊ້ code ໃດໆທີ່ໄດ້ມາ. ນອກຈາກນີ້, ບາງຄັ້ງ mods ຈະໃຫ້ source code ຢ່າງເປັນທາງການ, "
				+ "ເຊິ່ງໂດຍທົ່ວໄປແລ້ວຈະສະອາດ ແລະ ເຂົ້າໃຈງ່າຍກວ່າ code ທີ່ decompile ແລ້ວ. ຈົ່ງຈື່ໄວ້ວ່າ ການເຄົາລົບຊັບສິນທາງປັນຍາ ແລະ ໃບອະນຸຍາດການໃຊ້ງານແມ່ນສິ່ງສຳຄັນສຳລັບ "
				+ "ຊຸມຊົນນັກພັດທະນາ mods. ທ່ານສາມາດອ້າງອີງກົດໝາຍລິຂະສິດແຫ່ງຊາດ (Mexican Federal Copyright Law) ຈາກລິ້ງຕໍ່ໄປນີ້: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Ley Federal de Derechos de Autor (Spanish)</a> "
				+ "ແລະ ເວີຊັນພາສາອັງກິດຢູ່ທີ່ນີ້: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (English)</a>. "
				+ "ເນື່ອງຈາກເຮົາຢູ່ໃນ CurseForge, ເຮົາຍັງໃຫ້ລິ້ງໄປຍັງກົດໝາຍລິຂະສິດຂອງສະຫະລັດອາເມລິກາເປັນພາສາອັງກິດ: "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>. "
				+ "ນອກຈາກນີ້, ແນະນຳໃຫ້ຜູ້ໃຊ້ກວດສອບສະຖານທີ່ຂອງຕົນເອງ ແລະ ສຶກສາກົດໝາຍທີ່ກ່ຽວຂ້ອງໃນພື້ນທີ່ຂອງທ່ານ. "
				+ "GUI ຂອງເຮົາແມ່ນສຳລັບການກວດສອບພື້ນຖານເທົ່ານັ້ນ; ສຳລັບການວິເຄາະຂັ້ນສູງ, ທ່ານຄວນໃຊ້ Enigma Fork ຂອງ FabricMC ທີ່ມີຢູ່ໃນ "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>. ຖ້າທ່ານຕ້ອງການແກ້ໄຂໄຟລ໌ JAR ເພື່ອສ້າງ patch ເມື່ອບໍ່ມີ source code, ທ່ານສາມາດໃຊ້ Recaf ໃນ "
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">ເວັບໄຊต์ຂອງມັນ</a>.";
	}

	@Override
	public String botonDescargarCfr() {
		return "ດາວໂຫຼດ CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "ເປີດໂຟລເດີຕິດຕັ້ງ";
	}

	@Override
	public String colorFondoPrincipal() {
		return "ສີພື້ນຫຼັກ";
	}

	@Override
	public String colorTextoBotonReset() {
		return "ສີຕົວໜັງສືຂອງປຸ່ມ reset";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "ສີຕົວໜັງສືຂອງຊ່ອງຄົ້ນຫາ";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "ສີຕົວໜັງສືຂອງ menu ຕົກລົງ filter";
	}

	@Override
	public String colorTextoRenderer() {
		return "ສີຕົວໜັງສືຂອງ renderer";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "ສີຕົວໜັງສືຂອງ overlay ໂຫຼດ";
	}

	@Override
	public String colorBorde() {
		return "ສີຂອບ";
	}

	@Override
	public String colorFondoRetrato() {
		return "ສີພື້ນຫຼັງໃນໂໝດ portrait";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "ສີລິ້ງແບ່ງປັນ";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "ສີພື້ນຫຼັງຂອງຊ່ອງແບ່ງປັນ";
	}

	@Override
	public String rosaFondo() {
		return "ສີບົວພື້ນຫຼັງ";
	}

	@Override
	public String rosaSuave() {
		return "ສີບົວອ່ອນ";
	}

	@Override
	public String moradoAcento() {
		return "ສີມ່ວງເນັ້ນ";
	}

	@Override
	public String textoOscuro() {
		return "ຕົວໜັງສືສີເຂັ້ມ";
	}

	@Override
	public String bordeSuave() {
		return "ຂອບອ່ອນ";
	}

	@Override
	public String fondoCampo() {
		return "ພື້ນຫຼັງຊ່ອງປ້ອນຂໍ້ມູນ";
	}

	@Override
	public String fondoVistaPrevia() {
		return "ພື້ນຫຼັງຕົວຢ່າງ";
	}

	@Override
	public String sintaxisConstructor() {
		return "ສີ syntax: constructor";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "ສີ syntax: ຂໍ້ຄວາມຊ່ວຍເຫຼືອ";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "ສີ syntax: ແທັກ HTML";
	}

	@Override
	public String colorFondoVentana() {
		return "ສີພື້ນຫຼັງໜ້າຕ່າງ";
	}

	@Override
	public String colorPanel() {
		return "ສີແຜງ";
	}

	@Override
	public String colorBotonTexto() {
		return "ສີຕົວໜັງສືປຸ່ມ";
	}

	@Override
	public String colorCampo() {
		return "ສີຊ່ອງປ້ອນຂໍ້ມູນ";
	}

	@Override
	public String colorBordeDestacado() {
		return "ສີຂອບເນັ້ນ";
	}

	@Override
	public String colorSeleccionTexto() {
		return "ສີພື້ນຫຼັງການເລືອກຂໍ້ຄວາມ";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "ສີຕົວໜັງສືທີ່ຖືກເລືອກ";
	}

	@Override
	public String colorEstadoExito() {
		return "ສີສະຖານະ: ສຳເລັດ";
	}

	@Override
	public String colorEstadoFallo() {
		return "ສີສະຖານະ: ລົ້ມເຫຼວ";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "ສີສະຖານະ: snapshot";
	}

	@Override
	public String colorResultadoAnadido() {
		return "ສີຜົນລັບທີ່ເພີ່ມ";
	}

	@Override
	public String colorResultadoEliminado() {
		return "ສີຜົນລັບທີ່ຖືກລຶບ";
	}

	@Override
	public String colorBordeScroll() {
		return "ສີຂອບ scroll";
	}

	@Override
	public String colorFondoPanel() {
		return "ສີພື້ນຫຼັງແຜງ";
	}

	@Override
	public String colorBeigeListas() {
		return "ສີ beige ຂອງລາຍການ";
	}

	@Override
	public String colorTextoListas() {
		return "ສີຕົວໜັງສືໃນລາຍການ";
	}

	@Override
	public String colorBordeListas() {
		return "ສີຂອບຂອງລາຍການ";
	}

	@Override
	public String colorBotonFondo() {
		return "ສີພື້ນຫຼັງປຸ່ມ";
	}

	@Override
	public String colorBordeBoton() {
		return "ສີຂອບປຸ່ມ";
	}

	@Override
	public String colorDoradoTexto() {
		return "ສີທອງຂອງຕົວໜັງສື";
	}

	@Override
	public String colorPila() {
		return "ສີ stack trace";
	}

	@Override
	public String colorTextoPanel() {
		return "ສີຕົວໜັງສືແຜງ";
	}

	@Override
	public String colorTextoNegro() {
		return "ສີຕົວໜັງສືດຳ";
	}

	@Override
	public String colorTextoPrincipal() {
		return "ສີຕົວໜັງສືຫຼັກ";
	}

	@Override
	public String colorFondoResultados() {
		return "ສີພື້ນຫຼັງຜົນລັບ";
	}

	@Override
	public String colorEstado() {
		return "ສີສະຖານະ";
	}

	@Override
	public String colorTextoDescripcion() {
		return "ສີຕົວໜັງສືຄຳອະທິບາຍ";
	}

	@Override
	public String colorTextoEstado() {
		return "ສີຕົວໜັງສືສະຖານະ";
	}

	@Override
	public String colorTextoExtra() {
		return "ສີຕົວໜັງສືເພີ່ມເຕີມ";
	}

	@Override
	public String colorSeparador() {
		return "ສີຕົວແຍກ";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "ພົບ ຜິດພາດ native <code>StubRoutines::SafeFetch32</code>. "
				+ "ບັນຫານີ້ເກີດຂຶ້ນໃນ macOS ກັບ JDK 17.0.9 ແລະ ຖືກແກ້ໄຂໃນ JDK 17.0.10 ຫຼື ເວີຊັນຕໍ່ມາ. https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "ຜິດພາດ native SafeFetch32 ໃນ JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "ອັບເດດ JDK ຂອງທ່ານເປັນເວີຊັນ 17.0.10 ຫຼື ສູງກວ່າ (ຕົວຢ່າງ: 17.0.15).";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "ຖ້າທ່ານໃຊ້ launcher ເຊັ່ນ MultiMC, Prism Launcher ຫຼື TLauncher, ໃຫ້ຕັ້ງຄ່າໃຫ້ໃຊ້ JDK ທີ່ໃໝ່ກວ່າ. "
				+ "ບາງຕົວມີ JDK 17.0.15 ຕິດຕັ້ງມາໃຫ້ແລ້ວ.";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "Mod Spark ກໍ່ອາດຈະເຮັດໃຫ້ເກີດ ຜິດພາດ ນີ້. ພິຈາລະນາປິດການເຮັດວຽກຊົ່ວຄາວ. https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mod MCEF (Chromium Embedded Framework) ກຳລັງເຮັດໃຫ້ເກີດການ hang ແບບ silent.</b>" + "<ul>"
				+ "<li>MCEF ກຳລັງ initialize ຢູ່ທ້າຍ log, ເຊິ່ງໂດຍທົ່ວໄປໝາຍຄວາມວ່າເກມ hang ໃນລະຫວ່າງໂຫຼດ.</li>"
				+ "<li>Mod ນີ້ເປັນທີ່ຮູ້ຈັກວ່າເຮັດໃຫ້ເກີດ crash ໃນລະບົບ Linux, macOS ຫຼື ກັບ Java ບາງເວີຊັນ.</li>"
				+ "<li>ບໍ່ໄດ້ມີ ຜິດພາດ ທີ່ຊັດເຈນສະເໝີໄປ, ແຕ່ເກມຈະບໍ່ໄປເຖິງ main menu.</li>" + "</ul>"
				+ "<p>ຖ້າທ່ານບໍ່ຕ້ອງການຟັງຊັນ browser ໃນເກມ (ເຊັ່ນ ແຜນທີ່ web ຫຼື ໜ້າ embed), ໃຫ້ລຶບ mod ນີ້ອອກ.</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "ບັນຫາການ initialize ຂອງ MCEF (mod browser embed)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "ລຶບໄຟລ໌ mod MCEF (ຊອກຫາ 'mcef' ໃນຊື່ໄຟລ໌) ອອກຈາກໂຟລເດີ 'mods'.";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "ຖ້າທ່ານຈຳເປັນຕ້ອງໃຊ້ມັນ, ໃຫ້ແນ່ໃຈວ່າໃຊ້ເວີຊັນທີ່ເຂົ້າກັນໄດ້ກັບລະບົບປະຕິບັດການ ແລະ ເວີຊັນ Minecraft ຂອງທ່ານ.";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງລະຫວ່າງ <b>OptiFine</b> ແລະ <b>Iris/Oculus</b>.</b>" + "<ul>"
				+ "<li>OptiFine ດັດແປງການ render ຂອງ Minecraft ແບບບໍ່ເຂົ້າກັນກັບ Iris ຫຼື Oculus.</li>"
				+ "<li>ຜິດພາດ <code>MixinLevelRenderer failed injection check</code> ມາຈາກ <code>mixins.iris.json</code> ຫຼື <code>mixins.oculus.json</code>.</li>"
				+ "</ul>"
				+ "<p>Mods ເຫຼົ່ານີ້ບໍ່ສາມາດໃຊ້ຮ່ວມກັນໄດ້. ລຶບ OptiFine ອອກເພື່ອໃຊ້ shaders ກັບ Iris ຫຼື Oculus.</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "ຂໍ້ຂັດແຍ່ງລະຫວ່າງ OptiFine ແລະ Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "ລຶບໄຟລ໌ OptiFine ອອກຈາກໂຟລເດີ 'mods'.";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "ໃຊ້ Iris ຫຼື Oculus ໂດຍບໍ່ມີ OptiFine ສຳລັບ shaders ສະໄໝໃໝ່.";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງລະຫວ່າງ <b>ModernFix</b> ແລະ <b>OptiFine</b>.</b>" + "<ul>"
				+ "<li>ModernFix ບໍ່ເຂົ້າກັນກັບ OptiFine ເພາະມັນທຳລາຍຟັງຊັນຂອງ Forge ແລະ ເຮັດໃຫ້ການເລີ່ມຕົ້ນຊ້າລົງ.</li>"
				+ "<li>ModernFix ເອງກໍ່ມີຄຳເຕືອນ: <i>\"Use of ModernFix with OptiFine is not supported\"</i>.</li>"
				+ "</ul>" + "<p>ທ່ານຕ້ອງ ລົບ ຕົວໃດຕົວໜຶ່ງອອກເພື່ອໃຫ້ເກມເຮັດວຽກໄດ້ຢ່າງຖືກຕ້ອງ.</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "ຂໍ້ຂັດແຍ່ງລະຫວ່າງ ModernFix ແລະ OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "ລຶບ OptiFine ຫຼື ModernFix ອອກຈາກໂຟລເດີ 'mods'. ພວກມັນບໍ່ສາມາດໃຊ້ຮ່ວມກັນໄດ້.";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "ຖ້າທ່ານຕ້ອງການການປັບປຸງປະສິດທິພາບ, ພິຈາລະນາໃຊ້ສະເພາະ OptiFine, ຫຼື ແທນທີ່ ModernFix ດ້ວຍ mods ທີ່ເບົາກວ່າເຊັ່ນ FerriteCore ຫຼື EntityCulling.";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ຜິດພາດ: registry key ບໍ່ຖືກຕ້ອງ ມີຕົວອັກສອນທີ່ບໍ່ອະນຸຍາດ.</b>" + "<ul>"
				+ "<li><b>Key ທີ່ພົບ:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>ໃນ Minecraft, registry keys ທັງໝົດ (tags, recipes, achievements, ແລະອື່ນໆ) ຕ້ອງເປັນ <b>ຕົວພິມນ້ອຍ</b> ແລະ ໃຊ້ໄດ້ສະເພາະຕົວອັກສອນ, ຕົວເລກ, underscore, hyphen ແລະ slash.</li>"
				+ "<li>ຜິດພາດ ນີ້ມັກເກີດຈາກ mod ທີ່ຂຽນບໍ່ດີ ຫຼື datapack ທີ່ເສຍຫາຍ.</li>" + "</ul>"
				+ "<p><b>ຄຳແນະນຳສຳຄັນ:</b> ໃຊ້ເຄື່ອງມື <b>grepr</b> ຫຼື <b>fgrepr</b> ໃນ sidebar ແລະ ເປີດໃຊ້ຕົວເລືອກ <b>\"ຄົ້ນຫາ ໃນໄຟລ໌ JAR\"</b> ເພື່ອຊອກຫາວ່າ mod ໃດມີ key ທີ່ບໍ່ຖືກຕ້ອງນີ້.</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "Registry key ມີຕົວພິມໃຫຍ່ ຫຼື ຕົວອັກສອນບໍ່ຖືກຕ້ອງ";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "ໃຊ້ 'grepr' ຫຼື 'fgrepr' ພ້ອມກັບ \"ຄົ້ນຫາ ໃນໄຟລ໌ JAR\" ເພື່ອຊອກຫາ mod ທີ່ເປັນສາເຫດ.";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "ຖ້າທ່ານບໍ່ສາມາດລະບຸ mod ໄດ້, ໃຫ້ລຶບ mods ທີ່ຕິດຕັ້ງໃໝ່ໆ, ໂດຍສະເພາະໂຕທີ່ເພີ່ມ blocks, items ຫຼື ເຄື່ອງມື.";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ຜິດພາດໃນການໂຫຼດ mod <b>"
				+ escapeHtml(modNombre) + "</b>.</b>" + "<ul>"
				+ "<li>Mod ລົ້ມເຫຼວໃນການ initialize ອົງປະກອບໜຶ່ງຂອງມັນ (ຕົວຢ່າງ: menu ຕັ້ງຄ່າ).</li>"
				+ "<li>ສິ່ງນີ້ມັກເກີດຈາກຄວາມບໍ່ເຂົ້າກັນກັບເວີຊັນ Minecraft, Fabric ຫຼື ກັບ mods ອື່ນໆ.</li>" + "</ul>"
				+ "<p>ຖ້າ ຜິດພາດ ຍັງຄົງຢູ່, ໃຫ້ລຶບ ຫຼື ອັບເດດ mod <b>" + escapeHtml(modNombre) + "</b>.</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "ຜິດພາດການ initialize ຂອງ mod (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "ລຶບ mod '" + modNombre + "' ອອກຈາກໂຟລເດີ 'mods'.";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "ອັບເດດ mod '" + modNombre + "' ເປັນເວີຊັນທີ່ເຂົ້າກັນໄດ້ກັບການຕິດຕັ້ງຂອງທ່ານ.";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບ ຜິດພາດ ທີ່ກ່ຽວຂ້ອງກັບ mod <b>En Garde!</b>.</b>" + "<ul>"
				+ "<li>Mod ນີ້ເພີ່ມກົນໄກການຕໍ່ສູ້ແບບ close combat (parry, block, ແລະອື່ນໆ).</li>"
				+ "<li>ຜິດພາດ ມັກເກີດຈາກຄວາມບໍ່ເຂົ້າກັນກັບ mods ການຕໍ່ສູ້ອື່ນໆ (ເຊັ່ນ Epic Fight, DualRiders, ແລະອື່ນໆ) ຫຼື ໃຊ້ເວີຊັນທີ່ບໍ່ຖືກຕ້ອງສຳລັບ Minecraft ຂອງທ່ານ.</li>"
				+ "</ul>" + "<p>ຖ້າທ່ານບໍ່ໃຊ້ການຕໍ່ສູ້ຂັ້ນສູງ, ພິຈາລະນາ ລົບ En Garde! ເພື່ອຫຼີກລ່ຽງຂໍ້ຂັດແຍ່ງ.</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "ຜິດພາດໃນ mod En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "ໃຫ້ແນ່ໃຈວ່າໃຊ້ເວີຊັນ En Garde! ທີ່ເຂົ້າກັນໄດ້ກັບເວີຊັນ Minecraft ແລະ loader (Fabric/Forge) ຂອງທ່ານ.";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "ຖ້າທ່ານໃຊ້ mods ການຕໍ່ສູ້ອື່ນໆ (Epic Fight, Caelus, ແລະອື່ນໆ), ໃຫ້ປິດການເຮັດວຽກ ຫຼື ລຶບ En Garde! ເພື່ອຫຼີກລ່ຽງຂໍ້ຂັດແຍ່ງ.";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບ ຜິດພາດ ທີ່ເກີດຈາກ mod <b>IdleTweaks</b>.</b>" + "<ul>"
				+ "<li>IdleTweaks ພະຍາຍາມ release network channel ທີ່ບໍ່ມີຢູ່ແລ້ວ (<code>Tried to release unknown channel</code>).</li>"
				+ "<li>ຜິດພາດ ນີ້ມັກເກີດໃນເວີຊັນເກົ່າຂອງ mod ຫຼື ເມື່ອໃຊ້ໃນ servers ທີ່ຕັ້ງຄ່າບໍ່ຖືກຕ້ອງ.</li>"
				+ "</ul>"
				+ "<p>IdleTweaks ແມ່ນ mod ປັບປຸງປະສົບການຜູ້ໃຊ້, ແຕ່ອາດເຮັດໃຫ້ເກີດຄວາມບໍ່ໝັ້ນຄົງ. ພິຈາລະນາອັບເດດ ຫຼື ລົບ ມັນ.</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "ຜິດພາດໃນ IdleTweaks (network channel ບໍ່ຮູ້ຈັກ)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "ອັບເດດ IdleTweaks ເປັນເວີຊັນລ່າສຸດທີ່ເຂົ້າກັນໄດ້ກັບ Minecraft ຂອງທ່ານ.";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "ລຶບ IdleTweaks ອອກຈາກໂຟລເດີ 'mods' ຖ້າທ່ານບໍ່ຈຳເປັນຕ້ອງໃຊ້.";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບ ຜິດພາດ ການຢັ້ງຢືນຕົວຕົນ (HTTP 401) ໃນລະຫວ່າງການເຂົ້າສູ່ລະບົບ Minecraft.</b>"
				+ "<p>ຜິດພາດ ນີ້ <b>ຫາຍາກທີ່ຈະເປັນສາເຫດໂດຍກົງຂອງ crash</b>, ແຕ່ມັນຊີ້ບອກວ່າທ່ານກຳລັງໃຊ້ບັນຊີທີ່ບໍ່ໄດ້ຢັ້ງຢືນ (pirate).</p>"
				+ "<p>ຊ່ອງທາງຊ່ວຍເຫຼືອທາງການ (ໂຄງການບໍລິສັດ, VTubers, ຜູ້ສ້າງ modpacks, ແລະອື່ນໆ) <b>ບໍ່ສາມາດຊ່ວຍທ່ານໄດ້</b> ຖ້າທ່ານໃຊ້ສຳເນົາ pirate, "
				+ "ເນື່ອງຈາກຂໍ້ຈຳກັດຂອງກົດລະບຽບ chat, ສັນຍາ, ຂໍ້ຕົກລົງກັບ Mojang/Microsoft, ຫຼື ນະໂຍບາຍຊື່ສຽງ.</p>"
				+ "<p>ການກວດສອບນີ້ສາມາດ <b>ປິດການເຮັດວຽກໄດ້ໃນການຕັ້ງຄ່າບໍລິສັດ</b> ຂອງ detector. "
				+ "ຄຳເຕືອນ: ການກວດຈັບ antipiracy <b>ບໍ່ສົມບູນແບບ</b> ແລະ ອາດຈະຖືກກະຕຸ້ນໃນສະພາບແວດລ້ອມພັດທະນາ, ເມື່ອອິນເຕີເນັດບໍ່ໝັ້ນຄົງ, ຫຼື ເມື່ອໃຊ້ launchers ທີ່ຖືກດັດແປງ.</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>ສິດທິ Miranda ຖ້າທ່ານພະຍາຍາມເຂົ້າຮ່ວມການຊ່ວຍເຫຼືອ:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Minecraft ລະເມີດລິຂະສິດ";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "ປິດການກວດສອບ antipiracy";
	}

	@Override
	public String comprarMC() {
		return "ຊື້ Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "ທ່ານກຳລັງໃຊ້ launcher <code>" + id
				+ "</code>, ເຊິ່ງ <b>ບໍ່ໄດ້ຢູ່ໃນລາຍຊື່ launchers ທີ່ແນະນຳ</b>.</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>ເຖິງວ່າມັນອາດຈະເຮັດວຽກໄດ້, ແຕ່ launchers ທີ່ບໍ່ແນະນຳມັກຈະເຮັດໃຫ້ເກີດ:</p>" + "<ul>"
				+ "<li>ການຕິດຕັ້ງ mods ຫຼື App ທີ່ເສຍຫາຍ.</li>"
				+ "<li>ເກມບໍ່ເລີ່ມຕົ້ນ ຫຼື hang ໂດຍບໍ່ມີ ຜິດພາດ ທີ່ຊັດເຈນ.</li>"
				+ "<li>ໂຄງສ້າງໂຟລເດີທີ່ຜິດປົກກະຕິ (ເຮັດໃຫ້ການວິນິດໄສຍາກ).</li>"
				+ "<li>ພຶດຕິກຳທີ່ບໍ່ຄາດຄິດກັບ Java, ຫນ່ວຍຄວາມຈຳ ຫຼື mods.</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "ເພື່ອປະສົບການທີ່ດີກວ່າ, ໃຫ້ໃຊ້ໜຶ່ງໃນ launchers ທີ່ແນະນຳເຫຼົ່ານີ້:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Launcher ທີ່ບໍ່ແນະນຳ";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "ປ່ຽນໄປໃຊ້ launcher ຈາກລາຍຊື່ທີ່ແນະນຳ.";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "ທ່ານກຳລັງໃຊ້ <b>launcher ທີ່ບໍ່ຄວນໃຊ້</b>: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>Launchers ທີ່ບໍ່ຄວນໃຊ້ອາດຈະເຮັດໃຫ້ເກີດ:</p>" + "<ul>" + "<li>ການຕິດຕັ້ງ App ຫຼື mods ທີ່ເສຍຫາຍ.</li>"
				+ "<li>ເກມບໍ່ເລີ່ມຕົ້ນ ຫຼື ລົ້ມເຫຼວແບບ silent.</li>"
				+ "<li>ການຈັດລຽງໄຟລ໌ທີ່ຜິດປົກກະຕິ (ຍາກຕໍ່ການ debug).</li>"
				+ "<li>ຄວາມບໍ່ແນ່ນອນກ່ຽວກັບວິທີການຈັດການ mods, Java ຫຼື ຫນ່ວຍຄວາມຈຳ.</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "ແນະນຳຢ່າງເຂັ້ມງວດໃຫ້ໃຊ້ໜຶ່ງໃນ launchers ຕໍ່ໄປນີ້:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Launcher ທີ່ບໍ່ຄວນໃຊ້";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "ປ່ຽນໄປໃຊ້ launcher ທີ່ແນະນຳເພື່ອຮັບການຊ່ວຍເຫຼືອ.";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "ຂາດ mods ທີ່ແນະນຳສຳລັບສະພາບແວດລ້ອມນີ້.</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "ຂາດ mods ທີ່ແນະນຳ";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "ຕິດຕັ້ງ mods ທີ່ແນະນຳເພື່ອປະສົບການທີ່ດີທີ່ສຸດ.";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບ mods ທີ່ບໍ່ຄວນໃຊ້ໃນການຕິດຕັ້ງຂອງທ່ານ.</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "ພົບ mods ທີ່ບໍ່ຄວນໃຊ້";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "ລຶບ mods ທີ່ບໍ່ຄວນໃຊ້ອອກເພື່ອຫຼີກລ່ຽງບັນຫາ.";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ພົບການດັດແປງໄຟລ໌ສຳຄັນໂດຍບໍ່ໄດ້ຮັບອະນຸຍາດ. ທ່ານໄດ້ດັດແປງໄຟລ໌ ຫຼື ກຳລັງໃຊ້ launcher ທີ່ບໍ່ໜ້າເຊື່ອຖື.</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "ພົບການດັດແປງ";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "ຕິດຕັ້ງໄຟລ໌ດັ້ງເດີມໃໝ່ເພື່ອຟື້ນຟູຄວາມຖືກຕ້ອງ.";
	}

	@Override
	public String configuracionCorporativa() {
		return "ການຕັ້ງຄ່າ Corporativa";
	}

	@Override
	public String idiomaRespaldo() {
		return "ພາສາສຳຮອງ";
	}

	@Override
	public String buscardorHabilitado() {
		return "ເປີດໃຊ້ ຕົວຊອກຫາ";
	}

	@Override
	public String nombreHerramienta() {
		return "ຊື່ເຄື່ອງມື";
	}

	@Override
	public String condenarPirateria() {
		return "ປະນາມການລະເມີດລິຂະສິດ";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "Launchers ທີ່ແນະນຳ";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "Launchers ທີ່ບໍ່ຄວນໃຊ້";
	}

	@Override
	public String modsRecomendados() {
		return "Mods ທີ່ແນະນຳ";
	}

	@Override
	public String modsDesaconsejados() {
		return "Mods ທີ່ບໍ່ຄວນໃຊ້";
	}

	@Override
	public String antiTamper() {
		return "AntiTamper";
	}

	@Override
	public String proximamente() {
		return "ໄວໆນີ້";
	}

	@Override
	public String informacion() {
		return "ຂໍ້ມູນ";
	}

	@Override
	public String errorCargandoImagen() {
		return "ຜິດພາດໃນການໂຫຼດຮູບ";
	}

	@Override
	public String configuracionBasica() {
		return "ການຕັ້ງຄ່າ ພື້ນຖານ";
	}

	@Override
	public String funcionalidades() {
		return "ຟັງຊັນການເຮັດວຽກ";
	}

	@Override
	public String derechosMiranda() {
		return "ສິດທິ Miranda (ແນະນຳຢ່າງຍິ່ງ)";
	}

	@Override
	public String gestionVerificaciones() {
		return "ການຈັດການການກວດສອບ";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "ຊື່";
	}

	@Override
	public String codigoVerificacion() {
		return "ລະຫັດ";
	}

	@Override
	public String documentacionVerificacion() {
		return "ເອກະສານ";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "ການກວດສອບທີ່ເປີດໃຊ້:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "ການກວດສອບທີ່ປິດໃຊ້:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "ປິດໃຊ້ທຸກໂຕທີ່ບໍ່ແມ່ນບໍລິສັດ";
	}

	@Override
	public String verCodigo() {
		return "ເບິ່ງລະຫັດ";
	}

	@Override
	public String verDocumentacion() {
		return "ເບິ່ງເອກະສານ";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "ກະລຸນາເລືອກການກວດສອບເພື່ອປິດໃຊ້.";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "ກະລຸນາເລືອກການກວດສອບເພື່ອເປີດໃຊ້.";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "ໄດ້ປິດໃຊ້ການກວດສອບ %d ອັນທີ່ບໍ່ແນະນຳສຳລັບການໃຊ້ງານໃນບໍລິສັດ.";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "ບໍ່ມີການກວດສອບທີ່ບໍ່ແມ່ນບໍລິສັດເພື່ອປິດໃຊ້.";
	}

	@Override
	public String operacionCompletada() {
		return "ດຳເນີນການສຳເລັດ";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "ພວກເຮົາຄິດຮອດ Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "ສີການກວດສອບຂອງບໍລິສັດ";
	}

	// Métodos para la gestión de lanzadores
	@Override
	public String nombreLanzador() {
		return "ຊື່ Launcher";
	}

	@Override
	public String motivo() {
		return "ເຫດຜົນ";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Launchers ທີ່ບໍ່ຄວນໃຊ້";
	}

	@Override
	public String moverADesaconsejados() {
		return "ບໍ່ແນະນຳ";
	}

	@Override
	public String moverARecomendados() {
		return "ແນະນຳ";
	}

	@Override
	public String guardarCambios() {
		return "ບັນທຶກການປ່ຽນແປງ";
	}

	@Override
	public String cancelar() {
		return "ຍົກເລີກ";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "ກະລຸນາເລືອກ launcher ເພື່ອຍ້າຍ.";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "ບັນທຶກການປ່ຽນແປງສຳເລັດ!";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEs(String nombreLanzador) {
		return "Launcher ນີ້ບໍ່ໄດ້ຮັບການແນະນຳເນື່ອງຈາກບັນຫາຄວາມປອດໄພ ແລະ ຄວາມໝັ້ນຄົງທີ່ຮູ້ຈັກ.";
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
		return "ເຫດຜົນ";
	}

	@Override
	public String agregarLanzador() {
		return "ເພີ່ມ launcher";
	}

	@Override
	public String quitarLanzador() {
		return "ລຶບ launcher";
	}

	@Override
	public String editarRazones() {
		return "ແກ້ໄຂເຫດຜົນ";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "ເລືອກ launcher ທີ່ຈະລຶບ.";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "ເລືອກ launcher ທີ່ຈະແກ້ໄຂ.";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "ແກ້ໄຂເຫດຜົນສຳລັບ " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "ເພີ່ມພາສາໃໝ່";
	}

	@Override
	public String aceptar() {
		return "ຕົກລົງ";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "ເລືອກພາສາ";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "Launchers ເຫຼົ່ານີ້ແມ່ນໂຕທີ່ CrashDetector ແນະນຳວ່າດີ.";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "ຜົນລັບທີ່ຖືກຕ້ອງ";
	}

	public String modsNoRecomendados() {
		return "Mods ທີ່ບໍ່ຄວນໃຊ້";
	}

	public String agregarMod() {
		return "ເພີ່ມ mod";
	}

	public String quitarMod() {
		return "ລຶບ mod";
	}

	public String modId() {
		return "Mod ID/ຊື່ JBoss Modules";
	}

	public String rutaMod() {
		return "ເສັ້ນທາງ / ໄຟລ໌ mod";
	}

	public String errorDebeIndicarMod() {
		return "ທ່ານຕ້ອງລະບຸຢ່າງໜ້ອຍ modid ຫຼື ເສັ້ນທາງຂອງ mod.";
	}

	public String modsNoRecomendadosAviso() {
		return "ທ່ານສາມາດລົງທະບຽນ mods ທີ່ບໍ່ຄວນໃຊ້ເພື່ອໃຫ້ CrashDetector ກວດພົບຖ້າມັນຖືກຕິດຕັ້ງ.";
	}

	@Override
	public String anularNormal() {
		return "ຍົກເລີກປົກກະຕິ";
	}

	@Override
	public String anularNormalDescripcion() {
		return "CrashDetector ຄວນແຈ້ງເຕືອນເຖິງແມ່ນວ່າຈະບໍ່ມີ crash";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "ລົງທະບຽນ mods ທີ່ CrashDetector ແນະນຳ. ຖ້າຂາດ, CrashDetector ອາດຈະແຈ້ງເຕືອນ.";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "ຖ້າທ່ານຕັດສິນໃຈເປີດໃຊ້ຄຳເຕືອນ antipiracy, ແນະນຳໃຫ້ກຳນົດ "
				+ "ສິດທິຂອງຜູ້ທີ່ຂໍການຊ່ວຍເຫຼືອຢູ່ທີ່ນີ້, ເປັນມາດຕະການປ້ອງກັນ.\n\n"

				+ "ກົງກັນຂ້າມກັບຄວາມເຊື່ອທົ່ວໄປ, ຫຼາຍຊຸມຊົນ ແລະ ຊ່ອງທາງຊ່ວຍເຫຼືອທີ່ນິຍົມ "
				+ "ບໍ່ຈຳເປັນຕ້ອງເປີດໃຊ້ຄຳເຕືອນ antipiracy ເພື່ອໃຫ້ຄວາມຊ່ວຍເຫຼືອ. ເຖິງຢ່າງໃດກໍ່ຕາມ, "
				+ "ການບັນທຶກສິດທິເຫຼົ່ານີ້ອາດຈະມີປະໂຫຍດໃນກໍລະນີທີ່ບຸກຄົນເຂົ້າເຖິງຊ່ອງທາງ "
				+ "ຊ່ວຍເຫຼືອຢ່າງໃດກໍ່ຕາມ.\n\n"

				+ "ທ່ານສາມາດອ້າງອີງຈາກເອກະສານທາງການເຊັ່ນ Cartilla de Derechos Básicos del Detenido " + "ໃນ Mexico:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "ເຊັ່ນດຽວກັນກັບຫຼັກການທາງກົດໝາຍທີ່ຄ້າຍຄືກັນທີ່ໃຊ້ໃນປະເທດອື່ນໆ, ລວມທັງ "
				+ "ສະຫະລັດອາເມລິກາ, ສະຫະພັນຣັດເຊຍ, ສາທາລະນະລັດປະຊາຊົນຈີນ, ສາທາລະນະລັດອິສລາມ "
				+ "ອີຣ່ານ ແລະ ສາທາລະນະລັດປະຊາທິປະໄຕປະຊາຊົນເກົາຫຼີ.\n\n"

				+ "ບາງຕົວຢ່າງຂອງສິດທິທີ່ສາມາດລວມເຂົ້າໄປໄດ້ແມ່ນ:\n"
				+ "• ສິດທີ່ຈະບໍ່ໃຫ້ຂໍ້ມູນທີ່ບໍ່ຈຳເປັນສຳລັບການຊ່ວຍເຫຼືອ, ເຊັ່ນ launcher ທີ່ໃຊ້, "
				+ "ຊື່ຜູ້ໃຊ້ ຫຼື UUID.\n" + "• ສິດທີ່ຈະບໍ່ໃຫ້ຄຳໃຫ້ທີ່ເປັນຜົນຮ້າຍຕໍ່ຕົນເອງ.\n"
				+ "• ສິດທີ່ຈະປະຕິເສດການຕອບຄຳຖາມທີ່ບໍ່ຈຳເປັນສຳລັບການແກ້ໄຂບັນຫາ.\n"
				+ "• ສິດທີ່ຈະໄດ້ຮັບຄຳແນະນຳພາຍໃນ chat.\n"
				+ "• ສິດທີ່ຈະໃຊ້ການປິດບັງຊື່ (anonymization) ຂອງ logs ທີ່ມີຢູ່ໃນ CrashDetector.\n\n"

				+ "ຂໍ້ຄວາມນີ້ຍອມຮັບເນື້ອຫາ HTML.";
	}

	@Override
	public String editar() {
		return "ແກ້ໄຂ";
	}

	@Override
	public String advertenciaHashLento() {
		return "ຄຳເຕືອນ: ການເພີ່ມໄຟລ໌ຂະໜາດໃຫຍ່ຫຼາຍໆໄຟລ໌ອາດເຮັດໃຫ້ການກວດສອບ "
				+ "ໃຊ້ເວລາຫຼາຍນາທີ. CrashDetector ຈະຕ້ອງຄິດໄລ່ hash ຂອງແຕ່ລະໄຟລ໌ "
				+ "ກ່ອນທີ່ຈະດຳເນີນການຕໍ່. ແນະນຳໃຫ້ປ້ອງກັນສະເພາະໄຟລ໌ທີ່ຈຳເປັນຢ່າງເຂັ້ມງວດ.";
	}

	@Override
	public String agregarArchivo() {
		return "ເພີ່ມໄຟລ໌";
	}

	@Override
	public String agregarCarpeta() {
		return "ເພີ່ມໂຟລເດີ";
	}

	@Override
	public String quitar() {
		return "ລຶບ";
	}

	@Override
	public String rutaArchivo() {
		return "ເສັ້ນທາງໄຟລ໌";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "ເສັ້ນທາງທີ່ເລືອກຢູ່ນອກໂດຍເລກຕອຣີປະຈຸບັນຂອງເກມ. "
				+ "ອະນຸຍາດໃຫ້ໃຊ້ໄດ້ສະເພາະໄຟລ໌ ແລະ ໂຟລເດີທີ່ຢູ່ໃນໂດຍເລກຕອຣີປະຈຸບັນ ຫຼື ໂຟລເດີຍ່ອຍຂອງມັນເທົ່ານັ້ນ.";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>"
				+ "ຄວາມຄິດເຫັນ ແລະ ຄຳເຫັນຂອງ Sylent Bell ອາດຈະບໍ່ສອດຄ່ອງກັບຂອງເຮົາ; "
				+ "ເຮົາພຽງແຕ່ຄິດວ່າມັນຈະຕະຫຼົກຖ້າເອົາມາໄວ້ທີ່ນີ້. CrashDetector ແມ່ນ secular." + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Mod GML (Groovy ModLoader) ຕ້ອງການການປ່ຽນແປງເຫຼົ່ານີ້ ແລະ ເປັນສາເຫດຫຼັກຂອງບັນຫານີ້.</b>";
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
				+ "ພົບການໃຊ້ <i>Independiente Flywheel</i>.</b>"
				+ "<p><b>Independiente Flywheel ໝົດອາຍຸແລ້ວ (deprecated)</b> ແລະ ບໍ່ຄວນໃຊ້ໃນເວີຊັນສະໄໝໃໝ່.</p>"
				+ "<p>ເວີຊັນປະຈຸບັນຂອງ <b>Create</b> <b>ລວມມີ Flywheel ຢູ່ແລ້ວ</b>, ດັ່ງນັ້ນການຕິດຕັ້ງແຍກຕ່າງຫາກ "
				+ "ຈະເຮັດໃຫ້ເກີດຂໍ້ຂັດແຍ່ງຄວາມເຂົ້າກັນໄດ້ ແລະ ຜິດພາດ ໃນການໂຫຼດ.</p>"
				+ "<p>ບາງ mods ທີ່ຂຶ້ນກັບ Independiente Flywheel ຢ່າງຊັດເຈນອາດຈະ "
				+ "<b>ບໍ່ເຮັດວຽກ</b> ຫຼື <b>ເຮັດວຽກບໍ່ໝັ້ນຄົງ</b>. "
				+ "ໃນກໍລະນີຂັ້ນສູງບາງຢ່າງ, mods ເຫຼົ່ານີ້ອາດຈະເຮັດວຽກໄດ້ຖ້າ "
				+ "<b>ແກ້ໄຂໄຟລ໌ <code>mods.toml</code> ດ້ວຍມື</b> ເພື່ອປັບຊ່ວງເວີຊັນ, "
				+ "ເຖິງວ່າສິ່ງນີ້ <b>ບໍ່ໄດ້ຮັບການແນະນຳ</b>.</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>Mods ທີ່ພົບວ່າອ້າງອີງເຖິງ Flywheel:</b></p>" + "<ul>" + listaMods.toString() + "</ul>")
				+ "<p>ວິທີແກ້ໄຂທີ່ແນະນຳແມ່ນ <b>ລົບ Independiente Flywheel</b> ແລະ ໃຊ້ສະເພາະ "
				+ "ເວີຊັນທີ່ມາພ້ອມກັບ Create.</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Flywheel ແຍກຕ່າງຫາກ";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບ ຜິດພາດ ທີ່ກ່ຽວຂ້ອງກັບ mod <i>Floral Enchantments</i>.</b>"
				+ "<p>ການ crash ເກີດຈາກຂໍ້ຜິດພາດພາຍໃນຂອງ mod ໃນການຈັດການຂໍ້ມູນເກມ, "
				+ "ເຊິ່ງເຮັດໃຫ້ເກີດ <b>NullPointerException</b> ໃນລະຫວ່າງການເຮັດວຽກ.</p>"
				+ "<p>ບັນຫານີ້ມັກຈະແກ້ໄຂໄດ້ໂດຍການອັບເດດ mod ຫຼື ລຶບມັນອອກ.</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "ຜິດພາດຂອງ Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "ທ່ານມີ MixinExtras ເວີຊັນ NeoForge ແລະ ເວີຊັນປົກກະຕິ. ຖ້າທ່ານໃຊ້ MinecraftForge, ທ່ານສາມາດຕິດຕັ້ງ <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>ລິ້ງນີ້</a> ເພື່ອແກ້ໄຂບັນຫາ.</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບ ຜິດພາດ ໃນເງົາຂອງພື້ນທີ່ (terrain) ກັບ shaders (Iris).</b>"
				+ "<p>ບັນຫາເກີດຂຶ້ນໃນລະຫວ່າງການ render ພື້ນທີ່.</p>"
				+ "<p>ແນະນຳໃຫ້ <b>ລອງຫຼິ້ນເກມໂດຍບໍ່ມີ shaders</b> ຫຼື ຫຼຸດຄຸນນະພາບກຣາຟິກລົງ, "
				+ "ໂດຍສະເພາະໃນການຕັ້ງຄ່າ <b>Ultra</b>.</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "ເງົາຂອງພື້ນທີ່ (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບ server tick ທີ່ຍາວເກີນໄປ.</b>"
				+ "<p>ສິ່ງນີ້ຊີ້ບອກວ່າເກມໄດ້ຖືກ block ເປັນເວລາດົນເກີນໄປໃນ tick ດຽວ.</p>"
				+ "<p>ແນະນຳໃຫ້ <b>ກວດສອບ thread dump</b> ທີ່ຖືກສ້າງໃນ log ເພື່ອລະບຸສາເຫດ.</p>"
				+ "<p><b>ການວິເຄາະ Stack Trace</b> ສາມາດຊ່ວຍທ່ານຊອກຫາແຫຼ່ງທີ່ມາຂອງການ block.</p>"
				+ "<p>ນອກຈາກນີ້, ປຸ່ມ <b>ເບິ່ງໃນ log</b> ຈະ highlight ສີແດງ mods ທີ່ອາດຈະເປັນສາເຫດ, "
				+ "ເຊັ່ນດຽວກັບ entries ທີ່ຢູ່ໃນ <code>$modid$</code>, ເຊິ່ງມັກຈະຊີ້ບອກແຫຼ່ງທີ່ມາຂອງບັນຫາ. ສຳລັບການສະແກນແບບ real-time, ແນະນຳໃຫ້ໃຊ້ CPU sampler ໃນ VisualVM. ໃຫ້ແນ່ໃຈວ່າ server ຫຼື ຄອມພິວເຕີຂອງທ່ານມີປະສິດທິພາບພຽງພໍທີ່ຈະຈັດການກັບ mods ທີ່ທ່ານໃຊ້, ເນື່ອງຈາກເປັນໄປໄດ້ວ່າ mods ທັງໝົດຂອງທ່ານເຮັດວຽກຖືກຕ້ອງ, ແຕ່ທ່ານອາດຈະມີຈຳນວນຫຼາຍເກີນໄປ.</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Server tick ຍາວ";
	}

	@Override
	public String tituloLFPDPPP() {
		return "ກົດໝາຍສະຫະພັນວ່າດ້ວຍການປົກປ້ອງຂໍ້ມູນສ່ວນຕົວໃນການຄອບຄອງຂອງບຸກຄົນ";
	}

	@Override
	public String aceptarPermanentemente() {
		return "ຍອມຮັບຖາວອນ";
	}

	// Métodos para la Acta de Protección del Idioma Cultural de Pyongyang
	public String actaProteccionIdiomaCultural() {
		return "ກົດໝາຍປົກປ້ອງພາສາວັດທະນະທຳແຫ່ງ Pyongyang";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "ການແປພາສາເກົາຫຼີມີຄຳສັງ slang ຈາກພາກໃຕ້ທີ່ຄວນຫຼີກລ່ຽງເພື່ອປະຕິບັດຕາມກົດໝາຍ. "
				+ "ການໃຊ້ພາສາຕ່າງປະເທດ, ໂດຍສະເພາະຈາກພາກໃຕ້, ຖືກຫ້າມຢ່າງເຂັ້ມງວດ "
				+ "ຕາມກົດໝາຍປົກປ້ອງພາສາວັດທະນະທຳແຫ່ງ Pyongyang.";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "ສຳລັບຂໍ້ມູນເພີ່ມເຕີມ, ກະລຸນາເບິ່ງເອກະສານທາງການຂອງກົດໝາຍ: "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>ກົດໝາຍປົກປ້ອງພາສາວັດທະນະທຳແຫ່ງ Pyongyang</a>";
	}

	public String leerLeyCompleta() {
		return "ອ່ານກົດໝາຍເຕັມຮູບແບບ";
	}

	public String errorAbriendoEnlace() {
		return "ຜິດພາດໃນການເປີດລິ້ງ";
	}

	@Override
	public String canarioTitulo() {
		return "Canary ທາງກົດໝາຍ";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — ຕົວຕິດຕາມການສອດແນມ";
	}

	@Override
	public String revisar() {
		return "ກວດສອບ";
	}

	@Override
	public String cerrar() {
		return "ປິດ";
	}

	@Override
	public String canarioTodoSeguro() {
		return "ບໍລິການທັງໝົດລາຍງານສະຖານະປອດໄພ.";
	}

	@Override
	public String canarioComprometido(int c) {
		return "ຄຳເຕືອນ: ມີ " + c + " ບໍລິການລາຍງານສະຖານະບໍ່ປອດໄພ.";
	}

	@Override
	public String colorAlerta() {
		return "ສີຄຳເຕືອນ";
	}

	public String opcionesMunidiales() {
		return "ຕົວເລືອກ Munidial";
	}

	public String consentimientoLFPDPPP() {
		return "ຍອມຮັບ LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "ເປີດໃຊ້ການໂອນ token ການເຂົ້າເຖິງໃນ Handoff ສຳລັບ ReLauncher (ບໍ່ແນະນຳ).";
	}

	public String consolaDesarrollo() {
		return "Console ພັດທະນາ";
	}

	public String mundial() {
		return "ສາກົນ";
	}

	public String ningun() {
		return "ບໍ່ມີ";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "Console ນັກພັດທະນາ";
	}

	public String bajar() {
		return "ລົງ";
	}

	public String logsSoporte() {
		return "Logs ສຳລັບການຊ່ວຍເຫຼືອ";
	}

	public String detenerProceso() {
		return "ຢຸດ process";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "ຄັດລອກທີ່ເລືອກ";
	}

	public String seleccionarTodo() {
		return "ເລືອກທັງໝົດ";
	}

	public String copiarTodo() {
		return "ຄັດລອກທັງໝົດ";
	}

	public String guardarTodoComoArchivo() {
		return "ບັນທຶກທັງໝົດເປັນໄຟລ໌";
	}

	public String obtenerEnlaceSoporte() {
		return "ຮັບລິ້ງຊ່ວຍເຫຼືອ";
	}

	public String borrarTodo() {
		return "ລຶບທັງໝົດ";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "ສີພື້ນຫຼັງ console";
	}

	public String colorTextoConsola() {
		return "ສີຕົວໜັງສື console";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "ຢັ້ງຢືນການຍອມຮັບແລ້ວ.\nການລວມຕົວແບ່ງປັນ logs ຈະຖືກຈັດຕັ້ງປະຕິບັດຢູ່ທີ່ນີ້.";
	}

	@Override
	public String usarSakuraOriginal() {
		return "ໃຊ້ຮູບ Sakura Riddle ດັ້ງເດີມ";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "\"Warrant canary\" ແມ່ນກົນໄກຄວາມໂປ່ງໃສ.\n\n"
				+ "ຕາບໃດທີ່ຂໍ້ຄວາມນີ້ຍັງມີຢູ່ ແລະ ບໍລິການຕ່າງໆສະແດງວ່າປອດໄພ, "
				+ "ໝາຍຄວາມວ່າໂຄງການ ບໍ່ໄດ້ຮັບຄຳສັ່ງສານລັບ, " + "ຄຳສັ່ງเซนເຊີ, ຫຼື ຄຳຮ້ອງຂໍທາງກົດໝາຍເພື່ອການສອດແນມ.\n\n"
				+ "ຖ້າ canary ໃດໜຶ່ງຫາຍໄປ ຫຼື ຖືກໝາຍວ່າບໍ່ປອດໄພ, " + "ສິ່ງນັ້ນຊີ້ບອກວ່າມີການປ່ຽນແປງທາງກົດໝາຍ.\n\n"
				+ "ແຜງນີ້ຈະກວດສອບ canaries ທັງໝົດທີ່ລົງທະບຽນໃນລະບົບ ແລະ ສະແດງ " + "ສະຖານະປະຈຸບັນ.\n\n"
				+ "ກົດ \"ກວດສອບ\" ເພື່ອອັບເດດສະຖານະ.";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "ຕັ້ງຄ່າທັງໝົດຄືນເປັນຄ່າເລີ່ມຕົ້ນ?";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "ບໍ່ມີຕົວເລືອກ";
	}

	@Override
	public String seleccionaColor() {
		return "ເລືອກສີ";
	}

	@Override
	public String botonMostrarGUI() {
		return "ສະແດງ GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "ບັນທຶກທັງໝົດ";
	}

	@Override
	public String botonRestablecerTodo() {
		return "ຕັ້ງຄ່າຄືນທັງໝົດ";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms ບໍ່ໄດ້ໂຫຼດ";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບ ຜິດພາດ ໃນການເຂົ້າເຖິງ API ຂອງ LuckPerms.</b>"
				+ "<p>ຂໍ້ຄວາມຊີ້ບອກວ່າ <b>LuckPerms ບໍ່ໄດ້ໂຫຼດ</b> ໃນຂະນະທີ່ plugin ອື່ນພະຍາຍາມໃຊ້ມັນ.</p>"
				+ "<p><b>ສາເຫດທີ່ເປັນໄປໄດ້:</b></p>" + "<ul>"
				+ "<li>Plugin <b>LuckPerms ບໍ່ໄດ້ຕິດຕັ້ງ</b> ຫຼື <b>ລົ້ມເຫຼວໃນການເລີ່ມຕົ້ນ</b>.</li>"
				+ "<li>Plugin ອື່ນພະຍາຍາມເຂົ້າເຖິງ LuckPerms ໄວເກີນໄປ ຫຼື ຜິດວິທີ.</li>" + "</ul>"
				+ "<p>ແນະນຳໃຫ້ <b>ກວດສອບ console</b> ໂດຍໃຊ້ລິ້ງເພື່ອລະບຸ "
				+ "plugin ທີ່ກຳລັງເອີ້ນ LuckPerms ແລະ ກວດສອບຄວາມເຂົ້າກັນໄດ້ຂອງມັນ.</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Shaderpack ຂອງ Iris ບໍ່ໄດ້ໂຫຼດ";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "ບໍ່ຮູ້ຈັກ" : shaderpack;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບ ຜິດພາດ ໃນການໂຫຼດ shaderpack ກັບ Iris/Oculus.</b>" + "<p><b>Shaderpack ທີ່ໄດ້ຮັບຜົນກະທົບ:</b> "
				+ nombre + "</p>" + "<p>Minecraft ບໍ່ສາມາດເປີດໄຟລ໌ shaderpack ໄດ້ (FileSystemNotFoundException).</p>"
				+ "<p><b>ວິທີແກ້ໄຂທີ່ເປັນໄປໄດ້:</b></p>" + "<ul>"
				+ "<li>ກວດສອບວ່າ shaderpack ຖືກຕິດຕັ້ງຢ່າງຖືກຕ້ອງໃນໂຟລເດີ <b>shaderpacks</b>.</li>"
				+ "<li>ດາວໂຫຼດ shaderpack ໃໝ່, ເນື່ອງຈາກໄຟລ໌ອາດຈະເສຍຫາຍ.</li>"
				+ "<li>ຖ້າບັນຫາຍັງຄົງຢູ່, ໃຫ້ລຶບໄຟລ໌ <b>config/iris.properties</b> ເພື່ອ reset ການຕັ້ງຄ່າຂອງ Iris.</li>"
				+ "</ul>" + "<p>ຫຼັງຈາກປ່ຽນແປງ, ໃຫ້ເລີ່ມເກມໃໝ່.</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "ບໍ່ສາມາດຂຽນໄຟລ໌ການຕັ້ງຄ່າໄດ້";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "ບໍ່ຮູ້ຈັກ" : ruta;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ເກີດ ຜິດພາດ ໃນການບັນທຶກໄຟລ໌ການຕັ້ງຄ່າ.</b>" + "<p><b>ໄຟລ໌ ທີ່ໄດ້ຮັບຜົນກະທົບ:</b> " + archivo + "</p>"
				+ "<p>Minecraft ບໍ່ສາມາດຂຽນໄຟລ໌ໂດຍໃຊ້ atomic write (REPLACE_ATOMIC).</p>"
				+ "<p><b>ສິ່ງນີ້ມັກເກີດຈາກ:</b></p>" + "<ul>"
				+ "<li>ສິດທິ (permissions) ບໍ່ຖືກຕ້ອງໃນໂຟລເດີ ຫຼື ໄຟລ໌.</li>" + "<li>ໄຟລ໌ຖືກຕັ້ງຄ່າເປັນ read-only.</li>"
				+ "<li>ໂປຣແກຣມອື່ນ (antivirus, backup, editor) ກຳລັງ block ໄຟລ໌ຢູ່.</li>" + "</ul>"
				+ "<p><b>ຄຳແນະນຳ:</b></p>" + "<ul>" + "<li>ກວດສອບວ່າທ່ານມີສິດຂຽນໃນໂຟລເດີ.</li>"
				+ "<li>ເອົາ attribute read-only ອອກຈາກໄຟລ໌.</li>" + "<li>ປິດໂປຣແກຣມທີ່ອາດຈະກຳລັງໃຊ້ໄຟລ໌ນັ້ນຢູ່.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "ການເຂົ້າເຖິງຖືກປະຕິເສດໃນການສ້າງສຳຮອງ";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "ບໍ່ຮູ້ຈັກ" : origen;
		String dst = backup == null || backup.isEmpty() ? "ບໍ່ຮູ້ຈັກ" : backup;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ເກີດ ຜິດພາດ ສິດທິ (permissions) ໃນລະຫວ່າງການສ້າງສຳຮອງໄຟລ໌ການຕັ້ງຄ່າ.</b>"
				+ "<p><b>ໄຟລ໌ ດັ້ງເດີມ:</b> " + src + "</p>" + "<p><b>ໄຟລ໌ ສຳຮອງ:</b> " + dst + "</p>"
				+ "<p>ລະບົບປະຕິບັດການໄດ້ block ການເຂົ້າເຖິງໃນລະຫວ່າງການບັນທຶກໄຟລ໌.</p>"
				+ "<p><b>ສິ່ງນີ້ມັກເກີດຈາກ:</b></p>" + "<ul>" + "<li>ສິດທິບໍ່ພຽງພໍໃນໂຟລເດີ.</li>"
				+ "<li>ໄຟລ໌ຖືກຕັ້ງຄ່າເປັນ read-only.</li>"
				+ "<li>ໂປຣແກຣມອື່ນ (antivirus, sync, editor) ກຳລັງໃຊ້ໄຟລ໌ຢູ່.</li>" + "</ul>" + "<p><b>ຄຳແນະນຳ:</b></p>"
				+ "<ul>" + "<li>ກວດສອບສິດທິຂອງໂຟລເດີ <b>config</b>.</li>"
				+ "<li>ປິດໂປຣແກຣມທີ່ອາດຈະກຳລັງເຂົ້າເຖິງໄຟລ໌.</li>"
				+ "<li>ລອງເລີ່ມ launcher ຫຼື Minecraft ໃນຖານະຜູ້ດູແລລະບົບ (administrator).</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		return "ເປີດໃຊ້ Console";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>ເຄື່ອງມື Debugging</b><br><br>"
				+ "ຢູ່ທີ່ນີ້ທ່ານສາມາດເປີດໃຊ້ຟັງຊັນຂັ້ນສູງເພື່ອ debug CrashDetector ແລະ ເກມຂອງທ່ານ.<br><br>"
				+ "ແນະນຳໃຫ້ເປີດໃຊ້ console ພັດທະນາເພື່ອຮັບຂໍ້ມູນລະອຽດ, traces ແລະ ການວິນິດໄຍໃນລະຫວ່າງການວິເຄາະ.<br><br>"
				+ "ຖ້າທ່ານຕ້ອງການທົດສອບ server multiplayer ໃນໂໝດ online, ອາດຈະຈຳເປັນຕ້ອງອະນຸຍາດໃຫ້ໂອນ token ການເຂົ້າເຖິງໄປຍັງ process ຂອງ CrashDetector ຈາກການຕັ້ງຄ່າຄວາມເປັນສ່ວນຕົວ. "
				+ "ສິ່ງນີ້ໂດຍທົ່ວໄປແລ້ວ <b>ບໍ່ແນະນຳ</b> ໃນກໍລະນີອື່ນໆ.<br><br>"
				+ "ຄຳແນະນຳເຕັມຮູບແບບ: <a href='https://example.com'>ລິ້ງ!</a>";// TODO
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບຄວາມບໍ່ເຂົ້າກັນລະຫວ່າງ Simple Clouds ແລະ shaders.</b>"
				+ "<p>Simple Clouds ບໍ່ເຂົ້າກັນກັບ mods ເງົາ (Iris/Oculus) ເມື່ອຕິດຕັ້ງ Distant Horizons.</p>"
				+ "<p><b>ຕົວເລືອກທີ່ແນະນຳ:</b></p>" + "<ul>"
				+ "<li>ລຶບ <b>Simple Clouds</b> ຖ້າທ່ານຕ້ອງການໃຊ້ shaders.</li>"
				+ "<li>ຫຼື, ຖອນການຕິດຕັ້ງ <b>Iris ຫຼື Oculus</b> ຖ້າທ່ານຕ້ອງການຮັກສາ Simple Clouds.</li>" + "</ul>"
				+ "<p>ຂໍ້ຈຳກັດນີ້ມາຈາກ mod Simple Clouds ເອງ ແລະ ບໍ່ສາມາດແກ້ໄຂໄດ້ໂດຍບໍ່ດັດແປງ code ຂອງມັນ.</p>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "ຄວາມບໍ່ເຂົ້າກັນ: Simple Clouds vs Shaders";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "ສີປຸ່ມແຖບດ້ານຂ້າງ";
	}

	@Override
	public String profilerTitulo() {
		return "Profiler";
	}

	@Override
	public String profilerDescripcion() {
		return "ເຄື່ອງມືວິເຄາະປະສິດທິພາບໂດຍອີງໃສ່ instrumentation ແລະ sampling.";
	}

	@Override
	public String profilerIniciar() {
		return "ເລີ່ມ";
	}

	@Override
	public String profilerDetener() {
		return "ຢຸດ";
	}

	@Override
	public String profilerLimpiar() {
		return "ລ້າງ";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "Profiler ເລີ່ມແລ້ວ.";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "Profiler ຢຸດແລ້ວ.";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "ໄດ້ຮັບ sample ຈາກ thread: " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "Sampling stacks ເປັນໄລຍະເພື່ອກວດຫາ bottlenecks ແລະ ການ block.";
	}

	@Override
	public String entrarAlJuego() {
		return "ເຂົ້າສູ່ເກມ";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ຜິດພາດໃນເສັ້ນທາງລະບົບທີ່ພົບ.</b>"
				+ "<p>Minecraft ບໍ່ສາມາດເລີ່ມຕົ້ນໄດ້ເນື່ອງຈາກມີຕົວອັກສອນທີ່ຜິດກົດໝາຍໃນຊື່ໂຟລເດີ.</p>"
				+ "<p>ລະບົບພົບຕົວອັກສອນທີ່ບໍ່ຖືກຕ້ອງໃນເສັ້ນທາງ (ຕົວຢ່າງ: ':' ຫຼື ສັນຍາລັກພິເສດອື່ນໆ).</p>"
				+ "<p><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b></p>" + "<ul>" + "<li>ປ່ຽນຊື່ໂຟລເດີ instance ຫຼື profile.</li>"
				+ "<li>ໃຊ້ສະເພາະຕົວອັກສອນ ASCII ພື້ນຖານ (A-Z, a-z, 0-9).</li>"
				+ "<li>ຢ່າໃຊ້ສັນຍາລັກເນັ້ນສຽງ, ສັນຍາລັກພິເສດ, ຊ່ອງວ່າງທີ່ມີບັນຫາ ຫຼື emojis.</li>" + "</ul>"
				+ "<p>ຕົວຢ່າງທີ່ຖືກຕ້ອງ: <b>MiInstancia1</b></p>"
				+ "<p>ຕົວຢ່າງທີ່ຜິດ: <b>Instancia🔥</b> ຫຼື <b>Instancia:Mod</b></p>";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "ເສັ້ນທາງບໍ່ຖືກຕ້ອງ: ຕົວອັກສອນທີ່ບໍ່ອະນຸຍາດ";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບການລົ້ມເຫຼວໃນ shaders ຂອງ Twilight Forest ກັບ GPU Intel.</b>"
				+ "<p>ຜິດພາດ ນີ້ກ່ຽວຂ້ອງກັບ drivers ກຣາຟິກຂອງ Intel ໃນລະຫວ່າງການໂຫຼດ shaders ຂອງ mod Twilight Forest.</p>"
				+ "<p>ການລົ້ມເຫຼວເກີດຂຶ້ນພາຍໃນ driver (igxelpicd64) ແລະ ບໍ່ແມ່ນບັນຫາໂດຍກົງຂອງ mod ຫຼື Minecraft.</p>"
				+ "<p><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b></p>" + "<ul>" + "<li>ອັບເດດ drivers Intel ເປັນເວີຊັນລ່າສຸດທີ່ມີ.</li>"
				+ "<li>ລອງໃຊ້ເວີຊັນ 31.0.101.8331 ຫຼື 31.0.101.8247 WHQL ໂດຍສະເພາະ, ເຊິ່ງຕາມຄຳເຫັນແລ້ວບໍ່ມີບັນຫານີ້.</li>"
				+ "</ul>" + "<p>ການຕິດຕາມບັນຫາຢ່າງເປັນທາງການ:</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>ໝາຍເຫດ:</b> GPUs Intel ຮຸ່ນເກົ່າບາງຕົວອາດຈະບໍ່ໄດ້ຮັບການອັບເດດທີ່ແກ້ໄຂບັນຫານີ້.</p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "Crash: Twilight Forest + Drivers Intel";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "Forge: ຜູ້ໃຫ້ບໍລິການພາສາບໍ່ສາມາດໂຫຼດໄດ້";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "ຜູ້ໃຫ້ບໍລິການບໍ່ຮູ້ຈັກ" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Forge ບໍ່ສາມາດໂຫຼດຜູ້ໃຫ້ບໍລິການພາສາໄດ້.</b>"
				+ "<p>ເກີດ ຜິດພາດ ໃນລະຫວ່າງການ initialize IModLanguageProvider.</p>"
				+ "<p><b>ຜູ້ໃຫ້ບໍລິການທີ່ລົ້ມເຫຼວ:</b> " + providerTexto + "</p>" + "<p>ບັນຫານີ້ມັກເກີດຂຶ້ນເມື່ອ:</p>"
				+ "<ul>" + "<li>ຂາດ dependency ທີ່ຈຳເປັນ (ຕົວຢ່າງ: Kotlin for Forge).</li>"
				+ "<li>ເວີຊັນຂອງ mod ບໍ່ເຂົ້າກັນກັບເວີຊັນ Forge ຂອງທ່ານ.</li>" + "<li>ໄຟລ໌ mod ເສຍຫາຍ.</li>" + "</ul>"
				+ "<p><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b></p>" + "<ul>" + "<li>ຕິດຕັ້ງ mod ນັ້ນໃໝ່.</li>"
				+ "<li>ກວດສອບວ່າ dependencies ທັງໝົດຖືກຕິດຕັ້ງແລ້ວ.</li>"
				+ "<li>ໃຫ້ແນ່ໃຈວ່າໃຊ້ເວີຊັນທີ່ເຂົ້າກັນໄດ້ກັບ Forge ປະຈຸບັນຂອງທ່ານ.</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "Crash: Lets Do Compat (RecipeManager intercept)";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບ Crash ໃນ Lets Do Compat (interceptApply).</b>" + "<p>ຜິດພາດ ເກີດຂຶ້ນພາຍໃນການ transform method "
				+ "<b>RecipeManager.interceptApply</b> ທີ່ເຮັດໂດຍ Lets Do Compat.</p>" + "<p>ສິ່ງນີ້ມັກຊີ້ບອກວ່າ:</p>"
				+ "<ul>" + "<li>ຄວາມບໍ່ເຂົ້າກັນລະຫວ່າງ Lets Do Compat ແລະ mod ອື່ນທີ່ດັດແປງ recipes.</li>"
				+ "<li>ເວີຊັນບໍ່ຖືກຕ້ອງສຳລັບເວີຊັນ Minecraft ຂອງທ່ານ.</li>"
				+ "<li>ຂໍ້ຂັດແຍ່ງລະຫວ່າງ transformers (mixin/coremod).</li>" + "</ul>"
				+ "<p><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b></p>" + "<ul>"
				+ "<li>ລອງ ລົບ Lets Do Compat ຊົ່ວຄາວເພື່ອຢັ້ງຢືນຂໍ້ຂັດແຍ່ງ.</li>" + "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI: ລົ້ມເຫຼວໃນ Item Group (plugin ບໍ່ເຂົ້າກັນ)";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "Plugin ບໍ່ຮູ້ຈັກ" : String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "JEI ພົບການລົ້ມເຫຼວໃນການສ້າງກຸ່ມ items.</b>"
				+ "<p>ໜຶ່ງ ຫຼື ຫຼາຍ plugins ເຮັດໃຫ້ເກີດ ຜິດພາດ ໃນຂະນະທີ່ JEI ສ້າງລາຍການ ingredients.</p>"
				+ "<p><b>ກຸ່ມ/Plugins ທີ່ໄດ້ຮັບຜົນກະທົບ:</b> " + listaPlugins + "</p>"
				+ "<p>ບັນຫານີ້ເປັນເລື່ອງທຳມະດາເມື່ອ:</p>" + "<ul>"
				+ "<li>Plugin ຂອງ JEI ຖືກ implement ຜິດ ຫຼື ຕົກຍຸກ.</li>"
				+ "<li>ມີຄວາມບໍ່ເຂົ້າກັນກັບເວີຊັນ JEI ປະຈຸບັນ.</li>"
				+ "<li>ໃຊ້ Fabric API ແລະ ມີ mod ບາງຕົວລົງທະບຽນ Item Group ຜິດວິທີ.</li>" + "</ul>"
				+ "<p><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b></p>" + "<ul>" + "<li>ອັບເດດ JEI ແລະ mods ທີ່ລະບຸໄວ້.</li>"
				+ "<li>ລຶບ plugins ທີ່ໄດ້ຮັບຜົນກະທົບຊົ່ວຄາວເພື່ອຢັ້ງຢືນຂໍ້ຂັດແຍ່ງ.</li>"
				+ "<li>ລາຍງານ ຜິດພາດ ໃຫ້ຜູ້ພັດທະນາ mod ທີ່ກ່ຽວຂ້ອງ.</li>" + "</ul>"
				+ "<p>Items ໃນກຸ່ມເຫຼົ່ານີ້ຈະບໍ່ປາກົດໃນລາຍການ ingredients ຈົນກວ່າບັນຫາຈະຖືກແກ້ໄຂ.</p>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "ເວີຊັນ mod ບໍ່ຖືກຕ້ອງ (SemVer)";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "ບໍ່ຮູ້ຈັກ" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "ບໍ່ສາມາດລະບຸຕຳແໜ່ງ mod ໄດ້" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບເວີຊັນ mod ທີ່ບໍ່ຖືກຕ້ອງ.</b>" + "<p>ເວີຊັນ <b>" + v
				+ "</b> ບໍ່ສອດຄ່ອງກັບຮູບແບບ SemVer ທີ່ຖືກຕ້ອງ.</p>"
				+ "<p>ຜິດພາດ ຊີ້ບອກວ່າມີ pre-release ວ່າງເປົ່າ (ລົງທ້າຍດ້ວຍ '+').</p>"
				+ "<p><b>Mod ທີ່ເປັນສາເຫດ:</b><br>" + u + "</p>" + "<p><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b></p>" + "<ul>"
				+ "<li>ແກ້ໄຂໄຟລ໌ mod ແລະ ປັບເວີຊັນໃຫ້ຖືກຕ້ອງ.</li>"
				+ "<li>ລຶບ '+' ທ້າຍສຸດອອກຖ້າບໍ່ມີ metadata ຕໍ່ທ້າຍ.</li>"
				+ "<li>ອັບເດດ mod ເປັນເວີຊັນທີ່ແກ້ໄຂແລ້ວ.</li>" + "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS: ການເຂົ້າເຖິງຜິດກົດໝາຍລະຫວ່າງ modules";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບການເຂົ້າເຖິງຜິດກົດໝາຍລະຫວ່າງ modules (JPMS).</b>"
				+ "<p>ລະບົບ modules ຂອງ Java (JPMS) ໄດ້ block ການເຂົ້າເຖິງລະຫວ່າງ classes.</p>"
				+ "<p><b>Class ທີ່ພະຍາຍາມເຂົ້າເຖິງ:</b><br>" + claseOrigen + " (module: " + moduloOrigen + ")</p>"
				+ "<p><b>Class ທີ່ຖືກ block:</b><br>" + claseDestino + " (module: " + moduloDestino + ")</p>"
				+ "<p>ຜິດພາດ ປະເພດນີ້ເກີດຂຶ້ນເມື່ອ mod ບໍ່ໄດ້ລະບຸ "
				+ "exports ຫຼື opens ຢ່າງຖືກຕ້ອງໃນ module-info.java.</p>" + "<p><b>ສາເຫດທີ່ເປັນໄປໄດ້:</b></p>" + "<ul>"
				+ "<li>Module ບໍ່ໄດ້ export package ທີ່ຈຳເປັນ.</li>"
				+ "<li>ຂາດ directive <b>opens</b> ສຳລັບ reflection.</li>"
				+ "<li>Mod ບໍ່ໄດ້ຕັ້ງຄ່າຢ່າງຖືກຕ້ອງສຳລັບ JPMS.</li>" + "</ul>"
				+ "<p>ບັນຫານີ້ຕ້ອງໄດ້ຮັບການແກ້ໄຂໂດຍຜູ້ພັດທະນາ mod.</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin: class ຢູ່ຜິດທີ່ໃນ package mixin";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Class ຖືກວາງໄວ້ຜິດທີ່ໃນ package Mixin.</b>"
				+ "<p>Class ປົກກະຕິຖືກວາງໄວ້ພາຍໃນ package ທີ່ຖືກປະກາດວ່າເປັນ mixin.</p>"
				+ "<p><b>Class ທີ່ມີຂໍ້ຂັດແຍ່ງ:</b><br>" + clase + "</p>" + "<p><b>Package mixin ທີ່ປະກາດ:</b><br>"
				+ paquete + "</p>" + "<p><b>ໄຟລ໌ mixins ທີ່ເປັນສາເຫດ:</b><br>" + archivoMixin + "</p>"
				+ "<p>Classes ປົກກະຕິບໍ່ຄວນຢູ່ພາຍໃນ package ທີ່ກຳນົດໄວ້ໃນ mixins.json.</p>"
				+ "<p>ມີພຽງແຕ່ classes ທີ່ມີ annotation ເປັນ mixin ເທົ່ານັ້ນທີ່ຄວນມີຢູ່ໃນ package ນັ້ນ.</p>"
				+ "<p><b>ວິທີແກ້ໄຂສຳລັບ dev:</b> ຍ້າຍ classes ປົກກະຕິອອກຈາກ package mixin "
				+ "ຫຼື ແກ້ໄຂການຕັ້ງຄ່າໃນໄຟລ໌ mixins.json.</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບບັນຫາກັບ drivers GPU Matrox.</b>"
				+ "<p>Log ຊີ້ບອກວ່າການລົ້ມເຫຼວເກີດຂຶ້ນພາຍໃນ library ຂອງ driver Matrox.</p>"
				+ "<p>GPUs Matrox (ໂດຍສະເພາະຮຸ່ນ G200/G400 ທີ່ໃຊ້ໃນ servers) "
				+ "ບໍ່ໄດ້ຖືກອອກແບບມາສຳລັບການ render 3D ສະໄໝໃໝ່ ແລະ ອາດຈະບໍ່ຮອງຮັບ "
				+ "ເວີຊັນ OpenGL ທີ່ Minecraft ຕ້ອງການ.</p>" + "<p><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b></p>" + "<ul>"
				+ "<li>ອັບເດດ driver Matrox ເປັນເວີຊັນລ່າສຸດທີ່ມີ.</li>"
				+ "<li>ຕິດຕັ້ງ drivers ທາງການແທນທີ່ຈະໃຊ້ drivers ທົ່ວໄປຂອງລະບົບ.</li>"
				+ "<li>ຖ້າ hardware ເກົ່າ, ໃຫ້ໃຊ້ GPU ທີ່ຮອງຮັບ OpenGL 3.2 ຫຼື ສູງກວ່າ.</li>" + "</ul>"
				+ "<p>ໃນ servers, GPUs ເຫຼົ່ານີ້ມັກຈະໃຊ້ສຳລັບ output video ພື້ນຖານເທົ່ານັ້ນ "
				+ "ບໍ່ແມ່ນສຳລັບ applications 3D ເຊັ່ນ Minecraft.</p>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod: GPU ບໍ່ເຂົ້າກັນ";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod ບໍ່ສາມາດກວດພົບ GPU ທີ່ເຂົ້າກັນໄດ້.</b>"
				+ "<p>Mod <b>VulkanMod</b> ພະຍາຍາມເລີ່ມຕົ້ນໂດຍໃຊ້ Vulkan ແຕ່ບໍ່ພົບ GPU ທີ່ຮອງຮັບ Vulkan ຢ່າງເໝາະສົມ.</p>"
				+ "<p>ສິ່ງນີ້ມັກເກີດຂຶ້ນເມື່ອ:</p>" + "<ul>" + "<li>GPU ບໍ່ຮອງຮັບ Vulkan.</li>"
				+ "<li>Drivers ຂອງ GPU ຕົກຍຸກ ຫຼື ຂາດຫາຍໄປ.</li>"
				+ "<li>ກຳລັງໃຊ້ adapter ກຣາຟິກຜິດ (ຕົວຢ່າງ: GPU integrated ແທນທີ່ຈະເປັນ dedicated).</li>" + "</ul>"
				+ "<p><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b></p>" + "<ul>" + "<li>ອັບເດດ drivers ຂອງ GPU ເປັນເວີຊັນລ່າສຸດ.</li>"
				+ "<li>ກວດສອບວ່າ GPU ຂອງທ່ານຮອງຮັບ Vulkan.</li>"
				+ "<li>ຖ້າມີສອງ GPUs, ບັງຄັບໃຫ້ໃຊ້ GPU dedicated ສຳລັບ Minecraft.</li>"
				+ "<li>ຖ້າ GPU ຂອງທ່ານບໍ່ຮອງຮັບ Vulkan, ໃຫ້ຖອນການຕິດຕັ້ງ VulkanMod.</li>" + "</ul>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "RenderType ບໍ່ຖືກຕ້ອງສຳລັບ outline";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		String base = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod ພະຍາຍາມໃຊ້ outline ກັບ RenderType ທີ່ບໍ່ເຂົ້າກັນ.</b>" + "<p>ຜິດພາດ ແມ່ນ:</p>"
				+ "<code>Can't render an outline for this rendertype!</code>";

		if (enchantDetectado) {
			base += "<p><b>ພົບ mod enchant-outline / better-enchants ໃນລາຍງານ.</b></p>"
					+ "<p>Mod ນີ້ເປັນທີ່ຮູ້ຈັກວ່າເຮັດໃຫ້ເກີດບັນຫານີ້ໃນເວີຊັນ Minecraft ລ່າສຸດ.</p>"
					+ "<p><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b> ລົບ ຫຼື ອັບເດດ enchant-outline.</p>";
		} else {
			base += "<p>ບັນຫານີ້ມັກກ່ຽວຂ້ອງກັບ mods ທີ່ດັດແປງການ render "
					+ "(Entity Model Features, Entity Texture Features, Visuality ຫຼື ຂໍ້ຂັດແຍ່ງກັບ Sodium).</p>"
					+ "<p><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b> ອັບເດດ ຫຼື ປິດການເຮັດວຽກ mods render ທີລະຕົວ.</p>";
		}

		return base;
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – DimensionalInventory ເປັນ null";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG ພະຍາຍາມບັນທຶກ DimensionalInventory ທີ່ເປັນ null.</b>" + "<p>ເກມໄດ້ launch:</p>"
				+ "<code>Cannot invoke DimensionalInventory.saveInventory(...) because \"d\" is null</code>"
				+ "<p>ນີ້ແມ່ນ bug ທີ່ຮູ້ຈັກຂອງ DivineRPG ທີ່ກ່ຽວຂ້ອງກັບລະບົບ inventory Vethean.</p>"
				+ "<p><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b></p>" + "<ul>" + "<li>ໄປທີ່ໄຟລ໌ການຕັ້ງຄ່າຂອງ DivineRPG.</li>"
				+ "<li>ຕັ້ງຄ່າ <code>saferVetheanInventory=true</code></li>" + "<li>ບັນທຶກ ແລະ ເລີ່ມເກມໃໝ່.</li>"
				+ "</ul>" + "<p>ຍັງແນະນຳໃຫ້ອັບເດດ DivineRPG ຖ້າມີເວີຊັນລ່າສຸດວາງຈຳໜ່າຍ.</p>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "ຂໍ້ຂັດແຍ່ງໃນ Render Pass";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງໃນລະບົບ rendering.</b>" + "<p>ເກມໄດ້ launch:</p>"
				+ "<code>Close the existing render pass before performing additional commands</code>"
				+ "<p>ຜິດພາດ ນີ້ມັກກ່ຽວຂ້ອງກັບຂໍ້ຂັດແຍ່ງລະຫວ່າງ mods render "
				+ "ເຊັ່ນ Iris, OptiFine, VulkanMod ຫຼື mods ອື່ນໆທີ່ດັດແປງ graphics pipeline.</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "ການລົ້ມເຫຼວພາຍໃນຂອງ Feather Client";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບການລົ້ມເຫຼວພາຍໃນຂອງ Feather Client.</b>" + "<p>ເກມໄດ້ launch:</p>"
				+ "<code>NoClassDefFoundError: feather/lib/sentry/Sentry</code>"
				+ "<p>ຜິດພາດ ນີ້ເກີດຈາກ Feather Client.</p>" + "<p>Feather ບໍ່ໄດ້ຮັບການແນະນຳເນື່ອງຈາກ:</p>" + "<ul>"
				+ "<li>ໃຊ້ເວີຊັນທີ່ເປັນ proprietary ແລະ ຖືກດັດແປງຂອງ mods ຍອດນິຍົມ.</li>"
				+ "<li>ທຳລາຍຄວາມເຂົ້າກັນໄດ້ກັບ Fabric ມາດຕະຖານ.</li>"
				+ "<li>ເຮັດວຽກຄືກັບ modpack ທີ່ຈັດແຈງມາແລ້ວພ້ອມການດັດແປງພາຍໃນ.</li>"
				+ "<li>ມັກຈະເຮັດໃຫ້ເກີດຂໍ້ຂັດແຍ່ງກັບ Sodium ແລະ mods ປັບປຸງປະສິດທິພາບອື່ນໆ.</li>" + "</ul>"
				+ "<p>ແນະນຳໃຫ້ໃຊ້ການຕິດຕັ້ງ Fabric ມາດຕະຖານແທນ Feather.</p>";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "ຂໍ້ຂັດແຍ່ງ Iris + Flywheel (Create 6)";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງລະຫວ່າງ Iris ແລະ Flywheel ໃນ Create 6.</b>" + "<p>ເກມໄດ້ launch:</p>"
				+ "<code>NoSuchFieldError: TESSELATION_SHADERS</code>"
				+ "<p>ພົບການອ້າງອີງພາຍໃນ <code>$irisflw$</code>, "
				+ "ເຊິ່ງຊີ້ບອກເຖິງຂໍ້ຂັດແຍ່ງລະຫວ່າງ Iris ແລະ Flywheel.</p>"
				+ "<p>Iris Flywheel 2.0 ສຳລັບ Create 6 ຮອງຮັບຢ່າງເປັນທາງການສະເພາະ NeoForge ເທົ່ານັ້ນ.</p>"
				+ "<p>ຖ້າທ່ານໃຊ້ Forge ຫຼື Fabric, ການປະສົມນີ້ອາດຈະເຮັດໃຫ້ເກີດ ຜິດພາດ ນີ້.</p>";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "ບໍ່ພົບໂມເດລ GeckoLib";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod ບໍ່ສາມາດຊອກຫາໂມເດລ GeckoLib ໄດ້.</b>" + "<p>ໂມເດລທີ່ໄດ້ຮັບຜົນກະທົບ:</p>" + "<code>" + modelo
				+ "</code>" + "<p>ຜິດພາດ ນີ້ເກີດຂຶ້ນເມື່ອໄຟລ໌ <code>.geo.json</code> ບໍ່ມີຢູ່ "
				+ "ຫຼື ຕັ້ງຄ່າຜິດພາດພາຍໃນ mod.</p>" + "<p>ສາເຫດທີ່ເປັນໄປໄດ້:</p>" + "<ul>"
				+ "<li>ໂມເດລຖືກລຶບແລ້ວແຕ່ຍັງຖືກອ້າງອີງຢູ່.</li>" + "<li>ຜິດພາດໃນເສັ້ນທາງໄຟລ໌.</li>"
				+ "<li>ຂາດ ໄຟລ໌ ພາຍໃນ JAR.</li>" + "<li>ເວີຊັນ mod ບໍ່ເຂົ້າກັນ.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – ການເຄື່ອນໄຫວບໍ່ມີຢູ່";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon ພະຍາຍາມຫຼິ້ນການເຄື່ອນໄຫວທີ່ບໍ່ມີຢູ່.</b>" + "<p>ການເຄື່ອນໄຫວ:</p>" + "<code>" + animacion
				+ "</code>" + "<p>ກຸ່ມ:</p>" + "<code>" + grupo + "</code>" + "<p>ຜິດພາດ ນີ້ມັກເກີດຂຶ້ນເມື່ອ:</p>"
				+ "<ul>" + "<li>ປະສົມເວີຊັນ Cobblemon ທີ່ບໍ່ເຂົ້າກັນ.</li>"
				+ "<li>Addon ບໍ່ກົງກັບເວີຊັນທີ່ຕິດຕັ້ງ.</li>" + "<li>ຂາດ resources ຫຼື ການເຄື່ອນໄຫວພາຍໃນ.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "ການລົ້ມເຫຼວພາຍໃນຂອງ Lunar Client";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບການລົ້ມເຫຼວພາຍໃນຂອງ Lunar Client.</b>"
				+ "<p>ຜິດພາດ ມາຈາກ client Lunar ເອງ ບໍ່ແມ່ນຈາກເກມພື້ນຖານ ຫຼື mods.</p>"
				+ "<p>Lunar Client ໃຊ້ການດັດແປງພາຍໃນ ແລະ ແບບ custom ທີ່ອາດຈະ "
				+ "ເຮັດໃຫ້ເກີດຄວາມບໍ່ເຂົ້າກັນກັບ mods ຫຼື ການຕັ້ງຄ່າສະເພາະ.</p>"
				+ "<p>ແນະນຳໃຫ້ລອງໃຊ້ການຕິດຕັ້ງ Minecraft ມາດຕະຖານ " + "ເພື່ອຕັດບັນຫາທີ່ເກີດຈາກ client ອອກ.</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "ການເຂົ້າເຖິງ method ຫຼື field ຜິດກົດໝາຍ";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod ພະຍາຍາມເຂົ້າເຖິງ method ຫຼື field ທີ່ປ້ອງກັນ/ເປັນສ່ວນຕົວ.</b>" + "<p>Class ທີ່ເປັນສາເຫດ:</p>"
				+ "<code>" + clase + "</code>" + "<p>Member ທີ່ຖືກເຂົ້າເຖິງ:</p>" + "<code>" + miembro + "</code>"
				+ "<p>ຜິດພາດ ນີ້ມັກເກີດຂຶ້ນເມື່ອ:</p>" + "<ul>" + "<li>Mod ຖືກ compile ສຳລັບເວີຊັນ Minecraft ອື່ນ.</li>"
				+ "<li>ມີການປະສົມ mappings ທີ່ບໍ່ເຂົ້າກັນ.</li>" + "<li>Mod ຕົກຍຸກ.</li>"
				+ "<li>ໃຊ້ loader ຜິດ (Fabric/Forge/NeoForge).</li>" + "</ul>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "ຜິດພາດໃນການໂຫຼດ datapack/resourcepack";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Datapack ຫຼື resourcepack ລົ້ມເຫຼວໃນການໂຫຼດ.</b>" + "<p>ໄຟລ໌ ທີ່ມີບັນຫາ:</p>" + "<code>" + archivo
				+ "</code>" + "<p>Pack:</p>" + "<code>" + pack + "</code>"
				+ "<p>ເກມບໍ່ສາມາດ parse ໄຟລ໌ນີ້ໄດ້ ແລະ ເຮັດໃຫ້ເກີດ ຜິດພາດ ໃນການໂຫຼດ registry.</p>"
				+ "<p>ບັນຫານີ້ມັກເກີດຈາກ:</p>" + "<ul>" + "<li>JSON ຜິດຮູບແບບ.</li>"
				+ "<li>ເວີຊັນ pack ບໍ່ເຂົ້າກັນ.</li>" + "<li>Pack ຕົກຍຸກສຳລັບເວີຊັນເກມປະຈຸບັນ.</li>"
				+ "<li>ຂໍ້ຂັດແຍ່ງລະຫວ່າງ datapacks.</li>" + "</ul>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "ຜິດພາດໃນການ compile shader";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ການ compile shader ລົ້ມເຫຼວ.</b>" + "<p>ເກມບໍ່ສາມາດ compile ໜຶ່ງໃນ shaders ທີ່ກຳລັງໃຊ້ຢູ່ໄດ້.</p>"
				+ "<p>ບັນຫານີ້ມັກກ່ຽວຂ້ອງກັບ Sodium, Iris ຫຼື shaderpacks ທີ່ບໍ່ເຂົ້າກັນ.</p>" + "<p>ຄຳແນະນຳ:</p>"
				+ "<ul>" + "<li>ລອງໃຊ້ shader ອື່ນ.</li>" + "<li>ປິດການໃຊ້ shaders ຊົ່ວຄາວ.</li>"
				+ "<li>ອັບເດດ drivers ຂອງ GPU.</li>" + "<li>ຖ້າບັນຫາຍັງຄົງຢູ່, ລອງເລີ່ມເກມໂດຍບໍ່ມີ Sodium.</li>"
				+ "</ul>";
	}

	public String nombreErrorCreacionModelo() {
		return "ຜິດພາດໃນການສ້າງ ຫຼື ໂຫຼດໂມເດລ";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>ເກີດ ຜິດພາດ ໃນຂະນະທີ່ພະຍາຍາມສ້າງ ຫຼື ໂຫຼດໂມເດລ Minecraft.</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>ໂມເດລທີ່ໄດ້ຮັບຜົນກະທົບ: <code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>ຜິດພາດ ປະເພດນີ້ມັກເກີດຂຶ້ນເມື່ອ:</p>");
		sb.append("<ul>");
		sb.append("<li>Mod ມີໂມເດລທີ່ຕັ້ງຄ່າຜິດ.</li>");
		sb.append("<li>ໂມເດລ JSON ເສຍຫາຍ ຫຼື ບໍ່ສົມບູນ.</li>");
		sb.append("<li>ມີຂໍ້ຂັດແຍ່ງລະຫວ່າງ mods ທີ່ດັດແປງໂມເດລ ຫຼື ການ render.</li>");
		sb.append("<li>Resource pack ຫຼື datapack ມີໂມເດລທີ່ບໍ່ເຂົ້າກັນ.</li>");
		sb.append("</ul>");
		sb.append("<p>ລອງລະບຸວ່າ mod ຫຼື resource pack ໃດເປັນຜູ້ໃຫ້ໂມເດລທີ່ລະບຸໄວ້.</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>ສາເຫດທີ່ເປັນໄປໄດ້ທີ່ພົບ:</b></p>");
		sb.append("<p>ພົບກິດຈະກຳຂອງ mod <b>Cooler Animations</b> ໃນ log.</p>");
		sb.append(
				"<p>Mod ນີ້ດັດແປງລະບົບການເຄື່ອນໄຫວ ແລະ ໂມເດລ, ແລະ ໃນບາງກໍລະນີອາດຈະເຮັດໃຫ້ເກີດ ຜິດພາດ ໃນການໂຫຼດໂມເດລ.</p>");
		sb.append("<p>ຖ້າບັນຫາຍັງຄົງຢູ່, ລອງເລີ່ມເກມໂດຍບໍ່ມີ Cooler Animations ເພື່ອກວດສອບວ່າ ຜິດພາດ ຫາຍໄປຫຼືບໍ່.</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "ບັນຫາກັບ Starlight";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບ ຜິດພາດ ທີ່ກ່ຽວຂ້ອງກັບ Starlight.</b>"
				+ "<p>ຜິດພາດ ເກີດຂຶ້ນພາຍໃນ <code>BlockStarLightEngine.initNibble</code>.</p>"
				+ "<p>ສິ່ງນີ້ຊີ້ບອກເຖິງການລົ້ມເຫຼວໃນ engine ແສງສະຫວ່າງຂອງ mod <b>Starlight</b>.</p>"
				+ "<p>Starlight ແມ່ນ mod ທີ່ດັດແປງລະບົບແສງສະຫວ່າງຂອງ Minecraft ຢ່າງສົມບູນ ແລະ ເປັນທີ່ຮູ້ຈັກວ່າເຮັດໃຫ້ເກີດບັນຫາຕ່າງໆໃນບາງສະພາບແວດລ້ອມ mods.</p>"
				+ "<p>ນີ້ແມ່ນພຽງແຕ່ໜຶ່ງໃນຫຼາຍໆ ຜິດພາດ ທີ່ຮູ້ຈັກກັນດີທີ່ກ່ຽວຂ້ອງກັບ Starlight.</p>"
				+ "<p>ຖ້າບັນຫາຍັງຄົງຢູ່, ລອງເລີ່ມເກມໂດຍບໍ່ມີ Starlight.</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "ບັນຫາກັບ AAAParticles / Effekseer";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບ crash native ທີ່ກ່ຽວຂ້ອງກັບ Effekseer.</b>"
				+ "<p>ຜິດພາດ ເກີດຂຶ້ນພາຍໃນ library native <code>EffekseerNativeForJava</code>.</p>"
				+ "<p>Library ນີ້ຖືກໃຊ້ໂດຍ mod <b>AAAParticles</b> ທີ່ພັດທະນາໂດຍ ChloePrime.</p>"
				+ "<p>Crashes ໃນ libraries native ມັກຊີ້ບອກເຖິງບັນຫາພາຍໃນ mod ເອງ ຫຼື ໃນ dependencies native ຂອງມັນ.</p>"
				+ "<p>ຖ້າບັນຫາຍັງຄົງຢູ່, ລອງເລີ່ມເກມໂດຍບໍ່ມີ AAAParticles.</p>";
	}

	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບ crash native ຂອງ Java Runtime Environment (JVM).</b>"
				+ "<p>ຜິດພາດ ປະເພດນີ້ເກີດຂຶ້ນພາຍໃນ Java Virtual Machine ເອງ (ຕົວຢ່າງ: ໃນ <code>jvm.dll</code>, <code>libjvm.so</code>, ແລະອື່ນໆ), "
				+ "ແລະ ບໍ່ຈຳເປັນຕ້ອງເກີດຈາກ mod.</p>"
				+ "<p>ເຖິງວ່າໃນກໍລະນີຫາຍາກອາດເກີດຈາກ mods ທີ່ໃຊ້ libraries native ທີ່ບໍ່ເຂົ້າກັນ, "
				+ "<b>ມັນມີໂອກາດສູງກວ່າທີ່ຈະເກີດຈາກ JVM ທີ່ມີບັນຫາ, ເສຍຫາຍ ຫຼື ຕົກຍຸກ</b>.</p>"
				+ "<p>ສິ່ງນີ້ເປັນເລື່ອງທຳມະດາໂດຍສະເພາະຖ້າທ່ານໃຊ້ build ເກົ່າ ຫຼື ບໍ່ເປັນທາງການຂອງ Java (ຕົວຢ່າງ: builds ຊຸມຊົນທີ່ບໍ່ມີການສະໜັບສະໜູນ).</p>"
				+ "<p><b>ແນະນຳໃຫ້ໃຊ້ JVM ທີ່ໜ້າເຊື່ອຖື ແລະ ມີການບຳລຸງຮັກສາ:</b></p>" + "<ul>"
				+ "<li><b>Red Hat Build of OpenJDK</b> (ໝັ້ນຄົງ, ທົດສອບມາດີ, ເໝາະສຳລັບ Windows/Linux)</li>"
				+ "<li><b>OpenLogic OpenJDK</b> (ຮອງຮັບຫຼາຍແພລດຟອມ, ລວມທັງ macOS Intel)</li>"
				+ "<li><b>Azul Zulu</b> (ຮັບຮອງ, ມີການສະໜັບສະໜູນ LTS ຟຣີ)</li>"
				+ "<li><b>Oracle JDK</b> (ທາງການ, ມີການອັບເດດສະໝ່ຳສະເໝີ)</li>" + "</ul>"
				+ "<p>ຫຼີກລ່ຽງ builds ທີ່ບໍ່ຮູ້ຈັກ, ດັດແປງເອງ ຫຼື ເກົ່າຫຼາຍ, ເພາະອາດມີ ຜິດພາດ ວິກິດໃນ engine ຂອງ JVM.</p>"
				+ "<p><b>ໃຊ້ Prism Launcher ຫຼື TLauncher?</b> ມັນງ່າຍຫຼາຍທີ່ຈະຕັ້ງຄ່າ JVM ແບບ custom: "
				+ "ໃນ Prism Launcher, ໄປທີ່ <i>Instalaciones</i> → <i>Editar instancia</i> → <i>ການຕັ້ງຄ່າ de Java</i>; "
				+ "ໃນ TLauncher, ໄປທີ່ <i>Settings</i> → <i>Java Settings</i> ແລະ ເລືອກເສັ້ນທາງຂອງ JDK/JRE ທີ່ທ່ານດາວໂຫຼດ. "
				+ "ທ່ານບໍ່ຈຳເປັນຕ້ອງປ່ຽນ JVM ຂອງລະບົບ!</p>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "ຂໍ້ຂັດແຍ່ງລະຫວ່າງ Paranoia ແລະ C2ME";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງລະຫວ່າງ mods Paranoia ແລະ C2ME.</b>"
				+ "<p>ຜິດພາດ ຊີ້ບອກວ່າ <code>ThreadLocalRandom</code> ຖືກເຂົ້າເຖິງຈາກ thread ທີ່ຜິດ.</p>"
				+ "<p>ສິ່ງນີ້ມັກເກີດຂຶ້ນເມື່ອ mod <b>Paranoia</b> ດຳເນີນການ code ທີ່ບໍ່ປອດໄພສຳລັບຫຼາຍ threads ໃນຂະນະທີ່ <b>C2ME</b> ກຳລັງເຮັດການ optimize multithreading.</p>"
				+ "<p>ຂໍ້ຂັດແຍ່ງປະເພດນີ້ເປັນເລື່ອງທຳມະດາກັບ mods ທີ່ສ້າງດ້ວຍ MCreator.</p>"
				+ "<p>ຖ້າບັນຫາຍັງຄົງຢູ່, ລອງເລີ່ມເກມໂດຍບໍ່ມີ Paranoia ຫຼື ປິດການເຮັດວຽກ C2ME.</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "ຂາດໂດຍເລກຕອຣີ assets ຂອງ Minecraft";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft ບໍ່ສາມາດຊອກຫາໂດຍເລກຕອຣີ assets ໄດ້.</b>"
				+ "<p>Launcher ພະຍາຍາມເລີ່ມເກມດ້ວຍເສັ້ນທາງ assets ທີ່ບໍ່ຖືກຕ້ອງ.</p>"
				+ "<p>ສິ່ງນີ້ໝາຍຄວາມວ່າໄຟລ໌ resources ຂອງເກມ (assets) ບໍ່ມີຢູ່ ຫຼື ບໍ່ໄດ້ຕິດຕັ້ງຢ່າງຖືກຕ້ອງ.</p>"
				+ "<p>ບັນຫານີ້ມັກເກີດຂຶ້ນກັບການຕິດຕັ້ງ Minecraft ທີ່ຜິດ ຫຼື launchers ທີ່ຕັ້ງຄ່າຜິດ.</p>"
				+ "<p>ມັນຍັງອາດເກີດຂຶ້ນເມື່ອໃຊ້ launchers ທີ່ບໍ່ເປັນທາງການທີ່ຈັດການ assets ຜິດວິທີ ເຊັ່ນ FreshCraft.</p>"
				+ "<p>ຖ້າບັນຫາຍັງຄົງຢູ່, ລອງຕິດຕັ້ງ modpack ໃໝ່ ຫຼື ເລີ່ມເກມຈາກ launcher ທາງການ ຫຼື ທີ່ໜ້າເຊື່ອຖື.</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return "ຕິດຕັ້ງ " + dependencia + " ເວີຊັນ " + version + " ຫຼື ສູງກວ່າ";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return "ແທນທີ່ " + dependencia + " ດ້ວຍເວີຊັນລະຫວ່າງ " + min + " ແລະ " + max;
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "Mod " + mod + " ຕ້ອງການ " + dependencia + " ເວີຊັນຂັ້ນຕ່ຳ " + version;
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "Mod " + mod + " ຕ້ອງການ " + dependencia + " ເວີຊັນ " + version;
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {

		return "Mod " + mod + " ຕ້ອງການ " + dependencia + " ລະຫວ່າງ " + min + " ແລະ " + max + " (ປະຈຸບັນ: " + actual
				+ ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "ເວີຊັນ Cupboard ບໍ່ເຂົ້າກັນ";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບ crash ທີ່ເກີດຈາກເວີຊັນ Cupboard ເກົ່າ.</b>"
				+ "<p>ຜິດພາດ ເກີດຂຶ້ນພາຍໃນ <code>ClientConfigCompat.setupNeoforge</code> ເນື່ອງຈາກ "
				+ "<code>ModList.get()</code> ສົ່ງຄ່າ <code>null</code> ກັບມາ.</p>"
				+ "<p>ນີ້ແມ່ນບັນຫາທີ່ຮູ້ຈັກກັນດີໃນເວີຊັນເກົ່າຂອງ mod <b>Cupboard</b>.</p>"
				+ "<p>ເວີຊັນເກົ່າເຊັ່ນ <b>3.2</b> ມີ bug ນີ້.</p>"
				+ "<p><b>ວິທີແກ້ໄຂ:</b> ອັບເດດ Cupboard ເປັນເວີຊັນ <b>3.5</b> ຫຼື ສູງກວ່າ.</p>";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		return "ເນື່ອງຈາກທ່ານຢູ່ໃນ macOS ແລະ ເກມກຳລັງພະຍາຍາມໃຊ້ OpenGL, ເຊິ່ງບໍ່ເຂົ້າກັນກັບເວີຊັນລ່າສຸດຂອງ macOS. "
				+ "ທ່ານຕ້ອງໃຊ້ເວີຊັນ Minecraft ທີ່ຮອງຮັບ Metal ຫຼື ໃຊ້ Linux ຖ້າທ່ານມີ Mac Intel ຫຼື M1/M2 ແຕ່ບໍ່ແມ່ນ M3+ ຫຼື Neo.";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "ບໍ່ພົບການເຄື່ອນໄຫວ GeckoLib";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod ບໍ່ສາມາດໂຫຼດໄຟລ໌ການເຄື່ອນໄຫວຂອງ GeckoLib ໄດ້.</b>" + "<p>ໄຟລ໌ ທີ່ໄດ້ຮັບຜົນກະທົບ:</p>" + "<code>"
				+ archivo + "</code>" + "<p>ຜິດພາດ ນີ້ເກີດຂຶ້ນເມື່ອໄຟລ໌ animation <code>.json</code> ບໍ່ມີຢູ່, "
				+ "ມີ ຜິດພາດ ທາງ syntax ຫຼື ເສັ້ນທາງຜິດ.</p>" + "<p>ສາເຫດທີ່ເປັນໄປໄດ້:</p>" + "<ul>"
				+ "<li>ໄຟລ໌ຖືກລຶບແລ້ວແຕ່ຍັງຖືກອ້າງອີງໃນ code.</li>" + "<li>ຜິດພາດ syntax ພາຍໃນໄຟລ໌ JSON.</li>"
				+ "<li>ເສັ້ນທາງທີ່ກຳນົດໃນການລົງທະບຽນຂອງ mod ຜິດ.</li>"
				+ "<li>ຂໍ້ຂັດແຍ່ງ dependencies ຫຼື ເວີຊັນບໍ່ເຂົ້າກັນ.</li>" + "</ul>";
	}

	@Override
	public String nombreAnimacionGeckoInexiste() {
		return "ບໍ່ພົບການເຄື່ອນໄຫວ GeckoLib";
	}

	@Override
	public String mensajeAnimacionGeckoInexiste(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Mod ບໍ່ສາມາດຊອກຫາໄຟລ໌ການເຄື່ອນໄຫວຂອງ GeckoLib ໄດ້.</b>" + "<p>ໄຟລ໌ ທີ່ໄດ້ຮັບຜົນກະທົບ:</p>" + "<code>"
				+ archivo + "</code>"
				+ "<p>ຜິດພາດ ນີ້ເກີດຂຶ້ນເມື່ອ GeckoLib ພະຍາຍາມໂຫຼດການເຄື່ອນໄຫວທີ່ບໍ່ມີຢູ່ໃນເສັ້ນທາງທີ່ລະບຸ. "
				+ "ຕ່າງຈາກ ຜິດພາດ ໂຫຼດ (syntax), ຜິດພາດ ນີ້ຊີ້ບອກວ່າໄຟລ໌ຂາດຫາຍໄປທາງກາຍະພາບ ຫຼື ເສັ້ນທາງຜິດ.</p>"
				+ "<p>ສາເຫດທີ່ເປັນໄປໄດ້:</p>" + "<ul>"
				+ "<li>ໄຟລ໌ <code>.json</code> ຖືກລຶບ ຫຼື ບໍ່ໄດ້ລວມຢູ່ໃນ JAR ສຸດທ້າຍຂອງ mod.</li>"
				+ "<li>ຜິດພາດການພິມໃນເສັ້ນທາງທີ່ກຳນົດໃນ code (ຕົວຢ່າງ: 'animations' vs 'animaciones').</li>"
				+ "<li>ຄວາມແຕກຕ່າງລະຫວ່າງຕົວພິມໃຫຍ່/ນ້ອຍ (ລະບົບປະຕິບັດການ server ແມ່ນ Linux (ແຍກແຍະ) ແລະ ການພັດທະນາແມ່ນໃນ Windows (ບໍ່ແຍກແຍະ)).</li>"
				+ "<li>Mod ບໍ່ໄດ້ອັບເດດຢ່າງສົມບູນ ຫຼື dependencies ຂອງມັນເສຍຫາຍ.</li>" + "</ul>";
	}

	@Override
	public String nombreRegistroDuplicadoObjeto() {
		return "ຂໍ້ຂັດແຍ່ງການລົງທະບຽນຊ້ຳ";
	}

	@Override
	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal: Solo el texto descriptivo lleva el color de error
		String mensajeBase = "<span style='color:#" + color + "'>ຂໍ້ຂັດແຍ່ງວິກິດ: ພະຍາຍາມລົງທະບຽນ object ຊ້ຳສອງຄັ້ງ. "
				+ "Mods </span>" + mod1 + "<span style='color:#" + color + "'> ແລະ </span>" + mod2
				+ "<span style='color:#" + color + "'> ກຳລັງພະຍາຍາມລົງທະບຽນ object ດຽວກັນ. "
				+ "Object ທີ່ມີຂໍ້ຂັດແຍ່ງ: </span>" + objeto + "<br><br>";

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "ສິ່ງນີ້ມັກເກີດຂຶ້ນເມື່ອ mods ສອງຕົວທີ່ແຕກຕ່າງກັນເພີ່ມ object ດ້ວຍຊື່ດຽວກັນ "
				+ "ໃນ namespace ດຽວກັນ, ຫຼື ເມື່ອມີ ຜິດພາດ ໃນ code ຂອງ mod ໜຶ່ງ.<br><br>"
				+ "<b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b><br>" + "<ul>"
				+ "<li>ກວດສອບວ່າ mod ໜຶ່ງແມ່ນການອັບເດດ ຫຼື fork ຂອງອີກ mod ໜຶ່ງຫຼືບໍ່.</li>"
				+ "<li>ລອງ ລົບ mod ທີ່ມີຂໍ້ຂັດແຍ່ງໜຶ່ງໃນສອງຕົວ.</li>"
				+ "<li>ກວດສອບໄຟລ໌ການຕັ້ງຄ່າຂອງທັງສອງ mods ເພື່ອເບິ່ງວ່າທ່ານສາມາດປ່ຽນ ID ຂອງ object ໄດ້ຫຼືບໍ່.</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreFalloFabricRenderingAPI() {
		return "ຂາດ Fabric Rendering API";
	}

	@Override
	public String mensajeFalloFabricRenderingAPI() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color
				+ "'>Mod (ໂດຍທົ່ວໄປແມ່ນ Porting Lib ຫຼື dependencies ຂອງມັນ) ລົ້ມເຫຼວເນື່ອງຈາກ </span>"
				+ "Fabric Rendering API<span style='color:#" + color + "'> ບໍ່ມີຢູ່.</span><br><br>";

		// Instrucciones de reparación (Actualizadas para versiones modernas donde
		// Indium es obsoleto)
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b><br>"
				+ "ຂໍ້ຄວາມແນະນຳໃຫ້ຕິດຕັ້ງ Indium, ແຕ່ mod ນີ້ໝົດອາຍຸແລ້ວໃນເວີຊັນສະໄໝໃໝ່ຂອງເກມ.<br>" + "<ul>"
				+ "<li><b>ອັບເດດ Sodium</b> ເປັນເວີຊັນ <b>0.6.0</b> ຫຼື ສູງກວ່າ (ເວີຊັນນີ້ລວມມີການຮອງຮັບທີ່ຈຳເປັນ).</li>"
				+ "<li>ໃຫ້ແນ່ໃຈວ່າທ່ານໄດ້ຕິດຕັ້ງ <b>Fabric API</b> ຖ້າຍັງບໍ່ໄດ້ຕິດຕັ້ງ.</li>"
				+ "<li>ຖ້າທ່ານໃຊ້ເວີຊັນເກົ່າຂອງເກມ (1.20.6 ຫຼື ຕ່ຳກວ່າ), ໃຫ້ຕິດຕັ້ງ Indium.</li>" + "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRestriccionesDependenciaNoCumplidas() {
		return "ບໍ່ໄດ້ປະຕິບັດຕາມຂໍ້ຈຳກັດຂອງ dependencies";
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>ພົບ </span>" + cantidad + "<span style='color:#"
				+ color + "'> ຂໍ້ຈຳກັດຂອງ dependencies ທີ່ບໍ່ໄດ້ປະຕິບັດຕາມ.</span><br><br>";

		// Construcción de la lista de conflictos
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictos != null && !conflictos.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>ພົບຂໍ້ຂັດແຍ່ງໃນໄຟລ໌ຕໍ່ໄປນີ້:</span><br><ul>");
			for (String[] par : conflictos) {
				String dep = par[0]; // Dependencia
				String jar = par[1]; // Archivo JAR
				// Variable en color por defecto, texto fijo en color error
				listaDetalle.append("<li>").append("<span style='color:#").append(color).append("'>ໄຟລ໌: </span>")
						.append(jar).append("<br><span style='color:#").append(color).append("'>ຕ້ອງການ: </span>")
						.append(dep).append("</li>");
			}
			listaDetalle.append("</ul><br>");
		}

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "ສິ່ງນີ້ເກີດຂຶ້ນເມື່ອ mods ສອງຕົວ ຫຼື ຫຼາຍກວ່ານັ້ນຕ້ອງການເວີຊັນທີ່ແຕກຕ່າງກັນ ແລະ ບໍ່ເຂົ້າກັນຂອງ library ພາຍໃນດຽວກັນ.<br><br>"
				+ "<b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b><br>" + "<ul>"
				+ "<li>ລອງອັບເດດ ຫຼື ລົບ mods ທີ່ລະບຸໄວ້ຂ້າງເທິງເພື່ອແກ້ໄຂຄວາມບໍ່ເຂົ້າກັນ.</li>"
				+ "<li>ຖ້າທ່ານບໍ່ພົບເວີຊັນທີ່ເຂົ້າກັນໄດ້, ທ່ານສາມາດລອງແກ້ໄຂໄຟລ໌ <code>mods.toml</code> ພາຍໃນໄຟລ໌ JAR ຂອງ mod ດ້ວຍມື (ໂດຍໃຊ້ໂປຣແກຣມບີບອັດເຊັ່ນ WinRAR ຫຼື 7-Zip) ເພື່ອປ່ຽນ ຫຼື ລົບ ຂໍ້ຈຳກັດເວີຊັນ, ເຖິງວ່າສິ່ງນີ້ອາດຈະເຮັດໃຫ້ເກີດຄວາມບໍ່ໝັ້ນຄົງ.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>ພົບ </span>" + cantidad + "<span style='color:#"
				+ color + "'> ຂໍ້ຈຳກັດຂອງ dependencies ທີ່ບໍ່ໄດ້ປະຕິບັດຕາມ.</span><br><br>";

		// Construcción de la lista agrupada por Mod
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictosPorMod != null && !conflictosPorMod.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Mods ທີ່ກ່ຽວຂ້ອງ ແລະ dependencies ທີ່ຕ້ອງການ:</span><br><ul>");

			for (Map.Entry<String, List<String>> entry : conflictosPorMod.entrySet()) {
				String archivo = entry.getKey();
				List<String> dependencias = entry.getValue();

				// Nombre del Mod (color por defecto)
				listaDetalle.append("<li><b>").append(archivo).append("</b>");

				// Lista de dependencias para este mod
				listaDetalle.append("<ul>");
				for (String dep : dependencias) {
					// Dependencia (color por defecto)
					listaDetalle.append("<li>").append(dep).append("</li>");
				}
				listaDetalle.append("</ul></li>");
			}
			listaDetalle.append("</ul><br>");
		} else {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>ບໍ່ສາມາດລະບຸໄຟລ໌ສະເພາະຈາກ log ໄດ້.</span><br>");
		}

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "ຜິດພາດ ນີ້ເກີດຂຶ້ນເມື່ອ mods ລວມເອົາເວີຊັນພາຍໃນຂອງ libraries (JarInJar) ທີ່ບໍ່ເຂົ້າກັນ.<br><br>"
				+ "<b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b><br>" + "<ul>"
				+ "<li>ກວດສອບລາຍຊື່ຂ້າງເທິງເພື່ອລະບຸວ່າ mods ໃດຕ້ອງການເວີຊັນທີ່ແຕກຕ່າງກັນຂອງ library ດຽວກັນ.</li>"
				+ "<li>ລອງອັບເດດທັງສອງ mods ເປັນເວີຊັນລ່າສຸດ.</li>"
				+ "<li>ເປັນວິທີສຸດທ້າຍ, ທ່ານສາມາດເປີດໄຟລ໌ <code>.jar</code> ຂອງ mod ດ້ວຍໂປຣແກຣມບີບອັດ (ເຊັ່ນ WinRAR), ແກ້ໄຂ <code>META-INF/mods.toml</code> ແລະ ປ່ຽນຊ່ວງເວີຊັນຂອງ dependency ດ້ວຍມື, ເຖິງວ່າສິ່ງນີ້ຈະມີຄວາມສ່ຽງ ແລະ ອາດຈະເຮັດໃຫ້ mod ເສຍຫາຍ.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String nombreNeruinaOcultaAdvertencia() {
		return "Neruina ປ້ອງກັນບໍ່ໃຫ້ debug";
	}

	@Override
	public String mensajeNeruinaOcultaAdvertencia() {
		String color = Config.obtenerInstancia().obtenerColorAdvertencia();

		// Advertencia principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>ຄຳເຕືອນ:</b> Mod <b>Neruina</b> ກຳລັງລົ້ມເຫຼວໃນການຈັດການ ຜິດພາດ, ເຊິ່ງເຮັດໃຫ້ບັງສາເຫດທີ່ແທ້ຈິງຂອງ crash.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Neruina ມັກຈະບໍ່ຈຳເປັນ ແລະ ເຮັດໃຫ້ຍາກທີ່ຈະຮູ້ວ່າສິ່ງໃດຜິດພາດແທ້ໆ. ແນະນຳໃຫ້ ລົບ ມັນອອກເພື່ອ debug.</span><br><br>";

		// Instrucciones de recuperación
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>ຄຳແນະນຳການຟື້ນຟູ:</b><br>"
				+ "1. **MCForge**: ໄປທີ່ '[ຊື່ໂລກ]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: ໄປທີ່ 'config/neoforge-server.toml'.<br>"
				+ "   *(ໝາຍເຫດ: ໃນເກມ Singleplayer, ໂລກຢູ່ໃນໂຟລເດີ 'saves')*.<br>"
				+ "3. ປ່ຽນ **removeErroringBlockEntities** ແລະ **removeErroringEntities** ເປັນ **true**.<br><br>"
				+ "<b>ຕົວເລືອກອື່ນໆ:</b><br>" + "- **MCEdit**: ໃຊ້ມັນເພື່ອ ລົບ entity ດ້ວຍມືຢູ່ທີ່ພິກັດທີ່ລະບຸ.<br>"
				+ "- ຖ້າ ຜິດພາດ ນີ້ຍັງຄົງຢູ່, ມັນເປັນໄປໄດ້ວ່າ Neruina ບໍ່ໄດ້ເຮັດວຽກຢ່າງຖືກຕ້ອງ ແລະ ພຽງແຕ່ສ້າງ ຜິດພາດ ໃໝ່ໆ."
				+ "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreApothicAttributeSinDueño() {
		return "ຜິດພາດຂອງ Apothic Attributes";
	}

	@Override
	public String mensajeApothicAttributeSinDueño(boolean chestCavityDetectado) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Apothic Attributes</b> ພົບຂໍ້ຂັດແຍ່ງ: <b>AttributeMap</b> ຖືກດັດແປງໂດຍບໍ່ມີ owner ກຳນົດ.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "ສິ່ງນີ້ມັກເກີດຂຶ້ນເມື່ອ mod ພະຍາຍາມດັດແປງ attributes ຂອງ entity (ເຊັ່ນ: ຊີວິດ, ຄວາມເສຍຫາຍ, ຄວາມໄວ) "
				+ "ໃນເວລາທີ່ບໍ່ເໝາະສົມ ຫຼື ຜິດວິທີ.</span><br><br>";

		// Nota específica sobre Chest Cavity
		String notaChestCavity = "";
		if (chestCavityDetectado) {
			notaChestCavity = "<span style='color:#" + color + "'>" + "<b>ພົບ mod Chest Cavity ໃນ log.</b> "
					+ "Mod ນີ້ເປັນສາເຫດທົ່ວໄປຂອງ ຜິດພາດ ສະເພາະນີ້ ເນື່ອງຈາກວິທີທີ່ມັນຈັດການ attributes ຂອງ entities.</span><br><br>";
		}

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b><br>" + "<ul>"
				+ "<li>ຖ້າຕິດຕັ້ງ Chest Cavity, ລອງອັບເດດ ຫຼື ລົບ ມັນຊົ່ວຄາວເພື່ອກວດສອບວ່າເປັນສາເຫດຫຼືບໍ່.</li>"
				+ "<li>ກວດສອບວ່າມີ mods ອື່ນໆທີ່ດັດແປງ attributes ຂອງ mobs ຫຼືບໍ່ ແລະ ລອງປິດການເຮັດວຽກພວກມັນ.</li>"
				+ "<li>ຊອກຫາການອັບເດດຂອງ <b>Apothic Attributes</b> ເພາະອາດຈະເປັນ ຜິດພາດ ທີ່ຖືກແກ້ໄຂແລ້ວໃນເວີຊັນລ່າສຸດ.</li>"
				+ "</ul></span>";

		return mensajeBase + notaChestCavity + instrucciones;
	}

	@Override
	public String nombreErrorPotBlockEntity() {
		return "ຜິດພາດຂອງ DecoratedPot (Cataclysm)";
	}

	@Override
	public String mensajeErrorPotBlockEntity() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "ເກີດ ຜິດພາດ ຄວາມບໍ່ເຂົ້າກັນກັບ <b>DecoratedPotBlockEntity</b>.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "ນີ້ແມ່ນບັນຫາທີ່ຮູ້ຈັກກັນດີໃນເວີຊັນ 1.19.2 ຂອງ mod <b>L_Enders_Cataclysm</b>, "
				+ "ເຊິ່ງຂາດການ implement ທີ່ເກມຕ້ອງການ.</span><br><br>";

		// Solución
		String solucion = "<span style='color:#" + color + "'>" + "<b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b><br>"
				+ "ຕິດຕັ້ງ mod <b>PotFix (Cataclysm Patch)</b> ເພື່ອແກ້ໄຂ ຜິດພາດ ນີ້.<br>"
				+ "ທ່ານສາມາດດາວໂຫຼດໄດ້ທີ່ນີ້: <a href='https://www.curseforge.com/minecraft/mc-mods/potfix-cataclysm-patch  '>CurseForge - PotFix</a>"
				+ "</span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorPreloadingTricks() {
		return "ຜິດພາດຂອງ Preloading Tricks";
	}

	@Override
	public String mensajeErrorPreloadingTricks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງທີ່ເກີດຈາກ <b>Preloading Tricks</b>.</span><br><br>" + "<span style='color:#" + color
				+ "'>" + "ຜິດພາດ <i>ClassCastException: String cannot be cast to ModuleDescriptor</i> "
				+ "ຊີ້ບອກວ່າ mod ກຳລັງດັດແປງ classes ຂອງລະບົບ modules ຂອງ Java ຢ່າງຜິດວິທີ.</span><br><br>";

		// Explicación y Solución
		String explicacion = "<span style='color:#" + color + "'>"
				+ "<b>Preloading Tricks</b> ແມ່ນ mod ທີ່ອອກແບບມາສຳລັບ <b>ນັກພັດທະນາ</b> ເປັນຫຼັກ. "
				+ "ມັນດຳເນີນການດັດແປງ classes (mixins) ທີ່ສັບສົນໃນຂັ້ນຕອນການໂຫຼດເກມທີ່ເລີ່ມຕົ້ນຫຼາຍ, "
				+ "ເຊິ່ງສາມາດທຳລາຍຄວາມໝັ້ນຄົງໄດ້ງ່າຍຖ້າມີການໂຕ້ຕອບກັບອື່ນໆ.</span><br><br>" + "<span style='color:#"
				+ color + "'><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b><br>" + "<ul>"
				+ "<li>ລຶບ mod <b>Preloading Tricks</b>. ໂດຍທົ່ວໄປແລ້ວບໍ່ຈຳເປັນສຳລັບການຫຼິ້ນໃນ servers ສາທາລະນະ ຫຼື packs ທີ່ໝັ້ນຄົງ.</li>"
				+ "<li>ຖ້າທ່ານເປັນນັກພັດທະນາ ແລະ ຕ້ອງການມັນສຳລັບການທົດສອບ, ໃຫ້ກວດສອບການຕັ້ງຄ່າ environment ຂອງທ່ານ.</li>"
				+ "</ul></span>";

		return mensajeBase + explicacion;
	}

	@Override
	public String nombreErrorSimpleRadioLexiconfig() {
		return "ຄວາມບໍ່ເຂົ້າກັນ Simple Radio / Lexiconfig";
	}

	@Override
	public String mensajeErrorSimpleRadioLexiconfig() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "ພົບຂໍ້ຂັດແຍ່ງລະຫວ່າງ <b>Simple Radio</b> ແລະ <b>Lexiconfig</b>.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "ຜິດພາດ ເກີດຂຶ້ນໃນລະຫວ່າງຂະບວນການ 'shelveLexicons', ເຊິ່ງຊີ້ບອກເຖິງຄວາມບໍ່ເຂົ້າກັນທາງ binary ລະຫວ່າງ libraries ທັງສອງ.</span><br><br>";

		// Solución específica
		String solucion = "<span style='color:#" + color + "'>" + "<b>ສາເຫດທີ່ຮູ້ຈັກ:</b><br>"
				+ "Simple Radio ມັກຈະຖືກອອກແບບມາສຳລັບ Lexiconfig ເວີຊັນເກົ່າ, ໃນຂະນະທີ່ທ່ານຕິດຕັ້ງເວີຊັນທີ່ໃໝ່ກວ່າໄວ້.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b><br>" + "<ul>"
				+ "<li>ລອງໃຊ້ <b>Lexiconfig</b> ເວີຊັນເກົ່າກວ່າ.</li>"
				+ "<li>ແນະນຳໃຫ້ລອງເວີຊັນ <b>1.3.11</b> ຫຼື ເກົ່າກວ່ານັ້ນ, ເຊິ່ງມັກຈະເຂົ້າກັນໄດ້ກັບ Simple Radio.</li>"
				+ "<li>ຖ້າບັນຫາຍັງຄົງຢູ່, ໃຫ້ກວດສອບວ່າມີການອັບເດດ Simple Radio ຫຼືບໍ່.</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorMobAITweaks() {
		return "ຜິດພາດຂອງ Mob AI Tweaks";
	}

	@Override
	public String mensajeErrorMobAITweaks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "ພົບ ຜິດພາດ ທີ່ກ່ຽວຂ້ອງກັບ <b>Mob AI Tweaks</b>.</span><br><br>" + "<span style='color:#" + color
				+ "'>" + "ຜິດພາດ ມາຈາກ Mixin (<code>$mob-ai-tweaks$onSpawned</code>) ທີ່ແຊກແຊງ "
				+ "ເມື່ອ entity ປາກົດຕົວ (spawn). ສິ່ງນີ້ມັກຊີ້ບອກເຖິງຂໍ້ຂັດແຍ່ງກັບ mod ອື່ນ "
				+ "ທີ່ດັດແປງພຶດຕິກຳການ spawn ຂອງ mobs ເຊັ່ນກັນ.</span><br><br>";

		// Solución
		String solucion = "<span style='color:#" + color + "'><b>ວິທີແກ້ໄຂທີ່ແນະນຳ:</b><br>" + "<ul>"
				+ "<li>ລອງ ລົບ <b>Mob AI Tweaks</b> ເພື່ອກວດສອບວ່າຄວາມບໍ່ໝັ້ນຄົງຫາຍໄປຫຼືບໍ່.</li>" + "</ul></span>";

		return mensajeBase + solucion;
	}

	public String nombre_verificacion_gpu() {
		return "ການກວດສອບ GPU (OpenGL / ການເລືອກ GPU)";
	}

	public String desactivar_parche_gpu() {
		return "ປິດການກວດສອບ GPU";
	}

	// ==================== CRASH ====================

	public String gpu_crash_posible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>ຕົວກວດສອບ GPU ອາດຈະເຮັດໃຫ້ເກມປິດລົງ.</b>";
	}

	public String gpu_crash_causas() {
		return "ການກວດສອບເລີ່ມຕົ້ນແຕ່ບໍ່ສຳເລັດ. ສິ່ງນີ້ມັກຊີ້ບອກເຖິງການລົ້ມເຫຼວໃນ OpenGL ຫຼື drivers ກຣາຟິກ.<br><br>"
				+ "ສາເຫດທີ່ເປັນໄປໄດ້:<br>" + "- Drivers ຕົກຍຸກ ຫຼື ບໍ່ໝັ້ນຄົງ<br>" + "- ບັນຫາກັບ OpenGL<br>"
				+ "- GPUs ເກົ່າ ຫຼື ການຕັ້ງຄ່າແບບ hybrid";
	}

	public String gpu_crash_recomendaciones() {
		return "ຄຳແນະນຳ:<br>" + "- ອັບເດດ drivers ຂອງ GPU<br>" + "- ບັງຄັບໃຊ້ GPU dedicated<br>"
				+ "- ຫຼີກລ່ຽງສະພາບແວດລ້ອມ remote ຫຼື virtualized";
	}

	// ==================== NO ÓPTIMA ====================

	public String gpu_no_optima() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>ເກມບໍ່ໄດ້ໃຊ້ GPU ທີ່ດີທີ່ສຸດທີ່ມີຢູ່.</b>";
	}

	public String gpu_no_optima_detalles() {
		return "ສິ່ງນີ້ອາດຫຼຸດປະສິດທິພາບ (FPS ຕ່ຳ), ແຕ່ໂດຍທົ່ວໄປແລ້ວບໍ່ເຮັດໃຫ້ເກີດ crash.";
	}

	public String gpu_recomendaciones_rendimiento() {
		return "ຄຳແນະນຳ:<br>" + "- ບັງຄັບໃຊ້ GPU dedicated ໃນ control panel<br>"
				+ "- ຕັ້ງຄ່າ Java/Minecraft ໃນໂໝດ high performance";
	}

	// ==================== GENERALES ====================

	public String gpu_nota_precision() {
		return "<b>ໝາຍເຫດ:</b> ລະບົບການກວດຈັບນີ້ບໍ່ໄດ້ຖືກຕ້ອງ 100%.";
	}

	public String gpu_consumo_energia() {
		return "GPUs ທີ່ມີປະສິດທິພາບສູງຈະໃຊ້ພະລັງງານຫຼາຍກວ່າ ແລະ ອາດຫຼຸດອາຍຸການໃຊ້ງານຂອງແບັດເຕີຣີໃນ laptops.";
	}

	public String gpu_parche_info() {
		return "ທ່ານສາມາດປິດການກວດສອບນີ້ໂດຍໃຊ້ປຸ່ມແກ້ໄຂດ່ວນ.";
	}

	@Override
	public String nombreVerificacionRaptorLake() {
		return "ຄຳເຕືອນຄວາມໝັ້ນຄົງ CPU Intel ຮຸ່ນ 13/14";
	}

	@Override
	public String advertenciaRaptorLakeTitulo() {
		return "ຄວາມບໍ່ໝັ້ນຄົງທີ່ເປັນໄປໄດ້ໃນ processor Intel Raptor Lake";
	}

	@Override
	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String targetMicrocode) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "ພົບ processor " + cpu
				+ " ທີ່ມີ microcode " + microcode + "." + "</b> "
				+ "Processors Intel ຮຸ່ນ 13 ແລະ 14 ໄດ້ພົບບັນຫາຄວາມບໍ່ໝັ້ນຄົງເນື່ອງຈາກການຂໍ voltage ຫຼາຍເກີນໄປ, "
				+ "ເຊິ່ງອາດຫຼຸດອາຍຸການໃຊ້ງານຂອງ processor.<br><br>"
				+ "ແນະນຳໃຫ້ອັບເດດ microcode ຫຼື BIOS ຂອງ mainboard ຂອງທ່ານເປັນເວີຊັນທີ່ລວມມີ microcode <b>"
				+ targetMicrocode + "</b> ຫຼື ສູງກວ່າ. "
				+ "<b>ຄຳເຕືອນ:</b> ການ ອັບເດດ BIOS ມີຄວາມສ່ຽງຖ້າບໍ່ເຮັດຢ່າງຖືກຕ້ອງ.<br><br>"
				+ "<i>ໝາຍເຫດ: ສິ່ງນີ້ເກືອບແນ່ນອນວ່າບໍ່ແມ່ນສາເຫດຂອງ crash ປະຈຸບັນຂອງທ່ານ, ມັນເປັນພຽງແຕ່ຄຳເຕືອນກ່ຽວກັບສຸຂະພາບ hardware.</i>";
	}

	@Override
	public String desactivarVerificacionRaptorLake() {
		return "ຢ່າແຈ້ງເຕືອນຂ້ອຍກ່ຽວກັບສິ່ງນີ້ອີກ";
	}

	@Override
	public String verArticuloRaptorLake(String fuente) {
		return "ອ່ານບົດຄວາມໃນ " + fuente;
	}

	@Override
	public String tituloMixins() {
		return "ຕົວສຳຫຼວດ Mixins";
	}

	@Override
	public String mixinsBotonLateral() {
		return "Mixins";
	}

	@Override
	public String mixinsRaiz() {
		return "ພົບ Mixins";
	}

	@Override
	public String mixinsTodosLosMods() {
		return "ທັງໝົດ";
	}

	@Override
	public String mixinsModConMixin() {
		return "Mod ທີ່ມີ mixins";
	}

	@Override
	public String mixinsTooltipCombo() {
		return "ກອງຕາມ mod ທີ່ມີ mixins";
	}

	@Override
	public String mixinsRecargar() {
		return "ໂຫຼດໃໝ່";
	}

	@Override
	public String mixinsDescompilarSeleccion() {
		return "Decompile ສ່ວນທີ່ເລືອກ";
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
		return "Targets ຂອງ method";
	}

	@Override
	public String mixinsMetodos() {
		return "Methods";
	}

	@Override
	public String mixinsCampos() {
		return "Fields";
	}

	@Override
	public String mixinsCantidad() {
		return "ຈຳນວນ mixins";
	}

	@Override
	public String mixinsDetalleMixin() {
		return "ລາຍລະອຽດຂອງ mixin";
	}

	@Override
	public String mixinsDetalleTarget() {
		return "ລາຍລະອຽດຂອງ target";
	}

	@Override
	public String mixinsDetalleMetodo() {
		return "ລາຍລະອຽດຂອງ method mixin";
	}

	@Override
	public String mixinsDetalleCampo() {
		return "ລາຍລະອຽດຂອງ field mixin";
	}

	@Override
	public String mixinsDetalleConflicto() {
		return "ລາຍລະອຽດຂໍ້ຂັດແຍ່ງ";
	}

	@Override
	public String mixinsBuscarConflictos() {
		return "ຄົ້ນຫາ ຂໍ້ຂັດແຍ່ງທີ່ເປັນໄປໄດ້";
	}

	@Override
	public String mixinsResultadosConflictos() {
		return "ຜົນການຊອກຫາຂໍ້ຂັດແຍ່ງ";
	}

	@Override
	public String mixinsConflictosPosibles() {
		return "ຂໍ້ຂັດແຍ່ງທີ່ເປັນໄປໄດ້";
	}

	@Override
	public String mixinsErrorDescompilar() {
		return "ຜິດພາດໃນການ decompile";
	}

	@Override
	public String mixinsColorPanel() {
		return "ສີແຜງ mixins";
	}

	@Override
	public String mixinsColorTexto() {
		return "ສີຕົວໜັງສື mixins";
	}

	@Override
	public String mixinsColorTextoSuave() {
		return "ສີຕົວໜັງສືຮອງຂອງ mixins";
	}

	@Override
	public String mixinsAyudaUso1() {
		return "ເຄື່ອງມືນີ້ສະແດງ mods ທີ່ມີ mixins ຂອງ SpongePowered ແລະ ອະນຸຍາດໃຫ້ກວດສອບ classes, targets, methods ແລະ fields ຂອງພວກມັນ.";
	}

	@Override
	public String mixinsAyudaUso2() {
		return "ໃຊ້ຕົວເລືອກດ້ານເທິງເພື່ອກອງຕາມ mod ສະເພາະ ຫຼື ສະແດງທຸກໆ mod ທີ່ມີ mixins.";
	}

	@Override
	public String mixinsAyudaUso3() {
		return "ຂະຫຍາຍຕົ້ນໄມ້ເພື່ອເບິ່ງແຕ່ລະ mixin, classes ເປົ້າໝາຍ, methods ທີ່ມີ annotation ແລະ shadow fields.";
	}

	@Override
	public String mixinsAyudaUso4() {
		return "ຄລິກຂວາໃສ່ mod, mixin, target, method ຫຼື field ເພື່ອຊອກຫາຂໍ້ຂັດແຍ່ງທີ່ເປັນໄປໄດ້ກັບ mixins ອື່ນໆ.";
	}

	@Override
	public String mixinsAyudaUso5() {
		return "ທ່ານສາມາດ decompile ສ່ວນທີ່ເລືອກປະຈຸບັນ ຫຼື ຜົນການຊອກຫາຂໍ້ຂັດແຍ່ງເພື່ອກວດສອບ code ທີ່ກ່ຽວຂ້ອງ.";
	}

	@Override
	public String mixinsColorComboFondo() {
		return "ສີພື້ນຫຼັງຂອງຕົວເລືອກ mod";
	}

	@Override
	public String mixinsColorAreaContenidoFondo() {
		return "ສີພື້ນຫຼັງຂອງແຜງລາຍລະອຽດ";
	}

	@Override
	public String mixinsColorSeleccionTexto() {
		return "ສີການເລືອກຕົວໜັງສື";
	}

	@Override
	public String mixinsColorSeleccionTextoActivo() {
		return "ສີຂອງຕົວໜັງສືທີ່ຖືກເລືອກ";
	}

	@Override
	public String mixinsColorAyudaTexto() {
		return "ສີຕົວໜັງສືຄຳແນະນຳ";
	}

	@Override
	public String mixinsColorArbolFondo() {
		return "ສີພື້ນຫຼັງຂອງຕົ້ນໄມ້";
	}

	@Override
	public String mixinsColorRendererSeleccionTexto() {
		return "ສີຕົວໜັງສືທີ່ຖືກເລືອກຂອງຕົ້ນໄມ້";
	}

	@Override
	public String mixinsColorRendererSeleccionFondo() {
		return "ສີພື້ນຫຼັງທີ່ຖືກເລືອກຂອງຕົ້ນໄມ້";
	}

	@Override
	public String mixinsColorRendererBordeSeleccion() {
		return "ສີຂອບການເລືອກຂອງຕົ້ນໄມ້";
	}

	@Override
	public String depmapTitulo() {
		return "ແຜນທີ່ Dependencies";
	}

	@Override
	public String depmapBotonLateral() {
		return "DepMap";
	}

	@Override
	public String depmapPestanaMapa() {
		return "ແຜນທີ່";
	}

	@Override
	public String depmapPestanaDependientes() {
		return "Dependents";
	}

	@Override
	public String depmapRecargar() {
		return "ໂຫຼດໃໝ່";
	}

	@Override
	public String depmapDescompilarSeleccion() {
		return "Decompile ສ່ວນທີ່ເລືອກ";
	}

	@Override
	public String depmapVerReferencias() {
		return "ເບິ່ງ references";
	}

	@Override
	public String depmapDependencias() {
		return "Dependencies";
	}

	@Override
	public String depmapDependientes() {
		return "Dependents";
	}

	@Override
	public String depmapDependiente() {
		return "Dependent";
	}

	@Override
	public String depmapSinDependencias() {
		return "ບໍ່ມີ dependents";
	}

	@Override
	public String depmapSeleccionarMod() {
		return "ເລືອກ mod";
	}

	@Override
	public String depmapSeleccionarModBase() {
		return "Mod ພື້ນຖານ";
	}

	@Override
	public String depmapSeleccionarDependiente() {
		return "Mod dependent";
	}

	@Override
	public String depmapSeleccionarPaquete() {
		return "Package";
	}

	@Override
	public String depmapComprobarNoAlineadas() {
		return "ກວດສອບທີ່ບໍ່ aligned";
	}

	@Override
	public String depmapResultadoNoAlineadas() {
		return "ຜົນຂອງ dependencies ທີ່ບໍ່ aligned";
	}

	@Override
	public String depmapClaseInexistente() {
		return "Class ທີ່ບໍ່ມີຢູ່";
	}

	@Override
	public String depmapClaseReferenciada() {
		return "Class ທີ່ຖືກອ້າງອີງ";
	}

	@Override
	public String depmapOrigen() {
		return "ແຫຼ່ງທີ່ມາ";
	}

	@Override
	public String depmapDestino() {
		return "ຈຸດໝາຍປາຍທາງ";
	}

	@Override
	public String depmapDependenciaDetalle() {
		return "ລາຍລະອຽດຂອງ dependency";
	}

	@Override
	public String depmapReferenciaDetalle() {
		return "ລາຍລະອຽດຂອງ reference";
	}

	@Override
	public String depmapMetodoOrigen() {
		return "Method ແຫຼ່ງທີ່ມາ";
	}

	@Override
	public String depmapModBase() {
		return "Mod ພື້ນຖານ";
	}

	@Override
	public String depmapTodos() {
		return "ທັງໝົດ";
	}

	@Override
	public String depmapSeleccioneUnMod() {
		return "ເລືອກ mod";
	}

	@Override
	public String depmapSeleccioneParametrosNoAlineadas() {
		return "ເລືອກ mod ພື້ນຖານ, dependent ແລະ package";
	}

	@Override
	public String depmapSeleccioneClaseParaDescompilar() {
		return "ເລືອກ reference ຫຼື ຜົນການຊອກຫາເພື່ອ decompile";
	}

	@Override
	public String depmapErrorDescompilar() {
		return "ຜິດພາດໃນການ decompile";
	}

	@Override
	public String depmapAyuda1() {
		return "ເຄື່ອງມືນີ້ສ້າງແຜນທີ່ dependencies ລະຫວ່າງ mods ໂດຍໃຊ້ class references ລະຫວ່າງພວກມັນ.";
	}

	@Override
	public String depmapAyuda2() {
		return "Tab ແຜນທີ່ສະແດງ bubble graph ທີ່ມີແຕ່ລະ mod ເຊື່ອມຕໍ່ກັບ dependencies ທີ່ມັນໃຊ້.";
	}

	@Override
	public String depmapAyuda3() {
		return "Tab dependents ຈັດລຽງ mods ຕັ້ງແຕ່ໂຕທີ່ມີ dependents ຫຼາຍທີ່ສຸດ ຈົນເຖິງໂຕທີ່ບໍ່ມີເລີຍ.";
	}

	@Override
	public String depmapAyuda4() {
		return "ທ່ານສາມາດກວດສອບ references ສະເພາະລະຫວ່າງ mod ແລະ dependencies ຂອງມັນ, ເຊັ່ນດຽວກັບ decompile classes ທີ່ກ່ຽວຂ້ອງ.";
	}

	@Override
	public String depmapAyuda5() {
		return "ການກວດສອບ dependencies ທີ່ບໍ່ aligned ຊອກຫາ references ໄປຍັງ classes ທີ່ບໍ່ມີຢູ່ພາຍໃນ package ຫຼື subpackage ຂອງ mod ພື້ນຖານ.";
	}

	@Override
	public String depmapColorPanel() {
		return "ສີຂອງແຜງ";
	}

	@Override
	public String depmapColorTexto() {
		return "ສີຕົວໜັງສືຫຼັກ";
	}

	@Override
	public String depmapColorTextoSecundario() {
		return "ສີຕົວໜັງສືຮອງ";
	}

	@Override
	public String depmapColorAyudaTexto() {
		return "ສີຕົວໜັງສືຄຳແນະນຳ";
	}

	@Override
	public String depmapColorGrafoFondo() {
		return "ສີພື້ນຫຼັງຂອງ graph";
	}

	@Override
	public String depmapColorListaFondo() {
		return "ສີພື້ນຫຼັງຂອງລາຍການ";
	}

	@Override
	public String depmapColorArbolFondo() {
		return "ສີພື້ນຫຼັງຂອງຕົ້ນໄມ້";
	}

	@Override
	public String depmapColorNodo() {
		return "ສີ nodes ຂອງ graph";
	}

	@Override
	public String depmapColorEnlace() {
		return "ສີ links ຂອງ graph";
	}

	@Override
	public String depmapColorSeleccion() {
		return "ສີການເລືອກ";
	}

	@Override
	public String depmapColorSeleccionTexto() {
		return "ສີຂອງຕົວໜັງສືທີ່ຖືກເລືອກ";
	}

	@Override
	public String nombreProblemaAzureLibAnimaciones() {
		return "ບັນຫາກັບ addon ຂອງ AzureLib";
	}

	@Override
	public String mensajeProblemaAzureLibAnimaciones() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບ ຜິດພາດ ຂອງ AzureLib ໃນລະຫວ່າງການໂຫຼດ animations.</b>"
				+ "<p>ເກມພົບ JSON ຂອງ animations ທີ່ມີຮູບແບບຜິດ.</p>"
				+ "<p>ບັນຫານີ້ມັກເກີດຈາກ mod ຫຼື addons ທີ່ໃຊ້ <b>AzureLib</b>.</p>" + "<p><b>ຄຳແນະນຳ:</b></p>" + "<ul>"
				+ "<li>ໃຊ້ <b>DepMap</b> ໃນແຖບດ້ານຂ້າງເພື່ອຊອກຫາ addons ທັງໝົດທີ່ຂຶ້ນກັບ AzureLib.</li>"
				+ "<li>ລອງເລີ່ມເກມໂດຍບໍ່ມີ addons ບາງຕົວຈົນກວ່າຈະພົບໂຕທີ່ເຮັດໃຫ້ເກີດບັນຫາ.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaDecocraftNatureEssentialPartnerMod() {
		return "ບັນຫາກັບ Decocraft Nature";
	}

	@Override
	public String mensajeProblemaDecocraftNatureEssentialPartnerMod() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບບັນຫາທີ່ເກີດຈາກ Decocraft Nature.</b>" + "<p>ຜິດພາດ ເກີດຂຶ້ນໃນລະຫວ່າງການ initialize mixin config "
				+ "<code>com/razz/essentialpartnermod/mixins.json</code>.</p>"
				+ "<p>ບັນຫານີ້ສາມາດແກ້ໄຂໄດ້ໂດຍການແກ້ໄຂໄຟລ໌ JAR ຂອງ mod.</p>" + "<p><b>ຂັ້ນຕອນ:</b></p>" + "<ul>"
				+ "<li>ເປີດໄຟລ໌ JAR ດ້ວຍໂປຣແກຣມຈັດການໄຟລ໌ເຊັ່ນ File Roller, Ark, WinRAR, 7-Zip ຫຼື WinZip.</li>"
				+ "<li>ເຂົ້າໄປທີ່ <code>META-INF/MANIFEST.MF</code>.</li>" + "<li>ລຶບແຖວນີ້:</li>" + "</ul>"
				+ "<code>MixinConfigs: com/razz/essentialpartnermod/mixins.json</code>"
				+ "<p><b>ສຳຄັນ:</b> ຮັກສາແຖວວ່າງພຽງແຖວດຽວໄວ້ທ້າຍໄຟລ໌.</p>";
	}

	@Override
	public String mensajeTetraDeserializadorModeloEstatico() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບຜິດພາດໃນ Tetra ຫຼື ໃນ addon ໜຶ່ງຂອງມັນ.</b>"
				+ "<p>Log ຊີ້ບອກວ່າບໍ່ພົບ deserializer ສຳລັບປະເພດໂມເດລທີ່ໃຊ້ໂດຍ <b>Tetra</b> ຫຼື ສ່ວນເສີມຂອງມັນ.</p>"
				+ "<p><b>ຂັ້ນຕອນທີ່ແນະນຳ:</b></p>" + "<ul>"
				+ "<li>ກ່ອນອື່ນໝົດ, ລຶບ ຫຼື ປິດການເຮັດວຽກ <b>addons ຂອງ Tetra</b> ແລ້ວລອງໃໝ່.</li>"
				+ "<li>ຖ້າ ຜິດພາດ ຍັງຄົງຢູ່, ລອງລຶບ <b>Tetra</b> ອອກດ້ວຍ.</li>"
				+ "<li>ທ່ານສາມາດລອງຊອກຫາ addons ທີ່ກ່ຽວຂ້ອງກັບ Tetra ໃນ <b>DepMap</b>, ເຖິງວ່າມັນອາດຈະບໍ່ປາກົດຢູ່ທີ່ນັ້ນສະເໝີໄປ.</li>"
				+ "</ul>" + "<p>ໃນບາງກໍລະນີບັນຫາມາຈາກ addon, ແຕ່ໃນບາງກໍລະນີມັນເກີດຈາກ <b>Tetra</b> ເອງ.</p>";
	}

	@Override
	public String nombreTetraDeserializadorModeloEstatico() {
		return "ຜິດພາດການ deserialize ໂມເດລໃນ Tetra";
	}

	@Override
	public String mensajeSimpleEmotesSetupAnimTail() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ພົບຜິດພາດໃນ Simple Emotes.</b>"
				+ "<p>Log ມີສາຍຄຳ <b>$simpleemotes$setupAnimTAIL</b>, ເຊິ່ງຊີ້ໄປທີ່ mod <b>Simple Emotes</b> ໂດຍກົງ.</p>"
				+ "<p><b>ຕົວເລືອກທີ່ແນະນຳ:</b></p>" + "<ul>"
				+ "<li>ລຶບ ຫຼື ປິດການເຮັດວຽກ <b>Simple Emotes</b> ແລ້ວລອງໃໝ່.</li>"
				+ "<li>ຖ້າທ່ານໃຊ້ mods ທີ່ປ່ຽນ animations ຂອງຜູ້ຫຼິ້ນ ຫຼື ໂມເດລ, ໃຫ້ກວດສອບຄວາມບໍ່ເຂົ້າກັນທີ່ເປັນໄປໄດ້ກັບ <b>Simple Emotes</b>.</li>"
				+ "<li>ອັບເດດ <b>Simple Emotes</b> ແລະ mods ທີ່ກ່ຽວຂ້ອງກັບ animations ໃຫ້ເປັນເວີຊັນທີ່ເຂົ້າກັນໄດ້.</li>"
				+ "</ul>" + "<p>ຜິດພາດ ນີ້ມັກຊີ້ບອກວ່າ <b>Simple Emotes</b> ມີສ່ວນກ່ຽວຂ້ອງໂດຍກົງໃນການລົ້ມເຫຼວ.</p>";
	}

	@Override
	public String nombreSimpleEmotesSetupAnimTail() {
		return "ຜິດພາດໃນ Simple Emotes";
	}

	// En Idioma

	@Override
	public String mensajeAdvertenciaSKLauncher() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "ຄຳເຕືອນກ່ຽວກັບ SKLauncher.</b>"
				+ "<p>ໃນໄລຍະເດືອນຜ່ານມາ ພົບຫຼາຍກໍລະນີຂອງ <b>ການເສຍຫາຍ (corruption)</b> ແລະ ບັນຫາອື່ນໆທີ່ກ່ຽວຂ້ອງກັບ <b>SKLauncher</b>.</p>"
				+ "<p>ສິ່ງນີ້ບໍ່ໄດ້ໝາຍຄວາມວ່າ SKLauncher ເປັນສາເຫດຂອງ ຜິດພາດ ສະເໝີໄປ, ແຕ່ມັນອາດຈະມີສ່ວນເຮັດໃຫ້ເກີດບັນຫາ.</p>"
				+ "<p><b>ສັນຍານຂອງການເສຍຫາຍທີ່ເປັນໄປໄດ້:</b></p>" + "<ul>"
				+ "<li>ເກມປິດລົງໄວຫຼາຍໃນລະຫວ່າງການເລີ່ມຕົ້ນ.</li>"
				+ "<li>ເກມຍັງລົ້ມເຫຼວເຖິງແມ່ນວ່າ <b>ບໍ່ມີ mods ຕິດຕັ້ງ</b>.</li>" + "</ul>"
				+ "<p>ຖ້າເກີດກໍລະນີໃດໜຶ່ງ, ລອງໃຊ້ <b>launcher ອື່ນ</b> ເພື່ອກວດສອບວ່າບັນຫາຫາຍໄປຫຼືບໍ່.</p>"
				+ "<p>ເບິ່ງລາຍຊື່ launchers ທີ່ແນະນຳທີ່ນີ້:</p>"
				+ "<p><a href='https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/docs/ingles/minecraft/Launchers.md'>ເບິ່ງເອກະສານ launchers</a></p>";
	}

	@Override
	public String nombreAdvertenciaSKLauncher() {
		return "ຄຳເຕືອນ: ບັນຫາທີ່ເປັນໄປໄດ້ກັບ SKLauncher";
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
		return "ສະແກນ servers ແລະ malware";
	}

	@Override
	public String guardEscanearServidores() {
		return "ຄົ້ນຫາ servers";
	}

	@Override
	public String guardEscanearMalware() {
		return "ຄົ້ນຫາ malware";
	}

	@Override
	public String guardTablaServidores() {
		return "Servers ທີ່ມີບັນຫາ";
	}

	@Override
	public String guardTablaMalware() {
		return "ຜົນການຊອກຫາ malware";
	}

	@Override
	public String guardColServidor() {
		return "Server";
	}

	@Override
	public String guardColDefinicion() {
		return "ນິຍາມ";
	}

	@Override
	public String guardColMensaje() {
		return "ຂໍ້ຄວາມ";
	}

	@Override
	public String guardColUbicacion() {
		return "ຕຳແໜ່ງ";
	}

	@Override
	public String guardColClase() {
		return "Class";
	}

	@Override
	public String guardColCfr() {
		return "CFR";
	}

	@Override
	public String guardCfr() {
		return "Decompile";
	}

	@Override
	public String guardCfrTitulo() {
		return "Code ທີ່ decompile ແລ້ວ";
	}

	@Override
	public String guardDescripcion1() {
		return "ເຄື່ອງມືນີ້ຊ່ວຍໃນການຊອກຫາ servers ທີ່ມີບັນຫາ ແລະ ຜົນການຊອກຫາ malware ທີ່ເປັນໄປໄດ້ໃນ mods.";
	}

	@Override
	public String guardDescripcion2() {
		return "ອາດມີ false positives, ໂດຍສະເພາະເມື່ອນິຍາມອື່ນໆ ຫຼື scanners malware ມີຄວາມ aggressive.";
	}

	@Override
	public String guardDescripcion3() {
		return "ການກວດສອບ servers ໃຊ້ນິຍາມຈາກພາຍນອກ. ຖ້າທ່ານຍັງບໍ່ໄດ້ດາວໂຫຼດ, ທ່ານຈະຕ້ອງດາວໂຫຼດມັນກ່ອນ.";
	}

	@Override
	public String guardDescripcion4() {
		return "ຖ້າທ່ານມີນິຍາມທ້ອງຖິ່ນແລ້ວ, ເຄື່ອງມືຈະໃຫ້ທ່ານຕັດສິນໃຈວ່າຈະໃຊ້ຊ້ຳ ຫຼື ອັບເດດມັນ.";
	}

	@Override
	public String guardDescripcion5() {
		return "ໃນຕາຕະລາງ malware, ຖ້າມີ class ວ່າງຢູ່, ທ່ານສາມາດ decompile ມັນດ້ວຍ CFR ເພື່ອກວດສອບ.";
	}

	@Override
	public String guardEstadoEscaneandoTodo() {
		return "ກຳລັງສະແກນ servers ແລະ malware...";
	}

	@Override
	public String guardEstadoEscaneandoServidores() {
		return "ກຳລັງຊອກຫາ servers ທີ່ມີບັນຫາ...";
	}

	@Override
	public String guardEstadoEscaneandoMalware() {
		return "ກຳລັງຊອກຫາ malware...";
	}

	@Override
	public String guardEstadoListo() {
		return "ພ້ອມ";
	}

	@Override
	public String guardDefsNoEncontradasTitulo() {
		return "ບໍ່ພົບນິຍາມ";
	}

	@Override
	public String guardDefsNoEncontradasMensaje() {
		return "ບໍ່ພົບນິຍາມຂອງ servers. ທ່ານຕ້ອງການດາວໂຫຼດມັນດຽວນີ້ບໍ່?";
	}

	@Override
	public String guardDefsDescargar() {
		return "ດາວໂຫຼດ";
	}

	@Override
	public String guardDefsCancelar() {
		return "ຍົກເລີກ";
	}

	@Override
	public String guardDefsActualizarTitulo() {
		return "ນິຍາມຂອງ servers";
	}

	@Override
	public String guardDefsActualizarMensaje() {
		return "ມີນິຍາມທ້ອງຖິ່ນຢູ່ແລ້ວ. ທ່ານຕ້ອງການໃຊ້ຕາມທີ່ມີ ຫຼື ອັບເດດມັນ?";
	}

	@Override
	public String guardDefsUsarLocales() {
		return "ໃຊ້ແບບທ້ອງຖິ່ນ";
	}

	@Override
	public String guardDefsActualizar() {
		return "ອັບເດດ";
	}

	@Override
	public String guardFuenteDefinicionesTLauncher() {
		return "ລາຍຊື່ຂອງ TLauncher";
	}

	@Override
	public String guardErrorDescompilar() {
		return "ຜິດພາດໃນການ decompile";
	}

	@Override
	public String guardColorPanel() {
		return "ສີຂອງແຜງ";
	}

	@Override
	public String guardColorTexto() {
		return "ສີຂອງຕົວໜັງສື";
	}

	@Override
	public String guardColorTextoSecundario() {
		return "ສີຂອງຕົວໜັງສືຮອງ";
	}

	@Override
	public String guardColorTabla() {
		return "ສີຂອງຕາຕະລາງ";
	}

	@Override
	public String guardColorSeleccion() {
		return "ສີການເລືອກ";
	}

	@Override
	public String guardColorSeleccionTexto() {
		return "ສີຂອງຕົວໜັງສືທີ່ຖືກເລືອກ";
	}

	@Override
	public String texto_de_boton_compartir_instancia_modpack() {
		return "ແບ່ງປັນ instance/modpack";
	}

	@Override
	public String popup_compartir_instancia_modpack() {
		return "ຟັງຊັນສຳລັບແບ່ງປັນ instance ຫຼື modpack ຍັງບໍ່ໄດ້ຖືກຈັດຕັ້ງປະຕິບັດ.";
	}

	@Override
	public String colorBotonCompartirVerdeOscuro() {
		return "ສີຂອງປຸ່ມແບ່ງປັນຫຼັກ";
	}

	@Override
	public String colorBotonCompartirVerdeClaro() {
		return "ສີຂອງປຸ່ມແບ່ງປັນລິ້ງ";
	}

	@Override
	public String colorTextoBotonesCompartir() {
		return "ສີຕົວໜັງສືຂອງປຸ່ມແບ່ງປັນ";
	}

	@Override
	public String compartirInstanciaTitulo() {
		return "ແບ່ງປັນ instance";
	}

	@Override
	public String compartirInstanciaBotonLateral() {
		return "ແບ່ງປັນ instance";
	}

	@Override
	public String compartirInstanciaFormato() {
		return "ຮູບແບບ";
	}

	@Override
	public String compartirInstanciaServicio() {
		return "ບໍລິການອັບໂຫຼດ";
	}

	@Override
	public String compartirInstanciaBotonCompartir() {
		return "ບັນຈຸ ແລະ ແບ່ງປັນ";
	}

	@Override
	public String compartirInstanciaBotonRefrescar() {
		return "ໂຫຼດໃໝ່";
	}

	@Override
	public String compartirInstanciaEstadoListo() {
		return "ພ້ອມ";
	}

	@Override
	public String compartirInstanciaEstadoEmpaquetando() {
		return "ກຳລັງບັນຈຸສ່ວນທີ່ເລືອກ...";
	}

	@Override
	public String compartirInstanciaEstadoSubiendo() {
		return "ກຳລັງອັບໂຫຼດໄຟລ໌...";
	}

	@Override
	public String compartirInstanciaEstadoError() {
		return "ຜິດພາດ";
	}

	@Override
	public String compartirInstanciaCodigo() {
		return "ລະຫັດ";
	}

	@Override
	public String compartirInstanciaEnlace() {
		return "ລິ້ງ";
	}

	@Override
	public String compartirInstanciaMantenerAbierto() {
		return "ທ່ານຕ້ອງຮັກສາ application ໃຫ້ເປີດໄວ້ເພື່ອໃຫ້ການໂອນຍັງຄົງຢູ່.";
	}

	@Override
	public String compartirInstanciaSinSeleccion() {
		return "ບໍ່ມີໂຟລເດີ ຫຼື ໄຟລ໌ທີ່ຖືກເລືອກ.";
	}

	@Override
	public String compartirInstanciaFormatoNoSoportado() {
		return "ຮູບແບບນັ້ນຍັງບໍ່ໄດ້ຮັບການສະໜັບສະໜູນ.";
	}

	@Override
	public String compartirInstanciaServicioNoDisponible() {
		return "ບໍລິການທີ່ເລືອກບໍ່ມີຢູ່.";
	}

	@Override
	public String compartirInstanciaSubidaCompleta() {
		return "ເລີ່ມການໂອນສຳເລັດ.";
	}

	@Override
	public String compartirInstanciaErrorSubir() {
		return "ບໍ່ສາມາດອັບໂຫຼດໄຟລ໌ທີ່ເລືອກໄດ້.";
	}

	@Override
	public String compartirInstanciaColorPanel() {
		return "ສີຂອງແຜງ";
	}

	@Override
	public String compartirInstanciaColorTexto() {
		return "ສີຂອງຕົວໜັງສື";
	}

	@Override
	public String compartirInstanciaPolitica1() {
		return "ປະເພດທີ່ແນະນຳ: mods, configs, saves, worlds, datapacks, resource packs ແລະ ໄຟລ໌ options. ຫຼີກລ່ຽງການລວມເອົາວັດສະດຸສ່ວນຕົວທີ່ບໍ່ຈຳເປັນ.";
	}

	@Override
	public String compartirInstanciaPolitica2() {
		return "Extensions ສາມາດເພີ່ມບໍລິການອັບໂຫຼດຂອງພວກມັນເອງ. ບໍລິການທີ່ມາພ້ອມໂດຍຄ່າເລີ່ມຕົ້ນຄວນສະແດງຢູ່ທີ່ນີ້.";
	}

	@Override
	public String compartirInstanciaPolitica3() {
		return "wormhole.app: ສູງສຸດ 5 GiB ສຳລັບການອັບໂຫຼດປົກກະຕິ; ລະຫວ່າງ 5 ແລະ 10 GiB ຕ້ອງຮັກສາຝັ່ງສົ່ງໃຫ້ເປີດໄວ້. ໃນການ implement ປະຈຸບັນຂອງໂຄງການ, ການ integrate ຕົວຈິງຍັງຄ້າງຢູ່.";
	}

	@Override
	public String compartirInstanciaPolitica4() {
		return "limewire.com: ອອກແບບມາເປັນບໍລິການທີ່ເກັບຮັກສາຊົ່ວຄາວ. ຍັງບໍ່ໄດ້ຮັບການສະໜັບສະໜູນໂດຍການ implement ນີ້.";
	}

	@Override
	public String compartirInstanciaPolitica5() {
		return "bittorrent: ໂໝດທີ່ປອດໄພທີ່ສຸດເນື່ອງຈາກເປັນການແຈກຢາຍ P2P ໂດຍກົງ, ບໍ່ມີ central hosting. ຍັງບໍ່ໄດ້ຮັບການສະໜັບສະໜູນໂດຍການ implement ນີ້.";
	}

	@Override
	public String compartirInstanciaPolitica6() {
		return "ໂດຍຄ່າເລີ່ມຕົ້ນຈະເລືອກໂຟລເດີ ແລະ ໄຟລ໌ທີ່ພົບເລື້ອຍທີ່ສຸດຂອງ instance ເພື່ອອຳນວຍຄວາມສະດວກໃນການຊ່ວຍເຫຼືອທາງດ້ານເຕັກນິກ.";
	}

	@Override
	public String compartirInstanciaPolitica7() {
		return "ຖ້າທ່ານລວມເອົາໂຟລເດີພາຍໃນຂອງ CrashDetector, ການຕັ້ງຄ່າ, logs ແລະ ຂໍ້ມູນຊ່ວຍເຫຼືອກໍ່ຈະຖືກສົ່ງໄປນຳ, ດັ່ງນັ້ນທ່ານສາມາດຍົກເລີກການເລືອກມັນໄດ້ຖ້າບໍ່ຈຳເປັນ.";
	}

	@Override
	public String malware_fracturiser_detectado() {
		return "ພົບ Fracturiser ທີ່ເປັນໄປໄດ້. ຫຼັກຖານ:";
	}

	@Override
	public String malware_info_stealer_detectado() {
		return "ພົບໂປຣແກຣມຂโมຍຂໍ້ມູນທີ່ເປັນໄປໄດ້. ຫຼັກຖານ:";
	}

	@Override
	public String malware_evidencia_clase_sospechosa() {
		return "Class ທີ່ນ่าสงสัย:";
	}

	@Override
	public String malware_evidencia_archivo_sospechoso() {
		return "ໄຟລ໌ ທີ່ນ่าสงสัย:";
	}

	@Override
	public String malware_brightsdk_detectado() {
		return "ພົບ Bright SDK ທີ່ເປັນໄປໄດ້. ຫຼັກຖານ:";
	}

	@Override
	public String malware_evidencia_paquete_sospechoso() {
		return "Package ທີ່ນ่าสงสัย:";
	}

	@Override
	public String docsTituloVentana() {
		return "ຕົວອ່ານເອກະສານ";
	}

	@Override
	public String docsAyudaDeUso() {
		return "<b>ວິທີໃຊ້ຕົວອ່ານນີ້</b><br>" + "ເລືອກພາສາຢູ່ດ້ານລຸ່ມເພື່ອເບິ່ງເອກະສານທີ່ມີຢູ່ໃນພາສານັ້ນ.<br>"
				+ "ໃນແຜງດ້ານຊ້າຍທ່ານສາມາດນຳທາງຜ່ານໂຟລເດີ ແລະ ເອກະສານ.<br>"
				+ "ເມື່ອຄລິກໃສ່ເອກະສານ, ເນື້ອຫາຂອງມັນຈະປາກົດຢູ່ດ້ານຂວາ.<br>"
				+ "ລິ້ງພາຍໃນທີ່ມີ protocol <b>docs://</b> ຈະເປີດເອກະສານອື່ນໆພາຍໃນຕົວອ່ານນີ້.";
	}

	@Override
	public String docsArbolTitulo() {
		return "ເອກະສານ";
	}

	@Override
	public String docsVisorTitulo() {
		return "ເນື້ອຫາ";
	}

	@Override
	public String docsNoHayDocumentos() {
		return "ບໍ່ມີເອກະສານສຳລັບພາສານີ້.";
	}

	@Override
	public String docsNoHayMarkdown() {
		return "ບໍ່ພົບໄຟລ໌ Markdown ໃນພາສານີ້.";
	}

	@Override
	public String docsDocumentoNoEncontrado() {
		return "ບໍ່ພົບເອກະສານທີ່ຕ້ອງການ.";
	}

	@Override
	public String docsErrorAlAbrirDocumento() {
		return "ຜິດພາດໃນການເປີດເອກະສານ:";
	}

	@Override
	public String docsCargando() {
		return "ກຳລັງໂຫລດ ເອກະສານ...";
	}

	@Override
	public String docsImagenNoDisponible() {
		return "ບໍ່ມີຮູບພາບ";
	}

	@Override
	public String colorPanelSecundario() {
		return "ສີຂອງແຜງຮອງ";
	}

	@Override
	public String colorTextoSuave() {
		return "ສີຂອງຕົວໜັງສືອ່ອນ";
	}

	@Override
	public String colorSeleccion() {
		return "ສີການເລືອກ";
	}

	@Override
	public String colorFondoDocumento() {
		return "ສີພື້ນຫຼັງຂອງເອກະສານ";
	}

	@Override
	public String iaTituloVentana() {
		return "IA";
	}

	@Override
	public String iaTituloPrincipal() {
		return "ການວິເຄາະດ້ວຍ IA";
	}

	@Override
	public String iaDescripcionTitulo() {
		return "ຕົວແທນວິເຄາະ crashes";
	}

	@Override
	public String iaDescripcionCuerpo() {
		return "ເຄື່ອງມືນີ້ເປີດຕົວແທນພາຍນອກທີ່ສາມາດຊ່ວຍທ່ານວິເຄາະ crashes, ຜິດພາດes ແລະ logs ທີ່ກ່ຽວຂ້ອງກັບ Minecraft.";
	}

	@Override
	public String iaDescripcionUso() {
		return "ເພື່ອໃຊ້ລະບົບນີ້, ໃຫ້ເປີດລິ້ງ, ເຂົ້າສູ່ລະບົບດ້ວຍບັນຊີ Baidu ແລ້ວໃຊ້ຕົວແທນເພື່ອກວດສອບ crash ຫຼື logs ຂອງທ່ານ.";
	}

	@Override
	public String iaAvisoCuentaBaidu() {
		return "ທ່ານຈະຕ້ອງມີບັນຊີ Baidu ເພື່ອເຂົ້າເຖິງຕົວແທນ.";
	}

	@Override
	public String iaCopiarEnlace() {
		return "ຄັດລອກລິ້ງ";
	}

	@Override
	public String iaAbrirEnlace() {
		return "ເປີດ ລິ້ງ";
	}

	@Override
	public String iaImagenNoDisponible() {
		return "ບໍ່ມີຮູບພາບ";
	}

}
