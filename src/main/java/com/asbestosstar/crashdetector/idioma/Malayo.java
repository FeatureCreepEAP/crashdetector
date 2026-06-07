package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Statics;

public class Malayo extends Indonesia {
	Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		return "ms";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "malayo";
	}

	@Override
	public String nombre_del_idioma() {
		return "Bahasa Melayu";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_malasia.png");
	}

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>bukan folder mods yang sah</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Tidak diketahui di mana fail JAR "
				+ Statics.nombre_cd.obtener() + " berada</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Mencari PID: " + String.valueOf(pid) + "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") telah mati!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>JVM tidak ditemui</span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Ini ialah GUI " + Statics.nombre_cd.obtener()
				+ ". Jika permainan ditutup tanpa masalah, abaikan ini.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>Lihat Laporan</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>Lihat laporan tempatan dalam pelayar anda.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "Kongsi Laporan";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "Kongsi laporan. Log anda akan dimuat naik ke securelogger.net dan laporan anda akan dimuat naik ke tapak lain.";
	}

	@Override
	public String analisisAvanzado() {
		return "Analisis Lanjutan";
	}

	@Override
	public String seleccionarCarpeta() {
		return "Pilih Folder";
	}

	@Override
	public String cadenaBusqueda() {
		return "Rentetan Carian";
	}

	@Override
	public String usarRegex() {
		return "Gunakan Regex";
	}

	@Override
	public String ignorarMayusculas() {
		return "Abaikan Huruf Besar/Kecil";
	}

	@Override
	public String buscar() {
		return "Cari";
	}

	@Override
	public String busquedaEnProgreso() {
		return "Carian sedang dijalankan";
	}

	@Override
	public String noSeEncontraronResultados() {
		return "Tiada hasil ditemui";
	}

	@Override
	public String errorBusqueda() {
		return "Ralat semasa carian";
	}

	@Override
	public String omitirYCerrar() {
		return "Langkau dan Tutup";
	}

	@Override
	public String guardarYCerrar() {
		return "Simpan dan Tutup";
	}

	@Override
	public String pegaLosRegistrosAqui() {
		return "Tampal log di sini";
	}

	@Override
	public String archivo() {
		return "Fail";
	}

	@Override
	public String incluir() {
		return "Sertakan";
	}

	@Override
	public String abrir() {
		return "Buka";
	}

	@Override
	public String endpointDeInforme() {
		return "Titik Akhir Laporan";
	}

	@Override
	public String sitoDeLogging() {
		return "Tapak Logging:";
	}

	@Override
	public String apiDeLogging() {
		return "API Logging:";
	}

	@Override
	public String anonimizarRegistros() {
		return "Anonimkan log";
	}

	@Override
	public String botonDeCompartirInforme() {
		return "Kongsi laporan dan semua log yang dipilih";
	}

	@Override
	public String enlaceDelReporte() {
		return "Pautan Laporan:";
	}

	@Override
	public String guardarConfigDeCompartir() {
		return "Simpan Konfigurasi Perkongsian";
	}

	@Override
	public String registroDemasiadoGrande() {
		return "Log terlalu besar untuk tapak log ini. Pilih tapak lain dan cuba lagi.";
	}

	@Override
	public String errorConPublicarRegistro(String error) {
		return "Ralat semasa menerbitkan log " + error;
	}

	@Override
	public String apiDeRegistroNoExiste() {
		return "API log tidak tersedia. Sila ubah API log dalam tetapan.";
	}

	@Override
	public String errorSSL() {
		return "Anda mengalami ralat SSL. Ini lazim berlaku pada versi Java lama, "
				+ "termasuk Java 8 dalam launcher Minecraft lalai serta versi dari sun.com dan java.com. "
				+ "Ini menjejaskan banyak perkara seperti fail JAR pemasang MinecraftForge, ciri perkongsian laporan "
				+ Statics.nombre_cd.obtener()
				+ " apabila menggunakan titik akhir lalai, beberapa mod yang memerlukan internet, "
				+ "dan beberapa tapak logging. Jika ini berlaku semasa cuba berkongsi laporan, "
				+ "lampirkan sahaja tangkapan skrin dan pilih tapak logging yang serasi dengan Java 8 lama.";
	}

	@Override
	public String escanear() {
		return "Imbas";
	}

	@Override
	public String cargando() {
		return "Memuatkan";
	}

	@Override
	public String inicioApp() {
		return "Mulakan Permainan/App";
	}

	@Override
	public String ajustesCrashDetector() {
		return "Tetapan " + Statics.nombre_cd.obtener();
	}

	@Override
	public String confidencialidad() {
		return "Privasi";
	}

	@Override
	public String tooltip() {
		return "Tip alat";
	}

	@Override
	public String config() {
		return "Konfigurasi";
	}

	@Override
	public String abrirCarpeta() {
		return "Buka Folder";
	}

	@Override
	public String actualizar() {
		return "Kemas Kini";
	}

	@Override
	public String anadirRegistro() {
		return "Tambah Log";
	}

	@Override
	public String usarIdiomaDelSistema() {
		return "Gunakan bahasa sistem";
	}

	@Override
	public String volver() {
		return "Kembali";
	}

	@Override
	public String colorFondo() {
		return "Warna latar belakang (#RRGGBB):";
	}

	@Override
	public String colorTexto() {
		return "Warna teks (#RRGGBB):";
	}

	@Override
	public String colorBoton() {
		return "Warna butang (#RRGGBB):";
	}

	@Override
	public String colorCajaTexto() {
		return "Warna kotak teks (#RRGGBB):";
	}

	@Override
	public String colorEnlace() {
		return "Warna pautan (#RRGGBB):";
	}

	@Override
	public String colorTitulosConsolas() {
		return "Warna tajuk konsol (#RRGGBB):";
	}

	@Override
	public String colorError() {
		return "Warna ralat (#RRGGBB):";
	}

	@Override
	public String colorAdvertencia() {
		return "Warna amaran (#RRGGBB):";
	}

	@Override
	public String colorInfo() {
		return "Warna maklumat (#RRGGBB):";
	}

	@Override
	public String colorTitulo() {
		return "Warna tajuk (#RRGGBB):";
	}

	@Override
	public String colorEnlaceTexto() {
		return "Warna teks pautan (#RRGGBB):";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "Buka " + Statics.nombre_cd.obtener() + " hanya apabila berlaku crash";
	}

	@Override
	public String activar_parche() {
		return "Aktifkan Tampalan";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "Tiada Penyelesaian Tersedia";
	}

	@Override
	public String error() {
		return "ralat";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "Gagal memadam JAR";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "JAR berjaya dipadam";
	}

	@Override
	public String exito() {
		return "berjaya";
	}

	@Override
	public String eliminar() {
		return "padam";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "Gagal memadam pakej";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "Pakej berjaya dipadam";
	}

	@Override
	public String eliminar_paquete() {
		return "Padam pakej";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "Tiada mod ditemui dengan pakej tersebut";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "Tidak dapat memadam pakej";
	}

	@Override
	public String eliminar_jar() {
		return "Padam JAR";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "Tiada mod pendua ditemui";
	}

	@Override
	public String archivo_no_encontrado() {
		return "Fail tidak ditemui";
	}

	@Override
	public String directorio_eliminado() {
		return "Direktori dipadam";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "Gagal memadam JAR bersarang";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "Fail dalaman tidak ditemui";
	}

	@Override
	public String archivo_eliminado() {
		return "Fail dipadam";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "Gagal memadam fail";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "Fail luaran tidak sah";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "Elemen mod dipadam";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "Gagal menggantikan JAR luaran";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "Gagal memadam elemen mod";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "Gagal memadam direktori";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "Format tidak sah untuk JAR bersarang";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "JAR bersarang dipadam";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "Gagal membersihkan fail sementara";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Tiada hasil</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Lokasi Log:</b>";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "Log Launcher Tidak Ditemui";
	}

	@Override
	public String imagenNoEncontrada() {
		return "Imej tidak ditemui";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "Pautan dengan Markdown: Tampal di sini sebarang pautan yang mengandungi log dalam format Markdown. Sistem akan cuba mengekstrak pautan ke log (latest.log, launcher_log.txt, debug.log, dll.) dan menganalisisnya secara automatik.";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "GENERIC: Pilih jenis launcher yang anda gunakan. Log launcher (launcher_log.txt, stdout, dll.) mengandungi maklumat penting tentang ralat yang tidak muncul dalam latest.log. "
				+ Statics.nombre_cd.obtener()
				+ " tidak dapat membaca log Launcher anda; mungkin tiada fail log dan anda perlu menampal log secara manual.<br>"
				+ "Untuk maklumat lanjut, lihat <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">isu ini</a>. Log ini mengandungi output standard (STDOUT), yang diperlukan untuk mendiagnosis banyak jenis ralat.";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher): Anda perlu memilih folder tempat HMCL dipasang dan memilih folder \".hcml\". Log HMCL disimpan di lokasi ini dan mengandungi maklumat penting tentang ralat.<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: Launcher ini mempunyai tab pembangunan yang memaparkan log terperinci. Anda boleh menemuinya dalam menu pilihan launcher.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: Terdapat dialog popup dengan log. Ia mempunyai butang untuk menyalin dan memuat naik log. Log dijana secara automatik apabila permainan dimulakan dan mengandungi maklumat kritikal untuk diagnosis.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: Klik kanan pada instance dan pilih \"Konfigurasi\". Kemudian pergi ke bahagian Log untuk melihat maklumat penting yang mengandungi output standard (STDOUT).";
	}

	@Override
	public String quitar() {
		return "Padam";
	}

	@Override
	public String rutaArchivo() {
		return "Laluan fail";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "Laluan yang dipilih berada di luar direktori permainan semasa. "
				+ "Hanya fail dan folder dalam direktori semasa atau subdirektorinya dibenarkan.";
	}

	@Override
	public String quitarLanzador() {
		return "Padam launcher";
	}

	@Override
	public String editarRazones() {
		return "Edit sebab";
	}

	@Override
	public String agregarNuevoIdioma() {
		return "Tambah bahasa baharu";
	}

	@Override
	public String aceptar() {
		return "Terima";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "Warna hasil betul";
	}

	@Override
	public String gpu_crash_causas() {
		return "Punca yang mungkin: Driver lapuk, masalah OpenGL, atau GPU lama yang tidak serasi.";
	}

	@Override
	public String gpu_crash_recomendaciones() {
		return "Cadangan: Kemas kini driver, paksa penggunaan GPU yang betul, dan elakkan persekitaran grafik yang bermasalah.";
	}

	@Override
	public String gpu_no_optima() {
		return "Permainan tidak menggunakan GPU terbaik yang tersedia.";
	}

	@Override
	public String gpu_no_optima_detalles() {
		return "Ini boleh mengurangkan prestasi (FPS rendah), tetapi biasanya tidak menyebabkan crash dengan sendirinya.";
	}

	@Override
	public String gpu_recomendaciones_rendimiento() {
		return "Cadangan: Paksa penggunaan GPU khusus dan konfigurasikan Java/Minecraft dengan betul.";
	}

	@Override
	public String gpu_nota_precision() {
		return "<b>Nota:</b> Sistem pengesanan ini tidak 100% sempurna.";
	}

	@Override
	public String gpu_consumo_energia() {
		return "GPU yang lebih berkuasa menggunakan lebih banyak tenaga dan boleh mengurangkan hayat bateri pada komputer riba.";
	}

	@Override
	public String gpu_parche_info() {
		return "Anda boleh melumpuhkan pemeriksaan ini menggunakan butang penyelesaian pantas.";
	}

	@Override
	public String auditorias_transformer() {
		return "Audit Transformer";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Berikut ialah hasil kandungan Audit Transformer pada Launcher Vanilla. Biasanya ia kurang tepat berbanding analisis StackTrace, tetapi Launcher Vanilla tidak semestinya mempunyai kandungan {}.</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "Ini mencari mod yang dibuat dengan MCreator. Walaupun banyak mod MCreator bagus, kadangkala ia mempunyai masalah dan reputasi yang kurang baik. Ini membantu mengenal pastinya. Perlu diingat, malah mod yang popular mungkin hanya mempunyai integrasi MCreator dan bukan dibina sepenuhnya dengannya.";
	}

	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' mempunyai beberapa versi yang dipasang.</b> ";
	}

	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "Mod pendua dalam Fabric";
	}

	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "Padam fail mod pendua: " + rutaMod;
	}

	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + primerMod + "' dan '" + segundoMod
				+ "' tidak serasi antara satu sama lain.</b> ";
	}

	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "Mod tidak serasi dalam Fabric";
	}

	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "Padam mod '" + nombreMod + "'";
	}

	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' mempunyai ralat fatal dan tidak boleh dijalankan.</b> ";
	}

	@Override
	public String nombreProblemaModFatal() {
		return "Mod dengan ralat fatal";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod berikut diperlukan tetapi belum dipasang: "
				+ deps.toString() + ".</b>";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod + "' memerlukan mod '"
					+ dependencia + "' sebagai dependensi.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod + "' memerlukan mod '"
					+ dependencia + "' dengan versi " + version + ".</b>";
		}
	}

	@Override
	public String nombreProblemaDependenciaMod() {
		return "Dependensi mod yang hilang";
	}

	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "Pasang mod '" + nombreMod + "'";
	}

	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "Pasang mod '" + nombreMod + "' dengan versi " + version;
	}

	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Plugin '" + nombrePlugin
				+ "' tidak serasi dengan ticking serantau Folia.</b> ";
	}

	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Plugin berikut tidak serasi dengan ticking serantau Folia: ");

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
		return "Plugin tidak serasi dengan ticking serantau";
	}

	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "Pasang perisian tanpa ticking serantau, seperti " + software;
	}

	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod + "' tiada dalam datapack.</b>";
	}

	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Mod berikut tiada dalam datapack: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" dan ");
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
		return "Mod tiada dalam datapack";
	}

	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' telah menghasilkan ralat.</b>";
	}

	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Mod berikut telah menghasilkan ralat: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" dan ");
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
		return "Mod Forge dengan pengecualian";
	}

	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "Pasang versi lain bagi mod '" + nombreMod + "'";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' tidak serasi dengan versi Minecraft " + versionMC + ".</b>";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Mod berikut tidak serasi dengan versi Minecraft: ");

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

	@Override
	public String nombreProblemaModIncompatibleConMinecraft() {
		return "Mod tidak serasi dengan Minecraft";
	}

	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "Pasang versi Forge yang serasi dengan Minecraft " + versionMC;
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod '" + nombreMod
				+ "' tiada dan diperlukan untuk memuatkan dunia atau plugin.</b>";
	}

	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "Mod yang tiada";
	}

	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Dunia disimpan dengan mod '" + nombreMod
				+ "' yang kini nampaknya sudah tiada.</b>";
	}

	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Dunia disimpan dengan mod berikut yang kini nampaknya sudah tiada: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" dan ");
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
		return "Mod tiada dalam dunia";
	}
}
