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

	@Override
	public String gitAvanzadoTipo() {
		return "Klien Git lanjutan";
	}

	@Override
	public String jgitAbrirClienteAvanzado() {
		return "Buka klien Git lanjutan";
	}

	@Override
	public String gitAvanzadoTitulo() {
		return "Klien Git lanjutan — Wendy Mizumi: Lost Media";
	}

	@Override
	public String gitAvanzadoSerieLostMedia() {
		return "Wendy Mizumi — Lost Media / arkib repositori semasa";
	}

	@Override
	public String gitAvanzadoRepositorio() {
		return "Repositori";
	}

	@Override
	public String gitAvanzadoRama() {
		return "Cabang";
	}

	@Override
	public String gitAvanzadoActualizar() {
		return "Muat semula";
	}

	@Override
	public String gitAvanzadoFetch() {
		return "Ambil";
	}

	@Override
	public String gitAvanzadoPull() {
		return "Tarik";
	}

	@Override
	public String gitAvanzadoPush() {
		return "Tolak";
	}

	@Override
	public String gitAvanzadoNuevaRama() {
		return "Cabang baharu";
	}

	@Override
	public String gitAvanzadoHistorial() {
		return "Sejarah commit";
	}

	@Override
	public String gitAvanzadoNoPreparados() {
		return "Perubahan belum distage";
	}

	@Override
	public String gitAvanzadoPreparados() {
		return "Perubahan telah distage";
	}

	@Override
	public String gitAvanzadoPrepararSeleccion() {
		return "Stage pilihan";
	}

	@Override
	public String gitAvanzadoPrepararTodo() {
		return "Stage semua";
	}

	@Override
	public String gitAvanzadoQuitarSeleccion() {
		return "Unstage pilihan";
	}

	@Override
	public String gitAvanzadoQuitarTodo() {
		return "Unstage semua";
	}

	@Override
	public String gitAvanzadoVerDiff() {
		return "Lihat diff";
	}

	@Override
	public String gitAvanzadoVerBlame() {
		return "Lihat blame";
	}

	@Override
	public String gitAvanzadoMensajeCommit() {
		return "Mesej commit";
	}

	@Override
	public String gitAvanzadoCommit() {
		return "Cipta commit";
	}

	@Override
	public String gitAvanzadoDiff() {
		return "Diff";
	}

	@Override
	public String gitAvanzadoBlame() {
		return "Blame";
	}

	@Override
	public String gitAvanzadoActividad() {
		return "Aktiviti";
	}

	@Override
	public String gitAvanzadoActualizando() {
		return "Memuat semula repositori semasa...";
	}

	@Override
	public String gitAvanzadoJGitNoDisponible() {
		return "JGit tidak tersedia. Pasang kebergantungan daripada GUI Git biasa, kemudian mulakan semula aplikasi.";
	}

	@Override
	public String gitAvanzadoRepositorioRequerido() {
		return "Klien lanjutan hanya boleh dibuka selepas repositori bagi instans semasa dicipta melalui GUI Git biasa.";
	}

	@Override
	public String gitAvanzadoEstadoResumen(int noPreparados, int preparados, int commits) {
		return "Belum distage: " + noPreparados + " | Telah distage: " + preparados + " | Commit dipaparkan: "
				+ commits;
	}

	@Override
	public String gitAvanzadoMensajeRequerido() {
		return "Masukkan mesej commit.";
	}

	@Override
	public String gitAvanzadoNombreNuevaRama() {
		return "Nama cabang baharu:";
	}

	@Override
	public String gitAvanzadoConfirmarCambioRama(String rama) {
		return "Tukar ke cabang '" + rama + "'? Perubahan tempatan mestilah serasi dengan pertukaran cabang.";
	}

	@Override
	public String gitAvanzadoErrorOperacion() {
		return "Operasi Git tidak dapat diselesaikan. Semak tab Aktiviti dan log CrashDetector.";
	}

	@Override
	public String gitAvanzadoOperacionCompletada() {
		return "Operasi selesai.";
	}

	@Override
	public String gitAvanzadoOperacionFallida() {
		return "Operasi gagal.";
	}

	@Override
	public String gitAvanzadoActividadActualizado() {
		return "Status dan sejarah telah dimuat semula.";
	}

	@Override
	public String gitAvanzadoActividadFetch() {
		return "Mengambil rujukan remote...";
	}

	@Override
	public String gitAvanzadoActividadPull() {
		return "Memuat turun dan menggabungkan perubahan...";
	}

	@Override
	public String gitAvanzadoActividadPush() {
		return "Menghantar commit ke repositori remote...";
	}

	@Override
	public String gitAvanzadoActividadPreparar() {
		return "Men-stage fail yang dipilih...";
	}

	@Override
	public String gitAvanzadoActividadPrepararTodo() {
		return "Men-stage semua perubahan...";
	}

	@Override
	public String gitAvanzadoActividadQuitarPreparacion() {
		return "Mengeluarkan fail yang dipilih daripada staging area...";
	}

	@Override
	public String gitAvanzadoActividadQuitarTodo() {
		return "Mengosongkan staging area...";
	}

	@Override
	public String gitAvanzadoActividadCommit() {
		return "Mencipta commit...";
	}

	@Override
	public String gitAvanzadoActividadCrearRama(String rama) {
		return "Mencipta dan bertukar ke cabang '" + rama + "'...";
	}

	@Override
	public String gitAvanzadoActividadCambiarRama(String rama) {
		return "Bertukar ke cabang '" + rama + "'...";
	}

	@Override
	public String gitAvanzadoColEstado() {
		return "Status";
	}

	@Override
	public String gitAvanzadoColRuta() {
		return "Laluan";
	}

	@Override
	public String gitAvanzadoColHash() {
		return "Hash";
	}

	@Override
	public String gitAvanzadoColFecha() {
		return "Tarikh";
	}

	@Override
	public String gitAvanzadoColAutor() {
		return "Pengarang";
	}

	@Override
	public String gitAvanzadoColMensaje() {
		return "Mesej";
	}

	@Override
	public String gitAvanzadoEstadoAgregado() {
		return "Ditambah";
	}

	@Override
	public String gitAvanzadoEstadoModificado() {
		return "Diubah";
	}

	@Override
	public String gitAvanzadoEstadoEliminado() {
		return "Dipadam";
	}

	@Override
	public String gitAvanzadoEstadoNoRastreado() {
		return "Tidak dijejak";
	}

	@Override
	public String gitAvanzadoEstadoConflicto() {
		return "Konflik";
	}

	@Override
	public String gitAvanzadoEstadoRenombrado() {
		return "Dinamakan semula";
	}

	@Override
	public String gitAvanzadoWendyImagenAlternativa() {
		return "Wendy Mizumi — Lost Media";
	}

	@Override
	public String gitAvanzadoColorFondo() {
		return "Git lanjutan — latar Lost Media";
	}

	@Override
	public String gitAvanzadoColorPanel() {
		return "Git lanjutan — panel";
	}

	@Override
	public String gitAvanzadoColorPanelSecundario() {
		return "Git lanjutan — panel sekunder";
	}

	@Override
	public String gitAvanzadoColorTexto() {
		return "Git lanjutan — teks";
	}

	@Override
	public String gitAvanzadoColorTextoSuave() {
		return "Git lanjutan — teks lembut";
	}

	@Override
	public String gitAvanzadoColorAzul() {
		return "Git lanjutan — biru arkib";
	}

	@Override
	public String gitAvanzadoColorRosa() {
		return "Git lanjutan — merah jambu reben";
	}

	@Override
	public String gitAvanzadoColorBorde() {
		return "Git lanjutan — sempadan";
	}

	@Override
	public String gitAvanzadoColorTabla() {
		return "Git lanjutan — jadual";
	}

	@Override
	public String gitAvanzadoColorSeleccion() {
		return "Git lanjutan — pilihan";
	}

	@Override
	public String gitAvanzadoColorDiff() {
		return "Git lanjutan — latar diff";
	}

	@Override
	public String gitAvanzadoColorBoton() {
		return "Git lanjutan — butang";
	}

	@Override
	public String errorSistemaSonidoKB4515384() {
		return "<p><b>Windows 10 versi 1903 dikesan dengan kemas kini KB4515384 dan tanpa pembetulan yang diketahui. KB4515384 boleh menyebabkan audio dalam sesetengah permainan menjadi lebih perlahan, berbeza, atau kehilangan saluran. Microsoft membetulkan masalah ini dalam KB4517211. Pasang kemas kini kumulatif Windows yang lebih baharu sebelum menukar mod atau pemacu audio.</b></p>";
	}

	// IDIOMA_GUI_SIEMPRE_ENCIMA_INICIO
	@Override
	public String guiPrincipalSiempreEncima() {
		return "Sentiasa paparkan tetingkap utama di atas";
	}
	// IDIOMA_GUI_SIEMPRE_ENCIMA_FIN

	// CD_BINARY_SEARCH_TRANSLATIONS

	@Override
	public String busquedaBinariaModsBotonLateral() {
		return "Carian binari mod";
	}

	@Override
	public String busquedaBinariaModsTitulo() {
		return "Carian binari mod — Anya Nyabyss";
	}

	@Override
	public String busquedaBinariaModsDescripcion() {
		return "Menyahaktifkan sementara kira-kira separuh mod, melancarkan semula permainan dengan CDLauncher dan mengecilkan senarai suspek berdasarkan keputusan setiap ujian.";
	}

	@Override
	public String busquedaBinariaModsAdvertenciaVariosMods() {
		return "Penting: carian ini menganggap satu mod menyebabkan masalah. Ia tidak boleh dipercayai apabila dua atau lebih mod menyebabkannya secara berasingan, atau apabila kegagalan bergantung pada interaksi beberapa mod.";
	}

	@Override
	public String busquedaBinariaModsProteccionDependencias() {
		return "Perlindungan kebergantungan: apabila suatu kebergantungan dinyahaktifkan, mod yang bergantung padanya turut dinyahaktifkan. Mod yang kekal aktif tidak akan kehilangan kebergantungan yang dikesan.";
	}

	@Override
	public String busquedaBinariaModsIniciar() {
		return "Mulakan carian";
	}

	@Override
	public String busquedaBinariaModsProblemaPersiste() {
		return "Masalah masih berlaku";
	}

	@Override
	public String busquedaBinariaModsProblemaDesaparecio() {
		return "Masalah telah hilang";
	}

	@Override
	public String busquedaBinariaModsRestaurar() {
		return "Pulihkan semua mod";
	}

	@Override
	public String busquedaBinariaModsCandidatos() {
		return "Mod calon";
	}

	@Override
	public String busquedaBinariaModsDesactivados() {
		return "Mod dinyahaktifkan untuk ujian ini";
	}

	@Override
	public String busquedaBinariaModsHistorial() {
		return "Sejarah ujian";
	}

	@Override
	public String busquedaBinariaModsEstado(String clave, int ronda, int candidatos, int desactivados, String detalle) {
		if (clave == null)
			return detalle == null ? "" : detalle;
		switch (clave) {
		case "sin_sesion":
			return "Tiada carian aktif.";
		case "recuperacion":
			return "Terdapat sesi terputus. Pulihkan mod sebelum meneruskan.";
		case "escaneando":
			return "Menganalisis mod dan kebergantungan…";
		case "juego_activo":
			return "Permainan yang dilancarkan semula sedang berjalan.";
		case "restaurando":
			return "Memulihkan mod…";
		case "preparando":
			return "Menyediakan pusingan " + ronda + " (" + candidatos + " / " + desactivados + ")";
		case "lanzando":
			return "Melancarkan semula permainan untuk pusingan " + ronda + " (" + candidatos + " / " + desactivados
					+ ")";
		case "juego":
			return "Permainan berjalan untuk pusingan " + ronda + " (" + candidatos + " / " + desactivados + ")";
		case "esperando":
			return "Permainan telah tamat. Nyatakan sama ada masalah masih berlaku atau telah hilang.";
		case "ronda_persiste":
			return "Pusingan ditanda: masalah masih berlaku.";
		case "ronda_desaparece":
			return "Pusingan ditanda: masalah telah hilang.";
		case "restaurado":
			return "Semua mod telah dipulihkan.";
		case "sin_mods":
			return "Sekurang-kurangnya dua mod aktif diperlukan.";
		case "sin_division":
			return "Baki set tidak boleh dibahagi tanpa memutuskan kebergantungan yang dikesan.";
		case "inconsistente":
			return "Keputusan tidak konsisten. Ini lazimnya bermaksud beberapa punca atau interaksi antara mod.";
		default:
			return detalle == null ? "" : detalle;
		}
	}

	@Override
	public String busquedaBinariaModsConfirmacion() {
		return "Alat akan menamakan semula fail mod buat sementara dan melancarkan semula permainan. Manifes pemulihan akan dibuat. Teruskan?";
	}

	@Override
	public String busquedaBinariaModsResultadoUnico(String mod) {
		return "Mod suspek telah diasingkan: " + mod;
	}

	@Override
	public String busquedaBinariaModsResultadoGrupo(String mods) {
		return "Kumpulan kebergantungan ini tidak dapat dipisahkan: " + mods;
	}

	@Override
	public String busquedaBinariaModsError(String detalle) {
		return "Ralat carian binari: " + detalle;
	}

	@Override
	public String busquedaBinariaModsColor(String clave) {
		if (clave == null)
			return "";
		switch (clave) {
		case "fondo":
			return "Warna latar";
		case "panel":
			return "Warna panel";
		case "texto":
			return "Warna teks";
		case "acento":
			return "Warna aksen";
		case "advertencia":
			return "Warna amaran";
		case "exito":
			return "Warna kejayaan";
		case "borde":
			return "Warna sempadan";
		case "seleccion":
			return "Warna pilihan";
		default:
			return clave;
		}
	}

	@Override
	public String universalatorBoton() {
		return "Universalator";
	}

	@Override
	public String universalatorTitulo() {
		return "Universalator - JellyStara";
	}

	@Override
	public String universalatorCita() {
		return "\"If you are ever feeling stressed out making a server pack, just remember the universalator tool can make the job a whole lot easier-JellyStara , but not actually\"";
	}

	@Override
	public String universalatorDescripcion() {
		return "GUI ini membantu mencari mod yang berkemungkinan hanya untuk klien supaya boleh dikeluarkan daripada pek pelayan. Buat masa ini hanya kaedah Universalator dilaksanakan.";
	}

	@Override
	public String universalatorAdvertenciaSoloCliente() {
		return "Imbasan ini hanyalah panduan. Semak hasil sebelum mengeluarkan mod. Dua kaedah lain belum dilaksanakan.";
	}

	@Override
	public String universalatorMetodoUniversalator() {
		return "Gunakan Universalator untuk mencari mod klien yang hendak dikeluarkan";
	}

	@Override
	public String universalatorMetodoMetadatosLocales() {
		return "Semak metadata tempatan fail mod";
	}

	@Override
	public String universalatorMetodoMetadatosRemotos() {
		return "Semak metadata fail Modrinth dan CurseForge";
	}

	@Override
	public String universalatorNoImplementado() {
		return "belum dilaksanakan";
	}

	@Override
	public String universalatorMetodosTitulo() {
		return "Kaedah carian";
	}

	@Override
	public String universalatorEscanear() {
		return "Imbas mod klien";
	}

	@Override
	public String universalatorActualizar() {
		return "Segarkan paparan";
	}

	@Override
	public String universalatorEstadoListo() {
		return "Sedia.";
	}

	@Override
	public String universalatorEstadoEscaneando() {
		return "Sedang mengimbas mod... sila tunggu.";
	}

	@Override
	public String universalatorResultadoTitulo() {
		return "Mod klien yang berkemungkinan";
	}

	@Override
	public String universalatorReporteTitulo() {
		return "Laporan";
	}

	@Override
	public String universalatorSinResultados() {
		return "Tiada mod yang kelihatan khusus untuk klien ditemui.";
	}

	@Override
	public String universalatorBotonElegirEliminar() {
		return "Pilih mod untuk dikeluarkan...";
	}

	@Override
	public String universalatorDialogoElegirMods() {
		return "Pilih mod untuk dikeluarkan";
	}

	@Override
	public String universalatorConfirmarEliminar() {
		return "Pilih mod yang hendak dikeluarkan daripada pek pelayan. Untuk keselamatan, mod akan dipindahkan ke folder sandaran dan bukan dipadam secara senyap.";
	}

	@Override
	public String universalatorEliminadosExito() {
		return "Mod dipindahkan ke folder sandaran";
	}

	@Override
	public String universalatorNadaSeleccionado() {
		return "Tiada mod dipilih.";
	}

	@Override
	public String universalatorErrorEscaneo() {
		return "Ralat semasa mengimbas mod.";
	}

	@Override
	public String universalatorErrorEliminar() {
		return "Ralat semasa mengeluarkan mod yang dipilih.";
	}

	@Override
	public String universalatorConfianza() {
		return "Keyakinan";
	}

	@Override
	public String universalatorRazones() {
		return "Sebab";
	}

	@Override
	public String universalatorRazonEntornoCliente() {
		return "metadata mengisytiharkan persekitaran klien";
	}

	@Override
	public String universalatorRazonMetadatosCliente() {
		return "metadata menunjukkan penggunaan sisi klien";
	}

	@Override
	public String universalatorRazonHeuristicaNombre() {
		return "nama sepadan dengan heuristik mod klien";
	}

	@Override
	public String universalatorColor(String clave) {
		if ("fondo".equalsIgnoreCase(clave)) {
			return "Warna latar";
		}
		if ("panel".equalsIgnoreCase(clave)) {
			return "Warna panel";
		}
		if ("texto".equalsIgnoreCase(clave)) {
			return "Warna teks";
		}
		if ("acento".equalsIgnoreCase(clave)) {
			return "Warna aksen";
		}
		if ("borde".equalsIgnoreCase(clave)) {
			return "Warna sempadan";
		}
		if ("advertencia".equalsIgnoreCase(clave)) {
			return "Warna amaran";
		}
		if ("exito".equalsIgnoreCase(clave)) {
			return "Warna kejayaan";
		}
		if ("seleccion".equalsIgnoreCase(clave)) {
			return "Warna pilihan";
		}
		return "Warna";
	}

	@Override
	public String errorAccessTransformersTomlInvalido(String nombreJar) {
		return "Ralat kritikal pada mod <b>" + nombreJar
				+ "</b>. Forge menolak bahagian metadata <b>accessTransformers</b> kerana ia diisytiharkan sebagai senarai berbilang objek sedangkan Forge menjangkakan satu jadual sahaja.";
	}

	@Override
	public String nombre_de_error_access_transformers_toml_invalido() {
		return "Struktur accessTransformers tidak sah dalam mods.toml";
	}

	@Override
	public String paso1_access_transformers_toml_invalido(String nombreJar) {
		return "Mod yang bermasalah ialah <b>" + nombreJar + "</b>.";
	}

	@Override
	public String paso2_access_transformers_toml_invalido(String nombreJar) {
		return "Jika anda hanya bermain, kemas kini atau pasang semula <b>" + nombreJar
				+ "</b>, atau gunakan versi mod yang dibina untuk versi Minecraft dan Forge anda.";
	}

	@Override
	public String paso3_access_transformers_toml_invalido() {
		return "Jika anda pembangun mod, buka <b>META-INF/mods.toml</b> dan semak <b>accessTransformers</b>. Ralat ini menunjukkan <b>[[accessTransformers]]</b> digunakan sedangkan versi Forge ini menjangkakan <b>[accessTransformers]</b>; buang satu pasangan kurungan siku seperti yang dinyatakan oleh pengecualian.";
	}

	@Override
	public String opcionInforme() {
		return "Informe";
	}

	@Override
	public String descripcionInforme() {
		return "Tampal URL atau HTML laporan Crash Assistant atau CrashDetector di sini. CrashDetector akan mencari pautan log yang disokong dalam laporan dan mengimportnya secara automatik.";
	}

	@Override
	public String neoforgeWaifuTitulo() {
		return "NeoForge Waifu";
	}

	@Override
	public String neoforgeWaifuDescripcion() {
		return "Kueri indeks NeoForge Waifu melalui GraphQL. Kueri pratetap meliputi operasi utama skema, manakala editor lanjutan boleh menjalankan sebarang kueri GraphQL yang sah.";
	}

	@Override
	public String neoforgeWaifuSinMascota() {
		return "Malangnya, NeoForge Waifu masih belum mempunyai maskot waifu.";
	}

	@Override
	public String neoforgeWaifuTipoConsulta() {
		return "Jenis kueri";
	}

	@Override
	public String neoforgeWaifuNombreOperacion() {
		return "Nama operasi";
	}

	@Override
	public String neoforgeWaifuVariablesJson() {
		return "Pemboleh ubah JSON";
	}

	@Override
	public String neoforgeWaifuConsultaGraphql() {
		return "Kueri GraphQL";
	}

	@Override
	public String neoforgeWaifuEjecutar() {
		return "Jalankan kueri";
	}

	@Override
	public String neoforgeWaifuMostrarEditorAvanzado() {
		return "Tunjukkan editor GraphQL lanjutan";
	}

	@Override
	public String neoforgeWaifuSoloLectura() {
		return "NeoForge Waifu dalam CrashDetector hanya membenarkan kueri GraphQL baca sahaja.";
	}

	@Override
	public String mensajeErrorRenderSystemHiloIncorrectoSodium() {
		return "<b style='color:#Config.obtenerInstancia().obtenerColorError()'>Sodium menyebabkan ralat thread dalam sistem rendering Minecraft.</b><p>Log mengandungi ralat:</p><code>java.lang.IllegalStateException: Rendersystem called from wrong thread</code><p>Ini berlaku apabila Sodium cuba mengakses sistem rendering dari thread yang salah.</p>";
	}

	@Override
	public String nombreErrorRenderSystemHiloIncorrectoSodium() {
		return "Ralat thread dengan Sodium";
	}

	@Override
	public String mensajeErrorEnlazadorNoSoportado(String biblioteca) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>" 
				+ "Platform tidak menyokong paut asli Java (FFM).</b>"
				+ "<p>Ralat berlaku semasa cuba memuat pustaka asli:</p>"
				+ "<code>java.lang.UnsupportedOperationException: Platform does not support native linker</code>";
	}

	@Override
	public String nombreErrorEnlazadorNoSoportado() {
		return "Paut asli tidak disokong oleh platform";
	}

	@Override
	public String mensajeAdvertenciaHotSpotAttachFeatureCreep() {
		return "<b style='color:#FFAA00;'>FeatureCreep tidak dapat melampirkan ejen HotSpot secara langsung.</b><p>Ini jarang menjadi masalah kerana kebanyakan mod tidak memerlukan Instrumentation. FeatureCreep akan meneruskan tanpa ia.</p>";
	}

	@Override
	public String nombreAdvertenciaHotSpotAttachFeatureCreep() {
		return "FeatureCreep: lampiran HotSpot gagal (amaran)";
	}

	@Override
	public String mensajeErrorDependenciaModuloFeatureCreep() {
		return "<b style='color:#FF4444;'>FeatureCreep tidak dapat menyelesaikan kebergantungan modul.</b><p>Sebuah modul menyatakan kebergantungan yang tidak dapat dipenuhi. Permainan mungkin tidak bermula dengan betul.</p><p><b>Solución:</b> Pasang kebergantungan yang hilang atau buang modul yang tidak serasi.</p>";
	}

	@Override
	public String nombreErrorDependenciaModuloFeatureCreep() {
		return "FeatureCreep: kebergantungan modul belum diselesaikan";
	}

	@Override
	public String mensajeErrorVersionModuloFeatureCreep() {
		return "<b style='color:#FF4444;'>FeatureCreep mengesan ketidakserasian versi antara modul.</b><p>Sebuah modul memerlukan versi permainan atau modul lain yang berbeza. Ini boleh menyebabkan kerosakan atau fungsi yang hilang.</p><p><b>Solución:</b> Kemas kini semua modul ke versi yang serasi.</p>";
	}

	@Override
	public String nombreErrorVersionModuloFeatureCreep() {
		return "FeatureCreep: ketidakpadanan versi modul";
	}

	@Override
	public String mensajeErrorMixinBootstrapFeatureCreep() {
		return "<b style='color:#FF4444;'>FeatureCreep tidak dapat memulakan runtime Mixin.</b><p>Mixin diperlukan untuk transformasi kelas. Tanpanya, mod yang menggunakan Mixin tidak akan dimuat dan permainan mungkin rosak.</p><p><b>Solución:</b> Pastikan FeatureCreep dipasang dengan betul dengan sokongan Mixin.</p>";
	}

	@Override
	public String nombreErrorMixinBootstrapFeatureCreep() {
		return "FeatureCreep: kegagalan permulaan Mixin";
	}

	@Override
	public String mensajeErrorModDuplicadoRift() {
		return "<b style='color:#FF4444;'>Rift menemui mod duplikat.</b><p>Dua mod berkongsi ID mod yang sama, yang tidak dibenarkan. Rift tidak dapat memuat kedua-dua mod.</p><p><b>Solución:</b> Buang salah satu mod duplikat.</p>";
	}

	@Override
	public String nombreErrorModDuplicadoRift() {
		return "Rift: ID mod duplikat";
	}

	@Override
	public String mensajeErrorRiftModJSONRift() {
		return "<b style='color:#FF4444;'>Rift tidak dapat membaca riftmod.json.</b><p>Fail manifest mod rosak atau hilang. Rift tidak dapat memuat mod tanpa manifest yang sah.</p><p><b>Solución:</b> Pasang semula mod yang kehilangan fail riftmod.json.</p>";
	}

	@Override
	public String nombreErrorRiftModJSONRift() {
		return "Rift: manifest mod rosak";
	}

	@Override
	public String mensajeErrorListenerRift() {
		return "<b style='color:#FF4444;'>Rift tidak dapat menemui kelas pendengar.</b><p>Sebuah mod merujuk kepada kelas pendengar yang tidak wujud. Ini biasanya disebabkan oleh mod yang tidak serasi atau rosak.</p><p><b>Solución:</b> Kemas kini atau buang mod yang merujuk kepada kelas yang hilang.</p>";
	}

	@Override
	public String nombreErrorListenerRift() {
		return "Rift: kelas pendengar tidak ditemui";
	}

	@Override
	public String mensajeErrorTransformadorAccesoRift() {
		return "<b style='color:#FF4444;'>Rift tidak dapat memulakan penukar akses.</b><p>Penukar akses mengubah keterlihatan kelas untuk mod. Tanpanya, mod tidak dapat mengakses medan dan kaedah peribadi.</p><p><b>Solución:</b> Pasang semula Rift dan mod yang terjejas.</p>";
	}

	@Override
	public String nombreErrorTransformadorAccesoRift() {
		return "Rift: penukar akses gagal";
	}

	@Override
	public String mensajeAdvertenciaJarNoValidoRift() {
		return "<b style='color:#FFAA00;'>Rift menemui fail JAR yang tidak sah.</b><p>Sebuah fail dalam folder mod bukan JAR yang sah. Rift akan melangkau dan terus memuat mod lain.</p><p><b>Solución:</b> Buang fail yang rosak dari folder mod.</p>";
	}

	@Override
	public String nombreAdvertenciaJarNoValidoRift() {
		return "Rift: fail JAR tidak sah (amaran)";
	}

	@Override
	public String mensajeErrorJavaVersionM3L() {
		return "<b style='color:#FF4444;'>M3L memerlukan Java 7 atau lebih baru.</b><p>Versi Java anda terlalu lama untuk menjalankan pelancar M3L.</p><p><b>Solución:</b> Kemas kini ke versi Java yang lebih baru.</p>";
	}

	@Override
	public String nombreErrorJavaVersionM3L() {
		return "M3L: versi Java terlalu lama";
	}

	@Override
	public String mensajeErrorMinecraftVersionNoInstaladaM3L() {
		return "<b style='color:#FF4444;'>M3L memerlukan Minecraft dipasang terlebih dahulu.</b><p>Pasang versi Minecraft asas sebelum memasang M3L.</p>";
	}

	@Override
	public String nombreErrorMinecraftVersionNoInstaladaM3L() {
		return "M3L: Minecraft belum dipasang";
	}

	@Override
	public String mensajeErrorJSONLauncherM3L() {
		return "<b style='color:#FF4444;'>M3L tidak dapat membaca konfigurasi pelancar.</b><p>Fail launcher_profiles.json rosak atau tidak dapat diakses. M3L tidak dapat bermula tanpa konfigurasi yang sah.</p><p><b>Solución:</b> Pulihkan atau cipta semula fail launcher_profiles.json.</p>";
	}

	@Override
	public String nombreErrorJSONLauncherM3L() {
		return "M3L: konfigurasi pelancar rosak";
	}

	@Override
	public String mensajeAdvertenciaOSNoDetectadoM3L() {
		return "<b style='color:#FFAA00;'>M3L tidak dapat mengesan sistem operasi anda.</b><p>M3L hanya mengenali Windows, Mac dan Linux. Ia mungkin menggunakan Linux sebagai lalai, yang boleh menyebabkan masalah laluan.</p>";
	}

	@Override
	public String nombreAdvertenciaOSNoDetectadoM3L() {
		return "M3L: sistem operasi tidak dikesan (amaran)";
	}

	@Override
	public String mensajeErrorMySQLConexionHMod() {
		return "<b style='color:#FF4444;'>HMod tidak dapat menyambung ke MySQL.</b><p>Sambungan pangkalan data pelayan gagal. Semua operasi pangkalan data akan gagal.</p><p><b>Solución:</b> Semak sama ada pelayan MySQL berjalan dan tetapan sambungan betul.</p>";
	}

	@Override
	public String nombreErrorMySQLConexionHMod() {
		return "HMod: sambungan MySQL gagal";
	}

	@Override
	public String mensajeErrorCargaPluginHMod() {
		return "<b style='color:#FF4444;'>HMod gagal memuat plugin.</b><p>Sebuah plugin rosak, tidak serasi atau hilang. Plugin tidak akan dimuat.</p><p><b>Solución:</b> Semak sama ada semua plugin serasi dengan versi HMod anda.</p>";
	}

	@Override
	public String nombreErrorCargaPluginHMod() {
		return "HMod: kegagalan memuat plugin";
	}

	@Override
	public String mensajeErrorGrupoPorDefectoHMod() {
		return "<b style='color:#FF4444;'>HMod tidak mempunyai kumpulan lalai yang dikonfigurasi.</b><p>Sekurang-kurangnya satu kumpulan mesti ditandakan sebagai kumpulan lalai. Tanpanya, pemain baru tidak dapat ditugaskan dengan betul.</p><p><b>Solución:</b> Tandakan satu kumpulan sebagai DefaultGroup dalam konfigurasi HMod anda.</p>";
	}

	@Override
	public String nombreErrorGrupoPorDefectoHMod() {
		return "HMod: tiada kumpulan lalai";
	}

	@Override
	public String mensajeAdvertenciaPluginHookHMod() {
		return "<b style='color:#FFAA00;'>Pengecualian hook plugin HMod.</b><p>Sebuah plugin membuang pengecualian semasa panggilan hook. Pelayan terus berjalan, tetapi fungsi plugin yang terjejas mungkin rosak.</p><p><b>Solución:</b> Kemas kini atau gantikan plugin yang terjejas.</p>";
	}

	@Override
	public String nombreAdvertenciaPluginHookHMod() {
		return "HMod: pengecualian hook plugin (amaran)";
	}

	@Override
	public String mensajeErrorInicializacionModLoader() {
		return "<b style='color:#FF4444;'>ModLoader gagal dimulakan.</b><p>ModLoader tidak dapat bermula kerana ralat refleksi, ketidakserasian versi atau mod yang rosak. Permainan tidak akan memuat mod.</p><p><b>Solución:</b> Semak sama ada ModLoader dan semua mod serasi dengan versi Minecraft anda.</p>";
	}

	@Override
	public String nombreErrorInicializacionModLoader() {
		return "ModLoader: kegagalan permulaan";
	}

	@Override
	public String mensajeErrorCargaModModLoader() {
		return "<b style='color:#FF4444;'>ModLoader gagal memuat mod.</b><p>Sebuah mod rosak, tidak serasi atau mempunyai ralat kod. Ia tidak akan dimuat.</p><p><b>Solución:</b> Buang atau kemas kini mod yang rosak.</p>";
	}

	@Override
	public String nombreErrorCargaModModLoader() {
		return "ModLoader: kegagalan memuat mod";
	}

	@Override
	public String mensajeErrorDependenciaModuloModLoader() {
		return "<b style='color:#FF4444;'>ModLoader kehilangan kebergantungan.</b><p>Sebuah mod memerlukan mod lain yang belum dipasang. Mod tidak akan dimuat tanpa kebergantungannya.</p><p><b>Solución:</b> Pasang mod kebergantungan yang diperlukan.</p>";
	}

	@Override
	public String nombreErrorDependenciaModuloModLoader() {
		return "ModLoader: kebergantungan hilang";
	}

	@Override
	public String mensajeAdvertenciaCanalInvalidoModLoader() {
		return "<b style='color:#FFAA00;'>Nama saluran tidak sah dalam ModLoader.</b><p>Sebuah mod mendaftar saluran pakej dengan nama yang terlalu panjang (≥ 16 aksara). Komunikasi pakej mod ini mungkin tidak berfungsi dengan betul.</p><p><b>Solución:</b> Kemas kini mod untuk menggunakan nama saluran yang lebih pendek.</p>";
	}

	@Override
	public String nombreAdvertenciaCanalInvalidoModLoader() {
		return "ModLoader: nama saluran tidak sah (amaran)";
	}

	@Override
	public String mensajeErrorRegistroPacketModLoaderMP() {
		return "<b style='color:#FF4444;'>Pendaftaran pakej ModLoaderMP gagal.</b><p>Pendaftaran Packet230 melalui refleksi gagal, biasanya kerana ketidakserasian versi. Rangkaian berbilang pemain tidak akan berfungsi.</p><p><b>Solución:</b> Pastikan ModLoaderMP sepadan dengan versi Minecraft anda.</p>";
	}

	@Override
	public String nombreErrorRegistroPacketModLoaderMP() {
		return "ModLoaderMP: pendaftaran pakej gagal";
	}

	@Override
	public String mensajeErrorEntidadPersonalizadaModLoaderMP() {
		return "<b style='color:#FF4444;'>Penciptaan entiti tersuai ModLoaderMP gagal.</b><p>Entiti tersuai tidak dapat dimulakan semasa berbilang pemain. Entiti tidak akan muncul dalam permainan.</p><p><b>Solución:</b> Semak sama ada mod yang menyediakan entiti serasi.</p>";
	}

	@Override
	public String nombreErrorEntidadPersonalizadaModLoaderMP() {
		return "ModLoaderMP: entiti tersuai gagal";
	}

	@Override
	public String mensajeErrorModNuloModLoaderMP() {
		return "<b style='color:#FF4444;'>Rujukan mod null dalam ModLoaderMP.</b><p>Sebuah mod memanggil sendPacket atau sendKey dengan rujukan null. Ini adalah ralat pengaturcaraan dalam mod.</p><p><b>Solución:</b> Kemas kini atau gantikan mod dengan ralat rujukan null.</p>";
	}

	@Override
	public String nombreErrorModNuloModLoaderMP() {
		return "ModLoaderMP: rujukan mod null";
	}

	@Override
	public String mensajeErrorDatosPacketModLoaderMP() {
		return "<b style='color:#FF4444;'>Data pakej ModLoaderMP terlalu besar.</b><p>Sebuah pakej mengandungi lebih daripada 65535 elemen, melebihi saiz maksimum. Pakej tidak dapat dihantar.</p><p><b>Solución:</b> Kemas kini mod yang menghantar pakej terlalu besar.</p>";
	}

	@Override
	public String nombreErrorDatosPacketModLoaderMP() {
		return "ModLoaderMP: data pakej terlalu besar";
	}

	@Override
	public String mensajeAdvertenciaRegistroDuplicadoModLoaderMP() {
		return "<b style='color:#FFAA00;'>Pendaftaran duplikat dalam ModLoaderMP.</b><p>Jenis inventori GUI atau ID entiti didaftarkan dua kali. Pendaftaran kedua diabaikan.</p><p><b>Solución:</b> Semak sama ada terdapat mod yang konflik.</p>";
	}

	@Override
	public String nombreAdvertenciaRegistroDuplicadoModLoaderMP() {
		return "ModLoaderMP: pendaftaran duplikat (amaran)";
	}

	@Override
	public String mensajeErrorPluginInvalidoCanary() {
		return "<b style='color:#FF4444;'>Plugin tidak sah dalam Canary/Neptune.</b><p>Canary gagal memuat plugin kerana ia rosak, mempunyai fail Canary.inf yang tidak terbentuk dengan baik atau kelas utama yang ditentukan tidak wujud.</p><p><b>Solución:</b> Sahkan bahawa plugin serasi dengan versi Canary/Neptune anda dan tidak rosak.</p>";
	}

	@Override
	public String nombreErrorPluginInvalidoCanary() {
		return "Canary/Neptune: plugin tidak sah";
	}

	@Override
	public String mensajeErrorDependenciaPluginCanary() {
		return "<b style='color:#FF4444;'>Kebergantungan plugin hilang dalam Canary/Neptune.</b><p>Sebuah plugin menyatakan kebergantungan yang belum dipasang pada pelayan. Plugin tidak dapat dimuat tanpa kebergantungannya.</p><p><b>Solución:</b> Pasang kebergantungan yang diperlukan untuk plugin.</p>";
	}

	@Override
	public String nombreErrorDependenciaPluginCanary() {
		return "Canary/Neptune: kebergantungan plugin hilang";
	}

	@Override
	public String mensajeErrorConsistenciaHookCanary() {
		return "<b style='color:#FF4444;'>Ralat tandatangan hook dalam Canary/Neptune.</b><p>Sebuah plugin mendaftar kaedah hook dengan tandatangan yang salah. Kaedah @HookHandler mesti mempunyai tepat 1 parameter yang merupakan subjenis Hook.</p><p><b>Solución:</b> Kemas kini atau gantikan plugin yang menyebabkan ralat ini.</p>";
	}

	@Override
	public String nombreErrorConsistenciaHookCanary() {
		return "Canary/Neptune: tandatangan hook salah";
	}

	@Override
	public String mensajeAdvertenciaEjecucionHookCanary() {
		return "<b style='color:#FFAA00;'>Pengecualian hook dalam Canary/Neptune.</b><p>Sebuah plugin membuang pengecualian semasa mengendalikan acara (hook). Pelayan terus berjalan, tetapi fungsi plugin yang terjejas mungkin rosak.</p><p><b>Solución:</b> Kemas kini atau gantikan plugin yang menyebabkan pengecualian ini.</p>";
	}

	@Override
	public String nombreAdvertenciaEjecucionHookCanary() {
		return "Canary/Neptune: pengecualian hook (amaran)";
	}

	@Override
	public String mensajeErrorBaseDeDatosCanary() {
		return "<b style='color:#FF4444;'>Ralat pangkalan data dalam Canary/Neptune.</b><p>Canary tidak dapat mengakses pangkalan data. Ini boleh disebabkan oleh sambungan yang gagal, penyesuai pangkalan data yang rosak atau konfigurasi yang salah.</p><p><b>Solución:</b> Sahkan konfigurasi pangkalan data dan bahawa penyesuai serasi.</p>";
	}

	@Override
	public String nombreErrorBaseDeDatosCanary() {
		return "Canary/Neptune: ralat pangkalan data";
	}

	@Override
	public String mensajeErrorComandoCanary() {
		return "<b style='color:#FF4444;'>Ralat arahan dalam Canary/Neptune.</b><p>Sebuah arahan gagal mendaftar. Ini boleh disebabkan oleh arahan induk yang hilang, arahan duplikat yang didaftarkan oleh plugin lain atau ralat sistem arahan.</p><p><b>Solución:</b> Semak konflik arahan antara plugin.</p>";
	}

	@Override
	public String nombreErrorComandoCanary() {
		return "Canary/Neptune: ralat arahan";
	}
}
